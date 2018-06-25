package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_importedContacts extends TLObject {
    public static int constructor = 2010127419;
    public ArrayList<TLRPC$TL_importedContact> imported = new ArrayList();
    public ArrayList<TLRPC$TL_popularContact> popular_invites = new ArrayList();
    public ArrayList<Long> retry_contacts = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_contacts_importedContacts TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_contacts_importedContacts result = new TLRPC$TL_contacts_importedContacts();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_contacts_importedContacts", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_importedContact object = TLRPC$TL_importedContact.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.imported.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$TL_popularContact object2 = TLRPC$TL_popularContact.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.popular_invites.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    for (a = 0; a < count; a++) {
                        this.retry_contacts.add(Long.valueOf(stream.readInt64(exception)));
                    }
                    if (stream.readInt32(exception) == 481674261) {
                        count = stream.readInt32(exception);
                        a = 0;
                        while (a < count) {
                            User object3 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                            if (object3 != null) {
                                this.users.add(object3);
                                a++;
                            } else {
                                return;
                            }
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
        int count = this.imported.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_importedContact) this.imported.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.popular_invites.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_popularContact) this.popular_invites.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.retry_contacts.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.retry_contacts.get(a)).longValue());
        }
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
