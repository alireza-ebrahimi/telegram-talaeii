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
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;

public class CameraSession {
    public static final int ORIENTATION_HYSTERESIS = 5;
    private AutoFocusCallback autoFocusCallback = new C16931();
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
    class C16931 implements AutoFocusCallback {
        C16931() {
        }

        public void onAutoFocus(boolean success, Camera camera) {
            if (!success) {
            }
        }
    }

    public CameraSession(CameraInfo info, Size preview, Size picture, int format) {
        this.previewSize = preview;
        this.pictureSize = picture;
        this.pictureFormat = format;
        this.cameraInfo = info;
        this.currentFlashMode = ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).getString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", "off");
        this.orientationEventListener = new OrientationEventListener(ApplicationLoader.applicationContext) {
            public void onOrientationChanged(int orientation) {
                if (CameraSession.this.orientationEventListener != null && CameraSession.this.initied && orientation != -1) {
                    CameraSession.this.jpegOrientation = CameraSession.this.roundOrientation(orientation, CameraSession.this.jpegOrientation);
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

    private int roundOrientation(int orientation, int orientationHistory) {
        boolean changeOrientation;
        if (orientationHistory == -1) {
            changeOrientation = true;
        } else {
            int dist = Math.abs(orientation - orientationHistory);
            changeOrientation = Math.min(dist, 360 - dist) >= 50;
        }
        if (changeOrientation) {
            return (((orientation + 45) / 90) * 90) % 360;
        }
        return orientationHistory;
    }

    public void checkFlashMode(String mode) {
        if (!CameraController.getInstance().availableFlashModes.contains(this.currentFlashMode)) {
            this.currentFlashMode = mode;
            configurePhotoCamera();
            ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).edit().putString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", mode).commit();
        }
    }

    public void setCurrentFlashMode(String mode) {
        this.currentFlashMode = mode;
        configurePhotoCamera();
        ApplicationLoader.applicationContext.getSharedPreferences("camera", 0).edit().putString(this.cameraInfo.frontCamera != 0 ? "flashMode_front" : "flashMode", mode).commit();
    }

    public String getCurrentFlashMode() {
        return this.currentFlashMode;
    }

    public String getNextFlashMode() {
        ArrayList<String> modes = CameraController.getInstance().availableFlashModes;
        int a = 0;
        while (a < modes.size()) {
            if (!((String) modes.get(a)).equals(this.currentFlashMode)) {
                a++;
            } else if (a < modes.size() - 1) {
                return (String) modes.get(a + 1);
            } else {
                return (String) modes.get(0);
            }
        }
        return this.currentFlashMode;
    }

    public void setInitied() {
        this.initied = true;
    }

    public boolean isInitied() {
        return this.initied;
    }

    public int getCurrentOrientation() {
        return this.currentOrientation;
    }

    public int getWorldAngle() {
        return this.diffOrientation;
    }

    public boolean isSameTakePictureOrientation() {
        return this.sameTakePictureOrientation;
    }

    protected void configureRoundCamera() {
        boolean z = true;
        this.isVideo = true;
        Camera camera = this.cameraInfo.camera;
        if (camera != null) {
            int cameraDisplayOrientation;
            CameraInfo info = new CameraInfo();
            Parameters params = null;
            try {
                params = camera.getParameters();
            } catch (Exception e) {
                FileLog.e(e);
            } catch (Throwable e2) {
                FileLog.e(e2);
                return;
            }
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), info);
            int displayOrientation = getDisplayOrientation(info, true);
            if ("samsung".equals(Build.MANUFACTURER) && "sf2wifixx".equals(Build.PRODUCT)) {
                cameraDisplayOrientation = 0;
            } else {
                int temp;
                int degrees = 0;
                switch (displayOrientation) {
                    case 0:
                        degrees = 0;
                        break;
                    case 1:
                        degrees = 90;
                        break;
                    case 2:
                        degrees = 180;
                        break;
                    case 3:
                        degrees = 270;
                        break;
                }
                if (info.orientation % 90 != 0) {
                    info.orientation = 0;
                }
                if (info.facing == 1) {
                    temp = (360 - ((info.orientation + degrees) % 360)) % 360;
                } else {
                    temp = ((info.orientation - degrees) + 360) % 360;
                }
                cameraDisplayOrientation = temp;
            }
            this.currentOrientation = cameraDisplayOrientation;
            camera.setDisplayOrientation(cameraDisplayOrientation);
            this.diffOrientation = this.currentOrientation - displayOrientation;
            if (params != null) {
                FileLog.e("set preview size = " + this.previewSize.getWidth() + " " + this.previewSize.getHeight());
                params.setPreviewSize(this.previewSize.getWidth(), this.previewSize.getHeight());
                FileLog.e("set picture size = " + this.pictureSize.getWidth() + " " + this.pictureSize.getHeight());
                params.setPictureSize(this.pictureSize.getWidth(), this.pictureSize.getHeight());
                params.setPictureFormat(this.pictureFormat);
                params.setRecordingHint(true);
                String desiredMode = "auto";
                if (params.getSupportedFocusModes().contains(desiredMode)) {
                    params.setFocusMode(desiredMode);
                }
                int outputOrientation = 0;
                if (this.jpegOrientation != -1) {
                    if (info.facing == 1) {
                        outputOrientation = ((info.orientation - this.jpegOrientation) + 360) % 360;
                    } else {
                        outputOrientation = (info.orientation + this.jpegOrientation) % 360;
                    }
                }
                try {
                    params.setRotation(outputOrientation);
                    if (info.facing == 1) {
                        if ((360 - displayOrientation) % 360 != outputOrientation) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    } else {
                        if (displayOrientation != outputOrientation) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    }
                } catch (Exception e3) {
                }
                params.setFlashMode(this.currentFlashMode);
                try {
                    camera.setParameters(params);
                } catch (Exception e4) {
                }
                if (params.getMaxNumMeteringAreas() > 0) {
                    this.meteringAreaSupported = true;
                }
            }
        }
    }

