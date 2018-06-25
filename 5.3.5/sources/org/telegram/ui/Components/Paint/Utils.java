package org.telegram.ui.Components.Paint;

import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

public class Utils {
    public static void HasGLError() {
        int error = GLES20.glGetError();
        if (error != 0) {
            Log.d("Paint", GLUtils.getEGLErrorString(error));
        }
    }

    public static void RectFIntegral(RectF rect) {
        rect.left = (float) ((int) Math.floor((double) rect.left));
        rect.top = (float) ((int) Math.floor((double) rect.top));
        rect.right = (float) ((int) Math.ceil((double) rect.right));
        rect.bottom = (float) ((int) Math.ceil((double) rect.bottom));
    }
}
