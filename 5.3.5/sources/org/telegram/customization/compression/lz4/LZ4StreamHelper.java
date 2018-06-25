package org.telegram.customization.compression.lz4;

import android.support.v4.view.MotionEventCompat;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LZ4StreamHelper {
    static void writeLength(int length, OutputStream os) throws IOException {
        int b2 = (16711680 & length) >> 16;
        int b3 = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & length) >> 8;
        int b4 = length & -16776961;
        os.write((-16777216 & length) >> 24);
        os.write(b2);
        os.write(b3);
        os.write(b4);
    }

    static int readLength(InputStream is) throws IOException {
        int b1 = is.read();
        int b2 = is.read();
        int b3 = is.read();
        int b4 = is.read();
        if (-1 == b1 || -1 == b2 || -1 == b3 || -1 == b4) {
            return -1;
        }
        return (((b1 << 24) | (b2 << 16)) | (b3 << 8)) | b4;
    }
}
