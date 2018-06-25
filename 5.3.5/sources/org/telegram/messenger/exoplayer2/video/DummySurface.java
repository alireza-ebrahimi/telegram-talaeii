package org.telegram.messenger.exoplayer2.video;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

@TargetApi(17)
public final class DummySurface extends Surface {
    private static final int EGL_PROTECTED_CONTENT_EXT = 12992;
    private static final String TAG = "DummySurface";
    private static boolean secureSupported;
    private static boolean secureSupportedInitialized;
    public final boolean secure;
    private final DummySurfaceThread thread;
    private boolean threadReleased;

    private static class DummySurfaceThread extends HandlerThread implements OnFrameAvailableListener, Callback {
        private static final int MSG_INIT = 1;
        private static final int MSG_RELEASE = 3;
        private static final int MSG_UPDATE_TEXTURE = 2;
        private EGLContext context;
        private EGLDisplay display;
        private Handler handler;
        private Error initError;
        private RuntimeException initException;
        private EGLSurface pbuffer;
        private DummySurface surface;
        private SurfaceTexture surfaceTexture;
        private final int[] textureIdHolder = new int[1];

        public DummySurfaceThread() {
            super("dummySurface");
        }

        public DummySurface init(boolean secure) {
            int i = 1;
            start();
            this.handler = new Handler(getLooper(), this);
            boolean wasInterrupted = false;
            synchronized (this) {
                Handler handler = this.handler;
                if (!secure) {
                    i = 0;
                }
                handler.obtainMessage(1, i, 0).sendToTarget();
                while (this.surface == null && this.initException == null && this.initError == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        wasInterrupted = true;
                    }
                }
            }
            if (wasInterrupted) {
                Thread.currentThread().interrupt();
            }
            if (this.initException != null) {
                throw this.initException;
            } else if (this.initError == null) {
                return this.surface;
            } else {
                throw this.initError;
            }
        }

        public void release() {
            this.handler.sendEmptyMessage(3);
        }

        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            this.handler.sendEmptyMessage(2);
        }

        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    try {
                        initInternal(msg.arg1 != 0);
                        synchronized (this) {
                            notify();
                        }
                        break;
                    } catch (RuntimeException e) {
                        Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e);
                        this.initException = e;
                        synchronized (this) {
                            notify();
                            break;
                        }
                    } catch (Error e2) {
                        Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e2);
                        this.initError = e2;
                        synchronized (this) {
                            notify();
                            break;
                        }
                    } catch (Throwable th) {
                        synchronized (this) {
                            notify();
                        }
                    }
                case 2:
                    this.surfaceTexture.updateTexImage();
                    break;
                case 3:
                    try {
                        releaseInternal();
                        break;
                    } catch (Throwable e3) {
                        Log.e(DummySurface.TAG, "Failed to release dummy surface", e3);
                        break;
                    } finally {
                        quit();
                    }
            }
            return true;
        }

        private void initInternal(boolean secure) {
            int[] glAttributes;
            int[] pbufferAttributes;
            this.display = EGL14.eglGetDisplay(0);
            Assertions.checkState(this.display != null, "eglGetDisplay failed");
            int[] version = new int[2];
            Assertions.checkState(EGL14.eglInitialize(this.display, version, 0, version, 1), "eglInitialize failed");
            EGLConfig[] configs = new EGLConfig[1];
            int[] numConfigs = new int[1];
            boolean z = EGL14.eglChooseConfig(this.display, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12327, 12344, 12339, 4, 12344}, 0, configs, 0, 1, numConfigs, 0) && numConfigs[0] > 0 && configs[0] != null;
            Assertions.checkState(z, "eglChooseConfig failed");
            EGLConfig config = configs[0];
            if (secure) {
                glAttributes = new int[]{12440, 2, DummySurface.EGL_PROTECTED_CONTENT_EXT, 1, 12344};
            } else {
                glAttributes = new int[]{12440, 2, 12344};
            }
            this.context = EGL14.eglCreateContext(this.display, config, EGL14.EGL_NO_CONTEXT, glAttributes, 0);
            Assertions.checkState(this.context != null, "eglCreateContext failed");
            if (secure) {
                pbufferAttributes = new int[]{12375, 1, 12374, 1, DummySurface.EGL_PROTECTED_CONTENT_EXT, 1, 12344};
            } else {
                pbufferAttributes = new int[]{12375, 1, 12374, 1, 12344};
            }
            this.pbuffer = EGL14.eglCreatePbufferSurface(this.display, config, pbufferAttributes, 0);
            Assertions.checkState(this.pbuffer != null, "eglCreatePbufferSurface failed");
            Assertions.checkState(EGL14.eglMakeCurrent(this.display, this.pbuffer, this.pbuffer, this.context), "eglMakeCurrent failed");
            GLES20.glGenTextures(1, this.textureIdHolder, 0);
            this.surfaceTexture = new SurfaceTexture(this.textureIdHolder[0]);
            this.surfaceTexture.setOnFrameAvailableListener(this);
            this.surface = new DummySurface(this, this.surfaceTexture, secure);
        }

        private void releaseInternal() {
            try {
                if (this.surfaceTexture != null) {
                    this.surfaceTexture.release();
                    GLES20.glDeleteTextures(1, this.textureIdHolder, 0);
                }
                if (this.pbuffer != null) {
                    EGL14.eglDestroySurface(this.display, this.pbuffer);
                }
                if (this.context != null) {
                    EGL14.eglDestroyContext(this.display, this.context);
                }
                this.pbuffer = null;
                this.context = null;
                this.display = null;
                this.surface = null;
                this.surfaceTexture = null;
            } catch (Throwable th) {
                if (this.pbuffer != null) {
                    EGL14.eglDestroySurface(this.display, this.pbuffer);
                }
                if (this.context != null) {
                    EGL14.eglDestroyContext(this.display, this.context);
                }
                this.pbuffer = null;
                this.context = null;
                this.display = null;
                this.surface = null;
                this.surfaceTexture = null;
            }
        }
    }

    public static synchronized boolean isSecureSupported(Context context) {
        boolean z = true;
        synchronized (DummySurface.class) {
            if (!secureSupportedInitialized) {
                if (Util.SDK_INT < 24 || !enableSecureDummySurfaceV24(context)) {
                    z = false;
                }
                secureSupported = z;
                secureSupportedInitialized = true;
            }
            z = secureSupported;
        }
        return z;
    }

    public static DummySurface newInstanceV17(Context context, boolean secure) {
        assertApiLevel17OrHigher();
        boolean z = !secure || isSecureSupported(context);
        Assertions.checkState(z);
        return new DummySurfaceThread().init(secure);
    }

    private DummySurface(DummySurfaceThread thread, SurfaceTexture surfaceTexture, boolean secure) {
        super(surfaceTexture);
        this.thread = thread;
        this.secure = secure;
    }

    public void release() {
        super.release();
        synchronized (this.thread) {
            if (!this.threadReleased) {
                this.thread.release();
                this.threadReleased = true;
            }
        }
    }

    private static void assertApiLevel17OrHigher() {
        if (Util.SDK_INT < 17) {
            throw new UnsupportedOperationException("Unsupported prior to API level 17");
        }
    }

    @TargetApi(24)
    private static boolean enableSecureDummySurfaceV24(Context context) {
        String eglExtensions = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373);
        if (eglExtensions == null || !eglExtensions.contains("EGL_EXT_protected_content")) {
            return false;
        }
        if (Util.SDK_INT == 24 && "samsung".equals(Util.MANUFACTURER)) {
            return false;
        }
        if (Util.SDK_INT >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) {
            return true;
        }
        return false;
    }
}
