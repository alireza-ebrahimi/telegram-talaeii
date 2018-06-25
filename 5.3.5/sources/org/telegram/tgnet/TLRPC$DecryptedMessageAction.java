package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$DecryptedMessageAction extends TLObject {
    public TLRPC$SendMessageAction action;
    public int end_seq_no;
    public long exchange_id;
    public byte[] g_a;
    public byte[] g_b;
    public long key_fingerprint;
    public int layer;
    public ArrayList<Long> random_ids = new ArrayList();
    public int start_seq_no;
    public int ttl_seconds;

    public static TLRPC$DecryptedMessageAction TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$DecryptedMessageAction result = null;
        switch (constructor) {
            case -1967000459:
                result = new TLRPC$TL_decryptedMessageActionScreenshotMessages();
                break;
            case -1586283796:
                result = new TLRPC$TL_decryptedMessageActionSetMessageTTL();
                break;
            case -1473258141:
                result = new TLRPC$TL_decryptedMessageActionNoop();
                break;
            case -860719551:
                result = new TLRPC$TL_decryptedMessageActionTyping();
                break;
            case -586814357:
                result = new TLRPC$TL_decryptedMessageActionAbortKey();
                break;
            case -332526693:
                result = new TLRPC$TL_decryptedMessageActionCommitKey();
                break;
            case -217806717:
                result = new TLRPC$TL_decryptedMessageActionNotifyLayer();
                break;
            case -204906213:
                result = new TLRPC$TL_decryptedMessageActionRequestKey();
                break;
            case 206520510:
                result = new TLRPC$TL_decryptedMessageActionReadMessages();
                break;
            case 1360072880:
                result = new TLRPC$TL_decryptedMessageActionResend();
                break;
            case 1700872964:
                result = new TLRPC$TL_decryptedMessageActionDeleteMessages();
                break;
            case 1729750108:
                result = new TLRPC$TL_decryptedMessageActionFlushHistory();
                break;
            case 1877046107:
                result = new TLRPC$TL_decryptedMessageActionAcceptKey();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in DecryptedMessageAction", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
