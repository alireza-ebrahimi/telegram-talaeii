package com.google.android.gms.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GmsVersionParser {
    public static final int UNKNOWN = -1;
    private static Pattern zzzy = null;

    private GmsVersionParser() {
    }

    public static int parseBuildMajorVersion(int i) {
        return i == -1 ? -1 : i / DefaultOggSeeker.MATCH_BYTE_RANGE;
    }

    public static long parseBuildNumber(String str) {
        long j = -1;
        if (str != null) {
            Matcher matcher = zzdc().matcher(str);
            if (matcher.find()) {
                try {
                    j = Long.parseLong(matcher.group(2));
                } catch (NumberFormatException e) {
                }
            }
        }
        return j;
    }

    public static int parseBuildType(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) (parseVariantCode / 10000);
    }

    public static int parseBuildVersion(int i) {
        return i == -1 ? -1 : i / 1000;
    }

    public static int parseScreenDensity(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) (parseVariantCode % 100);
    }

    public static int parseTargetArchitecture(String str) {
        long parseVariantCode = parseVariantCode(str);
        return parseVariantCode == -1 ? -1 : (int) ((parseVariantCode / 100) % 100);
    }

    public static long parseVariantCode(String str) {
        long j = -1;
        if (str != null) {
            Matcher matcher = zzdc().matcher(str);
            if (matcher.find()) {
                try {
                    j = Long.parseLong(matcher.group(1));
                } catch (NumberFormatException e) {
                }
            }
        }
        return j;
    }

    private static Pattern zzdc() {
        if (zzzy == null) {
            zzzy = Pattern.compile("\\((?:eng-)?(\\d+)-(.+?)[-)$]");
        }
        return zzzy;
    }
}
