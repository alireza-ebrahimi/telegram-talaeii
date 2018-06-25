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
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
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
    class C26401 implements SurfaceTextureListener {

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$1$1 */
        class C26381 implements Runnable {
            C26381() {
            }

            public void run() {
                if (RenderView.this.internal != null) {
                    RenderView.this.internal.requestRender();
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$1$2 */
        class C26392 implements Runnable {
            C26392() {
            }

            public void run() {
                RenderView.this.internal.shutdown();
                RenderView.this.internal = null;
            }
        }

        C26401() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if (surface != null && RenderView.this.internal == null) {
                RenderView.this.internal = new CanvasInternal(surface);
                RenderView.this.internal.setBufferSize(width, height);
                RenderView.this.updateTransform();
                RenderView.this.internal.requestRender();
                if (RenderView.this.painting.isPaused()) {
                    RenderView.this.painting.onResume();
                }
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            if (RenderView.this.internal != null) {
                RenderView.this.internal.setBufferSize(width, height);
                RenderView.this.updateTransform();
                RenderView.this.internal.requestRender();
                RenderView.this.internal.postRunnable(new C26381());
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (!(RenderView.this.internal == null || RenderView.this.shuttingDown)) {
                RenderView.this.painting.onPause(new C26392());
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.RenderView$2 */
    class C26412 implements PaintingDelegate {
        C26412() {
        }

        public void contentChanged(RectF rect) {
            if (RenderView.this.internal != null) {
                RenderView.this.internal.scheduleRedraw();
            }
        }

        public void strokeCommited() {
        }

        public UndoStore requestUndoStore() {
            return RenderView.this.undoStore;
        }

        public DispatchQueue requestDispatchQueue() {
            return RenderView.this.queue;
        }
    }

    /* renamed from: org.telegram.ui.Components.Paint.RenderView$3 */
    class C26423 implements Runnable {
        C26423() {
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
        private Runnable drawRunnable = new C26451();
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
        class C26451 implements Runnable {

            /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$1$1 */
            class C26441 implements Runnable {
                C26441() {
                }

                public void run() {
                    CanvasInternal.this.ready = true;
                }
            }

            C26451() {
            }

            public void run() {
                if (CanvasInternal.this.initialized && !RenderView.this.shuttingDown) {
                    CanvasInternal.this.setCurrentContext();
                    GLES20.glBindFramebuffer(36160, 0);
                    GLES20.glViewport(0, 0, CanvasInternal.this.bufferWidth, CanvasInternal.this.bufferHeight);
                    GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                    GLES20.glClear(16384);
                    RenderView.this.painting.render();
                    GLES20.glBlendFunc(1, 771);
                    CanvasInternal.this.egl10.eglSwapBuffers(CanvasInternal.this.eglDisplay, CanvasInternal.this.eglSurface);
                    if (!CanvasInternal.this.ready) {
                        RenderView.this.queue.postRunnable(new C26441(), 200);
                    }
                }
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$2 */
        class C26462 implements Runnable {
            C26462() {
            }

            public void run() {
                CanvasInternal.this.drawRunnable.run();
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$3 */
        class C26473 implements Runnable {
            C26473() {
            }

            public void run() {
                CanvasInternal.this.scheduledRunnable = null;
                CanvasInternal.this.drawRunnable.run();
            }
        }

        /* renamed from: org.telegram.ui.Components.Paint.RenderView$CanvasInternal$4 */
        class C26484 implements Runnable {
            C26484() {
            }

            public void run() {
                CanvasInternal.this.finish();
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    looper.quit();
                }
            }
        }

        public CanvasInternal(SurfaceTexture surface) {
            super("CanvasInternal");
            this.surfaceTexture = surface;
        }

        public void run() {
            if (RenderView.this.bitmap != null && !RenderView.this.bitmap.isRecycled()) {
                this.initialized = initGL();
                super.run();
            }
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
                int[] configsCount = new int[1];
                EGLConfig[] configs = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12326, 0, 12344}, configs, 1, configsCount)) {
                    FileLog.e("eglChooseConfig failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    finish();
                    return false;
                } else if (configsCount[0] > 0) {
                    this.eglConfig = configs[0];
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

        private Bitmap createBitmap(Bitmap bitmap, float scale) {
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            matrix.postRotate((float) RenderView.this.orientation);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }

        private void checkBitmap() {
            Size paintingSize = RenderView.this.painting.getSize();
            if (((float) RenderView.this.bitmap.getWidth()) != paintingSize.width || ((float) RenderView.this.bitmap.getHeight()) != paintingSize.height || RenderView.this.orientation != 0) {
                float bitmapWidth = (float) RenderView.this.bitmap.getWidth();
                if (RenderView.this.orientation % 360 == 90 || RenderView.this.orientation % 360 == 270) {
                    bitmapWidth = (float) RenderView.this.bitmap.getHeight();
                }
                RenderView.this.bitmap = createBitmap(RenderView.this.bitmap, paintingSize.width / bitmapWidth);
                RenderView.this.orientation = 0;
                RenderView.this.transformedBitmap = true;
            }
        }

        private boolean setCurrentContext() {
            if (!this.initialized) {
                return false;
            }
            if ((this.eglContext.equals(this.egl10.eglGetCurrentContext()) && this.eglSurface.equals(this.egl10.eglGetCurrentSurface(12377))) || this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                return true;
            }
            return false;
        }

        public void setBufferSize(int width, int height) {
            this.bufferWidth = width;
            this.bufferHeight = height;
        }

        public void requestRender() {
            postRunnable(new C26462());
        }

        public void scheduleRedraw() {
            if (this.scheduledRunnable != null) {
                cancelRunnable(this.scheduledRunnable);
                this.scheduledRunnable = null;
            }
            this.scheduledRunnable = new C26473();
            postRunnable(this.scheduledRunnable, 1);
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

        public void shutdown() {
            postRunnable(new C26484());
        }

        public Bitmap getTexture() {
            if (!this.initialized) {
                return null;
            }
            final Semaphore semaphore = new Semaphore(0);
            final Bitmap[] object = new Bitmap[1];
            try {
                postRunnable(new Runnable() {
                    public void run() {
                        object[0] = RenderView.this.painting.getPaintingData(new RectF(0.0f, 0.0f, RenderView.this.painting.getSize().width, RenderView.this.painting.getSize().height), false).bitmap;
                        semaphore.release();
                    }
                });
                semaphore.acquire();
            } catch (Exception e) {
                FileLog.e(e);
            }
            return object[0];
        }
    }

    public interface RenderViewDelegate {
        void onBeganDrawing();

        void onFinishedDrawing(boolean z);

        boolean shouldDraw();
    }

    public RenderView(Context context, Painting paint, Bitmap b, int rotation) {
        super(context);
        this.bitmap = b;
        this.orientation = rotation;
        this.painting = paint;
        this.painting.setRenderView(this);
        setSurfaceTextureListener(new C26401());
        this.painting.setDelegate(new C26412());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 1) {
            return false;
        }
        if (this.internal == null || !this.internal.initialized || !this.internal.ready) {
            return true;
        }
        this.input.process(event);
        return true;
    }

    public void setUndoStore(UndoStore store) {
        this.undoStore = store;
    }

    public void setQueue(DispatchQueue dispatchQueue) {
        this.queue = dispatchQueue;
    }

    public void setDelegate(RenderViewDelegate renderViewDelegate) {
        this.delegate = renderViewDelegate;
    }

    public Painting getPainting() {
        return this.painting;
    }

    private float brushWeightForSize(float size) {
        float paintingWidth = this.painting.getSize().width;
        return (0.00390625f * paintingWidth) + ((0.043945312f * paintingWidth) * size);
    }

    public int getCurrentColor() {
        return this.color;
    }

    public void setColor(int value) {
        this.color = value;
    }

    public float getCurrentWeight() {
        return this.weight;
    }

    public void setBrushSize(float size) {
        this.weight = brushWeightForSize(size);
    }

    public Brush getCurrentBrush() {
        return this.brush;
    }

    public void setBrush(Brush value) {
        Painting painting = this.painting;
        this.brush = value;
        painting.setBrush(value);
    }

    private void updateTransform() {
        float scale;
        Matrix matrix = new Matrix();
        if (this.painting != null) {
            scale = ((float) getWidth()) / this.painting.getSize().width;
        } else {
            scale = 1.0f;
        }
        if (scale <= 0.0f) {
            scale = 1.0f;
        }
        Size paintingSize = getPainting().getSize();
        matrix.preTranslate(((float) getWidth()) / 2.0f, ((float) getHeight()) / 2.0f);
        matrix.preScale(scale, -scale);
        matrix.preTranslate((-paintingSize.width) / 2.0f, (-paintingSize.height) / 2.0f);
        this.input.setMatrix(matrix);
        this.painting.setRenderProjection(GLMatrix.MultiplyMat4f(GLMatrix.LoadOrtho(0.0f, (float) this.internal.bufferWidth, 0.0f, (float) this.internal.bufferHeight, -1.0f, 1.0f), GLMatrix.LoadGraphicsMatrix(matrix)));
    }

    public boolean shouldDraw() {
        return this.delegate == null || this.delegate.shouldDraw();
    }

    public void onBeganDrawing() {
        if (this.delegate != null) {
            this.delegate.onBeganDrawing();
        }
    }

    public void onFinishedDrawing(boolean moved) {
        if (this.delegate != null) {
            this.delegate.onFinishedDrawing(moved);
        }
    }

    public void shutdown() {
        this.shuttingDown = true;
        if (this.internal != null) {
            performInContext(new C26423());
        }
        setVisibility(8);
    }

    public Bitmap getResultBitmap() {
        return this.internal != null ? this.internal.getTexture() : null;
    }

    public void performInContext(final Runnable action) {
        if (this.internal != null) {
            this.internal.postRunnable(new Runnable() {
                public void run() {
                    if (RenderView.this.internal != null && RenderView.this.internal.initialized) {
                        RenderView.this.internal.setCurrentContext();
                        action.run();
                    }
                }
            });
        }
    }
}
