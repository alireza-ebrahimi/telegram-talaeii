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

    public static TLRPC$TL_payments_paymentForm TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_payments_paymentForm tLRPC$TL_payments_paymentForm = new TLRPC$TL_payments_paymentForm();
            tLRPC$TL_payments_paymentForm.readParams(abstractSerializedData, z);
            return tLRPC$TL_payments_paymentForm;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_payments_paymentForm", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.can_save_credentials = (this.flags & 4) != 0;
        this.password_missing = (this.flags & 8) != 0;
        this.bot_id = abstractSerializedData.readInt32(z);
        this.invoice = TLRPC$TL_invoice.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.provider_id = abstractSerializedData.readInt32(z);
        this.url = abstractSerializedData.readString(z);
        if ((this.flags & 16) != 0) {
            this.native_provider = abstractSerializedData.readString(z);
        }
        if ((this.flags & 16) != 0) {
            this.native_params = TLRPC$TL_dataJSON.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 1) != 0) {
            this.saved_info = TLRPC$TL_paymentRequestedInfo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        if ((this.flags & 2) != 0) {
            this.saved_credentials = TLRPC$TL_paymentSavedCredentialsCard.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        }
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.users.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.can_save_credentials ? this.flags | 4 : this.flags & -5;
        this.flags = this.password_missing ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(this.bot_id);
        this.invoice.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.provider_id);
        abstractSerializedData.writeString(this.url);
        if ((this.flags & 16) != 0) {
            abstractSerializedData.writeString(this.native_provider);
        }
        if ((this.flags & 16) != 0) {
            this.native_params.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 1) != 0) {
            this.saved_info.serializeToStream(abstractSerializedData);
        }
        if ((this.flags & 2) != 0) {
            this.saved_credentials.serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((User) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
