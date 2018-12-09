package org.telegram.messenger;

import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.StaticLayout.Builder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.telegram.messenger.Emoji.EmojiSpan;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionScreenshotMessages;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_game;
import org.telegram.tgnet.TLRPC$TL_inputMessageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonBuy;
import org.telegram.tgnet.TLRPC$TL_keyboardButtonRow;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelMigrateFrom;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeletePhoto;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatEditPhoto;
import org.telegram.tgnet.TLRPC$TL_messageActionChatEditTitle;
import org.telegram.tgnet.TLRPC$TL_messageActionChatJoinedByLink;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_messageActionCreatedBroadcastList;
import org.telegram.tgnet.TLRPC$TL_messageActionCustomAction;
import org.telegram.tgnet.TLRPC$TL_messageActionEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionGameScore;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionLoginUnknownLocation;
import org.telegram.tgnet.TLRPC$TL_messageActionPaymentSent;
import org.telegram.tgnet.TLRPC$TL_messageActionPhoneCall;
import org.telegram.tgnet.TLRPC$TL_messageActionPinMessage;
import org.telegram.tgnet.TLRPC$TL_messageActionScreenshotTaken;
import org.telegram.tgnet.TLRPC$TL_messageActionTTLChange;
import org.telegram.tgnet.TLRPC$TL_messageActionUserJoined;
import org.telegram.tgnet.TLRPC$TL_messageActionUserUpdatedPhoto;
import org.telegram.tgnet.TLRPC$TL_messageEmpty;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_messageEntityBold;
import org.telegram.tgnet.TLRPC$TL_messageEntityBotCommand;
import org.telegram.tgnet.TLRPC$TL_messageEntityCode;
import org.telegram.tgnet.TLRPC$TL_messageEntityEmail;
import org.telegram.tgnet.TLRPC$TL_messageEntityHashtag;
import org.telegram.tgnet.TLRPC$TL_messageEntityItalic;
import org.telegram.tgnet.TLRPC$TL_messageEntityMention;
import org.telegram.tgnet.TLRPC$TL_messageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageEntityPre;
import org.telegram.tgnet.TLRPC$TL_messageEntityTextUrl;
import org.telegram.tgnet.TLRPC$TL_messageEntityUrl;
import org.telegram.tgnet.TLRPC$TL_messageForwarded_old;
import org.telegram.tgnet.TLRPC$TL_messageForwarded_old2;
import org.telegram.tgnet.TLRPC$TL_messageMediaContact;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageMediaGame;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeo;
import org.telegram.tgnet.TLRPC$TL_messageMediaGeoLive;
import org.telegram.tgnet.TLRPC$TL_messageMediaInvoice;
import org.telegram.tgnet.TLRPC$TL_messageMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messageMediaUnsupported;
import org.telegram.tgnet.TLRPC$TL_messageMediaVenue;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_message_old;
import org.telegram.tgnet.TLRPC$TL_message_old2;
import org.telegram.tgnet.TLRPC$TL_message_old3;
import org.telegram.tgnet.TLRPC$TL_message_old4;
import org.telegram.tgnet.TLRPC$TL_message_secret;
import org.telegram.tgnet.TLRPC$TL_pageBlockCollage;
import org.telegram.tgnet.TLRPC$TL_pageBlockPhoto;
import org.telegram.tgnet.TLRPC$TL_pageBlockSlideshow;
import org.telegram.tgnet.TLRPC$TL_pageBlockVideo;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonBusy;
import org.telegram.tgnet.TLRPC$TL_phoneCallDiscardReasonMissed;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC$TL_replyInlineMarkup;
import org.telegram.tgnet.TLRPC$TL_webDocument;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEvent;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangeAbout;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangePhoto;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangeStickerSet;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangeTitle;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionChangeUsername;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionDeleteMessage;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionEditMessage;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionParticipantInvite;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionParticipantJoin;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionParticipantLeave;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionParticipantToggleAdmin;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionParticipantToggleBan;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionToggleInvites;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionTogglePreHistoryHidden;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionToggleSignatures;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventActionUpdatePinned;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.TL_chatPhotoEmpty;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanBotCommand;
import org.telegram.ui.Components.URLSpanMono;
import org.telegram.ui.Components.URLSpanNoUnderline;
import org.telegram.ui.Components.URLSpanNoUnderlineBold;
import org.telegram.ui.Components.URLSpanReplacement;
import org.telegram.ui.Components.URLSpanUserMention;

public class MessageObject {
    private static final int LINES_PER_BLOCK = 10;
    public static final int MESSAGE_SEND_STATE_SENDING = 1;
    public static final int MESSAGE_SEND_STATE_SEND_ERROR = 2;
    public static final int MESSAGE_SEND_STATE_SENT = 0;
    public static final int POSITION_FLAG_BOTTOM = 8;
    public static final int POSITION_FLAG_LEFT = 1;
    public static final int POSITION_FLAG_RIGHT = 2;
    public static final int POSITION_FLAG_TOP = 4;
    public static Pattern urlPattern;
    public boolean attachPathExists;
    public float audioProgress;
    public int audioProgressSec;
    public StringBuilder botButtonsLayout;
    public CharSequence caption;
    public CharSequence captionBU;
    public int contentType;
    public TL_channelAdminLogEvent currentEvent;
    public String customReplyName;
    public String dateKey;
    public boolean deleted;
    public long eventId;
    public boolean forceUpdate;
    private int generatedWithMinSize;
    public float gifState;
    public boolean hasRtl;
    public boolean isDateObject;
    private int isRoundVideoCached;
    public int lastLineWidth;
    private boolean layoutCreated;
    public CharSequence linkDescription;
    public long localGroupId;
    public boolean mediaExists;
    public Message messageOwner;
    public String messageOwnerMessageBU;
    public CharSequence messageText;
    public CharSequence messageTextBU;
    public String monthKey;
    public ArrayList<PhotoSize> photoThumbs;
    public ArrayList<PhotoSize> photoThumbs2;
    public MessageObject replyMessageObject;
    public boolean resendAsIs;
    public int textHeight;
    public ArrayList<TextLayoutBlock> textLayoutBlocks;
    public int textWidth;
    public float textXOffset;
    public int type;
    public boolean useCustomPhoto;
    public VideoEditedInfo videoEditedInfo;
    public boolean viewsReloaded;
    public int wantedBotKeyboardWidth;

    public static class GroupedMessagePosition {
        public float aspectRatio;
        public boolean edge;
        public int flags;
        public boolean last;
        public int leftSpanOffset;
        public byte maxX;
        public byte maxY;
        public byte minX;
        public byte minY;
        public float ph;
        public int pw;
        public float[] siblingHeights;
        public int spanSize;

        public void set(int i, int i2, int i3, int i4, int i5, float f, int i6) {
            this.minX = (byte) i;
            this.maxX = (byte) i2;
            this.minY = (byte) i3;
            this.maxY = (byte) i4;
            this.pw = i5;
            this.spanSize = i5;
            this.ph = f;
            this.flags = (byte) i6;
        }
    }

    public static class GroupedMessages {
        private int firstSpanAdditionalSize = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
        public long groupId;
        public boolean hasSibling;
        private int maxSizeWidth = 800;
        public ArrayList<MessageObject> messages = new ArrayList();
        public ArrayList<GroupedMessagePosition> posArray = new ArrayList();
        public HashMap<MessageObject, GroupedMessagePosition> positions = new HashMap();

        private class MessageGroupedLayoutAttempt {
            public float[] heights;
            public int[] lineCounts;

            public MessageGroupedLayoutAttempt(int i, int i2, float f, float f2) {
                this.lineCounts = new int[]{i, i2};
                this.heights = new float[]{f, f2};
            }

            public MessageGroupedLayoutAttempt(int i, int i2, int i3, float f, float f2, float f3) {
                this.lineCounts = new int[]{i, i2, i3};
                this.heights = new float[]{f, f2, f3};
            }

            public MessageGroupedLayoutAttempt(int i, int i2, int i3, int i4, float f, float f2, float f3, float f4) {
                this.lineCounts = new int[]{i, i2, i3, i4};
                this.heights = new float[]{f, f2, f3, f4};
            }
        }

        private float multiHeight(float[] fArr, int i, int i2) {
            float f = BitmapDescriptorFactory.HUE_RED;
            while (i < i2) {
                f += fArr[i];
                i++;
            }
            return ((float) this.maxSizeWidth) / f;
        }

