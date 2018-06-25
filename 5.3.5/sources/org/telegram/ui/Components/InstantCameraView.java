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
import org.telegram.customization.fetch.FetchService;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController$PhotoEntry;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.camera.CameraInfo;
import org.telegram.messenger.camera.CameraSession;
import org.telegram.messenger.camera.Size;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.video.MP4Builder;
import org.telegram.messenger.video.Mp4Movie;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$InputEncryptedFile;
import org.telegram.tgnet.TLRPC$InputFile;
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
    private TLRPC$InputEncryptedFile encryptedFile;
    private TLRPC$InputFile file;
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
    private Runnable timerRunnable = new C26041();
    private FloatBuffer vertexBuffer;
    private VideoEditedInfo videoEditedInfo;
    private VideoPlayer videoPlayer;

    /* renamed from: org.telegram.ui.Components.InstantCameraView$1 */
    class C26041 implements Runnable {
        C26041() {
        }

        public void run() {
            if (InstantCameraView.this.recording) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.recordProgressChanged, new Object[]{Long.valueOf(InstantCameraView.this.duration = System.currentTimeMillis() - InstantCameraView.this.recordStartTime), Double.valueOf(0.0d)});
                AndroidUtilities.runOnUIThread(InstantCameraView.this.timerRunnable, 50);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$2 */
    class C26062 implements OnTouchListener {

        /* renamed from: org.telegram.ui.Components.InstantCameraView$2$1 */
        class C26051 extends AnimatorListenerAdapter {
            C26051() {
            }

            public void onAnimationEnd(Animator animation) {
                if (animation.equals(InstantCameraView.this.muteAnimation)) {
                    InstantCameraView.this.muteAnimation = null;
                }
            }
        }

        C26062() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            float f = 1.0f;
            if (event.getAction() == 0 && InstantCameraView.this.baseFragment != null) {
                if (InstantCameraView.this.videoPlayer != null) {
                    float f2;
                    boolean mute = !InstantCameraView.this.videoPlayer.isMuted();
                    InstantCameraView.this.videoPlayer.setMute(mute);
                    if (InstantCameraView.this.muteAnimation != null) {
                        InstantCameraView.this.muteAnimation.cancel();
                    }
                    InstantCameraView.this.muteAnimation = new AnimatorSet();
                    AnimatorSet access$600 = InstantCameraView.this.muteAnimation;
                    Animator[] animatorArr = new Animator[3];
                    ImageView access$700 = InstantCameraView.this.muteImageView;
                    String str = "alpha";
                    float[] fArr = new float[1];
                    fArr[0] = mute ? 1.0f : 0.0f;
                    animatorArr[0] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$700 = InstantCameraView.this.muteImageView;
                    str = "scaleX";
                    fArr = new float[1];
                    if (mute) {
                        f2 = 1.0f;
                    } else {
                        f2 = 0.5f;
                    }
                    fArr[0] = f2;
                    animatorArr[1] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$700 = InstantCameraView.this.muteImageView;
                    str = "scaleY";
                    fArr = new float[1];
                    if (!mute) {
                        f = 0.5f;
                    }
                    fArr[0] = f;
                    animatorArr[2] = ObjectAnimator.ofFloat(access$700, str, fArr);
                    access$600.playTogether(animatorArr);
                    InstantCameraView.this.muteAnimation.addListener(new C26051());
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
    class C26095 extends ViewOutlineProvider {
        C26095() {
        }

        @TargetApi(21)
        public void getOutline(View view, Outline outline) {
            outline.setOval(0, 0, AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize);
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$7 */
    class C26127 implements OnClickListener {

        /* renamed from: org.telegram.ui.Components.InstantCameraView$7$1 */
        class C26111 extends AnimatorListenerAdapter {
            C26111() {
            }

            public void onAnimationEnd(Animator animator) {
                InstantCameraView.this.switchCameraButton.setImageResource(InstantCameraView.this.isFrontface ? R.drawable.camera_revert1 : R.drawable.camera_revert2);
                ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "scaleX", new float[]{1.0f}).setDuration(100).start();
            }
        }

        C26127() {
        }

        public void onClick(View v) {
            if (InstantCameraView.this.cameraReady && InstantCameraView.this.cameraSession != null && InstantCameraView.this.cameraSession.isInitied() && InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.switchCamera();
                ObjectAnimator animator = ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "scaleX", new float[]{0.0f}).setDuration(100);
                animator.addListener(new C26111());
                animator.start();
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$8 */
    class C26138 implements SurfaceTextureListener {
        C26138() {
        }

        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            FileLog.e("camera surface available");
            if (InstantCameraView.this.cameraThread == null && surface != null && !InstantCameraView.this.cancelled) {
                FileLog.e("start create thread");
                InstantCameraView.this.cameraThread = new CameraGLThread(surface, width, height);
            }
        }

        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            if (InstantCameraView.this.cameraThread != null) {
                InstantCameraView.this.cameraThread.shutdown(0);
                InstantCameraView.this.cameraThread = null;
            }
            if (InstantCameraView.this.cameraSession != null) {
                CameraController.getInstance().close(InstantCameraView.this.cameraSession, null, null);
            }
            return true;
        }

        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        }
    }

    /* renamed from: org.telegram.ui.Components.InstantCameraView$9 */
    class C26149 extends AnimatorListenerAdapter {
        C26149() {
        }

        public void onAnimationEnd(Animator animation) {
            if (animation.equals(InstantCameraView.this.animatorSet)) {
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
        class C26151 implements OnFrameAvailableListener {
            C26151() {
            }

            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                CameraGLThread.this.requestRender();
            }
        }

        /* renamed from: org.telegram.ui.Components.InstantCameraView$CameraGLThread$2 */
        class C26162 implements OnFrameAvailableListener {
            C26162() {
            }

            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
                CameraGLThread.this.requestRender();
            }
        }

        public CameraGLThread(SurfaceTexture surface, int surfaceWidth, int surfaceHeight) {
            super("CameraGLThread");
            this.surfaceTexture = surface;
            int width = InstantCameraView.this.previewSize.getWidth();
            int height = InstantCameraView.this.previewSize.getHeight();
            float scale = ((float) surfaceWidth) / ((float) Math.min(width, height));
            width = (int) (((float) width) * scale);
            height = (int) (((float) height) * scale);
            if (width > height) {
                InstantCameraView.this.scaleX = 1.0f;
                InstantCameraView.this.scaleY = ((float) width) / ((float) surfaceHeight);
                return;
            }
            InstantCameraView.this.scaleX = ((float) height) / ((float) surfaceWidth);
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
                int[] configsCount = new int[1];
                EGLConfig[] configs = new EGLConfig[1];
                if (!this.egl10.eglChooseConfig(this.eglDisplay, new int[]{12352, 4, 12324, 8, 12323, 8, 12322, 8, 12321, 0, 12325, 0, 12326, 0, 12344}, configs, 1, configsCount)) {
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
                        }
                        if (this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                            this.gl = this.eglContext.getGL();
                            float tX = (1.0f / InstantCameraView.this.scaleX) / 2.0f;
                            float tY = (1.0f / InstantCameraView.this.scaleY) / 2.0f;
                            float[] fArr = new float[12];
                            fArr = new float[]{-1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, -1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f};
                            float[] texData = new float[]{0.5f - tX, 0.5f - tY, 0.5f + tX, 0.5f - tY, 0.5f - tX, 0.5f + tY, 0.5f + tX, 0.5f + tY};
                            this.videoEncoder = new VideoRecorder();
                            InstantCameraView.this.vertexBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                            InstantCameraView.this.vertexBuffer.put(fArr).position(0);
                            InstantCameraView.this.textureBuffer = ByteBuffer.allocateDirect(texData.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
                            InstantCameraView.this.textureBuffer.put(texData).position(0);
                            Matrix.setIdentityM(InstantCameraView.this.mSTMatrix, 0);
                            int vertexShader = InstantCameraView.this.loadShader(35633, InstantCameraView.VERTEX_SHADER);
                            int fragmentShader = InstantCameraView.this.loadShader(35632, InstantCameraView.FRAGMENT_SCREEN_SHADER);
                            if (vertexShader == 0 || fragmentShader == 0) {
                                FileLog.e("failed creating shader");
                                finish();
                                return false;
                            }
                            this.drawProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.drawProgram, vertexShader);
                            GLES20.glAttachShader(this.drawProgram, fragmentShader);
                            GLES20.glLinkProgram(this.drawProgram);
                            int[] linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.drawProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
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
                            this.cameraSurface.setOnFrameAvailableListener(new C26151());
                            InstantCameraView.this.createCamera(this.cameraSurface);
                            FileLog.e("gl initied");
                            return true;
                        }
                        FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                        finish();
                        return false;
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

        public void reinitForNewCamera() {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(2), 0);
            }
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

        public void setCurrentSession(CameraSession session) {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(3, session), 0);
            }
        }

        private void onDraw(Integer cameraId) {
            if (!this.initied) {
                return;
            }
            if ((this.eglContext.equals(this.egl10.eglGetCurrentContext()) && this.eglSurface.equals(this.egl10.eglGetCurrentSurface(12377))) || this.egl10.eglMakeCurrent(this.eglDisplay, this.eglSurface, this.eglSurface, this.eglContext)) {
                this.cameraSurface.updateTexImage();
                if (!this.recording) {
                    int resolution;
                    int bitrate;
                    String model = Build.DEVICE;
                    if (model == null) {
                        model = "";
                    }
                    if (model.startsWith("zeroflte") || model.startsWith("zenlte")) {
                        resolution = FetchService.ACTION_LOGGING;
                        bitrate = 600000;
                    } else {
                        resolution = PsExtractor.VIDEO_STREAM_MASK;
                        bitrate = 400000;
                    }
                    this.videoEncoder.startRecording(InstantCameraView.this.cameraFile, resolution, bitrate, EGL14.eglGetCurrentContext());
                    this.recording = true;
                    int orientation = this.currentSession.getCurrentOrientation();
                    if (orientation == 90 || orientation == 270) {
                        float temp = InstantCameraView.this.scaleX;
                        InstantCameraView.this.scaleX = InstantCameraView.this.scaleY;
                        InstantCameraView.this.scaleY = temp;
                    }
                }
                this.videoEncoder.frameAvailable(this.cameraSurface, cameraId, System.nanoTime());
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

        public void run() {
            this.initied = initGL();
            super.run();
        }

        public void handleMessage(Message inputMessage) {
            switch (inputMessage.what) {
                case 0:
                    onDraw((Integer) inputMessage.obj);
                    return;
                case 1:
                    finish();
                    if (this.recording) {
                        this.videoEncoder.stopRecording(inputMessage.arg1);
                    }
                    Looper looper = Looper.myLooper();
                    if (looper != null) {
                        looper.quit();
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
                            InstantCameraView.this.cameraTextureAlpha = 0.0f;
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
                        this.cameraSurface.setOnFrameAvailableListener(new C26162());
                        InstantCameraView.this.createCamera(this.cameraSurface);
                        return;
                    }
                    FileLog.e("eglMakeCurrent failed " + GLUtils.getEGLErrorString(this.egl10.eglGetError()));
                    return;
                case 3:
                    FileLog.d("set gl rednderer session");
                    CameraSession newSession = inputMessage.obj;
                    if (this.currentSession == newSession) {
                        this.rotationAngle = this.currentSession.getWorldAngle();
                        Matrix.setIdentityM(InstantCameraView.this.mMVPMatrix, 0);
                        if (this.rotationAngle != 0) {
                            Matrix.rotateM(InstantCameraView.this.mMVPMatrix, 0, (float) this.rotationAngle, 0.0f, 0.0f, 1.0f);
                            return;
                        }
                        return;
                    }
                    this.currentSession = newSession;
                    return;
                default:
                    return;
            }
        }

        public void shutdown(int send) {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(1, send, 0), 0);
            }
        }

        public void requestRender() {
            Handler handler = InstantCameraView.this.getHandler();
            if (handler != null) {
                sendMessage(handler.obtainMessage(0, this.cameraId), 0);
            }
        }
    }

    private static class EncoderHandler extends Handler {
        private WeakReference<VideoRecorder> mWeakEncoder;

        public EncoderHandler(VideoRecorder encoder) {
            this.mWeakEncoder = new WeakReference(encoder);
        }

        public void handleMessage(Message inputMessage) {
            int what = inputMessage.what;
            Object obj = inputMessage.obj;
            VideoRecorder encoder = (VideoRecorder) this.mWeakEncoder.get();
            if (encoder != null) {
                switch (what) {
                    case 0:
                        try {
                            FileLog.e("start encoder");
                            encoder.prepareEncoder();
                            return;
                        } catch (Exception e) {
                            FileLog.e(e);
                            encoder.handleStopRecording(0);
                            Looper.myLooper().quit();
                            return;
                        }
                    case 1:
                        FileLog.e("stop encoder");
                        encoder.handleStopRecording(inputMessage.arg1);
                        return;
                    case 2:
                        encoder.handleVideoFrameAvailable((((long) inputMessage.arg1) << 32) | (((long) inputMessage.arg2) & 4294967295L), inputMessage.obj);
                        return;
                    case 3:
                        encoder.handleAudioFrameAvailable((AudioBufferInfo) inputMessage.obj);
                        return;
                    default:
                        return;
                }
            }
        }

        public void exit() {
            Looper.myLooper().quit();
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
        class C26171 implements Runnable {
            C26171() {
            }

            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r14 = this;
                r13 = 0;
                r12 = 10;
                r11 = 1;
                r4 = 0;
            L_0x0005:
                if (r4 != 0) goto L_0x002c;
            L_0x0007:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.running;
                if (r7 != 0) goto L_0x0052;
            L_0x000f:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.audioRecorder;
                r7 = r7.getRecordingState();
                if (r7 == r11) goto L_0x0052;
            L_0x001b:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x004f }
                r7 = r7.audioRecorder;	 Catch:{ Exception -> 0x004f }
                r7.stop();	 Catch:{ Exception -> 0x004f }
            L_0x0024:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.sendWhenDone;
                if (r7 != 0) goto L_0x0052;
            L_0x002c:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x00ed }
                r7 = r7.audioRecorder;	 Catch:{ Exception -> 0x00ed }
                r7.release();	 Catch:{ Exception -> 0x00ed }
            L_0x0035:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.handler;
                r8 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r8 = r8.handler;
                r9 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r9 = r9.sendWhenDone;
                r8 = r8.obtainMessage(r11, r9, r13);
                r7.sendMessage(r8);
                return;
            L_0x004f:
                r5 = move-exception;
                r4 = 1;
                goto L_0x0024;
            L_0x0052:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.buffers;
                r7 = r7.isEmpty();
                if (r7 == 0) goto L_0x00bc;
            L_0x005e:
                r1 = new org.telegram.ui.Components.InstantCameraView$AudioBufferInfo;
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = org.telegram.ui.Components.InstantCameraView.this;
                r8 = 0;
                r1.<init>();
            L_0x0068:
                r1.lastWroteBuffer = r13;
                r1.results = r12;
                r0 = 0;
            L_0x006d:
                if (r0 >= r12) goto L_0x0091;
            L_0x006f:
                r2 = java.lang.System.nanoTime();
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.audioRecorder;
                r8 = r1.buffer;
                r9 = r0 * 2048;
                r10 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
                r6 = r7.read(r8, r9, r10);
                if (r6 > 0) goto L_0x00c9;
            L_0x0085:
                r1.results = r0;
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.running;
                if (r7 != 0) goto L_0x0091;
            L_0x008f:
                r1.last = r11;
            L_0x0091:
                r7 = r1.results;
                if (r7 >= 0) goto L_0x0099;
            L_0x0095:
                r7 = r1.last;
                if (r7 == 0) goto L_0x00d4;
            L_0x0099:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.running;
                if (r7 != 0) goto L_0x00a6;
            L_0x00a1:
                r7 = r1.results;
                if (r7 >= r12) goto L_0x00a6;
            L_0x00a5:
                r4 = 1;
            L_0x00a6:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.handler;
                r8 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r8 = r8.handler;
                r9 = 3;
                r8 = r8.obtainMessage(r9, r1);
                r7.sendMessage(r8);
                goto L_0x0005;
            L_0x00bc:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.buffers;
                r1 = r7.poll();
                r1 = (org.telegram.ui.Components.InstantCameraView.AudioBufferInfo) r1;
                goto L_0x0068;
            L_0x00c9:
                r7 = r1.offset;
                r7[r0] = r2;
                r7 = r1.read;
                r7[r0] = r6;
                r0 = r0 + 1;
                goto L_0x006d;
            L_0x00d4:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;
                r7 = r7.running;
                if (r7 != 0) goto L_0x00df;
            L_0x00dc:
                r4 = 1;
                goto L_0x0005;
            L_0x00df:
                r7 = org.telegram.ui.Components.InstantCameraView.VideoRecorder.this;	 Catch:{ Exception -> 0x00ea }
                r7 = r7.buffers;	 Catch:{ Exception -> 0x00ea }
                r7.put(r1);	 Catch:{ Exception -> 0x00ea }
                goto L_0x0005;
            L_0x00ea:
                r7 = move-exception;
                goto L_0x0005;
            L_0x00ed:
                r5 = move-exception;
                org.telegram.messenger.FileLog.e(r5);
                goto L_0x0035;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.1.run():void");
            }
        }

        /* renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$3 */
        class C26203 implements Runnable {
            C26203() {
            }

            public void run() {
                if (!InstantCameraView.this.cancelled) {
                    try {
                        ((Vibrator) ApplicationLoader.applicationContext.getSystemService("vibrator")).vibrate(50);
                    } catch (Exception e) {
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
            this.recorderRunnable = new C26171();
        }

        public void startRecording(File outputFile, int size, int bitRate, android.opengl.EGLContext sharedContext) {
            this.videoFile = outputFile;
            this.videoWidth = size;
            this.videoHeight = size;
            this.videoBitrate = bitRate;
            this.sharedEglContext = sharedContext;
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

        public void stopRecording(int send) {
            this.handler.sendMessage(this.handler.obtainMessage(1, send, 0));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void frameAvailable(android.graphics.SurfaceTexture r10, java.lang.Integer r11, long r12) {
            /*
            r9 = this;
            r3 = r9.sync;
            monitor-enter(r3);
            r2 = r9.ready;	 Catch:{ all -> 0x0033 }
            if (r2 != 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r3);	 Catch:{ all -> 0x0033 }
        L_0x0008:
            return;
        L_0x0009:
            monitor-exit(r3);	 Catch:{ all -> 0x0033 }
            r0 = r10.getTimestamp();
            r2 = 0;
            r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r2 != 0) goto L_0x0036;
        L_0x0014:
            r2 = r9.zeroTimeStamps;
            r2 = r2 + 1;
            r9.zeroTimeStamps = r2;
            r2 = r9.zeroTimeStamps;
            r3 = 1;
            if (r2 <= r3) goto L_0x0008;
        L_0x001f:
            r0 = r12;
        L_0x0020:
            r2 = r9.handler;
            r3 = r9.handler;
            r4 = 2;
            r5 = 32;
            r6 = r0 >> r5;
            r5 = (int) r6;
            r6 = (int) r0;
            r3 = r3.obtainMessage(r4, r5, r6, r11);
            r2.sendMessage(r3);
            goto L_0x0008;
        L_0x0033:
            r2 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0033 }
            throw r2;
        L_0x0036:
            r2 = 0;
            r9.zeroTimeStamps = r2;
            goto L_0x0020;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.InstantCameraView.VideoRecorder.frameAvailable(android.graphics.SurfaceTexture, java.lang.Integer, long):void");
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

        private void handleAudioFrameAvailable(AudioBufferInfo input) {
            if (this.audioStartTime == -1) {
                this.audioStartTime = input.offset[0];
            }
            this.buffersToWrite.add(input);
            if (this.buffersToWrite.size() > 1) {
                input = (AudioBufferInfo) this.buffersToWrite.get(0);
            }
            try {
                drainEncoder(false);
            } catch (Exception e) {
                FileLog.e(e);
            }
            boolean isLast = false;
            while (input != null) {
                int inputBufferIndex = this.audioEncoder.dequeueInputBuffer(0);
                if (inputBufferIndex >= 0) {
                    ByteBuffer inputBuffer;
                    if (VERSION.SDK_INT >= 21) {
                        inputBuffer = this.audioEncoder.getInputBuffer(inputBufferIndex);
                    } else {
                        try {
                            inputBuffer = this.audioEncoder.getInputBuffers()[inputBufferIndex];
                            inputBuffer.clear();
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                            return;
                        }
                    }
                    long startWriteTime = input.offset[input.lastWroteBuffer];
                    for (int a = input.lastWroteBuffer; a <= input.results; a++) {
                        if (a < input.results) {
                            if (inputBuffer.remaining() < input.read[a]) {
                                input.lastWroteBuffer = a;
                                input = null;
                                break;
                            }
                            inputBuffer.put(input.buffer, a * 2048, input.read[a]);
                        }
                        if (a >= input.results - 1) {
                            this.buffersToWrite.remove(input);
                            if (this.running) {
                                this.buffers.put(input);
                            }
                            if (this.buffersToWrite.isEmpty()) {
                                isLast = input.last;
                                input = null;
                                break;
                            }
                            input = (AudioBufferInfo) this.buffersToWrite.get(0);
                        }
                    }
                    this.audioEncoder.queueInputBuffer(inputBufferIndex, 0, inputBuffer.position(), startWriteTime == 0 ? 0 : (startWriteTime - this.audioStartTime) / 1000, isLast ? 4 : 0);
                }
            }
        }

        private void handleVideoFrameAvailable(long timestampNanos, Integer cameraId) {
            long dt;
            long alphaDt;
            try {
                drainEncoder(false);
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (!this.lastCameraId.equals(cameraId)) {
                this.lastTimestamp = -1;
                this.lastCameraId = cameraId;
            }
            if (this.lastTimestamp == -1) {
                this.lastTimestamp = timestampNanos;
                if (this.currentTimestamp != 0) {
                    dt = (System.currentTimeMillis() - this.lastCommitedFrameTime) * C0907C.MICROS_PER_SECOND;
                    alphaDt = 0;
                } else {
                    dt = 0;
                    alphaDt = 0;
                }
            } else {
                dt = timestampNanos - this.lastTimestamp;
                alphaDt = dt;
                this.lastTimestamp = timestampNanos;
            }
            this.lastCommitedFrameTime = System.currentTimeMillis();
            if (!this.skippedFirst) {
                this.skippedTime += dt;
                if (this.skippedTime >= 200000000) {
                    this.skippedFirst = true;
                } else {
                    return;
                }
            }
            this.currentTimestamp += dt;
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
                InstantCameraView.this.cameraTextureAlpha = InstantCameraView.this.cameraTextureAlpha + (((float) alphaDt) / 2.0E8f);
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

        private void handleStopRecording(final int send) {
            if (this.running) {
                this.sendWhenDone = send;
                this.running = false;
                return;
            }
            try {
                drainEncoder(true);
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (this.videoEncoder != null) {
                try {
                    this.videoEncoder.stop();
                    this.videoEncoder.release();
                    this.videoEncoder = null;
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            if (this.audioEncoder != null) {
                try {
                    this.audioEncoder.stop();
                    this.audioEncoder.release();
                    this.audioEncoder = null;
                } catch (Exception e22) {
                    FileLog.e(e22);
                }
            }
            if (this.mediaMuxer != null) {
                try {
                    this.mediaMuxer.finishMovie();
                } catch (Exception e222) {
                    FileLog.e(e222);
                }
            }
            if (send != 0) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.Components.InstantCameraView$VideoRecorder$2$1 */
                    class C26181 implements VideoPlayerDelegate {
                        C26181() {
                        }

                        public void onStateChanged(boolean playWhenReady, int playbackState) {
                            long j = 0;
                            if (InstantCameraView.this.videoPlayer != null && InstantCameraView.this.videoPlayer.isPlaying() && playbackState == 4) {
                                VideoPlayer access$500 = InstantCameraView.this.videoPlayer;
                                if (InstantCameraView.this.videoEditedInfo.startTime > 0) {
                                    j = InstantCameraView.this.videoEditedInfo.startTime;
                                }
                                access$500.seekTo(j);
                            }
                        }

                        public void onError(Exception e) {
                            FileLog.e(e);
                        }

                        public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
                        }

                        public void onRenderedFirstFrame() {
                        }

                        public boolean onSurfaceDestroyed(SurfaceTexture surfaceTexture) {
                            return false;
                        }

                        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
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
                        if (send == 1) {
                            InstantCameraView.this.baseFragment.sendMedia(new MediaController$PhotoEntry(0, 0, 0, VideoRecorder.this.videoFile.getAbsolutePath(), 0, true), InstantCameraView.this.videoEditedInfo);
                        } else {
                            InstantCameraView.this.videoPlayer = new VideoPlayer();
                            InstantCameraView.this.videoPlayer.setDelegate(new C26181());
                            InstantCameraView.this.videoPlayer.setTextureView(InstantCameraView.this.textureView);
                            InstantCameraView.this.videoPlayer.preparePlayer(Uri.fromFile(VideoRecorder.this.videoFile), "other");
                            InstantCameraView.this.videoPlayer.play();
                            InstantCameraView.this.videoPlayer.setMute(true);
                            InstantCameraView.this.startProgressTimer();
                            AnimatorSet animatorSet = new AnimatorSet();
                            r1 = new Animator[3];
                            r1[0] = ObjectAnimator.ofFloat(InstantCameraView.this.switchCameraButton, "alpha", new float[]{0.0f});
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

        private void prepareEncoder() {
            try {
                int recordBufferSize = AudioRecord.getMinBufferSize(44100, 16, 2);
                if (recordBufferSize <= 0) {
                    recordBufferSize = 3584;
                }
                int bufferSize = 49152;
                if (49152 < recordBufferSize) {
                    bufferSize = (((recordBufferSize / 2048) + 1) * 2048) * 2;
                }
                for (int a = 0; a < 3; a++) {
                    this.buffers.add(new AudioBufferInfo());
                }
                this.audioRecorder = new AudioRecord(1, 44100, 16, 2, bufferSize);
                this.audioRecorder.startRecording();
                new Thread(this.recorderRunnable).start();
                this.audioBufferInfo = new BufferInfo();
                this.videoBufferInfo = new BufferInfo();
                MediaFormat audioFormat = new MediaFormat();
                audioFormat.setString("mime", "audio/mp4a-latm");
                audioFormat.setInteger("aac-profile", 2);
                audioFormat.setInteger("sample-rate", 44100);
                audioFormat.setInteger("channel-count", 1);
                audioFormat.setInteger("bitrate", 32000);
                audioFormat.setInteger("max-input-size", CacheDataSink.DEFAULT_BUFFER_SIZE);
                this.audioEncoder = MediaCodec.createEncoderByType("audio/mp4a-latm");
                this.audioEncoder.configure(audioFormat, null, null, 1);
                this.audioEncoder.start();
                this.videoEncoder = MediaCodec.createEncoderByType("video/avc");
                MediaFormat format = MediaFormat.createVideoFormat("video/avc", this.videoWidth, this.videoHeight);
                format.setInteger("color-format", 2130708361);
                MediaFormat mediaFormat = format;
                mediaFormat.setInteger("bitrate", this.videoBitrate);
                format.setInteger("frame-rate", 30);
                format.setInteger("i-frame-interval", 1);
                this.videoEncoder.configure(format, null, null, 1);
                this.surface = this.videoEncoder.createInputSurface();
                this.videoEncoder.start();
                Mp4Movie movie = new Mp4Movie();
                movie.setCacheFile(this.videoFile);
                movie.setRotation(0);
                movie.setSize(this.videoWidth, this.videoHeight);
                this.mediaMuxer = new MP4Builder().createMovie(movie);
                AndroidUtilities.runOnUIThread(new C26203());
                if (this.eglDisplay != EGL14.EGL_NO_DISPLAY) {
                    throw new RuntimeException("EGL already set up");
                }
                this.eglDisplay = EGL14.eglGetDisplay(0);
                if (this.eglDisplay == EGL14.EGL_NO_DISPLAY) {
                    throw new RuntimeException("unable to get EGL14 display");
                }
                int[] version = new int[2];
                if (EGL14.eglInitialize(this.eglDisplay, version, 0, version, 1)) {
                    if (this.eglContext == EGL14.EGL_NO_CONTEXT) {
                        android.opengl.EGLConfig[] configs = new android.opengl.EGLConfig[1];
                        if (EGL14.eglChooseConfig(this.eglDisplay, new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12352, 4, 12610, 1, 12344}, 0, configs, 0, configs.length, new int[1], 0)) {
                            int[] iArr = new int[3];
                            this.eglContext = EGL14.eglCreateContext(this.eglDisplay, configs[0], this.sharedEglContext, new int[]{12440, 2, 12344}, 0);
                            this.eglConfig = configs[0];
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
                        int vertexShader = InstantCameraView.this.loadShader(35633, InstantCameraView.VERTEX_SHADER);
                        int fragmentShader = InstantCameraView.this.loadShader(35632, InstantCameraView.FRAGMENT_SHADER);
                        if (vertexShader != 0 && fragmentShader != 0) {
                            this.drawProgram = GLES20.glCreateProgram();
                            GLES20.glAttachShader(this.drawProgram, vertexShader);
                            GLES20.glAttachShader(this.drawProgram, fragmentShader);
                            GLES20.glLinkProgram(this.drawProgram);
                            int[] linkStatus = new int[1];
                            GLES20.glGetProgramiv(this.drawProgram, 35714, linkStatus, 0);
                            if (linkStatus[0] == 0) {
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
            } catch (Throwable ioe) {
                throw new RuntimeException(ioe);
            }
        }

        public Surface getInputSurface() {
            return this.surface;
        }

        private void didWriteData(File file, boolean last) {
            if (this.videoConvertFirstWrite) {
                FileLoader.getInstance().uploadFile(file.toString(), false, false, 1, ConnectionsManager.FileTypeVideo);
                this.videoConvertFirstWrite = false;
                return;
            }
            FileLoader.getInstance().checkUploadNewDataAvailable(file.toString(), false, last ? file.length() : 0);
        }

        public void drainEncoder(boolean endOfStream) throws Exception {
            if (endOfStream) {
                this.videoEncoder.signalEndOfInputStream();
            }
            ByteBuffer[] encoderOutputBuffers = null;
            if (VERSION.SDK_INT < 21) {
                encoderOutputBuffers = this.videoEncoder.getOutputBuffers();
            }
            while (true) {
                MediaFormat newFormat;
                ByteBuffer encodedData;
                int encoderStatus = this.videoEncoder.dequeueOutputBuffer(this.videoBufferInfo, 10000);
                if (encoderStatus == -1) {
                    if (!endOfStream) {
                        break;
                    }
                } else if (encoderStatus == -3) {
                    if (VERSION.SDK_INT < 21) {
                        encoderOutputBuffers = this.videoEncoder.getOutputBuffers();
                    }
                } else if (encoderStatus == -2) {
                    newFormat = this.videoEncoder.getOutputFormat();
                    if (this.videoTrackIndex == -5) {
                        this.videoTrackIndex = this.mediaMuxer.addTrack(newFormat, false);
                    }
                } else if (encoderStatus < 0) {
                    continue;
                } else {
                    if (VERSION.SDK_INT < 21) {
                        encodedData = encoderOutputBuffers[encoderStatus];
                    } else {
                        encodedData = this.videoEncoder.getOutputBuffer(encoderStatus);
                    }
                    if (encodedData == null) {
                        throw new RuntimeException("encoderOutputBuffer " + encoderStatus + " was null");
                    }
                    if (this.videoBufferInfo.size > 1) {
                        if ((this.videoBufferInfo.flags & 2) == 0) {
                            if (this.mediaMuxer.writeSampleData(this.videoTrackIndex, encodedData, this.videoBufferInfo, true)) {
                                didWriteData(this.videoFile, false);
                            }
                        } else if (this.videoTrackIndex == -5) {
                            byte[] csd = new byte[this.videoBufferInfo.size];
                            encodedData.limit(this.videoBufferInfo.offset + this.videoBufferInfo.size);
                            encodedData.position(this.videoBufferInfo.offset);
                            encodedData.get(csd);
                            ByteBuffer sps = null;
                            ByteBuffer pps = null;
                            int a = this.videoBufferInfo.size - 1;
                            while (a >= 0 && a > 3) {
                                if (csd[a] == (byte) 1 && csd[a - 1] == (byte) 0 && csd[a - 2] == (byte) 0 && csd[a - 3] == (byte) 0) {
                                    sps = ByteBuffer.allocate(a - 3);
                                    pps = ByteBuffer.allocate(this.videoBufferInfo.size - (a - 3));
                                    sps.put(csd, 0, a - 3).position(0);
                                    pps.put(csd, a - 3, this.videoBufferInfo.size - (a - 3)).position(0);
                                    break;
                                }
                                a--;
                            }
                            newFormat = MediaFormat.createVideoFormat("video/avc", this.videoWidth, this.videoHeight);
                            if (!(sps == null || pps == null)) {
                                newFormat.setByteBuffer("csd-0", sps);
                                newFormat.setByteBuffer("csd-1", pps);
                            }
                            this.videoTrackIndex = this.mediaMuxer.addTrack(newFormat, false);
                        }
                    }
                    this.videoEncoder.releaseOutputBuffer(encoderStatus, false);
                    if ((this.videoBufferInfo.flags & 4) != 0) {
                        break;
                    }
                }
            }
            if (VERSION.SDK_INT < 21) {
                encoderOutputBuffers = this.audioEncoder.getOutputBuffers();
            }
            while (true) {
                encoderStatus = this.audioEncoder.dequeueOutputBuffer(this.audioBufferInfo, 0);
                if (encoderStatus == -1) {
                    if (!endOfStream) {
                        return;
                    }
                    if (!this.running && this.sendWhenDone == 0) {
                        return;
                    }
                } else if (encoderStatus == -3) {
                    if (VERSION.SDK_INT < 21) {
                        encoderOutputBuffers = this.audioEncoder.getOutputBuffers();
                    }
                } else if (encoderStatus == -2) {
                    newFormat = this.audioEncoder.getOutputFormat();
                    if (this.audioTrackIndex == -5) {
                        this.audioTrackIndex = this.mediaMuxer.addTrack(newFormat, true);
                    }
                } else if (encoderStatus < 0) {
                    continue;
                } else {
                    if (VERSION.SDK_INT < 21) {
                        encodedData = encoderOutputBuffers[encoderStatus];
                    } else {
                        encodedData = this.audioEncoder.getOutputBuffer(encoderStatus);
                    }
                    if (encodedData == null) {
                        throw new RuntimeException("encoderOutputBuffer " + encoderStatus + " was null");
                    }
                    if ((this.audioBufferInfo.flags & 2) != 0) {
                        this.audioBufferInfo.size = 0;
                    }
                    if (this.audioBufferInfo.size != 0 && this.mediaMuxer.writeSampleData(this.audioTrackIndex, encodedData, this.audioBufferInfo, false)) {
                        didWriteData(this.videoFile, false);
                    }
                    this.audioEncoder.releaseOutputBuffer(encoderStatus, false);
                    if ((this.audioBufferInfo.flags & 4) != 0) {
                        return;
                    }
                }
            }
        }

        protected void finalize() throws Throwable {
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
    }

    public InstantCameraView(Context context, ChatActivity parentFragment) {
        Size size;
        super(context);
        if (MediaController.getInstance().canRoundCamera16to9()) {
            size = new Size(16, 9);
        } else {
            size = new Size(4, 3);
        }
        this.aspectRatio = size;
        this.mMVPMatrix = new float[16];
        this.mSTMatrix = new float[16];
        this.moldSTMatrix = new float[16];
        setOnTouchListener(new C26062());
        setWillNotDraw(false);
        setBackgroundColor(-1073741824);
        this.baseFragment = parentFragment;
        this.paint = new Paint(1) {
            public void setAlpha(int a) {
                super.setAlpha(a);
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
                public void setScaleX(float scaleX) {
                    super.setScaleX(scaleX);
                    InstantCameraView.this.invalidate();
                }

                public void setAlpha(float alpha) {
                    super.setAlpha(alpha);
                    InstantCameraView.this.invalidate();
                }
            };
            this.cameraContainer.setOutlineProvider(new C26095());
            this.cameraContainer.setClipToOutline(true);
            this.cameraContainer.setWillNotDraw(false);
        } else {
            final Path path = new Path();
            final Paint paint = new Paint(1);
            paint.setColor(-16777216);
            paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
            this.cameraContainer = new FrameLayout(context) {
                public void setScaleX(float scaleX) {
                    super.setScaleX(scaleX);
                    InstantCameraView.this.invalidate();
                }

                protected void onSizeChanged(int w, int h, int oldw, int oldh) {
                    super.onSizeChanged(w, h, oldw, oldh);
                    path.reset();
                    path.addCircle((float) (w / 2), (float) (h / 2), (float) (w / 2), Direction.CW);
                    path.toggleInverseFillType();
                }

                protected void dispatchDraw(Canvas canvas) {
                    try {
                        super.dispatchDraw(canvas);
                        canvas.drawPath(path, paint);
                    } catch (Exception e) {
                    }
                }
            };
            this.cameraContainer.setWillNotDraw(false);
            this.cameraContainer.setLayerType(2, null);
        }
        addView(this.cameraContainer, new LayoutParams(AndroidUtilities.roundMessageSize, AndroidUtilities.roundMessageSize, 17));
        this.switchCameraButton = new ImageView(context);
        this.switchCameraButton.setScaleType(ScaleType.CENTER);
        addView(this.switchCameraButton, LayoutHelper.createFrame(48, 48.0f, 83, 20.0f, 0.0f, 0.0f, 14.0f));
        this.switchCameraButton.setOnClickListener(new C26127());
        this.muteImageView = new ImageView(context);
        this.muteImageView.setScaleType(ScaleType.CENTER);
        this.muteImageView.setImageResource(R.drawable.video_mute);
        this.muteImageView.setAlpha(0.0f);
        addView(this.muteImageView, LayoutHelper.createFrame(48, 48, 17));
        ((LayoutParams) this.muteImageView.getLayoutParams()).topMargin = (AndroidUtilities.roundMessageSize / 2) - AndroidUtilities.dp(24.0f);
        setVisibility(4);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.onInterceptTouchEvent(ev);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (getVisibility() != 0) {
            this.cameraContainer.setTranslationY((float) (getMeasuredHeight() / 2));
        }
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

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.recordProgressChanged) {
            long t = ((Long) args[0]).longValue();
            this.progress = ((float) t) / 60000.0f;
            this.recordedTime = t;
            invalidate();
        } else if (id == NotificationCenter.FileDidUpload) {
            String location = args[0];
            if (this.cameraFile != null && this.cameraFile.getAbsolutePath().equals(location)) {
                this.file = (TLRPC$InputFile) args[1];
                this.encryptedFile = (TLRPC$InputEncryptedFile) args[2];
                this.size = ((Long) args[5]).longValue();
                if (this.encryptedFile != null) {
                    this.key = (byte[]) args[3];
                    this.iv = (byte[]) args[4];
                }
            }
        }
    }

    public void destroy(boolean async, Runnable beforeDestroyRunnable) {
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, !async ? new Semaphore(0) : null, beforeDestroyRunnable);
        }
    }

    protected void onDraw(Canvas canvas) {
        float x = this.cameraContainer.getX();
        float y = this.cameraContainer.getY();
        this.rect.set(x - ((float) AndroidUtilities.dp(8.0f)), y - ((float) AndroidUtilities.dp(8.0f)), (((float) this.cameraContainer.getMeasuredWidth()) + x) + ((float) AndroidUtilities.dp(8.0f)), (((float) this.cameraContainer.getMeasuredHeight()) + y) + ((float) AndroidUtilities.dp(8.0f)));
        if (this.progress != 0.0f) {
            canvas.drawArc(this.rect, -90.0f, this.progress * 360.0f, false, this.paint);
        }
        if (Theme.chat_roundVideoShadow != null) {
            int x1 = ((int) x) - AndroidUtilities.dp(3.0f);
            int y1 = ((int) y) - AndroidUtilities.dp(2.0f);
            canvas.save();
            canvas.scale(this.cameraContainer.getScaleX(), this.cameraContainer.getScaleY(), (float) (((AndroidUtilities.roundMessageSize / 2) + x1) + AndroidUtilities.dp(3.0f)), (float) (((AndroidUtilities.roundMessageSize / 2) + y1) + AndroidUtilities.dp(3.0f)));
            Theme.chat_roundVideoShadow.setAlpha((int) (this.cameraContainer.getAlpha() * 255.0f));
            Theme.chat_roundVideoShadow.setBounds(x1, y1, (AndroidUtilities.roundMessageSize + x1) + AndroidUtilities.dp(6.0f), (AndroidUtilities.roundMessageSize + y1) + AndroidUtilities.dp(6.0f));
            Theme.chat_roundVideoShadow.draw(canvas);
            canvas.restore();
        }
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        setAlpha(0.0f);
        this.switchCameraButton.setAlpha(0.0f);
        this.cameraContainer.setAlpha(0.0f);
        this.muteImageView.setAlpha(0.0f);
        this.muteImageView.setScaleX(1.0f);
        this.muteImageView.setScaleY(1.0f);
        this.cameraContainer.setScaleX(0.1f);
        this.cameraContainer.setScaleY(0.1f);
        if (this.cameraContainer.getMeasuredWidth() != 0) {
            this.cameraContainer.setPivotX((float) (this.cameraContainer.getMeasuredWidth() / 2));
            this.cameraContainer.setPivotY((float) (this.cameraContainer.getMeasuredHeight() / 2));
        }
        if (visibility == 0) {
            try {
                ((Activity) getContext()).getWindow().addFlags(128);
                return;
            } catch (Exception e) {
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
            this.progress = 0.0f;
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
                this.textureView.setSurfaceTextureListener(new C26138());
                this.cameraContainer.addView(this.textureView, LayoutHelper.createFrame(-1, -1.0f));
                setVisibility(0);
                startAnimation(true);
            }
        }
    }

    public FrameLayout getCameraContainer() {
        return this.cameraContainer;
    }

    public void startAnimation(boolean open) {
        float f;
        int i;
        float f2 = 1.0f;
        float f3 = 0.0f;
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
        }
        PipRoundVideoView pipRoundVideoView = PipRoundVideoView.getInstance();
        if (pipRoundVideoView != null) {
            pipRoundVideoView.showTemporary(!open);
        }
        this.animatorSet = new AnimatorSet();
        AnimatorSet animatorSet = this.animatorSet;
        Animator[] animatorArr = new Animator[8];
        String str = "alpha";
        float[] fArr = new float[1];
        if (open) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        fArr[0] = f;
        animatorArr[0] = ObjectAnimator.ofFloat(this, str, fArr);
        ImageView imageView = this.switchCameraButton;
        String str2 = "alpha";
        float[] fArr2 = new float[1];
        if (open) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        fArr2[0] = f;
        animatorArr[1] = ObjectAnimator.ofFloat(imageView, str2, fArr2);
        animatorArr[2] = ObjectAnimator.ofFloat(this.muteImageView, "alpha", new float[]{0.0f});
        Paint paint = this.paint;
        String str3 = "alpha";
        int[] iArr = new int[1];
        if (open) {
            i = 255;
        } else {
            i = 0;
        }
        iArr[0] = i;
        animatorArr[3] = ObjectAnimator.ofInt(paint, str3, iArr);
        FrameLayout frameLayout = this.cameraContainer;
        str3 = "alpha";
        float[] fArr3 = new float[1];
        if (open) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        fArr3[0] = f;
        animatorArr[4] = ObjectAnimator.ofFloat(frameLayout, str3, fArr3);
        frameLayout = this.cameraContainer;
        str3 = "scaleX";
        fArr3 = new float[1];
        if (open) {
            f = 1.0f;
        } else {
            f = 0.1f;
        }
        fArr3[0] = f;
        animatorArr[5] = ObjectAnimator.ofFloat(frameLayout, str3, fArr3);
        FrameLayout frameLayout2 = this.cameraContainer;
        str2 = "scaleY";
        fArr2 = new float[1];
        if (!open) {
            f2 = 0.1f;
        }
        fArr2[0] = f2;
        animatorArr[6] = ObjectAnimator.ofFloat(frameLayout2, str2, fArr2);
        FrameLayout frameLayout3 = this.cameraContainer;
        str = "translationY";
        fArr = new float[2];
        if (open) {
            f = (float) (getMeasuredHeight() / 2);
        } else {
            f = 0.0f;
        }
        fArr[0] = f;
        if (!open) {
            f3 = (float) (getMeasuredHeight() / 2);
        }
        fArr[1] = f3;
        animatorArr[7] = ObjectAnimator.ofFloat(frameLayout3, str, fArr);
        animatorSet.playTogether(animatorArr);
        if (!open) {
            this.animatorSet.addListener(new C26149());
        }
        this.animatorSet.setDuration(180);
        this.animatorSet.setInterpolator(new DecelerateInterpolator());
        this.animatorSet.start();
    }

    public Rect getCameraRect() {
        this.cameraContainer.getLocationOnScreen(this.position);
        return new Rect((float) this.position[0], (float) this.position[1], (float) this.cameraContainer.getWidth(), (float) this.cameraContainer.getHeight());
    }

    public void changeVideoPreviewState(int state, float progress) {
        if (this.videoPlayer != null) {
            if (state == 0) {
                startProgressTimer();
                this.videoPlayer.play();
            } else if (state == 1) {
                stopProgressTimer();
                this.videoPlayer.pause();
            } else if (state == 2) {
                this.videoPlayer.seekTo((long) (((float) this.videoPlayer.getDuration()) * progress));
            }
        }
    }

    public void send(int state) {
        if (this.textureView != null) {
            stopProgressTimer();
            if (this.videoPlayer != null) {
                this.videoPlayer.releasePlayer();
                this.videoPlayer = null;
            }
            if (state == 4) {
                if (this.videoEditedInfo.needConvert()) {
                    long endTime;
                    VideoEditedInfo videoEditedInfo;
                    this.file = null;
                    this.encryptedFile = null;
                    this.key = null;
                    this.iv = null;
                    double totalDuration = (double) this.videoEditedInfo.estimatedDuration;
                    long startTime = this.videoEditedInfo.startTime >= 0 ? this.videoEditedInfo.startTime : 0;
                    if (this.videoEditedInfo.endTime >= 0) {
                        endTime = this.videoEditedInfo.endTime;
                    } else {
                        endTime = this.videoEditedInfo.estimatedDuration;
                    }
                    this.videoEditedInfo.estimatedDuration = endTime - startTime;
                    this.videoEditedInfo.estimatedSize = (long) (((double) this.size) * (((double) this.videoEditedInfo.estimatedDuration) / totalDuration));
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
                this.baseFragment.sendMedia(new MediaController$PhotoEntry(0, 0, 0, this.cameraFile.getAbsolutePath(), 0, true), this.videoEditedInfo);
                return;
            }
            this.cancelled = this.recordedTime < 800;
            this.recording = false;
            AndroidUtilities.cancelRunOnUIThread(this.timerRunnable);
            if (this.cameraThread != null) {
                int send;
                NotificationCenter instance = NotificationCenter.getInstance();
                int i = NotificationCenter.recordStopped;
                Object[] objArr = new Object[1];
                int i2 = (this.cancelled || state != 3) ? 0 : 2;
                objArr[0] = Integer.valueOf(i2);
                instance.postNotificationName(i, objArr);
                if (this.cancelled) {
                    send = 0;
                } else if (state == 3) {
                    send = 2;
                } else {
                    send = 1;
                }
                this.cameraThread.shutdown(send);
                this.cameraThread = null;
            }
            if (this.cancelled) {
                startAnimation(false);
            }
        }
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

    public void setAlpha(float alpha) {
        ((ColorDrawable) getBackground()).setAlpha((int) (192.0f * alpha));
        invalidate();
    }

    public View getSwitchButtonView() {
        return this.switchCameraButton;
    }

    public View getMuteImageView() {
        return this.muteImageView;
    }

    public Paint getPaint() {
        return this.paint;
    }

    public void hideCamera(boolean async) {
        destroy(async, null);
        this.cameraContainer.removeView(this.textureView);
        this.cameraContainer.setTranslationX(0.0f);
        this.cameraContainer.setTranslationY(0.0f);
        this.textureView = null;
    }

    private void switchCamera() {
        boolean z;
        if (this.cameraSession != null) {
            this.cameraSession.destroy();
            CameraController.getInstance().close(this.cameraSession, null, null);
            this.cameraSession = null;
        }
        if (this.isFrontface) {
            z = false;
        } else {
            z = true;
        }
        this.isFrontface = z;
        initCamera();
        this.cameraReady = false;
        this.cameraThread.reinitForNewCamera();
    }

    private boolean initCamera() {
        ArrayList<CameraInfo> cameraInfos = CameraController.getInstance().getCameras();
        if (cameraInfos == null) {
            return false;
        }
        int a;
        CameraInfo notFrontface = null;
        for (a = 0; a < cameraInfos.size(); a++) {
            CameraInfo cameraInfo = (CameraInfo) cameraInfos.get(a);
            if (!cameraInfo.isFrontface()) {
                notFrontface = cameraInfo;
            }
            if ((this.isFrontface && cameraInfo.isFrontface()) || (!this.isFrontface && !cameraInfo.isFrontface())) {
                this.selectedCamera = cameraInfo;
                break;
            }
            notFrontface = cameraInfo;
        }
        if (this.selectedCamera == null) {
            this.selectedCamera = notFrontface;
        }
        if (this.selectedCamera == null) {
            return false;
        }
        ArrayList<Size> previewSizes = this.selectedCamera.getPreviewSizes();
        ArrayList<Size> pictureSizes = this.selectedCamera.getPictureSizes();
        this.previewSize = CameraController.chooseOptimalSize(previewSizes, FetchService.QUERY_SINGLE, 270, this.aspectRatio);
        this.pictureSize = CameraController.chooseOptimalSize(pictureSizes, FetchService.QUERY_SINGLE, 270, this.aspectRatio);
        if (this.previewSize.mWidth != this.pictureSize.mWidth) {
            Size preview;
            int b;
            Size picture;
            boolean found = false;
            for (a = previewSizes.size() - 1; a >= 0; a--) {
                preview = (Size) previewSizes.get(a);
                for (b = pictureSizes.size() - 1; b >= 0; b--) {
                    picture = (Size) pictureSizes.get(b);
                    if (preview.mWidth >= this.pictureSize.mWidth && preview.mHeight >= this.pictureSize.mHeight && preview.mWidth == picture.mWidth && preview.mHeight == picture.mHeight) {
                        this.previewSize = preview;
                        this.pictureSize = picture;
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                }
            }
            if (!found) {
                for (a = previewSizes.size() - 1; a >= 0; a--) {
                    preview = (Size) previewSizes.get(a);
                    for (b = pictureSizes.size() - 1; b >= 0; b--) {
                        picture = (Size) pictureSizes.get(b);
                        if (preview.mWidth >= PsExtractor.VIDEO_STREAM_MASK && preview.mHeight >= PsExtractor.VIDEO_STREAM_MASK && preview.mWidth == picture.mWidth && preview.mHeight == picture.mHeight) {
                            this.previewSize = preview;
                            this.pictureSize = picture;
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        break;
                    }
                }
            }
        }
        FileLog.d("preview w = " + this.previewSize.mWidth + " h = " + this.previewSize.mHeight);
        return true;
    }

    private void createCamera(final SurfaceTexture surfaceTexture) {
        AndroidUtilities.runOnUIThread(new Runnable() {

            /* renamed from: org.telegram.ui.Components.InstantCameraView$10$1 */
            class C26011 implements Runnable {
                C26011() {
                }

                public void run() {
                    if (InstantCameraView.this.cameraSession != null) {
                        FileLog.e("camera initied");
                        InstantCameraView.this.cameraSession.setInitied();
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.InstantCameraView$10$2 */
            class C26022 implements Runnable {
                C26022() {
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
                    CameraController.getInstance().openRound(InstantCameraView.this.cameraSession, surfaceTexture, new C26011(), new C26022());
                }
            }
        });
    }

    private int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compileStatus, 0);
        if (compileStatus[0] != 0) {
            return shader;
        }
        FileLog.e(GLES20.glGetShaderInfoLog(shader));
        GLES20.glDeleteShader(shader);
        return 0;
    }

    private void startProgressTimer() {
        if (this.progressTimer != null) {
            try {
                this.progressTimer.cancel();
                this.progressTimer = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        this.progressTimer = new Timer();
        this.progressTimer.schedule(new TimerTask() {

            /* renamed from: org.telegram.ui.Components.InstantCameraView$11$1 */
            class C26031 implements Runnable {
                C26031() {
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
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            }

            public void run() {
                AndroidUtilities.runOnUIThread(new C26031());
            }
        }, 0, 17);
    }

    private void stopProgressTimer() {
        if (this.progressTimer != null) {
            try {
                this.progressTimer.cancel();
                this.progressTimer = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }
}
