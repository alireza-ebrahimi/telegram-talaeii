package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LayoutHelper;

public class ChatUnreadCell extends FrameLayout {
    private FrameLayout backgroundLayout;
    private ImageView imageView;
    private TextView textView;

    public ChatUnreadCell(Context context) {
        super(context);
        this.backgroundLayout = new FrameLayout(context);
        this.backgroundLayout.setBackgroundResource(R.drawable.newmsg_divider);
        this.backgroundLayout.getBackground().setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_unreadMessagesStartBackground), Mode.MULTIPLY));
        addView(this.backgroundLayout, LayoutHelper.createFrame(-1, 27.0f, 51, BitmapDescriptorFactory.HUE_RED, 7.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.imageView = new ImageView(context);
        this.imageView.setImageResource(R.drawable.ic_ab_new);
        this.imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_unreadMessagesStartArrowIcon), Mode.MULTIPLY));
        this.imageView.setPadding(0, AndroidUtilities.dp(2.0f), 0, 0);
        this.backgroundLayout.addView(this.imageView, LayoutHelper.createFrame(-2, -2.0f, 21, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 10.0f, BitmapDescriptorFactory.HUE_RED));
        this.textView = new TextView(context);
        this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(1.0f));
        this.textView.setTextSize(1, 14.0f);
        this.textView.setTextColor(Theme.getColor(Theme.key_chat_unreadMessagesStartText));
        this.textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        addView(this.textView, LayoutHelper.createFrame(-2, -2, 17));
    }

    public FrameLayout getBackgroundLayout() {
        return this.backgroundLayout;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public TextView getTextView() {
        return this.textView;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(40.0f), 1073741824));
    }

    public void setText(String str) {
        this.textView.setText(str);
    }
}
