package org.telegram.tgnet;

public abstract class TLRPC$upload_CdnFile extends TLObject {
    public NativeByteBuffer bytes;
    public byte[] request_token;

    public static TLRPC$upload_CdnFile TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$upload_CdnFile result = null;
        switch (constructor) {
            case -1449145777:
                result = new TLRPC$TL_upload_cdnFile();
                break;
            case -290921362:
                result = new TLRPC$TL_upload_cdnFileReuploadNeeded();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in upload_CdnFile", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
