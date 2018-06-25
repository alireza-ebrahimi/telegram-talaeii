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
import org.telegram.customization.easyvideoplayer.C2541a;
import org.telegram.customization.easyvideoplayer.EasyVideoPlayer;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import utils.p178a.C3791b;

/* renamed from: org.telegram.customization.Activities.d */
public class C2542d extends BaseFragment implements OnClickListener, C2541a {
    /* renamed from: a */
    View f8494a;
    /* renamed from: b */
    View f8495b;
    /* renamed from: c */
    TextView f8496c;
    /* renamed from: d */
    VideoView f8497d;
    /* renamed from: e */
    private EasyVideoPlayer f8498e;

    /* renamed from: org.telegram.customization.Activities.d$1 */
    class C25401 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2542d f8493a;

        C25401(C2542d c2542d) {
            this.f8493a = c2542d;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8493a.finishFragment();
            }
        }
    }

    /* renamed from: a */
    public void mo3431a(int i) {
    }

    /* renamed from: a */
    public void mo3432a(EasyVideoPlayer easyVideoPlayer) {
    }

    /* renamed from: a */
    public void mo3433a(EasyVideoPlayer easyVideoPlayer, Uri uri) {
    }

    /* renamed from: a */
    public void mo3434a(EasyVideoPlayer easyVideoPlayer, Exception exception) {
    }

    /* renamed from: b */
    public void mo3435b(EasyVideoPlayer easyVideoPlayer) {
    }

    /* renamed from: b */
    public void mo3436b(EasyVideoPlayer easyVideoPlayer, Uri uri) {
    }

    /* renamed from: c */
    public void mo3437c(EasyVideoPlayer easyVideoPlayer) {
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Advertise", R.string.Advertise));
        this.actionBar.setActionBarMenuOnItemClick(new C25401(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_ads_join, null);
        try {
            this.f8498e = (EasyVideoPlayer) this.fragmentView.findViewById(R.id.player);
            this.f8498e.setCallback(this);
            this.f8498e.setSource(Uri.parse(C3791b.ah(ApplicationLoader.applicationContext)));
            this.f8498e.f8972a.setVisibility(8);
            this.f8498e.f8973b.setVisibility(8);
        } catch (Exception e) {
        }
        this.f8494a = this.fragmentView.findViewById(R.id.btn_join);
        this.f8496c = (TextView) this.fragmentView.findViewById(R.id.tv_join_message);
        this.f8497d = (VideoView) this.fragmentView.findViewById(R.id.video_view);
        this.f8495b = this.fragmentView.findViewById(R.id.iv_play);
        this.f8495b.setOnClickListener(this);
        this.f8497d.setOnClickListener(this);
        if (!TextUtils.isEmpty(C3791b.af(ApplicationLoader.applicationContext))) {
            this.f8496c.setText(C3791b.af(ApplicationLoader.applicationContext));
            this.f8496c.setVisibility(0);
        }
        this.f8494a.setOnClickListener(this);
        return this.fragmentView;
    }

    /* renamed from: d */
    public void mo3438d(EasyVideoPlayer easyVideoPlayer) {
    }

    /* renamed from: e */
    public void mo3439e(EasyVideoPlayer easyVideoPlayer) {
    }

    /* renamed from: f */
    public void mo3440f(EasyVideoPlayer easyVideoPlayer) {
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
                if (this.f8497d.isPlaying()) {
                    this.f8497d.pause();
                    this.f8495b.setVisibility(0);
                    return;
                }
                return;
            case R.id.iv_play:
                if (!this.f8497d.isPlaying()) {
                    this.f8495b.setVisibility(8);
                    this.f8497d.setVideoPath(C3791b.ah(ApplicationLoader.applicationContext));
                    this.f8497d.start();
                    return;
                }
                return;
            case R.id.btn_join:
                presentFragment(new C2529a());
                return;
            default:
                return;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onResume() {
        super.onResume();
    }
}
