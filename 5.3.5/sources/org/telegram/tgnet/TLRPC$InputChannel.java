package org.telegram.tgnet;

public abstract class TLRPC$InputChannel extends TLObject {
    public long access_hash;
    public int channel_id;

    public static TLRPC$InputChannel TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputChannel result = null;
        switch (constructor) {
            case -1343524562:
                result = new TLRPC$TL_inputChannel();
                break;
            case -292807034:
                result = new TLRPC$TL_inputChannelEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputChannel", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
