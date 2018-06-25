package org.telegram.tgnet;

public class TLRPC$TL_dialog extends TLObject {
    public static int constructor = -455150117;
    public TLRPC$DraftMessage draft;
    public int flags;
    public long id;
    public boolean isSelected = false;
    public int last_message_date;
    public TLRPC$PeerNotifySettings notify_settings;
    public TLRPC$Peer peer;
    public boolean pinned;
    public int pinnedNum;
    public int pts;
    public int read_inbox_max_id;
    public int read_outbox_max_id;
    public int top_message;
    public int unread_count;
    public int unread_mentions_count;

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public static TLRPC$TL_dialog TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_dialog result = new TLRPC$TL_dialog();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_dialog", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.pinned = (this.flags & 4) != 0;
        this.peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.top_message = stream.readInt32(exception);
        this.read_inbox_max_id = stream.readInt32(exception);
        this.read_outbox_max_id = stream.readInt32(exception);
        this.unread_count = stream.readInt32(exception);
        this.unread_mentions_count = stream.readInt32(exception);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 1) != 0) {
            this.pts = stream.readInt32(exception);
        }
        if ((this.flags & 2) != 0) {
            this.draft = TLRPC$DraftMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.pinned ? this.flags | 4 : this.flags & -5;
        stream.writeInt32(this.flags);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.top_message);
        stream.writeInt32(this.read_inbox_max_id);
        stream.writeInt32(this.read_outbox_max_id);
        stream.writeInt32(this.unread_count);
        stream.writeInt32(this.unread_mentions_count);
        this.notify_settings.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.pts);
        }
        if ((this.flags & 2) != 0) {
            this.draft.serializeToStream(stream);
        }
    }
}
