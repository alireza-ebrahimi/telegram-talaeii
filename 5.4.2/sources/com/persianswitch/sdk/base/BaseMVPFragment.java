package com.persianswitch.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.BaseContract.ActionListener;
import com.persianswitch.sdk.base.BaseContract.View;
import com.persianswitch.sdk.base.manager.LanguageManager;
import java.lang.ref.WeakReference;

public abstract class BaseMVPFragment<P extends ActionListener> extends Fragment implements View<P> {
    /* renamed from: a */
    private static Context f6991a;
    /* renamed from: b */
    private WeakReference<ProgressDialog> f6992b = new WeakReference(null);

    /* renamed from: a */
    public Context mo3228a() {
        if (getContext() != null) {
            f6991a = getContext().getApplicationContext();
            LanguageManager.m10673b(f6991a);
        }
        return f6991a;
    }

    /* renamed from: a */
    protected abstract void mo3316a(android.view.View view, Bundle bundle);

    /* renamed from: b */
    public void m10449b() {
        if (isAdded()) {
            m10450c();
            ProgressDialog a = new ProgressDialogBuilder().m10490a();
            a.setStyle(2, C2262R.style.asanpardakht_SDKTheme_ProgressDialog);
            a.setCancelable(false);
            a.m10444a(getFragmentManager(), TtmlNode.ANONYMOUS_REGION_ID);
            this.f6992b = new WeakReference(a);
        }
    }

    /* renamed from: c */
    public void m10450c() {
        if (isAdded()) {
            ProgressDialog progressDialog = (ProgressDialog) this.f6992b.get();
            if (isAdded() && progressDialog != null) {
                progressDialog.dismissAllowingStateLoss();
            }
        }
    }

    /* renamed from: d */
    protected abstract int mo3330d();

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        LanguageManager.m10671a(getActivity(), getActivity());
    }

    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(mo3330d(), viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        mo3316a(view, bundle);
    }
}
