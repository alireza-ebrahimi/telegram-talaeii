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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.telegram.customization.fetch.FetchService;
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
    class C16841 implements Runnable {

        /* renamed from: org.telegram.messenger.camera.CameraController$1$1 */
        class C16791 implements Comparator<Size> {
            C16791() {
            }

            public int compare(Size o1, Size o2) {
                if (o1.mWidth < o2.mWidth) {
                    return 1;
                }
                if (o1.mWidth > o2.mWidth) {
                    return -1;
                }
                if (o1.mHeight < o2.mHeight) {
                    return 1;
                }
                if (o1.mHeight > o2.mHeight) {
                    return -1;
                }
                return 0;
            }
        }

        /* renamed from: org.telegram.messenger.camera.CameraController$1$2 */
        class C16802 implements Runnable {
            C16802() {
            }

            public void run() {
                CameraController.this.loadingCameras = false;
                CameraController.this.cameraInitied = true;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.cameraInitied, new Object[0]);
            }
        }

        /* renamed from: org.telegram.messenger.camera.CameraController$1$3 */
        class C16813 implements Runnable {
            C16813() {
            }

            public void run() {
                CameraController.this.loadingCameras = false;
                CameraController.this.cameraInitied = false;
            }
        }

        C16841() {
        }

        public void run() {
            try {
                if (CameraController.this.cameraInfos == null) {
                    int count = Camera.getNumberOfCameras();
                    ArrayList<CameraInfo> result = new ArrayList();
                    CameraInfo info = new CameraInfo();
                    for (int cameraId = 0; cameraId < count; cameraId++) {
                        int a;
                        Size size;
                        Camera.getCameraInfo(cameraId, info);
                        CameraInfo cameraInfo = new CameraInfo(cameraId, info);
                        Camera camera = Camera.open(cameraInfo.getCameraId());
                        Parameters params = camera.getParameters();
                        List<Size> list = params.getSupportedPreviewSizes();
                        for (a = 0; a < list.size(); a++) {
                            size = (Size) list.get(a);
                            if ((size.width != 1280 || size.height == 720) && size.height < 2160 && size.width < 2160) {
                                cameraInfo.previewSizes.add(new Size(size.width, size.height));
                                FileLog.e("preview size = " + size.width + " " + size.height);
                            }
                        }
                        list = params.getSupportedPictureSizes();
                        for (a = 0; a < list.size(); a++) {
                            size = (Size) list.get(a);
                            if ((size.width != 1280 || size.height == 720) && !("samsung".equals(Build.MANUFACTURER) && "jflteuc".equals(Build.PRODUCT) && size.width >= 2048)) {
                                cameraInfo.pictureSizes.add(new Size(size.width, size.height));
                                FileLog.e("picture size = " + size.width + " " + size.height);
                            }
                        }
                        camera.release();
                        result.add(cameraInfo);
                        Comparator<Size> comparator = new C16791();
                        Collections.sort(cameraInfo.previewSizes, comparator);
                        Collections.sort(cameraInfo.pictureSizes, comparator);
                    }
                    CameraController.this.cameraInfos = result;
                }
                AndroidUtilities.runOnUIThread(new C16802());
            } catch (Exception e) {
                AndroidUtilities.runOnUIThread(new C16813());
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.messenger.camera.CameraController$2 */
    class C16852 implements Runnable {
        C16852() {
        }

        public void run() {
            if (CameraController.this.cameraInfos != null && !CameraController.this.cameraInfos.isEmpty()) {
                for (int a = 0; a < CameraController.this.cameraInfos.size(); a++) {
                    CameraInfo info = (CameraInfo) CameraController.this.cameraInfos.get(a);
                    if (info.camera != null) {
                        info.camera.stopPreview();
                        info.camera.setPreviewCallbackWithBuffer(null);
                        info.camera.release();
                        info.camera = null;
                    }
                }
                CameraController.this.cameraInfos = null;
            }
        }
    }

    static class CompareSizesByArea implements Comparator<Size> {
        CompareSizesByArea() {
        }

        public int compare(Size lhs, Size rhs) {
            return Long.signum((((long) lhs.getWidth()) * ((long) lhs.getHeight())) - (((long) rhs.getWidth()) * ((long) rhs.getHeight())));
        }
    }

    public interface VideoTakeCallback {
        void onFinishVideoRecording(Bitmap bitmap);
    }

    public static CameraController getInstance() {
        CameraController localInstance = Instance;
        if (localInstance == null) {
            synchronized (CameraController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        CameraController localInstance2 = new CameraController();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public void initCamera() {
        if (!this.loadingCameras && !this.cameraInitied) {
            this.loadingCameras = true;
            this.threadPool.execute(new C16841());
        }
    }

    public boolean isCameraInitied() {
        return (!this.cameraInitied || this.cameraInfos == null || this.cameraInfos.isEmpty()) ? false : true;
    }

    public void cleanup() {
        this.threadPool.execute(new C16852());
    }

    public void close(final CameraSession session, final Semaphore semaphore, final Runnable beforeDestroyRunnable) {
        session.destroy();
        this.threadPool.execute(new Runnable() {
            public void run() {
                if (beforeDestroyRunnable != null) {
                    beforeDestroyRunnable.run();
                }
                if (session.cameraInfo.camera != null) {
                    try {
                        session.cameraInfo.camera.stopPreview();
                        session.cameraInfo.camera.setPreviewCallbackWithBuffer(null);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    try {
                        session.cameraInfo.camera.release();
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                    session.cameraInfo.camera = null;
                    if (semaphore != null) {
                        semaphore.release();
                    }
                }
            }
        });
        if (semaphore != null) {
            try {
                semaphore.acquire();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public ArrayList<CameraInfo> getCameras() {
        return this.cameraInfos;
    }

    private static int getOrientation(byte[] jpeg) {
        boolean littleEndian = true;
        if (jpeg == null) {
            return 0;
        }
        int offset = 0;
        int length = 0;
        while (offset + 3 < jpeg.length) {
            int offset2 = offset + 1;
            if ((jpeg[offset] & 255) != 255) {
                offset = offset2;
                break;
            }
            int marker = jpeg[offset2] & 255;
            if (marker != 255) {
                offset = offset2 + 1;
                if (!(marker == 216 || marker == 1)) {
                    if (marker != 217 && marker != 218) {
                        length = pack(jpeg, offset, 2, false);
                        if (length >= 2 && offset + length <= jpeg.length) {
                            if (marker == 225 && length >= 8 && pack(jpeg, offset + 2, 4, false) == 1165519206 && pack(jpeg, offset + 6, 2, false) == 0) {
                                offset += 8;
                                length -= 8;
                                break;
                            }
                            offset += length;
                            length = 0;
                        } else {
                            return 0;
                        }
                    }
                    break;
                }
            }
            offset = offset2;
        }
        if (length <= 8) {
            return 0;
        }
        int tag = pack(jpeg, offset, 4, false);
        if (tag != 1229531648 && tag != 1296891946) {
            return 0;
        }
        if (tag != 1229531648) {
            littleEndian = false;
        }
        int count = pack(jpeg, offset + 4, 4, littleEndian) + 2;
        if (count < 10 || count > length) {
            return 0;
        }
        offset += count;
        length -= count;
        int count2 = pack(jpeg, offset - 2, 2, littleEndian);
        while (true) {
            count = count2 - 1;
            if (count2 <= 0 || length < 12) {
                return 0;
            }
            if (pack(jpeg, offset, 2, littleEndian) == 274) {
                break;
            }
            offset += 12;
            length -= 12;
            count2 = count;
        }
        switch (pack(jpeg, offset + 8, 2, littleEndian)) {
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

    private static int pack(byte[] bytes, int offset, int length, boolean littleEndian) {
        int step = 1;
        if (littleEndian) {
            offset += length - 1;
            step = -1;
        }
        int value = 0;
        int length2 = length;
        while (true) {
            length = length2 - 1;
            if (length2 <= 0) {
                return value;
            }
            value = (value << 8) | (bytes[offset] & 255);
            offset += step;
            length2 = length;
        }
    }

    public boolean takePicture(final File path, CameraSession session, final Runnable callback) {
        if (session == null) {
            return false;
        }
        final CameraInfo info = session.cameraInfo;
        try {
            info.camera.takePicture(null, null, new PictureCallback() {
                public void onPictureTaken(byte[] data, Camera camera) {
                    Bitmap bitmap = null;
                    int size = (int) (((float) AndroidUtilities.getPhotoSize()) / AndroidUtilities.density);
                    String key = String.format(Locale.US, "%s@%d_%d", new Object[]{Utilities.MD5(path.getAbsolutePath()), Integer.valueOf(size), Integer.valueOf(size)});
                    try {
                        Options options = new Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeByteArray(data, 0, data.length, options);
                        float scaleFactor = Math.max(((float) options.outWidth) / ((float) AndroidUtilities.getPhotoSize()), ((float) options.outHeight) / ((float) AndroidUtilities.getPhotoSize()));
                        if (scaleFactor < 1.0f) {
                            scaleFactor = 1.0f;
                        }
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = (int) scaleFactor;
                        options.inPurgeable = true;
                        bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    try {
                        FileOutputStream outputStream;
                        if (info.frontCamera != 0) {
                            try {
                                Matrix matrix = new Matrix();
                                matrix.setRotate((float) CameraController.getOrientation(data));
                                matrix.postScale(-1.0f, 1.0f);
                                Bitmap scaled = Bitmaps.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
                                bitmap.recycle();
                                outputStream = new FileOutputStream(path);
                                scaled.compress(CompressFormat.JPEG, 80, outputStream);
                                outputStream.flush();
                                outputStream.getFD().sync();
                                outputStream.close();
                                if (scaled != null) {
                                    ImageLoader.getInstance().putImageToCache(new BitmapDrawable(scaled), key);
                                }
                                if (callback != null) {
                                    callback.run();
                                    return;
                                }
                                return;
                            } catch (Throwable e2) {
                                FileLog.e(e2);
                            }
                        }
                        outputStream = new FileOutputStream(path);
                        outputStream.write(data);
                        outputStream.flush();
                        outputStream.getFD().sync();
                        outputStream.close();
                        if (bitmap != null) {
                            ImageLoader.getInstance().putImageToCache(new BitmapDrawable(bitmap), key);
                        }
                    } catch (Exception e3) {
                        FileLog.e(e3);
                    }
                    if (callback != null) {
                        callback.run();
                    }
                }
            });
            return true;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    public void startPreview(final CameraSession session) {
        if (session != null) {
            this.threadPool.execute(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    Camera camera = session.cameraInfo.camera;
                    if (camera == null) {
                        try {
                            CameraInfo cameraInfo = session.cameraInfo;
                            Camera camera2 = Camera.open(session.cameraInfo.cameraId);
                            cameraInfo.camera = camera2;
                            camera = camera2;
                        } catch (Exception e) {
                            session.cameraInfo.camera = null;
                            if (camera != null) {
                                camera.release();
                            }
                            FileLog.e(e);
                            return;
                        }
                    }
                    camera.startPreview();
                }
            });
        }
    }

    public void openRound(CameraSession session, SurfaceTexture texture, Runnable callback, Runnable configureCallback) {
        if (session == null || texture == null) {
            FileLog.e("failed to open round " + session + " tex = " + texture);
            return;
        }
        final CameraSession cameraSession = session;
        final Runnable runnable = configureCallback;
        final SurfaceTexture surfaceTexture = texture;
        final Runnable runnable2 = callback;
        this.threadPool.execute(new Runnable() {
            @SuppressLint({"NewApi"})
            public void run() {
                Camera camera = cameraSession.cameraInfo.camera;
                try {
                    FileLog.e("start creating round camera session");
                    if (camera == null) {
                        CameraInfo cameraInfo = cameraSession.cameraInfo;
                        Camera camera2 = Camera.open(cameraSession.cameraInfo.cameraId);
                        cameraInfo.camera = camera2;
                        camera = camera2;
                    }
                    Parameters params = camera.getParameters();
                    cameraSession.configureRoundCamera();
                    if (runnable != null) {
                        runnable.run();
                    }
                    camera.setPreviewTexture(surfaceTexture);
                    camera.startPreview();
                    if (runnable2 != null) {
                        AndroidUtilities.runOnUIThread(runnable2);
                    }
                    FileLog.e("round camera session created");
                } catch (Exception e) {
                    cameraSession.cameraInfo.camera = null;
                    if (camera != null) {
                        camera.release();
                    }
                    FileLog.e(e);
                }
            }
        });
    }

    public void open(CameraSession session, SurfaceTexture texture, Runnable callback, Runnable prestartCallback) {
        if (session != null && texture != null) {
            final CameraSession cameraSession = session;
            final Runnable runnable = prestartCallback;
            final SurfaceTexture surfaceTexture = texture;
            final Runnable runnable2 = callback;
            this.threadPool.execute(new Runnable() {
                @SuppressLint({"NewApi"})
                public void run() {
                    Camera camera = cameraSession.cameraInfo.camera;
                    if (camera == null) {
                        try {
                            CameraInfo cameraInfo = cameraSession.cameraInfo;
                            Camera camera2 = Camera.open(cameraSession.cameraInfo.cameraId);
                            cameraInfo.camera = camera2;
                            camera = camera2;
                        } catch (Exception e) {
                            cameraSession.cameraInfo.camera = null;
                            if (camera != null) {
                                camera.release();
                            }
                            FileLog.e(e);
                            return;
                        }
                    }
                    List<String> rawFlashModes = camera.getParameters().getSupportedFlashModes();
                    CameraController.this.availableFlashModes.clear();
                    if (rawFlashModes != null) {
                        for (int a = 0; a < rawFlashModes.size(); a++) {
                            String rawFlashMode = (String) rawFlashModes.get(a);
                            if (rawFlashMode.equals("off") || rawFlashMode.equals("on") || rawFlashMode.equals("auto")) {
                                CameraController.this.availableFlashModes.add(rawFlashMode);
                            }
                        }
                        cameraSession.checkFlashMode((String) CameraController.this.availableFlashModes.get(0));
                    }
                    if (runnable != null) {
                        runnable.run();
                    }
                    cameraSession.configurePhotoCamera();
                    camera.setPreviewTexture(surfaceTexture);
                    camera.startPreview();
                    if (runnable2 != null) {
                        AndroidUtilities.runOnUIThread(runnable2);
                    }
                }
            });
        }
    }

    public void recordVideo(CameraSession session, File path, VideoTakeCallback callback, Runnable onVideoStartRecord, boolean smallVideo) {
        if (session != null) {
            final CameraInfo info = session.cameraInfo;
            final Camera camera = info.camera;
            final CameraSession cameraSession = session;
            final boolean z = smallVideo;
            final File file = path;
            final VideoTakeCallback videoTakeCallback = callback;
            final Runnable runnable = onVideoStartRecord;
            this.threadPool.execute(new Runnable() {
                public void run() {
                    try {
                        if (camera != null) {
                            try {
                                Parameters params = camera.getParameters();
                                params.setFlashMode(cameraSession.getCurrentFlashMode().equals("on") ? "torch" : "off");
                                camera.setParameters(params);
                            } catch (Exception e) {
                                FileLog.e(e);
                            }
                            camera.unlock();
                            try {
                                Size pictureSize;
                                CameraController.this.recordingSmallVideo = z;
                                CameraController.this.recorder = new MediaRecorder();
                                CameraController.this.recorder.setCamera(camera);
                                CameraController.this.recorder.setVideoSource(1);
                                CameraController.this.recorder.setAudioSource(5);
                                cameraSession.configureRecorder(1, CameraController.this.recorder);
                                CameraController.this.recorder.setOutputFile(file.getAbsolutePath());
                                CameraController.this.recorder.setMaxFileSize(1073741824);
                                CameraController.this.recorder.setVideoFrameRate(30);
                                CameraController.this.recorder.setMaxDuration(0);
                                if (CameraController.this.recordingSmallVideo) {
                                    pictureSize = CameraController.chooseOptimalSize(info.getPictureSizes(), 640, FetchService.QUERY_SINGLE, new Size(4, 3));
                                    CameraController.this.recorder.setVideoEncodingBitRate(1800000);
                                    CameraController.this.recorder.setAudioEncodingBitRate(32000);
                                    CameraController.this.recorder.setAudioChannels(1);
                                } else {
                                    pictureSize = CameraController.chooseOptimalSize(info.getPictureSizes(), 720, FetchService.QUERY_SINGLE, new Size(16, 9));
                                    CameraController.this.recorder.setVideoEncodingBitRate(1800000);
                                }
                                CameraController.this.recorder.setVideoSize(pictureSize.getWidth(), pictureSize.getHeight());
                                CameraController.this.recorder.setOnInfoListener(CameraController.this);
                                CameraController.this.recorder.prepare();
                                CameraController.this.recorder.start();
                                CameraController.this.onVideoTakeCallback = videoTakeCallback;
                                CameraController.this.recordedFile = file.getAbsolutePath();
                                if (runnable != null) {
                                    AndroidUtilities.runOnUIThread(runnable);
                                }
                            } catch (Exception e2) {
                                CameraController.this.recorder.release();
                                CameraController.this.recorder = null;
                                FileLog.e(e2);
                            }
                        }
                    } catch (Exception e22) {
                        FileLog.e(e22);
                    }
                }
            });
        }
    }

    public void onInfo(MediaRecorder mediaRecorder, int what, int extra) {
        if (what == 800 || what == 801 || what == 1) {
            MediaRecorder tempRecorder = this.recorder;
            this.recorder = null;
            if (tempRecorder != null) {
                tempRecorder.stop();
                tempRecorder.release();
            }
            if (this.onVideoTakeCallback != null) {
                final Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(this.recordedFile, 1);
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (CameraController.this.onVideoTakeCallback != null) {
                            CameraController.this.onVideoTakeCallback.onFinishVideoRecording(bitmap);
                            CameraController.this.onVideoTakeCallback = null;
                        }
                    }
                });
            }
        }
    }

    public void stopVideoRecording(final CameraSession session, final boolean abandon) {
        this.threadPool.execute(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                r8 = this;
                r0 = 0;
                r6 = r3;	 Catch:{ Exception -> 0x0076 }
                r3 = r6.cameraInfo;	 Catch:{ Exception -> 0x0076 }
                r1 = r3.camera;	 Catch:{ Exception -> 0x0076 }
                if (r1 == 0) goto L_0x002e;
            L_0x0009:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r6 = r6.recorder;	 Catch:{ Exception -> 0x0076 }
                if (r6 == 0) goto L_0x002e;
            L_0x0011:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r5 = r6.recorder;	 Catch:{ Exception -> 0x0076 }
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r7 = 0;
                r6.recorder = r7;	 Catch:{ Exception -> 0x0076 }
                r5.stop();	 Catch:{ Exception -> 0x0071 }
            L_0x0020:
                r5.release();	 Catch:{ Exception -> 0x0078 }
            L_0x0023:
                r1.reconnect();	 Catch:{ Exception -> 0x007d }
                r1.startPreview();	 Catch:{ Exception -> 0x007d }
            L_0x0029:
                r6 = r3;	 Catch:{ Exception -> 0x0082 }
                r6.stopVideoRecording();	 Catch:{ Exception -> 0x0082 }
            L_0x002e:
                r4 = r1.getParameters();	 Catch:{ Exception -> 0x0087 }
                r6 = "off";
                r4.setFlashMode(r6);	 Catch:{ Exception -> 0x0087 }
                r1.setParameters(r4);	 Catch:{ Exception -> 0x0087 }
            L_0x003b:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r6 = r6.threadPool;	 Catch:{ Exception -> 0x0076 }
                r7 = new org.telegram.messenger.camera.CameraController$10$1;	 Catch:{ Exception -> 0x0076 }
                r7.<init>(r1);	 Catch:{ Exception -> 0x0076 }
                r6.execute(r7);	 Catch:{ Exception -> 0x0076 }
                r6 = r4;	 Catch:{ Exception -> 0x0076 }
                if (r6 != 0) goto L_0x008c;
            L_0x004d:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r6 = r6.onVideoTakeCallback;	 Catch:{ Exception -> 0x0076 }
                if (r6 == 0) goto L_0x008c;
            L_0x0055:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r6 = r6.recordingSmallVideo;	 Catch:{ Exception -> 0x0076 }
                if (r6 != 0) goto L_0x0068;
            L_0x005d:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r6 = r6.recordedFile;	 Catch:{ Exception -> 0x0076 }
                r7 = 1;
                r0 = android.media.ThumbnailUtils.createVideoThumbnail(r6, r7);	 Catch:{ Exception -> 0x0076 }
            L_0x0068:
                r6 = new org.telegram.messenger.camera.CameraController$10$2;	 Catch:{ Exception -> 0x0076 }
                r6.<init>(r0);	 Catch:{ Exception -> 0x0076 }
                org.telegram.messenger.AndroidUtilities.runOnUIThread(r6);	 Catch:{ Exception -> 0x0076 }
            L_0x0070:
                return;
            L_0x0071:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0020;
            L_0x0076:
                r6 = move-exception;
                goto L_0x0070;
            L_0x0078:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0023;
            L_0x007d:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0076 }
                goto L_0x0029;
            L_0x0082:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0076 }
                goto L_0x002e;
            L_0x0087:
                r2 = move-exception;
                org.telegram.messenger.FileLog.e(r2);	 Catch:{ Exception -> 0x0076 }
                goto L_0x003b;
            L_0x008c:
                r6 = org.telegram.messenger.camera.CameraController.this;	 Catch:{ Exception -> 0x0076 }
                r7 = 0;
                r6.onVideoTakeCallback = r7;	 Catch:{ Exception -> 0x0076 }
                goto L_0x0070;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.messenger.camera.CameraController.10.run():void");
            }
        });
    }

    public static Size chooseOptimalSize(List<Size> choices, int width, int height, Size aspectRatio) {
        List<Size> bigEnough = new ArrayList();
        int w = aspectRatio.getWidth();
        int h = aspectRatio.getHeight();
        for (int a = 0; a < choices.size(); a++) {
            Size option = (Size) choices.get(a);
            if (option.getHeight() == (option.getWidth() * h) / w && option.getWidth() >= width && option.getHeight() >= height) {
                bigEnough.add(option);
            }
        }
        if (bigEnough.size() > 0) {
            return (Size) Collections.min(bigEnough, new CompareSizesByArea());
        }
        return (Size) Collections.max(choices, new CompareSizesByArea());
    }
}
