package com.persianswitch.sdk.payment.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.widgets.edittext.ApLabelEditText;
import com.persianswitch.sdk.payment.model.Bank;

public class CardTextWatcher implements TextWatcher {
    private final ApLabelEditText mEdtCardNo;

    public CardTextWatcher(ApLabelEditText edtCardNo) {
        this.mEdtCardNo = edtCardNo;
        this.mEdtCardNo.setStartImage(C0770R.drawable.asanpardakht_ic_bank_empty);
    }

    public void afterTextChanged(Editable s) {
        String currentString = s.toString();
        EditText editText = (EditText) this.mEdtCardNo.getInnerInput();
        if (!currentString.startsWith("*")) {
            if (currentString.length() > 0) {
                String code = separate4DigitWithDash(StringUtils.keepNumbersOnly(currentString));
                editText.removeTextChangedListener(this);
                editText.setText(code);
                editText.setSelection(editText.getText().length());
                editText.addTextChangedListener(this);
            }
            if (currentString.length() > 7) {
                Bank bankByCardNo = Bank.getByCardNo(StringUtils.keepNumbersOnly(currentString));
                if (!(bankByCardNo == null || bankByCardNo == Bank.UNDEFINED)) {
                    onBankIdReady(bankByCardNo.getBankId());
                }
                int bankLogo = 0;
                if (bankByCardNo != null) {
                    bankLogo = bankByCardNo.getBankLogoResource();
                }
                if (bankLogo > 0) {
                    this.mEdtCardNo.setStartImage(bankLogo);
                    return;
                } else {
                    this.mEdtCardNo.setStartImage(-1);
                    return;
                }
            }
            this.mEdtCardNo.setStartImage(-1);
        }
    }

    private String separate4DigitWithDash(CharSequence s) {
        int groupDigits = 0;
        String tmp = "";
        int i = 0;
        while (i < s.length() - 1) {
            tmp = tmp + s.charAt(i);
            groupDigits++;
            if (groupDigits == 4) {
                tmp = tmp + "-";
                groupDigits = 0;
            }
            i++;
        }
        return tmp + (i <= s.length() + -1 ? Character.valueOf(s.charAt(i)) : "");
    }

    public void onBankIdReady(long bankId) {
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }
}
