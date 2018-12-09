package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.persianswitch.sdk.C2262R;
import com.persianswitch.sdk.base.widgets.APCustomView;

public class ApLabelEditText extends APCustomView {
    /* renamed from: a */
    private boolean f7339a;
    /* renamed from: b */
    private TextView f7340b;
    /* renamed from: c */
    private TextView f7341c;
    /* renamed from: d */
    private ImageView f7342d;
    /* renamed from: e */
    private ImageView f7343e;

    public ApLabelEditText(Context context) {
        super(context);
    }

    public ApLabelEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ApLabelEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* renamed from: a */
    public void mo3285a() {
        this.f7341c.requestFocus();
    }

    /* renamed from: a */
    protected void mo3286a(AttributeSet attributeSet) {
        setAddStatesFromChildren(true);
        setOrientation(0);
        setGravity(17);
        int i = C2262R.drawable.asanpardakht_rounded_white_box_bg;
        String str = TtmlNode.ANONYMOUS_REGION_ID;
        String str2 = TtmlNode.ANONYMOUS_REGION_ID;
        String str3 = TtmlNode.ANONYMOUS_REGION_ID;
        int i2 = 0;
        int i3 = 0;
        int i4 = 20;
        int i5 = 17;
        int i6 = 1;
        int i7 = 0;
        int i8 = 1;
        float f = -1.0f;
        int i9 = 0;
        int i10 = -1;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, C2262R.styleable.asanpardakht_AP);
            i = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_android_background, C2262R.drawable.asanpardakht_rounded_white_box_bg);
            str = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_android_text);
            str2 = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_android_hint);
            str3 = obtainStyledAttributes.getString(C2262R.styleable.asanpardakht_AP_asanpardakht_label);
            i2 = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_asanpardakht_rightImage, 0);
            i3 = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_asanpardakht_leftImage, 0);
            i4 = obtainStyledAttributes.getInteger(C2262R.styleable.asanpardakht_AP_android_maxLength, 20);
            i8 = obtainStyledAttributes.getInteger(C2262R.styleable.asanpardakht_AP_android_lines, 1);
            i5 = obtainStyledAttributes.getInt(C2262R.styleable.asanpardakht_AP_android_gravity, 17);
            i6 = obtainStyledAttributes.getInt(C2262R.styleable.asanpardakht_AP_android_inputType, 1);
            i7 = obtainStyledAttributes.getInt(C2262R.styleable.asanpardakht_AP_android_imeOptions, 0);
            f = obtainStyledAttributes.getDimension(C2262R.styleable.asanpardakht_AP_asanpardakht_inputTextSize, -1.0f);
            i9 = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_asanpardakht_upInput, -1);
            i10 = obtainStyledAttributes.getResourceId(C2262R.styleable.asanpardakht_AP_asanpardakht_nextInput, -1);
            obtainStyledAttributes.recycle();
        }
        String str4 = str;
        str = str3;
        int i11 = i3;
        i3 = i5;
        i5 = i7;
        float f2 = f;
        int i12 = i10;
        i10 = i;
        String str5 = str2;
        int i13 = i2;
        i2 = i4;
        i4 = i6;
        i6 = i8;
        i8 = i9;
        if (i10 > 0) {
            setBackgroundResource(i10);
        }
        this.f7340b = (TextView) findViewById(C2262R.id.ap_txt_label);
        this.f7340b.setFreezesText(true);
        View findViewById = findViewById(C2262R.id.ap_edt_input);
        if (findViewById != null) {
            this.f7341c = (TextView) findViewById;
            this.f7341c.setFreezesText(true);
        }
        this.f7342d = (ImageView) findViewById(C2262R.id.ap_img_start);
        this.f7343e = (ImageView) findViewById(C2262R.id.ap_img_end);
        setLabel(str);
        if (findViewById != null) {
            setEndImage(i13);
            setStartImage(i11);
            setText(str4);
            setHint(str5);
            setInnerGravity(i3);
            if (i6 <= 1) {
                this.f7341c.setSingleLine(true);
            } else {
                this.f7341c.setSingleLine(false);
                this.f7341c.setLines(i6);
                this.f7341c.getLayoutParams().height = i6 * this.f7341c.getLayoutParams().height;
            }
            if (!this.f7339a) {
                setMaxLength(i2);
                setImeOptions(i5);
                setInputType(i4);
            }
            if (f2 > BitmapDescriptorFactory.HUE_RED) {
                this.f7341c.setTextSize(0, f2);
            }
            if (i8 > 0) {
                findViewById.setNextFocusUpId(i8);
            }
            if (i12 > 0) {
                findViewById.setNextFocusForwardId(i12);
            }
        }
    }

    /* renamed from: b */
    public void mo3287b() {
    }

    public ImageView getEndImageView() {
        return this.f7343e;
    }

    public int getImeOptions() {
        return this.f7341c.getImeOptions();
    }

    public int getInnerGravity() {
        return this.f7341c.getGravity();
    }

    public TextView getInnerInput() {
        return this.f7341c;
    }

    public int getInputType() {
        return this.f7341c.getInputType();
    }

    public CharSequence getLabel() {
        return this.f7340b.getText();
    }

    public ImageView getStartImageView() {
        return this.f7342d;
    }

    public CharSequence getText() {
        return this.f7341c.getText();
    }

    protected int getViewLayoutResourceId() {
        return C2262R.layout.asanpardakht_view_ap_label_edit_text;
    }

    public void setEnabled(boolean z) {
        super.setEnabled(z);
        if (this.f7341c != null) {
            this.f7341c.setEnabled(z);
        }
    }

    public void setEndImage(int i) {
        if (i > 0) {
            this.f7343e.setVisibility(0);
            this.f7343e.setImageResource(i);
            return;
        }
        this.f7343e.setVisibility(8);
    }

    public void setEndOnClickListener(OnClickListener onClickListener) {
        this.f7343e.setOnClickListener(onClickListener);
    }

    public void setHint(CharSequence charSequence) {
        if (charSequence != null) {
            this.f7341c.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{charSequence})));
            this.f7341c.setEllipsize(TruncateAt.END);
        }
    }

    public void setImeOptions(int i) {
        this.f7341c.setImeOptions(i);
    }

    public void setInnerGravity(int i) {
        this.f7341c.setGravity(i);
    }

    public void setInputType(int i) {
        this.f7341c.setInputType(i);
    }

    public void setLabel(CharSequence charSequence) {
        this.f7340b.setText(charSequence);
    }

    public void setMaxLength(int i) {
        this.f7341c.setFilters(new InputFilter[]{new LengthFilter(i)});
    }

    public void setStartImage(int i) {
        if (i > 0) {
            this.f7342d.setVisibility(0);
            this.f7342d.setImageResource(i);
            return;
        }
        this.f7342d.setVisibility(8);
    }

    public void setStartOnClickListener(OnClickListener onClickListener) {
        this.f7342d.setOnClickListener(onClickListener);
    }

    public void setText(CharSequence charSequence) {
        this.f7341c.setText(charSequence);
    }
}