        public void calculate() {
            this.posArray.clear();
            this.positions.clear();
            int size = this.messages.size();
            if (size > 1) {
                GroupedMessagePosition groupedMessagePosition;
                StringBuilder stringBuilder = new StringBuilder();
                boolean z = false;
                byte b = (byte) 0;
                Object obj = null;
                Object obj2 = null;
                this.hasSibling = false;
                int i = 0;
                float f = 1.0f;
                while (i < size) {
                    boolean isOutOwner;
                    MessageObject messageObject = (MessageObject) this.messages.get(i);
                    if (i == 0) {
                        isOutOwner = messageObject.isOutOwner();
                        obj2 = (isOutOwner || ((messageObject.messageOwner.fwd_from == null || messageObject.messageOwner.fwd_from.saved_from_peer == null) && (messageObject.messageOwner.from_id <= 0 || (messageObject.messageOwner.to_id.channel_id == 0 && messageObject.messageOwner.to_id.chat_id == 0 && !(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) && !(messageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice))))) ? null : 1;
                    } else {
                        isOutOwner = z;
                    }
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(messageObject.photoThumbs, AndroidUtilities.getPhotoSize());
                    GroupedMessagePosition groupedMessagePosition2 = new GroupedMessagePosition();
                    groupedMessagePosition2.last = i == size + -1;
                    groupedMessagePosition2.aspectRatio = closestPhotoSizeWithSize == null ? 1.0f : ((float) closestPhotoSizeWithSize.f10147w) / ((float) closestPhotoSizeWithSize.f10146h);
                    if (groupedMessagePosition2.aspectRatio > 1.2f) {
                        stringBuilder.append("w");
                    } else if (groupedMessagePosition2.aspectRatio < 0.8f) {
                        stringBuilder.append("n");
                    } else {
                        stringBuilder.append("q");
                    }
                    f += groupedMessagePosition2.aspectRatio;
                    Object obj3 = groupedMessagePosition2.aspectRatio > 2.0f ? 1 : obj;
                    this.positions.put(messageObject, groupedMessagePosition2);
                    this.posArray.add(groupedMessagePosition2);
                    i++;
                    obj = obj3;
                    z = isOutOwner;
                }
                if (obj2 != null) {
                    this.maxSizeWidth -= 50;
                    this.firstSpanAdditionalSize += 50;
                }
                int dp = AndroidUtilities.dp(120.0f);
                int dp2 = (int) (((float) AndroidUtilities.dp(120.0f)) / (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) / ((float) this.maxSizeWidth)));
                int dp3 = (int) (((float) AndroidUtilities.dp(40.0f)) / (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) / ((float) this.maxSizeWidth)));
                float f2 = ((float) this.maxSizeWidth) / 814.0f;
                float f3 = f / ((float) size);
                int i2;
                float f4;
                float f5;
                if (obj != null || (size != 2 && size != 3 && size != 4)) {
                    int length;
                    float[] fArr = new float[this.posArray.size()];
                    for (i2 = 0; i2 < size; i2++) {
                        if (f3 > 1.1f) {
                            fArr[i2] = Math.max(1.0f, ((GroupedMessagePosition) this.posArray.get(i2)).aspectRatio);
                        } else {
                            fArr[i2] = Math.min(1.0f, ((GroupedMessagePosition) this.posArray.get(i2)).aspectRatio);
                        }
                        fArr[i2] = Math.max(0.66667f, Math.min(1.7f, fArr[i2]));
                    }
                    ArrayList arrayList = new ArrayList();
                    for (dp = 1; dp < fArr.length; dp++) {
                        dp3 = fArr.length - dp;
                        if (dp <= 3 && dp3 <= 3) {
                            arrayList.add(new MessageGroupedLayoutAttempt(dp, dp3, multiHeight(fArr, 0, dp), multiHeight(fArr, dp, fArr.length)));
                        }
                    }
                    for (dp = 1; dp < fArr.length - 1; dp++) {
                        for (dp3 = 1; dp3 < fArr.length - dp; dp3++) {
                            length = (fArr.length - dp) - dp3;
                            if (dp <= 3) {
                                if (dp3 <= (f3 < 0.85f ? 4 : 3) && length <= 3) {
                                    arrayList.add(new MessageGroupedLayoutAttempt(dp, dp3, length, multiHeight(fArr, 0, dp), multiHeight(fArr, dp, dp + dp3), multiHeight(fArr, dp + dp3, fArr.length)));
                                }
                            }
                        }
                    }
                    for (dp = 1; dp < fArr.length - 2; dp++) {
                        dp3 = 1;
                        while (dp3 < fArr.length - dp) {
                            length = 1;
                            while (length < (fArr.length - dp) - dp3) {
                                i = ((fArr.length - dp) - dp3) - length;
                                if (dp <= 3 && dp3 <= 3 && length <= 3 && i <= 3) {
                                    arrayList.add(new MessageGroupedLayoutAttempt(dp, dp3, length, i, multiHeight(fArr, 0, dp), multiHeight(fArr, dp, dp + dp3), multiHeight(fArr, dp + dp3, (dp + dp3) + length), multiHeight(fArr, (dp + dp3) + length, fArr.length)));
                                }
                                length++;
                            }
                            dp3++;
                        }
                    }
                    MessageGroupedLayoutAttempt messageGroupedLayoutAttempt = null;
                    float f6 = BitmapDescriptorFactory.HUE_RED;
                    f = (float) ((this.maxSizeWidth / 3) * 4);
                    dp3 = 0;
                    while (dp3 < arrayList.size()) {
                        MessageGroupedLayoutAttempt messageGroupedLayoutAttempt2;
                        MessageGroupedLayoutAttempt messageGroupedLayoutAttempt3 = (MessageGroupedLayoutAttempt) arrayList.get(dp3);
                        f2 = BitmapDescriptorFactory.HUE_RED;
                        float f7 = Float.MAX_VALUE;
                        for (dp = 0; dp < messageGroupedLayoutAttempt3.heights.length; dp++) {
                            f2 += messageGroupedLayoutAttempt3.heights[dp];
                            if (messageGroupedLayoutAttempt3.heights[dp] < f7) {
                                f7 = messageGroupedLayoutAttempt3.heights[dp];
                            }
                        }
                        float abs = Math.abs(f2 - f);
                        if (messageGroupedLayoutAttempt3.lineCounts.length > 1 && (messageGroupedLayoutAttempt3.lineCounts[0] > messageGroupedLayoutAttempt3.lineCounts[1] || ((messageGroupedLayoutAttempt3.lineCounts.length > 2 && messageGroupedLayoutAttempt3.lineCounts[1] > messageGroupedLayoutAttempt3.lineCounts[2]) || (messageGroupedLayoutAttempt3.lineCounts.length > 3 && messageGroupedLayoutAttempt3.lineCounts[2] > messageGroupedLayoutAttempt3.lineCounts[3])))) {
                            abs *= 1.2f;
                        }
                        if (f7 < ((float) dp2)) {
                            abs *= 1.5f;
                        }
                        if (messageGroupedLayoutAttempt == null || abs < f6) {
                            messageGroupedLayoutAttempt2 = messageGroupedLayoutAttempt3;
                            f4 = abs;
                        } else {
                            f4 = f6;
                            messageGroupedLayoutAttempt2 = messageGroupedLayoutAttempt;
                        }
                        dp3++;
                        messageGroupedLayoutAttempt = messageGroupedLayoutAttempt2;
                        f6 = f4;
                    }
                    if (messageGroupedLayoutAttempt != null) {
                        dp3 = 0;
                        f3 = BitmapDescriptorFactory.HUE_RED;
                        int i3 = 0;
                        while (dp3 < messageGroupedLayoutAttempt.lineCounts.length) {
                            int i4 = messageGroupedLayoutAttempt.lineCounts[dp3];
                            f5 = messageGroupedLayoutAttempt.heights[dp3];
                            dp = this.maxSizeWidth;
                            GroupedMessagePosition groupedMessagePosition3 = null;
                            b = Math.max(b, i4 - 1);
                            i2 = 0;
                            dp2 = i3;
                            i3 = dp;
                            while (i2 < i4) {
                                int i5;
                                GroupedMessagePosition groupedMessagePosition4;
                                i = (int) (fArr[dp2] * f5);
                                int i6 = i3 - i;
                                groupedMessagePosition = (GroupedMessagePosition) this.posArray.get(dp2);
                                dp = 0;
                                if (dp3 == 0) {
                                    dp = 4;
                                }
                                if (dp3 == messageGroupedLayoutAttempt.lineCounts.length - 1) {
                                    dp |= 8;
                                }
                                if (i2 == 0) {
                                    i5 = dp | 1;
                                    groupedMessagePosition4 = z ? groupedMessagePosition : groupedMessagePosition3;
                                } else {
                                    i5 = dp;
                                    groupedMessagePosition4 = groupedMessagePosition3;
                                }
                                if (i2 == i4 - 1) {
                                    i5 |= 2;
                                    if (!z) {
                                        groupedMessagePosition3 = groupedMessagePosition;
                                        groupedMessagePosition.set(i2, i2, dp3, dp3, i, f5 / 814.0f, i5);
                                        dp2++;
                                        i2++;
                                        i3 = i6;
                                    }
                                }
                                groupedMessagePosition3 = groupedMessagePosition4;
                                groupedMessagePosition.set(i2, i2, dp3, dp3, i, f5 / 814.0f, i5);
                                dp2++;
                                i2++;
                                i3 = i6;
                            }
                            groupedMessagePosition3.pw += i3;
                            groupedMessagePosition3.spanSize = i3 + groupedMessagePosition3.spanSize;
                            dp3++;
                            f3 += f5;
                            i3 = dp2;
                        }
                    } else {
                        return;
                    }
                } else if (size == 2) {
                    groupedMessagePosition = (GroupedMessagePosition) this.posArray.get(0);
                    GroupedMessagePosition groupedMessagePosition5 = (GroupedMessagePosition) this.posArray.get(1);
                    String stringBuilder2 = stringBuilder.toString();
                    if (stringBuilder2.equals("ww") && ((double) f3) > ((double) f2) * 1.4d && ((double) (groupedMessagePosition.aspectRatio - groupedMessagePosition5.aspectRatio)) < 0.2d) {
                        f = ((float) Math.round(Math.min(((float) this.maxSizeWidth) / groupedMessagePosition.aspectRatio, Math.min(((float) this.maxSizeWidth) / groupedMessagePosition5.aspectRatio, 814.0f / 2.0f)))) / 814.0f;
                        groupedMessagePosition.set(0, 0, 0, 0, this.maxSizeWidth, f, 7);
                        groupedMessagePosition5.set(0, 0, 1, 1, this.maxSizeWidth, f, 11);
                        r2 = (byte) 0;
                    } else if (stringBuilder2.equals("ww") || stringBuilder2.equals("qq")) {
                        i = this.maxSizeWidth / 2;
                        f = ((float) Math.round(Math.min(((float) i) / groupedMessagePosition.aspectRatio, Math.min(((float) i) / groupedMessagePosition5.aspectRatio, 814.0f)))) / 814.0f;
                        groupedMessagePosition.set(0, 0, 0, 0, i, f, 13);
                        groupedMessagePosition5.set(1, 1, 0, 0, i, f, 14);
                        r2 = (byte) 1;
                    } else {
                        i2 = (int) Math.max(0.4f * ((float) this.maxSizeWidth), (float) Math.round((((float) this.maxSizeWidth) / groupedMessagePosition.aspectRatio) / ((1.0f / groupedMessagePosition.aspectRatio) + (1.0f / groupedMessagePosition5.aspectRatio))));
                        i = this.maxSizeWidth - i2;
                        if (i < dp2) {
                            i = dp2;
                            r11 = i2 - (dp2 - i);
                        } else {
                            r11 = i2;
                        }
                        f = Math.min(814.0f, (float) Math.round(Math.min(((float) i) / groupedMessagePosition.aspectRatio, ((float) r11) / groupedMessagePosition5.aspectRatio))) / 814.0f;
                        groupedMessagePosition.set(0, 0, 0, 0, i, f, 13);
                        groupedMessagePosition5.set(1, 1, 0, 0, r11, f, 14);
                        r2 = (byte) 1;
                    }
                    b = r2;
                } else if (size == 3) {
                    groupedMessagePosition = (GroupedMessagePosition) this.posArray.get(0);
                    GroupedMessagePosition groupedMessagePosition6 = (GroupedMessagePosition) this.posArray.get(1);
                    GroupedMessagePosition groupedMessagePosition7 = (GroupedMessagePosition) this.posArray.get(2);
                    if (stringBuilder.charAt(0) == 'n') {
                        f5 = Math.min(0.5f * 814.0f, (float) Math.round((groupedMessagePosition6.aspectRatio * ((float) this.maxSizeWidth)) / (groupedMessagePosition7.aspectRatio + groupedMessagePosition6.aspectRatio)));
                        float f8 = 814.0f - f5;
                        r13 = (int) Math.max((float) dp2, Math.min(((float) this.maxSizeWidth) * 0.5f, (float) Math.round(Math.min(groupedMessagePosition7.aspectRatio * f5, groupedMessagePosition6.aspectRatio * f8))));
                        i = Math.round(Math.min((groupedMessagePosition.aspectRatio * 814.0f) + ((float) dp3), (float) (this.maxSizeWidth - r13)));
                        groupedMessagePosition.set(0, 0, 0, 1, i, 1.0f, 13);
                        groupedMessagePosition6.set(1, 1, 0, 0, r13, f8 / 814.0f, 6);
                        groupedMessagePosition7.set(0, 1, 1, 1, r13, f5 / 814.0f, 10);
                        groupedMessagePosition7.spanSize = this.maxSizeWidth;
                        groupedMessagePosition.siblingHeights = new float[]{f5 / 814.0f, f8 / 814.0f};
                        if (z) {
                            groupedMessagePosition.spanSize = this.maxSizeWidth - r13;
                        } else {
                            groupedMessagePosition6.spanSize = this.maxSizeWidth - i;
                            groupedMessagePosition7.leftSpanOffset = i;
                        }
                        this.hasSibling = true;
                        r2 = (byte) 1;
                    } else {
                        f = ((float) Math.round(Math.min(((float) this.maxSizeWidth) / groupedMessagePosition.aspectRatio, 0.66f * 814.0f))) / 814.0f;
                        groupedMessagePosition.set(0, 1, 0, 0, this.maxSizeWidth, f, 7);
                        i = this.maxSizeWidth / 2;
                        f = Math.min(814.0f - f, (float) Math.round(Math.min(((float) i) / groupedMessagePosition6.aspectRatio, ((float) i) / groupedMessagePosition7.aspectRatio))) / 814.0f;
                        groupedMessagePosition6.set(0, 0, 1, 1, i, f, 9);
                        groupedMessagePosition7.set(1, 1, 1, 1, i, f, 10);
                        r2 = (byte) 1;
                    }
                    b = r2;
                } else if (size == 4) {
                    groupedMessagePosition = (GroupedMessagePosition) this.posArray.get(0);
                    GroupedMessagePosition groupedMessagePosition8 = (GroupedMessagePosition) this.posArray.get(1);
                    GroupedMessagePosition groupedMessagePosition9 = (GroupedMessagePosition) this.posArray.get(2);
                    GroupedMessagePosition groupedMessagePosition10 = (GroupedMessagePosition) this.posArray.get(3);
                    if (stringBuilder.charAt(0) == 'w') {
                        f = ((float) Math.round(Math.min(((float) this.maxSizeWidth) / groupedMessagePosition.aspectRatio, 0.66f * 814.0f))) / 814.0f;
                        groupedMessagePosition.set(0, 2, 0, 0, this.maxSizeWidth, f, 7);
                        f4 = (float) Math.round(((float) this.maxSizeWidth) / ((groupedMessagePosition8.aspectRatio + groupedMessagePosition9.aspectRatio) + groupedMessagePosition10.aspectRatio));
                        i = (int) Math.max((float) dp2, Math.min(((float) this.maxSizeWidth) * 0.4f, groupedMessagePosition8.aspectRatio * f4));
                        int max = (int) Math.max(Math.max((float) dp2, ((float) this.maxSizeWidth) * 0.33f), groupedMessagePosition10.aspectRatio * f4);
                        r11 = (this.maxSizeWidth - i) - max;
                        f = Math.min(814.0f - f, f4) / 814.0f;
                        groupedMessagePosition8.set(0, 0, 1, 1, i, f, 9);
                        groupedMessagePosition9.set(1, 1, 1, 1, r11, f, 8);
                        groupedMessagePosition10.set(2, 2, 1, 1, max, f, 10);
                        r2 = (byte) 2;
                    } else {
                        r13 = Math.max(dp2, Math.round(814.0f / ((1.0f / ((GroupedMessagePosition) this.posArray.get(3)).aspectRatio) + ((1.0f / groupedMessagePosition9.aspectRatio) + (1.0f / groupedMessagePosition8.aspectRatio)))));
                        float min = Math.min(0.33f, Math.max((float) dp, ((float) r13) / groupedMessagePosition8.aspectRatio) / 814.0f);
                        float min2 = Math.min(0.33f, Math.max((float) dp, ((float) r13) / groupedMessagePosition9.aspectRatio) / 814.0f);
                        float f9 = (1.0f - min) - min2;
                        i = Math.round(Math.min((groupedMessagePosition.aspectRatio * 814.0f) + ((float) dp3), (float) (this.maxSizeWidth - r13)));
                        groupedMessagePosition.set(0, 0, 0, 2, i, (min + min2) + f9, 13);
                        groupedMessagePosition8.set(1, 1, 0, 0, r13, min, 6);
                        groupedMessagePosition9.set(0, 1, 1, 1, r13, min2, 2);
                        groupedMessagePosition9.spanSize = this.maxSizeWidth;
                        groupedMessagePosition10.set(0, 1, 2, 2, r13, f9, 10);
                        groupedMessagePosition10.spanSize = this.maxSizeWidth;
                        if (z) {
                            groupedMessagePosition.spanSize = this.maxSizeWidth - r13;
                        } else {
                            groupedMessagePosition8.spanSize = this.maxSizeWidth - i;
                            groupedMessagePosition9.leftSpanOffset = i;
                            groupedMessagePosition10.leftSpanOffset = i;
                        }
                        groupedMessagePosition.siblingHeights = new float[]{min, min2, f9};
                        this.hasSibling = true;
                        r2 = (byte) 1;
                    }
                    b = r2;
                }
                for (dp = 0; dp < size; dp++) {
                    groupedMessagePosition = (GroupedMessagePosition) this.posArray.get(dp);
                    if (z) {
                        if (groupedMessagePosition.minX == (byte) 0) {
                            groupedMessagePosition.spanSize += this.firstSpanAdditionalSize;
                        }
                        if ((groupedMessagePosition.flags & 2) != 0) {
                            groupedMessagePosition.edge = true;
                        }
                    } else {
                        if (groupedMessagePosition.maxX == b || (groupedMessagePosition.flags & 2) != 0) {
                            groupedMessagePosition.spanSize += this.firstSpanAdditionalSize;
                        }
                        if ((groupedMessagePosition.flags & 1) != 0) {
                            groupedMessagePosition.edge = true;
                        }
                    }
                    MessageObject messageObject2 = (MessageObject) this.messages.get(dp);
                    if (!z && messageObject2.needDrawAvatar()) {
                        if (groupedMessagePosition.edge) {
                            if (groupedMessagePosition.spanSize != 1000) {
                                groupedMessagePosition.spanSize += 108;
                            }
                            groupedMessagePosition.pw += 108;
                        } else if ((groupedMessagePosition.flags & 2) != 0) {
                            if (groupedMessagePosition.spanSize != 1000) {
                                groupedMessagePosition.spanSize -= 108;
                            } else if (groupedMessagePosition.leftSpanOffset != 0) {
                                groupedMessagePosition.leftSpanOffset += 108;
                            }
                        }
                    }
                }
            }
        }
    }

    public static class TextLayoutBlock {
        public int charactersEnd;
        public int charactersOffset;
        public byte directionFlags;
        public int height;
        public int heightByOffset;
        public StaticLayout textLayout;
        public float textYOffset;

        public boolean isRtl() {
            return (this.directionFlags & 1) != 0 && (this.directionFlags & 2) == 0;
        }
    }

    public MessageObject(Message message, AbstractMap<Integer, User> abstractMap, AbstractMap<Integer, Chat> abstractMap2, boolean z) {
        this(message, (AbstractMap) abstractMap, (AbstractMap) abstractMap2, z, 0);
    }

    public MessageObject(Message message, AbstractMap<Integer, User> abstractMap, AbstractMap<Integer, Chat> abstractMap2, boolean z, long j) {
        User user;
        int i;
        int intValue;
        int dialogId;
        this.type = 1000;
        Theme.createChatResources(null, true);
        this.messageOwner = message;
        this.eventId = j;
        if (message.replyMessage != null) {
            this.replyMessageObject = new MessageObject(message.replyMessage, abstractMap, abstractMap2, false);
        }
        User user2 = null;
        if (message.from_id > 0) {
            if (abstractMap != null) {
                user2 = (User) abstractMap.get(Integer.valueOf(message.from_id));
            }
            user = user2 == null ? MessagesController.getInstance().getUser(Integer.valueOf(message.from_id)) : user2;
        } else {
            user = null;
        }
        String firstName;
        if (message instanceof TLRPC$TL_messageService) {
            if (message.action != null) {
                if (message.action instanceof TLRPC$TL_messageActionCustomAction) {
                    this.messageText = message.action.message;
                } else if (message.action instanceof TLRPC$TL_messageActionChatCreate) {
                    if (isOut()) {
                        this.messageText = LocaleController.getString("ActionYouCreateGroup", R.string.ActionYouCreateGroup);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionCreateGroup", R.string.ActionCreateGroup), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    if (message.action.user_id != message.from_id) {
                        r0 = null;
                        if (abstractMap != null) {
                            r0 = (User) abstractMap.get(Integer.valueOf(message.action.user_id));
                        }
                        if (r0 == null) {
                            r0 = MessagesController.getInstance().getUser(Integer.valueOf(message.action.user_id));
                        }
                        if (isOut()) {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionYouKickUser", R.string.ActionYouKickUser), "un2", r0);
                        } else if (message.action.user_id == UserConfig.getClientUserId()) {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionKickUserYou", R.string.ActionKickUserYou), "un1", user);
                        } else {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionKickUser", R.string.ActionKickUser), "un2", r0);
                            this.messageText = replaceWithLink(this.messageText, "un1", user);
                        }
                    } else if (isOut()) {
                        this.messageText = LocaleController.getString("ActionYouLeftUser", R.string.ActionYouLeftUser);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionLeftUser", R.string.ActionLeftUser), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatAddUser) {
                    i = this.messageOwner.action.user_id;
                    intValue = (i == 0 && this.messageOwner.action.users.size() == 1) ? ((Integer) this.messageOwner.action.users.get(0)).intValue() : i;
                    if (intValue != 0) {
                        r0 = null;
                        if (abstractMap != null) {
                            r0 = (User) abstractMap.get(Integer.valueOf(intValue));
                        }
                        if (r0 == null) {
                            r0 = MessagesController.getInstance().getUser(Integer.valueOf(intValue));
                        }
                        if (intValue == message.from_id) {
                            if (message.to_id.channel_id != 0 && !isMegagroup()) {
                                this.messageText = LocaleController.getString("ChannelJoined", R.string.ChannelJoined);
                            } else if (message.to_id.channel_id == 0 || !isMegagroup()) {
                                if (isOut()) {
                                    this.messageText = LocaleController.getString("ActionAddUserSelfYou", R.string.ActionAddUserSelfYou);
                                } else {
                                    this.messageText = replaceWithLink(LocaleController.getString("ActionAddUserSelf", R.string.ActionAddUserSelf), "un1", user);
                                }
                            } else if (intValue == UserConfig.getClientUserId()) {
                                this.messageText = LocaleController.getString("ChannelMegaJoined", R.string.ChannelMegaJoined);
                            } else {
                                this.messageText = replaceWithLink(LocaleController.getString("ActionAddUserSelfMega", R.string.ActionAddUserSelfMega), "un1", user);
                            }
                        } else if (isOut()) {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionYouAddUser", R.string.ActionYouAddUser), "un2", r0);
                        } else if (intValue != UserConfig.getClientUserId()) {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionAddUser", R.string.ActionAddUser), "un2", r0);
                            this.messageText = replaceWithLink(this.messageText, "un1", user);
                        } else if (message.to_id.channel_id == 0) {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionAddUserYou", R.string.ActionAddUserYou), "un1", user);
                        } else if (isMegagroup()) {
                            this.messageText = replaceWithLink(LocaleController.getString("MegaAddedBy", R.string.MegaAddedBy), "un1", user);
                        } else {
                            this.messageText = replaceWithLink(LocaleController.getString("ChannelAddedBy", R.string.ChannelAddedBy), "un1", user);
                        }
                    } else if (isOut()) {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionYouAddUser", R.string.ActionYouAddUser), "un2", message.action.users, abstractMap);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionAddUser", R.string.ActionAddUser), "un2", message.action.users, abstractMap);
                        this.messageText = replaceWithLink(this.messageText, "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatJoinedByLink) {
                    if (isOut()) {
                        this.messageText = LocaleController.getString("ActionInviteYou", R.string.ActionInviteYou);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionInviteUser", R.string.ActionInviteUser), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatEditPhoto) {
                    if (message.to_id.channel_id != 0 && !isMegagroup()) {
                        this.messageText = LocaleController.getString("ActionChannelChangedPhoto", R.string.ActionChannelChangedPhoto);
                    } else if (isOut()) {
                        this.messageText = LocaleController.getString("ActionYouChangedPhoto", R.string.ActionYouChangedPhoto);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionChangedPhoto", R.string.ActionChangedPhoto), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatEditTitle) {
                    if (message.to_id.channel_id != 0 && !isMegagroup()) {
                        this.messageText = LocaleController.getString("ActionChannelChangedTitle", R.string.ActionChannelChangedTitle).replace("un2", message.action.title);
                    } else if (isOut()) {
                        this.messageText = LocaleController.getString("ActionYouChangedTitle", R.string.ActionYouChangedTitle).replace("un2", message.action.title);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionChangedTitle", R.string.ActionChangedTitle).replace("un2", message.action.title), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatDeletePhoto) {
                    if (message.to_id.channel_id != 0 && !isMegagroup()) {
                        this.messageText = LocaleController.getString("ActionChannelRemovedPhoto", R.string.ActionChannelRemovedPhoto);
                    } else if (isOut()) {
                        this.messageText = LocaleController.getString("ActionYouRemovedPhoto", R.string.ActionYouRemovedPhoto);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionRemovedPhoto", R.string.ActionRemovedPhoto), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionTTLChange) {
                    if (message.action.ttl != 0) {
                        if (isOut()) {
                            this.messageText = LocaleController.formatString("MessageLifetimeChangedOutgoing", R.string.MessageLifetimeChangedOutgoing, LocaleController.formatTTLString(message.action.ttl));
                        } else {
                            this.messageText = LocaleController.formatString("MessageLifetimeChanged", R.string.MessageLifetimeChanged, UserObject.getFirstName(user), LocaleController.formatTTLString(message.action.ttl));
                        }
                    } else if (isOut()) {
                        this.messageText = LocaleController.getString("MessageLifetimeYouRemoved", R.string.MessageLifetimeYouRemoved);
                    } else {
                        this.messageText = LocaleController.formatString("MessageLifetimeRemoved", R.string.MessageLifetimeRemoved, UserObject.getFirstName(user));
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionLoginUnknownLocation) {
                    Object formatString;
                    long j2 = ((long) message.date) * 1000;
                    if (LocaleController.getInstance().formatterDay == null || LocaleController.getInstance().formatterYear == null) {
                        r2 = TtmlNode.ANONYMOUS_REGION_ID + message.date;
                    } else {
                        formatString = LocaleController.formatString("formatDateAtTime", R.string.formatDateAtTime, LocaleController.getInstance().formatterYear.format(j2), LocaleController.getInstance().formatterDay.format(j2));
                    }
                    user2 = UserConfig.getCurrentUser();
                    if (user2 == null) {
                        if (abstractMap != null) {
                            user2 = (User) abstractMap.get(Integer.valueOf(this.messageOwner.to_id.user_id));
                        }
                        if (user2 == null) {
                            user2 = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.to_id.user_id));
                        }
                    }
                    firstName = user2 != null ? UserObject.getFirstName(user2) : TtmlNode.ANONYMOUS_REGION_ID;
                    this.messageText = LocaleController.formatString("NotificationUnrecognizedDevice", R.string.NotificationUnrecognizedDevice, firstName, formatString, message.action.title, message.action.address);
                } else if (message.action instanceof TLRPC$TL_messageActionUserJoined) {
                    this.messageText = LocaleController.formatString("NotificationContactJoined", R.string.NotificationContactJoined, UserObject.getUserName(user));
                } else if (message.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto) {
                    this.messageText = LocaleController.formatString("NotificationContactNewPhoto", R.string.NotificationContactNewPhoto, UserObject.getUserName(user));
                } else if (message.action instanceof TLRPC$TL_messageEncryptedAction) {
                    if (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) {
                        if (isOut()) {
                            this.messageText = LocaleController.formatString("ActionTakeScreenshootYou", R.string.ActionTakeScreenshootYou, new Object[0]);
                        } else {
                            this.messageText = replaceWithLink(LocaleController.getString("ActionTakeScreenshoot", R.string.ActionTakeScreenshoot), "un1", user);
                        }
                    } else if (message.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL) {
                        if (((TLRPC$TL_decryptedMessageActionSetMessageTTL) message.action.encryptedAction).ttl_seconds != 0) {
                            if (isOut()) {
                                this.messageText = LocaleController.formatString("MessageLifetimeChangedOutgoing", R.string.MessageLifetimeChangedOutgoing, LocaleController.formatTTLString(r0.ttl_seconds));
                            } else {
                                this.messageText = LocaleController.formatString("MessageLifetimeChanged", R.string.MessageLifetimeChanged, UserObject.getFirstName(user), LocaleController.formatTTLString(r0.ttl_seconds));
                            }
                        } else if (isOut()) {
                            this.messageText = LocaleController.getString("MessageLifetimeYouRemoved", R.string.MessageLifetimeYouRemoved);
                        } else {
                            this.messageText = LocaleController.formatString("MessageLifetimeRemoved", R.string.MessageLifetimeRemoved, UserObject.getFirstName(user));
                        }
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionScreenshotTaken) {
                    if (isOut()) {
                        this.messageText = LocaleController.formatString("ActionTakeScreenshootYou", R.string.ActionTakeScreenshootYou, new Object[0]);
                    } else {
                        this.messageText = replaceWithLink(LocaleController.getString("ActionTakeScreenshoot", R.string.ActionTakeScreenshoot), "un1", user);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionCreatedBroadcastList) {
                    this.messageText = LocaleController.formatString("YouCreatedBroadcastList", R.string.YouCreatedBroadcastList, new Object[0]);
                } else if (message.action instanceof TLRPC$TL_messageActionChannelCreate) {
                    if (isMegagroup()) {
                        this.messageText = LocaleController.getString("ActionCreateMega", R.string.ActionCreateMega);
                    } else {
                        this.messageText = LocaleController.getString("ActionCreateChannel", R.string.ActionCreateChannel);
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionChatMigrateTo) {
                    this.messageText = LocaleController.getString("ActionMigrateFromGroup", R.string.ActionMigrateFromGroup);
                } else if (message.action instanceof TLRPC$TL_messageActionChannelMigrateFrom) {
                    this.messageText = LocaleController.getString("ActionMigrateFromGroup", R.string.ActionMigrateFromGroup);
                } else if (message.action instanceof TLRPC$TL_messageActionPinMessage) {
                    Chat chat = (user != null || abstractMap2 == null) ? null : (Chat) abstractMap2.get(Integer.valueOf(message.to_id.channel_id));
                    generatePinMessageText(user, chat);
                } else if (message.action instanceof TLRPC$TL_messageActionHistoryClear) {
                    this.messageText = LocaleController.getString("HistoryCleared", R.string.HistoryCleared);
                } else if (message.action instanceof TLRPC$TL_messageActionGameScore) {
                    generateGameMessageText(user);
                } else if (message.action instanceof TLRPC$TL_messageActionPhoneCall) {
                    TLRPC$TL_messageActionPhoneCall tLRPC$TL_messageActionPhoneCall = (TLRPC$TL_messageActionPhoneCall) this.messageOwner.action;
                    boolean z2 = tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonMissed;
                    if (this.messageOwner.from_id == UserConfig.getClientUserId()) {
                        if (z2) {
                            this.messageText = LocaleController.getString("CallMessageOutgoingMissed", R.string.CallMessageOutgoingMissed);
                        } else {
                            this.messageText = LocaleController.getString("CallMessageOutgoing", R.string.CallMessageOutgoing);
                        }
                    } else if (z2) {
                        this.messageText = LocaleController.getString("CallMessageIncomingMissed", R.string.CallMessageIncomingMissed);
                    } else if (tLRPC$TL_messageActionPhoneCall.reason instanceof TLRPC$TL_phoneCallDiscardReasonBusy) {
                        this.messageText = LocaleController.getString("CallMessageIncomingDeclined", R.string.CallMessageIncomingDeclined);
                    } else {
                        this.messageText = LocaleController.getString("CallMessageIncoming", R.string.CallMessageIncoming);
                    }
                    if (tLRPC$TL_messageActionPhoneCall.duration > 0) {
                        r2 = LocaleController.formatCallDuration(tLRPC$TL_messageActionPhoneCall.duration);
                        this.messageText = LocaleController.formatString("CallMessageWithDuration", R.string.CallMessageWithDuration, this.messageText, r2);
                        String charSequence = this.messageText.toString();
                        i = charSequence.indexOf(r2);
                        if (i != -1) {
                            CharSequence spannableString = new SpannableString(this.messageText);
                            intValue = r2.length() + i;
                            if (i > 0 && charSequence.charAt(i - 1) == '(') {
                                i--;
                            }
                            if (intValue < charSequence.length() && charSequence.charAt(intValue) == ')') {
                                intValue++;
                            }
                            spannableString.setSpan(new TypefaceSpan(Typeface.DEFAULT), i, intValue, 0);
                            this.messageText = spannableString;
                        }
                    }
                } else if (message.action instanceof TLRPC$TL_messageActionPaymentSent) {
                    dialogId = (int) getDialogId();
                    user2 = abstractMap != null ? (User) abstractMap.get(Integer.valueOf(dialogId)) : user;
                    if (user2 == null) {
                        user2 = MessagesController.getInstance().getUser(Integer.valueOf(dialogId));
                    }
                    generatePaymentSentMessageText(null);
                    user = user2;
                }
            }
        } else if (isMediaEmpty()) {
            this.messageText = message.message;
        } else if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
            this.messageText = LocaleController.getString("AttachPhoto", R.string.AttachPhoto);
        } else if (isVideo() || ((message.media instanceof TLRPC$TL_messageMediaDocument) && (message.media.document instanceof TLRPC$TL_documentEmpty) && message.media.ttl_seconds != 0)) {
            this.messageText = LocaleController.getString("AttachVideo", R.string.AttachVideo);
        } else if (isVoice()) {
            this.messageText = LocaleController.getString("AttachAudio", R.string.AttachAudio);
        } else if (isRoundVideo()) {
            this.messageText = LocaleController.getString("AttachRound", R.string.AttachRound);
        } else if ((message.media instanceof TLRPC$TL_messageMediaGeo) || (message.media instanceof TLRPC$TL_messageMediaVenue)) {
            this.messageText = LocaleController.getString("AttachLocation", R.string.AttachLocation);
        } else if (message.media instanceof TLRPC$TL_messageMediaGeoLive) {
            this.messageText = LocaleController.getString("AttachLiveLocation", R.string.AttachLiveLocation);
        } else if (message.media instanceof TLRPC$TL_messageMediaContact) {
            this.messageText = LocaleController.getString("AttachContact", R.string.AttachContact);
        } else if (message.media instanceof TLRPC$TL_messageMediaGame) {
            this.messageText = message.message;
        } else if (message.media instanceof TLRPC$TL_messageMediaInvoice) {
            this.messageText = message.media.description;
        } else if (message.media instanceof TLRPC$TL_messageMediaUnsupported) {
            this.messageText = LocaleController.getString("UnsupportedMedia", R.string.UnsupportedMedia);
        } else if (message.media instanceof TLRPC$TL_messageMediaDocument) {
            if (isSticker()) {
                firstName = getStrickerChar();
                if (firstName == null || firstName.length() <= 0) {
                    this.messageText = LocaleController.getString("AttachSticker", R.string.AttachSticker);
                } else {
                    this.messageText = String.format("%s %s", new Object[]{firstName, LocaleController.getString("AttachSticker", R.string.AttachSticker)});
                }
            } else if (isMusic()) {
                this.messageText = LocaleController.getString("AttachMusic", R.string.AttachMusic);
            } else if (isGif()) {
                this.messageText = LocaleController.getString("AttachGif", R.string.AttachGif);
            } else {
                CharSequence documentFileName = FileLoader.getDocumentFileName(message.media.document);
                if (documentFileName == null || documentFileName.length() <= 0) {
                    this.messageText = LocaleController.getString("AttachDocument", R.string.AttachDocument);
                } else {
                    this.messageText = documentFileName;
                }
            }
        }
        if (this.messageText == null) {
            this.messageText = TtmlNode.ANONYMOUS_REGION_ID;
        }
        setType();
        measureInlineBotButtons();
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(((long) this.messageOwner.date) * 1000);
        intValue = gregorianCalendar.get(6);
        dialogId = gregorianCalendar.get(1);
        i = gregorianCalendar.get(2);
        this.dateKey = String.format("%d_%02d_%02d", new Object[]{Integer.valueOf(dialogId), Integer.valueOf(i), Integer.valueOf(intValue)});
        this.monthKey = String.format("%d_%02d", new Object[]{Integer.valueOf(dialogId), Integer.valueOf(i)});
        if (this.messageOwner.message != null && this.messageOwner.id < 0 && this.messageOwner.message.length() > 6 && (isVideo() || isNewGif() || isRoundVideo())) {
            this.videoEditedInfo = new VideoEditedInfo();
            if (this.videoEditedInfo.parseString(this.messageOwner.message)) {
                this.videoEditedInfo.roundVideo = isRoundVideo();
            } else {
                this.videoEditedInfo = null;
            }
        }
        generateCaption();
        if (z) {
            TextPaint textPaint = this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? Theme.chat_msgGameTextPaint : Theme.chat_msgTextPaint;
            int[] iArr = MessagesController.getInstance().allowBigEmoji ? new int[1] : null;
            this.messageText = Emoji.replaceEmoji(this.messageText, textPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false, iArr);
            if (iArr != null && iArr[0] >= 1 && iArr[0] <= 3) {
                TextPaint textPaint2;
                switch (iArr[0]) {
                    case 1:
                        textPaint2 = Theme.chat_msgTextPaintOneEmoji;
                        intValue = AndroidUtilities.dp(32.0f);
                        break;
                    case 2:
                        textPaint2 = Theme.chat_msgTextPaintTwoEmoji;
                        intValue = AndroidUtilities.dp(28.0f);
                        break;
                    default:
                        textPaint2 = Theme.chat_msgTextPaintThreeEmoji;
                        intValue = AndroidUtilities.dp(24.0f);
                        break;
                }
                EmojiSpan[] emojiSpanArr = (EmojiSpan[]) ((Spannable) this.messageText).getSpans(0, this.messageText.length(), EmojiSpan.class);
                if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                    for (EmojiSpan replaceFontMetrics : emojiSpanArr) {
                        replaceFontMetrics.replaceFontMetrics(textPaint2.getFontMetricsInt(), intValue);
                    }
                }
            }
            generateLayout(user);
        }
        this.layoutCreated = z;
        generateThumbs(false);
        checkMediaExistance();
    }

    public MessageObject(Message message, AbstractMap<Integer, User> abstractMap, boolean z) {
        this(message, abstractMap, null, z);
    }

    public MessageObject(TL_channelAdminLogEvent tL_channelAdminLogEvent, ArrayList<MessageObject> arrayList, HashMap<String, ArrayList<MessageObject>> hashMap, Chat chat, int[] iArr) {
        Message message;
        Message tLRPC$TL_message;
        Message message2;
        this.type = 1000;
        TLObject user = (tL_channelAdminLogEvent.user_id <= 0 || null != null) ? null : MessagesController.getInstance().getUser(Integer.valueOf(tL_channelAdminLogEvent.user_id));
        this.currentEvent = tL_channelAdminLogEvent;
        Calendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTimeInMillis(((long) tL_channelAdminLogEvent.date) * 1000);
        int i = gregorianCalendar.get(6);
        int i2 = gregorianCalendar.get(1);
        int i3 = gregorianCalendar.get(2);
        this.dateKey = String.format("%d_%02d_%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i)});
        this.monthKey = String.format("%d_%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)});
        Peer tLRPC$TL_peerChannel = new TLRPC$TL_peerChannel();
        tLRPC$TL_peerChannel.channel_id = chat.id;
        String str;
        if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionChangeTitle) {
            str = ((TL_channelAdminLogEventActionChangeTitle) tL_channelAdminLogEvent.action).new_value;
            if (chat.megagroup) {
                this.messageText = replaceWithLink(LocaleController.formatString("EventLogEditedGroupTitle", R.string.EventLogEditedGroupTitle, str), "un1", user);
            } else {
                this.messageText = replaceWithLink(LocaleController.formatString("EventLogEditedChannelTitle", R.string.EventLogEditedChannelTitle, str), "un1", user);
            }
            message = null;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionChangePhoto) {
            this.messageOwner = new TLRPC$TL_messageService();
            if (tL_channelAdminLogEvent.action.new_photo instanceof TL_chatPhotoEmpty) {
                this.messageOwner.action = new TLRPC$TL_messageActionChatDeletePhoto();
                if (chat.megagroup) {
                    this.messageText = replaceWithLink(LocaleController.getString("EventLogRemovedWGroupPhoto", R.string.EventLogRemovedWGroupPhoto), "un1", user);
                    message = null;
                } else {
                    this.messageText = replaceWithLink(LocaleController.getString("EventLogRemovedChannelPhoto", R.string.EventLogRemovedChannelPhoto), "un1", user);
                    message = null;
                }
            } else {
                this.messageOwner.action = new TLRPC$TL_messageActionChatEditPhoto();
                this.messageOwner.action.photo = new TLRPC$TL_photo();
                TLRPC$TL_photoSize tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
                tLRPC$TL_photoSize.location = tL_channelAdminLogEvent.action.new_photo.photo_small;
                tLRPC$TL_photoSize.type = "s";
                tLRPC$TL_photoSize.h = 80;
                tLRPC$TL_photoSize.w = 80;
                this.messageOwner.action.photo.sizes.add(tLRPC$TL_photoSize);
                tLRPC$TL_photoSize = new TLRPC$TL_photoSize();
                tLRPC$TL_photoSize.location = tL_channelAdminLogEvent.action.new_photo.photo_big;
                tLRPC$TL_photoSize.type = "m";
                tLRPC$TL_photoSize.h = 640;
                tLRPC$TL_photoSize.w = 640;
                this.messageOwner.action.photo.sizes.add(tLRPC$TL_photoSize);
                if (chat.megagroup) {
                    this.messageText = replaceWithLink(LocaleController.getString("EventLogEditedGroupPhoto", R.string.EventLogEditedGroupPhoto), "un1", user);
                } else {
                    this.messageText = replaceWithLink(LocaleController.getString("EventLogEditedChannelPhoto", R.string.EventLogEditedChannelPhoto), "un1", user);
                }
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionParticipantJoin) {
            if (chat.megagroup) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogGroupJoined", R.string.EventLogGroupJoined), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogChannelJoined", R.string.EventLogChannelJoined), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionParticipantLeave) {
            this.messageOwner = new TLRPC$TL_messageService();
            this.messageOwner.action = new TLRPC$TL_messageActionChatDeleteUser();
            this.messageOwner.action.user_id = tL_channelAdminLogEvent.user_id;
            if (chat.megagroup) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogLeftGroup", R.string.EventLogLeftGroup), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogLeftChannel", R.string.EventLogLeftChannel), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionParticipantInvite) {
            this.messageOwner = new TLRPC$TL_messageService();
            this.messageOwner.action = new TLRPC$TL_messageActionChatAddUser();
            TLObject user2 = MessagesController.getInstance().getUser(Integer.valueOf(tL_channelAdminLogEvent.action.participant.user_id));
            if (tL_channelAdminLogEvent.action.participant.user_id != this.messageOwner.from_id) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogAdded", R.string.EventLogAdded), "un2", user2);
                this.messageText = replaceWithLink(this.messageText, "un1", user);
            } else if (chat.megagroup) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogGroupJoined", R.string.EventLogGroupJoined), "un1", user);
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogChannelJoined", R.string.EventLogChannelJoined), "un1", user);
            }
            message = null;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionParticipantToggleAdmin) {
            this.messageOwner = new TLRPC$TL_message();
            User user3 = MessagesController.getInstance().getUser(Integer.valueOf(tL_channelAdminLogEvent.action.prev_participant.user_id));
            String string = LocaleController.getString("EventLogPromoted", R.string.EventLogPromoted);
            i2 = string.indexOf("%1$s");
            StringBuilder stringBuilder = new StringBuilder(String.format(string, new Object[]{getUserName(user3, this.messageOwner.entities, i2)}));
            stringBuilder.append("\n");
            TL_channelAdminRights tL_channelAdminRights = tL_channelAdminLogEvent.action.prev_participant.admin_rights;
            TL_channelAdminRights tL_channelAdminRights2 = tL_channelAdminLogEvent.action.new_participant.admin_rights;
            TL_channelAdminRights tL_channelAdminRights3 = tL_channelAdminRights == null ? new TL_channelAdminRights() : tL_channelAdminRights;
            if (tL_channelAdminRights2 == null) {
                tL_channelAdminRights2 = new TL_channelAdminRights();
            }
            if (tL_channelAdminRights3.change_info != tL_channelAdminRights2.change_info) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.change_info ? '+' : '-').append(' ');
                stringBuilder.append(chat.megagroup ? LocaleController.getString("EventLogPromotedChangeGroupInfo", R.string.EventLogPromotedChangeGroupInfo) : LocaleController.getString("EventLogPromotedChangeChannelInfo", R.string.EventLogPromotedChangeChannelInfo));
            }
            if (!chat.megagroup) {
                if (tL_channelAdminRights3.post_messages != tL_channelAdminRights2.post_messages) {
                    stringBuilder.append('\n').append(tL_channelAdminRights2.post_messages ? '+' : '-').append(' ');
                    stringBuilder.append(LocaleController.getString("EventLogPromotedPostMessages", R.string.EventLogPromotedPostMessages));
                }
                if (tL_channelAdminRights3.edit_messages != tL_channelAdminRights2.edit_messages) {
                    stringBuilder.append('\n').append(tL_channelAdminRights2.edit_messages ? '+' : '-').append(' ');
                    stringBuilder.append(LocaleController.getString("EventLogPromotedEditMessages", R.string.EventLogPromotedEditMessages));
                }
            }
            if (tL_channelAdminRights3.delete_messages != tL_channelAdminRights2.delete_messages) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.delete_messages ? '+' : '-').append(' ');
                stringBuilder.append(LocaleController.getString("EventLogPromotedDeleteMessages", R.string.EventLogPromotedDeleteMessages));
            }
            if (tL_channelAdminRights3.add_admins != tL_channelAdminRights2.add_admins) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.add_admins ? '+' : '-').append(' ');
                stringBuilder.append(LocaleController.getString("EventLogPromotedAddAdmins", R.string.EventLogPromotedAddAdmins));
            }
            if (chat.megagroup && tL_channelAdminRights3.ban_users != tL_channelAdminRights2.ban_users) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.ban_users ? '+' : '-').append(' ');
                stringBuilder.append(LocaleController.getString("EventLogPromotedBanUsers", R.string.EventLogPromotedBanUsers));
            }
            if (tL_channelAdminRights3.invite_users != tL_channelAdminRights2.invite_users) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.invite_users ? '+' : '-').append(' ');
                stringBuilder.append(LocaleController.getString("EventLogPromotedAddUsers", R.string.EventLogPromotedAddUsers));
            }
            if (chat.megagroup && tL_channelAdminRights3.pin_messages != tL_channelAdminRights2.pin_messages) {
                stringBuilder.append('\n').append(tL_channelAdminRights2.pin_messages ? '+' : '-').append(' ');
                stringBuilder.append(LocaleController.getString("EventLogPromotedPinMessages", R.string.EventLogPromotedPinMessages));
            }
            this.messageText = stringBuilder.toString();
            message = null;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionParticipantToggleBan) {
            this.messageOwner = new TLRPC$TL_message();
            User user4 = MessagesController.getInstance().getUser(Integer.valueOf(tL_channelAdminLogEvent.action.prev_participant.user_id));
            TL_channelBannedRights tL_channelBannedRights = tL_channelAdminLogEvent.action.prev_participant.banned_rights;
            TL_channelBannedRights tL_channelBannedRights2 = tL_channelAdminLogEvent.action.new_participant.banned_rights;
            if (!chat.megagroup || (tL_channelBannedRights2 != null && tL_channelBannedRights2.view_messages && (tL_channelBannedRights2 == null || tL_channelBannedRights == null || tL_channelBannedRights2.until_date == tL_channelBannedRights.until_date))) {
                str = (tL_channelBannedRights2 == null || !(tL_channelBannedRights == null || tL_channelBannedRights2.view_messages)) ? LocaleController.getString("EventLogChannelUnrestricted", R.string.EventLogChannelUnrestricted) : LocaleController.getString("EventLogChannelRestricted", R.string.EventLogChannelRestricted);
                i = str.indexOf("%1$s");
                this.messageText = String.format(str, new Object[]{getUserName(user4, this.messageOwner.entities, i)});
            } else {
                StringBuilder stringBuilder2;
                int i4;
                String str2;
                Object obj;
                if (tL_channelBannedRights2 == null || AndroidUtilities.isBannedForever(tL_channelBannedRights2.until_date)) {
                    stringBuilder2 = new StringBuilder(LocaleController.getString("UserRestrictionsUntilForever", R.string.UserRestrictionsUntilForever));
                } else {
                    stringBuilder2 = new StringBuilder();
                    i2 = tL_channelBannedRights2.until_date - tL_channelAdminLogEvent.date;
                    int i5 = ((i2 / 60) / 60) / 24;
                    i2 -= ((i5 * 60) * 60) * 24;
                    int i6 = (i2 / 60) / 60;
                    int i7 = (i2 - ((i6 * 60) * 60)) / 60;
                    i4 = 0;
                    for (int i8 = 0; i8 < 3; i8++) {
                        str2 = null;
                        if (i8 == 0) {
                            if (i5 != 0) {
                                str2 = LocaleController.formatPluralString("Days", i5);
                                i4++;
                            }
                        } else if (i8 == 1) {
                            if (i6 != 0) {
                                str2 = LocaleController.formatPluralString("Hours", i6);
                                i4++;
                            }
                        } else if (i7 != 0) {
                            str2 = LocaleController.formatPluralString("Minutes", i7);
                            i4++;
                        }
                        if (str2 != null) {
                            if (stringBuilder2.length() > 0) {
                                stringBuilder2.append(", ");
                            }
                            stringBuilder2.append(str2);
                        }
                        if (i4 == 2) {
                            break;
                        }
                    }
                }
                str2 = LocaleController.getString("EventLogRestrictedUntil", R.string.EventLogRestrictedUntil);
                i4 = str2.indexOf("%1$s");
                StringBuilder stringBuilder3 = new StringBuilder(String.format(str2, new Object[]{getUserName(user4, this.messageOwner.entities, i4), stringBuilder2.toString()}));
                if (tL_channelBannedRights == null) {
                    tL_channelBannedRights = new TL_channelBannedRights();
                }
                TL_channelBannedRights tL_channelBannedRights3 = tL_channelBannedRights2 == null ? new TL_channelBannedRights() : tL_channelBannedRights2;
                if (tL_channelBannedRights.view_messages != tL_channelBannedRights3.view_messages) {
                    stringBuilder3.append('\n');
                    obj = 1;
                    stringBuilder3.append('\n').append(!tL_channelBannedRights3.view_messages ? '+' : '-').append(' ');
                    stringBuilder3.append(LocaleController.getString("EventLogRestrictedReadMessages", R.string.EventLogRestrictedReadMessages));
                } else {
                    obj = null;
                }
                if (tL_channelBannedRights.send_messages != tL_channelBannedRights3.send_messages) {
                    if (obj == null) {
                        stringBuilder3.append('\n');
                        obj = 1;
                    }
                    stringBuilder3.append('\n').append(!tL_channelBannedRights3.send_messages ? '+' : '-').append(' ');
                    stringBuilder3.append(LocaleController.getString("EventLogRestrictedSendMessages", R.string.EventLogRestrictedSendMessages));
                }
                if (!(tL_channelBannedRights.send_stickers == tL_channelBannedRights3.send_stickers && tL_channelBannedRights.send_inline == tL_channelBannedRights3.send_inline && tL_channelBannedRights.send_gifs == tL_channelBannedRights3.send_gifs && tL_channelBannedRights.send_games == tL_channelBannedRights3.send_games)) {
                    if (obj == null) {
                        stringBuilder3.append('\n');
                        obj = 1;
                    }
                    stringBuilder3.append('\n').append(!tL_channelBannedRights3.send_stickers ? '+' : '-').append(' ');
                    stringBuilder3.append(LocaleController.getString("EventLogRestrictedSendStickers", R.string.EventLogRestrictedSendStickers));
                }
                if (tL_channelBannedRights.send_media != tL_channelBannedRights3.send_media) {
                    if (obj == null) {
                        stringBuilder3.append('\n');
                        obj = 1;
                    }
                    stringBuilder3.append('\n').append(!tL_channelBannedRights3.send_media ? '+' : '-').append(' ');
                    stringBuilder3.append(LocaleController.getString("EventLogRestrictedSendMedia", R.string.EventLogRestrictedSendMedia));
                }
                if (tL_channelBannedRights.embed_links != tL_channelBannedRights3.embed_links) {
                    if (obj == null) {
                        stringBuilder3.append('\n');
                    }
                    stringBuilder3.append('\n').append(!tL_channelBannedRights3.embed_links ? '+' : '-').append(' ');
                    stringBuilder3.append(LocaleController.getString("EventLogRestrictedSendEmbed", R.string.EventLogRestrictedSendEmbed));
                }
                this.messageText = stringBuilder3.toString();
            }
            message = null;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionUpdatePinned) {
            if (tL_channelAdminLogEvent.action.message instanceof TLRPC$TL_messageEmpty) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogUnpinnedMessages", R.string.EventLogUnpinnedMessages), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogPinnedMessages", R.string.EventLogPinnedMessages), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionToggleSignatures) {
            if (((TL_channelAdminLogEventActionToggleSignatures) tL_channelAdminLogEvent.action).new_value) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledSignaturesOn", R.string.EventLogToggledSignaturesOn), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledSignaturesOff", R.string.EventLogToggledSignaturesOff), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionToggleInvites) {
            if (((TL_channelAdminLogEventActionToggleInvites) tL_channelAdminLogEvent.action).new_value) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledInvitesOn", R.string.EventLogToggledInvitesOn), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledInvitesOff", R.string.EventLogToggledInvitesOff), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionDeleteMessage) {
            this.messageText = replaceWithLink(LocaleController.getString("EventLogDeletedMessages", R.string.EventLogDeletedMessages), "un1", user);
            message = null;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionTogglePreHistoryHidden) {
            if (((TL_channelAdminLogEventActionTogglePreHistoryHidden) tL_channelAdminLogEvent.action).new_value) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledInvitesHistoryOff", R.string.EventLogToggledInvitesHistoryOff), "un1", user);
                message = null;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogToggledInvitesHistoryOn", R.string.EventLogToggledInvitesHistoryOn), "un1", user);
                message = null;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionChangeAbout) {
            this.messageText = replaceWithLink(chat.megagroup ? LocaleController.getString("EventLogEditedGroupDescription", R.string.EventLogEditedGroupDescription) : LocaleController.getString("EventLogEditedChannelDescription", R.string.EventLogEditedChannelDescription), "un1", user);
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.unread = false;
            tLRPC$TL_message.from_id = tL_channelAdminLogEvent.user_id;
            tLRPC$TL_message.to_id = tLRPC$TL_peerChannel;
            tLRPC$TL_message.date = tL_channelAdminLogEvent.date;
            tLRPC$TL_message.message = ((TL_channelAdminLogEventActionChangeAbout) tL_channelAdminLogEvent.action).new_value;
            if (TextUtils.isEmpty(((TL_channelAdminLogEventActionChangeAbout) tL_channelAdminLogEvent.action).prev_value)) {
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
                message = tLRPC$TL_message;
            } else {
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaWebPage();
                tLRPC$TL_message.media.webpage = new TLRPC$TL_webPage();
                tLRPC$TL_message.media.webpage.flags = 10;
                tLRPC$TL_message.media.webpage.display_url = TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_message.media.webpage.url = TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_message.media.webpage.site_name = LocaleController.getString("EventLogPreviousGroupDescription", R.string.EventLogPreviousGroupDescription);
                tLRPC$TL_message.media.webpage.description = ((TL_channelAdminLogEventActionChangeAbout) tL_channelAdminLogEvent.action).prev_value;
                message = tLRPC$TL_message;
            }
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionChangeUsername) {
            Object obj2 = ((TL_channelAdminLogEventActionChangeUsername) tL_channelAdminLogEvent.action).new_value;
            if (TextUtils.isEmpty(obj2)) {
                this.messageText = replaceWithLink(chat.megagroup ? LocaleController.getString("EventLogRemovedGroupLink", R.string.EventLogRemovedGroupLink) : LocaleController.getString("EventLogRemovedChannelLink", R.string.EventLogRemovedChannelLink), "un1", user);
            } else {
                this.messageText = replaceWithLink(chat.megagroup ? LocaleController.getString("EventLogChangedGroupLink", R.string.EventLogChangedGroupLink) : LocaleController.getString("EventLogChangedChannelLink", R.string.EventLogChangedChannelLink), "un1", user);
            }
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.unread = false;
            tLRPC$TL_message.from_id = tL_channelAdminLogEvent.user_id;
            tLRPC$TL_message.to_id = tLRPC$TL_peerChannel;
            tLRPC$TL_message.date = tL_channelAdminLogEvent.date;
            if (TextUtils.isEmpty(obj2)) {
                tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
            } else {
                tLRPC$TL_message.message = "https://" + MessagesController.getInstance().linkPrefix + "/" + obj2;
            }
            TLRPC$TL_messageEntityUrl tLRPC$TL_messageEntityUrl = new TLRPC$TL_messageEntityUrl();
            tLRPC$TL_messageEntityUrl.offset = 0;
            tLRPC$TL_messageEntityUrl.length = tLRPC$TL_message.message.length();
            tLRPC$TL_message.entities.add(tLRPC$TL_messageEntityUrl);
            if (TextUtils.isEmpty(((TL_channelAdminLogEventActionChangeUsername) tL_channelAdminLogEvent.action).prev_value)) {
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaEmpty();
            } else {
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaWebPage();
                tLRPC$TL_message.media.webpage = new TLRPC$TL_webPage();
                tLRPC$TL_message.media.webpage.flags = 10;
                tLRPC$TL_message.media.webpage.display_url = TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_message.media.webpage.url = TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_message.media.webpage.site_name = LocaleController.getString("EventLogPreviousLink", R.string.EventLogPreviousLink);
                tLRPC$TL_message.media.webpage.description = "https://" + MessagesController.getInstance().linkPrefix + "/" + ((TL_channelAdminLogEventActionChangeUsername) tL_channelAdminLogEvent.action).prev_value;
            }
            message = tLRPC$TL_message;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionEditMessage) {
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.out = false;
            tLRPC$TL_message.unread = false;
            tLRPC$TL_message.from_id = tL_channelAdminLogEvent.user_id;
            tLRPC$TL_message.to_id = tLRPC$TL_peerChannel;
            tLRPC$TL_message.date = tL_channelAdminLogEvent.date;
            message2 = ((TL_channelAdminLogEventActionEditMessage) tL_channelAdminLogEvent.action).new_message;
            message = ((TL_channelAdminLogEventActionEditMessage) tL_channelAdminLogEvent.action).prev_message;
            if (message2.media == null || (message2.media instanceof TLRPC$TL_messageMediaEmpty) || (message2.media instanceof TLRPC$TL_messageMediaWebPage) || !TextUtils.isEmpty(message2.message)) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogEditedMessages", R.string.EventLogEditedMessages), "un1", user);
                tLRPC$TL_message.message = message2.message;
                tLRPC$TL_message.media = new TLRPC$TL_messageMediaWebPage();
                tLRPC$TL_message.media.webpage = new TLRPC$TL_webPage();
                tLRPC$TL_message.media.webpage.site_name = LocaleController.getString("EventLogOriginalMessages", R.string.EventLogOriginalMessages);
                tLRPC$TL_message.media.webpage.description = message.message;
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogEditedCaption", R.string.EventLogEditedCaption), "un1", user);
                tLRPC$TL_message.media = message2.media;
                tLRPC$TL_message.media.webpage = new TLRPC$TL_webPage();
                tLRPC$TL_message.media.webpage.site_name = LocaleController.getString("EventLogOriginalCaption", R.string.EventLogOriginalCaption);
                if (TextUtils.isEmpty(message.media.caption)) {
                    tLRPC$TL_message.media.webpage.description = LocaleController.getString("EventLogOriginalCaptionEmpty", R.string.EventLogOriginalCaptionEmpty);
                } else {
                    tLRPC$TL_message.media.webpage.description = message.media.caption;
                }
            }
            tLRPC$TL_message.reply_markup = message2.reply_markup;
            tLRPC$TL_message.media.webpage.flags = 10;
            tLRPC$TL_message.media.webpage.display_url = TtmlNode.ANONYMOUS_REGION_ID;
            tLRPC$TL_message.media.webpage.url = TtmlNode.ANONYMOUS_REGION_ID;
            message = tLRPC$TL_message;
        } else if (tL_channelAdminLogEvent.action instanceof TL_channelAdminLogEventActionChangeStickerSet) {
            InputStickerSet inputStickerSet = ((TL_channelAdminLogEventActionChangeStickerSet) tL_channelAdminLogEvent.action).new_stickerset;
            InputStickerSet inputStickerSet2 = ((TL_channelAdminLogEventActionChangeStickerSet) tL_channelAdminLogEvent.action).new_stickerset;
            if (inputStickerSet == null || (inputStickerSet instanceof TLRPC$TL_inputStickerSetEmpty)) {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogRemovedStickersSet", R.string.EventLogRemovedStickersSet), "un1", user);
            } else {
                this.messageText = replaceWithLink(LocaleController.getString("EventLogChangedStickersSet", R.string.EventLogChangedStickersSet), "un1", user);
            }
            message = null;
        } else {
            this.messageText = "unsupported " + tL_channelAdminLogEvent.action;
            message = null;
        }
        if (this.messageOwner == null) {
            this.messageOwner = new TLRPC$TL_messageService();
        }
        this.messageOwner.message = this.messageText.toString();
        this.messageOwner.from_id = tL_channelAdminLogEvent.user_id;
        this.messageOwner.date = tL_channelAdminLogEvent.date;
        tLRPC$TL_message = this.messageOwner;
        int i9 = iArr[0];
        iArr[0] = i9 + 1;
        tLRPC$TL_message.id = i9;
        this.eventId = tL_channelAdminLogEvent.id;
        this.messageOwner.out = false;
        this.messageOwner.to_id = new TLRPC$TL_peerChannel();
        this.messageOwner.to_id.channel_id = chat.id;
        this.messageOwner.unread = false;
        if (chat.megagroup) {
            tLRPC$TL_message = this.messageOwner;
            tLRPC$TL_message.flags |= Integer.MIN_VALUE;
        }
        message2 = (tL_channelAdminLogEvent.action.message == null || (tL_channelAdminLogEvent.action.message instanceof TLRPC$TL_messageEmpty)) ? message : tL_channelAdminLogEvent.action.message;
        if (message2 != null) {
            message2.out = false;
            i = iArr[0];
            iArr[0] = i + 1;
            message2.id = i;
            message2.reply_to_msg_id = 0;
            message2.flags &= -32769;
            if (chat.megagroup) {
                message2.flags |= Integer.MIN_VALUE;
            }
            MessageObject messageObject = new MessageObject(message2, null, null, true, this.eventId);
            if (messageObject.contentType >= 0) {
                createDateArray(tL_channelAdminLogEvent, arrayList, hashMap);
                arrayList.add(arrayList.size() - 1, messageObject);
            } else {
                this.contentType = -1;
            }
        }
        if (this.contentType >= 0) {
            createDateArray(tL_channelAdminLogEvent, arrayList, hashMap);
            arrayList.add(arrayList.size() - 1, this);
            if (this.messageText == null) {
                this.messageText = TtmlNode.ANONYMOUS_REGION_ID;
            }
            setType();
            measureInlineBotButtons();
            if (this.messageOwner.message != null && this.messageOwner.id < 0 && this.messageOwner.message.length() > 6 && (isVideo() || isNewGif() || isRoundVideo())) {
                this.videoEditedInfo = new VideoEditedInfo();
                if (this.videoEditedInfo.parseString(this.messageOwner.message)) {
                    this.videoEditedInfo.roundVideo = isRoundVideo();
                } else {
                    this.videoEditedInfo = null;
                }
            }
            generateCaption();
            TextPaint textPaint = this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? Theme.chat_msgGameTextPaint : Theme.chat_msgTextPaint;
            int[] iArr2 = MessagesController.getInstance().allowBigEmoji ? new int[1] : null;
            this.messageText = Emoji.replaceEmoji(this.messageText, textPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false, iArr2);
            if (iArr2 != null && iArr2[0] >= 1 && iArr2[0] <= 3) {
                TextPaint textPaint2;
                switch (iArr2[0]) {
                    case 1:
                        textPaint2 = Theme.chat_msgTextPaintOneEmoji;
                        i = AndroidUtilities.dp(32.0f);
                        break;
                    case 2:
                        textPaint2 = Theme.chat_msgTextPaintTwoEmoji;
                        i = AndroidUtilities.dp(28.0f);
                        break;
                    default:
                        textPaint2 = Theme.chat_msgTextPaintThreeEmoji;
                        i = AndroidUtilities.dp(24.0f);
                        break;
                }
                EmojiSpan[] emojiSpanArr = (EmojiSpan[]) ((Spannable) this.messageText).getSpans(0, this.messageText.length(), EmojiSpan.class);
                if (emojiSpanArr != null && emojiSpanArr.length > 0) {
                    for (EmojiSpan replaceFontMetrics : emojiSpanArr) {
                        replaceFontMetrics.replaceFontMetrics(textPaint2.getFontMetricsInt(), i);
                    }
                }
            }
            generateLayout(user);
            this.layoutCreated = true;
            generateThumbs(false);
            checkMediaExistance();
        }
    }

    public static void addLinks(boolean z, CharSequence charSequence) {
        addLinks(z, charSequence, true);
    }

    public static void addLinks(boolean z, CharSequence charSequence, boolean z2) {
        if ((charSequence instanceof Spannable) && containsUrls(charSequence)) {
            if (charSequence.length() < Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                try {
                    Linkify.addLinks((Spannable) charSequence, 5);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
            } else {
                try {
                    Linkify.addLinks((Spannable) charSequence, 1);
                } catch (Throwable e2) {
                    FileLog.m13728e(e2);
                }
            }
            addUsernamesAndHashtags(z, charSequence, z2);
        }
    }

    private static void addUsernamesAndHashtags(boolean z, CharSequence charSequence, boolean z2) {
        try {
            if (urlPattern == null) {
                urlPattern = Pattern.compile("(^|\\s)/[a-zA-Z@\\d_]{1,255}|(^|\\s)@[a-zA-Z\\d_]{1,32}|(^|\\s)#[\\w\\.]+");
            }
            Matcher matcher = urlPattern.matcher(charSequence);
            while (matcher.find()) {
                Object uRLSpanBotCommand;
                int start = matcher.start();
                int end = matcher.end();
                int i = (charSequence.charAt(start) == '@' || charSequence.charAt(start) == '#' || charSequence.charAt(start) == '/') ? start : start + 1;
                if (charSequence.charAt(i) == '/') {
                    uRLSpanBotCommand = z2 ? new URLSpanBotCommand(charSequence.subSequence(i, end).toString(), z) : null;
                } else {
                    URLSpanNoUnderline uRLSpanNoUnderline = new URLSpanNoUnderline(charSequence.subSequence(i, end).toString());
                }
                if (uRLSpanBotCommand != null) {
                    ((Spannable) charSequence).setSpan(uRLSpanBotCommand, i, end, 0);
                }
            }
        } catch (Throwable e) {
            FileLog.m13728e(e);
        }
    }

    public static boolean canDeleteMessage(Message message, Chat chat) {
        boolean z = false;
        if (message.id < 0) {
            return true;
        }
        if (chat == null && message.to_id.channel_id != 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(message.to_id.channel_id));
        }
        if (ChatObject.isChannel(chat)) {
            if (message.id != 1) {
                if (chat.creator) {
                    return true;
                }
                if (chat.admin_rights != null && (chat.admin_rights.delete_messages || message.out)) {
                    return true;
                }
                if (chat.megagroup && message.out && message.from_id > 0) {
                    return true;
                }
            }
            return false;
        }
        if (isOut(message) || !ChatObject.isChannel(chat)) {
            z = true;
        }
        return z;
    }

    public static boolean canEditMessage(Message message, Chat chat) {
        if (message == null || message.to_id == null || ((message.media != null && (isRoundVideoDocument(message.media.document) || isStickerDocument(message.media.document))) || ((message.action != null && !(message.action instanceof TLRPC$TL_messageActionEmpty)) || isForwardedMessage(message) || message.via_bot_id != 0 || message.id < 0))) {
            return false;
        }
        if (message.from_id == message.to_id.user_id && message.from_id == UserConfig.getClientUserId() && !isLiveLocationMessage(message)) {
            return true;
        }
        if (chat == null && message.to_id.channel_id != 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(message.to_id.channel_id));
            if (chat == null) {
                return false;
            }
        }
        if (message.out && chat != null && chat.megagroup) {
            if (chat.creator) {
                return true;
            }
            if (chat.admin_rights != null && chat.admin_rights.pin_messages) {
                return true;
            }
        }
        if (Math.abs(message.date - ConnectionsManager.getInstance().getCurrentTime()) > MessagesController.getInstance().maxEditTime) {
            return false;
        }
        if (message.to_id.channel_id == 0) {
            if (message.out || message.from_id == UserConfig.getClientUserId()) {
                if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                    return true;
                }
                if (((message.media instanceof TLRPC$TL_messageMediaDocument) && !isStickerMessage(message)) || (message.media instanceof TLRPC$TL_messageMediaEmpty) || (message.media instanceof TLRPC$TL_messageMediaWebPage) || message.media == null) {
                    return true;
                }
            }
            return false;
        }
        if ((chat.megagroup && message.out) || (!chat.megagroup && ((chat.creator || (chat.admin_rights != null && (chat.admin_rights.edit_messages || message.out))) && message.post))) {
            if (message.media instanceof TLRPC$TL_messageMediaPhoto) {
                return true;
            }
            if (((message.media instanceof TLRPC$TL_messageMediaDocument) && !isStickerMessage(message)) || (message.media instanceof TLRPC$TL_messageMediaEmpty) || (message.media instanceof TLRPC$TL_messageMediaWebPage) || message.media == null) {
                return true;
            }
        }
        return false;
    }

    public static boolean canEditMessageAnytime(Message message, Chat chat) {
        if (message == null || message.to_id == null || ((message.media != null && (isRoundVideoDocument(message.media.document) || isStickerDocument(message.media.document))) || ((message.action != null && !(message.action instanceof TLRPC$TL_messageActionEmpty)) || isForwardedMessage(message) || message.via_bot_id != 0 || message.id < 0))) {
            return false;
        }
        if (message.from_id == message.to_id.user_id && message.from_id == UserConfig.getClientUserId() && !isLiveLocationMessage(message)) {
            return true;
        }
        if (chat == null && message.to_id.channel_id != 0) {
            chat = MessagesController.getInstance().getChat(Integer.valueOf(message.to_id.channel_id));
            if (chat == null) {
                return false;
            }
        }
        if (message.out && chat != null && chat.megagroup) {
            if (chat.creator) {
                return true;
            }
            if (chat.admin_rights != null && chat.admin_rights.pin_messages) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsUrls(CharSequence charSequence) {
        if (charSequence == null || charSequence.length() < 2 || charSequence.length() > CacheDataSink.DEFAULT_BUFFER_SIZE) {
            return false;
        }
        int length = charSequence.length();
        int i = 0;
        char c = '\u0000';
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (i < length) {
            char charAt = charSequence.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                i2 = i4 + 1;
                if (i2 >= 6) {
                    return true;
                }
                i3 = 0;
                i4 = i2;
                i2 = 0;
            } else if (charAt == ' ' || i4 <= 0) {
                i4 = 0;
            }
            if ((charAt == '@' || charAt == '#' || charAt == '/') && i == 0) {
                return true;
            }
            if (i != 0 && (charSequence.charAt(i - 1) == ' ' || charSequence.charAt(i - 1) == '\n')) {
                return true;
            }
            if (charAt == ':') {
                i3 = i3 == 0 ? 1 : 0;
            } else if (charAt == '/') {
                if (i3 == 2) {
                    return true;
                }
                i3 = i3 == 1 ? i3 + 1 : 0;
            } else if (charAt == '.') {
                i2 = (i2 != 0 || c == ' ') ? 0 : i2 + 1;
            } else if (charAt != ' ' && c == '.' && i2 == 1) {
                return true;
            } else {
                i2 = 0;
            }
            i++;
            c = charAt;
        }
        return false;
    }

    private void createDateArray(TL_channelAdminLogEvent tL_channelAdminLogEvent, ArrayList<MessageObject> arrayList, HashMap<String, ArrayList<MessageObject>> hashMap) {
        if (((ArrayList) hashMap.get(this.dateKey)) == null) {
            hashMap.put(this.dateKey, new ArrayList());
            Message tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.message = LocaleController.formatDateChat((long) tL_channelAdminLogEvent.date);
            tLRPC$TL_message.id = 0;
            tLRPC$TL_message.date = tL_channelAdminLogEvent.date;
            MessageObject messageObject = new MessageObject(tLRPC$TL_message, null, false);
            messageObject.type = 10;
            messageObject.contentType = 1;
            messageObject.isDateObject = true;
            arrayList.add(messageObject);
        }
    }

    public static long getDialogId(Message message) {
        if (message.dialog_id == 0 && message.to_id != null) {
            if (message.to_id.chat_id != 0) {
                if (message.to_id.chat_id < 0) {
                    message.dialog_id = AndroidUtilities.makeBroadcastId(message.to_id.chat_id);
                } else {
                    message.dialog_id = (long) (-message.to_id.chat_id);
                }
            } else if (message.to_id.channel_id != 0) {
                message.dialog_id = (long) (-message.to_id.channel_id);
            } else if (isOut(message)) {
                message.dialog_id = (long) message.to_id.user_id;
            } else {
                message.dialog_id = (long) message.from_id;
            }
        }
        return message.dialog_id;
    }

    private Document getDocumentWithId(TLRPC$WebPage tLRPC$WebPage, long j) {
        if (tLRPC$WebPage == null || tLRPC$WebPage.cached_page == null) {
            return null;
        }
        if (tLRPC$WebPage.document != null && tLRPC$WebPage.document.id == j) {
            return tLRPC$WebPage.document;
        }
        for (int i = 0; i < tLRPC$WebPage.cached_page.documents.size(); i++) {
            Document document = (Document) tLRPC$WebPage.cached_page.documents.get(i);
            if (document.id == j) {
                return document;
            }
        }
        return null;
    }

    public static InputStickerSet getInputStickerSet(Message message) {
        if (!(message.media == null || message.media.document == null)) {
            Iterator it = message.media.document.attributes.iterator();
            while (it.hasNext()) {
                DocumentAttribute documentAttribute = (DocumentAttribute) it.next();
                if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                    return documentAttribute.stickerset instanceof TLRPC$TL_inputStickerSetEmpty ? null : documentAttribute.stickerset;
                }
            }
        }
        return null;
    }

    private MessageObject getMessageObjectForBlock(TLRPC$WebPage tLRPC$WebPage, PageBlock pageBlock) {
        Message tLRPC$TL_message;
        if (pageBlock instanceof TLRPC$TL_pageBlockPhoto) {
            Photo photoWithId = getPhotoWithId(tLRPC$WebPage, pageBlock.photo_id);
            if (photoWithId == tLRPC$WebPage.photo) {
                return this;
            }
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaPhoto();
            tLRPC$TL_message.media.photo = photoWithId;
        } else if (!(pageBlock instanceof TLRPC$TL_pageBlockVideo)) {
            tLRPC$TL_message = null;
        } else if (getDocumentWithId(tLRPC$WebPage, pageBlock.video_id) == tLRPC$WebPage.document) {
            return this;
        } else {
            tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
            tLRPC$TL_message.media.document = getDocumentWithId(tLRPC$WebPage, pageBlock.video_id);
        }
        tLRPC$TL_message.message = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_message.id = Utilities.random.nextInt();
        tLRPC$TL_message.date = this.messageOwner.date;
        tLRPC$TL_message.to_id = this.messageOwner.to_id;
        tLRPC$TL_message.out = this.messageOwner.out;
        tLRPC$TL_message.from_id = this.messageOwner.from_id;
        return new MessageObject(tLRPC$TL_message, null, false);
    }

    public static int getMessageSize(Message message) {
        return (message.media == null || message.media.document == null) ? 0 : message.media.document.size;
    }

    private Photo getPhotoWithId(TLRPC$WebPage tLRPC$WebPage, long j) {
        if (tLRPC$WebPage == null || tLRPC$WebPage.cached_page == null) {
            return null;
        }
        if (tLRPC$WebPage.photo != null && tLRPC$WebPage.photo.id == j) {
            return tLRPC$WebPage.photo;
        }
        for (int i = 0; i < tLRPC$WebPage.cached_page.photos.size(); i++) {
            Photo photo = (Photo) tLRPC$WebPage.cached_page.photos.get(i);
            if (photo.id == j) {
                return photo;
            }
        }
        return null;
    }

    public static int getUnreadFlags(Message message) {
        int i = 0;
        if (!message.unread) {
            i = 1;
        }
        return !message.media_unread ? i | 2 : i;
    }

    private String getUserName(User user, ArrayList<MessageEntity> arrayList, int i) {
        String formatName = user == null ? TtmlNode.ANONYMOUS_REGION_ID : ContactsController.formatName(user.first_name, user.last_name);
        if (i >= 0) {
            TLRPC$TL_messageEntityMentionName tLRPC$TL_messageEntityMentionName = new TLRPC$TL_messageEntityMentionName();
            tLRPC$TL_messageEntityMentionName.user_id = user.id;
            tLRPC$TL_messageEntityMentionName.offset = i;
            tLRPC$TL_messageEntityMentionName.length = formatName.length();
            arrayList.add(tLRPC$TL_messageEntityMentionName);
        }
        if (TextUtils.isEmpty(user.username)) {
            return formatName;
        }
        if (i >= 0) {
            tLRPC$TL_messageEntityMentionName = new TLRPC$TL_messageEntityMentionName();
            tLRPC$TL_messageEntityMentionName.user_id = user.id;
            tLRPC$TL_messageEntityMentionName.offset = (formatName.length() + i) + 2;
            tLRPC$TL_messageEntityMentionName.length = user.username.length() + 1;
            arrayList.add(tLRPC$TL_messageEntityMentionName);
        }
        return String.format("%1$s (@%2$s)", new Object[]{formatName, user.username});
    }

    public static boolean isContentUnread(Message message) {
        return message.media_unread;
    }

    public static boolean isForwardedMessage(Message message) {
        return ((message.flags & 4) == 0 || message.fwd_from == null) ? false : true;
    }

    public static boolean isGameMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaGame;
    }

    public static boolean isGifDocument(Document document) {
        return (document == null || document.thumb == null || document.mime_type == null || (!document.mime_type.equals("image/gif") && !isNewGifDocument(document))) ? false : true;
    }

    public static boolean isGifMessage(Message message) {
        return (message.media == null || message.media.document == null || !isGifDocument(message.media.document)) ? false : true;
    }

    public static boolean isImageWebDocument(TLRPC$TL_webDocument tLRPC$TL_webDocument) {
        return tLRPC$TL_webDocument != null && tLRPC$TL_webDocument.mime_type.startsWith("image/");
    }

    public static boolean isInvoiceMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaInvoice;
    }

    public static boolean isLiveLocationMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaGeoLive;
    }

    public static boolean isMaskDocument(Document document) {
        if (document == null) {
            return false;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if ((documentAttribute instanceof TLRPC$TL_documentAttributeSticker) && documentAttribute.mask) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMaskMessage(Message message) {
        return (message.media == null || message.media.document == null || !isMaskDocument(message.media.document)) ? false : true;
    }

    public static boolean isMediaEmpty(Message message) {
        return message == null || message.media == null || (message.media instanceof TLRPC$TL_messageMediaEmpty) || (message.media instanceof TLRPC$TL_messageMediaWebPage);
    }

    public static boolean isMegagroup(Message message) {
        return (message.flags & Integer.MIN_VALUE) != 0;
    }

    public static boolean isMusicDocument(Document document) {
        if (document == null) {
            return false;
        }
        int i = 0;
        while (i < document.attributes.size()) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                return !documentAttribute.voice;
            } else {
                i++;
            }
        }
        return false;
    }

    public static boolean isMusicMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? isMusicDocument(message.media.webpage.document) : (message.media == null || message.media.document == null || !isMusicDocument(message.media.document)) ? false : true;
    }

    public static boolean isNewGifDocument(Document document) {
        if (!(document == null || document.mime_type == null || !document.mime_type.equals(MimeTypes.VIDEO_MP4))) {
            boolean z = false;
            int i = 0;
            int i2 = 0;
            for (int i3 = 0; i3 < document.attributes.size(); i3++) {
                DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i3);
                if (documentAttribute instanceof TLRPC$TL_documentAttributeAnimated) {
                    z = true;
                } else if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                    i2 = documentAttribute.f10140w;
                    i = documentAttribute.f10140w;
                }
            }
            if (z && r5 <= 1280 && r4 <= 1280) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNewGifMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? isNewGifDocument(message.media.webpage.document) : (message.media == null || message.media.document == null || !isNewGifDocument(message.media.document)) ? false : true;
    }

    public static boolean isOut(Message message) {
        return message.out;
    }

    public static boolean isPhoto(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? message.media.webpage.photo instanceof TLRPC$TL_photo : message.media instanceof TLRPC$TL_messageMediaPhoto;
    }

    public static boolean isRoundVideoDocument(Document document) {
        if (document == null || document.mime_type == null || !document.mime_type.equals(MimeTypes.VIDEO_MP4)) {
            return false;
        }
        boolean z = false;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < document.attributes.size(); i3++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i3);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                i2 = documentAttribute.f10140w;
                i = documentAttribute.f10140w;
                z = documentAttribute.round_message;
            }
        }
        return z && i2 <= 1280 && i <= 1280;
    }

    public static boolean isRoundVideoMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? isRoundVideoDocument(message.media.webpage.document) : (message.media == null || message.media.document == null || !isRoundVideoDocument(message.media.document)) ? false : true;
    }

    public static boolean isSecretPhotoOrVideo(Message message) {
        return ((message instanceof TLRPC$TL_message) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)) && message.media.ttl_seconds != 0)) || ((message instanceof TLRPC$TL_message_secret) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || isRoundVideoMessage(message) || isVideoMessage(message)) && message.ttl > 0 && message.ttl <= 60));
    }

    public static boolean isStickerDocument(Document document) {
        if (document == null) {
            return false;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            if (((DocumentAttribute) document.attributes.get(i)) instanceof TLRPC$TL_documentAttributeSticker) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStickerMessage(Message message) {
        return (message.media == null || message.media.document == null || !isStickerDocument(message.media.document)) ? false : true;
    }

    public static boolean isUnread(Message message) {
        return message.unread;
    }

    public static boolean isVideoDocument(Document document) {
        boolean z = true;
        if (document == null) {
            return false;
        }
        int i = 0;
        int i2 = 0;
        boolean z2 = false;
        boolean z3 = false;
        for (int i3 = 0; i3 < document.attributes.size(); i3++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i3);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                if (documentAttribute.round_message) {
                    return false;
                }
                i2 = documentAttribute.f10140w;
                i = documentAttribute.f10139h;
                z2 = true;
            } else if (documentAttribute instanceof TLRPC$TL_documentAttributeAnimated) {
                z3 = true;
            }
        }
        if (z3 && (r4 > 1280 || r3 > 1280)) {
            z3 = false;
        }
        if (!z2 || r6) {
            z = false;
        }
        return z;
    }

    public static boolean isVideoMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? isVideoDocument(message.media.webpage.document) : (message.media == null || message.media.document == null || !isVideoDocument(message.media.document)) ? false : true;
    }

    public static boolean isVideoWebDocument(TLRPC$TL_webDocument tLRPC$TL_webDocument) {
        return tLRPC$TL_webDocument != null && tLRPC$TL_webDocument.mime_type.startsWith("video/");
    }

    public static boolean isVoiceDocument(Document document) {
        if (document == null) {
            return false;
        }
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                return documentAttribute.voice;
            }
        }
        return false;
    }

    public static boolean isVoiceMessage(Message message) {
        return message.media instanceof TLRPC$TL_messageMediaWebPage ? isVoiceDocument(message.media.webpage.document) : (message.media == null || message.media.document == null || !isVoiceDocument(message.media.document)) ? false : true;
    }

    public static boolean isVoiceWebDocument(TLRPC$TL_webDocument tLRPC$TL_webDocument) {
        return tLRPC$TL_webDocument != null && tLRPC$TL_webDocument.mime_type.equals("audio/ogg");
    }

    public static void setUnreadFlags(Message message, int i) {
        boolean z = true;
        message.unread = (i & 1) == 0;
        if ((i & 2) != 0) {
            z = false;
        }
        message.media_unread = z;
    }

    public static boolean shouldEncryptPhotoOrVideo(Message message) {
        return ((message instanceof TLRPC$TL_message) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || (message.media instanceof TLRPC$TL_messageMediaDocument)) && message.media.ttl_seconds != 0)) || ((message instanceof TLRPC$TL_message_secret) && (((message.media instanceof TLRPC$TL_messageMediaPhoto) || isVideoMessage(message)) && message.ttl > 0 && message.ttl <= 60));
    }

    public void applyNewText() {
        if (!TextUtils.isEmpty(this.messageOwner.message)) {
            User user = null;
            if (isFromUser()) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.from_id));
            }
            this.messageText = this.messageOwner.message;
            this.messageText = Emoji.replaceEmoji(this.messageText, (this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? Theme.chat_msgGameTextPaint : Theme.chat_msgTextPaint).getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            generateLayout(user);
        }
    }

    public void backupCaptionAndText() {
        this.messageTextBU = this.messageText;
        this.captionBU = this.caption;
        try {
            this.messageOwnerMessageBU = this.messageOwner.message;
        } catch (Exception e) {
        }
    }

    public boolean canDeleteMessage(Chat chat) {
        return this.eventId == 0 && canDeleteMessage(this.messageOwner, chat);
    }

    public boolean canEditMessage(Chat chat) {
        return canEditMessage(this.messageOwner, chat);
    }

    public boolean canEditMessageAnytime(Chat chat) {
        return canEditMessageAnytime(this.messageOwner, chat);
    }

    public boolean checkLayout() {
        if (this.type != 0 || this.messageOwner.to_id == null || this.messageText == null || this.messageText.length() == 0) {
            return false;
        }
        if (this.layoutCreated) {
            if (Math.abs(this.generatedWithMinSize - (AndroidUtilities.isTablet() ? AndroidUtilities.getMinTabletSide() : AndroidUtilities.displaySize.x)) > AndroidUtilities.dp(52.0f)) {
                this.layoutCreated = false;
            }
        }
        if (this.layoutCreated) {
            return false;
        }
        this.layoutCreated = true;
        User user = null;
        if (isFromUser()) {
            user = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.from_id));
        }
        this.messageText = Emoji.replaceEmoji(this.messageText, (this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? Theme.chat_msgGameTextPaint : Theme.chat_msgTextPaint).getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
        generateLayout(user);
        return true;
    }

    public void checkMediaExistance() {
        this.attachPathExists = false;
        this.mediaExists = false;
        File pathToMessage;
        if (this.type == 1) {
            if (FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, AndroidUtilities.getPhotoSize()) != null) {
                pathToMessage = FileLoader.getPathToMessage(this.messageOwner);
                if (isSecretPhoto()) {
                    this.mediaExists = new File(pathToMessage.getAbsolutePath() + ".enc").exists();
                }
                if (!this.mediaExists) {
                    this.mediaExists = pathToMessage.exists();
                }
            }
        } else if (this.type == 8 || this.type == 3 || this.type == 9 || this.type == 2 || this.type == 14 || this.type == 5) {
            if (this.messageOwner.attachPath != null && this.messageOwner.attachPath.length() > 0) {
                this.attachPathExists = new File(this.messageOwner.attachPath).exists();
            }
            if (!this.attachPathExists) {
                pathToMessage = FileLoader.getPathToMessage(this.messageOwner);
                if (this.type == 3 && isSecretPhoto()) {
                    this.mediaExists = new File(pathToMessage.getAbsolutePath() + ".enc").exists();
                }
                if (!this.mediaExists) {
                    this.mediaExists = pathToMessage.exists();
                }
            }
        } else {
            TLObject document = getDocument();
            if (document != null) {
                this.mediaExists = FileLoader.getPathToAttach(document).exists();
            } else if (this.type == 0) {
                document = FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, AndroidUtilities.getPhotoSize());
                if (document != null && document != null) {
                    this.mediaExists = FileLoader.getPathToAttach(document, true).exists();
                }
            }
        }
    }

    public void generateCaption() {
        if (this.caption == null && !isRoundVideo() && this.messageOwner.media != null && !TextUtils.isEmpty(this.messageOwner.media.caption)) {
            this.caption = Emoji.replaceEmoji(this.messageOwner.media.caption, Theme.chat_msgTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            if (containsUrls(this.caption)) {
                try {
                    Linkify.addLinks((Spannable) this.caption, 5);
                } catch (Throwable e) {
                    FileLog.m13728e(e);
                }
                addUsernamesAndHashtags(isOutOwner(), this.caption, true);
            }
        }
    }

    public void generateGameMessageText(User user) {
        if (user == null && this.messageOwner.from_id > 0) {
            TLObject user2 = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.from_id));
        }
        TLObject tLObject = null;
        if (!(this.replyMessageObject == null || this.replyMessageObject.messageOwner.media == null || this.replyMessageObject.messageOwner.media.game == null)) {
            tLObject = this.replyMessageObject.messageOwner.media.game;
        }
        if (tLObject != null) {
            if (user2 == null || user2.id != UserConfig.getClientUserId()) {
                this.messageText = replaceWithLink(LocaleController.formatString("ActionUserScoredInGame", R.string.ActionUserScoredInGame, LocaleController.formatPluralString("Points", this.messageOwner.action.score)), "un1", user2);
            } else {
                this.messageText = LocaleController.formatString("ActionYouScoredInGame", R.string.ActionYouScoredInGame, LocaleController.formatPluralString("Points", this.messageOwner.action.score));
            }
            this.messageText = replaceWithLink(this.messageText, "un2", tLObject);
        } else if (user2 == null || user2.id != UserConfig.getClientUserId()) {
            this.messageText = replaceWithLink(LocaleController.formatString("ActionUserScored", R.string.ActionUserScored, LocaleController.formatPluralString("Points", this.messageOwner.action.score)), "un1", user2);
        } else {
            this.messageText = LocaleController.formatString("ActionYouScored", R.string.ActionYouScored, LocaleController.formatPluralString("Points", this.messageOwner.action.score));
        }
    }

    public void generateLayout(User user) {
        if (this.type == 0 && this.messageOwner.to_id != null && !TextUtils.isEmpty(this.messageText)) {
            int i;
            Object obj;
            int size;
            int i2;
            int spanEnd;
            Object obj2;
            StaticLayout build;
            int lineCount;
            int ceil;
            int i3;
            float f;
            int i4;
            TextLayoutBlock textLayoutBlock;
            float f2;
            int lineStart;
            float lineLeft;
            float f3;
            int ceil2;
            float f4;
            int i5;
            int i6;
            int i7;
            Object obj3;
            float lineWidth;
            float f5;
            float max;
            generateLinkDescription();
            this.textLayoutBlocks = new ArrayList();
            this.textWidth = 0;
            if (this.messageOwner.send_state != 0) {
                for (i = 0; i < this.messageOwner.entities.size(); i++) {
                    if (!(this.messageOwner.entities.get(i) instanceof TLRPC$TL_inputMessageEntityMentionName)) {
                        obj = 1;
                        break;
                    }
                }
                obj = null;
            } else {
                obj = !this.messageOwner.entities.isEmpty() ? 1 : null;
            }
            Object obj4 = (obj == null && (this.eventId != 0 || (this.messageOwner instanceof TLRPC$TL_message_old) || (this.messageOwner instanceof TLRPC$TL_message_old2) || (this.messageOwner instanceof TLRPC$TL_message_old3) || (this.messageOwner instanceof TLRPC$TL_message_old4) || (this.messageOwner instanceof TLRPC$TL_messageForwarded_old) || (this.messageOwner instanceof TLRPC$TL_messageForwarded_old2) || (this.messageOwner instanceof TLRPC$TL_message_secret) || (this.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) || ((isOut() && this.messageOwner.send_state != 0) || this.messageOwner.id < 0 || (this.messageOwner.media instanceof TLRPC$TL_messageMediaUnsupported)))) ? 1 : null;
            if (obj4 != null) {
                addLinks(isOutOwner(), this.messageText);
            } else if ((this.messageText instanceof Spannable) && this.messageText.length() < Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                try {
                    Linkify.addLinks((Spannable) this.messageText, 4);
                } catch (Throwable th) {
                    FileLog.m13728e(th);
                }
            }
            Object obj5 = null;
            if (this.messageText instanceof Spannable) {
                Spannable spannable = (Spannable) this.messageText;
                size = this.messageOwner.entities.size();
                URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, this.messageText.length(), URLSpan.class);
                if (uRLSpanArr != null && uRLSpanArr.length > 0) {
                    obj5 = 1;
                }
                int i8 = 0;
                Object obj6 = obj5;
                while (i8 < size) {
                    MessageEntity messageEntity = (MessageEntity) this.messageOwner.entities.get(i8);
                    if (messageEntity.length > 0 && messageEntity.offset >= 0) {
                        if (messageEntity.offset >= this.messageOwner.message.length()) {
                            obj5 = obj6;
                        } else {
                            if (messageEntity.offset + messageEntity.length > this.messageOwner.message.length()) {
                                messageEntity.length = this.messageOwner.message.length() - messageEntity.offset;
                            }
                            if (((messageEntity instanceof TLRPC$TL_messageEntityBold) || (messageEntity instanceof TLRPC$TL_messageEntityItalic) || (messageEntity instanceof TLRPC$TL_messageEntityCode) || (messageEntity instanceof TLRPC$TL_messageEntityPre) || (messageEntity instanceof TLRPC$TL_messageEntityMentionName) || (messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName)) && uRLSpanArr != null && uRLSpanArr.length > 0) {
                                for (i2 = 0; i2 < uRLSpanArr.length; i2++) {
                                    if (uRLSpanArr[i2] != null) {
                                        int spanStart = spannable.getSpanStart(uRLSpanArr[i2]);
                                        spanEnd = spannable.getSpanEnd(uRLSpanArr[i2]);
                                        if ((messageEntity.offset <= spanStart && messageEntity.offset + messageEntity.length >= spanStart) || (messageEntity.offset <= spanEnd && messageEntity.offset + messageEntity.length >= spanEnd)) {
                                            spannable.removeSpan(uRLSpanArr[i2]);
                                            uRLSpanArr[i2] = null;
                                        }
                                    }
                                }
                            }
                            if (messageEntity instanceof TLRPC$TL_messageEntityBold) {
                                spannable.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                obj5 = obj6;
                            } else if (messageEntity instanceof TLRPC$TL_messageEntityItalic) {
                                spannable.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/ritalic.ttf")), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                obj5 = obj6;
                            } else if ((messageEntity instanceof TLRPC$TL_messageEntityCode) || (messageEntity instanceof TLRPC$TL_messageEntityPre)) {
                                spannable.setSpan(new URLSpanMono(spannable, messageEntity.offset, messageEntity.offset + messageEntity.length, isOutOwner()), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                obj5 = obj6;
                            } else if (messageEntity instanceof TLRPC$TL_messageEntityMentionName) {
                                spannable.setSpan(new URLSpanUserMention(TtmlNode.ANONYMOUS_REGION_ID + ((TLRPC$TL_messageEntityMentionName) messageEntity).user_id, isOutOwner()), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                obj5 = obj6;
                            } else if (messageEntity instanceof TLRPC$TL_inputMessageEntityMentionName) {
                                spannable.setSpan(new URLSpanUserMention(TtmlNode.ANONYMOUS_REGION_ID + ((TLRPC$TL_inputMessageEntityMentionName) messageEntity).user_id.user_id, isOutOwner()), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                obj5 = obj6;
                            } else if (obj4 == null) {
                                String substring = this.messageOwner.message.substring(messageEntity.offset, messageEntity.offset + messageEntity.length);
                                if (messageEntity instanceof TLRPC$TL_messageEntityBotCommand) {
                                    spannable.setSpan(new URLSpanBotCommand(substring, isOutOwner()), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                    obj5 = obj6;
                                } else if ((messageEntity instanceof TLRPC$TL_messageEntityHashtag) || (messageEntity instanceof TLRPC$TL_messageEntityMention)) {
                                    spannable.setSpan(new URLSpanNoUnderline(substring), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                    obj5 = obj6;
                                } else if (messageEntity instanceof TLRPC$TL_messageEntityEmail) {
                                    spannable.setSpan(new URLSpanReplacement("mailto:" + substring), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                    obj5 = obj6;
                                } else if (messageEntity instanceof TLRPC$TL_messageEntityUrl) {
                                    int i9;
                                    if (substring.toLowerCase().startsWith("http") || substring.toLowerCase().startsWith("tg://")) {
                                        spannable.setSpan(new URLSpan(substring), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                        i9 = 1;
                                    } else {
                                        spannable.setSpan(new URLSpan("http://" + substring), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                        i9 = 1;
                                    }
                                } else if (messageEntity instanceof TLRPC$TL_messageEntityTextUrl) {
                                    spannable.setSpan(new URLSpanReplacement(messageEntity.url), messageEntity.offset, messageEntity.length + messageEntity.offset, 33);
                                }
                            }
                        }
                        i8++;
                        obj6 = obj5;
                    }
                    obj5 = obj6;
                    i8++;
                    obj6 = obj5;
                }
                obj2 = obj6;
            } else {
                obj2 = null;
            }
            obj = (this.eventId != 0 || isOutOwner() || ((this.messageOwner.fwd_from == null || (this.messageOwner.fwd_from.saved_from_peer == null && this.messageOwner.fwd_from.from_id == 0 && this.messageOwner.fwd_from.channel_id == 0)) && (this.messageOwner.from_id <= 0 || (this.messageOwner.to_id.channel_id == 0 && this.messageOwner.to_id.chat_id == 0 && !(this.messageOwner.media instanceof TLRPC$TL_messageMediaGame) && !(this.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice))))) ? null : 1;
            this.generatedWithMinSize = AndroidUtilities.isTablet() ? AndroidUtilities.getMinTabletSide() : AndroidUtilities.displaySize.x;
            int i10 = this.generatedWithMinSize;
            float f6 = (obj == null && this.eventId == 0) ? 80.0f : 132.0f;
            i = i10 - AndroidUtilities.dp(f6);
            if ((user != null && user.bot) || ((isMegagroup() || !(this.messageOwner.fwd_from == null || this.messageOwner.fwd_from.channel_id == 0)) && !isOut())) {
                i -= AndroidUtilities.dp(20.0f);
            }
            i2 = this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? i - AndroidUtilities.dp(10.0f) : i;
            TextPaint textPaint = this.messageOwner.media instanceof TLRPC$TL_messageMediaGame ? Theme.chat_msgGameTextPaint : Theme.chat_msgTextPaint;
            if (obj2 != null) {
                try {
                    if (VERSION.SDK_INT >= 24) {
                        build = Builder.obtain(this.messageText, 0, this.messageText.length(), textPaint, i2).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(Alignment.ALIGN_NORMAL).build();
                        this.textHeight = build.getHeight();
                        lineCount = build.getLineCount();
                        ceil = (int) Math.ceil((double) (((float) lineCount) / 10.0f));
                        i3 = 0;
                        f = BitmapDescriptorFactory.HUE_RED;
                        i4 = 0;
                        while (i4 < ceil) {
                            i10 = Math.min(10, lineCount - i3);
                            textLayoutBlock = new TextLayoutBlock();
                            if (ceil != 1) {
                                textLayoutBlock.textLayout = build;
                                textLayoutBlock.textYOffset = BitmapDescriptorFactory.HUE_RED;
                                textLayoutBlock.charactersOffset = 0;
                                textLayoutBlock.height = this.textHeight;
                                f2 = f;
                            } else {
                                lineStart = build.getLineStart(i3);
                                size = build.getLineEnd((i3 + i10) - 1);
                                if (size >= lineStart) {
                                    i10 = i3;
                                } else {
                                    textLayoutBlock.charactersOffset = lineStart;
                                    textLayoutBlock.charactersEnd = size;
                                    if (obj2 != null) {
                                        try {
                                            if (VERSION.SDK_INT >= 24) {
                                                textLayoutBlock.textLayout = Builder.obtain(this.messageText, lineStart, size, textPaint, AndroidUtilities.dp(2.0f) + i2).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(Alignment.ALIGN_NORMAL).build();
                                                textLayoutBlock.textYOffset = (float) build.getLineTop(i3);
                                                if (i4 != 0) {
                                                    textLayoutBlock.height = (int) (textLayoutBlock.textYOffset - f);
                                                }
                                                textLayoutBlock.height = Math.max(textLayoutBlock.height, textLayoutBlock.textLayout.getLineBottom(textLayoutBlock.textLayout.getLineCount() - 1));
                                                f2 = textLayoutBlock.textYOffset;
                                                if (i4 == ceil - 1) {
                                                    i10 = Math.max(i10, textLayoutBlock.textLayout.getLineCount());
                                                    try {
                                                        this.textHeight = Math.max(this.textHeight, (int) (textLayoutBlock.textYOffset + ((float) textLayoutBlock.textLayout.getHeight())));
                                                    } catch (Throwable e) {
                                                        FileLog.m13728e(e);
                                                    }
                                                }
                                            }
                                        } catch (Throwable e2) {
                                            FileLog.m13728e(e2);
                                            i10 = i3;
                                        }
                                    }
                                    textLayoutBlock.textLayout = new StaticLayout(this.messageText, lineStart, size, textPaint, i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                                    textLayoutBlock.textYOffset = (float) build.getLineTop(i3);
                                    if (i4 != 0) {
                                        textLayoutBlock.height = (int) (textLayoutBlock.textYOffset - f);
                                    }
                                    textLayoutBlock.height = Math.max(textLayoutBlock.height, textLayoutBlock.textLayout.getLineBottom(textLayoutBlock.textLayout.getLineCount() - 1));
                                    f2 = textLayoutBlock.textYOffset;
                                    if (i4 == ceil - 1) {
                                        i10 = Math.max(i10, textLayoutBlock.textLayout.getLineCount());
                                        this.textHeight = Math.max(this.textHeight, (int) (textLayoutBlock.textYOffset + ((float) textLayoutBlock.textLayout.getHeight())));
                                    }
                                }
                                i4++;
                                i3 = i10;
                            }
                            this.textLayoutBlocks.add(textLayoutBlock);
                            try {
                                lineLeft = textLayoutBlock.textLayout.getLineLeft(i10 - 1);
                                if (i4 == 0) {
                                    this.textXOffset = lineLeft;
                                }
                                f3 = lineLeft;
                            } catch (Throwable e3) {
                                if (i4 == 0) {
                                    this.textXOffset = BitmapDescriptorFactory.HUE_RED;
                                }
                                FileLog.m13728e(e3);
                                f3 = BitmapDescriptorFactory.HUE_RED;
                            }
                            try {
                                lineLeft = textLayoutBlock.textLayout.getLineWidth(i10 - 1);
                            } catch (Throwable e32) {
                                Throwable th2 = e32;
                                lineLeft = BitmapDescriptorFactory.HUE_RED;
                                FileLog.m13728e(th2);
                            }
                            spanEnd = (int) Math.ceil((double) lineLeft);
                            if (i4 == ceil - 1) {
                                this.lastLineWidth = spanEnd;
                            }
                            ceil2 = (int) Math.ceil((double) (lineLeft + f3));
                            if (i10 <= 1) {
                                f3 = BitmapDescriptorFactory.HUE_RED;
                                f4 = BitmapDescriptorFactory.HUE_RED;
                                i5 = 0;
                                i6 = ceil2;
                                i7 = spanEnd;
                                obj3 = null;
                                while (i5 < i10) {
                                    try {
                                        lineWidth = textLayoutBlock.textLayout.getLineWidth(i5);
                                    } catch (Throwable e4) {
                                        FileLog.m13728e(e4);
                                        lineWidth = BitmapDescriptorFactory.HUE_RED;
                                    }
                                    f5 = lineWidth <= ((float) (i2 + 20)) ? (float) i2 : lineWidth;
                                    try {
                                        lineWidth = textLayoutBlock.textLayout.getLineLeft(i5);
                                    } catch (Throwable e42) {
                                        FileLog.m13728e(e42);
                                        lineWidth = BitmapDescriptorFactory.HUE_RED;
                                    }
                                    if (lineWidth <= BitmapDescriptorFactory.HUE_RED) {
                                        this.textXOffset = Math.min(this.textXOffset, lineWidth);
                                        textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 1);
                                        this.hasRtl = true;
                                    } else {
                                        textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 2);
                                    }
                                    if (obj3 == null && lineWidth == BitmapDescriptorFactory.HUE_RED) {
                                        try {
                                            if (textLayoutBlock.textLayout.getParagraphDirection(i5) == 1) {
                                                obj3 = 1;
                                            }
                                        } catch (Exception e5) {
                                            obj3 = 1;
                                        }
                                    }
                                    max = Math.max(f3, f5);
                                    f3 = Math.max(f4, f5 + lineWidth);
                                    i7 = Math.max(i7, (int) Math.ceil((double) f5));
                                    i5++;
                                    i6 = Math.max(i6, (int) Math.ceil((double) (f5 + lineWidth)));
                                    f4 = f3;
                                    f3 = max;
                                }
                                if (obj3 != null) {
                                    if (i4 == ceil - 1) {
                                        this.lastLineWidth = i7;
                                    }
                                    lineLeft = f3;
                                } else if (i4 != ceil - 1) {
                                    this.lastLineWidth = ceil2;
                                    lineLeft = f4;
                                } else {
                                    lineLeft = f4;
                                }
                                this.textWidth = Math.max(this.textWidth, (int) Math.ceil((double) lineLeft));
                            } else {
                                if (f3 <= BitmapDescriptorFactory.HUE_RED) {
                                    this.textXOffset = Math.min(this.textXOffset, f3);
                                    this.hasRtl = ceil == 1;
                                    textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 1);
                                } else {
                                    textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 2);
                                }
                                this.textWidth = Math.max(this.textWidth, Math.min(i2, spanEnd));
                            }
                            i10 += i3;
                            f = f2;
                            i4++;
                            i3 = i10;
                        }
                    }
                } catch (Throwable th3) {
                    FileLog.m13728e(th3);
                    return;
                }
            }
            build = new StaticLayout(this.messageText, textPaint, i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            this.textHeight = build.getHeight();
            lineCount = build.getLineCount();
            ceil = (int) Math.ceil((double) (((float) lineCount) / 10.0f));
            i3 = 0;
            f = BitmapDescriptorFactory.HUE_RED;
            i4 = 0;
            while (i4 < ceil) {
                i10 = Math.min(10, lineCount - i3);
                textLayoutBlock = new TextLayoutBlock();
                if (ceil != 1) {
                    lineStart = build.getLineStart(i3);
                    size = build.getLineEnd((i3 + i10) - 1);
                    if (size >= lineStart) {
                        textLayoutBlock.charactersOffset = lineStart;
                        textLayoutBlock.charactersEnd = size;
                        if (obj2 != null) {
                            if (VERSION.SDK_INT >= 24) {
                                textLayoutBlock.textLayout = Builder.obtain(this.messageText, lineStart, size, textPaint, AndroidUtilities.dp(2.0f) + i2).setBreakStrategy(1).setHyphenationFrequency(0).setAlignment(Alignment.ALIGN_NORMAL).build();
                                textLayoutBlock.textYOffset = (float) build.getLineTop(i3);
                                if (i4 != 0) {
                                    textLayoutBlock.height = (int) (textLayoutBlock.textYOffset - f);
                                }
                                textLayoutBlock.height = Math.max(textLayoutBlock.height, textLayoutBlock.textLayout.getLineBottom(textLayoutBlock.textLayout.getLineCount() - 1));
                                f2 = textLayoutBlock.textYOffset;
                                if (i4 == ceil - 1) {
                                    i10 = Math.max(i10, textLayoutBlock.textLayout.getLineCount());
                                    this.textHeight = Math.max(this.textHeight, (int) (textLayoutBlock.textYOffset + ((float) textLayoutBlock.textLayout.getHeight())));
                                }
                            }
                        }
                        textLayoutBlock.textLayout = new StaticLayout(this.messageText, lineStart, size, textPaint, i2, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                        textLayoutBlock.textYOffset = (float) build.getLineTop(i3);
                        if (i4 != 0) {
                            textLayoutBlock.height = (int) (textLayoutBlock.textYOffset - f);
                        }
                        textLayoutBlock.height = Math.max(textLayoutBlock.height, textLayoutBlock.textLayout.getLineBottom(textLayoutBlock.textLayout.getLineCount() - 1));
                        f2 = textLayoutBlock.textYOffset;
                        if (i4 == ceil - 1) {
                            i10 = Math.max(i10, textLayoutBlock.textLayout.getLineCount());
                            this.textHeight = Math.max(this.textHeight, (int) (textLayoutBlock.textYOffset + ((float) textLayoutBlock.textLayout.getHeight())));
                        }
                    } else {
                        i10 = i3;
                    }
                    i4++;
                    i3 = i10;
                } else {
                    textLayoutBlock.textLayout = build;
                    textLayoutBlock.textYOffset = BitmapDescriptorFactory.HUE_RED;
                    textLayoutBlock.charactersOffset = 0;
                    textLayoutBlock.height = this.textHeight;
                    f2 = f;
                }
                this.textLayoutBlocks.add(textLayoutBlock);
                lineLeft = textLayoutBlock.textLayout.getLineLeft(i10 - 1);
                if (i4 == 0) {
                    this.textXOffset = lineLeft;
                }
                f3 = lineLeft;
                lineLeft = textLayoutBlock.textLayout.getLineWidth(i10 - 1);
                spanEnd = (int) Math.ceil((double) lineLeft);
                if (i4 == ceil - 1) {
                    this.lastLineWidth = spanEnd;
                }
                ceil2 = (int) Math.ceil((double) (lineLeft + f3));
                if (i10 <= 1) {
                    if (f3 <= BitmapDescriptorFactory.HUE_RED) {
                        textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 2);
                    } else {
                        this.textXOffset = Math.min(this.textXOffset, f3);
                        if (ceil == 1) {
                        }
                        this.hasRtl = ceil == 1;
                        textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 1);
                    }
                    this.textWidth = Math.max(this.textWidth, Math.min(i2, spanEnd));
                } else {
                    f3 = BitmapDescriptorFactory.HUE_RED;
                    f4 = BitmapDescriptorFactory.HUE_RED;
                    i5 = 0;
                    i6 = ceil2;
                    i7 = spanEnd;
                    obj3 = null;
                    while (i5 < i10) {
                        lineWidth = textLayoutBlock.textLayout.getLineWidth(i5);
                        if (lineWidth <= ((float) (i2 + 20))) {
                        }
                        lineWidth = textLayoutBlock.textLayout.getLineLeft(i5);
                        if (lineWidth <= BitmapDescriptorFactory.HUE_RED) {
                            textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 2);
                        } else {
                            this.textXOffset = Math.min(this.textXOffset, lineWidth);
                            textLayoutBlock.directionFlags = (byte) (textLayoutBlock.directionFlags | 1);
                            this.hasRtl = true;
                        }
                        if (textLayoutBlock.textLayout.getParagraphDirection(i5) == 1) {
                            obj3 = 1;
                        }
                        max = Math.max(f3, f5);
                        f3 = Math.max(f4, f5 + lineWidth);
                        i7 = Math.max(i7, (int) Math.ceil((double) f5));
                        i5++;
                        i6 = Math.max(i6, (int) Math.ceil((double) (f5 + lineWidth)));
                        f4 = f3;
                        f3 = max;
                    }
                    if (obj3 != null) {
                        if (i4 == ceil - 1) {
                            this.lastLineWidth = i7;
                        }
                        lineLeft = f3;
                    } else if (i4 != ceil - 1) {
                        lineLeft = f4;
                    } else {
                        this.lastLineWidth = ceil2;
                        lineLeft = f4;
                    }
                    this.textWidth = Math.max(this.textWidth, (int) Math.ceil((double) lineLeft));
                }
                i10 += i3;
                f = f2;
                i4++;
                i3 = i10;
            }
        }
    }

    public void generateLinkDescription() {
        if (this.linkDescription == null) {
            if ((this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && (this.messageOwner.media.webpage instanceof TLRPC$TL_webPage) && this.messageOwner.media.webpage.description != null) {
                this.linkDescription = Factory.getInstance().newSpannable(this.messageOwner.media.webpage.description);
            } else if ((this.messageOwner.media instanceof TLRPC$TL_messageMediaGame) && this.messageOwner.media.game.description != null) {
                this.linkDescription = Factory.getInstance().newSpannable(this.messageOwner.media.game.description);
            } else if ((this.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) && this.messageOwner.media.description != null) {
                this.linkDescription = Factory.getInstance().newSpannable(this.messageOwner.media.description);
            }
            if (this.linkDescription != null) {
                if (containsUrls(this.linkDescription)) {
                    try {
                        Linkify.addLinks((Spannable) this.linkDescription, 1);
                    } catch (Throwable e) {
                        FileLog.m13728e(e);
                    }
                }
                this.linkDescription = Emoji.replaceEmoji(this.linkDescription, Theme.chat_msgTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            }
        }
    }

    public void generatePaymentSentMessageText(User user) {
        if (user == null) {
            user = MessagesController.getInstance().getUser(Integer.valueOf((int) getDialogId()));
        }
        String firstName = user != null ? UserObject.getFirstName(user) : TtmlNode.ANONYMOUS_REGION_ID;
        if (this.replyMessageObject == null || !(this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice)) {
            this.messageText = LocaleController.formatString("PaymentSuccessfullyPaidNoItem", R.string.PaymentSuccessfullyPaidNoItem, LocaleController.getInstance().formatCurrencyString(this.messageOwner.action.total_amount, this.messageOwner.action.currency), firstName);
        } else {
            this.messageText = LocaleController.formatString("PaymentSuccessfullyPaid", R.string.PaymentSuccessfullyPaid, LocaleController.getInstance().formatCurrencyString(this.messageOwner.action.total_amount, this.messageOwner.action.currency), firstName, this.replyMessageObject.messageOwner.media.title);
        }
    }

    public void generatePinMessageText(User user, Chat chat) {
        TLObject user2;
        if (user == null && chat == null) {
            if (this.messageOwner.from_id > 0) {
                user2 = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.from_id));
            }
            if (user2 == null) {
                TLObject chat2 = MessagesController.getInstance().getChat(Integer.valueOf(this.messageOwner.to_id.channel_id));
            }
        }
        CharSequence string;
        String str;
        if (this.replyMessageObject == null || (this.replyMessageObject.messageOwner instanceof TLRPC$TL_messageEmpty) || (this.replyMessageObject.messageOwner.action instanceof TLRPC$TL_messageActionHistoryClear)) {
            string = LocaleController.getString("ActionPinnedNoText", R.string.ActionPinnedNoText);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isMusic()) {
            string = LocaleController.getString("ActionPinnedMusic", R.string.ActionPinnedMusic);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isVideo()) {
            string = LocaleController.getString("ActionPinnedVideo", R.string.ActionPinnedVideo);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isGif()) {
            string = LocaleController.getString("ActionPinnedGif", R.string.ActionPinnedGif);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isVoice()) {
            string = LocaleController.getString("ActionPinnedVoice", R.string.ActionPinnedVoice);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isRoundVideo()) {
            string = LocaleController.getString("ActionPinnedRound", R.string.ActionPinnedRound);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.isSticker()) {
            string = LocaleController.getString("ActionPinnedSticker", R.string.ActionPinnedSticker);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
            string = LocaleController.getString("ActionPinnedFile", R.string.ActionPinnedFile);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) {
            string = LocaleController.getString("ActionPinnedGeo", R.string.ActionPinnedGeo);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive) {
            string = LocaleController.getString("ActionPinnedGeoLive", R.string.ActionPinnedGeoLive);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
            string = LocaleController.getString("ActionPinnedContact", R.string.ActionPinnedContact);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
            string = LocaleController.getString("ActionPinnedPhoto", R.string.ActionPinnedPhoto);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else if (this.replyMessageObject.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
            string = LocaleController.formatString("ActionPinnedGame", R.string.ActionPinnedGame, "🎮 " + this.replyMessageObject.messageOwner.media.game.title);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
            this.messageText = Emoji.replaceEmoji(this.messageText, Theme.chat_msgTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
        } else if (this.replyMessageObject.messageText == null || this.replyMessageObject.messageText.length() <= 0) {
            string = LocaleController.getString("ActionPinnedNoText", R.string.ActionPinnedNoText);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        } else {
            string = this.replyMessageObject.messageText;
            if (string.length() > 20) {
                string = string.subSequence(0, 20) + "...";
            }
            string = Emoji.replaceEmoji(string, Theme.chat_msgTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            string = LocaleController.formatString("ActionPinnedText", R.string.ActionPinnedText, string);
            str = "un1";
            if (user2 == null) {
                user2 = chat2;
            }
            this.messageText = replaceWithLink(string, str, user2);
        }
    }

    public void generateThumbs(boolean z) {
        int i;
        PhotoSize photoSize;
        int i2;
        PhotoSize photoSize2;
        if (this.messageOwner instanceof TLRPC$TL_messageService) {
            if (!(this.messageOwner.action instanceof TLRPC$TL_messageActionChatEditPhoto)) {
                return;
            }
            if (!z) {
                this.photoThumbs = new ArrayList(this.messageOwner.action.photo.sizes);
            } else if (this.photoThumbs != null && !this.photoThumbs.isEmpty()) {
                for (i = 0; i < this.photoThumbs.size(); i++) {
                    photoSize = (PhotoSize) this.photoThumbs.get(i);
                    for (i2 = 0; i2 < this.messageOwner.action.photo.sizes.size(); i2++) {
                        photoSize2 = (PhotoSize) this.messageOwner.action.photo.sizes.get(i2);
                        if (!(photoSize2 instanceof TLRPC$TL_photoSizeEmpty) && photoSize2.type.equals(photoSize.type)) {
                            photoSize.location = photoSize2.location;
                            break;
                        }
                    }
                }
            }
        } else if (this.messageOwner.media != null && !(this.messageOwner.media instanceof TLRPC$TL_messageMediaEmpty)) {
            if (this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                if (!z || (this.photoThumbs != null && this.photoThumbs.size() != this.messageOwner.media.photo.sizes.size())) {
                    this.photoThumbs = new ArrayList(this.messageOwner.media.photo.sizes);
                } else if (this.photoThumbs != null && !this.photoThumbs.isEmpty()) {
                    for (i = 0; i < this.photoThumbs.size(); i++) {
                        photoSize = (PhotoSize) this.photoThumbs.get(i);
                        for (i2 = 0; i2 < this.messageOwner.media.photo.sizes.size(); i2++) {
                            photoSize2 = (PhotoSize) this.messageOwner.media.photo.sizes.get(i2);
                            if (!(photoSize2 instanceof TLRPC$TL_photoSizeEmpty) && photoSize2.type.equals(photoSize.type)) {
                                photoSize.location = photoSize2.location;
                                break;
                            }
                        }
                    }
                }
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                if (!(this.messageOwner.media.document.thumb instanceof TLRPC$TL_photoSizeEmpty)) {
                    if (!z) {
                        this.photoThumbs = new ArrayList();
                        this.photoThumbs.add(this.messageOwner.media.document.thumb);
                    } else if (this.photoThumbs != null && !this.photoThumbs.isEmpty() && this.messageOwner.media.document.thumb != null) {
                        photoSize = (PhotoSize) this.photoThumbs.get(0);
                        photoSize.location = this.messageOwner.media.document.thumb.location;
                        photoSize.f10147w = this.messageOwner.media.document.thumb.f10147w;
                        photoSize.f10146h = this.messageOwner.media.document.thumb.f10146h;
                    }
                }
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                if (!(this.messageOwner.media.game.document == null || (this.messageOwner.media.game.document.thumb instanceof TLRPC$TL_photoSizeEmpty))) {
                    if (!z) {
                        this.photoThumbs = new ArrayList();
                        this.photoThumbs.add(this.messageOwner.media.game.document.thumb);
                    } else if (!(this.photoThumbs == null || this.photoThumbs.isEmpty() || this.messageOwner.media.game.document.thumb == null)) {
                        ((PhotoSize) this.photoThumbs.get(0)).location = this.messageOwner.media.game.document.thumb.location;
                    }
                }
                if (this.messageOwner.media.game.photo != null) {
                    if (!z || this.photoThumbs2 == null) {
                        this.photoThumbs2 = new ArrayList(this.messageOwner.media.game.photo.sizes);
                    } else if (!this.photoThumbs2.isEmpty()) {
                        for (i = 0; i < this.photoThumbs2.size(); i++) {
                            photoSize = (PhotoSize) this.photoThumbs2.get(i);
                            for (i2 = 0; i2 < this.messageOwner.media.game.photo.sizes.size(); i2++) {
                                photoSize2 = (PhotoSize) this.messageOwner.media.game.photo.sizes.get(i2);
                                if (!(photoSize2 instanceof TLRPC$TL_photoSizeEmpty) && photoSize2.type.equals(photoSize.type)) {
                                    photoSize.location = photoSize2.location;
                                    break;
                                }
                            }
                        }
                    }
                }
                if (this.photoThumbs == null && this.photoThumbs2 != null) {
                    this.photoThumbs = this.photoThumbs2;
                    this.photoThumbs2 = null;
                }
            } else if (!(this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage)) {
            } else {
                if (this.messageOwner.media.webpage.photo != null) {
                    if (!z || this.photoThumbs == null) {
                        this.photoThumbs = new ArrayList(this.messageOwner.media.webpage.photo.sizes);
                    } else if (!this.photoThumbs.isEmpty()) {
                        for (i = 0; i < this.photoThumbs.size(); i++) {
                            photoSize = (PhotoSize) this.photoThumbs.get(i);
                            for (i2 = 0; i2 < this.messageOwner.media.webpage.photo.sizes.size(); i2++) {
                                photoSize2 = (PhotoSize) this.messageOwner.media.webpage.photo.sizes.get(i2);
                                if (!(photoSize2 instanceof TLRPC$TL_photoSizeEmpty) && photoSize2.type.equals(photoSize.type)) {
                                    photoSize.location = photoSize2.location;
                                    break;
                                }
                            }
                        }
                    }
                } else if (this.messageOwner.media.webpage.document != null && !(this.messageOwner.media.webpage.document.thumb instanceof TLRPC$TL_photoSizeEmpty)) {
                    if (!z) {
                        this.photoThumbs = new ArrayList();
                        this.photoThumbs.add(this.messageOwner.media.webpage.document.thumb);
                    } else if (this.photoThumbs != null && !this.photoThumbs.isEmpty() && this.messageOwner.media.webpage.document.thumb != null) {
                        ((PhotoSize) this.photoThumbs.get(0)).location = this.messageOwner.media.webpage.document.thumb.location;
                    }
                }
            }
        }
    }

    public int getApproximateHeight() {
        int i = 0;
        int dp;
        if (this.type == 0) {
            int i2 = this.textHeight;
            dp = ((this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && (this.messageOwner.media.webpage instanceof TLRPC$TL_webPage)) ? AndroidUtilities.dp(100.0f) : 0;
            dp += i2;
            return isReply() ? dp + AndroidUtilities.dp(42.0f) : dp;
        } else if (this.type == 2) {
            return AndroidUtilities.dp(72.0f);
        } else {
            if (this.type == 12) {
                return AndroidUtilities.dp(71.0f);
            }
            if (this.type == 9) {
                return AndroidUtilities.dp(100.0f);
            }
            if (this.type == 4) {
                return AndroidUtilities.dp(114.0f);
            }
            if (this.type == 14) {
                return AndroidUtilities.dp(82.0f);
            }
            if (this.type == 10) {
                return AndroidUtilities.dp(30.0f);
            }
            if (this.type == 11) {
                return AndroidUtilities.dp(50.0f);
            }
            if (this.type == 5) {
                return AndroidUtilities.roundMessageSize;
            }
            if (this.type == 13) {
                int i3;
                float f = ((float) AndroidUtilities.displaySize.y) * 0.4f;
                float minTabletSide = AndroidUtilities.isTablet() ? ((float) AndroidUtilities.getMinTabletSide()) * 0.5f : ((float) AndroidUtilities.displaySize.x) * 0.5f;
                Iterator it = this.messageOwner.media.document.attributes.iterator();
                while (it.hasNext()) {
                    DocumentAttribute documentAttribute = (DocumentAttribute) it.next();
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeImageSize) {
                        i3 = documentAttribute.f10140w;
                        i = documentAttribute.f10139h;
                        dp = i3;
                        break;
                    }
                }
                dp = 0;
                if (dp == 0) {
                    i = (int) f;
                    dp = AndroidUtilities.dp(100.0f) + i;
                }
                if (((float) i) > f) {
                    i3 = (int) (((float) dp) * (f / ((float) i)));
                    dp = (int) f;
                    i = i3;
                } else {
                    i3 = dp;
                    dp = i;
                    i = i3;
                }
                if (((float) i) > minTabletSide) {
                    dp = (int) (((float) dp) * (minTabletSide / ((float) i)));
                }
                return dp + AndroidUtilities.dp(14.0f);
            }
            dp = AndroidUtilities.isTablet() ? (int) (((float) AndroidUtilities.getMinTabletSide()) * 0.7f) : (int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.7f);
            i = AndroidUtilities.dp(100.0f) + dp;
            if (dp > AndroidUtilities.getPhotoSize()) {
                dp = AndroidUtilities.getPhotoSize();
            }
            if (i > AndroidUtilities.getPhotoSize()) {
                i = AndroidUtilities.getPhotoSize();
            }
            PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.photoThumbs, AndroidUtilities.getPhotoSize());
            if (closestPhotoSizeWithSize != null) {
                dp = (int) (((float) closestPhotoSizeWithSize.f10146h) / (((float) closestPhotoSizeWithSize.f10147w) / ((float) dp)));
                if (dp == 0) {
                    dp = AndroidUtilities.dp(100.0f);
                }
                if (dp <= i) {
                    i = dp < AndroidUtilities.dp(120.0f) ? AndroidUtilities.dp(120.0f) : dp;
                }
                if (isSecretPhoto()) {
                    i = AndroidUtilities.isTablet() ? (int) (((float) AndroidUtilities.getMinTabletSide()) * 0.5f) : (int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.5f);
                }
            }
            return AndroidUtilities.dp(14.0f) + i;
        }
    }

    public int getChannelId() {
        return this.messageOwner.to_id != null ? this.messageOwner.to_id.channel_id : 0;
    }

    public long getDialogId() {
        return getDialogId(this.messageOwner);
    }

    public Document getDocument() {
        return this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage ? this.messageOwner.media.webpage.document : this.messageOwner.media != null ? this.messageOwner.media.document : null;
    }

    public String getDocumentName() {
        return this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument ? FileLoader.getDocumentFileName(this.messageOwner.media.document) : this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage ? FileLoader.getDocumentFileName(this.messageOwner.media.webpage.document) : TtmlNode.ANONYMOUS_REGION_ID;
    }

    public int getDuration() {
        Document document = this.type == 0 ? this.messageOwner.media.webpage.document : this.messageOwner.media.document;
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                return documentAttribute.duration;
            }
            if (documentAttribute instanceof TLRPC$TL_documentAttributeVideo) {
                return documentAttribute.duration;
            }
        }
        return 0;
    }

    public String getExtension() {
        String fileName = getFileName();
        int lastIndexOf = fileName.lastIndexOf(46);
        String str = null;
        if (lastIndexOf != -1) {
            str = fileName.substring(lastIndexOf + 1);
        }
        if (str == null || str.length() == 0) {
            str = this.messageOwner.media.document.mime_type;
        }
        if (str == null) {
            str = TtmlNode.ANONYMOUS_REGION_ID;
        }
        return str.toUpperCase();
    }

    public String getFileName() {
        if (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
            return FileLoader.getAttachFileName(this.messageOwner.media.document);
        }
        if (this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
            ArrayList arrayList = this.messageOwner.media.photo.sizes;
            if (arrayList.size() > 0) {
                TLObject closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize());
                if (closestPhotoSizeWithSize != null) {
                    return FileLoader.getAttachFileName(closestPhotoSizeWithSize);
                }
            }
        } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
            return FileLoader.getAttachFileName(this.messageOwner.media.webpage.document);
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    public int getFileType() {
        return isVideo() ? 2 : isVoice() ? 1 : this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument ? 3 : this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto ? 0 : 4;
    }

    public String getForwardedName() {
        if (this.messageOwner.fwd_from != null) {
            if (this.messageOwner.fwd_from.channel_id != 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.messageOwner.fwd_from.channel_id));
                if (chat != null) {
                    return chat.title;
                }
            } else if (this.messageOwner.fwd_from.from_id != 0) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.fwd_from.from_id));
                if (user != null) {
                    return UserObject.getUserName(user);
                }
            }
        }
        return null;
    }

    public int getFromId() {
        if (this.messageOwner.fwd_from == null || this.messageOwner.fwd_from.saved_from_peer == null) {
            if (this.messageOwner.from_id != 0) {
                return this.messageOwner.from_id;
            }
            if (this.messageOwner.post) {
                return this.messageOwner.to_id.channel_id;
            }
        } else if (this.messageOwner.fwd_from.saved_from_peer.user_id != 0) {
            return this.messageOwner.fwd_from.from_id != 0 ? this.messageOwner.fwd_from.from_id : this.messageOwner.fwd_from.saved_from_peer.user_id;
        } else {
            if (this.messageOwner.fwd_from.saved_from_peer.channel_id != 0) {
                return (!isSavedFromMegagroup() || this.messageOwner.fwd_from.from_id == 0) ? -this.messageOwner.fwd_from.saved_from_peer.channel_id : this.messageOwner.fwd_from.from_id;
            } else {
                if (this.messageOwner.fwd_from.saved_from_peer.chat_id != 0) {
                    return this.messageOwner.fwd_from.from_id != 0 ? this.messageOwner.fwd_from.from_id : -this.messageOwner.fwd_from.saved_from_peer.chat_id;
                }
            }
        }
        return 0;
    }

    public long getGroupId() {
        return this.localGroupId != 0 ? this.localGroupId : this.messageOwner.grouped_id;
    }

    public int getId() {
        return this.messageOwner.id;
    }

    public long getIdWithChannel() {
        long j = (long) this.messageOwner.id;
        return (this.messageOwner.to_id == null || this.messageOwner.to_id.channel_id == 0) ? j : j | (((long) this.messageOwner.to_id.channel_id) << 32);
    }

    public InputStickerSet getInputStickerSet() {
        return getInputStickerSet(this.messageOwner);
    }

    public String getMimeType() {
        if (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
            return this.messageOwner.media.document.mime_type;
        }
        if (this.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) {
            TLRPC$TL_webDocument tLRPC$TL_webDocument = ((TLRPC$TL_messageMediaInvoice) this.messageOwner.media).photo;
            if (tLRPC$TL_webDocument != null) {
                return tLRPC$TL_webDocument.mime_type;
            }
        } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
            return "image/jpeg";
        } else {
            if (this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) {
                if (this.messageOwner.media.webpage.document != null) {
                    return this.messageOwner.media.document.mime_type;
                }
                if (this.messageOwner.media.webpage.photo != null) {
                    return "image/jpeg";
                }
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    public String getMusicAuthor() {
        return getMusicAuthor(true);
    }

    public String getMusicAuthor(boolean z) {
        int i = 0;
        Document document = this.type == 0 ? this.messageOwner.media.webpage.document : this.messageOwner.media.document;
        int i2 = 0;
        while (i < document.attributes.size()) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                if (documentAttribute.voice) {
                    i2 = 1;
                } else {
                    String str = documentAttribute.performer;
                    return (TextUtils.isEmpty(str) && z) ? LocaleController.getString("AudioUnknownArtist", R.string.AudioUnknownArtist) : str;
                }
            } else if ((documentAttribute instanceof TLRPC$TL_documentAttributeVideo) && documentAttribute.round_message) {
                i2 = 1;
            }
            if (i2 != 0) {
                if (!z) {
                    return null;
                }
                if (isOutOwner() || (this.messageOwner.fwd_from != null && this.messageOwner.fwd_from.from_id == UserConfig.getClientUserId())) {
                    return LocaleController.getString("FromYou", R.string.FromYou);
                }
                Chat chat;
                User user;
                if (this.messageOwner.fwd_from != null && this.messageOwner.fwd_from.channel_id != 0) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(this.messageOwner.fwd_from.channel_id));
                    user = null;
                } else if (this.messageOwner.fwd_from != null && this.messageOwner.fwd_from.from_id != 0) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.fwd_from.from_id));
                    chat = null;
                } else if (this.messageOwner.from_id < 0) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(-this.messageOwner.from_id));
                    user = null;
                } else {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(this.messageOwner.from_id));
                    chat = null;
                }
                if (user != null) {
                    return UserObject.getUserName(user);
                }
                if (chat != null) {
                    return chat.title;
                }
            }
            i++;
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    public String getMusicTitle() {
        return getMusicTitle(true);
    }

    public String getMusicTitle(boolean z) {
        Document document = this.type == 0 ? this.messageOwner.media.webpage.document : this.messageOwner.media.document;
        int i = 0;
        while (i < document.attributes.size()) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeAudio) {
                if (documentAttribute.voice) {
                    return !z ? null : LocaleController.formatDateAudio((long) this.messageOwner.date);
                } else {
                    String str = documentAttribute.title;
                    if (str != null && str.length() != 0) {
                        return str;
                    }
                    str = FileLoader.getDocumentFileName(document);
                    return (TextUtils.isEmpty(str) && z) ? LocaleController.getString("AudioUnknownTitle", R.string.AudioUnknownTitle) : str;
                }
            } else if ((documentAttribute instanceof TLRPC$TL_documentAttributeVideo) && documentAttribute.round_message) {
                return LocaleController.formatDateAudio((long) this.messageOwner.date);
            } else {
                i++;
            }
        }
        return TtmlNode.ANONYMOUS_REGION_ID;
    }

    public int getSecretTimeLeft() {
        return this.messageOwner.destroyTime != 0 ? Math.max(0, this.messageOwner.destroyTime - ConnectionsManager.getInstance().getCurrentTime()) : this.messageOwner.ttl;
    }

    public String getSecretTimeString() {
        if (!isSecretMedia()) {
            return null;
        }
        int secretTimeLeft = getSecretTimeLeft();
        return secretTimeLeft < 60 ? secretTimeLeft + "s" : (secretTimeLeft / 60) + "m";
    }

    public int getSize() {
        return getMessageSize(this.messageOwner);
    }

    public String getStickerEmoji() {
        int i = 0;
        while (i < this.messageOwner.media.document.attributes.size()) {
            DocumentAttribute documentAttribute = (DocumentAttribute) this.messageOwner.media.document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                return (documentAttribute.alt == null || documentAttribute.alt.length() <= 0) ? null : documentAttribute.alt;
            } else {
                i++;
            }
        }
        return null;
    }

    public String getStrickerChar() {
        if (!(this.messageOwner.media == null || this.messageOwner.media.document == null)) {
            Iterator it = this.messageOwner.media.document.attributes.iterator();
            while (it.hasNext()) {
                DocumentAttribute documentAttribute = (DocumentAttribute) it.next();
                if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                    return documentAttribute.alt;
                }
            }
        }
        return null;
    }

    public int getUnradFlags() {
        return getUnreadFlags(this.messageOwner);
    }

    public ArrayList<MessageObject> getWebPagePhotos(ArrayList<MessageObject> arrayList, ArrayList<PageBlock> arrayList2) {
        TLRPC$WebPage tLRPC$WebPage = this.messageOwner.media.webpage;
        if (arrayList == null) {
            arrayList = new ArrayList();
        }
        if (tLRPC$WebPage.cached_page != null) {
            ArrayList arrayList3;
            if (arrayList2 == null) {
                arrayList3 = tLRPC$WebPage.cached_page.blocks;
            }
            for (int i = 0; i < arrayList3.size(); i++) {
                PageBlock pageBlock = (PageBlock) arrayList3.get(i);
                int i2;
                if (pageBlock instanceof TLRPC$TL_pageBlockSlideshow) {
                    TLRPC$TL_pageBlockSlideshow tLRPC$TL_pageBlockSlideshow = (TLRPC$TL_pageBlockSlideshow) pageBlock;
                    for (i2 = 0; i2 < tLRPC$TL_pageBlockSlideshow.items.size(); i2++) {
                        arrayList.add(getMessageObjectForBlock(tLRPC$WebPage, (PageBlock) tLRPC$TL_pageBlockSlideshow.items.get(i2)));
                    }
                } else if (pageBlock instanceof TLRPC$TL_pageBlockCollage) {
                    TLRPC$TL_pageBlockCollage tLRPC$TL_pageBlockCollage = (TLRPC$TL_pageBlockCollage) pageBlock;
                    for (i2 = 0; i2 < tLRPC$TL_pageBlockCollage.items.size(); i2++) {
                        arrayList.add(getMessageObjectForBlock(tLRPC$WebPage, (PageBlock) tLRPC$TL_pageBlockCollage.items.get(i2)));
                    }
                }
            }
        }
        return arrayList;
    }

    public boolean hasPhotoStickers() {
        return (this.messageOwner.media == null || this.messageOwner.media.photo == null || !this.messageOwner.media.photo.has_stickers) ? false : true;
    }

    public boolean hasValidGroupId() {
        return (getGroupId() == 0 || this.photoThumbs == null || this.photoThumbs.isEmpty()) ? false : true;
    }

    public boolean hasValidReplyMessageObject() {
        return (this.replyMessageObject == null || (this.replyMessageObject.messageOwner instanceof TLRPC$TL_messageEmpty) || (this.replyMessageObject.messageOwner.action instanceof TLRPC$TL_messageActionHistoryClear)) ? false : true;
    }

    public boolean isContentUnread() {
        return this.messageOwner.media_unread;
    }

    public boolean isForwarded() {
        return isForwardedMessage(this.messageOwner);
    }

    public boolean isFromUser() {
        return this.messageOwner.from_id > 0 && !this.messageOwner.post;
    }

    public boolean isGame() {
        return isGameMessage(this.messageOwner);
    }

    public boolean isGif() {
        return isGifMessage(this.messageOwner);
    }

    public boolean isInvoice() {
        return isInvoiceMessage(this.messageOwner);
    }

    public boolean isLiveLocation() {
        return isLiveLocationMessage(this.messageOwner);
    }

    public boolean isMask() {
        return isMaskMessage(this.messageOwner);
    }

    public boolean isMediaEmpty() {
        return isMediaEmpty(this.messageOwner);
    }

    public boolean isMegagroup() {
        return isMegagroup(this.messageOwner);
    }

    public boolean isMusic() {
        return isMusicMessage(this.messageOwner);
    }

    public boolean isNewGif() {
        return this.messageOwner.media != null && isNewGifDocument(this.messageOwner.media.document);
    }

    public boolean isOut() {
        return this.messageOwner.out;
    }

    public boolean isOutOwner() {
        if (!this.messageOwner.out || this.messageOwner.from_id <= 0 || this.messageOwner.post) {
            return false;
        }
        if (this.messageOwner.fwd_from == null) {
            return true;
        }
        int clientUserId = UserConfig.getClientUserId();
        return getDialogId() == ((long) clientUserId) ? this.messageOwner.fwd_from.from_id == clientUserId || (this.messageOwner.fwd_from.saved_from_peer != null && this.messageOwner.fwd_from.saved_from_peer.user_id == clientUserId) : this.messageOwner.fwd_from.saved_from_peer == null || this.messageOwner.fwd_from.saved_from_peer.user_id == clientUserId;
    }

    public boolean isReply() {
        return (this.replyMessageObject == null || !(this.replyMessageObject.messageOwner instanceof TLRPC$TL_messageEmpty)) && !((this.messageOwner.reply_to_msg_id == 0 && this.messageOwner.reply_to_random_id == 0) || (this.messageOwner.flags & 8) == 0);
    }

    public boolean isRoundVideo() {
        if (this.isRoundVideoCached == 0) {
            int i = (this.type == 5 || isRoundVideoMessage(this.messageOwner)) ? 1 : 2;
            this.isRoundVideoCached = i;
        }
        return this.isRoundVideoCached == 1;
    }

    public boolean isSavedFromMegagroup() {
        return (this.messageOwner.fwd_from == null || this.messageOwner.fwd_from.saved_from_peer == null || this.messageOwner.fwd_from.saved_from_peer.channel_id == 0) ? false : ChatObject.isMegagroup(MessagesController.getInstance().getChat(Integer.valueOf(this.messageOwner.fwd_from.saved_from_peer.channel_id)));
    }

    public boolean isSecretMedia() {
        return ((this.messageOwner instanceof TLRPC$TL_message) && (((this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument)) && this.messageOwner.media.ttl_seconds != 0)) || ((this.messageOwner instanceof TLRPC$TL_message_secret) && (((this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) && this.messageOwner.ttl > 0 && this.messageOwner.ttl <= 60) || isVoice() || isRoundVideo() || isVideo()));
    }

    public boolean isSecretPhoto() {
        return ((this.messageOwner instanceof TLRPC$TL_message) && (((this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument)) && this.messageOwner.media.ttl_seconds != 0)) || ((this.messageOwner instanceof TLRPC$TL_message_secret) && ((((this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) || isVideo()) && this.messageOwner.ttl > 0 && this.messageOwner.ttl <= 60) || isRoundVideo()));
    }

    public boolean isSendError() {
        return this.messageOwner.send_state == 2 && this.messageOwner.id < 0;
    }

    public boolean isSending() {
        return this.messageOwner.send_state == 1 && this.messageOwner.id < 0;
    }

    public boolean isSent() {
        return this.messageOwner.send_state == 0 || this.messageOwner.id > 0;
    }

    public boolean isSticker() {
        return this.type != 1000 ? this.type == 13 : isStickerMessage(this.messageOwner);
    }

    public boolean isUnread() {
        return this.messageOwner.unread;
    }

    public boolean isVideo() {
        return isVideoMessage(this.messageOwner);
    }

    public boolean isVoice() {
        return isVoiceMessage(this.messageOwner);
    }

    public boolean isWebpageDocument() {
        return (!(this.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) || this.messageOwner.media.webpage.document == null || isGifDocument(this.messageOwner.media.webpage.document)) ? false : true;
    }

    public void measureInlineBotButtons() {
        this.wantedBotKeyboardWidth = 0;
        if (this.messageOwner.reply_markup instanceof TLRPC$TL_replyInlineMarkup) {
            Theme.createChatResources(null, true);
            if (this.botButtonsLayout == null) {
                this.botButtonsLayout = new StringBuilder();
            } else {
                this.botButtonsLayout.setLength(0);
            }
            for (int i = 0; i < this.messageOwner.reply_markup.rows.size(); i++) {
                TLRPC$TL_keyboardButtonRow tLRPC$TL_keyboardButtonRow = (TLRPC$TL_keyboardButtonRow) this.messageOwner.reply_markup.rows.get(i);
                int size = tLRPC$TL_keyboardButtonRow.buttons.size();
                int i2 = 0;
                int i3 = 0;
                while (i2 < size) {
                    int max;
                    KeyboardButton keyboardButton = (KeyboardButton) tLRPC$TL_keyboardButtonRow.buttons.get(i2);
                    this.botButtonsLayout.append(i).append(i2);
                    CharSequence replaceEmoji = (!(keyboardButton instanceof TLRPC$TL_keyboardButtonBuy) || (this.messageOwner.media.flags & 4) == 0) ? Emoji.replaceEmoji(keyboardButton.text, Theme.chat_msgBotButtonPaint.getFontMetricsInt(), AndroidUtilities.dp(15.0f), false) : LocaleController.getString("PaymentReceipt", R.string.PaymentReceipt);
                    StaticLayout staticLayout = new StaticLayout(replaceEmoji, Theme.chat_msgBotButtonPaint, AndroidUtilities.dp(2000.0f), Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    if (staticLayout.getLineCount() > 0) {
                        float lineWidth = staticLayout.getLineWidth(0);
                        float lineLeft = staticLayout.getLineLeft(0);
                        max = Math.max(i3, ((int) Math.ceil((double) (lineLeft < lineWidth ? lineWidth - lineLeft : lineWidth))) + AndroidUtilities.dp(4.0f));
                    } else {
                        max = i3;
                    }
                    i2++;
                    i3 = max;
                }
                this.wantedBotKeyboardWidth = Math.max(this.wantedBotKeyboardWidth, ((AndroidUtilities.dp(12.0f) + i3) * size) + (AndroidUtilities.dp(5.0f) * (size - 1)));
            }
        }
    }

    public boolean needDrawAvatar() {
        return (!isFromUser() && this.eventId == 0 && (this.messageOwner.fwd_from == null || this.messageOwner.fwd_from.saved_from_peer == null)) ? false : true;
    }

    public boolean needDrawForwarded() {
        return ((this.messageOwner.flags & 4) == 0 || this.messageOwner.fwd_from == null || this.messageOwner.fwd_from.saved_from_peer != null || ((long) UserConfig.getClientUserId()) == getDialogId()) ? false : true;
    }

    public CharSequence replaceWithLink(CharSequence charSequence, String str, ArrayList<Integer> arrayList, AbstractMap<Integer, User> abstractMap) {
        if (TextUtils.indexOf(charSequence, str) < 0) {
            return charSequence;
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(TtmlNode.ANONYMOUS_REGION_ID);
        for (int i = 0; i < arrayList.size(); i++) {
            User user = null;
            if (abstractMap != null) {
                user = (User) abstractMap.get(arrayList.get(i));
            }
            if (user == null) {
                user = MessagesController.getInstance().getUser((Integer) arrayList.get(i));
            }
            if (user != null) {
                Object userName = UserObject.getUserName(user);
                int length = spannableStringBuilder.length();
                if (spannableStringBuilder.length() != 0) {
                    spannableStringBuilder.append(", ");
                }
                spannableStringBuilder.append(userName);
                spannableStringBuilder.setSpan(new URLSpanNoUnderlineBold(TtmlNode.ANONYMOUS_REGION_ID + user.id), length, userName.length() + length, 33);
            }
        }
        return TextUtils.replace(charSequence, new String[]{str}, new CharSequence[]{spannableStringBuilder});
    }

    public CharSequence replaceWithLink(CharSequence charSequence, String str, TLObject tLObject) {
        int indexOf = TextUtils.indexOf(charSequence, str);
        if (indexOf < 0) {
            return charSequence;
        }
        String userName;
        String str2;
        if (tLObject instanceof User) {
            userName = UserObject.getUserName((User) tLObject);
            str2 = TtmlNode.ANONYMOUS_REGION_ID + ((User) tLObject).id;
        } else if (tLObject instanceof Chat) {
            userName = ((Chat) tLObject).title;
            str2 = TtmlNode.ANONYMOUS_REGION_ID + (-((Chat) tLObject).id);
        } else if (tLObject instanceof TLRPC$TL_game) {
            userName = ((TLRPC$TL_game) tLObject).title;
            str2 = "game";
        } else {
            userName = TtmlNode.ANONYMOUS_REGION_ID;
            str2 = "0";
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(TextUtils.replace(charSequence, new String[]{str}, new String[]{userName.replace('\n', ' ')}));
        spannableStringBuilder.setSpan(new URLSpanNoUnderlineBold(TtmlNode.ANONYMOUS_REGION_ID + str2), indexOf, r3.length() + indexOf, 33);
        return spannableStringBuilder;
    }

    public void restoreCaptionAndText() {
        if (!TextUtils.isEmpty(this.messageTextBU)) {
            this.messageText = this.messageTextBU;
        }
        if (!TextUtils.isEmpty(this.captionBU)) {
            this.caption = this.captionBU;
        }
        try {
            if (!TextUtils.isEmpty(this.messageOwnerMessageBU)) {
                this.messageOwner.message = this.messageOwnerMessageBU;
            }
        } catch (Exception e) {
        }
    }

    public void setContentIsRead() {
        this.messageOwner.media_unread = false;
    }

    public void setIsRead() {
        this.messageOwner.unread = false;
    }

    public void setType() {
        int i = this.type;
        if ((this.messageOwner instanceof TLRPC$TL_message) || (this.messageOwner instanceof TLRPC$TL_messageForwarded_old2)) {
            if (isMediaEmpty()) {
                this.type = 0;
                if (TextUtils.isEmpty(this.messageText) && this.eventId == 0) {
                    this.messageText = "Empty message";
                }
            } else if (this.messageOwner.media.ttl_seconds != 0 && ((this.messageOwner.media.photo instanceof TLRPC$TL_photoEmpty) || (this.messageOwner.media.document instanceof TLRPC$TL_documentEmpty))) {
                this.contentType = 1;
                this.type = 10;
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaPhoto) {
                this.type = 1;
            } else if ((this.messageOwner.media instanceof TLRPC$TL_messageMediaGeo) || (this.messageOwner.media instanceof TLRPC$TL_messageMediaVenue) || (this.messageOwner.media instanceof TLRPC$TL_messageMediaGeoLive)) {
                this.type = 4;
            } else if (isRoundVideo()) {
                this.type = 5;
            } else if (isVideo()) {
                this.type = 3;
            } else if (isVoice()) {
                this.type = 2;
            } else if (isMusic()) {
                this.type = 14;
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaContact) {
                this.type = 12;
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaUnsupported) {
                this.type = 0;
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaDocument) {
                if (this.messageOwner.media.document == null || this.messageOwner.media.document.mime_type == null) {
                    this.type = 9;
                } else if (isGifDocument(this.messageOwner.media.document)) {
                    this.type = 8;
                } else if (this.messageOwner.media.document.mime_type.equals("image/webp") && isSticker()) {
                    this.type = 13;
                } else {
                    this.type = 9;
                }
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaGame) {
                this.type = 0;
            } else if (this.messageOwner.media instanceof TLRPC$TL_messageMediaInvoice) {
                this.type = 0;
            }
        } else if (this.messageOwner instanceof TLRPC$TL_messageService) {
            if (this.messageOwner.action instanceof TLRPC$TL_messageActionLoginUnknownLocation) {
                this.type = 0;
            } else if ((this.messageOwner.action instanceof TLRPC$TL_messageActionChatEditPhoto) || (this.messageOwner.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto)) {
                this.contentType = 1;
                this.type = 11;
            } else if (this.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) {
                if ((this.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionScreenshotMessages) || (this.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) {
                    this.contentType = 1;
                    this.type = 10;
                } else {
                    this.contentType = -1;
                    this.type = -1;
                }
            } else if (this.messageOwner.action instanceof TLRPC$TL_messageActionHistoryClear) {
                this.contentType = -1;
                this.type = -1;
            } else if (this.messageOwner.action instanceof TLRPC$TL_messageActionPhoneCall) {
                this.type = 16;
            } else {
                this.contentType = 1;
                this.type = 10;
            }
        }
        if (i != 1000 && i != this.type) {
            generateThumbs(false);
        }
    }

    public boolean shouldEncryptPhotoOrVideo() {
        return shouldEncryptPhotoOrVideo(this.messageOwner);
    }
}
