package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class GLMatrix {
    public static float[] LoadGraphicsMatrix(Matrix matrix) {
        r0 = new float[16];
        r1 = new float[9];
        matrix.getValues(r1);
        r0[0] = r1[0];
        r0[1] = r1[1];
        r0[2] = BitmapDescriptorFactory.HUE_RED;
        r0[3] = BitmapDescriptorFactory.HUE_RED;
        r0[4] = r1[3];
        r0[5] = r1[4];
        r0[6] = BitmapDescriptorFactory.HUE_RED;
        r0[7] = BitmapDescriptorFactory.HUE_RED;
        r0[8] = BitmapDescriptorFactory.HUE_RED;
        r0[9] = BitmapDescriptorFactory.HUE_RED;
        r0[10] = 1.0f;
        r0[11] = BitmapDescriptorFactory.HUE_RED;
        r0[12] = r1[2];
        r0[13] = r1[5];
        r0[14] = BitmapDescriptorFactory.HUE_RED;
        r0[15] = 1.0f;
        return r0;
    }

    public static float[] LoadOrtho(float f, float f2, float f3, float f4, float f5, float f6) {
        float f7 = f4 - f3;
        float f8 = f6 - f5;
        float f9 = (-(f2 + f)) / (f2 - f);
        float f10 = (-(f4 + f3)) / (f4 - f3);
        float f11 = (-(f6 + f5)) / (f6 - f5);
        return new float[]{2.0f / (f2 - f), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 2.0f / f7, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, -2.0f / f8, BitmapDescriptorFactory.HUE_RED, f9, f10, f11, 1.0f};
    }

    public static float[] MultiplyMat4f(float[] fArr, float[] fArr2) {
        return new float[]{(((fArr[0] * fArr2[0]) + (fArr[4] * fArr2[1])) + (fArr[8] * fArr2[2])) + (fArr[12] * fArr2[3]), (((fArr[1] * fArr2[0]) + (fArr[5] * fArr2[1])) + (fArr[9] * fArr2[2])) + (fArr[13] * fArr2[3]), (((fArr[2] * fArr2[0]) + (fArr[6] * fArr2[1])) + (fArr[10] * fArr2[2])) + (fArr[14] * fArr2[3]), (((fArr[3] * fArr2[0]) + (fArr[7] * fArr2[1])) + (fArr[11] * fArr2[2])) + (fArr[15] * fArr2[3]), (((fArr[0] * fArr2[4]) + (fArr[4] * fArr2[5])) + (fArr[8] * fArr2[6])) + (fArr[12] * fArr2[7]), (((fArr[1] * fArr2[4]) + (fArr[5] * fArr2[5])) + (fArr[9] * fArr2[6])) + (fArr[13] * fArr2[7]), (((fArr[2] * fArr2[4]) + (fArr[6] * fArr2[5])) + (fArr[10] * fArr2[6])) + (fArr[14] * fArr2[7]), (((fArr[3] * fArr2[4]) + (fArr[7] * fArr2[5])) + (fArr[11] * fArr2[6])) + (fArr[15] * fArr2[7]), (((fArr[0] * fArr2[8]) + (fArr[4] * fArr2[9])) + (fArr[8] * fArr2[10])) + (fArr[12] * fArr2[11]), (((fArr[1] * fArr2[8]) + (fArr[5] * fArr2[9])) + (fArr[9] * fArr2[10])) + (fArr[13] * fArr2[11]), (((fArr[2] * fArr2[8]) + (fArr[6] * fArr2[9])) + (fArr[10] * fArr2[10])) + (fArr[14] * fArr2[11]), (((fArr[3] * fArr2[8]) + (fArr[7] * fArr2[9])) + (fArr[11] * fArr2[10])) + (fArr[15] * fArr2[11]), (((fArr[0] * fArr2[12]) + (fArr[4] * fArr2[13])) + (fArr[8] * fArr2[14])) + (fArr[12] * fArr2[15]), (((fArr[1] * fArr2[12]) + (fArr[5] * fArr2[13])) + (fArr[9] * fArr2[14])) + (fArr[13] * fArr2[15]), (((fArr[2] * fArr2[12]) + (fArr[6] * fArr2[13])) + (fArr[10] * fArr2[14])) + (fArr[14] * fArr2[15]), (((fArr[3] * fArr2[12]) + (fArr[7] * fArr2[13])) + (fArr[11] * fArr2[14])) + (fArr[15] * fArr2[15])};
    }
}
