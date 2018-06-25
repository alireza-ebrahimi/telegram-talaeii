package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$ChatParticipants extends TLObject {
    public int admin_id;
    public int chat_id;
    public int flags;
    public ArrayList<TLRPC$ChatParticipant> participants = new ArrayList();
    public TLRPC$ChatParticipant self_participant;
    public int version;

    public static TLRPC$ChatParticipants TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChatParticipants result = null;
        switch (constructor) {
            case -57668565:
                result = new TLRPC$TL_chatParticipantsForbidden();
                break;
            case 265468810:
                result = new TLRPC$TL_chatParticipantsForbidden_old();
                break;
            case 1061556205:
                result = new TLRPC$TL_chatParticipants();
                break;
            case 2017571861:
                result = new TLRPC$TL_chatParticipants_old();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatParticipants", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
