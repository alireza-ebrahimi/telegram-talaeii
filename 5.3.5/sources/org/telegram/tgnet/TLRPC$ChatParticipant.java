package org.telegram.tgnet;

public abstract class TLRPC$ChatParticipant extends TLObject {
    public int date;
    public int inviter_id;
    public int user_id;

    public static TLRPC$ChatParticipant TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChatParticipant result = null;
        switch (constructor) {
            case -925415106:
                result = new TLRPC$TL_chatParticipant();
                break;
            case -636267638:
                result = new TLRPC$TL_chatParticipantCreator();
                break;
            case -489233354:
                result = new TLRPC$TL_chatParticipantAdmin();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatParticipant", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
