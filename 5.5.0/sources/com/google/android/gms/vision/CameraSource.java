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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.WindowManager;
import com.google.android.gms.common.images.Size;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class CameraSource {
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_BACK = 0;
    @SuppressLint({"InlinedApi"})
    public static final int CAMERA_FACING_FRONT = 1;
    private Context mContext;
    private final Object zzd;
    @GuardedBy("mCameraLock")
    private Camera zze;
    private int zzf;
    private int zzg;
    private Size zzh;
    private float zzi;
    private int zzj;
    private int zzk;
    private boolean zzl;
    private SurfaceTexture zzm;
    private boolean zzn;
    private Thread zzo;
    private zzb zzp;
    private Map<byte[], ByteBuffer> zzq;

    public static class Builder {
        private final Detector<?> zzr;
        private CameraSource zzs = new CameraSource();

        public Builder(Context context, Detector<?> detector) {
            if (context == null) {
                throw new IllegalArgumentException("No context supplied.");
            } else if (detector == null) {
                throw new IllegalArgumentException("No detector supplied.");
            } else {
                this.zzr = detector;
                this.zzs.mContext = context;
            }
        }

        public CameraSource build() {
            CameraSource cameraSource = this.zzs;
            CameraSource cameraSource2 = this.zzs;
            cameraSource2.getClass();
            cameraSource.zzp = new zzb(cameraSource2, this.zzr);
            return this.zzs;
        }

        public Builder setAutoFocusEnabled(boolean z) {
            this.zzs.zzl = z;
            return this;
        }

        public Builder setFacing(int i) {
            if (i == 0 || i == 1) {
                this.zzs.zzf = i;
                return this;
            }
            throw new IllegalArgumentException("Invalid camera: " + i);
        }

        public Builder setRequestedFps(float f) {
            if (f <= BitmapDescriptorFactory.HUE_RED) {
                throw new IllegalArgumentException("Invalid fps: " + f);
            }
            this.zzs.zzi = f;
            return this;
        }

        public Builder setRequestedPreviewSize(int i, int i2) {
            if (i <= 0 || i > 1000000 || i2 <= 0 || i2 > 1000000) {
                throw new IllegalArgumentException("Invalid preview size: " + i + "x" + i2);
            }
            this.zzs.zzj = i;
            this.zzs.zzk = i2;
            return this;
        }
    }

    public interface PictureCallback {
        void onPictureTaken(byte[] bArr);
    }

    public interface ShutterCallback {
        void onShutter();
    }

    private class zza implements PreviewCallback {
        private final /* synthetic */ CameraSource zzt;

        private zza(CameraSource cameraSource) {
            this.zzt = cameraSource;
        }

        public final void onPreviewFrame(byte[] bArr, Camera camera) {
            this.zzt.zzp.zza(bArr, camera);
        }
    }

    private class zzb implements Runnable {
        private boolean mActive = true;
        private final Object mLock = new Object();
        private Detector<?> zzr;
        private final /* synthetic */ CameraSource zzt;
        private long zzu = SystemClock.elapsedRealtime();
        private long zzv;
        private int zzw = 0;
        private ByteBuffer zzx;

        zzb(CameraSource cameraSource, Detector<?> detector) {
            this.zzt = cameraSource;
            this.zzr = detector;
        }

        @SuppressLint({"Assert"})
        final void release() {
            this.zzr.release();
            this.zzr = null;
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
            r0 = r6.zzx;	 Catch:{ all -> 0x0023 }
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
            r2 = r6.zzx;	 Catch:{ all -> 0x0023 }
            r3 = r6.zzt;	 Catch:{ all -> 0x0023 }
            r3 = r3.zzh;	 Catch:{ all -> 0x0023 }
            r3 = r3.getWidth();	 Catch:{ all -> 0x0023 }
            r4 = r6.zzt;	 Catch:{ all -> 0x0023 }
            r4 = r4.zzh;	 Catch:{ all -> 0x0023 }
            r4 = r4.getHeight();	 Catch:{ all -> 0x0023 }
            r5 = 17;
            r0 = r0.setImageData(r2, r3, r4, r5);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzw;	 Catch:{ all -> 0x0023 }
            r0 = r0.setId(r2);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzv;	 Catch:{ all -> 0x0023 }
            r0 = r0.setTimestampMillis(r2);	 Catch:{ all -> 0x0023 }
            r2 = r6.zzt;	 Catch:{ all -> 0x0023 }
            r2 = r2.zzg;	 Catch:{ all -> 0x0023 }
            r0 = r0.setRotation(r2);	 Catch:{ all -> 0x0023 }
            r0 = r0.build();	 Catch:{ all -> 0x0023 }
            r2 = r6.zzx;	 Catch:{ all -> 0x0023 }
            r3 = 0;
            r6.zzx = r3;	 Catch:{ all -> 0x0023 }
            monitor-exit(r1);	 Catch:{ all -> 0x0023 }
            r1 = r6.zzr;	 Catch:{ Throwable -> 0x007a }
            r1.receiveFrame(r0);	 Catch:{ Throwable -> 0x007a }
            r0 = r6.zzt;
            r0 = r0.zze;
            r1 = r2.array();
            r0.addCallbackBuffer(r1);
            goto L_0x0000;
        L_0x007a:
            r0 = move-exception;
            r1 = "CameraSource";
            r3 = "Exception thrown from receiver.";
            android.util.Log.e(r1, r3, r0);	 Catch:{ all -> 0x0093 }
            r0 = r6.zzt;
            r0 = r0.zze;
            r1 = r2.array();
            r0.addCallbackBuffer(r1);
            goto L_0x0000;
        L_0x0093:
            r0 = move-exception;
            r1 = r6.zzt;
            r1 = r1.zze;
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
                if (this.zzx != null) {
                    camera.addCallbackBuffer(this.zzx.array());
                    this.zzx = null;
                }
                if (this.zzt.zzq.containsKey(bArr)) {
                    this.zzv = SystemClock.elapsedRealtime() - this.zzu;
                    this.zzw++;
                    this.zzx = (ByteBuffer) this.zzt.zzq.get(bArr);
                    this.mLock.notifyAll();
                    return;
                }
                Log.d("CameraSource", "Skipping frame. Could not find ByteBuffer associated with the image data from the camera.");
            }
        }
    }

    private class zzc implements android.hardware.Camera.PictureCallback {
        private final /* synthetic */ CameraSource zzt;
        private PictureCallback zzy;

        private zzc(CameraSource cameraSource) {
            this.zzt = cameraSource;
        }

        public final void onPictureTaken(byte[] bArr, Camera camera) {
            if (this.zzy != null) {
                this.zzy.onPictureTaken(bArr);
            }
            synchronized (this.zzt.zzd) {
                if (this.zzt.zze != null) {
                    this.zzt.zze.startPreview();
                }
            }
        }
    }

    private static class zzd implements android.hardware.Camera.ShutterCallback {
        private ShutterCallback zzz;

        private zzd() {
        }

        public final void onShutter() {
            if (this.zzz != null) {
                this.zzz.onShutter();
            }
        }
    }

    @VisibleForTesting
    static class zze {
        private Size zzaa;
        private Size zzab;

        public zze(Camera.Size size, @Nullable Camera.Size size2) {
            this.zzaa = new Size(size.width, size.height);
            if (size2 != null) {
                this.zzab = new Size(size2.width, size2.height);
            }
        }

        public final Size zzb() {
            return this.zzaa;
        }

        @Nullable
        public final Size zzc() {
            return this.zzab;
        }
    }

    private CameraSource() {
        this.zzd = new Object();
        this.zzf = 0;
        this.zzi = 30.0f;
        this.zzj = 1024;
        this.zzk = 768;
        this.zzl = false;
        this.zzq = new HashMap();
    }

    @SuppressLint({"InlinedApi"})
    private final Camera zza() {
        int i;
        int i2;
        int i3 = this.zzf;
        CameraInfo cameraInfo = new CameraInfo();
        for (i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == i3) {
                i2 = i;
                break;
            }
        }
        i2 = -1;
        if (i2 == -1) {
            throw new IOException("Could not find requested camera.");
        }
        Camera open = Camera.open(i2);
        int i4 = this.zzj;
        int i5 = this.zzk;
        Parameters parameters = open.getParameters();
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
        zze zze = null;
        int i6 = Integer.MAX_VALUE;
        ArrayList arrayList2 = (ArrayList) arrayList;
        int size4 = arrayList2.size();
        int i7 = 0;
        while (i7 < size4) {
            zze zze2;
            int i8 = i7 + 1;
            zze zze3 = (zze) arrayList2.get(i7);
            Size zzb = zze3.zzb();
            i7 = Math.abs(zzb.getHeight() - i5) + Math.abs(zzb.getWidth() - i4);
            if (i7 < i6) {
                int i9 = i7;
                zze2 = zze3;
                i3 = i9;
            } else {
                i3 = i6;
                zze2 = zze;
            }
            i6 = i3;
            zze = zze2;
            i7 = i8;
        }
        if (zze == null) {
            throw new IOException("Could not find suitable preview size.");
        }
        Size zzc = zze.zzc();
        this.zzh = zze.zzb();
        int i10 = (int) (this.zzi * 1000.0f);
        int[] iArr = null;
        i7 = Integer.MAX_VALUE;
        for (int[] iArr2 : open.getParameters().getSupportedPreviewFpsRange()) {
            int[] iArr3;
            i3 = Math.abs(i10 - iArr2[0]) + Math.abs(i10 - iArr2[1]);
            if (i3 < i7) {
                i9 = i3;
                iArr3 = iArr2;
                i = i9;
            } else {
                i = i7;
                iArr3 = iArr;
            }
            i7 = i;
            iArr = iArr3;
        }
        if (iArr == null) {
            throw new IOException("Could not find suitable preview frames per second range.");
        }
        Parameters parameters2 = open.getParameters();
        if (zzc != null) {
            parameters2.setPictureSize(zzc.getWidth(), zzc.getHeight());
        }
        parameters2.setPreviewSize(this.zzh.getWidth(), this.zzh.getHeight());
        parameters2.setPreviewFpsRange(iArr[0], iArr[1]);
        parameters2.setPreviewFormat(17);
        i = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
        switch (i) {
            case 0:
                i = 0;
                break;
            case 1:
                i = 90;
                break;
            case 2:
                i = 180;
                break;
            case 3:
                i = 270;
                break;
            default:
                Log.e("CameraSource", "Bad rotation value: " + i);
                i = 0;
                break;
        }
        CameraInfo cameraInfo2 = new CameraInfo();
        Camera.getCameraInfo(i2, cameraInfo2);
        if (cameraInfo2.facing == 1) {
            i3 = (i + cameraInfo2.orientation) % 360;
            i = (360 - i3) % 360;
        } else {
            i = ((cameraInfo2.orientation - i) + 360) % 360;
            i3 = i;
        }
        this.zzg = i3 / 90;
        open.setDisplayOrientation(i);
        parameters2.setRotation(i3);
        if (this.zzl) {
            if (parameters2.getSupportedFocusModes().contains("continuous-video")) {
                parameters2.setFocusMode("continuous-video");
            } else {
                Log.i("CameraSource", "Camera auto focus is not supported on this device.");
            }
        }
        open.setParameters(parameters2);
        open.setPreviewCallbackWithBuffer(new zza());
        open.addCallbackBuffer(zza(this.zzh));
        open.addCallbackBuffer(zza(this.zzh));
        open.addCallbackBuffer(zza(this.zzh));
        open.addCallbackBuffer(zza(this.zzh));
        return open;
    }

    @SuppressLint({"InlinedApi"})
    private final byte[] zza(Size size) {
        Object obj = new byte[(((int) Math.ceil(((double) ((long) (ImageFormat.getBitsPerPixel(17) * (size.getHeight() * size.getWidth())))) / 8.0d)) + 1)];
        ByteBuffer wrap = ByteBuffer.wrap(obj);
        if (wrap.hasArray() && wrap.array() == obj) {
            this.zzq.put(obj, wrap);
            return obj;
        }
        throw new IllegalStateException("Failed to create valid buffer for camera source.");
    }

    public int getCameraFacing() {
        return this.zzf;
    }

    public Size getPreviewSize() {
        return this.zzh;
    }

    public void release() {
        synchronized (this.zzd) {
            stop();
            this.zzp.release();
        }
    }

    public CameraSource start() {
        synchronized (this.zzd) {
            if (this.zze != null) {
            } else {
                this.zze = zza();
                this.zzm = new SurfaceTexture(100);
                this.zze.setPreviewTexture(this.zzm);
                this.zzn = true;
                this.zze.startPreview();
                this.zzo = new Thread(this.zzp);
                this.zzp.setActive(true);
                this.zzo.start();
            }
        }
        return this;
    }

    public CameraSource start(SurfaceHolder surfaceHolder) {
        synchronized (this.zzd) {
            if (this.zze != null) {
            } else {
                this.zze = zza();
                this.zze.setPreviewDisplay(surfaceHolder);
                this.zze.startPreview();
                this.zzo = new Thread(this.zzp);
                this.zzp.setActive(true);
                this.zzo.start();
                this.zzn = false;
            }
        }
        return this;
    }

    public void stop() {
        synchronized (this.zzd) {
            this.zzp.setActive(false);
            if (this.zzo != null) {
                try {
                    this.zzo.join();
                } catch (InterruptedException e) {
                    Log.d("CameraSource", "Frame processing thread interrupted on release.");
                }
                this.zzo = null;
            }
            if (this.zze != null) {
                this.zze.stopPreview();
                this.zze.setPreviewCallbackWithBuffer(null);
                try {
                    if (this.zzn) {
                        this.zze.setPreviewTexture(null);
                    } else {
                        this.zze.setPreviewDisplay(null);
                    }
                } catch (Exception e2) {
                    String valueOf = String.valueOf(e2);
                    Log.e("CameraSource", new StringBuilder(String.valueOf(valueOf).length() + 32).append("Failed to clear camera preview: ").append(valueOf).toString());
                }
                this.zze.release();
                this.zze = null;
            }
            this.zzq.clear();
        }
    }

    public void takePicture(ShutterCallback shutterCallback, PictureCallback pictureCallback) {
        synchronized (this.zzd) {
            if (this.zze != null) {
                android.hardware.Camera.ShutterCallback zzd = new zzd();
                zzd.zzz = shutterCallback;
                android.hardware.Camera.PictureCallback zzc = new zzc();
                zzc.zzy = pictureCallback;
                this.zze.takePicture(zzd, null, null, zzc);
            }
        }
    }
}
