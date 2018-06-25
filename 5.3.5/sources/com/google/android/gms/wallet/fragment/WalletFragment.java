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
import com.google.android.gms.C0489R;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.LifecycleDelegate;
import com.google.android.gms.dynamic.zzj;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.internal.zzdmd;
import com.google.android.gms.internal.zzdmh;
import com.google.android.gms.internal.zzdnc;
import com.google.android.gms.wallet.MaskedWallet;
import com.google.android.gms.wallet.MaskedWalletRequest;

@TargetApi(12)
public final class WalletFragment extends Fragment {
    private boolean mCreated = false;
    private final Fragment zzhcy = this;
    private WalletFragmentOptions zzlos;
    private WalletFragmentInitParams zzlot;
    private MaskedWalletRequest zzlou;
    private MaskedWallet zzlov;
    private Boolean zzlow;
    private zzb zzlpb;
    private final zzj zzlpc = zzj.zza(this);
    private final zzc zzlpd = new zzc();
    private zza zzlpe = new zza(this);

    public interface OnStateChangedListener {
        void onStateChanged(WalletFragment walletFragment, int i, int i2, Bundle bundle);
    }

    @Hide
    static class zza extends zzdmh {
        private OnStateChangedListener zzlpf;
        private final WalletFragment zzlpg;

        zza(WalletFragment walletFragment) {
            this.zzlpg = walletFragment;
        }

        public final void zza(int i, int i2, Bundle bundle) {
            if (this.zzlpf != null) {
                this.zzlpf.onStateChanged(this.zzlpg, i, i2, bundle);
            }
        }

        public final void zza(OnStateChangedListener onStateChangedListener) {
            this.zzlpf = onStateChangedListener;
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
        private /* synthetic */ WalletFragment zzlph;

        private zzc(WalletFragment walletFragment) {
            this.zzlph = walletFragment;
        }

        public final void onClick(View view) {
            Context activity = this.zzlph.zzhcy.getActivity();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity), activity, -1);
        }

        protected final void zza(FrameLayout frameLayout) {
            int i = -1;
            int i2 = -2;
            View button = new Button(this.zzlph.zzhcy.getActivity());
            button.setText(C0489R.string.wallet_buy_button_place_holder);
            if (this.zzlph.zzlos != null) {
                WalletFragmentStyle fragmentStyle = this.zzlph.zzlos.getFragmentStyle();
                if (fragmentStyle != null) {
                    DisplayMetrics displayMetrics = this.zzlph.zzhcy.getResources().getDisplayMetrics();
                    i = fragmentStyle.zza("buyButtonWidth", displayMetrics, -1);
                    i2 = fragmentStyle.zza("buyButtonHeight", displayMetrics, -2);
                }
            }
            button.setLayoutParams(new LayoutParams(i, i2));
            button.setOnClickListener(this);
            frameLayout.addView(button);
        }

