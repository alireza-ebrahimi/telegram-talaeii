package org.telegram.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.ChatActivityEnterView;
import org.telegram.ui.Components.ChatActivityEnterView.ChatActivityEnterViewDelegate;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.PlayingGameDrawable;
import org.telegram.ui.Components.PopupAudioView;
import org.telegram.ui.Components.RecordStatusDrawable;
import org.telegram.ui.Components.RoundStatusDrawable;
import org.telegram.ui.Components.SendingFileDrawable;
import org.telegram.ui.Components.SizeNotifierFrameLayout;
import org.telegram.ui.Components.StatusDrawable;
import org.telegram.ui.Components.TypingDotsDrawable;

public class PopupNotificationActivity extends Activity implements NotificationCenterDelegate {
    private ActionBar actionBar;
    private boolean animationInProgress = false;
    private long animationStartTime = 0;
    private ArrayList<ViewGroup> audioViews = new ArrayList();
    private FrameLayout avatarContainer;
    private BackupImageView avatarImageView;
    private ViewGroup centerView;
    private ChatActivityEnterView chatActivityEnterView;
    private int classGuid;
    private TextView countText;
    private Chat currentChat;
    private int currentMessageNum = 0;
    private MessageObject currentMessageObject = null;
    private User currentUser;
    private boolean finished = false;
    private ArrayList<ViewGroup> imageViews = new ArrayList();
    private boolean isReply;
    private CharSequence lastPrintString;
    private ViewGroup leftView;
    private ViewGroup messageContainer;
    private float moveStartX = -1.0f;
    private TextView nameTextView;
    private Runnable onAnimationEndRunnable = null;
    private TextView onlineTextView;
    private RelativeLayout popupContainer;
    private ArrayList<MessageObject> popupMessages = new ArrayList();
    private ViewGroup rightView;
    private boolean startedMoving = false;
    private StatusDrawable[] statusDrawables = new StatusDrawable[5];
    private ArrayList<ViewGroup> textViews = new ArrayList();
    private VelocityTracker velocityTracker = null;
    private WakeLock wakeLock = null;

    /* renamed from: org.telegram.ui.PopupNotificationActivity$2 */
    class C50822 implements ChatActivityEnterViewDelegate {
        C50822() {
        }

        public void didPressedAttachButton() {
        }

        public void needChangeVideoPreviewState(int i, float f) {
        }

        public void needSendTyping() {
            if (PopupNotificationActivity.this.currentMessageObject != null) {
                MessagesController.getInstance().sendTyping(PopupNotificationActivity.this.currentMessageObject.getDialogId(), 0, PopupNotificationActivity.this.classGuid);
            }
        }

        public void needShowMediaBanHint() {
        }

        public void needStartRecordAudio(int i) {
        }

        public void needStartRecordVideo(int i) {
        }

        public void onAttachButtonHidden() {
        }

        public void onAttachButtonShow() {
        }

        public void onMessageEditEnd(boolean z) {
        }

        public void onMessageSend(CharSequence charSequence) {
            if (PopupNotificationActivity.this.currentMessageObject != null) {
                if (PopupNotificationActivity.this.currentMessageNum >= 0 && PopupNotificationActivity.this.currentMessageNum < PopupNotificationActivity.this.popupMessages.size()) {
                    PopupNotificationActivity.this.popupMessages.remove(PopupNotificationActivity.this.currentMessageNum);
                }
                MessagesController.getInstance().markDialogAsRead(PopupNotificationActivity.this.currentMessageObject.getDialogId(), PopupNotificationActivity.this.currentMessageObject.getId(), Math.max(0, PopupNotificationActivity.this.currentMessageObject.getId()), PopupNotificationActivity.this.currentMessageObject.messageOwner.date, true, true);
                PopupNotificationActivity.this.currentMessageObject = null;
                PopupNotificationActivity.this.getNewMessage();
            }
        }

        public void onPreAudioVideoRecord() {
        }

        public void onStickersTab(boolean z) {
        }

        public void onSwitchRecordMode(boolean z) {
        }

        public void onTextChanged(CharSequence charSequence, boolean z) {
        }

        public void onWindowSizeChanged(int i) {
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$3 */
    class C50833 extends ActionBarMenuOnItemClick {
        C50833() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PopupNotificationActivity.this.onFinish();
                PopupNotificationActivity.this.finish();
            } else if (i == 1) {
                PopupNotificationActivity.this.openCurrentMessage();
            } else if (i == 2) {
                PopupNotificationActivity.this.switchToNextMessage();
            }
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$4 */
    class C50844 implements OnClickListener {
        C50844() {
        }

        @TargetApi(9)
        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + ApplicationLoader.applicationContext.getPackageName()));
                PopupNotificationActivity.this.startActivity(intent);
            } catch (Throwable e) {
                FileLog.e(e);
            }
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$5 */
    class C50855 implements Runnable {
        C50855() {
        }

