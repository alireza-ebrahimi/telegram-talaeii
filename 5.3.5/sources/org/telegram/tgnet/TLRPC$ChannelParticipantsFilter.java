package org.telegram.tgnet;

public abstract class TLRPC$ChannelParticipantsFilter extends TLObject {
    /* renamed from: q */
    public String f69q;

    public static TLRPC$ChannelParticipantsFilter TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChannelParticipantsFilter result = null;
        switch (constructor) {
            case -1548400251:
                result = new TLRPC$TL_channelParticipantsKicked();
                break;
            case -1328445861:
                result = new TLRPC$TL_channelParticipantsBots();
                break;
            case -1268741783:
                result = new TLRPC$TL_channelParticipantsAdmins();
                break;
            case -566281095:
                result = new TLRPC$TL_channelParticipantsRecent();
                break;
            case 106343499:
                result = new TLRPC$TL_channelParticipantsSearch();
                break;
            case 338142689:
                result = new TLRPC$TL_channelParticipantsBanned();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChannelParticipantsFilter", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
