package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.util.Log;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.TrackOutput.CryptoData;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class TrackEncryptionBox {
    private static final String TAG = "TrackEncryptionBox";
    public final CryptoData cryptoData;
    public final byte[] defaultInitializationVector;
    public final int initializationVectorSize;
    public final boolean isEncrypted;
    public final String schemeType;

    public TrackEncryptionBox(boolean z, String str, int i, byte[] bArr, int i2, int i3, byte[] bArr2) {
        int i4 = 1;
        int i5 = i == 0 ? 1 : 0;
        if (bArr2 != null) {
            i4 = 0;
        }
        Assertions.checkArgument(i4 ^ i5);
        this.isEncrypted = z;
        this.schemeType = str;
        this.initializationVectorSize = i;
        this.defaultInitializationVector = bArr2;
        this.cryptoData = new CryptoData(schemeToCryptoMode(str), bArr, i2, i3);
    }

    private static int schemeToCryptoMode(String str) {
        if (str == null) {
            return 1;
        }
        int i = -1;
        switch (str.hashCode()) {
            case 3046605:
                if (str.equals(C3446C.CENC_TYPE_cbc1)) {
                    i = 2;
                    break;
                }
                break;
            case 3046671:
                if (str.equals(C3446C.CENC_TYPE_cbcs)) {
                    i = 3;
                    break;
                }
                break;
            case 3049879:
                if (str.equals(C3446C.CENC_TYPE_cenc)) {
                    i = 0;
                    break;
                }
                break;
            case 3049895:
                if (str.equals(C3446C.CENC_TYPE_cens)) {
                    i = 1;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
            case 1:
                return 1;
            case 2:
            case 3:
                return 2;
            default:
                Log.w(TAG, "Unsupported protection scheme type '" + str + "'. Assuming AES-CTR crypto mode.");
                return 1;
        }
    }
}
