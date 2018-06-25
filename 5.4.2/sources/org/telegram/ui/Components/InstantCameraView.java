package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaFormat;
import android.net.Uri;
import android.opengl.EGL14;
import android.opengl.EGLExt;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewOutlineProvider;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.google.android.gms.gcm.Task;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.PhotoEntry;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraInfo;
import org.telegram.messenger.camera.CameraSession;
import org.telegram.messenger.camera.Size;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.video.Mp4Movie;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC.InputEncryptedFile;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.VideoPlayer.VideoPlayerDelegate;

@TargetApi(18)
public class InstantCameraView extends FrameLayout implements NotificationCenterDelegate {
    private static final String FRAGMENT_SCREEN_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision lowp float;\nvarying vec2 vTextureCoord;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   gl_FragColor = texture2D(sTexture, vTextureCoord);\n}\n";
    private static final String FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\nprecision highp float;\nvarying vec2 vTextureCoord;\nuniform float scaleX;\nuniform float scaleY;\nuniform float alpha;\nuniform samplerExternalOES sTexture;\nvoid main() {\n   vec2 coord = vec2((vTextureCoord.x - 0.5) * scaleX, (vTextureCoord.y - 0.5) * scaleY);\n   float coef = ceil(clamp(0.2601 - dot(coord, coord), 0.0, 1.0));\n   vec3 color = texture2D(sTexture, vTextureCoord).rgb * coef + (1.0 - step(0.001, coef));\n   gl_FragColor = vec4(color * alpha, alpha);\n}\n";
    private static final int MSG_AUDIOFRAME_AVAILABLE = 3;
    private static final int MSG_START_RECORDING = 0;
    private static final int MSG_STOP_RECORDING = 1;
    private static final int MSG_VIDEOFRAME_AVAILABLE = 2;
    private static final String VERTEX_SHADER = "uniform mat4 uMVPMatrix;\nuniform mat4 uSTMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 vTextureCoord;\nvoid main() {\n   gl_Position = uMVPMatrix * aPosition;\n   vTextureCoord = (uSTMatrix * aTextureCoord).xy;\n}\n";
    private AnimatorSet animatorSet;
    private Size aspectRatio;
    private ChatActivity baseFragment;
    private FrameLayout cameraContainer;
    private File cameraFile;
    private volatile boolean cameraReady;
    private CameraSession cameraSession;
    private int[] cameraTexture = new int[1];
    private float cameraTextureAlpha = 1.0f;
    private CameraGLThread cameraThread;
    private boolean cancelled;
    private boolean deviceHasGoodCamera;
    private long duration;
    private InputEncryptedFile encryptedFile;
    private InputFile file;
    private boolean isFrontface = true;
    private byte[] iv;
    private byte[] key;
    private float[] mMVPMatrix;
    private float[] mSTMatrix;
    private float[] moldSTMatrix;
    private AnimatorSet muteAnimation;
    private ImageView muteImageView;
    private int[] oldCameraTexture = new int[1];
    private Paint paint;
    private Size pictureSize;
    private int[] position = new int[2];
    private Size previewSize;
    private float progress;
    private Timer progressTimer;
    private long recordStartTime;
    private long recordedTime;
    private boolean recording;
    private RectF rect;
    private boolean requestingPermissions;
    private float scaleX;
    private float scaleY;
    private CameraInfo selectedCamera;
    private long size;
    private ImageView switchCameraButton;
    private FloatBuffer textureBuffer;
    private TextureView textureView;
    private Runnable timerRunnable = new C44421();
    private FloatBuffer vertexBuffer;
    private VideoEditedInfo videoEditedInfo;
    private VideoPlayer videoPlayer;

    /* renamed from: org.telegram.ui.Components.InstantCameraView$1 */
    class C44421 implements Runnable {
        C44421() {
        }

        public void run() {
            if (InstantCameraView.this.recording) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordProgressChanged, new Object[]{Long.valueOf(InstantCameraView.this.duration = System.currentTimeMillis() - InstantCameraView.this.recordStartTime), Double.valueOf(0.0d)});
                AndroidUtilities.runOnUIThread(InstantCameraView.this.timerRunnable, 50);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$2 */
    class C44442 implements OnTouchListener {

        /* renamed from: org.telegram.ui.Components.InstantCameraView$2$1 */
        class C44431 extends AnimatorListenerAdapter {
            C44431() {
            }

            public void onAnimationEnd(Animator animator) {
                if (animator.equals(InstantCameraView.this.muteAnimation)) {
                    InstantCameraView.this.muteAnimation = null;
                }
            }
        }

        C44442() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            float f = 1.0f;
            if (motionEvent.getAction() == 0 && InstantCameraView.this.baseFragment != null) {
                if (InstantCameraView.this.videoPlayer != null) {
                    boolean z = !InstantCameraView.this.videoPlayer.isMuted();
                    InstantCameraView.this.videoPlayer.setMute(z);
                    if (InstantCameraView.this.muteAnimation != null) {
                        InstantCameraView.this.muteAnimation.cancel();
                    }
                    InstantCameraView.this.muteAnimation = new AnimatorSet();
                    AnimatorSet access$600 = InstantCameraView.this.muteAnimation;
                    Animator[] animatorArr = new Animator[3];
                    ImageView access$700 = InstantCameraView.this.muteImageView;
                    String str = "alpha";
                    float[] fArr = new float[1];
                    fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                    animatorArr[0] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$700 = InstantCameraView.this.muteImageView;
                    str = "scaleX";
                    fArr = new float[1];
                    fArr[0] = z ? 1.0f : 0.5f;
                    animatorArr[1] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$700 = InstantCameraView.this.muteImageView;
                    str = "scaleY";
                    fArr = new float[1];
                    if (!z) {
                        f = 0.5f;
                    }
                    fArr[0] = f;
                    animatorArr[2] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$600.playTogether(animatorArr);
                    InstantCameraView.this.muteAnimation.addListener(new C44431());
                    InstantCameraView.this.muteAnimation.setDuration(180);
                    InstantCameraView.this.muteAnimation.setInterpolator(new DecelerateInterpolator());
                    InstantCameraView.this.muteAnimation.start();
                } else {
                    InstantCameraView.this.baseFragment.checkRecordLocked();
                }
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$5 */
    class C44475 extends ViewOutlineProvider {
        C44475() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize);
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$7 */
    class C44507 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.InstantCameraView$7$1 */
        class C44491 extends AnimatorListenerAdapter {
            C44491() {
            }

            public void onAnimationEnd(Animator animator) {
                InstantCameraView.this.switchCameraButton.setImageResource(InstantCameraView.this.isFrontface ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "scaleX", new float[]{1.0f}).setDuration(100).start();
            }
        }

        C44507() {
        }

        public void onClick(View view) {
            if (InstantCameraView.this.cameraReady && InstantCameraView.this.cameraSession != null && InstantCameraView.this.cameraSession.isInitied() && InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.switchCamera();
                ObjectAnimator duration = ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED}).setDuration(100);
                duration.addListener(new C44491());
                duration.start();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$8 */
    class C44518 implements SurfaceTextureListener {
        C44518() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
            FileLog.e("camera surface available");
            if (InstantCameraView.this.cameraThread == null && surfaceTexture != null && !InstantCameraView.this.cancelled) {
                FileLog.e("start create thread");
                InstantCameraView.this.cameraThread = new CameraGLThread(surfaceTexture, i, i2);
            }
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            if (InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.cameraThread.shutdown(0);
                InstantCameraView.this.cameraThread = null;
            }
            if (InstantCameraView.this.cameraSession != null) {
                CameraController.getInstance().close(InstantCameraView.this.cameraSession, null, null);
            }
            return true;
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$9 */
    class C44529 extends AnimatorListenerAdapter {
        C44529() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(InstantCameraView.this.animatorSet)) {
                InstantCameraView.this.hideCamera(true);
                InstantCameraView.this.setVisibility(4);
            }
        }
    }

    private class AudioBufferInfo {
        byte[] buffer;
        boolean last;
        int lastWroteBuffer;
        long[] offset;
        int[] read;
        int results;

        private AudioBufferInfo() {
            this.buffer = new byte[CacheDataSink.DEFAULT_BUFFER_SIZE];
            this.offset = new long[10];
            this.read = new int[10];
        }
    }

    public class CameraGLThread extends DispatchQueue {
        private final int DO_REINIT_MESSAGE = 2;
        private final int DO_RENDER_MESSAGE = 0;
        private final int DO_SETSESSION_MESSAGE = 3;
        private final int DO_SHUTDOWN_MESSAGE = 1;
        private final int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private final int EGL_OPENGL_ES2_BIT = 4;
        private Integer cameraId = Integer.valueOf(0);
        private SurfaceTexture cameraSurface;
        private CameraSession currentSession;
        private int drawProgram;
        private EGL10 egl10;
        private EGLConfig eglConfig;
        private EGLContext eglContext;
        private EGLDisplay eglDisplay;
        private EGLSurface eglSurface;
        private GL gl;
        private boolean initied;
        private int positionHandle;
        private boolean recording;
        private int rotationAngle;
        private SurfaceTexture surfaceTexture;
        private int textureHandle;
        private int textureMatrixHandle;
        private int vertexMatrixHandle;
        private VideoRecorder videoEncoder;

        /* renamed from: org.telegram.ui.Components.InstantCameraView$CameraGLThread$1 */
        class C44531 implements OnFrameAvailableListener {
            C44531() {
            }

            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                CameraGLThread.this.requestRender();
            }
        }

        /* renamed from: org.telegram.ui.Components.InstantCameraView$CameraGLThread$2 */
        class C44542 implements OnFrameAvailableListener {
            C44542() {
            }

            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                CameraGLThread.this.requestRender();
            }
        }

        public CameraGLThread(SurfaceTexture surfaceTexture, int i, int i2) {
            super("CameraGLThread");
            this.surfaceTexture = surfaceTexture;
            int width = InstantCameraView.this.previewSize.getWidth();
            int height = InstantCameraView.this.previewSize.getHeight();
            float min = ((float) i) / ((float) Math.min(width, height));
            width = (int) (((float) width) * min);
            height = (int) (((float) height) * min);
            if (width > height) {
                InstantCameraView.this.scaleX = 1.0f;
                InstantCameraView.this.scaleY = ((float) width) / ((float) i2);
                return;
            }
            InstantCameraView.this.scaleX = ((float) height) / ((float) i);
            InstantCameraView.this.scaleY = 1.0f;
        }

        private boolean initGL() {
            FileLog.e("start init gl");
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
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12344}, eGLConfigArr, 1, iArr)) {
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
                            this.gl = this.eglContext.getGL();
                            float access$2000 = (1.0f / InstantCameraView.this.scaleX) / 2.0f;
                            float access$2100 = (1.0f / InstantCameraView.this.scaleY) / 2.0f;
                            float[] fArr = new float[]{-1.0f, -1.0f, BitmapDescriptorFactory.HUE_RED, 1.0f, -1.0f, BitmapDescriptorFactory.HUE_RED, -1.0f, 1.0f, BitmapDescriptorFactory.HUE_RED, 1.0f, 1.0f, BitmapDescriptorFactory.HUE_RED};
                            float[] fArr2 = new float[]{0.5f - access$2000, 0.5f - access$2100, 0.5f + access$2000, 0.5f - access$2100, 0.5f - access$2000, 0.5f + access$2100, access$2000 + 0.5f, access$2100 + 0.5f};
                            this.videoEncoder = new VideoRecorder();
                            InstantCameraView.this.vertexBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                            InstantCameraView.this.vertexBuffer.put(fArr).position(0);
                            InstantCameraView.this.textureBuffer = ByteBuffer.allocateDirect(fArr2.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                            InstantCameraView.this.textureBuffer.put(fArr2).position(0);
                            Matrix.setIdentityM(InstantCameraView.this.mSTMatrix, 0);
                            int access$2600 = InstantCameraView.this.loadShader(35633, InstantCameraView.VERTEX_SHADER);
                            int access$26002 = InstantCameraView.this.loadShader(35632, InstantCameraView.FRAGMENT_SCREEN_SHADER);
                            if (access$2600 == 0 || access$26002 == 0) {
                                FileLog.e("failed creating shader");
                                finish();
                                return false;
                            }
                            this.drawProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.drawProgram, access$2600);
                            GLES20.glAttachShader(this.drawProgram, access$26002);
                            GLES20.glLinkProgram(this.drawProgram);
                            int[] iArr2 = new int[1];
                            GLES20.glGetProgramiv(this.drawProgram, 35714, iArr2, 0);
                            if (iArr2[0] == 0) {
                                FileLog.e("failed link shader");
                                GLES20.glDeleteProgram(this.drawProgram);
                                this.drawProgram = 0;
                            } else {
                                this.positionHandle = GLES20.glGetAttribLocation(this.drawProgram, "aPosition");
                                this.textureHandle = GLES20.glGetAttribLocation(this.drawProgram, "aTextureCoord");
                                this.vertexMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uMVPMatrix");
                                this.textureMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uSTMatrix");
                            }
                            GLES20.glGenTextures(1, InstantCameraView.this.cameraTexture, 0);
                            GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[0]);
                            GLES20.glTexParameteri(36197, 10241, 9729);
                            GLES20.glTexParameteri(36197, Task.EXTRAS_LIMIT_BYTES, 9729);
                            GLES20.glTexParameteri(36197, 10242, 33071);
                            GLES20.glTexParameteri(36197, 10243, 33071);
                            Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                            this.cameraSurface = new SurfaceTexture(InstantCameraView.this.cameraTexture[0]);
                            this.cameraSurface.setOnFrameAvailableListener(new C44531());
                            InstantCameraView.this.createCamera(this.cameraSurface);
                            FileLog.e("gl initied");
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

        private void onDraw(Integer num) {
            if (!this.initied) {
                return;
            }
            if ((this.eglContext.equals(this.egl10.eglGetCurrentContext()) && this.eglSurface.equals(this.egl10.eglGetCurrentSurface(12377))) || this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                this.cameraSurface.updateTexImage();
                if (!this.recording) {
                    int i;
                    int i2;
                    String str = Build.DEVICE;
                    if (str == null) {
                        str = TtmlNode.ANONYMOUS_REGION_ID;
                    }
                    if (str.startsWith("zeroflte") || str.startsWith("zenlte")) {
                        i = 320;
                        i2 = 600000;
                    } else {
                        i = PsExtractor.VIDEO_STREAM_MASK;
                        i2 = 400000;
                    }
                    this.videoEncoder.startRecording(InstantCameraView.this.cameraFile, i, i2, EGL14.eglGetCurrentContext());
                    this.recording = true;
                    i2 = this.currentSession.getCurrentOrientation();
                    if (i2 == 90 || i2 == 270) {
                        float access$2000 = InstantCameraView.this.scaleX;
                        InstantCameraView.this.scaleX = InstantCameraView.this.scaleY;
                        InstantCameraView.this.scaleY = access$2000;
                    }
                }
                this.videoEncoder.frameAvailable(this.cameraSurface, num, System.nanoTime());
                this.cameraSurface.getTransformMatrix(InstantCameraView.this.mSTMatrix);
                GLES20.glUseProgram(this.drawProgram);
                GLES20.glActiveTexture(33984);
                GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[0]);
                GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 12, InstantCameraView.this.vertexBuffer);
                GLES20.glEnableVertexAttribArray(this.positionHandle);
                GLES20.glVertexAttribPointer(this.textureHandle, 2, 5126, false, 8, InstantCameraView.this.textureBuffer);
                GLES20.glEnableVertexAttribArray(this.textureHandle);
                GLES20.glUniformMatrix4fv(this.textureMatrixHandle, 1, false, InstantCameraView.this.mSTMatrix, 0);
                GLES20.glUniformMatrix4fv(this.vertexMatrixHandle, 1, false, InstantCameraView.this.mMVPMatrix, 0);
                GLES20.glDrawArrays(5, 0, 4);
                GLES20.glDisableVertexAttribArray(this.positionHandle);
                GLES20.glDisableVertexAttribArray(this.textureHandle);
                GLES20.glBindTexture(36197, 0);
                GLES20.glUseProgram(0);
                this.egl10.eglSwapBuffers(this.eglDisplay, this.eglSurface);
                return;
            }
            FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
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

        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    onDraw((Integer) message.obj);
                    return;
                case 1:
                    finish();
                    if (this.recording) {
                        this.videoEncoder.stopRecording(message.arg1);
                    }
                    Looper myLooper = Looper.myLooper();
                    if (myLooper != null) {
                        myLooper.quit();
                        return;
                    }
                    return;
                case 2:
                    if (this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                        if (this.cameraSurface != null) {
                            this.cameraSurface.getTransformMatrix(InstantCameraView.this.moldSTMatrix);
                            this.cameraSurface.setOnFrameAvailableListener(null);
                            this.cameraSurface.release();
                            InstantCameraView.this.oldCameraTexture[0] = InstantCameraView.this.cameraTexture[0];
                            InstantCameraView.this.cameraTextureAlpha = BitmapDescriptorFactory.HUE_RED;
                            InstantCameraView.this.cameraTexture[0] = 0;
                        }
                        Integer num = this.cameraId;
                        this.cameraId = Integer.valueOf(this.cameraId.intValue() + 1);
                        InstantCameraView.this.cameraReady = false;
                        GLES20.glGenTextures(1, InstantCameraView.this.cameraTexture, 0);
                        GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[0]);
                        GLES20.glTexParameteri(36197, 10241, 9729);
                        GLES20.glTexParameteri(36197, Task.EXTRAS_LIMIT_BYTES, 9729);
                        GLES20.glTexParameteri(36197, 10242, 33071);
                        GLES20.glTexParameteri(36197, 10243, 33071);
                        this.cameraSurface = new SurfaceTexture(InstantCameraView.this.cameraTexture[0]);
                        this.cameraSurface.setOnFrameAvailableListener(new C44542());
                        InstantCameraView.this.createCamera(this.cameraSurface);
                        return;
                    }
                    FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    return;
                case 3:
                    FileLog.d("set gl rednderer session");
                    CameraSession cameraSession = (CameraSession) message.obj;
                    if (this.currentSession == cameraSession) {
                        this.rotationAngle = this.currentSession.getWorldAngle();
                        Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                        if (this.rotationAngle != 0) {
                            Matrix.rotateM(InstantCameraView.this.mMVPMatrix, 0, (float) this.rotationAngle, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f);
                            return;
                        }
                        return;
                    }
                    this.currentSession = cameraSession;
                    return;
                default:
                    return;
            }
        }

        public void reinitForNewCamera() {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(2), 0);
            }
        }

        public void requestRender() {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(0, this.cameraId), 0);
            }
        }

        public void run() {
            this.initied = initGL();
            super.run();
        }

        public void setCurrentSession(CameraSession cameraSession) {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(3, cameraSession), 0);
            }
        }

        public void shutdown(int i) {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(1, i, 0), 0);
            }
        }
    }

    private static class EncoderHandler extends Handler {
        private WeakReference<VideoRecorder> mWeakEncoder;

        public EncoderHandler(VideoRecorder videoRecorder) {
            this.mWeakEncoder = new WeakReference(videoRecorder);
        }

        public void exit() {
            Looper.myLooper().quit();
        }

        public void handleMessage(Message message) {
            int i = message.what;
            Object obj = message.obj;
            VideoRecorder videoRecorder = (VideoRecorder) this.mWeakEncoder.get();
            if (videoRecorder != null) {
                switch (i) {
                    case 0:
                        try {
                            FileLog.e("start encoder");
                            videoRecorder.prepareEncoder();
                            return;
                        } catch (Throwable e) {
                            FileLog.e(e);
                            videoRecorder.handleStopRecording(0);
                            Looper.myLooper().quit();
                            return;
                        }
                    case 1:
                        FileLog.e("stop encoder");
                        videoRecorder.handleStopRecording(message.arg1);
                        return;
                    case 2:
                        videoRecorder.handleVideoFrameAvailable((((long) message.arg1) << 32) | (((long) message.arg2) & 4294967295L), (Integer) message.obj);
                        return;
                    case 3:
                        videoRecorder.handleAudioFrameAvailable((AudioBufferInfo) message.obj);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class VideoRecorder implements Runnable {
        private static final String AUDIO_MIME_TYPE = "audio/mp4a-latm";
        private static final int FRAME_RATE = 30;
        private static final int IFRAME_INTERVAL = 1;
        private static final String VIDEO_MIME_TYPE = "video/avc";
        private int alphaHandle;
        private BufferInfo audioBufferInfo;
        private MediaCodec audioEncoder;
        private AudioRecord audioRecorder;
        private long audioStartTime;
        private int audioTrackIndex;
        private boolean blendEnabled;
        private ArrayBlockingQueue<AudioBufferInfo> buffers;
        private ArrayList<AudioBufferInfo> buffersToWrite;
        private long currentTimestamp;
        private int drawProgram;
        private android.opengl.EGLConfig eglConfig;
        private android.opengl.EGLContext eglContext;
        private android.opengl.EGLDisplay eglDisplay;
        private android.opengl.EGLSurface eglSurface;
        private volatile EncoderHandler handler;
        private Integer lastCameraId;
        private long lastCommitedFrameTime;
        private long lastTimestamp;
        private MP4Builder mediaMuxer;
        private int positionHandle;
        private boolean ready;
        private Runnable recorderRunnable;
        private volatile boolean running;
        private int scaleXHandle;
        private int scaleYHandle;
        private volatile int sendWhenDone;
        private android.opengl.EGLContext sharedEglContext;
        private boolean skippedFirst;
        private long skippedTime;
        private Surface surface;
        private final Object sync;
        private int textureHandle;
        private int textureMatrixHandle;
        private int vertexMatrixHandle;
        private int videoBitrate;
        private BufferInfo videoBufferInfo;
        private boolean videoConvertFirstWrite;
        private MediaCodec videoEncoder;
        private File videoFile;
        private int videoHeight;
        private int videoTrackIndex;
        private int videoWidth;
        private int zeroTimeStamps;

        /* renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$1 */
        class C44551 implements Runnable {
            C44551() {
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r12 = this;
                r11 = 10;
                r4 = 0;
                r1 = 1;
                r0 = r4;
            L_0x0005:
                if (r0 != 0) goto L_0x002c;
            L_0x0007:
                r2 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r2 = r2.running;
                if (r2 != 0) goto L_0x0052;
            L_0x000f:
                r2 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r2 = r2.audioRecorder;
                r2 = r2.getRecordingState();
                if (r2 == r1) goto L_0x0052;
            L_0x001b:
                r2 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x004f }
                r2 = r2.audioRecorder;	 Catch:{ Exception -> 0x004f }
                r2.stop();	 Catch:{ Exception -> 0x004f }
            L_0x0024:
                r2 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r2 = r2.sendWhenDone;
                if (r2 != 0) goto L_0x0052;
            L_0x002c:
                r0 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x00ec }
                r0 = r0.audioRecorder;	 Catch:{ Exception -> 0x00ec }
                r0.release();	 Catch:{ Exception -> 0x00ec }
            L_0x0035:
                r0 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r0 = r0.handler;
                r2 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r2 = r2.handler;
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = r3.sendWhenDone;
                r1 = r2.obtainMessage(r1, r3, r4);
                r0.sendMessage(r1);
                return;
            L_0x004f:
                r0 = move-exception;
                r0 = r1;
                goto L_0x0024;
            L_0x0052:
                r2 = r0;
                r0 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r0 = r0.buffers;
                r0 = r0.isEmpty();
                if (r0 == 0) goto L_0x00be;
            L_0x005f:
                r0 = new org.telegram.ui.Components.InstantCameraView$AudioBufferInfo;
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = org.telegram.ui.Components.InstantCameraView.this;
                r5 = 0;
                r0.<init>();
            L_0x0069:
                r0.lastWroteBuffer = r4;
                r0.results = r11;
                r3 = r4;
            L_0x006e:
                if (r3 >= r11) goto L_0x0092;
            L_0x0070:
                r6 = java.lang.System.nanoTime();
                r5 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r5 = r5.audioRecorder;
                r8 = r0.buffer;
                r9 = r3 * 2048;
                r10 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
                r5 = r5.read(r8, r9, r10);
                if (r5 > 0) goto L_0x00cb;
            L_0x0086:
                r0.results = r3;
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = r3.running;
                if (r3 != 0) goto L_0x0092;
            L_0x0090:
                r0.last = r1;
            L_0x0092:
                r3 = r0.results;
                if (r3 >= 0) goto L_0x009a;
            L_0x0096:
                r3 = r0.last;
                if (r3 == 0) goto L_0x00d6;
            L_0x009a:
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = r3.running;
                if (r3 != 0) goto L_0x00a7;
            L_0x00a2:
                r3 = r0.results;
                if (r3 >= r11) goto L_0x00a7;
            L_0x00a6:
                r2 = r1;
            L_0x00a7:
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = r3.handler;
                r5 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r5 = r5.handler;
                r6 = 3;
                r0 = r5.obtainMessage(r6, r0);
                r3.sendMessage(r0);
            L_0x00bb:
                r0 = r2;
                goto L_0x0005;
            L_0x00be:
                r0 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r0 = r0.buffers;
                r0 = r0.poll();
                r0 = (org.telegram.ui.Components.InstantCameraView.AudioBufferInfo) r0;
                goto L_0x0069;
            L_0x00cb:
                r8 = r0.offset;
                r8[r3] = r6;
                r6 = r0.read;
                r6[r3] = r5;
                r3 = r3 + 1;
                goto L_0x006e;
            L_0x00d6:
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r3 = r3.running;
                if (r3 != 0) goto L_0x00e0;
            L_0x00de:
                r2 = r1;
                goto L_0x00bb;
            L_0x00e0:
                r3 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x00ea }
                r3 = r3.buffers;	 Catch:{ Exception -> 0x00ea }
                r3.put(r0);	 Catch:{ Exception -> 0x00ea }
                goto L_0x00bb;
            L_0x00ea:
                r0 = move-exception;
                goto L_0x00bb;
            L_0x00ec:
                r0 = move-exception;
                org.telegram.messenger.FileLog.e(r0);
                goto L_0x0035;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.1.run():void");
            }
        }

        /* renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$3 */
        class C44583 implements Runnable {
            C44583() {
            }

            public void run() {
                if (!InstantCameraView.this.cancelled) {
                    try {
                        ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(50);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    AndroidUtilities.lockOrientation(InstantCameraView.this.baseFragment.getParentActivity());
                    InstantCameraView.this.recording = true;
                    InstantCameraView.this.recordStartTime = System.currentTimeMillis();
                    AndroidUtilities.runOnUIThread(InstantCameraView.this.timerRunnable);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStarted, new Object[0]);
                }
            }
        }

        private VideoRecorder() {
            this.videoConvertFirstWrite = true;
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            this.buffersToWrite = new ArrayList();
            this.videoTrackIndex = -5;
            this.audioTrackIndex = -5;
            this.audioStartTime = -1;
            this.currentTimestamp = 0;
            this.lastTimestamp = -1;
            this.sync = new Object();
            this.lastCameraId = Integer.valueOf(0);
            this.buffers = new ArrayBlockingQueue(10);
            this.recorderRunnable = new C44551();
        }

        private void didWriteData(File file, boolean z) {
            if (this.videoConvertFirstWrite) {
                FileLoader.getInstance().uploadFile(file.toString(), false, false, 1, ConnectionsManager.FileTypeVideo);
                this.videoConvertFirstWrite = false;
                return;
            }
            FileLoader.getInstance().checkUploadNewDataAvailable(file.toString(), false, z ? file.length() : 0);
        }

        private void handleAudioFrameAvailable(AudioBufferInfo audioBufferInfo) {
            if (this.audioStartTime == -1) {
                this.audioStartTime = audioBufferInfo.offset[0];
            }
            this.buffersToWrite.add(audioBufferInfo);
            AudioBufferInfo audioBufferInfo2 = this.buffersToWrite.size() > 1 ? (AudioBufferInfo) this.buffersToWrite.get(0) : audioBufferInfo;
            try {
                drainEncoder(false);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            boolean z = false;
            while (audioBufferInfo2 != null) {
                boolean z2;
                AudioBufferInfo audioBufferInfo3;
                int dequeueInputBuffer = this.audioEncoder.dequeueInputBuffer(0);
                if (dequeueInputBuffer >= 0) {
                    ByteBuffer inputBuffer;
                    AudioBufferInfo audioBufferInfo4;
                    boolean z3;
                    if (VERSION.SDK_INT >= 21) {
                        inputBuffer = this.audioEncoder.getInputBuffer(dequeueInputBuffer);
                    } else {
                        try {
                            ByteBuffer byteBuffer = this.audioEncoder.getInputBuffers()[dequeueInputBuffer];
                            byteBuffer.clear();
                            inputBuffer = byteBuffer;
                        } catch (Throwable th) {
                            FileLog.e(th);
                            return;
                        }
                    }
                    long j = audioBufferInfo2.offset[audioBufferInfo2.lastWroteBuffer];
                    for (int i = audioBufferInfo2.lastWroteBuffer; i <= audioBufferInfo2.results; i++) {
                        if (i < audioBufferInfo2.results) {
                            if (inputBuffer.remaining() < audioBufferInfo2.read[i]) {
                                audioBufferInfo2.lastWroteBuffer = i;
                                audioBufferInfo4 = null;
                                z3 = z;
                                break;
                            }
                            inputBuffer.put(audioBufferInfo2.buffer, i * 2048, audioBufferInfo2.read[i]);
                        }
                        if (i >= audioBufferInfo2.results - 1) {
                            this.buffersToWrite.remove(audioBufferInfo2);
                            if (this.running) {
                                this.buffers.put(audioBufferInfo2);
                            }
                            if (this.buffersToWrite.isEmpty()) {
                                audioBufferInfo4 = null;
                                z3 = audioBufferInfo2.last;
                                break;
                            }
                            audioBufferInfo2 = (AudioBufferInfo) this.buffersToWrite.get(0);
                        }
                    }
                    z3 = z;
                    audioBufferInfo4 = audioBufferInfo2;
                    this.audioEncoder.queueInputBuffer(dequeueInputBuffer, 0, inputBuffer.position(), j == 0 ? 0 : (j - this.audioStartTime) / 1000, z3 ? 4 : 0);
                    z2 = z3;
                    audioBufferInfo3 = audioBufferInfo4;
                } else {
                    audioBufferInfo3 = audioBufferInfo2;
                    z2 = z;
                }
                z = z2;
                audioBufferInfo2 = audioBufferInfo3;
            }
        }

        private void handleStopRecording(final int i) {
            if (this.running) {
                this.sendWhenDone = i;
                this.running = false;
                return;
            }
            try {
                drainEncoder(true);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (this.videoEncoder != null) {
                try {
                    this.videoEncoder.stop();
                    this.videoEncoder.release();
                    this.videoEncoder = null;
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            if (this.audioEncoder != null) {
                try {
                    this.audioEncoder.stop();
                    this.audioEncoder.release();
                    this.audioEncoder = null;
                } catch (Throwable e22) {
                    FileLog.e(e22);
                }
            }
            if (this.mediaMuxer != null) {
                try {
                    this.mediaMuxer.finishMovie();
                } catch (Throwable e222) {
                    FileLog.e(e222);
                }
            }
            if (i != 0) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$2$1 */
                    class C44561 implements VideoPlayerDelegate {
                        C44561() {
                        }

                        public void onError(Exception exception) {
                            FileLog.e(exception);
                        }

                        public void onRenderedFirstFrame() {
                        }

                        public void onStateChanged(boolean z, int i) {
                            long j = 0;
                            if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoPlayer.isPlaying() && i == 4) {
                                VideoPlayer access$500 = InstantCameraView.this.videoPlayer;
                                if (InstantCameraView.this.videoEditedInfo.startTime > 0) {
                                    j = InstantCameraView.this.videoEditedInfo.startTime;
                                }
                                access$500.seekTo(j);
                            }
                        }

                        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                            return false;
                        }

                        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
                        }

                        public void onVideoSizeChanged(int i, int i2, int i3, float f) {
                        }
                    }

                    public void run() {
                        InstantCameraView.this.videoEditedInfo = new VideoEditedInfo();
                        InstantCameraView.this.videoEditedInfo.roundVideo = true;
                        InstantCameraView.this.videoEditedInfo.startTime = -1;
                        InstantCameraView.this.videoEditedInfo.endTime = -1;
                        InstantCameraView.this.videoEditedInfo.file = InstantCameraView.this.file;
                        InstantCameraView.this.videoEditedInfo.encryptedFile = InstantCameraView.this.encryptedFile;
                        InstantCameraView.this.videoEditedInfo.key = InstantCameraView.this.key;
                        InstantCameraView.this.videoEditedInfo.iv = InstantCameraView.this.iv;
                        InstantCameraView.this.videoEditedInfo.estimatedSize = InstantCameraView.this.size;
                        VideoEditedInfo access$1900 = InstantCameraView.this.videoEditedInfo;
                        InstantCameraView.this.videoEditedInfo.originalWidth = PsExtractor.VIDEO_STREAM_MASK;
                        access$1900.resultWidth = PsExtractor.VIDEO_STREAM_MASK;
                        access$1900 = InstantCameraView.this.videoEditedInfo;
                        InstantCameraView.this.videoEditedInfo.originalHeight = PsExtractor.VIDEO_STREAM_MASK;
                        access$1900.resultHeight = PsExtractor.VIDEO_STREAM_MASK;
                        InstantCameraView.this.videoEditedInfo.originalPath = VideoRecorder.this.videoFile.getAbsolutePath();
                        if (i == 1) {
                            InstantCameraView.this.baseFragment.sendMedia(new PhotoEntry(0, 0, 0, VideoRecorder.this.videoFile.getAbsolutePath(), 0, true), InstantCameraView.this.videoEditedInfo);
                        } else {
                            InstantCameraView.this.videoPlayer = new VideoPlayer();
                            InstantCameraView.this.videoPlayer.setDelegate(new C44561());
                            InstantCameraView.this.videoPlayer.setTextureView(InstantCameraView.this.textureView);
                            InstantCameraView.this.videoPlayer.preparePlayer(Uri.fromFile(VideoRecorder.this.videoFile), "other");
                            InstantCameraView.this.videoPlayer.play();
                            InstantCameraView.this.videoPlayer.setMute(true);
                            InstantCameraView.this.startProgressTimer();
                            AnimatorSet animatorSet = new AnimatorSet();
                            r1 = new Animator[3];
                            r1[0] = ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            r1[1] = ObjectAnimator.ofInt(InstantCameraView.this.paint, "alpha", new int[]{0});
                            r1[2] = ObjectAnimator.ofFloat(InstantCameraView.this.muteImageView, "alpha", new float[]{1.0f});
                            animatorSet.playTogether(r1);
                            animatorSet.setDuration(180);
                            animatorSet.setInterpolator(new DecelerateInterpolator());
                            animatorSet.start();
                            InstantCameraView.this.videoEditedInfo.estimatedDuration = InstantCameraView.this.duration;
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.audioDidSent, new Object[]{InstantCameraView.this.videoEditedInfo, VideoRecorder.this.videoFile.getAbsolutePath()});
                        }
                        VideoRecorder.this.didWriteData(VideoRecorder.this.videoFile, true);
                    }
                });
            } else {
                FileLoader.getInstance().cancelUploadFile(this.videoFile.getAbsolutePath(), false);
                this.videoFile.delete();
            }
            EGL14.eglDestroySurface(this.eglDisplay, this.eglSurface);
            this.eglSurface = EGL14.EGL_NO_SURFACE;
            if (this.surface != null) {
                this.surface.release();
                this.surface = null;
            }
            if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
                EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
                EGL14.eglReleaseThread();
                EGL14.eglTerminate(this.eglDisplay);
            }
            this.eglDisplay = EGL14.EGL_NO_DISPLAY;
            this.eglContext = EGL14.EGL_NO_CONTEXT;
            this.eglConfig = null;
            this.handler.exit();
        }

        private void handleVideoFrameAvailable(long j, Integer num) {
            long j2;
            long currentTimeMillis;
            try {
                drainEncoder(false);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (!this.lastCameraId.equals(num)) {
                this.lastTimestamp = -1;
                this.lastCameraId = num;
            }
            if (this.lastTimestamp == -1) {
                this.lastTimestamp = j;
                if (this.currentTimestamp != 0) {
                    j2 = 0;
                    currentTimeMillis = C3446C.MICROS_PER_SECOND * (System.currentTimeMillis() - this.lastCommitedFrameTime);
                } else {
                    currentTimeMillis = 0;
                    j2 = 0;
                }
            } else {
                currentTimeMillis = j - this.lastTimestamp;
                this.lastTimestamp = j;
                j2 = currentTimeMillis;
            }
            this.lastCommitedFrameTime = System.currentTimeMillis();
            if (!this.skippedFirst) {
                this.skippedTime += currentTimeMillis;
                if (this.skippedTime >= 200000000) {
                    this.skippedFirst = true;
                } else {
                    return;
                }
            }
            this.currentTimestamp = currentTimeMillis + this.currentTimestamp;
            GLES20.glUseProgram(this.drawProgram);
            GLES20.glVertexAttribPointer(this.positionHandle, 3, 5126, false, 12, InstantCameraView.this.vertexBuffer);
            GLES20.glEnableVertexAttribArray(this.positionHandle);
            GLES20.glVertexAttribPointer(this.textureHandle, 2, 5126, false, 8, InstantCameraView.this.textureBuffer);
            GLES20.glEnableVertexAttribArray(this.textureHandle);
            GLES20.glUniform1f(this.scaleXHandle, InstantCameraView.this.scaleX);
            GLES20.glUniform1f(this.scaleYHandle, InstantCameraView.this.scaleY);
            GLES20.glUniformMatrix4fv(this.vertexMatrixHandle, 1, false, InstantCameraView.this.mMVPMatrix, 0);
            GLES20.glActiveTexture(33984);
            if (InstantCameraView.this.oldCameraTexture[0] != 0) {
                if (!this.blendEnabled) {
                    GLES20.glEnable(3042);
                    this.blendEnabled = true;
                }
                GLES20.glUniformMatrix4fv(this.textureMatrixHandle, 1, false, InstantCameraView.this.moldSTMatrix, 0);
                GLES20.glUniform1f(this.alphaHandle, 1.0f);
                GLES20.glBindTexture(36197, InstantCameraView.this.oldCameraTexture[0]);
                GLES20.glDrawArrays(5, 0, 4);
            }
            GLES20.glUniformMatrix4fv(this.textureMatrixHandle, 1, false, InstantCameraView.this.mSTMatrix, 0);
            GLES20.glUniform1f(this.alphaHandle, InstantCameraView.this.cameraTextureAlpha);
            GLES20.glBindTexture(36197, InstantCameraView.this.cameraTexture[0]);
            GLES20.glDrawArrays(5, 0, 4);
            GLES20.glDisableVertexAttribArray(this.positionHandle);
            GLES20.glDisableVertexAttribArray(this.textureHandle);
            GLES20.glBindTexture(36197, 0);
            GLES20.glUseProgram(0);
            FileLog.e("frame time = " + this.currentTimestamp);
            EGLExt.eglPresentationTimeANDROID(this.eglDisplay, this.eglSurface, this.currentTimestamp);
            EGL14.eglSwapBuffers(this.eglDisplay, this.eglSurface);
            if (InstantCameraView.this.oldCameraTexture[0] != 0 && InstantCameraView.this.cameraTextureAlpha < 1.0f) {
                InstantCameraView.this.cameraTextureAlpha = InstantCameraView.this.cameraTextureAlpha + (((float) j2) / 2.0E8f);
                if (InstantCameraView.this.cameraTextureAlpha > 1.0f) {
                    GLES20.glDisable(3042);
                    this.blendEnabled = false;
                    InstantCameraView.this.cameraTextureAlpha = 1.0f;
                    GLES20.glDeleteTextures(1, InstantCameraView.this.oldCameraTexture, 0);
                    InstantCameraView.this.oldCameraTexture[0] = 0;
                    if (!InstantCameraView.this.cameraReady) {
                        InstantCameraView.this.cameraReady = true;
                    }
                }
            } else if (!InstantCameraView.this.cameraReady) {
                InstantCameraView.this.cameraReady = true;
            }
        }

        private void prepareEncoder() {
            try {
                int minBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
                if (minBufferSize <= 0) {
                    minBufferSize = 3584;
                }
                int i = 49152;
                if (49152 < minBufferSize) {
                    i = (((minBufferSize / 2048) + 1) * 2048) * 2;
                }
                for (minBufferSize = 0; minBufferSize < 3; minBufferSize++) {
                    this.buffers.add(new AudioBufferInfo());
                }
                this.audioRecorder = new AudioRecord(1, 44100, 16, 2, i);
                this.audioRecorder.startRecording();
                new Thread(this.recorderRunnable).start();
                this.audioBufferInfo = new BufferInfo();
                this.videoBufferInfo = new BufferInfo();
                MediaFormat mediaFormat = new MediaFormat();
                mediaFormat.setString("mime", "audio/mp4a-latm");
                mediaFormat.setInteger("aac-profile", 2);
                mediaFormat.setInteger("sample-rate", 44100);
                mediaFormat.setInteger("channel-count", 1);
                mediaFormat.setInteger("bitrate", 32000);
                mediaFormat.setInteger("max-input-size", CacheDataSink.DEFAULT_BUFFER_SIZE);
                this.audioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
                this.audioEncoder.configure(mediaFormat, null, null, 1);
                this.audioEncoder.start();
                this.videoEncoder = MediaCodec.createEncoderByType("video/avc");
                mediaFormat = MediaFormat.createVideoFormat("video/avc", this.videoWidth, this.videoHeight);
                mediaFormat.setInteger("color-format", 2130708361);
                mediaFormat.setInteger("bitrate", this.videoBitrate);
                mediaFormat.setInteger("frame-rate", 30);
                mediaFormat.setInteger("i-frame-interval", 1);
                this.videoEncoder.configure(mediaFormat, null, null, 1);
                this.surface = this.videoEncoder.createInputSurface();
                this.videoEncoder.start();
                Mp4Movie mp4Movie = new Mp4Movie();
                mp4Movie.setCacheFile(this.videoFile);
                mp4Movie.setRotation(0);
                mp4Movie.setSize(this.videoWidth, this.videoHeight);
                this.mediaMuxer = new MP4Builder().createMovie(mp4Movie);
                AndroidUtilities.runOnUIThread(new C44583());
                if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                    throw new RuntimeException("EGL already set up");
                }
                this.eglDisplay = EGL14.eglGetDisplay(0);
                if (this.eglDisplay == EGL14.EGL_NO_DISPLAY) {
                    throw new RuntimeException("unable to get EGL14 display");
                }
                int[] iArr = new int[2];
                if (EGL14.eglInitialize(this.eglDisplay, iArr, 0, iArr, 1)) {
                    if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
                        android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
                        if (EGL14.eglChooseConfig(this.eglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12610, 1, 12344}, 0, eGLConfigArr, 0, eGLConfigArr.length, new int[1], 0)) {
                            this.eglContext = EGL14.eglCreateContext(this.eglDisplay, eGLConfigArr[0], this.sharedEglContext, new int[]{12440, 2, 12344}, 0);
                            this.eglConfig = eGLConfigArr[0];
                        } else {
                            throw new RuntimeException("Unable to find a suitable EGLConfig");
                        }
                    }
                    EGL14.eglQueryContext(this.eglDisplay, this.eglContext, 12440, new int[1], 0);
                    if (this.eglSurface != EGL14.EGL_NO_SURFACE) {
                        throw new IllegalStateException("surface already created");
                    }
                    this.eglSurface = EGL14.eglCreateWindowSurface(this.eglDisplay, this.eglConfig, this.surface, new int[]{12344}, 0);
                    if (this.eglSurface == null) {
                        throw new RuntimeException("surface was null");
                    } else if (EGL14.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                        GLES20.glBlendFunc(770, 771);
                        minBufferSize = InstantCameraView.this.loadShader(35633, InstantCameraView.VERTEX_SHADER);
                        int access$2600 = InstantCameraView.this.loadShader(35632, InstantCameraView.FRAGMENT_SHADER);
                        if (minBufferSize != 0 && access$2600 != 0) {
                            this.drawProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.drawProgram, minBufferSize);
                            GLES20.glAttachShader(this.drawProgram, access$2600);
                            GLES20.glLinkProgram(this.drawProgram);
                            iArr = new int[1];
                            GLES20.glGetProgramiv(this.drawProgram, 35714, iArr, 0);
                            if (iArr[0] == 0) {
                                GLES20.glDeleteProgram(this.drawProgram);
                                this.drawProgram = 0;
                                return;
                            }
                            this.positionHandle = GLES20.glGetAttribLocation(this.drawProgram, "aPosition");
                            this.textureHandle = GLES20.glGetAttribLocation(this.drawProgram, "aTextureCoord");
                            this.scaleXHandle = GLES20.glGetUniformLocation(this.drawProgram, "scaleX");
                            this.scaleYHandle = GLES20.glGetUniformLocation(this.drawProgram, "scaleY");
                            this.alphaHandle = GLES20.glGetUniformLocation(this.drawProgram, "alpha");
                            this.vertexMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uMVPMatrix");
                            this.textureMatrixHandle = GLES20.glGetUniformLocation(this.drawProgram, "uSTMatrix");
                            return;
                        }
                        return;
                    } else {
                        FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(EGL14.eglGetError()));
                        throw new RuntimeException("eglMakeCurrent failed");
                    }
                }
                this.eglDisplay = null;
                throw new RuntimeException("unable to initialize EGL14");
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public void drainEncoder(boolean z) {
            if (z) {
                this.videoEncoder.signalEndOfInputStream();
            }
            ByteBuffer[] outputBuffers = VERSION.SDK_INT < 21 ? this.videoEncoder.getOutputBuffers() : null;
            while (true) {
                int dequeueOutputBuffer = this.videoEncoder.dequeueOutputBuffer(this.videoBufferInfo, 10000);
                if (dequeueOutputBuffer == -1) {
                    if (!z) {
                        break;
                    }
                } else if (dequeueOutputBuffer == -3) {
                    if (VERSION.SDK_INT < 21) {
                        outputBuffers = this.videoEncoder.getOutputBuffers();
                    }
                } else if (dequeueOutputBuffer == -2) {
                    MediaFormat outputFormat = this.videoEncoder.getOutputFormat();
                    if (this.videoTrackIndex == -5) {
                        this.videoTrackIndex = this.mediaMuxer.addTrack(outputFormat, false);
                    }
                } else if (dequeueOutputBuffer < 0) {
                    continue;
                } else {
                    ByteBuffer outputBuffer = VERSION.SDK_INT < 21 ? outputBuffers[dequeueOutputBuffer] : this.videoEncoder.getOutputBuffer(dequeueOutputBuffer);
                    if (outputBuffer == null) {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer + " was null");
                    }
                    if (this.videoBufferInfo.size > 1) {
                        if ((this.videoBufferInfo.flags & 2) == 0) {
                            if (this.mediaMuxer.writeSampleData(this.videoTrackIndex, outputBuffer, this.videoBufferInfo, true)) {
                                didWriteData(this.videoFile, false);
                            }
                        } else if (this.videoTrackIndex == -5) {
                            ByteBuffer allocate;
                            byte[] bArr = new byte[this.videoBufferInfo.size];
                            outputBuffer.limit(this.videoBufferInfo.offset + this.videoBufferInfo.size);
                            outputBuffer.position(this.videoBufferInfo.offset);
                            outputBuffer.get(bArr);
                            int i = this.videoBufferInfo.size - 1;
                            while (i >= 0 && i > 3) {
                                if (bArr[i] == (byte) 1 && bArr[i - 1] == (byte) 0 && bArr[i - 2] == (byte) 0 && bArr[i - 3] == (byte) 0) {
                                    allocate = ByteBuffer.allocate(i - 3);
                                    outputBuffer = ByteBuffer.allocate(this.videoBufferInfo.size - (i - 3));
                                    allocate.put(bArr, 0, i - 3).position(0);
                                    outputBuffer.put(bArr, i - 3, this.videoBufferInfo.size - (i - 3)).position(0);
                                    break;
                                }
                                i--;
                            }
                            outputBuffer = null;
                            allocate = null;
                            MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", this.videoWidth, this.videoHeight);
                            if (!(allocate == null || outputBuffer == null)) {
                                createVideoFormat.setByteBuffer("csd-0", allocate);
                                createVideoFormat.setByteBuffer("csd-1", outputBuffer);
                            }
                            this.videoTrackIndex = this.mediaMuxer.addTrack(createVideoFormat, false);
                        }
                    }
                    this.videoEncoder.releaseOutputBuffer(dequeueOutputBuffer, false);
                    if ((this.videoBufferInfo.flags & 4) != 0) {
                        break;
                    }
                }
            }
            if (VERSION.SDK_INT < 21) {
                outputBuffers = this.audioEncoder.getOutputBuffers();
            }
            while (true) {
                int dequeueOutputBuffer2 = this.audioEncoder.dequeueOutputBuffer(this.audioBufferInfo, 0);
                if (dequeueOutputBuffer2 == -1) {
                    if (!z) {
                        return;
                    }
                    if (!this.running && this.sendWhenDone == 0) {
                        return;
                    }
                } else if (dequeueOutputBuffer2 == -3) {
                    if (VERSION.SDK_INT < 21) {
                        outputBuffers = this.audioEncoder.getOutputBuffers();
                    }
                } else if (dequeueOutputBuffer2 == -2) {
                    MediaFormat outputFormat2 = this.audioEncoder.getOutputFormat();
                    if (this.audioTrackIndex == -5) {
                        this.audioTrackIndex = this.mediaMuxer.addTrack(outputFormat2, true);
                    }
                } else if (dequeueOutputBuffer2 < 0) {
                    continue;
                } else {
                    ByteBuffer outputBuffer2 = VERSION.SDK_INT < 21 ? outputBuffers[dequeueOutputBuffer2] : this.audioEncoder.getOutputBuffer(dequeueOutputBuffer2);
                    if (outputBuffer2 == null) {
                        throw new RuntimeException("encoderOutputBuffer " + dequeueOutputBuffer2 + " was null");
                    }
                    if ((this.audioBufferInfo.flags & 2) != 0) {
                        this.audioBufferInfo.size = 0;
                    }
                    if (this.audioBufferInfo.size != 0 && this.mediaMuxer.writeSampleData(this.audioTrackIndex, outputBuffer2, this.audioBufferInfo, false)) {
                        didWriteData(this.videoFile, false);
                    }
                    this.audioEncoder.releaseOutputBuffer(dequeueOutputBuffer2, false);
                    if ((this.audioBufferInfo.flags & 4) != 0) {
                        return;
                    }
                }
            }
        }

        protected void finalize() {
            try {
                if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                    EGL14.eglMakeCurrent(this.eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
                    EGL14.eglDestroyContext(this.eglDisplay, this.eglContext);
                    EGL14.eglReleaseThread();
                    EGL14.eglTerminate(this.eglDisplay);
                    this.eglDisplay = EGL14.EGL_NO_DISPLAY;
                    this.eglContext = EGL14.EGL_NO_CONTEXT;
                    this.eglConfig = null;
                }
                super.finalize();
            } catch (Throwable th) {
                super.finalize();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void frameAvailable(android.graphics.SurfaceTexture r8, java.lang.Integer r9, long r10) {
            /*
            r7 = this;
            r1 = r7.sync;
            monitor-enter(r1);
            r0 = r7.ready;	 Catch:{ all -> 0x0032 }
            if (r0 != 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
        L_0x0008:
            return;
        L_0x0009:
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
            r0 = r8.getTimestamp();
            r2 = 0;
            r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r2 != 0) goto L_0x0035;
        L_0x0014:
            r0 = r7.zeroTimeStamps;
            r0 = r0 + 1;
            r7.zeroTimeStamps = r0;
            r0 = r7.zeroTimeStamps;
            r1 = 1;
            if (r0 <= r1) goto L_0x0008;
        L_0x001f:
            r0 = r7.handler;
            r1 = r7.handler;
            r2 = 2;
            r3 = 32;
            r4 = r10 >> r3;
            r3 = (int) r4;
            r4 = (int) r10;
            r1 = r1.obtainMessage(r2, r3, r4, r9);
            r0.sendMessage(r1);
            goto L_0x0008;
        L_0x0032:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0032 }
            throw r0;
        L_0x0035:
            r2 = 0;
            r7.zeroTimeStamps = r2;
            r10 = r0;
            goto L_0x001f;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.frameAvailable(android.graphics.SurfaceTexture, java.lang.Integer, long):void");
        }

        public Surface getInputSurface() {
            return this.surface;
        }

        public void run() {
            Looper.prepare();
            synchronized (this.sync) {
                this.handler = new EncoderHandler(this);
                this.ready = true;
                this.sync.notify();
            }
            Looper.loop();
            synchronized (this.sync) {
                this.ready = false;
            }
        }

        public void startRecording(File file, int i, int i2, android.opengl.EGLContext eGLContext) {
            this.videoFile = file;
            this.videoWidth = i;
            this.videoHeight = i;
            this.videoBitrate = i2;
            this.sharedEglContext = eGLContext;
            synchronized (this.sync) {
                if (this.running) {
                    return;
                }
                this.running = true;
                new Thread(this, "TextureMovieEncoder").start();
                while (!this.ready) {
                    try {
                        this.sync.wait();
                    } catch (InterruptedException e) {
                    }
                }
                this.handler.sendMessage(this.handler.obtainMessage(0));
            }
        }

        public void stopRecording(int i) {
            this.handler.sendMessage(this.handler.obtainMessage(1, i, 0));
        }
    }

    public InstantCameraView(Context context, ChatActivity chatActivity) {
        super(context);
        this.aspectRatio = MediaController.getInstance().canRoundCamera16to9() ? new Size(16, 9) : new Size(4, 3);
        this.mMVPMatrix = new float[16];
        this.mSTMatrix = new float[16];
        this.moldSTMatrix = new float[16];
        setOnTouchListener(new C44442());
        setWillNotDraw(false);
        setBackgroundColor(-1073741824);
        this.baseFragment = chatActivity;
        this.paint = new Paint(1) {
            public void setAlpha(int i) {
                super.setAlpha(i);
                InstantCameraView.this.invalidate();
            }
        };
        this.paint.setStyle(Style.STROKE);
        this.paint.setStrokeCap(Cap.ROUND);
        this.paint.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        this.paint.setColor(-1);
        this.rect = new RectF();
        if (VERSION.SDK_INT >= 21) {
            this.cameraContainer = new FrameLayout(context) {
                public void setAlpha(float f) {
                    super.setAlpha(f);
                    InstantCameraView.this.invalidate();
                }

                public void setScaleX(float f) {
                    super.setScaleX(f);
                    InstantCameraView.this.invalidate();
                }
            };
            this.cameraContainer.setOutlineProvider(new C44475());
            this.cameraContainer.setClipToOutline(true);
            this.cameraContainer.setWillNotDraw(false);
        } else {
            final Path path = new Path();
            final Paint paint = new Paint(1);
            paint.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            this.cameraContainer = new FrameLayout(context) {
                protected void dispatchDraw(Canvas canvas) {
                    try {
                        super.dispatchDraw(canvas);
                        canvas.drawPath(path, paint);
                    } catch (Exception e) {
                    }
                }

                protected void onSizeChanged(int i, int i2, int i3, int i4) {
                    super.onSizeChanged(i, i2, i3, i4);
                    path.reset();
                    path.addCircle((float) (i / 2), (float) (i2 / 2), (float) (i / 2), Direction.CW);
                    path.toggleInverseFillType();
                }

                public void setScaleX(float f) {
                    super.setScaleX(f);
                    InstantCameraView.this.invalidate();
                }
            };
            this.cameraContainer.setWillNotDraw(false);
            this.cameraContainer.setLayerType(2, null);
        }
        addView(this.cameraContainer, new LayoutParams(AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize, 17));
        this.switchCameraButton = new ImageView(context);
        this.switchCameraButton.setScaleType(ScaleType.CENTER);
        addView(this.switchCameraButton, LayoutHelper.createFrame(48, 48.0f, 83, 20.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 14.0f));
        this.switchCameraButton.setOnClickListener(new C44507());
        this.muteImageView = new ImageView(context);
        this.muteImageView.setScaleType(ScaleType.CENTER);
        this.muteImageView.setImageResource(R.drawable.video_mute);
        this.muteImageView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        addView(this.muteImageView, LayoutHelper.createFrame(48, 48, 17));
        ((LayoutParams) this.muteImageView.getLayoutParams()).topMargin = (AndroidUtilities.roundMessageSize / 2) - AndroidUtilities.dp(24.0f);
        setVisibility(4);
    }

    private void createCamera(final SurfaceTexture surfaceTexture) {
        AndroidUtilities.runOnUIThread(new Runnable() {

            /* renamed from: org.telegram.ui.Components.InstantCameraView$10$1 */
            class C44391 implements Runnable {
                C44391() {
                }

                public void run() {
                    if (InstantCameraView.this.cameraSession != null) {
                        FileLog.e("camera initied");
                        InstantCameraView.this.cameraSession.setInitied();
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.InstantCameraView$10$2 */
            class C44402 implements Runnable {
                C44402() {
                }

                public void run() {
                    InstantCameraView.this.cameraThread.setCurrentSession(InstantCameraView.this.cameraSession);
                }
            }

            public void run() {
                if (InstantCameraView.this.cameraThread != null) {
                    FileLog.e("create camera session");
                    surfaceTexture.setDefaultBufferSize(InstantCameraView.this.previewSize.getWidth(), InstantCameraView.this.previewSize.getHeight());
                    InstantCameraView.this.cameraSession = new CameraSession(InstantCameraView.this.selectedCamera, InstantCameraView.this.previewSize, InstantCameraView.this.pictureSize, 256);
                    InstantCameraView.this.cameraThread.setCurrentSession(InstantCameraView.this.cameraSession);
                    CameraController.getInstance().openRound(InstantCameraView.this.cameraSession, surfaceTexture, new C44391(), new C44402());
                }
            }
        });
    }

    private boolean initCamera() {
        ArrayList cameras = CameraController.getInstance().getCameras();
        if (cameras == null) {
            return false;
        }
        CameraInfo cameraInfo = null;
        int i = 0;
        while (i < cameras.size()) {
            CameraInfo cameraInfo2 = (CameraInfo) cameras.get(i);
            if (!cameraInfo2.isFrontface()) {
                cameraInfo = cameraInfo2;
            }
            if ((this.isFrontface && cameraInfo2.isFrontface()) || (!this.isFrontface && !cameraInfo2.isFrontface())) {
                this.selectedCamera = cameraInfo2;
                break;
            }
            i++;
            cameraInfo = cameraInfo2;
        }
        if (this.selectedCamera == null) {
            this.selectedCamera = cameraInfo;
        }
        if (this.selectedCamera == null) {
            return false;
        }
        ArrayList previewSizes = this.selectedCamera.getPreviewSizes();
        ArrayList pictureSizes = this.selectedCamera.getPictureSizes();
        this.previewSize = CameraController.chooseOptimalSize(previewSizes, 480, 270, this.aspectRatio);
        this.pictureSize = CameraController.chooseOptimalSize(pictureSizes, 480, 270, this.aspectRatio);
        if (this.previewSize.mWidth != this.pictureSize.mWidth) {
            int size;
            Size size2;
            int size3;
            Size size4;
            boolean z = false;
            for (size = previewSizes.size() - 1; size >= 0; size--) {
                size2 = (Size) previewSizes.get(size);
                for (size3 = pictureSizes.size() - 1; size3 >= 0; size3--) {
                    size4 = (Size) pictureSizes.get(size3);
                    if (size2.mWidth >= this.pictureSize.mWidth && size2.mHeight >= this.pictureSize.mHeight && size2.mWidth == size4.mWidth && size2.mHeight == size4.mHeight) {
                        this.previewSize = size2;
                        this.pictureSize = size4;
                        z = true;
                        break;
                    }
                }
                if (z) {
                    break;
                }
            }
            if (!z) {
                size = previewSizes.size() - 1;
                while (size >= 0) {
                    boolean z2;
                    size2 = (Size) previewSizes.get(size);
                    for (size3 = pictureSizes.size() - 1; size3 >= 0; size3--) {
                        size4 = (Size) pictureSizes.get(size3);
                        if (size2.mWidth >= PsExtractor.VIDEO_STREAM_MASK && size2.mHeight >= PsExtractor.VIDEO_STREAM_MASK && size2.mWidth == size4.mWidth && size2.mHeight == size4.mHeight) {
                            this.previewSize = size2;
                            this.pictureSize = size4;
                            z2 = true;
                            break;
                        }
                    }
                    z2 = z;
                    if (z2) {
                        break;
                    }
                    size--;
                    z = z2;
                }
            }
        }
        FileLog.d("preview w = " + this.previewSize.mWidth + " h = " + this.previewSize.mHeight);
        return true;
    }

    private int loadShader(int i, String str) {
        int glCreateShader = GLES20.glCreateShader(i);
        GLES20.glShaderSource(glCreateShader, str);
        GLES20.glCompileShader(glCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(glCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return glCreateShader;
        }
        FileLog.e(GLES20.glGetShaderInfoLog(glCreateShader));
        GLES20.glDeleteShader(glCreateShader);
        return 0;
    }

    private void startProgressTimer() {
        if (this.progressTimer != null) {
            try {
                this.progressTimer.cancel();
                this.progressTimer = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        this.progressTimer = new Timer();
        this.progressTimer.schedule(new TimerTask() {

            /* renamed from: org.telegram.ui.Components.InstantCameraView$11$1 */
            class C44411 implements Runnable {
                C44411() {
                }

                public void run() {
                    long j = 0;
                    try {
                        if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoEditedInfo != null && InstantCameraView.this.videoEditedInfo.endTime > 0 && InstantCameraView.this.videoPlayer.getCurrentPosition() >= InstantCameraView.this.videoEditedInfo.endTime) {
                            VideoPlayer access$500 = InstantCameraView.this.videoPlayer;
                            if (InstantCameraView.this.videoEditedInfo.startTime > 0) {
                                j = InstantCameraView.this.videoEditedInfo.startTime;
                            }
                            access$500.seekTo(j);
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C44411());
            }
        }, 0, 17);
    }

    private void stopProgressTimer() {
        if (this.progressTimer != null) {
            try {
                this.progressTimer.cancel();
                this.progressTimer = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    private void switchCamera() {
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, null, null);
            this.cameraSession = null;
        }
        this.isFrontface = !this.isFrontface;
        initCamera();
        this.cameraReady = false;
        this.cameraThread.reinitForNewCamera();
    }

    public void cancel() {
        stopProgressTimer();
        if (this.videoPlayer != null) {
            this.videoPlayer.releasePlayer();
            this.videoPlayer = null;
        }
        if (this.textureView != null) {
            this.cancelled = true;
            this.recording = false;
            AndroidUtilities.cancelRunOnUIThread(this.timerRunnable);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordStopped, new Object[]{Integer.valueOf(0)});
            if (this.cameraThread != null) {
                this.cameraThread.shutdown(0);
                this.cameraThread = null;
            }
            if (this.cameraFile != null) {
                this.cameraFile.delete();
                this.cameraFile = null;
            }
            startAnimation(false);
        }
    }

    public void changeVideoPreviewState(int i, float f) {
        if (this.videoPlayer != null) {
            if (i == 0) {
                startProgressTimer();
                this.videoPlayer.play();
            } else if (i == 1) {
                stopProgressTimer();
                this.videoPlayer.pause();
            } else if (i == 2) {
                this.videoPlayer.seekTo((long) (((float) this.videoPlayer.getDuration()) * f));
            }
        }
    }

    public void destroy(boolean z, Runnable runnable) {
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, !z ? new Semaphore(0) : null, runnable);
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.recordProgressChanged) {
            long longValue = ((Long) objArr[0]).longValue();
            this.progress = ((float) longValue) / 60000.0f;
            this.recordedTime = longValue;
            invalidate();
        } else if (i == NotificationCenter.FileDidUpload) {
            String str = (String) objArr[0];
            if (this.cameraFile != null && this.cameraFile.getAbsolutePath().equals(str)) {
                this.file = (InputFile) objArr[1];
                this.encryptedFile = (InputEncryptedFile) objArr[2];
                this.size = ((Long) objArr[5]).longValue();
                if (this.encryptedFile != null) {
                    this.key = (byte[]) objArr[3];
                    this.iv = (byte[]) objArr[4];
                }
            }
        }
    }

    public FrameLayout getCameraContainer() {
        return this.cameraContainer;
    }

    public Rect getCameraRect() {
        this.cameraContainer.getLocationOnScreen(this.position);
        return new Rect((float) this.position[0], (float) this.position[1], (float) this.cameraContainer.getWidth(), (float) this.cameraContainer.getHeight());
    }

    public View getMuteImageView() {
        return this.muteImageView;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public View getSwitchButtonView() {
        return this.switchCameraButton;
    }

    public void hideCamera(boolean z) {
        destroy(z, null);
        this.cameraContainer.removeView(this.textureView);
        this.cameraContainer.setTranslationX(BitmapDescriptorFactory.HUE_RED);
        this.cameraContainer.setTranslationY(BitmapDescriptorFactory.HUE_RED);
        this.textureView = null;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recordProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.FileDidUpload);
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recordProgressChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.FileDidUpload);
    }

    protected void onDraw(Canvas canvas) {
        float x = this.cameraContainer.getX();
        float y = this.cameraContainer.getY();
        this.rect.set(x - ((float) AndroidUtilities.dp(8.0f)), y - ((float) AndroidUtilities.dp(8.0f)), (((float) this.cameraContainer.getMeasuredWidth()) + x) + ((float) AndroidUtilities.dp(8.0f)), (((float) this.cameraContainer.getMeasuredHeight()) + y) + ((float) AndroidUtilities.dp(8.0f)));
        if (this.progress != BitmapDescriptorFactory.HUE_RED) {
            canvas.drawArc(this.rect, -90.0f, this.progress * 360.0f, false, this.paint);
        }
        if (Theme.chat_roundVideoShadow != null) {
            int dp = ((int) x) - AndroidUtilities.dp(3.0f);
            int dp2 = ((int) y) - AndroidUtilities.dp(2.0f);
            canvas.save();
            canvas.scale(this.cameraContainer.getScaleX(), this.cameraContainer.getScaleY(), (float) (((AndroidUtilities.roundMessageSize / 2) + dp) + AndroidUtilities.dp(3.0f)), (float) (((AndroidUtilities.roundMessageSize / 2) + dp2) + AndroidUtilities.dp(3.0f)));
            Theme.chat_roundVideoShadow.setAlpha((int) (this.cameraContainer.getAlpha() * 255.0f));
            Theme.chat_roundVideoShadow.setBounds(dp, dp2, (AndroidUtilities.roundMessageSize + dp) + AndroidUtilities.dp(6.0f), (AndroidUtilities.roundMessageSize + dp2) + AndroidUtilities.dp(6.0f));
            Theme.chat_roundVideoShadow.draw(canvas);
            canvas.restore();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(motionEvent);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (getVisibility() != 0) {
            this.cameraContainer.setTranslationY((float) (getMeasuredHeight() / 2));
        }
    }

    public void send(int i) {
        int i2 = 2;
        if (this.textureView != null) {
            stopProgressTimer();
            if (this.videoPlayer != null) {
                this.videoPlayer.releasePlayer();
                this.videoPlayer = null;
            }
            if (i == 4) {
                if (this.videoEditedInfo.needConvert()) {
                    VideoEditedInfo videoEditedInfo;
                    this.file = null;
                    this.encryptedFile = null;
                    this.key = null;
                    this.iv = null;
                    double d = (double) this.videoEditedInfo.estimatedDuration;
                    this.videoEditedInfo.estimatedDuration = (this.videoEditedInfo.endTime >= 0 ? this.videoEditedInfo.endTime : this.videoEditedInfo.estimatedDuration) - (this.videoEditedInfo.startTime >= 0 ? this.videoEditedInfo.startTime : 0);
                    this.videoEditedInfo.estimatedSize = (long) (((double) this.size) * (((double) this.videoEditedInfo.estimatedDuration) / d));
                    this.videoEditedInfo.bitrate = 400000;
                    if (this.videoEditedInfo.startTime > 0) {
                        videoEditedInfo = this.videoEditedInfo;
                        videoEditedInfo.startTime *= 1000;
                    }
                    if (this.videoEditedInfo.endTime > 0) {
                        videoEditedInfo = this.videoEditedInfo;
                        videoEditedInfo.endTime *= 1000;
                    }
                    FileLoader.getInstance().cancelUploadFile(this.cameraFile.getAbsolutePath(), false);
                } else {
                    this.videoEditedInfo.estimatedSize = this.size;
                }
                this.videoEditedInfo.file = this.file;
                this.videoEditedInfo.encryptedFile = this.encryptedFile;
                this.videoEditedInfo.key = this.key;
                this.videoEditedInfo.iv = this.iv;
                this.baseFragment.sendMedia(new PhotoEntry(0, 0, 0, this.cameraFile.getAbsolutePath(), 0, true), this.videoEditedInfo);
                return;
            }
            this.cancelled = this.recordedTime < 800;
            this.recording = false;
            AndroidUtilities.cancelRunOnUIThread(this.timerRunnable);
            if (this.cameraThread != null) {
                NotificationCenter instance = NotificationCenter.getInstance();
                int i3 = NotificationCenter.recordStopped;
                Object[] objArr = new Object[1];
                int i4 = (this.cancelled || i != 3) ? 0 : 2;
                objArr[0] = Integer.valueOf(i4);
                instance.postNotificationName(i3, objArr);
                if (this.cancelled) {
                    i2 = 0;
                } else if (i != 3) {
                    i2 = 1;
                }
                this.cameraThread.shutdown(i2);
                this.cameraThread = null;
            }
            if (this.cancelled) {
                startAnimation(false);
            }
        }
    }

    public void setAlpha(float f) {
        ((ColorDrawable) getBackground()).setAlpha((int) (192.0f * f));
        invalidate();
    }

    public void setVisibility(int i) {
        super.setVisibility(i);
        setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.switchCameraButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.cameraContainer.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.muteImageView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.muteImageView.setScaleX(1.0f);
        this.muteImageView.setScaleY(1.0f);
        this.cameraContainer.setScaleX(0.1f);
        this.cameraContainer.setScaleY(0.1f);
        if (this.cameraContainer.getMeasuredWidth() != 0) {
            this.cameraContainer.setPivotX((float) (this.cameraContainer.getMeasuredWidth() / 2));
            this.cameraContainer.setPivotY((float) (this.cameraContainer.getMeasuredHeight() / 2));
        }
        if (i == 0) {
            try {
                ((Activity) getContext()).getWindow().addFlags(128);
                return;
            } catch (Throwable e) {
                FileLog.e(e);
                return;
            }
        }
        ((Activity) getContext()).getWindow().clearFlags(128);
    }

    public void showCamera() {
        if (this.textureView == null) {
            this.switchCameraButton.setImageResource(R.drawable.camera_revert1);
            this.isFrontface = true;
            this.selectedCamera = null;
            this.recordedTime = 0;
            this.progress = BitmapDescriptorFactory.HUE_RED;
            this.cancelled = false;
            this.file = null;
            this.encryptedFile = null;
            this.key = null;
            this.iv = null;
            if (initCamera()) {
                MediaController.getInstance().pauseMessage(MediaController.getInstance().getPlayingMessageObject());
                this.cameraFile = new File(FileLoader.getInstance().getDirectory(4), UserConfig.lastLocalId + ".mp4");
                UserConfig.lastLocalId--;
                UserConfig.saveConfig(false);
                FileLog.e("show round camera");
                this.textureView = new TextureView(getContext());
                this.textureView.setSurfaceTextureListener(new C44518());
                this.cameraContainer.addView(this.textureView, LayoutHelper.createFrame(-1, -1.0f));
                setVisibility(0);
                startAnimation(true);
            }
        }
    }

    public void startAnimation(boolean z) {
        float f = 1.0f;
        float f2 = BitmapDescriptorFactory.HUE_RED;
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
        }
        PipRoundVideoView instance = PipRoundVideoView.getInstance();
        if (instance != null) {
            instance.showTemporary(!z);
        }
        this.animatorSet = new AnimatorSet();
        AnimatorSet animatorSet = this.animatorSet;
        Animator[] animatorArr = new Animator[8];
        String str = "alpha";
        float[] fArr = new float[1];
        fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        animatorArr[0] = ObjectAnimator.ofFloat(this, str, fArr);
        ImageView imageView = this.switchCameraButton;
        String str2 = "alpha";
        float[] fArr2 = new float[1];
        fArr2[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        animatorArr[1] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
        animatorArr[2] = ObjectAnimator.ofFloat(this.muteImageView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        Paint paint = this.paint;
        String str3 = "alpha";
        int[] iArr = new int[1];
        iArr[0] = z ? 255 : 0;
        animatorArr[3] = ObjectAnimator.ofInt(paint, str3, iArr);
        FrameLayout frameLayout = this.cameraContainer;
        str3 = "alpha";
        float[] fArr3 = new float[1];
        fArr3[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
        animatorArr[4] = ObjectAnimator.ofFloat(frameLayout, str3, fArr3);
        frameLayout = this.cameraContainer;
        str3 = "scaleX";
        fArr3 = new float[1];
        fArr3[0] = z ? 1.0f : 0.1f;
        animatorArr[5] = ObjectAnimator.ofFloat(frameLayout, str3, fArr3);
        FrameLayout frameLayout2 = this.cameraContainer;
        str2 = "scaleY";
        fArr2 = new float[1];
        if (!z) {
            f = 0.1f;
        }
        fArr2[0] = f;
        animatorArr[6] = ObjectAnimator.ofFloat(frameLayout2, str2, fArr2);
        FrameLayout frameLayout3 = this.cameraContainer;
        str = "translationY";
        fArr = new float[2];
        fArr[0] = z ? (float) (getMeasuredHeight() / 2) : BitmapDescriptorFactory.HUE_RED;
        if (!z) {
            f2 = (float) (getMeasuredHeight() / 2);
        }
        fArr[1] = f2;
        animatorArr[7] = ObjectAnimator.ofFloat(frameLayout3, str, fArr);
        animatorSet.playTogether(animatorArr);
        if (!z) {
            this.animatorSet.addListener(new C44529());
        }
        this.animatorSet.setDuration(180);
        this.animatorSet.setInterpolator(new DecelerateInterpolator());
        this.animatorSet.start();
    }
}
