package org.telegram.tgnet;

public abstract class TLRPC$InputGame extends TLObject {
    public long access_hash;
    public TLRPC$InputUser bot_id;
    public long id;
    public String short_name;

    public static TLRPC$InputGame TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputGame result = null;
        switch (constructor) {
            case -1020139510:
                result = new TLRPC$TL_inputGameShortName();
                break;
            case 53231223:
                result = new TLRPC$TL_inputGameID();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputGame", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
