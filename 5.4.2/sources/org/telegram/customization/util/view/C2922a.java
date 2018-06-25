package org.telegram.customization.util.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.C0235a;
import android.support.v4.view.ViewPager.C0188f;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.VideoView;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.util.C2868b;
import org.telegram.customization.util.C2872c;
import org.telegram.customization.util.C2879f;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.ui.LaunchActivity;
import utils.C3792d;
import utils.view.VideoController.DensityUtil;
import utils.view.VideoController.LightnessController;
import utils.view.VideoController.VolumnController;

/* renamed from: org.telegram.customization.util.view.a */
public class C2922a extends LinearLayout implements C0188f, OnClickListener {
    /* renamed from: A */
    private TextView f9614A;
    /* renamed from: B */
    private TextView f9615B;
    /* renamed from: C */
    private int f9616C;
    /* renamed from: D */
    private boolean f9617D = true;
    /* renamed from: E */
    private boolean f9618E = true;
    /* renamed from: F */
    private VolumnController f9619F;
    /* renamed from: G */
    private LightnessController f9620G;
    /* renamed from: H */
    private AudioManager f9621H;
    /* renamed from: I */
    private float f9622I;
    /* renamed from: J */
    private float f9623J;
    /* renamed from: K */
    private OnSeekBarChangeListener f9624K = new C29071(this);
    /* renamed from: L */
    private Runnable f9625L = new C29082(this);
    @SuppressLint({"HandlerLeak"})
    /* renamed from: M */
    private Handler f9626M = new C29093(this);
    /* renamed from: N */
    private float f9627N;
    /* renamed from: O */
    private float f9628O;
    /* renamed from: P */
    private int f9629P;
    /* renamed from: Q */
    private int f9630Q;
    /* renamed from: R */
    private int f9631R;
    /* renamed from: S */
    private boolean f9632S = true;
    /* renamed from: T */
    private OnTouchListener f9633T = new C29157(this);
    /* renamed from: a */
    View f9634a;
    /* renamed from: b */
    Context f9635b;
    /* renamed from: c */
    Activity f9636c;
    /* renamed from: d */
    LayoutInflater f9637d;
    /* renamed from: e */
    SlsBaseMessage f9638e;
    /* renamed from: f */
    ImageView f9639f;
    /* renamed from: g */
    ImageView f9640g;
    /* renamed from: h */
    ImageView f9641h;
    /* renamed from: i */
    ImageView f9642i;
    /* renamed from: j */
    TextView f9643j;
    /* renamed from: k */
    TextView f9644k;
    /* renamed from: l */
    TextView f9645l;
    /* renamed from: m */
    VideoView f9646m;
    /* renamed from: n */
    TextView f9647n;
    /* renamed from: o */
    View f9648o;
    /* renamed from: p */
    View f9649p;
    /* renamed from: q */
    View f9650q;
    /* renamed from: r */
    View f9651r;
    /* renamed from: s */
    ProgressBar f9652s;
    /* renamed from: t */
    int f9653t = 0;
    /* renamed from: u */
    int f9654u = 0;
    /* renamed from: v */
    View f9655v;
    /* renamed from: w */
    CircularProgressBar f9656w;
    /* renamed from: x */
    private View f9657x;
    /* renamed from: y */
    private SeekBar f9658y;
    /* renamed from: z */
    private ImageView f9659z;

    /* renamed from: org.telegram.customization.util.view.a$1 */
    class C29071 implements OnSeekBarChangeListener {
        /* renamed from: a */
        final /* synthetic */ C2922a f9595a;

