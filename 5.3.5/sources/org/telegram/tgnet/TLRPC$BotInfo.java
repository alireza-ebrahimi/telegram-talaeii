package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$BotInfo extends TLObject {
    public ArrayList<TLRPC$TL_botCommand> commands = new ArrayList();
    public String description;
    public int user_id;
    public int version;

    public static TLRPC$BotInfo TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$BotInfo result = null;
        switch (constructor) {
            case -1729618630:
                result = new TLRPC$TL_botInfo();
                break;
            case -1154598962:
                result = new TLRPC$TL_botInfoEmpty_layer48();
                break;
            case 164583517:
                result = new TLRPC$TL_botInfo_layer48();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in BotInfo", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
