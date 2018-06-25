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
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import org.ir.talaeii.R;
import org.telegram.customization.Adapters.AdsChannelsAdapter.OnQuickAccessClickListener;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Payment.PaymentReport;
import org.telegram.customization.Model.Payment.SettleHelper;
import org.telegram.customization.Model.Payment.SettleReport;
import org.telegram.customization.dynamicadapter.viewholder.ItemSettleHeaderViewBinder;
import org.telegram.customization.dynamicadapter.viewholder.ItemSettleViewBinder;
import org.telegram.customization.util.JalaliCalendar;
import org.telegram.customization.util.JalaliCalendar.YearMonthDate;
import org.telegram.customization.util.view.RecyclerSectionItemDecoration.SectionCallback;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import tellh.com.stickyheaderview_rv.adapter.DataBean;
import tellh.com.stickyheaderview_rv.adapter.StickyHeaderViewAdapter;
import utils.view.Constants;
import utils.view.ToastUtil;

public class PaymentSettleListActivity extends BaseFragment implements OnClickListener, OnQuickAccessClickListener, OnItemClickListener, IResponseReceiver, OnDateSetListener {
    StickyHeaderViewAdapter adapter;
    View btnSubmit;
    long endDate = (System.currentTimeMillis() / 1000);
    String endDateText = "";
    ActionBarMenu menu;
    int pageCount = 100;
    int pageIndex = 1;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    ArrayList<SettleHelper> reportHelpers = new ArrayList();
    long startDate = ((System.currentTimeMillis() - 2592000000L) / 1000);
    String startDateText = "";
    TextView tvEndDate;
    TextView tvStartDate;

    /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$1 */
    class C10571 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$1$1 */
        class C10531 implements OnClickListener {

            /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$1$1$1 */
            class C10521 implements OnDateSetListener {
                C10521() {
                }

                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    PaymentSettleListActivity.this.startDateText = year + "/" + monthOfYear + "/" + dayOfMonth;
                    PaymentSettleListActivity.this.tvStartDate.setText(PaymentSettleListActivity.this.startDateText);
                    YearMonthDate gregorian = JalaliCalendar.jalaliToGregorian(new YearMonthDate(year, monthOfYear, dayOfMonth));
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(1, gregorian.getYear());
                    calendar.set(2, gregorian.getMonth());
                    calendar.set(5, gregorian.getDate());
                    PaymentSettleListActivity.this.startDate = calendar.getTimeInMillis() / 1000;
                }
            }

            C10531() {
            }

