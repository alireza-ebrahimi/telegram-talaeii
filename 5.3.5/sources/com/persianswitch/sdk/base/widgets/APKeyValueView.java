package com.persianswitch.sdk.base.widgets;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;

public class APKeyValueView extends LinearLayout {
    private ImageView mImgSmallValueIcon;
    private TextView txtKey;
    private TextView txtValue;

    public APKeyValueView(Context context, CharSequence key, CharSequence value) {
        this(context, key, value, 0);
    }

    public APKeyValueView(Context context, CharSequence key, CharSequence value, int resId) {
        super(context);
        initView();
        setKey(key);
        setValue(value);
        setSmallIcon(resId);
    }

    public APKeyValueView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(C0770R.layout.asanpardakht_view_key_value, this, true);
        this.txtKey = (TextView) findViewById(C0770R.id.qa_view_key);
        this.txtValue = (TextView) findViewById(C0770R.id.qa_view_value);
        this.mImgSmallValueIcon = (ImageView) findViewById(C0770R.id.img_small_value_icon);
    }

    public void setKey(CharSequence key) {
        this.txtKey.setText(key);
    }

    public void setValue(CharSequence value) {
        this.txtValue.setText(value);
    }

    public void setSmallIcon(@DrawableRes int resId) {
        if (resId > 0) {
            this.mImgSmallValueIcon.setImageResource(resId);
            this.mImgSmallValueIcon.setVisibility(0);
            return;
        }
        this.mImgSmallValueIcon.setVisibility(8);
    }
}
