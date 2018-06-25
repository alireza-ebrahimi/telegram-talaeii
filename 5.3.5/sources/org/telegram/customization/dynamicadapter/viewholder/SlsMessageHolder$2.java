package org.telegram.customization.dynamicadapter.viewholder;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import org.telegram.messenger.ApplicationLoader;
import utils.app.AppPreferences;

class SlsMessageHolder$2 implements OnClickListener {
    final /* synthetic */ SlsMessageHolder this$0;

    /* renamed from: org.telegram.customization.dynamicadapter.viewholder.SlsMessageHolder$2$1 */
    class C12031 implements DialogInterface.OnClickListener {
        C12031() {
        }

        public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
    }

    SlsMessageHolder$2(SlsMessageHolder this$0) {
        this.this$0 = this$0;
    }

    public void onClick(View v) {
        if (AppPreferences.isShowFreePopup(this.this$0.getActivity())) {
            Builder alertDialogBuilder = new Builder(this.this$0.getActivity());
            alertDialogBuilder.setTitle("");
            String onClickMessage = AppPreferences.getFreeStateText(ApplicationLoader.applicationContext, SlsMessageHolder.access$000(this.this$0).getMessage().getFreeState());
            if (TextUtils.isEmpty(onClickMessage)) {
                onClickMessage = "";
            }
            if (!TextUtils.isEmpty(onClickMessage)) {
                alertDialogBuilder.setMessage(onClickMessage).setCancelable(true).setPositiveButton("گرفتم", new C12031());
                alertDialogBuilder.create().show();
            }
        }
    }
}
