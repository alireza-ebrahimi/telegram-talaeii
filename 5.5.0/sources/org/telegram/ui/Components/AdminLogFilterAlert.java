package org.telegram.ui.Components;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.TL_channelAdminLogEventsFilter;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetCell;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.CheckBoxUserCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.StickerPreviewViewer;

public class AdminLogFilterAlert extends BottomSheet {
    private ListAdapter adapter;
    private int adminsRow;
    private int allAdminsRow;
    private ArrayList<ChannelParticipant> currentAdmins;
    private TL_channelAdminLogEventsFilter currentFilter;
    private AdminLogFilterAlertDelegate delegate;
    private int deleteRow;
    private int editRow;
    private boolean ignoreLayout;
    private int infoRow;
    private boolean isMegagroup;
    private int leavingRow;
    private RecyclerListView listView;
    private int membersRow;
    private FrameLayout pickerBottomLayout;
    private int pinnedRow;
    private int reqId;
    private int restrictionsRow;
    private BottomSheetCell saveButton;
    private int scrollOffsetY;
    private HashMap<Integer, User> selectedAdmins;
    private Drawable shadowDrawable;
    private Pattern urlPattern;

    public interface AdminLogFilterAlertDelegate {
        void didSelectRights(TL_channelAdminLogEventsFilter tL_channelAdminLogEventsFilter, HashMap<Integer, User> hashMap);
    }

    /* renamed from: org.telegram.ui.Components.AdminLogFilterAlert$3 */
    class C43063 extends OnScrollListener {
        C43063() {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            AdminLogFilterAlert.this.updateLayout();
        }
    }

    /* renamed from: org.telegram.ui.Components.AdminLogFilterAlert$4 */
    class C43074 implements OnItemClickListener {
        C43074() {
        }

