package org.telegram.tgnet;

import java.util.ArrayList;
import java.util.HashMap;

public class TLRPC$Message extends TLObject {
    public TLRPC$MessageAction action;
    public String attachPath = "";
    public int date;
    public int destroyTime;
    public long dialog_id;
    public int edit_date;
    public ArrayList<TLRPC$MessageEntity> entities = new ArrayList();
    public int flags;
    public int from_id;
    public TLRPC$MessageFwdHeader fwd_from;
    public int fwd_msg_id = 0;
    public long grouped_id;
    public int id;
    public int layer;
    public int local_id = 0;
    public TLRPC$MessageMedia media;
    public boolean media_unread;
    public boolean mentioned;
    public String message;
    public boolean out;
    public HashMap<String, String> params;
    public boolean post;
    public String post_author;
    public long random_id;
    public TLRPC$Message replyMessage;
    public TLRPC$ReplyMarkup reply_markup;
    public int reply_to_msg_id;
    public long reply_to_random_id;
    public int send_state = 0;
    public int seq_in;
    public int seq_out;
    public boolean silent;
    public TLRPC$Peer to_id;
    public int ttl;
    public boolean unread;
    public int via_bot_id;
    public String via_bot_name;
    public int views;
    public boolean with_my_score;

    public static TLRPC$Message TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Message result = null;
        switch (constructor) {
            case -2082087340:
                result = new TLRPC$TL_messageEmpty();
                break;
            case -1864508399:
                result = new TLRPC$TL_message_layer72();
                break;
            case -1642487306:
                result = new TLRPC$TL_messageService();
                break;
            case -1618124613:
                result = new TLRPC$TL_messageService_old();
                break;
            case -1553471722:
                result = new TLRPC$TL_messageForwarded_old2();
                break;
            case -1481959023:
                result = new TLRPC$TL_message_old3();
                break;
            case -1066691065:
                result = new TLRPC$TL_messageService_layer48();
                break;
            case -1063525281:
                result = new TLRPC$TL_message_layer68();
                break;
            case -1023016155:
                result = new TLRPC$TL_message_old4();
                break;
            case -913120932:
                result = new TLRPC$TL_message_layer47();
                break;
            case -260565816:
                result = new TLRPC$TL_message_old5();
                break;
            case 99903492:
                result = new TLRPC$TL_messageForwarded_old();
                break;
            case 495384334:
                result = new TLRPC$TL_messageService_old2();
                break;
            case 585853626:
                result = new TLRPC$TL_message_old();
                break;
            case 736885382:
                result = new TLRPC$TL_message_old6();
                break;
            case 1157215293:
                result = new TLRPC$TL_message();
                break;
            case 1431655928:
                result = new TLRPC$TL_message_secret_old();
                break;
            case 1431655929:
                result = new TLRPC$TL_message_secret_layer72();
                break;
            case 1431655930:
                result = new TLRPC$TL_message_secret();
                break;
            case 1450613171:
                result = new TLRPC$TL_message_old2();
                break;
            case 1537633299:
                result = new TLRPC$TL_message_old7();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Message", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
