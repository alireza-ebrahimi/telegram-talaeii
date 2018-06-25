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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.Bitmaps;
import org.telegram.messenger.DispatchQueue;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_maskCoords;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputDocument;
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
    class C45301 implements UndoStoreDelegate {
        C45301() {
        }

        public void historyChanged() {
            PhotoPaintView.this.colorPicker.setUndoEnabled(PhotoPaintView.this.undoStore.canUndo());
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$2 */
    class C45312 implements RenderViewDelegate {
        C45312() {
        }

        public void onBeganDrawing() {
            if (PhotoPaintView.this.currentEntityView != null) {
                PhotoPaintView.this.selectEntity(null);
            }
        }

        public void onFinishedDrawing(boolean z) {
            PhotoPaintView.this.colorPicker.setUndoEnabled(PhotoPaintView.this.undoStore.canUndo());
        }

        public boolean shouldDraw() {
            boolean z = PhotoPaintView.this.currentEntityView == null;
            if (!z) {
                PhotoPaintView.this.selectEntity(null);
            }
            return z;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$3 */
    class C45323 implements EntitiesContainerViewDelegate {
        C45323() {
        }

        public void onEntityDeselect() {
            PhotoPaintView.this.selectEntity(null);
        }

        public EntityView onSelectedEntityRequest() {
            return PhotoPaintView.this.currentEntityView;
        }

        public boolean shouldReceiveTouches() {
            return PhotoPaintView.this.textDimView.getVisibility() != 0;
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$4 */
    class C45334 implements OnClickListener {
        C45334() {
        }

        public void onClick(View view) {
            PhotoPaintView.this.closeTextEnter(true);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$6 */
    class C45356 implements ColorPickerDelegate {
        C45356() {
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
    class C45367 implements OnClickListener {
        C45367() {
        }

        public void onClick(View view) {
            PhotoPaintView.this.selectEntity(null);
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$8 */
    class C45378 implements OnClickListener {
        C45378() {
        }

        public void onClick(View view) {
            PhotoPaintView.this.openStickersView();
        }
    }

    /* renamed from: org.telegram.ui.Components.PhotoPaintView$9 */
    class C45389 implements OnClickListener {
        C45389() {
        }

        public void onClick(View view) {
            PhotoPaintView.this.createText();
        }
    }

    private class StickerPosition {
        private float angle;
        private Point position;
        private float scale;

        StickerPosition(Point point, float f, float f2) {
            this.position = point;
            this.scale = f;
            this.angle = f2;
        }
    }

    public PhotoPaintView(Context context, Bitmap bitmap, int i) {
        super(context);
        this.bitmapToEdit = bitmap;
        this.orientation = i;
        this.undoStore = new UndoStore();
        this.undoStore.setDelegate(new C45301());
        this.curtainView = new FrameLayout(context);
        this.curtainView.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        this.curtainView.setVisibility(4);
        addView(this.curtainView);
        this.renderView = new RenderView(context, new Painting(getPaintingSize()), bitmap, this.orientation);
        this.renderView.setDelegate(new C45312());
        this.renderView.setUndoStore(this.undoStore);
        this.renderView.setQueue(this.queue);
        this.renderView.setVisibility(4);
        this.renderView.setBrush(this.brushes[0]);
        addView(this.renderView, LayoutHelper.createFrame(-1, -1, 51));
        this.entitiesView = new EntitiesContainerView(context, new C45323());
        this.entitiesView.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.entitiesView.setPivotY(BitmapDescriptorFactory.HUE_RED);
        addView(this.entitiesView);
        this.dimView = new FrameLayout(context);
        this.dimView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.dimView.setBackgroundColor(1711276032);
        this.dimView.setVisibility(8);
        addView(this.dimView);
        this.textDimView = new FrameLayout(context);
        this.textDimView.setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.textDimView.setBackgroundColor(1711276032);
        this.textDimView.setVisibility(8);
        this.textDimView.setOnClickListener(new C45334());
        this.selectionContainerView = new FrameLayout(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return false;
            }
        };
        addView(this.selectionContainerView);
        this.colorPicker = new ColorPicker(context);
        addView(this.colorPicker);
        this.colorPicker.setDelegate(new C45356());
        this.toolsView = new FrameLayout(context);
        this.toolsView.setBackgroundColor(Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
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
        this.toolsView.addView(this.paintButton, LayoutHelper.createFrame(54, -1.0f, 17, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 56.0f, BitmapDescriptorFactory.HUE_RED));
        this.paintButton.setOnClickListener(new C45367());
        View imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageResource(R.drawable.photo_sticker);
        imageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.toolsView.addView(imageView, LayoutHelper.createFrame(54, -1, 17));
        imageView.setOnClickListener(new C45378());
        imageView = new ImageView(context);
        imageView.setScaleType(ScaleType.CENTER);
        imageView.setImageResource(R.drawable.photo_paint_text);
        imageView.setBackgroundDrawable(Theme.createSelectorDrawable(Theme.ACTION_BAR_WHITE_SELECTOR_COLOR));
        this.toolsView.addView(imageView, LayoutHelper.createFrame(54, -1.0f, 17, 56.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        imageView.setOnClickListener(new C45389());
        this.colorPicker.setUndoEnabled(false);
        setCurrentSwatch(this.colorPicker.getSwatch(), false);
        updateSettingsButton();
    }

    private int baseFontSize() {
        return (int) (getPaintingSize().width / 9.0f);
    }

    private Size baseStickerSize() {
        float floor = (float) Math.floor(((double) getPaintingSize().width) * 0.5d);
        return new Size(floor, floor);
    }

    private FrameLayout buttonForBrush(final int i, int i2, boolean z) {
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        frameLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PhotoPaintView.this.setBrush(i);
                if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                    PhotoPaintView.this.popupWindow.dismiss(true);
                }
            }
        });
        View imageView = new ImageView(getContext());
        imageView.setImageResource(i2);
        frameLayout.addView(imageView, LayoutHelper.createFrame(165, 44.0f, 19, 46.0f, BitmapDescriptorFactory.HUE_RED, 8.0f, BitmapDescriptorFactory.HUE_RED));
        if (z) {
            View imageView2 = new ImageView(getContext());
            imageView2.setImageResource(R.drawable.ic_ab_done);
            imageView2.setScaleType(ScaleType.CENTER);
            frameLayout.addView(imageView2, LayoutHelper.createFrame(50, -1.0f));
        }
        return frameLayout;
    }

    private FrameLayout buttonForText(final boolean z, String str, boolean z2) {
        int i = Theme.ACTION_BAR_VIDEO_EDIT_COLOR;
        FrameLayout anonymousClass20 = new FrameLayout(getContext()) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return true;
            }
        };
        anonymousClass20.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        anonymousClass20.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                PhotoPaintView.this.setStroke(z);
                if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                    PhotoPaintView.this.popupWindow.dismiss(true);
                }
            }
        });
        View editTextOutline = new EditTextOutline(getContext());
        editTextOutline.setBackgroundColor(0);
        editTextOutline.setEnabled(false);
        editTextOutline.setStrokeWidth((float) AndroidUtilities.dp(3.0f));
        editTextOutline.setTextColor(z ? -1 : Theme.ACTION_BAR_VIDEO_EDIT_COLOR);
        if (!z) {
            i = 0;
        }
        editTextOutline.setStrokeColor(i);
        editTextOutline.setPadding(AndroidUtilities.dp(2.0f), 0, AndroidUtilities.dp(2.0f), 0);
        editTextOutline.setTextSize(1, 18.0f);
        editTextOutline.setTypeface(null, 1);
        editTextOutline.setTag(Boolean.valueOf(z));
        editTextOutline.setText(str);
        anonymousClass20.addView(editTextOutline, LayoutHelper.createFrame(-2, -2.0f, 19, 46.0f, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
        if (z2) {
            View imageView = new ImageView(getContext());
            imageView.setImageResource(R.drawable.ic_ab_done);
            imageView.setScaleType(ScaleType.CENTER);
            anonymousClass20.addView(imageView, LayoutHelper.createFrame(50, -1.0f));
        }
        return anonymousClass20;
    }

    private StickerPosition calculateStickerPosition(Document document) {
        TLRPC$TL_maskCoords tLRPC$TL_maskCoords;
        for (int i = 0; i < document.attributes.size(); i++) {
            DocumentAttribute documentAttribute = (DocumentAttribute) document.attributes.get(i);
            if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                tLRPC$TL_maskCoords = documentAttribute.mask_coords;
                break;
            }
        }
        tLRPC$TL_maskCoords = null;
        StickerPosition stickerPosition = new StickerPosition(centerPositionForEntity(), 0.75f, BitmapDescriptorFactory.HUE_RED);
        if (tLRPC$TL_maskCoords == null || this.faces == null || this.faces.size() == 0) {
            return stickerPosition;
        }
        int i2 = tLRPC$TL_maskCoords.f10161n;
        PhotoFace randomFaceWithVacantAnchor = getRandomFaceWithVacantAnchor(i2, document.id, tLRPC$TL_maskCoords);
        if (randomFaceWithVacantAnchor == null) {
            return stickerPosition;
        }
        Point pointForAnchor = randomFaceWithVacantAnchor.getPointForAnchor(i2);
        float widthForAnchor = randomFaceWithVacantAnchor.getWidthForAnchor(i2);
        float angle = randomFaceWithVacantAnchor.getAngle();
        float toRadians = (float) Math.toRadians((double) angle);
        float cos = (float) ((Math.cos(1.5707963267948966d - ((double) toRadians)) * ((double) widthForAnchor)) * tLRPC$TL_maskCoords.f10162x);
        return new StickerPosition(new Point((pointForAnchor.f10184x + ((float) ((Math.sin(1.5707963267948966d - ((double) toRadians)) * ((double) widthForAnchor)) * tLRPC$TL_maskCoords.f10162x))) + ((float) ((Math.cos(((double) toRadians) + 1.5707963267948966d) * ((double) widthForAnchor)) * tLRPC$TL_maskCoords.f10163y)), (pointForAnchor.f10185y + cos) + ((float) ((Math.sin(((double) toRadians) + 1.5707963267948966d) * ((double) widthForAnchor)) * tLRPC$TL_maskCoords.f10163y))), (float) (((double) (widthForAnchor / baseStickerSize().width)) * tLRPC$TL_maskCoords.zoom), angle);
    }

    private Point centerPositionForEntity() {
        Size paintingSize = getPaintingSize();
        return new Point(paintingSize.width / 2.0f, paintingSize.height / 2.0f);
    }

    private void closeStickersView() {
        if (this.stickersView != null && this.stickersView.getVisibility() == 0) {
            this.pickingSticker = false;
            Animator ofFloat = ObjectAnimator.ofFloat(this.stickersView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
            ofFloat.setDuration(200);
            ofFloat.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animator) {
                    PhotoPaintView.this.stickersView.setVisibility(8);
                }
            });
            ofFloat.start();
        }
    }

    private void createSticker(Document document) {
        StickerPosition calculateStickerPosition = calculateStickerPosition(document);
        View stickerView = new StickerView(getContext(), calculateStickerPosition.position, calculateStickerPosition.angle, calculateStickerPosition.scale, baseStickerSize(), document);
        stickerView.setDelegate(this);
        this.entitiesView.addView(stickerView);
        registerRemovalUndo(stickerView);
        selectEntity(stickerView);
    }

    private void createText() {
        Swatch swatch = this.colorPicker.getSwatch();
        Swatch swatch2 = new Swatch(-1, 1.0f, swatch.brushWeight);
        Swatch swatch3 = new Swatch(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, 0.85f, swatch.brushWeight);
        if (!this.selectedStroke) {
            swatch3 = swatch2;
        }
        setCurrentSwatch(swatch3, true);
        View textPaintView = new TextPaintView(getContext(), startPositionRelativeToEntity(null), baseFontSize(), TtmlNode.ANONYMOUS_REGION_ID, this.colorPicker.getSwatch(), this.selectedStroke);
        textPaintView.setDelegate(this);
        textPaintView.setMaxWidth((int) (getPaintingSize().width - 20.0f));
        this.entitiesView.addView(textPaintView, LayoutHelper.createFrame(-2, -2.0f));
        registerRemovalUndo(textPaintView);
        selectEntity(textPaintView);
        editSelectedTextEntity();
    }

    private void detectFaces() {
        this.queue.postRunnable(new Runnable() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0031 in list [B:6:0x002e]
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
                r9 = this;
                r0 = 0;
                r1 = 0;
                r2 = new com.google.android.gms.vision.face.FaceDetector$Builder;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = r3.getContext();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2.<init>(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = 1;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.setMode(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = 1;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.setLandmarkType(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = 0;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.setTrackingEnabled(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r1 = r2.build();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r1.isOperational();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r2 != 0) goto L_0x0032;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x0026:
                r0 = "face detection is not operational";	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r1 == 0) goto L_0x0031;
            L_0x002e:
                r1.release();
            L_0x0031:
                return;
            L_0x0032:
                r2 = new com.google.android.gms.vision.Frame$Builder;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2.<init>();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = r3.bitmapToEdit;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.setBitmap(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = r3.getFrameRotation();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.setRotation(r3);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r2.build();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r3 = r1.detect(r2);	 Catch:{ Throwable -> 0x008d }
                r4 = new java.util.ArrayList;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r4.<init>();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r5 = r2.getPaintingSize();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r0;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x005f:
                r0 = r3.size();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r2 >= r0) goto L_0x0097;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x0065:
                r0 = r3.keyAt(r2);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r0 = r3.get(r0);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r0 = (com.google.android.gms.vision.face.Face) r0;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r6 = new org.telegram.ui.Components.Paint.PhotoFace;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r7 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r7 = r7.bitmapToEdit;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r8 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r8 = r8.isSidewardOrientation();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r6.<init>(r0, r7, r5, r8);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r0 = r6.isSufficient();	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r0 == 0) goto L_0x0089;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x0086:
                r4.add(r6);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x0089:
                r0 = r2 + 1;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r2 = r0;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                goto L_0x005f;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
            L_0x008d:
                r0 = move-exception;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r1 == 0) goto L_0x0031;
            L_0x0093:
                r1.release();
                goto L_0x0031;
            L_0x0097:
                r0 = org.telegram.ui.Components.PhotoPaintView.this;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                r0.faces = r4;	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r1 == 0) goto L_0x0031;
            L_0x009e:
                r1.release();
                goto L_0x0031;
            L_0x00a2:
                r0 = move-exception;
                org.telegram.messenger.FileLog.e(r0);	 Catch:{ Exception -> 0x00a2, all -> 0x00ac }
                if (r1 == 0) goto L_0x0031;
            L_0x00a8:
                r1.release();
                goto L_0x0031;
            L_0x00ac:
                r0 = move-exception;
                if (r1 == 0) goto L_0x00b2;
            L_0x00af:
                r1.release();
            L_0x00b2:
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: org.telegram.ui.Components.PhotoPaintView.26.run():void");
            }
        });
    }

    private void duplicateSelectedEntity() {
        if (this.currentEntityView != null) {
            EntityView entityView = null;
            Point startPositionRelativeToEntity = startPositionRelativeToEntity(this.currentEntityView);
            View stickerView;
            if (this.currentEntityView instanceof StickerView) {
                stickerView = new StickerView(getContext(), (StickerView) this.currentEntityView, startPositionRelativeToEntity);
                stickerView.setDelegate(this);
                this.entitiesView.addView(stickerView);
                entityView = stickerView;
            } else if (this.currentEntityView instanceof TextPaintView) {
                stickerView = new TextPaintView(getContext(), (TextPaintView) this.currentEntityView, startPositionRelativeToEntity);
                stickerView.setDelegate(this);
                stickerView.setMaxWidth((int) (getPaintingSize().width - 20.0f));
                this.entitiesView.addView(stickerView, LayoutHelper.createFrame(-2, -2.0f));
                View view = stickerView;
            }
            registerRemovalUndo(entityView);
            selectEntity(entityView);
            updateSettingsButton();
        }
    }

    private void editSelectedTextEntity() {
        if ((this.currentEntityView instanceof TextPaintView) && !this.editingText) {
            this.curtainView.setVisibility(0);
            TextPaintView textPaintView = (TextPaintView) this.currentEntityView;
            this.initialText = textPaintView.getText();
            this.editingText = true;
            this.editedTextPosition = textPaintView.getPosition();
            this.editedTextRotation = textPaintView.getRotation();
            this.editedTextScale = textPaintView.getScale();
            textPaintView.setPosition(centerPositionForEntity());
            textPaintView.setRotation(BitmapDescriptorFactory.HUE_RED);
            textPaintView.setScale(1.0f);
            this.toolsView.setVisibility(8);
            setTextDimVisibility(true, textPaintView);
            textPaintView.beginEditing();
            ((InputMethodManager) ApplicationLoader.applicationContext.getSystemService("input_method")).toggleSoftInputFromWindow(textPaintView.getFocusedView().getWindowToken(), 2, 0);
        }
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

    private Size getPaintingSize() {
        if (this.paintingSize != null) {
            return this.paintingSize;
        }
        float height = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
        float width = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        Size size = new Size(height, width);
        size.width = 1280.0f;
        size.height = (float) Math.floor((double) ((size.width * width) / height));
        if (size.height > 1280.0f) {
            size.height = 1280.0f;
            size.width = (float) Math.floor((double) ((height * size.height) / width));
        }
        this.paintingSize = size;
        return size;
    }

    private PhotoFace getRandomFaceWithVacantAnchor(int i, long j, TLRPC$TL_maskCoords tLRPC$TL_maskCoords) {
        if (i < 0 || i > 3 || this.faces.isEmpty()) {
            return null;
        }
        int size = this.faces.size();
        int nextInt = Utilities.random.nextInt(size);
        for (int i2 = size; i2 > 0; i2--) {
            PhotoFace photoFace = (PhotoFace) this.faces.get(nextInt);
            if (!isFaceAnchorOccupied(photoFace, i, j, tLRPC$TL_maskCoords)) {
                return photoFace;
            }
            nextInt = (nextInt + 1) % size;
        }
        return null;
    }

    private boolean hasChanges() {
        return this.undoStore.canUndo() || this.entitiesView.entitiesCount() > 0;
    }

    private boolean isFaceAnchorOccupied(PhotoFace photoFace, int i, long j, TLRPC$TL_maskCoords tLRPC$TL_maskCoords) {
        Point pointForAnchor = photoFace.getPointForAnchor(i);
        if (pointForAnchor == null) {
            return true;
        }
        float widthForAnchor = photoFace.getWidthForAnchor(0) * 1.1f;
        for (int i2 = 0; i2 < this.entitiesView.getChildCount(); i2++) {
            View childAt = this.entitiesView.getChildAt(i2);
            if (childAt instanceof StickerView) {
                StickerView stickerView = (StickerView) childAt;
                if (stickerView.getAnchor() == i) {
                    Point position = stickerView.getPosition();
                    float hypot = (float) Math.hypot((double) (position.f10184x - pointForAnchor.f10184x), (double) (position.f10185y - pointForAnchor.f10185y));
                    if ((j == stickerView.getSticker().id || this.faces.size() > 1) && hypot < widthForAnchor) {
                        return true;
                    }
                }
                continue;
            }
        }
        return false;
    }

    private boolean isSidewardOrientation() {
        return this.orientation % 360 == 90 || this.orientation % 360 == 270;
    }

    private void mirrorSticker() {
        if (this.currentEntityView instanceof StickerView) {
            ((StickerView) this.currentEntityView).mirror();
        }
    }

    private void openStickersView() {
        if (this.stickersView == null || this.stickersView.getVisibility() != 0) {
            this.pickingSticker = true;
            if (this.stickersView == null) {
                this.stickersView = new StickerMasksView(getContext());
                this.stickersView.setListener(new Listener() {
                    public void onStickerSelected(Document document) {
                        PhotoPaintView.this.closeStickersView();
                        PhotoPaintView.this.createSticker(document);
                    }

                    public void onTypeChanged() {
                    }
                });
                addView(this.stickersView, LayoutHelper.createFrame(-1, -1, 51));
            }
            this.stickersView.setVisibility(0);
            Animator ofFloat = ObjectAnimator.ofFloat(this.stickersView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
            ofFloat.setDuration(200);
            ofFloat.start();
        }
    }

    private void registerRemovalUndo(final EntityView entityView) {
        this.undoStore.registerUndo(entityView.getUUID(), new Runnable() {
            public void run() {
                PhotoPaintView.this.removeEntity(entityView);
            }
        });
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

    private boolean selectEntity(EntityView entityView) {
        boolean z = true;
        boolean z2 = false;
        if (this.currentEntityView != null) {
            if (this.currentEntityView == entityView) {
                if (!this.editingText) {
                    showMenuForEntity(this.currentEntityView);
                }
                return z;
            }
            this.currentEntityView.deselect();
            z2 = true;
        }
        this.currentEntityView = entityView;
        if (this.currentEntityView != null) {
            this.currentEntityView.select(this.selectionContainerView);
            this.entitiesView.bringViewToFront(this.currentEntityView);
            if (this.currentEntityView instanceof TextPaintView) {
                setCurrentSwatch(((TextPaintView) this.currentEntityView).getSwatch(), true);
            }
        } else {
            z = z2;
        }
        updateSettingsButton();
        return z;
    }

    private void setBrush(int i) {
        RenderView renderView = this.renderView;
        Brush[] brushArr = this.brushes;
        this.currentBrush = i;
        renderView.setBrush(brushArr[i]);
    }

    private void setCurrentSwatch(Swatch swatch, boolean z) {
        this.renderView.setColor(swatch.color);
        this.renderView.setBrushSize(swatch.brushWeight);
        if (z) {
            this.colorPicker.setSwatch(swatch);
        }
        if (this.currentEntityView instanceof TextPaintView) {
            ((TextPaintView) this.currentEntityView).setSwatch(swatch);
        }
    }

    private void setDimVisibility(final boolean z) {
        Animator ofFloat;
        if (z) {
            this.dimView.setVisibility(0);
            ofFloat = ObjectAnimator.ofFloat(this.dimView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        } else {
            ofFloat = ObjectAnimator.ofFloat(this.dimView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
        }
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (!z) {
                    PhotoPaintView.this.dimView.setVisibility(8);
                }
            }
        });
        ofFloat.setDuration(200);
        ofFloat.start();
    }

    private void setStroke(boolean z) {
        this.selectedStroke = z;
        if (this.currentEntityView instanceof TextPaintView) {
            Swatch swatch = this.colorPicker.getSwatch();
            if (z && swatch.color == -1) {
                setCurrentSwatch(new Swatch(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, 0.85f, swatch.brushWeight), true);
            } else if (!z && swatch.color == Theme.ACTION_BAR_VIDEO_EDIT_COLOR) {
                setCurrentSwatch(new Swatch(-1, 1.0f, swatch.brushWeight), true);
            }
            ((TextPaintView) this.currentEntityView).setStroke(z);
        }
    }

    private void setTextDimVisibility(final boolean z, EntityView entityView) {
        Animator ofFloat;
        if (z && entityView != null) {
            ViewGroup viewGroup = (ViewGroup) entityView.getParent();
            if (this.textDimView.getParent() != null) {
                ((EntitiesContainerView) this.textDimView.getParent()).removeView(this.textDimView);
            }
            viewGroup.addView(this.textDimView, viewGroup.indexOfChild(entityView));
        }
        entityView.setSelectionVisibility(!z);
        if (z) {
            this.textDimView.setVisibility(0);
            ofFloat = ObjectAnimator.ofFloat(this.textDimView, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f});
        } else {
            ofFloat = ObjectAnimator.ofFloat(this.textDimView, "alpha", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED});
        }
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                if (!z) {
                    PhotoPaintView.this.textDimView.setVisibility(8);
                    if (PhotoPaintView.this.textDimView.getParent() != null) {
                        ((EntitiesContainerView) PhotoPaintView.this.textDimView.getParent()).removeView(PhotoPaintView.this.textDimView);
                    }
                }
            }
        });
        ofFloat.setDuration(200);
        ofFloat.start();
    }

    private void showBrushSettings() {
        showPopup(new Runnable() {
            public void run() {
                boolean z = true;
                View access$2500 = PhotoPaintView.this.buttonForBrush(0, R.drawable.paint_radial_preview, PhotoPaintView.this.currentBrush == 0);
                PhotoPaintView.this.popupLayout.addView(access$2500);
                LayoutParams layoutParams = (LayoutParams) access$2500.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                access$2500.setLayoutParams(layoutParams);
                access$2500 = PhotoPaintView.this.buttonForBrush(1, R.drawable.paint_elliptical_preview, PhotoPaintView.this.currentBrush == 1);
                PhotoPaintView.this.popupLayout.addView(access$2500);
                layoutParams = (LayoutParams) access$2500.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                access$2500.setLayoutParams(layoutParams);
                PhotoPaintView photoPaintView = PhotoPaintView.this;
                if (PhotoPaintView.this.currentBrush != 2) {
                    z = false;
                }
                View access$25002 = photoPaintView.buttonForBrush(2, R.drawable.paint_neon_preview, z);
                PhotoPaintView.this.popupLayout.addView(access$25002);
                layoutParams = (LayoutParams) access$25002.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(52.0f);
                access$25002.setLayoutParams(layoutParams);
            }
        }, this, 85, 0, AndroidUtilities.dp(48.0f));
    }

    private void showMenuForEntity(final EntityView entityView) {
        showPopup(new Runnable() {

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$1 */
            class C45271 implements OnClickListener {
                C45271() {
                }

                public void onClick(View view) {
                    PhotoPaintView.this.removeEntity(entityView);
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$2 */
            class C45282 implements OnClickListener {
                C45282() {
                }

                public void onClick(View view) {
                    PhotoPaintView.this.editSelectedTextEntity();
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            /* renamed from: org.telegram.ui.Components.PhotoPaintView$17$3 */
            class C45293 implements OnClickListener {
                C45293() {
                }

                public void onClick(View view) {
                    PhotoPaintView.this.duplicateSelectedEntity();
                    if (PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                        PhotoPaintView.this.popupWindow.dismiss(true);
                    }
                }
            }

            public void run() {
                View linearLayout = new LinearLayout(PhotoPaintView.this.getContext());
                linearLayout.setOrientation(0);
                View textView = new TextView(PhotoPaintView.this.getContext());
                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                textView.setGravity(16);
                textView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(14.0f), 0);
                textView.setTextSize(1, 18.0f);
                textView.setTag(Integer.valueOf(0));
                textView.setText(LocaleController.getString("PaintDelete", R.string.PaintDelete));
                textView.setOnClickListener(new C45271());
                linearLayout.addView(textView, LayoutHelper.createLinear(-2, 48));
                if (entityView instanceof TextPaintView) {
                    textView = new TextView(PhotoPaintView.this.getContext());
                    textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                    textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    textView.setGravity(16);
                    textView.setPadding(AndroidUtilities.dp(16.0f), 0, AndroidUtilities.dp(16.0f), 0);
                    textView.setTextSize(1, 18.0f);
                    textView.setTag(Integer.valueOf(1));
                    textView.setText(LocaleController.getString("PaintEdit", R.string.PaintEdit));
                    textView.setOnClickListener(new C45282());
                    linearLayout.addView(textView, LayoutHelper.createLinear(-2, 48));
                }
                textView = new TextView(PhotoPaintView.this.getContext());
                textView.setTextColor(Theme.getColor(Theme.key_actionBarDefaultSubmenuItem));
                textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                textView.setGravity(16);
                textView.setPadding(AndroidUtilities.dp(14.0f), 0, AndroidUtilities.dp(16.0f), 0);
                textView.setTextSize(1, 18.0f);
                textView.setTag(Integer.valueOf(2));
                textView.setText(LocaleController.getString("PaintDuplicate", R.string.PaintDuplicate));
                textView.setOnClickListener(new C45293());
                linearLayout.addView(textView, LayoutHelper.createLinear(-2, 48));
                PhotoPaintView.this.popupLayout.addView(linearLayout);
                LayoutParams layoutParams = (LayoutParams) linearLayout.getLayoutParams();
                layoutParams.width = -2;
                layoutParams.height = -2;
                linearLayout.setLayoutParams(layoutParams);
            }
        }, entityView, 17, (int) ((entityView.getPosition().f10184x - ((float) (this.entitiesView.getWidth() / 2))) * this.entitiesView.getScaleX()), ((int) (((entityView.getPosition().f10185y - ((((float) entityView.getHeight()) * entityView.getScale()) / 2.0f)) - ((float) (this.entitiesView.getHeight() / 2))) * this.entitiesView.getScaleY())) - AndroidUtilities.dp(32.0f));
    }

    private void showPopup(Runnable runnable, View view, int i, int i2, int i3) {
        if (this.popupWindow == null || !this.popupWindow.isShowing()) {
            if (this.popupLayout == null) {
                this.popupRect = new Rect();
                this.popupLayout = new ActionBarPopupWindowLayout(getContext());
                this.popupLayout.setAnimationEnabled(false);
                this.popupLayout.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getActionMasked() == 0 && PhotoPaintView.this.popupWindow != null && PhotoPaintView.this.popupWindow.isShowing()) {
                            view.getHitRect(PhotoPaintView.this.popupRect);
                            if (!PhotoPaintView.this.popupRect.contains((int) motionEvent.getX(), (int) motionEvent.getY())) {
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
            runnable.run();
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
            this.popupWindow.showAtLocation(view, i, i2, i3);
            this.popupWindow.startAnimation();
            return;
        }
        this.popupWindow.dismiss();
    }

    private void showTextSettings() {
        showPopup(new Runnable() {
            public void run() {
                View access$2800 = PhotoPaintView.this.buttonForText(true, LocaleController.getString("PaintOutlined", R.string.PaintOutlined), PhotoPaintView.this.selectedStroke);
                PhotoPaintView.this.popupLayout.addView(access$2800);
                LayoutParams layoutParams = (LayoutParams) access$2800.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(48.0f);
                access$2800.setLayoutParams(layoutParams);
                View access$28002 = PhotoPaintView.this.buttonForText(false, LocaleController.getString("PaintRegular", R.string.PaintRegular), !PhotoPaintView.this.selectedStroke);
                PhotoPaintView.this.popupLayout.addView(access$28002);
                layoutParams = (LayoutParams) access$28002.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = AndroidUtilities.dp(48.0f);
                access$28002.setLayoutParams(layoutParams);
            }
        }, this, 85, 0, AndroidUtilities.dp(48.0f));
    }

    private Point startPositionRelativeToEntity(EntityView entityView) {
        if (entityView != null) {
            Point position = entityView.getPosition();
            return new Point(position.f10184x + 200.0f, position.f10185y + 200.0f);
        }
        Point centerPositionForEntity = centerPositionForEntity();
        while (true) {
            Object obj = null;
            for (int i = 0; i < this.entitiesView.getChildCount(); i++) {
                View childAt = this.entitiesView.getChildAt(i);
                if (childAt instanceof EntityView) {
                    position = ((EntityView) childAt).getPosition();
                    if (((float) Math.sqrt(Math.pow((double) (position.f10184x - centerPositionForEntity.f10184x), 2.0d) + Math.pow((double) (position.f10185y - centerPositionForEntity.f10185y), 2.0d))) < 100.0f) {
                        obj = 1;
                    }
                }
            }
            if (obj == null) {
                return centerPositionForEntity;
            }
            centerPositionForEntity = new Point(centerPositionForEntity.f10184x + 200.0f, centerPositionForEntity.f10185y + 200.0f);
        }
    }

    private void updateSettingsButton() {
        int i = R.drawable.photo_paint_brush;
        if (this.currentEntityView != null) {
            if (this.currentEntityView instanceof StickerView) {
                i = R.drawable.photo_flip;
            } else if (this.currentEntityView instanceof TextPaintView) {
                i = R.drawable.photo_outline;
            }
            this.paintButton.setImageResource(R.drawable.photo_paint);
            this.paintButton.setColorFilter(null);
        } else {
            this.paintButton.setColorFilter(new PorterDuffColorFilter(-11420173, Mode.MULTIPLY));
            this.paintButton.setImageResource(R.drawable.photo_paint);
        }
        this.colorPicker.setSettingsButtonImage(i);
    }

    public boolean allowInteraction(EntityView entityView) {
        return !this.editingText;
    }

    public void closeTextEnter(boolean z) {
        if (this.editingText && (this.currentEntityView instanceof TextPaintView)) {
            TextPaintView textPaintView = (TextPaintView) this.currentEntityView;
            this.toolsView.setVisibility(0);
            AndroidUtilities.hideKeyboard(textPaintView.getFocusedView());
            textPaintView.getFocusedView().clearFocus();
            textPaintView.endEditing();
            if (!z) {
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
                this.editedTextRotation = BitmapDescriptorFactory.HUE_RED;
                this.editedTextScale = BitmapDescriptorFactory.HUE_RED;
            }
            setTextDimVisibility(false, textPaintView);
            this.editingText = false;
            this.initialText = null;
            this.curtainView.setVisibility(8);
        }
    }

    public Bitmap getBitmap() {
        Bitmap resultBitmap = this.renderView.getResultBitmap();
        if (resultBitmap != null && this.entitiesView.entitiesCount() > 0) {
            Canvas canvas = new Canvas(resultBitmap);
            for (int i = 0; i < this.entitiesView.getChildCount(); i++) {
                View childAt = this.entitiesView.getChildAt(i);
                canvas.save();
                if (childAt instanceof EntityView) {
                    EntityView entityView = (EntityView) childAt;
                    canvas.translate(entityView.getPosition().f10184x, entityView.getPosition().f10185y);
                    canvas.scale(childAt.getScaleX(), childAt.getScaleY());
                    canvas.rotate(childAt.getRotation());
                    canvas.translate((float) ((-entityView.getWidth()) / 2), (float) ((-entityView.getHeight()) / 2));
                    if (childAt instanceof TextPaintView) {
                        Bitmap createBitmap = Bitmaps.createBitmap(childAt.getWidth(), childAt.getHeight(), Config.ARGB_8888);
                        Canvas canvas2 = new Canvas(createBitmap);
                        childAt.draw(canvas2);
                        canvas.drawBitmap(createBitmap, null, new Rect(0, 0, createBitmap.getWidth(), createBitmap.getHeight()), null);
                        try {
                            canvas2.setBitmap(null);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        createBitmap.recycle();
                    } else {
                        childAt.draw(canvas);
                    }
                }
                canvas.restore();
            }
        }
        return resultBitmap;
    }

    public TextView getCancelTextView() {
        return this.cancelTextView;
    }

    public ColorPicker getColorPicker() {
        return this.colorPicker;
    }

    public TextView getDoneTextView() {
        return this.doneTextView;
    }

    public ArrayList<InputDocument> getMasks() {
        ArrayList<InputDocument> arrayList = null;
        int childCount = this.entitiesView.getChildCount();
        int i = 0;
        while (i < childCount) {
            ArrayList<InputDocument> arrayList2;
            View childAt = this.entitiesView.getChildAt(i);
            if (childAt instanceof StickerView) {
                Document sticker = ((StickerView) childAt).getSticker();
                arrayList2 = arrayList == null ? new ArrayList() : arrayList;
                TLRPC$TL_inputDocument tLRPC$TL_inputDocument = new TLRPC$TL_inputDocument();
                tLRPC$TL_inputDocument.id = sticker.id;
                tLRPC$TL_inputDocument.access_hash = sticker.access_hash;
                arrayList2.add(tLRPC$TL_inputDocument);
            } else {
                arrayList2 = arrayList;
            }
            i++;
            arrayList = arrayList2;
        }
        return arrayList;
    }

    public FrameLayout getToolsView() {
        return this.toolsView;
    }

    public void init() {
        this.renderView.setVisibility(0);
        detectFaces();
    }

    public void maybeShowDismissalAlert(PhotoViewer photoViewer, Activity activity, final Runnable runnable) {
        if (this.editingText) {
            closeTextEnter(false);
        } else if (this.pickingSticker) {
            closeStickersView();
        } else if (!hasChanges()) {
            runnable.run();
        } else if (activity != null) {
            Builder builder = new Builder(activity);
            builder.setMessage(LocaleController.getString("DiscardChanges", R.string.DiscardChanges));
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    runnable.run();
                }
            });
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            photoViewer.showAlertDialog(builder);
        }
    }

    public boolean onEntityLongClicked(EntityView entityView) {
        showMenuForEntity(entityView);
        return true;
    }

    public boolean onEntitySelected(EntityView entityView) {
        return selectEntity(entityView);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float height;
        float width;
        int i5 = i3 - i;
        int i6 = i4 - i2;
        int i7 = VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0;
        int currentActionBarHeight = ActionBar.getCurrentActionBarHeight();
        int currentActionBarHeight2 = ActionBar.getCurrentActionBarHeight() + i7;
        int dp = (AndroidUtilities.displaySize.y - currentActionBarHeight) - AndroidUtilities.dp(48.0f);
        if (this.bitmapToEdit != null) {
            height = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
            width = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        } else {
            height = (float) i5;
            width = (float) ((i6 - currentActionBarHeight) - AndroidUtilities.dp(48.0f));
        }
        float f = (float) i5;
        height = ((float) Math.floor((double) ((f * width) / height))) > ((float) dp) ? (float) Math.floor((double) ((height * ((float) dp)) / width)) : f;
        currentActionBarHeight = (int) Math.ceil((double) ((i5 - this.renderView.getMeasuredWidth()) / 2));
        int dp2 = ((((((i6 - currentActionBarHeight2) - AndroidUtilities.dp(48.0f)) - this.renderView.getMeasuredHeight()) / 2) + currentActionBarHeight2) - ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.dp(8.0f);
        this.renderView.layout(currentActionBarHeight, dp2, this.renderView.getMeasuredWidth() + currentActionBarHeight, this.renderView.getMeasuredHeight() + dp2);
        height /= this.paintingSize.width;
        this.entitiesView.setScaleX(height);
        this.entitiesView.setScaleY(height);
        this.entitiesView.layout(currentActionBarHeight, dp2, this.entitiesView.getMeasuredWidth() + currentActionBarHeight, this.entitiesView.getMeasuredHeight() + dp2);
        this.dimView.layout(0, i7, this.dimView.getMeasuredWidth(), this.dimView.getMeasuredHeight() + i7);
        this.selectionContainerView.layout(0, i7, this.selectionContainerView.getMeasuredWidth(), this.selectionContainerView.getMeasuredHeight() + i7);
        this.colorPicker.layout(0, currentActionBarHeight2, this.colorPicker.getMeasuredWidth(), this.colorPicker.getMeasuredHeight() + currentActionBarHeight2);
        this.toolsView.layout(0, i6 - this.toolsView.getMeasuredHeight(), this.toolsView.getMeasuredWidth(), i6);
        this.curtainView.layout(0, 0, i5, dp);
        if (this.stickersView != null) {
            this.stickersView.layout(0, i7, this.stickersView.getMeasuredWidth(), this.stickersView.getMeasuredHeight() + i7);
        }
        if (this.currentEntityView != null) {
            this.currentEntityView.updateSelectionView();
            this.currentEntityView.setOffset(this.entitiesView.getLeft() - this.selectionContainerView.getLeft(), this.entitiesView.getTop() - this.selectionContainerView.getTop());
        }
    }

    protected void onMeasure(int i, int i2) {
        float height;
        float width;
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        setMeasuredDimension(size, size2);
        int currentActionBarHeight = (AndroidUtilities.displaySize.y - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(48.0f);
        if (this.bitmapToEdit != null) {
            height = isSidewardOrientation() ? (float) this.bitmapToEdit.getHeight() : (float) this.bitmapToEdit.getWidth();
            width = isSidewardOrientation() ? (float) this.bitmapToEdit.getWidth() : (float) this.bitmapToEdit.getHeight();
        } else {
            height = (float) size;
            width = (float) ((size2 - ActionBar.getCurrentActionBarHeight()) - AndroidUtilities.dp(48.0f));
        }
        float f = (float) size;
        float floor = (float) Math.floor((double) ((f * width) / height));
        if (floor > ((float) currentActionBarHeight)) {
            floor = (float) currentActionBarHeight;
            width = (float) Math.floor((double) ((height * floor) / width));
            height = floor;
        } else {
            height = floor;
            width = f;
        }
        this.renderView.measure(MeasureSpec.makeMeasureSpec((int) width, 1073741824), MeasureSpec.makeMeasureSpec((int) height, 1073741824));
        this.entitiesView.measure(MeasureSpec.makeMeasureSpec((int) this.paintingSize.width, 1073741824), MeasureSpec.makeMeasureSpec((int) this.paintingSize.height, 1073741824));
        this.dimView.measure(i, MeasureSpec.makeMeasureSpec(currentActionBarHeight, Integer.MIN_VALUE));
        this.selectionContainerView.measure(i, MeasureSpec.makeMeasureSpec(currentActionBarHeight, 1073741824));
        this.colorPicker.measure(MeasureSpec.makeMeasureSpec(size, 1073741824), MeasureSpec.makeMeasureSpec(currentActionBarHeight, 1073741824));
        this.toolsView.measure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
        if (this.stickersView != null) {
            this.stickersView.measure(i, MeasureSpec.makeMeasureSpec(AndroidUtilities.displaySize.y, 1073741824));
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.currentEntityView != null) {
            if (this.editingText) {
                closeTextEnter(true);
            } else {
                selectEntity(null);
            }
        }
        return true;
    }

    public void shutdown() {
        this.renderView.shutdown();
        this.entitiesView.setVisibility(8);
        this.selectionContainerView.setVisibility(8);
        this.queue.postRunnable(new Runnable() {
            public void run() {
                Looper myLooper = Looper.myLooper();
                if (myLooper != null) {
                    myLooper.quit();
                }
            }
        });
    }
}
