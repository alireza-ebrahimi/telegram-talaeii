package org.telegram.ui.Components;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils.TruncateAt;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.Emoji;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.GridLayoutManager;
import org.telegram.messenger.support.widget.GridLayoutManager.SpanSizeLookup;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ItemDecoration;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.State;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$InputStickerSet;
import org.telegram.tgnet.TLRPC$Photo;
import org.telegram.tgnet.TLRPC$StickerSetCovered;
import org.telegram.tgnet.TLRPC$TL_documentAttributeSticker;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPhoto;
import org.telegram.tgnet.TLRPC$TL_inputStickerSetID;
import org.telegram.tgnet.TLRPC$TL_inputStickeredMediaPhoto;
import org.telegram.tgnet.TLRPC$TL_messages_getAttachedStickers;
import org.telegram.tgnet.TLRPC$TL_messages_getStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_installStickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSetInstallResultArchive;
import org.telegram.tgnet.TLRPC$Vector;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.FeaturedStickerSetInfoCell;
import org.telegram.ui.Cells.StickerEmojiCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.StickerPreviewViewer;

public class StickersAlert extends BottomSheet implements NotificationCenterDelegate {
    private GridAdapter adapter;
    private StickersAlertDelegate delegate;
    private FrameLayout emptyView;
    private RecyclerListView gridView;
    private boolean ignoreLayout;
    private TLRPC$InputStickerSet inputStickerSet;
    private StickersAlertInstallDelegate installDelegate;
    private int itemSize;
    private GridLayoutManager layoutManager;
    private Activity parentActivity;
    private BaseFragment parentFragment;
    private PickerBottomLayout pickerBottomLayout;
    private ImageView previewFavButton;
    private TextView previewSendButton;
    private View previewSendButtonShadow;
    private int reqId;
    private int scrollOffsetY;
    private TLRPC$Document selectedSticker;
    private View[] shadow;
    private AnimatorSet[] shadowAnimation;
    private Drawable shadowDrawable;
    private boolean showEmoji;
    private TextView stickerEmojiTextView;
    private BackupImageView stickerImageView;
    private FrameLayout stickerPreviewLayout;
    private TLRPC$TL_messages_stickerSet stickerSet;
    private ArrayList<TLRPC$StickerSetCovered> stickerSetCovereds;
    private OnItemClickListener stickersOnItemClickListener;
    private TextView titleTextView;
    private Pattern urlPattern;

    public interface StickersAlertInstallDelegate {
        void onStickerSetInstalled();

        void onStickerSetUninstalled();
    }

    public interface StickersAlertDelegate {
        void onStickerSelected(TLRPC$Document tLRPC$Document);
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$2 */
    class C27722 implements RequestDelegate {
        C27722() {
        }

