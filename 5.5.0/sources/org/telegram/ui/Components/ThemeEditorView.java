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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
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
    class C46253 extends AnimatorListenerAdapter {
        C46253() {
        }

        public void onAnimationEnd(Animator animator) {
            if (ThemeEditorView.this.windowView != null) {
                ThemeEditorView.this.windowManager.removeView(ThemeEditorView.this.windowView);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.ThemeEditorView$4 */
    class C46264 extends AnimatorListenerAdapter {
        C46264() {
        }

        public void onAnimationEnd(Animator animator) {
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
        class C46359 extends AnimatorListenerAdapter {
            C46359() {
            }

            public void onAnimationEnd(Animator animator) {
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
            private float[] colorHSV = new float[]{BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f};
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
                int i = 0;
                while (i < 4) {
                    this.colorEditText[i] = new EditTextBoldCursor(context);
                    this.colorEditText[i].setInputType(2);
                    this.colorEditText[i].setTextColor(-14606047);
                    this.colorEditText[i].setCursorColor(-14606047);
                    this.colorEditText[i].setCursorSize(AndroidUtilities.dp(20.0f));
                    this.colorEditText[i].setCursorWidth(1.5f);
                    this.colorEditText[i].setTextSize(1, 18.0f);
                    this.colorEditText[i].setBackgroundDrawable(Theme.createEditTextDrawable(context, true));
                    this.colorEditText[i].setMaxLines(1);
                    this.colorEditText[i].setTag(Integer.valueOf(i));
                    this.colorEditText[i].setGravity(17);
                    if (i == 0) {
                        this.colorEditText[i].setHint("red");
                    } else if (i == 1) {
                        this.colorEditText[i].setHint("green");
                    } else if (i == 2) {
                        this.colorEditText[i].setHint("blue");
                    } else if (i == 3) {
                        this.colorEditText[i].setHint("alpha");
                    }
                    this.colorEditText[i].setImeOptions((i == 3 ? 6 : 5) | ErrorDialogData.BINDER_CRASH);
                    this.colorEditText[i].setFilters(new InputFilter[]{new LengthFilter(3)});
                    this.linearLayout.addView(this.colorEditText[i], LayoutHelper.createLinear(55, 36, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, i != 3 ? 16.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                    this.colorEditText[i].addTextChangedListener(new TextWatcher(EditorAlert.this) {
                        public void afterTextChanged(Editable editable) {
                            int i = 255;
                            if (!EditorAlert.this.ignoreTextChange) {
                                EditorAlert.this.ignoreTextChange = true;
                                int intValue = Utilities.parseInt(editable.toString()).intValue();
                                if (intValue < 0) {
                                    ColorPicker.this.colorEditText[i].setText(TtmlNode.ANONYMOUS_REGION_ID + 0);
                                    ColorPicker.this.colorEditText[i].setSelection(ColorPicker.this.colorEditText[i].length());
                                    i = 0;
                                } else if (intValue > 255) {
                                    ColorPicker.this.colorEditText[i].setText(TtmlNode.ANONYMOUS_REGION_ID + 255);
                                    ColorPicker.this.colorEditText[i].setSelection(ColorPicker.this.colorEditText[i].length());
                                } else {
                                    i = intValue;
                                }
                                intValue = ColorPicker.this.getColor();
                                if (i == 2) {
                                    i = (i & 255) | (intValue & -256);
                                } else if (i == 1) {
                                    i = ((i & 255) << 8) | (intValue & -65281);
                                } else if (i == 0) {
                                    i = ((i & 255) << 16) | (intValue & -16711681);
                                } else if (i == 3) {
                                    i = ((i & 255) << 24) | (intValue & 16777215);
                                } else {
                                    i = intValue;
                                }
                                ColorPicker.this.setColor(i);
                                for (intValue = 0; intValue < ThemeEditorView.this.currentThemeDesription.size(); intValue++) {
                                    ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(intValue)).setColor(ColorPicker.this.getColor(), false);
                                }
                                EditorAlert.this.ignoreTextChange = false;
                            }
                        }

                        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }

                        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                        }
                    });
                    this.colorEditText[i].setOnEditorActionListener(new OnEditorActionListener(EditorAlert.this) {
                        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                            if (i != 6) {
                                return false;
                            }
                            AndroidUtilities.hideKeyboard(textView);
                            return true;
                        }
                    });
                    i++;
                }
            }

            private Bitmap createColorWheelBitmap(int i, int i2) {
                Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.ARGB_8888);
                int[] iArr = new int[13];
                float[] fArr = new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f, 1.0f};
                for (int i3 = 0; i3 < iArr.length; i3++) {
                    fArr[0] = (float) (((i3 * 30) + 180) % 360);
                    iArr[i3] = Color.HSVToColor(fArr);
                }
                iArr[12] = iArr[0];
                this.colorWheelPaint.setShader(new ComposeShader(new SweepGradient((float) (i / 2), (float) (i2 / 2), iArr, null), new RadialGradient((float) (i / 2), (float) (i2 / 2), (float) this.colorWheelRadius, -1, 16777215, TileMode.CLAMP), Mode.SRC_OVER));
                new Canvas(createBitmap).drawCircle((float) (i / 2), (float) (i2 / 2), (float) this.colorWheelRadius, this.colorWheelPaint);
                return createBitmap;
            }

            private void drawPointerArrow(Canvas canvas, int i, int i2, int i3) {
                int dp = AndroidUtilities.dp(13.0f);
                this.circleDrawable.setBounds(i - dp, i2 - dp, i + dp, dp + i2);
                this.circleDrawable.draw(canvas);
                this.circlePaint.setColor(-1);
                canvas.drawCircle((float) i, (float) i2, (float) AndroidUtilities.dp(11.0f), this.circlePaint);
                this.circlePaint.setColor(i3);
                canvas.drawCircle((float) i, (float) i2, (float) AndroidUtilities.dp(9.0f), this.circlePaint);
            }

            private void startColorChange(boolean z) {
                if (EditorAlert.this.startedColorChange != z) {
                    if (EditorAlert.this.colorChangeAnimation != null) {
                        EditorAlert.this.colorChangeAnimation.cancel();
                    }
                    EditorAlert.this.startedColorChange = z;
                    EditorAlert.this.colorChangeAnimation = new AnimatorSet();
                    AnimatorSet access$400 = EditorAlert.this.colorChangeAnimation;
                    Animator[] animatorArr = new Animator[2];
                    ColorDrawable access$500 = EditorAlert.this.backDrawable;
                    String str = "alpha";
                    int[] iArr = new int[1];
                    iArr[0] = z ? 0 : 51;
                    animatorArr[0] = ObjectAnimator.ofInt(access$500, str, iArr);
                    ViewGroup access$600 = EditorAlert.this.containerView;
                    str = "alpha";
                    float[] fArr = new float[1];
                    fArr[0] = z ? 0.2f : 1.0f;
                    animatorArr[1] = ObjectAnimator.ofFloat(access$600, str, fArr);
                    access$400.playTogether(animatorArr);
                    EditorAlert.this.colorChangeAnimation.setDuration(150);
                    EditorAlert.this.colorChangeAnimation.setInterpolator(this.decelerateInterpolator);
                    EditorAlert.this.colorChangeAnimation.start();
                }
            }

            public int getColor() {
                return (Color.HSVToColor(this.colorHSV) & 16777215) | (((int) (this.alpha * 255.0f)) << 24);
            }

            protected void onDraw(Canvas canvas) {
                int width = (getWidth() / 2) - (this.paramValueSliderWidth * 2);
                int height = (getHeight() / 2) - AndroidUtilities.dp(8.0f);
                canvas.drawBitmap(this.colorWheelBitmap, (float) (width - this.colorWheelRadius), (float) (height - this.colorWheelRadius), null);
                float toRadians = (float) Math.toRadians((double) this.colorHSV[0]);
                int i = ((int) (((-Math.cos((double) toRadians)) * ((double) this.colorHSV[1])) * ((double) this.colorWheelRadius))) + width;
                int i2 = ((int) (((-Math.sin((double) toRadians)) * ((double) this.colorHSV[1])) * ((double) this.colorWheelRadius))) + height;
                float f = 0.075f * ((float) this.colorWheelRadius);
                this.hsvTemp[0] = this.colorHSV[0];
                this.hsvTemp[1] = this.colorHSV[1];
                this.hsvTemp[2] = 1.0f;
                drawPointerArrow(canvas, i, i2, Color.HSVToColor(this.hsvTemp));
                int i3 = (width + this.colorWheelRadius) + this.paramValueSliderWidth;
                int i4 = height - this.colorWheelRadius;
                int dp = AndroidUtilities.dp(9.0f);
                int i5 = this.colorWheelRadius * 2;
                if (this.colorGradient == null) {
                    this.colorGradient = new LinearGradient((float) i3, (float) i4, (float) (i3 + dp), (float) (i4 + i5), new int[]{Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Color.HSVToColor(this.hsvTemp)}, null, TileMode.CLAMP);
                }
                this.valueSliderPaint.setShader(this.colorGradient);
                canvas.drawRect((float) i3, (float) i4, (float) (i3 + dp), (float) (i4 + i5), this.valueSliderPaint);
                drawPointerArrow(canvas, (dp / 2) + i3, (int) (((float) i4) + (this.colorHSV[2] * ((float) i5))), Color.HSVToColor(this.colorHSV));
                i3 += this.paramValueSliderWidth * 2;
                if (this.alphaGradient == null) {
                    int HSVToColor = Color.HSVToColor(this.hsvTemp);
                    this.alphaGradient = new LinearGradient((float) i3, (float) i4, (float) (i3 + dp), (float) (i4 + i5), new int[]{HSVToColor, HSVToColor & 16777215}, null, TileMode.CLAMP);
                }
                this.valueSliderPaint.setShader(this.alphaGradient);
                canvas.drawRect((float) i3, (float) i4, (float) (i3 + dp), (float) (i4 + i5), this.valueSliderPaint);
                drawPointerArrow(canvas, (dp / 2) + i3, (int) (((float) i4) + ((1.0f - this.alpha) * ((float) i5))), (Color.HSVToColor(this.colorHSV) & 16777215) | (((int) (255.0f * this.alpha)) << 24));
            }

            protected void onMeasure(int i, int i2) {
                int min = Math.min(MeasureSpec.getSize(i), MeasureSpec.getSize(i2));
                measureChild(this.linearLayout, i, i2);
                setMeasuredDimension(min, min);
            }

            protected void onSizeChanged(int i, int i2, int i3, int i4) {
                this.colorWheelRadius = Math.max(1, ((i / 2) - (this.paramValueSliderWidth * 2)) - AndroidUtilities.dp(20.0f));
                this.colorWheelBitmap = createColorWheelBitmap(this.colorWheelRadius * 2, this.colorWheelRadius * 2);
                this.colorGradient = null;
                this.alphaGradient = null;
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0:
                    case 2:
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        int width = (getWidth() / 2) - (this.paramValueSliderWidth * 2);
                        int height = (getHeight() / 2) - AndroidUtilities.dp(8.0f);
                        int i = x - width;
                        int i2 = y - height;
                        double sqrt = Math.sqrt((double) ((i * i) + (i2 * i2)));
                        if (this.circlePressed || !(this.alphaPressed || this.colorPressed || sqrt > ((double) this.colorWheelRadius))) {
                            if (sqrt > ((double) this.colorWheelRadius)) {
                                sqrt = (double) this.colorWheelRadius;
                            }
                            this.circlePressed = true;
                            this.colorHSV[0] = (float) (Math.toDegrees(Math.atan2((double) i2, (double) i)) + 180.0d);
                            this.colorHSV[1] = Math.max(BitmapDescriptorFactory.HUE_RED, Math.min(1.0f, (float) (sqrt / ((double) this.colorWheelRadius))));
                            this.colorGradient = null;
                            this.alphaGradient = null;
                        }
                        if (this.colorPressed || (!this.circlePressed && !this.alphaPressed && x >= (this.colorWheelRadius + width) + this.paramValueSliderWidth && x <= (this.colorWheelRadius + width) + (this.paramValueSliderWidth * 2) && y >= height - this.colorWheelRadius && y <= this.colorWheelRadius + height)) {
                            float f = ((float) (y - (height - this.colorWheelRadius))) / (((float) this.colorWheelRadius) * 2.0f);
                            if (f < BitmapDescriptorFactory.HUE_RED) {
                                f = BitmapDescriptorFactory.HUE_RED;
                            } else if (f > 1.0f) {
                                f = 1.0f;
                            }
                            this.colorHSV[2] = f;
                            this.colorPressed = true;
                        }
                        if (this.alphaPressed || (!this.circlePressed && !this.colorPressed && x >= (this.colorWheelRadius + width) + (this.paramValueSliderWidth * 3) && x <= (this.colorWheelRadius + width) + (this.paramValueSliderWidth * 4) && y >= height - this.colorWheelRadius && y <= this.colorWheelRadius + height)) {
                            this.alpha = 1.0f - (((float) (y - (height - this.colorWheelRadius))) / (((float) this.colorWheelRadius) * 2.0f));
                            if (this.alpha < BitmapDescriptorFactory.HUE_RED) {
                                this.alpha = BitmapDescriptorFactory.HUE_RED;
                            } else if (this.alpha > 1.0f) {
                                this.alpha = 1.0f;
                            }
                            this.alphaPressed = true;
                        }
                        if (this.alphaPressed || this.colorPressed || this.circlePressed) {
                            int i3;
                            startColorChange(true);
                            x = getColor();
                            for (i3 = 0; i3 < ThemeEditorView.this.currentThemeDesription.size(); i3++) {
                                ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(i3)).setColor(x, false);
                            }
                            int red = Color.red(x);
                            i3 = Color.green(x);
                            y = Color.blue(x);
                            x = Color.alpha(x);
                            if (!EditorAlert.this.ignoreTextChange) {
                                EditorAlert.this.ignoreTextChange = true;
                                this.colorEditText[0].setText(TtmlNode.ANONYMOUS_REGION_ID + red);
                                this.colorEditText[1].setText(TtmlNode.ANONYMOUS_REGION_ID + i3);
                                this.colorEditText[2].setText(TtmlNode.ANONYMOUS_REGION_ID + y);
                                this.colorEditText[3].setText(TtmlNode.ANONYMOUS_REGION_ID + x);
                                for (red = 0; red < 4; red++) {
                                    this.colorEditText[red].setSelection(this.colorEditText[red].length());
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
                return super.onTouchEvent(motionEvent);
            }

            public void setColor(int i) {
                int red = Color.red(i);
                int green = Color.green(i);
                int blue = Color.blue(i);
                int alpha = Color.alpha(i);
                if (!EditorAlert.this.ignoreTextChange) {
                    EditorAlert.this.ignoreTextChange = true;
                    this.colorEditText[0].setText(TtmlNode.ANONYMOUS_REGION_ID + red);
                    this.colorEditText[1].setText(TtmlNode.ANONYMOUS_REGION_ID + green);
                    this.colorEditText[2].setText(TtmlNode.ANONYMOUS_REGION_ID + blue);
                    this.colorEditText[3].setText(TtmlNode.ANONYMOUS_REGION_ID + alpha);
                    for (red = 0; red < 4; red++) {
                        this.colorEditText[red].setSelection(this.colorEditText[red].length());
                    }
                    EditorAlert.this.ignoreTextChange = false;
                }
                this.alphaGradient = null;
                this.colorGradient = null;
                this.alpha = ((float) alpha) / 255.0f;
                Color.colorToHSV(i, this.colorHSV);
                invalidate();
            }
        }

        private class ListAdapter extends SelectionAdapter {
            private Context context;
            private int currentCount;
            private ArrayList<ArrayList<ThemeDescription>> items = new ArrayList();
            private HashMap<String, ArrayList<ThemeDescription>> itemsMap = new HashMap();

            public ListAdapter(Context context, ThemeDescription[] themeDescriptionArr) {
                this.context = context;
                for (ThemeDescription themeDescription : themeDescriptionArr) {
                    String currentKey = themeDescription.getCurrentKey();
                    ArrayList arrayList = (ArrayList) this.itemsMap.get(currentKey);
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.itemsMap.put(currentKey, arrayList);
                        this.items.add(arrayList);
                    }
                    arrayList.add(themeDescription);
                }
            }

            public ArrayList<ThemeDescription> getItem(int i) {
                return (i < 0 || i >= this.items.size()) ? null : (ArrayList) this.items.get(i);
            }

            public int getItemCount() {
                return this.items.size();
            }

            public int getItemViewType(int i) {
                return 0;
            }

            public boolean isEnabled(ViewHolder viewHolder) {
                return true;
            }

            public void onBindViewHolder(ViewHolder viewHolder, int i) {
                ThemeDescription themeDescription = (ThemeDescription) ((ArrayList) this.items.get(i)).get(0);
                ((TextColorThemeCell) viewHolder.itemView).setTextAndColor(themeDescription.getTitle(), themeDescription.getCurrentKey().equals(Theme.key_chat_wallpaper) ? 0 : themeDescription.getSetColor());
            }

            public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                View textColorThemeCell = new TextColorThemeCell(this.context);
                textColorThemeCell.setLayoutParams(new RecyclerView.LayoutParams(-1, -2));
                return new Holder(textColorThemeCell);
            }
        }

        public EditorAlert(Context context, ThemeDescription[] themeDescriptionArr) {
            super(context, true);
            this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
            this.containerView = new FrameLayout(context, ThemeEditorView.this) {
                private boolean ignoreLayout = false;

                protected void onDraw(Canvas canvas) {
                    EditorAlert.this.shadowDrawable.setBounds(0, EditorAlert.this.scrollOffsetY - EditorAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                    EditorAlert.this.shadowDrawable.draw(canvas);
                }

                public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 0 || EditorAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) EditorAlert.this.scrollOffsetY)) {
                        return super.onInterceptTouchEvent(motionEvent);
                    }
                    EditorAlert.this.dismiss();
                    return true;
                }

                protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                    super.onLayout(z, i, i2, i3, i4);
                    EditorAlert.this.updateLayout();
                }

                protected void onMeasure(int i, int i2) {
                    int size = MeasureSpec.getSize(i);
                    int size2 = MeasureSpec.getSize(i2);
                    if (VERSION.SDK_INT >= 21) {
                        size2 -= AndroidUtilities.statusBarHeight;
                    }
                    size = size2 - Math.min(size, size2);
                    if (EditorAlert.this.listView.getPaddingTop() != size) {
                        this.ignoreLayout = true;
                        EditorAlert.this.listView.getPaddingTop();
                        EditorAlert.this.listView.setPadding(0, size, 0, AndroidUtilities.dp(48.0f));
                        if (EditorAlert.this.colorPicker.getVisibility() == 0) {
                            EditorAlert.this.scrollOffsetY = EditorAlert.this.listView.getPaddingTop();
                            EditorAlert.this.listView.setTopGlowOffset(EditorAlert.this.scrollOffsetY);
                            EditorAlert.this.colorPicker.setTranslationY((float) EditorAlert.this.scrollOffsetY);
                            EditorAlert.this.previousScrollPosition = 0;
                        }
                        this.ignoreLayout = false;
                    }
                    super.onMeasure(i, MeasureSpec.makeMeasureSpec(size2, 1073741824));
                }

                public boolean onTouchEvent(MotionEvent motionEvent) {
                    return !EditorAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
                }

                public void requestLayout() {
                    if (!this.ignoreLayout) {
                        super.requestLayout();
                    }
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
            Adapter listAdapter = new ListAdapter(context, themeDescriptionArr);
            this.listAdapter = listAdapter;
            recyclerListView.setAdapter(listAdapter);
            this.listView.setGlowColor(-657673);
            this.listView.setItemAnimator(null);
            this.listView.setLayoutAnimation(null);
            this.listView.setOnItemClickListener(new OnItemClickListener(ThemeEditorView.this) {
                public void onItemClick(View view, int i) {
                    ThemeEditorView.this.currentThemeDesription = EditorAlert.this.listAdapter.getItem(i);
                    ThemeEditorView.this.currentThemeDesriptionPosition = i;
                    for (int i2 = 0; i2 < ThemeEditorView.this.currentThemeDesription.size(); i2++) {
                        ThemeDescription themeDescription = (ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(i2);
                        if (themeDescription.getCurrentKey().equals(Theme.key_chat_wallpaper)) {
                            ThemeEditorView.this.wallpaperUpdater.showAlert(true);
                            return;
                        }
                        themeDescription.startEditing();
                        if (i2 == 0) {
                            EditorAlert.this.colorPicker.setColor(themeDescription.getCurrentColor());
                        }
                    }
                    EditorAlert.this.setColorPickerVisible(true);
                }
            });
            this.listView.setOnScrollListener(new OnScrollListener(ThemeEditorView.this) {
                public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                    EditorAlert.this.updateLayout();
                }
            });
            this.colorPicker = new ColorPicker(context);
            this.colorPicker.setVisibility(8);
            this.containerView.addView(this.colorPicker, LayoutHelper.createFrame(-1, -1, 1));
            this.shadow = new View(context);
            this.shadow.setBackgroundResource(R.drawable.header_shadow_reverse);
            this.containerView.addView(this.shadow, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
            this.bottomSaveLayout = new FrameLayout(context);
            this.bottomSaveLayout.setBackgroundColor(-1);
            this.containerView.addView(this.bottomSaveLayout, LayoutHelper.createFrame(-1, 48, 83));
            View textView = new TextView(context);
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(-15095832);
            textView.setGravity(17);
            textView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            textView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            textView.setText(LocaleController.getString("CloseEditor", R.string.CloseEditor).toUpperCase());
            textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.bottomSaveLayout.addView(textView, LayoutHelper.createFrame(-2, -1, 51));
            textView.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View view) {
                    EditorAlert.this.dismiss();
                }
            });
            textView = new TextView(context);
            textView.setTextSize(1, 14.0f);
            textView.setTextColor(-15095832);
            textView.setGravity(17);
            textView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            textView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            textView.setText(LocaleController.getString("SaveTheme", R.string.SaveTheme).toUpperCase());
            textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            this.bottomSaveLayout.addView(textView, LayoutHelper.createFrame(-2, -1, 53));
            textView.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View view) {
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
                public void onClick(View view) {
                    for (int i = 0; i < ThemeEditorView.this.currentThemeDesription.size(); i++) {
                        ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(i)).setPreviousColor();
                    }
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
            textView = new LinearLayout(context);
            textView.setOrientation(0);
            this.bottomLayout.addView(textView, LayoutHelper.createFrame(-2, -1, 53));
            this.defaultButtom = new TextView(context);
            this.defaultButtom.setTextSize(1, 14.0f);
            this.defaultButtom.setTextColor(-15095832);
            this.defaultButtom.setGravity(17);
            this.defaultButtom.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            this.defaultButtom.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            this.defaultButtom.setText(LocaleController.getString("Default", R.string.Default).toUpperCase());
            this.defaultButtom.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            textView.addView(this.defaultButtom, LayoutHelper.createFrame(-2, -1, 51));
            this.defaultButtom.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View view) {
                    for (int i = 0; i < ThemeEditorView.this.currentThemeDesription.size(); i++) {
                        ((ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(i)).setDefaultColor();
                    }
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
            View textView2 = new TextView(context);
            textView2.setTextSize(1, 14.0f);
            textView2.setTextColor(-15095832);
            textView2.setGravity(17);
            textView2.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_AUDIO_SELECTOR_COLOR, 0));
            textView2.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
            textView2.setText(LocaleController.getString("Save", R.string.Save).toUpperCase());
            textView2.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            textView.addView(textView2, LayoutHelper.createFrame(-2, -1, 51));
            textView2.setOnClickListener(new OnClickListener(ThemeEditorView.this) {
                public void onClick(View view) {
                    EditorAlert.this.setColorPickerVisible(false);
                }
            });
        }

        private int getCurrentTop() {
            if (this.listView.getChildCount() != 0) {
                View childAt = this.listView.getChildAt(0);
                Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
                if (holder != null) {
                    int paddingTop = this.listView.getPaddingTop();
                    int top = (holder.getAdapterPosition() != 0 || childAt.getTop() < 0) ? 0 : childAt.getTop();
                    return paddingTop - top;
                }
            }
            return C3446C.PRIORITY_DOWNLOAD;
        }

        private void setColorPickerVisible(boolean z) {
            if (z) {
                this.animationInProgress = true;
                this.colorPicker.setVisibility(0);
                this.bottomLayout.setVisibility(0);
                this.colorPicker.setAlpha(BitmapDescriptorFactory.HUE_RED);
                this.bottomLayout.setAlpha(BitmapDescriptorFactory.HUE_RED);
                AnimatorSet animatorSet = new AnimatorSet();
                r1 = new Animator[5];
                r1[0] = ObjectAnimator.ofFloat(this.colorPicker, "alpha", new float[]{1.0f});
                r1[1] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{1.0f});
                r1[2] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                r1[3] = ObjectAnimator.ofFloat(this.bottomSaveLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                r1[4] = ObjectAnimator.ofInt(this, "scrollOffsetY", new int[]{this.listView.getPaddingTop()});
                animatorSet.playTogether(r1);
                animatorSet.setDuration(150);
                animatorSet.setInterpolator(ThemeEditorView.this.decelerateInterpolator);
                animatorSet.addListener(new C46359());
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
            this.listView.setAlpha(BitmapDescriptorFactory.HUE_RED);
            animatorSet = new AnimatorSet();
            r1 = new Animator[5];
            r1[0] = ObjectAnimator.ofFloat(this.colorPicker, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[1] = ObjectAnimator.ofFloat(this.bottomLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
            r1[2] = ObjectAnimator.ofFloat(this.listView, "alpha", new float[]{1.0f});
            r1[3] = ObjectAnimator.ofFloat(this.bottomSaveLayout, "alpha", new float[]{1.0f});
            r1[4] = ObjectAnimator.ofInt(this, "scrollOffsetY", new int[]{this.previousScrollPosition});
            animatorSet.playTogether(r1);
            animatorSet.setDuration(150);
            animatorSet.setInterpolator(ThemeEditorView.this.decelerateInterpolator);
            animatorSet.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    EditorAlert.this.colorPicker.setVisibility(8);
                    EditorAlert.this.bottomLayout.setVisibility(8);
                    EditorAlert.this.animationInProgress = false;
                }
            });
            animatorSet.start();
            this.listAdapter.notifyItemChanged(ThemeEditorView.this.currentThemeDesriptionPosition);
        }

        @SuppressLint({"NewApi"})
        private void updateLayout() {
            if (this.listView.getChildCount() > 0 && this.listView.getVisibility() == 0 && !this.animationInProgress) {
                View childAt = this.listView.getChildAt(0);
                Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
                int paddingTop = (this.listView.getVisibility() != 0 || this.animationInProgress) ? this.listView.getPaddingTop() : childAt.getTop() - AndroidUtilities.dp(8.0f);
                int i = (paddingTop <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : paddingTop;
                if (this.scrollOffsetY != i) {
                    setScrollOffsetY(i);
                }
            }
        }

        protected boolean canDismissWithSwipe() {
            return false;
        }

        public int getScrollOffsetY() {
            return this.scrollOffsetY;
        }

        @Keep
        public void setScrollOffsetY(int i) {
            RecyclerListView recyclerListView = this.listView;
            this.scrollOffsetY = i;
            recyclerListView.setTopGlowOffset(i);
            this.colorPicker.setTranslationY((float) this.scrollOffsetY);
            this.containerView.invalidate();
        }
    }

    private void animateToBoundsMaybe() {
        boolean z;
        int sideCoord = getSideCoord(true, 0, BitmapDescriptorFactory.HUE_RED, this.editorWidth);
        int sideCoord2 = getSideCoord(true, 1, BitmapDescriptorFactory.HUE_RED, this.editorWidth);
        int sideCoord3 = getSideCoord(false, 0, BitmapDescriptorFactory.HUE_RED, this.editorHeight);
        int sideCoord4 = getSideCoord(false, 1, BitmapDescriptorFactory.HUE_RED, this.editorHeight);
        Collection collection = null;
        Editor edit = this.preferences.edit();
        int dp = AndroidUtilities.dp(20.0f);
        if (Math.abs(sideCoord - this.windowLayoutParams.x) <= dp || (this.windowLayoutParams.x < 0 && this.windowLayoutParams.x > (-this.editorWidth) / 4)) {
            if (null == null) {
                collection = new ArrayList();
            }
            edit.putInt("sidex", 0);
            if (this.windowView.getAlpha() != 1.0f) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            collection.add(ObjectAnimator.ofInt(this, "x", new int[]{sideCoord}));
            z = false;
        } else if (Math.abs(sideCoord2 - this.windowLayoutParams.x) <= dp || (this.windowLayoutParams.x > AndroidUtilities.displaySize.x - this.editorWidth && this.windowLayoutParams.x < AndroidUtilities.displaySize.x - ((this.editorWidth / 4) * 3))) {
            if (null == null) {
                collection = new ArrayList();
            }
            edit.putInt("sidex", 1);
            if (this.windowView.getAlpha() != 1.0f) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f}));
            }
            collection.add(ObjectAnimator.ofInt(this, "x", new int[]{sideCoord2}));
            z = false;
        } else if (this.windowView.getAlpha() != 1.0f) {
            if (null == null) {
                collection = new ArrayList();
            }
            if (this.windowLayoutParams.x < 0) {
                collection.add(ObjectAnimator.ofInt(this, "x", new int[]{-this.editorWidth}));
            } else {
                collection.add(ObjectAnimator.ofInt(this, "x", new int[]{AndroidUtilities.displaySize.x}));
            }
            z = true;
        } else {
            edit.putFloat("px", ((float) (this.windowLayoutParams.x - sideCoord)) / ((float) (sideCoord2 - sideCoord)));
            edit.putInt("sidex", 2);
            z = false;
        }
        if (!z) {
            if (Math.abs(sideCoord3 - this.windowLayoutParams.y) <= dp || this.windowLayoutParams.y <= ActionBar.getCurrentActionBarHeight()) {
                if (collection == null) {
                    collection = new ArrayList();
                }
                edit.putInt("sidey", 0);
                collection.add(ObjectAnimator.ofInt(this, "y", new int[]{sideCoord3}));
            } else if (Math.abs(sideCoord4 - this.windowLayoutParams.y) <= dp) {
                if (collection == null) {
                    collection = new ArrayList();
                }
                edit.putInt("sidey", 1);
                collection.add(ObjectAnimator.ofInt(this, "y", new int[]{sideCoord4}));
            } else {
                edit.putFloat("py", ((float) (this.windowLayoutParams.y - sideCoord3)) / ((float) (sideCoord4 - sideCoord3)));
                edit.putInt("sidey", 2);
            }
            edit.commit();
        }
        if (collection != null) {
            if (this.decelerateInterpolator == null) {
                this.decelerateInterpolator = new DecelerateInterpolator();
            }
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setInterpolator(this.decelerateInterpolator);
            animatorSet.setDuration(150);
            if (z) {
                collection.add(ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
                animatorSet.addListener(new C46264());
            }
            animatorSet.playTogether(collection);
            animatorSet.start();
        }
    }

    public static ThemeEditorView getInstance() {
        return Instance;
    }

    private static int getSideCoord(boolean z, int i, float f, int i2) {
        int currentActionBarHeight = z ? AndroidUtilities.displaySize.x - i2 : (AndroidUtilities.displaySize.y - i2) - ActionBar.getCurrentActionBarHeight();
        currentActionBarHeight = i == 0 ? AndroidUtilities.dp(10.0f) : i == 1 ? currentActionBarHeight - AndroidUtilities.dp(10.0f) : Math.round(((float) (currentActionBarHeight - AndroidUtilities.dp(20.0f))) * f) + AndroidUtilities.dp(10.0f);
        return !z ? currentActionBarHeight + ActionBar.getCurrentActionBarHeight() : currentActionBarHeight;
    }

    private void hide() {
        if (this.parentActivity != null) {
            try {
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}), ObjectAnimator.ofFloat(this.windowView, "scaleX", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}), ObjectAnimator.ofFloat(this.windowView, "scaleY", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED})});
                animatorSet.setInterpolator(this.decelerateInterpolator);
                animatorSet.setDuration(150);
                animatorSet.addListener(new C46253());
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

    private void showWithAnimation() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(this.windowView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleX", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}), ObjectAnimator.ofFloat(this.windowView, "scaleY", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
        animatorSet.setInterpolator(this.decelerateInterpolator);
        animatorSet.setDuration(150);
        animatorSet.start();
    }

    public void close() {
        try {
            this.windowManager.removeView(this.windowView);
        } catch (Exception e) {
        }
        this.parentActivity = null;
    }

    public void destroy() {
        this.wallpaperUpdater.cleanup();
        if (this.parentActivity != null && this.windowView != null) {
            try {
                this.windowManager.removeViewImmediate(this.windowView);
                this.windowView = null;
            } catch (Throwable e) {
                FileLog.e(e);
            }
            try {
                if (this.editorAlert != null) {
                    this.editorAlert.dismiss();
                    this.editorAlert = null;
                }
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            this.parentActivity = null;
            Instance = null;
        }
    }

    public int getX() {
        return this.windowLayoutParams.x;
    }

    public int getY() {
        return this.windowLayoutParams.y;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (this.wallpaperUpdater != null) {
            this.wallpaperUpdater.onActivityResult(i, i2, intent);
        }
    }

    public void onConfigurationChanged() {
        int i = this.preferences.getInt("sidex", 1);
        int i2 = this.preferences.getInt("sidey", 0);
        float f = this.preferences.getFloat("px", BitmapDescriptorFactory.HUE_RED);
        float f2 = this.preferences.getFloat("py", BitmapDescriptorFactory.HUE_RED);
        this.windowLayoutParams.x = getSideCoord(true, i, f, this.editorWidth);
        this.windowLayoutParams.y = getSideCoord(false, i2, f2, this.editorHeight);
        try {
            if (this.windowView.getParent() != null) {
                this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }

    @Keep
    public void setX(int i) {
        this.windowLayoutParams.x = i;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    @Keep
    public void setY(int i) {
        this.windowLayoutParams.y = i;
        this.windowManager.updateViewLayout(this.windowView, this.windowLayoutParams);
    }

    public void show(Activity activity, final String str) {
        if (Instance != null) {
            Instance.destroy();
        }
        this.hidden = false;
        this.currentThemeName = str;
        this.windowView = new FrameLayout(activity) {
            private boolean dragging;
            private float startX;
            private float startY;

            /* renamed from: org.telegram.ui.Components.ThemeEditorView$1$1 */
            class C46211 implements OnDismissListener {
                C46211() {
                }

                public void onDismiss(DialogInterface dialogInterface) {
                }
            }

            /* renamed from: org.telegram.ui.Components.ThemeEditorView$1$2 */
            class C46222 implements OnDismissListener {
                C46222() {
                }

                public void onDismiss(DialogInterface dialogInterface) {
                    ThemeEditorView.this.editorAlert = null;
                    ThemeEditorView.this.show();
                }
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return true;
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                float rawX = motionEvent.getRawX();
                float rawY = motionEvent.getRawY();
                if (motionEvent.getAction() == 0) {
                    this.startX = rawX;
                    this.startY = rawY;
                } else if (motionEvent.getAction() != 2 || this.dragging) {
                    if (motionEvent.getAction() == 1 && !this.dragging && ThemeEditorView.this.editorAlert == null) {
                        ActionBarLayout actionBarLayout = ((LaunchActivity) ThemeEditorView.this.parentActivity).getActionBarLayout();
                        if (!actionBarLayout.fragmentsStack.isEmpty()) {
                            ThemeDescription[] themeDescriptions = ((BaseFragment) actionBarLayout.fragmentsStack.get(actionBarLayout.fragmentsStack.size() - 1)).getThemeDescriptions();
                            if (themeDescriptions != null) {
                                ThemeEditorView.this.editorAlert = new EditorAlert(ThemeEditorView.this.parentActivity, themeDescriptions);
                                ThemeEditorView.this.editorAlert.setOnDismissListener(new C46211());
                                ThemeEditorView.this.editorAlert.setOnDismissListener(new C46222());
                                ThemeEditorView.this.editorAlert.show();
                                ThemeEditorView.this.hide();
                            }
                        }
                    }
                } else if (Math.abs(this.startX - rawX) >= AndroidUtilities.getPixelsInCM(0.3f, true) || Math.abs(this.startY - rawY) >= AndroidUtilities.getPixelsInCM(0.3f, false)) {
                    this.dragging = true;
                    this.startX = rawX;
                    this.startY = rawY;
                }
                if (this.dragging) {
                    if (motionEvent.getAction() == 2) {
                        float f = rawX - this.startX;
                        float f2 = rawY - this.startY;
                        LayoutParams access$2700 = ThemeEditorView.this.windowLayoutParams;
                        access$2700.x = (int) (f + ((float) access$2700.x));
                        LayoutParams access$27002 = ThemeEditorView.this.windowLayoutParams;
                        access$27002.y = (int) (f2 + ((float) access$27002.y));
                        int access$2800 = ThemeEditorView.this.editorWidth / 2;
                        if (ThemeEditorView.this.windowLayoutParams.x < (-access$2800)) {
                            ThemeEditorView.this.windowLayoutParams.x = -access$2800;
                        } else if (ThemeEditorView.this.windowLayoutParams.x > (AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width) + access$2800) {
                            ThemeEditorView.this.windowLayoutParams.x = (AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width) + access$2800;
                        }
                        f = ThemeEditorView.this.windowLayoutParams.x < 0 ? ((((float) ThemeEditorView.this.windowLayoutParams.x) / ((float) access$2800)) * 0.5f) + 1.0f : ThemeEditorView.this.windowLayoutParams.x > AndroidUtilities.displaySize.x - ThemeEditorView.this.windowLayoutParams.width ? 1.0f - ((((float) ((ThemeEditorView.this.windowLayoutParams.x - AndroidUtilities.displaySize.x) + ThemeEditorView.this.windowLayoutParams.width)) / ((float) access$2800)) * 0.5f) : 1.0f;
                        if (ThemeEditorView.this.windowView.getAlpha() != f) {
                            ThemeEditorView.this.windowView.setAlpha(f);
                        }
                        if (ThemeEditorView.this.windowLayoutParams.y < (-null)) {
                            ThemeEditorView.this.windowLayoutParams.y = -null;
                        } else if (ThemeEditorView.this.windowLayoutParams.y > (AndroidUtilities.displaySize.y - ThemeEditorView.this.windowLayoutParams.height) + 0) {
                            ThemeEditorView.this.windowLayoutParams.y = (AndroidUtilities.displaySize.y - ThemeEditorView.this.windowLayoutParams.height) + 0;
                        }
                        ThemeEditorView.this.windowManager.updateViewLayout(ThemeEditorView.this.windowView, ThemeEditorView.this.windowLayoutParams);
                        this.startX = rawX;
                        this.startY = rawY;
                    } else if (motionEvent.getAction() == 1) {
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
        int i = this.preferences.getInt("sidex", 1);
        int i2 = this.preferences.getInt("sidey", 0);
        float f = this.preferences.getFloat("px", BitmapDescriptorFactory.HUE_RED);
        float f2 = this.preferences.getFloat("py", BitmapDescriptorFactory.HUE_RED);
        try {
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.width = this.editorWidth;
            this.windowLayoutParams.height = this.editorHeight;
            this.windowLayoutParams.x = getSideCoord(true, i, f, this.editorWidth);
            this.windowLayoutParams.y = getSideCoord(false, i2, f2, this.editorHeight);
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.gravity = 51;
            this.windowLayoutParams.type = 99;
            this.windowLayoutParams.flags = 16777736;
            this.windowManager.addView(this.windowView, this.windowLayoutParams);
            this.wallpaperUpdater = new WallpaperUpdater(activity, new WallpaperUpdaterDelegate() {
                public void didSelectWallpaper(File file, Bitmap bitmap) {
                    Theme.setThemeWallpaper(str, bitmap, file);
                }

                public void needOpenColorPicker() {
                    for (int i = 0; i < ThemeEditorView.this.currentThemeDesription.size(); i++) {
                        ThemeDescription themeDescription = (ThemeDescription) ThemeEditorView.this.currentThemeDesription.get(i);
                        themeDescription.startEditing();
                        if (i == 0) {
                            ThemeEditorView.this.editorAlert.colorPicker.setColor(themeDescription.getCurrentColor());
                        }
                    }
                    ThemeEditorView.this.editorAlert.setColorPickerVisible(true);
                }
            });
            Instance = this;
            this.parentActivity = activity;
            showWithAnimation();
        } catch (Throwable e) {
            FileLog.e(e);
        }
    }
}
