package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.ChatParticipants;
import org.telegram.tgnet.TLRPC.ContactLink;
import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.GroupCallParticipant;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.PeerNotifySettings;
import org.telegram.tgnet.TLRPC.PhoneCall;
import org.telegram.tgnet.TLRPC.PrivacyKey;
import org.telegram.tgnet.TLRPC.PrivacyRule;
import org.telegram.tgnet.TLRPC.SendMessageAction;

public abstract class TLRPC$Update extends TLObject {
    public SendMessageAction action;
    public int available_min_id;
    public boolean blocked;
    public int channel_id;
    public EncryptedChat chat;
    public int chat_id;
    public long chat_instance;
    public byte[] data;
    public int date;
    public ArrayList<TLRPC$TL_dcOption> dc_options = new ArrayList();
    public TLRPC$TL_langPackDifference difference;
    public DraftMessage draft;
    public boolean enabled;
    public ArrayList<MessageEntity> entities = new ArrayList();
    public String first_name;
    public int flags;
    public ContactLink foreign_link;
    public String game_short_name;
    public GeoPoint geo;
    public int inbox_date;
    public int inviter_id;
    public boolean is_admin;
    public PrivacyKey key;
    public String last_name;
    public boolean masks;
    public int max_date;
    public int max_id;
    public MessageMedia media;
    public ArrayList<Integer> messages = new ArrayList();
    public ContactLink my_link;
    public PeerNotifySettings notify_settings;
    public String offset;
    public GroupCallParticipant participant;
    public ChatParticipants participants;
    public String phone;
    public PhoneCall phone_call;
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
    public ArrayList<PrivacyRule> rules = new ArrayList();
    public TLRPC$UserStatus status;
    public TLRPC$TL_messages_stickerSet stickerset;
    public String type;
    public int user_id;
    public String username;
    public int version;
    public int views;
    public TLRPC$WebPage webpage;

