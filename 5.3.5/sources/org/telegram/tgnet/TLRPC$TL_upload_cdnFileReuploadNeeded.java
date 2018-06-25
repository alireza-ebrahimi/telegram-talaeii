package org.telegram.tgnet;

public class TLRPC$TL_upload_cdnFileReuploadNeeded extends TLRPC$upload_CdnFile {
    public static int constructor = -290921362;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.request_token = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.request_token);
    }
}
