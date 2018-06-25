package org.telegram.tgnet;

public class TLRPC$TL_groupCallParticipantAdmin extends TLRPC$GroupCallParticipant {
    public static int constructor = 1326135736;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.member_tag_hash = stream.readByteArray(exception);
        this.streams = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeByteArray(this.member_tag_hash);
        stream.writeByteArray(this.streams);
    }
}
