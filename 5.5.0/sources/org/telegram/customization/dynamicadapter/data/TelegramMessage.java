package org.telegram.customization.dynamicadapter.data;

import android.content.Context;
import com.google.p098a.C1768f;
import java.util.ArrayList;
import org.telegram.customization.util.C2886j;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_messageMediaVideo_layer45;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;

public class TelegramMessage extends ObjBase implements Cloneable {
    private static TelegramMessage telegramMessage = null;
    private static String telegramMessageJson = null;
    public int mediaType;
    public Message message;

    public static TelegramMessage getTelegramMessage(Context context) {
        if (telegramMessage == null) {
            telegramMessage = (TelegramMessage) new C1768f().m8392a(C2886j.m13401a(context, "message.txt"), TelegramMessage.class);
            telegramMessage.castToMedia();
        }
        return (TelegramMessage) telegramMessage.clone();
    }

    public void castToMedia() {
        ArrayList arrayList = new ArrayList();
        switch (this.mediaType) {
            case 8:
                arrayList.add(new TLRPC$TL_documentAttributeVideo());
                break;
        }
        this.message.media.document.attributes = arrayList;
        MessageMedia tLRPC$TL_messageMediaVideo_layer45 = new TLRPC$TL_messageMediaVideo_layer45();
        tLRPC$TL_messageMediaVideo_layer45.caption = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_messageMediaVideo_layer45.document = this.message.media.document;
        this.message.media = tLRPC$TL_messageMediaVideo_layer45;
        this.message.post = true;
    }

    protected Object clone() {
        return super.clone();
    }

    public Message getMessage() {
        return this.message;
    }
}
