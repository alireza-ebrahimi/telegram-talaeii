package com.persianswitch.sdk.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.persianswitch.sdk.C2262R;

public class ProgressDialog extends BaseDialogFragment {
    /* renamed from: a */
    private String f6997a;
    /* renamed from: b */
    private Typeface f6998b;
    /* renamed from: c */
    private Drawable f6999c;
    /* renamed from: d */
    private TextView f7000d;
    /* renamed from: e */
    private OnDismissListener f7001e;

    /* renamed from: a */
    public void m10484a(Typeface typeface) {
        this.f6998b = typeface;
    }

    /* renamed from: a */
    public void m10485a(Drawable drawable) {
        this.f6999c = drawable;
    }

    /* renamed from: a */
    public void mo3229a(Bundle bundle) {
    }

    /* renamed from: a */
    protected void mo3230a(View view, Bundle bundle) {
        ProgressBar progressBar = (ProgressBar) view.findViewById(C2262R.id.prg_default);
        ImageView imageView = (ImageView) view.findViewById(C2262R.id.img_progress);
        this.f7000d = (TextView) view.findViewById(C2262R.id.txt_message);
        if (this.f6999c != null) {
            imageView.setImageDrawable(this.f6999c);
            progressBar.setVisibility(8);
        }
        if (this.f6997a != null) {
            this.f7000d.setText(this.f6997a);
        }
        if (this.f6998b != null) {
            this.f7000d.setTypeface(this.f6998b);
        }
    }

    /* renamed from: a */
    public void m10488a(String str) {
        this.f6997a = str;
    }

    /* renamed from: b */
    protected int mo3231b() {
        return C2262R.layout.asanpardakht_dialog_progress;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, getTheme());
    }

    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        if (this.f7001e != null) {
            this.f7001e.onDismiss(dialogInterface);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
    }

    public void onStart() {
        super.onStart();
        LayoutParams layoutParams = new LayoutParams();
        Window window = getDialog().getWindow();
        layoutParams.copyFrom(window.getAttributes());
        layoutParams.width = -1;
        layoutParams.height = -1;
        window.setAttributes(layoutParams);
    }
}
