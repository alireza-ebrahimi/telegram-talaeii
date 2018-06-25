package org.telegram.tgnet;

public class TLRPC$TL_replyKeyboardMarkup extends TLRPC$ReplyMarkup {
    public static int constructor = 889353612;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.resize = z;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.single_use = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.selective = z;
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
        this.flags = this.resize ? this.flags | 1 : this.flags & -2;
        this.flags = this.single_use ? this.flags | 2 : this.flags & -3;
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        stream.writeInt32(this.flags);
        stream.writeInt32(481674261);
        int count = this.rows.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_keyboardButtonRow) this.rows.get(a)).serializeToStream(stream);
        }
    }
}
