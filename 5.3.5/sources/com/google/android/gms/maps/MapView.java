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
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamic.zzo;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzby;
import com.google.android.gms.maps.internal.zzbz;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.List;

public class MapView extends FrameLayout {
    private final zzb zzjbp;

    @Hide
    static class zza implements MapLifecycleDelegate {
        private final ViewGroup parent;
        private final IMapViewDelegate zzjbq;
        private View zzjbr;

        public zza(ViewGroup viewGroup, IMapViewDelegate iMapViewDelegate) {
            this.zzjbq = (IMapViewDelegate) zzbq.checkNotNull(iMapViewDelegate);
            this.parent = (ViewGroup) zzbq.checkNotNull(viewGroup);
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            try {
                this.zzjbq.getMapAsync(new zzac(this, onMapReadyCallback));
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onCreate(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                this.zzjbq.onCreate(bundle2);
                zzby.zzd(bundle2, bundle);
                this.zzjbr = (View) zzn.zzy(this.zzjbq.getView());
                this.parent.removeAllViews();
                this.parent.addView(this.zzjbr);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
        }

        public final void onDestroy() {
            try {
                this.zzjbq.onDestroy();
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
                zzby.zzd(bundle, bundle2);
                this.zzjbq.onEnterAmbient(bundle2);
                zzby.zzd(bundle2, bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onExitAmbient() {
            try {
                this.zzjbq.onExitAmbient();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
            throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
        }

        public final void onLowMemory() {
            try {
                this.zzjbq.onLowMemory();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onPause() {
            try {
                this.zzjbq.onPause();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onResume() {
            try {
                this.zzjbq.onResume();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onSaveInstanceState(Bundle bundle) {
            try {
                Bundle bundle2 = new Bundle();
                zzby.zzd(bundle, bundle2);
                this.zzjbq.onSaveInstanceState(bundle2);
                zzby.zzd(bundle2, bundle);
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onStart() {
            try {
                this.zzjbq.onStart();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }

        public final void onStop() {
            try {
                this.zzjbq.onStop();
            } catch (RemoteException e) {
                throw new RuntimeRemoteException(e);
            }
        }
    }

    static class zzb extends com.google.android.gms.dynamic.zza<zza> {
        private zzo<zza> zzjbn;
        private final List<OnMapReadyCallback> zzjbo = new ArrayList();
        private final ViewGroup zzjbs;
        private final Context zzjbt;
        private final GoogleMapOptions zzjbu;

        zzb(ViewGroup viewGroup, Context context, GoogleMapOptions googleMapOptions) {
            this.zzjbs = viewGroup;
            this.zzjbt = context;
            this.zzjbu = googleMapOptions;
        }

        public final void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
            if (zzarg() != null) {
                ((zza) zzarg()).getMapAsync(onMapReadyCallback);
            } else {
                this.zzjbo.add(onMapReadyCallback);
            }
        }

        protected final void zza(zzo<zza> zzo) {
            this.zzjbn = zzo;
            if (this.zzjbn != null && zzarg() == null) {
                try {
                    MapsInitializer.initialize(this.zzjbt);
                    IMapViewDelegate zza = zzbz.zzdz(this.zzjbt).zza(zzn.zzz(this.zzjbt), this.zzjbu);
                    if (zza != null) {
                        this.zzjbn.zza(new zza(this.zzjbs, zza));
                        for (OnMapReadyCallback mapAsync : this.zzjbo) {
                            ((zza) zzarg()).getMapAsync(mapAsync);
                        }
                        this.zzjbo.clear();
                    }
                } catch (RemoteException e) {
                    throw new RuntimeRemoteException(e);
                } catch (GooglePlayServicesNotAvailableException e2) {
                }
            }
        }
    }

    public MapView(Context context) {
        super(context);
        this.zzjbp = new zzb(this, context, null);
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzjbp = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzjbp = new zzb(this, context, GoogleMapOptions.createFromAttributes(context, attributeSet));
        setClickable(true);
    }

    public MapView(Context context, GoogleMapOptions googleMapOptions) {
        super(context);
        this.zzjbp = new zzb(this, context, googleMapOptions);
        setClickable(true);
    }

    public void getMapAsync(OnMapReadyCallback onMapReadyCallback) {
        zzbq.zzgn("getMapAsync() must be called on the main thread");
        this.zzjbp.getMapAsync(onMapReadyCallback);
    }

    public final void onCreate(Bundle bundle) {
        ThreadPolicy threadPolicy = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new Builder(threadPolicy).permitAll().build());
        try {
            this.zzjbp.onCreate(bundle);
            if (this.zzjbp.zzarg() == null) {
                com.google.android.gms.dynamic.zza.zzb((FrameLayout) this);
            }
            StrictMode.setThreadPolicy(threadPolicy);
        } catch (Throwable th) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    public final void onDestroy() {
        this.zzjbp.onDestroy();
    }

    public final void onEnterAmbient(Bundle bundle) {
        zzbq.zzgn("onEnterAmbient() must be called on the main thread");
        com.google.android.gms.dynamic.zza zza = this.zzjbp;
        if (zza.zzarg() != null) {
            ((zza) zza.zzarg()).onEnterAmbient(bundle);
        }
    }

    public final void onExitAmbient() {
        zzbq.zzgn("onExitAmbient() must be called on the main thread");
        com.google.android.gms.dynamic.zza zza = this.zzjbp;
        if (zza.zzarg() != null) {
            ((zza) zza.zzarg()).onExitAmbient();
        }
    }

    public final void onLowMemory() {
        this.zzjbp.onLowMemory();
    }

    public final void onPause() {
        this.zzjbp.onPause();
    }

    public final void onResume() {
        this.zzjbp.onResume();
    }

    public final void onSaveInstanceState(Bundle bundle) {
        this.zzjbp.onSaveInstanceState(bundle);
    }

    public final void onStart() {
        this.zzjbp.onStart();
    }

    public final void onStop() {
        this.zzjbp.onStop();
    }
}
