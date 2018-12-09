package org.telegram.tgnet;

import com.google.p098a.p099a.C1662c;
import java.util.ArrayList;
import java.util.HashMap;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.MimeTypes;

public class TLRPC {
    public static final int CHAT_FLAG_IS_PUBLIC = 64;
    public static final int LAYER = 73;
    public static final int MESSAGE_FLAG_EDITED = 32768;
    public static final int MESSAGE_FLAG_FWD = 4;
    public static final int MESSAGE_FLAG_HAS_BOT_ID = 2048;
    public static final int MESSAGE_FLAG_HAS_ENTITIES = 128;
    public static final int MESSAGE_FLAG_HAS_FROM_ID = 256;
    public static final int MESSAGE_FLAG_HAS_MARKUP = 64;
    public static final int MESSAGE_FLAG_HAS_MEDIA = 512;
    public static final int MESSAGE_FLAG_HAS_VIEWS = 1024;
    public static final int MESSAGE_FLAG_MEGAGROUP = Integer.MIN_VALUE;
    public static final int MESSAGE_FLAG_REPLY = 8;
    public static final int USER_FLAG_ACCESS_HASH = 1;
    public static final int USER_FLAG_FIRST_NAME = 2;
    public static final int USER_FLAG_LAST_NAME = 4;
    public static final int USER_FLAG_PHONE = 16;
    public static final int USER_FLAG_PHOTO = 32;
    public static final int USER_FLAG_STATUS = 64;
    public static final int USER_FLAG_UNUSED = 128;
    public static final int USER_FLAG_UNUSED2 = 256;
    public static final int USER_FLAG_UNUSED3 = 512;
    public static final int USER_FLAG_USERNAME = 8;

    public static class Message extends TLObject {
        public MessageAction action;
        public String attachPath = TtmlNode.ANONYMOUS_REGION_ID;
        public int date;
        public int destroyTime;
        public long dialog_id;
        public int edit_date;
        public ArrayList<MessageEntity> entities = new ArrayList();
        public int flags;
        public int from_id;
        public MessageFwdHeader fwd_from;
        public int fwd_msg_id = 0;
        public long grouped_id;
        public int id;
        public int layer;
        public int local_id = 0;
        public MessageMedia media;
        public boolean media_unread;
        public boolean mentioned;
        public String message;
        public boolean out;
        public HashMap<String, String> params;
        public boolean post;
        public String post_author;
        public long random_id;
        public Message replyMessage;
        public ReplyMarkup reply_markup;
        public int reply_to_msg_id;
        public long reply_to_random_id;
        public int send_state = 0;
        public int seq_in;
        public int seq_out;
        public boolean silent;
        public Peer to_id;
        public int ttl;
        public boolean unread;
        public int via_bot_id;
        public String via_bot_name;
        public int views;
        public boolean with_my_score;

