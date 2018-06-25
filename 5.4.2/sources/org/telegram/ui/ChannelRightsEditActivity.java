package org.telegram.ui;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
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
import org.telegram.messenger.support.widget.RecyclerView.LayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
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
    private TL_channelAdminRights adminRights;
    private int banUsersRow;
    private TL_channelBannedRights bannedRights;
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
    private TL_channelAdminRights myAdminRights;
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
        void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights);
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$1 */
    class C41851 extends ActionBarMenuOnItemClick {
        C41851() {
        }

        public void onItemClick(int i) {
            int i2 = 0;
            if (i == -1) {
                ChannelRightsEditActivity.this.finishFragment();
            } else if (i == 1) {
                if (ChannelRightsEditActivity.this.currentType == 0) {
                    TL_channelAdminRights access$200;
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
                            i2 = 1;
                        }
                        access$500.didSetRights(i2, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.bannedRights);
                    }
                } else if (ChannelRightsEditActivity.this.currentType == 1) {
                    MessagesController.setUserBannedRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, ChannelRightsEditActivity.this.bannedRights, ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(1));
                    if (!ChannelRightsEditActivity.this.bannedRights.view_messages) {
                        if (ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_stickers || ChannelRightsEditActivity.this.bannedRights.embed_links || ChannelRightsEditActivity.this.bannedRights.send_media || ChannelRightsEditActivity.this.bannedRights.send_gifs || ChannelRightsEditActivity.this.bannedRights.send_games || ChannelRightsEditActivity.this.bannedRights.send_inline) {
                            i2 = 1;
                        } else {
                            ChannelRightsEditActivity.this.bannedRights.until_date = 0;
                            i2 = 2;
                        }
                    }
                    if (ChannelRightsEditActivity.this.delegate != null) {
                        ChannelRightsEditActivity.this.delegate.didSetRights(i2, ChannelRightsEditActivity.this.adminRights, ChannelRightsEditActivity.this.bannedRights);
                    }
                }
                ChannelRightsEditActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3 */
    class C41933 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$1 */
        class C41891 implements OnDateSetListener {

            /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$1$2 */
            class C41882 implements OnClickListener {
                C41882() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                }
            }

            C41891() {
            }

            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                Calendar instance = Calendar.getInstance();
                instance.clear();
                instance.set(i, i2, i3);
                final int time = (int) (instance.getTime().getTime() / 1000);
                try {
                    Dialog timePickerDialog = new TimePickerDialog(ChannelRightsEditActivity.this.getParentActivity(), new OnTimeSetListener() {
                        public void onTimeSet(TimePicker timePicker, int i, int i2) {
                            ChannelRightsEditActivity.this.bannedRights.until_date = (time + (i * 3600)) + (i2 * 60);
                            ChannelRightsEditActivity.this.listViewAdapter.notifyItemChanged(ChannelRightsEditActivity.this.untilDateRow);
                        }
                    }, 0, 0, true);
                    timePickerDialog.setButton(-1, LocaleController.getString("Set", R.string.Set), timePickerDialog);
                    timePickerDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C41882());
                    ChannelRightsEditActivity.this.showDialog(timePickerDialog);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$2 */
        class C41902 implements OnClickListener {
            C41902() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                ChannelRightsEditActivity.this.bannedRights.until_date = 0;
                ChannelRightsEditActivity.this.listViewAdapter.notifyItemChanged(ChannelRightsEditActivity.this.untilDateRow);
            }
        }

        /* renamed from: org.telegram.ui.ChannelRightsEditActivity$3$3 */
        class C41913 implements OnClickListener {
            C41913() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }

        C41933() {
        }

        public void onItemClick(View view, int i) {
            boolean z = true;
            if (!ChannelRightsEditActivity.this.canEdit) {
                return;
            }
            if (i == 0) {
                Bundle bundle = new Bundle();
                bundle.putInt("user_id", ChannelRightsEditActivity.this.currentUser.id);
                ChannelRightsEditActivity.this.presentFragment(new ProfileActivity(bundle));
            } else if (i == ChannelRightsEditActivity.this.removeAdminRow) {
                if (ChannelRightsEditActivity.this.currentType == 0) {
                    MessagesController.setUserAdminRole(ChannelRightsEditActivity.this.chatId, ChannelRightsEditActivity.this.currentUser, new TL_channelAdminRights(), ChannelRightsEditActivity.this.isMegagroup, ChannelRightsEditActivity.this.getFragmentForAlert(0));
                } else if (ChannelRightsEditActivity.this.currentType == 1) {
                    ChannelRightsEditActivity.this.bannedRights = new TL_channelBannedRights();
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
            } else if (i == ChannelRightsEditActivity.this.untilDateRow) {
                if (ChannelRightsEditActivity.this.getParentActivity() != null) {
                    Calendar instance = Calendar.getInstance();
                    try {
                        Dialog datePickerDialog = new DatePickerDialog(ChannelRightsEditActivity.this.getParentActivity(), new C41891(), instance.get(1), instance.get(2), instance.get(5));
                        final DatePicker datePicker = datePickerDialog.getDatePicker();
                        Calendar instance2 = Calendar.getInstance();
                        instance2.setTimeInMillis(System.currentTimeMillis());
                        instance2.set(11, instance2.getMinimum(11));
                        instance2.set(12, instance2.getMinimum(12));
                        instance2.set(13, instance2.getMinimum(13));
                        instance2.set(14, instance2.getMinimum(14));
                        datePicker.setMinDate(instance2.getTimeInMillis());
                        instance2.setTimeInMillis(System.currentTimeMillis() + 31536000000L);
                        instance2.set(11, instance2.getMaximum(11));
                        instance2.set(12, instance2.getMaximum(12));
                        instance2.set(13, instance2.getMaximum(13));
                        instance2.set(14, instance2.getMaximum(14));
                        datePicker.setMaxDate(instance2.getTimeInMillis());
                        datePickerDialog.setButton(-1, LocaleController.getString("Set", R.string.Set), datePickerDialog);
                        datePickerDialog.setButton(-3, LocaleController.getString("UserRestrictionsUntilForever", R.string.UserRestrictionsUntilForever), new C41902());
                        datePickerDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C41913());
                        if (VERSION.SDK_INT >= 21) {
                            datePickerDialog.setOnShowListener(new OnShowListener() {
                                public void onShow(DialogInterface dialogInterface) {
                                    int childCount = datePicker.getChildCount();
                                    for (int i = 0; i < childCount; i++) {
                                        View childAt = datePicker.getChildAt(i);
                                        LayoutParams layoutParams = childAt.getLayoutParams();
                                        layoutParams.width = -1;
                                        childAt.setLayoutParams(layoutParams);
                                    }
                                }
                            });
                        }
                        ChannelRightsEditActivity.this.showDialog(datePickerDialog);
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            } else if (view instanceof TextCheckCell2) {
                TextCheckCell2 textCheckCell2 = (TextCheckCell2) view;
                if (textCheckCell2.isEnabled()) {
                    textCheckCell2.setChecked(!textCheckCell2.isChecked());
                    TL_channelAdminRights access$200;
                    if (i == ChannelRightsEditActivity.this.changeInfoRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.change_info) {
                            z = false;
                        }
                        access$200.change_info = z;
                    } else if (i == ChannelRightsEditActivity.this.postMessagesRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.post_messages) {
                            z = false;
                        }
                        access$200.post_messages = z;
                    } else if (i == ChannelRightsEditActivity.this.editMesagesRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.edit_messages) {
                            z = false;
                        }
                        access$200.edit_messages = z;
                    } else if (i == ChannelRightsEditActivity.this.deleteMessagesRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.delete_messages) {
                            z = false;
                        }
                        access$200.delete_messages = z;
                    } else if (i == ChannelRightsEditActivity.this.addAdminsRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.add_admins) {
                            z = false;
                        }
                        access$200.add_admins = z;
                    } else if (i == ChannelRightsEditActivity.this.banUsersRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.ban_users) {
                            z = false;
                        }
                        access$200.ban_users = z;
                    } else if (i == ChannelRightsEditActivity.this.addUsersRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        TL_channelAdminRights access$2002 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.invite_users) {
                            z = false;
                        }
                        access$2002.invite_link = z;
                        access$200.invite_users = z;
                    } else if (i == ChannelRightsEditActivity.this.pinMessagesRow) {
                        access$200 = ChannelRightsEditActivity.this.adminRights;
                        if (ChannelRightsEditActivity.this.adminRights.pin_messages) {
                            z = false;
                        }
                        access$200.pin_messages = z;
                    } else if (ChannelRightsEditActivity.this.bannedRights != null) {
                        TL_channelBannedRights access$600;
                        boolean z2 = !textCheckCell2.isChecked();
                        if (i == ChannelRightsEditActivity.this.viewMessagesRow) {
                            ChannelRightsEditActivity.this.bannedRights.view_messages = !ChannelRightsEditActivity.this.bannedRights.view_messages;
                        } else if (i == ChannelRightsEditActivity.this.sendMessagesRow) {
                            ChannelRightsEditActivity.this.bannedRights.send_messages = !ChannelRightsEditActivity.this.bannedRights.send_messages;
                        } else if (i == ChannelRightsEditActivity.this.sendMediaRow) {
                            ChannelRightsEditActivity.this.bannedRights.send_media = !ChannelRightsEditActivity.this.bannedRights.send_media;
                        } else if (i == ChannelRightsEditActivity.this.sendStickersRow) {
                            access$600 = ChannelRightsEditActivity.this.bannedRights;
                            TL_channelBannedRights access$6002 = ChannelRightsEditActivity.this.bannedRights;
                            TL_channelBannedRights access$6003 = ChannelRightsEditActivity.this.bannedRights;
                            TL_channelBannedRights access$6004 = ChannelRightsEditActivity.this.bannedRights;
                            boolean z3 = !ChannelRightsEditActivity.this.bannedRights.send_stickers;
                            access$6004.send_inline = z3;
                            access$6003.send_gifs = z3;
                            access$6002.send_games = z3;
                            access$600.send_stickers = z3;
                        } else if (i == ChannelRightsEditActivity.this.embedLinksRow) {
                            ChannelRightsEditActivity.this.bannedRights.embed_links = !ChannelRightsEditActivity.this.bannedRights.embed_links;
                        }
                        ViewHolder findViewHolderForAdapterPosition;
                        if (z2) {
                            if (ChannelRightsEditActivity.this.bannedRights.view_messages && !ChannelRightsEditActivity.this.bannedRights.send_messages) {
                                ChannelRightsEditActivity.this.bannedRights.send_messages = true;
                                findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMessagesRow);
                                if (findViewHolderForAdapterPosition != null) {
                                    ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages) && !ChannelRightsEditActivity.this.bannedRights.send_media) {
                                ChannelRightsEditActivity.this.bannedRights.send_media = true;
                                findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMediaRow);
                                if (findViewHolderForAdapterPosition != null) {
                                    ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_media) && !ChannelRightsEditActivity.this.bannedRights.send_stickers) {
                                TL_channelBannedRights access$6005 = ChannelRightsEditActivity.this.bannedRights;
                                TL_channelBannedRights access$6006 = ChannelRightsEditActivity.this.bannedRights;
                                access$600 = ChannelRightsEditActivity.this.bannedRights;
                                ChannelRightsEditActivity.this.bannedRights.send_inline = true;
                                access$600.send_gifs = true;
                                access$6006.send_games = true;
                                access$6005.send_stickers = true;
                                findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendStickersRow);
                                if (findViewHolderForAdapterPosition != null) {
                                    ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(false);
                                }
                            }
                            if ((ChannelRightsEditActivity.this.bannedRights.view_messages || ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.send_media) && !ChannelRightsEditActivity.this.bannedRights.embed_links) {
                                ChannelRightsEditActivity.this.bannedRights.embed_links = true;
                                findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.embedLinksRow);
                                if (findViewHolderForAdapterPosition != null) {
                                    ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(false);
                                    return;
                                }
                                return;
                            }
                            return;
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.send_messages && ChannelRightsEditActivity.this.bannedRights.embed_links && ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.send_media) && ChannelRightsEditActivity.this.bannedRights.view_messages) {
                            ChannelRightsEditActivity.this.bannedRights.view_messages = false;
                            findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.viewMessagesRow);
                            if (findViewHolderForAdapterPosition != null) {
                                ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(true);
                            }
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.embed_links && ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.send_media) && ChannelRightsEditActivity.this.bannedRights.send_messages) {
                            ChannelRightsEditActivity.this.bannedRights.send_messages = false;
                            findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMessagesRow);
                            if (findViewHolderForAdapterPosition != null) {
                                ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(true);
                            }
                        }
                        if (!(ChannelRightsEditActivity.this.bannedRights.send_inline && ChannelRightsEditActivity.this.bannedRights.embed_links) && ChannelRightsEditActivity.this.bannedRights.send_media) {
                            ChannelRightsEditActivity.this.bannedRights.send_media = false;
                            findViewHolderForAdapterPosition = ChannelRightsEditActivity.this.listView.findViewHolderForAdapterPosition(ChannelRightsEditActivity.this.sendMediaRow);
                            if (findViewHolderForAdapterPosition != null) {
                                ((TextCheckCell2) findViewHolderForAdapterPosition.itemView).setChecked(true);
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelRightsEditActivity$4 */
    class C41944 implements ThemeDescriptionDelegate {
        C41944() {
        }

        public void didSetColor(int i) {
            int childCount = ChannelRightsEditActivity.this.listView.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = ChannelRightsEditActivity.this.listView.getChildAt(i2);
                if (childAt instanceof UserCell) {
                    ((UserCell) childAt).update(0);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ChannelRightsEditActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return i == 0 ? 0 : (i == 1 || i == ChannelRightsEditActivity.this.rightsShadowRow || i == ChannelRightsEditActivity.this.removeAdminShadowRow) ? 5 : i == 2 ? 3 : (i == ChannelRightsEditActivity.this.changeInfoRow || i == ChannelRightsEditActivity.this.postMessagesRow || i == ChannelRightsEditActivity.this.editMesagesRow || i == ChannelRightsEditActivity.this.deleteMessagesRow || i == ChannelRightsEditActivity.this.addAdminsRow || i == ChannelRightsEditActivity.this.banUsersRow || i == ChannelRightsEditActivity.this.addUsersRow || i == ChannelRightsEditActivity.this.pinMessagesRow || i == ChannelRightsEditActivity.this.viewMessagesRow || i == ChannelRightsEditActivity.this.sendMessagesRow || i == ChannelRightsEditActivity.this.sendMediaRow || i == ChannelRightsEditActivity.this.sendStickersRow || i == ChannelRightsEditActivity.this.embedLinksRow) ? 4 : i != ChannelRightsEditActivity.this.cantEditInfoRow ? 2 : 1;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            boolean z = true;
            if (!ChannelRightsEditActivity.this.canEdit) {
                return false;
            }
            int itemViewType = viewHolder.getItemViewType();
            if (ChannelRightsEditActivity.this.currentType == 0 && itemViewType == 4) {
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition == ChannelRightsEditActivity.this.changeInfoRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.change_info;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.postMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.post_messages;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.editMesagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.edit_messages;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.deleteMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.delete_messages;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.addAdminsRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.add_admins;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.banUsersRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.ban_users;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.addUsersRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.invite_users;
                }
                if (adapterPosition == ChannelRightsEditActivity.this.pinMessagesRow) {
                    return ChannelRightsEditActivity.this.myAdminRights.pin_messages;
                }
            }
            if (itemViewType == 3 || itemViewType == 1 || itemViewType == 5) {
                z = false;
            }
            return z;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = R.drawable.greydivider_bottom;
            boolean z = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    ((UserCell) viewHolder.itemView).setData(ChannelRightsEditActivity.this.currentUser, null, null, 0);
                    return;
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == ChannelRightsEditActivity.this.cantEditInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("EditAdminCantEdit", R.string.EditAdminCantEdit));
                        return;
                    }
                    return;
                case 2:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == ChannelRightsEditActivity.this.removeAdminRow) {
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText3));
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteRedText3);
                        if (ChannelRightsEditActivity.this.currentType == 0) {
                            textSettingsCell.setText(LocaleController.getString("EditAdminRemoveAdmin", R.string.EditAdminRemoveAdmin), false);
                            return;
                        } else if (ChannelRightsEditActivity.this.currentType == 1) {
                            textSettingsCell.setText(LocaleController.getString("UserRestrictionsBlock", R.string.UserRestrictionsBlock), false);
                            return;
                        } else {
                            return;
                        }
                    } else if (i == ChannelRightsEditActivity.this.untilDateRow) {
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                        String string = (ChannelRightsEditActivity.this.bannedRights.until_date == 0 || Math.abs(((long) ChannelRightsEditActivity.this.bannedRights.until_date) - (System.currentTimeMillis() / 1000)) > 315360000) ? LocaleController.getString("UserRestrictionsUntilForever", R.string.UserRestrictionsUntilForever) : LocaleController.formatDateForBan((long) ChannelRightsEditActivity.this.bannedRights.until_date);
                        textSettingsCell.setTextAndValue(LocaleController.getString("UserRestrictionsUntil", R.string.UserRestrictionsUntil), string, false);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
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
                    TextCheckCell2 textCheckCell2 = (TextCheckCell2) viewHolder.itemView;
                    if (i == ChannelRightsEditActivity.this.changeInfoRow) {
                        if (ChannelRightsEditActivity.this.isMegagroup) {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminChangeGroupInfo", R.string.EditAdminChangeGroupInfo), ChannelRightsEditActivity.this.adminRights.change_info, true);
                        } else {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminChangeChannelInfo", R.string.EditAdminChangeChannelInfo), ChannelRightsEditActivity.this.adminRights.change_info, true);
                        }
                    } else if (i == ChannelRightsEditActivity.this.postMessagesRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminPostMessages", R.string.EditAdminPostMessages), ChannelRightsEditActivity.this.adminRights.post_messages, true);
                    } else if (i == ChannelRightsEditActivity.this.editMesagesRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminEditMessages", R.string.EditAdminEditMessages), ChannelRightsEditActivity.this.adminRights.edit_messages, true);
                    } else if (i == ChannelRightsEditActivity.this.deleteMessagesRow) {
                        if (ChannelRightsEditActivity.this.isMegagroup) {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminGroupDeleteMessages", R.string.EditAdminGroupDeleteMessages), ChannelRightsEditActivity.this.adminRights.delete_messages, true);
                        } else {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminDeleteMessages", R.string.EditAdminDeleteMessages), ChannelRightsEditActivity.this.adminRights.delete_messages, true);
                        }
                    } else if (i == ChannelRightsEditActivity.this.addAdminsRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminAddAdmins", R.string.EditAdminAddAdmins), ChannelRightsEditActivity.this.adminRights.add_admins, false);
                    } else if (i == ChannelRightsEditActivity.this.banUsersRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminBanUsers", R.string.EditAdminBanUsers), ChannelRightsEditActivity.this.adminRights.ban_users, true);
                    } else if (i == ChannelRightsEditActivity.this.addUsersRow) {
                        if (ChannelRightsEditActivity.this.isDemocracy) {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminAddUsersViaLink", R.string.EditAdminAddUsersViaLink), ChannelRightsEditActivity.this.adminRights.invite_users, true);
                        } else {
                            textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminAddUsers", R.string.EditAdminAddUsers), ChannelRightsEditActivity.this.adminRights.invite_users, true);
                        }
                    } else if (i == ChannelRightsEditActivity.this.pinMessagesRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("EditAdminPinMessages", R.string.EditAdminPinMessages), ChannelRightsEditActivity.this.adminRights.pin_messages, true);
                    } else if (i == ChannelRightsEditActivity.this.viewMessagesRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("UserRestrictionsRead", R.string.UserRestrictionsRead), !ChannelRightsEditActivity.this.bannedRights.view_messages, true);
                    } else if (i == ChannelRightsEditActivity.this.sendMessagesRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("UserRestrictionsSend", R.string.UserRestrictionsSend), !ChannelRightsEditActivity.this.bannedRights.send_messages, true);
                    } else if (i == ChannelRightsEditActivity.this.sendMediaRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("UserRestrictionsSendMedia", R.string.UserRestrictionsSendMedia), !ChannelRightsEditActivity.this.bannedRights.send_media, true);
                    } else if (i == ChannelRightsEditActivity.this.sendStickersRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("UserRestrictionsSendStickers", R.string.UserRestrictionsSendStickers), !ChannelRightsEditActivity.this.bannedRights.send_stickers, true);
                    } else if (i == ChannelRightsEditActivity.this.embedLinksRow) {
                        textCheckCell2.setTextAndCheck(LocaleController.getString("UserRestrictionsEmbedLinks", R.string.UserRestrictionsEmbedLinks), !ChannelRightsEditActivity.this.bannedRights.embed_links, true);
                    }
                    if (i == ChannelRightsEditActivity.this.sendMediaRow || i == ChannelRightsEditActivity.this.sendStickersRow || i == ChannelRightsEditActivity.this.embedLinksRow) {
                        if (ChannelRightsEditActivity.this.bannedRights.send_messages || ChannelRightsEditActivity.this.bannedRights.view_messages) {
                            z = false;
                        }
                        textCheckCell2.setEnabled(z);
                        return;
                    } else if (i == ChannelRightsEditActivity.this.sendMessagesRow) {
                        if (ChannelRightsEditActivity.this.bannedRights.view_messages) {
                            z = false;
                        }
                        textCheckCell2.setEnabled(z);
                        return;
                    } else {
                        return;
                    }
                case 5:
                    ShadowSectionCell shadowSectionCell = (ShadowSectionCell) viewHolder.itemView;
                    if (i == ChannelRightsEditActivity.this.rightsShadowRow) {
                        Context context = this.mContext;
                        if (ChannelRightsEditActivity.this.removeAdminRow != -1) {
                            i2 = R.drawable.greydivider;
                        }
                        shadowSectionCell.setBackgroundDrawable(Theme.getThemedDrawable(context, i2, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ChannelRightsEditActivity.this.removeAdminShadowRow) {
                        shadowSectionCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        shadowSectionCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View userCell;
            switch (i) {
                case 0:
                    userCell = new UserCell(this.mContext, 1, 0, false);
                    userCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    userCell = new TextInfoPrivacyCell(this.mContext);
                    userCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    break;
                case 2:
                    userCell = new TextSettingsCell(this.mContext);
                    userCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    userCell = new HeaderCell(this.mContext);
                    userCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    userCell = new TextCheckCell2(this.mContext);
                    userCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    userCell = new ShadowSectionCell(this.mContext);
                    break;
            }
            return new Holder(userCell);
        }
    }

    public ChannelRightsEditActivity(int i, int i2, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights, int i3, boolean z) {
        this.chatId = i2;
        this.currentUser = MessagesController.getInstance().getUser(Integer.valueOf(i));
        this.currentType = i3;
        this.canEdit = z;
        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (chat != null) {
            this.isMegagroup = chat.megagroup;
            this.myAdminRights = chat.admin_rights;
        }
        if (this.myAdminRights == null) {
            this.myAdminRights = new TL_channelAdminRights();
            TL_channelAdminRights tL_channelAdminRights2 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights3 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights4 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights5 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights6 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights7 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights8 = this.myAdminRights;
            TL_channelAdminRights tL_channelAdminRights9 = this.myAdminRights;
            this.myAdminRights.add_admins = true;
            tL_channelAdminRights9.pin_messages = true;
            tL_channelAdminRights8.invite_link = true;
            tL_channelAdminRights7.invite_users = true;
            tL_channelAdminRights6.ban_users = true;
            tL_channelAdminRights5.delete_messages = true;
            tL_channelAdminRights4.edit_messages = true;
            tL_channelAdminRights3.post_messages = true;
            tL_channelAdminRights2.change_info = true;
        }
        Object obj;
        if (i3 == 0) {
            this.adminRights = new TL_channelAdminRights();
            if (tL_channelAdminRights == null) {
                this.adminRights.change_info = this.myAdminRights.change_info;
                this.adminRights.post_messages = this.myAdminRights.post_messages;
                this.adminRights.edit_messages = this.myAdminRights.edit_messages;
                this.adminRights.delete_messages = this.myAdminRights.delete_messages;
                this.adminRights.ban_users = this.myAdminRights.ban_users;
                this.adminRights.invite_users = this.myAdminRights.invite_users;
                this.adminRights.invite_link = this.myAdminRights.invite_link;
                this.adminRights.pin_messages = this.myAdminRights.pin_messages;
                obj = null;
            } else {
                this.adminRights.change_info = tL_channelAdminRights.change_info;
                this.adminRights.post_messages = tL_channelAdminRights.post_messages;
                this.adminRights.edit_messages = tL_channelAdminRights.edit_messages;
                this.adminRights.delete_messages = tL_channelAdminRights.delete_messages;
                this.adminRights.ban_users = tL_channelAdminRights.ban_users;
                this.adminRights.invite_users = tL_channelAdminRights.invite_users;
                this.adminRights.invite_link = tL_channelAdminRights.invite_link;
                this.adminRights.pin_messages = tL_channelAdminRights.pin_messages;
                this.adminRights.add_admins = tL_channelAdminRights.add_admins;
                obj = (this.adminRights.change_info || this.adminRights.post_messages || this.adminRights.edit_messages || this.adminRights.delete_messages || this.adminRights.ban_users || this.adminRights.invite_users || this.adminRights.invite_link || this.adminRights.pin_messages || this.adminRights.add_admins) ? 1 : null;
            }
        } else {
            this.bannedRights = new TL_channelBannedRights();
            if (tL_channelBannedRights == null) {
                TL_channelBannedRights tL_channelBannedRights2 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights3 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights4 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights5 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights6 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights7 = this.bannedRights;
                TL_channelBannedRights tL_channelBannedRights8 = this.bannedRights;
                this.bannedRights.send_inline = true;
                tL_channelBannedRights8.send_games = true;
                tL_channelBannedRights7.send_gifs = true;
                tL_channelBannedRights6.send_stickers = true;
                tL_channelBannedRights5.embed_links = true;
                tL_channelBannedRights4.send_messages = true;
                tL_channelBannedRights3.send_media = true;
                tL_channelBannedRights2.view_messages = true;
            } else {
                this.bannedRights.view_messages = tL_channelBannedRights.view_messages;
                this.bannedRights.send_messages = tL_channelBannedRights.send_messages;
                this.bannedRights.send_media = tL_channelBannedRights.send_media;
                this.bannedRights.send_stickers = tL_channelBannedRights.send_stickers;
                this.bannedRights.send_gifs = tL_channelBannedRights.send_gifs;
                this.bannedRights.send_games = tL_channelBannedRights.send_games;
                this.bannedRights.send_inline = tL_channelBannedRights.send_inline;
                this.bannedRights.embed_links = tL_channelBannedRights.embed_links;
                this.bannedRights.until_date = tL_channelBannedRights.until_date;
            }
            obj = (tL_channelBannedRights == null || !tL_channelBannedRights.view_messages) ? 1 : null;
        }
        this.rowCount += 3;
        int i4;
        if (i3 == 0) {
            if (this.isMegagroup) {
                int i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.changeInfoRow = i5;
                i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.deleteMessagesRow = i5;
                i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.banUsersRow = i5;
                i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.addUsersRow = i5;
                i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.pinMessagesRow = i5;
                i5 = this.rowCount;
                this.rowCount = i5 + 1;
                this.addAdminsRow = i5;
                this.isDemocracy = chat.democracy;
            } else {
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.changeInfoRow = i4;
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.postMessagesRow = i4;
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.editMesagesRow = i4;
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.deleteMessagesRow = i4;
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.addUsersRow = i4;
                i4 = this.rowCount;
                this.rowCount = i4 + 1;
                this.addAdminsRow = i4;
            }
        } else if (i3 == 1) {
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.viewMessagesRow = i4;
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.sendMessagesRow = i4;
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.sendMediaRow = i4;
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.sendStickersRow = i4;
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.embedLinksRow = i4;
            i4 = this.rowCount;
            this.rowCount = i4 + 1;
            this.untilDateRow = i4;
        }
        int i6;
        if (!this.canEdit || r1 == null) {
            this.removeAdminRow = -1;
            this.removeAdminShadowRow = -1;
            if (i3 != 0 || this.canEdit) {
                i6 = this.rowCount;
                this.rowCount = i6 + 1;
                this.rightsShadowRow = i6;
                return;
            }
            this.rightsShadowRow = -1;
            i6 = this.rowCount;
            this.rowCount = i6 + 1;
            this.cantEditInfoRow = i6;
            return;
        }
        i6 = this.rowCount;
        this.rowCount = i6 + 1;
        this.rightsShadowRow = i6;
        i6 = this.rowCount;
        this.rowCount = i6 + 1;
        this.removeAdminRow = i6;
        i6 = this.rowCount;
        this.rowCount = i6 + 1;
        this.removeAdminShadowRow = i6;
        this.cantEditInfoRow = -1;
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
        this.actionBar.setActionBarMenuOnItemClick(new C41851());
        if (this.canEdit) {
            this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        }
        this.fragmentView = new FrameLayout(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        this.listView = new RecyclerListView(context);
        LayoutManager c41862 = new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(c41862);
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
        this.listView.setOnItemClickListener(new C41933());
        return this.fragmentView;
    }

    public ThemeDescription[] getThemeDescriptions() {
        C41944 c41944 = new C41944();
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
        r10[24] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) c41944, Theme.key_windowBackgroundWhiteGrayText);
        r10[25] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) c41944, Theme.key_windowBackgroundWhiteBlueText);
        r10[26] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[27] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundRed);
        r10[28] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundOrange);
        r10[29] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundViolet);
        r10[30] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundGreen);
        r10[31] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundCyan);
        r10[32] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundBlue);
        r10[33] = new ThemeDescription(null, 0, null, null, null, c41944, Theme.key_avatar_backgroundPink);
        return r10;
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
}
