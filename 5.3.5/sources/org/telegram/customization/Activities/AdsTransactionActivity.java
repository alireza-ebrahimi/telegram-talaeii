package org.telegram.customization.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.AdsChannelsAdapter;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Ads.Statistics;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.util.AppUtilities;
import org.telegram.customization.util.IntentMaker;
import org.telegram.customization.util.Prefs;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.Builder;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt.OnHidePromptListener;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.ToastUtil;

public class AdsTransactionActivity extends BaseFragment implements OnClickListener, IResponseReceiver, OnItemClickListener, NotificationCenterDelegate {
    AdsChannelsAdapter adapter;
    View btnAdsResult;
    View btnChannels;
    View btnScoreReport;
    ArrayList<Category> categories = new ArrayList();
    ProgressDialog dialogLoading;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    Statistics statistics = new Statistics();
    TextView tvScore;

    /* renamed from: org.telegram.customization.Activities.AdsTransactionActivity$1 */
    class C10101 extends ActionBarMenuOnItemClick {
        C10101() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                AdsTransactionActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.AdsTransactionActivity$2 */
    class C10122 implements Runnable {

        /* renamed from: org.telegram.customization.Activities.AdsTransactionActivity$2$1 */
        class C10111 implements OnHidePromptListener {
            C10111() {
            }

            public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                Prefs.setAdsTu1(AdsTransactionActivity.this.getParentActivity(), false);
            }

            public void onHidePromptComplete() {
            }
        }

        C10122() {
        }

        public void run() {
            if (Prefs.getAdsTu1(ApplicationLoader.applicationContext)) {
                new Builder(AdsTransactionActivity.this.getParentActivity()).setTarget(AdsTransactionActivity.this.btnChannels).setTextGravity(GravityCompat.END).setPrimaryTextTypeface(AppUtilities.getLightSansTypeface(AdsTransactionActivity.this.getParentActivity())).setSecondaryTextTypeface(AppUtilities.getUltraLightSansTypeface(AdsTransactionActivity.this.getParentActivity())).setPrimaryText(LocaleController.getString("Advertise", R.string.Advertise)).setSecondaryText(AppPreferences.getAdsTutorialChannelList(ApplicationLoader.applicationContext)).setOnHidePromptListener(new C10111()).show();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.AdsTransactionActivity$3 */
    class C10133 implements Runnable {
        C10133() {
        }

        public void run() {
            HandleRequest.getNew(AdsTransactionActivity.this.getParentActivity(), AdsTransactionActivity.this).getStatistics();
            HandleRequest.getNew(AdsTransactionActivity.this.getParentActivity(), AdsTransactionActivity.this).getCategories();
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Advertise", R.string.Advertise));
        this.actionBar.setActionBarMenuOnItemClick(new C10101());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_ads_transaction, null);
        this.tvScore = (TextView) this.fragmentView.findViewById(R.id.tv_score);
        this.recyclerView = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.btnScoreReport = this.fragmentView.findViewById(R.id.btn_score_report);
        this.btnScoreReport.setOnClickListener(this);
        this.btnAdsResult = this.fragmentView.findViewById(R.id.btn_ads_result);
        this.btnAdsResult.setOnClickListener(this);
        try {
            new Handler().postDelayed(new C10122(), 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("Advertise", R.string.Advertise), "لطفا منتظر بمانید");
        new Handler().postDelayed(new C10133(), 500);
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
            case R.id.btn_ads_result:
                Browser.openUrlSls(ApplicationLoader.applicationContext, Uri.parse(AppPreferences.getAdsUrl(ApplicationLoader.applicationContext)), true);
                return;
            case R.id.btn_score_report:
                final Dialog dialog = new Dialog(getParentActivity());
                dialog.setContentView(R.layout.dialog_transaction);
                TextView tvJoin = (TextView) dialog.findViewById(R.id.tv_join_count);
                ((TextView) dialog.findViewById(R.id.tv_link_count)).setText(this.statistics.getUrlCount() + "");
                tvJoin.setText(this.statistics.getJoinCount() + "");
                dialog.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return;
            default:
                return;
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case Constants.ERROR_GET_STATISTICS /*-22*/:
                this.dialogLoading.dismiss();
                return;
            case Constants.ERROR_MANAGE_ADS_CATEGORIES /*-20*/:
                ToastUtil.AppToast(getParentActivity(), "خطا در ثبت اطلاعات").show();
                this.dialogLoading.dismiss();
                return;
            case 20:
                ToastUtil.AppToast(getParentActivity(), "اطلاعات با موفقیت ثبت شد").show();
                this.dialogLoading.dismiss();
                ArrayList<Category> selectedCategories = new ArrayList();
                Iterator it = this.categories.iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getStatus() == 1) {
                        SlsMessageHolder.addToChannel(category.getChannelId(), category.getUserName());
                        selectedCategories.add(category);
                    } else {
                        MessagesController.getInstance().deleteUserFromChat(category.getChannelId(), UserConfig.getCurrentUser(), null);
                    }
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                return;
            case 21:
                this.categories = (ArrayList) object;
                this.adapter = new AdsChannelsAdapter(this.categories, this, this.recyclerView);
                AppPreferences.addAdsChannel(ApplicationLoader.applicationContext, this.categories);
                this.recyclerView.setAdapter(this.adapter);
                this.dialogLoading.dismiss();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                return;
            case 22:
                Log.d("alireza", "alireza");
                this.statistics = (Statistics) object;
                this.tvScore.setText((this.statistics.getJoinCount() + this.statistics.getUrlCount()) + "");
                this.dialogLoading.dismiss();
                return;
            default:
                return;
        }
    }

    private void showLoadingDialog(Context context, String title, String message) {
        this.dialogLoading = new ProgressDialog(getParentActivity());
        this.dialogLoading.setTitle(title);
        this.dialogLoading.setMessage(message);
        this.dialogLoading.show();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IntentMaker.goToChannel(-100, -100, getParentActivity(), ((Category) this.categories.get(i)).getUserName());
    }

    public void didReceivedNotification(int id, Object... args) {
    }
}
