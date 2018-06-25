package org.telegram.customization.dynamicadapter.data;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_messageMediaVideo_layer45;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class SlsBaseMessage extends ObjBase {
    private SlsMessage message;
    private int tagId;

    public SlsMessage getMessage() {
        return this.message;
    }

    public TLRPC$TL_message castToTL_message() {
        castToMedia();
        TLRPC$TL_message msg = new TLRPC$TL_message();
        msg.dialog_id = this.message.dialog_id;
        msg.id = this.message.id;
        msg.message = this.message.message;
        msg.date = (int) (System.currentTimeMillis() / 1000);
        msg.from_id = this.message.from_id;
        msg.media = this.message.media;
        msg.media = new TLRPC$TL_messageMediaDocument();
        msg.media.caption = this.message.media.caption;
        msg.media.document = this.message.media.document;
        msg.flags |= 768;
        msg.to_id = new TLRPC$TL_peerUser();
        return msg;
    }

    public void castToMedia() {
        ArrayList<DocumentAttribute> documentAttributes = new ArrayList();
        Iterator it = this.message.media.document.attributes.iterator();
        while (it.hasNext()) {
            DocumentAttribute newAttr;
            DocumentAttribute attr = (DocumentAttribute) it.next();
            switch (this.message.getMediaType()) {
                case 1:
                    newAttr = new TLRPC$TL_documentAttributeVideo();
                    break;
                default:
                    newAttr = attr;
                    break;
            }
            newAttr.f44w = attr.f44w;
            newAttr.f43h = attr.f43h;
            newAttr.duration = attr.duration;
            newAttr.alt = attr.alt;
            newAttr.stickerset = attr.stickerset;
            newAttr.flags = attr.flags;
            newAttr.voice = attr.voice;
            newAttr.title = attr.title;
            newAttr.performer = attr.performer;
            newAttr.waveform = attr.waveform;
            newAttr.file_name = attr.file_name;
            documentAttributes.add(newAttr);
        }
        this.message.media.document.attributes = documentAttributes;
        TLRPC$TL_messageMediaVideo_layer45 mediaDoc = new TLRPC$TL_messageMediaVideo_layer45();
        mediaDoc.caption = "";
        mediaDoc.document = this.message.media.document;
        this.message.media = mediaDoc;
        this.message.post = true;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public static boolean isMediaAvailable(ObjBase objBase) {
        if (objBase instanceof SlsBaseMessage) {
            SlsBaseMessage slsBaseMessage = (SlsBaseMessage) objBase;
            if ((slsBaseMessage.getMessage().getMediaType() == 6 || slsBaseMessage.getMessage().getMediaType() == 8 || slsBaseMessage.getMessage().getMediaType() == 9 || slsBaseMessage.getMessage().getMediaType() == 2) && !TextUtils.isEmpty(slsBaseMessage.getMessage().getFileUrl())) {
                return true;
            }
        }
        return false;
    }
}
