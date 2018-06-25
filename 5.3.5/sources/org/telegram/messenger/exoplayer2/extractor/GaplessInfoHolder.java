package org.telegram.messenger.exoplayer2.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
import org.telegram.messenger.exoplayer2.metadata.id3.CommentFrame;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder.FramePredicate;

public final class GaplessInfoHolder {
    private static final String GAPLESS_COMMENT_ID = "iTunSMPB";
    private static final Pattern GAPLESS_COMMENT_PATTERN = Pattern.compile("^ [0-9a-fA-F]{8} ([0-9a-fA-F]{8}) ([0-9a-fA-F]{8})");
    public static final FramePredicate GAPLESS_INFO_ID3_FRAME_PREDICATE = new GaplessInfoHolder$1();
    public int encoderDelay = -1;
    public int encoderPadding = -1;

    public boolean setFromXingHeaderValue(int value) {
        int encoderDelay = value >> 12;
        int encoderPadding = value & 4095;
        if (encoderDelay <= 0 && encoderPadding <= 0) {
            return false;
        }
        this.encoderDelay = encoderDelay;
        this.encoderPadding = encoderPadding;
        return true;
    }

    public boolean setFromMetadata(Metadata metadata) {
        for (int i = 0; i < metadata.length(); i++) {
            Metadata$Entry entry = metadata.get(i);
            if (entry instanceof CommentFrame) {
                CommentFrame commentFrame = (CommentFrame) entry;
                if (setFromComment(commentFrame.description, commentFrame.text)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean setFromComment(String name, String data) {
        if (!GAPLESS_COMMENT_ID.equals(name)) {
            return false;
        }
        Matcher matcher = GAPLESS_COMMENT_PATTERN.matcher(data);
        if (!matcher.find()) {
            return false;
        }
        try {
            int encoderDelay = Integer.parseInt(matcher.group(1), 16);
            int encoderPadding = Integer.parseInt(matcher.group(2), 16);
            if (encoderDelay <= 0 && encoderPadding <= 0) {
                return false;
            }
            this.encoderDelay = encoderDelay;
            this.encoderPadding = encoderPadding;
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean hasGaplessInfo() {
        return (this.encoderDelay == -1 || this.encoderPadding == -1) ? false : true;
    }
}
