package org.telegram.messenger.exoplayer2.text.webvtt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class WebvttParserUtil {
    private static final Pattern COMMENT = Pattern.compile("^NOTE(( |\t).*)?$");
    private static final Pattern HEADER = Pattern.compile("^﻿?WEBVTT(( |\t).*)?$");

    private WebvttParserUtil() {
    }

    public static Matcher findNextCueHeader(ParsableByteArray parsableByteArray) {
        while (true) {
            CharSequence readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return null;
            }
            if (COMMENT.matcher(readLine).matches()) {
                while (true) {
                    String readLine2 = parsableByteArray.readLine();
                    if (readLine2 == null || readLine2.isEmpty()) {
                        break;
                    }
                }
            } else {
                Matcher matcher = WebvttCueParser.CUE_HEADER_PATTERN.matcher(readLine);
                if (matcher.matches()) {
                    return matcher;
                }
            }
        }
    }

    public static float parsePercentage(String str) {
        if (str.endsWith("%")) {
            return Float.parseFloat(str.substring(0, str.length() - 1)) / 100.0f;
        }
        throw new NumberFormatException("Percentages must end with %");
    }

    public static long parseTimestampUs(String str) {
        int i = 0;
        long j = 0;
        String[] split = str.split("\\.", 2);
        String[] split2 = split[0].split(":");
        while (i < split2.length) {
            j = (j * 60) + Long.parseLong(split2[i]);
            i++;
        }
        return (Long.parseLong(split[1]) + (j * 1000)) * 1000;
    }

    public static void validateWebvttHeaderLine(ParsableByteArray parsableByteArray) {
        Object readLine = parsableByteArray.readLine();
        if (readLine == null || !HEADER.matcher(readLine).matches()) {
            throw new SubtitleDecoderException("Expected WEBVTT. Got " + readLine);
        }
    }
}