        public void run(final TLObject response, final TLRPC$TL_error error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    StickersAlert.this.reqId = 0;
                    if (error == null) {
                        StickersAlert.this.stickerSet = (TLRPC$TL_messages_stickerSet) response;
                        StickersAlert.this.showEmoji = !StickersAlert.this.stickerSet.set.masks;
                        StickersAlert.this.updateSendButton();
                        StickersAlert.this.updateFields();
                        StickersAlert.this.adapter.notifyDataSetChanged();
                        return;
                    }
                    Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("AddStickersNotFound", R.string.AddStickersNotFound), 0).show();
                    StickersAlert.this.dismiss();
                }
            });
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$5 */
    class C27755 extends SpanSizeLookup {
        C27755() {
        }

        public int getSpanSize(int position) {
            if ((StickersAlert.this.stickerSetCovereds == null || !(StickersAlert.this.adapter.cache.get(Integer.valueOf(position)) instanceof Integer)) && position != StickersAlert.this.adapter.totalItems) {
                return 1;
            }
            return StickersAlert.this.adapter.stickersPerRow;
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$6 */
    class C27766 extends ItemDecoration {
        C27766() {
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            outRect.top = 0;
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$7 */
    class C27777 implements OnTouchListener {
        C27777() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            return StickerPreviewViewer.getInstance().onTouch(event, StickersAlert.this.gridView, 0, StickersAlert.this.stickersOnItemClickListener, null);
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$8 */
    class C27788 extends OnScrollListener {
        C27788() {
        }

        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            StickersAlert.this.updateLayout();
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$9 */
    class C27799 implements OnItemClickListener {
        C27799() {
        }

        public void onItemClick(View view, int position) {
            if (StickersAlert.this.stickerSetCovereds != null) {
                TLRPC$StickerSetCovered pack = (TLRPC$StickerSetCovered) StickersAlert.this.adapter.positionsToSets.get(Integer.valueOf(position));
                if (pack != null) {
                    StickersAlert.this.dismiss();
                    TLRPC$TL_inputStickerSetID inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                    inputStickerSetID.access_hash = pack.set.access_hash;
                    inputStickerSetID.id = pack.set.id;
                    new StickersAlert(StickersAlert.this.parentActivity, StickersAlert.this.parentFragment, inputStickerSetID, null, null).show();
                }
            } else if (StickersAlert.this.stickerSet != null && position >= 0 && position < StickersAlert.this.stickerSet.documents.size()) {
                boolean fav;
                ImageView access$3100;
                int i;
                LayoutParams layoutParams;
                AnimatorSet animatorSet;
                StickersAlert.this.selectedSticker = (TLRPC$Document) StickersAlert.this.stickerSet.documents.get(position);
                boolean set = false;
                for (int a = 0; a < StickersAlert.this.selectedSticker.attributes.size(); a++) {
                    DocumentAttribute attribute = (DocumentAttribute) StickersAlert.this.selectedSticker.attributes.get(a);
                    if (attribute instanceof TLRPC$TL_documentAttributeSticker) {
                        if (attribute.alt != null && attribute.alt.length() > 0) {
                            StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(attribute.alt, StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                            set = true;
                        }
                        if (!set) {
                            StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(StickersQuery.getEmojiForSticker(StickersAlert.this.selectedSticker.id), StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                        }
                        fav = StickersQuery.isStickerInFavorites(StickersAlert.this.selectedSticker);
                        StickersAlert.this.previewFavButton.setImageResource(fav ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite);
                        StickersAlert.this.previewFavButton.setTag(fav ? Integer.valueOf(1) : null);
                        if (StickersAlert.this.previewFavButton.getVisibility() != 8) {
                            access$3100 = StickersAlert.this.previewFavButton;
                            i = (fav || StickersQuery.canAddStickerToFavorites()) ? 0 : 4;
                            access$3100.setVisibility(i);
                        }
                        StickersAlert.this.stickerImageView.getImageReceiver().setImage(StickersAlert.this.selectedSticker, null, StickersAlert.this.selectedSticker.thumb.location, null, "webp", 1);
                        layoutParams = (FrameLayout.LayoutParams) StickersAlert.this.stickerPreviewLayout.getLayoutParams();
                        layoutParams.topMargin = StickersAlert.this.scrollOffsetY;
                        StickersAlert.this.stickerPreviewLayout.setLayoutParams(layoutParams);
                        StickersAlert.this.stickerPreviewLayout.setVisibility(0);
                        animatorSet = new AnimatorSet();
                        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(StickersAlert.this.stickerPreviewLayout, "alpha", new float[]{0.0f, 1.0f})});
                        animatorSet.setDuration(200);
                        animatorSet.start();
                    }
                }
                if (set) {
                    StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(StickersQuery.getEmojiForSticker(StickersAlert.this.selectedSticker.id), StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                }
                fav = StickersQuery.isStickerInFavorites(StickersAlert.this.selectedSticker);
                if (fav) {
                }
                StickersAlert.this.previewFavButton.setImageResource(fav ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite);
                if (fav) {
                }
                StickersAlert.this.previewFavButton.setTag(fav ? Integer.valueOf(1) : null);
                if (StickersAlert.this.previewFavButton.getVisibility() != 8) {
                    access$3100 = StickersAlert.this.previewFavButton;
                    if (!fav) {
                    }
                    access$3100.setVisibility(i);
                }
                StickersAlert.this.stickerImageView.getImageReceiver().setImage(StickersAlert.this.selectedSticker, null, StickersAlert.this.selectedSticker.thumb.location, null, "webp", 1);
                layoutParams = (FrameLayout.LayoutParams) StickersAlert.this.stickerPreviewLayout.getLayoutParams();
                layoutParams.topMargin = StickersAlert.this.scrollOffsetY;
                StickersAlert.this.stickerPreviewLayout.setLayoutParams(layoutParams);
                StickersAlert.this.stickerPreviewLayout.setVisibility(0);
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(StickersAlert.this.stickerPreviewLayout, "alpha", new float[]{0.0f, 1.0f})});
                animatorSet.setDuration(200);
                animatorSet.start();
            }
        }
    }

    private class GridAdapter extends SelectionAdapter {
        private HashMap<Integer, Object> cache = new HashMap();
        private Context context;
        private HashMap<Integer, TLRPC$StickerSetCovered> positionsToSets = new HashMap();
        private int stickersPerRow;
        private int stickersRowCount;
        private int totalItems;

        public GridAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return this.totalItems;
        }

        public int getItemViewType(int position) {
            if (StickersAlert.this.stickerSetCovereds == null) {
                return 0;
            }
            Object object = this.cache.get(Integer.valueOf(position));
            if (object == null) {
                return 1;
            }
            if (object instanceof TLRPC$Document) {
                return 0;
            }
            return 2;
        }

        public boolean isEnabled(ViewHolder holder) {
            return false;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new StickerEmojiCell(this.context) {
                        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                            super.onMeasure(MeasureSpec.makeMeasureSpec(StickersAlert.this.itemSize, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(82.0f), 1073741824));
                        }
                    };
                    break;
                case 1:
                    view = new EmptyCell(this.context);
                    break;
                case 2:
                    view = new FeaturedStickerSetInfoCell(this.context, 8);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            if (StickersAlert.this.stickerSetCovereds != null) {
                switch (holder.getItemViewType()) {
                    case 0:
                        ((StickerEmojiCell) holder.itemView).setSticker((TLRPC$Document) this.cache.get(Integer.valueOf(position)), false);
                        return;
                    case 1:
                        ((EmptyCell) holder.itemView).setHeight(AndroidUtilities.dp(82.0f));
                        return;
                    case 2:
                        holder.itemView.setStickerSet((TLRPC$StickerSetCovered) StickersAlert.this.stickerSetCovereds.get(((Integer) this.cache.get(Integer.valueOf(position))).intValue()), false);
                        return;
                    default:
                        return;
                }
            }
            ((StickerEmojiCell) holder.itemView).setSticker((TLRPC$Document) StickersAlert.this.stickerSet.documents.get(position), StickersAlert.this.showEmoji);
        }

        public void notifyDataSetChanged() {
            int i = 0;
            if (StickersAlert.this.stickerSetCovereds != null) {
                int width = StickersAlert.this.gridView.getMeasuredWidth();
                if (width == 0) {
                    width = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = width / AndroidUtilities.dp(72.0f);
                StickersAlert.this.layoutManager.setSpanCount(this.stickersPerRow);
                this.cache.clear();
                this.positionsToSets.clear();
                this.totalItems = 0;
                this.stickersRowCount = 0;
                for (int a = 0; a < StickersAlert.this.stickerSetCovereds.size(); a++) {
                    TLRPC$StickerSetCovered pack = (TLRPC$StickerSetCovered) StickersAlert.this.stickerSetCovereds.get(a);
                    if (!pack.covers.isEmpty() || pack.cover != null) {
                        int count;
                        int b;
                        this.stickersRowCount = (int) (((double) this.stickersRowCount) + Math.ceil((double) (((float) StickersAlert.this.stickerSetCovereds.size()) / ((float) this.stickersPerRow))));
                        this.positionsToSets.put(Integer.valueOf(this.totalItems), pack);
                        HashMap hashMap = this.cache;
                        int i2 = this.totalItems;
                        this.totalItems = i2 + 1;
                        hashMap.put(Integer.valueOf(i2), Integer.valueOf(a));
                        int startRow = this.totalItems / this.stickersPerRow;
                        if (pack.covers.isEmpty()) {
                            count = 1;
                            this.cache.put(Integer.valueOf(this.totalItems), pack.cover);
                        } else {
                            count = (int) Math.ceil((double) (((float) pack.covers.size()) / ((float) this.stickersPerRow)));
                            for (b = 0; b < pack.covers.size(); b++) {
                                this.cache.put(Integer.valueOf(this.totalItems + b), pack.covers.get(b));
                            }
                        }
                        for (b = 0; b < this.stickersPerRow * count; b++) {
                            this.positionsToSets.put(Integer.valueOf(this.totalItems + b), pack);
                        }
                        this.totalItems += this.stickersPerRow * count;
                    }
                }
            } else {
                if (StickersAlert.this.stickerSet != null) {
                    i = StickersAlert.this.stickerSet.documents.size();
                }
                this.totalItems = i;
            }
            super.notifyDataSetChanged();
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            try {
                boolean result = super.onTouchEvent(widget, buffer, event);
                if (event.getAction() != 1 && event.getAction() != 3) {
                    return result;
                }
                Selection.removeSelection(buffer);
                return result;
            } catch (Exception e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    public StickersAlert(Context context, TLRPC$Photo photo) {
        super(context, false);
        this.shadowAnimation = new AnimatorSet[2];
        this.shadow = new View[2];
        this.parentActivity = (Activity) context;
        final TLRPC$TL_messages_getAttachedStickers req = new TLRPC$TL_messages_getAttachedStickers();
        TLRPC$TL_inputStickeredMediaPhoto inputStickeredMediaPhoto = new TLRPC$TL_inputStickeredMediaPhoto();
        inputStickeredMediaPhoto.id = new TLRPC$TL_inputPhoto();
        inputStickeredMediaPhoto.id.id = photo.id;
        inputStickeredMediaPhoto.id.access_hash = photo.access_hash;
        req.media = inputStickeredMediaPhoto;
        this.reqId = ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        StickersAlert.this.reqId = 0;
                        if (error == null) {
                            TLRPC$Vector vector = response;
                            if (vector.objects.isEmpty()) {
                                StickersAlert.this.dismiss();
                                return;
                            } else if (vector.objects.size() == 1) {
                                TLRPC$StickerSetCovered set = (TLRPC$StickerSetCovered) vector.objects.get(0);
                                StickersAlert.this.inputStickerSet = new TLRPC$TL_inputStickerSetID();
                                StickersAlert.this.inputStickerSet.id = set.set.id;
                                StickersAlert.this.inputStickerSet.access_hash = set.set.access_hash;
                                StickersAlert.this.loadStickerSet();
                                return;
                            } else {
                                StickersAlert.this.stickerSetCovereds = new ArrayList();
                                for (int a = 0; a < vector.objects.size(); a++) {
                                    StickersAlert.this.stickerSetCovereds.add((TLRPC$StickerSetCovered) vector.objects.get(a));
                                }
                                StickersAlert.this.gridView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 48.0f));
                                StickersAlert.this.titleTextView.setVisibility(8);
                                StickersAlert.this.shadow[0].setVisibility(8);
                                StickersAlert.this.adapter.notifyDataSetChanged();
                                return;
                            }
                        }
                        AlertsCreator.processError(error, StickersAlert.this.parentFragment, req, new Object[0]);
                        StickersAlert.this.dismiss();
                    }
                });
            }
        });
        init(context);
    }

    public StickersAlert(Context context, BaseFragment baseFragment, TLRPC$InputStickerSet set, TLRPC$TL_messages_stickerSet loadedSet, StickersAlertDelegate stickersAlertDelegate) {
        super(context, false);
        this.shadowAnimation = new AnimatorSet[2];
        this.shadow = new View[2];
        this.delegate = stickersAlertDelegate;
        this.inputStickerSet = set;
        this.stickerSet = loadedSet;
        this.parentFragment = baseFragment;
        loadStickerSet();
        init(context);
    }

    private void loadStickerSet() {
        if (this.inputStickerSet != null) {
            if (this.stickerSet == null && this.inputStickerSet.short_name != null) {
                this.stickerSet = StickersQuery.getStickerSetByName(this.inputStickerSet.short_name);
            }
            if (this.stickerSet == null) {
                this.stickerSet = StickersQuery.getStickerSetById(Long.valueOf(this.inputStickerSet.id));
            }
            if (this.stickerSet == null) {
                TLRPC$TL_messages_getStickerSet req = new TLRPC$TL_messages_getStickerSet();
                req.stickerset = this.inputStickerSet;
                ConnectionsManager.getInstance().sendRequest(req, new C27722());
            } else if (this.adapter != null) {
                updateSendButton();
                updateFields();
                this.adapter.notifyDataSetChanged();
            }
        }
        if (this.stickerSet != null) {
            this.showEmoji = !this.stickerSet.set.masks;
        }
    }

    private void init(Context context) {
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.containerView = new FrameLayout(context) {
            private int lastNotifyWidth;

            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (ev.getAction() != 0 || StickersAlert.this.scrollOffsetY == 0 || ev.getY() >= ((float) StickersAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(ev);
                }
                StickersAlert.this.dismiss();
                return true;
            }

            public boolean onTouchEvent(MotionEvent e) {
                return !StickersAlert.this.isDismissed() && super.onTouchEvent(e);
            }

            protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                int contentSize;
                int height = MeasureSpec.getSize(heightMeasureSpec);
                if (VERSION.SDK_INT >= 21) {
                    height -= AndroidUtilities.statusBarHeight;
                }
                int measuredWidth = getMeasuredWidth();
                StickersAlert.this.itemSize = (MeasureSpec.getSize(widthMeasureSpec) - AndroidUtilities.dp(36.0f)) / 5;
                if (StickersAlert.this.stickerSetCovereds != null) {
                    contentSize = (AndroidUtilities.dp(56.0f) + (AndroidUtilities.dp(60.0f) * StickersAlert.this.stickerSetCovereds.size())) + (StickersAlert.this.adapter.stickersRowCount * AndroidUtilities.dp(82.0f));
                } else {
                    contentSize = ((Math.max(3, StickersAlert.this.stickerSet != null ? (int) Math.ceil((double) (((float) StickersAlert.this.stickerSet.documents.size()) / 5.0f)) : 0) * AndroidUtilities.dp(82.0f)) + AndroidUtilities.dp(96.0f)) + StickersAlert.backgroundPaddingTop;
                }
                int padding = ((double) contentSize) < ((double) (height / 5)) * 3.2d ? 0 : (height / 5) * 2;
                if (padding != 0 && contentSize < height) {
                    padding -= height - contentSize;
                }
                if (padding == 0) {
                    padding = StickersAlert.backgroundPaddingTop;
                }
                if (StickersAlert.this.stickerSetCovereds != null) {
                    padding += AndroidUtilities.dp(8.0f);
                }
                if (StickersAlert.this.gridView.getPaddingTop() != padding) {
                    StickersAlert.this.ignoreLayout = true;
                    StickersAlert.this.gridView.setPadding(AndroidUtilities.dp(10.0f), padding, AndroidUtilities.dp(10.0f), 0);
                    StickersAlert.this.emptyView.setPadding(0, padding, 0, 0);
                    StickersAlert.this.ignoreLayout = false;
                }
                super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(Math.min(contentSize, height), 1073741824));
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                if (this.lastNotifyWidth != right - left) {
                    this.lastNotifyWidth = right - left;
                    if (!(StickersAlert.this.adapter == null || StickersAlert.this.stickerSetCovereds == null)) {
                        StickersAlert.this.adapter.notifyDataSetChanged();
                    }
                }
                super.onLayout(changed, left, top, right, bottom);
                StickersAlert.this.updateLayout();
            }

            public void requestLayout() {
                if (!StickersAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }

            protected void onDraw(Canvas canvas) {
                StickersAlert.this.shadowDrawable.setBounds(0, StickersAlert.this.scrollOffsetY - StickersAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                StickersAlert.this.shadowDrawable.draw(canvas);
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.shadow[0] = new View(context);
        this.shadow[0].setBackgroundResource(R.drawable.header_shadow);
        this.shadow[0].setAlpha(0.0f);
        this.shadow[0].setVisibility(4);
        this.shadow[0].setTag(Integer.valueOf(1));
        this.containerView.addView(this.shadow[0], LayoutHelper.createFrame(-1, 3.0f, 51, 0.0f, 48.0f, 0.0f, 0.0f));
        this.gridView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent event) {
                boolean result = StickerPreviewViewer.getInstance().onInterceptTouchEvent(event, StickersAlert.this.gridView, 0, null);
                if (super.onInterceptTouchEvent(event) || result) {
                    return true;
                }
                return false;
            }

            public void requestLayout() {
                if (!StickersAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.gridView.setTag(Integer.valueOf(14));
        RecyclerListView recyclerListView = this.gridView;
        LayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        this.layoutManager = gridLayoutManager;
        recyclerListView.setLayoutManager(gridLayoutManager);
        this.layoutManager.setSpanSizeLookup(new C27755());
        recyclerListView = this.gridView;
        Adapter gridAdapter = new GridAdapter(context);
        this.adapter = gridAdapter;
        recyclerListView.setAdapter(gridAdapter);
        this.gridView.setVerticalScrollBarEnabled(false);
        this.gridView.addItemDecoration(new C27766());
        this.gridView.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
        this.gridView.setClipToPadding(false);
        this.gridView.setEnabled(true);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnTouchListener(new C27777());
        this.gridView.setOnScrollListener(new C27788());
        this.stickersOnItemClickListener = new C27799();
        this.gridView.setOnItemClickListener(this.stickersOnItemClickListener);
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 48.0f, 0.0f, 48.0f));
        this.emptyView = new FrameLayout(context) {
            public void requestLayout() {
                if (!StickersAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f, 51, 0.0f, 0.0f, 0.0f, 48.0f));
        this.gridView.setEmptyView(this.emptyView);
        this.emptyView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        this.titleTextView = new TextView(context);
        this.titleTextView.setLines(1);
        this.titleTextView.setSingleLine(true);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
        this.titleTextView.setTextSize(1, 20.0f);
        this.titleTextView.setLinkTextColor(Theme.getColor(Theme.key_dialogTextLink));
        this.titleTextView.setHighlightColor(Theme.getColor(Theme.key_dialogLinkSelection));
        this.titleTextView.setEllipsize(TruncateAt.MIDDLE);
        this.titleTextView.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.titleTextView.setGravity(16);
        this.titleTextView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.titleTextView.setMovementMethod(new LinkMovementMethodMy());
        this.containerView.addView(this.titleTextView, LayoutHelper.createLinear(-1, 48));
        this.emptyView.addView(new RadialProgressView(context), LayoutHelper.createFrame(-2, -2, 17));
        this.shadow[1] = new View(context);
        this.shadow[1].setBackgroundResource(R.drawable.header_shadow_reverse);
        this.containerView.addView(this.shadow[1], LayoutHelper.createFrame(-1, 3.0f, 83, 0.0f, 0.0f, 0.0f, 48.0f));
        this.pickerBottomLayout = new PickerBottomLayout(context, false);
        this.pickerBottomLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.containerView.addView(this.pickerBottomLayout, LayoutHelper.createFrame(-1, 48, 83));
        this.pickerBottomLayout.cancelButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.pickerBottomLayout.cancelButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        this.pickerBottomLayout.cancelButton.setText(LocaleController.getString("Close", R.string.Close).toUpperCase());
        this.pickerBottomLayout.cancelButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                StickersAlert.this.dismiss();
            }
        });
        this.pickerBottomLayout.doneButton.setPadding(AndroidUtilities.dp(18.0f), 0, AndroidUtilities.dp(18.0f), 0);
        this.pickerBottomLayout.doneButtonBadgeTextView.setBackgroundDrawable(Theme.createRoundRectDrawable(AndroidUtilities.dp(12.5f), Theme.getColor(Theme.key_dialogBadgeBackground)));
        this.stickerPreviewLayout = new FrameLayout(context);
        this.stickerPreviewLayout.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground) & -536870913);
        this.stickerPreviewLayout.setVisibility(8);
        this.stickerPreviewLayout.setSoundEffectsEnabled(false);
        this.containerView.addView(this.stickerPreviewLayout, LayoutHelper.createFrame(-1, -1.0f));
        this.stickerPreviewLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StickersAlert.this.hidePreview();
            }
        });
        ImageView closeButton = new ImageView(context);
        closeButton.setImageResource(R.drawable.msg_panel_clear);
        closeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogTextGray3), Mode.MULTIPLY));
        closeButton.setScaleType(ScaleType.CENTER);
        this.stickerPreviewLayout.addView(closeButton, LayoutHelper.createFrame(48, 48, 53));
        closeButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StickersAlert.this.hidePreview();
            }
        });
        this.stickerImageView = new BackupImageView(context);
        this.stickerImageView.setAspectFit(true);
        this.stickerPreviewLayout.addView(this.stickerImageView);
        this.stickerEmojiTextView = new TextView(context);
        this.stickerEmojiTextView.setTextSize(1, 30.0f);
        this.stickerEmojiTextView.setGravity(85);
        this.stickerPreviewLayout.addView(this.stickerEmojiTextView);
        this.previewSendButton = new TextView(context);
        this.previewSendButton.setTextSize(1, 14.0f);
        this.previewSendButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        this.previewSendButton.setGravity(17);
        this.previewSendButton.setBackgroundColor(Theme.getColor(Theme.key_dialogBackground));
        this.previewSendButton.setPadding(AndroidUtilities.dp(29.0f), 0, AndroidUtilities.dp(29.0f), 0);
        this.previewSendButton.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
        this.stickerPreviewLayout.addView(this.previewSendButton, LayoutHelper.createFrame(-1, 48, 83));
        this.previewSendButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StickersAlert.this.delegate.onStickerSelected(StickersAlert.this.selectedSticker);
                StickersAlert.this.dismiss();
            }
        });
        this.previewFavButton = new ImageView(context);
        this.previewFavButton.setScaleType(ScaleType.CENTER);
        this.stickerPreviewLayout.addView(this.previewFavButton, LayoutHelper.createFrame(48, 48.0f, 85, 0.0f, 0.0f, 4.0f, 0.0f));
        this.previewFavButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
        this.previewFavButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                StickersQuery.addRecentSticker(2, StickersAlert.this.selectedSticker, (int) (System.currentTimeMillis() / 1000), StickersAlert.this.previewFavButton.getTag() != null);
                if (StickersAlert.this.previewFavButton.getTag() == null) {
                    StickersAlert.this.previewFavButton.setTag(Integer.valueOf(1));
                    StickersAlert.this.previewFavButton.setImageResource(R.drawable.stickers_unfavorite);
                    return;
                }
                StickersAlert.this.previewFavButton.setTag(null);
                StickersAlert.this.previewFavButton.setImageResource(R.drawable.stickers_favorite);
            }
        });
        this.previewSendButtonShadow = new View(context);
        this.previewSendButtonShadow.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.stickerPreviewLayout.addView(this.previewSendButtonShadow, LayoutHelper.createFrame(-1, 3.0f, 83, 0.0f, 0.0f, 0.0f, 48.0f));
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        updateFields();
        updateSendButton();
        this.adapter.notifyDataSetChanged();
    }

    private void updateSendButton() {
        int size = (int) (((float) (Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) / 2)) / AndroidUtilities.density);
        if (this.delegate == null || (this.stickerSet != null && this.stickerSet.set.masks)) {
            this.previewSendButton.setText(LocaleController.getString("Close", R.string.Close).toUpperCase());
            this.stickerImageView.setLayoutParams(LayoutHelper.createFrame(size, size, 17));
            this.stickerEmojiTextView.setLayoutParams(LayoutHelper.createFrame(size, size, 17));
            this.previewSendButton.setVisibility(8);
            this.previewFavButton.setVisibility(8);
            this.previewSendButtonShadow.setVisibility(8);
            return;
        }
        this.previewSendButton.setText(LocaleController.getString("SendSticker", R.string.SendSticker).toUpperCase());
        this.stickerImageView.setLayoutParams(LayoutHelper.createFrame(size, (float) size, 17, 0.0f, 0.0f, 0.0f, 30.0f));
        this.stickerEmojiTextView.setLayoutParams(LayoutHelper.createFrame(size, (float) size, 17, 0.0f, 0.0f, 0.0f, 30.0f));
        this.previewSendButton.setVisibility(0);
        this.previewFavButton.setVisibility(0);
        this.previewSendButtonShadow.setVisibility(0);
    }

    public void setInstallDelegate(StickersAlertInstallDelegate stickersAlertInstallDelegate) {
        this.installDelegate = stickersAlertInstallDelegate;
    }

    private void updateFields() {
        Exception e;
        SpannableStringBuilder spannableStringBuilder;
        if (this.titleTextView != null) {
            if (this.stickerSet != null) {
                spannableStringBuilder = null;
                try {
                    if (this.urlPattern == null) {
                        this.urlPattern = Pattern.compile("@[a-zA-Z\\d_]{1,32}");
                    }
                    Matcher matcher = this.urlPattern.matcher(this.stickerSet.set.title);
                    SpannableStringBuilder stringBuilder = null;
                    while (matcher.find()) {
                        try {
                            if (stringBuilder == null) {
                                spannableStringBuilder = new SpannableStringBuilder(this.stickerSet.set.title);
                            } else {
                                spannableStringBuilder = stringBuilder;
                            }
                            int start = matcher.start();
                            int end = matcher.end();
                            if (this.stickerSet.set.title.charAt(start) != '@') {
                                start++;
                            }
                            URLSpanNoUnderline url = new URLSpanNoUnderline(this.stickerSet.set.title.subSequence(start + 1, end).toString()) {
                                public void onClick(View widget) {
                                    MessagesController.openByUserName(getURL(), StickersAlert.this.parentFragment, 1);
                                    StickersAlert.this.dismiss();
                                }
                            };
                            if (url != null) {
                                spannableStringBuilder.setSpan(url, start, end, 0);
                            }
                            stringBuilder = spannableStringBuilder;
                        } catch (Exception e2) {
                            e = e2;
                            spannableStringBuilder = stringBuilder;
                        }
                    }
                    spannableStringBuilder = stringBuilder;
                } catch (Exception e3) {
                    e = e3;
                }
                TextView textView = this.titleTextView;
                if (spannableStringBuilder == null) {
                    spannableStringBuilder = this.stickerSet.set.title;
                }
                textView.setText(spannableStringBuilder);
                if (this.stickerSet.set != null || !StickersQuery.isStickerPackInstalled(this.stickerSet.set.id)) {
                    OnClickListener anonymousClass18 = new OnClickListener() {

                        /* renamed from: org.telegram.ui.Components.StickersAlert$18$1 */
                        class C27691 implements RequestDelegate {
                            C27691() {
                            }

                            public void run(final TLObject response, final TLRPC$TL_error error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        try {
                                            int i;
                                            if (error == null) {
                                                if (StickersAlert.this.stickerSet.set.masks) {
                                                    Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("AddMasksInstalled", R.string.AddMasksInstalled), 0).show();
                                                } else {
                                                    Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("AddStickersInstalled", R.string.AddStickersInstalled), 0).show();
                                                }
                                                if (response instanceof TLRPC$TL_messages_stickerSetInstallResultArchive) {
                                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadArchivedStickers, new Object[0]);
                                                    if (!(StickersAlert.this.parentFragment == null || StickersAlert.this.parentFragment.getParentActivity() == null)) {
                                                        StickersAlert.this.parentFragment.showDialog(new StickersArchiveAlert(StickersAlert.this.parentFragment.getParentActivity(), StickersAlert.this.parentFragment, ((TLRPC$TL_messages_stickerSetInstallResultArchive) response).sets).create());
                                                    }
                                                }
                                                if (StickersAlert.this.stickerSet.set.masks) {
                                                    i = 0;
                                                } else {
                                                    i = 1;
                                                }
                                                StickersQuery.loadStickers(i, false, true);
                                            }
                                            Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred), 0).show();
                                            if (StickersAlert.this.stickerSet.set.masks) {
                                                i = 0;
                                            } else {
                                                i = 1;
                                            }
                                            StickersQuery.loadStickers(i, false, true);
                                        } catch (Exception e) {
                                            FileLog.e(e);
                                        }
                                    }
                                });
                            }
                        }

                        public void onClick(View v) {
                            StickersAlert.this.dismiss();
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetInstalled();
                            }
                            TLRPC$TL_messages_installStickerSet req = new TLRPC$TL_messages_installStickerSet();
                            req.stickerset = StickersAlert.this.inputStickerSet;
                            ConnectionsManager.getInstance().sendRequest(req, new C27691());
                        }
                    };
                    String string = (this.stickerSet == null && this.stickerSet.set.masks) ? LocaleController.getString("AddMasks", R.string.AddMasks) : LocaleController.getString("AddStickers", R.string.AddStickers);
                    setRightButton(anonymousClass18, string, Theme.getColor(Theme.key_dialogTextBlue2), true);
                } else if (this.stickerSet.set.official) {
                    setRightButton(new OnClickListener() {
                        public void onClick(View v) {
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetUninstalled();
                            }
                            StickersAlert.this.dismiss();
                            StickersQuery.removeStickersSet(StickersAlert.this.getContext(), StickersAlert.this.stickerSet.set, 1, StickersAlert.this.parentFragment, true);
                        }
                    }, LocaleController.getString("StickersRemove", R.string.StickersHide), Theme.getColor(Theme.key_dialogTextRed), false);
                } else {
                    setRightButton(new OnClickListener() {
                        public void onClick(View v) {
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetUninstalled();
                            }
                            StickersAlert.this.dismiss();
                            StickersQuery.removeStickersSet(StickersAlert.this.getContext(), StickersAlert.this.stickerSet.set, 0, StickersAlert.this.parentFragment, true);
                        }
                    }, LocaleController.getString("StickersRemove", R.string.StickersRemove), Theme.getColor(Theme.key_dialogTextRed), false);
                }
                this.adapter.notifyDataSetChanged();
            }
            setRightButton(null, null, Theme.getColor(Theme.key_dialogTextRed), false);
            return;
        }
        return;
        FileLog.e(e);
        TextView textView2 = this.titleTextView;
        if (spannableStringBuilder == null) {
            spannableStringBuilder = this.stickerSet.set.title;
        }
        textView2.setText(spannableStringBuilder);
        if (this.stickerSet.set != null) {
        }
        OnClickListener anonymousClass182 = /* anonymous class already generated */;
        if (this.stickerSet == null) {
        }
        setRightButton(anonymousClass182, string, Theme.getColor(Theme.key_dialogTextBlue2), true);
        this.adapter.notifyDataSetChanged();
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        if (this.gridView.getChildCount() <= 0) {
            RecyclerListView recyclerListView = this.gridView;
            int paddingTop = this.gridView.getPaddingTop();
            this.scrollOffsetY = paddingTop;
            recyclerListView.setTopGlowOffset(paddingTop);
            if (this.stickerSetCovereds == null) {
                this.titleTextView.setTranslationY((float) this.scrollOffsetY);
                this.shadow[0].setTranslationY((float) this.scrollOffsetY);
            }
            this.containerView.invalidate();
            return;
        }
        View child = this.gridView.getChildAt(0);
        Holder holder = (Holder) this.gridView.findContainingViewHolder(child);
        int top = child.getTop();
        int newOffset = 0;
        if (top < 0 || holder == null || holder.getAdapterPosition() != 0) {
            runShadowAnimation(0, true);
        } else {
            newOffset = top;
            runShadowAnimation(0, false);
        }
        if (this.scrollOffsetY != newOffset) {
            recyclerListView = this.gridView;
            this.scrollOffsetY = newOffset;
            recyclerListView.setTopGlowOffset(newOffset);
            if (this.stickerSetCovereds == null) {
                this.titleTextView.setTranslationY((float) this.scrollOffsetY);
                this.shadow[0].setTranslationY((float) this.scrollOffsetY);
            }
            this.containerView.invalidate();
        }
    }

    private void hidePreview() {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.stickerPreviewLayout, "alpha", new float[]{0.0f});
        animatorSet.playTogether(animatorArr);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                StickersAlert.this.stickerPreviewLayout.setVisibility(8);
            }
        });
        animatorSet.start();
    }

    private void runShadowAnimation(final int num, final boolean show) {
        if (this.stickerSetCovereds == null) {
            if ((show && this.shadow[num].getTag() != null) || (!show && this.shadow[num].getTag() == null)) {
                this.shadow[num].setTag(show ? null : Integer.valueOf(1));
                if (show) {
                    this.shadow[num].setVisibility(0);
                }
                if (this.shadowAnimation[num] != null) {
                    this.shadowAnimation[num].cancel();
                }
                this.shadowAnimation[num] = new AnimatorSet();
                AnimatorSet animatorSet = this.shadowAnimation[num];
                Animator[] animatorArr = new Animator[1];
                Object obj = this.shadow[num];
                String str = "alpha";
                float[] fArr = new float[1];
                fArr[0] = show ? 1.0f : 0.0f;
                animatorArr[0] = ObjectAnimator.ofFloat(obj, str, fArr);
                animatorSet.playTogether(animatorArr);
                this.shadowAnimation[num].setDuration(150);
                this.shadowAnimation[num].addListener(new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        if (StickersAlert.this.shadowAnimation[num] != null && StickersAlert.this.shadowAnimation[num].equals(animation)) {
                            if (!show) {
                                StickersAlert.this.shadow[num].setVisibility(4);
                            }
                            StickersAlert.this.shadowAnimation[num] = null;
                        }
                    }

                    public void onAnimationCancel(Animator animation) {
                        if (StickersAlert.this.shadowAnimation[num] != null && StickersAlert.this.shadowAnimation[num].equals(animation)) {
                            StickersAlert.this.shadowAnimation[num] = null;
                        }
                    }
                });
                this.shadowAnimation[num].start();
            }
        }
    }

    public void dismiss() {
        super.dismiss();
        if (this.reqId != 0) {
            ConnectionsManager.getInstance().cancelRequest(this.reqId, true);
            this.reqId = 0;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.emojiDidLoaded);
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.emojiDidLoaded) {
            if (this.gridView != null) {
                int count = this.gridView.getChildCount();
                for (int a = 0; a < count; a++) {
                    this.gridView.getChildAt(a).invalidate();
                }
            }
            if (StickerPreviewViewer.getInstance().isVisible()) {
                StickerPreviewViewer.getInstance().close();
            }
            StickerPreviewViewer.getInstance().reset();
        }
    }

    private void setRightButton(OnClickListener onClickListener, String title, int color, boolean showCircle) {
        if (title == null) {
            this.pickerBottomLayout.doneButton.setVisibility(8);
            return;
        }
        this.pickerBottomLayout.doneButton.setVisibility(0);
        if (showCircle) {
            this.pickerBottomLayout.doneButtonBadgeTextView.setVisibility(0);
            this.pickerBottomLayout.doneButtonBadgeTextView.setText(String.format("%d", new Object[]{Integer.valueOf(this.stickerSet.documents.size())}));
        } else {
            this.pickerBottomLayout.doneButtonBadgeTextView.setVisibility(8);
        }
        this.pickerBottomLayout.doneButtonTextView.setTextColor(color);
        this.pickerBottomLayout.doneButtonTextView.setText(title.toUpperCase());
        this.pickerBottomLayout.doneButton.setOnClickListener(onClickListener);
    }
}
