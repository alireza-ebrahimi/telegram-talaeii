package org.telegram.messenger.exoplayer2.text.subrip;

import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.util.LongArray;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class SubripDecoder extends SimpleSubtitleDecoder {
    private static final String SUBRIP_TIMECODE = "(?:(\\d+):)?(\\d+):(\\d+),(\\d+)";
    private static final Pattern SUBRIP_TIMING_LINE = Pattern.compile("\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))\\s*-->\\s*((?:(\\d+):)?(\\d+):(\\d+),(\\d+))?\\s*");
    private static final String TAG = "SubripDecoder";
    private final StringBuilder textBuilder = new StringBuilder();

    public SubripDecoder() {
        super(TAG);
    }

    private static long parseTimecode(Matcher matcher, int i) {
        return ((((((Long.parseLong(matcher.group(i + 1)) * 60) * 60) * 1000) + ((Long.parseLong(matcher.group(i + 2)) * 60) * 1000)) + (Long.parseLong(matcher.group(i + 3)) * 1000)) + Long.parseLong(matcher.group(i + 4))) * 1000;
    }

    protected SubripSubtitle decode(byte[] bArr, int i, boolean z) {
        ArrayList arrayList = new ArrayList();
        LongArray longArray = new LongArray();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        while (true) {
            String readLine = parsableByteArray.readLine();
            if (readLine == null) {
                break;
            } else if (readLine.length() != 0) {
                try {
                    Integer.parseInt(readLine);
                    Object readLine2 = parsableByteArray.readLine();
                    if (readLine2 == null) {
                        break;
                    }
                    Matcher matcher = SUBRIP_TIMING_LINE.matcher(readLine2);
                    if (matcher.matches()) {
                        int i2;
                        longArray.add(parseTimecode(matcher, 1));
                        if (TextUtils.isEmpty(matcher.group(6))) {
                            i2 = 0;
                        } else {
                            longArray.add(parseTimecode(matcher, 6));
                            i2 = 1;
                        }
                        this.textBuilder.setLength(0);
                        while (true) {
                            Object readLine3 = parsableByteArray.readLine();
                            if (TextUtils.isEmpty(readLine3)) {
                                break;
                            }
                            if (this.textBuilder.length() > 0) {
                                this.textBuilder.append("<br>");
                            }
                            this.textBuilder.append(readLine3.trim());
                        }
                        arrayList.add(new Cue(Html.fromHtml(this.textBuilder.toString())));
                        if (i2 != 0) {
                            arrayList.add(null);
                        }
                    } else {
                        Log.w(TAG, "Skipping invalid timing: " + readLine2);
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Skipping invalid index: " + readLine);
                }
            }
        }
        Log.w(TAG, "Unexpected end");
        Cue[] cueArr = new Cue[arrayList.size()];
        arrayList.toArray(cueArr);
        return new SubripSubtitle(cueArr, longArray.toArray());
    }
}
