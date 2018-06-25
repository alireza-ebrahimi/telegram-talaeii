package org.telegram.tgnet;

public abstract class TLRPC$SendMessageAction extends TLObject {
    public int progress;

    public static TLRPC$SendMessageAction TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$SendMessageAction result = null;
        switch (constructor) {
            case -1997373508:
                result = new TLRPC$TL_sendMessageRecordRoundAction();
                break;
            case -1884362354:
                result = new TLRPC$TL_sendMessageUploadDocumentAction_old();
                break;
            case -1845219337:
                result = new TLRPC$TL_sendMessageUploadVideoAction_old();
                break;
            case -1727382502:
                result = new TLRPC$TL_sendMessageUploadPhotoAction_old();
                break;
            case -1584933265:
                result = new TLRPC$TL_sendMessageRecordVideoAction();
                break;
            case -1441998364:
                result = new TLRPC$TL_sendMessageUploadDocumentAction();
                break;
            case -774682074:
                result = new TLRPC$TL_sendMessageUploadPhotoAction();
                break;
            case -718310409:
                result = new TLRPC$TL_sendMessageRecordAudioAction();
                break;
            case -580219064:
                result = new TLRPC$TL_sendMessageGamePlayAction();
                break;
            case -424899985:
                result = new TLRPC$TL_sendMessageUploadAudioAction_old();
                break;
            case -378127636:
                result = new TLRPC$TL_sendMessageUploadVideoAction();
                break;
            case -212740181:
                result = new TLRPC$TL_sendMessageUploadAudioAction();
                break;
            case -44119819:
                result = new TLRPC$TL_sendMessageCancelAction();
                break;
            case 381645902:
                result = new TLRPC$TL_sendMessageTypingAction();
                break;
            case 393186209:
                result = new TLRPC$TL_sendMessageGeoLocationAction();
                break;
            case 608050278:
                result = new TLRPC$TL_sendMessageUploadRoundAction();
                break;
            case 1653390447:
                result = new TLRPC$TL_sendMessageChooseContactAction();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in SendMessageAction", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
