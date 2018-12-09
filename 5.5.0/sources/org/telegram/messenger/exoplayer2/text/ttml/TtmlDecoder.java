package org.telegram.messenger.exoplayer2.text.ttml;

import android.util.Log;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.util.Util;
import org.telegram.messenger.exoplayer2.util.XmlPullParserUtil;
import org.xmlpull.v1.XmlPullParser;
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

        FrameAndTickRate(float f, int i, int i2) {
            this.effectiveFrameRate = f;
            this.subFrameRate = i;
            this.tickRate = i2;
        }
    }

    public TtmlDecoder() {
        super(TAG);
        try {
            this.xmlParserFactory = XmlPullParserFactory.newInstance();
            this.xmlParserFactory.setNamespaceAware(true);
        } catch (Throwable e) {
            throw new RuntimeException("Couldn't create XmlPullParserFactory instance", e);
        }
    }

    private TtmlStyle createIfNull(TtmlStyle ttmlStyle) {
        return ttmlStyle == null ? new TtmlStyle() : ttmlStyle;
    }

    private static boolean isSupportedTag(String str) {
        return str.equals(TtmlNode.TAG_TT) || str.equals(TtmlNode.TAG_HEAD) || str.equals(TtmlNode.TAG_BODY) || str.equals(TtmlNode.TAG_DIV) || str.equals(TtmlNode.TAG_P) || str.equals(TtmlNode.TAG_SPAN) || str.equals(TtmlNode.TAG_BR) || str.equals("style") || str.equals(TtmlNode.TAG_STYLING) || str.equals(TtmlNode.TAG_LAYOUT) || str.equals("region") || str.equals(TtmlNode.TAG_METADATA) || str.equals(TtmlNode.TAG_SMPTE_IMAGE) || str.equals(TtmlNode.TAG_SMPTE_DATA) || str.equals(TtmlNode.TAG_SMPTE_INFORMATION);
    }

    private static void parseFontSize(String str, TtmlStyle ttmlStyle) {
        Matcher matcher;
        String[] split = str.split("\\s+");
        if (split.length == 1) {
            matcher = FONT_SIZE.matcher(str);
        } else if (split.length == 2) {
            matcher = FONT_SIZE.matcher(split[1]);
            Log.w(TAG, "Multiple values in fontSize attribute. Picking the second value for vertical font size and ignoring the first.");
        } else {
            throw new SubtitleDecoderException("Invalid number of entries for fontSize: " + split.length + ".");
        }
        if (matcher.matches()) {
            String group = matcher.group(3);
            int i = -1;
            switch (group.hashCode()) {
                case 37:
                    if (group.equals("%")) {
                        i = 2;
                        break;
                    }
                    break;
                case 3240:
                    if (group.equals("em")) {
                        i = 1;
                        break;
                    }
                    break;
                case 3592:
                    if (group.equals("px")) {
                        i = 0;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    ttmlStyle.setFontSizeUnit(1);
                    break;
                case 1:
                    ttmlStyle.setFontSizeUnit(2);
                    break;
                case 2:
                    ttmlStyle.setFontSizeUnit(3);
                    break;
                default:
                    throw new SubtitleDecoderException("Invalid unit for fontSize: '" + group + "'.");
            }
            ttmlStyle.setFontSize(Float.valueOf(matcher.group(1)).floatValue());
            return;
        }
        throw new SubtitleDecoderException("Invalid expression for fontSize: '" + str + "'.");
    }

    private FrameAndTickRate parseFrameAndTickRates(XmlPullParser xmlPullParser) {
        int i = 30;
        String attributeValue = xmlPullParser.getAttributeValue(TTP, "frameRate");
        if (attributeValue != null) {
            i = Integer.parseInt(attributeValue);
        }
        float f = 1.0f;
        String attributeValue2 = xmlPullParser.getAttributeValue(TTP, "frameRateMultiplier");
        if (attributeValue2 != null) {
            String[] split = attributeValue2.split(" ");
            if (split.length != 2) {
                throw new SubtitleDecoderException("frameRateMultiplier doesn't have 2 parts");
            }
            f = ((float) Integer.parseInt(split[0])) / ((float) Integer.parseInt(split[1]));
        }
        int i2 = DEFAULT_FRAME_AND_TICK_RATE.subFrameRate;
        String attributeValue3 = xmlPullParser.getAttributeValue(TTP, "subFrameRate");
        if (attributeValue3 != null) {
            i2 = Integer.parseInt(attributeValue3);
        }
        int i3 = DEFAULT_FRAME_AND_TICK_RATE.tickRate;
        String attributeValue4 = xmlPullParser.getAttributeValue(TTP, "tickRate");
        if (attributeValue4 != null) {
            i3 = Integer.parseInt(attributeValue4);
        }
        return new FrameAndTickRate(((float) i) * f, i2, i3);
    }

    private Map<String, TtmlStyle> parseHeader(XmlPullParser xmlPullParser, Map<String, TtmlStyle> map, Map<String, TtmlRegion> map2) {
        do {
            xmlPullParser.next();
            if (XmlPullParserUtil.isStartTag(xmlPullParser, "style")) {
                String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, "style");
                TtmlStyle parseStyleAttributes = parseStyleAttributes(xmlPullParser, new TtmlStyle());
                if (attributeValue != null) {
                    for (Object obj : parseStyleIds(attributeValue)) {
                        parseStyleAttributes.chain((TtmlStyle) map.get(obj));
                    }
                }
                if (parseStyleAttributes.getId() != null) {
                    map.put(parseStyleAttributes.getId(), parseStyleAttributes);
                }
            } else if (XmlPullParserUtil.isStartTag(xmlPullParser, "region")) {
                TtmlRegion parseRegionAttributes = parseRegionAttributes(xmlPullParser);
                if (parseRegionAttributes != null) {
                    map2.put(parseRegionAttributes.id, parseRegionAttributes);
                }
            }
        } while (!XmlPullParserUtil.isEndTag(xmlPullParser, TtmlNode.TAG_HEAD));
        return map;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.telegram.messenger.exoplayer2.text.ttml.TtmlNode parseNode(org.xmlpull.v1.XmlPullParser r20, org.telegram.messenger.exoplayer2.text.ttml.TtmlNode r21, java.util.Map<java.lang.String, org.telegram.messenger.exoplayer2.text.ttml.TtmlRegion> r22, org.telegram.messenger.exoplayer2.text.ttml.TtmlDecoder.FrameAndTickRate r23) {
        /*
        r19 = this;
        r12 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r6 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r4 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r10 = "";
        r9 = 0;
        r14 = r20.getAttributeCount();
        r2 = 0;
        r0 = r19;
        r1 = r20;
        r8 = r0.parseStyleAttributes(r1, r2);
        r2 = 0;
        r11 = r2;
    L_0x0022:
        if (r11 >= r14) goto L_0x00b9;
    L_0x0024:
        r0 = r20;
        r15 = r0.getAttributeName(r11);
        r0 = r20;
        r2 = r0.getAttributeValue(r11);
        r3 = -1;
        r16 = r15.hashCode();
        switch(r16) {
            case -934795532: goto L_0x0070;
            case 99841: goto L_0x005a;
            case 100571: goto L_0x004f;
            case 93616297: goto L_0x0044;
            case 109780401: goto L_0x0065;
            default: goto L_0x0038;
        };
    L_0x0038:
        switch(r3) {
            case 0: goto L_0x007b;
            case 1: goto L_0x0088;
            case 2: goto L_0x0091;
            case 3: goto L_0x009e;
            case 4: goto L_0x00ac;
            default: goto L_0x003b;
        };
    L_0x003b:
        r2 = r4;
        r4 = r6;
        r6 = r12;
    L_0x003e:
        r11 = r11 + 1;
        r12 = r6;
        r6 = r4;
        r4 = r2;
        goto L_0x0022;
    L_0x0044:
        r16 = "begin";
        r15 = r15.equals(r16);
        if (r15 == 0) goto L_0x0038;
    L_0x004d:
        r3 = 0;
        goto L_0x0038;
    L_0x004f:
        r16 = "end";
        r15 = r15.equals(r16);
        if (r15 == 0) goto L_0x0038;
    L_0x0058:
        r3 = 1;
        goto L_0x0038;
    L_0x005a:
        r16 = "dur";
        r15 = r15.equals(r16);
        if (r15 == 0) goto L_0x0038;
    L_0x0063:
        r3 = 2;
        goto L_0x0038;
    L_0x0065:
        r16 = "style";
        r15 = r15.equals(r16);
        if (r15 == 0) goto L_0x0038;
    L_0x006e:
        r3 = 3;
        goto L_0x0038;
    L_0x0070:
        r16 = "region";
        r15 = r15.equals(r16);
        if (r15 == 0) goto L_0x0038;
    L_0x0079:
        r3 = 4;
        goto L_0x0038;
    L_0x007b:
        r0 = r23;
        r2 = parseTimeExpression(r2, r0);
        r6 = r12;
        r17 = r2;
        r2 = r4;
        r4 = r17;
        goto L_0x003e;
    L_0x0088:
        r0 = r23;
        r2 = parseTimeExpression(r2, r0);
        r4 = r6;
        r6 = r12;
        goto L_0x003e;
    L_0x0091:
        r0 = r23;
        r2 = parseTimeExpression(r2, r0);
        r17 = r4;
        r4 = r6;
        r6 = r2;
        r2 = r17;
        goto L_0x003e;
    L_0x009e:
        r0 = r19;
        r2 = r0.parseStyleIds(r2);
        r3 = r2.length;
        if (r3 <= 0) goto L_0x003b;
    L_0x00a7:
        r9 = r2;
        r2 = r4;
        r4 = r6;
        r6 = r12;
        goto L_0x003e;
    L_0x00ac:
        r0 = r22;
        r3 = r0.containsKey(r2);
        if (r3 == 0) goto L_0x003b;
    L_0x00b4:
        r10 = r2;
        r2 = r4;
        r4 = r6;
        r6 = r12;
        goto L_0x003e;
    L_0x00b9:
        if (r21 == 0) goto L_0x011a;
    L_0x00bb:
        r0 = r21;
        r2 = r0.startTimeUs;
        r14 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r2 > r14 ? 1 : (r2 == r14 ? 0 : -1));
        if (r2 == 0) goto L_0x011a;
    L_0x00c8:
        r2 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x00d6;
    L_0x00d1:
        r0 = r21;
        r2 = r0.startTimeUs;
        r6 = r6 + r2;
    L_0x00d6:
        r2 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x011a;
    L_0x00df:
        r0 = r21;
        r2 = r0.startTimeUs;
        r4 = r4 + r2;
        r17 = r4;
        r4 = r6;
        r6 = r17;
    L_0x00e9:
        r2 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x00fd;
    L_0x00f2:
        r2 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r12 > r2 ? 1 : (r12 == r2 ? 0 : -1));
        if (r2 == 0) goto L_0x0106;
    L_0x00fb:
        r6 = r4 + r12;
    L_0x00fd:
        r3 = r20.getName();
        r2 = org.telegram.messenger.exoplayer2.text.ttml.TtmlNode.buildNode(r3, r4, r6, r8, r9, r10);
        return r2;
    L_0x0106:
        if (r21 == 0) goto L_0x00fd;
    L_0x0108:
        r0 = r21;
        r2 = r0.endTimeUs;
        r12 = -9223372036854775807; // 0x8000000000000001 float:1.4E-45 double:-4.9E-324;
        r2 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r2 == 0) goto L_0x00fd;
    L_0x0115:
        r0 = r21;
        r6 = r0.endTimeUs;
        goto L_0x00fd;
    L_0x011a:
        r17 = r4;
        r4 = r6;
        r6 = r17;
        goto L_0x00e9;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.ttml.TtmlDecoder.parseNode(org.xmlpull.v1.XmlPullParser, org.telegram.messenger.exoplayer2.text.ttml.TtmlNode, java.util.Map, org.telegram.messenger.exoplayer2.text.ttml.TtmlDecoder$FrameAndTickRate):org.telegram.messenger.exoplayer2.text.ttml.TtmlNode");
    }

    private TtmlRegion parseRegionAttributes(XmlPullParser xmlPullParser) {
        int i = 1;
        TtmlRegion ttmlRegion = null;
        String attributeValue = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_ID);
        if (attributeValue == null) {
            return ttmlRegion;
        }
        String attributeValue2 = XmlPullParserUtil.getAttributeValue(xmlPullParser, "origin");
        if (attributeValue2 != null) {
            Matcher matcher = PERCENTAGE_COORDINATES.matcher(attributeValue2);
            if (matcher.matches()) {
                try {
                    float parseFloat = Float.parseFloat(matcher.group(1)) / 100.0f;
                    float parseFloat2 = Float.parseFloat(matcher.group(2)) / 100.0f;
                    CharSequence attributeValue3 = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_TTS_EXTENT);
                    if (attributeValue3 != null) {
                        Matcher matcher2 = PERCENTAGE_COORDINATES.matcher(attributeValue3);
                        if (matcher2.matches()) {
                            try {
                                float parseFloat3 = Float.parseFloat(matcher2.group(1)) / 100.0f;
                                float parseFloat4 = Float.parseFloat(matcher2.group(2)) / 100.0f;
                                String attributeValue4 = XmlPullParserUtil.getAttributeValue(xmlPullParser, TtmlNode.ATTR_TTS_DISPLAY_ALIGN);
                                if (attributeValue4 != null) {
                                    String toLowerInvariant = Util.toLowerInvariant(attributeValue4);
                                    int i2 = -1;
                                    switch (toLowerInvariant.hashCode()) {
                                        case -1364013995:
                                            if (toLowerInvariant.equals(TtmlNode.CENTER)) {
                                                i2 = 0;
                                                break;
                                            }
                                            break;
                                        case 92734940:
                                            if (toLowerInvariant.equals("after")) {
                                                i2 = 1;
                                                break;
                                            }
                                            break;
                                    }
                                    switch (i2) {
                                        case 0:
                                            parseFloat2 += parseFloat4 / 2.0f;
                                            break;
                                        case 1:
                                            parseFloat2 += parseFloat4;
                                            i = 2;
                                            break;
                                    }
                                }
                                i = 0;
                                return new TtmlRegion(attributeValue, parseFloat, parseFloat2, 0, i, parseFloat3);
                            } catch (NumberFormatException e) {
                                Log.w(TAG, "Ignoring region with malformed extent: " + attributeValue2);
                                return ttmlRegion;
                            }
                        }
                        Log.w(TAG, "Ignoring region with unsupported extent: " + attributeValue2);
                        return ttmlRegion;
                    }
                    Log.w(TAG, "Ignoring region without an extent");
                    return ttmlRegion;
                } catch (NumberFormatException e2) {
                    Log.w(TAG, "Ignoring region with malformed origin: " + attributeValue2);
                    return ttmlRegion;
                }
            }
            Log.w(TAG, "Ignoring region with unsupported origin: " + attributeValue2);
            return ttmlRegion;
        }
        Log.w(TAG, "Ignoring region without an origin");
        return ttmlRegion;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle parseStyleAttributes(org.xmlpull.v1.XmlPullParser r13, org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle r14) {
        /*
        r12 = this;
        r6 = 3;
        r5 = 2;
        r3 = -1;
        r4 = 1;
        r2 = 0;
        r8 = r13.getAttributeCount();
        r7 = r2;
        r0 = r14;
    L_0x000b:
        if (r7 >= r8) goto L_0x022d;
    L_0x000d:
        r9 = r13.getAttributeValue(r7);
        r1 = r13.getAttributeName(r7);
        r10 = r1.hashCode();
        switch(r10) {
            case -1550943582: goto L_0x0066;
            case -1224696685: goto L_0x0045;
            case -1065511464: goto L_0x0071;
            case -879295043: goto L_0x007c;
            case -734428249: goto L_0x005b;
            case 3355: goto L_0x0024;
            case 94842723: goto L_0x003a;
            case 365601008: goto L_0x0050;
            case 1287124693: goto L_0x002f;
            default: goto L_0x001c;
        };
    L_0x001c:
        r1 = r3;
    L_0x001d:
        switch(r1) {
            case 0: goto L_0x0088;
            case 1: goto L_0x009e;
            case 2: goto L_0x00c8;
            case 3: goto L_0x00f2;
            case 4: goto L_0x00fc;
            case 5: goto L_0x0122;
            case 6: goto L_0x0133;
            case 7: goto L_0x0144;
            case 8: goto L_0x01c8;
            default: goto L_0x0020;
        };
    L_0x0020:
        r1 = r7 + 1;
        r7 = r1;
        goto L_0x000b;
    L_0x0024:
        r10 = "id";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x002d:
        r1 = r2;
        goto L_0x001d;
    L_0x002f:
        r10 = "backgroundColor";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x0038:
        r1 = r4;
        goto L_0x001d;
    L_0x003a:
        r10 = "color";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x0043:
        r1 = r5;
        goto L_0x001d;
    L_0x0045:
        r10 = "fontFamily";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x004e:
        r1 = r6;
        goto L_0x001d;
    L_0x0050:
        r10 = "fontSize";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x0059:
        r1 = 4;
        goto L_0x001d;
    L_0x005b:
        r10 = "fontWeight";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x0064:
        r1 = 5;
        goto L_0x001d;
    L_0x0066:
        r10 = "fontStyle";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x006f:
        r1 = 6;
        goto L_0x001d;
    L_0x0071:
        r10 = "textAlign";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x007a:
        r1 = 7;
        goto L_0x001d;
    L_0x007c:
        r10 = "textDecoration";
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x001c;
    L_0x0085:
        r1 = 8;
        goto L_0x001d;
    L_0x0088:
        r1 = "style";
        r10 = r13.getName();
        r1 = r1.equals(r10);
        if (r1 == 0) goto L_0x0020;
    L_0x0095:
        r0 = r12.createIfNull(r0);
        r0 = r0.setId(r9);
        goto L_0x0020;
    L_0x009e:
        r0 = r12.createIfNull(r0);
        r1 = org.telegram.messenger.exoplayer2.util.ColorParser.parseTtmlColor(r9);	 Catch:{ IllegalArgumentException -> 0x00ab }
        r0.setBackgroundColor(r1);	 Catch:{ IllegalArgumentException -> 0x00ab }
        goto L_0x0020;
    L_0x00ab:
        r1 = move-exception;
        r1 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing background value: ";
        r10 = r10.append(r11);
        r9 = r10.append(r9);
        r9 = r9.toString();
        android.util.Log.w(r1, r9);
        goto L_0x0020;
    L_0x00c8:
        r0 = r12.createIfNull(r0);
        r1 = org.telegram.messenger.exoplayer2.util.ColorParser.parseTtmlColor(r9);	 Catch:{ IllegalArgumentException -> 0x00d5 }
        r0.setFontColor(r1);	 Catch:{ IllegalArgumentException -> 0x00d5 }
        goto L_0x0020;
    L_0x00d5:
        r1 = move-exception;
        r1 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing color value: ";
        r10 = r10.append(r11);
        r9 = r10.append(r9);
        r9 = r9.toString();
        android.util.Log.w(r1, r9);
        goto L_0x0020;
    L_0x00f2:
        r0 = r12.createIfNull(r0);
        r0 = r0.setFontFamily(r9);
        goto L_0x0020;
    L_0x00fc:
        r0 = r12.createIfNull(r0);	 Catch:{ SubtitleDecoderException -> 0x0105 }
        parseFontSize(r9, r0);	 Catch:{ SubtitleDecoderException -> 0x0105 }
        goto L_0x0020;
    L_0x0105:
        r1 = move-exception;
        r1 = "TtmlDecoder";
        r10 = new java.lang.StringBuilder;
        r10.<init>();
        r11 = "Failed parsing fontSize value: ";
        r10 = r10.append(r11);
        r9 = r10.append(r9);
        r9 = r9.toString();
        android.util.Log.w(r1, r9);
        goto L_0x0020;
    L_0x0122:
        r0 = r12.createIfNull(r0);
        r1 = "bold";
        r1 = r1.equalsIgnoreCase(r9);
        r0 = r0.setBold(r1);
        goto L_0x0020;
    L_0x0133:
        r0 = r12.createIfNull(r0);
        r1 = "italic";
        r1 = r1.equalsIgnoreCase(r9);
        r0 = r0.setItalic(r1);
        goto L_0x0020;
    L_0x0144:
        r1 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r9);
        r9 = r1.hashCode();
        switch(r9) {
            case -1364013995: goto L_0x018d;
            case 100571: goto L_0x0182;
            case 3317767: goto L_0x0161;
            case 108511772: goto L_0x0177;
            case 109757538: goto L_0x016c;
            default: goto L_0x014f;
        };
    L_0x014f:
        r1 = r3;
    L_0x0150:
        switch(r1) {
            case 0: goto L_0x0155;
            case 1: goto L_0x0198;
            case 2: goto L_0x01a4;
            case 3: goto L_0x01b0;
            case 4: goto L_0x01bc;
            default: goto L_0x0153;
        };
    L_0x0153:
        goto L_0x0020;
    L_0x0155:
        r0 = r12.createIfNull(r0);
        r1 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r0 = r0.setTextAlign(r1);
        goto L_0x0020;
    L_0x0161:
        r9 = "left";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x014f;
    L_0x016a:
        r1 = r2;
        goto L_0x0150;
    L_0x016c:
        r9 = "start";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x014f;
    L_0x0175:
        r1 = r4;
        goto L_0x0150;
    L_0x0177:
        r9 = "right";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x014f;
    L_0x0180:
        r1 = r5;
        goto L_0x0150;
    L_0x0182:
        r9 = "end";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x014f;
    L_0x018b:
        r1 = r6;
        goto L_0x0150;
    L_0x018d:
        r9 = "center";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x014f;
    L_0x0196:
        r1 = 4;
        goto L_0x0150;
    L_0x0198:
        r0 = r12.createIfNull(r0);
        r1 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r0 = r0.setTextAlign(r1);
        goto L_0x0020;
    L_0x01a4:
        r0 = r12.createIfNull(r0);
        r1 = android.text.Layout.Alignment.ALIGN_OPPOSITE;
        r0 = r0.setTextAlign(r1);
        goto L_0x0020;
    L_0x01b0:
        r0 = r12.createIfNull(r0);
        r1 = android.text.Layout.Alignment.ALIGN_OPPOSITE;
        r0 = r0.setTextAlign(r1);
        goto L_0x0020;
    L_0x01bc:
        r0 = r12.createIfNull(r0);
        r1 = android.text.Layout.Alignment.ALIGN_CENTER;
        r0 = r0.setTextAlign(r1);
        goto L_0x0020;
    L_0x01c8:
        r1 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r9);
        r9 = r1.hashCode();
        switch(r9) {
            case -1461280213: goto L_0x0204;
            case -1026963764: goto L_0x01f9;
            case 913457136: goto L_0x01ee;
            case 1679736913: goto L_0x01e3;
            default: goto L_0x01d3;
        };
    L_0x01d3:
        r1 = r3;
    L_0x01d4:
        switch(r1) {
            case 0: goto L_0x01d9;
            case 1: goto L_0x020f;
            case 2: goto L_0x0219;
            case 3: goto L_0x0223;
            default: goto L_0x01d7;
        };
    L_0x01d7:
        goto L_0x0020;
    L_0x01d9:
        r0 = r12.createIfNull(r0);
        r0 = r0.setLinethrough(r4);
        goto L_0x0020;
    L_0x01e3:
        r9 = "linethrough";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x01d3;
    L_0x01ec:
        r1 = r2;
        goto L_0x01d4;
    L_0x01ee:
        r9 = "nolinethrough";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x01d3;
    L_0x01f7:
        r1 = r4;
        goto L_0x01d4;
    L_0x01f9:
        r9 = "underline";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x01d3;
    L_0x0202:
        r1 = r5;
        goto L_0x01d4;
    L_0x0204:
        r9 = "nounderline";
        r1 = r1.equals(r9);
        if (r1 == 0) goto L_0x01d3;
    L_0x020d:
        r1 = r6;
        goto L_0x01d4;
    L_0x020f:
        r0 = r12.createIfNull(r0);
        r0 = r0.setLinethrough(r2);
        goto L_0x0020;
    L_0x0219:
        r0 = r12.createIfNull(r0);
        r0 = r0.setUnderline(r4);
        goto L_0x0020;
    L_0x0223:
        r0 = r12.createIfNull(r0);
        r0 = r0.setUnderline(r2);
        goto L_0x0020;
    L_0x022d:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.ttml.TtmlDecoder.parseStyleAttributes(org.xmlpull.v1.XmlPullParser, org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle):org.telegram.messenger.exoplayer2.text.ttml.TtmlStyle");
    }

    private String[] parseStyleIds(String str) {
        return str.split("\\s+");
    }

    private static long parseTimeExpression(String str, FrameAndTickRate frameAndTickRate) {
        double d = 0.0d;
        Matcher matcher = CLOCK_TIME.matcher(str);
        if (matcher.matches()) {
            double parseLong = ((double) Long.parseLong(matcher.group(3))) + (((double) (Long.parseLong(matcher.group(1)) * 3600)) + ((double) (Long.parseLong(matcher.group(2)) * 60)));
            String group = matcher.group(4);
            parseLong += group != null ? Double.parseDouble(group) : 0.0d;
            group = matcher.group(5);
            double parseLong2 = (group != null ? (double) (((float) Long.parseLong(group)) / frameAndTickRate.effectiveFrameRate) : 0.0d) + parseLong;
            String group2 = matcher.group(6);
            if (group2 != null) {
                d = (((double) Long.parseLong(group2)) / ((double) frameAndTickRate.subFrameRate)) / ((double) frameAndTickRate.effectiveFrameRate);
            }
            return (long) ((parseLong2 + d) * 1000000.0d);
        }
        Matcher matcher2 = OFFSET_TIME.matcher(str);
        if (matcher2.matches()) {
            parseLong2 = Double.parseDouble(matcher2.group(1));
            String group3 = matcher2.group(2);
            int i = -1;
            switch (group3.hashCode()) {
                case 102:
                    if (group3.equals("f")) {
                        i = 4;
                        break;
                    }
                    break;
                case 104:
                    if (group3.equals("h")) {
                        i = 0;
                        break;
                    }
                    break;
                case 109:
                    if (group3.equals("m")) {
                        i = 1;
                        break;
                    }
                    break;
                case 115:
                    if (group3.equals("s")) {
                        i = 2;
                        break;
                    }
                    break;
                case 116:
                    if (group3.equals("t")) {
                        i = 5;
                        break;
                    }
                    break;
                case 3494:
                    if (group3.equals("ms")) {
                        i = 3;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    parseLong2 *= 3600.0d;
                    break;
                case 1:
                    parseLong2 *= 60.0d;
                    break;
                case 3:
                    parseLong2 /= 1000.0d;
                    break;
                case 4:
                    parseLong2 /= (double) frameAndTickRate.effectiveFrameRate;
                    break;
                case 5:
                    parseLong2 /= (double) frameAndTickRate.tickRate;
                    break;
            }
            return (long) (parseLong2 * 1000000.0d);
        }
        throw new SubtitleDecoderException("Malformed time expression: " + str);
    }

    protected TtmlSubtitle decode(byte[] bArr, int i, boolean z) {
        try {
            XmlPullParser newPullParser = this.xmlParserFactory.newPullParser();
            Map hashMap = new HashMap();
            Map hashMap2 = new HashMap();
            hashMap2.put(TtmlNode.ANONYMOUS_REGION_ID, new TtmlRegion(null));
            newPullParser.setInput(new ByteArrayInputStream(bArr, 0, i), null);
            TtmlSubtitle ttmlSubtitle = null;
            LinkedList linkedList = new LinkedList();
            int i2 = 0;
            int eventType = newPullParser.getEventType();
            FrameAndTickRate frameAndTickRate = DEFAULT_FRAME_AND_TICK_RATE;
            for (int i3 = eventType; i3 != 1; i3 = newPullParser.getEventType()) {
                TtmlNode ttmlNode = (TtmlNode) linkedList.peekLast();
                if (i2 == 0) {
                    TtmlSubtitle ttmlSubtitle2;
                    FrameAndTickRate frameAndTickRate2;
                    int i4;
                    String name = newPullParser.getName();
                    if (i3 == 2) {
                        if (TtmlNode.TAG_TT.equals(name)) {
                            frameAndTickRate = parseFrameAndTickRates(newPullParser);
                        }
                        int i5;
                        if (!isSupportedTag(name)) {
                            Log.i(TAG, "Ignoring unsupported tag: " + newPullParser.getName());
                            eventType = i2 + 1;
                            ttmlSubtitle2 = ttmlSubtitle;
                            i5 = eventType;
                            frameAndTickRate2 = frameAndTickRate;
                            i4 = i5;
                        } else if (TtmlNode.TAG_HEAD.equals(name)) {
                            parseHeader(newPullParser, hashMap, hashMap2);
                            frameAndTickRate2 = frameAndTickRate;
                            i4 = i2;
                            ttmlSubtitle2 = ttmlSubtitle;
                        } else {
                            try {
                                TtmlNode parseNode = parseNode(newPullParser, ttmlNode, hashMap2, frameAndTickRate);
                                linkedList.addLast(parseNode);
                                if (ttmlNode != null) {
                                    ttmlNode.addChild(parseNode);
                                }
                                frameAndTickRate2 = frameAndTickRate;
                                i4 = i2;
                                ttmlSubtitle2 = ttmlSubtitle;
                            } catch (Throwable e) {
                                Log.w(TAG, "Suppressing parser error", e);
                                eventType = i2 + 1;
                                ttmlSubtitle2 = ttmlSubtitle;
                                i5 = eventType;
                                frameAndTickRate2 = frameAndTickRate;
                                i4 = i5;
                            }
                        }
                    } else if (i3 == 4) {
                        ttmlNode.addChild(TtmlNode.buildTextNode(newPullParser.getText()));
                        frameAndTickRate2 = frameAndTickRate;
                        i4 = i2;
                        ttmlSubtitle2 = ttmlSubtitle;
                    } else if (i3 == 3) {
                        TtmlSubtitle ttmlSubtitle3 = newPullParser.getName().equals(TtmlNode.TAG_TT) ? new TtmlSubtitle((TtmlNode) linkedList.getLast(), hashMap, hashMap2) : ttmlSubtitle;
                        linkedList.removeLast();
                        FrameAndTickRate frameAndTickRate3 = frameAndTickRate;
                        i4 = i2;
                        ttmlSubtitle2 = ttmlSubtitle3;
                        frameAndTickRate2 = frameAndTickRate3;
                    } else {
                        frameAndTickRate2 = frameAndTickRate;
                        i4 = i2;
                        ttmlSubtitle2 = ttmlSubtitle;
                    }
                    ttmlSubtitle = ttmlSubtitle2;
                    i2 = i4;
                    frameAndTickRate = frameAndTickRate2;
                } else if (i3 == 2) {
                    i2++;
                } else if (i3 == 3) {
                    i2--;
                }
                newPullParser.next();
            }
            return ttmlSubtitle;
        } catch (Throwable e2) {
            throw new SubtitleDecoderException("Unable to decode source", e2);
        } catch (Throwable e22) {
            throw new IllegalStateException("Unexpected error when reading input.", e22);
        }
    }
}
