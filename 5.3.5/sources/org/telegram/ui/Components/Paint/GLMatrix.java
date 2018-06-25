package org.telegram.ui.Components.Paint;

import android.graphics.Matrix;

public class GLMatrix {
    public static float[] LoadOrtho(float left, float right, float bottom, float top, float near, float far) {
        float t_b = top - bottom;
        float f_n = far - near;
        float tx = (-(right + left)) / (right - left);
        float ty = (-(top + bottom)) / (top - bottom);
        float tz = (-(far + near)) / (far - near);
        return new float[]{2.0f / (right - left), 0.0f, 0.0f, 0.0f, 0.0f, 2.0f / t_b, 0.0f, 0.0f, 0.0f, 0.0f, -2.0f / f_n, 0.0f, tx, ty, tz, 1.0f};
    }

    public static float[] LoadGraphicsMatrix(Matrix matrix) {
        m = new float[16];
        v = new float[9];
        matrix.getValues(v);
        m[0] = v[0];
        m[1] = v[1];
        m[2] = 0.0f;
        m[3] = 0.0f;
        m[4] = v[3];
        m[5] = v[4];
        m[6] = 0.0f;
        m[7] = 0.0f;
        m[8] = 0.0f;
        m[9] = 0.0f;
        m[10] = 1.0f;
        m[11] = 0.0f;
        m[12] = v[2];
        m[13] = v[5];
        m[14] = 0.0f;
        m[15] = 1.0f;
        return m;
    }

    public static float[] MultiplyMat4f(float[] a, float[] b) {
        return new float[]{(((a[0] * b[0]) + (a[4] * b[1])) + (a[8] * b[2])) + (a[12] * b[3]), (((a[1] * b[0]) + (a[5] * b[1])) + (a[9] * b[2])) + (a[13] * b[3]), (((a[2] * b[0]) + (a[6] * b[1])) + (a[10] * b[2])) + (a[14] * b[3]), (((a[3] * b[0]) + (a[7] * b[1])) + (a[11] * b[2])) + (a[15] * b[3]), (((a[0] * b[4]) + (a[4] * b[5])) + (a[8] * b[6])) + (a[12] * b[7]), (((a[1] * b[4]) + (a[5] * b[5])) + (a[9] * b[6])) + (a[13] * b[7]), (((a[2] * b[4]) + (a[6] * b[5])) + (a[10] * b[6])) + (a[14] * b[7]), (((a[3] * b[4]) + (a[7] * b[5])) + (a[11] * b[6])) + (a[15] * b[7]), (((a[0] * b[8]) + (a[4] * b[9])) + (a[8] * b[10])) + (a[12] * b[11]), (((a[1] * b[8]) + (a[5] * b[9])) + (a[9] * b[10])) + (a[13] * b[11]), (((a[2] * b[8]) + (a[6] * b[9])) + (a[10] * b[10])) + (a[14] * b[11]), (((a[3] * b[8]) + (a[7] * b[9])) + (a[11] * b[10])) + (a[15] * b[11]), (((a[0] * b[12]) + (a[4] * b[13])) + (a[8] * b[14])) + (a[12] * b[15]), (((a[1] * b[12]) + (a[5] * b[13])) + (a[9] * b[14])) + (a[13] * b[15]), (((a[2] * b[12]) + (a[6] * b[13])) + (a[10] * b[14])) + (a[14] * b[15]), (((a[3] * b[12]) + (a[7] * b[13])) + (a[11] * b[14])) + (a[15] * b[15])};
    }
}
