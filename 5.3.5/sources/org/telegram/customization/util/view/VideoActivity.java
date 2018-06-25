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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.customization.util.Constants;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.ui.LaunchActivity;
import utils.Utilities;
import utils.view.FarsiTextView;
import utils.view.VideoController.DensityUtil;
import utils.view.VideoController.LightnessController;
import utils.view.VideoController.VolumnController;

public class VideoActivity extends Activity implements OnClickListener {
    private static final int HIDE_TIME = 5000;
    FarsiTextView ftvVideoDuration;
    private float height;
    private Runnable hideRunnable = new C12551();
    private boolean isClick = true;
    boolean isFromPush = false;
    ImageView ivReplay;
    private LightnessController lightnessController;
    LinearLayout llButtonContainer;
    LinearLayout llContainer;
    LinearLayout llReload;
    private AudioManager mAudioManager;
    private View mBottomView;
    private TextView mDurationTime;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new C12562();
    private float mLastMotionX;
    private float mLastMotionY;
    private ImageView mPlay;
    private TextView mPlayTime;
    private SeekBar mSeekBar;
    private OnSeekBarChangeListener mSeekBarChangeListener = new C12573();
    private View mTopView;
    private OnTouchListener mTouchListener = new C12584();
    private VideoView mVideo;
    private int orginalLight;
    ProgressBar pbVideo;
    private int playTime;
    private View rootView;
    private int startX;
    private int startY;
    private int threshold;
    TextView title;
    private String videoContent;
    private String videoUrl = "http://hn2.asset.aparat.com/aparat-video/7dcaacabfc08704d721b5e585efe03f51978186__95263.apt?start=0";
    private VolumnController volumnController;
    private float width;

    /* renamed from: org.telegram.customization.util.view.VideoActivity$1 */
    class C12551 implements Runnable {
        C12551() {
        }

