package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$PrivacyRule extends TLObject {
    public ArrayList<Integer> users = new ArrayList();

    public static TLRPC$PrivacyRule TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$PrivacyRule result = null;
        switch (constructor) {
            case -1955338397:
                result = new TLRPC$TL_privacyValueDisallowAll();
                break;
            case -125240806:
                result = new TLRPC$TL_privacyValueDisallowContacts();
                break;
            case -123988:
                result = new TLRPC$TL_privacyValueAllowContacts();
                break;
            case 209668535:
                result = new TLRPC$TL_privacyValueDisallowUsers();
                break;
            case 1297858060:
                result = new TLRPC$TL_privacyValueAllowUsers();
                break;
            case 1698855810:
                result = new TLRPC$TL_privacyValueAllowAll();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in PrivacyRule", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
