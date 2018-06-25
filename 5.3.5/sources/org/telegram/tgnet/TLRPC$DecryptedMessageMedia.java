package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public abstract class TLRPC$DecryptedMessageMedia extends TLObject {
    public double _long;
    public long access_hash;
    public String address;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public String caption;
    public int date;
    public int dc_id;
    public int duration;
    public String file_name;
    public String first_name;
    /* renamed from: h */
    public int f70h;
    public long id;
    public byte[] iv;
    public byte[] key;
    public String last_name;
    public double lat;
    public String mime_type;
    public String phone_number;
    public String provider;
    public int size;
    public int thumb_h;
    public int thumb_w;
    public String title;
    public String url;
    public int user_id;
    public String venue_id;
    /* renamed from: w */
    public int f71w;

    public static TLRPC$DecryptedMessageMedia TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$DecryptedMessageMedia result = null;
        switch (constructor) {
            case -1978796689:
                result = new TLRPC$TL_decryptedMessageMediaVenue();
                break;
            case -1760785394:
                result = new TLRPC$TL_decryptedMessageMediaVideo();
                break;
            case -1332395189:
                result = new TLRPC$TL_decryptedMessageMediaDocument_layer8();
                break;
            case -452652584:
                result = new TLRPC$TL_decryptedMessageMediaWebPage();
                break;
            case -235238024:
                result = new TLRPC$TL_decryptedMessageMediaPhoto();
                break;
            case -90853155:
                result = new TLRPC$TL_decryptedMessageMediaExternalDocument();
                break;
            case 144661578:
                result = new TLRPC$TL_decryptedMessageMediaEmpty();
                break;
            case 846826124:
                result = new TLRPC$TL_decryptedMessageMediaPhoto_layer8();
                break;
            case 893913689:
                result = new TLRPC$TL_decryptedMessageMediaGeoPoint();
                break;
            case 1290694387:
                result = new TLRPC$TL_decryptedMessageMediaVideo_layer8();
                break;
            case 1380598109:
                result = new TLRPC$TL_decryptedMessageMediaVideo_layer17();
                break;
            case 1474341323:
                result = new TLRPC$TL_decryptedMessageMediaAudio();
                break;
            case 1485441687:
                result = new TLRPC$TL_decryptedMessageMediaContact();
                break;
            case 1619031439:
                result = new TLRPC$TL_decryptedMessageMediaAudio_layer8();
                break;
            case 2063502050:
                result = new TLRPC$TL_decryptedMessageMediaDocument();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessageMedia", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
