package org.telegram.customization.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
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
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.Ads.Statistics;
import org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p156a.C2634a;
import org.telegram.customization.util.C2872c;
import org.telegram.customization.util.C2879f;
import org.telegram.customization.util.C2885i;
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
import p129d.p130a.p131a.p132a.C2339b.C2335b;
import p129d.p130a.p131a.p132a.C2339b.C2336c;
import utils.p178a.C3791b;
import utils.view.ToastUtil;

/* renamed from: org.telegram.customization.Activities.a */
public class C2529a extends BaseFragment implements OnClickListener, OnItemClickListener, C2497d, NotificationCenterDelegate {
    /* renamed from: a */
    View f8470a;
    /* renamed from: b */
    TextView f8471b;
    /* renamed from: c */
    RecyclerView f8472c;
    /* renamed from: d */
    C2634a f8473d;
    /* renamed from: e */
    ArrayList<Category> f8474e = new ArrayList();
    /* renamed from: f */
    ProgressDialog f8475f;
    /* renamed from: g */
    View f8476g;
    /* renamed from: h */
    View f8477h;
    /* renamed from: i */
    Statistics f8478i = new Statistics();

    /* renamed from: org.telegram.customization.Activities.a$1 */
    class C25241 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2529a f8464a;

        C25241(C2529a c2529a) {
            this.f8464a = c2529a;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8464a.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.a$2 */
    class C25262 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2529a f8466a;

        /* renamed from: org.telegram.customization.Activities.a$2$1 */
        class C25251 implements C2336c {
            /* renamed from: a */
            final /* synthetic */ C25262 f8465a;

            C25251(C25262 c25262) {
                this.f8465a = c25262;
            }

            public void onHidePrompt(MotionEvent motionEvent, boolean z) {
                C2885i.m13386f(this.f8465a.f8466a.getParentActivity(), false);
            }

            public void onHidePromptComplete() {
            }
        }

        C25262(C2529a c2529a) {
            this.f8466a = c2529a;
        }

        public void run() {
            if (C2885i.m13392l(ApplicationLoader.applicationContext)) {
                new C2335b(this.f8466a.getParentActivity()).m11623a(this.f8466a.f8470a).m11620a(8388613).m11621a(C2872c.m13346b(this.f8466a.getParentActivity())).m11627b(C2872c.m13349c(this.f8466a.getParentActivity())).m11625a(LocaleController.getString("Advertise", R.string.Advertise)).m11629b(C3791b.ae(ApplicationLoader.applicationContext)).m11624a(new C25251(this)).m11630b();
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.a$3 */
    class C25273 implements Runnable {
        /* renamed from: a */
        final /* synthetic */ C2529a f8467a;

        C25273(C2529a c2529a) {
            this.f8467a = c2529a;
        }

        public void run() {
            C2818c.m13087a(this.f8467a.getParentActivity(), this.f8467a).m13134f();
            C2818c.m13087a(this.f8467a.getParentActivity(), this.f8467a).m13132e();
        }
    }

    /* renamed from: a */
    private void m12294a(Context context, String str, String str2) {
        this.f8475f = new ProgressDialog(getParentActivity());
        this.f8475f.setTitle(str);
        this.f8475f.setMessage(str2);
        this.f8475f.show();
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("Advertise", R.string.Advertise));
        this.actionBar.setActionBarMenuOnItemClick(new C25241(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_ads_transaction, null);
        this.f8471b = (TextView) this.fragmentView.findViewById(R.id.tv_score);
        this.f8472c = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.f8472c.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.f8476g = this.fragmentView.findViewById(R.id.btn_score_report);
        this.f8476g.setOnClickListener(this);
        this.f8477h = this.fragmentView.findViewById(R.id.btn_ads_result);
        this.f8477h.setOnClickListener(this);
        try {
            new Handler().postDelayed(new C25262(this), 1000);
        } catch (Exception e) {
            e.printStackTrace();
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
            case R.id.btn_ads_result:
                Browser.openUrlSls(ApplicationLoader.applicationContext, Uri.parse(C3791b.ag(ApplicationLoader.applicationContext)), true);
                return;
            case R.id.btn_score_report:
                final Dialog dialog = new Dialog(getParentActivity());
                dialog.setContentView(R.layout.dialog_transaction);
                TextView textView = (TextView) dialog.findViewById(R.id.tv_join_count);
                ((TextView) dialog.findViewById(R.id.tv_link_count)).setText(this.f8478i.getUrlCount() + TtmlNode.ANONYMOUS_REGION_ID);
                textView.setText(this.f8478i.getJoinCount() + TtmlNode.ANONYMOUS_REGION_ID);
                dialog.findViewById(R.id.iv_close).setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C2529a f8469b;

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

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.refreshTabs);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.refreshTabs);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        C2879f.m13356a(-100, -100, getParentActivity(), ((Category) this.f8474e.get(i)).getUserName());
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case -22:
                this.f8475f.dismiss();
                return;
            case -20:
                ToastUtil.a(getParentActivity(), "خطا در ثبت اطلاعات").show();
                this.f8475f.dismiss();
                return;
            case 20:
                ToastUtil.a(getParentActivity(), "اطلاعات با موفقیت ثبت شد").show();
                this.f8475f.dismiss();
                ArrayList arrayList = new ArrayList();
                Iterator it = this.f8474e.iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getStatus() == 1) {
                        SlsMessageHolder.addToChannel(category.getChannelId(), category.getUserName());
                        arrayList.add(category);
                    } else {
                        MessagesController.getInstance().deleteUserFromChat(category.getChannelId(), UserConfig.getCurrentUser(), null);
                    }
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(-1));
                return;
            case 21:
                this.f8474e = (ArrayList) obj;
                this.f8473d = new C2634a(this.f8474e, this, this.f8472c);
                C3791b.m13935a(ApplicationLoader.applicationContext, this.f8474e);
                this.f8472c.setAdapter(this.f8473d);
                this.f8475f.dismiss();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.refreshTabs, Integer.valueOf(-1));
                return;
            case 22:
                Log.d("alireza", "alireza");
                this.f8478i = (Statistics) obj;
                this.f8471b.setText((this.f8478i.getJoinCount() + this.f8478i.getUrlCount()) + TtmlNode.ANONYMOUS_REGION_ID);
                this.f8475f.dismiss();
                return;
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
        m12294a(ApplicationLoader.applicationContext, LocaleController.getString("Advertise", R.string.Advertise), "لطفا منتظر بمانید");
        new Handler().postDelayed(new C25273(this), 500);
    }
}
