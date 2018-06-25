package org.telegram.messenger.exoplayer2.metadata;

public interface MetadataDecoder {
    Metadata decode(MetadataInputBuffer metadataInputBuffer) throws MetadataDecoderException;
}
