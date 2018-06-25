package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$Updates extends TLObject {
    public int chat_id;
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public int date;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public int from_id;
    public TLRPC$MessageFwdHeader fwd_from;
    public int id;
    public TLRPC$MessageMedia media;
    public boolean media_unread;
    public boolean mentioned;
    public String message;
    public boolean out;
    public int pts;
    public int pts_count;
    public int reply_to_msg_id;
    public int seq;
    public int seq_start;
    public boolean silent;
    public TLRPC$Update update;
    public ArrayList<TLRPC$Update> updates = new ArrayList();
    public int user_id;
    public ArrayList<User> users = new ArrayList();
    public int via_bot_id;

    public static TLRPC$Updates TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Updates result = null;
        switch (constructor) {
            case -1857044719:
                result = new TLRPC$TL_updateShortMessage();
                break;
            case -484987010:
                result = new TLRPC$TL_updatesTooLong();
                break;
            case 301019932:
                result = new TLRPC$TL_updateShortSentMessage();
                break;
            case 377562760:
                result = new TLRPC$TL_updateShortChatMessage();
                break;
            case 1918567619:
                result = new TLRPC$TL_updatesCombined();
                break;
            case 1957577280:
                result = new TLRPC$TL_updates();
                break;
            case 2027216577:
                result = new TLRPC$TL_updateShort();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Updates", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
