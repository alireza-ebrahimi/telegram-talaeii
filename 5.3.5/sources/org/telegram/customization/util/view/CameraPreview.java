package org.telegram.customization.util.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.io.IOException;
import java.util.List;
import org.telegram.customization.Activities.VideoCaptureActivity;

public class CameraPreview extends SurfaceView implements Callback {
    private VideoCaptureActivity activity;
    private Path clipPath;
    Context context;
    private Camera mCamera;
    private SurfaceHolder mHolder = getHolder();

    public Camera getmCamera() {
        return this.mCamera;
    }

    public void setmCamera(Camera mCamera) {
        this.mCamera = mCamera;
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;
        this.mHolder.addCallback(this);
        this.context = context;
        this.mHolder.setType(3);
        init();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            if (this.mCamera == null) {
                this.mCamera.setPreviewDisplay(holder);
                this.mCamera.startPreview();
            }
        } catch (IOException e) {
            Log.d("View", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void refreshCamera(Camera camera) {
        if (this.mHolder.getSurface() != null) {
            try {
                this.mCamera.stopPreview();
            } catch (Exception e) {
            }
            setCamera(camera);
            try {
                CameraInfo camInfo = new CameraInfo();
                Camera.getCameraInfo(1, camInfo);
                int cameraRotationOffset = camInfo.orientation;
                Parameters parameters = camera.getParameters();
                List<Size> previewSizes = parameters.getSupportedPreviewSizes();
                Size previewSize = null;
                float closestRatio = Float.MAX_VALUE;
                float targetRatio = ((float) getHeight()) / ((float) getWidth());
                for (Size candidateSize : previewSizes) {
                    float whRatio = ((float) candidateSize.width) / ((float) candidateSize.height);
                    if (previewSize == null || Math.abs(targetRatio - whRatio) < Math.abs(targetRatio - closestRatio)) {
                        closestRatio = whRatio;
                        previewSize = candidateSize;
                    }
                }
                int degrees = 0;
                switch (this.activity.getWindowManager().getDefaultDisplay().getRotation()) {
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
                this.mCamera.setDisplayOrientation((360 - ((cameraRotationOffset + degrees) % 360)) % 360);
                int rotate = ((cameraRotationOffset + 360) + degrees) % 360;
                parameters.setPreviewSize(previewSize.width, previewSize.height);
                parameters.setRotation(rotate);
                camera.setParameters(parameters);
                this.mCamera.setPreviewDisplay(this.mHolder);
                this.mCamera.startPreview();
            } catch (Exception e2) {
                Log.d("View", "Error starting camera preview: " + e2.getMessage());
            }
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        refreshCamera(this.mCamera);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public CameraPreview(Context context) {
        super(context);
        this.mHolder.addCallback(this);
        this.context = context;
        this.mHolder.setType(3);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mHolder.addCallback(this);
        this.context = context;
        this.mHolder.setType(3);
        init();
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mHolder.addCallback(this);
        this.context = context;
        this.mHolder.setType(3);
        init();
    }

    private void init() {
        this.clipPath = new Path();
        this.clipPath.addCircle(310.0f, 330.0f, 250.0f, Direction.CW);
    }

    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(this.clipPath);
        super.dispatchDraw(canvas);
    }

    public void setActivity(VideoCaptureActivity activity) {
        this.activity = activity;
    }
}
