package org.telegram.tgnet;

public abstract class TLRPC$Bool extends TLObject {
    public static TLRPC$Bool TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Bool result = null;
        switch (constructor) {
            case -1720552011:
                result = new TLRPC$TL_boolTrue();
                break;
            case -1132882121:
                result = new TLRPC$TL_boolFalse();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Bool", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
