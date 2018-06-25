package org.telegram.tgnet;

public class TLRPC$TL_maskCoords extends TLObject {
    public static int constructor = -1361650766;
    /* renamed from: n */
    public int f10161n;
    /* renamed from: x */
    public double f10162x;
    /* renamed from: y */
    public double f10163y;
    public double zoom;

    public static TLRPC$TL_maskCoords TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_maskCoords tLRPC$TL_maskCoords = new TLRPC$TL_maskCoords();
            tLRPC$TL_maskCoords.readParams(abstractSerializedData, z);
            return tLRPC$TL_maskCoords;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_maskCoords", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.f10161n = abstractSerializedData.readInt32(z);
        this.f10162x = abstractSerializedData.readDouble(z);
        this.f10163y = abstractSerializedData.readDouble(z);
        this.zoom = abstractSerializedData.readDouble(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.f10161n);
        abstractSerializedData.writeDouble(this.f10162x);
        abstractSerializedData.writeDouble(this.f10163y);
        abstractSerializedData.writeDouble(this.zoom);
    }
}
