package org.telegram.ui.Adapters;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC.RecentMeUrl;
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
    class C38501 implements OnClickListener {
        C38501() {
        }

        public void onClick(View view) {
            MessagesController.getInstance().hintDialogs.clear();
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("installReferer").commit();
            DialogsAdapter.this.notifyDataSetChanged();
        }
    }

    public DialogsAdapter(Context context, int i) {
        this.mContext = context;
        this.dialogsType = i;
    }

    public DialogsAdapter(Context context, int i, boolean z) {
        this.mContext = context;
        this.dialogsType = i;
        this.isOnlySelect = z;
        boolean z2 = i == 0 && !z;
        this.hasHints = z2;
        if (z) {
            this.selectedDialogs = new ArrayList();
        }
    }

    private ArrayList<TLRPC$TL_dialog> getDialogsArray() {
        return this.dialogsType == 0 ? MessagesController.getInstance().dialogsAll : this.dialogsType == 1 ? MessagesController.getInstance().dialogsServerOnly : this.dialogsType == 2 ? MessagesController.getInstance().dialogsGroupsOnly : this.dialogsType == 13 ? MessagesController.getInstance().dialogsForward : this.dialogsType == 3 ? MessagesController.getInstance().dialogsUsers : this.dialogsType == 4 ? MessagesController.getInstance().dialogsGroups : this.dialogsType == 5 ? MessagesController.getInstance().dialogsChannels : this.dialogsType == 6 ? MessagesController.getInstance().dialogsBots : this.dialogsType == 7 ? MessagesController.getInstance().dialogsMegaGroups : this.dialogsType == 8 ? MessagesController.getInstance().dialogsFavs : this.dialogsType == 9 ? MessagesController.getInstance().dialogsGroupsAll : this.dialogsType == 10 ? MessagesController.getInstance().dialogsHidden : this.dialogsType == 11 ? MessagesController.getInstance().dialogsUnread : this.dialogsType == 12 ? MessagesController.getInstance().dialogsAds : null;
    }

    public void addOrRemoveSelectedDialog(long j, View view) {
        try {
            if (this.selectedDialogs.contains(Long.valueOf(j))) {
                this.selectedDialogs.remove(Long.valueOf(j));
                if (view instanceof DialogCell) {
                    ((DialogCell) view).setChecked(false, true);
                    return;
                }
                return;
            }
            this.selectedDialogs.add(Long.valueOf(j));
            if (view instanceof DialogCell) {
                ((DialogCell) view).setChecked(true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TLObject getItem(int i) {
        ArrayList dialogsArray = getDialogsArray();
        if (this.hasHints) {
            int size = MessagesController.getInstance().hintDialogs.size();
            if (i < size + 2) {
                return (TLObject) MessagesController.getInstance().hintDialogs.get(i - 1);
            }
            i -= size + 2;
        }
        return (i < 0 || i >= dialogsArray.size()) ? null : (TLObject) dialogsArray.get(i);
    }

    public int getItemCount() {
        int size = getDialogsArray().size();
        if (size == 0 && MessagesController.getInstance().loadingDialogs) {
            return 0;
        }
        if (!MessagesController.getInstance().dialogsEndReached || size == 0) {
            size++;
        }
        if (this.hasHints) {
            size += MessagesController.getInstance().hintDialogs.size() + 2;
        }
        this.currentCount = size;
        return size;
    }

    public int getItemViewType(int i) {
        if (this.hasHints) {
            int size = MessagesController.getInstance().hintDialogs.size();
            if (i < size + 2) {
                return i == 0 ? 2 : i == size + 1 ? 3 : 4;
            } else {
                i -= size + 2;
            }
        }
        return i == getDialogsArray().size() ? !MessagesController.getInstance().dialogsEndReached ? 1 : 5 : 0;
    }

    public ArrayList<Long> getSelectedDialogs() {
        return this.selectedDialogs;
    }

    public boolean hasSelectedDialogs() {
        return (this.selectedDialogs == null || this.selectedDialogs.isEmpty()) ? false : true;
    }

    public boolean isDataSetChanged() {
        int i = this.currentCount;
        return i != getItemCount() || i == 1;
    }

    public boolean isEnabled(ViewHolder viewHolder) {
        int itemViewType = viewHolder.getItemViewType();
        return (itemViewType == 1 || itemViewType == 5 || itemViewType == 3) ? false : true;
    }

    public void notifyDataSetChanged() {
        boolean z = (this.dialogsType != 0 || this.isOnlySelect || MessagesController.getInstance().hintDialogs.isEmpty()) ? false : true;
        this.hasHints = z;
        super.notifyDataSetChanged();
    }

    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        boolean z = true;
        switch (viewHolder.getItemViewType()) {
            case 0:
                DialogCell dialogCell = (DialogCell) viewHolder.itemView;
                TLRPC$TL_dialog tLRPC$TL_dialog = (TLRPC$TL_dialog) getItem(i);
                if (this.hasHints) {
                    i -= MessagesController.getInstance().hintDialogs.size() + 2;
                }
                dialogCell.useSeparator = i != getItemCount() + -1;
                if (tLRPC$TL_dialog.isSelected()) {
                    dialogCell.setDialogSelected(true);
                } else {
                    dialogCell.setDialogSelected(false);
                }
                if (this.dialogsType == 0 && AndroidUtilities.isTablet()) {
                    if (tLRPC$TL_dialog.id != this.openedDialogId) {
                        z = false;
                    }
                    dialogCell.setDialogSelected(z);
                }
                if (this.selectedDialogs != null) {
                    dialogCell.setChecked(this.selectedDialogs.contains(Long.valueOf(tLRPC$TL_dialog.id)), false);
                }
                dialogCell.setDialog(tLRPC$TL_dialog, i, this.dialogsType);
                return;
            case 4:
                ((DialogMeUrlCell) viewHolder.itemView).setRecentMeUrl((RecentMeUrl) getItem(i));
                return;
            default:
                return;
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View dialogCell;
        switch (i) {
            case 0:
                dialogCell = new DialogCell(this.mContext, this.isOnlySelect);
                break;
            case 1:
                dialogCell = new LoadingCell(this.mContext);
                break;
            case 2:
                View headerCell = new HeaderCell(this.mContext);
                headerCell.setText(LocaleController.getString("RecentlyViewed", R.string.RecentlyViewed));
                View textView = new TextView(this.mContext);
                textView.setTextSize(1, 15.0f);
                textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueHeader));
                textView.setText(LocaleController.getString("RecentlyViewedHide", R.string.RecentlyViewedHide));
                textView.setGravity((LocaleController.isRTL ? 3 : 5) | 16);
                headerCell.addView(textView, LayoutHelper.createFrame(-1, -1.0f, (LocaleController.isRTL ? 3 : 5) | 48, 17.0f, 15.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
                textView.setOnClickListener(new C38501());
                dialogCell = headerCell;
                break;
            case 3:
                View c38512 = new FrameLayout(this.mContext) {
                    protected void onMeasure(int i, int i2) {
                        super.onMeasure(MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i), 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(12.0f), 1073741824));
                    }
                };
                c38512.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
                View view = new View(this.mContext);
                view.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                c38512.addView(view, LayoutHelper.createFrame(-1, -1.0f));
                dialogCell = c38512;
                break;
            case 4:
                dialogCell = new DialogMeUrlCell(this.mContext);
                break;
            default:
                dialogCell = new DialogsEmptyCell(this.mContext);
                break;
        }
        dialogCell.setLayoutParams(new LayoutParams(-1, i == 5 ? -1 : -2));
        return new Holder(dialogCell);
    }

    public void onViewAttachedToWindow(ViewHolder viewHolder) {
        if (viewHolder.itemView instanceof DialogCell) {
            ((DialogCell) viewHolder.itemView).checkCurrentDialogIndex();
        }
    }

    public void setOpenedDialogId(long j) {
        this.openedDialogId = j;
    }
}
