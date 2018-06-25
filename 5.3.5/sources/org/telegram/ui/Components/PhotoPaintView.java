package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputDocument;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_maskCoords;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBarPopupWindow;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.ActionBarPopupWindowLayout;
import org.telegram.ui.ActionBar.ActionBarPopupWindow.OnDispatchKeyEventListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Components.Paint.Brush;
import org.telegram.ui.Components.Paint.Brush.Elliptical;
import org.telegram.ui.Components.Paint.Brush.Neon;
import org.telegram.ui.Components.Paint.Brush.Radial;
import org.telegram.ui.Components.Paint.Painting;
import org.telegram.ui.Components.Paint.PhotoFace;
import org.telegram.ui.Components.Paint.RenderView;
import org.telegram.ui.Components.Paint.RenderView.RenderViewDelegate;
import org.telegram.ui.Components.Paint.Swatch;
import org.telegram.ui.Components.Paint.UndoStore;
import org.telegram.ui.Components.Paint.UndoStore.UndoStoreDelegate;
import org.telegram.ui.Components.Paint.Views.ColorPicker;
import org.telegram.ui.Components.Paint.Views.ColorPicker.ColorPickerDelegate;
import org.telegram.ui.Components.Paint.Views.EditTextOutline;
import org.telegram.ui.Components.Paint.Views.EntitiesContainerView;
import org.telegram.ui.Components.Paint.Views.EntitiesContainerView.EntitiesContainerViewDelegate;
import org.telegram.ui.Components.Paint.Views.EntityView;
import org.telegram.ui.Components.Paint.Views.EntityView.EntityViewDelegate;
import org.telegram.ui.Components.Paint.Views.StickerView;
import org.telegram.ui.Components.Paint.Views.TextPaintView;
import org.telegram.ui.Components.StickerMasksView.Listener;
import org.telegram.ui.PhotoViewer;

@SuppressLint({"NewApi"})
public class PhotoPaintView extends FrameLayout implements EntityViewDelegate {
    private static final int gallery_menu_done = 1;
    private Bitmap bitmapToEdit;
    private Brush[] brushes = new Brush[]{new Radial(), new Elliptical(), new Neon()};
    private TextView cancelTextView;
    private ColorPicker colorPicker;
    private Animator colorPickerAnimator;
    int currentBrush;
    private EntityView currentEntityView;
    private FrameLayout curtainView;
    private FrameLayout dimView;
    private TextView doneTextView;
    private Point editedTextPosition;
    private float editedTextRotation;
    private float editedTextScale;
    private boolean editingText;
    private EntitiesContainerView entitiesView;
    private ArrayList<PhotoFace> faces;
    private String initialText;
    private int orientation;
    private ImageView paintButton;
    private Size paintingSize;
    private boolean pickingSticker;
    private ActionBarPopupWindowLayout popupLayout;
    private Rect popupRect;
    private ActionBarPopupWindow popupWindow;
    private DispatchQueue queue = new DispatchQueue("Paint");
    private RenderView renderView;
    private boolean selectedStroke = true;
    private FrameLayout selectionContainerView;
    private StickerMasksView stickersView;
    private FrameLayout textDimView;
    private FrameLayout toolsView;
    private UndoStore undoStore;

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$1 */
    class C26921 implements UndoStoreDelegate {
        C26921() {
        }

