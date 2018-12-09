package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PhotoSize;

public abstract class TLRPC$Video extends TLObject {
    public long access_hash;
    public String caption;
    public int date;
    public int dc_id;
    public int duration;
    /* renamed from: h */
    public int f10169h;
    public long id;
    public byte[] iv;
    public byte[] key;
    public String mime_type;
    public int size;
    public PhotoSize thumb;
    public int user_id;
    /* renamed from: w */
    public int f10170w;

    public static TLRPC$Video TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$Video tLRPC$Video = null;
        switch (i) {
            case -1056548696:
                tLRPC$Video = new TLRPC$TL_videoEmpty_layer45();
                break;
            case -291550643:
                tLRPC$Video = new TLRPC$TL_video_old3();
                break;
            case -148338733:
                tLRPC$Video = new TLRPC$TL_video_layer45();
                break;
            case 948937617:
                tLRPC$Video = new TLRPC$TL_video_old2();
                break;
            case 1431655763:
                tLRPC$Video = new TLRPC$TL_videoEncrypted();
                break;
            case 1510253727:
                tLRPC$Video = new TLRPC$TL_video_old();
                break;
        }
        if (tLRPC$Video == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in Video", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$Video != null) {
            tLRPC$Video.readParams(abstractSerializedData, z);
        }
        return tLRPC$Video;
    }
}
