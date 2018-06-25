package org.telegram.messenger;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Locale;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;

public class VideoEditedInfo {
    public int bitrate;
    public TLRPC$InputEncryptedFile encryptedFile;
    public long endTime;
    public long estimatedDuration;
    public long estimatedSize;
    public TLRPC$InputFile file;
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

    public boolean parseString(String string) {
        if (string.length() < 6) {
            return false;
        }
        try {
            String[] args = string.split(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            if (args.length >= 10) {
                this.startTime = Long.parseLong(args[1]);
                this.endTime = Long.parseLong(args[2]);
                this.rotationValue = Integer.parseInt(args[3]);
                this.originalWidth = Integer.parseInt(args[4]);
                this.originalHeight = Integer.parseInt(args[5]);
                this.bitrate = Integer.parseInt(args[6]);
                this.resultWidth = Integer.parseInt(args[7]);
                this.resultHeight = Integer.parseInt(args[8]);
                for (int a = 9; a < args.length; a++) {
                    if (this.originalPath == null) {
                        this.originalPath = args[a];
                    } else {
                        this.originalPath += EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + args[a];
                    }
                }
            }
            return true;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    public boolean needConvert() {
        return !this.roundVideo || (this.roundVideo && (this.startTime > 0 || !(this.endTime == -1 || this.endTime == this.estimatedDuration)));
    }
}