        C29071(C2922a c2922a) {
            this.f9595a = c2922a;
        }

        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (z) {
                this.f9595a.f9646m.seekTo((this.f9595a.f9646m.getDuration() * i) / 100);
            }
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            this.f9595a.f9626M.removeCallbacks(this.f9595a.f9625L);
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            this.f9595a.f9626M.postDelayed(this.f9595a.f9625L, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$2 */
    class C29082 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2922a f9596a;

        C29082(C2922a c2922a) {
            this.f9596a = c2922a;
        }

        public void run() {
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$3 */
    class C29093 extends Handler {
        /* renamed from: a */
        final /* synthetic */ C2922a f9597a;

        C29093(C2922a c2922a) {
            this.f9597a = c2922a;
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            switch (message.what) {
                case 1:
                    if (this.f9597a.f9646m.getCurrentPosition() > 0) {
                        this.f9597a.f9614A.setText(this.f9597a.m13490a((long) this.f9597a.f9646m.getCurrentPosition()));
                        this.f9597a.f9658y.setProgress((this.f9597a.f9646m.getCurrentPosition() * 100) / this.f9597a.f9646m.getDuration());
                        if (this.f9597a.f9646m.getCurrentPosition() > this.f9597a.f9646m.getDuration() - 100) {
                            this.f9597a.f9614A.setText("00:00");
                            this.f9597a.f9658y.setProgress(0);
                        }
                        this.f9597a.f9658y.setSecondaryProgress(this.f9597a.f9646m.getBufferPercentage());
                        return;
                    }
                    this.f9597a.f9614A.setText("00:00");
                    this.f9597a.f9658y.setProgress(0);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$4 */
    class C29114 implements OnErrorListener {
        /* renamed from: a */
        final /* synthetic */ C2922a f9599a;

        /* renamed from: org.telegram.customization.util.view.a$4$1 */
        class C29101 implements Runnable {
            /* renamed from: a */
            final /* synthetic */ C29114 f9598a;

            C29101(C29114 c29114) {
                this.f9598a = c29114;
            }

            public void run() {
                this.f9598a.f9599a.f9652s.setVisibility(8);
                this.f9598a.f9599a.f9646m.setVisibility(8);
                this.f9598a.f9599a.f9651r.setVisibility(0);
            }
        }

        C29114(C2922a c2922a) {
            this.f9599a = c2922a;
        }

        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            new Handler().post(new C29101(this));
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$5 */
    class C29135 implements OnPreparedListener {
        /* renamed from: a */
        final /* synthetic */ C2922a f9601a;

        /* renamed from: org.telegram.customization.util.view.a$5$1 */
        class C29121 extends TimerTask {
            /* renamed from: a */
            final /* synthetic */ C29135 f9600a;

            C29121(C29135 c29135) {
                this.f9600a = c29135;
            }

            public void run() {
                this.f9600a.f9601a.f9626M.sendEmptyMessage(1);
            }
        }

        C29135(C2922a c2922a) {
            this.f9601a = c2922a;
        }

        public void onPrepared(MediaPlayer mediaPlayer) {
            try {
                this.f9601a.f9646m.start();
                this.f9601a.f9652s.setVisibility(8);
                if (this.f9601a.f9616C != 0) {
                    this.f9601a.f9646m.seekTo(this.f9601a.f9616C);
                    this.f9601a.f9616C = 0;
                }
                this.f9601a.f9626M.removeCallbacks(this.f9601a.f9625L);
                this.f9601a.f9626M.postDelayed(this.f9601a.f9625L, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                this.f9601a.f9615B.setText(this.f9601a.m13490a((long) this.f9601a.f9646m.getDuration()));
                new Timer().schedule(new C29121(this), 0, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$6 */
    class C29146 implements OnCompletionListener {
        /* renamed from: a */
        final /* synthetic */ C2922a f9602a;

        C29146(C2922a c2922a) {
            this.f9602a = c2922a;
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            this.f9602a.f9646m.stopPlayback();
            this.f9602a.f9640g.setVisibility(0);
            this.f9602a.f9639f.setVisibility(0);
            this.f9602a.m13513f();
            mediaPlayer.release();
        }
    }

    /* renamed from: org.telegram.customization.util.view.a$7 */
    class C29157 implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ C2922a f9603a;

        C29157(C2922a c2922a) {
            this.f9603a = c2922a;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            boolean z = false;
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            switch (motionEvent.getAction()) {
                case 0:
                    this.f9603a.f9627N = x;
                    this.f9603a.f9628O = y;
                    this.f9603a.f9629P = (int) x;
                    this.f9603a.f9630Q = (int) y;
                    break;
                case 1:
                    if (Math.abs(x - ((float) this.f9603a.f9629P)) > ((float) this.f9603a.f9631R) || Math.abs(y - ((float) this.f9603a.f9630Q)) > ((float) this.f9603a.f9631R)) {
                        this.f9603a.f9632S = false;
                    }
                    this.f9603a.f9627N = BitmapDescriptorFactory.HUE_RED;
                    this.f9603a.f9628O = BitmapDescriptorFactory.HUE_RED;
                    this.f9603a.f9629P = 0;
                    if (this.f9603a.f9632S) {
                        this.f9603a.m13528a();
                    }
                    this.f9603a.f9632S = true;
                    break;
                case 2:
                    float h = x - this.f9603a.f9627N;
                    float i = y - this.f9603a.f9628O;
                    float abs = Math.abs(h);
                    float abs2 = Math.abs(i);
                    if (abs <= ((float) this.f9603a.f9631R) || abs2 <= ((float) this.f9603a.f9631R)) {
                        if (abs < ((float) this.f9603a.f9631R) && abs2 > ((float) this.f9603a.f9631R)) {
                            z = true;
                        } else if (abs > ((float) this.f9603a.f9631R) && abs2 < ((float) this.f9603a.f9631R)) {
                        }
                    } else if (abs < abs2) {
                        z = true;
                    }
                    if (z) {
                        if (x < this.f9603a.f9622I / 2.0f) {
                            if (i > BitmapDescriptorFactory.HUE_RED) {
                                this.f9603a.m13510e(abs2);
                            } else if (i < BitmapDescriptorFactory.HUE_RED) {
                                this.f9603a.m13514f(abs2);
                            }
                        } else if (i > BitmapDescriptorFactory.HUE_RED) {
                            this.f9603a.m13502c(abs2);
                        } else if (i < BitmapDescriptorFactory.HUE_RED) {
                            this.f9603a.m13506d(abs2);
                        }
                    } else if (h > BitmapDescriptorFactory.HUE_RED) {
                        this.f9603a.m13498b(abs);
                    } else if (h < BitmapDescriptorFactory.HUE_RED) {
                        this.f9603a.m13492a(abs);
                    }
                    this.f9603a.f9627N = x;
                    this.f9603a.f9628O = y;
                    break;
            }
            return true;
        }
    }

    public C2922a(Context context) {
        super(context);
    }

    @SuppressLint({"SimpleDateFormat"})
    /* renamed from: a */
    private String m13490a(long j) {
        try {
            String[] split = new SimpleDateFormat("mm:ss").format(new Date(j)).split(":");
            return (Integer.parseInt(split[0]) - 30) + ":" + split[1];
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    /* renamed from: a */
    private void m13492a(float f) {
        int currentPosition = this.f9646m.getCurrentPosition() - ((int) ((f / this.f9622I) * ((float) this.f9646m.getDuration())));
        this.f9646m.seekTo(currentPosition);
        this.f9658y.setProgress((currentPosition * 100) / this.f9646m.getDuration());
        this.f9614A.setText(m13490a((long) currentPosition));
    }

    /* renamed from: b */
    private Intent m13496b(String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setFlags(ErrorDialogData.BINDER_CRASH);
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", TtmlNode.ANONYMOUS_REGION_ID);
        intent.putExtra("android.intent.extra.TEXT", "نرم افزار هاتگرام\n" + this.f9638e.getMessage().getImage() + "\n" + this.f9638e.getMessage().message + "\n" + this.f9638e.getMessage().getChannel_name());
        return Intent.createChooser(intent, LocaleController.getString("MyAppName", R.string.MyAppName));
    }

    /* renamed from: b */
    private void m13498b(float f) {
        int currentPosition = this.f9646m.getCurrentPosition() + ((int) ((f / this.f9622I) * ((float) this.f9646m.getDuration())));
        this.f9646m.seekTo(currentPosition);
        this.f9658y.setProgress((currentPosition * 100) / this.f9646m.getDuration());
        this.f9614A.setText(m13490a((long) currentPosition));
    }

    /* renamed from: c */
    private void m13501c() {
        this.f9643j.setText(this.f9638e.getMessage().message);
        this.f9644k.setText(this.f9638e.getMessage().getChannel_name());
        this.f9647n.setText((this.f9654u + 1) + "/" + this.f9653t);
        this.f9645l.setText(C2872c.m13340a((long) this.f9638e.getMessage().date));
        if (this.f9638e.getMessage().getMediaType() == 8 || this.f9638e.getMessage().getMediaType() == 6) {
            this.f9640g.setVisibility(0);
            C2868b.m13331b(this.f9639f, this.f9638e.getMessage().getImage(), this.f9656w);
            return;
        }
        C2868b.m13330a(this.f9639f, this.f9638e.getMessage().getImage(), this.f9656w);
        this.f9640g.setVisibility(8);
        this.f9657x.setVisibility(8);
        this.f9646m.setVisibility(8);
    }

    /* renamed from: c */
    private void m13502c(float f) {
        int streamMaxVolume = this.f9621H.getStreamMaxVolume(3);
        int max = Math.max(this.f9621H.getStreamVolume(3) - ((int) (((f / this.f9623J) * ((float) streamMaxVolume)) * 3.0f)), 0);
        this.f9621H.setStreamVolume(3, max, 0);
        this.f9619F.a((float) ((max * 100) / streamMaxVolume));
    }

    /* renamed from: d */
    private void m13505d() {
        this.f9634a = this.f9637d.inflate(R.layout.sls_media_viewer_page, null);
        this.f9655v = this.f9634a.findViewById(R.id.tv_go_to_channel);
        this.f9639f = (ImageView) this.f9634a.findViewById(R.id.iv_main);
        this.f9640g = (ImageView) this.f9634a.findViewById(R.id.iv_play);
        this.f9641h = (ImageView) this.f9634a.findViewById(R.id.iv_share);
        this.f9646m = (VideoView) this.f9634a.findViewById(R.id.video_view);
        this.f9642i = (ImageView) this.f9634a.findViewById(R.id.iv_back);
        this.f9647n = (TextView) this.f9634a.findViewById(R.id.tv_number);
        this.f9648o = this.f9634a.findViewById(R.id.rootView);
        this.f9649p = this.f9634a.findViewById(R.id.ll_bottom);
        this.f9650q = this.f9634a.findViewById(R.id.toolbar);
        this.f9639f.setOnClickListener(this);
        this.f9642i.setOnClickListener(this);
        this.f9655v.setOnClickListener(this);
        this.f9651r = this.f9634a.findViewById(R.id.ll_video_error);
        this.f9656w = (CircularProgressBar) this.f9634a.findViewById(R.id.pb_image_loading);
        this.f9656w.setColor(C3792d.m14083b());
        this.f9656w.setBackgroundColor(C0235a.m1075c(getContext(), R.color.white));
        this.f9648o.setOnClickListener(this);
        this.f9614A = (TextView) this.f9634a.findViewById(R.id.play_time);
        this.f9615B = (TextView) this.f9634a.findViewById(R.id.total_time);
        this.f9659z = (ImageView) this.f9634a.findViewById(R.id.play_btn);
        this.f9659z.setOnClickListener(this);
        this.f9658y = (SeekBar) this.f9634a.findViewById(R.id.seekbar);
        this.f9657x = this.f9634a.findViewById(R.id.bottom_layout);
        this.f9658y.setOnSeekBarChangeListener(this.f9624K);
        this.f9652s = (ProgressBar) this.f9634a.findViewById(R.id.pbVideo);
        this.f9619F = new VolumnController(this.f9635b);
        this.f9620G = new LightnessController(this.f9635b);
        this.f9646m.setOnTouchListener(this.f9633T);
        this.f9621H = (AudioManager) this.f9635b.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.f9622I = DensityUtil.b(this.f9635b);
        this.f9623J = DensityUtil.a(this.f9635b);
        this.f9631R = DensityUtil.a(this.f9635b, 18.0f);
        this.f9643j = (TextView) this.f9634a.findViewById(R.id.ftv_main);
        this.f9644k = (TextView) this.f9634a.findViewById(R.id.ftv_channel_name);
        this.f9645l = (TextView) this.f9634a.findViewById(R.id.ftv_date);
        this.f9640g.setOnClickListener(this);
        this.f9641h.setOnClickListener(this);
    }

    /* renamed from: d */
    private void m13506d(float f) {
        int streamMaxVolume = this.f9621H.getStreamMaxVolume(3);
        int min = Math.min(this.f9621H.getStreamVolume(3) + ((int) (((f / this.f9623J) * ((float) streamMaxVolume)) * 3.0f)), streamMaxVolume);
        this.f9621H.setStreamVolume(3, min, 0);
        this.f9619F.a((float) ((min * 100) / streamMaxVolume));
    }

    /* renamed from: e */
    private void m13509e() {
        if (LaunchActivity.me != null) {
            this.f9636c.finish();
            if (this.f9638e != null && this.f9638e.getMessage() != null && this.f9638e.getMessage().to_id != null) {
                C2879f.m13356a((long) Math.abs(this.f9638e.getMessage().to_id.channel_id), (long) this.f9638e.getMessage().id, LaunchActivity.me, this.f9638e.getMessage().getUsername());
            }
        }
    }

    /* renamed from: e */
    private void m13510e(float f) {
        int a = LightnessController.a(this.f9636c) - ((int) (((f / this.f9623J) * 255.0f) * 3.0f));
        LightnessController.a(this.f9636c, a);
        this.f9620G.a((float) ((a * 100) / 255));
    }

    /* renamed from: f */
    private void m13513f() {
        this.f9639f.setVisibility(8);
        this.f9646m.setVisibility(0);
        this.f9657x.setVisibility(0);
        try {
            if (this.f9646m.isPlaying()) {
                this.f9646m.pause();
                this.f9659z.setImageResource(R.drawable.video_btn_down);
                return;
            }
            this.f9646m.start();
            this.f9659z.setImageResource(R.drawable.video_btn_on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: f */
    private void m13514f(float f) {
        int a = ((int) (((f / this.f9623J) * 255.0f) * 3.0f)) + LightnessController.a(this.f9636c);
        LightnessController.a(this.f9636c, a);
        this.f9620G.a((float) ((a * 100) / 255));
    }

    /* renamed from: g */
    private void m13516g() {
        this.f9649p.setVisibility(0);
    }

    /* renamed from: h */
    private void m13520h() {
        this.f9649p.setVisibility(8);
    }

    /* renamed from: a */
    public void m13528a() {
        boolean z = false;
        if (this.f9618E) {
            this.f9643j.setVisibility(8);
        } else {
            this.f9643j.setVisibility(0);
        }
        if (!this.f9618E) {
            z = true;
        }
        this.f9618E = z;
    }

    /* renamed from: a */
    public void m13529a(Context context, Activity activity, LayoutInflater layoutInflater, SlsBaseMessage slsBaseMessage, int i, int i2) {
        this.f9635b = context;
        this.f9636c = activity;
        this.f9637d = layoutInflater;
        this.f9638e = slsBaseMessage;
        this.f9653t = i;
        this.f9654u = i2;
        m13505d();
        m13501c();
        removeAllViews();
        addView(this.f9634a);
    }

    /* renamed from: a */
    public void m13530a(String str) {
        try {
            File directory = FileLoader.getInstance().getDirectory(this.f9638e.getMessage().getMediaType() == 8 ? 2 : 3);
            String attachFileName = FileLoader.getAttachFileName(this.f9638e.getMessage().media.document);
            if (attachFileName.lastIndexOf(46) < 0) {
                attachFileName = attachFileName + str.substring(str.lastIndexOf(46));
            }
            File file = new File(directory.getAbsolutePath() + File.separator + attachFileName);
            if (file.exists() && file.length() == ((long) this.f9638e.getMessage().media.document.size)) {
                this.f9646m.setVideoPath(file.getPath());
            } else {
                this.f9646m.setVideoPath(str);
            }
            m13516g();
            this.f9646m.setOnClickListener(this);
            this.f9640g.setVisibility(8);
            this.f9639f.setVisibility(8);
            this.f9652s.setVisibility(0);
            m13513f();
            this.f9646m.requestFocus();
            this.f9646m.setOnErrorListener(new C29114(this));
            this.f9646m.setOnPreparedListener(new C29135(this));
            this.f9646m.setOnCompletionListener(new C29146(this));
        } catch (Exception e) {
            this.f9646m.setVideoPath(str);
            e.printStackTrace();
        }
    }

    /* renamed from: b */
    public void m13531b() {
        if (this.f9617D) {
            m13520h();
        } else {
            m13516g();
        }
        this.f9617D = !this.f9617D;
    }

    public VideoView getVideoView() {
        return this.f9646m;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_view:
            case R.id.iv_main:
                m13531b();
                return;
            case R.id.iv_play:
                try {
                    m13530a(this.f9638e.getMessage().getFileUrl());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.iv_back:
                this.f9636c.onBackPressed();
                return;
            case R.id.iv_share:
                this.f9636c.startActivity(m13496b(this.f9638e.getMessage().message));
                return;
            case R.id.play_btn:
                m13513f();
                return;
            case R.id.tv_go_to_channel:
                m13509e();
                return;
            default:
                return;
        }
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void onPageScrolled(int i, float f, int i2) {
        if (i2 > 50 && this.f9646m.isPlaying()) {
            this.f9646m.pause();
            this.f9659z.setImageResource(R.drawable.video_btn_down);
        }
    }

    public void onPageSelected(int i) {
        this.f9647n.setText((i + 1) + "/" + this.f9653t);
    }

    public void setPlayTime(int i) {
        this.f9616C = i;
    }
}
