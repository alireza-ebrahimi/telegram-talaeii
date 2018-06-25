package org.telegram.ui.Cells;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewStructure;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.dynamicadapter.data.DocAvailableInfo;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.ImageReceiver.ImageReceiverDelegate;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessageObject.GroupedMessagePosition;
import org.telegram.messenger.MessageObject.GroupedMessages;
import org.telegram.messenger.MessageObject.TextLayoutBlock;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonCallback;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonGame;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRequestGeoLocation;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonSwitchInline;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonUrl;
import org.telegram.tgnet.TLRPC$TL_messageMediaContact;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.MessageFwdHeader;
import org.telegram.tgnet.TLRPC.PhoneCallDiscardReason;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.LinkPath;
import org.telegram.ui.Components.RadialProgress;
import org.telegram.ui.Components.RoundVideoPlayingDrawable;
import org.telegram.ui.Components.SeekBar;
import org.telegram.ui.Components.SeekBar.SeekBarDelegate;
import org.telegram.ui.Components.SeekBarWaveform;
import org.telegram.ui.Components.StaticLayoutEx;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanBotCommand;
import org.telegram.ui.Components.URLSpanMono;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.PhotoViewer;
import org.telegram.ui.SecretMediaViewer;
import utils.C3792d;
import utils.C5319b;
import utils.p178a.C3791b;
import utils.view.FarsiTextView;
import utils.view.TitleTextView;
import utils.view.ToastUtil;

public class ChatMessageCell extends BaseCell implements ImageReceiverDelegate, FileDownloadProgressListener, SeekBarDelegate {
    private static final int DOCUMENT_ATTACH_TYPE_AUDIO = 3;
    private static final int DOCUMENT_ATTACH_TYPE_DOCUMENT = 1;
    private static final int DOCUMENT_ATTACH_TYPE_GIF = 2;
    private static final int DOCUMENT_ATTACH_TYPE_MUSIC = 5;
    private static final int DOCUMENT_ATTACH_TYPE_NONE = 0;
    private static final int DOCUMENT_ATTACH_TYPE_ROUND = 7;
    private static final int DOCUMENT_ATTACH_TYPE_STICKER = 6;
    private static final int DOCUMENT_ATTACH_TYPE_VIDEO = 4;
    static final int FAVORITE_FALSE = 0;
    static final int FAVORITE_TRUE = 1;
    static final int FAVORITE_UNKNOWN = -1;
    static Bitmap nonFreeBitmap = null;
    private int TAG;
    private StaticLayout adminLayout;
    private boolean allowAssistant;
    private StaticLayout authorLayout;
    private int authorX;
    private int availableTimeWidth;
    private AvatarDrawable avatarDrawable;
    private ImageReceiver avatarImage = new ImageReceiver();
    private boolean avatarPressed;
    private int backgroundDrawableLeft;
    private int backgroundDrawableRight;
    private int backgroundWidth = 100;
    private ArrayList<BotButton> botButtons = new ArrayList();
    private HashMap<String, BotButton> botButtonsByData = new HashMap();
    private HashMap<String, BotButton> botButtonsByPosition = new HashMap();
    private String botButtonsLayout;
    private int buttonPressed;
    private int buttonState;
    private int buttonX;
    private int buttonY;
    DocAvailableInfo cacheChecker = new DocAvailableInfo();
    private boolean cancelLoading;
    private int captionHeight;
    private StaticLayout captionLayout;
    private int captionX;
    private int captionY;
    private AvatarDrawable contactAvatarDrawable;
    private float controlsAlpha = 1.0f;
    private Drawable currentBackgroundDrawable;
    private Chat currentChat;
    private Chat currentForwardChannel;
    private String currentForwardNameString;
    private User currentForwardUser;
    private MessageObject currentMessageObject;
    private GroupedMessages currentMessagesGroup;
    private String currentNameString;
    private FileLocation currentPhoto;
    private String currentPhotoFilter;
    private String currentPhotoFilterThumb;
    private PhotoSize currentPhotoObject;
    private PhotoSize currentPhotoObjectThumb;
    private GroupedMessagePosition currentPosition;
    private FileLocation currentReplyPhoto;
    private String currentTimeString;
    private String currentUrl;
    private User currentUser;
    private User currentViaBotUser;
    private String currentViewsString;
    private ChatMessageCellDelegate delegate;
    private RectF deleteProgressRect = new RectF();
    private StaticLayout descriptionLayout;
    private int descriptionX;
    private int descriptionY;
    private boolean disallowLongPress;
    private StaticLayout docTitleLayout;
    private int docTitleOffsetX;
    private Document documentAttach;
    private int documentAttachType;
    private boolean drawBackground = true;
    protected boolean drawFavoriteButton;
    private boolean drawForwardedName;
    private boolean drawImageButton;
    private boolean drawInstantView;
    private int drawInstantViewType;
    private boolean drawJoinChannelView;
    private boolean drawJoinGroupView;
    private boolean drawName;
    private boolean drawNameLayout;
    private boolean drawPhotoImage;
    private boolean drawPinnedBottom;
    private boolean drawPinnedTop;
    private boolean drawRadialCheckBackground;
    private boolean drawShareButton;
    private boolean drawTime = true;
    private boolean drwaShareGoIcon;
    private StaticLayout durationLayout;
    private int durationWidth;
    private boolean favoritePressed;
    private int favoriteStartX;
    private int favoriteStartY;
    private volatile int favoriteStatus = -1;
    private int firstVisibleBlockNum;
    private boolean forceNotDrawTime;
    private boolean forwardBotPressed;
    private boolean forwardName;
    private float[] forwardNameOffsetX = new float[2];
    private boolean forwardNamePressed;
    private int forwardNameX;
    private int forwardNameY;
    private StaticLayout[] forwardedNameLayout = new StaticLayout[2];
    private int forwardedNameWidth;
    private boolean fullyDraw;
    private boolean gamePreviewPressed;
    private boolean groupPhotoInvisible;
    private boolean hasGamePreview;
    private boolean hasInvoicePreview;
    private boolean hasLinkPreview;
    private boolean hasOldCaptionPreview;
    private int highlightProgress;
    private boolean imagePressed;
    private boolean inLayout;
    private StaticLayout infoLayout;
    private int infoWidth;
    private boolean instantButtonPressed;
    private boolean instantPressed;
    private int instantTextX;
    private StaticLayout instantViewLayout;
    private Drawable instantViewSelectorDrawable;
    private int instantWidth;
    private Runnable invalidateRunnable = new C39991();
    private boolean isAvatarVisible;
    public boolean isChat;
    private boolean isCheckPressed = true;
    private boolean isHighlighted;
    private boolean isHighlightedAnimated;
    boolean isPayment = false;
    private boolean isPressed;
    private boolean isSmallImage;
    private int keyboardHeight;
    private long lastControlsAlphaChangeTime;
    private int lastDeleteDate;
    private int lastHeight;
    private long lastHighlightProgressTime;
    private int lastSendState;
    private int lastTime;
    private int lastViewsCount;
    private int lastVisibleBlockNum;
    private int layoutHeight;
    private int layoutWidth;
    private int linkBlockNum;
    private int linkPreviewHeight;
    private boolean linkPreviewPressed;
    private int linkSelectionBlockNum;
    private boolean locationExpired;
    private ImageReceiver locationImageReceiver;
    private boolean mediaBackground;
    private int mediaOffsetY;
    private boolean mediaWasInvisible;
    private StaticLayout nameLayout;
    private float nameOffsetX;
    private int nameWidth;
    private float nameX;
    private float nameY;
    private int namesOffset;
    private boolean needNewVisiblePart;
    private boolean needReplyImage;
    private boolean otherPressed;
    private int otherX;
    private int otherY;
    private StaticLayout performerLayout;
    private int performerX;
    private ImageReceiver photoImage;
    private boolean photoNotSet;
    private StaticLayout photosCountLayout;
    private int photosCountWidth;
    private boolean pinnedBottom;
    private boolean pinnedTop;
    private int pressedBotButton;
    private CharacterStyle pressedLink;
    private int pressedLinkType;
    private int[] pressedState = new int[]{16842910, 16842919};
    private RadialProgress radialProgress;
    private RectF rect = new RectF();
    private ImageReceiver replyImageReceiver;
    private StaticLayout replyNameLayout;
    private float replyNameOffset;
    private int replyNameWidth;
    private boolean replyPressed;
    private int replyStartX;
    private int replyStartY;
    private StaticLayout replyTextLayout;
    private float replyTextOffset;
    private int replyTextWidth;
    private RoundVideoPlayingDrawable roundVideoPlayingDrawable;
    private boolean scheduledInvalidate;
    private Rect scrollRect = new Rect();
    private SeekBar seekBar;
    private SeekBarWaveform seekBarWaveform;
    private int seekBarX;
    private int seekBarY;
    private boolean sharePressed;
    private int shareStartX;
    private int shareStartY;
    private boolean showEditedMark;
    private StaticLayout siteNameLayout;
    Rect slsBounds = new Rect();
    FontMetrics slsFm = new FontMetrics();
    Paint slsPaint = new Paint();
    private boolean slsPressed;
    RectF slsRectF = new RectF();
    Rect slsTagBounds = new Rect();
    FontMetrics slsTagFm = new FontMetrics();
    Paint slsTagPaint = new Paint();
    private boolean slsTagPressed;
    RectF slsTagRectF = new RectF();
    private StaticLayout songLayout;
    private int songX;
    private int substractBackgroundHeight;
    private int tagStartX;
    private int tagStartY;
    private int textX;
    private int textY;
    private float timeAlpha = 1.0f;
    private int timeAudioX;
    private StaticLayout timeLayout;
    private int timeTextWidth;
    private boolean timeWasInvisible;
    private int timeWidth;
    private int timeWidthAudio;
    private int timeX;
    private StaticLayout titleLayout;
    private int titleX;
    private long totalChangeTime;
    private int totalHeight;
    private int totalVisibleBlocksCount;
    private ArrayList<LinkPath> urlPath = new ArrayList();
    private ArrayList<LinkPath> urlPathCache = new ArrayList();
    private ArrayList<LinkPath> urlPathSelection = new ArrayList();
    private boolean useSeekBarWaweform;
    private int viaNameWidth;
    private int viaWidth;
    private StaticLayout videoInfoLayout;
    private StaticLayout viewsLayout;
    private int viewsTextWidth;
    private boolean wasLayout;
    private int widthBeforeNewTimeLine;
    private int widthForButtons;

    /* renamed from: org.telegram.ui.Cells.ChatMessageCell$1 */
    class C39991 implements Runnable {
        C39991() {
        }

        public void run() {
            ChatMessageCell.this.checkLocationExpired();
            if (ChatMessageCell.this.locationExpired) {
                ChatMessageCell.this.invalidate();
                ChatMessageCell.this.scheduledInvalidate = false;
                return;
            }
            ChatMessageCell.this.invalidate(((int) ChatMessageCell.this.rect.left) - 5, ((int) ChatMessageCell.this.rect.top) - 5, ((int) ChatMessageCell.this.rect.right) + 5, ((int) ChatMessageCell.this.rect.bottom) + 5);
            if (ChatMessageCell.this.scheduledInvalidate) {
                AndroidUtilities.runOnUIThread(ChatMessageCell.this.invalidateRunnable, 1000);
            }
        }
    }

    /* renamed from: org.telegram.ui.Cells.ChatMessageCell$2 */
    class C40002 implements OnClickListener {
        C40002() {
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    private class BotButton {
        private int angle;
        private KeyboardButton button;
        private int height;
        private long lastUpdateTime;
        private float progressAlpha;
        private StaticLayout title;
        private int width;
        /* renamed from: x */
        private int f10171x;
        /* renamed from: y */
        private int f10172y;

        private BotButton() {
        }
    }

    public interface ChatMessageCellDelegate {
        boolean canPerformActions();

        void didLongPressed(ChatMessageCell chatMessageCell);

        void didPressedBotButton(ChatMessageCell chatMessageCell, KeyboardButton keyboardButton);

        void didPressedCancelSendButton(ChatMessageCell chatMessageCell);

        void didPressedChannelAvatar(ChatMessageCell chatMessageCell, Chat chat, int i);

        void didPressedImage(ChatMessageCell chatMessageCell);

        void didPressedInstantButton(ChatMessageCell chatMessageCell, int i);

        void didPressedOther(ChatMessageCell chatMessageCell);

        void didPressedReplyMessage(ChatMessageCell chatMessageCell, int i);

        void didPressedShare(ChatMessageCell chatMessageCell);

        void didPressedUrl(MessageObject messageObject, CharacterStyle characterStyle, boolean z);

        void didPressedUserAvatar(ChatMessageCell chatMessageCell, User user);

        void didPressedViaBot(ChatMessageCell chatMessageCell, String str);

        void didTagPressed(String str);

        boolean isChatAdminCell(int i);

        void needOpenWebView(String str, String str2, String str3, String str4, int i, int i2);

        boolean needPlayMessage(MessageObject messageObject);
    }

    public ChatMessageCell(Context context) {
        super(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarDrawable = new AvatarDrawable();
        this.replyImageReceiver = new ImageReceiver(this);
        this.locationImageReceiver = new ImageReceiver(this);
        this.locationImageReceiver.setRoundRadius(AndroidUtilities.dp(26.1f));
        this.TAG = MediaController.getInstance().generateObserverTag();
        this.contactAvatarDrawable = new AvatarDrawable();
        this.photoImage = new ImageReceiver(this);
        this.photoImage.setDelegate(this);
        this.radialProgress = new RadialProgress(this);
        this.seekBar = new SeekBar(context);
        this.seekBar.setDelegate(this);
        this.seekBarWaveform = new SeekBarWaveform(context);
        this.seekBarWaveform.setDelegate(this);
        this.seekBarWaveform.setParentView(this);
        this.roundVideoPlayingDrawable = new RoundVideoPlayingDrawable(this);
    }

    private void calcBackgroundWidth(int i, int i2, int i3) {
        if (this.hasLinkPreview || this.hasOldCaptionPreview || this.hasGamePreview || this.hasInvoicePreview || i - this.currentMessageObject.lastLineWidth < i2 || this.currentMessageObject.hasRtl) {
            this.totalHeight += AndroidUtilities.dp(14.0f);
            this.backgroundWidth = Math.max(i3, this.currentMessageObject.lastLineWidth) + AndroidUtilities.dp(31.0f);
            this.backgroundWidth = Math.max(this.backgroundWidth, this.timeWidth + AndroidUtilities.dp(31.0f));
            return;
        }
        int i4 = i3 - this.currentMessageObject.lastLineWidth;
        if (i4 < 0 || i4 > i2) {
            this.backgroundWidth = Math.max(i3, this.currentMessageObject.lastLineWidth + i2) + AndroidUtilities.dp(31.0f);
        } else {
            this.backgroundWidth = ((i3 + i2) - i4) + AndroidUtilities.dp(31.0f);
        }
    }

    private boolean checkAudioMotionEvent(MotionEvent motionEvent) {
        boolean z = true;
        if (this.documentAttachType != 3 && this.documentAttachType != 5) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        boolean onTouch = this.useSeekBarWaweform ? this.seekBarWaveform.onTouch(motionEvent.getAction(), (motionEvent.getX() - ((float) this.seekBarX)) - ((float) AndroidUtilities.dp(13.0f)), motionEvent.getY() - ((float) this.seekBarY)) : this.seekBar.onTouch(motionEvent.getAction(), motionEvent.getX() - ((float) this.seekBarX), motionEvent.getY() - ((float) this.seekBarY));
        if (onTouch) {
            if (!this.useSeekBarWaweform && motionEvent.getAction() == 0) {
                getParent().requestDisallowInterceptTouchEvent(true);
            } else if (this.useSeekBarWaweform && !this.seekBarWaveform.isStartDraging() && motionEvent.getAction() == 1) {
                didPressedButton(true);
            }
            this.disallowLongPress = true;
            invalidate();
            z = onTouch;
        } else {
            int dp = AndroidUtilities.dp(36.0f);
            boolean z2 = (this.buttonState == 0 || this.buttonState == 1 || this.buttonState == 2) ? x >= this.buttonX - AndroidUtilities.dp(12.0f) && x <= (this.buttonX - AndroidUtilities.dp(12.0f)) + this.backgroundWidth && y >= this.namesOffset + this.mediaOffsetY && y <= this.layoutHeight : x >= this.buttonX && x <= this.buttonX + dp && y >= this.buttonY && y <= this.buttonY + dp;
            if (motionEvent.getAction() == 0) {
                if (z2) {
                    this.buttonPressed = 1;
                    invalidate();
                    updateRadialProgressBackground(true);
                }
            } else if (this.buttonPressed != 0) {
                if (motionEvent.getAction() == 1) {
                    this.buttonPressed = 0;
                    playSoundEffect(0);
                    didPressedButton(true);
                    invalidate();
                } else if (motionEvent.getAction() == 3) {
                    this.buttonPressed = 0;
                    invalidate();
                } else if (motionEvent.getAction() == 2 && !z2) {
                    this.buttonPressed = 0;
                    invalidate();
                }
                updateRadialProgressBackground(true);
            }
            z = onTouch;
        }
        return z;
    }

    private boolean checkBotButtonMotionEvent(MotionEvent motionEvent) {
        if (this.botButtons.isEmpty() || this.currentMessageObject.eventId != 0) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            int measuredWidth;
            if (this.currentMessageObject.isOutOwner()) {
                measuredWidth = (getMeasuredWidth() - this.widthForButtons) - AndroidUtilities.dp(10.0f);
            } else {
                measuredWidth = AndroidUtilities.dp(this.mediaBackground ? 1.0f : 7.0f) + this.backgroundDrawableLeft;
            }
            int i = 0;
            while (i < this.botButtons.size()) {
                BotButton botButton = (BotButton) this.botButtons.get(i);
                int access$600 = (botButton.f10172y + this.layoutHeight) - AndroidUtilities.dp(2.0f);
                if (x < botButton.f10171x + measuredWidth || x > (botButton.f10171x + measuredWidth) + botButton.width || y < access$600 || y > botButton.height + access$600) {
                    i++;
                } else {
                    this.pressedBotButton = i;
                    invalidate();
                    return true;
                }
            }
            return false;
        } else if (motionEvent.getAction() != 1 || this.pressedBotButton == -1) {
            return false;
        } else {
            playSoundEffect(0);
            this.delegate.didPressedBotButton(this, ((BotButton) this.botButtons.get(this.pressedBotButton)).button);
            this.pressedBotButton = -1;
            invalidate();
            return false;
        }
    }

    private boolean checkCaptionMotionEvent(MotionEvent motionEvent) {
        if (!(this.currentMessageObject.caption instanceof Spannable) || this.captionLayout == null) {
            return false;
        }
        if (motionEvent.getAction() == 0 || ((this.linkPreviewPressed || this.pressedLink != null) && motionEvent.getAction() == 1)) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (x < this.captionX || x > this.captionX + this.backgroundWidth || y < this.captionY || y > this.captionY + this.captionHeight) {
                resetPressedLink(3);
            } else if (motionEvent.getAction() == 0) {
                try {
                    x -= this.captionX;
                    y = this.captionLayout.getLineForVertical(y - this.captionY);
                    int offsetForHorizontal = this.captionLayout.getOffsetForHorizontal(y, (float) x);
                    float lineLeft = this.captionLayout.getLineLeft(y);
                    if (lineLeft <= ((float) x) && this.captionLayout.getLineWidth(y) + lineLeft >= ((float) x)) {
                        Spannable spannable = (Spannable) this.currentMessageObject.caption;
                        ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                        boolean z = clickableSpanArr.length == 0 || !(clickableSpanArr.length == 0 || !(clickableSpanArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled);
                        if (!z) {
                            this.pressedLink = clickableSpanArr[0];
                            this.pressedLinkType = 3;
                            resetUrlPaths(false);
                            try {
                                Path obtainNewUrlPath = obtainNewUrlPath(false);
                                offsetForHorizontal = spannable.getSpanStart(this.pressedLink);
                                obtainNewUrlPath.setCurrentLayout(this.captionLayout, offsetForHorizontal, BitmapDescriptorFactory.HUE_RED);
                                this.captionLayout.getSelectionPath(offsetForHorizontal, spannable.getSpanEnd(this.pressedLink), obtainNewUrlPath);
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                            invalidate();
                            return true;
                        }
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            } else if (this.pressedLinkType == 3) {
                this.delegate.didPressedUrl(this.currentMessageObject, this.pressedLink, false);
                resetPressedLink(3);
                return true;
            }
        }
        return false;
    }

    private boolean checkGameMotionEvent(MotionEvent motionEvent) {
        if (!this.hasGamePreview) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.drawPhotoImage && this.photoImage.isInsideImage((float) x, (float) y)) {
                this.gamePreviewPressed = true;
                return true;
            } else if (this.descriptionLayout == null || y < this.descriptionY) {
                return false;
            } else {
                try {
                    x -= (this.textX + AndroidUtilities.dp(10.0f)) + this.descriptionX;
                    y = this.descriptionLayout.getLineForVertical(y - this.descriptionY);
                    int offsetForHorizontal = this.descriptionLayout.getOffsetForHorizontal(y, (float) x);
                    float lineLeft = this.descriptionLayout.getLineLeft(y);
                    if (lineLeft > ((float) x) || this.descriptionLayout.getLineWidth(y) + lineLeft < ((float) x)) {
                        return false;
                    }
                    Spannable spannable = (Spannable) this.currentMessageObject.linkDescription;
                    ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                    boolean z = clickableSpanArr.length == 0 || !(clickableSpanArr.length == 0 || !(clickableSpanArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled);
                    if (z) {
                        return false;
                    }
                    this.pressedLink = clickableSpanArr[0];
                    this.linkBlockNum = -10;
                    this.pressedLinkType = 2;
                    resetUrlPaths(false);
                    try {
                        Path obtainNewUrlPath = obtainNewUrlPath(false);
                        offsetForHorizontal = spannable.getSpanStart(this.pressedLink);
                        obtainNewUrlPath.setCurrentLayout(this.descriptionLayout, offsetForHorizontal, BitmapDescriptorFactory.HUE_RED);
                        this.descriptionLayout.getSelectionPath(offsetForHorizontal, spannable.getSpanEnd(this.pressedLink), obtainNewUrlPath);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    invalidate();
                    return true;
                } catch (Throwable e2) {
                    FileLog.e(e2);
                    return false;
                }
            }
        } else if (motionEvent.getAction() != 1) {
            return false;
        } else {
            if (this.pressedLinkType != 2 && !this.gamePreviewPressed) {
                resetPressedLink(2);
                return false;
            } else if (this.pressedLink != null) {
                if (this.pressedLink instanceof URLSpan) {
                    Browser.openUrl(getContext(), ((URLSpan) this.pressedLink).getURL());
                } else if (this.pressedLink instanceof ClickableSpan) {
                    ((ClickableSpan) this.pressedLink).onClick(this);
                }
                resetPressedLink(2);
                return false;
            } else {
                this.gamePreviewPressed = false;
                for (y = 0; y < this.botButtons.size(); y++) {
                    BotButton botButton = (BotButton) this.botButtons.get(y);
                    if (botButton.button instanceof TLRPC$TL_keyboardButtonGame) {
                        playSoundEffect(0);
                        this.delegate.didPressedBotButton(this, botButton.button);
                        invalidate();
                        break;
                    }
                }
                resetPressedLink(2);
                return true;
            }
        }
    }

    private boolean checkLinkPreviewMotionEvent(MotionEvent motionEvent) {
        if (this.currentMessageObject.type != 0 || !this.hasLinkPreview) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (x < this.textX || x > this.textX + this.backgroundWidth || y < this.textY + this.currentMessageObject.textHeight) {
            return false;
        }
        if (y > AndroidUtilities.dp((float) ((this.drawInstantView ? 46 : 0) + 8)) + (this.linkPreviewHeight + (this.textY + this.currentMessageObject.textHeight))) {
            return false;
        }
        if (motionEvent.getAction() == 0) {
            if (this.descriptionLayout != null && y >= this.descriptionY) {
                try {
                    int dp = x - ((this.textX + AndroidUtilities.dp(10.0f)) + this.descriptionX);
                    int i = y - this.descriptionY;
                    if (i <= this.descriptionLayout.getHeight()) {
                        i = this.descriptionLayout.getLineForVertical(i);
                        int offsetForHorizontal = this.descriptionLayout.getOffsetForHorizontal(i, (float) dp);
                        float lineLeft = this.descriptionLayout.getLineLeft(i);
                        if (lineLeft <= ((float) dp) && this.descriptionLayout.getLineWidth(i) + lineLeft >= ((float) dp)) {
                            Spannable spannable = (Spannable) this.currentMessageObject.linkDescription;
                            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                            boolean z = clickableSpanArr.length == 0 || !(clickableSpanArr.length == 0 || !(clickableSpanArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled);
                            if (!z) {
                                this.pressedLink = clickableSpanArr[0];
                                this.linkBlockNum = -10;
                                this.pressedLinkType = 2;
                                resetUrlPaths(false);
                                try {
                                    Path obtainNewUrlPath = obtainNewUrlPath(false);
                                    offsetForHorizontal = spannable.getSpanStart(this.pressedLink);
                                    obtainNewUrlPath.setCurrentLayout(this.descriptionLayout, offsetForHorizontal, BitmapDescriptorFactory.HUE_RED);
                                    this.descriptionLayout.getSelectionPath(offsetForHorizontal, spannable.getSpanEnd(this.pressedLink), obtainNewUrlPath);
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                invalidate();
                                return true;
                            }
                        }
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            if (this.pressedLink != null) {
                return false;
            }
            if (this.drawPhotoImage && this.drawImageButton && this.buttonState != -1 && x >= this.buttonX && x <= this.buttonX + AndroidUtilities.dp(48.0f) && y >= this.buttonY && y <= this.buttonY + AndroidUtilities.dp(48.0f)) {
                this.buttonPressed = 1;
                return true;
            } else if (this.drawInstantView) {
                this.instantPressed = true;
                if (VERSION.SDK_INT >= 21 && this.instantViewSelectorDrawable != null && this.instantViewSelectorDrawable.getBounds().contains(x, y)) {
                    this.instantViewSelectorDrawable.setState(this.pressedState);
                    this.instantViewSelectorDrawable.setHotspot((float) x, (float) y);
                    this.instantButtonPressed = true;
                }
                invalidate();
                return true;
            } else if (this.documentAttachType == 1 || !this.drawPhotoImage || !this.photoImage.isInsideImage((float) x, (float) y)) {
                return false;
            } else {
                this.linkPreviewPressed = true;
                TLRPC$WebPage tLRPC$WebPage = this.currentMessageObject.messageOwner.media.webpage;
                if (this.documentAttachType != 2 || this.buttonState != -1 || !MediaController.getInstance().canAutoplayGifs() || (this.photoImage.getAnimation() != null && TextUtils.isEmpty(tLRPC$WebPage.embed_url))) {
                    return true;
                }
                this.linkPreviewPressed = false;
                return false;
            }
        } else if (motionEvent.getAction() == 1) {
            if (this.instantPressed) {
                if (C3791b.f() && this.drawInstantViewType == 1000) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.goToPayment, new Object[]{this.currentMessageObject.messageOwner.message});
                    return false;
                }
                if (this.delegate != null) {
                    this.delegate.didPressedInstantButton(this, this.drawInstantViewType);
                }
                playSoundEffect(0);
                if (VERSION.SDK_INT >= 21 && this.instantViewSelectorDrawable != null) {
                    this.instantViewSelectorDrawable.setState(StateSet.NOTHING);
                }
                this.instantButtonPressed = false;
                this.instantPressed = false;
                invalidate();
                return false;
            } else if (this.pressedLinkType != 2 && this.buttonPressed == 0 && !this.linkPreviewPressed) {
                resetPressedLink(2);
                return false;
            } else if (this.buttonPressed != 0) {
                this.buttonPressed = 0;
                playSoundEffect(0);
                didPressedButton(false);
                invalidate();
                return false;
            } else if (this.pressedLink != null) {
                if (this.pressedLink instanceof URLSpan) {
                    Browser.openUrl(getContext(), ((URLSpan) this.pressedLink).getURL());
                } else if (this.pressedLink instanceof ClickableSpan) {
                    ((ClickableSpan) this.pressedLink).onClick(this);
                }
                resetPressedLink(2);
                return false;
            } else {
                if (this.documentAttachType == 7) {
                    if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isMessagePaused()) {
                        this.delegate.needPlayMessage(this.currentMessageObject);
                    } else {
                        MediaController.getInstance().pauseMessage(this.currentMessageObject);
                    }
                } else if (this.documentAttachType != 2 || !this.drawImageButton) {
                    TLRPC$WebPage tLRPC$WebPage2 = this.currentMessageObject.messageOwner.media.webpage;
                    if (tLRPC$WebPage2 != null && !TextUtils.isEmpty(tLRPC$WebPage2.embed_url)) {
                        this.delegate.needOpenWebView(tLRPC$WebPage2.embed_url, tLRPC$WebPage2.site_name, tLRPC$WebPage2.title, tLRPC$WebPage2.url, tLRPC$WebPage2.embed_width, tLRPC$WebPage2.embed_height);
                    } else if (this.buttonState == -1 || this.buttonState == 3) {
                        this.delegate.didPressedImage(this);
                        playSoundEffect(0);
                    } else if (tLRPC$WebPage2 != null) {
                        Browser.openUrl(getContext(), tLRPC$WebPage2.url);
                    }
                } else if (this.buttonState == -1) {
                    if (MediaController.getInstance().canAutoplayGifs()) {
                        this.delegate.didPressedImage(this);
                    } else {
                        this.buttonState = 2;
                        this.currentMessageObject.gifState = 1.0f;
                        this.photoImage.setAllowStartAnimation(false);
                        this.photoImage.stopAnimation();
                        this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                        invalidate();
                        playSoundEffect(0);
                    }
                } else if (this.buttonState == 2 || this.buttonState == 0) {
                    didPressedButton(false);
                    playSoundEffect(0);
                }
                resetPressedLink(2);
                return true;
            }
        } else if (motionEvent.getAction() != 2 || !this.instantButtonPressed || VERSION.SDK_INT < 21 || this.instantViewSelectorDrawable == null) {
            return false;
        } else {
            this.instantViewSelectorDrawable.setHotspot((float) x, (float) y);
            return false;
        }
    }

    private void checkLocationExpired() {
        if (this.currentMessageObject != null) {
            boolean isCurrentLocationTimeExpired = isCurrentLocationTimeExpired(this.currentMessageObject);
            if (isCurrentLocationTimeExpired != this.locationExpired) {
                this.locationExpired = isCurrentLocationTimeExpired;
                if (this.locationExpired) {
                    MessageObject messageObject = this.currentMessageObject;
                    this.currentMessageObject = null;
                    setMessageObject(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
                    return;
                }
                AndroidUtilities.runOnUIThread(this.invalidateRunnable, 1000);
                this.scheduledInvalidate = true;
                this.docTitleLayout = new StaticLayout(LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation), Theme.chat_locationTitlePaint, this.backgroundWidth - AndroidUtilities.dp(91.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            }
        }
    }

    private boolean checkNeedDrawShareButton(MessageObject messageObject) {
        boolean z;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.from_id));
        if (!(this.currentUser == null || this.currentMessageObject.isOut() || messageObject.type == 13)) {
            z = sharedPreferences.getBoolean("showDSBtnUsers", false);
            boolean z2 = sharedPreferences.getBoolean("showDSBtnGroups", true);
            boolean z3 = sharedPreferences.getBoolean("showDSBtnSGroups", true);
            if ((sharedPreferences.getBoolean("showDSBtnBots", true) && this.currentUser.bot) || (!(this.isChat || this.currentUser.bot || !z) || ((z3 && messageObject.isMegagroup()) || (messageObject.messageOwner.to_id.chat_id != 0 && z2)))) {
                z = true;
                if (this.currentPosition == null && !this.currentPosition.last) {
                    return false;
                }
                if (messageObject.eventId != 0) {
                    return false;
                }
                if (messageObject.messageOwner.fwd_from == null && !messageObject.isOutOwner() && messageObject.messageOwner.fwd_from.saved_from_peer != null && messageObject.getDialogId() == ((long) UserConfig.getClientUserId())) {
                    this.drwaShareGoIcon = true;
                    return true;
                } else if (messageObject.type == 13) {
                    return false;
                } else {
                    if (messageObject.messageOwner.fwd_from == null && messageObject.messageOwner.fwd_from.channel_id != 0 && !messageObject.isOutOwner()) {
                        return z;
                    } else {
                        if (messageObject.isFromUser()) {
                            if (messageObject.messageOwner.from_id < 0 || messageObject.messageOwner.post) {
                                z = sharedPreferences.getBoolean("showDSBtnChannels", true);
                                if (messageObject.messageOwner.to_id.channel_id != 0 && ((messageObject.messageOwner.via_bot_id == 0 && messageObject.messageOwner.reply_to_msg_id == 0) || messageObject.type != 13)) {
                                    return z;
                                }
                            }
                        } else if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty) || messageObject.messageOwner.media == null || ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && !(messageObject.messageOwner.media.webpage instanceof TLRPC$TL_webPage))) {
                            return false;
                        } else {
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
                            if (user != null && user.bot) {
                                return z;
                            } else {
                                if (!messageObject.isOut()) {
                                    if ((messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
                                        return true;
                                    }
                                    if (messageObject.isMegagroup()) {
                                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(messageObject.messageOwner.to_id.channel_id));
                                        return (!z || chat == null || chat.username == null || chat.username.length() <= 0 || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) || (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo)) ? false : true;
                                    }
                                }
                            }
                        }
                        return false;
                    }
                }
            }
        }
        z = false;
        if (this.currentPosition == null) {
        }
        if (messageObject.eventId != 0) {
            return false;
        }
        if (messageObject.messageOwner.fwd_from == null) {
        }
        if (messageObject.type == 13) {
            return false;
        }
        if (messageObject.messageOwner.fwd_from == null) {
        }
        if (messageObject.isFromUser()) {
            z = sharedPreferences.getBoolean("showDSBtnChannels", true);
            if (z) {
            }
        }
        if (!(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty)) {
        }
        return false;
        return false;
    }

    private boolean checkOtherButtonMotionEvent(MotionEvent motionEvent) {
        boolean z = this.currentMessageObject.type == 16;
        if (!z) {
            z = ((this.documentAttachType != 1 && this.currentMessageObject.type != 12 && this.documentAttachType != 5 && this.documentAttachType != 4 && this.documentAttachType != 2 && this.currentMessageObject.type != 8) || this.hasGamePreview || this.hasInvoicePreview) ? false : true;
        }
        if (!z) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.currentMessageObject.type == 16) {
                if (x < this.otherX || x > this.otherX + AndroidUtilities.dp(235.0f) || y < this.otherY - AndroidUtilities.dp(14.0f) || y > this.otherY + AndroidUtilities.dp(50.0f)) {
                    return false;
                }
                this.otherPressed = true;
                invalidate();
                return true;
            } else if (x < this.otherX - AndroidUtilities.dp(20.0f) || x > this.otherX + AndroidUtilities.dp(20.0f) || y < this.otherY - AndroidUtilities.dp(4.0f) || y > this.otherY + AndroidUtilities.dp(30.0f)) {
                return false;
            } else {
                this.otherPressed = true;
                invalidate();
                return true;
            }
        } else if (motionEvent.getAction() != 1 || !this.otherPressed) {
            return false;
        } else {
            this.otherPressed = false;
            playSoundEffect(0);
            this.delegate.didPressedOther(this);
            invalidate();
            return true;
        }
    }

    private boolean checkPhotoImageMotionEvent(MotionEvent motionEvent) {
        boolean z = true;
        if (!this.drawPhotoImage && this.documentAttachType != 1) {
            return false;
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.buttonState == -1 || x < this.buttonX || x > this.buttonX + AndroidUtilities.dp(48.0f) || y < this.buttonY || y > this.buttonY + AndroidUtilities.dp(48.0f)) {
                if (this.documentAttachType == 1) {
                    if (x >= this.photoImage.getImageX() && x <= (this.photoImage.getImageX() + this.backgroundWidth) - AndroidUtilities.dp(50.0f) && y >= this.photoImage.getImageY() && y <= this.photoImage.getImageY() + this.photoImage.getImageHeight()) {
                        this.imagePressed = true;
                    }
                } else if (!(this.currentMessageObject.type == 13 && this.currentMessageObject.getInputStickerSet() == null)) {
                    if (x < this.photoImage.getImageX() || x > this.photoImage.getImageX() + this.backgroundWidth || y < this.photoImage.getImageY() || y > this.photoImage.getImageY() + this.photoImage.getImageHeight()) {
                        z = false;
                    } else {
                        this.imagePressed = true;
                    }
                    if (this.currentMessageObject.type == 12 && MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.media.user_id)) == null) {
                        this.imagePressed = false;
                        z = false;
                    }
                }
                z = false;
            } else {
                this.buttonPressed = 1;
                invalidate();
            }
            if (this.imagePressed) {
                if (this.currentMessageObject.isSendError()) {
                    this.imagePressed = false;
                    return false;
                } else if (this.currentMessageObject.type == 8 && this.buttonState == -1 && MediaController.getInstance().canAutoplayGifs() && this.photoImage.getAnimation() == null) {
                    this.imagePressed = false;
                    return false;
                } else if (this.currentMessageObject.type == 5 && this.buttonState != -1) {
                    this.imagePressed = false;
                    return false;
                }
            }
            return z;
        } else if (motionEvent.getAction() != 1) {
            return false;
        } else {
            if (this.buttonPressed == 1) {
                this.buttonPressed = 0;
                playSoundEffect(0);
                didPressedButton(false);
                updateRadialProgressBackground(true);
                invalidate();
                return false;
            } else if (!this.imagePressed) {
                return false;
            } else {
                this.imagePressed = false;
                if (this.buttonState == -1 || this.buttonState == 2 || this.buttonState == 3) {
                    playSoundEffect(0);
                    didClickedImage();
                } else if (this.buttonState == 0 && this.documentAttachType == 1) {
                    playSoundEffect(0);
                    didPressedButton(false);
                }
                invalidate();
                return false;
            }
        }
    }

    private boolean checkTextBlockMotionEvent(MotionEvent motionEvent) {
        if (this.currentMessageObject.type != 0 || this.currentMessageObject.textLayoutBlocks == null || this.currentMessageObject.textLayoutBlocks.isEmpty() || !(this.currentMessageObject.messageText instanceof Spannable)) {
            return false;
        }
        if (motionEvent.getAction() == 0 || (motionEvent.getAction() == 1 && this.pressedLinkType == 1)) {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (x < this.textX || y < this.textY || x > this.textX + this.currentMessageObject.textWidth || y > this.textY + this.currentMessageObject.textHeight) {
                resetPressedLink(1);
            } else {
                int i = y - this.textY;
                int i2 = 0;
                int i3 = 0;
                while (i3 < this.currentMessageObject.textLayoutBlocks.size() && ((TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i3)).textYOffset <= ((float) i)) {
                    i2 = i3;
                    i3++;
                }
                try {
                    TextLayoutBlock textLayoutBlock = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i2);
                    i3 = (int) (((float) x) - (((float) this.textX) - (textLayoutBlock.isRtl() ? this.currentMessageObject.textXOffset : BitmapDescriptorFactory.HUE_RED)));
                    x = textLayoutBlock.textLayout.getLineForVertical((int) (((float) i) - textLayoutBlock.textYOffset));
                    int offsetForHorizontal = textLayoutBlock.textLayout.getOffsetForHorizontal(x, (float) i3);
                    float lineLeft = textLayoutBlock.textLayout.getLineLeft(x);
                    if (lineLeft <= ((float) i3) && textLayoutBlock.textLayout.getLineWidth(x) + lineLeft >= ((float) i3)) {
                        Object obj;
                        CharacterStyle[] characterStyleArr;
                        Spannable spannable = (Spannable) this.currentMessageObject.messageText;
                        CharacterStyle[] characterStyleArr2 = (CharacterStyle[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                        if (characterStyleArr2 == null || characterStyleArr2.length == 0) {
                            obj = 1;
                            characterStyleArr = (CharacterStyle[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, URLSpanMono.class);
                        } else {
                            obj = null;
                            characterStyleArr = characterStyleArr2;
                        }
                        Object obj2 = null;
                        if (characterStyleArr.length == 0 || !(characterStyleArr.length == 0 || !(characterStyleArr[0] instanceof URLSpanBotCommand) || URLSpanBotCommand.enabled)) {
                            obj2 = 1;
                        }
                        if (obj2 == null) {
                            if (motionEvent.getAction() == 0) {
                                this.pressedLink = characterStyleArr[0];
                                this.linkBlockNum = i2;
                                this.pressedLinkType = 1;
                                resetUrlPaths(false);
                                try {
                                    Path obtainNewUrlPath = obtainNewUrlPath(false);
                                    int spanStart = spannable.getSpanStart(this.pressedLink);
                                    int spanEnd = spannable.getSpanEnd(this.pressedLink);
                                    obtainNewUrlPath.setCurrentLayout(textLayoutBlock.textLayout, spanStart, BitmapDescriptorFactory.HUE_RED);
                                    textLayoutBlock.textLayout.getSelectionPath(spanStart, spanEnd, obtainNewUrlPath);
                                    if (spanEnd >= textLayoutBlock.charactersEnd) {
                                        offsetForHorizontal = i2 + 1;
                                        while (offsetForHorizontal < this.currentMessageObject.textLayoutBlocks.size()) {
                                            TextLayoutBlock textLayoutBlock2 = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(offsetForHorizontal);
                                            characterStyleArr = (CharacterStyle[]) spannable.getSpans(textLayoutBlock2.charactersOffset, textLayoutBlock2.charactersOffset, obj != null ? URLSpanMono.class : ClickableSpan.class);
                                            if (characterStyleArr != null && characterStyleArr.length != 0 && characterStyleArr[0] == this.pressedLink) {
                                                Path obtainNewUrlPath2 = obtainNewUrlPath(false);
                                                obtainNewUrlPath2.setCurrentLayout(textLayoutBlock2.textLayout, 0, textLayoutBlock2.textYOffset - textLayoutBlock.textYOffset);
                                                textLayoutBlock2.textLayout.getSelectionPath(0, spanEnd, obtainNewUrlPath2);
                                                if (spanEnd < textLayoutBlock2.charactersEnd - 1) {
                                                    break;
                                                }
                                                offsetForHorizontal++;
                                            } else {
                                                break;
                                            }
                                        }
                                    }
                                    if (spanStart <= textLayoutBlock.charactersOffset) {
                                        i = i2 - 1;
                                        i2 = 0;
                                        while (i >= 0) {
                                            textLayoutBlock = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i);
                                            characterStyleArr2 = (CharacterStyle[]) spannable.getSpans(textLayoutBlock.charactersEnd - 1, textLayoutBlock.charactersEnd - 1, obj != null ? URLSpanMono.class : ClickableSpan.class);
                                            if (characterStyleArr2 != null && characterStyleArr2.length != 0 && characterStyleArr2[0] == this.pressedLink) {
                                                Path obtainNewUrlPath3 = obtainNewUrlPath(false);
                                                spanStart = spannable.getSpanStart(this.pressedLink);
                                                x = i2 - textLayoutBlock.height;
                                                obtainNewUrlPath3.setCurrentLayout(textLayoutBlock.textLayout, spanStart, (float) x);
                                                textLayoutBlock.textLayout.getSelectionPath(spanStart, spannable.getSpanEnd(this.pressedLink), obtainNewUrlPath3);
                                                if (spanStart <= textLayoutBlock.charactersOffset) {
                                                    i--;
                                                    i2 = x;
                                                }
                                            }
                                        }
                                    }
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                invalidate();
                                return true;
                            } else if (characterStyleArr[0] == this.pressedLink) {
                                this.delegate.didPressedUrl(this.currentMessageObject, this.pressedLink, false);
                                resetPressedLink(1);
                                return true;
                            }
                        }
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
        }
        return false;
    }

    private int createDocumentLayout(int i, MessageObject messageObject) {
        if (messageObject.type == 0) {
            this.documentAttach = messageObject.messageOwner.media.webpage.document;
        } else {
            this.documentAttach = messageObject.messageOwner.media.document;
        }
        if (this.documentAttach == null) {
            return 0;
        }
        int i2;
        DocumentAttribute documentAttribute;
        int i3;
        if (MessageObject.isVoiceDocument(this.documentAttach)) {
            this.documentAttachType = 3;
            for (i2 = 0; i2 < this.documentAttach.attributes.size(); i2++) {
                documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i2);
                if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                    i3 = documentAttribute.duration;
                    break;
                }
            }
            i3 = 0;
            this.widthBeforeNewTimeLine = (i - AndroidUtilities.dp(94.0f)) - ((int) Math.ceil((double) Theme.chat_audioTimePaint.measureText("00:00")));
            this.availableTimeWidth = i - AndroidUtilities.dp(18.0f);
            measureTime(messageObject);
            i2 = AndroidUtilities.dp(174.0f) + this.timeWidth;
            if (!this.hasLinkPreview) {
                this.backgroundWidth = Math.min(i, (i3 * AndroidUtilities.dp(10.0f)) + i2);
            }
            this.seekBarWaveform.setMessageObject(messageObject);
            return 0;
        } else if (MessageObject.isMusicDocument(this.documentAttach)) {
            this.documentAttachType = 5;
            int dp = i - AndroidUtilities.dp(86.0f);
            this.songLayout = new StaticLayout(TextUtils.ellipsize(messageObject.getMusicTitle().replace('\n', ' '), Theme.chat_audioTitlePaint, (float) (dp - AndroidUtilities.dp(12.0f)), TruncateAt.END), Theme.chat_audioTitlePaint, dp, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            if (this.songLayout.getLineCount() > 0) {
                this.songX = -((int) Math.ceil((double) this.songLayout.getLineLeft(0)));
            }
            this.performerLayout = new StaticLayout(TextUtils.ellipsize(messageObject.getMusicAuthor().replace('\n', ' '), Theme.chat_audioPerformerPaint, (float) dp, TruncateAt.END), Theme.chat_audioPerformerPaint, dp, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            if (this.performerLayout.getLineCount() > 0) {
                this.performerX = -((int) Math.ceil((double) this.performerLayout.getLineLeft(0)));
            }
            for (i2 = 0; i2 < this.documentAttach.attributes.size(); i2++) {
                documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i2);
                if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                    i3 = documentAttribute.duration;
                    break;
                }
            }
            i3 = 0;
            r10 = (int) Math.ceil((double) Theme.chat_audioTimePaint.measureText(String.format("%d:%02d / %d:%02d", new Object[]{Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60), Integer.valueOf(i3 / 60), Integer.valueOf(i3 % 60)})));
            this.widthBeforeNewTimeLine = (this.backgroundWidth - AndroidUtilities.dp(86.0f)) - r10;
            this.availableTimeWidth = this.backgroundWidth - AndroidUtilities.dp(28.0f);
            return r10;
        } else if (MessageObject.isVideoDocument(this.documentAttach)) {
            this.documentAttachType = 4;
            if (!messageObject.isSecretPhoto()) {
                for (i2 = 0; i2 < this.documentAttach.attributes.size(); i2++) {
                    documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i2);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                        i3 = documentAttribute.duration;
                        break;
                    }
                }
                i3 = 0;
                i3 -= (i3 / 60) * 60;
                r1 = String.format("%d:%02d, %s", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), AndroidUtilities.formatFileSize((long) this.documentAttach.size)});
                this.infoWidth = (int) Math.ceil((double) Theme.chat_infoPaint.measureText(r1));
                this.infoLayout = new StaticLayout(r1, Theme.chat_infoPaint, this.infoWidth, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            }
            return 0;
        } else {
            boolean z = (this.documentAttach.mime_type != null && this.documentAttach.mime_type.toLowerCase().startsWith("image/")) || ((this.documentAttach.thumb instanceof TLRPC$TL_photoSize) && !(this.documentAttach.thumb.location instanceof TLRPC$TL_fileLocationUnavailable));
            this.drawPhotoImage = z;
            int dp2 = !this.drawPhotoImage ? i + AndroidUtilities.dp(30.0f) : i;
            this.documentAttachType = 1;
            CharSequence documentFileName = FileLoader.getDocumentFileName(this.documentAttach);
            if (documentFileName == null || documentFileName.length() == 0) {
                documentFileName = LocaleController.getString("AttachDocument", R.string.AttachDocument);
            }
            this.docTitleLayout = StaticLayoutEx.createStaticLayout(documentFileName, Theme.chat_docNamePaint, dp2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false, TruncateAt.MIDDLE, dp2, this.drawPhotoImage ? 2 : 1);
            this.docTitleOffsetX = Integer.MIN_VALUE;
            if (this.docTitleLayout == null || this.docTitleLayout.getLineCount() <= 0) {
                this.docTitleOffsetX = 0;
                r10 = dp2;
            } else {
                i2 = 0;
                for (i3 = 0; i3 < this.docTitleLayout.getLineCount(); i3++) {
                    i2 = Math.max(i2, (int) Math.ceil((double) this.docTitleLayout.getLineWidth(i3)));
                    this.docTitleOffsetX = Math.max(this.docTitleOffsetX, (int) Math.ceil((double) (-this.docTitleLayout.getLineLeft(i3))));
                }
                r10 = Math.min(dp2, i2);
            }
            documentFileName = AndroidUtilities.formatFileSize((long) this.documentAttach.size) + " " + FileLoader.getDocumentExtension(this.documentAttach);
            this.infoWidth = Math.min(dp2 - AndroidUtilities.dp(30.0f), (int) Math.ceil((double) Theme.chat_infoPaint.measureText(documentFileName)));
            r1 = TextUtils.ellipsize(documentFileName, Theme.chat_infoPaint, (float) this.infoWidth, TruncateAt.END);
            try {
                if (this.infoWidth < 0) {
                    this.infoWidth = AndroidUtilities.dp(10.0f);
                }
                this.infoLayout = new StaticLayout(r1, Theme.chat_infoPaint, this.infoWidth, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (!this.drawPhotoImage) {
                return r10;
            }
            this.currentPhotoObject = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
            this.photoImage.setNeedsQualityThumb(true);
            this.photoImage.setShouldGenerateQualityThumb(true);
            this.photoImage.setParentMessageObject(messageObject);
            if (this.currentPhotoObject != null) {
                this.currentPhotoFilter = "86_86_b";
                this.photoImage.setImage(null, null, null, null, this.currentPhotoObject.location, this.currentPhotoFilter, 0, null, 1);
                return r10;
            }
            this.photoImage.setImageBitmap((BitmapDrawable) null);
            return r10;
        }
    }

    private void didClickedImage() {
        if (this.currentMessageObject.type == 1 || this.currentMessageObject.type == 13) {
            if (this.buttonState == -1) {
                this.delegate.didPressedImage(this);
            } else if (this.buttonState == 0) {
                didPressedButton(false);
            }
        } else if (this.currentMessageObject.type == 12) {
            this.delegate.didPressedUserAvatar(this, MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.media.user_id)));
        } else if (this.currentMessageObject.type == 5) {
            if (!MediaController.getInstance().isPlayingMessage(this.currentMessageObject) || MediaController.getInstance().isMessagePaused()) {
                this.delegate.needPlayMessage(this.currentMessageObject);
            } else {
                MediaController.getInstance().pauseMessage(this.currentMessageObject);
            }
        } else if (this.currentMessageObject.type == 8) {
            if (this.buttonState == -1) {
                if (MediaController.getInstance().canAutoplayGifs()) {
                    this.delegate.didPressedImage(this);
                    return;
                }
                this.buttonState = 2;
                this.currentMessageObject.gifState = 1.0f;
                this.photoImage.setAllowStartAnimation(false);
                this.photoImage.stopAnimation();
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            } else if (this.buttonState == 2 || this.buttonState == 0) {
                didPressedButton(false);
            }
        } else if (this.documentAttachType == 4) {
            if (this.buttonState == -1) {
                this.delegate.didPressedImage(this);
            } else if (this.buttonState == 0 || this.buttonState == 3) {
                didPressedButton(false);
            }
        } else if (this.currentMessageObject.type == 4) {
            this.delegate.didPressedImage(this);
        } else if (this.documentAttachType == 1) {
            if (this.buttonState == -1) {
                this.delegate.didPressedImage(this);
            }
        } else if (this.documentAttachType == 2) {
            if (this.buttonState == -1) {
                TLRPC$WebPage tLRPC$WebPage = this.currentMessageObject.messageOwner.media.webpage;
                if (tLRPC$WebPage == null) {
                    return;
                }
                if (tLRPC$WebPage.embed_url == null || tLRPC$WebPage.embed_url.length() == 0) {
                    Browser.openUrl(getContext(), tLRPC$WebPage.url);
                } else {
                    this.delegate.needOpenWebView(tLRPC$WebPage.embed_url, tLRPC$WebPage.site_name, tLRPC$WebPage.description, tLRPC$WebPage.url, tLRPC$WebPage.embed_width, tLRPC$WebPage.embed_height);
                }
            }
        } else if (this.hasInvoicePreview && this.buttonState == -1) {
            this.delegate.didPressedImage(this);
        }
    }

    private void didPressedButton(boolean z) {
        int i = 2;
        if (this.buttonState == 0) {
            if (this.documentAttachType != 3 && this.documentAttachType != 5) {
                this.cancelLoading = false;
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                if (this.currentMessageObject.type == 1) {
                    this.photoImage.setForceLoading(true);
                    ImageReceiver imageReceiver = this.photoImage;
                    TLObject tLObject = this.currentPhotoObject.location;
                    String str = this.currentPhotoFilter;
                    FileLocation fileLocation = this.currentPhotoObjectThumb != null ? this.currentPhotoObjectThumb.location : null;
                    String str2 = this.currentPhotoFilterThumb;
                    int i2 = this.currentPhotoObject.size;
                    if (!this.currentMessageObject.shouldEncryptPhotoOrVideo()) {
                        i = 0;
                    }
                    imageReceiver.setImage(tLObject, str, fileLocation, str2, i2, null, i);
                } else if (this.currentMessageObject.type == 8) {
                    this.currentMessageObject.gifState = 2.0f;
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(this.currentMessageObject.messageOwner.media.document, null, this.currentPhotoObject != null ? this.currentPhotoObject.location : null, this.currentPhotoFilterThumb, this.currentMessageObject.messageOwner.media.document.size, null, 0);
                } else if (this.currentMessageObject.isRoundVideo()) {
                    if (this.currentMessageObject.isSecretMedia()) {
                        FileLoader.getInstance().loadFile(this.currentMessageObject.getDocument(), true, 1);
                    } else {
                        this.currentMessageObject.gifState = 2.0f;
                        TLObject document = this.currentMessageObject.getDocument();
                        this.photoImage.setForceLoading(true);
                        this.photoImage.setImage(document, null, this.currentPhotoObject != null ? this.currentPhotoObject.location : null, this.currentPhotoFilterThumb, document.size, null, 0);
                    }
                } else if (this.currentMessageObject.type == 9) {
                    FileLoader.getInstance().loadFile(this.currentMessageObject.messageOwner.media.document, false, 0);
                } else if (this.documentAttachType == 4) {
                    FileLoader instance = FileLoader.getInstance();
                    Document document2 = this.documentAttach;
                    if (!this.currentMessageObject.shouldEncryptPhotoOrVideo()) {
                        i = 0;
                    }
                    instance.loadFile(document2, true, i);
                } else if (this.currentMessageObject.type != 0 || this.documentAttachType == 0) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(this.currentPhotoObject.location, this.currentPhotoFilter, this.currentPhotoObjectThumb != null ? this.currentPhotoObjectThumb.location : null, this.currentPhotoFilterThumb, 0, null, 0);
                } else if (this.documentAttachType == 2) {
                    this.photoImage.setForceLoading(true);
                    this.photoImage.setImage(this.currentMessageObject.messageOwner.media.webpage.document, null, this.currentPhotoObject.location, this.currentPhotoFilterThumb, this.currentMessageObject.messageOwner.media.webpage.document.size, null, 0);
                    this.currentMessageObject.gifState = 2.0f;
                } else if (this.documentAttachType == 1) {
                    FileLoader.getInstance().loadFile(this.currentMessageObject.messageOwner.media.webpage.document, false, 0);
                }
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
                invalidate();
            } else if (this.delegate.needPlayMessage(this.currentMessageObject)) {
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        } else if (this.buttonState == 1) {
            if (this.documentAttachType == 3 || this.documentAttachType == 5) {
                if (MediaController.getInstance().pauseMessage(this.currentMessageObject)) {
                    this.buttonState = 0;
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                    invalidate();
                }
            } else if (this.currentMessageObject.isOut() && this.currentMessageObject.isSending()) {
                this.delegate.didPressedCancelSendButton(this);
            } else {
                this.cancelLoading = true;
                if (this.documentAttachType == 4 || this.documentAttachType == 1) {
                    FileLoader.getInstance().cancelLoadFile(this.documentAttach);
                } else if (this.currentMessageObject.type == 0 || this.currentMessageObject.type == 1 || this.currentMessageObject.type == 8 || this.currentMessageObject.type == 5) {
                    ImageLoader.getInstance().cancelForceLoadingForImageReceiver(this.photoImage);
                    this.photoImage.cancelLoadImage();
                } else if (this.currentMessageObject.type == 9) {
                    FileLoader.getInstance().cancelLoadFile(this.currentMessageObject.messageOwner.media.document);
                }
                this.buttonState = 0;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                invalidate();
            }
        } else if (this.buttonState == 2) {
            if (this.documentAttachType == 3 || this.documentAttachType == 5) {
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                FileLoader.getInstance().loadFile(this.documentAttach, true, 0);
                this.buttonState = 4;
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, false);
                invalidate();
                return;
            }
            this.photoImage.setAllowStartAnimation(true);
            this.photoImage.startAnimation();
            this.currentMessageObject.gifState = BitmapDescriptorFactory.HUE_RED;
            this.buttonState = -1;
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
        } else if (this.buttonState == 3) {
            this.delegate.didPressedImage(this);
        } else if (this.buttonState != 4) {
        } else {
            if (this.documentAttachType != 3 && this.documentAttachType != 5) {
                return;
            }
            if ((!this.currentMessageObject.isOut() || !this.currentMessageObject.isSending()) && !this.currentMessageObject.isSendError()) {
                FileLoader.getInstance().cancelLoadFile(this.documentAttach);
                this.buttonState = 2;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            } else if (this.delegate != null) {
                this.delegate.didPressedCancelSendButton(this);
            }
        }
    }

    private void drawContent(Canvas canvas) {
        int i;
        int i2;
        int i3;
        int dp;
        int dp2;
        int dp3;
        Drawable drawable;
        String str;
        if (this.needNewVisiblePart && this.currentMessageObject.type == 0) {
            getLocalVisibleRect(this.scrollRect);
            setVisiblePart(this.scrollRect.top, this.scrollRect.bottom - this.scrollRect.top);
            this.needNewVisiblePart = false;
        }
        this.forceNotDrawTime = this.currentMessagesGroup != null;
        ImageReceiver imageReceiver = this.photoImage;
        int i4 = isDrawSelectedBackground() ? this.currentPosition != null ? 2 : 1 : 0;
        imageReceiver.setPressed(i4);
        imageReceiver = this.photoImage;
        boolean z = (PhotoViewer.getInstance().isShowingImage(this.currentMessageObject) || SecretMediaViewer.getInstance().isShowingImage(this.currentMessageObject)) ? false : true;
        imageReceiver.setVisible(z, false);
        if (!this.photoImage.getVisible()) {
            this.mediaWasInvisible = true;
            this.timeWasInvisible = true;
        } else if (this.groupPhotoInvisible) {
            this.timeWasInvisible = true;
        } else if (this.mediaWasInvisible || this.timeWasInvisible) {
            if (this.mediaWasInvisible) {
                this.controlsAlpha = BitmapDescriptorFactory.HUE_RED;
                this.mediaWasInvisible = false;
            }
            if (this.timeWasInvisible) {
                this.timeAlpha = BitmapDescriptorFactory.HUE_RED;
                this.timeWasInvisible = false;
            }
            this.lastControlsAlphaChangeTime = System.currentTimeMillis();
            this.totalChangeTime = 0;
        }
        this.radialProgress.setHideCurrentDrawable(false);
        this.radialProgress.setProgressColor(Theme.getColor(Theme.key_chat_mediaProgress));
        boolean z2 = false;
        if (this.currentMessageObject.type == 0) {
            if (this.currentMessageObject.isOutOwner()) {
                this.textX = this.currentBackgroundDrawable.getBounds().left + AndroidUtilities.dp(11.0f);
            } else {
                i = this.currentBackgroundDrawable.getBounds().left;
                float f = (this.mediaBackground || !this.drawPinnedBottom) ? 17.0f : 11.0f;
                this.textX = AndroidUtilities.dp(f) + i;
            }
            if (this.hasGamePreview) {
                this.textX += AndroidUtilities.dp(11.0f);
                this.textY = AndroidUtilities.dp(14.0f) + this.namesOffset;
                if (this.siteNameLayout != null) {
                    this.textY += this.siteNameLayout.getLineBottom(this.siteNameLayout.getLineCount() - 1);
                }
            } else if (this.hasInvoicePreview) {
                this.textY = AndroidUtilities.dp(14.0f) + this.namesOffset;
                if (this.siteNameLayout != null) {
                    this.textY += this.siteNameLayout.getLineBottom(this.siteNameLayout.getLineCount() - 1);
                }
            } else {
                this.textY = AndroidUtilities.dp(10.0f) + this.namesOffset;
            }
            if (!(this.currentMessageObject.textLayoutBlocks == null || this.currentMessageObject.textLayoutBlocks.isEmpty())) {
                if (this.fullyDraw) {
                    this.firstVisibleBlockNum = 0;
                    this.lastVisibleBlockNum = this.currentMessageObject.textLayoutBlocks.size();
                }
                if (this.firstVisibleBlockNum >= 0) {
                    i2 = this.firstVisibleBlockNum;
                    while (i2 <= this.lastVisibleBlockNum && i2 < this.currentMessageObject.textLayoutBlocks.size()) {
                        TextLayoutBlock textLayoutBlock = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i2);
                        canvas.save();
                        canvas.translate((float) (this.textX - (textLayoutBlock.isRtl() ? (int) Math.ceil((double) this.currentMessageObject.textXOffset) : 0)), ((float) this.textY) + textLayoutBlock.textYOffset);
                        if (this.pressedLink != null && i2 == this.linkBlockNum) {
                            for (i3 = 0; i3 < this.urlPath.size(); i3++) {
                                canvas.drawPath((Path) this.urlPath.get(i3), Theme.chat_urlPaint);
                            }
                        }
                        if (i2 == this.linkSelectionBlockNum && !this.urlPathSelection.isEmpty()) {
                            for (i3 = 0; i3 < this.urlPathSelection.size(); i3++) {
                                canvas.drawPath((Path) this.urlPathSelection.get(i3), Theme.chat_textSearchSelectionPaint);
                            }
                        }
                        try {
                            textLayoutBlock.textLayout.draw(canvas);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        canvas.restore();
                        i2++;
                    }
                }
            }
            if (this.hasLinkPreview || this.hasGamePreview || this.hasInvoicePreview) {
                boolean draw;
                int i5;
                if (this.hasGamePreview) {
                    dp = this.textX - AndroidUtilities.dp(10.0f);
                    dp2 = this.namesOffset + AndroidUtilities.dp(14.0f);
                } else if (this.hasInvoicePreview) {
                    dp = this.textX + AndroidUtilities.dp(1.0f);
                    dp2 = this.namesOffset + AndroidUtilities.dp(14.0f);
                } else {
                    dp = this.textX + AndroidUtilities.dp(1.0f);
                    dp2 = AndroidUtilities.dp(8.0f) + (this.textY + this.currentMessageObject.textHeight);
                }
                if (!this.hasInvoicePreview) {
                    Theme.chat_replyLinePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewLine : Theme.key_chat_inPreviewLine));
                    canvas.drawRect((float) dp, (float) (dp2 - AndroidUtilities.dp(3.0f)), (float) (AndroidUtilities.dp(2.0f) + dp), (float) ((this.linkPreviewHeight + dp2) + AndroidUtilities.dp(3.0f)), Theme.chat_replyLinePaint);
                }
                if (this.siteNameLayout != null) {
                    Theme.chat_replyNamePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outSiteNameText : Theme.key_chat_inSiteNameText));
                    canvas.save();
                    canvas.translate((float) ((this.hasInvoicePreview ? 0 : AndroidUtilities.dp(10.0f)) + dp), (float) (dp2 - AndroidUtilities.dp(3.0f)));
                    this.siteNameLayout.draw(canvas);
                    canvas.restore();
                    i4 = this.siteNameLayout.getLineBottom(this.siteNameLayout.getLineCount() - 1) + dp2;
                } else {
                    i4 = dp2;
                }
                if ((this.hasGamePreview || this.hasInvoicePreview) && this.currentMessageObject.textHeight != 0) {
                    dp2 += this.currentMessageObject.textHeight + AndroidUtilities.dp(4.0f);
                    i4 += this.currentMessageObject.textHeight + AndroidUtilities.dp(4.0f);
                }
                if (this.drawPhotoImage && this.drawInstantView) {
                    if (i4 != dp2) {
                        i4 += AndroidUtilities.dp(2.0f);
                    }
                    this.photoImage.setImageCoords(AndroidUtilities.dp(10.0f) + dp, i4, this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
                    if (this.drawImageButton) {
                        i = AndroidUtilities.dp(48.0f);
                        this.buttonX = (int) (((float) this.photoImage.getImageX()) + (((float) (this.photoImage.getImageWidth() - i)) / 2.0f));
                        this.buttonY = (int) (((float) this.photoImage.getImageY()) + (((float) (this.photoImage.getImageHeight() - i)) / 2.0f));
                        this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + i, i + this.buttonY);
                    }
                    draw = this.photoImage.draw(canvas);
                    i4 += this.photoImage.getImageHeight() + AndroidUtilities.dp(6.0f);
                } else {
                    draw = false;
                }
                if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_replyNamePaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                    Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                } else {
                    Theme.chat_replyNamePaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                    Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                }
                if (this.titleLayout != null) {
                    if (i4 != dp2) {
                        i4 += AndroidUtilities.dp(2.0f);
                    }
                    i2 = i4 - AndroidUtilities.dp(1.0f);
                    canvas.save();
                    canvas.translate((float) ((AndroidUtilities.dp(10.0f) + dp) + this.titleX), (float) (i4 - AndroidUtilities.dp(3.0f)));
                    this.titleLayout.draw(canvas);
                    canvas.restore();
                    i5 = i2;
                    i2 = i4 + this.titleLayout.getLineBottom(this.titleLayout.getLineCount() - 1);
                    i4 = i5;
                } else {
                    i2 = i4;
                    i4 = 0;
                }
                if (this.authorLayout != null) {
                    if (i2 != dp2) {
                        i2 += AndroidUtilities.dp(2.0f);
                    }
                    if (i4 == 0) {
                        i4 = i2 - AndroidUtilities.dp(1.0f);
                    }
                    canvas.save();
                    canvas.translate((float) ((AndroidUtilities.dp(10.0f) + dp) + this.authorX), (float) (i2 - AndroidUtilities.dp(3.0f)));
                    this.authorLayout.draw(canvas);
                    canvas.restore();
                    i2 += this.authorLayout.getLineBottom(this.authorLayout.getLineCount() - 1);
                }
                if (this.descriptionLayout != null) {
                    dp3 = i2 != dp2 ? i2 + AndroidUtilities.dp(2.0f) : i2;
                    i2 = i4 == 0 ? dp3 - AndroidUtilities.dp(1.0f) : i4;
                    this.descriptionY = dp3 - AndroidUtilities.dp(3.0f);
                    canvas.save();
                    canvas.translate((float) (((this.hasInvoicePreview ? 0 : AndroidUtilities.dp(10.0f)) + dp) + this.descriptionX), (float) this.descriptionY);
                    if (this.pressedLink != null && this.linkBlockNum == -10) {
                        for (i3 = 0; i3 < this.urlPath.size(); i3++) {
                            canvas.drawPath((Path) this.urlPath.get(i3), Theme.chat_urlPaint);
                        }
                    }
                    this.descriptionLayout.draw(canvas);
                    canvas.restore();
                    i4 = this.descriptionLayout.getLineBottom(this.descriptionLayout.getLineCount() - 1) + dp3;
                } else {
                    i5 = i4;
                    i4 = i2;
                    i2 = i5;
                }
                if (!this.drawPhotoImage || this.drawInstantView) {
                    z2 = draw;
                } else {
                    if (i4 != dp2) {
                        i4 += AndroidUtilities.dp(2.0f);
                    }
                    if (this.isSmallImage) {
                        this.photoImage.setImageCoords((this.backgroundWidth + dp) - AndroidUtilities.dp(81.0f), i2, this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
                    } else {
                        this.photoImage.setImageCoords((this.hasInvoicePreview ? -AndroidUtilities.dp(6.3f) : AndroidUtilities.dp(10.0f)) + dp, i4, this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
                        if (this.drawImageButton) {
                            i = AndroidUtilities.dp(48.0f);
                            this.buttonX = (int) (((float) this.photoImage.getImageX()) + (((float) (this.photoImage.getImageWidth() - i)) / 2.0f));
                            this.buttonY = (int) (((float) this.photoImage.getImageY()) + (((float) (this.photoImage.getImageHeight() - i)) / 2.0f));
                            this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + i, i + this.buttonY);
                        }
                    }
                    if (this.currentMessageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(this.currentMessageObject) && MediaController.getInstance().isRoundVideoDrawingReady()) {
                        this.drawTime = true;
                        z2 = true;
                    } else {
                        z2 = this.photoImage.draw(canvas);
                    }
                }
                if (this.photosCountLayout != null && this.photoImage.getVisible()) {
                    i = ((this.photoImage.getImageX() + this.photoImage.getImageWidth()) - AndroidUtilities.dp(8.0f)) - this.photosCountWidth;
                    i2 = (this.photoImage.getImageY() + this.photoImage.getImageHeight()) - AndroidUtilities.dp(19.0f);
                    this.rect.set((float) (i - AndroidUtilities.dp(4.0f)), (float) (i2 - AndroidUtilities.dp(1.5f)), (float) ((this.photosCountWidth + i) + AndroidUtilities.dp(4.0f)), (float) (AndroidUtilities.dp(14.5f) + i2));
                    i3 = Theme.chat_timeBackgroundPaint.getAlpha();
                    Theme.chat_timeBackgroundPaint.setAlpha((int) (((float) i3) * this.controlsAlpha));
                    Theme.chat_durationPaint.setAlpha((int) (255.0f * this.controlsAlpha));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_timeBackgroundPaint);
                    Theme.chat_timeBackgroundPaint.setAlpha(i3);
                    canvas.save();
                    canvas.translate((float) i, (float) i2);
                    this.photosCountLayout.draw(canvas);
                    canvas.restore();
                    Theme.chat_durationPaint.setAlpha(255);
                }
                if (this.videoInfoLayout != null && (!this.drawPhotoImage || this.photoImage.getVisible())) {
                    if (!this.hasGamePreview && !this.hasInvoicePreview) {
                        i = ((this.photoImage.getImageX() + this.photoImage.getImageWidth()) - AndroidUtilities.dp(8.0f)) - this.durationWidth;
                        i4 = (this.photoImage.getImageY() + this.photoImage.getImageHeight()) - AndroidUtilities.dp(19.0f);
                        this.rect.set((float) (i - AndroidUtilities.dp(4.0f)), (float) (i4 - AndroidUtilities.dp(1.5f)), (float) ((this.durationWidth + i) + AndroidUtilities.dp(4.0f)), (float) (AndroidUtilities.dp(14.5f) + i4));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_timeBackgroundPaint);
                    } else if (this.drawPhotoImage) {
                        i = AndroidUtilities.dp(8.5f) + this.photoImage.getImageX();
                        i4 = this.photoImage.getImageY() + AndroidUtilities.dp(6.0f);
                        this.rect.set((float) (i - AndroidUtilities.dp(4.0f)), (float) (i4 - AndroidUtilities.dp(1.5f)), (float) ((this.durationWidth + i) + AndroidUtilities.dp(4.0f)), (float) (AndroidUtilities.dp(16.5f) + i4));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_timeBackgroundPaint);
                    } else {
                        i = dp;
                    }
                    canvas.save();
                    canvas.translate((float) i, (float) i4);
                    if (this.hasInvoicePreview) {
                        if (this.drawPhotoImage) {
                            Theme.chat_shipmentPaint.setColor(Theme.getColor(Theme.key_chat_previewGameText));
                        } else if (this.currentMessageObject.isOutOwner()) {
                            Theme.chat_shipmentPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                        } else {
                            Theme.chat_shipmentPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                        }
                    }
                    this.videoInfoLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.drawInstantView) {
                    dp2 = (this.linkPreviewHeight + dp2) + AndroidUtilities.dp(10.0f);
                    Paint paint = Theme.chat_instantViewRectPaint;
                    if (this.currentMessageObject.isOutOwner()) {
                        drawable = Theme.chat_msgOutInstantDrawable;
                        Theme.chat_instantViewPaint.setColor(Theme.getColor(Theme.key_chat_outPreviewInstantText));
                        paint.setColor(Theme.getColor(Theme.key_chat_outPreviewInstantText));
                    } else {
                        drawable = Theme.chat_msgInInstantDrawable;
                        Theme.chat_instantViewPaint.setColor(Theme.getColor(Theme.key_chat_inPreviewInstantText));
                        paint.setColor(Theme.getColor(Theme.key_chat_inPreviewInstantText));
                    }
                    if (VERSION.SDK_INT >= 21) {
                        this.instantViewSelectorDrawable.setBounds(dp, dp2, this.instantWidth + dp, AndroidUtilities.dp(36.0f) + dp2);
                        this.instantViewSelectorDrawable.draw(canvas);
                    }
                    this.rect.set((float) dp, (float) dp2, (float) (this.instantWidth + dp), (float) (AndroidUtilities.dp(36.0f) + dp2));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(6.0f), (float) AndroidUtilities.dp(6.0f), paint);
                    if (this.drawInstantViewType == 0) {
                        setDrawableBounds(drawable, (this.instantTextX + dp) - AndroidUtilities.dp(15.0f), dp2 + AndroidUtilities.dp(11.5f), AndroidUtilities.dp(9.0f), AndroidUtilities.dp(13.0f));
                        drawable.draw(canvas);
                    }
                    if (this.instantViewLayout != null) {
                        canvas.save();
                        canvas.translate((float) (this.instantTextX + dp), (float) (AndroidUtilities.dp(10.5f) + dp2));
                        this.instantViewLayout.draw(canvas);
                        canvas.restore();
                    }
                }
            }
            this.drawTime = true;
        } else if (this.drawPhotoImage) {
            if (this.currentMessageObject.isRoundVideo() && MediaController.getInstance().isPlayingMessage(this.currentMessageObject) && MediaController.getInstance().isRoundVideoDrawingReady()) {
                z2 = true;
                this.drawTime = true;
            } else {
                if (this.currentMessageObject.type == 5 && Theme.chat_roundVideoShadow != null) {
                    i4 = this.photoImage.getImageX() - AndroidUtilities.dp(3.0f);
                    i = this.photoImage.getImageY() - AndroidUtilities.dp(2.0f);
                    Theme.chat_roundVideoShadow.setAlpha((int) (this.photoImage.getCurrentAlpha() * 255.0f));
                    Theme.chat_roundVideoShadow.setBounds(i4, i, (AndroidUtilities.roundMessageSize + i4) + AndroidUtilities.dp(6.0f), (AndroidUtilities.roundMessageSize + i) + AndroidUtilities.dp(6.0f));
                    Theme.chat_roundVideoShadow.draw(canvas);
                }
                z2 = this.photoImage.draw(canvas);
                z = this.drawTime;
                this.drawTime = this.photoImage.getVisible();
                if (!(this.currentPosition == null || z == this.drawTime)) {
                    ViewGroup viewGroup = (ViewGroup) getParent();
                    if (viewGroup != null) {
                        if (this.currentPosition.last) {
                            viewGroup.invalidate();
                        } else {
                            dp3 = viewGroup.getChildCount();
                            for (i3 = 0; i3 < dp3; i3++) {
                                View childAt = viewGroup.getChildAt(i3);
                                if (childAt != this && (childAt instanceof ChatMessageCell)) {
                                    ChatMessageCell chatMessageCell = (ChatMessageCell) childAt;
                                    if (chatMessageCell.getCurrentMessagesGroup() == this.currentMessagesGroup) {
                                        GroupedMessagePosition currentPosition = chatMessageCell.getCurrentPosition();
                                        if (currentPosition.last && currentPosition.maxY == this.currentPosition.maxY && (chatMessageCell.timeX - AndroidUtilities.dp(4.0f)) + chatMessageCell.getLeft() < getRight()) {
                                            chatMessageCell.groupPhotoInvisible = !this.drawTime;
                                            chatMessageCell.invalidate();
                                            viewGroup.invalidate();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.buttonState == -1 && this.currentMessageObject.isSecretPhoto() && !MediaController.getInstance().isPlayingMessage(this.currentMessageObject) && this.photoImage.getVisible()) {
            i4 = 4;
            if (this.currentMessageObject.messageOwner.destroyTime != 0) {
                i4 = this.currentMessageObject.isOutOwner() ? 6 : 5;
            }
            setDrawableBounds(Theme.chat_photoStatesDrawables[i4][this.buttonPressed], this.buttonX, this.buttonY);
            Theme.chat_photoStatesDrawables[i4][this.buttonPressed].setAlpha((int) ((255.0f * (1.0f - this.radialProgress.getAlpha())) * this.controlsAlpha));
            Theme.chat_photoStatesDrawables[i4][this.buttonPressed].draw(canvas);
            if (this.currentMessageObject.messageOwner.destroyTime != 0) {
                if (!this.currentMessageObject.isOutOwner()) {
                    float max = ((float) Math.max(0, (((long) this.currentMessageObject.messageOwner.destroyTime) * 1000) - (System.currentTimeMillis() + ((long) (ConnectionsManager.getInstance().getTimeDifference() * 1000))))) / (((float) this.currentMessageObject.messageOwner.ttl) * 1000.0f);
                    Theme.chat_deleteProgressPaint.setAlpha((int) (255.0f * this.controlsAlpha));
                    canvas.drawArc(this.deleteProgressRect, -90.0f, -360.0f * max, true, Theme.chat_deleteProgressPaint);
                    if (max != BitmapDescriptorFactory.HUE_RED) {
                        i4 = AndroidUtilities.dp(2.0f);
                        invalidate(((int) this.deleteProgressRect.left) - i4, ((int) this.deleteProgressRect.top) - i4, ((int) this.deleteProgressRect.right) + (i4 * 2), (i4 * 2) + ((int) this.deleteProgressRect.bottom));
                    }
                }
                updateSecretTimeText(this.currentMessageObject);
            }
        }
        if (this.documentAttachType == 2 || this.currentMessageObject.type == 8) {
            if (!(!this.photoImage.getVisible() || this.hasGamePreview || this.currentMessageObject.isSecretPhoto())) {
                i4 = ((BitmapDrawable) Theme.chat_msgMediaMenuDrawable).getPaint().getAlpha();
                Theme.chat_msgMediaMenuDrawable.setAlpha((int) (((float) i4) * this.controlsAlpha));
                drawable = Theme.chat_msgMediaMenuDrawable;
                i2 = (this.photoImage.getImageX() + this.photoImage.getImageWidth()) - AndroidUtilities.dp(14.0f);
                this.otherX = i2;
                i3 = this.photoImage.getImageY() + AndroidUtilities.dp(8.1f);
                this.otherY = i3;
                setDrawableBounds(drawable, i2, i3);
                Theme.chat_msgMediaMenuDrawable.draw(canvas);
                Theme.chat_msgMediaMenuDrawable.setAlpha(i4);
            }
        } else if (this.documentAttachType == 7 || this.currentMessageObject.type == 5) {
            if (this.durationLayout != null) {
                boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (isPlayingMessage) {
                    this.rect.set(((float) this.photoImage.getImageX()) + AndroidUtilities.dpf2(1.5f), ((float) this.photoImage.getImageY()) + AndroidUtilities.dpf2(1.5f), ((float) this.photoImage.getImageX2()) - AndroidUtilities.dpf2(1.5f), ((float) this.photoImage.getImageY2()) - AndroidUtilities.dpf2(1.5f));
                    canvas.drawArc(this.rect, -90.0f, this.currentMessageObject.audioProgress * 360.0f, false, Theme.chat_radialProgressPaint);
                }
                if (this.documentAttachType == 7) {
                    i = this.backgroundDrawableLeft + AndroidUtilities.dp(this.currentMessageObject.isOutOwner() ? 12.0f : 18.0f);
                    i4 = (this.layoutHeight - AndroidUtilities.dp(6.3f - ((float) (this.drawPinnedBottom ? 2 : 0)))) - this.timeLayout.getHeight();
                } else {
                    i4 = this.backgroundDrawableLeft + AndroidUtilities.dp(8.0f);
                    i2 = this.layoutHeight - AndroidUtilities.dp(28.0f);
                    this.rect.set((float) i4, (float) i2, (float) ((this.timeWidthAudio + i4) + AndroidUtilities.dp(22.0f)), (float) (AndroidUtilities.dp(17.0f) + i2));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_actionBackgroundPaint);
                    if (isPlayingMessage || !this.currentMessageObject.isContentUnread()) {
                        if (!isPlayingMessage || MediaController.getInstance().isMessagePaused()) {
                            this.roundVideoPlayingDrawable.stop();
                        } else {
                            this.roundVideoPlayingDrawable.start();
                        }
                        setDrawableBounds(this.roundVideoPlayingDrawable, (this.timeWidthAudio + i4) + AndroidUtilities.dp(6.0f), AndroidUtilities.dp(2.3f) + i2);
                        this.roundVideoPlayingDrawable.draw(canvas);
                    } else {
                        Theme.chat_docBackPaint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
                        canvas.drawCircle((float) ((this.timeWidthAudio + i4) + AndroidUtilities.dp(12.0f)), (float) (AndroidUtilities.dp(8.3f) + i2), (float) AndroidUtilities.dp(3.0f), Theme.chat_docBackPaint);
                    }
                    i = AndroidUtilities.dp(4.0f) + i4;
                    i4 = AndroidUtilities.dp(1.7f) + i2;
                }
                canvas.save();
                canvas.translate((float) i, (float) i4);
                this.durationLayout.draw(canvas);
                canvas.restore();
            }
        } else if (this.documentAttachType == 5) {
            if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_audioTitlePaint.setColor(Theme.getColor(Theme.key_chat_outAudioTitleText));
                Theme.chat_audioPerformerPaint.setColor(Theme.getColor(Theme.key_chat_outAudioPerfomerText));
                Theme.chat_audioTimePaint.setColor(Theme.getColor(Theme.key_chat_outAudioDurationText));
                r1 = this.radialProgress;
                str = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_outAudioSelectedProgress : Theme.key_chat_outAudioProgress;
                r1.setProgressColor(Theme.getColor(str));
            } else {
                Theme.chat_audioTitlePaint.setColor(Theme.getColor(Theme.key_chat_inAudioTitleText));
                Theme.chat_audioPerformerPaint.setColor(Theme.getColor(Theme.key_chat_inAudioPerfomerText));
                Theme.chat_audioTimePaint.setColor(Theme.getColor(Theme.key_chat_inAudioDurationText));
                r1 = this.radialProgress;
                str = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress;
                r1.setProgressColor(Theme.getColor(str));
            }
            this.radialProgress.draw(canvas);
            canvas.save();
            canvas.translate((float) (this.timeAudioX + this.songX), (float) ((AndroidUtilities.dp(13.0f) + this.namesOffset) + this.mediaOffsetY));
            this.songLayout.draw(canvas);
            canvas.restore();
            canvas.save();
            if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                canvas.translate((float) this.seekBarX, (float) this.seekBarY);
                this.seekBar.draw(canvas);
            } else {
                canvas.translate((float) (this.timeAudioX + this.performerX), (float) ((AndroidUtilities.dp(35.0f) + this.namesOffset) + this.mediaOffsetY));
                this.performerLayout.draw(canvas);
            }
            canvas.restore();
            canvas.save();
            canvas.translate((float) this.timeAudioX, (float) ((AndroidUtilities.dp(57.0f) + this.namesOffset) + this.mediaOffsetY));
            this.durationLayout.draw(canvas);
            canvas.restore();
            Drawable drawable2 = this.currentMessageObject.isOutOwner() ? isDrawSelectedBackground() ? Theme.chat_msgOutMenuSelectedDrawable : Theme.chat_msgOutMenuDrawable : isDrawSelectedBackground() ? Theme.chat_msgInMenuSelectedDrawable : Theme.chat_msgInMenuDrawable;
            i = (this.backgroundWidth + this.buttonX) - AndroidUtilities.dp(this.currentMessageObject.type == 0 ? 58.0f : 48.0f);
            this.otherX = i;
            i2 = this.buttonY - AndroidUtilities.dp(5.0f);
            this.otherY = i2;
            setDrawableBounds(drawable2, i, i2);
            drawable2.draw(canvas);
        } else if (this.documentAttachType == 3) {
            if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_audioTimePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outAudioDurationSelectedText : Theme.key_chat_outAudioDurationText));
                r1 = this.radialProgress;
                str = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_outAudioSelectedProgress : Theme.key_chat_outAudioProgress;
                r1.setProgressColor(Theme.getColor(str));
            } else {
                Theme.chat_audioTimePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inAudioDurationSelectedText : Theme.key_chat_inAudioDurationText));
                r1 = this.radialProgress;
                str = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress;
                r1.setProgressColor(Theme.getColor(str));
            }
            this.radialProgress.draw(canvas);
            canvas.save();
            if (this.useSeekBarWaweform) {
                canvas.translate((float) (this.seekBarX + AndroidUtilities.dp(13.0f)), (float) this.seekBarY);
                this.seekBarWaveform.draw(canvas);
            } else {
                canvas.translate((float) this.seekBarX, (float) this.seekBarY);
                this.seekBar.draw(canvas);
            }
            canvas.restore();
            canvas.save();
            canvas.translate((float) this.timeAudioX, (float) ((AndroidUtilities.dp(44.0f) + this.namesOffset) + this.mediaOffsetY));
            this.durationLayout.draw(canvas);
            canvas.restore();
            if (this.currentMessageObject.type != 0 && this.currentMessageObject.isContentUnread()) {
                Theme.chat_docBackPaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outVoiceSeekbarFill : Theme.key_chat_inVoiceSeekbarFill));
                canvas.drawCircle((float) ((this.timeAudioX + this.timeWidthAudio) + AndroidUtilities.dp(6.0f)), (float) ((AndroidUtilities.dp(51.0f) + this.namesOffset) + this.mediaOffsetY), (float) AndroidUtilities.dp(3.0f), Theme.chat_docBackPaint);
            }
        }
        if (this.currentMessageObject.type == 1 || this.documentAttachType == 4) {
            if (this.photoImage.getVisible()) {
                if (!this.currentMessageObject.isSecretPhoto() && this.documentAttachType == 4) {
                    i4 = ((BitmapDrawable) Theme.chat_msgMediaMenuDrawable).getPaint().getAlpha();
                    Theme.chat_msgMediaMenuDrawable.setAlpha((int) (((float) i4) * this.controlsAlpha));
                    drawable = Theme.chat_msgMediaMenuDrawable;
                    i2 = (this.photoImage.getImageX() + this.photoImage.getImageWidth()) - AndroidUtilities.dp(14.0f);
                    this.otherX = i2;
                    i3 = this.photoImage.getImageY() + AndroidUtilities.dp(8.1f);
                    this.otherY = i3;
                    setDrawableBounds(drawable, i2, i3);
                    Theme.chat_msgMediaMenuDrawable.draw(canvas);
                    Theme.chat_msgMediaMenuDrawable.setAlpha(i4);
                }
                if (!(this.forceNotDrawTime || this.infoLayout == null || (this.buttonState != 1 && this.buttonState != 0 && this.buttonState != 3 && !this.currentMessageObject.isSecretPhoto()))) {
                    Theme.chat_infoPaint.setColor(Theme.getColor(Theme.key_chat_mediaInfoText));
                    i4 = this.photoImage.getImageX() + AndroidUtilities.dp(4.0f);
                    i = this.photoImage.getImageY() + AndroidUtilities.dp(4.0f);
                    this.rect.set((float) i4, (float) i, (float) ((i4 + this.infoWidth) + AndroidUtilities.dp(8.0f)), (float) (i + AndroidUtilities.dp(16.5f)));
                    i4 = Theme.chat_timeBackgroundPaint.getAlpha();
                    Theme.chat_timeBackgroundPaint.setAlpha((int) (((float) i4) * this.controlsAlpha));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), Theme.chat_timeBackgroundPaint);
                    Theme.chat_timeBackgroundPaint.setAlpha(i4);
                    canvas.save();
                    canvas.translate((float) (this.photoImage.getImageX() + AndroidUtilities.dp(8.0f)), (float) (this.photoImage.getImageY() + AndroidUtilities.dp(5.5f)));
                    Theme.chat_infoPaint.setAlpha((int) (255.0f * this.controlsAlpha));
                    this.infoLayout.draw(canvas);
                    canvas.restore();
                    Theme.chat_infoPaint.setAlpha(255);
                }
            }
        } else if (this.currentMessageObject.type == 4) {
            if (this.docTitleLayout != null) {
                if (this.currentMessageObject.isOutOwner()) {
                    if (this.currentMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                        Theme.chat_locationTitlePaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                    } else {
                        Theme.chat_locationTitlePaint.setColor(Theme.getColor(Theme.key_chat_outVenueNameText));
                    }
                    Theme.chat_locationAddressPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outVenueInfoSelectedText : Theme.key_chat_outVenueInfoText));
                } else {
                    if (this.currentMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                        Theme.chat_locationTitlePaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                    } else {
                        Theme.chat_locationTitlePaint.setColor(Theme.getColor(Theme.key_chat_inVenueNameText));
                    }
                    Theme.chat_locationAddressPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inVenueInfoSelectedText : Theme.key_chat_inVenueInfoText));
                }
                if (this.currentMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                    dp = this.photoImage.getImageY2() + AndroidUtilities.dp(30.0f);
                    if (!this.locationExpired) {
                        this.forceNotDrawTime = true;
                        f = 1.0f - (((float) Math.abs(ConnectionsManager.getInstance().getCurrentTime() - this.currentMessageObject.messageOwner.date)) / ((float) this.currentMessageObject.messageOwner.media.period));
                        this.rect.set((float) (this.photoImage.getImageX2() - AndroidUtilities.dp(43.0f)), (float) (dp - AndroidUtilities.dp(15.0f)), (float) (this.photoImage.getImageX2() - AndroidUtilities.dp(13.0f)), (float) (AndroidUtilities.dp(15.0f) + dp));
                        if (this.currentMessageObject.isOutOwner()) {
                            Theme.chat_radialProgress2Paint.setColor(Theme.getColor(Theme.key_chat_outInstant));
                            Theme.chat_livePaint.setColor(Theme.getColor(Theme.key_chat_outInstant));
                        } else {
                            Theme.chat_radialProgress2Paint.setColor(Theme.getColor(Theme.key_chat_inInstant));
                            Theme.chat_livePaint.setColor(Theme.getColor(Theme.key_chat_inInstant));
                        }
                        Theme.chat_radialProgress2Paint.setAlpha(50);
                        canvas.drawCircle(this.rect.centerX(), this.rect.centerY(), (float) AndroidUtilities.dp(15.0f), Theme.chat_radialProgress2Paint);
                        Theme.chat_radialProgress2Paint.setAlpha(255);
                        canvas.drawArc(this.rect, -90.0f, -360.0f * f, false, Theme.chat_radialProgress2Paint);
                        str = LocaleController.formatLocationLeftTime(Math.abs(this.currentMessageObject.messageOwner.media.period - (ConnectionsManager.getInstance().getCurrentTime() - this.currentMessageObject.messageOwner.date)));
                        canvas.drawText(str, this.rect.centerX() - (Theme.chat_livePaint.measureText(str) / 2.0f), (float) (AndroidUtilities.dp(4.0f) + dp), Theme.chat_livePaint);
                        canvas.save();
                        canvas.translate((float) (this.photoImage.getImageX() + AndroidUtilities.dp(10.0f)), (float) (this.photoImage.getImageY2() + AndroidUtilities.dp(10.0f)));
                        this.docTitleLayout.draw(canvas);
                        canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(23.0f));
                        this.infoLayout.draw(canvas);
                        canvas.restore();
                    }
                    i4 = (this.photoImage.getImageX() + (this.photoImage.getImageWidth() / 2)) - AndroidUtilities.dp(31.0f);
                    i = (this.photoImage.getImageY() + (this.photoImage.getImageHeight() / 2)) - AndroidUtilities.dp(38.0f);
                    setDrawableBounds(Theme.chat_msgAvatarLiveLocationDrawable, i4, i);
                    Theme.chat_msgAvatarLiveLocationDrawable.draw(canvas);
                    this.locationImageReceiver.setImageCoords(i4 + AndroidUtilities.dp(5.0f), i + AndroidUtilities.dp(5.0f), AndroidUtilities.dp(52.0f), AndroidUtilities.dp(52.0f));
                    this.locationImageReceiver.draw(canvas);
                } else {
                    canvas.save();
                    canvas.translate((float) (((this.docTitleOffsetX + this.photoImage.getImageX()) + this.photoImage.getImageWidth()) + AndroidUtilities.dp(10.0f)), (float) (this.photoImage.getImageY() + AndroidUtilities.dp(8.0f)));
                    this.docTitleLayout.draw(canvas);
                    canvas.restore();
                    if (this.infoLayout != null) {
                        canvas.save();
                        canvas.translate((float) ((this.photoImage.getImageX() + this.photoImage.getImageWidth()) + AndroidUtilities.dp(10.0f)), (float) ((this.photoImage.getImageY() + this.docTitleLayout.getLineBottom(this.docTitleLayout.getLineCount() - 1)) + AndroidUtilities.dp(13.0f)));
                        this.infoLayout.draw(canvas);
                        canvas.restore();
                    }
                }
            }
        } else if (this.currentMessageObject.type == 16) {
            Drawable drawable3;
            if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_audioTitlePaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                Theme.chat_contactPhonePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
            } else {
                Theme.chat_audioTitlePaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                Theme.chat_contactPhonePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
            }
            this.forceNotDrawTime = true;
            i4 = this.currentMessageObject.isOutOwner() ? (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(16.0f) : (this.isChat && this.currentMessageObject.needDrawAvatar()) ? AndroidUtilities.dp(74.0f) : AndroidUtilities.dp(25.0f);
            this.otherX = i4;
            if (this.titleLayout != null) {
                canvas.save();
                canvas.translate((float) i4, (float) (AndroidUtilities.dp(12.0f) + this.namesOffset));
                this.titleLayout.draw(canvas);
                canvas.restore();
            }
            if (this.docTitleLayout != null) {
                canvas.save();
                canvas.translate((float) (AndroidUtilities.dp(19.0f) + i4), (float) (AndroidUtilities.dp(37.0f) + this.namesOffset));
                this.docTitleLayout.draw(canvas);
                canvas.restore();
            }
            if (this.currentMessageObject.isOutOwner()) {
                drawable3 = Theme.chat_msgCallUpGreenDrawable;
                drawable = (isDrawSelectedBackground() || this.otherPressed) ? Theme.chat_msgOutCallSelectedDrawable : Theme.chat_msgOutCallDrawable;
            } else {
                PhoneCallDiscardReason phoneCallDiscardReason = this.currentMessageObject.messageOwner.action.reason;
                drawable = ((phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonMissed) || (phoneCallDiscardReason instanceof TLRPC$TL_phoneCallDiscardReasonBusy)) ? Theme.chat_msgCallDownRedDrawable : Theme.chat_msgCallDownGreenDrawable;
                drawable3 = (isDrawSelectedBackground() || this.otherPressed) ? Theme.chat_msgInCallSelectedDrawable : Theme.chat_msgInCallDrawable;
                Drawable drawable4 = drawable3;
                drawable3 = drawable;
                drawable = drawable4;
            }
            setDrawableBounds(drawable3, i4 - AndroidUtilities.dp(3.0f), AndroidUtilities.dp(36.0f) + this.namesOffset);
            drawable3.draw(canvas);
            i4 += AndroidUtilities.dp(205.0f);
            i2 = AndroidUtilities.dp(22.0f);
            this.otherY = i2;
            setDrawableBounds(drawable, i4, i2);
            drawable.draw(canvas);
        } else if (this.currentMessageObject.type == 12) {
            Theme.chat_contactNamePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outContactNameText : Theme.key_chat_inContactNameText));
            Theme.chat_contactPhonePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outContactPhoneText : Theme.key_chat_inContactPhoneText));
            if (this.titleLayout != null) {
                canvas.save();
                canvas.translate((float) ((this.photoImage.getImageX() + this.photoImage.getImageWidth()) + AndroidUtilities.dp(9.0f)), (float) (AndroidUtilities.dp(16.0f) + this.namesOffset));
                this.titleLayout.draw(canvas);
                canvas.restore();
            }
            if (this.docTitleLayout != null) {
                canvas.save();
                canvas.translate((float) ((this.photoImage.getImageX() + this.photoImage.getImageWidth()) + AndroidUtilities.dp(9.0f)), (float) (AndroidUtilities.dp(39.0f) + this.namesOffset));
                this.docTitleLayout.draw(canvas);
                canvas.restore();
            }
            drawable2 = this.currentMessageObject.isOutOwner() ? isDrawSelectedBackground() ? Theme.chat_msgOutMenuSelectedDrawable : Theme.chat_msgOutMenuDrawable : isDrawSelectedBackground() ? Theme.chat_msgInMenuSelectedDrawable : Theme.chat_msgInMenuDrawable;
            i = (this.photoImage.getImageX() + this.backgroundWidth) - AndroidUtilities.dp(48.0f);
            this.otherX = i;
            i2 = this.photoImage.getImageY() - AndroidUtilities.dp(5.0f);
            this.otherY = i2;
            setDrawableBounds(drawable2, i, i2);
            drawable2.draw(canvas);
        }
        if (this.currentPosition == null) {
            drawCaptionLayout(canvas);
        }
        if (this.hasOldCaptionPreview) {
            if (this.currentMessageObject.type == 1 || this.documentAttachType == 4 || this.currentMessageObject.type == 8) {
                dp = this.photoImage.getImageX() + AndroidUtilities.dp(5.0f);
            } else {
                dp = AndroidUtilities.dp(this.currentMessageObject.isOutOwner() ? 11.0f : 17.0f) + this.backgroundDrawableLeft;
            }
            dp2 = ((this.totalHeight - AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f)) - this.linkPreviewHeight) - AndroidUtilities.dp(8.0f);
            Theme.chat_replyLinePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outPreviewLine : Theme.key_chat_inPreviewLine));
            canvas.drawRect((float) dp, (float) (dp2 - AndroidUtilities.dp(3.0f)), (float) (AndroidUtilities.dp(2.0f) + dp), (float) (this.linkPreviewHeight + dp2), Theme.chat_replyLinePaint);
            if (this.siteNameLayout != null) {
                Theme.chat_replyNamePaint.setColor(Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outSiteNameText : Theme.key_chat_inSiteNameText));
                canvas.save();
                canvas.translate((float) ((this.hasInvoicePreview ? 0 : AndroidUtilities.dp(10.0f)) + dp), (float) (dp2 - AndroidUtilities.dp(3.0f)));
                this.siteNameLayout.draw(canvas);
                canvas.restore();
                i4 = this.siteNameLayout.getLineBottom(this.siteNameLayout.getLineCount() - 1) + dp2;
            } else {
                i4 = dp2;
            }
            if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
            } else {
                Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
            }
            if (this.descriptionLayout != null) {
                if (i4 != dp2) {
                    i4 += AndroidUtilities.dp(2.0f);
                }
                this.descriptionY = i4 - AndroidUtilities.dp(3.0f);
                canvas.save();
                canvas.translate((float) ((AndroidUtilities.dp(10.0f) + dp) + this.descriptionX), (float) this.descriptionY);
                this.descriptionLayout.draw(canvas);
                canvas.restore();
            }
            this.drawTime = true;
        }
        if (this.documentAttachType == 1) {
            if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_docNamePaint.setColor(Theme.getColor(Theme.key_chat_outFileNameText));
                Theme.chat_infoPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outFileInfoSelectedText : Theme.key_chat_outFileInfoText));
                Theme.chat_docBackPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outFileBackgroundSelected : Theme.key_chat_outFileBackground));
                drawable2 = isDrawSelectedBackground() ? Theme.chat_msgOutMenuSelectedDrawable : Theme.chat_msgOutMenuDrawable;
            } else {
                Theme.chat_docNamePaint.setColor(Theme.getColor(Theme.key_chat_inFileNameText));
                Theme.chat_infoPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inFileInfoSelectedText : Theme.key_chat_inFileInfoText));
                Theme.chat_docBackPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inFileBackgroundSelected : Theme.key_chat_inFileBackground));
                drawable2 = isDrawSelectedBackground() ? Theme.chat_msgInMenuSelectedDrawable : Theme.chat_msgInMenuDrawable;
            }
            RadialProgress radialProgress;
            if (this.drawPhotoImage) {
                if (this.currentMessageObject.type == 0) {
                    i = (this.photoImage.getImageX() + this.backgroundWidth) - AndroidUtilities.dp(56.0f);
                    this.otherX = i;
                    i2 = this.photoImage.getImageY() + AndroidUtilities.dp(1.0f);
                    this.otherY = i2;
                    setDrawableBounds(drawable2, i, i2);
                } else {
                    i = (this.photoImage.getImageX() + this.backgroundWidth) - AndroidUtilities.dp(40.0f);
                    this.otherX = i;
                    i2 = this.photoImage.getImageY() + AndroidUtilities.dp(1.0f);
                    this.otherY = i2;
                    setDrawableBounds(drawable2, i, i2);
                }
                dp3 = (this.photoImage.getImageX() + this.photoImage.getImageWidth()) + AndroidUtilities.dp(10.0f);
                i3 = this.photoImage.getImageY() + AndroidUtilities.dp(8.0f);
                i2 = AndroidUtilities.dp(13.0f) + (this.photoImage.getImageY() + this.docTitleLayout.getLineBottom(this.docTitleLayout.getLineCount() - 1));
                if (this.buttonState >= 0 && this.buttonState < 4) {
                    if (z2) {
                        this.radialProgress.swapBackground(Theme.chat_photoStatesDrawables[this.buttonState][this.buttonPressed]);
                    } else {
                        i = this.buttonState;
                        if (this.buttonState == 0) {
                            i = this.currentMessageObject.isOutOwner() ? 7 : 10;
                        } else if (this.buttonState == 1) {
                            i = this.currentMessageObject.isOutOwner() ? 8 : 11;
                        }
                        radialProgress = this.radialProgress;
                        Drawable[] drawableArr = Theme.chat_photoStatesDrawables[i];
                        i = (isDrawSelectedBackground() || this.buttonPressed != 0) ? 1 : 0;
                        radialProgress.swapBackground(drawableArr[i]);
                    }
                }
                if (z2) {
                    if (this.buttonState == -1) {
                        this.radialProgress.setHideCurrentDrawable(true);
                    }
                    this.radialProgress.setProgressColor(Theme.getColor(Theme.key_chat_mediaProgress));
                    i = i2;
                    i2 = i3;
                    i3 = dp3;
                } else {
                    this.rect.set((float) this.photoImage.getImageX(), (float) this.photoImage.getImageY(), (float) (this.photoImage.getImageX() + this.photoImage.getImageWidth()), (float) (this.photoImage.getImageY() + this.photoImage.getImageHeight()));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(3.0f), (float) AndroidUtilities.dp(3.0f), Theme.chat_docBackPaint);
                    if (this.currentMessageObject.isOutOwner()) {
                        this.radialProgress.setProgressColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outFileProgressSelected : Theme.key_chat_outFileProgress));
                        i = i2;
                        i2 = i3;
                        i3 = dp3;
                    } else {
                        this.radialProgress.setProgressColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inFileProgressSelected : Theme.key_chat_inFileProgress));
                        i = i2;
                        i2 = i3;
                        i3 = dp3;
                    }
                }
            } else {
                i = (this.backgroundWidth + this.buttonX) - AndroidUtilities.dp(this.currentMessageObject.type == 0 ? 58.0f : 48.0f);
                this.otherX = i;
                i2 = this.buttonY - AndroidUtilities.dp(5.0f);
                this.otherY = i2;
                setDrawableBounds(drawable2, i, i2);
                dp3 = this.buttonX + AndroidUtilities.dp(53.0f);
                i3 = this.buttonY + AndroidUtilities.dp(4.0f);
                i2 = AndroidUtilities.dp(27.0f) + this.buttonY;
                String str2;
                if (this.currentMessageObject.isOutOwner()) {
                    radialProgress = this.radialProgress;
                    str2 = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_outAudioSelectedProgress : Theme.key_chat_outAudioProgress;
                    radialProgress.setProgressColor(Theme.getColor(str2));
                    i = i2;
                    i2 = i3;
                    i3 = dp3;
                } else {
                    radialProgress = this.radialProgress;
                    str2 = (isDrawSelectedBackground() || this.buttonPressed != 0) ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress;
                    radialProgress.setProgressColor(Theme.getColor(str2));
                    i = i2;
                    i2 = i3;
                    i3 = dp3;
                }
            }
            drawable2.draw(canvas);
            try {
                if (this.docTitleLayout != null) {
                    canvas.save();
                    canvas.translate((float) (this.docTitleOffsetX + i3), (float) i2);
                    this.docTitleLayout.draw(canvas);
                    canvas.restore();
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            try {
                if (this.infoLayout != null) {
                    canvas.save();
                    canvas.translate((float) i3, (float) i);
                    this.infoLayout.draw(canvas);
                    canvas.restore();
                }
            } catch (Throwable e22) {
                FileLog.e(e22);
            }
        }
        if (this.drawImageButton && this.photoImage.getVisible()) {
            if (this.controlsAlpha != 1.0f) {
                this.radialProgress.setOverrideAlpha(this.controlsAlpha);
            }
            this.radialProgress.draw(canvas);
        }
        if (!this.botButtons.isEmpty()) {
            if (this.currentMessageObject.isOutOwner()) {
                dp2 = (getMeasuredWidth() - this.widthForButtons) - AndroidUtilities.dp(10.0f);
            } else {
                dp2 = AndroidUtilities.dp(this.mediaBackground ? 1.0f : 7.0f) + this.backgroundDrawableLeft;
            }
            int i6 = 0;
            while (i6 < this.botButtons.size()) {
                BotButton botButton = (BotButton) this.botButtons.get(i6);
                i = (botButton.f10172y + this.layoutHeight) - AndroidUtilities.dp(2.0f);
                Theme.chat_systemDrawable.setColorFilter(i6 == this.pressedBotButton ? Theme.colorPressedFilter : Theme.colorFilter);
                Theme.chat_systemDrawable.setBounds(botButton.f10171x + dp2, i, (botButton.f10171x + dp2) + botButton.width, botButton.height + i);
                Theme.chat_systemDrawable.draw(canvas);
                canvas.save();
                canvas.translate((float) ((botButton.f10171x + dp2) + AndroidUtilities.dp(5.0f)), (float) (((AndroidUtilities.dp(44.0f) - botButton.title.getLineBottom(botButton.title.getLineCount() - 1)) / 2) + i));
                botButton.title.draw(canvas);
                canvas.restore();
                if (botButton.button instanceof TLRPC$TL_keyboardButtonUrl) {
                    setDrawableBounds(Theme.chat_botLinkDrawalbe, (((botButton.f10171x + botButton.width) - AndroidUtilities.dp(3.0f)) - Theme.chat_botLinkDrawalbe.getIntrinsicWidth()) + dp2, i + AndroidUtilities.dp(3.0f));
                    Theme.chat_botLinkDrawalbe.draw(canvas);
                } else if (botButton.button instanceof TLRPC$TL_keyboardButtonSwitchInline) {
                    setDrawableBounds(Theme.chat_botInlineDrawable, (((botButton.f10171x + botButton.width) - AndroidUtilities.dp(3.0f)) - Theme.chat_botInlineDrawable.getIntrinsicWidth()) + dp2, i + AndroidUtilities.dp(3.0f));
                    Theme.chat_botInlineDrawable.draw(canvas);
                } else if ((botButton.button instanceof TLRPC$TL_keyboardButtonCallback) || (botButton.button instanceof TLRPC$TL_keyboardButtonRequestGeoLocation) || (botButton.button instanceof TLRPC$TL_keyboardButtonGame) || (botButton.button instanceof TLRPC$TL_keyboardButtonBuy)) {
                    Object obj = ((((botButton.button instanceof TLRPC$TL_keyboardButtonCallback) || (botButton.button instanceof TLRPC$TL_keyboardButtonGame) || (botButton.button instanceof TLRPC$TL_keyboardButtonBuy)) && SendMessagesHelper.getInstance().isSendingCallback(this.currentMessageObject, botButton.button)) || ((botButton.button instanceof TLRPC$TL_keyboardButtonRequestGeoLocation) && SendMessagesHelper.getInstance().isSendingCurrentLocation(this.currentMessageObject, botButton.button))) ? 1 : null;
                    if (obj != null || (obj == null && botButton.progressAlpha != BitmapDescriptorFactory.HUE_RED)) {
                        Theme.chat_botProgressPaint.setAlpha(Math.min(255, (int) (botButton.progressAlpha * 255.0f)));
                        i4 = ((botButton.f10171x + botButton.width) - AndroidUtilities.dp(12.0f)) + dp2;
                        this.rect.set((float) i4, (float) (AndroidUtilities.dp(4.0f) + i), (float) (i4 + AndroidUtilities.dp(8.0f)), (float) (i + AndroidUtilities.dp(12.0f)));
                        canvas.drawArc(this.rect, (float) botButton.angle, 220.0f, false, Theme.chat_botProgressPaint);
                        invalidate(((int) this.rect.left) - AndroidUtilities.dp(2.0f), ((int) this.rect.top) - AndroidUtilities.dp(2.0f), ((int) this.rect.right) + AndroidUtilities.dp(2.0f), ((int) this.rect.bottom) + AndroidUtilities.dp(2.0f));
                        long currentTimeMillis = System.currentTimeMillis();
                        if (Math.abs(botButton.lastUpdateTime - System.currentTimeMillis()) < 1000) {
                            long access$1400 = currentTimeMillis - botButton.lastUpdateTime;
                            botButton.angle = (int) ((((float) (360 * access$1400)) / 2000.0f) + ((float) botButton.angle));
                            botButton.angle = botButton.angle - ((botButton.angle / 360) * 360);
                            if (obj != null) {
                                if (botButton.progressAlpha < 1.0f) {
                                    botButton.progressAlpha = (((float) access$1400) / 200.0f) + botButton.progressAlpha;
                                    if (botButton.progressAlpha > 1.0f) {
                                        botButton.progressAlpha = 1.0f;
                                    }
                                }
                            } else if (botButton.progressAlpha > BitmapDescriptorFactory.HUE_RED) {
                                botButton.progressAlpha = botButton.progressAlpha - (((float) access$1400) / 200.0f);
                                if (botButton.progressAlpha < BitmapDescriptorFactory.HUE_RED) {
                                    botButton.progressAlpha = BitmapDescriptorFactory.HUE_RED;
                                }
                            }
                        }
                        botButton.lastUpdateTime = currentTimeMillis;
                    }
                }
                i6++;
            }
        }
    }

    public static StaticLayout generateStaticLayout(CharSequence charSequence, TextPaint textPaint, int i, int i2, int i3, int i4) {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(charSequence);
        StaticLayout staticLayout = new StaticLayout(charSequence, textPaint, i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        int i5 = 0;
        int i6 = 0;
        int i7 = i;
        while (i5 < i3) {
            staticLayout.getLineDirections(i5);
            if (staticLayout.getLineLeft(i5) != BitmapDescriptorFactory.HUE_RED || staticLayout.isRtlCharAt(staticLayout.getLineStart(i5)) || staticLayout.isRtlCharAt(staticLayout.getLineEnd(i5))) {
                i7 = i2;
            }
            int lineEnd = staticLayout.getLineEnd(i5);
            if (lineEnd != charSequence.length()) {
                lineEnd--;
                if (spannableStringBuilder.charAt(lineEnd + i6) == ' ') {
                    spannableStringBuilder.replace(lineEnd + i6, (lineEnd + i6) + 1, "\n");
                } else if (spannableStringBuilder.charAt(lineEnd + i6) != '\n') {
                    spannableStringBuilder.insert(lineEnd + i6, "\n");
                    i6++;
                }
                if (i5 == staticLayout.getLineCount() - 1) {
                    break;
                } else if (i5 == i4 - 1) {
                    i6 = i7;
                    break;
                } else {
                    i5++;
                }
            } else {
                i6 = i7;
                break;
            }
        }
        i6 = i7;
        return StaticLayoutEx.createStaticLayout(spannableStringBuilder, textPaint, i6, Alignment.ALIGN_NORMAL, 1.0f, (float) AndroidUtilities.dp(1.0f), false, TruncateAt.END, i6, i4);
    }

    private Drawable getDrawableForCurrentState() {
        int i = 3;
        int i2 = 0;
        int i3 = 1;
        if (this.documentAttachType != 3 && this.documentAttachType != 5) {
            Drawable[] drawableArr;
            if (this.documentAttachType != 1 || this.drawPhotoImage) {
                this.radialProgress.setAlphaForPrevious(true);
                if (this.buttonState < 0 || this.buttonState >= 4) {
                    if (this.buttonState == -1 && this.documentAttachType == 1) {
                        drawableArr = Theme.chat_photoStatesDrawables[this.currentMessageObject.isOutOwner() ? 9 : 12];
                        if (!isDrawSelectedBackground()) {
                            i3 = 0;
                        }
                        return drawableArr[i3];
                    }
                } else if (this.documentAttachType != 1) {
                    return Theme.chat_photoStatesDrawables[this.buttonState][this.buttonPressed];
                } else {
                    i = this.buttonState;
                    if (this.buttonState == 0) {
                        i = this.currentMessageObject.isOutOwner() ? 7 : 10;
                    } else if (this.buttonState == 1) {
                        i = this.currentMessageObject.isOutOwner() ? 8 : 11;
                    }
                    drawableArr = Theme.chat_photoStatesDrawables[i];
                    if (isDrawSelectedBackground() || this.buttonPressed != 0) {
                        i2 = 1;
                    }
                    return drawableArr[i2];
                }
            }
            this.radialProgress.setAlphaForPrevious(false);
            if (this.buttonState == -1) {
                Drawable[][] drawableArr2 = Theme.chat_fileStatesDrawable;
                if (!this.currentMessageObject.isOutOwner()) {
                    i = 8;
                }
                drawableArr = drawableArr2[i];
                if (!isDrawSelectedBackground()) {
                    i3 = 0;
                }
                return drawableArr[i3];
            } else if (this.buttonState == 0) {
                drawableArr = Theme.chat_fileStatesDrawable[this.currentMessageObject.isOutOwner() ? 2 : 7];
                if (!isDrawSelectedBackground()) {
                    i3 = 0;
                }
                return drawableArr[i3];
            } else if (this.buttonState == 1) {
                drawableArr = Theme.chat_fileStatesDrawable[this.currentMessageObject.isOutOwner() ? 4 : 9];
                if (!isDrawSelectedBackground()) {
                    i3 = 0;
                }
                return drawableArr[i3];
            }
            return null;
        } else if (this.buttonState == -1) {
            return null;
        } else {
            this.radialProgress.setAlphaForPrevious(false);
            Drawable[] drawableArr3 = Theme.chat_fileStatesDrawable[this.currentMessageObject.isOutOwner() ? this.buttonState : this.buttonState + 5];
            i = (isDrawSelectedBackground() || this.buttonPressed != 0) ? 1 : 0;
            return drawableArr3[i];
        }
    }

    private int getGroupPhotosWidth() {
        if (AndroidUtilities.isInMultiwindow || !AndroidUtilities.isTablet() || (AndroidUtilities.isSmallTablet() && getResources().getConfiguration().orientation != 2)) {
            return AndroidUtilities.displaySize.x;
        }
        int i = (AndroidUtilities.displaySize.x / 100) * 35;
        if (i < AndroidUtilities.dp(320.0f)) {
            i = AndroidUtilities.dp(320.0f);
        }
        return AndroidUtilities.displaySize.x - i;
    }

    private int getMaxNameWidth() {
        int i = 0;
        if (this.documentAttachType == 6 || this.currentMessageObject.type == 5) {
            int minTabletSide = AndroidUtilities.isTablet() ? (this.isChat && !this.currentMessageObject.isOutOwner() && this.currentMessageObject.needDrawAvatar()) ? AndroidUtilities.getMinTabletSide() - AndroidUtilities.dp(42.0f) : AndroidUtilities.getMinTabletSide() : (this.isChat && !this.currentMessageObject.isOutOwner() && this.currentMessageObject.needDrawAvatar()) ? Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) - AndroidUtilities.dp(42.0f) : Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y);
            return (minTabletSide - this.backgroundWidth) - AndroidUtilities.dp(57.0f);
        } else if (this.currentMessagesGroup != null) {
            int minTabletSide2 = AndroidUtilities.isTablet() ? AndroidUtilities.getMinTabletSide() : AndroidUtilities.displaySize.x;
            int i2 = 0;
            for (int i3 = 0; i3 < this.currentMessagesGroup.posArray.size(); i3++) {
                GroupedMessagePosition groupedMessagePosition = (GroupedMessagePosition) this.currentMessagesGroup.posArray.get(i3);
                if (groupedMessagePosition.minY != (byte) 0) {
                    break;
                }
                i2 = (int) (((double) i2) + Math.ceil((double) ((((float) (groupedMessagePosition.leftSpanOffset + groupedMessagePosition.pw)) / 1000.0f) * ((float) minTabletSide2))));
            }
            if (this.isAvatarVisible) {
                i = 48;
            }
            return i2 - AndroidUtilities.dp((float) (i + 31));
        } else {
            return this.backgroundWidth - AndroidUtilities.dp(this.mediaBackground ? 22.0f : 31.0f);
        }
    }

    static Bitmap getNonFreeBitmap(RectF rectF) {
        if (nonFreeBitmap == null) {
            nonFreeBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(ApplicationLoader.applicationContext.getResources(), R.drawable.gift_grey), (int) (rectF.right - rectF.left), (int) (rectF.bottom - rectF.top), false);
        }
        return nonFreeBitmap;
    }

    private boolean intersect(float f, float f2, float f3, float f4) {
        return f <= f3 ? f2 >= f3 : f <= f4;
    }

    private boolean isCurrentLocationTimeExpired(MessageObject messageObject) {
        return this.currentMessageObject.messageOwner.media.period % 60 == 0 ? Math.abs(ConnectionsManager.getInstance().getCurrentTime() - messageObject.messageOwner.date) > messageObject.messageOwner.media.period : Math.abs(ConnectionsManager.getInstance().getCurrentTime() - messageObject.messageOwner.date) > messageObject.messageOwner.media.period + -5;
    }

    private boolean isDrawSelectedBackground() {
        return (isPressed() && this.isCheckPressed) || ((!this.isCheckPressed && this.isPressed) || this.isHighlighted);
    }

    private boolean isOpenChatByShare(MessageObject messageObject) {
        return (messageObject.messageOwner.fwd_from == null || messageObject.messageOwner.fwd_from.saved_from_peer == null) ? false : true;
    }

    private boolean isPhotoDataChanged(MessageObject messageObject) {
        if (messageObject.type == 0 || messageObject.type == 14) {
            return false;
        }
        if (messageObject.type == 4) {
            if (this.currentUrl == null) {
                return true;
            }
            String format;
            double d = messageObject.messageOwner.media.geo.lat;
            double d2 = messageObject.messageOwner.media.geo._long;
            if (messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
                int dp = this.backgroundWidth - AndroidUtilities.dp(21.0f);
                int dp2 = AndroidUtilities.dp(195.0f);
                double d3 = ((double) 268435456) / 3.141592653589793d;
                d = ((1.5707963267948966d - (Math.atan(Math.exp((((double) (Math.round(((double) 268435456) - ((Math.log((1.0d + Math.sin((3.141592653589793d * d) / 180.0d)) / (1.0d - Math.sin((d * 3.141592653589793d) / 180.0d))) * d3) / 2.0d)) - ((long) (AndroidUtilities.dp(10.3f) << 6)))) - ((double) 268435456)) / d3)) * 2.0d)) * 180.0d) / 3.141592653589793d;
                format = String.format(Locale.US, "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=%dx%d&maptype=roadmap&scale=%d&sensor=false", new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf((int) (((float) dp) / AndroidUtilities.density)), Integer.valueOf((int) (((float) dp2) / AndroidUtilities.density)), Integer.valueOf(Math.min(2, (int) Math.ceil((double) AndroidUtilities.density)))});
            } else if (TextUtils.isEmpty(messageObject.messageOwner.media.title)) {
                format = String.format(Locale.US, "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=200x100&maptype=roadmap&scale=%d&markers=color:red|size:mid|%f,%f&sensor=false", new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf(Math.min(2, (int) Math.ceil((double) AndroidUtilities.density))), Double.valueOf(d), Double.valueOf(d2)});
            } else {
                format = String.format(Locale.US, "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=72x72&maptype=roadmap&scale=%d&markers=color:red|size:mid|%f,%f&sensor=false", new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf(Math.min(2, (int) Math.ceil((double) AndroidUtilities.density))), Double.valueOf(d), Double.valueOf(d2)});
            }
            if (!format.equals(this.currentUrl)) {
                return true;
            }
        } else if (this.currentPhotoObject == null || (this.currentPhotoObject.location instanceof TLRPC$TL_fileLocationUnavailable)) {
            return true;
        } else {
            if (this.currentMessageObject != null && this.photoNotSet && FileLoader.getPathToMessage(this.currentMessageObject.messageOwner).exists()) {
                return true;
            }
        }
        return false;
    }

    private boolean isUserDataChanged() {
        Object obj = null;
        if (this.currentMessageObject != null && !this.hasLinkPreview && this.currentMessageObject.messageOwner.media != null && (this.currentMessageObject.messageOwner.media.webpage instanceof TLRPC$TL_webPage)) {
            return true;
        }
        if (this.currentMessageObject == null || (this.currentUser == null && this.currentChat == null)) {
            return false;
        }
        if (this.lastSendState != this.currentMessageObject.messageOwner.send_state || this.lastDeleteDate != this.currentMessageObject.messageOwner.destroyTime || this.lastViewsCount != this.currentMessageObject.messageOwner.views) {
            return true;
        }
        FileLocation fileLocation;
        PhotoSize closestPhotoSizeWithSize;
        String forwardedName;
        boolean z;
        updateCurrentUserAndChat();
        if (this.isAvatarVisible) {
            if (this.currentUser != null && this.currentUser.photo != null) {
                fileLocation = this.currentUser.photo.photo_small;
                if (this.replyTextLayout != null) {
                }
                if (this.currentPhoto != null) {
                }
                if (this.currentPhoto == null) {
                }
                if (this.currentPhoto == null) {
                }
                if (this.replyNameLayout != null) {
                    closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.currentMessageObject.replyMessageObject.photoThumbs, 80);
                    fileLocation = closestPhotoSizeWithSize.location;
                    if (this.currentReplyPhoto != null) {
                    }
                    if (this.currentUser != null) {
                        obj = UserObject.getUserName(this.currentUser);
                    } else if (this.currentChat != null) {
                        obj = this.currentChat.title;
                    }
                    if (this.currentNameString != null) {
                    }
                    if (this.currentNameString == null) {
                    }
                    if (this.currentNameString == null) {
                    }
                    if (this.drawForwardedName) {
                        return false;
                    }
                    forwardedName = this.currentMessageObject.getForwardedName();
                    if (this.currentForwardNameString == null) {
                    }
                    return z;
                }
                fileLocation = null;
                if (this.currentReplyPhoto != null) {
                }
                if (this.currentUser != null) {
                    obj = UserObject.getUserName(this.currentUser);
                } else if (this.currentChat != null) {
                    obj = this.currentChat.title;
                }
                if (this.currentNameString != null) {
                }
                if (this.currentNameString == null) {
                }
                if (this.currentNameString == null) {
                }
                if (this.drawForwardedName) {
                    return false;
                }
                forwardedName = this.currentMessageObject.getForwardedName();
                if (this.currentForwardNameString == null) {
                }
                return z;
            } else if (!(this.currentChat == null || this.currentChat.photo == null)) {
                fileLocation = this.currentChat.photo.photo_small;
                if (this.replyTextLayout != null && this.currentMessageObject.replyMessageObject != null) {
                    return true;
                }
                if (this.currentPhoto != null && r0 != null) {
                    return true;
                }
                if (this.currentPhoto == null && r0 == null) {
                    return true;
                }
                if (this.currentPhoto == null && r0 != null && (this.currentPhoto.local_id != r0.local_id || this.currentPhoto.volume_id != r0.volume_id)) {
                    return true;
                }
                if (this.replyNameLayout != null) {
                    closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.currentMessageObject.replyMessageObject.photoThumbs, 80);
                    if (!(closestPhotoSizeWithSize == null || this.currentMessageObject.replyMessageObject.type == 13)) {
                        fileLocation = closestPhotoSizeWithSize.location;
                        if (this.currentReplyPhoto != null && r0 != null) {
                            return true;
                        }
                        if (this.drawName && this.isChat && !this.currentMessageObject.isOutOwner()) {
                            if (this.currentUser != null) {
                                obj = UserObject.getUserName(this.currentUser);
                            } else if (this.currentChat != null) {
                                obj = this.currentChat.title;
                            }
                        }
                        if (this.currentNameString != null && r1 != null) {
                            return true;
                        }
                        if (this.currentNameString == null && r1 == null) {
                            return true;
                        }
                        if (this.currentNameString == null && r1 != null && !this.currentNameString.equals(r1)) {
                            return true;
                        }
                        if (this.drawForwardedName) {
                            return false;
                        }
                        forwardedName = this.currentMessageObject.getForwardedName();
                        z = !(this.currentForwardNameString == null || forwardedName == null) || ((this.currentForwardNameString != null && forwardedName == null) || !(this.currentForwardNameString == null || forwardedName == null || this.currentForwardNameString.equals(forwardedName)));
                        return z;
                    }
                }
                fileLocation = null;
                if (this.currentReplyPhoto != null) {
                }
                if (this.currentUser != null) {
                    obj = UserObject.getUserName(this.currentUser);
                } else if (this.currentChat != null) {
                    obj = this.currentChat.title;
                }
                if (this.currentNameString != null) {
                }
                if (this.currentNameString == null) {
                }
                if (this.currentNameString == null) {
                }
                if (this.drawForwardedName) {
                    return false;
                }
                forwardedName = this.currentMessageObject.getForwardedName();
                if (this.currentForwardNameString == null) {
                }
                return z;
            }
        }
        fileLocation = null;
        if (this.replyTextLayout != null) {
        }
        if (this.currentPhoto != null) {
        }
        if (this.currentPhoto == null) {
        }
        if (this.currentPhoto == null) {
        }
        if (this.replyNameLayout != null) {
            closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.currentMessageObject.replyMessageObject.photoThumbs, 80);
            fileLocation = closestPhotoSizeWithSize.location;
            if (this.currentReplyPhoto != null) {
            }
            if (this.currentUser != null) {
                obj = UserObject.getUserName(this.currentUser);
            } else if (this.currentChat != null) {
                obj = this.currentChat.title;
            }
            if (this.currentNameString != null) {
            }
            if (this.currentNameString == null) {
            }
            if (this.currentNameString == null) {
            }
            if (this.drawForwardedName) {
                return false;
            }
            forwardedName = this.currentMessageObject.getForwardedName();
            if (this.currentForwardNameString == null) {
            }
            return z;
        }
        fileLocation = null;
        if (this.currentReplyPhoto != null) {
        }
        if (this.currentUser != null) {
            obj = UserObject.getUserName(this.currentUser);
        } else if (this.currentChat != null) {
            obj = this.currentChat.title;
        }
        if (this.currentNameString != null) {
        }
        if (this.currentNameString == null) {
        }
        if (this.currentNameString == null) {
        }
        if (this.drawForwardedName) {
            return false;
        }
        forwardedName = this.currentMessageObject.getForwardedName();
        if (this.currentForwardNameString == null) {
        }
        return z;
    }

    private void measureTime(MessageObject messageObject) {
        Object replace;
        String format;
        if (messageObject.messageOwner.post_author != null) {
            replace = messageObject.messageOwner.post_author.replace("\n", TtmlNode.ANONYMOUS_REGION_ID);
        } else if (messageObject.messageOwner.fwd_from != null && messageObject.messageOwner.fwd_from.post_author != null) {
            replace = messageObject.messageOwner.fwd_from.post_author.replace("\n", TtmlNode.ANONYMOUS_REGION_ID);
        } else if (messageObject.isOutOwner() || messageObject.messageOwner.from_id <= 0 || !messageObject.messageOwner.post) {
            replace = null;
        } else {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id));
            replace = user != null ? ContactsController.formatName(user.first_name, user.last_name).replace('\n', ' ') : null;
        }
        User user2 = this.currentMessageObject.isFromUser() ? MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.from_id)) : null;
        if (messageObject.isLiveLocation() || messageObject.messageOwner.via_bot_id != 0 || messageObject.messageOwner.via_bot_name != null || ((user2 != null && user2.bot) || (messageObject.messageOwner.flags & TLRPC.MESSAGE_FLAG_EDITED) == 0 || this.currentPosition != null)) {
            format = LocaleController.getInstance().formatterDay.format(((long) messageObject.messageOwner.date) * 1000);
        } else {
            format = (this.showEditedMark ? LocaleController.getString("EditedMessage", R.string.EditedMessage) : " ") + " " + LocaleController.getInstance().formatterDay.format(((long) messageObject.messageOwner.date) * 1000);
        }
        if (replace != null) {
            this.currentTimeString = ", " + format;
        } else {
            this.currentTimeString = format;
        }
        if (this.showEditedMark && messageObject.messageOwner.edit_date > 0) {
            this.currentTimeString += "     ";
        }
        int ceil = (int) Math.ceil((double) Theme.chat_timePaint.measureText(this.currentTimeString));
        this.timeWidth = ceil;
        this.timeTextWidth = ceil;
        if ((messageObject.messageOwner.flags & 1024) != 0) {
            this.currentViewsString = String.format("%s", new Object[]{LocaleController.formatShortNumber(Math.max(1, messageObject.messageOwner.views), null)});
            this.viewsTextWidth = (int) Math.ceil((double) Theme.chat_timePaint.measureText(this.currentViewsString));
            this.timeWidth += (this.viewsTextWidth + Theme.chat_msgInViewsDrawable.getIntrinsicWidth()) + AndroidUtilities.dp(10.0f);
        }
        if (replace != null) {
            if (this.availableTimeWidth == 0) {
                this.availableTimeWidth = AndroidUtilities.dp(1000.0f);
            }
            int i = this.availableTimeWidth - this.timeWidth;
            if (messageObject.isOutOwner()) {
                i = messageObject.type == 5 ? i - AndroidUtilities.dp(20.0f) : i - AndroidUtilities.dp(96.0f);
            }
            ceil = (int) Math.ceil((double) Theme.chat_timePaint.measureText(replace, 0, replace.length()));
            if (ceil <= i) {
                i = ceil;
            } else if (i <= 0) {
                replace = TtmlNode.ANONYMOUS_REGION_ID;
                i = 0;
            } else {
                replace = TextUtils.ellipsize(replace, Theme.chat_timePaint, (float) i, TruncateAt.END);
            }
            this.currentTimeString = replace + this.currentTimeString;
            this.timeTextWidth += i;
            this.timeWidth += i;
        }
    }

    private LinkPath obtainNewUrlPath(boolean z) {
        LinkPath linkPath;
        if (this.urlPathCache.isEmpty()) {
            linkPath = new LinkPath();
        } else {
            linkPath = (LinkPath) this.urlPathCache.get(0);
            this.urlPathCache.remove(0);
        }
        if (z) {
            this.urlPathSelection.add(linkPath);
        } else {
            this.urlPath.add(linkPath);
        }
        return linkPath;
    }

    private void resetPressedLink(int i) {
        if (this.pressedLink == null) {
            return;
        }
        if (this.pressedLinkType == i || i == -1) {
            resetUrlPaths(false);
            this.pressedLink = null;
            this.pressedLinkType = -1;
            invalidate();
        }
    }

    private void resetUrlPaths(boolean z) {
        if (z) {
            if (!this.urlPathSelection.isEmpty()) {
                this.urlPathCache.addAll(this.urlPathSelection);
                this.urlPathSelection.clear();
            }
        } else if (!this.urlPath.isEmpty()) {
            this.urlPathCache.addAll(this.urlPath);
            this.urlPath.clear();
        }
    }

    private void setMessageObjectInternal(MessageObject messageObject) {
        CharSequence charSequence;
        String str;
        CharSequence ellipsize;
        int color;
        CharSequence ellipsize2;
        if (!((messageObject.messageOwner.flags & 1024) == 0 || this.currentMessageObject.viewsReloaded)) {
            MessagesController.getInstance().addToViewsQueue(this.currentMessageObject.messageOwner);
            this.currentMessageObject.viewsReloaded = true;
        }
        updateCurrentUserAndChat();
        if (this.isAvatarVisible) {
            if (this.currentUser != null) {
                if (this.currentUser.photo != null) {
                    this.currentPhoto = this.currentUser.photo.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                this.avatarDrawable.setInfo(this.currentUser);
            } else if (this.currentChat != null) {
                if (this.currentChat.photo != null) {
                    this.currentPhoto = this.currentChat.photo.photo_small;
                } else {
                    this.currentPhoto = null;
                }
                this.avatarDrawable.setInfo(this.currentChat);
            } else {
                this.currentPhoto = null;
                this.avatarDrawable.setInfo(messageObject.messageOwner.from_id, null, null, false);
            }
            this.avatarImage.setImage(this.currentPhoto, "50_50", this.avatarDrawable, null, 0);
        }
        measureTime(messageObject);
        this.namesOffset = 0;
        String str2 = null;
        CharSequence charSequence2 = null;
        if (messageObject.messageOwner.via_bot_id != 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.via_bot_id));
            if (!(user == null || user.username == null || user.username.length() <= 0)) {
                str2 = "@" + user.username;
                charSequence2 = AndroidUtilities.replaceTags(String.format(" via <b>%s</b>", new Object[]{str2}));
                this.viaWidth = (int) Math.ceil((double) Theme.chat_replyNamePaint.measureText(charSequence2, 0, charSequence2.length()));
                this.currentViaBotUser = user;
            }
            charSequence = charSequence2;
            str = str2;
        } else if (messageObject.messageOwner.via_bot_name == null || messageObject.messageOwner.via_bot_name.length() <= 0) {
            charSequence = null;
            str = null;
        } else {
            charSequence2 = AndroidUtilities.replaceTags(String.format(" via <b>%s</b>", new Object[]{"@" + messageObject.messageOwner.via_bot_name}));
            this.viaWidth = (int) Math.ceil((double) Theme.chat_replyNamePaint.measureText(charSequence2, 0, charSequence2.length()));
            charSequence = charSequence2;
            str = str2;
        }
        Object obj = (this.drawName && this.isChat && !this.currentMessageObject.isOutOwner()) ? 1 : null;
        Object obj2 = ((messageObject.messageOwner.fwd_from == null || messageObject.type == 14) && str != null) ? 1 : null;
        if (obj == null && obj2 == null) {
            this.currentNameString = null;
            this.nameLayout = null;
            this.nameWidth = 0;
        } else {
            String str3;
            int i;
            this.drawNameLayout = true;
            this.nameWidth = getMaxNameWidth();
            if (this.nameWidth < 0) {
                this.nameWidth = AndroidUtilities.dp(100.0f);
            }
            if (this.currentUser == null || this.currentMessageObject.isOutOwner() || this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5 || !this.delegate.isChatAdminCell(this.currentUser.id)) {
                str3 = null;
                i = 0;
            } else {
                str2 = LocaleController.getString("ChatAdmin", R.string.ChatAdmin);
                int ceil = (int) Math.ceil((double) Theme.chat_adminPaint.measureText(str2));
                this.nameWidth -= ceil;
                str3 = str2;
                i = ceil;
            }
            if (obj == null) {
                this.currentNameString = TtmlNode.ANONYMOUS_REGION_ID;
            } else if (this.currentUser != null) {
                this.currentNameString = UserObject.getUserName(this.currentUser);
            } else if (this.currentChat != null) {
                this.currentNameString = this.currentChat.title;
            } else {
                this.currentNameString = "DELETED";
            }
            ellipsize = TextUtils.ellipsize(this.currentNameString.replace('\n', ' '), Theme.chat_namePaint, (float) (this.nameWidth - (obj2 != null ? this.viaWidth : 0)), TruncateAt.END);
            if (obj2 != null) {
                this.viaNameWidth = (int) Math.ceil((double) Theme.chat_namePaint.measureText(ellipsize, 0, ellipsize.length()));
                if (this.viaNameWidth != 0) {
                    this.viaNameWidth += AndroidUtilities.dp(4.0f);
                }
                if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                    color = Theme.getColor(Theme.key_chat_stickerViaBotNameText);
                } else {
                    color = Theme.getColor(this.currentMessageObject.isOutOwner() ? Theme.key_chat_outViaBotNameText : Theme.key_chat_inViaBotNameText);
                }
                SpannableStringBuilder spannableStringBuilder;
                if (this.currentNameString.length() > 0) {
                    spannableStringBuilder = new SpannableStringBuilder(String.format("%s via %s", new Object[]{ellipsize, str}));
                    spannableStringBuilder.setSpan(new TypefaceSpan(Typeface.DEFAULT, 0, color), ellipsize.length() + 1, ellipsize.length() + 4, 33);
                    spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, color), ellipsize.length() + 5, spannableStringBuilder.length(), 33);
                    charSequence2 = spannableStringBuilder;
                } else {
                    spannableStringBuilder = new SpannableStringBuilder(String.format("via %s", new Object[]{str}));
                    spannableStringBuilder.setSpan(new TypefaceSpan(Typeface.DEFAULT, 0, color), 0, 4, 33);
                    spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf"), 0, color), 4, spannableStringBuilder.length(), 33);
                    obj = spannableStringBuilder;
                }
                ellipsize2 = TextUtils.ellipsize(charSequence2, Theme.chat_namePaint, (float) this.nameWidth, TruncateAt.END);
            } else {
                ellipsize2 = ellipsize;
            }
            try {
                this.nameLayout = new StaticLayout(ellipsize2, Theme.chat_namePaint, this.nameWidth + AndroidUtilities.dp(2.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                if (this.nameLayout == null || this.nameLayout.getLineCount() <= 0) {
                    this.nameWidth = 0;
                } else {
                    this.nameWidth = (int) Math.ceil((double) this.nameLayout.getLineWidth(0));
                    if (messageObject.type != 13) {
                        this.namesOffset += AndroidUtilities.dp(19.0f);
                    }
                    this.nameOffsetX = this.nameLayout.getLineLeft(0);
                }
                if (str3 != null) {
                    this.adminLayout = new StaticLayout(str3, Theme.chat_adminPaint, i + AndroidUtilities.dp(2.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    this.nameWidth = (int) (((float) this.nameWidth) + (this.adminLayout.getLineWidth(0) + ((float) AndroidUtilities.dp(8.0f))));
                } else {
                    this.adminLayout = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (this.currentNameString.length() == 0) {
                this.currentNameString = null;
            }
        }
        this.currentForwardUser = null;
        this.currentForwardNameString = null;
        this.currentForwardChannel = null;
        this.forwardedNameLayout[0] = null;
        this.forwardedNameLayout[1] = null;
        this.forwardedNameWidth = 0;
        if (this.drawForwardedName && messageObject.needDrawForwarded() && (this.currentPosition == null || this.currentPosition.minY == (byte) 0)) {
            if (messageObject.messageOwner.fwd_from.channel_id != 0) {
                this.currentForwardChannel = MessagesController.getInstance().getChat(Integer.valueOf(messageObject.messageOwner.fwd_from.channel_id));
            }
            if (messageObject.messageOwner.fwd_from.from_id != 0) {
                this.currentForwardUser = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.messageOwner.fwd_from.from_id));
            }
            if (!(this.currentForwardUser == null && this.currentForwardChannel == null)) {
                if (this.currentForwardChannel != null) {
                    if (this.currentForwardUser != null) {
                        this.currentForwardNameString = String.format("%s (%s)", new Object[]{this.currentForwardChannel.title, UserObject.getUserName(this.currentForwardUser)});
                    } else {
                        this.currentForwardNameString = this.currentForwardChannel.title;
                    }
                } else if (this.currentForwardUser != null) {
                    this.currentForwardNameString = UserObject.getUserName(this.currentForwardUser);
                }
                this.forwardedNameWidth = getMaxNameWidth();
                str2 = LocaleController.getString("From", R.string.From);
                ellipsize = TextUtils.ellipsize(this.currentForwardNameString.replace('\n', ' '), Theme.chat_replyNamePaint, (float) ((this.forwardedNameWidth - ((int) Math.ceil((double) Theme.chat_forwardNamePaint.measureText(str2 + " ")))) - this.viaWidth), TruncateAt.END);
                if (charSequence != null) {
                    charSequence2 = new SpannableStringBuilder(String.format("%s %s via %s", new Object[]{str2, ellipsize, str}));
                    this.viaNameWidth = (int) Math.ceil((double) Theme.chat_forwardNamePaint.measureText(str2 + " " + ellipsize));
                    charSequence2.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), (charSequence2.length() - str.length()) - 1, charSequence2.length(), 33);
                } else {
                    charSequence2 = new SpannableStringBuilder(String.format("%s %s", new Object[]{str2, ellipsize}));
                }
                charSequence2.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), str2.length() + 1, (str2.length() + 1) + ellipsize.length(), 33);
                try {
                    this.forwardedNameLayout[1] = new StaticLayout(TextUtils.ellipsize(charSequence2, Theme.chat_forwardNamePaint, (float) this.forwardedNameWidth, TruncateAt.END), Theme.chat_forwardNamePaint, this.forwardedNameWidth + AndroidUtilities.dp(2.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    this.forwardedNameLayout[0] = new StaticLayout(TextUtils.ellipsize(AndroidUtilities.replaceTags(LocaleController.getString("ForwardedMessage", R.string.ForwardedMessage)), Theme.chat_forwardNamePaint, (float) this.forwardedNameWidth, TruncateAt.END), Theme.chat_forwardNamePaint, this.forwardedNameWidth + AndroidUtilities.dp(2.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    this.forwardedNameWidth = Math.max((int) Math.ceil((double) this.forwardedNameLayout[0].getLineWidth(0)), (int) Math.ceil((double) this.forwardedNameLayout[1].getLineWidth(0)));
                    this.forwardNameOffsetX[0] = this.forwardedNameLayout[0].getLineLeft(0);
                    this.forwardNameOffsetX[1] = this.forwardedNameLayout[1].getLineLeft(0);
                    if (messageObject.type != 5) {
                        this.namesOffset += AndroidUtilities.dp(36.0f);
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
        }
        if (messageObject.hasValidReplyMessageObject() && (this.currentPosition == null || this.currentPosition.minY == (byte) 0)) {
            int i2;
            CharSequence ellipsize3;
            if (!(messageObject.type == 13 || messageObject.type == 5)) {
                this.namesOffset += AndroidUtilities.dp(42.0f);
                if (messageObject.type != 0) {
                    this.namesOffset += AndroidUtilities.dp(5.0f);
                }
            }
            color = getMaxNameWidth();
            int dp = (messageObject.type == 13 || messageObject.type == 5) ? color : color - AndroidUtilities.dp(10.0f);
            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.replyMessageObject.photoThumbs2, 80);
            PhotoSize closestPhotoSizeWithSize2 = closestPhotoSizeWithSize == null ? FileLoader.getClosestPhotoSizeWithSize(messageObject.replyMessageObject.photoThumbs, 80) : closestPhotoSizeWithSize;
            if (closestPhotoSizeWithSize2 == null || messageObject.replyMessageObject.type == 13 || ((messageObject.type == 13 && !AndroidUtilities.isTablet()) || messageObject.replyMessageObject.isSecretMedia())) {
                this.replyImageReceiver.setImageBitmap((Drawable) null);
                this.needReplyImage = false;
                i2 = dp;
            } else {
                if (messageObject.replyMessageObject.isRoundVideo()) {
                    this.replyImageReceiver.setRoundRadius(AndroidUtilities.dp(22.0f));
                } else {
                    this.replyImageReceiver.setRoundRadius(0);
                }
                this.currentReplyPhoto = closestPhotoSizeWithSize2.location;
                this.replyImageReceiver.setImage(closestPhotoSizeWithSize2.location, "50_50", null, null, 1);
                this.needReplyImage = true;
                i2 = dp - AndroidUtilities.dp(44.0f);
            }
            String str4 = null;
            if (messageObject.customReplyName != null) {
                str4 = messageObject.customReplyName;
            } else if (messageObject.replyMessageObject.isFromUser()) {
                User user2 = MessagesController.getInstance().getUser(Integer.valueOf(messageObject.replyMessageObject.messageOwner.from_id));
                if (user2 != null) {
                    str4 = UserObject.getUserName(user2);
                }
            } else if (messageObject.replyMessageObject.messageOwner.from_id < 0) {
                r1 = MessagesController.getInstance().getChat(Integer.valueOf(-messageObject.replyMessageObject.messageOwner.from_id));
                if (r1 != null) {
                    str4 = r1.title;
                }
            } else {
                r1 = MessagesController.getInstance().getChat(Integer.valueOf(messageObject.replyMessageObject.messageOwner.to_id.channel_id));
                if (r1 != null) {
                    str4 = r1.title;
                }
            }
            if (str4 == null) {
                str4 = LocaleController.getString("Loading", R.string.Loading);
            }
            ellipsize2 = TextUtils.ellipsize(str4.replace('\n', ' '), Theme.chat_replyNamePaint, (float) i2, TruncateAt.END);
            if (messageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                ellipsize3 = TextUtils.ellipsize(Emoji.replaceEmoji(messageObject.replyMessageObject.messageOwner.media.game.title, Theme.chat_replyTextPaint.getFontMetricsInt(), AndroidUtilities.dp(14.0f), false), Theme.chat_replyTextPaint, (float) i2, TruncateAt.END);
            } else if (messageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) {
                ellipsize3 = TextUtils.ellipsize(Emoji.replaceEmoji(messageObject.replyMessageObject.messageOwner.media.title, Theme.chat_replyTextPaint.getFontMetricsInt(), AndroidUtilities.dp(14.0f), false), Theme.chat_replyTextPaint, (float) i2, TruncateAt.END);
            } else if (messageObject.replyMessageObject.messageText == null || messageObject.replyMessageObject.messageText.length() <= 0) {
                ellipsize3 = null;
            } else {
                str4 = messageObject.replyMessageObject.messageText.toString();
                if (str4.length() > 150) {
                    str4 = str4.substring(0, 150);
                }
                ellipsize3 = TextUtils.ellipsize(Emoji.replaceEmoji(str4.replace('\n', ' '), Theme.chat_replyTextPaint.getFontMetricsInt(), AndroidUtilities.dp(14.0f), false), Theme.chat_replyTextPaint, (float) i2, TruncateAt.END);
            }
            try {
                this.replyNameWidth = AndroidUtilities.dp((float) ((this.needReplyImage ? 44 : 0) + 4));
                if (ellipsize2 != null) {
                    this.replyNameLayout = new StaticLayout(ellipsize2, Theme.chat_replyNamePaint, AndroidUtilities.dp(6.0f) + i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    if (this.replyNameLayout.getLineCount() > 0) {
                        this.replyNameWidth += ((int) Math.ceil((double) this.replyNameLayout.getLineWidth(0))) + AndroidUtilities.dp(8.0f);
                        this.replyNameOffset = this.replyNameLayout.getLineLeft(0);
                    }
                }
            } catch (Throwable e22) {
                FileLog.e(e22);
            }
            try {
                this.replyTextWidth = AndroidUtilities.dp((float) ((this.needReplyImage ? 44 : 0) + 4));
                if (ellipsize3 != null) {
                    this.replyTextLayout = new StaticLayout(ellipsize3, Theme.chat_replyTextPaint, i2 + AndroidUtilities.dp(6.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    if (this.replyTextLayout.getLineCount() > 0) {
                        this.replyTextWidth += ((int) Math.ceil((double) this.replyTextLayout.getLineWidth(0))) + AndroidUtilities.dp(8.0f);
                        this.replyTextOffset = this.replyTextLayout.getLineLeft(0);
                    }
                }
            } catch (Throwable e222) {
                FileLog.e(e222);
            }
        }
        requestLayout();
    }

    private void updateCurrentUserAndChat() {
        MessageFwdHeader messageFwdHeader = this.currentMessageObject.messageOwner.fwd_from;
        int clientUserId = UserConfig.getClientUserId();
        if (messageFwdHeader != null && messageFwdHeader.channel_id != 0 && this.currentMessageObject.getDialogId() == ((long) clientUserId)) {
            this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(messageFwdHeader.channel_id));
        } else if (messageFwdHeader == null || messageFwdHeader.saved_from_peer == null) {
            if (messageFwdHeader != null && messageFwdHeader.from_id != 0 && messageFwdHeader.channel_id == 0 && this.currentMessageObject.getDialogId() == ((long) clientUserId)) {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(messageFwdHeader.from_id));
            } else if (this.currentMessageObject.isFromUser()) {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(this.currentMessageObject.messageOwner.from_id));
            } else if (this.currentMessageObject.messageOwner.from_id < 0) {
                this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(-this.currentMessageObject.messageOwner.from_id));
            } else if (this.currentMessageObject.messageOwner.post) {
                this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.currentMessageObject.messageOwner.to_id.channel_id));
            }
        } else if (messageFwdHeader.saved_from_peer.user_id != 0) {
            if (messageFwdHeader.from_id != 0) {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(messageFwdHeader.from_id));
            } else {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(messageFwdHeader.saved_from_peer.user_id));
            }
        } else if (messageFwdHeader.saved_from_peer.channel_id != 0) {
            if (!this.currentMessageObject.isSavedFromMegagroup() || messageFwdHeader.from_id == 0) {
                this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(messageFwdHeader.saved_from_peer.channel_id));
            } else {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(messageFwdHeader.from_id));
            }
        } else if (messageFwdHeader.saved_from_peer.chat_id == 0) {
        } else {
            if (messageFwdHeader.from_id != 0) {
                this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(messageFwdHeader.from_id));
            } else {
                this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(messageFwdHeader.saved_from_peer.chat_id));
            }
        }
    }

    private void updateRadialProgressBackground(boolean z) {
        if (!this.drawRadialCheckBackground && z) {
            this.radialProgress.swapBackground(getDrawableForCurrentState());
        }
    }

    private void updateSecretTimeText(MessageObject messageObject) {
        if (messageObject != null && messageObject.isSecretPhoto()) {
            CharSequence secretTimeString = messageObject.getSecretTimeString();
            if (secretTimeString != null) {
                this.infoWidth = (int) Math.ceil((double) Theme.chat_infoPaint.measureText(secretTimeString));
                this.infoLayout = new StaticLayout(TextUtils.ellipsize(secretTimeString, Theme.chat_infoPaint, (float) this.infoWidth, TruncateAt.END), Theme.chat_infoPaint, this.infoWidth, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                invalidate();
            }
        }
    }

    private void updateWaveform() {
        boolean z = false;
        if (this.currentMessageObject != null && this.documentAttachType == 3) {
            for (int i = 0; i < this.documentAttach.attributes.size(); i++) {
                DocumentAttribute documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i);
                if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                    if (documentAttribute.waveform == null || documentAttribute.waveform.length == 0) {
                        MediaController.getInstance().generateWaveform(this.currentMessageObject);
                    }
                    if (documentAttribute.waveform != null) {
                        z = true;
                    }
                    this.useSeekBarWaweform = z;
                    this.seekBarWaveform.setWaveform(documentAttribute.waveform);
                    return;
                }
            }
        }
    }

    public void checkRoundVideoPlayback(boolean z) {
        boolean z2 = z ? MediaController.getInstance().getPlayingMessageObject() == null : z;
        this.photoImage.setAllowStartAnimation(z2);
        if (z2) {
            this.photoImage.startAnimation();
        } else {
            this.photoImage.stopAnimation();
        }
    }

    public void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2) {
        if (this.currentMessageObject != null && z && !z2 && !this.currentMessageObject.mediaExists && !this.currentMessageObject.attachPathExists) {
            this.currentMessageObject.mediaExists = true;
            updateButtonState(true);
        }
    }

    public void downloadAudioIfNeed() {
        if (this.documentAttachType == 3 && this.buttonState == 2) {
            FileLoader.getInstance().loadFile(this.documentAttach, true, 0);
            this.buttonState = 4;
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
        }
    }

    public void drawCaptionLayout(Canvas canvas) {
        float f = 11.0f;
        float f2 = 9.0f;
        if (this.captionLayout != null) {
            int imageY;
            canvas.save();
            int imageX;
            if (this.currentMessageObject.type == 1 || this.documentAttachType == 4 || this.currentMessageObject.type == 8) {
                imageX = this.photoImage.getImageX() + AndroidUtilities.dp(5.0f);
                this.captionX = imageX;
                f = (float) imageX;
                imageY = (this.photoImage.getImageY() + this.photoImage.getImageHeight()) + AndroidUtilities.dp(6.0f);
                this.captionY = imageY;
                canvas.translate(f, (float) imageY);
            } else if (this.hasOldCaptionPreview) {
                r4 = this.backgroundDrawableLeft;
                if (!this.currentMessageObject.isOutOwner()) {
                    f = 17.0f;
                }
                imageX = AndroidUtilities.dp(f) + r4;
                this.captionX = imageX;
                float f3 = (float) imageX;
                imageX = (((this.totalHeight - this.captionHeight) - AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f)) - this.linkPreviewHeight) - AndroidUtilities.dp(17.0f);
                this.captionY = imageX;
                canvas.translate(f3, (float) imageX);
            } else {
                r4 = this.backgroundDrawableLeft;
                if (!this.currentMessageObject.isOutOwner()) {
                    f = 17.0f;
                }
                imageX = AndroidUtilities.dp(f) + r4;
                this.captionX = imageX;
                f = (float) imageX;
                imageY = this.totalHeight - this.captionHeight;
                if (!this.drawPinnedTop) {
                    f2 = 10.0f;
                }
                imageY -= AndroidUtilities.dp(f2);
                this.captionY = imageY;
                canvas.translate(f, (float) imageY);
            }
            if (this.pressedLink != null) {
                for (imageY = 0; imageY < this.urlPath.size(); imageY++) {
                    canvas.drawPath((Path) this.urlPath.get(imageY), Theme.chat_urlPaint);
                }
            }
            try {
                this.captionLayout.draw(canvas);
            } catch (Throwable e) {
                FileLog.e(e);
            }
            canvas.restore();
        }
    }

    public void drawNamesLayout(Canvas canvas) {
        int dp;
        float f = 11.0f;
        int i = 0;
        if (this.drawNameLayout && this.nameLayout != null) {
            canvas.save();
            if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                Theme.chat_namePaint.setColor(Theme.getColor(Theme.key_chat_stickerNameText));
                if (this.currentMessageObject.isOutOwner()) {
                    this.nameX = (float) AndroidUtilities.dp(28.0f);
                } else {
                    this.nameX = (float) ((this.backgroundDrawableLeft + this.backgroundDrawableRight) + AndroidUtilities.dp(22.0f));
                }
                this.nameY = (float) (this.layoutHeight - AndroidUtilities.dp(38.0f));
                Theme.chat_systemDrawable.setColorFilter(Theme.colorFilter);
                Theme.chat_systemDrawable.setBounds(((int) this.nameX) - AndroidUtilities.dp(12.0f), ((int) this.nameY) - AndroidUtilities.dp(5.0f), (((int) this.nameX) + AndroidUtilities.dp(12.0f)) + this.nameWidth, ((int) this.nameY) + AndroidUtilities.dp(22.0f));
                Theme.chat_systemDrawable.draw(canvas);
            } else {
                if (this.mediaBackground || this.currentMessageObject.isOutOwner()) {
                    this.nameX = ((float) (this.backgroundDrawableLeft + AndroidUtilities.dp(11.0f))) - this.nameOffsetX;
                } else {
                    int i2 = this.backgroundDrawableLeft;
                    float f2 = (this.mediaBackground || !this.drawPinnedBottom) ? 17.0f : 11.0f;
                    this.nameX = ((float) (AndroidUtilities.dp(f2) + i2)) - this.nameOffsetX;
                }
                if (this.currentUser != null) {
                    Theme.chat_namePaint.setColor(AvatarDrawable.getNameColorForId(this.currentUser.id));
                } else if (this.currentChat == null) {
                    Theme.chat_namePaint.setColor(AvatarDrawable.getNameColorForId(0));
                } else if (!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup) {
                    Theme.chat_namePaint.setColor(AvatarDrawable.getNameColorForId(this.currentChat.id));
                } else {
                    Theme.chat_namePaint.setColor(AvatarDrawable.getNameColorForId(5));
                }
                this.nameY = (float) AndroidUtilities.dp(this.drawPinnedTop ? 9.0f : 10.0f);
            }
            canvas.translate(this.nameX, this.nameY);
            this.nameLayout.draw(canvas);
            canvas.restore();
            if (this.adminLayout != null) {
                Theme.chat_adminPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_adminSelectedText : Theme.key_chat_adminText));
                canvas.save();
                canvas.translate(((float) ((this.backgroundDrawableLeft + this.backgroundDrawableRight) - AndroidUtilities.dp(11.0f))) - this.adminLayout.getLineWidth(0), this.nameY + ((float) AndroidUtilities.dp(0.5f)));
                this.adminLayout.draw(canvas);
                canvas.restore();
            }
        }
        if (this.drawForwardedName && this.forwardedNameLayout[0] != null && this.forwardedNameLayout[1] != null && (this.currentPosition == null || (this.currentPosition.minY == (byte) 0 && this.currentPosition.minX == (byte) 0))) {
            if (this.currentMessageObject.type == 5) {
                Theme.chat_forwardNamePaint.setColor(Theme.getColor(Theme.key_chat_stickerReplyNameText));
                if (this.currentMessageObject.isOutOwner()) {
                    this.forwardNameX = AndroidUtilities.dp(23.0f);
                } else {
                    this.forwardNameX = (this.backgroundDrawableLeft + this.backgroundDrawableRight) + AndroidUtilities.dp(17.0f);
                }
                this.forwardNameY = AndroidUtilities.dp(12.0f);
                dp = this.forwardedNameWidth + AndroidUtilities.dp(14.0f);
                Theme.chat_systemDrawable.setColorFilter(Theme.colorFilter);
                Theme.chat_systemDrawable.setBounds(this.forwardNameX - AndroidUtilities.dp(7.0f), this.forwardNameY - AndroidUtilities.dp(6.0f), dp + (this.forwardNameX - AndroidUtilities.dp(7.0f)), this.forwardNameY + AndroidUtilities.dp(38.0f));
                Theme.chat_systemDrawable.draw(canvas);
            } else {
                this.forwardNameY = AndroidUtilities.dp((float) ((this.drawNameLayout ? 19 : 0) + 10));
                if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_forwardNamePaint.setColor(Theme.getColor(Theme.key_chat_outForwardedNameText));
                    this.forwardNameX = this.backgroundDrawableLeft + AndroidUtilities.dp(11.0f);
                } else {
                    Theme.chat_forwardNamePaint.setColor(Theme.getColor(Theme.key_chat_inForwardedNameText));
                    if (this.mediaBackground) {
                        this.forwardNameX = this.backgroundDrawableLeft + AndroidUtilities.dp(11.0f);
                    } else {
                        dp = this.backgroundDrawableLeft;
                        if (this.mediaBackground || !this.drawPinnedBottom) {
                            f = 17.0f;
                        }
                        this.forwardNameX = dp + AndroidUtilities.dp(f);
                    }
                }
            }
            for (dp = 0; dp < 2; dp++) {
                canvas.save();
                canvas.translate(((float) this.forwardNameX) - this.forwardNameOffsetX[dp], (float) (this.forwardNameY + (AndroidUtilities.dp(16.0f) * dp)));
                this.forwardedNameLayout[dp].draw(canvas);
                canvas.restore();
            }
        }
        if (this.replyNameLayout != null) {
            if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                Theme.chat_replyLinePaint.setColor(Theme.getColor(Theme.key_chat_stickerReplyLine));
                Theme.chat_replyNamePaint.setColor(Theme.getColor(Theme.key_chat_stickerReplyNameText));
                Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_stickerReplyMessageText));
                if (this.currentMessageObject.isOutOwner()) {
                    this.replyStartX = AndroidUtilities.dp(23.0f);
                } else {
                    this.replyStartX = (this.backgroundDrawableLeft + this.backgroundDrawableRight) + AndroidUtilities.dp(17.0f);
                }
                this.replyStartY = AndroidUtilities.dp(12.0f);
                if (this.nameLayout != null) {
                    this.replyStartY -= AndroidUtilities.dp(31.0f);
                }
                dp = Math.max(this.replyNameWidth, this.replyTextWidth) + AndroidUtilities.dp(14.0f);
                Theme.chat_systemDrawable.setColorFilter(Theme.colorFilter);
                Theme.chat_systemDrawable.setBounds(this.replyStartX - AndroidUtilities.dp(7.0f), this.replyStartY - AndroidUtilities.dp(6.0f), dp + (this.replyStartX - AndroidUtilities.dp(7.0f)), this.replyStartY + AndroidUtilities.dp(41.0f));
                Theme.chat_systemDrawable.draw(canvas);
            } else {
                int i3;
                if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_replyLinePaint.setColor(Theme.getColor(Theme.key_chat_outReplyLine));
                    Theme.chat_replyNamePaint.setColor(Theme.getColor(Theme.key_chat_outReplyNameText));
                    if (!this.currentMessageObject.hasValidReplyMessageObject() || this.currentMessageObject.replyMessageObject.type != 0 || (this.currentMessageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) || (this.currentMessageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
                        Theme.chat_replyTextPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outReplyMediaMessageSelectedText : Theme.key_chat_outReplyMediaMessageText));
                    } else {
                        Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_outReplyMessageText));
                    }
                    this.replyStartX = this.backgroundDrawableLeft + AndroidUtilities.dp(12.0f);
                } else {
                    Theme.chat_replyLinePaint.setColor(Theme.getColor(Theme.key_chat_inReplyLine));
                    Theme.chat_replyNamePaint.setColor(Theme.getColor(Theme.key_chat_inReplyNameText));
                    if (!this.currentMessageObject.hasValidReplyMessageObject() || this.currentMessageObject.replyMessageObject.type != 0 || (this.currentMessageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) || (this.currentMessageObject.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
                        Theme.chat_replyTextPaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inReplyMediaMessageSelectedText : Theme.key_chat_inReplyMediaMessageText));
                    } else {
                        Theme.chat_replyTextPaint.setColor(Theme.getColor(Theme.key_chat_inReplyMessageText));
                    }
                    if (this.mediaBackground) {
                        this.replyStartX = this.backgroundDrawableLeft + AndroidUtilities.dp(12.0f);
                    } else {
                        i3 = this.backgroundDrawableLeft;
                        f2 = (this.mediaBackground || !this.drawPinnedBottom) ? 18.0f : 12.0f;
                        this.replyStartX = AndroidUtilities.dp(f2) + i3;
                    }
                }
                dp = (!this.drawForwardedName || this.forwardedNameLayout[0] == null) ? 0 : 36;
                i3 = dp + 12;
                dp = (!this.drawNameLayout || this.nameLayout == null) ? 0 : 20;
                this.replyStartY = AndroidUtilities.dp((float) (dp + i3));
            }
            if (this.currentPosition == null || (this.currentPosition.minY == (byte) 0 && this.currentPosition.minX == (byte) 0)) {
                canvas.drawRect((float) this.replyStartX, (float) this.replyStartY, (float) (this.replyStartX + AndroidUtilities.dp(2.0f)), (float) (this.replyStartY + AndroidUtilities.dp(35.0f)), Theme.chat_replyLinePaint);
                if (this.needReplyImage) {
                    this.replyImageReceiver.setImageCoords(this.replyStartX + AndroidUtilities.dp(10.0f), this.replyStartY, AndroidUtilities.dp(35.0f), AndroidUtilities.dp(35.0f));
                    this.replyImageReceiver.draw(canvas);
                }
                if (this.replyNameLayout != null) {
                    canvas.save();
                    canvas.translate(((float) AndroidUtilities.dp((float) ((this.needReplyImage ? 44 : 0) + 10))) + (((float) this.replyStartX) - this.replyNameOffset), (float) this.replyStartY);
                    this.replyNameLayout.draw(canvas);
                    canvas.restore();
                }
                if (this.replyTextLayout != null) {
                    canvas.save();
                    f2 = ((float) this.replyStartX) - this.replyTextOffset;
                    if (this.needReplyImage) {
                        i = 44;
                    }
                    canvas.translate(f2 + ((float) AndroidUtilities.dp((float) (i + 10))), (float) (this.replyStartY + AndroidUtilities.dp(19.0f)));
                    this.replyTextLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }
    }

    public void drawTimeLayout(Canvas canvas) {
        int i = 1;
        int i2 = 0;
        if ((this.drawTime && !this.groupPhotoInvisible) || !this.mediaBackground) {
            int alpha;
            int dp;
            int i3;
            Drawable drawable;
            if (this.currentMessageObject.type == 5) {
                Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
            } else if (this.mediaBackground && this.captionLayout == null) {
                if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                    Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_serviceText));
                } else {
                    Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
                }
            } else if (this.currentMessageObject.isOutOwner()) {
                Theme.chat_timePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
            } else {
                Theme.chat_timePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
            }
            if (this.drawPinnedBottom) {
                canvas.translate(BitmapDescriptorFactory.HUE_RED, (float) AndroidUtilities.dp(2.0f));
            }
            if (this.mediaBackground && this.captionLayout == null) {
                Paint paint = (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) ? Theme.chat_actionBackgroundPaint : Theme.chat_timeBackgroundPaint;
                alpha = paint.getAlpha();
                paint.setAlpha((int) (((float) alpha) * this.timeAlpha));
                Theme.chat_timePaint.setAlpha((int) (255.0f * this.timeAlpha));
                dp = this.timeX - AndroidUtilities.dp(4.0f);
                int dp2 = this.layoutHeight - AndroidUtilities.dp(28.0f);
                this.rect.set((float) dp, (float) dp2, (float) (AndroidUtilities.dp((float) ((this.currentMessageObject.isOutOwner() ? 20 : 0) + 8)) + (this.timeWidth + dp)), (float) (dp2 + AndroidUtilities.dp(17.0f)));
                canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(4.0f), paint);
                paint.setAlpha(alpha);
                i3 = (int) (-this.timeLayout.getLineLeft(0));
                if ((this.currentMessageObject.messageOwner.flags & 1024) != 0) {
                    alpha = i3 + ((int) (((float) this.timeWidth) - this.timeLayout.getLineWidth(0)));
                    if (this.currentMessageObject.isSending()) {
                        if (!this.currentMessageObject.isOutOwner()) {
                            setDrawableBounds(Theme.chat_msgMediaClockDrawable, this.timeX + AndroidUtilities.dp(11.0f), (this.layoutHeight - AndroidUtilities.dp(14.0f)) - Theme.chat_msgMediaClockDrawable.getIntrinsicHeight());
                            Theme.chat_msgMediaClockDrawable.draw(canvas);
                            i3 = alpha;
                        }
                    } else if (!this.currentMessageObject.isSendError()) {
                        Drawable drawable2 = (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) ? Theme.chat_msgStickerViewsDrawable : Theme.chat_msgMediaViewsDrawable;
                        i3 = ((BitmapDrawable) drawable2).getPaint().getAlpha();
                        drawable2.setAlpha((int) (this.timeAlpha * ((float) i3)));
                        setDrawableBounds(drawable2, this.timeX, (this.layoutHeight - AndroidUtilities.dp(10.5f)) - this.timeLayout.getHeight());
                        drawable2.draw(canvas);
                        drawable2.setAlpha(i3);
                        if (this.viewsLayout != null) {
                            canvas.save();
                            canvas.translate((float) ((this.timeX + drawable2.getIntrinsicWidth()) + AndroidUtilities.dp(3.0f)), (float) ((this.layoutHeight - AndroidUtilities.dp(12.3f)) - this.timeLayout.getHeight()));
                            this.viewsLayout.draw(canvas);
                            canvas.restore();
                        }
                    } else if (!this.currentMessageObject.isOutOwner()) {
                        i3 = this.timeX + AndroidUtilities.dp(11.0f);
                        dp = this.layoutHeight - AndroidUtilities.dp(27.5f);
                        this.rect.set((float) i3, (float) dp, (float) (AndroidUtilities.dp(14.0f) + i3), (float) (AndroidUtilities.dp(14.0f) + dp));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), Theme.chat_msgErrorPaint);
                        setDrawableBounds(Theme.chat_msgErrorDrawable, i3 + AndroidUtilities.dp(6.0f), dp + AndroidUtilities.dp(2.0f));
                        Theme.chat_msgErrorDrawable.draw(canvas);
                        i3 = alpha;
                    }
                    i3 = alpha;
                }
                canvas.save();
                canvas.translate((float) (i3 + this.timeX), (float) ((this.layoutHeight - AndroidUtilities.dp(12.3f)) - this.timeLayout.getHeight()));
                this.timeLayout.draw(canvas);
                canvas.restore();
                Theme.chat_timePaint.setAlpha(255);
            } else {
                i3 = (int) (-this.timeLayout.getLineLeft(0));
                if ((this.currentMessageObject.messageOwner.flags & 1024) != 0) {
                    dp = ((int) (((float) this.timeWidth) - this.timeLayout.getLineWidth(0))) + i3;
                    if (this.currentMessageObject.isSending()) {
                        if (!this.currentMessageObject.isOutOwner()) {
                            drawable = isDrawSelectedBackground() ? Theme.chat_msgInSelectedClockDrawable : Theme.chat_msgInClockDrawable;
                            setDrawableBounds(drawable, this.timeX + AndroidUtilities.dp(11.0f), (this.layoutHeight - AndroidUtilities.dp(8.5f)) - drawable.getIntrinsicHeight());
                            drawable.draw(canvas);
                            i3 = dp;
                        }
                    } else if (!this.currentMessageObject.isSendError()) {
                        if (this.currentMessageObject.isOutOwner()) {
                            drawable = isDrawSelectedBackground() ? Theme.chat_msgOutViewsSelectedDrawable : Theme.chat_msgOutViewsDrawable;
                            setDrawableBounds(drawable, this.timeX, (this.layoutHeight - AndroidUtilities.dp(4.5f)) - this.timeLayout.getHeight());
                            drawable.draw(canvas);
                        } else {
                            drawable = isDrawSelectedBackground() ? Theme.chat_msgInViewsSelectedDrawable : Theme.chat_msgInViewsDrawable;
                            setDrawableBounds(drawable, this.timeX, (this.layoutHeight - AndroidUtilities.dp(4.5f)) - this.timeLayout.getHeight());
                            drawable.draw(canvas);
                        }
                        if (this.viewsLayout != null) {
                            canvas.save();
                            canvas.translate((float) ((this.timeX + Theme.chat_msgInViewsDrawable.getIntrinsicWidth()) + AndroidUtilities.dp(3.0f)), (float) ((this.layoutHeight - AndroidUtilities.dp(6.5f)) - this.timeLayout.getHeight()));
                            this.viewsLayout.draw(canvas);
                            canvas.restore();
                        }
                    } else if (!this.currentMessageObject.isOutOwner()) {
                        i3 = this.timeX + AndroidUtilities.dp(11.0f);
                        alpha = this.layoutHeight - AndroidUtilities.dp(20.5f);
                        this.rect.set((float) i3, (float) alpha, (float) (AndroidUtilities.dp(14.0f) + i3), (float) (AndroidUtilities.dp(14.0f) + alpha));
                        canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), Theme.chat_msgErrorPaint);
                        setDrawableBounds(Theme.chat_msgErrorDrawable, i3 + AndroidUtilities.dp(6.0f), alpha + AndroidUtilities.dp(2.0f));
                        Theme.chat_msgErrorDrawable.draw(canvas);
                        i3 = dp;
                    }
                    i3 = dp;
                }
                canvas.save();
                canvas.translate((float) (i3 + this.timeX), (float) ((this.layoutHeight - AndroidUtilities.dp(6.5f)) - this.timeLayout.getHeight()));
                this.timeLayout.draw(canvas);
                canvas.restore();
            }
            if (this.currentMessageObject.isOutOwner()) {
                i3 = ((int) (this.currentMessageObject.getDialogId() >> 32)) == 1 ? 1 : 0;
                if (this.currentMessageObject.isSending()) {
                    dp = 0;
                    alpha = 0;
                } else if (this.currentMessageObject.isSendError()) {
                    dp = 0;
                    alpha = 0;
                    i = 0;
                    i2 = 1;
                } else if (this.currentMessageObject.isSent()) {
                    alpha = !this.currentMessageObject.isUnread() ? 1 : 0;
                    dp = 1;
                    i = 0;
                } else {
                    i = 0;
                    dp = 0;
                    alpha = 0;
                }
                if (i != 0) {
                    if (!this.mediaBackground || this.captionLayout != null) {
                        setDrawableBounds(Theme.chat_msgOutClockDrawable, (this.layoutWidth - AndroidUtilities.dp(18.5f)) - Theme.chat_msgOutClockDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(8.5f)) - Theme.chat_msgOutClockDrawable.getIntrinsicHeight());
                        Theme.chat_msgOutClockDrawable.draw(canvas);
                    } else if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                        setDrawableBounds(Theme.chat_msgStickerClockDrawable, (this.layoutWidth - AndroidUtilities.dp(22.0f)) - Theme.chat_msgStickerClockDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgStickerClockDrawable.getIntrinsicHeight());
                        Theme.chat_msgStickerClockDrawable.draw(canvas);
                    } else {
                        setDrawableBounds(Theme.chat_msgMediaClockDrawable, (this.layoutWidth - AndroidUtilities.dp(22.0f)) - Theme.chat_msgMediaClockDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgMediaClockDrawable.getIntrinsicHeight());
                        Theme.chat_msgMediaClockDrawable.draw(canvas);
                    }
                }
                if (i3 == 0) {
                    if (dp != 0) {
                        if (!this.mediaBackground || this.captionLayout != null) {
                            drawable = isDrawSelectedBackground() ? Theme.chat_msgOutCheckSelectedDrawable : Theme.chat_msgOutCheckDrawable;
                            if (alpha != 0) {
                                setDrawableBounds(drawable, (this.layoutWidth - AndroidUtilities.dp(22.5f)) - drawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(8.0f)) - drawable.getIntrinsicHeight());
                            } else {
                                setDrawableBounds(drawable, (this.layoutWidth - AndroidUtilities.dp(18.5f)) - drawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(8.0f)) - drawable.getIntrinsicHeight());
                            }
                            drawable.draw(canvas);
                        } else if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                            if (alpha != 0) {
                                setDrawableBounds(Theme.chat_msgStickerCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(26.3f)) - Theme.chat_msgStickerCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgStickerCheckDrawable.getIntrinsicHeight());
                            } else {
                                setDrawableBounds(Theme.chat_msgStickerCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(21.5f)) - Theme.chat_msgStickerCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgStickerCheckDrawable.getIntrinsicHeight());
                            }
                            Theme.chat_msgStickerCheckDrawable.draw(canvas);
                        } else {
                            if (alpha != 0) {
                                setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(26.3f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight());
                            } else {
                                setDrawableBounds(Theme.chat_msgMediaCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(21.5f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgMediaCheckDrawable.getIntrinsicHeight());
                            }
                            Theme.chat_msgMediaCheckDrawable.setAlpha((int) (255.0f * this.timeAlpha));
                            Theme.chat_msgMediaCheckDrawable.draw(canvas);
                            Theme.chat_msgMediaCheckDrawable.setAlpha(255);
                        }
                    }
                    if (alpha != 0) {
                        if (!this.mediaBackground || this.captionLayout != null) {
                            drawable = isDrawSelectedBackground() ? Theme.chat_msgOutHalfCheckSelectedDrawable : Theme.chat_msgOutHalfCheckDrawable;
                            setDrawableBounds(drawable, (this.layoutWidth - AndroidUtilities.dp(18.0f)) - drawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(8.0f)) - drawable.getIntrinsicHeight());
                            drawable.draw(canvas);
                        } else if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                            setDrawableBounds(Theme.chat_msgStickerHalfCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(21.5f)) - Theme.chat_msgStickerHalfCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgStickerHalfCheckDrawable.getIntrinsicHeight());
                            Theme.chat_msgStickerHalfCheckDrawable.draw(canvas);
                        } else {
                            setDrawableBounds(Theme.chat_msgMediaHalfCheckDrawable, (this.layoutWidth - AndroidUtilities.dp(21.5f)) - Theme.chat_msgMediaHalfCheckDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(13.5f)) - Theme.chat_msgMediaHalfCheckDrawable.getIntrinsicHeight());
                            Theme.chat_msgMediaHalfCheckDrawable.setAlpha((int) (255.0f * this.timeAlpha));
                            Theme.chat_msgMediaHalfCheckDrawable.draw(canvas);
                            Theme.chat_msgMediaHalfCheckDrawable.setAlpha(255);
                        }
                    }
                } else if (!(alpha == 0 && dp == 0)) {
                    if (this.mediaBackground && this.captionLayout == null) {
                        setDrawableBounds(Theme.chat_msgBroadcastMediaDrawable, (this.layoutWidth - AndroidUtilities.dp(24.0f)) - Theme.chat_msgBroadcastMediaDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(14.0f)) - Theme.chat_msgBroadcastMediaDrawable.getIntrinsicHeight());
                        Theme.chat_msgBroadcastMediaDrawable.draw(canvas);
                    } else {
                        setDrawableBounds(Theme.chat_msgBroadcastDrawable, (this.layoutWidth - AndroidUtilities.dp(20.5f)) - Theme.chat_msgBroadcastDrawable.getIntrinsicWidth(), (this.layoutHeight - AndroidUtilities.dp(8.0f)) - Theme.chat_msgBroadcastDrawable.getIntrinsicHeight());
                        Theme.chat_msgBroadcastDrawable.draw(canvas);
                    }
                }
                if (i2 != 0) {
                    if (this.mediaBackground && this.captionLayout == null) {
                        dp = this.layoutWidth - AndroidUtilities.dp(34.5f);
                        i3 = this.layoutHeight - AndroidUtilities.dp(26.5f);
                    } else {
                        dp = this.layoutWidth - AndroidUtilities.dp(32.0f);
                        i3 = this.layoutHeight - AndroidUtilities.dp(21.0f);
                    }
                    this.rect.set((float) dp, (float) i3, (float) (AndroidUtilities.dp(14.0f) + dp), (float) (AndroidUtilities.dp(14.0f) + i3));
                    canvas.drawRoundRect(this.rect, (float) AndroidUtilities.dp(1.0f), (float) AndroidUtilities.dp(1.0f), Theme.chat_msgErrorPaint);
                    setDrawableBounds(Theme.chat_msgErrorDrawable, dp + AndroidUtilities.dp(6.0f), i3 + AndroidUtilities.dp(2.0f));
                    Theme.chat_msgErrorDrawable.draw(canvas);
                }
            }
        }
    }

    public ImageReceiver getAvatarImage() {
        return this.isAvatarVisible ? this.avatarImage : null;
    }

    public int getBackgroundDrawableLeft() {
        int i = 0;
        if (this.currentMessageObject.isOutOwner()) {
            int i2 = this.layoutWidth - this.backgroundWidth;
            if (this.mediaBackground) {
                i = AndroidUtilities.dp(9.0f);
            }
            return i2 - i;
        }
        if (this.isChat && this.isAvatarVisible) {
            i = 48;
        }
        return AndroidUtilities.dp((float) (i + (!this.mediaBackground ? 3 : 9)));
    }

    public GroupedMessages getCurrentMessagesGroup() {
        return this.currentMessagesGroup;
    }

    public GroupedMessagePosition getCurrentPosition() {
        return this.currentPosition;
    }

    public int getLayoutHeight() {
        return this.layoutHeight;
    }

    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public int getObserverTag() {
        return this.TAG;
    }

    public ImageReceiver getPhotoImage() {
        return this.photoImage;
    }

    public boolean hasCaptionLayout() {
        return this.captionLayout != null;
    }

    public boolean hasNameLayout() {
        return (this.drawNameLayout && this.nameLayout != null) || ((this.drawForwardedName && this.forwardedNameLayout[0] != null && this.forwardedNameLayout[1] != null && (this.currentPosition == null || (this.currentPosition.minY == (byte) 0 && this.currentPosition.minX == (byte) 0))) || this.replyNameLayout != null);
    }

    public boolean isInsideBackground(float f, float f2) {
        return this.currentBackgroundDrawable != null && f >= ((float) (getLeft() + this.backgroundDrawableLeft)) && f <= ((float) ((getLeft() + this.backgroundDrawableLeft) + this.backgroundDrawableRight));
    }

    public boolean isPinnedBottom() {
        return this.pinnedBottom;
    }

    public boolean isPinnedTop() {
        return this.pinnedTop;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
        this.avatarImage.setParentView((View) getParent());
        this.replyImageReceiver.onAttachedToWindow();
        this.locationImageReceiver.onAttachedToWindow();
        if (!this.drawPhotoImage) {
            updateButtonState(false);
        } else if (this.photoImage.onAttachedToWindow()) {
            updateButtonState(false);
        }
        if (this.currentMessageObject != null && this.currentMessageObject.isRoundVideo()) {
            checkRoundVideoPlayback(true);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.avatarImage.onDetachedFromWindow();
        this.replyImageReceiver.onDetachedFromWindow();
        this.locationImageReceiver.onDetachedFromWindow();
        this.photoImage.onDetachedFromWindow();
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    protected void onDraw(Canvas canvas) {
        if (this.currentMessageObject != null) {
            if (this.wasLayout) {
                Drawable drawable;
                Drawable drawable2;
                int i;
                int dp;
                int dp2;
                long currentTimeMillis;
                long abs;
                float a;
                if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_msgTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                    Theme.chat_msgTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkOut);
                    Theme.chat_msgGameTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextOut));
                    Theme.chat_msgGameTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkOut);
                    Theme.chat_replyTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkOut);
                } else {
                    Theme.chat_msgTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                    Theme.chat_msgTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkIn);
                    Theme.chat_msgGameTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
                    Theme.chat_msgGameTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkIn);
                    Theme.chat_replyTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkIn);
                }
                if (this.documentAttach != null) {
                    if (this.documentAttachType == 3) {
                        if (this.currentMessageObject.isOutOwner()) {
                            this.seekBarWaveform.setColors(Theme.getColor(Theme.key_chat_outVoiceSeekbar), Theme.getColor(Theme.key_chat_outVoiceSeekbarFill), Theme.getColor(Theme.key_chat_outVoiceSeekbarSelected));
                            this.seekBar.setColors(Theme.getColor(Theme.key_chat_outAudioSeekbar), Theme.getColor(Theme.key_chat_outAudioSeekbarFill), Theme.getColor(Theme.key_chat_outAudioSeekbarSelected));
                        } else {
                            this.seekBarWaveform.setColors(Theme.getColor(Theme.key_chat_inVoiceSeekbar), Theme.getColor(Theme.key_chat_inVoiceSeekbarFill), Theme.getColor(Theme.key_chat_inVoiceSeekbarSelected));
                            this.seekBar.setColors(Theme.getColor(Theme.key_chat_inAudioSeekbar), Theme.getColor(Theme.key_chat_inAudioSeekbarFill), Theme.getColor(Theme.key_chat_inAudioSeekbarSelected));
                        }
                    } else if (this.documentAttachType == 5) {
                        this.documentAttachType = 5;
                        if (this.currentMessageObject.isOutOwner()) {
                            this.seekBar.setColors(Theme.getColor(Theme.key_chat_outAudioSeekbar), Theme.getColor(Theme.key_chat_outAudioSeekbarFill), Theme.getColor(Theme.key_chat_outAudioSeekbarSelected));
                        } else {
                            this.seekBar.setColors(Theme.getColor(Theme.key_chat_inAudioSeekbar), Theme.getColor(Theme.key_chat_inAudioSeekbarFill), Theme.getColor(Theme.key_chat_inAudioSeekbarSelected));
                        }
                    }
                }
                if (this.currentMessageObject.type == 5) {
                    Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
                } else if (this.mediaBackground) {
                    if (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) {
                        Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_serviceText));
                    } else {
                        Theme.chat_timePaint.setColor(Theme.getColor(Theme.key_chat_mediaTimeText));
                    }
                } else if (this.currentMessageObject.isOutOwner()) {
                    Theme.chat_timePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_outTimeSelectedText : Theme.key_chat_outTimeText));
                } else {
                    Theme.chat_timePaint.setColor(Theme.getColor(isDrawSelectedBackground() ? Theme.key_chat_inTimeSelectedText : Theme.key_chat_inTimeText));
                }
                int i2 = 0;
                int i3 = 0;
                int dp3;
                if (this.currentMessageObject.isOutOwner()) {
                    if (this.mediaBackground || this.drawPinnedBottom) {
                        this.currentBackgroundDrawable = Theme.chat_msgOutMediaDrawable;
                        drawable = Theme.chat_msgOutMediaSelectedDrawable;
                        drawable2 = Theme.chat_msgOutMediaShadowDrawable;
                    } else {
                        this.currentBackgroundDrawable = Theme.chat_msgOutDrawable;
                        drawable = Theme.chat_msgOutSelectedDrawable;
                        drawable2 = Theme.chat_msgOutShadowDrawable;
                    }
                    this.backgroundDrawableLeft = (this.layoutWidth - this.backgroundWidth) - (!this.mediaBackground ? 0 : AndroidUtilities.dp(9.0f));
                    this.backgroundDrawableRight = this.backgroundWidth - (this.mediaBackground ? 0 : AndroidUtilities.dp(3.0f));
                    if (!(this.currentMessagesGroup == null || this.currentPosition.edge)) {
                        this.backgroundDrawableRight += AndroidUtilities.dp(10.0f);
                    }
                    i = this.backgroundDrawableLeft;
                    if (!this.mediaBackground && this.drawPinnedBottom) {
                        this.backgroundDrawableRight -= AndroidUtilities.dp(6.0f);
                    }
                    if (this.currentPosition != null) {
                        if ((this.currentPosition.flags & 2) == 0) {
                            this.backgroundDrawableRight += AndroidUtilities.dp(8.0f);
                        }
                        if ((this.currentPosition.flags & 1) == 0) {
                            i -= AndroidUtilities.dp(8.0f);
                            this.backgroundDrawableRight += AndroidUtilities.dp(8.0f);
                        }
                        if ((this.currentPosition.flags & 4) == 0) {
                            dp = AndroidUtilities.dp(9.0f) + 0;
                            i3 = 0 - AndroidUtilities.dp(9.0f);
                        } else {
                            dp = 0;
                            i3 = 0;
                        }
                        if ((this.currentPosition.flags & 8) == 0) {
                            dp3 = dp + AndroidUtilities.dp(9.0f);
                            i2 = i3;
                            i3 = i;
                        } else {
                            dp3 = dp;
                            i2 = i3;
                            i3 = i;
                        }
                    } else {
                        dp3 = 0;
                        i3 = i;
                    }
                    dp2 = (this.drawPinnedBottom && this.drawPinnedTop) ? 0 : this.drawPinnedBottom ? AndroidUtilities.dp(1.0f) : AndroidUtilities.dp(2.0f);
                    i = (this.drawPinnedTop || (this.drawPinnedTop && this.drawPinnedBottom)) ? 0 : AndroidUtilities.dp(1.0f);
                    i2 += i;
                    setDrawableBounds(this.currentBackgroundDrawable, i3, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                    setDrawableBounds(drawable, i3, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                    setDrawableBounds(drawable2, i3, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                } else {
                    if (this.mediaBackground || this.drawPinnedBottom) {
                        this.currentBackgroundDrawable = Theme.chat_msgInMediaDrawable;
                        drawable = Theme.chat_msgInMediaSelectedDrawable;
                        drawable2 = Theme.chat_msgInMediaShadowDrawable;
                    } else {
                        this.currentBackgroundDrawable = Theme.chat_msgInDrawable;
                        drawable = Theme.chat_msgInSelectedDrawable;
                        drawable2 = Theme.chat_msgInShadowDrawable;
                    }
                    i = (this.isChat && this.isAvatarVisible) ? 48 : 0;
                    this.backgroundDrawableLeft = AndroidUtilities.dp((float) (i + (!this.mediaBackground ? 3 : 9)));
                    this.backgroundDrawableRight = this.backgroundWidth - (this.mediaBackground ? 0 : AndroidUtilities.dp(3.0f));
                    if (this.currentMessagesGroup != null) {
                        if (!this.currentPosition.edge) {
                            this.backgroundDrawableLeft -= AndroidUtilities.dp(10.0f);
                            this.backgroundDrawableRight += AndroidUtilities.dp(10.0f);
                        }
                        if (this.currentPosition.leftSpanOffset != 0) {
                            this.backgroundDrawableLeft += (int) Math.ceil((double) ((((float) this.currentPosition.leftSpanOffset) / 1000.0f) * ((float) getGroupPhotosWidth())));
                        }
                    }
                    if (!this.mediaBackground && this.drawPinnedBottom) {
                        this.backgroundDrawableRight -= AndroidUtilities.dp(6.0f);
                        this.backgroundDrawableLeft += AndroidUtilities.dp(6.0f);
                    }
                    if (this.currentPosition != null) {
                        if ((this.currentPosition.flags & 2) == 0) {
                            this.backgroundDrawableRight += AndroidUtilities.dp(8.0f);
                        }
                        if ((this.currentPosition.flags & 1) == 0) {
                            this.backgroundDrawableLeft -= AndroidUtilities.dp(8.0f);
                            this.backgroundDrawableRight += AndroidUtilities.dp(8.0f);
                        }
                        if ((this.currentPosition.flags & 4) == 0) {
                            i2 = 0 - AndroidUtilities.dp(9.0f);
                            i3 = 0 + AndroidUtilities.dp(9.0f);
                        }
                        if ((this.currentPosition.flags & 8) == 0) {
                            dp3 = i3 + AndroidUtilities.dp(10.0f);
                            dp2 = (this.drawPinnedBottom || !this.drawPinnedTop) ? this.drawPinnedBottom ? AndroidUtilities.dp(1.0f) : AndroidUtilities.dp(2.0f) : 0;
                            i = (this.drawPinnedTop || (this.drawPinnedTop && this.drawPinnedBottom)) ? 0 : AndroidUtilities.dp(1.0f);
                            i2 += i;
                            setDrawableBounds(this.currentBackgroundDrawable, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                            setDrawableBounds(drawable, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                            setDrawableBounds(drawable2, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                        }
                    }
                    dp3 = i3;
                    if (this.drawPinnedBottom) {
                    }
                    if (this.drawPinnedBottom) {
                    }
                    if (!this.drawPinnedTop) {
                    }
                    i2 += i;
                    setDrawableBounds(this.currentBackgroundDrawable, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                    setDrawableBounds(drawable, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                    setDrawableBounds(drawable2, this.backgroundDrawableLeft, i2, this.backgroundDrawableRight, (this.layoutHeight - dp2) + dp3);
                }
                if (this.drawBackground && this.currentBackgroundDrawable != null) {
                    if (this.isHighlightedAnimated) {
                        this.currentBackgroundDrawable.draw(canvas);
                        float f = this.highlightProgress >= 300 ? 1.0f : ((float) this.highlightProgress) / 300.0f;
                        if (this.currentPosition == null) {
                            drawable.setAlpha((int) (f * 255.0f));
                            drawable.draw(canvas);
                        }
                        currentTimeMillis = System.currentTimeMillis();
                        abs = Math.abs(currentTimeMillis - this.lastHighlightProgressTime);
                        if (abs > 17) {
                            abs = 17;
                        }
                        this.highlightProgress = (int) (((long) this.highlightProgress) - abs);
                        this.lastHighlightProgressTime = currentTimeMillis;
                        if (this.highlightProgress <= 0) {
                            this.highlightProgress = 0;
                            this.isHighlightedAnimated = false;
                        }
                        invalidate();
                    } else if (!isDrawSelectedBackground() || (this.currentPosition != null && getBackground() == null)) {
                        this.currentBackgroundDrawable.draw(canvas);
                    } else {
                        drawable.setAlpha(255);
                        drawable.draw(canvas);
                    }
                    if (this.currentPosition == null || this.currentPosition.flags != 0) {
                        drawable2.draw(canvas);
                    }
                }
                drawContent(canvas);
                dp2 = AndroidUtilities.dp(5.0f);
                if (C3791b.R(getContext())) {
                    Object obj;
                    try {
                        long j = getMessageObject().getDocument().id;
                        this.cacheChecker.setValues(j, 0, 0, 0);
                        obj = j != 0 ? C5319b.m14132a(this.cacheChecker) == 1 ? 1 : null : null;
                    } catch (Exception e) {
                        obj = null;
                    }
                    if (obj == null && getMessageObject().photoThumbs != null) {
                        Iterator it = getMessageObject().photoThumbs.iterator();
                        while (it.hasNext()) {
                            PhotoSize photoSize = (PhotoSize) it.next();
                            this.cacheChecker.setValues(0, photoSize.location.local_id, photoSize.location.volume_id, 0);
                            if (C5319b.m14132a(this.cacheChecker) == 1) {
                                obj = 1;
                                continue;
                            } else {
                                obj = null;
                                continue;
                            }
                            if (obj != null) {
                                break;
                            }
                        }
                    }
                    if (obj != null) {
                        String c = C5319b.m14140c(this.cacheChecker);
                        String str = "showicon";
                        if (!TextUtils.isEmpty(c) && str.equals(c)) {
                            this.shareStartX = this.currentBackgroundDrawable.getBounds().right + AndroidUtilities.dp(1.0f);
                            this.shareStartY = (this.currentBackgroundDrawable.getBounds().top - ((int) this.slsFm.top)) + dp2;
                            this.slsRectF.right = (float) (this.shareStartX + AndroidUtilities.dp(30.0f));
                            this.slsRectF.top = (float) (this.shareStartY + dp2);
                            this.slsRectF.left = (float) this.shareStartX;
                            this.slsRectF.bottom = (float) ((this.shareStartY + AndroidUtilities.dp(30.0f)) + dp2);
                            i = (int) (((float) dp2) + this.slsRectF.bottom);
                            try {
                                canvas.drawBitmap(getNonFreeBitmap(this.slsRectF), this.slsRectF.left, this.slsRectF.top, this.slsPaint);
                            } catch (Exception e2) {
                                e2.printStackTrace();
                            }
                            canvas.save();
                        } else if (!TextUtils.isEmpty(c)) {
                            dp = AndroidUtilities.dp(5.0f);
                            i3 = AndroidUtilities.dp(5.0f);
                            a = (float) C3792d.a(12.0f, ApplicationLoader.applicationContext);
                            this.slsPaint.setTextSize(a);
                            this.slsPaint.getTextBounds(c, 0, c.length(), this.slsBounds);
                            this.slsPaint.setStyle(Style.FILL);
                            float width = (float) this.slsBounds.width();
                            this.slsPaint.setTextSize(a);
                            this.slsPaint.setColor(Color.parseColor("#80000000"));
                            this.slsPaint.setTypeface(FarsiTextView.m14168a(getContext()));
                            this.slsPaint.getFontMetrics(this.slsFm);
                            this.shareStartX = this.currentBackgroundDrawable.getBounds().right + AndroidUtilities.dp(1.0f);
                            this.shareStartY = (this.currentBackgroundDrawable.getBounds().top - ((int) this.slsFm.top)) + dp;
                            this.slsRectF.right = (width + ((float) this.shareStartX)) + ((float) AndroidUtilities.dp(10.0f));
                            this.slsRectF.top = (float) dp;
                            this.slsRectF.left = (float) this.shareStartX;
                            this.slsRectF.bottom = (float) (dp + (this.shareStartY + AndroidUtilities.dp(2.0f)));
                            canvas.drawRoundRect(this.slsRectF, (float) i3, (float) i3, this.slsPaint);
                            this.slsPaint.setColor(-1);
                            canvas.rotate(90.0f);
                            canvas.drawText(c, (float) (this.shareStartX + AndroidUtilities.dp(5.0f)), (float) this.shareStartY, this.slsPaint);
                            canvas.save();
                        }
                    }
                    i = dp2;
                } else {
                    i = dp2;
                }
                i3 = this.layoutHeight;
                if (this.drawShareButton) {
                    Theme.chat_shareDrawable.setColorFilter(this.sharePressed ? Theme.colorPressedFilter : Theme.colorFilter);
                    if (this.currentMessageObject.isOutOwner()) {
                        this.shareStartX = (this.currentBackgroundDrawable.getBounds().left - AndroidUtilities.dp(8.0f)) - Theme.chat_shareDrawable.getIntrinsicWidth();
                    } else {
                        this.shareStartX = this.currentBackgroundDrawable.getBounds().right + AndroidUtilities.dp(8.0f);
                    }
                    Drawable drawable3 = Theme.chat_shareDrawable;
                    i2 = this.shareStartX;
                    int dp4 = this.layoutHeight - AndroidUtilities.dp(41.0f);
                    this.shareStartY = dp4;
                    setDrawableBounds(drawable3, i2, dp4);
                    Theme.chat_shareDrawable.draw(canvas);
                    if (this.drwaShareGoIcon) {
                        setDrawableBounds(Theme.chat_goIconDrawable, this.shareStartX + AndroidUtilities.dp(12.0f), this.shareStartY + AndroidUtilities.dp(9.0f));
                        Theme.chat_goIconDrawable.draw(canvas);
                    } else {
                        setDrawableBounds(Theme.chat_shareIconDrawable, this.shareStartX + AndroidUtilities.dp(9.0f), this.shareStartY + AndroidUtilities.dp(9.0f));
                        Theme.chat_shareIconDrawable.draw(canvas);
                    }
                }
                dp = this.drawShareButton ? this.shareStartY : i3;
                if (this.currentChat != null && ChatObject.isChannel(this.currentChat) && this.drawFavoriteButton) {
                    Theme.favoriteDrawable.setColorFilter(this.favoritePressed ? Theme.colorPressedFilter : Theme.colorFilter);
                    this.favoriteStartY = this.shareStartY - AndroidUtilities.dp(35.0f);
                    this.favoriteStartX = this.shareStartX;
                    if (this.favoriteStartY < 0) {
                        this.favoriteStartY = this.shareStartY;
                        this.favoriteStartX = this.shareStartX + AndroidUtilities.dp(35.0f);
                    }
                    setDrawableBounds(Theme.favoriteDrawable, this.favoriteStartX, this.favoriteStartY);
                    Theme.favoriteDrawable.draw(canvas);
                    if (this.favoriteStatus == 1) {
                        setDrawableBounds(Theme.favoriteOnIconDrawable, this.favoriteStartX + AndroidUtilities.dp(9.0f), this.favoriteStartY + AndroidUtilities.dp(9.0f));
                        Theme.favoriteOnIconDrawable.draw(canvas);
                    } else {
                        setDrawableBounds(Theme.favoriteOffIconDrawable, this.favoriteStartX + AndroidUtilities.dp(9.0f), this.favoriteStartY + AndroidUtilities.dp(9.0f));
                        Theme.favoriteOffIconDrawable.draw(canvas);
                    }
                    canvas.save();
                    dp = this.favoriteStartY;
                }
                if (C3791b.g()) {
                    Object a2 = C5319b.m14134a(this.currentMessageObject.getId(), (long) this.currentMessageObject.getChannelId());
                    if (TextUtils.isEmpty(a2) || !a2.contentEquals("IFX")) {
                        i2 = AndroidUtilities.dp(1.0f);
                        dp = (dp - i) - AndroidUtilities.dp(10.0f);
                        if (!TextUtils.isEmpty(a2) && dp >= i2 && ChatObject.isChannel(this.currentChat)) {
                            String str2 = "#" + a2;
                            AndroidUtilities.dp(5.0f);
                            a = (float) C3792d.a(18.0f, ApplicationLoader.applicationContext);
                            this.slsTagPaint.setTextSize(a);
                            this.slsTagPaint.getTextBounds(str2, 0, str2.length(), this.slsTagBounds);
                            if (this.slsTagBounds.width() <= dp) {
                                this.slsTagPaint.setStyle(Style.FILL);
                                float width2 = (float) this.slsTagBounds.width();
                                this.slsTagPaint.setTextSize(a);
                                this.slsTagPaint.setColor(Color.parseColor("#00000000"));
                                this.slsTagPaint.setTypeface(TitleTextView.m14192a(getContext()));
                                this.slsTagPaint.getFontMetrics(this.slsTagFm);
                                this.tagStartX = this.currentBackgroundDrawable.getBounds().right + AndroidUtilities.dp(5.0f);
                                this.tagStartY = this.currentBackgroundDrawable.getBounds().top + i;
                                this.slsTagRectF.right = (width2 + ((float) this.tagStartX)) + ((float) AndroidUtilities.dp(10.0f));
                                this.slsTagRectF.top = (float) i;
                                this.slsTagRectF.left = (float) this.tagStartX;
                                this.slsTagRectF.bottom = (float) ((this.tagStartY + AndroidUtilities.dp(2.0f)) + AndroidUtilities.dp(5.0f));
                                canvas.save();
                                this.slsTagPaint.setColor(Color.parseColor("#64b5f6"));
                                canvas.rotate(90.0f, (float) (this.tagStartX + AndroidUtilities.dp(5.0f)), (float) this.tagStartY);
                                canvas.drawText(str2, (float) (this.tagStartX + AndroidUtilities.dp(5.0f)), (float) this.tagStartY, this.slsTagPaint);
                                canvas.restore();
                                canvas.rotate(BitmapDescriptorFactory.HUE_RED);
                                canvas.save();
                            }
                        }
                    } else {
                        setMeasuredDimension(0, 0);
                        return;
                    }
                }
                if (this.currentPosition == null) {
                    drawNamesLayout(canvas);
                }
                if ((this.drawTime || !this.mediaBackground) && !this.forceNotDrawTime) {
                    drawTimeLayout(canvas);
                }
                if (this.controlsAlpha != 1.0f || this.timeAlpha != 1.0f) {
                    currentTimeMillis = System.currentTimeMillis();
                    abs = Math.abs(this.lastControlsAlphaChangeTime - currentTimeMillis);
                    if (abs > 17) {
                        abs = 17;
                    }
                    this.totalChangeTime = abs + this.totalChangeTime;
                    if (this.totalChangeTime > 100) {
                        this.totalChangeTime = 100;
                    }
                    this.lastControlsAlphaChangeTime = currentTimeMillis;
                    if (this.controlsAlpha != 1.0f) {
                        this.controlsAlpha = AndroidUtilities.decelerateInterpolator.getInterpolation(((float) this.totalChangeTime) / 100.0f);
                    }
                    if (this.timeAlpha != 1.0f) {
                        this.timeAlpha = AndroidUtilities.decelerateInterpolator.getInterpolation(((float) this.totalChangeTime) / 100.0f);
                    }
                    invalidate();
                    if (this.forceNotDrawTime && this.currentPosition != null && this.currentPosition.last && getParent() != null) {
                        ((View) getParent()).invalidate();
                        return;
                    }
                    return;
                }
                return;
            }
            requestLayout();
        }
    }

    public void onFailedDownload(String str) {
        boolean z = this.documentAttachType == 3 || this.documentAttachType == 5;
        updateButtonState(z);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.currentMessageObject != null) {
            if (z || !this.wasLayout) {
                this.layoutWidth = getMeasuredWidth();
                this.layoutHeight = getMeasuredHeight() - this.substractBackgroundHeight;
                if (this.timeTextWidth < 0) {
                    this.timeTextWidth = AndroidUtilities.dp(10.0f);
                }
                this.timeLayout = new StaticLayout(this.currentTimeString, Theme.chat_timePaint, this.timeTextWidth + AndroidUtilities.dp(100.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                if (this.mediaBackground) {
                    if (this.currentMessageObject.isOutOwner()) {
                        this.timeX = (this.layoutWidth - this.timeWidth) - AndroidUtilities.dp(42.0f);
                    } else {
                        this.timeX = (this.isAvatarVisible ? AndroidUtilities.dp(48.0f) : 0) + ((this.backgroundWidth - AndroidUtilities.dp(4.0f)) - this.timeWidth);
                        if (!(this.currentPosition == null || this.currentPosition.leftSpanOffset == 0)) {
                            this.timeX += (int) Math.ceil((double) ((((float) this.currentPosition.leftSpanOffset) / 1000.0f) * ((float) getGroupPhotosWidth())));
                        }
                    }
                } else if (this.currentMessageObject.isOutOwner()) {
                    this.timeX = (this.layoutWidth - this.timeWidth) - AndroidUtilities.dp(38.5f);
                } else {
                    this.timeX = (this.isAvatarVisible ? AndroidUtilities.dp(48.0f) : 0) + ((this.backgroundWidth - AndroidUtilities.dp(9.0f)) - this.timeWidth);
                }
                if ((this.currentMessageObject.messageOwner.flags & 1024) != 0) {
                    this.viewsLayout = new StaticLayout(this.currentViewsString, Theme.chat_timePaint, this.viewsTextWidth, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                } else {
                    this.viewsLayout = null;
                }
                if (this.isAvatarVisible) {
                    this.avatarImage.setImageCoords(AndroidUtilities.dp(6.0f), this.avatarImage.getImageY(), AndroidUtilities.dp(42.0f), AndroidUtilities.dp(42.0f));
                }
                this.wasLayout = true;
            }
            if (this.currentMessageObject.type == 0) {
                this.textY = AndroidUtilities.dp(10.0f) + this.namesOffset;
            }
            if (this.currentMessageObject.isRoundVideo()) {
                updatePlayingMessageProgress();
            }
            if (this.documentAttachType == 3) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.seekBarX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(57.0f);
                    this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
                    this.timeAudioX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(67.0f);
                } else if (this.isChat && this.currentMessageObject.needDrawAvatar()) {
                    this.seekBarX = AndroidUtilities.dp(114.0f);
                    this.buttonX = AndroidUtilities.dp(71.0f);
                    this.timeAudioX = AndroidUtilities.dp(124.0f);
                } else {
                    this.seekBarX = AndroidUtilities.dp(66.0f);
                    this.buttonX = AndroidUtilities.dp(23.0f);
                    this.timeAudioX = AndroidUtilities.dp(76.0f);
                }
                if (this.hasLinkPreview) {
                    this.seekBarX += AndroidUtilities.dp(10.0f);
                    this.buttonX += AndroidUtilities.dp(10.0f);
                    this.timeAudioX += AndroidUtilities.dp(10.0f);
                }
                this.seekBarWaveform.setSize(this.backgroundWidth - AndroidUtilities.dp((float) ((this.hasLinkPreview ? 10 : 0) + 92)), AndroidUtilities.dp(30.0f));
                this.seekBar.setSize(this.backgroundWidth - AndroidUtilities.dp((float) ((this.hasLinkPreview ? 10 : 0) + 72)), AndroidUtilities.dp(30.0f));
                this.seekBarY = (AndroidUtilities.dp(13.0f) + this.namesOffset) + this.mediaOffsetY;
                this.buttonY = (AndroidUtilities.dp(13.0f) + this.namesOffset) + this.mediaOffsetY;
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + AndroidUtilities.dp(44.0f), this.buttonY + AndroidUtilities.dp(44.0f));
                updatePlayingMessageProgress();
            } else if (this.documentAttachType == 5) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.seekBarX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(56.0f);
                    this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
                    this.timeAudioX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(67.0f);
                } else if (this.isChat && this.currentMessageObject.needDrawAvatar()) {
                    this.seekBarX = AndroidUtilities.dp(113.0f);
                    this.buttonX = AndroidUtilities.dp(71.0f);
                    this.timeAudioX = AndroidUtilities.dp(124.0f);
                } else {
                    this.seekBarX = AndroidUtilities.dp(65.0f);
                    this.buttonX = AndroidUtilities.dp(23.0f);
                    this.timeAudioX = AndroidUtilities.dp(76.0f);
                }
                if (this.hasLinkPreview) {
                    this.seekBarX += AndroidUtilities.dp(10.0f);
                    this.buttonX += AndroidUtilities.dp(10.0f);
                    this.timeAudioX += AndroidUtilities.dp(10.0f);
                }
                this.seekBar.setSize(this.backgroundWidth - AndroidUtilities.dp((float) ((this.hasLinkPreview ? 10 : 0) + 65)), AndroidUtilities.dp(30.0f));
                this.seekBarY = (AndroidUtilities.dp(29.0f) + this.namesOffset) + this.mediaOffsetY;
                this.buttonY = (AndroidUtilities.dp(13.0f) + this.namesOffset) + this.mediaOffsetY;
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + AndroidUtilities.dp(44.0f), this.buttonY + AndroidUtilities.dp(44.0f));
                updatePlayingMessageProgress();
            } else if (this.documentAttachType == 1 && !this.drawPhotoImage) {
                if (this.currentMessageObject.isOutOwner()) {
                    this.buttonX = (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f);
                } else if (this.isChat && this.currentMessageObject.needDrawAvatar()) {
                    this.buttonX = AndroidUtilities.dp(71.0f);
                } else {
                    this.buttonX = AndroidUtilities.dp(23.0f);
                }
                if (this.hasLinkPreview) {
                    this.buttonX += AndroidUtilities.dp(10.0f);
                }
                this.buttonY = (AndroidUtilities.dp(13.0f) + this.namesOffset) + this.mediaOffsetY;
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + AndroidUtilities.dp(44.0f), this.buttonY + AndroidUtilities.dp(44.0f));
                this.photoImage.setImageCoords(this.buttonX - AndroidUtilities.dp(10.0f), this.buttonY - AndroidUtilities.dp(10.0f), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
            } else if (this.currentMessageObject.type == 12) {
                r0 = this.currentMessageObject.isOutOwner() ? (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(14.0f) : (this.isChat && this.currentMessageObject.needDrawAvatar()) ? AndroidUtilities.dp(72.0f) : AndroidUtilities.dp(23.0f);
                this.photoImage.setImageCoords(r0, AndroidUtilities.dp(13.0f) + this.namesOffset, AndroidUtilities.dp(44.0f), AndroidUtilities.dp(44.0f));
            } else {
                if (this.currentMessageObject.type == 0 && (this.hasLinkPreview || this.hasGamePreview || this.hasInvoicePreview)) {
                    r0 = this.hasGamePreview ? this.textX - AndroidUtilities.dp(10.0f) : this.hasInvoicePreview ? this.textX + AndroidUtilities.dp(1.0f) : this.textX + AndroidUtilities.dp(1.0f);
                    if (this.isSmallImage) {
                        r0 = (r0 + this.backgroundWidth) - AndroidUtilities.dp(81.0f);
                    } else {
                        r0 += this.hasInvoicePreview ? -AndroidUtilities.dp(6.3f) : AndroidUtilities.dp(10.0f);
                    }
                } else if (this.currentMessageObject.isOutOwner()) {
                    r0 = this.mediaBackground ? (this.layoutWidth - this.backgroundWidth) - AndroidUtilities.dp(3.0f) : (this.layoutWidth - this.backgroundWidth) + AndroidUtilities.dp(6.0f);
                } else {
                    r0 = (this.isChat && this.isAvatarVisible) ? AndroidUtilities.dp(63.0f) : AndroidUtilities.dp(15.0f);
                    if (!(this.currentPosition == null || this.currentPosition.edge)) {
                        r0 -= AndroidUtilities.dp(10.0f);
                    }
                }
                if (this.currentPosition != null) {
                    if ((this.currentPosition.flags & 1) == 0) {
                        r0 -= AndroidUtilities.dp(4.0f);
                    }
                    if (this.currentPosition.leftSpanOffset != 0) {
                        r0 += (int) Math.ceil((double) ((((float) this.currentPosition.leftSpanOffset) / 1000.0f) * ((float) getGroupPhotosWidth())));
                    }
                }
                this.photoImage.setImageCoords(r0, this.photoImage.getImageY(), this.photoImage.getImageWidth(), this.photoImage.getImageHeight());
                this.buttonX = (int) (((float) r0) + (((float) (this.photoImage.getImageWidth() - AndroidUtilities.dp(48.0f))) / 2.0f));
                this.buttonY = ((int) (((float) AndroidUtilities.dp(7.0f)) + (((float) (this.photoImage.getImageHeight() - AndroidUtilities.dp(48.0f))) / 2.0f))) + this.namesOffset;
                this.radialProgress.setProgressRect(this.buttonX, this.buttonY, this.buttonX + AndroidUtilities.dp(48.0f), this.buttonY + AndroidUtilities.dp(48.0f));
                this.deleteProgressRect.set((float) (this.buttonX + AndroidUtilities.dp(3.0f)), (float) (this.buttonY + AndroidUtilities.dp(3.0f)), (float) (this.buttonX + AndroidUtilities.dp(45.0f)), (float) (this.buttonY + AndroidUtilities.dp(45.0f)));
            }
        }
    }

    protected void onLongPress() {
        if (this.pressedLink instanceof URLSpanMono) {
            this.delegate.didPressedUrl(this.currentMessageObject, this.pressedLink, true);
            return;
        }
        if (this.pressedLink instanceof URLSpanNoUnderline) {
            if (((URLSpanNoUnderline) this.pressedLink).getURL().startsWith("/")) {
                this.delegate.didPressedUrl(this.currentMessageObject, this.pressedLink, true);
                return;
            }
        } else if (this.pressedLink instanceof URLSpan) {
            this.delegate.didPressedUrl(this.currentMessageObject, this.pressedLink, true);
            return;
        }
        resetPressedLink(-1);
        if (!(this.buttonPressed == 0 && this.pressedBotButton == -1)) {
            this.buttonPressed = 0;
            this.pressedBotButton = -1;
            invalidate();
        }
        if (this.instantPressed) {
            this.instantButtonPressed = false;
            this.instantPressed = false;
            if (VERSION.SDK_INT >= 21 && this.instantViewSelectorDrawable != null) {
                this.instantViewSelectorDrawable.setState(StateSet.NOTHING);
            }
            invalidate();
        }
        if (this.delegate != null) {
            this.delegate.didLongPressed(this);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.currentMessageObject != null && (this.currentMessageObject.checkLayout() || !(this.currentPosition == null || this.lastHeight == AndroidUtilities.displaySize.y))) {
            this.inLayout = true;
            MessageObject messageObject = this.currentMessageObject;
            this.currentMessageObject = null;
            setMessageObject(messageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
            this.inLayout = false;
        }
        setMeasuredDimension(MeasureSpec.getSize(i), this.totalHeight + this.keyboardHeight);
        this.lastHeight = AndroidUtilities.displaySize.y;
    }

    public void onProgressDownload(String str, float f) {
        this.radialProgress.setProgress(f, true);
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            if (this.buttonState != 4) {
                updateButtonState(false);
            }
        } else if (this.buttonState != 1) {
            updateButtonState(false);
        }
    }

    public void onProgressUpload(String str, float f, boolean z) {
        this.radialProgress.setProgress(f, true);
        if (f == 1.0f && this.currentPosition != null && SendMessagesHelper.getInstance().isSendingMessage(this.currentMessageObject.getId()) && this.buttonState == 1) {
            this.drawRadialCheckBackground = true;
            this.radialProgress.setCheckBackground(false, true);
        }
    }

    public void onProvideStructure(ViewStructure viewStructure) {
        super.onProvideStructure(viewStructure);
        if (this.allowAssistant && VERSION.SDK_INT >= 23) {
            if (this.currentMessageObject.messageText != null && this.currentMessageObject.messageText.length() > 0) {
                viewStructure.setText(this.currentMessageObject.messageText);
            } else if (this.currentMessageObject.caption != null && this.currentMessageObject.caption.length() > 0) {
                viewStructure.setText(this.currentMessageObject.caption);
            }
        }
    }

    public void onSeekBarDrag(float f) {
        if (this.currentMessageObject != null) {
            this.currentMessageObject.audioProgress = f;
            MediaController.getInstance().seekToProgress(this.currentMessageObject, f);
        }
    }

    public void onSuccessDownload(String str) {
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            updateButtonState(true);
            updateWaveform();
            return;
        }
        this.radialProgress.setProgress(1.0f, true);
        if (this.currentMessageObject.type != 0) {
            if (!this.photoNotSet || ((this.currentMessageObject.type == 8 || this.currentMessageObject.type == 5) && this.currentMessageObject.gifState != 1.0f)) {
                if ((this.currentMessageObject.type == 8 || this.currentMessageObject.type == 5) && this.currentMessageObject.gifState != 1.0f) {
                    this.photoNotSet = false;
                    this.buttonState = 2;
                    didPressedButton(true);
                } else {
                    updateButtonState(true);
                }
            }
            if (this.photoNotSet) {
                setMessageObject(this.currentMessageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
            }
        } else if (this.documentAttachType == 2 && this.currentMessageObject.gifState != 1.0f) {
            this.buttonState = 2;
            didPressedButton(true);
        } else if (this.photoNotSet) {
            setMessageObject(this.currentMessageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
        } else {
            updateButtonState(true);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.currentMessageObject == null || !this.delegate.canPerformActions()) {
            return super.onTouchEvent(motionEvent);
        }
        boolean z;
        this.disallowLongPress = false;
        boolean checkTextBlockMotionEvent = checkTextBlockMotionEvent(motionEvent);
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkOtherButtonMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkCaptionMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkAudioMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkLinkPreviewMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkGameMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkPhotoImageMotionEvent(motionEvent);
        }
        if (!checkTextBlockMotionEvent) {
            checkTextBlockMotionEvent = checkBotButtonMotionEvent(motionEvent);
        }
        if (motionEvent.getAction() == 3) {
            this.buttonPressed = 0;
            this.pressedBotButton = -1;
            this.linkPreviewPressed = false;
            this.otherPressed = false;
            this.imagePressed = false;
            this.instantButtonPressed = false;
            this.instantPressed = false;
            if (VERSION.SDK_INT >= 21 && this.instantViewSelectorDrawable != null) {
                this.instantViewSelectorDrawable.setState(StateSet.NOTHING);
            }
            resetPressedLink(-1);
            z = false;
        } else {
            z = checkTextBlockMotionEvent;
        }
        if (!this.disallowLongPress && z && motionEvent.getAction() == 0) {
            startCheckLongPress();
        }
        if (!(motionEvent.getAction() == 0 || motionEvent.getAction() == 2)) {
            cancelCheckLongPress();
        }
        if (z) {
            return z;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int max;
        if (motionEvent.getAction() != 0) {
            if (motionEvent.getAction() != 2) {
                cancelCheckLongPress();
            }
            if (this.avatarPressed) {
                if (motionEvent.getAction() == 1) {
                    this.avatarPressed = false;
                    playSoundEffect(0);
                    if (this.delegate == null) {
                        return z;
                    }
                    if (this.currentUser != null) {
                        this.delegate.didPressedUserAvatar(this, this.currentUser);
                        return z;
                    } else if (this.currentChat == null) {
                        return z;
                    } else {
                        this.delegate.didPressedChannelAvatar(this, this.currentChat, 0);
                        return z;
                    }
                } else if (motionEvent.getAction() == 3) {
                    this.avatarPressed = false;
                    return z;
                } else if (motionEvent.getAction() != 2 || !this.isAvatarVisible || this.avatarImage.isInsideImage(x, ((float) getTop()) + y)) {
                    return z;
                } else {
                    this.avatarPressed = false;
                    return z;
                }
            } else if (this.forwardNamePressed) {
                if (motionEvent.getAction() == 1) {
                    this.forwardNamePressed = false;
                    playSoundEffect(0);
                    if (this.delegate == null) {
                        return z;
                    }
                    if (this.currentForwardChannel != null) {
                        this.delegate.didPressedChannelAvatar(this, this.currentForwardChannel, this.currentMessageObject.messageOwner.fwd_from.channel_post);
                        return z;
                    } else if (this.currentForwardUser == null) {
                        return z;
                    } else {
                        this.delegate.didPressedUserAvatar(this, this.currentForwardUser);
                        return z;
                    }
                } else if (motionEvent.getAction() == 3) {
                    this.forwardNamePressed = false;
                    return z;
                } else if (motionEvent.getAction() != 2) {
                    return z;
                } else {
                    if (x >= ((float) this.forwardNameX) && x <= ((float) (this.forwardNameX + this.forwardedNameWidth)) && y >= ((float) this.forwardNameY) && y <= ((float) (this.forwardNameY + AndroidUtilities.dp(32.0f)))) {
                        return z;
                    }
                    this.forwardNamePressed = false;
                    return z;
                }
            } else if (this.forwardBotPressed) {
                if (motionEvent.getAction() == 1) {
                    this.forwardBotPressed = false;
                    playSoundEffect(0);
                    if (this.delegate == null) {
                        return z;
                    }
                    this.delegate.didPressedViaBot(this, this.currentViaBotUser != null ? this.currentViaBotUser.username : this.currentMessageObject.messageOwner.via_bot_name);
                    return z;
                } else if (motionEvent.getAction() == 3) {
                    this.forwardBotPressed = false;
                    return z;
                } else if (motionEvent.getAction() != 2) {
                    return z;
                } else {
                    if (!this.drawForwardedName || this.forwardedNameLayout[0] == null) {
                        if (x >= this.nameX + ((float) this.viaNameWidth) && x <= (this.nameX + ((float) this.viaNameWidth)) + ((float) this.viaWidth) && y >= this.nameY - ((float) AndroidUtilities.dp(4.0f)) && y <= this.nameY + ((float) AndroidUtilities.dp(20.0f))) {
                            return z;
                        }
                        this.forwardBotPressed = false;
                        return z;
                    } else if (x >= ((float) this.forwardNameX) && x <= ((float) (this.forwardNameX + this.forwardedNameWidth)) && y >= ((float) this.forwardNameY) && y <= ((float) (this.forwardNameY + AndroidUtilities.dp(32.0f)))) {
                        return z;
                    } else {
                        this.forwardBotPressed = false;
                        return z;
                    }
                }
            } else if (this.replyPressed) {
                if (motionEvent.getAction() == 1) {
                    this.replyPressed = false;
                    playSoundEffect(0);
                    if (this.delegate == null) {
                        return z;
                    }
                    this.delegate.didPressedReplyMessage(this, this.currentMessageObject.messageOwner.reply_to_msg_id);
                    return z;
                } else if (motionEvent.getAction() == 3) {
                    this.replyPressed = false;
                    return z;
                } else if (motionEvent.getAction() != 2) {
                    return z;
                } else {
                    max = (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) ? this.replyStartX + Math.max(this.replyNameWidth, this.replyTextWidth) : this.replyStartX + this.backgroundDrawableRight;
                    if (x >= ((float) this.replyStartX) && x <= ((float) max) && y >= ((float) this.replyStartY) && y <= ((float) (this.replyStartY + AndroidUtilities.dp(35.0f)))) {
                        return z;
                    }
                    this.replyPressed = false;
                    return z;
                }
            } else if (this.sharePressed) {
                if (motionEvent.getAction() == 1) {
                    this.sharePressed = false;
                    playSoundEffect(0);
                    if (this.delegate != null) {
                        this.delegate.didPressedShare(this);
                    }
                } else if (motionEvent.getAction() == 3) {
                    this.sharePressed = false;
                } else if (motionEvent.getAction() == 2 && (x < ((float) this.shareStartX) || x > ((float) (this.shareStartX + AndroidUtilities.dp(40.0f))) || y < ((float) this.shareStartY) || y > ((float) (this.shareStartY + AndroidUtilities.dp(32.0f))))) {
                    this.sharePressed = false;
                }
                invalidate();
                return z;
            } else if (this.favoritePressed) {
                if (motionEvent.getAction() == 1) {
                    Log.d("alireza", "startX : " + this.favoriteStartX + "  stgartY : " + this.favoriteStartY);
                    this.favoritePressed = false;
                    playSoundEffect(0);
                    if (Favourite.isFavouriteMessage(Long.valueOf(this.currentMessageObject.getDialogId()), (long) this.currentMessageObject.getId())) {
                        Favourite.deleteFavouriteMessage(Long.valueOf(this.currentMessageObject.getDialogId()), Long.valueOf((long) this.currentMessageObject.getId()));
                        LocaleController.getString("RemovedFromFavorite", R.string.RemovedFromFavorite);
                        this.favoriteStatus = 0;
                    } else {
                        Favourite.addFavouriteMessage(Long.valueOf(this.currentMessageObject.getDialogId()), (long) this.currentMessageObject.getId(), this.currentMessageObject);
                        LocaleController.getString("AddedToFavorite", R.string.AddedToFavorite);
                        this.favoriteStatus = 1;
                    }
                    ToastUtil.m14194a(ApplicationLoader.applicationContext, LocaleController.getString("operationSuccess", R.string.operationSuccess)).show();
                } else if (motionEvent.getAction() == 3) {
                    this.favoritePressed = false;
                } else if (motionEvent.getAction() == 2 && (x < ((float) this.favoriteStartX) || x > ((float) (this.favoriteStartX + AndroidUtilities.dp(40.0f))) || y < ((float) this.favoriteStartY) || y > ((float) (this.favoriteStartY + AndroidUtilities.dp(32.0f))))) {
                    this.favoritePressed = false;
                }
                invalidate();
                return z;
            } else if (this.slsPressed) {
                if (!C3791b.U(ApplicationLoader.applicationContext) || motionEvent.getAction() != 1) {
                    return z;
                }
                this.slsPressed = false;
                playSoundEffect(0);
                Builder builder = new Builder(getContext());
                builder.setTitle(TtmlNode.ANONYMOUS_REGION_ID);
                r0 = C5319b.m14141d(this.cacheChecker);
                if (TextUtils.isEmpty(r0)) {
                    r0 = TtmlNode.ANONYMOUS_REGION_ID;
                }
                builder.setMessage(r0).setCancelable(true).setPositiveButton("گرفتم", new C40002());
                builder.create().show();
                return z;
            } else if (!this.slsTagPressed) {
                return z;
            } else {
                if (motionEvent.getAction() == 1) {
                    String a;
                    Iterator it;
                    PhotoSize photoSize;
                    Object obj;
                    CharSequence charSequence;
                    this.slsTagPressed = false;
                    playSoundEffect(0);
                    String str = TtmlNode.ANONYMOUS_REGION_ID;
                    try {
                        long j = getMessageObject().getDocument().id;
                        this.cacheChecker.setValues(j, 0, 0, 0);
                        if (j != 0) {
                            a = C5319b.m14134a(this.currentMessageObject.getId(), (long) this.currentMessageObject.getChannelId());
                            if (getMessageObject().photoThumbs == null) {
                                it = getMessageObject().photoThumbs.iterator();
                                str = a;
                                while (it.hasNext()) {
                                    photoSize = (PhotoSize) it.next();
                                    this.cacheChecker.setValues(0, photoSize.location.local_id, photoSize.location.volume_id, 0);
                                    r0 = C5319b.m14134a(this.currentMessageObject.getId(), (long) this.currentMessageObject.getChannelId());
                                    if (!TextUtils.isEmpty(r0)) {
                                        obj = str;
                                    }
                                    charSequence = r0;
                                }
                            } else {
                                str = a;
                            }
                            this.delegate.didTagPressed(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    a = str;
                    if (getMessageObject().photoThumbs == null) {
                        str = a;
                    } else {
                        it = getMessageObject().photoThumbs.iterator();
                        str = a;
                        while (it.hasNext()) {
                            photoSize = (PhotoSize) it.next();
                            this.cacheChecker.setValues(0, photoSize.location.local_id, photoSize.location.volume_id, 0);
                            r0 = C5319b.m14134a(this.currentMessageObject.getId(), (long) this.currentMessageObject.getChannelId());
                            if (!TextUtils.isEmpty(r0)) {
                                obj = str;
                            }
                            charSequence = r0;
                        }
                    }
                    this.delegate.didTagPressed(str);
                } else if (motionEvent.getAction() == 3) {
                    this.slsTagPressed = false;
                }
                invalidate();
                return z;
            }
        } else if (this.delegate != null && !this.delegate.canPerformActions()) {
            return z;
        } else {
            if (this.isAvatarVisible && this.avatarImage.isInsideImage(x, ((float) getTop()) + y)) {
                this.avatarPressed = true;
                z = true;
            } else if (this.drawForwardedName && this.forwardedNameLayout[0] != null && x >= ((float) this.forwardNameX) && x <= ((float) (this.forwardNameX + this.forwardedNameWidth)) && y >= ((float) this.forwardNameY) && y <= ((float) (this.forwardNameY + AndroidUtilities.dp(32.0f)))) {
                if (this.viaWidth == 0 || x < ((float) ((this.forwardNameX + this.viaNameWidth) + AndroidUtilities.dp(4.0f)))) {
                    this.forwardNamePressed = true;
                } else {
                    this.forwardBotPressed = true;
                }
                z = true;
            } else if (this.drawNameLayout && this.nameLayout != null && this.viaWidth != 0 && x >= this.nameX + ((float) this.viaNameWidth) && x <= (this.nameX + ((float) this.viaNameWidth)) + ((float) this.viaWidth) && y >= this.nameY - ((float) AndroidUtilities.dp(4.0f)) && y <= this.nameY + ((float) AndroidUtilities.dp(20.0f))) {
                this.forwardBotPressed = true;
                z = true;
            } else if (this.currentMessageObject.isReply() && x >= ((float) this.replyStartX) && x <= ((float) (this.replyStartX + Math.max(this.replyNameWidth, this.replyTextWidth))) && y >= ((float) this.replyStartY) && y <= ((float) (this.replyStartY + AndroidUtilities.dp(35.0f)))) {
                this.replyPressed = true;
                z = true;
            } else if (this.drawShareButton && x >= ((float) this.shareStartX) && x <= ((float) (this.shareStartX + AndroidUtilities.dp(40.0f))) && y >= ((float) this.shareStartY) && y <= ((float) (this.shareStartY + AndroidUtilities.dp(32.0f)))) {
                this.sharePressed = true;
                invalidate();
                z = true;
            } else if (this.drawFavoriteButton && x >= ((float) this.favoriteStartX) && x <= ((float) (this.favoriteStartX + AndroidUtilities.dp(40.0f))) && y >= ((float) this.favoriteStartY) && y <= ((float) (this.favoriteStartY + AndroidUtilities.dp(32.0f)))) {
                this.favoritePressed = true;
                invalidate();
                z = true;
            } else if (this.slsRectF.left < x && x < this.slsRectF.right && this.slsRectF.top < y && y < this.slsRectF.bottom) {
                this.slsPressed = true;
                z = true;
            } else if (this.slsTagRectF.left < x && x < this.slsTagRectF.right && this.slsTagRectF.top < y && y < this.slsTagRectF.bottom + (this.slsTagRectF.right - this.slsTagRectF.left)) {
                this.slsTagPressed = true;
                z = true;
            } else if (this.replyNameLayout != null) {
                max = (this.currentMessageObject.type == 13 || this.currentMessageObject.type == 5) ? this.replyStartX + Math.max(this.replyNameWidth, this.replyTextWidth) : this.replyStartX + this.backgroundDrawableRight;
                if (x >= ((float) this.replyStartX) && x <= ((float) max) && y >= ((float) this.replyStartY) && y <= ((float) (this.replyStartY + AndroidUtilities.dp(35.0f)))) {
                    this.replyPressed = true;
                    z = true;
                }
            }
            if (!z) {
                return z;
            }
            startCheckLongPress();
            return z;
        }
    }

    public void requestLayout() {
        if (!this.inLayout) {
            super.requestLayout();
        }
    }

    public void setAllowAssistant(boolean z) {
        this.allowAssistant = z;
    }

    public void setCheckPressed(boolean z, boolean z2) {
        this.isCheckPressed = z;
        this.isPressed = z2;
        updateRadialProgressBackground(true);
        if (this.useSeekBarWaweform) {
            this.seekBarWaveform.setSelected(isDrawSelectedBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectedBackground());
        }
        invalidate();
    }

    public void setDelegate(ChatMessageCellDelegate chatMessageCellDelegate) {
        this.delegate = chatMessageCellDelegate;
    }

    public void setFullyDraw(boolean z) {
        this.fullyDraw = z;
    }

    public void setHighlighted(boolean z) {
        if (this.isHighlighted != z) {
            this.isHighlighted = z;
            if (!this.isHighlighted) {
                this.lastHighlightProgressTime = System.currentTimeMillis();
                this.isHighlightedAnimated = true;
                this.highlightProgress = 300;
            }
            updateRadialProgressBackground(true);
            if (this.useSeekBarWaweform) {
                this.seekBarWaveform.setSelected(isDrawSelectedBackground());
            } else {
                this.seekBar.setSelected(isDrawSelectedBackground());
            }
            invalidate();
        }
    }

    public void setHighlightedAnimated() {
        this.isHighlightedAnimated = true;
        this.highlightProgress = 1000;
        this.lastHighlightProgressTime = System.currentTimeMillis();
        invalidate();
    }

    public void setHighlightedText(String str) {
        if (this.currentMessageObject.messageOwner.message != null && this.currentMessageObject != null && this.currentMessageObject.type == 0 && !TextUtils.isEmpty(this.currentMessageObject.messageText) && str != null) {
            int indexOf = TextUtils.indexOf(this.currentMessageObject.messageOwner.message.toLowerCase(), str.toLowerCase());
            if (indexOf != -1) {
                int length = indexOf + str.length();
                int i = 0;
                while (i < this.currentMessageObject.textLayoutBlocks.size()) {
                    TextLayoutBlock textLayoutBlock = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i);
                    if (indexOf < textLayoutBlock.charactersOffset || indexOf >= textLayoutBlock.charactersOffset + textLayoutBlock.textLayout.getText().length()) {
                        i++;
                    } else {
                        this.linkSelectionBlockNum = i;
                        resetUrlPaths(true);
                        try {
                            Path obtainNewUrlPath = obtainNewUrlPath(true);
                            int length2 = textLayoutBlock.textLayout.getText().length();
                            obtainNewUrlPath.setCurrentLayout(textLayoutBlock.textLayout, indexOf, BitmapDescriptorFactory.HUE_RED);
                            textLayoutBlock.textLayout.getSelectionPath(indexOf, length - textLayoutBlock.charactersOffset, obtainNewUrlPath);
                            if (length >= textLayoutBlock.charactersOffset + length2) {
                                for (indexOf = i + 1; indexOf < this.currentMessageObject.textLayoutBlocks.size(); indexOf++) {
                                    TextLayoutBlock textLayoutBlock2 = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(indexOf);
                                    int length3 = textLayoutBlock2.textLayout.getText().length();
                                    Path obtainNewUrlPath2 = obtainNewUrlPath(true);
                                    obtainNewUrlPath2.setCurrentLayout(textLayoutBlock2.textLayout, 0, (float) textLayoutBlock2.height);
                                    textLayoutBlock2.textLayout.getSelectionPath(0, length - textLayoutBlock2.charactersOffset, obtainNewUrlPath2);
                                    if (length < (textLayoutBlock.charactersOffset + length3) - 1) {
                                        break;
                                    }
                                }
                            }
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        invalidate();
                        return;
                    }
                }
            } else if (!this.urlPathSelection.isEmpty()) {
                this.linkSelectionBlockNum = -1;
                resetUrlPaths(true);
                invalidate();
            }
        } else if (!this.urlPathSelection.isEmpty()) {
            this.linkSelectionBlockNum = -1;
            resetUrlPaths(true);
            invalidate();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setMessageObject(org.telegram.messenger.MessageObject r39, org.telegram.messenger.MessageObject.GroupedMessages r40, boolean r41, boolean r42) {
        /*
        r38 = this;
        r4 = r39.checkLayout();
        if (r4 != 0) goto L_0x0016;
    L_0x0006:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x001b;
    L_0x000c:
        r0 = r38;
        r4 = r0.lastHeight;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        if (r4 == r5) goto L_0x001b;
    L_0x0016:
        r4 = 0;
        r0 = r38;
        r0.currentMessageObject = r4;
    L_0x001b:
        r4 = -1;
        r0 = r38;
        r0.favoriteStatus = r4;
        if (r39 == 0) goto L_0x0034;
    L_0x0022:
        r4 = r39.getDialogId();
        r6 = r39.getId();
        r7 = new org.telegram.ui.Cells.ChatMessageCell$3;
        r0 = r38;
        r7.<init>(r4, r6);
        r7.start();
    L_0x0034:
        r0 = r38;
        r4 = r0.currentMessageObject;
        if (r4 == 0) goto L_0x0048;
    L_0x003a:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.getId();
        r5 = r39.getId();
        if (r4 == r5) goto L_0x07d6;
    L_0x0048:
        r4 = 1;
        r16 = r4;
    L_0x004b:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r0 = r39;
        if (r4 != r0) goto L_0x0059;
    L_0x0053:
        r0 = r39;
        r4 = r0.forceUpdate;
        if (r4 == 0) goto L_0x07db;
    L_0x0059:
        r4 = 1;
        r5 = r4;
    L_0x005b:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r0 = r39;
        if (r4 != r0) goto L_0x07df;
    L_0x0063:
        r4 = r38.isUserDataChanged();
        if (r4 != 0) goto L_0x006f;
    L_0x0069:
        r0 = r38;
        r4 = r0.photoNotSet;
        if (r4 == 0) goto L_0x07df;
    L_0x006f:
        r4 = 1;
        r17 = r4;
    L_0x0072:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r0 = r40;
        if (r0 == r4) goto L_0x07e4;
    L_0x007a:
        r4 = 1;
    L_0x007b:
        if (r4 != 0) goto L_0x00a1;
    L_0x007d:
        if (r40 == 0) goto L_0x00a1;
    L_0x007f:
        r0 = r40;
        r4 = r0.messages;
        r4 = r4.size();
        r6 = 1;
        if (r4 <= r6) goto L_0x07e7;
    L_0x008a:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.positions;
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.get(r6);
        r4 = (org.telegram.messenger.MessageObject.GroupedMessagePosition) r4;
    L_0x009a:
        r0 = r38;
        r6 = r0.currentPosition;
        if (r4 == r6) goto L_0x07ea;
    L_0x00a0:
        r4 = 1;
    L_0x00a1:
        if (r5 != 0) goto L_0x00bd;
    L_0x00a3:
        if (r17 != 0) goto L_0x00bd;
    L_0x00a5:
        if (r4 != 0) goto L_0x00bd;
    L_0x00a7:
        r4 = r38.isPhotoDataChanged(r39);
        if (r4 != 0) goto L_0x00bd;
    L_0x00ad:
        r0 = r38;
        r4 = r0.pinnedBottom;
        r0 = r41;
        if (r4 != r0) goto L_0x00bd;
    L_0x00b5:
        r0 = r38;
        r4 = r0.pinnedTop;
        r0 = r42;
        if (r4 == r0) goto L_0x3475;
    L_0x00bd:
        r0 = r41;
        r1 = r38;
        r1.pinnedBottom = r0;
        r0 = r42;
        r1 = r38;
        r1.pinnedTop = r0;
        r4 = -2;
        r0 = r38;
        r0.lastTime = r4;
        r4 = 0;
        r0 = r38;
        r0.isHighlightedAnimated = r4;
        r4 = -1;
        r0 = r38;
        r0.widthBeforeNewTimeLine = r4;
        r0 = r39;
        r1 = r38;
        r1.currentMessageObject = r0;
        r0 = r40;
        r1 = r38;
        r1.currentMessagesGroup = r0;
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        if (r4 == 0) goto L_0x07ed;
    L_0x00ea:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.posArray;
        r4 = r4.size();
        r6 = 1;
        if (r4 <= r6) goto L_0x07ed;
    L_0x00f7:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.positions;
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.get(r6);
        r4 = (org.telegram.messenger.MessageObject.GroupedMessagePosition) r4;
        r0 = r38;
        r0.currentPosition = r4;
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 != 0) goto L_0x0116;
    L_0x0111:
        r4 = 0;
        r0 = r38;
        r0.currentMessagesGroup = r4;
    L_0x0116:
        r0 = r38;
        r4 = r0.pinnedTop;
        if (r4 == 0) goto L_0x07f9;
    L_0x011c:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x012c;
    L_0x0122:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.flags;
        r4 = r4 & 4;
        if (r4 == 0) goto L_0x07f9;
    L_0x012c:
        r4 = 1;
    L_0x012d:
        r0 = r38;
        r0.drawPinnedTop = r4;
        r0 = r38;
        r4 = r0.pinnedBottom;
        if (r4 == 0) goto L_0x07fc;
    L_0x0137:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x0147;
    L_0x013d:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.flags;
        r4 = r4 & 8;
        if (r4 == 0) goto L_0x07fc;
    L_0x0147:
        r4 = 1;
    L_0x0148:
        r0 = r38;
        r0.drawPinnedBottom = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setCrossfadeWithOldImage(r6);
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.send_state;
        r0 = r38;
        r0.lastSendState = r4;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.destroyTime;
        r0 = r38;
        r0.lastDeleteDate = r4;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.views;
        r0 = r38;
        r0.lastViewsCount = r4;
        r4 = 0;
        r0 = r38;
        r0.isPressed = r4;
        r4 = 1;
        r0 = r38;
        r0.isCheckPressed = r4;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x07ff;
    L_0x0182:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x07ff;
    L_0x0188:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x07ff;
    L_0x018e:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x019c;
    L_0x0194:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.edge;
        if (r4 == 0) goto L_0x07ff;
    L_0x019c:
        r4 = 1;
    L_0x019d:
        r0 = r38;
        r0.isAvatarVisible = r4;
        r4 = 0;
        r0 = r38;
        r0.wasLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.drwaShareGoIcon = r4;
        r4 = 0;
        r0 = r38;
        r0.groupPhotoInvisible = r4;
        r4 = r38.checkNeedDrawShareButton(r39);
        r0 = r38;
        r0.drawShareButton = r4;
        r0 = r38;
        r4 = r0.drawShareButton;
        r0 = r38;
        r0.drawFavoriteButton = r4;
        r4 = 0;
        r0 = r38;
        r0.replyNameLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.adminLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.replyTextLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.replyNameWidth = r4;
        r4 = 0;
        r0 = r38;
        r0.replyTextWidth = r4;
        r4 = 0;
        r0 = r38;
        r0.viaWidth = r4;
        r4 = 0;
        r0 = r38;
        r0.viaNameWidth = r4;
        r4 = 0;
        r0 = r38;
        r0.currentReplyPhoto = r4;
        r4 = 0;
        r0 = r38;
        r0.currentUser = r4;
        r4 = 0;
        r0 = r38;
        r0.currentChat = r4;
        r4 = 0;
        r0 = r38;
        r0.currentViaBotUser = r4;
        r4 = 0;
        r0 = r38;
        r0.drawNameLayout = r4;
        r0 = r38;
        r4 = r0.scheduledInvalidate;
        if (r4 == 0) goto L_0x020e;
    L_0x0202:
        r0 = r38;
        r4 = r0.invalidateRunnable;
        org.telegram.messenger.AndroidUtilities.cancelRunOnUIThread(r4);
        r4 = 0;
        r0 = r38;
        r0.scheduledInvalidate = r4;
    L_0x020e:
        r4 = -1;
        r0 = r38;
        r0.resetPressedLink(r4);
        r4 = 0;
        r0 = r39;
        r0.forceUpdate = r4;
        r4 = 0;
        r0 = r38;
        r0.drawPhotoImage = r4;
        r4 = 0;
        r0 = r38;
        r0.hasLinkPreview = r4;
        r4 = 0;
        r0 = r38;
        r0.hasOldCaptionPreview = r4;
        r4 = 0;
        r0 = r38;
        r0.hasGamePreview = r4;
        r4 = 0;
        r0 = r38;
        r0.hasInvoicePreview = r4;
        r4 = 0;
        r0 = r38;
        r0.instantButtonPressed = r4;
        r0 = r38;
        r0.instantPressed = r4;
        r4 = android.os.Build.VERSION.SDK_INT;
        r6 = 21;
        if (r4 < r6) goto L_0x0259;
    L_0x0241:
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        if (r4 == 0) goto L_0x0259;
    L_0x0247:
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        r6 = 0;
        r7 = 0;
        r4.setVisible(r6, r7);
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        r6 = android.util.StateSet.NOTHING;
        r4.setState(r6);
    L_0x0259:
        r4 = 0;
        r0 = r38;
        r0.linkPreviewPressed = r4;
        r4 = 0;
        r0 = r38;
        r0.buttonPressed = r4;
        r4 = -1;
        r0 = r38;
        r0.pressedBotButton = r4;
        r4 = 0;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r4 = 0;
        r0 = r38;
        r0.mediaOffsetY = r4;
        r4 = 0;
        r0 = r38;
        r0.documentAttachType = r4;
        r4 = 0;
        r0 = r38;
        r0.documentAttach = r4;
        r4 = 0;
        r0 = r38;
        r0.descriptionLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.titleLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.videoInfoLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.photosCountLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.siteNameLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.authorLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.captionLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.docTitleLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.drawImageButton = r4;
        r4 = 0;
        r0 = r38;
        r0.currentPhotoObject = r4;
        r4 = 0;
        r0 = r38;
        r0.currentPhotoObjectThumb = r4;
        r4 = 0;
        r0 = r38;
        r0.currentPhotoFilter = r4;
        r4 = 0;
        r0 = r38;
        r0.infoLayout = r4;
        r4 = 0;
        r0 = r38;
        r0.cancelLoading = r4;
        r4 = -1;
        r0 = r38;
        r0.buttonState = r4;
        r4 = 0;
        r0 = r38;
        r0.currentUrl = r4;
        r4 = 0;
        r0 = r38;
        r0.photoNotSet = r4;
        r4 = 1;
        r0 = r38;
        r0.drawBackground = r4;
        r4 = 0;
        r0 = r38;
        r0.drawName = r4;
        r4 = 0;
        r0 = r38;
        r0.useSeekBarWaweform = r4;
        r4 = 0;
        r0 = r38;
        r0.drawInstantView = r4;
        r4 = 0;
        r0 = r38;
        r0.drawInstantViewType = r4;
        r4 = 0;
        r0 = r38;
        r0.drawForwardedName = r4;
        r4 = 0;
        r0 = r38;
        r0.mediaBackground = r4;
        r30 = 0;
        r4 = 0;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setForceLoading(r6);
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setNeedsQualityThumb(r6);
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setShouldGenerateQualityThumb(r6);
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setAllowDecodeSingleFrame(r6);
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r4.setParentMessageObject(r6);
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4.setRoundRadius(r6);
        r4 = org.telegram.messenger.ApplicationLoader.applicationContext;
        r6 = "plusconfig";
        r7 = 0;
        r4 = r4.getSharedPreferences(r6, r7);
        r6 = "showEditedMark";
        r7 = 1;
        r4 = r4.getBoolean(r6, r7);
        r0 = r38;
        r0.showEditedMark = r4;
        if (r5 == 0) goto L_0x0357;
    L_0x0348:
        r4 = 0;
        r0 = r38;
        r0.firstVisibleBlockNum = r4;
        r4 = 0;
        r0 = r38;
        r0.lastVisibleBlockNum = r4;
        r4 = 1;
        r0 = r38;
        r0.needNewVisiblePart = r4;
    L_0x0357:
        r0 = r39;
        r4 = r0.type;
        if (r4 != 0) goto L_0x1abb;
    L_0x035d:
        r4 = 1;
        r0 = r38;
        r0.drawForwardedName = r4;
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x0828;
    L_0x0368:
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x0802;
    L_0x036e:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x0802;
    L_0x0374:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x0802;
    L_0x037a:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r5 = 1123287040; // 0x42f40000 float:122.0 double:5.54977537E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r5 = 1;
        r0 = r38;
        r0.drawName = r5;
        r18 = r4;
    L_0x038c:
        r0 = r18;
        r1 = r38;
        r1.availableTimeWidth = r0;
        r38.measureTime(r39);
        r0 = r38;
        r4 = r0.timeWidth;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r5 = r39.isOutOwner();
        if (r5 == 0) goto L_0x3577;
    L_0x03a6:
        r5 = 1101266944; // 0x41a40000 float:20.5 double:5.44098164E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r19 = r4;
    L_0x03af:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGame;
        if (r4 == 0) goto L_0x0885;
    L_0x03b9:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.game;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_game;
        if (r4 == 0) goto L_0x0885;
    L_0x03c5:
        r4 = 1;
    L_0x03c6:
        r0 = r38;
        r0.hasGamePreview = r4;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
        r0 = r38;
        r0.hasInvoicePreview = r4;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
        if (r4 == 0) goto L_0x0888;
    L_0x03e0:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_webPage;
        if (r4 == 0) goto L_0x0888;
    L_0x03ec:
        r4 = 1;
    L_0x03ed:
        r0 = r38;
        r0.hasLinkPreview = r4;
        r0 = r38;
        r4 = r0.hasLinkPreview;
        if (r4 == 0) goto L_0x088b;
    L_0x03f7:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.cached_page;
        if (r4 == 0) goto L_0x088b;
    L_0x0403:
        r4 = 1;
    L_0x0404:
        r0 = r38;
        r0.drawInstantView = r4;
        r6 = 0;
        r0 = r38;
        r4 = r0.hasLinkPreview;
        if (r4 == 0) goto L_0x088e;
    L_0x040f:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.site_name;
    L_0x0419:
        r0 = r38;
        r5 = r0.hasLinkPreview;
        if (r5 == 0) goto L_0x0891;
    L_0x041f:
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.webpage;
        r5 = r5.type;
    L_0x0429:
        r0 = r38;
        r7 = r0.drawInstantView;
        if (r7 != 0) goto L_0x08c7;
    L_0x042f:
        r4 = "telegram_channel";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x0894;
    L_0x0438:
        r4 = 1;
        r0 = r38;
        r0.drawInstantView = r4;
        r4 = 1;
        r0 = r38;
        r0.drawInstantViewType = r4;
        r5 = r6;
    L_0x0443:
        r4 = android.os.Build.VERSION.SDK_INT;
        r6 = 21;
        if (r4 < r6) goto L_0x04aa;
    L_0x0449:
        r0 = r38;
        r4 = r0.drawInstantView;
        if (r4 == 0) goto L_0x04aa;
    L_0x044f:
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        if (r4 != 0) goto L_0x0983;
    L_0x0455:
        r4 = new android.graphics.Paint;
        r6 = 1;
        r4.<init>(r6);
        r6 = -1;
        r4.setColor(r6);
        r6 = new org.telegram.ui.Cells.ChatMessageCell$4;
        r0 = r38;
        r6.<init>(r4);
        r7 = new android.content.res.ColorStateList;
        r4 = 1;
        r8 = new int[r4][];
        r4 = 0;
        r9 = android.util.StateSet.WILD_CARD;
        r8[r4] = r9;
        r4 = 1;
        r9 = new int[r4];
        r10 = 0;
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.isOutOwner();
        if (r4 == 0) goto L_0x097e;
    L_0x047e:
        r4 = "chat_outPreviewInstantText";
    L_0x0481:
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r11 = 1610612735; // 0x5fffffff float:3.6893486E19 double:7.95748421E-315;
        r4 = r4 & r11;
        r9[r10] = r4;
        r7.<init>(r8, r9);
        r4 = new android.graphics.drawable.RippleDrawable;
        r8 = 0;
        r4.<init>(r7, r8, r6);
        r0 = r38;
        r0.instantViewSelectorDrawable = r4;
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        r0 = r38;
        r4.setCallback(r0);
    L_0x04a1:
        r0 = r38;
        r4 = r0.instantViewSelectorDrawable;
        r6 = 1;
        r7 = 0;
        r4.setVisible(r6, r7);
    L_0x04aa:
        r0 = r18;
        r1 = r38;
        r1.backgroundWidth = r0;
        r0 = r38;
        r4 = r0.hasLinkPreview;
        if (r4 != 0) goto L_0x04cc;
    L_0x04b6:
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 != 0) goto L_0x04cc;
    L_0x04bc:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 != 0) goto L_0x04cc;
    L_0x04c2:
        r0 = r39;
        r4 = r0.lastLineWidth;
        r4 = r18 - r4;
        r0 = r19;
        if (r4 >= r0) goto L_0x09a6;
    L_0x04cc:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r39;
        r6 = r0.lastLineWidth;
        r4 = java.lang.Math.max(r4, r6);
        r6 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.backgroundWidth = r4;
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r38;
        r6 = r0.timeWidth;
        r7 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r6 = r6 + r7;
        r4 = java.lang.Math.max(r4, r6);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x04fa:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r38.setMessageObjectInternal(r39);
        r0 = r39;
        r6 = r0.textWidth;
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 != 0) goto L_0x051c;
    L_0x0516:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x09e5;
    L_0x051c:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
    L_0x0522:
        r4 = r4 + r6;
        r0 = r38;
        r0.backgroundWidth = r4;
        r0 = r39;
        r4 = r0.textHeight;
        r6 = 1100742656; // 0x419c0000 float:19.5 double:5.43839131E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r6 = r0.namesOffset;
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x0550;
    L_0x0541:
        r0 = r38;
        r4 = r0.namesOffset;
        r6 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r0 = r38;
        r0.namesOffset = r4;
    L_0x0550:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r38;
        r6 = r0.nameWidth;
        r4 = java.lang.Math.max(r4, r6);
        r0 = r38;
        r6 = r0.forwardedNameWidth;
        r4 = java.lang.Math.max(r4, r6);
        r0 = r38;
        r6 = r0.replyNameWidth;
        r4 = java.lang.Math.max(r4, r6);
        r0 = r38;
        r6 = r0.replyTextWidth;
        r31 = java.lang.Math.max(r4, r6);
        r33 = 0;
        r0 = r38;
        r4 = r0.hasLinkPreview;
        if (r4 != 0) goto L_0x0588;
    L_0x057c:
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 != 0) goto L_0x0588;
    L_0x0582:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x1aa4;
    L_0x0588:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x09f5;
    L_0x058e:
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x09e8;
    L_0x0594:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x09e8;
    L_0x059a:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.isOut();
        if (r4 != 0) goto L_0x09e8;
    L_0x05a4:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r6 = 1124335616; // 0x43040000 float:132.0 double:5.554956023E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
    L_0x05af:
        r0 = r38;
        r6 = r0.drawShareButton;
        if (r6 == 0) goto L_0x3571;
    L_0x05b5:
        r6 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r6 = r4;
    L_0x05bd:
        r0 = r38;
        r4 = r0.hasLinkPreview;
        if (r4 == 0) goto L_0x0a3b;
    L_0x05c3:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = (org.telegram.tgnet.TLRPC$TL_webPage) r4;
        r15 = r4.site_name;
        r14 = r4.title;
        r13 = r4.author;
        r12 = r4.description;
        r11 = r4.photo;
        r9 = 0;
        r10 = r4.document;
        r7 = r4.type;
        r8 = r4.duration;
        if (r15 == 0) goto L_0x356d;
    L_0x05e0:
        if (r11 == 0) goto L_0x356d;
    L_0x05e2:
        r4 = r15.toLowerCase();
        r20 = "instagram";
        r0 = r20;
        r4 = r4.equals(r0);
        if (r4 == 0) goto L_0x356d;
    L_0x05f1:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.y;
        r4 = r4 / 3;
        r0 = r38;
        r6 = r0.currentMessageObject;
        r6 = r6.textWidth;
        r6 = java.lang.Math.max(r4, r6);
        r20 = r6;
    L_0x0603:
        if (r5 != 0) goto L_0x0a35;
    L_0x0605:
        r0 = r38;
        r4 = r0.drawInstantView;
        if (r4 != 0) goto L_0x0a35;
    L_0x060b:
        if (r10 != 0) goto L_0x0a35;
    L_0x060d:
        if (r7 == 0) goto L_0x0a35;
    L_0x060f:
        r4 = "app";
        r4 = r7.equals(r4);
        if (r4 != 0) goto L_0x062a;
    L_0x0618:
        r4 = "profile";
        r4 = r7.equals(r4);
        if (r4 != 0) goto L_0x062a;
    L_0x0621:
        r4 = "article";
        r4 = r7.equals(r4);
        if (r4 == 0) goto L_0x0a35;
    L_0x062a:
        r6 = 1;
    L_0x062b:
        if (r5 != 0) goto L_0x0a38;
    L_0x062d:
        r0 = r38;
        r4 = r0.drawInstantView;
        if (r4 != 0) goto L_0x0a38;
    L_0x0633:
        if (r10 != 0) goto L_0x0a38;
    L_0x0635:
        if (r12 == 0) goto L_0x0a38;
    L_0x0637:
        if (r7 == 0) goto L_0x0a38;
    L_0x0639:
        r4 = "app";
        r4 = r7.equals(r4);
        if (r4 != 0) goto L_0x0654;
    L_0x0642:
        r4 = "profile";
        r4 = r7.equals(r4);
        if (r4 != 0) goto L_0x0654;
    L_0x064b:
        r4 = "article";
        r4 = r7.equals(r4);
        if (r4 == 0) goto L_0x0a38;
    L_0x0654:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.photoThumbs;
        if (r4 == 0) goto L_0x0a38;
    L_0x065c:
        r4 = 1;
    L_0x065d:
        r0 = r38;
        r0.isSmallImage = r4;
        r21 = r6;
        r22 = r8;
        r23 = r9;
        r24 = r10;
        r25 = r11;
        r26 = r12;
        r27 = r13;
        r28 = r14;
        r5 = r15;
        r6 = r20;
        r20 = r7;
    L_0x0676:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x0ab5;
    L_0x067c:
        r4 = 0;
        r29 = r4;
    L_0x067f:
        r32 = 3;
        r12 = 0;
        r35 = r6 - r29;
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.photoThumbs;
        if (r4 != 0) goto L_0x0696;
    L_0x068c:
        if (r25 == 0) goto L_0x0696;
    L_0x068e:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r6 = 1;
        r4.generateThumbs(r6);
    L_0x0696:
        if (r5 == 0) goto L_0x3567;
    L_0x0698:
        r4 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x0abf }
        r4 = r4.measureText(r5);	 Catch:{ Exception -> 0x0abf }
        r6 = (double) r4;	 Catch:{ Exception -> 0x0abf }
        r6 = java.lang.Math.ceil(r6);	 Catch:{ Exception -> 0x0abf }
        r7 = (int) r6;	 Catch:{ Exception -> 0x0abf }
        r4 = new android.text.StaticLayout;	 Catch:{ Exception -> 0x0abf }
        r6 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x0abf }
        r0 = r35;
        r7 = java.lang.Math.min(r7, r0);	 Catch:{ Exception -> 0x0abf }
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x0abf }
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x0abf }
        r0 = r38;
        r0.siteNameLayout = r4;	 Catch:{ Exception -> 0x0abf }
        r0 = r38;
        r4 = r0.siteNameLayout;	 Catch:{ Exception -> 0x0abf }
        r0 = r38;
        r6 = r0.siteNameLayout;	 Catch:{ Exception -> 0x0abf }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x0abf }
        r6 = r6 + -1;
        r4 = r4.getLineBottom(r6);	 Catch:{ Exception -> 0x0abf }
        r0 = r38;
        r6 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0abf }
        r6 = r6 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r6;	 Catch:{ Exception -> 0x0abf }
        r0 = r38;
        r6 = r0.totalHeight;	 Catch:{ Exception -> 0x0abf }
        r6 = r6 + r4;
        r0 = r38;
        r0.totalHeight = r6;	 Catch:{ Exception -> 0x0abf }
        r6 = r12 + r4;
        r0 = r38;
        r4 = r0.siteNameLayout;	 Catch:{ Exception -> 0x34f0 }
        r4 = r4.getWidth();	 Catch:{ Exception -> 0x34f0 }
        r7 = r4 + r29;
        r0 = r31;
        r7 = java.lang.Math.max(r0, r7);	 Catch:{ Exception -> 0x34f0 }
        r4 = r4 + r29;
        r0 = r33;
        r33 = java.lang.Math.max(r0, r4);	 Catch:{ Exception -> 0x34f5 }
        r36 = r6;
        r34 = r7;
    L_0x06fd:
        r31 = 0;
        if (r28 == 0) goto L_0x355d;
    L_0x0701:
        r4 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r38;
        r0.titleX = r4;	 Catch:{ Exception -> 0x34dc }
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x34dc }
        if (r4 == 0) goto L_0x072c;
    L_0x070e:
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x34dc }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x34dc }
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;	 Catch:{ Exception -> 0x34dc }
        r0 = r38;
        r4 = r0.totalHeight;	 Catch:{ Exception -> 0x34dc }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x34dc }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x34dc }
    L_0x072c:
        r4 = 0;
        r0 = r38;
        r6 = r0.isSmallImage;	 Catch:{ Exception -> 0x34dc }
        if (r6 == 0) goto L_0x0735;
    L_0x0733:
        if (r26 != 0) goto L_0x0acc;
    L_0x0735:
        r7 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x34dc }
        r9 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x34dc }
        r10 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x34dc }
        r11 = (float) r6;	 Catch:{ Exception -> 0x34dc }
        r12 = 0;
        r13 = android.text.TextUtils.TruncateAt.END;	 Catch:{ Exception -> 0x34dc }
        r15 = 4;
        r6 = r28;
        r8 = r35;
        r14 = r35;
        r6 = org.telegram.ui.Components.StaticLayoutEx.createStaticLayout(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);	 Catch:{ Exception -> 0x34dc }
        r0 = r38;
        r0.titleLayout = r6;	 Catch:{ Exception -> 0x34dc }
        r7 = r32;
        r32 = r4;
    L_0x0758:
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x34e7 }
        r0 = r38;
        r6 = r0.titleLayout;	 Catch:{ Exception -> 0x34e7 }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x34e7 }
        r6 = r6 + -1;
        r4 = r4.getLineBottom(r6);	 Catch:{ Exception -> 0x34e7 }
        r0 = r38;
        r6 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x34e7 }
        r6 = r6 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r6;	 Catch:{ Exception -> 0x34e7 }
        r0 = r38;
        r6 = r0.totalHeight;	 Catch:{ Exception -> 0x34e7 }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x34e7 }
        r4 = 0;
        r10 = r4;
        r6 = r31;
        r8 = r33;
        r9 = r34;
    L_0x0784:
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x0b00 }
        r4 = r4.getLineCount();	 Catch:{ Exception -> 0x0b00 }
        if (r10 >= r4) goto L_0x0c6c;
    L_0x078e:
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x0b00 }
        r4 = r4.getLineLeft(r10);	 Catch:{ Exception -> 0x0b00 }
        r11 = (int) r4;	 Catch:{ Exception -> 0x0b00 }
        if (r11 == 0) goto L_0x079a;
    L_0x0799:
        r6 = 1;
    L_0x079a:
        r0 = r38;
        r4 = r0.titleX;	 Catch:{ Exception -> 0x0b00 }
        r12 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        if (r4 != r12) goto L_0x0af1;
    L_0x07a3:
        r4 = -r11;
        r0 = r38;
        r0.titleX = r4;	 Catch:{ Exception -> 0x0b00 }
    L_0x07a8:
        if (r11 == 0) goto L_0x0c5c;
    L_0x07aa:
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x0b00 }
        r4 = r4.getWidth();	 Catch:{ Exception -> 0x0b00 }
        r4 = r4 - r11;
    L_0x07b3:
        r0 = r32;
        if (r10 < r0) goto L_0x07bf;
    L_0x07b7:
        if (r11 == 0) goto L_0x07c6;
    L_0x07b9:
        r0 = r38;
        r11 = r0.isSmallImage;	 Catch:{ Exception -> 0x0b00 }
        if (r11 == 0) goto L_0x07c6;
    L_0x07bf:
        r11 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r11 = org.telegram.messenger.AndroidUtilities.dp(r11);	 Catch:{ Exception -> 0x0b00 }
        r4 = r4 + r11;
    L_0x07c6:
        r11 = r4 + r29;
        r9 = java.lang.Math.max(r9, r11);	 Catch:{ Exception -> 0x0b00 }
        r4 = r4 + r29;
        r8 = java.lang.Math.max(r8, r4);	 Catch:{ Exception -> 0x0b00 }
        r4 = r10 + 1;
        r10 = r4;
        goto L_0x0784;
    L_0x07d6:
        r4 = 0;
        r16 = r4;
        goto L_0x004b;
    L_0x07db:
        r4 = 0;
        r5 = r4;
        goto L_0x005b;
    L_0x07df:
        r4 = 0;
        r17 = r4;
        goto L_0x0072;
    L_0x07e4:
        r4 = 0;
        goto L_0x007b;
    L_0x07e7:
        r4 = 0;
        goto L_0x009a;
    L_0x07ea:
        r4 = 0;
        goto L_0x00a1;
    L_0x07ed:
        r4 = 0;
        r0 = r38;
        r0.currentMessagesGroup = r4;
        r4 = 0;
        r0 = r38;
        r0.currentPosition = r4;
        goto L_0x0116;
    L_0x07f9:
        r4 = 0;
        goto L_0x012d;
    L_0x07fc:
        r4 = 0;
        goto L_0x0148;
    L_0x07ff:
        r4 = 0;
        goto L_0x019d;
    L_0x0802:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.to_id;
        r4 = r4.channel_id;
        if (r4 == 0) goto L_0x0826;
    L_0x080c:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x0826;
    L_0x0812:
        r4 = 1;
    L_0x0813:
        r0 = r38;
        r0.drawName = r4;
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r5 = 1117782016; // 0x42a00000 float:80.0 double:5.522576936E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r18 = r4;
        goto L_0x038c;
    L_0x0826:
        r4 = 0;
        goto L_0x0813;
    L_0x0828:
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x0856;
    L_0x082e:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x0856;
    L_0x0834:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x0856;
    L_0x083a:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r4 = java.lang.Math.min(r4, r5);
        r5 = 1123287040; // 0x42f40000 float:122.0 double:5.54977537E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r5 = 1;
        r0 = r38;
        r0.drawName = r5;
        r18 = r4;
        goto L_0x038c;
    L_0x0856:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r4 = java.lang.Math.min(r4, r5);
        r5 = 1117782016; // 0x42a00000 float:80.0 double:5.522576936E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = r4 - r5;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.to_id;
        r4 = r4.channel_id;
        if (r4 == 0) goto L_0x0883;
    L_0x0874:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x0883;
    L_0x087a:
        r4 = 1;
    L_0x087b:
        r0 = r38;
        r0.drawName = r4;
        r18 = r5;
        goto L_0x038c;
    L_0x0883:
        r4 = 0;
        goto L_0x087b;
    L_0x0885:
        r4 = 0;
        goto L_0x03c6;
    L_0x0888:
        r4 = 0;
        goto L_0x03ed;
    L_0x088b:
        r4 = 0;
        goto L_0x0404;
    L_0x088e:
        r4 = 0;
        goto L_0x0419;
    L_0x0891:
        r5 = 0;
        goto L_0x0429;
    L_0x0894:
        r4 = "telegram_megagroup";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x08aa;
    L_0x089d:
        r4 = 1;
        r0 = r38;
        r0.drawInstantView = r4;
        r4 = 2;
        r0 = r38;
        r0.drawInstantViewType = r4;
        r5 = r6;
        goto L_0x0443;
    L_0x08aa:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.message;
        r5 = "pay.hotgram.ir";
        r4 = r4.contains(r5);
        if (r4 == 0) goto L_0x3574;
    L_0x08b9:
        r4 = 1;
        r0 = r38;
        r0.drawInstantView = r4;
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r38;
        r0.drawInstantViewType = r4;
        r5 = r6;
        goto L_0x0443;
    L_0x08c7:
        if (r4 == 0) goto L_0x3574;
    L_0x08c9:
        r4 = r4.toLowerCase();
        r5 = "instagram";
        r5 = r4.equals(r5);
        if (r5 != 0) goto L_0x08df;
    L_0x08d6:
        r5 = "twitter";
        r4 = r4.equals(r5);
        if (r4 == 0) goto L_0x3574;
    L_0x08df:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.cached_page;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_pageFull;
        if (r4 == 0) goto L_0x3574;
    L_0x08ed:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.photo;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_photo;
        if (r4 == 0) goto L_0x3574;
    L_0x08fb:
        r4 = 0;
        r0 = r38;
        r0.drawInstantView = r4;
        r12 = 1;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        r4 = r4.cached_page;
        r7 = r4.blocks;
        r5 = 1;
        r4 = 0;
        r6 = r5;
        r5 = r4;
    L_0x0911:
        r4 = r7.size();
        if (r5 >= r4) goto L_0x093a;
    L_0x0917:
        r4 = r7.get(r5);
        r4 = (org.telegram.tgnet.TLRPC.PageBlock) r4;
        r8 = r4 instanceof org.telegram.tgnet.TLRPC$TL_pageBlockSlideshow;
        if (r8 == 0) goto L_0x092d;
    L_0x0921:
        r4 = (org.telegram.tgnet.TLRPC$TL_pageBlockSlideshow) r4;
        r4 = r4.items;
        r6 = r4.size();
    L_0x0929:
        r4 = r5 + 1;
        r5 = r4;
        goto L_0x0911;
    L_0x092d:
        r8 = r4 instanceof org.telegram.tgnet.TLRPC$TL_pageBlockCollage;
        if (r8 == 0) goto L_0x0929;
    L_0x0931:
        r4 = (org.telegram.tgnet.TLRPC$TL_pageBlockCollage) r4;
        r4 = r4.items;
        r6 = r4.size();
        goto L_0x0929;
    L_0x093a:
        r4 = "Of";
        r5 = 2131232020; // 0x7f080514 float:1.8080137E38 double:1.0529685244E-314;
        r7 = 2;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r9 = 1;
        r9 = java.lang.Integer.valueOf(r9);
        r7[r8] = r9;
        r8 = 1;
        r6 = java.lang.Integer.valueOf(r6);
        r7[r8] = r6;
        r5 = org.telegram.messenger.LocaleController.formatString(r4, r5, r7);
        r4 = org.telegram.ui.ActionBar.Theme.chat_durationPaint;
        r4 = r4.measureText(r5);
        r6 = (double) r4;
        r6 = java.lang.Math.ceil(r6);
        r4 = (int) r6;
        r0 = r38;
        r0.photosCountWidth = r4;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_durationPaint;
        r0 = r38;
        r7 = r0.photosCountWidth;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.photosCountLayout = r4;
        r5 = r12;
        goto L_0x0443;
    L_0x097e:
        r4 = "chat_inPreviewInstantText";
        goto L_0x0481;
    L_0x0983:
        r0 = r38;
        r6 = r0.instantViewSelectorDrawable;
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.isOutOwner();
        if (r4 == 0) goto L_0x09a2;
    L_0x0991:
        r4 = "chat_outPreviewInstantText";
    L_0x0994:
        r4 = org.telegram.ui.ActionBar.Theme.getColor(r4);
        r7 = 1610612735; // 0x5fffffff float:3.6893486E19 double:7.95748421E-315;
        r4 = r4 & r7;
        r7 = 1;
        org.telegram.ui.ActionBar.Theme.setSelectorDrawableColor(r6, r4, r7);
        goto L_0x04a1;
    L_0x09a2:
        r4 = "chat_inPreviewInstantText";
        goto L_0x0994;
    L_0x09a6:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r39;
        r6 = r0.lastLineWidth;
        r4 = r4 - r6;
        if (r4 < 0) goto L_0x09ca;
    L_0x09b1:
        r0 = r19;
        if (r4 > r0) goto L_0x09ca;
    L_0x09b5:
        r0 = r38;
        r6 = r0.backgroundWidth;
        r6 = r6 + r19;
        r4 = r6 - r4;
        r6 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x04fa;
    L_0x09ca:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r39;
        r6 = r0.lastLineWidth;
        r6 = r6 + r19;
        r4 = java.lang.Math.max(r4, r6);
        r6 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x04fa;
    L_0x09e5:
        r4 = 0;
        goto L_0x0522;
    L_0x09e8:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r6 = 1117782016; // 0x42a00000 float:80.0 double:5.522576936E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        goto L_0x05af;
    L_0x09f5:
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x0a20;
    L_0x09fb:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x0a20;
    L_0x0a01:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.isOutOwner();
        if (r4 != 0) goto L_0x0a20;
    L_0x0a0b:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r6 = org.telegram.messenger.AndroidUtilities.displaySize;
        r6 = r6.y;
        r4 = java.lang.Math.min(r4, r6);
        r6 = 1124335616; // 0x43040000 float:132.0 double:5.554956023E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        goto L_0x05af;
    L_0x0a20:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r6 = org.telegram.messenger.AndroidUtilities.displaySize;
        r6 = r6.y;
        r4 = java.lang.Math.min(r4, r6);
        r6 = 1117782016; // 0x42a00000 float:80.0 double:5.522576936E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        goto L_0x05af;
    L_0x0a35:
        r6 = 0;
        goto L_0x062b;
    L_0x0a38:
        r4 = 0;
        goto L_0x065d;
    L_0x0a3b:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x0a77;
    L_0x0a41:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r14 = r4.title;
        r13 = 0;
        r11 = 0;
        r10 = 0;
        r12 = 0;
        r9 = 0;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = (org.telegram.tgnet.TLRPC$TL_messageMediaInvoice) r4;
        r8 = r4.photo;
        r7 = 0;
        r4 = "invoice";
        r5 = 0;
        r0 = r38;
        r0.isSmallImage = r5;
        r5 = 0;
        r20 = r4;
        r21 = r5;
        r22 = r7;
        r23 = r8;
        r24 = r9;
        r25 = r10;
        r26 = r11;
        r27 = r12;
        r28 = r13;
        r5 = r14;
        goto L_0x0676;
    L_0x0a77:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r5 = r4.game;
        r14 = r5.title;
        r13 = 0;
        r9 = 0;
        r0 = r39;
        r4 = r0.messageText;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 == 0) goto L_0x0ab3;
    L_0x0a8d:
        r4 = r5.description;
    L_0x0a8f:
        r11 = r5.photo;
        r12 = 0;
        r10 = r5.document;
        r8 = 0;
        r5 = "game";
        r7 = 0;
        r0 = r38;
        r0.isSmallImage = r7;
        r7 = 0;
        r20 = r5;
        r21 = r7;
        r22 = r8;
        r23 = r9;
        r24 = r10;
        r25 = r11;
        r26 = r4;
        r27 = r12;
        r28 = r13;
        r5 = r14;
        goto L_0x0676;
    L_0x0ab3:
        r4 = 0;
        goto L_0x0a8f;
    L_0x0ab5:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r29 = r4;
        goto L_0x067f;
    L_0x0abf:
        r4 = move-exception;
        r6 = r12;
        r7 = r31;
    L_0x0ac3:
        org.telegram.messenger.FileLog.e(r4);
        r36 = r6;
        r34 = r7;
        goto L_0x06fd;
    L_0x0acc:
        r7 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x34dc }
        r4 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);	 Catch:{ Exception -> 0x34dc }
        r9 = r35 - r4;
        r11 = 4;
        r6 = r28;
        r8 = r35;
        r10 = r32;
        r4 = generateStaticLayout(r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x34dc }
        r0 = r38;
        r0.titleLayout = r4;	 Catch:{ Exception -> 0x34dc }
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x34dc }
        r4 = r4.getLineCount();	 Catch:{ Exception -> 0x34dc }
        r7 = r32 - r4;
        goto L_0x0758;
    L_0x0af1:
        r0 = r38;
        r4 = r0.titleX;	 Catch:{ Exception -> 0x0b00 }
        r12 = -r11;
        r4 = java.lang.Math.max(r4, r12);	 Catch:{ Exception -> 0x0b00 }
        r0 = r38;
        r0.titleX = r4;	 Catch:{ Exception -> 0x0b00 }
        goto L_0x07a8;
    L_0x0b00:
        r4 = move-exception;
    L_0x0b01:
        org.telegram.messenger.FileLog.e(r4);
        r33 = r6;
        r15 = r7;
        r32 = r8;
        r31 = r9;
    L_0x0b0b:
        r14 = 0;
        if (r27 == 0) goto L_0x3554;
    L_0x0b0e:
        if (r28 != 0) goto L_0x3554;
    L_0x0b10:
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0cab }
        if (r4 == 0) goto L_0x0b34;
    L_0x0b16:
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0cab }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x0cab }
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;	 Catch:{ Exception -> 0x0cab }
        r0 = r38;
        r4 = r0.totalHeight;	 Catch:{ Exception -> 0x0cab }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x0cab }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x0cab }
    L_0x0b34:
        r4 = 3;
        if (r15 != r4) goto L_0x0c75;
    L_0x0b37:
        r0 = r38;
        r4 = r0.isSmallImage;	 Catch:{ Exception -> 0x0cab }
        if (r4 == 0) goto L_0x0b3f;
    L_0x0b3d:
        if (r26 != 0) goto L_0x0c75;
    L_0x0b3f:
        r6 = new android.text.StaticLayout;	 Catch:{ Exception -> 0x0cab }
        r8 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x0cab }
        r10 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x0cab }
        r11 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r12 = 0;
        r13 = 0;
        r7 = r27;
        r9 = r35;
        r6.<init>(r7, r8, r9, r10, r11, r12, r13);	 Catch:{ Exception -> 0x0cab }
        r0 = r38;
        r0.authorLayout = r6;	 Catch:{ Exception -> 0x0cab }
        r7 = r15;
    L_0x0b55:
        r0 = r38;
        r4 = r0.authorLayout;	 Catch:{ Exception -> 0x34ce }
        r0 = r38;
        r6 = r0.authorLayout;	 Catch:{ Exception -> 0x34ce }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x34ce }
        r6 = r6 + -1;
        r4 = r4.getLineBottom(r6);	 Catch:{ Exception -> 0x34ce }
        r0 = r38;
        r6 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x34ce }
        r6 = r6 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r6;	 Catch:{ Exception -> 0x34ce }
        r0 = r38;
        r6 = r0.totalHeight;	 Catch:{ Exception -> 0x34ce }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x34ce }
        r0 = r38;
        r4 = r0.authorLayout;	 Catch:{ Exception -> 0x34ce }
        r6 = 0;
        r4 = r4.getLineLeft(r6);	 Catch:{ Exception -> 0x34ce }
        r4 = (int) r4;	 Catch:{ Exception -> 0x34ce }
        r6 = -r4;
        r0 = r38;
        r0.authorX = r6;	 Catch:{ Exception -> 0x34ce }
        if (r4 == 0) goto L_0x0c99;
    L_0x0b8a:
        r0 = r38;
        r6 = r0.authorLayout;	 Catch:{ Exception -> 0x34ce }
        r6 = r6.getWidth();	 Catch:{ Exception -> 0x34ce }
        r4 = r6 - r4;
        r6 = 1;
    L_0x0b95:
        r8 = r4 + r29;
        r0 = r31;
        r8 = java.lang.Math.max(r0, r8);	 Catch:{ Exception -> 0x34d4 }
        r4 = r4 + r29;
        r0 = r32;
        r28 = java.lang.Math.max(r0, r4);	 Catch:{ Exception -> 0x34d9 }
        r31 = r6;
        r10 = r7;
        r27 = r8;
    L_0x0baa:
        if (r26 == 0) goto L_0x3550;
    L_0x0bac:
        r4 = 0;
        r0 = r38;
        r0.descriptionX = r4;	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r4 = r0.currentMessageObject;	 Catch:{ Exception -> 0x0ce7 }
        r4.generateLinkDescription();	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0ce7 }
        if (r4 == 0) goto L_0x0bdc;
    L_0x0bbe:
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0ce7 }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x0ce7 }
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r4 = r0.totalHeight;	 Catch:{ Exception -> 0x0ce7 }
        r6 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x0ce7 }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x0ce7 }
    L_0x0bdc:
        r4 = 0;
        r6 = 3;
        if (r10 != r6) goto L_0x0cbc;
    L_0x0be0:
        r0 = r38;
        r6 = r0.isSmallImage;	 Catch:{ Exception -> 0x0ce7 }
        if (r6 != 0) goto L_0x0cbc;
    L_0x0be6:
        r0 = r39;
        r6 = r0.linkDescription;	 Catch:{ Exception -> 0x0ce7 }
        r7 = org.telegram.ui.ActionBar.Theme.chat_replyTextPaint;	 Catch:{ Exception -> 0x0ce7 }
        r9 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x0ce7 }
        r10 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);	 Catch:{ Exception -> 0x0ce7 }
        r11 = (float) r8;	 Catch:{ Exception -> 0x0ce7 }
        r12 = 0;
        r13 = android.text.TextUtils.TruncateAt.END;	 Catch:{ Exception -> 0x0ce7 }
        r15 = 6;
        r8 = r35;
        r14 = r35;
        r6 = org.telegram.ui.Components.StaticLayoutEx.createStaticLayout(r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r0.descriptionLayout = r6;	 Catch:{ Exception -> 0x0ce7 }
        r11 = r4;
    L_0x0c08:
        r0 = r38;
        r4 = r0.descriptionLayout;	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r6 = r0.descriptionLayout;	 Catch:{ Exception -> 0x0ce7 }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x0ce7 }
        r6 = r6 + -1;
        r4 = r4.getLineBottom(r6);	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r6 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x0ce7 }
        r6 = r6 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r6;	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r6 = r0.totalHeight;	 Catch:{ Exception -> 0x0ce7 }
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x0ce7 }
        r6 = 0;
        r4 = 0;
        r37 = r4;
        r4 = r6;
        r6 = r37;
    L_0x0c33:
        r0 = r38;
        r7 = r0.descriptionLayout;	 Catch:{ Exception -> 0x0ce7 }
        r7 = r7.getLineCount();	 Catch:{ Exception -> 0x0ce7 }
        if (r6 >= r7) goto L_0x1342;
    L_0x0c3d:
        r0 = r38;
        r7 = r0.descriptionLayout;	 Catch:{ Exception -> 0x0ce7 }
        r7 = r7.getLineLeft(r6);	 Catch:{ Exception -> 0x0ce7 }
        r8 = (double) r7;	 Catch:{ Exception -> 0x0ce7 }
        r8 = java.lang.Math.ceil(r8);	 Catch:{ Exception -> 0x0ce7 }
        r7 = (int) r8;	 Catch:{ Exception -> 0x0ce7 }
        if (r7 == 0) goto L_0x0c59;
    L_0x0c4d:
        r4 = 1;
        r0 = r38;
        r8 = r0.descriptionX;	 Catch:{ Exception -> 0x0ce7 }
        if (r8 != 0) goto L_0x0cd8;
    L_0x0c54:
        r7 = -r7;
        r0 = r38;
        r0.descriptionX = r7;	 Catch:{ Exception -> 0x0ce7 }
    L_0x0c59:
        r6 = r6 + 1;
        goto L_0x0c33;
    L_0x0c5c:
        r0 = r38;
        r4 = r0.titleLayout;	 Catch:{ Exception -> 0x0b00 }
        r4 = r4.getLineWidth(r10);	 Catch:{ Exception -> 0x0b00 }
        r12 = (double) r4;	 Catch:{ Exception -> 0x0b00 }
        r12 = java.lang.Math.ceil(r12);	 Catch:{ Exception -> 0x0b00 }
        r4 = (int) r12;
        goto L_0x07b3;
    L_0x0c6c:
        r33 = r6;
        r15 = r7;
        r32 = r8;
        r31 = r9;
        goto L_0x0b0b;
    L_0x0c75:
        r7 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x0cab }
        r4 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);	 Catch:{ Exception -> 0x0cab }
        r9 = r35 - r4;
        r11 = 1;
        r6 = r27;
        r8 = r35;
        r10 = r15;
        r4 = generateStaticLayout(r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x0cab }
        r0 = r38;
        r0.authorLayout = r4;	 Catch:{ Exception -> 0x0cab }
        r0 = r38;
        r4 = r0.authorLayout;	 Catch:{ Exception -> 0x0cab }
        r4 = r4.getLineCount();	 Catch:{ Exception -> 0x0cab }
        r7 = r15 - r4;
        goto L_0x0b55;
    L_0x0c99:
        r0 = r38;
        r4 = r0.authorLayout;	 Catch:{ Exception -> 0x34ce }
        r6 = 0;
        r4 = r4.getLineWidth(r6);	 Catch:{ Exception -> 0x34ce }
        r8 = (double) r4;	 Catch:{ Exception -> 0x34ce }
        r8 = java.lang.Math.ceil(r8);	 Catch:{ Exception -> 0x34ce }
        r4 = (int) r8;
        r6 = r14;
        goto L_0x0b95;
    L_0x0cab:
        r4 = move-exception;
        r6 = r14;
        r7 = r15;
        r8 = r31;
    L_0x0cb0:
        org.telegram.messenger.FileLog.e(r4);
        r31 = r6;
        r10 = r7;
        r28 = r32;
        r27 = r8;
        goto L_0x0baa;
    L_0x0cbc:
        r0 = r39;
        r6 = r0.linkDescription;	 Catch:{ Exception -> 0x0ce7 }
        r7 = org.telegram.ui.ActionBar.Theme.chat_replyTextPaint;	 Catch:{ Exception -> 0x0ce7 }
        r4 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);	 Catch:{ Exception -> 0x0ce7 }
        r9 = r35 - r4;
        r11 = 6;
        r8 = r35;
        r4 = generateStaticLayout(r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r0.descriptionLayout = r4;	 Catch:{ Exception -> 0x0ce7 }
        r11 = r10;
        goto L_0x0c08;
    L_0x0cd8:
        r0 = r38;
        r8 = r0.descriptionX;	 Catch:{ Exception -> 0x0ce7 }
        r7 = -r7;
        r7 = java.lang.Math.max(r8, r7);	 Catch:{ Exception -> 0x0ce7 }
        r0 = r38;
        r0.descriptionX = r7;	 Catch:{ Exception -> 0x0ce7 }
        goto L_0x0c59;
    L_0x0ce7:
        r4 = move-exception;
        r6 = r27;
    L_0x0cea:
        org.telegram.messenger.FileLog.e(r4);
        r8 = r6;
    L_0x0cee:
        if (r21 == 0) goto L_0x0d0e;
    L_0x0cf0:
        r0 = r38;
        r4 = r0.descriptionLayout;
        if (r4 == 0) goto L_0x0d07;
    L_0x0cf6:
        r0 = r38;
        r4 = r0.descriptionLayout;
        if (r4 == 0) goto L_0x0d0e;
    L_0x0cfc:
        r0 = r38;
        r4 = r0.descriptionLayout;
        r4 = r4.getLineCount();
        r6 = 1;
        if (r4 != r6) goto L_0x0d0e;
    L_0x0d07:
        r21 = 0;
        r4 = 0;
        r0 = r38;
        r0.isSmallImage = r4;
    L_0x0d0e:
        if (r21 == 0) goto L_0x13d5;
    L_0x0d10:
        r4 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r4);
    L_0x0d16:
        if (r24 == 0) goto L_0x16f9;
    L_0x0d18:
        r4 = org.telegram.messenger.MessageObject.isGifDocument(r24);
        if (r4 == 0) goto L_0x13e1;
    L_0x0d1e:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r4 = r4.canAutoplayGifs();
        if (r4 != 0) goto L_0x0d2e;
    L_0x0d28:
        r4 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r39;
        r0.gifState = r4;
    L_0x0d2e:
        r0 = r38;
        r6 = r0.photoImage;
        r0 = r39;
        r4 = r0.gifState;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r4 = (r4 > r9 ? 1 : (r4 == r9 ? 0 : -1));
        if (r4 == 0) goto L_0x13d9;
    L_0x0d3c:
        r4 = 1;
    L_0x0d3d:
        r6.setAllowStartAnimation(r4);
        r0 = r24;
        r4 = r0.thumb;
        r0 = r38;
        r0.currentPhotoObject = r4;
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x0dae;
    L_0x0d4e:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x0d5e;
    L_0x0d56:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x0dae;
    L_0x0d5e:
        r4 = 0;
        r6 = r4;
    L_0x0d60:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.size();
        if (r6 >= r4) goto L_0x0d8c;
    L_0x0d6a:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.get(r6);
        r4 = (org.telegram.tgnet.TLRPC.DocumentAttribute) r4;
        r9 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
        if (r9 != 0) goto L_0x0d7c;
    L_0x0d78:
        r9 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
        if (r9 == 0) goto L_0x13dc;
    L_0x0d7c:
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = r4.f10140w;
        r6.f10147w = r9;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r4 = r4.f10139h;
        r6.f10146h = r4;
    L_0x0d8c:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x0d9c;
    L_0x0d94:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x0dae;
    L_0x0d9c:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6.f10146h = r9;
        r4.f10147w = r9;
    L_0x0dae:
        r4 = 2;
        r0 = r38;
        r0.documentAttachType = r4;
        r6 = r8;
    L_0x0db4:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 5;
        if (r4 == r8) goto L_0x1092;
    L_0x0dbb:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 3;
        if (r4 == r8) goto L_0x1092;
    L_0x0dc2:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 1;
        if (r4 == r8) goto L_0x1092;
    L_0x0dc9:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 != 0) goto L_0x0dd1;
    L_0x0dcf:
        if (r23 == 0) goto L_0x1a01;
    L_0x0dd1:
        if (r20 == 0) goto L_0x1769;
    L_0x0dd3:
        r4 = "photo";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 != 0) goto L_0x0e02;
    L_0x0dde:
        r4 = "document";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x0df0;
    L_0x0de9:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 6;
        if (r4 != r8) goto L_0x0e02;
    L_0x0df0:
        r4 = "gif";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 != 0) goto L_0x0e02;
    L_0x0dfb:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 4;
        if (r4 != r8) goto L_0x1769;
    L_0x0e02:
        r4 = 1;
    L_0x0e03:
        r0 = r38;
        r0.drawImageButton = r4;
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        if (r4 == 0) goto L_0x0e2b;
    L_0x0e0d:
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4 = r4 + r8;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r4 = r0.totalHeight;
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r4 = r4 + r8;
        r0 = r38;
        r0.totalHeight = r4;
    L_0x0e2b:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 6;
        if (r4 != r8) goto L_0x1777;
    L_0x0e32:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x176c;
    L_0x0e38:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r4 = (float) r4;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r4 = r4 * r7;
        r7 = (int) r4;
    L_0x0e41:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x178a;
    L_0x0e47:
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
    L_0x0e4d:
        r4 = r7 - r4;
        r4 = r4 + r29;
        r14 = java.lang.Math.max(r6, r4);
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x178d;
    L_0x0e5b:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r6 = -1;
        r4.size = r6;
        r0 = r38;
        r4 = r0.currentPhotoObjectThumb;
        if (r4 == 0) goto L_0x0e6f;
    L_0x0e68:
        r0 = r38;
        r4 = r0.currentPhotoObjectThumb;
        r6 = -1;
        r4.size = r6;
    L_0x0e6f:
        if (r21 != 0) goto L_0x0e78;
    L_0x0e71:
        r0 = r38;
        r4 = r0.documentAttachType;
        r6 = 7;
        if (r4 != r6) goto L_0x1794;
    L_0x0e78:
        r8 = r7;
        r9 = r7;
    L_0x0e7a:
        r0 = r38;
        r4 = r0.isSmallImage;
        if (r4 == 0) goto L_0x1810;
    L_0x0e80:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r36;
        r0 = r38;
        r5 = r0.linkPreviewHeight;
        if (r4 <= r5) goto L_0x0eb7;
    L_0x0e8e:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = r5 + r36;
        r0 = r38;
        r6 = r0.linkPreviewHeight;
        r5 = r5 - r6;
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r5 = r5 + r6;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r36;
        r0 = r38;
        r0.linkPreviewHeight = r4;
    L_0x0eb7:
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r5 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.linkPreviewHeight = r4;
    L_0x0ec6:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r4.setImageCoords(r5, r6, r9, r8);
        r4 = java.util.Locale.US;
        r5 = "%d_%d";
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r7 = 0;
        r10 = java.lang.Integer.valueOf(r9);
        r6[r7] = r10;
        r7 = 1;
        r10 = java.lang.Integer.valueOf(r8);
        r6[r7] = r10;
        r4 = java.lang.String.format(r4, r5, r6);
        r0 = r38;
        r0.currentPhotoFilter = r4;
        r4 = java.util.Locale.US;
        r5 = "%d_%d_b";
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r7 = 0;
        r10 = java.lang.Integer.valueOf(r9);
        r6[r7] = r10;
        r7 = 1;
        r10 = java.lang.Integer.valueOf(r8);
        r6[r7] = r10;
        r4 = java.lang.String.format(r4, r5, r6);
        r0 = r38;
        r0.currentPhotoFilterThumb = r4;
        if (r23 == 0) goto L_0x182b;
    L_0x0f0d:
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoFilter;
        r8 = 0;
        r9 = 0;
        r10 = "b1";
        r0 = r23;
        r11 = r0.size;
        r12 = 0;
        r13 = 1;
        r5 = r23;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11, r12, r13);
    L_0x0f26:
        r4 = 1;
        r0 = r38;
        r0.drawPhotoImage = r4;
        if (r20 == 0) goto L_0x19c6;
    L_0x0f2d:
        r4 = "video";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x19c6;
    L_0x0f38:
        if (r22 == 0) goto L_0x19c6;
    L_0x0f3a:
        r4 = r22 / 60;
        r5 = r4 * 60;
        r5 = r22 - r5;
        r6 = "%d:%02d";
        r7 = 2;
        r7 = new java.lang.Object[r7];
        r8 = 0;
        r4 = java.lang.Integer.valueOf(r4);
        r7[r8] = r4;
        r4 = 1;
        r5 = java.lang.Integer.valueOf(r5);
        r7[r4] = r5;
        r5 = java.lang.String.format(r6, r7);
        r4 = org.telegram.ui.ActionBar.Theme.chat_durationPaint;
        r4 = r4.measureText(r5);
        r6 = (double) r4;
        r6 = java.lang.Math.ceil(r6);
        r4 = (int) r6;
        r0 = r38;
        r0.durationWidth = r4;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_durationPaint;
        r0 = r38;
        r7 = r0.durationWidth;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.videoInfoLayout = r4;
    L_0x0f7d:
        r12 = r14;
    L_0x0f7e:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x105a;
    L_0x0f84:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.flags;
        r4 = r4 & 4;
        if (r4 == 0) goto L_0x1a2c;
    L_0x0f90:
        r4 = "PaymentReceipt";
        r5 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r4 = r4.toUpperCase();
    L_0x0f9e:
        r5 = org.telegram.messenger.LocaleController.getInstance();
        r0 = r39;
        r6 = r0.messageOwner;
        r6 = r6.media;
        r6 = r6.total_amount;
        r0 = r39;
        r8 = r0.messageOwner;
        r8 = r8.media;
        r8 = r8.currency;
        r6 = r5.formatCurrencyString(r6, r8);
        r5 = new android.text.SpannableStringBuilder;
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r7 = r7.append(r6);
        r8 = " ";
        r7 = r7.append(r8);
        r4 = r7.append(r4);
        r4 = r4.toString();
        r5.<init>(r4);
        r4 = new org.telegram.ui.Components.TypefaceSpan;
        r7 = "fonts/rmedium.ttf";
        r7 = org.telegram.messenger.AndroidUtilities.getTypeface(r7);
        r4.<init>(r7);
        r7 = 0;
        r6 = r6.length();
        r8 = 33;
        r5.setSpan(r4, r7, r6, r8);
        r4 = org.telegram.ui.ActionBar.Theme.chat_shipmentPaint;
        r6 = 0;
        r7 = r5.length();
        r4 = r4.measureText(r5, r6, r7);
        r6 = (double) r4;
        r6 = java.lang.Math.ceil(r6);
        r4 = (int) r6;
        r0 = r38;
        r0.durationWidth = r4;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_shipmentPaint;
        r0 = r38;
        r7 = r0.durationWidth;
        r8 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r7 = r7 + r8;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.videoInfoLayout = r4;
        r0 = r38;
        r4 = r0.drawPhotoImage;
        if (r4 != 0) goto L_0x105a;
    L_0x1020:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.durationWidth;
        r0 = r38;
        r5 = r0.timeWidth;
        r4 = r4 + r5;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r18;
        if (r4 <= r0) goto L_0x1a56;
    L_0x1043:
        r0 = r38;
        r4 = r0.durationWidth;
        r12 = java.lang.Math.max(r4, r12);
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
    L_0x105a:
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 == 0) goto L_0x1089;
    L_0x1060:
        r0 = r39;
        r4 = r0.textHeight;
        if (r4 == 0) goto L_0x1089;
    L_0x1066:
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r0 = r39;
        r5 = r0.textHeight;
        r6 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r5 = r5 + r6;
        r4 = r4 + r5;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
    L_0x1089:
        r0 = r38;
        r1 = r18;
        r2 = r19;
        r0.calcBackgroundWidth(r1, r2, r12);
    L_0x1092:
        r0 = r38;
        r4 = r0.drawInstantView;
        if (r4 == 0) goto L_0x1144;
    L_0x1098:
        r4 = 1107558400; // 0x42040000 float:33.0 double:5.47206556E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r0.instantWidth = r4;
        r0 = r38;
        r4 = r0.drawInstantViewType;
        r5 = 1;
        if (r4 != r5) goto L_0x1a6c;
    L_0x10a9:
        r4 = "OpenChannel";
        r5 = 2131232037; // 0x7f080525 float:1.8080172E38 double:1.052968533E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = r4;
    L_0x10b4:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1117126656; // 0x42960000 float:75.0 double:5.51933903E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r7 = r4 - r6;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_instantViewPaint;
        r8 = (float) r7;
        r9 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r5, r6, r8, r9);
        r6 = org.telegram.ui.ActionBar.Theme.chat_instantViewPaint;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.instantViewLayout = r4;
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1107820544; // 0x42080000 float:34.0 double:5.473360725E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.instantWidth = r4;
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1110966272; // 0x42380000 float:46.0 double:5.488902687E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.instantViewLayout;
        if (r4 == 0) goto L_0x1144;
    L_0x10fe:
        r0 = r38;
        r4 = r0.instantViewLayout;
        r4 = r4.getLineCount();
        if (r4 <= 0) goto L_0x1144;
    L_0x1108:
        r0 = r38;
        r4 = r0.instantWidth;
        r4 = (double) r4;
        r0 = r38;
        r6 = r0.instantViewLayout;
        r7 = 0;
        r6 = r6.getLineWidth(r7);
        r6 = (double) r6;
        r6 = java.lang.Math.ceil(r6);
        r4 = r4 - r6;
        r4 = (int) r4;
        r5 = r4 / 2;
        r0 = r38;
        r4 = r0.drawInstantViewType;
        if (r4 != 0) goto L_0x1aa1;
    L_0x1125:
        r4 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
    L_0x112b:
        r4 = r4 + r5;
        r0 = r38;
        r0.instantTextX = r4;
        r0 = r38;
        r4 = r0.instantTextX;
        r0 = r38;
        r5 = r0.instantViewLayout;
        r6 = 0;
        r5 = r5.getLineLeft(r6);
        r5 = -r5;
        r5 = (int) r5;
        r4 = r4 + r5;
        r0 = r38;
        r0.instantTextX = r4;
    L_0x1144:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 != 0) goto L_0x3121;
    L_0x114a:
        r0 = r38;
        r4 = r0.captionLayout;
        if (r4 != 0) goto L_0x3121;
    L_0x1150:
        r0 = r39;
        r4 = r0.caption;
        if (r4 == 0) goto L_0x3121;
    L_0x1156:
        r0 = r39;
        r4 = r0.type;
        r5 = 13;
        if (r4 == r5) goto L_0x3121;
    L_0x115e:
        r0 = r38;
        r4 = r0.backgroundWidth;	 Catch:{ Exception -> 0x3118 }
        r5 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x3118 }
        r12 = r4 - r5;
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x3118 }
        r5 = 24;
        if (r4 < r5) goto L_0x30f9;
    L_0x1170:
        r0 = r39;
        r4 = r0.caption;	 Catch:{ Exception -> 0x3118 }
        r5 = 0;
        r0 = r39;
        r6 = r0.caption;	 Catch:{ Exception -> 0x3118 }
        r6 = r6.length();	 Catch:{ Exception -> 0x3118 }
        r7 = org.telegram.ui.ActionBar.Theme.chat_msgTextPaint;	 Catch:{ Exception -> 0x3118 }
        r8 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);	 Catch:{ Exception -> 0x3118 }
        r8 = r12 - r8;
        r4 = android.text.StaticLayout.Builder.obtain(r4, r5, r6, r7, r8);	 Catch:{ Exception -> 0x3118 }
        r5 = 1;
        r4 = r4.setBreakStrategy(r5);	 Catch:{ Exception -> 0x3118 }
        r5 = 0;
        r4 = r4.setHyphenationFrequency(r5);	 Catch:{ Exception -> 0x3118 }
        r5 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x3118 }
        r4 = r4.setAlignment(r5);	 Catch:{ Exception -> 0x3118 }
        r4 = r4.build();	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r0.captionLayout = r4;	 Catch:{ Exception -> 0x3118 }
    L_0x11a3:
        r0 = r38;
        r4 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r4 = r4.getLineCount();	 Catch:{ Exception -> 0x3118 }
        if (r4 <= 0) goto L_0x1233;
    L_0x11ad:
        r0 = r38;
        r5 = r0.timeWidth;	 Catch:{ Exception -> 0x3118 }
        r4 = r39.isOutOwner();	 Catch:{ Exception -> 0x3118 }
        if (r4 == 0) goto L_0x311e;
    L_0x11b7:
        r4 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);	 Catch:{ Exception -> 0x3118 }
    L_0x11bd:
        r4 = r4 + r5;
        r0 = r38;
        r5 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r5 = r5.getHeight();	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r0.captionHeight = r5;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r5 = r0.totalHeight;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r6 = r0.captionHeight;	 Catch:{ Exception -> 0x3118 }
        r7 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);	 Catch:{ Exception -> 0x3118 }
        r6 = r6 + r7;
        r5 = r5 + r6;
        r0 = r38;
        r0.totalHeight = r5;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r5 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r6 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x3118 }
        r6 = r6 + -1;
        r5 = r5.getLineWidth(r6);	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r6 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r7 = r0.captionLayout;	 Catch:{ Exception -> 0x3118 }
        r7 = r7.getLineCount();	 Catch:{ Exception -> 0x3118 }
        r7 = r7 + -1;
        r6 = r6.getLineLeft(r7);	 Catch:{ Exception -> 0x3118 }
        r5 = r5 + r6;
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x3118 }
        r6 = r12 - r6;
        r6 = (float) r6;	 Catch:{ Exception -> 0x3118 }
        r5 = r6 - r5;
        r4 = (float) r4;	 Catch:{ Exception -> 0x3118 }
        r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1));
        if (r4 >= 0) goto L_0x1233;
    L_0x1213:
        r0 = r38;
        r4 = r0.totalHeight;	 Catch:{ Exception -> 0x3118 }
        r5 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x3118 }
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r4 = r0.captionHeight;	 Catch:{ Exception -> 0x3118 }
        r5 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x3118 }
        r4 = r4 + r5;
        r0 = r38;
        r0.captionHeight = r4;	 Catch:{ Exception -> 0x3118 }
        r30 = 2;
    L_0x1233:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.eventId;
        r6 = 0;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x3195;
    L_0x123f:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.isMediaEmpty();
        if (r4 != 0) goto L_0x3195;
    L_0x1249:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r4 = r4.webpage;
        if (r4 == 0) goto L_0x3195;
    L_0x1255:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1109655552; // 0x42240000 float:41.0 double:5.48242687E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r14 = r4 - r5;
        r4 = 1;
        r0 = r38;
        r0.hasOldCaptionPreview = r4;
        r4 = 0;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.messageOwner;
        r4 = r4.media;
        r12 = r4.webpage;
        r4 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x3148 }
        r5 = r12.site_name;	 Catch:{ Exception -> 0x3148 }
        r4 = r4.measureText(r5);	 Catch:{ Exception -> 0x3148 }
        r4 = (double) r4;	 Catch:{ Exception -> 0x3148 }
        r4 = java.lang.Math.ceil(r4);	 Catch:{ Exception -> 0x3148 }
        r7 = (int) r4;	 Catch:{ Exception -> 0x3148 }
        r4 = new android.text.StaticLayout;	 Catch:{ Exception -> 0x3148 }
        r5 = r12.site_name;	 Catch:{ Exception -> 0x3148 }
        r6 = org.telegram.ui.ActionBar.Theme.chat_replyNamePaint;	 Catch:{ Exception -> 0x3148 }
        r7 = java.lang.Math.min(r7, r14);	 Catch:{ Exception -> 0x3148 }
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x3148 }
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x3148 }
        r0 = r38;
        r0.siteNameLayout = r4;	 Catch:{ Exception -> 0x3148 }
        r0 = r38;
        r4 = r0.siteNameLayout;	 Catch:{ Exception -> 0x3148 }
        r0 = r38;
        r5 = r0.siteNameLayout;	 Catch:{ Exception -> 0x3148 }
        r5 = r5.getLineCount();	 Catch:{ Exception -> 0x3148 }
        r5 = r5 + -1;
        r4 = r4.getLineBottom(r5);	 Catch:{ Exception -> 0x3148 }
        r0 = r38;
        r5 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x3148 }
        r5 = r5 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r5;	 Catch:{ Exception -> 0x3148 }
        r0 = r38;
        r5 = r0.totalHeight;	 Catch:{ Exception -> 0x3148 }
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x3148 }
    L_0x12be:
        r4 = 0;
        r0 = r38;
        r0.descriptionX = r4;	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r4 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x315d }
        if (r4 == 0) goto L_0x12d8;
    L_0x12c9:
        r0 = r38;
        r4 = r0.totalHeight;	 Catch:{ Exception -> 0x315d }
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x315d }
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x315d }
    L_0x12d8:
        r4 = r12.description;	 Catch:{ Exception -> 0x315d }
        r5 = org.telegram.ui.ActionBar.Theme.chat_replyTextPaint;	 Catch:{ Exception -> 0x315d }
        r7 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x315d }
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);	 Catch:{ Exception -> 0x315d }
        r9 = (float) r6;	 Catch:{ Exception -> 0x315d }
        r10 = 0;
        r11 = android.text.TextUtils.TruncateAt.END;	 Catch:{ Exception -> 0x315d }
        r13 = 6;
        r6 = r14;
        r12 = r14;
        r4 = org.telegram.ui.Components.StaticLayoutEx.createStaticLayout(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r0.descriptionLayout = r4;	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r4 = r0.descriptionLayout;	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r5 = r0.descriptionLayout;	 Catch:{ Exception -> 0x315d }
        r5 = r5.getLineCount();	 Catch:{ Exception -> 0x315d }
        r5 = r5 + -1;
        r4 = r4.getLineBottom(r5);	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r5 = r0.linkPreviewHeight;	 Catch:{ Exception -> 0x315d }
        r5 = r5 + r4;
        r0 = r38;
        r0.linkPreviewHeight = r5;	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r5 = r0.totalHeight;	 Catch:{ Exception -> 0x315d }
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;	 Catch:{ Exception -> 0x315d }
        r4 = 0;
    L_0x131a:
        r0 = r38;
        r5 = r0.descriptionLayout;	 Catch:{ Exception -> 0x315d }
        r5 = r5.getLineCount();	 Catch:{ Exception -> 0x315d }
        if (r4 >= r5) goto L_0x3161;
    L_0x1324:
        r0 = r38;
        r5 = r0.descriptionLayout;	 Catch:{ Exception -> 0x315d }
        r5 = r5.getLineLeft(r4);	 Catch:{ Exception -> 0x315d }
        r6 = (double) r5;	 Catch:{ Exception -> 0x315d }
        r6 = java.lang.Math.ceil(r6);	 Catch:{ Exception -> 0x315d }
        r5 = (int) r6;	 Catch:{ Exception -> 0x315d }
        if (r5 == 0) goto L_0x133f;
    L_0x1334:
        r0 = r38;
        r6 = r0.descriptionX;	 Catch:{ Exception -> 0x315d }
        if (r6 != 0) goto L_0x314e;
    L_0x133a:
        r5 = -r5;
        r0 = r38;
        r0.descriptionX = r5;	 Catch:{ Exception -> 0x315d }
    L_0x133f:
        r4 = r4 + 1;
        goto L_0x131a;
    L_0x1342:
        r0 = r38;
        r6 = r0.descriptionLayout;	 Catch:{ Exception -> 0x0ce7 }
        r9 = r6.getWidth();	 Catch:{ Exception -> 0x0ce7 }
        r6 = 0;
        r10 = r6;
        r7 = r28;
        r6 = r27;
    L_0x1350:
        r0 = r38;
        r8 = r0.descriptionLayout;	 Catch:{ Exception -> 0x34cb }
        r8 = r8.getLineCount();	 Catch:{ Exception -> 0x34cb }
        if (r10 >= r8) goto L_0x13d2;
    L_0x135a:
        r0 = r38;
        r8 = r0.descriptionLayout;	 Catch:{ Exception -> 0x34cb }
        r8 = r8.getLineLeft(r10);	 Catch:{ Exception -> 0x34cb }
        r12 = (double) r8;	 Catch:{ Exception -> 0x34cb }
        r12 = java.lang.Math.ceil(r12);	 Catch:{ Exception -> 0x34cb }
        r12 = (int) r12;	 Catch:{ Exception -> 0x34cb }
        if (r12 != 0) goto L_0x1375;
    L_0x136a:
        r0 = r38;
        r8 = r0.descriptionX;	 Catch:{ Exception -> 0x34cb }
        if (r8 == 0) goto L_0x1375;
    L_0x1370:
        r8 = 0;
        r0 = r38;
        r0.descriptionX = r8;	 Catch:{ Exception -> 0x34cb }
    L_0x1375:
        if (r12 == 0) goto L_0x13bb;
    L_0x1377:
        r8 = r9 - r12;
    L_0x1379:
        if (r10 < r11) goto L_0x1385;
    L_0x137b:
        if (r11 == 0) goto L_0x138c;
    L_0x137d:
        if (r12 == 0) goto L_0x138c;
    L_0x137f:
        r0 = r38;
        r12 = r0.isSmallImage;	 Catch:{ Exception -> 0x34cb }
        if (r12 == 0) goto L_0x138c;
    L_0x1385:
        r12 = 1112539136; // 0x42500000 float:52.0 double:5.496673668E-315;
        r12 = org.telegram.messenger.AndroidUtilities.dp(r12);	 Catch:{ Exception -> 0x34cb }
        r8 = r8 + r12;
    L_0x138c:
        r12 = r8 + r29;
        if (r7 >= r12) goto L_0x13af;
    L_0x1390:
        if (r33 == 0) goto L_0x139e;
    L_0x1392:
        r0 = r38;
        r12 = r0.titleX;	 Catch:{ Exception -> 0x34cb }
        r13 = r8 + r29;
        r13 = r13 - r7;
        r12 = r12 + r13;
        r0 = r38;
        r0.titleX = r12;	 Catch:{ Exception -> 0x34cb }
    L_0x139e:
        if (r31 == 0) goto L_0x13ad;
    L_0x13a0:
        r0 = r38;
        r12 = r0.authorX;	 Catch:{ Exception -> 0x34cb }
        r13 = r8 + r29;
        r7 = r13 - r7;
        r7 = r7 + r12;
        r0 = r38;
        r0.authorX = r7;	 Catch:{ Exception -> 0x34cb }
    L_0x13ad:
        r7 = r8 + r29;
    L_0x13af:
        r8 = r8 + r29;
        r27 = java.lang.Math.max(r6, r8);	 Catch:{ Exception -> 0x34cb }
        r6 = r10 + 1;
        r10 = r6;
        r6 = r27;
        goto L_0x1350;
    L_0x13bb:
        if (r4 == 0) goto L_0x13bf;
    L_0x13bd:
        r8 = r9;
        goto L_0x1379;
    L_0x13bf:
        r0 = r38;
        r8 = r0.descriptionLayout;	 Catch:{ Exception -> 0x34cb }
        r8 = r8.getLineWidth(r10);	 Catch:{ Exception -> 0x34cb }
        r14 = (double) r8;	 Catch:{ Exception -> 0x34cb }
        r14 = java.lang.Math.ceil(r14);	 Catch:{ Exception -> 0x34cb }
        r8 = (int) r14;	 Catch:{ Exception -> 0x34cb }
        r8 = java.lang.Math.min(r8, r9);	 Catch:{ Exception -> 0x34cb }
        goto L_0x1379;
    L_0x13d2:
        r8 = r6;
        goto L_0x0cee;
    L_0x13d5:
        r7 = r35;
        goto L_0x0d16;
    L_0x13d9:
        r4 = 0;
        goto L_0x0d3d;
    L_0x13dc:
        r4 = r6 + 1;
        r6 = r4;
        goto L_0x0d60;
    L_0x13e1:
        r4 = org.telegram.messenger.MessageObject.isVideoDocument(r24);
        if (r4 == 0) goto L_0x1460;
    L_0x13e7:
        r0 = r24;
        r4 = r0.thumb;
        r0 = r38;
        r0.currentPhotoObject = r4;
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x1451;
    L_0x13f5:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x1405;
    L_0x13fd:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x1451;
    L_0x1405:
        r4 = 0;
        r6 = r4;
    L_0x1407:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.size();
        if (r6 >= r4) goto L_0x142f;
    L_0x1411:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.get(r6);
        r4 = (org.telegram.tgnet.TLRPC.DocumentAttribute) r4;
        r9 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
        if (r9 == 0) goto L_0x145c;
    L_0x141f:
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = r4.f10140w;
        r6.f10147w = r9;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r4 = r4.f10139h;
        r6.f10146h = r4;
    L_0x142f:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x143f;
    L_0x1437:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x1451;
    L_0x143f:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6.f10146h = r9;
        r4.f10147w = r9;
    L_0x1451:
        r4 = 0;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r6 = r8;
        goto L_0x0db4;
    L_0x145c:
        r4 = r6 + 1;
        r6 = r4;
        goto L_0x1407;
    L_0x1460:
        r4 = org.telegram.messenger.MessageObject.isStickerDocument(r24);
        if (r4 == 0) goto L_0x14e2;
    L_0x1466:
        r0 = r24;
        r4 = r0.thumb;
        r0 = r38;
        r0.currentPhotoObject = r4;
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x14d0;
    L_0x1474:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x1484;
    L_0x147c:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x14d0;
    L_0x1484:
        r4 = 0;
        r6 = r4;
    L_0x1486:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.size();
        if (r6 >= r4) goto L_0x14ae;
    L_0x1490:
        r0 = r24;
        r4 = r0.attributes;
        r4 = r4.get(r6);
        r4 = (org.telegram.tgnet.TLRPC.DocumentAttribute) r4;
        r9 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
        if (r9 == 0) goto L_0x14de;
    L_0x149e:
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = r4.f10140w;
        r6.f10147w = r9;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r4 = r4.f10139h;
        r6.f10146h = r4;
    L_0x14ae:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        if (r4 == 0) goto L_0x14be;
    L_0x14b6:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10146h;
        if (r4 != 0) goto L_0x14d0;
    L_0x14be:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r9 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6.f10146h = r9;
        r4.f10147w = r9;
    L_0x14d0:
        r0 = r24;
        r1 = r38;
        r1.documentAttach = r0;
        r4 = 6;
        r0 = r38;
        r0.documentAttachType = r4;
        r6 = r8;
        goto L_0x0db4;
    L_0x14de:
        r4 = r6 + 1;
        r6 = r4;
        goto L_0x1486;
    L_0x14e2:
        r4 = org.telegram.messenger.MessageObject.isRoundVideoDocument(r24);
        if (r4 == 0) goto L_0x14fe;
    L_0x14e8:
        r0 = r24;
        r4 = r0.thumb;
        r0 = r38;
        r0.currentPhotoObject = r4;
        r0 = r24;
        r1 = r38;
        r1.documentAttach = r0;
        r4 = 7;
        r0 = r38;
        r0.documentAttachType = r4;
        r6 = r8;
        goto L_0x0db4;
    L_0x14fe:
        r0 = r38;
        r1 = r18;
        r2 = r19;
        r0.calcBackgroundWidth(r1, r2, r8);
        r4 = org.telegram.messenger.MessageObject.isStickerDocument(r24);
        if (r4 != 0) goto L_0x1766;
    L_0x150d:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r6 + r18;
        if (r4 >= r6) goto L_0x1527;
    L_0x151b:
        r4 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r18;
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x1527:
        r4 = org.telegram.messenger.MessageObject.isVoiceDocument(r24);
        if (r4 == 0) goto L_0x157f;
    L_0x152d:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.textHeight;
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r6 = r0.linkPreviewHeight;
        r4 = r4 + r6;
        r0 = r38;
        r0.mediaOffsetY = r4;
        r0 = r38;
        r4 = r0.totalHeight;
        r6 = 1110441984; // 0x42300000 float:44.0 double:5.48631236E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r6 = 1110441984; // 0x42300000 float:44.0 double:5.48631236E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r1 = r18;
        r2 = r19;
        r0.calcBackgroundWidth(r1, r2, r8);
        r6 = r8;
        goto L_0x0db4;
    L_0x157f:
        r4 = org.telegram.messenger.MessageObject.isMusicDocument(r24);
        if (r4 == 0) goto L_0x1643;
    L_0x1585:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r0 = r38;
        r1 = r39;
        r4 = r0.createDocumentLayout(r4, r1);
        r0 = r38;
        r6 = r0.currentMessageObject;
        r6 = r6.textHeight;
        r9 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6 = r6 + r9;
        r0 = r38;
        r9 = r0.linkPreviewHeight;
        r6 = r6 + r9;
        r0 = r38;
        r0.mediaOffsetY = r6;
        r0 = r38;
        r6 = r0.totalHeight;
        r9 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6 = r6 + r9;
        r0 = r38;
        r0.totalHeight = r6;
        r0 = r38;
        r6 = r0.linkPreviewHeight;
        r9 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r6 = r6 + r9;
        r0 = r38;
        r0.linkPreviewHeight = r6;
        r6 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r18 = r18 - r6;
        r4 = r4 + r29;
        r6 = 1119617024; // 0x42bc0000 float:94.0 double:5.53164308E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r4 = java.lang.Math.max(r8, r4);
        r0 = r38;
        r6 = r0.songLayout;
        if (r6 == 0) goto L_0x160c;
    L_0x15e7:
        r0 = r38;
        r6 = r0.songLayout;
        r6 = r6.getLineCount();
        if (r6 <= 0) goto L_0x160c;
    L_0x15f1:
        r4 = (float) r4;
        r0 = r38;
        r6 = r0.songLayout;
        r8 = 0;
        r6 = r6.getLineWidth(r8);
        r0 = r29;
        r8 = (float) r0;
        r6 = r6 + r8;
        r8 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r8 = (float) r8;
        r6 = r6 + r8;
        r4 = java.lang.Math.max(r4, r6);
        r4 = (int) r4;
    L_0x160c:
        r0 = r38;
        r6 = r0.performerLayout;
        if (r6 == 0) goto L_0x1637;
    L_0x1612:
        r0 = r38;
        r6 = r0.performerLayout;
        r6 = r6.getLineCount();
        if (r6 <= 0) goto L_0x1637;
    L_0x161c:
        r4 = (float) r4;
        r0 = r38;
        r6 = r0.performerLayout;
        r8 = 0;
        r6 = r6.getLineWidth(r8);
        r0 = r29;
        r8 = (float) r0;
        r6 = r6 + r8;
        r8 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r8 = (float) r8;
        r6 = r6 + r8;
        r4 = java.lang.Math.max(r4, r6);
        r4 = (int) r4;
    L_0x1637:
        r0 = r38;
        r1 = r18;
        r2 = r19;
        r0.calcBackgroundWidth(r1, r2, r4);
        r6 = r4;
        goto L_0x0db4;
    L_0x1643:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r6 = 1126694912; // 0x43280000 float:168.0 double:5.566612494E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 - r6;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r4 = 1;
        r0 = r38;
        r0.drawImageButton = r4;
        r0 = r38;
        r4 = r0.drawPhotoImage;
        if (r4 == 0) goto L_0x169e;
    L_0x1660:
        r0 = r38;
        r4 = r0.totalHeight;
        r6 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r6 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r0 = r38;
        r9 = r0.totalHeight;
        r0 = r38;
        r10 = r0.namesOffset;
        r9 = r9 + r10;
        r10 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r10 = org.telegram.messenger.AndroidUtilities.dp(r10);
        r11 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r11 = org.telegram.messenger.AndroidUtilities.dp(r11);
        r4.setImageCoords(r6, r9, r10, r11);
        r6 = r8;
        goto L_0x0db4;
    L_0x169e:
        r0 = r38;
        r4 = r0.currentMessageObject;
        r4 = r4.textHeight;
        r6 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r6 = r0.linkPreviewHeight;
        r4 = r4 + r6;
        r0 = r38;
        r0.mediaOffsetY = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r0 = r38;
        r9 = r0.totalHeight;
        r0 = r38;
        r10 = r0.namesOffset;
        r9 = r9 + r10;
        r10 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r10 = org.telegram.messenger.AndroidUtilities.dp(r10);
        r9 = r9 - r10;
        r10 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r10 = org.telegram.messenger.AndroidUtilities.dp(r10);
        r11 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r11 = org.telegram.messenger.AndroidUtilities.dp(r11);
        r4.setImageCoords(r6, r9, r10, r11);
        r0 = r38;
        r4 = r0.totalHeight;
        r6 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r6 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r6 = r8;
        goto L_0x0db4;
    L_0x16f9:
        if (r25 == 0) goto L_0x1750;
    L_0x16fb:
        if (r20 == 0) goto L_0x174a;
    L_0x16fd:
        r4 = "photo";
        r0 = r20;
        r4 = r0.equals(r4);
        if (r4 == 0) goto L_0x174a;
    L_0x1708:
        r4 = 1;
    L_0x1709:
        r0 = r38;
        r0.drawImageButton = r4;
        r0 = r39;
        r9 = r0.photoThumbs;
        r0 = r38;
        r4 = r0.drawImageButton;
        if (r4 == 0) goto L_0x174c;
    L_0x1717:
        r4 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
    L_0x171b:
        r0 = r38;
        r6 = r0.drawImageButton;
        if (r6 != 0) goto L_0x174e;
    L_0x1721:
        r6 = 1;
    L_0x1722:
        r4 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r9, r4, r6);
        r0 = r38;
        r0.currentPhotoObject = r4;
        r0 = r39;
        r4 = r0.photoThumbs;
        r6 = 80;
        r4 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r4, r6);
        r0 = r38;
        r0.currentPhotoObjectThumb = r4;
        r0 = r38;
        r4 = r0.currentPhotoObjectThumb;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        if (r4 != r6) goto L_0x1766;
    L_0x1742:
        r4 = 0;
        r0 = r38;
        r0.currentPhotoObjectThumb = r4;
        r6 = r8;
        goto L_0x0db4;
    L_0x174a:
        r4 = 0;
        goto L_0x1709;
    L_0x174c:
        r4 = r7;
        goto L_0x171b;
    L_0x174e:
        r6 = 0;
        goto L_0x1722;
    L_0x1750:
        if (r23 == 0) goto L_0x1766;
    L_0x1752:
        r0 = r23;
        r4 = r0.mime_type;
        r6 = "image/";
        r4 = r4.startsWith(r6);
        if (r4 != 0) goto L_0x1761;
    L_0x175f:
        r23 = 0;
    L_0x1761:
        r4 = 0;
        r0 = r38;
        r0.drawImageButton = r4;
    L_0x1766:
        r6 = r8;
        goto L_0x0db4;
    L_0x1769:
        r4 = 0;
        goto L_0x0e03;
    L_0x176c:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r4 = (float) r4;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r4 = r4 * r7;
        r7 = (int) r4;
        goto L_0x0e41;
    L_0x1777:
        r0 = r38;
        r4 = r0.documentAttachType;
        r8 = 7;
        if (r4 != r8) goto L_0x0e41;
    L_0x177e:
        r7 = org.telegram.messenger.AndroidUtilities.roundMessageSize;
        r0 = r38;
        r4 = r0.photoImage;
        r8 = 1;
        r4.setAllowDecodeSingleFrame(r8);
        goto L_0x0e41;
    L_0x178a:
        r4 = 0;
        goto L_0x0e4d;
    L_0x178d:
        r4 = -1;
        r0 = r23;
        r0.size = r4;
        goto L_0x0e6f;
    L_0x1794:
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 != 0) goto L_0x17a0;
    L_0x179a:
        r0 = r38;
        r4 = r0.hasInvoicePreview;
        if (r4 == 0) goto L_0x17b8;
    L_0x17a0:
        r4 = 640; // 0x280 float:8.97E-43 double:3.16E-321;
        r5 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        r6 = (float) r4;
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r7 = r7 - r8;
        r7 = (float) r7;
        r6 = r6 / r7;
        r4 = (float) r4;
        r4 = r4 / r6;
        r4 = (int) r4;
        r5 = (float) r5;
        r5 = r5 / r6;
        r7 = (int) r5;
        r8 = r7;
        r9 = r4;
        goto L_0x0e7a;
    L_0x17b8:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r6 = r6.f10146h;
        r8 = (float) r4;
        r9 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r7 = r7 - r9;
        r7 = (float) r7;
        r7 = r8 / r7;
        r4 = (float) r4;
        r4 = r4 / r7;
        r4 = (int) r4;
        r6 = (float) r6;
        r6 = r6 / r7;
        r7 = (int) r6;
        if (r5 == 0) goto L_0x17ec;
    L_0x17d7:
        if (r5 == 0) goto L_0x17fe;
    L_0x17d9:
        r5 = r5.toLowerCase();
        r6 = "instagram";
        r5 = r5.equals(r6);
        if (r5 != 0) goto L_0x17fe;
    L_0x17e6:
        r0 = r38;
        r5 = r0.documentAttachType;
        if (r5 != 0) goto L_0x17fe;
    L_0x17ec:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r5 = r5 / 3;
        if (r7 <= r5) goto L_0x354c;
    L_0x17f4:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r7 = r5 / 3;
        r8 = r7;
        r9 = r4;
        goto L_0x0e7a;
    L_0x17fe:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r5 = r5 / 2;
        if (r7 <= r5) goto L_0x354c;
    L_0x1806:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r7 = r5 / 2;
        r8 = r7;
        r9 = r4;
        goto L_0x0e7a;
    L_0x1810:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = r5 + r8;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r4 = r4 + r8;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        goto L_0x0ec6;
    L_0x182b:
        r0 = r38;
        r4 = r0.documentAttachType;
        r5 = 6;
        if (r4 != r5) goto L_0x1860;
    L_0x1832:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r38;
        r5 = r0.documentAttach;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoFilter;
        r8 = 0;
        r0 = r38;
        r9 = r0.currentPhotoObject;
        if (r9 == 0) goto L_0x185e;
    L_0x1846:
        r0 = r38;
        r9 = r0.currentPhotoObject;
        r9 = r9.location;
    L_0x184c:
        r10 = "b1";
        r0 = r38;
        r11 = r0.documentAttach;
        r11 = r11.size;
        r12 = "webp";
        r13 = 1;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11, r12, r13);
        goto L_0x0f26;
    L_0x185e:
        r9 = 0;
        goto L_0x184c;
    L_0x1860:
        r0 = r38;
        r4 = r0.documentAttachType;
        r5 = 4;
        if (r4 != r5) goto L_0x187f;
    L_0x1867:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.location;
        r0 = r38;
        r8 = r0.currentPhotoFilter;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0f26;
    L_0x187f:
        r0 = r38;
        r4 = r0.documentAttachType;
        r5 = 2;
        if (r4 == r5) goto L_0x188d;
    L_0x1886:
        r0 = r38;
        r4 = r0.documentAttachType;
        r5 = 7;
        if (r4 != r5) goto L_0x1928;
    L_0x188d:
        r5 = org.telegram.messenger.FileLoader.getAttachFileName(r24);
        r4 = 0;
        r6 = org.telegram.messenger.MessageObject.isNewGifDocument(r24);
        if (r6 == 0) goto L_0x18e3;
    L_0x1898:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.canDownloadMedia(r6);
    L_0x18a4:
        r6 = r39.isSending();
        if (r6 != 0) goto L_0x1903;
    L_0x18aa:
        r0 = r39;
        r6 = r0.mediaExists;
        if (r6 != 0) goto L_0x18bc;
    L_0x18b0:
        r6 = org.telegram.messenger.FileLoader.getInstance();
        r5 = r6.isLoadingFile(r5);
        if (r5 != 0) goto L_0x18bc;
    L_0x18ba:
        if (r4 == 0) goto L_0x1903;
    L_0x18bc:
        r4 = 0;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = 0;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        if (r5 == 0) goto L_0x1901;
    L_0x18cc:
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r7 = r5.location;
    L_0x18d2:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r0 = r24;
        r9 = r0.size;
        r10 = 0;
        r11 = 0;
        r5 = r24;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0f26;
    L_0x18e3:
        r6 = org.telegram.messenger.MessageObject.isRoundVideoDocument(r24);
        if (r6 == 0) goto L_0x18a4;
    L_0x18e9:
        r0 = r38;
        r4 = r0.photoImage;
        r6 = org.telegram.messenger.AndroidUtilities.roundMessageSize;
        r6 = r6 / 2;
        r4.setRoundRadius(r6);
        r4 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.canDownloadMedia(r6);
        goto L_0x18a4;
    L_0x1901:
        r7 = 0;
        goto L_0x18d2;
    L_0x1903:
        r4 = 1;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        if (r7 == 0) goto L_0x1926;
    L_0x1914:
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.location;
    L_0x191a:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0f26;
    L_0x1926:
        r7 = 0;
        goto L_0x191a;
    L_0x1928:
        r0 = r39;
        r4 = r0.mediaExists;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = org.telegram.messenger.FileLoader.getAttachFileName(r5);
        r0 = r38;
        r6 = r0.hasGamePreview;
        if (r6 != 0) goto L_0x1954;
    L_0x193a:
        if (r4 != 0) goto L_0x1954;
    L_0x193c:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.canDownloadMedia(r6);
        if (r4 != 0) goto L_0x1954;
    L_0x194a:
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r4 = r4.isLoadingFile(r5);
        if (r4 == 0) goto L_0x1981;
    L_0x1954:
        r4 = 0;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = r5.location;
        r0 = r38;
        r6 = r0.currentPhotoFilter;
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        if (r7 == 0) goto L_0x197f;
    L_0x196d:
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        r7 = r7.location;
    L_0x1973:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0f26;
    L_0x197f:
        r7 = 0;
        goto L_0x1973;
    L_0x1981:
        r4 = 1;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.currentPhotoObjectThumb;
        if (r4 == 0) goto L_0x19ba;
    L_0x198c:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        r7 = r7.location;
        r10 = java.util.Locale.US;
        r11 = "%d_%d_b";
        r12 = 2;
        r12 = new java.lang.Object[r12];
        r13 = 0;
        r9 = java.lang.Integer.valueOf(r9);
        r12[r13] = r9;
        r9 = 1;
        r8 = java.lang.Integer.valueOf(r8);
        r12[r9] = r8;
        r8 = java.lang.String.format(r10, r11, r12);
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x0f26;
    L_0x19ba:
        r0 = r38;
        r5 = r0.photoImage;
        r4 = 0;
        r4 = (android.graphics.drawable.Drawable) r4;
        r5.setImageBitmap(r4);
        goto L_0x0f26;
    L_0x19c6:
        r0 = r38;
        r4 = r0.hasGamePreview;
        if (r4 == 0) goto L_0x0f7d;
    L_0x19cc:
        r4 = "AttachGame";
        r5 = 2131230932; // 0x7f0800d4 float:1.807793E38 double:1.052967987E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = r4.toUpperCase();
        r4 = org.telegram.ui.ActionBar.Theme.chat_gamePaint;
        r4 = r4.measureText(r5);
        r6 = (double) r4;
        r6 = java.lang.Math.ceil(r6);
        r4 = (int) r6;
        r0 = r38;
        r0.durationWidth = r4;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_gamePaint;
        r0 = r38;
        r7 = r0.durationWidth;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.videoInfoLayout = r4;
        goto L_0x0f7d;
    L_0x1a01:
        r0 = r38;
        r5 = r0.photoImage;
        r4 = 0;
        r4 = (android.graphics.drawable.Drawable) r4;
        r5.setImageBitmap(r4);
        r0 = r38;
        r4 = r0.linkPreviewHeight;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.linkPreviewHeight = r4;
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r12 = r6;
        goto L_0x0f7e;
    L_0x1a2c:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.test;
        if (r4 == 0) goto L_0x1a46;
    L_0x1a36:
        r4 = "PaymentTestInvoice";
        r5 = 2131232125; // 0x7f08057d float:1.808035E38 double:1.0529685763E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r4 = r4.toUpperCase();
        goto L_0x0f9e;
    L_0x1a46:
        r4 = "PaymentInvoice";
        r5 = 2131232104; // 0x7f080568 float:1.8080308E38 double:1.052968566E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r4 = r4.toUpperCase();
        goto L_0x0f9e;
    L_0x1a56:
        r0 = r38;
        r4 = r0.durationWidth;
        r0 = r38;
        r5 = r0.timeWidth;
        r4 = r4 + r5;
        r5 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r12 = java.lang.Math.max(r4, r12);
        goto L_0x105a;
    L_0x1a6c:
        r0 = r38;
        r4 = r0.drawInstantViewType;
        r5 = 2;
        if (r4 != r5) goto L_0x1a80;
    L_0x1a73:
        r4 = "OpenGroup";
        r5 = 2131232038; // 0x7f080526 float:1.8080174E38 double:1.0529685333E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = r4;
        goto L_0x10b4;
    L_0x1a80:
        r4 = utils.p178a.C3791b.f();
        if (r4 == 0) goto L_0x1a94;
    L_0x1a86:
        r0 = r38;
        r4 = r0.drawInstantViewType;
        r5 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        if (r4 != r5) goto L_0x1a94;
    L_0x1a8e:
        r4 = "پرداخت";
        r5 = r4;
        goto L_0x10b4;
    L_0x1a94:
        r4 = "InstantView";
        r5 = 2131231654; // 0x7f0803a6 float:1.8079395E38 double:1.0529683436E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r5 = r4;
        goto L_0x10b4;
    L_0x1aa1:
        r4 = 0;
        goto L_0x112b;
    L_0x1aa4:
        r0 = r38;
        r5 = r0.photoImage;
        r4 = 0;
        r4 = (android.graphics.drawable.Drawable) r4;
        r5.setImageBitmap(r4);
        r0 = r38;
        r1 = r18;
        r2 = r19;
        r3 = r31;
        r0.calcBackgroundWidth(r1, r2, r3);
        goto L_0x1144;
    L_0x1abb:
        r0 = r39;
        r4 = r0.type;
        r5 = 16;
        if (r4 != r5) goto L_0x1c54;
    L_0x1ac3:
        r4 = 0;
        r0 = r38;
        r0.drawName = r4;
        r4 = 0;
        r0 = r38;
        r0.drawForwardedName = r4;
        r4 = 0;
        r0 = r38;
        r0.drawPhotoImage = r4;
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x1beb;
    L_0x1ad8:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1be7;
    L_0x1ae2:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1be7;
    L_0x1ae8:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1be7;
    L_0x1aee:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1af0:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x1b04:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r4 = r38.getMaxNameWidth();
        r5 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        if (r4 >= 0) goto L_0x3549;
    L_0x1b20:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r12 = r4;
    L_0x1b27:
        r4 = org.telegram.messenger.LocaleController.getInstance();
        r4 = r4.formatterDay;
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r6 = (long) r5;
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 * r8;
        r6 = r4.format(r6);
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.action;
        r4 = (org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall) r4;
        r5 = r4.reason;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
        r7 = r39.isOutOwner();
        if (r7 == 0) goto L_0x1c28;
    L_0x1b4d:
        if (r5 == 0) goto L_0x1c1c;
    L_0x1b4f:
        r5 = "CallMessageOutgoingMissed";
        r7 = 2131231008; // 0x7f080120 float:1.8078085E38 double:1.0529680244E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r7);
    L_0x1b59:
        r7 = r4.duration;
        if (r7 <= 0) goto L_0x3546;
    L_0x1b5d:
        r7 = new java.lang.StringBuilder;
        r7.<init>();
        r6 = r7.append(r6);
        r7 = ", ";
        r6 = r6.append(r7);
        r4 = r4.duration;
        r4 = org.telegram.messenger.LocaleController.formatCallDuration(r4);
        r4 = r6.append(r4);
        r4 = r4.toString();
        r13 = r4;
    L_0x1b7c:
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_audioTitlePaint;
        r7 = (float) r12;
        r8 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r5, r6, r7, r8);
        r6 = org.telegram.ui.ActionBar.Theme.chat_audioTitlePaint;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r7 + r12;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.titleLayout = r4;
        r4 = new android.text.StaticLayout;
        r5 = org.telegram.ui.ActionBar.Theme.chat_contactPhonePaint;
        r6 = (float) r12;
        r7 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r13, r5, r6, r7);
        r6 = org.telegram.ui.ActionBar.Theme.chat_contactPhonePaint;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r7 + r12;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.docTitleLayout = r4;
        r38.setMessageObjectInternal(r39);
        r4 = 1115815936; // 0x42820000 float:65.0 double:5.51286321E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r5 = r0.namesOffset;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x1144;
    L_0x1bd6:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.namesOffset = r4;
        goto L_0x1144;
    L_0x1be7:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1af0;
    L_0x1beb:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1c19;
    L_0x1bf5:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1c19;
    L_0x1bfb:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1c19;
    L_0x1c01:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1c03:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x1b04;
    L_0x1c19:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1c03;
    L_0x1c1c:
        r5 = "CallMessageOutgoing";
        r7 = 2131231007; // 0x7f08011f float:1.8078083E38 double:1.052968024E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r7);
        goto L_0x1b59;
    L_0x1c28:
        if (r5 == 0) goto L_0x1c36;
    L_0x1c2a:
        r5 = "CallMessageIncomingMissed";
        r7 = 2131231006; // 0x7f08011e float:1.807808E38 double:1.0529680234E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r7);
        goto L_0x1b59;
    L_0x1c36:
        r5 = r4.reason;
        r5 = r5 instanceof org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
        if (r5 == 0) goto L_0x1c48;
    L_0x1c3c:
        r5 = "CallMessageIncomingDeclined";
        r7 = 2131231005; // 0x7f08011d float:1.8078079E38 double:1.052968023E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r7);
        goto L_0x1b59;
    L_0x1c48:
        r5 = "CallMessageIncoming";
        r7 = 2131231004; // 0x7f08011c float:1.8078077E38 double:1.0529680224E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r7);
        goto L_0x1b59;
    L_0x1c54:
        r0 = r39;
        r4 = r0.type;
        r5 = 12;
        if (r4 != r5) goto L_0x1e88;
    L_0x1c5c:
        r4 = 0;
        r0 = r38;
        r0.drawName = r4;
        r4 = 1;
        r0 = r38;
        r0.drawForwardedName = r4;
        r4 = 1;
        r0 = r38;
        r0.drawPhotoImage = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1102053376; // 0x41b00000 float:22.0 double:5.44486713E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4.setRoundRadius(r5);
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x1e1c;
    L_0x1c7e:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1e18;
    L_0x1c88:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1e18;
    L_0x1c8e:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1e18;
    L_0x1c94:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1c96:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x1caa:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1106771968; // 0x41f80000 float:31.0 double:5.46818007E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.user_id;
        r5 = org.telegram.messenger.MessagesController.getInstance();
        r4 = java.lang.Integer.valueOf(r4);
        r7 = r5.getUser(r4);
        r4 = r38.getMaxNameWidth();
        r5 = 1121714176; // 0x42dc0000 float:110.0 double:5.54200439E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        if (r4 >= 0) goto L_0x3543;
    L_0x1cda:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r13 = r4;
    L_0x1ce1:
        r4 = 0;
        if (r7 == 0) goto L_0x3540;
    L_0x1ce4:
        r5 = r7.photo;
        if (r5 == 0) goto L_0x1cec;
    L_0x1ce8:
        r4 = r7.photo;
        r4 = r4.photo_small;
    L_0x1cec:
        r0 = r38;
        r5 = r0.contactAvatarDrawable;
        r5.setInfo(r7);
        r5 = r4;
    L_0x1cf4:
        r0 = r38;
        r4 = r0.photoImage;
        r6 = "50_50";
        if (r7 == 0) goto L_0x1e4d;
    L_0x1cfd:
        r0 = r38;
        r7 = r0.contactAvatarDrawable;
    L_0x1d01:
        r8 = 0;
        r9 = 0;
        r4.setImage(r5, r6, r7, r8, r9);
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.phone_number;
        if (r4 == 0) goto L_0x1e5c;
    L_0x1d10:
        r5 = r4.length();
        if (r5 == 0) goto L_0x1e5c;
    L_0x1d16:
        r5 = org.telegram.p149a.C2488b.a();
        r4 = r5.e(r4);
        r12 = r4;
    L_0x1d1f:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.first_name;
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.last_name;
        r4 = org.telegram.messenger.ContactsController.formatName(r4, r5);
        r5 = 10;
        r6 = 32;
        r4 = r4.replace(r5, r6);
        r5 = r4.length();
        if (r5 != 0) goto L_0x353d;
    L_0x1d41:
        r5 = r12;
    L_0x1d42:
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_contactNamePaint;
        r7 = (float) r13;
        r8 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r5, r6, r7, r8);
        r6 = org.telegram.ui.ActionBar.Theme.chat_contactNamePaint;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r7 + r13;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.titleLayout = r4;
        r4 = new android.text.StaticLayout;
        r5 = 10;
        r6 = 32;
        r5 = r12.replace(r5, r6);
        r6 = org.telegram.ui.ActionBar.Theme.chat_contactPhonePaint;
        r7 = (float) r13;
        r8 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r5, r6, r7, r8);
        r6 = org.telegram.ui.ActionBar.Theme.chat_contactPhonePaint;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r7 + r13;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.docTitleLayout = r4;
        r38.setMessageObjectInternal(r39);
        r0 = r38;
        r4 = r0.drawForwardedName;
        if (r4 == 0) goto L_0x1e69;
    L_0x1d95:
        r4 = r39.needDrawForwarded();
        if (r4 == 0) goto L_0x1e69;
    L_0x1d9b:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x1da9;
    L_0x1da1:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.minY;
        if (r4 != 0) goto L_0x1e69;
    L_0x1da9:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.namesOffset = r4;
    L_0x1db8:
        r4 = 1116471296; // 0x428c0000 float:70.0 double:5.51610112E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r5 = r0.namesOffset;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x1ddc;
    L_0x1dcd:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.namesOffset = r4;
    L_0x1ddc:
        r0 = r38;
        r4 = r0.docTitleLayout;
        r4 = r4.getLineCount();
        if (r4 <= 0) goto L_0x1144;
    L_0x1de6:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1121714176; // 0x42dc0000 float:110.0 double:5.54200439E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r5 = r0.docTitleLayout;
        r6 = 0;
        r5 = r5.getLineWidth(r6);
        r6 = (double) r5;
        r6 = java.lang.Math.ceil(r6);
        r5 = (int) r6;
        r4 = r4 - r5;
        r0 = r38;
        r5 = r0.timeWidth;
        if (r4 >= r5) goto L_0x1144;
    L_0x1e07:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        goto L_0x1144;
    L_0x1e18:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1c96;
    L_0x1e1c:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1e4a;
    L_0x1e26:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1e4a;
    L_0x1e2c:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1e4a;
    L_0x1e32:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1e34:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x1caa;
    L_0x1e4a:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1e34;
    L_0x1e4d:
        r8 = org.telegram.ui.ActionBar.Theme.chat_contactDrawable;
        r7 = r39.isOutOwner();
        if (r7 == 0) goto L_0x1e5a;
    L_0x1e55:
        r7 = 1;
    L_0x1e56:
        r7 = r8[r7];
        goto L_0x1d01;
    L_0x1e5a:
        r7 = 0;
        goto L_0x1e56;
    L_0x1e5c:
        r4 = "NumberUnknown";
        r5 = 2131232017; // 0x7f080511 float:1.8080131E38 double:1.052968523E-314;
        r4 = org.telegram.messenger.LocaleController.getString(r4, r5);
        r12 = r4;
        goto L_0x1d1f;
    L_0x1e69:
        r0 = r38;
        r4 = r0.drawNameLayout;
        if (r4 == 0) goto L_0x1db8;
    L_0x1e6f:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.reply_to_msg_id;
        if (r4 != 0) goto L_0x1db8;
    L_0x1e77:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.namesOffset = r4;
        goto L_0x1db8;
    L_0x1e88:
        r0 = r39;
        r4 = r0.type;
        r5 = 2;
        if (r4 != r5) goto L_0x1f2d;
    L_0x1e8f:
        r4 = 1;
        r0 = r38;
        r0.drawForwardedName = r4;
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x1efd;
    L_0x1e9a:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1efa;
    L_0x1ea4:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1efa;
    L_0x1eaa:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1efa;
    L_0x1eb0:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1eb2:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x1ec6:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r38.setMessageObjectInternal(r39);
        r4 = 1116471296; // 0x428c0000 float:70.0 double:5.51610112E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r5 = r0.namesOffset;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x1144;
    L_0x1ee9:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.namesOffset = r4;
        goto L_0x1144;
    L_0x1efa:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1eb2;
    L_0x1efd:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1f2a;
    L_0x1f07:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1f2a;
    L_0x1f0d:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1f2a;
    L_0x1f13:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1f15:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x1ec6;
    L_0x1f2a:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1f15;
    L_0x1f2d:
        r0 = r39;
        r4 = r0.type;
        r5 = 14;
        if (r4 != r5) goto L_0x1fce;
    L_0x1f35:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x1f9e;
    L_0x1f3b:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1f9b;
    L_0x1f45:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1f9b;
    L_0x1f4b:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1f9b;
    L_0x1f51:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1f53:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x1f67:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r38.setMessageObjectInternal(r39);
        r4 = 1118044160; // 0x42a40000 float:82.0 double:5.5238721E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r5 = r0.namesOffset;
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x1144;
    L_0x1f8a:
        r0 = r38;
        r4 = r0.namesOffset;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.namesOffset = r4;
        goto L_0x1144;
    L_0x1f9b:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1f53;
    L_0x1f9e:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x1fcb;
    L_0x1fa8:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x1fcb;
    L_0x1fae:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x1fcb;
    L_0x1fb4:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x1fb6:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x1f67;
    L_0x1fcb:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x1fb6;
    L_0x1fce:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.fwd_from;
        if (r4 == 0) goto L_0x2201;
    L_0x1fd6:
        r0 = r39;
        r4 = r0.type;
        r5 = 13;
        if (r4 == r5) goto L_0x2201;
    L_0x1fde:
        r4 = 1;
    L_0x1fdf:
        r0 = r38;
        r0.drawForwardedName = r4;
        r0 = r39;
        r4 = r0.type;
        r5 = 9;
        if (r4 == r5) goto L_0x2204;
    L_0x1feb:
        r4 = 1;
    L_0x1fec:
        r0 = r38;
        r0.mediaBackground = r4;
        r4 = 1;
        r0 = r38;
        r0.drawImageButton = r4;
        r4 = 1;
        r0 = r38;
        r0.drawPhotoImage = r4;
        r7 = 0;
        r6 = 0;
        r14 = 0;
        r0 = r39;
        r4 = r0.gifState;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1));
        if (r4 == 0) goto L_0x2026;
    L_0x2007:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r4 = r4.canAutoplayGifs();
        if (r4 != 0) goto L_0x2026;
    L_0x2011:
        r0 = r39;
        r4 = r0.type;
        r5 = 8;
        if (r4 == r5) goto L_0x2020;
    L_0x2019:
        r0 = r39;
        r4 = r0.type;
        r5 = 5;
        if (r4 != r5) goto L_0x2026;
    L_0x2020:
        r4 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = r39;
        r0.gifState = r4;
    L_0x2026:
        r4 = r39.isRoundVideo();
        if (r4 == 0) goto L_0x220a;
    L_0x202c:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setAllowDecodeSingleFrame(r5);
        r0 = r38;
        r5 = r0.photoImage;
        r4 = org.telegram.messenger.MediaController.getInstance();
        r4 = r4.getPlayingMessageObject();
        if (r4 != 0) goto L_0x2207;
    L_0x2042:
        r4 = 1;
    L_0x2043:
        r5.setAllowStartAnimation(r4);
    L_0x2046:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = r39.isSecretPhoto();
        r4.setForcePreview(r5);
        r0 = r39;
        r4 = r0.type;
        r5 = 9;
        if (r4 != r5) goto L_0x2278;
    L_0x2059:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x2223;
    L_0x205f:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x221f;
    L_0x2069:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x221f;
    L_0x206f:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x221f;
    L_0x2075:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x2077:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x208b:
        r4 = r38.checkNeedDrawShareButton(r39);
        if (r4 == 0) goto L_0x20a0;
    L_0x2091:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x20a0:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1124728832; // 0x430a0000 float:138.0 double:5.55689877E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r0 = r39;
        r5 = r0.caption;
        r5 = android.text.TextUtils.isEmpty(r5);
        if (r5 != 0) goto L_0x20c3;
    L_0x20bc:
        r5 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
    L_0x20c3:
        r0 = r38;
        r5 = r0.drawPhotoImage;
        if (r5 == 0) goto L_0x2254;
    L_0x20c9:
        r5 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
    L_0x20d5:
        r0 = r38;
        r0.availableTimeWidth = r4;
        r0 = r38;
        r4 = r0.drawPhotoImage;
        if (r4 != 0) goto L_0x211e;
    L_0x20df:
        r0 = r39;
        r4 = r0.caption;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 == 0) goto L_0x211e;
    L_0x20e9:
        r0 = r38;
        r4 = r0.infoLayout;
        r4 = r4.getLineCount();
        if (r4 <= 0) goto L_0x211e;
    L_0x20f3:
        r38.measureTime(r39);
        r0 = r38;
        r4 = r0.backgroundWidth;
        r7 = 1123287040; // 0x42f40000 float:122.0 double:5.54977537E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 - r7;
        r0 = r38;
        r7 = r0.infoLayout;
        r8 = 0;
        r7 = r7.getLineWidth(r8);
        r8 = (double) r7;
        r8 = java.lang.Math.ceil(r8);
        r7 = (int) r8;
        r4 = r4 - r7;
        r0 = r38;
        r7 = r0.timeWidth;
        if (r4 >= r7) goto L_0x211e;
    L_0x2117:
        r4 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = r5 + r4;
    L_0x211e:
        r38.setMessageObjectInternal(r39);
        r0 = r38;
        r4 = r0.drawForwardedName;
        if (r4 == 0) goto L_0x30da;
    L_0x2127:
        r4 = r39.needDrawForwarded();
        if (r4 == 0) goto L_0x30da;
    L_0x212d:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x213b;
    L_0x2133:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.minY;
        if (r4 != 0) goto L_0x30da;
    L_0x213b:
        r0 = r39;
        r4 = r0.type;
        r7 = 5;
        if (r4 == r7) goto L_0x2151;
    L_0x2142:
        r0 = r38;
        r4 = r0.namesOffset;
        r7 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 + r7;
        r0 = r38;
        r0.namesOffset = r4;
    L_0x2151:
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r5;
        r0 = r38;
        r7 = r0.namesOffset;
        r4 = r4 + r7;
        r4 = r4 + r14;
        r0 = r38;
        r0.totalHeight = r4;
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x2181;
    L_0x2168:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.flags;
        r4 = r4 & 8;
        if (r4 != 0) goto L_0x2181;
    L_0x2172:
        r0 = r38;
        r4 = r0.totalHeight;
        r7 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 - r7;
        r0 = r38;
        r0.totalHeight = r4;
    L_0x2181:
        r4 = 0;
        r0 = r38;
        r7 = r0.currentPosition;
        if (r7 == 0) goto L_0x21d3;
    L_0x2188:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.flags;
        r7 = r7 & 2;
        if (r7 != 0) goto L_0x2199;
    L_0x2192:
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r6 = r6 + r7;
    L_0x2199:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.flags;
        r7 = r7 & 1;
        if (r7 != 0) goto L_0x21aa;
    L_0x21a3:
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r6 = r6 + r7;
    L_0x21aa:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.flags;
        r7 = r7 & 4;
        if (r7 != 0) goto L_0x21c2;
    L_0x21b4:
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r5 = r5 + r7;
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 - r7;
    L_0x21c2:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.flags;
        r7 = r7 & 8;
        if (r7 != 0) goto L_0x21d3;
    L_0x21cc:
        r7 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r5 = r5 + r7;
    L_0x21d3:
        r0 = r38;
        r7 = r0.drawPinnedTop;
        if (r7 == 0) goto L_0x21e8;
    L_0x21d9:
        r0 = r38;
        r7 = r0.namesOffset;
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r8 = org.telegram.messenger.AndroidUtilities.dp(r8);
        r7 = r7 - r8;
        r0 = r38;
        r0.namesOffset = r7;
    L_0x21e8:
        r0 = r38;
        r7 = r0.photoImage;
        r8 = 0;
        r9 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r9 = org.telegram.messenger.AndroidUtilities.dp(r9);
        r0 = r38;
        r10 = r0.namesOffset;
        r9 = r9 + r10;
        r4 = r4 + r9;
        r7.setImageCoords(r8, r4, r6, r5);
        r38.invalidate();
        goto L_0x1144;
    L_0x2201:
        r4 = 0;
        goto L_0x1fdf;
    L_0x2204:
        r4 = 0;
        goto L_0x1fec;
    L_0x2207:
        r4 = 0;
        goto L_0x2043;
    L_0x220a:
        r0 = r38;
        r5 = r0.photoImage;
        r0 = r39;
        r4 = r0.gifState;
        r8 = 0;
        r4 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r4 != 0) goto L_0x221d;
    L_0x2217:
        r4 = 1;
    L_0x2218:
        r5.setAllowStartAnimation(r4);
        goto L_0x2046;
    L_0x221d:
        r4 = 0;
        goto L_0x2218;
    L_0x221f:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x2077;
    L_0x2223:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x2251;
    L_0x222d:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x2251;
    L_0x2233:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x2251;
    L_0x2239:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x223b:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x208b;
    L_0x2251:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x223b;
    L_0x2254:
        r5 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r0 = r39;
        r5 = r0.caption;
        r5 = android.text.TextUtils.isEmpty(r5);
        if (r5 == 0) goto L_0x2275;
    L_0x226a:
        r5 = 1112276992; // 0x424c0000 float:51.0 double:5.495378504E-315;
    L_0x226c:
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r5 = r6;
        r6 = r7;
        goto L_0x20d5;
    L_0x2275:
        r5 = 1101529088; // 0x41a80000 float:21.0 double:5.442276803E-315;
        goto L_0x226c;
    L_0x2278:
        r0 = r39;
        r4 = r0.type;
        r5 = 4;
        if (r4 != r5) goto L_0x26ca;
    L_0x227f:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.geo;
        r0 = r4.lat;
        r20 = r0;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.geo;
        r0 = r4._long;
        r22 = r0;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
        if (r4 == 0) goto L_0x250e;
    L_0x22a1:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x249e;
    L_0x22a7:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x249a;
    L_0x22b1:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x249a;
    L_0x22b7:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x249a;
    L_0x22bd:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x22bf:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1133543424; // 0x43908000 float:289.0 double:5.60044864E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x22d4:
        r4 = r38.checkNeedDrawShareButton(r39);
        if (r4 == 0) goto L_0x22e9;
    L_0x22da:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x22e9:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1108606976; // 0x42140000 float:37.0 double:5.477246216E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r5 = 1113063424; // 0x42580000 float:54.0 double:5.499263994E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r7 = r4 - r5;
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1101529088; // 0x41a80000 float:21.0 double:5.442276803E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r18 = r4 - r5;
        r4 = 1128464384; // 0x43430000 float:195.0 double:5.575354847E-315;
        r15 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r8 = (double) r4;
        r10 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r8 = r8 / r10;
        r10 = (double) r4;
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r24 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r24 = r24 * r20;
        r26 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r24 = r24 / r26;
        r24 = java.lang.Math.sin(r24);
        r12 = r12 + r24;
        r24 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r26 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r20 = r20 * r26;
        r26 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r20 = r20 / r26;
        r20 = java.lang.Math.sin(r20);
        r20 = r24 - r20;
        r12 = r12 / r20;
        r12 = java.lang.Math.log(r12);
        r12 = r12 * r8;
        r20 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r12 = r12 / r20;
        r10 = r10 - r12;
        r10 = java.lang.Math.round(r10);
        r5 = 1092930765; // 0x4124cccd float:10.3 double:5.399795443E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = r5 << 6;
        r12 = (long) r5;
        r10 = r10 - r12;
        r10 = (double) r10;
        r12 = 4609753056924675352; // 0x3ff921fb54442d18 float:3.37028055E12 double:1.5707963267948966;
        r20 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r4 = (double) r4;
        r4 = r10 - r4;
        r4 = r4 / r8;
        r4 = java.lang.Math.exp(r4);
        r4 = java.lang.Math.atan(r4);
        r4 = r4 * r20;
        r4 = r12 - r4;
        r8 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r4 = r4 * r8;
        r8 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r4 = r4 / r8;
        r6 = java.util.Locale.US;
        r8 = "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=%dx%d&maptype=roadmap&scale=%d&sensor=false";
        r9 = 5;
        r9 = new java.lang.Object[r9];
        r10 = 0;
        r4 = java.lang.Double.valueOf(r4);
        r9[r10] = r4;
        r4 = 1;
        r5 = java.lang.Double.valueOf(r22);
        r9[r4] = r5;
        r4 = 2;
        r0 = r18;
        r5 = (float) r0;
        r10 = org.telegram.messenger.AndroidUtilities.density;
        r5 = r5 / r10;
        r5 = (int) r5;
        r5 = java.lang.Integer.valueOf(r5);
        r9[r4] = r5;
        r4 = 3;
        r5 = (float) r15;
        r10 = org.telegram.messenger.AndroidUtilities.density;
        r5 = r5 / r10;
        r5 = (int) r5;
        r5 = java.lang.Integer.valueOf(r5);
        r9[r4] = r5;
        r4 = 4;
        r5 = 2;
        r10 = org.telegram.messenger.AndroidUtilities.density;
        r10 = (double) r10;
        r10 = java.lang.Math.ceil(r10);
        r10 = (int) r10;
        r5 = java.lang.Math.min(r5, r10);
        r5 = java.lang.Integer.valueOf(r5);
        r9[r4] = r5;
        r4 = java.lang.String.format(r6, r8, r9);
        r0 = r38;
        r0.currentUrl = r4;
        r4 = r38.isCurrentLocationTimeExpired(r39);
        r0 = r38;
        r0.locationExpired = r4;
        if (r4 != 0) goto L_0x24d0;
    L_0x23dd:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setCrossfadeWithOldImage(r5);
        r4 = 0;
        r0 = r38;
        r0.mediaBackground = r4;
        r4 = 1113587712; // 0x42600000 float:56.0 double:5.50185432E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r5 = r0.invalidateRunnable;
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        org.telegram.messenger.AndroidUtilities.runOnUIThread(r5, r8);
        r5 = 1;
        r0 = r38;
        r0.scheduledInvalidate = r5;
        r14 = r4;
    L_0x23ff:
        r4 = new android.text.StaticLayout;
        r5 = "AttachLiveLocation";
        r6 = 2131232843; // 0x7f08084b float:1.8081807E38 double:1.052968931E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r5, r6);
        r6 = org.telegram.ui.ActionBar.Theme.chat_locationTitlePaint;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.docTitleLayout = r4;
        r4 = 0;
        r38.updateCurrentUserAndChat();
        r0 = r38;
        r5 = r0.currentUser;
        if (r5 == 0) goto L_0x24e1;
    L_0x2424:
        r0 = r38;
        r5 = r0.currentUser;
        r5 = r5.photo;
        if (r5 == 0) goto L_0x2434;
    L_0x242c:
        r0 = r38;
        r4 = r0.currentUser;
        r4 = r4.photo;
        r4 = r4.photo_small;
    L_0x2434:
        r0 = r38;
        r5 = r0.contactAvatarDrawable;
        r0 = r38;
        r6 = r0.currentUser;
        r5.setInfo(r6);
        r9 = r4;
    L_0x2440:
        r0 = r38;
        r8 = r0.locationImageReceiver;
        r10 = "50_50";
        r0 = r38;
        r11 = r0.contactAvatarDrawable;
        r12 = 0;
        r13 = 0;
        r8.setImage(r9, r10, r11, r12, r13);
        r4 = new android.text.StaticLayout;
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.edit_date;
        if (r5 == 0) goto L_0x2505;
    L_0x245a:
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.edit_date;
        r8 = (long) r5;
    L_0x2461:
        r5 = org.telegram.messenger.LocaleController.formatLocationUpdateDate(r8);
        r6 = org.telegram.ui.ActionBar.Theme.chat_locationAddressPaint;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.infoLayout = r4;
        r10 = r15;
        r11 = r18;
    L_0x2477:
        r0 = r38;
        r4 = r0.currentUrl;
        if (r4 == 0) goto L_0x2496;
    L_0x247d:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r38;
        r5 = r0.currentUrl;
        r6 = 0;
        r8 = org.telegram.ui.ActionBar.Theme.chat_locationDrawable;
        r7 = r39.isOutOwner();
        if (r7 == 0) goto L_0x26c7;
    L_0x248e:
        r7 = 1;
    L_0x248f:
        r7 = r8[r7];
        r8 = 0;
        r9 = 0;
        r4.setImage(r5, r6, r7, r8, r9);
    L_0x2496:
        r5 = r10;
        r6 = r11;
        goto L_0x211e;
    L_0x249a:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x22bf;
    L_0x249e:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x24cd;
    L_0x24a8:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x24cd;
    L_0x24ae:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x24cd;
    L_0x24b4:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x24b6:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1133543424; // 0x43908000 float:289.0 double:5.60044864E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x22d4;
    L_0x24cd:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x24b6;
    L_0x24d0:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x23ff;
    L_0x24e1:
        r0 = r38;
        r5 = r0.currentChat;
        if (r5 == 0) goto L_0x353a;
    L_0x24e7:
        r0 = r38;
        r5 = r0.currentChat;
        r5 = r5.photo;
        if (r5 == 0) goto L_0x24f7;
    L_0x24ef:
        r0 = r38;
        r4 = r0.currentChat;
        r4 = r4.photo;
        r4 = r4.photo_small;
    L_0x24f7:
        r0 = r38;
        r5 = r0.contactAvatarDrawable;
        r0 = r38;
        r6 = r0.currentChat;
        r5.setInfo(r6);
        r9 = r4;
        goto L_0x2440;
    L_0x2505:
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.date;
        r8 = (long) r5;
        goto L_0x2461;
    L_0x250e:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.title;
        r4 = android.text.TextUtils.isEmpty(r4);
        if (r4 != 0) goto L_0x2662;
    L_0x251c:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x262a;
    L_0x2522:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x2626;
    L_0x252c:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x2626;
    L_0x2532:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x2626;
    L_0x2538:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x253a:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x254e:
        r4 = r38.checkNeedDrawShareButton(r39);
        if (r4 == 0) goto L_0x2563;
    L_0x2554:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x2563:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r5 = 1123418112; // 0x42f60000 float:123.0 double:5.55042295E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r6 = r4 - r5;
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.title;
        r5 = org.telegram.ui.ActionBar.Theme.chat_locationTitlePaint;
        r7 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r9 = 0;
        r10 = 0;
        r11 = android.text.TextUtils.TruncateAt.END;
        r13 = 2;
        r12 = r6;
        r4 = org.telegram.ui.Components.StaticLayoutEx.createStaticLayout(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);
        r0 = r38;
        r0.docTitleLayout = r4;
        r0 = r38;
        r4 = r0.docTitleLayout;
        r12 = r4.getLineCount();
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.address;
        if (r4 == 0) goto L_0x265b;
    L_0x259d:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.address;
        r4 = r4.length();
        if (r4 <= 0) goto L_0x265b;
    L_0x25ab:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.address;
        r5 = org.telegram.ui.ActionBar.Theme.chat_locationAddressPaint;
        r7 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r9 = 0;
        r10 = 0;
        r11 = android.text.TextUtils.TruncateAt.END;
        r13 = 3;
        r12 = 3 - r12;
        r13 = java.lang.Math.min(r13, r12);
        r12 = r6;
        r4 = org.telegram.ui.Components.StaticLayoutEx.createStaticLayout(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13);
        r0 = r38;
        r0.infoLayout = r4;
    L_0x25cd:
        r4 = 0;
        r0 = r38;
        r0.mediaBackground = r4;
        r0 = r38;
        r0.availableTimeWidth = r6;
        r4 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = 1118568448; // 0x42ac0000 float:86.0 double:5.526462427E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r6 = java.util.Locale.US;
        r7 = "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=72x72&maptype=roadmap&scale=%d&markers=color:red|size:mid|%f,%f&sensor=false";
        r8 = 5;
        r8 = new java.lang.Object[r8];
        r9 = 0;
        r10 = java.lang.Double.valueOf(r20);
        r8[r9] = r10;
        r9 = 1;
        r10 = java.lang.Double.valueOf(r22);
        r8[r9] = r10;
        r9 = 2;
        r10 = 2;
        r11 = org.telegram.messenger.AndroidUtilities.density;
        r12 = (double) r11;
        r12 = java.lang.Math.ceil(r12);
        r11 = (int) r12;
        r10 = java.lang.Math.min(r10, r11);
        r10 = java.lang.Integer.valueOf(r10);
        r8[r9] = r10;
        r9 = 3;
        r10 = java.lang.Double.valueOf(r20);
        r8[r9] = r10;
        r9 = 4;
        r10 = java.lang.Double.valueOf(r22);
        r8[r9] = r10;
        r6 = java.lang.String.format(r6, r7, r8);
        r0 = r38;
        r0.currentUrl = r6;
        r10 = r4;
        r11 = r5;
        goto L_0x2477;
    L_0x2626:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x253a;
    L_0x262a:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r4.x;
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x2658;
    L_0x2634:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x2658;
    L_0x263a:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x2658;
    L_0x2640:
        r4 = 1120665600; // 0x42cc0000 float:102.0 double:5.536823734E-315;
    L_0x2642:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r5 - r4;
        r5 = 1132920832; // 0x43870000 float:270.0 double:5.597372625E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = java.lang.Math.min(r4, r5);
        r0 = r38;
        r0.backgroundWidth = r4;
        goto L_0x254e;
    L_0x2658:
        r4 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        goto L_0x2642;
    L_0x265b:
        r4 = 0;
        r0 = r38;
        r0.infoLayout = r4;
        goto L_0x25cd;
    L_0x2662:
        r4 = 1127874560; // 0x433a0000 float:186.0 double:5.57244073E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r0.availableTimeWidth = r4;
        r4 = 1128792064; // 0x43480000 float:200.0 double:5.5769738E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r6 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r6 + r5;
        r0 = r38;
        r0.backgroundWidth = r6;
        r6 = java.util.Locale.US;
        r7 = "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=200x100&maptype=roadmap&scale=%d&markers=color:red|size:mid|%f,%f&sensor=false";
        r8 = 5;
        r8 = new java.lang.Object[r8];
        r9 = 0;
        r10 = java.lang.Double.valueOf(r20);
        r8[r9] = r10;
        r9 = 1;
        r10 = java.lang.Double.valueOf(r22);
        r8[r9] = r10;
        r9 = 2;
        r10 = 2;
        r11 = org.telegram.messenger.AndroidUtilities.density;
        r12 = (double) r11;
        r12 = java.lang.Math.ceil(r12);
        r11 = (int) r12;
        r10 = java.lang.Math.min(r10, r11);
        r10 = java.lang.Integer.valueOf(r10);
        r8[r9] = r10;
        r9 = 3;
        r10 = java.lang.Double.valueOf(r20);
        r8[r9] = r10;
        r9 = 4;
        r10 = java.lang.Double.valueOf(r22);
        r8[r9] = r10;
        r6 = java.lang.String.format(r6, r7, r8);
        r0 = r38;
        r0.currentUrl = r6;
        r10 = r4;
        r11 = r5;
        goto L_0x2477;
    L_0x26c7:
        r7 = 0;
        goto L_0x248f;
    L_0x26ca:
        r0 = r39;
        r4 = r0.type;
        r5 = 13;
        if (r4 != r5) goto L_0x2822;
    L_0x26d2:
        r4 = 0;
        r0 = r38;
        r0.drawBackground = r4;
        r4 = 0;
        r5 = r4;
    L_0x26d9:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.attributes;
        r4 = r4.size();
        if (r5 >= r4) goto L_0x3536;
    L_0x26e9:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.attributes;
        r4 = r4.get(r5);
        r4 = (org.telegram.tgnet.TLRPC.DocumentAttribute) r4;
        r8 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
        if (r8 == 0) goto L_0x27ad;
    L_0x26fd:
        r5 = r4.f10140w;
        r4 = r4.f10139h;
    L_0x2701:
        r6 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r6 == 0) goto L_0x27b2;
    L_0x2707:
        r6 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r6 = (float) r6;
        r7 = 1053609165; // 0x3ecccccd float:0.4 double:5.205520926E-315;
        r6 = r6 * r7;
        r7 = r6;
    L_0x2711:
        if (r5 != 0) goto L_0x271b;
    L_0x2713:
        r4 = (int) r7;
        r5 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r5 = r5 + r4;
    L_0x271b:
        r4 = (float) r4;
        r5 = (float) r5;
        r5 = r6 / r5;
        r4 = r4 * r5;
        r4 = (int) r4;
        r5 = (int) r6;
        r6 = (float) r4;
        r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1));
        if (r6 <= 0) goto L_0x3531;
    L_0x2727:
        r5 = (float) r5;
        r4 = (float) r4;
        r4 = r7 / r4;
        r4 = r4 * r5;
        r5 = (int) r4;
        r4 = (int) r7;
        r15 = r4;
        r18 = r5;
    L_0x2731:
        r4 = 6;
        r0 = r38;
        r0.documentAttachType = r4;
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r18 - r4;
        r0 = r38;
        r0.availableTimeWidth = r4;
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r18;
        r0 = r38;
        r0.backgroundWidth = r4;
        r0 = r39;
        r4 = r0.photoThumbs;
        r5 = 80;
        r4 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r4, r5);
        r0 = r38;
        r0.currentPhotoObjectThumb = r4;
        r0 = r39;
        r4 = r0.attachPathExists;
        if (r4 == 0) goto L_0x27c7;
    L_0x2762:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r0 = r39;
        r6 = r0.messageOwner;
        r6 = r6.attachPath;
        r7 = java.util.Locale.US;
        r8 = "%d_%d";
        r9 = 2;
        r9 = new java.lang.Object[r9];
        r10 = 0;
        r11 = java.lang.Integer.valueOf(r18);
        r9[r10] = r11;
        r10 = 1;
        r11 = java.lang.Integer.valueOf(r15);
        r9[r10] = r11;
        r7 = java.lang.String.format(r7, r8, r9);
        r8 = 0;
        r0 = r38;
        r9 = r0.currentPhotoObjectThumb;
        if (r9 == 0) goto L_0x27c5;
    L_0x278e:
        r0 = r38;
        r9 = r0.currentPhotoObjectThumb;
        r9 = r9.location;
    L_0x2794:
        r10 = "b1";
        r0 = r39;
        r11 = r0.messageOwner;
        r11 = r11.media;
        r11 = r11.document;
        r11 = r11.size;
        r12 = "webp";
        r13 = 1;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11, r12, r13);
    L_0x27a8:
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x27ad:
        r4 = r5 + 1;
        r5 = r4;
        goto L_0x26d9;
    L_0x27b2:
        r6 = org.telegram.messenger.AndroidUtilities.displaySize;
        r6 = r6.x;
        r7 = org.telegram.messenger.AndroidUtilities.displaySize;
        r7 = r7.y;
        r6 = java.lang.Math.min(r6, r7);
        r6 = (float) r6;
        r7 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r6 = r6 * r7;
        r7 = r6;
        goto L_0x2711;
    L_0x27c5:
        r9 = 0;
        goto L_0x2794;
    L_0x27c7:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.id;
        r6 = 0;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 == 0) goto L_0x27a8;
    L_0x27d7:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.document;
        r6 = 0;
        r7 = java.util.Locale.US;
        r8 = "%d_%d";
        r9 = 2;
        r9 = new java.lang.Object[r9];
        r10 = 0;
        r11 = java.lang.Integer.valueOf(r18);
        r9[r10] = r11;
        r10 = 1;
        r11 = java.lang.Integer.valueOf(r15);
        r9[r10] = r11;
        r7 = java.lang.String.format(r7, r8, r9);
        r8 = 0;
        r0 = r38;
        r9 = r0.currentPhotoObjectThumb;
        if (r9 == 0) goto L_0x2820;
    L_0x2805:
        r0 = r38;
        r9 = r0.currentPhotoObjectThumb;
        r9 = r9.location;
    L_0x280b:
        r10 = "b1";
        r0 = r39;
        r11 = r0.messageOwner;
        r11 = r11.media;
        r11 = r11.document;
        r11 = r11.size;
        r12 = "webp";
        r13 = 1;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11, r12, r13);
        goto L_0x27a8;
    L_0x2820:
        r9 = 0;
        goto L_0x280b;
    L_0x2822:
        r0 = r39;
        r4 = r0.type;
        r5 = 5;
        if (r4 != r5) goto L_0x29b9;
    L_0x2829:
        r4 = org.telegram.messenger.AndroidUtilities.roundMessageSize;
        r5 = r4;
    L_0x282c:
        r6 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r6 + r5;
        r0 = r39;
        r7 = r0.type;
        r8 = 5;
        if (r7 == r8) goto L_0x352e;
    L_0x283a:
        r7 = r38.checkNeedDrawShareButton(r39);
        if (r7 == 0) goto L_0x352e;
    L_0x2840:
        r7 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 - r7;
        r7 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r5 = r5 - r7;
        r12 = r4;
    L_0x284f:
        r4 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
        if (r5 <= r4) goto L_0x352b;
    L_0x2855:
        r5 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
        r13 = r5;
    L_0x285a:
        r4 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
        if (r6 <= r4) goto L_0x3528;
    L_0x2860:
        r4 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
        r15 = r4;
    L_0x2865:
        r0 = r39;
        r4 = r0.type;
        r5 = 1;
        if (r4 != r5) goto L_0x29e1;
    L_0x286c:
        r38.updateSecretTimeText(r39);
        r0 = r39;
        r4 = r0.photoThumbs;
        r5 = 80;
        r4 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r4, r5);
        r0 = r38;
        r0.currentPhotoObjectThumb = r4;
    L_0x287d:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        if (r4 != 0) goto L_0x288e;
    L_0x2883:
        r0 = r39;
        r4 = r0.caption;
        if (r4 == 0) goto L_0x288e;
    L_0x2889:
        r4 = 0;
        r0 = r38;
        r0.mediaBackground = r4;
    L_0x288e:
        r0 = r39;
        r4 = r0.photoThumbs;
        r5 = org.telegram.messenger.AndroidUtilities.getPhotoSize();
        r4 = org.telegram.messenger.FileLoader.getClosestPhotoSizeWithSize(r4, r5);
        r0 = r38;
        r0.currentPhotoObject = r4;
        r5 = 0;
        r4 = 0;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        if (r6 == 0) goto L_0x28b5;
    L_0x28a6:
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        if (r6 != r7) goto L_0x28b5;
    L_0x28b0:
        r6 = 0;
        r0 = r38;
        r0.currentPhotoObjectThumb = r6;
    L_0x28b5:
        r0 = r38;
        r6 = r0.currentPhotoObject;
        if (r6 == 0) goto L_0x28f2;
    L_0x28bb:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.f10147w;
        r4 = (float) r4;
        r5 = (float) r13;
        r4 = r4 / r5;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = r5.f10147w;
        r5 = (float) r5;
        r5 = r5 / r4;
        r5 = (int) r5;
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r6 = r6.f10146h;
        r6 = (float) r6;
        r4 = r6 / r4;
        r4 = (int) r4;
        if (r5 != 0) goto L_0x28df;
    L_0x28d9:
        r5 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
    L_0x28df:
        if (r4 != 0) goto L_0x28e7;
    L_0x28e1:
        r4 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
    L_0x28e7:
        if (r4 <= r15) goto L_0x2a99;
    L_0x28e9:
        r4 = (float) r4;
        r6 = (float) r15;
        r4 = r4 / r6;
        r5 = (float) r5;
        r4 = r5 / r4;
        r4 = (int) r4;
        r5 = r4;
        r4 = r15;
    L_0x28f2:
        r0 = r39;
        r6 = r0.type;
        r7 = 5;
        if (r6 != r7) goto L_0x3524;
    L_0x28f9:
        r6 = org.telegram.messenger.AndroidUtilities.roundMessageSize;
        r7 = r6;
    L_0x28fc:
        if (r7 == 0) goto L_0x2900;
    L_0x28fe:
        if (r6 != 0) goto L_0x3520;
    L_0x2900:
        r0 = r39;
        r4 = r0.type;
        r5 = 8;
        if (r4 != r5) goto L_0x3520;
    L_0x2908:
        r4 = 0;
        r5 = r4;
    L_0x290a:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.attributes;
        r4 = r4.size();
        if (r5 >= r4) goto L_0x3520;
    L_0x291a:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.attributes;
        r4 = r4.get(r5);
        r4 = (org.telegram.tgnet.TLRPC.DocumentAttribute) r4;
        r8 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
        if (r8 != 0) goto L_0x2932;
    L_0x292e:
        r8 = r4 instanceof org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
        if (r8 == 0) goto L_0x2aed;
    L_0x2932:
        r5 = r4.f10140w;
        r5 = (float) r5;
        r6 = (float) r13;
        r6 = r5 / r6;
        r5 = r4.f10140w;
        r5 = (float) r5;
        r5 = r5 / r6;
        r5 = (int) r5;
        r7 = r4.f10139h;
        r7 = (float) r7;
        r6 = r7 / r6;
        r6 = (int) r6;
        if (r6 <= r15) goto L_0x2ac8;
    L_0x2945:
        r4 = (float) r6;
        r6 = (float) r15;
        r4 = r4 / r6;
        r5 = (float) r5;
        r4 = r5 / r4;
        r5 = (int) r4;
        r4 = r15;
    L_0x294d:
        if (r5 == 0) goto L_0x2951;
    L_0x294f:
        if (r4 != 0) goto L_0x3513;
    L_0x2951:
        r4 = 1125515264; // 0x43160000 float:150.0 double:5.56078426E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r5 = r4;
    L_0x2958:
        r0 = r39;
        r6 = r0.type;
        r7 = 3;
        if (r6 != r7) goto L_0x3510;
    L_0x295f:
        r0 = r38;
        r6 = r0.infoWidth;
        r7 = 1109393408; // 0x42200000 float:40.0 double:5.481131706E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r6 = r6 + r7;
        if (r4 >= r6) goto L_0x3510;
    L_0x296c:
        r0 = r38;
        r4 = r0.infoWidth;
        r6 = 1109393408; // 0x42200000 float:40.0 double:5.481131706E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r4 = r4 + r6;
        r6 = r4;
    L_0x2978:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        if (r4 == 0) goto L_0x2c03;
    L_0x297e:
        r7 = 0;
        r9 = r38.getGroupPhotosWidth();
        r4 = 0;
        r8 = r7;
        r7 = r4;
    L_0x2986:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.posArray;
        r4 = r4.size();
        if (r7 >= r4) goto L_0x2af2;
    L_0x2992:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.posArray;
        r4 = r4.get(r7);
        r4 = (org.telegram.messenger.MessageObject.GroupedMessagePosition) r4;
        r10 = r4.minY;
        if (r10 != 0) goto L_0x2af2;
    L_0x29a2:
        r10 = (double) r8;
        r8 = r4.pw;
        r4 = r4.leftSpanOffset;
        r4 = r4 + r8;
        r4 = (float) r4;
        r8 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r4 = r4 / r8;
        r8 = (float) r9;
        r4 = r4 * r8;
        r12 = (double) r4;
        r12 = java.lang.Math.ceil(r12);
        r10 = r10 + r12;
        r8 = (int) r10;
        r4 = r7 + 1;
        r7 = r4;
        goto L_0x2986;
    L_0x29b9:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x29cc;
    L_0x29bf:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r4 = (float) r4;
        r5 = 1060320051; // 0x3f333333 float:0.7 double:5.23867711E-315;
        r4 = r4 * r5;
        r4 = (int) r4;
        r5 = r4;
        goto L_0x282c;
    L_0x29cc:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r4 = java.lang.Math.min(r4, r5);
        r4 = (float) r4;
        r5 = 1060320051; // 0x3f333333 float:0.7 double:5.23867711E-315;
        r4 = r4 * r5;
        r4 = (int) r4;
        r5 = r4;
        goto L_0x282c;
    L_0x29e1:
        r0 = r39;
        r4 = r0.type;
        r5 = 3;
        if (r4 != r5) goto L_0x2a14;
    L_0x29e8:
        r4 = 0;
        r0 = r38;
        r1 = r39;
        r0.createDocumentLayout(r4, r1);
        r38.updateSecretTimeText(r39);
        r4 = r39.isSecretPhoto();
        if (r4 != 0) goto L_0x2a09;
    L_0x29f9:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setNeedsQualityThumb(r5);
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setShouldGenerateQualityThumb(r5);
    L_0x2a09:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r39;
        r4.setParentMessageObject(r0);
        goto L_0x287d;
    L_0x2a14:
        r0 = r39;
        r4 = r0.type;
        r5 = 5;
        if (r4 != r5) goto L_0x2a3c;
    L_0x2a1b:
        r4 = r39.isSecretPhoto();
        if (r4 != 0) goto L_0x2a31;
    L_0x2a21:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setNeedsQualityThumb(r5);
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setShouldGenerateQualityThumb(r5);
    L_0x2a31:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r39;
        r4.setParentMessageObject(r0);
        goto L_0x287d;
    L_0x2a3c:
        r0 = r39;
        r4 = r0.type;
        r5 = 8;
        if (r4 != r5) goto L_0x287d;
    L_0x2a44:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r4 = r4.size;
        r4 = (long) r4;
        r5 = org.telegram.messenger.AndroidUtilities.formatFileSize(r4);
        r4 = org.telegram.ui.ActionBar.Theme.chat_infoPaint;
        r4 = r4.measureText(r5);
        r6 = (double) r4;
        r6 = java.lang.Math.ceil(r6);
        r4 = (int) r6;
        r0 = r38;
        r0.infoWidth = r4;
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_infoPaint;
        r0 = r38;
        r7 = r0.infoWidth;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r38;
        r0.infoLayout = r4;
        r4 = r39.isSecretPhoto();
        if (r4 != 0) goto L_0x2a8e;
    L_0x2a7e:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setNeedsQualityThumb(r5);
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 1;
        r4.setShouldGenerateQualityThumb(r5);
    L_0x2a8e:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r39;
        r4.setParentMessageObject(r0);
        goto L_0x287d;
    L_0x2a99:
        r6 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        if (r4 >= r6) goto L_0x28f2;
    L_0x2aa1:
        r4 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r6 = r0.currentPhotoObject;
        r6 = r6.f10146h;
        r6 = (float) r6;
        r7 = (float) r4;
        r6 = r6 / r7;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.f10147w;
        r7 = (float) r7;
        r7 = r7 / r6;
        r8 = (float) r13;
        r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1));
        if (r7 >= 0) goto L_0x28f2;
    L_0x2abd:
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = r5.f10147w;
        r5 = (float) r5;
        r5 = r5 / r6;
        r5 = (int) r5;
        goto L_0x28f2;
    L_0x2ac8:
        r7 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        if (r6 >= r7) goto L_0x351d;
    L_0x2ad0:
        r6 = 1123024896; // 0x42f00000 float:120.0 double:5.548480205E-315;
        r15 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r4.f10139h;
        r6 = (float) r6;
        r7 = (float) r15;
        r6 = r6 / r7;
        r7 = r4.f10140w;
        r7 = (float) r7;
        r7 = r7 / r6;
        r8 = (float) r13;
        r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1));
        if (r7 >= 0) goto L_0x351a;
    L_0x2ae4:
        r4 = r4.f10140w;
        r4 = (float) r4;
        r4 = r4 / r6;
        r4 = (int) r4;
    L_0x2ae9:
        r5 = r4;
        r4 = r15;
        goto L_0x294d;
    L_0x2aed:
        r4 = r5 + 1;
        r5 = r4;
        goto L_0x290a;
    L_0x2af2:
        r4 = 1108082688; // 0x420c0000 float:35.0 double:5.47465589E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r8 - r4;
        r0 = r38;
        r0.availableTimeWidth = r4;
    L_0x2afe:
        r0 = r39;
        r4 = r0.type;
        r7 = 5;
        if (r4 != r7) goto L_0x2b26;
    L_0x2b05:
        r0 = r38;
        r4 = r0.availableTimeWidth;
        r8 = (double) r4;
        r4 = org.telegram.ui.ActionBar.Theme.chat_audioTimePaint;
        r7 = "00:00";
        r4 = r4.measureText(r7);
        r10 = (double) r4;
        r10 = java.lang.Math.ceil(r10);
        r4 = 1104150528; // 0x41d00000 float:26.0 double:5.455228437E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r12 = (double) r4;
        r10 = r10 + r12;
        r8 = r8 - r10;
        r4 = (int) r8;
        r0 = r38;
        r0.availableTimeWidth = r4;
    L_0x2b26:
        r38.measureTime(r39);
        r0 = r38;
        r7 = r0.timeWidth;
        r4 = r39.isOutOwner();
        if (r4 == 0) goto L_0x2c11;
    L_0x2b33:
        r4 = 20;
    L_0x2b35:
        r4 = r4 + 14;
        r4 = (float) r4;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r12 = r7 + r4;
        if (r6 >= r12) goto L_0x2b41;
    L_0x2b40:
        r6 = r12;
    L_0x2b41:
        r4 = r39.isRoundVideo();
        if (r4 == 0) goto L_0x2c14;
    L_0x2b47:
        r5 = java.lang.Math.min(r6, r5);
        r4 = 0;
        r0 = r38;
        r0.drawBackground = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r6 = r5 / 2;
        r4.setRoundRadius(r6);
        r6 = r5;
    L_0x2b5a:
        r13 = 0;
        r11 = 0;
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        if (r4 == 0) goto L_0x2e7e;
    L_0x2b62:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r4 = java.lang.Math.max(r4, r5);
        r4 = (float) r4;
        r5 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r15 = r4 * r5;
        r18 = r38.getGroupPhotosWidth();
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.pw;
        r4 = (float) r4;
        r5 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r4 = r4 / r5;
        r0 = r18;
        r5 = (float) r0;
        r4 = r4 * r5;
        r4 = (double) r4;
        r4 = java.lang.Math.ceil(r4);
        r10 = (int) r4;
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.minY;
        if (r4 == 0) goto L_0x350d;
    L_0x2b93:
        r4 = r39.isOutOwner();
        if (r4 == 0) goto L_0x2ba3;
    L_0x2b99:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.flags;
        r4 = r4 & 1;
        if (r4 != 0) goto L_0x2bb3;
    L_0x2ba3:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x350d;
    L_0x2ba9:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.flags;
        r4 = r4 & 2;
        if (r4 == 0) goto L_0x350d;
    L_0x2bb3:
        r6 = 0;
        r5 = 0;
        r4 = 0;
        r7 = r6;
        r6 = r5;
        r5 = r4;
    L_0x2bb9:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.posArray;
        r4 = r4.size();
        if (r5 >= r4) goto L_0x2c85;
    L_0x2bc5:
        r0 = r38;
        r4 = r0.currentMessagesGroup;
        r4 = r4.posArray;
        r4 = r4.get(r5);
        r4 = (org.telegram.messenger.MessageObject.GroupedMessagePosition) r4;
        r8 = r4.minY;
        if (r8 != 0) goto L_0x2c43;
    L_0x2bd5:
        r0 = (double) r7;
        r20 = r0;
        r7 = r4.pw;
        r7 = (float) r7;
        r8 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r7 = r7 / r8;
        r0 = r18;
        r8 = (float) r0;
        r7 = r7 * r8;
        r8 = (double) r7;
        r22 = java.lang.Math.ceil(r8);
        r7 = r4.leftSpanOffset;
        if (r7 == 0) goto L_0x2c40;
    L_0x2beb:
        r4 = r4.leftSpanOffset;
        r4 = (float) r4;
        r7 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r4 = r4 / r7;
        r0 = r18;
        r7 = (float) r0;
        r4 = r4 * r7;
        r8 = (double) r4;
        r8 = java.lang.Math.ceil(r8);
    L_0x2bfa:
        r8 = r8 + r22;
        r8 = r8 + r20;
        r7 = (int) r8;
    L_0x2bff:
        r4 = r5 + 1;
        r5 = r4;
        goto L_0x2bb9;
    L_0x2c03:
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r12 - r4;
        r0 = r38;
        r0.availableTimeWidth = r4;
        goto L_0x2afe;
    L_0x2c11:
        r4 = 0;
        goto L_0x2b35;
    L_0x2c14:
        r4 = r39.isSecretPhoto();
        if (r4 == 0) goto L_0x2b5a;
    L_0x2c1a:
        r4 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r4 == 0) goto L_0x2c2c;
    L_0x2c20:
        r4 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r4 = (float) r4;
        r5 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r4 = r4 * r5;
        r5 = (int) r4;
        r6 = r5;
        goto L_0x2b5a;
    L_0x2c2c:
        r4 = org.telegram.messenger.AndroidUtilities.displaySize;
        r4 = r4.x;
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.y;
        r4 = java.lang.Math.min(r4, r5);
        r4 = (float) r4;
        r5 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r4 = r4 * r5;
        r5 = (int) r4;
        r6 = r5;
        goto L_0x2b5a;
    L_0x2c40:
        r8 = 0;
        goto L_0x2bfa;
    L_0x2c43:
        r8 = r4.minY;
        r0 = r38;
        r9 = r0.currentPosition;
        r9 = r9.minY;
        if (r8 != r9) goto L_0x2c7b;
    L_0x2c4d:
        r0 = (double) r6;
        r20 = r0;
        r6 = r4.pw;
        r6 = (float) r6;
        r8 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r6 = r6 / r8;
        r0 = r18;
        r8 = (float) r0;
        r6 = r6 * r8;
        r8 = (double) r6;
        r22 = java.lang.Math.ceil(r8);
        r6 = r4.leftSpanOffset;
        if (r6 == 0) goto L_0x2c78;
    L_0x2c63:
        r4 = r4.leftSpanOffset;
        r4 = (float) r4;
        r6 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r4 = r4 / r6;
        r0 = r18;
        r6 = (float) r0;
        r4 = r4 * r6;
        r8 = (double) r4;
        r8 = java.lang.Math.ceil(r8);
    L_0x2c72:
        r8 = r8 + r22;
        r8 = r8 + r20;
        r6 = (int) r8;
        goto L_0x2bff;
    L_0x2c78:
        r8 = 0;
        goto L_0x2c72;
    L_0x2c7b:
        r4 = r4.minY;
        r0 = r38;
        r8 = r0.currentPosition;
        r8 = r8.minY;
        if (r4 <= r8) goto L_0x2bff;
    L_0x2c85:
        r4 = r7 - r6;
        r4 = r4 + r10;
    L_0x2c88:
        r5 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r5 = r0.isAvatarVisible;
        if (r5 == 0) goto L_0x2c9c;
    L_0x2c95:
        r5 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
    L_0x2c9c:
        r0 = r38;
        r5 = r0.currentPosition;
        r5 = r5.siblingHeights;
        if (r5 == 0) goto L_0x2e6f;
    L_0x2ca4:
        r6 = 0;
        r5 = 0;
    L_0x2ca6:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.siblingHeights;
        r7 = r7.length;
        if (r5 >= r7) goto L_0x2cc2;
    L_0x2caf:
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.siblingHeights;
        r7 = r7[r5];
        r7 = r7 * r15;
        r8 = (double) r7;
        r8 = java.lang.Math.ceil(r8);
        r7 = (int) r8;
        r6 = r6 + r7;
        r5 = r5 + 1;
        goto L_0x2ca6;
    L_0x2cc2:
        r0 = r38;
        r5 = r0.currentPosition;
        r5 = r5.maxY;
        r0 = r38;
        r7 = r0.currentPosition;
        r7 = r7.minY;
        r5 = r5 - r7;
        r7 = 1093664768; // 0x41300000 float:11.0 double:5.4034219E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r5 = r5 * r7;
        r5 = r5 + r6;
    L_0x2cd7:
        r0 = r38;
        r0.backgroundWidth = r4;
        r6 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r4 - r6;
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.edge;
        if (r4 != 0) goto L_0x350a;
    L_0x2ceb:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r6;
    L_0x2cf2:
        r7 = r11;
        r19 = r6;
        r15 = r5;
        r18 = r4;
        r37 = r5;
        r5 = r13;
        r13 = r37;
    L_0x2cfd:
        if (r5 == 0) goto L_0x2d88;
    L_0x2cff:
        r4 = android.os.Build.VERSION.SDK_INT;	 Catch:{ Exception -> 0x2ec6 }
        r6 = 24;
        if (r4 < r6) goto L_0x2eb3;
    L_0x2d05:
        r4 = 0;
        r6 = r5.length();	 Catch:{ Exception -> 0x2ec6 }
        r8 = org.telegram.ui.ActionBar.Theme.chat_msgTextPaint;	 Catch:{ Exception -> 0x2ec6 }
        r4 = android.text.StaticLayout.Builder.obtain(r5, r4, r6, r8, r7);	 Catch:{ Exception -> 0x2ec6 }
        r5 = 1;
        r4 = r4.setBreakStrategy(r5);	 Catch:{ Exception -> 0x2ec6 }
        r5 = 0;
        r4 = r4.setHyphenationFrequency(r5);	 Catch:{ Exception -> 0x2ec6 }
        r5 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4.setAlignment(r5);	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4.build();	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r0.captionLayout = r4;	 Catch:{ Exception -> 0x2ec6 }
    L_0x2d28:
        r0 = r38;
        r4 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4.getLineCount();	 Catch:{ Exception -> 0x2ec6 }
        if (r4 <= 0) goto L_0x3506;
    L_0x2d32:
        r0 = r38;
        r4 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4.getHeight();	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r0.captionHeight = r4;	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r4 = r0.captionHeight;	 Catch:{ Exception -> 0x2ec6 }
        r5 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4 + r5;
        r14 = r14 + r4;
        r0 = r38;
        r4 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r5 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r5 = r5.getLineCount();	 Catch:{ Exception -> 0x2ec6 }
        r5 = r5 + -1;
        r4 = r4.getLineWidth(r5);	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r5 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r6 = r0.captionLayout;	 Catch:{ Exception -> 0x2ec6 }
        r6 = r6.getLineCount();	 Catch:{ Exception -> 0x2ec6 }
        r6 = r6 + -1;
        r5 = r5.getLineLeft(r6);	 Catch:{ Exception -> 0x2ec6 }
        r4 = r4 + r5;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);	 Catch:{ Exception -> 0x2ec6 }
        r5 = r5 + r7;
        r5 = (float) r5;	 Catch:{ Exception -> 0x2ec6 }
        r4 = r5 - r4;
        r5 = (float) r12;	 Catch:{ Exception -> 0x2ec6 }
        r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1));
        if (r4 >= 0) goto L_0x3506;
    L_0x2d7e:
        r4 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);	 Catch:{ Exception -> 0x2ec6 }
        r14 = r14 + r4;
        r4 = 1;
    L_0x2d86:
        r30 = r4;
    L_0x2d88:
        r4 = java.util.Locale.US;
        r5 = "%d_%d";
        r6 = 2;
        r6 = new java.lang.Object[r6];
        r7 = 0;
        r0 = r19;
        r8 = (float) r0;
        r9 = org.telegram.messenger.AndroidUtilities.density;
        r8 = r8 / r9;
        r8 = (int) r8;
        r8 = java.lang.Integer.valueOf(r8);
        r6[r7] = r8;
        r7 = 1;
        r8 = (float) r13;
        r9 = org.telegram.messenger.AndroidUtilities.density;
        r8 = r8 / r9;
        r8 = (int) r8;
        r8 = java.lang.Integer.valueOf(r8);
        r6[r7] = r8;
        r4 = java.lang.String.format(r4, r5, r6);
        r0 = r38;
        r0.currentPhotoFilterThumb = r4;
        r0 = r38;
        r0.currentPhotoFilter = r4;
        r0 = r39;
        r4 = r0.photoThumbs;
        if (r4 == 0) goto L_0x2dc7;
    L_0x2dbc:
        r0 = r39;
        r4 = r0.photoThumbs;
        r4 = r4.size();
        r5 = 1;
        if (r4 > r5) goto L_0x2ddd;
    L_0x2dc7:
        r0 = r39;
        r4 = r0.type;
        r5 = 3;
        if (r4 == r5) goto L_0x2ddd;
    L_0x2dce:
        r0 = r39;
        r4 = r0.type;
        r5 = 8;
        if (r4 == r5) goto L_0x2ddd;
    L_0x2dd6:
        r0 = r39;
        r4 = r0.type;
        r5 = 5;
        if (r4 != r5) goto L_0x2e1b;
    L_0x2ddd:
        r4 = r39.isSecretPhoto();
        if (r4 == 0) goto L_0x2ecc;
    L_0x2de3:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r38;
        r5 = r0.currentPhotoFilter;
        r4 = r4.append(r5);
        r5 = "_b2";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0 = r38;
        r0.currentPhotoFilter = r4;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r38;
        r5 = r0.currentPhotoFilterThumb;
        r4 = r4.append(r5);
        r5 = "_b2";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0 = r38;
        r0.currentPhotoFilterThumb = r4;
    L_0x2e1b:
        r4 = 0;
        r0 = r39;
        r5 = r0.type;
        r6 = 3;
        if (r5 == r6) goto L_0x2e32;
    L_0x2e23:
        r0 = r39;
        r5 = r0.type;
        r6 = 8;
        if (r5 == r6) goto L_0x2e32;
    L_0x2e2b:
        r0 = r39;
        r5 = r0.type;
        r6 = 5;
        if (r5 != r6) goto L_0x3503;
    L_0x2e32:
        r4 = 1;
        r9 = r4;
    L_0x2e34:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x2e4b;
    L_0x2e3a:
        if (r9 != 0) goto L_0x2e4b;
    L_0x2e3c:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r4 = r4.size;
        if (r4 != 0) goto L_0x2e4b;
    L_0x2e44:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        r5 = -1;
        r4.size = r5;
    L_0x2e4b:
        r0 = r39;
        r4 = r0.type;
        r5 = 1;
        if (r4 != r5) goto L_0x2fac;
    L_0x2e52:
        r0 = r39;
        r4 = r0.useCustomPhoto;
        if (r4 == 0) goto L_0x2eea;
    L_0x2e58:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = r38.getResources();
        r6 = 2130838353; // 0x7f020351 float:1.7281686E38 double:1.052774027E-314;
        r5 = r5.getDrawable(r6);
        r4.setImageBitmap(r5);
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x2e6f:
        r0 = r38;
        r5 = r0.currentPosition;
        r5 = r5.ph;
        r5 = r5 * r15;
        r6 = (double) r5;
        r6 = java.lang.Math.ceil(r6);
        r5 = (int) r6;
        goto L_0x2cd7;
    L_0x2e7e:
        r4 = 1094713344; // 0x41400000 float:12.0 double:5.408602553E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r6;
        r0 = r38;
        r0.backgroundWidth = r4;
        r0 = r38;
        r4 = r0.mediaBackground;
        if (r4 != 0) goto L_0x2e9e;
    L_0x2e8f:
        r0 = r38;
        r4 = r0.backgroundWidth;
        r7 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 + r7;
        r0 = r38;
        r0.backgroundWidth = r4;
    L_0x2e9e:
        r0 = r39;
        r4 = r0.caption;
        r7 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r6 - r7;
        r13 = r5;
        r19 = r6;
        r15 = r5;
        r18 = r6;
        r5 = r4;
        goto L_0x2cfd;
    L_0x2eb3:
        r4 = new android.text.StaticLayout;	 Catch:{ Exception -> 0x2ec6 }
        r6 = org.telegram.ui.ActionBar.Theme.chat_msgTextPaint;	 Catch:{ Exception -> 0x2ec6 }
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x2ec6 }
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x2ec6 }
        r0 = r38;
        r0.captionLayout = r4;	 Catch:{ Exception -> 0x2ec6 }
        goto L_0x2d28;
    L_0x2ec6:
        r4 = move-exception;
        org.telegram.messenger.FileLog.e(r4);
        goto L_0x2d88;
    L_0x2ecc:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r38;
        r5 = r0.currentPhotoFilterThumb;
        r4 = r4.append(r5);
        r5 = "_b";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0 = r38;
        r0.currentPhotoFilterThumb = r4;
        goto L_0x2e1b;
    L_0x2eea:
        r0 = r38;
        r4 = r0.currentPhotoObject;
        if (r4 == 0) goto L_0x2f9d;
    L_0x2ef0:
        r4 = 1;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = org.telegram.messenger.FileLoader.getAttachFileName(r5);
        r0 = r39;
        r6 = r0.mediaExists;
        if (r6 == 0) goto L_0x2f57;
    L_0x2eff:
        r6 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r6.removeLoadingFileObserver(r0);
    L_0x2f08:
        if (r4 != 0) goto L_0x2f22;
    L_0x2f0a:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r6 = r0.currentMessageObject;
        r4 = r4.canDownloadMedia(r6);
        if (r4 != 0) goto L_0x2f22;
    L_0x2f18:
        r4 = org.telegram.messenger.FileLoader.getInstance();
        r4 = r4.isLoadingFile(r5);
        if (r4 == 0) goto L_0x2f64;
    L_0x2f22:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r38;
        r5 = r0.currentPhotoObject;
        r5 = r5.location;
        r0 = r38;
        r6 = r0.currentPhotoFilter;
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        if (r7 == 0) goto L_0x2f59;
    L_0x2f36:
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        r7 = r7.location;
    L_0x2f3c:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        if (r9 == 0) goto L_0x2f5b;
    L_0x2f42:
        r9 = 0;
    L_0x2f43:
        r10 = 0;
        r0 = r38;
        r11 = r0.currentMessageObject;
        r11 = r11.shouldEncryptPhotoOrVideo();
        if (r11 == 0) goto L_0x2f62;
    L_0x2f4e:
        r11 = 2;
    L_0x2f4f:
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
    L_0x2f52:
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x2f57:
        r4 = 0;
        goto L_0x2f08;
    L_0x2f59:
        r7 = 0;
        goto L_0x2f3c;
    L_0x2f5b:
        r0 = r38;
        r9 = r0.currentPhotoObject;
        r9 = r9.size;
        goto L_0x2f43;
    L_0x2f62:
        r11 = 0;
        goto L_0x2f4f;
    L_0x2f64:
        r4 = 1;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.currentPhotoObjectThumb;
        if (r4 == 0) goto L_0x2f92;
    L_0x2f6f:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObjectThumb;
        r7 = r7.location;
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r9 = 0;
        r10 = 0;
        r0 = r38;
        r11 = r0.currentMessageObject;
        r11 = r11.shouldEncryptPhotoOrVideo();
        if (r11 == 0) goto L_0x2f90;
    L_0x2f8b:
        r11 = 2;
    L_0x2f8c:
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x2f52;
    L_0x2f90:
        r11 = 0;
        goto L_0x2f8c;
    L_0x2f92:
        r0 = r38;
        r5 = r0.photoImage;
        r4 = 0;
        r4 = (android.graphics.drawable.Drawable) r4;
        r5.setImageBitmap(r4);
        goto L_0x2f52;
    L_0x2f9d:
        r0 = r38;
        r5 = r0.photoImage;
        r4 = 0;
        r4 = (android.graphics.drawable.BitmapDrawable) r4;
        r5.setImageBitmap(r4);
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x2fac:
        r0 = r39;
        r4 = r0.type;
        r5 = 8;
        if (r4 == r5) goto L_0x2fbb;
    L_0x2fb4:
        r0 = r39;
        r4 = r0.type;
        r5 = 5;
        if (r4 != r5) goto L_0x30ab;
    L_0x2fbb:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.document;
        r6 = org.telegram.messenger.FileLoader.getAttachFileName(r4);
        r4 = 0;
        r0 = r39;
        r5 = r0.attachPathExists;
        if (r5 == 0) goto L_0x3033;
    L_0x2fce:
        r4 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r4.removeLoadingFileObserver(r0);
        r4 = 1;
    L_0x2fd8:
        r5 = 0;
        r0 = r39;
        r7 = r0.messageOwner;
        r7 = r7.media;
        r7 = r7.document;
        r7 = org.telegram.messenger.MessageObject.isNewGifDocument(r7);
        if (r7 == 0) goto L_0x303b;
    L_0x2fe7:
        r5 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r7 = r0.currentMessageObject;
        r5 = r5.canDownloadMedia(r7);
    L_0x2ff3:
        r7 = r39.isSending();
        if (r7 != 0) goto L_0x3087;
    L_0x2ff9:
        if (r4 != 0) goto L_0x3007;
    L_0x2ffb:
        r7 = org.telegram.messenger.FileLoader.getInstance();
        r6 = r7.isLoadingFile(r6);
        if (r6 != 0) goto L_0x3007;
    L_0x3005:
        if (r5 == 0) goto L_0x3087;
    L_0x3007:
        r5 = 1;
        if (r4 != r5) goto L_0x3058;
    L_0x300a:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = r39.isSendError();
        if (r6 == 0) goto L_0x304f;
    L_0x3015:
        r6 = 0;
    L_0x3016:
        r7 = 0;
        r8 = 0;
        r0 = r38;
        r9 = r0.currentPhotoObject;
        if (r9 == 0) goto L_0x3056;
    L_0x301e:
        r0 = r38;
        r9 = r0.currentPhotoObject;
        r9 = r9.location;
    L_0x3024:
        r0 = r38;
        r10 = r0.currentPhotoFilterThumb;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11, r12, r13);
    L_0x302e:
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x3033:
        r0 = r39;
        r5 = r0.mediaExists;
        if (r5 == 0) goto L_0x2fd8;
    L_0x3039:
        r4 = 2;
        goto L_0x2fd8;
    L_0x303b:
        r0 = r39;
        r7 = r0.type;
        r8 = 5;
        if (r7 != r8) goto L_0x2ff3;
    L_0x3042:
        r5 = org.telegram.messenger.MediaController.getInstance();
        r0 = r38;
        r7 = r0.currentMessageObject;
        r5 = r5.canDownloadMedia(r7);
        goto L_0x2ff3;
    L_0x304f:
        r0 = r39;
        r6 = r0.messageOwner;
        r6 = r6.attachPath;
        goto L_0x3016;
    L_0x3056:
        r9 = 0;
        goto L_0x3024;
    L_0x3058:
        r0 = r38;
        r4 = r0.photoImage;
        r0 = r39;
        r5 = r0.messageOwner;
        r5 = r5.media;
        r5 = r5.document;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        if (r7 == 0) goto L_0x3085;
    L_0x306b:
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.location;
    L_0x3071:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r0 = r39;
        r9 = r0.messageOwner;
        r9 = r9.media;
        r9 = r9.document;
        r9 = r9.size;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x302e;
    L_0x3085:
        r7 = 0;
        goto L_0x3071;
    L_0x3087:
        r4 = 1;
        r0 = r38;
        r0.photoNotSet = r4;
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        if (r7 == 0) goto L_0x30a9;
    L_0x3098:
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.location;
    L_0x309e:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        goto L_0x302e;
    L_0x30a9:
        r7 = 0;
        goto L_0x309e;
    L_0x30ab:
        r0 = r38;
        r4 = r0.photoImage;
        r5 = 0;
        r6 = 0;
        r0 = r38;
        r7 = r0.currentPhotoObject;
        if (r7 == 0) goto L_0x30d6;
    L_0x30b7:
        r0 = r38;
        r7 = r0.currentPhotoObject;
        r7 = r7.location;
    L_0x30bd:
        r0 = r38;
        r8 = r0.currentPhotoFilterThumb;
        r9 = 0;
        r10 = 0;
        r0 = r38;
        r11 = r0.currentMessageObject;
        r11 = r11.shouldEncryptPhotoOrVideo();
        if (r11 == 0) goto L_0x30d8;
    L_0x30cd:
        r11 = 2;
    L_0x30ce:
        r4.setImage(r5, r6, r7, r8, r9, r10, r11);
        r5 = r15;
        r6 = r18;
        goto L_0x211e;
    L_0x30d6:
        r7 = 0;
        goto L_0x30bd;
    L_0x30d8:
        r11 = 0;
        goto L_0x30ce;
    L_0x30da:
        r0 = r38;
        r4 = r0.drawNameLayout;
        if (r4 == 0) goto L_0x2151;
    L_0x30e0:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.reply_to_msg_id;
        if (r4 != 0) goto L_0x2151;
    L_0x30e8:
        r0 = r38;
        r4 = r0.namesOffset;
        r7 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 + r7;
        r0 = r38;
        r0.namesOffset = r4;
        goto L_0x2151;
    L_0x30f9:
        r4 = new android.text.StaticLayout;	 Catch:{ Exception -> 0x3118 }
        r0 = r39;
        r5 = r0.caption;	 Catch:{ Exception -> 0x3118 }
        r6 = org.telegram.ui.ActionBar.Theme.chat_msgTextPaint;	 Catch:{ Exception -> 0x3118 }
        r7 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);	 Catch:{ Exception -> 0x3118 }
        r7 = r12 - r7;
        r8 = android.text.Layout.Alignment.ALIGN_NORMAL;	 Catch:{ Exception -> 0x3118 }
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);	 Catch:{ Exception -> 0x3118 }
        r0 = r38;
        r0.captionLayout = r4;	 Catch:{ Exception -> 0x3118 }
        goto L_0x11a3;
    L_0x3118:
        r4 = move-exception;
        org.telegram.messenger.FileLog.e(r4);
        goto L_0x1233;
    L_0x311e:
        r4 = 0;
        goto L_0x11bd;
    L_0x3121:
        r0 = r38;
        r4 = r0.widthBeforeNewTimeLine;
        r5 = -1;
        if (r4 == r5) goto L_0x1233;
    L_0x3128:
        r0 = r38;
        r4 = r0.availableTimeWidth;
        r0 = r38;
        r5 = r0.widthBeforeNewTimeLine;
        r4 = r4 - r5;
        r0 = r38;
        r5 = r0.timeWidth;
        if (r4 >= r5) goto L_0x1233;
    L_0x3137:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        goto L_0x1233;
    L_0x3148:
        r4 = move-exception;
        org.telegram.messenger.FileLog.e(r4);
        goto L_0x12be;
    L_0x314e:
        r0 = r38;
        r6 = r0.descriptionX;	 Catch:{ Exception -> 0x315d }
        r5 = -r5;
        r5 = java.lang.Math.max(r6, r5);	 Catch:{ Exception -> 0x315d }
        r0 = r38;
        r0.descriptionX = r5;	 Catch:{ Exception -> 0x315d }
        goto L_0x133f;
    L_0x315d:
        r4 = move-exception;
        org.telegram.messenger.FileLog.e(r4);
    L_0x3161:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.totalHeight = r4;
        if (r30 == 0) goto L_0x3195;
    L_0x3172:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.totalHeight = r4;
        r4 = 2;
        r0 = r30;
        if (r0 != r4) goto L_0x3195;
    L_0x3186:
        r0 = r38;
        r4 = r0.captionHeight;
        r5 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.captionHeight = r4;
    L_0x3195:
        r0 = r38;
        r4 = r0.botButtons;
        r4.clear();
        if (r16 == 0) goto L_0x31b1;
    L_0x319e:
        r0 = r38;
        r4 = r0.botButtonsByData;
        r4.clear();
        r0 = r38;
        r4 = r0.botButtonsByPosition;
        r4.clear();
        r4 = 0;
        r0 = r38;
        r0.botButtonsLayout = r4;
    L_0x31b1:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 != 0) goto L_0x3480;
    L_0x31b7:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.reply_markup;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
        if (r4 == 0) goto L_0x3480;
    L_0x31c1:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.reply_markup;
        r4 = r4.rows;
        r19 = r4.size();
        r4 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 * r19;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r38;
        r0.keyboardHeight = r4;
        r0 = r38;
        r0.substractBackgroundHeight = r4;
        r0 = r38;
        r4 = r0.backgroundWidth;
        r0 = r38;
        r0.widthForButtons = r4;
        r4 = 0;
        r0 = r39;
        r5 = r0.wantedBotKeyboardWidth;
        r0 = r38;
        r6 = r0.widthForButtons;
        if (r5 <= r6) goto L_0x3500;
    L_0x31f7:
        r0 = r38;
        r4 = r0.isChat;
        if (r4 == 0) goto L_0x3293;
    L_0x31fd:
        r4 = r39.needDrawAvatar();
        if (r4 == 0) goto L_0x3293;
    L_0x3203:
        r4 = r39.isOutOwner();
        if (r4 != 0) goto L_0x3293;
    L_0x3209:
        r4 = 1115160576; // 0x42780000 float:62.0 double:5.5096253E-315;
    L_0x320b:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = -r4;
        r5 = org.telegram.messenger.AndroidUtilities.isTablet();
        if (r5 == 0) goto L_0x3297;
    L_0x3216:
        r5 = org.telegram.messenger.AndroidUtilities.getMinTabletSide();
        r4 = r4 + r5;
    L_0x321b:
        r0 = r38;
        r5 = r0.backgroundWidth;
        r0 = r39;
        r6 = r0.wantedBotKeyboardWidth;
        r4 = java.lang.Math.min(r6, r4);
        r4 = java.lang.Math.max(r5, r4);
        r0 = r38;
        r0.widthForButtons = r4;
        r4 = 1;
        r13 = r4;
    L_0x3231:
        r5 = 0;
        r20 = new java.util.HashMap;
        r0 = r38;
        r4 = r0.botButtonsByData;
        r0 = r20;
        r0.<init>(r4);
        r0 = r39;
        r4 = r0.botButtonsLayout;
        if (r4 == 0) goto L_0x32a6;
    L_0x3243:
        r0 = r38;
        r4 = r0.botButtonsLayout;
        if (r4 == 0) goto L_0x32a6;
    L_0x3249:
        r0 = r38;
        r4 = r0.botButtonsLayout;
        r0 = r39;
        r6 = r0.botButtonsLayout;
        r6 = r6.toString();
        r4 = r4.equals(r6);
        if (r4 == 0) goto L_0x32a6;
    L_0x325b:
        r4 = new java.util.HashMap;
        r0 = r38;
        r6 = r0.botButtonsByPosition;
        r4.<init>(r6);
        r14 = r4;
    L_0x3265:
        r0 = r38;
        r4 = r0.botButtonsByData;
        r4.clear();
        r4 = 0;
        r18 = r4;
    L_0x326f:
        r0 = r18;
        r1 = r19;
        if (r0 >= r1) goto L_0x3438;
    L_0x3275:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.reply_markup;
        r4 = r4.rows;
        r0 = r18;
        r4 = r4.get(r0);
        r12 = r4;
        r12 = (org.telegram.tgnet.TLRPC$TL_keyboardButtonRow) r12;
        r4 = r12.buttons;
        r6 = r4.size();
        if (r6 != 0) goto L_0x32bb;
    L_0x328e:
        r4 = r18 + 1;
        r18 = r4;
        goto L_0x326f;
    L_0x3293:
        r4 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        goto L_0x320b;
    L_0x3297:
        r5 = org.telegram.messenger.AndroidUtilities.displaySize;
        r5 = r5.x;
        r6 = org.telegram.messenger.AndroidUtilities.displaySize;
        r6 = r6.y;
        r5 = java.lang.Math.min(r5, r6);
        r4 = r4 + r5;
        goto L_0x321b;
    L_0x32a6:
        r0 = r39;
        r4 = r0.botButtonsLayout;
        if (r4 == 0) goto L_0x32b8;
    L_0x32ac:
        r0 = r39;
        r4 = r0.botButtonsLayout;
        r4 = r4.toString();
        r0 = r38;
        r0.botButtonsLayout = r4;
    L_0x32b8:
        r4 = 0;
        r14 = r4;
        goto L_0x3265;
    L_0x32bb:
        r0 = r38;
        r4 = r0.widthForButtons;
        r7 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r8 = r6 + -1;
        r7 = r7 * r8;
        r7 = r4 - r7;
        if (r13 != 0) goto L_0x33f5;
    L_0x32cc:
        r0 = r38;
        r4 = r0.mediaBackground;
        if (r4 == 0) goto L_0x33f5;
    L_0x32d2:
        r4 = 0;
    L_0x32d3:
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r7 - r4;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r4 = r4 - r7;
        r21 = r4 / r6;
        r4 = 0;
        r15 = r4;
        r16 = r5;
    L_0x32e6:
        r4 = r12.buttons;
        r4 = r4.size();
        if (r15 >= r4) goto L_0x34fc;
    L_0x32ee:
        r22 = new org.telegram.ui.Cells.ChatMessageCell$BotButton;
        r4 = 0;
        r0 = r22;
        r1 = r38;
        r0.<init>();
        r4 = r12.buttons;
        r4 = r4.get(r15);
        r4 = (org.telegram.tgnet.TLRPC.KeyboardButton) r4;
        r0 = r22;
        r0.button = r4;
        r4 = r22.button;
        r4 = r4.data;
        r5 = org.telegram.messenger.Utilities.bytesToHex(r4);
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = r18;
        r4 = r4.append(r0);
        r6 = "";
        r4 = r4.append(r6);
        r4 = r4.append(r15);
        r6 = r4.toString();
        if (r14 == 0) goto L_0x33f9;
    L_0x332b:
        r4 = r14.get(r6);
        r4 = (org.telegram.ui.Cells.ChatMessageCell.BotButton) r4;
    L_0x3331:
        if (r4 == 0) goto L_0x3403;
    L_0x3333:
        r7 = r4.progressAlpha;
        r0 = r22;
        r0.progressAlpha = r7;
        r7 = r4.angle;
        r0 = r22;
        r0.angle = r7;
        r8 = r4.lastUpdateTime;
        r0 = r22;
        r0.lastUpdateTime = r8;
    L_0x334e:
        r0 = r38;
        r4 = r0.botButtonsByData;
        r0 = r22;
        r4.put(r5, r0);
        r0 = r38;
        r4 = r0.botButtonsByPosition;
        r0 = r22;
        r4.put(r6, r0);
        r4 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 + r21;
        r4 = r4 * r15;
        r0 = r22;
        r0.f10171x = r4;
        r4 = 1111490560; // 0x42400000 float:48.0 double:5.491493014E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r4 = r4 * r18;
        r5 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 + r5;
        r0 = r22;
        r0.f10172y = r4;
        r0 = r22;
        r1 = r21;
        r0.width = r1;
        r4 = 1110441984; // 0x42300000 float:44.0 double:5.48631236E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r22;
        r0.height = r4;
        r4 = r22.button;
        r4 = r4 instanceof org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
        if (r4 == 0) goto L_0x340e;
    L_0x339c:
        r0 = r39;
        r4 = r0.messageOwner;
        r4 = r4.media;
        r4 = r4.flags;
        r4 = r4 & 4;
        if (r4 == 0) goto L_0x340e;
    L_0x33a8:
        r4 = "PaymentReceipt";
        r5 = 2131232107; // 0x7f08056b float:1.8080314E38 double:1.0529685674E-314;
        r5 = org.telegram.messenger.LocaleController.getString(r4, r5);
    L_0x33b2:
        r4 = new android.text.StaticLayout;
        r6 = org.telegram.ui.ActionBar.Theme.chat_botButtonPaint;
        r7 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r7 = org.telegram.messenger.AndroidUtilities.dp(r7);
        r7 = r21 - r7;
        r8 = android.text.Layout.Alignment.ALIGN_CENTER;
        r9 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r10 = 0;
        r11 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10, r11);
        r0 = r22;
        r0.title = r4;
        r0 = r38;
        r4 = r0.botButtons;
        r0 = r22;
        r4.add(r0);
        r4 = r12.buttons;
        r4 = r4.size();
        r4 = r4 + -1;
        if (r15 != r4) goto L_0x34f8;
    L_0x33df:
        r4 = r22.f10171x;
        r5 = r22.width;
        r4 = r4 + r5;
        r0 = r16;
        r5 = java.lang.Math.max(r0, r4);
    L_0x33ee:
        r4 = r15 + 1;
        r15 = r4;
        r16 = r5;
        goto L_0x32e6;
    L_0x33f5:
        r4 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        goto L_0x32d3;
    L_0x33f9:
        r0 = r20;
        r4 = r0.get(r5);
        r4 = (org.telegram.ui.Cells.ChatMessageCell.BotButton) r4;
        goto L_0x3331;
    L_0x3403:
        r8 = java.lang.System.currentTimeMillis();
        r0 = r22;
        r0.lastUpdateTime = r8;
        goto L_0x334e;
    L_0x340e:
        r4 = r22.button;
        r4 = r4.text;
        r5 = org.telegram.ui.ActionBar.Theme.chat_botButtonPaint;
        r5 = r5.getFontMetricsInt();
        r6 = 1097859072; // 0x41700000 float:15.0 double:5.424144515E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r7 = 0;
        r4 = org.telegram.messenger.Emoji.replaceEmoji(r4, r5, r6, r7);
        r5 = org.telegram.ui.ActionBar.Theme.chat_botButtonPaint;
        r6 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r6 = org.telegram.messenger.AndroidUtilities.dp(r6);
        r6 = r21 - r6;
        r6 = (float) r6;
        r7 = android.text.TextUtils.TruncateAt.END;
        r5 = android.text.TextUtils.ellipsize(r4, r5, r6, r7);
        goto L_0x33b2;
    L_0x3438:
        r0 = r38;
        r0.widthForButtons = r5;
    L_0x343c:
        r0 = r38;
        r4 = r0.drawPinnedBottom;
        if (r4 == 0) goto L_0x348b;
    L_0x3442:
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x348b;
    L_0x3448:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.totalHeight = r4;
    L_0x3457:
        r0 = r39;
        r4 = r0.type;
        r5 = 13;
        if (r4 != r5) goto L_0x3475;
    L_0x345f:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1116471296; // 0x428c0000 float:70.0 double:5.51610112E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        if (r4 >= r5) goto L_0x3475;
    L_0x346b:
        r4 = 1116471296; // 0x428c0000 float:70.0 double:5.51610112E-315;
        r4 = org.telegram.messenger.AndroidUtilities.dp(r4);
        r0 = r38;
        r0.totalHeight = r4;
    L_0x3475:
        r38.updateWaveform();
        r0 = r38;
        r1 = r17;
        r0.updateButtonState(r1);
        return;
    L_0x3480:
        r4 = 0;
        r0 = r38;
        r0.substractBackgroundHeight = r4;
        r4 = 0;
        r0 = r38;
        r0.keyboardHeight = r4;
        goto L_0x343c;
    L_0x348b:
        r0 = r38;
        r4 = r0.drawPinnedBottom;
        if (r4 == 0) goto L_0x34a1;
    L_0x3491:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.totalHeight = r4;
        goto L_0x3457;
    L_0x34a1:
        r0 = r38;
        r4 = r0.drawPinnedTop;
        if (r4 == 0) goto L_0x3457;
    L_0x34a7:
        r0 = r38;
        r4 = r0.pinnedBottom;
        if (r4 == 0) goto L_0x3457;
    L_0x34ad:
        r0 = r38;
        r4 = r0.currentPosition;
        if (r4 == 0) goto L_0x3457;
    L_0x34b3:
        r0 = r38;
        r4 = r0.currentPosition;
        r4 = r4.siblingHeights;
        if (r4 != 0) goto L_0x3457;
    L_0x34bb:
        r0 = r38;
        r4 = r0.totalHeight;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5 = org.telegram.messenger.AndroidUtilities.dp(r5);
        r4 = r4 - r5;
        r0 = r38;
        r0.totalHeight = r4;
        goto L_0x3457;
    L_0x34cb:
        r4 = move-exception;
        goto L_0x0cea;
    L_0x34ce:
        r4 = move-exception;
        r6 = r14;
        r8 = r31;
        goto L_0x0cb0;
    L_0x34d4:
        r4 = move-exception;
        r8 = r31;
        goto L_0x0cb0;
    L_0x34d9:
        r4 = move-exception;
        goto L_0x0cb0;
    L_0x34dc:
        r4 = move-exception;
        r6 = r31;
        r7 = r32;
        r8 = r33;
        r9 = r34;
        goto L_0x0b01;
    L_0x34e7:
        r4 = move-exception;
        r6 = r31;
        r8 = r33;
        r9 = r34;
        goto L_0x0b01;
    L_0x34f0:
        r4 = move-exception;
        r7 = r31;
        goto L_0x0ac3;
    L_0x34f5:
        r4 = move-exception;
        goto L_0x0ac3;
    L_0x34f8:
        r5 = r16;
        goto L_0x33ee;
    L_0x34fc:
        r5 = r16;
        goto L_0x328e;
    L_0x3500:
        r13 = r4;
        goto L_0x3231;
    L_0x3503:
        r9 = r4;
        goto L_0x2e34;
    L_0x3506:
        r4 = r30;
        goto L_0x2d86;
    L_0x350a:
        r4 = r6;
        goto L_0x2cf2;
    L_0x350d:
        r4 = r10;
        goto L_0x2c88;
    L_0x3510:
        r6 = r4;
        goto L_0x2978;
    L_0x3513:
        r37 = r4;
        r4 = r5;
        r5 = r37;
        goto L_0x2958;
    L_0x351a:
        r4 = r5;
        goto L_0x2ae9;
    L_0x351d:
        r4 = r6;
        goto L_0x294d;
    L_0x3520:
        r4 = r6;
        r5 = r7;
        goto L_0x294d;
    L_0x3524:
        r6 = r4;
        r7 = r5;
        goto L_0x28fc;
    L_0x3528:
        r15 = r6;
        goto L_0x2865;
    L_0x352b:
        r13 = r5;
        goto L_0x285a;
    L_0x352e:
        r12 = r4;
        goto L_0x284f;
    L_0x3531:
        r15 = r4;
        r18 = r5;
        goto L_0x2731;
    L_0x3536:
        r4 = r6;
        r5 = r7;
        goto L_0x2701;
    L_0x353a:
        r9 = r4;
        goto L_0x2440;
    L_0x353d:
        r5 = r4;
        goto L_0x1d42;
    L_0x3540:
        r5 = r4;
        goto L_0x1cf4;
    L_0x3543:
        r13 = r4;
        goto L_0x1ce1;
    L_0x3546:
        r13 = r6;
        goto L_0x1b7c;
    L_0x3549:
        r12 = r4;
        goto L_0x1b27;
    L_0x354c:
        r8 = r7;
        r9 = r4;
        goto L_0x0e7a;
    L_0x3550:
        r8 = r27;
        goto L_0x0cee;
    L_0x3554:
        r10 = r15;
        r28 = r32;
        r27 = r31;
        r31 = r14;
        goto L_0x0baa;
    L_0x355d:
        r15 = r32;
        r32 = r33;
        r33 = r31;
        r31 = r34;
        goto L_0x0b0b;
    L_0x3567:
        r36 = r12;
        r34 = r31;
        goto L_0x06fd;
    L_0x356d:
        r20 = r6;
        goto L_0x0603;
    L_0x3571:
        r6 = r4;
        goto L_0x05bd;
    L_0x3574:
        r5 = r6;
        goto L_0x0443;
    L_0x3577:
        r19 = r4;
        goto L_0x03af;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatMessageCell.setMessageObject(org.telegram.messenger.MessageObject, org.telegram.messenger.MessageObject$GroupedMessages, boolean, boolean):void");
    }

    public void setPressed(boolean z) {
        super.setPressed(z);
        updateRadialProgressBackground(true);
        if (this.useSeekBarWaweform) {
            this.seekBarWaveform.setSelected(isDrawSelectedBackground());
        } else {
            this.seekBar.setSelected(isDrawSelectedBackground());
        }
        invalidate();
    }

    public void setVisiblePart(int i, int i2) {
        int i3 = 0;
        if (this.currentMessageObject != null && this.currentMessageObject.textLayoutBlocks != null) {
            int i4 = i - this.textY;
            int i5 = 0;
            int i6 = 0;
            while (i5 < this.currentMessageObject.textLayoutBlocks.size() && ((TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i5)).textYOffset <= ((float) i4)) {
                i6 = i5;
                i5++;
            }
            i5 = -1;
            int i7 = -1;
            while (i6 < this.currentMessageObject.textLayoutBlocks.size()) {
                int i8;
                TextLayoutBlock textLayoutBlock = (TextLayoutBlock) this.currentMessageObject.textLayoutBlocks.get(i6);
                float f = textLayoutBlock.textYOffset;
                if (intersect(f, ((float) textLayoutBlock.height) + f, (float) i4, (float) (i4 + i2))) {
                    if (i7 == -1) {
                        i7 = i6;
                    }
                    i3++;
                    i8 = i6;
                    i5 = i7;
                } else if (f > ((float) i4)) {
                    break;
                } else {
                    i8 = i5;
                    i5 = i7;
                }
                i6++;
                i7 = i5;
                i5 = i8;
            }
            if (this.lastVisibleBlockNum != i5 || this.firstVisibleBlockNum != i7 || this.totalVisibleBlocksCount != i3) {
                this.lastVisibleBlockNum = i5;
                this.firstVisibleBlockNum = i7;
                this.totalVisibleBlocksCount = i3;
                invalidate();
            }
        }
    }

    public void updateButtonState(boolean z) {
        String str;
        boolean z2;
        float f = BitmapDescriptorFactory.HUE_RED;
        boolean z3 = true;
        this.drawRadialCheckBackground = false;
        if (this.currentMessageObject.type != 1) {
            if (this.currentMessageObject.type == 8 || this.currentMessageObject.type == 5 || this.documentAttachType == 7 || this.documentAttachType == 4 || this.currentMessageObject.type == 9 || this.documentAttachType == 3 || this.documentAttachType == 5) {
                if (this.currentMessageObject.useCustomPhoto) {
                    this.buttonState = 1;
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                    return;
                } else if (this.currentMessageObject.attachPathExists) {
                    str = this.currentMessageObject.messageOwner.attachPath;
                    z2 = true;
                } else if (!this.currentMessageObject.isSendError() || this.documentAttachType == 3 || this.documentAttachType == 5) {
                    str = this.currentMessageObject.getFileName();
                    z2 = this.currentMessageObject.mediaExists;
                }
            } else if (this.documentAttachType != 0) {
                str = FileLoader.getAttachFileName(this.documentAttach);
                z2 = this.currentMessageObject.mediaExists;
            } else if (this.currentPhotoObject != null) {
                str = FileLoader.getAttachFileName(this.currentPhotoObject);
                z2 = this.currentMessageObject.mediaExists;
            }
            str = null;
            z2 = false;
        } else if (this.currentPhotoObject != null) {
            str = FileLoader.getAttachFileName(this.currentPhotoObject);
            z2 = this.currentMessageObject.mediaExists;
        } else {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            this.radialProgress.setBackground(null, false, false);
            return;
        }
        boolean z4 = this.currentMessageObject.messageOwner.params != null && this.currentMessageObject.messageOwner.params.containsKey("query_id");
        Float fileProgress;
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            if ((this.currentMessageObject.isOut() && this.currentMessageObject.isSending()) || (this.currentMessageObject.isSendError() && z4)) {
                MediaController.getInstance().addLoadingFileObserver(this.currentMessageObject.messageOwner.attachPath, this.currentMessageObject, this);
                this.buttonState = 4;
                RadialProgress radialProgress = this.radialProgress;
                Drawable drawableForCurrentState = getDrawableForCurrentState();
                if (z4) {
                    z3 = false;
                }
                radialProgress.setBackground(drawableForCurrentState, z3, z);
                if (z4) {
                    this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                } else {
                    fileProgress = ImageLoader.getInstance().getFileProgress(this.currentMessageObject.messageOwner.attachPath);
                    if (fileProgress == null && SendMessagesHelper.getInstance().isSendingMessage(this.currentMessageObject.getId())) {
                        fileProgress = Float.valueOf(1.0f);
                    }
                    this.radialProgress.setProgress(fileProgress != null ? fileProgress.floatValue() : BitmapDescriptorFactory.HUE_RED, false);
                }
            } else if (z2) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                z2 = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (!z2 || (z2 && MediaController.getInstance().isMessagePaused())) {
                    this.buttonState = 0;
                } else {
                    this.buttonState = 1;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            } else {
                MediaController.getInstance().addLoadingFileObserver(str, this.currentMessageObject, this);
                if (FileLoader.getInstance().isLoadingFile(str)) {
                    this.buttonState = 4;
                    fileProgress = ImageLoader.getInstance().getFileProgress(str);
                    if (fileProgress != null) {
                        this.radialProgress.setProgress(fileProgress.floatValue(), z);
                    } else {
                        this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                    }
                    this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
                } else {
                    this.buttonState = 2;
                    this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                }
            }
            updatePlayingMessageProgress();
        } else if (this.currentMessageObject.type != 0 || this.documentAttachType == 1 || this.documentAttachType == 4) {
            if (!this.currentMessageObject.isOut() || !this.currentMessageObject.isSending()) {
                if (!(this.currentMessageObject.messageOwner.attachPath == null || this.currentMessageObject.messageOwner.attachPath.length() == 0)) {
                    MediaController.getInstance().removeLoadingFileObserver(this);
                }
                if (z2) {
                    MediaController.getInstance().removeLoadingFileObserver(this);
                    if (this.currentMessageObject.isSecretPhoto()) {
                        this.buttonState = -1;
                    } else if (this.currentMessageObject.type == 8 && !this.photoImage.isAllowStartAnimation()) {
                        this.buttonState = 2;
                    } else if (this.documentAttachType == 4) {
                        this.buttonState = 3;
                    } else {
                        this.buttonState = -1;
                    }
                    this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                    if (this.photoNotSet) {
                        setMessageObject(this.currentMessageObject, this.currentMessagesGroup, this.pinnedBottom, this.pinnedTop);
                    }
                    invalidate();
                    return;
                }
                MediaController.getInstance().addLoadingFileObserver(str, this.currentMessageObject, this);
                if (FileLoader.getInstance().isLoadingFile(str)) {
                    this.buttonState = 1;
                    fileProgress = ImageLoader.getInstance().getFileProgress(str);
                    if (fileProgress != null) {
                        f = fileProgress.floatValue();
                    }
                } else {
                    z2 = this.currentMessageObject.type == 1 ? MediaController.getInstance().canDownloadMedia(this.currentMessageObject) : (this.currentMessageObject.type == 8 && MessageObject.isNewGifDocument(this.currentMessageObject.messageOwner.media.document)) ? MediaController.getInstance().canDownloadMedia(this.currentMessageObject) : this.currentMessageObject.type == 5 ? MediaController.getInstance().canDownloadMedia(this.currentMessageObject) : false;
                    if (this.cancelLoading || !r0) {
                        this.buttonState = 0;
                        z3 = false;
                    } else {
                        this.buttonState = 1;
                    }
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), z3, z);
                this.radialProgress.setProgress(f, false);
                invalidate();
            } else if (this.currentMessageObject.messageOwner.attachPath != null && this.currentMessageObject.messageOwner.attachPath.length() > 0) {
                MediaController.getInstance().addLoadingFileObserver(this.currentMessageObject.messageOwner.attachPath, this.currentMessageObject, this);
                z2 = this.currentMessageObject.messageOwner.attachPath == null || !this.currentMessageObject.messageOwner.attachPath.startsWith("http");
                HashMap hashMap = this.currentMessageObject.messageOwner.params;
                if (this.currentMessageObject.messageOwner.message == null || hashMap == null || !(hashMap.containsKey(ImagesContract.URL) || hashMap.containsKey("bot"))) {
                    this.buttonState = 1;
                } else {
                    this.buttonState = -1;
                    z2 = false;
                }
                boolean isSendingMessage = SendMessagesHelper.getInstance().isSendingMessage(this.currentMessageObject.getId());
                if (this.currentPosition != null && isSendingMessage && this.buttonState == 1) {
                    this.drawRadialCheckBackground = true;
                    this.radialProgress.setCheckBackground(false, z);
                } else {
                    this.radialProgress.setBackground(getDrawableForCurrentState(), z2, z);
                }
                if (z2) {
                    fileProgress = ImageLoader.getInstance().getFileProgress(this.currentMessageObject.messageOwner.attachPath);
                    if (fileProgress == null && isSendingMessage) {
                        fileProgress = Float.valueOf(1.0f);
                    }
                    RadialProgress radialProgress2 = this.radialProgress;
                    if (fileProgress != null) {
                        f = fileProgress.floatValue();
                    }
                    radialProgress2.setProgress(f, false);
                } else {
                    this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
                }
                invalidate();
            }
        } else if (this.currentPhotoObject != null && this.drawImageButton) {
            if (z2) {
                MediaController.getInstance().removeLoadingFileObserver(this);
                if (this.documentAttachType != 2 || this.photoImage.isAllowStartAnimation()) {
                    this.buttonState = -1;
                } else {
                    this.buttonState = 2;
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
                invalidate();
                return;
            }
            MediaController.getInstance().addLoadingFileObserver(str, this.currentMessageObject, this);
            if (FileLoader.getInstance().isLoadingFile(str)) {
                this.buttonState = 1;
                fileProgress = ImageLoader.getInstance().getFileProgress(str);
                if (fileProgress != null) {
                    f = fileProgress.floatValue();
                }
            } else if (this.cancelLoading || !((this.documentAttachType == 0 && MediaController.getInstance().canDownloadMedia(this.currentMessageObject)) || (this.documentAttachType == 2 && MediaController.getInstance().canDownloadMedia(this.currentMessageObject)))) {
                this.buttonState = 0;
                z3 = false;
            } else {
                this.buttonState = 1;
            }
            this.radialProgress.setProgress(f, false);
            this.radialProgress.setBackground(getDrawableForCurrentState(), z3, z);
            invalidate();
        }
    }

    public void updatePlayingMessageProgress() {
        if (this.currentMessageObject != null) {
            int i;
            DocumentAttribute documentAttribute;
            int i2;
            CharSequence format;
            if (this.currentMessageObject.isRoundVideo()) {
                Document document = this.currentMessageObject.getDocument();
                for (i = 0; i < document.attributes.size(); i++) {
                    documentAttribute = (DocumentAttribute) document.attributes.get(i);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                        i2 = documentAttribute.duration;
                        break;
                    }
                }
                i2 = 0;
                if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                    i2 = Math.max(0, i2 - this.currentMessageObject.audioProgressSec);
                }
                if (this.lastTime != i2) {
                    this.lastTime = i2;
                    format = String.format("%02d:%02d", new Object[]{Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)});
                    this.timeWidthAudio = (int) Math.ceil((double) Theme.chat_timePaint.measureText(format));
                    this.durationLayout = new StaticLayout(format, Theme.chat_timePaint, this.timeWidthAudio, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    invalidate();
                }
            } else if (this.documentAttach != null) {
                if (this.useSeekBarWaweform) {
                    if (!this.seekBarWaveform.isDragging()) {
                        this.seekBarWaveform.setProgress(this.currentMessageObject.audioProgress);
                    }
                } else if (!this.seekBar.isDragging()) {
                    this.seekBar.setProgress(this.currentMessageObject.audioProgress);
                }
                if (this.documentAttachType == 3) {
                    if (MediaController.getInstance().isPlayingMessage(this.currentMessageObject)) {
                        i2 = this.currentMessageObject.audioProgressSec;
                    } else {
                        for (i = 0; i < this.documentAttach.attributes.size(); i++) {
                            documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i);
                            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                                i2 = documentAttribute.duration;
                                break;
                            }
                        }
                        i2 = 0;
                    }
                    if (this.lastTime != i2) {
                        this.lastTime = i2;
                        format = String.format("%02d:%02d", new Object[]{Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)});
                        this.timeWidthAudio = (int) Math.ceil((double) Theme.chat_audioTimePaint.measureText(format));
                        this.durationLayout = new StaticLayout(format, Theme.chat_audioTimePaint, this.timeWidthAudio, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    }
                } else {
                    for (i = 0; i < this.documentAttach.attributes.size(); i++) {
                        documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i);
                        if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                            i2 = documentAttribute.duration;
                            break;
                        }
                    }
                    i2 = 0;
                    i = MediaController.getInstance().isPlayingMessage(this.currentMessageObject) ? this.currentMessageObject.audioProgressSec : 0;
                    if (this.lastTime != i) {
                        this.lastTime = i;
                        format = String.format("%d:%02d / %d:%02d", new Object[]{Integer.valueOf(i / 60), Integer.valueOf(i % 60), Integer.valueOf(i2 / 60), Integer.valueOf(i2 % 60)});
                        this.durationLayout = new StaticLayout(format, Theme.chat_audioTimePaint, (int) Math.ceil((double) Theme.chat_audioTimePaint.measureText(format)), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    }
                }
                invalidate();
            }
        }
    }

    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.instantViewSelectorDrawable;
    }
}
