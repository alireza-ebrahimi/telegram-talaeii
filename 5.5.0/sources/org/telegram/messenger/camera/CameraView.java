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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
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
    class C34441 implements Runnable {
        C34441() {
        }

        public void run() {
            if (CameraView.this.cameraSession != null) {
                CameraView.this.cameraSession.setInitied();
            }
            CameraView.this.checkPreviewMatrix();
        }
    }

    /* renamed from: org.telegram.messenger.camera.CameraView$2 */
    class C34452 implements Runnable {
        C34452() {
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

    public CameraView(Context context, boolean z) {
        super(context, null);
        this.isFrontface = z;
        this.initialFrontface = z;
        this.textureView = new TextureView(context);
        this.textureView.setSurfaceTextureListener(this);
        addView(this.textureView);
        this.focusAreaSize = AndroidUtilities.dp(96.0f);
        this.outerPaint.setColor(-1);
        this.outerPaint.setStyle(Style.STROKE);
        this.outerPaint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
        this.innerPaint.setColor(Integer.MAX_VALUE);
    }

    private void adjustAspectRatio(int i, int i2, int i3) {
        this.txform.reset();
        int width = getWidth();
        int height = getHeight();
        float f = (float) (width / 2);
        float f2 = (float) (height / 2);
        float max = (i3 == 0 || i3 == 2) ? Math.max(((float) (this.clipTop + height)) / ((float) i), ((float) (this.clipLeft + width)) / ((float) i2)) : Math.max(((float) (this.clipTop + height)) / ((float) i2), ((float) (this.clipLeft + width)) / ((float) i));
        this.txform.postScale((max * ((float) i2)) / ((float) width), (((float) i) * max) / ((float) height), f, f2);
        if (1 == i3 || 3 == i3) {
            this.txform.postRotate((float) ((i3 - 2) * 90), f, f2);
        } else if (2 == i3) {
            this.txform.postRotate(180.0f, f, f2);
        }
        if (this.mirror) {
            this.txform.postScale(-1.0f, 1.0f, f, f2);
        }
        if (!(this.clipTop == 0 && this.clipLeft == 0)) {
            this.txform.postTranslate((float) ((-this.clipLeft) / 2), (float) ((-this.clipTop) / 2));
        }
        this.textureView.setTransform(this.txform);
        Matrix matrix = new Matrix();
        matrix.postRotate((float) this.cameraSession.getDisplayOrientation());
        matrix.postScale(((float) width) / 2000.0f, ((float) height) / 2000.0f);
        matrix.postTranslate(((float) width) / 2.0f, ((float) height) / 2.0f);
        matrix.invert(this.matrix);
    }

    private Rect calculateTapArea(float f, float f2, float f3) {
        int intValue = Float.valueOf(((float) this.focusAreaSize) * f3).intValue();
        int clamp = clamp(((int) f) - (intValue / 2), 0, getWidth() - intValue);
        int clamp2 = clamp(((int) f2) - (intValue / 2), 0, getHeight() - intValue);
        RectF rectF = new RectF((float) clamp, (float) clamp2, (float) (clamp + intValue), (float) (intValue + clamp2));
        this.matrix.mapRect(rectF);
        return new Rect(Math.round(rectF.left), Math.round(rectF.top), Math.round(rectF.right), Math.round(rectF.bottom));
    }

    private void checkPreviewMatrix() {
        if (this.previewSize != null) {
            adjustAspectRatio(this.previewSize.getWidth(), this.previewSize.getHeight(), ((Activity) getContext()).getWindowManager().getDefaultDisplay().getRotation());
        }
    }

    private int clamp(int i, int i2, int i3) {
        return i > i3 ? i3 : i < i2 ? i2 : i;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initCamera(boolean r15) {
        /*
        r14 = this;
        r13 = 3;
        r12 = 1036831949; // 0x3dcccccd float:0.1 double:5.122630465E-315;
        r11 = 16;
        r10 = 9;
        r2 = 1280; // 0x500 float:1.794E-42 double:6.324E-321;
        r3 = 0;
        r0 = org.telegram.messenger.camera.CameraController.getInstance();
        r4 = r0.getCameras();
        if (r4 != 0) goto L_0x0016;
    L_0x0015:
        return;
    L_0x0016:
        r0 = 0;
        r1 = r0;
    L_0x0018:
        r0 = r4.size();
        if (r1 >= r0) goto L_0x0134;
    L_0x001e:
        r0 = r4.get(r1);
        r0 = (org.telegram.messenger.camera.CameraInfo) r0;
        r5 = r14.isFrontface;
        if (r5 == 0) goto L_0x002c;
    L_0x0028:
        r5 = r0.frontCamera;
        if (r5 != 0) goto L_0x0034;
    L_0x002c:
        r5 = r14.isFrontface;
        if (r5 != 0) goto L_0x0106;
    L_0x0030:
        r5 = r0.frontCamera;
        if (r5 != 0) goto L_0x0106;
    L_0x0034:
        r5 = r0;
    L_0x0035:
        if (r5 == 0) goto L_0x0015;
    L_0x0037:
        r6 = 1068149419; // 0x3faaaaab float:1.3333334 double:5.277359326E-315;
        r0 = org.telegram.messenger.AndroidUtilities.displaySize;
        r0 = r0.x;
        r1 = org.telegram.messenger.AndroidUtilities.displaySize;
        r1 = r1.y;
        r0 = java.lang.Math.max(r0, r1);
        r0 = (float) r0;
        r1 = org.telegram.messenger.AndroidUtilities.displaySize;
        r1 = r1.x;
        r3 = org.telegram.messenger.AndroidUtilities.displaySize;
        r3 = r3.y;
        r1 = java.lang.Math.min(r1, r3);
        r1 = (float) r1;
        r7 = r0 / r1;
        r0 = r14.initialFrontface;
        if (r0 == 0) goto L_0x010b;
    L_0x005a:
        r3 = new org.telegram.messenger.camera.Size;
        r3.<init>(r11, r10);
        r1 = 480; // 0x1e0 float:6.73E-43 double:2.37E-321;
        r0 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
    L_0x0063:
        r4 = r14.textureView;
        r4 = r4.getWidth();
        if (r4 <= 0) goto L_0x0093;
    L_0x006b:
        r4 = r14.textureView;
        r4 = r4.getHeight();
        if (r4 <= 0) goto L_0x0093;
    L_0x0073:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r8 = org.telegram.messenger.AndroidUtilities.displaySize;
        r8 = r8.y;
        r4 = java.lang.Math.min(r4, r8);
        r8 = r3.getHeight();
        r8 = r8 * r4;
        r9 = r3.getWidth();
        r8 = r8 / r9;
        r9 = r5.getPreviewSizes();
        r4 = org.telegram.messenger.camera.CameraController.chooseOptimalSize(r9, r4, r8, r3);
        r14.previewSize = r4;
    L_0x0093:
        r4 = r5.getPictureSizes();
        r4 = org.telegram.messenger.camera.CameraController.chooseOptimalSize(r4, r1, r0, r3);
        r3 = r4.getWidth();
        if (r3 < r2) goto L_0x0132;
    L_0x00a1:
        r3 = r4.getHeight();
        if (r3 < r2) goto L_0x0132;
    L_0x00a7:
        r3 = r7 - r6;
        r3 = java.lang.Math.abs(r3);
        r3 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1));
        if (r3 >= 0) goto L_0x012c;
    L_0x00b1:
        r3 = new org.telegram.messenger.camera.Size;
        r6 = 4;
        r3.<init>(r13, r6);
    L_0x00b7:
        r6 = r5.getPictureSizes();
        r0 = org.telegram.messenger.camera.CameraController.chooseOptimalSize(r6, r0, r1, r3);
        r1 = r0.getWidth();
        if (r1 < r2) goto L_0x00cb;
    L_0x00c5:
        r1 = r0.getHeight();
        if (r1 >= r2) goto L_0x0132;
    L_0x00cb:
        r1 = r14.textureView;
        r1 = r1.getSurfaceTexture();
        r2 = r14.previewSize;
        if (r2 == 0) goto L_0x0015;
    L_0x00d5:
        if (r1 == 0) goto L_0x0015;
    L_0x00d7:
        r2 = r14.previewSize;
        r2 = r2.getWidth();
        r3 = r14.previewSize;
        r3 = r3.getHeight();
        r1.setDefaultBufferSize(r2, r3);
        r2 = new org.telegram.messenger.camera.CameraSession;
        r3 = r14.previewSize;
        r4 = 256; // 0x100 float:3.59E-43 double:1.265E-321;
        r2.<init>(r5, r3, r0, r4);
        r14.cameraSession = r2;
        r0 = org.telegram.messenger.camera.CameraController.getInstance();
        r2 = r14.cameraSession;
        r3 = new org.telegram.messenger.camera.CameraView$1;
        r3.<init>();
        r4 = new org.telegram.messenger.camera.CameraView$2;
        r4.<init>();
        r0.open(r2, r1, r3, r4);
        goto L_0x0015;
    L_0x0106:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0018;
    L_0x010b:
        r0 = r7 - r6;
        r0 = java.lang.Math.abs(r0);
        r0 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1));
        if (r0 >= 0) goto L_0x0121;
    L_0x0115:
        r1 = new org.telegram.messenger.camera.Size;
        r0 = 4;
        r1.<init>(r0, r13);
        r0 = 960; // 0x3c0 float:1.345E-42 double:4.743E-321;
        r3 = r1;
        r1 = r2;
        goto L_0x0063;
    L_0x0121:
        r1 = new org.telegram.messenger.camera.Size;
        r1.<init>(r11, r10);
        r0 = 720; // 0x2d0 float:1.009E-42 double:3.557E-321;
        r3 = r1;
        r1 = r2;
        goto L_0x0063;
    L_0x012c:
        r3 = new org.telegram.messenger.camera.Size;
        r3.<init>(r10, r11);
        goto L_0x00b7;
    L_0x0132:
        r0 = r4;
        goto L_0x00cb;
    L_0x0134:
        r5 = r3;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.CameraView.initCamera(boolean):void");
    }

    public void destroy(boolean z, Runnable runnable) {
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, !z ? new Semaphore(0) : null, runnable);
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        boolean drawChild = super.drawChild(canvas, view, j);
        if (!(this.focusProgress == 1.0f && this.innerAlpha == BitmapDescriptorFactory.HUE_RED && this.outerAlpha == BitmapDescriptorFactory.HUE_RED)) {
            int dp = AndroidUtilities.dp(30.0f);
            long currentTimeMillis = System.currentTimeMillis();
            long j2 = currentTimeMillis - this.lastDrawTime;
            if (j2 < 0 || j2 > 17) {
                j2 = 17;
            }
            this.lastDrawTime = currentTimeMillis;
            this.outerPaint.setAlpha((int) (this.interpolator.getInterpolation(this.outerAlpha) * 255.0f));
            this.innerPaint.setAlpha((int) (this.interpolator.getInterpolation(this.innerAlpha) * 127.0f));
            float interpolation = this.interpolator.getInterpolation(this.focusProgress);
            canvas.drawCircle((float) this.cx, (float) this.cy, ((float) dp) + (((float) dp) * (1.0f - interpolation)), this.outerPaint);
            canvas.drawCircle((float) this.cx, (float) this.cy, ((float) dp) * interpolation, this.innerPaint);
            if (this.focusProgress < 1.0f) {
                this.focusProgress = (((float) j2) / 200.0f) + this.focusProgress;
                if (this.focusProgress > 1.0f) {
                    this.focusProgress = 1.0f;
                }
                invalidate();
            } else if (this.innerAlpha != BitmapDescriptorFactory.HUE_RED) {
                this.innerAlpha -= ((float) j2) / 150.0f;
                if (this.innerAlpha < BitmapDescriptorFactory.HUE_RED) {
                    this.innerAlpha = BitmapDescriptorFactory.HUE_RED;
                }
                invalidate();
            } else if (this.outerAlpha != BitmapDescriptorFactory.HUE_RED) {
                this.outerAlpha -= ((float) j2) / 150.0f;
                if (this.outerAlpha < BitmapDescriptorFactory.HUE_RED) {
                    this.outerAlpha = BitmapDescriptorFactory.HUE_RED;
                }
                invalidate();
            }
        }
        return drawChild;
    }

    public void focusToPoint(int i, int i2) {
        Rect calculateTapArea = calculateTapArea((float) i, (float) i2, 1.0f);
        Rect calculateTapArea2 = calculateTapArea((float) i, (float) i2, 1.5f);
        if (this.cameraSession != null) {
            this.cameraSession.focusToRect(calculateTapArea, calculateTapArea2);
        }
        this.focusProgress = BitmapDescriptorFactory.HUE_RED;
        this.innerAlpha = 1.0f;
        this.outerAlpha = 1.0f;
        this.cx = i;
        this.cy = i2;
        this.lastDrawTime = System.currentTimeMillis();
        invalidate();
    }

    public CameraSession getCameraSession() {
        return this.cameraSession;
    }

    public Size getPreviewSize() {
        return this.previewSize;
    }

    public boolean hasFrontFaceCamera() {
        ArrayList cameras = CameraController.getInstance().getCameras();
        for (int i = 0; i < cameras.size(); i++) {
            if (((CameraInfo) cameras.get(i)).frontCamera != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean isFrontface() {
        return this.isFrontface;
    }

    public boolean isInitied() {
        return this.initied;
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        checkPreviewMatrix();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        initCamera(this.isFrontface);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.cameraSession != null) {
            CameraController.getInstance().close(this.cameraSession, null, null);
        }
        return false;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        checkPreviewMatrix();
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (!this.initied && this.cameraSession != null && this.cameraSession.isInitied()) {
            if (this.delegate != null) {
                this.delegate.onCameraInit();
            }
            this.initied = true;
        }
    }

    public void setClipLeft(int i) {
        this.clipLeft = i;
    }

    public void setClipTop(int i) {
        this.clipTop = i;
    }

    public void setDelegate(CameraViewDelegate cameraViewDelegate) {
        this.delegate = cameraViewDelegate;
    }

    public void setMirror(boolean z) {
        this.mirror = z;
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
}
