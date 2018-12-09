package com.persianswitch.sdk.payment.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelEditText;
import com.persianswitch.sdk.payment.model.Bank;

public class CardTextWatcher implements TextWatcher {
    /* renamed from: a */
    private final ApLabelEditText f7662a;

    public CardTextWatcher(ApLabelEditText apLabelEditText) {
        this.f7662a = apLabelEditText;
        this.f7662a.setStartImage(C2262R.drawable.asanpardakht_ic_bank_empty);
    }

    /* renamed from: a */
    private String m11449a(CharSequence charSequence) {
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        int i = 0;
        int i2 = 0;
        while (i < charSequence.length() - 1) {
            str = str + charSequence.charAt(i);
            i2++;
            if (i2 == 4) {
                str = str + "-";
                i2 = 0;
            }
            i++;
        }
        return str + (i <= charSequence.length() + -1 ? Character.valueOf(charSequence.charAt(i)) : TtmlNode.ANONYMOUS_REGION_ID);
    }

    /* renamed from: a */
    public void mo3313a(long j) {
    }

    public void afterTextChanged(Editable editable) {
        CharSequence obj = editable.toString();
        EditText editText = (EditText) this.f7662a.getInnerInput();
        if (!obj.startsWith("*")) {
            if (obj.length() > 0) {
                CharSequence a = m11449a(StringUtils.m10799a(obj));
                editText.removeTextChangedListener(this);
                editText.setText(a);
                editText.setSelection(editText.getText().length());
                editText.addTextChangedListener(this);
            }
            if (obj.length() > 7) {
                Bank a2 = Bank.m11115a(StringUtils.m10799a(obj));
                if (!(a2 == null || a2 == Bank.UNDEFINED)) {
                    mo3313a(a2.m11116a());
                }
                int i = 0;
                if (a2 != null) {
                    i = a2.m11119d();
                }
                if (i > 0) {
                    this.f7662a.setStartImage(i);
                    return;
                } else {
                    this.f7662a.setStartImage(-1);
                    return;
                }
            }
            this.f7662a.setStartImage(-1);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
