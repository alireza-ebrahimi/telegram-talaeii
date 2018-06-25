package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$ChatFull extends TLObject {
    public String about;
    public int admins_count;
    public int available_min_id;
    public int banned_count;
    public ArrayList<TLRPC$BotInfo> bot_info = new ArrayList();
    public int call_msg_id;
    public boolean can_set_stickers;
    public boolean can_set_username;
    public boolean can_view_participants;
    public TLRPC$Photo chat_photo;
    public TLRPC$ExportedChatInvite exported_invite;
    public int flags;
    public boolean hidden_prehistory;
    public int id;
    public int kicked_count;
    public int migrated_from_chat_id;
    public int migrated_from_max_id;
    public TLRPC$PeerNotifySettings notify_settings;
    public TLRPC$ChatParticipants participants;
    public int participants_count;
    public int pinned_msg_id;
    public int read_inbox_max_id;
    public int read_outbox_max_id;
    public TLRPC$StickerSet stickerset;
    public int unread_count;
    public int unread_important_count;

    public static TLRPC$ChatFull TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChatFull result = null;
        switch (constructor) {
            case -1781833897:
                result = new TLRPC$TL_channelFull_layer70();
                break;
            case -1749097118:
                result = new TLRPC$TL_channelFull_layer52();
                break;
            case -1640751649:
                result = new TLRPC$TL_channelFull_layer48();
                break;
            case -1009430225:
                result = new TLRPC$TL_channelFull_layer67();
                break;
            case -877254512:
                result = new TLRPC$TL_channelFull();
                break;
            case -88925533:
                result = new TLRPC$TL_channelFull_old();
                break;
            case 401891279:
                result = new TLRPC$TL_channelFull_layer71();
                break;
            case 771925524:
                result = new TLRPC$TL_chatFull();
                break;
            case 1991201921:
                result = new TLRPC$TL_channelFull_layer72();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatFull", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
