package org.telegram.tgnet;

public abstract class TLRPC$GroupCallParticipant extends TLObject {
    public int date;
    public int flags;
    public int inviter_id;
    public byte[] member_tag_hash;
    public TLRPC$TL_inputPhoneCall phone_call;
    public boolean readonly;
    public byte[] streams;
    public int user_id;

    public static TLRPC$GroupCallParticipant TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$GroupCallParticipant result = null;
        switch (constructor) {
            case 930387696:
                result = new TLRPC$TL_groupCallParticipantInvited();
                break;
            case 1100680690:
                result = new TLRPC$TL_groupCallParticipantLeft();
                break;
            case 1326135736:
                result = new TLRPC$TL_groupCallParticipantAdmin();
                break;
            case 1486730135:
                result = new TLRPC$TL_groupCallParticipant();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in GroupCallParticipant", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
