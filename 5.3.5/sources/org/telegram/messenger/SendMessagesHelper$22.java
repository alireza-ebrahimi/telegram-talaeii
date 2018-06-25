package org.telegram.messenger;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Build.VERSION;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import java.util.HashMap;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAnimated;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo_layer65;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;

class SendMessagesHelper$22 implements Runnable {
    final /* synthetic */ CharSequence val$caption;
    final /* synthetic */ long val$dialog_id;
    final /* synthetic */ long val$duration;
    final /* synthetic */ long val$estimatedSize;
    final /* synthetic */ int val$height;
    final /* synthetic */ MessageObject val$reply_to_msg;
    final /* synthetic */ int val$ttl;
    final /* synthetic */ VideoEditedInfo val$videoEditedInfo;
    final /* synthetic */ String val$videoPath;
    final /* synthetic */ int val$width;

    SendMessagesHelper$22(long j, VideoEditedInfo videoEditedInfo, String str, long j2, int i, int i2, int i3, long j3, CharSequence charSequence, MessageObject messageObject) {
        this.val$dialog_id = j;
        this.val$videoEditedInfo = videoEditedInfo;
        this.val$videoPath = str;
        this.val$duration = j2;
        this.val$ttl = i;
        this.val$height = i2;
        this.val$width = i3;
        this.val$estimatedSize = j3;
        this.val$caption = charSequence;
        this.val$reply_to_msg = messageObject;
    }

