package org.telegram.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.ImageReceiver;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.ui.ActionBar.BottomSheet.Builder;
import org.telegram.ui.Cells.ContextLinkCell;
import org.telegram.ui.Cells.StickerCell;
import org.telegram.ui.Cells.StickerEmojiCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;

public class StickerPreviewViewer {
    @SuppressLint({"StaticFieldLeak"})
    private static volatile StickerPreviewViewer Instance = null;
    private static TextPaint textPaint;
    private ColorDrawable backgroundDrawable = new ColorDrawable(1895825408);
    private ImageReceiver centerImage = new ImageReceiver();
    private FrameLayoutDrawer containerView;
    private InputStickerSet currentSet;
    private Document currentSticker;
    private View currentStickerPreviewCell;
    private StickerPreviewViewerDelegate delegate;
    private boolean isVisible = false;
    private int keyboardHeight = AndroidUtilities.dp(200.0f);
    private long lastUpdateTime;
    private Runnable openStickerPreviewRunnable;
    private Activity parentActivity;
    private float showProgress;
    private Runnable showSheetRunnable = new C52121();
    private int startX;
    private int startY;
    private StaticLayout stickerEmojiLayout;
    private Dialog visibleDialog;
    private LayoutParams windowLayoutParams;
    private FrameLayout windowView;

    public interface StickerPreviewViewerDelegate {
        void openSet(InputStickerSet inputStickerSet);

        void sentSticker(Document document);
    }

    /* renamed from: org.telegram.ui.StickerPreviewViewer$1 */
    class C52121 implements Runnable {

        /* renamed from: org.telegram.ui.StickerPreviewViewer$1$2 */
        class C52112 implements OnDismissListener {
            C52112() {
            }

            public void onDismiss(DialogInterface dialogInterface) {
                StickerPreviewViewer.this.visibleDialog = null;
                StickerPreviewViewer.this.close();
            }
        }

        C52121() {
        }

