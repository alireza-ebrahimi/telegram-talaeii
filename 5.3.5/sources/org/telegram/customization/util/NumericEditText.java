package org.telegram.customization.util;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import utils.view.FarsiTextView;

public class NumericEditText extends EditText {
    private static Typeface defaultTypeface;
    private final char DECIMAL_SEPARATOR = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    private final char GROUPING_SEPARATOR = DecimalFormatSymbols.getInstance().getGroupingSeparator();
    private final String LEADING_ZERO_FILTER_REGEX = "^0+(?!$)";
    private boolean hasCustomDecimalSeparator = false;
    private char mDecimalSeparator = this.DECIMAL_SEPARATOR;
    private String mDefaultText = null;
    private String mNumberFilterRegex = ("[^\\d\\" + this.DECIMAL_SEPARATOR + "]");
    private List<NumericValueWatcher> mNumericListeners = new ArrayList();
    private String mPreviousText = "";
    private final TextWatcher mTextWatcher = new C12341();

    /* renamed from: org.telegram.customization.util.NumericEditText$1 */
    class C12341 implements TextWatcher {
        private boolean validateLock = false;

        C12341() {
        }

        public void afterTextChanged(Editable s) {
            if (!this.validateLock) {
                if (StringUtils.countMatches(s.toString(), String.valueOf(NumericEditText.this.mDecimalSeparator)) > 1) {
                    this.validateLock = true;
                    NumericEditText.this.setText(NumericEditText.this.mPreviousText);
                    NumericEditText.this.setSelection(NumericEditText.this.mPreviousText.length());
                    this.validateLock = false;
                } else if (s.length() == 0) {
                    NumericEditText.this.handleNumericValueCleared();
                } else {
                    NumericEditText.this.setTextInternal(NumericEditText.this.format(s.toString()));
                    NumericEditText.this.setSelection(NumericEditText.this.getText().length());
                    NumericEditText.this.handleNumericValueChanged();
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    }

    /* renamed from: org.telegram.customization.util.NumericEditText$2 */
    class C12352 implements OnClickListener {
        C12352() {
        }

        public void onClick(View v) {
            NumericEditText.this.setSelection(NumericEditText.this.getText().length());
        }
    }

    public interface NumericValueWatcher {
        void onChanged(double d);

        void onCleared();
    }

    public static Typeface getTypeface(Context ctx) {
        if (defaultTypeface == null) {
            defaultTypeface = FarsiTextView.getTypeface(ctx);
        }
        return defaultTypeface;
    }

    private void handleNumericValueCleared() {
        this.mPreviousText = "";
        for (NumericValueWatcher listener : this.mNumericListeners) {
            listener.onCleared();
        }
    }

    private void handleNumericValueChanged() {
        this.mPreviousText = getText().toString();
        for (NumericValueWatcher listener : this.mNumericListeners) {
            listener.onChanged(getNumericValue());
        }
    }

    public NumericEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this.mTextWatcher);
        setOnClickListener(new C12352());
        setTypeface(getTypeface(getContext()));
        setGravity(5);
    }

    public void addNumericValueChangedListener(NumericValueWatcher watcher) {
        this.mNumericListeners.add(watcher);
    }

    public void removeAllNumericValueChangedListeners() {
        while (!this.mNumericListeners.isEmpty()) {
            this.mNumericListeners.remove(0);
        }
    }

    public void setDefaultNumericValue(double defaultNumericValue, String defaultNumericFormat) {
        this.mDefaultText = String.format(defaultNumericFormat, new Object[]{Double.valueOf(defaultNumericValue)});
        if (this.hasCustomDecimalSeparator) {
            this.mDefaultText = StringUtils.replace(this.mDefaultText, String.valueOf(this.DECIMAL_SEPARATOR), String.valueOf(this.mDecimalSeparator));
        }
        setTextInternal(this.mDefaultText);
    }

    public void setCustomDecimalSeparator(char customDecimalSeparator) {
        this.mDecimalSeparator = customDecimalSeparator;
        this.hasCustomDecimalSeparator = true;
        this.mNumberFilterRegex = "[^\\d\\" + this.mDecimalSeparator + "]";
    }

    public void clear() {
        setTextInternal(this.mDefaultText != null ? this.mDefaultText : "");
        if (this.mDefaultText != null) {
            handleNumericValueChanged();
        }
    }

    public String getClearText() {
        return getText().toString().replaceAll(this.mNumberFilterRegex, "");
    }

    public double getNumericValue() {
        String original = getText().toString().replaceAll(this.mNumberFilterRegex, "");
        if (this.hasCustomDecimalSeparator) {
            original = StringUtils.replace(original, String.valueOf(this.mDecimalSeparator), String.valueOf(this.DECIMAL_SEPARATOR));
        }
        try {
            return NumberFormat.getInstance().parse(original).doubleValue();
        } catch (ParseException e) {
            return Double.NaN;
        }
    }

    private String format(String original) {
        String[] parts = original.split("\\" + this.mDecimalSeparator, -1);
        String number = parts[0].replaceAll(this.mNumberFilterRegex, "").replaceFirst("^0+(?!$)", "");
        if (!this.hasCustomDecimalSeparator) {
            number = StringUtils.removeStart(StringUtils.reverse(StringUtils.reverse(number).replaceAll("(.{3})", "$1" + this.GROUPING_SEPARATOR)), String.valueOf(this.GROUPING_SEPARATOR));
        }
        if (parts.length > 1) {
            return number + this.mDecimalSeparator + parts[1];
        }
        return number;
    }

    private void setTextInternal(String text) {
        removeTextChangedListener(this.mTextWatcher);
        setText(text);
        setTypeface(getTypeface(getContext()));
        addTextChangedListener(this.mTextWatcher);
    }
}
