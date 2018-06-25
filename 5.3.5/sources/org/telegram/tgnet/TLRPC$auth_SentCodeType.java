package org.telegram.tgnet;

public abstract class TLRPC$auth_SentCodeType extends TLObject {
    public int length;
    public String pattern;

    public static TLRPC$auth_SentCodeType TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$auth_SentCodeType result = null;
        switch (constructor) {
            case -1425815847:
                result = new TLRPC$TL_auth_sentCodeTypeFlashCall();
                break;
            case -1073693790:
                result = new TLRPC$TL_auth_sentCodeTypeSms();
                break;
            case 1035688326:
                result = new TLRPC$TL_auth_sentCodeTypeApp();
                break;
            case 1398007207:
                result = new TLRPC$TL_auth_sentCodeTypeCall();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in auth_SentCodeType", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
