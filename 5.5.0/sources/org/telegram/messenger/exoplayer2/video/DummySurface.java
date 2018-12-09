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

        private void initInternal(boolean z) {
            this.display = EGL14.eglGetDisplay(0);
            Assertions.checkState(this.display != null, "eglGetDisplay failed");
            int[] iArr = new int[2];
            Assertions.checkState(EGL14.eglInitialize(this.display, iArr, 0, iArr, 1), "eglInitialize failed");
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            int[] iArr2 = new int[1];
            boolean z2 = EGL14.eglChooseConfig(this.display, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 0, 12327, 12344, 12339, 4, 12344}, 0, eGLConfigArr, 0, 1, iArr2, 0) && iArr2[0] > 0 && eGLConfigArr[0] != null;
            Assertions.checkState(z2, "eglChooseConfig failed");
            EGLConfig eGLConfig = eGLConfigArr[0];
            this.context = EGL14.eglCreateContext(this.display, eGLConfig, EGL14.EGL_NO_CONTEXT, z ? new int[]{12440, 2, DummySurface.EGL_PROTECTED_CONTENT_EXT, 1, 12344} : new int[]{12440, 2, 12344}, 0);
            Assertions.checkState(this.context != null, "eglCreateContext failed");
            this.pbuffer = EGL14.eglCreatePbufferSurface(this.display, eGLConfig, z ? new int[]{12375, 1, 12374, 1, DummySurface.EGL_PROTECTED_CONTENT_EXT, 1, 12344} : new int[]{12375, 1, 12374, 1, 12344}, 0);
            Assertions.checkState(this.pbuffer != null, "eglCreatePbufferSurface failed");
            Assertions.checkState(EGL14.eglMakeCurrent(this.display, this.pbuffer, this.pbuffer, this.context), "eglMakeCurrent failed");
            GLES20.glGenTextures(1, this.textureIdHolder, 0);
            this.surfaceTexture = new SurfaceTexture(this.textureIdHolder[0]);
            this.surfaceTexture.setOnFrameAvailableListener(this);
            this.surface = new DummySurface(this, this.surfaceTexture, z);
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

        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    try {
                        initInternal(message.arg1 != 0);
                        synchronized (this) {
                            notify();
                        }
                        break;
                    } catch (Throwable e) {
                        Log.e(DummySurface.TAG, "Failed to initialize dummy surface", e);
                        this.initException = e;
                        synchronized (this) {
                            notify();
                            break;
                        }
                    } catch (Throwable e2) {
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
                    } catch (Throwable e22) {
                        Log.e(DummySurface.TAG, "Failed to release dummy surface", e22);
                        break;
                    } finally {
                        quit();
                    }
            }
            return true;
        }

        public DummySurface init(boolean z) {
            Object obj = null;
            start();
            this.handler = new Handler(getLooper(), this);
            synchronized (this) {
                this.handler.obtainMessage(1, z ? 1 : 0, 0).sendToTarget();
                while (this.surface == null && this.initException == null && this.initError == null) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        obj = 1;
                    }
                }
            }
            if (obj != null) {
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

        public void onFrameAvailable(SurfaceTexture surfaceTexture) {
            this.handler.sendEmptyMessage(2);
        }

        public void release() {
            this.handler.sendEmptyMessage(3);
        }
    }

    private DummySurface(DummySurfaceThread dummySurfaceThread, SurfaceTexture surfaceTexture, boolean z) {
        super(surfaceTexture);
        this.thread = dummySurfaceThread;
        this.secure = z;
    }

    private static void assertApiLevel17OrHigher() {
        if (Util.SDK_INT < 17) {
            throw new UnsupportedOperationException("Unsupported prior to API level 17");
        }
    }

    @TargetApi(24)
    private static boolean enableSecureDummySurfaceV24(Context context) {
        String eglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), 12373);
        return (eglQueryString == null || !eglQueryString.contains("EGL_EXT_protected_content")) ? false : (Util.SDK_INT == 24 && "samsung".equals(Util.MANUFACTURER)) ? false : Util.SDK_INT >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance");
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

    public static DummySurface newInstanceV17(Context context, boolean z) {
        assertApiLevel17OrHigher();
        boolean z2 = !z || isSecureSupported(context);
        Assertions.checkState(z2);
        return new DummySurfaceThread().init(z);
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
}
