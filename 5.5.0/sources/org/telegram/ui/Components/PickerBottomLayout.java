package org.telegram.ui.Components;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;

public class PickerBottomLayout extends FrameLayout {
    public TextView cancelButton;
    public LinearLayout doneButton;
    public TextView doneButtonBadgeTextView;
    public TextView doneButtonTextView;
    private boolean isDarkTheme;

    public PickerBottomLayout(Context context) {
        this(context, true);
    }

    public PickerBottomLayout(Context context, boolean z) {
        int i = -1;
        super(context);
        this.isDarkTheme = z;
        setBackgroundColor(this.isDarkTheme ? -15066598 : Theme.getColor(Theme.key_windowBackgroundWhite));
        this.cancelButton = new TextView(context);
        this.cancelButton.setTextSize(1, 14.0f);
        this.cancelButton.setTextColor(this.isDarkTheme ? -1 : Theme.getColor(Theme.key_picker_enabledButton));
        this.cancelButton.setGravity(17);
        this.cancelButton.setBackgroundDrawable(Theme.createSelectorDrawable(this.isDarkTheme ? Theme.ACTION_BAR_PICKER_SELECTOR_COLOR : Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
        this.cancelButton.setPadding(AndroidUtilities.dp(29.0f), 0, AndroidUtilities.dp(29.0f), 0);
        this.cancelButton.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        this.cancelButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(this.cancelButton, LayoutHelper.createFrame(-2, -1, 51));
        this.doneButton = new LinearLayout(context);
        this.doneButton.setOrientation(0);
        this.doneButton.setBackgroundDrawable(Theme.createSelectorDrawable(this.isDarkTheme ? Theme.ACTION_BAR_PICKER_SELECTOR_COLOR : Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
        this.doneButton.setPadding(AndroidUtilities.dp(29.0f), 0, AndroidUtilities.dp(29.0f), 0);
        addView(this.doneButton, LayoutHelper.createFrame(-2, -1, 53));
        this.doneButtonBadgeTextView = new TextView(context);
        this.doneButtonBadgeTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButtonBadgeTextView.setTextSize(1, 13.0f);
        this.doneButtonBadgeTextView.setTextColor(this.isDarkTheme ? -1 : Theme.getColor(Theme.key_picker_badgeText));
        this.doneButtonBadgeTextView.setGravity(17);
        this.doneButtonBadgeTextView.setBackgroundDrawable(this.isDarkTheme ? Theme.createRoundRectDrawable(AndroidUtilities.dp(11.0f), -10043398) : Theme.createRoundRectDrawable(AndroidUtilities.dp(11.0f), Theme.getColor(Theme.key_picker_badge)));
        this.doneButtonBadgeTextView.setMinWidth(AndroidUtilities.dp(23.0f));
        this.doneButtonBadgeTextView.setPadding(AndroidUtilities.dp(8.0f), 0, AndroidUtilities.dp(8.0f), AndroidUtilities.dp(1.0f));
        this.doneButton.addView(this.doneButtonBadgeTextView, LayoutHelper.createLinear(-2, 23, 16, 0, 0, 10, 0));
        this.doneButtonTextView = new TextView(context);
        this.doneButtonTextView.setTextSize(1, 14.0f);
        TextView textView = this.doneButtonTextView;
        if (!this.isDarkTheme) {
            i = Theme.getColor(Theme.key_picker_enabledButton);
        }
        textView.setTextColor(i);
        this.doneButtonTextView.setGravity(17);
        this.doneButtonTextView.setCompoundDrawablePadding(AndroidUtilities.dp(8.0f));
        this.doneButtonTextView.setText(LocaleController.getString("Send", R.string.Send).toUpperCase());
        this.doneButtonTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.doneButton.addView(this.doneButtonTextView, LayoutHelper.createLinear(-2, -2, 16));
    }

    public void updateSelectedCount(int i, boolean z) {
        int i2 = -1;
        Object obj = null;
        if (i == 0) {
            this.doneButtonBadgeTextView.setVisibility(8);
            if (z) {
                TextView textView = this.doneButtonTextView;
                if (!this.isDarkTheme) {
                    obj = Theme.key_picker_disabledButton;
                }
                textView.setTag(obj);
                this.doneButtonTextView.setTextColor(this.isDarkTheme ? -6710887 : Theme.getColor(Theme.key_picker_disabledButton));
                this.doneButton.setEnabled(false);
                return;
            }
            TextView textView2 = this.doneButtonTextView;
            if (!this.isDarkTheme) {
                obj = Theme.key_picker_enabledButton;
            }
            textView2.setTag(obj);
            this.doneButtonTextView.setTextColor(this.isDarkTheme ? -1 : Theme.getColor(Theme.key_picker_enabledButton));
            return;
        }
        this.doneButtonBadgeTextView.setVisibility(0);
        this.doneButtonBadgeTextView.setText(String.format("%d", new Object[]{Integer.valueOf(i)}));
        textView2 = this.doneButtonTextView;
        if (!this.isDarkTheme) {
            obj = Theme.key_picker_enabledButton;
        }
        textView2.setTag(obj);
        TextView textView3 = this.doneButtonTextView;
        if (!this.isDarkTheme) {
            i2 = Theme.getColor(Theme.key_picker_enabledButton);
        }
        textView3.setTextColor(i2);
        if (z) {
            this.doneButton.setEnabled(true);
        }
    }
}
