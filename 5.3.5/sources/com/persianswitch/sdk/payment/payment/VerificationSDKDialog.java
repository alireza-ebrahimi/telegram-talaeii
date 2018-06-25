package com.persianswitch.sdk.payment.payment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.BaseDialogFragment;
import com.persianswitch.sdk.base.manager.FontManager;
import com.persianswitch.sdk.base.utils.Spanny;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import java.net.MalformedURLException;
import java.net.URL;

public class VerificationSDKDialog extends BaseDialogFragment {
    private String mDescription;
    private String mTrustCode;
    private TextView mTxtAuthenticateCode;
    private TextView mTxtDescription;
    private boolean mUSSDOriginalityAvailable;
    private String mUssdDial;
    private boolean mWebOriginalityAvailable;
    private String mWebURL;

    /* renamed from: com.persianswitch.sdk.payment.payment.VerificationSDKDialog$1 */
    class C08281 implements OnClickListener {
        C08281() {
        }

        public void onClick(View view) {
            VerificationSDKDialog.this.onThroughWebClicked();
        }
    }

    /* renamed from: com.persianswitch.sdk.payment.payment.VerificationSDKDialog$2 */
    class C08292 implements OnClickListener {
        C08292() {
        }

        public void onClick(View view) {
            VerificationSDKDialog.this.onThroughUSSD();
        }
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(1, getTheme());
    }

    protected int getLayoutResourceId() {
        return C0770R.layout.asanpardakht_dialog_trust;
    }

    protected void onFragmentCreated(View view, Bundle savedInstanceState) {
        int i;
        int i2 = 0;
        this.mTrustCode = getArguments().getString("code");
        this.mUssdDial = getArguments().getString("ussdDial");
        this.mWebURL = getArguments().getString("url");
        this.mUSSDOriginalityAvailable = getArguments().getBoolean("ussd_available");
        this.mWebOriginalityAvailable = getArguments().getBoolean("web_available");
        this.mDescription = getArguments().getString("desc");
        this.mTxtAuthenticateCode = (TextView) view.findViewById(C0770R.id.txt_auth_code);
        this.mTxtDescription = (TextView) view.findViewById(C0770R.id.txt_description);
        FontManager.overrideFonts(view);
        int highlightColor = ContextCompat.getColor(getActivity(), C0770R.color.asanpardakht_textHighlightColor);
        String hostName = null;
        try {
            hostName = "https://" + new URL(this.mWebURL).getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        this.mTxtDescription.setText(this.mDescription.replace("@#ussd#@", StringUtils.toNonNullString(this.mUssdDial)).replace("@#url#@", StringUtils.toNonNullString(hostName)));
        this.mTxtAuthenticateCode.setText(new Spanny().append(getString(C0770R.string.asanpardakht_verification_code)).append((CharSequence) " : ").append(this.mTrustCode, new ForegroundColorSpan(highlightColor)));
        boolean webOriginality = this.mWebOriginalityAvailable;
        View btnThroughWeb = view.findViewById(C0770R.id.btn_through_web);
        if (webOriginality) {
            i = 0;
        } else {
            i = 8;
        }
        btnThroughWeb.setVisibility(i);
        btnThroughWeb.setOnClickListener(new C08281());
        boolean ussdOriginality = this.mUSSDOriginalityAvailable;
        View btnThroughUSSD = view.findViewById(C0770R.id.btn_through_ussd);
        if (!ussdOriginality) {
            i2 = 8;
        }
        btnThroughUSSD.setVisibility(i2);
        btnThroughUSSD.setOnClickListener(new C08292());
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRecoverInstanceState(Bundle savedState) {
    }

    private void onThroughWebClicked() {
        Intent i = new Intent("android.intent.action.VIEW");
        i.setData(Uri.parse(this.mWebURL));
        startActivity(i);
    }

    private void onThroughUSSD() {
        startActivity(new Intent("android.intent.action.DIAL", Uri.parse("tel:" + Uri.encode(this.mUssdDial))));
    }
}
