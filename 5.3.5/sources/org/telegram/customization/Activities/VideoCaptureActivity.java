package org.telegram.customization.Activities;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import org.ir.talaeii.R;
import org.telegram.customization.util.view.CameraPreview;

public class VideoCaptureActivity extends Activity {
    private boolean cameraFront = false;
    OnClickListener captrureListener = new C11122();
    private Button capture;
    private Camera mCamera;
    private CameraPreview mPreview;
    private MediaRecorder mediaRecorder;
    private Context myContext;
    boolean recording = false;
    private Button switchCamera;
    OnClickListener switchCameraListener = new C11101();

    /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$1 */
    class C11101 implements OnClickListener {
        C11101() {
        }

        public void onClick(View v) {
            if (!VideoCaptureActivity.this.recording) {
                if (Camera.getNumberOfCameras() > 1) {
                    VideoCaptureActivity.this.releaseCamera();
                    VideoCaptureActivity.this.chooseCamera();
                    return;
                }
                Toast.makeText(VideoCaptureActivity.this.myContext, "Sorry, your phone has only one camera!", 1).show();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$2 */
    class C11122 implements OnClickListener {

        /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$2$1 */
        class C11111 implements Runnable {
            C11111() {
            }

            public void run() {
                try {
                    VideoCaptureActivity.this.mediaRecorder.start();
                } catch (Exception e) {
                }
            }
        }

        C11122() {
        }

        public void onClick(View v) {
            if (VideoCaptureActivity.this.recording) {
                VideoCaptureActivity.this.mediaRecorder.stop();
                VideoCaptureActivity.this.releaseMediaRecorder();
                Toast.makeText(VideoCaptureActivity.this, "Video captured!", 1).show();
                VideoCaptureActivity.this.recording = false;
                return;
            }
            if (!VideoCaptureActivity.this.prepareMediaRecorder()) {
                Toast.makeText(VideoCaptureActivity.this, "Fail in prepareMediaRecorder()!\n - Ended -", 1).show();
                VideoCaptureActivity.this.finish();
            }
            VideoCaptureActivity.this.runOnUiThread(new C11111());
            VideoCaptureActivity.this.recording = true;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_capture);
        getWindow().addFlags(128);
        this.myContext = this;
        initialize();
    }

    private int findFrontFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 1) {
                int cameraId = i;
                this.cameraFront = true;
                return cameraId;
            }
        }
        return -1;
    }

    private int findBackFacingCamera() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == 0) {
                int cameraId = i;
                this.cameraFront = false;
                return cameraId;
            }
        }
        return -1;
    }

    public void onResume() {
        super.onResume();
        if (!hasCamera(this.myContext)) {
            Toast.makeText(this.myContext, "Sorry, your phone does not have a camera!", 1).show();
            finish();
        }
        if (this.mCamera == null) {
            if (findFrontFacingCamera() < 0) {
                Toast.makeText(this, "No front facing camera found.", 1).show();
                this.switchCamera.setVisibility(8);
            }
            this.mCamera = Camera.open(findFrontFacingCamera());
            this.mPreview.refreshCamera(this.mCamera);
        }
    }

    public void initialize() {
        this.mPreview = (CameraPreview) findViewById(R.id.camera_preview);
        this.mPreview.setCamera(this.mCamera);
        this.mPreview.setActivity(this);
        this.capture = (Button) findViewById(R.id.button_capture);
        this.capture.setOnClickListener(this.captrureListener);
        this.switchCamera = (Button) findViewById(R.id.button_ChangeCamera);
        this.switchCamera.setOnClickListener(this.switchCameraListener);
    }

    public void chooseCamera() {
        int cameraId;
        if (this.cameraFront) {
            cameraId = findBackFacingCamera();
            if (cameraId >= 0) {
                this.mCamera = Camera.open(cameraId);
                this.mPreview.refreshCamera(this.mCamera);
                return;
            }
            return;
        }
        cameraId = findFrontFacingCamera();
        if (cameraId >= 0) {
            this.mCamera = Camera.open(cameraId);
            this.mPreview.refreshCamera(this.mCamera);
        }
    }

    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private boolean hasCamera(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            return true;
        }
        return false;
    }

    private void releaseMediaRecorder() {
        if (this.mediaRecorder != null) {
            this.mediaRecorder.reset();
            this.mediaRecorder.release();
            this.mediaRecorder = null;
            this.mCamera.lock();
        }
    }

    private boolean prepareMediaRecorder() {
        this.mediaRecorder = new MediaRecorder();
        this.mCamera.unlock();
        this.mediaRecorder.setCamera(this.mCamera);
        this.mediaRecorder.setAudioSource(5);
        this.mediaRecorder.setVideoSource(1);
        this.mediaRecorder.setProfile(CamcorderProfile.get(5));
        this.mediaRecorder.setOutputFile("/sdcard/myvideo.mp4");
        this.mediaRecorder.setMaxDuration(600000);
        this.mediaRecorder.setMaxFileSize(50000000);
        try {
            this.mediaRecorder.prepare();
            return true;
        } catch (IllegalStateException e) {
            releaseMediaRecorder();
            return false;
        } catch (IOException e2) {
            releaseMediaRecorder();
            return false;
        }
    }

    private void releaseCamera() {
        if (this.mCamera != null) {
            this.mCamera.release();
            this.mCamera = null;
        }
    }
}
