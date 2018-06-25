package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$BotInlineMessage extends TLObject {
    public String address;
    public String caption;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public String first_name;
    public int flags;
    public TLRPC$GeoPoint geo;
    public String last_name;
    public String message;
    public boolean no_webpage;
    public int period;
    public String phone_number;
    public String provider;
    public TLRPC$ReplyMarkup reply_markup;
    public String title;
    public String venue_id;

    public static TLRPC$BotInlineMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$BotInlineMessage result = null;
        switch (constructor) {
            case -1937807902:
                result = new TLRPC$TL_botInlineMessageText();
                break;
            case -1222451611:
                result = new TLRPC$TL_botInlineMessageMediaGeo();
                break;
            case 175419739:
                result = new TLRPC$TL_botInlineMessageMediaAuto();
                break;
            case 904770772:
                result = new TLRPC$TL_botInlineMessageMediaContact();
                break;
            case 982505656:
                result = new TLRPC$TL_botInlineMessageMediaGeo_layer71();
                break;
            case 1130767150:
                result = new TLRPC$TL_botInlineMessageMediaVenue();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in BotInlineMessage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
