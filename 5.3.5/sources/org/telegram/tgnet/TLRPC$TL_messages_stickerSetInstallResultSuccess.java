package org.telegram.tgnet;

public class TLRPC$TL_messages_stickerSetInstallResultSuccess extends TLRPC$messages_StickerSetInstallResult {
    public static int constructor = 946083368;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
