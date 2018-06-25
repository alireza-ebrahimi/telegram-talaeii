package org.telegram.messenger.exoplayer2.text.ttml;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.util.XmlPullParserUtil;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public final class TtmlDecoder extends SimpleSubtitleDecoder {
    private static final String ATTR_BEGIN = "begin";
    private static final String ATTR_DURATION = "dur";
    private static final String ATTR_END = "end";
    private static final String ATTR_REGION = "region";
    private static final String ATTR_STYLE = "style";
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    private static final FrameAndTickRate DEFAULT_FRAME_AND_TICK_RATE = new FrameAndTickRate(30.0f, 1, 1);
    private static final int DEFAULT_FRAME_RATE = 30;
    private static final Pattern FONT_SIZE = Pattern.compile("^(([0-9]*.)?[0-9]+)(px|em|%)$");
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    private static final Pattern PERCENTAGE_COORDINATES = Pattern.compile("^(\\d+\\.?\\d*?)% (\\d+\\.?\\d*?)%$");
    private static final String TAG = "TtmlDecoder";
    private static final String TTP = "http://www.w3.org/ns/ttml#parameter";
    private final XmlPullParserFactory xmlParserFactory;

    private static final class FrameAndTickRate {
        final float effectiveFrameRate;
        final int subFrameRate;
        final int tickRate;

        FrameAndTickRate(float effectiveFrameRate, int subFrameRate, int tickRate) {
            this.effectiveFrameRate = effectiveFrameRate;
            this.subFrameRate = subFrameRate;
            this.tickRate = tickRate;
        }
    }

    public TtmlDecoder() {
        super(TAG);
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
            this.xmlParserFactory.setNamespaceAware(true);
        } catch (XmlPullParserException e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    protected TtmlSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        try {
            XmlPullParser xmlParser = this.xmlParserFactory.newPullParser();
            Map<String, TtmlStyle> globalStyles = new HashMap();
            Map<String, TtmlRegion> regionMap = new HashMap();
            regionMap.put("", new TtmlRegion(null));
            xmlParser.setInput(new ByteArrayInputStream(bytes, 0, length), null);
            TtmlSubtitle ttmlSubtitle = null;
            LinkedList<TtmlNode> nodeStack = new LinkedList();
            int unsupportedNodeDepth = 0;
            FrameAndTickRate frameAndTickRate = DEFAULT_FRAME_AND_TICK_RATE;
            for (int eventType = xmlParser.getEventType(); eventType != 1; eventType = xmlParser.getEventType()) {
                TtmlNode parent = (TtmlNode) nodeStack.peekLast();
                if (unsupportedNodeDepth == 0) {
                    String name = xmlParser.getName();
                    if (eventType == 2) {
                        if (TtmlNode.TAG_TT.equals(name)) {
                            frameAndTickRate = parseFrameAndTickRates(xmlParser);
                        }
                        if (!isSupportedTag(name)) {
                            Log.i(TAG, "Ignoring unsupported tag: " + xmlParser.getName());
                            unsupportedNodeDepth++;
                        } else if (TtmlNode.TAG_HEAD.equals(name)) {
                            parseHeader(xmlParser, globalStyles, regionMap);
                        } else {
                            try {
                                TtmlNode node = parseNode(xmlParser, parent, regionMap, frameAndTickRate);
                                nodeStack.addLast(node);
                                if (parent != null) {
                                    parent.addChild(node);
                                }
                            } catch (SubtitleDecoderException e) {
                                Log.w(TAG, "Suppressing parser error", e);
                                unsupportedNodeDepth++;
                            }
                        }
                    } else if (eventType == 4) {
                        parent.addChild(TtmlNode.buildTextNode(xmlParser.getText()));
                    } else if (eventType == 3) {
                        if (xmlParser.getName().equals(TtmlNode.TAG_TT)) {
                            ttmlSubtitle = new TtmlSubtitle((TtmlNode) nodeStack.getLast(), globalStyles, regionMap);
                        }
                        nodeStack.removeLast();
                    } else {
                        continue;
                    }
                } else if (eventType == 2) {
                    unsupportedNodeDepth++;
                } else if (eventType == 3) {
                    unsupportedNodeDepth--;
                }
                xmlParser.next();
            }
            return ttmlSubtitle;
        } catch (Throwable xppe) {
            throw new SubtitleDecoderException("Unable to decode source", xppe);
        } catch (IOException e2) {
            throw new IllegalStateException("Unexpected error when reading input.", e2);
        }
    }

    private FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlParser) throws SubtitleDecoderException {
        int frameRate = 30;
        String frameRateString = xmlParser.getAttributeValue(TTP, "frameRate");
        if (frameRateString != null) {
            frameRate = Integer.parseInt(frameRateString);
        }
        float frameRateMultiplier = 1.0f;
        String frameRateMultiplierString = xmlParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (frameRateMultiplierString != null) {
            String[] parts = frameRateMultiplierString.split(" ");
            if (parts.length != 2) {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
            frameRateMultiplier = ((float) Integer.parseInt(parts[0])) / ((float) Integer.parseInt(parts[1]));
        }
        int subFrameRate = DEFAULT_FRAME_AND_TICK_RATE.subFrameRate;
        String subFrameRateString = xmlParser.getAttributeValue(TTP, "subFrameRate");
        if (subFrameRateString != null) {
            subFrameRate = Integer.parseInt(subFrameRateString);
        }
        int tickRate = DEFAULT_FRAME_AND_TICK_RATE.tickRate;
        String tickRateString = xmlParser.getAttributeValue(TTP, "tickRate");
        if (tickRateString != null) {
            tickRate = Integer.parseInt(tickRateString);
        }
        return new FrameAndTickRate(((float) frameRate) * frameRateMultiplier, subFrameRate, tickRate);
    }

    private Map<String, TtmlStyle> parseHeader(XmlPullParser xmlParser, Map<String, TtmlStyle> globalStyles, Map<String, TtmlRegion> globalRegions) throws IOException, XmlPullParserException {
        do {
            xmlParser.next();
            if (XmlPullParserUtil.isStartTag(xmlParser, "style")) {
                String parentStyleId = XmlPullParserUtil.getAttributeValue(xmlParser, "style");
                TtmlStyle style = parseStyleAttributes(xmlParser, new TtmlStyle());
                if (parentStyleId != null) {
                    for (String id : parseStyleIds(parentStyleId)) {
                        style.chain((TtmlStyle) globalStyles.get(id));
                    }
                }
                if (style.getId() != null) {
                    globalStyles.put(style.getId(), style);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlParser, "region")) {
                TtmlRegion ttmlRegion = parseRegionAttributes(xmlParser);
                if (ttmlRegion != null) {
                    globalRegions.put(ttmlRegion.id, ttmlRegion);
                }
            }
        } while (!XmlPullParserUtil.isEndTag(xmlParser, TtmlNode.TAG_HEAD));
        return globalStyles;
    }

    private TtmlRegion parseRegionAttributes(XmlPullParser xmlParser) {
        String regionId = XmlPullParserUtil.getAttributeValue(xmlParser, "id");
        if (regionId == null) {
            return null;
        }
        String regionOrigin = XmlPullParserUtil.getAttributeValue(xmlParser, "origin");
        if (regionOrigin != null) {
            Matcher originMatcher = PERCENTAGE_COORDINATES.matcher(regionOrigin);
            if (originMatcher.matches()) {
                try {
                    float position = Float.parseFloat(originMatcher.group(1)) / 100.0f;
                    float line = Float.parseFloat(originMatcher.group(2)) / 100.0f;
                    String regionExtent = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_EXTENT);
                    if (regionExtent != null) {
                        Matcher extentMatcher = PERCENTAGE_COORDINATES.matcher(regionExtent);
                        if (extentMatcher.matches()) {
                            try {
                                float width = Float.parseFloat(extentMatcher.group(1)) / 100.0f;
                                float height = Float.parseFloat(extentMatcher.group(2)) / 100.0f;
                                int lineAnchor = 0;
                                String displayAlign = XmlPullParserUtil.getAttributeValue(xmlParser, TtmlNode.ATTR_TTS_DISPLAY_ALIGN);
                                if (displayAlign != null) {
                                    String toLowerInvariant = Util.toLowerInvariant(displayAlign);
                                    Object obj = -1;
                                    switch (toLowerInvariant.hashCode()) {
                                        case -1364013995:
                                            if (toLowerInvariant.equals(TtmlNode.CENTER)) {
                                                obj = null;
                                                break;
                                            }
                                            break;
                                        case 92734940:
                                            if (toLowerInvariant.equals("after")) {
                                                obj = 1;
                                                break;
                                            }
                                            break;
                                    }
                                    switch (obj) {
                                        case null:
                                            lineAnchor = 1;
                                            line += height / 2.0f;
                                            break;
                                        case 1:
                                            lineAnchor = 2;
                                            line += height;
                                            break;
                                    }
                                }
                                return new TtmlRegion(regionId, position, line, 0, lineAnchor, width);
                            } catch (NumberFormatException e) {
                                Log.w(TAG, "Ignoring region with malformed extent: " + regionOrigin);
                                return null;
                            }
                        }
                        Log.w(TAG, "Ignoring region with unsupported extent: " + regionOrigin);
                        return null;
                    }
                    Log.w(TAG, "Ignoring region without an extent");
                    return null;
                } catch (NumberFormatException e2) {
                    Log.w(TAG, "Ignoring region with malformed origin: " + regionOrigin);
                    return null;
                }
            }
            Log.w(TAG, "Ignoring region with unsupported origin: " + regionOrigin);
            return null;
        }
        Log.w(TAG, "Ignoring region without an origin");
        return null;
    }

    private String[] parseStyleIds(String parentStyleIds) {
        return parentStyleIds.split("\\s+");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle parseStyleAttributes(org.xmlpull.v1.XmlPullParser r13, org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle r14) {
        /*
        r12 = this;
        r9 = 3;
        r8 = 2;
        r6 = -1;
        r7 = 1;
        r5 = 0;
        r0 = r13.getAttributeCount();
        r3 = 0;
    L_0x000a:
        if (r3 >= r0) goto L_0x022b;
    L_0x000c:
        r1 = r13.getAttributeValue(r3);
        r4 = r13.getAttributeName(r3);
        r10 = r4.hashCode();
        switch(r10) {
            case -1550943582: goto L_0x0064;
            case -1224696685: goto L_0x0043;
            case -1065511464: goto L_0x006f;
            case -879295043: goto L_0x007a;
            case -734428249: goto L_0x0059;
            case 3355: goto L_0x0022;
            case 94842723: goto L_0x0038;
            case 365601008: goto L_0x004e;
            case 1287124693: goto L_0x002d;
            default: goto L_0x001b;
        };
    L_0x001b:
        r4 = r6;
    L_0x001c:
        switch(r4) {
            case 0: goto L_0x0086;
            case 1: goto L_0x009c;
            case 2: goto L_0x00c6;
            case 3: goto L_0x00f0;
            case 4: goto L_0x00fa;
            case 5: goto L_0x0120;
            case 6: goto L_0x0131;
            case 7: goto L_0x0142;
            case 8: goto L_0x01c6;
            default: goto L_0x001f;
        };
    L_0x001f:
        r3 = r3 + 1;
        goto L_0x000a;
    L_0x0022:
        r10 = "id";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x002b:
        r4 = r5;
        goto L_0x001c;
    L_0x002d:
        r10 = "backgroundColor";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0036:
        r4 = r7;
        goto L_0x001c;
    L_0x0038:
        r10 = "color";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0041:
        r4 = r8;
        goto L_0x001c;
    L_0x0043:
        r10 = "fontFamily";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x004c:
        r4 = r9;
        goto L_0x001c;
    L_0x004e:
        r10 = "fontSize";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0057:
        r4 = 4;
        goto L_0x001c;
    L_0x0059:
        r10 = "fontWeight";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0062:
        r4 = 5;
        goto L_0x001c;
    L_0x0064:
        r10 = "fontStyle";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x006d:
        r4 = 6;
        goto L_0x001c;
    L_0x006f:
        r10 = "textAlign";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0078:
        r4 = 7;
        goto L_0x001c;
    L_0x007a:
        r10 = "textDecoration";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001b;
    L_0x0083:
        r4 = 8;
        goto L_0x001c;
    L_0x0086:
        r4 = "style";
        r10 = r13.getName();
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x001f;
    L_0x0093:
        r4 = r12.createIfNull(r14);
        r14 = r4.setId(r1);
        goto L_0x001f;
    L_0x009c:
        r14 = r12.createIfNull(r14);
        r4 = org.telegram.messenger.exoplayer2.util.ColorParser.parseTtmlColor(r1);	 Catch:{ IllegalArgumentException -> 0x00a9 }
        r14.setBackgroundColor(r4);	 Catch:{ IllegalArgumentException -> 0x00a9 }
        goto L_0x001f;
    L_0x00a9:
        r2 = move-exception;
        r4 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing background value: ";
        r10 = r10.append(r11);
        r10 = r10.append(r1);
        r10 = r10.toString();
        android.util.Log.w(r4, r10);
        goto L_0x001f;
    L_0x00c6:
        r14 = r12.createIfNull(r14);
        r4 = org.telegram.messenger.exoplayer2.util.ColorParser.parseTtmlColor(r1);	 Catch:{ IllegalArgumentException -> 0x00d3 }
        r14.setFontColor(r4);	 Catch:{ IllegalArgumentException -> 0x00d3 }
        goto L_0x001f;
    L_0x00d3:
        r2 = move-exception;
        r4 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing color value: ";
        r10 = r10.append(r11);
        r10 = r10.append(r1);
        r10 = r10.toString();
        android.util.Log.w(r4, r10);
        goto L_0x001f;
    L_0x00f0:
        r4 = r12.createIfNull(r14);
        r14 = r4.setFontFamily(r1);
        goto L_0x001f;
    L_0x00fa:
        r14 = r12.createIfNull(r14);	 Catch:{ SubtitleDecoderException -> 0x0103 }
        parseFontSize(r1, r14);	 Catch:{ SubtitleDecoderException -> 0x0103 }
        goto L_0x001f;
    L_0x0103:
        r2 = move-exception;
        r4 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing fontSize value: ";
        r10 = r10.append(r11);
        r10 = r10.append(r1);
        r10 = r10.toString();
        android.util.Log.w(r4, r10);
        goto L_0x001f;
    L_0x0120:
        r4 = r12.createIfNull(r14);
        r10 = "bold";
        r10 = r10.equalsIgnoreCase(r1);
        r14 = r4.setBold(r10);
        goto L_0x001f;
    L_0x0131:
        r4 = r12.createIfNull(r14);
        r10 = "italic";
        r10 = r10.equalsIgnoreCase(r1);
        r14 = r4.setItalic(r10);
        goto L_0x001f;
    L_0x0142:
        r4 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r1);
        r10 = r4.hashCode();
        switch(r10) {
            case -1364013995: goto L_0x018b;
            case 100571: goto L_0x0180;
            case 3317767: goto L_0x015f;
            case 108511772: goto L_0x0175;
            case 109757538: goto L_0x016a;
            default: goto L_0x014d;
        };
    L_0x014d:
        r4 = r6;
    L_0x014e:
        switch(r4) {
            case 0: goto L_0x0153;
            case 1: goto L_0x0196;
            case 2: goto L_0x01a2;
            case 3: goto L_0x01ae;
            case 4: goto L_0x01ba;
            default: goto L_0x0151;
        };
    L_0x0151:
        goto L_0x001f;
    L_0x0153:
        r4 = r12.createIfNull(r14);
        r10 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r14 = r4.setTextAlign(r10);
        goto L_0x001f;
    L_0x015f:
        r10 = "left";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x014d;
    L_0x0168:
        r4 = r5;
        goto L_0x014e;
    L_0x016a:
        r10 = "start";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x014d;
    L_0x0173:
        r4 = r7;
        goto L_0x014e;
    L_0x0175:
        r10 = "right";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x014d;
    L_0x017e:
        r4 = r8;
        goto L_0x014e;
    L_0x0180:
        r10 = "end";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x014d;
    L_0x0189:
        r4 = r9;
        goto L_0x014e;
    L_0x018b:
        r10 = "center";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x014d;
    L_0x0194:
        r4 = 4;
        goto L_0x014e;
    L_0x0196:
        r4 = r12.createIfNull(r14);
        r10 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r14 = r4.setTextAlign(r10);
        goto L_0x001f;
    L_0x01a2:
        r4 = r12.createIfNull(r14);
        r10 = android.text.Layout.Alignment.ALIGN_OPPOSITE;
        r14 = r4.setTextAlign(r10);
        goto L_0x001f;
    L_0x01ae:
        r4 = r12.createIfNull(r14);
        r10 = android.text.Layout.Alignment.ALIGN_OPPOSITE;
        r14 = r4.setTextAlign(r10);
        goto L_0x001f;
    L_0x01ba:
        r4 = r12.createIfNull(r14);
        r10 = android.text.Layout.Alignment.ALIGN_CENTER;
        r14 = r4.setTextAlign(r10);
        goto L_0x001f;
    L_0x01c6:
        r4 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r1);
        r10 = r4.hashCode();
        switch(r10) {
            case -1461280213: goto L_0x0202;
            case -1026963764: goto L_0x01f7;
            case 913457136: goto L_0x01ec;
            case 1679736913: goto L_0x01e1;
            default: goto L_0x01d1;
        };
    L_0x01d1:
        r4 = r6;
    L_0x01d2:
        switch(r4) {
            case 0: goto L_0x01d7;
            case 1: goto L_0x020d;
            case 2: goto L_0x0217;
            case 3: goto L_0x0221;
            default: goto L_0x01d5;
        };
    L_0x01d5:
        goto L_0x001f;
    L_0x01d7:
        r4 = r12.createIfNull(r14);
        r14 = r4.setLinethrough(r7);
        goto L_0x001f;
    L_0x01e1:
        r10 = "linethrough";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x01d1;
    L_0x01ea:
        r4 = r5;
        goto L_0x01d2;
    L_0x01ec:
        r10 = "nolinethrough";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x01d1;
    L_0x01f5:
        r4 = r7;
        goto L_0x01d2;
    L_0x01f7:
        r10 = "underline";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x01d1;
    L_0x0200:
        r4 = r8;
        goto L_0x01d2;
    L_0x0202:
        r10 = "nounderline";
        r4 = r4.equals(r10);
        if (r4 == 0) goto L_0x01d1;
    L_0x020b:
        r4 = r9;
        goto L_0x01d2;
    L_0x020d:
        r4 = r12.createIfNull(r14);
        r14 = r4.setLinethrough(r5);
        goto L_0x001f;
    L_0x0217:
        r4 = r12.createIfNull(r14);
        r14 = r4.setUnderline(r7);
        goto L_0x001f;
    L_0x0221:
        r4 = r12.createIfNull(r14);
        r14 = r4.setUnderline(r5);
        goto L_0x001f;
    L_0x022b:
        return r14;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.ttml.TtmlDecoder.parseStyleAttributes(org.xmlpull.v1.XmlPullParser, org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle):org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle");
    }

    private TtmlStyle createIfNull(TtmlStyle style) {
        return style == null ? new TtmlStyle() : style;
    }

    private TtmlNode parseNode(XmlPullParser parser, TtmlNode parent, Map<String, TtmlRegion> regionMap, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        long duration = C0907C.TIME_UNSET;
        long startTime = C0907C.TIME_UNSET;
        long endTime = C0907C.TIME_UNSET;
        String regionId = "";
        String[] styleIds = null;
        int attributeCount = parser.getAttributeCount();
        TtmlStyle style = parseStyleAttributes(parser, null);
        for (int i = 0; i < attributeCount; i++) {
            String attr = parser.getAttributeName(i);
            String value = parser.getAttributeValue(i);
            Object obj = -1;
            switch (attr.hashCode()) {
                case -934795532:
                    if (attr.equals("region")) {
                        obj = 4;
                        break;
                    }
                    break;
                case 99841:
                    if (attr.equals(ATTR_DURATION)) {
                        obj = 2;
                        break;
                    }
                    break;
                case 100571:
                    if (attr.equals("end")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 93616297:
                    if (attr.equals(ATTR_BEGIN)) {
                        obj = null;
                        break;
                    }
                    break;
                case 109780401:
                    if (attr.equals("style")) {
                        obj = 3;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    startTime = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 1:
                    endTime = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 2:
                    duration = parseTimeExpression(value, frameAndTickRate);
                    break;
                case 3:
                    String[] ids = parseStyleIds(value);
                    if (ids.length <= 0) {
                        break;
                    }
                    styleIds = ids;
                    break;
                case 4:
                    if (!regionMap.containsKey(value)) {
                        break;
                    }
                    regionId = value;
                    break;
                default:
                    break;
            }
        }
        if (!(parent == null || parent.startTimeUs == C0907C.TIME_UNSET)) {
            if (startTime != C0907C.TIME_UNSET) {
                startTime += parent.startTimeUs;
            }
            if (endTime != C0907C.TIME_UNSET) {
                endTime += parent.startTimeUs;
            }
        }
        if (endTime == C0907C.TIME_UNSET) {
            if (duration != C0907C.TIME_UNSET) {
                endTime = startTime + duration;
            } else if (!(parent == null || parent.endTimeUs == C0907C.TIME_UNSET)) {
                endTime = parent.endTimeUs;
            }
        }
        return TtmlNode.buildNode(parser.getName(), startTime, endTime, style, styleIds, regionId);
    }

    private static boolean isSupportedTag(String tag) {
        return tag.equals(TtmlNode.TAG_TT) || tag.equals(TtmlNode.TAG_HEAD) || tag.equals(TtmlNode.TAG_BODY) || tag.equals(TtmlNode.TAG_DIV) || tag.equals(TtmlNode.TAG_P) || tag.equals(TtmlNode.TAG_SPAN) || tag.equals(TtmlNode.TAG_BR) || tag.equals("style") || tag.equals(TtmlNode.TAG_STYLING) || tag.equals(TtmlNode.TAG_LAYOUT) || tag.equals("region") || tag.equals(TtmlNode.TAG_METADATA) || tag.equals(TtmlNode.TAG_SMPTE_IMAGE) || tag.equals(TtmlNode.TAG_SMPTE_DATA) || tag.equals(TtmlNode.TAG_SMPTE_INFORMATION);
    }

    private static void parseFontSize(String expression, TtmlStyle out) throws SubtitleDecoderException {
        Matcher matcher;
        String[] expressions = expression.split("\\s+");
        if (expressions.length == 1) {
            matcher = FONT_SIZE.matcher(expression);
        } else if (expressions.length == 2) {
            matcher = FONT_SIZE.matcher(expressions[1]);
            Log.w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            throw new SubtitleDecoderException("Invalid number of entries for fontSize: " + expressions.length + ".");
        }
        if (matcher.matches()) {
            String unit = matcher.group(3);
            int i = -1;
            switch (unit.hashCode()) {
                case 37:
                    if (unit.equals("%")) {
                        i = 2;
                        break;
                    }
                    break;
                case 3240:
                    if (unit.equals("em")) {
                        i = 1;
                        break;
                    }
                    break;
                case 3592:
                    if (unit.equals("px")) {
                        i = 0;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    out.setFontSizeUnit(1);
                    break;
                case 1:
                    out.setFontSizeUnit(2);
                    break;
                case 2:
                    out.setFontSizeUnit(3);
                    break;
                default:
                    throw new SubtitleDecoderException("Invalid unit for fontSize: '" + unit + "'.");
            }
            out.setFontSize(Float.valueOf(matcher.group(1)).floatValue());
            return;
        }
        throw new SubtitleDecoderException("Invalid expression for fontSize: '" + expression + "'.");
    }

    private static long parseTimeExpression(String time, FrameAndTickRate frameAndTickRate) throws SubtitleDecoderException {
        Matcher matcher = CLOCK_TIME.matcher(time);
        if (matcher.matches()) {
            double durationSeconds = (((double) (Long.parseLong(matcher.group(1)) * 3600)) + ((double) (Long.parseLong(matcher.group(2)) * 60))) + ((double) Long.parseLong(matcher.group(3)));
            String fraction = matcher.group(4);
            durationSeconds += fraction != null ? Double.parseDouble(fraction) : 0.0d;
            String frames = matcher.group(5);
            durationSeconds += frames != null ? (double) (((float) Long.parseLong(frames)) / frameAndTickRate.effectiveFrameRate) : 0.0d;
            String subframes = matcher.group(6);
            return (long) (1000000.0d * (durationSeconds + (subframes != null ? (((double) Long.parseLong(subframes)) / ((double) frameAndTickRate.subFrameRate)) / ((double) frameAndTickRate.effectiveFrameRate) : 0.0d)));
        }
        matcher = OFFSET_TIME.matcher(time);
        if (matcher.matches()) {
            double offsetSeconds = Double.parseDouble(matcher.group(1));
            String unit = matcher.group(2);
            Object obj = -1;
            switch (unit.hashCode()) {
                case 102:
                    if (unit.equals("f")) {
                        obj = 4;
                        break;
                    }
                    break;
                case 104:
                    if (unit.equals("h")) {
                        obj = null;
                        break;
                    }
                    break;
                case 109:
                    if (unit.equals("m")) {
                        obj = 1;
                        break;
                    }
                    break;
                case 115:
                    if (unit.equals("s")) {
                        obj = 2;
                        break;
                    }
                    break;
                case 116:
                    if (unit.equals("t")) {
                        obj = 5;
                        break;
                    }
                    break;
                case 3494:
                    if (unit.equals("ms")) {
                        obj = 3;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    offsetSeconds *= 3600.0d;
                    break;
                case 1:
                    offsetSeconds *= 60.0d;
                    break;
                case 3:
                    offsetSeconds /= 1000.0d;
                    break;
                case 4:
                    offsetSeconds /= (double) frameAndTickRate.effectiveFrameRate;
                    break;
                case 5:
                    offsetSeconds /= (double) frameAndTickRate.tickRate;
                    break;
            }
            return (long) (1000000.0d * offsetSeconds);
        }
        throw new SubtitleDecoderException("Malformed time expression: " + time);
    }
}
