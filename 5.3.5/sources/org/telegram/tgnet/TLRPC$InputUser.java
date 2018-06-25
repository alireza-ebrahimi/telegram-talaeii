package org.telegram.tgnet;

public abstract class TLRPC$InputUser extends TLObject {
    public long access_hash;
    public int user_id;

    public static TLRPC$InputUser TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputUser result = null;
        switch (constructor) {
            case -1182234929:
                result = new TLRPC$TL_inputUserEmpty();
                break;
            case -668391402:
                result = new TLRPC$TL_inputUser();
                break;
            case -138301121:
                result = new TLRPC$TL_inputUserSelf();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputUser", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
