package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.utils.func.Action0;

public abstract class APBaseAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private OnTouchListener autoCompleteDefaultTouchListener = new C07821();
    private Drawable imgCloseButton;
    private Action0 onClearCallback;

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APBaseAutoCompleteTextView$1 */
    class C07821 implements OnTouchListener {
        C07821() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            APBaseAutoCompleteTextView et = APBaseAutoCompleteTextView.this;
            if (et.getCompoundDrawables()[2] != null && event.getAction() == 1 && event.getX() > ((float) ((et.getWidth() - et.getPaddingRight()) - APBaseAutoCompleteTextView.this.imgCloseButton.getIntrinsicWidth()))) {
                et.setText("");
                APBaseAutoCompleteTextView.this.handleClearButton();
                if (APBaseAutoCompleteTextView.this.onClearCallback != null) {
                    APBaseAutoCompleteTextView.this.onClearCallback.call();
                }
            }
            return false;
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APBaseAutoCompleteTextView$2 */
    class C07832 implements TextWatcher {
        C07832() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            APBaseAutoCompleteTextView.this.setError(null);
            APBaseAutoCompleteTextView.this.handleClearButton();
        }

        public void afterTextChanged(Editable arg0) {
        }
    }

    public APBaseAutoCompleteTextView(Context context) {
        super(context);
        init();
    }

    public APBaseAutoCompleteTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public APBaseAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setEllipsize(TruncateAt.END);
        try {
            setDropDownBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), C0770R.color.asanpardakht_white)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setFreezesText(true);
        this.imgCloseButton = ContextCompat.getDrawable(getContext(), C0770R.drawable.asanpardakht_x_sign);
        this.imgCloseButton.setBounds(0, 0, this.imgCloseButton.getIntrinsicWidth(), this.imgCloseButton.getIntrinsicHeight());
        handleClearButton();
        setOnTouchListener(this.autoCompleteDefaultTouchListener);
        addTextChangedListener(new C07832());
    }

    void handleClearButton() {
        if (getText().toString().equals("")) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        } else if (isEnabled()) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.imgCloseButton, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }
    }

    public void setDefaultTouchListener() {
        setOnTouchListener(this.autoCompleteDefaultTouchListener);
    }

    public boolean enoughToFilter() {
        return true;
    }

    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (event.getAction() == 1) {
                showSuggestion();
            }
        } catch (Exception e) {
        }
        return super.onTouchEvent(event);
    }

    public void showSuggestion() {
        if (isEnabled()) {
            handleClearButton();
            showDropDown();
            setError(null);
            requestFocus();
            handleClearButton();
        }
    }
}
