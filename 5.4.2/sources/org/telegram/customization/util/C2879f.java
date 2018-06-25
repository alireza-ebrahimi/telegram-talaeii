package org.telegram.customization.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.customization.p151g.C2497d;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.LaunchActivity;

/* renamed from: org.telegram.customization.util.f */
public class C2879f {
    /* renamed from: a */
    public static void m13356a(long j, long j2, Activity activity, String str) {
        if (MessagesController.getInstance().getChat(Integer.valueOf((int) j)) == null) {
            FileLog.m13725d("goToChannel 1");
            final ArrayList arrayList = new ArrayList();
            final ProgressDialog progressDialog = new ProgressDialog(activity);
            progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setCancelable(true);
            progressDialog.show();
            progressDialog.setOnCancelListener(new OnCancelListener() {
                public void onCancel(DialogInterface dialogInterface) {
                    FileLog.m13725d("goToChannel 2");
                    arrayList.add(Boolean.valueOf(true));
                }
            });
            progressDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialogInterface) {
                    FileLog.m13725d("goToChannel 3");
                    arrayList.add(Boolean.valueOf(true));
                }
            });
            final long j3 = j;
            final String str2 = str;
            final long j4 = j2;
            final Activity activity2 = activity;
            MessagesController.loadChannelInfoByUsername(str, new C2497d() {

                /* renamed from: org.telegram.customization.util.f$3$1 */
                class C28771 implements Runnable {
                    /* renamed from: a */
                    final /* synthetic */ C28783 f9493a;

                    C28771(C28783 c28783) {
                        this.f9493a = c28783;
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

                public void onResult(Object obj, int i) {
                    FileLog.m13725d("goToChannel 4");
                    if (arrayList.isEmpty()) {
                        if (progressDialog != null) {
                            AndroidUtilities.runOnUIThread(new C28771(this));
                        }
                        FileLog.m13725d("goToChannel 5");
                        if (MessagesController.getInstance().getChat(Integer.valueOf((int) j3)) == null) {
                            FileLog.m13725d("goToChannel 6");
                            if (!TextUtils.isEmpty(str2)) {
                                FileLog.m13725d("goToChannel 7");
                                MessagesController.openByUserName(str2, (BaseFragment) LaunchActivity.mainFragmentsStack.get(LaunchActivity.mainFragmentsStack.size() - 1), 1);
                                return;
                            }
                            return;
                        }
                        FileLog.m13725d("goToChannel 8");
                        C2879f.m13357b(j3, j4, activity2);
                    }
                }
            });
            return;
        }
        C2879f.m13357b(j, j2, activity);
    }

    /* renamed from: b */
    private static void m13357b(long j, long j2, Activity activity) {
        Bundle bundle = new Bundle();
        int i = (int) (j >> 32);
        bundle.putInt("chat_id", (int) j);
        if (j2 != 0) {
            bundle.putInt("message_id", (int) j2);
        }
        if (MessagesController.checkCanOpenChat(bundle, new BaseFragment())) {
            ((LaunchActivity) activity).presentFragment(new ChatActivity(bundle));
        }
    }
}
