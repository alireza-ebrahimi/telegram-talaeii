package org.telegram.tgnet;

public abstract class TLRPC$upload_CdnFile extends TLObject {
    public NativeByteBuffer bytes;
    public byte[] request_token;

    public static TLRPC$upload_CdnFile TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$upload_CdnFile tLRPC$upload_CdnFile = null;
        switch (i) {
            case -1449145777:
                tLRPC$upload_CdnFile = new TLRPC$TL_upload_cdnFile();
                break;
            case -290921362:
                tLRPC$upload_CdnFile = new TLRPC$TL_upload_cdnFileReuploadNeeded();
                break;
        }
        if (tLRPC$upload_CdnFile == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in upload_CdnFile", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$upload_CdnFile != null) {
            tLRPC$upload_CdnFile.readParams(abstractSerializedData, z);
        }
        return tLRPC$upload_CdnFile;
    }
}
