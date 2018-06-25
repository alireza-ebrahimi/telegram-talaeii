package org.telegram.messenger.audioinfo.mp3;

import android.support.v4.media.TransportMediator;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.CharEncoding;
import org.telegram.messenger.audioinfo.AudioInfo;
import tellh.com.stickyheaderview_rv.BuildConfig;

public class ID3v1Info extends AudioInfo {
    public static boolean isID3v1StartPosition(InputStream input) throws IOException {
        input.mark(3);
        try {
            boolean z = input.read() == 84 && input.read() == 65 && input.read() == 71;
            input.reset();
            return z;
        } catch (Throwable th) {
            input.reset();
        }
    }

    public ID3v1Info(InputStream input) throws IOException {
        if (isID3v1StartPosition(input)) {
            this.brand = "ID3";
            this.version = BuildConfig.VERSION_NAME;
            byte[] bytes = readBytes(input, 128);
            this.title = extractString(bytes, 3, 30);
            this.artist = extractString(bytes, 33, 30);
            this.album = extractString(bytes, 63, 30);
            try {
                this.year = Short.parseShort(extractString(bytes, 93, 4));
            } catch (NumberFormatException e) {
                this.year = (short) 0;
            }
            this.comment = extractString(bytes, 97, 30);
            ID3v1Genre id3v1Genre = ID3v1Genre.getGenre(bytes[127]);
            if (id3v1Genre != null) {
                this.genre = id3v1Genre.getDescription();
            }
            if (bytes[125] == (byte) 0 && bytes[TransportMediator.KEYCODE_MEDIA_PLAY] != (byte) 0) {
                this.version = com.github.vivchar.viewpagerindicator.BuildConfig.VERSION_NAME;
                this.track = (short) (bytes[TransportMediator.KEYCODE_MEDIA_PLAY] & 255);
            }
        }
    }

    byte[] readBytes(InputStream input, int len) throws IOException {
        int total = 0;
        byte[] bytes = new byte[len];
        while (total < len) {
            int current = input.read(bytes, total, len - total);
            if (current > 0) {
                total += current;
            } else {
                throw new EOFException();
            }
        }
        return bytes;
    }

    String extractString(byte[] bytes, int offset, int length) {
        try {
            String text = new String(bytes, offset, length, CharEncoding.ISO_8859_1);
            int zeroIndex = text.indexOf(0);
            if (zeroIndex < 0) {
                return text;
            }
            return text.substring(0, zeroIndex);
        } catch (Exception e) {
            return "";
        }
    }
}
