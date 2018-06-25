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

    public BaseFragment(Bundle args) {
        this.isFinished = false;
        this.visibleDialog = null;
        this.classGuid = 0;
        this.swipeBackEnabled = true;
        this.hasOwnBackground = false;
        this.arguments = args;
        this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
    }

    public ActionBar getActionBar() {
        return this.actionBar;
    }

    public View getFragmentView() {
        return this.fragmentView;
    }

    public View createView(Context context) {
        return null;
    }

    public Bundle getArguments() {
        return this.arguments;
    }

    protected void clearViews() {
        ViewGroup parent;
        if (this.fragmentView != null) {
            parent = (ViewGroup) this.fragmentView.getParent();
            if (parent != null) {
                try {
                    onRemoveFromParent();
                    parent.removeView(this.fragmentView);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            this.fragmentView = null;
        }
        if (this.actionBar != null) {
            parent = (ViewGroup) this.actionBar.getParent();
            if (parent != null) {
                try {
                    parent.removeView(this.actionBar);
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            this.actionBar = null;
        }
        this.parentLayout = null;
    }

    protected void onRemoveFromParent() {
    }

    public void setParentLayout(ActionBarLayout layout) {
        if (this.parentLayout != layout) {
            ViewGroup parent;
            this.parentLayout = layout;
            if (this.fragmentView != null) {
                parent = (ViewGroup) this.fragmentView.getParent();
                if (parent != null) {
                    try {
                        onRemoveFromParent();
                        parent.removeView(this.fragmentView);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
                if (!(this.parentLayout == null || this.parentLayout.getContext() == this.fragmentView.getContext())) {
                    this.fragmentView = null;
                }
            }
            if (this.actionBar != null) {
                boolean differentParent = (this.parentLayout == null || this.parentLayout.getContext() == this.actionBar.getContext()) ? false : true;
                if (this.actionBar.getAddToContainer() || differentParent) {
                    parent = (ViewGroup) this.actionBar.getParent();
                    if (parent != null) {
                        try {
                            parent.removeView(this.actionBar);
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                    }
                }
                if (differentParent) {
                    this.actionBar = null;
                }
            }
            if (this.parentLayout != null && this.actionBar == null) {
                this.actionBar = createActionBar(this.parentLayout.getContext());
                this.actionBar.parentFragment = this;
            }
        }
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

    public void finishFragment() {
        finishFragment(true);
    }

    public void finishFragment(boolean animated) {
        if (!this.isFinished && this.parentLayout != null) {
            this.parentLayout.closeLastFragment(animated);
        }
    }

    public void removeSelfFromStack() {
        if (!this.isFinished && this.parentLayout != null) {
            this.parentLayout.removeFragmentFromStack(this);
        }
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

    public boolean needDelayOpenAnimation() {
        return false;
    }

    public void onResume() {
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
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public BaseFragment getFragmentForAlert(int offset) {
        if (this.parentLayout == null || this.parentLayout.fragmentsStack.size() <= offset + 1) {
            return this;
        }
        return (BaseFragment) this.parentLayout.fragmentsStack.get((this.parentLayout.fragmentsStack.size() - 2) - offset);
    }

    public void onConfigurationChanged(Configuration newConfig) {
    }

    public boolean onBackPressed() {
        return true;
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
    }

    public void saveSelfArgs(Bundle args) {
    }

    public void restoreSelfArgs(Bundle args) {
    }

    public boolean presentFragment(BaseFragment fragment) {
        return this.parentLayout != null && this.parentLayout.presentFragment(fragment);
    }

    public boolean presentFragment(BaseFragment fragment, boolean removeLast) {
        return this.parentLayout != null && this.parentLayout.presentFragment(fragment, removeLast);
    }

    public boolean presentFragment(BaseFragment fragment, boolean removeLast, boolean forceWithoutAnimation) {
        return this.parentLayout != null && this.parentLayout.presentFragment(fragment, removeLast, forceWithoutAnimation, true);
    }

    public Activity getParentActivity() {
        if (this.parentLayout != null) {
            return this.parentLayout.parentActivity;
        }
        return null;
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (this.parentLayout != null) {
            this.parentLayout.startActivityForResult(intent, requestCode);
        }
    }

    public void dismissCurrentDialig() {
        if (this.visibleDialog != null) {
            try {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public boolean dismissDialogOnPause(Dialog dialog) {
        return true;
    }

    public void onBeginSlide() {
        try {
            if (this.visibleDialog != null && this.visibleDialog.isShowing()) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
        if (this.actionBar != null) {
            this.actionBar.onPause();
        }
    }

    protected void onTransitionAnimationStart(boolean isOpen, boolean backward) {
    }

    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
    }

    protected void onBecomeFullyVisible() {
    }

    protected AnimatorSet onCustomTransitionAnimation(boolean isOpen, Runnable callback) {
        return null;
    }

    public void onLowMemory() {
    }

    public Dialog showDialog(Dialog dialog) {
        return showDialog(dialog, false, null);
    }

    public Dialog showDialog(Dialog dialog, OnDismissListener onDismissListener) {
        return showDialog(dialog, false, onDismissListener);
    }

    public Dialog showDialog(Dialog dialog, boolean allowInTransition, final OnDismissListener onDismissListener) {
        Dialog dialog2 = null;
        if (!(dialog == null || this.parentLayout == null || this.parentLayout.animationInProgress || this.parentLayout.startedTracking || (!allowInTransition && this.parentLayout.checkTransitionAnimation()))) {
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                this.visibleDialog = dialog;
                this.visibleDialog.setCanceledOnTouchOutside(true);
                this.visibleDialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        if (onDismissListener != null) {
                            onDismissListener.onDismiss(dialog);
                        }
                        BaseFragment.this.onDialogDismiss(BaseFragment.this.visibleDialog);
                        BaseFragment.this.visibleDialog = null;
                    }
                });
                this.visibleDialog.show();
                dialog2 = this.visibleDialog;
            } catch (Exception e2) {
                FileLog.e(e2);
            }
        }
        return dialog2;
    }

    protected void onDialogDismiss(Dialog dialog) {
    }

    public Dialog getVisibleDialog() {
        return this.visibleDialog;
    }

    public void setVisibleDialog(Dialog dialog) {
        this.visibleDialog = dialog;
    }

    public boolean extendActionMode(Menu menu) {
        return false;
    }

    public ThemeDescription[] getThemeDescriptions() {
        return null;
    }
}
