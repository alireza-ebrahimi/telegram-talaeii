package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$InputPrivacyRule extends TLObject {
    public ArrayList<TLRPC$InputUser> users = new ArrayList();

    public static TLRPC$InputPrivacyRule TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPrivacyRule result = null;
        switch (constructor) {
            case -1877932953:
                result = new TLRPC$TL_inputPrivacyValueDisallowUsers();
                break;
            case -697604407:
                result = new TLRPC$TL_inputPrivacyValueDisallowAll();
                break;
            case 195371015:
                result = new TLRPC$TL_inputPrivacyValueDisallowContacts();
                break;
            case 218751099:
                result = new TLRPC$TL_inputPrivacyValueAllowContacts();
                break;
            case 320652927:
                result = new TLRPC$TL_inputPrivacyValueAllowUsers();
                break;
            case 407582158:
                result = new TLRPC$TL_inputPrivacyValueAllowAll();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPrivacyRule", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
