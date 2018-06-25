package org.telegram.messenger.exoplayer2.text;

import org.telegram.messenger.exoplayer2.decoder.Decoder;

public interface SubtitleDecoder extends Decoder<SubtitleInputBuffer, SubtitleOutputBuffer, SubtitleDecoderException> {
    void setPositionUs(long j);
}