            public void onClick(View view) {
                PersianCalendar persianCalendar = new PersianCalendar(PaymentSettleListActivity.this.startDate * 1000);
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(PaymentSettleListActivity.this, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                datePickerDialog.show(PaymentSettleListActivity.this.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                datePickerDialog.setOnDateSetListener(new C10521());
            }
        }

        /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$1$2 */
        class C10552 implements OnClickListener {

            /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$1$2$1 */
            class C10541 implements OnDateSetListener {
                C10541() {
                }

                public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                    PaymentSettleListActivity.this.endDateText = year + "/" + monthOfYear + "/" + dayOfMonth;
                    PaymentSettleListActivity.this.tvEndDate.setText(PaymentSettleListActivity.this.endDateText);
                    YearMonthDate gregorian = JalaliCalendar.jalaliToGregorian(new YearMonthDate(year, monthOfYear, dayOfMonth));
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(1, gregorian.getYear());
                    calendar.set(2, gregorian.getMonth());
                    calendar.set(5, gregorian.getDate());
                    PaymentSettleListActivity.this.endDate = calendar.getTimeInMillis() / 1000;
                }
            }

            C10552() {
            }

            public void onClick(View view) {
                PersianCalendar persianCalendar = new PersianCalendar(PaymentSettleListActivity.this.endDate * 1000);
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(PaymentSettleListActivity.this, persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                datePickerDialog.show(PaymentSettleListActivity.this.getParentActivity().getFragmentManager(), "StartDatepickerdialog");
                datePickerDialog.setOnDateSetListener(new C10541());
            }
        }

        C10571() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PaymentSettleListActivity.this.finishFragment();
            } else if (id == 1000) {
                final Dialog dialog = new Dialog(PaymentSettleListActivity.this.getParentActivity());
                dialog.setContentView(R.layout.dialog_date_filter);
                dialog.setTitle("");
                PaymentSettleListActivity.this.tvStartDate = (TextView) dialog.findViewById(R.id.tv_start_date);
                PaymentSettleListActivity.this.tvEndDate = (TextView) dialog.findViewById(R.id.tv_end_date);
                PaymentSettleListActivity.this.btnSubmit = dialog.findViewById(R.id.ftv_submit);
                if (!TextUtils.isEmpty(PaymentSettleListActivity.this.startDateText)) {
                    PaymentSettleListActivity.this.tvStartDate.setText(PaymentSettleListActivity.this.startDateText);
                }
                if (!TextUtils.isEmpty(PaymentSettleListActivity.this.endDateText)) {
                    PaymentSettleListActivity.this.tvEndDate.setText(PaymentSettleListActivity.this.endDateText);
                }
                dialog.findViewById(R.id.startDate).setOnClickListener(new C10531());
                dialog.findViewById(R.id.endDate).setOnClickListener(new C10552());
                dialog.show();
                PaymentSettleListActivity.this.btnSubmit.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        PaymentSettleListActivity.this.pageIndex = 1;
                        PaymentSettleListActivity.this.callWebservice();
                        dialog.dismiss();
                    }
                });
            }
        }
    }

    /* renamed from: org.telegram.customization.Activities.PaymentSettleListActivity$2 */
    class C10582 implements SectionCallback {
        C10582() {
        }

        public boolean isSection(int position) {
            return position == 0;
        }

        public CharSequence getSectionHeader(int position) {
            return "";
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
        this.menu = this.actionBar.createMenu();
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("settleListReport", R.string.settleListReport));
        this.menu.addItem(1000, ApplicationLoader.applicationContext.getResources().getDrawable(R.drawable.search_calendar));
        this.actionBar.setActionBarMenuOnItemClick(new C10571());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_transaction_list, null);
        this.recyclerView = (RecyclerView) this.fragmentView.findViewById(R.id.recycler);
        this.progressBar = (ProgressBar) this.fragmentView.findViewById(R.id.pb_loading);
        HandleRequest.getNew(getParentActivity(), this).getSettleReport(this.pageIndex, this.pageCount, this.startDate, this.endDate);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(ApplicationLoader.applicationContext));
        return this.fragmentView;
    }

    private void callWebservice() {
        this.progressBar.setVisibility(0);
        HandleRequest.getNew(getParentActivity(), this).getSettleReport(this.pageIndex, this.pageCount, this.startDate, this.endDate);
    }

    private SectionCallback getSectionCallback(List<PaymentReport> list) {
        return new C10582();
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
    }

    public void onClick(User user, TLRPC$Chat chat) {
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    }

    public void onResult(Object object, int StatusCode) {
        this.progressBar.setVisibility(8);
        switch (StatusCode) {
            case Constants.ERROR_SETTLE_REPORT /*-34*/:
                ToastUtil.AppToast(ApplicationLoader.applicationContext, "خطا در دریافت اطلاعات").show();
                return;
            case 34:
                this.reportHelpers = (ArrayList) object;
                if (this.reportHelpers.size() > 0) {
                    this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(8);
                    ArrayList<DataBean> dataBeans = new ArrayList();
                    Iterator it = this.reportHelpers.iterator();
                    while (it.hasNext()) {
                        SettleHelper reportHelper = (SettleHelper) it.next();
                        dataBeans.add(reportHelper.getKey());
                        Iterator it2 = reportHelper.getValue().iterator();
                        while (it2.hasNext()) {
                            dataBeans.add((SettleReport) it2.next());
                        }
                    }
                    this.adapter = new StickyHeaderViewAdapter(dataBeans).RegisterItemType(new ItemSettleViewBinder()).RegisterItemType(new ItemSettleHeaderViewBinder());
                    this.recyclerView.setAdapter(this.adapter);
                    return;
                }
                this.fragmentView.findViewById(R.id.tv_not_found).setVisibility(0);
                this.progressBar.setVisibility(8);
                return;
            default:
                return;
        }
    }

    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    }
}
