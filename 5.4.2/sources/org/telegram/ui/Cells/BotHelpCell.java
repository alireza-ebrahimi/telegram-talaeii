package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.StaticLayout;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.browser.Browser;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.LinkPath;
import org.telegram.ui.Components.TypefaceSpan;
import org.telegram.ui.Components.URLSpanNoUnderline;

public class BotHelpCell extends View {
    private BotHelpCellDelegate delegate;
    private int height;
    private String oldText;
    private ClickableSpan pressedLink;
    private StaticLayout textLayout;
    private int textX;
    private int textY;
    private LinkPath urlPath = new LinkPath();
    private int width;

    public interface BotHelpCellDelegate {
        void didPressUrl(String str);
    }

    public BotHelpCell(Context context) {
        super(context);
    }

    private void resetPressedLink() {
        if (this.pressedLink != null) {
            this.pressedLink = null;
        }
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        int width = (canvas.getWidth() - this.width) / 2;
        int dp = AndroidUtilities.dp(4.0f);
        Theme.chat_msgInMediaShadowDrawable.setBounds(width, dp, this.width + width, this.height + dp);
        Theme.chat_msgInMediaShadowDrawable.draw(canvas);
        Theme.chat_msgInMediaDrawable.setBounds(width, dp, this.width + width, this.height + dp);
        Theme.chat_msgInMediaDrawable.draw(canvas);
        Theme.chat_msgTextPaint.setColor(Theme.getColor(Theme.key_chat_messageTextIn));
        Theme.chat_msgTextPaint.linkColor = Theme.getColor(Theme.key_chat_messageLinkIn);
        canvas.save();
        width += AndroidUtilities.dp(11.0f);
        this.textX = width;
        float f = (float) width;
        dp += AndroidUtilities.dp(11.0f);
        this.textY = dp;
        canvas.translate(f, (float) dp);
        if (this.pressedLink != null) {
            canvas.drawPath(this.urlPath, Theme.chat_urlPaint);
        }
        if (this.textLayout != null) {
            this.textLayout.draw(canvas);
        }
        canvas.restore();
    }

    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), this.height + AndroidUtilities.dp(8.0f));
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        Throwable e;
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (this.textLayout != null) {
            if (motionEvent.getAction() == 0 || (this.pressedLink != null && motionEvent.getAction() == 1)) {
                if (motionEvent.getAction() == 0) {
                    resetPressedLink();
                    try {
                        int i = (int) (x - ((float) this.textX));
                        int lineForVertical = this.textLayout.getLineForVertical((int) (y - ((float) this.textY)));
                        int offsetForHorizontal = this.textLayout.getOffsetForHorizontal(lineForVertical, (float) i);
                        float lineLeft = this.textLayout.getLineLeft(lineForVertical);
                        if (lineLeft > ((float) i) || this.textLayout.getLineWidth(lineForVertical) + lineLeft < ((float) i)) {
                            resetPressedLink();
                        } else {
                            Spannable spannable = (Spannable) this.textLayout.getText();
                            ClickableSpan[] clickableSpanArr = (ClickableSpan[]) spannable.getSpans(offsetForHorizontal, offsetForHorizontal, ClickableSpan.class);
                            if (clickableSpanArr.length != 0) {
                                resetPressedLink();
                                this.pressedLink = clickableSpanArr[0];
                                try {
                                    lineForVertical = spannable.getSpanStart(this.pressedLink);
                                    this.urlPath.setCurrentLayout(this.textLayout, lineForVertical, BitmapDescriptorFactory.HUE_RED);
                                    this.textLayout.getSelectionPath(lineForVertical, spannable.getSpanEnd(this.pressedLink), this.urlPath);
                                    z = true;
                                } catch (Throwable e2) {
                                    try {
                                        FileLog.e(e2);
                                        z = true;
                                    } catch (Exception e3) {
                                        e2 = e3;
                                        boolean z2 = true;
                                        resetPressedLink();
                                        FileLog.e(e2);
                                        z = z2;
                                        if (!z) {
                                        }
                                    }
                                }
                                return z || super.onTouchEvent(motionEvent);
                            } else {
                                resetPressedLink();
                            }
                        }
                    } catch (Exception e4) {
                        e2 = e4;
                        z2 = false;
                        resetPressedLink();
                        FileLog.e(e2);
                        z = z2;
                        if (z) {
                        }
                    }
                } else if (this.pressedLink != null) {
                    try {
                        if (this.pressedLink instanceof URLSpanNoUnderline) {
                            String url = ((URLSpanNoUnderline) this.pressedLink).getURL();
                            if ((url.startsWith("@") || url.startsWith("#") || url.startsWith("/")) && this.delegate != null) {
                                this.delegate.didPressUrl(url);
                            }
                        } else if (this.pressedLink instanceof URLSpan) {
                            Browser.openUrl(getContext(), ((URLSpan) this.pressedLink).getURL());
                        } else {
                            this.pressedLink.onClick(this);
                        }
                    } catch (Throwable e22) {
                        FileLog.e(e22);
                    }
                    resetPressedLink();
                    z = true;
                    if (z) {
                    }
                }
            } else if (motionEvent.getAction() == 3) {
                resetPressedLink();
            }
        }
        z = false;
        if (z) {
        }
    }

    public void setDelegate(BotHelpCellDelegate botHelpCellDelegate) {
        this.delegate = botHelpCellDelegate;
    }

    public void setText(String str) {
        int i = 0;
        if (str == null || str.length() == 0) {
            setVisibility(8);
        } else if (str == null || this.oldText == null || !str.equals(this.oldText)) {
            int i2;
            this.oldText = str;
            setVisibility(0);
            int minTabletSide = AndroidUtilities.isTablet() ? (int) (((float) AndroidUtilities.getMinTabletSide()) * 0.7f) : (int) (((float) Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y)) * 0.7f);
            String[] split = str.split("\n");
            CharSequence spannableStringBuilder = new SpannableStringBuilder();
            Object string = LocaleController.getString("BotInfoTitle", R.string.BotInfoTitle);
            spannableStringBuilder.append(string);
            spannableStringBuilder.append("\n\n");
            for (i2 = 0; i2 < split.length; i2++) {
                spannableStringBuilder.append(split[i2].trim());
                if (i2 != split.length - 1) {
                    spannableStringBuilder.append("\n");
                }
            }
            MessageObject.addLinks(false, spannableStringBuilder);
            spannableStringBuilder.setSpan(new TypefaceSpan(AndroidUtilities.getTypeface("fonts/rmedium.ttf")), 0, string.length(), 33);
            Emoji.replaceEmoji(spannableStringBuilder, Theme.chat_msgTextPaint.getFontMetricsInt(), AndroidUtilities.dp(20.0f), false);
            try {
                this.textLayout = new StaticLayout(spannableStringBuilder, Theme.chat_msgTextPaint, minTabletSide, Alignment.ALIGN_NORMAL, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                this.width = 0;
                this.height = this.textLayout.getHeight() + AndroidUtilities.dp(22.0f);
                i2 = this.textLayout.getLineCount();
                while (i < i2) {
                    this.width = (int) Math.ceil((double) Math.max((float) this.width, this.textLayout.getLineWidth(i) + this.textLayout.getLineLeft(i)));
                    i++;
                }
                if (this.width > minTabletSide) {
                    this.width = minTabletSide;
                }
            } catch (Throwable e) {
                FileLog.e("tmessage", e);
            }
            this.width += AndroidUtilities.dp(22.0f);
        }
    }
}
