package org.telegram.tgnet;

public abstract class TLRPC$KeyboardButton extends TLObject {
    public byte[] data;
    public int flags;
    public String query;
    public boolean same_peer;
    public String text;
    public String url;

    public static TLRPC$KeyboardButton TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$KeyboardButton result = null;
        switch (constructor) {
            case -1560655744:
                result = new TLRPC$TL_keyboardButton();
                break;
            case -1344716869:
                result = new TLRPC$TL_keyboardButtonBuy();
                break;
            case -1318425559:
                result = new TLRPC$TL_keyboardButtonRequestPhone();
                break;
            case -59151553:
                result = new TLRPC$TL_keyboardButtonRequestGeoLocation();
                break;
            case 90744648:
                result = new TLRPC$TL_keyboardButtonSwitchInline();
                break;
            case 629866245:
                result = new TLRPC$TL_keyboardButtonUrl();
                break;
            case 1358175439:
                result = new TLRPC$TL_keyboardButtonGame();
                break;
            case 1748655686:
                result = new TLRPC$TL_keyboardButtonCallback();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in KeyboardButton", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
