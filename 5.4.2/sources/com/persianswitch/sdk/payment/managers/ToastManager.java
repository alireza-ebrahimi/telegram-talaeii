package com.persianswitch.sdk.payment.managers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.manager.FontManager;

public class ToastManager {
    /* renamed from: a */
    static ToastManager f7406a;
    /* renamed from: b */
    private Context f7407b;
    /* renamed from: c */
    private Toast f7408c;
    /* renamed from: d */
    private TextView f7409d;

    private ToastManager(Context context) {
        this.f7407b = context;
    }

    /* renamed from: a */
    private static ToastManager m11107a(Context context) {
        if (f7406a == null) {
            f7406a = new ToastManager(context);
        }
        return f7406a;
    }

    /* renamed from: a */
    public static void m11108a(Context context, String str) {
        m11107a(context).m11109a(str);
    }

    /* renamed from: a */
    private void m11109a(String str) {
        if (this.f7408c == null) {
            this.f7408c = new Toast(this.f7407b);
            this.f7408c.setDuration(0);
            View inflate = LayoutInflater.from(this.f7407b).inflate(C2262R.layout.asanpardakht_lyt_toast, null);
            this.f7409d = (TextView) inflate.findViewById(C2262R.id.txt_toast_message);
            FontManager.m10664a(this.f7409d);
            this.f7408c.setView(inflate);
        }
        this.f7409d.setText(str);
        this.f7408c.show();
    }
}
