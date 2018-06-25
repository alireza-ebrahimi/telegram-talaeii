package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.utils.func.Func1;

public class APEditText extends AppCompatEditText {
    private Drawable imgCloseButton;
    private Func1<Void, Void> onClearCallback;

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APEditText$1 */
    class C07841 implements OnTouchListener {
        C07841() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            APEditText et = APEditText.this;
            if (et.getCompoundDrawables()[2] != null && event.getAction() == 1 && event.getX() > ((float) (((et.getWidth() - et.getPaddingRight()) - APEditText.this.imgCloseButton.getIntrinsicWidth()) - 15))) {
                if (APEditText.this.onClearCallback != null) {
                    APEditText.this.onClearCallback.apply(null);
                }
                et.setError(null);
                et.setText("");
                APEditText.this.handleClearButton();
            }
            return false;
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APEditText$2 */
    class C07852 implements TextWatcher {
        C07852() {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            APEditText.this.handleClearButton();
        }

        public void afterTextChanged(Editable arg0) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    }

    public APEditText(Context context) {
        super(context);
        init();
    }

    public APEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public APEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init() {
        setEllipsize(TruncateAt.END);
        this.imgCloseButton = ContextCompat.getDrawable(getContext(), C0770R.drawable.asanpardakht_x_sign);
        this.imgCloseButton.setBounds(0, 0, this.imgCloseButton.getIntrinsicWidth(), this.imgCloseButton.getIntrinsicHeight());
        handleClearButton();
        setOnTouchListener(new C07841());
        addTextChangedListener(new C07852());
    }

    void handleClearButton() {
        if (getText().toString().equals("")) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        } else if (isEnabled()) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.imgCloseButton, getCompoundDrawables()[3]);
        }
    }

    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        handleClearButton();
    }

    public boolean onTouchEvent(MotionEvent event) {
        handleClearButton();
        return super.onTouchEvent(event);
    }

    public void removeClearButton() {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }

    public void setOnClearCallback(Func1<Void, Void> onClearCallback) {
        this.onClearCallback = onClearCallback;
    }
}
