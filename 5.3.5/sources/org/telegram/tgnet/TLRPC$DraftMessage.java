package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$DraftMessage extends TLObject {
    public int date;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public String message;
    public boolean no_webpage;
    public int reply_to_msg_id;

    public static TLRPC$DraftMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$DraftMessage result = null;
        switch (constructor) {
            case -1169445179:
                result = new TLRPC$TL_draftMessageEmpty();
                break;
            case -40996577:
                result = new TLRPC$TL_draftMessage();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in DraftMessage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
