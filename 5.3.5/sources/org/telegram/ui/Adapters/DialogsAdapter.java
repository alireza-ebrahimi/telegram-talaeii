package org.telegram.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$RecentMeUrl;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.DialogCell;
import org.telegram.ui.Cells.DialogMeUrlCell;
import org.telegram.ui.Cells.DialogsEmptyCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class DialogsAdapter extends SelectionAdapter {
    private int currentCount;
    private int dialogsType;
    private boolean hasHints;
    private boolean isOnlySelect;
    private Context mContext;
    private long openedDialogId;
    private ArrayList<Long> selectedDialogs;

    /* renamed from: org.telegram.ui.Adapters.DialogsAdapter$1 */
    class C20121 implements OnClickListener {
        C20121() {
        }

        public void onClick(View view) {
            MessagesController.getInstance().hintDialogs.clear();
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("installReferer").commit();
            DialogsAdapter.this.notifyDataSetChanged();
        }
    }

    public DialogsAdapter(Context context, int type) {
        this.mContext = context;
        this.dialogsType = type;
    }

    public DialogsAdapter(Context context, int type, boolean onlySelect) {
        this.mContext = context;
        this.dialogsType = type;
        this.isOnlySelect = onlySelect;
        boolean z = type == 0 && !onlySelect;
        this.hasHints = z;
        if (onlySelect) {
            this.selectedDialogs = new ArrayList();
        }
    }

    public void setOpenedDialogId(long id) {
        this.openedDialogId = id;
    }

    public boolean hasSelectedDialogs() {
        return (this.selectedDialogs == null || this.selectedDialogs.isEmpty()) ? false : true;
    }

    public void addOrRemoveSelectedDialog(long did, View cell) {
        try {
            if (this.selectedDialogs.contains(Long.valueOf(did))) {
                this.selectedDialogs.remove(Long.valueOf(did));
                if (cell instanceof DialogCell) {
                    ((DialogCell) cell).setChecked(false, true);
                    return;
                }
                return;
            }
            this.selectedDialogs.add(Long.valueOf(did));
            if (cell instanceof DialogCell) {
                ((DialogCell) cell).setChecked(true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Long> getSelectedDialogs() {
        return this.selectedDialogs;
    }

    public boolean isDataSetChanged() {
        int current = this.currentCount;
        if (current != getItemCount() || current == 1) {
            return true;
        }
        return false;
    }

    private ArrayList<TLRPC$TL_dialog> getDialogsArray() {
        if (this.dialogsType == 0) {
            return MessagesController.getInstance().dialogsAll;
        }
        if (this.dialogsType == 1) {
            return MessagesController.getInstance().dialogsServerOnly;
        }
        if (this.dialogsType == 2) {
            return MessagesController.getInstance().dialogsGroupsOnly;
        }
        if (this.dialogsType == 13) {
            return MessagesController.getInstance().dialogsForward;
        }
        if (this.dialogsType == 3) {
            return MessagesController.getInstance().dialogsUsers;
        }
        if (this.dialogsType == 4) {
            return MessagesController.getInstance().dialogsGroups;
        }
        if (this.dialogsType == 5) {
            return MessagesController.getInstance().dialogsChannels;
        }
        if (this.dialogsType == 6) {
            return MessagesController.getInstance().dialogsBots;
        }
        if (this.dialogsType == 7) {
            return MessagesController.getInstance().dialogsMegaGroups;
        }
        if (this.dialogsType == 8) {
            return MessagesController.getInstance().dialogsFavs;
        }
        if (this.dialogsType == 9) {
            return MessagesController.getInstance().dialogsGroupsAll;
        }
        if (this.dialogsType == 10) {
            return MessagesController.getInstance().dialogsHidden;
        }
        if (this.dialogsType == 11) {
            return MessagesController.getInstance().dialogsUnread;
        }
        if (this.dialogsType == 12) {
            return MessagesController.getInstance().dialogsAds;
        }
        return null;
    }

    public int getItemCount() {
        int count = getDialogsArray().size();
        if (count == 0 && MessagesController.getInstance().loadingDialogs) {
            return 0;
        }
        if (!MessagesController.getInstance().dialogsEndReached || count == 0) {
            count++;
        }
        if (this.hasHints) {
            count += MessagesController.getInstance().hintDialogs.size() + 2;
        }
        this.currentCount = count;
        return count;
    }

    public TLObject getItem(int i) {
        ArrayList<TLRPC$TL_dialog> arrayList = getDialogsArray();
        if (this.hasHints) {
            int count = MessagesController.getInstance().hintDialogs.size();
            if (i < count + 2) {
                return (TLObject) MessagesController.getInstance().hintDialogs.get(i - 1);
            }
            i -= count + 2;
        }
        if (i < 0 || i >= arrayList.size()) {
            return null;
        }
        return (TLObject) arrayList.get(i);
    }

    public void notifyDataSetChanged() {
        boolean z = (this.dialogsType != 0 || this.isOnlySelect || MessagesController.getInstance().hintDialogs.isEmpty()) ? false : true;
        this.hasHints = z;
        super.notifyDataSetChanged();
    }

    public void onViewAttachedToWindow(ViewHolder holder) {
        if (holder.itemView instanceof DialogCell) {
            ((DialogCell) holder.itemView).checkCurrentDialogIndex();
        }
    }

    public boolean isEnabled(ViewHolder holder) {
        int viewType = holder.getItemViewType();
        if (viewType == 1 || viewType == 5 || viewType == 3) {
            return false;
        }
        return true;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = new DialogCell(this.mContext, this.isOnlySelect);
                break;
            case 1:
                view = new LoadingCell(this.mContext);
                break;
            case 2:
                int i;
                View headerCell = new HeaderCell(this.mContext);
                headerCell.setText(LocaleController.getString("RecentlyViewed", R.string.RecentlyViewed));
                TextView textView = new TextView(this.mContext);
                textView.setTextSize(1, 15.0f);
                textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader));
                textView.setText(LocaleController.getString("RecentlyViewedHide", R.string.RecentlyViewedHide));
                if (LocaleController.isRTL) {
                    i = 3;
                } else {
                    i = 5;
                }
                textView.setGravity(i | 16);
                if (LocaleController.isRTL) {
                    i = 3;
                } else {
                    i = 5;
                }
                headerCell.addView(textView, LayoutHelper.createFrame(-1, -1.0f, i | 48, 17.0f, 15.0f, 17.0f, 0.0f));
                textView.setOnClickListener(new C20121());
                view = headerCell;
                break;
            case 3:
                View frameLayout = new FrameLayout(this.mContext) {
                    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
                        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(12.0f), 1073741824));
                    }
                };
                frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
                View v = new View(this.mContext);
                v.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                frameLayout.addView(v, LayoutHelper.createFrame(-1, -1.0f));
                view = frameLayout;
                break;
            case 4:
                view = new DialogMeUrlCell(this.mContext);
                break;
            default:
                view = new DialogsEmptyCell(this.mContext);
                break;
        }
        view.setLayoutParams(new LayoutParams(-1, viewType == 5 ? -1 : -2));
        return new Holder(view);
    }

    public void onBindViewHolder(ViewHolder holder, int i) {
        boolean z = true;
        switch (holder.getItemViewType()) {
            case 0:
                DialogCell cell = holder.itemView;
                TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) getItem(i);
                if (this.hasHints) {
                    i -= MessagesController.getInstance().hintDialogs.size() + 2;
                }
                cell.useSeparator = i != getItemCount() + -1;
                if (dialog.isSelected()) {
                    cell.setDialogSelected(true);
                } else {
                    cell.setDialogSelected(false);
                }
                if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                    if (dialog.id != this.openedDialogId) {
                        z = false;
                    }
                    cell.setDialogSelected(z);
                }
                if (this.selectedDialogs != null) {
                    cell.setChecked(this.selectedDialogs.contains(Long.valueOf(dialog.id)), false);
                }
                cell.setDialog(dialog, i, this.dialogsType);
                return;
            case 4:
                holder.itemView.setRecentMeUrl((TLRPC$RecentMeUrl) getItem(i));
                return;
            default:
                return;
        }
    }

    public int getItemViewType(int i) {
        if (this.hasHints) {
            int count = MessagesController.getInstance().hintDialogs.size();
            if (i >= count + 2) {
                i -= count + 2;
            } else if (i == 0) {
                return 2;
            } else {
                if (i == count + 1) {
                    return 3;
                }
                return 4;
            }
        }
        if (i != getDialogsArray().size()) {
            return 0;
        }
        if (MessagesController.getInstance().dialogsEndReached) {
            return 5;
        }
        return 1;
    }
}
