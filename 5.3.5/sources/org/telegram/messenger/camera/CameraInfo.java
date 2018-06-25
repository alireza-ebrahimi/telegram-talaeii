package org.telegram.messenger.camera;

import android.hardware.Camera;
import java.util.ArrayList;

public class CameraInfo {
    protected Camera camera;
    protected int cameraId;
    protected final int frontCamera;
    protected ArrayList<Size> pictureSizes = new ArrayList();
    protected ArrayList<Size> previewSizes = new ArrayList();

    public CameraInfo(int id, android.hardware.Camera.CameraInfo info) {
        this.cameraId = id;
        this.frontCamera = info.facing;
    }

    public int getCameraId() {
        return this.cameraId;
    }

    private Camera getCamera() {
        return this.camera;
    }

    public ArrayList<Size> getPreviewSizes() {
        return this.previewSizes;
    }

    public ArrayList<Size> getPictureSizes() {
        return this.pictureSizes;
    }

    public boolean isFrontface() {
        return this.frontCamera != 0;
    }
}
