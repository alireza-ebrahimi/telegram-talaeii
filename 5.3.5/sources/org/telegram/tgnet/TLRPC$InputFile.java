package org.telegram.tgnet;

public abstract class TLRPC$InputFile extends TLObject {
    public long id;
    public String md5_checksum;
    public String name;
    public int parts;

    public static TLRPC$InputFile TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputFile result = null;
        switch (constructor) {
            case -181407105:
                result = new TLRPC$TL_inputFile();
                break;
            case -95482955:
                result = new TLRPC$TL_inputFileBig();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputFile", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
