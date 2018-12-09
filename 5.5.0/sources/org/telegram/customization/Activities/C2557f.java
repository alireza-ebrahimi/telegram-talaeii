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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Payment.Menu;
import org.telegram.customization.Model.Payment.User;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.C2879f;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;

/* renamed from: org.telegram.customization.Activities.f */
public class C2557f extends BaseFragment implements OnClickListener, OnItemClickListener, C2497d, NotificationCenterDelegate {
    /* renamed from: a */
    ArrayList<Category> f8537a = new ArrayList();
    /* renamed from: b */
    ProgressDialog f8538b;
    /* renamed from: c */
    LinearLayout f8539c;
    /* renamed from: d */
    TextView f8540d;
    /* renamed from: e */
    Button f8541e;
    /* renamed from: f */
    LinearLayout f8542f;
    /* renamed from: g */
    int f8543g;
    /* renamed from: h */
    boolean f8544h = false;
    /* renamed from: i */
    User f8545i;

    /* renamed from: org.telegram.customization.Activities.f$1 */
    class C25541 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2557f f8534a;

        C25541(C2557f c2557f) {
            this.f8534a = c2557f;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8534a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.f$2 */
    class C25552 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2557f f8535a;

        C25552(C2557f c2557f) {
            this.f8535a = c2557f;
        }

        public void onClick(View view) {
            this.f8535a.presentFragment(new C2571h());
        }
    }

    /* renamed from: org.telegram.customization.Activities.f$3 */
    class C25563 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ C2557f f8536a;

        C25563(C2557f c2557f) {
            this.f8536a = c2557f;
        }

        public void onClick(View view) {
            this.f8536a.presentFragment(new C2564g());
        }
    }

    public C2557f(Bundle bundle) {
        super(bundle);
    }

    /* renamed from: a */
    private void m12346a(long j) {
        Intent intent = new Intent("android.intent.action.DIAL", Uri.fromParts("tel", "0" + j, null));
        intent.setFlags(ErrorDialogData.BINDER_CRASH);
        ApplicationLoader.applicationContext.startActivity(intent);
    }

    /* renamed from: a */
    private void m12347a(Context context, String str, String str2) {
        if (this.f8538b == null) {
            this.f8538b = new ProgressDialog(getParentActivity());
            this.f8538b.setTitle(str);
            this.f8538b.setMessage(str2);
            this.f8538b.show();
        }
    }

    /* renamed from: a */
    private void m12348a(ArrayList<Menu> arrayList) {
        this.f8539c.removeAllViews();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Menu menu = (Menu) it.next();
            if (getParentActivity() != null) {
                View inflate = getParentActivity().getLayoutInflater().inflate(R.layout.item_menu, null);
                TextView textView = (TextView) inflate.findViewById(R.id.tv_title);
                textView.setText(menu.getTitle());
                textView.setTag(Integer.valueOf(menu.getId()));
                this.f8539c.addView(inflate);
            }
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("HotgramPayment", R.string.HotgramPayment));
        this.actionBar.setActionBarMenuOnItemClick(new C25541(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_main_payment, null);
        this.f8540d = (TextView) this.fragmentView.findViewById(R.id.tv_user_status);
        this.f8541e = (Button) this.fragmentView.findViewById(R.id.btn_register_call);
        this.f8539c = (LinearLayout) this.fragmentView.findViewById(R.id.ll_menu_container);
        this.f8542f = (LinearLayout) this.fragmentView.findViewById(R.id.ll_item_container);
        this.fragmentView.findViewById(R.id.btn_payment_report).setOnClickListener(new C25552(this));
        this.fragmentView.findViewById(R.id.btn_settle_report).setOnClickListener(new C25563(this));
        this.f8541e.setOnClickListener(this);
        if (this.f8543g == 81) {
            this.f8540d.setText("شما هنوز در سامانه ثبت نام نکرده اید ");
            this.f8542f.setVisibility(8);
        } else if (this.f8543g == 80) {
            this.f8542f.setVisibility(8);
            this.f8540d.setText("برای فعال سازی با کلیک بر روی دکمه زیر با سامانه تماس بگیرید");
        } else {
            this.f8540d.setVisibility(8);
            this.f8541e.setVisibility(8);
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
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
                if (this.f8543g == 81) {
                    getParentActivity().startActivity(new Intent(ApplicationLoader.applicationContext, PaymentRegisterActivitySls.class));
                    return;
                } else if (this.f8543g == 80 && this.f8545i != null) {
                    m12346a(this.f8545i.getContactNumber());
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.f8543g = this.arguments.getInt("user_status", 0);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        C2879f.m13356a(-100, -100, getParentActivity(), ((Category) this.f8537a.get(i)).getUserName());
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case 29:
                Log.d("alireza", "alireza");
                if (this.f8538b != null) {
                    this.f8538b.dismiss();
                }
                User user = (User) obj;
                if (user != null) {
                    this.f8545i = user;
                    this.f8543g = this.f8545i.getStatus();
                    if (this.f8543g == 81) {
                        this.f8540d.setText("شما هنوز در سامانه ثبت نام نکرده اید ");
                        this.f8540d.setVisibility(0);
                    } else if (this.f8543g == 80) {
                        this.f8540d.setVisibility(0);
                        this.f8540d.setText("برای فعال سازی با کلیک بر روی دکمه زیر با سامانه تماس بگیرید");
                    } else {
                        this.f8540d.setVisibility(8);
                        this.f8541e.setVisibility(8);
                    }
                    if (this.f8545i.getStatus() == 81) {
                        this.f8541e.setText(LocaleController.getString("signup", R.string.signup));
                        this.f8541e.setVisibility(0);
                        this.f8542f.setVisibility(8);
                        if (!TextUtils.isEmpty(this.f8545i.getMessage())) {
                            this.f8540d.setText(this.f8545i.getMessage());
                            return;
                        }
                        return;
                    } else if (this.f8545i.getStatus() == 80) {
                        this.f8541e.setText(LocaleController.getString("call", R.string.call));
                        this.f8541e.setVisibility(0);
                        if (!TextUtils.isEmpty(this.f8545i.getMessage())) {
                            this.f8540d.setText(this.f8545i.getMessage());
                            return;
                        }
                        return;
                    } else {
                        this.f8540d.setVisibility(8);
                        this.f8542f.setVisibility(0);
                        m12348a(this.f8545i.getMenus());
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
        m12347a(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
        C2818c.m13087a(getParentActivity(), (C2497d) this).m13142j();
    }
}
