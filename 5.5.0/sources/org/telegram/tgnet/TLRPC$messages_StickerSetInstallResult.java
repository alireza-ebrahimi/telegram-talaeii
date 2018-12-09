package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.StickerSetCovered;

public abstract class TLRPC$messages_StickerSetInstallResult extends TLObject {
    public ArrayList<StickerSetCovered> sets = new ArrayList();

    public static TLRPC$messages_StickerSetInstallResult TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_StickerSetInstallResult tLRPC$messages_StickerSetInstallResult = null;
        switch (i) {
            case 904138920:
                tLRPC$messages_StickerSetInstallResult = new TLRPC$TL_messages_stickerSetInstallResultArchive();
                break;
            case 946083368:
                tLRPC$messages_StickerSetInstallResult = new TLRPC$TL_messages_stickerSetInstallResultSuccess();
                break;
        }
        if (tLRPC$messages_StickerSetInstallResult == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_StickerSetInstallResult", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_StickerSetInstallResult != null) {
            tLRPC$messages_StickerSetInstallResult.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_StickerSetInstallResult;
    }
}
