package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.Date;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.ImageReceiver.ImageReceiverDelegate;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MediaController;
import org.telegram.messenger.MediaController.FileDownloadProgressListener;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLRPC$TL_documentAttributeAudio;
import org.telegram.tgnet.TLRPC$TL_photoSizeEmpty;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LineProgressView;

public class SharedDocumentCell extends FrameLayout implements FileDownloadProgressListener {
    private int TAG = MediaController.getInstance().generateObserverTag();
    private CheckBox checkBox;
    private TextView dateTextView;
    private TextView extTextView;
    private int[] icons = new int[]{R.drawable.media_doc_blue, R.drawable.media_doc_green, R.drawable.media_doc_red, R.drawable.media_doc_yellow};
    private boolean loaded;
    private boolean loading;
    private MessageObject message;
    private TextView nameTextView;
    private boolean needDivider;
    private ImageView placeholderImageView;
    private LineProgressView progressView;
    private ImageView statusImageView;
    private BackupImageView thumbImageView;

    /* renamed from: org.telegram.ui.Cells.SharedDocumentCell$1 */
    class C40251 implements ImageReceiverDelegate {
        C40251() {
        }

        public void didSetImage(ImageReceiver imageReceiver, boolean z, boolean z2) {
            int i = 4;
            SharedDocumentCell.this.extTextView.setVisibility(z ? 4 : 0);
            ImageView access$100 = SharedDocumentCell.this.placeholderImageView;
            if (!z) {
                i = 0;
            }
            access$100.setVisibility(i);
        }
    }

