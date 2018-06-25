package org.telegram.tgnet;

public class TLRPC$TL_maskCoords extends TLObject {
    public static int constructor = -1361650766;
    /* renamed from: n */
    public int f82n;
    /* renamed from: x */
    public double f83x;
    /* renamed from: y */
    public double f84y;
    public double zoom;

    public static TLRPC$TL_maskCoords TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_maskCoords result = new TLRPC$TL_maskCoords();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_maskCoords", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.f82n = stream.readInt32(exception);
        this.f83x = stream.readDouble(exception);
        this.f84y = stream.readDouble(exception);
        this.zoom = stream.readDouble(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.f82n);
        stream.writeDouble(this.f83x);
        stream.writeDouble(this.f84y);
        stream.writeDouble(this.zoom);
    }
}
