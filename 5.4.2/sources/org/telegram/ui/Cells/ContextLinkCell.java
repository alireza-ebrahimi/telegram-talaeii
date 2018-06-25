package org.telegram.ui.Cells;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.animation.AccelerateInterpolator;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.tgnet.TLRPC$TL_document;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_documentAttributeFilename;
import org.telegram.tgnet.TLRPC$TL_documentAttributeImageSize;
import org.telegram.tgnet.TLRPC$TL_documentAttributeVideo;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageMediaDocument;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photo;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC.BotInlineResult;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.MessageMedia;
import org.telegram.tgnet.TLRPC.Peer;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaGeo;
import org.telegram.tgnet.TLRPC.TL_botInlineMessageMediaVenue;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LetterDrawable;
import org.telegram.ui.Components.RadialProgress;
import org.telegram.ui.PhotoViewer;

public class ContextLinkCell extends View implements FileDownloadProgressListener {
    private static final int DOCUMENT_ATTACH_TYPE_AUDIO = 3;
    private static final int DOCUMENT_ATTACH_TYPE_DOCUMENT = 1;
    private static final int DOCUMENT_ATTACH_TYPE_GEO = 8;
    private static final int DOCUMENT_ATTACH_TYPE_GIF = 2;
    private static final int DOCUMENT_ATTACH_TYPE_MUSIC = 5;
    private static final int DOCUMENT_ATTACH_TYPE_NONE = 0;
    private static final int DOCUMENT_ATTACH_TYPE_PHOTO = 7;
    private static final int DOCUMENT_ATTACH_TYPE_STICKER = 6;
    private static final int DOCUMENT_ATTACH_TYPE_VIDEO = 4;
    private static AccelerateInterpolator interpolator = new AccelerateInterpolator(0.5f);
    private int TAG = MediaController.getInstance().generateObserverTag();
    private boolean buttonPressed;
    private int buttonState;
    private MessageObject currentMessageObject;
    private PhotoSize currentPhotoObject;
    private ContextLinkCellDelegate delegate;
    private StaticLayout descriptionLayout;
    private int descriptionY = AndroidUtilities.dp(27.0f);
    private Document documentAttach;
    private int documentAttachType;
    private boolean drawLinkImageView;
    private BotInlineResult inlineResult;
    private long lastUpdateTime;
    private LetterDrawable letterDrawable = new LetterDrawable();
    private ImageReceiver linkImageView = new ImageReceiver(this);
    private StaticLayout linkLayout;
    private int linkY;
    private boolean mediaWebpage;
    private boolean needDivider;
    private boolean needShadow;
    private RadialProgress radialProgress = new RadialProgress(this);
    private float scale;
    private boolean scaled;
    private long time = 0;
    private StaticLayout titleLayout;
    private int titleY = AndroidUtilities.dp(7.0f);

    public interface ContextLinkCellDelegate {
        void didPressedImage(ContextLinkCell contextLinkCell);
    }

    public ContextLinkCell(Context context) {
        super(context);
    }

