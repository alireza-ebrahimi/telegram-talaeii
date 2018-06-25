package org.telegram.customization.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;

public class PaymentCallbackActivity extends BaseFragment implements OnClickListener, IResponseReceiver {
    Button btnSubmit;
    ProgressDialog dialogLoading;
    View itemDescription;
    View itemIssueTracking;
    View itemPaymentId;
    View itemStatus;
    ProgressBar progressBar;
    TextView tvAmount;
    TextView tvDate;

    /* renamed from: org.telegram.customization.Activities.PaymentCallbackActivity$1 */
    class C10411 extends ActionBarMenuOnItemClick {
        C10411() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                PaymentCallbackActivity.this.finishFragment();
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
        this.actionBar.setActionBarMenuOnItemClick(new C10411());
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.activity_payment_callback, null);
        this.progressBar = (ProgressBar) this.fragmentView.findViewById(R.id.pb_loading);
        this.tvDate = (TextView) this.fragmentView.findViewById(R.id.tv_date);
        this.itemPaymentId = this.fragmentView.findViewById(R.id.item_id);
        this.itemIssueTracking = this.fragmentView.findViewById(R.id.item_issue_tracking);
        this.itemDescription = this.fragmentView.findViewById(R.id.item_desc);
        this.itemStatus = this.fragmentView.findViewById(R.id.item_status);
        this.tvAmount = (TextView) this.fragmentView.findViewById(R.id.tv_amount);
        this.btnSubmit = (Button) this.fragmentView.findViewById(R.id.btn_submit);
        HandleRequest.getNew(getParentActivity(), this).getCategories();
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
    }

    public void onResult(Object object, int StatusCode) {
    }

    private void showLoadingDialog(Context context, String title, String message) {
        this.dialogLoading = new ProgressDialog(getParentActivity());
        this.dialogLoading.setTitle(title);
        this.dialogLoading.setMessage(message);
        this.dialogLoading.show();
    }
}
