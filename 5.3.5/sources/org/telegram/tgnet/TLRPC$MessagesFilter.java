package org.telegram.tgnet;

public abstract class TLRPC$MessagesFilter extends TLObject {
    public int flags;
    public boolean missed;

    public static TLRPC$MessagesFilter TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$MessagesFilter result = null;
        switch (constructor) {
            case -2134272152:
                result = new TLRPC$TL_inputMessagesFilterPhoneCalls();
                break;
            case -1777752804:
                result = new TLRPC$TL_inputMessagesFilterPhotos();
                break;
            case -1629621880:
                result = new TLRPC$TL_inputMessagesFilterDocument();
                break;
            case -1614803355:
                result = new TLRPC$TL_inputMessagesFilterVideo();
                break;
            case -1253451181:
                result = new TLRPC$TL_inputMessagesFilterRoundVideo();
                break;
            case -1040652646:
                result = new TLRPC$TL_inputMessagesFilterMyMentions();
                break;
            case -648121413:
                result = new TLRPC$TL_inputMessagesFilterPhotoVideoDocuments();
                break;
            case -530392189:
                result = new TLRPC$TL_inputMessagesFilterContacts();
                break;
            case -419271411:
                result = new TLRPC$TL_inputMessagesFilterGeo();
                break;
            case -3644025:
                result = new TLRPC$TL_inputMessagesFilterGif();
                break;
            case 928101534:
                result = new TLRPC$TL_inputMessagesFilterMusic();
                break;
            case 975236280:
                result = new TLRPC$TL_inputMessagesFilterChatPhotos();
                break;
            case 1358283666:
                result = new TLRPC$TL_inputMessagesFilterVoice();
                break;
            case 1458172132:
                result = new TLRPC$TL_inputMessagesFilterPhotoVideo();
                break;
            case 1474492012:
                result = new TLRPC$TL_inputMessagesFilterEmpty();
                break;
            case 2054952868:
                result = new TLRPC$TL_inputMessagesFilterRoundVoice();
                break;
            case 2129714567:
                result = new TLRPC$TL_inputMessagesFilterUrl();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in MessagesFilter", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
