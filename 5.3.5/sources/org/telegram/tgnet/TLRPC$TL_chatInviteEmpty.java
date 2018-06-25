package org.telegram.tgnet;

public class TLRPC$TL_chatInviteEmpty extends TLRPC$ExportedChatInvite {
    public static int constructor = 1776236393;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
