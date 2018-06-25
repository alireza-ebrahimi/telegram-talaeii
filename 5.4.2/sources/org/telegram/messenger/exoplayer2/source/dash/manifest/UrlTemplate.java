package org.telegram.messenger.exoplayer2.source.dash.manifest;

import java.util.Locale;

public final class UrlTemplate {
    private static final String BANDWIDTH = "Bandwidth";
    private static final int BANDWIDTH_ID = 3;
    private static final String DEFAULT_FORMAT_TAG = "%01d";
    private static final String ESCAPED_DOLLAR = "$$";
    private static final String NUMBER = "Number";
    private static final int NUMBER_ID = 2;
    private static final String REPRESENTATION = "RepresentationID";
    private static final int REPRESENTATION_ID = 1;
    private static final String TIME = "Time";
    private static final int TIME_ID = 4;
    private final int identifierCount;
    private final String[] identifierFormatTags;
    private final int[] identifiers;
    private final String[] urlPieces;

    private UrlTemplate(String[] strArr, int[] iArr, String[] strArr2, int i) {
        this.urlPieces = strArr;
        this.identifiers = iArr;
        this.identifierFormatTags = strArr2;
        this.identifierCount = i;
    }

    public static UrlTemplate compile(String str) {
        String[] strArr = new String[5];
        int[] iArr = new int[4];
        String[] strArr2 = new String[4];
        return new UrlTemplate(strArr, iArr, strArr2, parseTemplate(str, strArr, iArr, strArr2));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int parseTemplate(java.lang.String r10, java.lang.String[] r11, int[] r12, java.lang.String[] r13) {
        /*
        r6 = 2;
        r5 = 1;
        r4 = -1;
        r1 = 0;
        r0 = "";
        r11[r1] = r0;
        r0 = r1;
        r2 = r1;
    L_0x000b:
        r3 = r10.length();
        if (r2 >= r3) goto L_0x0123;
    L_0x0011:
        r3 = "$";
        r3 = r10.indexOf(r3, r2);
        if (r3 != r4) goto L_0x0038;
    L_0x001a:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r7 = r11[r0];
        r3 = r3.append(r7);
        r2 = r10.substring(r2);
        r2 = r3.append(r2);
        r2 = r2.toString();
        r11[r0] = r2;
        r2 = r10.length();
        goto L_0x000b;
    L_0x0038:
        if (r3 == r2) goto L_0x0055;
    L_0x003a:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r8 = r11[r0];
        r7 = r7.append(r8);
        r2 = r10.substring(r2, r3);
        r2 = r7.append(r2);
        r2 = r2.toString();
        r11[r0] = r2;
        r2 = r3;
        goto L_0x000b;
    L_0x0055:
        r3 = "$$";
        r3 = r10.startsWith(r3, r2);
        if (r3 == 0) goto L_0x0079;
    L_0x005e:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r7 = r11[r0];
        r3 = r3.append(r7);
        r7 = "$";
        r3 = r3.append(r7);
        r3 = r3.toString();
        r11[r0] = r3;
        r2 = r2 + 2;
        goto L_0x000b;
    L_0x0079:
        r3 = "$";
        r7 = r2 + 1;
        r7 = r10.indexOf(r3, r7);
        r2 = r2 + 1;
        r3 = r10.substring(r2, r7);
        r2 = "RepresentationID";
        r2 = r3.equals(r2);
        if (r2 == 0) goto L_0x009e;
    L_0x0091:
        r12[r0] = r5;
    L_0x0093:
        r0 = r0 + 1;
        r2 = "";
        r11[r0] = r2;
        r2 = r7 + 1;
        goto L_0x000b;
    L_0x009e:
        r2 = "%0";
        r8 = r3.indexOf(r2);
        r2 = "%01d";
        if (r8 == r4) goto L_0x00cf;
    L_0x00aa:
        r2 = r3.substring(r8);
        r9 = "d";
        r9 = r2.endsWith(r9);
        if (r9 != 0) goto L_0x00cb;
    L_0x00b7:
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r2 = r9.append(r2);
        r9 = "d";
        r2 = r2.append(r9);
        r2 = r2.toString();
    L_0x00cb:
        r3 = r3.substring(r1, r8);
    L_0x00cf:
        r8 = r3.hashCode();
        switch(r8) {
            case -1950496919: goto L_0x00f4;
            case 2606829: goto L_0x010a;
            case 38199441: goto L_0x00ff;
            default: goto L_0x00d6;
        };
    L_0x00d6:
        r3 = r4;
    L_0x00d7:
        switch(r3) {
            case 0: goto L_0x0115;
            case 1: goto L_0x011b;
            case 2: goto L_0x011f;
            default: goto L_0x00da;
        };
    L_0x00da:
        r0 = new java.lang.IllegalArgumentException;
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "Invalid template: ";
        r1 = r1.append(r2);
        r1 = r1.append(r10);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x00f4:
        r8 = "Number";
        r3 = r3.equals(r8);
        if (r3 == 0) goto L_0x00d6;
    L_0x00fd:
        r3 = r1;
        goto L_0x00d7;
    L_0x00ff:
        r8 = "Bandwidth";
        r3 = r3.equals(r8);
        if (r3 == 0) goto L_0x00d6;
    L_0x0108:
        r3 = r5;
        goto L_0x00d7;
    L_0x010a:
        r8 = "Time";
        r3 = r3.equals(r8);
        if (r3 == 0) goto L_0x00d6;
    L_0x0113:
        r3 = r6;
        goto L_0x00d7;
    L_0x0115:
        r12[r0] = r6;
    L_0x0117:
        r13[r0] = r2;
        goto L_0x0093;
    L_0x011b:
        r3 = 3;
        r12[r0] = r3;
        goto L_0x0117;
    L_0x011f:
        r3 = 4;
        r12[r0] = r3;
        goto L_0x0117;
    L_0x0123:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.source.dash.manifest.UrlTemplate.parseTemplate(java.lang.String, java.lang.String[], int[], java.lang.String[]):int");
    }

    public String buildUri(String str, int i, int i2, long j) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i3 = 0; i3 < this.identifierCount; i3++) {
            stringBuilder.append(this.urlPieces[i3]);
            if (this.identifiers[i3] == 1) {
                stringBuilder.append(str);
            } else if (this.identifiers[i3] == 2) {
                stringBuilder.append(String.format(Locale.US, this.identifierFormatTags[i3], new Object[]{Integer.valueOf(i)}));
            } else if (this.identifiers[i3] == 3) {
                stringBuilder.append(String.format(Locale.US, this.identifierFormatTags[i3], new Object[]{Integer.valueOf(i2)}));
            } else if (this.identifiers[i3] == 4) {
                stringBuilder.append(String.format(Locale.US, this.identifierFormatTags[i3], new Object[]{Long.valueOf(j)}));
            }
        }
        stringBuilder.append(this.urlPieces[this.identifierCount]);
        return stringBuilder.toString();
    }
}
