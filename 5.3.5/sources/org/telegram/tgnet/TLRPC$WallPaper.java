package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$WallPaper extends TLObject {
    public int bg_color;
    public int color;
    public int id;
    public ArrayList<TLRPC$PhotoSize> sizes = new ArrayList();
    public String title;

    public static TLRPC$WallPaper TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$WallPaper result = null;
        switch (constructor) {
            case -860866985:
                result = new TLRPC$TL_wallPaper();
                break;
            case 1662091044:
                result = new TLRPC$TL_wallPaperSolid();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in WallPaper", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
