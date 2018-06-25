package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$ReplyMarkup extends TLObject {
    public int flags;
    public boolean resize;
    public ArrayList<TLRPC$TL_keyboardButtonRow> rows = new ArrayList();
    public boolean selective;
    public boolean single_use;

    public static TLRPC$ReplyMarkup TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ReplyMarkup result = null;
        switch (constructor) {
            case -1606526075:
                result = new TLRPC$TL_replyKeyboardHide();
                break;
            case -200242528:
                result = new TLRPC$TL_replyKeyboardForceReply();
                break;
            case 889353612:
                result = new TLRPC$TL_replyKeyboardMarkup();
                break;
            case 1218642516:
                result = new TLRPC$TL_replyInlineMarkup();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ReplyMarkup", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
