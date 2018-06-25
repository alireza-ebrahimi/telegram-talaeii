package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$messages_BotResults extends TLObject {
    public int cache_time;
    public int flags;
    public boolean gallery;
    public String next_offset;
    public long query_id;
    public ArrayList<BotInlineResult> results = new ArrayList();
    public TLRPC$TL_inlineBotSwitchPM switch_pm;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$messages_BotResults TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$messages_BotResults tLRPC$messages_BotResults = null;
        switch (i) {
            case -1803769784:
                tLRPC$messages_BotResults = new TLRPC$TL_messages_botResults();
                break;
            case -858565059:
                tLRPC$messages_BotResults = new TLRPC$TL_messages_botResults_layer71();
                break;
        }
        if (tLRPC$messages_BotResults == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in messages_BotResults", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$messages_BotResults != null) {
            tLRPC$messages_BotResults.readParams(abstractSerializedData, z);
        }
        return tLRPC$messages_BotResults;
    }
}
