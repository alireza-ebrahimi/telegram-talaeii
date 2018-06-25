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
import org.ir.talaeii.R;
import org.telegram.messenger.C0906R;

public class FarsiCheckBox extends RelativeLayout implements OnClickListener {
    CheckBox checkBox;
    OnClickListener listener;
    TextView title;

    public FarsiCheckBox(Context context) {
        super(context);
        init(context);
    }

    public FarsiCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        handleAttrs(context, attrs);
    }

    public FarsiCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        handleAttrs(context, attrs);
    }

    @TargetApi(21)
    public FarsiCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
        handleAttrs(context, attrs);
    }

    private void init(Context context) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.rtl_checkbox, this, true);
        this.title = (TextView) findViewById(R.id.title);
        this.checkBox = (CheckBox) findViewById(R.id.checkbox);
        super.setOnClickListener(this);
    }

    private void handleAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, C0906R.styleable.SettingsItemView, 0, 0);
        setChecked(a.getBoolean(1, false));
        setCheckBoxEnable(a.getBoolean(0, true));
        try {
            setTitle(a.getString(2));
        } catch (Exception e) {
        }
        setTitleTextSize(a.getDimension(3, 0.0f));
        a.recycle();
    }

    private void setTitleTextSize(float textSize) {
        try {
            this.title.setTextSize(0, textSize);
        } catch (Exception e) {
        }
    }

    public void setTitle(String t) {
        try {
            this.title.setText(t);
        } catch (Exception e) {
        }
    }

    public void setChecked(boolean b) {
        try {
            this.checkBox.setChecked(b);
        } catch (Exception e) {
        }
    }

    public boolean isChecked() {
        try {
            return this.checkBox.isChecked();
        } catch (Exception e) {
            return false;
        }
    }

    public void setCheckBoxEnable(boolean enable) {
        if (enable) {
            this.checkBox.setVisibility(0);
        } else {
            this.checkBox.setVisibility(8);
        }
    }

    public void setCheckListener(OnCheckedChangeListener c) {
        try {
            this.checkBox.setOnCheckedChangeListener(c);
        } catch (Exception e) {
        }
    }

    public void setOnClickListener(OnClickListener l) {
        this.listener = l;
    }

    public void onClick(View view) {
        if (this.checkBox.getVisibility() == 0) {
            this.checkBox.toggle();
        }
        if (this.listener != null) {
            this.listener.onClick(view);
        }
    }
}
