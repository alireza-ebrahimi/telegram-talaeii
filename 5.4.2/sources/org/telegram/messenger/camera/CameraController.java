package org.telegram.messenger.camera;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.media.ThumbnailUtils;
import android.os.Build;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.Utilities;

public class CameraController implements OnInfoListener {
    private static final int CORE_POOL_SIZE = 1;
    private static volatile CameraController Instance = null;
    private static final int KEEP_ALIVE_SECONDS = 60;
    private static final int MAX_POOL_SIZE = 1;
    protected ArrayList<String> availableFlashModes = new ArrayList();
    protected ArrayList<CameraInfo> cameraInfos = null;
    private boolean cameraInitied;
    private boolean loadingCameras;
    private VideoTakeCallback onVideoTakeCallback;
    private String recordedFile;
    private MediaRecorder recorder;
    private boolean recordingSmallVideo;
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());

    /* renamed from: org.telegram.messenger.camera.CameraController$1 */
    class C34331 implements Runnable {

        /* renamed from: org.telegram.messenger.camera.CameraController$1$1 */
        class C34281 implements Comparator<Size> {
            C34281() {
            }

            public int compare(Size size, Size size2) {
                return size.mWidth < size2.mWidth ? 1 : size.mWidth > size2.mWidth ? -1 : size.mHeight >= size2.mHeight ? size.mHeight > size2.mHeight ? -1 : 0 : 1;
            }
        }

        /* renamed from: org.telegram.messenger.camera.CameraController$1$2 */
        class C34292 implements Runnable {
            C34292() {
            }

            public void run() {
                CameraController.this.loadingCameras = false;
                CameraController.this.cameraInitied = true;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.cameraInitied, new Object[0]);
            }
        }

        /* renamed from: org.telegram.messenger.camera.CameraController$1$3 */
        class C34303 implements Runnable {
            C34303() {
            }

            public void run() {
                CameraController.this.loadingCameras = false;
                CameraController.this.cameraInitied = false;
            }
        }

        C34331() {
        }

        public void run() {
            try {
                if (CameraController.this.cameraInfos == null) {
                    int numberOfCameras = Camera.getNumberOfCameras();
                    ArrayList arrayList = new ArrayList();
                    CameraInfo cameraInfo = new CameraInfo();
                    for (int i = 0; i < numberOfCameras; i++) {
                        int i2;
                        Size size;
                        Camera.getCameraInfo(i, cameraInfo);
                        CameraInfo cameraInfo2 = new CameraInfo(i, cameraInfo);
                        Camera open = Camera.open(cameraInfo2.getCameraId());
                        Parameters parameters = open.getParameters();
                        List supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                        for (i2 = 0; i2 < supportedPreviewSizes.size(); i2++) {
                            size = (Size) supportedPreviewSizes.get(i2);
                            if ((size.width != 1280 || size.height == 720) && size.height < 2160 && size.width < 2160) {
                                cameraInfo2.previewSizes.add(new Size(size.width, size.height));
                                FileLog.m13726e("preview size = " + size.width + " " + size.height);
                            }
                        }
                        List supportedPictureSizes = parameters.getSupportedPictureSizes();
                        for (i2 = 0; i2 < supportedPictureSizes.size(); i2++) {
                            size = (Size) supportedPictureSizes.get(i2);
                            if ((size.width != 1280 || size.height == 720) && !("samsung".equals(Build.MANUFACTURER) && "jflteuc".equals(Build.PRODUCT) && size.width >= 2048)) {
                                cameraInfo2.pictureSizes.add(new Size(size.width, size.height));
                                FileLog.m13726e("picture size = " + size.width + " " + size.height);
                            }
                        }
                        open.release();
                        arrayList.add(cameraInfo2);
                        Comparator c34281 = new C34281();
                        Collections.sort(cameraInfo2.previewSizes, c34281);
                        Collections.sort(cameraInfo2.pictureSizes, c34281);
                    }
                    CameraController.this.cameraInfos = arrayList;
                }
                AndroidUtilities.runOnUIThread(new C34292());
            } catch (Throwable e) {
                AndroidUtilities.runOnUIThread(new C34303());
                FileLog.m13728e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.camera.CameraController$2 */
    class C34342 implements Runnable {
        C34342() {
        }

        public void run() {
            if (CameraController.this.cameraInfos != null && !CameraController.this.cameraInfos.isEmpty()) {
                for (int i = 0; i < CameraController.this.cameraInfos.size(); i++) {
                    CameraInfo cameraInfo = (CameraInfo) CameraController.this.cameraInfos.get(i);
                    if (cameraInfo.camera != null) {
                        cameraInfo.camera.stopPreview();
                        cameraInfo.camera.setPreviewCallbackWithBuffer(null);
                        cameraInfo.camera.release();
                        cameraInfo.camera = null;
                    }
                }
                CameraController.this.cameraInfos = null;
            }
        }
    }

    static class CompareSizesByArea implements Comparator<Size> {
        CompareSizesByArea() {
        }

        public int compare(Size size, Size size2) {
            return Long.signum((((long) size.getWidth()) * ((long) size.getHeight())) - (((long) size2.getWidth()) * ((long) size2.getHeight())));
        }
    }

    public interface VideoTakeCallback {
        void onFinishVideoRecording(Bitmap bitmap);
    }

    public static Size chooseOptimalSize(List<Size> list, int i, int i2, Size size) {
        Collection arrayList = new ArrayList();
        int width = size.getWidth();
        int height = size.getHeight();
        for (int i3 = 0; i3 < list.size(); i3++) {
            Size size2 = (Size) list.get(i3);
            if (size2.getHeight() == (size2.getWidth() * height) / width && size2.getWidth() >= i && size2.getHeight() >= i2) {
                arrayList.add(size2);
            }
        }
        return arrayList.size() > 0 ? (Size) Collections.min(arrayList, new CompareSizesByArea()) : (Size) Collections.max(list, new CompareSizesByArea());
    }

    public static CameraController getInstance() {
        CameraController cameraController = Instance;
        if (cameraController == null) {
            synchronized (CameraController.class) {
                cameraController = Instance;
                if (cameraController == null) {
                    cameraController = new CameraController();
                    Instance = cameraController;
                }
            }
        }
        return cameraController;
    }

    private static int getOrientation(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        int i;
        int i2;
        int i3 = 0;
        while (i3 + 3 < bArr.length) {
            i = i3 + 1;
            if ((bArr[i3] & 255) != 255) {
                i2 = i;
                i = 0;
                break;
            }
            i2 = bArr[i] & 255;
            int pack;
            if (i2 != 255) {
                i3 = i + 1;
                if (!(i2 == 216 || i2 == 1)) {
                    if (i2 != 217) {
                        if (i2 != 218) {
                            pack = pack(bArr, i3, 2, false);
                            if (pack >= 2 && i3 + pack <= bArr.length) {
                                if (i2 == 225 && pack >= 8 && pack(bArr, i3 + 2, 4, false) == 1165519206 && pack(bArr, i3 + 6, 2, false) == 0) {
                                    i2 = i3 + 8;
                                    i = pack - 8;
                                    break;
                                }
                                i3 += pack;
                            } else {
                                return 0;
                            }
                        }
                        i = 0;
                        i2 = i3;
                        break;
                    }
                    break;
                }
            }
            i3 = i;
        }
        i = 0;
        i2 = i3;
        if (i <= 8) {
            return 0;
        }
        i3 = pack(bArr, i2, 4, false);
        if (i3 != 1229531648 && i3 != 1296891946) {
            return 0;
        }
        boolean z = i3 == 1229531648;
        int pack2 = pack(bArr, i2 + 4, 4, z) + 2;
        if (pack2 < 10 || pack2 > i) {
            return 0;
        }
        i2 += pack2;
        pack2 = i - pack2;
        i = pack(bArr, i2 - 2, 2, z);
        pack = i2;
        i2 = pack2;
        while (true) {
            pack2 = i - 1;
            if (i <= 0 || i2 < 12) {
                return 0;
            }
            if (pack(bArr, pack, 2, z) == 274) {
                break;
            }
            pack += 12;
            i2 -= 12;
            i = pack2;
        }
        switch (pack(bArr, pack + 8, 2, z)) {
            case 1:
                return 0;
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }

    private static int pack(byte[] bArr, int i, int i2, boolean z) {
        int i3 = 1;
        if (z) {
            i += i2 - 1;
            i3 = -1;
        }
        int i4 = 0;
        while (true) {
            int i5 = i2 - 1;
            if (i2 <= 0) {
                return i4;
            }
            i4 = (i4 << 8) | (bArr[i] & 255);
            i += i3;
            i2 = i5;
        }
    }

    public void cleanup() {
        this.threadPool.execute(new C34342());
    }

    public void close(final CameraSession cameraSession, final Semaphore semaphore, final Runnable runnable) {
        cameraSession.destroy();
        this.threadPool.execute(new Runnable() {
            public void run() {
                if (runnable != null) {
                    runnable.run();
                }
                if (cameraSession.cameraInfo.camera != null) {
                    try {
                        cameraSession.cameraInfo.camera.stopPreview();
                        cameraSession.cameraInfo.camera.setPreviewCallbackWithBuffer(null);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                    try {
                        cameraSession.cameraInfo.camera.release();
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                    cameraSession.cameraInfo.camera = null;
                    if (semaphore != null) {
                        semaphore.release();
                    }
                }
            }
        });
        if (semaphore != null) {
            try {
                semaphore.acquire();
            } catch (Throwable e) {
                FileLog.m13728e(e);
            }
        }
    }

    public ArrayList<CameraInfo> getCameras() {
        return this.cameraInfos;
    }

    public void initCamera() {
        if (!this.loadingCameras && !this.cameraInitied) {
            this.loadingCameras = true;
            this.threadPool.execute(new C34331());
        }
    }

    public boolean isCameraInitied() {
        return (!this.cameraInitied || this.cameraInfos == null || this.cameraInfos.isEmpty()) ? false : true;
    }

    public void onInfo(MediaRecorder mediaRecorder, int i, int i2) {
        if (i == 800 || i == 801 || i == 1) {
            MediaRecorder mediaRecorder2 = this.recorder;
            this.recorder = null;
            if (mediaRecorder2 != null) {
                mediaRecorder2.stop();
                mediaRecorder2.release();
            }
            if (this.onVideoTakeCallback != null) {
                final Bitmap createVideoThumbnail = ThumbnailUtils.createVideoThumbnail(this.recordedFile, 1);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (CameraController.this.onVideoTakeCallback != null) {
                            CameraController.this.onVideoTakeCallback.onFinishVideoRecording(createVideoThumbnail);
                            CameraController.this.onVideoTakeCallback = null;
                        }
                    }
                });
            }
        }
    }

    public void open(CameraSession cameraSession, SurfaceTexture surfaceTexture, Runnable runnable, Runnable runnable2) {
        if (cameraSession != null && surfaceTexture != null) {
            final CameraSession cameraSession2 = cameraSession;
            final Runnable runnable3 = runnable2;
            final SurfaceTexture surfaceTexture2 = surfaceTexture;
            final Runnable runnable4 = runnable;
            this.threadPool.execute(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    Camera camera = cameraSession2.cameraInfo.camera;
                    if (camera == null) {
                        try {
                            CameraInfo cameraInfo = cameraSession2.cameraInfo;
                            Camera open = Camera.open(cameraSession2.cameraInfo.cameraId);
                            cameraInfo.camera = open;
                            camera = open;
                        } catch (Throwable e) {
                            cameraSession2.cameraInfo.camera = null;
                            if (camera != null) {
                                camera.release();
                            }
                            FileLog.m13728e(e);
                            return;
                        }
                    }
                    List supportedFlashModes = camera.getParameters().getSupportedFlashModes();
                    CameraController.this.availableFlashModes.clear();
                    if (supportedFlashModes != null) {
                        for (int i = 0; i < supportedFlashModes.size(); i++) {
                            String str = (String) supportedFlashModes.get(i);
                            if (str.equals("off") || str.equals("on") || str.equals("auto")) {
                                CameraController.this.availableFlashModes.add(str);
                            }
                        }
                        cameraSession2.checkFlashMode((String) CameraController.this.availableFlashModes.get(0));
                    }
                    if (runnable3 != null) {
                        runnable3.run();
                    }
                    cameraSession2.configurePhotoCamera();
                    camera.setPreviewTexture(surfaceTexture2);
                    camera.startPreview();
                    if (runnable4 != null) {
                        AndroidUtilities.runOnUIThread(runnable4);
                    }
                }
            });
        }
    }

    public void openRound(CameraSession cameraSession, SurfaceTexture surfaceTexture, Runnable runnable, Runnable runnable2) {
        if (cameraSession == null || surfaceTexture == null) {
            FileLog.m13726e("failed to open round " + cameraSession + " tex = " + surfaceTexture);
            return;
        }
        final CameraSession cameraSession2 = cameraSession;
        final Runnable runnable3 = runnable2;
        final SurfaceTexture surfaceTexture2 = surfaceTexture;
        final Runnable runnable4 = runnable;
        this.threadPool.execute(new Runnable() {
            @SuppressLint({"NewApi"})
            public void run() {
                Camera camera = cameraSession2.cameraInfo.camera;
                try {
                    FileLog.m13726e("start creating round camera session");
                    if (camera == null) {
                        CameraInfo cameraInfo = cameraSession2.cameraInfo;
                        Camera open = Camera.open(cameraSession2.cameraInfo.cameraId);
                        cameraInfo.camera = open;
                        camera = open;
                    }
                    camera.getParameters();
                    cameraSession2.configureRoundCamera();
                    if (runnable3 != null) {
                        runnable3.run();
                    }
                    camera.setPreviewTexture(surfaceTexture2);
                    camera.startPreview();
                    if (runnable4 != null) {
                        AndroidUtilities.runOnUIThread(runnable4);
                    }
                    FileLog.m13726e("round camera session created");
                } catch (Throwable e) {
                    cameraSession2.cameraInfo.camera = null;
                    if (camera != null) {
                        camera.release();
                    }
                    FileLog.m13728e(e);
                }
            }
        });
    }

    public void recordVideo(CameraSession cameraSession, File file, VideoTakeCallback videoTakeCallback, Runnable runnable, boolean z) {
        if (cameraSession != null) {
            final CameraInfo cameraInfo = cameraSession.cameraInfo;
            final Camera camera = cameraInfo.camera;
            final CameraSession cameraSession2 = cameraSession;
            final boolean z2 = z;
            final File file2 = file;
            final VideoTakeCallback videoTakeCallback2 = videoTakeCallback;
            final Runnable runnable2 = runnable;
            this.threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        if (camera != null) {
                            try {
                                Parameters parameters = camera.getParameters();
                                parameters.setFlashMode(cameraSession2.getCurrentFlashMode().equals("on") ? "torch" : "off");
                                camera.setParameters(parameters);
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                            camera.unlock();
                            try {
                                Size chooseOptimalSize;
                                CameraController.this.recordingSmallVideo = z2;
                                CameraController.this.recorder = new MediaRecorder();
                                CameraController.this.recorder.setCamera(camera);
                                CameraController.this.recorder.setVideoSource(1);
                                CameraController.this.recorder.setAudioSource(5);
                                cameraSession2.configureRecorder(1, CameraController.this.recorder);
                                CameraController.this.recorder.setOutputFile(file2.getAbsolutePath());
                                CameraController.this.recorder.setMaxFileSize(1073741824);
                                CameraController.this.recorder.setVideoFrameRate(30);
                                CameraController.this.recorder.setMaxDuration(0);
                                if (CameraController.this.recordingSmallVideo) {
                                    chooseOptimalSize = CameraController.chooseOptimalSize(cameraInfo.getPictureSizes(), 640, 480, new Size(4, 3));
                                    CameraController.this.recorder.setVideoEncodingBitRate(1800000);
                                    CameraController.this.recorder.setAudioEncodingBitRate(32000);
                                    CameraController.this.recorder.setAudioChannels(1);
                                } else {
                                    chooseOptimalSize = CameraController.chooseOptimalSize(cameraInfo.getPictureSizes(), 720, 480, new Size(16, 9));
                                    CameraController.this.recorder.setVideoEncodingBitRate(1800000);
                                }
                                CameraController.this.recorder.setVideoSize(chooseOptimalSize.getWidth(), chooseOptimalSize.getHeight());
                                CameraController.this.recorder.setOnInfoListener(CameraController.this);
                                CameraController.this.recorder.prepare();
                                CameraController.this.recorder.start();
                                CameraController.this.onVideoTakeCallback = videoTakeCallback2;
                                CameraController.this.recordedFile = file2.getAbsolutePath();
                                if (runnable2 != null) {
                                    AndroidUtilities.runOnUIThread(runnable2);
                                }
                            } catch (Throwable e2) {
                                CameraController.this.recorder.release();
                                CameraController.this.recorder = null;
                                FileLog.m13728e(e2);
                            }
                        }
                    } catch (Throwable e22) {
                        FileLog.m13728e(e22);
                    }
                }
            });
        }
    }

    public void startPreview(final CameraSession cameraSession) {
        if (cameraSession != null) {
            this.threadPool.execute(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    Camera camera = cameraSession.cameraInfo.camera;
                    if (camera == null) {
                        try {
                            CameraInfo cameraInfo = cameraSession.cameraInfo;
                            Camera open = Camera.open(cameraSession.cameraInfo.cameraId);
                            cameraInfo.camera = open;
                            camera = open;
                        } catch (Throwable e) {
                            cameraSession.cameraInfo.camera = null;
                            if (camera != null) {
                                camera.release();
                            }
                            FileLog.m13728e(e);
                            return;
                        }
                    }
                    camera.startPreview();
                }
            });
        }
    }

    public void stopVideoRecording(final CameraSession cameraSession, final boolean z) {
        this.threadPool.execute(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r5 = this;
                r1 = 0;
                r0 = r3;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.cameraInfo;	 Catch:{ Exception -> 0x0076 }
                r2 = r0.camera;	 Catch:{ Exception -> 0x0076 }
                if (r2 == 0) goto L_0x002e;
            L_0x0009:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.recorder;	 Catch:{ Exception -> 0x0076 }
                if (r0 == 0) goto L_0x002e;
            L_0x0011:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.recorder;	 Catch:{ Exception -> 0x0076 }
                r3 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r4 = 0;
                r3.recorder = r4;	 Catch:{ Exception -> 0x0076 }
                r0.stop();	 Catch:{ Exception -> 0x0071 }
            L_0x0020:
                r0.release();	 Catch:{ Exception -> 0x0078 }
            L_0x0023:
                r2.reconnect();	 Catch:{ Exception -> 0x007d }
                r2.startPreview();	 Catch:{ Exception -> 0x007d }
            L_0x0029:
                r0 = r3;	 Catch:{ Exception -> 0x0082 }
                r0.stopVideoRecording();	 Catch:{ Exception -> 0x0082 }
            L_0x002e:
                r0 = r2.getParameters();	 Catch:{ Exception -> 0x0087 }
                r3 = "off";
                r0.setFlashMode(r3);	 Catch:{ Exception -> 0x0087 }
                r2.setParameters(r0);	 Catch:{ Exception -> 0x0087 }
            L_0x003b:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.threadPool;	 Catch:{ Exception -> 0x0076 }
                r3 = new org.telegram.messenger.camera.CameraController$10$1;	 Catch:{ Exception -> 0x0076 }
                r3.<init>(r2);	 Catch:{ Exception -> 0x0076 }
                r0.execute(r3);	 Catch:{ Exception -> 0x0076 }
                r0 = r4;	 Catch:{ Exception -> 0x0076 }
                if (r0 != 0) goto L_0x008e;
            L_0x004d:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.onVideoTakeCallback;	 Catch:{ Exception -> 0x0076 }
                if (r0 == 0) goto L_0x008e;
            L_0x0055:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.recordingSmallVideo;	 Catch:{ Exception -> 0x0076 }
                if (r0 != 0) goto L_0x008c;
            L_0x005d:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r0 = r0.recordedFile;	 Catch:{ Exception -> 0x0076 }
                r1 = 1;
                r0 = android.media.ThumbnailUtils.createVideoThumbnail(r0, r1);	 Catch:{ Exception -> 0x0076 }
            L_0x0068:
                r1 = new org.telegram.messenger.camera.CameraController$10$2;	 Catch:{ Exception -> 0x0076 }
                r1.<init>(r0);	 Catch:{ Exception -> 0x0076 }
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r1);	 Catch:{ Exception -> 0x0076 }
            L_0x0070:
                return;
            L_0x0071:
                r3 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r3);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0020;
            L_0x0076:
                r0 = move-exception;
                goto L_0x0070;
            L_0x0078:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0023;
            L_0x007d:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0029;
            L_0x0082:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0076 }
                goto L_0x002e;
            L_0x0087:
                r0 = move-exception;
                org.telegram.messenger.FileLog.m13728e(r0);	 Catch:{ Exception -> 0x0076 }
                goto L_0x003b;
            L_0x008c:
                r0 = r1;
                goto L_0x0068;
            L_0x008e:
                r0 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r1 = 0;
                r0.onVideoTakeCallback = r1;	 Catch:{ Exception -> 0x0076 }
                goto L_0x0070;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.CameraController.10.run():void");
            }
        });
    }

    public boolean takePicture(final File file, CameraSession cameraSession, final Runnable runnable) {
        if (cameraSession == null) {
            return false;
        }
        final CameraInfo cameraInfo = cameraSession.cameraInfo;
        try {
            cameraInfo.camera.takePicture(null, null, new PictureCallback() {
                public void onPictureTaken(byte[] bArr, Camera camera) {
                    Bitmap decodeByteArray;
                    float f = 1.0f;
                    int photoSize = (int) (((float) AndroidUtilities.getPhotoSize()) / AndroidUtilities.density);
                    String format = String.format(Locale.US, "%s@%d_%d", new Object[]{Utilities.MD5(file.getAbsolutePath()), Integer.valueOf(photoSize), Integer.valueOf(photoSize)});
                    try {
                        Options options = new Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                        float max = Math.max(((float) options.outWidth) / ((float) AndroidUtilities.getPhotoSize()), ((float) options.outHeight) / ((float) AndroidUtilities.getPhotoSize()));
                        if (max >= 1.0f) {
                            f = max;
                        }
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = (int) f;
                        options.inPurgeable = true;
                        decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
                    } catch (Throwable th) {
                        FileLog.m13728e(th);
                        decodeByteArray = null;
                    }
                    try {
                        if (cameraInfo.frontCamera != 0) {
                            try {
                                Matrix matrix = new Matrix();
                                matrix.setRotate((float) CameraController.getOrientation(bArr));
                                matrix.postScale(-1.0f, 1.0f);
                                Bitmap createBitmap = Bitmaps.createBitmap(decodeByteArray, 0, 0, decodeByteArray.getWidth(), decodeByteArray.getHeight(), matrix, false);
                                decodeByteArray.recycle();
                                OutputStream fileOutputStream = new FileOutputStream(file);
                                createBitmap.compress(CompressFormat.JPEG, 80, fileOutputStream);
                                fileOutputStream.flush();
                                fileOutputStream.getFD().sync();
                                fileOutputStream.close();
                                if (createBitmap != null) {
                                    ImageLoader.getInstance().putImageToCache(new BitmapDrawable(createBitmap), format);
                                }
                                if (runnable != null) {
                                    runnable.run();
                                    return;
                                }
                                return;
                            } catch (Throwable th2) {
                                FileLog.m13728e(th2);
                            }
                        }
                        FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                        fileOutputStream2.write(bArr);
                        fileOutputStream2.flush();
                        fileOutputStream2.getFD().sync();
                        fileOutputStream2.close();
                        if (decodeByteArray != null) {
                            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(decodeByteArray), format);
                        }
                    } catch (Throwable th3) {
                        FileLog.m13728e(th3);
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            });
            return true;
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return false;
        }
    }
}
