package org.telegram.tgnet;

public abstract class TLRPC$GeoChatMessage extends TLObject {
    public TLRPC$MessageAction action;
    public int chat_id;
    public int date;
    public int from_id;
    public int id;
    public TLRPC$MessageMedia media;
    public String message;

    public static TLRPC$GeoChatMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$GeoChatMessage result = null;
        switch (constructor) {
            case -749755826:
                result = new TLRPC$TL_geoChatMessageService();
                break;
            case 1158019297:
                result = new TLRPC$TL_geoChatMessage();
                break;
            case 1613830811:
                result = new TLRPC$TL_geoChatMessageEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in GeoChatMessage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
