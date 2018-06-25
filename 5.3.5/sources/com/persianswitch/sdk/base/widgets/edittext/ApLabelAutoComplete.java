package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.util.AttributeSet;
import com.persianswitch.sdk.C0770R;

public class ApLabelAutoComplete extends ApLabelEditText {
    public ApLabelAutoComplete(Context context) {
        super(context);
    }

    public ApLabelAutoComplete(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ApLabelAutoComplete(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onViewFocused() {
        super.onViewFocused();
        getInnerInput().showSuggestion();
    }

    protected int getViewLayoutResourceId() {
        return C0770R.layout.asanpardakht_view_ap_label_auto_complate;
    }

    public APAutoCompleteTextView getInnerInput() {
        return (APAutoCompleteTextView) super.getInnerInput();
    }
}
