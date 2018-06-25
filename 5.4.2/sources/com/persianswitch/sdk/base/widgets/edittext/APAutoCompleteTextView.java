package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.util.AttributeSet;
import com.persianswitch.sdk.base.fastkit.FastFilterableAdapter;

public class APAutoCompleteTextView extends APBaseAutoCompleteTextView {
    public APAutoCompleteTextView(Context context) {
        super(context);
    }

    public APAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public APAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setFilterEnabled(boolean z) {
        if (getAdapter() instanceof FastFilterableAdapter) {
            ((FastFilterableAdapter) getAdapter()).m10628a(z);
        }
    }
}
