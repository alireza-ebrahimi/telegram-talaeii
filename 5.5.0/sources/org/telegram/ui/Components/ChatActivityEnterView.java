package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.design.widget.C0147c;
import android.support.p010c.p012b.p013a.C0044a;
import android.support.p010c.p012b.p013a.C0054c;
import android.support.p010c.p012b.p013a.C0054c.C0053d;
import android.support.p010c.p012b.p013a.C0060e;
import android.support.v4.p014d.C0432c;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.Property;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.C2557f;
import org.telegram.customization.Model.Payment.PaymentLink;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.util.NumericEditText;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.VideoEditedInfo;
import org.telegram.messenger.camera.CameraController;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_keyboardButton;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonCallback;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRequestGeoLocation;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRequestPhone;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrl;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageEntityBold;
import org.telegram.tgnet.TLRPC$TL_messageEntityCode;
import org.telegram.tgnet.TLRPC$TL_messageEntityItalic;
import org.telegram.tgnet.TLRPC$TL_messageEntityPre;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardMarkup;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.StickerSet;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.BotKeyboardView.BotKeyboardViewDelegate;
import org.telegram.ui.Components.EmojiView.DragListener;
import org.telegram.ui.Components.EmojiView.Listener;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;
import org.telegram.ui.Components.SizeNotifierFrameLayout.SizeNotifierFrameLayoutDelegate;
import org.telegram.ui.Components.StickersAlert.StickersAlertDelegate;
import org.telegram.ui.Components.VideoTimelineView.VideoTimelineViewDelegate;
import org.telegram.ui.DialogsActivity;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.GroupStickersActivity;
import org.telegram.ui.StickersActivity;
import utils.p178a.C3791b;
import utils.view.FarsiEditText;
import utils.view.ToastUtil;

public class ChatActivityEnterView extends FrameLayout implements C2497d, NotificationCenterDelegate, SizeNotifierFrameLayoutDelegate, StickersAlertDelegate {
    private boolean allowGifs;
    private boolean allowShowTopView;
    private boolean allowStickers;
    private ImageView attachButton;
    private LinearLayout attachLayout;
    private ImageView audioSendButton;
    private TLRPC$TL_document audioToSend;
    private MessageObject audioToSendMessageObject;
    private String audioToSendPath;
    private AnimatorSet audioVideoButtonAnimation;
    private FrameLayout audioVideoButtonContainer;
    private ImageView botButton;
    private MessageObject botButtonsMessageObject;
    private int botCount;
    private PopupWindow botKeyboardPopup;
    private BotKeyboardView botKeyboardView;
    private MessageObject botMessageObject;
    private TLRPC$TL_replyKeyboardMarkup botReplyMarkup;
    private boolean calledRecordRunnable;
    private Drawable cameraDrawable;
    private boolean canWriteToChannel;
    private ImageView cancelBotButton;
    private Chat currentChat;
    private int currentPopupContentType;
    private AnimatorSet currentTopViewAnimation;
    private ChatActivityEnterViewDelegate delegate;
    long dialogId;
    ProgressDialog dialogLoading;
    private long dialog_id;
    private float distCanMove;
    private AnimatorSet doneButtonAnimation;
    private FrameLayout doneButtonContainer;
    private ImageView doneButtonImage;
    private ContextProgressView doneButtonProgress;
    private Paint dotPaint;
    private boolean editingCaption;
    private MessageObject editingMessageObject;
    private int editingMessageReqId;
    private ImageView emojiButton;
    private int emojiPadding;
    private EmojiView emojiView;
    private ImageView expandStickersButton;
    private boolean forceShowSendButton;
    private boolean gifsTabOpen;
    private boolean hasBotCommands;
    private boolean hasRecordVideo;
    private boolean ignoreTextChange;
    private ChatFull info;
    private int innerTextChange;
    private boolean isPaused;
    private int keyboardHeight;
    private int keyboardHeightLand;
    private boolean keyboardVisible;
    private int lastSizeChangeValue1;
    private boolean lastSizeChangeValue2;
    private String lastTimeString;
    private long lastTypingSendTime;
    private long lastTypingTimeSend;
    private Drawable lockArrowDrawable;
    private Drawable lockBackgroundDrawable;
    private Drawable lockDrawable;
    private Drawable lockShadowDrawable;
    private Drawable lockTopDrawable;
    private EditTextCaption messageEditText;
    private TLRPC$WebPage messageWebPage;
    private boolean messageWebPageSearch;
    private Drawable micDrawable;
    private boolean needShowTopView;
    private ImageView notifyButton;
    private Runnable openKeyboardRunnable;
    private Paint paint;
    private Paint paintRecord;
    private ImageView paintingButton;
    private Activity parentActivity;
    private ChatActivity parentFragment;
    private Drawable pauseDrawable;
    private KeyboardButton pendingLocationButton;
    private MessageObject pendingMessageObject;
    private Drawable playDrawable;
    private CloseProgressDrawable2 progressDrawable;
    private Runnable recordAudioVideoRunnable;
    private boolean recordAudioVideoRunnableStarted;
    private ImageView recordCancelImage;
    private TextView recordCancelText;
    private RecordCircle recordCircle;
    private ImageView recordDeleteImageView;
    private RecordDot recordDot;
    private int recordInterfaceState;
    private FrameLayout recordPanel;
    private TextView recordSendText;
    private LinearLayout recordTimeContainer;
    private TextView recordTimeText;
    private View recordedAudioBackground;
    private FrameLayout recordedAudioPanel;
    private ImageView recordedAudioPlayButton;
    private SeekBarWaveformView recordedAudioSeekBar;
    private TextView recordedAudioTimeTextView;
    private boolean recordingAudioVideo;
    private RectF rect;
    private Paint redDotPaint;
    private MessageObject replyingMessageObject;
    private Property<View, Integer> roundedTranslationYProperty;
    private AnimatorSet runningAnimation;
    private AnimatorSet runningAnimation2;
    private AnimatorSet runningAnimationAudio;
    private int runningAnimationType;
    private ImageView sendButton;
    private FrameLayout sendButtonContainer;
    private boolean sendByEnter;
    private Drawable sendDrawable;
    private boolean showKeyboardOnResume;
    private boolean silent;
    private SizeNotifierFrameLayout sizeNotifierLayout;
    private LinearLayout slideText;
    private float startedDraggingX;
    private AnimatedArrowDrawable stickersArrow;
    private boolean stickersDragging;
    private boolean stickersExpanded;
    private int stickersExpandedHeight;
    private Animator stickersExpansionAnim;
    private float stickersExpansionProgress;
    private boolean stickersTabOpen;
    private LinearLayout textFieldContainer;
    private View topView;
    private boolean topViewShowed;
    private Runnable updateExpandabilityRunnable;
    private ImageView videoSendButton;
    private VideoTimelineView videoTimelineView;
    private VideoEditedInfo videoToSendMessageObject;
    private boolean waitingForKeyboardOpen;
    private WakeLock wakeLock;

    public interface ChatActivityEnterViewDelegate {
        void didPressedAttachButton();

        void needChangeVideoPreviewState(int i, float f);

        void needSendTyping();

        void needShowMediaBanHint();

        void needStartRecordAudio(int i);

        void needStartRecordVideo(int i);

        void onAttachButtonHidden();

        void onAttachButtonShow();

        void onMessageEditEnd(boolean z);

        void onMessageSend(CharSequence charSequence);

        void onPreAudioVideoRecord();

        void onStickersTab(boolean z);

        void onSwitchRecordMode(boolean z);

        void onTextChanged(CharSequence charSequence, boolean z);

        void onWindowSizeChanged(int i);
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$1 */
    class C43401 implements Runnable {
        C43401() {
        }

