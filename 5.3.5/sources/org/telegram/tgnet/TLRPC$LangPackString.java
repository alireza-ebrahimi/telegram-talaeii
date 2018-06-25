package org.telegram.tgnet;

public abstract class TLRPC$LangPackString extends TLObject {
    public String few_value;
    public int flags;
    public String key;
    public String many_value;
    public String one_value;
    public String other_value;
    public String two_value;
    public String value;
    public String zero_value;

    public static TLRPC$LangPackString TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$LangPackString result = null;
        switch (constructor) {
            case -892239370:
                result = new TLRPC$TL_langPackString();
                break;
            case 695856818:
                result = new TLRPC$TL_langPackStringDeleted();
                break;
            case 1816636575:
                result = new TLRPC$TL_langPackStringPluralized();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in LangPackString", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
