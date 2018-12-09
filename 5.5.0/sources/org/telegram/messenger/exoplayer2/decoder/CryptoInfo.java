package org.telegram.messenger.exoplayer2.decoder;

import android.annotation.TargetApi;
import android.media.MediaCodec.CryptoInfo.Pattern;
import org.telegram.messenger.exoplayer2.util.Util;

public final class CryptoInfo {
    public int clearBlocks;
    public int encryptedBlocks;
    private final android.media.MediaCodec.CryptoInfo frameworkCryptoInfo;
    public byte[] iv;
    public byte[] key;
    public int mode;
    public int[] numBytesOfClearData;
    public int[] numBytesOfEncryptedData;
    public int numSubSamples;
    private final PatternHolderV24 patternHolder;

    @TargetApi(24)
    private static final class PatternHolderV24 {
        private final android.media.MediaCodec.CryptoInfo frameworkCryptoInfo;
        private final Pattern pattern;

        private PatternHolderV24(android.media.MediaCodec.CryptoInfo cryptoInfo) {
            this.frameworkCryptoInfo = cryptoInfo;
            this.pattern = new Pattern(0, 0);
        }

        private void set(int i, int i2) {
            this.pattern.set(i, i2);
            this.frameworkCryptoInfo.setPattern(this.pattern);
        }
    }

    public CryptoInfo() {
        PatternHolderV24 patternHolderV24 = null;
        this.frameworkCryptoInfo = Util.SDK_INT >= 16 ? newFrameworkCryptoInfoV16() : null;
        if (Util.SDK_INT >= 24) {
            patternHolderV24 = new PatternHolderV24(this.frameworkCryptoInfo);
        }
        this.patternHolder = patternHolderV24;
    }

    @TargetApi(16)
    private android.media.MediaCodec.CryptoInfo newFrameworkCryptoInfoV16() {
        return new android.media.MediaCodec.CryptoInfo();
    }

    @TargetApi(16)
    private void updateFrameworkCryptoInfoV16() {
        this.frameworkCryptoInfo.numSubSamples = this.numSubSamples;
        this.frameworkCryptoInfo.numBytesOfClearData = this.numBytesOfClearData;
        this.frameworkCryptoInfo.numBytesOfEncryptedData = this.numBytesOfEncryptedData;
        this.frameworkCryptoInfo.key = this.key;
        this.frameworkCryptoInfo.iv = this.iv;
        this.frameworkCryptoInfo.mode = this.mode;
        if (Util.SDK_INT >= 24) {
            this.patternHolder.set(this.encryptedBlocks, this.clearBlocks);
        }
    }

    @TargetApi(16)
    public android.media.MediaCodec.CryptoInfo getFrameworkCryptoInfoV16() {
        return this.frameworkCryptoInfo;
    }

    public void set(int i, int[] iArr, int[] iArr2, byte[] bArr, byte[] bArr2, int i2, int i3, int i4) {
        this.numSubSamples = i;
        this.numBytesOfClearData = iArr;
        this.numBytesOfEncryptedData = iArr2;
        this.key = bArr;
        this.iv = bArr2;
        this.mode = i2;
        this.encryptedBlocks = i3;
        this.clearBlocks = i4;
        if (Util.SDK_INT >= 16) {
            updateFrameworkCryptoInfoV16();
        }
    }
}
