package org.telegram.messenger.exoplayer2.metadata.emsg;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
import org.telegram.messenger.exoplayer2.metadata.MetadataDecoder;
import org.telegram.messenger.exoplayer2.metadata.MetadataInputBuffer;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class EventMessageDecoder implements MetadataDecoder {
    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        ParsableByteArray emsgData = new ParsableByteArray(buffer.array(), buffer.limit());
        String schemeIdUri = emsgData.readNullTerminatedString();
        String value = emsgData.readNullTerminatedString();
        long timescale = emsgData.readUnsignedInt();
        emsgData.skipBytes(4);
        return new Metadata(new Metadata$Entry[]{new EventMessage(schemeIdUri, value, (emsgData.readUnsignedInt() * 1000) / timescale, emsgData.readUnsignedInt(), Arrays.copyOfRange(data, emsgData.getPosition(), size))});
    }
}
