package org.telegram.tgnet;

public class TLRPC$TL_messages_getInlineGameHighScores extends TLObject {
    public static int constructor = 258170395;
    public TLRPC$TL_inputBotInlineMessageID id;
    public TLRPC$InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_highScores.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
        this.user_id.serializeToStream(stream);
    }
}
