package org.telegram.ui.Components.Paint;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.google.android.gms.gcm.Task;
import org.telegram.ui.Components.Size;

public class Texture {
    private Bitmap bitmap;
    private int texture;

    public Texture(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void cleanResources(boolean recycleBitmap) {
        if (this.texture != 0) {
            GLES20.glDeleteTextures(1, new int[]{this.texture}, 0);
            this.texture = 0;
            if (recycleBitmap) {
                this.bitmap.recycle();
            }
        }
    }

    private boolean isPOT(int x) {
        return ((x + -1) & x) == 0;
    }

    public int texture() {
        int i = 9729;
        if (this.texture != 0) {
            return this.texture;
        }
        if (this.bitmap.isRecycled()) {
            return 0;
        }
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        this.texture = textures[0];
        GLES20.glBindTexture(3553, this.texture);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
        if (null != null) {
            i = 9987;
        }
        GLES20.glTexParameteri(3553, 10241, i);
        GLUtils.texImage2D(3553, 0, this.bitmap, 0);
        if (null != null) {
            GLES20.glGenerateMipmap(3553);
        }
        Utils.HasGLError();
        return this.texture;
    }

    public static int generateTexture(Size size) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int texture = textures[0];
        GLES20.glBindTexture(3553, texture);
        GLES20.glTexParameteri(3553, 10242, 33071);
        GLES20.glTexParameteri(3553, 10243, 33071);
        GLES20.glTexParameteri(3553, Task.EXTRAS_LIMIT_BYTES, 9729);
        GLES20.glTexParameteri(3553, 10241, 9729);
        GLES20.glTexImage2D(3553, 0, 6408, (int) size.width, (int) size.height, 0, 6408, 5121, null);
        return texture;
    }
}
