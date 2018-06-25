package org.telegram.tgnet;

public abstract class TLRPC$InputNotifyPeer extends TLObject {
    public static TLRPC$InputNotifyPeer TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputNotifyPeer result = null;
        switch (constructor) {
            case -1540769658:
                result = new TLRPC$TL_inputNotifyAll();
                break;
            case -1195615476:
                result = new TLRPC$TL_inputNotifyPeer();
                break;
            case 423314455:
                result = new TLRPC$TL_inputNotifyUsers();
                break;
            case 1251338318:
                result = new TLRPC$TL_inputNotifyChats();
                break;
            case 1301143240:
                result = new TLRPC$TL_inputNotifyGeoChatPeer();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputNotifyPeer", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
