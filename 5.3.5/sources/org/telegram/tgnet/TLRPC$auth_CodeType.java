package org.telegram.tgnet;

public abstract class TLRPC$auth_CodeType extends TLObject {
    public static TLRPC$auth_CodeType TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$auth_CodeType result = null;
        switch (constructor) {
            case 577556219:
                result = new TLRPC$TL_auth_codeTypeFlashCall();
                break;
            case 1923290508:
                result = new TLRPC$TL_auth_codeTypeSms();
                break;
            case 1948046307:
                result = new TLRPC$TL_auth_codeTypeCall();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in auth_CodeType", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
