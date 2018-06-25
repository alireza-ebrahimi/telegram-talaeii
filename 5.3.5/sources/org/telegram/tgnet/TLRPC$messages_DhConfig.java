package org.telegram.tgnet;

public abstract class TLRPC$messages_DhConfig extends TLObject {
    /* renamed from: g */
    public int f85g;
    /* renamed from: p */
    public byte[] f86p;
    public byte[] random;
    public int version;

    public static TLRPC$messages_DhConfig TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_DhConfig result = null;
        switch (constructor) {
            case -1058912715:
                result = new TLRPC$TL_messages_dhConfigNotModified();
                break;
            case 740433629:
                result = new TLRPC$TL_messages_dhConfig();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_DhConfig", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
