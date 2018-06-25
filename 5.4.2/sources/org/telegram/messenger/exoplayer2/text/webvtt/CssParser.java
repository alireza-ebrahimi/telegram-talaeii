package org.telegram.messenger.exoplayer2.text.webvtt;

import android.text.TextUtils;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.util.ColorParser;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class CssParser {
    private static final String BLOCK_END = "}";
    private static final String BLOCK_START = "{";
    private static final String PROPERTY_BGCOLOR = "background-color";
    private static final String PROPERTY_FONT_FAMILY = "font-family";
    private static final String PROPERTY_FONT_STYLE = "font-style";
    private static final String PROPERTY_FONT_WEIGHT = "font-weight";
    private static final String PROPERTY_TEXT_DECORATION = "text-decoration";
    private static final String VALUE_BOLD = "bold";
    private static final String VALUE_ITALIC = "italic";
    private static final String VALUE_UNDERLINE = "underline";
    private static final Pattern VOICE_NAME_PATTERN = Pattern.compile("\\[voice=\"([^\"]*)\"\\]");
    private final StringBuilder stringBuilder = new StringBuilder();
    private final ParsableByteArray styleInput = new ParsableByteArray();

    private void applySelectorToStyle(WebvttCssStyle webvttCssStyle, String str) {
        if (!TtmlNode.ANONYMOUS_REGION_ID.equals(str)) {
            int indexOf = str.indexOf(91);
            if (indexOf != -1) {
                Matcher matcher = VOICE_NAME_PATTERN.matcher(str.substring(indexOf));
                if (matcher.matches()) {
                    webvttCssStyle.setTargetVoice(matcher.group(1));
                }
                str = str.substring(0, indexOf);
            }
            String[] split = str.split("\\.");
            String str2 = split[0];
            int indexOf2 = str2.indexOf(35);
            if (indexOf2 != -1) {
                webvttCssStyle.setTargetTagName(str2.substring(0, indexOf2));
                webvttCssStyle.setTargetId(str2.substring(indexOf2 + 1));
            } else {
                webvttCssStyle.setTargetTagName(str2);
            }
            if (split.length > 1) {
                webvttCssStyle.setTargetClasses((String[]) Arrays.copyOfRange(split, 1, split.length));
            }
        }
    }

    private static boolean maybeSkipComment(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        byte[] bArr = parsableByteArray.data;
        if (position + 2 <= limit) {
            int i = position + 1;
            if (bArr[position] == (byte) 47) {
                position = i + 1;
                if (bArr[i] == (byte) 42) {
                    i = position;
                    while (i + 1 < limit) {
                        position = i + 1;
                        if (((char) bArr[i]) == '*' && ((char) bArr[position]) == '/') {
                            limit = position + 1;
                            position = limit;
                        }
                        i = position;
                    }
                    parsableByteArray.skipBytes(limit - parsableByteArray.getPosition());
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean maybeSkipWhitespace(ParsableByteArray parsableByteArray) {
        switch (peekCharAtPosition(parsableByteArray, parsableByteArray.getPosition())) {
            case '\t':
            case '\n':
            case '\f':
            case '\r':
            case ' ':
                parsableByteArray.skipBytes(1);
                return true;
            default:
                return false;
        }
    }

    private static String parseIdentifier(ParsableByteArray parsableByteArray, StringBuilder stringBuilder) {
        int i = 0;
        stringBuilder.setLength(0);
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        while (position < limit && r0 == 0) {
            char c = (char) parsableByteArray.data[position];
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && !((c >= '0' && c <= '9') || c == '#' || c == '-' || c == '.' || c == '_'))) {
                i = 1;
            } else {
                position++;
                stringBuilder.append(c);
            }
        }
        parsableByteArray.skipBytes(position - parsableByteArray.getPosition());
        return stringBuilder.toString();
    }

    static String parseNextToken(ParsableByteArray parsableByteArray, StringBuilder stringBuilder) {
        skipWhitespaceAndComments(parsableByteArray);
        if (parsableByteArray.bytesLeft() == 0) {
            return null;
        }
        String parseIdentifier = parseIdentifier(parsableByteArray, stringBuilder);
        return TtmlNode.ANONYMOUS_REGION_ID.equals(parseIdentifier) ? TtmlNode.ANONYMOUS_REGION_ID + ((char) parsableByteArray.readUnsignedByte()) : parseIdentifier;
    }

    private static String parsePropertyValue(ParsableByteArray parsableByteArray, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder();
        Object obj = null;
        while (obj == null) {
            int position = parsableByteArray.getPosition();
            String parseNextToken = parseNextToken(parsableByteArray, stringBuilder);
            if (parseNextToken == null) {
                return null;
            }
            if (BLOCK_END.equals(parseNextToken) || ";".equals(parseNextToken)) {
                parsableByteArray.setPosition(position);
                obj = 1;
            } else {
                stringBuilder2.append(parseNextToken);
            }
        }
        return stringBuilder2.toString();
    }

    private static String parseSelector(ParsableByteArray parsableByteArray, StringBuilder stringBuilder) {
        skipWhitespaceAndComments(parsableByteArray);
        if (parsableByteArray.bytesLeft() < 5) {
            return null;
        }
        if (!"::cue".equals(parsableByteArray.readString(5))) {
            return null;
        }
        int position = parsableByteArray.getPosition();
        String parseNextToken = parseNextToken(parsableByteArray, stringBuilder);
        if (parseNextToken == null) {
            return null;
        }
        if (BLOCK_START.equals(parseNextToken)) {
            parsableByteArray.setPosition(position);
            return TtmlNode.ANONYMOUS_REGION_ID;
        }
        String readCueTarget = "(".equals(parseNextToken) ? readCueTarget(parsableByteArray) : null;
        parseNextToken = parseNextToken(parsableByteArray, stringBuilder);
        return (!")".equals(parseNextToken) || parseNextToken == null) ? null : readCueTarget;
    }

    private static void parseStyleDeclaration(ParsableByteArray parsableByteArray, WebvttCssStyle webvttCssStyle, StringBuilder stringBuilder) {
        skipWhitespaceAndComments(parsableByteArray);
        String parseIdentifier = parseIdentifier(parsableByteArray, stringBuilder);
        if (!TtmlNode.ANONYMOUS_REGION_ID.equals(parseIdentifier) && ":".equals(parseNextToken(parsableByteArray, stringBuilder))) {
            skipWhitespaceAndComments(parsableByteArray);
            String parsePropertyValue = parsePropertyValue(parsableByteArray, stringBuilder);
            if (parsePropertyValue != null && !TtmlNode.ANONYMOUS_REGION_ID.equals(parsePropertyValue)) {
                int position = parsableByteArray.getPosition();
                String parseNextToken = parseNextToken(parsableByteArray, stringBuilder);
                if (!";".equals(parseNextToken)) {
                    if (BLOCK_END.equals(parseNextToken)) {
                        parsableByteArray.setPosition(position);
                    } else {
                        return;
                    }
                }
                if (TtmlNode.ATTR_TTS_COLOR.equals(parseIdentifier)) {
                    webvttCssStyle.setFontColor(ColorParser.parseCssColor(parsePropertyValue));
                } else if (PROPERTY_BGCOLOR.equals(parseIdentifier)) {
                    webvttCssStyle.setBackgroundColor(ColorParser.parseCssColor(parsePropertyValue));
                } else if (PROPERTY_TEXT_DECORATION.equals(parseIdentifier)) {
                    if ("underline".equals(parsePropertyValue)) {
                        webvttCssStyle.setUnderline(true);
                    }
                } else if (PROPERTY_FONT_FAMILY.equals(parseIdentifier)) {
                    webvttCssStyle.setFontFamily(parsePropertyValue);
                } else if (PROPERTY_FONT_WEIGHT.equals(parseIdentifier)) {
                    if ("bold".equals(parsePropertyValue)) {
                        webvttCssStyle.setBold(true);
                    }
                } else if (PROPERTY_FONT_STYLE.equals(parseIdentifier) && "italic".equals(parsePropertyValue)) {
                    webvttCssStyle.setItalic(true);
                }
            }
        }
    }

    private static char peekCharAtPosition(ParsableByteArray parsableByteArray, int i) {
        return (char) parsableByteArray.data[i];
    }

    private static String readCueTarget(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        int limit = parsableByteArray.limit();
        int i = position;
        Object obj = null;
        while (i < limit && r0 == null) {
            int i2 = i + 1;
            obj = ((char) parsableByteArray.data[i]) == ')' ? 1 : null;
            i = i2;
        }
        return parsableByteArray.readString((i - 1) - parsableByteArray.getPosition()).trim();
    }

    static void skipStyleBlock(ParsableByteArray parsableByteArray) {
        do {
        } while (!TextUtils.isEmpty(parsableByteArray.readLine()));
    }

    static void skipWhitespaceAndComments(ParsableByteArray parsableByteArray) {
        Object obj = 1;
        while (parsableByteArray.bytesLeft() > 0 && r0 != null) {
            obj = (maybeSkipWhitespace(parsableByteArray) || maybeSkipComment(parsableByteArray)) ? 1 : null;
        }
    }

    public WebvttCssStyle parseBlock(ParsableByteArray parsableByteArray) {
        this.stringBuilder.setLength(0);
        int position = parsableByteArray.getPosition();
        skipStyleBlock(parsableByteArray);
        this.styleInput.reset(parsableByteArray.data, parsableByteArray.getPosition());
        this.styleInput.setPosition(position);
        String parseSelector = parseSelector(this.styleInput, this.stringBuilder);
        if (parseSelector == null || !BLOCK_START.equals(parseNextToken(this.styleInput, this.stringBuilder))) {
            return null;
        }
        WebvttCssStyle webvttCssStyle = new WebvttCssStyle();
        applySelectorToStyle(webvttCssStyle, parseSelector);
        int i = 0;
        Object obj = null;
        while (i == 0) {
            int position2 = this.styleInput.getPosition();
            obj = parseNextToken(this.styleInput, this.stringBuilder);
            i = (obj == null || BLOCK_END.equals(obj)) ? 1 : 0;
            if (i == 0) {
                this.styleInput.setPosition(position2);
                parseStyleDeclaration(this.styleInput, webvttCssStyle, this.stringBuilder);
            }
        }
        return !BLOCK_END.equals(obj) ? null : webvttCssStyle;
    }
}
