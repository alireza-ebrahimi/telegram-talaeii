package org.telegram.tgnet;

public abstract class TLRPC$help_AppUpdate extends TLObject {
    public boolean critical;
    public int id;
    public String text;
    public String url;

    public static TLRPC$help_AppUpdate TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$help_AppUpdate tLRPC$help_AppUpdate = null;
        switch (i) {
            case -1987579119:
                tLRPC$help_AppUpdate = new TLRPC$TL_help_appUpdate();
                break;
            case -1000708810:
                tLRPC$help_AppUpdate = new TLRPC$TL_help_noAppUpdate();
                break;
        }
        if (tLRPC$help_AppUpdate == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in help_AppUpdate", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$help_AppUpdate != null) {
            tLRPC$help_AppUpdate.readParams(abstractSerializedData, z);
        }
        return tLRPC$help_AppUpdate;
    }
}
