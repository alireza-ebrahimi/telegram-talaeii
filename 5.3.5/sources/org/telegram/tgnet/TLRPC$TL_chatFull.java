package org.telegram.tgnet;

public class TLRPC$TL_chatFull extends TLRPC$ChatFull {
    public static int constructor = 771925524;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.participants = TLRPC$ChatParticipants.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.chat_photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.notify_settings = TLRPC$PeerNotifySettings.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.exported_invite = TLRPC$ExportedChatInvite.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$BotInfo object = TLRPC$BotInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.bot_info.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        this.participants.serializeToStream(stream);
        this.chat_photo.serializeToStream(stream);
        this.notify_settings.serializeToStream(stream);
        this.exported_invite.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.bot_info.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$BotInfo) this.bot_info.get(a)).serializeToStream(stream);
        }
    }
}
