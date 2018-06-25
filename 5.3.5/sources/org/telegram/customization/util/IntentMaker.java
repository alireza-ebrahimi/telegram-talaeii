package org.telegram.customization.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.util.view.VideoActivity;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.LaunchActivity;

public class IntentMaker {
    public static void goToChannel(long dialogId, long messageId, Activity activity, String username) {
        if (MessagesController.getInstance().getChat(Integer.valueOf((int) dialogId)) == null) {
            FileLog.d("goToChannel 1");
            final ArrayList<Boolean> cancel = new ArrayList();
            final ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialog) {
                    FileLog.d("goToChannel 2");
                    cancel.add(Boolean.valueOf(true));
                }
            });
            progressDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    FileLog.d("goToChannel 3");
                    cancel.add(Boolean.valueOf(true));
                }
            });
            final long j = dialogId;
            final String str = username;
            final long j2 = messageId;
            final Activity activity2 = activity;
            MessagesController.loadChannelInfoByUsername(username, new IResponseReceiver() {

                /* renamed from: org.telegram.customization.util.IntentMaker$3$1 */
                class C12321 implements Runnable {
                    C12321() {
                    }

                    public void run() {
                        try {
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                        } catch (Exception e) {
                        }
                    }
                }

                public void onResult(Object object, int StatusCode) {
                    FileLog.d("goToChannel 4");
                    if (cancel.isEmpty()) {
                        if (progressDialog != null) {
                            AndroidUtilities.runOnUIThread(new C12321());
                        }
                        FileLog.d("goToChannel 5");
                        if (MessagesController.getInstance().getChat(Integer.valueOf((int) j)) == null) {
                            FileLog.d("goToChannel 6");
                            if (!TextUtils.isEmpty(str)) {
                                FileLog.d("goToChannel 7");
                                MessagesController.openByUserName(str, (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                                return;
                            }
                            return;
                        }
                        FileLog.d("goToChannel 8");
                        IntentMaker.internallyGoToChannel(j, j2, activity2);
                    }
                }
            });
            return;
        }
        internallyGoToChannel(dialogId, messageId, activity);
    }

    private static void internallyGoToChannel(long dialogId, long messageId, Activity activity) {
        Bundle args = new Bundle();
        int high_id = (int) (dialogId >> 32);
        args.putInt("chat_id", (int) dialogId);
        if (messageId != 0) {
            args.putInt("message_id", (int) messageId);
        }
        if (MessagesController.checkCanOpenChat(args, new BaseFragment())) {
            ((LaunchActivity) activity).presentFragment(new ChatActivity(args));
        }
    }

    public static Intent makeVideoIntent(Context context, String address) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(Constants.EXTRA_VIDEO_URL, address);
        return intent;
    }
}
