package org.telegram.messenger;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import java.io.File;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC$BotInlineResult;
import org.telegram.tgnet.TLRPC$TL_botInlineMediaResult;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_fileLocationUnavailable;
import org.telegram.tgnet.TLRPC$TL_game;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetEmpty;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoSize;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;

class SendMessagesHelper$19 implements Runnable {
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ HashMap val$params;
    final /* synthetic */ MessageObject val$reply_to_msg;
    final /* synthetic */ TLRPC$BotInlineResult val$result;

    SendMessagesHelper$19(TLRPC$BotInlineResult tLRPC$BotInlineResult, long j, HashMap hashMap, MessageObject messageObject) {
        this.val$result = tLRPC$BotInlineResult;
        this.val$dialog_id = j;
        this.val$params = hashMap;
        this.val$reply_to_msg = messageObject;
    }

    public void run() {
        String finalPath = null;
        TLRPC$TL_document document = null;
        TLRPC$TL_photo photo = null;
        TLRPC$TL_game game = null;
        if (!(this.val$result instanceof TLRPC$TL_botInlineMediaResult)) {
            if (this.val$result.content_url != null) {
                File file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(this.val$result.content_url) + "." + ImageLoader.getHttpUrlExtension(this.val$result.content_url, "file"));
                if (file.exists()) {
                    finalPath = file.getAbsolutePath();
                } else {
                    finalPath = this.val$result.content_url;
                }
                String str = this.val$result.type;
                Object obj = -1;
                switch (str.hashCode()) {
                    case -1890252483:
                        if (str.equals("sticker")) {
                            obj = 4;
                            break;
                        }
                        break;
                    case 102340:
                        if (str.equals("gif")) {
                            obj = 5;
                            break;
                        }
                        break;
                    case 3143036:
                        if (str.equals("file")) {
                            obj = 2;
                            break;
                        }
                        break;
                    case 93166550:
                        if (str.equals(MimeTypes.BASE_TYPE_AUDIO)) {
                            obj = null;
                            break;
                        }
                        break;
                    case 106642994:
                        if (str.equals("photo")) {
                            obj = 6;
                            break;
                        }
                        break;
                    case 112202875:
                        if (str.equals(MimeTypes.BASE_TYPE_VIDEO)) {
                            obj = 3;
                            break;
                        }
                        break;
                    case 112386354:
                        if (str.equals("voice")) {
                            obj = 1;
                            break;
                        }
                        break;
                }
                switch (obj) {
                    case null:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                        document = new TLRPC$TL_document();
                        document.id = 0;
                        document.size = 0;
                        document.dc_id = 0;
                        document.mime_type = this.val$result.content_type;
                        document.date = ConnectionsManager.getInstance().getCurrentTime();
                        TLRPC$TL_documentAttributeFilename fileName = new TLRPC$TL_documentAttributeFilename();
                        document.attributes.add(fileName);
                        str = this.val$result.type;
                        obj = -1;
                        switch (str.hashCode()) {
                            case -1890252483:
                                if (str.equals("sticker")) {
                                    obj = 5;
                                    break;
                                }
                                break;
                            case 102340:
                                if (str.equals("gif")) {
                                    obj = null;
                                    break;
                                }
                                break;
                            case 3143036:
                                if (str.equals("file")) {
                                    obj = 3;
                                    break;
                                }
                                break;
                            case 93166550:
                                if (str.equals(MimeTypes.BASE_TYPE_AUDIO)) {
                                    obj = 2;
                                    break;
                                }
                                break;
                            case 112202875:
                                if (str.equals(MimeTypes.BASE_TYPE_VIDEO)) {
                                    obj = 4;
                                    break;
                                }
                                break;
                            case 112386354:
                                if (str.equals("voice")) {
                                    obj = 1;
                                    break;
                                }
                                break;
                        }
                        Bitmap bitmap;
                        TLRPC$TL_documentAttributeAudio audio;
                        switch (obj) {
                            case null:
                                fileName.file_name = "animation.gif";
                                if (finalPath.endsWith("mp4")) {
                                    document.mime_type = MimeTypes.VIDEO_MP4;
                                    document.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                                } else {
                                    document.mime_type = "image/gif";
                                }
                                try {
                                    if (finalPath.endsWith("mp4")) {
                                        bitmap = ThumbnailUtils.createVideoThumbnail(finalPath, 1);
                                    } else {
                                        bitmap = ImageLoader.loadBitmap(finalPath, null, 90.0f, 90.0f, true);
                                    }
                                    if (bitmap != null) {
                                        document.thumb = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, false);
                                        bitmap.recycle();
                                        break;
                                    }
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                    break;
                                }
                                break;
                            case 1:
                                audio = new TLRPC$TL_documentAttributeAudio();
                                audio.duration = this.val$result.duration;
                                audio.voice = true;
                                fileName.file_name = "audio.ogg";
                                document.attributes.add(audio);
                                document.thumb = new TLRPC$TL_photoSizeEmpty();
                                document.thumb.type = "s";
                                break;
                            case 2:
                                audio = new TLRPC$TL_documentAttributeAudio();
                                audio.duration = this.val$result.duration;
                                audio.title = this.val$result.title;
                                audio.flags |= 1;
                                if (this.val$result.description != null) {
                                    audio.performer = this.val$result.description;
                                    audio.flags |= 2;
                                }
                                fileName.file_name = "audio.mp3";
                                document.attributes.add(audio);
                                document.thumb = new TLRPC$TL_photoSizeEmpty();
                                document.thumb.type = "s";
                                break;
                            case 3:
                                int idx = this.val$result.content_type.indexOf(47);
                                if (idx == -1) {
                                    fileName.file_name = "file";
                                    break;
                                } else {
                                    fileName.file_name = "file." + this.val$result.content_type.substring(idx + 1);
                                    break;
                                }
                            case 4:
                                fileName.file_name = "video.mp4";
                                TLRPC$TL_documentAttributeVideo attributeVideo = new TLRPC$TL_documentAttributeVideo();
                                attributeVideo.w = this.val$result.f68w;
                                attributeVideo.h = this.val$result.f67h;
                                attributeVideo.duration = this.val$result.duration;
                                document.attributes.add(attributeVideo);
                                try {
                                    bitmap = ImageLoader.loadBitmap(new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(this.val$result.thumb_url) + "." + ImageLoader.getHttpUrlExtension(this.val$result.thumb_url, "jpg")).getAbsolutePath(), null, 90.0f, 90.0f, true);
                                    if (bitmap != null) {
                                        document.thumb = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, false);
                                        bitmap.recycle();
                                        break;
                                    }
                                } catch (Throwable e2) {
                                    FileLog.e(e2);
                                    break;
                                }
                                break;
                            case 5:
                                TLRPC$TL_documentAttributeSticker attributeSticker = new TLRPC$TL_documentAttributeSticker();
                                attributeSticker.alt = "";
                                attributeSticker.stickerset = new TLRPC$TL_inputStickerSetEmpty();
                                document.attributes.add(attributeSticker);
                                TLRPC$TL_documentAttributeImageSize attributeImageSize = new TLRPC$TL_documentAttributeImageSize();
                                attributeImageSize.w = this.val$result.f68w;
                                attributeImageSize.h = this.val$result.f67h;
                                document.attributes.add(attributeImageSize);
                                fileName.file_name = "sticker.webp";
                                try {
                                    bitmap = ImageLoader.loadBitmap(new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(this.val$result.thumb_url) + "." + ImageLoader.getHttpUrlExtension(this.val$result.thumb_url, "webp")).getAbsolutePath(), null, 90.0f, 90.0f, true);
                                    if (bitmap != null) {
                                        document.thumb = ImageLoader.scaleAndSaveImage(bitmap, 90.0f, 90.0f, 55, false);
                                        bitmap.recycle();
                                        break;
                                    }
                                } catch (Throwable e22) {
                                    FileLog.e(e22);
                                    break;
                                }
                                break;
                        }
                        if (fileName.file_name == null) {
                            fileName.file_name = "file";
                        }
                        if (document.mime_type == null) {
                            document.mime_type = "application/octet-stream";
                        }
                        if (document.thumb == null) {
                            document.thumb = new TLRPC$TL_photoSize();
                            document.thumb.f78w = this.val$result.f68w;
                            document.thumb.f77h = this.val$result.f67h;
                            document.thumb.size = 0;
                            document.thumb.location = new TLRPC$TL_fileLocationUnavailable();
                            document.thumb.type = "x";
                            break;
                        }
                        break;
                    case 6:
                        if (file.exists()) {
                            photo = SendMessagesHelper.getInstance().generatePhotoSizes(finalPath, null);
                        }
                        if (photo == null) {
                            photo = new TLRPC$TL_photo();
                            photo.date = ConnectionsManager.getInstance().getCurrentTime();
                            TLRPC$TL_photoSize photoSize = new TLRPC$TL_photoSize();
                            photoSize.w = this.val$result.f68w;
                            photoSize.h = this.val$result.f67h;
                            photoSize.size = 1;
                            photoSize.location = new TLRPC$TL_fileLocationUnavailable();
                            photoSize.type = "x";
                            photo.sizes.add(photoSize);
                            break;
                        }
                        break;
                    default:
                        break;
                }
            }
        } else if (this.val$result.type.equals("game")) {
            if (((int) this.val$dialog_id) != 0) {
                game = new TLRPC$TL_game();
                game.title = this.val$result.title;
                game.description = this.val$result.description;
                game.short_name = this.val$result.id;
                game.photo = this.val$result.photo;
                if (this.val$result.document instanceof TLRPC$TL_document) {
                    game.document = this.val$result.document;
                    game.flags |= 1;
                }
            } else {
                return;
            }
        } else if (this.val$result.document != null) {
            if (this.val$result.document instanceof TLRPC$TL_document) {
                document = this.val$result.document;
            }
        } else if (this.val$result.photo != null && (this.val$result.photo instanceof TLRPC$TL_photo)) {
            photo = (TLRPC$TL_photo) this.val$result.photo;
        }
        final String finalPathFinal = finalPath;
        final TLRPC$TL_document finalDocument = document;
        final TLRPC$TL_photo finalPhoto = photo;
        final TLRPC$TL_game finalGame = game;
        if (!(this.val$params == null || this.val$result.content_url == null)) {
            this.val$params.put("originalPath", this.val$result.content_url);
        }
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                if (finalDocument != null) {
                    finalDocument.caption = SendMessagesHelper$19.this.val$result.send_message.caption;
                    SendMessagesHelper.getInstance().sendMessage(finalDocument, null, finalPathFinal, SendMessagesHelper$19.this.val$dialog_id, SendMessagesHelper$19.this.val$reply_to_msg, SendMessagesHelper$19.this.val$result.send_message.reply_markup, SendMessagesHelper$19.this.val$params, 0);
                } else if (finalPhoto != null) {
                    finalPhoto.caption = SendMessagesHelper$19.this.val$result.send_message.caption;
                    SendMessagesHelper.getInstance().sendMessage(finalPhoto, SendMessagesHelper$19.this.val$result.content_url, SendMessagesHelper$19.this.val$dialog_id, SendMessagesHelper$19.this.val$reply_to_msg, SendMessagesHelper$19.this.val$result.send_message.reply_markup, SendMessagesHelper$19.this.val$params, 0);
                } else if (finalGame != null) {
                    SendMessagesHelper.getInstance().sendMessage(finalGame, SendMessagesHelper$19.this.val$dialog_id, SendMessagesHelper$19.this.val$result.send_message.reply_markup, SendMessagesHelper$19.this.val$params);
                }
            }
        });
    }
}
