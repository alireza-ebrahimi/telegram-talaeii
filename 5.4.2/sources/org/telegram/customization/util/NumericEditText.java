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
import org.apache.p144a.p145a.C2450b;
import utils.view.FarsiTextView;

public class NumericEditText extends EditText {
    /* renamed from: i */
    private static Typeface f9456i;
    /* renamed from: a */
    private final char f9457a = DecimalFormatSymbols.getInstance().getGroupingSeparator();
    /* renamed from: b */
    private final char f9458b = DecimalFormatSymbols.getInstance().getDecimalSeparator();
    /* renamed from: c */
    private final String f9459c = "^0+(?!$)";
    /* renamed from: d */
    private String f9460d = null;
    /* renamed from: e */
    private String f9461e = TtmlNode.ANONYMOUS_REGION_ID;
    /* renamed from: f */
    private String f9462f = ("[^\\d\\" + this.f9458b + "]");
    /* renamed from: g */
    private char f9463g = this.f9458b;
    /* renamed from: h */
    private boolean f9464h = false;
    /* renamed from: j */
    private List<C2859a> f9465j = new ArrayList();
    /* renamed from: k */
    private final TextWatcher f9466k = new C28571(this);

    /* renamed from: org.telegram.customization.util.NumericEditText$1 */
    class C28571 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ NumericEditText f9453a;
        /* renamed from: b */
        private boolean f9454b = false;

        C28571(NumericEditText numericEditText) {
            this.f9453a = numericEditText;
        }

        public void afterTextChanged(Editable editable) {
            if (!this.f9454b) {
                if (C2450b.m11993a(editable.toString(), String.valueOf(this.f9453a.f9463g)) > 1) {
                    this.f9454b = true;
                    this.f9453a.setText(this.f9453a.f9461e);
                    this.f9453a.setSelection(this.f9453a.f9461e.length());
                    this.f9454b = false;
                } else if (editable.length() == 0) {
                    this.f9453a.m13312a();
                } else {
                    this.f9453a.setTextInternal(this.f9453a.m13310a(editable.toString()));
                    this.f9453a.setSelection(this.f9453a.getText().length());
                    this.f9453a.m13314b();
                }
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.customization.util.NumericEditText$2 */
    class C28582 implements OnClickListener {
        /* renamed from: a */
        final /* synthetic */ NumericEditText f9455a;

        C28582(NumericEditText numericEditText) {
            this.f9455a = numericEditText;
        }

        public void onClick(View view) {
            this.f9455a.setSelection(this.f9455a.getText().length());
        }
    }

    /* renamed from: org.telegram.customization.util.NumericEditText$a */
    public interface C2859a {
        /* renamed from: a */
        void m13306a();

        /* renamed from: a */
        void m13307a(double d);
    }

    public NumericEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        addTextChangedListener(this.f9466k);
        setOnClickListener(new C28582(this));
        setTypeface(m13309a(getContext()));
        setGravity(5);
    }

    /* renamed from: a */
    public static Typeface m13309a(Context context) {
        if (f9456i == null) {
            f9456i = FarsiTextView.a(context);
        }
        return f9456i;
    }

    /* renamed from: a */
    private String m13310a(String str) {
        String[] split = str.split("\\" + this.f9463g, -1);
        String replaceFirst = split[0].replaceAll(this.f9462f, TtmlNode.ANONYMOUS_REGION_ID).replaceFirst("^0+(?!$)", TtmlNode.ANONYMOUS_REGION_ID);
        if (!this.f9464h) {
            replaceFirst = C2450b.m11995a(C2450b.m11994a(C2450b.m11994a(replaceFirst).replaceAll("(.{3})", "$1" + this.f9457a)), String.valueOf(this.f9457a));
        }
        return split.length > 1 ? replaceFirst + this.f9463g + split[1] : replaceFirst;
    }

    /* renamed from: a */
    private void m13312a() {
        this.f9461e = TtmlNode.ANONYMOUS_REGION_ID;
        for (C2859a a : this.f9465j) {
            a.m13306a();
        }
    }

    /* renamed from: b */
    private void m13314b() {
        this.f9461e = getText().toString();
        for (C2859a a : this.f9465j) {
            a.m13307a(getNumericValue());
        }
    }

    private void setTextInternal(String str) {
        removeTextChangedListener(this.f9466k);
        setText(str);
        setTypeface(m13309a(getContext()));
        addTextChangedListener(this.f9466k);
    }

    public String getClearText() {
        return getText().toString().replaceAll(this.f9462f, TtmlNode.ANONYMOUS_REGION_ID);
    }

    public double getNumericValue() {
        String replaceAll = getText().toString().replaceAll(this.f9462f, TtmlNode.ANONYMOUS_REGION_ID);
        if (this.f9464h) {
            replaceAll = C2450b.m11996a(replaceAll, String.valueOf(this.f9463g), String.valueOf(this.f9458b));
        }
        try {
            return NumberFormat.getInstance().parse(replaceAll).doubleValue();
        } catch (ParseException e) {
            return Double.NaN;
        }
    }

    public void setCustomDecimalSeparator(char c) {
        this.f9463g = c;
        this.f9464h = true;
        this.f9462f = "[^\\d\\" + this.f9463g + "]";
    }
}
