package utils.volley.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ByteArrayPool {
    protected static final Comparator<byte[]> BUF_COMPARATOR = new C53631();
    private final List<byte[]> mBuffersByLastUse = new ArrayList();
    private final List<byte[]> mBuffersBySize = new ArrayList(64);
    private int mCurrentSize = 0;
    private final int mSizeLimit;

    /* renamed from: utils.volley.toolbox.ByteArrayPool$1 */
    static class C53631 implements Comparator<byte[]> {
        C53631() {
        }

        public int compare(byte[] bArr, byte[] bArr2) {
            return bArr.length - bArr2.length;
        }
    }

    public ByteArrayPool(int i) {
        this.mSizeLimit = i;
    }

    private synchronized void trim() {
        while (this.mCurrentSize > this.mSizeLimit) {
            byte[] bArr = (byte[]) this.mBuffersByLastUse.remove(0);
            this.mBuffersBySize.remove(bArr);
            this.mCurrentSize -= bArr.length;
        }
    }

    public synchronized byte[] getBuf(int i) {
        byte[] bArr;
        for (int i2 = 0; i2 < this.mBuffersBySize.size(); i2++) {
            bArr = (byte[]) this.mBuffersBySize.get(i2);
            if (bArr.length >= i) {
                this.mCurrentSize -= bArr.length;
                this.mBuffersBySize.remove(i2);
                this.mBuffersByLastUse.remove(bArr);
                break;
            }
        }
        bArr = new byte[i];
        return bArr;
    }

    public synchronized void returnBuf(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length <= this.mSizeLimit) {
                this.mBuffersByLastUse.add(bArr);
                int binarySearch = Collections.binarySearch(this.mBuffersBySize, bArr, BUF_COMPARATOR);
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 1;
                }
                this.mBuffersBySize.add(binarySearch, bArr);
                this.mCurrentSize += bArr.length;
                trim();
            }
        }
    }
}
