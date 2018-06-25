package org.telegram.customization.Activities;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2036b;
import com.mohamadamin.persianmaterialdatetimepicker.date.C2036b.C2035b;
import com.mohamadamin.persianmaterialdatetimepicker.p121a.C2018b;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Payment.PaymentReport;
import org.telegram.customization.Model.Payment.ReportHelper;
import org.telegram.customization.dynamicadapter.viewholder.ItemHeaderViewBinder;
import org.telegram.customization.dynamicadapter.viewholder.ItemReportViewBinder;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.C2881g;
import org.telegram.customization.util.C2881g.C2880a;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import tellh.com.stickyheaderview_rv.p179a.C5306e;
import utils.view.ToastUtil;

/* renamed from: org.telegram.customization.Activities.h */
public class C2571h extends BaseFragment implements OnClickListener, OnItemClickListener, C2035b, C2497d {
    /* renamed from: a */
    RecyclerView f8574a;
    /* renamed from: b */
    ProgressBar f8575b;
    /* renamed from: c */
    int f8576c = 1;
    /* renamed from: d */
    int f8577d = 100;
    /* renamed from: e */
    long f8578e = ((System.currentTimeMillis() - 2592000000L) / 1000);
    /* renamed from: f */
    long f8579f = (System.currentTimeMillis() / 1000);
    /* renamed from: g */
    ActionBarMenu f8580g;
    /* renamed from: h */
    String f8581h = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: i */
    String f8582i = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: j */
    TextView f8583j;
    /* renamed from: k */
    TextView f8584k;
    /* renamed from: l */
    View f8585l;
    /* renamed from: m */
    C5306e f8586m;
    /* renamed from: n */
    ArrayList<ReportHelper> f8587n = new ArrayList();

    /* renamed from: org.telegram.customization.Activities.h$1 */
    class C25701 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2571h f8573a;

        /* renamed from: org.telegram.customization.Activities.h$1$1 */
        class C25661 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C25701 f8568a;

            /* renamed from: org.telegram.customization.Activities.h$1$1$1 */
            class C25651 implements C2035b {
                /* renamed from: a */
                final /* synthetic */ C25661 f8567a;

                C25651(C25661 c25661) {
                    this.f8567a = c25661;
                }

