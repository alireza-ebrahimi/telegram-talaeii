package org.telegram.messenger;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import org.ir.talaeii.R;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.BaseFragment;

class MessagesController$136 implements Runnable {
    final /* synthetic */ BaseFragment val$fragment;
    final /* synthetic */ AlertDialog[] val$progressDialog;
    final /* synthetic */ int val$reqId;

    /* renamed from: org.telegram.messenger.MessagesController$136$1 */
    class C14701 implements OnClickListener {
        C14701() {
        }

        public void onClick(DialogInterface dialog, int which) {
            ConnectionsManager.getInstance().cancelRequest(MessagesController$136.this.val$reqId, true);
            try {
                dialog.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    MessagesController$136(AlertDialog[] alertDialogArr, int i, BaseFragment baseFragment) {
        this.val$progressDialog = alertDialogArr;
        this.val$reqId = i;
        this.val$fragment = baseFragment;
    }

    public void run() {
        if (this.val$progressDialog[0] != null) {
            this.val$progressDialog[0].setMessage(LocaleController.getString("Loading", R.string.Loading));
            this.val$progressDialog[0].setCanceledOnTouchOutside(false);
            this.val$progressDialog[0].setCancelable(false);
            this.val$progressDialog[0].setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C14701());
            this.val$fragment.showDialog(this.val$progressDialog[0]);
        }
    }
}
