package org.telegram.messenger.exoplayer2.text.ssa;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.LongArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class SsaDecoder extends SimpleSubtitleDecoder {
    private static final String DIALOGUE_LINE_PREFIX = "Dialogue: ";
    private static final String FORMAT_LINE_PREFIX = "Format: ";
    private static final Pattern SSA_TIMECODE_PATTERN = Pattern.compile("(?:(\\d+):)?(\\d+):(\\d+)(?::|\\.)(\\d+)");
    private static final String TAG = "SsaDecoder";
    private int formatEndIndex;
    private int formatKeyCount;
    private int formatStartIndex;
    private int formatTextIndex;
    private final boolean haveInitializationData;

    public SsaDecoder() {
        this(null);
    }

    public SsaDecoder(List<byte[]> list) {
        super(TAG);
        if (list != null) {
            this.haveInitializationData = true;
            String str = new String((byte[]) list.get(0));
            Assertions.checkArgument(str.startsWith(FORMAT_LINE_PREFIX));
            parseFormatLine(str);
            parseHeader(new ParsableByteArray((byte[]) list.get(1)));
            return;
        }
        this.haveInitializationData = false;
    }

    private void parseDialogueLine(String str, List<Cue> list, LongArray longArray) {
        if (this.formatKeyCount == 0) {
            Log.w(TAG, "Skipping dialogue line before format: " + str);
            return;
        }
        String[] split = str.substring(DIALOGUE_LINE_PREFIX.length()).split(",", this.formatKeyCount);
        long parseTimecodeUs = parseTimecodeUs(split[this.formatStartIndex]);
        if (parseTimecodeUs == C3446C.TIME_UNSET) {
            Log.w(TAG, "Skipping invalid timing: " + str);
            return;
        }
        long j;
        String str2 = split[this.formatEndIndex];
        if (str2.trim().isEmpty()) {
            j = C3446C.TIME_UNSET;
        } else {
            j = parseTimecodeUs(str2);
            if (j == C3446C.TIME_UNSET) {
                Log.w(TAG, "Skipping invalid timing: " + str);
                return;
            }
        }
        list.add(new Cue(split[this.formatTextIndex].replaceAll("\\{.*?\\}", TtmlNode.ANONYMOUS_REGION_ID).replaceAll("\\\\N", "\n").replaceAll("\\\\n", "\n")));
        longArray.add(parseTimecodeUs);
        if (j != C3446C.TIME_UNSET) {
            list.add(null);
            longArray.add(j);
        }
    }

    private void parseEventBody(ParsableByteArray parsableByteArray, List<Cue> list, LongArray longArray) {
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
            if (!this.haveInitializationData && readLine.startsWith(FORMAT_LINE_PREFIX)) {
                parseFormatLine(readLine);
            } else if (readLine.startsWith(DIALOGUE_LINE_PREFIX)) {
                parseDialogueLine(readLine, list, longArray);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseFormatLine(java.lang.String r7) {
        /*
        r6 = this;
        r1 = 0;
        r3 = -1;
        r0 = "Format: ";
        r0 = r0.length();
        r0 = r7.substring(r0);
        r2 = ",";
        r4 = android.text.TextUtils.split(r0, r2);
        r0 = r4.length;
        r6.formatKeyCount = r0;
        r6.formatStartIndex = r3;
        r6.formatEndIndex = r3;
        r6.formatTextIndex = r3;
        r0 = r1;
    L_0x001e:
        r2 = r6.formatKeyCount;
        if (r0 >= r2) goto L_0x0064;
    L_0x0022:
        r2 = r4[r0];
        r2 = r2.trim();
        r2 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r2);
        r5 = r2.hashCode();
        switch(r5) {
            case 100571: goto L_0x0045;
            case 3556653: goto L_0x0050;
            case 109757538: goto L_0x003a;
            default: goto L_0x0033;
        };
    L_0x0033:
        r2 = r3;
    L_0x0034:
        switch(r2) {
            case 0: goto L_0x005b;
            case 1: goto L_0x005e;
            case 2: goto L_0x0061;
            default: goto L_0x0037;
        };
    L_0x0037:
        r0 = r0 + 1;
        goto L_0x001e;
    L_0x003a:
        r5 = "start";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x0033;
    L_0x0043:
        r2 = r1;
        goto L_0x0034;
    L_0x0045:
        r5 = "end";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x0033;
    L_0x004e:
        r2 = 1;
        goto L_0x0034;
    L_0x0050:
        r5 = "text";
        r2 = r2.equals(r5);
        if (r2 == 0) goto L_0x0033;
    L_0x0059:
        r2 = 2;
        goto L_0x0034;
    L_0x005b:
        r6.formatStartIndex = r0;
        goto L_0x0037;
    L_0x005e:
        r6.formatEndIndex = r0;
        goto L_0x0037;
    L_0x0061:
        r6.formatTextIndex = r0;
        goto L_0x0037;
    L_0x0064:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.ssa.SsaDecoder.parseFormatLine(java.lang.String):void");
    }

    private void parseHeader(ParsableByteArray parsableByteArray) {
        String readLine;
        do {
            readLine = parsableByteArray.readLine();
            if (readLine == null) {
                return;
            }
        } while (!readLine.startsWith("[Events]"));
    }

    public static long parseTimecodeUs(String str) {
        Matcher matcher = SSA_TIMECODE_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return C3446C.TIME_UNSET;
        }
        return (Long.parseLong(matcher.group(4)) * 10000) + (((((Long.parseLong(matcher.group(1)) * 60) * 60) * C3446C.MICROS_PER_SECOND) + ((Long.parseLong(matcher.group(2)) * 60) * C3446C.MICROS_PER_SECOND)) + (Long.parseLong(matcher.group(3)) * C3446C.MICROS_PER_SECOND));
    }

    protected SsaSubtitle decode(byte[] bArr, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        LongArray longArray = new LongArray();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        if (!this.haveInitializationData) {
            parseHeader(parsableByteArray);
        }
        parseEventBody(parsableByteArray, arrayList, longArray);
        Cue[] cueArr = new Cue[arrayList.size()];
        arrayList.toArray(cueArr);
        return new SsaSubtitle(cueArr, longArray.toArray());
    }
}
