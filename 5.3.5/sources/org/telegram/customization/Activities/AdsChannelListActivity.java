package org.telegram.customization.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.AdsChannelsAdapter;
import org.telegram.customization.Adapters.AdsChannelsAdapter.OnQuickAccessClickListener;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.ToastUtil;

public class AdsChannelListActivity extends BaseFragment implements OnClickListener, OnQuickAccessClickListener, OnItemClickListener, IResponseReceiver {
    AdsChannelsAdapter adapter;
    View btnDone;
    ArrayList<Category> categories = new ArrayList();
    ProgressDialog dialogLoading;
    ProgressBar progressBar;
    RecyclerView recyclerView;

    /* renamed from: org.telegram.customization.Activities.AdsChannelListActivity$1 */
    class C10091 extends ActionBarMenuOnItemClick {
        C10091() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                AdsChannelListActivity.this.finishFragment();
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
        this.actionBar.setTitle(LocaleController.getString("AdvertiseChannels", R.string.AdvertiseChannels));
        this.actionBar.setActionBarMenuOnItemClick(new C10091());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_ads_channel_list, null);
        this.recyclerView = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.progressBar = (ProgressBar) this.fragmentView.findViewById(R.id.pb_loading);
        this.btnDone = this.fragmentView.findViewById(R.id.btn_done);
        this.btnDone.setOnClickListener(this);
        HandleRequest.getNew(getParentActivity(), this).getCategories();
        this.recyclerView.setLayoutManager(new LinearLayoutManager(context, 1, false));
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
            case R.id.btn_done:
                HandleRequest.getNew(getParentActivity(), this).manageCategory(this.categories);
                showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("AdvertiseChannels", R.string.AdvertiseChannels), "لطفا منتظر بمانید");
                return;
            default:
                return;
        }
    }

    public void onClick(User user, TLRPC$Chat chat) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (((Category) this.categories.get(i)).getStatus() == 1) {
            ((Category) this.categories.get(i)).setStatus(2);
        } else {
            ((Category) this.categories.get(i)).setStatus(1);
        }
        this.adapter.notifyItemChanged(i);
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
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
                AppPreferences.addAdsChannel(ApplicationLoader.applicationContext, selectedCategories);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, new Object[]{Integer.valueOf(-1)});
                finishFragment();
                return;
            case 21:
                this.categories = (ArrayList) object;
                this.adapter = new AdsChannelsAdapter(this.categories, this, this.recyclerView);
                this.recyclerView.setAdapter(this.adapter);
                this.progressBar.setVisibility(8);
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
}
