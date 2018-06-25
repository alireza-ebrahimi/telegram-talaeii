package com.google.android.gms.wallet.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.google.android.gms.C0489R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.dynamic.zzr;
import com.google.android.gms.internal.zzdmd;
import com.google.android.gms.internal.zzdmh;
import com.google.android.gms.internal.zzdnc;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

public final class SupportWalletFragment extends Fragment {
    private boolean mCreated = false;
    private final Fragment zzhdb = this;
    private zzb zzloo;
    private final zzr zzlop = zzr.zza(this);
    private final zzc zzloq = new zzc();
    private zza zzlor = new zza(this);
    private WalletFragmentOptions zzlos;
    private WalletFragmentInitParams zzlot;
    private MaskedWalletRequest zzlou;
    private MaskedWallet zzlov;
    private Boolean zzlow;

    public interface OnStateChangedListener {
        void onStateChanged(SupportWalletFragment supportWalletFragment, int i, int i2, Bundle bundle);
    }

    @Hide
    static class zza extends zzdmh {
        private OnStateChangedListener zzlox;
        private final SupportWalletFragment zzloy;

        zza(SupportWalletFragment supportWalletFragment) {
            this.zzloy = supportWalletFragment;
        }

        public final void zza(int i, int i2, Bundle bundle) {
            if (this.zzlox != null) {
                this.zzlox.onStateChanged(this.zzloy, i, i2, bundle);
            }
        }

        public final void zza(OnStateChangedListener onStateChangedListener) {
            this.zzlox = onStateChangedListener;
        }
    }

    static class zzb implements LifecycleDelegate {
        private final zzdmd zzloz;

        private zzb(zzdmd zzdmd) {
            this.zzloz = zzdmd;
        }

