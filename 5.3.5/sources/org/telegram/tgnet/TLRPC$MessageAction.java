package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$MessageAction extends TLObject {
    public String address;
    public TLRPC$TL_inputGroupCall call;
    public long call_id;
    public int channel_id;
    public int chat_id;
    public String currency;
    public int duration;
    public TLRPC$DecryptedMessageAction encryptedAction;
    public int flags;
    public long game_id;
    public int inviter_id;
    public String message;
    public TLRPC$UserProfilePhoto newUserPhoto;
    public TLRPC$Photo photo;
    public TLRPC$PhoneCallDiscardReason reason;
    public int score;
    public String title;
    public long total_amount;
    public int ttl;
    public int user_id;
    public ArrayList<Integer> users = new ArrayList();

    public static TLRPC$MessageAction TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$MessageAction result = null;
        switch (constructor) {
            case -2132731265:
                result = new TLRPC$TL_messageActionPhoneCall();
                break;
            case -1834538890:
                result = new TLRPC$TL_messageActionGameScore();
                break;
            case -1799538451:
                result = new TLRPC$TL_messageActionPinMessage();
                break;
            case -1781355374:
                result = new TLRPC$TL_messageActionChannelCreate();
                break;
            case -1780220945:
                result = new TLRPC$TL_messageActionChatDeletePhoto();
                break;
            case -1615153660:
                result = new TLRPC$TL_messageActionHistoryClear();
                break;
            case -1503425638:
                result = new TLRPC$TL_messageActionChatCreate();
                break;
            case -1336546578:
                result = new TLRPC$TL_messageActionChannelMigrateFrom();
                break;
            case -1297179892:
                result = new TLRPC$TL_messageActionChatDeleteUser();
                break;
            case -1247687078:
                result = new TLRPC$TL_messageActionChatEditTitle();
                break;
            case -1230047312:
                result = new TLRPC$TL_messageActionEmpty();
                break;
            case -123931160:
                result = new TLRPC$TL_messageActionChatJoinedByLink();
                break;
            case -85549226:
                result = new TLRPC$TL_messageActionCustomAction();
                break;
            case 209540062:
                result = new TLRPC$TL_messageActionGeoChatCheckin();
                break;
            case 1080663248:
                result = new TLRPC$TL_messageActionPaymentSent();
                break;
            case 1200788123:
                result = new TLRPC$TL_messageActionScreenshotTaken();
                break;
            case 1217033015:
                result = new TLRPC$TL_messageActionChatAddUser();
                break;
            case 1371385889:
                result = new TLRPC$TL_messageActionChatMigrateTo();
                break;
            case 1431655760:
                result = new TLRPC$TL_messageActionUserJoined();
                break;
            case 1431655761:
                result = new TLRPC$TL_messageActionUserUpdatedPhoto();
                break;
            case 1431655762:
                result = new TLRPC$TL_messageActionTTLChange();
                break;
            case 1431655767:
                result = new TLRPC$TL_messageActionCreatedBroadcastList();
                break;
            case 1431655925:
                result = new TLRPC$TL_messageActionLoginUnknownLocation();
                break;
            case 1431655927:
                result = new TLRPC$TL_messageEncryptedAction();
                break;
            case 1581055051:
                result = new TLRPC$TL_messageActionChatAddUser_old();
                break;
            case 1862504124:
                result = new TLRPC$TL_messageActionGeoChatCreate();
                break;
            case 2047704898:
                result = new TLRPC$TL_messageActionGroupCall();
                break;
            case 2144015272:
                result = new TLRPC$TL_messageActionChatEditPhoto();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in MessageAction", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
