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
    /* renamed from: a */
    OnClickListener f8454a = new C25211(this);
    /* renamed from: b */
    boolean f8455b = false;
    /* renamed from: c */
    OnClickListener f8456c = new C25232(this);
    /* renamed from: d */
    private Camera f8457d;
    /* renamed from: e */
    private CameraPreview f8458e;
    /* renamed from: f */
    private MediaRecorder f8459f;
    /* renamed from: g */
    private Button f8460g;
    /* renamed from: h */
    private Button f8461h;
    /* renamed from: i */
    private Context f8462i;
    /* renamed from: j */
    private boolean f8463j = false;

    /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$1 */
    class C25211 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ VideoCaptureActivity f8451a;

        C25211(VideoCaptureActivity videoCaptureActivity) {
            this.f8451a = videoCaptureActivity;
        }

        public void onClick(View view) {
            if (!this.f8451a.f8455b) {
                if (Camera.getNumberOfCameras() > 1) {
                    this.f8451a.m12291g();
                    this.f8451a.m12293b();
                    return;
                }
                Toast.makeText(this.f8451a.f8462i, "Sorry, your phone has only one camera!", 1).show();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$2 */
    class C25232 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ VideoCaptureActivity f8453a;

        /* renamed from: org.telegram.customization.Activities.VideoCaptureActivity$2$1 */
        class C25221 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C25232 f8452a;

            C25221(C25232 c25232) {
                this.f8452a = c25232;
            }

            public void run() {
                try {
                    this.f8452a.f8453a.f8459f.start();
                } catch (Exception e) {
                }
            }
        }

        C25232(VideoCaptureActivity videoCaptureActivity) {
            this.f8453a = videoCaptureActivity;
        }

        public void onClick(View view) {
            if (this.f8453a.f8455b) {
                this.f8453a.f8459f.stop();
                this.f8453a.m12288e();
                Toast.makeText(this.f8453a, "Video captured!", 1).show();
                this.f8453a.f8455b = false;
                return;
            }
            if (!this.f8453a.m12290f()) {
                Toast.makeText(this.f8453a, "Fail in prepareMediaRecorder()!\n - Ended -", 1).show();
                this.f8453a.finish();
            }
            this.f8453a.runOnUiThread(new C25221(this));
            this.f8453a.f8455b = true;
        }
    }

    /* renamed from: a */
    private boolean m12282a(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    /* renamed from: c */
    private int m12284c() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 1) {
                this.f8463j = true;
                return i;
            }
        }
        return -1;
    }

    /* renamed from: d */
    private int m12286d() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo cameraInfo = new CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                this.f8463j = false;
                return i;
            }
        }
        return -1;
    }

    /* renamed from: e */
    private void m12288e() {
        if (this.f8459f != null) {
            this.f8459f.reset();
            this.f8459f.release();
            this.f8459f = null;
            this.f8457d.lock();
        }
    }

    /* renamed from: f */
    private boolean m12290f() {
        this.f8459f = new MediaRecorder();
        this.f8457d.unlock();
        this.f8459f.setCamera(this.f8457d);
        this.f8459f.setAudioSource(5);
        this.f8459f.setVideoSource(1);
        this.f8459f.setProfile(CamcorderProfile.get(5));
        this.f8459f.setOutputFile("/sdcard/myvideo.mp4");
        this.f8459f.setMaxDuration(600000);
        this.f8459f.setMaxFileSize(50000000);
        try {
            this.f8459f.prepare();
            return true;
        } catch (IllegalStateException e) {
            m12288e();
            return false;
        } catch (IOException e2) {
            m12288e();
            return false;
        }
    }

    /* renamed from: g */
    private void m12291g() {
        if (this.f8457d != null) {
            this.f8457d.release();
            this.f8457d = null;
        }
    }

    /* renamed from: a */
    public void m12292a() {
        this.f8458e = (CameraPreview) findViewById(R.id.camera_preview);
        this.f8458e.setCamera(this.f8457d);
        this.f8458e.setActivity(this);
        this.f8460g = (Button) findViewById(R.id.button_capture);
        this.f8460g.setOnClickListener(this.f8456c);
        this.f8461h = (Button) findViewById(R.id.button_ChangeCamera);
        this.f8461h.setOnClickListener(this.f8454a);
    }

    /* renamed from: b */
    public void m12293b() {
        int d;
        if (this.f8463j) {
            d = m12286d();
            if (d >= 0) {
                this.f8457d = Camera.open(d);
                this.f8458e.m13407a(this.f8457d);
                return;
            }
            return;
        }
        d = m12284c();
        if (d >= 0) {
            this.f8457d = Camera.open(d);
            this.f8458e.m13407a(this.f8457d);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_video_capture);
        getWindow().addFlags(128);
        this.f8462i = this;
        m12292a();
    }

    protected void onPause() {
        super.onPause();
        m12291g();
    }

    public void onResume() {
        super.onResume();
        if (!m12282a(this.f8462i)) {
            Toast.makeText(this.f8462i, "Sorry, your phone does not have a camera!", 1).show();
            finish();
        }
        if (this.f8457d == null) {
            if (m12284c() < 0) {
                Toast.makeText(this, "No front facing camera found.", 1).show();
                this.f8461h.setVisibility(8);
            }
            this.f8457d = Camera.open(m12284c());
            this.f8458e.m13407a(this.f8457d);
        }
    }
}
