package org.telegram.tgnet;

public abstract class TLRPC$InputChatPhoto extends TLObject {
    public TLRPC$InputFile file;
    public TLRPC$InputPhoto id;

    public static TLRPC$InputChatPhoto TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputChatPhoto result = null;
        switch (constructor) {
            case -1991004873:
                result = new TLRPC$TL_inputChatPhoto();
                break;
            case -1837345356:
                result = new TLRPC$TL_inputChatUploadedPhoto();
                break;
            case 480546647:
                result = new TLRPC$TL_inputChatPhotoEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputChatPhoto", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
