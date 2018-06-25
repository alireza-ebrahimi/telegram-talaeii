package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.util.AttributeSet;
import com.persianswitch.sdk.base.fastkit.FastFilterableAdapter;

public class APAutoCompleteTextView extends APBaseAutoCompleteTextView {
    public APAutoCompleteTextView(Context context) {
        super(context);
    }

    public APAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public APAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFilterEnabled(boolean filterEnabled) {
        if (getAdapter() instanceof FastFilterableAdapter) {
            ((FastFilterableAdapter) getAdapter()).setFilterEnabled(filterEnabled);
        }
    }
}
