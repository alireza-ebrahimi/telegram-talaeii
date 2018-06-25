package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$upload_File extends TLObject {
    public NativeByteBuffer bytes;
    public ArrayList<TLRPC$TL_cdnFileHash> cdn_file_hashes = new ArrayList();
    public int dc_id;
    public byte[] encryption_iv;
    public byte[] encryption_key;
    public byte[] file_token;
    public int mtime;
    public TLRPC$storage_FileType type;

    public static TLRPC$upload_File TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$upload_File result = null;
        switch (constructor) {
            case -363659686:
                result = new TLRPC$TL_upload_fileCdnRedirect();
                break;
            case 157948117:
                result = new TLRPC$TL_upload_file();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in upload_File", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
