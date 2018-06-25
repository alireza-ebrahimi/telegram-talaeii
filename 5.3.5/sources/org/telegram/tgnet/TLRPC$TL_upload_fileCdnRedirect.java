package org.telegram.tgnet;

public class TLRPC$TL_upload_fileCdnRedirect extends TLRPC$upload_File {
    public static int constructor = -363659686;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.dc_id = stream.readInt32(exception);
        this.file_token = stream.readByteArray(exception);
        this.encryption_key = stream.readByteArray(exception);
        this.encryption_iv = stream.readByteArray(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_cdnFileHash object = TLRPC$TL_cdnFileHash.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.cdn_file_hashes.add(object);
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
        stream.writeInt32(this.dc_id);
        stream.writeByteArray(this.file_token);
        stream.writeByteArray(this.encryption_key);
        stream.writeByteArray(this.encryption_iv);
        stream.writeInt32(481674261);
        int count = this.cdn_file_hashes.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$TL_cdnFileHash) this.cdn_file_hashes.get(a)).serializeToStream(stream);
        }
    }
}
