package org.telegram.ui.Components.Crop;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class CropState {
    private float height;
    private Matrix matrix = new Matrix();
    private float minimumScale;
    private float rotation = BitmapDescriptorFactory.HUE_RED;
    private float scale = 1.0f;
    private float[] values = new float[9];
    private float width;
    /* renamed from: x */
    private float f10173x = BitmapDescriptorFactory.HUE_RED;
    /* renamed from: y */
    private float f10174y = BitmapDescriptorFactory.HUE_RED;

    public CropState(Bitmap bitmap) {
        this.width = (float) bitmap.getWidth();
        this.height = (float) bitmap.getHeight();
    }

    private void updateValues() {
        this.matrix.getValues(this.values);
    }

    public void getConcatMatrix(Matrix matrix) {
        matrix.postConcat(this.matrix);
    }

    public float getHeight() {
        return this.height;
    }

    public Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.set(this.matrix);
        return matrix;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScale() {
        return this.scale;
    }

    public float getWidth() {
        return this.width;
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

    public void reset(CropAreaView cropAreaView) {
        this.matrix.reset();
        this.f10173x = BitmapDescriptorFactory.HUE_RED;
        this.f10174y = BitmapDescriptorFactory.HUE_RED;
        this.rotation = BitmapDescriptorFactory.HUE_RED;
        this.minimumScale = cropAreaView.getCropWidth() / this.width;
        this.scale = this.minimumScale;
        this.matrix.postScale(this.scale, this.scale);
    }

    public void rotate(float f, float f2, float f3) {
        this.rotation += f;
        this.matrix.postRotate(f, f2, f3);
    }

    public void scale(float f, float f2, float f3) {
        this.scale *= f;
        this.matrix.postScale(f, f, f2, f3);
    }

    public void translate(float f, float f2) {
        this.f10173x += f;
        this.f10174y += f2;
        this.matrix.postTranslate(f, f2);
    }
}
