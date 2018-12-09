package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.android.gms.dynamic.DeferredLifecycleHelper;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamic.OnDelegateCreatedListener;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {
    private final zzb zzbf;

    @VisibleForTesting
    static class zza implements MapLifecycleDelegate {
        private final ViewGroup parent;
        private final IMapViewDelegate zzbg;
        private View zzbh;

        public zza(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.zzbg = (IMapViewDelegate) Preconditions.checkNotNull(iMapViewDelegate);
            this.parent = (ViewGroup) Preconditions.checkNotNull(viewGroup);
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            try {
                this.zzbg.getMapAsync(new zzac(this, onMapReadyCallback));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onCreate(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zza(bundle, bundle2);
                this.zzbg.onCreate(bundle2);
                zzby.zza(bundle2, bundle);
                this.zzbh = (View) ObjectWrapper.unwrap(this.zzbg.getView());
                this.parent.removeAllViews();
                this.parent.addView(this.zzbh);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        public final void onDestroy() {
            try {
                this.zzbg.onDestroy();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onDestroyView() {
            throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
        }

        public final void onEnterAmbient(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zza(bundle, bundle2);
                this.zzbg.onEnterAmbient(bundle2);
                zzby.zza(bundle2, bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onExitAmbient() {
            try {
                this.zzbg.onExitAmbient();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        public final void onLowMemory() {
            try {
                this.zzbg.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onPause() {
            try {
                this.zzbg.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onResume() {
            try {
                this.zzbg.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onSaveInstanceState(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zza(bundle, bundle2);
                this.zzbg.onSaveInstanceState(bundle2);
                zzby.zza(bundle2, bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onStart() {
            try {
                this.zzbg.onStart();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onStop() {
            try {
                this.zzbg.onStop();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    @VisibleForTesting
    static class zzb extends DeferredLifecycleHelper<zza> {
        private OnDelegateCreatedListener<zza> zzbc;
        private final List<OnMapReadyCallback> zzbe = new ArrayList();
        private final ViewGroup zzbi;
        private final Context zzbj;
        private final GoogleMapOptions zzbk;

        @VisibleForTesting
        zzb(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.zzbi = viewGroup;
            this.zzbj = context;
            this.zzbk = googleMapOptions;
        }

        protected final void createDelegate(OnDelegateCreatedListener<zza> onDelegateCreatedListener) {
            this.zzbc = onDelegateCreatedListener;
            if (this.zzbc != null && getDelegate() == null) {
                try {
                    MapsInitializer.initialize(this.zzbj);
                    IMapViewDelegate zza = zzbz.zza(this.zzbj).zza(ObjectWrapper.wrap(this.zzbj), this.zzbk);
                    if (zza != null) {
                        this.zzbc.onDelegateCreated(new zza(this.zzbi, zza));
                        for (OnMapReadyCallback mapAsync : this.zzbe) {
                            ((zza) getDelegate()).getMapAsync(mapAsync);
                        }
                        this.zzbe.clear();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            if (getDelegate() != null) {
                ((zza) getDelegate()).getMapAsync(onMapReadyCallback);
            } else {
                this.zzbe.add(onMapReadyCallback);
            }
        }
    }

    public MapView(Context context) {
        super(context);
        this.zzbf = new zzb(this, context, null);
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzbf = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzbf = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.zzbf = new zzb(this, context, googleMapOptions);
        setClickable(true);
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        Preconditions.checkMainThread("getMapAsync() must be called on the main thread");
        this.zzbf.getMapAsync(onMapReadyCallback);
    }

    public final void onCreate(Bundle bundle) {
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new Builder(threadPolicy).permitAll().build());
        try {
            this.zzbf.onCreate(bundle);
            if (this.zzbf.getDelegate() == null) {
                DeferredLifecycleHelper.showGooglePlayUnavailableMessage(this);
            }
            StrictMode.setThreadPolicy(threadPolicy);
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    public final void onDestroy() {
        this.zzbf.onDestroy();
    }

    public final void onEnterAmbient(Bundle bundle) {
        Preconditions.checkMainThread("onEnterAmbient() must be called on the main thread");
        zzb zzb = this.zzbf;
        if (zzb.getDelegate() != null) {
            ((zza) zzb.getDelegate()).onEnterAmbient(bundle);
        }
    }

    public final void onExitAmbient() {
        Preconditions.checkMainThread("onExitAmbient() must be called on the main thread");
        zzb zzb = this.zzbf;
        if (zzb.getDelegate() != null) {
            ((zza) zzb.getDelegate()).onExitAmbient();
        }
    }

    public final void onLowMemory() {
        this.zzbf.onLowMemory();
    }

    public final void onPause() {
        this.zzbf.onPause();
    }

    public final void onResume() {
        this.zzbf.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.zzbf.onSaveInstanceState(bundle);
    }

    public final void onStart() {
        this.zzbf.onStart();
    }

    public final void onStop() {
        this.zzbf.onStop();
    }
}
