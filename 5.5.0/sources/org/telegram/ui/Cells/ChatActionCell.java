package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.text.Spannable;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.FileLoader;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_messageActionUserUpdatedPhoto;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC.KeyboardButton;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.PhotoViewer;

public class ChatActionCell extends BaseCell {
    private AvatarDrawable avatarDrawable;
    private MessageObject currentMessageObject;
    private int customDate;
    private CharSequence customText;
    private ChatActionCellDelegate delegate;
    private boolean hasReplyMessage;
    private boolean imagePressed = false;
    private ImageReceiver imageReceiver = new ImageReceiver(this);
    private URLSpan pressedLink;
    private int previousWidth = 0;
    private int textHeight = 0;
    private StaticLayout textLayout;
    private int textWidth = 0;
    private int textX = 0;
    private int textXLeft = 0;
    private int textY = 0;

    /* renamed from: org.telegram.ui.Cells.ChatActionCell$1 */
    class C39981 implements Runnable {
        C39981() {
        }

        public void run() {
            ChatActionCell.this.requestLayout();
        }
    }

    public interface ChatActionCellDelegate {
        void didClickedImage(ChatActionCell chatActionCell);

        void didLongPressed(ChatActionCell chatActionCell);

        void didPressedBotButton(MessageObject messageObject, KeyboardButton keyboardButton);

        void didPressedReplyMessage(ChatActionCell chatActionCell, int i);

        void needOpenUserProfile(int i);
    }

