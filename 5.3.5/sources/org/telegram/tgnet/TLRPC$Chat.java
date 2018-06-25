package org.telegram.tgnet;

public abstract class TLRPC$Chat extends TLObject {
    public long access_hash;
    public String address;
    public boolean admin;
    public TLRPC$TL_channelAdminRights admin_rights;
    public boolean admins_enabled;
    public TLRPC$TL_channelBannedRights banned_rights;
    public boolean broadcast;
    public boolean checked_in;
    public boolean creator;
    public int date;
    public boolean deactivated;
    public boolean democracy;
    public boolean explicit_content;
    public int flags;
    public TLRPC$GeoPoint geo;
    public int id;
    public boolean kicked;
    public boolean left;
    public boolean megagroup;
    public TLRPC$InputChannel migrated_to;
    public boolean min;
    public boolean moderator;
    public int participants_count;
    public TLRPC$ChatPhoto photo;
    public boolean restricted;
    public String restriction_reason;
    public boolean signatures;
    public String title;
    public int until_date;
    public String username;
    public String venue;
    public boolean verified;
    public int version;

    public static TLRPC$Chat TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Chat result = null;
        switch (constructor) {
            case -2059962289:
                result = new TLRPC$TL_channelForbidden_layer67();
                break;
            case -1683826688:
                result = new TLRPC$TL_chatEmpty();
                break;
            case -1588737454:
                result = new TLRPC$TL_channel_layer67();
                break;
            case -652419756:
                result = new TLRPC$TL_chat();
                break;
            case -83047359:
                result = new TLRPC$TL_chatForbidden_old();
                break;
            case 120753115:
                result = new TLRPC$TL_chatForbidden();
                break;
            case 213142300:
                result = new TLRPC$TL_channel_layer72();
                break;
            case 681420594:
                result = new TLRPC$TL_channelForbidden();
                break;
            case 763724588:
                result = new TLRPC$TL_channelForbidden_layer52();
                break;
            case 1158377749:
                result = new TLRPC$TL_channel();
                break;
            case 1260090630:
                result = new TLRPC$TL_channel_layer48();
                break;
            case 1737397639:
                result = new TLRPC$TL_channel_old();
                break;
            case 1855757255:
                result = new TLRPC$TL_chat_old();
                break;
            case 1930607688:
                result = new TLRPC$TL_chat_old2();
                break;
            case 1978329690:
                result = new TLRPC$TL_geoChat();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Chat", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
