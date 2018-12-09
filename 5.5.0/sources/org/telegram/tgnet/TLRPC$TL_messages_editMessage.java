package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLRPC.InputGeoPoint;
import org.telegram.tgnet.TLRPC.InputPeer;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_messages_editMessage extends TLObject {
    public static int constructor = 97630429;
    public ArrayList<MessageEntity> entities = new ArrayList();
    public int flags;
    public InputGeoPoint geo_point;
    public int id;
    public String message;
    public boolean no_webpage;
    public InputPeer peer;
    public ReplyMarkup reply_markup;
    public boolean stop_geo_live;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.no_webpage ? this.flags | 2 : this.flags & -3;
        this.flags = this.stop_geo_live ? this.flags | 4096 : this.flags & -4097;
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.id);
        if ((this.flags & 2048) != 0) {
            abstractSerializedData.writeString(this.message);
        }
        if ((this.flags & 4) != 0) {
            this.reply_markup.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 8) != 0) {
            abstractSerializedData.writeInt32(481674261);
            int size = this.entities.size();
            abstractSerializedData.writeInt32(size);
            for (int i = 0; i < size; i++) {
                ((MessageEntity) this.entities.get(i)).serializeToStream(abstractSerializedData);
            }
        }
        if ((this.flags & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
            this.geo_point.serializeToStream(abstractSerializedData);
        }
    }
}
