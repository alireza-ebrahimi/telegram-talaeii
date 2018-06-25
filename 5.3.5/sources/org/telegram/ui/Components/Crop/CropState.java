package org.telegram.ui.Components.Crop;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class CropState {
    private float height;
    private Matrix matrix = new Matrix();
    private float minimumScale;
    private float rotation = 0.0f;
    private float scale = 1.0f;
    private float[] values = new float[9];
    private float width;
    /* renamed from: x */
    private float f94x = 0.0f;
    /* renamed from: y */
    private float f95y = 0.0f;

    public CropState(Bitmap bitmap) {
        this.width = (float) bitmap.getWidth();
        this.height = (float) bitmap.getHeight();
    }

    private void updateValues() {
        this.matrix.getValues(this.values);
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void translate(float x, float y) {
        this.f94x += x;
        this.f95y += y;
        this.matrix.postTranslate(x, y);
    }

    public float getX() {
        updateValues();
        float[] fArr = this.values;
        Matrix matrix = this.matrix;
        return fArr[2];
    }

    public float getY() {
        updateValues();
        float[] fArr = this.values;
        Matrix matrix = this.matrix;
        return fArr[5];
    }

    public void scale(float s, float pivotX, float pivotY) {
        this.scale *= s;
        this.matrix.postScale(s, s, pivotX, pivotY);
    }

    public float getScale() {
        return this.scale;
    }

    public void rotate(float angle, float pivotX, float pivotY) {
        this.rotation += angle;
        this.matrix.postRotate(angle, pivotX, pivotY);
    }

    public float getRotation() {
        return this.rotation;
    }

    public void reset(CropAreaView areaView) {
        this.matrix.reset();
        this.f94x = 0.0f;
        this.f95y = 0.0f;
        this.rotation = 0.0f;
        this.minimumScale = areaView.getCropWidth() / this.width;
        this.scale = this.minimumScale;
        this.matrix.postScale(this.scale, this.scale);
    }

    public void getConcatMatrix(Matrix toMatrix) {
        toMatrix.postConcat(this.matrix);
    }

    public Matrix getMatrix() {
        Matrix m = new Matrix();
        m.set(this.matrix);
        return m;
    }
}
