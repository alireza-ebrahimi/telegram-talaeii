package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.internal.Hide;

@Hide
@SuppressLint({"NewApi"})
public final class zzj extends zzl {
    private Fragment zzhcy;

    private zzj(Fragment fragment) {
        this.zzhcy = fragment;
    }

    public static zzj zza(Fragment fragment) {
        return fragment != null ? new zzj(fragment) : null;
    }

    public final Bundle getArguments() {
        return this.zzhcy.getArguments();
    }

    public final int getId() {
        return this.zzhcy.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzhcy.getRetainInstance();
    }

    public final String getTag() {
        return this.zzhcy.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzhcy.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzhcy.getUserVisibleHint();
    }

    public final IObjectWrapper getView() {
        return zzn.zzz(this.zzhcy.getView());
    }

    public final boolean isAdded() {
        return this.zzhcy.isAdded();
    }

    public final boolean isDetached() {
        return this.zzhcy.isDetached();
    }

    public final boolean isHidden() {
        return this.zzhcy.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzhcy.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzhcy.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzhcy.isResumed();
    }

    public final boolean isVisible() {
        return this.zzhcy.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzhcy.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzhcy.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzhcy.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzhcy.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzhcy.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzhcy.startActivityForResult(intent, i);
    }

    public final IObjectWrapper zzarh() {
        return zzn.zzz(this.zzhcy.getActivity());
    }

    public final zzk zzari() {
        return zza(this.zzhcy.getParentFragment());
    }

    public final IObjectWrapper zzarj() {
        return zzn.zzz(this.zzhcy.getResources());
    }

    public final zzk zzark() {
        return zza(this.zzhcy.getTargetFragment());
    }

    public final void zzw(IObjectWrapper iObjectWrapper) {
        this.zzhcy.registerForContextMenu((View) zzn.zzy(iObjectWrapper));
    }

    public final void zzx(IObjectWrapper iObjectWrapper) {
        this.zzhcy.unregisterForContextMenu((View) zzn.zzy(iObjectWrapper));
    }
}
