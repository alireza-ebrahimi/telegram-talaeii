package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Keep;
import android.support.v4.view.InputDeviceCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarLayout;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.TextColorThemeCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.WallpaperUpdater.WallpaperUpdaterDelegate;
import org.telegram.ui.LaunchActivity;

public class ThemeEditorView {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile ThemeEditorView Instance = null;
    private ArrayList<ThemeDescription> currentThemeDesription;
    private int currentThemeDesriptionPosition;
    private String currentThemeName;
    private DecelerateInterpolator decelerateInterpolator;
    private EditorAlert editorAlert;
    private final int editorHeight = AndroidUtilities.dp(54.0f);
    private final int editorWidth = AndroidUtilities.dp(54.0f);
    private boolean hidden;
    private Activity parentActivity;
    private SharedPreferences preferences;
    private WallpaperUpdater wallpaperUpdater;
    private LayoutParams windowLayoutParams;
    private WindowManager windowManager;
    private FrameLayout windowView;

    /* renamed from: org.telegram.ui.Components.ThemeEditorView$3 */
    class C27873 extends AnimatorListenerAdapter {
        C27873() {
        }

        public void onAnimationEnd(Animator animation) {
            if (ThemeEditorView.this.windowView != null) {
                ThemeEditorView.this.windowManager.removeView(ThemeEditorView.this.windowView);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ThemeEditorView$4 */
    class C27884 extends AnimatorListenerAdapter {
        C27884() {
        }

        public void onAnimationEnd(Animator animation) {
            Theme.saveCurrentTheme(ThemeEditorView.this.currentThemeName, true);
            ThemeEditorView.this.destroy();
        }
    }

    public class EditorAlert extends BottomSheet {
        private boolean animationInProgress;
        private FrameLayout bottomLayout;
        private FrameLayout bottomSaveLayout;
        private TextView cancelButton;
        private AnimatorSet colorChangeAnimation;
        private ColorPicker colorPicker;
        private TextView defaultButtom;
        private boolean ignoreTextChange;
        private LinearLayoutManager layoutManager;
        private ListAdapter listAdapter;
        private RecyclerListView listView;
        private int previousScrollPosition;
        private TextView saveButton;
        private int scrollOffsetY;
        private View shadow;
        private Drawable shadowDrawable;
        private boolean startedColorChange;
        private int topBeforeSwitch;

        /* renamed from: org.telegram.ui.Components.ThemeEditorView$EditorAlert$9 */
        class C27979 extends AnimatorListenerAdapter {
            C27979() {
            }

            public void onAnimationEnd(Animator animation) {
                EditorAlert.this.listView.setVisibility(4);
                EditorAlert.this.bottomSaveLayout.setVisibility(4);
                EditorAlert.this.animationInProgress = false;
            }
        }

        private class ColorPicker extends FrameLayout {
            private float alpha = 1.0f;
            private LinearGradient alphaGradient;
            private boolean alphaPressed;
            private Drawable circleDrawable;
            private Paint circlePaint;
            private boolean circlePressed;
            private EditTextBoldCursor[] colorEditText = new EditTextBoldCursor[4];
            private LinearGradient colorGradient;
            private float[] colorHSV = new float[]{0.0f, 0.0f, 1.0f};
            private boolean colorPressed;
            private Bitmap colorWheelBitmap;
            private Paint colorWheelPaint;
            private int colorWheelRadius;
            private DecelerateInterpolator decelerateInterpolator = new DecelerateInterpolator();
            private float[] hsvTemp = new float[3];
            private LinearLayout linearLayout;
            private final int paramValueSliderWidth = AndroidUtilities.dp(20.0f);
            private Paint valueSliderPaint;

            public ColorPicker(Context context) {
                super(context);
                setWillNotDraw(false);
                this.circlePaint = new Paint(1);
                this.circleDrawable = context.getResources().getDrawable(R.drawable.knob_shadow).mutate();
                this.colorWheelPaint = new Paint();
                this.colorWheelPaint.setAntiAlias(true);
                this.colorWheelPaint.setDither(true);
                this.valueSliderPaint = new Paint();
                this.valueSliderPaint.setAntiAlias(true);
                this.valueSliderPaint.setDither(true);
                this.linearLayout = new LinearLayout(context);
                this.linearLayout.setOrientation(0);
                addView(this.linearLayout, LayoutHelper.createFrame(-2, -2, 49));
                int a = 0;
                while (a < 4) {
                    this.colorEditText[a] = new EditTextBoldCursor(context);
                    this.colorEditText[a].setInputType(2);
                    this.colorEditText[a].setTextColor(-14606047);
                    this.colorEditText[a].setCursorColor(-14606047);
                    this.colorEditText[a].setCursorSize(AndroidUtilities.dp(20.0f));
                    this.colorEditText[a].setCursorWidth(1.5f);
                    this.colorEditText[a].setTextSize(1, 18.0f);
                    this.colorEditText[a].setBackgroundDrawable(Theme.createEditTextDrawable(context, true));
                    this.colorEditText[a].setMaxLines(1);
                    this.colorEditText[a].setTag(Integer.valueOf(a));
                    this.colorEditText[a].setGravity(17);
                    if (a == 0) {
                        this.colorEditText[a].setHint("red");
                    } else if (a == 1) {
                        this.colorEditText[a].setHint("green");
                    } else if (a == 2) {
                        this.colorEditText[a].setHint("blue");
                    } else if (a == 3) {
                        this.colorEditText[a].setHint("alpha");
                    }
                    this.colorEditText[a].setImeOptions((a == 3 ? 6 : 5) | 268435456);
                    this.colorEditText[a].setFilters(new InputFilter[]{new LengthFilter(3)});
                    final int num = a;
                    this.linearLayout.addView(this.colorEditText[a], LayoutHelper.createLinear(55, 36, 0.0f, 0.0f, a != 3 ? 16.0f : 0.0f, 0.0f));
                    this.colorEditText[a].addTextChangedListener(new TextWatcher(EditorAlert.this) {
                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void afterTextChanged(Editable editable) {
                            if (!EditorAlert.this.ignoreTextChange) {
                                EditorAlert.this.ignoreTextChange = true;
                                int color = Utilities.parseInt(editable.toString()).intValue();
                                if (color < 0) {
                                    color = 0;
                                    ColorPicker.this.colorEditText[num].setText("" + 0);
                                    ColorPicker.this.colorEditText[num].setSelection(ColorPicker.this.colorEditText[num].length());
                                } else if (color > 255) {
                                    color = 255;
                                    ColorPicker.this.colorEditText[num].setText("" + 255);
                                    ColorPicker.this.colorEditText[num].setSelection(ColorPicker.this.colorEditText[num].length());
                                }
                                int currentColor = ColorPicker.this.getColor();
                                if (num == 2) {
                                    currentColor = (currentColor & InputDeviceCompat.SOURCE_ANY) | (color & 255);
                                } else if (num == 1) {
                                    currentColor = (-65281 & currentColor) | ((color & 255) << 8);
                                } else if (num == 0) {
                                    currentColor = (-16711681 & currentColor) | ((color & 255) << 16);
                                } else if (num == 3) {
                                    currentColor = (16777215 & currentColor) | ((color & 255) << 24);
                                }
                                ColorPicker.this.setColor(currentColor);
                                for (int a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                                    ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a)).setColor(ColorPicker.this.getColor(), false);
                                }
                                EditorAlert.this.ignoreTextChange = false;
                            }
                        }
                    });
                    this.colorEditText[a].setOnEditorActionListener(new OnEditorActionListener(EditorAlert.this) {
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if (i != 6) {
                                return false;
                            }
                            AndroidUtilities.hideKeyboard(textView);
                            return true;
                        }
                    });
                    a++;
                }
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int size = Math.min(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
                measureChild(this.linearLayout, widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(size, size);
            }

            protected void onDraw(Canvas canvas) {
                int centerX = (getWidth() / 2) - (this.paramValueSliderWidth * 2);
                int centerY = (getHeight() / 2) - AndroidUtilities.dp(8.0f);
                canvas.drawBitmap(this.colorWheelBitmap, (float) (centerX - this.colorWheelRadius), (float) (centerY - this.colorWheelRadius), null);
                float hueAngle = (float) Math.toRadians((double) this.colorHSV[0]);
                int colorPointX = ((int) (((-Math.cos((double) hueAngle)) * ((double) this.colorHSV[1])) * ((double) this.colorWheelRadius))) + centerX;
                int colorPointY = ((int) (((-Math.sin((double) hueAngle)) * ((double) this.colorHSV[1])) * ((double) this.colorWheelRadius))) + centerY;
                float pointerRadius = 0.075f * ((float) this.colorWheelRadius);
                this.hsvTemp[0] = this.colorHSV[0];
                this.hsvTemp[1] = this.colorHSV[1];
                this.hsvTemp[2] = 1.0f;
                drawPointerArrow(canvas, colorPointX, colorPointY, Color.HSVToColor(this.hsvTemp));
                int x = (this.colorWheelRadius + centerX) + this.paramValueSliderWidth;
                int y = centerY - this.colorWheelRadius;
                int width = AndroidUtilities.dp(9.0f);
                int height = this.colorWheelRadius * 2;
                if (this.colorGradient == null) {
                    this.colorGradient = new LinearGradient((float) x, (float) y, (float) (x + width), (float) (y + height), new int[]{-16777216, Color.HSVToColor(this.hsvTemp)}, null, TileMode.CLAMP);
                }
                this.valueSliderPaint.setShader(this.colorGradient);
                canvas.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), this.valueSliderPaint);
                drawPointerArrow(canvas, (width / 2) + x, (int) (((float) y) + (this.colorHSV[2] * ((float) height))), Color.HSVToColor(this.colorHSV));
                x += this.paramValueSliderWidth * 2;
                if (this.alphaGradient == null) {
                    int color = Color.HSVToColor(this.hsvTemp);
                    this.alphaGradient = new LinearGradient((float) x, (float) y, (float) (x + width), (float) (y + height), new int[]{color, 16777215 & color}, null, TileMode.CLAMP);
                }
                this.valueSliderPaint.setShader(this.alphaGradient);
                canvas.drawRect((float) x, (float) y, (float) (x + width), (float) (y + height), this.valueSliderPaint);
                drawPointerArrow(canvas, (width / 2) + x, (int) (((float) y) + ((1.0f - this.alpha) * ((float) height))), (Color.HSVToColor(this.colorHSV) & 16777215) | (((int) (255.0f * this.alpha)) << 24));
            }

            private void drawPointerArrow(Canvas canvas, int x, int y, int color) {
                int side = AndroidUtilities.dp(13.0f);
                this.circleDrawable.setBounds(x - side, y - side, x + side, y + side);
                this.circleDrawable.draw(canvas);
                this.circlePaint.setColor(-1);
                canvas.drawCircle((float) x, (float) y, (float) AndroidUtilities.dp(11.0f), this.circlePaint);
                this.circlePaint.setColor(color);
                canvas.drawCircle((float) x, (float) y, (float) AndroidUtilities.dp(9.0f), this.circlePaint);
            }

            protected void onSizeChanged(int width, int height, int oldw, int oldh) {
                this.colorWheelRadius = Math.max(1, ((width / 2) - (this.paramValueSliderWidth * 2)) - AndroidUtilities.dp(20.0f));
                this.colorWheelBitmap = createColorWheelBitmap(this.colorWheelRadius * 2, this.colorWheelRadius * 2);
                this.colorGradient = null;
                this.alphaGradient = null;
            }

            private Bitmap createColorWheelBitmap(int width, int height) {
                Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
                int[] colors = new int[13];
                float[] hsv = new float[]{0.0f, 1.0f, 1.0f};
                for (int i = 0; i < colors.length; i++) {
                    hsv[0] = (float) (((i * 30) + 180) % 360);
                    colors[i] = Color.HSVToColor(hsv);
                }
                colors[12] = colors[0];
                this.colorWheelPaint.setShader(new ComposeShader(new SweepGradient((float) (width / 2), (float) (height / 2), colors, null), new RadialGradient((float) (width / 2), (float) (height / 2), (float) this.colorWheelRadius, -1, 16777215, TileMode.CLAMP), Mode.SRC_OVER));
                new Canvas(bitmap).drawCircle((float) (width / 2), (float) (height / 2), (float) this.colorWheelRadius, this.colorWheelPaint);
                return bitmap;
            }

            private void startColorChange(boolean start) {
                if (EditorAlert.this.startedColorChange != start) {
                    if (EditorAlert.this.colorChangeAnimation != null) {
                        EditorAlert.this.colorChangeAnimation.cancel();
                    }
                    EditorAlert.this.startedColorChange = start;
                    EditorAlert.this.colorChangeAnimation = new AnimatorSet();
                    AnimatorSet access$400 = EditorAlert.this.colorChangeAnimation;
                    Animator[] animatorArr = new Animator[2];
                    ColorDrawable access$500 = EditorAlert.this.backDrawable;
                    String str = "alpha";
                    int[] iArr = new int[1];
                    iArr[0] = start ? 0 : 51;
                    animatorArr[0] = ObjectAnimator.ofInt(access$500, str, iArr);
                    ViewGroup access$600 = EditorAlert.this.containerView;
                    str = "alpha";
                    float[] fArr = new float[1];
                    fArr[0] = start ? 0.2f : 1.0f;
                    animatorArr[1] = ObjectAnimator.ofFloat(access$600, str, fArr);
                    access$400.playTogether(animatorArr);
                    EditorAlert.this.colorChangeAnimation.setDuration(150);
                    EditorAlert.this.colorChangeAnimation.setInterpolator(this.decelerateInterpolator);
                    EditorAlert.this.colorChangeAnimation.start();
                }
            }

            public boolean onTouchEvent(MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                    case 2:
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        int centerX = (getWidth() / 2) - (this.paramValueSliderWidth * 2);
                        int centerY = (getHeight() / 2) - AndroidUtilities.dp(8.0f);
                        int cx = x - centerX;
                        int cy = y - centerY;
                        double d = Math.sqrt((double) ((cx * cx) + (cy * cy)));
                        if (this.circlePressed || !(this.alphaPressed || this.colorPressed || d > ((double) this.colorWheelRadius))) {
                            if (d > ((double) this.colorWheelRadius)) {
                                d = (double) this.colorWheelRadius;
                            }
                            this.circlePressed = true;
                            this.colorHSV[0] = (float) (Math.toDegrees(Math.atan2((double) cy, (double) cx)) + 180.0d);
                            this.colorHSV[1] = Math.max(0.0f, Math.min(1.0f, (float) (d / ((double) this.colorWheelRadius))));
                            this.colorGradient = null;
                            this.alphaGradient = null;
                        }
                        if (this.colorPressed || (!this.circlePressed && !this.alphaPressed && x >= (this.colorWheelRadius + centerX) + this.paramValueSliderWidth && x <= (this.colorWheelRadius + centerX) + (this.paramValueSliderWidth * 2) && y >= centerY - this.colorWheelRadius && y <= this.colorWheelRadius + centerY)) {
                            float value = ((float) (y - (centerY - this.colorWheelRadius))) / (((float) this.colorWheelRadius) * 2.0f);
                            if (value < 0.0f) {
                                value = 0.0f;
                            } else if (value > 1.0f) {
                                value = 1.0f;
                            }
                            this.colorHSV[2] = value;
                            this.colorPressed = true;
                        }
                        if (this.alphaPressed || (!this.circlePressed && !this.colorPressed && x >= (this.colorWheelRadius + centerX) + (this.paramValueSliderWidth * 3) && x <= (this.colorWheelRadius + centerX) + (this.paramValueSliderWidth * 4) && y >= centerY - this.colorWheelRadius && y <= this.colorWheelRadius + centerY)) {
                            this.alpha = 1.0f - (((float) (y - (centerY - this.colorWheelRadius))) / (((float) this.colorWheelRadius) * 2.0f));
                            if (this.alpha < 0.0f) {
                                this.alpha = 0.0f;
                            } else if (this.alpha > 1.0f) {
                                this.alpha = 1.0f;
                            }
                            this.alphaPressed = true;
                        }
                        if (this.alphaPressed || this.colorPressed || this.circlePressed) {
                            int a;
                            startColorChange(true);
                            int color = getColor();
                            for (a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                                ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a)).setColor(color, false);
                            }
                            int red = Color.red(color);
                            int green = Color.green(color);
                            int blue = Color.blue(color);
                            a = Color.alpha(color);
                            if (!EditorAlert.this.ignoreTextChange) {
                                EditorAlert.this.ignoreTextChange = true;
                                this.colorEditText[0].setText("" + red);
                                this.colorEditText[1].setText("" + green);
                                this.colorEditText[2].setText("" + blue);
                                this.colorEditText[3].setText("" + a);
                                for (int b = 0; b < 4; b++) {
                                    this.colorEditText[b].setSelection(this.colorEditText[b].length());
                                }
                                EditorAlert.this.ignoreTextChange = false;
                            }
                            invalidate();
                        }
                        return true;
                    case 1:
                        this.alphaPressed = false;
                        this.colorPressed = false;
                        this.circlePressed = false;
                        startColorChange(false);
                        break;
                }
                return super.onTouchEvent(event);
            }

            public void setColor(int color) {
                int red = Color.red(color);
                int green = Color.green(color);
                int blue = Color.blue(color);
                int a = Color.alpha(color);
                if (!EditorAlert.this.ignoreTextChange) {
                    EditorAlert.this.ignoreTextChange = true;
                    this.colorEditText[0].setText("" + red);
                    this.colorEditText[1].setText("" + green);
                    this.colorEditText[2].setText("" + blue);
                    this.colorEditText[3].setText("" + a);
                    for (int b = 0; b < 4; b++) {
                        this.colorEditText[b].setSelection(this.colorEditText[b].length());
                    }
                    EditorAlert.this.ignoreTextChange = false;
                }
                this.alphaGradient = null;
                this.colorGradient = null;
                this.alpha = ((float) a) / 255.0f;
                Color.colorToHSV(color, this.colorHSV);
                invalidate();
            }

            public int getColor() {
                return (Color.HSVToColor(this.colorHSV) & 16777215) | (((int) (this.alpha * 255.0f)) << 24);
            }
        }

        private class ListAdapter extends SelectionAdapter {
            private Context context;
            private int currentCount;
            private ArrayList<ArrayList<ThemeDescription>> items = new ArrayList();
            private HashMap<String, ArrayList<ThemeDescription>> itemsMap = new HashMap();

            public ListAdapter(Context context, ThemeDescription[] descriptions) {
                this.context = context;
                for (ThemeDescription description : descriptions) {
                    String key = description.getCurrentKey();
                    ArrayList<ThemeDescription> arrayList = (ArrayList) this.itemsMap.get(key);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.itemsMap.put(key, arrayList);
                        this.items.add(arrayList);
                    }
                    arrayList.add(description);
                }
            }

            public int getItemCount() {
                return this.items.size();
            }

            public ArrayList<ThemeDescription> getItem(int i) {
                if (i < 0 || i >= this.items.size()) {
                    return null;
                }
                return (ArrayList) this.items.get(i);
            }

            public boolean isEnabled(ViewHolder holder) {
                return true;
            }

            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = new TextColorThemeCell(this.context);
                view.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                return new Holder(view);
            }

            public void onBindViewHolder(ViewHolder holder, int position) {
                int color;
                ThemeDescription description = (ThemeDescription) ((ArrayList) this.items.get(position)).get(0);
                if (description.getCurrentKey().equals(Theme.key_chat_wallpaper)) {
                    color = 0;
                } else {
                    color = description.getSetColor();
                }
                ((TextColorThemeCell) holder.itemView).setTextAndColor(description.getTitle(), color);
            }

            public int getItemViewType(int i) {
                return 0;
            }
        }

        public EditorAlert(Context context, ThemeDescription[] items) {
            super(context, true);
            this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
            this.containerView = new FrameLayout(context, ThemeEditorView.this) {
                private boolean ignoreLayout = false;

                public boolean onInterceptTouchEvent(MotionEvent ev) {
                    if (ev.getAction() != 0 || EditorAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) EditorAlert.this.scrollOffsetY)) {
                        return super.onInterceptTouchEvent(ev);
                    }
                    EditorAlert.this.dismiss();
                    return true;
                }

                public boolean onTouchEvent(MotionEvent e) {
                    return !EditorAlert.this.isDismissed() && super.onTouchEvent(e);
                }

                protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                    int width = MeasureSpec.getSize(widthMeasureSpec);
                    int height = MeasureSpec.getSize(heightMeasureSpec);
                    if (VERSION.SDK_INT >= 21) {
                        height -= AndroidUtilities.statusBarHeight;
                    }
                    int padding = height - Math.min(width, height);
                    if (EditorAlert.this.listView.getPaddingTop() != padding) {
                        this.ignoreLayout = true;
                        int previousPadding = EditorAlert.this.listView.getPaddingTop();
                        EditorAlert.this.listView.setPadding(0, padding, 0, AndroidUtilities.dp(48.0f));
                        if (EditorAlert.this.colorPicker.getVisibility() == 0) {
                            EditorAlert.this.scrollOffsetY = EditorAlert.this.listView.getPaddingTop();
                            EditorAlert.this.listView.setTopGlowOffset(EditorAlert.this.scrollOffsetY);
                            EditorAlert.this.colorPicker.setTranslationY((float) EditorAlert.this.scrollOffsetY);
                            EditorAlert.this.previousScrollPosition = 0;
                        }
                        this.ignoreLayout = false;
                    }
                    super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(height, 1073741824));
                }

                protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                    super.onLayout(changed, left, top, right, bottom);
                    EditorAlert.this.updateLayout();
                }

                public void requestLayout() {
                    if (!this.ignoreLayout) {
                        super.requestLayout();
                    }
                }

                protected void onDraw(Canvas canvas) {
                    EditorAlert.this.shadowDrawable.setBounds(0, EditorAlert.this.scrollOffsetY - EditorAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                    EditorAlert.this.shadowDrawable.draw(canvas);
                }
            };
            this.containerView.setWillNotDraw(false);
            this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
            this.listView = new RecyclerListView(context);
            this.listView.setPadding(0, 0, 0, AndroidUtilities.dp(48.0f));
            this.listView.setClipToPadding(false);
            RecyclerListView recyclerListView = this.listView;
            LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            this.layoutManager = linearLayoutManager;
            recyclerListView.setLayoutManager(linearLayoutManager);
            this.listView.setHorizontalScrollBarEnabled(false);
            this.listView.setVerticalScrollBarEnabled(false);
            this.containerView.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
            recyclerListView = this.listView;
            Adapter listAdapter = new ListAdapter(context, items);
            this.listAdapter = listAdapter;
            recyclerListView.setAdapter(listAdapter);
            this.listView.setGlowColor(-657673);
            this.listView.setItemAnimator(null);
            this.listView.setLayoutAnimation(null);
            this.listView.setOnItemClickListener(new OnItemClickListener(ThemeEditorView.this) {
                public void onItemClick(View view, int position) {
                    ThemeEditorView.this.currentThemeDesription = EditorAlert.this.listAdapter.getItem(position);
                    ThemeEditorView.this.currentThemeDesriptionPosition = position;
                    for (int a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                        ThemeDescription description = (ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a);
                        if (description.getCurrentKey().equals(Theme.key_chat_wallpaper)) {
                            ThemeEditorView.this.wallpaperUpdater.showAlert(true);
                            return;
                        }
                        description.startEditing();
                        if (a == 0) {
                            EditorAlert.this.colorPicker.setColor(description.getCurrentColor());
                        }
                    }
                    EditorAlert.this.setColorPickerVisible(true);
                }
            });
            this.listView.setOnScrollListener(new OnScrollListener(ThemeEditorView.this) {
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    EditorAlert.this.updateLayout();
                }
            });
            this.colorPicker = new ColorPicker(context);
            this.colorPicker.setVisibility(8);
            this.containerView.addView(this.colorPicker, LayoutHelper.createFrame(-1, -1, 1));
            this.shadow = new View(context);
            this.shadow.setBackgroundResource(R.drawable.header_shadow_reverse);
            this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 83, 0.0f, 0.0f, 0.0f, 48.0f));
            this.bottomSaveLayout = new FrameLayout(context);
            this.bottomSaveLayout.setBackgroundColor(-1);
            this.containerView.addView(this.bottomSaveLayout, LayoutHelper.createFrame(-1, 48, 83));
            TextView closeButton = new TextView(context);
            closeButton.setTextSize(1, 14.0f);
            closeButton.setTextColor(-15095832);
            closeButton.setGravity(17);
            closeButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            closeButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            closeButton.setText(LocaleController.getString("CloseEditor", R.string.CloseEditor).toUpperCase());
            closeButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.bottomSaveLayout.addView(closeButton, LayoutHelper.createFrame(-2, -1, 51));
            closeButton.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View v) {
                    EditorAlert.this.dismiss();
                }
            });
            TextView saveButton = new TextView(context);
            saveButton.setTextSize(1, 14.0f);
            saveButton.setTextColor(-15095832);
            saveButton.setGravity(17);
            saveButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            saveButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            saveButton.setText(LocaleController.getString("SaveTheme", R.string.SaveTheme).toUpperCase());
            saveButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.bottomSaveLayout.addView(saveButton, LayoutHelper.createFrame(-2, -1, 53));
            saveButton.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View v) {
                    Theme.saveCurrentTheme(ThemeEditorView.this.currentThemeName, true);
                    EditorAlert.this.setOnDismissListener(null);
                    EditorAlert.this.dismiss();
                    ThemeEditorView.this.close();
                }
            });
            this.bottomLayout = new FrameLayout(context);
            this.bottomLayout.setVisibility(8);
            this.bottomLayout.setBackgroundColor(-1);
            this.containerView.addView(this.bottomLayout, LayoutHelper.createFrame(-1, 48, 83));
            this.cancelButton = new TextView(context);
            this.cancelButton.setTextSize(1, 14.0f);
            this.cancelButton.setTextColor(-15095832);
            this.cancelButton.setGravity(17);
            this.cancelButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            this.cancelButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            this.cancelButton.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
            this.cancelButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.bottomLayout.addView(this.cancelButton, LayoutHelper.createFrame(-2, -1, 51));
            this.cancelButton.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View v) {
                    for (int a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                        ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a)).setPreviousColor();
                    }
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setOrientation(0);
            this.bottomLayout.addView(linearLayout, LayoutHelper.createFrame(-2, -1, 53));
            this.defaultButtom = new TextView(context);
            this.defaultButtom.setTextSize(1, 14.0f);
            this.defaultButtom.setTextColor(-15095832);
            this.defaultButtom.setGravity(17);
            this.defaultButtom.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            this.defaultButtom.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            this.defaultButtom.setText(LocaleController.getString("Default", R.string.Default).toUpperCase());
            this.defaultButtom.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            linearLayout.addView(this.defaultButtom, LayoutHelper.createFrame(-2, -1, 51));
            this.defaultButtom.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View v) {
                    for (int a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                        ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a)).setDefaultColor();
                    }
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
            saveButton = new TextView(context);
            saveButton.setTextSize(1, 14.0f);
            saveButton.setTextColor(-15095832);
            saveButton.setGravity(17);
            saveButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            saveButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            saveButton.setText(LocaleController.getString("Save", R.string.Save).toUpperCase());
            saveButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            linearLayout.addView(saveButton, LayoutHelper.createFrame(-2, -1, 51));
            saveButton.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View v) {
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
        }

        private void setColorPickerVisible(boolean visible) {
            if (visible) {
                this.animationInProgress = true;
                this.colorPicker.setVisibility(0);
                this.bottomLayout.setVisibility(0);
                this.colorPicker.setAlpha(0.0f);
                this.bottomLayout.setAlpha(0.0f);
                AnimatorSet animatorSet = new AnimatorSet();
                r1 = new Animator[5];
                r1[0] = ObjectAnimator.ofFloat(this.colorPicker, "alpha", new float[]{1.0f});
                r1[1] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{1.0f});
                r1[2] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{0.0f});
                r1[3] = ObjectAnimator.ofFloat(this.bottomSaveLayout, "alpha", new float[]{0.0f});
                r1[4] = ObjectAnimator.ofInt(this, "scrollOffsetY", new int[]{this.listView.getPaddingTop()});
                animatorSet.playTogether(r1);
                animatorSet.setDuration(150);
                animatorSet.setInterpolator(ThemeEditorView.this.decelerateInterpolator);
                animatorSet.addListener(new C27979());
                animatorSet.start();
                this.previousScrollPosition = this.scrollOffsetY;
                return;
            }
            if (ThemeEditorView.this.parentActivity != null) {
                ((LaunchActivity) ThemeEditorView.this.parentActivity).rebuildAllFragments(false);
            }
            Theme.saveCurrentTheme(ThemeEditorView.this.currentThemeName, false);
            AndroidUtilities.hideKeyboard(getCurrentFocus());
            this.animationInProgress = true;
            this.listView.setVisibility(0);
            this.bottomSaveLayout.setVisibility(0);
            this.listView.setAlpha(0.0f);
            animatorSet = new AnimatorSet();
            r1 = new Animator[5];
            r1[0] = ObjectAnimator.ofFloat(this.colorPicker, "alpha", new float[]{0.0f});
            r1[1] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{0.0f});
            r1[2] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{1.0f});
            r1[3] = ObjectAnimator.ofFloat(this.bottomSaveLayout, "alpha", new float[]{1.0f});
            r1[4] = ObjectAnimator.ofInt(this, "scrollOffsetY", new int[]{this.previousScrollPosition});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(150);
            animatorSet.setInterpolator(ThemeEditorView.this.decelerateInterpolator);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    EditorAlert.this.colorPicker.setVisibility(8);
                    EditorAlert.this.bottomLayout.setVisibility(8);
                    EditorAlert.this.animationInProgress = false;
                }
            });
            animatorSet.start();
            this.listAdapter.notifyItemChanged(ThemeEditorView.this.currentThemeDesriptionPosition);
        }

        private int getCurrentTop() {
            int i = 0;
            if (this.listView.getChildCount() != 0) {
                View child = this.listView.getChildAt(0);
                Holder holder = (Holder) this.listView.findContainingViewHolder(child);
                if (holder != null) {
                    int paddingTop = this.listView.getPaddingTop();
                    if (holder.getAdapterPosition() == 0 && child.getTop() >= 0) {
                        i = child.getTop();
                    }
                    return paddingTop - i;
                }
            }
            return -1000;
        }

        protected boolean canDismissWithSwipe() {
            return false;
        }

        @SuppressLint({"NewApi"})
        private void updateLayout() {
            int newOffset = 0;
            if (this.listView.getChildCount() > 0 && this.listView.getVisibility() == 0 && !this.animationInProgress) {
                int top;
                View child = this.listView.getChildAt(0);
                Holder holder = (Holder) this.listView.findContainingViewHolder(child);
                if (this.listView.getVisibility() != 0 || this.animationInProgress) {
                    top = this.listView.getPaddingTop();
                } else {
                    top = child.getTop() - AndroidUtilities.dp(8.0f);
                }
                if (top > 0 && holder != null && holder.getAdapterPosition() == 0) {
                    newOffset = top;
                }
                if (this.scrollOffsetY != newOffset) {
                    setScrollOffsetY(newOffset);
                }
            }
        }

        public int getScrollOffsetY() {
            return this.scrollOffsetY;
        }

        @Keep
        public void setScrollOffsetY(int value) {
            RecyclerListView recyclerListView = this.listView;
            this.scrollOffsetY = value;
            recyclerListView.setTopGlowOffset(value);
            this.colorPicker.setTranslationY((float) this.scrollOffsetY);
            this.containerView.invalidate();
        }
    }

    public static ThemeEditorView getInstance() {
        return Instance;
    }

    public void destroy() {
        this.wallpaperUpdater.cleanup();
        if (this.parentActivity != null && this.windowView != null) {
            try {
                this.windowManager.removeViewImmediate(this.windowView);
                this.windowView = null;
            } catch (Exception e) {
                FileLog.e(e);
            }
            try {
                if (this.editorAlert != null) {
                    this.editorAlert.dismiss();
                    this.editorAlert = null;
                }
            } catch (Exception e2) {
                FileLog.e(e2);
            }
            this.parentActivity = null;
            Instance = null;
        }
    }

    public void show(Activity activity, final String themeName) {
        if (Instance != null) {
            Instance.destroy();
        }
        this.hidden = false;
        this.currentThemeName = themeName;
        this.windowView = new FrameLayout(activity) {
            private boolean dragging;
            private float startX;
            private float startY;

            /* renamed from: org.telegram.ui.Components.ThemeEditorView$1$1 */
            class C27831 implements OnDismissListener {
                C27831() {
                }

                public void onDismiss(DialogInterface dialog) {
                }
            }

            /* renamed from: org.telegram.ui.Components.ThemeEditorView$1$2 */
            class C27842 implements OnDismissListener {
                C27842() {
                }

                public void onDismiss(DialogInterface dialog) {
                    ThemeEditorView.this.editorAlert = null;
                    ThemeEditorView.this.show();
                }
            }

            public boolean onInterceptTouchEvent(MotionEvent event) {
                return true;
            }

            public boolean onTouchEvent(MotionEvent event) {
                float x = event.getRawX();
                float y = event.getRawY();
                if (event.getAction() == 0) {
                    this.startX = x;
                    this.startY = y;
                } else if (event.getAction() != 2 || this.dragging) {
                    if (event.getAction() == 1 && !this.dragging && ThemeEditorView.this.editorAlert == null) {
                        ActionBarLayout actionBarLayout = ((LaunchActivity) ThemeEditorView.this.parentActivity).getActionBarLayout();
                        if (!actionBarLayout.fragmentsStack.isEmpty()) {
                            ThemeDescription[] items = ((BaseFragment) actionBarLayout.fragmentsStack.get(actionBarLayout.fragmentsStack.size() - 1)).getThemeDescriptions();
                            if (items != null) {
                                ThemeEditorView.this.editorAlert = new EditorAlert(ThemeEditorView.this.parentActivity, items);
                                ThemeEditorView.this.editorAlert.setOnDismissListener(new C27831());
                                ThemeEditorView.this.editorAlert.setOnDismissListener(new C27842());
                                ThemeEditorView.this.editorAlert.show();
                                ThemeEditorView.this.hide();
                            }
                        }
                    }
                } else if (Math.abs(this.startX - x) >= AndroidUtilities.getPixelsInCM(0.3f, true) || Math.abs(this.startY - y) >= AndroidUtilities.getPixelsInCM(0.3f, false)) {
                    this.dragging = true;
                    this.startX = x;
                    this.startY = y;
                }
                if (this.dragging) {
                    if (event.getAction() == 2) {
                        float dx = x - this.startX;
                        float dy = y - this.startY;
                        LayoutParams access$2700 = ThemeEditorView.this.windowLayoutParams;
                        access$2700.x = (int) (((float) access$2700.x) + dx);
                        access$2700 = ThemeEditorView.this.windowLayoutParams;
                        access$2700.y = (int) (((float) access$2700.y) + dy);
                        int maxDiff = ThemeEditorView.this.editorWidth / 2;
                        if (ThemeEditorView.this.windowLayoutParams.x < (-maxDiff)) {
                            ThemeEditorView.this.windowLayoutParams.x = -maxDiff;
                        } else if (ThemeEditorView.this.windowLayoutParams.x > (AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width) + maxDiff) {
                            ThemeEditorView.this.windowLayoutParams.x = (AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width) + maxDiff;
                        }
                        float alpha = 1.0f;
                        if (ThemeEditorView.this.windowLayoutParams.x < 0) {
                            alpha = 1.0f + ((((float) ThemeEditorView.this.windowLayoutParams.x) / ((float) maxDiff)) * 0.5f);
                        } else if (ThemeEditorView.this.windowLayoutParams.x > AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width) {
                            alpha = 1.0f - ((((float) ((ThemeEditorView.this.windowLayoutParams.x - AndroidUtilities.displaySize.x) + ThemeEditorView.this.windowLayoutParams.width)) / ((float) maxDiff)) * 0.5f);
                        }
                        if (ThemeEditorView.this.windowView.getAlpha() != alpha) {
                            ThemeEditorView.this.windowView.setAlpha(alpha);
                        }
                        if (ThemeEditorView.this.windowLayoutParams.y < (-null)) {
                            ThemeEditorView.this.windowLayoutParams.y = -null;
                        } else if (ThemeEditorView.this.windowLayoutParams.y > (AndroidUtilities.displaySize.y - ThemeEditorView.this.windowLayoutParams.height) + 0) {
                            ThemeEditorView.this.windowLayoutParams.y = (AndroidUtilities.displaySize.y - ThemeEditorView.this.windowLayoutParams.height) + 0;
                        }
                        ThemeEditorView.this.windowManager.updateViewLayout(ThemeEditorView.this.windowView, ThemeEditorView.this.windowLayoutParams);
                        this.startX = x;
                        this.startY = y;
                    } else if (event.getAction() == 1) {
                        this.dragging = false;
                        ThemeEditorView.this.animateToBoundsMaybe();
                    }
                }
                return true;
            }
        };
        this.windowView.setBackgroundResource(R.drawable.theme_picker);
        this.windowManager = (WindowManager) activity.getSystemService("window");
        this.preferences = ApplicationLoader.applicationContext.getSharedPreferences("themeconfig", 0);
        int sidex = this.preferences.getInt("sidex", 1);
        int sidey = this.preferences.getInt("sidey", 0);
        float px = this.preferences.getFloat("px", 0.0f);
        float py = this.preferences.getFloat("py", 0.0f);
        try {
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.width = this.editorWidth;
            this.windowLayoutParams.height = this.editorHeight;
            this.windowLayoutParams.x = getSideCoord(true, sidex, px, this.editorWidth);
            this.windowLayoutParams.y = getSideCoord(false, sidey, py, this.editorHeight);
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.gravity = 51;
            this.windowLayoutParams.type = 99;
            this.windowLayoutParams.flags = 16777736;
            this.windowManager.addView(this.windowView, this.windowLayoutParams);
            this.wallpaperUpdater = new WallpaperUpdater(activity, new WallpaperUpdaterDelegate() {
                public void didSelectWallpaper(File file, Bitmap bitmap) {
                    Theme.setThemeWallpaper(themeName, bitmap, file);
                }

                public void needOpenColorPicker() {
                    for (int a = 0; a < ThemeEditorView.this.currentThemeDesription.size(); a++) {
                        ThemeDescription description = (ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(a);
                        description.startEditing();
                        if (a == 0) {
                            ThemeEditorView.this.editorAlert.colorPicker.setColor(description.getCurrentColor());
                        }
                    }
                    ThemeEditorView.this.editorAlert.setColorPickerVisible(true);
                }
            });
            Instance = this;
            this.parentActivity = activity;
            showWithAnimation();
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    private void showWithAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleX", new float[]{0.0f, 1.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleY", new float[]{0.0f, 1.0f})});
        animatorSet.setInterpolator(this.decelerateInterpolator);
        animatorSet.setDuration(150);
        animatorSet.start();
    }

    private static int getSideCoord(boolean isX, int side, float p, int sideSize) {
        int total;
        int result;
        if (isX) {
            total = AndroidUtilities.displaySize.x - sideSize;
        } else {
            total = (AndroidUtilities.displaySize.y - sideSize) - ActionBar.getCurrentActionBarHeight();
        }
        if (side == 0) {
            result = AndroidUtilities.dp(10.0f);
        } else if (side == 1) {
            result = total - AndroidUtilities.dp(10.0f);
        } else {
            result = Math.round(((float) (total - AndroidUtilities.dp(20.0f))) * p) + AndroidUtilities.dp(10.0f);
        }
        if (isX) {
            return result;
        }
        return result + ActionBar.getCurrentActionBarHeight();
    }

    private void hide() {
        if (this.parentActivity != null) {
            try {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f, 0.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleX", new float[]{1.0f, 0.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleY", new float[]{1.0f, 0.0f})});
                animatorSet.setInterpolator(this.decelerateInterpolator);
                animatorSet.setDuration(150);
                animatorSet.addListener(new C27873());
                animatorSet.start();
                this.hidden = true;
            } catch (Exception e) {
            }
        }
    }

    private void show() {
        if (this.parentActivity != null) {
            try {
                this.windowManager.addView(this.windowView, this.windowLayoutParams);
                this.hidden = false;
                showWithAnimation();
            } catch (Exception e) {
            }
        }
    }

    public void close() {
        try {
            this.windowManager.removeView(this.windowView);
        } catch (Exception e) {
        }
        this.parentActivity = null;
    }

    public void onConfigurationChanged() {
        int sidex = this.preferences.getInt("sidex", 1);
        int sidey = this.preferences.getInt("sidey", 0);
        float px = this.preferences.getFloat("px", 0.0f);
        float py = this.preferences.getFloat("py", 0.0f);
        this.windowLayoutParams.x = getSideCoord(true, sidex, px, this.editorWidth);
        this.windowLayoutParams.y = getSideCoord(false, sidey, py, this.editorHeight);
        try {
            if (this.windowView.getParent() != null) {
                this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
            }
        } catch (Exception e) {
            FileLog.e(e);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.wallpaperUpdater != null) {
            this.wallpaperUpdater.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void animateToBoundsMaybe() {
        int startX = getSideCoord(true, 0, 0.0f, this.editorWidth);
        int endX = getSideCoord(true, 1, 0.0f, this.editorWidth);
        int startY = getSideCoord(false, 0, 0.0f, this.editorHeight);
        int endY = getSideCoord(false, 1, 0.0f, this.editorHeight);
        ArrayList<Animator> animators = null;
        Editor editor = this.preferences.edit();
        int maxDiff = AndroidUtilities.dp(20.0f);
        boolean slideOut = false;
        if (Math.abs(startX - this.windowLayoutParams.x) <= maxDiff || (this.windowLayoutParams.x < 0 && this.windowLayoutParams.x > (-this.editorWidth) / 4)) {
            if (null == null) {
                animators = new ArrayList();
            }
            editor.putInt("sidex", 0);
            if (this.windowView.getAlpha() != 1.0f) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            animators.add(ObjectAnimator.ofInt(this, "x", new int[]{startX}));
        } else if (Math.abs(endX - this.windowLayoutParams.x) <= maxDiff || (this.windowLayoutParams.x > AndroidUtilities.displaySize.x - this.editorWidth && this.windowLayoutParams.x < AndroidUtilities.displaySize.x - ((this.editorWidth / 4) * 3))) {
            if (null == null) {
                animators = new ArrayList();
            }
            editor.putInt("sidex", 1);
            if (this.windowView.getAlpha() != 1.0f) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            animators.add(ObjectAnimator.ofInt(this, "x", new int[]{endX}));
        } else if (this.windowView.getAlpha() != 1.0f) {
            if (null == null) {
                animators = new ArrayList();
            }
            if (this.windowLayoutParams.x < 0) {
                animators.add(ObjectAnimator.ofInt(this, "x", new int[]{-this.editorWidth}));
            } else {
                animators.add(ObjectAnimator.ofInt(this, "x", new int[]{AndroidUtilities.displaySize.x}));
            }
            slideOut = true;
        } else {
            editor.putFloat("px", ((float) (this.windowLayoutParams.x - startX)) / ((float) (endX - startX)));
            editor.putInt("sidex", 2);
        }
        if (!slideOut) {
            if (Math.abs(startY - this.windowLayoutParams.y) <= maxDiff || this.windowLayoutParams.y <= ActionBar.getCurrentActionBarHeight()) {
                if (animators == null) {
                    animators = new ArrayList();
                }
                editor.putInt("sidey", 0);
                animators.add(ObjectAnimator.ofInt(this, "y", new int[]{startY}));
            } else if (Math.abs(endY - this.windowLayoutParams.y) <= maxDiff) {
                if (animators == null) {
                    animators = new ArrayList();
                }
                editor.putInt("sidey", 1);
                animators.add(ObjectAnimator.ofInt(this, "y", new int[]{endY}));
            } else {
                editor.putFloat("py", ((float) (this.windowLayoutParams.y - startY)) / ((float) (endY - startY)));
                editor.putInt("sidey", 2);
            }
            editor.commit();
        }
        if (animators != null) {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(this.decelerateInterpolator);
            animatorSet.setDuration(150);
            if (slideOut) {
                animators.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{0.0f}));
                animatorSet.addListener(new C27884());
            }
            animatorSet.playTogether(animators);
            animatorSet.start();
        }
    }

    public int getX() {
        return this.windowLayoutParams.x;
    }

    public int getY() {
        return this.windowLayoutParams.y;
    }

    @Keep
    public void setX(int value) {
        this.windowLayoutParams.x = value;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    @Keep
    public void setY(int value) {
        this.windowLayoutParams.y = value;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }
}
