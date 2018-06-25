package org.telegram.tgnet;

public abstract class TLRPC$InputStickeredMedia extends TLObject {
    public static TLRPC$InputStickeredMedia TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputStickeredMedia result = null;
        switch (constructor) {
            case 70813275:
                result = new TLRPC$TL_inputStickeredMediaDocument();
                break;
            case 1251549527:
                result = new TLRPC$TL_inputStickeredMediaPhoto();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputStickeredMedia", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
