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
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
import com.mikhaellopez.circularprogressbar.CircularProgressBar;
import com.persianswitch.sdk.base.log.LogCollector;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.customization.dynamicadapter.data.SlsBaseMessage;
import org.telegram.customization.util.AppImageLoader;
import org.telegram.customization.util.AppUtilities;
import org.telegram.customization.util.IntentMaker;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.ui.LaunchActivity;
import utils.Utilities;
import utils.view.VideoController.DensityUtil;
import utils.view.VideoController.LightnessController;
import utils.view.VideoController.VolumnController;

public class MediaViewPage extends LinearLayout implements OnClickListener, OnPageChangeListener {
    private static final int HIDE_TIME = 5000;
    public static final int threshold1 = 50;
    Activity activity;
    View bottomContainer;
    Context context;
    int firstPos = 0;
    private float height;
    private Runnable hideRunnable = new C12412();
    LayoutInflater inflater;
    private boolean isClick = true;
    ImageView ivBack;
    ImageView ivMain;
    ImageView ivPlay;
    ImageView ivShare;
    private LightnessController lightnessController;
    private AudioManager mAudioManager;
    private View mBottomView;
    private TextView mDurationTime;
    @SuppressLint({"HandlerLeak"})
    private Handler mHandler = new C12423();
    private float mLastMotionX;
    private float mLastMotionY;
    private ImageView mPlay;
    private TextView mPlayTime;
    private SeekBar mSeekBar;
    private OnSeekBarChangeListener mSeekBarChangeListener = new C12401();
    private OnTouchListener mTouchListener = new C12487();
    SlsBaseMessage message;
    int objNo = 0;
    CircularProgressBar pbImageLoading;
    ProgressBar pbVideo;
    private int playTime;
    View root;
    private boolean showFlag = true;
    private boolean showFlag1 = true;
    private int startX;
    private int startY;
    private int threshold;
    View toolbar;
    TextView tvChannelName;
    TextView tvDate;
    View tvGotoChannel;
    TextView tvMain;
    TextView tvNumber;
    View videoError;
    VideoView videoView;
    View view;
    private VolumnController volumnController;
    private float width;

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$1 */
    class C12401 implements OnSeekBarChangeListener {
        C12401() {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            MediaViewPage.this.mHandler.postDelayed(MediaViewPage.this.hideRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            MediaViewPage.this.mHandler.removeCallbacks(MediaViewPage.this.hideRunnable);
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                MediaViewPage.this.videoView.seekTo((MediaViewPage.this.videoView.getDuration() * progress) / 100);
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$2 */
    class C12412 implements Runnable {
        C12412() {
        }

        public void run() {
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$3 */
    class C12423 extends Handler {
        C12423() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (MediaViewPage.this.videoView.getCurrentPosition() > 0) {
                        MediaViewPage.this.mPlayTime.setText(MediaViewPage.this.formatTime((long) MediaViewPage.this.videoView.getCurrentPosition()));
                        MediaViewPage.this.mSeekBar.setProgress((MediaViewPage.this.videoView.getCurrentPosition() * 100) / MediaViewPage.this.videoView.getDuration());
                        if (MediaViewPage.this.videoView.getCurrentPosition() > MediaViewPage.this.videoView.getDuration() - 100) {
                            MediaViewPage.this.mPlayTime.setText("00:00");
                            MediaViewPage.this.mSeekBar.setProgress(0);
                        }
                        MediaViewPage.this.mSeekBar.setSecondaryProgress(MediaViewPage.this.videoView.getBufferPercentage());
                        return;
                    }
                    MediaViewPage.this.mPlayTime.setText("00:00");
                    MediaViewPage.this.mSeekBar.setProgress(0);
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$4 */
    class C12444 implements OnErrorListener {

        /* renamed from: org.telegram.customization.util.view.MediaViewPage$4$1 */
        class C12431 implements Runnable {
            C12431() {
            }

            public void run() {
                MediaViewPage.this.pbVideo.setVisibility(8);
                MediaViewPage.this.videoView.setVisibility(8);
                MediaViewPage.this.videoError.setVisibility(0);
            }
        }

        C12444() {
        }

        public boolean onError(MediaPlayer mp, int what, int extra) {
            new Handler().post(new C12431());
            return true;
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$5 */
    class C12465 implements OnPreparedListener {

        /* renamed from: org.telegram.customization.util.view.MediaViewPage$5$1 */
        class C12451 extends TimerTask {
            C12451() {
            }

            public void run() {
                MediaViewPage.this.mHandler.sendEmptyMessage(1);
            }
        }

        C12465() {
        }

        public void onPrepared(MediaPlayer mp) {
            try {
                MediaViewPage.this.videoView.start();
                MediaViewPage.this.pbVideo.setVisibility(8);
                if (MediaViewPage.this.playTime != 0) {
                    MediaViewPage.this.videoView.seekTo(MediaViewPage.this.playTime);
                    MediaViewPage.this.playTime = 0;
                }
                MediaViewPage.this.mHandler.removeCallbacks(MediaViewPage.this.hideRunnable);
                MediaViewPage.this.mHandler.postDelayed(MediaViewPage.this.hideRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                MediaViewPage.this.mDurationTime.setText(MediaViewPage.this.formatTime((long) MediaViewPage.this.videoView.getDuration()));
                new Timer().schedule(new C12451(), 0, 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$6 */
    class C12476 implements OnCompletionListener {
        C12476() {
        }

        public void onCompletion(MediaPlayer mp) {
            MediaViewPage.this.videoView.stopPlayback();
            MediaViewPage.this.ivPlay.setVisibility(0);
            MediaViewPage.this.ivMain.setVisibility(0);
            MediaViewPage.this.changeVideoState();
            mp.release();
        }
    }

    /* renamed from: org.telegram.customization.util.view.MediaViewPage$7 */
    class C12487 implements OnTouchListener {
        C12487() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch (event.getAction()) {
                case 0:
                    MediaViewPage.this.mLastMotionX = x;
                    MediaViewPage.this.mLastMotionY = y;
                    MediaViewPage.this.startX = (int) x;
                    MediaViewPage.this.startY = (int) y;
                    break;
                case 1:
                    if (Math.abs(x - ((float) MediaViewPage.this.startX)) > ((float) MediaViewPage.this.threshold) || Math.abs(y - ((float) MediaViewPage.this.startY)) > ((float) MediaViewPage.this.threshold)) {
                        MediaViewPage.this.isClick = false;
                    }
                    MediaViewPage.this.mLastMotionX = 0.0f;
                    MediaViewPage.this.mLastMotionY = 0.0f;
                    MediaViewPage.this.startX = 0;
                    if (MediaViewPage.this.isClick) {
                        MediaViewPage.this.changeShowDesc();
                    }
                    MediaViewPage.this.isClick = true;
                    break;
                case 2:
                    boolean isAdjustAudio;
                    float deltaX = x - MediaViewPage.this.mLastMotionX;
                    float deltaY = y - MediaViewPage.this.mLastMotionY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (absDeltaX <= ((float) MediaViewPage.this.threshold) || absDeltaY <= ((float) MediaViewPage.this.threshold)) {
                        if (absDeltaX < ((float) MediaViewPage.this.threshold) && absDeltaY > ((float) MediaViewPage.this.threshold)) {
                            isAdjustAudio = true;
                        } else if (absDeltaX > ((float) MediaViewPage.this.threshold) && absDeltaY < ((float) MediaViewPage.this.threshold)) {
                            isAdjustAudio = false;
                        }
                    } else if (absDeltaX < absDeltaY) {
                        isAdjustAudio = true;
                    } else {
                        isAdjustAudio = false;
                    }
                    if (isAdjustAudio) {
                        if (x < MediaViewPage.this.width / 2.0f) {
                            if (deltaY > 0.0f) {
                                MediaViewPage.this.lightDown(absDeltaY);
                            } else if (deltaY < 0.0f) {
                                MediaViewPage.this.lightUp(absDeltaY);
                            }
                        } else if (deltaY > 0.0f) {
                            MediaViewPage.this.volumeDown(absDeltaY);
                        } else if (deltaY < 0.0f) {
                            MediaViewPage.this.volumeUp(absDeltaY);
                        }
                    } else if (deltaX > 0.0f) {
                        MediaViewPage.this.forward(absDeltaX);
                    } else if (deltaX < 0.0f) {
                        MediaViewPage.this.backward(absDeltaX);
                    }
                    MediaViewPage.this.mLastMotionX = x;
                    MediaViewPage.this.mLastMotionY = y;
                    break;
            }
            return true;
        }
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public MediaViewPage(Context context) {
        super(context);
    }

    public void initMediaViewPage(Context mContext, Activity mActivity, LayoutInflater mInflater, SlsBaseMessage mMessage, int mObjNo, int pos) {
        this.context = mContext;
        this.activity = mActivity;
        this.inflater = mInflater;
        this.message = mMessage;
        this.objNo = mObjNo;
        this.firstPos = pos;
        initView();
        fillView();
        removeAllViews();
        addView(this.view);
    }

    public VideoView getVideoView() {
        return this.videoView;
    }

    private void fillView() {
        this.tvMain.setText(this.message.getMessage().message);
        this.tvChannelName.setText(this.message.getMessage().getChannel_name());
        this.tvNumber.setText((this.firstPos + 1) + "/" + this.objNo);
        this.tvDate.setText(AppUtilities.convertUnixTimeToString((long) this.message.getMessage().date));
        if (this.message.getMessage().getMediaType() == 8 || this.message.getMessage().getMediaType() == 6) {
            this.ivPlay.setVisibility(0);
            AppImageLoader.loadImageLikeTelegramForVideo(this.ivMain, this.message.getMessage().getImage(), this.pbImageLoading);
            return;
        }
        AppImageLoader.loadImageLikeTelegram(this.ivMain, this.message.getMessage().getImage(), this.pbImageLoading);
        this.ivPlay.setVisibility(8);
        this.mBottomView.setVisibility(8);
        this.videoView.setVisibility(8);
    }

    private void initView() {
        this.view = this.inflater.inflate(R.layout.sls_media_viewer_page, null);
        this.tvGotoChannel = this.view.findViewById(R.id.tv_go_to_channel);
        this.ivMain = (ImageView) this.view.findViewById(R.id.iv_main);
        this.ivPlay = (ImageView) this.view.findViewById(R.id.iv_play);
        this.ivShare = (ImageView) this.view.findViewById(R.id.iv_share);
        this.videoView = (VideoView) this.view.findViewById(R.id.video_view);
        this.ivBack = (ImageView) this.view.findViewById(R.id.iv_back);
        this.tvNumber = (TextView) this.view.findViewById(R.id.tv_number);
        this.root = this.view.findViewById(R.id.rootView);
        this.bottomContainer = this.view.findViewById(R.id.ll_bottom);
        this.toolbar = this.view.findViewById(R.id.toolbar);
        this.ivMain.setOnClickListener(this);
        this.ivBack.setOnClickListener(this);
        this.tvGotoChannel.setOnClickListener(this);
        this.videoError = this.view.findViewById(R.id.ll_video_error);
        this.pbImageLoading = (CircularProgressBar) this.view.findViewById(R.id.pb_image_loading);
        this.pbImageLoading.setColor(Utilities.getThemeColor());
        this.pbImageLoading.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
        this.root.setOnClickListener(this);
        this.mPlayTime = (TextView) this.view.findViewById(R.id.play_time);
        this.mDurationTime = (TextView) this.view.findViewById(R.id.total_time);
        this.mPlay = (ImageView) this.view.findViewById(R.id.play_btn);
        this.mPlay.setOnClickListener(this);
        this.mSeekBar = (SeekBar) this.view.findViewById(R.id.seekbar);
        this.mBottomView = this.view.findViewById(R.id.bottom_layout);
        this.mSeekBar.setOnSeekBarChangeListener(this.mSeekBarChangeListener);
        this.pbVideo = (ProgressBar) this.view.findViewById(R.id.pbVideo);
        this.volumnController = new VolumnController(this.context);
        this.lightnessController = new LightnessController(this.context);
        this.videoView.setOnTouchListener(this.mTouchListener);
        this.mAudioManager = (AudioManager) this.context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
        this.width = DensityUtil.getWidthInPx(this.context);
        this.height = DensityUtil.getHeightInPx(this.context);
        this.threshold = DensityUtil.dip2px(this.context, 18.0f);
        this.tvMain = (TextView) this.view.findViewById(R.id.ftv_main);
        this.tvChannelName = (TextView) this.view.findViewById(R.id.ftv_channel_name);
        this.tvDate = (TextView) this.view.findViewById(R.id.ftv_date);
        this.ivPlay.setOnClickListener(this);
        this.ivShare.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_view:
            case R.id.iv_main:
                changeShowMessageMeta();
                return;
            case R.id.iv_play:
                try {
                    playVideo(this.message.getMessage().getFileUrl());
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case R.id.iv_back:
                this.activity.onBackPressed();
                return;
            case R.id.iv_share:
                this.activity.startActivity(createShareIntent(this.message.getMessage().message));
                return;
            case R.id.play_btn:
                changeVideoState();
                return;
            case R.id.tv_go_to_channel:
                goToChannel();
                return;
            default:
                return;
        }
    }

    private void goToChannel() {
        if (LaunchActivity.me != null) {
            this.activity.finish();
            if (this.message != null && this.message.getMessage() != null && this.message.getMessage().to_id != null) {
                IntentMaker.goToChannel((long) Math.abs(this.message.getMessage().to_id.channel_id), (long) this.message.getMessage().id, LaunchActivity.me, this.message.getMessage().getUsername());
            }
        }
    }

    private Intent createShareIntent(String desc) {
        Intent share = new Intent("android.intent.action.SEND");
        share.setFlags(268435456);
        share.setType("text/plain");
        share.putExtra("android.intent.extra.SUBJECT", "");
        share.putExtra("android.intent.extra.TEXT", "نرم افزار هاتگرام\n" + this.message.getMessage().getImage() + LogCollector.LINE_SEPARATOR + this.message.getMessage().message + LogCollector.LINE_SEPARATOR + this.message.getMessage().getChannel_name());
        return Intent.createChooser(share, LocaleController.getString("MyAppName", R.string.MyAppName));
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

    public void playVideo(String url) {
        try {
            File fileDirectory = FileLoader.getInstance().getDirectory(this.message.getMessage().getMediaType() == 8 ? 2 : 3);
            String fileName = FileLoader.getAttachFileName(this.message.getMessage().media.document);
            if (fileName.lastIndexOf(46) < 0) {
                fileName = fileName + url.substring(url.lastIndexOf(46));
            }
            File dlFile = new File(fileDirectory.getAbsolutePath() + File.separator + fileName);
            if (dlFile.exists() && dlFile.length() == ((long) this.message.getMessage().media.document.size)) {
                this.videoView.setVideoPath(dlFile.getPath());
            } else {
                this.videoView.setVideoPath(url);
            }
            showMeta();
            this.videoView.setOnClickListener(this);
            this.ivPlay.setVisibility(8);
            this.ivMain.setVisibility(8);
            this.pbVideo.setVisibility(0);
            changeVideoState();
            this.videoView.requestFocus();
            this.videoView.setOnErrorListener(new C12444());
            this.videoView.setOnPreparedListener(new C12465());
            this.videoView.setOnCompletionListener(new C12476());
        } catch (Exception e) {
            this.videoView.setVideoPath(url);
            e.printStackTrace();
        }
    }

    private void changeVideoState() {
        this.ivMain.setVisibility(8);
        this.videoView.setVisibility(0);
        this.mBottomView.setVisibility(0);
        try {
            if (this.videoView.isPlaying()) {
                this.videoView.pause();
                this.mPlay.setImageResource(R.drawable.video_btn_down);
                return;
            }
            this.videoView.start();
            this.mPlay.setImageResource(R.drawable.video_btn_on);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffsetPixels > 50 && this.videoView.isPlaying()) {
            this.videoView.pause();
            this.mPlay.setImageResource(R.drawable.video_btn_down);
        }
    }

    public void onPageSelected(int position) {
        this.tvNumber.setText((position + 1) + "/" + this.objNo);
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void changeShowDesc() {
        boolean z = false;
        if (this.showFlag1) {
            this.tvMain.setVisibility(8);
        } else {
            this.tvMain.setVisibility(0);
        }
        if (!this.showFlag1) {
            z = true;
        }
        this.showFlag1 = z;
    }

    public void changeShowMessageMeta() {
        if (this.showFlag) {
            hiddenMeta();
        } else {
            showMeta();
        }
        this.showFlag = !this.showFlag;
    }

    private void showMeta() {
        this.bottomContainer.setVisibility(0);
    }

    private void hiddenMeta() {
        this.bottomContainer.setVisibility(8);
    }

    private void backward(float delataX) {
        int currentTime = this.videoView.getCurrentPosition() - ((int) ((delataX / this.width) * ((float) this.videoView.getDuration())));
        this.videoView.seekTo(currentTime);
        this.mSeekBar.setProgress((currentTime * 100) / this.videoView.getDuration());
        this.mPlayTime.setText(formatTime((long) currentTime));
    }

    private void forward(float delataX) {
        int currentTime = this.videoView.getCurrentPosition() + ((int) ((delataX / this.width) * ((float) this.videoView.getDuration())));
        this.videoView.seekTo(currentTime);
        this.mSeekBar.setProgress((currentTime * 100) / this.videoView.getDuration());
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
        int transformatLight = LightnessController.getLightness(this.activity) - ((int) (((delatY / this.height) * 255.0f) * 3.0f));
        LightnessController.setLightness(this.activity, transformatLight);
        this.lightnessController.show((float) ((transformatLight * 100) / 255));
    }

    private void lightUp(float delatY) {
        int transformatLight = LightnessController.getLightness(this.activity) + ((int) (((delatY / this.height) * 255.0f) * 3.0f));
        LightnessController.setLightness(this.activity, transformatLight);
        this.lightnessController.show((float) ((transformatLight * 100) / 255));
    }
}
