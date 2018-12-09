package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.C0357x;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.GooglePlayServicesUtilLight;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.gms.internal.wallet.zzak;
import com.google.android.gms.internal.wallet.zzl;
import com.google.android.gms.internal.wallet.zzp;
import com.google.android.gms.wallet.C1806R;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class SupportWalletFragment extends Fragment {
    private final Fragment fragment = this;
    private zzb zzfb;
    private boolean zzfc = false;
    private final SupportFragmentWrapper zzfd = SupportFragmentWrapper.wrap(this);
    private final zzc zzfe = new zzc();
    private zza zzff = new zza(this);
    private WalletFragmentOptions zzfg;
    private WalletFragmentInitParams zzfh;
    private MaskedWalletRequest zzfi;
    private MaskedWallet zzfj;
    private Boolean zzfk;

    public interface OnStateChangedListener {
        void onStateChanged(SupportWalletFragment supportWalletFragment, int i, int i2, Bundle bundle);
    }

    static class zza extends zzp {
        private OnStateChangedListener zzfl;
        private final SupportWalletFragment zzfm;

        zza(SupportWalletFragment supportWalletFragment) {
            this.zzfm = supportWalletFragment;
        }

        public final void zza(int i, int i2, Bundle bundle) {
            if (this.zzfl != null) {
                this.zzfl.onStateChanged(this.zzfm, i, i2, bundle);
            }
        }

        public final void zza(OnStateChangedListener onStateChangedListener) {
            this.zzfl = onStateChangedListener;
        }
    }

    private static class zzb implements LifecycleDelegate {
        private final zzl zzfn;

        private zzb(zzl zzl) {
            this.zzfn = zzl;
        }

        private final int getState() {
            try {
                return this.zzfn.getState();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.zzfn.initialize(walletFragmentInitParams);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void onActivityResult(int i, int i2, Intent intent) {
            try {
                this.zzfn.onActivityResult(i, i2, intent);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void setEnabled(boolean z) {
            try {
                this.zzfn.setEnabled(z);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void updateMaskedWallet(MaskedWallet maskedWallet) {
            try {
                this.zzfn.updateMaskedWallet(maskedWallet);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.zzfn.updateMaskedWalletRequest(maskedWalletRequest);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onCreate(Bundle bundle) {
            try {
                this.zzfn.onCreate(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) ObjectWrapper.unwrap(this.zzfn.onCreateView(ObjectWrapper.wrap(layoutInflater), ObjectWrapper.wrap(viewGroup), bundle));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onDestroy() {
        }

        public final void onDestroyView() {
        }

        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            try {
                this.zzfn.zza(ObjectWrapper.wrap(activity), (WalletFragmentOptions) bundle.getParcelable("extraWalletFragmentOptions"), bundle2);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onLowMemory() {
        }

        public final void onPause() {
            try {
                this.zzfn.onPause();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onResume() {
            try {
                this.zzfn.onResume();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzfn.onSaveInstanceState(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStart() {
            try {
                this.zzfn.onStart();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStop() {
            try {
                this.zzfn.onStop();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class zzc extends DeferredLifecycleHelper<zzb> implements OnClickListener {
        private final /* synthetic */ SupportWalletFragment zzfo;

        private zzc(SupportWalletFragment supportWalletFragment) {
            this.zzfo = supportWalletFragment;
        }

        protected final void createDelegate(OnDelegateCreatedListener<zzb> onDelegateCreatedListener) {
            Activity activity = this.zzfo.fragment.getActivity();
            if (this.zzfo.zzfb == null && this.zzfo.zzfc && activity != null) {
                try {
                    this.zzfo.zzfb = new zzb(zzak.zza(activity, this.zzfo.zzfd, this.zzfo.zzfg, this.zzfo.zzff));
                    this.zzfo.zzfg = null;
                    onDelegateCreatedListener.onDelegateCreated(this.zzfo.zzfb);
                    if (this.zzfo.zzfh != null) {
                        this.zzfo.zzfb.initialize(this.zzfo.zzfh);
                        this.zzfo.zzfh = null;
                    }
                    if (this.zzfo.zzfi != null) {
                        this.zzfo.zzfb.updateMaskedWalletRequest(this.zzfo.zzfi);
                        this.zzfo.zzfi = null;
                    }
                    if (this.zzfo.zzfj != null) {
                        this.zzfo.zzfb.updateMaskedWallet(this.zzfo.zzfj);
                        this.zzfo.zzfj = null;
                    }
                    if (this.zzfo.zzfk != null) {
                        this.zzfo.zzfb.setEnabled(this.zzfo.zzfk.booleanValue());
                        this.zzfo.zzfk = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }

        protected final void handleGooglePlayUnavailable(FrameLayout frameLayout) {
            int i = -1;
            int i2 = -2;
            View button = new Button(this.zzfo.fragment.getActivity());
            button.setText(C1806R.string.wallet_buy_button_place_holder);
            if (this.zzfo.zzfg != null) {
                WalletFragmentStyle fragmentStyle = this.zzfo.zzfg.getFragmentStyle();
                if (fragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzfo.fragment.getResources().getDisplayMetrics();
                    i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        public final void onClick(View view) {
            Context activity = this.zzfo.fragment.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(activity, 12451000), activity, -1);
        }
    }

    public static SupportWalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        SupportWalletFragment supportWalletFragment = new SupportWalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        supportWalletFragment.fragment.setArguments(bundle);
        return supportWalletFragment;
    }

    public final int getState() {
        return this.zzfb != null ? this.zzfb.getState() : 0;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzfb != null) {
            this.zzfb.initialize(walletFragmentInitParams);
            this.zzfh = null;
        } else if (this.zzfh == null) {
            this.zzfh = walletFragmentInitParams;
            if (this.zzfi != null) {
                Log.w("SupportWalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzfj != null) {
                Log.w("SupportWalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzfb != null) {
            this.zzfb.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzfh != null) {
                    Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.zzfh = walletFragmentInitParams;
            }
            if (this.zzfi == null) {
                this.zzfi = (MaskedWalletRequest) bundle.getParcelable("maskedWalletRequest");
            }
            if (this.zzfj == null) {
                this.zzfj = (MaskedWallet) bundle.getParcelable("maskedWallet");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.zzfg = (WalletFragmentOptions) bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.zzfk = Boolean.valueOf(bundle.getBoolean("enabled"));
            }
        } else if (this.fragment.getArguments() != null) {
            WalletFragmentOptions walletFragmentOptions = (WalletFragmentOptions) this.fragment.getArguments().getParcelable("extraWalletFragmentOptions");
            if (walletFragmentOptions != null) {
                walletFragmentOptions.zza(this.fragment.getActivity());
                this.zzfg = walletFragmentOptions;
            }
        }
        this.zzfc = true;
        this.zzfe.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzfe.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzfc = false;
    }

    public final void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.zzfg == null) {
            this.zzfg = WalletFragmentOptions.zza((Context) activity, attributeSet);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("attrKeyWalletFragmentOptions", this.zzfg);
        this.zzfe.onInflate(activity, bundle2, bundle);
    }

    public final void onPause() {
        super.onPause();
        this.zzfe.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.zzfe.onResume();
        C0357x e = this.fragment.getActivity().m1547e();
        Fragment a = e.mo283a(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (a != null) {
            e.mo284a().mo246a(a).mo244a();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(this.fragment.getActivity(), 12451000), this.fragment.getActivity(), -1);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzfe.onSaveInstanceState(bundle);
        if (this.zzfh != null) {
            bundle.putParcelable("walletFragmentInitParams", this.zzfh);
            this.zzfh = null;
        }
        if (this.zzfi != null) {
            bundle.putParcelable("maskedWalletRequest", this.zzfi);
            this.zzfi = null;
        }
        if (this.zzfj != null) {
            bundle.putParcelable("maskedWallet", this.zzfj);
            this.zzfj = null;
        }
        if (this.zzfg != null) {
            bundle.putParcelable("walletFragmentOptions", this.zzfg);
            this.zzfg = null;
        }
        if (this.zzfk != null) {
            bundle.putBoolean("enabled", this.zzfk.booleanValue());
            this.zzfk = null;
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzfe.onStart();
    }

    public final void onStop() {
        super.onStop();
        this.zzfe.onStop();
    }

    public final void setEnabled(boolean z) {
        if (this.zzfb != null) {
            this.zzfb.setEnabled(z);
            this.zzfk = null;
            return;
        }
        this.zzfk = Boolean.valueOf(z);
    }

    public final void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzff.zza(onStateChangedListener);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzfb != null) {
            this.zzfb.updateMaskedWallet(maskedWallet);
            this.zzfj = null;
            return;
        }
        this.zzfj = maskedWallet;
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzfb != null) {
            this.zzfb.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzfi = null;
            return;
        }
        this.zzfi = maskedWalletRequest;
    }
}
