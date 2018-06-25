package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.google.firebase.analytics.FirebaseAnalytics.C1797b;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.drm.DrmInitData;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;
import org.telegram.messenger.exoplayer2.extractor.mp4.PsshAtomUtil;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.SegmentList;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.SegmentTemplate;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.SegmentTimelineElement;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase;
import org.telegram.messenger.exoplayer2.upstream.ParsingLoadable.Parser;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.UriUtil;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.util.XmlPullParserUtil;
import org.xml.sax.helpers.DefaultHandler;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class DashManifestParser extends DefaultHandler implements Parser<DashManifest> {
    private static final Pattern CEA_608_ACCESSIBILITY_PATTERN = Pattern.compile("CC([1-4])=.*");
    private static final Pattern CEA_708_ACCESSIBILITY_PATTERN = Pattern.compile("([1-9]|[1-5][0-9]|6[0-3])=.*");
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile("(\\d+)(?:/(\\d+))?");
    private static final String TAG = "MpdParser";
    private final String contentId;
    private final XmlPullParserFactory xmlParserFactory;

    private static final class RepresentationInfo {
        public final String baseUrl;
        public final ArrayList<SchemeData> drmSchemeDatas;
        public final Format format;
        public final ArrayList<Descriptor> inbandEventStreams;
        public final SegmentBase segmentBase;

        public RepresentationInfo(Format format, String str, SegmentBase segmentBase, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
            this.format = format;
            this.baseUrl = str;
            this.segmentBase = segmentBase;
            this.drmSchemeDatas = arrayList;
            this.inbandEventStreams = arrayList2;
        }
    }

    public DashManifestParser() {
        this(null);
    }

    public DashManifestParser(String str) {
        this.contentId = str;
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
        } catch (Throwable e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    private static int checkContentTypeConsistency(int i, int i2) {
        if (i == -1) {
            return i2;
        }
        if (i2 == -1) {
            return i;
        }
        Assertions.checkState(i == i2);
        return i;
    }

    private static String checkLanguageConsistency(String str, String str2) {
        if (str == null) {
            return str2;
        }
        if (str2 == null) {
            return str;
        }
        Assertions.checkState(str.equals(str2));
        return str;
    }

    private static String getSampleMimeType(String str, String str2) {
        if (MimeTypes.isAudio(str)) {
            return MimeTypes.getAudioMediaMimeType(str2);
        }
        if (MimeTypes.isVideo(str)) {
            return MimeTypes.getVideoMediaMimeType(str2);
        }
        if (mimeTypeIsRawText(str)) {
            return str;
        }
        if (MimeTypes.APPLICATION_MP4.equals(str)) {
            if ("stpp".equals(str2)) {
                return MimeTypes.APPLICATION_TTML;
            }
            if ("wvtt".equals(str2)) {
                return MimeTypes.APPLICATION_MP4VTT;
            }
        } else if (MimeTypes.APPLICATION_RAWCC.equals(str)) {
            if (str2 != null) {
                if (str2.contains("cea708")) {
                    return MimeTypes.APPLICATION_CEA708;
                }
                if (str2.contains("eia608") || str2.contains("cea608")) {
                    return MimeTypes.APPLICATION_CEA608;
                }
            }
            return null;
        }
        return null;
    }

    private static boolean mimeTypeIsRawText(String str) {
        return MimeTypes.isText(str) || MimeTypes.APPLICATION_TTML.equals(str) || MimeTypes.APPLICATION_MP4VTT.equals(str) || MimeTypes.APPLICATION_CEA708.equals(str) || MimeTypes.APPLICATION_CEA608.equals(str);
    }

    protected static String parseBaseUrl(XmlPullParser xmlPullParser, String str) {
        xmlPullParser.next();
        return UriUtil.resolve(str, xmlPullParser.getText());
    }

    protected static int parseCea608AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-608:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_608_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.w(TAG, "Unable to parse CEA-608 channel number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static int parseCea708AccessibilityChannel(List<Descriptor> list) {
        for (int i = 0; i < list.size(); i++) {
            Descriptor descriptor = (Descriptor) list.get(i);
            if ("urn:scte:dash:cc:cea-708:2015".equals(descriptor.schemeIdUri) && descriptor.value != null) {
                Matcher matcher = CEA_708_ACCESSIBILITY_PATTERN.matcher(descriptor.value);
                if (matcher.matches()) {
                    return Integer.parseInt(matcher.group(1));
                }
                Log.w(TAG, "Unable to parse CEA-708 service block number from: " + descriptor.value);
            }
        }
        return -1;
    }

    protected static long parseDateTime(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Util.parseXsDateTime(attributeValue);
    }

    protected static Descriptor parseDescriptor(XmlPullParser xmlPullParser, String str) {
        String parseString = parseString(xmlPullParser, "schemeIdUri", TtmlNode.ANONYMOUS_REGION_ID);
        String parseString2 = parseString(xmlPullParser, C1797b.VALUE, null);
        String parseString3 = parseString(xmlPullParser, TtmlNode.ATTR_ID, null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, str));
        return new Descriptor(parseString, parseString2, parseString3);
    }

    protected static long parseDuration(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Util.parseXsDuration(attributeValue);
    }

    protected static float parseFrameRate(XmlPullParser xmlPullParser, float f) {
        CharSequence attributeValue = xmlPullParser.getAttributeValue(null, "frameRate");
        if (attributeValue == null) {
            return f;
        }
        Matcher matcher = FRAME_RATE_PATTERN.matcher(attributeValue);
        if (!matcher.matches()) {
            return f;
        }
        int parseInt = Integer.parseInt(matcher.group(1));
        Object group = matcher.group(2);
        return !TextUtils.isEmpty(group) ? ((float) parseInt) / ((float) Integer.parseInt(group)) : (float) parseInt;
    }

    protected static int parseInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }

    protected static long parseLong(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? j : Long.parseLong(attributeValue);
    }

    protected static String parseString(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue == null ? str2 : attributeValue;
    }

    protected AdaptationSet buildAdaptationSet(int i, int i2, List<Representation> list, List<Descriptor> list2, List<Descriptor> list3) {
        return new AdaptationSet(i, i2, list, list2, list3);
    }

    protected Format buildFormat(String str, String str2, int i, int i2, float f, int i3, int i4, int i5, String str3, int i6, List<Descriptor> list, String str4) {
        String sampleMimeType = getSampleMimeType(str2, str4);
        if (sampleMimeType != null) {
            if (MimeTypes.isVideo(sampleMimeType)) {
                return Format.createVideoContainerFormat(str, str2, sampleMimeType, str4, i5, i, i2, f, null, i6);
            }
            if (MimeTypes.isAudio(sampleMimeType)) {
                return Format.createAudioContainerFormat(str, str2, sampleMimeType, str4, i5, i3, i4, null, i6, str3);
            }
            if (mimeTypeIsRawText(sampleMimeType)) {
                int parseCea608AccessibilityChannel = MimeTypes.APPLICATION_CEA608.equals(sampleMimeType) ? parseCea608AccessibilityChannel(list) : MimeTypes.APPLICATION_CEA708.equals(sampleMimeType) ? parseCea708AccessibilityChannel(list) : -1;
                return Format.createTextContainerFormat(str, str2, sampleMimeType, str4, i5, i6, str3, parseCea608AccessibilityChannel);
            }
        }
        return Format.createContainerFormat(str, str2, sampleMimeType, str4, i5, i6, str3);
    }

    protected DashManifest buildMediaPresentationDescription(long j, long j2, long j3, boolean z, long j4, long j5, long j6, UtcTimingElement utcTimingElement, Uri uri, List<Period> list) {
        return new DashManifest(j, j2, j3, z, j4, j5, j6, utcTimingElement, uri, list);
    }

    protected Period buildPeriod(String str, long j, List<AdaptationSet> list) {
        return new Period(str, j, list);
    }

    protected RangedUri buildRangedUri(String str, long j, long j2) {
        return new RangedUri(str, j, j2);
    }

    protected Representation buildRepresentation(RepresentationInfo representationInfo, String str, ArrayList<SchemeData> arrayList, ArrayList<Descriptor> arrayList2) {
        Format format = representationInfo.format;
        List list = representationInfo.drmSchemeDatas;
        list.addAll(arrayList);
        if (!list.isEmpty()) {
            format = format.copyWithDrmInitData(new DrmInitData(list));
        }
        List list2 = representationInfo.inbandEventStreams;
        list2.addAll(arrayList2);
        return Representation.newInstance(str, -1, format, representationInfo.baseUrl, representationInfo.segmentBase, list2);
    }

    protected SegmentList buildSegmentList(RangedUri rangedUri, long j, long j2, int i, long j3, List<SegmentTimelineElement> list, List<RangedUri> list2) {
        return new SegmentList(rangedUri, j, j2, i, j3, list, list2);
    }

    protected SegmentTemplate buildSegmentTemplate(RangedUri rangedUri, long j, long j2, int i, long j3, List<SegmentTimelineElement> list, UrlTemplate urlTemplate, UrlTemplate urlTemplate2) {
        return new SegmentTemplate(rangedUri, j, j2, i, j3, list, urlTemplate, urlTemplate2);
    }

    protected SegmentTimelineElement buildSegmentTimelineElement(long j, long j2) {
        return new SegmentTimelineElement(j, j2);
    }

    protected SingleSegmentBase buildSingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
        return new SingleSegmentBase(rangedUri, j, j2, j3, j4);
    }

    protected UtcTimingElement buildUtcTimingElement(String str, String str2) {
        return new UtcTimingElement(str, str2);
    }

    protected int getContentType(Format format) {
        String str = format.sampleMimeType;
        return TextUtils.isEmpty(str) ? -1 : MimeTypes.isVideo(str) ? 2 : MimeTypes.isAudio(str) ? 1 : mimeTypeIsRawText(str) ? 3 : -1;
    }

    public DashManifest parse(Uri uri, InputStream inputStream) {
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            newPullParser.setInput(inputStream, null);
            if (newPullParser.next() == 2 && "MPD".equals(newPullParser.getName())) {
                return parseMediaPresentationDescription(newPullParser, uri.toString());
            }
            throw new ParserException("inputStream does not contain a valid media presentation description");
        } catch (Throwable e) {
            throw new ParserException(e);
        }
    }

    protected AdaptationSet parseAdaptationSet(XmlPullParser xmlPullParser, String str, SegmentBase segmentBase) {
        int i;
        int parseInt = parseInt(xmlPullParser, TtmlNode.ATTR_ID, -1);
        int parseContentType = parseContentType(xmlPullParser);
        String attributeValue = xmlPullParser.getAttributeValue(null, "mimeType");
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "codecs");
        int parseInt2 = parseInt(xmlPullParser, "width", -1);
        int parseInt3 = parseInt(xmlPullParser, "height", -1);
        float parseFrameRate = parseFrameRate(xmlPullParser, -1.0f);
        int i2 = -1;
        int parseInt4 = parseInt(xmlPullParser, "audioSamplingRate", -1);
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "lang");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        List arrayList3 = new ArrayList();
        ArrayList arrayList4 = new ArrayList();
        List arrayList5 = new ArrayList();
        int i3 = 0;
        Object obj = null;
        SegmentBase segmentBase2 = segmentBase;
        String str2 = str;
        while (true) {
            Object obj2;
            String str3;
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (obj == null) {
                    str2 = parseBaseUrl(xmlPullParser, str2);
                    obj2 = 1;
                    str3 = attributeValue3;
                    i = parseContentType;
                }
                obj2 = obj;
                str3 = attributeValue3;
                i = parseContentType;
            } else {
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "ContentProtection")) {
                    SchemeData parseContentProtection = parseContentProtection(xmlPullParser);
                    if (parseContentProtection != null) {
                        arrayList.add(parseContentProtection);
                    }
                    obj2 = obj;
                    str3 = attributeValue3;
                    i = parseContentType;
                } else {
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "ContentComponent")) {
                        attributeValue3 = checkLanguageConsistency(attributeValue3, xmlPullParser.getAttributeValue(null, "lang"));
                        str3 = attributeValue3;
                        i = checkContentTypeConsistency(parseContentType, parseContentType(xmlPullParser));
                        obj2 = obj;
                    } else {
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "Role")) {
                            i3 |= parseRole(xmlPullParser);
                            obj2 = obj;
                            str3 = attributeValue3;
                            i = parseContentType;
                        } else {
                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "AudioChannelConfiguration")) {
                                i2 = parseAudioChannelConfiguration(xmlPullParser);
                                obj2 = obj;
                                str3 = attributeValue3;
                                i = parseContentType;
                            } else {
                                if (XmlPullParserUtil.isStartTag(xmlPullParser, "Accessibility")) {
                                    arrayList3.add(parseDescriptor(xmlPullParser, "Accessibility"));
                                    obj2 = obj;
                                    str3 = attributeValue3;
                                    i = parseContentType;
                                } else {
                                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "SupplementalProperty")) {
                                        arrayList4.add(parseDescriptor(xmlPullParser, "SupplementalProperty"));
                                        obj2 = obj;
                                        str3 = attributeValue3;
                                        i = parseContentType;
                                    } else {
                                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "Representation")) {
                                            RepresentationInfo parseRepresentation = parseRepresentation(xmlPullParser, str2, attributeValue, attributeValue2, parseInt2, parseInt3, parseFrameRate, i2, parseInt4, attributeValue3, i3, arrayList3, segmentBase2);
                                            int checkContentTypeConsistency = checkContentTypeConsistency(parseContentType, getContentType(parseRepresentation.format));
                                            arrayList5.add(parseRepresentation);
                                            str3 = attributeValue3;
                                            i = checkContentTypeConsistency;
                                            obj2 = obj;
                                        } else {
                                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                                                segmentBase2 = parseSegmentBase(xmlPullParser, (SingleSegmentBase) segmentBase2);
                                                obj2 = obj;
                                                str3 = attributeValue3;
                                                i = parseContentType;
                                            } else {
                                                if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                                                    segmentBase2 = parseSegmentList(xmlPullParser, (SegmentList) segmentBase2);
                                                    obj2 = obj;
                                                    str3 = attributeValue3;
                                                    i = parseContentType;
                                                } else {
                                                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                                                        segmentBase2 = parseSegmentTemplate(xmlPullParser, (SegmentTemplate) segmentBase2);
                                                        obj2 = obj;
                                                        str3 = attributeValue3;
                                                        i = parseContentType;
                                                    } else {
                                                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "InbandEventStream")) {
                                                            arrayList2.add(parseDescriptor(xmlPullParser, "InbandEventStream"));
                                                            obj2 = obj;
                                                            str3 = attributeValue3;
                                                            i = parseContentType;
                                                        } else {
                                                            if (XmlPullParserUtil.isStartTag(xmlPullParser)) {
                                                                parseAdaptationSetChild(xmlPullParser);
                                                            }
                                                            obj2 = obj;
                                                            str3 = attributeValue3;
                                                            i = parseContentType;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "AdaptationSet")) {
                break;
            }
            obj = obj2;
            parseContentType = i;
            attributeValue3 = str3;
        }
        List arrayList6 = new ArrayList(arrayList5.size());
        for (int i4 = 0; i4 < arrayList5.size(); i4++) {
            arrayList6.add(buildRepresentation((RepresentationInfo) arrayList5.get(i4), this.contentId, arrayList, arrayList2));
        }
        return buildAdaptationSet(parseInt, i, arrayList6, arrayList3, arrayList4);
    }

    protected void parseAdaptationSetChild(XmlPullParser xmlPullParser) {
    }

    protected int parseAudioChannelConfiguration(XmlPullParser xmlPullParser) {
        int i = -1;
        if ("urn:mpeg:dash:23003:3:audio_channel_configuration:2011".equals(parseString(xmlPullParser, "schemeIdUri", null))) {
            i = parseInt(xmlPullParser, C1797b.VALUE, -1);
        }
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "AudioChannelConfiguration"));
        return i;
    }

    protected SchemeData parseContentProtection(XmlPullParser xmlPullParser) {
        boolean equals = "urn:uuid:9a04f079-9840-4286-ab92-e65be0885f95".equals(xmlPullParser.getAttributeValue(null, "schemeIdUri"));
        String attributeValue = xmlPullParser.getAttributeValue(null, C1797b.VALUE);
        boolean z = false;
        UUID uuid = null;
        byte[] bArr = null;
        do {
            xmlPullParser.next();
            if (bArr == null && XmlPullParserUtil.isStartTag(xmlPullParser, "cenc:pssh") && xmlPullParser.next() == 4) {
                bArr = Base64.decode(xmlPullParser.getText(), 0);
                uuid = PsshAtomUtil.parseUuid(bArr);
                if (uuid == null) {
                    Log.w(TAG, "Skipping malformed cenc:pssh data");
                    bArr = null;
                }
            } else if (bArr == null && equals && XmlPullParserUtil.isStartTag(xmlPullParser, "mspr:pro") && xmlPullParser.next() == 4) {
                bArr = PsshAtomUtil.buildPsshAtom(C3446C.PLAYREADY_UUID, Base64.decode(xmlPullParser.getText(), 0));
                uuid = C3446C.PLAYREADY_UUID;
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "widevine:license")) {
                String attributeValue2 = xmlPullParser.getAttributeValue(null, "robustness_level");
                z = attributeValue2 != null && attributeValue2.startsWith("HW");
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "ContentProtection"));
        return bArr != null ? new SchemeData(uuid, attributeValue, MimeTypes.VIDEO_MP4, bArr, z) : null;
    }

    protected int parseContentType(XmlPullParser xmlPullParser) {
        CharSequence attributeValue = xmlPullParser.getAttributeValue(null, "contentType");
        return TextUtils.isEmpty(attributeValue) ? -1 : MimeTypes.BASE_TYPE_AUDIO.equals(attributeValue) ? 1 : MimeTypes.BASE_TYPE_VIDEO.equals(attributeValue) ? 2 : MimeTypes.BASE_TYPE_TEXT.equals(attributeValue) ? 3 : -1;
    }

    protected RangedUri parseInitialization(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "sourceURL", "range");
    }

    protected DashManifest parseMediaPresentationDescription(XmlPullParser xmlPullParser, String str) {
        long j;
        long parseDateTime = parseDateTime(xmlPullParser, "availabilityStartTime", C3446C.TIME_UNSET);
        long parseDuration = parseDuration(xmlPullParser, "mediaPresentationDuration", C3446C.TIME_UNSET);
        long parseDuration2 = parseDuration(xmlPullParser, "minBufferTime", C3446C.TIME_UNSET);
        String attributeValue = xmlPullParser.getAttributeValue(null, Param.TYPE);
        boolean z = attributeValue != null && attributeValue.equals("dynamic");
        long parseDuration3 = z ? parseDuration(xmlPullParser, "minimumUpdatePeriod", C3446C.TIME_UNSET) : C3446C.TIME_UNSET;
        long parseDuration4 = z ? parseDuration(xmlPullParser, "timeShiftBufferDepth", C3446C.TIME_UNSET) : C3446C.TIME_UNSET;
        long parseDuration5 = z ? parseDuration(xmlPullParser, "suggestedPresentationDelay", C3446C.TIME_UNSET) : C3446C.TIME_UNSET;
        UtcTimingElement utcTimingElement = null;
        List arrayList = new ArrayList();
        Object obj = null;
        long j2 = z ? C3446C.TIME_UNSET : 0;
        String str2 = str;
        Uri uri = null;
        Object obj2 = null;
        while (true) {
            Object obj3;
            String parseBaseUrl;
            Object obj4;
            UtcTimingElement utcTimingElement2;
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (obj == null) {
                    obj3 = 1;
                    parseBaseUrl = parseBaseUrl(xmlPullParser, str2);
                    obj4 = obj2;
                    utcTimingElement2 = utcTimingElement;
                }
                obj3 = obj;
                obj4 = obj2;
                utcTimingElement2 = utcTimingElement;
                parseBaseUrl = str2;
            } else {
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "UTCTiming")) {
                    obj4 = obj2;
                    utcTimingElement2 = parseUtcTiming(xmlPullParser);
                    obj3 = obj;
                    parseBaseUrl = str2;
                } else {
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "Location")) {
                        uri = Uri.parse(xmlPullParser.nextText());
                        obj3 = obj;
                        obj4 = obj2;
                        utcTimingElement2 = utcTimingElement;
                        parseBaseUrl = str2;
                    } else {
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "Period") && obj2 == null) {
                            Pair parsePeriod = parsePeriod(xmlPullParser, str2, j2);
                            Period period = (Period) parsePeriod.first;
                            if (period.startMs != C3446C.TIME_UNSET) {
                                j2 = ((Long) parsePeriod.second).longValue();
                                j2 = j2 == C3446C.TIME_UNSET ? C3446C.TIME_UNSET : j2 + period.startMs;
                                arrayList.add(period);
                            } else if (z) {
                                int i = 1;
                                utcTimingElement2 = utcTimingElement;
                                obj3 = obj;
                                parseBaseUrl = str2;
                            } else {
                                throw new ParserException("Unable to determine start of period " + arrayList.size());
                            }
                        }
                        obj3 = obj;
                        obj4 = obj2;
                        utcTimingElement2 = utcTimingElement;
                        parseBaseUrl = str2;
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "MPD")) {
                break;
            }
            utcTimingElement = utcTimingElement2;
            str2 = parseBaseUrl;
            obj = obj3;
            obj2 = obj4;
        }
        if (parseDuration == C3446C.TIME_UNSET) {
            if (j2 != C3446C.TIME_UNSET) {
                j = j2;
                if (arrayList.isEmpty()) {
                    return buildMediaPresentationDescription(parseDateTime, j, parseDuration2, z, parseDuration3, parseDuration4, parseDuration5, utcTimingElement2, uri, arrayList);
                }
                throw new ParserException("No periods found.");
            } else if (!z) {
                throw new ParserException("Unable to determine duration of static manifest.");
            }
        }
        j = parseDuration;
        if (arrayList.isEmpty()) {
            return buildMediaPresentationDescription(parseDateTime, j, parseDuration2, z, parseDuration3, parseDuration4, parseDuration5, utcTimingElement2, uri, arrayList);
        }
        throw new ParserException("No periods found.");
    }

    protected Pair<Period, Long> parsePeriod(XmlPullParser xmlPullParser, String str, long j) {
        String attributeValue = xmlPullParser.getAttributeValue(null, TtmlNode.ATTR_ID);
        long parseDuration = parseDuration(xmlPullParser, TtmlNode.START, j);
        long parseDuration2 = parseDuration(xmlPullParser, "duration", C3446C.TIME_UNSET);
        List arrayList = new ArrayList();
        Object obj = null;
        SegmentBase segmentBase = null;
        String str2 = str;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (obj == null) {
                    str2 = parseBaseUrl(xmlPullParser, str2);
                    obj = 1;
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "AdaptationSet")) {
                arrayList.add(parseAdaptationSet(xmlPullParser, str2, segmentBase));
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                segmentBase = parseSegmentBase(xmlPullParser, null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                segmentBase = parseSegmentList(xmlPullParser, null);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                segmentBase = parseSegmentTemplate(xmlPullParser, null);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Period"));
        return Pair.create(buildPeriod(attributeValue, parseDuration, arrayList), Long.valueOf(parseDuration2));
    }

    protected RangedUri parseRangedUrl(XmlPullParser xmlPullParser, String str, String str2) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        long j = 0;
        long j2 = -1;
        String attributeValue2 = xmlPullParser.getAttributeValue(null, str2);
        if (attributeValue2 != null) {
            String[] split = attributeValue2.split("-");
            j = Long.parseLong(split[0]);
            if (split.length == 2) {
                j2 = (Long.parseLong(split[1]) - j) + 1;
            }
        }
        return buildRangedUri(attributeValue, j, j2);
    }

    protected RepresentationInfo parseRepresentation(XmlPullParser xmlPullParser, String str, String str2, String str3, int i, int i2, float f, int i3, int i4, String str4, int i5, List<Descriptor> list, SegmentBase segmentBase) {
        SegmentBase segmentBase2;
        String parseBaseUrl;
        int i6;
        String attributeValue = xmlPullParser.getAttributeValue(null, TtmlNode.ATTR_ID);
        int parseInt = parseInt(xmlPullParser, "bandwidth", -1);
        String parseString = parseString(xmlPullParser, "mimeType", str2);
        String parseString2 = parseString(xmlPullParser, "codecs", str3);
        int parseInt2 = parseInt(xmlPullParser, "width", i);
        int parseInt3 = parseInt(xmlPullParser, "height", i2);
        float parseFrameRate = parseFrameRate(xmlPullParser, f);
        int parseInt4 = parseInt(xmlPullParser, "audioSamplingRate", i4);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Object obj = null;
        int i7 = i3;
        SegmentBase segmentBase3 = segmentBase;
        String str5 = str;
        while (true) {
            Object obj2;
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "BaseURL")) {
                if (obj == null) {
                    segmentBase2 = segmentBase3;
                    parseBaseUrl = parseBaseUrl(xmlPullParser, str5);
                    obj2 = 1;
                    i6 = i7;
                }
                segmentBase2 = segmentBase3;
                parseBaseUrl = str5;
                obj2 = obj;
                i6 = i7;
            } else {
                if (XmlPullParserUtil.isStartTag(xmlPullParser, "AudioChannelConfiguration")) {
                    segmentBase2 = segmentBase3;
                    parseBaseUrl = str5;
                    obj2 = obj;
                    i6 = parseAudioChannelConfiguration(xmlPullParser);
                } else {
                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentBase")) {
                        segmentBase2 = parseSegmentBase(xmlPullParser, (SingleSegmentBase) segmentBase3);
                        parseBaseUrl = str5;
                        obj2 = obj;
                        i6 = i7;
                    } else {
                        if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentList")) {
                            segmentBase2 = parseSegmentList(xmlPullParser, (SegmentList) segmentBase3);
                            parseBaseUrl = str5;
                            obj2 = obj;
                            i6 = i7;
                        } else {
                            if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTemplate")) {
                                segmentBase2 = parseSegmentTemplate(xmlPullParser, (SegmentTemplate) segmentBase3);
                                parseBaseUrl = str5;
                                obj2 = obj;
                                i6 = i7;
                            } else {
                                if (XmlPullParserUtil.isStartTag(xmlPullParser, "ContentProtection")) {
                                    SchemeData parseContentProtection = parseContentProtection(xmlPullParser);
                                    if (parseContentProtection != null) {
                                        arrayList.add(parseContentProtection);
                                    }
                                    segmentBase2 = segmentBase3;
                                    parseBaseUrl = str5;
                                    obj2 = obj;
                                    i6 = i7;
                                } else {
                                    if (XmlPullParserUtil.isStartTag(xmlPullParser, "InbandEventStream")) {
                                        arrayList2.add(parseDescriptor(xmlPullParser, "InbandEventStream"));
                                    }
                                    segmentBase2 = segmentBase3;
                                    parseBaseUrl = str5;
                                    obj2 = obj;
                                    i6 = i7;
                                }
                            }
                        }
                    }
                }
            }
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "Representation")) {
                break;
            }
            i7 = i6;
            str5 = parseBaseUrl;
            obj = obj2;
            segmentBase3 = segmentBase2;
        }
        return new RepresentationInfo(buildFormat(attributeValue, parseString, parseInt2, parseInt3, parseFrameRate, i6, parseInt4, parseInt, str4, i5, list, parseString2), parseBaseUrl, segmentBase2 != null ? segmentBase2 : new SingleSegmentBase(), arrayList, arrayList2);
    }

    protected int parseRole(XmlPullParser xmlPullParser) {
        String parseString = parseString(xmlPullParser, "schemeIdUri", null);
        String parseString2 = parseString(xmlPullParser, C1797b.VALUE, null);
        do {
            xmlPullParser.next();
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "Role"));
        return ("urn:mpeg:dash:role:2011".equals(parseString) && "main".equals(parseString2)) ? 1 : 0;
    }

    protected SingleSegmentBase parseSegmentBase(XmlPullParser xmlPullParser, SingleSegmentBase singleSegmentBase) {
        long j;
        long j2 = 0;
        long parseLong = parseLong(xmlPullParser, "timescale", singleSegmentBase != null ? singleSegmentBase.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", singleSegmentBase != null ? singleSegmentBase.presentationTimeOffset : 0);
        long j3 = singleSegmentBase != null ? singleSegmentBase.indexStart : 0;
        if (singleSegmentBase != null) {
            j2 = singleSegmentBase.indexLength;
        }
        String attributeValue = xmlPullParser.getAttributeValue(null, "indexRange");
        if (attributeValue != null) {
            String[] split = attributeValue.split("-");
            j3 = Long.parseLong(split[0]);
            j2 = (Long.parseLong(split[1]) - j3) + 1;
            j = j3;
        } else {
            j = j3;
        }
        RangedUri rangedUri = singleSegmentBase != null ? singleSegmentBase.initialization : null;
        while (true) {
            xmlPullParser.next();
            RangedUri parseInitialization = XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization") ? parseInitialization(xmlPullParser) : rangedUri;
            if (XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentBase")) {
                return buildSingleSegmentBase(parseInitialization, parseLong, parseLong2, j, j2);
            }
            rangedUri = parseInitialization;
        }
    }

    protected SegmentList parseSegmentList(XmlPullParser xmlPullParser, SegmentList segmentList) {
        List list;
        RangedUri rangedUri;
        List list2;
        RangedUri rangedUri2 = null;
        long parseLong = parseLong(xmlPullParser, "timescale", segmentList != null ? segmentList.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", segmentList != null ? segmentList.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser, "duration", segmentList != null ? segmentList.duration : C3446C.TIME_UNSET);
        int parseInt = parseInt(xmlPullParser, "startNumber", segmentList != null ? segmentList.startNumber : 1);
        List list3 = null;
        List list4 = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUri2 = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                list4 = parseSegmentTimeline(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentURL")) {
                if (list3 == null) {
                    list3 = new ArrayList();
                }
                list3.add(parseSegmentUrl(xmlPullParser));
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentList"));
        if (segmentList != null) {
            RangedUri rangedUri3 = rangedUri2 != null ? rangedUri2 : segmentList.initialization;
            list = list4 != null ? list4 : segmentList.segmentTimeline;
            if (list3 == null) {
                list3 = segmentList.mediaSegments;
            }
            rangedUri = rangedUri3;
            list2 = list3;
        } else {
            list2 = list3;
            List list5 = list4;
            rangedUri = rangedUri2;
            list = list5;
        }
        return buildSegmentList(rangedUri, parseLong, parseLong2, parseInt, parseLong3, list, list2);
    }

    protected SegmentTemplate parseSegmentTemplate(XmlPullParser xmlPullParser, SegmentTemplate segmentTemplate) {
        List list;
        long parseLong = parseLong(xmlPullParser, "timescale", segmentTemplate != null ? segmentTemplate.timescale : 1);
        long parseLong2 = parseLong(xmlPullParser, "presentationTimeOffset", segmentTemplate != null ? segmentTemplate.presentationTimeOffset : 0);
        long parseLong3 = parseLong(xmlPullParser, "duration", segmentTemplate != null ? segmentTemplate.duration : C3446C.TIME_UNSET);
        int parseInt = parseInt(xmlPullParser, "startNumber", segmentTemplate != null ? segmentTemplate.startNumber : 1);
        UrlTemplate parseUrlTemplate = parseUrlTemplate(xmlPullParser, "media", segmentTemplate != null ? segmentTemplate.mediaTemplate : null);
        UrlTemplate parseUrlTemplate2 = parseUrlTemplate(xmlPullParser, "initialization", segmentTemplate != null ? segmentTemplate.initializationTemplate : null);
        List list2 = null;
        RangedUri rangedUri = null;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "Initialization")) {
                rangedUri = parseInitialization(xmlPullParser);
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "SegmentTimeline")) {
                list2 = parseSegmentTimeline(xmlPullParser);
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTemplate"));
        if (segmentTemplate != null) {
            if (rangedUri == null) {
                rangedUri = segmentTemplate.initialization;
            }
            if (list2 == null) {
                list2 = segmentTemplate.segmentTimeline;
            }
            list = list2;
        } else {
            list = list2;
        }
        return buildSegmentTemplate(rangedUri, parseLong, parseLong2, parseInt, parseLong3, list, parseUrlTemplate2, parseUrlTemplate);
    }

    protected List<SegmentTimelineElement> parseSegmentTimeline(XmlPullParser xmlPullParser) {
        List<SegmentTimelineElement> arrayList = new ArrayList();
        long j = 0;
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "S")) {
                j = parseLong(xmlPullParser, "t", j);
                long parseLong = parseLong(xmlPullParser, "d", C3446C.TIME_UNSET);
                int parseInt = parseInt(xmlPullParser, "r", 0) + 1;
                int i = 0;
                while (i < parseInt) {
                    arrayList.add(buildSegmentTimelineElement(j, parseLong));
                    i++;
                    j += parseLong;
                }
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, "SegmentTimeline"));
        return arrayList;
    }

    protected RangedUri parseSegmentUrl(XmlPullParser xmlPullParser) {
        return parseRangedUrl(xmlPullParser, "media", "mediaRange");
    }

    protected UrlTemplate parseUrlTemplate(XmlPullParser xmlPullParser, String str, UrlTemplate urlTemplate) {
        String attributeValue = xmlPullParser.getAttributeValue(null, str);
        return attributeValue != null ? UrlTemplate.compile(attributeValue) : urlTemplate;
    }

    protected UtcTimingElement parseUtcTiming(XmlPullParser xmlPullParser) {
        return buildUtcTimingElement(xmlPullParser.getAttributeValue(null, "schemeIdUri"), xmlPullParser.getAttributeValue(null, C1797b.VALUE));
    }
}
