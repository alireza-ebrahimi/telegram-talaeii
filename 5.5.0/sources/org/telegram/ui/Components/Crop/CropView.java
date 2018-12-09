package org.telegram.ui.Components.Crop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.Crop.CropGestureDetector.CropGestureListener;

public class CropView extends FrameLayout implements AreaViewListener, CropGestureListener {
    private static final float EPSILON = 1.0E-5f;
    private static final float MAX_SCALE = 30.0f;
    private static final int RESULT_SIDE = 1280;
    private boolean animating = false;
    private CropAreaView areaView;
    private View backView;
    private Bitmap bitmap;
    private float bottomPadding;
    private CropGestureDetector detector;
    private boolean freeform;
    private boolean hasAspectRatioDialog;
    private ImageView imageView;
    private RectF initialAreaRect = new RectF();
    private CropViewListener listener;
    private Matrix presentationMatrix = new Matrix();
    private RectF previousAreaRect = new RectF();
    private float rotationStartScale;
    private CropState state;
    private Matrix tempMatrix = new Matrix();
    private CropRectangle tempRect = new CropRectangle();

    /* renamed from: org.telegram.ui.Components.Crop.CropView$1 */
    class C43851 implements OnPreDrawListener {
        C43851() {
        }

        public boolean onPreDraw() {
            CropView.this.reset();
            CropView.this.imageView.getViewTreeObserver().removeOnPreDrawListener(this);
            return false;
        }
    }

    /* renamed from: org.telegram.ui.Components.Crop.CropView$7 */
    class C43917 implements OnCancelListener {
        C43917() {
        }

        public void onCancel(DialogInterface dialogInterface) {
            CropView.this.hasAspectRatioDialog = false;
        }
    }

    private class CropRectangle {
        float[] coords = new float[8];

        CropRectangle() {
        }

        void applyMatrix(Matrix matrix) {
            matrix.mapPoints(this.coords);
        }

        void getRect(RectF rectF) {
            rectF.set(this.coords[0], this.coords[1], this.coords[2], this.coords[7]);
        }

        void setRect(RectF rectF) {
            this.coords[0] = rectF.left;
            this.coords[1] = rectF.top;
            this.coords[2] = rectF.right;
            this.coords[3] = rectF.top;
            this.coords[4] = rectF.right;
            this.coords[5] = rectF.bottom;
            this.coords[6] = rectF.left;
            this.coords[7] = rectF.bottom;
        }
    }

    private class CropState {
        private float baseRotation;
        private float height;
        private Matrix matrix;
        private float minimumScale;
        private float orientation;
        private float rotation;
        private float scale;
        private float width;
        /* renamed from: x */
        private float f10175x;
        /* renamed from: y */
        private float f10176y;

        private CropState(Bitmap bitmap, int i) {
            this.width = (float) bitmap.getWidth();
            this.height = (float) bitmap.getHeight();
            this.f10175x = BitmapDescriptorFactory.HUE_RED;
            this.f10176y = BitmapDescriptorFactory.HUE_RED;
            this.scale = 1.0f;
            this.baseRotation = (float) i;
            this.rotation = BitmapDescriptorFactory.HUE_RED;
            this.matrix = new Matrix();
        }

        private float getBaseRotation() {
            return this.baseRotation;
        }

        private void getConcatMatrix(Matrix matrix) {
            matrix.postConcat(this.matrix);
        }

        private float getHeight() {
            return this.height;
        }

        private Matrix getMatrix() {
            Matrix matrix = new Matrix();
            matrix.set(this.matrix);
            return matrix;
        }

        private float getMinimumScale() {
            return this.minimumScale;
        }

        private float getOrientation() {
            return this.orientation + this.baseRotation;
        }

        private float getOrientedHeight() {
            return (this.orientation + this.baseRotation) % 180.0f != BitmapDescriptorFactory.HUE_RED ? this.width : this.height;
        }

