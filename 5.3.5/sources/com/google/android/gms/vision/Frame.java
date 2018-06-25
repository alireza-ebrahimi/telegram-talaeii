package com.google.android.gms.vision;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.google.android.gms.common.internal.Hide;
import java.nio.ByteBuffer;

public class Frame {
    public static final int ROTATION_0 = 0;
    public static final int ROTATION_180 = 2;
    public static final int ROTATION_270 = 3;
    public static final int ROTATION_90 = 1;
    private Bitmap mBitmap;
    private Metadata zzlgd;
    private ByteBuffer zzlge;

    public static class Builder {
        private Frame zzlgf = new Frame();

        public Frame build() {
            if (this.zzlgf.zzlge != null || this.zzlgf.mBitmap != null) {
                return this.zzlgf;
            }
            throw new IllegalStateException("Missing image data.  Call either setBitmap or setImageData to specify the image");
        }

        public Builder setBitmap(Bitmap bitmap) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            this.zzlgf.mBitmap = bitmap;
            Metadata metadata = this.zzlgf.getMetadata();
            metadata.zzalt = width;
            metadata.zzalu = height;
            return this;
        }

        public Builder setId(int i) {
            this.zzlgf.getMetadata().mId = i;
            return this;
        }

        public Builder setImageData(ByteBuffer byteBuffer, int i, int i2, int i3) {
            if (byteBuffer == null) {
                throw new IllegalArgumentException("Null image data supplied.");
            } else if (byteBuffer.capacity() < i * i2) {
                throw new IllegalArgumentException("Invalid image data size.");
            } else {
                switch (i3) {
                    case 16:
                    case 17:
                    case 842094169:
                        this.zzlgf.zzlge = byteBuffer;
                        Metadata metadata = this.zzlgf.getMetadata();
                        metadata.zzalt = i;
                        metadata.zzalu = i2;
                        metadata.format = i3;
                        return this;
                    default:
                        throw new IllegalArgumentException("Unsupported image format: " + i3);
                }
            }
        }

        public Builder setRotation(int i) {
            this.zzlgf.getMetadata().zzcma = i;
            return this;
        }

        public Builder setTimestampMillis(long j) {
            this.zzlgf.getMetadata().zziee = j;
            return this;
        }
    }

    public static class Metadata {
        private int format = -1;
        private int mId;
        private int zzalt;
        private int zzalu;
        private int zzcma;
        private long zziee;

        public Metadata(Metadata metadata) {
            this.zzalt = metadata.getWidth();
            this.zzalu = metadata.getHeight();
            this.mId = metadata.getId();
            this.zziee = metadata.getTimestampMillis();
            this.zzcma = metadata.getRotation();
        }

        public int getFormat() {
            return this.format;
        }

        public int getHeight() {
            return this.zzalu;
        }

        public int getId() {
            return this.mId;
        }

        public int getRotation() {
            return this.zzcma;
        }

        public long getTimestampMillis() {
            return this.zziee;
        }

        public int getWidth() {
            return this.zzalt;
        }

        @Hide
        public final void zzblk() {
            if (this.zzcma % 2 != 0) {
                int i = this.zzalt;
                this.zzalt = this.zzalu;
                this.zzalu = i;
            }
            this.zzcma = 0;
        }
    }

    private Frame() {
        this.zzlgd = new Metadata();
        this.zzlge = null;
        this.mBitmap = null;
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public ByteBuffer getGrayscaleImageData() {
        int i = 0;
        if (this.mBitmap == null) {
            return this.zzlge;
        }
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        int[] iArr = new int[(width * height)];
        this.mBitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        byte[] bArr = new byte[(width * height)];
        while (i < iArr.length) {
            bArr[i] = (byte) ((int) (((((float) Color.red(iArr[i])) * 0.299f) + (((float) Color.green(iArr[i])) * 0.587f)) + (((float) Color.blue(iArr[i])) * 0.114f)));
            i++;
        }
        return ByteBuffer.wrap(bArr);
    }

    public Metadata getMetadata() {
        return this.zzlgd;
    }
}
