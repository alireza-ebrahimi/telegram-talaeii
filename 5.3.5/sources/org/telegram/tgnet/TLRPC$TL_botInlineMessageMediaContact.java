package org.telegram.tgnet;

public class TLRPC$TL_botInlineMessageMediaContact extends TLRPC$BotInlineMessage {
    public static int constructor = 904770772;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.phone_number = stream.readString(exception);
        this.first_name = stream.readString(exception);
        this.last_name = stream.readString(exception);
        if ((this.flags & 4) != 0) {
            this.reply_markup = TLRPC$ReplyMarkup.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeString(this.phone_number);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(stream);
        }
    }
}