    protected void configurePhotoCamera() {
        boolean z = true;
        Camera camera = this.cameraInfo.camera;
        if (camera != null) {
            int cameraDisplayOrientation;
            CameraInfo info = new CameraInfo();
            Parameters params = null;
            try {
                params = camera.getParameters();
            } catch (Exception e) {
                FileLog.e(e);
            } catch (Throwable e2) {
                FileLog.e(e2);
                return;
            }
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), info);
            int displayOrientation = getDisplayOrientation(info, true);
            if ("samsung".equals(Build.MANUFACTURER) && "sf2wifixx".equals(Build.PRODUCT)) {
                cameraDisplayOrientation = 0;
            } else {
                int temp;
                int degrees = 0;
                switch (displayOrientation) {
                    case 0:
                        degrees = 0;
                        break;
                    case 1:
                        degrees = 90;
                        break;
                    case 2:
                        degrees = 180;
                        break;
                    case 3:
                        degrees = 270;
                        break;
                }
                if (info.orientation % 90 != 0) {
                    info.orientation = 0;
                }
                if (info.facing == 1) {
                    temp = (360 - ((info.orientation + degrees) % 360)) % 360;
                } else {
                    temp = ((info.orientation - degrees) + 360) % 360;
                }
                cameraDisplayOrientation = temp;
            }
            this.currentOrientation = cameraDisplayOrientation;
            camera.setDisplayOrientation(cameraDisplayOrientation);
            if (params != null) {
                params.setPreviewSize(this.previewSize.getWidth(), this.previewSize.getHeight());
                params.setPictureSize(this.pictureSize.getWidth(), this.pictureSize.getHeight());
                params.setPictureFormat(this.pictureFormat);
                String desiredMode = "continuous-picture";
                if (params.getSupportedFocusModes().contains(desiredMode)) {
                    params.setFocusMode(desiredMode);
                }
                int outputOrientation = 0;
                if (this.jpegOrientation != -1) {
                    if (info.facing == 1) {
                        outputOrientation = ((info.orientation - this.jpegOrientation) + 360) % 360;
                    } else {
                        outputOrientation = (info.orientation + this.jpegOrientation) % 360;
                    }
                }
                try {
                    params.setRotation(outputOrientation);
                    if (info.facing == 1) {
                        if ((360 - displayOrientation) % 360 != outputOrientation) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    } else {
                        if (displayOrientation != outputOrientation) {
                            z = false;
                        }
                        this.sameTakePictureOrientation = z;
                    }
                } catch (Exception e3) {
                }
                params.setFlashMode(this.currentFlashMode);
                try {
                    camera.setParameters(params);
                } catch (Exception e4) {
                }
                if (params.getMaxNumMeteringAreas() > 0) {
                    this.meteringAreaSupported = true;
                }
            }
        }
    }

    protected void focusToRect(Rect focusRect, Rect meteringRect) {
        try {
            Camera camera = this.cameraInfo.camera;
            if (camera != null) {
                camera.cancelAutoFocus();
                Parameters parameters = null;
                try {
                    parameters = camera.getParameters();
                } catch (Exception e) {
                    FileLog.e(e);
                }
                if (parameters != null) {
                    parameters.setFocusMode("auto");
                    ArrayList<Area> meteringAreas = new ArrayList();
                    meteringAreas.add(new Area(focusRect, 1000));
                    parameters.setFocusAreas(meteringAreas);
                    if (this.meteringAreaSupported) {
                        meteringAreas = new ArrayList();
                        meteringAreas.add(new Area(meteringRect, 1000));
                        parameters.setMeteringAreas(meteringAreas);
                    }
                    try {
                        camera.setParameters(parameters);
                        camera.autoFocus(this.autoFocusCallback);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                }
            }
        } catch (Exception e22) {
            FileLog.e(e22);
        }
    }

    protected void configureRecorder(int quality, MediaRecorder recorder) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(this.cameraInfo.cameraId, info);
        int displayOrientation = getDisplayOrientation(info, false);
        int outputOrientation = 0;
        if (this.jpegOrientation != -1) {
            if (info.facing == 1) {
                outputOrientation = ((info.orientation - this.jpegOrientation) + 360) % 360;
            } else {
                outputOrientation = (info.orientation + this.jpegOrientation) % 360;
            }
        }
        recorder.setOrientationHint(outputOrientation);
        int highProfile = getHigh();
        boolean canGoHigh = CamcorderProfile.hasProfile(this.cameraInfo.cameraId, highProfile);
        boolean canGoLow = CamcorderProfile.hasProfile(this.cameraInfo.cameraId, 0);
        if (canGoHigh && (quality == 1 || !canGoLow)) {
            recorder.setProfile(CamcorderProfile.get(this.cameraInfo.cameraId, highProfile));
        } else if (canGoLow) {
            recorder.setProfile(CamcorderProfile.get(this.cameraInfo.cameraId, 0));
        } else {
            throw new IllegalStateException("cannot find valid CamcorderProfile");
        }
        this.isVideo = true;
    }

    protected void stopVideoRecording() {
        this.isVideo = false;
        configurePhotoCamera();
    }

    private int getHigh() {
        if ("LGE".equals(Build.MANUFACTURER) && "g3_tmo_us".equals(Build.PRODUCT)) {
            return 4;
        }
        return 1;
    }

    private int getDisplayOrientation(CameraInfo info, boolean isStillCapture) {
        int degrees = 0;
        switch (((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = 270;
                break;
        }
        if (info.facing != 1) {
            return ((info.orientation - degrees) + 360) % 360;
        }
        int displayOrientation = (360 - ((info.orientation + degrees) % 360)) % 360;
        if (!isStillCapture && displayOrientation == 90) {
            displayOrientation = 270;
        }
        if (!isStillCapture && "Huawei".equals(Build.MANUFACTURER) && "angler".equals(Build.PRODUCT) && displayOrientation == 270) {
            return 90;
        }
        return displayOrientation;
    }

    public int getDisplayOrientation() {
        try {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(this.cameraInfo.getCameraId(), info);
            return getDisplayOrientation(info, true);
        } catch (Exception e) {
            FileLog.e(e);
            return 0;
        }
    }

    public void destroy() {
        this.initied = false;
        if (this.orientationEventListener != null) {
            this.orientationEventListener.disable();
            this.orientationEventListener = null;
        }
    }
}
