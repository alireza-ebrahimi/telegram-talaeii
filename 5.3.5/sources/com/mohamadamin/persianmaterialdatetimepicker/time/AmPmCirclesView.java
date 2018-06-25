package com.mohamadamin.persianmaterialdatetimepicker.time;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import com.mohamadamin.persianmaterialdatetimepicker.C0610R;

public class AmPmCirclesView extends View {
    private static final int AM = 0;
    private static final int PM = 1;
    private static final int SELECTED_ALPHA = 255;
    private static final int SELECTED_ALPHA_THEME_DARK = 255;
    private static final String TAG = "AmPmCirclesView";
    private int mAmOrPm;
    private int mAmOrPmPressed;
    private int mAmPmCircleRadius;
    private float mAmPmCircleRadiusMultiplier;
    private int mAmPmSelectedTextColor;
    private int mAmPmTextColor;
    private int mAmPmYCenter;
    private String mAmText;
    private int mAmXCenter;
    private float mCircleRadiusMultiplier;
    private boolean mDrawValuesReady;
    private boolean mIsInitialized = false;
    private final Paint mPaint = new Paint();
    private String mPmText;
    private int mPmXCenter;
    private int mSelectedAlpha;
    private int mSelectedColor;
    private int mTouchedColor;
    private int mUnselectedColor;

    public AmPmCirclesView(Context context) {
        super(context);
    }

    public void initialize(Context context, int amOrPm) {
        if (this.mIsInitialized) {
            Log.e(TAG, "AmPmCirclesView may only be initialized once.");
            return;
        }
        Resources res = context.getResources();
        this.mUnselectedColor = res.getColor(C0610R.color.mdtp_white);
        this.mSelectedColor = res.getColor(C0610R.color.mdtp_accent_color);
        this.mTouchedColor = res.getColor(C0610R.color.mdtp_accent_color_dark);
        this.mAmPmTextColor = res.getColor(C0610R.color.mdtp_ampm_text_color);
        this.mAmPmSelectedTextColor = res.getColor(C0610R.color.mdtp_white);
        this.mSelectedAlpha = 255;
        this.mPaint.setTypeface(Typeface.create(res.getString(C0610R.string.mdtp_sans_serif), 0));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextAlign(Align.CENTER);
        this.mCircleRadiusMultiplier = Float.parseFloat(res.getString(C0610R.string.mdtp_circle_radius_multiplier));
        this.mAmPmCircleRadiusMultiplier = Float.parseFloat(res.getString(C0610R.string.mdtp_ampm_circle_radius_multiplier));
        this.mAmText = "قبل‌ازظهر";
        this.mPmText = "بعدازظهر";
        setAmOrPm(amOrPm);
        this.mAmOrPmPressed = -1;
        this.mIsInitialized = true;
    }

    void setTheme(Context context, boolean themeDark) {
        Resources res = context.getResources();
        if (themeDark) {
            this.mUnselectedColor = res.getColor(C0610R.color.mdtp_circle_background_dark_theme);
            this.mSelectedColor = res.getColor(C0610R.color.mdtp_red);
            this.mAmPmTextColor = res.getColor(C0610R.color.mdtp_white);
            this.mSelectedAlpha = 255;
            return;
        }
        this.mUnselectedColor = res.getColor(C0610R.color.mdtp_white);
        this.mSelectedColor = res.getColor(C0610R.color.mdtp_accent_color);
        this.mAmPmTextColor = res.getColor(C0610R.color.mdtp_ampm_text_color);
        this.mSelectedAlpha = 255;
    }

    public void setAmOrPm(int amOrPm) {
        this.mAmOrPm = amOrPm;
    }

    public void setAmOrPmPressed(int amOrPmPressed) {
        this.mAmOrPmPressed = amOrPmPressed;
    }

    public int getIsTouchingAmOrPm(float xCoord, float yCoord) {
        if (!this.mDrawValuesReady) {
            return -1;
        }
        int squaredYDistance = (int) ((yCoord - ((float) this.mAmPmYCenter)) * (yCoord - ((float) this.mAmPmYCenter)));
        if (((int) Math.sqrt((double) (((xCoord - ((float) this.mAmXCenter)) * (xCoord - ((float) this.mAmXCenter))) + ((float) squaredYDistance)))) <= this.mAmPmCircleRadius) {
            return 0;
        }
        if (((int) Math.sqrt((double) (((xCoord - ((float) this.mPmXCenter)) * (xCoord - ((float) this.mPmXCenter))) + ((float) squaredYDistance)))) <= this.mAmPmCircleRadius) {
            return 1;
        }
        return -1;
    }

    public void onDraw(Canvas canvas) {
        if (getWidth() != 0 && this.mIsInitialized) {
            if (!this.mDrawValuesReady) {
                int layoutXCenter = getWidth() / 2;
                int layoutYCenter = getHeight() / 2;
                int circleRadius = (int) (((float) Math.min(layoutXCenter, layoutYCenter)) * this.mCircleRadiusMultiplier);
                this.mAmPmCircleRadius = (int) (((float) circleRadius) * this.mAmPmCircleRadiusMultiplier);
                layoutYCenter = (int) (((double) layoutYCenter) + (((double) this.mAmPmCircleRadius) * 0.75d));
                this.mPaint.setTextSize((float) ((this.mAmPmCircleRadius * 3) / 4));
                this.mAmPmYCenter = (layoutYCenter - (this.mAmPmCircleRadius / 2)) + circleRadius;
                this.mAmXCenter = (layoutXCenter - circleRadius) + this.mAmPmCircleRadius;
                this.mPmXCenter = (layoutXCenter + circleRadius) - this.mAmPmCircleRadius;
                this.mDrawValuesReady = true;
            }
            int amColor = this.mUnselectedColor;
            int amAlpha = 255;
            int amTextColor = this.mAmPmTextColor;
            int pmColor = this.mUnselectedColor;
            int pmAlpha = 255;
            int pmTextColor = this.mAmPmTextColor;
            if (this.mAmOrPm == 0) {
                amColor = this.mSelectedColor;
                amAlpha = this.mSelectedAlpha;
                amTextColor = this.mAmPmSelectedTextColor;
            } else if (this.mAmOrPm == 1) {
                pmColor = this.mSelectedColor;
                pmAlpha = this.mSelectedAlpha;
                pmTextColor = this.mAmPmSelectedTextColor;
            }
            if (this.mAmOrPmPressed == 0) {
                amColor = this.mTouchedColor;
                amAlpha = this.mSelectedAlpha;
            } else if (this.mAmOrPmPressed == 1) {
                pmColor = this.mTouchedColor;
                pmAlpha = this.mSelectedAlpha;
            }
            this.mPaint.setColor(amColor);
            this.mPaint.setAlpha(amAlpha);
            canvas.drawCircle((float) this.mAmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(pmColor);
            this.mPaint.setAlpha(pmAlpha);
            canvas.drawCircle((float) this.mPmXCenter, (float) this.mAmPmYCenter, (float) this.mAmPmCircleRadius, this.mPaint);
            this.mPaint.setColor(amTextColor);
            int textYCenter = this.mAmPmYCenter - (((int) (this.mPaint.descent() + this.mPaint.ascent())) / 2);
            canvas.drawText(this.mAmText, (float) this.mAmXCenter, (float) textYCenter, this.mPaint);
            this.mPaint.setColor(pmTextColor);
            canvas.drawText(this.mPmText, (float) this.mPmXCenter, (float) textYCenter, this.mPaint);
        }
    }
}
