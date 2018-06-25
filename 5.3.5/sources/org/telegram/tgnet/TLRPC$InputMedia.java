package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public abstract class TLRPC$InputMedia extends TLObject {
    public String address;
    public ArrayList<DocumentAttribute> attributes = new ArrayList();
    public String caption;
    public TLRPC$InputFile file;
    public String first_name;
    public int flags;
    public TLRPC$InputGeoPoint geo_point;
    public String last_name;
    public String mime_type;
    public boolean nosound_video;
    public int period;
    public String phone_number;
    public String provider;
    /* renamed from: q */
    public String f74q;
    public ArrayList<TLRPC$InputDocument> stickers = new ArrayList();
    public TLRPC$InputFile thumb;
    public String title;
    public int ttl_seconds;
    public String url;
    public String venue_id;
    public String venue_type;

    public static TLRPC$InputMedia TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputMedia result = null;
        switch (constructor) {
            case -2114308294:
                result = new TLRPC$TL_inputMediaPhoto();
                break;
            case -1771768449:
                result = new TLRPC$TL_inputMediaEmpty();
                break;
            case -1494984313:
                result = new TLRPC$TL_inputMediaContact();
                break;
            case -1225309387:
                result = new TLRPC$TL_inputMediaDocumentExternal();
                break;
            case -1052959727:
                result = new TLRPC$TL_inputMediaVenue();
                break;
            case -750828557:
                result = new TLRPC$TL_inputMediaGame();
                break;
            case -476700163:
                result = new TLRPC$TL_inputMediaUploadedDocument();
                break;
            case -104578748:
                result = new TLRPC$TL_inputMediaGeoPoint();
                break;
            case 153267905:
                result = new TLRPC$TL_inputMediaPhotoExternal();
                break;
            case 792191537:
                result = new TLRPC$TL_inputMediaUploadedPhoto();
                break;
            case 1212395773:
                result = new TLRPC$TL_inputMediaGifExternal();
                break;
            case 1523279502:
                result = new TLRPC$TL_inputMediaDocument();
                break;
            case 2065305999:
                result = new TLRPC$TL_inputMediaGeoLive();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputMedia", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
