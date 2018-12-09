package org.telegram.ui.Components;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.EmojiView.Listener;
import org.telegram.ui.Components.SizeNotifierFrameLayoutPhoto.SizeNotifierFrameLayoutPhotoDelegate;

public class PhotoViewerCaptionEnterView extends FrameLayout implements NotificationCenterDelegate, SizeNotifierFrameLayoutPhotoDelegate {
    private int audioInterfaceState;
    private final int captionMaxLength = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
    private ActionMode currentActionMode;
    private PhotoViewerCaptionEnterViewDelegate delegate;
    private ImageView emojiButton;
    private int emojiPadding;
    private EmojiView emojiView;
    private boolean forceFloatingEmoji;
    private boolean innerTextChange;
    private int keyboardHeight;
    private int keyboardHeightLand;
    private boolean keyboardVisible;
    private int lastSizeChangeValue1;
    private boolean lastSizeChangeValue2;
    private EditTextBoldCursor messageEditText;
    private AnimatorSet runningAnimation;
    private AnimatorSet runningAnimation2;
    private ObjectAnimator runningAnimationAudio;
    private int runningAnimationType;
    private SizeNotifierFrameLayoutPhoto sizeNotifierLayout;
    private View windowView;

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$1 */
    class C45391 implements OnClickListener {
        C45391() {
        }