        public void run() {
            if (StickerPreviewViewer.this.parentActivity != null && StickerPreviewViewer.this.currentSet != null) {
                final boolean isStickerInFavorites = StickersQuery.isStickerInFavorites(StickerPreviewViewer.this.currentSticker);
                Builder builder = new Builder(StickerPreviewViewer.this.parentActivity);
                ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                if (StickerPreviewViewer.this.delegate != null) {
                    arrayList.add(LocaleController.getString("SendStickerPreview", R.string.SendStickerPreview));
                    arrayList3.add(Integer.valueOf(R.drawable.stickers_send));
                    arrayList2.add(Integer.valueOf(0));
                    arrayList.add(LocaleController.formatString("ViewPackPreview", R.string.ViewPackPreview, new Object[0]));
                    arrayList3.add(Integer.valueOf(R.drawable.stickers_pack));
                    arrayList2.add(Integer.valueOf(1));
                }
                if (!MessageObject.isMaskDocument(StickerPreviewViewer.this.currentSticker) && (isStickerInFavorites || StickersQuery.canAddStickerToFavorites())) {
                    arrayList.add(isStickerInFavorites ? LocaleController.getString("DeleteFromFavorites", R.string.DeleteFromFavorites) : LocaleController.getString("AddToFavorites", R.string.AddToFavorites));
                    arrayList3.add(Integer.valueOf(isStickerInFavorites ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite));
                    arrayList2.add(Integer.valueOf(2));
                }
                if (!arrayList.isEmpty()) {
                    int[] iArr = new int[arrayList3.size()];
                    for (int i = 0; i < arrayList3.size(); i++) {
                        iArr[i] = ((Integer) arrayList3.get(i)).intValue();
                    }
                    builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), iArr, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (StickerPreviewViewer.this.parentActivity != null) {
                                if (((Integer) arrayList2.get(i)).intValue() == 0) {
                                    if (StickerPreviewViewer.this.delegate != null) {
                                        StickerPreviewViewer.this.delegate.sentSticker(StickerPreviewViewer.this.currentSticker);
                                    }
                                } else if (((Integer) arrayList2.get(i)).intValue() == 1) {
                                    if (StickerPreviewViewer.this.delegate != null) {
                                        StickerPreviewViewer.this.delegate.openSet(StickerPreviewViewer.this.currentSet);
                                    }
                                } else if (((Integer) arrayList2.get(i)).intValue() == 2) {
                                    StickersQuery.addRecentSticker(2, StickerPreviewViewer.this.currentSticker, (int) (System.currentTimeMillis() / 1000), isStickerInFavorites);
                                }
                            }
                        }
                    });
                    StickerPreviewViewer.this.visibleDialog = builder.create();
                    StickerPreviewViewer.this.visibleDialog.setOnDismissListener(new C52112());
                    StickerPreviewViewer.this.visibleDialog.show();
                    StickerPreviewViewer.this.containerView.performHapticFeedback(0);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.StickerPreviewViewer$4 */
    class C52154 implements OnTouchListener {
        C52154() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 6 || motionEvent.getAction() == 3) {
                StickerPreviewViewer.this.close();
            }
            return true;
        }
    }

    /* renamed from: org.telegram.ui.StickerPreviewViewer$5 */
    class C52165 implements Runnable {
        C52165() {
        }

        public void run() {
            StickerPreviewViewer.this.centerImage.setImageBitmap((Bitmap) null);
        }
    }

    private class FrameLayoutDrawer extends FrameLayout {
        public FrameLayoutDrawer(Context context) {
            super(context);
            setWillNotDraw(false);
        }

        protected void onDraw(Canvas canvas) {
            StickerPreviewViewer.this.onDraw(canvas);
        }
    }

    public static StickerPreviewViewer getInstance() {
        StickerPreviewViewer stickerPreviewViewer = Instance;
        if (stickerPreviewViewer == null) {
            synchronized (PhotoViewer.class) {
                stickerPreviewViewer = Instance;
                if (stickerPreviewViewer == null) {
                    stickerPreviewViewer = new StickerPreviewViewer();
                    Instance = stickerPreviewViewer;
                }
            }
        }
        return stickerPreviewViewer;
    }

    @SuppressLint({"DrawAllocation"})
    private void onDraw(Canvas canvas) {
        int i = 0;
        if (this.containerView != null && this.backgroundDrawable != null) {
            this.backgroundDrawable.setAlpha((int) (180.0f * this.showProgress));
            this.backgroundDrawable.setBounds(0, 0, this.containerView.getWidth(), this.containerView.getHeight());
            this.backgroundDrawable.draw(canvas);
            canvas.save();
            int min = (int) (((float) Math.min(this.containerView.getWidth(), this.containerView.getHeight())) / 1.8f);
            float width = (float) (this.containerView.getWidth() / 2);
            int i2 = (min / 2) + AndroidUtilities.statusBarHeight;
            if (this.stickerEmojiLayout != null) {
                i = AndroidUtilities.dp(40.0f);
            }
            canvas.translate(width, (float) Math.max(i + i2, (this.containerView.getHeight() - this.keyboardHeight) / 2));
            if (this.centerImage.getBitmap() != null) {
                i = (int) (((this.showProgress * 0.8f) / 0.8f) * ((float) min));
                this.centerImage.setAlpha(this.showProgress);
                this.centerImage.setImageCoords((-i) / 2, (-i) / 2, i, i);
                this.centerImage.draw(canvas);
            }
            if (this.stickerEmojiLayout != null) {
                canvas.translate((float) (-AndroidUtilities.dp(50.0f)), (float) (((-this.centerImage.getImageHeight()) / 2) - AndroidUtilities.dp(30.0f)));
                this.stickerEmojiLayout.draw(canvas);
            }
            canvas.restore();
            long currentTimeMillis;
            long j;
            if (this.isVisible) {
                if (this.showProgress != 1.0f) {
                    currentTimeMillis = System.currentTimeMillis();
                    j = currentTimeMillis - this.lastUpdateTime;
                    this.lastUpdateTime = currentTimeMillis;
                    this.showProgress += ((float) j) / 120.0f;
                    this.containerView.invalidate();
                    if (this.showProgress > 1.0f) {
                        this.showProgress = 1.0f;
                    }
                }
            } else if (this.showProgress != BitmapDescriptorFactory.HUE_RED) {
                currentTimeMillis = System.currentTimeMillis();
                j = currentTimeMillis - this.lastUpdateTime;
                this.lastUpdateTime = currentTimeMillis;
                this.showProgress -= ((float) j) / 120.0f;
                this.containerView.invalidate();
                if (this.showProgress < BitmapDescriptorFactory.HUE_RED) {
                    this.showProgress = BitmapDescriptorFactory.HUE_RED;
                }
                if (this.showProgress == BitmapDescriptorFactory.HUE_RED) {
                    AndroidUtilities.unlockOrientation(this.parentActivity);
                    AndroidUtilities.runOnUIThread(new C52165());
                    try {
                        if (this.windowView.getParent() != null) {
                            ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            }
        }
    }

    public void close() {
        if (this.parentActivity != null && this.visibleDialog == null) {
            AndroidUtilities.cancelRunOnUIThread(this.showSheetRunnable);
            this.showProgress = 1.0f;
            this.lastUpdateTime = System.currentTimeMillis();
            this.containerView.invalidate();
            try {
                if (this.visibleDialog != null) {
                    this.visibleDialog.dismiss();
                    this.visibleDialog = null;
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.currentSticker = null;
            this.currentSet = null;
            this.delegate = null;
            this.isVisible = false;
        }
    }

    public void destroy() {
        this.isVisible = false;
        this.delegate = null;
        this.currentSticker = null;
        this.currentSet = null;
        try {
            if (this.visibleDialog != null) {
                this.visibleDialog.dismiss();
                this.visibleDialog = null;
            }
        } catch (Throwable e) {
            FileLog.e(e);
        }
        if (this.parentActivity != null && this.windowView != null) {
            try {
                if (this.windowView.getParent() != null) {
                    ((WindowManager) this.parentActivity.getSystemService("window")).removeViewImmediate(this.windowView);
                }
                this.windowView = null;
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            Instance = null;
        }
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent, final View view, final int i, StickerPreviewViewerDelegate stickerPreviewViewerDelegate) {
        this.delegate = stickerPreviewViewerDelegate;
        if (motionEvent.getAction() != 0) {
            return false;
        }
        int childCount;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (view instanceof AbsListView) {
            childCount = ((AbsListView) view).getChildCount();
        } else if (view instanceof RecyclerListView) {
            childCount = ((RecyclerListView) view).getChildCount();
        } else {
            boolean z = false;
        }
        int i2 = 0;
        while (i2 < childCount) {
            View view2 = null;
            if (view instanceof AbsListView) {
                view2 = ((AbsListView) view).getChildAt(i2);
            } else if (view instanceof RecyclerListView) {
                view2 = ((RecyclerListView) view).getChildAt(i2);
            }
            if (view2 == null) {
                return false;
            }
            int top = view2.getTop();
            int bottom = view2.getBottom();
            int left = view2.getLeft();
            int right = view2.getRight();
            if (top > y || bottom < y || left > x || right < x) {
                i2++;
            } else {
                boolean showingBitmap;
                if (view2 instanceof StickerEmojiCell) {
                    showingBitmap = ((StickerEmojiCell) view2).showingBitmap();
                } else if (view2 instanceof StickerCell) {
                    showingBitmap = ((StickerCell) view2).showingBitmap();
                } else if (view2 instanceof ContextLinkCell) {
                    ContextLinkCell contextLinkCell = (ContextLinkCell) view2;
                    showingBitmap = contextLinkCell.isSticker() && contextLinkCell.showingBitmap();
                } else {
                    showingBitmap = false;
                }
                if (!showingBitmap) {
                    return false;
                }
                this.startX = x;
                this.startY = y;
                this.currentStickerPreviewCell = view2;
                this.openStickerPreviewRunnable = new Runnable() {
                    public void run() {
                        if (StickerPreviewViewer.this.openStickerPreviewRunnable != null) {
                            if (view instanceof AbsListView) {
                                ((AbsListView) view).setOnItemClickListener(null);
                                ((AbsListView) view).requestDisallowInterceptTouchEvent(true);
                            } else if (view instanceof RecyclerListView) {
                                ((RecyclerListView) view).setOnItemClickListener((OnItemClickListener) null);
                                ((RecyclerListView) view).requestDisallowInterceptTouchEvent(true);
                            }
                            StickerPreviewViewer.this.openStickerPreviewRunnable = null;
                            StickerPreviewViewer.this.setParentActivity((Activity) view.getContext());
                            StickerPreviewViewer.this.setKeyboardHeight(i);
                            if (StickerPreviewViewer.this.currentStickerPreviewCell instanceof StickerEmojiCell) {
                                StickerPreviewViewer.this.open(((StickerEmojiCell) StickerPreviewViewer.this.currentStickerPreviewCell).getSticker(), ((StickerEmojiCell) StickerPreviewViewer.this.currentStickerPreviewCell).isRecent());
                                ((StickerEmojiCell) StickerPreviewViewer.this.currentStickerPreviewCell).setScaled(true);
                            } else if (StickerPreviewViewer.this.currentStickerPreviewCell instanceof StickerCell) {
                                StickerPreviewViewer.this.open(((StickerCell) StickerPreviewViewer.this.currentStickerPreviewCell).getSticker(), false);
                                ((StickerCell) StickerPreviewViewer.this.currentStickerPreviewCell).setScaled(true);
                            } else if (StickerPreviewViewer.this.currentStickerPreviewCell instanceof ContextLinkCell) {
                                StickerPreviewViewer.this.open(((ContextLinkCell) StickerPreviewViewer.this.currentStickerPreviewCell).getDocument(), false);
                                ((ContextLinkCell) StickerPreviewViewer.this.currentStickerPreviewCell).setScaled(true);
                            }
                        }
                    }
                };
                AndroidUtilities.runOnUIThread(this.openStickerPreviewRunnable, 200);
                return true;
            }
        }
        return false;
    }

    public boolean onTouch(MotionEvent motionEvent, final View view, int i, final Object obj, StickerPreviewViewerDelegate stickerPreviewViewerDelegate) {
        this.delegate = stickerPreviewViewerDelegate;
        if (this.openStickerPreviewRunnable != null || isVisible()) {
            if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3 || motionEvent.getAction() == 6) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        if (view instanceof AbsListView) {
                            ((AbsListView) view).setOnItemClickListener((AdapterView.OnItemClickListener) obj);
                        } else if (view instanceof RecyclerListView) {
                            ((RecyclerListView) view).setOnItemClickListener((OnItemClickListener) obj);
                        }
                    }
                }, 150);
                if (this.openStickerPreviewRunnable != null) {
                    AndroidUtilities.cancelRunOnUIThread(this.openStickerPreviewRunnable);
                    this.openStickerPreviewRunnable = null;
                } else if (isVisible()) {
                    close();
                    if (this.currentStickerPreviewCell != null) {
                        if (this.currentStickerPreviewCell instanceof StickerEmojiCell) {
                            ((StickerEmojiCell) this.currentStickerPreviewCell).setScaled(false);
                        } else if (this.currentStickerPreviewCell instanceof StickerCell) {
                            ((StickerCell) this.currentStickerPreviewCell).setScaled(false);
                        } else if (this.currentStickerPreviewCell instanceof ContextLinkCell) {
                            ((ContextLinkCell) this.currentStickerPreviewCell).setScaled(false);
                        }
                        this.currentStickerPreviewCell = null;
                    }
                }
            } else if (motionEvent.getAction() != 0) {
                if (isVisible()) {
                    if (motionEvent.getAction() == 2) {
                        int x = (int) motionEvent.getX();
                        int y = (int) motionEvent.getY();
                        int childCount = view instanceof AbsListView ? ((AbsListView) view).getChildCount() : view instanceof RecyclerListView ? ((RecyclerListView) view).getChildCount() : 0;
                        int i2 = 0;
                        while (i2 < childCount) {
                            View view2 = null;
                            if (view instanceof AbsListView) {
                                view2 = ((AbsListView) view).getChildAt(i2);
                            } else if (view instanceof RecyclerListView) {
                                view2 = ((RecyclerListView) view).getChildAt(i2);
                            }
                            if (view2 == null) {
                                return false;
                            }
                            int top = view2.getTop();
                            int bottom = view2.getBottom();
                            int left = view2.getLeft();
                            int right = view2.getRight();
                            if (top > y || bottom < y || left > x || right < x) {
                                i2++;
                            } else {
                                boolean z = false;
                                if (view2 instanceof StickerEmojiCell) {
                                    z = true;
                                } else if (view2 instanceof StickerCell) {
                                    z = true;
                                } else if (view2 instanceof ContextLinkCell) {
                                    z = ((ContextLinkCell) view2).isSticker();
                                }
                                if (z && view2 != this.currentStickerPreviewCell) {
                                    if (this.currentStickerPreviewCell instanceof StickerEmojiCell) {
                                        ((StickerEmojiCell) this.currentStickerPreviewCell).setScaled(false);
                                    } else if (this.currentStickerPreviewCell instanceof StickerCell) {
                                        ((StickerCell) this.currentStickerPreviewCell).setScaled(false);
                                    } else if (this.currentStickerPreviewCell instanceof ContextLinkCell) {
                                        ((ContextLinkCell) this.currentStickerPreviewCell).setScaled(false);
                                    }
                                    this.currentStickerPreviewCell = view2;
                                    setKeyboardHeight(i);
                                    if (this.currentStickerPreviewCell instanceof StickerEmojiCell) {
                                        open(((StickerEmojiCell) this.currentStickerPreviewCell).getSticker(), ((StickerEmojiCell) this.currentStickerPreviewCell).isRecent());
                                        ((StickerEmojiCell) this.currentStickerPreviewCell).setScaled(true);
                                    } else if (this.currentStickerPreviewCell instanceof StickerCell) {
                                        open(((StickerCell) this.currentStickerPreviewCell).getSticker(), false);
                                        ((StickerCell) this.currentStickerPreviewCell).setScaled(true);
                                    } else if (this.currentStickerPreviewCell instanceof ContextLinkCell) {
                                        open(((ContextLinkCell) this.currentStickerPreviewCell).getDocument(), false);
                                        ((ContextLinkCell) this.currentStickerPreviewCell).setScaled(true);
                                    }
                                    return true;
                                }
                            }
                        }
                    }
                    return true;
                } else if (this.openStickerPreviewRunnable != null) {
                    if (motionEvent.getAction() != 2) {
                        AndroidUtilities.cancelRunOnUIThread(this.openStickerPreviewRunnable);
                        this.openStickerPreviewRunnable = null;
                    } else if (Math.hypot((double) (((float) this.startX) - motionEvent.getX()), (double) (((float) this.startY) - motionEvent.getY())) > ((double) AndroidUtilities.dp(10.0f))) {
                        AndroidUtilities.cancelRunOnUIThread(this.openStickerPreviewRunnable);
                        this.openStickerPreviewRunnable = null;
                    }
                }
            }
        }
        return false;
    }

    public void open(Document document, boolean z) {
        if (this.parentActivity != null && document != null) {
            int i;
            DocumentAttribute documentAttribute;
            InputStickerSet inputStickerSet;
            if (textPaint == null) {
                textPaint = new TextPaint(1);
                textPaint.setTextSize((float) AndroidUtilities.dp(24.0f));
            }
            for (i = 0; i < document.attributes.size(); i++) {
                documentAttribute = (DocumentAttribute) document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC$TL_documentAttributeSticker) && documentAttribute.stickerset != null) {
                    inputStickerSet = documentAttribute.stickerset;
                    break;
                }
            }
            inputStickerSet = null;
            if (inputStickerSet != null) {
                try {
                    if (this.visibleDialog != null) {
                        this.visibleDialog.setOnDismissListener(null);
                        this.visibleDialog.dismiss();
                        this.visibleDialog = null;
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                AndroidUtilities.cancelRunOnUIThread(this.showSheetRunnable);
                AndroidUtilities.runOnUIThread(this.showSheetRunnable, 1300);
            }
            this.currentSet = inputStickerSet;
            this.centerImage.setImage(document, null, document.thumb.location, null, "webp", 1);
            this.stickerEmojiLayout = null;
            for (i = 0; i < document.attributes.size(); i++) {
                documentAttribute = (DocumentAttribute) document.attributes.get(i);
                if ((documentAttribute instanceof TLRPC$TL_documentAttributeSticker) && !TextUtils.isEmpty(documentAttribute.alt)) {
                    this.stickerEmojiLayout = new StaticLayout(Emoji.replaceEmoji(documentAttribute.alt, textPaint.getFontMetricsInt(), AndroidUtilities.dp(24.0f), false), textPaint, AndroidUtilities.dp(100.0f), Alignment.ALIGN_CENTER, 1.0f, BitmapDescriptorFactory.HUE_RED, false);
                    break;
                }
            }
            this.currentSticker = document;
            this.containerView.invalidate();
            if (!this.isVisible) {
                AndroidUtilities.lockOrientation(this.parentActivity);
                try {
                    if (this.windowView.getParent() != null) {
                        ((WindowManager) this.parentActivity.getSystemService("window")).removeView(this.windowView);
                    }
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
                ((WindowManager) this.parentActivity.getSystemService("window")).addView(this.windowView, this.windowLayoutParams);
                this.isVisible = true;
                this.showProgress = BitmapDescriptorFactory.HUE_RED;
                this.lastUpdateTime = System.currentTimeMillis();
            }
        }
    }

    public void reset() {
        if (this.openStickerPreviewRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.openStickerPreviewRunnable);
            this.openStickerPreviewRunnable = null;
        }
        if (this.currentStickerPreviewCell != null) {
            if (this.currentStickerPreviewCell instanceof StickerEmojiCell) {
                ((StickerEmojiCell) this.currentStickerPreviewCell).setScaled(false);
            } else if (this.currentStickerPreviewCell instanceof StickerCell) {
                ((StickerCell) this.currentStickerPreviewCell).setScaled(false);
            } else if (this.currentStickerPreviewCell instanceof ContextLinkCell) {
                ((ContextLinkCell) this.currentStickerPreviewCell).setScaled(false);
            }
            this.currentStickerPreviewCell = null;
        }
    }

    public void setDelegate(StickerPreviewViewerDelegate stickerPreviewViewerDelegate) {
        this.delegate = stickerPreviewViewerDelegate;
    }

    public void setKeyboardHeight(int i) {
        this.keyboardHeight = i;
    }

    public void setParentActivity(Activity activity) {
        if (this.parentActivity != activity) {
            this.parentActivity = activity;
            this.windowView = new FrameLayout(activity);
            this.windowView.setFocusable(true);
            this.windowView.setFocusableInTouchMode(true);
            if (VERSION.SDK_INT >= 23) {
                this.windowView.setFitsSystemWindows(true);
            }
            this.containerView = new FrameLayoutDrawer(activity);
            this.containerView.setFocusable(false);
            this.windowView.addView(this.containerView, LayoutHelper.createFrame(-1, -1, 51));
            this.containerView.setOnTouchListener(new C52154());
            this.windowLayoutParams = new LayoutParams();
            this.windowLayoutParams.height = -1;
            this.windowLayoutParams.format = -3;
            this.windowLayoutParams.width = -1;
            this.windowLayoutParams.gravity = 48;
            this.windowLayoutParams.type = 99;
            if (VERSION.SDK_INT >= 21) {
                this.windowLayoutParams.flags = -2147483640;
            } else {
                this.windowLayoutParams.flags = 8;
            }
            this.centerImage.setAspectFit(true);
            this.centerImage.setInvalidateAll(true);
            this.centerImage.setParentView(this.containerView);
        }
    }
}
