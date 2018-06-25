package org.telegram.customization.util.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.ui.LaunchActivity;
import utils.view.VideoController.DensityUtil;
import utils.view.VideoController.LightnessController;
import utils.view.VideoController.VolumnController;

public class VideoActivity extends Activity implements OnClickListener {
    /* renamed from: A */
    private int f9564A;
    /* renamed from: B */
    private int f9565B;
    /* renamed from: C */
    private int f9566C;
    /* renamed from: D */
    private boolean f9567D = true;
    /* renamed from: E */
    private OnTouchListener f9568E = new C29047(this);
    /* renamed from: a */
    boolean f9569a = false;
    /* renamed from: b */
    ProgressBar f9570b;
    /* renamed from: c */
    LinearLayout f9571c;
    /* renamed from: d */
    ImageView f9572d;
    /* renamed from: e */
    private String f9573e;
    /* renamed from: f */
    private View f9574f;
    /* renamed from: g */
    private VideoView f9575g;
    /* renamed from: h */
    private View f9576h;
    /* renamed from: i */
    private View f9577i;
    /* renamed from: j */
    private SeekBar f9578j;
    /* renamed from: k */
    private ImageView f9579k;
    /* renamed from: l */
    private TextView f9580l;
    /* renamed from: m */
    private TextView f9581m;
    /* renamed from: n */
    private AudioManager f9582n;
    /* renamed from: o */
    private float f9583o;
    /* renamed from: p */
    private float f9584p;
    /* renamed from: q */
    private int f9585q;
    /* renamed from: r */
    private String f9586r = "http://hn2.asset.aparat.com/aparat-video/7dcaacabfc08704d721b5e585efe03f51978186__95263.apt?start=0";
    /* renamed from: s */
    private VolumnController f9587s;
    /* renamed from: t */
    private LightnessController f9588t;
    /* renamed from: u */
    private int f9589u;
    /* renamed from: v */
    private Runnable f9590v = new C28981(this);
    @SuppressLint({"HandlerLeak"})
    /* renamed from: w */
    private Handler f9591w = new C29025(this);
    /* renamed from: x */
    private OnSeekBarChangeListener f9592x = new C29036(this);
    /* renamed from: y */
    private float f9593y;
    /* renamed from: z */
    private float f9594z;

    /* renamed from: org.telegram.customization.util.view.VideoActivity$1 */
    class C28981 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9555a;

        C28981(VideoActivity videoActivity) {
            this.f9555a = videoActivity;
        }