        protected final void zza(zzo<zzb> zzo) {
            Activity activity = this.zzlph.zzhcy.getActivity();
            if (this.zzlph.zzlpb == null && this.zzlph.mCreated && activity != null) {
                try {
                    this.zzlph.zzlpb = new zzb(zzdnc.zza(activity, this.zzlph.zzlpc, this.zzlph.zzlos, this.zzlph.zzlpe));
                    this.zzlph.zzlos = null;
                    zzo.zza(this.zzlph.zzlpb);
                    if (this.zzlph.zzlot != null) {
                        this.zzlph.zzlpb.initialize(this.zzlph.zzlot);
                        this.zzlph.zzlot = null;
                    }
                    if (this.zzlph.zzlou != null) {
                        this.zzlph.zzlpb.updateMaskedWalletRequest(this.zzlph.zzlou);
                        this.zzlph.zzlou = null;
                    }
                    if (this.zzlph.zzlov != null) {
                        this.zzlph.zzlpb.updateMaskedWallet(this.zzlph.zzlov);
                        this.zzlph.zzlov = null;
                    }
                    if (this.zzlph.zzlow != null) {
                        this.zzlph.zzlpb.setEnabled(this.zzlph.zzlow.booleanValue());
                        this.zzlph.zzlow = null;
                    }
                } catch (GooglePlayServicesNotAvailableException e) {
                }
            }
        }
    }

    public static WalletFragment newInstance(WalletFragmentOptions walletFragmentOptions) {
        WalletFragment walletFragment = new WalletFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("extraWalletFragmentOptions", walletFragmentOptions);
        walletFragment.zzhcy.setArguments(bundle);
        return walletFragment;
    }

    public final int getState() {
        return this.zzlpb != null ? this.zzlpb.getState() : 0;
    }

    public final void initialize(WalletFragmentInitParams walletFragmentInitParams) {
        if (this.zzlpb != null) {
            this.zzlpb.initialize(walletFragmentInitParams);
            this.zzlot = null;
        } else if (this.zzlot == null) {
            this.zzlot = walletFragmentInitParams;
            if (this.zzlou != null) {
                Log.w("WalletFragment", "updateMaskedWalletRequest() was called before initialize()");
            }
            if (this.zzlov != null) {
                Log.w("WalletFragment", "updateMaskedWallet() was called before initialize()");
            }
        } else {
            Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once. Ignoring.");
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (this.zzlpb != null) {
            this.zzlpb.onActivityResult(i, i2, intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
            WalletFragmentInitParams walletFragmentInitParams = (WalletFragmentInitParams) bundle.getParcelable("walletFragmentInitParams");
            if (walletFragmentInitParams != null) {
                if (this.zzlot != null) {
                    Log.w("WalletFragment", "initialize(WalletFragmentInitParams) was called more than once.Ignoring.");
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
        } else if (this.zzhcy.getArguments() != null) {
            WalletFragmentOptions walletFragmentOptions = (WalletFragmentOptions) this.zzhcy.getArguments().getParcelable("extraWalletFragmentOptions");
            if (walletFragmentOptions != null) {
                walletFragmentOptions.zzet(this.zzhcy.getActivity());
                this.zzlos = walletFragmentOptions;
            }
        }
        this.mCreated = true;
        this.zzlpd.onCreate(bundle);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.zzlpd.onCreateView(layoutInflater, viewGroup, bundle);
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
        this.zzlpd.onInflate(activity, bundle2, bundle);
    }

    public final void onPause() {
        super.onPause();
        this.zzlpd.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.zzlpd.onResume();
        FragmentManager fragmentManager = this.zzhcy.getActivity().getFragmentManager();
        Fragment findFragmentByTag = fragmentManager.findFragmentByTag(GooglePlayServicesUtil.GMS_ERROR_DIALOG);
        if (findFragmentByTag != null) {
            fragmentManager.beginTransaction().remove(findFragmentByTag).commit();
            GooglePlayServicesUtil.showErrorDialogFragment(GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzhcy.getActivity()), this.zzhcy.getActivity(), -1);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.setClassLoader(WalletFragmentOptions.class.getClassLoader());
        this.zzlpd.onSaveInstanceState(bundle);
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
        this.zzlpd.onStart();
    }

    public final void onStop() {
        super.onStop();
        this.zzlpd.onStop();
    }

    public final void setEnabled(boolean z) {
        if (this.zzlpb != null) {
            this.zzlpb.setEnabled(z);
            this.zzlow = null;
            return;
        }
        this.zzlow = Boolean.valueOf(z);
    }

    public final void setOnStateChangedListener(OnStateChangedListener onStateChangedListener) {
        this.zzlpe.zza(onStateChangedListener);
    }

    public final void updateMaskedWallet(MaskedWallet maskedWallet) {
        if (this.zzlpb != null) {
            this.zzlpb.updateMaskedWallet(maskedWallet);
            this.zzlov = null;
            return;
        }
        this.zzlov = maskedWallet;
    }

    public final void updateMaskedWalletRequest(MaskedWalletRequest maskedWalletRequest) {
        if (this.zzlpb != null) {
            this.zzlpb.updateMaskedWalletRequest(maskedWalletRequest);
            this.zzlou = null;
            return;
        }
        this.zzlou = maskedWalletRequest;
    }
}
