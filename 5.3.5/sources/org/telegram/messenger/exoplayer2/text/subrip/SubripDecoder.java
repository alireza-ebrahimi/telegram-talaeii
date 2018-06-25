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

    protected SubripSubtitle decode(byte[] bytes, int length, boolean reset) {
        ArrayList<Cue> cues = new ArrayList();
        LongArray cueTimesUs = new LongArray();
        ParsableByteArray subripData = new ParsableByteArray(bytes, length);
        while (true) {
            String currentLine = subripData.readLine();
            if (currentLine == null) {
                break;
            } else if (currentLine.length() != 0) {
                try {
                    Integer.parseInt(currentLine);
                    boolean haveEndTimecode = false;
                    currentLine = subripData.readLine();
                    if (currentLine == null) {
                        break;
                    }
                    Matcher matcher = SUBRIP_TIMING_LINE.matcher(currentLine);
                    if (matcher.matches()) {
                        cueTimesUs.add(parseTimecode(matcher, 1));
                        if (!TextUtils.isEmpty(matcher.group(6))) {
                            haveEndTimecode = true;
                            cueTimesUs.add(parseTimecode(matcher, 6));
                        }
                        this.textBuilder.setLength(0);
                        while (true) {
                            currentLine = subripData.readLine();
                            if (TextUtils.isEmpty(currentLine)) {
                                break;
                            }
                            if (this.textBuilder.length() > 0) {
                                this.textBuilder.append("<br>");
                            }
                            this.textBuilder.append(currentLine.trim());
                        }
                        cues.add(new Cue(Html.fromHtml(this.textBuilder.toString())));
                        if (haveEndTimecode) {
                            cues.add(null);
                        }
                    } else {
                        Log.w(TAG, "Skipping invalid timing: " + currentLine);
                    }
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Skipping invalid index: " + currentLine);
                }
            }
        }
        Log.w(TAG, "Unexpected end");
        Cue[] cuesArray = new Cue[cues.size()];
        cues.toArray(cuesArray);
        return new SubripSubtitle(cuesArray, cueTimesUs.toArray());
    }

    private static long parseTimecode(Matcher matcher, int groupOffset) {
        return ((((((Long.parseLong(matcher.group(groupOffset + 1)) * 60) * 60) * 1000) + ((Long.parseLong(matcher.group(groupOffset + 2)) * 60) * 1000)) + (Long.parseLong(matcher.group(groupOffset + 3)) * 1000)) + Long.parseLong(matcher.group(groupOffset + 4))) * 1000;
    }
}
