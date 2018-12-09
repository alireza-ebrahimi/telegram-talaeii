package org.telegram.tgnet;

public class TLRPC$TL_contacts_exportCard extends TLObject {
    public static int constructor = -2065352905;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            tLRPC$Vector.objects.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
