package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$messages_StickerSetInstallResult extends TLObject {
    public ArrayList<TLRPC$StickerSetCovered> sets = new ArrayList();

    public static TLRPC$messages_StickerSetInstallResult TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_StickerSetInstallResult result = null;
        switch (constructor) {
            case 904138920:
                result = new TLRPC$TL_messages_stickerSetInstallResultArchive();
                break;
            case 946083368:
                result = new TLRPC$TL_messages_stickerSetInstallResultSuccess();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_StickerSetInstallResult", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
