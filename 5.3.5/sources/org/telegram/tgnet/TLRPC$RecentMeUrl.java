package org.telegram.tgnet;

public abstract class TLRPC$RecentMeUrl extends TLObject {
    public int chat_id;
    public TLRPC$ChatInvite chat_invite;
    public TLRPC$StickerSetCovered set;
    public String url;
    public int user_id;

    public static TLRPC$RecentMeUrl TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$RecentMeUrl result = null;
        switch (constructor) {
            case -1917045962:
                result = new TLRPC$TL_recentMeUrlUser();
                break;
            case -1608834311:
                result = new TLRPC$TL_recentMeUrlChat();
                break;
            case -1140172836:
                result = new TLRPC$TL_recentMeUrlStickerSet();
                break;
            case -347535331:
                result = new TLRPC$TL_recentMeUrlChatInvite();
                break;
            case 1189204285:
                result = new TLRPC$TL_recentMeUrlUnknown();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in RecentMeUrl", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