        public void run() {
            PopupNotificationActivity.this.animationInProgress = false;
            PopupNotificationActivity.this.switchToPreviousMessage();
            AndroidUtilities.unlockOrientation(PopupNotificationActivity.this);
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$6 */
    class C50866 implements Runnable {
        C50866() {
        }

        public void run() {
            PopupNotificationActivity.this.animationInProgress = false;
            PopupNotificationActivity.this.switchToNextMessage();
            AndroidUtilities.unlockOrientation(PopupNotificationActivity.this);
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$7 */
    class C50877 implements Runnable {
        C50877() {
        }

        public void run() {
            PopupNotificationActivity.this.animationInProgress = false;
            PopupNotificationActivity.this.applyViewsLayoutParams(0);
            AndroidUtilities.unlockOrientation(PopupNotificationActivity.this);
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$8 */
    class C50888 implements View.OnClickListener {
        C50888() {
        }

        public void onClick(View view) {
            PopupNotificationActivity.this.openCurrentMessage();
        }
    }

    /* renamed from: org.telegram.ui.PopupNotificationActivity$9 */
    class C50899 implements View.OnClickListener {
        C50899() {
        }

        public void onClick(View view) {
            PopupNotificationActivity.this.openCurrentMessage();
        }
    }

    public class FrameLayoutAnimationListener extends FrameLayout {
        public FrameLayoutAnimationListener(Context context) {
            super(context);
        }

        public FrameLayoutAnimationListener(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public FrameLayoutAnimationListener(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        protected void onAnimationEnd() {
            super.onAnimationEnd();
            if (PopupNotificationActivity.this.onAnimationEndRunnable != null) {
                PopupNotificationActivity.this.onAnimationEndRunnable.run();
                PopupNotificationActivity.this.onAnimationEndRunnable = null;
            }
        }
    }

    private class FrameLayoutTouch extends FrameLayout {
        public FrameLayoutTouch(Context context) {
            super(context);
        }

        public FrameLayoutTouch(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public FrameLayoutTouch(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            return PopupNotificationActivity.this.checkTransitionAnimation() || ((PopupNotificationActivity) getContext()).onTouchEventMy(motionEvent);
        }

        public boolean onTouchEvent(MotionEvent motionEvent) {
            return PopupNotificationActivity.this.checkTransitionAnimation() || ((PopupNotificationActivity) getContext()).onTouchEventMy(motionEvent);
        }

        public void requestDisallowInterceptTouchEvent(boolean z) {
            ((PopupNotificationActivity) getContext()).onTouchEventMy(null);
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    private void applyViewsLayoutParams(int i) {
        int dp = AndroidUtilities.displaySize.x - AndroidUtilities.dp(24.0f);
        if (this.leftView != null) {
            LayoutParams layoutParams = (LayoutParams) this.leftView.getLayoutParams();
            layoutParams.gravity = 51;
            layoutParams.height = -1;
            layoutParams.width = dp;
            layoutParams.leftMargin = (-dp) + i;
            this.leftView.setLayoutParams(layoutParams);
        }
        if (this.centerView != null) {
            layoutParams = (LayoutParams) this.centerView.getLayoutParams();
            layoutParams.gravity = 51;
            layoutParams.height = -1;
            layoutParams.width = dp;
            layoutParams.leftMargin = i;
            this.centerView.setLayoutParams(layoutParams);
        }
        if (this.rightView != null) {
            layoutParams = (LayoutParams) this.rightView.getLayoutParams();
            layoutParams.gravity = 51;
            layoutParams.height = -1;
            layoutParams.width = dp;
            layoutParams.leftMargin = dp + i;
            this.rightView.setLayoutParams(layoutParams);
        }
        this.messageContainer.invalidate();
    }

    private void checkAndUpdateAvatar() {
        TLObject tLObject;
        Drawable drawable = null;
        Drawable avatarDrawable;
        if (this.currentChat != null) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentChat.id));
            if (chat != null) {
                this.currentChat = chat;
                if (this.currentChat.photo != null) {
                    drawable = this.currentChat.photo.photo_small;
                }
                avatarDrawable = new AvatarDrawable(this.currentChat);
                tLObject = drawable;
                drawable = avatarDrawable;
            } else {
                return;
            }
        } else if (this.currentUser != null) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.currentUser.id));
            if (user != null) {
                this.currentUser = user;
                if (this.currentUser.photo != null) {
                    drawable = this.currentUser.photo.photo_small;
                }
                avatarDrawable = new AvatarDrawable(this.currentUser);
                Object obj = drawable;
                drawable = avatarDrawable;
            } else {
                return;
            }
        } else {
            tLObject = null;
        }
        if (this.avatarImageView != null) {
            this.avatarImageView.setImage(tLObject, "50_50", drawable);
        }
    }

    private void fixLayout() {
        if (this.avatarContainer != null) {
            this.avatarContainer.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (PopupNotificationActivity.this.avatarContainer != null) {
                        PopupNotificationActivity.this.avatarContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    int currentActionBarHeight = (ActionBar.getCurrentActionBarHeight() - AndroidUtilities.dp(48.0f)) / 2;
                    PopupNotificationActivity.this.avatarContainer.setPadding(PopupNotificationActivity.this.avatarContainer.getPaddingLeft(), currentActionBarHeight, PopupNotificationActivity.this.avatarContainer.getPaddingRight(), currentActionBarHeight);
                    return true;
                }
            });
        }
        if (this.messageContainer != null) {
            this.messageContainer.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    PopupNotificationActivity.this.messageContainer.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (!(PopupNotificationActivity.this.checkTransitionAnimation() || PopupNotificationActivity.this.startedMoving)) {
                        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) PopupNotificationActivity.this.messageContainer.getLayoutParams();
                        marginLayoutParams.topMargin = ActionBar.getCurrentActionBarHeight();
                        marginLayoutParams.bottomMargin = AndroidUtilities.dp(48.0f);
                        marginLayoutParams.width = -1;
                        marginLayoutParams.height = -1;
                        PopupNotificationActivity.this.messageContainer.setLayoutParams(marginLayoutParams);
                        PopupNotificationActivity.this.applyViewsLayoutParams(0);
                    }
                    return true;
                }
            });
        }
    }

    private void getNewMessage() {
        if (this.popupMessages.isEmpty()) {
            onFinish();
            finish();
            return;
        }
        int i;
        if ((this.currentMessageNum != 0 || this.chatActivityEnterView.hasText() || this.startedMoving) && this.currentMessageObject != null) {
            for (int i2 = 0; i2 < this.popupMessages.size(); i2++) {
                if (((MessageObject) this.popupMessages.get(i2)).getId() == this.currentMessageObject.getId()) {
                    this.currentMessageNum = i2;
                    i = 1;
                    break;
                }
            }
        }
        i = 0;
        if (i == 0) {
            this.currentMessageNum = 0;
            this.currentMessageObject = (MessageObject) this.popupMessages.get(0);
            updateInterfaceForCurrentMessage(0);
        } else if (this.startedMoving) {
            if (this.currentMessageNum == this.popupMessages.size() - 1) {
                prepareLayouts(3);
            } else if (this.currentMessageNum == 1) {
                prepareLayouts(4);
            }
        }
        this.countText.setText(String.format("%d/%d", new Object[]{Integer.valueOf(this.currentMessageNum + 1), Integer.valueOf(this.popupMessages.size())}));
    }

    private ViewGroup getViewForMessage(int i, boolean z) {
        if (this.popupMessages.size() == 1 && (i < 0 || i >= this.popupMessages.size())) {
            return null;
        }
        View frameLayout;
        if (i == -1) {
            i = this.popupMessages.size() - 1;
        } else if (i == this.popupMessages.size()) {
            i = 0;
        }
        MessageObject messageObject = (MessageObject) this.popupMessages.get(i);
        View view;
        View backupImageView;
        TextView textView;
        if (messageObject.type == 1 || messageObject.type == 4) {
            View view2;
            if (this.imageViews.size() > 0) {
                view = (ViewGroup) this.imageViews.get(0);
                this.imageViews.remove(0);
                view2 = view;
            } else {
                view = new FrameLayoutAnimationListener(this);
                frameLayout = new FrameLayout(this);
                frameLayout.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
                frameLayout.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                view.addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f));
                backupImageView = new BackupImageView(this);
                backupImageView.setTag(Integer.valueOf(311));
                frameLayout.addView(backupImageView, LayoutHelper.createFrame(-1, -1.0f));
                backupImageView = new TextView(this);
                backupImageView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                backupImageView.setTextSize(1, 16.0f);
                backupImageView.setGravity(17);
                backupImageView.setTag(Integer.valueOf(312));
                frameLayout.addView(backupImageView, LayoutHelper.createFrame(-1, -2, 17));
                view.setTag(Integer.valueOf(2));
                view.setOnClickListener(new C50888());
                view2 = view;
            }
            textView = (TextView) view2.findViewWithTag(Integer.valueOf(312));
            BackupImageView backupImageView2 = (BackupImageView) view2.findViewWithTag(Integer.valueOf(311));
            backupImageView2.setAspectFit(true);
            if (messageObject.type == 1) {
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
                PhotoSize closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, 100);
                Object obj = null;
                if (closestPhotoSizeWithSize != null) {
                    Object obj2 = 1;
                    if (messageObject.type == 1 && !FileLoader.getPathToMessage(messageObject.messageOwner).exists()) {
                        obj2 = null;
                    }
                    if (obj2 != null || MediaController.getInstance().canDownloadMedia(messageObject)) {
                        backupImageView2.setImage(closestPhotoSizeWithSize.location, "100_100", closestPhotoSizeWithSize2.location, closestPhotoSizeWithSize.size);
                        obj = 1;
                    } else if (closestPhotoSizeWithSize2 != null) {
                        backupImageView2.setImage(closestPhotoSizeWithSize2.location, null, (Drawable) null);
                        obj = 1;
                    }
                }
                if (obj == null) {
                    backupImageView2.setVisibility(8);
                    textView.setVisibility(0);
                    textView.setTextSize(2, (float) MessagesController.getInstance().fontSize);
                    textView.setText(messageObject.messageText);
                } else {
                    backupImageView2.setVisibility(0);
                    textView.setVisibility(8);
                }
            } else if (messageObject.type == 4) {
                textView.setVisibility(8);
                textView.setText(messageObject.messageText);
                backupImageView2.setVisibility(0);
                double d = messageObject.messageOwner.media.geo.lat;
                double d2 = messageObject.messageOwner.media.geo._long;
                backupImageView2.setImage(String.format(Locale.US, "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=13&size=100x100&maptype=roadmap&scale=%d&markers=color:red|size:big|%f,%f&sensor=false", new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf(Math.min(2, (int) Math.ceil((double) AndroidUtilities.density))), Double.valueOf(d), Double.valueOf(d2)}), null, null);
            }
            frameLayout = view2;
        } else if (messageObject.type == 2) {
            PopupAudioView popupAudioView;
            if (this.audioViews.size() > 0) {
                view = (ViewGroup) this.audioViews.get(0);
                this.audioViews.remove(0);
                popupAudioView = (PopupAudioView) view.findViewWithTag(Integer.valueOf(300));
            } else {
                View frameLayoutAnimationListener = new FrameLayoutAnimationListener(this);
                View frameLayout2 = new FrameLayout(this);
                frameLayout2.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
                frameLayout2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                frameLayoutAnimationListener.addView(frameLayout2, LayoutHelper.createFrame(-1, -1.0f));
                View frameLayout3 = new FrameLayout(this);
                frameLayout2.addView(frameLayout3, LayoutHelper.createFrame(-1, -2.0f, 17, 20.0f, BitmapDescriptorFactory.HUE_RED, 20.0f, BitmapDescriptorFactory.HUE_RED));
                popupAudioView = new PopupAudioView(this);
                popupAudioView.setTag(Integer.valueOf(300));
                frameLayout3.addView(popupAudioView);
                frameLayoutAnimationListener.setTag(Integer.valueOf(3));
                frameLayoutAnimationListener.setOnClickListener(new C50899());
                view = frameLayoutAnimationListener;
            }
            popupAudioView.setMessageObject(messageObject);
            if (MediaController.getInstance().canDownloadMedia(messageObject)) {
                popupAudioView.downloadAudioIfNeed();
            }
            frameLayout = view;
        } else {
            if (this.textViews.size() > 0) {
                view = (ViewGroup) this.textViews.get(0);
                this.textViews.remove(0);
                frameLayout = view;
            } else {
                view = new FrameLayoutAnimationListener(this);
                frameLayout = new ScrollView(this);
                frameLayout.setFillViewport(true);
                view.addView(frameLayout, LayoutHelper.createFrame(-1, -1.0f));
                backupImageView = new LinearLayout(this);
                backupImageView.setOrientation(0);
                backupImageView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                frameLayout.addView(backupImageView, LayoutHelper.createScroll(-1, -2, 1));
                backupImageView.setPadding(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f), AndroidUtilities.dp(10.0f));
                backupImageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        PopupNotificationActivity.this.openCurrentMessage();
                    }
                });
                frameLayout = new TextView(this);
                frameLayout.setTextSize(1, 16.0f);
                frameLayout.setTag(Integer.valueOf(301));
                frameLayout.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                frameLayout.setLinkTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                frameLayout.setGravity(17);
                backupImageView.addView(frameLayout, LayoutHelper.createLinear(-1, -2, 17));
                view.setTag(Integer.valueOf(1));
                frameLayout = view;
            }
            textView = (TextView) frameLayout.findViewWithTag(Integer.valueOf(301));
            textView.setTextSize(2, (float) MessagesController.getInstance().fontSize);
            textView.setText(messageObject.messageText);
        }
        if (frameLayout.getParent() == null) {
            this.messageContainer.addView(frameLayout);
        }
        frameLayout.setVisibility(0);
        if (!z) {
            return frameLayout;
        }
        int dp = AndroidUtilities.displaySize.x - AndroidUtilities.dp(24.0f);
        LayoutParams layoutParams = (LayoutParams) frameLayout.getLayoutParams();
        layoutParams.gravity = 51;
        layoutParams.height = -1;
        layoutParams.width = dp;
        if (i == this.currentMessageNum) {
            layoutParams.leftMargin = 0;
        } else if (i == this.currentMessageNum - 1) {
            layoutParams.leftMargin = -dp;
        } else if (i == this.currentMessageNum + 1) {
            layoutParams.leftMargin = dp;
        }
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.invalidate();
        return frameLayout;
    }

    private void handleIntent(Intent intent) {
        boolean z = intent != null && intent.getBooleanExtra("force", false);
        this.isReply = z;
        if (this.isReply) {
            this.popupMessages = NotificationsController.getInstance().popupReplyMessages;
        } else {
            this.popupMessages = NotificationsController.getInstance().popupMessages;
        }
        if (((KeyguardManager) getSystemService("keyguard")).inKeyguardRestrictedInputMode() || !ApplicationLoader.isScreenOn) {
            getWindow().addFlags(2623490);
        } else {
            getWindow().addFlags(2623488);
            getWindow().clearFlags(2);
        }
        if (this.currentMessageObject == null) {
            this.currentMessageNum = 0;
        }
        getNewMessage();
    }

    private void openCurrentMessage() {
        if (this.currentMessageObject != null) {
            Intent intent = new Intent(ApplicationLoader.applicationContext, LaunchActivity.class);
            long dialogId = this.currentMessageObject.getDialogId();
            if (((int) dialogId) != 0) {
                int i = (int) dialogId;
                if (i < 0) {
                    intent.putExtra("chatId", -i);
                } else {
                    intent.putExtra("userId", i);
                }
            } else {
                intent.putExtra("encId", (int) (dialogId >> 32));
            }
            intent.setAction("com.tmessages.openchat" + Math.random() + Integer.MAX_VALUE);
            intent.setFlags(TLRPC.MESSAGE_FLAG_EDITED);
            startActivity(intent);
            onFinish();
            finish();
        }
    }

    private void prepareLayouts(int i) {
        if (i == 0) {
            reuseView(this.centerView);
            reuseView(this.leftView);
            reuseView(this.rightView);
            for (int i2 = this.currentMessageNum - 1; i2 < this.currentMessageNum + 2; i2++) {
                if (i2 == this.currentMessageNum - 1) {
                    this.leftView = getViewForMessage(i2, true);
                } else if (i2 == this.currentMessageNum) {
                    this.centerView = getViewForMessage(i2, true);
                } else if (i2 == this.currentMessageNum + 1) {
                    this.rightView = getViewForMessage(i2, true);
                }
            }
        } else if (i == 1) {
            reuseView(this.rightView);
            this.rightView = this.centerView;
            this.centerView = this.leftView;
            this.leftView = getViewForMessage(this.currentMessageNum - 1, true);
        } else if (i == 2) {
            reuseView(this.leftView);
            this.leftView = this.centerView;
            this.centerView = this.rightView;
            this.rightView = getViewForMessage(this.currentMessageNum + 1, true);
        } else if (i == 3) {
            if (this.rightView != null) {
                r1 = ((LayoutParams) this.rightView.getLayoutParams()).leftMargin;
                reuseView(this.rightView);
                r0 = getViewForMessage(this.currentMessageNum + 1, false);
                this.rightView = r0;
                if (r0 != null) {
                    r2 = AndroidUtilities.displaySize.x - AndroidUtilities.dp(24.0f);
                    r0 = (LayoutParams) this.rightView.getLayoutParams();
                    r0.gravity = 51;
                    r0.height = -1;
                    r0.width = r2;
                    r0.leftMargin = r1;
                    this.rightView.setLayoutParams(r0);
                    this.rightView.invalidate();
                }
            }
        } else if (i == 4 && this.leftView != null) {
            r1 = ((LayoutParams) this.leftView.getLayoutParams()).leftMargin;
            reuseView(this.leftView);
            r0 = getViewForMessage(0, false);
            this.leftView = r0;
            if (r0 != null) {
                r2 = AndroidUtilities.displaySize.x - AndroidUtilities.dp(24.0f);
                r0 = (LayoutParams) this.leftView.getLayoutParams();
                r0.gravity = 51;
                r0.height = -1;
                r0.width = r2;
                r0.leftMargin = r1;
                this.leftView.setLayoutParams(r0);
                this.leftView.invalidate();
            }
        }
    }

    private void reuseView(ViewGroup viewGroup) {
        if (viewGroup != null) {
            int intValue = ((Integer) viewGroup.getTag()).intValue();
            viewGroup.setVisibility(8);
            if (intValue == 1) {
                this.textViews.add(viewGroup);
            } else if (intValue == 2) {
                this.imageViews.add(viewGroup);
            } else if (intValue == 3) {
                this.audioViews.add(viewGroup);
            }
        }
    }

    private void setTypingAnimation(boolean z) {
        int i = 0;
        if (this.actionBar != null) {
            if (z) {
                try {
                    Integer num = (Integer) MessagesController.getInstance().printingStringsTypes.get(Long.valueOf(this.currentMessageObject.getDialogId()));
                    this.onlineTextView.setCompoundDrawablesWithIntrinsicBounds(this.statusDrawables[num.intValue()], null, null, null);
                    this.onlineTextView.setCompoundDrawablePadding(AndroidUtilities.dp(4.0f));
                    while (i < this.statusDrawables.length) {
                        if (i == num.intValue()) {
                            this.statusDrawables[i].start();
                        } else {
                            this.statusDrawables[i].stop();
                        }
                        i++;
                    }
                    return;
                } catch (Throwable e) {
                    FileLog.e(e);
                    return;
                }
            }
            this.onlineTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
            this.onlineTextView.setCompoundDrawablePadding(0);
            for (StatusDrawable stop : this.statusDrawables) {
                stop.stop();
            }
        }
    }

    private void switchToNextMessage() {
        if (this.popupMessages.size() > 1) {
            if (this.currentMessageNum < this.popupMessages.size() - 1) {
                this.currentMessageNum++;
            } else {
                this.currentMessageNum = 0;
            }
            this.currentMessageObject = (MessageObject) this.popupMessages.get(this.currentMessageNum);
            updateInterfaceForCurrentMessage(2);
            this.countText.setText(String.format("%d/%d", new Object[]{Integer.valueOf(this.currentMessageNum + 1), Integer.valueOf(this.popupMessages.size())}));
        }
    }

    private void switchToPreviousMessage() {
        if (this.popupMessages.size() > 1) {
            if (this.currentMessageNum > 0) {
                this.currentMessageNum--;
            } else {
                this.currentMessageNum = this.popupMessages.size() - 1;
            }
            this.currentMessageObject = (MessageObject) this.popupMessages.get(this.currentMessageNum);
            updateInterfaceForCurrentMessage(1);
            this.countText.setText(String.format("%d/%d", new Object[]{Integer.valueOf(this.currentMessageNum + 1), Integer.valueOf(this.popupMessages.size())}));
        }
    }

    private void updateInterfaceForCurrentMessage(int i) {
        if (this.actionBar != null) {
            this.currentChat = null;
            this.currentUser = null;
            long dialogId = this.currentMessageObject.getDialogId();
            this.chatActivityEnterView.setDialogId(dialogId);
            if (((int) dialogId) != 0) {
                int i2 = (int) dialogId;
                if (i2 > 0) {
                    this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(i2));
                } else {
                    this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-i2));
                    this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.from_id));
                }
            } else {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (dialogId >> 32))).user_id));
            }
            if (this.currentChat != null && this.currentUser != null) {
                this.nameTextView.setText(this.currentChat.title);
                this.onlineTextView.setText(UserObject.getUserName(this.currentUser));
                this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                this.nameTextView.setCompoundDrawablePadding(0);
            } else if (this.currentUser != null) {
                this.nameTextView.setText(UserObject.getUserName(this.currentUser));
                if (((int) dialogId) == 0) {
                    this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lock_white, 0, 0, 0);
                    this.nameTextView.setCompoundDrawablePadding(AndroidUtilities.dp(4.0f));
                } else {
                    this.nameTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    this.nameTextView.setCompoundDrawablePadding(0);
                }
            }
            prepareLayouts(i);
            updateSubtitle();
            checkAndUpdateAvatar();
            applyViewsLayoutParams(0);
        }
    }

    private void updateSubtitle() {
        if (this.actionBar != null && this.currentChat == null && this.currentUser != null) {
            if (this.currentUser.id / 1000 == 777 || this.currentUser.id / 1000 == 333 || ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.currentUser.id)) != null || (ContactsController.getInstance().contactsDict.size() == 0 && ContactsController.getInstance().isLoadingContacts())) {
                this.nameTextView.setText(UserObject.getUserName(this.currentUser));
            } else if (this.currentUser.phone == null || this.currentUser.phone.length() == 0) {
                this.nameTextView.setText(UserObject.getUserName(this.currentUser));
            } else {
                this.nameTextView.setText(C2488b.a().e("+" + this.currentUser.phone));
            }
            CharSequence charSequence = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.currentMessageObject.getDialogId()));
            if (charSequence == null || charSequence.length() == 0) {
                this.lastPrintString = null;
                setTypingAnimation(false);
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.currentUser.id));
                if (user != null) {
                    this.currentUser = user;
                }
                this.onlineTextView.setText(LocaleController.formatUserStatus(this.currentUser));
                return;
            }
            this.lastPrintString = charSequence;
            this.onlineTextView.setText(charSequence);
            setTypingAnimation(true);
        }
    }

    public boolean checkTransitionAnimation() {
        if (this.animationInProgress && this.animationStartTime < System.currentTimeMillis() - 400) {
            this.animationInProgress = false;
            if (this.onAnimationEndRunnable != null) {
                this.onAnimationEndRunnable.run();
                this.onAnimationEndRunnable = null;
            }
        }
        return this.animationInProgress;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        int i2 = 0;
        if (i == NotificationCenter.appDidLogout) {
            onFinish();
            finish();
        } else if (i == NotificationCenter.pushMessagesUpdated) {
            getNewMessage();
        } else if (i == NotificationCenter.updateInterfaces) {
            if (this.currentMessageObject != null) {
                int intValue = ((Integer) objArr[0]).intValue();
                if (!((intValue & 1) == 0 && (intValue & 4) == 0 && (intValue & 16) == 0 && (intValue & 32) == 0)) {
                    updateSubtitle();
                }
                if (!((intValue & 2) == 0 && (intValue & 8) == 0)) {
                    checkAndUpdateAvatar();
                }
                if ((intValue & 64) != 0) {
                    CharSequence charSequence = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.currentMessageObject.getDialogId()));
                    if ((this.lastPrintString != null && charSequence == null) || ((this.lastPrintString == null && charSequence != null) || (this.lastPrintString != null && charSequence != null && !this.lastPrintString.equals(charSequence)))) {
                        updateSubtitle();
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingDidReset) {
            r0 = (Integer) objArr[0];
            if (this.messageContainer != null) {
                r3 = this.messageContainer.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r4 = this.messageContainer.getChildAt(r2);
                    if (((Integer) r4.getTag()).intValue() == 3) {
                        r1 = (PopupAudioView) r4.findViewWithTag(Integer.valueOf(300));
                        if (r1.getMessageObject() != null && r1.getMessageObject().getId() == r0.intValue()) {
                            r1.updateButtonState();
                            return;
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.messagePlayingProgressDidChanged) {
            r0 = (Integer) objArr[0];
            if (this.messageContainer != null) {
                r3 = this.messageContainer.getChildCount();
                for (r2 = 0; r2 < r3; r2++) {
                    r4 = this.messageContainer.getChildAt(r2);
                    if (((Integer) r4.getTag()).intValue() == 3) {
                        r1 = (PopupAudioView) r4.findViewWithTag(Integer.valueOf(300));
                        if (r1.getMessageObject() != null && r1.getMessageObject().getId() == r0.intValue()) {
                            r1.updateProgress();
                            return;
                        }
                    }
                }
            }
        } else if (i == NotificationCenter.emojiDidLoaded) {
            if (this.messageContainer != null) {
                r2 = this.messageContainer.getChildCount();
                while (i2 < r2) {
                    View childAt = this.messageContainer.getChildAt(i2);
                    if (((Integer) childAt.getTag()).intValue() == 1) {
                        TextView textView = (TextView) childAt.findViewWithTag(Integer.valueOf(301));
                        if (textView != null) {
                            textView.invalidate();
                        }
                    }
                    i2++;
                }
            }
        } else if (i == NotificationCenter.contactsDidLoaded) {
            updateSubtitle();
        }
    }

    public void onBackPressed() {
        if (this.chatActivityEnterView.isPopupShowing()) {
            this.chatActivityEnterView.hidePopup(true);
        } else {
            super.onBackPressed();
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        AndroidUtilities.checkDisplaySize(this, configuration);
        fixLayout();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Theme.createChatResources(this, false);
        int identifier = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            AndroidUtilities.statusBarHeight = getResources().getDimensionPixelSize(identifier);
        }
        this.classGuid = ConnectionsManager.getInstance().generateClassGuid();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.appDidLogout);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.pushMessagesUpdated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.messagePlayingDidReset);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        this.statusDrawables[0] = new TypingDotsDrawable();
        this.statusDrawables[1] = new RecordStatusDrawable();
        this.statusDrawables[2] = new SendingFileDrawable();
        this.statusDrawables[3] = new PlayingGameDrawable();
        this.statusDrawables[4] = new RoundStatusDrawable();
        View c50811 = new SizeNotifierFrameLayout(this) {
            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                int childCount = getChildCount();
                int emojiPadding = getKeyboardHeight() <= AndroidUtilities.dp(20.0f) ? PopupNotificationActivity.this.chatActivityEnterView.getEmojiPadding() : 0;
                for (int i5 = 0; i5 < childCount; i5++) {
                    View childAt = getChildAt(i5);
                    if (childAt.getVisibility() != 8) {
                        int measuredHeight;
                        LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                        int measuredWidth = childAt.getMeasuredWidth();
                        int measuredHeight2 = childAt.getMeasuredHeight();
                        int i6 = layoutParams.gravity;
                        if (i6 == -1) {
                            i6 = 51;
                        }
                        int i7 = i6 & 7;
                        i6 &= 112;
                        switch (i7 & 7) {
                            case 1:
                                i7 = ((((i3 - i) - measuredWidth) / 2) + layoutParams.leftMargin) - layoutParams.rightMargin;
                                break;
                            case 5:
                                i7 = (i3 - measuredWidth) - layoutParams.rightMargin;
                                break;
                            default:
                                i7 = layoutParams.leftMargin;
                                break;
                        }
                        switch (i6) {
                            case 16:
                                i6 = (((((i4 - emojiPadding) - i2) - measuredHeight2) / 2) + layoutParams.topMargin) - layoutParams.bottomMargin;
                                break;
                            case 48:
                                i6 = layoutParams.topMargin;
                                break;
                            case 80:
                                i6 = (((i4 - emojiPadding) - i2) - measuredHeight2) - layoutParams.bottomMargin;
                                break;
                            default:
                                i6 = layoutParams.topMargin;
                                break;
                        }
                        if (PopupNotificationActivity.this.chatActivityEnterView.isPopupView(childAt)) {
                            measuredHeight = emojiPadding != 0 ? getMeasuredHeight() - emojiPadding : getMeasuredHeight();
                            i6 = i7;
                        } else if (PopupNotificationActivity.this.chatActivityEnterView.isRecordCircle(childAt)) {
                            i6 = ((PopupNotificationActivity.this.popupContainer.getLeft() + PopupNotificationActivity.this.popupContainer.getMeasuredWidth()) - childAt.getMeasuredWidth()) - layoutParams.rightMargin;
                            measuredHeight = ((PopupNotificationActivity.this.popupContainer.getTop() + PopupNotificationActivity.this.popupContainer.getMeasuredHeight()) - childAt.getMeasuredHeight()) - layoutParams.bottomMargin;
                        } else {
                            measuredHeight = i6;
                            i6 = i7;
                        }
                        childAt.layout(i6, measuredHeight, i6 + measuredWidth, measuredHeight + measuredHeight2);
                    }
                }
                notifyHeightChanged();
            }

            protected void onMeasure(int i, int i2) {
                MeasureSpec.getMode(i);
                MeasureSpec.getMode(i2);
                int size = MeasureSpec.getSize(i);
                int size2 = MeasureSpec.getSize(i2);
                setMeasuredDimension(size, size2);
                int emojiPadding = getKeyboardHeight() <= AndroidUtilities.dp(20.0f) ? size2 - PopupNotificationActivity.this.chatActivityEnterView.getEmojiPadding() : size2;
                int childCount = getChildCount();
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt = getChildAt(i3);
                    if (childAt.getVisibility() != 8) {
                        if (PopupNotificationActivity.this.chatActivityEnterView.isPopupView(childAt)) {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(childAt.getLayoutParams().height, 1073741824));
                        } else if (PopupNotificationActivity.this.chatActivityEnterView.isRecordCircle(childAt)) {
                            measureChildWithMargins(childAt, i, 0, i2, 0);
                        } else {
                            childAt.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(Math.max(AndroidUtilities.dp(10.0f), AndroidUtilities.dp(2.0f) + emojiPadding), 1073741824));
                        }
                    }
                }
            }
        };
        setContentView(c50811);
        c50811.setBackgroundColor(-1728053248);
        View relativeLayout = new RelativeLayout(this);
        c50811.addView(relativeLayout, LayoutHelper.createFrame(-1, -1.0f));
        this.popupContainer = new RelativeLayout(this);
        this.popupContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        relativeLayout.addView(this.popupContainer, LayoutHelper.createRelative(-1, PsExtractor.VIDEO_STREAM_MASK, 12, 0, 12, 0, 13));
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.onDestroy();
        }
        this.chatActivityEnterView = new ChatActivityEnterView(this, c50811, null, false, 0);
        this.popupContainer.addView(this.chatActivityEnterView, LayoutHelper.createRelative(-1, -2, 12));
        this.chatActivityEnterView.setDelegate(new C50822());
        this.messageContainer = new FrameLayoutTouch(this);
        this.popupContainer.addView(this.messageContainer, 0);
        this.actionBar = new ActionBar(this);
        this.actionBar.setOccupyStatusBar(false);
        this.actionBar.setBackButtonImage(R.drawable.ic_close_white);
        this.actionBar.setBackgroundColor(Theme.getColor(Theme.key_actionBarDefault));
        this.actionBar.setItemsBackgroundColor(Theme.getColor(Theme.key_actionBarDefaultSelector), false);
        this.popupContainer.addView(this.actionBar);
        ViewGroup.LayoutParams layoutParams = this.actionBar.getLayoutParams();
        layoutParams.width = -1;
        this.actionBar.setLayoutParams(layoutParams);
        ActionBarMenuItem addItemWithWidth = this.actionBar.createMenu().addItemWithWidth(2, 0, AndroidUtilities.dp(56.0f));
        this.countText = new TextView(this);
        this.countText.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        this.countText.setTextSize(1, 14.0f);
        this.countText.setGravity(17);
        addItemWithWidth.addView(this.countText, LayoutHelper.createFrame(56, -1.0f));
        this.avatarContainer = new FrameLayout(this);
        this.avatarContainer.setPadding(AndroidUtilities.dp(4.0f), 0, AndroidUtilities.dp(4.0f), 0);
        this.actionBar.addView(this.avatarContainer);
        LayoutParams layoutParams2 = (LayoutParams) this.avatarContainer.getLayoutParams();
        layoutParams2.height = -1;
        layoutParams2.width = -2;
        layoutParams2.rightMargin = AndroidUtilities.dp(48.0f);
        layoutParams2.leftMargin = AndroidUtilities.dp(60.0f);
        layoutParams2.gravity = 51;
        this.avatarContainer.setLayoutParams(layoutParams2);
        this.avatarImageView = new BackupImageView(this);
        this.avatarImageView.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarContainer.addView(this.avatarImageView);
        layoutParams2 = (LayoutParams) this.avatarImageView.getLayoutParams();
        layoutParams2.width = AndroidUtilities.dp(42.0f);
        layoutParams2.height = AndroidUtilities.dp(42.0f);
        layoutParams2.topMargin = AndroidUtilities.dp(3.0f);
        this.avatarImageView.setLayoutParams(layoutParams2);
        this.nameTextView = new TextView(this);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
        this.nameTextView.setTextSize(1, 18.0f);
        this.nameTextView.setLines(1);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setGravity(3);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.avatarContainer.addView(this.nameTextView);
        layoutParams2 = (LayoutParams) this.nameTextView.getLayoutParams();
        layoutParams2.width = -2;
        layoutParams2.height = -2;
        layoutParams2.leftMargin = AndroidUtilities.dp(54.0f);
        layoutParams2.bottomMargin = AndroidUtilities.dp(22.0f);
        layoutParams2.gravity = 80;
        this.nameTextView.setLayoutParams(layoutParams2);
        this.onlineTextView = new TextView(this);
        this.onlineTextView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubtitle));
        this.onlineTextView.setTextSize(1, 14.0f);
        this.onlineTextView.setLines(1);
        this.onlineTextView.setMaxLines(1);
        this.onlineTextView.setSingleLine(true);
        this.onlineTextView.setEllipsize(TruncateAt.END);
        this.onlineTextView.setGravity(3);
        this.avatarContainer.addView(this.onlineTextView);
        layoutParams2 = (LayoutParams) this.onlineTextView.getLayoutParams();
        layoutParams2.width = -2;
        layoutParams2.height = -2;
        layoutParams2.leftMargin = AndroidUtilities.dp(54.0f);
        layoutParams2.bottomMargin = AndroidUtilities.dp(4.0f);
        layoutParams2.gravity = 80;
        this.onlineTextView.setLayoutParams(layoutParams2);
        this.actionBar.setActionBarMenuOnItemClick(new C50833());
        this.wakeLock = ((PowerManager) ApplicationLoader.applicationContext.getSystemService("power")).newWakeLock(268435462, "screen");
        this.wakeLock.setReferenceCounted(false);
        handleIntent(getIntent());
    }

    protected void onDestroy() {
        super.onDestroy();
        onFinish();
        if (this.wakeLock.isHeld()) {
            this.wakeLock.release();
        }
        if (this.avatarImageView != null) {
            this.avatarImageView.setImageDrawable(null);
        }
    }

    protected void onFinish() {
        if (!this.finished) {
            this.finished = true;
            if (this.isReply) {
                this.popupMessages.clear();
            }
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.appDidLogout);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.pushMessagesUpdated);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingProgressDidChanged);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.messagePlayingDidReset);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
            if (this.chatActivityEnterView != null) {
                this.chatActivityEnterView.onDestroy();
            }
            if (this.wakeLock.isHeld()) {
                this.wakeLock.release();
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.hidePopup(false);
            this.chatActivityEnterView.setFieldFocused(false);
        }
        ConnectionsManager.getInstance().setAppPaused(true, false);
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 3 && iArr[0] != 0) {
            Builder builder = new Builder(this);
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.getString("PermissionNoAudio", R.string.PermissionNoAudio));
            builder.setNegativeButton(LocaleController.getString("PermissionOpenSettings", R.string.PermissionOpenSettings), new C50844());
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            builder.show();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.chatActivityEnterView != null) {
            this.chatActivityEnterView.setFieldFocused(true);
        }
        ConnectionsManager.getInstance().setAppPaused(false, false);
        fixLayout();
        checkAndUpdateAvatar();
        this.wakeLock.acquire(7000);
    }

    public boolean onTouchEventMy(MotionEvent motionEvent) {
        if (checkTransitionAnimation()) {
            return false;
        }
        if (motionEvent != null && motionEvent.getAction() == 0) {
            this.moveStartX = motionEvent.getX();
        } else if (motionEvent != null && motionEvent.getAction() == 2) {
            float x = motionEvent.getX();
            int i = (int) (x - this.moveStartX);
            if (!(this.moveStartX == -1.0f || this.startedMoving || Math.abs(i) <= AndroidUtilities.dp(10.0f))) {
                this.startedMoving = true;
                this.moveStartX = x;
                AndroidUtilities.lockOrientation(this);
                if (this.velocityTracker == null) {
                    this.velocityTracker = VelocityTracker.obtain();
                    i = 0;
                } else {
                    this.velocityTracker.clear();
                    i = 0;
                }
            }
            if (this.startedMoving) {
                if (this.leftView == null && r0 > 0) {
                    i = 0;
                }
                if (this.rightView == null && r0 < 0) {
                    i = 0;
                }
                if (this.velocityTracker != null) {
                    this.velocityTracker.addMovement(motionEvent);
                }
                applyViewsLayoutParams(i);
            }
        } else if (motionEvent == null || motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            if (motionEvent == null || !this.startedMoving) {
                applyViewsLayoutParams(0);
            } else {
                boolean z;
                int i2;
                View view;
                int abs;
                Animation translateAnimation;
                LayoutParams layoutParams = (LayoutParams) this.centerView.getLayoutParams();
                int x2 = (int) (motionEvent.getX() - this.moveStartX);
                int dp = AndroidUtilities.displaySize.x - AndroidUtilities.dp(24.0f);
                if (this.velocityTracker != null) {
                    this.velocityTracker.computeCurrentVelocity(1000);
                    if (this.velocityTracker.getXVelocity() >= 3500.0f) {
                        z = true;
                    } else if (this.velocityTracker.getXVelocity() <= -3500.0f) {
                        z = true;
                    }
                    if ((!z || x2 > dp / 3) && this.leftView != null) {
                        i2 = dp - layoutParams.leftMargin;
                        view = this.leftView;
                        this.onAnimationEndRunnable = new C50855();
                    } else if ((z || x2 < (-dp) / 3) && this.rightView != null) {
                        i2 = (-dp) - layoutParams.leftMargin;
                        view = this.rightView;
                        this.onAnimationEndRunnable = new C50866();
                    } else if (layoutParams.leftMargin != 0) {
                        i2 = -layoutParams.leftMargin;
                        view = x2 > 0 ? this.leftView : this.rightView;
                        this.onAnimationEndRunnable = new C50877();
                    } else {
                        view = null;
                        i2 = 0;
                    }
                    if (i2 != 0) {
                        abs = (int) (Math.abs(((float) i2) / ((float) dp)) * 200.0f);
                        translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, (float) i2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                        translateAnimation.setDuration((long) abs);
                        this.centerView.startAnimation(translateAnimation);
                        if (view != null) {
                            translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, (float) i2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                            translateAnimation.setDuration((long) abs);
                            view.startAnimation(translateAnimation);
                        }
                        this.animationInProgress = true;
                        this.animationStartTime = System.currentTimeMillis();
                    }
                }
                z = false;
                if (!z) {
                }
                i2 = dp - layoutParams.leftMargin;
                view = this.leftView;
                this.onAnimationEndRunnable = new C50855();
                if (i2 != 0) {
                    abs = (int) (Math.abs(((float) i2) / ((float) dp)) * 200.0f);
                    translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, (float) i2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                    translateAnimation.setDuration((long) abs);
                    this.centerView.startAnimation(translateAnimation);
                    if (view != null) {
                        translateAnimation = new TranslateAnimation(BitmapDescriptorFactory.HUE_RED, (float) i2, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
                        translateAnimation.setDuration((long) abs);
                        view.startAnimation(translateAnimation);
                    }
                    this.animationInProgress = true;
                    this.animationStartTime = System.currentTimeMillis();
                }
            }
            if (this.velocityTracker != null) {
                this.velocityTracker.recycle();
                this.velocityTracker = null;
            }
            this.startedMoving = false;
            this.moveStartX = -1.0f;
        }
        return this.startedMoving;
    }
}
