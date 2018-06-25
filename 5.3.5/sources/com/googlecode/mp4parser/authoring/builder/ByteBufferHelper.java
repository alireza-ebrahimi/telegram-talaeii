package com.googlecode.mp4parser.authoring.builder;

import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ByteBufferHelper {
    public static List<ByteBuffer> mergeAdjacentBuffers(List<ByteBuffer> samples) {
        ArrayList<ByteBuffer> nuSamples = new ArrayList(samples.size());
        for (ByteBuffer buffer : samples) {
            int lastIndex = nuSamples.size() - 1;
            if (lastIndex >= 0 && buffer.hasArray() && ((ByteBuffer) nuSamples.get(lastIndex)).hasArray() && buffer.array() == ((ByteBuffer) nuSamples.get(lastIndex)).array()) {
                if (((ByteBuffer) nuSamples.get(lastIndex)).limit() + ((ByteBuffer) nuSamples.get(lastIndex)).arrayOffset() == buffer.arrayOffset()) {
                    ByteBuffer oldBuffer = (ByteBuffer) nuSamples.remove(lastIndex);
                    nuSamples.add(ByteBuffer.wrap(buffer.array(), oldBuffer.arrayOffset(), oldBuffer.limit() + buffer.limit()).slice());
                }
            }
            if (lastIndex >= 0 && (buffer instanceof MappedByteBuffer) && (nuSamples.get(lastIndex) instanceof MappedByteBuffer) && ((ByteBuffer) nuSamples.get(lastIndex)).limit() == ((ByteBuffer) nuSamples.get(lastIndex)).capacity() - buffer.capacity()) {
                oldBuffer = (ByteBuffer) nuSamples.get(lastIndex);
                oldBuffer.limit(buffer.limit() + oldBuffer.limit());
            } else {
                buffer.reset();
                nuSamples.add(buffer);
            }
        }
        return nuSamples;
    }
}
