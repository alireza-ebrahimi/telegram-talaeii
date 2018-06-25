package org.telegram.tgnet;

public abstract class TLRPC$Video extends TLObject {
    public long access_hash;
    public String caption;
    public int date;
    public int dc_id;
    public int duration;
    /* renamed from: h */
    public int f90h;
    public long id;
    public byte[] iv;
    public byte[] key;
    public String mime_type;
    public int size;
    public TLRPC$PhotoSize thumb;
    public int user_id;
    /* renamed from: w */
    public int f91w;

    public static TLRPC$Video TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Video result = null;
        switch (constructor) {
            case -1056548696:
                result = new TLRPC$TL_videoEmpty_layer45();
                break;
            case -291550643:
                result = new TLRPC$TL_video_old3();
                break;
            case -148338733:
                result = new TLRPC$TL_video_layer45();
                break;
            case 948937617:
                result = new TLRPC$TL_video_old2();
                break;
            case 1431655763:
                result = new TLRPC$TL_videoEncrypted();
                break;
            case 1510253727:
                result = new TLRPC$TL_video_old();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Video", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
