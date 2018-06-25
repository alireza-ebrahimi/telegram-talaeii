package org.telegram.tgnet;

public abstract class TLRPC$InputStickerSet extends TLObject {
    public long access_hash;
    public long id;
    public String short_name;

    public static TLRPC$InputStickerSet TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputStickerSet result = null;
        switch (constructor) {
            case -2044933984:
                result = new TLRPC$TL_inputStickerSetShortName();
                break;
            case -1645763991:
                result = new TLRPC$TL_inputStickerSetID();
                break;
            case -4838507:
                result = new TLRPC$TL_inputStickerSetEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputStickerSet", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
