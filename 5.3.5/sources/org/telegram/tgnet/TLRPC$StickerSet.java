package org.telegram.tgnet;

public abstract class TLRPC$StickerSet extends TLObject {
    public long access_hash;
    public boolean archived;
    public int count;
    public int flags;
    public int hash;
    public long id;
    public boolean installed;
    public boolean masks;
    public boolean official;
    public String short_name;
    public String title;

    public static TLRPC$StickerSet TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$StickerSet result = null;
        switch (constructor) {
            case -1482409193:
                result = new TLRPC$TL_stickerSet_old();
                break;
            case -852477119:
                result = new TLRPC$TL_stickerSet();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in StickerSet", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
