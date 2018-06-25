package org.telegram.tgnet;

import java.util.HashMap;
import java.util.Map.Entry;

public class TLRPC$TL_message extends TLRPC$Message {
    public static int constructor = 1157215293;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        int a;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.out = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mentioned = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.media_unread = z;
        if ((this.flags & 8192) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.silent = z;
        if ((this.flags & 16384) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.post = z;
        this.id = stream.readInt32(exception);
        if ((this.flags & 256) != 0) {
            this.from_id = stream.readInt32(exception);
        }
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 4) != 0) {
            this.fwd_from = TLRPC$MessageFwdHeader.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2048) != 0) {
            this.via_bot_id = stream.readInt32(exception);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = stream.readInt32(exception);
        }
        this.date = stream.readInt32(exception);
        this.message = stream.readString(exception);
        if ((this.flags & 512) != 0) {
            this.media = TLRPC$MessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (this.media != null) {
                this.ttl = this.media.ttl_seconds;
            }
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup = TLRPC$ReplyMarkup.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 128) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$MessageEntity object = TLRPC$MessageEntity.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.entities.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            } else {
                return;
            }
        }
        if ((this.flags & 1024) != 0) {
            this.views = stream.readInt32(exception);
        }
        if ((this.flags & 32768) != 0) {
            this.edit_date = stream.readInt32(exception);
        }
        if ((this.flags & 65536) != 0) {
            this.post_author = stream.readString(exception);
        }
        if ((this.flags & 131072) != 0) {
            this.grouped_id = stream.readInt64(exception);
        }
        if (this.id < 0 || !(this.media == null || (this.media instanceof TLRPC$TL_messageMediaEmpty) || (this.media instanceof TLRPC$TL_messageMediaWebPage) || this.message == null || this.message.length() == 0 || !this.message.startsWith("-1"))) {
            this.attachPath = stream.readString(exception);
            if (this.id < 0 && this.attachPath.startsWith("||")) {
                String[] args = this.attachPath.split("\\|\\|");
                if (args.length > 0) {
                    this.params = new HashMap();
                    for (a = 1; a < args.length - 1; a++) {
                        String[] args2 = args[a].split("\\|=\\|");
                        if (args2.length == 2) {
                            this.params.put(args2[0], args2[1]);
                        }
                    }
                    this.attachPath = args[args.length - 1];
                }
            }
        }
        if ((this.flags & 4) != 0 && this.id < 0) {
            this.fwd_msg_id = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        stream.writeInt32(constructor);
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        this.flags = this.silent ? this.flags | 8192 : this.flags & -8193;
        if (this.post) {
            i = this.flags | 16384;
        } else {
            i = this.flags & -16385;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        if ((this.flags & 256) != 0) {
            stream.writeInt32(this.from_id);
        }
        this.to_id.serializeToStream(stream);
        if ((this.flags & 4) != 0) {
            this.fwd_from.serializeToStream(stream);
        }
        if ((this.flags & 2048) != 0) {
            stream.writeInt32(this.via_bot_id);
        }
        if ((this.flags & 8) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        stream.writeInt32(this.date);
        stream.writeString(this.message);
        if ((this.flags & 512) != 0) {
            this.media.serializeToStream(stream);
        }
        if ((this.flags & 64) != 0) {
            this.reply_markup.serializeToStream(stream);
        }
        if ((this.flags & 128) != 0) {
            stream.writeInt32(481674261);
            int count = this.entities.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 1024) != 0) {
            stream.writeInt32(this.views);
        }
        if ((this.flags & 32768) != 0) {
            stream.writeInt32(this.edit_date);
        }
        if ((this.flags & 65536) != 0) {
            stream.writeString(this.post_author);
        }
        if ((this.flags & 131072) != 0) {
            stream.writeInt64(this.grouped_id);
        }
        String path = this.attachPath;
        if (this.id < 0 && this.params != null && this.params.size() > 0) {
            for (Entry<String, String> entry : this.params.entrySet()) {
                path = ((String) entry.getKey()) + "|=|" + ((String) entry.getValue()) + "||" + path;
            }
            path = "||" + path;
        }
        stream.writeString(path);
        if ((this.flags & 4) != 0 && this.id < 0) {
            stream.writeInt32(this.fwd_msg_id);
        }
    }
}