    public void run() {
        boolean isEncrypted = ((int) this.val$dialog_id) == 0;
        boolean isRound = this.val$videoEditedInfo != null && this.val$videoEditedInfo.roundVideo;
        Bitmap thumb = null;
        String thumbKey = null;
        if (this.val$videoEditedInfo != null || this.val$videoPath.endsWith("mp4") || isRound) {
            String path = this.val$videoPath;
            String originalPath = this.val$videoPath;
            File file = new File(originalPath);
            long startTime = 0;
            originalPath = originalPath + file.length() + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + file.lastModified();
            if (this.val$videoEditedInfo != null) {
                if (!isRound) {
                    originalPath = originalPath + this.val$duration + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.val$videoEditedInfo.startTime + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.val$videoEditedInfo.endTime + (this.val$videoEditedInfo.muted ? "_m" : "");
                    if (this.val$videoEditedInfo.resultWidth == this.val$videoEditedInfo.originalWidth) {
                        originalPath = originalPath + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + this.val$videoEditedInfo.resultWidth;
                    }
                }
                startTime = this.val$videoEditedInfo.startTime >= 0 ? this.val$videoEditedInfo.startTime : 0;
            }
            TLRPC$TL_document tLRPC$TL_document = null;
            if (!isEncrypted && this.val$ttl == 0) {
                tLRPC$TL_document = (TLRPC$TL_document) MessagesStorage.getInstance().getSentFile(originalPath, !isEncrypted ? 2 : 5);
            }
            if (tLRPC$TL_document == null) {
                TLRPC$TL_documentAttributeVideo attributeVideo;
                thumb = SendMessagesHelper.access$1900(this.val$videoPath, startTime);
                if (thumb == null) {
                    thumb = ThumbnailUtils.createVideoThumbnail(this.val$videoPath, 1);
                }
                TLRPC$PhotoSize size = ImageLoader.scaleAndSaveImage(thumb, 90.0f, 90.0f, 55, isEncrypted);
                if (!(thumb == null || size == null)) {
                    if (!isRound) {
                        thumb = null;
                    } else if (isEncrypted) {
                        Utilities.blurBitmap(thumb, 7, VERSION.SDK_INT < 21 ? 0 : 1, thumb.getWidth(), thumb.getHeight(), thumb.getRowBytes());
                        thumbKey = String.format(size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id + "@%d_%d_b2", new Object[]{Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density)), Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density))});
                    } else {
                        Utilities.blurBitmap(thumb, 3, VERSION.SDK_INT < 21 ? 0 : 1, thumb.getWidth(), thumb.getHeight(), thumb.getRowBytes());
                        thumbKey = String.format(size.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + size.location.local_id + "@%d_%d_b", new Object[]{Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density)), Integer.valueOf((int) (((float) AndroidUtilities.roundMessageSize) / AndroidUtilities.density))});
                    }
                }
                tLRPC$TL_document = new TLRPC$TL_document();
                tLRPC$TL_document.thumb = size;
                if (tLRPC$TL_document.thumb == null) {
                    tLRPC$TL_document.thumb = new TLRPC$TL_photoSizeEmpty();
                    tLRPC$TL_document.thumb.type = "s";
                } else {
                    tLRPC$TL_document.thumb.type = "s";
                }
                tLRPC$TL_document.mime_type = MimeTypes.VIDEO_MP4;
                UserConfig.saveConfig(false);
                if (isEncrypted) {
                    TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (this.val$dialog_id >> 32)));
                    if (encryptedChat != null) {
                        if (AndroidUtilities.getPeerLayerVersion(encryptedChat.layer) >= 66) {
                            attributeVideo = new TLRPC$TL_documentAttributeVideo();
                        } else {
                            attributeVideo = new TLRPC$TL_documentAttributeVideo_layer65();
                        }
                    } else {
                        return;
                    }
                }
                attributeVideo = new TLRPC$TL_documentAttributeVideo();
                attributeVideo.round_message = isRound;
                tLRPC$TL_document.attributes.add(attributeVideo);
                if (this.val$videoEditedInfo == null || !this.val$videoEditedInfo.needConvert()) {
                    if (file.exists()) {
                        tLRPC$TL_document.size = (int) file.length();
                    }
                    SendMessagesHelper.access$2000(this.val$videoPath, attributeVideo, null);
                } else {
                    if (this.val$videoEditedInfo.muted) {
                        tLRPC$TL_document.attributes.add(new TLRPC$TL_documentAttributeAnimated());
                        SendMessagesHelper.access$2000(this.val$videoPath, attributeVideo, this.val$videoEditedInfo);
                        this.val$videoEditedInfo.originalWidth = attributeVideo.w;
                        this.val$videoEditedInfo.originalHeight = attributeVideo.h;
                        attributeVideo.w = this.val$videoEditedInfo.resultWidth;
                        attributeVideo.h = this.val$videoEditedInfo.resultHeight;
                    } else {
                        attributeVideo.duration = (int) (this.val$duration / 1000);
                        if (this.val$videoEditedInfo.rotationValue == 90 || this.val$videoEditedInfo.rotationValue == 270) {
                            attributeVideo.w = this.val$height;
                            attributeVideo.h = this.val$width;
                        } else {
                            attributeVideo.w = this.val$width;
                            attributeVideo.h = this.val$height;
                        }
                    }
                    tLRPC$TL_document.size = (int) this.val$estimatedSize;
                    String fileName = "-2147483648_" + UserConfig.lastLocalId + ".mp4";
                    UserConfig.lastLocalId--;
                    file = new File(FileLoader.getInstance().getDirectory(4), fileName);
                    UserConfig.saveConfig(false);
                    path = file.getAbsolutePath();
                }
            }
            final TLRPC$TL_document videoFinal = tLRPC$TL_document;
            String originalPathFinal = originalPath;
            final String finalPath = path;
            final HashMap<String, String> params = new HashMap();
            final Bitmap thumbFinal = thumb;
            final String thumbKeyFinal = thumbKey;
            if (this.val$caption != null) {
                videoFinal.caption = this.val$caption.toString();
            } else {
                videoFinal.caption = "";
            }
            if (originalPath != null) {
                params.put("originalPath", originalPath);
            }
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    if (!(thumbFinal == null || thumbKeyFinal == null)) {
                        ImageLoader.getInstance().putImageToCache(new BitmapDrawable(thumbFinal), thumbKeyFinal);
                    }
                    SendMessagesHelper.getInstance().sendMessage(videoFinal, SendMessagesHelper$22.this.val$videoEditedInfo, finalPath, SendMessagesHelper$22.this.val$dialog_id, SendMessagesHelper$22.this.val$reply_to_msg, null, params, SendMessagesHelper$22.this.val$ttl);
                }
            });
            return;
        }
        SendMessagesHelper.access$1500(this.val$videoPath, this.val$videoPath, null, null, this.val$dialog_id, this.val$reply_to_msg, this.val$caption);
    }
}
