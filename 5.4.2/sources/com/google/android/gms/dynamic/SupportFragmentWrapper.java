package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.IFragmentWrapper.Stub;

public final class SupportFragmentWrapper extends Stub {
    private Fragment zzabq;

    private SupportFragmentWrapper(Fragment fragment) {
        this.zzabq = fragment;
    }

    public static SupportFragmentWrapper wrap(Fragment fragment) {
        return fragment != null ? new SupportFragmentWrapper(fragment) : null;
    }

    public final IObjectWrapper getActivity() {
        return ObjectWrapper.wrap(this.zzabq.getActivity());
    }

    public final Bundle getArguments() {
        return this.zzabq.getArguments();
    }

    public final int getId() {
        return this.zzabq.getId();
    }

    public final IFragmentWrapper getParentFragment() {
        return wrap(this.zzabq.getParentFragment());
    }

    public final IObjectWrapper getResources() {
        return ObjectWrapper.wrap(this.zzabq.getResources());
    }

    public final boolean getRetainInstance() {
        return this.zzabq.getRetainInstance();
    }

    public final String getTag() {
        return this.zzabq.getTag();
    }

    public final IFragmentWrapper getTargetFragment() {
        return wrap(this.zzabq.getTargetFragment());
    }

    public final int getTargetRequestCode() {
        return this.zzabq.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzabq.getUserVisibleHint();
    }

    public final IObjectWrapper getView() {
        return ObjectWrapper.wrap(this.zzabq.getView());
    }

    public final boolean isAdded() {
        return this.zzabq.isAdded();
    }

    public final boolean isDetached() {
        return this.zzabq.isDetached();
    }

    public final boolean isHidden() {
        return this.zzabq.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzabq.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzabq.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzabq.isResumed();
    }

    public final boolean isVisible() {
        return this.zzabq.isVisible();
    }

    public final void registerForContextMenu(IObjectWrapper iObjectWrapper) {
        this.zzabq.registerForContextMenu((View) ObjectWrapper.unwrap(iObjectWrapper));
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzabq.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzabq.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzabq.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzabq.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzabq.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzabq.startActivityForResult(intent, i);
    }

    public final void unregisterForContextMenu(IObjectWrapper iObjectWrapper) {
        this.zzabq.unregisterForContextMenu((View) ObjectWrapper.unwrap(iObjectWrapper));
    }
}
