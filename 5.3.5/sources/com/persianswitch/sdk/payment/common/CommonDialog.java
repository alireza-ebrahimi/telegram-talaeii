package com.persianswitch.sdk.payment.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.BaseDialogFragment;
import com.persianswitch.sdk.base.BaseDialogFragment.BaseDialogInterface;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class CommonDialog extends BaseDialogFragment {
    private CommonDialogParam mDialogParam;

    /* renamed from: com.persianswitch.sdk.payment.common.CommonDialog$1 */
    class C07901 implements OnClickListener {
        C07901() {
        }

        public void onClick(View v) {
            CommonDialog.this.onPositiveClicked();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.common.CommonDialog$2 */
    class C07912 implements OnClickListener {
        C07912() {
        }

        public void onClick(View v) {
            CommonDialog.this.onNegativeClicked();
        }
    }

    public interface CommonDialogInterface extends BaseDialogInterface {
        void onCommonDialogNegativeClicked(CommonDialog commonDialog);

        void onCommonDialogPositiveClicked(CommonDialog commonDialog);
    }

    public static final class CommonDialogParam {
        private static final String MESSAGE = "message";
        public static final String NEGATIVE_BUTTON_TEXT = "negative_button_text";
        public static final String POSITIVE_BUTTON_TEXT = "positive_button_text";
        private static final String TITLE = "title";
        public String message;
        public String negativeTextButton;
        public String positiveTextButton;
        public String title;

        public static CommonDialogParam fromBundle(Bundle arguments) {
            CommonDialogParam dialogParam = new CommonDialogParam();
            dialogParam.title = arguments.getString("title");
            dialogParam.message = arguments.getString("message");
            dialogParam.positiveTextButton = arguments.getString(POSITIVE_BUTTON_TEXT);
            dialogParam.negativeTextButton = arguments.getString(NEGATIVE_BUTTON_TEXT);
            arguments.getString("title");
            return dialogParam;
        }

        public void injectToBundle(Bundle bundle) {
            bundle.putString("title", this.title);
            bundle.putString("message", this.message);
            bundle.putString(POSITIVE_BUTTON_TEXT, this.positiveTextButton);
            bundle.putString(NEGATIVE_BUTTON_TEXT, this.negativeTextButton);
        }
    }

    public static CommonDialog getInstance(CommonDialogParam dialogParam) {
        CommonDialog commonDialog = new CommonDialog();
        Bundle args = new Bundle();
        dialogParam.injectToBundle(args);
        commonDialog.setArguments(args);
        return commonDialog;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, getTheme());
    }

    protected int getLayoutResourceId() {
        return C0770R.layout.asanpardakht_dialog_common;
    }

    protected void onFragmentCreated(View view, Bundle savedInstanceState) {
        FontManager.overrideFonts(view);
        ViewGroup lytTitle = (ViewGroup) view.findViewById(C0770R.id.lyt_title);
        TextView txtTitle = (TextView) view.findViewById(C0770R.id.txt_title);
        TextView txtMessage = (TextView) view.findViewById(C0770R.id.txt_message);
        Button btnPositive = (Button) view.findViewById(C0770R.id.btn_dialog_positive);
        Button btnNegative = (Button) view.findViewById(C0770R.id.btn_dialog_negative);
        if (getArguments() != null) {
            this.mDialogParam = CommonDialogParam.fromBundle(getArguments());
        }
        if (StringUtils.isEmpty(this.mDialogParam.positiveTextButton)) {
            btnPositive.setVisibility(8);
        } else {
            btnPositive.setText(this.mDialogParam.positiveTextButton);
        }
        if (StringUtils.isEmpty(this.mDialogParam.negativeTextButton)) {
            btnNegative.setVisibility(8);
        } else {
            btnNegative.setText(this.mDialogParam.negativeTextButton);
        }
        btnPositive.setOnClickListener(new C07901());
        btnNegative.setOnClickListener(new C07912());
        if (StringUtils.isEmpty(this.mDialogParam.title)) {
            lytTitle.setVisibility(8);
        } else {
            txtTitle.setText(this.mDialogParam.title);
            lytTitle.setVisibility(0);
        }
        txtMessage.setText(this.mDialogParam.message);
    }

    private void onPositiveClicked() {
        if (getCallback() instanceof CommonDialogInterface) {
            ((CommonDialogInterface) getCallback()).onCommonDialogPositiveClicked(this);
        }
    }

    private void onNegativeClicked() {
        if (getCallback() instanceof CommonDialogInterface) {
            ((CommonDialogInterface) getCallback()).onCommonDialogNegativeClicked(this);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        this.mDialogParam.injectToBundle(outState);
    }

    public void onRecoverInstanceState(Bundle savedState) {
        if (savedState != null) {
            this.mDialogParam = CommonDialogParam.fromBundle(savedState);
        }
    }
}
