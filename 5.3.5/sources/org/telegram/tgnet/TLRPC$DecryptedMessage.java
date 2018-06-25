package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$DecryptedMessage extends TLObject {
    public TLRPC$DecryptedMessageAction action;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public long grouped_id;
    public TLRPC$DecryptedMessageMedia media;
    public String message;
    public byte[] random_bytes;
    public long random_id;
    public long reply_to_random_id;
    public int ttl;
    public String via_bot_name;

    public static TLRPC$DecryptedMessage TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$DecryptedMessage result = null;
        switch (constructor) {
            case -1848883596:
                result = new TLRPC$TL_decryptedMessage();
                break;
            case -1438109059:
                result = new TLRPC$TL_decryptedMessageService_layer8();
                break;
            case 528568095:
                result = new TLRPC$TL_decryptedMessage_layer8();
                break;
            case 541931640:
                result = new TLRPC$TL_decryptedMessage_layer17();
                break;
            case 917541342:
                result = new TLRPC$TL_decryptedMessage_layer45();
                break;
            case 1930838368:
                result = new TLRPC$TL_decryptedMessageService();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessage", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