    public ChatActionCell(Context context) {
        super(context);
        this.imageReceiver.setRoundRadius(AndroidUtilities.dp(32.0f));
        this.avatarDrawable = new AvatarDrawable();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void createLayout(java.lang.CharSequence r11, int r12) {
        /*
        r10 = this;
        r7 = 0;
        r0 = 1106247680; // 0x41f00000 float:30.0 double:5.465589745E-315;
        r0 = org.telegram.messenger.AndroidUtilities.dp(r0);
        r3 = r12 - r0;
        r0 = new android.text.StaticLayout;
        r2 = org.telegram.ui.ActionBar.Theme.chat_actionTextPaint;
        r4 = android.text.Layout.Alignment.ALIGN_CENTER;
        r5 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r6 = 0;
        r1 = r11;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r10.textLayout = r0;
        r10.textHeight = r7;
        r10.textWidth = r7;
        r0 = r10.textLayout;	 Catch:{ Exception -> 0x005c }
        r1 = r0.getLineCount();	 Catch:{ Exception -> 0x005c }
    L_0x0022:
        if (r7 >= r1) goto L_0x0060;
    L_0x0024:
        r0 = r10.textLayout;	 Catch:{ Exception -> 0x0057 }
        r0 = r0.getLineWidth(r7);	 Catch:{ Exception -> 0x0057 }
        r2 = (float) r3;	 Catch:{ Exception -> 0x0057 }
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0030;
    L_0x002f:
        r0 = (float) r3;	 Catch:{ Exception -> 0x0057 }
    L_0x0030:
        r2 = r10.textHeight;	 Catch:{ Exception -> 0x0057 }
        r4 = (double) r2;	 Catch:{ Exception -> 0x0057 }
        r2 = r10.textLayout;	 Catch:{ Exception -> 0x0057 }
        r2 = r2.getLineBottom(r7);	 Catch:{ Exception -> 0x0057 }
        r8 = (double) r2;	 Catch:{ Exception -> 0x0057 }
        r8 = java.lang.Math.ceil(r8);	 Catch:{ Exception -> 0x0057 }
        r4 = java.lang.Math.max(r4, r8);	 Catch:{ Exception -> 0x0057 }
        r2 = (int) r4;	 Catch:{ Exception -> 0x0057 }
        r10.textHeight = r2;	 Catch:{ Exception -> 0x0057 }
        r2 = r10.textWidth;	 Catch:{ Exception -> 0x005c }
        r4 = (double) r2;	 Catch:{ Exception -> 0x005c }
        r8 = (double) r0;	 Catch:{ Exception -> 0x005c }
        r8 = java.lang.Math.ceil(r8);	 Catch:{ Exception -> 0x005c }
        r4 = java.lang.Math.max(r4, r8);	 Catch:{ Exception -> 0x005c }
        r0 = (int) r4;	 Catch:{ Exception -> 0x005c }
        r10.textWidth = r0;	 Catch:{ Exception -> 0x005c }
        r7 = r7 + 1;
        goto L_0x0022;
    L_0x0057:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x005c }
    L_0x005b:
        return;
    L_0x005c:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
    L_0x0060:
        r0 = r10.textWidth;
        r0 = r12 - r0;
        r0 = r0 / 2;
        r10.textX = r0;
        r0 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r0 = org.telegram.messenger.AndroidUtilities.dp(r0);
        r10.textY = r0;
        r0 = r10.textLayout;
        r0 = r0.getWidth();
        r0 = r12 - r0;
        r0 = r0 / 2;
        r10.textXLeft = r0;
        goto L_0x005b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ChatActionCell.createLayout(java.lang.CharSequence, int):void");
    }

    private int findMaxWidthAroundLine(int i) {
        int i2;
        int ceil = (int) Math.ceil((double) this.textLayout.getLineWidth(i));
        int lineCount = this.textLayout.getLineCount();
        for (i2 = i + 1; i2 < lineCount; i2++) {
            int ceil2 = (int) Math.ceil((double) this.textLayout.getLineWidth(i2));
            if (Math.abs(ceil2 - ceil) >= AndroidUtilities.dp(10.0f)) {
                break;
            }
            ceil = Math.max(ceil2, ceil);
        }
        for (i2 = i - 1; i2 >= 0; i2--) {
            lineCount = (int) Math.ceil((double) this.textLayout.getLineWidth(i2));
            if (Math.abs(lineCount - ceil) >= AndroidUtilities.dp(10.0f)) {
                break;
            }
            ceil = Math.max(lineCount, ceil);
        }
        return ceil;
    }

    private boolean isLineBottom(int i, int i2, int i3, int i4, int i5) {
        return i3 == i4 + -1 || (i3 >= 0 && i3 <= i4 - 1 && findMaxWidthAroundLine(i3 + 1) + (i5 * 3) < i);
    }

    private boolean isLineTop(int i, int i2, int i3, int i4, int i5) {
        return i3 == 0 || (i3 >= 0 && i3 < i4 && findMaxWidthAroundLine(i3 - 1) + (i5 * 3) < i);
    }

    public int getCustomDate() {
        return this.customDate;
    }

    public MessageObject getMessageObject() {
        return this.currentMessageObject;
    }

    public ImageReceiver getPhotoImage() {
        return this.imageReceiver;
    }

    protected void onDraw(Canvas canvas) {
        if (this.currentMessageObject != null && this.currentMessageObject.type == 11) {
            this.imageReceiver.draw(canvas);
        }
        if (this.textLayout != null) {
            int lineCount = this.textLayout.getLineCount();
            int dp = AndroidUtilities.dp(11.0f);
            int dp2 = AndroidUtilities.dp(6.0f);
            int i = dp - dp2;
            int dp3 = AndroidUtilities.dp(8.0f);
            int dp4 = AndroidUtilities.dp(7.0f);
            int i2 = 0;
            int i3 = 0;
            while (i2 < lineCount) {
                int i4;
                Object obj;
                int i5;
                int i6;
                Object obj2;
                int findMaxWidthAroundLine;
                int i7;
                int measuredWidth;
                int dp5;
                int findMaxWidthAroundLine2 = findMaxWidthAroundLine(i2);
                int measuredWidth2 = ((getMeasuredWidth() - findMaxWidthAroundLine2) - i) / 2;
                int i8 = findMaxWidthAroundLine2 + i;
                int lineBottom = this.textLayout.getLineBottom(i2);
                int i9 = lineBottom - i3;
                Object obj3 = i2 == lineCount + -1 ? 1 : null;
                Object obj4 = i2 == 0 ? 1 : null;
                if (obj4 != null) {
                    dp4 -= AndroidUtilities.dp(3.0f);
                    i9 += AndroidUtilities.dp(3.0f);
                }
                if (obj3 != null) {
                    i9 += AndroidUtilities.dp(3.0f);
                }
                findMaxWidthAroundLine2 = 0;
                if (obj3 != null || i2 + 1 >= lineCount) {
                    i4 = 0;
                    obj = obj3;
                } else {
                    findMaxWidthAroundLine2 = findMaxWidthAroundLine(i2 + 1) + i;
                    if ((i * 2) + findMaxWidthAroundLine2 < i8) {
                        i4 = 1;
                        obj = 1;
                    } else if ((i * 2) + i8 < findMaxWidthAroundLine2) {
                        i4 = 2;
                        obj = obj3;
                    } else {
                        i4 = 3;
                        obj = obj3;
                    }
                }
                if (obj4 != null || i2 <= 0) {
                    i5 = 0;
                    i6 = 0;
                    obj2 = obj4;
                } else {
                    findMaxWidthAroundLine = findMaxWidthAroundLine(i2 - 1) + i;
                    if ((i * 2) + findMaxWidthAroundLine < i8) {
                        i5 = findMaxWidthAroundLine;
                        i6 = 1;
                        obj2 = 1;
                    } else if ((i * 2) + i8 < findMaxWidthAroundLine) {
                        i5 = findMaxWidthAroundLine;
                        i6 = 2;
                        obj2 = obj4;
                    } else {
                        i5 = findMaxWidthAroundLine;
                        i6 = 3;
                        obj2 = obj4;
                    }
                }
                if (i4 == 0) {
                    i7 = 0;
                } else if (i4 == 1) {
                    measuredWidth = (getMeasuredWidth() - findMaxWidthAroundLine2) / 2;
                    dp5 = AndroidUtilities.dp(3.0f);
                    if (isLineBottom(findMaxWidthAroundLine2, i8, i2 + 1, lineCount, i)) {
                        canvas.drawRect((float) (measuredWidth2 + dp2), (float) (dp4 + i9), (float) (measuredWidth - i), (float) ((dp4 + i9) + AndroidUtilities.dp(3.0f)), Theme.chat_actionBackgroundPaint);
                        canvas.drawRect((float) ((measuredWidth + findMaxWidthAroundLine2) + i), (float) (dp4 + i9), (float) ((measuredWidth2 + i8) - dp2), (float) ((dp4 + i9) + AndroidUtilities.dp(3.0f)), Theme.chat_actionBackgroundPaint);
                    } else {
                        canvas.drawRect((float) (measuredWidth2 + dp2), (float) (dp4 + i9), (float) measuredWidth, (float) ((dp4 + i9) + AndroidUtilities.dp(3.0f)), Theme.chat_actionBackgroundPaint);
                        canvas.drawRect((float) (measuredWidth + findMaxWidthAroundLine2), (float) (dp4 + i9), (float) ((measuredWidth2 + i8) - dp2), (float) ((dp4 + i9) + AndroidUtilities.dp(3.0f)), Theme.chat_actionBackgroundPaint);
                    }
                    i7 = dp5;
                } else if (i4 == 2) {
                    i3 = AndroidUtilities.dp(3.0f);
                    findMaxWidthAroundLine = (dp4 + i9) - AndroidUtilities.dp(11.0f);
                    findMaxWidthAroundLine2 = measuredWidth2 - dp3;
                    if (!(i6 == 2 || i6 == 3)) {
                        findMaxWidthAroundLine2 -= i;
                    }
                    if (!(obj2 == null && obj == null)) {
                        canvas.drawRect((float) (findMaxWidthAroundLine2 + dp3), (float) (AndroidUtilities.dp(3.0f) + findMaxWidthAroundLine), (float) ((findMaxWidthAroundLine2 + dp3) + dp), (float) (findMaxWidthAroundLine + dp), Theme.chat_actionBackgroundPaint);
                    }
                    Theme.chat_cornerInner[2].setBounds(findMaxWidthAroundLine2, findMaxWidthAroundLine, findMaxWidthAroundLine2 + dp3, findMaxWidthAroundLine + dp3);
                    Theme.chat_cornerInner[2].draw(canvas);
                    findMaxWidthAroundLine2 = measuredWidth2 + i8;
                    if (!(i6 == 2 || i6 == 3)) {
                        findMaxWidthAroundLine2 += i;
                    }
                    if (!(obj2 == null && obj == null)) {
                        canvas.drawRect((float) (findMaxWidthAroundLine2 - dp), (float) (AndroidUtilities.dp(3.0f) + findMaxWidthAroundLine), (float) findMaxWidthAroundLine2, (float) (findMaxWidthAroundLine + dp), Theme.chat_actionBackgroundPaint);
                    }
                    Theme.chat_cornerInner[3].setBounds(findMaxWidthAroundLine2, findMaxWidthAroundLine, findMaxWidthAroundLine2 + dp3, findMaxWidthAroundLine + dp3);
                    Theme.chat_cornerInner[3].draw(canvas);
                    i7 = i3;
                } else {
                    i7 = AndroidUtilities.dp(6.0f);
                }
                if (i6 == 0) {
                    i3 = i9;
                    findMaxWidthAroundLine2 = dp4;
                } else if (i6 == 1) {
                    int measuredWidth3 = (getMeasuredWidth() - i5) / 2;
                    measuredWidth = dp4 - AndroidUtilities.dp(3.0f);
                    dp5 = i9 + AndroidUtilities.dp(3.0f);
                    if (isLineTop(i5, i8, i2 - 1, lineCount, i)) {
                        canvas.drawRect((float) (measuredWidth2 + dp2), (float) measuredWidth, (float) (measuredWidth3 - i), (float) (AndroidUtilities.dp(3.0f) + measuredWidth), Theme.chat_actionBackgroundPaint);
                        canvas.drawRect((float) ((measuredWidth3 + i5) + i), (float) measuredWidth, (float) ((measuredWidth2 + i8) - dp2), (float) (AndroidUtilities.dp(3.0f) + measuredWidth), Theme.chat_actionBackgroundPaint);
                    } else {
                        canvas.drawRect((float) (measuredWidth2 + dp2), (float) measuredWidth, (float) measuredWidth3, (float) (AndroidUtilities.dp(3.0f) + measuredWidth), Theme.chat_actionBackgroundPaint);
                        canvas.drawRect((float) (measuredWidth3 + i5), (float) measuredWidth, (float) ((measuredWidth2 + i8) - dp2), (float) (AndroidUtilities.dp(3.0f) + measuredWidth), Theme.chat_actionBackgroundPaint);
                    }
                    i3 = dp5;
                    findMaxWidthAroundLine2 = measuredWidth;
                } else if (i6 == 2) {
                    findMaxWidthAroundLine2 = dp4 - AndroidUtilities.dp(3.0f);
                    i3 = AndroidUtilities.dp(3.0f) + i9;
                    dp5 = findMaxWidthAroundLine2 + AndroidUtilities.dp(6.2f);
                    findMaxWidthAroundLine = measuredWidth2 - dp3;
                    if (!(i4 == 2 || i4 == 3)) {
                        findMaxWidthAroundLine -= i;
                    }
                    if (!(obj2 == null && obj == null)) {
                        canvas.drawRect((float) (findMaxWidthAroundLine + dp3), (float) (AndroidUtilities.dp(3.0f) + findMaxWidthAroundLine2), (float) ((findMaxWidthAroundLine + dp3) + dp), (float) (AndroidUtilities.dp(11.0f) + findMaxWidthAroundLine2), Theme.chat_actionBackgroundPaint);
                    }
                    Theme.chat_cornerInner[0].setBounds(findMaxWidthAroundLine, dp5, findMaxWidthAroundLine + dp3, dp5 + dp3);
                    Theme.chat_cornerInner[0].draw(canvas);
                    findMaxWidthAroundLine = measuredWidth2 + i8;
                    if (!(i4 == 2 || i4 == 3)) {
                        findMaxWidthAroundLine += i;
                    }
                    if (!(obj2 == null && obj == null)) {
                        canvas.drawRect((float) (findMaxWidthAroundLine - dp), (float) (AndroidUtilities.dp(3.0f) + findMaxWidthAroundLine2), (float) findMaxWidthAroundLine, (float) (AndroidUtilities.dp(11.0f) + findMaxWidthAroundLine2), Theme.chat_actionBackgroundPaint);
                    }
                    Theme.chat_cornerInner[1].setBounds(findMaxWidthAroundLine, dp5, findMaxWidthAroundLine + dp3, dp5 + dp3);
                    Theme.chat_cornerInner[1].draw(canvas);
                } else {
                    findMaxWidthAroundLine2 = dp4 - AndroidUtilities.dp(6.0f);
                    i3 = AndroidUtilities.dp(6.0f) + i9;
                }
                if (obj2 == null && obj == null) {
                    canvas.drawRect((float) measuredWidth2, (float) dp4, (float) (measuredWidth2 + i8), (float) (dp4 + i9), Theme.chat_actionBackgroundPaint);
                } else {
                    canvas.drawRect((float) (measuredWidth2 + dp2), (float) dp4, (float) ((measuredWidth2 + i8) - dp2), (float) (dp4 + i9), Theme.chat_actionBackgroundPaint);
                }
                findMaxWidthAroundLine = measuredWidth2 - i;
                i8 = (i8 + measuredWidth2) - dp2;
                if (obj2 != null && obj == null && i4 != 2) {
                    canvas.drawRect((float) findMaxWidthAroundLine, (float) (findMaxWidthAroundLine2 + dp), (float) (findMaxWidthAroundLine + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - AndroidUtilities.dp(6.0f)), Theme.chat_actionBackgroundPaint);
                    canvas.drawRect((float) i8, (float) (findMaxWidthAroundLine2 + dp), (float) (i8 + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - AndroidUtilities.dp(6.0f)), Theme.chat_actionBackgroundPaint);
                } else if (obj != null && obj2 == null && i6 != 2) {
                    canvas.drawRect((float) findMaxWidthAroundLine, (float) ((findMaxWidthAroundLine2 + dp) - AndroidUtilities.dp(5.0f)), (float) (findMaxWidthAroundLine + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - dp), Theme.chat_actionBackgroundPaint);
                    canvas.drawRect((float) i8, (float) ((findMaxWidthAroundLine2 + dp) - AndroidUtilities.dp(5.0f)), (float) (i8 + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - dp), Theme.chat_actionBackgroundPaint);
                } else if (!(obj2 == null && obj == null)) {
                    canvas.drawRect((float) findMaxWidthAroundLine, (float) (findMaxWidthAroundLine2 + dp), (float) (findMaxWidthAroundLine + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - dp), Theme.chat_actionBackgroundPaint);
                    canvas.drawRect((float) i8, (float) (findMaxWidthAroundLine2 + dp), (float) (i8 + dp), (float) (((findMaxWidthAroundLine2 + i3) + i7) - dp), Theme.chat_actionBackgroundPaint);
                }
                if (obj2 != null) {
                    Theme.chat_cornerOuter[0].setBounds(findMaxWidthAroundLine, findMaxWidthAroundLine2, findMaxWidthAroundLine + dp, findMaxWidthAroundLine2 + dp);
                    Theme.chat_cornerOuter[0].draw(canvas);
                    Theme.chat_cornerOuter[1].setBounds(i8, findMaxWidthAroundLine2, i8 + dp, findMaxWidthAroundLine2 + dp);
                    Theme.chat_cornerOuter[1].draw(canvas);
                }
                if (obj != null) {
                    int i10 = ((findMaxWidthAroundLine2 + i3) + i7) - dp;
                    Theme.chat_cornerOuter[2].setBounds(i8, i10, i8 + dp, i10 + dp);
                    Theme.chat_cornerOuter[2].draw(canvas);
                    Theme.chat_cornerOuter[3].setBounds(findMaxWidthAroundLine, i10, findMaxWidthAroundLine + dp, i10 + dp);
                    Theme.chat_cornerOuter[3].draw(canvas);
                }
                dp4 = findMaxWidthAroundLine2 + i3;
                i2++;
                i3 = lineBottom;
            }
            canvas.save();
            canvas.translate((float) this.textXLeft, (float) this.textY);
            this.textLayout.draw(canvas);
            canvas.restore();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }

    protected void onLongPress() {
        if (this.delegate != null) {
            this.delegate.didLongPressed(this);
        }
    }

    protected void onMeasure(int i, int i2) {
        if (this.currentMessageObject == null && this.customText == null) {
            setMeasuredDimension(MeasureSpec.getSize(i), this.textHeight + AndroidUtilities.dp(14.0f));
            return;
        }
        int max = Math.max(AndroidUtilities.dp(30.0f), MeasureSpec.getSize(i));
        if (max != this.previousWidth) {
            CharSequence string = this.currentMessageObject != null ? (this.currentMessageObject.messageOwner == null || this.currentMessageObject.messageOwner.media == null || this.currentMessageObject.messageOwner.media.ttl_seconds == 0) ? this.currentMessageObject.messageText : this.currentMessageObject.messageOwner.media.photo instanceof TLRPC$TL_photoEmpty ? LocaleController.getString("AttachPhotoExpired", R.string.AttachPhotoExpired) : this.currentMessageObject.messageOwner.media.document instanceof TLRPC$TL_documentEmpty ? LocaleController.getString("AttachVideoExpired", R.string.AttachVideoExpired) : this.currentMessageObject.messageText : this.customText;
            this.previousWidth = max;
            createLayout(string, max);
            if (this.currentMessageObject != null && this.currentMessageObject.type == 11) {
                this.imageReceiver.setImageCoords((max - AndroidUtilities.dp(64.0f)) / 2, this.textHeight + AndroidUtilities.dp(15.0f), AndroidUtilities.dp(64.0f), AndroidUtilities.dp(64.0f));
            }
        }
        int i3 = this.textHeight;
        int i4 = (this.currentMessageObject == null || this.currentMessageObject.type != 11) ? 0 : 70;
        setMeasuredDimension(max, AndroidUtilities.dp((float) (i4 + 14)) + i3);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.currentMessageObject == null) {
            return super.onTouchEvent(motionEvent);
        }
        boolean z;
        boolean z2;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            if (this.delegate != null) {
                if (this.currentMessageObject.type == 11 && this.imageReceiver.isInsideImage(x, y)) {
                    this.imagePressed = true;
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    startCheckLongPress();
                }
            }
            z = false;
        } else {
            if (motionEvent.getAction() != 2) {
                cancelCheckLongPress();
            }
            if (this.imagePressed) {
                if (motionEvent.getAction() == 1) {
                    this.imagePressed = false;
                    if (this.delegate != null) {
                        this.delegate.didClickedImage(this);
                        playSoundEffect(0);
                        z = false;
                    }
                } else if (motionEvent.getAction() == 3) {
                    this.imagePressed = false;
                    z = false;
                } else if (motionEvent.getAction() == 2 && !this.imageReceiver.isInsideImage(x, y)) {
                    this.imagePressed = false;
                }
            }
            z = false;
        }
        if (!z && (motionEvent.getAction() == 0 || (this.pressedLink != null && motionEvent.getAction() == 1))) {
            if (x < ((float) this.textX) || y < ((float) this.textY) || x > ((float) (this.textX + this.textWidth)) || y > ((float) (this.textY + this.textHeight))) {
                this.pressedLink = null;
            } else {
                x -= (float) this.textXLeft;
                int lineForVertical = this.textLayout.getLineForVertical((int) (y - ((float) this.textY)));
                int offsetForHorizontal = this.textLayout.getOffsetForHorizontal(lineForVertical, x);
                float lineLeft = this.textLayout.getLineLeft(lineForVertical);
                if (lineLeft > x || this.textLayout.getLineWidth(lineForVertical) + lineLeft < x || !(this.currentMessageObject.messageText instanceof Spannable)) {
                    this.pressedLink = null;
                } else {
                    URLSpan[] uRLSpanArr = (URLSpan[]) ((Spannable) this.currentMessageObject.messageText).getSpans(offsetForHorizontal, offsetForHorizontal, URLSpan.class);
                    if (uRLSpanArr.length != 0) {
                        if (motionEvent.getAction() == 0) {
                            this.pressedLink = uRLSpanArr[0];
                            z2 = true;
                        } else if (uRLSpanArr[0] == this.pressedLink) {
                            if (this.delegate != null) {
                                String url = uRLSpanArr[0].getURL();
                                if (url.startsWith("game")) {
                                    this.delegate.didPressedReplyMessage(this, this.currentMessageObject.messageOwner.reply_to_msg_id);
                                } else {
                                    this.delegate.needOpenUserProfile(Integer.parseInt(url));
                                }
                            }
                            z2 = true;
                        }
                        return z2 ? super.onTouchEvent(motionEvent) : z2;
                    } else {
                        this.pressedLink = null;
                    }
                }
            }
        }
        z2 = z;
        if (z2) {
        }
    }

    public void setCustomDate(int i) {
        if (this.customDate != i) {
            String str = TtmlNode.ANONYMOUS_REGION_ID;
            CharSequence formatDateChat = BuildConfig.FLAVOR.contentEquals("arabgram") ? LocaleController.formatDateChat((long) i) : ChatActivity.getPersianDate((long) i);
            if (this.customText == null || !TextUtils.equals(formatDateChat, this.customText)) {
                this.previousWidth = 0;
                this.customDate = i;
                this.customText = formatDateChat;
                if (getMeasuredWidth() != 0) {
                    createLayout(this.customText, getMeasuredWidth());
                    invalidate();
                }
                AndroidUtilities.runOnUIThread(new C39981());
            }
        }
    }

    public void setDelegate(ChatActionCellDelegate chatActionCellDelegate) {
        this.delegate = chatActionCellDelegate;
    }

    public void setMessageObject(MessageObject messageObject) {
        boolean z = true;
        if (this.currentMessageObject != messageObject || (!this.hasReplyMessage && messageObject.replyMessageObject != null)) {
            this.currentMessageObject = messageObject;
            this.hasReplyMessage = messageObject.replyMessageObject != null;
            this.previousWidth = 0;
            if (this.currentMessageObject.type == 11) {
                int i;
                if (messageObject.messageOwner.to_id == null) {
                    i = 0;
                } else if (messageObject.messageOwner.to_id.chat_id != 0) {
                    i = messageObject.messageOwner.to_id.chat_id;
                } else if (messageObject.messageOwner.to_id.channel_id != 0) {
                    i = messageObject.messageOwner.to_id.channel_id;
                } else {
                    i = messageObject.messageOwner.to_id.user_id;
                    if (i == UserConfig.getClientUserId()) {
                        i = messageObject.messageOwner.from_id;
                    }
                }
                this.avatarDrawable.setInfo(i, null, null, false);
                if (this.currentMessageObject.messageOwner.action instanceof TLRPC$TL_messageActionUserUpdatedPhoto) {
                    this.imageReceiver.setImage(this.currentMessageObject.messageOwner.action.newUserPhoto.photo_small, "50_50", this.avatarDrawable, null, 0);
                } else {
                    PhotoSize closestPhotoSizeWithSize = FileLoader.getClosestPhotoSizeWithSize(this.currentMessageObject.photoThumbs, AndroidUtilities.dp(64.0f));
                    if (closestPhotoSizeWithSize != null) {
                        this.imageReceiver.setImage(closestPhotoSizeWithSize.location, "50_50", this.avatarDrawable, null, 0);
                    } else {
                        this.imageReceiver.setImageBitmap(this.avatarDrawable);
                    }
                }
                ImageReceiver imageReceiver = this.imageReceiver;
                if (PhotoViewer.getInstance().isShowingImage(this.currentMessageObject)) {
                    z = false;
                }
                imageReceiver.setVisible(z, false);
            } else {
                this.imageReceiver.setImageBitmap((Bitmap) null);
            }
            requestLayout();
        }
    }
}
