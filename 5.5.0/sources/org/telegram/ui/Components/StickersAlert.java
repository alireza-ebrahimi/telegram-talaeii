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
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.tgnet.TLRPC.Document;
import org.telegram.tgnet.TLRPC.DocumentAttribute;
import org.telegram.tgnet.TLRPC.InputStickerSet;
import org.telegram.tgnet.TLRPC.InputStickeredMedia;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.StickerSetCovered;
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
    private InputStickerSet inputStickerSet;
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
    private Document selectedSticker;
    private View[] shadow;
    private AnimatorSet[] shadowAnimation;
    private Drawable shadowDrawable;
    private boolean showEmoji;
    private TextView stickerEmojiTextView;
    private BackupImageView stickerImageView;
    private FrameLayout stickerPreviewLayout;
    private TLRPC$TL_messages_stickerSet stickerSet;
    private ArrayList<StickerSetCovered> stickerSetCovereds;
    private OnItemClickListener stickersOnItemClickListener;
    private TextView titleTextView;
    private Pattern urlPattern;

    public interface StickersAlertInstallDelegate {
        void onStickerSetInstalled();

        void onStickerSetUninstalled();
    }

    public interface StickersAlertDelegate {
        void onStickerSelected(Document document);
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$2 */
    class C46102 implements RequestDelegate {
        C46102() {
        }

        public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    StickersAlert.this.reqId = 0;
                    if (tLRPC$TL_error == null) {
                        StickersAlert.this.stickerSet = (TLRPC$TL_messages_stickerSet) tLObject;
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
    class C46135 extends SpanSizeLookup {
        C46135() {
        }

        public int getSpanSize(int i) {
            return ((StickersAlert.this.stickerSetCovereds == null || !(StickersAlert.this.adapter.cache.get(Integer.valueOf(i)) instanceof Integer)) && i != StickersAlert.this.adapter.totalItems) ? 1 : StickersAlert.this.adapter.stickersPerRow;
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$6 */
    class C46146 extends ItemDecoration {
        C46146() {
        }

        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, State state) {
            rect.left = 0;
            rect.right = 0;
            rect.bottom = 0;
            rect.top = 0;
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$7 */
    class C46157 implements OnTouchListener {
        C46157() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return StickerPreviewViewer.getInstance().onTouch(motionEvent, StickersAlert.this.gridView, 0, StickersAlert.this.stickersOnItemClickListener, null);
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$8 */
    class C46168 extends OnScrollListener {
        C46168() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            StickersAlert.this.updateLayout();
        }
    }

    /* renamed from: org.telegram.ui.Components.StickersAlert$9 */
    class C46179 implements OnItemClickListener {
        C46179() {
        }

        public void onItemClick(View view, int i) {
            if (StickersAlert.this.stickerSetCovereds != null) {
                StickerSetCovered stickerSetCovered = (StickerSetCovered) StickersAlert.this.adapter.positionsToSets.get(Integer.valueOf(i));
                if (stickerSetCovered != null) {
                    StickersAlert.this.dismiss();
                    InputStickerSet tLRPC$TL_inputStickerSetID = new TLRPC$TL_inputStickerSetID();
                    tLRPC$TL_inputStickerSetID.access_hash = stickerSetCovered.set.access_hash;
                    tLRPC$TL_inputStickerSetID.id = stickerSetCovered.set.id;
                    new StickersAlert(StickersAlert.this.parentActivity, StickersAlert.this.parentFragment, tLRPC$TL_inputStickerSetID, null, null).show();
                }
            } else if (StickersAlert.this.stickerSet != null && i >= 0 && i < StickersAlert.this.stickerSet.documents.size()) {
                int i2;
                boolean isStickerInFavorites;
                ImageView access$3100;
                LayoutParams layoutParams;
                AnimatorSet animatorSet;
                boolean z;
                StickersAlert.this.selectedSticker = (Document) StickersAlert.this.stickerSet.documents.get(i);
                for (int i3 = 0; i3 < StickersAlert.this.selectedSticker.attributes.size(); i3++) {
                    DocumentAttribute documentAttribute = (DocumentAttribute) StickersAlert.this.selectedSticker.attributes.get(i3);
                    if (documentAttribute instanceof TLRPC$TL_documentAttributeSticker) {
                        if (documentAttribute.alt != null && documentAttribute.alt.length() > 0) {
                            StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(documentAttribute.alt, StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                            i2 = 1;
                            if (i2 == 0) {
                                StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(StickersQuery.getEmojiForSticker(StickersAlert.this.selectedSticker.id), StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                            }
                            isStickerInFavorites = StickersQuery.isStickerInFavorites(StickersAlert.this.selectedSticker);
                            StickersAlert.this.previewFavButton.setImageResource(isStickerInFavorites ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite);
                            StickersAlert.this.previewFavButton.setTag(isStickerInFavorites ? Integer.valueOf(1) : null);
                            if (StickersAlert.this.previewFavButton.getVisibility() != 8) {
                                access$3100 = StickersAlert.this.previewFavButton;
                                i2 = (isStickerInFavorites || StickersQuery.canAddStickerToFavorites()) ? 0 : 4;
                                access$3100.setVisibility(i2);
                            }
                            StickersAlert.this.stickerImageView.getImageReceiver().setImage(StickersAlert.this.selectedSticker, null, StickersAlert.this.selectedSticker.thumb.location, null, "webp", 1);
                            layoutParams = (LayoutParams) StickersAlert.this.stickerPreviewLayout.getLayoutParams();
                            layoutParams.topMargin = StickersAlert.this.scrollOffsetY;
                            StickersAlert.this.stickerPreviewLayout.setLayoutParams(layoutParams);
                            StickersAlert.this.stickerPreviewLayout.setVisibility(0);
                            animatorSet = new AnimatorSet();
                            animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(StickersAlert.this.stickerPreviewLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                            animatorSet.setDuration(200);
                            animatorSet.start();
                        }
                        z = false;
                        if (i2 == 0) {
                            StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(StickersQuery.getEmojiForSticker(StickersAlert.this.selectedSticker.id), StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                        }
                        isStickerInFavorites = StickersQuery.isStickerInFavorites(StickersAlert.this.selectedSticker);
                        if (isStickerInFavorites) {
                        }
                        StickersAlert.this.previewFavButton.setImageResource(isStickerInFavorites ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite);
                        if (isStickerInFavorites) {
                        }
                        StickersAlert.this.previewFavButton.setTag(isStickerInFavorites ? Integer.valueOf(1) : null);
                        if (StickersAlert.this.previewFavButton.getVisibility() != 8) {
                            access$3100 = StickersAlert.this.previewFavButton;
                            if (!isStickerInFavorites) {
                            }
                            access$3100.setVisibility(i2);
                        }
                        StickersAlert.this.stickerImageView.getImageReceiver().setImage(StickersAlert.this.selectedSticker, null, StickersAlert.this.selectedSticker.thumb.location, null, "webp", 1);
                        layoutParams = (LayoutParams) StickersAlert.this.stickerPreviewLayout.getLayoutParams();
                        layoutParams.topMargin = StickersAlert.this.scrollOffsetY;
                        StickersAlert.this.stickerPreviewLayout.setLayoutParams(layoutParams);
                        StickersAlert.this.stickerPreviewLayout.setVisibility(0);
                        animatorSet = new AnimatorSet();
                        animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(StickersAlert.this.stickerPreviewLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                        animatorSet.setDuration(200);
                        animatorSet.start();
                    }
                }
                z = false;
                if (i2 == 0) {
                    StickersAlert.this.stickerEmojiTextView.setText(Emoji.replaceEmoji(StickersQuery.getEmojiForSticker(StickersAlert.this.selectedSticker.id), StickersAlert.this.stickerEmojiTextView.getPaint().getFontMetricsInt(), AndroidUtilities.dp(30.0f), false));
                }
                isStickerInFavorites = StickersQuery.isStickerInFavorites(StickersAlert.this.selectedSticker);
                if (isStickerInFavorites) {
                }
                StickersAlert.this.previewFavButton.setImageResource(isStickerInFavorites ? R.drawable.stickers_unfavorite : R.drawable.stickers_favorite);
                if (isStickerInFavorites) {
                }
                StickersAlert.this.previewFavButton.setTag(isStickerInFavorites ? Integer.valueOf(1) : null);
                if (StickersAlert.this.previewFavButton.getVisibility() != 8) {
                    access$3100 = StickersAlert.this.previewFavButton;
                    if (isStickerInFavorites) {
                    }
                    access$3100.setVisibility(i2);
                }
                StickersAlert.this.stickerImageView.getImageReceiver().setImage(StickersAlert.this.selectedSticker, null, StickersAlert.this.selectedSticker.thumb.location, null, "webp", 1);
                layoutParams = (LayoutParams) StickersAlert.this.stickerPreviewLayout.getLayoutParams();
                layoutParams.topMargin = StickersAlert.this.scrollOffsetY;
                StickersAlert.this.stickerPreviewLayout.setLayoutParams(layoutParams);
                StickersAlert.this.stickerPreviewLayout.setVisibility(0);
                animatorSet = new AnimatorSet();
                animatorSet.playTogether(new Animator[]{ObjectAnimator.ofFloat(StickersAlert.this.stickerPreviewLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f})});
                animatorSet.setDuration(200);
                animatorSet.start();
            }
        }
    }

    private class GridAdapter extends SelectionAdapter {
        private HashMap<Integer, Object> cache = new HashMap();
        private Context context;
        private HashMap<Integer, StickerSetCovered> positionsToSets = new HashMap();
        private int stickersPerRow;
        private int stickersRowCount;
        private int totalItems;

        public GridAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return this.totalItems;
        }

        public int getItemViewType(int i) {
            if (StickersAlert.this.stickerSetCovereds == null) {
                return 0;
            }
            Object obj = this.cache.get(Integer.valueOf(i));
            return obj != null ? obj instanceof Document ? 0 : 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return false;
        }

        public void notifyDataSetChanged() {
            int i = 0;
            if (StickersAlert.this.stickerSetCovereds != null) {
                int measuredWidth = StickersAlert.this.gridView.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = AndroidUtilities.displaySize.x;
                }
                this.stickersPerRow = measuredWidth / AndroidUtilities.dp(72.0f);
                StickersAlert.this.layoutManager.setSpanCount(this.stickersPerRow);
                this.cache.clear();
                this.positionsToSets.clear();
                this.totalItems = 0;
                this.stickersRowCount = 0;
                for (int i2 = 0; i2 < StickersAlert.this.stickerSetCovereds.size(); i2++) {
                    StickerSetCovered stickerSetCovered = (StickerSetCovered) StickersAlert.this.stickerSetCovereds.get(i2);
                    if (!stickerSetCovered.covers.isEmpty() || stickerSetCovered.cover != null) {
                        this.stickersRowCount = (int) (((double) this.stickersRowCount) + Math.ceil((double) (((float) StickersAlert.this.stickerSetCovereds.size()) / ((float) this.stickersPerRow))));
                        this.positionsToSets.put(Integer.valueOf(this.totalItems), stickerSetCovered);
                        HashMap hashMap = this.cache;
                        int i3 = this.totalItems;
                        this.totalItems = i3 + 1;
                        hashMap.put(Integer.valueOf(i3), Integer.valueOf(i2));
                        int i4 = this.totalItems / this.stickersPerRow;
                        if (stickerSetCovered.covers.isEmpty()) {
                            i4 = 1;
                            this.cache.put(Integer.valueOf(this.totalItems), stickerSetCovered.cover);
                        } else {
                            i3 = (int) Math.ceil((double) (((float) stickerSetCovered.covers.size()) / ((float) this.stickersPerRow)));
                            for (i4 = 0; i4 < stickerSetCovered.covers.size(); i4++) {
                                this.cache.put(Integer.valueOf(this.totalItems + i4), stickerSetCovered.covers.get(i4));
                            }
                            i4 = i3;
                        }
                        for (i3 = 0; i3 < this.stickersPerRow * i4; i3++) {
                            this.positionsToSets.put(Integer.valueOf(this.totalItems + i3), stickerSetCovered);
                        }
                        this.totalItems += i4 * this.stickersPerRow;
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

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            if (StickersAlert.this.stickerSetCovereds != null) {
                switch (viewHolder.getItemViewType()) {
                    case 0:
                        ((StickerEmojiCell) viewHolder.itemView).setSticker((Document) this.cache.get(Integer.valueOf(i)), false);
                        return;
                    case 1:
                        ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(82.0f));
                        return;
                    case 2:
                        ((FeaturedStickerSetInfoCell) viewHolder.itemView).setStickerSet((StickerSetCovered) StickersAlert.this.stickerSetCovereds.get(((Integer) this.cache.get(Integer.valueOf(i))).intValue()), false);
                        return;
                    default:
                        return;
                }
            }
            ((StickerEmojiCell) viewHolder.itemView).setSticker((Document) StickersAlert.this.stickerSet.documents.get(i), StickersAlert.this.showEmoji);
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new StickerEmojiCell(this.context) {
                        public void onMeasure(int i, int i2) {
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
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            try {
                boolean onTouchEvent = super.onTouchEvent(textView, spannable, motionEvent);
                if (motionEvent.getAction() != 1 && motionEvent.getAction() != 3) {
                    return onTouchEvent;
                }
                Selection.removeSelection(spannable);
                return onTouchEvent;
            } catch (Throwable e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    public StickersAlert(Context context, Photo photo) {
        super(context, false);
        this.shadowAnimation = new AnimatorSet[2];
        this.shadow = new View[2];
        this.parentActivity = (Activity) context;
        final TLObject tLRPC$TL_messages_getAttachedStickers = new TLRPC$TL_messages_getAttachedStickers();
        InputStickeredMedia tLRPC$TL_inputStickeredMediaPhoto = new TLRPC$TL_inputStickeredMediaPhoto();
        tLRPC$TL_inputStickeredMediaPhoto.id = new TLRPC$TL_inputPhoto();
        tLRPC$TL_inputStickeredMediaPhoto.id.id = photo.id;
        tLRPC$TL_inputStickeredMediaPhoto.id.access_hash = photo.access_hash;
        tLRPC$TL_messages_getAttachedStickers.media = tLRPC$TL_inputStickeredMediaPhoto;
        this.reqId = ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getAttachedStickers, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        StickersAlert.this.reqId = 0;
                        if (tLRPC$TL_error == null) {
                            TLRPC$Vector tLRPC$Vector = (TLRPC$Vector) tLObject;
                            if (tLRPC$Vector.objects.isEmpty()) {
                                StickersAlert.this.dismiss();
                                return;
                            } else if (tLRPC$Vector.objects.size() == 1) {
                                StickerSetCovered stickerSetCovered = (StickerSetCovered) tLRPC$Vector.objects.get(0);
                                StickersAlert.this.inputStickerSet = new TLRPC$TL_inputStickerSetID();
                                StickersAlert.this.inputStickerSet.id = stickerSetCovered.set.id;
                                StickersAlert.this.inputStickerSet.access_hash = stickerSetCovered.set.access_hash;
                                StickersAlert.this.loadStickerSet();
                                return;
                            } else {
                                StickersAlert.this.stickerSetCovereds = new ArrayList();
                                for (int i = 0; i < tLRPC$Vector.objects.size(); i++) {
                                    StickersAlert.this.stickerSetCovereds.add((StickerSetCovered) tLRPC$Vector.objects.get(i));
                                }
                                StickersAlert.this.gridView.setLayoutParams(LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
                                StickersAlert.this.titleTextView.setVisibility(8);
                                StickersAlert.this.shadow[0].setVisibility(8);
                                StickersAlert.this.adapter.notifyDataSetChanged();
                                return;
                            }
                        }
                        AlertsCreator.processError(tLRPC$TL_error, StickersAlert.this.parentFragment, tLRPC$TL_messages_getAttachedStickers, new Object[0]);
                        StickersAlert.this.dismiss();
                    }
                });
            }
        });
        init(context);
    }

    public StickersAlert(Context context, BaseFragment baseFragment, InputStickerSet inputStickerSet, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet, StickersAlertDelegate stickersAlertDelegate) {
        super(context, false);
        this.shadowAnimation = new AnimatorSet[2];
        this.shadow = new View[2];
        this.delegate = stickersAlertDelegate;
        this.inputStickerSet = inputStickerSet;
        this.stickerSet = tLRPC$TL_messages_stickerSet;
        this.parentFragment = baseFragment;
        loadStickerSet();
        init(context);
    }

    private void hidePreview() {
        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animatorArr = new Animator[1];
        animatorArr[0] = ObjectAnimator.ofFloat(this.stickerPreviewLayout, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
        animatorSet.playTogether(animatorArr);
        animatorSet.setDuration(200);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                StickersAlert.this.stickerPreviewLayout.setVisibility(8);
            }
        });
        animatorSet.start();
    }

    private void init(Context context) {
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.containerView = new FrameLayout(context) {
            private int lastNotifyWidth;

            protected void onDraw(Canvas canvas) {
                StickersAlert.this.shadowDrawable.setBounds(0, StickersAlert.this.scrollOffsetY - StickersAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                StickersAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || StickersAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) StickersAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                StickersAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                if (this.lastNotifyWidth != i3 - i) {
                    this.lastNotifyWidth = i3 - i;
                    if (!(StickersAlert.this.adapter == null || StickersAlert.this.stickerSetCovereds == null)) {
                        StickersAlert.this.adapter.notifyDataSetChanged();
                    }
                }
                super.onLayout(z, i, i2, i3, i4);
                StickersAlert.this.updateLayout();
            }

            protected void onMeasure(int i, int i2) {
                int dp;
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                getMeasuredWidth();
                StickersAlert.this.itemSize = (MeasureSpec.getSize(i) - AndroidUtilities.dp(36.0f)) / 5;
                if (StickersAlert.this.stickerSetCovereds != null) {
                    dp = (AndroidUtilities.dp(56.0f) + (AndroidUtilities.dp(60.0f) * StickersAlert.this.stickerSetCovereds.size())) + (StickersAlert.this.adapter.stickersRowCount * AndroidUtilities.dp(82.0f));
                } else {
                    dp = ((Math.max(3, StickersAlert.this.stickerSet != null ? (int) Math.ceil((double) (((float) StickersAlert.this.stickerSet.documents.size()) / 5.0f)) : 0) * AndroidUtilities.dp(82.0f)) + AndroidUtilities.dp(96.0f)) + StickersAlert.backgroundPaddingTop;
                }
                int i3 = ((double) dp) < ((double) (size / 5)) * 3.2d ? 0 : (size / 5) * 2;
                if (i3 != 0 && dp < size) {
                    i3 -= size - dp;
                }
                if (i3 == 0) {
                    i3 = StickersAlert.backgroundPaddingTop;
                }
                if (StickersAlert.this.stickerSetCovereds != null) {
                    i3 += AndroidUtilities.dp(8.0f);
                }
                if (StickersAlert.this.gridView.getPaddingTop() != i3) {
                    StickersAlert.this.ignoreLayout = true;
                    StickersAlert.this.gridView.setPadding(AndroidUtilities.dp(10.0f), i3, AndroidUtilities.dp(10.0f), 0);
                    StickersAlert.this.emptyView.setPadding(0, i3, 0, 0);
                    StickersAlert.this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(dp, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !StickersAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!StickersAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.shadow[0] = new View(context);
        this.shadow[0].setBackgroundResource(R.drawable.header_shadow);
        this.shadow[0].setAlpha(BitmapDescriptorFactory.HUE_RED);
        this.shadow[0].setVisibility(4);
        this.shadow[0].setTag(Integer.valueOf(1));
        this.containerView.addView(this.shadow[0], LayoutHelper.createFrame(-1, 3.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.gridView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, StickersAlert.this.gridView, 0, null);
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
        this.layoutManager.setSpanSizeLookup(new C46135());
        recyclerListView = this.gridView;
        Adapter gridAdapter = new GridAdapter(context);
        this.adapter = gridAdapter;
        recyclerListView.setAdapter(gridAdapter);
        this.gridView.setVerticalScrollBarEnabled(false);
        this.gridView.addItemDecoration(new C46146());
        this.gridView.setPadding(AndroidUtilities.dp(10.0f), 0, AndroidUtilities.dp(10.0f), 0);
        this.gridView.setClipToPadding(false);
        this.gridView.setEnabled(true);
        this.gridView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.gridView.setOnTouchListener(new C46157());
        this.gridView.setOnScrollListener(new C46168());
        this.stickersOnItemClickListener = new C46179();
        this.gridView.setOnItemClickListener(this.stickersOnItemClickListener);
        this.containerView.addView(this.gridView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, 48.0f, BitmapDescriptorFactory.HUE_RED, 48.0f));
        this.emptyView = new FrameLayout(context) {
            public void requestLayout() {
                if (!StickersAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        this.gridView.setEmptyView(this.emptyView);
        this.emptyView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
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
        this.containerView.addView(this.shadow[1], LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
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
            public void onClick(View view) {
                StickersAlert.this.hidePreview();
            }
        });
        View imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.msg_panel_clear);
        imageView.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogTextGray3), Mode.MULTIPLY));
        imageView.setScaleType(ScaleType.CENTER);
        this.stickerPreviewLayout.addView(imageView, LayoutHelper.createFrame(48, 48, 53));
        imageView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
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
            public void onClick(View view) {
                StickersAlert.this.delegate.onStickerSelected(StickersAlert.this.selectedSticker);
                StickersAlert.this.dismiss();
            }
        });
        this.previewFavButton = new ImageView(context);
        this.previewFavButton.setScaleType(ScaleType.CENTER);
        this.stickerPreviewLayout.addView(this.previewFavButton, LayoutHelper.createFrame(48, 48.0f, 85, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 4.0f, BitmapDescriptorFactory.HUE_RED));
        this.previewFavButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
        this.previewFavButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
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
        this.stickerPreviewLayout.addView(this.previewSendButtonShadow, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.emojiDidLoaded);
        updateFields();
        updateSendButton();
        this.adapter.notifyDataSetChanged();
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
                TLObject tLRPC$TL_messages_getStickerSet = new TLRPC$TL_messages_getStickerSet();
                tLRPC$TL_messages_getStickerSet.stickerset = this.inputStickerSet;
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_getStickerSet, new C46102());
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

    private void runShadowAnimation(final int i, final boolean z) {
        if (this.stickerSetCovereds == null) {
            if ((z && this.shadow[i].getTag() != null) || (!z && this.shadow[i].getTag() == null)) {
                this.shadow[i].setTag(z ? null : Integer.valueOf(1));
                if (z) {
                    this.shadow[i].setVisibility(0);
                }
                if (this.shadowAnimation[i] != null) {
                    this.shadowAnimation[i].cancel();
                }
                this.shadowAnimation[i] = new AnimatorSet();
                AnimatorSet animatorSet = this.shadowAnimation[i];
                Animator[] animatorArr = new Animator[1];
                Object obj = this.shadow[i];
                String str = "alpha";
                float[] fArr = new float[1];
                fArr[0] = z ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                animatorArr[0] = ObjectAnimator.ofFloat(obj, str, fArr);
                animatorSet.playTogether(animatorArr);
                this.shadowAnimation[i].setDuration(150);
                this.shadowAnimation[i].addListener(new AnimatorListenerAdapter() {
                    public void onAnimationCancel(Animator animator) {
                        if (StickersAlert.this.shadowAnimation[i] != null && StickersAlert.this.shadowAnimation[i].equals(animator)) {
                            StickersAlert.this.shadowAnimation[i] = null;
                        }
                    }

                    public void onAnimationEnd(Animator animator) {
                        if (StickersAlert.this.shadowAnimation[i] != null && StickersAlert.this.shadowAnimation[i].equals(animator)) {
                            if (!z) {
                                StickersAlert.this.shadow[i].setVisibility(4);
                            }
                            StickersAlert.this.shadowAnimation[i] = null;
                        }
                    }
                });
                this.shadowAnimation[i].start();
            }
        }
    }

    private void setRightButton(OnClickListener onClickListener, String str, int i, boolean z) {
        if (str == null) {
            this.pickerBottomLayout.doneButton.setVisibility(8);
            return;
        }
        this.pickerBottomLayout.doneButton.setVisibility(0);
        if (z) {
            this.pickerBottomLayout.doneButtonBadgeTextView.setVisibility(0);
            this.pickerBottomLayout.doneButtonBadgeTextView.setText(String.format("%d", new Object[]{Integer.valueOf(this.stickerSet.documents.size())}));
        } else {
            this.pickerBottomLayout.doneButtonBadgeTextView.setVisibility(8);
        }
        this.pickerBottomLayout.doneButtonTextView.setTextColor(i);
        this.pickerBottomLayout.doneButtonTextView.setText(str.toUpperCase());
        this.pickerBottomLayout.doneButton.setOnClickListener(onClickListener);
    }

    private void updateFields() {
        CharSequence charSequence;
        Throwable th;
        Throwable th2;
        TextView textView;
        OnClickListener anonymousClass18;
        String string;
        if (this.titleTextView != null) {
            if (this.stickerSet != null) {
                try {
                    if (this.urlPattern == null) {
                        this.urlPattern = Pattern.compile("@[a-zA-Z\\d_]{1,32}");
                    }
                    Matcher matcher = this.urlPattern.matcher(this.stickerSet.set.title);
                    charSequence = null;
                    while (matcher.find()) {
                        try {
                            SpannableStringBuilder spannableStringBuilder;
                            if (charSequence == null) {
                                spannableStringBuilder = new SpannableStringBuilder(this.stickerSet.set.title);
                            } else {
                                CharSequence charSequence2 = charSequence;
                            }
                            Object obj;
                            try {
                                int start = matcher.start();
                                int end = matcher.end();
                                if (this.stickerSet.set.title.charAt(start) != '@') {
                                    start++;
                                }
                                AnonymousClass17 anonymousClass17 = new URLSpanNoUnderline(this.stickerSet.set.title.subSequence(start + 1, end).toString()) {
                                    public void onClick(View view) {
                                        MessagesController.openByUserName(getURL(), StickersAlert.this.parentFragment, 1);
                                        StickersAlert.this.dismiss();
                                    }
                                };
                                if (anonymousClass17 != null) {
                                    spannableStringBuilder.setSpan(anonymousClass17, start, end, 0);
                                }
                                obj = spannableStringBuilder;
                            } catch (Throwable e) {
                                th = e;
                                obj = spannableStringBuilder;
                                th2 = th;
                            }
                        } catch (Exception e2) {
                            th2 = e2;
                        }
                    }
                } catch (Throwable e3) {
                    th = e3;
                    charSequence = null;
                    th2 = th;
                    FileLog.e(th2);
                    textView = this.titleTextView;
                    if (charSequence == null) {
                        charSequence = this.stickerSet.set.title;
                    }
                    textView.setText(charSequence);
                    if (this.stickerSet.set != null) {
                    }
                    anonymousClass18 = new OnClickListener() {

                        /* renamed from: org.telegram.ui.Components.StickersAlert$18$1 */
                        class C46071 implements RequestDelegate {
                            C46071() {
                            }

                            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                                AndroidUtilities.runOnUIThread(new Runnable() {
                                    public void run() {
                                        try {
                                            if (tLRPC$TL_error == null) {
                                                if (StickersAlert.this.stickerSet.set.masks) {
                                                    Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("AddMasksInstalled", R.string.AddMasksInstalled), 0).show();
                                                } else {
                                                    Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("AddStickersInstalled", R.string.AddStickersInstalled), 0).show();
                                                }
                                                if (tLObject instanceof TLRPC$TL_messages_stickerSetInstallResultArchive) {
                                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadArchivedStickers, new Object[0]);
                                                    if (!(StickersAlert.this.parentFragment == null || StickersAlert.this.parentFragment.getParentActivity() == null)) {
                                                        StickersAlert.this.parentFragment.showDialog(new StickersArchiveAlert(StickersAlert.this.parentFragment.getParentActivity(), StickersAlert.this.parentFragment, ((TLRPC$TL_messages_stickerSetInstallResultArchive) tLObject).sets).create());
                                                    }
                                                }
                                                StickersQuery.loadStickers(StickersAlert.this.stickerSet.set.masks ? 1 : 0, false, true);
                                            }
                                            Toast.makeText(StickersAlert.this.getContext(), LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred), 0).show();
                                            if (StickersAlert.this.stickerSet.set.masks) {
                                            }
                                            StickersQuery.loadStickers(StickersAlert.this.stickerSet.set.masks ? 1 : 0, false, true);
                                        } catch (Throwable e) {
                                            FileLog.e(e);
                                        }
                                    }
                                });
                            }
                        }

                        public void onClick(View view) {
                            StickersAlert.this.dismiss();
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetInstalled();
                            }
                            TLObject tLRPC$TL_messages_installStickerSet = new TLRPC$TL_messages_installStickerSet();
                            tLRPC$TL_messages_installStickerSet.stickerset = StickersAlert.this.inputStickerSet;
                            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_installStickerSet, new C46071());
                        }
                    };
                    if (this.stickerSet == null) {
                    }
                    setRightButton(anonymousClass18, string, Theme.getColor(Theme.key_dialogTextBlue2), true);
                    this.adapter.notifyDataSetChanged();
                    return;
                }
                textView = this.titleTextView;
                if (charSequence == null) {
                    charSequence = this.stickerSet.set.title;
                }
                textView.setText(charSequence);
                if (this.stickerSet.set != null || !StickersQuery.isStickerPackInstalled(this.stickerSet.set.id)) {
                    anonymousClass18 = /* anonymous class already generated */;
                    string = (this.stickerSet == null && this.stickerSet.set.masks) ? LocaleController.getString("AddMasks", R.string.AddMasks) : LocaleController.getString("AddStickers", R.string.AddStickers);
                    setRightButton(anonymousClass18, string, Theme.getColor(Theme.key_dialogTextBlue2), true);
                } else if (this.stickerSet.set.official) {
                    setRightButton(new OnClickListener() {
                        public void onClick(View view) {
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetUninstalled();
                            }
                            StickersAlert.this.dismiss();
                            StickersQuery.removeStickersSet(StickersAlert.this.getContext(), StickersAlert.this.stickerSet.set, 1, StickersAlert.this.parentFragment, true);
                        }
                    }, LocaleController.getString("StickersRemove", R.string.StickersHide), Theme.getColor(Theme.key_dialogTextRed), false);
                } else {
                    setRightButton(new OnClickListener() {
                        public void onClick(View view) {
                            if (StickersAlert.this.installDelegate != null) {
                                StickersAlert.this.installDelegate.onStickerSetUninstalled();
                            }
                            StickersAlert.this.dismiss();
                            StickersQuery.removeStickersSet(StickersAlert.this.getContext(), StickersAlert.this.stickerSet.set, 0, StickersAlert.this.parentFragment, true);
                        }
                    }, LocaleController.getString("StickersRemove", R.string.StickersRemove), Theme.getColor(Theme.key_dialogTextRed), false);
                }
                this.adapter.notifyDataSetChanged();
                return;
            }
            setRightButton(null, null, Theme.getColor(Theme.key_dialogTextRed), false);
        }
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
        int i;
        View childAt = this.gridView.getChildAt(0);
        Holder holder = (Holder) this.gridView.findContainingViewHolder(childAt);
        paddingTop = childAt.getTop();
        if (paddingTop < 0 || holder == null || holder.getAdapterPosition() != 0) {
            runShadowAnimation(0, true);
            i = 0;
        } else {
            runShadowAnimation(0, false);
            i = paddingTop;
        }
        if (this.scrollOffsetY != i) {
            RecyclerListView recyclerListView2 = this.gridView;
            this.scrollOffsetY = i;
            recyclerListView2.setTopGlowOffset(i);
            if (this.stickerSetCovereds == null) {
                this.titleTextView.setTranslationY((float) this.scrollOffsetY);
                this.shadow[0].setTranslationY((float) this.scrollOffsetY);
            }
            this.containerView.invalidate();
        }
    }

    private void updateSendButton() {
        int min = (int) (((float) (Math.min(AndroidUtilities.displaySize.x, AndroidUtilities.displaySize.y) / 2)) / AndroidUtilities.density);
        if (this.delegate == null || (this.stickerSet != null && this.stickerSet.set.masks)) {
            this.previewSendButton.setText(LocaleController.getString("Close", R.string.Close).toUpperCase());
            this.stickerImageView.setLayoutParams(LayoutHelper.createFrame(min, min, 17));
            this.stickerEmojiTextView.setLayoutParams(LayoutHelper.createFrame(min, min, 17));
            this.previewSendButton.setVisibility(8);
            this.previewFavButton.setVisibility(8);
            this.previewSendButtonShadow.setVisibility(8);
            return;
        }
        this.previewSendButton.setText(LocaleController.getString("SendSticker", R.string.SendSticker).toUpperCase());
        this.stickerImageView.setLayoutParams(LayoutHelper.createFrame(min, (float) min, 17, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 30.0f));
        this.stickerEmojiTextView.setLayoutParams(LayoutHelper.createFrame(min, (float) min, 17, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 30.0f));
        this.previewSendButton.setVisibility(0);
        this.previewFavButton.setVisibility(0);
        this.previewSendButtonShadow.setVisibility(0);
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.emojiDidLoaded) {
            if (this.gridView != null) {
                int childCount = this.gridView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    this.gridView.getChildAt(i2).invalidate();
                }
            }
            if (StickerPreviewViewer.getInstance().isVisible()) {
                StickerPreviewViewer.getInstance().close();
            }
            StickerPreviewViewer.getInstance().reset();
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

    public void setInstallDelegate(StickersAlertInstallDelegate stickersAlertInstallDelegate) {
        this.installDelegate = stickersAlertInstallDelegate;
    }
}
