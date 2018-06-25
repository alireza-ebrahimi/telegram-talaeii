package com.persianswitch.sdk.base;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

public class ProgressDialogBuilder {
    private Drawable drawable;
    private String message;
    private Typeface typeface;

    public ProgressDialogBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public ProgressDialogBuilder setTypeface(Typeface typeface) {
        this.typeface = typeface;
        return this;
    }

    public ProgressDialogBuilder setDrawable(Drawable drawable) {
        this.drawable = drawable;
        return this;
    }

    public ProgressDialogBuilder setDrawable(Context context, int resourceId) {
        this.drawable = ContextCompat.getDrawable(context, resourceId);
        return this;
    }

    public ProgressDialog build() {
        ProgressDialog progressDialog = new ProgressDialog();
        progressDialog.setMessage(this.message);
        progressDialog.setTypeface(this.typeface);
        progressDialog.setDrawable(this.drawable);
        return progressDialog;
    }
}
