package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.TL_cdnFileHash;

public class TLRPC$TL_upload_fileCdnRedirect extends TLRPC$upload_File {
    public static int constructor = -363659686;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.dc_id = abstractSerializedData.readInt32(z);
        this.file_token = abstractSerializedData.readByteArray(z);
        this.encryption_key = abstractSerializedData.readByteArray(z);
        this.encryption_iv = abstractSerializedData.readByteArray(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                TL_cdnFileHash TLdeserialize = TL_cdnFileHash.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.cdn_file_hashes.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.dc_id);
        abstractSerializedData.writeByteArray(this.file_token);
        abstractSerializedData.writeByteArray(this.encryption_key);
        abstractSerializedData.writeByteArray(this.encryption_iv);
        abstractSerializedData.writeInt32(481674261);
        int size = this.cdn_file_hashes.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TL_cdnFileHash) this.cdn_file_hashes.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
