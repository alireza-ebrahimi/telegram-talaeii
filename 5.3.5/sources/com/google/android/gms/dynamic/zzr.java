package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzr extends zzl {
    private Fragment zzhdb;

    private zzr(Fragment fragment) {
        this.zzhdb = fragment;
    }

    public static zzr zza(Fragment fragment) {
        return fragment != null ? new zzr(fragment) : null;
    }

    public final Bundle getArguments() {
        return this.zzhdb.getArguments();
    }

    public final int getId() {
        return this.zzhdb.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzhdb.getRetainInstance();
    }

    public final String getTag() {
        return this.zzhdb.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzhdb.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzhdb.getUserVisibleHint();
    }

    public final IObjectWrapper getView() {
        return zzn.zzz(this.zzhdb.getView());
    }

    public final boolean isAdded() {
        return this.zzhdb.isAdded();
    }

    public final boolean isDetached() {
        return this.zzhdb.isDetached();
    }

    public final boolean isHidden() {
        return this.zzhdb.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzhdb.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzhdb.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzhdb.isResumed();
    }

    public final boolean isVisible() {
        return this.zzhdb.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzhdb.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzhdb.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzhdb.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzhdb.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzhdb.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzhdb.startActivityForResult(intent, i);
    }

    public final IObjectWrapper zzarh() {
        return zzn.zzz(this.zzhdb.getActivity());
    }

    public final zzk zzari() {
        return zza(this.zzhdb.getParentFragment());
    }

    public final IObjectWrapper zzarj() {
        return zzn.zzz(this.zzhdb.getResources());
    }

    public final zzk zzark() {
        return zza(this.zzhdb.getTargetFragment());
    }

    public final void zzw(IObjectWrapper iObjectWrapper) {
        this.zzhdb.registerForContextMenu((View) zzn.zzy(iObjectWrapper));
    }

    public final void zzx(IObjectWrapper iObjectWrapper) {
        this.zzhdb.unregisterForContextMenu((View) zzn.zzy(iObjectWrapper));
    }
}
