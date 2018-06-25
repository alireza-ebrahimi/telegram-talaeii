package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_messages_peerDialogs extends TLObject {
    public static int constructor = 863093588;
    public ArrayList<TLRPC$Chat> chats = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogs = new ArrayList();
    public ArrayList<TLRPC$Message> messages = new ArrayList();
    public TLRPC$TL_updates_state state;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_messages_peerDialogs TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_peerDialogs result = new TLRPC$TL_messages_peerDialogs();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_peerDialogs", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_dialog object = TLRPC$TL_dialog.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.dialogs.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$Message object2 = TLRPC$Message.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.messages.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        TLRPC$Chat object3 = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object3 != null) {
                            this.chats.add(object3);
                            a++;
                        } else {
                            return;
                        }
                    }
                    if (stream.readInt32(exception) == 481674261) {
                        count = stream.readInt32(exception);
                        a = 0;
                        while (a < count) {
                            User object4 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                            if (object4 != null) {
                                this.users.add(object4);
                                a++;
                            } else {
                                return;
                            }
                        }
                        this.state = TLRPC$TL_updates_state.TLdeserialize(stream, stream.readInt32(exception), exception);
                    } else if (exception) {
                        throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
                    }
                } else if (exception) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.dialogs.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_dialog) this.dialogs.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.messages.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Message) this.messages.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.chats.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Chat) this.chats.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
        this.state.serializeToStream(stream);
    }
}