    private void didPressedButton() {
        if (this.documentAttachType != 3 && this.documentAttachType != 5) {
            return;
        }
        if (this.buttonState == 0) {
            if (MediaController.getInstance().playMessage(this.currentMessageObject)) {
                this.buttonState = 1;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        } else if (this.buttonState == 1) {
            if (MediaController.getInstance().pauseMessage(this.currentMessageObject)) {
                this.buttonState = 0;
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
                invalidate();
            }
        } else if (this.buttonState == 2) {
            this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, false);
            if (this.documentAttach != null) {
                FileLoader.getInstance().loadFile(this.documentAttach, true, 0);
            } else {
                ImageLoader.getInstance().loadHttpFile(this.inlineResult.content_url, this.documentAttachType == 5 ? "mp3" : "ogg");
            }
            this.buttonState = 4;
            this.radialProgress.setBackground(getDrawableForCurrentState(), true, false);
            invalidate();
        } else if (this.buttonState == 4) {
            if (this.documentAttach != null) {
                FileLoader.getInstance().cancelLoadFile(this.documentAttach);
            } else {
                ImageLoader.getInstance().cancelLoadHttpFile(this.inlineResult.content_url);
            }
            this.buttonState = 2;
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, false);
            invalidate();
        }
    }

    private Drawable getDrawableForCurrentState() {
        int i = 1;
        if (this.documentAttachType != 3 && this.documentAttachType != 5) {
            return this.buttonState == 1 ? Theme.chat_photoStatesDrawables[5][0] : null;
        } else {
            if (this.buttonState == -1) {
                return null;
            }
            this.radialProgress.setAlphaForPrevious(false);
            Drawable[] drawableArr = Theme.chat_fileStatesDrawable[this.buttonState + 5];
            if (!this.buttonPressed) {
                i = 0;
            }
            return drawableArr[i];
        }
    }

    private void setAttachType() {
        this.currentMessageObject = null;
        this.documentAttachType = 0;
        if (this.documentAttach != null) {
            if (MessageObject.isGifDocument(this.documentAttach)) {
                this.documentAttachType = 2;
            } else if (MessageObject.isStickerDocument(this.documentAttach)) {
                this.documentAttachType = 6;
            } else if (MessageObject.isMusicDocument(this.documentAttach)) {
                this.documentAttachType = 5;
            } else if (MessageObject.isVoiceDocument(this.documentAttach)) {
                this.documentAttachType = 3;
            }
        } else if (this.inlineResult != null) {
            if (this.inlineResult.photo != null) {
                this.documentAttachType = 7;
            } else if (this.inlineResult.type.equals(MimeTypes.BASE_TYPE_AUDIO)) {
                this.documentAttachType = 5;
            } else if (this.inlineResult.type.equals("voice")) {
                this.documentAttachType = 3;
            }
        }
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            Message tLRPC$TL_message = new TLRPC$TL_message();
            tLRPC$TL_message.out = true;
            tLRPC$TL_message.id = -Utilities.random.nextInt();
            tLRPC$TL_message.to_id = new TLRPC$TL_peerUser();
            Peer peer = tLRPC$TL_message.to_id;
            int clientUserId = UserConfig.getClientUserId();
            tLRPC$TL_message.from_id = clientUserId;
            peer.user_id = clientUserId;
            tLRPC$TL_message.date = (int) (System.currentTimeMillis() / 1000);
            tLRPC$TL_message.message = "-1";
            tLRPC$TL_message.media = new TLRPC$TL_messageMediaDocument();
            MessageMedia messageMedia = tLRPC$TL_message.media;
            messageMedia.flags |= 3;
            tLRPC$TL_message.media.document = new TLRPC$TL_document();
            tLRPC$TL_message.flags |= 768;
            if (this.documentAttach != null) {
                tLRPC$TL_message.media.document = this.documentAttach;
                tLRPC$TL_message.attachPath = TtmlNode.ANONYMOUS_REGION_ID;
            } else {
                String httpUrlExtension = ImageLoader.getHttpUrlExtension(this.inlineResult.content_url, this.documentAttachType == 5 ? "mp3" : "ogg");
                tLRPC$TL_message.media.document.id = 0;
                tLRPC$TL_message.media.document.access_hash = 0;
                tLRPC$TL_message.media.document.date = tLRPC$TL_message.date;
                tLRPC$TL_message.media.document.mime_type = "audio/" + httpUrlExtension;
                tLRPC$TL_message.media.document.size = 0;
                tLRPC$TL_message.media.document.thumb = new TLRPC$TL_photoSizeEmpty();
                tLRPC$TL_message.media.document.thumb.type = "s";
                tLRPC$TL_message.media.document.dc_id = 0;
                TLRPC$TL_documentAttributeAudio tLRPC$TL_documentAttributeAudio = new TLRPC$TL_documentAttributeAudio();
                tLRPC$TL_documentAttributeAudio.duration = this.inlineResult.duration;
                tLRPC$TL_documentAttributeAudio.title = this.inlineResult.title != null ? this.inlineResult.title : TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_documentAttributeAudio.performer = this.inlineResult.description != null ? this.inlineResult.description : TtmlNode.ANONYMOUS_REGION_ID;
                tLRPC$TL_documentAttributeAudio.flags |= 3;
                if (this.documentAttachType == 3) {
                    tLRPC$TL_documentAttributeAudio.voice = true;
                }
                tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeAudio);
                TLRPC$TL_documentAttributeFilename tLRPC$TL_documentAttributeFilename = new TLRPC$TL_documentAttributeFilename();
                tLRPC$TL_documentAttributeFilename.file_name = Utilities.MD5(this.inlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(this.inlineResult.content_url, this.documentAttachType == 5 ? "mp3" : "ogg");
                tLRPC$TL_message.media.document.attributes.add(tLRPC$TL_documentAttributeFilename);
                tLRPC$TL_message.attachPath = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(this.inlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(this.inlineResult.content_url, this.documentAttachType == 5 ? "mp3" : "ogg")).getAbsolutePath();
            }
            this.currentMessageObject = new MessageObject(tLRPC$TL_message, null, false);
        }
    }

    public Document getDocument() {
        return this.documentAttach;
    }

    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public int getObserverTag() {
        return this.TAG;
    }

    public ImageReceiver getPhotoImage() {
        return this.linkImageView;
    }

    public BotInlineResult getResult() {
        return this.inlineResult;
    }

    public boolean isSticker() {
        return this.documentAttachType == 6;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.drawLinkImageView && this.linkImageView.onAttachedToWindow()) {
            updateButtonState(false);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.drawLinkImageView) {
            this.linkImageView.onDetachedFromWindow();
        }
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    protected void onDraw(Canvas canvas) {
        float f = 8.0f;
        if (this.titleLayout != null) {
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.titleY);
            this.titleLayout.draw(canvas);
            canvas.restore();
        }
        if (this.descriptionLayout != null) {
            Theme.chat_contextResult_descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText2));
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.descriptionY);
            this.descriptionLayout.draw(canvas);
            canvas.restore();
        }
        if (this.linkLayout != null) {
            Theme.chat_contextResult_descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
            canvas.save();
            if (!LocaleController.isRTL) {
                f = (float) AndroidUtilities.leftBaseline;
            }
            canvas.translate((float) AndroidUtilities.dp(f), (float) this.linkY);
            this.linkLayout.draw(canvas);
            canvas.restore();
        }
        int intrinsicWidth;
        int intrinsicHeight;
        int imageX;
        int imageY;
        if (this.mediaWebpage) {
            if (this.inlineResult != null && ((this.inlineResult.send_message instanceof TL_botInlineMessageMediaGeo) || (this.inlineResult.send_message instanceof TL_botInlineMessageMediaVenue))) {
                intrinsicWidth = Theme.chat_inlineResultLocation.getIntrinsicWidth();
                intrinsicHeight = Theme.chat_inlineResultLocation.getIntrinsicHeight();
                imageX = this.linkImageView.getImageX() + ((this.linkImageView.getImageWidth() - intrinsicWidth) / 2);
                imageY = this.linkImageView.getImageY() + ((this.linkImageView.getImageHeight() - intrinsicHeight) / 2);
                canvas.drawRect((float) this.linkImageView.getImageX(), (float) this.linkImageView.getImageY(), (float) (this.linkImageView.getImageX() + this.linkImageView.getImageWidth()), (float) (this.linkImageView.getImageY() + this.linkImageView.getImageHeight()), LetterDrawable.paint);
                Theme.chat_inlineResultLocation.setBounds(imageX, imageY, imageX + intrinsicWidth, imageY + intrinsicHeight);
                Theme.chat_inlineResultLocation.draw(canvas);
            }
        } else if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            this.radialProgress.setProgressColor(Theme.getColor(this.buttonPressed ? Theme.key_chat_inAudioSelectedProgress : Theme.key_chat_inAudioProgress));
            this.radialProgress.draw(canvas);
        } else if (this.inlineResult != null && this.inlineResult.type.equals("file")) {
            intrinsicWidth = Theme.chat_inlineResultFile.getIntrinsicWidth();
            intrinsicHeight = Theme.chat_inlineResultFile.getIntrinsicHeight();
            imageX = this.linkImageView.getImageX() + ((AndroidUtilities.dp(52.0f) - intrinsicWidth) / 2);
            imageY = this.linkImageView.getImageY() + ((AndroidUtilities.dp(52.0f) - intrinsicHeight) / 2);
            canvas.drawRect((float) this.linkImageView.getImageX(), (float) this.linkImageView.getImageY(), (float) (this.linkImageView.getImageX() + AndroidUtilities.dp(52.0f)), (float) (this.linkImageView.getImageY() + AndroidUtilities.dp(52.0f)), LetterDrawable.paint);
            Theme.chat_inlineResultFile.setBounds(imageX, imageY, imageX + intrinsicWidth, imageY + intrinsicHeight);
            Theme.chat_inlineResultFile.draw(canvas);
        } else if (this.inlineResult != null && (this.inlineResult.type.equals(MimeTypes.BASE_TYPE_AUDIO) || this.inlineResult.type.equals("voice"))) {
            intrinsicWidth = Theme.chat_inlineResultAudio.getIntrinsicWidth();
            intrinsicHeight = Theme.chat_inlineResultAudio.getIntrinsicHeight();
            imageX = this.linkImageView.getImageX() + ((AndroidUtilities.dp(52.0f) - intrinsicWidth) / 2);
            imageY = this.linkImageView.getImageY() + ((AndroidUtilities.dp(52.0f) - intrinsicHeight) / 2);
            canvas.drawRect((float) this.linkImageView.getImageX(), (float) this.linkImageView.getImageY(), (float) (this.linkImageView.getImageX() + AndroidUtilities.dp(52.0f)), (float) (this.linkImageView.getImageY() + AndroidUtilities.dp(52.0f)), LetterDrawable.paint);
            Theme.chat_inlineResultAudio.setBounds(imageX, imageY, imageX + intrinsicWidth, imageY + intrinsicHeight);
            Theme.chat_inlineResultAudio.draw(canvas);
        } else if (this.inlineResult == null || !(this.inlineResult.type.equals("venue") || this.inlineResult.type.equals("geo"))) {
            this.letterDrawable.draw(canvas);
        } else {
            intrinsicWidth = Theme.chat_inlineResultLocation.getIntrinsicWidth();
            intrinsicHeight = Theme.chat_inlineResultLocation.getIntrinsicHeight();
            imageX = this.linkImageView.getImageX() + ((AndroidUtilities.dp(52.0f) - intrinsicWidth) / 2);
            imageY = this.linkImageView.getImageY() + ((AndroidUtilities.dp(52.0f) - intrinsicHeight) / 2);
            canvas.drawRect((float) this.linkImageView.getImageX(), (float) this.linkImageView.getImageY(), (float) (this.linkImageView.getImageX() + AndroidUtilities.dp(52.0f)), (float) (this.linkImageView.getImageY() + AndroidUtilities.dp(52.0f)), LetterDrawable.paint);
            Theme.chat_inlineResultLocation.setBounds(imageX, imageY, imageX + intrinsicWidth, imageY + intrinsicHeight);
            Theme.chat_inlineResultLocation.draw(canvas);
        }
        if (this.drawLinkImageView) {
            if (this.inlineResult != null) {
                this.linkImageView.setVisible(!PhotoViewer.getInstance().isShowingImage(this.inlineResult), false);
            }
            canvas.save();
            if ((this.scaled && this.scale != 0.8f) || !(this.scaled || this.scale == 1.0f)) {
                long currentTimeMillis = System.currentTimeMillis();
                long j = currentTimeMillis - this.lastUpdateTime;
                this.lastUpdateTime = currentTimeMillis;
                if (!this.scaled || this.scale == 0.8f) {
                    this.scale += ((float) j) / 400.0f;
                    if (this.scale > 1.0f) {
                        this.scale = 1.0f;
                    }
                } else {
                    this.scale -= ((float) j) / 400.0f;
                    if (this.scale < 0.8f) {
                        this.scale = 0.8f;
                    }
                }
                invalidate();
            }
            canvas.scale(this.scale, this.scale, (float) (getMeasuredWidth() / 2), (float) (getMeasuredHeight() / 2));
            this.linkImageView.draw(canvas);
            canvas.restore();
        }
        if (this.mediaWebpage && (this.documentAttachType == 7 || this.documentAttachType == 2)) {
            this.radialProgress.draw(canvas);
        }
        if (this.needDivider && !this.mediaWebpage) {
            if (LocaleController.isRTL) {
                canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
            } else {
                canvas.drawLine((float) AndroidUtilities.dp((float) AndroidUtilities.leftBaseline), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
            }
        }
        if (this.needShadow) {
            Theme.chat_contextResult_shadowUnderSwitchDrawable.setBounds(0, 0, getMeasuredWidth(), AndroidUtilities.dp(3.0f));
            Theme.chat_contextResult_shadowUnderSwitchDrawable.draw(canvas);
        }
    }

    public void onFailedDownload(String str) {
        updateButtonState(false);
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        this.drawLinkImageView = false;
        this.descriptionLayout = null;
        this.titleLayout = null;
        this.linkLayout = null;
        this.currentPhotoObject = null;
        this.linkY = AndroidUtilities.dp(27.0f);
        if (this.inlineResult == null && this.documentAttach == null) {
            setMeasuredDimension(AndroidUtilities.dp(100.0f), AndroidUtilities.dp(100.0f));
            return;
        }
        ArrayList arrayList;
        PhotoSize photoSize;
        String str;
        int i3;
        int i4;
        int size = MeasureSpec.getSize(i);
        int dp = (size - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - AndroidUtilities.dp(8.0f);
        if (this.documentAttach != null) {
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(this.documentAttach.thumb);
            arrayList = arrayList2;
        } else {
            arrayList = (this.inlineResult == null || this.inlineResult.photo == null) ? null : new ArrayList(this.inlineResult.photo.sizes);
        }
        if (!(this.mediaWebpage || this.inlineResult == null)) {
            if (this.inlineResult.title != null) {
                try {
                    this.titleLayout = new StaticLayout(TextUtils.ellipsize(Emoji.replaceEmoji(this.inlineResult.title.replace('\n', ' '), Theme.chat_contextResult_titleTextPaint.getFontMetricsInt(), AndroidUtilities.dp(15.0f), false), Theme.chat_contextResult_titleTextPaint, (float) Math.min((int) Math.ceil((double) Theme.chat_contextResult_titleTextPaint.measureText(this.inlineResult.title)), dp), TruncateAt.END), Theme.chat_contextResult_titleTextPaint, AndroidUtilities.dp(4.0f) + dp, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                this.letterDrawable.setTitle(this.inlineResult.title);
            }
            if (this.inlineResult.description != null) {
                try {
                    this.descriptionLayout = ChatMessageCell.generateStaticLayout(Emoji.replaceEmoji(this.inlineResult.description, Theme.chat_contextResult_descriptionTextPaint.getFontMetricsInt(), AndroidUtilities.dp(13.0f), false), Theme.chat_contextResult_descriptionTextPaint, dp, dp, 0, 3);
                    if (this.descriptionLayout.getLineCount() > 0) {
                        this.linkY = (this.descriptionY + this.descriptionLayout.getLineBottom(this.descriptionLayout.getLineCount() - 1)) + AndroidUtilities.dp(1.0f);
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
            if (this.inlineResult.url != null) {
                try {
                    this.linkLayout = new StaticLayout(TextUtils.ellipsize(this.inlineResult.url.replace('\n', ' '), Theme.chat_contextResult_descriptionTextPaint, (float) Math.min((int) Math.ceil((double) Theme.chat_contextResult_descriptionTextPaint.measureText(this.inlineResult.url)), dp), TruncateAt.MIDDLE), Theme.chat_contextResult_descriptionTextPaint, dp, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                } catch (Throwable e22) {
                    FileLog.e(e22);
                }
            }
        }
        String str2 = null;
        if (this.documentAttach == null) {
            if (!(this.inlineResult == null || this.inlineResult.photo == null)) {
                this.currentPhotoObject = FileLoader.getClosestPhotoSizeWithSize(arrayList, AndroidUtilities.getPhotoSize(), true);
                PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(arrayList, 80);
                photoSize = closestPhotoSizeWithSize == this.currentPhotoObject ? null : closestPhotoSizeWithSize;
            }
            photoSize = null;
        } else if (MessageObject.isGifDocument(this.documentAttach)) {
            this.currentPhotoObject = this.documentAttach.thumb;
            photoSize = null;
        } else if (MessageObject.isStickerDocument(this.documentAttach)) {
            this.currentPhotoObject = this.documentAttach.thumb;
            str2 = "webp";
            photoSize = null;
        } else {
            if (!(this.documentAttachType == 5 || this.documentAttachType == 3)) {
                this.currentPhotoObject = this.documentAttach.thumb;
                photoSize = null;
            }
            photoSize = null;
        }
        if (this.inlineResult != null) {
            if (!(this.inlineResult.content_url == null || this.inlineResult.type == null)) {
                String str3;
                if (this.inlineResult.type.startsWith("gif")) {
                    if (this.documentAttachType != 2) {
                        str3 = this.inlineResult.content_url;
                        this.documentAttachType = 2;
                        str = str3;
                        if (str == null && this.inlineResult.thumb_url != null) {
                            str = this.inlineResult.thumb_url;
                        }
                    }
                } else if (this.inlineResult.type.equals("photo")) {
                    str3 = this.inlineResult.thumb_url;
                    str = str3 == null ? this.inlineResult.content_url : str3;
                    str = this.inlineResult.thumb_url;
                }
            }
            str = null;
            str = this.inlineResult.thumb_url;
        } else {
            str = null;
        }
        if (str == null && this.currentPhotoObject == null && photoSize == null && ((this.inlineResult.send_message instanceof TL_botInlineMessageMediaVenue) || (this.inlineResult.send_message instanceof TL_botInlineMessageMediaGeo))) {
            double d = this.inlineResult.send_message.geo.lat;
            double d2 = this.inlineResult.send_message.geo._long;
            str = String.format(Locale.US, "https://maps.googleapis.com/maps/api/staticmap?center=%f,%f&zoom=15&size=72x72&maptype=roadmap&scale=%d&markers=color:red|size:small|%f,%f&sensor=false", new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf(Math.min(2, (int) Math.ceil((double) AndroidUtilities.density))), Double.valueOf(d), Double.valueOf(d2)});
        }
        if (this.documentAttach != null) {
            for (i3 = 0; i3 < this.documentAttach.attributes.size(); i3++) {
                DocumentAttribute documentAttribute = (DocumentAttribute) this.documentAttach.attributes.get(i3);
                if ((documentAttribute instanceof TLRPC$TL_documentAttributeImageSize) || (documentAttribute instanceof TLRPC$TL_documentAttributeVideo)) {
                    i3 = documentAttribute.f10140w;
                    i4 = documentAttribute.f10139h;
                    break;
                }
            }
        }
        i4 = 0;
        i3 = 0;
        if (i3 == 0 || r2 == 0) {
            if (this.currentPhotoObject != null) {
                if (photoSize != null) {
                    photoSize.size = -1;
                }
                i3 = this.currentPhotoObject.f10147w;
                i4 = this.currentPhotoObject.f10146h;
            } else if (this.inlineResult != null) {
                i3 = this.inlineResult.f10135w;
                i4 = this.inlineResult.f10134h;
            }
        }
        if (i3 == 0 || r2 == 0) {
            i4 = AndroidUtilities.dp(80.0f);
            i3 = i4;
        }
        if (!(this.documentAttach == null && this.currentPhotoObject == null && str == null)) {
            String str4;
            String str5 = "52_52_b";
            if (this.mediaWebpage) {
                i4 = (int) (((float) i3) / (((float) i4) / ((float) AndroidUtilities.dp(80.0f))));
                if (this.documentAttachType == 2) {
                    str5 = String.format(Locale.US, "%d_%d_b", new Object[]{Integer.valueOf((int) (((float) i4) / AndroidUtilities.density)), Integer.valueOf(80)});
                    str4 = str5;
                } else {
                    str4 = String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf((int) (((float) i4) / AndroidUtilities.density)), Integer.valueOf(80)});
                    str5 = str4 + "_b";
                }
            } else {
                str4 = "52_52";
            }
            this.linkImageView.setAspectFit(this.documentAttachType == 6);
            if (this.documentAttachType == 2) {
                if (this.documentAttach != null) {
                    this.linkImageView.setImage(this.documentAttach, null, this.currentPhotoObject != null ? this.currentPhotoObject.location : null, str4, this.documentAttach.size, str2, 0);
                } else {
                    this.linkImageView.setImage(null, str, null, null, this.currentPhotoObject != null ? this.currentPhotoObject.location : null, str4, -1, str2, 1);
                }
            } else if (this.currentPhotoObject != null) {
                this.linkImageView.setImage(this.currentPhotoObject.location, str4, photoSize != null ? photoSize.location : null, str5, this.currentPhotoObject.size, str2, 0);
            } else {
                this.linkImageView.setImage(null, str, str4, null, photoSize != null ? photoSize.location : null, str5, -1, str2, 1);
            }
            this.drawLinkImageView = true;
        }
        if (this.mediaWebpage) {
            i4 = MeasureSpec.getSize(i2);
            if (i4 == 0) {
                i4 = AndroidUtilities.dp(100.0f);
            }
            setMeasuredDimension(size, i4);
            int dp2 = (size - AndroidUtilities.dp(24.0f)) / 2;
            i3 = (i4 - AndroidUtilities.dp(24.0f)) / 2;
            this.radialProgress.setProgressRect(dp2, i3, AndroidUtilities.dp(24.0f) + dp2, AndroidUtilities.dp(24.0f) + i3);
            this.linkImageView.setImageCoords(0, 0, size, i4);
            return;
        }
        i4 = 0;
        if (!(this.titleLayout == null || this.titleLayout.getLineCount() == 0)) {
            i4 = 0 + this.titleLayout.getLineBottom(this.titleLayout.getLineCount() - 1);
        }
        if (!(this.descriptionLayout == null || this.descriptionLayout.getLineCount() == 0)) {
            i4 += this.descriptionLayout.getLineBottom(this.descriptionLayout.getLineCount() - 1);
        }
        if (this.linkLayout != null && this.linkLayout.getLineCount() > 0) {
            i4 += this.linkLayout.getLineBottom(this.linkLayout.getLineCount() - 1);
        }
        setMeasuredDimension(MeasureSpec.getSize(i), (this.needDivider ? 1 : 0) + Math.max(AndroidUtilities.dp(68.0f), Math.max(AndroidUtilities.dp(52.0f), i4) + AndroidUtilities.dp(16.0f)));
        dp2 = AndroidUtilities.dp(52.0f);
        i4 = LocaleController.isRTL ? (MeasureSpec.getSize(i) - AndroidUtilities.dp(8.0f)) - dp2 : AndroidUtilities.dp(8.0f);
        this.letterDrawable.setBounds(i4, AndroidUtilities.dp(8.0f), i4 + dp2, AndroidUtilities.dp(60.0f));
        this.linkImageView.setImageCoords(i4, AndroidUtilities.dp(8.0f), dp2, dp2);
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            this.radialProgress.setProgressRect(AndroidUtilities.dp(4.0f) + i4, AndroidUtilities.dp(12.0f), i4 + AndroidUtilities.dp(48.0f), AndroidUtilities.dp(56.0f));
        }
    }

    public void onProgressDownload(String str, float f) {
        this.radialProgress.setProgress(f, true);
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            if (this.buttonState != 4) {
                updateButtonState(false);
            }
        } else if (this.buttonState != 1) {
            updateButtonState(false);
        }
    }

    public void onProgressUpload(String str, float f, boolean z) {
    }

    public void onSuccessDownload(String str) {
        this.radialProgress.setProgress(1.0f, true);
        updateButtonState(true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = true;
        if (this.mediaWebpage || this.delegate == null || this.inlineResult == null) {
            return super.onTouchEvent(motionEvent);
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        AndroidUtilities.dp(48.0f);
        if (this.documentAttachType == 3 || this.documentAttachType == 5) {
            boolean contains = this.letterDrawable.getBounds().contains(x, y);
            if (motionEvent.getAction() == 0) {
                if (contains) {
                    this.buttonPressed = true;
                    invalidate();
                    this.radialProgress.swapBackground(getDrawableForCurrentState());
                }
            } else if (this.buttonPressed) {
                if (motionEvent.getAction() == 1) {
                    this.buttonPressed = false;
                    playSoundEffect(0);
                    didPressedButton();
                    invalidate();
                } else if (motionEvent.getAction() == 3) {
                    this.buttonPressed = false;
                    invalidate();
                } else if (motionEvent.getAction() == 2 && !contains) {
                    this.buttonPressed = false;
                    invalidate();
                }
                this.radialProgress.swapBackground(getDrawableForCurrentState());
            }
            z = false;
        } else {
            if (!(this.inlineResult == null || this.inlineResult.content_url == null || this.inlineResult.content_url.length() <= 0)) {
                if (motionEvent.getAction() == 0) {
                    if (this.letterDrawable.getBounds().contains(x, y)) {
                        this.buttonPressed = true;
                    }
                } else if (this.buttonPressed) {
                    if (motionEvent.getAction() == 1) {
                        this.buttonPressed = false;
                        playSoundEffect(0);
                        this.delegate.didPressedImage(this);
                        z = false;
                    } else if (motionEvent.getAction() == 3) {
                        this.buttonPressed = false;
                        z = false;
                    } else if (motionEvent.getAction() == 2 && !this.letterDrawable.getBounds().contains(x, y)) {
                        this.buttonPressed = false;
                    }
                }
            }
            z = false;
        }
        return !z ? super.onTouchEvent(motionEvent) : z;
    }

    public void setDelegate(ContextLinkCellDelegate contextLinkCellDelegate) {
        this.delegate = contextLinkCellDelegate;
    }

    public void setGif(Document document, boolean z) {
        this.needDivider = z;
        this.needShadow = false;
        this.inlineResult = null;
        this.documentAttach = document;
        this.mediaWebpage = true;
        setAttachType();
        requestLayout();
        updateButtonState(false);
    }

    public void setLink(BotInlineResult botInlineResult, boolean z, boolean z2, boolean z3) {
        this.needDivider = z2;
        this.needShadow = z3;
        this.inlineResult = botInlineResult;
        if (this.inlineResult == null || this.inlineResult.document == null) {
            this.documentAttach = null;
        } else {
            this.documentAttach = this.inlineResult.document;
        }
        this.mediaWebpage = z;
        setAttachType();
        requestLayout();
        updateButtonState(false);
    }

    public void setScaled(boolean z) {
        this.scaled = z;
        this.lastUpdateTime = System.currentTimeMillis();
        invalidate();
    }

    public boolean showingBitmap() {
        return this.linkImageView.getBitmap() != null;
    }

    public void updateButtonState(boolean z) {
        String str = null;
        File file = null;
        if (this.documentAttachType == 5 || this.documentAttachType == 3) {
            if (this.documentAttach != null) {
                str = FileLoader.getAttachFileName(this.documentAttach);
                file = FileLoader.getPathToAttach(this.documentAttach);
            } else {
                String str2 = this.inlineResult.content_url;
                file = new File(FileLoader.getInstance().getDirectory(4), Utilities.MD5(this.inlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(this.inlineResult.content_url, this.documentAttachType == 5 ? "mp3" : "ogg"));
                str = str2;
            }
        } else if (this.mediaWebpage) {
            if (this.inlineResult != null) {
                if (this.inlineResult.document instanceof TLRPC$TL_document) {
                    str = FileLoader.getAttachFileName(this.inlineResult.document);
                    file = FileLoader.getPathToAttach(this.inlineResult.document);
                } else if (this.inlineResult.photo instanceof TLRPC$TL_photo) {
                    this.currentPhotoObject = FileLoader.getClosestPhotoSizeWithSize(this.inlineResult.photo.sizes, AndroidUtilities.getPhotoSize(), true);
                    str = FileLoader.getAttachFileName(this.currentPhotoObject);
                    file = FileLoader.getPathToAttach(this.currentPhotoObject);
                } else if (this.inlineResult.content_url != null) {
                    str = Utilities.MD5(this.inlineResult.content_url) + "." + ImageLoader.getHttpUrlExtension(this.inlineResult.content_url, "jpg");
                    file = new File(FileLoader.getInstance().getDirectory(4), str);
                } else if (this.inlineResult.thumb_url != null) {
                    str = Utilities.MD5(this.inlineResult.thumb_url) + "." + ImageLoader.getHttpUrlExtension(this.inlineResult.thumb_url, "jpg");
                    file = new File(FileLoader.getInstance().getDirectory(4), str);
                }
            } else if (this.documentAttach != null) {
                str = FileLoader.getAttachFileName(this.documentAttach);
                file = FileLoader.getPathToAttach(this.documentAttach);
            }
        }
        if (TextUtils.isEmpty(str)) {
            this.radialProgress.setBackground(null, false, false);
            return;
        }
        if (file.exists() && file.length() == 0) {
            file.delete();
        }
        if (file.exists()) {
            MediaController.getInstance().removeLoadingFileObserver(this);
            if (this.documentAttachType == 5 || this.documentAttachType == 3) {
                boolean isPlayingMessage = MediaController.getInstance().isPlayingMessage(this.currentMessageObject);
                if (!isPlayingMessage || (isPlayingMessage && MediaController.getInstance().isMessagePaused())) {
                    this.buttonState = 0;
                } else {
                    this.buttonState = 1;
                }
            } else {
                this.buttonState = -1;
            }
            this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            invalidate();
            return;
        }
        MediaController.getInstance().addLoadingFileObserver(str, this);
        Float fileProgress;
        if (this.documentAttachType == 5 || this.documentAttachType == 3) {
            if (this.documentAttach != null ? FileLoader.getInstance().isLoadingFile(str) : ImageLoader.getInstance().isLoadingHttpFile(str)) {
                this.buttonState = 4;
                fileProgress = ImageLoader.getInstance().getFileProgress(str);
                if (fileProgress != null) {
                    this.radialProgress.setProgress(fileProgress.floatValue(), z);
                } else {
                    this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                }
                this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
            } else {
                this.buttonState = 2;
                this.radialProgress.setProgress(BitmapDescriptorFactory.HUE_RED, z);
                this.radialProgress.setBackground(getDrawableForCurrentState(), false, z);
            }
        } else {
            this.buttonState = 1;
            fileProgress = ImageLoader.getInstance().getFileProgress(str);
            this.radialProgress.setProgress(fileProgress != null ? fileProgress.floatValue() : BitmapDescriptorFactory.HUE_RED, false);
            this.radialProgress.setBackground(getDrawableForCurrentState(), true, z);
        }
        invalidate();
    }
}
