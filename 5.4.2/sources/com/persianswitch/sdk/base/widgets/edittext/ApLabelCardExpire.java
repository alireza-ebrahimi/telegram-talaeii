package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.widgets.APCustomView;
import java.util.Locale;

public class ApLabelCardExpire extends APCustomView {
    /* renamed from: a */
    private EditText f7348a;
    /* renamed from: b */
    private EditText f7349b;
    /* renamed from: c */
    private TextView f7350c;
    /* renamed from: d */
    private TextView f7351d;
    /* renamed from: e */
    private boolean f7352e;

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$1 */
    class C22781 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ ApLabelCardExpire f7344a;
        /* renamed from: b */
        private String f7345b;

        C22781(ApLabelCardExpire apLabelCardExpire) {
            this.f7344a = apLabelCardExpire;
        }

        public void afterTextChanged(Editable editable) {
            this.f7344a.setFieldEdited(true);
            this.f7344a.f7348a.removeTextChangedListener(this);
            try {
                if (editable.length() > 0) {
                    int parseInt = Integer.parseInt(editable.toString());
                    if (parseInt == 1 || (this.f7344a.f7348a.getText().length() == 1 && parseInt == 0)) {
                        this.f7344a.f7348a.setText(editable);
                    } else if (parseInt >= 2 && parseInt <= 12) {
                        this.f7344a.f7348a.setText(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(parseInt)}));
                    } else if (this.f7345b.length() < editable.length()) {
                        this.f7344a.f7348a.setText(this.f7345b);
                    }
                    this.f7344a.f7348a.setSelection(this.f7344a.f7348a.getText().length());
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.f7344a.f7348a.setText(this.f7345b);
            }
            if (this.f7344a.f7348a.getText().toString().length() == 2 && this.f7344a.f7349b.getText().toString().isEmpty()) {
                this.f7344a.f7349b.requestFocus();
            }
            this.f7344a.f7348a.addTextChangedListener(this);
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!(this.f7344a.m11015c() || this.f7344a.f7348a.getText().toString().equals(TtmlNode.ANONYMOUS_REGION_ID))) {
                this.f7344a.f7348a.removeTextChangedListener(this);
                this.f7344a.f7348a.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7344a.f7349b.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7344a.f7348a.addTextChangedListener(this);
            }
            this.f7345b = this.f7344a.f7348a.getText().toString();
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$2 */
    class C22792 implements TextWatcher {
        /* renamed from: a */
        final /* synthetic */ ApLabelCardExpire f7346a;

        C22792(ApLabelCardExpire apLabelCardExpire) {
            this.f7346a = apLabelCardExpire;
        }

        public void afterTextChanged(Editable editable) {
            this.f7346a.setFieldEdited(true);
            if (this.f7346a.f7349b.getText().toString().length() == 2 && this.f7346a.f7348a.getText().toString().isEmpty()) {
                this.f7346a.f7348a.requestFocus();
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!this.f7346a.m11015c() && !this.f7346a.f7349b.getText().toString().equals(TtmlNode.ANONYMOUS_REGION_ID)) {
                this.f7346a.f7349b.removeTextChangedListener(this);
                this.f7346a.f7348a.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7346a.f7349b.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.f7346a.f7349b.addTextChangedListener(this);
            }
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$3 */
    class C22803 implements OnFocusChangeListener {
        /* renamed from: a */
        final /* synthetic */ ApLabelCardExpire f7347a;

        C22803(ApLabelCardExpire apLabelCardExpire) {
            this.f7347a = apLabelCardExpire;
        }

        public void onFocusChange(View view, boolean z) {
            if (view.getId() == this.f7347a.getId() && z) {
                this.f7347a.f7348a.requestFocus();
            }
        }
    }

    public ApLabelCardExpire(Context context) {
        super(context);
    }

    public ApLabelCardExpire(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ApLabelCardExpire(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* renamed from: a */
    public void mo3285a() {
        if (StringUtils.m10803a(this.f7348a.getText().toString())) {
            this.f7348a.requestFocus();
        } else if (StringUtils.m10803a(this.f7349b.getText().toString())) {
            this.f7349b.requestFocus();
        }
        this.f7348a.requestFocus();
    }

    /* renamed from: a */
    protected void mo3286a(AttributeSet attributeSet) {
        float f = -1.0f;
        setAddStatesFromChildren(true);
        setOrientation(0);
        setGravity(17);
        int i = C2262R.drawable.asanpardakht_rounded_white_box_bg;
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        String str3 = TtmlNode.ANONYMOUS_REGION_ID;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C2262R.styleable.asanpardakht_AP);
            i = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_android_background, C2262R.drawable.asanpardakht_rounded_white_box_bg);
            str = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_asanpardakht_label);
            str2 = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_asanpardakht_monthHint);
            str3 = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_asanpardakht_yearHint);
            f = obtainStyledAttributes.getDimension(C2262R.styleable.asanpardakht_AP_asanpardakht_inputTextSize, -1.0f);
            obtainStyledAttributes.recycle();
        }
        float f2 = f;
        int i2 = i;
        String str4 = str;
        str = str2;
        str2 = str3;
        float f3 = f2;
        if (i2 > 0) {
            setBackgroundResource(i2);
        }
        this.f7350c = (TextView) findViewById(C2262R.id.ap_txt_label);
        this.f7351d = (TextView) findViewById(C2262R.id.ap_txt_separator);
        this.f7348a = (EditText) findViewById(C2262R.id.asanpardakht_edt_card_expiration_month);
        this.f7349b = (EditText) findViewById(C2262R.id.asanpardakht_edt_card_expiration_year);
        this.f7350c.setText(str4);
        this.f7351d.setText("/");
        if (f3 > BitmapDescriptorFactory.HUE_RED) {
            this.f7348a.setTextSize(0, f3);
            this.f7349b.setTextSize(0, f3);
        }
        this.f7348a.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{str})));
        this.f7349b.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{str2})));
        this.f7348a.setNextFocusForwardId(this.f7349b.getId());
        this.f7348a.addTextChangedListener(new C22781(this));
        this.f7349b.addTextChangedListener(new C22792(this));
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(new C22803(this));
    }

    /* renamed from: b */
    public void mo3287b() {
    }

    /* renamed from: c */
    public boolean m11015c() {
        return this.f7352e;
    }

    public EditText getMonthEditText() {
        return this.f7348a;
    }

    protected int getViewLayoutResourceId() {
        return C2262R.layout.asanpardakht_view_ap_label_card_expire;
    }

    public EditText getYearEditText() {
        return this.f7349b;
    }

    public void setFieldEdited(boolean z) {
        this.f7352e = z;
    }
}
