package org.telegram.messenger;

import org.telegram.tgnet.TLRPC$TL_dialog;

public class DialogObject {
    public static boolean isChannel(TLRPC$TL_dialog dialog) {
        return (dialog == null || (dialog.flags & 1) == 0) ? false : true;
    }
}
