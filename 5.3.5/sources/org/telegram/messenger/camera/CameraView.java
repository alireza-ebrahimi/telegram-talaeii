package org.telegram.messenger.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import org.telegram.customization.fetch.FetchService;
import org.telegram.messenger.AndroidUtilities;

@SuppressLint({"NewApi"})
public class CameraView extends FrameLayout implements SurfaceTextureListener {
    private CameraSession cameraSession;
    private boolean circleShape = false;
    private int clipLeft;
    private int clipTop;
    private int cx;
    private int cy;
    private CameraViewDelegate delegate;
    private int focusAreaSize;
    private float focusProgress = 1.0f;
    private boolean initialFrontface;
    private boolean initied;
    private float innerAlpha;
    private Paint innerPaint = new Paint(1);
    private DecelerateInterpolator interpolator = new DecelerateInterpolator();
    private boolean isFrontface;
    private long lastDrawTime;
    private Matrix matrix = new Matrix();
    private boolean mirror;
    private float outerAlpha;
    private Paint outerPaint = new Paint(1);
    private Size previewSize;
    private TextureView textureView;
    private Matrix txform = new Matrix();

    /* renamed from: org.telegram.messenger.camera.CameraView$1 */
    class C16951 implements Runnable {
        C16951() {
        }

        public void run() {
            if (CameraView.this.cameraSession != null) {
                CameraView.this.cameraSession.setInitied();
            }
            CameraView.this.checkPreviewMatrix();
        }
    }

    /* renamed from: org.telegram.messenger.camera.CameraView$2 */
    class C16962 implements Runnable {
        C16962() {
        }

        public void run() {
            if (CameraView.this.delegate != null) {
                CameraView.this.delegate.onCameraCreated(CameraView.this.cameraSession.cameraInfo.camera);
            }
        }
    }

    public interface CameraViewDelegate {
        void onCameraCreated(Camera camera);

        void onCameraInit();
    }

    public CameraView(Context context, boolean frontface) {
        super(context, null);
        this.isFrontface = frontface;
        this.initialFrontface = frontface;
        this.textureView = new TextureView(context);
        this.textureView.setSurfaceTextureListener(this);
        addView(this.textureView);
        this.focusAreaSize = AndroidUtilities.dp(96.0f);
        this.outerPaint.setColor(-1);
        this.outerPaint.setStyle(Style.STROKE);
        this.outerPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.innerPaint.setColor(Integer.MAX_VALUE);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        checkPreviewMatrix();
    }

    public void setMirror(boolean value) {
        this.mirror = value;
    }

    public boolean isFrontface() {
        return this.isFrontface;
    }

    public boolean hasFrontFaceCamera() {
        ArrayList<CameraInfo> cameraInfos = CameraController.getInstance().getCameras();
        for (int a = 0; a < cameraInfos.size(); a++) {
            if (((CameraInfo) cameraInfos.get(a)).frontCamera != 0) {
                return true;
            }
        }
        return false;
    }

    public void switchCamera() {
        boolean z = false;
        if (this.cameraSession != null) {
            CameraController.getInstance().close(this.cameraSession, null, null);
            this.cameraSession = null;
        }
        this.initied = false;
        if (!this.isFrontface) {
            z = true;
        }
        this.isFrontface = z;
        initCamera(this.isFrontface);
    }

