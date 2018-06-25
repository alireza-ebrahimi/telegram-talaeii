package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_payments_paymentForm extends TLObject {
    public static int constructor = 1062645411;
    public int bot_id;
    public boolean can_save_credentials;
    public int flags;
    public TLRPC$TL_invoice invoice;
    public TLRPC$TL_dataJSON native_params;
    public String native_provider;
    public boolean password_missing;
    public int provider_id;
    public TLRPC$TL_paymentSavedCredentialsCard saved_credentials;
    public TLRPC$TL_paymentRequestedInfo saved_info;
    public String url;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_payments_paymentForm TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_payments_paymentForm result = new TLRPC$TL_payments_paymentForm();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_paymentForm", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.can_save_credentials = z;
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.password_missing = z;
        this.bot_id = stream.readInt32(exception);
        this.invoice = TLRPC$TL_invoice.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.provider_id = stream.readInt32(exception);
        this.url = stream.readString(exception);
        if ((this.flags & 16) != 0) {
            this.native_provider = stream.readString(exception);
        }
        if ((this.flags & 16) != 0) {
            this.native_params = TLRPC$TL_dataJSON.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 1) != 0) {
            this.saved_info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2) != 0) {
            this.saved_credentials = TLRPC$TL_paymentSavedCredentialsCard.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                User object = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.users.add(object);
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
        int i;
        stream.writeInt32(constructor);
        this.flags = this.can_save_credentials ? this.flags | 4 : this.flags & -5;
        if (this.password_missing) {
            i = this.flags | 8;
        } else {
            i = this.flags & -9;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.bot_id);
        this.invoice.serializeToStream(stream);
        stream.writeInt32(this.provider_id);
        stream.writeString(this.url);
        if ((this.flags & 16) != 0) {
            stream.writeString(this.native_provider);
        }
        if ((this.flags & 16) != 0) {
            this.native_params.serializeToStream(stream);
        }
        if ((this.flags & 1) != 0) {
            this.saved_info.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            this.saved_credentials.serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        int count = this.users.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