        private float getOrientedWidth() {
            return (this.orientation + this.baseRotation) % 180.0f != BitmapDescriptorFactory.HUE_RED ? this.height : this.width;
        }

        private float getRotation() {
            return this.rotation;
        }

        private float getScale() {
            return this.scale;
        }

        private float getWidth() {
            return this.width;
        }

        private float getX() {
            return this.f10175x;
        }

        private float getY() {
            return this.f10176y;
        }

        private boolean hasChanges() {
            return Math.abs(this.f10175x) > CropView.EPSILON || Math.abs(this.f10176y) > CropView.EPSILON || Math.abs(this.scale - this.minimumScale) > CropView.EPSILON || Math.abs(this.rotation) > CropView.EPSILON || Math.abs(this.orientation) > CropView.EPSILON;
        }

        private void reset(CropAreaView cropAreaView, float f, boolean z) {
            this.matrix.reset();
            this.f10175x = BitmapDescriptorFactory.HUE_RED;
            this.f10176y = BitmapDescriptorFactory.HUE_RED;
            this.rotation = BitmapDescriptorFactory.HUE_RED;
            this.orientation = f;
            float f2 = (this.orientation + this.baseRotation) % 180.0f != BitmapDescriptorFactory.HUE_RED ? this.height : this.width;
            float f3 = (this.orientation + this.baseRotation) % 180.0f != BitmapDescriptorFactory.HUE_RED ? this.width : this.height;
            if (z) {
                this.minimumScale = cropAreaView.getCropWidth() / f2;
            } else {
                this.minimumScale = Math.max(cropAreaView.getCropWidth() / f2, cropAreaView.getCropHeight() / f3);
            }
            this.scale = this.minimumScale;
            this.matrix.postScale(this.scale, this.scale);
        }

        private void rotate(float f, float f2, float f3) {
            this.rotation += f;
            this.matrix.postRotate(f, f2, f3);
        }

        private void scale(float f, float f2, float f3) {
            this.scale *= f;
            this.matrix.postScale(f, f, f2, f3);
        }

        private void translate(float f, float f2) {
            this.f10175x += f;
            this.f10176y += f2;
            this.matrix.postTranslate(f, f2);
        }
    }

    public interface CropViewListener {
        void onAspectLock(boolean z);

        void onChange(boolean z);
    }

    public CropView(Context context) {
        super(context);
        this.backView = new View(context);
        this.backView.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.backView.setVisibility(4);
        addView(this.backView);
        this.imageView = new ImageView(context);
        this.imageView.setDrawingCacheEnabled(true);
        this.imageView.setScaleType(ScaleType.MATRIX);
        addView(this.imageView);
        this.detector = new CropGestureDetector(context);
        this.detector.setOnGestureListener(this);
        this.areaView = new CropAreaView(context);
        this.areaView.setListener(this);
        addView(this.areaView);
    }

