package org.telegram.tgnet;

public class TLRPC$TL_replyInlineMarkup extends TLRPC$ReplyMarkup {
    public static int constructor = 1218642516;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_keyboardButtonRow object = TLRPC$TL_keyboardButtonRow.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.rows.add(object);
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
        stream.writeInt32(481674261);
        int count = this.rows.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_keyboardButtonRow) this.rows.get(a)).serializeToStream(stream);
        }
    }
}