        public void onItemClick(View view, int i) {
            ViewHolder findViewHolderForAdapterPosition;
            boolean z;
            if (view instanceof CheckBoxCell) {
                CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                boolean isChecked = checkBoxCell.isChecked();
                checkBoxCell.setChecked(!isChecked, true);
                TL_channelAdminLogEventsFilter access$1000;
                TL_channelAdminLogEventsFilter access$10002;
                TL_channelAdminLogEventsFilter access$10003;
                TL_channelAdminLogEventsFilter access$10004;
                TL_channelAdminLogEventsFilter access$10005;
                TL_channelAdminLogEventsFilter access$10006;
                TL_channelAdminLogEventsFilter access$10007;
                TL_channelAdminLogEventsFilter access$10008;
                TL_channelAdminLogEventsFilter access$10009;
                TL_channelAdminLogEventsFilter access$100010;
                TL_channelAdminLogEventsFilter access$100011;
                TL_channelAdminLogEventsFilter access$100012;
                int childCount;
                int i2;
                View childAt;
                ViewHolder findContainingViewHolder;
                if (i == 0) {
                    if (isChecked) {
                        AdminLogFilterAlert.this.currentFilter = new TL_channelAdminLogEventsFilter();
                        access$1000 = AdminLogFilterAlert.this.currentFilter;
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        access$10004 = AdminLogFilterAlert.this.currentFilter;
                        access$10005 = AdminLogFilterAlert.this.currentFilter;
                        access$10006 = AdminLogFilterAlert.this.currentFilter;
                        access$10007 = AdminLogFilterAlert.this.currentFilter;
                        access$10008 = AdminLogFilterAlert.this.currentFilter;
                        access$10009 = AdminLogFilterAlert.this.currentFilter;
                        access$100010 = AdminLogFilterAlert.this.currentFilter;
                        access$100011 = AdminLogFilterAlert.this.currentFilter;
                        access$100012 = AdminLogFilterAlert.this.currentFilter;
                        TL_channelAdminLogEventsFilter access$100013 = AdminLogFilterAlert.this.currentFilter;
                        AdminLogFilterAlert.this.currentFilter.delete = false;
                        access$100013.edit = false;
                        access$100012.pinned = false;
                        access$100011.settings = false;
                        access$100010.info = false;
                        access$10009.demote = false;
                        access$10008.promote = false;
                        access$10007.unkick = false;
                        access$10006.kick = false;
                        access$10005.unban = false;
                        access$10004.ban = false;
                        access$10003.invite = false;
                        access$10002.leave = false;
                        access$1000.join = false;
                    } else {
                        AdminLogFilterAlert.this.currentFilter = null;
                    }
                    childCount = AdminLogFilterAlert.this.listView.getChildCount();
                    for (i2 = 0; i2 < childCount; i2++) {
                        childAt = AdminLogFilterAlert.this.listView.getChildAt(i2);
                        findContainingViewHolder = AdminLogFilterAlert.this.listView.findContainingViewHolder(childAt);
                        int adapterPosition = findContainingViewHolder.getAdapterPosition();
                        if (findContainingViewHolder.getItemViewType() == 0 && adapterPosition > 0 && adapterPosition < AdminLogFilterAlert.this.allAdminsRow - 1) {
                            ((CheckBoxCell) childAt).setChecked(!isChecked, true);
                        }
                    }
                } else if (i == AdminLogFilterAlert.this.allAdminsRow) {
                    if (isChecked) {
                        AdminLogFilterAlert.this.selectedAdmins = new HashMap();
                    } else {
                        AdminLogFilterAlert.this.selectedAdmins = null;
                    }
                    childCount = AdminLogFilterAlert.this.listView.getChildCount();
                    for (i2 = 0; i2 < childCount; i2++) {
                        childAt = AdminLogFilterAlert.this.listView.getChildAt(i2);
                        findContainingViewHolder = AdminLogFilterAlert.this.listView.findContainingViewHolder(childAt);
                        findContainingViewHolder.getAdapterPosition();
                        if (findContainingViewHolder.getItemViewType() == 2) {
                            ((CheckBoxUserCell) childAt).setChecked(!isChecked, true);
                        }
                    }
                } else {
                    TL_channelAdminLogEventsFilter access$100014;
                    if (AdminLogFilterAlert.this.currentFilter == null) {
                        AdminLogFilterAlert.this.currentFilter = new TL_channelAdminLogEventsFilter();
                        access$1000 = AdminLogFilterAlert.this.currentFilter;
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        access$100014 = AdminLogFilterAlert.this.currentFilter;
                        access$10004 = AdminLogFilterAlert.this.currentFilter;
                        access$10005 = AdminLogFilterAlert.this.currentFilter;
                        access$10006 = AdminLogFilterAlert.this.currentFilter;
                        access$10007 = AdminLogFilterAlert.this.currentFilter;
                        access$10008 = AdminLogFilterAlert.this.currentFilter;
                        access$10009 = AdminLogFilterAlert.this.currentFilter;
                        access$100010 = AdminLogFilterAlert.this.currentFilter;
                        access$100011 = AdminLogFilterAlert.this.currentFilter;
                        access$100012 = AdminLogFilterAlert.this.currentFilter;
                        AdminLogFilterAlert.this.currentFilter.delete = true;
                        access$100012.edit = true;
                        access$100011.pinned = true;
                        access$100010.settings = true;
                        access$10009.info = true;
                        access$10008.demote = true;
                        access$10007.promote = true;
                        access$10006.unkick = true;
                        access$10005.kick = true;
                        access$10004.unban = true;
                        access$100014.ban = true;
                        access$10003.invite = true;
                        access$10002.leave = true;
                        access$1000.join = true;
                        findViewHolderForAdapterPosition = AdminLogFilterAlert.this.listView.findViewHolderForAdapterPosition(0);
                        if (findViewHolderForAdapterPosition != null) {
                            ((CheckBoxCell) findViewHolderForAdapterPosition.itemView).setChecked(false, true);
                        }
                    }
                    if (i == AdminLogFilterAlert.this.restrictionsRow) {
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        access$100014 = AdminLogFilterAlert.this.currentFilter;
                        access$10004 = AdminLogFilterAlert.this.currentFilter;
                        z = !AdminLogFilterAlert.this.currentFilter.kick;
                        access$10004.unban = z;
                        access$100014.unkick = z;
                        access$10003.ban = z;
                        access$10002.kick = z;
                    } else if (i == AdminLogFilterAlert.this.adminsRow) {
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        z = !AdminLogFilterAlert.this.currentFilter.demote;
                        access$10003.demote = z;
                        access$10002.promote = z;
                    } else if (i == AdminLogFilterAlert.this.membersRow) {
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        z = !AdminLogFilterAlert.this.currentFilter.join;
                        access$10003.join = z;
                        access$10002.invite = z;
                    } else if (i == AdminLogFilterAlert.this.infoRow) {
                        access$10002 = AdminLogFilterAlert.this.currentFilter;
                        access$10003 = AdminLogFilterAlert.this.currentFilter;
                        z = !AdminLogFilterAlert.this.currentFilter.info;
                        access$10003.settings = z;
                        access$10002.info = z;
                    } else if (i == AdminLogFilterAlert.this.deleteRow) {
                        AdminLogFilterAlert.this.currentFilter.delete = !AdminLogFilterAlert.this.currentFilter.delete;
                    } else if (i == AdminLogFilterAlert.this.editRow) {
                        AdminLogFilterAlert.this.currentFilter.edit = !AdminLogFilterAlert.this.currentFilter.edit;
                    } else if (i == AdminLogFilterAlert.this.pinnedRow) {
                        AdminLogFilterAlert.this.currentFilter.pinned = !AdminLogFilterAlert.this.currentFilter.pinned;
                    } else if (i == AdminLogFilterAlert.this.leavingRow) {
                        AdminLogFilterAlert.this.currentFilter.leave = !AdminLogFilterAlert.this.currentFilter.leave;
                    }
                }
                if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.join || AdminLogFilterAlert.this.currentFilter.leave || AdminLogFilterAlert.this.currentFilter.leave || AdminLogFilterAlert.this.currentFilter.invite || AdminLogFilterAlert.this.currentFilter.ban || AdminLogFilterAlert.this.currentFilter.unban || AdminLogFilterAlert.this.currentFilter.kick || AdminLogFilterAlert.this.currentFilter.unkick || AdminLogFilterAlert.this.currentFilter.promote || AdminLogFilterAlert.this.currentFilter.demote || AdminLogFilterAlert.this.currentFilter.info || AdminLogFilterAlert.this.currentFilter.settings || AdminLogFilterAlert.this.currentFilter.pinned || AdminLogFilterAlert.this.currentFilter.edit || AdminLogFilterAlert.this.currentFilter.delete) {
                    AdminLogFilterAlert.this.saveButton.setEnabled(true);
                    AdminLogFilterAlert.this.saveButton.setAlpha(1.0f);
                    return;
                }
                AdminLogFilterAlert.this.saveButton.setEnabled(false);
                AdminLogFilterAlert.this.saveButton.setAlpha(0.5f);
            } else if (view instanceof CheckBoxUserCell) {
                CheckBoxUserCell checkBoxUserCell = (CheckBoxUserCell) view;
                if (AdminLogFilterAlert.this.selectedAdmins == null) {
                    AdminLogFilterAlert.this.selectedAdmins = new HashMap();
                    findViewHolderForAdapterPosition = AdminLogFilterAlert.this.listView.findViewHolderForAdapterPosition(AdminLogFilterAlert.this.allAdminsRow);
                    if (findViewHolderForAdapterPosition != null) {
                        ((CheckBoxCell) findViewHolderForAdapterPosition.itemView).setChecked(false, true);
                    }
                    for (int i3 = 0; i3 < AdminLogFilterAlert.this.currentAdmins.size(); i3++) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChannelParticipant) AdminLogFilterAlert.this.currentAdmins.get(i3)).user_id));
                        AdminLogFilterAlert.this.selectedAdmins.put(Integer.valueOf(user.id), user);
                    }
                }
                z = checkBoxUserCell.isChecked();
                User currentUser = checkBoxUserCell.getCurrentUser();
                if (z) {
                    AdminLogFilterAlert.this.selectedAdmins.remove(Integer.valueOf(currentUser.id));
                } else {
                    AdminLogFilterAlert.this.selectedAdmins.put(Integer.valueOf(currentUser.id), currentUser);
                }
                checkBoxUserCell.setChecked(!z, true);
            }
        }
    }

    /* renamed from: org.telegram.ui.Components.AdminLogFilterAlert$5 */
    class C43085 implements OnClickListener {
        C43085() {
        }

        public void onClick(View view) {
            AdminLogFilterAlert.this.delegate.didSelectRights(AdminLogFilterAlert.this.currentFilter, AdminLogFilterAlert.this.selectedAdmins);
            AdminLogFilterAlert.this.dismiss();
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return (AdminLogFilterAlert.this.isMegagroup ? 9 : 7) + (AdminLogFilterAlert.this.currentAdmins != null ? AdminLogFilterAlert.this.currentAdmins.size() + 2 : 0);
        }

        public int getItemViewType(int i) {
            return (i < AdminLogFilterAlert.this.allAdminsRow + -1 || i == AdminLogFilterAlert.this.allAdminsRow) ? 0 : i == AdminLogFilterAlert.this.allAdminsRow + -1 ? 1 : 2;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            boolean z2 = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    CheckBoxCell checkBoxCell = (CheckBoxCell) viewHolder.itemView;
                    if (i == 0) {
                        checkBoxCell.setText(LocaleController.getString("EventLogFilterAll", R.string.EventLogFilterAll), TtmlNode.ANONYMOUS_REGION_ID, AdminLogFilterAlert.this.currentFilter == null, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.restrictionsRow) {
                        r1 = LocaleController.getString("EventLogFilterNewRestrictions", R.string.EventLogFilterNewRestrictions);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.kick && AdminLogFilterAlert.this.currentFilter.ban && AdminLogFilterAlert.this.currentFilter.unkick && AdminLogFilterAlert.this.currentFilter.unban)) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.adminsRow) {
                        r1 = LocaleController.getString("EventLogFilterNewAdmins", R.string.EventLogFilterNewAdmins);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.promote && AdminLogFilterAlert.this.currentFilter.demote)) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.membersRow) {
                        r1 = LocaleController.getString("EventLogFilterNewMembers", R.string.EventLogFilterNewMembers);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.invite && AdminLogFilterAlert.this.currentFilter.join)) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.infoRow) {
                        if (AdminLogFilterAlert.this.isMegagroup) {
                            r1 = LocaleController.getString("EventLogFilterGroupInfo", R.string.EventLogFilterGroupInfo);
                            r4 = TtmlNode.ANONYMOUS_REGION_ID;
                            if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.info) {
                                z = true;
                            }
                            checkBoxCell.setText(r1, r4, z, true);
                            return;
                        }
                        r1 = LocaleController.getString("EventLogFilterChannelInfo", R.string.EventLogFilterChannelInfo);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.info) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.deleteRow) {
                        r1 = LocaleController.getString("EventLogFilterDeletedMessages", R.string.EventLogFilterDeletedMessages);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.delete) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.editRow) {
                        r1 = LocaleController.getString("EventLogFilterEditedMessages", R.string.EventLogFilterEditedMessages);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.edit) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.pinnedRow) {
                        r1 = LocaleController.getString("EventLogFilterPinnedMessages", R.string.EventLogFilterPinnedMessages);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.pinned) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else if (i == AdminLogFilterAlert.this.leavingRow) {
                        r1 = LocaleController.getString("EventLogFilterLeavingMembers", R.string.EventLogFilterLeavingMembers);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.leave)) {
                            z2 = false;
                        }
                        checkBoxCell.setText(r1, r4, z2, false);
                        return;
                    } else if (i == AdminLogFilterAlert.this.allAdminsRow) {
                        r1 = LocaleController.getString("EventLogAllAdmins", R.string.EventLogAllAdmins);
                        r4 = TtmlNode.ANONYMOUS_REGION_ID;
                        if (AdminLogFilterAlert.this.selectedAdmins == null) {
                            z = true;
                        }
                        checkBoxCell.setText(r1, r4, z, true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    CheckBoxUserCell checkBoxUserCell = (CheckBoxUserCell) viewHolder.itemView;
                    int i2 = ((ChannelParticipant) AdminLogFilterAlert.this.currentAdmins.get((i - AdminLogFilterAlert.this.allAdminsRow) - 1)).user_id;
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(i2));
                    boolean z3 = AdminLogFilterAlert.this.selectedAdmins == null || AdminLogFilterAlert.this.selectedAdmins.containsKey(Integer.valueOf(i2));
                    if (i == getItemCount() - 1) {
                        z2 = false;
                    }
                    checkBoxUserCell.setUser(user, z3, z2);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
                case 0:
                    view = new CheckBoxCell(this.context, true);
                    view.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    break;
                case 1:
                    View shadowSectionCell = new ShadowSectionCell(this.context);
                    shadowSectionCell.setSize(18);
                    view = new FrameLayout(this.context);
                    ((FrameLayout) view).addView(shadowSectionCell, LayoutHelper.createFrame(-1, -1.0f));
                    view.setBackgroundColor(Theme.getColor(Theme.key_dialogBackgroundGray));
                    break;
                case 2:
                    view = new CheckBoxUserCell(this.context, true);
                    break;
            }
            return new Holder(view);
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            boolean z = true;
            int adapterPosition = viewHolder.getAdapterPosition();
            switch (viewHolder.getItemViewType()) {
                case 0:
                    CheckBoxCell checkBoxCell = (CheckBoxCell) viewHolder.itemView;
                    if (adapterPosition == 0) {
                        checkBoxCell.setChecked(AdminLogFilterAlert.this.currentFilter == null, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.restrictionsRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.kick && AdminLogFilterAlert.this.currentFilter.ban && AdminLogFilterAlert.this.currentFilter.unkick && AdminLogFilterAlert.this.currentFilter.unban))) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.adminsRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.promote && AdminLogFilterAlert.this.currentFilter.demote))) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.membersRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || (AdminLogFilterAlert.this.currentFilter.invite && AdminLogFilterAlert.this.currentFilter.join))) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.infoRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.info)) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.deleteRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.delete)) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.editRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.edit)) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.pinnedRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.pinned)) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.leavingRow) {
                        if (!(AdminLogFilterAlert.this.currentFilter == null || AdminLogFilterAlert.this.currentFilter.leave)) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else if (adapterPosition == AdminLogFilterAlert.this.allAdminsRow) {
                        if (AdminLogFilterAlert.this.selectedAdmins != null) {
                            z = false;
                        }
                        checkBoxCell.setChecked(z, false);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    CheckBoxUserCell checkBoxUserCell = (CheckBoxUserCell) viewHolder.itemView;
                    adapterPosition = ((ChannelParticipant) AdminLogFilterAlert.this.currentAdmins.get((adapterPosition - AdminLogFilterAlert.this.allAdminsRow) - 1)).user_id;
                    if (!(AdminLogFilterAlert.this.selectedAdmins == null || AdminLogFilterAlert.this.selectedAdmins.containsKey(Integer.valueOf(adapterPosition)))) {
                        z = false;
                    }
                    checkBoxUserCell.setChecked(z, false);
                    return;
                default:
                    return;
            }
        }
    }

    public AdminLogFilterAlert(Context context, TL_channelAdminLogEventsFilter tL_channelAdminLogEventsFilter, HashMap<Integer, User> hashMap, boolean z) {
        int i;
        super(context, false);
        if (tL_channelAdminLogEventsFilter != null) {
            this.currentFilter = new TL_channelAdminLogEventsFilter();
            this.currentFilter.join = tL_channelAdminLogEventsFilter.join;
            this.currentFilter.leave = tL_channelAdminLogEventsFilter.leave;
            this.currentFilter.invite = tL_channelAdminLogEventsFilter.invite;
            this.currentFilter.ban = tL_channelAdminLogEventsFilter.ban;
            this.currentFilter.unban = tL_channelAdminLogEventsFilter.unban;
            this.currentFilter.kick = tL_channelAdminLogEventsFilter.kick;
            this.currentFilter.unkick = tL_channelAdminLogEventsFilter.unkick;
            this.currentFilter.promote = tL_channelAdminLogEventsFilter.promote;
            this.currentFilter.demote = tL_channelAdminLogEventsFilter.demote;
            this.currentFilter.info = tL_channelAdminLogEventsFilter.info;
            this.currentFilter.settings = tL_channelAdminLogEventsFilter.settings;
            this.currentFilter.pinned = tL_channelAdminLogEventsFilter.pinned;
            this.currentFilter.edit = tL_channelAdminLogEventsFilter.edit;
            this.currentFilter.delete = tL_channelAdminLogEventsFilter.delete;
        }
        if (hashMap != null) {
            this.selectedAdmins = new HashMap(hashMap);
        }
        this.isMegagroup = z;
        if (this.isMegagroup) {
            i = 2;
            this.restrictionsRow = 1;
        } else {
            this.restrictionsRow = -1;
            i = 1;
        }
        int i2 = i + 1;
        this.adminsRow = i;
        i = i2 + 1;
        this.membersRow = i2;
        i2 = i + 1;
        this.infoRow = i;
        i = i2 + 1;
        this.deleteRow = i2;
        i2 = i + 1;
        this.editRow = i;
        if (this.isMegagroup) {
            i = i2 + 1;
            this.pinnedRow = i2;
        } else {
            this.pinnedRow = -1;
            i = i2;
        }
        this.leavingRow = i;
        this.allAdminsRow = i + 2;
        this.shadowDrawable = context.getResources().getDrawable(R.drawable.sheet_shadow).mutate();
        this.shadowDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogBackground), Mode.MULTIPLY));
        this.containerView = new FrameLayout(context) {
            protected void onDraw(Canvas canvas) {
                AdminLogFilterAlert.this.shadowDrawable.setBounds(0, AdminLogFilterAlert.this.scrollOffsetY - AdminLogFilterAlert.backgroundPaddingTop, getMeasuredWidth(), getMeasuredHeight());
                AdminLogFilterAlert.this.shadowDrawable.draw(canvas);
            }

            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (motionEvent.getAction() != 0 || AdminLogFilterAlert.this.scrollOffsetY == 0 || motionEvent.getY() >= ((float) AdminLogFilterAlert.this.scrollOffsetY)) {
                    return super.onInterceptTouchEvent(motionEvent);
                }
                AdminLogFilterAlert.this.dismiss();
                return true;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                AdminLogFilterAlert.this.updateLayout();
            }

            protected void onMeasure(int i, int i2) {
                int size = MeasureSpec.getSize(i2);
                if (VERSION.SDK_INT >= 21) {
                    size -= AndroidUtilities.statusBarHeight;
                }
                getMeasuredWidth();
                int dp = (((AdminLogFilterAlert.this.isMegagroup ? 9 : 7) * AndroidUtilities.dp(48.0f)) + AndroidUtilities.dp(48.0f)) + AdminLogFilterAlert.backgroundPaddingTop;
                if (AdminLogFilterAlert.this.currentAdmins != null) {
                    dp += ((AdminLogFilterAlert.this.currentAdmins.size() + 1) * AndroidUtilities.dp(48.0f)) + AndroidUtilities.dp(20.0f);
                }
                int i3 = ((float) dp) < ((float) (size / 5)) * 3.2f ? 0 : (size / 5) * 2;
                if (i3 != 0 && dp < size) {
                    i3 -= size - dp;
                }
                if (i3 == 0) {
                    i3 = AdminLogFilterAlert.backgroundPaddingTop;
                }
                if (AdminLogFilterAlert.this.listView.getPaddingTop() != i3) {
                    AdminLogFilterAlert.this.ignoreLayout = true;
                    AdminLogFilterAlert.this.listView.setPadding(0, i3, 0, 0);
                    AdminLogFilterAlert.this.ignoreLayout = false;
                }
                super.onMeasure(i, MeasureSpec.makeMeasureSpec(Math.min(dp, size), 1073741824));
            }

            public boolean onTouchEvent(MotionEvent motionEvent) {
                return !AdminLogFilterAlert.this.isDismissed() && super.onTouchEvent(motionEvent);
            }

            public void requestLayout() {
                if (!AdminLogFilterAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.containerView.setWillNotDraw(false);
        this.containerView.setPadding(backgroundPaddingLeft, 0, backgroundPaddingLeft, 0);
        this.listView = new RecyclerListView(context) {
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                return super.onInterceptTouchEvent(motionEvent) || StickerPreviewViewer.getInstance().onInterceptTouchEvent(motionEvent, AdminLogFilterAlert.this.listView, 0, null);
            }

            public void requestLayout() {
                if (!AdminLogFilterAlert.this.ignoreLayout) {
                    super.requestLayout();
                }
            }
        };
        this.listView.setLayoutManager(new LinearLayoutManager(getContext(), 1, false));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setClipToPadding(false);
        this.listView.setEnabled(true);
        this.listView.setGlowColor(Theme.getColor(Theme.key_dialogScrollGlow));
        this.listView.setOnScrollListener(new C43063());
        this.listView.setOnItemClickListener(new C43074());
        this.containerView.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f, 51, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        View view = new View(context);
        view.setBackgroundResource(R.drawable.header_shadow_reverse);
        this.containerView.addView(view, LayoutHelper.createFrame(-1, 3.0f, 83, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 48.0f));
        this.saveButton = new BottomSheetCell(context, 1);
        this.saveButton.setBackgroundDrawable(Theme.getSelectorDrawable(false));
        this.saveButton.setTextAndIcon(LocaleController.getString("Save", R.string.Save).toUpperCase(), 0);
        this.saveButton.setTextColor(Theme.getColor(Theme.key_dialogTextBlue2));
        this.saveButton.setOnClickListener(new C43085());
        this.containerView.addView(this.saveButton, LayoutHelper.createFrame(-1, 48, 83));
        this.adapter.notifyDataSetChanged();
    }

    @SuppressLint({"NewApi"})
    private void updateLayout() {
        if (this.listView.getChildCount() <= 0) {
            RecyclerListView recyclerListView = this.listView;
            int paddingTop = this.listView.getPaddingTop();
            this.scrollOffsetY = paddingTop;
            recyclerListView.setTopGlowOffset(paddingTop);
            this.containerView.invalidate();
            return;
        }
        View childAt = this.listView.getChildAt(0);
        Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
        paddingTop = childAt.getTop() - AndroidUtilities.dp(8.0f);
        int i = (paddingTop <= 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : paddingTop;
        if (this.scrollOffsetY != i) {
            RecyclerListView recyclerListView2 = this.listView;
            this.scrollOffsetY = i;
            recyclerListView2.setTopGlowOffset(i);
            this.containerView.invalidate();
        }
    }

    protected boolean canDismissWithSwipe() {
        return false;
    }

    public void setAdminLogFilterAlertDelegate(AdminLogFilterAlertDelegate adminLogFilterAlertDelegate) {
        this.delegate = adminLogFilterAlertDelegate;
    }

    public void setCurrentAdmins(ArrayList<ChannelParticipant> arrayList) {
        this.currentAdmins = arrayList;
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
    }
}
