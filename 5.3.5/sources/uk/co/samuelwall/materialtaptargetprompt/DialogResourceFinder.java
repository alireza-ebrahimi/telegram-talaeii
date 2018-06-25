package uk.co.samuelwall.materialtaptargetprompt;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.Window;

public class DialogResourceFinder extends ActivityResourceFinder {
    private final Dialog mDialog;

    public DialogResourceFinder(Dialog dialog) {
        super(dialog.getOwnerActivity());
        this.mDialog = dialog;
    }

    public View findViewById(@IdRes int resId) {
        return this.mDialog.findViewById(resId);
    }

    public Window getWindow() {
        return this.mDialog.getWindow();
    }

    public Context getContext() {
        return this.mDialog.getContext();
    }
}
