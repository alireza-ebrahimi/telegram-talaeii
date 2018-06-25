package org.telegram.customization.dynamicadapter.data;

import android.content.Context;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.telegram.customization.util.ReadTextFile;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_messageMediaVideo_layer45;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TelegramMessage extends ObjBase implements Cloneable {
    private static TelegramMessage telegramMessage = null;
    private static String telegramMessageJson = null;
    public int mediaType;
    public TLRPC$Message message;

    public static TelegramMessage getTelegramMessage(Context context) throws CloneNotSupportedException {
        if (telegramMessage == null) {
            telegramMessage = (TelegramMessage) new Gson().fromJson(ReadTextFile.getFileText(context, "message.txt"), TelegramMessage.class);
            telegramMessage.castToMedia();
        }
        return (TelegramMessage) telegramMessage.clone();
    }

    public TLRPC$Message getMessage() {
        return this.message;
    }

    public void castToMedia() {
        ArrayList<DocumentAttribute> documentAttributes = new ArrayList();
        switch (this.mediaType) {
            case 8:
                documentAttributes.add(new TLRPC$TL_documentAttributeVideo());
                break;
        }
        this.message.media.document.attributes = documentAttributes;
        TLRPC$TL_messageMediaVideo_layer45 mediaDoc = new TLRPC$TL_messageMediaVideo_layer45();
        mediaDoc.caption = "";
        mediaDoc.document = this.message.media.document;
        this.message.media = mediaDoc;
        this.message.post = true;
    }

    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
