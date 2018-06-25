package org.telegram.messenger.exoplayer2.text.webvtt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.text.Cue;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.text.SubtitleDecoderException;
import org.telegram.messenger.exoplayer2.text.webvtt.WebvttCue.Builder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

public final class Mp4WebvttDecoder extends SimpleSubtitleDecoder {
    private static final int BOX_HEADER_SIZE = 8;
    private static final int TYPE_payl = Util.getIntegerCodeForString("payl");
    private static final int TYPE_sttg = Util.getIntegerCodeForString("sttg");
    private static final int TYPE_vttc = Util.getIntegerCodeForString("vttc");
    private final Builder builder = new Builder();
    private final ParsableByteArray sampleData = new ParsableByteArray();

    public Mp4WebvttDecoder() {
        super("Mp4WebvttDecoder");
    }

    protected Mp4WebvttSubtitle decode(byte[] bytes, int length, boolean reset) throws SubtitleDecoderException {
        this.sampleData.reset(bytes, length);
        List<Cue> resultingCueList = new ArrayList();
        while (this.sampleData.bytesLeft() > 0) {
            if (this.sampleData.bytesLeft() < 8) {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
            int boxSize = this.sampleData.readInt();
            if (this.sampleData.readInt() == TYPE_vttc) {
                resultingCueList.add(parseVttCueBox(this.sampleData, this.builder, boxSize - 8));
            } else {
                this.sampleData.skipBytes(boxSize - 8);
            }
        }
        return new Mp4WebvttSubtitle(resultingCueList);
    }

    private static Cue parseVttCueBox(ParsableByteArray sampleData, Builder builder, int remainingCueBoxBytes) throws SubtitleDecoderException {
        builder.reset();
        while (remainingCueBoxBytes > 0) {
            if (remainingCueBoxBytes < 8) {
                throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
            }
            int boxSize = sampleData.readInt();
            int boxType = sampleData.readInt();
            remainingCueBoxBytes -= 8;
            int payloadLength = boxSize - 8;
            String boxPayload = new String(sampleData.data, sampleData.getPosition(), payloadLength);
            sampleData.skipBytes(payloadLength);
            remainingCueBoxBytes -= payloadLength;
            if (boxType == TYPE_sttg) {
                WebvttCueParser.parseCueSettingsList(boxPayload, builder);
            } else if (boxType == TYPE_payl) {
                WebvttCueParser.parseCueText(null, boxPayload.trim(), builder, Collections.emptyList());
            }
        }
        return builder.build();
    }
}
