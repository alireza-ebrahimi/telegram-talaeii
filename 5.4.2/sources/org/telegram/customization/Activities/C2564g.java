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
import org.telegram.customization.Model.Payment.SettleHelper;
import org.telegram.customization.Model.Payment.SettleReport;
import org.telegram.customization.dynamicadapter.viewholder.ItemSettleHeaderViewBinder;
import org.telegram.customization.dynamicadapter.viewholder.ItemSettleViewBinder;
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

/* renamed from: org.telegram.customization.Activities.g */
public class C2564g extends BaseFragment implements OnClickListener, OnItemClickListener, C2035b, C2497d {
    /* renamed from: a */
    RecyclerView f8553a;
    /* renamed from: b */
    ProgressBar f8554b;
    /* renamed from: c */
    int f8555c = 1;
    /* renamed from: d */
    int f8556d = 100;
    /* renamed from: e */
    long f8557e = ((System.currentTimeMillis() - 2592000000L) / 1000);
    /* renamed from: f */
    long f8558f = (System.currentTimeMillis() / 1000);
    /* renamed from: g */
    ActionBarMenu f8559g;
    /* renamed from: h */
    TextView f8560h;
    /* renamed from: i */
    TextView f8561i;
    /* renamed from: j */
    View f8562j;
    /* renamed from: k */
    String f8563k = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: l */
    String f8564l = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: m */
    C5306e f8565m;
    /* renamed from: n */
    ArrayList<SettleHelper> f8566n = new ArrayList();

    /* renamed from: org.telegram.customization.Activities.g$1 */
    class C25631 extends ActionBarMenuOnItemClick {
        /* renamed from: a */
        final /* synthetic */ C2564g f8552a;

        /* renamed from: org.telegram.customization.Activities.g$1$1 */
        class C25591 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C25631 f8547a;

            /* renamed from: org.telegram.customization.Activities.g$1$1$1 */
            class C25581 implements C2035b {
                /* renamed from: a */
                final /* synthetic */ C25591 f8546a;

                C25581(C25591 c25591) {
                    this.f8546a = c25591;
                }

                /* renamed from: a */
                public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
                    this.f8546a.f8547a.f8552a.f8563k = i + "/" + i2 + "/" + i3;
                    this.f8546a.f8547a.f8552a.f8560h.setText(this.f8546a.f8547a.f8552a.f8563k);
                    C2880a b = C2881g.m13366b(new C2880a(i, i2, i3));
                    Calendar instance = Calendar.getInstance();
                    instance.set(1, b.m13358a());
                    instance.set(2, b.m13360b());
                    instance.set(5, b.m13362c());
                    this.f8546a.f8547a.f8552a.f8557e = instance.getTimeInMillis() / 1000;
                }
            }

            C25591(C25631 c25631) {
                this.f8547a = c25631;
            }

            public void onClick(View view) {
                C2018b c2018b = new C2018b(this.f8547a.f8552a.f8557e * 1000);
                C2036b a = C2036b.m9136a(this.f8547a.f8552a, c2018b.m9095b(), c2018b.m9096c(), c2018b.m9098e());
                a.show(this.f8547a.f8552a.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                a.m9146a(new C25581(this));
            }
        }

        /* renamed from: org.telegram.customization.Activities.g$1$2 */
        class C25612 implements OnClickListener {
            /* renamed from: a */
            final /* synthetic */ C25631 f8549a;

            /* renamed from: org.telegram.customization.Activities.g$1$2$1 */
            class C25601 implements C2035b {
                /* renamed from: a */
                final /* synthetic */ C25612 f8548a;

                C25601(C25612 c25612) {
                    this.f8548a = c25612;
                }

                /* renamed from: a */
                public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
                    this.f8548a.f8549a.f8552a.f8564l = i + "/" + i2 + "/" + i3;
                    this.f8548a.f8549a.f8552a.f8561i.setText(this.f8548a.f8549a.f8552a.f8564l);
                    C2880a b = C2881g.m13366b(new C2880a(i, i2, i3));
                    Calendar instance = Calendar.getInstance();
                    instance.set(1, b.m13358a());
                    instance.set(2, b.m13360b());
                    instance.set(5, b.m13362c());
                    this.f8548a.f8549a.f8552a.f8558f = instance.getTimeInMillis() / 1000;
                }
            }

