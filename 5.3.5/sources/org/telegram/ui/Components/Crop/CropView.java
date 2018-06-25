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
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
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
    class C25471 implements OnPreDrawListener {
        C25471() {
        }

        public boolean onPreDraw() {
            CropView.this.reset();
            CropView.this.imageView.getViewTreeObserver().removeOnPreDrawListener(this);
            return false;
        }
    }

    /* renamed from: org.telegram.ui.Components.Crop.CropView$7 */
    class C25537 implements OnCancelListener {
        C25537() {
        }

        public void onCancel(DialogInterface dialog) {
            CropView.this.hasAspectRatioDialog = false;
        }
    }

    private class CropRectangle {
        float[] coords = new float[8];

        CropRectangle() {
        }

        void setRect(RectF rect) {
            this.coords[0] = rect.left;
            this.coords[1] = rect.top;
            this.coords[2] = rect.right;
            this.coords[3] = rect.top;
            this.coords[4] = rect.right;
            this.coords[5] = rect.bottom;
            this.coords[6] = rect.left;
            this.coords[7] = rect.bottom;
        }

        void applyMatrix(Matrix m) {
            m.mapPoints(this.coords);
        }

        void getRect(RectF rect) {
            rect.set(this.coords[0], this.coords[1], this.coords[2], this.coords[7]);
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
        private float f96x;
        /* renamed from: y */
        private float f97y;

        private CropState(Bitmap bitmap, int bRotation) {
            this.width = (float) bitmap.getWidth();
            this.height = (float) bitmap.getHeight();
            this.f96x = 0.0f;
            this.f97y = 0.0f;
            this.scale = 1.0f;
            this.baseRotation = (float) bRotation;
            this.rotation = 0.0f;
            this.matrix = new Matrix();
        }

        private boolean hasChanges() {
            return Math.abs(this.f96x) > CropView.EPSILON || Math.abs(this.f97y) > CropView.EPSILON || Math.abs(this.scale - this.minimumScale) > CropView.EPSILON || Math.abs(this.rotation) > CropView.EPSILON || Math.abs(this.orientation) > CropView.EPSILON;
        }

        private float getWidth() {
            return this.width;
        }

        private float getHeight() {
            return this.height;
        }

        private float getOrientedWidth() {
            return (this.orientation + this.baseRotation) % 180.0f != 0.0f ? this.height : this.width;
        }

        private float getOrientedHeight() {
            return (this.orientation + this.baseRotation) % 180.0f != 0.0f ? this.width : this.height;
        }

        private void translate(float x, float y) {
            this.f96x += x;
            this.f97y += y;
            this.matrix.postTranslate(x, y);
        }

        private float getX() {
            return this.f96x;
        }

        private float getY() {
            return this.f97y;
        }

        private void scale(float s, float pivotX, float pivotY) {
            this.scale *= s;
            this.matrix.postScale(s, s, pivotX, pivotY);
        }

        private float getScale() {
            return this.scale;
        }

        private float getMinimumScale() {
            return this.minimumScale;
        }

        private void rotate(float angle, float pivotX, float pivotY) {
            this.rotation += angle;
            this.matrix.postRotate(angle, pivotX, pivotY);
        }

        private float getRotation() {
            return this.rotation;
        }

        private float getOrientation() {
            return this.orientation + this.baseRotation;
        }

        private float getBaseRotation() {
            return this.baseRotation;
        }

        private void reset(CropAreaView areaView, float orient, boolean freeform) {
            this.matrix.reset();
            this.f96x = 0.0f;
            this.f97y = 0.0f;
            this.rotation = 0.0f;
            this.orientation = orient;
            float w = (this.orientation + this.baseRotation) % 180.0f != 0.0f ? this.height : this.width;
            float h = (this.orientation + this.baseRotation) % 180.0f != 0.0f ? this.width : this.height;
            if (freeform) {
                this.minimumScale = areaView.getCropWidth() / w;
            } else {
                this.minimumScale = Math.max(areaView.getCropWidth() / w, areaView.getCropHeight() / h);
            }
            this.scale = this.minimumScale;
            this.matrix.postScale(this.scale, this.scale);
        }

        private void getConcatMatrix(Matrix toMatrix) {
            toMatrix.postConcat(this.matrix);
        }

        private Matrix getMatrix() {
            Matrix m = new Matrix();
            m.set(this.matrix);
            return m;
        }
    }

    public interface CropViewListener {
        void onAspectLock(boolean z);

        void onChange(boolean z);
    }

    public CropView(Context context) {
        super(context);
        this.backView = new View(context);
        this.backView.setBackgroundColor(-16777216);
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

    public boolean isReady() {
        return (this.detector.isScaling() || this.detector.isDragging() || this.areaView.isDragging()) ? false : true;
    }

    public void setListener(CropViewListener l) {
        this.listener = l;
    }

    public void setBottomPadding(float value) {
        this.bottomPadding = value;
        this.areaView.setBottomPadding(value);
    }

    public void setBitmap(Bitmap b, int rotation, boolean fform) {
        this.bitmap = b;
        this.freeform = fform;
        this.state = new CropState(this.bitmap, rotation);
        this.backView.setVisibility(4);
        this.imageView.setVisibility(4);
        if (fform) {
            this.areaView.setDimVisibility(false);
        }
        this.imageView.getViewTreeObserver().addOnPreDrawListener(new C25471());
        this.imageView.setImageBitmap(this.bitmap);
    }

    public void willShow() {
        this.areaView.setFrameVisibility(true);
        this.areaView.setDimVisibility(true);
        this.areaView.invalidate();
    }

    public void show() {
        this.backView.setVisibility(0);
        this.imageView.setVisibility(0);
        this.areaView.setDimVisibility(true);
        this.areaView.setFrameVisibility(true);
        this.areaView.invalidate();
    }

    public void hide() {
        this.backView.setVisibility(4);
        this.imageView.setVisibility(4);
        this.areaView.setDimVisibility(false);
        this.areaView.setFrameVisibility(false);
        this.areaView.invalidate();
    }

    public void reset() {
        this.areaView.resetAnimator();
        this.areaView.setBitmap(this.bitmap, this.state.getBaseRotation() % 180.0f != 0.0f, this.freeform);
        this.areaView.setLockedAspectRatio(this.freeform ? 0.0f : 1.0f);
        this.state.reset(this.areaView, 0.0f, this.freeform);
        this.areaView.getCropRect(this.initialAreaRect);
        updateMatrix();
        resetRotationStartScale();
        if (this.listener != null) {
            this.listener.onChange(true);
            this.listener.onAspectLock(false);
        }
    }

    public void updateMatrix() {
        this.presentationMatrix.reset();
        this.presentationMatrix.postTranslate((-this.state.getWidth()) / 2.0f, (-this.state.getHeight()) / 2.0f);
        this.presentationMatrix.postRotate(this.state.getOrientation());
        this.state.getConcatMatrix(this.presentationMatrix);
        this.presentationMatrix.postTranslate(this.areaView.getCropCenterX(), this.areaView.getCropCenterY());
        this.imageView.setImageMatrix(this.presentationMatrix);
    }

    private void fillAreaView(RectF targetRect, boolean allowZoomOut) {
        final float[] currentScale = new float[]{1.0f};
        float scale = Math.max(targetRect.width() / this.areaView.getCropWidth(), targetRect.height() / this.areaView.getCropHeight());
        boolean ensureFit = false;
        if (this.state.getScale() * scale > 30.0f) {
            scale = 30.0f / this.state.getScale();
            ensureFit = true;
        }
        final float x = ((targetRect.centerX() - ((float) (this.imageView.getWidth() / 2))) / this.areaView.getCropWidth()) * this.state.getOrientedWidth();
        final float y = ((targetRect.centerY() - (((((float) this.imageView.getHeight()) - this.bottomPadding) + ((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0))) / 2.0f)) / this.areaView.getCropHeight()) * this.state.getOrientedHeight();
        final float targetScale = scale;
        final boolean animEnsureFit = ensureFit;
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float deltaScale = (((targetScale - 1.0f) * ((Float) animation.getAnimatedValue()).floatValue()) + 1.0f) / currentScale[0];
                float[] fArr = currentScale;
                fArr[0] = fArr[0] * deltaScale;
                CropView.this.state.scale(deltaScale, x, y);
                CropView.this.updateMatrix();
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (animEnsureFit) {
                    CropView.this.fitContentInBounds(false, false, true);
                }
            }
        });
        this.areaView.fill(targetRect, animator, true);
        this.initialAreaRect.set(targetRect);
    }

    private float fitScale(RectF contentRect, float scale, float ratio) {
        float scaledW = contentRect.width() * ratio;
        float scaledH = contentRect.height() * ratio;
        float scaledX = (contentRect.width() - scaledW) / 2.0f;
        float scaledY = (contentRect.height() - scaledH) / 2.0f;
        contentRect.set(contentRect.left + scaledX, contentRect.top + scaledY, (contentRect.left + scaledX) + scaledW, (contentRect.top + scaledY) + scaledH);
        return scale * ratio;
    }

    private void fitTranslation(RectF contentRect, RectF boundsRect, PointF translation, float radians) {
        float frameLeft = boundsRect.left;
        float frameTop = boundsRect.top;
        float frameRight = boundsRect.right;
        float frameBottom = boundsRect.bottom;
        if (contentRect.left > frameLeft) {
            frameRight += contentRect.left - frameLeft;
            frameLeft = contentRect.left;
        }
        if (contentRect.top > frameTop) {
            frameBottom += contentRect.top - frameTop;
            frameTop = contentRect.top;
        }
        if (contentRect.right < frameRight) {
            frameLeft += contentRect.right - frameRight;
        }
        if (contentRect.bottom < frameBottom) {
            frameTop += contentRect.bottom - frameBottom;
        }
        float deltaX = boundsRect.centerX() - ((boundsRect.width() / 2.0f) + frameLeft);
        float deltaY = boundsRect.centerY() - ((boundsRect.height() / 2.0f) + frameTop);
        float yCompX = (float) (Math.cos(1.5707963267948966d + ((double) radians)) * ((double) deltaY));
        float yCompY = (float) (Math.sin(1.5707963267948966d + ((double) radians)) * ((double) deltaY));
        PointF pointF = translation;
        pointF.set((translation.x + ((float) (Math.sin(1.5707963267948966d - ((double) radians)) * ((double) deltaX)))) + yCompX, (translation.y + ((float) (Math.cos(1.5707963267948966d - ((double) radians)) * ((double) deltaX)))) + yCompY);
    }

    public RectF calculateBoundingBox(float w, float h, float rotation) {
        RectF result = new RectF(0.0f, 0.0f, w, h);
        Matrix m = new Matrix();
        m.postRotate(rotation, w / 2.0f, h / 2.0f);
        m.mapRect(result);
        return result;
    }

    public float scaleWidthToMaxSize(RectF sizeRect, RectF maxSizeRect) {
        float w = maxSizeRect.width();
        if (((float) Math.floor((double) ((sizeRect.height() * w) / sizeRect.width()))) <= maxSizeRect.height()) {
            return w;
        }
        return (float) Math.floor((double) ((sizeRect.width() * maxSizeRect.height()) / sizeRect.height()));
    }

    private void fitContentInBounds(boolean allowScale, boolean maximize, boolean animated) {
        fitContentInBounds(allowScale, maximize, animated, false);
    }

    private void fitContentInBounds(boolean allowScale, boolean maximize, boolean animated, boolean fast) {
        float boundsW = this.areaView.getCropWidth();
        float boundsH = this.areaView.getCropHeight();
        float contentW = this.state.getOrientedWidth();
        float contentH = this.state.getOrientedHeight();
        float rotation = this.state.getRotation();
        float radians = (float) Math.toRadians((double) rotation);
        RectF boundsRect = calculateBoundingBox(boundsW, boundsH, rotation);
        RectF rectF = new RectF(0.0f, 0.0f, contentW, contentH);
        float initialX = (boundsW - contentW) / 2.0f;
        float initialY = (boundsH - contentH) / 2.0f;
        float scale = this.state.getScale();
        this.tempRect.setRect(rectF);
        Matrix matrix = this.state.getMatrix();
        matrix.preTranslate(initialX / scale, initialY / scale);
        this.tempMatrix.reset();
        this.tempMatrix.setTranslate(rectF.centerX(), rectF.centerY());
        this.tempMatrix.setConcat(this.tempMatrix, matrix);
        this.tempMatrix.preTranslate(-rectF.centerX(), -rectF.centerY());
        this.tempRect.applyMatrix(this.tempMatrix);
        this.tempMatrix.reset();
        this.tempMatrix.preRotate(-rotation, contentW / 2.0f, contentH / 2.0f);
        this.tempRect.applyMatrix(this.tempMatrix);
        this.tempRect.getRect(rectF);
        PointF pointF = new PointF(this.state.getX(), this.state.getY());
        float targetScale = scale;
        if (!rectF.contains(boundsRect)) {
            if (allowScale && (boundsRect.width() > rectF.width() || boundsRect.height() > rectF.height())) {
                targetScale = fitScale(rectF, scale, boundsRect.width() / scaleWidthToMaxSize(boundsRect, rectF));
            }
            fitTranslation(rectF, boundsRect, pointF, radians);
        } else if (maximize && this.rotationStartScale > 0.0f) {
            float ratio = boundsRect.width() / scaleWidthToMaxSize(boundsRect, rectF);
            if (this.state.getScale() * ratio < this.rotationStartScale) {
                ratio = 1.0f;
            }
            targetScale = fitScale(rectF, scale, ratio);
            fitTranslation(rectF, boundsRect, pointF, radians);
        }
        float dx = pointF.x - this.state.getX();
        float dy = pointF.y - this.state.getY();
        if (animated) {
            final float animScale = targetScale / scale;
            final float animDX = dx;
            final float animDY = dy;
            if (Math.abs(animScale - 1.0f) >= EPSILON || Math.abs(animDX) >= EPSILON || Math.abs(animDY) >= EPSILON) {
                this.animating = true;
                final float[] currentValues = new float[]{1.0f, 0.0f, 0.0f};
                ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                animator.addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = ((Float) animation.getAnimatedValue()).floatValue();
                        float deltaX = (animDX * value) - currentValues[1];
                        float[] fArr = currentValues;
                        fArr[1] = fArr[1] + deltaX;
                        float deltaY = (animDY * value) - currentValues[2];
                        fArr = currentValues;
                        fArr[2] = fArr[2] + deltaY;
                        CropView.this.state.translate(currentValues[0] * deltaX, currentValues[0] * deltaY);
                        float deltaScale = (((animScale - 1.0f) * value) + 1.0f) / currentValues[0];
                        fArr = currentValues;
                        fArr[0] = fArr[0] * deltaScale;
                        CropView.this.state.scale(deltaScale, 0.0f, 0.0f);
                        CropView.this.updateMatrix();
                    }
                });
                final boolean z = fast;
                final boolean z2 = allowScale;
                final boolean z3 = maximize;
                final boolean z4 = animated;
                animator.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        CropView.this.animating = false;
                        if (!z) {
                            CropView.this.fitContentInBounds(z2, z3, z4, true);
                        }
                    }
                });
                animator.setInterpolator(this.areaView.getInterpolator());
                animator.setDuration(fast ? 100 : 200);
                animator.start();
                return;
            }
            return;
        }
        this.state.translate(dx, dy);
        this.state.scale(targetScale / scale, 0.0f, 0.0f);
        updateMatrix();
    }

    public void rotate90Degrees() {
        boolean z = true;
        this.areaView.resetAnimator();
        resetRotationStartScale();
        float orientation = ((this.state.getOrientation() - this.state.getBaseRotation()) - 90.0f) % 360.0f;
        boolean fform = this.freeform;
        if (!this.freeform || this.areaView.getLockAspectRatio() <= 0.0f) {
            this.areaView.setBitmap(this.bitmap, (this.state.getBaseRotation() + orientation) % 180.0f != 0.0f, this.freeform);
        } else {
            this.areaView.setLockedAspectRatio(1.0f / this.areaView.getLockAspectRatio());
            this.areaView.setActualRect(this.areaView.getLockAspectRatio());
            fform = false;
        }
        this.state.reset(this.areaView, orientation, fform);
        updateMatrix();
        if (this.listener != null) {
            CropViewListener cropViewListener = this.listener;
            if (!(orientation == 0.0f && this.areaView.getLockAspectRatio() == 0.0f)) {
                z = false;
            }
            cropViewListener.onChange(z);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.animating) {
            return true;
        }
        boolean result = false;
        if (this.areaView.onTouchEvent(event)) {
            return true;
        }
        switch (event.getAction()) {
            case 0:
                onScrollChangeBegan();
                break;
            case 1:
            case 3:
                onScrollChangeEnded();
                break;
        }
        try {
            return this.detector.onTouchEvent(event);
        } catch (Exception e) {
            return result;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void onAreaChangeBegan() {
        this.areaView.getCropRect(this.previousAreaRect);
        resetRotationStartScale();
        if (this.listener != null) {
            this.listener.onChange(false);
        }
    }

    public void onAreaChange() {
        this.areaView.setGridType(GridType.MAJOR, false);
        this.state.translate(this.previousAreaRect.centerX() - this.areaView.getCropCenterX(), this.previousAreaRect.centerY() - this.areaView.getCropCenterY());
        updateMatrix();
        this.areaView.getCropRect(this.previousAreaRect);
        fitContentInBounds(true, false, false);
    }

    public void onAreaChangeEnded() {
        this.areaView.setGridType(GridType.NONE, true);
        fillAreaView(this.areaView.getTargetRectToFill(), false);
    }

    public void onDrag(float dx, float dy) {
        if (!this.animating) {
            this.state.translate(dx, dy);
            updateMatrix();
        }
    }

    public void onFling(float startX, float startY, float velocityX, float velocityY) {
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

    public void onScale(float scale, float x, float y) {
        if (!this.animating) {
            if (this.state.getScale() * scale > 30.0f) {
                scale = 30.0f / this.state.getScale();
            }
            this.state.scale(scale, ((x - ((float) (this.imageView.getWidth() / 2))) / this.areaView.getCropWidth()) * this.state.getOrientedWidth(), ((y - (((((float) this.imageView.getHeight()) - this.bottomPadding) - ((float) (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0))) / 2.0f)) / this.areaView.getCropHeight()) * this.state.getOrientedHeight());
            updateMatrix();
        }
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

    private void resetRotationStartScale() {
        this.rotationStartScale = 0.0f;
    }

    public void setRotation(float angle) {
        this.state.rotate(angle - this.state.getRotation(), 0.0f, 0.0f);
        fitContentInBounds(true, true, false);
    }

    public Bitmap getResult() {
        if (!this.state.hasChanges() && this.state.getBaseRotation() < EPSILON && this.freeform) {
            return this.bitmap;
        }
        RectF cropRect = new RectF();
        this.areaView.getCropRect(cropRect);
        int width = (int) Math.ceil((double) scaleWidthToMaxSize(cropRect, new RectF(0.0f, 0.0f, 1280.0f, 1280.0f)));
        int height = (int) Math.ceil((double) (((float) width) / this.areaView.getAspectRatio()));
        Bitmap resultBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Matrix matrix = new Matrix();
        matrix.postTranslate((-this.state.getWidth()) / 2.0f, (-this.state.getHeight()) / 2.0f);
        matrix.postRotate(this.state.getOrientation());
        this.state.getConcatMatrix(matrix);
        float scale = ((float) width) / this.areaView.getCropWidth();
        matrix.postScale(scale, scale);
        matrix.postTranslate((float) (width / 2), (float) (height / 2));
        new Canvas(resultBitmap).drawBitmap(this.bitmap, matrix, new Paint(2));
        return resultBitmap;
    }

    private void setLockedAspectRatio(float aspectRatio) {
        this.areaView.setLockedAspectRatio(aspectRatio);
        RectF targetRect = new RectF();
        this.areaView.calculateRect(targetRect, aspectRatio);
        fillAreaView(targetRect, true);
        if (this.listener != null) {
            this.listener.onChange(false);
            this.listener.onAspectLock(true);
        }
    }

    public void showAspectRatioDialog() {
        if (this.areaView.getLockAspectRatio() > 0.0f) {
            this.areaView.setLockedAspectRatio(0.0f);
            if (this.listener != null) {
                this.listener.onAspectLock(false);
            }
        } else if (!this.hasAspectRatioDialog) {
            this.hasAspectRatioDialog = true;
            actions = new String[8];
            ratios = new Integer[6][];
            ratios[0] = new Integer[]{Integer.valueOf(3), Integer.valueOf(2)};
            ratios[1] = new Integer[]{Integer.valueOf(5), Integer.valueOf(3)};
            ratios[2] = new Integer[]{Integer.valueOf(4), Integer.valueOf(3)};
            ratios[3] = new Integer[]{Integer.valueOf(5), Integer.valueOf(4)};
            ratios[4] = new Integer[]{Integer.valueOf(7), Integer.valueOf(5)};
            ratios[5] = new Integer[]{Integer.valueOf(16), Integer.valueOf(9)};
            actions[0] = LocaleController.getString("CropOriginal", R.string.CropOriginal);
            actions[1] = LocaleController.getString("CropSquare", R.string.CropSquare);
            int i = 2;
            for (Integer[] ratioPair : ratios) {
                if (this.areaView.getAspectRatio() > 1.0f) {
                    actions[i] = String.format("%d:%d", new Object[]{ratioPair[0], ratioPair[1]});
                } else {
                    actions[i] = String.format("%d:%d", new Object[]{ratioPair[1], ratioPair[0]});
                }
                i++;
            }
            AlertDialog dialog = new Builder(getContext()).setItems(actions, new OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    CropView.this.hasAspectRatioDialog = false;
                    switch (which) {
                        case 0:
                            CropView.this.setLockedAspectRatio((CropView.this.state.getBaseRotation() % 180.0f != 0.0f ? CropView.this.state.getHeight() : CropView.this.state.getWidth()) / (CropView.this.state.getBaseRotation() % 180.0f != 0.0f ? CropView.this.state.getWidth() : CropView.this.state.getHeight()));
                            return;
                        case 1:
                            CropView.this.setLockedAspectRatio(1.0f);
                            return;
                        default:
                            Integer[] ratioPair = ratios[which - 2];
                            if (CropView.this.areaView.getAspectRatio() > 1.0f) {
                                CropView.this.setLockedAspectRatio(((float) ratioPair[0].intValue()) / ((float) ratioPair[1].intValue()));
                                return;
                            } else {
                                CropView.this.setLockedAspectRatio(((float) ratioPair[1].intValue()) / ((float) ratioPair[0].intValue()));
                                return;
                            }
                    }
                }
            }).create();
            dialog.setCanceledOnTouchOutside(true);
            dialog.setOnCancelListener(new C25537());
            dialog.show();
        }
    }

    public void updateLayout() {
        float w = this.areaView.getCropWidth();
        this.areaView.calculateRect(this.initialAreaRect, this.state.getWidth() / this.state.getHeight());
        this.areaView.setActualRect(this.areaView.getAspectRatio());
        this.areaView.getCropRect(this.previousAreaRect);
        this.state.scale(this.areaView.getCropWidth() / w, 0.0f, 0.0f);
        updateMatrix();
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

    public float getCropHeight() {
        return this.areaView.getCropHeight();
    }
}
