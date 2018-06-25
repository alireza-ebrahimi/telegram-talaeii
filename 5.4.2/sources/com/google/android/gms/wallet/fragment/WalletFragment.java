package com.google.android.gms.wallet.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.dynamic.FragmentWrapper;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.internal.wallet.zzak;
import com.google.android.gms.internal.wallet.zzl;
import com.google.android.gms.internal.wallet.zzp;
import com.google.android.gms.wallet.C1806R;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

@TargetApi(12)
public final class WalletFragment extends Fragment {
    private boolean zzfc = false;
    private WalletFragmentOptions zzfg;
    private WalletFragmentInitParams zzfh;
    private MaskedWalletRequest zzfi;
    private MaskedWallet zzfj;
    private Boolean zzfk;
    private zzb zzfp;
    private final FragmentWrapper zzfq = FragmentWrapper.wrap(this);
    private final zzc zzfr = new zzc();
    private zza zzfs = new zza(this);
    private final Fragment zzft = this;

    public interface OnStateChangedListener {
        void onStateChanged(WalletFragment walletFragment, int i, int i2, Bundle bundle);
    }

    static class zza extends zzp {
        private OnStateChangedListener zzfu;
        private final WalletFragment zzfv;

        zza(WalletFragment walletFragment) {
            this.zzfv = walletFragment;
        }

        public final void zza(int i, int i2, Bundle bundle) {
            if (this.zzfu != null) {
                this.zzfu.onStateChanged(this.zzfv, i, i2, bundle);
            }
        }

        public final void zza(OnStateChangedListener onStateChangedListener) {
            this.zzfu = onStateChangedListener;
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
        private final /* synthetic */ WalletFragment zzfw;

        private zzc(WalletFragment walletFragment) {
            this.zzfw = walletFragment;
        }

        protected final void createDelegate(OnDelegateCreatedListener<zzb> onDelegateCreatedListener) {
            Activity activity = this.zzfw.zzft.getActivity();
            if (this.zzfw.zzfp == null && this.zzfw.zzfc && activity != null) {
                try {
                    this.zzfw.zzfp = new zzb(zzak.zza(activity, this.zzfw.zzfq, this.zzfw.zzfg, this.zzfw.zzfs));
                    this.zzfw.zzfg = null;
                    onDelegateCreatedListener.onDelegateCreated(this.zzfw.zzfp);
                    if (this.zzfw.zzfh != null) {
                        this.zzfw.zzfp.initialize(this.zzfw.zzfh);
                        this.zzfw.zzfh = null;
                    }
                    if (this.zzfw.zzfi != null) {
                        this.zzfw.zzfp.updateMaskedWalletRequest(this.zzfw.zzfi);
                        this.zzfw.zzfi = null;
                    }
                    if (this.zzfw.zzfj != null) {
                        this.zzfw.zzfp.updateMaskedWallet(this.zzfw.zzfj);
                        this.zzfw.zzfj = null;
                    }
                    if (this.zzfw.zzfk != null) {
                        this.zzfw.zzfp.setEnabled(this.zzfw.zzfk.booleanValue());
                        this.zzfw.zzfk = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }

        protected final void handleGooglePlayUnavailable(FrameLayout frameLayout) {
            int i = -1;
            int i2 = -2;
            View button = new Button(this.zzfw.zzft.getActivity());
            button.setText(C1806R.string.wallet_buy_button_place_holder);
            if (this.zzfw.zzfg != null) {
                WalletFragmentStyle fragmentStyle = this.zzfw.zzfg.getFragmentStyle();
                if (fragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzfw.zzft.getResources().getDisplayMetrics();
                    i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        public final void onClick(View view) {
            Context activity = this.zzfw.zzft.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(activity, 12451000), activity, -1);
        }
    }

    public static WalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        WalletFragment walletFragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        walletFragment.zzft.setArguments(bundle);
        return walletFragment;
    }

    public final int getState() {
        return this.zzfp != null ? this.zzfp.getState() : 0;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzfp != null) {
            this.zzfp.initialize(walletFragmentInitParams);
            this.zzfh = null;
        } else if (this.zzfh == null) {
            this.zzfh = walletFragmentInitParams;
            if (this.zzfi != null) {
                Log.w("WalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzfj != null) {
                Log.w("WalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzfp != null) {
            this.zzfp.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzfh != null) {
                    Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
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
        } else if (this.zzft.getArguments() != null) {
            WalletFragmentOptions walletFragmentOptions = (WalletFragmentOptions) this.zzft.getArguments().getParcelable("extraWalletFragmentOptions");
            if (walletFragmentOptions != null) {
                walletFragmentOptions.zza(this.zzft.getActivity());
                this.zzfg = walletFragmentOptions;
            }
        }
        this.zzfc = true;
        this.zzfr.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzfr.onCreateView(layoutInflater, viewGroup, bundle);
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
        this.zzfr.onInflate(activity, bundle2, bundle);
    }

    public final void onPause() {
        super.onPause();
        this.zzfr.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.zzfr.onResume();
        FragmentManager fragmentManager = this.zzft.getActivity().getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (findFragmentByTag != null) {
            fragmentManager.beginTransaction().remove(findFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtilLight.isGooglePlayServicesAvailable(this.zzft.getActivity(), 12451000), this.zzft.getActivity(), -1);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzfr.onSaveInstanceState(bundle);
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
        this.zzfr.onStart();
    }

    public final void onStop() {
        super.onStop();
        this.zzfr.onStop();
    }

    public final void setEnabled(boolean z) {
        if (this.zzfp != null) {
            this.zzfp.setEnabled(z);
            this.zzfk = null;
            return;
        }
        this.zzfk = Boolean.valueOf(z);
    }

    public final void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzfs.zza(onStateChangedListener);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzfp != null) {
            this.zzfp.updateMaskedWallet(maskedWallet);
            this.zzfj = null;
            return;
        }
        this.zzfj = maskedWallet;
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzfp != null) {
            this.zzfp.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzfi = null;
            return;
        }
        this.zzfi = maskedWalletRequest;
    }
}
