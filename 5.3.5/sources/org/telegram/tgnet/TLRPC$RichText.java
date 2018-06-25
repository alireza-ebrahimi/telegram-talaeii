package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$RichText extends TLObject {
    public String email;
    public TLRPC$RichText parentRichText;
    public ArrayList<TLRPC$RichText> texts = new ArrayList();
    public String url;
    public long webpage_id;

    public static TLRPC$RichText TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$RichText result = null;
        switch (constructor) {
            case -1678197867:
                result = new TLRPC$TL_textStrike();
                break;
            case -1054465340:
                result = new TLRPC$TL_textUnderline();
                break;
            case -653089380:
                result = new TLRPC$TL_textItalic();
                break;
            case -599948721:
                result = new TLRPC$TL_textEmpty();
                break;
            case -564523562:
                result = new TLRPC$TL_textEmail();
                break;
            case 1009288385:
                result = new TLRPC$TL_textUrl();
                break;
            case 1730456516:
                result = new TLRPC$TL_textBold();
                break;
            case 1816074681:
                result = new TLRPC$TL_textFixed();
                break;
            case 1950782688:
                result = new TLRPC$TL_textPlain();
                break;
            case 2120376535:
                result = new TLRPC$TL_textConcat();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in RichText", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
