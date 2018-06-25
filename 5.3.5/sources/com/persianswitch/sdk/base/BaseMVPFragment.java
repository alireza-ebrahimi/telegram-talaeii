package com.persianswitch.sdk.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.BaseContract.ActionListener;
import com.persianswitch.sdk.base.BaseContract.View;
import com.persianswitch.sdk.base.manager.LanguageManager;
import java.lang.ref.WeakReference;

public abstract class BaseMVPFragment<P extends ActionListener> extends Fragment implements View<P> {
    private static Context applicationContext;
    private WeakReference<ProgressDialog> mOptProgress = new WeakReference(null);

    protected abstract int getLayoutResourceId();

    protected abstract void onFragmentCreated(android.view.View view, Bundle bundle);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LanguageManager.applyCurrentLocale(getActivity(), getActivity());
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResourceId(), container, false);
    }

    public void onViewCreated(android.view.View view, @Nullable Bundle savedInstanceState) {
        onFragmentCreated(view, savedInstanceState);
    }

    public void showProgress() {
        if (isAdded()) {
            dismissProgress();
            ProgressDialog progressDialog = new ProgressDialogBuilder().build();
            progressDialog.setStyle(2, C0770R.style.asanpardakht_SDKTheme_ProgressDialog);
            progressDialog.setCancelable(false);
            progressDialog.showAllowStateLoss(getFragmentManager(), "");
            this.mOptProgress = new WeakReference(progressDialog);
        }
    }

    public void dismissProgress() {
        if (isAdded()) {
            ProgressDialog dialog = (ProgressDialog) this.mOptProgress.get();
            if (isAdded() && dialog != null) {
                dialog.dismissAllowingStateLoss();
            }
        }
    }

    public Context getApplicationContext() {
        if (getContext() != null) {
            applicationContext = getContext().getApplicationContext();
            LanguageManager.localizeContext(applicationContext);
        }
        return applicationContext;
    }
}
