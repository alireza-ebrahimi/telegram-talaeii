package org.telegram.messenger.exoplayer2.text.ssa;

import android.util.Log;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.C0907C;
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

    public SsaDecoder(List<byte[]> initializationData) {
        super(TAG);
        if (initializationData != null) {
            this.haveInitializationData = true;
            String formatLine = new String((byte[]) initializationData.get(0));
            Assertions.checkArgument(formatLine.startsWith(FORMAT_LINE_PREFIX));
            parseFormatLine(formatLine);
            parseHeader(new ParsableByteArray((byte[]) initializationData.get(1)));
            return;
        }
        this.haveInitializationData = false;
    }

    protected SsaSubtitle decode(byte[] bytes, int length, boolean reset) {
        ArrayList<Cue> cues = new ArrayList();
        LongArray cueTimesUs = new LongArray();
        ParsableByteArray data = new ParsableByteArray(bytes, length);
        if (!this.haveInitializationData) {
            parseHeader(data);
        }
        parseEventBody(data, cues, cueTimesUs);
        Cue[] cuesArray = new Cue[cues.size()];
        cues.toArray(cuesArray);
        return new SsaSubtitle(cuesArray, cueTimesUs.toArray());
    }

    private void parseHeader(ParsableByteArray data) {
        String currentLine;
        do {
            currentLine = data.readLine();
            if (currentLine == null) {
                return;
            }
        } while (!currentLine.startsWith("[Events]"));
    }

    private void parseEventBody(ParsableByteArray data, List<Cue> cues, LongArray cueTimesUs) {
        while (true) {
            String currentLine = data.readLine();
            if (currentLine == null) {
                return;
            }
            if (!this.haveInitializationData && currentLine.startsWith(FORMAT_LINE_PREFIX)) {
                parseFormatLine(currentLine);
            } else if (currentLine.startsWith(DIALOGUE_LINE_PREFIX)) {
                parseDialogueLine(currentLine, cues, cueTimesUs);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void parseFormatLine(java.lang.String r7) {
        /*
        r6 = this;
        r4 = -1;
        r3 = "Format: ";
        r3 = r3.length();
        r3 = r7.substring(r3);
        r5 = ",";
        r2 = android.text.TextUtils.split(r3, r5);
        r3 = r2.length;
        r6.formatKeyCount = r3;
        r6.formatStartIndex = r4;
        r6.formatEndIndex = r4;
        r6.formatTextIndex = r4;
        r0 = 0;
    L_0x001d:
        r3 = r6.formatKeyCount;
        if (r0 >= r3) goto L_0x0063;
    L_0x0021:
        r3 = r2[r0];
        r3 = r3.trim();
        r1 = org.telegram.messenger.exoplayer2.util.Util.toLowerInvariant(r3);
        r3 = r1.hashCode();
        switch(r3) {
            case 100571: goto L_0x0044;
            case 3556653: goto L_0x004f;
            case 109757538: goto L_0x0039;
            default: goto L_0x0032;
        };
    L_0x0032:
        r3 = r4;
    L_0x0033:
        switch(r3) {
            case 0: goto L_0x005a;
            case 1: goto L_0x005d;
            case 2: goto L_0x0060;
            default: goto L_0x0036;
        };
    L_0x0036:
        r0 = r0 + 1;
        goto L_0x001d;
    L_0x0039:
        r3 = "start";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x0032;
    L_0x0042:
        r3 = 0;
        goto L_0x0033;
    L_0x0044:
        r3 = "end";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x0032;
    L_0x004d:
        r3 = 1;
        goto L_0x0033;
    L_0x004f:
        r3 = "text";
        r3 = r1.equals(r3);
        if (r3 == 0) goto L_0x0032;
    L_0x0058:
        r3 = 2;
        goto L_0x0033;
    L_0x005a:
        r6.formatStartIndex = r0;
        goto L_0x0036;
    L_0x005d:
        r6.formatEndIndex = r0;
        goto L_0x0036;
    L_0x0060:
        r6.formatTextIndex = r0;
        goto L_0x0036;
    L_0x0063:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.exoplayer2.text.ssa.SsaDecoder.parseFormatLine(java.lang.String):void");
    }

    private void parseDialogueLine(String dialogueLine, List<Cue> cues, LongArray cueTimesUs) {
        if (this.formatKeyCount == 0) {
            Log.w(TAG, "Skipping dialogue line before format: " + dialogueLine);
            return;
        }
        String[] lineValues = dialogueLine.substring(DIALOGUE_LINE_PREFIX.length()).split(",", this.formatKeyCount);
        long startTimeUs = parseTimecodeUs(lineValues[this.formatStartIndex]);
        if (startTimeUs == C0907C.TIME_UNSET) {
            Log.w(TAG, "Skipping invalid timing: " + dialogueLine);
            return;
        }
        long endTimeUs = C0907C.TIME_UNSET;
        String endTimeString = lineValues[this.formatEndIndex];
        if (!endTimeString.trim().isEmpty()) {
            endTimeUs = parseTimecodeUs(endTimeString);
            if (endTimeUs == C0907C.TIME_UNSET) {
                Log.w(TAG, "Skipping invalid timing: " + dialogueLine);
                return;
            }
        }
        cues.add(new Cue(lineValues[this.formatTextIndex].replaceAll("\\{.*?\\}", "").replaceAll("\\\\N", LogCollector.LINE_SEPARATOR).replaceAll("\\\\n", LogCollector.LINE_SEPARATOR)));
        cueTimesUs.add(startTimeUs);
        if (endTimeUs != C0907C.TIME_UNSET) {
            cues.add(null);
            cueTimesUs.add(endTimeUs);
        }
    }

    public static long parseTimecodeUs(String timeString) {
        Matcher matcher = SSA_TIMECODE_PATTERN.matcher(timeString);
        if (matcher.matches()) {
            return (((((Long.parseLong(matcher.group(1)) * 60) * 60) * C0907C.MICROS_PER_SECOND) + ((Long.parseLong(matcher.group(2)) * 60) * C0907C.MICROS_PER_SECOND)) + (Long.parseLong(matcher.group(3)) * C0907C.MICROS_PER_SECOND)) + (Long.parseLong(matcher.group(4)) * 10000);
        }
        return C0907C.TIME_UNSET;
    }
}
