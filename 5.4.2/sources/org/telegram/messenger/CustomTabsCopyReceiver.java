package org.telegram.messenger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import org.ir.talaeii.R;

public class CustomTabsCopyReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        CharSequence dataString = intent.getDataString();
        if (dataString != null) {
            AndroidUtilities.addToClipboard(dataString);
            Toast.makeText(context, LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
        }
    }
}
