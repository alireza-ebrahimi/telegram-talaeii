package org.telegram.tgnet;

public abstract class TLRPC$storage_FileType extends TLObject {
    public static TLRPC$storage_FileType TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$storage_FileType result = null;
        switch (constructor) {
            case -1432995067:
                result = new TLRPC$TL_storage_fileUnknown();
                break;
            case -1373745011:
                result = new TLRPC$TL_storage_filePdf();
                break;
            case -1278304028:
                result = new TLRPC$TL_storage_fileMp4();
                break;
            case -891180321:
                result = new TLRPC$TL_storage_fileGif();
                break;
            case 8322574:
                result = new TLRPC$TL_storage_fileJpeg();
                break;
            case 172975040:
                result = new TLRPC$TL_storage_filePng();
                break;
            case 276907596:
                result = new TLRPC$TL_storage_fileWebp();
                break;
            case 1086091090:
                result = new TLRPC$TL_storage_filePartial();
                break;
            case 1258941372:
                result = new TLRPC$TL_storage_fileMov();
                break;
            case 1384777335:
                result = new TLRPC$TL_storage_fileMp3();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in storage_FileType", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
