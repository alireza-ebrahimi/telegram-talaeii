package org.telegram.tgnet;

public class TLRPC$TL_inputBotInlineMessageID extends TLObject {
    public static int constructor = -1995686519;
    public long access_hash;
    public int dc_id;
    public long id;

    public static TLRPC$TL_inputBotInlineMessageID TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputBotInlineMessageID result = new TLRPC$TL_inputBotInlineMessageID();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputBotInlineMessageID", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.dc_id = stream.readInt32(exception);
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.dc_id);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
    }
}
