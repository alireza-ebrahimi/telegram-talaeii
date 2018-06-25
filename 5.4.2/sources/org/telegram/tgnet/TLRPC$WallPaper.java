package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.PhotoSize;

public abstract class TLRPC$WallPaper extends TLObject {
    public int bg_color;
    public int color;
    public int id;
    public ArrayList<PhotoSize> sizes = new ArrayList();
    public String title;

    public static TLRPC$WallPaper TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$WallPaper tLRPC$WallPaper = null;
        switch (i) {
            case -860866985:
                tLRPC$WallPaper = new TLRPC$TL_wallPaper();
                break;
            case 1662091044:
                tLRPC$WallPaper = new TLRPC$TL_wallPaperSolid();
                break;
        }
        if (tLRPC$WallPaper == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in WallPaper", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$WallPaper != null) {
            tLRPC$WallPaper.readParams(abstractSerializedData, z);
        }
        return tLRPC$WallPaper;
    }
}
