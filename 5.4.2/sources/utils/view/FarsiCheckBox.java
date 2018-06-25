package utils.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.C3336R;

public class FarsiCheckBox extends RelativeLayout implements OnClickListener {
    /* renamed from: a */
    TextView f10270a;
    /* renamed from: b */
    CheckBox f10271b;
    /* renamed from: c */
    OnClickListener f10272c;

    public FarsiCheckBox(Context context) {
        super(context);
        m14163a(context);
    }

    public FarsiCheckBox(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m14163a(context);
        m14164a(context, attributeSet);
    }

    public FarsiCheckBox(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m14163a(context);
        m14164a(context, attributeSet);
    }

    @TargetApi(21)
    public FarsiCheckBox(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        m14163a(context);
        m14164a(context, attributeSet);
    }

    /* renamed from: a */
    private void m14163a(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.rtl_checkbox, this, true);
        this.f10270a = (TextView) findViewById(R.id.title);
        this.f10271b = (CheckBox) findViewById(R.id.checkbox);
        super.setOnClickListener(this);
    }

    /* renamed from: a */
    private void m14164a(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C3336R.styleable.SettingsItemView, 0, 0);
        setChecked(obtainStyledAttributes.getBoolean(1, false));
        setCheckBoxEnable(obtainStyledAttributes.getBoolean(0, true));
        try {
            setTitle(obtainStyledAttributes.getString(2));
        } catch (Exception e) {
        }
        setTitleTextSize(obtainStyledAttributes.getDimension(3, BitmapDescriptorFactory.HUE_RED));
        obtainStyledAttributes.recycle();
    }

    private void setTitleTextSize(float f) {
        try {
            this.f10270a.setTextSize(0, f);
        } catch (Exception e) {
        }
    }

    /* renamed from: a */
    public boolean m14165a() {
        try {
            return this.f10271b.isChecked();
        } catch (Exception e) {
            return false;
        }
    }

    public void onClick(View view) {
        if (this.f10271b.getVisibility() == 0) {
            this.f10271b.toggle();
        }
        if (this.f10272c != null) {
            this.f10272c.onClick(view);
        }
    }

    public void setCheckBoxEnable(boolean z) {
        if (z) {
            this.f10271b.setVisibility(0);
        } else {
            this.f10271b.setVisibility(8);
        }
    }

    public void setCheckListener(OnCheckedChangeListener onCheckedChangeListener) {
        try {
            this.f10271b.setOnCheckedChangeListener(onCheckedChangeListener);
        } catch (Exception e) {
        }
    }

    public void setChecked(boolean z) {
        try {
            this.f10271b.setChecked(z);
        } catch (Exception e) {
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.f10272c = onClickListener;
    }

    public void setTitle(String str) {
        try {
            this.f10270a.setText(str);
        } catch (Exception e) {
        }
    }
}
