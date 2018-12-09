package org.telegram.customization.compression.lz4;

import java.io.InputStream;
import java.io.OutputStream;
import org.telegram.ui.ActionBar.Theme;

public class LZ4StreamHelper {
    static int readLength(InputStream inputStream) {
        int read = inputStream.read();
        int read2 = inputStream.read();
        int read3 = inputStream.read();
        int read4 = inputStream.read();
        return (-1 == read || -1 == read2 || -1 == read3 || -1 == read4) ? -1 : (((read << 24) | (read2 << 16)) | (read3 << 8)) | read4;
    }

    static void writeLength(int i, OutputStream outputStream) {
        int i2 = (16711680 & i) >> 16;
        int i3 = (65280 & i) >> 8;
        int i4 = -16776961 & i;
        outputStream.write((Theme.ACTION_BAR_VIDEO_EDIT_COLOR & i) >> 24);
        outputStream.write(i2);
        outputStream.write(i3);
        outputStream.write(i4);
    }
}
