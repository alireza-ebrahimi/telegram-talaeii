package org.telegram.tgnet;

public abstract class TLRPC$ContactLink extends TLObject {
    public static TLRPC$ContactLink TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ContactLink result = null;
        switch (constructor) {
            case -721239344:
                result = new TLRPC$TL_contactLinkContact();
                break;
            case -17968211:
                result = new TLRPC$TL_contactLinkNone();
                break;
            case 646922073:
                result = new TLRPC$TL_contactLinkHasPhone();
                break;
            case 1599050311:
                result = new TLRPC$TL_contactLinkUnknown();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ContactLink", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
