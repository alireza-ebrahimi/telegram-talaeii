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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messages_reorderStickerSets;
import org.telegram.tgnet.TLRPC$TL_messages_stickerSet;
import org.telegram.tgnet.TLRPC.StickerSet;
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
    class C52171 extends ActionBarMenuOnItemClick {
        C52171() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                StickersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.StickersActivity$2 */
    class C52182 implements OnItemClickListener {
        C52182() {
        }

        public void onItemClick(View view, int i) {
            if (i >= StickersActivity.this.stickersStartRow && i < StickersActivity.this.stickersEndRow && StickersActivity.this.getParentActivity() != null) {
                StickersActivity.this.sendReorder();
                TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(StickersActivity.this.currentType).get(i - StickersActivity.this.stickersStartRow);
                ArrayList arrayList = tLRPC$TL_messages_stickerSet.documents;
                if (arrayList != null && !arrayList.isEmpty()) {
                    StickersActivity.this.showDialog(new StickersAlert(StickersActivity.this.getParentActivity(), StickersActivity.this, null, tLRPC$TL_messages_stickerSet, null));
                }
            } else if (i == StickersActivity.this.featuredRow) {
                StickersActivity.this.sendReorder();
                StickersActivity.this.presentFragment(new FeaturedStickersActivity());
            } else if (i == StickersActivity.this.archivedRow) {
                StickersActivity.this.sendReorder();
                StickersActivity.this.presentFragment(new ArchivedStickersActivity(StickersActivity.this.currentType));
            } else if (i == StickersActivity.this.masksRow) {
                StickersActivity.this.presentFragment(new StickersActivity(1));
            }
        }
    }

    /* renamed from: org.telegram.ui.StickersActivity$3 */
    class C52193 implements RequestDelegate {
        C52193() {
        }

        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.StickersActivity$ListAdapter$2 */
        class C52222 implements OnClickListener {
            C52222() {
            }

            public void onClick(View view) {
                int[] iArr;
                CharSequence[] charSequenceArr;
                StickersActivity.this.sendReorder();
                final TLRPC$TL_messages_stickerSet stickersSet = ((StickerSetCell) view.getParent()).getStickersSet();
                Builder builder = new Builder(StickersActivity.this.getParentActivity());
                builder.setTitle(stickersSet.set.title);
                if (StickersActivity.this.currentType == 0) {
                    if (stickersSet.set.official) {
                        iArr = new int[]{0};
                        charSequenceArr = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide)};
                    } else {
                        iArr = new int[]{0, 1, 2, 3};
                        charSequenceArr = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide), LocaleController.getString("StickersRemove", R.string.StickersRemove), LocaleController.getString("StickersShare", R.string.StickersShare), LocaleController.getString("StickersCopy", R.string.StickersCopy)};
                    }
                } else if (stickersSet.set.official) {
                    iArr = new int[]{0};
                    charSequenceArr = new CharSequence[]{LocaleController.getString("StickersRemove", R.string.StickersHide)};
                } else {
                    iArr = new int[]{0, 1, 2, 3};
                    charSequenceArr = new CharSequence[]{LocaleController.getString("StickersHide", R.string.StickersHide), LocaleController.getString("StickersRemove", R.string.StickersRemove), LocaleController.getString("StickersShare", R.string.StickersShare), LocaleController.getString("StickersCopy", R.string.StickersCopy)};
                }
                builder.setItems(charSequenceArr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListAdapter.this.processSelectionOption(iArr[i], stickersSet);
                    }
                });
                StickersActivity.this.showDialog(builder.create());
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        private void processSelectionOption(int i, TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet) {
            int i2 = 2;
            if (i == 0) {
                Context parentActivity = StickersActivity.this.getParentActivity();
                StickerSet stickerSet = tLRPC$TL_messages_stickerSet.set;
                if (!tLRPC$TL_messages_stickerSet.set.archived) {
                    i2 = 1;
                }
                StickersQuery.removeStickersSet(parentActivity, stickerSet, i2, StickersActivity.this, true);
            } else if (i == 1) {
                StickersQuery.removeStickersSet(StickersActivity.this.getParentActivity(), tLRPC$TL_messages_stickerSet.set, 0, StickersActivity.this, true);
            } else if (i == 2) {
                try {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.TEXT", String.format(Locale.US, "https://" + MessagesController.getInstance().linkPrefix + "/addstickers/%s", new Object[]{tLRPC$TL_messages_stickerSet.set.short_name}));
                    StickersActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("StickersShare", R.string.StickersShare)), ChatActivity.startAllServices);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            } else if (i == 3) {
                try {
                    ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", String.format(Locale.US, "https://" + MessagesController.getInstance().linkPrefix + "/addstickers/%s", new Object[]{tLRPC$TL_messages_stickerSet.set.short_name})));
                    Toast.makeText(StickersActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
            }
        }

        public int getItemCount() {
            return StickersActivity.this.rowCount;
        }

        public long getItemId(int i) {
            return (i < StickersActivity.this.stickersStartRow || i >= StickersActivity.this.stickersEndRow) ? (i == StickersActivity.this.archivedRow || i == StickersActivity.this.archivedInfoRow || i == StickersActivity.this.featuredRow || i == StickersActivity.this.featuredInfoRow || i == StickersActivity.this.masksRow || i == StickersActivity.this.masksInfoRow) ? -2147483648L : (long) i : ((TLRPC$TL_messages_stickerSet) StickersQuery.getStickerSets(StickersActivity.this.currentType).get(i - StickersActivity.this.stickersStartRow)).set.id;
        }

        public int getItemViewType(int i) {
            return (i < StickersActivity.this.stickersStartRow || i >= StickersActivity.this.stickersEndRow) ? (i == StickersActivity.this.featuredInfoRow || i == StickersActivity.this.archivedInfoRow || i == StickersActivity.this.masksInfoRow) ? 1 : (i == StickersActivity.this.featuredRow || i == StickersActivity.this.archivedRow || i == StickersActivity.this.masksRow) ? 2 : i == StickersActivity.this.stickersShadowRow ? 3 : 0 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 2;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ArrayList stickerSets = StickersQuery.getStickerSets(StickersActivity.this.currentType);
                    int access$200 = i - StickersActivity.this.stickersStartRow;
                    StickerSetCell stickerSetCell = (StickerSetCell) viewHolder.itemView;
                    TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets.get(access$200);
                    if (access$200 == stickerSets.size() - 1) {
                        z = false;
                    }
                    stickerSetCell.setStickersSet(tLRPC$TL_messages_stickerSet, z);
                    return;
                case 1:
                    if (i == StickersActivity.this.featuredInfoRow) {
                        CharSequence string = LocaleController.getString("FeaturedStickersInfo", R.string.FeaturedStickersInfo);
                        String str = "@stickers";
                        int indexOf = string.indexOf(str);
                        if (indexOf != -1) {
                            try {
                                CharSequence spannableStringBuilder = new SpannableStringBuilder(string);
                                spannableStringBuilder.setSpan(new URLSpanNoUnderline("@stickers") {
                                    public void onClick(View view) {
                                        MessagesController.openByUserName("stickers", StickersActivity.this, 1);
                                    }
                                }, indexOf, str.length() + indexOf, 18);
                                ((TextInfoPrivacyCell) viewHolder.itemView).setText(spannableStringBuilder);
                                return;
                            } catch (Throwable e) {
                                FileLog.e(e);
                                ((TextInfoPrivacyCell) viewHolder.itemView).setText(string);
                                return;
                            }
                        }
                        ((TextInfoPrivacyCell) viewHolder.itemView).setText(string);
                        return;
                    } else if (i == StickersActivity.this.archivedInfoRow) {
                        if (StickersActivity.this.currentType == 0) {
                            ((TextInfoPrivacyCell) viewHolder.itemView).setText(LocaleController.getString("ArchivedStickersInfo", R.string.ArchivedStickersInfo));
                            return;
                        } else {
                            ((TextInfoPrivacyCell) viewHolder.itemView).setText(LocaleController.getString("ArchivedMasksInfo", R.string.ArchivedMasksInfo));
                            return;
                        }
                    } else if (i == StickersActivity.this.masksInfoRow) {
                        ((TextInfoPrivacyCell) viewHolder.itemView).setText(LocaleController.getString("MasksInfo", R.string.MasksInfo));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    if (i == StickersActivity.this.featuredRow) {
                        String format;
                        int size = StickersQuery.getUnreadStickerSets().size();
                        TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                        String string2 = LocaleController.getString("FeaturedStickers", R.string.FeaturedStickers);
                        if (size != 0) {
                            format = String.format("%d", new Object[]{Integer.valueOf(size)});
                        } else {
                            format = TtmlNode.ANONYMOUS_REGION_ID;
                        }
                        textSettingsCell.setTextAndValue(string2, format, false);
                        return;
                    } else if (i == StickersActivity.this.archivedRow) {
                        if (StickersActivity.this.currentType == 0) {
                            ((TextSettingsCell) viewHolder.itemView).setText(LocaleController.getString("ArchivedStickers", R.string.ArchivedStickers), false);
                            return;
                        } else {
                            ((TextSettingsCell) viewHolder.itemView).setText(LocaleController.getString("ArchivedMasks", R.string.ArchivedMasks), false);
                            return;
                        }
                    } else if (i == StickersActivity.this.masksRow) {
                        ((TextSettingsCell) viewHolder.itemView).setText(LocaleController.getString("Masks", R.string.Masks), true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new StickerSetCell(this.mContext, 1);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((StickerSetCell) view).setOnOptionsClick(new C52222());
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

        public void swapElements(int i, int i2) {
            if (i != i2) {
                StickersActivity.this.needReorder = true;
            }
            ArrayList stickerSets = StickersQuery.getStickerSets(StickersActivity.this.currentType);
            TLRPC$TL_messages_stickerSet tLRPC$TL_messages_stickerSet = (TLRPC$TL_messages_stickerSet) stickerSets.get(i - StickersActivity.this.stickersStartRow);
            stickerSets.set(i - StickersActivity.this.stickersStartRow, stickerSets.get(i2 - StickersActivity.this.stickersStartRow));
            stickerSets.set(i2 - StickersActivity.this.stickersStartRow, tLRPC$TL_messages_stickerSet);
            notifyItemMoved(i, i2);
        }
    }

    public class TouchHelperCallback extends Callback {
        public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setPressed(false);
        }

        public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 0 ? makeMovementFlags(0, 0) : makeMovementFlags(3, 0);
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            super.onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, z);
        }

        public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder viewHolder2) {
            if (viewHolder.getItemViewType() != viewHolder2.getItemViewType()) {
                return false;
            }
            StickersActivity.this.listAdapter.swapElements(viewHolder.getAdapterPosition(), viewHolder2.getAdapterPosition());
            return true;
        }

        public void onSelectedChanged(ViewHolder viewHolder, int i) {
            if (i != 0) {
                StickersActivity.this.listView.cancelClickRunnables(false);
                viewHolder.itemView.setPressed(true);
            }
            super.onSelectedChanged(viewHolder, i);
        }

        public void onSwiped(ViewHolder viewHolder, int i) {
        }
    }

    public StickersActivity(int i) {
        this.currentType = i;
    }

    private void sendReorder() {
        if (this.needReorder) {
            StickersQuery.calcNewHash(this.currentType);
            this.needReorder = false;
            TLObject tLRPC$TL_messages_reorderStickerSets = new TLRPC$TL_messages_reorderStickerSets();
            tLRPC$TL_messages_reorderStickerSets.masks = this.currentType == 1;
            ArrayList stickerSets = StickersQuery.getStickerSets(this.currentType);
            for (int i = 0; i < stickerSets.size(); i++) {
                tLRPC$TL_messages_reorderStickerSets.order.add(Long.valueOf(((TLRPC$TL_messages_stickerSet) stickerSets.get(i)).set.id));
            }
            ConnectionsManager.getInstance().sendRequest(tLRPC$TL_messages_reorderStickerSets, new C52193());
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
        ArrayList stickerSets = StickersQuery.getStickerSets(this.currentType);
        if (stickerSets.isEmpty()) {
            this.stickersStartRow = -1;
            this.stickersEndRow = -1;
            this.stickersShadowRow = -1;
        } else {
            this.stickersStartRow = this.rowCount;
            this.stickersEndRow = this.rowCount + stickerSets.size();
            this.rowCount = stickerSets.size() + this.rowCount;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.stickersShadowRow = i;
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("StickersName", R.string.StickersName));
        } else {
            this.actionBar.setTitle(LocaleController.getString("Masks", R.string.Masks));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C52171());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setFocusable(true);
        this.listView.setTag(Integer.valueOf(7));
        LayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(1);
        this.listView.setLayoutManager(linearLayoutManager);
        new ItemTouchHelper(new TouchHelperCallback()).attachToRecyclerView(this.listView);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C52182());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.stickersDidLoaded) {
            if (((Integer) objArr[0]).intValue() == this.currentType) {
                updateRows();
            }
        } else if (i == NotificationCenter.featuredStickersDidLoaded) {
            if (this.listAdapter != null) {
                this.listAdapter.notifyItemChanged(0);
            }
        } else if (i == NotificationCenter.archivedStickersCountDidLoaded && ((Integer) objArr[0]).intValue() == this.currentType) {
            updateRows();
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

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