    public SharedDocumentCell(Context context) {
        super(context);
        this.placeholderImageView = new ImageView(context);
        addView(this.placeholderImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.extTextView = new TextView(context);
        this.extTextView.setTextColor(Theme.getColor(Theme.key_files_iconText));
        this.extTextView.setTextSize(1, 14.0f);
        this.extTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.extTextView.setLines(1);
        this.extTextView.setMaxLines(1);
        this.extTextView.setSingleLine(true);
        this.extTextView.setGravity(17);
        this.extTextView.setEllipsize(TruncateAt.END);
        addView(this.extTextView, LayoutHelper.createFrame(32, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 16.0f, 22.0f, LocaleController.isRTL ? 16.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.thumbImageView = new BackupImageView(context);
        addView(this.thumbImageView, LayoutHelper.createFrame(40, 40.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 12.0f, 8.0f, LocaleController.isRTL ? 12.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.thumbImageView.getImageReceiver().setDelegate(new C40251());
        this.nameTextView = new TextView(context);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.nameTextView.setLines(1);
        this.nameTextView.setMaxLines(1);
        this.nameTextView.setSingleLine(true);
        this.nameTextView.setEllipsize(TruncateAt.END);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 8.0f : 72.0f, 5.0f, LocaleController.isRTL ? 72.0f : 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.statusImageView = new ImageView(context);
        this.statusImageView.setVisibility(4);
        this.statusImageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_sharedMedia_startStopLoadIcon), Mode.MULTIPLY));
        addView(this.statusImageView, LayoutHelper.createFrame(-2, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 8.0f : 72.0f, 35.0f, LocaleController.isRTL ? 72.0f : 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.dateTextView = new TextView(context);
        this.dateTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText3));
        this.dateTextView.setTextSize(1, 14.0f);
        this.dateTextView.setLines(1);
        this.dateTextView.setMaxLines(1);
        this.dateTextView.setSingleLine(true);
        this.dateTextView.setEllipsize(TruncateAt.END);
        this.dateTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.dateTextView, LayoutHelper.createFrame(-1, -2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 8.0f : 72.0f, 30.0f, LocaleController.isRTL ? 72.0f : 8.0f, BitmapDescriptorFactory.HUE_RED));
        this.progressView = new LineProgressView(context);
        this.progressView.setProgressColor(Theme.getColor(Theme.key_sharedMedia_startStopLoadIcon));
        addView(this.progressView, LayoutHelper.createFrame(-1, 2.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 72.0f, 54.0f, LocaleController.isRTL ? 72.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setVisibility(4);
        this.checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
        addView(this.checkBox, LayoutHelper.createFrame(22, 22.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 34.0f, 30.0f, LocaleController.isRTL ? 34.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
    }

    private int getThumbForNameOrMime(String str, String str2) {
        if (str == null || str.length() == 0) {
            return this.icons[0];
        }
        int i = (str.contains(".doc") || str.contains(".txt") || str.contains(".psd")) ? 0 : (str.contains(".xls") || str.contains(".csv")) ? 1 : (str.contains(".pdf") || str.contains(".ppt") || str.contains(".key")) ? 2 : (str.contains(".zip") || str.contains(".rar") || str.contains(".ai") || str.contains(".mp3") || str.contains(".mov") || str.contains(".avi")) ? 3 : -1;
        if (i == -1) {
            i = str.lastIndexOf(46);
            String substring = i == -1 ? TtmlNode.ANONYMOUS_REGION_ID : str.substring(i + 1);
            i = substring.length() != 0 ? substring.charAt(0) % this.icons.length : str.charAt(0) % this.icons.length;
        }
        return this.icons[i];
    }

    public MessageObject getMessage() {
        return this.message;
    }

    public int getObserverTag() {
        return this.TAG;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public boolean isLoading() {
        return this.loading;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.progressView.getVisibility() == 0) {
            updateFileExistIcon();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        MediaController.getInstance().removeLoadingFileObserver(this);
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) AndroidUtilities.dp(72.0f), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
    }

    public void onFailedDownload(String str) {
        updateFileExistIcon();
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(56.0f), 1073741824));
    }

    public void onProgressDownload(String str, float f) {
        if (this.progressView.getVisibility() != 0) {
            updateFileExistIcon();
        }
        this.progressView.setProgress(f, true);
    }

    public void onProgressUpload(String str, float f, boolean z) {
    }

    public void onSuccessDownload(String str) {
        this.progressView.setProgress(1.0f, true);
        updateFileExistIcon();
    }

    public void setChecked(boolean z, boolean z2) {
        if (this.checkBox.getVisibility() != 0) {
            this.checkBox.setVisibility(0);
        }
        this.checkBox.setChecked(z, z2);
    }

    public void setDocument(MessageObject messageObject, boolean z) {
        this.needDivider = z;
        this.message = messageObject;
        this.loaded = false;
        this.loading = false;
        if (messageObject == null || messageObject.getDocument() == null) {
            this.nameTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.extTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.dateTextView.setText(TtmlNode.ANONYMOUS_REGION_ID);
            this.placeholderImageView.setVisibility(0);
            this.extTextView.setVisibility(0);
            this.thumbImageView.setVisibility(4);
            this.thumbImageView.setImageBitmap(null);
        } else {
            CharSequence charSequence;
            String str;
            if (messageObject.isMusic()) {
                Document document = messageObject.type == 0 ? messageObject.messageOwner.media.webpage.document : messageObject.messageOwner.media.document;
                int i = 0;
                charSequence = null;
                while (i < document.attributes.size()) {
                    DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
                    if (!(documentAttribute instanceof TLRPC$TL_documentAttributeAudio) || ((documentAttribute.performer == null || documentAttribute.performer.length() == 0) && (documentAttribute.title == null || documentAttribute.title.length() == 0))) {
                        CharSequence charSequence2 = charSequence;
                    } else {
                        str = messageObject.getMusicAuthor() + " - " + messageObject.getMusicTitle();
                    }
                    i++;
                    Object obj = str;
                }
            } else {
                charSequence = null;
            }
            str = FileLoader.getDocumentFileName(messageObject.getDocument());
            if (charSequence == null) {
                charSequence = str;
            }
            this.nameTextView.setText(charSequence);
            this.placeholderImageView.setVisibility(0);
            this.extTextView.setVisibility(0);
            this.placeholderImageView.setImageResource(getThumbForNameOrMime(str, messageObject.getDocument().mime_type));
            TextView textView = this.extTextView;
            int lastIndexOf = str.lastIndexOf(46);
            textView.setText(lastIndexOf == -1 ? TtmlNode.ANONYMOUS_REGION_ID : str.substring(lastIndexOf + 1).toLowerCase());
            if ((messageObject.getDocument().thumb instanceof TLRPC$TL_photoSizeEmpty) || messageObject.getDocument().thumb == null) {
                this.thumbImageView.setVisibility(4);
                this.thumbImageView.setImageBitmap(null);
            } else {
                this.thumbImageView.setVisibility(0);
                this.thumbImageView.setImage(messageObject.getDocument().thumb.location, "40_40", (Drawable) null);
            }
            long j = ((long) messageObject.messageOwner.date) * 1000;
            textView = this.dateTextView;
            Object[] objArr = new Object[2];
            objArr[0] = AndroidUtilities.formatFileSize((long) messageObject.getDocument().size);
            objArr[1] = LocaleController.formatString("formatDateAtTime", R.string.formatDateAtTime, new Object[]{LocaleController.getInstance().formatterYear.format(new Date(j)), LocaleController.getInstance().formatterDay.format(new Date(j))});
            textView.setText(String.format("%s, %s", objArr));
        }
        setWillNotDraw(!this.needDivider);
        this.progressView.setProgress(BitmapDescriptorFactory.HUE_RED, false);
        updateFileExistIcon();
    }

    public void setTextAndValueAndTypeAndThumb(String str, String str2, String str3, String str4, int i) {
        this.nameTextView.setText(str);
        this.dateTextView.setText(str2);
        if (str3 != null) {
            this.extTextView.setVisibility(0);
            this.extTextView.setText(str3);
        } else {
            this.extTextView.setVisibility(4);
        }
        if (i == 0) {
            this.placeholderImageView.setImageResource(getThumbForNameOrMime(str, str3));
            this.placeholderImageView.setVisibility(0);
        } else {
            this.placeholderImageView.setVisibility(4);
        }
        if (str4 == null && i == 0) {
            this.thumbImageView.setImageBitmap(null);
            this.thumbImageView.setVisibility(4);
            return;
        }
        if (str4 != null) {
            this.thumbImageView.setImage(str4, "40_40", null);
        } else {
            Drawable createCircleDrawableWithIcon = Theme.createCircleDrawableWithIcon(AndroidUtilities.dp(40.0f), i);
            Theme.setCombinedDrawableColor(createCircleDrawableWithIcon, Theme.getColor(Theme.key_files_folderIconBackground), false);
            Theme.setCombinedDrawableColor(createCircleDrawableWithIcon, Theme.getColor(Theme.key_files_folderIcon), true);
            this.thumbImageView.setImageDrawable(createCircleDrawableWithIcon);
        }
        this.thumbImageView.setVisibility(0);
    }

    public void updateFileExistIcon() {
        if (this.message == null || this.message.messageOwner.media == null) {
            this.loading = false;
            this.loaded = true;
            this.progressView.setVisibility(4);
            this.progressView.setProgress(BitmapDescriptorFactory.HUE_RED, false);
            this.statusImageView.setVisibility(4);
            this.dateTextView.setPadding(0, 0, 0, 0);
            MediaController.getInstance().removeLoadingFileObserver(this);
            return;
        }
        String str = null;
        if ((this.message.messageOwner.attachPath == null || this.message.messageOwner.attachPath.length() == 0 || !new File(this.message.messageOwner.attachPath).exists()) && !FileLoader.getPathToMessage(this.message.messageOwner).exists()) {
            str = FileLoader.getAttachFileName(this.message.getDocument());
        }
        this.loaded = false;
        if (str == null) {
            this.statusImageView.setVisibility(4);
            this.progressView.setVisibility(4);
            this.dateTextView.setPadding(0, 0, 0, 0);
            this.loading = false;
            this.loaded = true;
            MediaController.getInstance().removeLoadingFileObserver(this);
            return;
        }
        MediaController.getInstance().addLoadingFileObserver(str, this);
        this.loading = FileLoader.getInstance().isLoadingFile(str);
        this.statusImageView.setVisibility(0);
        this.statusImageView.setImageResource(this.loading ? R.drawable.media_doc_pause : R.drawable.media_doc_load);
        this.dateTextView.setPadding(LocaleController.isRTL ? 0 : AndroidUtilities.dp(14.0f), 0, LocaleController.isRTL ? AndroidUtilities.dp(14.0f) : 0, 0);
        if (this.loading) {
            this.progressView.setVisibility(0);
            Float fileProgress = ImageLoader.getInstance().getFileProgress(str);
            if (fileProgress == null) {
                fileProgress = Float.valueOf(BitmapDescriptorFactory.HUE_RED);
            }
            this.progressView.setProgress(fileProgress.floatValue(), false);
            return;
        }
        this.progressView.setVisibility(4);
    }
}
