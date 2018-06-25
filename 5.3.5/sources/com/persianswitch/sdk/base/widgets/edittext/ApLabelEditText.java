package com.persianswitch.sdk.base.widgets.edittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.persianswitch.sdk.C0770R;
import com.persianswitch.sdk.base.widgets.APCustomView;

public class ApLabelEditText extends APCustomView {
    private static final String TAG = "ApLabelEditText";
    private TextView edtInput;
    private ImageView imgEnd;
    private ImageView imgStart;
    private boolean lockInput;
    private TextView txtLabel;

    public ApLabelEditText(Context context) {
        super(context);
    }

    public ApLabelEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ApLabelEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int getViewLayoutResourceId() {
        return C0770R.layout.asanpardakht_view_ap_label_edit_text;
    }

    protected void initialView(@Nullable AttributeSet attrs) {
        setAddStatesFromChildren(true);
        setOrientation(0);
        setGravity(17);
        int backgroundRes = C0770R.drawable.asanpardakht_rounded_white_box_bg;
        String text = "";
        String hint = "";
        String label = "";
        int rightImageResource = 0;
        int leftImageResource = 0;
        int maxLength = 20;
        int gravity = 17;
        int inputType = 1;
        int imeOption = 0;
        int lines = 1;
        float inputTextSize = -1.0f;
        int upInputId = 0;
        int nextInputId = -1;
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, C0770R.styleable.asanpardakht_AP);
            backgroundRes = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_android_background, C0770R.drawable.asanpardakht_rounded_white_box_bg);
            text = typedArray.getString(C0770R.styleable.asanpardakht_AP_android_text);
            hint = typedArray.getString(C0770R.styleable.asanpardakht_AP_android_hint);
            label = typedArray.getString(C0770R.styleable.asanpardakht_AP_asanpardakht_label);
            rightImageResource = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_asanpardakht_rightImage, 0);
            leftImageResource = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_asanpardakht_leftImage, 0);
            maxLength = typedArray.getInteger(C0770R.styleable.asanpardakht_AP_android_maxLength, 20);
            lines = typedArray.getInteger(C0770R.styleable.asanpardakht_AP_android_lines, 1);
            gravity = typedArray.getInt(C0770R.styleable.asanpardakht_AP_android_gravity, 17);
            inputType = typedArray.getInt(C0770R.styleable.asanpardakht_AP_android_inputType, 1);
            imeOption = typedArray.getInt(C0770R.styleable.asanpardakht_AP_android_imeOptions, 0);
            inputTextSize = typedArray.getDimension(C0770R.styleable.asanpardakht_AP_asanpardakht_inputTextSize, -1.0f);
            upInputId = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_asanpardakht_upInput, -1);
            nextInputId = typedArray.getResourceId(C0770R.styleable.asanpardakht_AP_asanpardakht_nextInput, -1);
            typedArray.recycle();
        }
        if (backgroundRes > 0) {
            setBackgroundResource(backgroundRes);
        }
        this.txtLabel = (TextView) findViewById(C0770R.id.ap_txt_label);
        this.txtLabel.setFreezesText(true);
        View input = findViewById(C0770R.id.ap_edt_input);
        if (input != null) {
            this.edtInput = (TextView) input;
            this.edtInput.setFreezesText(true);
        }
        this.imgStart = (ImageView) findViewById(C0770R.id.ap_img_start);
        this.imgEnd = (ImageView) findViewById(C0770R.id.ap_img_end);
        setLabel(label);
        if (input != null) {
            setEndImage(rightImageResource);
            setStartImage(leftImageResource);
            setText(text);
            setHint(hint);
            setInnerGravity(gravity);
            if (lines <= 1) {
                this.edtInput.setSingleLine(true);
            } else {
                this.edtInput.setSingleLine(false);
                this.edtInput.setLines(lines);
                this.edtInput.getLayoutParams().height *= lines;
            }
            if (!this.lockInput) {
                setMaxLength(maxLength);
                setImeOptions(imeOption);
                setInputType(inputType);
            }
            if (inputTextSize > 0.0f) {
                this.edtInput.setTextSize(0, inputTextSize);
            }
            if (upInputId > 0) {
                input.setNextFocusUpId(upInputId);
            }
            if (nextInputId > 0) {
                input.setNextFocusForwardId(nextInputId);
            }
        }
    }

    public void updateView() {
    }

    public void onViewFocused() {
        this.edtInput.requestFocus();
    }

    public CharSequence getText() {
        return this.edtInput.getText();
    }

    public void setText(CharSequence charSequence) {
        this.edtInput.setText(charSequence);
    }

    public void setHint(CharSequence charSequence) {
        if (charSequence != null) {
            this.edtInput.setHint(Html.fromHtml(String.format("<small>%s</small>", new Object[]{charSequence})));
            this.edtInput.setEllipsize(TruncateAt.END);
        }
    }

    public CharSequence getLabel() {
        return this.txtLabel.getText();
    }

    public void setLabel(CharSequence charSequence) {
        this.txtLabel.setText(charSequence);
    }

    public void setEndImage(int resourceId) {
        if (resourceId > 0) {
            this.imgEnd.setVisibility(0);
            this.imgEnd.setImageResource(resourceId);
            return;
        }
        this.imgEnd.setVisibility(8);
    }

    public void setEndOnClickListener(OnClickListener onClickListener) {
        this.imgEnd.setOnClickListener(onClickListener);
    }

    public void setStartImage(int resourceId) {
        if (resourceId > 0) {
            this.imgStart.setVisibility(0);
            this.imgStart.setImageResource(resourceId);
            return;
        }
        this.imgStart.setVisibility(8);
    }

    public ImageView getStartImageView() {
        return this.imgStart;
    }

    public ImageView getEndImageView() {
        return this.imgEnd;
    }

    public void setStartOnClickListener(OnClickListener onClickListener) {
        this.imgStart.setOnClickListener(onClickListener);
    }

    public TextView getInnerInput() {
        return this.edtInput;
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.edtInput != null) {
            this.edtInput.setEnabled(enabled);
        }
    }

    public int getImeOptions() {
        return this.edtInput.getImeOptions();
    }

    public void setImeOptions(int imeOptions) {
        this.edtInput.setImeOptions(imeOptions);
    }

    public int getInnerGravity() {
        return this.edtInput.getGravity();
    }

    public void setInnerGravity(int gravity) {
        this.edtInput.setGravity(gravity);
    }

    public int getInputType() {
        return this.edtInput.getInputType();
    }

    public void setInputType(int type) {
        this.edtInput.setInputType(type);
    }

    public void setMaxLength(int maxLength) {
        this.edtInput.setFilters(new InputFilter[]{new LengthFilter(maxLength)});
    }
}
