package org.telegram.tgnet;

public abstract class TLRPC$MessageEntity extends TLObject {
    public String language;
    public int length;
    public int offset;
    public String url;

    public static TLRPC$MessageEntity TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$MessageEntity result = null;
        switch (constructor) {
            case -2106619040:
                result = new TLRPC$TL_messageEntityItalic();
                break;
            case -1148011883:
                result = new TLRPC$TL_messageEntityUnknown();
                break;
            case -1117713463:
                result = new TLRPC$TL_messageEntityBold();
                break;
            case -100378723:
                result = new TLRPC$TL_messageEntityMention();
                break;
            case 546203849:
                result = new TLRPC$TL_inputMessageEntityMentionName();
                break;
            case 681706865:
                result = new TLRPC$TL_messageEntityCode();
                break;
            case 892193368:
                result = new TLRPC$TL_messageEntityMentionName();
                break;
            case 1692693954:
                result = new TLRPC$TL_messageEntityEmail();
                break;
            case 1827637959:
                result = new TLRPC$TL_messageEntityBotCommand();
                break;
            case 1859134776:
                result = new TLRPC$TL_messageEntityUrl();
                break;
            case 1868782349:
                result = new TLRPC$TL_messageEntityHashtag();
                break;
            case 1938967520:
                result = new TLRPC$TL_messageEntityPre();
                break;
            case 1990644519:
                result = new TLRPC$TL_messageEntityTextUrl();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in MessageEntity", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
