package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$Update extends TLObject {
    public TLRPC$SendMessageAction action;
    public int available_min_id;
    public boolean blocked;
    public int channel_id;
    public TLRPC$EncryptedChat chat;
    public int chat_id;
    public long chat_instance;
    public byte[] data;
    public int date;
    public ArrayList<TLRPC$TL_dcOption> dc_options = new ArrayList();
    public TLRPC$TL_langPackDifference difference;
    public TLRPC$DraftMessage draft;
    public boolean enabled;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public String first_name;
    public int flags;
    public TLRPC$ContactLink foreign_link;
    public String game_short_name;
    public TLRPC$GeoPoint geo;
    public int inbox_date;
    public int inviter_id;
    public boolean is_admin;
    public TLRPC$PrivacyKey key;
    public String last_name;
    public boolean masks;
    public int max_date;
    public int max_id;
    public TLRPC$MessageMedia media;
    public ArrayList<Integer> messages = new ArrayList();
    public TLRPC$ContactLink my_link;
    public TLRPC$PeerNotifySettings notify_settings;
    public String offset;
    public TLRPC$GroupCallParticipant participant;
    public TLRPC$ChatParticipants participants;
    public String phone;
    public TLRPC$PhoneCall phone_call;
    public TLRPC$UserProfilePhoto photo;
    public boolean pinned;
    public boolean popup;
    public boolean previous;
    public int pts;
    public int pts_count;
    public int qts;
    public String query;
    public long query_id;
    public long random_id;
    public ArrayList<TLRPC$PrivacyRule> rules = new ArrayList();
    public TLRPC$UserStatus status;
    public TLRPC$TL_messages_stickerSet stickerset;
    public String type;
    public int user_id;
    public String username;
    public int version;
    public int views;
    public TLRPC$WebPage webpage;

    public static TLRPC$Update TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Update result = null;
        switch (constructor) {
            case -2131957734:
                result = new TLRPC$TL_updateUserBlocked();
                break;
            case -2046916883:
                result = new TLRPC$TL_updateGroupCall();
                break;
            case -1987495099:
                result = new TLRPC$TL_updateChannelReadMessagesContents();
                break;
            case -1906403213:
                result = new TLRPC$TL_updateDcOptions();
                break;
            case -1821035490:
                result = new TLRPC$TL_updateSavedGifs();
                break;
            case -1791935732:
                result = new TLRPC$TL_updateUserPhoto();
                break;
            case -1738988427:
                result = new TLRPC$TL_updateChannelPinnedMessage();
                break;
            case -1734268085:
                result = new TLRPC$TL_updateChannelMessageViews();
                break;
            case -1721631396:
                result = new TLRPC$TL_updateReadHistoryInbox();
                break;
            case -1706939360:
                result = new TLRPC$TL_updateRecentStickers();
                break;
            case -1704596961:
                result = new TLRPC$TL_updateChatUserTyping();
                break;
            case -1657903163:
                result = new TLRPC$TL_updateContactLink();
                break;
            case -1576161051:
                result = new TLRPC$TL_updateDeleteMessages();
                break;
            case -1574314746:
                result = new TLRPC$TL_updateConfig();
                break;
            case -1489818765:
                result = new TLRPC$TL_updateUserName();
                break;
            case -1425052898:
                result = new TLRPC$TL_updatePhoneCall();
                break;
            case -1264392051:
                result = new TLRPC$TL_updateEncryption();
                break;
            case -1232070311:
                result = new TLRPC$TL_updateChatParticipantAdmin();
                break;
            case -1227598250:
                result = new TLRPC$TL_updateChannel();
                break;
            case -1094555409:
                result = new TLRPC$TL_updateNotifySettings();
                break;
            case -1015733815:
                result = new TLRPC$TL_updateDeleteChannelMessages();
                break;
            case -686710068:
                result = new TLRPC$TL_updateDialogPinned();
                break;
            case -657787251:
                result = new TLRPC$TL_updatePinnedDialogs();
                break;
            case -469536605:
                result = new TLRPC$TL_updateEditMessage();
                break;
            case -451831443:
                result = new TLRPC$TL_updateFavedStickers();
                break;
            case -415938591:
                result = new TLRPC$TL_updateBotCallbackQuery();
                break;
            case -364179876:
                result = new TLRPC$TL_updateChatParticipantAdd();
                break;
            case -352032773:
                result = new TLRPC$TL_updateChannelTooLong();
                break;
            case -337352679:
                result = new TLRPC$TL_updateServiceNotification();
                break;
            case -299124375:
                result = new TLRPC$TL_updateDraftMessage();
                break;
            case -298113238:
                result = new TLRPC$TL_updatePrivacy();
                break;
            case -103646630:
                result = new TLRPC$TL_updateInlineBotCallbackQuery();
                break;
            case 92188360:
                result = new TLRPC$TL_updateGroupCallParticipant();
                break;
            case 125178264:
                result = new TLRPC$TL_updateChatParticipants();
                break;
            case 196268545:
                result = new TLRPC$TL_updateStickerSetsOrder();
                break;
            case 239663460:
                result = new TLRPC$TL_updateBotInlineSend();
                break;
            case 281165899:
                result = new TLRPC$TL_updateLangPackTooLong();
                break;
            case 314130811:
                result = new TLRPC$TL_updateUserPhone();
                break;
            case 314359194:
                result = new TLRPC$TL_updateNewEncryptedMessage();
                break;
            case 386986326:
                result = new TLRPC$TL_updateEncryptedChatTyping();
                break;
            case 457133559:
                result = new TLRPC$TL_updateEditChannelMessage();
                break;
            case 469489699:
                result = new TLRPC$TL_updateUserStatus();
                break;
            case 522914557:
                result = new TLRPC$TL_updateNewMessage();
                break;
            case 628472761:
                result = new TLRPC$TL_updateContactRegistered();
                break;
            case 634833351:
                result = new TLRPC$TL_updateReadChannelOutbox();
                break;
            case 791617983:
                result = new TLRPC$TL_updateReadHistoryOutbox();
                break;
            case 861169551:
                result = new TLRPC$TL_updatePtsChanged();
                break;
            case 956179895:
                result = new TLRPC$TL_updateEncryptedMessagesRead();
                break;
            case 1081547008:
                result = new TLRPC$TL_updateChannelWebPage();
                break;
            case 1108669311:
                result = new TLRPC$TL_updateReadChannelInbox();
                break;
            case 1135492588:
                result = new TLRPC$TL_updateStickerSets();
                break;
            case 1318109142:
                result = new TLRPC$TL_updateMessageID();
                break;
            case 1417832080:
                result = new TLRPC$TL_updateBotInlineQuery();
                break;
            case 1442983757:
                result = new TLRPC$TL_updateLangPack();
                break;
            case 1461528386:
                result = new TLRPC$TL_updateReadFeaturedStickers();
                break;
            case 1516823543:
                result = new TLRPC$TL_updateNewGeoChatMessage();
                break;
            case 1548249383:
                result = new TLRPC$TL_updateUserTyping();
                break;
            case 1656358105:
                result = new TLRPC$TL_updateNewChannelMessage();
                break;
            case 1753886890:
                result = new TLRPC$TL_updateNewStickerSet();
                break;
            case 1757493555:
                result = new TLRPC$TL_updateReadMessagesContents();
                break;
            case 1851755554:
                result = new TLRPC$TL_updateChatParticipantDelete();
                break;
            case 1855224129:
                result = new TLRPC$TL_updateChatAdmins();
                break;
            case 1887741886:
                result = new TLRPC$TL_updateContactsReset();
                break;
            case 1893427255:
                result = new TLRPC$TL_updateChannelAvailableMessages();
                break;
            case 2139689491:
                result = new TLRPC$TL_updateWebPage();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Update", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
