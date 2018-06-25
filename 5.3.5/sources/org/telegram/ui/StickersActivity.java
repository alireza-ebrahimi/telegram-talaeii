package org.telegram.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;
import org.ir.talaeii.R;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$StickerSet;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_reorderStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.StickerSetCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.StickersAlert;
import org.telegram.ui.Components.URLSpanNoUnderline;

public class StickersActivity extends BaseFragment implements NotificationCenterDelegate {
    private int archivedInfoRow;
    private int archivedRow;
    private int currentType;
    private int featuredInfoRow;
    private int featuredRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int masksInfoRow;
    private int masksRow;
    private boolean needReorder;
    private int rowCount;
    private int stickersEndRow;
    private int stickersShadowRow;
    private int stickersStartRow;

    /* renamed from: org.telegram.ui.StickersActivity$1 */
    class C33781 extends ActionBarMenuOnItemClick {
        C33781() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                StickersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.StickersActivity$2 */
    class C33792 implements OnItemClickListener {
        C33792() {
        }

        public void onItemClick(View view, int position) {
            if (position >= StickersActivity.this.stickersStartRow && position < StickersActivity.this.stickersEndRow && StickersActivity.this.getParentActivity() != null) {
                StickersActivity.this.sendReorder();
                TLRPC$TL_messages_stickerSet stickerSet = (TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(StickersActivity.this.currentType).get(position - StickersActivity.this.stickersStartRow);
                ArrayList<TLRPC$Document> stickers = stickerSet.documents;
                if (stickers != null && !stickers.isEmpty()) {
                    StickersActivity.this.showDialog(new StickersAlert(StickersActivity.this.getParentActivity(), StickersActivity.this, null, stickerSet, null));
                }
            } else if (position == StickersActivity.this.featuredRow) {
                StickersActivity.this.sendReorder();
                StickersActivity.this.presentFragment(new FeaturedStickersActivity());
            } else if (position == StickersActivity.this.archivedRow) {
                StickersActivity.this.sendReorder();
                StickersActivity.this.presentFragment(new ArchivedStickersActivity(StickersActivity.this.currentType));
            } else if (position == StickersActivity.this.masksRow) {
                StickersActivity.this.presentFragment(new StickersActivity(1));
            }
        }
    }

    /* renamed from: org.telegram.ui.StickersActivity$3 */
    class C33803 implements RequestDelegate {
        C33803() {
        }