        public void historyChanged() {
            PhotoPaintView.this.colorPicker.setUndoEnabled(PhotoPaintView.this.undoStore.canUndo());
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$2 */
    class C26932 implements RenderViewDelegate {
        C26932() {
        }

        public void onBeganDrawing() {
            if (PhotoPaintView.this.currentEntityView != null) {
                PhotoPaintView.this.selectEntity(null);
            }
        }

        public void onFinishedDrawing(boolean moved) {
            PhotoPaintView.this.colorPicker.setUndoEnabled(PhotoPaintView.this.undoStore.canUndo());
        }

        public boolean shouldDraw() {
            boolean draw = PhotoPaintView.this.currentEntityView == null;
            if (!draw) {
                PhotoPaintView.this.selectEntity(null);
            }
            return draw;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$3 */
    class C26943 implements EntitiesContainerViewDelegate {
        C26943() {
        }

        public boolean shouldReceiveTouches() {
            return PhotoPaintView.this.textDimView.getVisibility() != 0;
        }

        public EntityView onSelectedEntityRequest() {
            return PhotoPaintView.this.currentEntityView;
        }

        public void onEntityDeselect() {
            PhotoPaintView.this.selectEntity(null);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$4 */
    class C26954 implements OnClickListener {
        C26954() {
        }

        public void onClick(View v) {
            PhotoPaintView.this.closeTextEnter(true);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$6 */
    class C26976 implements ColorPickerDelegate {
        C26976() {
        }

        public void onBeganColorPicking() {
            if (!(PhotoPaintView.this.currentEntityView instanceof TextPaintView)) {
                PhotoPaintView.this.setDimVisibility(true);
            }
        }

        public void onColorValueChanged() {
            PhotoPaintView.this.setCurrentSwatch(PhotoPaintView.this.colorPicker.getSwatch(), false);
        }

        public void onFinishedColorPicking() {
            PhotoPaintView.this.setCurrentSwatch(PhotoPaintView.this.colorPicker.getSwatch(), false);
            if (!(PhotoPaintView.this.currentEntityView instanceof TextPaintView)) {
                PhotoPaintView.this.setDimVisibility(false);
            }
        }

        public void onSettingsPressed() {
            if (PhotoPaintView.this.currentEntityView == null) {
                PhotoPaintView.this.showBrushSettings();
            } else if (PhotoPaintView.this.currentEntityView instanceof StickerView) {
                PhotoPaintView.this.mirrorSticker();
            } else if (PhotoPaintView.this.currentEntityView instanceof TextPaintView) {
                PhotoPaintView.this.showTextSettings();
            }
        }

        public void onUndoPressed() {
            PhotoPaintView.this.undoStore.undo();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$7 */
    class C26987 implements OnClickListener {
        C26987() {
        }

        public void onClick(View v) {
            PhotoPaintView.this.selectEntity(null);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$8 */
    class C26998 implements OnClickListener {
        C26998() {
        }

        public void onClick(View v) {
            PhotoPaintView.this.openStickersView();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$9 */
    class C27009 implements OnClickListener {
        C27009() {
        }

        public void onClick(View v) {
            PhotoPaintView.this.createText();
        }
    }

    private class StickerPosition {
        private float angle;
        private Point position;
        private float scale;

        StickerPosition(Point position, float scale, float angle) {
            this.position = position;
            this.scale = scale;
            this.angle = angle;
        }
    }

    public PhotoPaintView(Context context, Bitmap bitmap, int rotation) {
        super(context);
        this.bitmapToEdit = bitmap;
        this.orientation = rotation;
        this.undoStore = new UndoStore();
        this.undoStore.setDelegate(new C26921());
        this.curtainView = new FrameLayout(context);
        this.curtainView.setBackgroundColor(-16777216);
        this.curtainView.setVisibility(4);
        addView(this.curtainView);
        this.renderView = new RenderView(context, new Painting(getPaintingSize()), bitmap, this.orientation);
        this.renderView.setDelegate(new C26932());
        this.renderView.setUndoStore(this.undoStore);
        this.renderView.setQueue(this.queue);
        this.renderView.setVisibility(4);
        this.renderView.setBrush(this.brushes[0]);
        addView(this.renderView, LayoutHelper.createFrame(-1, -1, 51));
        this.entitiesView = new EntitiesContainerView(context, new C26943());
        this.entitiesView.setPivotX(0.0f);
        this.entitiesView.setPivotY(0.0f);
        addView(this.entitiesView);
        this.dimView = new FrameLayout(context);
        this.dimView.setAlpha(0.0f);
        this.dimView.setBackgroundColor(1711276032);
        this.dimView.setVisibility(8);
        addView(this.dimView);
        this.textDimView = new FrameLayout(context);
        this.textDimView.setAlpha(0.0f);
        this.textDimView.setBackgroundColor(1711276032);
        this.textDimView.setVisibility(8);
        this.textDimView.setOnClickListener(new C26954());
        this.selectionContainerView = new FrameLayout(context) {
            public boolean onTouchEvent(MotionEvent event) {
                return false;
            }
        };
        addView(this.selectionContainerView);
        this.colorPicker = new ColorPicker(context);
        addView(this.colorPicker);
        this.colorPicker.setDelegate(new C26976());
        this.toolsView = new FrameLayout(context);
        this.toolsView.setBackgroundColor(-16777216);
        addView(this.toolsView, LayoutHelper.createFrame(-1, 48, 83));
        this.cancelTextView = new TextView(context);
        this.cancelTextView.setTextSize(1, 14.0f);
        this.cancelTextView.setTextColor(-1);
        this.cancelTextView.setGravity(17);
        this.cancelTextView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        this.cancelTextView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.cancelTextView.setText(LocaleController.getString("Cancel", R.string.Cancel).toUpperCase());
        this.cancelTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.toolsView.addView(this.cancelTextView, LayoutHelper.createFrame(-2, -1, 51));
        this.doneTextView = new TextView(context);
        this.doneTextView.setTextSize(1, 14.0f);
        this.doneTextView.setTextColor(-11420173);
        this.doneTextView.setGravity(17);
        this.doneTextView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_PICKER_SELECTOR_COLOR, 0));
        this.doneTextView.setPadding(AndroidUtilities.dp(20.0f), 0, AndroidUtilities.dp(20.0f), 0);
        this.doneTextView.setText(LocaleController.getString("Done", R.string.Done).toUpperCase());
        this.doneTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.toolsView.addView(this.doneTextView, LayoutHelper.createFrame(-2, -1, 53));
        this.paintButton = new ImageView(context);
        this.paintButton.setScaleType(ScaleType.CENTER);
        this.paintButton.setImageResource(R.drawable.photo_paint);
        this.paintButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.toolsView.addView(this.paintButton, LayoutHelper.createFrame(54, -1.0f, 17, 0.0f, 0.0f, 56.0f, 0.0f));
        this.paintButton.setOnClickListener(new C26987());
        ImageView stickerButton = new ImageView(context);
        stickerButton.setScaleType(ScaleType.CENTER);
        stickerButton.setImageResource(R.drawable.photo_sticker);
        stickerButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.toolsView.addView(stickerButton, LayoutHelper.createFrame(54, -1, 17));
        stickerButton.setOnClickListener(new C26998());
        ImageView textButton = new ImageView(context);
        textButton.setScaleType(ScaleType.CENTER);
        textButton.setImageResource(R.drawable.photo_paint_text);
        textButton.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.toolsView.addView(textButton, LayoutHelper.createFrame(54, -1.0f, 17, 56.0f, 0.0f, 0.0f, 0.0f));
        textButton.setOnClickListener(new C27009());
        this.colorPicker.setUndoEnabled(false);
        setCurrentSwatch(this.colorPicker.getSwatch(), false);
        updateSettingsButton();
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.currentEntityView != null) {
            if (this.editingText) {
                closeTextEnter(true);
            } else {
                selectEntity(null);
            }
        }
        return true;
    }

    private Size getPaintingSize() {
        if (this.paintingSize != null) {
            return this.paintingSize;
        }
        float width = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
        float height = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        Size size = new Size(width, height);
        size.width = 1280.0f;
        size.height = (float) Math.floor((double) ((size.width * height) / width));
        if (size.height > 1280.0f) {
            size.height = 1280.0f;
            size.width = (float) Math.floor((double) ((size.height * width) / height));
        }
        this.paintingSize = size;
        return size;
    }

    private boolean isSidewardOrientation() {
        return this.orientation % 360 == 90 || this.orientation % 360 == 270;
    }

    private void updateSettingsButton() {
        int resource = R.drawable.photo_paint_brush;
        if (this.currentEntityView != null) {
            if (this.currentEntityView instanceof StickerView) {
                resource = R.drawable.photo_flip;
            } else if (this.currentEntityView instanceof TextPaintView) {
                resource = R.drawable.photo_outline;
            }
            this.paintButton.setImageResource(R.drawable.photo_paint);
            this.paintButton.setColorFilter(null);
        } else {
            this.paintButton.setColorFilter(new PorterDuffColorFilter(-11420173, Mode.MULTIPLY));
            this.paintButton.setImageResource(R.drawable.photo_paint);
        }
        this.colorPicker.setSettingsButtonImage(resource);
    }

    public void init() {
        this.renderView.setVisibility(0);
        detectFaces();
    }

    public void shutdown() {
        this.renderView.shutdown();
        this.entitiesView.setVisibility(8);
        this.selectionContainerView.setVisibility(8);
        this.queue.postRunnable(new Runnable() {
            public void run() {
                Looper looper = Looper.myLooper();
                if (looper != null) {
                    looper.quit();
                }
            }
        });
    }

    public FrameLayout getToolsView() {
        return this.toolsView;
    }

    public TextView getDoneTextView() {
        return this.doneTextView;
    }

    public TextView getCancelTextView() {
        return this.cancelTextView;
    }

    public ColorPicker getColorPicker() {
        return this.colorPicker;
    }

    private boolean hasChanges() {
        return this.undoStore.canUndo() || this.entitiesView.entitiesCount() > 0;
    }

    public Bitmap getBitmap() {
        Bitmap bitmap = this.renderView.getResultBitmap();
        if (bitmap != null && this.entitiesView.entitiesCount() > 0) {
            Canvas canvas = new Canvas(bitmap);
            for (int i = 0; i < this.entitiesView.getChildCount(); i++) {
                View v = this.entitiesView.getChildAt(i);
                canvas.save();
                if (v instanceof EntityView) {
                    EntityView entity = (EntityView) v;
                    canvas.translate(entity.getPosition().f105x, entity.getPosition().f106y);
                    canvas.scale(v.getScaleX(), v.getScaleY());
                    canvas.rotate(v.getRotation());
                    canvas.translate((float) ((-entity.getWidth()) / 2), (float) ((-entity.getHeight()) / 2));
                    if (v instanceof TextPaintView) {
                        Bitmap b = Bitmaps.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
                        Canvas c = new Canvas(b);
                        v.draw(c);
                        canvas.drawBitmap(b, null, new Rect(0, 0, b.getWidth(), b.getHeight()), null);
                        try {
                            c.setBitmap(null);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                        b.recycle();
                    } else {
                        v.draw(canvas);
                    }
                }
                canvas.restore();
            }
        }
        return bitmap;
    }

    public void maybeShowDismissalAlert(PhotoViewer photoViewer, Activity parentActivity, final Runnable okRunnable) {
        if (this.editingText) {
            closeTextEnter(false);
        } else if (this.pickingSticker) {
            closeStickersView();
        } else if (!hasChanges()) {
            okRunnable.run();
        } else if (parentActivity != null) {
            Builder builder = new Builder(parentActivity);
            builder.setMessage(LocaleController.getString("DiscardChanges", R.string.DiscardChanges));
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    okRunnable.run();
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            photoViewer.showAlertDialog(builder);
        }
    }

    private void setCurrentSwatch(Swatch swatch, boolean updateInterface) {
        this.renderView.setColor(swatch.color);
        this.renderView.setBrushSize(swatch.brushWeight);
        if (updateInterface) {
            this.colorPicker.setSwatch(swatch);
        }
        if (this.currentEntityView instanceof TextPaintView) {
            ((TextPaintView) this.currentEntityView).setSwatch(swatch);
        }
    }

    private void setDimVisibility(final boolean visible) {
        Animator animator;
        if (visible) {
            this.dimView.setVisibility(0);
            animator = ObjectAnimator.ofFloat(this.dimView, "alpha", new float[]{0.0f, 1.0f});
        } else {
            animator = ObjectAnimator.ofFloat(this.dimView, "alpha", new float[]{1.0f, 0.0f});
        }
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (!visible) {
                    PhotoPaintView.this.dimView.setVisibility(8);
                }
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    private void setTextDimVisibility(final boolean visible, EntityView view) {
        boolean z;
        Animator animator;
        if (visible && view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (this.textDimView.getParent() != null) {
                ((EntitiesContainerView) this.textDimView.getParent()).removeView(this.textDimView);
            }
            parent.addView(this.textDimView, parent.indexOfChild(view));
        }
        if (visible) {
            z = false;
        } else {
            z = true;
        }
        view.setSelectionVisibility(z);
        if (visible) {
            this.textDimView.setVisibility(0);
            animator = ObjectAnimator.ofFloat(this.textDimView, "alpha", new float[]{0.0f, 1.0f});
        } else {
            animator = ObjectAnimator.ofFloat(this.textDimView, "alpha", new float[]{1.0f, 0.0f});
        }
        animator.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (!visible) {
                    PhotoPaintView.this.textDimView.setVisibility(8);
                    if (PhotoPaintView.this.textDimView.getParent() != null) {
                        ((EntitiesContainerView) PhotoPaintView.this.textDimView.getParent()).removeView(PhotoPaintView.this.textDimView);
                    }
                }
            }
        });
        animator.setDuration(200);
        animator.start();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        float bitmapW;
        float bitmapH;
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        int maxHeight = (AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(48.0f);
        if (this.bitmapToEdit != null) {
            bitmapW = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
            bitmapH = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        } else {
            bitmapW = (float) width;
            bitmapH = (float) ((height - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(48.0f));
        }
        float renderWidth = (float) width;
        float renderHeight = (float) Math.floor((double) ((renderWidth * bitmapH) / bitmapW));
        if (renderHeight > ((float) maxHeight)) {
            renderHeight = (float) maxHeight;
            renderWidth = (float) Math.floor((double) ((renderHeight * bitmapW) / bitmapH));
        }
        this.renderView.measure(MeasureSpec.makeMeasureSpec((int) renderWidth, 1073741824), MeasureSpec.makeMeasureSpec((int) renderHeight, 1073741824));
        this.entitiesView.measure(MeasureSpec.makeMeasureSpec((int) this.paintingSize.width, 1073741824), MeasureSpec.makeMeasureSpec((int) this.paintingSize.height, 1073741824));
        this.dimView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(maxHeight, Integer.MIN_VALUE));
        this.selectionContainerView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(maxHeight, 1073741824));
        this.colorPicker.measure(MeasureSpec.makeMeasureSpec(width, 1073741824), MeasureSpec.makeMeasureSpec(maxHeight, 1073741824));
        this.toolsView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        if (this.stickersView != null) {
            this.stickersView.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(AndroidUtilities.displaySize.y, 1073741824));
        }
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        float bitmapW;
        float bitmapH;
        int width = right - left;
        int height = bottom - top;
        int status = VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0;
        int actionBarHeight = ActionBar.getCurrentActionBarHeight();
        int actionBarHeight2 = ActionBar.getCurrentActionBarHeight() + status;
        int maxHeight = (AndroidUtilities.displaySize.y - actionBarHeight) - AndroidUtilities.dp(48.0f);
        if (this.bitmapToEdit != null) {
            bitmapW = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
            bitmapH = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        } else {
            bitmapW = (float) width;
            bitmapH = (float) ((height - actionBarHeight) - AndroidUtilities.dp(48.0f));
        }
        float renderWidth = (float) width;
        if (((float) Math.floor((double) ((renderWidth * bitmapH) / bitmapW))) > ((float) maxHeight)) {
            renderWidth = (float) Math.floor((double) ((((float) maxHeight) * bitmapW) / bitmapH));
        }
        int x = (int) Math.ceil((double) ((width - this.renderView.getMeasuredWidth()) / 2));
        int y = ((((((height - actionBarHeight2) - AndroidUtilities.dp(48.0f)) - this.renderView.getMeasuredHeight()) / 2) + actionBarHeight2) - ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.dp(8.0f);
        this.renderView.layout(x, y, this.renderView.getMeasuredWidth() + x, this.renderView.getMeasuredHeight() + y);
        float scale = renderWidth / this.paintingSize.width;
        this.entitiesView.setScaleX(scale);
        this.entitiesView.setScaleY(scale);
        this.entitiesView.layout(x, y, this.entitiesView.getMeasuredWidth() + x, this.entitiesView.getMeasuredHeight() + y);
        this.dimView.layout(0, status, this.dimView.getMeasuredWidth(), this.dimView.getMeasuredHeight() + status);
        this.selectionContainerView.layout(0, status, this.selectionContainerView.getMeasuredWidth(), this.selectionContainerView.getMeasuredHeight() + status);
        this.colorPicker.layout(0, actionBarHeight2, this.colorPicker.getMeasuredWidth(), this.colorPicker.getMeasuredHeight() + actionBarHeight2);
        this.toolsView.layout(0, height - this.toolsView.getMeasuredHeight(), this.toolsView.getMeasuredWidth(), height);
        this.curtainView.layout(0, 0, width, maxHeight);
        if (this.stickersView != null) {
            this.stickersView.layout(0, status, this.stickersView.getMeasuredWidth(), this.stickersView.getMeasuredHeight() + status);
        }
        if (this.currentEntityView != null) {
            this.currentEntityView.updateSelectionView();
            this.currentEntityView.setOffset(this.entitiesView.getLeft() - this.selectionContainerView.getLeft(), this.entitiesView.getTop() - this.selectionContainerView.getTop());
        }
    }

    public boolean onEntitySelected(EntityView entityView) {
        return selectEntity(entityView);
    }

    public boolean onEntityLongClicked(EntityView entityView) {
        showMenuForEntity(entityView);
        return true;
    }

    public boolean allowInteraction(EntityView entityView) {
        return !this.editingText;
    }

    private Point centerPositionForEntity() {
        Size paintingSize = getPaintingSize();
        return new Point(paintingSize.width / 2.0f, paintingSize.height / 2.0f);
    }

    private Point startPositionRelativeToEntity(EntityView entityView) {
        if (entityView != null) {
            Point position = entityView.getPosition();
            return new Point(position.f105x + 200.0f, position.f106y + 200.0f);
        }
        position = centerPositionForEntity();
        while (true) {
            boolean occupied = false;
            for (int index = 0; index < this.entitiesView.getChildCount(); index++) {
                View view = this.entitiesView.getChildAt(index);
                if (view instanceof EntityView) {
                    Point location = ((EntityView) view).getPosition();
                    if (((float) Math.sqrt(Math.pow((double) (location.f105x - position.f105x), 2.0d) + Math.pow((double) (location.f106y - position.f106y), 2.0d))) < 100.0f) {
                        occupied = true;
                    }
                }
            }
            if (!occupied) {
                return position;
            }
            position = new Point(position.f105x + 200.0f, position.f106y + 200.0f);
        }
    }

    public ArrayList<TLRPC$InputDocument> getMasks() {
        ArrayList<TLRPC$InputDocument> result = null;
        int count = this.entitiesView.getChildCount();
        for (int a = 0; a < count; a++) {
            View child = this.entitiesView.getChildAt(a);
            if (child instanceof StickerView) {
                TLRPC$Document document = ((StickerView) child).getSticker();
                if (result == null) {
                    result = new ArrayList();
                }
                TLRPC$TL_inputDocument inputDocument = new TLRPC$TL_inputDocument();
                inputDocument.id = document.id;
                inputDocument.access_hash = document.access_hash;
                result.add(inputDocument);
            }
        }
        return result;
    }

    private boolean selectEntity(EntityView entityView) {
        boolean changed = false;
        if (this.currentEntityView != null) {
            if (this.currentEntityView == entityView) {
                if (!this.editingText) {
                    showMenuForEntity(this.currentEntityView);
                }
                return true;
            }
            this.currentEntityView.deselect();
            changed = true;
        }
        this.currentEntityView = entityView;
        if (this.currentEntityView != null) {
            this.currentEntityView.select(this.selectionContainerView);
            this.entitiesView.bringViewToFront(this.currentEntityView);
            if (this.currentEntityView instanceof TextPaintView) {
                setCurrentSwatch(((TextPaintView) this.currentEntityView).getSwatch(), true);
            }
            changed = true;
        }
        updateSettingsButton();
        return changed;
    }

    private void removeEntity(EntityView entityView) {
        if (entityView == this.currentEntityView) {
            this.currentEntityView.deselect();
            if (this.editingText) {
                closeTextEnter(false);
            }
            this.currentEntityView = null;
            updateSettingsButton();
        }
        this.entitiesView.removeView(entityView);
        this.undoStore.unregisterUndo(entityView.getUUID());
    }

    private void duplicateSelectedEntity() {
        if (this.currentEntityView != null) {
            EntityView entityView = null;
            Point position = startPositionRelativeToEntity(this.currentEntityView);
            if (this.currentEntityView instanceof StickerView) {
                EntityView newStickerView = new StickerView(getContext(), (StickerView) this.currentEntityView, position);
                newStickerView.setDelegate(this);
                this.entitiesView.addView(newStickerView);
                entityView = newStickerView;
            } else if (this.currentEntityView instanceof TextPaintView) {
                EntityView newTextPaintView = new TextPaintView(getContext(), (TextPaintView) this.currentEntityView, position);
                newTextPaintView.setDelegate(this);
                newTextPaintView.setMaxWidth((int) (getPaintingSize().width - 20.0f));
                this.entitiesView.addView(newTextPaintView, LayoutHelper.createFrame(-2, -2.0f));
                entityView = newTextPaintView;
            }
            registerRemovalUndo(entityView);
            selectEntity(entityView);
            updateSettingsButton();
        }
    }

    private void openStickersView() {
        if (this.stickersView == null || this.stickersView.getVisibility() != 0) {
            this.pickingSticker = true;
            if (this.stickersView == null) {
                this.stickersView = new StickerMasksView(getContext());
                this.stickersView.setListener(new Listener() {
                    public void onStickerSelected(TLRPC$Document sticker) {
                        PhotoPaintView.this.closeStickersView();
                        PhotoPaintView.this.createSticker(sticker);
                    }

                    public void onTypeChanged() {
                    }
                });
                addView(this.stickersView, LayoutHelper.createFrame(-1, -1, 51));
            }
            this.stickersView.setVisibility(0);
            Animator a = ObjectAnimator.ofFloat(this.stickersView, "alpha", new float[]{0.0f, 1.0f});
            a.setDuration(200);
            a.start();
        }
    }

    private void closeStickersView() {
        if (this.stickersView != null && this.stickersView.getVisibility() == 0) {
            this.pickingSticker = false;
            Animator a = ObjectAnimator.ofFloat(this.stickersView, "alpha", new float[]{1.0f, 0.0f});
            a.setDuration(200);
            a.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    PhotoPaintView.this.stickersView.setVisibility(8);
                }
            });
            a.start();
        }
    }

    private Size baseStickerSize() {
        float side = (float) Math.floor(((double) getPaintingSize().width) * 0.5d);
        return new Size(side, side);
    }

    private void registerRemovalUndo(final EntityView entityView) {
        this.undoStore.registerUndo(entityView.getUUID(), new Runnable() {
            public void run() {
                PhotoPaintView.this.removeEntity(entityView);
            }
        });
    }

    private void createSticker(TLRPC$Document sticker) {
        StickerPosition position = calculateStickerPosition(sticker);
        StickerView view = new StickerView(getContext(), position.position, position.angle, position.scale, baseStickerSize(), sticker);
        view.setDelegate(this);
        this.entitiesView.addView(view);
        registerRemovalUndo(view);
        selectEntity(view);
    }

    private void mirrorSticker() {
        if (this.currentEntityView instanceof StickerView) {
            ((StickerView) this.currentEntityView).mirror();
        }
    }

    private int baseFontSize() {
        return (int) (getPaintingSize().width / 9.0f);
    }

    private void createText() {
        Swatch currentSwatch = this.colorPicker.getSwatch();
        Swatch whiteSwatch = new Swatch(-1, 1.0f, currentSwatch.brushWeight);
        Swatch blackSwatch = new Swatch(-16777216, 0.85f, currentSwatch.brushWeight);
        if (!this.selectedStroke) {
            blackSwatch = whiteSwatch;
        }
        setCurrentSwatch(blackSwatch, true);
        TextPaintView view = new TextPaintView(getContext(), startPositionRelativeToEntity(null), baseFontSize(), "", this.colorPicker.getSwatch(), this.selectedStroke);
        view.setDelegate(this);
        view.setMaxWidth((int) (getPaintingSize().width - 20.0f));
        this.entitiesView.addView(view, LayoutHelper.createFrame(-2, -2.0f));
        registerRemovalUndo(view);
        selectEntity(view);
        editSelectedTextEntity();
    }

    private void editSelectedTextEntity() {
        if ((this.currentEntityView instanceof TextPaintView) && !this.editingText) {
            this.curtainView.setVisibility(0);
            TextPaintView textPaintView = this.currentEntityView;
            this.initialText = textPaintView.getText();
            this.editingText = true;
            this.editedTextPosition = textPaintView.getPosition();
            this.editedTextRotation = textPaintView.getRotation();
            this.editedTextScale = textPaintView.getScale();
            textPaintView.setPosition(centerPositionForEntity());
            textPaintView.setRotation(0.0f);
            textPaintView.setScale(1.0f);
            this.toolsView.setVisibility(8);
            setTextDimVisibility(true, textPaintView);
            textPaintView.beginEditing();
            ((InputMethodManager) ApplicationLoader.applicationContext.getSystemService("input_method")).toggleSoftInputFromWindow(textPaintView.getFocusedView().getWindowToken(), 2, 0);
        }
    }

    public void closeTextEnter(boolean apply) {
        if (this.editingText && (this.currentEntityView instanceof TextPaintView)) {
            TextPaintView textPaintView = this.currentEntityView;
            this.toolsView.setVisibility(0);
            AndroidUtilities.hideKeyboard(textPaintView.getFocusedView());
            textPaintView.getFocusedView().clearFocus();
            textPaintView.endEditing();
            if (!apply) {
                textPaintView.setText(this.initialText);
            }
            if (textPaintView.getText().trim().length() == 0) {
                this.entitiesView.removeView(textPaintView);
                selectEntity(null);
            } else {
                textPaintView.setPosition(this.editedTextPosition);
                textPaintView.setRotation(this.editedTextRotation);
                textPaintView.setScale(this.editedTextScale);
                this.editedTextPosition = null;
                this.editedTextRotation = 0.0f;
                this.editedTextScale = 0.0f;
            }
            setTextDimVisibility(false, textPaintView);
            this.editingText = false;
            this.initialText = null;
            this.curtainView.setVisibility(8);
        }
    }

    private void setBrush(int brush) {
        RenderView renderView = this.renderView;
        Brush[] brushArr = this.brushes;
        this.currentBrush = brush;
        renderView.setBrush(brushArr[brush]);
    }

    private void setStroke(boolean stroke) {
        this.selectedStroke = stroke;
        if (this.currentEntityView instanceof TextPaintView) {
            Swatch currentSwatch = this.colorPicker.getSwatch();
            if (stroke && currentSwatch.color == -1) {
                setCurrentSwatch(new Swatch(-16777216, 0.85f, currentSwatch.brushWeight), true);
            } else if (!stroke && currentSwatch.color == -16777216) {
                setCurrentSwatch(new Swatch(-1, 1.0f, currentSwatch.brushWeight), true);
            }
            ((TextPaintView) this.currentEntityView).setStroke(stroke);
        }
    }

    private void showMenuForEntity(final EntityView entityView) {
        showPopup(new Runnable() {

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$1 */
            class C26891 implements OnClickListener {
                C26891() {
                }

                public void onClick(View v) {
                    PhotoPaintView.this.removeEntity(entityView);
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$2 */
            class C26902 implements OnClickListener {
                C26902() {
                }

                public void onClick(View v) {
                    PhotoPaintView.this.editSelectedTextEntity();
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$3 */
            class C26913 implements OnClickListener {
                C26913() {
                }

                public void onClick(View v) {
                    PhotoPaintView.this.duplicateSelectedEntity();
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            public void run() {
                LinearLayout parent = new LinearLayout(PhotoPaintView.this.getContext());
                parent.setOrientation(0);
                TextView deleteView = new TextView(PhotoPaintView.this.getContext());
                deleteView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                deleteView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                deleteView.setGravity(16);
                deleteView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(14.0f), 0);
                deleteView.setTextSize(1, 18.0f);
                deleteView.setTag(Integer.valueOf(0));
                deleteView.setText(LocaleController.getString("PaintDelete", R.string.PaintDelete));
                deleteView.setOnClickListener(new C26891());
                parent.addView(deleteView, LayoutHelper.createLinear(-2, 48));
                if (entityView instanceof TextPaintView) {
                    TextView editView = new TextView(PhotoPaintView.this.getContext());
                    editView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                    editView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    editView.setGravity(16);
                    editView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), 0);
                    editView.setTextSize(1, 18.0f);
                    editView.setTag(Integer.valueOf(1));
                    editView.setText(LocaleController.getString("PaintEdit", R.string.PaintEdit));
                    editView.setOnClickListener(new C26902());
                    parent.addView(editView, LayoutHelper.createLinear(-2, 48));
                }
                TextView duplicateView = new TextView(PhotoPaintView.this.getContext());
                duplicateView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                duplicateView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                duplicateView.setGravity(16);
                duplicateView.setPadding(AndroidUtilities.dp(14.0f), 0, AndroidUtilities.dp(16.0f), 0);
                duplicateView.setTextSize(1, 18.0f);
                duplicateView.setTag(Integer.valueOf(2));
                duplicateView.setText(LocaleController.getString("PaintDuplicate", R.string.PaintDuplicate));
                duplicateView.setOnClickListener(new C26913());
                parent.addView(duplicateView, LayoutHelper.createLinear(-2, 48));
                PhotoPaintView.this.popupLayout.addView(parent);
                LayoutParams params = (LayoutParams) parent.getLayoutParams();
                params.width = -2;
                params.height = -2;
                parent.setLayoutParams(params);
            }
        }, entityView, 17, (int) ((entityView.getPosition().f105x - ((float) (this.entitiesView.getWidth() / 2))) * this.entitiesView.getScaleX()), ((int) (((entityView.getPosition().f106y - ((((float) entityView.getHeight()) * entityView.getScale()) / 2.0f)) - ((float) (this.entitiesView.getHeight() / 2))) * this.entitiesView.getScaleY())) - AndroidUtilities.dp(32.0f));
    }

    private FrameLayout buttonForBrush(final int brush, int resource, boolean selected) {
        FrameLayout button = new FrameLayout(getContext());
        button.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoPaintView.this.setBrush(brush);
                if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                    PhotoPaintView.this.popupWindow.dismiss(true);
                }
            }
        });
        ImageView preview = new ImageView(getContext());
        preview.setImageResource(resource);
        button.addView(preview, LayoutHelper.createFrame(165, 44.0f, 19, 46.0f, 0.0f, 8.0f, 0.0f));
        if (selected) {
            ImageView check = new ImageView(getContext());
            check.setImageResource(R.drawable.ic_ab_done);
            check.setScaleType(ScaleType.CENTER);
            button.addView(check, LayoutHelper.createFrame(50, -1.0f));
        }
        return button;
    }

    private void showBrushSettings() {
        showPopup(new Runnable() {
            public void run() {
                boolean z;
                boolean z2 = true;
                PhotoPaintView photoPaintView = PhotoPaintView.this;
                if (PhotoPaintView.this.currentBrush == 0) {
                    z = true;
                } else {
                    z = false;
                }
                View radial = photoPaintView.buttonForBrush(0, R.drawable.paint_radial_preview, z);
                PhotoPaintView.this.popupLayout.addView(radial);
                LayoutParams layoutParams = (LayoutParams) radial.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                radial.setLayoutParams(layoutParams);
                photoPaintView = PhotoPaintView.this;
                if (PhotoPaintView.this.currentBrush == 1) {
                    z = true;
                } else {
                    z = false;
                }
                View elliptical = photoPaintView.buttonForBrush(1, R.drawable.paint_elliptical_preview, z);
                PhotoPaintView.this.popupLayout.addView(elliptical);
                layoutParams = (LayoutParams) elliptical.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                elliptical.setLayoutParams(layoutParams);
                PhotoPaintView photoPaintView2 = PhotoPaintView.this;
                if (PhotoPaintView.this.currentBrush != 2) {
                    z2 = false;
                }
                View neon = photoPaintView2.buttonForBrush(2, R.drawable.paint_neon_preview, z2);
                PhotoPaintView.this.popupLayout.addView(neon);
                layoutParams = (LayoutParams) neon.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                neon.setLayoutParams(layoutParams);
            }
        }, this, 85, 0, AndroidUtilities.dp(48.0f));
    }

    private FrameLayout buttonForText(final boolean stroke, String text, boolean selected) {
        int i = -16777216;
        FrameLayout button = new FrameLayout(getContext()) {
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                return true;
            }
        };
        button.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PhotoPaintView.this.setStroke(stroke);
                if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                    PhotoPaintView.this.popupWindow.dismiss(true);
                }
            }
        });
        EditTextOutline textView = new EditTextOutline(getContext());
        textView.setBackgroundColor(0);
        textView.setEnabled(false);
        textView.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        textView.setTextColor(stroke ? -1 : -16777216);
        if (!stroke) {
            i = 0;
        }
        textView.setStrokeColor(i);
        textView.setPadding(AndroidUtilities.dp(2.0f), 0, AndroidUtilities.dp(2.0f), 0);
        textView.setTextSize(1, 18.0f);
        textView.setTypeface(null, 1);
        textView.setTag(Boolean.valueOf(stroke));
        textView.setText(text);
        button.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 19, 46.0f, 0.0f, 16.0f, 0.0f));
        if (selected) {
            ImageView check = new ImageView(getContext());
            check.setImageResource(R.drawable.ic_ab_done);
            check.setScaleType(ScaleType.CENTER);
            button.addView(check, LayoutHelper.createFrame(50, -1.0f));
        }
        return button;
    }

    private void showTextSettings() {
        showPopup(new Runnable() {
            public void run() {
                boolean z = true;
                View outline = PhotoPaintView.this.buttonForText(true, LocaleController.getString("PaintOutlined", R.string.PaintOutlined), PhotoPaintView.this.selectedStroke);
                PhotoPaintView.this.popupLayout.addView(outline);
                LayoutParams layoutParams = (LayoutParams) outline.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(48.0f);
                outline.setLayoutParams(layoutParams);
                PhotoPaintView photoPaintView = PhotoPaintView.this;
                String string = LocaleController.getString("PaintRegular", R.string.PaintRegular);
                if (PhotoPaintView.this.selectedStroke) {
                    z = false;
                }
                View regular = photoPaintView.buttonForText(false, string, z);
                PhotoPaintView.this.popupLayout.addView(regular);
                layoutParams = (LayoutParams) regular.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(48.0f);
                regular.setLayoutParams(layoutParams);
            }
        }, this, 85, 0, AndroidUtilities.dp(48.0f));
    }

    private void showPopup(Runnable setupRunnable, View parent, int gravity, int x, int y) {
        if (this.popupWindow == null || !this.popupWindow.isShowing()) {
            if (this.popupLayout == null) {
                this.popupRect = new Rect();
                this.popupLayout = new ActionBarPopupWindowLayout(getContext());
                this.popupLayout.setAnimationEnabled(false);
                this.popupLayout.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getActionMasked() == 0 && PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                            v.getHitRect(PhotoPaintView.this.popupRect);
                            if (!PhotoPaintView.this.popupRect.contains((int) event.getX(), (int) event.getY())) {
                                PhotoPaintView.this.popupWindow.dismiss();
                            }
                        }
                        return false;
                    }
                });
                this.popupLayout.setDispatchKeyEventListener(new OnDispatchKeyEventListener() {
                    public void onDispatchKeyEvent(KeyEvent keyEvent) {
                        if (keyEvent.getKeyCode() == 4 && keyEvent.getRepeatCount() == 0 && PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                            PhotoPaintView.this.popupWindow.dismiss();
                        }
                    }
                });
                this.popupLayout.setShowedFromBotton(true);
            }
            this.popupLayout.removeInnerViews();
            setupRunnable.run();
            if (this.popupWindow == null) {
                this.popupWindow = new ActionBarPopupWindow(this.popupLayout, -2, -2);
                this.popupWindow.setAnimationEnabled(false);
                this.popupWindow.setAnimationStyle(R.style.PopupAnimation);
                this.popupWindow.setOutsideTouchable(true);
                this.popupWindow.setClippingEnabled(true);
                this.popupWindow.setInputMethodMode(2);
                this.popupWindow.setSoftInputMode(0);
                this.popupWindow.getContentView().setFocusableInTouchMode(true);
                this.popupWindow.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss() {
                        PhotoPaintView.this.popupLayout.removeInnerViews();
                    }
                });
            }
            this.popupLayout.measure(MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(1000.0f), Integer.MIN_VALUE));
            this.popupWindow.setFocusable(true);
            this.popupWindow.showAtLocation(parent, gravity, x, y);
            this.popupWindow.startAnimation();
            return;
        }
        this.popupWindow.dismiss();
    }

    private int getFrameRotation() {
        switch (this.orientation) {
            case 90:
                return 1;
            case 180:
                return 2;
            case 270:
                return 3;
            default:
                return 0;
        }
    }

    private void detectFaces() {
        this.queue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0030 in list [B:6:0x002d]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:43)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:282)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
