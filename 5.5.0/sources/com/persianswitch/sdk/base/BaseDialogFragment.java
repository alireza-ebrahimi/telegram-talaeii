package com.persianswitch.sdk.base;

import android.os.Bundle;
import android.support.v4.app.C0348s;
import android.support.v4.app.C0357x;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public abstract class BaseDialogFragment extends C0348s {

    public interface BaseDialogInterface {
    }

    /* renamed from: a */
    public static void m10441a(C0357x c0357x) {
        List<Fragment> c = c0357x.mo289c();
        if (c != null) {
            for (Fragment fragment : c) {
                if (fragment instanceof C0348s) {
                    ((C0348s) fragment).dismissAllowingStateLoss();
                }
            }
        }
    }

    /* renamed from: a */
    public BaseDialogInterface m10442a() {
        return getTargetFragment() instanceof BaseDialogInterface ? (BaseDialogInterface) getTargetFragment() : getActivity() instanceof BaseDialogInterface ? (BaseDialogInterface) getActivity() : null;
    }

    /* renamed from: a */
    public abstract void mo3229a(Bundle bundle);

    /* renamed from: a */
    public void m10444a(C0357x c0357x, String str) {
        c0357x.mo284a().mo247a((Fragment) this, str).mo249b();
    }

    /* renamed from: a */
    protected abstract void mo3230a(View view, Bundle bundle);

    /* renamed from: b */
    protected abstract int mo3231b();

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        mo3229a(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(mo3231b(), viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        mo3230a(view, bundle);
        mo3229a(bundle);
    }
}
