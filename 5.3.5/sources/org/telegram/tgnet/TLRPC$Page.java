package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$Page extends TLObject {
    public ArrayList<TLRPC$PageBlock> blocks = new ArrayList();
    public ArrayList<TLRPC$Document> documents = new ArrayList();
    public ArrayList<TLRPC$Photo> photos = new ArrayList();

    public static TLRPC$Page TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Page result = null;
        switch (constructor) {
            case -1913754556:
                result = new TLRPC$TL_pagePart_layer67();
                break;
            case -1908433218:
                result = new TLRPC$TL_pagePart();
                break;
            case -677274263:
                result = new TLRPC$TL_pageFull_layer67();
                break;
            case 1433323434:
                result = new TLRPC$TL_pageFull();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Page", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
