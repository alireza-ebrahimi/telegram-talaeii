package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_channels_deleteMessages extends TLObject {
    public static int constructor = -2067661490;
    public TLRPC$InputChannel channel;
    public ArrayList<Integer> id = new ArrayList();

    public static TLRPC$TL_channels_deleteMessages TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_channels_deleteMessages result = new TLRPC$TL_channels_deleteMessages();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_channels_deleteMessages", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedMessages.TLdeserialize(stream, constructor, exception);
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel = TLRPC$InputChannel.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.id.add(Integer.valueOf(stream.readInt32(exception)));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.channel.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
    }
}
