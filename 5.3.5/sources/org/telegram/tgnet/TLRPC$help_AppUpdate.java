package org.telegram.tgnet;

public abstract class TLRPC$help_AppUpdate extends TLObject {
    public boolean critical;
    public int id;
    public String text;
    public String url;

    public static TLRPC$help_AppUpdate TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$help_AppUpdate result = null;
        switch (constructor) {
            case -1987579119:
                result = new TLRPC$TL_help_appUpdate();
                break;
            case -1000708810:
                result = new TLRPC$TL_help_noAppUpdate();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in help_AppUpdate", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
