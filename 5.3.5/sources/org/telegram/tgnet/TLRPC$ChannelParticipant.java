package org.telegram.tgnet;

public abstract class TLRPC$ChannelParticipant extends TLObject {
    public TLRPC$TL_channelAdminRights admin_rights;
    public TLRPC$TL_channelBannedRights banned_rights;
    public boolean can_edit;
    public int date;
    public int flags;
    public int inviter_id;
    public int kicked_by;
    public boolean left;
    public int promoted_by;
    public int user_id;

    public static TLRPC$ChannelParticipant TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChannelParticipant result = null;
        switch (constructor) {
            case -1933187430:
                result = new TLRPC$TL_channelParticipantKicked_layer67();
                break;
            case -1861910545:
                result = new TLRPC$TL_channelParticipantModerator_layer67();
                break;
            case -1743180447:
                result = new TLRPC$TL_channelParticipantEditor_layer67();
                break;
            case -1557620115:
                result = new TLRPC$TL_channelParticipantSelf();
                break;
            case -1473271656:
                result = new TLRPC$TL_channelParticipantAdmin();
                break;
            case -471670279:
                result = new TLRPC$TL_channelParticipantCreator();
                break;
            case 367766557:
                result = new TLRPC$TL_channelParticipant();
                break;
            case 573315206:
                result = new TLRPC$TL_channelParticipantBanned();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChannelParticipant", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
