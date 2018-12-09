package org.telegram.messenger.exoplayer2.text.dvb;

import java.util.List;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DvbDecoder extends SimpleSubtitleDecoder {
    private final DvbParser parser;

    public DvbDecoder(List<byte[]> list) {
        super("DvbDecoder");
        ParsableByteArray parsableByteArray = new ParsableByteArray((byte[]) list.get(0));
        this.parser = new DvbParser(parsableByteArray.readUnsignedShort(), parsableByteArray.readUnsignedShort());
    }

    protected DvbSubtitle decode(byte[] bArr, int i, boolean z) {
        if (z) {
            this.parser.reset();
        }
        return new DvbSubtitle(this.parser.decode(bArr, i));
    }
}
