package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_contact;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.TL_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_channelParticipantBanned;
import org.telegram.tgnet.TLRPC.TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC.TL_channelParticipantSelf;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsAdmins;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsBanned;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsKicked;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_editBanned;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.ActionBarMenuItem.ActionBarMenuItemSearchListener;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Adapters.SearchAdapterHelper;
import org.telegram.ui.Adapters.SearchAdapterHelper.HashtagObject;
import org.telegram.ui.Adapters.SearchAdapterHelper.SearchAdapterHelperDelegate;
import org.telegram.ui.Cells.GraySectionCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ManageChatTextCell;
import org.telegram.ui.Cells.ManageChatUserCell;
import org.telegram.ui.Cells.ManageChatUserCell.ManageChatUserCellDelegate;
import org.telegram.ui.Cells.RadioCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.ChannelRightsEditActivity.ChannelRightsEditActivityDelegate;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.ContactsActivity.ContactsActivityDelegate;

public class ChannelUsersActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int search_button = 0;
    private int addNew2Row;
    private int addNewRow;
    private int addNewSectionRow;
    private int blockedEmptyRow;
    private int changeAddHeaderRow;
    private int changeAddRadio1Row;
    private int changeAddRadio2Row;
    private int changeAddSectionRow;
    private int chatId = this.arguments.getInt("chat_id");
    private Chat currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
    private EmptyTextProgressView emptyView;
    private boolean firstEndReached;
    private boolean firstLoaded;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private boolean loadingUsers;
    private boolean needOpenSearch = this.arguments.getBoolean("open_search");
    private ArrayList<ChannelParticipant> participants = new ArrayList();
    private ArrayList<ChannelParticipant> participants2 = new ArrayList();
    private int participants2EndRow;
    private int participants2StartRow;
    private int participantsDividerRow;
    private int participantsEndRow;
    private int participantsInfoRow;
    private HashMap<Integer, ChannelParticipant> participantsMap = new HashMap();
    private int participantsStartRow;
    private int restricted1SectionRow;
    private int restricted2SectionRow;
    private int rowCount;
    private ActionBarMenuItem searchItem;
    private SearchAdapter searchListViewAdapter;
    private boolean searchWas;
    private boolean searching;
    private int selectType = this.arguments.getInt("selectType");
    private int type = this.arguments.getInt(Param.TYPE);

    /* renamed from: org.telegram.ui.ChannelUsersActivity$1 */
    class C41951 extends ActionBarMenuOnItemClick {
        C41951() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChannelUsersActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelUsersActivity$2 */
    class C41962 extends ActionBarMenuItemSearchListener {
        C41962() {
        }

        public void onSearchCollapse() {
            ChannelUsersActivity.this.searchListViewAdapter.searchDialogs(null);
            ChannelUsersActivity.this.searching = false;
            ChannelUsersActivity.this.searchWas = false;
            ChannelUsersActivity.this.listView.setAdapter(ChannelUsersActivity.this.listViewAdapter);
            ChannelUsersActivity.this.listViewAdapter.notifyDataSetChanged();
            ChannelUsersActivity.this.listView.setFastScrollVisible(true);
            ChannelUsersActivity.this.listView.setVerticalScrollBarEnabled(false);
            ChannelUsersActivity.this.emptyView.setShowAtCenter(false);
        }

        public void onSearchExpand() {
            ChannelUsersActivity.this.searching = true;
            ChannelUsersActivity.this.emptyView.setShowAtCenter(true);
        }

        public void onTextChanged(EditText editText) {
            if (ChannelUsersActivity.this.searchListViewAdapter != null) {
                String obj = editText.getText().toString();
                if (obj.length() != 0) {
                    ChannelUsersActivity.this.searchWas = true;
                    if (ChannelUsersActivity.this.listView != null) {
                        ChannelUsersActivity.this.listView.setAdapter(ChannelUsersActivity.this.searchListViewAdapter);
                        ChannelUsersActivity.this.searchListViewAdapter.notifyDataSetChanged();
                        ChannelUsersActivity.this.listView.setFastScrollVisible(false);
                        ChannelUsersActivity.this.listView.setVerticalScrollBarEnabled(true);
                    }
                }
                ChannelUsersActivity.this.searchListViewAdapter.searchDialogs(obj);
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelUsersActivity$3 */
    class C42003 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.ChannelUsersActivity$3$1 */
        class C41971 implements ContactsActivityDelegate {
            C41971() {
            }

            public void didSelectContact(User user, String str, ContactsActivity contactsActivity) {
                MessagesController.getInstance().addUserToChat(ChannelUsersActivity.this.chatId, user, null, str != null ? Utilities.parseInt(str).intValue() : 0, null, ChannelUsersActivity.this);
            }
        }

        C42003() {
        }

        public void onItemClick(View view, int i) {
            int i2 = 1;
            Bundle bundle;
            if (i == ChannelUsersActivity.this.addNewRow) {
                if (ChannelUsersActivity.this.type == 0) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", ChannelUsersActivity.this.chatId);
                    bundle.putInt(Param.TYPE, 2);
                    bundle.putInt("selectType", 2);
                    ChannelUsersActivity.this.presentFragment(new ChannelUsersActivity(bundle));
                } else if (ChannelUsersActivity.this.type == 1) {
                    bundle = new Bundle();
                    bundle.putInt("chat_id", ChannelUsersActivity.this.chatId);
                    bundle.putInt(Param.TYPE, 2);
                    bundle.putInt("selectType", 1);
                    ChannelUsersActivity.this.presentFragment(new ChannelUsersActivity(bundle));
                } else if (ChannelUsersActivity.this.type == 2) {
                    bundle = new Bundle();
                    bundle.putBoolean("onlyUsers", true);
                    bundle.putBoolean("destroyAfterSelect", true);
                    bundle.putBoolean("returnAsResult", true);
                    bundle.putBoolean("needForwardCount", false);
                    bundle.putString("selectAlertString", LocaleController.getString("ChannelAddTo", R.string.ChannelAddTo));
                    BaseFragment contactsActivity = new ContactsActivity(bundle);
                    contactsActivity.setDelegate(new C41971());
                    ChannelUsersActivity.this.presentFragment(contactsActivity);
                }
            } else if (i == ChannelUsersActivity.this.addNew2Row) {
                ChannelUsersActivity.this.presentFragment(new GroupInviteActivity(ChannelUsersActivity.this.chatId));
            } else if (i == ChannelUsersActivity.this.changeAddRadio1Row || i == ChannelUsersActivity.this.changeAddRadio2Row) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ChannelUsersActivity.this.chatId));
                if (chat != null) {
                    int i3;
                    if (i == 1 && !chat.democracy) {
                        chat.democracy = true;
                        i3 = 1;
                    } else if (i == 2 && chat.democracy) {
                        chat.democracy = false;
                        i3 = 1;
                    } else {
                        r0 = false;
                    }
                    if (i3 != 0) {
                        MessagesController.getInstance().toogleChannelInvites(ChannelUsersActivity.this.chatId, chat.democracy);
                        int childCount = ChannelUsersActivity.this.listView.getChildCount();
                        for (r2 = 0; r2 < childCount; r2++) {
                            View childAt = ChannelUsersActivity.this.listView.getChildAt(r2);
                            if (childAt instanceof RadioCell) {
                                int intValue = ((Integer) childAt.getTag()).intValue();
                                RadioCell radioCell = (RadioCell) childAt;
                                boolean z = (intValue == 0 && chat.democracy) || (intValue == 1 && !chat.democracy);
                                radioCell.setChecked(z, true);
                            }
                        }
                    }
                }
            } else {
                int i4;
                TL_channelBannedRights tL_channelBannedRights;
                TL_channelAdminRights tL_channelAdminRights;
                boolean z2;
                ChannelParticipant channelParticipant;
                ChannelParticipant item;
                if (ChannelUsersActivity.this.listView.getAdapter() == ChannelUsersActivity.this.listViewAdapter) {
                    item = ChannelUsersActivity.this.listViewAdapter.getItem(i);
                    if (item != null) {
                        i4 = item.user_id;
                        tL_channelBannedRights = item.banned_rights;
                        tL_channelAdminRights = item.admin_rights;
                        r0 = !((item instanceof TL_channelParticipantAdmin) || (item instanceof TL_channelParticipantCreator)) || item.can_edit;
                        if (item instanceof TL_channelParticipantCreator) {
                            tL_channelAdminRights = new TL_channelAdminRights();
                            tL_channelAdminRights.add_admins = true;
                            tL_channelAdminRights.pin_messages = true;
                            tL_channelAdminRights.invite_link = true;
                            tL_channelAdminRights.invite_users = true;
                            tL_channelAdminRights.ban_users = true;
                            tL_channelAdminRights.delete_messages = true;
                            tL_channelAdminRights.edit_messages = true;
                            tL_channelAdminRights.post_messages = true;
                            tL_channelAdminRights.change_info = true;
                            z2 = r0;
                            channelParticipant = item;
                        } else {
                            z2 = r0;
                            channelParticipant = item;
                        }
                    } else {
                        z2 = false;
                        i4 = 0;
                        channelParticipant = item;
                        tL_channelAdminRights = null;
                        tL_channelBannedRights = null;
                    }
                } else {
                    TLObject item2 = ChannelUsersActivity.this.searchListViewAdapter.getItem(i);
                    if (item2 instanceof User) {
                        User user = (User) item2;
                        MessagesController.getInstance().putUser(user, false);
                        HashMap access$1200 = ChannelUsersActivity.this.participantsMap;
                        i4 = user.id;
                        item = (ChannelParticipant) access$1200.get(Integer.valueOf(i4));
                    } else if (item2 instanceof ChannelParticipant) {
                        i4 = 0;
                        item = (ChannelParticipant) item2;
                    } else {
                        i4 = 0;
                        item = null;
                    }
                    if (item != null) {
                        i4 = item.user_id;
                        r0 = !((item instanceof TL_channelParticipantAdmin) || (item instanceof TL_channelParticipantCreator)) || item.can_edit;
                        tL_channelBannedRights = item.banned_rights;
                        tL_channelAdminRights = item.admin_rights;
                        z2 = r0;
                        channelParticipant = item;
                    } else {
                        z2 = true;
                        channelParticipant = item;
                        tL_channelAdminRights = null;
                        tL_channelBannedRights = null;
                    }
                }
                if (i4 == 0) {
                    return;
                }
                BaseFragment channelRightsEditActivity;
                if (ChannelUsersActivity.this.selectType == 0) {
                    if (ChannelUsersActivity.this.type == 1) {
                        r0 = ChannelUsersActivity.this.currentChat.creator || z2;
                        z2 = r0;
                    } else {
                        z2 = ChannelUsersActivity.this.type == 0 ? ChatObject.canBlockUsers(ChannelUsersActivity.this.currentChat) : false;
                    }
                    if ((ChannelUsersActivity.this.type == 1 || ChannelUsersActivity.this.currentChat.megagroup) && !(ChannelUsersActivity.this.type == 2 && ChannelUsersActivity.this.selectType == 0)) {
                        if (tL_channelBannedRights == null) {
                            tL_channelBannedRights = new TL_channelBannedRights();
                            tL_channelBannedRights.view_messages = true;
                            tL_channelBannedRights.send_stickers = true;
                            tL_channelBannedRights.send_media = true;
                            tL_channelBannedRights.embed_links = true;
                            tL_channelBannedRights.send_messages = true;
                            tL_channelBannedRights.send_games = true;
                            tL_channelBannedRights.send_inline = true;
                            tL_channelBannedRights.send_gifs = true;
                        }
                        r2 = ChannelUsersActivity.this.chatId;
                        if (ChannelUsersActivity.this.type == 1) {
                            i2 = 0;
                        }
                        channelRightsEditActivity = new ChannelRightsEditActivity(i4, r2, tL_channelAdminRights, tL_channelBannedRights, i2, z2);
                        channelRightsEditActivity.setDelegate(new ChannelRightsEditActivityDelegate() {
                            public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                                if (channelParticipant != null) {
                                    channelParticipant.admin_rights = tL_channelAdminRights;
                                    channelParticipant.banned_rights = tL_channelBannedRights;
                                    ChannelParticipant channelParticipant = (ChannelParticipant) ChannelUsersActivity.this.participantsMap.get(Integer.valueOf(channelParticipant.user_id));
                                    if (channelParticipant != null) {
                                        channelParticipant.admin_rights = tL_channelAdminRights;
                                        channelParticipant.banned_rights = tL_channelBannedRights;
                                    }
                                }
                            }
                        });
                        ChannelUsersActivity.this.presentFragment(channelRightsEditActivity);
                        return;
                    }
                    bundle = new Bundle();
                    bundle.putInt("user_id", i4);
                    ChannelUsersActivity.this.presentFragment(new ProfileActivity(bundle));
                } else if (ChannelUsersActivity.this.currentChat.megagroup || ChannelUsersActivity.this.selectType == 1) {
                    r2 = ChannelUsersActivity.this.chatId;
                    if (ChannelUsersActivity.this.selectType == 1) {
                        i2 = 0;
                    }
                    channelRightsEditActivity = new ChannelRightsEditActivity(i4, r2, tL_channelAdminRights, tL_channelBannedRights, i2, z2);
                    channelRightsEditActivity.setDelegate(new ChannelRightsEditActivityDelegate() {
                        public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                            if (channelParticipant != null) {
                                channelParticipant.admin_rights = tL_channelAdminRights;
                                channelParticipant.banned_rights = tL_channelBannedRights;
                                ChannelParticipant channelParticipant = (ChannelParticipant) ChannelUsersActivity.this.participantsMap.get(Integer.valueOf(channelParticipant.user_id));
                                if (channelParticipant != null) {
                                    channelParticipant.admin_rights = tL_channelAdminRights;
                                    channelParticipant.banned_rights = tL_channelBannedRights;
                                }
                            }
                            ChannelUsersActivity.this.removeSelfFromStack();
                        }
                    });
                    ChannelUsersActivity.this.presentFragment(channelRightsEditActivity);
                } else {
                    MessagesController.getInstance().deleteUserFromChat(ChannelUsersActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(i4)), null);
                    ChannelUsersActivity.this.finishFragment();
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelUsersActivity$4 */
    class C42014 implements OnItemLongClickListener {
        C42014() {
        }

        public boolean onItemClick(View view, int i) {
            return ChannelUsersActivity.this.getParentActivity() != null && ChannelUsersActivity.this.listView.getAdapter() == ChannelUsersActivity.this.listViewAdapter && ChannelUsersActivity.this.createMenuForParticipant(ChannelUsersActivity.this.listViewAdapter.getItem(i), false);
        }
    }

    /* renamed from: org.telegram.ui.ChannelUsersActivity$5 */
    class C42025 extends OnScrollListener {
        C42025() {
        }

        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            if (i == 1 && ChannelUsersActivity.this.searching && ChannelUsersActivity.this.searchWas) {
                AndroidUtilities.hideKeyboard(ChannelUsersActivity.this.getParentActivity().getCurrentFocus());
            }
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            super.onScrolled(recyclerView, i, i2);
        }
    }

    /* renamed from: org.telegram.ui.ChannelUsersActivity$8 */
    class C42088 implements Runnable {
        C42088() {
        }

        public void run() {
            ChannelUsersActivity.this.firstEndReached = false;
            ChannelUsersActivity.this.getChannelParticipants(0, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ChannelUsersActivity$ListAdapter$1 */
        class C42131 implements ManageChatUserCellDelegate {
            C42131() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                return ChannelUsersActivity.this.createMenuForParticipant(ChannelUsersActivity.this.listViewAdapter.getItem(((Integer) manageChatUserCell.getTag()).intValue()), !z);
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ChannelParticipant getItem(int i) {
            return (ChannelUsersActivity.this.participantsStartRow == -1 || i < ChannelUsersActivity.this.participantsStartRow || i >= ChannelUsersActivity.this.participantsEndRow) ? (ChannelUsersActivity.this.participants2StartRow == -1 || i < ChannelUsersActivity.this.participants2StartRow || i >= ChannelUsersActivity.this.participants2EndRow) ? null : (ChannelParticipant) ChannelUsersActivity.this.participants2.get(i - ChannelUsersActivity.this.participants2StartRow) : (ChannelParticipant) ChannelUsersActivity.this.participants.get(i - ChannelUsersActivity.this.participantsStartRow);
        }

        public int getItemCount() {
            return (!ChannelUsersActivity.this.loadingUsers || ChannelUsersActivity.this.firstLoaded) ? ChannelUsersActivity.this.rowCount : 0;
        }

        public int getItemViewType(int i) {
            return (i == ChannelUsersActivity.this.addNewRow || i == ChannelUsersActivity.this.addNew2Row) ? 2 : (i < ChannelUsersActivity.this.participantsStartRow || i >= ChannelUsersActivity.this.participantsEndRow) ? (i < ChannelUsersActivity.this.participants2StartRow || i >= ChannelUsersActivity.this.participants2EndRow) ? (i == ChannelUsersActivity.this.addNewSectionRow || i == ChannelUsersActivity.this.changeAddSectionRow || i == ChannelUsersActivity.this.participantsDividerRow) ? 3 : i == ChannelUsersActivity.this.participantsInfoRow ? 1 : (i == ChannelUsersActivity.this.changeAddHeaderRow || i == ChannelUsersActivity.this.restricted1SectionRow || i == ChannelUsersActivity.this.restricted2SectionRow) ? 5 : (i == ChannelUsersActivity.this.changeAddRadio1Row || i == ChannelUsersActivity.this.changeAddRadio2Row) ? 6 : i == ChannelUsersActivity.this.blockedEmptyRow ? 4 : 0 : 0 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            return itemViewType == 0 || itemViewType == 2 || itemViewType == 6;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            boolean z2 = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ManageChatUserCell manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                    manageChatUserCell.setTag(Integer.valueOf(i));
                    ChannelParticipant item = getItem(i);
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(item.user_id));
                    if (user == null) {
                        return;
                    }
                    CharSequence charSequence;
                    if (ChannelUsersActivity.this.type == 0) {
                        if (!(item instanceof TL_channelParticipantBanned) || MessagesController.getInstance().getUser(Integer.valueOf(item.kicked_by)) == null) {
                            charSequence = null;
                        } else {
                            charSequence = LocaleController.formatString("UserRestrictionsBy", R.string.UserRestrictionsBy, new Object[]{ContactsController.formatName(MessagesController.getInstance().getUser(Integer.valueOf(item.kicked_by)).first_name, MessagesController.getInstance().getUser(Integer.valueOf(item.kicked_by)).last_name)});
                        }
                        manageChatUserCell.setData(user, null, charSequence);
                        return;
                    } else if (ChannelUsersActivity.this.type == 1) {
                        if ((item instanceof TL_channelParticipantCreator) || (item instanceof TL_channelParticipantSelf)) {
                            charSequence = LocaleController.getString("ChannelCreator", R.string.ChannelCreator);
                        } else if (!(item instanceof TL_channelParticipantAdmin) || MessagesController.getInstance().getUser(Integer.valueOf(item.promoted_by)) == null) {
                            charSequence = null;
                        } else {
                            charSequence = LocaleController.formatString("EditAdminPromotedBy", R.string.EditAdminPromotedBy, new Object[]{ContactsController.formatName(MessagesController.getInstance().getUser(Integer.valueOf(item.promoted_by)).first_name, MessagesController.getInstance().getUser(Integer.valueOf(item.promoted_by)).last_name)});
                        }
                        manageChatUserCell.setData(user, null, charSequence);
                        return;
                    } else if (ChannelUsersActivity.this.type == 2) {
                        manageChatUserCell.setData(user, null, null);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i != ChannelUsersActivity.this.participantsInfoRow) {
                        return;
                    }
                    if (ChannelUsersActivity.this.type == 0) {
                        if (ChatObject.canBlockUsers(ChannelUsersActivity.this.currentChat)) {
                            if (ChannelUsersActivity.this.currentChat.megagroup) {
                                textInfoPrivacyCell.setText(String.format("%1$s\n\n%2$s", new Object[]{LocaleController.getString("NoBlockedGroup", R.string.NoBlockedGroup), LocaleController.getString("UnbanText", R.string.UnbanText)}));
                            } else {
                                textInfoPrivacyCell.setText(String.format("%1$s\n\n%2$s", new Object[]{LocaleController.getString("NoBlockedChannel", R.string.NoBlockedChannel), LocaleController.getString("UnbanText", R.string.UnbanText)}));
                            }
                        } else if (ChannelUsersActivity.this.currentChat.megagroup) {
                            textInfoPrivacyCell.setText(LocaleController.getString("NoBlockedGroup", R.string.NoBlockedGroup));
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("NoBlockedChannel", R.string.NoBlockedChannel));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (ChannelUsersActivity.this.type == 1) {
                        if (ChannelUsersActivity.this.addNewRow != -1) {
                            if (ChannelUsersActivity.this.currentChat.megagroup) {
                                textInfoPrivacyCell.setText(LocaleController.getString("MegaAdminsInfo", R.string.MegaAdminsInfo));
                            } else {
                                textInfoPrivacyCell.setText(LocaleController.getString("ChannelAdminsInfo", R.string.ChannelAdminsInfo));
                            }
                            textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            return;
                        }
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (ChannelUsersActivity.this.type == 2) {
                        if (ChannelUsersActivity.this.currentChat.megagroup || ChannelUsersActivity.this.selectType != 0) {
                            textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("ChannelMembersInfo", R.string.ChannelMembersInfo));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    ManageChatTextCell manageChatTextCell = (ManageChatTextCell) viewHolder.itemView;
                    if (i == ChannelUsersActivity.this.addNewRow) {
                        if (ChannelUsersActivity.this.type == 0) {
                            manageChatTextCell.setText(LocaleController.getString("ChannelBlockUser", R.string.ChannelBlockUser), null, R.drawable.group_ban_new, false);
                            return;
                        } else if (ChannelUsersActivity.this.type == 1) {
                            manageChatTextCell.setText(LocaleController.getString("ChannelAddAdmin", R.string.ChannelAddAdmin), null, R.drawable.group_admin_new, false);
                            return;
                        } else if (ChannelUsersActivity.this.type != 2) {
                            return;
                        } else {
                            if (!ChatObject.isChannel(ChannelUsersActivity.this.currentChat) || ChannelUsersActivity.this.currentChat.megagroup) {
                                manageChatTextCell.setText(LocaleController.getString("AddMember", R.string.AddMember), null, R.drawable.menu_invite, true);
                                return;
                            } else {
                                manageChatTextCell.setText(LocaleController.getString("AddSubscriber", R.string.AddSubscriber), null, R.drawable.menu_invite, true);
                                return;
                            }
                        }
                    } else if (i == ChannelUsersActivity.this.addNew2Row) {
                        manageChatTextCell.setText(LocaleController.getString("ChannelInviteViaLink", R.string.ChannelInviteViaLink), null, R.drawable.msg_panel_link, false);
                        return;
                    } else {
                        return;
                    }
                case 5:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == ChannelUsersActivity.this.restricted1SectionRow) {
                        headerCell.setText(LocaleController.getString("ChannelRestrictedUsers", R.string.ChannelRestrictedUsers));
                        return;
                    } else if (i == ChannelUsersActivity.this.restricted2SectionRow) {
                        headerCell.setText(LocaleController.getString("ChannelBlockedUsers", R.string.ChannelBlockedUsers));
                        return;
                    } else if (i == ChannelUsersActivity.this.changeAddHeaderRow) {
                        headerCell.setText(LocaleController.getString("WhoCanAddMembers", R.string.WhoCanAddMembers));
                        return;
                    } else {
                        return;
                    }
                case 6:
                    RadioCell radioCell = (RadioCell) viewHolder.itemView;
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ChannelUsersActivity.this.chatId));
                    String string;
                    if (i == ChannelUsersActivity.this.changeAddRadio1Row) {
                        radioCell.setTag(Integer.valueOf(0));
                        string = LocaleController.getString("WhoCanAddMembersAllMembers", R.string.WhoCanAddMembersAllMembers);
                        if (chat != null && chat.democracy) {
                            z = true;
                        }
                        radioCell.setText(string, z, true);
                        return;
                    } else if (i == ChannelUsersActivity.this.changeAddRadio2Row) {
                        radioCell.setTag(Integer.valueOf(1));
                        string = LocaleController.getString("WhoCanAddMembersAdmins", R.string.WhoCanAddMembersAdmins);
                        if (chat == null || chat.democracy) {
                            z2 = false;
                        }
                        radioCell.setText(string, z2, false);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View manageChatUserCell;
            boolean z = true;
            switch (i) {
                case 0:
                    Context context = this.mContext;
                    int i2 = ChannelUsersActivity.this.type == 0 ? 8 : 1;
                    if (ChannelUsersActivity.this.selectType != 0) {
                        z = false;
                    }
                    manageChatUserCell = new ManageChatUserCell(context, i2, z);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ManageChatUserCell) manageChatUserCell).setDelegate(new C42131());
                    break;
                case 1:
                    manageChatUserCell = new TextInfoPrivacyCell(this.mContext);
                    break;
                case 2:
                    manageChatUserCell = new ManageChatTextCell(this.mContext);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    manageChatUserCell = new ShadowSectionCell(this.mContext);
                    break;
                case 4:
                    View c42142 = new FrameLayout(this.mContext) {
                        protected void onMeasure(int i, int i2) {
                            super.onMeasure(i, MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(i2) - AndroidUtilities.dp(56.0f), 1073741824));
                        }
                    };
                    FrameLayout frameLayout = (FrameLayout) c42142;
                    frameLayout.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    View linearLayout = new LinearLayout(this.mContext);
                    linearLayout.setOrientation(1);
                    frameLayout.addView(linearLayout, LayoutHelper.createFrame(-2, -2.0f, 17, 20.0f, BitmapDescriptorFactory.HUE_RED, 20.0f, BitmapDescriptorFactory.HUE_RED));
                    manageChatUserCell = new ImageView(this.mContext);
                    manageChatUserCell.setImageResource(R.drawable.group_ban_empty);
                    manageChatUserCell.setScaleType(ScaleType.CENTER);
                    manageChatUserCell.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_emptyListPlaceholder), Mode.MULTIPLY));
                    linearLayout.addView(manageChatUserCell, LayoutHelper.createLinear(-2, -2, 1));
                    View textView = new TextView(this.mContext);
                    textView.setText(LocaleController.getString("NoBlockedUsers", R.string.NoBlockedUsers));
                    textView.setTextColor(Theme.getColor(Theme.key_emptyListPlaceholder));
                    textView.setTextSize(1, 16.0f);
                    textView.setGravity(1);
                    textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                    linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 1, 0, 10, 0, 0));
                    textView = new TextView(this.mContext);
                    if (ChannelUsersActivity.this.currentChat.megagroup) {
                        textView.setText(LocaleController.getString("NoBlockedGroup", R.string.NoBlockedGroup));
                    } else {
                        textView.setText(LocaleController.getString("NoBlockedChannel", R.string.NoBlockedChannel));
                    }
                    textView.setTextColor(Theme.getColor(Theme.key_emptyListPlaceholder));
                    textView.setTextSize(1, 15.0f);
                    textView.setGravity(1);
                    linearLayout.addView(textView, LayoutHelper.createLinear(-2, -2, 1, 0, 10, 0, 0));
                    c42142.setLayoutParams(new LayoutParams(-1, -1));
                    manageChatUserCell = c42142;
                    break;
                case 5:
                    manageChatUserCell = new HeaderCell(this.mContext);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    manageChatUserCell = new RadioCell(this.mContext);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(manageChatUserCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) viewHolder.itemView).recycle();
            }
        }
    }

    private class SearchAdapter extends SelectionAdapter {
        private int contactsStartRow;
        private int globalStartRow;
        private int group2StartRow;
        private int groupStartRow;
        private Context mContext;
        private SearchAdapterHelper searchAdapterHelper;
        private ArrayList<User> searchResult = new ArrayList();
        private ArrayList<CharSequence> searchResultNames = new ArrayList();
        private Timer searchTimer;
        private int totalCount;

        /* renamed from: org.telegram.ui.ChannelUsersActivity$SearchAdapter$5 */
        class C42205 implements ManageChatUserCellDelegate {
            C42205() {
            }

            public boolean onOptionsButtonCheck(ManageChatUserCell manageChatUserCell, boolean z) {
                boolean z2 = false;
                if (!(SearchAdapter.this.getItem(((Integer) manageChatUserCell.getTag()).intValue()) instanceof ChannelParticipant)) {
                    return false;
                }
                ChannelParticipant channelParticipant = (ChannelParticipant) SearchAdapter.this.getItem(((Integer) manageChatUserCell.getTag()).intValue());
                ChannelUsersActivity channelUsersActivity = ChannelUsersActivity.this;
                if (!z) {
                    z2 = true;
                }
                return channelUsersActivity.createMenuForParticipant(channelParticipant, z2);
            }
        }

        public SearchAdapter(Context context) {
            this.mContext = context;
            this.searchAdapterHelper = new SearchAdapterHelper();
            this.searchAdapterHelper.setDelegate(new SearchAdapterHelperDelegate(ChannelUsersActivity.this) {
                public void onDataSetChanged() {
                    SearchAdapter.this.notifyDataSetChanged();
                }

                public void onSetHashtags(ArrayList<HashtagObject> arrayList, HashMap<String, HashtagObject> hashMap) {
                }
            });
        }

        private void processSearch(final String str) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SearchAdapter.this.searchAdapterHelper.queryServerSearch(str, ChannelUsersActivity.this.selectType != 0, false, true, true, ChannelUsersActivity.this.chatId, ChannelUsersActivity.this.type == 0);
                    if (ChannelUsersActivity.this.selectType == 1) {
                        final ArrayList arrayList = new ArrayList();
                        arrayList.addAll(ContactsController.getInstance().contacts);
                        Utilities.searchQueue.postRunnable(new Runnable() {
                            public void run() {
                                String toLowerCase = str.trim().toLowerCase();
                                if (toLowerCase.length() == 0) {
                                    SearchAdapter.this.updateSearchResults(new ArrayList(), new ArrayList());
                                    return;
                                }
                                String translitString = LocaleController.getInstance().getTranslitString(toLowerCase);
                                String str = (toLowerCase.equals(translitString) || translitString.length() == 0) ? null : translitString;
                                String[] strArr = new String[((str != null ? 1 : 0) + 1)];
                                strArr[0] = toLowerCase;
                                if (str != null) {
                                    strArr[1] = str;
                                }
                                ArrayList arrayList = new ArrayList();
                                ArrayList arrayList2 = new ArrayList();
                                for (int i = 0; i < arrayList.size(); i++) {
                                    User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$TL_contact) arrayList.get(i)).user_id));
                                    if (user.id != UserConfig.getClientUserId()) {
                                        String toLowerCase2 = ContactsController.formatName(user.first_name, user.last_name).toLowerCase();
                                        translitString = LocaleController.getInstance().getTranslitString(toLowerCase2);
                                        if (toLowerCase2.equals(translitString)) {
                                            translitString = null;
                                        }
                                        int length = strArr.length;
                                        Object obj = null;
                                        int i2 = 0;
                                        while (i2 < length) {
                                            String str2 = strArr[i2];
                                            if (toLowerCase2.startsWith(str2) || toLowerCase2.contains(" " + str2) || (r0 != null && (r0.startsWith(str2) || r0.contains(" " + str2)))) {
                                                obj = 1;
                                            } else if (user.username != null && user.username.startsWith(str2)) {
                                                obj = 2;
                                            }
                                            if (r2 != null) {
                                                if (r2 == 1) {
                                                    arrayList2.add(AndroidUtilities.generateSearchName(user.first_name, user.last_name, str2));
                                                } else {
                                                    arrayList2.add(AndroidUtilities.generateSearchName("@" + user.username, null, "@" + str2));
                                                }
                                                arrayList.add(user);
                                            } else {
                                                i2++;
                                            }
                                        }
                                    }
                                }
                                SearchAdapter.this.updateSearchResults(arrayList, arrayList2);
                            }
                        });
                    }
                }
            });
        }

        private void updateSearchResults(final ArrayList<User> arrayList, final ArrayList<CharSequence> arrayList2) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    SearchAdapter.this.searchResult = arrayList;
                    SearchAdapter.this.searchResultNames = arrayList2;
                    SearchAdapter.this.notifyDataSetChanged();
                }
            });
        }

        public TLObject getItem(int i) {
            int size = this.searchAdapterHelper.getGroupSearch().size();
            if (size != 0) {
                if (size + 1 > i) {
                    return i == 0 ? null : (TLObject) this.searchAdapterHelper.getGroupSearch().get(i - 1);
                } else {
                    i -= size + 1;
                }
            }
            size = this.searchAdapterHelper.getGroupSearch2().size();
            if (size != 0) {
                if (size + 1 > i) {
                    return i != 0 ? (TLObject) this.searchAdapterHelper.getGroupSearch2().get(i - 1) : null;
                } else {
                    i -= size + 1;
                }
            }
            size = this.searchResult.size();
            if (size != 0) {
                if (size + 1 > i) {
                    return i != 0 ? (TLObject) this.searchResult.get(i - 1) : null;
                } else {
                    i -= size + 1;
                }
            }
            size = this.searchAdapterHelper.getGlobalSearch().size();
            return (size == 0 || size + 1 <= i || i == 0) ? null : (TLObject) this.searchAdapterHelper.getGlobalSearch().get(i - 1);
        }

        public int getItemCount() {
            int size = this.searchResult.size();
            int size2 = this.searchAdapterHelper.getGlobalSearch().size();
            int size3 = this.searchAdapterHelper.getGroupSearch().size();
            int size4 = this.searchAdapterHelper.getGroupSearch2().size();
            int i = 0;
            if (size != 0) {
                i = 0 + (size + 1);
            }
            if (size2 != 0) {
                i += size2 + 1;
            }
            if (size3 != 0) {
                i += size3 + 1;
            }
            return size4 != 0 ? i + (size4 + 1) : i;
        }

        public int getItemViewType(int i) {
            return (i == this.globalStartRow || i == this.groupStartRow || i == this.contactsStartRow || i == this.group2StartRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            return viewHolder.getItemViewType() != 1;
        }

        public void notifyDataSetChanged() {
            this.totalCount = 0;
            int size = this.searchAdapterHelper.getGroupSearch().size();
            if (size != 0) {
                this.groupStartRow = 0;
                this.totalCount = (size + 1) + this.totalCount;
            } else {
                this.groupStartRow = -1;
            }
            size = this.searchAdapterHelper.getGroupSearch2().size();
            if (size != 0) {
                this.group2StartRow = this.totalCount;
                this.totalCount = (size + 1) + this.totalCount;
            } else {
                this.group2StartRow = -1;
            }
            size = this.searchResult.size();
            if (size != 0) {
                this.contactsStartRow = this.totalCount;
                this.totalCount = (size + 1) + this.totalCount;
            } else {
                this.contactsStartRow = -1;
            }
            size = this.searchAdapterHelper.getGlobalSearch().size();
            if (size != 0) {
                this.globalStartRow = this.totalCount;
                this.totalCount = (size + 1) + this.totalCount;
            } else {
                this.globalStartRow = -1;
            }
            super.notifyDataSetChanged();
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            CharSequence charSequence = null;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    int i2;
                    String str;
                    String lastFoundChannel2;
                    int i3;
                    CharSequence charSequence2;
                    CharSequence charSequence3;
                    String lastFoundUsername;
                    String substring;
                    SpannableStringBuilder spannableStringBuilder;
                    Object userName;
                    int indexOf;
                    ManageChatUserCell manageChatUserCell;
                    TLObject item = getItem(i);
                    User user = item instanceof User ? (User) item : MessagesController.getInstance().getUser(Integer.valueOf(((ChannelParticipant) item).user_id));
                    CharSequence charSequence4 = user.username;
                    int size = this.searchAdapterHelper.getGroupSearch().size();
                    if (size == 0) {
                        size = 0;
                        i2 = i;
                        str = null;
                    } else if (size + 1 > i) {
                        str = this.searchAdapterHelper.getLastFoundChannel();
                        size = 1;
                        i2 = i;
                    } else {
                        i -= size + 1;
                        size = 0;
                        i2 = i;
                        str = null;
                    }
                    if (size == 0) {
                        int size2 = this.searchAdapterHelper.getGroupSearch2().size();
                        if (size2 != 0) {
                            if (size2 + 1 > i2) {
                                lastFoundChannel2 = this.searchAdapterHelper.getLastFoundChannel2();
                                i3 = i2;
                            } else {
                                i2 -= size2 + 1;
                                lastFoundChannel2 = str;
                                i3 = i2;
                            }
                            if (size == 0) {
                                i2 = this.searchResult.size();
                                if (i2 != 0) {
                                    if (i2 + 1 <= i3) {
                                        charSequence2 = (CharSequence) this.searchResultNames.get(i3 - 1);
                                        if (charSequence2 != null || charSequence4 == null || charSequence4.length() <= 0 || !charSequence2.toString().startsWith("@" + charSequence4)) {
                                            i2 = i3;
                                            charSequence3 = null;
                                            charSequence = charSequence2;
                                            size = 1;
                                        } else {
                                            i2 = i3;
                                            charSequence3 = charSequence2;
                                            size = 1;
                                        }
                                    } else {
                                        i2 = i3 - (i2 + 1);
                                        charSequence3 = null;
                                    }
                                    if (size == 0) {
                                        size = this.searchAdapterHelper.getGlobalSearch().size();
                                        if (size != 0 && size + 1 > i2) {
                                            lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                                            substring = lastFoundUsername.startsWith("@") ? lastFoundUsername.substring(1) : lastFoundUsername;
                                            try {
                                                spannableStringBuilder = new SpannableStringBuilder(charSequence4);
                                                spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                                                charSequence4 = spannableStringBuilder;
                                            } catch (Throwable e) {
                                                FileLog.e(e);
                                            }
                                            if (lastFoundChannel2 == null) {
                                                userName = UserObject.getUserName(user);
                                                charSequence3 = new SpannableStringBuilder(userName);
                                                indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                                if (indexOf != -1) {
                                                    ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                                }
                                            } else {
                                                charSequence3 = charSequence;
                                            }
                                            manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                                            manageChatUserCell.setTag(Integer.valueOf(i2));
                                            manageChatUserCell.setData(user, charSequence3, charSequence4);
                                            return;
                                        }
                                    }
                                    charSequence4 = charSequence3;
                                    if (lastFoundChannel2 == null) {
                                        charSequence3 = charSequence;
                                    } else {
                                        userName = UserObject.getUserName(user);
                                        charSequence3 = new SpannableStringBuilder(userName);
                                        indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                        if (indexOf != -1) {
                                            ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                        }
                                    }
                                    manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                                    manageChatUserCell.setTag(Integer.valueOf(i2));
                                    manageChatUserCell.setData(user, charSequence3, charSequence4);
                                    return;
                                }
                            }
                            i2 = i3;
                            charSequence3 = null;
                            if (size == 0) {
                                size = this.searchAdapterHelper.getGlobalSearch().size();
                                lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                                if (lastFoundUsername.startsWith("@")) {
                                }
                                spannableStringBuilder = new SpannableStringBuilder(charSequence4);
                                spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                                charSequence4 = spannableStringBuilder;
                                if (lastFoundChannel2 == null) {
                                    userName = UserObject.getUserName(user);
                                    charSequence3 = new SpannableStringBuilder(userName);
                                    indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                    if (indexOf != -1) {
                                        ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                    }
                                } else {
                                    charSequence3 = charSequence;
                                }
                                manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                                manageChatUserCell.setTag(Integer.valueOf(i2));
                                manageChatUserCell.setData(user, charSequence3, charSequence4);
                                return;
                            }
                            charSequence4 = charSequence3;
                            if (lastFoundChannel2 == null) {
                                charSequence3 = charSequence;
                            } else {
                                userName = UserObject.getUserName(user);
                                charSequence3 = new SpannableStringBuilder(userName);
                                indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                if (indexOf != -1) {
                                    ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                }
                            }
                            manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                            manageChatUserCell.setTag(Integer.valueOf(i2));
                            manageChatUserCell.setData(user, charSequence3, charSequence4);
                            return;
                        }
                    }
                    lastFoundChannel2 = str;
                    i3 = i2;
                    if (size == 0) {
                        i2 = this.searchResult.size();
                        if (i2 != 0) {
                            if (i2 + 1 <= i3) {
                                i2 = i3 - (i2 + 1);
                                charSequence3 = null;
                            } else {
                                charSequence2 = (CharSequence) this.searchResultNames.get(i3 - 1);
                                if (charSequence2 != null) {
                                }
                                i2 = i3;
                                charSequence3 = null;
                                charSequence = charSequence2;
                                size = 1;
                            }
                            if (size == 0) {
                                size = this.searchAdapterHelper.getGlobalSearch().size();
                                lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                                if (lastFoundUsername.startsWith("@")) {
                                }
                                spannableStringBuilder = new SpannableStringBuilder(charSequence4);
                                spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                                charSequence4 = spannableStringBuilder;
                                if (lastFoundChannel2 == null) {
                                    userName = UserObject.getUserName(user);
                                    charSequence3 = new SpannableStringBuilder(userName);
                                    indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                    if (indexOf != -1) {
                                        ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                    }
                                } else {
                                    charSequence3 = charSequence;
                                }
                                manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                                manageChatUserCell.setTag(Integer.valueOf(i2));
                                manageChatUserCell.setData(user, charSequence3, charSequence4);
                                return;
                            }
                            charSequence4 = charSequence3;
                            if (lastFoundChannel2 == null) {
                                charSequence3 = charSequence;
                            } else {
                                userName = UserObject.getUserName(user);
                                charSequence3 = new SpannableStringBuilder(userName);
                                indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                                if (indexOf != -1) {
                                    ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                                }
                            }
                            manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                            manageChatUserCell.setTag(Integer.valueOf(i2));
                            manageChatUserCell.setData(user, charSequence3, charSequence4);
                            return;
                        }
                    }
                    i2 = i3;
                    charSequence3 = null;
                    if (size == 0) {
                        size = this.searchAdapterHelper.getGlobalSearch().size();
                        lastFoundUsername = this.searchAdapterHelper.getLastFoundUsername();
                        if (lastFoundUsername.startsWith("@")) {
                        }
                        spannableStringBuilder = new SpannableStringBuilder(charSequence4);
                        spannableStringBuilder.setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), 0, substring.length(), 33);
                        charSequence4 = spannableStringBuilder;
                        if (lastFoundChannel2 == null) {
                            userName = UserObject.getUserName(user);
                            charSequence3 = new SpannableStringBuilder(userName);
                            indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                            if (indexOf != -1) {
                                ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                            }
                        } else {
                            charSequence3 = charSequence;
                        }
                        manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                        manageChatUserCell.setTag(Integer.valueOf(i2));
                        manageChatUserCell.setData(user, charSequence3, charSequence4);
                        return;
                    }
                    charSequence4 = charSequence3;
                    if (lastFoundChannel2 == null) {
                        charSequence3 = charSequence;
                    } else {
                        userName = UserObject.getUserName(user);
                        charSequence3 = new SpannableStringBuilder(userName);
                        indexOf = userName.toLowerCase().indexOf(lastFoundChannel2);
                        if (indexOf != -1) {
                            ((SpannableStringBuilder) charSequence3).setSpan(new ForegroundColorSpan(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4)), indexOf, lastFoundChannel2.length() + indexOf, 33);
                        }
                    }
                    manageChatUserCell = (ManageChatUserCell) viewHolder.itemView;
                    manageChatUserCell.setTag(Integer.valueOf(i2));
                    manageChatUserCell.setData(user, charSequence3, charSequence4);
                    return;
                case 1:
                    GraySectionCell graySectionCell = (GraySectionCell) viewHolder.itemView;
                    if (i == this.groupStartRow) {
                        if (ChannelUsersActivity.this.type == 0) {
                            graySectionCell.setText(LocaleController.getString("ChannelRestrictedUsers", R.string.ChannelRestrictedUsers).toUpperCase());
                            return;
                        } else if (!ChatObject.isChannel(ChannelUsersActivity.this.currentChat) || ChannelUsersActivity.this.currentChat.megagroup) {
                            graySectionCell.setText(LocaleController.getString("ChannelMembers", R.string.ChannelMembers).toUpperCase());
                            return;
                        } else {
                            ChannelUsersActivity.this.actionBar.setTitle(LocaleController.getString("ChannelSubscribers", R.string.ChannelSubscribers));
                            return;
                        }
                    } else if (i == this.group2StartRow) {
                        graySectionCell.setText(LocaleController.getString("ChannelBlockedUsers", R.string.ChannelBlockedUsers).toUpperCase());
                        return;
                    } else if (i == this.globalStartRow) {
                        graySectionCell.setText(LocaleController.getString("GlobalSearch", R.string.GlobalSearch).toUpperCase());
                        return;
                    } else if (i == this.contactsStartRow) {
                        graySectionCell.setText(LocaleController.getString("Contacts", R.string.Contacts).toUpperCase());
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View manageChatUserCell;
            switch (i) {
                case 0:
                    manageChatUserCell = new ManageChatUserCell(this.mContext, 2, ChannelUsersActivity.this.selectType == 0);
                    manageChatUserCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    ((ManageChatUserCell) manageChatUserCell).setDelegate(new C42205());
                    break;
                default:
                    manageChatUserCell = new GraySectionCell(this.mContext);
                    break;
            }
            return new Holder(manageChatUserCell);
        }

        public void onViewRecycled(ViewHolder viewHolder) {
            if (viewHolder.itemView instanceof ManageChatUserCell) {
                ((ManageChatUserCell) viewHolder.itemView).recycle();
            }
        }

        public void searchDialogs(final String str) {
            try {
                if (this.searchTimer != null) {
                    this.searchTimer.cancel();
                }
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (str == null) {
                this.searchResult.clear();
                this.searchResultNames.clear();
                this.searchAdapterHelper.queryServerSearch(null, ChannelUsersActivity.this.type != 0, false, true, true, ChannelUsersActivity.this.chatId, ChannelUsersActivity.this.type == 0);
                notifyDataSetChanged();
                return;
            }
            this.searchTimer = new Timer();
            this.searchTimer.schedule(new TimerTask() {
                public void run() {
                    try {
                        SearchAdapter.this.searchTimer.cancel();
                        SearchAdapter.this.searchTimer = null;
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    SearchAdapter.this.processSearch(str);
                }
            }, 200, 300);
        }
    }

    public ChannelUsersActivity(Bundle bundle) {
        super(bundle);
    }

    private boolean createMenuForParticipant(final ChannelParticipant channelParticipant, boolean z) {
        ArrayList arrayList = null;
        if (channelParticipant == null || this.selectType != 0) {
            return false;
        }
        if (channelParticipant.user_id == UserConfig.getClientUserId()) {
            return false;
        }
        if (this.type == 2) {
            ArrayList arrayList2;
            final User user = MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant.user_id));
            int i = ((channelParticipant instanceof TL_channelParticipant) || (channelParticipant instanceof TL_channelParticipantBanned)) ? true : 0;
            int i2 = (((channelParticipant instanceof TL_channelParticipantAdmin) || (channelParticipant instanceof TL_channelParticipantCreator)) && !channelParticipant.can_edit) ? 0 : true;
            if (z) {
                arrayList2 = null;
            } else {
                arrayList2 = new ArrayList();
                arrayList = new ArrayList();
            }
            if (i != 0 && ChatObject.canAddAdmins(this.currentChat)) {
                if (z) {
                    return true;
                }
                arrayList2.add(LocaleController.getString("SetAsAdmin", R.string.SetAsAdmin));
                arrayList.add(Integer.valueOf(0));
            }
            if (ChatObject.canBlockUsers(this.currentChat) && i2 != 0) {
                if (z) {
                    return true;
                }
                if (this.currentChat.megagroup) {
                    arrayList2.add(LocaleController.getString("KickFromSupergroup", R.string.KickFromSupergroup));
                    arrayList.add(Integer.valueOf(1));
                    arrayList2.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
                    arrayList.add(Integer.valueOf(2));
                } else {
                    arrayList2.add(LocaleController.getString("ChannelRemoveUser", R.string.ChannelRemoveUser));
                    arrayList.add(Integer.valueOf(2));
                }
            }
            if (arrayList == null || arrayList.isEmpty()) {
                return false;
            }
            Builder builder = new Builder(getParentActivity());
            builder.setItems((CharSequence[]) arrayList2.toArray(new CharSequence[arrayList.size()]), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, final int i) {
                    if (((Integer) arrayList.get(i)).intValue() == 2) {
                        MessagesController.getInstance().deleteUserFromChat(ChannelUsersActivity.this.chatId, user, null);
                        for (int i2 = 0; i2 < ChannelUsersActivity.this.participants.size(); i2++) {
                            if (((ChannelParticipant) ChannelUsersActivity.this.participants.get(i2)).user_id == channelParticipant.user_id) {
                                ChannelUsersActivity.this.participants.remove(i2);
                                ChannelUsersActivity.this.updateRows();
                                ChannelUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                                return;
                            }
                        }
                        return;
                    }
                    BaseFragment channelRightsEditActivity = new ChannelRightsEditActivity(user.id, ChannelUsersActivity.this.chatId, channelParticipant.admin_rights, channelParticipant.banned_rights, ((Integer) arrayList.get(i)).intValue(), true);
                    channelRightsEditActivity.setDelegate(new ChannelRightsEditActivityDelegate() {
                        public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                            int i2 = 0;
                            if (((Integer) arrayList.get(i)).intValue() == 0) {
                                while (i2 < ChannelUsersActivity.this.participants.size()) {
                                    if (((ChannelParticipant) ChannelUsersActivity.this.participants.get(i2)).user_id == channelParticipant.user_id) {
                                        ChannelParticipant tL_channelParticipantAdmin = i == 1 ? new TL_channelParticipantAdmin() : new TL_channelParticipant();
                                        tL_channelParticipantAdmin.admin_rights = tL_channelAdminRights;
                                        tL_channelParticipantAdmin.banned_rights = tL_channelBannedRights;
                                        tL_channelParticipantAdmin.inviter_id = UserConfig.getClientUserId();
                                        tL_channelParticipantAdmin.user_id = channelParticipant.user_id;
                                        tL_channelParticipantAdmin.date = channelParticipant.date;
                                        ChannelUsersActivity.this.participants.set(i2, tL_channelParticipantAdmin);
                                        return;
                                    }
                                    i2++;
                                }
                            } else if (((Integer) arrayList.get(i)).intValue() == 1 && i == 0) {
                                while (i2 < ChannelUsersActivity.this.participants.size()) {
                                    if (((ChannelParticipant) ChannelUsersActivity.this.participants.get(i2)).user_id == channelParticipant.user_id) {
                                        ChannelUsersActivity.this.participants.remove(i2);
                                        ChannelUsersActivity.this.updateRows();
                                        ChannelUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                                        return;
                                    }
                                    i2++;
                                }
                            }
                        }
                    });
                    ChannelUsersActivity.this.presentFragment(channelRightsEditActivity);
                }
            });
            showDialog(builder.create());
            return true;
        }
        CharSequence[] charSequenceArr;
        if (this.type == 0 && ChatObject.canBlockUsers(this.currentChat)) {
            if (z) {
                return true;
            }
            charSequenceArr = new CharSequence[]{LocaleController.getString("Unban", R.string.Unban)};
        } else if (this.type != 1 || !ChatObject.canAddAdmins(this.currentChat) || !channelParticipant.can_edit) {
            charSequenceArr = null;
        } else if (z) {
            return true;
        } else {
            charSequenceArr = new CharSequence[]{LocaleController.getString("ChannelRemoveUserAdmin", R.string.ChannelRemoveUserAdmin)};
        }
        if (charSequenceArr == null) {
            return false;
        }
        builder = new Builder(getParentActivity());
        builder.setItems(charSequenceArr, new OnClickListener() {

            /* renamed from: org.telegram.ui.ChannelUsersActivity$7$1 */
            class C42061 implements RequestDelegate {
                C42061() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    if (tLObject != null) {
                        final TLRPC$Updates tLRPC$Updates = (TLRPC$Updates) tLObject;
                        MessagesController.getInstance().processUpdates(tLRPC$Updates, false);
                        if (!tLRPC$Updates.chats.isEmpty()) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    MessagesController.getInstance().loadFullChat(((Chat) tLRPC$Updates.chats.get(0)).id, 0, true);
                                }
                            }, 1000);
                        }
                    }
                }
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i != 0) {
                    return;
                }
                if (ChannelUsersActivity.this.type == 0) {
                    ChannelUsersActivity.this.participants.remove(channelParticipant);
                    ChannelUsersActivity.this.updateRows();
                    ChannelUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                    TLObject tL_channels_editBanned = new TL_channels_editBanned();
                    tL_channels_editBanned.user_id = MessagesController.getInputUser(channelParticipant.user_id);
                    tL_channels_editBanned.channel = MessagesController.getInputChannel(ChannelUsersActivity.this.chatId);
                    tL_channels_editBanned.banned_rights = new TL_channelBannedRights();
                    ConnectionsManager.getInstance().sendRequest(tL_channels_editBanned, new C42061());
                } else if (ChannelUsersActivity.this.type == 1) {
                    MessagesController.setUserAdminRole(ChannelUsersActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant.user_id)), new TL_channelAdminRights(), ChannelUsersActivity.this.currentChat.megagroup, ChannelUsersActivity.this);
                } else if (ChannelUsersActivity.this.type == 2) {
                    MessagesController.getInstance().deleteUserFromChat(ChannelUsersActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant.user_id)), null);
                }
            }
        });
        showDialog(builder.create());
        return true;
    }

    private int getChannelAdminParticipantType(ChannelParticipant channelParticipant) {
        return ((channelParticipant instanceof TL_channelParticipantCreator) || (channelParticipant instanceof TL_channelParticipantSelf)) ? 0 : channelParticipant instanceof TL_channelParticipantAdmin ? 1 : 2;
    }

    private void getChannelParticipants(int i, int i2) {
        if (!this.loadingUsers) {
            this.loadingUsers = true;
            if (!(this.emptyView == null || this.firstLoaded)) {
                this.emptyView.showProgress();
            }
            if (this.listViewAdapter != null) {
                this.listViewAdapter.notifyDataSetChanged();
            }
            TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            tL_channels_getParticipants.channel = MessagesController.getInputChannel(this.chatId);
            final boolean z = this.firstEndReached;
            if (this.type == 0) {
                if (z) {
                    tL_channels_getParticipants.filter = new TL_channelParticipantsKicked();
                } else {
                    tL_channels_getParticipants.filter = new TL_channelParticipantsBanned();
                }
            } else if (this.type == 1) {
                tL_channels_getParticipants.filter = new TL_channelParticipantsAdmins();
            } else if (this.type == 2) {
                tL_channels_getParticipants.filter = new TL_channelParticipantsRecent();
            }
            tL_channels_getParticipants.filter.f10136q = TtmlNode.ANONYMOUS_REGION_ID;
            tL_channels_getParticipants.offset = i;
            tL_channels_getParticipants.limit = i2;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.ChannelUsersActivity$9$1$1 */
                        class C42091 implements Comparator<ChannelParticipant> {
                            C42091() {
                            }

                            public int compare(ChannelParticipant channelParticipant, ChannelParticipant channelParticipant2) {
                                User user = MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant2.user_id));
                                User user2 = MessagesController.getInstance().getUser(Integer.valueOf(channelParticipant.user_id));
                                int currentTime = (user == null || user.status == null) ? 0 : user.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user.status.expires;
                                int currentTime2 = (user2 == null || user2.status == null) ? 0 : user2.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user2.status.expires;
                                return (currentTime <= 0 || currentTime2 <= 0) ? (currentTime >= 0 || currentTime2 >= 0) ? ((currentTime >= 0 || currentTime2 <= 0) && (currentTime != 0 || currentTime2 == 0)) ? (currentTime2 >= 0 || currentTime <= 0) ? (currentTime2 != 0 || currentTime == 0) ? 0 : 1 : 1 : -1 : currentTime <= currentTime2 ? currentTime < currentTime2 ? -1 : 0 : 1 : currentTime > currentTime2 ? 1 : currentTime < currentTime2 ? -1 : 0;
                            }
                        }

                        /* renamed from: org.telegram.ui.ChannelUsersActivity$9$1$2 */
                        class C42102 implements Comparator<ChannelParticipant> {
                            C42102() {
                            }

                            public int compare(ChannelParticipant channelParticipant, ChannelParticipant channelParticipant2) {
                                int access$2300 = ChannelUsersActivity.this.getChannelAdminParticipantType(channelParticipant);
                                int access$23002 = ChannelUsersActivity.this.getChannelAdminParticipantType(channelParticipant2);
                                return access$2300 > access$23002 ? 1 : access$2300 < access$23002 ? -1 : 0;
                            }
                        }

                        public void run() {
                            int i = 0;
                            boolean z = !ChannelUsersActivity.this.firstLoaded;
                            ChannelUsersActivity.this.loadingUsers = false;
                            ChannelUsersActivity.this.firstLoaded = true;
                            if (ChannelUsersActivity.this.emptyView != null) {
                                ChannelUsersActivity.this.emptyView.showTextView();
                            }
                            if (tLRPC$TL_error == null) {
                                TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                int clientUserId = UserConfig.getClientUserId();
                                if (ChannelUsersActivity.this.selectType != 0) {
                                    for (int i2 = 0; i2 < tL_channels_channelParticipants.participants.size(); i2++) {
                                        if (((ChannelParticipant) tL_channels_channelParticipants.participants.get(i2)).user_id == clientUserId) {
                                            tL_channels_channelParticipants.participants.remove(i2);
                                            break;
                                        }
                                    }
                                }
                                if (ChannelUsersActivity.this.type != 0) {
                                    ChannelUsersActivity.this.participantsMap.clear();
                                    ChannelUsersActivity.this.participants = tL_channels_channelParticipants.participants;
                                } else if (z) {
                                    ChannelUsersActivity.this.participants2 = tL_channels_channelParticipants.participants;
                                } else {
                                    ChannelUsersActivity.this.participants2 = new ArrayList();
                                    ChannelUsersActivity.this.participantsMap.clear();
                                    ChannelUsersActivity.this.participants = tL_channels_channelParticipants.participants;
                                    if (z) {
                                        ChannelUsersActivity.this.firstLoaded = false;
                                    }
                                    ChannelUsersActivity.this.firstEndReached = true;
                                    ChannelUsersActivity.this.getChannelParticipants(0, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                                }
                                while (i < tL_channels_channelParticipants.participants.size()) {
                                    ChannelParticipant channelParticipant = (ChannelParticipant) tL_channels_channelParticipants.participants.get(i);
                                    ChannelUsersActivity.this.participantsMap.put(Integer.valueOf(channelParticipant.user_id), channelParticipant);
                                    i++;
                                }
                                try {
                                    if (ChannelUsersActivity.this.type == 0 || ChannelUsersActivity.this.type == 2) {
                                        Collections.sort(tL_channels_channelParticipants.participants, new C42091());
                                    } else if (ChannelUsersActivity.this.type == 1) {
                                        Collections.sort(tL_channels_channelParticipants.participants, new C42102());
                                    }
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                            }
                            ChannelUsersActivity.this.updateRows();
                            if (ChannelUsersActivity.this.listViewAdapter != null) {
                                ChannelUsersActivity.this.listViewAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }), this.classGuid);
        }
    }

    private void updateRows() {
        this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (this.currentChat != null) {
            this.changeAddHeaderRow = -1;
            this.changeAddRadio1Row = -1;
            this.changeAddRadio2Row = -1;
            this.changeAddSectionRow = -1;
            this.addNewRow = -1;
            this.addNew2Row = -1;
            this.addNewSectionRow = -1;
            this.restricted1SectionRow = -1;
            this.participantsStartRow = -1;
            this.participantsDividerRow = -1;
            this.participantsEndRow = -1;
            this.restricted2SectionRow = -1;
            this.participants2StartRow = -1;
            this.participants2EndRow = -1;
            this.participantsInfoRow = -1;
            this.blockedEmptyRow = -1;
            this.rowCount = 0;
            int i;
            if (this.type == 0) {
                if (ChatObject.canBlockUsers(this.currentChat)) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.addNewRow = i;
                    if (!(this.participants.isEmpty() && this.participants2.isEmpty())) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.addNewSectionRow = i;
                    }
                } else {
                    this.addNewRow = -1;
                    this.addNewSectionRow = -1;
                }
                if (!this.participants.isEmpty()) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.restricted1SectionRow = i;
                    this.participantsStartRow = this.rowCount;
                    this.rowCount += this.participants.size();
                    this.participantsEndRow = this.rowCount;
                }
                if (!this.participants2.isEmpty()) {
                    if (this.restricted1SectionRow != -1) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.participantsDividerRow = i;
                    }
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.restricted2SectionRow = i;
                    this.participants2StartRow = this.rowCount;
                    this.rowCount += this.participants2.size();
                    this.participants2EndRow = this.rowCount;
                }
                if (this.participantsStartRow == -1 && this.participants2StartRow == -1) {
                    if (this.searchItem != null) {
                        this.searchItem.setVisibility(4);
                    }
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.blockedEmptyRow = i;
                    return;
                }
                if (this.searchItem != null) {
                    this.searchItem.setVisibility(0);
                }
                i = this.rowCount;
                this.rowCount = i + 1;
                this.participantsInfoRow = i;
            } else if (this.type == 1) {
                if (this.currentChat.creator && this.currentChat.megagroup) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.changeAddHeaderRow = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.changeAddRadio1Row = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.changeAddRadio2Row = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.changeAddSectionRow = i;
                }
                if (ChatObject.canAddAdmins(this.currentChat)) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.addNewRow = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.addNewSectionRow = i;
                } else {
                    this.addNewRow = -1;
                    this.addNewSectionRow = -1;
                }
                if (this.participants.isEmpty()) {
                    this.participantsStartRow = -1;
                    this.participantsEndRow = -1;
                } else {
                    this.participantsStartRow = this.rowCount;
                    this.rowCount += this.participants.size();
                    this.participantsEndRow = this.rowCount;
                }
                i = this.rowCount;
                this.rowCount = i + 1;
                this.participantsInfoRow = i;
            } else if (this.type == 2) {
                if (this.selectType == 0 && !this.currentChat.megagroup && ChatObject.canAddUsers(this.currentChat)) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.addNewRow = i;
                    if ((this.currentChat.flags & 64) == 0 && ChatObject.canAddViaLink(this.currentChat)) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.addNew2Row = i;
                    }
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.addNewSectionRow = i;
                }
                if (this.participants.isEmpty()) {
                    this.participantsStartRow = -1;
                    this.participantsEndRow = -1;
                } else {
                    this.participantsStartRow = this.rowCount;
                    this.rowCount += this.participants.size();
                    this.participantsEndRow = this.rowCount;
                }
                if (this.rowCount != 0) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.participantsInfoRow = i;
                }
            }
        }
    }

    public View createView(Context context) {
        int i = 1;
        this.searching = false;
        this.searchWas = false;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.type == 0) {
            this.actionBar.setTitle(LocaleController.getString("ChannelBlacklist", R.string.ChannelBlacklist));
        } else if (this.type == 1) {
            this.actionBar.setTitle(LocaleController.getString("ChannelAdministrators", R.string.ChannelAdministrators));
        } else if (this.type == 2) {
            if (this.selectType == 0) {
                if (!ChatObject.isChannel(this.currentChat) || this.currentChat.megagroup) {
                    this.actionBar.setTitle(LocaleController.getString("ChannelMembers", R.string.ChannelMembers));
                } else {
                    this.actionBar.setTitle(LocaleController.getString("ChannelSubscribers", R.string.ChannelSubscribers));
                }
            } else if (this.selectType == 1) {
                this.actionBar.setTitle(LocaleController.getString("ChannelAddAdmin", R.string.ChannelAddAdmin));
            } else if (this.selectType == 2) {
                this.actionBar.setTitle(LocaleController.getString("ChannelBlockUser", R.string.ChannelBlockUser));
            }
        }
        this.actionBar.setActionBarMenuOnItemClick(new C41951());
        if (this.selectType != 0 || this.type == 2 || this.type == 0) {
            this.searchListViewAdapter = new SearchAdapter(context);
            this.searchItem = this.actionBar.createMenu().addItem(0, (int) R.drawable.ic_ab_search).setIsSearchField(true).setActionBarMenuItemSearchListener(new C41962());
            this.searchItem.getSearchField().setHint(LocaleController.getString("Search", R.string.Search));
        }
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.emptyView = new EmptyTextProgressView(context);
        if (this.type == 0 || this.type == 2) {
            this.emptyView.setText(LocaleController.getString("NoResult", R.string.NoResult));
        }
        frameLayout.addView(this.emptyView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView = new RecyclerListView(context);
        this.listView.setEmptyView(this.emptyView);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.listViewAdapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        recyclerListView = this.listView;
        if (!LocaleController.isRTL) {
            i = 2;
        }
        recyclerListView.setVerticalScrollbarPosition(i);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setOnItemClickListener(new C42003());
        this.listView.setOnItemLongClickListener(new C42014());
        if (this.searchItem != null) {
            this.listView.setOnScrollListener(new C42025());
        }
        if (this.loadingUsers) {
            this.emptyView.showProgress();
        } else {
            this.emptyView.showTextView();
        }
        updateRows();
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
            if (chatFull.id == this.chatId && !booleanValue) {
                AndroidUtilities.runOnUIThread(new C42088());
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass10 anonymousClass10 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = ChannelUsersActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = ChannelUsersActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof ManageChatUserCell) {
                        ((ManageChatUserCell) childAt).update(0);
                    }
                }
            }
        };
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[32];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ManageChatUserCell.class, TextSettingsCell.class, ManageChatTextCell.class, RadioCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
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
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueImageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) anonymousClass10, Theme.key_windowBackgroundWhiteGrayText);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) anonymousClass10, Theme.key_windowBackgroundWhiteBlueText);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatUserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        themeDescriptionArr[23] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundRed);
        themeDescriptionArr[24] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundOrange);
        themeDescriptionArr[25] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundViolet);
        themeDescriptionArr[26] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundGreen);
        themeDescriptionArr[27] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundCyan);
        themeDescriptionArr[28] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundBlue);
        themeDescriptionArr[29] = new ThemeDescription(null, 0, null, null, null, anonymousClass10, Theme.key_avatar_backgroundPink);
        themeDescriptionArr[30] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatTextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[31] = new ThemeDescription(this.listView, 0, new Class[]{ManageChatTextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        getChannelParticipants(0, Callback.DEFAULT_DRAG_ANIMATION_DURATION);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && !z2 && this.needOpenSearch) {
            this.searchItem.openSearch(true);
        }
    }
}
