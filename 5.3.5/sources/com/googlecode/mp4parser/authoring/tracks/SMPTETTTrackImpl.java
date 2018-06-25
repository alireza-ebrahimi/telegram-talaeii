package com.googlecode.mp4parser.authoring.tracks;

import android.support.v4.internal.view.SupportMenu;
import com.coremedia.iso.Utf8;
import com.coremedia.iso.boxes.SampleDescriptionBox;
import com.coremedia.iso.boxes.SubSampleInformationBox;
import com.coremedia.iso.boxes.SubSampleInformationBox.SubSampleEntry;
import com.coremedia.iso.boxes.SubSampleInformationBox.SubSampleEntry.SubsampleEntry;
import com.googlecode.mp4parser.authoring.AbstractTrack;
import com.googlecode.mp4parser.authoring.Sample;
import com.googlecode.mp4parser.authoring.TrackMetaData;
import com.googlecode.mp4parser.util.Iso639;
import com.mp4parser.iso14496.part30.XMLSubtitleSampleEntry;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SMPTETTTrackImpl extends AbstractTrack {
    public static final String SMPTE_TT_NAMESPACE = "http://www.smpte-ra.org/schemas/2052-1/2010/smpte-tt";
    XMLSubtitleSampleEntry XMLSubtitleSampleEntry = new XMLSubtitleSampleEntry();
    boolean containsImages;
    SampleDescriptionBox sampleDescriptionBox = new SampleDescriptionBox();
    private long[] sampleDurations;
    List<Sample> samples = new ArrayList();
    SubSampleInformationBox subSampleInformationBox = new SubSampleInformationBox();
    TrackMetaData trackMetaData = new TrackMetaData();

    private static class TextTrackNamespaceContext implements NamespaceContext {
        private TextTrackNamespaceContext() {
        }

        public String getNamespaceURI(String prefix) {
            if (prefix.equals("ttml")) {
                return "http://www.w3.org/ns/ttml";
            }
            if (prefix.equals("smpte")) {
                return SMPTETTTrackImpl.SMPTE_TT_NAMESPACE;
            }
            return null;
        }

        public Iterator getPrefixes(String val) {
            return Arrays.asList(new String[]{"ttml", "smpte"}).iterator();
        }

        public String getPrefix(String uri) {
            if (uri.equals("http://www.w3.org/ns/ttml")) {
                return "ttml";
            }
            if (uri.equals(SMPTETTTrackImpl.SMPTE_TT_NAMESPACE)) {
                return "smpte";
            }
            return null;
        }
    }

    static long toTime(String expr) {
        Matcher m = Pattern.compile("([0-9][0-9]):([0-9][0-9]):([0-9][0-9])([\\.:][0-9][0-9]?[0-9]?)?").matcher(expr);
        if (m.matches()) {
            String hours = m.group(1);
            String minutes = m.group(2);
            String seconds = m.group(3);
            String fraction = m.group(4);
            if (fraction == null) {
                fraction = ".000";
            }
            return (long) (((double) (((((Long.parseLong(hours) * 60) * 60) * 1000) + ((Long.parseLong(minutes) * 60) * 1000)) + (Long.parseLong(seconds) * 1000))) + (Double.parseDouble("0" + fraction.replace(":", ".")) * 1000.0d));
        }
        throw new RuntimeException("Cannot match " + expr + " to time expression");
    }

    public static String getLanguage(Document document) {
        return document.getDocumentElement().getAttribute("xml:lang");
    }

    public static long earliestTimestamp(Document document) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        NamespaceContext ctx = new TextTrackNamespaceContext();
        XPath xpath = xPathfactory.newXPath();
        xpath.setNamespaceContext(ctx);
        try {
            NodeList timedNodes = (NodeList) xpath.compile("//*[@begin]").evaluate(document, XPathConstants.NODESET);
            long earliestTimestamp = 0;
            for (int i = 0; i < timedNodes.getLength(); i++) {
                earliestTimestamp = Math.min(toTime(timedNodes.item(i).getAttributes().getNamedItem("begin").getNodeValue()), earliestTimestamp);
            }
            return earliestTimestamp;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public static long latestTimestamp(Document document) {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        NamespaceContext ctx = new TextTrackNamespaceContext();
        XPath xpath = xPathfactory.newXPath();
        xpath.setNamespaceContext(ctx);
        try {
            NodeList timedNodes = (NodeList) xpath.compile("//*[@begin]").evaluate(document, XPathConstants.NODESET);
            long lastTimeStamp = 0;
            for (int i = 0; i < timedNodes.getLength(); i++) {
                long end;
                Node n = timedNodes.item(i);
                String begin = n.getAttributes().getNamedItem("begin").getNodeValue();
                if (n.getAttributes().getNamedItem("dur") != null) {
                    end = toTime(begin) + toTime(n.getAttributes().getNamedItem("dur").getNodeValue());
                } else if (n.getAttributes().getNamedItem(TtmlNode.END) != null) {
                    end = toTime(n.getAttributes().getNamedItem(TtmlNode.END).getNodeValue());
                } else {
                    throw new RuntimeException("neither end nor dur attribute is present");
                }
                lastTimeStamp = Math.max(end, lastTimeStamp);
            }
            return lastTimeStamp;
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }

    public SMPTETTTrackImpl(File... files) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException {
        super(files[0].getName());
        this.sampleDurations = new long[files.length];
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        long startTime = 0;
        String firstLang = null;
        for (int sampleNo = 0; sampleNo < files.length; sampleNo++) {
            final File file = files[sampleNo];
            SubSampleEntry subSampleEntry = new SubSampleEntry();
            this.subSampleInformationBox.getEntries().add(subSampleEntry);
            subSampleEntry.setSampleDelta(1);
            Document doc = dBuilder.parse(file);
            String lang = getLanguage(doc);
            if (firstLang == null) {
                firstLang = lang;
            } else if (!firstLang.equals(lang)) {
                throw new RuntimeException("Within one Track all sample documents need to have the same language");
            }
            XPathFactory xPathfactory = XPathFactory.newInstance();
            NamespaceContext ctx = new TextTrackNamespaceContext();
            XPath xpath = xPathfactory.newXPath();
            xpath.setNamespaceContext(ctx);
            long lastTimeStamp = latestTimestamp(doc);
            this.sampleDurations[sampleNo] = lastTimeStamp - startTime;
            startTime = lastTimeStamp;
            NodeList nl = (NodeList) xpath.compile("/ttml:tt/ttml:body/ttml:div/@smpte:backgroundImage").evaluate(doc, XPathConstants.NODESET);
            HashMap<String, String> internalName2Original = new HashMap();
            Collection<String> originalNames = new HashSet();
            for (int i = 0; i < nl.getLength(); i++) {
                originalNames.add(nl.item(i).getNodeValue());
            }
            Collection<String> arrayList = new ArrayList(originalNames);
            Collections.sort((List) arrayList);
            int p = 1;
            for (String originalName : arrayList) {
                int p2 = p + 1;
                internalName2Original.put(originalName, "urn:dece:container:subtitleimageindex:" + p + originalName.substring(originalName.lastIndexOf(".")));
                p = p2;
            }
            if (arrayList.isEmpty()) {
                this.samples.add(new Sample() {
                    public void writeTo(WritableByteChannel channel) throws IOException {
                        Channels.newOutputStream(channel).write(SMPTETTTrackImpl.this.streamToByteArray(new FileInputStream(file)));
                    }

                    public long getSize() {
                        return file.length();
                    }

                    public ByteBuffer asByteBuffer() {
                        try {
                            return ByteBuffer.wrap(SMPTETTTrackImpl.this.streamToByteArray(new FileInputStream(file)));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
            } else {
                String xml;
                String str = new String(streamToByteArray(new FileInputStream(file)));
                for (Entry<String, String> stringStringEntry : internalName2Original.entrySet()) {
                    xml = xml.replace((CharSequence) stringStringEntry.getKey(), (CharSequence) stringStringEntry.getValue());
                }
                final String finalXml = xml;
                List<File> pix = new ArrayList();
                final List<File> list = pix;
                this.samples.add(new Sample() {
                    public void writeTo(WritableByteChannel channel) throws IOException {
                        channel.write(ByteBuffer.wrap(Utf8.convert(finalXml)));
                        for (File file1 : list) {
                            FileInputStream fis = new FileInputStream(file1);
                            byte[] buffer = new byte[8096];
                            while (true) {
                                int n = fis.read(buffer);
                                if (-1 != n) {
                                    channel.write(ByteBuffer.wrap(buffer, 0, n));
                                }
                            }
                        }
                    }

                    public long getSize() {
                        long l = (long) Utf8.convert(finalXml).length;
                        for (File file1 : list) {
                            l += file1.length();
                        }
                        return l;
                    }

                    public ByteBuffer asByteBuffer() {
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        try {
                            writeTo(Channels.newChannel(baos));
                            return ByteBuffer.wrap(baos.toByteArray());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                SubsampleEntry xmlEntry = new SubsampleEntry();
                xmlEntry.setSubsampleSize((long) Utf8.utf8StringLengthInBytes(finalXml));
                subSampleEntry.getSubsampleEntries().add(xmlEntry);
                for (String originalName2 : arrayList) {
                    File file2 = new File(file.getParentFile(), originalName2);
                    pix.add(file2);
                    SubsampleEntry sse = new SubsampleEntry();
                    sse.setSubsampleSize(file2.length());
                    subSampleEntry.getSubsampleEntries().add(sse);
                }
            }
        }
        this.trackMetaData.setLanguage(Iso639.convert2to3(firstLang));
        this.XMLSubtitleSampleEntry.setNamespace(SMPTE_TT_NAMESPACE);
        this.XMLSubtitleSampleEntry.setSchemaLocation(SMPTE_TT_NAMESPACE);
        if (this.containsImages) {
            this.XMLSubtitleSampleEntry.setAuxiliaryMimeTypes("image/png");
        } else {
            this.XMLSubtitleSampleEntry.setAuxiliaryMimeTypes("");
        }
        this.sampleDescriptionBox.addBox(this.XMLSubtitleSampleEntry);
        this.trackMetaData.setTimescale(30000);
        this.trackMetaData.setLayer(SupportMenu.USER_MASK);
    }

    private byte[] streamToByteArray(InputStream input) throws IOException {
        byte[] buffer = new byte[8096];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while (true) {
            int n = input.read(buffer);
            if (-1 == n) {
                return output.toByteArray();
            }
            output.write(buffer, 0, n);
        }
    }

    public SampleDescriptionBox getSampleDescriptionBox() {
        return this.sampleDescriptionBox;
    }

    public long[] getSampleDurations() {
        long[] adoptedSampleDuration = new long[this.sampleDurations.length];
        for (int i = 0; i < adoptedSampleDuration.length; i++) {
            adoptedSampleDuration[i] = (this.sampleDurations[i] * this.trackMetaData.getTimescale()) / 1000;
        }
        return adoptedSampleDuration;
    }

    public TrackMetaData getTrackMetaData() {
        return this.trackMetaData;
    }

    public String getHandler() {
        return "subt";
    }

    public List<Sample> getSamples() {
        return this.samples;
    }

    public SubSampleInformationBox getSubsampleInformationBox() {
        return this.subSampleInformationBox;
    }

    public void close() throws IOException {
    }
}
