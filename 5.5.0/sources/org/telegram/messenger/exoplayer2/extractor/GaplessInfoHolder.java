package org.telegram.messenger.exoplayer2.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.Metadata.Entry;
import org.telegram.messenger.exoplayer2.metadata.id3.CommentFrame;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder.FramePredicate;

public final class GaplessInfoHolder {
    private static final String GAPLESS_COMMENT_ID = "iTunSMPB";
    private static final Pattern GAPLESS_COMMENT_PATTERN = Pattern.compile("^ [0-9a-fA-F]{8} ([0-9a-fA-F]{8}) ([0-9a-fA-F]{8})");
    public static final FramePredicate GAPLESS_INFO_ID3_FRAME_PREDICATE = new C34771();
    public int encoderDelay = -1;
    public int encoderPadding = -1;

    /* renamed from: org.telegram.messenger.exoplayer2.extractor.GaplessInfoHolder$1 */
    static class C34771 implements FramePredicate {
        C34771() {
        }

        public boolean evaluate(int i, int i2, int i3, int i4, int i5) {
            return i2 == 67 && i3 == 79 && i4 == 77 && (i5 == 77 || i == 2);
        }
    }

    private boolean setFromComment(String str, String str2) {
        if (!GAPLESS_COMMENT_ID.equals(str)) {
            return false;
        }
        Matcher matcher = GAPLESS_COMMENT_PATTERN.matcher(str2);
        if (!matcher.find()) {
            return false;
        }
        try {
            int parseInt = Integer.parseInt(matcher.group(1), 16);
            int parseInt2 = Integer.parseInt(matcher.group(2), 16);
            if (parseInt <= 0 && parseInt2 <= 0) {
                return false;
            }
            this.encoderDelay = parseInt;
            this.encoderPadding = parseInt2;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean hasGaplessInfo() {
        return (this.encoderDelay == -1 || this.encoderPadding == -1) ? false : true;
    }

    public boolean setFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Entry entry = metadata.get(i);
            if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                if (setFromComment(commentFrame.description, commentFrame.text)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean setFromXingHeaderValue(int i) {
        int i2 = i >> 12;
        int i3 = i & 4095;
        if (i2 <= 0 && i3 <= 0) {
            return false;
        }
        this.encoderDelay = i2;
        this.encoderPadding = i3;
        return true;
    }
}
