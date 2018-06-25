package org.telegram.tgnet;

public abstract class TLRPC$FileLocation extends TLObject {
    public int dc_id;
    public byte[] iv;
    public byte[] key;
    public int local_id;
    public long secret;
    public long volume_id;

    public static TLRPC$FileLocation TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$FileLocation result = null;
        switch (constructor) {
            case 1406570614:
                result = new TLRPC$TL_fileLocation();
                break;
            case 1431655764:
                result = new TLRPC$TL_fileEncryptedLocation();
                break;
            case 2086234950:
                result = new TLRPC$TL_fileLocationUnavailable();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in FileLocation", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
