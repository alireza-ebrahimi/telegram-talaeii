package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.EmojiData;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Components.IdenticonDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.URLSpanReplacement;

public class IdenticonActivity extends BaseFragment implements NotificationCenterDelegate {
    private AnimatorSet animatorSet;
    private int chat_id;
    private TextView codeTextView;
    private FrameLayout container;
    private boolean emojiSelected;
    private String emojiText;
    private TextView emojiTextView;
    private AnimatorSet hintAnimatorSet;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout1;
    private TextView textView;
    private int textWidth;

    /* renamed from: org.telegram.ui.IdenticonActivity$1 */
    class C47921 extends ActionBarMenuOnItemClick {
        C47921() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                IdenticonActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.IdenticonActivity$2 */
    class C47932 implements OnTouchListener {
        C47932() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.IdenticonActivity$4 */
    class C47954 extends AnimatorListenerAdapter {
        C47954() {
        }

        public void onAnimationEnd(Animator animator) {
            if (animator.equals(IdenticonActivity.this.animatorSet)) {
                IdenticonActivity.this.animatorSet = null;
            }
        }
    }

    /* renamed from: org.telegram.ui.IdenticonActivity$5 */
    class C47965 implements OnPreDrawListener {
        C47965() {
        }

        public boolean onPreDraw() {
            if (IdenticonActivity.this.fragmentView != null) {
                IdenticonActivity.this.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
                int rotation = ((WindowManager) ApplicationLoader.applicationContext.getSystemService("window")).getDefaultDisplay().getRotation();
                if (rotation == 3 || rotation == 1) {
                    IdenticonActivity.this.linearLayout.setOrientation(0);
                } else {
                    IdenticonActivity.this.linearLayout.setOrientation(1);
                }
                IdenticonActivity.this.fragmentView.setPadding(IdenticonActivity.this.fragmentView.getPaddingLeft(), 0, IdenticonActivity.this.fragmentView.getPaddingRight(), IdenticonActivity.this.fragmentView.getPaddingBottom());
            }
            return true;
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            try {
                return super.onTouchEvent(textView, spannable, motionEvent);
            } catch (Throwable e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    public IdenticonActivity(Bundle bundle) {
        super(bundle);
    }

    private void fixLayout() {
        this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new C47965());
    }

    private void updateEmojiButton(boolean z) {
        float f = BitmapDescriptorFactory.HUE_RED;
        if (this.animatorSet != null) {
            this.animatorSet.cancel();
            this.animatorSet = null;
        }
        if (z) {
            this.animatorSet = new AnimatorSet();
            AnimatorSet animatorSet = this.animatorSet;
            Animator[] animatorArr = new Animator[6];
            TextView textView = this.emojiTextView;
            String str = "alpha";
            float[] fArr = new float[1];
            fArr[0] = this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED;
            animatorArr[0] = ObjectAnimator.ofFloat(textView, str, fArr);
            textView = this.codeTextView;
            str = "alpha";
            fArr = new float[1];
            fArr[0] = this.emojiSelected ? BitmapDescriptorFactory.HUE_RED : 1.0f;
            animatorArr[1] = ObjectAnimator.ofFloat(textView, str, fArr);
            TextView textView2 = this.emojiTextView;
            String str2 = "scaleX";
            float[] fArr2 = new float[1];
            fArr2[0] = this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED;
            animatorArr[2] = ObjectAnimator.ofFloat(textView2, str2, fArr2);
            textView2 = this.emojiTextView;
            str2 = "scaleY";
            fArr2 = new float[1];
            fArr2[0] = this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED;
            animatorArr[3] = ObjectAnimator.ofFloat(textView2, str2, fArr2);
            textView2 = this.codeTextView;
            str2 = "scaleX";
            fArr2 = new float[1];
            fArr2[0] = this.emojiSelected ? BitmapDescriptorFactory.HUE_RED : 1.0f;
            animatorArr[4] = ObjectAnimator.ofFloat(textView2, str2, fArr2);
            textView = this.codeTextView;
            str = "scaleY";
            fArr = new float[1];
            if (!this.emojiSelected) {
                f = 1.0f;
            }
            fArr[0] = f;
            animatorArr[5] = ObjectAnimator.ofFloat(textView, str, fArr);
            animatorSet.playTogether(animatorArr);
            this.animatorSet.addListener(new C47954());
            this.animatorSet.setInterpolator(new DecelerateInterpolator());
            this.animatorSet.setDuration(150);
            this.animatorSet.start();
        } else {
            this.emojiTextView.setAlpha(this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.codeTextView.setAlpha(this.emojiSelected ? BitmapDescriptorFactory.HUE_RED : 1.0f);
            this.emojiTextView.setScaleX(this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.emojiTextView.setScaleY(this.emojiSelected ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.codeTextView.setScaleX(this.emojiSelected ? BitmapDescriptorFactory.HUE_RED : 1.0f);
            TextView textView3 = this.codeTextView;
            if (!this.emojiSelected) {
                f = 1.0f;
            }
            textView3.setScaleY(f);
        }
        this.emojiTextView.setTag(!this.emojiSelected ? Theme.key_chat_emojiPanelIcon : Theme.key_chat_emojiPanelIconSelected);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("EncryptionKey", R.string.EncryptionKey));
        this.actionBar.setActionBarMenuOnItemClick(new C47921());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView.setOnTouchListener(new C47932());
        this.linearLayout = new LinearLayout(context);
        this.linearLayout.setOrientation(1);
        this.linearLayout.setWeightSum(100.0f);
        frameLayout.addView(this.linearLayout, LayoutHelper.createFrame(-1, -1.0f));
        View frameLayout2 = new FrameLayout(context);
        frameLayout2.setPadding(AndroidUtilities.dp(20.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(20.0f), AndroidUtilities.dp(20.0f));
        this.linearLayout.addView(frameLayout2, LayoutHelper.createLinear(-1, -1, 50.0f));
        View imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.FIT_XY);
        frameLayout2.addView(imageView, LayoutHelper.createFrame(-1, -1.0f));
        this.container = new FrameLayout(context) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                if (IdenticonActivity.this.codeTextView != null) {
                    int left = (IdenticonActivity.this.codeTextView.getLeft() + (IdenticonActivity.this.codeTextView.getMeasuredWidth() / 2)) - (IdenticonActivity.this.emojiTextView.getMeasuredWidth() / 2);
                    int measuredHeight = (((IdenticonActivity.this.codeTextView.getMeasuredHeight() - IdenticonActivity.this.emojiTextView.getMeasuredHeight()) / 2) + IdenticonActivity.this.linearLayout1.getTop()) - AndroidUtilities.dp(16.0f);
                    IdenticonActivity.this.emojiTextView.layout(left, measuredHeight, IdenticonActivity.this.emojiTextView.getMeasuredWidth() + left, IdenticonActivity.this.emojiTextView.getMeasuredHeight() + measuredHeight);
                }
            }
        };
        this.container.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.container, LayoutHelper.createLinear(-1, -1, 50.0f));
        this.linearLayout1 = new LinearLayout(context);
        this.linearLayout1.setOrientation(1);
        this.linearLayout1.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
        this.container.addView(this.linearLayout1, LayoutHelper.createFrame(-2, -2, 17));
        this.codeTextView = new TextView(context);
        this.codeTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.codeTextView.setGravity(17);
        this.codeTextView.setTypeface(Typeface.MONOSPACE);
        this.codeTextView.setTextSize(1, 16.0f);
        this.linearLayout1.addView(this.codeTextView, LayoutHelper.createLinear(-2, -2, 1));
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.textView.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLinksClickable(true);
        this.textView.setClickable(true);
        this.textView.setGravity(17);
        this.textView.setMovementMethod(new LinkMovementMethodMy());
        this.linearLayout1.addView(this.textView, LayoutHelper.createFrame(-2, -2, 1));
        this.emojiTextView = new TextView(context);
        this.emojiTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
        this.emojiTextView.setGravity(17);
        this.emojiTextView.setTextSize(1, 32.0f);
        this.container.addView(this.emojiTextView, LayoutHelper.createFrame(-2, -2.0f));
        EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(this.chat_id));
        if (encryptedChat != null) {
            int i;
            Drawable identiconDrawable = new IdenticonDrawable();
            imageView.setImageDrawable(identiconDrawable);
            identiconDrawable.setEncryptedChat(encryptedChat);
            User user = MessagesController.getInstance().getUser(Integer.valueOf(encryptedChat.user_id));
            CharSequence spannableStringBuilder = new SpannableStringBuilder();
            StringBuilder stringBuilder = new StringBuilder();
            if (encryptedChat.key_hash.length > 16) {
                String bytesToHex = Utilities.bytesToHex(encryptedChat.key_hash);
                for (i = 0; i < 32; i++) {
                    if (i != 0) {
                        if (i % 8 == 0) {
                            spannableStringBuilder.append('\n');
                        } else if (i % 4 == 0) {
                            spannableStringBuilder.append(' ');
                        }
                    }
                    spannableStringBuilder.append(bytesToHex.substring(i * 2, (i * 2) + 2));
                    spannableStringBuilder.append(' ');
                }
                spannableStringBuilder.append("\n");
                for (i = 0; i < 5; i++) {
                    int i2 = ((((encryptedChat.key_hash[(i * 4) + 16] & 127) << 24) | ((encryptedChat.key_hash[((i * 4) + 16) + 1] & 255) << 16)) | ((encryptedChat.key_hash[((i * 4) + 16) + 2] & 255) << 8)) | (encryptedChat.key_hash[((i * 4) + 16) + 3] & 255);
                    if (i != 0) {
                        stringBuilder.append(" ");
                    }
                    stringBuilder.append(EmojiData.emojiSecret[i2 % EmojiData.emojiSecret.length]);
                }
                this.emojiText = stringBuilder.toString();
            }
            this.codeTextView.setText(spannableStringBuilder.toString());
            spannableStringBuilder.clear();
            spannableStringBuilder.append(AndroidUtilities.replaceTags(LocaleController.formatString("EncryptionKeyDescription", R.string.EncryptionKeyDescription, new Object[]{user.first_name, user.first_name})));
            String str = "telegram.org";
            i = spannableStringBuilder.toString().indexOf("telegram.org");
            if (i != -1) {
                spannableStringBuilder.setSpan(new URLSpanReplacement(LocaleController.getString("EncryptionKeyLink", R.string.EncryptionKeyLink)), i, "telegram.org".length() + i, 33);
            }
            this.textView.setText(spannableStringBuilder);
        }
        updateEmojiButton(false);
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded && this.emojiTextView != null) {
            this.emojiTextView.invalidate();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[9];
        themeDescriptionArr[0] = new ThemeDescription(this.container, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[6] = new ThemeDescription(this.textView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[7] = new ThemeDescription(this.codeTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[8] = new ThemeDescription(this.textView, ThemeDescription.FLAG_LINKCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        return themeDescriptionArr;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        fixLayout();
    }

    public boolean onFragmentCreate() {
        this.chat_id = getArguments().getInt("chat_id");
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
    }

    public void onResume() {
        super.onResume();
        fixLayout();
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2 && this.emojiText != null) {
            this.emojiTextView.setText(Emoji.replaceEmoji(this.emojiText, this.emojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(32.0f), false));
        }
    }
}
