package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.util.AttributeSet;
import com.persianswitch.sdk.C2262R;

public class ApLabelAutoComplete extends ApLabelEditText {
    public ApLabelAutoComplete(Context context) {
        super(context);
    }

    public ApLabelAutoComplete(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ApLabelAutoComplete(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* renamed from: a */
    public void mo3285a() {
        super.mo3285a();
        getInnerInput().m11001c();
    }

    public APAutoCompleteTextView getInnerInput() {
        return (APAutoCompleteTextView) super.getInnerInput();
    }

    protected int getViewLayoutResourceId() {
        return C2262R.layout.asanpardakht_view_ap_label_auto_complate;
    }
}
