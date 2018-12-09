package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsListener extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        boolean z;
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            z = false;
        } else {
            boolean equals = intent.getAction().equals("android.provider.Telephony.NEW_OUTGOING_SMS");
            if (equals) {
                z = equals;
            } else {
                return;
            }
        }
        if (AndroidUtilities.isWaitingForSms()) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                try {
                    Object[] objArr = (Object[]) extras.get("pdus");
                    SmsMessage[] smsMessageArr = new SmsMessage[objArr.length];
                    CharSequence charSequence = TtmlNode.ANONYMOUS_REGION_ID;
                    for (int i = 0; i < smsMessageArr.length; i++) {
                        smsMessageArr[i] = SmsMessage.createFromPdu((byte[]) objArr[i]);
                        charSequence = charSequence + smsMessageArr[i].getMessageBody();
                    }
                    if (!z) {
                        final Matcher matcher = Pattern.compile("[0-9]+").matcher(charSequence);
                        if (matcher.find() && matcher.group(0).length() >= 3) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceiveSmsCode, matcher.group(0));
                                }
                            });
                        }
                    }
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
        }
    }
}
