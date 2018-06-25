package org.telegram.tgnet;

public abstract class TLRPC$PhotoSize extends TLObject {
    public byte[] bytes;
    /* renamed from: h */
    public int f77h;
    public TLRPC$FileLocation location;
    public int size;
    public String type;
    /* renamed from: w */
    public int f78w;

    public static TLRPC$PhotoSize TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PhotoSize result = null;
        switch (constructor) {
            case -374917894:
                result = new TLRPC$TL_photoCachedSize();
                break;
            case 236446268:
                result = new TLRPC$TL_photoSizeEmpty();
                break;
            case 2009052699:
                result = new TLRPC$TL_photoSize();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PhotoSize", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
