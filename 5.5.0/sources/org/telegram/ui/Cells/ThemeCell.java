package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.Theme.ThemeInfo;
import org.telegram.ui.Components.LayoutHelper;

public class ThemeCell extends FrameLayout {
    private static byte[] bytes = new byte[1024];
    private ImageView checkImage;
    private ThemeInfo currentThemeInfo;
    private boolean needDivider;
    private ImageView optionsButton;
    private Paint paint = new Paint(1);
    private TextView textView;

    public ThemeCell(Context context) {
        int i = 3;
        super(context);
        setWillNotDraw(false);
        this.textView = new TextView(context);
        this.textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.textView.setTextSize(1, 16.0f);
        this.textView.setLines(1);
        this.textView.setMaxLines(1);
        this.textView.setSingleLine(true);
        this.textView.setPadding(0, 0, 0, AndroidUtilities.dp(1.0f));
        this.textView.setEllipsize(TruncateAt.END);
        this.textView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        addView(this.textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? 101.0f : 60.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 60.0f : 101.0f, BitmapDescriptorFactory.HUE_RED));
        this.checkImage = new ImageView(context);
        this.checkImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
        this.checkImage.setImageResource(R.drawable.sticker_added);
        addView(this.checkImage, LayoutHelper.createFrame(19, 14.0f, (LocaleController.isRTL ? 3 : 5) | 16, 55.0f, BitmapDescriptorFactory.HUE_RED, 55.0f, BitmapDescriptorFactory.HUE_RED));
        this.optionsButton = new ImageView(context);
        this.optionsButton.setFocusable(false);
        this.optionsButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_stickers_menuSelector)));
        this.optionsButton.setImageResource(R.drawable.ic_ab_other);
        this.optionsButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_stickers_menu), Mode.MULTIPLY));
        this.optionsButton.setScaleType(ScaleType.CENTER);
        View view = this.optionsButton;
        if (!LocaleController.isRTL) {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(48, 48, i | 48));
    }

    public ThemeInfo getCurrentThemeInfo() {
        return this.currentThemeInfo;
    }

    public TextView getTextView() {
        return this.textView;
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
        int dp = AndroidUtilities.dp(27.0f);
        if (LocaleController.isRTL) {
            dp = getWidth() - dp;
        }
        canvas.drawCircle((float) dp, (float) AndroidUtilities.dp(24.0f), (float) AndroidUtilities.dp(11.0f), this.paint);
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f), 1073741824));
    }

    public void setOnOptionsClick(OnClickListener onClickListener) {
        this.optionsButton.setOnClickListener(onClickListener);
    }

    public void setTextColor(int i) {
        this.textView.setTextColor(i);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setTheme(org.telegram.ui.ActionBar.Theme.ThemeInfo r14, boolean r15) {
        /*
        r13 = this;
        r13.currentThemeInfo = r14;
        r0 = r14.getName();
        r1 = ".attheme";
        r1 = r0.endsWith(r1);
        if (r1 == 0) goto L_0x001a;
    L_0x000f:
        r1 = 0;
        r2 = 46;
        r2 = r0.lastIndexOf(r2);
        r0 = r0.substring(r1, r2);
    L_0x001a:
        r1 = r13.textView;
        r1.setText(r0);
        r13.needDivider = r15;
        r1 = r13.checkImage;
        r0 = org.telegram.ui.ActionBar.Theme.getCurrentTheme();
        if (r14 != r0) goto L_0x009b;
    L_0x0029:
        r0 = 0;
    L_0x002a:
        r1.setVisibility(r0);
        r2 = 0;
        r0 = r14.pathToFile;
        if (r0 != 0) goto L_0x0036;
    L_0x0032:
        r0 = r14.assetName;
        if (r0 == 0) goto L_0x008c;
    L_0x0036:
        r1 = 0;
        r6 = 0;
        r0 = r14.assetName;	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        if (r0 == 0) goto L_0x009d;
    L_0x003c:
        r0 = r14.assetName;	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        r0 = org.telegram.ui.ActionBar.Theme.getAssetFile(r0);	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
    L_0x0042:
        r3 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        r3.<init>(r0);	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        r0 = 0;
        r4 = r2;
        r2 = r0;
    L_0x004a:
        r0 = bytes;	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r7 = r3.read(r0);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r0 = -1;
        if (r7 == r0) goto L_0x0141;
    L_0x0053:
        r1 = 0;
        r0 = 0;
        r5 = r0;
        r0 = r1;
        r1 = r2;
        r2 = r6;
    L_0x0059:
        if (r5 >= r7) goto L_0x013d;
    L_0x005b:
        r8 = bytes;	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r8 = r8[r5];	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r9 = 10;
        if (r8 != r9) goto L_0x0102;
    L_0x0063:
        r1 = r1 + 1;
        r8 = r5 - r0;
        r8 = r8 + 1;
        r9 = new java.lang.String;	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r10 = bytes;	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r11 = r8 + -1;
        r12 = "UTF-8";
        r9.<init>(r10, r0, r11, r12);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r10 = "WPS";
        r10 = r9.startsWith(r10);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        if (r10 == 0) goto L_0x00b4;
    L_0x007e:
        r0 = r1;
        r1 = r4;
    L_0x0080:
        if (r6 == r2) goto L_0x013a;
    L_0x0082:
        r4 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        if (r0 < r4) goto L_0x0106;
    L_0x0086:
        r2 = r1;
    L_0x0087:
        if (r3 == 0) goto L_0x008c;
    L_0x0089:
        r3.close();	 Catch:{ Exception -> 0x0118 }
    L_0x008c:
        if (r2 != 0) goto L_0x009a;
    L_0x008e:
        r0 = r13.paint;
        r1 = "actionBarDefault";
        r1 = org.telegram.ui.ActionBar.Theme.getDefaultColor(r1);
        r0.setColor(r1);
    L_0x009a:
        return;
    L_0x009b:
        r0 = 4;
        goto L_0x002a;
    L_0x009d:
        r0 = new java.io.File;	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        r3 = r14.pathToFile;	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x00a5, all -> 0x011e }
        goto L_0x0042;
    L_0x00a5:
        r0 = move-exception;
    L_0x00a6:
        org.telegram.messenger.FileLog.e(r0);	 Catch:{ all -> 0x012d }
        if (r1 == 0) goto L_0x008c;
    L_0x00ab:
        r1.close();	 Catch:{ Exception -> 0x00af }
        goto L_0x008c;
    L_0x00af:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x008c;
    L_0x00b4:
        r10 = 61;
        r10 = r9.indexOf(r10);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r11 = -1;
        if (r10 == r11) goto L_0x0100;
    L_0x00bd:
        r11 = 0;
        r11 = r9.substring(r11, r10);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r12 = "actionBarDefault";
        r11 = r11.equals(r12);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        if (r11 == 0) goto L_0x0100;
    L_0x00cb:
        r0 = r10 + 1;
        r0 = r9.substring(r0);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r5 = r0.length();	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        if (r5 <= 0) goto L_0x00f7;
    L_0x00d7:
        r5 = 0;
        r5 = r0.charAt(r5);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r7 = 35;
        if (r5 != r7) goto L_0x00f7;
    L_0x00e0:
        r0 = android.graphics.Color.parseColor(r0);	 Catch:{ Exception -> 0x00ed }
    L_0x00e4:
        r4 = 1;
        r5 = r13.paint;	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r5.setColor(r0);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r0 = r1;
        r1 = r4;
        goto L_0x0080;
    L_0x00ed:
        r5 = move-exception;
        r0 = org.telegram.messenger.Utilities.parseInt(r0);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        goto L_0x00e4;
    L_0x00f7:
        r0 = org.telegram.messenger.Utilities.parseInt(r0);	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        r0 = r0.intValue();	 Catch:{ Throwable -> 0x0130, all -> 0x012b }
        goto L_0x00e4;
    L_0x0100:
        r0 = r0 + r8;
        r2 = r2 + r8;
    L_0x0102:
        r5 = r5 + 1;
        goto L_0x0059;
    L_0x0106:
        r4 = r3.getChannel();	 Catch:{ Throwable -> 0x0135, all -> 0x012b }
        r6 = (long) r2;	 Catch:{ Throwable -> 0x0135, all -> 0x012b }
        r4.position(r6);	 Catch:{ Throwable -> 0x0135, all -> 0x012b }
        if (r1 == 0) goto L_0x0113;
    L_0x0110:
        r2 = r1;
        goto L_0x0087;
    L_0x0113:
        r6 = r2;
        r4 = r1;
        r2 = r0;
        goto L_0x004a;
    L_0x0118:
        r0 = move-exception;
        org.telegram.messenger.FileLog.e(r0);
        goto L_0x008c;
    L_0x011e:
        r0 = move-exception;
        r3 = r1;
    L_0x0120:
        if (r3 == 0) goto L_0x0125;
    L_0x0122:
        r3.close();	 Catch:{ Exception -> 0x0126 }
    L_0x0125:
        throw r0;
    L_0x0126:
        r1 = move-exception;
        org.telegram.messenger.FileLog.e(r1);
        goto L_0x0125;
    L_0x012b:
        r0 = move-exception;
        goto L_0x0120;
    L_0x012d:
        r0 = move-exception;
        r3 = r1;
        goto L_0x0120;
    L_0x0130:
        r0 = move-exception;
        r1 = r3;
        r2 = r4;
        goto L_0x00a6;
    L_0x0135:
        r0 = move-exception;
        r2 = r1;
        r1 = r3;
        goto L_0x00a6;
    L_0x013a:
        r2 = r1;
        goto L_0x0087;
    L_0x013d:
        r0 = r1;
        r1 = r4;
        goto L_0x0080;
    L_0x0141:
        r2 = r4;
        goto L_0x0087;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Cells.ThemeCell.setTheme(org.telegram.ui.ActionBar.Theme$ThemeInfo, boolean):void");
    }
}
