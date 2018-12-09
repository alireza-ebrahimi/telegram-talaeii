package org.telegram.ui.Components;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRow;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardMarkup;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.ui.ActionBar.Theme;

public class BotKeyboardView extends LinearLayout {
    private TLRPC$TL_replyKeyboardMarkup botButtons;
    private int buttonHeight;
    private ArrayList<TextView> buttonViews = new ArrayList();
    private LinearLayout container;
    private BotKeyboardViewDelegate delegate;
    private boolean isFullSize;
    private int panelHeight;
    private ScrollView scrollView;

    /* renamed from: org.telegram.ui.Components.BotKeyboardView$1 */
    class C43391 implements OnClickListener {
        C43391() {
        }

        public void onClick(View view) {
            BotKeyboardView.this.delegate.didPressedButton((KeyboardButton) view.getTag());
        }
    }

    public interface BotKeyboardViewDelegate {
        void didPressedButton(KeyboardButton keyboardButton);
    }

    public BotKeyboardView(Context context) {
        super(context);
        setOrientation(1);
        this.scrollView = new ScrollView(context);
        addView(this.scrollView);
        this.container = new LinearLayout(context);
        this.container.setOrientation(1);
        this.scrollView.addView(this.container);
        AndroidUtilities.setScrollViewEdgeEffectColor(this.scrollView, Theme.getColor(Theme.key_chat_emojiPanelBackground));
        setBackgroundColor(Theme.getColor(Theme.key_chat_emojiPanelBackground));
    }

    public int getKeyboardHeight() {
        return this.isFullSize ? this.panelHeight : ((this.botButtons.rows.size() * AndroidUtilities.dp((float) this.buttonHeight)) + AndroidUtilities.dp(30.0f)) + ((this.botButtons.rows.size() - 1) * AndroidUtilities.dp(10.0f));
    }

    public void invalidateViews() {
        for (int i = 0; i < this.buttonViews.size(); i++) {
            ((TextView) this.buttonViews.get(i)).invalidate();
        }
    }

    public boolean isFullSize() {
        return this.isFullSize;
    }

    public void setButtons(TLRPC$TL_replyKeyboardMarkup tLRPC$TL_replyKeyboardMarkup) {
        this.botButtons = tLRPC$TL_replyKeyboardMarkup;
        this.container.removeAllViews();
        this.buttonViews.clear();
        this.scrollView.scrollTo(0, 0);
        if (tLRPC$TL_replyKeyboardMarkup != null && this.botButtons.rows.size() != 0) {
            this.isFullSize = !tLRPC$TL_replyKeyboardMarkup.resize;
            this.buttonHeight = !this.isFullSize ? 42 : (int) Math.max(42.0f, ((float) (((this.panelHeight - AndroidUtilities.dp(30.0f)) - ((this.botButtons.rows.size() - 1) * AndroidUtilities.dp(10.0f))) / this.botButtons.rows.size())) / AndroidUtilities.density);
            int i = 0;
            while (i < tLRPC$TL_replyKeyboardMarkup.rows.size()) {
                TLRPC$TL_keyboardButtonRow tLRPC$TL_keyboardButtonRow = (TLRPC$TL_keyboardButtonRow) tLRPC$TL_replyKeyboardMarkup.rows.get(i);
                View linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(0);
                this.container.addView(linearLayout, LayoutHelper.createLinear(-1, this.buttonHeight, 15.0f, i == 0 ? 15.0f : 10.0f, 15.0f, i == tLRPC$TL_replyKeyboardMarkup.rows.size() + -1 ? 15.0f : BitmapDescriptorFactory.HUE_RED));
                float size = 1.0f / ((float) tLRPC$TL_keyboardButtonRow.buttons.size());
                int i2 = 0;
                while (i2 < tLRPC$TL_keyboardButtonRow.buttons.size()) {
                    KeyboardButton keyboardButton = (KeyboardButton) tLRPC$TL_keyboardButtonRow.buttons.get(i2);
                    View textView = new TextView(getContext());
                    textView.setTag(keyboardButton);
                    textView.setTextColor(Theme.getColor(Theme.key_chat_botKeyboardButtonText));
                    textView.setTextSize(1, 16.0f);
                    textView.setGravity(17);
                    textView.setBackgroundDrawable(Theme.createSimpleSelectorRoundRectDrawable(AndroidUtilities.dp(4.0f), Theme.getColor(Theme.key_chat_botKeyboardButtonBackground), Theme.getColor(Theme.key_chat_botKeyboardButtonBackgroundPressed)));
                    textView.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
                    textView.setText(Emoji.replaceEmoji(keyboardButton.text, textView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(16.0f), false));
                    linearLayout.addView(textView, LayoutHelper.createLinear(0, -1, size, 0, 0, i2 != tLRPC$TL_keyboardButtonRow.buttons.size() + -1 ? 10 : 0, 0));
                    textView.setOnClickListener(new C43391());
                    this.buttonViews.add(textView);
                    i2++;
                }
                i++;
            }
        }
    }

    public void setDelegate(BotKeyboardViewDelegate botKeyboardViewDelegate) {
        this.delegate = botKeyboardViewDelegate;
    }

    public void setPanelHeight(int i) {
        this.panelHeight = i;
        if (this.isFullSize && this.botButtons != null && this.botButtons.rows.size() != 0) {
            this.buttonHeight = !this.isFullSize ? 42 : (int) Math.max(42.0f, ((float) (((this.panelHeight - AndroidUtilities.dp(30.0f)) - ((this.botButtons.rows.size() - 1) * AndroidUtilities.dp(10.0f))) / this.botButtons.rows.size())) / AndroidUtilities.density);
            int childCount = this.container.getChildCount();
            int dp = AndroidUtilities.dp((float) this.buttonHeight);
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = this.container.getChildAt(i2);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.height != dp) {
                    layoutParams.height = dp;
                    childAt.setLayoutParams(layoutParams);
                }
            }
        }
    }
}
