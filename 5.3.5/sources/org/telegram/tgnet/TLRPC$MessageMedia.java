package org.telegram.tgnet;

import org.telegram.messenger.exoplayer2.util.MimeTypes;

public class TLRPC$MessageMedia extends TLObject {
    public String address;
    public TLRPC$Audio audio_unused;
    public byte[] bytes;
    public String caption;
    public String currency;
    public String description;
    public TLRPC$Document document;
    public String first_name;
    public int flags;
    public TLRPC$TL_game game;
    public TLRPC$GeoPoint geo;
    public String last_name;
    public int period;
    public String phone_number;
    public TLRPC$Photo photo;
    public String provider;
    public int receipt_msg_id;
    public boolean shipping_address_requested;
    public String start_param;
    public boolean test;
    public String title;
    public long total_amount;
    public int ttl_seconds;
    public int user_id;
    public String venue_id;
    public String venue_type;
    public TLRPC$Video video_unused;
    public TLRPC$WebPage webpage;

    public static TLRPC$MessageMedia TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$MessageMedia result = null;
        switch (constructor) {
            case -2074799289:
                result = new TLRPC$TL_messageMediaInvoice();
                break;
            case -1618676578:
                result = new TLRPC$TL_messageMediaUnsupported();
                break;
            case -1563278704:
                result = new TLRPC$TL_messageMediaVideo_old();
                break;
            case -1557277184:
                result = new TLRPC$TL_messageMediaWebPage();
                break;
            case -1256047857:
                result = new TLRPC$TL_messageMediaPhoto();
                break;
            case -961117440:
                result = new TLRPC$TL_messageMediaAudio_layer45();
                break;
            case -926655958:
                result = new TLRPC$TL_messageMediaPhoto_old();
                break;
            case -203411800:
                result = new TLRPC$TL_messageMediaDocument_layer68();
                break;
            case -38694904:
                result = new TLRPC$TL_messageMediaGame();
                break;
            case 694364726:
                result = new TLRPC$TL_messageMediaUnsupported_old();
                break;
            case 784356159:
                result = new TLRPC$TL_messageMediaVenue();
                break;
            case 802824708:
                result = new TLRPC$TL_messageMediaDocument_old();
                break;
            case 1032643901:
                result = new TLRPC$TL_messageMediaPhoto_layer68();
                break;
            case 1038967584:
                result = new TLRPC$TL_messageMediaEmpty();
                break;
            case 1457575028:
                result = new TLRPC$TL_messageMediaGeo();
                break;
            case 1540298357:
                result = new TLRPC$TL_messageMediaVideo_layer45();
                break;
            case 1585262393:
                result = new TLRPC$TL_messageMediaContact();
                break;
            case 2031269663:
                result = new TLRPC$TL_messageMediaVenue_layer71();
                break;
            case 2084316681:
                result = new TLRPC$TL_messageMediaGeoLive();
                break;
            case 2084836563:
                result = new TLRPC$TL_messageMediaDocument();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in MessageMedia", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
            TLRPC$MessageMedia mediaDocument;
            if (result.video_unused != null) {
                mediaDocument = new TLRPC$TL_messageMediaDocument();
                if (result.video_unused instanceof TLRPC$TL_videoEncrypted) {
                    mediaDocument.document = new TLRPC$TL_documentEncrypted();
                    mediaDocument.document.key = result.video_unused.key;
                    mediaDocument.document.iv = result.video_unused.iv;
                } else {
                    mediaDocument.document = new TLRPC$TL_document();
                }
                mediaDocument.flags = 3;
                mediaDocument.document.id = result.video_unused.id;
                mediaDocument.document.access_hash = result.video_unused.access_hash;
                mediaDocument.document.date = result.video_unused.date;
                if (result.video_unused.mime_type != null) {
                    mediaDocument.document.mime_type = result.video_unused.mime_type;
                } else {
                    mediaDocument.document.mime_type = MimeTypes.VIDEO_MP4;
                }
                mediaDocument.document.size = result.video_unused.size;
                mediaDocument.document.thumb = result.video_unused.thumb;
                mediaDocument.document.dc_id = result.video_unused.dc_id;
                mediaDocument.caption = result.caption;
                TLRPC$TL_documentAttributeVideo attributeVideo = new TLRPC$TL_documentAttributeVideo();
                attributeVideo.w = result.video_unused.f91w;
                attributeVideo.h = result.video_unused.f90h;
                attributeVideo.duration = result.video_unused.duration;
                mediaDocument.document.attributes.add(attributeVideo);
                result = mediaDocument;
                if (mediaDocument.caption == null) {
                    mediaDocument.caption = "";
                }
            } else if (result.audio_unused != null) {
                mediaDocument = new TLRPC$TL_messageMediaDocument();
                if (result.audio_unused instanceof TLRPC$TL_audioEncrypted) {
                    mediaDocument.document = new TLRPC$TL_documentEncrypted();
                    mediaDocument.document.key = result.audio_unused.key;
                    mediaDocument.document.iv = result.audio_unused.iv;
                } else {
                    mediaDocument.document = new TLRPC$TL_document();
                }
                mediaDocument.flags = 3;
                mediaDocument.document.id = result.audio_unused.id;
                mediaDocument.document.access_hash = result.audio_unused.access_hash;
                mediaDocument.document.date = result.audio_unused.date;
                if (result.audio_unused.mime_type != null) {
                    mediaDocument.document.mime_type = result.audio_unused.mime_type;
                } else {
                    mediaDocument.document.mime_type = "audio/ogg";
                }
                mediaDocument.document.size = result.audio_unused.size;
                mediaDocument.document.thumb = new TLRPC$TL_photoSizeEmpty();
                mediaDocument.document.thumb.type = "s";
                mediaDocument.document.dc_id = result.audio_unused.dc_id;
                mediaDocument.caption = result.caption;
                TLRPC$TL_documentAttributeAudio attributeAudio = new TLRPC$TL_documentAttributeAudio();
                attributeAudio.duration = result.audio_unused.duration;
                attributeAudio.voice = true;
                mediaDocument.document.attributes.add(attributeAudio);
                result = mediaDocument;
                if (mediaDocument.caption == null) {
                    mediaDocument.caption = "";
                }
            }
        }
        return result;
    }
}
