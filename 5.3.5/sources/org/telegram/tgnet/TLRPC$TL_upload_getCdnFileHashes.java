package org.telegram.tgnet;

public class TLRPC$TL_upload_getCdnFileHashes extends TLObject {
    public static int constructor = -149567365;
    public byte[] file_token;
    public int offset;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            TLRPC$TL_cdnFileHash object = TLRPC$TL_cdnFileHash.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.file_token);
        stream.writeInt32(this.offset);
    }
}