    private void initCamera(boolean front) {
        CameraInfo info = null;
        ArrayList<CameraInfo> cameraInfos = CameraController.getInstance().getCameras();
        if (cameraInfos != null) {
            for (int a = 0; a < cameraInfos.size(); a++) {
                CameraInfo cameraInfo = (CameraInfo) cameraInfos.get(a);
                if ((this.isFrontface && cameraInfo.frontCamera != 0) || (!this.isFrontface && cameraInfo.frontCamera == 0)) {
                    info = cameraInfo;
                    break;
                }
            }
            if (info != null) {
                Size aspectRatio;
                int wantedWidth;
                int wantedHeight;
                float screenSize = ((float) Math.max(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) / ((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y));
                if (this.initialFrontface) {
                    aspectRatio = new Size(16, 9);
                    wantedWidth = FetchService.QUERY_SINGLE;
                    wantedHeight = 270;
                } else if (Math.abs(screenSize - 1.3333334f) < 0.1f) {
                    aspectRatio = new Size(4, 3);
                    wantedWidth = 1280;
                    wantedHeight = 960;
                } else {
                    aspectRatio = new Size(16, 9);
                    wantedWidth = 1280;
                    wantedHeight = 720;
                }
                if (this.textureView.getWidth() > 0 && this.textureView.getHeight() > 0) {
                    int width = Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y);
                    this.previewSize = CameraController.chooseOptimalSize(info.getPreviewSizes(), width, (aspectRatio.getHeight() * width) / aspectRatio.getWidth(), aspectRatio);
                }
                Size pictureSize = CameraController.chooseOptimalSize(info.getPictureSizes(), wantedWidth, wantedHeight, aspectRatio);
                if (pictureSize.getWidth() >= 1280 && pictureSize.getHeight() >= 1280) {
                    if (Math.abs(screenSize - 1.3333334f) < 0.1f) {
                        aspectRatio = new Size(3, 4);
                    } else {
                        aspectRatio = new Size(9, 16);
                    }
                    Size pictureSize2 = CameraController.chooseOptimalSize(info.getPictureSizes(), wantedHeight, wantedWidth, aspectRatio);
                    if (pictureSize2.getWidth() < 1280 || pictureSize2.getHeight() < 1280) {
                        pictureSize = pictureSize2;
                    }
                }
                SurfaceTexture surfaceTexture = this.textureView.getSurfaceTexture();
                if (this.previewSize != null && surfaceTexture != null) {
                    surfaceTexture.setDefaultBufferSize(this.previewSize.getWidth(), this.previewSize.getHeight());
                    this.cameraSession = new CameraSession(info, this.previewSize, pictureSize, 256);
                    CameraController.getInstance().open(this.cameraSession, surfaceTexture, new C16951(), new C16962());
                }
            }
        }
    }

