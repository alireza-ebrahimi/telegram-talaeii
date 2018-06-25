package org.telegram.tgnet;

public class TLRPC$TL_peerSettings extends TLObject {
    public static int constructor = -2122045747;
    public int flags;
    public boolean report_spam;

    public static TLRPC$TL_peerSettings TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_peerSettings result = new TLRPC$TL_peerSettings();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_peerSettings", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.report_spam = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.report_spam ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
    }
}