                /* renamed from: a */
                public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
                    this.f8567a.f8568a.f8573a.f8581h = i + "/" + i2 + "/" + i3;
                    this.f8567a.f8568a.f8573a.f8583j.setText(this.f8567a.f8568a.f8573a.f8581h);
                    C2880a b = C2881g.m13366b(new C2880a(i, i2, i3));
                    Calendar instance = Calendar.getInstance();
                    instance.set(1, b.m13358a());
                    instance.set(2, b.m13360b());
                    instance.set(5, b.m13362c());
                    this.f8567a.f8568a.f8573a.f8578e = instance.getTimeInMillis() / 1000;
                }
            }

            C25661(C25701 c25701) {
                this.f8568a = c25701;
            }

            public void onClick(View view) {
                C2018b c2018b = new C2018b(this.f8568a.f8573a.f8578e * 1000);
                C2036b a = C2036b.m9136a(this.f8568a.f8573a, c2018b.m9095b(), c2018b.m9096c(), c2018b.m9098e());
                a.show(this.f8568a.f8573a.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                a.m9146a(new C25651(this));
            }
        }

        /* renamed from: org.telegram.customization.Activities.h$1$2 */
        class C25682 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C25701 f8570a;

            /* renamed from: org.telegram.customization.Activities.h$1$2$1 */
            class C25671 implements C2035b {
                /* renamed from: a */
                final /* synthetic */ C25682 f8569a;

                C25671(C25682 c25682) {
                    this.f8569a = c25682;
                }

                /* renamed from: a */
                public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
                    this.f8569a.f8570a.f8573a.f8582i = i + "/" + i2 + "/" + i3;
                    this.f8569a.f8570a.f8573a.f8584k.setText(this.f8569a.f8570a.f8573a.f8582i);
                    C2880a b = C2881g.m13366b(new C2880a(i, i2, i3));
                    Calendar instance = Calendar.getInstance();
                    instance.set(1, b.m13358a());
                    instance.set(2, b.m13360b());
                    instance.set(5, b.m13362c());
                    this.f8569a.f8570a.f8573a.f8579f = instance.getTimeInMillis() / 1000;
                }
            }

            C25682(C25701 c25701) {
                this.f8570a = c25701;
            }

            public void onClick(View view) {
                C2018b c2018b = new C2018b(this.f8570a.f8573a.f8579f * 1000);
                C2036b a = C2036b.m9136a(this.f8570a.f8573a, c2018b.m9095b(), c2018b.m9096c(), c2018b.m9098e());
                a.show(this.f8570a.f8573a.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                a.m9146a(new C25671(this));
            }
        }

        C25701(C2571h c2571h) {
            this.f8573a = c2571h;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8573a.finishFragment();
            } else if (i == 1000) {
                final Dialog dialog = new Dialog(this.f8573a.getParentActivity());
                dialog.setContentView(R.layout.dialog_date_filter);
                dialog.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
                this.f8573a.f8583j = (TextView) dialog.findViewById(R.id.tv_start_date);
                this.f8573a.f8584k = (TextView) dialog.findViewById(R.id.tv_end_date);
                this.f8573a.f8585l = dialog.findViewById(R.id.ftv_submit);
                if (!TextUtils.isEmpty(this.f8573a.f8581h)) {
                    this.f8573a.f8583j.setText(this.f8573a.f8581h);
                }
                if (!TextUtils.isEmpty(this.f8573a.f8582i)) {
                    this.f8573a.f8584k.setText(this.f8573a.f8582i);
                }
                dialog.findViewById(R.id.startDate).setOnClickListener(new C25661(this));
                dialog.findViewById(R.id.endDate).setOnClickListener(new C25682(this));
                dialog.show();
                this.f8573a.f8585l.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C25701 f8572b;

                    public void onClick(View view) {
                        this.f8572b.f8573a.f8576c = 1;
                        this.f8572b.f8573a.m12356a();
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    /* renamed from: a */
    private void m12356a() {
        this.f8575b.setVisibility(0);
        C2818c.m13087a(getParentActivity(), (C2497d) this).m13107a(this.f8576c, this.f8577d, this.f8578e, this.f8579f);
    }

    /* renamed from: a */
    public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
    }

    public View createView(Context context) {
        this.f8580g = this.actionBar.createMenu();
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("paymentList", R.string.paymentList));
        this.f8580g.addItem(1000, ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.search_calendar));
        this.actionBar.setActionBarMenuOnItemClick(new C25701(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_transaction_list, null);
        this.f8574a = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.f8575b = (ProgressBar) this.fragmentView.findViewById(R.id.pb_loading);
        C2818c.m13087a(getParentActivity(), (C2497d) this).m13107a(this.f8576c, this.f8577d, this.f8578e, this.f8579f);
        this.f8574a.setLayoutManager(new LinearLayoutManager(ApplicationLoader.applicationContext));
        return this.fragmentView;
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
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public void onResult(Object obj, int i) {
        this.f8575b.setVisibility(8);
        switch (i) {
            case -33:
                ToastUtil.a(ApplicationLoader.applicationContext, "خطا در دریافت اطلاعات").show();
                return;
            case 33:
                this.f8587n = (ArrayList) obj;
                if (this.f8587n.size() > 0) {
                    List arrayList = new ArrayList();
                    Iterator it = this.f8587n.iterator();
                    while (it.hasNext()) {
                        ReportHelper reportHelper = (ReportHelper) it.next();
                        arrayList.add(reportHelper);
                        Iterator it2 = reportHelper.getValue().iterator();
                        while (it2.hasNext()) {
                            arrayList.add((PaymentReport) it2.next());
                        }
                    }
                    this.f8586m = new C5306e(arrayList).a(new ItemReportViewBinder()).a(new ItemHeaderViewBinder());
                    this.f8574a.setAdapter(this.f8586m);
                    this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(8);
                    return;
                }
                this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(0);
                this.f8575b.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
    }
}
