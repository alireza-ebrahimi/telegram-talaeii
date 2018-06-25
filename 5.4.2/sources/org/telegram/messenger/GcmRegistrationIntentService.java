package org.telegram.messenger;

import android.app.IntentService;
import android.content.Intent;
import com.google.android.gms.iid.InstanceID;
import org.ir.talaeii.R;

public class GcmRegistrationIntentService extends IntentService {
    public GcmRegistrationIntentService() {
        super("GcmRegistrationIntentService");
    }

    private void sendRegistrationToServer(final String str) {
        Utilities.stageQueue.postRunnable(new Runnable() {

            /* renamed from: org.telegram.messenger.GcmRegistrationIntentService$3$1 */
            class C30611 implements Runnable {
                C30611() {
                }

                public void run() {
                    MessagesController.getInstance().registerForPush(str);
                }
            }

            public void run() {
                UserConfig.pushString = str;
                UserConfig.registeredForPush = false;
                UserConfig.saveConfig(false);
                if (UserConfig.getClientUserId() != 0) {
                    AndroidUtilities.runOnUIThread(new C30611());
                }
            }
        });
    }

    protected void onHandleIntent(Intent intent) {
        try {
            final String token = InstanceID.getInstance(this).getToken(getString(R.string.gcm_defaultSenderId), "GCM", null);
            FileLog.m13725d("GCM Registration Token: " + token);
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ApplicationLoader.postInitApplication();
                    GcmRegistrationIntentService.this.sendRegistrationToServer(token);
                }
            });
        } catch (Throwable e) {
            FileLog.m13728e(e);
            if (intent != null) {
                final int intExtra = intent.getIntExtra("failCount", 0);
                if (intExtra < 60) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            try {
                                Intent intent = new Intent(ApplicationLoader.applicationContext, GcmRegistrationIntentService.class);
                                intent.putExtra("failCount", intExtra + 1);
                                GcmRegistrationIntentService.this.startService(intent);
                            } catch (Throwable e) {
                                FileLog.m13728e(e);
                            }
                        }
                    }, intExtra < 20 ? 10000 : 1800000);
                }
            }
        }
    }
}
