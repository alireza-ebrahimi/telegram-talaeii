package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$PageBlock extends TLObject {
    public boolean allow_scrolling;
    public long audio_id;
    public String author;
    public long author_photo_id;
    public boolean autoplay;
    public ArrayList<TLRPC$PageBlock> blocks = new ArrayList();
    public boolean bottom;
    public TLRPC$RichText caption;
    public TLRPC$Chat channel;
    public TLRPC$PageBlock cover;
    public int date;
    public boolean first;
    public int flags;
    public boolean full_width;
    /* renamed from: h */
    public int f75h;
    public String html;
    public String language;
    public int level;
    public boolean loop;
    public int mid;
    public String name;
    public boolean ordered;
    public long photo_id;
    public long poster_photo_id;
    public int published_date;
    public TLRPC$RichText text;
    public String url;
    public long video_id;
    /* renamed from: w */
    public int f76w;
    public long webpage_id;

    public static TLRPC$PageBlock TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PageBlock result = null;
        switch (constructor) {
            case -1879401953:
                result = new TLRPC$TL_pageBlockSubtitle();
                break;
            case -1162877472:
                result = new TLRPC$TL_pageBlockAuthorDate();
                break;
            case -1076861716:
                result = new TLRPC$TL_pageBlockHeader();
                break;
            case -1066346178:
                result = new TLRPC$TL_pageBlockPreformatted();
                break;
            case -840826671:
                result = new TLRPC$TL_pageBlockEmbed();
                break;
            case -837994576:
                result = new TLRPC$TL_pageBlockAnchor();
                break;
            case -650782469:
                result = new TLRPC$TL_pageBlockEmbed_layer60();
                break;
            case -640214938:
                result = new TLRPC$TL_pageBlockVideo();
                break;
            case -618614392:
                result = new TLRPC$TL_pageBlockDivider();
                break;
            case -372860542:
                result = new TLRPC$TL_pageBlockPhoto();
                break;
            case -283684427:
                result = new TLRPC$TL_pageBlockChannel();
                break;
            case -248793375:
                result = new TLRPC$TL_pageBlockSubheader();
                break;
            case 145955919:
                result = new TLRPC$TL_pageBlockCollage();
                break;
            case 319588707:
                result = new TLRPC$TL_pageBlockSlideshow();
                break;
            case 324435594:
                result = new TLRPC$TL_pageBlockUnsupported();
                break;
            case 641563686:
                result = new TLRPC$TL_pageBlockBlockquote();
                break;
            case 690781161:
                result = new TLRPC$TL_pageBlockEmbedPost();
                break;
            case 834148991:
                result = new TLRPC$TL_pageBlockAudio();
                break;
            case 972174080:
                result = new TLRPC$TL_pageBlockCover();
                break;
            case 978896884:
                result = new TLRPC$TL_pageBlockList();
                break;
            case 1029399794:
                result = new TLRPC$TL_pageBlockAuthorDate_layer60();
                break;
            case 1182402406:
                result = new TLRPC$TL_pageBlockParagraph();
                break;
            case 1216809369:
                result = new TLRPC$TL_pageBlockFooter();
                break;
            case 1329878739:
                result = new TLRPC$TL_pageBlockPullquote();
                break;
            case 1890305021:
                result = new TLRPC$TL_pageBlockTitle();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PageBlock", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
