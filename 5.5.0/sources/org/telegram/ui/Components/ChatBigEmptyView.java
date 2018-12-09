package org.telegram.ui.Components;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;

public class ChatBigEmptyView extends LinearLayout {
    private ArrayList<ImageView> imageViews = new ArrayList();
    private TextView secretViewStatusTextView;
    private ArrayList<TextView> textViews = new ArrayList();

    public ChatBigEmptyView(Context context, boolean z) {
        View imageView;
        super(context);
        setBackgroundResource(R.drawable.system);
        getBackground().setColorFilter(Theme.colorFilter);
        setPadding(AndroidUtilities.dp(16.0f), AndroidUtilities.dp(12.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(12.0f));
        setOrientation(1);
        if (z) {
            this.secretViewStatusTextView = new TextView(context);
            this.secretViewStatusTextView.setTextSize(1, 15.0f);
            this.secretViewStatusTextView.setTextColor(Theme.getColor(Theme.key_chat_serviceText));
            this.secretViewStatusTextView.setGravity(1);
            this.secretViewStatusTextView.setMaxWidth(AndroidUtilities.dp(210.0f));
            this.textViews.add(this.secretViewStatusTextView);
            addView(this.secretViewStatusTextView, LayoutHelper.createLinear(-2, -2, 49));
        } else {
            imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.cloud_big);
            addView(imageView, LayoutHelper.createLinear(-2, -2, 49, 0, 2, 0, 0));
        }
        imageView = new TextView(context);
        if (z) {
            imageView.setText(LocaleController.getString("EncryptedDescriptionTitle", R.string.EncryptedDescriptionTitle));
            imageView.setTextSize(1, 15.0f);
        } else {
            imageView.setText(LocaleController.getString("ChatYourSelfTitle", R.string.ChatYourSelfTitle));
            imageView.setTextSize(1, 16.0f);
            imageView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            imageView.setGravity(1);
        }
        imageView.setTextColor(Theme.getColor(Theme.key_chat_serviceText));
        this.textViews.add(imageView);
        imageView.setMaxWidth(AndroidUtilities.dp(260.0f));
        int i = z ? LocaleController.isRTL ? 5 : 3 : 1;
        addView(imageView, LayoutHelper.createLinear(-2, -2, i | 48, 0, 8, 0, z ? 0 : 8));
        for (int i2 = 0; i2 < 4; i2++) {
            View linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            addView(linearLayout, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 0, 8, 0, 0));
            View imageView2 = new ImageView(context);
            imageView2.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_serviceText), Mode.MULTIPLY));
            imageView2.setImageResource(z ? R.drawable.ic_lock_white : R.drawable.list_circle);
            this.imageViews.add(imageView2);
            View textView = new TextView(context);
            textView.setTextSize(1, 15.0f);
            textView.setTextColor(Theme.getColor(Theme.key_chat_serviceText));
            this.textViews.add(textView);
            textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            textView.setMaxWidth(AndroidUtilities.dp(260.0f));
            switch (i2) {
                case 0:
                    if (!z) {
                        textView.setText(LocaleController.getString("ChatYourSelfDescription1", R.string.ChatYourSelfDescription1));
                        break;
                    } else {
                        textView.setText(LocaleController.getString("EncryptedDescription1", R.string.EncryptedDescription1));
                        break;
                    }
                case 1:
                    if (!z) {
                        textView.setText(LocaleController.getString("ChatYourSelfDescription2", R.string.ChatYourSelfDescription2));
                        break;
                    } else {
                        textView.setText(LocaleController.getString("EncryptedDescription2", R.string.EncryptedDescription2));
                        break;
                    }
                case 2:
                    if (!z) {
                        textView.setText(LocaleController.getString("ChatYourSelfDescription3", R.string.ChatYourSelfDescription3));
                        break;
                    } else {
                        textView.setText(LocaleController.getString("EncryptedDescription3", R.string.EncryptedDescription3));
                        break;
                    }
                case 3:
                    if (!z) {
                        textView.setText(LocaleController.getString("ChatYourSelfDescription4", R.string.ChatYourSelfDescription4));
                        break;
                    } else {
                        textView.setText(LocaleController.getString("EncryptedDescription4", R.string.EncryptedDescription4));
                        break;
                    }
            }
            if (LocaleController.isRTL) {
                linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2));
                if (z) {
                    linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, 8.0f, 3.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                } else {
                    linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, 8.0f, 7.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                }
            } else {
                if (z) {
                    linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, BitmapDescriptorFactory.HUE_RED, 4.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
                } else {
                    linearLayout.addView(imageView2, LayoutHelper.createLinear(-2, -2, BitmapDescriptorFactory.HUE_RED, 8.0f, 8.0f, BitmapDescriptorFactory.HUE_RED));
                }
                linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2));
            }
        }
    }

    public void setSecretText(String str) {
        this.secretViewStatusTextView.setText(str);
    }

    public void setTextColor(int i) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.textViews.size(); i3++) {
            ((TextView) this.textViews.get(i3)).setTextColor(i);
        }
        while (i2 < this.imageViews.size()) {
            ((ImageView) this.imageViews.get(i2)).setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_serviceText), Mode.MULTIPLY));
            i2++;
        }
    }
}