        public void run() {
            VideoActivity.this.showOrHide();
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$2 */
    class C12562 extends Handler {
        C12562() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (VideoActivity.this.mVideo.getCurrentPosition() > 0) {
                        VideoActivity.this.mPlayTime.setText(VideoActivity.this.formatTime((long) VideoActivity.this.mVideo.getCurrentPosition()));
                        VideoActivity.this.mSeekBar.setProgress((VideoActivity.this.mVideo.getCurrentPosition() * 100) / VideoActivity.this.mVideo.getDuration());
                        if (VideoActivity.this.mVideo.getCurrentPosition() > VideoActivity.this.mVideo.getDuration() - 100) {
                            VideoActivity.this.mPlayTime.setText("00:00");
                            VideoActivity.this.mSeekBar.setProgress(0);
                        }
                        VideoActivity.this.mSeekBar.setSecondaryProgress(VideoActivity.this.mVideo.getBufferPercentage());
                        return;
                    }
                    VideoActivity.this.mPlayTime.setText("00:00");
                    VideoActivity.this.mSeekBar.setProgress(0);
                    return;
                case 2:
                    VideoActivity.this.showOrHide();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$3 */
    class C12573 implements OnSeekBarChangeListener {
        C12573() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            VideoActivity.this.mHandler.postDelayed(VideoActivity.this.hideRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            VideoActivity.this.mHandler.removeCallbacks(VideoActivity.this.hideRunnable);
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                VideoActivity.this.mVideo.seekTo((VideoActivity.this.mVideo.getDuration() * progress) / 100);
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$4 */
    class C12584 implements OnTouchListener {
        C12584() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case 0:
                    VideoActivity.this.mLastMotionX = x;
                    VideoActivity.this.mLastMotionY = y;
                    VideoActivity.this.startX = (int) x;
                    VideoActivity.this.startY = (int) y;
                    break;
                case 1:
                    if (Math.abs(x - ((float) VideoActivity.this.startX)) > ((float) VideoActivity.this.threshold) || Math.abs(y - ((float) VideoActivity.this.startY)) > ((float) VideoActivity.this.threshold)) {
                        VideoActivity.this.isClick = false;
                    }
                    VideoActivity.this.mLastMotionX = 0.0f;
                    VideoActivity.this.mLastMotionY = 0.0f;
                    VideoActivity.this.startX = 0;
                    if (VideoActivity.this.isClick) {
                        VideoActivity.this.showOrHide();
                    }
                    VideoActivity.this.isClick = true;
                    break;
                case 2:
                    boolean isAdjustAudio;
                    float deltaX = x - VideoActivity.this.mLastMotionX;
                    float deltaY = y - VideoActivity.this.mLastMotionY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (absDeltaX <= ((float) VideoActivity.this.threshold) || absDeltaY <= ((float) VideoActivity.this.threshold)) {
                        if (absDeltaX < ((float) VideoActivity.this.threshold) && absDeltaY > ((float) VideoActivity.this.threshold)) {
                            isAdjustAudio = true;
                        } else if (absDeltaX > ((float) VideoActivity.this.threshold) && absDeltaY < ((float) VideoActivity.this.threshold)) {
                            isAdjustAudio = false;
                        }
                    } else if (absDeltaX < absDeltaY) {
                        isAdjustAudio = true;
                    } else {
                        isAdjustAudio = false;
                    }
                    if (isAdjustAudio) {
                        if (x < VideoActivity.this.width / 2.0f) {
                            if (deltaY > 0.0f) {
                                VideoActivity.this.volumeDown(absDeltaY);
                            } else if (deltaY < 0.0f) {
                                VideoActivity.this.volumeUp(absDeltaY);
                            }
                        } else if (deltaY > 0.0f) {
                            VideoActivity.this.volumeDown(absDeltaY);
                        } else if (deltaY < 0.0f) {
                            VideoActivity.this.volumeUp(absDeltaY);
                        }
                    } else if (deltaX > 0.0f) {
                        VideoActivity.this.forward(absDeltaX);
                    } else if (deltaX < 0.0f) {
                        VideoActivity.this.backward(absDeltaX);
                    }
                    VideoActivity.this.mLastMotionX = x;
                    VideoActivity.this.mLastMotionY = y;
                    break;
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$5 */
    class C12595 implements OnErrorListener {
        C12595() {
        }

        public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
            FileLog.d("TELEGRAM playing error " + what + " - " + extra);
            switch (what) {
                case 100:
                    VideoActivity.this.preparingShowVideo();
                    break;
                default:
                    VideoActivity.this.ivReplay.setVisibility(0);
                    VideoActivity.this.pbVideo.setVisibility(8);
                    break;
            }
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$6 */
    class C12606 implements OnClickListener {
        C12606() {
        }

        public void onClick(View view) {
            VideoActivity.this.preparingShowVideo();
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$7 */
    class C12617 implements Runnable {
        C12617() {
        }

        public void run() {
            if (VideoActivity.this.getResources().getConfiguration().orientation == 2) {
                VideoActivity.this.getWindow().clearFlags(2048);
                VideoActivity.this.getWindow().setFlags(1024, 1024);
                VideoActivity.this.height = DensityUtil.getWidthInPx(VideoActivity.this);
                VideoActivity.this.width = DensityUtil.getHeightInPx(VideoActivity.this);
                RelativeLayout rl_video_container = (RelativeLayout) VideoActivity.this.findViewById(R.id.rl_video_container);
                LayoutParams LP = (LayoutParams) rl_video_container.getLayoutParams();
                LP.weight = 100.0f;
                rl_video_container.setLayoutParams(LP);
            } else if (VideoActivity.this.getResources().getConfiguration().orientation == 1) {
                VideoActivity.this.getWindow().clearFlags(1024);
                VideoActivity.this.getWindow().setFlags(2048, 2048);
                VideoActivity.this.mVideo.setLayoutParams(new LayoutParams(-1, -2));
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$8 */
    class C12638 implements OnPreparedListener {

        /* renamed from: org.telegram.customization.util.view.VideoActivity$8$1 */
        class C12621 extends TimerTask {
            C12621() {
            }

            public void run() {
                VideoActivity.this.mHandler.sendEmptyMessage(1);
            }
        }

        C12638() {
        }

        public void onPrepared(MediaPlayer mp) {
            VideoActivity.this.rootView.setVisibility(0);
            VideoActivity.this.changeVideoState();
            VideoActivity.this.mVideo.start();
            VideoActivity.this.pbVideo.setVisibility(8);
            if (VideoActivity.this.playTime != 0) {
                VideoActivity.this.mVideo.seekTo(VideoActivity.this.playTime);
            }
            VideoActivity.this.mHandler.removeCallbacks(VideoActivity.this.hideRunnable);
            VideoActivity.this.mHandler.postDelayed(VideoActivity.this.hideRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            VideoActivity.this.mDurationTime.setText(VideoActivity.this.formatTime((long) VideoActivity.this.mVideo.getDuration()));
            new Timer().schedule(new C12621(), 0, 1000);
        }
    }

    /* renamed from: org.telegram.customization.util.view.VideoActivity$9 */
    class C12649 implements OnCompletionListener {
        C12649() {
        }

        public void onCompletion(MediaPlayer mp) {
            VideoActivity.this.changeVideoState();
            VideoActivity.this.mVideo.stopPlayback();
            mp.release();
            VideoActivity.this.mTopView.setVisibility(0);
            VideoActivity.this.mTopView.clearAnimation();
            VideoActivity.this.mTopView.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this.getApplicationContext(), R.anim.option_entry_from_top));
            VideoActivity.this.mBottomView.setVisibility(0);
            VideoActivity.this.mBottomView.clearAnimation();
            VideoActivity.this.mBottomView.startAnimation(AnimationUtils.loadAnimation(VideoActivity.this.getApplicationContext(), R.anim.option_entry_from_bottom));
            VideoActivity.this.ivReplay.setVisibility(0);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sls_video_activity);
        String contentJson = getIntent().getStringExtra(Constants.EXTRA_VIDEO_URL);
        this.isFromPush = getIntent().getBooleanExtra(utils.view.Constants.IS_FROM_PUSH, false);
        if (TextUtils.isEmpty(contentJson)) {
            finish();
            return;
        }
        initVideo();
        setVideoContent(contentJson);
        preparingShowVideo();
    }

    public void setVideoContent(String videoContent) {
        this.videoContent = videoContent;
    }

    public String getVideoContent() {
        return this.videoContent;
    }

    protected void initVideo() {
        if (getResources().getConfiguration().orientation == 2) {
            Configuration configuration = new Configuration();
            configuration.orientation = 2;
            onConfigurationChanged(configuration);
        }
        this.volumnController = new VolumnController(this);
        this.lightnessController = new LightnessController(this);
        this.mVideo = (VideoView) findViewById(R.id.videoview);
        this.mPlayTime = (TextView) findViewById(R.id.play_time);
        this.mDurationTime = (TextView) findViewById(R.id.total_time);
        this.mPlay = (ImageView) findViewById(R.id.play_btn);
        this.mSeekBar = (SeekBar) findViewById(R.id.seekbar);
        this.mTopView = findViewById(R.id.top_layout);
        this.mBottomView = findViewById(R.id.bottom_layout);
        this.pbVideo = (ProgressBar) findViewById(R.id.pbVideo);
        this.ivReplay = (ImageView) findViewById(R.id.iv_rePlay);
        this.rootView = findViewById(R.id.rootView);
        this.mVideo.setOnErrorListener(new C12595());
        this.mAudioManager = (AudioManager) getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.width = DensityUtil.getWidthInPx(this);
        this.height = DensityUtil.getHeightInPx(this);
        this.threshold = DensityUtil.dip2px(this, 18.0f);
        this.orginalLight = LightnessController.getLightness(this);
        this.mPlay.setOnClickListener(this);
        this.mSeekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
        this.llReload = (LinearLayout) findViewById(R.id.ll_retry);
        this.ivReplay.setOnClickListener(new C12606());
    }

    private void preparingShowVideo() {
        this.ivReplay.setVisibility(8);
        try {
            playVideo(getVideoContent());
        } catch (Exception e) {
            FileLog.d("TELEGRAM " + e.getMessage());
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        FileLog.d("TELEGRAM onconfigurationChanged");
        findViewById(R.id.rl_video_container).post(new C12617());
        super.onConfigurationChanged(newConfig);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.play_btn) {
            changeVideoState();
        }
    }

    private void changeVideoState() {
        try {
            if (this.mVideo.isPlaying()) {
                this.mVideo.pause();
                this.mPlay.setImageResource(R.drawable.video_btn_down);
                return;
            }
            this.mVideo.start();
            this.mPlay.setImageResource(R.drawable.video_btn_on);
        } catch (Exception e) {
            FileLog.d("TELEGRAM" + e.getMessage());
        }
    }

    protected void onPause() {
        super.onPause();
        LightnessController.setLightness(this, this.orginalLight);
    }

    private void backward(float delataX) {
        int currentTime = this.mVideo.getCurrentPosition() - ((int) ((delataX / this.width) * ((float) this.mVideo.getDuration())));
        this.mVideo.seekTo(currentTime);
        this.mSeekBar.setProgress((currentTime * 100) / this.mVideo.getDuration());
        this.mPlayTime.setText(formatTime((long) currentTime));
    }

    private void forward(float delataX) {
        int currentTime = this.mVideo.getCurrentPosition() + ((int) ((delataX / this.width) * ((float) this.mVideo.getDuration())));
        this.mVideo.seekTo(currentTime);
        this.mSeekBar.setProgress((currentTime * 100) / this.mVideo.getDuration());
        this.mPlayTime.setText(formatTime((long) currentTime));
    }

    private void volumeDown(float delatY) {
        int max = this.mAudioManager.getStreamMaxVolume(3);
        int volume = Math.max(this.mAudioManager.getStreamVolume(3) - ((int) (((delatY / this.height) * ((float) max)) * 3.0f)), 0);
        this.mAudioManager.setStreamVolume(3, volume, 0);
        this.volumnController.show((float) ((volume * 100) / max));
    }

    private void volumeUp(float delatY) {
        int max = this.mAudioManager.getStreamMaxVolume(3);
        int volume = Math.min(this.mAudioManager.getStreamVolume(3) + ((int) (((delatY / this.height) * ((float) max)) * 3.0f)), max);
        this.mAudioManager.setStreamVolume(3, volume, 0);
        this.volumnController.show((float) ((volume * 100) / max));
    }

    private void lightDown(float delatY) {
        int transformatLight = LightnessController.getLightness(this) - ((int) (((delatY / this.height) * 255.0f) * 3.0f));
        LightnessController.setLightness(this, transformatLight);
        this.lightnessController.show((float) ((transformatLight * 100) / 255));
    }

    private void lightUp(float delatY) {
        int transformatLight = LightnessController.getLightness(this) + ((int) (((delatY / this.height) * 255.0f) * 3.0f));
        LightnessController.setLightness(this, transformatLight);
        this.lightnessController.show((float) ((transformatLight * 100) / 255));
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mHandler.removeMessages(0);
        this.mHandler.removeCallbacksAndMessages(null);
    }

    private void playVideo(String url) {
        changeVideoState();
        try {
            this.rootView.setVisibility(4);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.pbVideo.setVisibility(0);
        this.mVideo.setVideoPath(url);
        this.mVideo.requestFocus();
        this.mVideo.setOnPreparedListener(new C12638());
        this.mVideo.setOnCompletionListener(new C12649());
        this.mVideo.setOnTouchListener(this.mTouchListener);
        new Handler().postDelayed(new Runnable() {
            public void run() {
            }
        }, 200);
    }

    private void fitVideoView(MediaPlayer mp) {
        int screenWidth = (int) DensityUtil.getWidthInPx(this);
        int fileWidth = mp.getVideoWidth();
        int fileHeight = mp.getVideoHeight();
        screenWidth = this.rootView.getWidth();
        float constVar = ((float) screenWidth) / ((float) fileWidth);
        fileWidth = screenWidth;
        fileHeight = (int) (((float) fileHeight) * constVar);
        if (((int) Utilities.convertPixelsToDp((float) fileHeight, this)) > ScheduleDownloadActivity.CHECK_CELL2) {
            float constH = ((float) Utilities.convertDpToPixel(300.0f, this)) / ((float) fileHeight);
            fileHeight = Utilities.convertDpToPixel(300.0f, this);
            fileWidth = (int) (((float) fileWidth) * constH);
        }
    }

    @SuppressLint({"SimpleDateFormat"})
    private String formatTime(long time) {
        try {
            String[] parts = new SimpleDateFormat("mm:ss").format(new Date(time)).split(":");
            return (Integer.parseInt(parts[0]) - 30) + ":" + parts[1];
        } catch (Exception e) {
            e.printStackTrace();
            return " ";
        }
    }

    private void showOrHide() {
        if (this.mTopView.getVisibility() == 0) {
            this.mTopView.clearAnimation();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.option_leave_from_top);
            animation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    VideoActivity.this.mTopView.setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            this.mTopView.startAnimation(animation);
            this.mBottomView.clearAnimation();
            Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.option_leave_from_bottom);
            animation1.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    VideoActivity.this.mBottomView.setVisibility(8);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            this.mBottomView.startAnimation(animation1);
            return;
        }
        this.mTopView.setVisibility(0);
        this.mTopView.clearAnimation();
        this.mTopView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.option_entry_from_top));
        this.mBottomView.setVisibility(0);
        this.mBottomView.clearAnimation();
        this.mBottomView.startAnimation(AnimationUtils.loadAnimation(this, R.anim.option_entry_from_bottom));
        this.mHandler.removeCallbacks(this.hideRunnable);
        this.mHandler.postDelayed(this.hideRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == 2) {
            setRequestedOrientation(1);
        } else if (getResources().getConfiguration().orientation == 1) {
            super.onBackPressed();
            if (this.isFromPush) {
                startActivity(new Intent(getApplicationContext(), LaunchActivity.class));
            }
            finish();
        }
    }
}
