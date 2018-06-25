package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_keyboardButtonRow extends TLObject {
    public static int constructor = 2002815875;
    public ArrayList<TLRPC$KeyboardButton> buttons = new ArrayList();

    public static TLRPC$TL_keyboardButtonRow TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_keyboardButtonRow result = new TLRPC$TL_keyboardButtonRow();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_keyboardButtonRow", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$KeyboardButton object = TLRPC$KeyboardButton.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.buttons.add(object);
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
        int count = this.buttons.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$KeyboardButton) this.buttons.get(a)).serializeToStream(stream);
        }
    }
}
