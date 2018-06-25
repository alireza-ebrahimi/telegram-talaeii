package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.C0235a;
import android.support.v7.widget.C0957g;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.utils.func.Action0;

public abstract class APBaseAutoCompleteTextView extends C0957g {
    /* renamed from: a */
    private Drawable f7330a;
    /* renamed from: b */
    private Action0 f7331b;
    /* renamed from: c */
    private OnTouchListener f7332c = new C22741(this);

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APBaseAutoCompleteTextView$1 */
    class C22741 implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ APBaseAutoCompleteTextView f7333a;

        C22741(APBaseAutoCompleteTextView aPBaseAutoCompleteTextView) {
            this.f7333a = aPBaseAutoCompleteTextView;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            APBaseAutoCompleteTextView aPBaseAutoCompleteTextView = this.f7333a;
            if (aPBaseAutoCompleteTextView.getCompoundDrawables()[2] != null && motionEvent.getAction() == 1 && motionEvent.getX() > ((float) ((aPBaseAutoCompleteTextView.getWidth() - aPBaseAutoCompleteTextView.getPaddingRight()) - this.f7333a.f7330a.getIntrinsicWidth()))) {
                aPBaseAutoCompleteTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7333a.m10999a();
                if (this.f7333a.f7331b != null) {
                    this.f7333a.f7331b.m10772a();
                }
            }
            return false;
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APBaseAutoCompleteTextView$2 */
    class C22752 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ APBaseAutoCompleteTextView f7334a;

        C22752(APBaseAutoCompleteTextView aPBaseAutoCompleteTextView) {
            this.f7334a = aPBaseAutoCompleteTextView;
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.f7334a.setError(null);
            this.f7334a.m10999a();
        }
    }

    public APBaseAutoCompleteTextView(Context context) {
        super(context);
        m10998d();
    }

    public APBaseAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m10998d();
    }

    public APBaseAutoCompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m10998d();
    }

    /* renamed from: d */
    private void m10998d() {
        setEllipsize(TruncateAt.END);
        try {
            setDropDownBackgroundDrawable(new ColorDrawable(C0235a.m1075c(getContext(), C2262R.color.asanpardakht_white)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        setFreezesText(true);
        this.f7330a = C0235a.m1066a(getContext(), C2262R.drawable.asanpardakht_x_sign);
        this.f7330a.setBounds(0, 0, this.f7330a.getIntrinsicWidth(), this.f7330a.getIntrinsicHeight());
        m10999a();
        setOnTouchListener(this.f7332c);
        addTextChangedListener(new C22752(this));
    }

    /* renamed from: a */
    void m10999a() {
        if (getText().toString().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        } else if (isEnabled()) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.f7330a, getCompoundDrawables()[3]);
        } else {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        }
    }

    /* renamed from: b */
    public void m11000b() {
        setOnTouchListener(this.f7332c);
    }

    /* renamed from: c */
    public void m11001c() {
        if (isEnabled()) {
            m10999a();
            showDropDown();
            setError(null);
            requestFocus();
            m10999a();
        }
    }

    public boolean enoughToFilter() {
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        try {
            if (motionEvent.getAction() == 1) {
                m11001c();
            }
        } catch (Exception e) {
        }
        return super.onTouchEvent(motionEvent);
    }
}