        private final int getState() {
            try {
                return this.zzloz.getState();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
            try {
                this.zzloz.initialize(walletFragmentInitParams);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void onActivityResult(int i, int i2, Intent intent) {
            try {
                this.zzloz.onActivityResult(i, i2, intent);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void setEnabled(boolean z) {
            try {
                this.zzloz.setEnabled(z);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void updateMaskedWallet(MaskedWallet maskedWallet) {
            try {
                this.zzloz.updateMaskedWallet(maskedWallet);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        private final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
            try {
                this.zzloz.updateMaskedWalletRequest(maskedWalletRequest);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onCreate(Bundle bundle) {
            try {
                this.zzloz.onCreate(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            try {
                return (View) zzn.zzy(this.zzloz.onCreateView(zzn.zzz(layoutInflater), zzn.zzz(viewGroup), bundle));
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
                this.zzloz.zza(zzn.zzz(activity), (WalletFragmentOptions) bundle.getParcelable("extraWalletFragmentOptions"), bundle2);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onLowMemory() {
        }

        public final void onPause() {
            try {
                this.zzloz.onPause();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onResume() {
            try {
                this.zzloz.onResume();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onSaveInstanceState(Bundle bundle) {
            try {
                this.zzloz.onSaveInstanceState(bundle);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStart() {
            try {
                this.zzloz.onStart();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        public final void onStop() {
            try {
                this.zzloz.onStop();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    class zzc extends com.google.android.gms.dynamic.zza<zzb> implements OnClickListener {
        private /* synthetic */ SupportWalletFragment zzlpa;

        private zzc(SupportWalletFragment supportWalletFragment) {
            this.zzlpa = supportWalletFragment;
        }

        public final void onClick(View view) {
            Context activity = this.zzlpa.zzhdb.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, -1);
        }

        protected final void zza(FrameLayout frameLayout) {
            int i = -1;
            int i2 = -2;
            View button = new Button(this.zzlpa.zzhdb.getActivity());
            button.setText(C0489R.string.wallet_buy_button_place_holder);
            if (this.zzlpa.zzlos != null) {
                WalletFragmentStyle fragmentStyle = this.zzlpa.zzlos.getFragmentStyle();
                if (fragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzlpa.zzhdb.getResources().getDisplayMetrics();
                    i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        protected final void zza(zzo<zzb> zzo) {
            Activity activity = this.zzlpa.zzhdb.getActivity();
            if (this.zzlpa.zzloo == null && this.zzlpa.mCreated && activity != null) {
                try {
                    this.zzlpa.zzloo = new zzb(zzdnc.zza(activity, this.zzlpa.zzlop, this.zzlpa.zzlos, this.zzlpa.zzlor));
                    this.zzlpa.zzlos = null;
                    zzo.zza(this.zzlpa.zzloo);
                    if (this.zzlpa.zzlot != null) {
                        this.zzlpa.zzloo.initialize(this.zzlpa.zzlot);
                        this.zzlpa.zzlot = null;
                    }
                    if (this.zzlpa.zzlou != null) {
                        this.zzlpa.zzloo.updateMaskedWalletRequest(this.zzlpa.zzlou);
                        this.zzlpa.zzlou = null;
                    }
                    if (this.zzlpa.zzlov != null) {
                        this.zzlpa.zzloo.updateMaskedWallet(this.zzlpa.zzlov);
                        this.zzlpa.zzlov = null;
                    }
                    if (this.zzlpa.zzlow != null) {
                        this.zzlpa.zzloo.setEnabled(this.zzlpa.zzlow.booleanValue());
                        this.zzlpa.zzlow = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }
    }

    public static SupportWalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        SupportWalletFragment supportWalletFragment = new SupportWalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        supportWalletFragment.zzhdb.setArguments(bundle);
        return supportWalletFragment;
    }

    public final int getState() {
        return this.zzloo != null ? this.zzloo.getState() : 0;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzloo != null) {
            this.zzloo.initialize(walletFragmentInitParams);
            this.zzlot = null;
        } else if (this.zzlot == null) {
            this.zzlot = walletFragmentInitParams;
            if (this.zzlou != null) {
                Log.w("SupportWalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzlov != null) {
                Log.w("SupportWalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzloo != null) {
            this.zzloo.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzlot != null) {
                    Log.w("SupportWalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
                }
                this.zzlot = walletFragmentInitParams;
            }
            if (this.zzlou == null) {
                this.zzlou = (MaskedWalletRequest) bundle.getParcelable("maskedWalletRequest");
            }
            if (this.zzlov == null) {
                this.zzlov = (MaskedWallet) bundle.getParcelable("maskedWallet");
            }
            if (bundle.containsKey("walletFragmentOptions")) {
                this.zzlos = (WalletFragmentOptions) bundle.getParcelable("walletFragmentOptions");
            }
            if (bundle.containsKey("enabled")) {
                this.zzlow = Boolean.valueOf(bundle.getBoolean("enabled"));
            }
        } else if (this.zzhdb.getArguments() != null) {
            WalletFragmentOptions walletFragmentOptions = (WalletFragmentOptions) this.zzhdb.getArguments().getParcelable("extraWalletFragmentOptions");
            if (walletFragmentOptions != null) {
                walletFragmentOptions.zzet(this.zzhdb.getActivity());
                this.zzlos = walletFragmentOptions;
            }
        }
        this.mCreated = true;
        this.zzloq.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzloq.onCreateView(layoutInflater, viewGroup, bundle);
    }

    public final void onDestroy() {
        super.onDestroy();
        this.mCreated = false;
    }

    public final void onInflate(Activity activity, AttributeSet attributeSet, Bundle bundle) {
        super.onInflate(activity, attributeSet, bundle);
        if (this.zzlos == null) {
            this.zzlos = WalletFragmentOptions.zza((Context) activity, attributeSet);
        }
        Bundle bundle2 = new Bundle();
        bundle2.putParcelable("attrKeyWalletFragmentOptions", this.zzlos);
        this.zzloq.onInflate(activity, bundle2, bundle);
    }

    public final void onPause() {
        super.onPause();
        this.zzloq.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.zzloq.onResume();
        FragmentManager supportFragmentManager = this.zzhdb.getActivity().getSupportFragmentManager();
        Fragment findFragmentByTag = supportFragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (findFragmentByTag != null) {
            supportFragmentManager.beginTransaction().remove(findFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzhdb.getActivity()), this.zzhdb.getActivity(), -1);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzloq.onSaveInstanceState(bundle);
        if (this.zzlot != null) {
            bundle.putParcelable("walletFragmentInitParams", this.zzlot);
            this.zzlot = null;
        }
        if (this.zzlou != null) {
            bundle.putParcelable("maskedWalletRequest", this.zzlou);
            this.zzlou = null;
        }
        if (this.zzlov != null) {
            bundle.putParcelable("maskedWallet", this.zzlov);
            this.zzlov = null;
        }
        if (this.zzlos != null) {
            bundle.putParcelable("walletFragmentOptions", this.zzlos);
            this.zzlos = null;
        }
        if (this.zzlow != null) {
            bundle.putBoolean("enabled", this.zzlow.booleanValue());
            this.zzlow = null;
        }
    }

    public final void onStart() {
        super.onStart();
        this.zzloq.onStart();
    }

    public final void onStop() {
        super.onStop();
        this.zzloq.onStop();
    }

    public final void setEnabled(boolean z) {
        if (this.zzloo != null) {
            this.zzloo.setEnabled(z);
            this.zzlow = null;
            return;
        }
        this.zzlow = Boolean.valueOf(z);
    }

    public final void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzlor.zza(onStateChangedListener);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzloo != null) {
            this.zzloo.updateMaskedWallet(maskedWallet);
            this.zzlov = null;
            return;
        }
        this.zzlov = maskedWallet;
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzloo != null) {
            this.zzloo.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzlou = null;
            return;
        }
        this.zzlou = maskedWalletRequest;
    }
}
