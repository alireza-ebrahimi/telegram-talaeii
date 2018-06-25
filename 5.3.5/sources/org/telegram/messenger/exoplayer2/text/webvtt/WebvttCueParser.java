package org.telegram.messenger.exoplayer2.text.webvtt;

import android.support.annotation.NonNull;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan.Standard;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.text.webvtt.WebvttCue.Builder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class WebvttCueParser {
    private static final char CHAR_AMPERSAND = '&';
    private static final char CHAR_GREATER_THAN = '>';
    private static final char CHAR_LESS_THAN = '<';
    private static final char CHAR_SEMI_COLON = ';';
    private static final char CHAR_SLASH = '/';
    private static final char CHAR_SPACE = ' ';
    public static final Pattern CUE_HEADER_PATTERN = Pattern.compile("^(\\S+)\\s+-->\\s+(\\S+)(.*)?$");
    private static final Pattern CUE_SETTING_PATTERN = Pattern.compile("(\\S+?):(\\S+)");
    private static final String ENTITY_AMPERSAND = "amp";
    private static final String ENTITY_GREATER_THAN = "gt";
    private static final String ENTITY_LESS_THAN = "lt";
    private static final String ENTITY_NON_BREAK_SPACE = "nbsp";
    private static final int STYLE_BOLD = 1;
    private static final int STYLE_ITALIC = 2;
    private static final String TAG = "WebvttCueParser";
    private static final String TAG_BOLD = "b";
    private static final String TAG_CLASS = "c";
    private static final String TAG_ITALIC = "i";
    private static final String TAG_LANG = "lang";
    private static final String TAG_UNDERLINE = "u";
    private static final String TAG_VOICE = "v";
    private final StringBuilder textBuilder = new StringBuilder();

    private static final class StartTag {
        private static final String[] NO_CLASSES = new String[0];
        public final String[] classes;
        public final String name;
        public final int position;
        public final String voice;

        private StartTag(String name, int position, String voice, String[] classes) {
            this.position = position;
            this.name = name;
            this.voice = voice;
            this.classes = classes;
        }

        public static StartTag buildStartTag(String fullTagExpression, int position) {
            fullTagExpression = fullTagExpression.trim();
            if (fullTagExpression.isEmpty()) {
                return null;
            }
            String voice;
            String[] classes;
            int voiceStartIndex = fullTagExpression.indexOf(" ");
            if (voiceStartIndex == -1) {
                voice = "";
            } else {
                voice = fullTagExpression.substring(voiceStartIndex).trim();
                fullTagExpression = fullTagExpression.substring(0, voiceStartIndex);
            }
            String[] nameAndClasses = fullTagExpression.split("\\.");
            String name = nameAndClasses[0];
            if (nameAndClasses.length > 1) {
                classes = (String[]) Arrays.copyOfRange(nameAndClasses, 1, nameAndClasses.length);
            } else {
                classes = NO_CLASSES;
            }
            return new StartTag(name, position, voice, classes);
        }

        public static StartTag buildWholeCueVirtualTag() {
            return new StartTag("", 0, "", new String[0]);
        }
    }

    private static final class StyleMatch implements Comparable<StyleMatch> {
        public final int score;
        public final WebvttCssStyle style;

        public StyleMatch(int score, WebvttCssStyle style) {
            this.score = score;
            this.style = style;
        }

        public int compareTo(@NonNull StyleMatch another) {
            return this.score - another.score;
        }
    }

    boolean parseCue(ParsableByteArray webvttData, Builder builder, List<WebvttCssStyle> styles) {
        String firstLine = webvttData.readLine();
        if (firstLine == null) {
            return false;
        }
        Matcher cueHeaderMatcher = CUE_HEADER_PATTERN.matcher(firstLine);
        if (cueHeaderMatcher.matches()) {
            return parseCue(null, cueHeaderMatcher, webvttData, builder, this.textBuilder, styles);
        }
        String secondLine = webvttData.readLine();
        if (secondLine == null) {
            return false;
        }
        cueHeaderMatcher = CUE_HEADER_PATTERN.matcher(secondLine);
        if (!cueHeaderMatcher.matches()) {
            return false;
        }
        return parseCue(firstLine.trim(), cueHeaderMatcher, webvttData, builder, this.textBuilder, styles);
    }

    static void parseCueSettingsList(String cueSettingsList, Builder builder) {
        Matcher cueSettingMatcher = CUE_SETTING_PATTERN.matcher(cueSettingsList);
        while (cueSettingMatcher.find()) {
            String name = cueSettingMatcher.group(1);
            String value = cueSettingMatcher.group(2);
            try {
                if ("line".equals(name)) {
                    parseLineAttribute(value, builder);
                } else if ("align".equals(name)) {
                    builder.setTextAlignment(parseTextAlignment(value));
                } else if ("position".equals(name)) {
                    parsePositionAttribute(value, builder);
                } else if ("size".equals(name)) {
                    builder.setWidth(WebvttParserUtil.parsePercentage(value));
                } else {
                    Log.w(TAG, "Unknown cue setting " + name + ":" + value);
                }
            } catch (NumberFormatException e) {
                Log.w(TAG, "Skipping bad cue setting: " + cueSettingMatcher.group());
            }
        }
    }

    static void parseCueText(String id, String markup, Builder builder, List<WebvttCssStyle> styles) {
        SpannableStringBuilder spannedText = new SpannableStringBuilder();
        Stack<StartTag> startTagStack = new Stack();
        List<StyleMatch> scratchStyleMatches = new ArrayList();
        int pos = 0;
        while (pos < markup.length()) {
            char curr = markup.charAt(pos);
            switch (curr) {
                case '&':
                    int semiColonEndIndex = markup.indexOf(59, pos + 1);
                    int spaceEndIndex = markup.indexOf(32, pos + 1);
                    int entityEndIndex = semiColonEndIndex == -1 ? spaceEndIndex : spaceEndIndex == -1 ? semiColonEndIndex : Math.min(semiColonEndIndex, spaceEndIndex);
                    if (entityEndIndex == -1) {
                        spannedText.append(curr);
                        pos++;
                        break;
                    }
                    applyEntity(markup.substring(pos + 1, entityEndIndex), spannedText);
                    if (entityEndIndex == spaceEndIndex) {
                        spannedText.append(" ");
                    }
                    pos = entityEndIndex + 1;
                    break;
                case '<':
                    if (pos + 1 < markup.length()) {
                        int i;
                        int ltPos = pos;
                        boolean isClosingTag = markup.charAt(ltPos + 1) == '/';
                        pos = findEndOfTag(markup, ltPos + 1);
                        boolean isVoidTag = markup.charAt(pos + -2) == '/';
                        int i2 = ltPos + (isClosingTag ? 2 : 1);
                        if (isVoidTag) {
                            i = pos - 2;
                        } else {
                            i = pos - 1;
                        }
                        String fullTagExpression = markup.substring(i2, i);
                        String tagName = getTagName(fullTagExpression);
                        if (tagName != null && isSupportedTag(tagName)) {
                            if (!isClosingTag) {
                                if (!isVoidTag) {
                                    startTagStack.push(StartTag.buildStartTag(fullTagExpression, spannedText.length()));
                                    break;
                                }
                                break;
                            }
                            while (!startTagStack.isEmpty()) {
                                StartTag startTag = (StartTag) startTagStack.pop();
                                applySpansForTag(id, startTag, spannedText, styles, scratchStyleMatches);
                                if (startTag.name.equals(tagName)) {
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    pos++;
                    break;
                default:
                    spannedText.append(curr);
                    pos++;
                    break;
            }
        }
        while (!startTagStack.isEmpty()) {
            applySpansForTag(id, (StartTag) startTagStack.pop(), spannedText, styles, scratchStyleMatches);
        }
        applySpansForTag(id, StartTag.buildWholeCueVirtualTag(), spannedText, styles, scratchStyleMatches);
        builder.setText(spannedText);
    }

    private static boolean parseCue(String id, Matcher cueHeaderMatcher, ParsableByteArray webvttData, Builder builder, StringBuilder textBuilder, List<WebvttCssStyle> styles) {
        try {
            builder.setStartTime(WebvttParserUtil.parseTimestampUs(cueHeaderMatcher.group(1))).setEndTime(WebvttParserUtil.parseTimestampUs(cueHeaderMatcher.group(2)));
            parseCueSettingsList(cueHeaderMatcher.group(3), builder);
            textBuilder.setLength(0);
            while (true) {
                String line = webvttData.readLine();
                if (TextUtils.isEmpty(line)) {
                    parseCueText(id, textBuilder.toString(), builder, styles);
                    return true;
                }
                if (textBuilder.length() > 0) {
                    textBuilder.append(LogCollector.LINE_SEPARATOR);
                }
                textBuilder.append(line.trim());
            }
        } catch (NumberFormatException e) {
            Log.w(TAG, "Skipping cue with bad header: " + cueHeaderMatcher.group());
            return false;
        }
    }

    private static void parseLineAttribute(String s, Builder builder) throws NumberFormatException {
        int commaIndex = s.indexOf(44);
        if (commaIndex != -1) {
            builder.setLineAnchor(parsePositionAnchor(s.substring(commaIndex + 1)));
            s = s.substring(0, commaIndex);
        } else {
            builder.setLineAnchor(Integer.MIN_VALUE);
        }
        if (s.endsWith("%")) {
            builder.setLine(WebvttParserUtil.parsePercentage(s)).setLineType(0);
            return;
        }
        int lineNumber = Integer.parseInt(s);
        if (lineNumber < 0) {
            lineNumber--;
        }
        builder.setLine((float) lineNumber).setLineType(1);
    }

    private static void parsePositionAttribute(String s, Builder builder) throws NumberFormatException {
        int commaIndex = s.indexOf(44);
        if (commaIndex != -1) {
            builder.setPositionAnchor(parsePositionAnchor(s.substring(commaIndex + 1)));
            s = s.substring(0, commaIndex);
        } else {
            builder.setPositionAnchor(Integer.MIN_VALUE);
        }
        builder.setPosition(WebvttParserUtil.parsePercentage(s));
    }

    private static int parsePositionAnchor(String s) {
        int i = -1;
        switch (s.hashCode()) {
            case -1364013995:
                if (s.equals(TtmlNode.CENTER)) {
                    i = 1;
                    break;
                }
                break;
            case -1074341483:
                if (s.equals("middle")) {
                    i = 2;
                    break;
                }
                break;
            case 100571:
                if (s.equals(TtmlNode.END)) {
                    i = 3;
                    break;
                }
                break;
            case 109757538:
                if (s.equals(TtmlNode.START)) {
                    i = 0;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                return 0;
            case 1:
            case 2:
                return 1;
            case 3:
                return 2;
            default:
                Log.w(TAG, "Invalid anchor value: " + s);
                return Integer.MIN_VALUE;
        }
    }

    private static Alignment parseTextAlignment(String s) {
        Object obj = -1;
        switch (s.hashCode()) {
            case -1364013995:
                if (s.equals(TtmlNode.CENTER)) {
                    obj = 2;
                    break;
                }
                break;
            case -1074341483:
                if (s.equals("middle")) {
                    obj = 3;
                    break;
                }
                break;
            case 100571:
                if (s.equals(TtmlNode.END)) {
                    obj = 4;
                    break;
                }
                break;
            case 3317767:
                if (s.equals(TtmlNode.LEFT)) {
                    obj = 1;
                    break;
                }
                break;
            case 108511772:
                if (s.equals(TtmlNode.RIGHT)) {
                    obj = 5;
                    break;
                }
                break;
            case 109757538:
                if (s.equals(TtmlNode.START)) {
                    obj = null;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
            case 1:
                return Alignment.ALIGN_NORMAL;
            case 2:
            case 3:
                return Alignment.ALIGN_CENTER;
            case 4:
            case 5:
                return Alignment.ALIGN_OPPOSITE;
            default:
                Log.w(TAG, "Invalid alignment value: " + s);
                return null;
        }
    }

    private static int findEndOfTag(String markup, int startPos) {
        int index = markup.indexOf(62, startPos);
        return index == -1 ? markup.length() : index + 1;
    }

    private static void applyEntity(String entity, SpannableStringBuilder spannedText) {
        Object obj = -1;
        switch (entity.hashCode()) {
            case 3309:
                if (entity.equals(ENTITY_GREATER_THAN)) {
                    obj = 1;
                    break;
                }
                break;
            case 3464:
                if (entity.equals(ENTITY_LESS_THAN)) {
                    obj = null;
                    break;
                }
                break;
            case 96708:
                if (entity.equals(ENTITY_AMPERSAND)) {
                    obj = 3;
                    break;
                }
                break;
            case 3374865:
                if (entity.equals(ENTITY_NON_BREAK_SPACE)) {
                    obj = 2;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                spannedText.append(CHAR_LESS_THAN);
                return;
            case 1:
                spannedText.append(CHAR_GREATER_THAN);
                return;
            case 2:
                spannedText.append(CHAR_SPACE);
                return;
            case 3:
                spannedText.append(CHAR_AMPERSAND);
                return;
            default:
                Log.w(TAG, "ignoring unsupported entity: '&" + entity + ";'");
                return;
        }
    }

    private static boolean isSupportedTag(String tagName) {
        boolean z = true;
        switch (tagName.hashCode()) {
            case 98:
                if (tagName.equals(TAG_BOLD)) {
                    z = false;
                    break;
                }
                break;
            case 99:
                if (tagName.equals(TAG_CLASS)) {
                    z = true;
                    break;
                }
                break;
            case 105:
                if (tagName.equals(TAG_ITALIC)) {
                    z = true;
                    break;
                }
                break;
            case 117:
                if (tagName.equals(TAG_UNDERLINE)) {
                    z = true;
                    break;
                }
                break;
            case 118:
                if (tagName.equals(TAG_VOICE)) {
                    z = true;
                    break;
                }
                break;
            case 3314158:
                if (tagName.equals(TAG_LANG)) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case false:
            case true:
            case true:
            case true:
            case true:
            case true:
                return true;
            default:
                return false;
        }
    }

    private static void applySpansForTag(String cueId, StartTag startTag, SpannableStringBuilder text, List<WebvttCssStyle> styles, List<StyleMatch> scratchStyleMatches) {
        int start = startTag.position;
        int end = text.length();
        String str = startTag.name;
        int i = -1;
        switch (str.hashCode()) {
            case 0:
                if (str.equals("")) {
                    i = 6;
                    break;
                }
                break;
            case 98:
                if (str.equals(TAG_BOLD)) {
                    i = 0;
                    break;
                }
                break;
            case 99:
                if (str.equals(TAG_CLASS)) {
                    i = 3;
                    break;
                }
                break;
            case 105:
                if (str.equals(TAG_ITALIC)) {
                    i = 1;
                    break;
                }
                break;
            case 117:
                if (str.equals(TAG_UNDERLINE)) {
                    i = 2;
                    break;
                }
                break;
            case 118:
                if (str.equals(TAG_VOICE)) {
                    i = 5;
                    break;
                }
                break;
            case 3314158:
                if (str.equals(TAG_LANG)) {
                    i = 4;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                text.setSpan(new StyleSpan(1), start, end, 33);
                break;
            case 1:
                text.setSpan(new StyleSpan(2), start, end, 33);
                break;
            case 2:
                text.setSpan(new UnderlineSpan(), start, end, 33);
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                break;
            default:
                return;
        }
        scratchStyleMatches.clear();
        getApplicableStyles(styles, cueId, startTag, scratchStyleMatches);
        int styleMatchesCount = scratchStyleMatches.size();
        for (int i2 = 0; i2 < styleMatchesCount; i2++) {
            applyStyleToText(text, ((StyleMatch) scratchStyleMatches.get(i2)).style, start, end);
        }
    }

    private static void applyStyleToText(SpannableStringBuilder spannedText, WebvttCssStyle style, int start, int end) {
        if (style != null) {
            if (style.getStyle() != -1) {
                spannedText.setSpan(new StyleSpan(style.getStyle()), start, end, 33);
            }
            if (style.isLinethrough()) {
                spannedText.setSpan(new StrikethroughSpan(), start, end, 33);
            }
            if (style.isUnderline()) {
                spannedText.setSpan(new UnderlineSpan(), start, end, 33);
            }
            if (style.hasFontColor()) {
                spannedText.setSpan(new ForegroundColorSpan(style.getFontColor()), start, end, 33);
            }
            if (style.hasBackgroundColor()) {
                spannedText.setSpan(new BackgroundColorSpan(style.getBackgroundColor()), start, end, 33);
            }
            if (style.getFontFamily() != null) {
                spannedText.setSpan(new TypefaceSpan(style.getFontFamily()), start, end, 33);
            }
            if (style.getTextAlign() != null) {
                spannedText.setSpan(new Standard(style.getTextAlign()), start, end, 33);
            }
            switch (style.getFontSizeUnit()) {
                case 1:
                    spannedText.setSpan(new AbsoluteSizeSpan((int) style.getFontSize(), true), start, end, 33);
                    return;
                case 2:
                    spannedText.setSpan(new RelativeSizeSpan(style.getFontSize()), start, end, 33);
                    return;
                case 3:
                    spannedText.setSpan(new RelativeSizeSpan(style.getFontSize() / 100.0f), start, end, 33);
                    return;
                default:
                    return;
            }
        }
    }

    private static String getTagName(String tagExpression) {
        tagExpression = tagExpression.trim();
        if (tagExpression.isEmpty()) {
            return null;
        }
        return tagExpression.split("[ \\.]")[0];
    }

    private static void getApplicableStyles(List<WebvttCssStyle> declaredStyles, String id, StartTag tag, List<StyleMatch> output) {
        int styleCount = declaredStyles.size();
        for (int i = 0; i < styleCount; i++) {
            WebvttCssStyle style = (WebvttCssStyle) declaredStyles.get(i);
            int score = style.getSpecificityScore(id, tag.name, tag.classes, tag.voice);
            if (score > 0) {
                output.add(new StyleMatch(score, style));
            }
        }
        Collections.sort(output);
    }
}
