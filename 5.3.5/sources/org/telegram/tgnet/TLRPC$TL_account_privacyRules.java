package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_account_privacyRules extends TLObject {
    public static int constructor = 1430961007;
    public ArrayList<TLRPC$PrivacyRule> rules = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_account_privacyRules TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_account_privacyRules result = new TLRPC$TL_account_privacyRules();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_account_privacyRules", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PrivacyRule object = TLRPC$PrivacyRule.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.rules.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    User object2 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.users.add(object2);
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
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.rules.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$PrivacyRule) this.rules.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
