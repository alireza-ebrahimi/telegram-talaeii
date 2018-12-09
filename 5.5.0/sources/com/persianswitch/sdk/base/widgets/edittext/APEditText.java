package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.C0235a;
import android.support.v7.widget.C0193m;
import android.text.Editable;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.utils.func.Func1;

public class APEditText extends C0193m {
    /* renamed from: a */
    private Drawable f7337a;
    /* renamed from: b */
    private Func1<Void, Void> f7338b;

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APEditText$1 */
    class C22761 implements OnTouchListener {
        /* renamed from: a */
        final /* synthetic */ APEditText f7335a;

        C22761(APEditText aPEditText) {
            this.f7335a = aPEditText;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            APEditText aPEditText = this.f7335a;
            if (aPEditText.getCompoundDrawables()[2] != null && motionEvent.getAction() == 1 && motionEvent.getX() > ((float) (((aPEditText.getWidth() - aPEditText.getPaddingRight()) - this.f7335a.f7337a.getIntrinsicWidth()) - 15))) {
                if (this.f7335a.f7338b != null) {
                    this.f7335a.f7338b.m10774a(null);
                }
                aPEditText.setError(null);
                aPEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7335a.m11005b();
            }
            return false;
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.APEditText$2 */
    class C22772 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ APEditText f7336a;

        C22772(APEditText aPEditText) {
            this.f7336a = aPEditText;
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            this.f7336a.m11005b();
        }
    }

    public APEditText(Context context) {
        super(context);
        m11004a();
    }

    public APEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m11004a();
    }

    public APEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m11004a();
    }

    /* renamed from: a */
    void m11004a() {
        setEllipsize(TruncateAt.END);
        this.f7337a = C0235a.m1066a(getContext(), C2262R.drawable.asanpardakht_x_sign);
        this.f7337a.setBounds(0, 0, this.f7337a.getIntrinsicWidth(), this.f7337a.getIntrinsicHeight());
        m11005b();
        setOnTouchListener(new C22761(this));
        addTextChangedListener(new C22772(this));
    }

    /* renamed from: b */
    void m11005b() {
        if (getText().toString().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
        } else if (isEnabled()) {
            setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], this.f7337a, getCompoundDrawables()[3]);
        }
    }

    protected void onFocusChanged(boolean z, int i, Rect rect) {
        super.onFocusChanged(z, i, rect);
        m11005b();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        m11005b();
        return super.onTouchEvent(motionEvent);
    }

    public void setOnClearCallback(Func1<Void, Void> func1) {
        this.f7338b = func1;
    }
}
