package com.google.android.gms.vision;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.SystemClock;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.google.android.gms.common.images.Size;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CameraSource {
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_BACK = 0;
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_FRONT = 1;
    private Context mContext;
    private int zzcma;
    private final Object zzlew;
    private Camera zzlex;
    private int zzley;
    private Size zzlez;
    private float zzlfa;
    private int zzlfb;
    private int zzlfc;
    private boolean zzlfd;
    private SurfaceTexture zzlfe;
    private boolean zzlff;
    private Thread zzlfg;
    private zzb zzlfh;
    private Map<byte[], ByteBuffer> zzlfi;

    public static class Builder {
        private final Detector<?> zzlfj;
        private CameraSource zzlfk = new CameraSource();

        public Builder(Context context, Detector<?> detector) {
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            } else if (detector == null) {
                throw new IllegalArgumentException("No detector supplied.");
            } else {
                this.zzlfj = detector;
                this.zzlfk.mContext = context;
            }
        }

        public CameraSource build() {
            CameraSource cameraSource = this.zzlfk;
            CameraSource cameraSource2 = this.zzlfk;
            cameraSource2.getClass();
            cameraSource.zzlfh = new zzb(cameraSource2, this.zzlfj);
            return this.zzlfk;
        }

        public Builder setAutoFocusEnabled(boolean z) {
            this.zzlfk.zzlfd = z;
            return this;
        }

        public Builder setFacing(int i) {
            if (i == 0 || i == 1) {
                this.zzlfk.zzley = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid camera: " + i);
        }

        public Builder setRequestedFps(float f) {
            if (f <= 0.0f) {
                throw new IllegalArgumentException("Invalid fps: " + f);
            }
            this.zzlfk.zzlfa = f;
            return this;
        }

        public Builder setRequestedPreviewSize(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                throw new IllegalArgumentException("Invalid preview size: " + i + "x" + i2);
            }
            this.zzlfk.zzlfb = i;
            this.zzlfk.zzlfc = i2;
            return this;
        }
    }

    public interface PictureCallback {
        void onPictureTaken(byte[] bArr);
    }

    public interface ShutterCallback {
        void onShutter();
    }

    class zza implements PreviewCallback {
        private /* synthetic */ CameraSource zzlfl;

        private zza(CameraSource cameraSource) {
            this.zzlfl = cameraSource;
        }

        public final void onPreviewFrame(byte[] bArr, Camera camera) {
            this.zzlfl.zzlfh.zza(bArr, camera);
        }
    }

    class zzb implements Runnable {
        private boolean mActive = true;
        private final Object mLock = new Object();
        private long zzebf = SystemClock.elapsedRealtime();
        private Detector<?> zzlfj;
        private /* synthetic */ CameraSource zzlfl;
        private long zzlfm;
        private int zzlfn = 0;
        private ByteBuffer zzlfo;

        zzb(CameraSource cameraSource, Detector<?> detector) {
            this.zzlfl = cameraSource;
            this.zzlfj = detector;
        }

        @SuppressLint({"Assert"})
        final void release() {
            this.zzlfj.release();
            this.zzlfj = null;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        @android.annotation.SuppressLint({"InlinedApi"})
        public final void run() {
            /*
            r6 = this;
        L_0x0000:
            r1 = r6.mLock;
            monitor-enter(r1);
        L_0x0003:
            r0 = r6.mActive;	 Catch:{ all -> 0x0023 }
            if (r0 == 0) goto L_0x001d;
        L_0x0007:
            r0 = r6.zzlfo;	 Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x001d;
        L_0x000b:
            r0 = r6.mLock;	 Catch:{ InterruptedException -> 0x0011 }
            r0.wait();	 Catch:{ InterruptedException -> 0x0011 }
            goto L_0x0003;
        L_0x0011:
            r0 = move-exception;
            r2 = "CameraSource";
            r3 = "Frame processing loop terminated.";
            android.util.Log.d(r2, r3, r0);	 Catch:{ all -> 0x0023 }
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        L_0x001c:
            return;
        L_0x001d:
            r0 = r6.mActive;	 Catch:{ all -> 0x0023 }
            if (r0 != 0) goto L_0x0026;
        L_0x0021:
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            goto L_0x001c;
        L_0x0023:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            throw r0;
        L_0x0026:
            r0 = new com.google.android.gms.vision.Frame$Builder;	 Catch:{ all -> 0x0023 }
            r0.<init>();	 Catch:{ all -> 0x0023 }
            r2 = r6.zzlfo;	 Catch:{ all -> 0x0023 }
            r3 = r6.zzlfl;	 Catch:{ all -> 0x0023 }
            r3 = r3.zzlez;	 Catch:{ all -> 0x0023 }
            r3 = r3.getWidth();	 Catch:{ all -> 0x0023 }
            r4 = r6.zzlfl;	 Catch:{ all -> 0x0023 }
            r4 = r4.zzlez;	 Catch:{ all -> 0x0023 }
            r4 = r4.getHeight();	 Catch:{ all -> 0x0023 }
            r5 = 17;
            r0 = r0.setImageData(r2, r3, r4, r5);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzlfn;	 Catch:{ all -> 0x0023 }
            r0 = r0.setId(r2);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzlfm;	 Catch:{ all -> 0x0023 }
            r0 = r0.setTimestampMillis(r2);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzlfl;	 Catch:{ all -> 0x0023 }
            r2 = r2.zzcma;	 Catch:{ all -> 0x0023 }
            r0 = r0.setRotation(r2);	 Catch:{ all -> 0x0023 }
            r0 = r0.build();	 Catch:{ all -> 0x0023 }
            r2 = r6.zzlfo;	 Catch:{ all -> 0x0023 }
            r3 = 0;
            r6.zzlfo = r3;	 Catch:{ all -> 0x0023 }
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            r1 = r6.zzlfj;	 Catch:{ Throwable -> 0x007a }
            r1.receiveFrame(r0);	 Catch:{ Throwable -> 0x007a }
            r0 = r6.zzlfl;
            r0 = r0.zzlex;
            r1 = r2.array();
            r0.addCallbackBuffer(r1);
            goto L_0x0000;
        L_0x007a:
            r0 = move-exception;
            r1 = "CameraSource";
            r3 = "Exception thrown from receiver.";
            android.util.Log.e(r1, r3, r0);	 Catch:{ all -> 0x0093 }
            r0 = r6.zzlfl;
            r0 = r0.zzlex;
            r1 = r2.array();
            r0.addCallbackBuffer(r1);
            goto L_0x0000;
        L_0x0093:
            r0 = move-exception;
            r1 = r6.zzlfl;
            r1 = r1.zzlex;
            r2 = r2.array();
            r1.addCallbackBuffer(r2);
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.vision.CameraSource.zzb.run():void");
        }

        final void setActive(boolean z) {
            synchronized (this.mLock) {
                this.mActive = z;
                this.mLock.notifyAll();
            }
        }

        final void zza(byte[] bArr, Camera camera) {
            synchronized (this.mLock) {
                if (this.zzlfo != null) {
                    camera.addCallbackBuffer(this.zzlfo.array());
                    this.zzlfo = null;
                }
                if (this.zzlfl.zzlfi.containsKey(bArr)) {
                    this.zzlfm = SystemClock.elapsedRealtime() - this.zzebf;
                    this.zzlfn++;
                    this.zzlfo = (ByteBuffer) this.zzlfl.zzlfi.get(bArr);
                    this.mLock.notifyAll();
                    return;
                }
                Log.d("CameraSource", "Skipping frame. Could not find ByteBuffer associated with the image data from the camera.");
            }
        }
    }

    class zzc implements android.hardware.Camera.PictureCallback {
        private /* synthetic */ CameraSource zzlfl;
        private PictureCallback zzlfp;

        private zzc(CameraSource cameraSource) {
            this.zzlfl = cameraSource;
        }

        public final void onPictureTaken(byte[] bArr, Camera camera) {
            if (this.zzlfp != null) {
                this.zzlfp.onPictureTaken(bArr);
            }
            synchronized (this.zzlfl.zzlew) {
                if (this.zzlfl.zzlex != null) {
                    this.zzlfl.zzlex.startPreview();
                }
            }
        }
    }

    static class zzd implements android.hardware.Camera.ShutterCallback {
        private ShutterCallback zzlfq;

        private zzd() {
        }

        public final void onShutter() {
            if (this.zzlfq != null) {
                this.zzlfq.onShutter();
            }
        }
    }

    static class zze {
        private Size zzlfr;
        private Size zzlfs;

        public zze(Camera.Size size, Camera.Size size2) {
            this.zzlfr = new Size(size.width, size.height);
            if (size2 != null) {
                this.zzlfs = new Size(size2.width, size2.height);
            }
        }

        public final Size zzbli() {
            return this.zzlfr;
        }

        public final Size zzblj() {
            return this.zzlfs;
        }
    }

    private CameraSource() {
        this.zzlew = new Object();
        this.zzley = 0;
        this.zzlfa = 30.0f;
        this.zzlfb = 1024;
        this.zzlfc = 768;
        this.zzlfd = false;
        this.zzlfi = new HashMap();
    }

    private static zze zza(Camera camera, int i, int i2) {
        zze zze = null;
        Parameters parameters = camera.getParameters();
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        List<Camera.Size> supportedPictureSizes = parameters.getSupportedPictureSizes();
        List arrayList = new ArrayList();
        for (Camera.Size size : supportedPreviewSizes) {
            float f = ((float) size.width) / ((float) size.height);
            for (Camera.Size size2 : supportedPictureSizes) {
                if (Math.abs(f - (((float) size2.width) / ((float) size2.height))) < 0.01f) {
                    arrayList.add(new zze(size, size2));
                    break;
                }
            }
        }
        if (arrayList.size() == 0) {
            Log.w("CameraSource", "No preview sizes have a corresponding same-aspect-ratio picture size");
            for (Camera.Size size3 : supportedPreviewSizes) {
                arrayList.add(new zze(size3, null));
            }
        }
        int i3 = Integer.MAX_VALUE;
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size4 = arrayList2.size();
        int i4 = 0;
        while (i4 < size4) {
            zze zze2;
            int i5;
            int i6 = i4 + 1;
            zze zze3 = (zze) arrayList2.get(i4);
            Size zzbli = zze3.zzbli();
            i4 = Math.abs(zzbli.getHeight() - i2) + Math.abs(zzbli.getWidth() - i);
            if (i4 < i3) {
                int i7 = i4;
                zze2 = zze3;
                i5 = i7;
            } else {
                i5 = i3;
                zze2 = zze;
            }
            i3 = i5;
            zze = zze2;
            i4 = i6;
        }
        return zze;
    }

    @SuppressLint({"InlinedApi"})
    private final byte[] zza(Size size) {
        Object obj = new byte[(((int) Math.ceil(((double) ((long) (ImageFormat.getBitsPerPixel(17) * (size.getHeight() * size.getWidth())))) / 8.0d)) + 1)];
        ByteBuffer wrap = ByteBuffer.wrap(obj);
        if (wrap.hasArray() && wrap.array() == obj) {
            this.zzlfi.put(obj, wrap);
            return obj;
        }
        throw new IllegalStateException("Failed to create valid buffer for camera source.");
    }

    @SuppressLint({"InlinedApi"})
    private static int[] zza(Camera camera, float f) {
        int i = (int) (1000.0f * f);
        int[] iArr = null;
        int i2 = Integer.MAX_VALUE;
        for (int[] iArr2 : camera.getParameters().getSupportedPreviewFpsRange()) {
            int[] iArr3;
            int i3;
            int abs = Math.abs(i - iArr2[0]) + Math.abs(i - iArr2[1]);
            if (abs < i2) {
                int i4 = abs;
                iArr3 = iArr2;
                i3 = i4;
            } else {
                i3 = i2;
                iArr3 = iArr;
            }
            i2 = i3;
            iArr = iArr3;
        }
        return iArr;
    }

    @SuppressLint({"InlinedApi"})
    private final Camera zzblh() throws IOException {
        int i;
        int i2 = 0;
        int i3 = this.zzley;
        CameraInfo cameraInfo = new CameraInfo();
        for (i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == i3) {
                i3 = i;
                break;
            }
        }
        i3 = -1;
        if (i3 == -1) {
            throw new IOException("Could not find requested camera.");
        }
        Camera open = Camera.open(i3);
        zze zza = zza(open, this.zzlfb, this.zzlfc);
        if (zza == null) {
            throw new IOException("Could not find suitable preview size.");
        }
        Size zzblj = zza.zzblj();
        this.zzlez = zza.zzbli();
        int[] zza2 = zza(open, this.zzlfa);
        if (zza2 == null) {
            throw new IOException("Could not find suitable preview frames per second range.");
        }
        Parameters parameters = open.getParameters();
        if (zzblj != null) {
            parameters.setPictureSize(zzblj.getWidth(), zzblj.getHeight());
        }
        parameters.setPreviewSize(this.zzlez.getWidth(), this.zzlez.getHeight());
        parameters.setPreviewFpsRange(zza2[0], zza2[1]);
        parameters.setPreviewFormat(17);
        i = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        switch (i) {
            case 0:
                break;
            case 1:
                i2 = 90;
                break;
            case 2:
                i2 = 180;
                break;
            case 3:
                i2 = 270;
                break;
            default:
                Log.e("CameraSource", "Bad rotation value: " + i);
                break;
        }
        CameraInfo cameraInfo2 = new CameraInfo();
        Camera.getCameraInfo(i3, cameraInfo2);
        if (cameraInfo2.facing == 1) {
            i2 = (cameraInfo2.orientation + i2) % 360;
            i = (360 - i2) % 360;
        } else {
            i = ((cameraInfo2.orientation - i2) + 360) % 360;
            i2 = i;
        }
        this.zzcma = i2 / 90;
        open.setDisplayOrientation(i);
        parameters.setRotation(i2);
        if (this.zzlfd) {
            if (parameters.getSupportedFocusModes().contains("continuous-video")) {
                parameters.setFocusMode("continuous-video");
            } else {
                Log.i("CameraSource", "Camera auto focus is not supported on this device.");
            }
        }
        open.setParameters(parameters);
        open.setPreviewCallbackWithBuffer(new zza());
        open.addCallbackBuffer(zza(this.zzlez));
        open.addCallbackBuffer(zza(this.zzlez));
        open.addCallbackBuffer(zza(this.zzlez));
        open.addCallbackBuffer(zza(this.zzlez));
        return open;
    }

    public int getCameraFacing() {
        return this.zzley;
    }

    public Size getPreviewSize() {
        return this.zzlez;
    }

    public void release() {
        synchronized (this.zzlew) {
            stop();
            this.zzlfh.release();
        }
    }

    @RequiresPermission("android.permission.CAMERA")
    public CameraSource start() throws IOException {
        synchronized (this.zzlew) {
            if (this.zzlex != null) {
            } else {
                this.zzlex = zzblh();
                this.zzlfe = new SurfaceTexture(100);
                this.zzlex.setPreviewTexture(this.zzlfe);
                this.zzlff = true;
                this.zzlex.startPreview();
                this.zzlfg = new Thread(this.zzlfh);
                this.zzlfh.setActive(true);
                this.zzlfg.start();
            }
        }
        return this;
    }

    @RequiresPermission("android.permission.CAMERA")
    public CameraSource start(SurfaceHolder surfaceHolder) throws IOException {
        synchronized (this.zzlew) {
            if (this.zzlex != null) {
            } else {
                this.zzlex = zzblh();
                this.zzlex.setPreviewDisplay(surfaceHolder);
                this.zzlex.startPreview();
                this.zzlfg = new Thread(this.zzlfh);
                this.zzlfh.setActive(true);
                this.zzlfg.start();
                this.zzlff = false;
            }
        }
        return this;
    }

    public void stop() {
        synchronized (this.zzlew) {
            this.zzlfh.setActive(false);
            if (this.zzlfg != null) {
                try {
                    this.zzlfg.join();
                } catch (InterruptedException e) {
                    Log.d("CameraSource", "Frame processing thread interrupted on release.");
                }
                this.zzlfg = null;
            }
            if (this.zzlex != null) {
                this.zzlex.stopPreview();
                this.zzlex.setPreviewCallbackWithBuffer(null);
                try {
                    if (this.zzlff) {
                        this.zzlex.setPreviewTexture(null);
                    } else {
                        this.zzlex.setPreviewDisplay(null);
                    }
                } catch (Exception e2) {
                    String valueOf = String.valueOf(e2);
                    Log.e("CameraSource", new StringBuilder(String.valueOf(valueOf).length() + 32).append("Failed to clear camera preview: ").append(valueOf).toString());
                }
                this.zzlex.release();
                this.zzlex = null;
            }
            this.zzlfi.clear();
        }
    }

    public void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback) {
        synchronized (this.zzlew) {
            if (this.zzlex != null) {
                android.hardware.Camera.ShutterCallback zzd = new zzd();
                zzd.zzlfq = shutterCallback;
                android.hardware.Camera.PictureCallback zzc = new zzc();
                zzc.zzlfp = pictureCallback;
                this.zzlex.takePicture(zzd, null, null, zzc);
            }
        }
    }
}
