package org.telegram.tgnet;

public abstract class TLRPC$storage_FileType extends TLObject {
    public static TLRPC$storage_FileType TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$storage_FileType tLRPC$storage_FileType = null;
        switch (i) {
            case -1432995067:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileUnknown();
                break;
            case -1373745011:
                tLRPC$storage_FileType = new TLRPC$TL_storage_filePdf();
                break;
            case -1278304028:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileMp4();
                break;
            case -891180321:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileGif();
                break;
            case 8322574:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileJpeg();
                break;
            case 172975040:
                tLRPC$storage_FileType = new TLRPC$TL_storage_filePng();
                break;
            case 276907596:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileWebp();
                break;
            case 1086091090:
                tLRPC$storage_FileType = new TLRPC$TL_storage_filePartial();
                break;
            case 1258941372:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileMov();
                break;
            case 1384777335:
                tLRPC$storage_FileType = new TLRPC$TL_storage_fileMp3();
                break;
        }
        if (tLRPC$storage_FileType == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in storage_FileType", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$storage_FileType != null) {
            tLRPC$storage_FileType.readParams(abstractSerializedData, z);
        }
        return tLRPC$storage_FileType;
    }
}
