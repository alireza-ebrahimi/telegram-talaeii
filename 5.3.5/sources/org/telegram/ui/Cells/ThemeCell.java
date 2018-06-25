package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
import java.io.File;
import java.io.FileInputStream;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
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
        int i;
        int i2 = 3;
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
        View view = this.textView;
        if (LocaleController.isRTL) {
            i = 5;
        } else {
            i = 3;
        }
        addView(view, LayoutHelper.createFrame(-1, -1.0f, i | 48, LocaleController.isRTL ? 101.0f : 60.0f, 0.0f, LocaleController.isRTL ? 60.0f : 101.0f, 0.0f));
        this.checkImage = new ImageView(context);
        this.checkImage.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_featuredStickers_addedIcon), Mode.MULTIPLY));
        this.checkImage.setImageResource(R.drawable.sticker_added);
        view = this.checkImage;
        if (LocaleController.isRTL) {
            i = 3;
        } else {
            i = 5;
        }
        addView(view, LayoutHelper.createFrame(19, 14.0f, i | 16, 55.0f, 0.0f, 55.0f, 0.0f));
        this.optionsButton = new ImageView(context);
        this.optionsButton.setFocusable(false);
        this.optionsButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.getColor(Theme.key_stickers_menuSelector)));
        this.optionsButton.setImageResource(R.drawable.ic_ab_other);
        this.optionsButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_stickers_menu), Mode.MULTIPLY));
        this.optionsButton.setScaleType(ScaleType.CENTER);
        View view2 = this.optionsButton;
        if (!LocaleController.isRTL) {
            i2 = 5;
        }
        addView(view2, LayoutHelper.createFrame(48, 48, i2 | 48));
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec((this.needDivider ? 1 : 0) + AndroidUtilities.dp(48.0f), 1073741824));
    }

    public void setOnOptionsClick(OnClickListener listener) {
        this.optionsButton.setOnClickListener(listener);
    }

    public TextView getTextView() {
        return this.textView;
    }

    public void setTextColor(int color) {
        this.textView.setTextColor(color);
    }

    public ThemeInfo getCurrentThemeInfo() {
        return this.currentThemeInfo;
    }

    public void setTheme(ThemeInfo themeInfo, boolean divider) {
        Throwable e;
        Throwable th;
        this.currentThemeInfo = themeInfo;
        String text = themeInfo.getName();
        if (text.endsWith(".attheme")) {
            text = text.substring(0, text.lastIndexOf(46));
        }
        this.textView.setText(text);
        this.needDivider = divider;
        this.checkImage.setVisibility(themeInfo == Theme.getCurrentTheme() ? 0 : 4);
        boolean finished = false;
        if (!(themeInfo.pathToFile == null && themeInfo.assetName == null)) {
            FileInputStream fileInputStream = null;
            int currentPosition = 0;
            try {
                File file;
                if (themeInfo.assetName != null) {
                    file = Theme.getAssetFile(themeInfo.assetName);
                } else {
                    file = new File(themeInfo.pathToFile);
                }
                FileInputStream fileInputStream2 = new FileInputStream(file);
                int linesRead = 0;
                do {
                    try {
                        int read = fileInputStream2.read(bytes);
                        if (read == -1) {
                            break;
                        }
                        int previousPosition = currentPosition;
                        int start = 0;
                        for (int a = 0; a < read; a++) {
                            if (bytes[a] == (byte) 10) {
                                linesRead++;
                                int len = (a - start) + 1;
                                String line = new String(bytes, start, len - 1, "UTF-8");
                                if (line.startsWith("WPS")) {
                                    break;
                                }
                                int idx = line.indexOf(61);
                                if (idx == -1 || !line.substring(0, idx).equals(Theme.key_actionBarDefault)) {
                                    start += len;
                                    currentPosition += len;
                                } else {
                                    int value;
                                    String param = line.substring(idx + 1);
                                    if (param.length() <= 0 || param.charAt(0) != '#') {
                                        value = Utilities.parseInt(param).intValue();
                                    } else {
                                        try {
                                            value = Color.parseColor(param);
                                        } catch (Exception e2) {
                                            value = Utilities.parseInt(param).intValue();
                                        }
                                    }
                                    finished = true;
                                    this.paint.setColor(value);
                                }
                            }
                        }
                        if (previousPosition == currentPosition || linesRead >= 500) {
                            break;
                        }
                        fileInputStream2.getChannel().position((long) currentPosition);
                    } catch (Throwable th2) {
                        th = th2;
                        fileInputStream = fileInputStream2;
                    }
                } while (!finished);
                if (fileInputStream2 != null) {
                    try {
                        fileInputStream2.close();
                    } catch (Exception e3) {
                        FileLog.e(e3);
                    }
                }
            } catch (Throwable th3) {
                e = th3;
                try {
                    FileLog.e(e);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e32) {
                            FileLog.e(e32);
                        }
                    }
                    if (!finished) {
                        this.paint.setColor(Theme.getDefaultColor(Theme.key_actionBarDefault));
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (Exception e322) {
                            FileLog.e(e322);
                        }
                    }
                    throw th;
                }
            }
        }
        if (!finished) {
            this.paint.setColor(Theme.getDefaultColor(Theme.key_actionBarDefault));
        }
    }

    protected void onDraw(Canvas canvas) {
        if (this.needDivider) {
            canvas.drawLine((float) getPaddingLeft(), (float) (getHeight() - 1), (float) (getWidth() - getPaddingRight()), (float) (getHeight() - 1), Theme.dividerPaint);
        }
        int x = AndroidUtilities.dp(27.0f);
        if (LocaleController.isRTL) {
            x = getWidth() - x;
        }
        canvas.drawCircle((float) x, (float) AndroidUtilities.dp(24.0f), (float) AndroidUtilities.dp(11.0f), this.paint);
    }
}
