package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DraftMessage;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.PeerNotifySettings;

public class TLRPC$TL_dialog extends TLObject {
    public static int constructor = -455150117;
    public DraftMessage draft;
    public int flags;
    public long id;
    public boolean isSelected = false;
    public int last_message_date;
    public PeerNotifySettings notify_settings;
    public Peer peer;
    public boolean pinned;
    public int pinnedNum;
    public int pts;
    public int read_inbox_max_id;
    public int read_outbox_max_id;
    public int top_message;
    public int unread_count;
    public int unread_mentions_count;

    public static TLRPC$TL_dialog TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_dialog tLRPC$TL_dialog = new TLRPC$TL_dialog();
            tLRPC$TL_dialog.readParams(abstractSerializedData, z);
            return tLRPC$TL_dialog;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_dialog", new Object[]{Integer.valueOf(i)}));
        }
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.pinned = (this.flags & 4) != 0;
        this.peer = Peer.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.top_message = abstractSerializedData.readInt32(z);
        this.read_inbox_max_id = abstractSerializedData.readInt32(z);
        this.read_outbox_max_id = abstractSerializedData.readInt32(z);
        this.unread_count = abstractSerializedData.readInt32(z);
        this.unread_mentions_count = abstractSerializedData.readInt32(z);
        this.notify_settings = PeerNotifySettings.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        if ((this.flags & 1) != 0) {
            this.pts = abstractSerializedData.readInt32(z);
        }
        if ((this.flags & 2) != 0) {
            this.draft = DraftMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.pinned ? this.flags | 4 : this.flags & -5;
        abstractSerializedData.writeInt32(this.flags);
        this.peer.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.top_message);
        abstractSerializedData.writeInt32(this.read_inbox_max_id);
        abstractSerializedData.writeInt32(this.read_outbox_max_id);
        abstractSerializedData.writeInt32(this.unread_count);
        abstractSerializedData.writeInt32(this.unread_mentions_count);
        this.notify_settings.serializeToStream(abstractSerializedData);
        if ((this.flags & 1) != 0) {
            abstractSerializedData.writeInt32(this.pts);
        }
        if ((this.flags & 2) != 0) {
            this.draft.serializeToStream(abstractSerializedData);
        }
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
