package org.telegram.tgnet;

public abstract class TLRPC$ExportedChatInvite extends TLObject {
    public String link;

    public static TLRPC$ExportedChatInvite TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ExportedChatInvite result = null;
        switch (constructor) {
            case -64092740:
                result = new TLRPC$TL_chatInviteExported();
                break;
            case 1776236393:
                result = new TLRPC$TL_chatInviteEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ExportedChatInvite", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
