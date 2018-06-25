package com.persianswitch.sdk.base.utils.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import com.persianswitch.sdk.base.widgets.edittext.APAutoCompleteTextView;

public class EnableFilterOnChangeTextWatcher implements TextWatcher {
    /* renamed from: a */
    private final APAutoCompleteTextView f7134a;

    public void afterTextChanged(Editable editable) {
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        this.f7134a.setFilterEnabled(true);
        this.f7134a.removeTextChangedListener(this);
    }
}