        public void run() {
            this.f9555a.m13445e();
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$2 */
    class C28992 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9556a;

        C28992(VideoActivity videoActivity) {
            this.f9556a = videoActivity;
        }

        public void run() {
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$3 */
    class C29003 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9557a;

        C29003(VideoActivity videoActivity) {
            this.f9557a = videoActivity;
        }

        public void onAnimationEnd(Animation animation) {
            this.f9557a.f9576h.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$4 */
    class C29014 implements AnimationListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9558a;

        C29014(VideoActivity videoActivity) {
            this.f9558a = videoActivity;
        }

        public void onAnimationEnd(Animation animation) {
            this.f9558a.f9577i.setVisibility(8);
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationStart(Animation animation) {
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$5 */
    class C29025 extends Handler {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9559a;

        C29025(VideoActivity videoActivity) {
            this.f9559a = videoActivity;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    if (this.f9559a.f9575g.getCurrentPosition() > 0) {
                        this.f9559a.f9580l.setText(this.f9559a.m13426a((long) this.f9559a.f9575g.getCurrentPosition()));
                        this.f9559a.f9578j.setProgress((this.f9559a.f9575g.getCurrentPosition() * 100) / this.f9559a.f9575g.getDuration());
                        if (this.f9559a.f9575g.getCurrentPosition() > this.f9559a.f9575g.getDuration() - 100) {
                            this.f9559a.f9580l.setText("00:00");
                            this.f9559a.f9578j.setProgress(0);
                        }
                        this.f9559a.f9578j.setSecondaryProgress(this.f9559a.f9575g.getBufferPercentage());
                        return;
                    }
                    this.f9559a.f9580l.setText("00:00");
                    this.f9559a.f9578j.setProgress(0);
                    return;
                case 2:
                    this.f9559a.m13445e();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$6 */
    class C29036 implements OnSeekBarChangeListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9560a;

        C29036(VideoActivity videoActivity) {
            this.f9560a = videoActivity;
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                this.f9560a.f9575g.seekTo((this.f9560a.f9575g.getDuration() * i) / 100);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            this.f9560a.f9591w.removeCallbacks(this.f9560a.f9590v);
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            this.f9560a.f9591w.postDelayed(this.f9560a.f9590v, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$7 */
    class C29047 implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9561a;

        C29047(VideoActivity videoActivity) {
            this.f9561a = videoActivity;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean z = false;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (motionEvent.getAction()) {
                case 0:
                    this.f9561a.f9593y = x;
                    this.f9561a.f9594z = y;
                    this.f9561a.f9564A = (int) x;
                    this.f9561a.f9565B = (int) y;
                    break;
                case 1:
                    if (Math.abs(x - ((float) this.f9561a.f9564A)) > ((float) this.f9561a.f9566C) || Math.abs(y - ((float) this.f9561a.f9565B)) > ((float) this.f9561a.f9566C)) {
                        this.f9561a.f9567D = false;
                    }
                    this.f9561a.f9593y = BitmapDescriptorFactory.HUE_RED;
                    this.f9561a.f9594z = BitmapDescriptorFactory.HUE_RED;
                    this.f9561a.f9564A = 0;
                    if (this.f9561a.f9567D) {
                        this.f9561a.m13445e();
                    }
                    this.f9561a.f9567D = true;
                    break;
                case 2:
                    float g = x - this.f9561a.f9593y;
                    float h = y - this.f9561a.f9594z;
                    float abs = Math.abs(g);
                    float abs2 = Math.abs(h);
                    if (abs <= ((float) this.f9561a.f9566C) || abs2 <= ((float) this.f9561a.f9566C)) {
                        if (abs < ((float) this.f9561a.f9566C) && abs2 > ((float) this.f9561a.f9566C)) {
                            z = true;
                        } else if (abs > ((float) this.f9561a.f9566C) && abs2 < ((float) this.f9561a.f9566C)) {
                        }
                    } else if (abs < abs2) {
                        z = true;
                    }
                    if (z) {
                        if (x < this.f9561a.f9583o / 2.0f) {
                            if (h > BitmapDescriptorFactory.HUE_RED) {
                                this.f9561a.m13438c(abs2);
                            } else if (h < BitmapDescriptorFactory.HUE_RED) {
                                this.f9561a.m13442d(abs2);
                            }
                        } else if (h > BitmapDescriptorFactory.HUE_RED) {
                            this.f9561a.m13438c(abs2);
                        } else if (h < BitmapDescriptorFactory.HUE_RED) {
                            this.f9561a.m13442d(abs2);
                        }
                    } else if (g > BitmapDescriptorFactory.HUE_RED) {
                        this.f9561a.m13434b(abs);
                    } else if (g < BitmapDescriptorFactory.HUE_RED) {
                        this.f9561a.m13428a(abs);
                    }
                    this.f9561a.f9593y = x;
                    this.f9561a.f9594z = y;
                    break;
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$8 */
    class C29058 implements OnErrorListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9562a;

        C29058(VideoActivity videoActivity) {
            this.f9562a = videoActivity;
        }

        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            FileLog.m13725d("TELEGRAM playing error " + i + " - " + i2);
            switch (i) {
                case 100:
                    this.f9562a.m13437c();
                    break;
                default:
                    this.f9562a.f9572d.setVisibility(0);
                    this.f9562a.f9570b.setVisibility(8);
                    break;
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$9 */
    class C29069 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ VideoActivity f9563a;

        C29069(VideoActivity videoActivity) {
            this.f9563a = videoActivity;
        }

        public void onClick(View view) {
            this.f9563a.m13437c();
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: a */
    private String m13426a(long j) {
        try {
            String[] split = new SimpleDateFormat("mm:ss").format(new Date(j)).split(":");
            return (Integer.parseInt(split[0]) - 30) + ":" + split[1];
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    /* renamed from: a */
    private void m13428a(float f) {
        int currentPosition = this.f9575g.getCurrentPosition() - ((int) ((f / this.f9583o) * ((float) this.f9575g.getDuration())));
        this.f9575g.seekTo(currentPosition);
        this.f9578j.setProgress((currentPosition * 100) / this.f9575g.getDuration());
        this.f9580l.setText(m13426a((long) currentPosition));
    }

    /* renamed from: b */
    private void m13434b(float f) {
        int currentPosition = this.f9575g.getCurrentPosition() + ((int) ((f / this.f9583o) * ((float) this.f9575g.getDuration())));
        this.f9575g.seekTo(currentPosition);
        this.f9578j.setProgress((currentPosition * 100) / this.f9575g.getDuration());
        this.f9580l.setText(m13426a((long) currentPosition));
    }

    /* renamed from: b */
    private void m13435b(String str) {
        m13441d();
        try {
            this.f9574f.setVisibility(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.f9570b.setVisibility(0);
        this.f9575g.setVideoPath(str);
        this.f9575g.requestFocus();
        this.f9575g.setOnPreparedListener(new OnPreparedListener(this) {
            /* renamed from: a */
            final /* synthetic */ VideoActivity f9553a;

            /* renamed from: org.telegram.customization.util.view.VideoActivity$11$1 */
            class C28971 extends TimerTask {
                /* renamed from: a */
                final /* synthetic */ AnonymousClass11 f9552a;

                C28971(AnonymousClass11 anonymousClass11) {
                    this.f9552a = anonymousClass11;
                }

                public void run() {
                    this.f9552a.f9553a.f9591w.sendEmptyMessage(1);
                }
            }

            {
                this.f9553a = r1;
            }

            public void onPrepared(MediaPlayer mediaPlayer) {
                this.f9553a.f9574f.setVisibility(0);
                this.f9553a.m13441d();
                this.f9553a.f9575g.start();
                this.f9553a.f9570b.setVisibility(8);
                if (this.f9553a.f9585q != 0) {
                    this.f9553a.f9575g.seekTo(this.f9553a.f9585q);
                }
                this.f9553a.f9591w.removeCallbacks(this.f9553a.f9590v);
                this.f9553a.f9591w.postDelayed(this.f9553a.f9590v, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                this.f9553a.f9581m.setText(this.f9553a.m13426a((long) this.f9553a.f9575g.getDuration()));
                new Timer().schedule(new C28971(this), 0, 1000);
            }
        });
        this.f9575g.setOnCompletionListener(new OnCompletionListener(this) {
            /* renamed from: a */
            final /* synthetic */ VideoActivity f9554a;

            {
                this.f9554a = r1;
            }

            public void onCompletion(MediaPlayer mediaPlayer) {
                this.f9554a.m13441d();
                this.f9554a.f9575g.stopPlayback();
                mediaPlayer.release();
                this.f9554a.f9576h.setVisibility(0);
                this.f9554a.f9576h.clearAnimation();
                this.f9554a.f9576h.startAnimation(AnimationUtils.loadAnimation(this.f9554a.getApplicationContext(), R.anim.option_entry_from_top));
                this.f9554a.f9577i.setVisibility(0);
                this.f9554a.f9577i.clearAnimation();
                this.f9554a.f9577i.startAnimation(AnimationUtils.loadAnimation(this.f9554a.getApplicationContext(), R.anim.option_entry_from_bottom));
                this.f9554a.f9572d.setVisibility(0);
            }
        });
        this.f9575g.setOnTouchListener(this.f9568E);
        new Handler().postDelayed(new C28992(this), 200);
    }

    /* renamed from: c */
    private void m13437c() {
        this.f9572d.setVisibility(8);
        try {
            m13435b(m13465a());
        } catch (Exception e) {
            FileLog.m13725d("TELEGRAM " + e.getMessage());
        }
    }

    /* renamed from: c */
    private void m13438c(float f) {
        int streamMaxVolume = this.f9582n.getStreamMaxVolume(3);
        int max = Math.max(this.f9582n.getStreamVolume(3) - ((int) (((f / this.f9584p) * ((float) streamMaxVolume)) * 3.0f)), 0);
        this.f9582n.setStreamVolume(3, max, 0);
        this.f9587s.a((float) ((max * 100) / streamMaxVolume));
    }

    /* renamed from: d */
    private void m13441d() {
        try {
            if (this.f9575g.isPlaying()) {
                this.f9575g.pause();
                this.f9579k.setImageResource(R.drawable.video_btn_down);
                return;
            }
            this.f9575g.start();
            this.f9579k.setImageResource(R.drawable.video_btn_on);
        } catch (Exception e) {
            FileLog.m13725d("TELEGRAM" + e.getMessage());
        }
    }

    /* renamed from: d */
    private void m13442d(float f) {
        int streamMaxVolume = this.f9582n.getStreamMaxVolume(3);
        int min = Math.min(this.f9582n.getStreamVolume(3) + ((int) (((f / this.f9584p) * ((float) streamMaxVolume)) * 3.0f)), streamMaxVolume);
        this.f9582n.setStreamVolume(3, min, 0);
        this.f9587s.a((float) ((min * 100) / streamMaxVolume));
    }

    /* renamed from: e */
    private void m13445e() {
        if (this.f9576h.getVisibility() == 0) {
            this.f9576h.clearAnimation();
            Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.option_leave_from_top);
            loadAnimation.setAnimationListener(new C29003(this));
            this.f9576h.startAnimation(loadAnimation);
            this.f9577i.clearAnimation();
            loadAnimation = AnimationUtils.loadAnimation(this, R.anim.option_leave_from_bottom);
            loadAnimation.setAnimationListener(new C29014(this));
            this.f9577i.startAnimation(loadAnimation);
            return;
        }
        this.f9576h.setVisibility(0);
        this.f9576h.clearAnimation();
        this.f9576h.startAnimation(AnimationUtils.loadAnimation(this, R.anim.option_entry_from_top));
        this.f9577i.setVisibility(0);
        this.f9577i.clearAnimation();
        this.f9577i.startAnimation(AnimationUtils.loadAnimation(this, R.anim.option_entry_from_bottom));
        this.f9591w.removeCallbacks(this.f9590v);
        this.f9591w.postDelayed(this.f9590v, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    /* renamed from: a */
    public String m13465a() {
        return this.f9573e;
    }

    /* renamed from: a */
    public void m13466a(String str) {
        this.f9573e = str;
    }

    /* renamed from: b */
    protected void m13467b() {
        if (getResources().getConfiguration().orientation == 2) {
            Configuration configuration = new Configuration();
            configuration.orientation = 2;
            onConfigurationChanged(configuration);
        }
        this.f9587s = new VolumnController(this);
        this.f9588t = new LightnessController(this);
        this.f9575g = (VideoView) findViewById(R.id.videoview);
        this.f9580l = (TextView) findViewById(R.id.play_time);
        this.f9581m = (TextView) findViewById(R.id.total_time);
        this.f9579k = (ImageView) findViewById(R.id.play_btn);
        this.f9578j = (SeekBar) findViewById(R.id.seekbar);
        this.f9576h = findViewById(R.id.top_layout);
        this.f9577i = findViewById(R.id.bottom_layout);
        this.f9570b = (ProgressBar) findViewById(R.id.pbVideo);
        this.f9572d = (ImageView) findViewById(R.id.iv_rePlay);
        this.f9574f = findViewById(R.id.rootView);
        this.f9575g.setOnErrorListener(new C29058(this));
        this.f9582n = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.f9583o = DensityUtil.b(this);
        this.f9584p = DensityUtil.a(this);
        this.f9566C = DensityUtil.a(this, 18.0f);
        this.f9589u = LightnessController.a(this);
        this.f9579k.setOnClickListener(this);
        this.f9578j.setOnSeekBarChangeListener(this.f9592x);
        this.f9571c = (LinearLayout) findViewById(R.id.ll_retry);
        this.f9572d.setOnClickListener(new C29069(this));
    }

    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(1);
        } else if (getResources().getConfiguration().orientation == 1) {
            super.onBackPressed();
            if (this.f9569a) {
                startActivity(new Intent(getApplicationContext(), LaunchActivity.class));
            }
            finish();
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.play_btn) {
            m13441d();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        FileLog.m13725d("TELEGRAM onconfigurationChanged");
        findViewById(R.id.rl_video_container).post(new Runnable(this) {
            /* renamed from: a */
            final /* synthetic */ VideoActivity f9551a;

            {
                this.f9551a = r1;
            }

            public void run() {
                if (this.f9551a.getResources().getConfiguration().orientation == 2) {
                    this.f9551a.getWindow().clearFlags(2048);
                    this.f9551a.getWindow().setFlags(1024, 1024);
                    this.f9551a.f9584p = DensityUtil.b(this.f9551a);
                    this.f9551a.f9583o = DensityUtil.a(this.f9551a);
                    RelativeLayout relativeLayout = (RelativeLayout) this.f9551a.findViewById(R.id.rl_video_container);
                    LayoutParams layoutParams = (LayoutParams) relativeLayout.getLayoutParams();
                    layoutParams.weight = 100.0f;
                    relativeLayout.setLayoutParams(layoutParams);
                } else if (this.f9551a.getResources().getConfiguration().orientation == 1) {
                    this.f9551a.getWindow().clearFlags(1024);
                    this.f9551a.getWindow().setFlags(2048, 2048);
                    this.f9551a.f9575g.setLayoutParams(new LayoutParams(-1, -2));
                }
            }
        });
        super.onConfigurationChanged(configuration);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sls_video_activity);
        String stringExtra = getIntent().getStringExtra("EXTRA_VIDEO_URL");
        this.f9569a = getIntent().getBooleanExtra("IS_FROM_PUSH", false);
        if (TextUtils.isEmpty(stringExtra)) {
            finish();
            return;
        }
        m13467b();
        m13466a(stringExtra);
        m13437c();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.f9591w.removeMessages(0);
        this.f9591w.removeCallbacksAndMessages(null);
    }

    protected void onPause() {
        super.onPause();
        LightnessController.a(this, this.f9589u);
    }

    protected void onResume() {
        super.onResume();
    }
}
