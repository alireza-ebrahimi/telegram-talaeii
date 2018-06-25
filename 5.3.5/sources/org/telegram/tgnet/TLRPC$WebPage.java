package org.telegram.tgnet;

public abstract class TLRPC$WebPage extends TLObject {
    public String author;
    public TLRPC$Page cached_page;
    public int date;
    public String description;
    public String display_url;
    public TLRPC$Document document;
    public int duration;
    public int embed_height;
    public String embed_type;
    public String embed_url;
    public int embed_width;
    public int flags;
    public int hash;
    public long id;
    public TLRPC$Photo photo;
    public String site_name;
    public String title;
    public String type;
    public String url;

    public static TLRPC$WebPage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$WebPage result = null;
        switch (constructor) {
            case -2054908813:
                result = new TLRPC$TL_webPageNotModified();
                break;
            case -1558273867:
                result = new TLRPC$TL_webPage_old();
                break;
            case -981018084:
                result = new TLRPC$TL_webPagePending();
                break;
            case -897446185:
                result = new TLRPC$TL_webPage_layer58();
                break;
            case -736472729:
                result = new TLRPC$TL_webPageUrlPending();
                break;
            case -350980120:
                result = new TLRPC$TL_webPageEmpty();
                break;
            case 1594340540:
                result = new TLRPC$TL_webPage();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in WebPage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