        public void run() {
            if (ChatActivityEnterView.this.messageEditText != null && ChatActivityEnterView.this.waitingForKeyboardOpen && !ChatActivityEnterView.this.keyboardVisible && !AndroidUtilities.usingHardwareInput && !AndroidUtilities.isInMultiwindow) {
                ChatActivityEnterView.this.messageEditText.requestFocus();
                AndroidUtilities.showKeyboard(ChatActivityEnterView.this.messageEditText);
                AndroidUtilities.cancelRunOnUIThread(ChatActivityEnterView.this.openKeyboardRunnable);
                AndroidUtilities.runOnUIThread(ChatActivityEnterView.this.openKeyboardRunnable, 100);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$2 */
    class C43412 implements Runnable {
        private int lastKnownPage = -1;

        C43412() {
        }

        public void run() {
            if (ChatActivityEnterView.this.emojiView != null) {
                int currentPage = ChatActivityEnterView.this.emojiView.getCurrentPage();
                if (currentPage != this.lastKnownPage) {
                    this.lastKnownPage = currentPage;
                    boolean access$600 = ChatActivityEnterView.this.stickersTabOpen;
                    ChatActivityEnterView chatActivityEnterView = ChatActivityEnterView.this;
                    boolean z = currentPage == 1 || currentPage == 2;
                    chatActivityEnterView.stickersTabOpen = z;
                    if (access$600 != ChatActivityEnterView.this.stickersTabOpen) {
                        ChatActivityEnterView.this.checkSendButton(true);
                    }
                    if (!ChatActivityEnterView.this.stickersTabOpen && ChatActivityEnterView.this.stickersExpanded) {
                        ChatActivityEnterView.this.setStickersExpanded(false, true);
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$4 */
    class C43454 implements Runnable {
        C43454() {
        }

        public void run() {
            if (ChatActivityEnterView.this.delegate != null && ChatActivityEnterView.this.parentActivity != null) {
                ChatActivityEnterView.this.delegate.onPreAudioVideoRecord();
                ChatActivityEnterView.this.calledRecordRunnable = true;
                ChatActivityEnterView.this.recordAudioVideoRunnableStarted = false;
                ChatActivityEnterView.this.recordCircle.setLockTranslation(10000.0f);
                ChatActivityEnterView.this.recordSendText.setAlpha(BitmapDescriptorFactory.HUE_RED);
                ChatActivityEnterView.this.slideText.setAlpha(1.0f);
                ChatActivityEnterView.this.slideText.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                if (ChatActivityEnterView.this.videoSendButton == null || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        if (VERSION.SDK_INT < 23 || ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                            String str;
                            if (((int) ChatActivityEnterView.this.dialog_id) < 0) {
                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) ChatActivityEnterView.this.dialog_id)));
                                str = (chat == null || chat.participants_count <= MessagesController.getInstance().groupBigSize) ? "chat_upload_audio" : "bigchat_upload_audio";
                            } else {
                                str = "pm_upload_audio";
                            }
                            if (!MessagesController.isFeatureEnabled(str, ChatActivityEnterView.this.parentFragment)) {
                                return;
                            }
                        }
                        ChatActivityEnterView.this.parentActivity.requestPermissions(new String[]{"android.permission.RECORD_AUDIO"}, 3);
                        return;
                    }
                    ChatActivityEnterView.this.delegate.needStartRecordAudio(1);
                    ChatActivityEnterView.this.startedDraggingX = -1.0f;
                    MediaController.getInstance().startRecording(ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject);
                    ChatActivityEnterView.this.updateRecordIntefrace();
                    ChatActivityEnterView.this.audioVideoButtonContainer.getParent().requestDisallowInterceptTouchEvent(true);
                    return;
                }
                if (VERSION.SDK_INT >= 23) {
                    boolean z = ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.RECORD_AUDIO") == 0;
                    boolean z2 = ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.CAMERA") == 0;
                    if (!(z && z2)) {
                        int i = (z || z2) ? 1 : 2;
                        String[] strArr = new String[i];
                        if (!z && !z2) {
                            strArr[0] = "android.permission.RECORD_AUDIO";
                            strArr[1] = "android.permission.CAMERA";
                        } else if (z) {
                            strArr[0] = "android.permission.CAMERA";
                        } else {
                            strArr[0] = "android.permission.RECORD_AUDIO";
                        }
                        ChatActivityEnterView.this.parentActivity.requestPermissions(strArr, 3);
                        return;
                    }
                }
                ChatActivityEnterView.this.delegate.needStartRecordVideo(0);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$6 */
    class C43476 implements OnClickListener {
        C43476() {
        }

        public void onClick(View view) {
            boolean z = true;
            if (ChatActivityEnterView.this.isPopupShowing() && ChatActivityEnterView.this.currentPopupContentType == 0) {
                ChatActivityEnterView.this.openKeyboardInternal();
                ChatActivityEnterView.this.removeGifFromInputField();
                return;
            }
            ChatActivityEnterView.this.showPopup(1, 0);
            EmojiView access$500 = ChatActivityEnterView.this.emojiView;
            if (ChatActivityEnterView.this.messageEditText.length() <= 0 || ChatActivityEnterView.this.messageEditText.getText().toString().startsWith("@gif")) {
                z = false;
            }
            access$500.onOpen(z);
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$8 */
    class C43508 implements OnKeyListener {
        boolean ctrlPressed = false;

        C43508() {
        }

        public boolean onKey(View view, int i, KeyEvent keyEvent) {
            boolean z = false;
            if (i == 4 && !ChatActivityEnterView.this.keyboardVisible && ChatActivityEnterView.this.isPopupShowing()) {
                if (keyEvent.getAction() != 1) {
                    return true;
                }
                if (ChatActivityEnterView.this.currentPopupContentType == 1 && ChatActivityEnterView.this.botButtonsMessageObject != null) {
                    ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("hidekeyboard_" + ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.botButtonsMessageObject.getId()).commit();
                }
                ChatActivityEnterView.this.showPopup(0, 0);
                ChatActivityEnterView.this.removeGifFromInputField();
                return true;
            } else if (i == 66 && ((this.ctrlPressed || ChatActivityEnterView.this.sendByEnter) && keyEvent.getAction() == 0 && ChatActivityEnterView.this.editingMessageObject == null)) {
                ChatActivityEnterView.this.sendMessage();
                return true;
            } else if (i != 113 && i != 114) {
                return false;
            } else {
                if (keyEvent.getAction() == 0) {
                    z = true;
                }
                this.ctrlPressed = z;
                return true;
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$9 */
    class C43519 implements OnEditorActionListener {
        boolean ctrlPressed = false;

        C43519() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            boolean z = false;
            if (i == 4) {
                ChatActivityEnterView.this.sendMessage();
                return true;
            }
            if (keyEvent != null && i == 0) {
                if ((this.ctrlPressed || ChatActivityEnterView.this.sendByEnter) && keyEvent.getAction() == 0 && ChatActivityEnterView.this.editingMessageObject == null) {
                    ChatActivityEnterView.this.sendMessage();
                    return true;
                } else if (i == 113 || i == 114) {
                    if (keyEvent.getAction() == 0) {
                        z = true;
                    }
                    this.ctrlPressed = z;
                    return true;
                }
            }
            return false;
        }
    }

    private class AnimatedArrowDrawable extends Drawable {
        private float animProgress = BitmapDescriptorFactory.HUE_RED;
        private Paint paint = new Paint(1);
        private Path path = new Path();

        public AnimatedArrowDrawable(int i) {
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            this.paint.setColor(i);
            updatePath();
        }

        private void updatePath() {
            this.path.reset();
            float f = (this.animProgress * 2.0f) - 1.0f;
            this.path.moveTo((float) AndroidUtilities.dp(3.0f), ((float) AndroidUtilities.dp(12.0f)) - (((float) AndroidUtilities.dp(4.0f)) * f));
            this.path.lineTo((float) AndroidUtilities.dp(13.0f), ((float) AndroidUtilities.dp(12.0f)) + (((float) AndroidUtilities.dp(4.0f)) * f));
            this.path.lineTo((float) AndroidUtilities.dp(23.0f), ((float) AndroidUtilities.dp(12.0f)) - (f * ((float) AndroidUtilities.dp(4.0f))));
        }

        public void draw(Canvas canvas) {
            canvas.drawPath(this.path, this.paint);
        }

        public float getAnimationProgress() {
            return this.animProgress;
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(26.0f);
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(26.0f);
        }

        public int getOpacity() {
            return 0;
        }

        public void setAlpha(int i) {
        }

        public void setAnimationProgress(float f) {
            this.animProgress = f;
            updatePath();
            invalidateSelf();
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    private class RecordCircle extends View {
        private float amplitude;
        private float animateAmplitudeDiff;
        private float animateToAmplitude;
        private long lastUpdateTime;
        private float lockAnimatedTranslation;
        private boolean pressed;
        private float scale;
        private boolean sendButtonVisible;
        private float startTranslation;

        public RecordCircle(Context context) {
            super(context);
            ChatActivityEnterView.this.paint.setColor(Theme.getColor(Theme.key_chat_messagePanelVoiceBackground));
            ChatActivityEnterView.this.paintRecord.setColor(Theme.getColor(Theme.key_chat_messagePanelVoiceShadow));
            ChatActivityEnterView.this.lockDrawable = getResources().getDrawable(R.drawable.lock_middle);
            ChatActivityEnterView.this.lockDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceLock), Mode.MULTIPLY));
            ChatActivityEnterView.this.lockTopDrawable = getResources().getDrawable(R.drawable.lock_top);
            ChatActivityEnterView.this.lockTopDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceLock), Mode.MULTIPLY));
            ChatActivityEnterView.this.lockArrowDrawable = getResources().getDrawable(R.drawable.lock_arrow);
            ChatActivityEnterView.this.lockArrowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceLock), Mode.MULTIPLY));
            ChatActivityEnterView.this.lockBackgroundDrawable = getResources().getDrawable(R.drawable.lock_round);
            ChatActivityEnterView.this.lockBackgroundDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceLockBackground), Mode.MULTIPLY));
            ChatActivityEnterView.this.lockShadowDrawable = getResources().getDrawable(R.drawable.lock_round_shadow);
            ChatActivityEnterView.this.lockShadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceLockShadow), Mode.MULTIPLY));
            ChatActivityEnterView.this.micDrawable = getResources().getDrawable(R.drawable.mic).mutate();
            ChatActivityEnterView.this.micDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoicePressed), Mode.MULTIPLY));
            ChatActivityEnterView.this.cameraDrawable = getResources().getDrawable(R.drawable.ic_msg_panel_video).mutate();
            ChatActivityEnterView.this.cameraDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoicePressed), Mode.MULTIPLY));
            ChatActivityEnterView.this.sendDrawable = getResources().getDrawable(R.drawable.ic_send).mutate();
            ChatActivityEnterView.this.sendDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoicePressed), Mode.MULTIPLY));
        }

        public float getLockAnimatedTranslation() {
            return this.lockAnimatedTranslation;
        }

        public float getScale() {
            return this.scale;
        }

        public boolean isSendButtonVisible() {
            return this.sendButtonVisible;
        }

        protected void onDraw(Canvas canvas) {
            float max;
            float f;
            float f2;
            int dp;
            int dp2;
            int dp3;
            int dp4;
            int measuredWidth = getMeasuredWidth() / 2;
            int dp5 = AndroidUtilities.dp(170.0f);
            if (this.lockAnimatedTranslation != 10000.0f) {
                max = (float) Math.max(0, (int) (this.startTranslation - this.lockAnimatedTranslation));
                if (max > ((float) AndroidUtilities.dp(57.0f))) {
                    max = (float) AndroidUtilities.dp(57.0f);
                }
            } else {
                max = BitmapDescriptorFactory.HUE_RED;
            }
            int i = (int) (((float) dp5) - max);
            if (this.scale <= 0.5f) {
                f = this.scale / 0.5f;
                f2 = f;
            } else if (this.scale <= 0.75f) {
                f2 = 1.0f - (((this.scale - 0.5f) / 0.25f) * 0.1f);
                f = 1.0f;
            } else {
                f2 = 0.9f + (((this.scale - 0.75f) / 0.25f) * 0.1f);
                f = 1.0f;
            }
            long currentTimeMillis = System.currentTimeMillis() - this.lastUpdateTime;
            if (this.animateToAmplitude != this.amplitude) {
                this.amplitude += ((float) currentTimeMillis) * this.animateAmplitudeDiff;
                if (this.animateAmplitudeDiff > BitmapDescriptorFactory.HUE_RED) {
                    if (this.amplitude > this.animateToAmplitude) {
                        this.amplitude = this.animateToAmplitude;
                    }
                } else if (this.amplitude < this.animateToAmplitude) {
                    this.amplitude = this.animateToAmplitude;
                }
                invalidate();
            }
            this.lastUpdateTime = System.currentTimeMillis();
            if (this.amplitude != BitmapDescriptorFactory.HUE_RED) {
                canvas.drawCircle(((float) getMeasuredWidth()) / 2.0f, (float) i, (((float) AndroidUtilities.dp(42.0f)) + (((float) AndroidUtilities.dp(20.0f)) * this.amplitude)) * this.scale, ChatActivityEnterView.this.paintRecord);
            }
            canvas.drawCircle(((float) getMeasuredWidth()) / 2.0f, (float) i, ((float) AndroidUtilities.dp(42.0f)) * f2, ChatActivityEnterView.this.paint);
            Drawable access$3400 = isSendButtonVisible() ? ChatActivityEnterView.this.sendDrawable : (ChatActivityEnterView.this.videoSendButton == null || ChatActivityEnterView.this.videoSendButton.getTag() == null) ? ChatActivityEnterView.this.micDrawable : ChatActivityEnterView.this.cameraDrawable;
            access$3400.setBounds(measuredWidth - (access$3400.getIntrinsicWidth() / 2), i - (access$3400.getIntrinsicHeight() / 2), (access$3400.getIntrinsicWidth() / 2) + measuredWidth, i + (access$3400.getIntrinsicHeight() / 2));
            access$3400.setAlpha((int) (255.0f * f));
            access$3400.draw(canvas);
            float dp6 = 1.0f - (max / ((float) AndroidUtilities.dp(57.0f)));
            float max2 = Math.max(BitmapDescriptorFactory.HUE_RED, 1.0f - ((max / ((float) AndroidUtilities.dp(57.0f))) * 2.0f));
            int i2 = (int) (255.0f * f);
            if (isSendButtonVisible()) {
                i = AndroidUtilities.dp(31.0f);
                dp = AndroidUtilities.dp(57.0f) + ((int) (((((float) AndroidUtilities.dp(30.0f)) * (1.0f - f2)) - max) + (((float) AndroidUtilities.dp(20.0f)) * dp6)));
                dp2 = dp + AndroidUtilities.dp(5.0f);
                dp5 = dp + AndroidUtilities.dp(11.0f);
                dp3 = AndroidUtilities.dp(25.0f) + dp;
                dp4 = (int) ((max / ((float) AndroidUtilities.dp(57.0f))) * ((float) i2));
                ChatActivityEnterView.this.lockBackgroundDrawable.setAlpha(255);
                ChatActivityEnterView.this.lockShadowDrawable.setAlpha(255);
                ChatActivityEnterView.this.lockTopDrawable.setAlpha(dp4);
                ChatActivityEnterView.this.lockDrawable.setAlpha(dp4);
                ChatActivityEnterView.this.lockArrowDrawable.setAlpha((int) (((float) dp4) * max2));
                dp4 = dp3;
                dp3 = dp5;
                dp5 = dp2;
                dp2 = dp;
                dp = i;
            } else {
                dp = AndroidUtilities.dp(31.0f) + ((int) (((float) AndroidUtilities.dp(29.0f)) * dp6));
                dp2 = (AndroidUtilities.dp(57.0f) + ((int) (((float) AndroidUtilities.dp(30.0f)) * (1.0f - f2)))) - ((int) max);
                dp5 = (AndroidUtilities.dp(5.0f) + dp2) + ((int) (((float) AndroidUtilities.dp(4.0f)) * dp6));
                dp3 = ((int) (((float) AndroidUtilities.dp(10.0f)) * dp6)) + (AndroidUtilities.dp(11.0f) + dp2);
                dp4 = (AndroidUtilities.dp(25.0f) + dp2) + ((int) (((float) AndroidUtilities.dp(16.0f)) * dp6));
                ChatActivityEnterView.this.lockBackgroundDrawable.setAlpha(i2);
                ChatActivityEnterView.this.lockShadowDrawable.setAlpha(i2);
                ChatActivityEnterView.this.lockTopDrawable.setAlpha(i2);
                ChatActivityEnterView.this.lockDrawable.setAlpha(i2);
                ChatActivityEnterView.this.lockArrowDrawable.setAlpha((int) (((float) i2) * max2));
            }
            ChatActivityEnterView.this.lockBackgroundDrawable.setBounds(measuredWidth - AndroidUtilities.dp(15.0f), dp2, AndroidUtilities.dp(15.0f) + measuredWidth, dp2 + dp);
            ChatActivityEnterView.this.lockBackgroundDrawable.draw(canvas);
            ChatActivityEnterView.this.lockShadowDrawable.setBounds(measuredWidth - AndroidUtilities.dp(16.0f), dp2 - AndroidUtilities.dp(1.0f), AndroidUtilities.dp(16.0f) + measuredWidth, (dp + dp2) + AndroidUtilities.dp(1.0f));
            ChatActivityEnterView.this.lockShadowDrawable.draw(canvas);
            ChatActivityEnterView.this.lockTopDrawable.setBounds(measuredWidth - AndroidUtilities.dp(6.0f), dp5, AndroidUtilities.dp(6.0f) + measuredWidth, AndroidUtilities.dp(14.0f) + dp5);
            ChatActivityEnterView.this.lockTopDrawable.draw(canvas);
            ChatActivityEnterView.this.lockDrawable.setBounds(measuredWidth - AndroidUtilities.dp(7.0f), dp3, AndroidUtilities.dp(7.0f) + measuredWidth, AndroidUtilities.dp(12.0f) + dp3);
            ChatActivityEnterView.this.lockDrawable.draw(canvas);
            ChatActivityEnterView.this.lockArrowDrawable.setBounds(measuredWidth - AndroidUtilities.dp(7.5f), dp4, AndroidUtilities.dp(7.5f) + measuredWidth, AndroidUtilities.dp(9.0f) + dp4);
            ChatActivityEnterView.this.lockArrowDrawable.draw(canvas);
            if (isSendButtonVisible()) {
                ChatActivityEnterView.this.redDotPaint.setAlpha(255);
                ChatActivityEnterView.this.rect.set((float) (measuredWidth - AndroidUtilities.dp2(6.5f)), (float) (AndroidUtilities.dp(9.0f) + dp2), (float) (AndroidUtilities.dp(6.5f) + measuredWidth), (float) (dp2 + AndroidUtilities.dp(22.0f)));
                canvas.drawRoundRect(ChatActivityEnterView.this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), ChatActivityEnterView.this.redDotPaint);
            }
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            if (this.sendButtonVisible) {
                int x = (int) motionEvent.getX();
                int y = (int) motionEvent.getY();
                if (motionEvent.getAction() == 0) {
                    boolean contains = ChatActivityEnterView.this.lockBackgroundDrawable.getBounds().contains(x, y);
                    this.pressed = contains;
                    if (contains) {
                        return true;
                    }
                } else if (this.pressed) {
                    if (motionEvent.getAction() != 1 || !ChatActivityEnterView.this.lockBackgroundDrawable.getBounds().contains(x, y)) {
                        return true;
                    }
                    if (ChatActivityEnterView.this.videoSendButton == null || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                        MediaController.getInstance().stopRecording(2);
                        ChatActivityEnterView.this.delegate.needStartRecordAudio(0);
                        return true;
                    }
                    ChatActivityEnterView.this.delegate.needStartRecordVideo(3);
                    return true;
                }
            }
            return false;
        }

        public void setAmplitude(double d) {
            this.animateToAmplitude = ((float) Math.min(100.0d, d)) / 100.0f;
            this.animateAmplitudeDiff = (this.animateToAmplitude - this.amplitude) / 150.0f;
            this.lastUpdateTime = System.currentTimeMillis();
            invalidate();
        }

        public void setLockAnimatedTranslation(float f) {
            this.lockAnimatedTranslation = f;
            invalidate();
        }

        public int setLockTranslation(float f) {
            if (f == 10000.0f) {
                this.sendButtonVisible = false;
                this.lockAnimatedTranslation = -1.0f;
                this.startTranslation = -1.0f;
                invalidate();
                return 0;
            } else if (this.sendButtonVisible) {
                return 2;
            } else {
                if (this.lockAnimatedTranslation == -1.0f) {
                    this.startTranslation = f;
                }
                this.lockAnimatedTranslation = f;
                invalidate();
                if (this.startTranslation - this.lockAnimatedTranslation < ((float) AndroidUtilities.dp(57.0f))) {
                    return 1;
                }
                this.sendButtonVisible = true;
                return 2;
            }
        }

        public void setScale(float f) {
            this.scale = f;
            invalidate();
        }

        public void setSendButtonInvisible() {
            this.sendButtonVisible = false;
            invalidate();
        }
    }

    private class RecordDot extends View {
        private float alpha;
        private boolean isIncr;
        private long lastUpdateTime;

        public RecordDot(Context context) {
            super(context);
            ChatActivityEnterView.this.redDotPaint.setColor(Theme.getColor(Theme.key_chat_recordedVoiceDot));
        }

        protected void onDraw(Canvas canvas) {
            ChatActivityEnterView.this.redDotPaint.setAlpha((int) (255.0f * this.alpha));
            long currentTimeMillis = System.currentTimeMillis() - this.lastUpdateTime;
            if (this.isIncr) {
                this.alpha = (((float) currentTimeMillis) / 400.0f) + this.alpha;
                if (this.alpha >= 1.0f) {
                    this.alpha = 1.0f;
                    this.isIncr = false;
                }
            } else {
                this.alpha -= ((float) currentTimeMillis) / 400.0f;
                if (this.alpha <= BitmapDescriptorFactory.HUE_RED) {
                    this.alpha = BitmapDescriptorFactory.HUE_RED;
                    this.isIncr = true;
                }
            }
            this.lastUpdateTime = System.currentTimeMillis();
            canvas.drawCircle((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), ChatActivityEnterView.this.redDotPaint);
            invalidate();
        }

        public void resetAlpha() {
            this.alpha = 1.0f;
            this.lastUpdateTime = System.currentTimeMillis();
            this.isIncr = false;
            invalidate();
        }
    }

    private class ScrimDrawable extends Drawable {
        private Paint paint = new Paint();

        public ScrimDrawable() {
            this.paint.setColor(0);
        }

        public void draw(Canvas canvas) {
            this.paint.setAlpha(Math.round(102.0f * ChatActivityEnterView.this.stickersExpansionProgress));
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) ChatActivityEnterView.this.getWidth(), (ChatActivityEnterView.this.emojiView.getY() - ((float) ChatActivityEnterView.this.getHeight())) + ((float) Theme.chat_composeShadowDrawable.getIntrinsicHeight()), this.paint);
        }

        public int getOpacity() {
            return 0;
        }

        public void setAlpha(int i) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    private class SeekBarWaveformView extends View {
        private SeekBarWaveform seekBarWaveform;

        public SeekBarWaveformView(Context context) {
            super(context);
            this.seekBarWaveform = new SeekBarWaveform(context);
            this.seekBarWaveform.setDelegate(new SeekBarDelegate(ChatActivityEnterView.this) {
                public void onSeekBarDrag(float f) {
                    if (ChatActivityEnterView.this.audioToSendMessageObject != null) {
                        ChatActivityEnterView.this.audioToSendMessageObject.audioProgress = f;
                        MediaController.getInstance().seekToProgress(ChatActivityEnterView.this.audioToSendMessageObject, f);
                    }
                }
            });
        }

        public boolean isDragging() {
            return this.seekBarWaveform.isDragging();
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.seekBarWaveform.setColors(Theme.getColor(Theme.key_chat_recordedVoiceProgress), Theme.getColor(Theme.key_chat_recordedVoiceProgressInner), Theme.getColor(Theme.key_chat_recordedVoiceProgress));
            this.seekBarWaveform.draw(canvas);
        }

        protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            this.seekBarWaveform.setSize(i3 - i, i4 - i2);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean onTouch = this.seekBarWaveform.onTouch(motionEvent.getAction(), motionEvent.getX(), motionEvent.getY());
            if (onTouch) {
                if (motionEvent.getAction() == 0) {
                    ChatActivityEnterView.this.requestDisallowInterceptTouchEvent(true);
                }
                invalidate();
            }
            return onTouch || super.onTouchEvent(motionEvent);
        }

        public void setProgress(float f) {
            this.seekBarWaveform.setProgress(f);
            invalidate();
        }

        public void setWaveform(byte[] bArr) {
            this.seekBarWaveform.setWaveform(bArr);
            invalidate();
        }
    }

    public ChatActivityEnterView(Activity activity, SizeNotifierFrameLayout sizeNotifierFrameLayout, ChatActivity chatActivity, boolean z) {
        this(activity, sizeNotifierFrameLayout, chatActivity, z, 0);
    }

    public ChatActivityEnterView(Activity activity, SizeNotifierFrameLayout sizeNotifierFrameLayout, ChatActivity chatActivity, boolean z, long j) {
        super(activity);
        this.currentPopupContentType = -1;
        this.isPaused = true;
        this.startedDraggingX = -1.0f;
        this.distCanMove = (float) AndroidUtilities.dp(80.0f);
        this.messageWebPageSearch = true;
        this.openKeyboardRunnable = new C43401();
        this.updateExpandabilityRunnable = new C43412();
        this.roundedTranslationYProperty = new Property<View, Integer>(Integer.class, "translationY") {
            public Integer get(View view) {
                return Integer.valueOf(Math.round(view.getTranslationY()));
            }

            public void set(View view, Integer num) {
                view.setTranslationY((float) num.intValue());
            }
        };
        this.redDotPaint = new Paint(1);
        this.recordAudioVideoRunnable = new C43454();
        this.paint = new Paint(1);
        this.paintRecord = new Paint(1);
        this.rect = new RectF();
        this.dialogId = j;
        this.dotPaint = new Paint(1);
        this.dotPaint.setColor(Theme.getColor(Theme.key_chat_emojiPanelNewTrending));
        setFocusable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recordStarted);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recordStartError);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recordStopped);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.recordProgressChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.audioDidSent);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.audioRouteChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
        this.parentActivity = activity;
        this.parentFragment = chatActivity;
        this.sizeNotifierLayout = sizeNotifierFrameLayout;
        this.sizeNotifierLayout.setDelegate(this);
        this.sendByEnter = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("send_by_enter", false);
        this.textFieldContainer = new LinearLayout(activity);
        this.textFieldContainer.setOrientation(0);
        addView(this.textFieldContainer, LayoutHelper.createFrame(-1, -2.0f, 51, BitmapDescriptorFactory.HUE_RED, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        View frameLayout = new FrameLayout(activity);
        this.textFieldContainer.addView(frameLayout, LayoutHelper.createLinear(0, -2, 1.0f));
        this.emojiButton = new ImageView(activity) {
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                if (ChatActivityEnterView.this.attachLayout == null) {
                    return;
                }
                if ((ChatActivityEnterView.this.emojiView == null || ChatActivityEnterView.this.emojiView.getVisibility() != 0) && !StickersQuery.getUnreadStickerSets().isEmpty() && ChatActivityEnterView.this.dotPaint != null) {
                    canvas.drawCircle((float) ((canvas.getWidth() / 2) + AndroidUtilities.dp(9.0f)), (float) ((canvas.getHeight() / 2) - AndroidUtilities.dp(8.0f)), (float) AndroidUtilities.dp(5.0f), ChatActivityEnterView.this.dotPaint);
                }
            }
        };
        this.emojiButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
        this.emojiButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.emojiButton.setPadding(0, AndroidUtilities.dp(1.0f), 0, 0);
        setEmojiButtonImage();
        frameLayout.addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, 83, 3.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.emojiButton.setOnClickListener(new C43476());
        this.messageEditText = new EditTextCaption(activity) {

            /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$7$1 */
            class C43481 implements C0053d {
                C43481() {
                }

                public boolean onCommitContent(C0060e c0060e, int i, Bundle bundle) {
                    if (C0432c.b() && (C0054c.f117a & i) != 0) {
                        try {
                            c0060e.c();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    if (c0060e.b().hasMimeType("image/gif")) {
                        String str = null;
                        SendMessagesHelper.prepareSendingDocument(null, str, c0060e.a(), "image/gif", ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, c0060e);
                    } else {
                        SendMessagesHelper.prepareSendingPhoto(null, c0060e.a(), ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, null, null, c0060e, 0);
                    }
                    if (ChatActivityEnterView.this.delegate != null) {
                        ChatActivityEnterView.this.delegate.onMessageSend(null);
                    }
                    return true;
                }
            }

            public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                InputConnection onCreateInputConnection = super.onCreateInputConnection(editorInfo);
                C0044a.a(editorInfo, new String[]{"image/gif", "image/*", "image/jpg", "image/png"});
                return C0054c.a(onCreateInputConnection, editorInfo, new C43481());
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                boolean z = false;
                if (ChatActivityEnterView.this.isPopupShowing() && motionEvent.getAction() == 0) {
                    ChatActivityEnterView.this.showPopup(AndroidUtilities.usingHardwareInput ? z : 2, z);
                    ChatActivityEnterView.this.openKeyboardInternal();
                }
                try {
                    z = super.onTouchEvent(motionEvent);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                return z;
            }
        };
        updateFieldHint();
        this.messageEditText.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.messageEditText.setInputType((this.messageEditText.getInputType() | MessagesController.UPDATE_MASK_CHAT_ADMINS) | 131072);
        this.messageEditText.setTypeface(AndroidUtilities.getTypeface(TtmlNode.ANONYMOUS_REGION_ID));
        this.messageEditText.setSingleLine(false);
        this.messageEditText.setMaxLines(4);
        this.messageEditText.setTextSize(1, 18.0f);
        this.messageEditText.setGravity(80);
        this.messageEditText.setPadding(0, AndroidUtilities.dp(11.0f), 0, AndroidUtilities.dp(12.0f));
        this.messageEditText.setBackgroundDrawable(null);
        this.messageEditText.setTextColor(Theme.getColor(Theme.key_chat_messagePanelText));
        this.messageEditText.setHintColor(Theme.getColor(Theme.key_chat_messagePanelHint));
        this.messageEditText.setHintTextColor(Theme.getColor(Theme.key_chat_messagePanelHint));
        frameLayout.addView(this.messageEditText, LayoutHelper.createFrame(-1, -2.0f, 80, 52.0f, BitmapDescriptorFactory.HUE_RED, z ? 50.0f : 2.0f, BitmapDescriptorFactory.HUE_RED));
        this.messageEditText.setOnKeyListener(new C43508());
        this.messageEditText.setOnEditorActionListener(new C43519());
        this.messageEditText.addTextChangedListener(new TextWatcher() {
            boolean processChange = false;

            public void afterTextChanged(Editable editable) {
                if (ChatActivityEnterView.this.innerTextChange == 0) {
                    if (ChatActivityEnterView.this.sendByEnter && editable.length() > 0 && editable.charAt(editable.length() - 1) == '\n' && ChatActivityEnterView.this.editingMessageObject == null) {
                        ChatActivityEnterView.this.sendMessage();
                    }
                    if (this.processChange) {
                        ImageSpan[] imageSpanArr = (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class);
                        for (Object removeSpan : imageSpanArr) {
                            editable.removeSpan(removeSpan);
                        }
                        Emoji.replaceEmoji(editable, ChatActivityEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        this.processChange = false;
                    }
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                if (ChatActivityEnterView.this.innerTextChange != 1) {
                    ChatActivityEnterView.this.checkSendButton(true);
                    CharSequence trimmedString = AndroidUtilities.getTrimmedString(charSequence.toString());
                    if (!(ChatActivityEnterView.this.delegate == null || ChatActivityEnterView.this.ignoreTextChange)) {
                        if (i3 > 2 || charSequence == null || charSequence.length() == 0) {
                            ChatActivityEnterView.this.messageWebPageSearch = true;
                        }
                        ChatActivityEnterViewDelegate access$1000 = ChatActivityEnterView.this.delegate;
                        boolean z = i2 > i3 + 1 || i3 - i2 > 2;
                        access$1000.onTextChanged(charSequence, z);
                    }
                    if (!(ChatActivityEnterView.this.innerTextChange == 2 || i2 == i3 || i3 - i2 <= 1)) {
                        this.processChange = true;
                    }
                    if (ChatActivityEnterView.this.editingMessageObject == null && !ChatActivityEnterView.this.canWriteToChannel && trimmedString.length() != 0 && ChatActivityEnterView.this.lastTypingTimeSend < System.currentTimeMillis() - DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS && !ChatActivityEnterView.this.ignoreTextChange) {
                        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                        User user = null;
                        if (((int) ChatActivityEnterView.this.dialog_id) > 0) {
                            user = MessagesController.getInstance().getUser(Integer.valueOf((int) ChatActivityEnterView.this.dialog_id));
                        }
                        if (user != null) {
                            if (user.id == UserConfig.getClientUserId()) {
                                return;
                            }
                            if (!(user.status == null || user.status.expires >= currentTime || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(user.id)))) {
                                return;
                            }
                        }
                        ChatActivityEnterView.this.lastTypingTimeSend = System.currentTimeMillis();
                        if (ChatActivityEnterView.this.delegate != null) {
                            ChatActivityEnterView.this.delegate.needSendTyping();
                        }
                    }
                }
            }
        });
        if (z) {
            this.attachLayout = new LinearLayout(activity);
            this.attachLayout.setOrientation(0);
            this.attachLayout.setEnabled(false);
            this.attachLayout.setPivotX((float) AndroidUtilities.dp(48.0f));
            frameLayout.addView(this.attachLayout, LayoutHelper.createFrame(-2, 48, 85));
            this.botButton = new ImageView(activity);
            this.botButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.botButton.setImageResource(R.drawable.bot_keyboard2);
            this.botButton.setScaleType(ScaleType.CENTER);
            this.botButton.setVisibility(8);
            this.attachLayout.addView(this.botButton, LayoutHelper.createLinear(48, 48));
            this.botButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (ChatActivityEnterView.this.botReplyMarkup != null) {
                        if (ChatActivityEnterView.this.isPopupShowing() && ChatActivityEnterView.this.currentPopupContentType == 1) {
                            if (ChatActivityEnterView.this.currentPopupContentType == 1 && ChatActivityEnterView.this.botButtonsMessageObject != null) {
                                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("hidekeyboard_" + ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.botButtonsMessageObject.getId()).commit();
                            }
                            ChatActivityEnterView.this.openKeyboardInternal();
                            return;
                        }
                        ChatActivityEnterView.this.showPopup(1, 1);
                        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("hidekeyboard_" + ChatActivityEnterView.this.dialog_id).commit();
                    } else if (ChatActivityEnterView.this.hasBotCommands) {
                        ChatActivityEnterView.this.setFieldText("/");
                        ChatActivityEnterView.this.messageEditText.requestFocus();
                        ChatActivityEnterView.this.openKeyboard();
                    }
                }
            });
            this.notifyButton = new ImageView(activity);
            this.notifyButton.setImageResource(this.silent ? R.drawable.notify_members_off : R.drawable.notify_members_on);
            this.notifyButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.notifyButton.setScaleType(ScaleType.CENTER);
            this.notifyButton.setVisibility(this.canWriteToChannel ? 0 : 8);
            this.attachLayout.addView(this.notifyButton, LayoutHelper.createLinear(48, 48));
            this.notifyButton.setOnClickListener(new OnClickListener() {
                private Toast visibleToast;

                public void onClick(View view) {
                    ChatActivityEnterView.this.silent = !ChatActivityEnterView.this.silent;
                    ChatActivityEnterView.this.notifyButton.setImageResource(ChatActivityEnterView.this.silent ? R.drawable.notify_members_off : R.drawable.notify_members_on);
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("silent_" + ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.silent).commit();
                    NotificationsController.updateServerNotificationsSettings(ChatActivityEnterView.this.dialog_id);
                    try {
                        if (this.visibleToast != null) {
                            this.visibleToast.cancel();
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    if (ChatActivityEnterView.this.silent) {
                        this.visibleToast = Toast.makeText(ChatActivityEnterView.this.parentActivity, LocaleController.getString("ChannelNotifyMembersInfoOff", R.string.ChannelNotifyMembersInfoOff), 0);
                    } else {
                        this.visibleToast = Toast.makeText(ChatActivityEnterView.this.parentActivity, LocaleController.getString("ChannelNotifyMembersInfoOn", R.string.ChannelNotifyMembersInfoOn), 0);
                    }
                    this.visibleToast.show();
                    ChatActivityEnterView.this.updateFieldHint();
                }
            });
            this.attachButton = new ImageView(activity);
            this.attachButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.attachButton.setImageResource(R.drawable.ic_ab_attach);
            this.attachButton.setScaleType(ScaleType.CENTER);
            this.attachLayout.addView(this.attachButton, LayoutHelper.createLinear(48, 48));
            this.attachButton.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    ChatActivityEnterView.this.delegate.didPressedAttachButton();
                }
            });
            Object obj = null;
            try {
                User user = MessagesController.getInstance().getUser(Integer.valueOf((int) this.dialogId));
                if (!(user == null || user.bot)) {
                    obj = 1;
                }
                if (C3791b.f() && z && r2 != null) {
                    this.paintingButton = new ImageView(activity);
                    this.paintingButton.setImageResource(R.drawable.ic_ab_payment);
                    this.paintingButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
                    this.paintingButton.setScaleType(ScaleType.CENTER);
                    this.paintingButton.setVisibility(0);
                    this.attachLayout.addView(this.paintingButton, LayoutHelper.createLinear(48, 48));
                    this.paintingButton.setOnClickListener(new OnClickListener() {
                        public void onClick(View view) {
                            ChatActivityEnterView.this.checkUserStatusRegistry();
                            ChatActivityEnterView.this.showLoadingDialog(ApplicationLoader.applicationContext, LocaleController.getString("HotgramPayment", R.string.HotgramPayment), "لطفا منتظر بمانید");
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.recordedAudioPanel = new FrameLayout(activity);
        this.recordedAudioPanel.setVisibility(this.audioToSend == null ? 8 : 0);
        this.recordedAudioPanel.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.recordedAudioPanel.setFocusable(true);
        this.recordedAudioPanel.setFocusableInTouchMode(true);
        this.recordedAudioPanel.setClickable(true);
        frameLayout.addView(this.recordedAudioPanel, LayoutHelper.createFrame(-1, 48, 80));
        this.recordDeleteImageView = new ImageView(activity);
        this.recordDeleteImageView.setScaleType(ScaleType.CENTER);
        this.recordDeleteImageView.setImageResource(R.drawable.ic_ab_delete);
        this.recordDeleteImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceDelete), Mode.MULTIPLY));
        this.recordedAudioPanel.addView(this.recordDeleteImageView, LayoutHelper.createFrame(48, 48.0f));
        this.recordDeleteImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.delegate.needStartRecordVideo(2);
                } else {
                    MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                    if (playingMessageObject != null && playingMessageObject == ChatActivityEnterView.this.audioToSendMessageObject) {
                        MediaController.getInstance().cleanupPlayer(true, true);
                    }
                }
                if (ChatActivityEnterView.this.audioToSendPath != null) {
                    new File(ChatActivityEnterView.this.audioToSendPath).delete();
                }
                ChatActivityEnterView.this.hideRecordedAudioPanel();
                ChatActivityEnterView.this.checkSendButton(true);
            }
        });
        this.videoTimelineView = new VideoTimelineView(activity);
        this.videoTimelineView.setColor(-11817481);
        this.videoTimelineView.setRoundFrames(true);
        this.videoTimelineView.setDelegate(new VideoTimelineViewDelegate() {
            public void didStartDragging() {
                ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(1, BitmapDescriptorFactory.HUE_RED);
            }

            public void didStopDragging() {
                ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(0, BitmapDescriptorFactory.HUE_RED);
            }

            public void onLeftProgressChanged(float f) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.videoToSendMessageObject.startTime = (long) (((float) ChatActivityEnterView.this.videoToSendMessageObject.estimatedDuration) * f);
                    ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(2, f);
                }
            }

            public void onRightProgressChanged(float f) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.videoToSendMessageObject.endTime = (long) (((float) ChatActivityEnterView.this.videoToSendMessageObject.estimatedDuration) * f);
                    ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(2, f);
                }
            }
        });
        this.recordedAudioPanel.addView(this.videoTimelineView, LayoutHelper.createFrame(-1, 32.0f, 19, 40.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.recordedAudioBackground = new View(activity);
        this.recordedAudioBackground.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(16.0f), Theme.getColor(Theme.key_chat_recordedVoiceBackground)));
        this.recordedAudioPanel.addView(this.recordedAudioBackground, LayoutHelper.createFrame(-1, 36.0f, 19, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.recordedAudioSeekBar = new SeekBarWaveformView(activity);
        this.recordedAudioPanel.addView(this.recordedAudioSeekBar, LayoutHelper.createFrame(-1, 32.0f, 19, 92.0f, BitmapDescriptorFactory.HUE_RED, 52.0f, BitmapDescriptorFactory.HUE_RED));
        this.playDrawable = Theme.createSimpleSelectorDrawable(activity, R.drawable.s_play, Theme.getColor(Theme.key_chat_recordedVoicePlayPause), Theme.getColor(Theme.key_chat_recordedVoicePlayPausePressed));
        this.pauseDrawable = Theme.createSimpleSelectorDrawable(activity, R.drawable.s_pause, Theme.getColor(Theme.key_chat_recordedVoicePlayPause), Theme.getColor(Theme.key_chat_recordedVoicePlayPausePressed));
        this.recordedAudioPlayButton = new ImageView(activity);
        this.recordedAudioPlayButton.setImageDrawable(this.playDrawable);
        this.recordedAudioPlayButton.setScaleType(ScaleType.CENTER);
        this.recordedAudioPanel.addView(this.recordedAudioPlayButton, LayoutHelper.createFrame(48, 48.0f, 83, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.recordedAudioPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ChatActivityEnterView.this.audioToSend != null) {
                    if (!MediaController.getInstance().isPlayingMessage(ChatActivityEnterView.this.audioToSendMessageObject) || MediaController.getInstance().isMessagePaused()) {
                        ChatActivityEnterView.this.recordedAudioPlayButton.setImageDrawable(ChatActivityEnterView.this.pauseDrawable);
                        MediaController.getInstance().playMessage(ChatActivityEnterView.this.audioToSendMessageObject);
                        return;
                    }
                    MediaController.getInstance().pauseMessage(ChatActivityEnterView.this.audioToSendMessageObject);
                    ChatActivityEnterView.this.recordedAudioPlayButton.setImageDrawable(ChatActivityEnterView.this.playDrawable);
                }
            }
        });
        this.recordedAudioTimeTextView = new TextView(activity);
        this.recordedAudioTimeTextView.setTextColor(Theme.getColor(Theme.key_chat_messagePanelVoiceDuration));
        this.recordedAudioTimeTextView.setTextSize(1, 13.0f);
        this.recordedAudioPanel.addView(this.recordedAudioTimeTextView, LayoutHelper.createFrame(-2, -2.0f, 21, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 13.0f, BitmapDescriptorFactory.HUE_RED));
        this.recordPanel = new FrameLayout(activity);
        this.recordPanel.setVisibility(8);
        this.recordPanel.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        frameLayout.addView(this.recordPanel, LayoutHelper.createFrame(-1, 48, 80));
        this.recordPanel.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        this.slideText = new LinearLayout(activity);
        this.slideText.setOrientation(0);
        this.recordPanel.addView(this.slideText, LayoutHelper.createFrame(-2, -2.0f, 17, 30.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.recordCancelImage = new ImageView(activity);
        this.recordCancelImage.setImageResource(R.drawable.slidearrow);
        this.recordCancelImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_recordVoiceCancel), Mode.MULTIPLY));
        this.slideText.addView(this.recordCancelImage, LayoutHelper.createLinear(-2, -2, 16, 0, 1, 0, 0));
        this.recordCancelText = new TextView(activity);
        this.recordCancelText.setText(LocaleController.getString("SlideToCancel", R.string.SlideToCancel));
        this.recordCancelText.setTextColor(Theme.getColor(Theme.key_chat_recordVoiceCancel));
        this.recordCancelText.setTextSize(1, 12.0f);
        this.slideText.addView(this.recordCancelText, LayoutHelper.createLinear(-2, -2, 16, 6, 0, 0, 0));
        this.recordSendText = new TextView(activity);
        this.recordSendText.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        this.recordSendText.setTextColor(Theme.getColor(Theme.key_chat_fieldOverlayText));
        this.recordSendText.setTextSize(1, 16.0f);
        this.recordSendText.setGravity(17);
        this.recordSendText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.recordSendText.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.recordSendText.setPadding(AndroidUtilities.dp(36.0f), 0, 0, 0);
        this.recordSendText.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                    ChatActivityEnterView.this.delegate.needStartRecordAudio(0);
                    MediaController.getInstance().stopRecording(0);
                } else {
                    ChatActivityEnterView.this.delegate.needStartRecordVideo(2);
                }
                ChatActivityEnterView.this.recordingAudioVideo = false;
                ChatActivityEnterView.this.updateRecordIntefrace();
            }
        });
        this.recordPanel.addView(this.recordSendText, LayoutHelper.createFrame(-2, -1.0f, 49, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.recordTimeContainer = new LinearLayout(activity);
        this.recordTimeContainer.setOrientation(0);
        this.recordTimeContainer.setPadding(AndroidUtilities.dp(13.0f), 0, 0, 0);
        this.recordTimeContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.recordPanel.addView(this.recordTimeContainer, LayoutHelper.createFrame(-2, -2, 16));
        this.recordDot = new RecordDot(activity);
        this.recordTimeContainer.addView(this.recordDot, LayoutHelper.createLinear(11, 11, 16, 0, 1, 0, 0));
        this.recordTimeText = new TextView(activity);
        this.recordTimeText.setTextColor(Theme.getColor(Theme.key_chat_recordTime));
        this.recordTimeText.setTextSize(1, 16.0f);
        this.recordTimeContainer.addView(this.recordTimeText, LayoutHelper.createLinear(-2, -2, 16, 6, 0, 0, 0));
        this.sendButtonContainer = new FrameLayout(activity);
        this.textFieldContainer.addView(this.sendButtonContainer, LayoutHelper.createLinear(48, 48, 80));
        this.audioVideoButtonContainer = new FrameLayout(activity);
        this.audioVideoButtonContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.audioVideoButtonContainer.setSoundEffectsEnabled(false);
        this.sendButtonContainer.addView(this.audioVideoButtonContainer, LayoutHelper.createFrame(48, 48.0f));
        this.audioVideoButtonContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                boolean z = false;
                if (motionEvent.getAction() == 0) {
                    if (!ChatActivityEnterView.this.recordCircle.isSendButtonVisible()) {
                        if (ChatActivityEnterView.this.parentFragment != null) {
                            Chat currentChat = ChatActivityEnterView.this.parentFragment.getCurrentChat();
                            if (ChatObject.isChannel(currentChat) && currentChat.banned_rights != null && currentChat.banned_rights.send_media) {
                                ChatActivityEnterView.this.delegate.needShowMediaBanHint();
                                return false;
                            }
                        }
                        if (ChatActivityEnterView.this.hasRecordVideo) {
                            ChatActivityEnterView.this.calledRecordRunnable = false;
                            ChatActivityEnterView.this.recordAudioVideoRunnableStarted = true;
                            AndroidUtilities.runOnUIThread(ChatActivityEnterView.this.recordAudioVideoRunnable, 150);
                        } else {
                            ChatActivityEnterView.this.recordAudioVideoRunnable.run();
                        }
                    } else if (ChatActivityEnterView.this.hasRecordVideo && !ChatActivityEnterView.this.calledRecordRunnable) {
                        return false;
                    } else {
                        ChatActivityEnterView.this.startedDraggingX = -1.0f;
                        if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                            ChatActivityEnterView.this.delegate.needStartRecordAudio(0);
                            MediaController.getInstance().stopRecording(1);
                        } else {
                            ChatActivityEnterView.this.delegate.needStartRecordVideo(1);
                        }
                        ChatActivityEnterView.this.recordingAudioVideo = false;
                        ChatActivityEnterView.this.updateRecordIntefrace();
                        return false;
                    }
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    if (ChatActivityEnterView.this.recordCircle.isSendButtonVisible() || ChatActivityEnterView.this.recordedAudioPanel.getVisibility() == 0) {
                        return false;
                    }
                    if (ChatActivityEnterView.this.recordAudioVideoRunnableStarted) {
                        AndroidUtilities.cancelRunOnUIThread(ChatActivityEnterView.this.recordAudioVideoRunnable);
                        ChatActivityEnterView.this.delegate.onSwitchRecordMode(ChatActivityEnterView.this.videoSendButton.getTag() == null);
                        ChatActivityEnterView chatActivityEnterView = ChatActivityEnterView.this;
                        if (ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                            z = true;
                        }
                        chatActivityEnterView.setRecordVideoButtonVisible(z, true);
                    } else if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.calledRecordRunnable) {
                        ChatActivityEnterView.this.startedDraggingX = -1.0f;
                        if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                            ChatActivityEnterView.this.delegate.needStartRecordAudio(0);
                            MediaController.getInstance().stopRecording(1);
                        } else {
                            ChatActivityEnterView.this.delegate.needStartRecordVideo(1);
                        }
                        ChatActivityEnterView.this.recordingAudioVideo = false;
                        ChatActivityEnterView.this.updateRecordIntefrace();
                    }
                } else if (motionEvent.getAction() == 2 && ChatActivityEnterView.this.recordingAudioVideo) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (ChatActivityEnterView.this.recordCircle.isSendButtonVisible()) {
                        return false;
                    }
                    if (ChatActivityEnterView.this.recordCircle.setLockTranslation(y) == 2) {
                        AnimatorSet animatorSet = new AnimatorSet();
                        r5 = new Animator[5];
                        r5[0] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordCircle, "lockAnimatedTranslation", new float[]{ChatActivityEnterView.this.recordCircle.startTranslation});
                        r5[1] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.slideText, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                        r5[2] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.slideText, "translationY", new float[]{(float) AndroidUtilities.dp(20.0f)});
                        r5[3] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordSendText, "alpha", new float[]{1.0f});
                        r5[4] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordSendText, "translationY", new float[]{(float) (-AndroidUtilities.dp(20.0f)), BitmapDescriptorFactory.HUE_RED});
                        animatorSet.playTogether(r5);
                        animatorSet.setInterpolator(new DecelerateInterpolator());
                        animatorSet.setDuration(150);
                        animatorSet.start();
                        return false;
                    }
                    if (x < (-ChatActivityEnterView.this.distCanMove)) {
                        if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                            ChatActivityEnterView.this.delegate.needStartRecordAudio(0);
                            MediaController.getInstance().stopRecording(0);
                        } else {
                            ChatActivityEnterView.this.delegate.needStartRecordVideo(2);
                        }
                        ChatActivityEnterView.this.recordingAudioVideo = false;
                        ChatActivityEnterView.this.updateRecordIntefrace();
                    }
                    y = x + ChatActivityEnterView.this.audioVideoButtonContainer.getX();
                    LayoutParams layoutParams = (LayoutParams) ChatActivityEnterView.this.slideText.getLayoutParams();
                    if (ChatActivityEnterView.this.startedDraggingX != -1.0f) {
                        float access$2000 = y - ChatActivityEnterView.this.startedDraggingX;
                        layoutParams.leftMargin = AndroidUtilities.dp(30.0f) + ((int) access$2000);
                        ChatActivityEnterView.this.slideText.setLayoutParams(layoutParams);
                        access$2000 = (access$2000 / ChatActivityEnterView.this.distCanMove) + 1.0f;
                        if (access$2000 > 1.0f) {
                            access$2000 = 1.0f;
                        } else if (access$2000 < BitmapDescriptorFactory.HUE_RED) {
                            access$2000 = BitmapDescriptorFactory.HUE_RED;
                        }
                        ChatActivityEnterView.this.slideText.setAlpha(access$2000);
                    }
                    if (y <= (ChatActivityEnterView.this.slideText.getX() + ((float) ChatActivityEnterView.this.slideText.getWidth())) + ((float) AndroidUtilities.dp(30.0f)) && ChatActivityEnterView.this.startedDraggingX == -1.0f) {
                        ChatActivityEnterView.this.startedDraggingX = y;
                        ChatActivityEnterView.this.distCanMove = ((float) ((ChatActivityEnterView.this.recordPanel.getMeasuredWidth() - ChatActivityEnterView.this.slideText.getMeasuredWidth()) - AndroidUtilities.dp(48.0f))) / 2.0f;
                        if (ChatActivityEnterView.this.distCanMove <= BitmapDescriptorFactory.HUE_RED) {
                            ChatActivityEnterView.this.distCanMove = (float) AndroidUtilities.dp(80.0f);
                        } else if (ChatActivityEnterView.this.distCanMove > ((float) AndroidUtilities.dp(80.0f))) {
                            ChatActivityEnterView.this.distCanMove = (float) AndroidUtilities.dp(80.0f);
                        }
                    }
                    if (layoutParams.leftMargin > AndroidUtilities.dp(30.0f)) {
                        layoutParams.leftMargin = AndroidUtilities.dp(30.0f);
                        ChatActivityEnterView.this.slideText.setLayoutParams(layoutParams);
                        ChatActivityEnterView.this.slideText.setAlpha(1.0f);
                        ChatActivityEnterView.this.startedDraggingX = -1.0f;
                    }
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });
        this.audioSendButton = new ImageView(activity);
        this.audioSendButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.audioSendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
        this.audioSendButton.setImageResource(R.drawable.mic);
        this.audioSendButton.setPadding(0, 0, AndroidUtilities.dp(4.0f), 0);
        this.audioVideoButtonContainer.addView(this.audioSendButton, LayoutHelper.createFrame(48, 48.0f));
        if (z) {
            this.videoSendButton = new ImageView(activity);
            this.videoSendButton.setScaleType(ScaleType.CENTER_INSIDE);
            this.videoSendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.videoSendButton.setImageResource(R.drawable.ic_msg_panel_video);
            this.videoSendButton.setPadding(0, 0, AndroidUtilities.dp(4.0f), 0);
            this.audioVideoButtonContainer.addView(this.videoSendButton, LayoutHelper.createFrame(48, 48.0f));
        }
        this.recordCircle = new RecordCircle(activity);
        this.recordCircle.setVisibility(8);
        this.sizeNotifierLayout.addView(this.recordCircle, LayoutHelper.createFrame(124, 194.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, -36.0f, BitmapDescriptorFactory.HUE_RED));
        this.cancelBotButton = new ImageView(activity);
        this.cancelBotButton.setVisibility(4);
        this.cancelBotButton.setScaleType(ScaleType.CENTER_INSIDE);
        ImageView imageView = this.cancelBotButton;
        Drawable closeProgressDrawable2 = new CloseProgressDrawable2();
        this.progressDrawable = closeProgressDrawable2;
        imageView.setImageDrawable(closeProgressDrawable2);
        this.progressDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelCancelInlineBot), Mode.MULTIPLY));
        this.cancelBotButton.setSoundEffectsEnabled(false);
        this.cancelBotButton.setScaleX(0.1f);
        this.cancelBotButton.setScaleY(0.1f);
        this.cancelBotButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.sendButtonContainer.addView(this.cancelBotButton, LayoutHelper.createFrame(48, 48.0f));
        this.cancelBotButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String obj = ChatActivityEnterView.this.messageEditText.getText().toString();
                int indexOf = obj.indexOf(32);
                if (indexOf == -1 || indexOf == obj.length() - 1) {
                    ChatActivityEnterView.this.setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                } else {
                    ChatActivityEnterView.this.setFieldText(obj.substring(0, indexOf + 1));
                }
            }
        });
        this.sendButton = new ImageView(activity);
        this.sendButton.setVisibility(4);
        this.sendButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.sendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelSend), Mode.MULTIPLY));
        this.sendButton.setImageResource(R.drawable.ic_send);
        this.sendButton.setSoundEffectsEnabled(false);
        this.sendButton.setScaleX(0.1f);
        this.sendButton.setScaleY(0.1f);
        this.sendButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.sendButtonContainer.addView(this.sendButton, LayoutHelper.createFrame(48, 48.0f));
        this.sendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivityEnterView.this.sendMessage();
            }
        });
        this.expandStickersButton = new ImageView(activity);
        this.expandStickersButton.setScaleType(ScaleType.CENTER);
        imageView = this.expandStickersButton;
        closeProgressDrawable2 = new AnimatedArrowDrawable(Theme.getColor(Theme.key_chat_messagePanelIcons));
        this.stickersArrow = closeProgressDrawable2;
        imageView.setImageDrawable(closeProgressDrawable2);
        this.expandStickersButton.setVisibility(8);
        this.expandStickersButton.setScaleX(0.1f);
        this.expandStickersButton.setScaleY(0.1f);
        this.expandStickersButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.sendButtonContainer.addView(this.expandStickersButton, LayoutHelper.createFrame(48, 48.0f));
        this.expandStickersButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (ChatActivityEnterView.this.expandStickersButton.getVisibility() == 0 && ChatActivityEnterView.this.expandStickersButton.getAlpha() == 1.0f && !ChatActivityEnterView.this.stickersDragging) {
                    ChatActivityEnterView.this.setStickersExpanded(!ChatActivityEnterView.this.stickersExpanded, true);
                }
            }
        });
        this.doneButtonContainer = new FrameLayout(activity);
        this.doneButtonContainer.setVisibility(8);
        this.textFieldContainer.addView(this.doneButtonContainer, LayoutHelper.createLinear(48, 48, 80));
        this.doneButtonContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivityEnterView.this.doneEditingMessage();
            }
        });
        this.doneButtonImage = new ImageView(activity);
        this.doneButtonImage.setScaleType(ScaleType.CENTER);
        this.doneButtonImage.setImageResource(R.drawable.edit_done);
        this.doneButtonImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_editDoneIcon), Mode.MULTIPLY));
        this.doneButtonContainer.addView(this.doneButtonImage, LayoutHelper.createFrame(48, 48.0f));
        this.doneButtonProgress = new ContextProgressView(activity, 0);
        this.doneButtonProgress.setVisibility(4);
        this.doneButtonContainer.addView(this.doneButtonProgress, LayoutHelper.createFrame(-1, -1.0f));
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        this.keyboardHeight = sharedPreferences.getInt("kbd_height", AndroidUtilities.dp(200.0f));
        this.keyboardHeightLand = sharedPreferences.getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
        setRecordVideoButtonVisible(false, false);
        checkSendButton(false);
        checkChannelRights();
    }

    private void CopyAssets() {
        AssetManager assets = ApplicationLoader.applicationContext.getAssets();
        String[] strArr = null;
        try {
            strArr = assets.list(DataBufferSafeParcelable.DATA_FIELD);
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (String str : r0) {
            if (str.contentEquals("white_bg.jpg")) {
                System.out.println("File name => " + str);
                try {
                    InputStream open = assets.open("data/" + str);
                    OutputStream fileOutputStream = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + str);
                    copyFile(open, fileOutputStream);
                    open.close();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (Exception e2) {
                    Log.e("tag", e2.getMessage());
                }
            }
        }
    }

    private void checkSendButton(boolean z) {
        if (this.editingMessageObject == null) {
            if (this.isPaused) {
                z = false;
            }
            if (AndroidUtilities.getTrimmedString(this.messageEditText.getText()).length() > 0 || this.forceShowSendButton || this.audioToSend != null || this.videoToSendMessageObject != null) {
                final String caption = this.messageEditText.getCaption();
                int i = (caption == null || !(this.sendButton.getVisibility() == 0 || this.expandStickersButton.getVisibility() == 0)) ? 0 : 1;
                int i2 = (caption == null && (this.cancelBotButton.getVisibility() == 0 || this.expandStickersButton.getVisibility() == 0)) ? 1 : 0;
                if (this.audioVideoButtonContainer.getVisibility() == 0 || i != 0 || i2 != 0) {
                    if (!z) {
                        this.audioVideoButtonContainer.setScaleX(0.1f);
                        this.audioVideoButtonContainer.setScaleY(0.1f);
                        this.audioVideoButtonContainer.setAlpha(BitmapDescriptorFactory.HUE_RED);
                        if (caption != null) {
                            this.sendButton.setScaleX(0.1f);
                            this.sendButton.setScaleY(0.1f);
                            this.sendButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                            this.cancelBotButton.setScaleX(1.0f);
                            this.cancelBotButton.setScaleY(1.0f);
                            this.cancelBotButton.setAlpha(1.0f);
                            this.cancelBotButton.setVisibility(0);
                            this.sendButton.setVisibility(8);
                        } else {
                            this.cancelBotButton.setScaleX(0.1f);
                            this.cancelBotButton.setScaleY(0.1f);
                            this.cancelBotButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                            this.sendButton.setScaleX(1.0f);
                            this.sendButton.setScaleY(1.0f);
                            this.sendButton.setAlpha(1.0f);
                            this.sendButton.setVisibility(0);
                            this.cancelBotButton.setVisibility(8);
                        }
                        this.audioVideoButtonContainer.setVisibility(8);
                        if (this.attachLayout != null) {
                            this.attachLayout.setVisibility(8);
                            if (this.delegate != null && getVisibility() == 0) {
                                this.delegate.onAttachButtonHidden();
                            }
                            updateFieldRight(0);
                        }
                    } else if (this.runningAnimationType != 1 || this.messageEditText.getCaption() != null) {
                        if (this.runningAnimationType != 3 || caption == null) {
                            if (this.runningAnimation != null) {
                                this.runningAnimation.cancel();
                                this.runningAnimation = null;
                            }
                            if (this.runningAnimation2 != null) {
                                this.runningAnimation2.cancel();
                                this.runningAnimation2 = null;
                            }
                            if (this.attachLayout != null) {
                                this.runningAnimation2 = new AnimatorSet();
                                AnimatorSet animatorSet = this.runningAnimation2;
                                r6 = new Animator[2];
                                r6[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                                r6[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED});
                                animatorSet.playTogether(r6);
                                this.runningAnimation2.setDuration(100);
                                this.runningAnimation2.addListener(new AnimatorListenerAdapter() {
                                    public void onAnimationCancel(Animator animator) {
                                        if (ChatActivityEnterView.this.runningAnimation2 != null && ChatActivityEnterView.this.runningAnimation2.equals(animator)) {
                                            ChatActivityEnterView.this.runningAnimation2 = null;
                                        }
                                    }

                                    public void onAnimationEnd(Animator animator) {
                                        if (ChatActivityEnterView.this.runningAnimation2 != null && ChatActivityEnterView.this.runningAnimation2.equals(animator)) {
                                            ChatActivityEnterView.this.attachLayout.setVisibility(8);
                                        }
                                    }
                                });
                                this.runningAnimation2.start();
                                updateFieldRight(0);
                                if (this.delegate != null && getVisibility() == 0) {
                                    this.delegate.onAttachButtonHidden();
                                }
                            }
                            this.runningAnimation = new AnimatorSet();
                            ArrayList arrayList = new ArrayList();
                            if (this.audioVideoButtonContainer.getVisibility() == 0) {
                                arrayList.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                            }
                            if (this.expandStickersButton.getVisibility() == 0) {
                                arrayList.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                            }
                            if (i != 0) {
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                            } else if (i2 != 0) {
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                            }
                            if (caption != null) {
                                this.runningAnimationType = 3;
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{1.0f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{1.0f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{1.0f}));
                                this.cancelBotButton.setVisibility(0);
                            } else {
                                this.runningAnimationType = 1;
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{1.0f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{1.0f}));
                                arrayList.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{1.0f}));
                                this.sendButton.setVisibility(0);
                            }
                            this.runningAnimation.playTogether(arrayList);
                            this.runningAnimation.setDuration(150);
                            this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationCancel(Animator animator) {
                                    if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                                        ChatActivityEnterView.this.runningAnimation = null;
                                    }
                                }

                                public void onAnimationEnd(Animator animator) {
                                    if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                                        if (caption != null) {
                                            ChatActivityEnterView.this.cancelBotButton.setVisibility(0);
                                            ChatActivityEnterView.this.sendButton.setVisibility(8);
                                        } else {
                                            ChatActivityEnterView.this.sendButton.setVisibility(0);
                                            ChatActivityEnterView.this.cancelBotButton.setVisibility(8);
                                        }
                                        ChatActivityEnterView.this.audioVideoButtonContainer.setVisibility(8);
                                        ChatActivityEnterView.this.expandStickersButton.setVisibility(8);
                                        ChatActivityEnterView.this.runningAnimation = null;
                                        ChatActivityEnterView.this.runningAnimationType = 0;
                                    }
                                }
                            });
                            this.runningAnimation.start();
                        }
                    }
                }
            } else if (this.emojiView == null || this.emojiView.getVisibility() != 0 || !this.stickersTabOpen || AndroidUtilities.isInMultiwindow) {
                if (this.sendButton.getVisibility() != 0 && this.cancelBotButton.getVisibility() != 0 && this.expandStickersButton.getVisibility() != 0) {
                    return;
                }
                if (!z) {
                    this.sendButton.setScaleX(0.1f);
                    this.sendButton.setScaleY(0.1f);
                    this.sendButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.cancelBotButton.setScaleX(0.1f);
                    this.cancelBotButton.setScaleY(0.1f);
                    this.cancelBotButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.expandStickersButton.setScaleX(0.1f);
                    this.expandStickersButton.setScaleY(0.1f);
                    this.expandStickersButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                    this.audioVideoButtonContainer.setScaleX(1.0f);
                    this.audioVideoButtonContainer.setScaleY(1.0f);
                    this.audioVideoButtonContainer.setAlpha(1.0f);
                    this.cancelBotButton.setVisibility(8);
                    this.sendButton.setVisibility(8);
                    this.expandStickersButton.setVisibility(8);
                    this.audioVideoButtonContainer.setVisibility(0);
                    if (this.attachLayout != null) {
                        if (getVisibility() == 0) {
                            this.delegate.onAttachButtonShow();
                        }
                        this.attachLayout.setVisibility(0);
                        updateFieldRight(1);
                    }
                } else if (this.runningAnimationType != 2) {
                    if (this.runningAnimation != null) {
                        this.runningAnimation.cancel();
                        this.runningAnimation = null;
                    }
                    if (this.runningAnimation2 != null) {
                        this.runningAnimation2.cancel();
                        this.runningAnimation2 = null;
                    }
                    if (this.attachLayout != null) {
                        this.attachLayout.setVisibility(0);
                        this.runningAnimation2 = new AnimatorSet();
                        r1 = this.runningAnimation2;
                        r3 = new Animator[2];
                        r3[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{1.0f});
                        r3[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{1.0f});
                        r1.playTogether(r3);
                        this.runningAnimation2.setDuration(100);
                        this.runningAnimation2.start();
                        updateFieldRight(1);
                        if (getVisibility() == 0) {
                            this.delegate.onAttachButtonShow();
                        }
                    }
                    this.audioVideoButtonContainer.setVisibility(0);
                    this.runningAnimation = new AnimatorSet();
                    this.runningAnimationType = 2;
                    r1 = new ArrayList();
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{1.0f}));
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{1.0f}));
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{1.0f}));
                    if (this.cancelBotButton.getVisibility() == 0) {
                        r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    } else if (this.expandStickersButton.getVisibility() == 0) {
                        r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    } else {
                        r1.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                        r1.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                    }
                    this.runningAnimation.playTogether(r1);
                    this.runningAnimation.setDuration(150);
                    this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationCancel(Animator animator) {
                            if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                                ChatActivityEnterView.this.runningAnimation = null;
                            }
                        }

                        public void onAnimationEnd(Animator animator) {
                            if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                                ChatActivityEnterView.this.sendButton.setVisibility(8);
                                ChatActivityEnterView.this.cancelBotButton.setVisibility(8);
                                ChatActivityEnterView.this.audioVideoButtonContainer.setVisibility(0);
                                ChatActivityEnterView.this.runningAnimation = null;
                                ChatActivityEnterView.this.runningAnimationType = 0;
                            }
                        }
                    });
                    this.runningAnimation.start();
                }
            } else if (!z) {
                this.sendButton.setScaleX(0.1f);
                this.sendButton.setScaleY(0.1f);
                this.sendButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.cancelBotButton.setScaleX(0.1f);
                this.cancelBotButton.setScaleY(0.1f);
                this.cancelBotButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.audioVideoButtonContainer.setScaleX(0.1f);
                this.audioVideoButtonContainer.setScaleY(0.1f);
                this.audioVideoButtonContainer.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.expandStickersButton.setScaleX(1.0f);
                this.expandStickersButton.setScaleY(1.0f);
                this.expandStickersButton.setAlpha(1.0f);
                this.cancelBotButton.setVisibility(8);
                this.sendButton.setVisibility(8);
                this.audioVideoButtonContainer.setVisibility(8);
                this.expandStickersButton.setVisibility(0);
                if (this.attachLayout != null) {
                    if (getVisibility() == 0) {
                        this.delegate.onAttachButtonShow();
                    }
                    this.attachLayout.setVisibility(0);
                    updateFieldRight(1);
                }
            } else if (this.runningAnimationType != 4) {
                if (this.runningAnimation != null) {
                    this.runningAnimation.cancel();
                    this.runningAnimation = null;
                }
                if (this.runningAnimation2 != null) {
                    this.runningAnimation2.cancel();
                    this.runningAnimation2 = null;
                }
                if (this.attachLayout != null) {
                    this.attachLayout.setVisibility(0);
                    this.runningAnimation2 = new AnimatorSet();
                    r1 = this.runningAnimation2;
                    r3 = new Animator[2];
                    r3[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{1.0f});
                    r3[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{1.0f});
                    r1.playTogether(r3);
                    this.runningAnimation2.setDuration(100);
                    this.runningAnimation2.start();
                    updateFieldRight(1);
                    if (getVisibility() == 0) {
                        this.delegate.onAttachButtonShow();
                    }
                }
                this.expandStickersButton.setVisibility(0);
                this.runningAnimation = new AnimatorSet();
                this.runningAnimationType = 4;
                r1 = new ArrayList();
                r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{1.0f}));
                r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{1.0f}));
                r1.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{1.0f}));
                if (this.cancelBotButton.getVisibility() == 0) {
                    r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                } else if (this.audioVideoButtonContainer.getVisibility() == 0) {
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                } else {
                    r1.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                    r1.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                }
                this.runningAnimation.playTogether(r1);
                this.runningAnimation.setDuration(150);
                this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                            ChatActivityEnterView.this.runningAnimation = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animator)) {
                            ChatActivityEnterView.this.sendButton.setVisibility(8);
                            ChatActivityEnterView.this.cancelBotButton.setVisibility(8);
                            ChatActivityEnterView.this.audioVideoButtonContainer.setVisibility(8);
                            ChatActivityEnterView.this.expandStickersButton.setVisibility(0);
                            ChatActivityEnterView.this.runningAnimation = null;
                            ChatActivityEnterView.this.runningAnimationType = 0;
                        }
                    }
                });
                this.runningAnimation.start();
            }
        }
    }

    private void checkUserStatusRegistry() {
        C2818c.a(getContext(), this).j();
    }

    private void copyFile(InputStream inputStream, OutputStream outputStream) {
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    private void createEmojiView() {
        if (this.emojiView == null) {
            this.emojiView = new EmojiView(this.allowStickers, this.allowGifs, this.parentActivity, this.info);
            this.emojiView.setVisibility(8);
            this.emojiView.setListener(new Listener() {

                /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$41$1 */
                class C43431 implements DialogInterface.OnClickListener {
                    C43431() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        ChatActivityEnterView.this.emojiView.clearRecentEmoji();
                    }
                }

                public boolean onBackspace() {
                    if (ChatActivityEnterView.this.messageEditText.length() == 0) {
                        return false;
                    }
                    ChatActivityEnterView.this.messageEditText.dispatchKeyEvent(new KeyEvent(0, 67));
                    return true;
                }

                public void onClearEmojiRecent() {
                    if (ChatActivityEnterView.this.parentFragment != null && ChatActivityEnterView.this.parentActivity != null) {
                        Builder builder = new Builder(ChatActivityEnterView.this.parentActivity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("ClearRecentEmoji", R.string.ClearRecentEmoji));
                        builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C43431());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivityEnterView.this.parentFragment.showDialog(builder.create());
                    }
                }

                public void onEmojiSelected(String str) {
                    int selectionEnd = ChatActivityEnterView.this.messageEditText.getSelectionEnd();
                    if (selectionEnd < 0) {
                        selectionEnd = 0;
                    }
                    try {
                        ChatActivityEnterView.this.innerTextChange = 2;
                        CharSequence replaceEmoji = Emoji.replaceEmoji(str, ChatActivityEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        ChatActivityEnterView.this.messageEditText.setText(ChatActivityEnterView.this.messageEditText.getText().insert(selectionEnd, replaceEmoji));
                        selectionEnd += replaceEmoji.length();
                        ChatActivityEnterView.this.messageEditText.setSelection(selectionEnd, selectionEnd);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    } finally {
                        ChatActivityEnterView.this.innerTextChange = 0;
                    }
                }

                public void onGifSelected(Document document) {
                    if (ChatActivityEnterView.this.stickersExpanded) {
                        ChatActivityEnterView.this.setStickersExpanded(false, true);
                    }
                    SendMessagesHelper.getInstance().sendSticker(document, ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, ChatActivityEnterView.this.getContext());
                    StickersQuery.addRecentGif(document, (int) (System.currentTimeMillis() / 1000));
                    if (((int) ChatActivityEnterView.this.dialog_id) == 0) {
                        MessagesController.getInstance().saveGif(document);
                    }
                    if (ChatActivityEnterView.this.delegate != null) {
                        ChatActivityEnterView.this.delegate.onMessageSend(null);
                    }
                }

                public void onGifTab(boolean z) {
                    ChatActivityEnterView.this.post(ChatActivityEnterView.this.updateExpandabilityRunnable);
                    if (!AndroidUtilities.usingHardwareInput) {
                        if (z) {
                            if (ChatActivityEnterView.this.messageEditText.length() == 0) {
                                ChatActivityEnterView.this.messageEditText.setText("@gif ");
                                ChatActivityEnterView.this.messageEditText.setSelection(ChatActivityEnterView.this.messageEditText.length());
                            }
                        } else if (ChatActivityEnterView.this.messageEditText.getText().toString().equals("@gif ")) {
                            ChatActivityEnterView.this.messageEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        }
                    }
                }

                public void onShowStickerSet(StickerSet stickerSet, InputStickerSet inputStickerSet) {
                    if (ChatActivityEnterView.this.parentFragment != null && ChatActivityEnterView.this.parentActivity != null) {
                        InputStickerSet tLRPC$TL_inputStickerSetID;
                        if (stickerSet != null) {
                            tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                            tLRPC$TL_inputStickerSetID.access_hash = stickerSet.access_hash;
                            tLRPC$TL_inputStickerSetID.id = stickerSet.id;
                        } else {
                            tLRPC$TL_inputStickerSetID = inputStickerSet;
                        }
                        ChatActivityEnterView.this.parentFragment.showDialog(new StickersAlert(ChatActivityEnterView.this.parentActivity, ChatActivityEnterView.this.parentFragment, tLRPC$TL_inputStickerSetID, null, ChatActivityEnterView.this));
                    }
                }

                public void onStickerSelected(Document document) {
                    if (ChatActivityEnterView.this.stickersExpanded) {
                        ChatActivityEnterView.this.setStickersExpanded(false, true);
                    }
                    ChatActivityEnterView.this.onStickerSelected(document);
                    StickersQuery.addRecentSticker(0, document, (int) (System.currentTimeMillis() / 1000), false);
                    if (((int) ChatActivityEnterView.this.dialog_id) == 0) {
                        MessagesController.getInstance().saveGif(document);
                    }
                }

                public void onStickerSetAdd(StickerSetCovered stickerSetCovered) {
                    StickersQuery.removeStickersSet(ChatActivityEnterView.this.parentActivity, stickerSetCovered.set, 2, ChatActivityEnterView.this.parentFragment, false);
                }

                public void onStickerSetRemove(StickerSetCovered stickerSetCovered) {
                    StickersQuery.removeStickersSet(ChatActivityEnterView.this.parentActivity, stickerSetCovered.set, 0, ChatActivityEnterView.this.parentFragment, false);
                }

                public void onStickersGroupClick(int i) {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        if (AndroidUtilities.isTablet()) {
                            ChatActivityEnterView.this.hidePopup(false);
                        }
                        BaseFragment groupStickersActivity = new GroupStickersActivity(i);
                        groupStickersActivity.setInfo(ChatActivityEnterView.this.info);
                        ChatActivityEnterView.this.parentFragment.presentFragment(groupStickersActivity);
                    }
                }

                public void onStickersSettingsClick() {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        ChatActivityEnterView.this.parentFragment.presentFragment(new StickersActivity(0));
                    }
                }

                public void onStickersTab(boolean z) {
                    ChatActivityEnterView.this.delegate.onStickersTab(z);
                    ChatActivityEnterView.this.post(ChatActivityEnterView.this.updateExpandabilityRunnable);
                }
            });
            this.emojiView.setDragListener(new DragListener() {
                int initialOffset;
                boolean wasExpanded;

                private boolean allowDragging() {
                    return ChatActivityEnterView.this.stickersTabOpen && ((ChatActivityEnterView.this.stickersExpanded || ChatActivityEnterView.this.messageEditText.length() <= 0) && ChatActivityEnterView.this.emojiView.areThereAnyStickers());
                }

                public void onDrag(int i) {
                    if (allowDragging()) {
                        int access$9900 = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? ChatActivityEnterView.this.keyboardHeightLand : ChatActivityEnterView.this.keyboardHeight;
                        int max = Math.max(Math.min(this.initialOffset + i, 0), -(ChatActivityEnterView.this.stickersExpandedHeight - access$9900));
                        ChatActivityEnterView.this.emojiView.setTranslationY((float) max);
                        ChatActivityEnterView.this.setTranslationY((float) max);
                        ChatActivityEnterView.this.stickersExpansionProgress = ((float) max) / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - access$9900)));
                        ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                    }
                }

                public void onDragCancel() {
                    if (ChatActivityEnterView.this.stickersTabOpen) {
                        ChatActivityEnterView.this.stickersDragging = false;
                        ChatActivityEnterView.this.setStickersExpanded(this.wasExpanded, true);
                    }
                }

                public void onDragEnd(float f) {
                    boolean z = false;
                    if (allowDragging()) {
                        ChatActivityEnterView.this.stickersDragging = false;
                        if ((!this.wasExpanded || f < ((float) AndroidUtilities.dp(200.0f))) && ((this.wasExpanded || f > ((float) AndroidUtilities.dp(-200.0f))) && ((!this.wasExpanded || ChatActivityEnterView.this.stickersExpansionProgress > 0.6f) && (this.wasExpanded || ChatActivityEnterView.this.stickersExpansionProgress < 0.4f)))) {
                            ChatActivityEnterView.this.setStickersExpanded(this.wasExpanded, true);
                            return;
                        }
                        ChatActivityEnterView chatActivityEnterView = ChatActivityEnterView.this;
                        if (!this.wasExpanded) {
                            z = true;
                        }
                        chatActivityEnterView.setStickersExpanded(z, true);
                    }
                }

                public void onDragStart() {
                    if (allowDragging()) {
                        if (ChatActivityEnterView.this.stickersExpansionAnim != null) {
                            ChatActivityEnterView.this.stickersExpansionAnim.cancel();
                        }
                        ChatActivityEnterView.this.stickersDragging = true;
                        this.wasExpanded = ChatActivityEnterView.this.stickersExpanded;
                        ChatActivityEnterView.this.stickersExpanded = true;
                        ChatActivityEnterView.this.stickersExpandedHeight = (((ChatActivityEnterView.this.sizeNotifierLayout.getHeight() - (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) - ActionBar.getCurrentActionBarHeight()) - ChatActivityEnterView.this.getHeight()) + Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                        ChatActivityEnterView.this.emojiView.getLayoutParams().height = ChatActivityEnterView.this.stickersExpandedHeight;
                        ChatActivityEnterView.this.emojiView.setLayerType(2, null);
                        ChatActivityEnterView.this.sizeNotifierLayout.requestLayout();
                        ChatActivityEnterView.this.sizeNotifierLayout.setForeground(new ScrimDrawable());
                        this.initialOffset = (int) ChatActivityEnterView.this.getTranslationY();
                    }
                }
            });
            this.emojiView.setVisibility(8);
            this.sizeNotifierLayout.addView(this.emojiView);
            checkChannelRights();
        }
    }

    private void hideRecordedAudioPanel() {
        this.audioToSendPath = null;
        this.audioToSend = null;
        this.audioToSendMessageObject = null;
        this.videoToSendMessageObject = null;
        this.videoTimelineView.destroy();
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.recordedAudioPanel, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ChatActivityEnterView.this.recordedAudioPanel.setVisibility(8);
            }
        });
        animatorSet.start();
    }

    private void onWindowSizeChanged() {
        int height = this.sizeNotifierLayout.getHeight();
        if (!this.keyboardVisible) {
            height -= this.emojiPadding;
        }
        if (this.delegate != null) {
            this.delegate.onWindowSizeChanged(height);
        }
        if (this.topView == null) {
            return;
        }
        if (height < AndroidUtilities.dp(72.0f) + ActionBar.getCurrentActionBarHeight()) {
            if (this.allowShowTopView) {
                this.allowShowTopView = false;
                if (this.needShowTopView) {
                    this.topView.setVisibility(8);
                    resizeForTopView(false);
                    this.topView.setTranslationY((float) this.topView.getLayoutParams().height);
                }
            }
        } else if (!this.allowShowTopView) {
            this.allowShowTopView = true;
            if (this.needShowTopView) {
                this.topView.setVisibility(0);
                resizeForTopView(true);
                this.topView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    private void openKeyboardInternal() {
        int i = (AndroidUtilities.usingHardwareInput || this.isPaused) ? 0 : 2;
        showPopup(i, 0);
        this.messageEditText.requestFocus();
        AndroidUtilities.showKeyboard(this.messageEditText);
        if (this.isPaused) {
            this.showKeyboardOnResume = true;
        } else if (!AndroidUtilities.usingHardwareInput && !this.keyboardVisible && !AndroidUtilities.isInMultiwindow) {
            this.waitingForKeyboardOpen = true;
            AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
            AndroidUtilities.runOnUIThread(this.openKeyboardRunnable, 100);
        }
    }

    private void removeGifFromInputField() {
        if (!AndroidUtilities.usingHardwareInput && this.messageEditText.getText().toString().equals("@gif ")) {
            this.messageEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
        }
    }

    private void resizeForTopView(boolean z) {
        LayoutParams layoutParams = (LayoutParams) this.textFieldContainer.getLayoutParams();
        layoutParams.topMargin = (z ? this.topView.getLayoutParams().height : 0) + AndroidUtilities.dp(2.0f);
        this.textFieldContainer.setLayoutParams(layoutParams);
        if (this.stickersExpanded) {
            setStickersExpanded(false, true);
        }
    }

    private void sendMessage() {
        if (this.parentFragment != null) {
            String str;
            if (((int) this.dialog_id) < 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                str = (chat == null || chat.participants_count <= MessagesController.getInstance().groupBigSize) ? "chat_message" : "bigchat_message";
            } else {
                str = "pm_message";
            }
            if (!MessagesController.isFeatureEnabled(str, this.parentFragment)) {
                return;
            }
        }
        if (this.videoToSendMessageObject != null) {
            this.delegate.needStartRecordVideo(4);
            hideRecordedAudioPanel();
            checkSendButton(true);
        } else if (this.audioToSend != null) {
            MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
            if (playingMessageObject != null && playingMessageObject == this.audioToSendMessageObject) {
                MediaController.getInstance().cleanupPlayer(true, true);
            }
            SendMessagesHelper.getInstance().sendMessage(this.audioToSend, null, this.audioToSendPath, this.dialog_id, this.replyingMessageObject, null, null, 0);
            if (this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
            hideRecordedAudioPanel();
            checkSendButton(true);
        } else {
            CharSequence text = this.messageEditText.getText();
            if (processSendingText(text)) {
                this.messageEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
                this.lastTypingTimeSend = 0;
                if (this.delegate != null) {
                    this.delegate.onMessageSend(text);
                }
            } else if (this.forceShowSendButton && this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
        }
    }

    private void setEmojiButtonImage() {
        int i = this.emojiView == null ? getContext().getSharedPreferences("emoji", 0).getInt("selected_page", 0) : this.emojiView.getCurrentPage();
        if (i == 0 || !(this.allowStickers || this.allowGifs)) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_smiles);
        } else if (i == 1) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_stickers);
        } else if (i == 2) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_gif);
        }
    }

    private void setRecordVideoButtonVisible(boolean z, boolean z2) {
        float f = BitmapDescriptorFactory.HUE_RED;
        float f2 = 0.1f;
        if (this.videoSendButton != null) {
            this.videoSendButton.setTag(z ? Integer.valueOf(1) : null);
            if (this.audioVideoButtonAnimation != null) {
                this.audioVideoButtonAnimation.cancel();
                this.audioVideoButtonAnimation = null;
            }
            if (z2) {
                int i;
                SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                if (((int) this.dialog_id) < 0) {
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                    i = (!ChatObject.isChannel(chat) || chat.megagroup) ? 0 : 1;
                } else {
                    i = 0;
                }
                sharedPreferences.edit().putBoolean(i != 0 ? "currentModeVideoChannel" : "currentModeVideo", z).commit();
                this.audioVideoButtonAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.audioVideoButtonAnimation;
                Animator[] animatorArr = new Animator[6];
                ImageView imageView = this.videoSendButton;
                String str = "scaleX";
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.1f;
                animatorArr[0] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.videoSendButton;
                str = "scaleY";
                fArr = new float[1];
                fArr[0] = z ? 1.0f : 0.1f;
                animatorArr[1] = ObjectAnimator.ofFloat(imageView, str, fArr);
                ImageView imageView2 = this.videoSendButton;
                String str2 = "alpha";
                float[] fArr2 = new float[1];
                fArr2[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                animatorArr[2] = ObjectAnimator.ofFloat(imageView2, str2, fArr2);
                imageView2 = this.audioSendButton;
                str2 = "scaleX";
                fArr2 = new float[1];
                fArr2[0] = z ? 0.1f : 1.0f;
                animatorArr[3] = ObjectAnimator.ofFloat(imageView2, str2, fArr2);
                imageView = this.audioSendButton;
                str = "scaleY";
                fArr = new float[1];
                if (!z) {
                    f2 = 1.0f;
                }
                fArr[0] = f2;
                animatorArr[4] = ObjectAnimator.ofFloat(imageView, str, fArr);
                ImageView imageView3 = this.audioSendButton;
                String str3 = "alpha";
                float[] fArr3 = new float[1];
                if (!z) {
                    f = 1.0f;
                }
                fArr3[0] = f;
                animatorArr[5] = ObjectAnimator.ofFloat(imageView3, str3, fArr3);
                animatorSet.playTogether(animatorArr);
                this.audioVideoButtonAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (animator.equals(ChatActivityEnterView.this.audioVideoButtonAnimation)) {
                            ChatActivityEnterView.this.audioVideoButtonAnimation = null;
                        }
                    }
                });
                this.audioVideoButtonAnimation.setInterpolator(new DecelerateInterpolator());
                this.audioVideoButtonAnimation.setDuration(150);
                this.audioVideoButtonAnimation.start();
                return;
            }
            this.videoSendButton.setScaleX(z ? 1.0f : 0.1f);
            this.videoSendButton.setScaleY(z ? 1.0f : 0.1f);
            this.videoSendButton.setAlpha(z ? 1.0f : BitmapDescriptorFactory.HUE_RED);
            this.audioSendButton.setScaleX(z ? 0.1f : 1.0f);
            ImageView imageView4 = this.audioSendButton;
            if (!z) {
                f2 = 1.0f;
            }
            imageView4.setScaleY(f2);
            imageView4 = this.audioSendButton;
            if (!z) {
                f = 1.0f;
            }
            imageView4.setAlpha(f);
        }
    }

    private void setStickersExpanded(boolean z, boolean z2) {
        if (this.emojiView == null) {
            return;
        }
        if (!z || this.emojiView.areThereAnyStickers()) {
            this.stickersExpanded = z;
            final int i = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight;
            if (this.stickersExpansionAnim != null) {
                this.stickersExpansionAnim.cancel();
                this.stickersExpansionAnim = null;
            }
            Animator animatorSet;
            Animator[] animatorArr;
            if (this.stickersExpanded) {
                this.stickersExpandedHeight = (((this.sizeNotifierLayout.getHeight() - (VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0)) - ActionBar.getCurrentActionBarHeight()) - getHeight()) + Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                this.emojiView.getLayoutParams().height = this.stickersExpandedHeight;
                this.sizeNotifierLayout.requestLayout();
                this.sizeNotifierLayout.setForeground(new ScrimDrawable());
                this.messageEditText.setText(this.messageEditText.getText());
                if (z2) {
                    animatorSet = new AnimatorSet();
                    animatorArr = new Animator[3];
                    animatorArr[0] = ObjectAnimator.ofInt(this, this.roundedTranslationYProperty, new int[]{-(this.stickersExpandedHeight - i)});
                    animatorArr[1] = ObjectAnimator.ofInt(this.emojiView, this.roundedTranslationYProperty, new int[]{-(this.stickersExpandedHeight - i)});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.stickersArrow, "animationProgress", new float[]{1.0f});
                    animatorSet.playTogether(animatorArr);
                    animatorSet.setDuration(400);
                    animatorSet.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    ((ObjectAnimator) animatorSet.getChildAnimations().get(0)).addUpdateListener(new AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            ChatActivityEnterView.this.stickersExpansionProgress = ChatActivityEnterView.this.getTranslationY() / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - i)));
                            ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                        }
                    });
                    animatorSet.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animator) {
                            ChatActivityEnterView.this.stickersExpansionAnim = null;
                            ChatActivityEnterView.this.emojiView.setLayerType(0, null);
                        }
                    });
                    this.stickersExpansionAnim = animatorSet;
                    this.emojiView.setLayerType(2, null);
                    animatorSet.start();
                    return;
                }
                this.stickersExpansionProgress = 1.0f;
                setTranslationY((float) (-(this.stickersExpandedHeight - i)));
                this.emojiView.setTranslationY((float) (-(this.stickersExpandedHeight - i)));
                this.stickersArrow.setAnimationProgress(1.0f);
            } else if (z2) {
                animatorSet = new AnimatorSet();
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofInt(this, this.roundedTranslationYProperty, new int[]{0});
                animatorArr[1] = ObjectAnimator.ofInt(this.emojiView, this.roundedTranslationYProperty, new int[]{0});
                animatorArr[2] = ObjectAnimator.ofFloat(this.stickersArrow, "animationProgress", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorSet.playTogether(animatorArr);
                animatorSet.setDuration(400);
                animatorSet.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                ((ObjectAnimator) animatorSet.getChildAnimations().get(0)).addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ChatActivityEnterView.this.stickersExpansionProgress = ChatActivityEnterView.this.getTranslationY() / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - i)));
                        ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                    }
                });
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        ChatActivityEnterView.this.stickersExpansionAnim = null;
                        ChatActivityEnterView.this.emojiView.getLayoutParams().height = i;
                        ChatActivityEnterView.this.sizeNotifierLayout.requestLayout();
                        ChatActivityEnterView.this.emojiView.setLayerType(0, null);
                        ChatActivityEnterView.this.sizeNotifierLayout.setForeground(null);
                        ChatActivityEnterView.this.sizeNotifierLayout.setWillNotDraw(false);
                    }
                });
                this.stickersExpansionAnim = animatorSet;
                this.emojiView.setLayerType(2, null);
                animatorSet.start();
            } else {
                this.stickersExpansionProgress = BitmapDescriptorFactory.HUE_RED;
                setTranslationY(BitmapDescriptorFactory.HUE_RED);
                this.emojiView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                this.emojiView.getLayoutParams().height = i;
                this.sizeNotifierLayout.requestLayout();
                this.sizeNotifierLayout.setForeground(null);
                this.sizeNotifierLayout.setWillNotDraw(false);
                this.stickersArrow.setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
            }
        }
    }

    private void showLoadingDialog(Context context, String str, String str2) {
        if (this.parentActivity != null) {
            this.dialogLoading = new ProgressDialog(this.parentActivity);
            this.dialogLoading.setTitle(str);
            this.dialogLoading.setMessage(str2);
            this.dialogLoading.show();
        }
    }

    private void showPaymentSheet1() {
        if (this.parentActivity != null) {
            final C0147c c0147c = new C0147c(this.parentActivity);
            c0147c.setContentView(R.layout.bottom_sheet_payment_request);
            final TextView textView = (TextView) c0147c.findViewById(R.id.btn_send_request);
            TextView textView2 = (TextView) c0147c.findViewById(R.id.btn_close);
            final NumericEditText numericEditText = (NumericEditText) c0147c.findViewById(R.id.et_amount);
            final FarsiEditText farsiEditText = (FarsiEditText) c0147c.findViewById(R.id.et_desc);
            final ProgressBar progressBar = (ProgressBar) c0147c.findViewById(R.id.pb_loading);
            final View findViewById = c0147c.findViewById(R.id.rl_fade);
            textView.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$47$1 */
                class C43441 implements C2497d {
                    C43441() {
                    }

                    public void onResult(Object obj, int i) {
                        progressBar.setVisibility(8);
                        findViewById.setVisibility(8);
                        switch (i) {
                            case -32:
                                ToastUtil.m14196a(ApplicationLoader.applicationContext, "خطلا در دریافت اطلاعات").show();
                                textView.setEnabled(true);
                                return;
                            case 32:
                                PaymentLink paymentLink = (PaymentLink) obj;
                                Log.d("alireza", "alireza payment" + paymentLink.getLink());
                                ChatActivityEnterView.this.messageEditText.setText(paymentLink.getLink());
                                ChatActivityEnterView.this.sendMessage();
                                c0147c.dismiss();
                                return;
                            default:
                                return;
                        }
                    }
                }

                public void onClick(View view) {
                    if (!TextUtils.isEmpty(numericEditText.getText().toString())) {
                        textView.setEnabled(false);
                        progressBar.setVisibility(0);
                        findViewById.setVisibility(0);
                        AndroidUtilities.hideKeyboard(farsiEditText);
                        AndroidUtilities.hideKeyboard(numericEditText);
                        C2818c.a(ApplicationLoader.applicationContext, new C43441()).a(ChatActivityEnterView.this.dialog_id, (long) UserConfig.getClientUserId(), Long.parseLong(numericEditText.getClearText()), farsiEditText.getText().toString());
                    }
                }
            });
            textView2.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    c0147c.dismiss();
                }
            });
            c0147c.show();
        }
    }

    private void showPopup(int i, int i2) {
        if (i == 1) {
            View view;
            if (i2 == 0 && this.emojiView == null) {
                if (this.parentActivity != null) {
                    createEmojiView();
                } else {
                    return;
                }
            }
            if (i2 == 0) {
                this.emojiView.setVisibility(0);
                if (!(this.botKeyboardView == null || this.botKeyboardView.getVisibility() == 8)) {
                    this.botKeyboardView.setVisibility(8);
                }
                view = this.emojiView;
            } else if (i2 == 1) {
                if (!(this.emojiView == null || this.emojiView.getVisibility() == 8)) {
                    this.emojiView.setVisibility(8);
                }
                this.botKeyboardView.setVisibility(0);
                view = this.botKeyboardView;
            } else {
                view = null;
            }
            this.currentPopupContentType = i2;
            if (this.keyboardHeight <= 0) {
                this.keyboardHeight = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height", AndroidUtilities.dp(200.0f));
            }
            if (this.keyboardHeightLand <= 0) {
                this.keyboardHeightLand = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
            }
            int i3 = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight;
            int min = i2 == 1 ? Math.min(this.botKeyboardView.getKeyboardHeight(), i3) : i3;
            if (this.botKeyboardView != null) {
                this.botKeyboardView.setPanelHeight(min);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.height = min;
            view.setLayoutParams(layoutParams);
            if (!AndroidUtilities.isInMultiwindow) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
            if (this.sizeNotifierLayout != null) {
                this.emojiPadding = min;
                this.sizeNotifierLayout.requestLayout();
                if (i2 == 0) {
                    this.emojiButton.setImageResource(R.drawable.ic_msg_panel_kb);
                } else if (i2 == 1) {
                    setEmojiButtonImage();
                }
                updateBotButton();
                onWindowSizeChanged();
            }
        } else {
            if (this.emojiButton != null) {
                setEmojiButtonImage();
            }
            this.currentPopupContentType = -1;
            if (this.emojiView != null) {
                this.emojiView.setVisibility(8);
            }
            if (this.botKeyboardView != null) {
                this.botKeyboardView.setVisibility(8);
            }
            if (this.sizeNotifierLayout != null) {
                if (i == 0) {
                    this.emojiPadding = 0;
                }
                this.sizeNotifierLayout.requestLayout();
                onWindowSizeChanged();
            }
            updateBotButton();
        }
        if (this.stickersTabOpen) {
            checkSendButton(true);
        }
        if (this.stickersExpanded && i != 1) {
            setStickersExpanded(false, false);
        }
    }

    private void updateBotButton() {
        if (this.botButton != null) {
            if (this.hasBotCommands || this.botReplyMarkup != null) {
                if (this.botButton.getVisibility() != 0) {
                    this.botButton.setVisibility(0);
                }
                if (this.botReplyMarkup == null) {
                    this.botButton.setImageResource(R.drawable.bot_keyboard);
                } else if (isPopupShowing() && this.currentPopupContentType == 1) {
                    this.botButton.setImageResource(R.drawable.ic_msg_panel_kb);
                } else {
                    this.botButton.setImageResource(R.drawable.bot_keyboard2);
                }
            } else {
                this.botButton.setVisibility(8);
            }
            updateFieldRight(2);
            LinearLayout linearLayout = this.attachLayout;
            float f = ((this.botButton == null || this.botButton.getVisibility() == 8) && (this.notifyButton == null || this.notifyButton.getVisibility() == 8)) ? 48.0f : 96.0f;
            linearLayout.setPivotX((float) AndroidUtilities.dp(f));
        }
    }

    private void updateFieldHint() {
        Object obj = null;
        if (((int) this.dialog_id) < 0) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
            if (ChatObject.isChannel(chat) && !chat.megagroup) {
                obj = 1;
            }
        }
        if (obj == null) {
            this.messageEditText.setHintText(LocaleController.getString("TypeMessage", R.string.TypeMessage));
        } else if (this.editingMessageObject != null) {
            this.messageEditText.setHintText(this.editingCaption ? LocaleController.getString("Caption", R.string.Caption) : LocaleController.getString("TypeMessage", R.string.TypeMessage));
        } else if (this.silent) {
            this.messageEditText.setHintText(LocaleController.getString("ChannelSilentBroadcast", R.string.ChannelSilentBroadcast));
        } else {
            this.messageEditText.setHintText(LocaleController.getString("ChannelBroadcast", R.string.ChannelBroadcast));
        }
    }

    private void updateFieldRight(int i) {
        if (this.messageEditText != null && this.editingMessageObject == null) {
            LayoutParams layoutParams = (LayoutParams) this.messageEditText.getLayoutParams();
            if (i == 1) {
                if ((this.botButton == null || this.botButton.getVisibility() != 0) && (this.notifyButton == null || this.notifyButton.getVisibility() != 0)) {
                    layoutParams.rightMargin = AndroidUtilities.dp(50.0f);
                } else {
                    layoutParams.rightMargin = AndroidUtilities.dp(98.0f);
                }
            } else if (i != 2) {
                layoutParams.rightMargin = AndroidUtilities.dp(2.0f);
            } else if (layoutParams.rightMargin != AndroidUtilities.dp(2.0f)) {
                if ((this.botButton == null || this.botButton.getVisibility() != 0) && (this.notifyButton == null || this.notifyButton.getVisibility() != 0)) {
                    layoutParams.rightMargin = AndroidUtilities.dp(50.0f);
                } else {
                    layoutParams.rightMargin = AndroidUtilities.dp(98.0f);
                }
            }
            this.messageEditText.setLayoutParams(layoutParams);
        }
    }

    private void updateRecordIntefrace() {
        AnimatorSet animatorSet;
        Animator[] animatorArr;
        if (!this.recordingAudioVideo) {
            if (this.wakeLock != null) {
                try {
                    this.wakeLock.release();
                    this.wakeLock = null;
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
            AndroidUtilities.unlockOrientation(this.parentActivity);
            if (this.recordInterfaceState != 0) {
                this.recordInterfaceState = 0;
                if (this.runningAnimationAudio != null) {
                    this.runningAnimationAudio.cancel();
                }
                this.runningAnimationAudio = new AnimatorSet();
                animatorSet = this.runningAnimationAudio;
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofFloat(this.recordPanel, "translationX", new float[]{(float) AndroidUtilities.displaySize.x});
                animatorArr[1] = ObjectAnimator.ofFloat(this.recordCircle, "scale", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[2] = ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
                this.runningAnimationAudio.setDuration(300);
                this.runningAnimationAudio.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivityEnterView.this.runningAnimationAudio != null && ChatActivityEnterView.this.runningAnimationAudio.equals(animator)) {
                            LayoutParams layoutParams = (LayoutParams) ChatActivityEnterView.this.slideText.getLayoutParams();
                            layoutParams.leftMargin = AndroidUtilities.dp(30.0f);
                            ChatActivityEnterView.this.slideText.setLayoutParams(layoutParams);
                            ChatActivityEnterView.this.slideText.setAlpha(1.0f);
                            ChatActivityEnterView.this.recordPanel.setVisibility(8);
                            ChatActivityEnterView.this.recordCircle.setVisibility(8);
                            ChatActivityEnterView.this.recordCircle.setSendButtonInvisible();
                            ChatActivityEnterView.this.runningAnimationAudio = null;
                        }
                    }
                });
                this.runningAnimationAudio.setInterpolator(new AccelerateInterpolator());
                this.runningAnimationAudio.start();
            }
        } else if (this.recordInterfaceState != 1) {
            this.recordInterfaceState = 1;
            try {
                if (this.wakeLock == null) {
                    this.wakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(536870918, "audio record lock");
                    this.wakeLock.acquire();
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            AndroidUtilities.lockOrientation(this.parentActivity);
            this.recordPanel.setVisibility(0);
            this.recordCircle.setVisibility(0);
            this.recordCircle.setAmplitude(0.0d);
            this.recordTimeText.setText(String.format("%02d:%02d.%02d", new Object[]{Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)}));
            this.recordDot.resetAlpha();
            this.lastTimeString = null;
            this.lastTypingSendTime = -1;
            LayoutParams layoutParams = (LayoutParams) this.slideText.getLayoutParams();
            layoutParams.leftMargin = AndroidUtilities.dp(30.0f);
            this.slideText.setLayoutParams(layoutParams);
            this.slideText.setAlpha(1.0f);
            this.recordPanel.setX((float) AndroidUtilities.displaySize.x);
            if (this.runningAnimationAudio != null) {
                this.runningAnimationAudio.cancel();
            }
            this.runningAnimationAudio = new AnimatorSet();
            animatorSet = this.runningAnimationAudio;
            animatorArr = new Animator[3];
            animatorArr[0] = ObjectAnimator.ofFloat(this.recordPanel, "translationX", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorArr[1] = ObjectAnimator.ofFloat(this.recordCircle, "scale", new float[]{1.0f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            animatorSet.playTogether(animatorArr);
            this.runningAnimationAudio.setDuration(300);
            this.runningAnimationAudio.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (ChatActivityEnterView.this.runningAnimationAudio != null && ChatActivityEnterView.this.runningAnimationAudio.equals(animator)) {
                        ChatActivityEnterView.this.recordPanel.setX(BitmapDescriptorFactory.HUE_RED);
                        ChatActivityEnterView.this.runningAnimationAudio = null;
                    }
                }
            });
            this.runningAnimationAudio.setInterpolator(new DecelerateInterpolator());
            this.runningAnimationAudio.start();
        }
    }

    public void addEmojiToRecent(String str) {
        createEmojiView();
        this.emojiView.addEmojiToRecent(str);
    }

    public void addRecentGif(Document document) {
        StickersQuery.addRecentGif(document, (int) (System.currentTimeMillis() / 1000));
        if (this.emojiView != null) {
            this.emojiView.addRecentGif(document);
        }
    }

    public void addStickerToRecent(Document document) {
        createEmojiView();
        this.emojiView.addRecentSticker(document);
    }

    public void addTopView(View view, int i) {
        if (view != null) {
            this.topView = view;
            this.topView.setVisibility(8);
            this.topView.setTranslationY((float) i);
            addView(this.topView, 0, LayoutHelper.createFrame(-1, (float) i, 51, BitmapDescriptorFactory.HUE_RED, 2.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.needShowTopView = false;
        }
    }

    public void cancelRecordingAudioVideo() {
        if (!this.hasRecordVideo || this.videoSendButton.getTag() == null) {
            this.delegate.needStartRecordAudio(0);
            MediaController.getInstance().stopRecording(0);
        } else {
            this.delegate.needStartRecordVideo(2);
        }
        this.recordingAudioVideo = false;
        updateRecordIntefrace();
    }

    public void checkChannelRights() {
        if (this.parentFragment != null) {
            Chat currentChat = this.parentFragment.getCurrentChat();
            if (ChatObject.isChannel(currentChat)) {
                FrameLayout frameLayout = this.audioVideoButtonContainer;
                float f = (currentChat.banned_rights == null || !currentChat.banned_rights.send_media) ? 1.0f : 0.5f;
                frameLayout.setAlpha(f);
                if (this.emojiView != null) {
                    EmojiView emojiView = this.emojiView;
                    boolean z = currentChat.banned_rights != null && currentChat.banned_rights.send_stickers;
                    emojiView.setStickersBanned(z, currentChat.id);
                }
            }
        }
    }

    public void checkRoundVideo() {
        boolean z = true;
        if (!this.hasRecordVideo) {
            if (this.attachLayout == null || VERSION.SDK_INT < 18) {
                this.hasRecordVideo = false;
                setRecordVideoButtonVisible(false, false);
                return;
            }
            int i = (int) (this.dialog_id >> 32);
            if (((int) this.dialog_id) != 0 || i == 0) {
                this.hasRecordVideo = true;
            } else if (AndroidUtilities.getPeerLayerVersion(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i)).layer) >= 66) {
                this.hasRecordVideo = true;
            }
            if (((int) this.dialog_id) < 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                if (!ChatObject.isChannel(chat) || chat.megagroup) {
                    z = false;
                }
                if (z && !chat.creator && (chat.admin_rights == null || !chat.admin_rights.post_messages)) {
                    this.hasRecordVideo = false;
                }
            } else {
                z = false;
            }
            if (!MediaController.getInstance().canInAppCamera()) {
                this.hasRecordVideo = false;
            }
            if (this.hasRecordVideo) {
                Long valueOf = Long.valueOf(System.currentTimeMillis());
                CameraController.getInstance().initCamera();
                Log.d("alireza", "alireza end Time : " + (System.currentTimeMillis() - valueOf.longValue()));
                setRecordVideoButtonVisible(ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean(z ? "currentModeVideoChannel" : "currentModeVideo", z), false);
                return;
            }
            setRecordVideoButtonVisible(false, false);
        }
    }

    public void closeKeyboard() {
        AndroidUtilities.hideKeyboard(this.messageEditText);
    }

    public void didPressedBotButton(final KeyboardButton keyboardButton, MessageObject messageObject, final MessageObject messageObject2) {
        if (keyboardButton != null && messageObject2 != null) {
            if (keyboardButton instanceof TLRPC$TL_keyboardButton) {
                SendMessagesHelper.getInstance().sendMessage(keyboardButton.text, this.dialog_id, messageObject, null, false, null, null, null);
            } else if (keyboardButton instanceof TLRPC$TL_keyboardButtonUrl) {
                this.parentFragment.showOpenUrlAlert(keyboardButton.url, true);
            } else if (keyboardButton instanceof TLRPC$TL_keyboardButtonRequestPhone) {
                this.parentFragment.shareMyContact(messageObject2);
            } else if (keyboardButton instanceof TLRPC$TL_keyboardButtonRequestGeoLocation) {
                Builder builder = new Builder(this.parentActivity);
                builder.setTitle(LocaleController.getString("ShareYouLocationTitle", R.string.ShareYouLocationTitle));
                builder.setMessage(LocaleController.getString("ShareYouLocationInfo", R.string.ShareYouLocationInfo));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (VERSION.SDK_INT < 23 || ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                            SendMessagesHelper.getInstance().sendCurrentLocation(messageObject2, keyboardButton);
                            return;
                        }
                        ChatActivityEnterView.this.parentActivity.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
                        ChatActivityEnterView.this.pendingMessageObject = messageObject2;
                        ChatActivityEnterView.this.pendingLocationButton = keyboardButton;
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                this.parentFragment.showDialog(builder.create());
            } else if ((keyboardButton instanceof TLRPC$TL_keyboardButtonCallback) || (keyboardButton instanceof TLRPC$TL_keyboardButtonGame) || (keyboardButton instanceof TLRPC$TL_keyboardButtonBuy)) {
                SendMessagesHelper.getInstance().sendCallback(true, messageObject2, keyboardButton, this.parentFragment);
            } else if ((keyboardButton instanceof TLRPC$TL_keyboardButtonSwitchInline) && !this.parentFragment.processSwitchButton((TLRPC$TL_keyboardButtonSwitchInline) keyboardButton)) {
                if (keyboardButton.same_peer) {
                    int i = messageObject2.messageOwner.from_id;
                    if (messageObject2.messageOwner.via_bot_id != 0) {
                        i = messageObject2.messageOwner.via_bot_id;
                    }
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                    if (user != null) {
                        setFieldText("@" + user.username + " " + keyboardButton.query);
                        return;
                    }
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putBoolean("onlySelect", true);
                bundle.putInt("dialogsType", 1);
                BaseFragment dialogsActivity = new DialogsActivity(bundle);
                dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                    public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                        int i = messageObject2.messageOwner.from_id;
                        if (messageObject2.messageOwner.via_bot_id != 0) {
                            i = messageObject2.messageOwner.via_bot_id;
                        }
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(i));
                        if (user == null) {
                            dialogsActivity.finishFragment();
                            return;
                        }
                        long longValue = ((Long) arrayList.get(0)).longValue();
                        DraftQuery.saveDraft(longValue, "@" + user.username + " " + keyboardButton.query, null, null, true);
                        if (longValue != ChatActivityEnterView.this.dialog_id) {
                            i = (int) longValue;
                            if (i != 0) {
                                Bundle bundle = new Bundle();
                                if (i > 0) {
                                    bundle.putInt("user_id", i);
                                } else if (i < 0) {
                                    bundle.putInt("chat_id", -i);
                                }
                                if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                                    if (!ChatActivityEnterView.this.parentFragment.presentFragment(new ChatActivity(bundle), true)) {
                                        dialogsActivity.finishFragment();
                                        return;
                                    } else if (!AndroidUtilities.isTablet()) {
                                        ChatActivityEnterView.this.parentFragment.removeSelfFromStack();
                                        return;
                                    } else {
                                        return;
                                    }
                                }
                                return;
                            }
                            dialogsActivity.finishFragment();
                            return;
                        }
                        dialogsActivity.finishFragment();
                    }
                });
                this.parentFragment.presentFragment(dialogsActivity);
            }
        }
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.emojiDidLoaded) {
            if (this.emojiView != null) {
                this.emojiView.invalidateViews();
            }
            if (this.botKeyboardView != null) {
                this.botKeyboardView.invalidateViews();
            }
        } else if (i == NotificationCenter.recordProgressChanged) {
            long longValue = ((Long) objArr[0]).longValue();
            long j = longValue / 1000;
            int i3 = ((int) (longValue % 1000)) / 10;
            CharSequence format = String.format("%02d:%02d.%02d", new Object[]{Long.valueOf(j / 60), Long.valueOf(j % 60), Integer.valueOf(i3)});
            if (this.lastTimeString == null || !this.lastTimeString.equals(format)) {
                if (this.lastTypingSendTime != j && j % 5 == 0) {
                    this.lastTypingSendTime = j;
                    MessagesController instance = MessagesController.getInstance();
                    long j2 = this.dialog_id;
                    i3 = (this.videoSendButton == null || this.videoSendButton.getTag() == null) ? 1 : 7;
                    instance.sendTyping(j2, i3, 0);
                }
                if (this.recordTimeText != null) {
                    this.recordTimeText.setText(format);
                }
            }
            if (this.recordCircle != null) {
                this.recordCircle.setAmplitude(((Double) objArr[1]).doubleValue());
            }
            if (this.videoSendButton != null && this.videoSendButton.getTag() != null && longValue >= 59500) {
                this.startedDraggingX = -1.0f;
                this.delegate.needStartRecordVideo(3);
            }
        } else if (i == NotificationCenter.closeChats) {
            if (this.messageEditText != null && this.messageEditText.isFocused()) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
        } else if (i == NotificationCenter.recordStartError || i == NotificationCenter.recordStopped) {
            if (this.recordingAudioVideo) {
                MessagesController.getInstance().sendTyping(this.dialog_id, 2, 0);
                this.recordingAudioVideo = false;
                updateRecordIntefrace();
            }
            if (i == NotificationCenter.recordStopped) {
                r0 = (Integer) objArr[0];
                if (r0.intValue() == 2) {
                    this.videoTimelineView.setVisibility(0);
                    this.recordedAudioBackground.setVisibility(8);
                    this.recordedAudioTimeTextView.setVisibility(8);
                    this.recordedAudioPlayButton.setVisibility(8);
                    this.recordedAudioSeekBar.setVisibility(8);
                    this.recordedAudioPanel.setAlpha(1.0f);
                    this.recordedAudioPanel.setVisibility(0);
                } else if (r0.intValue() != 1) {
                }
            }
        } else if (i == NotificationCenter.recordStarted) {
            if (!this.recordingAudioVideo) {
                this.recordingAudioVideo = true;
                updateRecordIntefrace();
            }
        } else if (i == NotificationCenter.audioDidSent) {
            Object obj = objArr[0];
            if (obj instanceof VideoEditedInfo) {
                this.videoToSendMessageObject = (VideoEditedInfo) obj;
                this.audioToSendPath = (String) objArr[1];
                this.videoTimelineView.setVideoPath(this.audioToSendPath);
                this.videoTimelineView.setVisibility(0);
                this.videoTimelineView.setMinProgressDiff(1000.0f / ((float) this.videoToSendMessageObject.estimatedDuration));
                this.recordedAudioBackground.setVisibility(8);
                this.recordedAudioTimeTextView.setVisibility(8);
                this.recordedAudioPlayButton.setVisibility(8);
                this.recordedAudioSeekBar.setVisibility(8);
                this.recordedAudioPanel.setAlpha(1.0f);
                this.recordedAudioPanel.setVisibility(0);
                closeKeyboard();
                hidePopup(false);
                checkSendButton(false);
                return;
            }
            this.audioToSend = (TLRPC$TL_document) objArr[0];
            this.audioToSendPath = (String) objArr[1];
            if (this.audioToSend != null) {
                if (this.recordedAudioPanel != null) {
                    int i4;
                    DocumentAttribute documentAttribute;
                    this.videoTimelineView.setVisibility(8);
                    this.recordedAudioBackground.setVisibility(0);
                    this.recordedAudioTimeTextView.setVisibility(0);
                    this.recordedAudioPlayButton.setVisibility(0);
                    this.recordedAudioSeekBar.setVisibility(0);
                    Message tLRPC$TL_message = new TLRPC$TL_message();
                    tLRPC$TL_message.out = true;
                    tLRPC$TL_message.id = 0;
                    tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
                    Peer peer = tLRPC$TL_message.to_id;
                    int clientUserId = UserConfig.getClientUserId();
                    tLRPC$TL_message.from_id = clientUserId;
                    peer.user_id = clientUserId;
                    tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
                    tLRPC$TL_message.message = "-1";
                    tLRPC$TL_message.attachPath = this.audioToSendPath;
                    tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
                    MessageMedia messageMedia = tLRPC$TL_message.media;
                    messageMedia.flags |= 3;
                    tLRPC$TL_message.media.document = this.audioToSend;
                    tLRPC$TL_message.flags |= 768;
                    this.audioToSendMessageObject = new MessageObject(tLRPC$TL_message, null, false);
                    this.recordedAudioPanel.setAlpha(1.0f);
                    this.recordedAudioPanel.setVisibility(0);
                    for (i4 = 0; i4 < this.audioToSend.attributes.size(); i4++) {
                        documentAttribute = (DocumentAttribute) this.audioToSend.attributes.get(i4);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            i4 = documentAttribute.duration;
                            break;
                        }
                    }
                    i4 = 0;
                    for (clientUserId = 0; clientUserId < this.audioToSend.attributes.size(); clientUserId++) {
                        documentAttribute = (DocumentAttribute) this.audioToSend.attributes.get(clientUserId);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            if (documentAttribute.waveform == null || documentAttribute.waveform.length == 0) {
                                documentAttribute.waveform = MediaController.getInstance().getWaveform(this.audioToSendPath);
                            }
                            this.recordedAudioSeekBar.setWaveform(documentAttribute.waveform);
                            this.recordedAudioTimeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(i4 / 60), Integer.valueOf(i4 % 60)}));
                            closeKeyboard();
                            hidePopup(false);
                            checkSendButton(false);
                        }
                    }
                    this.recordedAudioTimeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(i4 / 60), Integer.valueOf(i4 % 60)}));
                    closeKeyboard();
                    hidePopup(false);
                    checkSendButton(false);
                }
            } else if (this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
        } else if (i == NotificationCenter.audioRouteChanged) {
            if (this.parentActivity != null) {
                boolean booleanValue = ((Boolean) objArr[0]).booleanValue();
                Activity activity = this.parentActivity;
                if (!booleanValue) {
                    i2 = Integer.MIN_VALUE;
                }
                activity.setVolumeControlStream(i2);
            }
        } else if (i == NotificationCenter.messagePlayingDidReset) {
            if (this.audioToSendMessageObject != null && !MediaController.getInstance().isPlayingMessage(this.audioToSendMessageObject)) {
                this.recordedAudioPlayButton.setImageDrawable(this.playDrawable);
                this.recordedAudioSeekBar.setProgress(BitmapDescriptorFactory.HUE_RED);
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            r0 = (Integer) objArr[0];
            if (this.audioToSendMessageObject != null && MediaController.getInstance().isPlayingMessage(this.audioToSendMessageObject)) {
                MessageObject playingMessageObject = MediaController.getInstance().getPlayingMessageObject();
                this.audioToSendMessageObject.audioProgress = playingMessageObject.audioProgress;
                this.audioToSendMessageObject.audioProgressSec = playingMessageObject.audioProgressSec;
                if (!this.recordedAudioSeekBar.isDragging()) {
                    this.recordedAudioSeekBar.setProgress(this.audioToSendMessageObject.audioProgress);
                }
            }
        } else if (i == NotificationCenter.featuredStickersDidLoaded && this.emojiButton != null) {
            this.emojiButton.invalidate();
        }
    }

    public void doneEditingMessage() {
        if (this.editingMessageObject != null) {
            this.delegate.onMessageEditEnd(true);
            showEditDoneProgress(true, true);
            CharSequence[] charSequenceArr = new CharSequence[]{this.messageEditText.getText()};
            this.editingMessageReqId = SendMessagesHelper.getInstance().editMessage(this.editingMessageObject, charSequenceArr[0].toString(), this.messageWebPageSearch, this.parentFragment, MessagesQuery.getEntities(charSequenceArr), new Runnable() {
                public void run() {
                    ChatActivityEnterView.this.editingMessageReqId = 0;
                    ChatActivityEnterView.this.setEditingMessageObject(null, false);
                }
            });
        }
    }

    protected boolean drawChild(Canvas canvas, View view, long j) {
        if (view == this.topView) {
            canvas.save();
            canvas.clipRect(0, 0, getMeasuredWidth(), view.getLayoutParams().height + AndroidUtilities.dp(2.0f));
        }
        boolean drawChild = super.drawChild(canvas, view, j);
        if (view == this.topView) {
            canvas.restore();
        }
        return drawChild;
    }

    public ImageView getAttachButton() {
        return this.attachButton;
    }

    public ImageView getBotButton() {
        return this.botButton;
    }

    public int getCursorPosition() {
        return this.messageEditText == null ? 0 : this.messageEditText.getSelectionStart();
    }

    public MessageObject getEditingMessageObject() {
        return this.editingMessageObject;
    }

    public ImageView getEmojiButton() {
        return this.emojiButton;
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    public EmojiView getEmojiView() {
        return this.emojiView;
    }

    public CharSequence getFieldText() {
        return (this.messageEditText == null || this.messageEditText.length() <= 0) ? null : this.messageEditText.getText();
    }

    public int getSelectionLength() {
        int i = 0;
        if (this.messageEditText == null) {
            return i;
        }
        try {
            return this.messageEditText.getSelectionEnd() - this.messageEditText.getSelectionStart();
        } catch (Throwable e) {
            FileLog.e(e);
            return i;
        }
    }

    public ImageView getSendButton() {
        return this.sendButton;
    }

    public boolean hasAudioToSend() {
        return (this.audioToSendMessageObject == null && this.videoToSendMessageObject == null) ? false : true;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean hasRecordVideo() {
        return this.hasRecordVideo;
    }

    public boolean hasText() {
        return this.messageEditText != null && this.messageEditText.length() > 0;
    }

    public void hidePopup(boolean z) {
        if (isPopupShowing()) {
            if (this.currentPopupContentType == 1 && z && this.botButtonsMessageObject != null) {
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("hidekeyboard_" + this.dialog_id, this.botButtonsMessageObject.getId()).commit();
            }
            showPopup(0, 0);
            removeGifFromInputField();
        }
    }

    public void hideTopView(boolean z) {
        if (this.topView != null && this.topViewShowed) {
            this.topViewShowed = false;
            this.needShowTopView = false;
            if (this.allowShowTopView) {
                if (this.currentTopViewAnimation != null) {
                    this.currentTopViewAnimation.cancel();
                    this.currentTopViewAnimation = null;
                }
                if (z) {
                    this.currentTopViewAnimation = new AnimatorSet();
                    AnimatorSet animatorSet = this.currentTopViewAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.topView, "translationY", new float[]{(float) this.topView.getLayoutParams().height});
                    animatorSet.playTogether(animatorArr);
                    this.currentTopViewAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationCancel(Animator animator) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animator)) {
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }

                        public void onAnimationEnd(Animator animator) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animator)) {
                                ChatActivityEnterView.this.topView.setVisibility(8);
                                ChatActivityEnterView.this.resizeForTopView(false);
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }
                    });
                    this.currentTopViewAnimation.setDuration(200);
                    this.currentTopViewAnimation.start();
                    return;
                }
                this.topView.setVisibility(8);
                resizeForTopView(false);
                this.topView.setTranslationY((float) this.topView.getLayoutParams().height);
            }
        }
    }

    public boolean isEditingCaption() {
        return this.editingCaption;
    }

    public boolean isEditingMessage() {
        return this.editingMessageObject != null;
    }

    public boolean isInVideoMode() {
        return this.videoSendButton.getTag() != null;
    }

    public boolean isKeyboardVisible() {
        return this.keyboardVisible;
    }

    public boolean isMessageWebPageSearchEnabled() {
        return this.messageWebPageSearch;
    }

    public boolean isPopupShowing() {
        return (this.emojiView != null && this.emojiView.getVisibility() == 0) || (this.botKeyboardView != null && this.botKeyboardView.getVisibility() == 0);
    }

    public boolean isPopupView(View view) {
        return view == this.botKeyboardView || view == this.emojiView;
    }

    public boolean isRecordCircle(View view) {
        return view == this.recordCircle;
    }

    public boolean isRecordLocked() {
        return this.recordingAudioVideo && this.recordCircle.isSendButtonVisible();
    }

    public boolean isRecordingAudioVideo() {
        return this.recordingAudioVideo;
    }

    public boolean isSendButtonVisible() {
        return this.sendButton.getVisibility() == 0;
    }

    public boolean isTopViewVisible() {
        return this.topView != null && this.topView.getVisibility() == 0;
    }

    public void onDestroy() {
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recordStarted);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recordStartError);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recordStopped);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.recordProgressChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.audioDidSent);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.audioRouteChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
        if (this.emojiView != null) {
            this.emojiView.onDestroy();
        }
        if (this.wakeLock != null) {
            try {
                this.wakeLock.release();
                this.wakeLock = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
        if (this.sizeNotifierLayout != null) {
            this.sizeNotifierLayout.setDelegate(null);
        }
    }

    protected void onDraw(Canvas canvas) {
        int translationY = (this.topView == null || this.topView.getVisibility() != 0) ? 0 : (int) this.topView.getTranslationY();
        int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight() + translationY;
        Theme.chat_composeShadowDrawable.setBounds(0, translationY, getMeasuredWidth(), intrinsicHeight);
        Theme.chat_composeShadowDrawable.draw(canvas);
        canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
    }

    public void onEditTimeExpired() {
        this.doneButtonContainer.setVisibility(8);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.recordingAudioVideo) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void onPause() {
        this.isPaused = true;
        closeKeyboard();
    }

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 2 && this.pendingLocationButton != null) {
            if (iArr.length > 0 && iArr[0] == 0) {
                SendMessagesHelper.getInstance().sendCurrentLocation(this.pendingMessageObject, this.pendingLocationButton);
            }
            this.pendingLocationButton = null;
            this.pendingMessageObject = null;
        }
    }

    public void onResult(Object obj, int i) {
        switch (i) {
            case -29:
                if (this.dialogLoading != null) {
                    this.dialogLoading.dismiss();
                    return;
                }
                return;
            case 29:
                if (this.dialogLoading != null) {
                    this.dialogLoading.dismiss();
                }
                org.telegram.customization.Model.Payment.User user = (org.telegram.customization.Model.Payment.User) obj;
                Bundle bundle = new Bundle();
                if (user.getStatus() == 81) {
                    bundle.putInt("user_status", 81);
                    this.parentFragment.presentFragment(new C2557f(bundle));
                    return;
                } else if (user.getStatus() == 80) {
                    bundle.putInt("user_status", 80);
                    this.parentFragment.presentFragment(new C2557f(bundle));
                    return;
                } else if (user.getStatus() == 101) {
                    showPaymentSheet1();
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void onResume() {
        this.isPaused = false;
        if (this.showKeyboardOnResume) {
            this.showKeyboardOnResume = false;
            this.messageEditText.requestFocus();
            AndroidUtilities.showKeyboard(this.messageEditText);
            if (!AndroidUtilities.usingHardwareInput && !this.keyboardVisible && !AndroidUtilities.isInMultiwindow) {
                this.waitingForKeyboardOpen = true;
                AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
                AndroidUtilities.runOnUIThread(this.openKeyboardRunnable, 100);
            }
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 && this.stickersExpanded) {
            setStickersExpanded(false, false);
        }
        this.videoTimelineView.clearFrames();
    }

    public void onSizeChanged(int i, boolean z) {
        if (i > AndroidUtilities.dp(50.0f) && this.keyboardVisible && !AndroidUtilities.isInMultiwindow) {
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
            int dp = AndroidUtilities.dp((float) ((ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("emojiPopupSize", 60) - 40) * 10));
            if (dp >= i2) {
                i2 = dp;
            }
            dp = (this.currentPopupContentType != 1 || this.botKeyboardView.isFullSize()) ? i2 : Math.min(this.botKeyboardView.getKeyboardHeight(), i2);
            View view = this.currentPopupContentType == 0 ? this.emojiView : this.currentPopupContentType == 1 ? this.botKeyboardView : null;
            if (this.botKeyboardView != null) {
                this.botKeyboardView.setPanelHeight(dp);
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            if (!((layoutParams.width == AndroidUtilities.displaySize.x && layoutParams.height == dp) || this.stickersExpanded)) {
                layoutParams.width = AndroidUtilities.displaySize.x;
                layoutParams.height = dp;
                view.setLayoutParams(layoutParams);
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
            showPopup(0, this.currentPopupContentType);
        }
        if (!(this.emojiPadding == 0 || this.keyboardVisible || this.keyboardVisible == z2 || isPopupShowing())) {
            this.emojiPadding = 0;
            this.sizeNotifierLayout.requestLayout();
        }
        if (this.keyboardVisible && this.waitingForKeyboardOpen) {
            this.waitingForKeyboardOpen = false;
            AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
        }
        onWindowSizeChanged();
    }

    public void onStickerSelected(Document document) {
        SendMessagesHelper.getInstance().sendSticker(document, this.dialog_id, this.replyingMessageObject, getContext());
        if (this.delegate != null) {
            this.delegate.onMessageSend(null);
        }
    }

    public void openKeyboard() {
        AndroidUtilities.showKeyboard(this.messageEditText);
    }

    public boolean processSendingText(CharSequence charSequence) {
        CharSequence trimmedString = AndroidUtilities.getTrimmedString(charSequence);
        if (trimmedString.length() == 0) {
            return false;
        }
        int ceil = (int) Math.ceil((double) (((float) trimmedString.length()) / 4096.0f));
        for (int i = 0; i < ceil; i++) {
            CharSequence[] charSequenceArr = new CharSequence[]{trimmedString.subSequence(i * 4096, Math.min((i + 1) * 4096, trimmedString.length()))};
            SendMessagesHelper.getInstance().sendMessage(charSequenceArr[0].toString(), this.dialog_id, this.replyingMessageObject, this.messageWebPage, this.messageWebPageSearch, MessagesQuery.getEntities(charSequenceArr), null, null);
        }
        return true;
    }

    public void replaceWithText(int i, int i2, CharSequence charSequence, boolean z) {
        try {
            CharSequence spannableStringBuilder = new SpannableStringBuilder(this.messageEditText.getText());
            spannableStringBuilder.replace(i, i + i2, charSequence);
            if (z) {
                Emoji.replaceEmoji(spannableStringBuilder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            }
            this.messageEditText.setText(spannableStringBuilder);
            this.messageEditText.setSelection(charSequence.length() + i);
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    public void setAllowStickersAndGifs(boolean z, boolean z2) {
        if (!((this.allowStickers == z && this.allowGifs == z2) || this.emojiView == null)) {
            if (this.emojiView.getVisibility() == 0) {
                hidePopup(false);
            }
            this.sizeNotifierLayout.removeView(this.emojiView);
            this.emojiView = null;
        }
        this.allowStickers = z;
        this.allowGifs = z2;
        setEmojiButtonImage();
    }

    public void setBotsCount(int i, boolean z) {
        this.botCount = i;
        if (this.hasBotCommands != z) {
            this.hasBotCommands = z;
            updateBotButton();
        }
    }

    public void setButtons(MessageObject messageObject) {
        setButtons(messageObject, true);
    }

    public void setButtons(MessageObject messageObject, boolean z) {
        if (this.replyingMessageObject != null && this.replyingMessageObject == this.botButtonsMessageObject && this.replyingMessageObject != messageObject) {
            this.botMessageObject = messageObject;
        } else if (this.botButton == null) {
        } else {
            if (this.botButtonsMessageObject != null && this.botButtonsMessageObject == messageObject) {
                return;
            }
            if (this.botButtonsMessageObject != null || messageObject != null) {
                if (this.botKeyboardView == null) {
                    this.botKeyboardView = new BotKeyboardView(this.parentActivity);
                    this.botKeyboardView.setVisibility(8);
                    this.botKeyboardView.setDelegate(new BotKeyboardViewDelegate() {
                        public void didPressedButton(KeyboardButton keyboardButton) {
                            MessageObject access$2100 = ChatActivityEnterView.this.replyingMessageObject != null ? ChatActivityEnterView.this.replyingMessageObject : ((int) ChatActivityEnterView.this.dialog_id) < 0 ? ChatActivityEnterView.this.botButtonsMessageObject : null;
                            ChatActivityEnterView.this.didPressedBotButton(keyboardButton, access$2100, ChatActivityEnterView.this.replyingMessageObject != null ? ChatActivityEnterView.this.replyingMessageObject : ChatActivityEnterView.this.botButtonsMessageObject);
                            if (ChatActivityEnterView.this.replyingMessageObject != null) {
                                ChatActivityEnterView.this.openKeyboardInternal();
                                ChatActivityEnterView.this.setButtons(ChatActivityEnterView.this.botMessageObject, false);
                            } else if (ChatActivityEnterView.this.botButtonsMessageObject.messageOwner.reply_markup.single_use) {
                                ChatActivityEnterView.this.openKeyboardInternal();
                                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("answered_" + ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.botButtonsMessageObject.getId()).commit();
                            }
                            if (ChatActivityEnterView.this.delegate != null) {
                                ChatActivityEnterView.this.delegate.onMessageSend(null);
                            }
                        }
                    });
                    this.sizeNotifierLayout.addView(this.botKeyboardView);
                }
                this.botButtonsMessageObject = messageObject;
                TLRPC$TL_replyKeyboardMarkup tLRPC$TL_replyKeyboardMarkup = (messageObject == null || !(messageObject.messageOwner.reply_markup instanceof TLRPC$TL_replyKeyboardMarkup)) ? null : (TLRPC$TL_replyKeyboardMarkup) messageObject.messageOwner.reply_markup;
                this.botReplyMarkup = tLRPC$TL_replyKeyboardMarkup;
                this.botKeyboardView.setPanelHeight(AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight);
                this.botKeyboardView.setButtons(this.botReplyMarkup);
                if (this.botReplyMarkup != null) {
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    int i = sharedPreferences.getInt(new StringBuilder().append("hidekeyboard_").append(this.dialog_id).toString(), 0) == messageObject.getId() ? 1 : 0;
                    if (this.botButtonsMessageObject == this.replyingMessageObject || !this.botReplyMarkup.single_use || sharedPreferences.getInt("answered_" + this.dialog_id, 0) != messageObject.getId()) {
                        if (i == 0 && this.messageEditText.length() == 0 && !isPopupShowing()) {
                            showPopup(1, 1);
                        }
                    } else {
                        return;
                    }
                } else if (isPopupShowing() && this.currentPopupContentType == 1) {
                    if (z) {
                        openKeyboardInternal();
                    } else {
                        showPopup(0, 1);
                    }
                }
                updateBotButton();
            }
        }
    }

    public void setCaption(String str) {
        if (this.messageEditText != null) {
            this.messageEditText.setCaption(str);
            checkSendButton(true);
        }
    }

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        if (this.emojiView != null) {
            this.emojiView.setChatInfo(this.info);
        }
    }

    public void setCommand(MessageObject messageObject, String str, boolean z, boolean z2) {
        User user = null;
        if (str != null && getVisibility() == 0) {
            if (z) {
                String obj = this.messageEditText.getText().toString();
                if (messageObject != null && ((int) this.dialog_id) < 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
                }
                CharSequence charSequence = ((this.botCount != 1 || z2) && user != null && user.bot && !str.contains("@")) ? String.format(Locale.US, "%s@%s", new Object[]{str, user.username}) + " " + obj.replaceFirst("^/[a-zA-Z@\\d_]{1,255}(\\s|$)", TtmlNode.ANONYMOUS_REGION_ID) : str + " " + obj.replaceFirst("^/[a-zA-Z@\\d_]{1,255}(\\s|$)", TtmlNode.ANONYMOUS_REGION_ID);
                this.ignoreTextChange = true;
                this.messageEditText.setText(charSequence);
                this.messageEditText.setSelection(this.messageEditText.getText().length());
                this.ignoreTextChange = false;
                if (this.delegate != null) {
                    this.delegate.onTextChanged(this.messageEditText.getText(), true);
                }
                if (!this.keyboardVisible && this.currentPopupContentType == -1) {
                    openKeyboard();
                    return;
                }
                return;
            }
            User user2 = (messageObject == null || ((int) this.dialog_id) >= 0) ? null : MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
            if ((this.botCount != 1 || z2) && user2 != null && user2.bot && !str.contains("@")) {
                SendMessagesHelper.getInstance().sendMessage(String.format(Locale.US, "%s@%s", new Object[]{str, user2.username}), this.dialog_id, this.replyingMessageObject, null, false, null, null, null);
            } else {
                SendMessagesHelper.getInstance().sendMessage(str, this.dialog_id, this.replyingMessageObject, null, false, null, null, null);
            }
        }
    }

    public void setDelegate(ChatActivityEnterViewDelegate chatActivityEnterViewDelegate) {
        this.delegate = chatActivityEnterViewDelegate;
    }

    public void setDialogId(long j) {
        int i = 1;
        this.dialog_id = j;
        int i2 = (int) this.dialog_id;
        i2 = (int) (this.dialog_id >> 32);
        if (((int) this.dialog_id) < 0) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
            this.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + this.dialog_id, false);
            boolean z = ChatObject.isChannel(chat) && ((chat.creator || (chat.admin_rights != null && chat.admin_rights.post_messages)) && !chat.megagroup);
            this.canWriteToChannel = z;
            if (this.notifyButton != null) {
                this.notifyButton.setVisibility(this.canWriteToChannel ? 0 : 8);
                this.notifyButton.setImageResource(this.silent ? R.drawable.notify_members_off : R.drawable.notify_members_on);
                LinearLayout linearLayout = this.attachLayout;
                float f = ((this.botButton == null || this.botButton.getVisibility() == 8) && (this.notifyButton == null || this.notifyButton.getVisibility() == 8)) ? 48.0f : 96.0f;
                linearLayout.setPivotX((float) AndroidUtilities.dp(f));
            }
            if (this.attachLayout != null) {
                if (this.attachLayout.getVisibility() != 0) {
                    i = 0;
                }
                updateFieldRight(i);
            }
        }
        checkRoundVideo();
        updateFieldHint();
    }

    public void setEditingMessageObject(MessageObject messageObject, boolean z) {
        if (this.audioToSend == null && this.videoToSendMessageObject == null && this.editingMessageObject != messageObject) {
            if (this.editingMessageReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.editingMessageReqId, true);
                this.editingMessageReqId = 0;
            }
            this.editingMessageObject = messageObject;
            this.editingCaption = z;
            if (this.editingMessageObject != null) {
                if (this.doneButtonAnimation != null) {
                    this.doneButtonAnimation.cancel();
                    this.doneButtonAnimation = null;
                }
                this.doneButtonContainer.setVisibility(0);
                showEditDoneProgress(true, false);
                InputFilter[] inputFilterArr = new InputFilter[1];
                if (z) {
                    inputFilterArr[0] = new LengthFilter(Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                    if (this.editingMessageObject.caption != null) {
                        setFieldText(Emoji.replaceEmoji(new SpannableStringBuilder(this.editingMessageObject.caption.toString()), this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false));
                    } else {
                        setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                    }
                } else {
                    inputFilterArr[0] = new LengthFilter(4096);
                    if (this.editingMessageObject.messageText != null) {
                        int i;
                        ArrayList arrayList = this.editingMessageObject.messageOwner.entities;
                        MessagesQuery.sortEntities(arrayList);
                        CharSequence spannableStringBuilder = new SpannableStringBuilder(this.editingMessageObject.messageText);
                        Object[] spans = spannableStringBuilder.getSpans(0, spannableStringBuilder.length(), Object.class);
                        if (spans != null && spans.length > 0) {
                            for (Object removeSpan : spans) {
                                spannableStringBuilder.removeSpan(removeSpan);
                            }
                        }
                        if (arrayList != null) {
                            int i2 = 0;
                            int i3 = 0;
                            while (i2 < arrayList.size()) {
                                try {
                                    MessageEntity messageEntity = (MessageEntity) arrayList.get(i2);
                                    if ((messageEntity.offset + messageEntity.length) + i3 > spannableStringBuilder.length()) {
                                        i = i3;
                                    } else if (messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                                        if ((messageEntity.offset + messageEntity.length) + i3 < spannableStringBuilder.length() && spannableStringBuilder.charAt((messageEntity.offset + messageEntity.length) + i3) == ' ') {
                                            messageEntity.length++;
                                        }
                                        spannableStringBuilder.setSpan(new URLSpanUserMention(TtmlNode.ANONYMOUS_REGION_ID + ((TLRPC$TL_inputMessageEntityMentionName) messageEntity).user_id.user_id, true), messageEntity.offset + i3, (messageEntity.length + messageEntity.offset) + i3, 33);
                                        i = i3;
                                    } else if (messageEntity instanceof TLRPC$TL_messageEntityCode) {
                                        spannableStringBuilder.insert((messageEntity.offset + messageEntity.length) + i3, "`");
                                        spannableStringBuilder.insert(messageEntity.offset + i3, "`");
                                        i = i3 + 2;
                                    } else if (messageEntity instanceof TLRPC$TL_messageEntityPre) {
                                        spannableStringBuilder.insert((messageEntity.offset + messageEntity.length) + i3, "```");
                                        spannableStringBuilder.insert(messageEntity.offset + i3, "```");
                                        i = i3 + 6;
                                    } else if (messageEntity instanceof TLRPC$TL_messageEntityBold) {
                                        spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), messageEntity.offset + i3, (messageEntity.length + messageEntity.offset) + i3, 33);
                                        i = i3;
                                    } else {
                                        if (messageEntity instanceof TLRPC$TL_messageEntityItalic) {
                                            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), messageEntity.offset + i3, (messageEntity.length + messageEntity.offset) + i3, 33);
                                        }
                                        i = i3;
                                    }
                                    i2++;
                                    i3 = i;
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            }
                        }
                        setFieldText(Emoji.replaceEmoji(spannableStringBuilder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false));
                    } else {
                        setFieldText(TtmlNode.ANONYMOUS_REGION_ID);
                    }
                }
                this.messageEditText.setFilters(inputFilterArr);
                openKeyboard();
                LayoutParams layoutParams = (LayoutParams) this.messageEditText.getLayoutParams();
                layoutParams.rightMargin = AndroidUtilities.dp(4.0f);
                this.messageEditText.setLayoutParams(layoutParams);
                this.sendButton.setVisibility(8);
                this.cancelBotButton.setVisibility(8);
                this.audioVideoButtonContainer.setVisibility(8);
                this.attachLayout.setVisibility(8);
                this.sendButtonContainer.setVisibility(8);
            } else {
                this.doneButtonContainer.setVisibility(8);
                this.messageEditText.setFilters(new InputFilter[0]);
                this.delegate.onMessageEditEnd(false);
                this.audioVideoButtonContainer.setVisibility(0);
                this.attachLayout.setVisibility(0);
                this.sendButtonContainer.setVisibility(0);
                this.attachLayout.setScaleX(1.0f);
                this.attachLayout.setAlpha(1.0f);
                this.sendButton.setScaleX(0.1f);
                this.sendButton.setScaleY(0.1f);
                this.sendButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.cancelBotButton.setScaleX(0.1f);
                this.cancelBotButton.setScaleY(0.1f);
                this.cancelBotButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.audioVideoButtonContainer.setScaleX(1.0f);
                this.audioVideoButtonContainer.setScaleY(1.0f);
                this.audioVideoButtonContainer.setAlpha(1.0f);
                this.sendButton.setVisibility(8);
                this.cancelBotButton.setVisibility(8);
                this.messageEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
                if (getVisibility() == 0) {
                    this.delegate.onAttachButtonShow();
                }
                updateFieldRight(1);
            }
            updateFieldHint();
        }
    }

    public void setFieldFocused() {
        if (this.messageEditText != null) {
            try {
                this.messageEditText.requestFocus();
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    public void setFieldFocused(boolean z) {
        if (this.messageEditText != null) {
            if (z) {
                if (!this.messageEditText.isFocused()) {
                    this.messageEditText.postDelayed(new Runnable() {
                        public void run() {
                            if (ChatActivityEnterView.this.messageEditText != null) {
                                try {
                                    ChatActivityEnterView.this.messageEditText.requestFocus();
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
            this.ignoreTextChange = true;
            this.messageEditText.setText(charSequence);
            this.messageEditText.setSelection(this.messageEditText.getText().length());
            this.ignoreTextChange = false;
            if (this.delegate != null) {
                this.delegate.onTextChanged(this.messageEditText.getText(), true);
            }
        }
    }

    public void setForceShowSendButton(boolean z, boolean z2) {
        this.forceShowSendButton = z;
        checkSendButton(z2);
    }

    public void setOpenGifsTabFirst() {
        createEmojiView();
        StickersQuery.loadRecents(0, true, true, false);
        this.emojiView.switchToGifRecent();
    }

    public void setReplyingMessageObject(MessageObject messageObject) {
        if (messageObject != null) {
            if (this.botMessageObject == null && this.botButtonsMessageObject != this.replyingMessageObject) {
                this.botMessageObject = this.botButtonsMessageObject;
            }
            this.replyingMessageObject = messageObject;
            setButtons(this.replyingMessageObject, true);
        } else if (messageObject == null && this.replyingMessageObject == this.botButtonsMessageObject) {
            this.replyingMessageObject = null;
            setButtons(this.botMessageObject, false);
            this.botMessageObject = null;
        } else {
            this.replyingMessageObject = messageObject;
        }
    }

    public void setSelection(int i) {
        if (this.messageEditText != null) {
            this.messageEditText.setSelection(i, this.messageEditText.length());
        }
    }

    public void setWebPage(TLRPC$WebPage tLRPC$WebPage, boolean z) {
        this.messageWebPage = tLRPC$WebPage;
        this.messageWebPageSearch = z;
    }

    public void showContextProgress(boolean z) {
        if (this.progressDrawable != null) {
            if (z) {
                this.progressDrawable.startAnimation();
            } else {
                this.progressDrawable.stopAnimation();
            }
        }
    }

    public void showEditDoneProgress(final boolean z, boolean z2) {
        if (this.doneButtonAnimation != null) {
            this.doneButtonAnimation.cancel();
        }
        if (z2) {
            this.doneButtonAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (z) {
                this.doneButtonProgress.setVisibility(0);
                this.doneButtonContainer.setEnabled(false);
                animatorSet = this.doneButtonAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneButtonImage, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneButtonProgress, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneButtonProgress, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneButtonProgress, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            } else {
                this.doneButtonImage.setVisibility(0);
                this.doneButtonContainer.setEnabled(true);
                animatorSet = this.doneButtonAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneButtonProgress, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneButtonProgress, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneButtonProgress, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneButtonImage, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneButtonAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationCancel(Animator animator) {
                    if (ChatActivityEnterView.this.doneButtonAnimation != null && ChatActivityEnterView.this.doneButtonAnimation.equals(animator)) {
                        ChatActivityEnterView.this.doneButtonAnimation = null;
                    }
                }

                public void onAnimationEnd(Animator animator) {
                    if (ChatActivityEnterView.this.doneButtonAnimation != null && ChatActivityEnterView.this.doneButtonAnimation.equals(animator)) {
                        if (z) {
                            ChatActivityEnterView.this.doneButtonImage.setVisibility(4);
                        } else {
                            ChatActivityEnterView.this.doneButtonProgress.setVisibility(4);
                        }
                    }
                }
            });
            this.doneButtonAnimation.setDuration(150);
            this.doneButtonAnimation.start();
        } else if (z) {
            this.doneButtonImage.setScaleX(0.1f);
            this.doneButtonImage.setScaleY(0.1f);
            this.doneButtonImage.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.doneButtonProgress.setScaleX(1.0f);
            this.doneButtonProgress.setScaleY(1.0f);
            this.doneButtonProgress.setAlpha(1.0f);
            this.doneButtonImage.setVisibility(4);
            this.doneButtonProgress.setVisibility(0);
            this.doneButtonContainer.setEnabled(false);
        } else {
            this.doneButtonProgress.setScaleX(0.1f);
            this.doneButtonProgress.setScaleY(0.1f);
            this.doneButtonProgress.setAlpha(BitmapDescriptorFactory.HUE_RED);
            this.doneButtonImage.setScaleX(1.0f);
            this.doneButtonImage.setScaleY(1.0f);
            this.doneButtonImage.setAlpha(1.0f);
            this.doneButtonImage.setVisibility(0);
            this.doneButtonProgress.setVisibility(4);
            this.doneButtonContainer.setEnabled(true);
        }
    }

    public void showTopView(boolean z, final boolean z2) {
        if (this.topView != null && !this.topViewShowed && getVisibility() == 0) {
            this.needShowTopView = true;
            this.topViewShowed = true;
            if (this.allowShowTopView) {
                this.topView.setVisibility(0);
                if (this.currentTopViewAnimation != null) {
                    this.currentTopViewAnimation.cancel();
                    this.currentTopViewAnimation = null;
                }
                resizeForTopView(true);
                if (!z) {
                    this.topView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    if (this.recordedAudioPanel.getVisibility() == 0) {
                        return;
                    }
                    if (!this.forceShowSendButton || z2) {
                        openKeyboard();
                    }
                } else if (this.keyboardVisible || isPopupShowing()) {
                    this.currentTopViewAnimation = new AnimatorSet();
                    AnimatorSet animatorSet = this.currentTopViewAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.topView, "translationY", new float[]{BitmapDescriptorFactory.HUE_RED});
                    animatorSet.playTogether(animatorArr);
                    this.currentTopViewAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationCancel(Animator animator) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animator)) {
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }

                        public void onAnimationEnd(Animator animator) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animator)) {
                                if (ChatActivityEnterView.this.recordedAudioPanel.getVisibility() != 0 && (!ChatActivityEnterView.this.forceShowSendButton || z2)) {
                                    ChatActivityEnterView.this.openKeyboard();
                                }
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }
                    });
                    this.currentTopViewAnimation.setDuration(200);
                    this.currentTopViewAnimation.start();
                } else {
                    this.topView.setTranslationY(BitmapDescriptorFactory.HUE_RED);
                    if (this.recordedAudioPanel.getVisibility() == 0) {
                        return;
                    }
                    if (!this.forceShowSendButton || z2) {
                        openKeyboard();
                    }
                }
            }
        }
    }
}