            C25612(C25631 c25631) {
                this.f8549a = c25631;
            }

            public void onClick(View view) {
                C2018b c2018b = new C2018b(this.f8549a.f8552a.f8558f * 1000);
                C2036b a = C2036b.m9136a(this.f8549a.f8552a, c2018b.m9095b(), c2018b.m9096c(), c2018b.m9098e());
                a.show(this.f8549a.f8552a.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                a.m9146a(new C25601(this));
            }
        }

        C25631(C2564g c2564g) {
            this.f8552a = c2564g;
        }

        public void onItemClick(int i) {
            if (i == -1) {
                this.f8552a.finishFragment();
            } else if (i == 1000) {
                final Dialog dialog = new Dialog(this.f8552a.getParentActivity());
                dialog.setContentView(R.layout.dialog_date_filter);
                dialog.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
                this.f8552a.f8560h = (TextView) dialog.findViewById(R.id.tv_start_date);
                this.f8552a.f8561i = (TextView) dialog.findViewById(R.id.tv_end_date);
                this.f8552a.f8562j = dialog.findViewById(R.id.ftv_submit);
                if (!TextUtils.isEmpty(this.f8552a.f8563k)) {
                    this.f8552a.f8560h.setText(this.f8552a.f8563k);
                }
                if (!TextUtils.isEmpty(this.f8552a.f8564l)) {
                    this.f8552a.f8561i.setText(this.f8552a.f8564l);
                }
                dialog.findViewById(R.id.startDate).setOnClickListener(new C25591(this));
                dialog.findViewById(R.id.endDate).setOnClickListener(new C25612(this));
                dialog.show();
                this.f8552a.f8562j.setOnClickListener(new OnClickListener(this) {
                    /* renamed from: b */
                    final /* synthetic */ C25631 f8551b;

                    public void onClick(View view) {
                        this.f8551b.f8552a.f8555c = 1;
                        this.f8551b.f8552a.m12351a();
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    /* renamed from: a */
    private void m12351a() {
        this.f8554b.setVisibility(0);
        C2818c.m13087a(getParentActivity(), (C2497d) this).m13125b(this.f8555c, this.f8556d, this.f8557e, this.f8558f);
    }

    /* renamed from: a */
    public void mo3443a(C2036b c2036b, int i, int i2, int i3) {
    }

    public View createView(Context context) {
        this.f8559g = this.actionBar.createMenu();
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("settleListReport", R.string.settleListReport));
        this.f8559g.addItem(1000, ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.search_calendar));
        this.actionBar.setActionBarMenuOnItemClick(new C25631(this));
        this.fragmentView = new FrameLayout(context);
        ((FrameLayout) this.fragmentView).setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_transaction_list, null);
        this.f8553a = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.f8554b = (ProgressBar) this.fragmentView.findViewById(R.id.pb_loading);
        C2818c.m13087a(getParentActivity(), (C2497d) this).m13125b(this.f8555c, this.f8556d, this.f8557e, this.f8558f);
        this.f8553a.setLayoutManager(new LinearLayoutManager(ApplicationLoader.applicationContext));
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
        this.f8554b.setVisibility(8);
        switch (i) {
            case -34:
                ToastUtil.a(ApplicationLoader.applicationContext, "خطا در دریافت اطلاعات").show();
                return;
            case 34:
                this.f8566n = (ArrayList) obj;
                if (this.f8566n.size() > 0) {
                    this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(8);
                    List arrayList = new ArrayList();
                    Iterator it = this.f8566n.iterator();
                    while (it.hasNext()) {
                        SettleHelper settleHelper = (SettleHelper) it.next();
                        arrayList.add(settleHelper.getKey());
                        Iterator it2 = settleHelper.getValue().iterator();
                        while (it2.hasNext()) {
                            arrayList.add((SettleReport) it2.next());
                        }
                    }
                    this.f8565m = new C5306e(arrayList).a(new ItemSettleViewBinder()).a(new ItemSettleHeaderViewBinder());
                    this.f8553a.setAdapter(this.f8565m);
                    return;
                }
                this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(0);
                this.f8554b.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public void onResume() {
        super.onResume();
    }
}
