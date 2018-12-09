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
import org.telegram.tgnet.TLRPC.MessageMedia;

public class SlsBaseMessage extends ObjBase {
    private SlsMessage message;
    private int tagId;

    public static boolean isMediaAvailable(ObjBase objBase) {
        if (objBase instanceof SlsBaseMessage) {
            SlsBaseMessage slsBaseMessage = (SlsBaseMessage) objBase;
            if ((slsBaseMessage.getMessage().getMediaType() == 6 || slsBaseMessage.getMessage().getMediaType() == 8 || slsBaseMessage.getMessage().getMediaType() == 9 || slsBaseMessage.getMessage().getMediaType() == 2) && !TextUtils.isEmpty(slsBaseMessage.getMessage().getFileUrl())) {
                return true;
            }
        }
        return false;
    }

    public void castToMedia() {
        ArrayList arrayList = new ArrayList();
        Iterator it = this.message.media.document.attributes.iterator();
        while (it.hasNext()) {
            DocumentAttribute tLRPC$TL_documentAttributeVideo;
            DocumentAttribute documentAttribute = (DocumentAttribute) it.next();
            switch (this.message.getMediaType()) {
                case 1:
                    tLRPC$TL_documentAttributeVideo = new TLRPC$TL_documentAttributeVideo();
                    break;
                default:
                    tLRPC$TL_documentAttributeVideo = documentAttribute;
                    break;
            }
            tLRPC$TL_documentAttributeVideo.f10140w = documentAttribute.f10140w;
            tLRPC$TL_documentAttributeVideo.f10139h = documentAttribute.f10139h;
            tLRPC$TL_documentAttributeVideo.duration = documentAttribute.duration;
            tLRPC$TL_documentAttributeVideo.alt = documentAttribute.alt;
            tLRPC$TL_documentAttributeVideo.stickerset = documentAttribute.stickerset;
            tLRPC$TL_documentAttributeVideo.flags = documentAttribute.flags;
            tLRPC$TL_documentAttributeVideo.voice = documentAttribute.voice;
            tLRPC$TL_documentAttributeVideo.title = documentAttribute.title;
            tLRPC$TL_documentAttributeVideo.performer = documentAttribute.performer;
            tLRPC$TL_documentAttributeVideo.waveform = documentAttribute.waveform;
            tLRPC$TL_documentAttributeVideo.file_name = documentAttribute.file_name;
            arrayList.add(tLRPC$TL_documentAttributeVideo);
        }
        this.message.media.document.attributes = arrayList;
        MessageMedia tLRPC$TL_messageMediaVideo_layer45 = new TLRPC$TL_messageMediaVideo_layer45();
        tLRPC$TL_messageMediaVideo_layer45.caption = TtmlNode.ANONYMOUS_REGION_ID;
        tLRPC$TL_messageMediaVideo_layer45.document = this.message.media.document;
        this.message.media = tLRPC$TL_messageMediaVideo_layer45;
        this.message.post = true;
    }

    public TLRPC$TL_message castToTL_message() {
        castToMedia();
        TLRPC$TL_message tLRPC$TL_message = new TLRPC$TL_message();
        tLRPC$TL_message.dialog_id = this.message.dialog_id;
        tLRPC$TL_message.id = this.message.id;
        tLRPC$TL_message.message = this.message.message;
        tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
        tLRPC$TL_message.from_id = this.message.from_id;
        tLRPC$TL_message.media = this.message.media;
        tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
        tLRPC$TL_message.media.caption = this.message.media.caption;
        tLRPC$TL_message.media.document = this.message.media.document;
        tLRPC$TL_message.flags |= 768;
        tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
        return tLRPC$TL_message;
    }

    public SlsMessage getMessage() {
        return this.message;
    }

    public int getTagId() {
        return this.tagId;
    }

    public void setTagId(int i) {
        this.tagId = i;
    }
}
