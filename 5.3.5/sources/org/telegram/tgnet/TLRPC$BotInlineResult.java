package org.telegram.tgnet;

public abstract class TLRPC$BotInlineResult extends TLObject {
    public String content_type;
    public String content_url;
    public String description;
    public TLRPC$Document document;
    public int duration;
    public int flags;
    /* renamed from: h */
    public int f67h;
    public String id;
    public TLRPC$Photo photo;
    public long query_id;
    public TLRPC$BotInlineMessage send_message;
    public String thumb_url;
    public String title;
    public String type;
    public String url;
    /* renamed from: w */
    public int f68w;

    public static TLRPC$BotInlineResult TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$BotInlineResult result = null;
        switch (constructor) {
            case -1679053127:
                result = new TLRPC$TL_botInlineResult();
                break;
            case 400266251:
                result = new TLRPC$TL_botInlineMediaResult();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in BotInlineResult", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
