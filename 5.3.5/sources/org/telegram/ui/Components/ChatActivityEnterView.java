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
import android.support.design.widget.BottomSheetDialog;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat.OnCommitContentListener;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.os.BuildCompat;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.customization.Activities.PaymentMainActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Payment.PaymentLink;
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
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$KeyboardButton;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$MessageMedia;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
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
import org.telegram.tgnet.TLRPC.DocumentAttribute;
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
import utils.app.AppPreferences;
import utils.view.Constants;
import utils.view.FarsiEditText;
import utils.view.ToastUtil;

public class ChatActivityEnterView extends FrameLayout implements NotificationCenterDelegate, SizeNotifierFrameLayoutDelegate, StickersAlertDelegate, IResponseReceiver {
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
    private TLRPC$Chat currentChat;
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
    private TLRPC$ChatFull info;
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
    private TLRPC$KeyboardButton pendingLocationButton;
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
    class C25021 implements Runnable {
        C25021() {
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
    class C25032 implements Runnable {
        private int lastKnownPage = -1;

        C25032() {
        }

        public void run() {
            if (ChatActivityEnterView.this.emojiView != null) {
                int curPage = ChatActivityEnterView.this.emojiView.getCurrentPage();
                if (curPage != this.lastKnownPage) {
                    boolean z;
                    this.lastKnownPage = curPage;
                    boolean prevOpen = ChatActivityEnterView.this.stickersTabOpen;
                    ChatActivityEnterView chatActivityEnterView = ChatActivityEnterView.this;
                    if (curPage == 1 || curPage == 2) {
                        z = true;
                    } else {
                        z = false;
                    }
                    chatActivityEnterView.stickersTabOpen = z;
                    if (prevOpen != ChatActivityEnterView.this.stickersTabOpen) {
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
    class C25074 implements Runnable {
        C25074() {
        }

        public void run() {
            if (ChatActivityEnterView.this.delegate != null && ChatActivityEnterView.this.parentActivity != null) {
                ChatActivityEnterView.this.delegate.onPreAudioVideoRecord();
                ChatActivityEnterView.this.calledRecordRunnable = true;
                ChatActivityEnterView.this.recordAudioVideoRunnableStarted = false;
                ChatActivityEnterView.this.recordCircle.setLockTranslation(10000.0f);
                ChatActivityEnterView.this.recordSendText.setAlpha(0.0f);
                ChatActivityEnterView.this.slideText.setAlpha(1.0f);
                ChatActivityEnterView.this.slideText.setTranslationY(0.0f);
                if (ChatActivityEnterView.this.videoSendButton == null || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        if (VERSION.SDK_INT < 23 || ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                            String action;
                            if (((int) ChatActivityEnterView.this.dialog_id) < 0) {
                                TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) ChatActivityEnterView.this.dialog_id)));
                                if (currentChat == null || currentChat.participants_count <= MessagesController.getInstance().groupBigSize) {
                                    action = "chat_upload_audio";
                                } else {
                                    action = "bigchat_upload_audio";
                                }
                            } else {
                                action = "pm_upload_audio";
                            }
                            if (!MessagesController.isFeatureEnabled(action, ChatActivityEnterView.this.parentFragment)) {
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
                    boolean hasAudio;
                    if (ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.RECORD_AUDIO") == 0) {
                        hasAudio = true;
                    } else {
                        hasAudio = false;
                    }
                    boolean hasVideo;
                    if (ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.CAMERA") == 0) {
                        hasVideo = true;
                    } else {
                        hasVideo = false;
                    }
                    if (!(hasAudio && hasVideo)) {
                        int i;
                        if (hasAudio || hasVideo) {
                            i = 1;
                        } else {
                            i = 2;
                        }
                        String[] permissions = new String[i];
                        if (!hasAudio && !hasVideo) {
                            permissions[0] = "android.permission.RECORD_AUDIO";
                            permissions[1] = "android.permission.CAMERA";
                        } else if (hasAudio) {
                            permissions[0] = "android.permission.CAMERA";
                        } else {
                            permissions[0] = "android.permission.RECORD_AUDIO";
                        }
                        ChatActivityEnterView.this.parentActivity.requestPermissions(permissions, 3);
                        return;
                    }
                }
                ChatActivityEnterView.this.delegate.needStartRecordVideo(0);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$6 */
    class C25096 implements OnClickListener {
        C25096() {
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
    class C25128 implements OnKeyListener {
        boolean ctrlPressed = false;

        C25128() {
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
    class C25139 implements OnEditorActionListener {
        boolean ctrlPressed = false;

        C25139() {
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
        private float animProgress = 0.0f;
        private Paint paint = new Paint(1);
        private Path path = new Path();

        public AnimatedArrowDrawable(int color) {
            this.paint.setStyle(Style.STROKE);
            this.paint.setStrokeWidth((float) AndroidUtilities.dp(2.0f));
            this.paint.setColor(color);
            updatePath();
        }

        public void draw(Canvas c) {
            c.drawPath(this.path, this.paint);
        }

        private void updatePath() {
            this.path.reset();
            float p = (this.animProgress * 2.0f) - 1.0f;
            this.path.moveTo((float) AndroidUtilities.dp(3.0f), ((float) AndroidUtilities.dp(12.0f)) - (((float) AndroidUtilities.dp(4.0f)) * p));
            this.path.lineTo((float) AndroidUtilities.dp(13.0f), ((float) AndroidUtilities.dp(12.0f)) + (((float) AndroidUtilities.dp(4.0f)) * p));
            this.path.lineTo((float) AndroidUtilities.dp(23.0f), ((float) AndroidUtilities.dp(12.0f)) - (((float) AndroidUtilities.dp(4.0f)) * p));
        }

        public void setAnimationProgress(float progress) {
            this.animProgress = progress;
            updatePath();
            invalidateSelf();
        }

        public float getAnimationProgress() {
            return this.animProgress;
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 0;
        }

        public int getIntrinsicWidth() {
            return AndroidUtilities.dp(26.0f);
        }

        public int getIntrinsicHeight() {
            return AndroidUtilities.dp(26.0f);
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

        public void setAmplitude(double value) {
            this.animateToAmplitude = ((float) Math.min(100.0d, value)) / 100.0f;
            this.animateAmplitudeDiff = (this.animateToAmplitude - this.amplitude) / 150.0f;
            this.lastUpdateTime = System.currentTimeMillis();
            invalidate();
        }

        public float getScale() {
            return this.scale;
        }

        public void setScale(float value) {
            this.scale = value;
            invalidate();
        }

        public void setLockAnimatedTranslation(float value) {
            this.lockAnimatedTranslation = value;
            invalidate();
        }

        public float getLockAnimatedTranslation() {
            return this.lockAnimatedTranslation;
        }

        public boolean isSendButtonVisible() {
            return this.sendButtonVisible;
        }

        public void setSendButtonInvisible() {
            this.sendButtonVisible = false;
            invalidate();
        }

        public int setLockTranslation(float value) {
            if (value == 10000.0f) {
                this.sendButtonVisible = false;
                this.lockAnimatedTranslation = -1.0f;
                this.startTranslation = -1.0f;
                invalidate();
                return 0;
            } else if (this.sendButtonVisible) {
                return 2;
            } else {
                if (this.lockAnimatedTranslation == -1.0f) {
                    this.startTranslation = value;
                }
                this.lockAnimatedTranslation = value;
                invalidate();
                if (this.startTranslation - this.lockAnimatedTranslation < ((float) AndroidUtilities.dp(57.0f))) {
                    return 1;
                }
                this.sendButtonVisible = true;
                return 2;
            }
        }

        public boolean onTouchEvent(MotionEvent event) {
            if (this.sendButtonVisible) {
                int x = (int) event.getX();
                int y = (int) event.getY();
                if (event.getAction() == 0) {
                    boolean contains = ChatActivityEnterView.this.lockBackgroundDrawable.getBounds().contains(x, y);
                    this.pressed = contains;
                    if (contains) {
                        return true;
                    }
                } else if (this.pressed) {
                    if (event.getAction() != 1 || !ChatActivityEnterView.this.lockBackgroundDrawable.getBounds().contains(x, y)) {
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

        protected void onDraw(Canvas canvas) {
            float sc;
            float alpha;
            Drawable drawable;
            int lockSize;
            int lockY;
            int lockTopY;
            int lockMiddleY;
            int lockArrowY;
            int cx = getMeasuredWidth() / 2;
            int cy = AndroidUtilities.dp(170.0f);
            float yAdd = 0.0f;
            if (this.lockAnimatedTranslation != 10000.0f) {
                yAdd = (float) Math.max(0, (int) (this.startTranslation - this.lockAnimatedTranslation));
                if (yAdd > ((float) AndroidUtilities.dp(57.0f))) {
                    yAdd = (float) AndroidUtilities.dp(57.0f);
                }
            }
            cy = (int) (((float) cy) - yAdd);
            if (this.scale <= 0.5f) {
                sc = this.scale / 0.5f;
                alpha = sc;
            } else if (this.scale <= 0.75f) {
                sc = 1.0f - (((this.scale - 0.5f) / 0.25f) * 0.1f);
                alpha = 1.0f;
            } else {
                sc = 0.9f + (((this.scale - 0.75f) / 0.25f) * 0.1f);
                alpha = 1.0f;
            }
            long dt = System.currentTimeMillis() - this.lastUpdateTime;
            if (this.animateToAmplitude != this.amplitude) {
                this.amplitude += this.animateAmplitudeDiff * ((float) dt);
                if (this.animateAmplitudeDiff > 0.0f) {
                    if (this.amplitude > this.animateToAmplitude) {
                        this.amplitude = this.animateToAmplitude;
                    }
                } else if (this.amplitude < this.animateToAmplitude) {
                    this.amplitude = this.animateToAmplitude;
                }
                invalidate();
            }
            this.lastUpdateTime = System.currentTimeMillis();
            if (this.amplitude != 0.0f) {
                canvas.drawCircle(((float) getMeasuredWidth()) / 2.0f, (float) cy, (((float) AndroidUtilities.dp(42.0f)) + (((float) AndroidUtilities.dp(20.0f)) * this.amplitude)) * this.scale, ChatActivityEnterView.this.paintRecord);
            }
            canvas.drawCircle(((float) getMeasuredWidth()) / 2.0f, (float) cy, ((float) AndroidUtilities.dp(42.0f)) * sc, ChatActivityEnterView.this.paint);
            if (isSendButtonVisible()) {
                drawable = ChatActivityEnterView.this.sendDrawable;
            } else if (ChatActivityEnterView.this.videoSendButton == null || ChatActivityEnterView.this.videoSendButton.getTag() == null) {
                drawable = ChatActivityEnterView.this.micDrawable;
            } else {
                drawable = ChatActivityEnterView.this.cameraDrawable;
            }
            drawable.setBounds(cx - (drawable.getIntrinsicWidth() / 2), cy - (drawable.getIntrinsicHeight() / 2), (drawable.getIntrinsicWidth() / 2) + cx, (drawable.getIntrinsicHeight() / 2) + cy);
            drawable.setAlpha((int) (255.0f * alpha));
            drawable.draw(canvas);
            float moveProgress = 1.0f - (yAdd / ((float) AndroidUtilities.dp(57.0f)));
            float moveProgress2 = Math.max(0.0f, 1.0f - ((yAdd / ((float) AndroidUtilities.dp(57.0f))) * 2.0f));
            int intAlpha = (int) (255.0f * alpha);
            if (isSendButtonVisible()) {
                lockSize = AndroidUtilities.dp(31.0f);
                lockY = AndroidUtilities.dp(57.0f) + ((int) (((((float) AndroidUtilities.dp(30.0f)) * (1.0f - sc)) - yAdd) + (((float) AndroidUtilities.dp(20.0f)) * moveProgress)));
                lockTopY = lockY + AndroidUtilities.dp(5.0f);
                lockMiddleY = lockY + AndroidUtilities.dp(11.0f);
                lockArrowY = lockY + AndroidUtilities.dp(25.0f);
                intAlpha = (int) (((float) intAlpha) * (yAdd / ((float) AndroidUtilities.dp(57.0f))));
                ChatActivityEnterView.this.lockBackgroundDrawable.setAlpha(255);
                ChatActivityEnterView.this.lockShadowDrawable.setAlpha(255);
                ChatActivityEnterView.this.lockTopDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockArrowDrawable.setAlpha((int) (((float) intAlpha) * moveProgress2));
            } else {
                lockSize = AndroidUtilities.dp(31.0f) + ((int) (((float) AndroidUtilities.dp(29.0f)) * moveProgress));
                lockY = (AndroidUtilities.dp(57.0f) + ((int) (((float) AndroidUtilities.dp(30.0f)) * (1.0f - sc)))) - ((int) yAdd);
                lockTopY = (AndroidUtilities.dp(5.0f) + lockY) + ((int) (((float) AndroidUtilities.dp(4.0f)) * moveProgress));
                lockMiddleY = (AndroidUtilities.dp(11.0f) + lockY) + ((int) (((float) AndroidUtilities.dp(10.0f)) * moveProgress));
                lockArrowY = (AndroidUtilities.dp(25.0f) + lockY) + ((int) (((float) AndroidUtilities.dp(16.0f)) * moveProgress));
                ChatActivityEnterView.this.lockBackgroundDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockShadowDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockTopDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockDrawable.setAlpha(intAlpha);
                ChatActivityEnterView.this.lockArrowDrawable.setAlpha((int) (((float) intAlpha) * moveProgress2));
            }
            ChatActivityEnterView.this.lockBackgroundDrawable.setBounds(cx - AndroidUtilities.dp(15.0f), lockY, AndroidUtilities.dp(15.0f) + cx, lockY + lockSize);
            ChatActivityEnterView.this.lockBackgroundDrawable.draw(canvas);
            ChatActivityEnterView.this.lockShadowDrawable.setBounds(cx - AndroidUtilities.dp(16.0f), lockY - AndroidUtilities.dp(1.0f), AndroidUtilities.dp(16.0f) + cx, (lockY + lockSize) + AndroidUtilities.dp(1.0f));
            ChatActivityEnterView.this.lockShadowDrawable.draw(canvas);
            ChatActivityEnterView.this.lockTopDrawable.setBounds(cx - AndroidUtilities.dp(6.0f), lockTopY, AndroidUtilities.dp(6.0f) + cx, AndroidUtilities.dp(14.0f) + lockTopY);
            ChatActivityEnterView.this.lockTopDrawable.draw(canvas);
            ChatActivityEnterView.this.lockDrawable.setBounds(cx - AndroidUtilities.dp(7.0f), lockMiddleY, AndroidUtilities.dp(7.0f) + cx, AndroidUtilities.dp(12.0f) + lockMiddleY);
            ChatActivityEnterView.this.lockDrawable.draw(canvas);
            ChatActivityEnterView.this.lockArrowDrawable.setBounds(cx - AndroidUtilities.dp(7.5f), lockArrowY, AndroidUtilities.dp(7.5f) + cx, AndroidUtilities.dp(9.0f) + lockArrowY);
            ChatActivityEnterView.this.lockArrowDrawable.draw(canvas);
            if (isSendButtonVisible()) {
                ChatActivityEnterView.this.redDotPaint.setAlpha(255);
                ChatActivityEnterView.this.rect.set((float) (cx - AndroidUtilities.dp2(6.5f)), (float) (AndroidUtilities.dp(9.0f) + lockY), (float) (AndroidUtilities.dp(6.5f) + cx), (float) (AndroidUtilities.dp(22.0f) + lockY));
                canvas.drawRoundRect(ChatActivityEnterView.this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), ChatActivityEnterView.this.redDotPaint);
            }
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

        public void resetAlpha() {
            this.alpha = 1.0f;
            this.lastUpdateTime = System.currentTimeMillis();
            this.isIncr = false;
            invalidate();
        }

        protected void onDraw(Canvas canvas) {
            ChatActivityEnterView.this.redDotPaint.setAlpha((int) (255.0f * this.alpha));
            long dt = System.currentTimeMillis() - this.lastUpdateTime;
            if (this.isIncr) {
                this.alpha += ((float) dt) / 400.0f;
                if (this.alpha >= 1.0f) {
                    this.alpha = 1.0f;
                    this.isIncr = false;
                }
            } else {
                this.alpha -= ((float) dt) / 400.0f;
                if (this.alpha <= 0.0f) {
                    this.alpha = 0.0f;
                    this.isIncr = true;
                }
            }
            this.lastUpdateTime = System.currentTimeMillis();
            canvas.drawCircle((float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), (float) AndroidUtilities.dp(5.0f), ChatActivityEnterView.this.redDotPaint);
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
            canvas.drawRect(0.0f, 0.0f, (float) ChatActivityEnterView.this.getWidth(), (ChatActivityEnterView.this.emojiView.getY() - ((float) ChatActivityEnterView.this.getHeight())) + ((float) Theme.chat_composeShadowDrawable.getIntrinsicHeight()), this.paint);
        }

        public void setAlpha(int alpha) {
        }

        public void setColorFilter(ColorFilter colorFilter) {
        }

        public int getOpacity() {
            return 0;
        }
    }

    private class SeekBarWaveformView extends View {
        private SeekBarWaveform seekBarWaveform;

        public SeekBarWaveformView(Context context) {
            super(context);
            this.seekBarWaveform = new SeekBarWaveform(context);
            this.seekBarWaveform.setDelegate(new SeekBarDelegate(ChatActivityEnterView.this) {
                public void onSeekBarDrag(float progress) {
                    if (ChatActivityEnterView.this.audioToSendMessageObject != null) {
                        ChatActivityEnterView.this.audioToSendMessageObject.audioProgress = progress;
                        MediaController.getInstance().seekToProgress(ChatActivityEnterView.this.audioToSendMessageObject, progress);
                    }
                }
            });
        }

        public void setWaveform(byte[] waveform) {
            this.seekBarWaveform.setWaveform(waveform);
            invalidate();
        }

        public void setProgress(float progress) {
            this.seekBarWaveform.setProgress(progress);
            invalidate();
        }

        public boolean isDragging() {
            return this.seekBarWaveform.isDragging();
        }

        public boolean onTouchEvent(MotionEvent event) {
            boolean result = this.seekBarWaveform.onTouch(event.getAction(), event.getX(), event.getY());
            if (result) {
                if (event.getAction() == 0) {
                    ChatActivityEnterView.this.requestDisallowInterceptTouchEvent(true);
                }
                invalidate();
            }
            if (result || super.onTouchEvent(event)) {
                return true;
            }
            return false;
        }

        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            this.seekBarWaveform.setSize(right - left, bottom - top);
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.seekBarWaveform.setColors(Theme.getColor(Theme.key_chat_recordedVoiceProgress), Theme.getColor(Theme.key_chat_recordedVoiceProgressInner), Theme.getColor(Theme.key_chat_recordedVoiceProgress));
            this.seekBarWaveform.draw(canvas);
        }
    }

    public ChatActivityEnterView(Activity context, SizeNotifierFrameLayout parent, ChatActivity fragment, boolean isChat) {
        this(context, parent, fragment, isChat, 0);
    }

    public ChatActivityEnterView(Activity context, SizeNotifierFrameLayout parent, ChatActivity fragment, boolean isChat, long mDialogId) {
        super(context);
        this.currentPopupContentType = -1;
        this.isPaused = true;
        this.startedDraggingX = -1.0f;
        this.distCanMove = (float) AndroidUtilities.dp(80.0f);
        this.messageWebPageSearch = true;
        this.openKeyboardRunnable = new C25021();
        this.updateExpandabilityRunnable = new C25032();
        this.roundedTranslationYProperty = new Property<View, Integer>(Integer.class, "translationY") {
            public Integer get(View object) {
                return Integer.valueOf(Math.round(object.getTranslationY()));
            }

            public void set(View object, Integer value) {
                object.setTranslationY((float) value.intValue());
            }
        };
        this.redDotPaint = new Paint(1);
        this.recordAudioVideoRunnable = new C25074();
        this.paint = new Paint(1);
        this.paintRecord = new Paint(1);
        this.rect = new RectF();
        this.dialogId = mDialogId;
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
        this.parentActivity = context;
        this.parentFragment = fragment;
        this.sizeNotifierLayout = parent;
        this.sizeNotifierLayout.setDelegate(this);
        this.sendByEnter = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("send_by_enter", false);
        this.textFieldContainer = new LinearLayout(context);
        this.textFieldContainer.setOrientation(0);
        addView(this.textFieldContainer, LayoutHelper.createFrame(-1, -2.0f, 51, 0.0f, 2.0f, 0.0f, 0.0f));
        FrameLayout frameLayout = new FrameLayout(context);
        this.textFieldContainer.addView(frameLayout, LayoutHelper.createLinear(0, -2, 1.0f));
        this.emojiButton = new ImageView(context) {
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
        frameLayout.addView(this.emojiButton, LayoutHelper.createFrame(48, 48.0f, 83, 3.0f, 0.0f, 0.0f, 0.0f));
        this.emojiButton.setOnClickListener(new C25096());
        this.messageEditText = new EditTextCaption(context) {

            /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$7$1 */
            class C25101 implements OnCommitContentListener {
                C25101() {
                }

                public boolean onCommitContent(InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {
                    if (BuildCompat.isAtLeastNMR1() && (InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION & flags) != 0) {
                        try {
                            inputContentInfo.requestPermission();
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    if (inputContentInfo.getDescription().hasMimeType("image/gif")) {
                        String str = null;
                        SendMessagesHelper.prepareSendingDocument(null, str, inputContentInfo.getContentUri(), "image/gif", ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, inputContentInfo);
                    } else {
                        SendMessagesHelper.prepareSendingPhoto(null, inputContentInfo.getContentUri(), ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, null, null, inputContentInfo, 0);
                    }
                    if (ChatActivityEnterView.this.delegate != null) {
                        ChatActivityEnterView.this.delegate.onMessageSend(null);
                    }
                    return true;
                }
            }

            public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
                InputConnection ic = super.onCreateInputConnection(editorInfo);
                EditorInfoCompat.setContentMimeTypes(editorInfo, new String[]{"image/gif", "image/*", "image/jpg", "image/png"});
                return InputConnectionCompat.createWrapper(ic, editorInfo, new C25101());
            }

            public boolean onTouchEvent(MotionEvent event) {
                boolean z = false;
                if (ChatActivityEnterView.this.isPopupShowing() && event.getAction() == 0) {
                    ChatActivityEnterView.this.showPopup(AndroidUtilities.usingHardwareInput ? z : 2, z);
                    ChatActivityEnterView.this.openKeyboardInternal();
                }
                try {
                    z = super.onTouchEvent(event);
                } catch (Exception e) {
                    FileLog.e(e);
                }
                return z;
            }
        };
        updateFieldHint();
        this.messageEditText.setImeOptions(268435456);
        this.messageEditText.setInputType((this.messageEditText.getInputType() | 16384) | 131072);
        this.messageEditText.setTypeface(AndroidUtilities.getTypeface(""));
        this.messageEditText.setSingleLine(false);
        this.messageEditText.setMaxLines(4);
        this.messageEditText.setTextSize(1, 18.0f);
        this.messageEditText.setGravity(80);
        this.messageEditText.setPadding(0, AndroidUtilities.dp(11.0f), 0, AndroidUtilities.dp(12.0f));
        this.messageEditText.setBackgroundDrawable(null);
        this.messageEditText.setTextColor(Theme.getColor(Theme.key_chat_messagePanelText));
        this.messageEditText.setHintColor(Theme.getColor(Theme.key_chat_messagePanelHint));
        this.messageEditText.setHintTextColor(Theme.getColor(Theme.key_chat_messagePanelHint));
        frameLayout.addView(this.messageEditText, LayoutHelper.createFrame(-1, -2.0f, 80, 52.0f, 0.0f, isChat ? 50.0f : 2.0f, 0.0f));
        this.messageEditText.setOnKeyListener(new C25128());
        this.messageEditText.setOnEditorActionListener(new C25139());
        this.messageEditText.addTextChangedListener(new TextWatcher() {
            boolean processChange = false;

            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (ChatActivityEnterView.this.innerTextChange != 1) {
                    ChatActivityEnterView.this.checkSendButton(true);
                    CharSequence message = AndroidUtilities.getTrimmedString(charSequence.toString());
                    if (!(ChatActivityEnterView.this.delegate == null || ChatActivityEnterView.this.ignoreTextChange)) {
                        if (count > 2 || charSequence == null || charSequence.length() == 0) {
                            ChatActivityEnterView.this.messageWebPageSearch = true;
                        }
                        ChatActivityEnterViewDelegate access$1000 = ChatActivityEnterView.this.delegate;
                        boolean z = before > count + 1 || count - before > 2;
                        access$1000.onTextChanged(charSequence, z);
                    }
                    if (!(ChatActivityEnterView.this.innerTextChange == 2 || before == count || count - before <= 1)) {
                        this.processChange = true;
                    }
                    if (ChatActivityEnterView.this.editingMessageObject == null && !ChatActivityEnterView.this.canWriteToChannel && message.length() != 0 && ChatActivityEnterView.this.lastTypingTimeSend < System.currentTimeMillis() - DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS && !ChatActivityEnterView.this.ignoreTextChange) {
                        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                        User currentUser = null;
                        if (((int) ChatActivityEnterView.this.dialog_id) > 0) {
                            currentUser = MessagesController.getInstance().getUser(Integer.valueOf((int) ChatActivityEnterView.this.dialog_id));
                        }
                        if (currentUser != null) {
                            if (currentUser.id == UserConfig.getClientUserId()) {
                                return;
                            }
                            if (!(currentUser.status == null || currentUser.status.expires >= currentTime || MessagesController.getInstance().onlinePrivacy.containsKey(Integer.valueOf(currentUser.id)))) {
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

            public void afterTextChanged(Editable editable) {
                if (ChatActivityEnterView.this.innerTextChange == 0) {
                    if (ChatActivityEnterView.this.sendByEnter && editable.length() > 0 && editable.charAt(editable.length() - 1) == '\n' && ChatActivityEnterView.this.editingMessageObject == null) {
                        ChatActivityEnterView.this.sendMessage();
                    }
                    if (this.processChange) {
                        ImageSpan[] spans = (ImageSpan[]) editable.getSpans(0, editable.length(), ImageSpan.class);
                        for (Object removeSpan : spans) {
                            editable.removeSpan(removeSpan);
                        }
                        Emoji.replaceEmoji(editable, ChatActivityEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        this.processChange = false;
                    }
                }
            }
        });
        if (isChat) {
            this.attachLayout = new LinearLayout(context);
            this.attachLayout.setOrientation(0);
            this.attachLayout.setEnabled(false);
            this.attachLayout.setPivotX((float) AndroidUtilities.dp(48.0f));
            frameLayout.addView(this.attachLayout, LayoutHelper.createFrame(-2, 48, 85));
            this.botButton = new ImageView(context);
            this.botButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.botButton.setImageResource(R.drawable.bot_keyboard2);
            this.botButton.setScaleType(ScaleType.CENTER);
            this.botButton.setVisibility(8);
            this.attachLayout.addView(this.botButton, LayoutHelper.createLinear(48, 48));
            this.botButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
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
            this.notifyButton = new ImageView(context);
            this.notifyButton.setImageResource(this.silent ? R.drawable.notify_members_off : R.drawable.notify_members_on);
            this.notifyButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.notifyButton.setScaleType(ScaleType.CENTER);
            this.notifyButton.setVisibility(this.canWriteToChannel ? 0 : 8);
            this.attachLayout.addView(this.notifyButton, LayoutHelper.createLinear(48, 48));
            this.notifyButton.setOnClickListener(new OnClickListener() {
                private Toast visibleToast;

                public void onClick(View v) {
                    ChatActivityEnterView.this.silent = !ChatActivityEnterView.this.silent;
                    ChatActivityEnterView.this.notifyButton.setImageResource(ChatActivityEnterView.this.silent ? R.drawable.notify_members_off : R.drawable.notify_members_on);
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putBoolean("silent_" + ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.silent).commit();
                    NotificationsController.updateServerNotificationsSettings(ChatActivityEnterView.this.dialog_id);
                    try {
                        if (this.visibleToast != null) {
                            this.visibleToast.cancel();
                        }
                    } catch (Exception e) {
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
            this.attachButton = new ImageView(context);
            this.attachButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.attachButton.setImageResource(R.drawable.ic_ab_attach);
            this.attachButton.setScaleType(ScaleType.CENTER);
            this.attachLayout.addView(this.attachButton, LayoutHelper.createLinear(48, 48));
            this.attachButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ChatActivityEnterView.this.delegate.didPressedAttachButton();
                }
            });
            boolean isUser = false;
            try {
                User user = MessagesController.getInstance().getUser(Integer.valueOf((int) this.dialogId));
                if (!(user == null || user.bot)) {
                    isUser = true;
                }
                if (AppPreferences.isPaymentEnable() && isChat && isUser) {
                    this.paintingButton = new ImageView(context);
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
        this.recordedAudioPanel = new FrameLayout(context);
        this.recordedAudioPanel.setVisibility(this.audioToSend == null ? 8 : 0);
        this.recordedAudioPanel.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.recordedAudioPanel.setFocusable(true);
        this.recordedAudioPanel.setFocusableInTouchMode(true);
        this.recordedAudioPanel.setClickable(true);
        frameLayout.addView(this.recordedAudioPanel, LayoutHelper.createFrame(-1, 48, 80));
        this.recordDeleteImageView = new ImageView(context);
        this.recordDeleteImageView.setScaleType(ScaleType.CENTER);
        this.recordDeleteImageView.setImageResource(R.drawable.ic_ab_delete);
        this.recordDeleteImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelVoiceDelete), Mode.MULTIPLY));
        this.recordedAudioPanel.addView(this.recordDeleteImageView, LayoutHelper.createFrame(48, 48.0f));
        this.recordDeleteImageView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.delegate.needStartRecordVideo(2);
                } else {
                    MessageObject playing = MediaController.getInstance().getPlayingMessageObject();
                    if (playing != null && playing == ChatActivityEnterView.this.audioToSendMessageObject) {
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
        this.videoTimelineView = new VideoTimelineView(context);
        this.videoTimelineView.setColor(-11817481);
        this.videoTimelineView.setRoundFrames(true);
        this.videoTimelineView.setDelegate(new VideoTimelineViewDelegate() {
            public void onLeftProgressChanged(float progress) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.videoToSendMessageObject.startTime = (long) (((float) ChatActivityEnterView.this.videoToSendMessageObject.estimatedDuration) * progress);
                    ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(2, progress);
                }
            }

            public void onRightProgressChanged(float progress) {
                if (ChatActivityEnterView.this.videoToSendMessageObject != null) {
                    ChatActivityEnterView.this.videoToSendMessageObject.endTime = (long) (((float) ChatActivityEnterView.this.videoToSendMessageObject.estimatedDuration) * progress);
                    ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(2, progress);
                }
            }

            public void didStartDragging() {
                ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(1, 0.0f);
            }

            public void didStopDragging() {
                ChatActivityEnterView.this.delegate.needChangeVideoPreviewState(0, 0.0f);
            }
        });
        this.recordedAudioPanel.addView(this.videoTimelineView, LayoutHelper.createFrame(-1, 32.0f, 19, 40.0f, 0.0f, 0.0f, 0.0f));
        this.recordedAudioBackground = new View(context);
        this.recordedAudioBackground.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(16.0f), Theme.getColor(Theme.key_chat_recordedVoiceBackground)));
        this.recordedAudioPanel.addView(this.recordedAudioBackground, LayoutHelper.createFrame(-1, 36.0f, 19, 48.0f, 0.0f, 0.0f, 0.0f));
        this.recordedAudioSeekBar = new SeekBarWaveformView(context);
        this.recordedAudioPanel.addView(this.recordedAudioSeekBar, LayoutHelper.createFrame(-1, 32.0f, 19, 92.0f, 0.0f, 52.0f, 0.0f));
        this.playDrawable = Theme.createSimpleSelectorDrawable(context, R.drawable.s_play, Theme.getColor(Theme.key_chat_recordedVoicePlayPause), Theme.getColor(Theme.key_chat_recordedVoicePlayPausePressed));
        this.pauseDrawable = Theme.createSimpleSelectorDrawable(context, R.drawable.s_pause, Theme.getColor(Theme.key_chat_recordedVoicePlayPause), Theme.getColor(Theme.key_chat_recordedVoicePlayPausePressed));
        this.recordedAudioPlayButton = new ImageView(context);
        this.recordedAudioPlayButton.setImageDrawable(this.playDrawable);
        this.recordedAudioPlayButton.setScaleType(ScaleType.CENTER);
        this.recordedAudioPanel.addView(this.recordedAudioPlayButton, LayoutHelper.createFrame(48, 48.0f, 83, 48.0f, 0.0f, 0.0f, 0.0f));
        this.recordedAudioPlayButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
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
        this.recordedAudioTimeTextView = new TextView(context);
        this.recordedAudioTimeTextView.setTextColor(Theme.getColor(Theme.key_chat_messagePanelVoiceDuration));
        this.recordedAudioTimeTextView.setTextSize(1, 13.0f);
        this.recordedAudioPanel.addView(this.recordedAudioTimeTextView, LayoutHelper.createFrame(-2, -2.0f, 21, 0.0f, 0.0f, 13.0f, 0.0f));
        this.recordPanel = new FrameLayout(context);
        this.recordPanel.setVisibility(8);
        this.recordPanel.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        frameLayout.addView(this.recordPanel, LayoutHelper.createFrame(-1, 48, 80));
        this.recordPanel.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.slideText = new LinearLayout(context);
        this.slideText.setOrientation(0);
        this.recordPanel.addView(this.slideText, LayoutHelper.createFrame(-2, -2.0f, 17, 30.0f, 0.0f, 0.0f, 0.0f));
        this.recordCancelImage = new ImageView(context);
        this.recordCancelImage.setImageResource(R.drawable.slidearrow);
        this.recordCancelImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_recordVoiceCancel), Mode.MULTIPLY));
        this.slideText.addView(this.recordCancelImage, LayoutHelper.createLinear(-2, -2, 16, 0, 1, 0, 0));
        this.recordCancelText = new TextView(context);
        this.recordCancelText.setText(LocaleController.getString("SlideToCancel", R.string.SlideToCancel));
        this.recordCancelText.setTextColor(Theme.getColor(Theme.key_chat_recordVoiceCancel));
        this.recordCancelText.setTextSize(1, 12.0f);
        this.slideText.addView(this.recordCancelText, LayoutHelper.createLinear(-2, -2, 16, 6, 0, 0, 0));
        this.recordSendText = new TextView(context);
        this.recordSendText.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        this.recordSendText.setTextColor(Theme.getColor(Theme.key_chat_fieldOverlayText));
        this.recordSendText.setTextSize(1, 16.0f);
        this.recordSendText.setGravity(17);
        this.recordSendText.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.recordSendText.setAlpha(0.0f);
        this.recordSendText.setPadding(AndroidUtilities.dp(36.0f), 0, 0, 0);
        this.recordSendText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
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
        this.recordPanel.addView(this.recordSendText, LayoutHelper.createFrame(-2, -1.0f, 49, 0.0f, 0.0f, 0.0f, 0.0f));
        this.recordTimeContainer = new LinearLayout(context);
        this.recordTimeContainer.setOrientation(0);
        this.recordTimeContainer.setPadding(AndroidUtilities.dp(13.0f), 0, 0, 0);
        this.recordTimeContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.recordPanel.addView(this.recordTimeContainer, LayoutHelper.createFrame(-2, -2, 16));
        this.recordDot = new RecordDot(context);
        this.recordTimeContainer.addView(this.recordDot, LayoutHelper.createLinear(11, 11, 16, 0, 1, 0, 0));
        this.recordTimeText = new TextView(context);
        this.recordTimeText.setTextColor(Theme.getColor(Theme.key_chat_recordTime));
        this.recordTimeText.setTextSize(1, 16.0f);
        this.recordTimeContainer.addView(this.recordTimeText, LayoutHelper.createLinear(-2, -2, 16, 6, 0, 0, 0));
        this.sendButtonContainer = new FrameLayout(context);
        this.textFieldContainer.addView(this.sendButtonContainer, LayoutHelper.createLinear(48, 48, 80));
        this.audioVideoButtonContainer = new FrameLayout(context);
        this.audioVideoButtonContainer.setBackgroundColor(Theme.getColor(Theme.key_chat_messagePanelBackground));
        this.audioVideoButtonContainer.setSoundEffectsEnabled(false);
        this.sendButtonContainer.addView(this.audioVideoButtonContainer, LayoutHelper.createFrame(48, 48.0f));
        this.audioVideoButtonContainer.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    if (ChatActivityEnterView.this.recordCircle.isSendButtonVisible()) {
                        if (!ChatActivityEnterView.this.hasRecordVideo || ChatActivityEnterView.this.calledRecordRunnable) {
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
                        return false;
                    }
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        TLRPC$Chat chat = ChatActivityEnterView.this.parentFragment.getCurrentChat();
                        if (ChatObject.isChannel(chat) && chat.banned_rights != null && chat.banned_rights.send_media) {
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
                } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
                    if (ChatActivityEnterView.this.recordCircle.isSendButtonVisible() || ChatActivityEnterView.this.recordedAudioPanel.getVisibility() == 0) {
                        return false;
                    }
                    if (ChatActivityEnterView.this.recordAudioVideoRunnableStarted) {
                        AndroidUtilities.cancelRunOnUIThread(ChatActivityEnterView.this.recordAudioVideoRunnable);
                        ChatActivityEnterView.this.delegate.onSwitchRecordMode(ChatActivityEnterView.this.videoSendButton.getTag() == null);
                        ChatActivityEnterView.this.setRecordVideoButtonVisible(ChatActivityEnterView.this.videoSendButton.getTag() == null, true);
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
                        r7 = new Animator[5];
                        r7[0] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordCircle, "lockAnimatedTranslation", new float[]{ChatActivityEnterView.this.recordCircle.startTranslation});
                        r7[1] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.slideText, "alpha", new float[]{0.0f});
                        r7[2] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.slideText, "translationY", new float[]{(float) AndroidUtilities.dp(20.0f)});
                        r7[3] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordSendText, "alpha", new float[]{1.0f});
                        r7[4] = ObjectAnimator.ofFloat(ChatActivityEnterView.this.recordSendText, "translationY", new float[]{(float) (-AndroidUtilities.dp(20.0f)), 0.0f});
                        animatorSet.playTogether(r7);
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
                    x += ChatActivityEnterView.this.audioVideoButtonContainer.getX();
                    LayoutParams params = (LayoutParams) ChatActivityEnterView.this.slideText.getLayoutParams();
                    if (ChatActivityEnterView.this.startedDraggingX != -1.0f) {
                        float dist = x - ChatActivityEnterView.this.startedDraggingX;
                        params.leftMargin = AndroidUtilities.dp(30.0f) + ((int) dist);
                        ChatActivityEnterView.this.slideText.setLayoutParams(params);
                        float alpha = 1.0f + (dist / ChatActivityEnterView.this.distCanMove);
                        if (alpha > 1.0f) {
                            alpha = 1.0f;
                        } else if (alpha < 0.0f) {
                            alpha = 0.0f;
                        }
                        ChatActivityEnterView.this.slideText.setAlpha(alpha);
                    }
                    if (x <= (ChatActivityEnterView.this.slideText.getX() + ((float) ChatActivityEnterView.this.slideText.getWidth())) + ((float) AndroidUtilities.dp(30.0f)) && ChatActivityEnterView.this.startedDraggingX == -1.0f) {
                        ChatActivityEnterView.this.startedDraggingX = x;
                        ChatActivityEnterView.this.distCanMove = ((float) ((ChatActivityEnterView.this.recordPanel.getMeasuredWidth() - ChatActivityEnterView.this.slideText.getMeasuredWidth()) - AndroidUtilities.dp(48.0f))) / 2.0f;
                        if (ChatActivityEnterView.this.distCanMove <= 0.0f) {
                            ChatActivityEnterView.this.distCanMove = (float) AndroidUtilities.dp(80.0f);
                        } else if (ChatActivityEnterView.this.distCanMove > ((float) AndroidUtilities.dp(80.0f))) {
                            ChatActivityEnterView.this.distCanMove = (float) AndroidUtilities.dp(80.0f);
                        }
                    }
                    if (params.leftMargin > AndroidUtilities.dp(30.0f)) {
                        params.leftMargin = AndroidUtilities.dp(30.0f);
                        ChatActivityEnterView.this.slideText.setLayoutParams(params);
                        ChatActivityEnterView.this.slideText.setAlpha(1.0f);
                        ChatActivityEnterView.this.startedDraggingX = -1.0f;
                    }
                }
                view.onTouchEvent(motionEvent);
                return true;
            }
        });
        this.audioSendButton = new ImageView(context);
        this.audioSendButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.audioSendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
        this.audioSendButton.setImageResource(R.drawable.mic);
        this.audioSendButton.setPadding(0, 0, AndroidUtilities.dp(4.0f), 0);
        this.audioVideoButtonContainer.addView(this.audioSendButton, LayoutHelper.createFrame(48, 48.0f));
        if (isChat) {
            this.videoSendButton = new ImageView(context);
            this.videoSendButton.setScaleType(ScaleType.CENTER_INSIDE);
            this.videoSendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelIcons), Mode.MULTIPLY));
            this.videoSendButton.setImageResource(R.drawable.ic_msg_panel_video);
            this.videoSendButton.setPadding(0, 0, AndroidUtilities.dp(4.0f), 0);
            this.audioVideoButtonContainer.addView(this.videoSendButton, LayoutHelper.createFrame(48, 48.0f));
        }
        this.recordCircle = new RecordCircle(context);
        this.recordCircle.setVisibility(8);
        this.sizeNotifierLayout.addView(this.recordCircle, LayoutHelper.createFrame(124, 194.0f, 85, 0.0f, 0.0f, -36.0f, 0.0f));
        this.cancelBotButton = new ImageView(context);
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
        this.cancelBotButton.setAlpha(0.0f);
        this.sendButtonContainer.addView(this.cancelBotButton, LayoutHelper.createFrame(48, 48.0f));
        this.cancelBotButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                String text = ChatActivityEnterView.this.messageEditText.getText().toString();
                int idx = text.indexOf(32);
                if (idx == -1 || idx == text.length() - 1) {
                    ChatActivityEnterView.this.setFieldText("");
                } else {
                    ChatActivityEnterView.this.setFieldText(text.substring(0, idx + 1));
                }
            }
        });
        this.sendButton = new ImageView(context);
        this.sendButton.setVisibility(4);
        this.sendButton.setScaleType(ScaleType.CENTER_INSIDE);
        this.sendButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_messagePanelSend), Mode.MULTIPLY));
        this.sendButton.setImageResource(R.drawable.ic_send);
        this.sendButton.setSoundEffectsEnabled(false);
        this.sendButton.setScaleX(0.1f);
        this.sendButton.setScaleY(0.1f);
        this.sendButton.setAlpha(0.0f);
        this.sendButtonContainer.addView(this.sendButton, LayoutHelper.createFrame(48, 48.0f));
        this.sendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivityEnterView.this.sendMessage();
            }
        });
        this.expandStickersButton = new ImageView(context);
        this.expandStickersButton.setScaleType(ScaleType.CENTER);
        imageView = this.expandStickersButton;
        closeProgressDrawable2 = new AnimatedArrowDrawable(Theme.getColor(Theme.key_chat_messagePanelIcons));
        this.stickersArrow = closeProgressDrawable2;
        imageView.setImageDrawable(closeProgressDrawable2);
        this.expandStickersButton.setVisibility(8);
        this.expandStickersButton.setScaleX(0.1f);
        this.expandStickersButton.setScaleY(0.1f);
        this.expandStickersButton.setAlpha(0.0f);
        this.sendButtonContainer.addView(this.expandStickersButton, LayoutHelper.createFrame(48, 48.0f));
        this.expandStickersButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (ChatActivityEnterView.this.expandStickersButton.getVisibility() == 0 && ChatActivityEnterView.this.expandStickersButton.getAlpha() == 1.0f && !ChatActivityEnterView.this.stickersDragging) {
                    ChatActivityEnterView.this.setStickersExpanded(!ChatActivityEnterView.this.stickersExpanded, true);
                }
            }
        });
        this.doneButtonContainer = new FrameLayout(context);
        this.doneButtonContainer.setVisibility(8);
        this.textFieldContainer.addView(this.doneButtonContainer, LayoutHelper.createLinear(48, 48, 80));
        this.doneButtonContainer.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                ChatActivityEnterView.this.doneEditingMessage();
            }
        });
        this.doneButtonImage = new ImageView(context);
        this.doneButtonImage.setScaleType(ScaleType.CENTER);
        this.doneButtonImage.setImageResource(R.drawable.edit_done);
        this.doneButtonImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_chat_editDoneIcon), Mode.MULTIPLY));
        this.doneButtonContainer.addView(this.doneButtonImage, LayoutHelper.createFrame(48, 48.0f));
        this.doneButtonProgress = new ContextProgressView(context, 0);
        this.doneButtonProgress.setVisibility(4);
        this.doneButtonContainer.addView(this.doneButtonProgress, LayoutHelper.createFrame(-1, -1.0f));
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0);
        this.keyboardHeight = sharedPreferences.getInt("kbd_height", AndroidUtilities.dp(200.0f));
        this.keyboardHeightLand = sharedPreferences.getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
        setRecordVideoButtonVisible(false, false);
        checkSendButton(false);
        checkChannelRights();
    }

    private void checkUserStatusRegistry() {
        HandleRequest.getNew(getContext(), this).checkRegisterStatus();
    }

    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        if (child == this.topView) {
            canvas.save();
            canvas.clipRect(0, 0, getMeasuredWidth(), child.getLayoutParams().height + AndroidUtilities.dp(2.0f));
        }
        boolean result = super.drawChild(canvas, child, drawingTime);
        if (child == this.topView) {
            canvas.restore();
        }
        return result;
    }

    protected void onDraw(Canvas canvas) {
        int top;
        if (this.topView == null || this.topView.getVisibility() != 0) {
            top = 0;
        } else {
            top = (int) this.topView.getTranslationY();
        }
        int bottom = top + Theme.chat_composeShadowDrawable.getIntrinsicHeight();
        Theme.chat_composeShadowDrawable.setBounds(0, top, getMeasuredWidth(), bottom);
        Theme.chat_composeShadowDrawable.draw(canvas);
        canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    public boolean isSendButtonVisible() {
        return this.sendButton.getVisibility() == 0;
    }

    private void setRecordVideoButtonVisible(boolean visible, boolean animated) {
        if (this.videoSendButton != null) {
            this.videoSendButton.setTag(visible ? Integer.valueOf(1) : null);
            if (this.audioVideoButtonAnimation != null) {
                this.audioVideoButtonAnimation.cancel();
                this.audioVideoButtonAnimation = null;
            }
            if (animated) {
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                boolean isChannel = false;
                if (((int) this.dialog_id) < 0) {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                    isChannel = ChatObject.isChannel(chat) && !chat.megagroup;
                }
                preferences.edit().putBoolean(isChannel ? "currentModeVideoChannel" : "currentModeVideo", visible).commit();
                this.audioVideoButtonAnimation = new AnimatorSet();
                AnimatorSet animatorSet = this.audioVideoButtonAnimation;
                Animator[] animatorArr = new Animator[6];
                ImageView imageView = this.videoSendButton;
                String str = "scaleX";
                float[] fArr = new float[1];
                fArr[0] = visible ? 1.0f : 0.1f;
                animatorArr[0] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.videoSendButton;
                str = "scaleY";
                fArr = new float[1];
                fArr[0] = visible ? 1.0f : 0.1f;
                animatorArr[1] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.videoSendButton;
                str = "alpha";
                fArr = new float[1];
                fArr[0] = visible ? 1.0f : 0.0f;
                animatorArr[2] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.audioSendButton;
                str = "scaleX";
                fArr = new float[1];
                fArr[0] = visible ? 0.1f : 1.0f;
                animatorArr[3] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.audioSendButton;
                str = "scaleY";
                fArr = new float[1];
                fArr[0] = visible ? 0.1f : 1.0f;
                animatorArr[4] = ObjectAnimator.ofFloat(imageView, str, fArr);
                imageView = this.audioSendButton;
                str = "alpha";
                fArr = new float[1];
                fArr[0] = visible ? 0.0f : 1.0f;
                animatorArr[5] = ObjectAnimator.ofFloat(imageView, str, fArr);
                animatorSet.playTogether(animatorArr);
                this.audioVideoButtonAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (animation.equals(ChatActivityEnterView.this.audioVideoButtonAnimation)) {
                            ChatActivityEnterView.this.audioVideoButtonAnimation = null;
                        }
                    }
                });
                this.audioVideoButtonAnimation.setInterpolator(new DecelerateInterpolator());
                this.audioVideoButtonAnimation.setDuration(150);
                this.audioVideoButtonAnimation.start();
                return;
            }
            float f;
            this.videoSendButton.setScaleX(visible ? 1.0f : 0.1f);
            this.videoSendButton.setScaleY(visible ? 1.0f : 0.1f);
            this.videoSendButton.setAlpha(visible ? 1.0f : 0.0f);
            this.audioSendButton.setScaleX(visible ? 0.1f : 1.0f);
            this.audioSendButton.setScaleY(visible ? 0.1f : 1.0f);
            ImageView imageView2 = this.audioSendButton;
            if (visible) {
                f = 0.0f;
            } else {
                f = 1.0f;
            }
            imageView2.setAlpha(f);
        }
    }

    public boolean isRecordingAudioVideo() {
        return this.recordingAudioVideo;
    }

    public boolean isRecordLocked() {
        return this.recordingAudioVideo && this.recordCircle.isSendButtonVisible();
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

    public void showContextProgress(boolean show) {
        if (this.progressDrawable != null) {
            if (show) {
                this.progressDrawable.startAnimation();
            } else {
                this.progressDrawable.stopAnimation();
            }
        }
    }

    public void setCaption(String caption) {
        if (this.messageEditText != null) {
            this.messageEditText.setCaption(caption);
            checkSendButton(true);
        }
    }

    public void addTopView(View view, int height) {
        if (view != null) {
            this.topView = view;
            this.topView.setVisibility(8);
            this.topView.setTranslationY((float) height);
            addView(this.topView, 0, LayoutHelper.createFrame(-1, (float) height, 51, 0.0f, 2.0f, 0.0f, 0.0f));
            this.needShowTopView = false;
        }
    }

    public void setForceShowSendButton(boolean value, boolean animated) {
        this.forceShowSendButton = value;
        checkSendButton(animated);
    }

    public void setAllowStickersAndGifs(boolean value, boolean value2) {
        if (!((this.allowStickers == value && this.allowGifs == value2) || this.emojiView == null)) {
            if (this.emojiView.getVisibility() == 0) {
                hidePopup(false);
            }
            this.sizeNotifierLayout.removeView(this.emojiView);
            this.emojiView = null;
        }
        this.allowStickers = value;
        this.allowGifs = value2;
        setEmojiButtonImage();
    }

    public void addEmojiToRecent(String code) {
        createEmojiView();
        this.emojiView.addEmojiToRecent(code);
    }

    public void setOpenGifsTabFirst() {
        createEmojiView();
        StickersQuery.loadRecents(0, true, true, false);
        this.emojiView.switchToGifRecent();
    }

    public void showTopView(boolean animated, final boolean openKeyboard) {
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
                if (!animated) {
                    this.topView.setTranslationY(0.0f);
                    if (this.recordedAudioPanel.getVisibility() == 0) {
                        return;
                    }
                    if (!this.forceShowSendButton || openKeyboard) {
                        openKeyboard();
                    }
                } else if (this.keyboardVisible || isPopupShowing()) {
                    this.currentTopViewAnimation = new AnimatorSet();
                    AnimatorSet animatorSet = this.currentTopViewAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.topView, "translationY", new float[]{0.0f});
                    animatorSet.playTogether(animatorArr);
                    this.currentTopViewAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animation)) {
                                if (ChatActivityEnterView.this.recordedAudioPanel.getVisibility() != 0 && (!ChatActivityEnterView.this.forceShowSendButton || openKeyboard)) {
                                    ChatActivityEnterView.this.openKeyboard();
                                }
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }

                        public void onAnimationCancel(Animator animation) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animation)) {
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }
                    });
                    this.currentTopViewAnimation.setDuration(200);
                    this.currentTopViewAnimation.start();
                } else {
                    this.topView.setTranslationY(0.0f);
                    if (this.recordedAudioPanel.getVisibility() == 0) {
                        return;
                    }
                    if (!this.forceShowSendButton || openKeyboard) {
                        openKeyboard();
                    }
                }
            }
        }
    }

    public void onEditTimeExpired() {
        this.doneButtonContainer.setVisibility(8);
    }

    public void showEditDoneProgress(final boolean show, boolean animated) {
        if (this.doneButtonAnimation != null) {
            this.doneButtonAnimation.cancel();
        }
        if (animated) {
            this.doneButtonAnimation = new AnimatorSet();
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            if (show) {
                this.doneButtonProgress.setVisibility(0);
                this.doneButtonContainer.setEnabled(false);
                animatorSet = this.doneButtonAnimation;
                animatorArr = new Animator[6];
                animatorArr[0] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleX", new float[]{0.1f});
                animatorArr[1] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleY", new float[]{0.1f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneButtonImage, "alpha", new float[]{0.0f});
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
                animatorArr[2] = ObjectAnimator.ofFloat(this.doneButtonProgress, "alpha", new float[]{0.0f});
                animatorArr[3] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleX", new float[]{1.0f});
                animatorArr[4] = ObjectAnimator.ofFloat(this.doneButtonImage, "scaleY", new float[]{1.0f});
                animatorArr[5] = ObjectAnimator.ofFloat(this.doneButtonImage, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
            }
            this.doneButtonAnimation.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    if (ChatActivityEnterView.this.doneButtonAnimation != null && ChatActivityEnterView.this.doneButtonAnimation.equals(animation)) {
                        if (show) {
                            ChatActivityEnterView.this.doneButtonImage.setVisibility(4);
                        } else {
                            ChatActivityEnterView.this.doneButtonProgress.setVisibility(4);
                        }
                    }
                }

                public void onAnimationCancel(Animator animation) {
                    if (ChatActivityEnterView.this.doneButtonAnimation != null && ChatActivityEnterView.this.doneButtonAnimation.equals(animation)) {
                        ChatActivityEnterView.this.doneButtonAnimation = null;
                    }
                }
            });
            this.doneButtonAnimation.setDuration(150);
            this.doneButtonAnimation.start();
        } else if (show) {
            this.doneButtonImage.setScaleX(0.1f);
            this.doneButtonImage.setScaleY(0.1f);
            this.doneButtonImage.setAlpha(0.0f);
            this.doneButtonProgress.setScaleX(1.0f);
            this.doneButtonProgress.setScaleY(1.0f);
            this.doneButtonProgress.setAlpha(1.0f);
            this.doneButtonImage.setVisibility(4);
            this.doneButtonProgress.setVisibility(0);
            this.doneButtonContainer.setEnabled(false);
        } else {
            this.doneButtonProgress.setScaleX(0.1f);
            this.doneButtonProgress.setScaleY(0.1f);
            this.doneButtonProgress.setAlpha(0.0f);
            this.doneButtonImage.setScaleX(1.0f);
            this.doneButtonImage.setScaleY(1.0f);
            this.doneButtonImage.setAlpha(1.0f);
            this.doneButtonImage.setVisibility(0);
            this.doneButtonProgress.setVisibility(4);
            this.doneButtonContainer.setEnabled(true);
        }
    }

    public void hideTopView(boolean animated) {
        if (this.topView != null && this.topViewShowed) {
            this.topViewShowed = false;
            this.needShowTopView = false;
            if (this.allowShowTopView) {
                if (this.currentTopViewAnimation != null) {
                    this.currentTopViewAnimation.cancel();
                    this.currentTopViewAnimation = null;
                }
                if (animated) {
                    this.currentTopViewAnimation = new AnimatorSet();
                    AnimatorSet animatorSet = this.currentTopViewAnimation;
                    Animator[] animatorArr = new Animator[1];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.topView, "translationY", new float[]{(float) this.topView.getLayoutParams().height});
                    animatorSet.playTogether(animatorArr);
                    this.currentTopViewAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animation)) {
                                ChatActivityEnterView.this.topView.setVisibility(8);
                                ChatActivityEnterView.this.resizeForTopView(false);
                                ChatActivityEnterView.this.currentTopViewAnimation = null;
                            }
                        }

                        public void onAnimationCancel(Animator animation) {
                            if (ChatActivityEnterView.this.currentTopViewAnimation != null && ChatActivityEnterView.this.currentTopViewAnimation.equals(animation)) {
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

    public boolean isTopViewVisible() {
        return this.topView != null && this.topView.getVisibility() == 0;
    }

    private void onWindowSizeChanged() {
        int size = this.sizeNotifierLayout.getHeight();
        if (!this.keyboardVisible) {
            size -= this.emojiPadding;
        }
        if (this.delegate != null) {
            this.delegate.onWindowSizeChanged(size);
        }
        if (this.topView == null) {
            return;
        }
        if (size < AndroidUtilities.dp(72.0f) + ActionBar.getCurrentActionBarHeight()) {
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
                this.topView.setTranslationY(0.0f);
            }
        }
    }

    private void resizeForTopView(boolean show) {
        int i;
        LayoutParams layoutParams = (LayoutParams) this.textFieldContainer.getLayoutParams();
        int dp = AndroidUtilities.dp(2.0f);
        if (show) {
            i = this.topView.getLayoutParams().height;
        } else {
            i = 0;
        }
        layoutParams.topMargin = i + dp;
        this.textFieldContainer.setLayoutParams(layoutParams);
        if (this.stickersExpanded) {
            setStickersExpanded(false, true);
        }
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
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
        if (this.sizeNotifierLayout != null) {
            this.sizeNotifierLayout.setDelegate(null);
        }
    }

    public void checkChannelRights() {
        if (this.parentFragment != null) {
            TLRPC$Chat chat = this.parentFragment.getCurrentChat();
            if (ChatObject.isChannel(chat)) {
                FrameLayout frameLayout = this.audioVideoButtonContainer;
                float f = (chat.banned_rights == null || !chat.banned_rights.send_media) ? 1.0f : 0.5f;
                frameLayout.setAlpha(f);
                if (this.emojiView != null) {
                    EmojiView emojiView = this.emojiView;
                    boolean z = chat.banned_rights != null && chat.banned_rights.send_stickers;
                    emojiView.setStickersBanned(z, chat.id);
                }
            }
        }
    }

    public void onPause() {
        this.isPaused = true;
        closeKeyboard();
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

    public void setDialogId(long id) {
        int i = 1;
        this.dialog_id = id;
        int lower_id = (int) this.dialog_id;
        int high_id = (int) (this.dialog_id >> 32);
        if (((int) this.dialog_id) < 0) {
            boolean z;
            TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
            this.silent = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("silent_" + this.dialog_id, false);
            if (!ChatObject.isChannel(currentChat) || ((!currentChat.creator && (currentChat.admin_rights == null || !currentChat.admin_rights.post_messages)) || currentChat.megagroup)) {
                z = false;
            } else {
                z = true;
            }
            this.canWriteToChannel = z;
            if (this.notifyButton != null) {
                int i2;
                ImageView imageView = this.notifyButton;
                if (this.canWriteToChannel) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                imageView.setVisibility(i2);
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

    public void setChatInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        if (this.emojiView != null) {
            this.emojiView.setChatInfo(this.info);
        }
    }

    public void checkRoundVideo() {
        if (!this.hasRecordVideo) {
            if (this.attachLayout == null || VERSION.SDK_INT < 18) {
                this.hasRecordVideo = false;
                setRecordVideoButtonVisible(false, false);
                return;
            }
            int high_id = (int) (this.dialog_id >> 32);
            if (((int) this.dialog_id) != 0 || high_id == 0) {
                this.hasRecordVideo = true;
            } else if (AndroidUtilities.getPeerLayerVersion(MessagesController.getInstance().getEncryptedChat(Integer.valueOf(high_id)).layer) >= 66) {
                this.hasRecordVideo = true;
            }
            boolean isChannel = false;
            if (((int) this.dialog_id) < 0) {
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                isChannel = ChatObject.isChannel(chat) && !chat.megagroup;
                if (isChannel && !chat.creator && (chat.admin_rights == null || !chat.admin_rights.post_messages)) {
                    this.hasRecordVideo = false;
                }
            }
            if (!MediaController.getInstance().canInAppCamera()) {
                this.hasRecordVideo = false;
            }
            if (this.hasRecordVideo) {
                Long startTime = Long.valueOf(System.currentTimeMillis());
                CameraController.getInstance().initCamera();
                Log.d("alireza", "alireza end Time : " + (System.currentTimeMillis() - startTime.longValue()));
                setRecordVideoButtonVisible(ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean(isChannel ? "currentModeVideoChannel" : "currentModeVideo", isChannel), false);
                return;
            }
            setRecordVideoButtonVisible(false, false);
        }
    }

    public boolean isInVideoMode() {
        return this.videoSendButton.getTag() != null;
    }

    public boolean hasRecordVideo() {
        return this.hasRecordVideo;
    }

    private void updateFieldHint() {
        boolean isChannel = false;
        if (((int) this.dialog_id) < 0) {
            TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
            isChannel = ChatObject.isChannel(chat) && !chat.megagroup;
        }
        if (!isChannel) {
            this.messageEditText.setHintText(LocaleController.getString("TypeMessage", R.string.TypeMessage));
        } else if (this.editingMessageObject != null) {
            String string;
            EditTextCaption editTextCaption = this.messageEditText;
            if (this.editingCaption) {
                string = LocaleController.getString("Caption", R.string.Caption);
            } else {
                string = LocaleController.getString("TypeMessage", R.string.TypeMessage);
            }
            editTextCaption.setHintText(string);
        } else if (this.silent) {
            this.messageEditText.setHintText(LocaleController.getString("ChannelSilentBroadcast", R.string.ChannelSilentBroadcast));
        } else {
            this.messageEditText.setHintText(LocaleController.getString("ChannelBroadcast", R.string.ChannelBroadcast));
        }
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

    public void setWebPage(TLRPC$WebPage webPage, boolean searchWebPages) {
        this.messageWebPage = webPage;
        this.messageWebPageSearch = searchWebPages;
    }

    public boolean isMessageWebPageSearchEnabled() {
        return this.messageWebPageSearch;
    }

    private void hideRecordedAudioPanel() {
        this.audioToSendPath = null;
        this.audioToSend = null;
        this.audioToSendMessageObject = null;
        this.videoToSendMessageObject = null;
        this.videoTimelineView.destroy();
        AnimatorSet AnimatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.recordedAudioPanel, "alpha", new float[]{0.0f});
        AnimatorSet.playTogether(animatorArr);
        AnimatorSet.setDuration(200);
        AnimatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ChatActivityEnterView.this.recordedAudioPanel.setVisibility(8);
            }
        });
        AnimatorSet.start();
    }

    private void sendMessage() {
        if (this.parentFragment != null) {
            String action;
            if (((int) this.dialog_id) < 0) {
                TLRPC$Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-((int) this.dialog_id)));
                if (currentChat == null || currentChat.participants_count <= MessagesController.getInstance().groupBigSize) {
                    action = "chat_message";
                } else {
                    action = "bigchat_message";
                }
            } else {
                action = "pm_message";
            }
            if (!MessagesController.isFeatureEnabled(action, this.parentFragment)) {
                return;
            }
        }
        if (this.videoToSendMessageObject != null) {
            this.delegate.needStartRecordVideo(4);
            hideRecordedAudioPanel();
            checkSendButton(true);
        } else if (this.audioToSend != null) {
            MessageObject playing = MediaController.getInstance().getPlayingMessageObject();
            if (playing != null && playing == this.audioToSendMessageObject) {
                MediaController.getInstance().cleanupPlayer(true, true);
            }
            SendMessagesHelper.getInstance().sendMessage(this.audioToSend, null, this.audioToSendPath, this.dialog_id, this.replyingMessageObject, null, null, 0);
            if (this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
            hideRecordedAudioPanel();
            checkSendButton(true);
        } else {
            CharSequence message = this.messageEditText.getText();
            if (processSendingText(message)) {
                this.messageEditText.setText("");
                this.lastTypingTimeSend = 0;
                if (this.delegate != null) {
                    this.delegate.onMessageSend(message);
                }
            } else if (this.forceShowSendButton && this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
        }
    }

    public void doneEditingMessage() {
        if (this.editingMessageObject != null) {
            this.delegate.onMessageEditEnd(true);
            showEditDoneProgress(true, true);
            CharSequence[] message = new CharSequence[]{this.messageEditText.getText()};
            this.editingMessageReqId = SendMessagesHelper.getInstance().editMessage(this.editingMessageObject, message[0].toString(), this.messageWebPageSearch, this.parentFragment, MessagesQuery.getEntities(message), new Runnable() {
                public void run() {
                    ChatActivityEnterView.this.editingMessageReqId = 0;
                    ChatActivityEnterView.this.setEditingMessageObject(null, false);
                }
            });
        }
    }

    public boolean processSendingText(CharSequence text) {
        text = AndroidUtilities.getTrimmedString(text);
        if (text.length() == 0) {
            return false;
        }
        int count = (int) Math.ceil((double) (((float) text.length()) / 4096.0f));
        for (int a = 0; a < count; a++) {
            CharSequence[] message = new CharSequence[]{text.subSequence(a * 4096, Math.min((a + 1) * 4096, text.length()))};
            SendMessagesHelper.getInstance().sendMessage(message[0].toString(), this.dialog_id, this.replyingMessageObject, this.messageWebPage, this.messageWebPageSearch, MessagesQuery.getEntities(message), null, null);
        }
        return true;
    }

    private void checkSendButton(boolean animated) {
        if (this.editingMessageObject == null) {
            if (this.isPaused) {
                animated = false;
            }
            AnimatorSet animatorSet;
            Animator[] animatorArr;
            ArrayList<Animator> animators;
            if (AndroidUtilities.getTrimmedString(this.messageEditText.getText()).length() > 0 || this.forceShowSendButton || this.audioToSend != null || this.videoToSendMessageObject != null) {
                final String caption = this.messageEditText.getCaption();
                boolean showBotButton = caption != null && (this.sendButton.getVisibility() == 0 || this.expandStickersButton.getVisibility() == 0);
                boolean showSendButton = caption == null && (this.cancelBotButton.getVisibility() == 0 || this.expandStickersButton.getVisibility() == 0);
                if (this.audioVideoButtonContainer.getVisibility() != 0 && !showBotButton && !showSendButton) {
                    return;
                }
                if (!animated) {
                    this.audioVideoButtonContainer.setScaleX(0.1f);
                    this.audioVideoButtonContainer.setScaleY(0.1f);
                    this.audioVideoButtonContainer.setAlpha(0.0f);
                    if (caption != null) {
                        this.sendButton.setScaleX(0.1f);
                        this.sendButton.setScaleY(0.1f);
                        this.sendButton.setAlpha(0.0f);
                        this.cancelBotButton.setScaleX(1.0f);
                        this.cancelBotButton.setScaleY(1.0f);
                        this.cancelBotButton.setAlpha(1.0f);
                        this.cancelBotButton.setVisibility(0);
                        this.sendButton.setVisibility(8);
                    } else {
                        this.cancelBotButton.setScaleX(0.1f);
                        this.cancelBotButton.setScaleY(0.1f);
                        this.cancelBotButton.setAlpha(0.0f);
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
                            animatorSet = this.runningAnimation2;
                            animatorArr = new Animator[2];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{0.0f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{0.0f});
                            animatorSet.playTogether(animatorArr);
                            this.runningAnimation2.setDuration(100);
                            this.runningAnimation2.addListener(new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator animation) {
                                    if (ChatActivityEnterView.this.runningAnimation2 != null && ChatActivityEnterView.this.runningAnimation2.equals(animation)) {
                                        ChatActivityEnterView.this.attachLayout.setVisibility(8);
                                    }
                                }

                                public void onAnimationCancel(Animator animation) {
                                    if (ChatActivityEnterView.this.runningAnimation2 != null && ChatActivityEnterView.this.runningAnimation2.equals(animation)) {
                                        ChatActivityEnterView.this.runningAnimation2 = null;
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
                        animators = new ArrayList();
                        if (this.audioVideoButtonContainer.getVisibility() == 0) {
                            animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{0.0f}));
                        }
                        if (this.expandStickersButton.getVisibility() == 0) {
                            animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{0.0f}));
                        }
                        if (showBotButton) {
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{0.0f}));
                        } else if (showSendButton) {
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{0.0f}));
                        }
                        if (caption != null) {
                            this.runningAnimationType = 3;
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{1.0f}));
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{1.0f}));
                            animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{1.0f}));
                            this.cancelBotButton.setVisibility(0);
                        } else {
                            this.runningAnimationType = 1;
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{1.0f}));
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{1.0f}));
                            animators.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{1.0f}));
                            this.sendButton.setVisibility(0);
                        }
                        this.runningAnimation.playTogether(animators);
                        this.runningAnimation.setDuration(150);
                        this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
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

                            public void onAnimationCancel(Animator animation) {
                                if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
                                    ChatActivityEnterView.this.runningAnimation = null;
                                }
                            }
                        });
                        this.runningAnimation.start();
                    }
                }
            } else if (this.emojiView == null || this.emojiView.getVisibility() != 0 || !this.stickersTabOpen || AndroidUtilities.isInMultiwindow) {
                if (this.sendButton.getVisibility() != 0 && this.cancelBotButton.getVisibility() != 0 && this.expandStickersButton.getVisibility() != 0) {
                    return;
                }
                if (!animated) {
                    this.sendButton.setScaleX(0.1f);
                    this.sendButton.setScaleY(0.1f);
                    this.sendButton.setAlpha(0.0f);
                    this.cancelBotButton.setScaleX(0.1f);
                    this.cancelBotButton.setScaleY(0.1f);
                    this.cancelBotButton.setAlpha(0.0f);
                    this.expandStickersButton.setScaleX(0.1f);
                    this.expandStickersButton.setScaleY(0.1f);
                    this.expandStickersButton.setAlpha(0.0f);
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
                        animatorSet = this.runningAnimation2;
                        animatorArr = new Animator[2];
                        animatorArr[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{1.0f});
                        animatorArr[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{1.0f});
                        animatorSet.playTogether(animatorArr);
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
                    animators = new ArrayList();
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{1.0f}));
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{1.0f}));
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{1.0f}));
                    if (this.cancelBotButton.getVisibility() == 0) {
                        animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{0.0f}));
                    } else if (this.expandStickersButton.getVisibility() == 0) {
                        animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{0.0f}));
                    } else {
                        animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                        animators.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{0.0f}));
                    }
                    this.runningAnimation.playTogether(animators);
                    this.runningAnimation.setDuration(150);
                    this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
                                ChatActivityEnterView.this.sendButton.setVisibility(8);
                                ChatActivityEnterView.this.cancelBotButton.setVisibility(8);
                                ChatActivityEnterView.this.audioVideoButtonContainer.setVisibility(0);
                                ChatActivityEnterView.this.runningAnimation = null;
                                ChatActivityEnterView.this.runningAnimationType = 0;
                            }
                        }

                        public void onAnimationCancel(Animator animation) {
                            if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
                                ChatActivityEnterView.this.runningAnimation = null;
                            }
                        }
                    });
                    this.runningAnimation.start();
                }
            } else if (!animated) {
                this.sendButton.setScaleX(0.1f);
                this.sendButton.setScaleY(0.1f);
                this.sendButton.setAlpha(0.0f);
                this.cancelBotButton.setScaleX(0.1f);
                this.cancelBotButton.setScaleY(0.1f);
                this.cancelBotButton.setAlpha(0.0f);
                this.audioVideoButtonContainer.setScaleX(0.1f);
                this.audioVideoButtonContainer.setScaleY(0.1f);
                this.audioVideoButtonContainer.setAlpha(0.0f);
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
                    animatorSet = this.runningAnimation2;
                    animatorArr = new Animator[2];
                    animatorArr[0] = ObjectAnimator.ofFloat(this.attachLayout, "alpha", new float[]{1.0f});
                    animatorArr[1] = ObjectAnimator.ofFloat(this.attachLayout, "scaleX", new float[]{1.0f});
                    animatorSet.playTogether(animatorArr);
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
                animators = new ArrayList();
                animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleX", new float[]{1.0f}));
                animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "scaleY", new float[]{1.0f}));
                animators.add(ObjectAnimator.ofFloat(this.expandStickersButton, "alpha", new float[]{1.0f}));
                if (this.cancelBotButton.getVisibility() == 0) {
                    animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleX", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "scaleY", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.cancelBotButton, "alpha", new float[]{0.0f}));
                } else if (this.audioVideoButtonContainer.getVisibility() == 0) {
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleX", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "scaleY", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{0.0f}));
                } else {
                    animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleX", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.sendButton, "scaleY", new float[]{0.1f}));
                    animators.add(ObjectAnimator.ofFloat(this.sendButton, "alpha", new float[]{0.0f}));
                }
                this.runningAnimation.playTogether(animators);
                this.runningAnimation.setDuration(150);
                this.runningAnimation.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
                            ChatActivityEnterView.this.sendButton.setVisibility(8);
                            ChatActivityEnterView.this.cancelBotButton.setVisibility(8);
                            ChatActivityEnterView.this.audioVideoButtonContainer.setVisibility(8);
                            ChatActivityEnterView.this.expandStickersButton.setVisibility(0);
                            ChatActivityEnterView.this.runningAnimation = null;
                            ChatActivityEnterView.this.runningAnimationType = 0;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (ChatActivityEnterView.this.runningAnimation != null && ChatActivityEnterView.this.runningAnimation.equals(animation)) {
                            ChatActivityEnterView.this.runningAnimation = null;
                        }
                    }
                });
                this.runningAnimation.start();
            }
        }
    }

    private void updateFieldRight(int attachVisible) {
        if (this.messageEditText != null && this.editingMessageObject == null) {
            LayoutParams layoutParams = (LayoutParams) this.messageEditText.getLayoutParams();
            if (attachVisible == 1) {
                if ((this.botButton == null || this.botButton.getVisibility() != 0) && (this.notifyButton == null || this.notifyButton.getVisibility() != 0)) {
                    layoutParams.rightMargin = AndroidUtilities.dp(50.0f);
                } else {
                    layoutParams.rightMargin = AndroidUtilities.dp(98.0f);
                }
            } else if (attachVisible != 2) {
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
                } catch (Exception e) {
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
                animatorArr[1] = ObjectAnimator.ofFloat(this.recordCircle, "scale", new float[]{0.0f});
                animatorArr[2] = ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{1.0f});
                animatorSet.playTogether(animatorArr);
                this.runningAnimationAudio.setDuration(300);
                this.runningAnimationAudio.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animator) {
                        if (ChatActivityEnterView.this.runningAnimationAudio != null && ChatActivityEnterView.this.runningAnimationAudio.equals(animator)) {
                            LayoutParams params = (LayoutParams) ChatActivityEnterView.this.slideText.getLayoutParams();
                            params.leftMargin = AndroidUtilities.dp(30.0f);
                            ChatActivityEnterView.this.slideText.setLayoutParams(params);
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
            } catch (Exception e2) {
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
            LayoutParams params = (LayoutParams) this.slideText.getLayoutParams();
            params.leftMargin = AndroidUtilities.dp(30.0f);
            this.slideText.setLayoutParams(params);
            this.slideText.setAlpha(1.0f);
            this.recordPanel.setX((float) AndroidUtilities.displaySize.x);
            if (this.runningAnimationAudio != null) {
                this.runningAnimationAudio.cancel();
            }
            this.runningAnimationAudio = new AnimatorSet();
            animatorSet = this.runningAnimationAudio;
            animatorArr = new Animator[3];
            animatorArr[0] = ObjectAnimator.ofFloat(this.recordPanel, "translationX", new float[]{0.0f});
            animatorArr[1] = ObjectAnimator.ofFloat(this.recordCircle, "scale", new float[]{1.0f});
            animatorArr[2] = ObjectAnimator.ofFloat(this.audioVideoButtonContainer, "alpha", new float[]{0.0f});
            animatorSet.playTogether(animatorArr);
            this.runningAnimationAudio.setDuration(300);
            this.runningAnimationAudio.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    if (ChatActivityEnterView.this.runningAnimationAudio != null && ChatActivityEnterView.this.runningAnimationAudio.equals(animator)) {
                        ChatActivityEnterView.this.recordPanel.setX(0.0f);
                        ChatActivityEnterView.this.runningAnimationAudio = null;
                    }
                }
            });
            this.runningAnimationAudio.setInterpolator(new DecelerateInterpolator());
            this.runningAnimationAudio.start();
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.recordingAudioVideo) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setDelegate(ChatActivityEnterViewDelegate chatActivityEnterViewDelegate) {
        this.delegate = chatActivityEnterViewDelegate;
    }

    public void setCommand(MessageObject messageObject, String command, boolean longPress, boolean username) {
        if (command != null && getVisibility() == 0) {
            User user;
            if (longPress) {
                String text = this.messageEditText.getText().toString();
                user = (messageObject == null || ((int) this.dialog_id) >= 0) ? null : MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
                if ((this.botCount != 1 || username) && user != null && user.bot && !command.contains("@")) {
                    text = String.format(Locale.US, "%s@%s", new Object[]{command, user.username}) + " " + text.replaceFirst("^/[a-zA-Z@\\d_]{1,255}(\\s|$)", "");
                } else {
                    text = command + " " + text.replaceFirst("^/[a-zA-Z@\\d_]{1,255}(\\s|$)", "");
                }
                this.ignoreTextChange = true;
                this.messageEditText.setText(text);
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
            user = (messageObject == null || ((int) this.dialog_id) >= 0) ? null : MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
            if ((this.botCount != 1 || username) && user != null && user.bot && !command.contains("@")) {
                SendMessagesHelper.getInstance().sendMessage(String.format(Locale.US, "%s@%s", new Object[]{command, user.username}), this.dialog_id, this.replyingMessageObject, null, false, null, null, null);
            } else {
                SendMessagesHelper.getInstance().sendMessage(command, this.dialog_id, this.replyingMessageObject, null, false, null, null, null);
            }
        }
    }

    public void setEditingMessageObject(MessageObject messageObject, boolean caption) {
        if (this.audioToSend == null && this.videoToSendMessageObject == null && this.editingMessageObject != messageObject) {
            if (this.editingMessageReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.editingMessageReqId, true);
                this.editingMessageReqId = 0;
            }
            this.editingMessageObject = messageObject;
            this.editingCaption = caption;
            if (this.editingMessageObject != null) {
                if (this.doneButtonAnimation != null) {
                    this.doneButtonAnimation.cancel();
                    this.doneButtonAnimation = null;
                }
                this.doneButtonContainer.setVisibility(0);
                showEditDoneProgress(true, false);
                InputFilter[] inputFilters = new InputFilter[1];
                if (caption) {
                    inputFilters[0] = new LengthFilter(200);
                    if (this.editingMessageObject.caption != null) {
                        setFieldText(Emoji.replaceEmoji(new SpannableStringBuilder(this.editingMessageObject.caption.toString()), this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false));
                    } else {
                        setFieldText("");
                    }
                } else {
                    inputFilters[0] = new LengthFilter(4096);
                    if (this.editingMessageObject.messageText != null) {
                        int a;
                        ArrayList<TLRPC$MessageEntity> entities = this.editingMessageObject.messageOwner.entities;
                        MessagesQuery.sortEntities(entities);
                        SpannableStringBuilder stringBuilder = new SpannableStringBuilder(this.editingMessageObject.messageText);
                        Object[] spansToRemove = stringBuilder.getSpans(0, stringBuilder.length(), Object.class);
                        if (spansToRemove != null && spansToRemove.length > 0) {
                            for (Object removeSpan : spansToRemove) {
                                stringBuilder.removeSpan(removeSpan);
                            }
                        }
                        if (entities != null) {
                            int addToOffset = 0;
                            for (a = 0; a < entities.size(); a++) {
                                TLRPC$MessageEntity entity = (TLRPC$MessageEntity) entities.get(a);
                                if ((entity.offset + entity.length) + addToOffset <= stringBuilder.length()) {
                                    if (entity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                                        if ((entity.offset + entity.length) + addToOffset < stringBuilder.length() && stringBuilder.charAt((entity.offset + entity.length) + addToOffset) == ' ') {
                                            entity.length++;
                                        }
                                        stringBuilder.setSpan(new URLSpanUserMention("" + ((TLRPC$TL_inputMessageEntityMentionName) entity).user_id.user_id, true), entity.offset + addToOffset, (entity.offset + entity.length) + addToOffset, 33);
                                    } else {
                                        try {
                                            if (entity instanceof TLRPC$TL_messageEntityCode) {
                                                stringBuilder.insert((entity.offset + entity.length) + addToOffset, "`");
                                                stringBuilder.insert(entity.offset + addToOffset, "`");
                                                addToOffset += 2;
                                            } else if (entity instanceof TLRPC$TL_messageEntityPre) {
                                                stringBuilder.insert((entity.offset + entity.length) + addToOffset, "```");
                                                stringBuilder.insert(entity.offset + addToOffset, "```");
                                                addToOffset += 6;
                                            } else if (entity instanceof TLRPC$TL_messageEntityBold) {
                                                stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), entity.offset + addToOffset, (entity.offset + entity.length) + addToOffset, 33);
                                            } else if (entity instanceof TLRPC$TL_messageEntityItalic) {
                                                stringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), entity.offset + addToOffset, (entity.offset + entity.length) + addToOffset, 33);
                                            }
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    }
                                }
                            }
                        }
                        setFieldText(Emoji.replaceEmoji(stringBuilder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false));
                    } else {
                        setFieldText("");
                    }
                }
                this.messageEditText.setFilters(inputFilters);
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
                this.sendButton.setAlpha(0.0f);
                this.cancelBotButton.setScaleX(0.1f);
                this.cancelBotButton.setScaleY(0.1f);
                this.cancelBotButton.setAlpha(0.0f);
                this.audioVideoButtonContainer.setScaleX(1.0f);
                this.audioVideoButtonContainer.setScaleY(1.0f);
                this.audioVideoButtonContainer.setAlpha(1.0f);
                this.sendButton.setVisibility(8);
                this.cancelBotButton.setVisibility(8);
                this.messageEditText.setText("");
                if (getVisibility() == 0) {
                    this.delegate.onAttachButtonShow();
                }
                updateFieldRight(1);
            }
            updateFieldHint();
        }
    }

    public ImageView getAttachButton() {
        return this.attachButton;
    }

    public ImageView getBotButton() {
        return this.botButton;
    }

    public ImageView getEmojiButton() {
        return this.emojiButton;
    }

    public ImageView getSendButton() {
        return this.sendButton;
    }

    public EmojiView getEmojiView() {
        return this.emojiView;
    }

    public void setFieldText(CharSequence text) {
        if (this.messageEditText != null) {
            this.ignoreTextChange = true;
            this.messageEditText.setText(text);
            this.messageEditText.setSelection(this.messageEditText.getText().length());
            this.ignoreTextChange = false;
            if (this.delegate != null) {
                this.delegate.onTextChanged(this.messageEditText.getText(), true);
            }
        }
    }

    public void setSelection(int start) {
        if (this.messageEditText != null) {
            this.messageEditText.setSelection(start, this.messageEditText.length());
        }
    }

    public int getCursorPosition() {
        if (this.messageEditText == null) {
            return 0;
        }
        return this.messageEditText.getSelectionStart();
    }

    public int getSelectionLength() {
        int i = 0;
        if (this.messageEditText == null) {
            return i;
        }
        try {
            return this.messageEditText.getSelectionEnd() - this.messageEditText.getSelectionStart();
        } catch (Exception e) {
            FileLog.e(e);
            return i;
        }
    }

    public void replaceWithText(int start, int len, CharSequence text, boolean parseEmoji) {
        try {
            SpannableStringBuilder builder = new SpannableStringBuilder(this.messageEditText.getText());
            builder.replace(start, start + len, text);
            if (parseEmoji) {
                Emoji.replaceEmoji(builder, this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            }
            this.messageEditText.setText(builder);
            this.messageEditText.setSelection(text.length() + start);
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void setFieldFocused() {
        if (this.messageEditText != null) {
            try {
                this.messageEditText.requestFocus();
            } catch (Exception e) {
                FileLog.e(e);
            }
        }
    }

    public void setFieldFocused(boolean focus) {
        if (this.messageEditText != null) {
            if (focus) {
                if (!this.messageEditText.isFocused()) {
                    this.messageEditText.postDelayed(new Runnable() {
                        public void run() {
                            if (ChatActivityEnterView.this.messageEditText != null) {
                                try {
                                    ChatActivityEnterView.this.messageEditText.requestFocus();
                                } catch (Exception e) {
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

    public boolean hasText() {
        return this.messageEditText != null && this.messageEditText.length() > 0;
    }

    public CharSequence getFieldText() {
        if (this.messageEditText == null || this.messageEditText.length() <= 0) {
            return null;
        }
        return this.messageEditText.getText();
    }

    private void updateBotButton() {
        if (this.botButton != null) {
            float f;
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
            if ((this.botButton == null || this.botButton.getVisibility() == 8) && (this.notifyButton == null || this.notifyButton.getVisibility() == 8)) {
                f = 48.0f;
            } else {
                f = 96.0f;
            }
            linearLayout.setPivotX((float) AndroidUtilities.dp(f));
        }
    }

    public void setBotsCount(int count, boolean hasCommands) {
        this.botCount = count;
        if (this.hasBotCommands != hasCommands) {
            this.hasBotCommands = hasCommands;
            updateBotButton();
        }
    }

    public void setButtons(MessageObject messageObject) {
        setButtons(messageObject, true);
    }

    public void setButtons(MessageObject messageObject, boolean openKeyboard) {
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
                        public void didPressedButton(TLRPC$KeyboardButton button) {
                            MessageObject object = ChatActivityEnterView.this.replyingMessageObject != null ? ChatActivityEnterView.this.replyingMessageObject : ((int) ChatActivityEnterView.this.dialog_id) < 0 ? ChatActivityEnterView.this.botButtonsMessageObject : null;
                            ChatActivityEnterView.this.didPressedBotButton(button, object, ChatActivityEnterView.this.replyingMessageObject != null ? ChatActivityEnterView.this.replyingMessageObject : ChatActivityEnterView.this.botButtonsMessageObject);
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
                    boolean keyboardHidden;
                    SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    if (preferences.getInt("hidekeyboard_" + this.dialog_id, 0) == messageObject.getId()) {
                        keyboardHidden = true;
                    } else {
                        keyboardHidden = false;
                    }
                    if (this.botButtonsMessageObject == this.replyingMessageObject || !this.botReplyMarkup.single_use || preferences.getInt("answered_" + this.dialog_id, 0) != messageObject.getId()) {
                        if (!(keyboardHidden || this.messageEditText.length() != 0 || isPopupShowing())) {
                            showPopup(1, 1);
                        }
                    } else {
                        return;
                    }
                } else if (isPopupShowing() && this.currentPopupContentType == 1) {
                    if (openKeyboard) {
                        openKeyboardInternal();
                    } else {
                        showPopup(0, 1);
                    }
                }
                updateBotButton();
            }
        }
    }

    public void didPressedBotButton(TLRPC$KeyboardButton button, MessageObject replyMessageObject, MessageObject messageObject) {
        if (button != null && messageObject != null) {
            if (button instanceof TLRPC$TL_keyboardButton) {
                SendMessagesHelper.getInstance().sendMessage(button.text, this.dialog_id, replyMessageObject, null, false, null, null, null);
            } else if (button instanceof TLRPC$TL_keyboardButtonUrl) {
                this.parentFragment.showOpenUrlAlert(button.url, true);
            } else if (button instanceof TLRPC$TL_keyboardButtonRequestPhone) {
                this.parentFragment.shareMyContact(messageObject);
            } else if (button instanceof TLRPC$TL_keyboardButtonRequestGeoLocation) {
                Builder builder = new Builder(this.parentActivity);
                builder.setTitle(LocaleController.getString("ShareYouLocationTitle", R.string.ShareYouLocationTitle));
                builder.setMessage(LocaleController.getString("ShareYouLocationInfo", R.string.ShareYouLocationInfo));
                r1 = messageObject;
                r2 = button;
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (VERSION.SDK_INT < 23 || ChatActivityEnterView.this.parentActivity.checkSelfPermission("android.permission.ACCESS_COARSE_LOCATION") == 0) {
                            SendMessagesHelper.getInstance().sendCurrentLocation(r1, r2);
                            return;
                        }
                        ChatActivityEnterView.this.parentActivity.requestPermissions(new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"}, 2);
                        ChatActivityEnterView.this.pendingMessageObject = r1;
                        ChatActivityEnterView.this.pendingLocationButton = r2;
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                this.parentFragment.showDialog(builder.create());
            } else if ((button instanceof TLRPC$TL_keyboardButtonCallback) || (button instanceof TLRPC$TL_keyboardButtonGame) || (button instanceof TLRPC$TL_keyboardButtonBuy)) {
                SendMessagesHelper.getInstance().sendCallback(true, messageObject, button, this.parentFragment);
            } else if ((button instanceof TLRPC$TL_keyboardButtonSwitchInline) && !this.parentFragment.processSwitchButton((TLRPC$TL_keyboardButtonSwitchInline) button)) {
                if (button.same_peer) {
                    int uid = messageObject.messageOwner.from_id;
                    if (messageObject.messageOwner.via_bot_id != 0) {
                        uid = messageObject.messageOwner.via_bot_id;
                    }
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
                    if (user != null) {
                        setFieldText("@" + user.username + " " + button.query);
                        return;
                    }
                    return;
                }
                Bundle args = new Bundle();
                args.putBoolean("onlySelect", true);
                args.putInt("dialogsType", 1);
                BaseFragment dialogsActivity = new DialogsActivity(args);
                r1 = messageObject;
                r2 = button;
                dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                    public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
                        int uid = r1.messageOwner.from_id;
                        if (r1.messageOwner.via_bot_id != 0) {
                            uid = r1.messageOwner.via_bot_id;
                        }
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(uid));
                        if (user == null) {
                            fragment.finishFragment();
                            return;
                        }
                        long did = ((Long) dids.get(0)).longValue();
                        DraftQuery.saveDraft(did, "@" + user.username + " " + r2.query, null, null, true);
                        if (did != ChatActivityEnterView.this.dialog_id) {
                            int lower_part = (int) did;
                            if (lower_part != 0) {
                                Bundle args = new Bundle();
                                if (lower_part > 0) {
                                    args.putInt("user_id", lower_part);
                                } else if (lower_part < 0) {
                                    args.putInt("chat_id", -lower_part);
                                }
                                if (MessagesController.checkCanOpenChat(args, fragment)) {
                                    if (!ChatActivityEnterView.this.parentFragment.presentFragment(new ChatActivity(args), true)) {
                                        fragment.finishFragment();
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
                            fragment.finishFragment();
                            return;
                        }
                        fragment.finishFragment();
                    }
                });
                this.parentFragment.presentFragment(dialogsActivity);
            }
        }
    }

    public boolean isPopupView(View view) {
        return view == this.botKeyboardView || view == this.emojiView;
    }

    public boolean isRecordCircle(View view) {
        return view == this.recordCircle;
    }

    private void createEmojiView() {
        if (this.emojiView == null) {
            this.emojiView = new EmojiView(this.allowStickers, this.allowGifs, this.parentActivity, this.info);
            this.emojiView.setVisibility(8);
            this.emojiView.setListener(new Listener() {

                /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$41$1 */
                class C25051 implements DialogInterface.OnClickListener {
                    C25051() {
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

                public void onEmojiSelected(String symbol) {
                    int i = ChatActivityEnterView.this.messageEditText.getSelectionEnd();
                    if (i < 0) {
                        i = 0;
                    }
                    try {
                        ChatActivityEnterView.this.innerTextChange = 2;
                        CharSequence localCharSequence = Emoji.replaceEmoji(symbol, ChatActivityEnterView.this.messageEditText.getPaint().getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        ChatActivityEnterView.this.messageEditText.setText(ChatActivityEnterView.this.messageEditText.getText().insert(i, localCharSequence));
                        int j = i + localCharSequence.length();
                        ChatActivityEnterView.this.messageEditText.setSelection(j, j);
                    } catch (Exception e) {
                        FileLog.e(e);
                    } finally {
                        ChatActivityEnterView.this.innerTextChange = 0;
                    }
                }

                public void onStickerSelected(TLRPC$Document sticker) {
                    if (ChatActivityEnterView.this.stickersExpanded) {
                        ChatActivityEnterView.this.setStickersExpanded(false, true);
                    }
                    ChatActivityEnterView.this.onStickerSelected(sticker);
                    StickersQuery.addRecentSticker(0, sticker, (int) (System.currentTimeMillis() / 1000), false);
                    if (((int) ChatActivityEnterView.this.dialog_id) == 0) {
                        MessagesController.getInstance().saveGif(sticker);
                    }
                }

                public void onStickersSettingsClick() {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        ChatActivityEnterView.this.parentFragment.presentFragment(new StickersActivity(0));
                    }
                }

                public void onGifSelected(TLRPC$Document gif) {
                    if (ChatActivityEnterView.this.stickersExpanded) {
                        ChatActivityEnterView.this.setStickersExpanded(false, true);
                    }
                    SendMessagesHelper.getInstance().sendSticker(gif, ChatActivityEnterView.this.dialog_id, ChatActivityEnterView.this.replyingMessageObject, ChatActivityEnterView.this.getContext());
                    StickersQuery.addRecentGif(gif, (int) (System.currentTimeMillis() / 1000));
                    if (((int) ChatActivityEnterView.this.dialog_id) == 0) {
                        MessagesController.getInstance().saveGif(gif);
                    }
                    if (ChatActivityEnterView.this.delegate != null) {
                        ChatActivityEnterView.this.delegate.onMessageSend(null);
                    }
                }

                public void onGifTab(boolean opened) {
                    ChatActivityEnterView.this.post(ChatActivityEnterView.this.updateExpandabilityRunnable);
                    if (!AndroidUtilities.usingHardwareInput) {
                        if (opened) {
                            if (ChatActivityEnterView.this.messageEditText.length() == 0) {
                                ChatActivityEnterView.this.messageEditText.setText("@gif ");
                                ChatActivityEnterView.this.messageEditText.setSelection(ChatActivityEnterView.this.messageEditText.length());
                            }
                        } else if (ChatActivityEnterView.this.messageEditText.getText().toString().equals("@gif ")) {
                            ChatActivityEnterView.this.messageEditText.setText("");
                        }
                    }
                }

                public void onStickersTab(boolean opened) {
                    ChatActivityEnterView.this.delegate.onStickersTab(opened);
                    ChatActivityEnterView.this.post(ChatActivityEnterView.this.updateExpandabilityRunnable);
                }

                public void onClearEmojiRecent() {
                    if (ChatActivityEnterView.this.parentFragment != null && ChatActivityEnterView.this.parentActivity != null) {
                        Builder builder = new Builder(ChatActivityEnterView.this.parentActivity);
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setMessage(LocaleController.getString("ClearRecentEmoji", R.string.ClearRecentEmoji));
                        builder.setPositiveButton(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), new C25051());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ChatActivityEnterView.this.parentFragment.showDialog(builder.create());
                    }
                }

                public void onShowStickerSet(TLRPC$StickerSet stickerSet, TLRPC$InputStickerSet inputStickerSet) {
                    if (ChatActivityEnterView.this.parentFragment != null && ChatActivityEnterView.this.parentActivity != null) {
                        if (stickerSet != null) {
                            inputStickerSet = new TLRPC$TL_inputStickerSetID();
                            inputStickerSet.access_hash = stickerSet.access_hash;
                            inputStickerSet.id = stickerSet.id;
                        }
                        ChatActivityEnterView.this.parentFragment.showDialog(new StickersAlert(ChatActivityEnterView.this.parentActivity, ChatActivityEnterView.this.parentFragment, inputStickerSet, null, ChatActivityEnterView.this));
                    }
                }

                public void onStickerSetAdd(TLRPC$StickerSetCovered stickerSet) {
                    StickersQuery.removeStickersSet(ChatActivityEnterView.this.parentActivity, stickerSet.set, 2, ChatActivityEnterView.this.parentFragment, false);
                }

                public void onStickerSetRemove(TLRPC$StickerSetCovered stickerSet) {
                    StickersQuery.removeStickersSet(ChatActivityEnterView.this.parentActivity, stickerSet.set, 0, ChatActivityEnterView.this.parentFragment, false);
                }

                public void onStickersGroupClick(int chatId) {
                    if (ChatActivityEnterView.this.parentFragment != null) {
                        if (AndroidUtilities.isTablet()) {
                            ChatActivityEnterView.this.hidePopup(false);
                        }
                        GroupStickersActivity fragment = new GroupStickersActivity(chatId);
                        fragment.setInfo(ChatActivityEnterView.this.info);
                        ChatActivityEnterView.this.parentFragment.presentFragment(fragment);
                    }
                }
            });
            this.emojiView.setDragListener(new DragListener() {
                int initialOffset;
                boolean wasExpanded;

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

                public void onDragEnd(float velocity) {
                    boolean z = false;
                    if (allowDragging()) {
                        ChatActivityEnterView.this.stickersDragging = false;
                        if ((!this.wasExpanded || velocity < ((float) AndroidUtilities.dp(200.0f))) && ((this.wasExpanded || velocity > ((float) AndroidUtilities.dp(-200.0f))) && ((!this.wasExpanded || ChatActivityEnterView.this.stickersExpansionProgress > 0.6f) && (this.wasExpanded || ChatActivityEnterView.this.stickersExpansionProgress < 0.4f)))) {
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

                public void onDragCancel() {
                    if (ChatActivityEnterView.this.stickersTabOpen) {
                        ChatActivityEnterView.this.stickersDragging = false;
                        ChatActivityEnterView.this.setStickersExpanded(this.wasExpanded, true);
                    }
                }

                public void onDrag(int offset) {
                    if (allowDragging()) {
                        int origHeight = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? ChatActivityEnterView.this.keyboardHeightLand : ChatActivityEnterView.this.keyboardHeight;
                        offset = Math.max(Math.min(offset + this.initialOffset, 0), -(ChatActivityEnterView.this.stickersExpandedHeight - origHeight));
                        ChatActivityEnterView.this.emojiView.setTranslationY((float) offset);
                        ChatActivityEnterView.this.setTranslationY((float) offset);
                        ChatActivityEnterView.this.stickersExpansionProgress = ((float) offset) / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - origHeight)));
                        ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                    }
                }

                private boolean allowDragging() {
                    return ChatActivityEnterView.this.stickersTabOpen && ((ChatActivityEnterView.this.stickersExpanded || ChatActivityEnterView.this.messageEditText.length() <= 0) && ChatActivityEnterView.this.emojiView.areThereAnyStickers());
                }
            });
            this.emojiView.setVisibility(8);
            this.sizeNotifierLayout.addView(this.emojiView);
            checkChannelRights();
        }
    }

    public void onStickerSelected(TLRPC$Document sticker) {
        SendMessagesHelper.getInstance().sendSticker(sticker, this.dialog_id, this.replyingMessageObject, getContext());
        if (this.delegate != null) {
            this.delegate.onMessageSend(null);
        }
    }

    public void addStickerToRecent(TLRPC$Document sticker) {
        createEmojiView();
        this.emojiView.addRecentSticker(sticker);
    }

    private void showPopup(int show, int contentType) {
        if (show == 1) {
            if (contentType == 0 && this.emojiView == null) {
                if (this.parentActivity != null) {
                    createEmojiView();
                } else {
                    return;
                }
            }
            View currentView = null;
            if (contentType == 0) {
                this.emojiView.setVisibility(0);
                if (!(this.botKeyboardView == null || this.botKeyboardView.getVisibility() == 8)) {
                    this.botKeyboardView.setVisibility(8);
                }
                currentView = this.emojiView;
            } else if (contentType == 1) {
                if (!(this.emojiView == null || this.emojiView.getVisibility() == 8)) {
                    this.emojiView.setVisibility(8);
                }
                this.botKeyboardView.setVisibility(0);
                currentView = this.botKeyboardView;
            }
            this.currentPopupContentType = contentType;
            if (this.keyboardHeight <= 0) {
                this.keyboardHeight = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height", AndroidUtilities.dp(200.0f));
            }
            if (this.keyboardHeightLand <= 0) {
                this.keyboardHeightLand = ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).getInt("kbd_height_land3", AndroidUtilities.dp(200.0f));
            }
            int currentHeight = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight;
            if (contentType == 1) {
                currentHeight = Math.min(this.botKeyboardView.getKeyboardHeight(), currentHeight);
            }
            if (this.botKeyboardView != null) {
                this.botKeyboardView.setPanelHeight(currentHeight);
            }
            LayoutParams layoutParams = (LayoutParams) currentView.getLayoutParams();
            layoutParams.height = currentHeight;
            currentView.setLayoutParams(layoutParams);
            if (!AndroidUtilities.isInMultiwindow) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
            if (this.sizeNotifierLayout != null) {
                this.emojiPadding = currentHeight;
                this.sizeNotifierLayout.requestLayout();
                if (contentType == 0) {
                    this.emojiButton.setImageResource(R.drawable.ic_msg_panel_kb);
                } else if (contentType == 1) {
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
                if (show == 0) {
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
        if (this.stickersExpanded && show != 1) {
            setStickersExpanded(false, false);
        }
    }

    private void setEmojiButtonImage() {
        int currentPage;
        if (this.emojiView == null) {
            currentPage = getContext().getSharedPreferences("emoji", 0).getInt("selected_page", 0);
        } else {
            currentPage = this.emojiView.getCurrentPage();
        }
        if (currentPage == 0 || !(this.allowStickers || this.allowGifs)) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_smiles);
        } else if (currentPage == 1) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_stickers);
        } else if (currentPage == 2) {
            this.emojiButton.setImageResource(R.drawable.ic_msg_panel_gif);
        }
    }

    public void hidePopup(boolean byBackButton) {
        if (isPopupShowing()) {
            if (this.currentPopupContentType == 1 && byBackButton && this.botButtonsMessageObject != null) {
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("hidekeyboard_" + this.dialog_id, this.botButtonsMessageObject.getId()).commit();
            }
            showPopup(0, 0);
            removeGifFromInputField();
        }
    }

    private void removeGifFromInputField() {
        if (!AndroidUtilities.usingHardwareInput && this.messageEditText.getText().toString().equals("@gif ")) {
            this.messageEditText.setText("");
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

    public boolean isEditingMessage() {
        return this.editingMessageObject != null;
    }

    public MessageObject getEditingMessageObject() {
        return this.editingMessageObject;
    }

    public boolean isEditingCaption() {
        return this.editingCaption;
    }

    public boolean hasAudioToSend() {
        return (this.audioToSendMessageObject == null && this.videoToSendMessageObject == null) ? false : true;
    }

    public void openKeyboard() {
        AndroidUtilities.showKeyboard(this.messageEditText);
    }

    public void closeKeyboard() {
        AndroidUtilities.hideKeyboard(this.messageEditText);
    }

    public boolean isPopupShowing() {
        return (this.emojiView != null && this.emojiView.getVisibility() == 0) || (this.botKeyboardView != null && this.botKeyboardView.getVisibility() == 0);
    }

    public boolean isKeyboardVisible() {
        return this.keyboardVisible;
    }

    public void addRecentGif(TLRPC$Document searchImage) {
        StickersQuery.addRecentGif(searchImage, (int) (System.currentTimeMillis() / 1000));
        if (this.emojiView != null) {
            this.emojiView.addRecentGif(searchImage);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w != oldw && this.stickersExpanded) {
            setStickersExpanded(false, false);
        }
        this.videoTimelineView.clearFrames();
    }

    public void onSizeChanged(int height, boolean isWidthGreater) {
        boolean z = true;
        if (height > AndroidUtilities.dp(50.0f) && this.keyboardVisible && !AndroidUtilities.isInMultiwindow) {
            if (isWidthGreater) {
                this.keyboardHeightLand = height;
                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height_land3", this.keyboardHeightLand).commit();
            } else {
                this.keyboardHeight = height;
                ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putInt("kbd_height", this.keyboardHeight).commit();
            }
        }
        if (isPopupShowing()) {
            int newHeight = isWidthGreater ? this.keyboardHeightLand : this.keyboardHeight;
            int popupSize = AndroidUtilities.dp((float) ((ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0).getInt("emojiPopupSize", 60) - 40) * 10));
            if (popupSize >= newHeight) {
                newHeight = popupSize;
            }
            if (this.currentPopupContentType == 1 && !this.botKeyboardView.isFullSize()) {
                newHeight = Math.min(this.botKeyboardView.getKeyboardHeight(), newHeight);
            }
            View currentView = null;
            if (this.currentPopupContentType == 0) {
                currentView = this.emojiView;
            } else if (this.currentPopupContentType == 1) {
                currentView = this.botKeyboardView;
            }
            if (this.botKeyboardView != null) {
                this.botKeyboardView.setPanelHeight(newHeight);
            }
            LayoutParams layoutParams = (LayoutParams) currentView.getLayoutParams();
            if (!((layoutParams.width == AndroidUtilities.displaySize.x && layoutParams.height == newHeight) || this.stickersExpanded)) {
                layoutParams.width = AndroidUtilities.displaySize.x;
                layoutParams.height = newHeight;
                currentView.setLayoutParams(layoutParams);
                if (this.sizeNotifierLayout != null) {
                    this.emojiPadding = layoutParams.height;
                    this.sizeNotifierLayout.requestLayout();
                    onWindowSizeChanged();
                }
            }
        }
        if (this.lastSizeChangeValue1 == height && this.lastSizeChangeValue2 == isWidthGreater) {
            onWindowSizeChanged();
            return;
        }
        this.lastSizeChangeValue1 = height;
        this.lastSizeChangeValue2 = isWidthGreater;
        boolean oldValue = this.keyboardVisible;
        if (height <= 0) {
            z = false;
        }
        this.keyboardVisible = z;
        if (this.keyboardVisible && isPopupShowing()) {
            showPopup(0, this.currentPopupContentType);
        }
        if (!(this.emojiPadding == 0 || this.keyboardVisible || this.keyboardVisible == oldValue || isPopupShowing())) {
            this.emojiPadding = 0;
            this.sizeNotifierLayout.requestLayout();
        }
        if (this.keyboardVisible && this.waitingForKeyboardOpen) {
            this.waitingForKeyboardOpen = false;
            AndroidUtilities.cancelRunOnUIThread(this.openKeyboardRunnable);
        }
        onWindowSizeChanged();
    }

    public int getEmojiPadding() {
        return this.emojiPadding;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.emojiDidLoaded) {
            if (this.emojiView != null) {
                this.emojiView.invalidateViews();
            }
            if (this.botKeyboardView != null) {
                this.botKeyboardView.invalidateViews();
            }
        } else if (id == NotificationCenter.recordProgressChanged) {
            long t = ((Long) args[0]).longValue();
            long time = t / 1000;
            int ms = ((int) (t % 1000)) / 10;
            String str = String.format("%02d:%02d.%02d", new Object[]{Long.valueOf(time / 60), Long.valueOf(time % 60), Integer.valueOf(ms)});
            if (this.lastTimeString == null || !this.lastTimeString.equals(str)) {
                if (this.lastTypingSendTime != time && time % 5 == 0) {
                    this.lastTypingSendTime = time;
                    MessagesController instance = MessagesController.getInstance();
                    long j = this.dialog_id;
                    int i = (this.videoSendButton == null || this.videoSendButton.getTag() == null) ? 1 : 7;
                    instance.sendTyping(j, i, 0);
                }
                if (this.recordTimeText != null) {
                    this.recordTimeText.setText(str);
                }
            }
            if (this.recordCircle != null) {
                this.recordCircle.setAmplitude(((Double) args[1]).doubleValue());
            }
            if (this.videoSendButton != null && this.videoSendButton.getTag() != null && t >= 59500) {
                this.startedDraggingX = -1.0f;
                this.delegate.needStartRecordVideo(3);
            }
        } else if (id == NotificationCenter.closeChats) {
            if (this.messageEditText != null && this.messageEditText.isFocused()) {
                AndroidUtilities.hideKeyboard(this.messageEditText);
            }
        } else if (id == NotificationCenter.recordStartError || id == NotificationCenter.recordStopped) {
            if (this.recordingAudioVideo) {
                MessagesController.getInstance().sendTyping(this.dialog_id, 2, 0);
                this.recordingAudioVideo = false;
                updateRecordIntefrace();
            }
            if (id == NotificationCenter.recordStopped) {
                Integer reason = args[0];
                if (reason.intValue() == 2) {
                    this.videoTimelineView.setVisibility(0);
                    this.recordedAudioBackground.setVisibility(8);
                    this.recordedAudioTimeTextView.setVisibility(8);
                    this.recordedAudioPlayButton.setVisibility(8);
                    this.recordedAudioSeekBar.setVisibility(8);
                    this.recordedAudioPanel.setAlpha(1.0f);
                    this.recordedAudioPanel.setVisibility(0);
                } else if (reason.intValue() != 1) {
                }
            }
        } else if (id == NotificationCenter.recordStarted) {
            if (!this.recordingAudioVideo) {
                this.recordingAudioVideo = true;
                updateRecordIntefrace();
            }
        } else if (id == NotificationCenter.audioDidSent) {
            Object audio = args[0];
            if (audio instanceof VideoEditedInfo) {
                this.videoToSendMessageObject = (VideoEditedInfo) audio;
                this.audioToSendPath = (String) args[1];
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
            this.audioToSend = (TLRPC$TL_document) args[0];
            this.audioToSendPath = (String) args[1];
            if (this.audioToSend != null) {
                if (this.recordedAudioPanel != null) {
                    int a;
                    DocumentAttribute attribute;
                    this.videoTimelineView.setVisibility(8);
                    this.recordedAudioBackground.setVisibility(0);
                    this.recordedAudioTimeTextView.setVisibility(0);
                    this.recordedAudioPlayButton.setVisibility(0);
                    this.recordedAudioSeekBar.setVisibility(0);
                    TLRPC$TL_message message = new TLRPC$TL_message();
                    message.out = true;
                    message.id = 0;
                    message.to_id = new TLRPC$TL_peerUser();
                    TLRPC$Peer tLRPC$Peer = message.to_id;
                    int clientUserId = UserConfig.getClientUserId();
                    message.from_id = clientUserId;
                    tLRPC$Peer.user_id = clientUserId;
                    message.date = (int) (System.currentTimeMillis() / 1000);
                    message.message = "-1";
                    message.attachPath = this.audioToSendPath;
                    message.media = new TLRPC$TL_messageMediaDocument();
                    TLRPC$MessageMedia tLRPC$MessageMedia = message.media;
                    tLRPC$MessageMedia.flags |= 3;
                    message.media.document = this.audioToSend;
                    message.flags |= 768;
                    this.audioToSendMessageObject = new MessageObject(message, null, false);
                    this.recordedAudioPanel.setAlpha(1.0f);
                    this.recordedAudioPanel.setVisibility(0);
                    int duration = 0;
                    for (a = 0; a < this.audioToSend.attributes.size(); a++) {
                        attribute = (DocumentAttribute) this.audioToSend.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            duration = attribute.duration;
                            break;
                        }
                    }
                    for (a = 0; a < this.audioToSend.attributes.size(); a++) {
                        attribute = (DocumentAttribute) this.audioToSend.attributes.get(a);
                        if (attribute instanceof TLRPC$TL_documentAttributeAudio) {
                            if (attribute.waveform == null || attribute.waveform.length == 0) {
                                attribute.waveform = MediaController.getInstance().getWaveform(this.audioToSendPath);
                            }
                            this.recordedAudioSeekBar.setWaveform(attribute.waveform);
                            this.recordedAudioTimeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)}));
                            closeKeyboard();
                            hidePopup(false);
                            checkSendButton(false);
                        }
                    }
                    this.recordedAudioTimeTextView.setText(String.format("%d:%02d", new Object[]{Integer.valueOf(duration / 60), Integer.valueOf(duration % 60)}));
                    closeKeyboard();
                    hidePopup(false);
                    checkSendButton(false);
                }
            } else if (this.delegate != null) {
                this.delegate.onMessageSend(null);
            }
        } else if (id == NotificationCenter.audioRouteChanged) {
            if (this.parentActivity != null) {
                this.parentActivity.setVolumeControlStream(((Boolean) args[0]).booleanValue() ? 0 : Integer.MIN_VALUE);
            }
        } else if (id == NotificationCenter.messagePlayingDidReset) {
            if (this.audioToSendMessageObject != null && !MediaController.getInstance().isPlayingMessage(this.audioToSendMessageObject)) {
                this.recordedAudioPlayButton.setImageDrawable(this.playDrawable);
                this.recordedAudioSeekBar.setProgress(0.0f);
            }
        } else if (id == NotificationCenter.messagePlayingProgressDidChanged) {
            Integer mid = args[0];
            if (this.audioToSendMessageObject != null && MediaController.getInstance().isPlayingMessage(this.audioToSendMessageObject)) {
                MessageObject player = MediaController.getInstance().getPlayingMessageObject();
                this.audioToSendMessageObject.audioProgress = player.audioProgress;
                this.audioToSendMessageObject.audioProgressSec = player.audioProgressSec;
                if (!this.recordedAudioSeekBar.isDragging()) {
                    this.recordedAudioSeekBar.setProgress(this.audioToSendMessageObject.audioProgress);
                }
            }
        } else if (id == NotificationCenter.featuredStickersDidLoaded && this.emojiButton != null) {
            this.emojiButton.invalidate();
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 2 && this.pendingLocationButton != null) {
            if (grantResults.length > 0 && grantResults[0] == 0) {
                SendMessagesHelper.getInstance().sendCurrentLocation(this.pendingMessageObject, this.pendingLocationButton);
            }
            this.pendingLocationButton = null;
            this.pendingMessageObject = null;
        }
    }

    private void setStickersExpanded(boolean expanded, boolean animated) {
        if (this.emojiView == null) {
            return;
        }
        if (!expanded || this.emojiView.areThereAnyStickers()) {
            this.stickersExpanded = expanded;
            final int origHeight = AndroidUtilities.displaySize.x > AndroidUtilities.displaySize.y ? this.keyboardHeightLand : this.keyboardHeight;
            if (this.stickersExpansionAnim != null) {
                this.stickersExpansionAnim.cancel();
                this.stickersExpansionAnim = null;
            }
            AnimatorSet anims;
            Animator[] animatorArr;
            if (this.stickersExpanded) {
                int i;
                int height = this.sizeNotifierLayout.getHeight();
                if (VERSION.SDK_INT >= 21) {
                    i = AndroidUtilities.statusBarHeight;
                } else {
                    i = 0;
                }
                this.stickersExpandedHeight = (((height - i) - ActionBar.getCurrentActionBarHeight()) - getHeight()) + Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                this.emojiView.getLayoutParams().height = this.stickersExpandedHeight;
                this.sizeNotifierLayout.requestLayout();
                this.sizeNotifierLayout.setForeground(new ScrimDrawable());
                this.messageEditText.setText(this.messageEditText.getText());
                if (animated) {
                    anims = new AnimatorSet();
                    animatorArr = new Animator[3];
                    animatorArr[0] = ObjectAnimator.ofInt(this, this.roundedTranslationYProperty, new int[]{-(this.stickersExpandedHeight - origHeight)});
                    animatorArr[1] = ObjectAnimator.ofInt(this.emojiView, this.roundedTranslationYProperty, new int[]{-(this.stickersExpandedHeight - origHeight)});
                    animatorArr[2] = ObjectAnimator.ofFloat(this.stickersArrow, "animationProgress", new float[]{1.0f});
                    anims.playTogether(animatorArr);
                    anims.setDuration(400);
                    anims.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                    ((ObjectAnimator) anims.getChildAnimations().get(0)).addUpdateListener(new AnimatorUpdateListener() {
                        public void onAnimationUpdate(ValueAnimator animation) {
                            ChatActivityEnterView.this.stickersExpansionProgress = ChatActivityEnterView.this.getTranslationY() / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - origHeight)));
                            ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                        }
                    });
                    anims.addListener(new AnimatorListenerAdapter() {
                        public void onAnimationEnd(Animator animation) {
                            ChatActivityEnterView.this.stickersExpansionAnim = null;
                            ChatActivityEnterView.this.emojiView.setLayerType(0, null);
                        }
                    });
                    this.stickersExpansionAnim = anims;
                    this.emojiView.setLayerType(2, null);
                    anims.start();
                    return;
                }
                this.stickersExpansionProgress = 1.0f;
                setTranslationY((float) (-(this.stickersExpandedHeight - origHeight)));
                this.emojiView.setTranslationY((float) (-(this.stickersExpandedHeight - origHeight)));
                this.stickersArrow.setAnimationProgress(1.0f);
            } else if (animated) {
                anims = new AnimatorSet();
                animatorArr = new Animator[3];
                animatorArr[0] = ObjectAnimator.ofInt(this, this.roundedTranslationYProperty, new int[]{0});
                animatorArr[1] = ObjectAnimator.ofInt(this.emojiView, this.roundedTranslationYProperty, new int[]{0});
                animatorArr[2] = ObjectAnimator.ofFloat(this.stickersArrow, "animationProgress", new float[]{0.0f});
                anims.playTogether(animatorArr);
                anims.setDuration(400);
                anims.setInterpolator(CubicBezierInterpolator.EASE_OUT_QUINT);
                ((ObjectAnimator) anims.getChildAnimations().get(0)).addUpdateListener(new AnimatorUpdateListener() {
                    public void onAnimationUpdate(ValueAnimator animation) {
                        ChatActivityEnterView.this.stickersExpansionProgress = ChatActivityEnterView.this.getTranslationY() / ((float) (-(ChatActivityEnterView.this.stickersExpandedHeight - origHeight)));
                        ChatActivityEnterView.this.sizeNotifierLayout.invalidate();
                    }
                });
                anims.addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        ChatActivityEnterView.this.stickersExpansionAnim = null;
                        ChatActivityEnterView.this.emojiView.getLayoutParams().height = origHeight;
                        ChatActivityEnterView.this.sizeNotifierLayout.requestLayout();
                        ChatActivityEnterView.this.emojiView.setLayerType(0, null);
                        ChatActivityEnterView.this.sizeNotifierLayout.setForeground(null);
                        ChatActivityEnterView.this.sizeNotifierLayout.setWillNotDraw(false);
                    }
                });
                this.stickersExpansionAnim = anims;
                this.emojiView.setLayerType(2, null);
                anims.start();
            } else {
                this.stickersExpansionProgress = 0.0f;
                setTranslationY(0.0f);
                this.emojiView.setTranslationY(0.0f);
                this.emojiView.getLayoutParams().height = origHeight;
                this.sizeNotifierLayout.requestLayout();
                this.sizeNotifierLayout.setForeground(null);
                this.sizeNotifierLayout.setWillNotDraw(false);
                this.stickersArrow.setAnimationProgress(0.0f);
            }
        }
    }

    private void CopyAssets() {
        Exception e;
        AssetManager assetManager = ApplicationLoader.applicationContext.getAssets();
        String[] files = null;
        try {
            files = assetManager.list("data");
        } catch (IOException e2) {
            Log.e("tag", e2.getMessage());
        }
        for (String filename : files) {
            if (filename.contentEquals("white_bg.jpg")) {
                System.out.println("File name => " + filename);
                try {
                    InputStream in = assetManager.open("data/" + filename);
                    OutputStream out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() + "/" + filename);
                    try {
                        copyFile(in, out);
                        in.close();
                        out.flush();
                        out.close();
                    } catch (Exception e3) {
                        e = e3;
                        OutputStream outputStream = out;
                        Log.e("tag", e.getMessage());
                    }
                } catch (Exception e4) {
                    e = e4;
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int read = in.read(buffer);
            if (read != -1) {
                out.write(buffer, 0, read);
            } else {
                return;
            }
        }
    }

    private void showPaymentSheet1() {
        if (this.parentActivity != null) {
            final BottomSheetDialog sheetDialog = new BottomSheetDialog(this.parentActivity);
            sheetDialog.setContentView(R.layout.bottom_sheet_payment_request);
            final TextView btnSendRequest = (TextView) sheetDialog.findViewById(R.id.btn_send_request);
            TextView btnClose = (TextView) sheetDialog.findViewById(R.id.btn_close);
            final NumericEditText etAmount = (NumericEditText) sheetDialog.findViewById(R.id.et_amount);
            final FarsiEditText etDesc = (FarsiEditText) sheetDialog.findViewById(R.id.et_desc);
            final ProgressBar progressBar = (ProgressBar) sheetDialog.findViewById(R.id.pb_loading);
            final View loadingBackground = sheetDialog.findViewById(R.id.rl_fade);
            btnSendRequest.setOnClickListener(new OnClickListener() {

                /* renamed from: org.telegram.ui.Components.ChatActivityEnterView$47$1 */
                class C25061 implements IResponseReceiver {
                    C25061() {
                    }

                    public void onResult(Object object, int StatusCode) {
                        progressBar.setVisibility(8);
                        loadingBackground.setVisibility(8);
                        switch (StatusCode) {
                            case Constants.ERROR_GENERATE_PAYMENT_LINK /*-32*/:
                                ToastUtil.AppToast(ApplicationLoader.applicationContext, "خطلا در دریافت اطلاعات").show();
                                btnSendRequest.setEnabled(true);
                                return;
                            case 32:
                                PaymentLink paymentLink = (PaymentLink) object;
                                Log.d("alireza", "alireza payment" + paymentLink.getLink());
                                ChatActivityEnterView.this.messageEditText.setText(paymentLink.getLink());
                                ChatActivityEnterView.this.sendMessage();
                                sheetDialog.dismiss();
                                return;
                            default:
                                return;
                        }
                    }
                }

                public void onClick(View view) {
                    if (!TextUtils.isEmpty(etAmount.getText().toString())) {
                        btnSendRequest.setEnabled(false);
                        progressBar.setVisibility(0);
                        loadingBackground.setVisibility(0);
                        AndroidUtilities.hideKeyboard(etDesc);
                        AndroidUtilities.hideKeyboard(etAmount);
                        HandleRequest.getNew(ApplicationLoader.applicationContext, new C25061()).generatePaymentLink(ChatActivityEnterView.this.dialog_id, (long) UserConfig.getClientUserId(), Long.parseLong(etAmount.getClearText()), etDesc.getText().toString());
                    }
                }
            });
            btnClose.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    sheetDialog.dismiss();
                }
            });
            sheetDialog.show();
        }
    }

    public void onResult(Object object, int StatusCode) {
        switch (StatusCode) {
            case Constants.ERROR_CHECK_REGISTER_STATUS /*-29*/:
                if (this.dialogLoading != null) {
                    this.dialogLoading.dismiss();
                    return;
                }
                return;
            case 29:
                if (this.dialogLoading != null) {
                    this.dialogLoading.dismiss();
                }
                org.telegram.customization.Model.Payment.User user = (org.telegram.customization.Model.Payment.User) object;
                Bundle args = new Bundle();
                if (user.getStatus() == 81) {
                    args.putInt("user_status", 81);
                    this.parentFragment.presentFragment(new PaymentMainActivity(args));
                    return;
                } else if (user.getStatus() == 80) {
                    args.putInt("user_status", 80);
                    this.parentFragment.presentFragment(new PaymentMainActivity(args));
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

    private void showLoadingDialog(Context context, String title, String message) {
        if (this.parentActivity != null) {
            this.dialogLoading = new ProgressDialog(this.parentActivity);
            this.dialogLoading.setTitle(title);
            this.dialogLoading.setMessage(message);
            this.dialogLoading.show();
        }
    }
}
