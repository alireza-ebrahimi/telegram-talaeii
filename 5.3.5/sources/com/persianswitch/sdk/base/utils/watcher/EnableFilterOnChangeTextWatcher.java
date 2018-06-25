package com.persianswitch.sdk.base.utils.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import com.persianswitch.sdk.base.widgets.edittext.APAutoCompleteTextView;

public class EnableFilterOnChangeTextWatcher implements TextWatcher {
    private final APAutoCompleteTextView mAutoCompleteTextView;

    public static void watch(APAutoCompleteTextView autoCompleteTextView) {
        if (autoCompleteTextView != null) {
            autoCompleteTextView.addTextChangedListener(new EnableFilterOnChangeTextWatcher(autoCompleteTextView));
        }
    }

    private EnableFilterOnChangeTextWatcher(APAutoCompleteTextView autoCompleteTextView) {
        this.mAutoCompleteTextView = autoCompleteTextView;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        this.mAutoCompleteTextView.setFilterEnabled(true);
        this.mAutoCompleteTextView.removeTextChangedListener(this);
    }

    public void afterTextChanged(Editable editable) {
    }
}
