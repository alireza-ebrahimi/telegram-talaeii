package org.telegram.tgnet;

public abstract class TLRPC$Audio extends TLObject {
    public long access_hash;
    public int date;
    public int dc_id;
    public int duration;
    public long id;
    public byte[] iv;
    public byte[] key;
    public String mime_type;
    public int size;
    public int user_id;

    public static TLRPC$Audio TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Audio result = null;
        switch (constructor) {
            case -945003370:
                result = new TLRPC$TL_audio_old2();
                break;
            case -102543275:
                result = new TLRPC$TL_audio_layer45();
                break;
            case 1114908135:
                result = new TLRPC$TL_audio_old();
                break;
            case 1431655926:
                result = new TLRPC$TL_audioEncrypted();
                break;
            case 1483311320:
                result = new TLRPC$TL_audioEmpty_layer45();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Audio", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
