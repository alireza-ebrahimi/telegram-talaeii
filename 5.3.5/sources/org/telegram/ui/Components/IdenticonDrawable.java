package org.telegram.ui.Components;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.tgnet.TLRPC$EncryptedChat;

public class IdenticonDrawable extends Drawable {
    private int[] colors = new int[]{-1, -2758925, -13805707, -13657655};
    private byte[] data;
    private Paint paint = new Paint();

    private int getBits(int bitOffset) {
        return (this.data[bitOffset / 8] >> (bitOffset % 8)) & 3;
    }

    public void setEncryptedChat(TLRPC$EncryptedChat encryptedChat) {
        this.data = encryptedChat.key_hash;
        if (this.data == null) {
            byte[] calcAuthKeyHash = AndroidUtilities.calcAuthKeyHash(encryptedChat.auth_key);
            this.data = calcAuthKeyHash;
            encryptedChat.key_hash = calcAuthKeyHash;
        }
        invalidateSelf();
    }

    public void setColors(int[] value) {
        if (this.colors.length != 4) {
            throw new IllegalArgumentException("colors must have length of 4");
        }
        this.colors = value;
        invalidateSelf();
    }

    public void draw(Canvas canvas) {
        if (this.data != null) {
            int bitPointer;
            float rectSize;
            float xOffset;
            float yOffset;
            int iy;
            int ix;
            if (this.data.length == 16) {
                bitPointer = 0;
                rectSize = (float) Math.floor((double) (((float) Math.min(getBounds().width(), getBounds().height())) / 8.0f));
                xOffset = Math.max(0.0f, (((float) getBounds().width()) - (8.0f * rectSize)) / 2.0f);
                yOffset = Math.max(0.0f, (((float) getBounds().height()) - (8.0f * rectSize)) / 2.0f);
                for (iy = 0; iy < 8; iy++) {
                    for (ix = 0; ix < 8; ix++) {
                        int byteValue = getBits(bitPointer);
                        bitPointer += 2;
                        this.paint.setColor(this.colors[Math.abs(byteValue) % 4]);
                        canvas.drawRect(xOffset + (((float) ix) * rectSize), (((float) iy) * rectSize) + yOffset, ((((float) ix) * rectSize) + xOffset) + rectSize, ((((float) iy) * rectSize) + rectSize) + yOffset, this.paint);
                    }
                }
                return;
            }
            bitPointer = 0;
            rectSize = (float) Math.floor((double) (((float) Math.min(getBounds().width(), getBounds().height())) / 12.0f));
            xOffset = Math.max(0.0f, (((float) getBounds().width()) - (12.0f * rectSize)) / 2.0f);
            yOffset = Math.max(0.0f, (((float) getBounds().height()) - (12.0f * rectSize)) / 2.0f);
            for (iy = 0; iy < 12; iy++) {
                for (ix = 0; ix < 12; ix++) {
                    this.paint.setColor(this.colors[Math.abs(getBits(bitPointer)) % 4]);
                    canvas.drawRect(xOffset + (((float) ix) * rectSize), (((float) iy) * rectSize) + yOffset, ((((float) ix) * rectSize) + xOffset) + rectSize, ((((float) iy) * rectSize) + rectSize) + yOffset, this.paint);
                    bitPointer += 2;
                }
            }
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }

    public int getIntrinsicWidth() {
        return AndroidUtilities.dp(32.0f);
    }

    public int getIntrinsicHeight() {
        return AndroidUtilities.dp(32.0f);
    }
}
