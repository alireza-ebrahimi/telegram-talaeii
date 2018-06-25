package com.persianswitch.sdk.payment.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.C0235a;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.google.android.gms.common.internal.ImagesContract;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.BaseDialogFragment;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.utils.Spanny;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.net.MalformedURLException;
import java.net.URL;

public class VerificationSDKDialog extends BaseDialogFragment {
    /* renamed from: a */
    private String f7732a;
    /* renamed from: b */
    private String f7733b;
    /* renamed from: c */
    private String f7734c;
    /* renamed from: d */
    private String f7735d;
    /* renamed from: e */
    private boolean f7736e;
    /* renamed from: f */
    private boolean f7737f;
    /* renamed from: g */
    private TextView f7738g;
    /* renamed from: h */
    private TextView f7739h;

    /* renamed from: com.persianswitch.sdk.payment.payment.VerificationSDKDialog$1 */
    class C23201 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ VerificationSDKDialog f7730a;

        C23201(VerificationSDKDialog verificationSDKDialog) {
            this.f7730a = verificationSDKDialog;
        }

        public void onClick(View view) {
            this.f7730a.m11586c();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.VerificationSDKDialog$2 */
    class C23212 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ VerificationSDKDialog f7731a;

        C23212(VerificationSDKDialog verificationSDKDialog) {
            this.f7731a = verificationSDKDialog;
        }

        public void onClick(View view) {
            this.f7731a.m11587d();
        }
    }

    /* renamed from: c */
    private void m11586c() {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(this.f7734c));
        startActivity(intent);
    }

    /* renamed from: d */
    private void m11587d() {
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(this.f7733b))));
    }

    /* renamed from: a */
    public void mo3229a(Bundle bundle) {
    }

    /* renamed from: a */
    protected void mo3230a(View view, Bundle bundle) {
        int i = 0;
        this.f7732a = getArguments().getString("code");
        this.f7733b = getArguments().getString("ussdDial");
        this.f7734c = getArguments().getString(ImagesContract.URL);
        this.f7736e = getArguments().getBoolean("ussd_available");
        this.f7737f = getArguments().getBoolean("web_available");
        this.f7735d = getArguments().getString("desc");
        this.f7738g = (TextView) view.findViewById(C2262R.id.txt_auth_code);
        this.f7739h = (TextView) view.findViewById(C2262R.id.txt_description);
        FontManager.m10664a(view);
        int c = C0235a.m1075c(getActivity(), C2262R.color.asanpardakht_textHighlightColor);
        Object obj = null;
        try {
            obj = "https://" + new URL(this.f7734c).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.f7739h.setText(this.f7735d.replace("@#ussd#@", StringUtils.m10800a(this.f7733b)).replace("@#url#@", StringUtils.m10800a(obj)));
        this.f7738g.setText(new Spanny().m10769a(getString(C2262R.string.asanpardakht_verification_code)).m10769a(" : ").m10770a(this.f7732a, new ForegroundColorSpan(c)));
        boolean z = this.f7737f;
        View findViewById = view.findViewById(C2262R.id.btn_through_web);
        findViewById.setVisibility(z ? 0 : 8);
        findViewById.setOnClickListener(new C23201(this));
        z = this.f7736e;
        findViewById = view.findViewById(C2262R.id.btn_through_ussd);
        if (!z) {
            i = 8;
        }
        findViewById.setVisibility(i);
        findViewById.setOnClickListener(new C23212(this));
    }

    /* renamed from: b */
    protected int mo3231b() {
        return C2262R.layout.asanpardakht_dialog_trust;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(1, getTheme());
    }

    public void onSaveInstanceState(Bundle bundle) {
    }
}
