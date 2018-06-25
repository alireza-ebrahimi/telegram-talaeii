package org.telegram.customization.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Payment.Menu;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.util.IntentMaker;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;

public class PaymentMainActivity extends BaseFragment implements OnClickListener, IResponseReceiver, OnItemClickListener, NotificationCenterDelegate {
    Button btnRegisterOrCall;
    ArrayList<Category> categories = new ArrayList();
    ProgressDialog dialogLoading;
    boolean didCalled = false;
    LinearLayout llItemContainer;
    LinearLayout llMenuContainer;
    TextView tvStatus;
    User user;
    int userStatus;

    /* renamed from: org.telegram.customization.Activities.PaymentMainActivity$1 */
    class C10451 extends ActionBarMenuOnItemClick {
        C10451() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PaymentMainActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentMainActivity$2 */
    class C10462 implements OnClickListener {
        C10462() {
        }

        public void onClick(View view) {
            PaymentMainActivity.this.presentFragment(new PaymentTransactionListActivity());
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentMainActivity$3 */
    class C10473 implements OnClickListener {
        C10473() {
        }

        public void onClick(View view) {
            PaymentMainActivity.this.presentFragment(new PaymentSettleListActivity());
        }
    }

    public PaymentMainActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.userStatus = this.arguments.getInt("user_status", 0);
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
        this.actionBar.setTitle(LocaleController.getString("HotgramPayment", R.string.HotgramPayment));
        this.actionBar.setActionBarMenuOnItemClick(new C10451());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_main_payment, null);
        this.tvStatus = (TextView) this.fragmentView.findViewById(R.id.tv_user_status);
        this.btnRegisterOrCall = (Button) this.fragmentView.findViewById(R.id.btn_register_call);
        this.llMenuContainer = (LinearLayout) this.fragmentView.findViewById(R.id.ll_menu_container);
        this.llItemContainer = (LinearLayout) this.fragmentView.findViewById(R.id.ll_item_container);
        this.fragmentView.findViewById(R.id.btn_payment_report).setOnClickListener(new C10462());
        this.fragmentView.findViewById(R.id.btn_settle_report).setOnClickListener(new C10473());
        this.btnRegisterOrCall.setOnClickListener(this);
        if (this.userStatus == 81) {
            this.tvStatus.setText("شما هنوز در سامانه ثبت نام نکرده اید ");
            this.llItemContainer.setVisibility(8);
        } else if (this.userStatus == 80) {
            this.llItemContainer.setVisibility(8);
            this.tvStatus.setText("برای فعال سازی با کلیک بر روی دکمه زیر با سامانه تماس بگیرید");
        } else {
            this.tvStatus.setVisibility(8);
            this.btnRegisterOrCall.setVisibility(8);
        }
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
        HandleRequest.getNew(getParentActivity(), this).checkRegisterStatus();
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
            case R.id.btn_register_call:
                if (this.userStatus == 81) {
                    getParentActivity().startActivity(new Intent(ApplicationLoader.applicationContext, PaymentRegisterActivitySls.class));
                    return;
                } else if (this.userStatus == 80 && this.user != null) {
                    callViaPhone(this.user.getContactNumber());
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    private void callViaPhone(long contact) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts("tel", "0" + contact, null));
        intent.setFlags(268435456);
        ApplicationLoader.applicationContext.startActivity(intent);
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case 29:
                Log.d("alireza", "alireza");
                if (this.dialogLoading != null) {
                    this.dialogLoading.dismiss();
                }
                User tempUser = (User) object;
                if (tempUser != null) {
                    this.user = tempUser;
                    this.userStatus = this.user.getStatus();
                    if (this.userStatus == 81) {
                        this.tvStatus.setText("شما هنوز در سامانه ثبت نام نکرده اید ");
                        this.tvStatus.setVisibility(0);
                    } else if (this.userStatus == 80) {
                        this.tvStatus.setVisibility(0);
                        this.tvStatus.setText("برای فعال سازی با کلیک بر روی دکمه زیر با سامانه تماس بگیرید");
                    } else {
                        this.tvStatus.setVisibility(8);
                        this.btnRegisterOrCall.setVisibility(8);
                    }
                    if (this.user.getStatus() == 81) {
                        this.btnRegisterOrCall.setText(LocaleController.getString("signup", R.string.signup));
                        this.btnRegisterOrCall.setVisibility(0);
                        this.llItemContainer.setVisibility(8);
                        if (!TextUtils.isEmpty(this.user.getMessage())) {
                            this.tvStatus.setText(this.user.getMessage());
                            return;
                        }
                        return;
                    } else if (this.user.getStatus() == 80) {
                        this.btnRegisterOrCall.setText(LocaleController.getString("call", R.string.call));
                        this.btnRegisterOrCall.setVisibility(0);
                        if (!TextUtils.isEmpty(this.user.getMessage())) {
                            this.tvStatus.setText(this.user.getMessage());
                            return;
                        }
                        return;
                    } else {
                        this.tvStatus.setVisibility(8);
                        this.llItemContainer.setVisibility(0);
                        generateMenu(this.user.getMenus());
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    private void generateMenu(ArrayList<Menu> menus) {
        this.llMenuContainer.removeAllViews();
        Iterator it = menus.iterator();
        while (it.hasNext()) {
            Menu menu = (Menu) it.next();
            if (getParentActivity() != null) {
                View menuItem = getParentActivity().getLayoutInflater().inflate(R.layout.item_menu, null);
                TextView tvTitle = (TextView) menuItem.findViewById(R.id.tv_title);
                tvTitle.setText(menu.getTitle());
                tvTitle.setTag(Integer.valueOf(menu.getId()));
                this.llMenuContainer.addView(menuItem);
            }
        }
    }

    private void showLoadingDialog(Context context, String title, String message) {
        if (this.dialogLoading == null) {
            this.dialogLoading = new ProgressDialog(getParentActivity());
            this.dialogLoading.setTitle(title);
            this.dialogLoading.setMessage(message);
            this.dialogLoading.show();
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        IntentMaker.goToChannel(-100, -100, getParentActivity(), ((Category) this.categories.get(i)).getUserName());
    }

    public void didReceivedNotification(int id, Object... args) {
    }
}
