package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.manager.FontManager;

public class ToastManager {
    static ToastManager instance;
    private Context mContext;
    private Toast mToast;
    private TextView mToastTextView;

    private static ToastManager getInstance(Context context) {
        if (instance == null) {
            instance = new ToastManager(context);
        }
        return instance;
    }

    public static void showSharedToast(Context context, String message) {
        getInstance(context).showToast(message);
    }

    private ToastManager(Context context) {
        this.mContext = context;
    }

    private void showToast(String message) {
        if (this.mToast == null) {
            this.mToast = new Toast(this.mContext);
            this.mToast.setDuration(0);
            View toastView = LayoutInflater.from(this.mContext).inflate(C0770R.layout.asanpardakht_lyt_toast, null);
            this.mToastTextView = (TextView) toastView.findViewById(C0770R.id.txt_toast_message);
            FontManager.overrideFonts(this.mToastTextView);
            this.mToast.setView(toastView);
        }
        this.mToastTextView.setText(message);
        this.mToast.show();
    }
}