        public static Message TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Message message = null;
            switch (i) {
                case -2082087340:
                    message = new TLRPC$TL_messageEmpty();
                    break;
                case -1864508399:
                    message = new TLRPC$TL_message_layer72();
                    break;
                case -1642487306:
                    message = new TLRPC$TL_messageService();
                    break;
                case -1618124613:
                    message = new TLRPC$TL_messageService_old();
                    break;
                case -1553471722:
                    message = new TLRPC$TL_messageForwarded_old2();
                    break;
                case -1481959023:
                    message = new TLRPC$TL_message_old3();
                    break;
                case -1066691065:
                    message = new TLRPC$TL_messageService_layer48();
                    break;
                case -1063525281:
                    message = new TLRPC$TL_message_layer68();
                    break;
                case -1023016155:
                    message = new TLRPC$TL_message_old4();
                    break;
                case -913120932:
                    message = new TLRPC$TL_message_layer47();
                    break;
                case -260565816:
                    message = new TLRPC$TL_message_old5();
                    break;
                case 99903492:
                    message = new TLRPC$TL_messageForwarded_old();
                    break;
                case 495384334:
                    message = new TLRPC$TL_messageService_old2();
                    break;
                case 585853626:
                    message = new TLRPC$TL_message_old();
                    break;
                case 736885382:
                    message = new TLRPC$TL_message_old6();
                    break;
                case 1157215293:
                    message = new TLRPC$TL_message();
                    break;
                case 1431655928:
                    message = new TLRPC$TL_message_secret_old();
                    break;
                case 1431655929:
                    message = new TLRPC$TL_message_secret_layer72();
                    break;
                case 1431655930:
                    message = new TLRPC$TL_message_secret();
                    break;
                case 1450613171:
                    message = new TLRPC$TL_message_old2();
                    break;
                case 1537633299:
                    message = new TLRPC$TL_message_old7();
                    break;
            }
            if (message == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Message", new Object[]{Integer.valueOf(i)}));
            }
            if (message != null) {
                message.readParams(abstractSerializedData, z);
            }
            return message;
        }
    }

    public static abstract class Audio extends TLObject {
        public long access_hash;
        public int date;
        public int dc_id;
        public int duration;
        public long id;
        public byte[] iv;
        public byte[] key;
        public String mime_type;
        public int size;
        public int user_id;

        public static Audio TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Audio audio = null;
            switch (i) {
                case -945003370:
                    audio = new TL_audio_old2();
                    break;
                case -102543275:
                    audio = new TL_audio_layer45();
                    break;
                case 1114908135:
                    audio = new TL_audio_old();
                    break;
                case 1431655926:
                    audio = new TL_audioEncrypted();
                    break;
                case 1483311320:
                    audio = new TL_audioEmpty_layer45();
                    break;
            }
            if (audio == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Audio", new Object[]{Integer.valueOf(i)}));
            }
            if (audio != null) {
                audio.readParams(abstractSerializedData, z);
            }
            return audio;
        }
    }

    public static abstract class Bool extends TLObject {
        public static Bool TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Bool bool = null;
            switch (i) {
                case -1720552011:
                    bool = new TL_boolTrue();
                    break;
                case -1132882121:
                    bool = new TL_boolFalse();
                    break;
            }
            if (bool == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Bool", new Object[]{Integer.valueOf(i)}));
            }
            if (bool != null) {
                bool.readParams(abstractSerializedData, z);
            }
            return bool;
        }
    }

    public static abstract class BotInfo extends TLObject {
        public ArrayList<TL_botCommand> commands = new ArrayList();
        public String description;
        public int user_id;
        public int version;

        public static BotInfo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            BotInfo botInfo = null;
            switch (i) {
                case -1729618630:
                    botInfo = new TL_botInfo();
                    break;
                case -1154598962:
                    botInfo = new TL_botInfoEmpty_layer48();
                    break;
                case 164583517:
                    botInfo = new TL_botInfo_layer48();
                    break;
            }
            if (botInfo == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in BotInfo", new Object[]{Integer.valueOf(i)}));
            }
            if (botInfo != null) {
                botInfo.readParams(abstractSerializedData, z);
            }
            return botInfo;
        }
    }

    public static abstract class BotInlineMessage extends TLObject {
        public String address;
        public String caption;
        public ArrayList<MessageEntity> entities = new ArrayList();
        public String first_name;
        public int flags;
        public GeoPoint geo;
        public String last_name;
        public String message;
        public boolean no_webpage;
        public int period;
        public String phone_number;
        public String provider;
        public ReplyMarkup reply_markup;
        public String title;
        public String venue_id;

        public static BotInlineMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            BotInlineMessage botInlineMessage = null;
            switch (i) {
                case -1937807902:
                    botInlineMessage = new TL_botInlineMessageText();
                    break;
                case -1222451611:
                    botInlineMessage = new TL_botInlineMessageMediaGeo();
                    break;
                case 175419739:
                    botInlineMessage = new TL_botInlineMessageMediaAuto();
                    break;
                case 904770772:
                    botInlineMessage = new TL_botInlineMessageMediaContact();
                    break;
                case 982505656:
                    botInlineMessage = new TL_botInlineMessageMediaGeo_layer71();
                    break;
                case 1130767150:
                    botInlineMessage = new TL_botInlineMessageMediaVenue();
                    break;
            }
            if (botInlineMessage == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in BotInlineMessage", new Object[]{Integer.valueOf(i)}));
            }
            if (botInlineMessage != null) {
                botInlineMessage.readParams(abstractSerializedData, z);
            }
            return botInlineMessage;
        }
    }

    public static abstract class BotInlineResult extends TLObject {
        public String content_type;
        public String content_url;
        public String description;
        public Document document;
        public int duration;
        public int flags;
        /* renamed from: h */
        public int f10134h;
        public String id;
        public Photo photo;
        public long query_id;
        public BotInlineMessage send_message;
        public String thumb_url;
        public String title;
        public String type;
        public String url;
        /* renamed from: w */
        public int f10135w;

        public static BotInlineResult TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            BotInlineResult botInlineResult = null;
            switch (i) {
                case -1679053127:
                    botInlineResult = new TL_botInlineResult();
                    break;
                case 400266251:
                    botInlineResult = new TL_botInlineMediaResult();
                    break;
            }
            if (botInlineResult == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in BotInlineResult", new Object[]{Integer.valueOf(i)}));
            }
            if (botInlineResult != null) {
                botInlineResult.readParams(abstractSerializedData, z);
            }
            return botInlineResult;
        }
    }

    public static abstract class ChannelAdminLogEventAction extends TLObject {
        public Message message;
        public Message new_message;
        public ChannelParticipant new_participant;
        public ChatPhoto new_photo;
        public InputStickerSet new_stickerset;
        public ChannelParticipant participant;
        public Message prev_message;
        public ChannelParticipant prev_participant;
        public ChatPhoto prev_photo;
        public InputStickerSet prev_stickerset;
        public String prev_value;

        public static ChannelAdminLogEventAction TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChannelAdminLogEventAction channelAdminLogEventAction = null;
            switch (i) {
                case -1312568665:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionChangeStickerSet();
                    break;
                case -1204857405:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionChangePhoto();
                    break;
                case -714643696:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionParticipantToggleAdmin();
                    break;
                case -484690728:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionParticipantInvite();
                    break;
                case -422036098:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionParticipantToggleBan();
                    break;
                case -421545947:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionChangeTitle();
                    break;
                case -370660328:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionUpdatePinned();
                    break;
                case -124291086:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionParticipantLeave();
                    break;
                case 405815507:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionParticipantJoin();
                    break;
                case 460916654:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionToggleInvites();
                    break;
                case 648939889:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionToggleSignatures();
                    break;
                case 1121994683:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionDeleteMessage();
                    break;
                case 1427671598:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionChangeAbout();
                    break;
                case 1599903217:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionTogglePreHistoryHidden();
                    break;
                case 1783299128:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionChangeUsername();
                    break;
                case 1889215493:
                    channelAdminLogEventAction = new TL_channelAdminLogEventActionEditMessage();
                    break;
            }
            if (channelAdminLogEventAction == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChannelAdminLogEventAction", new Object[]{Integer.valueOf(i)}));
            }
            if (channelAdminLogEventAction != null) {
                channelAdminLogEventAction.readParams(abstractSerializedData, z);
            }
            return channelAdminLogEventAction;
        }
    }

    public static abstract class ChannelMessagesFilter extends TLObject {
        public boolean exclude_new_messages;
        public int flags;
        public ArrayList<TLRPC$TL_messageRange> ranges = new ArrayList();

        public static ChannelMessagesFilter TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChannelMessagesFilter channelMessagesFilter = null;
            switch (i) {
                case -1798033689:
                    channelMessagesFilter = new TL_channelMessagesFilterEmpty();
                    break;
                case -847783593:
                    channelMessagesFilter = new TL_channelMessagesFilter();
                    break;
            }
            if (channelMessagesFilter == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChannelMessagesFilter", new Object[]{Integer.valueOf(i)}));
            }
            if (channelMessagesFilter != null) {
                channelMessagesFilter.readParams(abstractSerializedData, z);
            }
            return channelMessagesFilter;
        }
    }

    public static abstract class ChannelParticipant extends TLObject {
        public TL_channelAdminRights admin_rights;
        public TL_channelBannedRights banned_rights;
        public boolean can_edit;
        public int date;
        public int flags;
        public int inviter_id;
        public int kicked_by;
        public boolean left;
        public int promoted_by;
        public int user_id;

        public static ChannelParticipant TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChannelParticipant channelParticipant = null;
            switch (i) {
                case -1933187430:
                    channelParticipant = new TL_channelParticipantKicked_layer67();
                    break;
                case -1861910545:
                    channelParticipant = new TL_channelParticipantModerator_layer67();
                    break;
                case -1743180447:
                    channelParticipant = new TL_channelParticipantEditor_layer67();
                    break;
                case -1557620115:
                    channelParticipant = new TL_channelParticipantSelf();
                    break;
                case -1473271656:
                    channelParticipant = new TL_channelParticipantAdmin();
                    break;
                case -471670279:
                    channelParticipant = new TL_channelParticipantCreator();
                    break;
                case 367766557:
                    channelParticipant = new TL_channelParticipant();
                    break;
                case 573315206:
                    channelParticipant = new TL_channelParticipantBanned();
                    break;
            }
            if (channelParticipant == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChannelParticipant", new Object[]{Integer.valueOf(i)}));
            }
            if (channelParticipant != null) {
                channelParticipant.readParams(abstractSerializedData, z);
            }
            return channelParticipant;
        }
    }

    public static abstract class ChannelParticipantsFilter extends TLObject {
        /* renamed from: q */
        public String f10136q;

        public static ChannelParticipantsFilter TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChannelParticipantsFilter channelParticipantsFilter = null;
            switch (i) {
                case -1548400251:
                    channelParticipantsFilter = new TL_channelParticipantsKicked();
                    break;
                case -1328445861:
                    channelParticipantsFilter = new TL_channelParticipantsBots();
                    break;
                case -1268741783:
                    channelParticipantsFilter = new TL_channelParticipantsAdmins();
                    break;
                case -566281095:
                    channelParticipantsFilter = new TL_channelParticipantsRecent();
                    break;
                case 106343499:
                    channelParticipantsFilter = new TL_channelParticipantsSearch();
                    break;
                case 338142689:
                    channelParticipantsFilter = new TL_channelParticipantsBanned();
                    break;
            }
            if (channelParticipantsFilter == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChannelParticipantsFilter", new Object[]{Integer.valueOf(i)}));
            }
            if (channelParticipantsFilter != null) {
                channelParticipantsFilter.readParams(abstractSerializedData, z);
            }
            return channelParticipantsFilter;
        }
    }

    public static abstract class Chat extends TLObject {
        public long access_hash;
        public String address;
        public boolean admin;
        public TL_channelAdminRights admin_rights;
        public boolean admins_enabled;
        public TL_channelBannedRights banned_rights;
        public boolean broadcast;
        public boolean checked_in;
        public boolean creator;
        public int date;
        public boolean deactivated;
        public boolean democracy;
        public boolean explicit_content;
        public int flags;
        public GeoPoint geo;
        public int id;
        public boolean kicked;
        public boolean left;
        public boolean megagroup;
        public InputChannel migrated_to;
        public boolean min;
        public boolean moderator;
        public int participants_count;
        public ChatPhoto photo;
        public boolean restricted;
        public String restriction_reason;
        public boolean signatures;
        public String title;
        public int until_date;
        public String username;
        public String venue;
        public boolean verified;
        public int version;

        public static Chat TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Chat chat = null;
            switch (i) {
                case -2059962289:
                    chat = new TL_channelForbidden_layer67();
                    break;
                case -1683826688:
                    chat = new TL_chatEmpty();
                    break;
                case -1588737454:
                    chat = new TL_channel_layer67();
                    break;
                case -652419756:
                    chat = new TL_chat();
                    break;
                case -83047359:
                    chat = new TL_chatForbidden_old();
                    break;
                case 120753115:
                    chat = new TL_chatForbidden();
                    break;
                case 213142300:
                    chat = new TL_channel_layer72();
                    break;
                case 681420594:
                    chat = new TL_channelForbidden();
                    break;
                case 763724588:
                    chat = new TL_channelForbidden_layer52();
                    break;
                case 1158377749:
                    chat = new TL_channel();
                    break;
                case 1260090630:
                    chat = new TL_channel_layer48();
                    break;
                case 1737397639:
                    chat = new TL_channel_old();
                    break;
                case 1855757255:
                    chat = new TL_chat_old();
                    break;
                case 1930607688:
                    chat = new TL_chat_old2();
                    break;
                case 1978329690:
                    chat = new TLRPC$TL_geoChat();
                    break;
            }
            if (chat == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Chat", new Object[]{Integer.valueOf(i)}));
            }
            if (chat != null) {
                chat.readParams(abstractSerializedData, z);
            }
            return chat;
        }
    }

    public static abstract class ChatFull extends TLObject {
        public String about;
        public int admins_count;
        public int available_min_id;
        public int banned_count;
        public ArrayList<BotInfo> bot_info = new ArrayList();
        public int call_msg_id;
        public boolean can_set_stickers;
        public boolean can_set_username;
        public boolean can_view_participants;
        public Photo chat_photo;
        public ExportedChatInvite exported_invite;
        public int flags;
        public boolean hidden_prehistory;
        public int id;
        public int kicked_count;
        public int migrated_from_chat_id;
        public int migrated_from_max_id;
        public PeerNotifySettings notify_settings;
        public ChatParticipants participants;
        public int participants_count;
        public int pinned_msg_id;
        public int read_inbox_max_id;
        public int read_outbox_max_id;
        public StickerSet stickerset;
        public int unread_count;
        public int unread_important_count;

        public static ChatFull TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChatFull chatFull = null;
            switch (i) {
                case -1781833897:
                    chatFull = new TL_channelFull_layer70();
                    break;
                case -1749097118:
                    chatFull = new TL_channelFull_layer52();
                    break;
                case -1640751649:
                    chatFull = new TL_channelFull_layer48();
                    break;
                case -1009430225:
                    chatFull = new TL_channelFull_layer67();
                    break;
                case -877254512:
                    chatFull = new TL_channelFull();
                    break;
                case -88925533:
                    chatFull = new TL_channelFull_old();
                    break;
                case 401891279:
                    chatFull = new TL_channelFull_layer71();
                    break;
                case 771925524:
                    chatFull = new TL_chatFull();
                    break;
                case 1991201921:
                    chatFull = new TL_channelFull_layer72();
                    break;
            }
            if (chatFull == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChatFull", new Object[]{Integer.valueOf(i)}));
            }
            if (chatFull != null) {
                chatFull.readParams(abstractSerializedData, z);
            }
            return chatFull;
        }
    }

    public static abstract class ChatInvite extends TLObject {
        public boolean broadcast;
        public boolean channel;
        public Chat chat;
        public int flags;
        public boolean isPublic;
        public boolean megagroup;
        public ArrayList<User> participants = new ArrayList();
        public int participants_count;
        public ChatPhoto photo;
        public String title;

        public static ChatInvite TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChatInvite chatInvite = null;
            switch (i) {
                case -613092008:
                    chatInvite = new TL_chatInvite();
                    break;
                case 1516793212:
                    chatInvite = new TL_chatInviteAlready();
                    break;
            }
            if (chatInvite == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChatInvite", new Object[]{Integer.valueOf(i)}));
            }
            if (chatInvite != null) {
                chatInvite.readParams(abstractSerializedData, z);
            }
            return chatInvite;
        }
    }

    public static abstract class ChatParticipant extends TLObject {
        public int date;
        public int inviter_id;
        public int user_id;

        public static ChatParticipant TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChatParticipant chatParticipant = null;
            switch (i) {
                case -925415106:
                    chatParticipant = new TL_chatParticipant();
                    break;
                case -636267638:
                    chatParticipant = new TL_chatParticipantCreator();
                    break;
                case -489233354:
                    chatParticipant = new TL_chatParticipantAdmin();
                    break;
            }
            if (chatParticipant == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChatParticipant", new Object[]{Integer.valueOf(i)}));
            }
            if (chatParticipant != null) {
                chatParticipant.readParams(abstractSerializedData, z);
            }
            return chatParticipant;
        }
    }

    public static abstract class ChatParticipants extends TLObject {
        public int admin_id;
        public int chat_id;
        public int flags;
        public ArrayList<ChatParticipant> participants = new ArrayList();
        public ChatParticipant self_participant;
        public int version;

        public static ChatParticipants TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChatParticipants chatParticipants = null;
            switch (i) {
                case -57668565:
                    chatParticipants = new TL_chatParticipantsForbidden();
                    break;
                case 265468810:
                    chatParticipants = new TL_chatParticipantsForbidden_old();
                    break;
                case 1061556205:
                    chatParticipants = new TL_chatParticipants();
                    break;
                case 2017571861:
                    chatParticipants = new TL_chatParticipants_old();
                    break;
            }
            if (chatParticipants == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChatParticipants", new Object[]{Integer.valueOf(i)}));
            }
            if (chatParticipants != null) {
                chatParticipants.readParams(abstractSerializedData, z);
            }
            return chatParticipants;
        }
    }

    public static abstract class ChatPhoto extends TLObject {
        public FileLocation photo_big;
        public FileLocation photo_small;

        public static ChatPhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ChatPhoto chatPhoto = null;
            switch (i) {
                case 935395612:
                    chatPhoto = new TL_chatPhotoEmpty();
                    break;
                case 1632839530:
                    chatPhoto = new TL_chatPhoto();
                    break;
            }
            if (chatPhoto == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ChatPhoto", new Object[]{Integer.valueOf(i)}));
            }
            if (chatPhoto != null) {
                chatPhoto.readParams(abstractSerializedData, z);
            }
            return chatPhoto;
        }
    }

    public static abstract class ContactLink extends TLObject {
        public static ContactLink TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ContactLink contactLink = null;
            switch (i) {
                case -721239344:
                    contactLink = new TLRPC$TL_contactLinkContact();
                    break;
                case -17968211:
                    contactLink = new TLRPC$TL_contactLinkNone();
                    break;
                case 646922073:
                    contactLink = new TLRPC$TL_contactLinkHasPhone();
                    break;
                case 1599050311:
                    contactLink = new TLRPC$TL_contactLinkUnknown();
                    break;
            }
            if (contactLink == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ContactLink", new Object[]{Integer.valueOf(i)}));
            }
            if (contactLink != null) {
                contactLink.readParams(abstractSerializedData, z);
            }
            return contactLink;
        }
    }

    public static abstract class DecryptedMessage extends TLObject {
        public DecryptedMessageAction action;
        public ArrayList<MessageEntity> entities = new ArrayList();
        public int flags;
        public long grouped_id;
        public DecryptedMessageMedia media;
        public String message;
        public byte[] random_bytes;
        public long random_id;
        public long reply_to_random_id;
        public int ttl;
        public String via_bot_name;

        public static DecryptedMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            DecryptedMessage decryptedMessage = null;
            switch (i) {
                case -1848883596:
                    decryptedMessage = new TLRPC$TL_decryptedMessage();
                    break;
                case -1438109059:
                    decryptedMessage = new TLRPC$TL_decryptedMessageService_layer8();
                    break;
                case 528568095:
                    decryptedMessage = new TLRPC$TL_decryptedMessage_layer8();
                    break;
                case 541931640:
                    decryptedMessage = new TLRPC$TL_decryptedMessage_layer17();
                    break;
                case 917541342:
                    decryptedMessage = new TLRPC$TL_decryptedMessage_layer45();
                    break;
                case 1930838368:
                    decryptedMessage = new TLRPC$TL_decryptedMessageService();
                    break;
            }
            if (decryptedMessage == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessage", new Object[]{Integer.valueOf(i)}));
            }
            if (decryptedMessage != null) {
                decryptedMessage.readParams(abstractSerializedData, z);
            }
            return decryptedMessage;
        }
    }

    public static abstract class DecryptedMessageAction extends TLObject {
        public SendMessageAction action;
        public int end_seq_no;
        public long exchange_id;
        public byte[] g_a;
        public byte[] g_b;
        public long key_fingerprint;
        public int layer;
        public ArrayList<Long> random_ids = new ArrayList();
        public int start_seq_no;
        public int ttl_seconds;

        public static DecryptedMessageAction TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            DecryptedMessageAction decryptedMessageAction = null;
            switch (i) {
                case -1967000459:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionScreenshotMessages();
                    break;
                case -1586283796:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionSetMessageTTL();
                    break;
                case -1473258141:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionNoop();
                    break;
                case -860719551:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionTyping();
                    break;
                case -586814357:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionAbortKey();
                    break;
                case -332526693:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionCommitKey();
                    break;
                case -217806717:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionNotifyLayer();
                    break;
                case -204906213:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionRequestKey();
                    break;
                case 206520510:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionReadMessages();
                    break;
                case 1360072880:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionResend();
                    break;
                case 1700872964:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionDeleteMessages();
                    break;
                case 1729750108:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionFlushHistory();
                    break;
                case 1877046107:
                    decryptedMessageAction = new TLRPC$TL_decryptedMessageActionAcceptKey();
                    break;
            }
            if (decryptedMessageAction == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessageAction", new Object[]{Integer.valueOf(i)}));
            }
            if (decryptedMessageAction != null) {
                decryptedMessageAction.readParams(abstractSerializedData, z);
            }
            return decryptedMessageAction;
        }
    }

    public static abstract class DecryptedMessageMedia extends TLObject {
        public double _long;
        public long access_hash;
        public String address;
        public ArrayList<DocumentAttribute> attributes = new ArrayList();
        public String caption;
        public int date;
        public int dc_id;
        public int duration;
        public String file_name;
        public String first_name;
        /* renamed from: h */
        public int f10137h;
        public long id;
        public byte[] iv;
        public byte[] key;
        public String last_name;
        public double lat;
        public String mime_type;
        public String phone_number;
        public String provider;
        public int size;
        public int thumb_h;
        public int thumb_w;
        public String title;
        public String url;
        public int user_id;
        public String venue_id;
        /* renamed from: w */
        public int f10138w;

        public static DecryptedMessageMedia TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            DecryptedMessageMedia decryptedMessageMedia = null;
            switch (i) {
                case -1978796689:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaVenue();
                    break;
                case -1760785394:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaVideo();
                    break;
                case -1332395189:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaDocument_layer8();
                    break;
                case -452652584:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaWebPage();
                    break;
                case -235238024:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaPhoto();
                    break;
                case -90853155:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaExternalDocument();
                    break;
                case 144661578:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaEmpty();
                    break;
                case 846826124:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaPhoto_layer8();
                    break;
                case 893913689:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaGeoPoint();
                    break;
                case 1290694387:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaVideo_layer8();
                    break;
                case 1380598109:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaVideo_layer17();
                    break;
                case 1474341323:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaAudio();
                    break;
                case 1485441687:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaContact();
                    break;
                case 1619031439:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaAudio_layer8();
                    break;
                case 2063502050:
                    decryptedMessageMedia = new TLRPC$TL_decryptedMessageMediaDocument();
                    break;
            }
            if (decryptedMessageMedia == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessageMedia", new Object[]{Integer.valueOf(i)}));
            }
            if (decryptedMessageMedia != null) {
                decryptedMessageMedia.readParams(abstractSerializedData, z);
            }
            return decryptedMessageMedia;
        }
    }

    public static class Document extends TLObject {
        public long access_hash;
        public ArrayList<DocumentAttribute> attributes = new ArrayList();
        public String caption;
        public int date;
        public int dc_id;
        public String file_name;
        public long id;
        public byte[] iv;
        public byte[] key;
        public String mime_type;
        public int size;
        public PhotoSize thumb;
        public int user_id;
        public int version;

        public static Document TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Document document = null;
            switch (i) {
                case -2027738169:
                    document = new TLRPC$TL_document();
                    break;
                case -1627626714:
                    document = new TLRPC$TL_document_old();
                    break;
                case -106717361:
                    document = new TLRPC$TL_document_layer53();
                    break;
                case 922273905:
                    document = new TLRPC$TL_documentEmpty();
                    break;
                case 1431655766:
                    document = new TLRPC$TL_documentEncrypted_old();
                    break;
                case 1431655768:
                    document = new TLRPC$TL_documentEncrypted();
                    break;
            }
            if (document == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Document", new Object[]{Integer.valueOf(i)}));
            }
            if (document != null) {
                document.readParams(abstractSerializedData, z);
            }
            return document;
        }
    }

    public static class DocumentAttribute extends TLObject {
        public String alt;
        @C1662c(a = "documentTitle")
        public String documentTitle;
        public int duration;
        public String file_name;
        public int flags;
        /* renamed from: h */
        public int f10139h;
        public boolean mask;
        public TLRPC$TL_maskCoords mask_coords;
        public String performer;
        public boolean round_message;
        public InputStickerSet stickerset;
        public String title;
        public boolean voice;
        /* renamed from: w */
        public int f10140w;
        public byte[] waveform;

        public static DocumentAttribute TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            DocumentAttribute documentAttribute = null;
            switch (i) {
                case -1744710921:
                    documentAttribute = new TLRPC$TL_documentAttributeHasStickers();
                    break;
                case -1739392570:
                    documentAttribute = new TLRPC$TL_documentAttributeAudio();
                    break;
                case -1723033470:
                    documentAttribute = new TLRPC$TL_documentAttributeSticker_old2();
                    break;
                case -556656416:
                    documentAttribute = new TLRPC$TL_documentAttributeAudio_layer45();
                    break;
                case -83208409:
                    documentAttribute = new TLRPC$TL_documentAttributeSticker_old();
                    break;
                case 85215461:
                    documentAttribute = new TLRPC$TL_documentAttributeAudio_old();
                    break;
                case 250621158:
                    documentAttribute = new TLRPC$TL_documentAttributeVideo();
                    break;
                case 297109817:
                    documentAttribute = new TLRPC$TL_documentAttributeAnimated();
                    break;
                case 358154344:
                    documentAttribute = new TLRPC$TL_documentAttributeFilename();
                    break;
                case 978674434:
                    documentAttribute = new TLRPC$TL_documentAttributeSticker_layer55();
                    break;
                case 1494273227:
                    documentAttribute = new TLRPC$TL_documentAttributeVideo_layer65();
                    break;
                case 1662637586:
                    documentAttribute = new TLRPC$TL_documentAttributeSticker();
                    break;
                case 1815593308:
                    documentAttribute = new TLRPC$TL_documentAttributeImageSize();
                    break;
            }
            if (documentAttribute == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in DocumentAttribute", new Object[]{Integer.valueOf(i)}));
            }
            if (documentAttribute != null) {
                documentAttribute.readParams(abstractSerializedData, z);
            }
            return documentAttribute;
        }

        public String getDocumentTitle() {
            return this.documentTitle;
        }

        public void setDocumentTitle(String str) {
            this.documentTitle = str;
        }
    }

    public static abstract class DraftMessage extends TLObject {
        public int date;
        public ArrayList<MessageEntity> entities = new ArrayList();
        public int flags;
        public String message;
        public boolean no_webpage;
        public int reply_to_msg_id;

        public static DraftMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            DraftMessage draftMessage = null;
            switch (i) {
                case -1169445179:
                    draftMessage = new TLRPC$TL_draftMessageEmpty();
                    break;
                case -40996577:
                    draftMessage = new TLRPC$TL_draftMessage();
                    break;
            }
            if (draftMessage == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in DraftMessage", new Object[]{Integer.valueOf(i)}));
            }
            if (draftMessage != null) {
                draftMessage.readParams(abstractSerializedData, z);
            }
            return draftMessage;
        }
    }

    public static abstract class EncryptedChat extends TLObject {
        public byte[] a_or_b;
        public long access_hash;
        public int admin_id;
        public byte[] auth_key;
        public int date;
        public long exchange_id;
        public byte[] future_auth_key;
        public long future_key_fingerprint;
        public byte[] g_a;
        public byte[] g_a_or_b;
        public int id;
        public int in_seq_no;
        public int key_create_date;
        public long key_fingerprint;
        public byte[] key_hash;
        public short key_use_count_in;
        public short key_use_count_out;
        public int layer;
        public int mtproto_seq;
        public byte[] nonce;
        public int participant_id;
        public int seq_in;
        public int seq_out;
        public int ttl;
        public int user_id;

        public static EncryptedChat TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            EncryptedChat encryptedChat = null;
            switch (i) {
                case -1417756512:
                    encryptedChat = new TLRPC$TL_encryptedChatEmpty();
                    break;
                case -931638658:
                    encryptedChat = new TLRPC$TL_encryptedChatRequested();
                    break;
                case -94974410:
                    encryptedChat = new TLRPC$TL_encryptedChat();
                    break;
                case -39213129:
                    encryptedChat = new TLRPC$TL_encryptedChatRequested_old();
                    break;
                case 332848423:
                    encryptedChat = new TLRPC$TL_encryptedChatDiscarded();
                    break;
                case 1006044124:
                    encryptedChat = new TLRPC$TL_encryptedChatWaiting();
                    break;
                case 1711395151:
                    encryptedChat = new TLRPC$TL_encryptedChat_old();
                    break;
            }
            if (encryptedChat == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in EncryptedChat", new Object[]{Integer.valueOf(i)}));
            }
            if (encryptedChat != null) {
                encryptedChat.readParams(abstractSerializedData, z);
            }
            return encryptedChat;
        }
    }

    public static abstract class EncryptedFile extends TLObject {
        public long access_hash;
        public int dc_id;
        public long id;
        public int key_fingerprint;
        public int size;

        public static EncryptedFile TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            EncryptedFile encryptedFile = null;
            switch (i) {
                case -1038136962:
                    encryptedFile = new TLRPC$TL_encryptedFileEmpty();
                    break;
                case 1248893260:
                    encryptedFile = new TLRPC$TL_encryptedFile();
                    break;
            }
            if (encryptedFile == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in EncryptedFile", new Object[]{Integer.valueOf(i)}));
            }
            if (encryptedFile != null) {
                encryptedFile.readParams(abstractSerializedData, z);
            }
            return encryptedFile;
        }
    }

    public static abstract class EncryptedMessage extends TLObject {
        public byte[] bytes;
        public int chat_id;
        public int date;
        public EncryptedFile file;
        public long random_id;

        public static EncryptedMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            EncryptedMessage encryptedMessage = null;
            switch (i) {
                case -317144808:
                    encryptedMessage = new TLRPC$TL_encryptedMessage();
                    break;
                case 594758406:
                    encryptedMessage = new TLRPC$TL_encryptedMessageService();
                    break;
            }
            if (encryptedMessage == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in EncryptedMessage", new Object[]{Integer.valueOf(i)}));
            }
            if (encryptedMessage != null) {
                encryptedMessage.readParams(abstractSerializedData, z);
            }
            return encryptedMessage;
        }
    }

    public static abstract class ExportedChatInvite extends TLObject {
        public String link;

        public static ExportedChatInvite TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ExportedChatInvite exportedChatInvite = null;
            switch (i) {
                case -64092740:
                    exportedChatInvite = new TL_chatInviteExported();
                    break;
                case 1776236393:
                    exportedChatInvite = new TL_chatInviteEmpty();
                    break;
            }
            if (exportedChatInvite == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ExportedChatInvite", new Object[]{Integer.valueOf(i)}));
            }
            if (exportedChatInvite != null) {
                exportedChatInvite.readParams(abstractSerializedData, z);
            }
            return exportedChatInvite;
        }
    }

    public static abstract class FileLocation extends TLObject {
        public int dc_id;
        public byte[] iv;
        public byte[] key;
        public int local_id;
        public long secret;
        public long volume_id;

        public static FileLocation TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            FileLocation fileLocation = null;
            switch (i) {
                case 1406570614:
                    fileLocation = new TLRPC$TL_fileLocation();
                    break;
                case 1431655764:
                    fileLocation = new TLRPC$TL_fileEncryptedLocation();
                    break;
                case 2086234950:
                    fileLocation = new TLRPC$TL_fileLocationUnavailable();
                    break;
            }
            if (fileLocation == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in FileLocation", new Object[]{Integer.valueOf(i)}));
            }
            if (fileLocation != null) {
                fileLocation.readParams(abstractSerializedData, z);
            }
            return fileLocation;
        }
    }

    public static abstract class FoundGif extends TLObject {
        public String content_type;
        public String content_url;
        public Document document;
        /* renamed from: h */
        public int f10141h;
        public Photo photo;
        public String thumb_url;
        public String url;
        /* renamed from: w */
        public int f10142w;

        public static FoundGif TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            FoundGif foundGif = null;
            switch (i) {
                case -1670052855:
                    foundGif = new TLRPC$TL_foundGifCached();
                    break;
                case 372165663:
                    foundGif = new TLRPC$TL_foundGif();
                    break;
            }
            if (foundGif == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in FoundGif", new Object[]{Integer.valueOf(i)}));
            }
            if (foundGif != null) {
                foundGif.readParams(abstractSerializedData, z);
            }
            return foundGif;
        }
    }

    public static abstract class GeoChatMessage extends TLObject {
        public MessageAction action;
        public int chat_id;
        public int date;
        public int from_id;
        public int id;
        public MessageMedia media;
        public String message;

        public static GeoChatMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            GeoChatMessage geoChatMessage = null;
            switch (i) {
                case -749755826:
                    geoChatMessage = new TLRPC$TL_geoChatMessageService();
                    break;
                case 1158019297:
                    geoChatMessage = new TLRPC$TL_geoChatMessage();
                    break;
                case 1613830811:
                    geoChatMessage = new TLRPC$TL_geoChatMessageEmpty();
                    break;
            }
            if (geoChatMessage == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in GeoChatMessage", new Object[]{Integer.valueOf(i)}));
            }
            if (geoChatMessage != null) {
                geoChatMessage.readParams(abstractSerializedData, z);
            }
            return geoChatMessage;
        }
    }

    public static abstract class GeoPoint extends TLObject {
        public double _long;
        public double lat;

        public static GeoPoint TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            GeoPoint geoPoint = null;
            switch (i) {
                case 286776671:
                    geoPoint = new TLRPC$TL_geoPointEmpty();
                    break;
                case 541710092:
                    geoPoint = new TLRPC$TL_geoPoint();
                    break;
            }
            if (geoPoint == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in GeoPoint", new Object[]{Integer.valueOf(i)}));
            }
            if (geoPoint != null) {
                geoPoint.readParams(abstractSerializedData, z);
            }
            return geoPoint;
        }
    }

    public static abstract class GroupCall extends TLObject {
        public long access_hash;
        public int admin_id;
        public int channel_id;
        public TLRPC$TL_groupCallConnection connection;
        public int duration;
        public byte[] encryption_key;
        public int flags;
        public long id;
        public long key_fingerprint;
        public int participants_count;
        public TLRPC$TL_phoneCallProtocol protocol;
        public byte[] reflector_group_tag;
        public byte[] reflector_self_secret;
        public byte[] reflector_self_tag;

        public static GroupCall TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            GroupCall groupCall = null;
            switch (i) {
                case 177149476:
                    groupCall = new TLRPC$TL_groupCall();
                    break;
                case 1829443076:
                    groupCall = new TLRPC$TL_groupCallPrivate();
                    break;
                case 2004925620:
                    groupCall = new TLRPC$TL_groupCallDiscarded();
                    break;
            }
            if (groupCall == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in GroupCall", new Object[]{Integer.valueOf(i)}));
            }
            if (groupCall != null) {
                groupCall.readParams(abstractSerializedData, z);
            }
            return groupCall;
        }
    }

    public static abstract class GroupCallParticipant extends TLObject {
        public int date;
        public int flags;
        public int inviter_id;
        public byte[] member_tag_hash;
        public TLRPC$TL_inputPhoneCall phone_call;
        public boolean readonly;
        public byte[] streams;
        public int user_id;

        public static GroupCallParticipant TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            GroupCallParticipant groupCallParticipant = null;
            switch (i) {
                case 930387696:
                    groupCallParticipant = new TLRPC$TL_groupCallParticipantInvited();
                    break;
                case 1100680690:
                    groupCallParticipant = new TLRPC$TL_groupCallParticipantLeft();
                    break;
                case 1326135736:
                    groupCallParticipant = new TLRPC$TL_groupCallParticipantAdmin();
                    break;
                case 1486730135:
                    groupCallParticipant = new TLRPC$TL_groupCallParticipant();
                    break;
            }
            if (groupCallParticipant == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in GroupCallParticipant", new Object[]{Integer.valueOf(i)}));
            }
            if (groupCallParticipant != null) {
                groupCallParticipant.readParams(abstractSerializedData, z);
            }
            return groupCallParticipant;
        }
    }

    public static abstract class InputChannel extends TLObject {
        public long access_hash;
        public int channel_id;

        public static InputChannel TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputChannel inputChannel = null;
            switch (i) {
                case -1343524562:
                    inputChannel = new TLRPC$TL_inputChannel();
                    break;
                case -292807034:
                    inputChannel = new TLRPC$TL_inputChannelEmpty();
                    break;
            }
            if (inputChannel == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputChannel", new Object[]{Integer.valueOf(i)}));
            }
            if (inputChannel != null) {
                inputChannel.readParams(abstractSerializedData, z);
            }
            return inputChannel;
        }
    }

    public static abstract class InputChatPhoto extends TLObject {
        public InputFile file;
        public InputPhoto id;

        public static InputChatPhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputChatPhoto inputChatPhoto = null;
            switch (i) {
                case -1991004873:
                    inputChatPhoto = new TLRPC$TL_inputChatPhoto();
                    break;
                case -1837345356:
                    inputChatPhoto = new TLRPC$TL_inputChatUploadedPhoto();
                    break;
                case 480546647:
                    inputChatPhoto = new TLRPC$TL_inputChatPhotoEmpty();
                    break;
            }
            if (inputChatPhoto == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputChatPhoto", new Object[]{Integer.valueOf(i)}));
            }
            if (inputChatPhoto != null) {
                inputChatPhoto.readParams(abstractSerializedData, z);
            }
            return inputChatPhoto;
        }
    }

    public static abstract class InputDocument extends TLObject {
        public long access_hash;
        public long id;

        public static InputDocument TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputDocument inputDocument = null;
            switch (i) {
                case 410618194:
                    inputDocument = new TLRPC$TL_inputDocument();
                    break;
                case 1928391342:
                    inputDocument = new TLRPC$TL_inputDocumentEmpty();
                    break;
            }
            if (inputDocument == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputDocument", new Object[]{Integer.valueOf(i)}));
            }
            if (inputDocument != null) {
                inputDocument.readParams(abstractSerializedData, z);
            }
            return inputDocument;
        }
    }

    public static abstract class InputEncryptedFile extends TLObject {
        public long access_hash;
        public long id;
        public int key_fingerprint;
        public String md5_checksum;
        public int parts;

        public static InputEncryptedFile TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputEncryptedFile inputEncryptedFile = null;
            switch (i) {
                case 406307684:
                    inputEncryptedFile = new TLRPC$TL_inputEncryptedFileEmpty();
                    break;
                case 767652808:
                    inputEncryptedFile = new TLRPC$TL_inputEncryptedFileBigUploaded();
                    break;
                case 1511503333:
                    inputEncryptedFile = new TLRPC$TL_inputEncryptedFile();
                    break;
                case 1690108678:
                    inputEncryptedFile = new TLRPC$TL_inputEncryptedFileUploaded();
                    break;
            }
            if (inputEncryptedFile == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputEncryptedFile", new Object[]{Integer.valueOf(i)}));
            }
            if (inputEncryptedFile != null) {
                inputEncryptedFile.readParams(abstractSerializedData, z);
            }
            return inputEncryptedFile;
        }
    }

    public static abstract class InputFile extends TLObject {
        public long id;
        public String md5_checksum;
        public String name;
        public int parts;

        public static InputFile TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputFile inputFile = null;
            switch (i) {
                case -181407105:
                    inputFile = new TLRPC$TL_inputFile();
                    break;
                case -95482955:
                    inputFile = new TLRPC$TL_inputFileBig();
                    break;
            }
            if (inputFile == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputFile", new Object[]{Integer.valueOf(i)}));
            }
            if (inputFile != null) {
                inputFile.readParams(abstractSerializedData, z);
            }
            return inputFile;
        }
    }

    public static abstract class InputFileLocation extends TLObject {
        public long access_hash;
        public long id;
        public int local_id;
        public long secret;
        public long volume_id;

        public static InputFileLocation TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputFileLocation inputFileLocation = null;
            switch (i) {
                case -182231723:
                    inputFileLocation = new TLRPC$TL_inputEncryptedFileLocation();
                    break;
                case 342061462:
                    inputFileLocation = new TLRPC$TL_inputFileLocation();
                    break;
                case 1313188841:
                    inputFileLocation = new TLRPC$TL_inputDocumentFileLocation();
                    break;
            }
            if (inputFileLocation == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputFileLocation", new Object[]{Integer.valueOf(i)}));
            }
            if (inputFileLocation != null) {
                inputFileLocation.readParams(abstractSerializedData, z);
            }
            return inputFileLocation;
        }
    }

    public static abstract class InputGame extends TLObject {
        public long access_hash;
        public InputUser bot_id;
        public long id;
        public String short_name;

        public static InputGame TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputGame inputGame = null;
            switch (i) {
                case -1020139510:
                    inputGame = new TLRPC$TL_inputGameShortName();
                    break;
                case 53231223:
                    inputGame = new TLRPC$TL_inputGameID();
                    break;
            }
            if (inputGame == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputGame", new Object[]{Integer.valueOf(i)}));
            }
            if (inputGame != null) {
                inputGame.readParams(abstractSerializedData, z);
            }
            return inputGame;
        }
    }

    public static abstract class InputGeoPoint extends TLObject {
        public double _long;
        public double lat;

        public static InputGeoPoint TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputGeoPoint inputGeoPoint = null;
            switch (i) {
                case -457104426:
                    inputGeoPoint = new TLRPC$TL_inputGeoPointEmpty();
                    break;
                case -206066487:
                    inputGeoPoint = new TLRPC$TL_inputGeoPoint();
                    break;
            }
            if (inputGeoPoint == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputGeoPoint", new Object[]{Integer.valueOf(i)}));
            }
            if (inputGeoPoint != null) {
                inputGeoPoint.readParams(abstractSerializedData, z);
            }
            return inputGeoPoint;
        }
    }

    public static abstract class InputMedia extends TLObject {
        public String address;
        public ArrayList<DocumentAttribute> attributes = new ArrayList();
        public String caption;
        public InputFile file;
        public String first_name;
        public int flags;
        public InputGeoPoint geo_point;
        public String last_name;
        public String mime_type;
        public boolean nosound_video;
        public int period;
        public String phone_number;
        public String provider;
        /* renamed from: q */
        public String f10143q;
        public ArrayList<InputDocument> stickers = new ArrayList();
        public InputFile thumb;
        public String title;
        public int ttl_seconds;
        public String url;
        public String venue_id;
        public String venue_type;

        public static InputMedia TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputMedia inputMedia = null;
            switch (i) {
                case -2114308294:
                    inputMedia = new TLRPC$TL_inputMediaPhoto();
                    break;
                case -1771768449:
                    inputMedia = new TLRPC$TL_inputMediaEmpty();
                    break;
                case -1494984313:
                    inputMedia = new TLRPC$TL_inputMediaContact();
                    break;
                case -1225309387:
                    inputMedia = new TLRPC$TL_inputMediaDocumentExternal();
                    break;
                case -1052959727:
                    inputMedia = new TLRPC$TL_inputMediaVenue();
                    break;
                case -750828557:
                    inputMedia = new TLRPC$TL_inputMediaGame();
                    break;
                case -476700163:
                    inputMedia = new TLRPC$TL_inputMediaUploadedDocument();
                    break;
                case -104578748:
                    inputMedia = new TLRPC$TL_inputMediaGeoPoint();
                    break;
                case 153267905:
                    inputMedia = new TLRPC$TL_inputMediaPhotoExternal();
                    break;
                case 792191537:
                    inputMedia = new TLRPC$TL_inputMediaUploadedPhoto();
                    break;
                case 1212395773:
                    inputMedia = new TLRPC$TL_inputMediaGifExternal();
                    break;
                case 1523279502:
                    inputMedia = new TLRPC$TL_inputMediaDocument();
                    break;
                case 2065305999:
                    inputMedia = new TLRPC$TL_inputMediaGeoLive();
                    break;
            }
            if (inputMedia == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputMedia", new Object[]{Integer.valueOf(i)}));
            }
            if (inputMedia != null) {
                inputMedia.readParams(abstractSerializedData, z);
            }
            return inputMedia;
        }
    }

    public static abstract class InputNotifyPeer extends TLObject {
        public static InputNotifyPeer TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputNotifyPeer inputNotifyPeer = null;
            switch (i) {
                case -1540769658:
                    inputNotifyPeer = new TLRPC$TL_inputNotifyAll();
                    break;
                case -1195615476:
                    inputNotifyPeer = new TLRPC$TL_inputNotifyPeer();
                    break;
                case 423314455:
                    inputNotifyPeer = new TLRPC$TL_inputNotifyUsers();
                    break;
                case 1251338318:
                    inputNotifyPeer = new TLRPC$TL_inputNotifyChats();
                    break;
                case 1301143240:
                    inputNotifyPeer = new TLRPC$TL_inputNotifyGeoChatPeer();
                    break;
            }
            if (inputNotifyPeer == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputNotifyPeer", new Object[]{Integer.valueOf(i)}));
            }
            if (inputNotifyPeer != null) {
                inputNotifyPeer.readParams(abstractSerializedData, z);
            }
            return inputNotifyPeer;
        }
    }

    public static abstract class InputPaymentCredentials extends TLObject {
        public TLRPC$TL_dataJSON data;
        public int flags;
        public String id;
        public TLRPC$TL_dataJSON payment_token;
        public boolean save;
        public byte[] tmp_password;

        public static InputPaymentCredentials TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPaymentCredentials inputPaymentCredentials = null;
            switch (i) {
                case -1056001329:
                    inputPaymentCredentials = new TLRPC$TL_inputPaymentCredentialsSaved();
                    break;
                case 873977640:
                    inputPaymentCredentials = new TLRPC$TL_inputPaymentCredentials();
                    break;
                case 2035705766:
                    inputPaymentCredentials = new TLRPC$TL_inputPaymentCredentialsAndroidPay();
                    break;
            }
            if (inputPaymentCredentials == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPaymentCredentials", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPaymentCredentials != null) {
                inputPaymentCredentials.readParams(abstractSerializedData, z);
            }
            return inputPaymentCredentials;
        }
    }

    public static abstract class InputPeer extends TLObject {
        public long access_hash;
        public int channel_id;
        public int chat_id;
        public int user_id;

        public static InputPeer TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPeer inputPeer = null;
            switch (i) {
                case 396093539:
                    inputPeer = new TLRPC$TL_inputPeerChat();
                    break;
                case 548253432:
                    inputPeer = new TLRPC$TL_inputPeerChannel();
                    break;
                case 2072935910:
                    inputPeer = new TLRPC$TL_inputPeerUser();
                    break;
                case 2107670217:
                    inputPeer = new TLRPC$TL_inputPeerSelf();
                    break;
                case 2134579434:
                    inputPeer = new TLRPC$TL_inputPeerEmpty();
                    break;
            }
            if (inputPeer == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPeer", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPeer != null) {
                inputPeer.readParams(abstractSerializedData, z);
            }
            return inputPeer;
        }
    }

    public static abstract class InputPeerNotifyEvents extends TLObject {
        public static InputPeerNotifyEvents TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPeerNotifyEvents inputPeerNotifyEvents = null;
            switch (i) {
                case -395694988:
                    inputPeerNotifyEvents = new TLRPC$TL_inputPeerNotifyEventsAll();
                    break;
                case -265263912:
                    inputPeerNotifyEvents = new TLRPC$TL_inputPeerNotifyEventsEmpty();
                    break;
            }
            if (inputPeerNotifyEvents == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPeerNotifyEvents", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPeerNotifyEvents != null) {
                inputPeerNotifyEvents.readParams(abstractSerializedData, z);
            }
            return inputPeerNotifyEvents;
        }
    }

    public static abstract class InputPhoto extends TLObject {
        public long access_hash;
        public long id;

        public static InputPhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPhoto inputPhoto = null;
            switch (i) {
                case -74070332:
                    inputPhoto = new TLRPC$TL_inputPhoto();
                    break;
                case 483901197:
                    inputPhoto = new TLRPC$TL_inputPhotoEmpty();
                    break;
            }
            if (inputPhoto == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPhoto", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPhoto != null) {
                inputPhoto.readParams(abstractSerializedData, z);
            }
            return inputPhoto;
        }
    }

    public static abstract class InputPrivacyKey extends TLObject {
        public static InputPrivacyKey TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPrivacyKey inputPrivacyKey = null;
            switch (i) {
                case -1107622874:
                    inputPrivacyKey = new TLRPC$TL_inputPrivacyKeyChatInvite();
                    break;
                case -88417185:
                    inputPrivacyKey = new TLRPC$TL_inputPrivacyKeyPhoneCall();
                    break;
                case 1335282456:
                    inputPrivacyKey = new TLRPC$TL_inputPrivacyKeyStatusTimestamp();
                    break;
            }
            if (inputPrivacyKey == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPrivacyKey", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPrivacyKey != null) {
                inputPrivacyKey.readParams(abstractSerializedData, z);
            }
            return inputPrivacyKey;
        }
    }

    public static abstract class InputPrivacyRule extends TLObject {
        public ArrayList<InputUser> users = new ArrayList();

        public static InputPrivacyRule TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputPrivacyRule inputPrivacyRule = null;
            switch (i) {
                case -1877932953:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueDisallowUsers();
                    break;
                case -697604407:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueDisallowAll();
                    break;
                case 195371015:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueDisallowContacts();
                    break;
                case 218751099:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueAllowContacts();
                    break;
                case 320652927:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueAllowUsers();
                    break;
                case 407582158:
                    inputPrivacyRule = new TLRPC$TL_inputPrivacyValueAllowAll();
                    break;
            }
            if (inputPrivacyRule == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputPrivacyRule", new Object[]{Integer.valueOf(i)}));
            }
            if (inputPrivacyRule != null) {
                inputPrivacyRule.readParams(abstractSerializedData, z);
            }
            return inputPrivacyRule;
        }
    }

    public static abstract class InputStickerSet extends TLObject {
        public long access_hash;
        public long id;
        public String short_name;

        public static InputStickerSet TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputStickerSet inputStickerSet = null;
            switch (i) {
                case -2044933984:
                    inputStickerSet = new TLRPC$TL_inputStickerSetShortName();
                    break;
                case -1645763991:
                    inputStickerSet = new TLRPC$TL_inputStickerSetID();
                    break;
                case -4838507:
                    inputStickerSet = new TLRPC$TL_inputStickerSetEmpty();
                    break;
            }
            if (inputStickerSet == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputStickerSet", new Object[]{Integer.valueOf(i)}));
            }
            if (inputStickerSet != null) {
                inputStickerSet.readParams(abstractSerializedData, z);
            }
            return inputStickerSet;
        }
    }

    public static abstract class InputStickeredMedia extends TLObject {
        public static InputStickeredMedia TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputStickeredMedia inputStickeredMedia = null;
            switch (i) {
                case 70813275:
                    inputStickeredMedia = new TLRPC$TL_inputStickeredMediaDocument();
                    break;
                case 1251549527:
                    inputStickeredMedia = new TLRPC$TL_inputStickeredMediaPhoto();
                    break;
            }
            if (inputStickeredMedia == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputStickeredMedia", new Object[]{Integer.valueOf(i)}));
            }
            if (inputStickeredMedia != null) {
                inputStickeredMedia.readParams(abstractSerializedData, z);
            }
            return inputStickeredMedia;
        }
    }

    public static abstract class InputUser extends TLObject {
        public long access_hash;
        public int user_id;

        public static InputUser TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            InputUser inputUser = null;
            switch (i) {
                case -1182234929:
                    inputUser = new TLRPC$TL_inputUserEmpty();
                    break;
                case -668391402:
                    inputUser = new TLRPC$TL_inputUser();
                    break;
                case -138301121:
                    inputUser = new TLRPC$TL_inputUserSelf();
                    break;
            }
            if (inputUser == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in InputUser", new Object[]{Integer.valueOf(i)}));
            }
            if (inputUser != null) {
                inputUser.readParams(abstractSerializedData, z);
            }
            return inputUser;
        }
    }

    public static abstract class KeyboardButton extends TLObject {
        public byte[] data;
        public int flags;
        public String query;
        public boolean same_peer;
        public String text;
        public String url;

        public static KeyboardButton TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            KeyboardButton keyboardButton = null;
            switch (i) {
                case -1560655744:
                    keyboardButton = new TLRPC$TL_keyboardButton();
                    break;
                case -1344716869:
                    keyboardButton = new TLRPC$TL_keyboardButtonBuy();
                    break;
                case -1318425559:
                    keyboardButton = new TLRPC$TL_keyboardButtonRequestPhone();
                    break;
                case -59151553:
                    keyboardButton = new TLRPC$TL_keyboardButtonRequestGeoLocation();
                    break;
                case 90744648:
                    keyboardButton = new TLRPC$TL_keyboardButtonSwitchInline();
                    break;
                case 629866245:
                    keyboardButton = new TLRPC$TL_keyboardButtonUrl();
                    break;
                case 1358175439:
                    keyboardButton = new TLRPC$TL_keyboardButtonGame();
                    break;
                case 1748655686:
                    keyboardButton = new TLRPC$TL_keyboardButtonCallback();
                    break;
            }
            if (keyboardButton == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in KeyboardButton", new Object[]{Integer.valueOf(i)}));
            }
            if (keyboardButton != null) {
                keyboardButton.readParams(abstractSerializedData, z);
            }
            return keyboardButton;
        }
    }

    public static abstract class LangPackString extends TLObject {
        public String few_value;
        public int flags;
        public String key;
        public String many_value;
        public String one_value;
        public String other_value;
        public String two_value;
        public String value;
        public String zero_value;

        public static LangPackString TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            LangPackString langPackString = null;
            switch (i) {
                case -892239370:
                    langPackString = new TLRPC$TL_langPackString();
                    break;
                case 695856818:
                    langPackString = new TLRPC$TL_langPackStringDeleted();
                    break;
                case 1816636575:
                    langPackString = new TLRPC$TL_langPackStringPluralized();
                    break;
            }
            if (langPackString == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in LangPackString", new Object[]{Integer.valueOf(i)}));
            }
            if (langPackString != null) {
                langPackString.readParams(abstractSerializedData, z);
            }
            return langPackString;
        }
    }

    public static abstract class MessageAction extends TLObject {
        public String address;
        public TLRPC$TL_inputGroupCall call;
        public long call_id;
        public int channel_id;
        public int chat_id;
        public String currency;
        public int duration;
        public DecryptedMessageAction encryptedAction;
        public int flags;
        public long game_id;
        public int inviter_id;
        public String message;
        public TLRPC$UserProfilePhoto newUserPhoto;
        public Photo photo;
        public PhoneCallDiscardReason reason;
        public int score;
        public String title;
        public long total_amount;
        public int ttl;
        public int user_id;
        public ArrayList<Integer> users = new ArrayList();

        public static MessageAction TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            MessageAction messageAction = null;
            switch (i) {
                case -2132731265:
                    messageAction = new TLRPC$TL_messageActionPhoneCall();
                    break;
                case -1834538890:
                    messageAction = new TLRPC$TL_messageActionGameScore();
                    break;
                case -1799538451:
                    messageAction = new TLRPC$TL_messageActionPinMessage();
                    break;
                case -1781355374:
                    messageAction = new TLRPC$TL_messageActionChannelCreate();
                    break;
                case -1780220945:
                    messageAction = new TLRPC$TL_messageActionChatDeletePhoto();
                    break;
                case -1615153660:
                    messageAction = new TLRPC$TL_messageActionHistoryClear();
                    break;
                case -1503425638:
                    messageAction = new TLRPC$TL_messageActionChatCreate();
                    break;
                case -1336546578:
                    messageAction = new TLRPC$TL_messageActionChannelMigrateFrom();
                    break;
                case -1297179892:
                    messageAction = new TLRPC$TL_messageActionChatDeleteUser();
                    break;
                case -1247687078:
                    messageAction = new TLRPC$TL_messageActionChatEditTitle();
                    break;
                case -1230047312:
                    messageAction = new TLRPC$TL_messageActionEmpty();
                    break;
                case -123931160:
                    messageAction = new TLRPC$TL_messageActionChatJoinedByLink();
                    break;
                case -85549226:
                    messageAction = new TLRPC$TL_messageActionCustomAction();
                    break;
                case 209540062:
                    messageAction = new TLRPC$TL_messageActionGeoChatCheckin();
                    break;
                case 1080663248:
                    messageAction = new TLRPC$TL_messageActionPaymentSent();
                    break;
                case 1200788123:
                    messageAction = new TLRPC$TL_messageActionScreenshotTaken();
                    break;
                case 1217033015:
                    messageAction = new TLRPC$TL_messageActionChatAddUser();
                    break;
                case 1371385889:
                    messageAction = new TLRPC$TL_messageActionChatMigrateTo();
                    break;
                case 1431655760:
                    messageAction = new TLRPC$TL_messageActionUserJoined();
                    break;
                case 1431655761:
                    messageAction = new TLRPC$TL_messageActionUserUpdatedPhoto();
                    break;
                case 1431655762:
                    messageAction = new TLRPC$TL_messageActionTTLChange();
                    break;
                case 1431655767:
                    messageAction = new TLRPC$TL_messageActionCreatedBroadcastList();
                    break;
                case 1431655925:
                    messageAction = new TLRPC$TL_messageActionLoginUnknownLocation();
                    break;
                case 1431655927:
                    messageAction = new TLRPC$TL_messageEncryptedAction();
                    break;
                case 1581055051:
                    messageAction = new TLRPC$TL_messageActionChatAddUser_old();
                    break;
                case 1862504124:
                    messageAction = new TLRPC$TL_messageActionGeoChatCreate();
                    break;
                case 2047704898:
                    messageAction = new TLRPC$TL_messageActionGroupCall();
                    break;
                case 2144015272:
                    messageAction = new TLRPC$TL_messageActionChatEditPhoto();
                    break;
            }
            if (messageAction == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in MessageAction", new Object[]{Integer.valueOf(i)}));
            }
            if (messageAction != null) {
                messageAction.readParams(abstractSerializedData, z);
            }
            return messageAction;
        }
    }

    public static abstract class MessageEntity extends TLObject {
        public String language;
        public int length;
        public int offset;
        public String url;

        public static MessageEntity TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            MessageEntity messageEntity = null;
            switch (i) {
                case -2106619040:
                    messageEntity = new TLRPC$TL_messageEntityItalic();
                    break;
                case -1148011883:
                    messageEntity = new TLRPC$TL_messageEntityUnknown();
                    break;
                case -1117713463:
                    messageEntity = new TLRPC$TL_messageEntityBold();
                    break;
                case -100378723:
                    messageEntity = new TLRPC$TL_messageEntityMention();
                    break;
                case 546203849:
                    messageEntity = new TLRPC$TL_inputMessageEntityMentionName();
                    break;
                case 681706865:
                    messageEntity = new TLRPC$TL_messageEntityCode();
                    break;
                case 892193368:
                    messageEntity = new TLRPC$TL_messageEntityMentionName();
                    break;
                case 1692693954:
                    messageEntity = new TLRPC$TL_messageEntityEmail();
                    break;
                case 1827637959:
                    messageEntity = new TLRPC$TL_messageEntityBotCommand();
                    break;
                case 1859134776:
                    messageEntity = new TLRPC$TL_messageEntityUrl();
                    break;
                case 1868782349:
                    messageEntity = new TLRPC$TL_messageEntityHashtag();
                    break;
                case 1938967520:
                    messageEntity = new TLRPC$TL_messageEntityPre();
                    break;
                case 1990644519:
                    messageEntity = new TLRPC$TL_messageEntityTextUrl();
                    break;
            }
            if (messageEntity == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in MessageEntity", new Object[]{Integer.valueOf(i)}));
            }
            if (messageEntity != null) {
                messageEntity.readParams(abstractSerializedData, z);
            }
            return messageEntity;
        }
    }

    public static abstract class MessageFwdHeader extends TLObject {
        public int channel_id;
        public int channel_post;
        public int date;
        public int flags;
        public int from_id;
        public String post_author;
        public int saved_from_msg_id;
        public Peer saved_from_peer;

        public static MessageFwdHeader TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            MessageFwdHeader messageFwdHeader = null;
            switch (i) {
                case -947462709:
                    messageFwdHeader = new TLRPC$TL_messageFwdHeader_layer68();
                    break;
                case -85986132:
                    messageFwdHeader = new TLRPC$TL_messageFwdHeader_layer72();
                    break;
                case 1436466797:
                    messageFwdHeader = new TLRPC$TL_messageFwdHeader();
                    break;
            }
            if (messageFwdHeader == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in MessageFwdHeader", new Object[]{Integer.valueOf(i)}));
            }
            if (messageFwdHeader != null) {
                messageFwdHeader.readParams(abstractSerializedData, z);
            }
            return messageFwdHeader;
        }
    }

    public static class MessageMedia extends TLObject {
        public String address;
        public Audio audio_unused;
        public byte[] bytes;
        public String caption;
        public String currency;
        public String description;
        public Document document;
        public String first_name;
        public int flags;
        public TLRPC$TL_game game;
        public GeoPoint geo;
        public String last_name;
        public int period;
        public String phone_number;
        public Photo photo;
        public String provider;
        public int receipt_msg_id;
        public boolean shipping_address_requested;
        public String start_param;
        public boolean test;
        public String title;
        public long total_amount;
        public int ttl_seconds;
        public int user_id;
        public String venue_id;
        public String venue_type;
        public TLRPC$Video video_unused;
        public TLRPC$WebPage webpage;

        public static MessageMedia TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            MessageMedia messageMedia = null;
            switch (i) {
                case -2074799289:
                    messageMedia = new TLRPC$TL_messageMediaInvoice();
                    break;
                case -1618676578:
                    messageMedia = new TLRPC$TL_messageMediaUnsupported();
                    break;
                case -1563278704:
                    messageMedia = new TLRPC$TL_messageMediaVideo_old();
                    break;
                case -1557277184:
                    messageMedia = new TLRPC$TL_messageMediaWebPage();
                    break;
                case -1256047857:
                    messageMedia = new TLRPC$TL_messageMediaPhoto();
                    break;
                case -961117440:
                    messageMedia = new TLRPC$TL_messageMediaAudio_layer45();
                    break;
                case -926655958:
                    messageMedia = new TLRPC$TL_messageMediaPhoto_old();
                    break;
                case -203411800:
                    messageMedia = new TLRPC$TL_messageMediaDocument_layer68();
                    break;
                case -38694904:
                    messageMedia = new TLRPC$TL_messageMediaGame();
                    break;
                case 694364726:
                    messageMedia = new TLRPC$TL_messageMediaUnsupported_old();
                    break;
                case 784356159:
                    messageMedia = new TLRPC$TL_messageMediaVenue();
                    break;
                case 802824708:
                    messageMedia = new TLRPC$TL_messageMediaDocument_old();
                    break;
                case 1032643901:
                    messageMedia = new TLRPC$TL_messageMediaPhoto_layer68();
                    break;
                case 1038967584:
                    messageMedia = new TLRPC$TL_messageMediaEmpty();
                    break;
                case 1457575028:
                    messageMedia = new TLRPC$TL_messageMediaGeo();
                    break;
                case 1540298357:
                    messageMedia = new TLRPC$TL_messageMediaVideo_layer45();
                    break;
                case 1585262393:
                    messageMedia = new TLRPC$TL_messageMediaContact();
                    break;
                case 2031269663:
                    messageMedia = new TLRPC$TL_messageMediaVenue_layer71();
                    break;
                case 2084316681:
                    messageMedia = new TLRPC$TL_messageMediaGeoLive();
                    break;
                case 2084836563:
                    messageMedia = new TLRPC$TL_messageMediaDocument();
                    break;
            }
            if (messageMedia == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in MessageMedia", new Object[]{Integer.valueOf(i)}));
            }
            if (messageMedia != null) {
                messageMedia.readParams(abstractSerializedData, z);
                MessageMedia tLRPC$TL_messageMediaDocument;
                if (messageMedia.video_unused != null) {
                    tLRPC$TL_messageMediaDocument = new TLRPC$TL_messageMediaDocument();
                    if (messageMedia.video_unused instanceof TLRPC$TL_videoEncrypted) {
                        tLRPC$TL_messageMediaDocument.document = new TLRPC$TL_documentEncrypted();
                        tLRPC$TL_messageMediaDocument.document.key = messageMedia.video_unused.key;
                        tLRPC$TL_messageMediaDocument.document.iv = messageMedia.video_unused.iv;
                    } else {
                        tLRPC$TL_messageMediaDocument.document = new TLRPC$TL_document();
                    }
                    tLRPC$TL_messageMediaDocument.flags = 3;
                    tLRPC$TL_messageMediaDocument.document.id = messageMedia.video_unused.id;
                    tLRPC$TL_messageMediaDocument.document.access_hash = messageMedia.video_unused.access_hash;
                    tLRPC$TL_messageMediaDocument.document.date = messageMedia.video_unused.date;
                    if (messageMedia.video_unused.mime_type != null) {
                        tLRPC$TL_messageMediaDocument.document.mime_type = messageMedia.video_unused.mime_type;
                    } else {
                        tLRPC$TL_messageMediaDocument.document.mime_type = MimeTypes.VIDEO_MP4;
                    }
                    tLRPC$TL_messageMediaDocument.document.size = messageMedia.video_unused.size;
                    tLRPC$TL_messageMediaDocument.document.thumb = messageMedia.video_unused.thumb;
                    tLRPC$TL_messageMediaDocument.document.dc_id = messageMedia.video_unused.dc_id;
                    tLRPC$TL_messageMediaDocument.caption = messageMedia.caption;
                    TLRPC$TL_documentAttributeVideo tLRPC$TL_documentAttributeVideo = new TLRPC$TL_documentAttributeVideo();
                    tLRPC$TL_documentAttributeVideo.w = messageMedia.video_unused.f10170w;
                    tLRPC$TL_documentAttributeVideo.h = messageMedia.video_unused.f10169h;
                    tLRPC$TL_documentAttributeVideo.duration = messageMedia.video_unused.duration;
                    tLRPC$TL_messageMediaDocument.document.attributes.add(tLRPC$TL_documentAttributeVideo);
                    if (tLRPC$TL_messageMediaDocument.caption != null) {
                        return tLRPC$TL_messageMediaDocument;
                    }
                    tLRPC$TL_messageMediaDocument.caption = TtmlNode.ANONYMOUS_REGION_ID;
                    return tLRPC$TL_messageMediaDocument;
                } else if (messageMedia.audio_unused != null) {
                    tLRPC$TL_messageMediaDocument = new TLRPC$TL_messageMediaDocument();
                    if (messageMedia.audio_unused instanceof TL_audioEncrypted) {
                        tLRPC$TL_messageMediaDocument.document = new TLRPC$TL_documentEncrypted();
                        tLRPC$TL_messageMediaDocument.document.key = messageMedia.audio_unused.key;
                        tLRPC$TL_messageMediaDocument.document.iv = messageMedia.audio_unused.iv;
                    } else {
                        tLRPC$TL_messageMediaDocument.document = new TLRPC$TL_document();
                    }
                    tLRPC$TL_messageMediaDocument.flags = 3;
                    tLRPC$TL_messageMediaDocument.document.id = messageMedia.audio_unused.id;
                    tLRPC$TL_messageMediaDocument.document.access_hash = messageMedia.audio_unused.access_hash;
                    tLRPC$TL_messageMediaDocument.document.date = messageMedia.audio_unused.date;
                    if (messageMedia.audio_unused.mime_type != null) {
                        tLRPC$TL_messageMediaDocument.document.mime_type = messageMedia.audio_unused.mime_type;
                    } else {
                        tLRPC$TL_messageMediaDocument.document.mime_type = "audio/ogg";
                    }
                    tLRPC$TL_messageMediaDocument.document.size = messageMedia.audio_unused.size;
                    tLRPC$TL_messageMediaDocument.document.thumb = new TLRPC$TL_photoSizeEmpty();
                    tLRPC$TL_messageMediaDocument.document.thumb.type = "s";
                    tLRPC$TL_messageMediaDocument.document.dc_id = messageMedia.audio_unused.dc_id;
                    tLRPC$TL_messageMediaDocument.caption = messageMedia.caption;
                    TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                    tLRPC$TL_documentAttributeAudio.duration = messageMedia.audio_unused.duration;
                    tLRPC$TL_documentAttributeAudio.voice = true;
                    tLRPC$TL_messageMediaDocument.document.attributes.add(tLRPC$TL_documentAttributeAudio);
                    if (tLRPC$TL_messageMediaDocument.caption != null) {
                        return tLRPC$TL_messageMediaDocument;
                    }
                    tLRPC$TL_messageMediaDocument.caption = TtmlNode.ANONYMOUS_REGION_ID;
                    return tLRPC$TL_messageMediaDocument;
                }
            }
            return messageMedia;
        }
    }

    public static abstract class MessagesFilter extends TLObject {
        public int flags;
        public boolean missed;

        public static MessagesFilter TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            MessagesFilter messagesFilter = null;
            switch (i) {
                case -2134272152:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterPhoneCalls();
                    break;
                case -1777752804:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterPhotos();
                    break;
                case -1629621880:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterDocument();
                    break;
                case -1614803355:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterVideo();
                    break;
                case -1253451181:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterRoundVideo();
                    break;
                case -1040652646:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterMyMentions();
                    break;
                case -648121413:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterPhotoVideoDocuments();
                    break;
                case -530392189:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterContacts();
                    break;
                case -419271411:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterGeo();
                    break;
                case -3644025:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterGif();
                    break;
                case 928101534:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterMusic();
                    break;
                case 975236280:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterChatPhotos();
                    break;
                case 1358283666:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterVoice();
                    break;
                case 1458172132:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterPhotoVideo();
                    break;
                case 1474492012:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterEmpty();
                    break;
                case 2054952868:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterRoundVoice();
                    break;
                case 2129714567:
                    messagesFilter = new TLRPC$TL_inputMessagesFilterUrl();
                    break;
            }
            if (messagesFilter == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in MessagesFilter", new Object[]{Integer.valueOf(i)}));
            }
            if (messagesFilter != null) {
                messagesFilter.readParams(abstractSerializedData, z);
            }
            return messagesFilter;
        }
    }

    public static abstract class NotifyPeer extends TLObject {
        public Peer peer;

        public static NotifyPeer TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            NotifyPeer notifyPeer = null;
            switch (i) {
                case -1613493288:
                    notifyPeer = new TLRPC$TL_notifyPeer();
                    break;
                case -1261946036:
                    notifyPeer = new TLRPC$TL_notifyUsers();
                    break;
                case -1073230141:
                    notifyPeer = new TLRPC$TL_notifyChats();
                    break;
                case 1959820384:
                    notifyPeer = new TLRPC$TL_notifyAll();
                    break;
            }
            if (notifyPeer == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in NotifyPeer", new Object[]{Integer.valueOf(i)}));
            }
            if (notifyPeer != null) {
                notifyPeer.readParams(abstractSerializedData, z);
            }
            return notifyPeer;
        }
    }

    public static abstract class Page extends TLObject {
        public ArrayList<PageBlock> blocks = new ArrayList();
        public ArrayList<Document> documents = new ArrayList();
        public ArrayList<Photo> photos = new ArrayList();

        public static Page TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Page page = null;
            switch (i) {
                case -1913754556:
                    page = new TLRPC$TL_pagePart_layer67();
                    break;
                case -1908433218:
                    page = new TLRPC$TL_pagePart();
                    break;
                case -677274263:
                    page = new TLRPC$TL_pageFull_layer67();
                    break;
                case 1433323434:
                    page = new TLRPC$TL_pageFull();
                    break;
            }
            if (page == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Page", new Object[]{Integer.valueOf(i)}));
            }
            if (page != null) {
                page.readParams(abstractSerializedData, z);
            }
            return page;
        }
    }

    public static abstract class PageBlock extends TLObject {
        public boolean allow_scrolling;
        public long audio_id;
        public String author;
        public long author_photo_id;
        public boolean autoplay;
        public ArrayList<PageBlock> blocks = new ArrayList();
        public boolean bottom;
        public RichText caption;
        public Chat channel;
        public PageBlock cover;
        public int date;
        public boolean first;
        public int flags;
        public boolean full_width;
        /* renamed from: h */
        public int f10144h;
        public String html;
        public String language;
        public int level;
        public boolean loop;
        public int mid;
        public String name;
        public boolean ordered;
        public long photo_id;
        public long poster_photo_id;
        public int published_date;
        public RichText text;
        public String url;
        public long video_id;
        /* renamed from: w */
        public int f10145w;
        public long webpage_id;

        public static PageBlock TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PageBlock pageBlock = null;
            switch (i) {
                case -1879401953:
                    pageBlock = new TLRPC$TL_pageBlockSubtitle();
                    break;
                case -1162877472:
                    pageBlock = new TLRPC$TL_pageBlockAuthorDate();
                    break;
                case -1076861716:
                    pageBlock = new TLRPC$TL_pageBlockHeader();
                    break;
                case -1066346178:
                    pageBlock = new TLRPC$TL_pageBlockPreformatted();
                    break;
                case -840826671:
                    pageBlock = new TLRPC$TL_pageBlockEmbed();
                    break;
                case -837994576:
                    pageBlock = new TLRPC$TL_pageBlockAnchor();
                    break;
                case -650782469:
                    pageBlock = new TLRPC$TL_pageBlockEmbed_layer60();
                    break;
                case -640214938:
                    pageBlock = new TLRPC$TL_pageBlockVideo();
                    break;
                case -618614392:
                    pageBlock = new TLRPC$TL_pageBlockDivider();
                    break;
                case -372860542:
                    pageBlock = new TLRPC$TL_pageBlockPhoto();
                    break;
                case -283684427:
                    pageBlock = new TLRPC$TL_pageBlockChannel();
                    break;
                case -248793375:
                    pageBlock = new TLRPC$TL_pageBlockSubheader();
                    break;
                case 145955919:
                    pageBlock = new TLRPC$TL_pageBlockCollage();
                    break;
                case 319588707:
                    pageBlock = new TLRPC$TL_pageBlockSlideshow();
                    break;
                case 324435594:
                    pageBlock = new TLRPC$TL_pageBlockUnsupported();
                    break;
                case 641563686:
                    pageBlock = new TLRPC$TL_pageBlockBlockquote();
                    break;
                case 690781161:
                    pageBlock = new TLRPC$TL_pageBlockEmbedPost();
                    break;
                case 834148991:
                    pageBlock = new TLRPC$TL_pageBlockAudio();
                    break;
                case 972174080:
                    pageBlock = new TLRPC$TL_pageBlockCover();
                    break;
                case 978896884:
                    pageBlock = new TLRPC$TL_pageBlockList();
                    break;
                case 1029399794:
                    pageBlock = new TLRPC$TL_pageBlockAuthorDate_layer60();
                    break;
                case 1182402406:
                    pageBlock = new TLRPC$TL_pageBlockParagraph();
                    break;
                case 1216809369:
                    pageBlock = new TLRPC$TL_pageBlockFooter();
                    break;
                case 1329878739:
                    pageBlock = new TLRPC$TL_pageBlockPullquote();
                    break;
                case 1890305021:
                    pageBlock = new TLRPC$TL_pageBlockTitle();
                    break;
            }
            if (pageBlock == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PageBlock", new Object[]{Integer.valueOf(i)}));
            }
            if (pageBlock != null) {
                pageBlock.readParams(abstractSerializedData, z);
            }
            return pageBlock;
        }
    }

    public static class Peer extends TLObject {
        public int channel_id;
        public int chat_id;
        public int user_id;

        public static Peer TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Peer peer = null;
            switch (i) {
                case -1649296275:
                    peer = new TLRPC$TL_peerUser();
                    break;
                case -1160714821:
                    peer = new TLRPC$TL_peerChat();
                    break;
                case -1109531342:
                    peer = new TLRPC$TL_peerChannel();
                    break;
            }
            if (peer == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Peer", new Object[]{Integer.valueOf(i)}));
            }
            if (peer != null) {
                peer.readParams(abstractSerializedData, z);
            }
            return peer;
        }
    }

    public static abstract class PeerNotifyEvents extends TLObject {
        public static PeerNotifyEvents TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PeerNotifyEvents peerNotifyEvents = null;
            switch (i) {
                case -1378534221:
                    peerNotifyEvents = new TLRPC$TL_peerNotifyEventsEmpty();
                    break;
                case 1830677896:
                    peerNotifyEvents = new TLRPC$TL_peerNotifyEventsAll();
                    break;
            }
            if (peerNotifyEvents == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PeerNotifyEvents", new Object[]{Integer.valueOf(i)}));
            }
            if (peerNotifyEvents != null) {
                peerNotifyEvents.readParams(abstractSerializedData, z);
            }
            return peerNotifyEvents;
        }
    }

    public static abstract class PeerNotifySettings extends TLObject {
        public int events_mask;
        public int flags;
        public int mute_until;
        public boolean silent;
        public String sound;

        public static PeerNotifySettings TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PeerNotifySettings peerNotifySettings = null;
            switch (i) {
                case -1923214866:
                    peerNotifySettings = new TLRPC$TL_peerNotifySettings_layer47();
                    break;
                case -1697798976:
                    peerNotifySettings = new TLRPC$TL_peerNotifySettings();
                    break;
                case 1889961234:
                    peerNotifySettings = new TLRPC$TL_peerNotifySettingsEmpty();
                    break;
            }
            if (peerNotifySettings == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PeerNotifySettings", new Object[]{Integer.valueOf(i)}));
            }
            if (peerNotifySettings != null) {
                peerNotifySettings.readParams(abstractSerializedData, z);
            }
            return peerNotifySettings;
        }
    }

    public static abstract class PhoneCall extends TLObject {
        public long access_hash;
        public int admin_id;
        public ArrayList<TLRPC$TL_phoneConnection> alternative_connections = new ArrayList();
        public TLRPC$TL_phoneConnection connection;
        public int date;
        public int duration;
        public int flags;
        public byte[] g_a_hash;
        public byte[] g_a_or_b;
        public byte[] g_b;
        public long id;
        public long key_fingerprint;
        public boolean need_debug;
        public boolean need_rating;
        public int participant_id;
        public TLRPC$TL_phoneCallProtocol protocol;
        public PhoneCallDiscardReason reason;
        public int receive_date;
        public int start_date;

        public static PhoneCall TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PhoneCall phoneCall = null;
            switch (i) {
                case -2089411356:
                    phoneCall = new TLRPC$TL_phoneCallRequested();
                    break;
                case -1660057:
                    phoneCall = new TLRPC$TL_phoneCall();
                    break;
                case 462375633:
                    phoneCall = new TLRPC$TL_phoneCallWaiting();
                    break;
                case 1355435489:
                    phoneCall = new TLRPC$TL_phoneCallDiscarded();
                    break;
                case 1399245077:
                    phoneCall = new TLRPC$TL_phoneCallEmpty();
                    break;
                case 1828732223:
                    phoneCall = new TLRPC$TL_phoneCallAccepted();
                    break;
            }
            if (phoneCall == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PhoneCall", new Object[]{Integer.valueOf(i)}));
            }
            if (phoneCall != null) {
                phoneCall.readParams(abstractSerializedData, z);
            }
            return phoneCall;
        }
    }

    public static abstract class PhoneCallDiscardReason extends TLObject {
        public byte[] encrypted_key;

        public static PhoneCallDiscardReason TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PhoneCallDiscardReason phoneCallDiscardReason = null;
            switch (i) {
                case -2048646399:
                    phoneCallDiscardReason = new TLRPC$TL_phoneCallDiscardReasonMissed();
                    break;
                case -1344096199:
                    phoneCallDiscardReason = new TLRPC$TL_phoneCallDiscardReasonAllowGroupCall();
                    break;
                case -527056480:
                    phoneCallDiscardReason = new TLRPC$TL_phoneCallDiscardReasonDisconnect();
                    break;
                case -84416311:
                    phoneCallDiscardReason = new TLRPC$TL_phoneCallDiscardReasonBusy();
                    break;
                case 1471006352:
                    phoneCallDiscardReason = new TLRPC$TL_phoneCallDiscardReasonHangup();
                    break;
            }
            if (phoneCallDiscardReason == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PhoneCallDiscardReason", new Object[]{Integer.valueOf(i)}));
            }
            if (phoneCallDiscardReason != null) {
                phoneCallDiscardReason.readParams(abstractSerializedData, z);
            }
            return phoneCallDiscardReason;
        }
    }

    public static abstract class Photo extends TLObject {
        public long access_hash;
        public String caption;
        public int date;
        public int flags;
        public GeoPoint geo;
        public boolean has_stickers;
        public long id;
        public ArrayList<PhotoSize> sizes = new ArrayList();
        public int user_id;

        public static Photo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            Photo photo = null;
            switch (i) {
                case -1836524247:
                    photo = new TLRPC$TL_photo();
                    break;
                case -1014792074:
                    photo = new TLRPC$TL_photo_old2();
                    break;
                case -840088834:
                    photo = new TLRPC$TL_photo_layer55();
                    break;
                case 582313809:
                    photo = new TLRPC$TL_photo_old();
                    break;
                case 590459437:
                    photo = new TLRPC$TL_photoEmpty();
                    break;
            }
            if (photo == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in Photo", new Object[]{Integer.valueOf(i)}));
            }
            if (photo != null) {
                photo.readParams(abstractSerializedData, z);
            }
            return photo;
        }
    }

    public static abstract class PhotoSize extends TLObject {
        public byte[] bytes;
        /* renamed from: h */
        public int f10146h;
        public FileLocation location;
        public int size;
        public String type;
        /* renamed from: w */
        public int f10147w;

        public static PhotoSize TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PhotoSize photoSize = null;
            switch (i) {
                case -374917894:
                    photoSize = new TLRPC$TL_photoCachedSize();
                    break;
                case 236446268:
                    photoSize = new TLRPC$TL_photoSizeEmpty();
                    break;
                case 2009052699:
                    photoSize = new TLRPC$TL_photoSize();
                    break;
            }
            if (photoSize == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PhotoSize", new Object[]{Integer.valueOf(i)}));
            }
            if (photoSize != null) {
                photoSize.readParams(abstractSerializedData, z);
            }
            return photoSize;
        }
    }

    public static abstract class PrivacyKey extends TLObject {
        public static PrivacyKey TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PrivacyKey privacyKey = null;
            switch (i) {
                case -1137792208:
                    privacyKey = new TLRPC$TL_privacyKeyStatusTimestamp();
                    break;
                case 1030105979:
                    privacyKey = new TLRPC$TL_privacyKeyPhoneCall();
                    break;
                case 1343122938:
                    privacyKey = new TLRPC$TL_privacyKeyChatInvite();
                    break;
            }
            if (privacyKey == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PrivacyKey", new Object[]{Integer.valueOf(i)}));
            }
            if (privacyKey != null) {
                privacyKey.readParams(abstractSerializedData, z);
            }
            return privacyKey;
        }
    }

    public static abstract class PrivacyRule extends TLObject {
        public ArrayList<Integer> users = new ArrayList();

        public static PrivacyRule TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            PrivacyRule privacyRule = null;
            switch (i) {
                case -1955338397:
                    privacyRule = new TLRPC$TL_privacyValueDisallowAll();
                    break;
                case -125240806:
                    privacyRule = new TLRPC$TL_privacyValueDisallowContacts();
                    break;
                case -123988:
                    privacyRule = new TLRPC$TL_privacyValueAllowContacts();
                    break;
                case 209668535:
                    privacyRule = new TLRPC$TL_privacyValueDisallowUsers();
                    break;
                case 1297858060:
                    privacyRule = new TLRPC$TL_privacyValueAllowUsers();
                    break;
                case 1698855810:
                    privacyRule = new TLRPC$TL_privacyValueAllowAll();
                    break;
            }
            if (privacyRule == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in PrivacyRule", new Object[]{Integer.valueOf(i)}));
            }
            if (privacyRule != null) {
                privacyRule.readParams(abstractSerializedData, z);
            }
            return privacyRule;
        }
    }

    public static abstract class RecentMeUrl extends TLObject {
        public int chat_id;
        public ChatInvite chat_invite;
        public StickerSetCovered set;
        public String url;
        public int user_id;

        public static RecentMeUrl TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            RecentMeUrl recentMeUrl = null;
            switch (i) {
                case -1917045962:
                    recentMeUrl = new TLRPC$TL_recentMeUrlUser();
                    break;
                case -1608834311:
                    recentMeUrl = new TLRPC$TL_recentMeUrlChat();
                    break;
                case -1140172836:
                    recentMeUrl = new TLRPC$TL_recentMeUrlStickerSet();
                    break;
                case -347535331:
                    recentMeUrl = new TLRPC$TL_recentMeUrlChatInvite();
                    break;
                case 1189204285:
                    recentMeUrl = new TLRPC$TL_recentMeUrlUnknown();
                    break;
            }
            if (recentMeUrl == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in RecentMeUrl", new Object[]{Integer.valueOf(i)}));
            }
            if (recentMeUrl != null) {
                recentMeUrl.readParams(abstractSerializedData, z);
            }
            return recentMeUrl;
        }
    }

    public static abstract class ReplyMarkup extends TLObject {
        public int flags;
        public boolean resize;
        public ArrayList<TLRPC$TL_keyboardButtonRow> rows = new ArrayList();
        public boolean selective;
        public boolean single_use;

        public static ReplyMarkup TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ReplyMarkup replyMarkup = null;
            switch (i) {
                case -1606526075:
                    replyMarkup = new TLRPC$TL_replyKeyboardHide();
                    break;
                case -200242528:
                    replyMarkup = new TLRPC$TL_replyKeyboardForceReply();
                    break;
                case 889353612:
                    replyMarkup = new TLRPC$TL_replyKeyboardMarkup();
                    break;
                case 1218642516:
                    replyMarkup = new TLRPC$TL_replyInlineMarkup();
                    break;
            }
            if (replyMarkup == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ReplyMarkup", new Object[]{Integer.valueOf(i)}));
            }
            if (replyMarkup != null) {
                replyMarkup.readParams(abstractSerializedData, z);
            }
            return replyMarkup;
        }
    }

    public static abstract class ReportReason extends TLObject {
        public String text;

        public static ReportReason TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            ReportReason reportReason = null;
            switch (i) {
                case -512463606:
                    reportReason = new TLRPC$TL_inputReportReasonOther();
                    break;
                case 505595789:
                    reportReason = new TLRPC$TL_inputReportReasonViolence();
                    break;
                case 777640226:
                    reportReason = new TLRPC$TL_inputReportReasonPornography();
                    break;
                case 1490799288:
                    reportReason = new TLRPC$TL_inputReportReasonSpam();
                    break;
            }
            if (reportReason == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in ReportReason", new Object[]{Integer.valueOf(i)}));
            }
            if (reportReason != null) {
                reportReason.readParams(abstractSerializedData, z);
            }
            return reportReason;
        }
    }

    public static abstract class RichText extends TLObject {
        public String email;
        public RichText parentRichText;
        public ArrayList<RichText> texts = new ArrayList();
        public String url;
        public long webpage_id;

        public static RichText TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            RichText richText = null;
            switch (i) {
                case -1678197867:
                    richText = new TLRPC$TL_textStrike();
                    break;
                case -1054465340:
                    richText = new TLRPC$TL_textUnderline();
                    break;
                case -653089380:
                    richText = new TLRPC$TL_textItalic();
                    break;
                case -599948721:
                    richText = new TLRPC$TL_textEmpty();
                    break;
                case -564523562:
                    richText = new TLRPC$TL_textEmail();
                    break;
                case 1009288385:
                    richText = new TLRPC$TL_textUrl();
                    break;
                case 1730456516:
                    richText = new TLRPC$TL_textBold();
                    break;
                case 1816074681:
                    richText = new TLRPC$TL_textFixed();
                    break;
                case 1950782688:
                    richText = new TLRPC$TL_textPlain();
                    break;
                case 2120376535:
                    richText = new TLRPC$TL_textConcat();
                    break;
            }
            if (richText == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in RichText", new Object[]{Integer.valueOf(i)}));
            }
            if (richText != null) {
                richText.readParams(abstractSerializedData, z);
            }
            return richText;
        }
    }

    public static abstract class SendMessageAction extends TLObject {
        public int progress;

        public static SendMessageAction TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            SendMessageAction sendMessageAction = null;
            switch (i) {
                case -1997373508:
                    sendMessageAction = new TLRPC$TL_sendMessageRecordRoundAction();
                    break;
                case -1884362354:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadDocumentAction_old();
                    break;
                case -1845219337:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadVideoAction_old();
                    break;
                case -1727382502:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadPhotoAction_old();
                    break;
                case -1584933265:
                    sendMessageAction = new TLRPC$TL_sendMessageRecordVideoAction();
                    break;
                case -1441998364:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadDocumentAction();
                    break;
                case -774682074:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadPhotoAction();
                    break;
                case -718310409:
                    sendMessageAction = new TLRPC$TL_sendMessageRecordAudioAction();
                    break;
                case -580219064:
                    sendMessageAction = new TLRPC$TL_sendMessageGamePlayAction();
                    break;
                case -424899985:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadAudioAction_old();
                    break;
                case -378127636:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadVideoAction();
                    break;
                case -212740181:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadAudioAction();
                    break;
                case -44119819:
                    sendMessageAction = new TLRPC$TL_sendMessageCancelAction();
                    break;
                case 381645902:
                    sendMessageAction = new TLRPC$TL_sendMessageTypingAction();
                    break;
                case 393186209:
                    sendMessageAction = new TLRPC$TL_sendMessageGeoLocationAction();
                    break;
                case 608050278:
                    sendMessageAction = new TLRPC$TL_sendMessageUploadRoundAction();
                    break;
                case 1653390447:
                    sendMessageAction = new TLRPC$TL_sendMessageChooseContactAction();
                    break;
            }
            if (sendMessageAction == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in SendMessageAction", new Object[]{Integer.valueOf(i)}));
            }
            if (sendMessageAction != null) {
                sendMessageAction.readParams(abstractSerializedData, z);
            }
            return sendMessageAction;
        }
    }

    public static abstract class StickerSet extends TLObject {
        public long access_hash;
        public boolean archived;
        public int count;
        public int flags;
        public int hash;
        public long id;
        public boolean installed;
        public boolean masks;
        public boolean official;
        public String short_name;
        public String title;

        public static StickerSet TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            StickerSet stickerSet = null;
            switch (i) {
                case -1482409193:
                    stickerSet = new TLRPC$TL_stickerSet_old();
                    break;
                case -852477119:
                    stickerSet = new TLRPC$TL_stickerSet();
                    break;
            }
            if (stickerSet == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in StickerSet", new Object[]{Integer.valueOf(i)}));
            }
            if (stickerSet != null) {
                stickerSet.readParams(abstractSerializedData, z);
            }
            return stickerSet;
        }
    }

    public static abstract class StickerSetCovered extends TLObject {
        public Document cover;
        public ArrayList<Document> covers = new ArrayList();
        public StickerSet set;

        public static StickerSetCovered TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            StickerSetCovered stickerSetCovered = null;
            switch (i) {
                case 872932635:
                    stickerSetCovered = new TLRPC$TL_stickerSetMultiCovered();
                    break;
                case 1678812626:
                    stickerSetCovered = new TLRPC$TL_stickerSetCovered();
                    break;
            }
            if (stickerSetCovered == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in StickerSetCovered", new Object[]{Integer.valueOf(i)}));
            }
            if (stickerSetCovered != null) {
                stickerSetCovered.readParams(abstractSerializedData, z);
            }
            return stickerSetCovered;
        }
    }

    public static class TL_accountDaysTTL extends TLObject {
        public static int constructor = -1194283041;
        public int days;

        public static TL_accountDaysTTL TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_accountDaysTTL tL_accountDaysTTL = new TL_accountDaysTTL();
                tL_accountDaysTTL.readParams(abstractSerializedData, z);
                return tL_accountDaysTTL;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_accountDaysTTL", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.days = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.days);
        }
    }

    public static class TL_account_authorizations extends TLObject {
        public static int constructor = 307276766;
        public ArrayList<TL_authorization> authorizations = new ArrayList();

        public static TL_account_authorizations TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_account_authorizations tL_account_authorizations = new TL_account_authorizations();
                tL_account_authorizations.readParams(abstractSerializedData, z);
                return tL_account_authorizations;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_account_authorizations", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    TL_authorization TLdeserialize = TL_authorization.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.authorizations.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(481674261);
            int size = this.authorizations.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((TL_authorization) this.authorizations.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_account_changePhone extends TLObject {
        public static int constructor = 1891839707;
        public String phone_code;
        public String phone_code_hash;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return User.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.phone_code_hash);
            abstractSerializedData.writeString(this.phone_code);
        }
    }

    public static class TL_account_checkUsername extends TLObject {
        public static int constructor = 655677548;
        public String username;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.username);
        }
    }

    public static class TL_account_confirmPhone extends TLObject {
        public static int constructor = 1596029123;
        public String phone_code;
        public String phone_code_hash;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_code_hash);
            abstractSerializedData.writeString(this.phone_code);
        }
    }

    public static class TL_account_deleteAccount extends TLObject {
        public static int constructor = 1099779595;
        public String reason;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.reason);
        }
    }

    public static class TL_account_getAccountTTL extends TLObject {
        public static int constructor = 150761757;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_accountDaysTTL.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_account_getAuthorizations extends TLObject {
        public static int constructor = -484392616;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_account_authorizations.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_account_getNotifySettings extends TLObject {
        public static int constructor = 313765169;
        public InputNotifyPeer peer;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return PeerNotifySettings.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.peer.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_getPassword extends TLObject {
        public static int constructor = 1418342645;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$account_Password.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_account_getPasswordSettings extends TLObject {
        public static int constructor = -1131605573;
        public byte[] current_password_hash;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_account_passwordSettings.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.current_password_hash);
        }
    }

    public static class TL_account_getPrivacy extends TLObject {
        public static int constructor = -623130288;
        public InputPrivacyKey key;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_account_privacyRules.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.key.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_getTmpPassword extends TLObject {
        public static int constructor = 1250046590;
        public byte[] password_hash;
        public int period;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_account_tmpPassword.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.password_hash);
            abstractSerializedData.writeInt32(this.period);
        }
    }

    public static class TL_account_getWallPapers extends TLObject {
        public static int constructor = -1068696894;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            TLObject tLRPC$Vector = new TLRPC$Vector();
            int readInt32 = abstractSerializedData.readInt32(z);
            for (int i2 = 0; i2 < readInt32; i2++) {
                TLRPC$WallPaper TLdeserialize = TLRPC$WallPaper.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize == null) {
                    break;
                }
                tLRPC$Vector.objects.add(TLdeserialize);
            }
            return tLRPC$Vector;
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_account_noPassword extends TLRPC$account_Password {
        public static int constructor = -1764049896;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.new_salt = abstractSerializedData.readByteArray(z);
            this.email_unconfirmed_pattern = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.new_salt);
            abstractSerializedData.writeString(this.email_unconfirmed_pattern);
        }
    }

    public static class TL_account_password extends TLRPC$account_Password {
        public static int constructor = 2081952796;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.current_salt = abstractSerializedData.readByteArray(z);
            this.new_salt = abstractSerializedData.readByteArray(z);
            this.hint = abstractSerializedData.readString(z);
            this.has_recovery = abstractSerializedData.readBool(z);
            this.email_unconfirmed_pattern = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.current_salt);
            abstractSerializedData.writeByteArray(this.new_salt);
            abstractSerializedData.writeString(this.hint);
            abstractSerializedData.writeBool(this.has_recovery);
            abstractSerializedData.writeString(this.email_unconfirmed_pattern);
        }
    }

    public static class TL_account_passwordInputSettings extends TLObject {
        public static int constructor = -2037289493;
        public String email;
        public int flags;
        public String hint;
        public byte[] new_password_hash;
        public byte[] new_salt;

        public static TL_account_passwordInputSettings TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_account_passwordInputSettings tL_account_passwordInputSettings = new TL_account_passwordInputSettings();
                tL_account_passwordInputSettings.readParams(abstractSerializedData, z);
                return tL_account_passwordInputSettings;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_account_passwordInputSettings", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            if ((this.flags & 1) != 0) {
                this.new_salt = abstractSerializedData.readByteArray(z);
            }
            if ((this.flags & 1) != 0) {
                this.new_password_hash = abstractSerializedData.readByteArray(z);
            }
            if ((this.flags & 1) != 0) {
                this.hint = abstractSerializedData.readString(z);
            }
            if ((this.flags & 2) != 0) {
                this.email = abstractSerializedData.readString(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeByteArray(this.new_salt);
            }
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeByteArray(this.new_password_hash);
            }
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeString(this.hint);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeString(this.email);
            }
        }
    }

    public static class TL_account_passwordSettings extends TLObject {
        public static int constructor = -1212732749;
        public String email;

        public static TL_account_passwordSettings TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_account_passwordSettings tL_account_passwordSettings = new TL_account_passwordSettings();
                tL_account_passwordSettings.readParams(abstractSerializedData, z);
                return tL_account_passwordSettings;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_account_passwordSettings", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.email = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.email);
        }
    }

    public static class TL_account_privacyRules extends TLObject {
        public static int constructor = 1430961007;
        public ArrayList<PrivacyRule> rules = new ArrayList();
        public ArrayList<User> users = new ArrayList();

        public static TL_account_privacyRules TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_account_privacyRules tL_account_privacyRules = new TL_account_privacyRules();
                tL_account_privacyRules.readParams(abstractSerializedData, z);
                return tL_account_privacyRules;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_account_privacyRules", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            int i2;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                int readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    PrivacyRule TLdeserialize = PrivacyRule.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.rules.add(TLdeserialize);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    i2 = abstractSerializedData.readInt32(z);
                    while (i < i2) {
                        User TLdeserialize2 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize2 != null) {
                            this.users.add(TLdeserialize2);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            int i;
            int i2 = 0;
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(481674261);
            int size = this.rules.size();
            abstractSerializedData.writeInt32(size);
            for (i = 0; i < size; i++) {
                ((PrivacyRule) this.rules.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(481674261);
            i = this.users.size();
            abstractSerializedData.writeInt32(i);
            while (i2 < i) {
                ((User) this.users.get(i2)).serializeToStream(abstractSerializedData);
                i2++;
            }
        }
    }

    public static class TL_account_registerDevice extends TLObject {
        public static int constructor = 1669245048;
        public String token;
        public int token_type;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.token_type);
            abstractSerializedData.writeString(this.token);
        }
    }

    public static class TL_account_reportPeer extends TLObject {
        public static int constructor = -1374118561;
        public InputPeer peer;
        public ReportReason reason;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.peer.serializeToStream(abstractSerializedData);
            this.reason.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_resetAuthorization extends TLObject {
        public static int constructor = -545786948;
        public long hash;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.hash);
        }
    }

    public static class TL_account_resetNotifySettings extends TLObject {
        public static int constructor = -612493497;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_account_sendChangePhoneCode extends TLObject {
        public static int constructor = 149257707;
        public boolean allow_flashcall;
        public boolean current_number;
        public int flags;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_sentCode.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.allow_flashcall ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.phone_number);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeBool(this.current_number);
            }
        }
    }

    public static class TL_account_sendConfirmPhoneCode extends TLObject {
        public static int constructor = 353818557;
        public boolean allow_flashcall;
        public boolean current_number;
        public int flags;
        public String hash;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_sentCode.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.allow_flashcall ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.hash);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeBool(this.current_number);
            }
        }
    }

    public static class TL_account_setAccountTTL extends TLObject {
        public static int constructor = 608323678;
        public TL_accountDaysTTL ttl;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.ttl.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_setPrivacy extends TLObject {
        public static int constructor = -906486552;
        public InputPrivacyKey key;
        public ArrayList<InputPrivacyRule> rules = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_account_privacyRules.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.key.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.rules.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((InputPrivacyRule) this.rules.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_account_tmpPassword extends TLObject {
        public static int constructor = -614138572;
        public byte[] tmp_password;
        public int valid_until;

        public static TL_account_tmpPassword TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_account_tmpPassword tL_account_tmpPassword = new TL_account_tmpPassword();
                tL_account_tmpPassword.readParams(abstractSerializedData, z);
                return tL_account_tmpPassword;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_account_tmpPassword", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.tmp_password = abstractSerializedData.readByteArray(z);
            this.valid_until = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.tmp_password);
            abstractSerializedData.writeInt32(this.valid_until);
        }
    }

    public static class TL_account_unregisterDevice extends TLObject {
        public static int constructor = 1707432768;
        public String token;
        public int token_type;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.token_type);
            abstractSerializedData.writeString(this.token);
        }
    }

    public static class TL_account_updateDeviceLocked extends TLObject {
        public static int constructor = 954152242;
        public int period;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.period);
        }
    }

    public static class TL_account_updateNotifySettings extends TLObject {
        public static int constructor = -2067899501;
        public InputNotifyPeer peer;
        public TLRPC$TL_inputPeerNotifySettings settings;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.peer.serializeToStream(abstractSerializedData);
            this.settings.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_updatePasswordSettings extends TLObject {
        public static int constructor = -92517498;
        public byte[] current_password_hash;
        public TL_account_passwordInputSettings new_settings;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.current_password_hash);
            this.new_settings.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_account_updateProfile extends TLObject {
        public static int constructor = 2018596725;
        public String about;
        public String first_name;
        public int flags;
        public String last_name;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return User.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeString(this.first_name);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeString(this.last_name);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeString(this.about);
            }
        }
    }

    public static class TL_account_updateStatus extends TLObject {
        public static int constructor = 1713919532;
        public boolean offline;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeBool(this.offline);
        }
    }

    public static class TL_account_updateUsername extends TLObject {
        public static int constructor = 1040964988;
        public String username;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return User.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.username);
        }
    }

    public static class TL_audioEmpty_layer45 extends Audio {
        public static int constructor = 1483311320;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
        }
    }

    public static class TL_audio_layer45 extends Audio {
        public static int constructor = -102543275;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.date = abstractSerializedData.readInt32(z);
            this.duration = abstractSerializedData.readInt32(z);
            this.mime_type = abstractSerializedData.readString(z);
            this.size = abstractSerializedData.readInt32(z);
            this.dc_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.duration);
            abstractSerializedData.writeString(this.mime_type);
            abstractSerializedData.writeInt32(this.size);
            abstractSerializedData.writeInt32(this.dc_id);
        }
    }

    public static class TL_audioEncrypted extends TL_audio_layer45 {
        public static int constructor = 1431655926;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.user_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.duration = abstractSerializedData.readInt32(z);
            this.size = abstractSerializedData.readInt32(z);
            this.dc_id = abstractSerializedData.readInt32(z);
            this.key = abstractSerializedData.readByteArray(z);
            this.iv = abstractSerializedData.readByteArray(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.duration);
            abstractSerializedData.writeInt32(this.size);
            abstractSerializedData.writeInt32(this.dc_id);
            abstractSerializedData.writeByteArray(this.key);
            abstractSerializedData.writeByteArray(this.iv);
        }
    }

    public static class TL_audio_old2 extends TL_audio_layer45 {
        public static int constructor = -945003370;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.user_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.duration = abstractSerializedData.readInt32(z);
            this.mime_type = abstractSerializedData.readString(z);
            this.size = abstractSerializedData.readInt32(z);
            this.dc_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.duration);
            abstractSerializedData.writeString(this.mime_type);
            abstractSerializedData.writeInt32(this.size);
            abstractSerializedData.writeInt32(this.dc_id);
        }
    }

    public static class TL_audio_old extends TL_audio_layer45 {
        public static int constructor = 1114908135;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.user_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.duration = abstractSerializedData.readInt32(z);
            this.size = abstractSerializedData.readInt32(z);
            this.dc_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.duration);
            abstractSerializedData.writeInt32(this.size);
            abstractSerializedData.writeInt32(this.dc_id);
        }
    }

    public static class TL_auth_authorization extends TLObject {
        public static int constructor = -855308010;
        public int flags;
        public int tmp_sessions;
        public User user;

        public static TL_auth_authorization TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_auth_authorization tL_auth_authorization = new TL_auth_authorization();
                tL_auth_authorization.readParams(abstractSerializedData, z);
                return tL_auth_authorization;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_auth_authorization", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            if ((this.flags & 1) != 0) {
                this.tmp_sessions = abstractSerializedData.readInt32(z);
            }
            this.user = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.tmp_sessions);
            }
            this.user.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_auth_cancelCode extends TLObject {
        public static int constructor = 520357240;
        public String phone_code_hash;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.phone_code_hash);
        }
    }

    public static class TL_auth_checkPassword extends TLObject {
        public static int constructor = 174260510;
        public byte[] password_hash;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_authorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeByteArray(this.password_hash);
        }
    }

    public static class TL_auth_checkPhone extends TLObject {
        public static int constructor = 1877286395;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_checkedPhone.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
        }
    }

    public static class TL_auth_checkedPhone extends TLObject {
        public static int constructor = -2128698738;
        public boolean phone_registered;

        public static TL_auth_checkedPhone TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_auth_checkedPhone tL_auth_checkedPhone = new TL_auth_checkedPhone();
                tL_auth_checkedPhone.readParams(abstractSerializedData, z);
                return tL_auth_checkedPhone;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_auth_checkedPhone", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.phone_registered = abstractSerializedData.readBool(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeBool(this.phone_registered);
        }
    }

    public static class TL_auth_codeTypeCall extends TLRPC$auth_CodeType {
        public static int constructor = 1948046307;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_codeTypeFlashCall extends TLRPC$auth_CodeType {
        public static int constructor = 577556219;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_codeTypeSms extends TLRPC$auth_CodeType {
        public static int constructor = 1923290508;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_exportAuthorization extends TLObject {
        public static int constructor = -440401971;
        public int dc_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_exportedAuthorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.dc_id);
        }
    }

    public static class TL_auth_exportedAuthorization extends TLObject {
        public static int constructor = -543777747;
        public byte[] bytes;
        public int id;

        public static TL_auth_exportedAuthorization TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_auth_exportedAuthorization tL_auth_exportedAuthorization = new TL_auth_exportedAuthorization();
                tL_auth_exportedAuthorization.readParams(abstractSerializedData, z);
                return tL_auth_exportedAuthorization;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_auth_exportedAuthorization", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.bytes = abstractSerializedData.readByteArray(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeByteArray(this.bytes);
        }
    }

    public static class TL_auth_importAuthorization extends TLObject {
        public static int constructor = -470837741;
        public byte[] bytes;
        public int id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_authorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeByteArray(this.bytes);
        }
    }

    public static class TL_auth_logOut extends TLObject {
        public static int constructor = 1461180992;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_passwordRecovery extends TLObject {
        public static int constructor = 326715557;
        public String email_pattern;

        public static TL_auth_passwordRecovery TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_auth_passwordRecovery tL_auth_passwordRecovery = new TL_auth_passwordRecovery();
                tL_auth_passwordRecovery.readParams(abstractSerializedData, z);
                return tL_auth_passwordRecovery;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_auth_passwordRecovery", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.email_pattern = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.email_pattern);
        }
    }

    public static class TL_auth_recoverPassword extends TLObject {
        public static int constructor = 1319464594;
        public String code;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_authorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.code);
        }
    }

    public static class TL_auth_requestPasswordRecovery extends TLObject {
        public static int constructor = -661144474;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_passwordRecovery.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_resendCode extends TLObject {
        public static int constructor = 1056025023;
        public String phone_code_hash;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_sentCode.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.phone_code_hash);
        }
    }

    public static class TL_auth_resetAuthorizations extends TLObject {
        public static int constructor = -1616179942;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_auth_sendCode extends TLObject {
        public static int constructor = -2035355412;
        public boolean allow_flashcall;
        public String api_hash;
        public int api_id;
        public boolean current_number;
        public int flags;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_sentCode.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.allow_flashcall ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.phone_number);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeBool(this.current_number);
            }
            abstractSerializedData.writeInt32(this.api_id);
            abstractSerializedData.writeString(this.api_hash);
        }
    }

    public static class TL_auth_sendInvites extends TLObject {
        public static int constructor = 1998331287;
        public String message;
        public ArrayList<String> phone_numbers = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(481674261);
            int size = this.phone_numbers.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                abstractSerializedData.writeString((String) this.phone_numbers.get(i));
            }
            abstractSerializedData.writeString(this.message);
        }
    }

    public static class TL_auth_sentCode extends TLObject {
        public static int constructor = 1577067778;
        public int flags;
        public TLRPC$auth_CodeType next_type;
        public String phone_code_hash;
        public boolean phone_registered;
        public int timeout;
        public TLRPC$auth_SentCodeType type;

        public static TL_auth_sentCode TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_auth_sentCode tL_auth_sentCode = new TL_auth_sentCode();
                tL_auth_sentCode.readParams(abstractSerializedData, z);
                return tL_auth_sentCode;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_auth_sentCode", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.phone_registered = (this.flags & 1) != 0;
            this.type = TLRPC$auth_SentCodeType.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.phone_code_hash = abstractSerializedData.readString(z);
            if ((this.flags & 2) != 0) {
                this.next_type = TLRPC$auth_CodeType.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & 4) != 0) {
                this.timeout = abstractSerializedData.readInt32(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.phone_registered ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            this.type.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.phone_code_hash);
            if ((this.flags & 2) != 0) {
                this.next_type.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.timeout);
            }
        }
    }

    public static class TL_auth_sentCodeTypeApp extends TLRPC$auth_SentCodeType {
        public static int constructor = 1035688326;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.length = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.length);
        }
    }

    public static class TL_auth_sentCodeTypeCall extends TLRPC$auth_SentCodeType {
        public static int constructor = 1398007207;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.length = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.length);
        }
    }

    public static class TL_auth_sentCodeTypeFlashCall extends TLRPC$auth_SentCodeType {
        public static int constructor = -1425815847;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.pattern = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.pattern);
        }
    }

    public static class TL_auth_sentCodeTypeSms extends TLRPC$auth_SentCodeType {
        public static int constructor = -1073693790;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.length = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.length);
        }
    }

    public static class TL_auth_signIn extends TLObject {
        public static int constructor = -1126886015;
        public String phone_code;
        public String phone_code_hash;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_authorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.phone_code_hash);
            abstractSerializedData.writeString(this.phone_code);
        }
    }

    public static class TL_auth_signUp extends TLObject {
        public static int constructor = 453408308;
        public String first_name;
        public String last_name;
        public String phone_code;
        public String phone_code_hash;
        public String phone_number;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_auth_authorization.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.phone_code_hash);
            abstractSerializedData.writeString(this.phone_code);
            abstractSerializedData.writeString(this.first_name);
            abstractSerializedData.writeString(this.last_name);
        }
    }

    public static class TL_authorization extends TLObject {
        public static int constructor = 2079516406;
        public int api_id;
        public String app_name;
        public String app_version;
        public String country;
        public int date_active;
        public int date_created;
        public String device_model;
        public int flags;
        public long hash;
        public String ip;
        public String platform;
        public String region;
        public String system_version;

        public static TL_authorization TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_authorization tL_authorization = new TL_authorization();
                tL_authorization.readParams(abstractSerializedData, z);
                return tL_authorization;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_authorization", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.hash = abstractSerializedData.readInt64(z);
            this.flags = abstractSerializedData.readInt32(z);
            this.device_model = abstractSerializedData.readString(z);
            this.platform = abstractSerializedData.readString(z);
            this.system_version = abstractSerializedData.readString(z);
            this.api_id = abstractSerializedData.readInt32(z);
            this.app_name = abstractSerializedData.readString(z);
            this.app_version = abstractSerializedData.readString(z);
            this.date_created = abstractSerializedData.readInt32(z);
            this.date_active = abstractSerializedData.readInt32(z);
            this.ip = abstractSerializedData.readString(z);
            this.country = abstractSerializedData.readString(z);
            this.region = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.hash);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.device_model);
            abstractSerializedData.writeString(this.platform);
            abstractSerializedData.writeString(this.system_version);
            abstractSerializedData.writeInt32(this.api_id);
            abstractSerializedData.writeString(this.app_name);
            abstractSerializedData.writeString(this.app_version);
            abstractSerializedData.writeInt32(this.date_created);
            abstractSerializedData.writeInt32(this.date_active);
            abstractSerializedData.writeString(this.ip);
            abstractSerializedData.writeString(this.country);
            abstractSerializedData.writeString(this.region);
        }
    }

    public static class TL_boolFalse extends Bool {
        public static int constructor = -1132882121;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_boolTrue extends Bool {
        public static int constructor = -1720552011;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_botCommand extends TLObject {
        public static int constructor = -1032140601;
        public String command;
        public String description;

        public static TL_botCommand TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_botCommand tL_botCommand = new TL_botCommand();
                tL_botCommand.readParams(abstractSerializedData, z);
                return tL_botCommand;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_botCommand", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.command = abstractSerializedData.readString(z);
            this.description = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.command);
            abstractSerializedData.writeString(this.description);
        }
    }

    public static class TL_botInfo extends BotInfo {
        public static int constructor = -1729618630;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.user_id = abstractSerializedData.readInt32(z);
            this.description = abstractSerializedData.readString(z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    TL_botCommand TLdeserialize = TL_botCommand.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.commands.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeString(this.description);
            abstractSerializedData.writeInt32(481674261);
            int size = this.commands.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((TL_botCommand) this.commands.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInfoEmpty_layer48 extends TL_botInfo {
        public static int constructor = -1154598962;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_botInfo_layer48 extends TL_botInfo {
        public static int constructor = 164583517;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.user_id = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            abstractSerializedData.readString(z);
            this.description = abstractSerializedData.readString(z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    TL_botCommand TLdeserialize = TL_botCommand.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.commands.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.version);
            abstractSerializedData.writeString(TtmlNode.ANONYMOUS_REGION_ID);
            abstractSerializedData.writeString(this.description);
            abstractSerializedData.writeInt32(481674261);
            int size = this.commands.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((TL_botCommand) this.commands.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMediaResult extends BotInlineResult {
        public static int constructor = 400266251;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.id = abstractSerializedData.readString(z);
            this.type = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & 2) != 0) {
                this.document = Document.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & 4) != 0) {
                this.title = abstractSerializedData.readString(z);
            }
            if ((this.flags & 8) != 0) {
                this.description = abstractSerializedData.readString(z);
            }
            this.send_message = BotInlineMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.id);
            abstractSerializedData.writeString(this.type);
            if ((this.flags & 1) != 0) {
                this.photo.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 2) != 0) {
                this.document.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeString(this.title);
            }
            if ((this.flags & 8) != 0) {
                abstractSerializedData.writeString(this.description);
            }
            this.send_message.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_botInlineMessageMediaAuto extends BotInlineMessage {
        public static int constructor = 175419739;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.caption = abstractSerializedData.readString(z);
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.caption);
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMessageMediaContact extends BotInlineMessage {
        public static int constructor = 904770772;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.phone_number = abstractSerializedData.readString(z);
            this.first_name = abstractSerializedData.readString(z);
            this.last_name = abstractSerializedData.readString(z);
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.phone_number);
            abstractSerializedData.writeString(this.first_name);
            abstractSerializedData.writeString(this.last_name);
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMessageMediaGeo extends BotInlineMessage {
        public static int constructor = -1222451611;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.period = abstractSerializedData.readInt32(z);
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            this.geo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.period);
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMessageMediaGeo_layer71 extends TL_botInlineMessageMediaGeo {
        public static int constructor = 982505656;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            this.geo.serializeToStream(abstractSerializedData);
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMessageMediaVenue extends BotInlineMessage {
        public static int constructor = 1130767150;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.title = abstractSerializedData.readString(z);
            this.address = abstractSerializedData.readString(z);
            this.provider = abstractSerializedData.readString(z);
            this.venue_id = abstractSerializedData.readString(z);
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            this.geo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.title);
            abstractSerializedData.writeString(this.address);
            abstractSerializedData.writeString(this.provider);
            abstractSerializedData.writeString(this.venue_id);
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineMessageText extends BotInlineMessage {
        public static int constructor = -1937807902;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.no_webpage = (this.flags & 1) != 0;
            this.message = abstractSerializedData.readString(z);
            if ((this.flags & 2) != 0) {
                int readInt32;
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    readInt32 = abstractSerializedData.readInt32(z);
                    while (i < readInt32) {
                        MessageEntity TLdeserialize = MessageEntity.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize != null) {
                            this.entities.add(TLdeserialize);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
                } else {
                    return;
                }
            }
            if ((this.flags & 4) != 0) {
                this.reply_markup = ReplyMarkup.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.no_webpage ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.message);
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(481674261);
                int size = this.entities.size();
                abstractSerializedData.writeInt32(size);
                for (int i = 0; i < size; i++) {
                    ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
                }
            }
            if ((this.flags & 4) != 0) {
                this.reply_markup.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_botInlineResult extends BotInlineResult {
        public static int constructor = -1679053127;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.id = abstractSerializedData.readString(z);
            this.type = abstractSerializedData.readString(z);
            if ((this.flags & 2) != 0) {
                this.title = abstractSerializedData.readString(z);
            }
            if ((this.flags & 4) != 0) {
                this.description = abstractSerializedData.readString(z);
            }
            if ((this.flags & 8) != 0) {
                this.url = abstractSerializedData.readString(z);
            }
            if ((this.flags & 16) != 0) {
                this.thumb_url = abstractSerializedData.readString(z);
            }
            if ((this.flags & 32) != 0) {
                this.content_url = abstractSerializedData.readString(z);
            }
            if ((this.flags & 32) != 0) {
                this.content_type = abstractSerializedData.readString(z);
            }
            if ((this.flags & 64) != 0) {
                this.w = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 64) != 0) {
                this.h = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 128) != 0) {
                this.duration = abstractSerializedData.readInt32(z);
            }
            this.send_message = BotInlineMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.id);
            abstractSerializedData.writeString(this.type);
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeString(this.title);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeString(this.description);
            }
            if ((this.flags & 8) != 0) {
                abstractSerializedData.writeString(this.url);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeString(this.thumb_url);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeString(this.content_url);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeString(this.content_type);
            }
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeInt32(this.w);
            }
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeInt32(this.h);
            }
            if ((this.flags & 128) != 0) {
                abstractSerializedData.writeInt32(this.duration);
            }
            this.send_message.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_cdnFileHash extends TLObject {
        public static int constructor = 2012136335;
        public byte[] hash;
        public int limit;
        public int offset;

        public static TL_cdnFileHash TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_cdnFileHash tL_cdnFileHash = new TL_cdnFileHash();
                tL_cdnFileHash.readParams(abstractSerializedData, z);
                return tL_cdnFileHash;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_cdnFileHash", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.offset = abstractSerializedData.readInt32(z);
            this.limit = abstractSerializedData.readInt32(z);
            this.hash = abstractSerializedData.readByteArray(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.offset);
            abstractSerializedData.writeInt32(this.limit);
            abstractSerializedData.writeByteArray(this.hash);
        }
    }

    public static class TL_channel extends Chat {
        public static int constructor = 1158377749;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.left = (this.flags & 4) != 0;
            this.broadcast = (this.flags & 32) != 0;
            this.verified = (this.flags & 128) != 0;
            this.megagroup = (this.flags & 256) != 0;
            this.restricted = (this.flags & 512) != 0;
            this.democracy = (this.flags & 1024) != 0;
            this.signatures = (this.flags & 2048) != 0;
            if ((this.flags & 4096) == 0) {
                z2 = false;
            }
            this.min = z2;
            this.id = abstractSerializedData.readInt32(z);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                this.access_hash = abstractSerializedData.readInt64(z);
            }
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & 64) != 0) {
                this.username = abstractSerializedData.readString(z);
            }
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            if ((this.flags & 512) != 0) {
                this.restriction_reason = abstractSerializedData.readString(z);
            }
            if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
                this.admin_rights = TL_channelAdminRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
                this.banned_rights = TL_channelBannedRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & 131072) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.verified ? this.flags | 128 : this.flags & -129;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            this.flags = this.restricted ? this.flags | 512 : this.flags & -513;
            this.flags = this.democracy ? this.flags | 1024 : this.flags & -1025;
            this.flags = this.signatures ? this.flags | 2048 : this.flags & -2049;
            this.flags = this.min ? this.flags | 4096 : this.flags & -4097;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                abstractSerializedData.writeInt64(this.access_hash);
            }
            abstractSerializedData.writeString(this.title);
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeString(this.username);
            }
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeString(this.restriction_reason);
            }
            if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
                this.admin_rights.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
                this.banned_rights.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 131072) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
        }
    }

    public static class TL_channelAdminLogEvent extends TLObject {
        public static int constructor = 995769920;
        public ChannelAdminLogEventAction action;
        public int date;
        public long id;
        public int user_id;

        public static TL_channelAdminLogEvent TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channelAdminLogEvent tL_channelAdminLogEvent = new TL_channelAdminLogEvent();
                tL_channelAdminLogEvent.readParams(abstractSerializedData, z);
                return tL_channelAdminLogEvent;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminLogEvent", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt64(z);
            this.date = abstractSerializedData.readInt32(z);
            this.user_id = abstractSerializedData.readInt32(z);
            this.action = ChannelAdminLogEventAction.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt64(this.id);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.user_id);
            this.action.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionChangeAbout extends ChannelAdminLogEventAction {
        public static int constructor = 1427671598;
        public String new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_value = abstractSerializedData.readString(z);
            this.new_value = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.prev_value);
            abstractSerializedData.writeString(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionChangePhoto extends ChannelAdminLogEventAction {
        public static int constructor = -1204857405;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.new_photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.prev_photo.serializeToStream(abstractSerializedData);
            this.new_photo.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionChangeStickerSet extends ChannelAdminLogEventAction {
        public static int constructor = -1312568665;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_stickerset = InputStickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.new_stickerset = InputStickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.prev_stickerset.serializeToStream(abstractSerializedData);
            this.new_stickerset.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionChangeTitle extends ChannelAdminLogEventAction {
        public static int constructor = -421545947;
        public String new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_value = abstractSerializedData.readString(z);
            this.new_value = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.prev_value);
            abstractSerializedData.writeString(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionChangeUsername extends ChannelAdminLogEventAction {
        public static int constructor = 1783299128;
        public String new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_value = abstractSerializedData.readString(z);
            this.new_value = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.prev_value);
            abstractSerializedData.writeString(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionDeleteMessage extends ChannelAdminLogEventAction {
        public static int constructor = 1121994683;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.message = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.message.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionEditMessage extends ChannelAdminLogEventAction {
        public static int constructor = 1889215493;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_message = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.new_message = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.prev_message.serializeToStream(abstractSerializedData);
            this.new_message.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionParticipantInvite extends ChannelAdminLogEventAction {
        public static int constructor = -484690728;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.participant.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionParticipantJoin extends ChannelAdminLogEventAction {
        public static int constructor = 405815507;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelAdminLogEventActionParticipantLeave extends ChannelAdminLogEventAction {
        public static int constructor = -124291086;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelAdminLogEventActionParticipantToggleAdmin extends ChannelAdminLogEventAction {
        public static int constructor = -714643696;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.new_participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.prev_participant.serializeToStream(abstractSerializedData);
            this.new_participant.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionParticipantToggleBan extends ChannelAdminLogEventAction {
        public static int constructor = -422036098;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.prev_participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.new_participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.prev_participant.serializeToStream(abstractSerializedData);
            this.new_participant.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventActionToggleInvites extends ChannelAdminLogEventAction {
        public static int constructor = 460916654;
        public boolean new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.new_value = abstractSerializedData.readBool(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeBool(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionTogglePreHistoryHidden extends ChannelAdminLogEventAction {
        public static int constructor = 1599903217;
        public boolean new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.new_value = abstractSerializedData.readBool(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeBool(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionToggleSignatures extends ChannelAdminLogEventAction {
        public static int constructor = 648939889;
        public boolean new_value;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.new_value = abstractSerializedData.readBool(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeBool(this.new_value);
        }
    }

    public static class TL_channelAdminLogEventActionUpdatePinned extends ChannelAdminLogEventAction {
        public static int constructor = -370660328;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.message = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.message.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelAdminLogEventsFilter extends TLObject {
        public static int constructor = -368018716;
        public boolean ban;
        public boolean delete;
        public boolean demote;
        public boolean edit;
        public int flags;
        public boolean info;
        public boolean invite;
        public boolean join;
        public boolean kick;
        public boolean leave;
        public boolean pinned;
        public boolean promote;
        public boolean settings;
        public boolean unban;
        public boolean unkick;

        public static TL_channelAdminLogEventsFilter TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channelAdminLogEventsFilter tL_channelAdminLogEventsFilter = new TL_channelAdminLogEventsFilter();
                tL_channelAdminLogEventsFilter.readParams(abstractSerializedData, z);
                return tL_channelAdminLogEventsFilter;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminLogEventsFilter", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.join = (this.flags & 1) != 0;
            this.leave = (this.flags & 2) != 0;
            this.invite = (this.flags & 4) != 0;
            this.ban = (this.flags & 8) != 0;
            this.unban = (this.flags & 16) != 0;
            this.kick = (this.flags & 32) != 0;
            this.unkick = (this.flags & 64) != 0;
            this.promote = (this.flags & 128) != 0;
            this.demote = (this.flags & 256) != 0;
            this.info = (this.flags & 512) != 0;
            this.settings = (this.flags & 1024) != 0;
            this.pinned = (this.flags & 2048) != 0;
            this.edit = (this.flags & 4096) != 0;
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) == 0) {
                z2 = false;
            }
            this.delete = z2;
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.join ? this.flags | 1 : this.flags & -2;
            this.flags = this.leave ? this.flags | 2 : this.flags & -3;
            this.flags = this.invite ? this.flags | 4 : this.flags & -5;
            this.flags = this.ban ? this.flags | 8 : this.flags & -9;
            this.flags = this.unban ? this.flags | 16 : this.flags & -17;
            this.flags = this.kick ? this.flags | 32 : this.flags & -33;
            this.flags = this.unkick ? this.flags | 64 : this.flags & -65;
            this.flags = this.promote ? this.flags | 128 : this.flags & -129;
            this.flags = this.demote ? this.flags | 256 : this.flags & -257;
            this.flags = this.info ? this.flags | 512 : this.flags & -513;
            this.flags = this.settings ? this.flags | 1024 : this.flags & -1025;
            this.flags = this.pinned ? this.flags | 2048 : this.flags & -2049;
            this.flags = this.edit ? this.flags | 4096 : this.flags & -4097;
            this.flags = this.delete ? this.flags | MessagesController.UPDATE_MASK_CHANNEL : this.flags & -8193;
            abstractSerializedData.writeInt32(this.flags);
        }
    }

    public static class TL_channelAdminRights extends TLObject {
        public static int constructor = 1568467877;
        public boolean add_admins;
        public boolean ban_users;
        public boolean change_info;
        public boolean delete_messages;
        public boolean edit_messages;
        public int flags;
        public boolean invite_link;
        public boolean invite_users;
        public boolean pin_messages;
        public boolean post_messages;

        public static TL_channelAdminRights TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channelAdminRights tL_channelAdminRights = new TL_channelAdminRights();
                tL_channelAdminRights.readParams(abstractSerializedData, z);
                return tL_channelAdminRights;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channelAdminRights", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.change_info = (this.flags & 1) != 0;
            this.post_messages = (this.flags & 2) != 0;
            this.edit_messages = (this.flags & 4) != 0;
            this.delete_messages = (this.flags & 8) != 0;
            this.ban_users = (this.flags & 16) != 0;
            this.invite_users = (this.flags & 32) != 0;
            this.invite_link = (this.flags & 64) != 0;
            this.pin_messages = (this.flags & 128) != 0;
            if ((this.flags & 512) == 0) {
                z2 = false;
            }
            this.add_admins = z2;
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.change_info ? this.flags | 1 : this.flags & -2;
            this.flags = this.post_messages ? this.flags | 2 : this.flags & -3;
            this.flags = this.edit_messages ? this.flags | 4 : this.flags & -5;
            this.flags = this.delete_messages ? this.flags | 8 : this.flags & -9;
            this.flags = this.ban_users ? this.flags | 16 : this.flags & -17;
            this.flags = this.invite_users ? this.flags | 32 : this.flags & -33;
            this.flags = this.invite_link ? this.flags | 64 : this.flags & -65;
            this.flags = this.pin_messages ? this.flags | 128 : this.flags & -129;
            this.flags = this.add_admins ? this.flags | 512 : this.flags & -513;
            abstractSerializedData.writeInt32(this.flags);
        }
    }

    public static class TL_channelBannedRights extends TLObject {
        public static int constructor = 1489977929;
        public boolean embed_links;
        public int flags;
        public boolean send_games;
        public boolean send_gifs;
        public boolean send_inline;
        public boolean send_media;
        public boolean send_messages;
        public boolean send_stickers;
        public int until_date;
        public boolean view_messages;

        public static TL_channelBannedRights TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channelBannedRights tL_channelBannedRights = new TL_channelBannedRights();
                tL_channelBannedRights.readParams(abstractSerializedData, z);
                return tL_channelBannedRights;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channelBannedRights", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.view_messages = (this.flags & 1) != 0;
            this.send_messages = (this.flags & 2) != 0;
            this.send_media = (this.flags & 4) != 0;
            this.send_stickers = (this.flags & 8) != 0;
            this.send_gifs = (this.flags & 16) != 0;
            this.send_games = (this.flags & 32) != 0;
            this.send_inline = (this.flags & 64) != 0;
            if ((this.flags & 128) == 0) {
                z2 = false;
            }
            this.embed_links = z2;
            this.until_date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.view_messages ? this.flags | 1 : this.flags & -2;
            this.flags = this.send_messages ? this.flags | 2 : this.flags & -3;
            this.flags = this.send_media ? this.flags | 4 : this.flags & -5;
            this.flags = this.send_stickers ? this.flags | 8 : this.flags & -9;
            this.flags = this.send_gifs ? this.flags | 16 : this.flags & -17;
            this.flags = this.send_games ? this.flags | 32 : this.flags & -33;
            this.flags = this.send_inline ? this.flags | 64 : this.flags & -65;
            this.flags = this.embed_links ? this.flags | 128 : this.flags & -129;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.until_date);
        }
    }

    public static class TL_channelForbidden extends Chat {
        public static int constructor = 681420594;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.broadcast = (this.flags & 32) != 0;
            if ((this.flags & 256) == 0) {
                z2 = false;
            }
            this.megagroup = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) != 0) {
                this.until_date = abstractSerializedData.readInt32(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeString(this.title);
            if ((this.flags & C3446C.DEFAULT_BUFFER_SEGMENT_SIZE) != 0) {
                abstractSerializedData.writeInt32(this.until_date);
            }
        }
    }

    public static class TL_channelForbidden_layer52 extends TL_channelForbidden {
        public static int constructor = 763724588;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.title = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeString(this.title);
        }
    }

    public static class TL_channelForbidden_layer67 extends TL_channelForbidden {
        public static int constructor = -2059962289;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.broadcast = (this.flags & 32) != 0;
            if ((this.flags & 256) == 0) {
                z2 = false;
            }
            this.megagroup = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.title = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeString(this.title);
        }
    }

    public static class TL_channelFull extends ChatFull {
        public static int constructor = -877254512;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.can_set_stickers = (this.flags & 128) != 0;
            this.hidden_prehistory = (this.flags & 1024) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.banned_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.read_outbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 256) != 0) {
                    this.stickerset = StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                }
                if ((this.flags & 512) != 0) {
                    this.available_min_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 2048) != 0) {
                    this.call_msg_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            this.flags = this.can_set_stickers ? this.flags | 128 : this.flags & -129;
            this.flags = this.hidden_prehistory ? this.flags | 1024 : this.flags & -1025;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.banned_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.read_outbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
            if ((this.flags & 256) != 0) {
                this.stickerset.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeInt32(this.available_min_id);
            }
            if ((this.flags & 2048) != 0) {
                abstractSerializedData.writeInt32(this.call_msg_id);
            }
        }
    }

    public static class TL_channelFull_layer48 extends TL_channelFull {
        public static int constructor = -1640751649;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.unread_important_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            abstractSerializedData.writeInt32(this.unread_important_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
        }
    }

    public static class TL_channelFull_layer52 extends TL_channelFull {
        public static int constructor = -1749097118;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.unread_important_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            abstractSerializedData.writeInt32(this.unread_important_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
        }
    }

    public static class TL_channelFull_layer67 extends TL_channelFull {
        public static int constructor = -1009430225;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.read_outbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.read_outbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
        }
    }

    public static class TL_channelFull_layer70 extends TL_channelFull {
        public static int constructor = -1781833897;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.banned_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.read_outbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.banned_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.read_outbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
        }
    }

    public static class TL_channelFull_layer71 extends TL_channelFull {
        public static int constructor = 401891279;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.can_set_stickers = (this.flags & 128) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.banned_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.read_outbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 256) != 0) {
                    this.stickerset = StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            this.flags = this.can_set_stickers ? this.flags | 128 : this.flags & -129;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.banned_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.read_outbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
            if ((this.flags & 256) != 0) {
                this.stickerset.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channelFull_layer72 extends TL_channelFull {
        public static int constructor = 1991201921;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.can_set_username = (this.flags & 64) != 0;
            this.can_set_stickers = (this.flags & 128) != 0;
            this.hidden_prehistory = (this.flags & 1024) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.banned_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.read_outbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_chat_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 16) != 0) {
                    this.migrated_from_max_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 32) != 0) {
                    this.pinned_msg_id = abstractSerializedData.readInt32(z);
                }
                if ((this.flags & 256) != 0) {
                    this.stickerset = StickerSet.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                }
                if ((this.flags & 512) != 0) {
                    this.available_min_id = abstractSerializedData.readInt32(z);
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            this.flags = this.can_set_username ? this.flags | 64 : this.flags & -65;
            this.flags = this.can_set_stickers ? this.flags | 128 : this.flags & -129;
            this.flags = this.hidden_prehistory ? this.flags | 1024 : this.flags & -1025;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.banned_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.read_outbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_chat_id);
            }
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(this.migrated_from_max_id);
            }
            if ((this.flags & 32) != 0) {
                abstractSerializedData.writeInt32(this.pinned_msg_id);
            }
            if ((this.flags & 256) != 0) {
                this.stickerset.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeInt32(this.available_min_id);
            }
        }
    }

    public static class TL_channelFull_old extends TL_channelFull {
        public static int constructor = -88925533;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.can_view_participants = (this.flags & 8) != 0;
            this.id = abstractSerializedData.readInt32(z);
            this.about = abstractSerializedData.readString(z);
            if ((this.flags & 1) != 0) {
                this.participants_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 2) != 0) {
                this.admins_count = abstractSerializedData.readInt32(z);
            }
            if ((this.flags & 4) != 0) {
                this.kicked_count = abstractSerializedData.readInt32(z);
            }
            this.read_inbox_max_id = abstractSerializedData.readInt32(z);
            this.unread_count = abstractSerializedData.readInt32(z);
            this.unread_important_count = abstractSerializedData.readInt32(z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_view_participants ? this.flags | 8 : this.flags & -9;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.about);
            if ((this.flags & 1) != 0) {
                abstractSerializedData.writeInt32(this.participants_count);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(this.admins_count);
            }
            if ((this.flags & 4) != 0) {
                abstractSerializedData.writeInt32(this.kicked_count);
            }
            abstractSerializedData.writeInt32(this.read_inbox_max_id);
            abstractSerializedData.writeInt32(this.unread_count);
            abstractSerializedData.writeInt32(this.unread_important_count);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelMessagesFilter extends ChannelMessagesFilter {
        public static int constructor = -847783593;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.exclude_new_messages = (this.flags & 2) != 0;
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    TLRPC$TL_messageRange TLdeserialize = TLRPC$TL_messageRange.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.ranges.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.exclude_new_messages ? this.flags | 2 : this.flags & -3;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(481674261);
            int size = this.ranges.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((TLRPC$TL_messageRange) this.ranges.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channelMessagesFilterEmpty extends ChannelMessagesFilter {
        public static int constructor = -1798033689;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelParticipant extends ChannelParticipant {
        public static int constructor = 367766557;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_channelParticipantAdmin extends ChannelParticipant {
        public static int constructor = -1473271656;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.can_edit = (this.flags & 1) != 0;
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.promoted_by = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.admin_rights = TL_channelAdminRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.can_edit ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.promoted_by);
            abstractSerializedData.writeInt32(this.date);
            this.admin_rights.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelParticipantBanned extends ChannelParticipant {
        public static int constructor = 573315206;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.left = (this.flags & 1) != 0;
            this.user_id = abstractSerializedData.readInt32(z);
            this.kicked_by = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.banned_rights = TL_channelBannedRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.left ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.kicked_by);
            abstractSerializedData.writeInt32(this.date);
            this.banned_rights.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channelParticipantCreator extends ChannelParticipant {
        public static int constructor = -471670279;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
        }
    }

    public static class TL_channelParticipantEditor_layer67 extends TL_channelParticipantAdmin {
        public static int constructor = -1743180447;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_channelParticipantKicked_layer67 extends ChannelParticipant {
        public static int constructor = -1933187430;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.kicked_by = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.kicked_by);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_channelParticipantModerator_layer67 extends TL_channelParticipantAdmin {
        public static int constructor = -1861910545;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_channelParticipantSelf extends ChannelParticipant {
        public static int constructor = -1557620115;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_channelParticipantsAdmins extends ChannelParticipantsFilter {
        public static int constructor = -1268741783;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelParticipantsBanned extends ChannelParticipantsFilter {
        public static int constructor = 338142689;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.q = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.q);
        }
    }

    public static class TL_channelParticipantsBots extends ChannelParticipantsFilter {
        public static int constructor = -1328445861;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelParticipantsKicked extends ChannelParticipantsFilter {
        public static int constructor = -1548400251;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.q = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.q);
        }
    }

    public static class TL_channelParticipantsRecent extends ChannelParticipantsFilter {
        public static int constructor = -566281095;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channelParticipantsSearch extends ChannelParticipantsFilter {
        public static int constructor = 106343499;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.q = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.q);
        }
    }

    public static class TL_channel_layer48 extends TL_channel {
        public static int constructor = 1260090630;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.kicked = (this.flags & 2) != 0;
            this.left = (this.flags & 4) != 0;
            this.moderator = (this.flags & 16) != 0;
            this.broadcast = (this.flags & 32) != 0;
            this.verified = (this.flags & 128) != 0;
            this.megagroup = (this.flags & 256) != 0;
            this.restricted = (this.flags & 512) != 0;
            this.democracy = (this.flags & 1024) != 0;
            if ((this.flags & 2048) == 0) {
                z2 = false;
            }
            this.signatures = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & 64) != 0) {
                this.username = abstractSerializedData.readString(z);
            }
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            if ((this.flags & 512) != 0) {
                this.restriction_reason = abstractSerializedData.readString(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.moderator ? this.flags | 16 : this.flags & -17;
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.verified ? this.flags | 128 : this.flags & -129;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            this.flags = this.restricted ? this.flags | 512 : this.flags & -513;
            this.flags = this.democracy ? this.flags | 1024 : this.flags & -1025;
            this.flags = this.signatures ? this.flags | 2048 : this.flags & -2049;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeString(this.title);
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeString(this.username);
            }
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeString(this.restriction_reason);
            }
        }
    }

    public static class TL_channel_layer67 extends TL_channel {
        public static int constructor = -1588737454;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.kicked = (this.flags & 2) != 0;
            this.left = (this.flags & 4) != 0;
            this.moderator = (this.flags & 16) != 0;
            this.broadcast = (this.flags & 32) != 0;
            this.verified = (this.flags & 128) != 0;
            this.megagroup = (this.flags & 256) != 0;
            this.restricted = (this.flags & 512) != 0;
            this.democracy = (this.flags & 1024) != 0;
            this.signatures = (this.flags & 2048) != 0;
            if ((this.flags & 4096) == 0) {
                z2 = false;
            }
            this.min = z2;
            this.id = abstractSerializedData.readInt32(z);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                this.access_hash = abstractSerializedData.readInt64(z);
            }
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & 64) != 0) {
                this.username = abstractSerializedData.readString(z);
            }
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            if ((this.flags & 512) != 0) {
                this.restriction_reason = abstractSerializedData.readString(z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.moderator ? this.flags | 16 : this.flags & -17;
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.verified ? this.flags | 128 : this.flags & -129;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            this.flags = this.restricted ? this.flags | 512 : this.flags & -513;
            this.flags = this.democracy ? this.flags | 1024 : this.flags & -1025;
            this.flags = this.signatures ? this.flags | 2048 : this.flags & -2049;
            this.flags = this.min ? this.flags | 4096 : this.flags & -4097;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                abstractSerializedData.writeInt64(this.access_hash);
            }
            abstractSerializedData.writeString(this.title);
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeString(this.username);
            }
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeString(this.restriction_reason);
            }
        }
    }

    public static class TL_channel_layer72 extends TL_channel {
        public static int constructor = 213142300;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.left = (this.flags & 4) != 0;
            this.broadcast = (this.flags & 32) != 0;
            this.verified = (this.flags & 128) != 0;
            this.megagroup = (this.flags & 256) != 0;
            this.restricted = (this.flags & 512) != 0;
            this.democracy = (this.flags & 1024) != 0;
            this.signatures = (this.flags & 2048) != 0;
            if ((this.flags & 4096) == 0) {
                z2 = false;
            }
            this.min = z2;
            this.id = abstractSerializedData.readInt32(z);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                this.access_hash = abstractSerializedData.readInt64(z);
            }
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & 64) != 0) {
                this.username = abstractSerializedData.readString(z);
            }
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            if ((this.flags & 512) != 0) {
                this.restriction_reason = abstractSerializedData.readString(z);
            }
            if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
                this.admin_rights = TL_channelAdminRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
            if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
                this.banned_rights = TL_channelBannedRights.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.verified ? this.flags | 128 : this.flags & -129;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            this.flags = this.restricted ? this.flags | 512 : this.flags & -513;
            this.flags = this.democracy ? this.flags | 1024 : this.flags & -1025;
            this.flags = this.signatures ? this.flags | 2048 : this.flags & -2049;
            this.flags = this.min ? this.flags | 4096 : this.flags & -4097;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                abstractSerializedData.writeInt64(this.access_hash);
            }
            abstractSerializedData.writeString(this.title);
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeString(this.username);
            }
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
            if ((this.flags & 512) != 0) {
                abstractSerializedData.writeString(this.restriction_reason);
            }
            if ((this.flags & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
                this.admin_rights.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & TLRPC.MESSAGE_FLAG_EDITED) != 0) {
                this.banned_rights.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channel_old extends TL_channel {
        public static int constructor = 1737397639;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.kicked = (this.flags & 2) != 0;
            this.left = (this.flags & 4) != 0;
            this.moderator = (this.flags & 16) != 0;
            this.broadcast = (this.flags & 32) != 0;
            this.verified = (this.flags & 128) != 0;
            this.megagroup = (this.flags & 256) != 0;
            if ((this.flags & 512) == 0) {
                z2 = false;
            }
            this.explicit_content = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.access_hash = abstractSerializedData.readInt64(z);
            this.title = abstractSerializedData.readString(z);
            if ((this.flags & 64) != 0) {
                this.username = abstractSerializedData.readString(z);
            }
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.moderator ? this.flags | 16 : this.flags & -17;
            this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
            this.flags = this.verified ? this.flags | 128 : this.flags & -129;
            this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
            this.flags = this.explicit_content ? this.flags | 512 : this.flags & -513;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeInt64(this.access_hash);
            abstractSerializedData.writeString(this.title);
            if ((this.flags & 64) != 0) {
                abstractSerializedData.writeString(this.username);
            }
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
        }
    }

    public static class TL_channels_adminLogResults extends TLObject {
        public static int constructor = -309659827;
        public ArrayList<Chat> chats = new ArrayList();
        public ArrayList<TL_channelAdminLogEvent> events = new ArrayList();
        public ArrayList<User> users = new ArrayList();

        public static TL_channels_adminLogResults TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channels_adminLogResults tL_channels_adminLogResults = new TL_channels_adminLogResults();
                tL_channels_adminLogResults.readParams(abstractSerializedData, z);
                return tL_channels_adminLogResults;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channels_adminLogResults", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            int i2;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                int readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    TL_channelAdminLogEvent TLdeserialize = TL_channelAdminLogEvent.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.events.add(TLdeserialize);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    readInt32 = abstractSerializedData.readInt32(z);
                    i2 = 0;
                    while (i2 < readInt32) {
                        Chat TLdeserialize2 = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize2 != null) {
                            this.chats.add(TLdeserialize2);
                            i2++;
                        } else {
                            return;
                        }
                    }
                    if (abstractSerializedData.readInt32(z) == 481674261) {
                        i2 = abstractSerializedData.readInt32(z);
                        while (i < i2) {
                            User TLdeserialize3 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                            if (TLdeserialize3 != null) {
                                this.users.add(TLdeserialize3);
                                i++;
                            } else {
                                return;
                            }
                        }
                    } else if (z) {
                        throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            int i;
            int i2 = 0;
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(481674261);
            int size = this.events.size();
            abstractSerializedData.writeInt32(size);
            for (i = 0; i < size; i++) {
                ((TL_channelAdminLogEvent) this.events.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(481674261);
            size = this.chats.size();
            abstractSerializedData.writeInt32(size);
            for (i = 0; i < size; i++) {
                ((Chat) this.chats.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(481674261);
            i = this.users.size();
            abstractSerializedData.writeInt32(i);
            while (i2 < i) {
                ((User) this.users.get(i2)).serializeToStream(abstractSerializedData);
                i2++;
            }
        }
    }

    public static class TL_channels_channelParticipant extends TLObject {
        public static int constructor = -791039645;
        public ChannelParticipant participant;
        public ArrayList<User> users = new ArrayList();

        public static TL_channels_channelParticipant TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channels_channelParticipant tL_channels_channelParticipant = new TL_channels_channelParticipant();
                tL_channels_channelParticipant.readParams(abstractSerializedData, z);
                return tL_channels_channelParticipant;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channels_channelParticipant", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.participant = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.users.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.participant.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.users.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((User) this.users.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channels_channelParticipants extends TLRPC$channels_ChannelParticipants {
        public static int constructor = -177282392;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.count = abstractSerializedData.readInt32(z);
            int i2;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                int readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    ChannelParticipant TLdeserialize = ChannelParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.participants.add(TLdeserialize);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    i2 = abstractSerializedData.readInt32(z);
                    while (i < i2) {
                        User TLdeserialize2 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize2 != null) {
                            this.users.add(TLdeserialize2);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            int i;
            int i2 = 0;
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.count);
            abstractSerializedData.writeInt32(481674261);
            int size = this.participants.size();
            abstractSerializedData.writeInt32(size);
            for (i = 0; i < size; i++) {
                ((ChannelParticipant) this.participants.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(481674261);
            i = this.users.size();
            abstractSerializedData.writeInt32(i);
            while (i2 < i) {
                ((User) this.users.get(i2)).serializeToStream(abstractSerializedData);
                i2++;
            }
        }
    }

    public static class TL_channels_channelParticipantsNotModified extends TLRPC$channels_ChannelParticipants {
        public static int constructor = -266911767;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channels_checkUsername extends TLObject {
        public static int constructor = 283557164;
        public InputChannel channel;
        public String username;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.username);
        }
    }

    public static class TL_channels_createChannel extends TLObject {
        public static int constructor = -192332417;
        public String about;
        public boolean broadcast;
        public int flags;
        public boolean megagroup;
        public String title;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.broadcast ? this.flags | 1 : this.flags & -2;
            this.flags = this.megagroup ? this.flags | 2 : this.flags & -3;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.title);
            abstractSerializedData.writeString(this.about);
        }
    }

    public static class TL_channels_deleteChannel extends TLObject {
        public static int constructor = -1072619549;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_deleteHistory extends TLObject {
        public static int constructor = -1355375294;
        public InputChannel channel;
        public int max_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.max_id);
        }
    }

    public static class TL_channels_deleteMessages extends TLObject {
        public static int constructor = -2067661490;
        public InputChannel channel;
        public ArrayList<Integer> id = new ArrayList();

        public static TL_channels_deleteMessages TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_channels_deleteMessages tL_channels_deleteMessages = new TL_channels_deleteMessages();
                tL_channels_deleteMessages.readParams(abstractSerializedData, z);
                return tL_channels_deleteMessages;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_channels_deleteMessages", new Object[]{Integer.valueOf(i)}));
            }
        }

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_messages_affectedMessages.TLdeserialize(abstractSerializedData, i, z);
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.channel = InputChannel.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    this.id.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
                    i++;
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.id.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
            }
        }
    }

    public static class TL_channels_deleteUserHistory extends TLObject {
        public static int constructor = -787622117;
        public InputChannel channel;
        public InputUser user_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_messages_affectedHistory.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.user_id.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_editAbout extends TLObject {
        public static int constructor = 333610782;
        public String about;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.about);
        }
    }

    public static class TL_channels_editAdmin extends TLObject {
        public static int constructor = 548962836;
        public TL_channelAdminRights admin_rights;
        public InputChannel channel;
        public InputUser user_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.user_id.serializeToStream(abstractSerializedData);
            this.admin_rights.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_editBanned extends TLObject {
        public static int constructor = -1076292147;
        public TL_channelBannedRights banned_rights;
        public InputChannel channel;
        public InputUser user_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.user_id.serializeToStream(abstractSerializedData);
            this.banned_rights.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_editPhoto extends TLObject {
        public static int constructor = -248621111;
        public InputChannel channel;
        public InputChatPhoto photo;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.photo.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_editTitle extends TLObject {
        public static int constructor = 1450044624;
        public InputChannel channel;
        public String title;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.title);
        }
    }

    public static class TL_channels_exportInvite extends TLObject {
        public static int constructor = -950663035;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return ExportedChatInvite.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_exportMessageLink extends TLObject {
        public static int constructor = -934882771;
        public InputChannel channel;
        public int id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_exportedMessageLink.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.id);
        }
    }

    public static class TL_channels_getAdminLog extends TLObject {
        public static int constructor = 870184064;
        public ArrayList<InputUser> admins = new ArrayList();
        public InputChannel channel;
        public TL_channelAdminLogEventsFilter events_filter;
        public int flags;
        public int limit;
        public long max_id;
        public long min_id;
        /* renamed from: q */
        public String f10148q;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_channels_adminLogResults.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.f10148q);
            if ((this.flags & 1) != 0) {
                this.events_filter.serializeToStream(abstractSerializedData);
            }
            if ((this.flags & 2) != 0) {
                abstractSerializedData.writeInt32(481674261);
                int size = this.admins.size();
                abstractSerializedData.writeInt32(size);
                for (int i = 0; i < size; i++) {
                    ((InputUser) this.admins.get(i)).serializeToStream(abstractSerializedData);
                }
            }
            abstractSerializedData.writeInt64(this.max_id);
            abstractSerializedData.writeInt64(this.min_id);
            abstractSerializedData.writeInt32(this.limit);
        }
    }

    public static class TL_channels_getAdminedPublicChannels extends TLObject {
        public static int constructor = -1920105769;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_messages_chats.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_channels_getChannels extends TLObject {
        public static int constructor = 176122811;
        public ArrayList<InputChannel> id = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_messages_chats.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(481674261);
            int size = this.id.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((InputChannel) this.id.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channels_getFullChannel extends TLObject {
        public static int constructor = 141781513;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$TL_messages_chatFull.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_getMessages extends TLObject {
        public static int constructor = -1814580409;
        public InputChannel channel;
        public ArrayList<Integer> id = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$messages_Messages.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.id.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
            }
        }
    }

    public static class TL_channels_getParticipant extends TLObject {
        public static int constructor = 1416484774;
        public InputChannel channel;
        public InputUser user_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TL_channels_channelParticipant.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.user_id.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_getParticipants extends TLObject {
        public static int constructor = 306054633;
        public InputChannel channel;
        public ChannelParticipantsFilter filter;
        public int hash;
        public int limit;
        public int offset;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$channels_ChannelParticipants.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.filter.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.offset);
            abstractSerializedData.writeInt32(this.limit);
            abstractSerializedData.writeInt32(this.hash);
        }
    }

    public static class TL_channels_inviteToChannel extends TLObject {
        public static int constructor = 429865580;
        public InputChannel channel;
        public ArrayList<InputUser> users = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.users.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((InputUser) this.users.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_channels_joinChannel extends TLObject {
        public static int constructor = 615851205;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_leaveChannel extends TLObject {
        public static int constructor = -130635115;
        public InputChannel channel;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_readHistory extends TLObject {
        public static int constructor = -871347913;
        public InputChannel channel;
        public int max_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.max_id);
        }
    }

    public static class TL_channels_readMessageContents extends TLObject {
        public static int constructor = -357180360;
        public InputChannel channel;
        public ArrayList<Integer> id = new ArrayList();

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.id.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
            }
        }
    }

    public static class TL_channels_reportSpam extends TLObject {
        public static int constructor = -32999408;
        public InputChannel channel;
        public ArrayList<Integer> id = new ArrayList();
        public InputUser user_id;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.user_id.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.id.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                abstractSerializedData.writeInt32(((Integer) this.id.get(i)).intValue());
            }
        }
    }

    public static class TL_channels_setStickers extends TLObject {
        public static int constructor = -359881479;
        public InputChannel channel;
        public InputStickerSet stickerset;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            this.stickerset.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_channels_toggleInvites extends TLObject {
        public static int constructor = 1231065863;
        public InputChannel channel;
        public boolean enabled;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeBool(this.enabled);
        }
    }

    public static class TL_channels_togglePreHistoryHidden extends TLObject {
        public static int constructor = -356796084;
        public InputChannel channel;
        public boolean enabled;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeBool(this.enabled);
        }
    }

    public static class TL_channels_toggleSignatures extends TLObject {
        public static int constructor = 527021574;
        public InputChannel channel;
        public boolean enabled;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeBool(this.enabled);
        }
    }

    public static class TL_channels_updatePinnedMessage extends TLObject {
        public static int constructor = -1490162350;
        public InputChannel channel;
        public int flags;
        public int id;
        public boolean silent;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.silent ? this.flags | 1 : this.flags & -2;
            abstractSerializedData.writeInt32(this.flags);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.id);
        }
    }

    public static class TL_channels_updateUsername extends TLObject {
        public static int constructor = 890549214;
        public InputChannel channel;
        public String username;

        public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            return Bool.TLdeserialize(abstractSerializedData, i, z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.channel.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeString(this.username);
        }
    }

    public static class TL_chat extends Chat {
        public static int constructor = -652419756;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.kicked = (this.flags & 2) != 0;
            this.left = (this.flags & 4) != 0;
            this.admins_enabled = (this.flags & 8) != 0;
            this.admin = (this.flags & 16) != 0;
            if ((this.flags & 32) == 0) {
                z2 = false;
            }
            this.deactivated = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.title = abstractSerializedData.readString(z);
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.participants_count = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
            if ((this.flags & 64) != 0) {
                this.migrated_to = InputChannel.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.admins_enabled ? this.flags | 8 : this.flags & -9;
            this.flags = this.admin ? this.flags | 16 : this.flags & -17;
            this.flags = this.deactivated ? this.flags | 32 : this.flags & -33;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.title);
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.participants_count);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
            if ((this.flags & 64) != 0) {
                this.migrated_to.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_chatChannelParticipant extends ChatParticipant {
        public static int constructor = -925415106;
        public ChannelParticipant channelParticipant;
    }

    public static class TL_chatEmpty extends Chat {
        public static int constructor = -1683826688;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.title = "DELETED";
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
        }
    }

    public static class TL_chatForbidden extends Chat {
        public static int constructor = 120753115;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.title = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.title);
        }
    }

    public static class TL_chatForbidden_old extends TL_chatForbidden {
        public static int constructor = -83047359;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.title = abstractSerializedData.readString(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.title);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_chatFull extends ChatFull {
        public static int constructor = 771925524;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.id = abstractSerializedData.readInt32(z);
            this.participants = ChatParticipants.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.chat_photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.exported_invite = ExportedChatInvite.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    BotInfo TLdeserialize = BotInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.bot_info.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            this.participants.serializeToStream(abstractSerializedData);
            this.chat_photo.serializeToStream(abstractSerializedData);
            this.notify_settings.serializeToStream(abstractSerializedData);
            this.exported_invite.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(481674261);
            int size = this.bot_info.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((BotInfo) this.bot_info.get(i)).serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_chatInvite extends ChatInvite {
        public static int constructor = -613092008;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.flags = abstractSerializedData.readInt32(z);
            this.channel = (this.flags & 1) != 0;
            this.broadcast = (this.flags & 2) != 0;
            this.isPublic = (this.flags & 4) != 0;
            this.megagroup = (this.flags & 8) != 0;
            this.title = abstractSerializedData.readString(z);
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.participants_count = abstractSerializedData.readInt32(z);
            if ((this.flags & 16) != 0) {
                int readInt32;
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    readInt32 = abstractSerializedData.readInt32(z);
                    while (i < readInt32) {
                        User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize != null) {
                            this.participants.add(TLdeserialize);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
                }
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.channel ? this.flags | 1 : this.flags & -2;
            this.flags = this.broadcast ? this.flags | 2 : this.flags & -3;
            this.flags = this.isPublic ? this.flags | 4 : this.flags & -5;
            this.flags = this.megagroup ? this.flags | 8 : this.flags & -9;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeString(this.title);
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.participants_count);
            if ((this.flags & 16) != 0) {
                abstractSerializedData.writeInt32(481674261);
                int size = this.participants.size();
                abstractSerializedData.writeInt32(size);
                for (int i = 0; i < size; i++) {
                    ((User) this.participants.get(i)).serializeToStream(abstractSerializedData);
                }
            }
        }
    }

    public static class TL_chatInviteAlready extends ChatInvite {
        public static int constructor = 1516793212;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.chat = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.chat.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_chatInviteEmpty extends ExportedChatInvite {
        public static int constructor = 1776236393;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_chatInviteExported extends ExportedChatInvite {
        public static int constructor = -64092740;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.link = abstractSerializedData.readString(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeString(this.link);
        }
    }

    public static class TL_chatLocated extends TLObject {
        public static int constructor = 909233996;
        public int chat_id;
        public int distance;

        public static TL_chatLocated TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            if (constructor == i) {
                TL_chatLocated tL_chatLocated = new TL_chatLocated();
                tL_chatLocated.readParams(abstractSerializedData, z);
                return tL_chatLocated;
            } else if (!z) {
                return null;
            } else {
                throw new RuntimeException(String.format("can't parse magic %x in TL_chatLocated", new Object[]{Integer.valueOf(i)}));
            }
        }

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.chat_id = abstractSerializedData.readInt32(z);
            this.distance = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.chat_id);
            abstractSerializedData.writeInt32(this.distance);
        }
    }

    public static class TL_chatParticipant extends ChatParticipant {
        public static int constructor = -925415106;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_chatParticipantAdmin extends ChatParticipant {
        public static int constructor = -489233354;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
            this.inviter_id = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
            abstractSerializedData.writeInt32(this.inviter_id);
            abstractSerializedData.writeInt32(this.date);
        }
    }

    public static class TL_chatParticipantCreator extends ChatParticipant {
        public static int constructor = -636267638;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.user_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.user_id);
        }
    }

    public static class TL_chatParticipants extends ChatParticipants {
        public static int constructor = 1061556205;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.chat_id = abstractSerializedData.readInt32(z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    ChatParticipant TLdeserialize = ChatParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.participants.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                this.version = abstractSerializedData.readInt32(z);
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.chat_id);
            abstractSerializedData.writeInt32(481674261);
            int size = this.participants.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((ChatParticipant) this.participants.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(this.version);
        }
    }

    public static class TL_chatParticipantsForbidden extends ChatParticipants {
        public static int constructor = -57668565;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.flags = abstractSerializedData.readInt32(z);
            this.chat_id = abstractSerializedData.readInt32(z);
            if ((this.flags & 1) != 0) {
                this.self_participant = ChatParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.chat_id);
            if ((this.flags & 1) != 0) {
                this.self_participant.serializeToStream(abstractSerializedData);
            }
        }
    }

    public static class TL_chatParticipantsForbidden_old extends TL_chatParticipantsForbidden {
        public static int constructor = 265468810;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.chat_id = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.chat_id);
        }
    }

    public static class TL_chatParticipants_old extends TL_chatParticipants {
        public static int constructor = 2017571861;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            int i = 0;
            this.chat_id = abstractSerializedData.readInt32(z);
            this.admin_id = abstractSerializedData.readInt32(z);
            int readInt32;
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                while (i < readInt32) {
                    ChatParticipant TLdeserialize = ChatParticipant.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize != null) {
                        this.participants.add(TLdeserialize);
                        i++;
                    } else {
                        return;
                    }
                }
                this.version = abstractSerializedData.readInt32(z);
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
            }
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.chat_id);
            abstractSerializedData.writeInt32(this.admin_id);
            abstractSerializedData.writeInt32(481674261);
            int size = this.participants.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((ChatParticipant) this.participants.get(i)).serializeToStream(abstractSerializedData);
            }
            abstractSerializedData.writeInt32(this.version);
        }
    }

    public static class TL_chatPhoto extends ChatPhoto {
        public static int constructor = 1632839530;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.photo_small = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.photo_big = FileLocation.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.photo_small.serializeToStream(abstractSerializedData);
            this.photo_big.serializeToStream(abstractSerializedData);
        }
    }

    public static class TL_chatPhotoEmpty extends ChatPhoto {
        public static int constructor = 935395612;

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
        }
    }

    public static class TL_chat_old2 extends TL_chat {
        public static int constructor = 1930607688;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            boolean z2 = true;
            this.flags = abstractSerializedData.readInt32(z);
            this.creator = (this.flags & 1) != 0;
            this.kicked = (this.flags & 2) != 0;
            this.left = (this.flags & 4) != 0;
            this.admins_enabled = (this.flags & 8) != 0;
            this.admin = (this.flags & 16) != 0;
            if ((this.flags & 32) == 0) {
                z2 = false;
            }
            this.deactivated = z2;
            this.id = abstractSerializedData.readInt32(z);
            this.title = abstractSerializedData.readString(z);
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.participants_count = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.version = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            this.flags = this.creator ? this.flags | 1 : this.flags & -2;
            this.flags = this.kicked ? this.flags | 2 : this.flags & -3;
            this.flags = this.left ? this.flags | 4 : this.flags & -5;
            this.flags = this.admins_enabled ? this.flags | 8 : this.flags & -9;
            this.flags = this.admin ? this.flags | 16 : this.flags & -17;
            this.flags = this.deactivated ? this.flags | 32 : this.flags & -33;
            abstractSerializedData.writeInt32(this.flags);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.title);
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.participants_count);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeInt32(this.version);
        }
    }

    public static class TL_chat_old extends TL_chat {
        public static int constructor = 1855757255;

        public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
            this.id = abstractSerializedData.readInt32(z);
            this.title = abstractSerializedData.readString(z);
            this.photo = ChatPhoto.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
            this.participants_count = abstractSerializedData.readInt32(z);
            this.date = abstractSerializedData.readInt32(z);
            this.left = abstractSerializedData.readBool(z);
            this.version = abstractSerializedData.readInt32(z);
        }

        public void serializeToStream(AbstractSerializedData abstractSerializedData) {
            abstractSerializedData.writeInt32(constructor);
            abstractSerializedData.writeInt32(this.id);
            abstractSerializedData.writeString(this.title);
            this.photo.serializeToStream(abstractSerializedData);
            abstractSerializedData.writeInt32(this.participants_count);
            abstractSerializedData.writeInt32(this.date);
            abstractSerializedData.writeBool(this.left);
            abstractSerializedData.writeInt32(this.version);
        }
    }

    public static class User extends TLObject {
        public long access_hash;
        public boolean bot;
        public boolean bot_chat_history;
        public int bot_info_version;
        public boolean bot_inline_geo;
        public String bot_inline_placeholder;
        public boolean bot_nochats;
        public boolean contact;
        public boolean deleted;
        public boolean explicit_content;
        public String first_name;
        public int flags;
        public int id;
        public boolean inactive;
        public String lang_code;
        public String last_name;
        public boolean min;
        public boolean mutual_contact;
        public String phone;
        public TLRPC$UserProfilePhoto photo;
        public boolean restricted;
        public String restriction_reason;
        public boolean self;
        public TLRPC$UserStatus status;
        public String username;
        public boolean verified;

        public static User TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
            User user = null;
            switch (i) {
                case -1298475060:
                    user = new TLRPC$TL_userDeleted_old();
                    break;
                case -894214632:
                    user = new TLRPC$TL_userContact_old2();
                    break;
                case -787638374:
                    user = new TLRPC$TL_user_layer65();
                    break;
                case -704549510:
                    user = new TLRPC$TL_userDeleted_old2();
                    break;
                case -640891665:
                    user = new TLRPC$TL_userRequest_old2();
                    break;
                case -218397927:
                    user = new TLRPC$TL_userContact_old();
                    break;
                case 123533224:
                    user = new TLRPC$TL_userForeign_old2();
                    break;
                case 476112392:
                    user = new TLRPC$TL_userSelf_old3();
                    break;
                case 537022650:
                    user = new TLRPC$TL_userEmpty();
                    break;
                case 585404530:
                    user = new TLRPC$TL_user_old();
                    break;
                case 585682608:
                    user = new TLRPC$TL_userRequest_old();
                    break;
                case 773059779:
                    user = new TLRPC$TL_user();
                    break;
                case 1377093789:
                    user = new TLRPC$TL_userForeign_old();
                    break;
                case 1879553105:
                    user = new TLRPC$TL_userSelf_old2();
                    break;
                case 1912944108:
                    user = new TLRPC$TL_userSelf_old();
                    break;
            }
            if (user == null && z) {
                throw new RuntimeException(String.format("can't parse magic %x in User", new Object[]{Integer.valueOf(i)}));
            }
            if (user != null) {
                user.readParams(abstractSerializedData, z);
            }
            return user;
        }
    }
}
