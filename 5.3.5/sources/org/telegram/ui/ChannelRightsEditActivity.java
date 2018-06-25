package org.telegram.ui;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnShowListener;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import java.util.Calendar;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$TL_channelAdminRights;
import org.telegram.tgnet.TLRPC$TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell2;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ChannelRightsEditActivity extends BaseFragment {
    private static final int done_button = 1;
    private int addAdminsRow;
    private int addUsersRow;
    private TLRPC$TL_channelAdminRights adminRights;
    private int banUsersRow;
    private TLRPC$TL_channelBannedRights bannedRights;
    private boolean canEdit;
    private int cantEditInfoRow;
    private int changeInfoRow;
    private int chatId;
    private int currentType;
    private User currentUser;
    private ChannelRightsEditActivityDelegate delegate;
    private int deleteMessagesRow;
    private int editMesagesRow;
    private int embedLinksRow;
    private boolean isDemocracy;
    private boolean isMegagroup;
    private RecyclerListView listView;
    private ListAdapter listViewAdapter;
    private TLRPC$TL_channelAdminRights myAdminRights;
    private int pinMessagesRow;
    private int postMessagesRow;
    private int removeAdminRow;
    private int removeAdminShadowRow;
    private int rightsShadowRow;
    private int rowCount;
    private int sendMediaRow;
    private int sendMessagesRow;
    private int sendStickersRow;
    private int untilDateRow;
    private int viewMessagesRow;

    public interface ChannelRightsEditActivityDelegate {
        void didSetRights(int i, TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights, TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights);
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$1 */
    class C23471 extends ActionBarMenuOnItemClick {
        C23471() {
        }

        public void onItemClick(int id) {
            int i = 0;
            if (id == -1) {
                ChannelRightsEditActivity.this.finishFragment();
            } else if (id == 1) {
                if (ChannelRightsEditActivity.this.currentType == 0) {
                    TLRPC$TL_channelAdminRights access$200;
                    if (ChannelRightsEditActivity.this.isMegagroup) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        ChannelRightsEditActivity.this.adminRights.edit_messages = false;
                        access$200.post_messages = false;
                    } else {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        ChannelRightsEditActivity.this.adminRights.ban_users = false;
                        access$200.pin_messages = false;
                    }
                    MessagesController.setUserAdminRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(1));
                    if (ChannelRightsEditActivity.this.delegate != null) {
                        ChannelRightsEditActivityDelegate access$500 = ChannelRightsEditActivity.this.delegate;
                        if (ChannelRightsEditActivity.this.adminRights.change_info || ChannelRightsEditActivity.this.adminRights.post_messages || ChannelRightsEditActivity.this.adminRights.edit_messages || ChannelRightsEditActivity.this.adminRights.delete_messages || ChannelRightsEditActivity.this.adminRights.ban_users || ChannelRightsEditActivity.this.adminRights.invite_users || ChannelRightsEditActivity.this.adminRights.invite_link || ChannelRightsEditActivity.this.adminRights.pin_messages || ChannelRightsEditActivity.this.adminRights.add_admins) {
                            i = 1;
                        }
                        access$500.didSetRights(i, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.bannedRights);
                    }
                } else if (ChannelRightsEditActivity.this.currentType == 1) {
                    int rights;
                    MessagesController.setUserBannedRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, ChannelRightsEditActivity.this.bannedRights, ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(1));
                    if (ChannelRightsEditActivity.this.bannedRights.view_messages) {
                        rights = 0;
                    } else if (ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_stickers || ChannelRightsEditActivity.this.bannedRights.embed_links || ChannelRightsEditActivity.this.bannedRights.send_media || ChannelRightsEditActivity.this.bannedRights.send_gifs || ChannelRightsEditActivity.this.bannedRights.send_games || ChannelRightsEditActivity.this.bannedRights.send_inline) {
                        rights = 1;
                    } else {
                        ChannelRightsEditActivity.this.bannedRights.until_date = 0;
                        rights = 2;
                    }
                    if (ChannelRightsEditActivity.this.delegate != null) {
                        ChannelRightsEditActivity.this.delegate.didSetRights(rights, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.bannedRights);
                    }
                }
                ChannelRightsEditActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3 */
    class C23553 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$1 */
        class C23511 implements OnDateSetListener {

            /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$1$2 */
            class C23502 implements OnClickListener {
                C23502() {
                }

                public void onClick(DialogInterface dialog, int which) {
                }
            }

            C23511() {
            }

            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.set(year, month, dayOfMonth);
                final int time = (int) (calendar.getTime().getTime() / 1000);
                try {
                    TimePickerDialog dialog = new TimePickerDialog(ChannelRightsEditActivity.this.getParentActivity(), new OnTimeSetListener() {
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            ChannelRightsEditActivity.this.bannedRights.until_date = (time + (hourOfDay * 3600)) + (minute * 60);
                            ChannelRightsEditActivity.this.listViewAdapter.notifyItemChanged(ChannelRightsEditActivity.this.untilDateRow);
                        }
                    }, 0, 0, true);
                    dialog.setButton(-1, LocaleController.getString("Set", R.string.Set), dialog);
                    dialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C23502());
                    ChannelRightsEditActivity.this.showDialog(dialog);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$2 */
        class C23522 implements OnClickListener {
            C23522() {
            }

            public void onClick(DialogInterface dialog, int which) {
                ChannelRightsEditActivity.this.bannedRights.until_date = 0;
                ChannelRightsEditActivity.this.listViewAdapter.notifyItemChanged(ChannelRightsEditActivity.this.untilDateRow);
            }
        }

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$3 */
        class C23533 implements OnClickListener {
            C23533() {
            }

            public void onClick(DialogInterface dialog, int which) {
            }
        }

        C23553() {
        }

        public void onItemClick(View view, int position) {
            if (!ChannelRightsEditActivity.this.canEdit) {
                return;
            }
            if (position == 0) {
                Bundle args = new Bundle();
                args.putInt("user_id", ChannelRightsEditActivity.this.currentUser.id);
                ChannelRightsEditActivity.this.presentFragment(new ProfileActivity(args));
            } else if (position == ChannelRightsEditActivity.this.removeAdminRow) {
                if (ChannelRightsEditActivity.this.currentType == 0) {
                    MessagesController.setUserAdminRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, new TLRPC$TL_channelAdminRights(), ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(0));
                } else if (ChannelRightsEditActivity.this.currentType == 1) {
                    ChannelRightsEditActivity.this.bannedRights = new TLRPC$TL_channelBannedRights();
                    ChannelRightsEditActivity.this.bannedRights.view_messages = true;
                    ChannelRightsEditActivity.this.bannedRights.send_media = true;
                    ChannelRightsEditActivity.this.bannedRights.send_messages = true;
                    ChannelRightsEditActivity.this.bannedRights.send_stickers = true;
                    ChannelRightsEditActivity.this.bannedRights.send_gifs = true;
                    ChannelRightsEditActivity.this.bannedRights.send_games = true;
                    ChannelRightsEditActivity.this.bannedRights.send_inline = true;
                    ChannelRightsEditActivity.this.bannedRights.embed_links = true;
                    ChannelRightsEditActivity.this.bannedRights.until_date = 0;
                    MessagesController.setUserBannedRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, ChannelRightsEditActivity.this.bannedRights, ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(0));
                }
                if (ChannelRightsEditActivity.this.delegate != null) {
                    ChannelRightsEditActivity.this.delegate.didSetRights(0, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.bannedRights);
                }
                ChannelRightsEditActivity.this.finishFragment();
            } else if (position == ChannelRightsEditActivity.this.untilDateRow) {
                if (ChannelRightsEditActivity.this.getParentActivity() != null) {
                    Calendar calendar = Calendar.getInstance();
                    try {
                        DatePickerDialog dialog = new DatePickerDialog(ChannelRightsEditActivity.this.getParentActivity(), new C23511(), calendar.get(1), calendar.get(2), calendar.get(5));
                        final DatePicker datePicker = dialog.getDatePicker();
                        Calendar date = Calendar.getInstance();
                        date.setTimeInMillis(System.currentTimeMillis());
                        date.set(11, date.getMinimum(11));
                        date.set(12, date.getMinimum(12));
                        date.set(13, date.getMinimum(13));
                        date.set(14, date.getMinimum(14));
                        datePicker.setMinDate(date.getTimeInMillis());
                        date.setTimeInMillis(System.currentTimeMillis() + 31536000000L);
                        date.set(11, date.getMaximum(11));
                        date.set(12, date.getMaximum(12));
                        date.set(13, date.getMaximum(13));
                        date.set(14, date.getMaximum(14));
                        datePicker.setMaxDate(date.getTimeInMillis());
                        dialog.setButton(-1, LocaleController.getString("Set", R.string.Set), dialog);
                        dialog.setButton(-3, LocaleController.getString("UserRestrictionsUntilForever", R.string.UserRestrictionsUntilForever), new C23522());
                        dialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C23533());
                        if (VERSION.SDK_INT >= 21) {
                            dialog.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface dialog) {
                                    int count = datePicker.getChildCount();
                                    for (int a = 0; a < count; a++) {
                                        View child = datePicker.getChildAt(a);
                                        LayoutParams layoutParams = child.getLayoutParams();
                                        layoutParams.width = -1;
                                        child.setLayoutParams(layoutParams);
                                    }
                                }
                            });
                        }
                        ChannelRightsEditActivity.this.showDialog(dialog);
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            } else if (view instanceof TextCheckCell2) {
                TextCheckCell2 checkCell = (TextCheckCell2) view;
                if (checkCell.isEnabled()) {
                    checkCell.setChecked(!checkCell.isChecked());
                    if (position == ChannelRightsEditActivity.this.changeInfoRow) {
                        ChannelRightsEditActivity.this.adminRights.change_info = !ChannelRightsEditActivity.this.adminRights.change_info;
                    } else if (position == ChannelRightsEditActivity.this.postMessagesRow) {
                        ChannelRightsEditActivity.this.adminRights.post_messages = !ChannelRightsEditActivity.this.adminRights.post_messages;
                    } else if (position == ChannelRightsEditActivity.this.editMesagesRow) {
                        ChannelRightsEditActivity.this.adminRights.edit_messages = !ChannelRightsEditActivity.this.adminRights.edit_messages;
                    } else if (position == ChannelRightsEditActivity.this.deleteMessagesRow) {
                        ChannelRightsEditActivity.this.adminRights.delete_messages = !ChannelRightsEditActivity.this.adminRights.delete_messages;
                    } else if (position == ChannelRightsEditActivity.this.addAdminsRow) {
                        ChannelRightsEditActivity.this.adminRights.add_admins = !ChannelRightsEditActivity.this.adminRights.add_admins;
                    } else if (position == ChannelRightsEditActivity.this.banUsersRow) {
                        ChannelRightsEditActivity.this.adminRights.ban_users = !ChannelRightsEditActivity.this.adminRights.ban_users;
                    } else if (position == ChannelRightsEditActivity.this.addUsersRow) {
                        TLRPC$TL_channelAdminRights access$200 = ChannelRightsEditActivity.this.adminRights;
                        TLRPC$TL_channelAdminRights access$2002 = ChannelRightsEditActivity.this.adminRights;
                        r5 = !ChannelRightsEditActivity.this.adminRights.invite_users;
                        access$2002.invite_link = r5;
                        access$200.invite_users = r5;
                    } else if (position == ChannelRightsEditActivity.this.pinMessagesRow) {
                        ChannelRightsEditActivity.this.adminRights.pin_messages = !ChannelRightsEditActivity.this.adminRights.pin_messages;
                    } else if (ChannelRightsEditActivity.this.bannedRights != null) {
                        TLRPC$TL_channelBannedRights access$600;
                        TLRPC$TL_channelBannedRights access$6002;
                        boolean disabled = !checkCell.isChecked();
                        if (position == ChannelRightsEditActivity.this.viewMessagesRow) {
                            ChannelRightsEditActivity.this.bannedRights.view_messages = !ChannelRightsEditActivity.this.bannedRights.view_messages;
                        } else if (position == ChannelRightsEditActivity.this.sendMessagesRow) {
                            ChannelRightsEditActivity.this.bannedRights.send_messages = !ChannelRightsEditActivity.this.bannedRights.send_messages;
                        } else if (position == ChannelRightsEditActivity.this.sendMediaRow) {
                            ChannelRightsEditActivity.this.bannedRights.send_media = !ChannelRightsEditActivity.this.bannedRights.send_media;
                        } else if (position == ChannelRightsEditActivity.this.sendStickersRow) {
                            access$600 = ChannelRightsEditActivity.this.bannedRights;
                            access$6002 = ChannelRightsEditActivity.this.bannedRights;
                            TLRPC$TL_channelBannedRights access$6003 = ChannelRightsEditActivity.this.bannedRights;
                            TLRPC$TL_channelBannedRights access$6004 = ChannelRightsEditActivity.this.bannedRights;
                            r5 = !ChannelRightsEditActivity.this.bannedRights.send_stickers;
                            access$6004.send_inline = r5;
                            access$6003.send_gifs = r5;
                            access$6002.send_games = r5;
                            access$600.send_stickers = r5;
                        } else if (position == ChannelRightsEditActivity.this.embedLinksRow) {
                            ChannelRightsEditActivity.this.bannedRights.embed_links = !ChannelRightsEditActivity.this.bannedRights.embed_links;
                        }
                        ViewHolder holder;
                        if (disabled) {
                            if (ChannelRightsEditActivity.this.bannedRights.view_messages && !ChannelRightsEditActivity.this.bannedRights.send_messages) {
                                ChannelRightsEditActivity.this.bannedRights.send_messages = true;
                                holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMessagesRow);
                                if (holder != null) {
                                    ((TextCheckCell2) holder.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages) && !ChannelRightsEditActivity.this.bannedRights.send_media) {
                                ChannelRightsEditActivity.this.bannedRights.send_media = true;
                                holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMediaRow);
                                if (holder != null) {
                                    ((TextCheckCell2) holder.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_media) && !ChannelRightsEditActivity.this.bannedRights.send_stickers) {
                                TLRPC$TL_channelBannedRights access$6005 = ChannelRightsEditActivity.this.bannedRights;
                                access$600 = ChannelRightsEditActivity.this.bannedRights;
                                access$6002 = ChannelRightsEditActivity.this.bannedRights;
                                ChannelRightsEditActivity.this.bannedRights.send_inline = true;
                                access$6002.send_gifs = true;
                                access$600.send_games = true;
                                access$6005.send_stickers = true;
                                holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendStickersRow);
                                if (holder != null) {
                                    ((TextCheckCell2) holder.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_media) && !ChannelRightsEditActivity.this.bannedRights.embed_links) {
                                ChannelRightsEditActivity.this.bannedRights.embed_links = true;
                                holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.embedLinksRow);
                                if (holder != null) {
                                    ((TextCheckCell2) holder.itemView).setChecked(false);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.send_messages && ChannelRightsEditActivity.this.bannedRights.embed_links && ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.send_media) && ChannelRightsEditActivity.this.bannedRights.view_messages) {
                            ChannelRightsEditActivity.this.bannedRights.view_messages = false;
                            holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.viewMessagesRow);
                            if (holder != null) {
                                ((TextCheckCell2) holder.itemView).setChecked(true);
                            }
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.embed_links && ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.send_media) && ChannelRightsEditActivity.this.bannedRights.send_messages) {
                            ChannelRightsEditActivity.this.bannedRights.send_messages = false;
                            holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMessagesRow);
                            if (holder != null) {
                                ((TextCheckCell2) holder.itemView).setChecked(true);
                            }
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.embed_links) && ChannelRightsEditActivity.this.bannedRights.send_media) {
                            ChannelRightsEditActivity.this.bannedRights.send_media = false;
                            holder = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMediaRow);
                            if (holder != null) {
                                ((TextCheckCell2) holder.itemView).setChecked(true);
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$4 */
    class C23564 implements ThemeDescriptionDelegate {
        C23564() {
        }

        public void didSetColor(int color) {
            int count = ChannelRightsEditActivity.this.listView.getChildCount();
            for (int a = 0; a < count; a++) {
                View child = ChannelRightsEditActivity.this.listView.getChildAt(a);
                if (child instanceof UserCell) {
                    ((UserCell) child).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            boolean z = true;
            if (!ChannelRightsEditActivity.this.canEdit) {
                return false;
            }
            int type = holder.getItemViewType();
            if (ChannelRightsEditActivity.this.currentType == 0 && type == 4) {
                int position = holder.getAdapterPosition();
                if (position == ChannelRightsEditActivity.this.changeInfoRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.change_info;
                }
                if (position == ChannelRightsEditActivity.this.postMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.post_messages;
                }
                if (position == ChannelRightsEditActivity.this.editMesagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.edit_messages;
                }
                if (position == ChannelRightsEditActivity.this.deleteMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.delete_messages;
                }
                if (position == ChannelRightsEditActivity.this.addAdminsRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.add_admins;
                }
                if (position == ChannelRightsEditActivity.this.banUsersRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.ban_users;
                }
                if (position == ChannelRightsEditActivity.this.addUsersRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.invite_users;
                }
                if (position == ChannelRightsEditActivity.this.pinMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.pin_messages;
                }
            }
            if (type == 3 || type == 1 || type == 5) {
                z = false;
            }
            return z;
        }

        public int getItemCount() {
            return ChannelRightsEditActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new UserCell(this.mContext, 1, 0, false);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
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
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    view = new TextCheckCell2(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    view = new ShadowSectionCell(this.mContext);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            switch (holder.getItemViewType()) {
                case 0:
                    holder.itemView.setData(ChannelRightsEditActivity.this.currentUser, null, null, 0);
                    return;
                case 1:
                    TextInfoPrivacyCell privacyCell = holder.itemView;
                    if (position == ChannelRightsEditActivity.this.cantEditInfoRow) {
                        privacyCell.setText(LocaleController.getString("EditAdminCantEdit", R.string.EditAdminCantEdit));
                        return;
                    }
                    return;
                case 2:
                    TextSettingsCell actionCell = holder.itemView;
                    if (position == ChannelRightsEditActivity.this.removeAdminRow) {
                        actionCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText3));
                        actionCell.setTag(Theme.key_windowBackgroundWhiteRedText3);
                        if (ChannelRightsEditActivity.this.currentType == 0) {
                            actionCell.setText(LocaleController.getString("EditAdminRemoveAdmin", R.string.EditAdminRemoveAdmin), false);
                            return;
                        } else if (ChannelRightsEditActivity.this.currentType == 1) {
                            actionCell.setText(LocaleController.getString("UserRestrictionsBlock", R.string.UserRestrictionsBlock), false);
                            return;
                        } else {
                            return;
                        }
                    } else if (position == ChannelRightsEditActivity.this.untilDateRow) {
                        String value;
                        actionCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                        actionCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                        if (ChannelRightsEditActivity.this.bannedRights.until_date == 0 || Math.abs(((long) ChannelRightsEditActivity.this.bannedRights.until_date) - (System.currentTimeMillis() / 1000)) > 315360000) {
                            value = LocaleController.getString("UserRestrictionsUntilForever", R.string.UserRestrictionsUntilForever);
                        } else {
                            value = LocaleController.formatDateForBan((long) ChannelRightsEditActivity.this.bannedRights.until_date);
                        }
                        actionCell.setTextAndValue(LocaleController.getString("UserRestrictionsUntil", R.string.UserRestrictionsUntil), value, false);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    HeaderCell headerCell = holder.itemView;
                    if (ChannelRightsEditActivity.this.currentType == 0) {
                        headerCell.setText(LocaleController.getString("EditAdminWhatCanDo", R.string.EditAdminWhatCanDo));
                        return;
                    } else if (ChannelRightsEditActivity.this.currentType == 1) {
                        headerCell.setText(LocaleController.getString("UserRestrictionsCanDo", R.string.UserRestrictionsCanDo));
                        return;
                    } else {
                        return;
                    }
                case 4:
                    TextCheckCell2 checkCell = holder.itemView;
                    if (position == ChannelRightsEditActivity.this.changeInfoRow) {
                        if (ChannelRightsEditActivity.this.isMegagroup) {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminChangeGroupInfo", R.string.EditAdminChangeGroupInfo), ChannelRightsEditActivity.this.adminRights.change_info, true);
                        } else {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminChangeChannelInfo", R.string.EditAdminChangeChannelInfo), ChannelRightsEditActivity.this.adminRights.change_info, true);
                        }
                    } else if (position == ChannelRightsEditActivity.this.postMessagesRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("EditAdminPostMessages", R.string.EditAdminPostMessages), ChannelRightsEditActivity.this.adminRights.post_messages, true);
                    } else if (position == ChannelRightsEditActivity.this.editMesagesRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("EditAdminEditMessages", R.string.EditAdminEditMessages), ChannelRightsEditActivity.this.adminRights.edit_messages, true);
                    } else if (position == ChannelRightsEditActivity.this.deleteMessagesRow) {
                        if (ChannelRightsEditActivity.this.isMegagroup) {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminGroupDeleteMessages", R.string.EditAdminGroupDeleteMessages), ChannelRightsEditActivity.this.adminRights.delete_messages, true);
                        } else {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminDeleteMessages", R.string.EditAdminDeleteMessages), ChannelRightsEditActivity.this.adminRights.delete_messages, true);
                        }
                    } else if (position == ChannelRightsEditActivity.this.addAdminsRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("EditAdminAddAdmins", R.string.EditAdminAddAdmins), ChannelRightsEditActivity.this.adminRights.add_admins, false);
                    } else if (position == ChannelRightsEditActivity.this.banUsersRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("EditAdminBanUsers", R.string.EditAdminBanUsers), ChannelRightsEditActivity.this.adminRights.ban_users, true);
                    } else if (position == ChannelRightsEditActivity.this.addUsersRow) {
                        if (ChannelRightsEditActivity.this.isDemocracy) {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminAddUsersViaLink", R.string.EditAdminAddUsersViaLink), ChannelRightsEditActivity.this.adminRights.invite_users, true);
                        } else {
                            checkCell.setTextAndCheck(LocaleController.getString("EditAdminAddUsers", R.string.EditAdminAddUsers), ChannelRightsEditActivity.this.adminRights.invite_users, true);
                        }
                    } else if (position == ChannelRightsEditActivity.this.pinMessagesRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("EditAdminPinMessages", R.string.EditAdminPinMessages), ChannelRightsEditActivity.this.adminRights.pin_messages, true);
                    } else if (position == ChannelRightsEditActivity.this.viewMessagesRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("UserRestrictionsRead", R.string.UserRestrictionsRead), !ChannelRightsEditActivity.this.bannedRights.view_messages, true);
                    } else if (position == ChannelRightsEditActivity.this.sendMessagesRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("UserRestrictionsSend", R.string.UserRestrictionsSend), !ChannelRightsEditActivity.this.bannedRights.send_messages, true);
                    } else if (position == ChannelRightsEditActivity.this.sendMediaRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("UserRestrictionsSendMedia", R.string.UserRestrictionsSendMedia), !ChannelRightsEditActivity.this.bannedRights.send_media, true);
                    } else if (position == ChannelRightsEditActivity.this.sendStickersRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("UserRestrictionsSendStickers", R.string.UserRestrictionsSendStickers), !ChannelRightsEditActivity.this.bannedRights.send_stickers, true);
                    } else if (position == ChannelRightsEditActivity.this.embedLinksRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("UserRestrictionsEmbedLinks", R.string.UserRestrictionsEmbedLinks), !ChannelRightsEditActivity.this.bannedRights.embed_links, true);
                    }
                    if (position == ChannelRightsEditActivity.this.sendMediaRow || position == ChannelRightsEditActivity.this.sendStickersRow || position == ChannelRightsEditActivity.this.embedLinksRow) {
                        boolean z;
                        if (ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.view_messages) {
                            z = false;
                        } else {
                            z = true;
                        }
                        checkCell.setEnabled(z);
                        return;
                    } else if (position == ChannelRightsEditActivity.this.sendMessagesRow) {
                        checkCell.setEnabled(!ChannelRightsEditActivity.this.bannedRights.view_messages);
                        return;
                    } else {
                        return;
                    }
                case 5:
                    ShadowSectionCell shadowCell = holder.itemView;
                    if (position == ChannelRightsEditActivity.this.rightsShadowRow) {
                        shadowCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, ChannelRightsEditActivity.this.removeAdminRow == -1 ? R.drawable.greydivider_bottom : R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (position == ChannelRightsEditActivity.this.removeAdminShadowRow) {
                        shadowCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        shadowCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                default:
                    return;
            }
        }

        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            }
            if (position == 1 || position == ChannelRightsEditActivity.this.rightsShadowRow || position == ChannelRightsEditActivity.this.removeAdminShadowRow) {
                return 5;
            }
            if (position == 2) {
                return 3;
            }
            if (position == ChannelRightsEditActivity.this.changeInfoRow || position == ChannelRightsEditActivity.this.postMessagesRow || position == ChannelRightsEditActivity.this.editMesagesRow || position == ChannelRightsEditActivity.this.deleteMessagesRow || position == ChannelRightsEditActivity.this.addAdminsRow || position == ChannelRightsEditActivity.this.banUsersRow || position == ChannelRightsEditActivity.this.addUsersRow || position == ChannelRightsEditActivity.this.pinMessagesRow || position == ChannelRightsEditActivity.this.viewMessagesRow || position == ChannelRightsEditActivity.this.sendMessagesRow || position == ChannelRightsEditActivity.this.sendMediaRow || position == ChannelRightsEditActivity.this.sendStickersRow || position == ChannelRightsEditActivity.this.embedLinksRow) {
                return 4;
            }
            if (position != ChannelRightsEditActivity.this.cantEditInfoRow) {
                return 2;
            }
            return 1;
        }
    }

    public ChannelRightsEditActivity(int userId, int channelId, TLRPC$TL_channelAdminRights rightsAdmin, TLRPC$TL_channelBannedRights rightsBanned, int type, boolean edit) {
        int i;
        this.chatId = channelId;
        this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(userId));
        this.currentType = type;
        this.canEdit = edit;
        TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (chat != null) {
            this.isMegagroup = chat.megagroup;
            this.myAdminRights = chat.admin_rights;
        }
        if (this.myAdminRights == null) {
            this.myAdminRights = new TLRPC$TL_channelAdminRights();
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights2 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights3 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights4 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights5 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights6 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights7 = this.myAdminRights;
            TLRPC$TL_channelAdminRights tLRPC$TL_channelAdminRights8 = this.myAdminRights;
            this.myAdminRights.add_admins = true;
            tLRPC$TL_channelAdminRights8.pin_messages = true;
            tLRPC$TL_channelAdminRights7.invite_link = true;
            tLRPC$TL_channelAdminRights6.invite_users = true;
            tLRPC$TL_channelAdminRights5.ban_users = true;
            tLRPC$TL_channelAdminRights4.delete_messages = true;
            tLRPC$TL_channelAdminRights3.edit_messages = true;
            tLRPC$TL_channelAdminRights2.post_messages = true;
            tLRPC$TL_channelAdminRights.change_info = true;
        }
        boolean initialIsSet;
        if (type == 0) {
            this.adminRights = new TLRPC$TL_channelAdminRights();
            if (rightsAdmin == null) {
                this.adminRights.change_info = this.myAdminRights.change_info;
                this.adminRights.post_messages = this.myAdminRights.post_messages;
                this.adminRights.edit_messages = this.myAdminRights.edit_messages;
                this.adminRights.delete_messages = this.myAdminRights.delete_messages;
                this.adminRights.ban_users = this.myAdminRights.ban_users;
                this.adminRights.invite_users = this.myAdminRights.invite_users;
                this.adminRights.invite_link = this.myAdminRights.invite_link;
                this.adminRights.pin_messages = this.myAdminRights.pin_messages;
                initialIsSet = false;
            } else {
                this.adminRights.change_info = rightsAdmin.change_info;
                this.adminRights.post_messages = rightsAdmin.post_messages;
                this.adminRights.edit_messages = rightsAdmin.edit_messages;
                this.adminRights.delete_messages = rightsAdmin.delete_messages;
                this.adminRights.ban_users = rightsAdmin.ban_users;
                this.adminRights.invite_users = rightsAdmin.invite_users;
                this.adminRights.invite_link = rightsAdmin.invite_link;
                this.adminRights.pin_messages = rightsAdmin.pin_messages;
                this.adminRights.add_admins = rightsAdmin.add_admins;
                initialIsSet = this.adminRights.change_info || this.adminRights.post_messages || this.adminRights.edit_messages || this.adminRights.delete_messages || this.adminRights.ban_users || this.adminRights.invite_users || this.adminRights.invite_link || this.adminRights.pin_messages || this.adminRights.add_admins;
            }
        } else {
            this.bannedRights = new TLRPC$TL_channelBannedRights();
            if (rightsBanned == null) {
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights2 = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights3 = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights4 = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights5 = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights6 = this.bannedRights;
                TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights7 = this.bannedRights;
                this.bannedRights.send_inline = true;
                tLRPC$TL_channelBannedRights7.send_games = true;
                tLRPC$TL_channelBannedRights6.send_gifs = true;
                tLRPC$TL_channelBannedRights5.send_stickers = true;
                tLRPC$TL_channelBannedRights4.embed_links = true;
                tLRPC$TL_channelBannedRights3.send_messages = true;
                tLRPC$TL_channelBannedRights2.send_media = true;
                tLRPC$TL_channelBannedRights.view_messages = true;
            } else {
                this.bannedRights.view_messages = rightsBanned.view_messages;
                this.bannedRights.send_messages = rightsBanned.send_messages;
                this.bannedRights.send_media = rightsBanned.send_media;
                this.bannedRights.send_stickers = rightsBanned.send_stickers;
                this.bannedRights.send_gifs = rightsBanned.send_gifs;
                this.bannedRights.send_games = rightsBanned.send_games;
                this.bannedRights.send_inline = rightsBanned.send_inline;
                this.bannedRights.embed_links = rightsBanned.embed_links;
                this.bannedRights.until_date = rightsBanned.until_date;
            }
            initialIsSet = rightsBanned == null || !rightsBanned.view_messages;
        }
        this.rowCount += 3;
        if (type == 0) {
            if (this.isMegagroup) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.changeInfoRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.deleteMessagesRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.banUsersRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.addUsersRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.pinMessagesRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.addAdminsRow = i;
                this.isDemocracy = chat.democracy;
            } else {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.changeInfoRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.postMessagesRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.editMesagesRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.deleteMessagesRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.addUsersRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.addAdminsRow = i;
            }
        } else if (type == 1) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.viewMessagesRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.sendMessagesRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.sendMediaRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.sendStickersRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.embedLinksRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.untilDateRow = i;
        }
        if (this.canEdit && initialIsSet) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.rightsShadowRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.removeAdminRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.removeAdminShadowRow = i;
            this.cantEditInfoRow = -1;
            return;
        }
        this.removeAdminRow = -1;
        this.removeAdminShadowRow = -1;
        if (type != 0 || this.canEdit) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.rightsShadowRow = i;
            return;
        }
        this.rightsShadowRow = -1;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.cantEditInfoRow = i;
    }

    public View createView(Context context) {
        int i = 1;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.currentType == 0) {
            this.actionBar.setTitle(LocaleController.getString("EditAdmin", R.string.EditAdmin));
        } else {
            this.actionBar.setTitle(LocaleController.getString("UserRestrictions", R.string.UserRestrictions));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C23471());
        if (this.canEdit) {
            this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        }
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new RecyclerListView(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(linearLayoutManager);
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
        this.listView.setOnItemClickListener(new C23553());
        return this.fragmentView;
    }

    public void onResume() {
        super.onResume();
        if (this.listViewAdapter != null) {
            this.listViewAdapter.notifyDataSetChanged();
        }
    }

    public void setDelegate(ChannelRightsEditActivityDelegate channelRightsEditActivityDelegate) {
        this.delegate = channelRightsEditActivityDelegate;
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new C23564();
        r10 = new ThemeDescription[34];
        r10[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{UserCell.class, TextSettingsCell.class, TextCheckCell2.class, HeaderCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r10[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r10[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r10[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r10[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r10[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r10[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r10[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[10] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText3);
        r10[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[13] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r10[14] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueImageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[16] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[17] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        r10[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        r10[19] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        r10[20] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell2.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        r10[21] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[22] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r10[23] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[24] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        r10[25] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteBlueText);
        r10[26] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[27] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        r10[28] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        r10[29] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        r10[30] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        r10[31] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        r10[32] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r10[33] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        return r10;
    }
}