    private void fillAreaView(RectF rectF, boolean z) {
        boolean z2;
        int i = 0;
        final float[] fArr = new float[]{1.0f};
        float max = Math.max(rectF.width() / this.areaView.getCropWidth(), rectF.height() / this.areaView.getCropHeight());
        if (this.state.getScale() * max > 30.0f) {
            max = 30.0f / this.state.getScale();
            z2 = true;
        } else {
            z2 = false;
        }
        if (VERSION.SDK_INT >= 21) {
            i = AndroidUtilities.statusBarHeight;
        }
        final float access$900 = this.state.getOrientedWidth() * ((rectF.centerX() - ((float) (this.imageView.getWidth() / 2))) / this.areaView.getCropWidth());
        final float centerY = ((rectF.centerY() - ((((float) i) + (((float) this.imageView.getHeight()) - this.bottomPadding)) / 2.0f)) / this.areaView.getCropHeight()) * this.state.getOrientedHeight();
        Animator ofFloat = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((((Float) valueAnimator.getAnimatedValue()).floatValue() * (max - 1.0f)) + 1.0f) / fArr[0];
                float[] fArr = fArr;
                fArr[0] = fArr[0] * floatValue;
                CropView.this.state.scale(floatValue, access$900, centerY);
                CropView.this.updateMatrix();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (z2) {
                    CropView.this.fitContentInBounds(false, false, true);
                }
            }
        });
        this.areaView.fill(rectF, ofFloat, true);
        this.initialAreaRect.set(rectF);
    }

    private void fitContentInBounds(boolean z, boolean z2, boolean z3) {
        fitContentInBounds(z, z2, z3, false);
    }

    private void fitContentInBounds(boolean z, boolean z2, boolean z3, boolean z4) {
        float cropWidth = this.areaView.getCropWidth();
        float cropHeight = this.areaView.getCropHeight();
        float access$900 = this.state.getOrientedWidth();
        float access$1000 = this.state.getOrientedHeight();
        float access$1400 = this.state.getRotation();
        float toRadians = (float) Math.toRadians((double) access$1400);
        RectF calculateBoundingBox = calculateBoundingBox(cropWidth, cropHeight, access$1400);
        RectF rectF = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, access$900, access$1000);
        cropWidth = (cropWidth - access$900) / 2.0f;
        float f = (cropHeight - access$1000) / 2.0f;
        cropHeight = this.state.getScale();
        this.tempRect.setRect(rectF);
        Matrix access$1500 = this.state.getMatrix();
        access$1500.preTranslate(cropWidth / cropHeight, f / cropHeight);
        this.tempMatrix.reset();
        this.tempMatrix.setTranslate(rectF.centerX(), rectF.centerY());
        this.tempMatrix.setConcat(this.tempMatrix, access$1500);
        this.tempMatrix.preTranslate(-rectF.centerX(), -rectF.centerY());
        this.tempRect.applyMatrix(this.tempMatrix);
        this.tempMatrix.reset();
        this.tempMatrix.preRotate(-access$1400, access$900 / 2.0f, access$1000 / 2.0f);
        this.tempRect.applyMatrix(this.tempMatrix);
        this.tempRect.getRect(rectF);
        PointF pointF = new PointF(this.state.getX(), this.state.getY());
        if (!rectF.contains(calculateBoundingBox)) {
            cropWidth = (!z || (calculateBoundingBox.width() <= rectF.width() && calculateBoundingBox.height() <= rectF.height())) ? cropHeight : fitScale(rectF, cropHeight, calculateBoundingBox.width() / scaleWidthToMaxSize(calculateBoundingBox, rectF));
            fitTranslation(rectF, calculateBoundingBox, pointF, toRadians);
        } else if (!z2 || this.rotationStartScale <= BitmapDescriptorFactory.HUE_RED) {
            cropWidth = cropHeight;
        } else {
            cropWidth = calculateBoundingBox.width() / scaleWidthToMaxSize(calculateBoundingBox, rectF);
            if (this.state.getScale() * cropWidth < this.rotationStartScale) {
                cropWidth = 1.0f;
            }
            cropWidth = fitScale(rectF, cropHeight, cropWidth);
            fitTranslation(rectF, calculateBoundingBox, pointF, toRadians);
        }
        access$900 = pointF.x - this.state.getX();
        access$1400 = pointF.y - this.state.getY();
        if (z3) {
            toRadians = cropWidth / cropHeight;
            if (Math.abs(toRadians - 1.0f) >= EPSILON || Math.abs(access$900) >= EPSILON || Math.abs(access$1400) >= EPSILON) {
                this.animating = true;
                final float[] fArr = new float[]{1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED};
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
                ofFloat.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                        float f = (access$900 * floatValue) - fArr[1];
                        float[] fArr = fArr;
                        fArr[1] = fArr[1] + f;
                        float f2 = (access$1400 * floatValue) - fArr[2];
                        float[] fArr2 = fArr;
                        fArr2[2] = fArr2[2] + f2;
                        CropView.this.state.translate(f * fArr[0], f2 * fArr[0]);
                        floatValue = ((floatValue * (toRadians - 1.0f)) + 1.0f) / fArr[0];
                        float[] fArr3 = fArr;
                        fArr3[0] = fArr3[0] * floatValue;
                        CropView.this.state.scale(floatValue, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                        CropView.this.updateMatrix();
                    }
                });
                final boolean z5 = z4;
                final boolean z6 = z;
                final boolean z7 = z2;
                final boolean z8 = z3;
                ofFloat.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        CropView.this.animating = false;
                        if (!z5) {
                            CropView.this.fitContentInBounds(z6, z7, z8, true);
                        }
                    }
                });
                ofFloat.setInterpolator(this.areaView.getInterpolator());
                ofFloat.setDuration(z4 ? 100 : 200);
                ofFloat.start();
                return;
            }
            return;
        }
        this.state.translate(access$900, access$1400);
        this.state.scale(cropWidth / cropHeight, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        updateMatrix();
    }

    private float fitScale(RectF rectF, float f, float f2) {
        float width = rectF.width() * f2;
        float height = rectF.height() * f2;
        float width2 = (rectF.width() - width) / 2.0f;
        float height2 = (rectF.height() - height) / 2.0f;
        rectF.set(rectF.left + width2, rectF.top + height2, width + (width2 + rectF.left), height + (rectF.top + height2));
        return f * f2;
    }

    private void fitTranslation(RectF rectF, RectF rectF2, PointF pointF, float f) {
        float f2 = rectF2.left;
        float f3 = rectF2.top;
        float f4 = rectF2.right;
        float f5 = rectF2.bottom;
        if (rectF.left > f2) {
            f4 += rectF.left - f2;
            f2 = rectF.left;
        }
        if (rectF.top > f3) {
            f5 += rectF.top - f3;
            f3 = rectF.top;
        }
        if (rectF.right < f4) {
            f2 += rectF.right - f4;
        }
        if (rectF.bottom < f5) {
            f3 += rectF.bottom - f5;
        }
        f4 = rectF2.centerX() - (f2 + (rectF2.width() / 2.0f));
        f2 = rectF2.centerY() - ((rectF2.height() / 2.0f) + f3);
        f3 = (float) (Math.cos(((double) f) + 1.5707963267948966d) * ((double) f2));
        f2 = (float) (Math.sin(((double) f) + 1.5707963267948966d) * ((double) f2));
        pointF.set((((float) (Math.sin(1.5707963267948966d - ((double) f)) * ((double) f4))) + pointF.x) + f3, (((float) (Math.cos(1.5707963267948966d - ((double) f)) * ((double) f4))) + pointF.y) + f2);
    }

    private void resetRotationStartScale() {
        this.rotationStartScale = BitmapDescriptorFactory.HUE_RED;
    }

    private void setLockedAspectRatio(float f) {
        this.areaView.setLockedAspectRatio(f);
        RectF rectF = new RectF();
        this.areaView.calculateRect(rectF, f);
        fillAreaView(rectF, true);
        if (this.listener != null) {
            this.listener.onChange(false);
            this.listener.onAspectLock(true);
        }
    }

    public RectF calculateBoundingBox(float f, float f2, float f3) {
        RectF rectF = new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, f, f2);
        Matrix matrix = new Matrix();
        matrix.postRotate(f3, f / 2.0f, f2 / 2.0f);
        matrix.mapRect(rectF);
        return rectF;
    }

    public float getCropHeight() {
        return this.areaView.getCropHeight();
    }

    public float getCropLeft() {
        return this.areaView.getCropLeft();
    }

    public float getCropTop() {
        return this.areaView.getCropTop();
    }

    public float getCropWidth() {
        return this.areaView.getCropWidth();
    }

    public Bitmap getResult() {
        if (!this.state.hasChanges() && this.state.getBaseRotation() < EPSILON && this.freeform) {
            return this.bitmap;
        }
        RectF rectF = new RectF();
        this.areaView.getCropRect(rectF);
        int ceil = (int) Math.ceil((double) scaleWidthToMaxSize(rectF, new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1280.0f, 1280.0f)));
        int ceil2 = (int) Math.ceil((double) (((float) ceil) / this.areaView.getAspectRatio()));
        Bitmap createBitmap = Bitmap.createBitmap(ceil, ceil2, Config.ARGB_8888);
        Matrix matrix = new Matrix();
        matrix.postTranslate((-this.state.getWidth()) / 2.0f, (-this.state.getHeight()) / 2.0f);
        matrix.postRotate(this.state.getOrientation());
        this.state.getConcatMatrix(matrix);
        float cropWidth = ((float) ceil) / this.areaView.getCropWidth();
        matrix.postScale(cropWidth, cropWidth);
        matrix.postTranslate((float) (ceil / 2), (float) (ceil2 / 2));
        new Canvas(createBitmap).drawBitmap(this.bitmap, matrix, new Paint(2));
        return createBitmap;
    }

    public void hide() {
        this.backView.setVisibility(4);
        this.imageView.setVisibility(4);
        this.areaView.setDimVisibility(false);
        this.areaView.setFrameVisibility(false);
        this.areaView.invalidate();
    }

    public boolean isReady() {
        return (this.detector.isScaling() || this.detector.isDragging() || this.areaView.isDragging()) ? false : true;
    }

    public void onAreaChange() {
        this.areaView.setGridType(GridType.MAJOR, false);
        this.state.translate(this.previousAreaRect.centerX() - this.areaView.getCropCenterX(), this.previousAreaRect.centerY() - this.areaView.getCropCenterY());
        updateMatrix();
        this.areaView.getCropRect(this.previousAreaRect);
        fitContentInBounds(true, false, false);
    }

    public void onAreaChangeBegan() {
        this.areaView.getCropRect(this.previousAreaRect);
        resetRotationStartScale();
        if (this.listener != null) {
            this.listener.onChange(false);
        }
    }

    public void onAreaChangeEnded() {
        this.areaView.setGridType(GridType.NONE, true);
        fillAreaView(this.areaView.getTargetRectToFill(), false);
    }

    public void onDrag(float f, float f2) {
        if (!this.animating) {
            this.state.translate(f, f2);
            updateMatrix();
        }
    }

    public void onFling(float f, float f2, float f3, float f4) {
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public void onRotationBegan() {
        this.areaView.setGridType(GridType.MINOR, false);
        if (this.rotationStartScale < EPSILON) {
            this.rotationStartScale = this.state.getScale();
        }
    }

    public void onRotationEnded() {
        this.areaView.setGridType(GridType.NONE, true);
    }

    public void onScale(float f, float f2, float f3) {
        if (!this.animating) {
            if (this.state.getScale() * f > 30.0f) {
                f = 30.0f / this.state.getScale();
            }
            this.state.scale(f, ((f2 - ((float) (this.imageView.getWidth() / 2))) / this.areaView.getCropWidth()) * this.state.getOrientedWidth(), ((f3 - (((((float) this.imageView.getHeight()) - this.bottomPadding) - ((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0))) / 2.0f)) / this.areaView.getCropHeight()) * this.state.getOrientedHeight());
            updateMatrix();
        }
    }

    public void onScrollChangeBegan() {
        if (!this.animating) {
            this.areaView.setGridType(GridType.MAJOR, true);
            resetRotationStartScale();
            if (this.listener != null) {
                this.listener.onChange(false);
            }
        }
    }

    public void onScrollChangeEnded() {
        this.areaView.setGridType(GridType.NONE, true);
        fitContentInBounds(true, false, true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.animating) {
            return true;
        }
        boolean z = false;
        if (this.areaView.onTouchEvent(motionEvent)) {
            return true;
        }
        switch (motionEvent.getAction()) {
            case 0:
                onScrollChangeBegan();
                break;
            case 1:
            case 3:
                onScrollChangeEnded();
                break;
        }
        try {
            return this.detector.onTouchEvent(motionEvent);
        } catch (Exception e) {
            return z;
        }
    }

    public void reset() {
        this.areaView.resetAnimator();
        this.areaView.setBitmap(this.bitmap, this.state.getBaseRotation() % 180.0f != BitmapDescriptorFactory.HUE_RED, this.freeform);
        this.areaView.setLockedAspectRatio(this.freeform ? BitmapDescriptorFactory.HUE_RED : 1.0f);
        this.state.reset(this.areaView, BitmapDescriptorFactory.HUE_RED, this.freeform);
        this.areaView.getCropRect(this.initialAreaRect);
        updateMatrix();
        resetRotationStartScale();
        if (this.listener != null) {
            this.listener.onChange(true);
            this.listener.onAspectLock(false);
        }
    }

    public void rotate90Degrees() {
        boolean z;
        boolean z2 = true;
        this.areaView.resetAnimator();
        resetRotationStartScale();
        float access$600 = ((this.state.getOrientation() - this.state.getBaseRotation()) - 90.0f) % 360.0f;
        boolean z3 = this.freeform;
        if (!this.freeform || this.areaView.getLockAspectRatio() <= BitmapDescriptorFactory.HUE_RED) {
            this.areaView.setBitmap(this.bitmap, (this.state.getBaseRotation() + access$600) % 180.0f != BitmapDescriptorFactory.HUE_RED, this.freeform);
            z = z3;
        } else {
            this.areaView.setLockedAspectRatio(1.0f / this.areaView.getLockAspectRatio());
            this.areaView.setActualRect(this.areaView.getLockAspectRatio());
            z = false;
        }
        this.state.reset(this.areaView, access$600, z);
        updateMatrix();
        if (this.listener != null) {
            CropViewListener cropViewListener = this.listener;
            if (!(access$600 == BitmapDescriptorFactory.HUE_RED && this.areaView.getLockAspectRatio() == BitmapDescriptorFactory.HUE_RED)) {
                z2 = false;
            }
            cropViewListener.onChange(z2);
        }
    }

    public float scaleWidthToMaxSize(RectF rectF, RectF rectF2) {
        float width = rectF2.width();
        return ((float) Math.floor((double) ((rectF.height() * width) / rectF.width()))) > rectF2.height() ? (float) Math.floor((double) ((rectF2.height() * rectF.width()) / rectF.height())) : width;
    }

    public void setBitmap(Bitmap bitmap, int i, boolean z) {
        this.bitmap = bitmap;
        this.freeform = z;
        this.state = new CropState(this.bitmap, i);
        this.backView.setVisibility(4);
        this.imageView.setVisibility(4);
        if (z) {
            this.areaView.setDimVisibility(false);
        }
        this.imageView.getViewTreeObserver().addOnPreDrawListener(new C43851());
        this.imageView.setImageBitmap(this.bitmap);
    }

    public void setBottomPadding(float f) {
        this.bottomPadding = f;
        this.areaView.setBottomPadding(f);
    }

    public void setListener(CropViewListener cropViewListener) {
        this.listener = cropViewListener;
    }

    public void setRotation(float f) {
        this.state.rotate(f - this.state.getRotation(), BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        fitContentInBounds(true, true, false);
    }

    public void show() {
        this.backView.setVisibility(0);
        this.imageView.setVisibility(0);
        this.areaView.setDimVisibility(true);
        this.areaView.setFrameVisibility(true);
        this.areaView.invalidate();
    }

    public void showAspectRatioDialog() {
        if (this.areaView.getLockAspectRatio() > BitmapDescriptorFactory.HUE_RED) {
            this.areaView.setLockedAspectRatio(BitmapDescriptorFactory.HUE_RED);
            if (this.listener != null) {
                this.listener.onAspectLock(false);
            }
        } else if (!this.hasAspectRatioDialog) {
            this.hasAspectRatioDialog = true;
            r4 = new String[8];
            r5 = new Integer[6][];
            r5[0] = new Integer[]{Integer.valueOf(3), Integer.valueOf(2)};
            r5[1] = new Integer[]{Integer.valueOf(5), Integer.valueOf(3)};
            r5[2] = new Integer[]{Integer.valueOf(4), Integer.valueOf(3)};
            r5[3] = new Integer[]{Integer.valueOf(5), Integer.valueOf(4)};
            r5[4] = new Integer[]{Integer.valueOf(7), Integer.valueOf(5)};
            r5[5] = new Integer[]{Integer.valueOf(16), Integer.valueOf(9)};
            r4[0] = LocaleController.getString("CropOriginal", R.string.CropOriginal);
            r4[1] = LocaleController.getString("CropSquare", R.string.CropSquare);
            int i = 2;
            for (Object[] objArr : r5) {
                if (this.areaView.getAspectRatio() > 1.0f) {
                    r4[i] = String.format("%d:%d", new Object[]{objArr[0], objArr[1]});
                } else {
                    r4[i] = String.format("%d:%d", new Object[]{objArr[1], objArr[0]});
                }
                i++;
            }
            AlertDialog create = new Builder(getContext()).setItems(r4, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    CropView.this.hasAspectRatioDialog = false;
                    switch (i) {
                        case 0:
                            CropView.this.setLockedAspectRatio((CropView.this.state.getBaseRotation() % 180.0f != BitmapDescriptorFactory.HUE_RED ? CropView.this.state.getHeight() : CropView.this.state.getWidth()) / (CropView.this.state.getBaseRotation() % 180.0f != BitmapDescriptorFactory.HUE_RED ? CropView.this.state.getWidth() : CropView.this.state.getHeight()));
                            return;
                        case 1:
                            CropView.this.setLockedAspectRatio(1.0f);
                            return;
                        default:
                            Integer[] numArr = r5[i - 2];
                            if (CropView.this.areaView.getAspectRatio() > 1.0f) {
                                CropView.this.setLockedAspectRatio(((float) numArr[0].intValue()) / ((float) numArr[1].intValue()));
                                return;
                            } else {
                                CropView.this.setLockedAspectRatio(((float) numArr[1].intValue()) / ((float) numArr[0].intValue()));
                                return;
                            }
                    }
                }
            }).create();
            create.setCanceledOnTouchOutside(true);
            create.setOnCancelListener(new C43917());
            create.show();
        }
    }

    public void updateLayout() {
        float cropWidth = this.areaView.getCropWidth();
        this.areaView.calculateRect(this.initialAreaRect, this.state.getWidth() / this.state.getHeight());
        this.areaView.setActualRect(this.areaView.getAspectRatio());
        this.areaView.getCropRect(this.previousAreaRect);
        this.state.scale(this.areaView.getCropWidth() / cropWidth, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
        updateMatrix();
    }

    public void updateMatrix() {
        this.presentationMatrix.reset();
        this.presentationMatrix.postTranslate((-this.state.getWidth()) / 2.0f, (-this.state.getHeight()) / 2.0f);
        this.presentationMatrix.postRotate(this.state.getOrientation());
        this.state.getConcatMatrix(this.presentationMatrix);
        this.presentationMatrix.postTranslate(this.areaView.getCropCenterX(), this.areaView.getCropCenterY());
        this.imageView.setImageMatrix(this.presentationMatrix);
    }

    public void willShow() {
        this.areaView.setFrameVisibility(true);
        this.areaView.setDimVisibility(true);
        this.areaView.invalidate();
    }
}
