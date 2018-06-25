package org.telegram.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;

public class ChannelIntroActivity extends BaseFragment {
    private TextView createChannelText;
    private TextView descriptionText;
    private ImageView imageView;
    private TextView whatIsChannelText;

    /* renamed from: org.telegram.ui.ChannelIntroActivity$1 */
    class C41761 extends ActionBarMenuOnItemClick {
        C41761() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChannelIntroActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelIntroActivity$3 */
    class C41783 implements OnTouchListener {
        C41783() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChannelIntroActivity$4 */
    class C41794 implements OnClickListener {
        C41794() {
        }

        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putInt("step", 0);
            ChannelIntroActivity.this.presentFragment(new ChannelCreateActivity(bundle), true);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setItemsColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2), false);
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarWhiteSelector), false);
        this.actionBar.setCastShadows(false);
        if (!AndroidUtilities.isTablet()) {
            this.actionBar.showActionModeTop();
        }
        this.actionBar.setActionBarMenuOnItemClick(new C41761());
        this.fragmentView = new ViewGroup(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int i5 = i3 - i;
                int i6 = i4 - i2;
                if (i3 > i4) {
                    int i7 = (int) (((float) i6) * 0.05f);
                    ChannelIntroActivity.this.imageView.layout(0, i7, ChannelIntroActivity.this.imageView.getMeasuredWidth(), ChannelIntroActivity.this.imageView.getMeasuredHeight() + i7);
                    i7 = (int) (((float) i5) * 0.4f);
                    int i8 = (int) (((float) i6) * 0.14f);
                    ChannelIntroActivity.this.whatIsChannelText.layout(i7, i8, ChannelIntroActivity.this.whatIsChannelText.getMeasuredWidth() + i7, ChannelIntroActivity.this.whatIsChannelText.getMeasuredHeight() + i8);
                    i8 = (int) (((float) i6) * 0.61f);
                    ChannelIntroActivity.this.createChannelText.layout(i7, i8, ChannelIntroActivity.this.createChannelText.getMeasuredWidth() + i7, ChannelIntroActivity.this.createChannelText.getMeasuredHeight() + i8);
                    i5 = (int) (((float) i5) * 0.45f);
                    i6 = (int) (((float) i6) * 0.31f);
                    ChannelIntroActivity.this.descriptionText.layout(i5, i6, ChannelIntroActivity.this.descriptionText.getMeasuredWidth() + i5, ChannelIntroActivity.this.descriptionText.getMeasuredHeight() + i6);
                    return;
                }
                i7 = (int) (((float) i6) * 0.05f);
                ChannelIntroActivity.this.imageView.layout(0, i7, ChannelIntroActivity.this.imageView.getMeasuredWidth(), ChannelIntroActivity.this.imageView.getMeasuredHeight() + i7);
                i7 = (int) (((float) i6) * 0.59f);
                ChannelIntroActivity.this.whatIsChannelText.layout(0, i7, ChannelIntroActivity.this.whatIsChannelText.getMeasuredWidth(), ChannelIntroActivity.this.whatIsChannelText.getMeasuredHeight() + i7);
                i7 = (int) (((float) i6) * 0.68f);
                i5 = (int) (((float) i5) * 0.05f);
                ChannelIntroActivity.this.descriptionText.layout(i5, i7, ChannelIntroActivity.this.descriptionText.getMeasuredWidth() + i5, ChannelIntroActivity.this.descriptionText.getMeasuredHeight() + i7);
                i5 = (int) (((float) i6) * 0.86f);
                ChannelIntroActivity.this.createChannelText.layout(0, i5, ChannelIntroActivity.this.createChannelText.getMeasuredWidth(), ChannelIntroActivity.this.createChannelText.getMeasuredHeight() + i5);
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                if (size > size2) {
                    ChannelIntroActivity.this.imageView.measure(MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.45f), 1073741824), MeasureSpec.makeMeasureSpec((int) (((float) size2) * 0.78f), 1073741824));
                    ChannelIntroActivity.this.whatIsChannelText.measure(MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.6f), 1073741824), MeasureSpec.makeMeasureSpec(size2, 0));
                    ChannelIntroActivity.this.descriptionText.measure(MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.5f), 1073741824), MeasureSpec.makeMeasureSpec(size2, 0));
                    ChannelIntroActivity.this.createChannelText.measure(MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.6f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824));
                } else {
                    ChannelIntroActivity.this.imageView.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec((int) (((float) size2) * 0.44f), 1073741824));
                    ChannelIntroActivity.this.whatIsChannelText.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(size2, 0));
                    ChannelIntroActivity.this.descriptionText.measure(MeasureSpec.makeMeasureSpec((int) (((float) size) * 0.9f), 1073741824), MeasureSpec.makeMeasureSpec(size2, 0));
                    ChannelIntroActivity.this.createChannelText.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824));
                }
                setMeasuredDimension(size, size2);
            }
        };
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        ViewGroup viewGroup = (ViewGroup) this.fragmentView;
        viewGroup.setOnTouchListener(new C41783());
        this.imageView = new ImageView(context);
        this.imageView.setImageResource(R.drawable.channelintro);
        this.imageView.setScaleType(ScaleType.FIT_CENTER);
        viewGroup.addView(this.imageView);
        this.whatIsChannelText = new TextView(context);
        this.whatIsChannelText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.whatIsChannelText.setGravity(1);
        this.whatIsChannelText.setTextSize(1, 24.0f);
        this.whatIsChannelText.setText(LocaleController.getString("ChannelAlertTitle", R.string.ChannelAlertTitle));
        viewGroup.addView(this.whatIsChannelText);
        this.descriptionText = new TextView(context);
        this.descriptionText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
        this.descriptionText.setGravity(1);
        this.descriptionText.setTextSize(1, 16.0f);
        this.descriptionText.setText(LocaleController.getString("ChannelAlertText", R.string.ChannelAlertText));
        viewGroup.addView(this.descriptionText);
        this.createChannelText = new TextView(context);
        this.createChannelText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText5));
        this.createChannelText.setGravity(17);
        this.createChannelText.setTextSize(1, 16.0f);
        this.createChannelText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.createChannelText.setText(LocaleController.getString("ChannelAlertCreate", R.string.ChannelAlertCreate));
        viewGroup.addView(this.createChannelText);
        this.createChannelText.setOnClickListener(new C41794());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[7];
        themeDescriptionArr[0] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarWhiteSelector);
        themeDescriptionArr[4] = new ThemeDescription(this.whatIsChannelText, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[5] = new ThemeDescription(this.descriptionText, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        themeDescriptionArr[6] = new ThemeDescription(this.createChannelText, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText5);
        return themeDescriptionArr;
    }
}