        public void run(TLObject response, TLRPC$TL_error error) {
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.StickersActivity$ListAdapter$2 */
        class C33832 implements OnClickListener {
            C33832() {
            }

            public void onClick(View v) {
                int[] options;
                CharSequence[] items;
                StickersActivity.this.sendReorder();
                final TLRPC$TL_messages_stickerSet stickerSet = ((StickerSetCell) v.getParent()).getStickersSet();
                Builder builder = new Builder(StickersActivity.this.getParentActivity());
                builder.setTitle(stickerSet.set.title);
                if (StickersActivity.this.currentType == 0) {
                    if (stickerSet.set.official) {
                        options = new int[]{0};
                        items = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide)};
                    } else {
                        options = new int[]{0, 1, 2, 3};
                        items = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide), LocaleController.getString("StickersRemove", R.string.StickersRemove), LocaleController.getString("StickersShare", R.string.StickersShare), LocaleController.getString("StickersCopy", R.string.StickersCopy)};
                    }
                } else if (stickerSet.set.official) {
                    options = new int[]{0};
                    items = new CharSequence[]{LocaleController.getString("StickersRemove", R.string.StickersHide)};
                } else {
                    options = new int[]{0, 1, 2, 3};
                    items = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide), LocaleController.getString("StickersRemove", R.string.StickersRemove), LocaleController.getString("StickersShare", R.string.StickersShare), LocaleController.getString("StickersCopy", R.string.StickersCopy)};
                }
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ListAdapter.this.processSelectionOption(options[which], stickerSet);
                    }
                });
                StickersActivity.this.showDialog(builder.create());
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return StickersActivity.this.rowCount;
        }

        public long getItemId(int i) {
            if (i >= StickersActivity.this.stickersStartRow && i < StickersActivity.this.stickersEndRow) {
                return ((TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(StickersActivity.this.currentType).get(i - StickersActivity.this.stickersStartRow)).set.id;
            }
            if (i == StickersActivity.this.archivedRow || i == StickersActivity.this.archivedInfoRow || i == StickersActivity.this.featuredRow || i == StickersActivity.this.featuredInfoRow || i == StickersActivity.this.masksRow || i == StickersActivity.this.masksInfoRow) {
                return -2147483648L;
            }
            return (long) i;
        }

        private void processSelectionOption(int which, TLRPC$TL_messages_stickerSet stickerSet) {
            int i = 2;
            if (which == 0) {
                Context parentActivity = StickersActivity.this.getParentActivity();
                TLRPC$StickerSet tLRPC$StickerSet = stickerSet.set;
                if (!stickerSet.set.archived) {
                    i = 1;
                }
                StickersQuery.removeStickersSet(parentActivity, tLRPC$StickerSet, i, StickersActivity.this, true);
            } else if (which == 1) {
                StickersQuery.removeStickersSet(StickersActivity.this.getParentActivity(), stickerSet.set, 0, StickersActivity.this, true);
            } else if (which == 2) {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.TEXT", String.format(Locale.US, "https://" + MessagesController.getInstance().linkPrefix + "/addstickers/%s", new Object[]{stickerSet.set.short_name}));
                    StickersActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("StickersShare", R.string.StickersShare)), 500);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else if (which == 3) {
                try {
                    ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", String.format(Locale.US, "https://" + MessagesController.getInstance().linkPrefix + "/addstickers/%s", new Object[]{stickerSet.set.short_name})));
                    Toast.makeText(StickersActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    ArrayList<TLRPC$TL_messages_stickerSet> arrayList = StickersQuery.getStickerSets(StickersActivity.this.currentType);
                    int row = position - StickersActivity.this.stickersStartRow;
                    ((StickerSetCell) holder.itemView).setStickersSet((TLRPC$TL_messages_stickerSet) arrayList.get(row), row != arrayList.size() + -1);
                    return;
                case 1:
                    if (position == StickersActivity.this.featuredInfoRow) {
                        String text = LocaleController.getString("FeaturedStickersInfo", R.string.FeaturedStickersInfo);
                        String botName = "@stickers";
                        int index = text.indexOf(botName);
                        if (index != -1) {
                            try {
                                SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
                                stringBuilder.setSpan(new URLSpanNoUnderline("@stickers") {
                                    public void onClick(View widget) {
                                        MessagesController.openByUserName("stickers", StickersActivity.this, 1);
                                    }
                                }, index, botName.length() + index, 18);
                                ((TextInfoPrivacyCell) holder.itemView).setText(stringBuilder);
                                return;
                            } catch (Exception e) {
                                FileLog.e(e);
                                ((TextInfoPrivacyCell) holder.itemView).setText(text);
                                return;
                            }
                        }
                        ((TextInfoPrivacyCell) holder.itemView).setText(text);
                        return;
                    } else if (position == StickersActivity.this.archivedInfoRow) {
                        if (StickersActivity.this.currentType == 0) {
                            ((TextInfoPrivacyCell) holder.itemView).setText(LocaleController.getString("ArchivedStickersInfo", R.string.ArchivedStickersInfo));
                            return;
                        } else {
                            ((TextInfoPrivacyCell) holder.itemView).setText(LocaleController.getString("ArchivedMasksInfo", R.string.ArchivedMasksInfo));
                            return;
                        }
                    } else if (position == StickersActivity.this.masksInfoRow) {
                        ((TextInfoPrivacyCell) holder.itemView).setText(LocaleController.getString("MasksInfo", R.string.MasksInfo));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    if (position == StickersActivity.this.featuredRow) {
                        ((TextSettingsCell) holder.itemView).setTextAndValue(LocaleController.getString("FeaturedStickers", R.string.FeaturedStickers), StickersQuery.getUnreadStickerSets().size() != 0 ? String.format("%d", new Object[]{Integer.valueOf(StickersQuery.getUnreadStickerSets().size())}) : "", false);
                        return;
                    } else if (position == StickersActivity.this.archivedRow) {
                        if (StickersActivity.this.currentType == 0) {
                            ((TextSettingsCell) holder.itemView).setText(LocaleController.getString("ArchivedStickers", R.string.ArchivedStickers), false);
                            return;
                        } else {
                            ((TextSettingsCell) holder.itemView).setText(LocaleController.getString("ArchivedMasks", R.string.ArchivedMasks), false);
                            return;
                        }
                    } else if (position == StickersActivity.this.masksRow) {
                        ((TextSettingsCell) holder.itemView).setText(LocaleController.getString("Masks", R.string.Masks), true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            int type = holder.getItemViewType();
            return type == 0 || type == 2;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            switch (viewType) {
                case 0:
                    view = new StickerSetCell(this.mContext, 1);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((StickerSetCell) view).setOnOptionsClick(new C33832());
                    break;
                case 1:
                    view = new TextInfoPrivacyCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 2:
                    view = new TextSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new ShadowSectionCell(this.mContext);
                    view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public int getItemViewType(int i) {
            if (i >= StickersActivity.this.stickersStartRow && i < StickersActivity.this.stickersEndRow) {
                return 0;
            }
            if (i == StickersActivity.this.featuredInfoRow || i == StickersActivity.this.archivedInfoRow || i == StickersActivity.this.masksInfoRow) {
                return 1;
            }
            if (i == StickersActivity.this.featuredRow || i == StickersActivity.this.archivedRow || i == StickersActivity.this.masksRow) {
                return 2;
            }
            if (i == StickersActivity.this.stickersShadowRow) {
                return 3;
            }
            return 0;
        }

        public void swapElements(int fromIndex, int toIndex) {
            if (fromIndex != toIndex) {
                StickersActivity.this.needReorder = true;
            }
            ArrayList<TLRPC$TL_messages_stickerSet> arrayList = StickersQuery.getStickerSets(StickersActivity.this.currentType);
            TLRPC$TL_messages_stickerSet from = (TLRPC$TL_messages_stickerSet) arrayList.get(fromIndex - StickersActivity.this.stickersStartRow);
            arrayList.set(fromIndex - StickersActivity.this.stickersStartRow, arrayList.get(toIndex - StickersActivity.this.stickersStartRow));
            arrayList.set(toIndex - StickersActivity.this.stickersStartRow, from);
            notifyItemMoved(fromIndex, toIndex);
        }
    }

    public class TouchHelperCallback extends Callback {
        public boolean isLongPressDragEnabled() {
            return true;
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            if (viewHolder.getItemViewType() != 0) {
                return Callback.makeMovementFlags(0, 0);
            }
            return Callback.makeMovementFlags(3, 0);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder source, ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }
            StickersActivity.this.listAdapter.swapElements(source.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
            if (actionState != 0) {
                StickersActivity.this.listView.cancelClickRunnables(false);
                viewHolder.itemView.setPressed(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        public void onSwiped(ViewHolder viewHolder, int direction) {
        }

        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setPressed(false);
        }
    }

    public StickersActivity(int type) {
        this.currentType = type;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        StickersQuery.checkStickers(this.currentType);
        if (this.currentType == 0) {
            StickersQuery.checkFeaturedStickers();
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.stickersDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.archivedStickersCountDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.featuredStickersDidLoaded);
        updateRows();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.stickersDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.archivedStickersCountDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.featuredStickersDidLoaded);
        sendReorder();
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("StickersName", R.string.StickersName));
        } else {
            this.actionBar.setTitle(LocaleController.getString("Masks", R.string.Masks));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C33781());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setFocusable(true);
        this.listView.setTag(Integer.valueOf(7));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(1);
        this.listView.setLayoutManager(layoutManager);
        new ItemTouchHelper(new TouchHelperCallback()).attachToRecyclerView(this.listView);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C33792());
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.stickersDidLoaded) {
            if (((Integer) args[0]).intValue() == this.currentType) {
                updateRows();
            }
        } else if (id == NotificationCenter.featuredStickersDidLoaded) {
            if (this.listAdapter != null) {
                this.listAdapter.notifyItemChanged(0);
            }
        } else if (id == NotificationCenter.archivedStickersCountDidLoaded && ((Integer) args[0]).intValue() == this.currentType) {
            updateRows();
        }
    }

    private void sendReorder() {
        if (this.needReorder) {
            boolean z;
            StickersQuery.calcNewHash(this.currentType);
            this.needReorder = false;
            TLRPC$TL_messages_reorderStickerSets req = new TLRPC$TL_messages_reorderStickerSets();
            if (this.currentType == 1) {
                z = true;
            } else {
                z = false;
            }
            req.masks = z;
            ArrayList<TLRPC$TL_messages_stickerSet> arrayList = StickersQuery.getStickerSets(this.currentType);
            for (int a = 0; a < arrayList.size(); a++) {
                req.order.add(Long.valueOf(((TLRPC$TL_messages_stickerSet) arrayList.get(a)).set.id));
            }
            ConnectionsManager.getInstance().sendRequest(req, new C33803());
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.stickersDidLoaded, new Object[]{Integer.valueOf(this.currentType)});
        }
    }

    private void updateRows() {
        this.rowCount = 0;
        if (this.currentType == 0) {
            int i = this.rowCount;
            this.rowCount = i + 1;
            this.featuredRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.featuredInfoRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.masksRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.masksInfoRow = i;
        } else {
            this.featuredRow = -1;
            this.featuredInfoRow = -1;
            this.masksRow = -1;
            this.masksInfoRow = -1;
        }
        if (StickersQuery.getArchivedStickersCount(this.currentType) != 0) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.archivedRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.archivedInfoRow = i;
        } else {
            this.archivedRow = -1;
            this.archivedInfoRow = -1;
        }
        ArrayList<TLRPC$TL_messages_stickerSet> stickerSets = StickersQuery.getStickerSets(this.currentType);
        if (stickerSets.isEmpty()) {
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + stickerSets.size();
            this.rowCount += stickerSets.size();
            i = this.rowCount;
            this.rowCount = i + 1;
            this.stickersShadowRow = i;
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[19];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{StickerSetCell.class, TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_USEBACKGROUNDDRAWABLE | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, null, null, null, Theme.key_stickers_menuSelector);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{StickerSetCell.class}, new String[]{"optionsButton"}, null, null, null, Theme.key_stickers_menu);
        return themeDescriptionArr;
    }
}
