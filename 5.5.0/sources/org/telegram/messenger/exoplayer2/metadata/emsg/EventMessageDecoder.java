package org.telegram.messenger.exoplayer2.metadata.emsg;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.MetadataDecoder;
import org.telegram.messenger.exoplayer2.metadata.MetadataInputBuffer;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class EventMessageDecoder implements MetadataDecoder {
    public Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        ByteBuffer byteBuffer = metadataInputBuffer.data;
        ParsableByteArray parsableByteArray = new ParsableByteArray(byteBuffer.array(), byteBuffer.limit());
        String readNullTerminatedString = parsableByteArray.readNullTerminatedString();
        String readNullTerminatedString2 = parsableByteArray.readNullTerminatedString();
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(4);
        return new Metadata(new EventMessage(readNullTerminatedString, readNullTerminatedString2, (parsableByteArray.readUnsignedInt() * 1000) / readUnsignedInt, parsableByteArray.readUnsignedInt(), Arrays.copyOfRange(r1, parsableByteArray.getPosition(), r0)));
    }
}