*/
                /*
                r12 = this;
                r3 = 0;
                r10 = new com.google.android.gms.vision.face.FaceDetector$Builder;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = r11.getContext();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10.<init>(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = 1;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.setMode(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = 1;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.setLandmarkType(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = 0;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.setTrackingEnabled(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r3 = r10.build();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r3.isOperational();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r10 != 0) goto L_0x0031;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x0025:
                r10 = "face detection is not operational";	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                org.telegram.messenger.FileLog.e(r10);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r3 == 0) goto L_0x0030;
            L_0x002d:
                r3.release();
            L_0x0030:
                return;
            L_0x0031:
                r10 = new com.google.android.gms.vision.Frame$Builder;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = r11.bitmapToEdit;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.setBitmap(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = r11.getFrameRotation();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.setRotation(r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r5 = r10.build();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r4 = r3.detect(r5);	 Catch:{ Throwable -> 0x008b }
                r8 = new java.util.ArrayList;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r8.<init>();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r9 = r10.getPaintingSize();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r6 = 0;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x005e:
                r10 = r4.size();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r6 >= r10) goto L_0x0095;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x0064:
                r7 = r4.keyAt(r6);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r1 = r4.get(r7);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r1 = (com.google.android.gms.vision.face.Face) r1;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r2 = new org.telegram.ui.Components.Paint.PhotoFace;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r10.bitmapToEdit;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r11 = r11.isSidewardOrientation();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r2.<init>(r1, r10, r9, r11);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10 = r2.isSufficient();	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r10 == 0) goto L_0x0088;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x0085:
                r8.add(r2);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x0088:
                r6 = r6 + 1;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                goto L_0x005e;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
            L_0x008b:
                r0 = move-exception;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r3 == 0) goto L_0x0030;
            L_0x0091:
                r3.release();
                goto L_0x0030;
            L_0x0095:
                r10 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                r10.faces = r8;	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r3 == 0) goto L_0x0030;
            L_0x009c:
                r3.release();
                goto L_0x0030;
            L_0x00a0:
                r0 = move-exception;
                org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x00a0, all -> 0x00aa }
                if (r3 == 0) goto L_0x0030;
            L_0x00a6:
                r3.release();
                goto L_0x0030;
            L_0x00aa:
                r10 = move-exception;
                if (r3 == 0) goto L_0x00b0;
            L_0x00ad:
                r3.release();
            L_0x00b0:
                throw r10;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.PhotoPaintView.26.run():void");
            }
        });
    }

    private StickerPosition calculateStickerPosition(TLRPC$Document document) {
        TLRPC$TL_maskCoords maskCoords = null;
        for (int a = 0; a < document.attributes.size(); a++) {
            DocumentAttribute attribute = (DocumentAttribute) document.attributes.get(a);
            if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                maskCoords = attribute.mask_coords;
                break;
            }
        }
        StickerPosition defaultPosition = new StickerPosition(centerPositionForEntity(), 0.75f, 0.0f);
        if (maskCoords == null || this.faces == null || this.faces.size() == 0) {
            return defaultPosition;
        }
        int anchor = maskCoords.f82n;
        PhotoFace face = getRandomFaceWithVacantAnchor(anchor, document.id, maskCoords);
        if (face == null) {
            return defaultPosition;
        }
        Point referencePoint = face.getPointForAnchor(anchor);
        float referenceWidth = face.getWidthForAnchor(anchor);
        float angle = face.getAngle();
        float scale = (float) (((double) (referenceWidth / baseStickerSize().width)) * maskCoords.zoom);
        float radAngle = (float) Math.toRadians((double) angle);
        float yCompX = (float) ((Math.cos(1.5707963267948966d + ((double) radAngle)) * ((double) referenceWidth)) * maskCoords.f84y);
        float yCompY = (float) ((Math.sin(1.5707963267948966d + ((double) radAngle)) * ((double) referenceWidth)) * maskCoords.f84y);
        float x = (referencePoint.f105x + ((float) ((Math.sin(1.5707963267948966d - ((double) radAngle)) * ((double) referenceWidth)) * maskCoords.f83x))) + yCompX;
        return new StickerPosition(new Point(x, (referencePoint.f106y + ((float) ((Math.cos(1.5707963267948966d - ((double) radAngle)) * ((double) referenceWidth)) * maskCoords.f83x))) + yCompY), scale, angle);
    }

    private PhotoFace getRandomFaceWithVacantAnchor(int anchor, long documentId, TLRPC$TL_maskCoords maskCoords) {
        if (anchor < 0 || anchor > 3 || this.faces.isEmpty()) {
            return null;
        }
        int count = this.faces.size();
        int i = Utilities.random.nextInt(count);
        for (int remaining = count; remaining > 0; remaining--) {
            PhotoFace face = (PhotoFace) this.faces.get(i);
            if (!isFaceAnchorOccupied(face, anchor, documentId, maskCoords)) {
                return face;
            }
            i = (i + 1) % count;
        }
        return null;
    }

    private boolean isFaceAnchorOccupied(PhotoFace face, int anchor, long documentId, TLRPC$TL_maskCoords maskCoords) {
        Point anchorPoint = face.getPointForAnchor(anchor);
        if (anchorPoint == null) {
            return true;
        }
        float minDistance = face.getWidthForAnchor(0) * 1.1f;
        for (int index = 0; index < this.entitiesView.getChildCount(); index++) {
            View view = this.entitiesView.getChildAt(index);
            if (view instanceof StickerView) {
                StickerView stickerView = (StickerView) view;
                if (stickerView.getAnchor() == anchor) {
                    Point location = stickerView.getPosition();
                    float distance = (float) Math.hypot((double) (location.f105x - anchorPoint.f105x), (double) (location.f106y - anchorPoint.f106y));
                    if ((documentId == stickerView.getSticker().id || this.faces.size() > 1) && distance < minDistance) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }
}
