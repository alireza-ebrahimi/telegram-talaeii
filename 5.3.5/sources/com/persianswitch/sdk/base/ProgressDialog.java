package com.persianswitch.sdk.base;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;

public class ProgressDialog extends BaseDialogFragment {
    private OnDismissListener dismissCallback;
    private Drawable drawable;
    private String message;
    private TextView txtMessage;
    private Typeface typeface;

    public static ProgressDialog getInstance() {
        return new ProgressDialog();
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(2, getTheme());
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.dismissCallback != null) {
            this.dismissCallback.onDismiss(dialog);
        }
    }

    protected int getLayoutResourceId() {
        return C0770R.layout.asanpardakht_dialog_progress;
    }

    protected void onFragmentCreated(View inflatedView, Bundle savedInstanceState) {
        ProgressBar defaultProgressBar = (ProgressBar) inflatedView.findViewById(C0770R.id.prg_default);
        ImageView imgCustomProgress = (ImageView) inflatedView.findViewById(C0770R.id.img_progress);
        this.txtMessage = (TextView) inflatedView.findViewById(C0770R.id.txt_message);
        if (this.drawable != null) {
            imgCustomProgress.setImageDrawable(this.drawable);
            defaultProgressBar.setVisibility(8);
        }
        if (this.message != null) {
            this.txtMessage.setText(this.message);
        }
        if (this.typeface != null) {
            this.txtMessage.setTypeface(this.typeface);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
    }

    public void onRecoverInstanceState(Bundle savedState) {
    }

    public void onStart() {
        super.onStart();
        LayoutParams lp = new LayoutParams();
        Window window = getDialog().getWindow();
        lp.copyFrom(window.getAttributes());
        lp.width = -1;
        lp.height = -1;
        window.setAttributes(lp);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void updateMessage() {
        try {
            this.txtMessage.setText(this.message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setOnDismiss(OnDismissListener dismissCallback) {
        this.dismissCallback = dismissCallback;
    }
}
