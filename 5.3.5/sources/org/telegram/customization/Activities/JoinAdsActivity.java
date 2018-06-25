package org.telegram.customization.Activities;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.VideoView;
import org.ir.talaeii.R;
import org.telegram.customization.easyvideoplayer.EasyVideoCallback;
import org.telegram.customization.easyvideoplayer.EasyVideoPlayer;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import utils.app.AppPreferences;

public class JoinAdsActivity extends BaseFragment implements OnClickListener, EasyVideoCallback {
    View ivPlay;
    View join;
    private EasyVideoPlayer player;
    TextView tvJoinMessage;
    VideoView videoView;

    /* renamed from: org.telegram.customization.Activities.JoinAdsActivity$1 */
    class C10311 extends ActionBarMenuOnItemClick {
        C10311() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                JoinAdsActivity.this.finishFragment();
            }
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Advertise", R.string.Advertise));
        this.actionBar.setActionBarMenuOnItemClick(new C10311());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_ads_join, null);
        try {
            this.player = (EasyVideoPlayer) this.fragmentView.findViewById(R.id.player);
            this.player.setCallback(this);
            this.player.setSource(Uri.parse(AppPreferences.getAdsUrlTu(ApplicationLoader.applicationContext)));
            this.player.mBtnRestart.setVisibility(8);
            this.player.mBtnRetry.setVisibility(8);
        } catch (Exception e) {
        }
        this.join = this.fragmentView.findViewById(R.id.btn_join);
        this.tvJoinMessage = (TextView) this.fragmentView.findViewById(R.id.tv_join_message);
        this.videoView = (VideoView) this.fragmentView.findViewById(R.id.video_view);
        this.ivPlay = this.fragmentView.findViewById(R.id.iv_play);
        this.ivPlay.setOnClickListener(this);
        this.videoView.setOnClickListener(this);
        if (!TextUtils.isEmpty(AppPreferences.getAdsJoinMessage(ApplicationLoader.applicationContext))) {
            this.tvJoinMessage.setText(AppPreferences.getAdsJoinMessage(ApplicationLoader.applicationContext));
            this.tvJoinMessage.setVisibility(0);
        }
        this.join.setOnClickListener(this);
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[5];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        return themeDescriptionArr;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.video_view:
                if (this.videoView.isPlaying()) {
                    this.videoView.pause();
                    this.ivPlay.setVisibility(0);
                    return;
                }
                return;
            case R.id.iv_play:
                if (!this.videoView.isPlaying()) {
                    this.ivPlay.setVisibility(8);
                    this.videoView.setVideoPath(AppPreferences.getAdsUrlTu(ApplicationLoader.applicationContext));
                    this.videoView.start();
                    return;
                }
                return;
            case R.id.btn_join:
                presentFragment(new AdsTransactionActivity());
                return;
            default:
                return;
        }
    }

    public void onStarted(EasyVideoPlayer easyVideoPlayer) {
    }

    public void onPaused(EasyVideoPlayer easyVideoPlayer) {
    }

    public void onPreparing(EasyVideoPlayer easyVideoPlayer) {
    }

    public void onPrepared(EasyVideoPlayer easyVideoPlayer) {
    }

    public void onBuffering(int i) {
    }

    public void onError(EasyVideoPlayer easyVideoPlayer, Exception e) {
    }

    public void onCompletion(EasyVideoPlayer easyVideoPlayer) {
    }

    public void onRetry(EasyVideoPlayer easyVideoPlayer, Uri uri) {
    }

    public void onSubmit(EasyVideoPlayer easyVideoPlayer, Uri uri) {
    }

    public void onClickVideoFrame(EasyVideoPlayer player) {
    }
}
