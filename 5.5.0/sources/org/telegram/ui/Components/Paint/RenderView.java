package org.telegram.ui.Components.Paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.Components.Paint.Painting.PaintingDelegate;
import org.telegram.ui.Components.Size;

public class RenderView extends TextureView {
    private Bitmap bitmap;
    private Brush brush;
    private int color;
    private RenderViewDelegate delegate;
    private Input input = new Input(this);
    private CanvasInternal internal;
    private int orientation;
    private Painting painting;
    private DispatchQueue queue;
    private boolean shuttingDown;
    private boolean transformedBitmap;
    private UndoStore undoStore;
    private float weight;

    /* renamed from: org.telegram.ui.Components.Paint.RenderView$1 */
    class C44781 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$1$1 */
        class C44761 implements Runnable {
            C44761() {
            }

            public void run() {
                if (RenderView.this.internal != null) {
                    RenderView.this.internal.requestRender();
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$1$2 */
        class C44772 implements Runnable {
            C44772() {
            }

            public void run() {
                RenderView.this.internal.shutdown();
                RenderView.this.internal = null;
            }
        }

        C44781() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            if (surfaceTexture != null && RenderView.this.internal == null) {
                RenderView.this.internal = new CanvasInternal(surfaceTexture);
                RenderView.this.internal.setBufferSize(i, i2);
                RenderView.this.updateTransform();
                RenderView.this.internal.requestRender();
                if (RenderView.this.painting.isPaused()) {
                    RenderView.this.painting.onResume();
                }
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (!(RenderView.this.internal == null || RenderView.this.shuttingDown)) {
                RenderView.this.painting.onPause(new C44772());
            }
            return true;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
            if (RenderView.this.internal != null) {
                RenderView.this.internal.setBufferSize(i, i2);
                RenderView.this.updateTransform();
                RenderView.this.internal.requestRender();
                RenderView.this.internal.postRunnable(new C44761());
            }
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.RenderView$2 */
    class C44792 implements PaintingDelegate {
        C44792() {
        }

        public void contentChanged(RectF rectF) {
            if (RenderView.this.internal != null) {
                RenderView.this.internal.scheduleRedraw();
            }
        }

        public DispatchQueue requestDispatchQueue() {
            return RenderView.this.queue;
        }

        public UndoStore requestUndoStore() {
            return RenderView.this.undoStore;
        }

        public void strokeCommited() {
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.RenderView$3 */
    class C44803 implements Runnable {
        C44803() {
        }

        public void run() {
            RenderView.this.painting.cleanResources(RenderView.this.transformedBitmap);
            RenderView.this.internal.shutdown();
            RenderView.this.internal = null;
        }
    }

    private class CanvasInternal extends DispatchQueue {
        private final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private final int EGL_OPENGL_ES2_BIT = 4;
        private int bufferHeight;
        private int bufferWidth;
        private Runnable drawRunnable = new C44831();
        private EGL10 egl10;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLDisplay eglDisplay;
        private EGLSurface eglSurface;
        private boolean initialized;
        private long lastRenderCallTime;
        private boolean ready;
        private Runnable scheduledRunnable;
        private SurfaceTexture surfaceTexture;

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$1 */
        class C44831 implements Runnable {

            /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$1$1 */
            class C44821 implements Runnable {
                C44821() {
                }

                public void run() {
                    CanvasInternal.this.ready = true;
                }
            }

            C44831() {
            }

            public void run() {
                if (CanvasInternal.this.initialized && !RenderView.this.shuttingDown) {
                    CanvasInternal.this.setCurrentContext();
                    GLES20.glBindFramebuffer(36160, 0);
                    GLES20.glViewport(0, 0, CanvasInternal.this.bufferWidth, CanvasInternal.this.bufferHeight);
                    GLES20.glClearColor(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f);
                    GLES20.glClear(MessagesController.UPDATE_MASK_CHAT_ADMINS);
                    RenderView.this.painting.render();
                    GLES20.glBlendFunc(1, 771);
                    CanvasInternal.this.egl10.eglSwapBuffers(CanvasInternal.this.eglDisplay, CanvasInternal.this.eglSurface);
                    if (!CanvasInternal.this.ready) {
                        RenderView.this.queue.postRunnable(new C44821(), 200);
                    }
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$2 */
        class C44842 implements Runnable {
            C44842() {
            }

            public void run() {
                CanvasInternal.this.drawRunnable.run();
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$3 */
        class C44853 implements Runnable {
            C44853() {
            }

            public void run() {
                CanvasInternal.this.scheduledRunnable = null;
                CanvasInternal.this.drawRunnable.run();
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$4 */
        class C44864 implements Runnable {
            C44864() {
            }

            public void run() {
                CanvasInternal.this.finish();
                Looper myLooper = Looper.myLooper();
                if (myLooper != null) {
                    myLooper.quit();
                }
            }
        }

        public CanvasInternal(SurfaceTexture surfaceTexture) {
            super("CanvasInternal");
            this.surfaceTexture = surfaceTexture;
        }

        private void checkBitmap() {
            Size size = RenderView.this.painting.getSize();
            if (((float) RenderView.this.bitmap.getWidth()) != size.width || ((float) RenderView.this.bitmap.getHeight()) != size.height || RenderView.this.orientation != 0) {
                float width = (float) RenderView.this.bitmap.getWidth();
                if (RenderView.this.orientation % 360 == 90 || RenderView.this.orientation % 360 == 270) {
                    width = (float) RenderView.this.bitmap.getHeight();
                }
                RenderView.this.bitmap = createBitmap(RenderView.this.bitmap, size.width / width);
                RenderView.this.orientation = 0;
                RenderView.this.transformedBitmap = true;
            }
        }

        private Bitmap createBitmap(Bitmap bitmap, float f) {
            Matrix matrix = new Matrix();
            matrix.setScale(f, f);
            matrix.postRotate((float) RenderView.this.orientation);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        private boolean initGL() {
            this.egl10 = (EGL10) EGLContext.getEGL();
            this.eglDisplay = this.egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
            if (this.eglDisplay == EGL10.EGL_NO_DISPLAY) {
                FileLog.e("eglGetDisplay failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                finish();
                return false;
            }
            if (this.egl10.eglInitialize(this.eglDisplay, new int[2])) {
                int[] iArr = new int[1];
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344}, eGLConfigArr, 1, iArr)) {
                    FileLog.e("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    finish();
                    return false;
                } else if (iArr[0] > 0) {
                    this.eglConfig = eGLConfigArr[0];
                    this.eglContext = this.egl10.eglCreateContext(this.eglDisplay, this.eglConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
                    if (this.eglContext == null) {
                        FileLog.e("eglCreateContext failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        finish();
                        return false;
                    } else if (this.surfaceTexture instanceof SurfaceTexture) {
                        this.eglSurface = this.egl10.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surfaceTexture, null);
                        if (this.eglSurface == null || this.eglSurface == EGL10.EGL_NO_SURFACE) {
                            FileLog.e("createWindowSurface failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                            finish();
                            return false;
                        } else if (this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                            GLES20.glEnable(3042);
                            GLES20.glDisable(3024);
                            GLES20.glDisable(2960);
                            GLES20.glDisable(2929);
                            RenderView.this.painting.setupShaders();
                            checkBitmap();
                            RenderView.this.painting.setBitmap(RenderView.this.bitmap);
                            Utils.HasGLError();
                            return true;
                        } else {
                            FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                            finish();
                            return false;
                        }
                    } else {
                        finish();
                        return false;
                    }
                } else {
                    FileLog.e("eglConfig not initialized");
                    finish();
                    return false;
                }
            }
            FileLog.e("eglInitialize failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
            finish();
            return false;
        }

        private boolean setCurrentContext() {
            return !this.initialized ? false : (this.eglContext.equals(this.egl10.eglGetCurrentContext()) && this.eglSurface.equals(this.egl10.eglGetCurrentSurface(12377))) || this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext);
        }

        public void finish() {
            if (this.eglSurface != null) {
                this.egl10.eglMakeCurrent(this.eglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
                this.egl10.eglDestroySurface(this.eglDisplay, this.eglSurface);
                this.eglSurface = null;
            }
            if (this.eglContext != null) {
                this.egl10.eglDestroyContext(this.eglDisplay, this.eglContext);
                this.eglContext = null;
            }
            if (this.eglDisplay != null) {
                this.egl10.eglTerminate(this.eglDisplay);
                this.eglDisplay = null;
            }
        }

        public Bitmap getTexture() {
            if (!this.initialized) {
                return null;
            }
            final Semaphore semaphore = new Semaphore(0);
            final Bitmap[] bitmapArr = new Bitmap[1];
            try {
                postRunnable(new Runnable() {
                    public void run() {
                        bitmapArr[0] = RenderView.this.painting.getPaintingData(new RectF(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, RenderView.this.painting.getSize().width, RenderView.this.painting.getSize().height), false).bitmap;
                        semaphore.release();
                    }
                });
                semaphore.acquire();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            return bitmapArr[0];
        }

        public void requestRender() {
            postRunnable(new C44842());
        }

        public void run() {
            if (RenderView.this.bitmap != null && !RenderView.this.bitmap.isRecycled()) {
                this.initialized = initGL();
                super.run();
            }
        }

        public void scheduleRedraw() {
            if (this.scheduledRunnable != null) {
                cancelRunnable(this.scheduledRunnable);
                this.scheduledRunnable = null;
            }
            this.scheduledRunnable = new C44853();
            postRunnable(this.scheduledRunnable, 1);
        }

        public void setBufferSize(int i, int i2) {
            this.bufferWidth = i;
            this.bufferHeight = i2;
        }

        public void shutdown() {
            postRunnable(new C44864());
        }
    }

    public interface RenderViewDelegate {
        void onBeganDrawing();

        void onFinishedDrawing(boolean z);

        boolean shouldDraw();
    }

    public RenderView(Context context, Painting painting, Bitmap bitmap, int i) {
        super(context);
        this.bitmap = bitmap;
        this.orientation = i;
        this.painting = painting;
        this.painting.setRenderView(this);
        setSurfaceTextureListener(new C44781());
        this.painting.setDelegate(new C44792());
    }

    private float brushWeightForSize(float f) {
        float f2 = this.painting.getSize().width;
        return ((f2 * 0.043945312f) * f) + (0.00390625f * f2);
    }

    private void updateTransform() {
        Matrix matrix = new Matrix();
        float width = this.painting != null ? ((float) getWidth()) / this.painting.getSize().width : 1.0f;
        if (width <= BitmapDescriptorFactory.HUE_RED) {
            width = 1.0f;
        }
        Size size = getPainting().getSize();
        matrix.preTranslate(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        matrix.preScale(width, -width);
        matrix.preTranslate((-size.width) / 2.0f, (-size.height) / 2.0f);
        this.input.setMatrix(matrix);
        this.painting.setRenderProjection(GLMatrix.MultiplyMat4f(GLMatrix.LoadOrtho(BitmapDescriptorFactory.HUE_RED, (float) this.internal.bufferWidth, BitmapDescriptorFactory.HUE_RED, (float) this.internal.bufferHeight, -1.0f, 1.0f), GLMatrix.LoadGraphicsMatrix(matrix)));
    }

    public Brush getCurrentBrush() {
        return this.brush;
    }

    public int getCurrentColor() {
        return this.color;
    }

    public float getCurrentWeight() {
        return this.weight;
    }

    public Painting getPainting() {
        return this.painting;
    }

    public Bitmap getResultBitmap() {
        return this.internal != null ? this.internal.getTexture() : null;
    }

    public void onBeganDrawing() {
        if (this.delegate != null) {
            this.delegate.onBeganDrawing();
        }
    }

    public void onFinishedDrawing(boolean z) {
        if (this.delegate != null) {
            this.delegate.onFinishedDrawing(z);
        }
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return false;
        }
        if (this.internal == null || !this.internal.initialized || !this.internal.ready) {
            return true;
        }
        this.input.process(motionEvent);
        return true;
    }

    public void performInContext(final Runnable runnable) {
        if (this.internal != null) {
            this.internal.postRunnable(new Runnable() {
                public void run() {
                    if (RenderView.this.internal != null && RenderView.this.internal.initialized) {
                        RenderView.this.internal.setCurrentContext();
                        runnable.run();
                    }
                }
            });
        }
    }

    public void setBrush(Brush brush) {
        Painting painting = this.painting;
        this.brush = brush;
        painting.setBrush(brush);
    }

    public void setBrushSize(float f) {
        this.weight = brushWeightForSize(f);
    }

    public void setColor(int i) {
        this.color = i;
    }

    public void setDelegate(RenderViewDelegate renderViewDelegate) {
        this.delegate = renderViewDelegate;
    }

    public void setQueue(DispatchQueue dispatchQueue) {
        this.queue = dispatchQueue;
    }

    public void setUndoStore(UndoStore undoStore) {
        this.undoStore = undoStore;
    }

    public boolean shouldDraw() {
        return this.delegate == null || this.delegate.shouldDraw();
    }

    public void shutdown() {
        this.shuttingDown = true;
        if (this.internal != null) {
            performInContext(new C44803());
        }
        setVisibility(8);
    }
}