    public Size getPreviewSize() {
        return this.previewSize;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        initCamera(this.isFrontface);
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
        checkPreviewMatrix();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.cameraSession != null) {
            CameraController.getInstance().close(this.cameraSession, null, null);
        }
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (!this.initied && this.cameraSession != null && this.cameraSession.isInitied()) {
            if (this.delegate != null) {
                this.delegate.onCameraInit();
            }
            this.initied = true;
        }
    }

    public void setClipTop(int value) {
        this.clipTop = value;
    }

    public void setClipLeft(int value) {
        this.clipLeft = value;
    }

    private void checkPreviewMatrix() {
        if (this.previewSize != null) {
            adjustAspectRatio(this.previewSize.getWidth(), this.previewSize.getHeight(), ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation());
        }
    }

    private void adjustAspectRatio(int previewWidth, int previewHeight, int rotation) {
        float scale;
        this.txform.reset();
        int viewWidth = getWidth();
        int viewHeight = getHeight();
        float viewCenterX = (float) (viewWidth / 2);
        float viewCenterY = (float) (viewHeight / 2);
        if (rotation == 0 || rotation == 2) {
            scale = Math.max(((float) (this.clipTop + viewHeight)) / ((float) previewWidth), ((float) (this.clipLeft + viewWidth)) / ((float) previewHeight));
        } else {
            scale = Math.max(((float) (this.clipTop + viewHeight)) / ((float) previewHeight), ((float) (this.clipLeft + viewWidth)) / ((float) previewWidth));
        }
        this.txform.postScale((((float) previewHeight) * scale) / ((float) viewWidth), (((float) previewWidth) * scale) / ((float) viewHeight), viewCenterX, viewCenterY);
        if (1 == rotation || 3 == rotation) {
            this.txform.postRotate((float) ((rotation - 2) * 90), viewCenterX, viewCenterY);
        } else if (2 == rotation) {
            this.txform.postRotate(180.0f, viewCenterX, viewCenterY);
        }
        if (this.mirror) {
            this.txform.postScale(-1.0f, 1.0f, viewCenterX, viewCenterY);
        }
        if (!(this.clipTop == 0 && this.clipLeft == 0)) {
            this.txform.postTranslate((float) ((-this.clipLeft) / 2), (float) ((-this.clipTop) / 2));
        }
        this.textureView.setTransform(this.txform);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) this.cameraSession.getDisplayOrientation());
        matrix.postScale(((float) viewWidth) / 2000.0f, ((float) viewHeight) / 2000.0f);
        matrix.postTranslate(((float) viewWidth) / 2.0f, ((float) viewHeight) / 2.0f);
        matrix.invert(this.matrix);
    }

    private Rect calculateTapArea(float x, float y, float coefficient) {
        int areaSize = Float.valueOf(((float) this.focusAreaSize) * coefficient).intValue();
        int left = clamp(((int) x) - (areaSize / 2), 0, getWidth() - areaSize);
        int top = clamp(((int) y) - (areaSize / 2), 0, getHeight() - areaSize);
        RectF rectF = new RectF((float) left, (float) top, (float) (left + areaSize), (float) (top + areaSize));
        this.matrix.mapRect(rectF);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private int clamp(int x, int min, int max) {
        if (x > max) {
            return max;
        }
        if (x < min) {
            return min;
        }
        return x;
    }

    public void focusToPoint(int x, int y) {
        Rect focusRect = calculateTapArea((float) x, (float) y, 1.0f);
        Rect meteringRect = calculateTapArea((float) x, (float) y, 1.5f);
        if (this.cameraSession != null) {
            this.cameraSession.focusToRect(focusRect, meteringRect);
        }
        this.focusProgress = 0.0f;
        this.innerAlpha = 1.0f;
        this.outerAlpha = 1.0f;
        this.cx = x;
        this.cy = y;
        this.lastDrawTime = System.currentTimeMillis();
        invalidate();
    }

    public void setDelegate(CameraViewDelegate cameraViewDelegate) {
        this.delegate = cameraViewDelegate;
    }

    public boolean isInitied() {
        return this.initied;
    }

    public CameraSession getCameraSession() {
        return this.cameraSession;
    }

    public void destroy(boolean async, Runnable beforeDestroyRunnable) {
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, !async ? new Semaphore(0) : null, beforeDestroyRunnable);
        }
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean result = super.drawChild(canvas, child, drawingTime);
        if (!(this.focusProgress == 1.0f && this.innerAlpha == 0.0f && this.outerAlpha == 0.0f)) {
            int baseRad = AndroidUtilities.dp(30.0f);
            long newTime = System.currentTimeMillis();
            long dt = newTime - this.lastDrawTime;
            if (dt < 0 || dt > 17) {
                dt = 17;
            }
            this.lastDrawTime = newTime;
            this.outerPaint.setAlpha((int) (this.interpolator.getInterpolation(this.outerAlpha) * 255.0f));
            this.innerPaint.setAlpha((int) (this.interpolator.getInterpolation(this.innerAlpha) * 127.0f));
            float interpolated = this.interpolator.getInterpolation(this.focusProgress);
            canvas.drawCircle((float) this.cx, (float) this.cy, ((float) baseRad) + (((float) baseRad) * (1.0f - interpolated)), this.outerPaint);
            canvas.drawCircle((float) this.cx, (float) this.cy, ((float) baseRad) * interpolated, this.innerPaint);
            if (this.focusProgress < 1.0f) {
                this.focusProgress += ((float) dt) / 200.0f;
                if (this.focusProgress > 1.0f) {
                    this.focusProgress = 1.0f;
                }
                invalidate();
            } else if (this.innerAlpha != 0.0f) {
                this.innerAlpha -= ((float) dt) / 150.0f;
                if (this.innerAlpha < 0.0f) {
                    this.innerAlpha = 0.0f;
                }
                invalidate();
            } else if (this.outerAlpha != 0.0f) {
                this.outerAlpha -= ((float) dt) / 150.0f;
                if (this.outerAlpha < 0.0f) {
                    this.outerAlpha = 0.0f;
                }
                invalidate();
            }
        }
        return result;
    }
}
