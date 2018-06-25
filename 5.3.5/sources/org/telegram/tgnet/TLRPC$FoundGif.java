package org.telegram.tgnet;

public abstract class TLRPC$FoundGif extends TLObject {
    public String content_type;
    public String content_url;
    public TLRPC$Document document;
    /* renamed from: h */
    public int f72h;
    public TLRPC$Photo photo;
    public String thumb_url;
    public String url;
    /* renamed from: w */
    public int f73w;

    public static TLRPC$FoundGif TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$FoundGif result = null;
        switch (constructor) {
            case -1670052855:
                result = new TLRPC$TL_foundGifCached();
                break;
            case 372165663:
                result = new TLRPC$TL_foundGif();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in FoundGif", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
