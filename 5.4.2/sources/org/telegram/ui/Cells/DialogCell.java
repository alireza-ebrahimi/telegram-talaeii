package org.telegram.ui.Cells;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.text.style.ForegroundColorSpan;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.p149a.C2488b;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_encryptedChatDiscarded;
import org.telegram.tgnet.TLRPC$TL_encryptedChatRequested;
import org.telegram.tgnet.TLRPC$TL_encryptedChatWaiting;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.GroupCreateCheckBox;
import utils.view.PixelUtil;

public class DialogCell extends BaseCell {
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private ImageReceiver avatarImage = new ImageReceiver(this);
    private int avatarLeftMargin;
    private int avatarSize = AndroidUtilities.dp(52.0f);
    private int avatarTop = AndroidUtilities.dp(10.0f);
    private Chat chat = null;
    private GroupCreateCheckBox checkBox;
    private int checkDrawLeft;
    private int checkDrawTop = AndroidUtilities.dp(18.0f);
    private StaticLayout countLayout;
    private int countLeft;
    private int countTop = AndroidUtilities.dp(39.0f);
    private int countWidth;
    private long currentDialogId;
    private int currentEditDate;
    private CustomDialog customDialog;
    private boolean dialogMuted;
    private int dialogsType;
    private DraftMessage draftMessage;
    private boolean drawCheck1;
    private boolean drawCheck2;
    private boolean drawClock;
    private boolean drawCount;
    private boolean drawError;
    private boolean drawMention;
    private boolean drawNameBot;
    private boolean drawNameBroadcast;
    private boolean drawNameGroup;
    private boolean drawNameLock;
    private boolean drawPin;
    private boolean drawPinBackground;
    private boolean drawStatus;
    private boolean drawVerified;
    private EncryptedChat encryptedChat = null;
    private int errorLeft;
    private int errorTop = AndroidUtilities.dp(39.0f);
    private int halfCheckDrawLeft;
    private int index;
    private boolean isDialogCell;
    public boolean isSelected;
    private int lastMessageDate;
    private CharSequence lastPrintString = null;
    private int lastSendState;
    private boolean lastUnreadState;
    private int mentionCount;
    private int mentionLeft;
    private int mentionWidth;
    private MessageObject message;
    private StaticLayout messageLayout;
    private int messageLeft;
    private int messageTop = AndroidUtilities.dp(40.0f);
    private StaticLayout nameLayout;
    private int nameLeft;
    private int nameLockLeft;
    private int nameLockTop;
    private int nameMuteLeft;
    private int pinLeft;
    private int pinTop = AndroidUtilities.dp(39.0f);
    private RectF rect = new RectF();
    private GradientDrawable statusBG;
    private StaticLayout timeLayout;
    private int timeLeft;
    private int timeTop = AndroidUtilities.dp(17.0f);
    private int unreadCount;
    public boolean useSeparator = false;
    private User user = null;

    public static class CustomDialog {
        public int date;
        public int id;
        public boolean isMedia;
        public boolean isSelected;
        public String message;
        public boolean muted;
        public String name;
        public boolean pinned;
        public boolean sent;
        public int type;
        public int unread_count;
        public boolean verified;
    }

    public DialogCell(Context context, boolean z) {
        super(context);
        Theme.createDialogsResources(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(26.0f));
        this.statusBG = new GradientDrawable();
        this.statusBG.setColor(-7829368);
        this.statusBG.setCornerRadius((float) AndroidUtilities.dp(16.0f));
        this.statusBG.setStroke(AndroidUtilities.dp(2.0f), -1);
        if (z) {
            this.checkBox = new GroupCreateCheckBox(context);
            this.checkBox.setVisibility(0);
            addView(this.checkBox);
        }
    }

    private ArrayList<TLRPC$TL_dialog> getDialogsArray() {
        return this.dialogsType == 0 ? MessagesController.getInstance().dialogsAll : this.dialogsType == 1 ? MessagesController.getInstance().dialogsServerOnly : this.dialogsType == 2 ? MessagesController.getInstance().dialogsGroupsOnly : this.dialogsType == 13 ? MessagesController.getInstance().dialogsForward : this.dialogsType == 3 ? MessagesController.getInstance().dialogsUsers : this.dialogsType == 4 ? MessagesController.getInstance().dialogsGroups : this.dialogsType == 5 ? MessagesController.getInstance().dialogsChannels : this.dialogsType == 6 ? MessagesController.getInstance().dialogsBots : this.dialogsType == 7 ? MessagesController.getInstance().dialogsMegaGroups : this.dialogsType == 8 ? MessagesController.getInstance().dialogsFavs : this.dialogsType == 9 ? MessagesController.getInstance().dialogsGroupsAll : this.dialogsType == 10 ? MessagesController.getInstance().dialogsHidden : this.dialogsType == 11 ? MessagesController.getInstance().dialogsUnread : this.dialogsType == 12 ? MessagesController.getInstance().dialogsAds : null;
    }

    private void setStatusColor() {
        String formatUserStatus = LocaleController.formatUserStatus(this.user);
        if (formatUserStatus.equals(LocaleController.getString("ALongTimeAgo", R.string.ALongTimeAgo))) {
            this.statusBG.setColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        } else if (formatUserStatus.equals(LocaleController.getString("Online", R.string.Online))) {
            this.statusBG.setColor(-16718218);
        } else if (formatUserStatus.equals(LocaleController.getString("Lately", R.string.Lately))) {
            this.statusBG.setColor(-3355444);
        } else {
            this.statusBG.setColor(-7829368);
        }
        int currentTime = this.user.status != null ? ConnectionsManager.getInstance().getCurrentTime() - this.user.status.expires : -2;
        if (currentTime > 0 && currentTime < 86400) {
            this.statusBG.setColor(-3355444);
        }
    }

