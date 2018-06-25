package org.telegram.messenger.audioinfo.mp3;

import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

public enum ID3v2Encoding {
    ISO_8859_1(Charset.forName(CharEncoding.ISO_8859_1), 1),
    UTF_16(Charset.forName("UTF-16"), 2),
    UTF_16BE(Charset.forName(CharEncoding.UTF_16BE), 2),
    UTF_8(Charset.forName("UTF-8"), 1);
    
    private final Charset charset;
    private final int zeroBytes;

    private ID3v2Encoding(Charset charset, int zeroBytes) {
        this.charset = charset;
        this.zeroBytes = zeroBytes;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public int getZeroBytes() {
        return this.zeroBytes;
    }
}
