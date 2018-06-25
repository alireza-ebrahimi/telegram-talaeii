package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.BotInlineResult;

public class TLRPC$TL_messages_botResults_layer71 extends TLRPC$TL_messages_botResults {
    public static int constructor = -858565059;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.gallery = (this.flags & 1) != 0;
        this.query_id = abstractSerializedData.readInt64(z);
        if ((this.flags & 2) != 0) {
            this.next_offset = abstractSerializedData.readString(z);
        }
        if ((this.flags & 4) != 0) {
            this.switch_pm = TLRPC$TL_inlineBotSwitchPM.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                BotInlineResult TLdeserialize = BotInlineResult.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.results.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
            this.cache_time = abstractSerializedData.readInt32(z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.gallery ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.query_id);
        if ((this.flags & 2) != 0) {
            abstractSerializedData.writeString(this.next_offset);
        }
        if ((this.flags & 4) != 0) {
            this.switch_pm.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.results.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((BotInlineResult) this.results.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(this.cache_time);
    }
}
