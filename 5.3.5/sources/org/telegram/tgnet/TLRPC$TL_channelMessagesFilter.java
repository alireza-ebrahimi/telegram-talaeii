package org.telegram.tgnet;

public class TLRPC$TL_channelMessagesFilter extends TLRPC$ChannelMessagesFilter {
    public static int constructor = -847783593;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.exclude_new_messages = z;
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_messageRange object = TLRPC$TL_messageRange.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.ranges.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.exclude_new_messages ? this.flags | 2 : this.flags & -3;
        stream.writeInt32(this.flags);
        stream.writeInt32(481674261);
        int count = this.ranges.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_messageRange) this.ranges.get(a)).serializeToStream(stream);
        }
    }
}
