package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_BotResults extends TLObject {
    public int cache_time;
    public int flags;
    public boolean gallery;
    public String next_offset;
    public long query_id;
    public ArrayList<TLRPC$BotInlineResult> results = new ArrayList();
    public TLRPC$TL_inlineBotSwitchPM switch_pm;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_BotResults TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$messages_BotResults result = null;
        switch (constructor) {
            case -1803769784:
                result = new TLRPC$TL_messages_botResults();
                break;
            case -858565059:
                result = new TLRPC$TL_messages_botResults_layer71();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_BotResults", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
