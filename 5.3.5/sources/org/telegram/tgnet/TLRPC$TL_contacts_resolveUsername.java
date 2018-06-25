package org.telegram.tgnet;

public class TLRPC$TL_contacts_resolveUsername extends TLObject {
    public static int constructor = -113456221;
    public String username;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_contacts_resolvedPeer.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.username);
    }
}
