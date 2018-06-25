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
    /* renamed from: a */
    Context f9514a;
    /* renamed from: b */
    private SurfaceHolder f9515b = getHolder();
    /* renamed from: c */
    private VideoCaptureActivity f9516c;
    /* renamed from: d */
    private Camera f9517d;
    /* renamed from: e */
    private Path f9518e;

    public CameraPreview(Context context) {
        super(context);
        this.f9515b.addCallback(this);
        this.f9514a = context;
        this.f9515b.setType(3);
        m13406a();
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.f9517d = camera;
        this.f9515b.addCallback(this);
        this.f9514a = context;
        this.f9515b.setType(3);
        m13406a();
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f9515b.addCallback(this);
        this.f9514a = context;
        this.f9515b.setType(3);
        m13406a();
    }

    public CameraPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f9515b.addCallback(this);
        this.f9514a = context;
        this.f9515b.setType(3);
        m13406a();
    }

    /* renamed from: a */
    private void m13406a() {
        this.f9518e = new Path();
        this.f9518e.addCircle(310.0f, 330.0f, 250.0f, Direction.CW);
    }

    /* renamed from: a */
    public void m13407a(Camera camera) {
        if (this.f9515b.getSurface() != null) {
            try {
                this.f9517d.stopPreview();
            } catch (Exception e) {
            }
            setCamera(camera);
            try {
                int i;
                CameraInfo cameraInfo = new CameraInfo();
                Camera.getCameraInfo(1, cameraInfo);
                int i2 = cameraInfo.orientation;
                Parameters parameters = camera.getParameters();
                List<Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
                Size size = null;
                float f = Float.MAX_VALUE;
                float height = ((float) getHeight()) / ((float) getWidth());
                for (Size size2 : supportedPreviewSizes) {
                    Size size3;
                    float f2;
                    float f3 = ((float) size2.width) / ((float) size2.height);
                    if (size == null || Math.abs(height - f3) < Math.abs(height - f)) {
                        size3 = size2;
                        f2 = f3;
                    } else {
                        f2 = f;
                        size3 = size;
                    }
                    size = size3;
                    f = f2;
                }
                switch (this.f9516c.getWindowManager().getDefaultDisplay().getRotation()) {
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
                this.f9517d.setDisplayOrientation((360 - ((i2 + i) % 360)) % 360);
                i = (i + (i2 + 360)) % 360;
                parameters.setPreviewSize(size.width, size.height);
                parameters.setRotation(i);
                camera.setParameters(parameters);
                this.f9517d.setPreviewDisplay(this.f9515b);
                this.f9517d.startPreview();
            } catch (Exception e2) {
                Log.d("View", "Error starting camera preview: " + e2.getMessage());
            }
        }
    }

    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(this.f9518e);
        super.dispatchDraw(canvas);
    }

    public Camera getmCamera() {
        return this.f9517d;
    }

    public void setActivity(VideoCaptureActivity videoCaptureActivity) {
        this.f9516c = videoCaptureActivity;
    }

    public void setCamera(Camera camera) {
        this.f9517d = camera;
    }

    public void setmCamera(Camera camera) {
        this.f9517d = camera;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        m13407a(this.f9517d);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        try {
            if (this.f9517d == null) {
                this.f9517d.setPreviewDisplay(surfaceHolder);
                this.f9517d.startPreview();
            }
        } catch (IOException e) {
            Log.d("View", "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
}
