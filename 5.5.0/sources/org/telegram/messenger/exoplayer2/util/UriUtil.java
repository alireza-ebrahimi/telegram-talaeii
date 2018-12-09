package org.telegram.messenger.exoplayer2.util;

import android.net.Uri;
import android.text.TextUtils;

public final class UriUtil {
    private static final int FRAGMENT = 3;
    private static final int INDEX_COUNT = 4;
    private static final int PATH = 1;
    private static final int QUERY = 2;
    private static final int SCHEME_COLON = 0;

    private UriUtil() {
    }

    private static int[] getUriIndices(String str) {
        int[] iArr = new int[4];
        if (TextUtils.isEmpty(str)) {
            iArr[0] = -1;
            return iArr;
        }
        int length = str.length();
        int indexOf = str.indexOf(35);
        if (indexOf != -1) {
            length = indexOf;
        }
        indexOf = str.indexOf(63);
        if (indexOf == -1 || indexOf > length) {
            indexOf = length;
        }
        int indexOf2 = str.indexOf(47);
        if (indexOf2 == -1 || indexOf2 > indexOf) {
            indexOf2 = indexOf;
        }
        int indexOf3 = str.indexOf(58);
        if (indexOf3 > indexOf2) {
            indexOf3 = -1;
        }
        indexOf2 = (indexOf3 + 2 < indexOf && str.charAt(indexOf3 + 1) == '/' && str.charAt(indexOf3 + 2) == '/') ? 1 : 0;
        if (indexOf2 != 0) {
            indexOf2 = str.indexOf(47, indexOf3 + 3);
            if (indexOf2 == -1 || indexOf2 > indexOf) {
                indexOf2 = indexOf;
            }
        } else {
            indexOf2 = indexOf3 + 1;
        }
        iArr[0] = indexOf3;
        iArr[1] = indexOf2;
        iArr[2] = indexOf;
        iArr[3] = length;
        return iArr;
    }

    private static String removeDotSegments(StringBuilder stringBuilder, int i, int i2) {
        if (i >= i2) {
            return stringBuilder.toString();
        }
        if (stringBuilder.charAt(i) == '/') {
            i++;
        }
        int i3 = i;
        int i4 = i;
        int i5 = i2;
        while (i3 <= i5) {
            int i6;
            if (i3 == i5) {
                i6 = i3;
            } else if (stringBuilder.charAt(i3) == '/') {
                i6 = i3 + 1;
            } else {
                i3++;
            }
            if (i3 == i4 + 1 && stringBuilder.charAt(i4) == '.') {
                stringBuilder.delete(i4, i6);
                i5 -= i6 - i4;
                i3 = i4;
            } else if (i3 == i4 + 2 && stringBuilder.charAt(i4) == '.' && stringBuilder.charAt(i4 + 1) == '.') {
                i4 = stringBuilder.lastIndexOf("/", i4 - 2) + 1;
                i3 = i4 > i ? i4 : i;
                stringBuilder.delete(i3, i6);
                i5 -= i6 - i3;
                i3 = i4;
            } else {
                i4 = i3 + 1;
                i3 = i4;
            }
            int i7 = i4;
            i4 = i3;
            i3 = i7;
        }
        return stringBuilder.toString();
    }

    public static String resolve(String str, String str2) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str == null) {
            CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID;
        }
        if (str2 == null) {
            str2 = TtmlNode.ANONYMOUS_REGION_ID;
        }
        int[] uriIndices = getUriIndices(str2);
        if (uriIndices[0] != -1) {
            stringBuilder.append(str2);
            removeDotSegments(stringBuilder, uriIndices[1], uriIndices[2]);
            return stringBuilder.toString();
        }
        int[] uriIndices2 = getUriIndices(charSequence);
        if (uriIndices[3] == 0) {
            return stringBuilder.append(charSequence, 0, uriIndices2[3]).append(str2).toString();
        }
        if (uriIndices[2] == 0) {
            return stringBuilder.append(charSequence, 0, uriIndices2[2]).append(str2).toString();
        }
        int i;
        if (uriIndices[1] != 0) {
            i = uriIndices2[0] + 1;
            stringBuilder.append(charSequence, 0, i).append(str2);
            return removeDotSegments(stringBuilder, uriIndices[1] + i, i + uriIndices[2]);
        } else if (str2.charAt(uriIndices[1]) == '/') {
            stringBuilder.append(charSequence, 0, uriIndices2[1]).append(str2);
            return removeDotSegments(stringBuilder, uriIndices2[1], uriIndices[2] + uriIndices2[1]);
        } else if (uriIndices2[0] + 2 >= uriIndices2[1] || uriIndices2[1] != uriIndices2[2]) {
            i = charSequence.lastIndexOf(47, uriIndices2[2] - 1);
            i = i == -1 ? uriIndices2[1] : i + 1;
            stringBuilder.append(charSequence, 0, i).append(str2);
            return removeDotSegments(stringBuilder, uriIndices2[1], i + uriIndices[2]);
        } else {
            stringBuilder.append(charSequence, 0, uriIndices2[1]).append('/').append(str2);
            return removeDotSegments(stringBuilder, uriIndices2[1], (uriIndices[2] + uriIndices2[1]) + 1);
        }
    }

    public static Uri resolveToUri(String str, String str2) {
        return Uri.parse(resolve(str, str2));
    }
}
