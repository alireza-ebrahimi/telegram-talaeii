package com.persianswitch.sdk.payment.common;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.BaseDialogFragment;
import com.persianswitch.sdk.base.BaseDialogFragment.BaseDialogInterface;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.utils.strings.StringUtils;

public class CommonDialog extends BaseDialogFragment {
    /* renamed from: a */
    private CommonDialogParam f7384a;

    /* renamed from: com.persianswitch.sdk.payment.common.CommonDialog$1 */
    class C22821 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ CommonDialog f7378a;

        C22821(CommonDialog commonDialog) {
            this.f7378a = commonDialog;
        }

        public void onClick(View view) {
            this.f7378a.m11055c();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.common.CommonDialog$2 */
    class C22832 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ CommonDialog f7379a;

        C22832(CommonDialog commonDialog) {
            this.f7379a = commonDialog;
        }

        public void onClick(View view) {
            this.f7379a.m11056d();
        }
    }

    public interface CommonDialogInterface extends BaseDialogInterface {
        /* renamed from: a */
        void mo3318a(CommonDialog commonDialog);

        /* renamed from: b */
        void mo3324b(CommonDialog commonDialog);
    }

    public static final class CommonDialogParam {
        /* renamed from: a */
        public String f7380a;
        /* renamed from: b */
        public String f7381b;
        /* renamed from: c */
        public String f7382c;
        /* renamed from: d */
        public String f7383d;

        /* renamed from: a */
        public static CommonDialogParam m11050a(Bundle bundle) {
            CommonDialogParam commonDialogParam = new CommonDialogParam();
            commonDialogParam.f7380a = bundle.getString("title");
            commonDialogParam.f7381b = bundle.getString("message");
            commonDialogParam.f7382c = bundle.getString("positive_button_text");
            commonDialogParam.f7383d = bundle.getString("negative_button_text");
            bundle.getString("title");
            return commonDialogParam;
        }

        /* renamed from: b */
        public void m11051b(Bundle bundle) {
            bundle.putString("title", this.f7380a);
            bundle.putString("message", this.f7381b);
            bundle.putString("positive_button_text", this.f7382c);
            bundle.putString("negative_button_text", this.f7383d);
        }
    }

    /* renamed from: a */
    public static CommonDialog m11052a(CommonDialogParam commonDialogParam) {
        CommonDialog commonDialog = new CommonDialog();
        Bundle bundle = new Bundle();
        commonDialogParam.m11051b(bundle);
        commonDialog.setArguments(bundle);
        return commonDialog;
    }

    /* renamed from: c */
    private void m11055c() {
        if (m10442a() instanceof CommonDialogInterface) {
            ((CommonDialogInterface) m10442a()).mo3318a(this);
        }
    }

    /* renamed from: d */
    private void m11056d() {
        if (m10442a() instanceof CommonDialogInterface) {
            ((CommonDialogInterface) m10442a()).mo3324b(this);
        }
    }

    /* renamed from: a */
    public void mo3229a(Bundle bundle) {
        if (bundle != null) {
            this.f7384a = CommonDialogParam.m11050a(bundle);
        }
    }

    /* renamed from: a */
    protected void mo3230a(View view, Bundle bundle) {
        FontManager.m10664a(view);
        ViewGroup viewGroup = (ViewGroup) view.findViewById(C2262R.id.lyt_title);
        TextView textView = (TextView) view.findViewById(C2262R.id.txt_title);
        TextView textView2 = (TextView) view.findViewById(C2262R.id.txt_message);
        Button button = (Button) view.findViewById(C2262R.id.btn_dialog_positive);
        Button button2 = (Button) view.findViewById(C2262R.id.btn_dialog_negative);
        if (getArguments() != null) {
            this.f7384a = CommonDialogParam.m11050a(getArguments());
        }
        if (StringUtils.m10803a(this.f7384a.f7382c)) {
            button.setVisibility(8);
        } else {
            button.setText(this.f7384a.f7382c);
        }
        if (StringUtils.m10803a(this.f7384a.f7383d)) {
            button2.setVisibility(8);
        } else {
            button2.setText(this.f7384a.f7383d);
        }
        button.setOnClickListener(new C22821(this));
        button2.setOnClickListener(new C22832(this));
        if (StringUtils.m10803a(this.f7384a.f7380a)) {
            viewGroup.setVisibility(8);
        } else {
            textView.setText(this.f7384a.f7380a);
            viewGroup.setVisibility(0);
        }
        textView2.setText(this.f7384a.f7381b);
    }

    /* renamed from: b */
    protected int mo3231b() {
        return C2262R.layout.asanpardakht_dialog_common;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(1, getTheme());
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.f7384a.m11051b(bundle);
    }
}
