package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_updates_differenceSlice extends TLRPC$updates_Difference {
    public static int constructor = -1459938943;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Message object = TLRPC$Message.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.new_messages.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$EncryptedMessage object2 = TLRPC$EncryptedMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.new_encrypted_messages.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        TLRPC$Update object3 = TLRPC$Update.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object3 != null) {
                            this.other_updates.add(object3);
                            a++;
                        } else {
                            return;
                        }
                    }
                    if (stream.readInt32(exception) == 481674261) {
                        count = stream.readInt32(exception);
                        a = 0;
                        while (a < count) {
                            TLRPC$Chat object4 = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
                            if (object4 != null) {
                                this.chats.add(object4);
                                a++;
                            } else {
                                return;
                            }
                        }
                        if (stream.readInt32(exception) == 481674261) {
                            count = stream.readInt32(exception);
                            a = 0;
                            while (a < count) {
                                User object5 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                                if (object5 != null) {
                                    this.users.add(object5);
                                    a++;
                                } else {
                                    return;
                                }
                            }
                            this.intermediate_state = TLRPC$TL_updates_state.TLdeserialize(stream, stream.readInt32(exception), exception);
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
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.new_messages.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Message) this.new_messages.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.new_encrypted_messages.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$EncryptedMessage) this.new_encrypted_messages.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.other_updates.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Update) this.other_updates.get(a)).serializeToStream(stream);
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
        this.intermediate_state.serializeToStream(stream);
    }
}
