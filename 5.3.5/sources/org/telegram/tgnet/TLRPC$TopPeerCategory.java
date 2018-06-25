package org.telegram.tgnet;

public abstract class TLRPC$TopPeerCategory extends TLObject {
    public static TLRPC$TopPeerCategory TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$TopPeerCategory result = null;
        switch (constructor) {
            case -1419371685:
                result = new TLRPC$TL_topPeerCategoryBotsPM();
                break;
            case -1122524854:
                result = new TLRPC$TL_topPeerCategoryGroups();
                break;
            case 104314861:
                result = new TLRPC$TL_topPeerCategoryCorrespondents();
                break;
            case 344356834:
                result = new TLRPC$TL_topPeerCategoryBotsInline();
                break;
            case 371037736:
                result = new TLRPC$TL_topPeerCategoryChannels();
                break;
            case 511092620:
                result = new TLRPC$TL_topPeerCategoryPhoneCalls();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in TopPeerCategory", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
