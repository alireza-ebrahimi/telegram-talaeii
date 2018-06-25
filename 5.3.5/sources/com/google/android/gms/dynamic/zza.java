package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.zzf;
import java.util.LinkedList;

@Hide
public abstract class zza<T extends LifecycleDelegate> {
    private T zzhcp;
    private Bundle zzhcq;
    private LinkedList<zzi> zzhcr;
    private final zzo<T> zzhcs = new zzb(this);

    private final void zza(Bundle bundle, zzi zzi) {
        if (this.zzhcp != null) {
            zzi.zzb(this.zzhcp);
            return;
        }
        if (this.zzhcr == null) {
            this.zzhcr = new LinkedList();
        }
        this.zzhcr.add(zzi);
        if (bundle != null) {
            if (this.zzhcq == null) {
                this.zzhcq = (Bundle) bundle.clone();
            } else {
                this.zzhcq.putAll(bundle);
            }
        }
        zza(this.zzhcs);
    }

    public static void zzb(FrameLayout frameLayout) {
        zzf instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        CharSequence zzh = zzu.zzh(context, isGooglePlayServicesAvailable);
        CharSequence zzj = zzu.zzj(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzh);
        linearLayout.addView(textView);
        Intent zza = zzf.zza(context, isGooglePlayServicesAvailable, null);
        if (zza != null) {
            View button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzj);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, zza));
        }
    }

    private final void zzcz(int i) {
        while (!this.zzhcr.isEmpty() && ((zzi) this.zzhcr.getLast()).getState() >= i) {
            this.zzhcr.removeLast();
        }
    }

    public final void onCreate(Bundle bundle) {
        zza(bundle, new zzd(this, bundle));
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zza(bundle, new zze(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.zzhcp == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public final void onDestroy() {
        if (this.zzhcp != null) {
            this.zzhcp.onDestroy();
        } else {
            zzcz(1);
        }
    }

    public final void onDestroyView() {
        if (this.zzhcp != null) {
            this.zzhcp.onDestroyView();
        } else {
            zzcz(2);
        }
    }

    public final void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        zza(bundle2, new zzc(this, activity, bundle, bundle2));
    }

    public final void onLowMemory() {
        if (this.zzhcp != null) {
            this.zzhcp.onLowMemory();
        }
    }

    public final void onPause() {
        if (this.zzhcp != null) {
            this.zzhcp.onPause();
        } else {
            zzcz(5);
        }
    }

    public final void onResume() {
        zza(null, new zzh(this));
    }

    public final void onSaveInstanceState(Bundle bundle) {
        if (this.zzhcp != null) {
            this.zzhcp.onSaveInstanceState(bundle);
        } else if (this.zzhcq != null) {
            bundle.putAll(this.zzhcq);
        }
    }

    public final void onStart() {
        zza(null, new zzg(this));
    }

    public final void onStop() {
        if (this.zzhcp != null) {
            this.zzhcp.onStop();
        } else {
            zzcz(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzf instance = GoogleApiAvailability.getInstance();
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = instance.isGooglePlayServicesAvailable(context);
        CharSequence zzh = zzu.zzh(context, isGooglePlayServicesAvailable);
        CharSequence zzj = zzu.zzj(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzh);
        linearLayout.addView(textView);
        Intent zza = zzf.zza(context, isGooglePlayServicesAvailable, null);
        if (zza != null) {
            View button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzj);
            linearLayout.addView(button);
            button.setOnClickListener(new zzf(context, zza));
        }
    }

    protected abstract void zza(zzo<T> zzo);

    public final T zzarg() {
        return this.zzhcp;
    }
}
