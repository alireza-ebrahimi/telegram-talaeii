package org.telegram.tgnet;

public abstract class TLRPC$ChannelAdminLogEventAction extends TLObject {
    public TLRPC$Message message;
    public TLRPC$Message new_message;
    public TLRPC$ChannelParticipant new_participant;
    public TLRPC$ChatPhoto new_photo;
    public TLRPC$InputStickerSet new_stickerset;
    public TLRPC$ChannelParticipant participant;
    public TLRPC$Message prev_message;
    public TLRPC$ChannelParticipant prev_participant;
    public TLRPC$ChatPhoto prev_photo;
    public TLRPC$InputStickerSet prev_stickerset;
    public String prev_value;

    public static TLRPC$ChannelAdminLogEventAction TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChannelAdminLogEventAction result = null;
        switch (constructor) {
            case -1312568665:
                result = new TLRPC$TL_channelAdminLogEventActionChangeStickerSet();
                break;
            case -1204857405:
                result = new TLRPC$TL_channelAdminLogEventActionChangePhoto();
                break;
            case -714643696:
                result = new TLRPC$TL_channelAdminLogEventActionParticipantToggleAdmin();
                break;
            case -484690728:
                result = new TLRPC$TL_channelAdminLogEventActionParticipantInvite();
                break;
            case -422036098:
                result = new TLRPC$TL_channelAdminLogEventActionParticipantToggleBan();
                break;
            case -421545947:
                result = new TLRPC$TL_channelAdminLogEventActionChangeTitle();
                break;
            case -370660328:
                result = new TLRPC$TL_channelAdminLogEventActionUpdatePinned();
                break;
            case -124291086:
                result = new TLRPC$TL_channelAdminLogEventActionParticipantLeave();
                break;
            case 405815507:
                result = new TLRPC$TL_channelAdminLogEventActionParticipantJoin();
                break;
            case 460916654:
                result = new TLRPC$TL_channelAdminLogEventActionToggleInvites();
                break;
            case 648939889:
                result = new TLRPC$TL_channelAdminLogEventActionToggleSignatures();
                break;
            case 1121994683:
                result = new TLRPC$TL_channelAdminLogEventActionDeleteMessage();
                break;
            case 1427671598:
                result = new TLRPC$TL_channelAdminLogEventActionChangeAbout();
                break;
            case 1599903217:
                result = new TLRPC$TL_channelAdminLogEventActionTogglePreHistoryHidden();
                break;
            case 1783299128:
                result = new TLRPC$TL_channelAdminLogEventActionChangeUsername();
                break;
            case 1889215493:
                result = new TLRPC$TL_channelAdminLogEventActionEditMessage();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChannelAdminLogEventAction", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
