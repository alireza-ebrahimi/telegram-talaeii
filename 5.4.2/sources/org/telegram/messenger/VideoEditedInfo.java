package org.telegram.messenger;

import java.util.Locale;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;

public class VideoEditedInfo {
    public int bitrate;
    public InputEncryptedFile encryptedFile;
    public long endTime;
    public long estimatedDuration;
    public long estimatedSize;
    public InputFile file;
    public byte[] iv;
    public byte[] key;
    public boolean muted;
    public int originalHeight;
    public String originalPath;
    public int originalWidth;
    public int resultHeight;
    public int resultWidth;
    public int rotationValue;
    public boolean roundVideo;
    public long startTime;

    public String getString() {
        return String.format(Locale.US, "-1_%d_%d_%d_%d_%d_%d_%d_%d_%s", new Object[]{Long.valueOf(this.startTime), Long.valueOf(this.endTime), Integer.valueOf(this.rotationValue), Integer.valueOf(this.originalWidth), Integer.valueOf(this.originalHeight), Integer.valueOf(this.bitrate), Integer.valueOf(this.resultWidth), Integer.valueOf(this.resultHeight), this.originalPath});
    }

    public boolean needConvert() {
        return !this.roundVideo || (this.roundVideo && (this.startTime > 0 || !(this.endTime == -1 || this.endTime == this.estimatedDuration)));
    }

    public boolean parseString(String str) {
        if (str.length() < 6) {
            return false;
        }
        try {
            String[] split = str.split("_");
            if (split.length >= 10) {
                this.startTime = Long.parseLong(split[1]);
                this.endTime = Long.parseLong(split[2]);
                this.rotationValue = Integer.parseInt(split[3]);
                this.originalWidth = Integer.parseInt(split[4]);
                this.originalHeight = Integer.parseInt(split[5]);
                this.bitrate = Integer.parseInt(split[6]);
                this.resultWidth = Integer.parseInt(split[7]);
                this.resultHeight = Integer.parseInt(split[8]);
                for (int i = 9; i < split.length; i++) {
                    if (this.originalPath == null) {
                        this.originalPath = split[i];
                    } else {
                        this.originalPath += "_" + split[i];
                    }
                }
            }
            return true;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return false;
        }
    }
}
