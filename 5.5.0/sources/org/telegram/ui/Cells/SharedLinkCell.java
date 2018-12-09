package org.telegram.ui.Cells;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_messageEntityEmail;
import org.telegram.tgnet.TLRPC$TL_messageEntityTextUrl;
import org.telegram.tgnet.TLRPC$TL_messageEntityUrl;
import org.telegram.tgnet.TLRPC$TL_messageMediaWebPage;
import org.telegram.tgnet.TLRPC$TL_webPage;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC.MessageEntity;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.CheckBox;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.LetterDrawable;
import org.telegram.ui.Components.LinkPath;

public class SharedLinkCell extends FrameLayout {
    private CheckBox checkBox;
    private SharedLinkCellDelegate delegate;
    private int description2Y = AndroidUtilities.dp(27.0f);
    private StaticLayout descriptionLayout;
    private StaticLayout descriptionLayout2;
    private TextPaint descriptionTextPaint;
    private int descriptionY = AndroidUtilities.dp(27.0f);
    private boolean drawLinkImageView;
    private LetterDrawable letterDrawable;
    private ImageReceiver linkImageView;
    private ArrayList<StaticLayout> linkLayout = new ArrayList();
    private boolean linkPreviewPressed;
    private int linkY;
    ArrayList<String> links = new ArrayList();
    private MessageObject message;
    private boolean needDivider;
    private int pressedLink;
    private StaticLayout titleLayout;
    private TextPaint titleTextPaint = new TextPaint(1);
    private int titleY = AndroidUtilities.dp(7.0f);
    private LinkPath urlPath = new LinkPath();

    public interface SharedLinkCellDelegate {
        boolean canPerformActions();

        void needOpenWebView(TLRPC$WebPage tLRPC$WebPage);
    }