    public void buildLayout() {
        String str;
        int i;
        Object obj;
        TextPaint textPaint;
        CharSequence charSequence;
        String str2;
        String str3;
        int ceil;
        int intrinsicWidth;
        String str4 = TtmlNode.ANONYMOUS_REGION_ID;
        String str5 = TtmlNode.ANONYMOUS_REGION_ID;
        String str6 = null;
        String str7 = TtmlNode.ANONYMOUS_REGION_ID;
        CharSequence charSequence2 = null;
        if (this.isDialogCell) {
            charSequence2 = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.currentDialogId));
        }
        TextPaint textPaint2 = Theme.dialogs_namePaint;
        TextPaint textPaint3 = Theme.dialogs_messagePaint;
        Object obj2 = 1;
        this.drawNameGroup = false;
        this.drawNameBroadcast = false;
        this.drawNameLock = false;
        this.drawNameBot = false;
        this.drawVerified = false;
        this.drawStatus = false;
        this.drawPinBackground = false;
        Object obj3 = !UserObject.isUserSelf(this.user) ? 1 : null;
        Object obj4 = 1;
        String str8 = VERSION.SDK_INT >= 18 ? "%s: ⁨%s⁩" : "%s: %s";
        String string;
        CharSequence replaceEmoji;
        Object obj5;
        Object obj6;
        String str9;
        if (this.customDialog != null) {
            TextPaint textPaint4;
            String format;
            if (this.customDialog.type == 2) {
                this.drawNameLock = true;
                this.nameLockTop = AndroidUtilities.dp(16.5f);
                if (LocaleController.isRTL) {
                    this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - Theme.dialogs_lockDrawable.getIntrinsicWidth();
                    this.nameLeft = AndroidUtilities.dp(14.0f);
                } else {
                    this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                    this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
                }
            } else {
                this.drawVerified = this.customDialog.verified;
                if (this.customDialog.type == 1) {
                    this.drawNameGroup = true;
                    this.nameLockTop = AndroidUtilities.dp(17.5f);
                    if (LocaleController.isRTL) {
                        this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth());
                        this.nameLeft = AndroidUtilities.dp(14.0f);
                    } else {
                        this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                        this.nameLeft = (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth()) + AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
                    }
                } else if (LocaleController.isRTL) {
                    this.nameLeft = AndroidUtilities.dp(14.0f);
                } else {
                    this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                }
            }
            if (this.customDialog.type == 1) {
                string = LocaleController.getString("FromYou", R.string.FromYou);
                if (this.customDialog.isMedia) {
                    textPaint4 = Theme.dialogs_messagePrintingPaint;
                    charSequence2 = SpannableStringBuilder.valueOf(String.format(str8, new Object[]{string, this.message.messageText}));
                    charSequence2.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_attachMessage)), string.length() + 2, charSequence2.length(), 33);
                } else {
                    str = this.customDialog.message;
                    if (str.length() > 150) {
                        str = str.substring(0, 150);
                    }
                    charSequence2 = SpannableStringBuilder.valueOf(String.format(str8, new Object[]{string, str.replace('\n', ' ')}));
                    textPaint4 = textPaint3;
                }
                if (charSequence2.length() > 0) {
                    charSequence2.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_nameMessage)), 0, string.length() + 1, 33);
                }
                replaceEmoji = Emoji.replaceEmoji(charSequence2, Theme.dialogs_messagePaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                obj5 = null;
            } else {
                str = this.customDialog.message;
                if (this.customDialog.isMedia) {
                    textPaint4 = Theme.dialogs_messagePrintingPaint;
                    obj6 = str;
                    i = 1;
                } else {
                    textPaint4 = textPaint3;
                    obj6 = str;
                    i = 1;
                }
            }
            string = LocaleController.stringForMessageListDate((long) this.customDialog.date);
            if (this.customDialog.unread_count != 0) {
                this.drawCount = true;
                format = String.format("%d", new Object[]{Integer.valueOf(this.customDialog.unread_count)});
            } else {
                this.drawCount = false;
                format = null;
            }
            if (this.customDialog.sent) {
                this.drawCheck1 = true;
                this.drawCheck2 = true;
                this.drawClock = false;
                this.drawError = false;
            } else {
                this.drawCheck1 = false;
                this.drawCheck2 = false;
                this.drawClock = false;
                this.drawError = false;
            }
            str9 = this.customDialog.name;
            if (this.customDialog.type == 2) {
                textPaint2 = Theme.dialogs_nameEncryptedPaint;
                obj = obj5;
                textPaint = textPaint4;
                charSequence = replaceEmoji;
                str2 = null;
                str3 = format;
                str7 = str9;
                CharSequence charSequence3 = string;
            } else {
                obj = obj5;
                textPaint = textPaint4;
                charSequence = replaceEmoji;
                str2 = null;
                str3 = format;
                str7 = str9;
                obj3 = string;
            }
        } else {
            String str10;
            String str11;
            if (this.encryptedChat != null) {
                this.drawNameLock = true;
                this.nameLockTop = AndroidUtilities.dp(16.5f);
                if (LocaleController.isRTL) {
                    this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - Theme.dialogs_lockDrawable.getIntrinsicWidth();
                    this.nameLeft = AndroidUtilities.dp(14.0f);
                } else {
                    this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                    this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
                }
            } else if (this.chat != null) {
                if (this.chat.id < 0 || (ChatObject.isChannel(this.chat) && !this.chat.megagroup)) {
                    this.drawNameBroadcast = true;
                    this.nameLockTop = AndroidUtilities.dp(16.5f);
                } else {
                    this.drawNameGroup = true;
                    this.nameLockTop = AndroidUtilities.dp(17.5f);
                }
                this.drawVerified = this.chat.verified;
                if (LocaleController.isRTL) {
                    this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth());
                    this.nameLeft = AndroidUtilities.dp(14.0f);
                } else {
                    this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                    this.nameLeft = (this.drawNameGroup ? Theme.dialogs_groupDrawable.getIntrinsicWidth() : Theme.dialogs_broadcastDrawable.getIntrinsicWidth()) + AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4));
                }
            } else {
                if (LocaleController.isRTL) {
                    this.nameLeft = AndroidUtilities.dp(14.0f);
                } else {
                    this.nameLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                }
                if (this.user != null) {
                    if (this.user.bot) {
                        this.drawNameBot = true;
                        this.nameLockTop = AndroidUtilities.dp(16.5f);
                        if (LocaleController.isRTL) {
                            this.nameLockLeft = (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - Theme.dialogs_botDrawable.getIntrinsicWidth();
                            this.nameLeft = AndroidUtilities.dp(14.0f);
                        } else {
                            this.nameLockLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
                            this.nameLeft = AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 4)) + Theme.dialogs_botDrawable.getIntrinsicWidth();
                        }
                    }
                    this.drawVerified = this.user.verified;
                }
            }
            int i2 = this.lastMessageDate;
            if (this.lastMessageDate == 0 && this.message != null) {
                i2 = this.message.messageOwner.date;
            }
            if (this.isDialogCell) {
                this.draftMessage = DraftQuery.getDraft(this.currentDialogId);
                if ((this.draftMessage != null && ((TextUtils.isEmpty(this.draftMessage.message) && this.draftMessage.reply_to_msg_id == 0) || (r8 > this.draftMessage.date && this.unreadCount != 0))) || ((ChatObject.isChannel(this.chat) && !this.chat.megagroup && !this.chat.creator && (this.chat.admin_rights == null || !this.chat.admin_rights.post_messages)) || (this.chat != null && (this.chat.left || this.chat.kicked)))) {
                    this.draftMessage = null;
                }
            } else {
                this.draftMessage = null;
            }
            if (charSequence2 != null) {
                this.lastPrintString = charSequence2;
                textPaint3 = Theme.dialogs_messagePrintingPaint;
                str8 = charSequence2;
                obj5 = 1;
            } else {
                this.lastPrintString = null;
                if (this.draftMessage != null) {
                    obj2 = null;
                    if (TextUtils.isEmpty(this.draftMessage.message)) {
                        obj6 = LocaleController.getString("Draft", R.string.Draft);
                        SpannableStringBuilder valueOf = SpannableStringBuilder.valueOf(obj6);
                        valueOf.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_draft)), 0, obj6.length(), 33);
                        obj6 = valueOf;
                        i = 1;
                    } else {
                        str = this.draftMessage.message;
                        if (str.length() > 150) {
                            str = str.substring(0, 150);
                        }
                        charSequence2 = SpannableStringBuilder.valueOf(String.format(str8, new Object[]{LocaleController.getString("Draft", R.string.Draft), str.replace('\n', ' ')}));
                        charSequence2.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_draft)), 0, string.length() + 1, 33);
                        replaceEmoji = Emoji.replaceEmoji(charSequence2, Theme.dialogs_messagePaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        i = 1;
                    }
                } else if (this.message == null) {
                    if (this.encryptedChat != null) {
                        textPaint3 = Theme.dialogs_messagePrintingPaint;
                        if (this.encryptedChat instanceof TLRPC$TL_encryptedChatRequested) {
                            str8 = LocaleController.getString("EncryptionProcessing", R.string.EncryptionProcessing);
                            i = 1;
                        } else if (this.encryptedChat instanceof TLRPC$TL_encryptedChatWaiting) {
                            if (this.user == null || this.user.first_name == null) {
                                str8 = LocaleController.formatString("AwaitingEncryption", R.string.AwaitingEncryption, new Object[]{TtmlNode.ANONYMOUS_REGION_ID});
                                i = 1;
                            } else {
                                str8 = LocaleController.formatString("AwaitingEncryption", R.string.AwaitingEncryption, new Object[]{this.user.first_name});
                                i = 1;
                            }
                        } else if (this.encryptedChat instanceof TLRPC$TL_encryptedChatDiscarded) {
                            str8 = LocaleController.getString("EncryptionRejected", R.string.EncryptionRejected);
                            i = 1;
                        } else if (this.encryptedChat instanceof TLRPC$TL_encryptedChat) {
                            if (this.encryptedChat.admin_id != UserConfig.getClientUserId()) {
                                str8 = LocaleController.getString("EncryptedChatStartedIncoming", R.string.EncryptedChatStartedIncoming);
                                i = 1;
                            } else if (this.user == null || this.user.first_name == null) {
                                str8 = LocaleController.formatString("EncryptedChatStartedOutgoing", R.string.EncryptedChatStartedOutgoing, new Object[]{TtmlNode.ANONYMOUS_REGION_ID});
                                i = 1;
                            } else {
                                str8 = LocaleController.formatString("EncryptedChatStartedOutgoing", R.string.EncryptedChatStartedOutgoing, new Object[]{this.user.first_name});
                                i = 1;
                            }
                        }
                    }
                    i = 1;
                    str8 = str7;
                } else {
                    User user = null;
                    Chat chat = null;
                    if (this.message.isFromUser()) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(this.message.messageOwner.from_id));
                    } else {
                        chat = MessagesController.getInstance().getChat(Integer.valueOf(this.message.messageOwner.to_id.channel_id));
                    }
                    if (this.dialogsType == 3 && UserObject.isUserSelf(this.user)) {
                        str8 = LocaleController.getString("SavedMessagesInfo", R.string.SavedMessagesInfo);
                        obj3 = null;
                        obj5 = null;
                    } else if (this.message.messageOwner instanceof TLRPC$TL_messageService) {
                        if (ChatObject.isChannel(this.chat) && (this.message.messageOwner.action instanceof TLRPC$TL_messageActionHistoryClear)) {
                            str = TtmlNode.ANONYMOUS_REGION_ID;
                            obj3 = null;
                        } else {
                            str = this.message.messageText;
                        }
                        textPaint3 = Theme.dialogs_messagePrintingPaint;
                        str8 = str;
                        i = 1;
                    } else if (this.chat != null && this.chat.id > 0 && chat == null) {
                        str = this.message.isOutOwner() ? LocaleController.getString("FromYou", R.string.FromYou) : user != null ? UserObject.getFirstName(user).replace("\n", TtmlNode.ANONYMOUS_REGION_ID) : chat != null ? chat.title.replace("\n", TtmlNode.ANONYMOUS_REGION_ID) : "DELETED";
                        if (this.message.caption != null) {
                            str9 = this.message.caption.toString();
                            if (str9.length() > 150) {
                                str9 = str9.substring(0, 150);
                            }
                            replaceEmoji = SpannableStringBuilder.valueOf(String.format(str8, new Object[]{str, str9.replace('\n', ' ')}));
                        } else if (this.message.messageOwner.media != null && !this.message.isMediaEmpty()) {
                            textPaint3 = Theme.dialogs_messagePrintingPaint;
                            replaceEmoji = this.message.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? VERSION.SDK_INT >= 18 ? SpannableStringBuilder.valueOf(String.format("%s: 🎮 ⁨%s⁩", new Object[]{str, this.message.messageOwner.media.game.title})) : SpannableStringBuilder.valueOf(String.format("%s: 🎮 %s", new Object[]{str, this.message.messageOwner.media.game.title})) : this.message.type == 14 ? VERSION.SDK_INT >= 18 ? SpannableStringBuilder.valueOf(String.format("%s: 🎧 ⁨%s - %s⁩", new Object[]{str, this.message.getMusicAuthor(), this.message.getMusicTitle()})) : SpannableStringBuilder.valueOf(String.format("%s: 🎧 %s - %s", new Object[]{str, this.message.getMusicAuthor(), this.message.getMusicTitle()})) : SpannableStringBuilder.valueOf(String.format(str8, new Object[]{str, this.message.messageText}));
                            replaceEmoji.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_attachMessage)), str.length() + 2, replaceEmoji.length(), 33);
                        } else if (this.message.messageOwner.message != null) {
                            str9 = this.message.messageOwner.message;
                            if (str9.length() > 150) {
                                str9 = str9.substring(0, 150);
                            }
                            replaceEmoji = SpannableStringBuilder.valueOf(String.format(str8, new Object[]{str, str9.replace('\n', ' ')}));
                        } else {
                            replaceEmoji = SpannableStringBuilder.valueOf(TtmlNode.ANONYMOUS_REGION_ID);
                        }
                        if (replaceEmoji.length() > 0) {
                            replaceEmoji.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_chats_nameMessage)), 0, str.length() + 1, 33);
                        }
                        obj2 = null;
                        replaceEmoji = Emoji.replaceEmoji(replaceEmoji, Theme.dialogs_messagePaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
                        i = 1;
                    } else if ((this.message.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) && (this.message.messageOwner.media.photo instanceof TLRPC$TL_photoEmpty) && this.message.messageOwner.media.ttl_seconds != 0) {
                        str8 = LocaleController.getString("AttachPhotoExpired", R.string.AttachPhotoExpired);
                        i = 1;
                    } else if ((this.message.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) && (this.message.messageOwner.media.document instanceof TLRPC$TL_documentEmpty) && this.message.messageOwner.media.ttl_seconds != 0) {
                        str8 = LocaleController.getString("AttachVideoExpired", R.string.AttachVideoExpired);
                        i = 1;
                    } else if (this.message.caption != null) {
                        replaceEmoji = this.message.caption;
                        i = 1;
                    } else {
                        str = this.message.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? "🎮 " + this.message.messageOwner.media.game.title : this.message.type == 14 ? String.format("🎧 %s - %s", new Object[]{this.message.getMusicAuthor(), this.message.getMusicTitle()}) : this.message.messageText;
                        if (this.message.messageOwner.media == null || this.message.isMediaEmpty()) {
                            str8 = str;
                            i = 1;
                        } else {
                            textPaint3 = Theme.dialogs_messagePrintingPaint;
                            str8 = str;
                            i = 1;
                        }
                    }
                }
            }
            string = this.draftMessage != null ? LocaleController.stringForMessageListDate((long) this.draftMessage.date) : this.lastMessageDate != 0 ? LocaleController.stringForMessageListDate((long) this.lastMessageDate) : this.message != null ? LocaleController.stringForMessageListDate((long) this.message.messageOwner.date) : str5;
            if (this.message == null) {
                this.drawCheck1 = false;
                this.drawCheck2 = false;
                this.drawClock = false;
                this.drawCount = false;
                this.drawMention = false;
                this.drawError = false;
                str10 = null;
            } else {
                if (this.unreadCount == 0 || (this.unreadCount == 1 && this.unreadCount == this.mentionCount && this.message != null && this.message.messageOwner.mentioned)) {
                    this.drawCount = false;
                } else {
                    this.drawCount = true;
                    str6 = String.format("%d", new Object[]{Integer.valueOf(this.unreadCount)});
                }
                if (this.mentionCount != 0) {
                    this.drawMention = true;
                    str10 = "@";
                } else {
                    this.drawMention = false;
                    str10 = null;
                }
                if (!this.message.isOut() || this.draftMessage != null || r3 == null) {
                    this.drawCheck1 = false;
                    this.drawCheck2 = false;
                    this.drawClock = false;
                    this.drawError = false;
                } else if (this.message.isSending()) {
                    this.drawCheck1 = false;
                    this.drawCheck2 = false;
                    this.drawClock = true;
                    this.drawError = false;
                } else if (this.message.isSendError()) {
                    this.drawCheck1 = false;
                    this.drawCheck2 = false;
                    this.drawClock = false;
                    this.drawError = true;
                    this.drawCount = false;
                    this.drawMention = false;
                } else if (this.message.isSent()) {
                    boolean z = !this.message.isUnread() || (ChatObject.isChannel(this.chat) && !this.chat.megagroup);
                    this.drawCheck1 = z;
                    this.drawCheck2 = true;
                    this.drawClock = false;
                    this.drawError = false;
                }
            }
            if (this.chat != null) {
                str11 = this.chat.title;
            } else if (this.user != null) {
                if (UserObject.isUserSelf(this.user)) {
                    if (this.dialogsType == 3) {
                        this.drawPinBackground = true;
                    }
                    str11 = LocaleController.getString("SavedMessages", R.string.SavedMessages);
                } else {
                    str11 = (this.user.id / 1000 == 777 || this.user.id / 1000 == 333 || ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.user.id)) != null) ? UserObject.getUserName(this.user) : (ContactsController.getInstance().contactsDict.size() != 0 || (ContactsController.getInstance().contactsLoaded && !ContactsController.getInstance().isLoadingContacts())) ? (this.user.phone == null || this.user.phone.length() == 0) ? UserObject.getUserName(this.user) : C2488b.a().e("+" + this.user.phone) : UserObject.getUserName(this.user);
                }
                TextPaint textPaint5 = this.encryptedChat != null ? Theme.dialogs_nameEncryptedPaint : textPaint2;
                if (!this.drawNameBot) {
                    this.drawStatus = true;
                }
                textPaint2 = textPaint5;
            } else {
                str11 = str4;
            }
            Object obj7;
            if (str11.length() == 0) {
                obj = obj2;
                textPaint = textPaint3;
                obj7 = str8;
                str2 = str10;
                str3 = str6;
                str7 = LocaleController.getString("HiddenName", R.string.HiddenName);
                obj3 = string;
                obj4 = obj5;
            } else {
                obj = obj2;
                textPaint = textPaint3;
                obj7 = str8;
                str2 = str10;
                str3 = str6;
                str7 = str11;
                obj3 = string;
                obj4 = obj5;
            }
        }
        if (obj4 != null) {
            ceil = (int) Math.ceil((double) Theme.dialogs_timePaint.measureText(charSequence3));
            this.timeLayout = new StaticLayout(charSequence3, Theme.dialogs_timePaint, ceil, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            if (LocaleController.isRTL) {
                this.timeLeft = AndroidUtilities.dp(15.0f);
            } else {
                this.timeLeft = (getMeasuredWidth() - AndroidUtilities.dp(15.0f)) - ceil;
            }
        } else {
            ceil = 0;
            this.timeLayout = null;
            this.timeLeft = 0;
        }
        if (LocaleController.isRTL) {
            i = ((getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - ceil;
            this.nameLeft += ceil;
        } else {
            i = ((getMeasuredWidth() - this.nameLeft) - AndroidUtilities.dp(14.0f)) - ceil;
        }
        if (this.drawNameLock) {
            i -= AndroidUtilities.dp(4.0f) + Theme.dialogs_lockDrawable.getIntrinsicWidth();
        } else if (this.drawNameGroup) {
            i -= AndroidUtilities.dp(4.0f) + Theme.dialogs_groupDrawable.getIntrinsicWidth();
        } else if (this.drawNameBroadcast) {
            i -= AndroidUtilities.dp(4.0f) + Theme.dialogs_broadcastDrawable.getIntrinsicWidth();
        } else if (this.drawNameBot) {
            i -= AndroidUtilities.dp(4.0f) + Theme.dialogs_botDrawable.getIntrinsicWidth();
        }
        if (this.drawClock) {
            intrinsicWidth = Theme.dialogs_clockDrawable.getIntrinsicWidth() + AndroidUtilities.dp(5.0f);
            i -= intrinsicWidth;
            if (LocaleController.isRTL) {
                this.checkDrawLeft = (this.timeLeft + ceil) + AndroidUtilities.dp(5.0f);
                this.nameLeft = intrinsicWidth + this.nameLeft;
            } else {
                this.checkDrawLeft = this.timeLeft - intrinsicWidth;
            }
        } else if (this.drawCheck2) {
            intrinsicWidth = Theme.dialogs_checkDrawable.getIntrinsicWidth() + AndroidUtilities.dp(5.0f);
            i -= intrinsicWidth;
            if (this.drawCheck1) {
                i -= Theme.dialogs_halfCheckDrawable.getIntrinsicWidth() - AndroidUtilities.dp(8.0f);
                if (LocaleController.isRTL) {
                    this.checkDrawLeft = (this.timeLeft + ceil) + AndroidUtilities.dp(5.0f);
                    this.halfCheckDrawLeft = this.checkDrawLeft + AndroidUtilities.dp(5.5f);
                    this.nameLeft = ((intrinsicWidth + Theme.dialogs_halfCheckDrawable.getIntrinsicWidth()) - AndroidUtilities.dp(8.0f)) + this.nameLeft;
                } else {
                    this.halfCheckDrawLeft = this.timeLeft - intrinsicWidth;
                    this.checkDrawLeft = this.halfCheckDrawLeft - AndroidUtilities.dp(5.5f);
                }
            } else if (LocaleController.isRTL) {
                this.checkDrawLeft = (this.timeLeft + ceil) + AndroidUtilities.dp(5.0f);
                this.nameLeft = intrinsicWidth + this.nameLeft;
            } else {
                this.checkDrawLeft = this.timeLeft - intrinsicWidth;
            }
        }
        if (this.dialogMuted && !this.drawVerified) {
            intrinsicWidth = AndroidUtilities.dp(6.0f) + Theme.dialogs_muteDrawable.getIntrinsicWidth();
            i -= intrinsicWidth;
            if (LocaleController.isRTL) {
                this.nameLeft = intrinsicWidth + this.nameLeft;
            }
        } else if (this.drawVerified) {
            intrinsicWidth = AndroidUtilities.dp(6.0f) + Theme.dialogs_verifiedDrawable.getIntrinsicWidth();
            i -= intrinsicWidth;
            if (LocaleController.isRTL) {
                this.nameLeft = intrinsicWidth + this.nameLeft;
            }
        }
        ceil = Math.max(AndroidUtilities.dp(12.0f), i);
        try {
            this.nameLayout = new StaticLayout(TextUtils.ellipsize(str7.replace('\n', ' '), textPaint2, (float) (ceil - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint2, ceil, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e) {
            FileLog.e(e);
        }
        intrinsicWidth = getMeasuredWidth() - AndroidUtilities.dp((float) (AndroidUtilities.leftBaseline + 16));
        if (LocaleController.isRTL) {
            this.messageLeft = AndroidUtilities.dp(16.0f);
            i = getMeasuredWidth() - AndroidUtilities.dp(AndroidUtilities.isTablet() ? 65.0f : 61.0f);
        } else {
            this.messageLeft = AndroidUtilities.dp((float) AndroidUtilities.leftBaseline);
            i = AndroidUtilities.dp(AndroidUtilities.isTablet() ? 13.0f : 9.0f);
        }
        this.avatarImage.setImageCoords(i, this.avatarTop, AndroidUtilities.dp(52.0f), AndroidUtilities.dp(52.0f));
        int dp;
        if (this.drawError) {
            dp = AndroidUtilities.dp(31.0f);
            i = intrinsicWidth - dp;
            if (LocaleController.isRTL) {
                this.errorLeft = AndroidUtilities.dp(11.0f);
                this.messageLeft += dp;
            } else {
                this.errorLeft = getMeasuredWidth() - AndroidUtilities.dp(34.0f);
            }
            intrinsicWidth = i;
        } else {
            if (str3 == null && str2 == null) {
                if (this.drawPin) {
                    dp = AndroidUtilities.dp(8.0f) + Theme.dialogs_pinnedDrawable.getIntrinsicWidth();
                    i = intrinsicWidth - dp;
                    if (LocaleController.isRTL) {
                        this.pinLeft = AndroidUtilities.dp(14.0f);
                        this.messageLeft += dp;
                    } else {
                        this.pinLeft = (getMeasuredWidth() - Theme.dialogs_pinnedDrawable.getIntrinsicWidth()) - AndroidUtilities.dp(14.0f);
                    }
                } else {
                    i = intrinsicWidth;
                }
                this.drawCount = false;
                this.drawMention = false;
            } else {
                if (str3 != null) {
                    this.countWidth = Math.max(AndroidUtilities.dp(12.0f), (int) Math.ceil((double) Theme.dialogs_countTextPaint.measureText(str3)));
                    this.countLayout = new StaticLayout(str3, Theme.dialogs_countTextPaint, this.countWidth, Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    dp = AndroidUtilities.dp(18.0f) + this.countWidth;
                    i = intrinsicWidth - dp;
                    if (LocaleController.isRTL) {
                        this.countLeft = AndroidUtilities.dp(19.0f);
                        this.messageLeft += dp;
                    } else {
                        this.countLeft = (getMeasuredWidth() - this.countWidth) - AndroidUtilities.dp(19.0f);
                    }
                    this.drawCount = true;
                } else {
                    this.countWidth = 0;
                    i = intrinsicWidth;
                }
                if (str2 != null) {
                    this.mentionWidth = AndroidUtilities.dp(12.0f);
                    dp = AndroidUtilities.dp(18.0f) + this.mentionWidth;
                    intrinsicWidth = i - dp;
                    if (LocaleController.isRTL) {
                        this.mentionLeft = (this.countWidth != 0 ? this.countWidth + AndroidUtilities.dp(18.0f) : 0) + AndroidUtilities.dp(19.0f);
                        this.messageLeft += dp;
                    } else {
                        this.mentionLeft = ((getMeasuredWidth() - this.mentionWidth) - AndroidUtilities.dp(19.0f)) - (this.countWidth != 0 ? this.countWidth + AndroidUtilities.dp(18.0f) : 0);
                    }
                    this.drawMention = true;
                }
            }
            intrinsicWidth = i;
        }
        if (obj != null) {
            str = (charSequence == null ? TtmlNode.ANONYMOUS_REGION_ID : charSequence).toString();
            if (str.length() > 150) {
                str = str.substring(0, 150);
            }
            charSequence = Emoji.replaceEmoji(str.replace('\n', ' '), Theme.dialogs_messagePaint.getFontMetricsInt(), AndroidUtilities.dp(17.0f), false);
        }
        int max = Math.max(AndroidUtilities.dp(12.0f), intrinsicWidth);
        try {
            this.messageLayout = new StaticLayout(TextUtils.ellipsize(charSequence, textPaint, (float) (max - AndroidUtilities.dp(12.0f)), TruncateAt.END), textPaint, max, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
        } catch (Throwable e2) {
            FileLog.e(e2);
        }
        float lineLeft;
        double ceil2;
        double ceil3;
        if (LocaleController.isRTL) {
            if (this.nameLayout != null && this.nameLayout.getLineCount() > 0) {
                lineLeft = this.nameLayout.getLineLeft(0);
                ceil2 = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (this.dialogMuted && !this.drawVerified) {
                    this.nameMuteLeft = (int) (((((double) this.nameLeft) + (((double) ceil) - ceil2)) - ((double) AndroidUtilities.dp(6.0f))) - ((double) Theme.dialogs_muteDrawable.getIntrinsicWidth()));
                } else if (this.drawVerified) {
                    this.nameMuteLeft = (int) (((((double) this.nameLeft) + (((double) ceil) - ceil2)) - ((double) AndroidUtilities.dp(6.0f))) - ((double) Theme.dialogs_verifiedDrawable.getIntrinsicWidth()));
                }
                if (lineLeft == BitmapDescriptorFactory.HUE_RED && ceil2 < ((double) ceil)) {
                    this.nameLeft = (int) (((double) this.nameLeft) + (((double) ceil) - ceil2));
                }
            }
            if (this.messageLayout != null && this.messageLayout.getLineCount() > 0 && this.messageLayout.getLineLeft(0) == BitmapDescriptorFactory.HUE_RED) {
                ceil3 = Math.ceil((double) this.messageLayout.getLineWidth(0));
                if (ceil3 < ((double) max)) {
                    this.messageLeft = (int) ((((double) max) - ceil3) + ((double) this.messageLeft));
                    return;
                }
                return;
            }
            return;
        }
        if (this.nameLayout != null && this.nameLayout.getLineCount() > 0) {
            lineLeft = this.nameLayout.getLineRight(0);
            if (lineLeft == ((float) ceil)) {
                ceil2 = Math.ceil((double) this.nameLayout.getLineWidth(0));
                if (ceil2 < ((double) ceil)) {
                    this.nameLeft = (int) (((double) this.nameLeft) - (((double) ceil) - ceil2));
                }
            }
            if (this.dialogMuted || this.drawVerified) {
                this.nameMuteLeft = (int) ((lineLeft + ((float) this.nameLeft)) + ((float) AndroidUtilities.dp(6.0f)));
            }
        }
        if (this.messageLayout != null && this.messageLayout.getLineCount() > 0 && this.messageLayout.getLineRight(0) == ((float) max)) {
            ceil3 = Math.ceil((double) this.messageLayout.getLineWidth(0));
            if (ceil3 < ((double) max)) {
                this.messageLeft = (int) (((double) this.messageLeft) - (((double) max) - ceil3));
            }
        }
    }

    public void checkCurrentDialogIndex() {
        if (this.index < getDialogsArray().size()) {
            TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getDialogsArray().get(this.index);
            DraftMessage draft = DraftQuery.getDraft(this.currentDialogId);
            MessageObject messageObject = (MessageObject) MessagesController.getInstance().dialogMessage.get(Long.valueOf(tLRPC$TL_dialog.id));
            if (this.currentDialogId != tLRPC$TL_dialog.id || ((this.message != null && this.message.getId() != tLRPC$TL_dialog.top_message) || ((messageObject != null && messageObject.messageOwner.edit_date != this.currentEditDate) || this.unreadCount != tLRPC$TL_dialog.unread_count || this.mentionCount != tLRPC$TL_dialog.unread_mentions_count || this.message != messageObject || ((this.message == null && messageObject != null) || draft != this.draftMessage || this.drawPin != tLRPC$TL_dialog.pinned)))) {
                this.currentDialogId = tLRPC$TL_dialog.id;
                update(0);
            }
        }
    }

    public long getDialogId() {
        return this.currentDialogId;
    }

    public boolean hasOverlappingRendering() {
        return false;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.avatarImage.onAttachedToWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.avatarImage.onDetachedFromWindow();
    }

    protected void onDraw(Canvas canvas) {
        if (this.currentDialogId != 0 || this.customDialog != null) {
            if (this.isSelected) {
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.dialogs_tabletSeletedPaint);
            }
            if (this.drawPin || this.drawPinBackground) {
                canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.dialogs_pinnedPaint);
            }
            if (this.drawNameLock) {
                setDrawableBounds(Theme.dialogs_lockDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_lockDrawable.draw(canvas);
            } else if (this.drawNameGroup) {
                setDrawableBounds(Theme.dialogs_groupDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_groupDrawable.draw(canvas);
            } else if (this.drawNameBroadcast) {
                setDrawableBounds(Theme.dialogs_broadcastDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_broadcastDrawable.draw(canvas);
            } else if (this.drawNameBot) {
                setDrawableBounds(Theme.dialogs_botDrawable, this.nameLockLeft, this.nameLockTop);
                Theme.dialogs_botDrawable.draw(canvas);
            }
            if (this.nameLayout != null) {
                canvas.save();
                canvas.translate((float) this.nameLeft, (float) AndroidUtilities.dp(13.0f));
                this.nameLayout.draw(canvas);
                canvas.restore();
            }
            if (this.timeLayout != null) {
                canvas.save();
                canvas.translate((float) this.timeLeft, (float) this.timeTop);
                this.timeLayout.draw(canvas);
                canvas.restore();
            }
            if (this.messageLayout != null) {
                canvas.save();
                canvas.translate((float) this.messageLeft, (float) this.messageTop);
                try {
                    this.messageLayout.draw(canvas);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                canvas.restore();
            }
            if (this.drawClock) {
                setDrawableBounds(Theme.dialogs_clockDrawable, this.checkDrawLeft, this.checkDrawTop);
                Theme.dialogs_clockDrawable.draw(canvas);
            } else if (this.drawCheck2) {
                if (this.drawCheck1) {
                    setDrawableBounds(Theme.dialogs_halfCheckDrawable, this.halfCheckDrawLeft, this.checkDrawTop);
                    Theme.dialogs_halfCheckDrawable.draw(canvas);
                    setDrawableBounds(Theme.dialogs_checkDrawable, this.checkDrawLeft, this.checkDrawTop);
                    Theme.dialogs_checkDrawable.draw(canvas);
                } else {
                    setDrawableBounds(Theme.dialogs_checkDrawable, this.checkDrawLeft, this.checkDrawTop);
                    Theme.dialogs_checkDrawable.draw(canvas);
                }
            }
            if (this.dialogMuted && !this.drawVerified) {
                setDrawableBounds(Theme.dialogs_muteDrawable, this.nameMuteLeft, AndroidUtilities.dp(16.5f));
                Theme.dialogs_muteDrawable.draw(canvas);
            } else if (this.drawVerified) {
                setDrawableBounds(Theme.dialogs_verifiedDrawable, this.nameMuteLeft, AndroidUtilities.dp(16.5f));
                setDrawableBounds(Theme.dialogs_verifiedCheckDrawable, this.nameMuteLeft, AndroidUtilities.dp(16.5f));
                Theme.dialogs_verifiedDrawable.draw(canvas);
                Theme.dialogs_verifiedCheckDrawable.draw(canvas);
            }
            if (this.drawError) {
                this.rect.set((float) this.errorLeft, (float) this.errorTop, (float) (this.errorLeft + AndroidUtilities.dp(23.0f)), (float) (this.errorTop + AndroidUtilities.dp(23.0f)));
                canvas.drawRoundRect(this.rect, AndroidUtilities.density * 11.5f, AndroidUtilities.density * 11.5f, Theme.dialogs_errorPaint);
                setDrawableBounds(Theme.dialogs_errorDrawable, this.errorLeft + AndroidUtilities.dp(5.5f), this.errorTop + AndroidUtilities.dp(5.0f));
                Theme.dialogs_errorDrawable.draw(canvas);
            } else if (this.drawCount || this.drawMention) {
                int dp;
                if (this.drawCount) {
                    dp = this.countLeft - AndroidUtilities.dp(5.5f);
                    this.rect.set((float) dp, (float) this.countTop, (float) ((dp + this.countWidth) + AndroidUtilities.dp(11.0f)), (float) (this.countTop + AndroidUtilities.dp(23.0f)));
                    canvas.drawRoundRect(this.rect, 11.5f * AndroidUtilities.density, 11.5f * AndroidUtilities.density, this.dialogMuted ? Theme.dialogs_countGrayPaint : Theme.dialogs_countPaint);
                    canvas.save();
                    canvas.translate((float) this.countLeft, (float) (this.countTop + AndroidUtilities.dp(4.0f)));
                    if (this.countLayout != null) {
                        this.countLayout.draw(canvas);
                    }
                    canvas.restore();
                }
                if (this.drawMention) {
                    dp = this.mentionLeft - AndroidUtilities.dp(5.5f);
                    this.rect.set((float) dp, (float) this.countTop, (float) ((dp + this.mentionWidth) + AndroidUtilities.dp(11.0f)), (float) (this.countTop + AndroidUtilities.dp(23.0f)));
                    canvas.drawRoundRect(this.rect, AndroidUtilities.density * 11.5f, AndroidUtilities.density * 11.5f, Theme.dialogs_countPaint);
                    setDrawableBounds(Theme.dialogs_mentionDrawable, this.mentionLeft - AndroidUtilities.dp(2.0f), this.countTop + AndroidUtilities.dp(3.2f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
                    Theme.dialogs_mentionDrawable.draw(canvas);
                }
            } else if (this.drawPin) {
                setDrawableBounds(Theme.dialogs_pinnedDrawable, this.pinLeft, this.pinTop);
                Theme.dialogs_pinnedDrawable.draw(canvas);
            }
            if (this.useSeparator) {
                if (LocaleController.isRTL) {
                    canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
                } else {
                    canvas.drawLine((float) AndroidUtilities.dp((float) AndroidUtilities.leftBaseline), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
                }
            }
            this.avatarImage.draw(canvas);
            SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("theme", 0);
            this.avatarLeftMargin = AndroidUtilities.dp((float) sharedPreferences.getInt("chatsAvatarMarginLeft", AndroidUtilities.isTablet() ? 13 : 9));
            if (this.drawStatus && !sharedPreferences.getBoolean("chatsHideStatusIndicator", false)) {
                setDrawableBounds(this.statusBG, this.avatarLeftMargin + AndroidUtilities.dp(LocaleController.isRTL ? (float) (PixelUtil.m14172a(getContext()) - 30) : 36.0f), AndroidUtilities.dp(46.0f), AndroidUtilities.dp(16.0f), AndroidUtilities.dp(16.0f));
                this.statusBG.draw(canvas);
            }
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.currentDialogId != 0 || this.customDialog != null) {
            if (this.checkBox != null) {
                int dp = LocaleController.isRTL ? (i3 - i) - AndroidUtilities.dp(42.0f) : AndroidUtilities.dp(42.0f);
                int dp2 = AndroidUtilities.dp(43.0f);
                this.checkBox.layout(dp, dp2, this.checkBox.getMeasuredWidth() + dp, this.checkBox.getMeasuredHeight() + dp2);
            }
            if (z) {
                buildLayout();
            }
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.checkBox != null) {
            this.checkBox.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(24.0f), 1073741824));
        }
        setMeasuredDimension(MeasureSpec.getSize(i), (this.useSeparator ? 1 : 0) + AndroidUtilities.dp(72.0f));
    }

    public void setChecked(boolean z, boolean z2) {
        if (this.checkBox != null) {
            this.checkBox.setChecked(z, z2);
        }
    }

    public void setDialog(long j, MessageObject messageObject, int i) {
        this.currentDialogId = j;
        this.message = messageObject;
        this.isDialogCell = false;
        this.lastMessageDate = i;
        this.currentEditDate = messageObject != null ? messageObject.messageOwner.edit_date : 0;
        this.unreadCount = 0;
        this.mentionCount = 0;
        boolean z = messageObject != null && messageObject.isUnread();
        this.lastUnreadState = z;
        if (this.message != null) {
            this.lastSendState = this.message.messageOwner.send_state;
        }
        update(0);
    }

    public void setDialog(TLRPC$TL_dialog tLRPC$TL_dialog, int i, int i2) {
        this.currentDialogId = tLRPC$TL_dialog.id;
        this.isDialogCell = true;
        this.index = i;
        this.dialogsType = i2;
        update(0);
    }

    public void setDialog(CustomDialog customDialog) {
        this.customDialog = customDialog;
        update(0);
    }

    public void setDialogSelected(boolean z) {
        if (this.isSelected != z) {
            invalidate();
        }
        this.isSelected = z;
    }

    public void update(int i) {
        if (this.customDialog != null) {
            this.lastMessageDate = this.customDialog.date;
            this.lastUnreadState = this.customDialog.unread_count != 0;
            this.unreadCount = this.customDialog.unread_count;
            this.drawPin = this.customDialog.pinned;
            this.dialogMuted = this.customDialog.muted;
            this.avatarDrawable.setInfo(this.customDialog.id, this.customDialog.name, null, false);
            this.avatarImage.setImage((TLObject) null, "50_50", this.avatarDrawable, null, 0);
        } else {
            TLRPC$TL_dialog tLRPC$TL_dialog;
            boolean z;
            int i2;
            int i3;
            TLObject tLObject;
            if (this.isDialogCell) {
                tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.currentDialogId));
                if (tLRPC$TL_dialog != null && i == 0) {
                    this.message = (MessageObject) MessagesController.getInstance().dialogMessage.get(Long.valueOf(tLRPC$TL_dialog.id));
                    z = this.message != null && this.message.isUnread();
                    this.lastUnreadState = z;
                    this.unreadCount = tLRPC$TL_dialog.unread_count;
                    this.mentionCount = tLRPC$TL_dialog.unread_mentions_count;
                    this.currentEditDate = this.message != null ? this.message.messageOwner.edit_date : 0;
                    this.lastMessageDate = tLRPC$TL_dialog.last_message_date;
                    this.drawPin = tLRPC$TL_dialog.pinned;
                    if (this.message != null) {
                        this.lastSendState = this.message.messageOwner.send_state;
                    }
                }
            } else {
                this.drawPin = false;
            }
            if (i != 0) {
                if (this.isDialogCell && (i & 64) != 0) {
                    CharSequence charSequence = (CharSequence) MessagesController.getInstance().printingStrings.get(Long.valueOf(this.currentDialogId));
                    if ((this.lastPrintString != null && charSequence == null) || ((this.lastPrintString == null && charSequence != null) || !(this.lastPrintString == null || charSequence == null || this.lastPrintString.equals(charSequence)))) {
                        i2 = 1;
                        if (i2 == 0 && (i & 2) != 0 && this.chat == null) {
                            i2 = 1;
                        }
                        if (i2 == 0 && (i & 1) != 0 && this.chat == null) {
                            i2 = 1;
                        }
                        if (i2 == 0 && (i & 8) != 0 && this.user == null) {
                            i2 = 1;
                        }
                        if (i2 == 0 && (i & 16) != 0 && this.user == null) {
                            i2 = 1;
                        }
                        if (i2 == 0 && (i & 256) != 0) {
                            if (this.message == null && this.lastUnreadState != this.message.isUnread()) {
                                this.lastUnreadState = this.message.isUnread();
                                i3 = 1;
                                this.lastSendState = this.message.messageOwner.send_state;
                                i3 = 1;
                                if (i3 == 0) {
                                    return;
                                }
                            } else if (this.isDialogCell) {
                                tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.currentDialogId));
                                if (!(tLRPC$TL_dialog == null || (this.unreadCount == tLRPC$TL_dialog.unread_count && this.mentionCount == tLRPC$TL_dialog.unread_mentions_count))) {
                                    this.unreadCount = tLRPC$TL_dialog.unread_count;
                                    this.mentionCount = tLRPC$TL_dialog.unread_mentions_count;
                                    i3 = 1;
                                    if (!(i3 != 0 || (i & 4096) == 0 || this.message == null || this.lastSendState == this.message.messageOwner.send_state)) {
                                        this.lastSendState = this.message.messageOwner.send_state;
                                        i3 = 1;
                                    }
                                    if (i3 == 0) {
                                        return;
                                    }
                                }
                            }
                        }
                        i3 = i2;
                        this.lastSendState = this.message.messageOwner.send_state;
                        i3 = 1;
                        if (i3 == 0) {
                            return;
                        }
                    }
                }
                z = false;
                i2 = 1;
                i2 = 1;
                i2 = 1;
                i2 = 1;
                if (this.message == null) {
                }
                if (this.isDialogCell) {
                    tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(this.currentDialogId));
                    this.unreadCount = tLRPC$TL_dialog.unread_count;
                    this.mentionCount = tLRPC$TL_dialog.unread_mentions_count;
                    i3 = 1;
                    this.lastSendState = this.message.messageOwner.send_state;
                    i3 = 1;
                    if (i3 == 0) {
                        return;
                    }
                }
                i3 = i2;
                this.lastSendState = this.message.messageOwner.send_state;
                i3 = 1;
                if (i3 == 0) {
                    return;
                }
            }
            boolean z2 = this.isDialogCell && MessagesController.getInstance().isDialogMuted(this.currentDialogId);
            this.dialogMuted = z2;
            this.user = null;
            this.chat = null;
            this.encryptedChat = null;
            i3 = (int) this.currentDialogId;
            i2 = (int) (this.currentDialogId >> 32);
            if (i3 == 0) {
                this.encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf(i2));
                if (this.encryptedChat != null) {
                    this.user = MessagesController.getInstance().getUser(Integer.valueOf(this.encryptedChat.user_id));
                }
            } else if (i2 == 1) {
                this.chat = MessagesController.getInstance().getChat(Integer.valueOf(i3));
            } else if (i3 < 0) {
                this.chat = MessagesController.getInstance().getChat(Integer.valueOf(-i3));
                if (!(this.isDialogCell || this.chat == null || this.chat.migrated_to == null)) {
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat.migrated_to.channel_id));
                    if (chat != null) {
                        this.chat = chat;
                    }
                }
            } else {
                this.user = MessagesController.getInstance().getUser(Integer.valueOf(i3));
            }
            if (this.user != null) {
                this.avatarDrawable.setInfo(this.user);
                setStatusColor();
                if (UserObject.isUserSelf(this.user)) {
                    this.avatarDrawable.setSavedMessages(1);
                    tLObject = null;
                } else {
                    if (this.user.photo != null) {
                        tLObject = this.user.photo.photo_small;
                    }
                    tLObject = null;
                }
            } else {
                if (this.chat != null) {
                    TLObject tLObject2 = this.chat.photo != null ? this.chat.photo.photo_small : null;
                    this.avatarDrawable.setInfo(this.chat);
                    tLObject = tLObject2;
                }
                tLObject = null;
            }
            this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable, null, 0);
        }
        if (getMeasuredWidth() == 0 && getMeasuredHeight() == 0) {
            requestLayout();
        } else {
            buildLayout();
        }
        invalidate();
    }
}
