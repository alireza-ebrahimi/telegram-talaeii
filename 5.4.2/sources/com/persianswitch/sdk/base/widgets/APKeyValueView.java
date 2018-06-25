package com.persianswitch.sdk.base.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.persianswitch.sdk.C2262R;

public class APKeyValueView extends LinearLayout {
    /* renamed from: a */
    private TextView f7305a;
    /* renamed from: b */
    private TextView f7306b;
    /* renamed from: c */
    private ImageView f7307c;

    public APKeyValueView(Context context, CharSequence charSequence, CharSequence charSequence2) {
        this(context, charSequence, charSequence2, 0);
    }

    public APKeyValueView(Context context, CharSequence charSequence, CharSequence charSequence2, int i) {
        super(context);
        m10988a();
        setKey(charSequence);
        setValue(charSequence2);
        setSmallIcon(i);
    }

    /* renamed from: a */
    private void m10988a() {
        LayoutInflater.from(getContext()).inflate(C2262R.layout.asanpardakht_view_key_value, this, true);
        this.f7305a = (TextView) findViewById(C2262R.id.qa_view_key);
        this.f7306b = (TextView) findViewById(C2262R.id.qa_view_value);
        this.f7307c = (ImageView) findViewById(C2262R.id.img_small_value_icon);
    }

    public void setKey(CharSequence charSequence) {
        this.f7305a.setText(charSequence);
    }

    public void setSmallIcon(int i) {
        if (i > 0) {
            this.f7307c.setImageResource(i);
            this.f7307c.setVisibility(0);
            return;
        }
        this.f7307c.setVisibility(8);
    }

    public void setValue(CharSequence charSequence) {
        this.f7306b.setText(charSequence);
    }
}
