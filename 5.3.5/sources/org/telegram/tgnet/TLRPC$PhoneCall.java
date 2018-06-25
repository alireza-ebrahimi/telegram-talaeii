package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$PhoneCall extends TLObject {
    public long access_hash;
    public int admin_id;
    public ArrayList<TLRPC$TL_phoneConnection> alternative_connections = new ArrayList();
    public TLRPC$TL_phoneConnection connection;
    public int date;
    public int duration;
    public int flags;
    public byte[] g_a_hash;
    public byte[] g_a_or_b;
    public byte[] g_b;
    public long id;
    public long key_fingerprint;
    public boolean need_debug;
    public boolean need_rating;
    public int participant_id;
    public TLRPC$TL_phoneCallProtocol protocol;
    public TLRPC$PhoneCallDiscardReason reason;
    public int receive_date;
    public int start_date;

    public static TLRPC$PhoneCall TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PhoneCall result = null;
        switch (constructor) {
            case -2089411356:
                result = new TLRPC$TL_phoneCallRequested();
                break;
            case -1660057:
                result = new TLRPC$TL_phoneCall();
                break;
            case 462375633:
                result = new TLRPC$TL_phoneCallWaiting();
                break;
            case 1355435489:
                result = new TLRPC$TL_phoneCallDiscarded();
                break;
            case 1399245077:
                result = new TLRPC$TL_phoneCallEmpty();
                break;
            case 1828732223:
                result = new TLRPC$TL_phoneCallAccepted();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PhoneCall", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
