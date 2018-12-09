package org.telegram.tgnet;

public abstract class TLRPC$messages_DhConfig extends TLObject {
    /* renamed from: g */
    public int f10164g;
    /* renamed from: p */
    public byte[] f10165p;
    public byte[] random;
    public int version;

    public static TLRPC$messages_DhConfig TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_DhConfig tLRPC$messages_DhConfig = null;
        switch (i) {
            case -1058912715:
                tLRPC$messages_DhConfig = new TLRPC$TL_messages_dhConfigNotModified();
                break;
            case 740433629:
                tLRPC$messages_DhConfig = new TLRPC$TL_messages_dhConfig();
                break;
        }
        if (tLRPC$messages_DhConfig == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_DhConfig", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_DhConfig != null) {
            tLRPC$messages_DhConfig.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_DhConfig;
    }
}
