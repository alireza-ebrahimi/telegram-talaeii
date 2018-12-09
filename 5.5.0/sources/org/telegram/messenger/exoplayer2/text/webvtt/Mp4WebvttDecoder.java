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

    private static Cue parseVttCueBox(ParsableByteArray parsableByteArray, Builder builder, int i) {
        builder.reset();
        while (i > 0) {
            if (i < 8) {
                throw new SubtitleDecoderException("Incomplete vtt cue box header found.");
            }
            int readInt = parsableByteArray.readInt();
            int readInt2 = parsableByteArray.readInt();
            int i2 = i - 8;
            readInt -= 8;
            String str = new String(parsableByteArray.data, parsableByteArray.getPosition(), readInt);
            parsableByteArray.skipBytes(readInt);
            i = i2 - readInt;
            if (readInt2 == TYPE_sttg) {
                WebvttCueParser.parseCueSettingsList(str, builder);
            } else if (readInt2 == TYPE_payl) {
                WebvttCueParser.parseCueText(null, str.trim(), builder, Collections.emptyList());
            }
        }
        return builder.build();
    }

    protected Mp4WebvttSubtitle decode(byte[] bArr, int i, boolean z) {
        this.sampleData.reset(bArr, i);
        List arrayList = new ArrayList();
        while (this.sampleData.bytesLeft() > 0) {
            if (this.sampleData.bytesLeft() < 8) {
                throw new SubtitleDecoderException("Incomplete Mp4Webvtt Top Level box header found.");
            }
            int readInt = this.sampleData.readInt();
            if (this.sampleData.readInt() == TYPE_vttc) {
                arrayList.add(parseVttCueBox(this.sampleData, this.builder, readInt - 8));
            } else {
                this.sampleData.skipBytes(readInt - 8);
            }
        }
        return new Mp4WebvttSubtitle(arrayList);
    }
}
