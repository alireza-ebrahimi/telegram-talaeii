package com.google.android.gms.common.util;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@VisibleForTesting
public class Strings {
    private static final Pattern zzaak = Pattern.compile("\\$\\{(.*?)\\}");

    private Strings() {
    }

    public static String capitalize(String str) {
        if (str.length() == 0) {
            return str;
        }
        char charAt = str.charAt(0);
        char toUpperCase = Character.toUpperCase(charAt);
        if (charAt == toUpperCase) {
            return str;
        }
        String substring = str.substring(1);
        return new StringBuilder(String.valueOf(substring).length() + 1).append(toUpperCase).append(substring).toString();
    }

    public static String emptyToNull(String str) {
        return TextUtils.isEmpty(str) ? null : str;
    }

    public static String format(String str, Bundle bundle) {
        Matcher matcher = zzaak.matcher(str);
        if (!matcher.find()) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer();
        do {
            String group = matcher.group(1);
            Object obj = bundle.get(group);
            if (obj != null) {
                matcher.appendReplacement(stringBuffer, obj.toString());
            } else if (bundle.containsKey(group)) {
                matcher.appendReplacement(stringBuffer, "null");
            } else {
                matcher.appendReplacement(stringBuffer, TtmlNode.ANONYMOUS_REGION_ID);
            }
        } while (matcher.find());
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    public static boolean isEmptyOrWhitespace(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static String nullToEmpty(String str) {
        return str == null ? TtmlNode.ANONYMOUS_REGION_ID : str;
    }

    public static String padEnd(String str, int i, char c) {
        Preconditions.checkNotNull(str);
        if (str.length() >= i) {
            return str;
        }
        StringBuilder stringBuilder = new StringBuilder(i);
        stringBuilder.append(str);
        for (int length = str.length(); length < i; length++) {
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}
