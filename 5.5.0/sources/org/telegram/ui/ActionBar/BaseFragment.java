package org.telegram.ui.ActionBar;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import org.telegram.messenger.FileLog;
import org.telegram.tgnet.ConnectionsManager;

public class BaseFragment {
    public ActionBar actionBar;
    protected Bundle arguments;
    protected int classGuid;
    protected View fragmentView;
    protected boolean hasOwnBackground;
    private boolean isFinished;
    public ActionBarLayout parentLayout;
    protected boolean swipeBackEnabled;
    protected Dialog visibleDialog;

    public BaseFragment() {
        this.isFinished = false;
        this.visibleDialog = null;
        this.classGuid = 0;
        this.swipeBackEnabled = true;
        this.hasOwnBackground = false;
        this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
    }

    public BaseFragment(Bundle bundle) {
        this.isFinished = false;
        this.visibleDialog = null;
        this.classGuid = 0;
        this.swipeBackEnabled = true;
        this.hasOwnBackground = false;
        this.arguments = bundle;
        this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
    }

    protected void clearViews() {
        ViewGroup viewGroup;
        if (this.fragmentView != null) {
            viewGroup = (ViewGroup) this.fragmentView.getParent();
            if (viewGroup != null) {
                try {
                    onRemoveFromParent();
                    viewGroup.removeView(this.fragmentView);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            this.fragmentView = null;
        }
        if (this.actionBar != null) {
            viewGroup = (ViewGroup) this.actionBar.getParent();
            if (viewGroup != null) {
                try {
                    viewGroup.removeView(this.actionBar);
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            this.actionBar = null;
        }
        this.parentLayout = null;
    }

    protected ActionBar createActionBar(Context context) {
        ActionBar actionBar = new ActionBar(context);
        actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarDefaultSelector), false);
        actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarActionModeDefaultSelector), true);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon), false);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), true);
        return actionBar;
    }

    public View createView(Context context) {
        return null;
    }

    public void dismissCurrentDialig() {
        if (this.visibleDialog != null) {
            try {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public boolean dismissDialogOnPause(Dialog dialog) {
        return true;
    }

    public boolean extendActionMode(Menu menu) {
        return false;
    }

    public void finishFragment() {
        finishFragment(true);
    }

    public void finishFragment(boolean z) {
        if (!this.isFinished && this.parentLayout != null) {
            this.parentLayout.closeLastFragment(z);
        }
    }

    public ActionBar getActionBar() {
        return this.actionBar;
    }

    public Bundle getArguments() {
        return this.arguments;
    }

    public BaseFragment getFragmentForAlert(int i) {
        return (this.parentLayout == null || this.parentLayout.fragmentsStack.size() <= i + 1) ? this : (BaseFragment) this.parentLayout.fragmentsStack.get((this.parentLayout.fragmentsStack.size() - 2) - i);
    }

    public View getFragmentView() {
        return this.fragmentView;
    }

    public Activity getParentActivity() {
        return this.parentLayout != null ? this.parentLayout.parentActivity : null;
    }

    public ThemeDescription[] getThemeDescriptions() {
        return null;
    }

    public Dialog getVisibleDialog() {
        return this.visibleDialog;
    }

    public boolean needDelayOpenAnimation() {
        return false;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
    }

    public boolean onBackPressed() {
        return true;
    }

    protected void onBecomeFullyVisible() {
    }

    public void onBeginSlide() {
        try {
            if (this.visibleDialog != null && this.visibleDialog.isShowing()) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (this.actionBar != null) {
            this.actionBar.onPause();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    protected AnimatorSet onCustomTransitionAnimation(boolean z, Runnable runnable) {
        return null;
    }

    protected void onDialogDismiss(Dialog dialog) {
    }

    public boolean onFragmentCreate() {
        return true;
    }

    public void onFragmentDestroy() {
        ConnectionsManager.getInstance().cancelRequestsForGuid(this.classGuid);
        this.isFinished = true;
        if (this.actionBar != null) {
            this.actionBar.setEnabled(false);
        }
    }

    public void onLowMemory() {
    }

    public void onPause() {
        if (this.actionBar != null) {
            this.actionBar.onPause();
        }
        try {
            if (this.visibleDialog != null && this.visibleDialog.isShowing() && dismissDialogOnPause(this.visibleDialog)) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    protected void onRemoveFromParent() {
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
    }

    public void onResume() {
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
    }

    protected void onTransitionAnimationStart(boolean z, boolean z2) {
    }

    public boolean presentFragment(BaseFragment baseFragment) {
        return this.parentLayout != null && this.parentLayout.presentFragment(baseFragment);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z) {
        return this.parentLayout != null && this.parentLayout.presentFragment(baseFragment, z);
    }

    public boolean presentFragment(BaseFragment baseFragment, boolean z, boolean z2) {
        return this.parentLayout != null && this.parentLayout.presentFragment(baseFragment, z, z2, true);
    }

    public void removeSelfFromStack() {
        if (!this.isFinished && this.parentLayout != null) {
            this.parentLayout.removeFragmentFromStack(this);
        }
    }

    public void restoreSelfArgs(Bundle bundle) {
    }

    public void saveSelfArgs(Bundle bundle) {
    }

    public void setParentLayout(ActionBarLayout actionBarLayout) {
        if (this.parentLayout != actionBarLayout) {
            ViewGroup viewGroup;
            this.parentLayout = actionBarLayout;
            if (this.fragmentView != null) {
                viewGroup = (ViewGroup) this.fragmentView.getParent();
                if (viewGroup != null) {
                    try {
                        onRemoveFromParent();
                        viewGroup.removeView(this.fragmentView);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
                if (!(this.parentLayout == null || this.parentLayout.getContext() == this.fragmentView.getContext())) {
                    this.fragmentView = null;
                }
            }
            if (this.actionBar != null) {
                Object obj = (this.parentLayout == null || this.parentLayout.getContext() == this.actionBar.getContext()) ? null : 1;
                if (this.actionBar.getAddToContainer() || obj != null) {
                    viewGroup = (ViewGroup) this.actionBar.getParent();
                    if (viewGroup != null) {
                        try {
                            viewGroup.removeView(this.actionBar);
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    }
                }
                if (obj != null) {
                    this.actionBar = null;
                }
            }
            if (this.parentLayout != null && this.actionBar == null) {
                this.actionBar = createActionBar(this.parentLayout.getContext());
                this.actionBar.parentFragment = this;
            }
        }
    }

    public void setVisibleDialog(Dialog dialog) {
        this.visibleDialog = dialog;
    }

    public Dialog showDialog(Dialog dialog) {
        return showDialog(dialog, false, null);
    }

    public Dialog showDialog(Dialog dialog, OnDismissListener onDismissListener) {
        return showDialog(dialog, false, onDismissListener);
    }

    public Dialog showDialog(Dialog dialog, boolean z, final OnDismissListener onDismissListener) {
        Dialog dialog2 = null;
        if (!(dialog == null || this.parentLayout == null || this.parentLayout.animationInProgress || this.parentLayout.startedTracking || (!z && this.parentLayout.checkTransitionAnimation()))) {
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            try {
                this.visibleDialog = dialog;
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (onDismissListener != null) {
                            onDismissListener.onDismiss(dialogInterface);
                        }
                        BaseFragment.this.onDialogDismiss(BaseFragment.this.visibleDialog);
                        BaseFragment.this.visibleDialog = null;
                    }
                });
                this.visibleDialog.show();
                dialog2 = this.visibleDialog;
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
        }
        return dialog2;
    }

    public void startActivityForResult(Intent intent, int i) {
        if (this.parentLayout != null) {
            this.parentLayout.startActivityForResult(intent, i);
        }
    }
}
