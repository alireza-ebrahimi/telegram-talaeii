package org.telegram.messenger.camera;

import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.view.OrientationEventListener;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;

public class CameraSession {
    public static final int ORIENTATION_HYSTERESIS = 5;
    private AutoFocusCallback autoFocusCallback = new C34421();
    protected CameraInfo cameraInfo;
    private String currentFlashMode = "off";
    private int currentOrientation;
    private int diffOrientation;
    private boolean initied;
    private boolean isVideo;
    private int jpegOrientation;
    private int lastDisplayOrientation = -1;
    private int lastOrientation = -1;
    private boolean meteringAreaSupported;
    private OrientationEventListener orientationEventListener;
    private final int pictureFormat;
    private final Size pictureSize;
    private final Size previewSize;
    private boolean sameTakePictureOrientation;

    /* renamed from: org.telegram.messenger.camera.CameraSession$1 */
    class C34421 implements AutoFocusCallback {
        C34421() {
        }

        public void onAutoFocus(boolean z, Camera camera) {
            if (!z) {
            }
        }
    }

    public CameraSession(CameraInfo cameraInfo, Size size, Size size2, int i) {
        this.previewSize = size;
        this.pictureSize = size2;
        this.pictureFormat = i;
        this.cameraInfo = cameraInfo;
        this.currentFlashMode = ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).getString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", "off");
        this.orientationEventListener = new OrientationEventListener(ApplicationLoader.applicationContext) {
            public void onOrientationChanged(int i) {
                if (CameraSession.this.orientationEventListener != null && CameraSession.this.initied && i != -1) {
                    CameraSession.this.jpegOrientation = CameraSession.this.roundOrientation(i, CameraSession.this.jpegOrientation);
                    int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
                    if (CameraSession.this.lastOrientation != CameraSession.this.jpegOrientation || rotation != CameraSession.this.lastDisplayOrientation) {
                        if (!CameraSession.this.isVideo) {
                            CameraSession.this.configurePhotoCamera();
                        }
                        CameraSession.this.lastDisplayOrientation = rotation;
                        CameraSession.this.lastOrientation = CameraSession.this.jpegOrientation;
                    }
                }
            }
        };
        if (this.orientationEventListener.canDetectOrientation()) {
            this.orientationEventListener.enable();
            return;
        }
        this.orientationEventListener.disable();
        this.orientationEventListener = null;
    }

    private int getDisplayOrientation(CameraInfo cameraInfo, boolean z) {
        int i;
        switch (((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation()) {
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
                i = 0;
                break;
        }
        if (cameraInfo.facing != 1) {
            return ((cameraInfo.orientation - i) + 360) % 360;
        }
        i = (360 - ((i + cameraInfo.orientation) % 360)) % 360;
        if (!z && i == 90) {
            i = 270;
        }
        return (!z && "Huawei".equals(Build.MANUFACTURER) && "angler".equals(Build.PRODUCT) && i == 270) ? 90 : i;
    }

    private int getHigh() {
        return ("LGE".equals(Build.MANUFACTURER) && "g3_tmo_us".equals(Build.PRODUCT)) ? 4 : 1;
    }

    private int roundOrientation(int i, int i2) {
        Object obj = 1;
        if (i2 != -1) {
            int abs = Math.abs(i - i2);
            if (Math.min(abs, 360 - abs) < 50) {
                obj = null;
            }
        }
        return obj != null ? (((i + 45) / 90) * 90) % 360 : i2;
    }

    public void checkFlashMode(String str) {
        if (!CameraController.getInstance().availableFlashModes.contains(this.currentFlashMode)) {
            this.currentFlashMode = str;
            configurePhotoCamera();
            ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).edit().putString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", str).commit();
        }
    }

    protected void configurePhotoCamera() {
        boolean z = true;
        Camera camera = this.cameraInfo.camera;
        if (camera != null) {
            Parameters parameters;
            int i;
            CameraInfo cameraInfo = new CameraInfo();
            Parameters parameters2 = null;
            try {
                parameters = camera.getParameters();
            } catch (Throwable e) {
                FileLog.m13728e(e);
                parameters = parameters2;
            } catch (Throwable th) {
                FileLog.m13728e(th);
                return;
            }
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), cameraInfo);
            int displayOrientation = getDisplayOrientation(cameraInfo, true);
            if ("samsung".equals(Build.MANUFACTURER) && "sf2wifixx".equals(Build.PRODUCT)) {
                i = 0;
            } else {
                switch (displayOrientation) {
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
                        i = 0;
                        break;
                }
                if (cameraInfo.orientation % 90 != 0) {
                    cameraInfo.orientation = 0;
                }
                i = cameraInfo.facing == 1 ? (360 - ((i + cameraInfo.orientation) % 360)) % 360 : ((cameraInfo.orientation - i) + 360) % 360;
            }
            this.currentOrientation = i;
            camera.setDisplayOrientation(i);
            if (parameters != null) {
                parameters.setPreviewSize(this.previewSize.getWidth(), this.previewSize.getHeight());
                parameters.setPictureSize(this.pictureSize.getWidth(), this.pictureSize.getHeight());
                parameters.setPictureFormat(this.pictureFormat);
                String str = "continuous-picture";
                if (parameters.getSupportedFocusModes().contains(str)) {
                    parameters.setFocusMode(str);
                }
                i = this.jpegOrientation != -1 ? cameraInfo.facing == 1 ? ((cameraInfo.orientation - this.jpegOrientation) + 360) % 360 : (cameraInfo.orientation + this.jpegOrientation) % 360 : 0;
                try {
                    parameters.setRotation(i);
                    if (cameraInfo.facing == 1) {
                        if ((360 - displayOrientation) % 360 != i) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    } else {
                        this.sameTakePictureOrientation = displayOrientation == i;
                    }
                } catch (Exception e2) {
                }
                parameters.setFlashMode(this.currentFlashMode);
                try {
                    camera.setParameters(parameters);
                } catch (Exception e3) {
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.meteringAreaSupported = true;
                }
            }
        }
    }

    protected void configureRecorder(int i, MediaRecorder mediaRecorder) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(this.cameraInfo.cameraId, cameraInfo);
        getDisplayOrientation(cameraInfo, false);
        int i2 = this.jpegOrientation != -1 ? cameraInfo.facing == 1 ? ((cameraInfo.orientation - this.jpegOrientation) + 360) % 360 : (cameraInfo.orientation + this.jpegOrientation) % 360 : 0;
        mediaRecorder.setOrientationHint(i2);
        i2 = getHigh();
        boolean hasProfile = CamcorderProfile.hasProfile(this.cameraInfo.cameraId, i2);
        boolean hasProfile2 = CamcorderProfile.hasProfile(this.cameraInfo.cameraId, 0);
        if (hasProfile && (i == 1 || !hasProfile2)) {
            mediaRecorder.setProfile(CamcorderProfile.get(this.cameraInfo.cameraId, i2));
        } else if (hasProfile2) {
            mediaRecorder.setProfile(CamcorderProfile.get(this.cameraInfo.cameraId, 0));
        } else {
            throw new IllegalStateException("cannot find valid CamcorderProfile");
        }
        this.isVideo = true;
    }

    protected void configureRoundCamera() {
        boolean z = true;
        this.isVideo = true;
        Camera camera = this.cameraInfo.camera;
        if (camera != null) {
            Parameters parameters;
            int i;
            CameraInfo cameraInfo = new CameraInfo();
            Parameters parameters2 = null;
            try {
                parameters = camera.getParameters();
            } catch (Throwable e) {
                FileLog.m13728e(e);
                parameters = parameters2;
            } catch (Throwable th) {
                FileLog.m13728e(th);
                return;
            }
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), cameraInfo);
            int displayOrientation = getDisplayOrientation(cameraInfo, true);
            if ("samsung".equals(Build.MANUFACTURER) && "sf2wifixx".equals(Build.PRODUCT)) {
                i = 0;
            } else {
                switch (displayOrientation) {
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
                        i = 0;
                        break;
                }
                if (cameraInfo.orientation % 90 != 0) {
                    cameraInfo.orientation = 0;
                }
                i = cameraInfo.facing == 1 ? (360 - ((i + cameraInfo.orientation) % 360)) % 360 : ((cameraInfo.orientation - i) + 360) % 360;
            }
            this.currentOrientation = i;
            camera.setDisplayOrientation(i);
            this.diffOrientation = this.currentOrientation - displayOrientation;
            if (parameters != null) {
                FileLog.m13726e("set preview size = " + this.previewSize.getWidth() + " " + this.previewSize.getHeight());
                parameters.setPreviewSize(this.previewSize.getWidth(), this.previewSize.getHeight());
                FileLog.m13726e("set picture size = " + this.pictureSize.getWidth() + " " + this.pictureSize.getHeight());
                parameters.setPictureSize(this.pictureSize.getWidth(), this.pictureSize.getHeight());
                parameters.setPictureFormat(this.pictureFormat);
                parameters.setRecordingHint(true);
                String str = "auto";
                if (parameters.getSupportedFocusModes().contains(str)) {
                    parameters.setFocusMode(str);
                }
                i = this.jpegOrientation != -1 ? cameraInfo.facing == 1 ? ((cameraInfo.orientation - this.jpegOrientation) + 360) % 360 : (cameraInfo.orientation + this.jpegOrientation) % 360 : 0;
                try {
                    parameters.setRotation(i);
                    if (cameraInfo.facing == 1) {
                        if ((360 - displayOrientation) % 360 != i) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    } else {
                        this.sameTakePictureOrientation = displayOrientation == i;
                    }
                } catch (Exception e2) {
                }
                parameters.setFlashMode(this.currentFlashMode);
                try {
                    camera.setParameters(parameters);
                } catch (Exception e3) {
                }
                if (parameters.getMaxNumMeteringAreas() > 0) {
                    this.meteringAreaSupported = true;
                }
            }
        }
    }

    public void destroy() {
        this.initied = false;
        if (this.orientationEventListener != null) {
            this.orientationEventListener.disable();
            this.orientationEventListener = null;
        }
    }

    protected void focusToRect(Rect rect, Rect rect2) {
        try {
            Camera camera = this.cameraInfo.camera;
            if (camera != null) {
                camera.cancelAutoFocus();
                Parameters parameters = null;
                try {
                    parameters = camera.getParameters();
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
                if (parameters != null) {
                    parameters.setFocusMode("auto");
                    List arrayList = new ArrayList();
                    arrayList.add(new Area(rect, 1000));
                    parameters.setFocusAreas(arrayList);
                    if (this.meteringAreaSupported) {
                        arrayList = new ArrayList();
                        arrayList.add(new Area(rect2, 1000));
                        parameters.setMeteringAreas(arrayList);
                    }
                    try {
                        camera.setParameters(parameters);
                        camera.autoFocus(this.autoFocusCallback);
                    } catch (Throwable e2) {
                        FileLog.m13728e(e2);
                    }
                }
            }
        } catch (Throwable e22) {
            FileLog.m13728e(e22);
        }
    }

    public String getCurrentFlashMode() {
        return this.currentFlashMode;
    }

    public int getCurrentOrientation() {
        return this.currentOrientation;
    }

    public int getDisplayOrientation() {
        try {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), cameraInfo);
            return getDisplayOrientation(cameraInfo, true);
        } catch (Throwable e) {
            FileLog.m13728e(e);
            return 0;
        }
    }

    public String getNextFlashMode() {
        ArrayList arrayList = CameraController.getInstance().availableFlashModes;
        int i = 0;
        while (i < arrayList.size()) {
            if (((String) arrayList.get(i)).equals(this.currentFlashMode)) {
                return i < arrayList.size() + -1 ? (String) arrayList.get(i + 1) : (String) arrayList.get(0);
            } else {
                i++;
            }
        }
        return this.currentFlashMode;
    }

    public int getWorldAngle() {
        return this.diffOrientation;
    }

    public boolean isInitied() {
        return this.initied;
    }

    public boolean isSameTakePictureOrientation() {
        return this.sameTakePictureOrientation;
    }

    public void setCurrentFlashMode(String str) {
        this.currentFlashMode = str;
        configurePhotoCamera();
        ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).edit().putString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", str).commit();
    }

    public void setInitied() {
        this.initied = true;
    }

    protected void stopVideoRecording() {
        this.isVideo = false;
        configurePhotoCamera();
    }
}