        public void onClick(View view) {
            if (PhotoViewerCaptionEnterView.this.isPopupShowing()) {
                PhotoViewerCaptionEnterView.this.openKeyboardInternal();
            } else {
                PhotoViewerCaptionEnterView.this.showPopup(1);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$3 */
    class C45413 implements ActionMode.Callback {
        C45413() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            PhotoViewerCaptionEnterView.this.currentActionMode = actionMode;
            return true;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            if (PhotoViewerCaptionEnterView.this.currentActionMode == actionMode) {
                PhotoViewerCaptionEnterView.this.currentActionMode = null;
            }
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            if (VERSION.SDK_INT >= 23) {
                PhotoViewerCaptionEnterView.this.fixActionMode(actionMode);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$4 */
    class C45424 implements ActionMode.Callback {
        C45424() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            PhotoViewerCaptionEnterView.this.currentActionMode = actionMode;
            return true;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
            if (PhotoViewerCaptionEnterView.this.currentActionMode == actionMode) {
                PhotoViewerCaptionEnterView.this.currentActionMode = null;
            }
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            if (VERSION.SDK_INT >= 23) {
                PhotoViewerCaptionEnterView.this.fixActionMode(actionMode);
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$5 */
    class C45435 implements OnKeyListener {
        C45435() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (i == 4) {
                if (PhotoViewerCaptionEnterView.this.windowView != null && PhotoViewerCaptionEnterView.this.hideActionMode()) {
                    return true;
                }
                if (!PhotoViewerCaptionEnterView.this.keyboardVisible && PhotoViewerCaptionEnterView.this.isPopupShowing()) {
                    if (keyEvent.getAction() != 1) {
                        return true;
                    }
                    PhotoViewerCaptionEnterView.this.showPopup(0);
                    return true;
                }
            }
            return false;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$6 */
    class C45446 implements OnClickListener {
        C45446() {
        }

        public void onClick(View view) {
            if (PhotoViewerCaptionEnterView.this.isPopupShowing()) {
                PhotoViewerCaptionEnterView.this.showPopup(AndroidUtilities.usingHardwareInput ? 0 : 2);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$7 */
    class C45457 implements TextWatcher {
        boolean processChange = false;

        C45457() {
        }

        public void afterTextChanged(Editable editable) {
            if (!PhotoViewerCaptionEnterView.this.innerTextChange && this.processChange) {
                ImageSpan[] imageSpanArr = (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class);
                for (Object removeSpan : imageSpanArr) {
                    editable.removeSpan(removeSpan);
                }
                Emoji.replaceEmoji(editable, PhotoViewerCaptionEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                this.processChange = false;
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!PhotoViewerCaptionEnterView.this.innerTextChange) {
                if (PhotoViewerCaptionEnterView.this.delegate != null) {
                    PhotoViewerCaptionEnterView.this.delegate.onTextChanged(charSequence);
                }
                if (i2 != i3 && i3 - i2 > 1) {
                    this.processChange = true;
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$8 */
    class C45468 implements OnClickListener {
        C45468() {
        }

        public void onClick(View view) {
            PhotoViewerCaptionEnterView.this.delegate.onCaptionEnter();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoViewerCaptionEnterView$9 */
    class C45479 implements Listener {
        C45479() {
        }

        public boolean onBackspace() {
            if (PhotoViewerCaptionEnterView.this.messageEditText.length() == 0) {
                return false;
            }
            PhotoViewerCaptionEnterView.this.messageEditText.dispatchKeyEvent(new KeyEvent(0, 67));
            return true;
        }

        public void onClearEmojiRecent() {
        }

        public void onEmojiSelected(String str) {
            if (PhotoViewerCaptionEnterView.this.messageEditText.length() + str.length() <= Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                int selectionEnd = PhotoViewerCaptionEnterView.this.messageEditText.getSelectionEnd();
                if (selectionEnd < 0) {
                    selectionEnd = 0;
                }
                try {
                    PhotoViewerCaptionEnterView.this.innerTextChange = true;
                    CharSequence replaceEmoji = Emoji.replaceEmoji(str, PhotoViewerCaptionEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                    PhotoViewerCaptionEnterView.this.messageEditText.setText(PhotoViewerCaptionEnterView.this.messageEditText.getText().insert(selectionEnd, replaceEmoji));
                    selectionEnd += replaceEmoji.length();
                    PhotoViewerCaptionEnterView.this.messageEditText.setSelection(selectionEnd, selectionEnd);
                } catch (Throwable e) {
                    FileLog.e(e);
                } finally {
                    PhotoViewerCaptionEnterView.this.innerTextChange = false;
                }
            }
        }

        public void onGifSelected(Document document) {
        }

        public void onGifTab(boolean z) {
        }

        public void onShowStickerSet(StickerSet stickerSet, InputStickerSet inputStickerSet) {
        }

        public void onStickerSelected(Document document) {
        }

        public void onStickerSetAdd(StickerSetCovered stickerSetCovered) {
        }

        public void onStickerSetRemove(StickerSetCovered stickerSetCovered) {
        }

        public void onStickersGroupClick(int i) {
        }

        public void onStickersSettingsClick() {
        }

        public void onStickersTab(boolean z) {
        }
    }

    public interface PhotoViewerCaptionEnterViewDelegate {
        void onCaptionEnter();

        void onTextChanged(CharSequence charSequence);

        void onWindowSizeChanged(int i);
    }

    public PhotoViewerCaptionEnterView(Context context, SizeNotifierFrameLayoutPhoto sizeNotifierFrameLayoutPhoto, View view) {
        super(context);
        setBackgroundColor(Theme.ACTION_BAR_PHOTO_VIEWER_COLOR);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.windowView = view;
        this.sizeNotifierLayout = sizeNotifierFrameLayoutPhoto;
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(0);
        addView(linearLayout, LayoutHelper.createFrame(-1, -2.0f, 51, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View frameLayout = new FrameLayout(context);
        linearLayout.addView(frameLayout, LayoutHelper.createLinear(0, -2, 1.0f));
        this.emojiButton = new ImageView(context);
        this.emojiButton.setImageResource(R.drawable.ic_smile_w);
        this.emojiButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.emojiButton.setPadding(AndroidUtilities.dp(4.0f), AndroidUtilities.dp(1.0f), 0, 0);
        frameLayout.addView(this.emojiButton, LayoutHelper.createFrame(48, 48, 83));
        this.emojiButton.setOnClickListener(new C45391());
        this.messageEditText = new EditTextBoldCursor(context) {
            protected void onMeasure(int i, int i2) {
                try {
                    super.onMeasure(i, i2);
                } catch (Throwable e) {
                    setMeasuredDimension(MeasureSpec.getSize(i), AndroidUtilities.dp(51.0f));
                    FileLog.e(e);
                }
            }
        };
        if (VERSION.SDK_INT >= 23 && this.windowView != null) {
            this.messageEditText.setCustomSelectionActionModeCallback(new C45413());
            this.messageEditText.setCustomInsertionActionModeCallback(new C45424());
        }
        this.messageEditText.setHint(LocaleController.getString("AddCaption", R.string.AddCaption));
        this.messageEditText.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.messageEditText.setInputType(this.messageEditText.getInputType() | MessagesController.UPDATE_MASK_CHAT_ADMINS);
        this.messageEditText.setMaxLines(4);
        this.messageEditText.setHorizontallyScrolling(false);
        this.messageEditText.setTextSize(1, 18.0f);
        this.messageEditText.setGravity(80);
        this.messageEditText.setPadding(0, AndroidUtilities.dp(11.0f), 0, AndroidUtilities.dp(12.0f));
        this.messageEditText.setBackgroundDrawable(null);
        this.messageEditText.setCursorColor(-1);
        this.messageEditText.setCursorSize(AndroidUtilities.dp(20.0f));
        this.messageEditText.setTextColor(-1);
        this.messageEditText.setHintTextColor(-1291845633);
        this.messageEditText.setFilters(new InputFilter[]{new LengthFilter(Callback.DEFAULT_DRAG_ANIMATION_DURATION)});
        frameLayout.addView(this.messageEditText, LayoutHelper.createFrame(-1, -2.0f, 83, 52.0f, BitmapDescriptorFactory.HUE_RED, 6.0f, BitmapDescriptorFactory.HUE_RED));
        this.messageEditText.setOnKeyListener(new C45435());
        this.messageEditText.setOnClickListener(new C45446());
        this.messageEditText.addTextChangedListener(new C45457());
        View imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageResource(R.drawable.ic_done);
        linearLayout.addView(imageView, LayoutHelper.createLinear(48, 48, 80));
        if (VERSION.SDK_INT >= 21) {
            imageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        }
        imageView.setOnClickListener(new C45468());
    }

    private void createEmojiView() {
        if (this.emojiView == null) {
            this.emojiView = new EmojiView(false, false, getContext(), null);
            this.emojiView.setListener(new C45479());
            this.sizeNotifierLayout.addView(this.emojiView);
        }
    }

    private void fixActionMode(ActionMode actionMode) {
        try {
            Class cls = Class.forName("com.android.internal.view.FloatingActionMode");
            Field declaredField = cls.getDeclaredField("mFloatingToolbar");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(actionMode);
            Class cls2 = Class.forName("com.android.internal.widget.FloatingToolbar");
            Field declaredField2 = cls2.getDeclaredField("mPopup");
            Field declaredField3 = cls2.getDeclaredField("mWidthChanged");
            declaredField2.setAccessible(true);
            declaredField3.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            declaredField2 = Class.forName("com.android.internal.widget.FloatingToolbar$FloatingToolbarPopup").getDeclaredField("mParent");
            declaredField2.setAccessible(true);
            if (((View) declaredField2.get(obj2)) != this.windowView) {
                declaredField2.set(obj2, this.windowView);
                Method declaredMethod = cls.getDeclaredMethod("updateViewLocationInWindow", new Class[0]);
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(actionMode, new Object[0]);
            }
        } catch (Throwable th) {
            FileLog.e(th);
        }
    }

    private void onWindowSizeChanged() {
        int height = this.sizeNotifierLayout.getHeight();
        if (!this.keyboardVisible) {
            height -= this.emojiPadding;
        }
        if (this.delegate != null) {
            this.delegate.onWindowSizeChanged(height);
        }
    }

    private void openKeyboardInternal() {
        showPopup(AndroidUtilities.usingHardwareInput ? 0 : 2);
        openKeyboard();
    }

    private void showPopup(int i) {
        if (i == 1) {
            if (this.emojiView == null) {
                createEmojiView();
            }
            this.emojiView.setVisibility(0);
            if (this.keyboardHeight <= 0) {
                this.keyboardHeight = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height", AndroidUtilities.dp(200.0f));
            }
            if (this.keyboardHeightLand <= 0) {
                this.keyboardHeightLand = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
            }
            int i2 = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight;
            LayoutParams layoutParams = (LayoutParams) this.emojiView.getLayoutParams();
            layoutParams.width = AndroidUtilities.displaySize.x;
            layoutParams.height = i2;
            this.emojiView.setLayoutParams(layoutParams);
            if (!(AndroidUtilities.isInMultiwindow || this.forceFloatingEmoji)) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
            if (this.sizeNotifierLayout != null) {
                this.emojiPadding = i2;
                this.sizeNotifierLayout.requestLayout();
                this.emojiButton.setImageResource(R.drawable.ic_keyboard_w);
                onWindowSizeChanged();
                return;
            }
            return;
        }
        if (this.emojiButton != null) {
            this.emojiButton.setImageResource(R.drawable.ic_smile_w);
        }
        if (this.emojiView != null) {
            this.emojiView.setVisibility(8);
        }
        if (this.sizeNotifierLayout != null) {
            if (i == 0) {
                this.emojiPadding = 0;
            }
            this.sizeNotifierLayout.requestLayout();
            onWindowSizeChanged();
        }
    }

    public void addEmojiToRecent(String str) {
        createEmojiView();
        this.emojiView.addEmojiToRecent(str);
    }

    public void closeKeyboard() {
        AndroidUtilities.hideKeyboard(this.messageEditText);
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded && this.emojiView != null) {
            this.emojiView.invalidateViews();
        }
    }

    public int getCursorPosition() {
        return this.messageEditText == null ? 0 : this.messageEditText.getSelectionStart();
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    public CharSequence getFieldCharSequence() {
        return this.messageEditText.getText();
    }

    public boolean hideActionMode() {
        if (VERSION.SDK_INT < 23 || this.currentActionMode == null) {
            return false;
        }
        try {
            this.currentActionMode.finish();
        } catch (Throwable e) {
            FileLog.e(e);
        }
        this.currentActionMode = null;
        return true;
    }

    public void hidePopup() {
        if (isPopupShowing()) {
            showPopup(0);
        }
    }

    public boolean isKeyboardVisible() {
        return (AndroidUtilities.usingHardwareInput && getTag() != null) || this.keyboardVisible;
    }

    public boolean isPopupShowing() {
        return this.emojiView != null && this.emojiView.getVisibility() == 0;
    }

    public boolean isPopupView(View view) {
        return view == this.emojiView;
    }

    public void onCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        this.sizeNotifierLayout.setDelegate(this);
    }

    public void onDestroy() {
        hidePopup();
        if (isKeyboardVisible()) {
            closeKeyboard();
        }
        this.keyboardVisible = false;
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        if (this.sizeNotifierLayout != null) {
            this.sizeNotifierLayout.setDelegate(null);
        }
    }

    public void onSizeChanged(int i, boolean z) {
        if (i > AndroidUtilities.dp(50.0f) && this.keyboardVisible && !AndroidUtilities.isInMultiwindow && !this.forceFloatingEmoji) {
            if (z) {
                this.keyboardHeightLand = i;
                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height_land3", this.keyboardHeightLand).commit();
            } else {
                this.keyboardHeight = i;
                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height", this.keyboardHeight).commit();
            }
        }
        if (isPopupShowing()) {
            int i2 = z ? this.keyboardHeightLand : this.keyboardHeight;
            LayoutParams layoutParams = (LayoutParams) this.emojiView.getLayoutParams();
            if (!(layoutParams.width == AndroidUtilities.displaySize.x && layoutParams.height == i2)) {
                layoutParams.width = AndroidUtilities.displaySize.x;
                layoutParams.height = i2;
                this.emojiView.setLayoutParams(layoutParams);
                if (this.sizeNotifierLayout != null) {
                    this.emojiPadding = layoutParams.height;
                    this.sizeNotifierLayout.requestLayout();
                    onWindowSizeChanged();
                }
            }
        }
        if (this.lastSizeChangeValue1 == i && this.lastSizeChangeValue2 == z) {
            onWindowSizeChanged();
            return;
        }
        this.lastSizeChangeValue1 = i;
        this.lastSizeChangeValue2 = z;
        boolean z2 = this.keyboardVisible;
        this.keyboardVisible = i > 0;
        if (this.keyboardVisible && isPopupShowing()) {
            showPopup(0);
        }
        if (!(this.emojiPadding == 0 || this.keyboardVisible || this.keyboardVisible == z2 || isPopupShowing())) {
            this.emojiPadding = 0;
            this.sizeNotifierLayout.requestLayout();
        }
        onWindowSizeChanged();
    }

    public void openKeyboard() {
        int selectionStart;
        try {
            selectionStart = this.messageEditText.getSelectionStart();
        } catch (Throwable e) {
            Throwable th = e;
            int length = this.messageEditText.length();
            FileLog.e(th);
            selectionStart = length;
        }
        MotionEvent obtain = MotionEvent.obtain(0, 0, 0, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.messageEditText.onTouchEvent(obtain);
        obtain.recycle();
        MotionEvent obtain2 = MotionEvent.obtain(0, 0, 1, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 0);
        this.messageEditText.onTouchEvent(obtain2);
        obtain2.recycle();
        AndroidUtilities.showKeyboard(this.messageEditText);
        try {
            this.messageEditText.setSelection(selectionStart);
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
    }

    public void replaceWithText(int i, int i2, CharSequence charSequence, boolean z) {
        try {
            CharSequence spannableStringBuilder = new SpannableStringBuilder(this.messageEditText.getText());
            spannableStringBuilder.replace(i, i + i2, charSequence);
            if (z) {
                Emoji.replaceEmoji(spannableStringBuilder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            }
            this.messageEditText.setText(spannableStringBuilder);
            if (charSequence.length() + i <= this.messageEditText.length()) {
                this.messageEditText.setSelection(charSequence.length() + i);
            } else {
                this.messageEditText.setSelection(this.messageEditText.length());
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void setDelegate(PhotoViewerCaptionEnterViewDelegate photoViewerCaptionEnterViewDelegate) {
        this.delegate = photoViewerCaptionEnterViewDelegate;
    }

    public void setFieldFocused(boolean z) {
        if (this.messageEditText != null) {
            if (z) {
                if (!this.messageEditText.isFocused()) {
                    this.messageEditText.postDelayed(new Runnable() {
                        public void run() {
                            if (PhotoViewerCaptionEnterView.this.messageEditText != null) {
                                try {
                                    PhotoViewerCaptionEnterView.this.messageEditText.requestFocus();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                    }, 600);
                }
            } else if (this.messageEditText.isFocused() && !this.keyboardVisible) {
                this.messageEditText.clearFocus();
            }
        }
    }

    public void setFieldText(CharSequence charSequence) {
        if (this.messageEditText != null) {
            this.messageEditText.setText(charSequence);
            this.messageEditText.setSelection(this.messageEditText.getText().length());
            if (this.delegate != null) {
                this.delegate.onTextChanged(this.messageEditText.getText());
            }
        }
    }

    public void setForceFloatingEmoji(boolean z) {
        this.forceFloatingEmoji = z;
    }
}