    public static TLRPC$Update TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Update tLRPC$Update = null;
        switch (i) {
            case -2131957734:
                tLRPC$Update = new TLRPC$TL_updateUserBlocked();
                break;
            case -2046916883:
                tLRPC$Update = new TLRPC$TL_updateGroupCall();
                break;
            case -1987495099:
                tLRPC$Update = new TLRPC$TL_updateChannelReadMessagesContents();
                break;
            case -1906403213:
                tLRPC$Update = new TLRPC$TL_updateDcOptions();
                break;
            case -1821035490:
                tLRPC$Update = new TLRPC$TL_updateSavedGifs();
                break;
            case -1791935732:
                tLRPC$Update = new TLRPC$TL_updateUserPhoto();
                break;
            case -1738988427:
                tLRPC$Update = new TLRPC$TL_updateChannelPinnedMessage();
                break;
            case -1734268085:
                tLRPC$Update = new TLRPC$TL_updateChannelMessageViews();
                break;
            case -1721631396:
                tLRPC$Update = new TLRPC$TL_updateReadHistoryInbox();
                break;
            case -1706939360:
                tLRPC$Update = new TLRPC$TL_updateRecentStickers();
                break;
            case -1704596961:
                tLRPC$Update = new TLRPC$TL_updateChatUserTyping();
                break;
            case -1657903163:
                tLRPC$Update = new TLRPC$TL_updateContactLink();
                break;
            case -1576161051:
                tLRPC$Update = new TLRPC$TL_updateDeleteMessages();
                break;
            case -1574314746:
                tLRPC$Update = new TLRPC$TL_updateConfig();
                break;
            case -1489818765:
                tLRPC$Update = new TLRPC$TL_updateUserName();
                break;
            case -1425052898:
                tLRPC$Update = new TLRPC$TL_updatePhoneCall();
                break;
            case -1264392051:
                tLRPC$Update = new TLRPC$TL_updateEncryption();
                break;
            case -1232070311:
                tLRPC$Update = new TLRPC$TL_updateChatParticipantAdmin();
                break;
            case -1227598250:
                tLRPC$Update = new TLRPC$TL_updateChannel();
                break;
            case -1094555409:
                tLRPC$Update = new TLRPC$TL_updateNotifySettings();
                break;
            case -1015733815:
                tLRPC$Update = new TLRPC$TL_updateDeleteChannelMessages();
                break;
            case -686710068:
                tLRPC$Update = new TLRPC$TL_updateDialogPinned();
                break;
            case -657787251:
                tLRPC$Update = new TLRPC$TL_updatePinnedDialogs();
                break;
            case -469536605:
                tLRPC$Update = new TLRPC$TL_updateEditMessage();
                break;
            case -451831443:
                tLRPC$Update = new TLRPC$TL_updateFavedStickers();
                break;
            case -415938591:
                tLRPC$Update = new TLRPC$TL_updateBotCallbackQuery();
                break;
            case -364179876:
                tLRPC$Update = new TLRPC$TL_updateChatParticipantAdd();
                break;
            case -352032773:
                tLRPC$Update = new TLRPC$TL_updateChannelTooLong();
                break;
            case -337352679:
                tLRPC$Update = new TLRPC$TL_updateServiceNotification();
                break;
            case -299124375:
                tLRPC$Update = new TLRPC$TL_updateDraftMessage();
                break;
            case -298113238:
                tLRPC$Update = new TLRPC$TL_updatePrivacy();
                break;
            case -103646630:
                tLRPC$Update = new TLRPC$TL_updateInlineBotCallbackQuery();
                break;
            case 92188360:
                tLRPC$Update = new TLRPC$TL_updateGroupCallParticipant();
                break;
            case 125178264:
                tLRPC$Update = new TLRPC$TL_updateChatParticipants();
                break;
            case 196268545:
                tLRPC$Update = new TLRPC$TL_updateStickerSetsOrder();
                break;
            case 239663460:
                tLRPC$Update = new TLRPC$TL_updateBotInlineSend();
                break;
            case 281165899:
                tLRPC$Update = new TLRPC$TL_updateLangPackTooLong();
                break;
            case 314130811:
                tLRPC$Update = new TLRPC$TL_updateUserPhone();
                break;
            case 314359194:
                tLRPC$Update = new TLRPC$TL_updateNewEncryptedMessage();
                break;
            case 386986326:
                tLRPC$Update = new TLRPC$TL_updateEncryptedChatTyping();
                break;
            case 457133559:
                tLRPC$Update = new TLRPC$TL_updateEditChannelMessage();
                break;
            case 469489699:
                tLRPC$Update = new TLRPC$TL_updateUserStatus();
                break;
            case 522914557:
                tLRPC$Update = new TLRPC$TL_updateNewMessage();
                break;
            case 628472761:
                tLRPC$Update = new TLRPC$TL_updateContactRegistered();
                break;
            case 634833351:
                tLRPC$Update = new TLRPC$TL_updateReadChannelOutbox();
                break;
            case 791617983:
                tLRPC$Update = new TLRPC$TL_updateReadHistoryOutbox();
                break;
            case 861169551:
                tLRPC$Update = new TLRPC$TL_updatePtsChanged();
                break;
            case 956179895:
                tLRPC$Update = new TLRPC$TL_updateEncryptedMessagesRead();
                break;
            case 1081547008:
                tLRPC$Update = new TLRPC$TL_updateChannelWebPage();
                break;
            case 1108669311:
                tLRPC$Update = new TLRPC$TL_updateReadChannelInbox();
                break;
            case 1135492588:
                tLRPC$Update = new TLRPC$TL_updateStickerSets();
                break;
            case 1318109142:
                tLRPC$Update = new TLRPC$TL_updateMessageID();
                break;
            case 1417832080:
                tLRPC$Update = new TLRPC$TL_updateBotInlineQuery();
                break;
            case 1442983757:
                tLRPC$Update = new TLRPC$TL_updateLangPack();
                break;
            case 1461528386:
                tLRPC$Update = new TLRPC$TL_updateReadFeaturedStickers();
                break;
            case 1516823543:
                tLRPC$Update = new TLRPC$TL_updateNewGeoChatMessage();
                break;
            case 1548249383:
                tLRPC$Update = new TLRPC$TL_updateUserTyping();
                break;
            case 1656358105:
                tLRPC$Update = new TLRPC$TL_updateNewChannelMessage();
                break;
            case 1753886890:
                tLRPC$Update = new TLRPC$TL_updateNewStickerSet();
                break;
            case 1757493555:
                tLRPC$Update = new TLRPC$TL_updateReadMessagesContents();
                break;
            case 1851755554:
                tLRPC$Update = new TLRPC$TL_updateChatParticipantDelete();
                break;
            case 1855224129:
                tLRPC$Update = new TLRPC$TL_updateChatAdmins();
                break;
            case 1887741886:
                tLRPC$Update = new TLRPC$TL_updateContactsReset();
                break;
            case 1893427255:
                tLRPC$Update = new TLRPC$TL_updateChannelAvailableMessages();
                break;
            case 2139689491:
                tLRPC$Update = new TLRPC$TL_updateWebPage();
                break;
        }
        if (tLRPC$Update == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in Update", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$Update != null) {
            tLRPC$Update.readParams(abstractSerializedData, z);
        }
        return tLRPC$Update;
    }
}