    public SharedLinkCell(Context context) {
        super(context);
        this.titleTextPaint.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.descriptionTextPaint = new TextPaint(1);
        this.titleTextPaint.setTextSize((float) AndroidUtilities.dp(16.0f));
        this.descriptionTextPaint.setTextSize((float) AndroidUtilities.dp(16.0f));
        setWillNotDraw(false);
        this.linkImageView = new ImageReceiver(this);
        this.letterDrawable = new LetterDrawable();
        this.checkBox = new CheckBox(context, R.drawable.round_check2);
        this.checkBox.setVisibility(4);
        this.checkBox.setColor(Theme.getColor(Theme.key_checkbox), Theme.getColor(Theme.key_checkboxCheck));
        addView(this.checkBox, LayoutHelper.createFrame(22, 22.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 44.0f, 44.0f, LocaleController.isRTL ? 44.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
    }

    public String getLink(int i) {
        return (i < 0 || i >= this.links.size()) ? null : (String) this.links.get(i);
    }

    public MessageObject getMessage() {
        return this.message;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.drawLinkImageView) {
            this.linkImageView.onAttachedToWindow();
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.drawLinkImageView) {
            this.linkImageView.onDetachedFromWindow();
        }
    }

    protected void onDraw(Canvas canvas) {
        int i = 0;
        if (this.titleLayout != null) {
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.titleY);
            this.titleLayout.draw(canvas);
            canvas.restore();
        }
        if (this.descriptionLayout != null) {
            this.descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.descriptionY);
            this.descriptionLayout.draw(canvas);
            canvas.restore();
        }
        if (this.descriptionLayout2 != null) {
            this.descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            canvas.save();
            canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) this.description2Y);
            this.descriptionLayout2.draw(canvas);
            canvas.restore();
        }
        if (!this.linkLayout.isEmpty()) {
            this.descriptionTextPaint.setColor(Theme.getColor(Theme.key_windowBackgroundWhiteLinkText));
            int i2 = 0;
            while (i < this.linkLayout.size()) {
                StaticLayout staticLayout = (StaticLayout) this.linkLayout.get(i);
                if (staticLayout.getLineCount() > 0) {
                    canvas.save();
                    canvas.translate((float) AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline), (float) (this.linkY + i2));
                    if (this.pressedLink == i) {
                        canvas.drawPath(this.urlPath, Theme.linkSelectionPaint);
                    }
                    staticLayout.draw(canvas);
                    canvas.restore();
                    i2 += staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
                }
                i++;
            }
        }
        this.letterDrawable.draw(canvas);
        if (this.drawLinkImageView) {
            this.linkImageView.draw(canvas);
        }
        if (!this.needDivider) {
            return;
        }
        if (LocaleController.isRTL) {
            canvas.drawLine(BitmapDescriptorFactory.HUE_RED, (float) (getMeasuredHeight() - 1), (float) (getMeasuredWidth() - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
        } else {
            canvas.drawLine((float) AndroidUtilities.dp((float) AndroidUtilities.leftBaseline), (float) (getMeasuredHeight() - 1), (float) getMeasuredWidth(), (float) (getMeasuredHeight() - 1), Theme.dividerPaint);
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        Object obj;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        int lastIndexOf;
        Throwable th;
        this.drawLinkImageView = false;
        this.descriptionLayout = null;
        this.titleLayout = null;
        this.descriptionLayout2 = null;
        this.description2Y = this.descriptionY;
        this.linkLayout.clear();
        this.links.clear();
        int size = (MeasureSpec.getSize(i) - AndroidUtilities.dp((float) AndroidUtilities.leftBaseline)) - AndroidUtilities.dp(8.0f);
        Object obj2 = null;
        if ((this.message.messageOwner.media instanceof TLRPC$TL_messageMediaWebPage) && (this.message.messageOwner.media.webpage instanceof TLRPC$TL_webPage)) {
            TLRPC$WebPage tLRPC$WebPage = this.message.messageOwner.media.webpage;
            if (this.message.photoThumbs == null && tLRPC$WebPage.photo != null) {
                this.message.generateThumbs(true);
            }
            Object obj3 = (tLRPC$WebPage.photo == null || this.message.photoThumbs == null) ? null : 1;
            String str6 = tLRPC$WebPage.title;
            if (str6 == null) {
                str6 = tLRPC$WebPage.site_name;
            }
            obj = obj3;
            str = tLRPC$WebPage.description;
            String str7 = tLRPC$WebPage.url;
            str2 = str6;
            obj2 = str7;
        } else {
            obj = null;
            str = null;
            str2 = null;
        }
        if (this.message == null || this.message.messageOwner.entities.isEmpty()) {
            str3 = null;
            str4 = str;
            str5 = str2;
        } else {
            int i3 = 0;
            String str8 = null;
            String str9 = str2;
            str2 = str;
            while (i3 < this.message.messageOwner.entities.size()) {
                MessageEntity messageEntity = (MessageEntity) this.message.messageOwner.entities.get(i3);
                if (messageEntity.length <= 0 || messageEntity.offset < 0) {
                    str = str8;
                    str8 = str9;
                } else if (messageEntity.offset >= this.message.messageOwner.message.length()) {
                    str = str8;
                    str8 = str9;
                } else {
                    if (messageEntity.offset + messageEntity.length > this.message.messageOwner.message.length()) {
                        messageEntity.length = this.message.messageOwner.message.length() - messageEntity.offset;
                    }
                    if (!(i3 != 0 || obj2 == null || (messageEntity.offset == 0 && messageEntity.length == this.message.messageOwner.message.length()))) {
                        if (this.message.messageOwner.entities.size() != 1) {
                            str8 = this.message.messageOwner.message;
                        } else if (str2 == null) {
                            str8 = this.message.messageOwner.message;
                        }
                    }
                    String str10 = null;
                    try {
                        if ((messageEntity instanceof TLRPC$TL_messageEntityTextUrl) || (messageEntity instanceof TLRPC$TL_messageEntityUrl)) {
                            str10 = messageEntity instanceof TLRPC$TL_messageEntityUrl ? this.message.messageOwner.message.substring(messageEntity.offset, messageEntity.offset + messageEntity.length) : messageEntity.url;
                            if (str9 == null || str9.length() == 0) {
                                try {
                                    str9 = Uri.parse(str10).getHost();
                                    if (str9 == null) {
                                        str9 = str10;
                                    }
                                    if (str9 != null) {
                                        lastIndexOf = str9.lastIndexOf(46);
                                        if (lastIndexOf >= 0) {
                                            str9 = str9.substring(0, lastIndexOf);
                                            lastIndexOf = str9.lastIndexOf(46);
                                            if (lastIndexOf >= 0) {
                                                str9 = str9.substring(lastIndexOf + 1);
                                            }
                                            str9 = str9.substring(0, 1).toUpperCase() + str9.substring(1);
                                        }
                                    }
                                    str = (messageEntity.offset == 0 && messageEntity.length == this.message.messageOwner.message.length()) ? str2 : this.message.messageOwner.message;
                                    str2 = str;
                                    str = str9;
                                } catch (Throwable e) {
                                    th = e;
                                    str = str10;
                                    FileLog.e(th);
                                    str7 = str8;
                                    str8 = str;
                                    str = str7;
                                    i3++;
                                    str9 = str8;
                                    str8 = str;
                                }
                            }
                            str = str9;
                        } else {
                            if ((messageEntity instanceof TLRPC$TL_messageEntityEmail) && (str9 == null || str9.length() == 0)) {
                                str10 = "mailto:" + this.message.messageOwner.message.substring(messageEntity.offset, messageEntity.offset + messageEntity.length);
                                str9 = this.message.messageOwner.message.substring(messageEntity.offset, messageEntity.offset + messageEntity.length);
                                if (!(messageEntity.offset == 0 && messageEntity.length == this.message.messageOwner.message.length())) {
                                    str2 = this.message.messageOwner.message;
                                    str = str9;
                                }
                            }
                            str = str9;
                        }
                        if (str10 != null) {
                            try {
                                if (str10.toLowerCase().indexOf("http") == 0 || str10.toLowerCase().indexOf("mailto") == 0) {
                                    this.links.add(str10);
                                } else {
                                    this.links.add("http://" + str10);
                                }
                            } catch (Exception e2) {
                                th = e2;
                                FileLog.e(th);
                                str7 = str8;
                                str8 = str;
                                str = str7;
                                i3++;
                                str9 = str8;
                                str8 = str;
                            }
                        }
                        str7 = str8;
                        str8 = str;
                        str = str7;
                    } catch (Throwable e3) {
                        Throwable th2 = e3;
                        str = str9;
                        th = th2;
                        FileLog.e(th);
                        str7 = str8;
                        str8 = str;
                        str = str7;
                        i3++;
                        str9 = str8;
                        str8 = str;
                    }
                }
                i3++;
                str9 = str8;
                str8 = str;
            }
            str3 = str8;
            str4 = str2;
            str5 = str9;
        }
        if (obj2 != null && this.links.isEmpty()) {
            this.links.add(obj2);
        }
        if (str5 != null) {
            try {
                this.titleLayout = new StaticLayout(TextUtils.ellipsize(str5.replace('\n', ' '), this.titleTextPaint, (float) Math.min((int) Math.ceil((double) this.titleTextPaint.measureText(str5)), size), TruncateAt.END), this.titleTextPaint, size, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
            } catch (Throwable e32) {
                FileLog.e(e32);
            }
            this.letterDrawable.setTitle(str5);
        }
        if (str4 != null) {
            try {
                this.descriptionLayout = ChatMessageCell.generateStaticLayout(str4, this.descriptionTextPaint, size, size, 0, 3);
                if (this.descriptionLayout.getLineCount() > 0) {
                    this.description2Y = (this.descriptionY + this.descriptionLayout.getLineBottom(this.descriptionLayout.getLineCount() - 1)) + AndroidUtilities.dp(1.0f);
                }
            } catch (Throwable e322) {
                FileLog.e(e322);
            }
        }
        if (str3 != null) {
            try {
                this.descriptionLayout2 = ChatMessageCell.generateStaticLayout(str3, this.descriptionTextPaint, size, size, 0, 3);
                this.descriptionLayout2.getLineBottom(this.descriptionLayout2.getLineCount() - 1);
                if (this.descriptionLayout != null) {
                    this.description2Y += AndroidUtilities.dp(10.0f);
                }
            } catch (Throwable e3222) {
                FileLog.e(e3222);
            }
        }
        if (!this.links.isEmpty()) {
            for (lastIndexOf = 0; lastIndexOf < this.links.size(); lastIndexOf++) {
                try {
                    str = (String) this.links.get(lastIndexOf);
                    StaticLayout staticLayout = new StaticLayout(TextUtils.ellipsize(str.replace('\n', ' '), this.descriptionTextPaint, (float) Math.min((int) Math.ceil((double) this.descriptionTextPaint.measureText(str)), size), TruncateAt.MIDDLE), this.descriptionTextPaint, size, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    this.linkY = this.description2Y;
                    if (!(this.descriptionLayout2 == null || this.descriptionLayout2.getLineCount() == 0)) {
                        this.linkY += this.descriptionLayout2.getLineBottom(this.descriptionLayout2.getLineCount() - 1) + AndroidUtilities.dp(1.0f);
                    }
                    this.linkLayout.add(staticLayout);
                } catch (Throwable e32222) {
                    FileLog.e(e32222);
                }
            }
        }
        int dp = AndroidUtilities.dp(52.0f);
        int size2 = LocaleController.isRTL ? (MeasureSpec.getSize(i) - AndroidUtilities.dp(10.0f)) - dp : AndroidUtilities.dp(10.0f);
        this.letterDrawable.setBounds(size2, AndroidUtilities.dp(10.0f), size2 + dp, AndroidUtilities.dp(62.0f));
        if (obj != null) {
            PhotoSize photoSize;
            TLObject closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.message.photoThumbs, dp, true);
            TLObject closestPhotoSizeWithSize2 = FileLoader.getClosestPhotoSizeWithSize(this.message.photoThumbs, 80);
            if (closestPhotoSizeWithSize2 == closestPhotoSizeWithSize) {
                photoSize = null;
            } else {
                TLObject tLObject = closestPhotoSizeWithSize2;
            }
            closestPhotoSizeWithSize.size = -1;
            if (photoSize != null) {
                photoSize.size = -1;
            }
            this.linkImageView.setImageCoords(size2, AndroidUtilities.dp(10.0f), dp, dp);
            FileLoader.getAttachFileName(closestPhotoSizeWithSize);
            this.linkImageView.setImage(closestPhotoSizeWithSize.location, String.format(Locale.US, "%d_%d", new Object[]{Integer.valueOf(dp), Integer.valueOf(dp)}), photoSize != null ? photoSize.location : null, String.format(Locale.US, "%d_%d_b", new Object[]{Integer.valueOf(dp), Integer.valueOf(dp)}), 0, null, 0);
            this.drawLinkImageView = true;
        }
        size2 = 0;
        if (!(this.titleLayout == null || this.titleLayout.getLineCount() == 0)) {
            size2 = 0 + this.titleLayout.getLineBottom(this.titleLayout.getLineCount() - 1);
        }
        if (!(this.descriptionLayout == null || this.descriptionLayout.getLineCount() == 0)) {
            size2 += this.descriptionLayout.getLineBottom(this.descriptionLayout.getLineCount() - 1);
        }
        if (!(this.descriptionLayout2 == null || this.descriptionLayout2.getLineCount() == 0)) {
            size2 += this.descriptionLayout2.getLineBottom(this.descriptionLayout2.getLineCount() - 1);
            if (this.descriptionLayout != null) {
                size2 += AndroidUtilities.dp(10.0f);
            }
        }
        int i4 = size2;
        for (int i5 = 0; i5 < this.linkLayout.size(); i5++) {
            staticLayout = (StaticLayout) this.linkLayout.get(i5);
            if (staticLayout.getLineCount() > 0) {
                i4 += staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
            }
        }
        if (obj != null) {
            i4 = Math.max(AndroidUtilities.dp(48.0f), i4);
        }
        this.checkBox.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(22.0f), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(22.0f), 1073741824));
        setMeasuredDimension(MeasureSpec.getSize(i), (this.needDivider ? 1 : 0) + Math.max(AndroidUtilities.dp(72.0f), i4 + AndroidUtilities.dp(16.0f)));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (this.message == null || this.linkLayout.isEmpty() || this.delegate == null || !this.delegate.canPerformActions()) {
            resetPressedLink();
            z = false;
        } else if (motionEvent.getAction() == 0 || (this.linkPreviewPressed && motionEvent.getAction() == 1)) {
            boolean z2;
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            int i = 0;
            for (int i2 = 0; i2 < this.linkLayout.size(); i2++) {
                StaticLayout staticLayout = (StaticLayout) this.linkLayout.get(i2);
                if (staticLayout.getLineCount() > 0) {
                    int lineBottom = staticLayout.getLineBottom(staticLayout.getLineCount() - 1);
                    int dp = AndroidUtilities.dp(LocaleController.isRTL ? 8.0f : (float) AndroidUtilities.leftBaseline);
                    if (((float) x) < ((float) dp) + staticLayout.getLineLeft(0) || ((float) x) > ((float) dp) + staticLayout.getLineWidth(0) || y < this.linkY + i || y > (this.linkY + i) + lineBottom) {
                        i += lineBottom;
                    } else {
                        if (motionEvent.getAction() == 0) {
                            resetPressedLink();
                            this.pressedLink = i2;
                            this.linkPreviewPressed = true;
                            try {
                                this.urlPath.setCurrentLayout(staticLayout, 0, BitmapDescriptorFactory.HUE_RED);
                                staticLayout.getSelectionPath(0, staticLayout.getText().length(), this.urlPath);
                            } catch (Throwable e) {
                                FileLog.e(e);
                            }
                            z2 = true;
                            z = true;
                        } else if (this.linkPreviewPressed) {
                            try {
                                TLRPC$WebPage tLRPC$WebPage = (this.pressedLink != 0 || this.message.messageOwner.media == null) ? null : this.message.messageOwner.media.webpage;
                                if (tLRPC$WebPage == null || tLRPC$WebPage.embed_url == null || tLRPC$WebPage.embed_url.length() == 0) {
                                    Browser.openUrl(getContext(), (String) this.links.get(this.pressedLink));
                                    resetPressedLink();
                                    z2 = true;
                                    z = true;
                                } else {
                                    this.delegate.needOpenWebView(tLRPC$WebPage);
                                    resetPressedLink();
                                    z2 = true;
                                    z = true;
                                }
                            } catch (Throwable e2) {
                                FileLog.e(e2);
                            }
                        } else {
                            z2 = true;
                            z = false;
                        }
                        if (!z2) {
                            resetPressedLink();
                        }
                    }
                }
            }
            z2 = false;
            z = false;
            if (z2) {
                resetPressedLink();
            }
        } else {
            if (motionEvent.getAction() == 3) {
                resetPressedLink();
                z = false;
            }
            z = false;
        }
        return z || super.onTouchEvent(motionEvent);
    }

    protected void resetPressedLink() {
        this.pressedLink = -1;
        this.linkPreviewPressed = false;
        invalidate();
    }

    public void setChecked(boolean z, boolean z2) {
        if (this.checkBox.getVisibility() != 0) {
            this.checkBox.setVisibility(0);
        }
        this.checkBox.setChecked(z, z2);
    }

    public void setDelegate(SharedLinkCellDelegate sharedLinkCellDelegate) {
        this.delegate = sharedLinkCellDelegate;
    }

    public void setLink(MessageObject messageObject, boolean z) {
        this.needDivider = z;
        resetPressedLink();
        this.message = messageObject;
        requestLayout();
    }
}
