package org.telegram.tgnet;

public abstract class TLRPC$GroupCall extends TLObject {
    public long access_hash;
    public int admin_id;
    public int channel_id;
    public TLRPC$TL_groupCallConnection connection;
    public int duration;
    public byte[] encryption_key;
    public int flags;
    public long id;
    public long key_fingerprint;
    public int participants_count;
    public TLRPC$TL_phoneCallProtocol protocol;
    public byte[] reflector_group_tag;
    public byte[] reflector_self_secret;
    public byte[] reflector_self_tag;

    public static TLRPC$GroupCall TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$GroupCall result = null;
        switch (constructor) {
            case 177149476:
                result = new TLRPC$TL_groupCall();
                break;
            case 1829443076:
                result = new TLRPC$TL_groupCallPrivate();
                break;
            case 2004925620:
                result = new TLRPC$TL_groupCallDiscarded();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in GroupCall", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
