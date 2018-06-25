package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.utils.strings.StringUtils;
import com.persianswitch.sdk.base.widgets.APCustomView;
import java.util.Locale;

public class ApLabelCardExpire extends APCustomView {
    private static final String TAG = "ApLabelCardExpire";
    private EditText edtMonth;
    private EditText edtYear;
    private boolean isFieldEdited;
    private TextView txtLabel;
    private TextView txtSeparator;

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$1 */
    class C07861 implements TextWatcher {
        private String beforeStr;

        C07861() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!(ApLabelCardExpire.this.isFieldEdited() || ApLabelCardExpire.this.edtMonth.getText().toString().equals(""))) {
                ApLabelCardExpire.this.edtMonth.removeTextChangedListener(this);
                ApLabelCardExpire.this.edtMonth.setText("");
                ApLabelCardExpire.this.edtYear.setText("");
                ApLabelCardExpire.this.edtMonth.addTextChangedListener(this);
            }
            this.beforeStr = ApLabelCardExpire.this.edtMonth.getText().toString();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            ApLabelCardExpire.this.setFieldEdited(true);
            ApLabelCardExpire.this.edtMonth.removeTextChangedListener(this);
            try {
                if (s.length() > 0) {
                    int intValue = Integer.parseInt(s.toString());
                    if (intValue == 1 || (ApLabelCardExpire.this.edtMonth.getText().length() == 1 && intValue == 0)) {
                        ApLabelCardExpire.this.edtMonth.setText(s);
                    } else if (intValue >= 2 && intValue <= 12) {
                        ApLabelCardExpire.this.edtMonth.setText(String.format(Locale.US, "%02d", new Object[]{Integer.valueOf(intValue)}));
                    } else if (this.beforeStr.length() < s.length()) {
                        ApLabelCardExpire.this.edtMonth.setText(this.beforeStr);
                    }
                    ApLabelCardExpire.this.edtMonth.setSelection(ApLabelCardExpire.this.edtMonth.getText().length());
                }
            } catch (Exception e) {
                e.printStackTrace();
                ApLabelCardExpire.this.edtMonth.setText(this.beforeStr);
            }
            if (ApLabelCardExpire.this.edtMonth.getText().toString().length() == 2 && ApLabelCardExpire.this.edtYear.getText().toString().isEmpty()) {
                ApLabelCardExpire.this.edtYear.requestFocus();
            }
            ApLabelCardExpire.this.edtMonth.addTextChangedListener(this);
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$2 */
    class C07872 implements TextWatcher {
        C07872() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (!ApLabelCardExpire.this.isFieldEdited() && !ApLabelCardExpire.this.edtYear.getText().toString().equals("")) {
                ApLabelCardExpire.this.edtYear.removeTextChangedListener(this);
                ApLabelCardExpire.this.edtMonth.setText("");
                ApLabelCardExpire.this.edtYear.setText("");
                ApLabelCardExpire.this.edtYear.addTextChangedListener(this);
            }
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            ApLabelCardExpire.this.setFieldEdited(true);
            if (ApLabelCardExpire.this.edtYear.getText().toString().length() == 2 && ApLabelCardExpire.this.edtMonth.getText().toString().isEmpty()) {
                ApLabelCardExpire.this.edtMonth.requestFocus();
            }
        }
    }

    /* renamed from: com.persianswitch.sdk.base.widgets.edittext.ApLabelCardExpire$3 */
    class C07883 implements OnFocusChangeListener {
        C07883() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            if (v.getId() == ApLabelCardExpire.this.getId() && hasFocus) {
                ApLabelCardExpire.this.edtMonth.requestFocus();
            }
        }
    }

    public ApLabelCardExpire(Context context) {
        super(context);
    }

    public ApLabelCardExpire(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ApLabelCardExpire(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void onViewFocused() {
        if (StringUtils.isEmpty(this.edtMonth.getText().toString())) {
            this.edtMonth.requestFocus();
        } else if (StringUtils.isEmpty(this.edtYear.getText().toString())) {
            this.edtYear.requestFocus();
        }
        this.edtMonth.requestFocus();
    }

    protected int getViewLayoutResourceId() {
        return C0770R.layout.asanpardakht_view_ap_label_card_expire;
    }

    protected void initialView(@Nullable AttributeSet attrs) {
        setAddStatesFromChildren(true);
        setOrientation(0);
        setGravity(17);
        int backgroundRes = C0770R.drawable.asanpardakht_rounded_white_box_bg;
        String label = "";
        String monthHint = "";
        String yearHint = "";
        float inputTextSize = -1.0f;
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, C0770R.styleable.asanpardakht_AP);
            backgroundRes = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_android_background, C0770R.drawable.asanpardakht_rounded_white_box_bg);
            label = typedArray.getString(C0770R.styleable.asanpardakht_AP_asanpardakht_label);
            monthHint = typedArray.getString(C0770R.styleable.asanpardakht_AP_asanpardakht_monthHint);
            yearHint = typedArray.getString(C0770R.styleable.asanpardakht_AP_asanpardakht_yearHint);
            inputTextSize = typedArray.getDimension(C0770R.styleable.asanpardakht_AP_asanpardakht_inputTextSize, -1.0f);
            typedArray.recycle();
        }
        if (backgroundRes > 0) {
            setBackgroundResource(backgroundRes);
        }
        this.txtLabel = (TextView) findViewById(C0770R.id.ap_txt_label);
        this.txtSeparator = (TextView) findViewById(C0770R.id.ap_txt_separator);
        this.edtMonth = (EditText) findViewById(C0770R.id.asanpardakht_edt_card_expiration_month);
        this.edtYear = (EditText) findViewById(C0770R.id.asanpardakht_edt_card_expiration_year);
        this.txtLabel.setText(label);
        this.txtSeparator.setText("/");
        if (inputTextSize > 0.0f) {
            this.edtMonth.setTextSize(0, inputTextSize);
            this.edtYear.setTextSize(0, inputTextSize);
        }
        this.edtMonth.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{monthHint})));
        this.edtYear.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{yearHint})));
        this.edtMonth.setNextFocusForwardId(this.edtYear.getId());
        this.edtMonth.addTextChangedListener(new C07861());
        this.edtYear.addTextChangedListener(new C07872());
        setFocusable(true);
        setFocusableInTouchMode(true);
        setOnFocusChangeListener(new C07883());
    }

    public void updateView() {
    }

    public EditText getMonthEditText() {
        return this.edtMonth;
    }

    public EditText getYearEditText() {
        return this.edtYear;
    }

    public boolean isFieldEdited() {
        return this.isFieldEdited;
    }

    public void setFieldEdited(boolean fieldEdited) {
        this.isFieldEdited = fieldEdited;
    }
}
