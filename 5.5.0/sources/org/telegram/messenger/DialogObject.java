package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$TL_dialog;

public class DialogObject {
    public static boolean isChannel(TLRPC$TL_dialog tLRPC$TL_dialog) {
        return (tLRPC$TL_dialog == null || (tLRPC$TL_dialog.flags & 1) == 0) ? false : true;
    }
}
