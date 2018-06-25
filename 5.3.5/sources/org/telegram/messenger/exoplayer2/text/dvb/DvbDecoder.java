package org.telegram.messenger.exoplayer2.text.dvb;

import java.util.List;
import org.telegram.messenger.exoplayer2.text.SimpleSubtitleDecoder;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class DvbDecoder extends SimpleSubtitleDecoder {
    private final DvbParser parser;

    public DvbDecoder(List<byte[]> initializationData) {
        super("DvbDecoder");
        ParsableByteArray data = new ParsableByteArray((byte[]) initializationData.get(0));
        this.parser = new DvbParser(data.readUnsignedShort(), data.readUnsignedShort());
    }

    protected DvbSubtitle decode(byte[] data, int length, boolean reset) {
        if (reset) {
            this.parser.reset();
        }
        return new DvbSubtitle(this.parser.decode(data, length));
    }
}
