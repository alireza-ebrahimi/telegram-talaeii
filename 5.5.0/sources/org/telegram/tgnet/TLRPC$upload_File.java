package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.TL_cdnFileHash;

public abstract class TLRPC$upload_File extends TLObject {
    public NativeByteBuffer bytes;
    public ArrayList<TL_cdnFileHash> cdn_file_hashes = new ArrayList();
    public int dc_id;
    public byte[] encryption_iv;
    public byte[] encryption_key;
    public byte[] file_token;
    public int mtime;
    public TLRPC$storage_FileType type;

    public static TLRPC$upload_File TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$upload_File tLRPC$upload_File = null;
        switch (i) {
            case -363659686:
                tLRPC$upload_File = new TLRPC$TL_upload_fileCdnRedirect();
                break;
            case 157948117:
                tLRPC$upload_File = new TLRPC$TL_upload_file();
                break;
        }
        if (tLRPC$upload_File == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in upload_File", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$upload_File != null) {
            tLRPC$upload_File.readParams(abstractSerializedData, z);
        }
        return tLRPC$upload_File;
    }
}
