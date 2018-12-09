package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.provider.Settings.System;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.exoplayer2.extractor.ts.PsExtractor;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC.TL_account_resetNotifySettings;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextColorCell;
import org.telegram.ui.Cells.TextDetailSettingsCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class NotificationsSettingsActivity extends BaseFragment implements NotificationCenterDelegate {
    private ListAdapter adapter;
    private int androidAutoAlertRow;
    private int badgeNumberRow;
    private int callsRingtoneRow;
    private int callsSectionRow;
    private int callsSectionRow2;
    private int callsVibrateRow;
    private int contactJoinedRow;
    private int eventsSectionRow;
    private int eventsSectionRow2;
    private int groupAlertRow;
    private int groupLedRow;
    private int groupPopupNotificationRow;
    private int groupPreviewRow;
    private int groupPriorityRow;
    private int groupSectionRow;
    private int groupSectionRow2;
    private int groupSoundRow;
    private int groupVibrateRow;
    private int inappPreviewRow;
    private int inappPriorityRow;
    private int inappSectionRow;
    private int inappSectionRow2;
    private int inappSoundRow;
    private int inappVibrateRow;
    private int inchatSoundRow;
    private RecyclerListView listView;
    private int messageAlertRow;
    private int messageLedRow;
    private int messagePopupNotificationRow;
    private int messagePreviewRow;
    private int messagePriorityRow;
    private int messageSectionRow;
    private int messageSoundRow;
    private int messageVibrateRow;
    private int notificationsServiceConnectionRow;
    private int notificationsServiceRow;
    private int otherSectionRow;
    private int otherSectionRow2;
    private int pinnedMessageRow;
    private int repeatRow;
    private int resetNotificationsRow;
    private int resetSectionRow;
    private int resetSectionRow2;
    private boolean reseting = false;
    private int rowCount = 0;

    /* renamed from: org.telegram.ui.NotificationsSettingsActivity$1 */
    class C49721 extends ActionBarMenuOnItemClick {
        C49721() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                NotificationsSettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3 */
    class C49813 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3$1 */
        class C49751 implements RequestDelegate {

            /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3$1$1 */
            class C49741 implements Runnable {
                C49741() {
                }

                public void run() {
                    MessagesController.getInstance().enableJoined = true;
                    NotificationsSettingsActivity.this.reseting = false;
                    Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    edit.clear();
                    edit.commit();
                    NotificationsSettingsActivity.this.adapter.notifyDataSetChanged();
                    if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                        Toast.makeText(NotificationsSettingsActivity.this.getParentActivity(), LocaleController.getString("ResetNotificationsText", R.string.ResetNotificationsText), 0).show();
                    }
                }
            }

            C49751() {
            }

            public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new C49741());
            }
        }

        C49813() {
        }

        public void onItemClick(View view, final int i) {
            boolean z;
            Parcelable parcelable = null;
            int i2 = 2;
            boolean z2 = true;
            SharedPreferences sharedPreferences;
            Editor edit;
            boolean z3;
            if (i == NotificationsSettingsActivity.this.messageAlertRow || i == NotificationsSettingsActivity.this.groupAlertRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                if (i == NotificationsSettingsActivity.this.messageAlertRow) {
                    z3 = sharedPreferences.getBoolean("EnableAll", true);
                    edit.putBoolean("EnableAll", !z3);
                    z = z3;
                } else if (i == NotificationsSettingsActivity.this.groupAlertRow) {
                    z3 = sharedPreferences.getBoolean("EnableGroup", true);
                    edit.putBoolean("EnableGroup", !z3);
                    z = z3;
                } else {
                    z = false;
                }
                edit.commit();
                NotificationsSettingsActivity.this.updateServerNotificationsSettings(i == NotificationsSettingsActivity.this.groupAlertRow);
            } else if (i == NotificationsSettingsActivity.this.messagePreviewRow || i == NotificationsSettingsActivity.this.groupPreviewRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                if (i == NotificationsSettingsActivity.this.messagePreviewRow) {
                    z3 = sharedPreferences.getBoolean("EnablePreviewAll", true);
                    edit.putBoolean("EnablePreviewAll", !z3);
                    z = z3;
                } else if (i == NotificationsSettingsActivity.this.groupPreviewRow) {
                    z3 = sharedPreferences.getBoolean("EnablePreviewGroup", true);
                    edit.putBoolean("EnablePreviewGroup", !z3);
                    z = z3;
                } else {
                    z = false;
                }
                edit.commit();
                NotificationsSettingsActivity.this.updateServerNotificationsSettings(i == NotificationsSettingsActivity.this.groupPreviewRow);
            } else if (i == NotificationsSettingsActivity.this.messageSoundRow || i == NotificationsSettingsActivity.this.groupSoundRow || i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                try {
                    SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.TYPE", i == NotificationsSettingsActivity.this.callsRingtoneRow ? 1 : 2);
                    intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                    String str = "android.intent.extra.ringtone.DEFAULT_URI";
                    if (i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                        i2 = 1;
                    }
                    intent.putExtra(str, RingtoneManager.getDefaultUri(i2));
                    Uri uri = i == NotificationsSettingsActivity.this.callsRingtoneRow ? System.DEFAULT_RINGTONE_URI : System.DEFAULT_NOTIFICATION_URI;
                    str = uri != null ? uri.getPath() : null;
                    String string;
                    if (i == NotificationsSettingsActivity.this.messageSoundRow) {
                        string = sharedPreferences2.getString("GlobalSoundPath", str);
                        if (string == null || string.equals("NoSound")) {
                            uri = null;
                        } else if (!string.equals(str)) {
                            uri = Uri.parse(string);
                        }
                        parcelable = uri;
                    } else if (i == NotificationsSettingsActivity.this.groupSoundRow) {
                        string = sharedPreferences2.getString("GroupSoundPath", str);
                        if (!(string == null || string.equals("NoSound"))) {
                            if (string.equals(str)) {
                                r1 = uri;
                            } else {
                                parcelable = Uri.parse(string);
                            }
                        }
                    } else if (i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                        string = sharedPreferences2.getString("CallsRingtonfePath", str);
                        if (!(string == null || string.equals("NoSound"))) {
                            if (string.equals(str)) {
                                r1 = uri;
                            } else {
                                parcelable = Uri.parse(string);
                            }
                        }
                    }
                    intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", parcelable);
                    NotificationsSettingsActivity.this.startActivityForResult(intent, i);
                    z = false;
                } catch (Throwable e) {
                    FileLog.e(e);
                    z = false;
                }
            } else if (i == NotificationsSettingsActivity.this.resetNotificationsRow) {
                if (!NotificationsSettingsActivity.this.reseting) {
                    NotificationsSettingsActivity.this.reseting = true;
                    ConnectionsManager.getInstance().sendRequest(new TL_account_resetNotifySettings(), new C49751());
                    z = false;
                } else {
                    return;
                }
            } else if (i == NotificationsSettingsActivity.this.inappSoundRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableInAppSounds", true);
                edit.putBoolean("EnableInAppSounds", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.inappVibrateRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableInAppVibrate", true);
                edit.putBoolean("EnableInAppVibrate", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.inappPreviewRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableInAppPreview", true);
                edit.putBoolean("EnableInAppPreview", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.inchatSoundRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableInChatSound", true);
                edit.putBoolean("EnableInChatSound", !z3);
                edit.commit();
                NotificationsController.getInstance().setInChatSoundEnabled(!z3);
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.inappPriorityRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableInAppPriority", false);
                edit.putBoolean("EnableInAppPriority", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.contactJoinedRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableContactJoined", true);
                MessagesController.getInstance().enableJoined = !z3;
                edit.putBoolean("EnableContactJoined", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.pinnedMessageRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("PinnedMessages", true);
                edit.putBoolean("PinnedMessages", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.androidAutoAlertRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("EnableAutoNotifications", false);
                edit.putBoolean("EnableAutoNotifications", !z3);
                edit.commit();
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.badgeNumberRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                edit = sharedPreferences.edit();
                z3 = sharedPreferences.getBoolean("badgeNumber", true);
                edit.putBoolean("badgeNumber", !z3);
                edit.commit();
                NotificationsController.getInstance().setBadgeEnabled(!z3);
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                z3 = sharedPreferences.getBoolean("pushConnection", true);
                edit = sharedPreferences.edit();
                edit.putBoolean("pushConnection", !z3);
                edit.commit();
                if (z3) {
                    ConnectionsManager.getInstance().setPushConnectionEnabled(false);
                } else {
                    ConnectionsManager.getInstance().setPushConnectionEnabled(true);
                }
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.notificationsServiceRow) {
                sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                z3 = sharedPreferences.getBoolean("pushService", true);
                edit = sharedPreferences.edit();
                edit.putBoolean("pushService", !z3);
                edit.commit();
                if (z3) {
                    ApplicationLoader.stopPushService();
                } else {
                    ApplicationLoader.startPushService();
                }
                z = z3;
            } else if (i == NotificationsSettingsActivity.this.messageLedRow || i == NotificationsSettingsActivity.this.groupLedRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createColorSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), 0, i == NotificationsSettingsActivity.this.groupLedRow, i == NotificationsSettingsActivity.this.messageLedRow, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(i);
                        }
                    }));
                    z = false;
                } else {
                    return;
                }
            } else if (i == NotificationsSettingsActivity.this.messagePopupNotificationRow || i == NotificationsSettingsActivity.this.groupPopupNotificationRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createPopupSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, i == NotificationsSettingsActivity.this.groupPopupNotificationRow, i == NotificationsSettingsActivity.this.messagePopupNotificationRow, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(i);
                        }
                    }));
                    z = false;
                } else {
                    return;
                }
            } else if (i == NotificationsSettingsActivity.this.messageVibrateRow || i == NotificationsSettingsActivity.this.groupVibrateRow || i == NotificationsSettingsActivity.this.callsVibrateRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    String str2 = i == NotificationsSettingsActivity.this.messageVibrateRow ? "vibrate_messages" : i == NotificationsSettingsActivity.this.groupVibrateRow ? "vibrate_group" : i == NotificationsSettingsActivity.this.callsVibrateRow ? "vibrate_calls" : null;
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createVibrationSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, 0, str2, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(i);
                        }
                    }));
                    z = false;
                } else {
                    return;
                }
            } else if (i == NotificationsSettingsActivity.this.messagePriorityRow || i == NotificationsSettingsActivity.this.groupPriorityRow) {
                NotificationsSettingsActivity.this.showDialog(AlertsCreator.createPrioritySelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, 0, i == NotificationsSettingsActivity.this.groupPriorityRow, i == NotificationsSettingsActivity.this.messagePriorityRow, new Runnable() {
                    public void run() {
                        NotificationsSettingsActivity.this.adapter.notifyItemChanged(i);
                    }
                }));
                z = false;
            } else {
                if (i == NotificationsSettingsActivity.this.repeatRow) {
                    Builder builder = new Builder(NotificationsSettingsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications));
                    builder.setItems(new CharSequence[]{LocaleController.getString("RepeatDisabled", R.string.RepeatDisabled), LocaleController.formatPluralString("Minutes", 5), LocaleController.formatPluralString("Minutes", 10), LocaleController.formatPluralString("Minutes", 30), LocaleController.formatPluralString("Hours", 1), LocaleController.formatPluralString("Hours", 2), LocaleController.formatPluralString("Hours", 4)}, new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int i2 = 5;
                            if (i != 1) {
                                i2 = i == 2 ? 10 : i == 3 ? 30 : i == 4 ? 60 : i == 5 ? 120 : i == 6 ? PsExtractor.VIDEO_STREAM_MASK : 0;
                            }
                            ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("repeat_messages", i2).commit();
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(i);
                        }
                    });
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    NotificationsSettingsActivity.this.showDialog(builder.create());
                }
                z = false;
            }
            if (view instanceof TextCheckCell) {
                TextCheckCell textCheckCell = (TextCheckCell) view;
                if (z) {
                    z2 = false;
                }
                textCheckCell.setChecked(z2);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return NotificationsSettingsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == NotificationsSettingsActivity.this.messageSectionRow || i == NotificationsSettingsActivity.this.groupSectionRow || i == NotificationsSettingsActivity.this.inappSectionRow || i == NotificationsSettingsActivity.this.eventsSectionRow || i == NotificationsSettingsActivity.this.otherSectionRow || i == NotificationsSettingsActivity.this.resetSectionRow || i == NotificationsSettingsActivity.this.callsSectionRow) ? 0 : (i == NotificationsSettingsActivity.this.messageAlertRow || i == NotificationsSettingsActivity.this.messagePreviewRow || i == NotificationsSettingsActivity.this.groupAlertRow || i == NotificationsSettingsActivity.this.groupPreviewRow || i == NotificationsSettingsActivity.this.inappSoundRow || i == NotificationsSettingsActivity.this.inappVibrateRow || i == NotificationsSettingsActivity.this.inappPreviewRow || i == NotificationsSettingsActivity.this.contactJoinedRow || i == NotificationsSettingsActivity.this.pinnedMessageRow || i == NotificationsSettingsActivity.this.notificationsServiceRow || i == NotificationsSettingsActivity.this.badgeNumberRow || i == NotificationsSettingsActivity.this.inappPriorityRow || i == NotificationsSettingsActivity.this.inchatSoundRow || i == NotificationsSettingsActivity.this.androidAutoAlertRow || i == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) ? 1 : (i == NotificationsSettingsActivity.this.messageLedRow || i == NotificationsSettingsActivity.this.groupLedRow) ? 3 : (i == NotificationsSettingsActivity.this.eventsSectionRow2 || i == NotificationsSettingsActivity.this.groupSectionRow2 || i == NotificationsSettingsActivity.this.inappSectionRow2 || i == NotificationsSettingsActivity.this.otherSectionRow2 || i == NotificationsSettingsActivity.this.resetSectionRow2 || i == NotificationsSettingsActivity.this.callsSectionRow2) ? 4 : i == NotificationsSettingsActivity.this.resetNotificationsRow ? 2 : 5;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return (adapterPosition == NotificationsSettingsActivity.this.messageSectionRow || adapterPosition == NotificationsSettingsActivity.this.groupSectionRow || adapterPosition == NotificationsSettingsActivity.this.inappSectionRow || adapterPosition == NotificationsSettingsActivity.this.eventsSectionRow || adapterPosition == NotificationsSettingsActivity.this.otherSectionRow || adapterPosition == NotificationsSettingsActivity.this.resetSectionRow || adapterPosition == NotificationsSettingsActivity.this.eventsSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.groupSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.inappSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.otherSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.resetSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.callsSectionRow2 || adapterPosition == NotificationsSettingsActivity.this.callsSectionRow) ? false : true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = 0;
            SharedPreferences sharedPreferences;
            int i3;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == NotificationsSettingsActivity.this.messageSectionRow) {
                        headerCell.setText(LocaleController.getString("MessageNotifications", R.string.MessageNotifications));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.groupSectionRow) {
                        headerCell.setText(LocaleController.getString("GroupNotifications", R.string.GroupNotifications));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inappSectionRow) {
                        headerCell.setText(LocaleController.getString("InAppNotifications", R.string.InAppNotifications));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.eventsSectionRow) {
                        headerCell.setText(LocaleController.getString("Events", R.string.Events));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.otherSectionRow) {
                        headerCell.setText(LocaleController.getString("NotificationsOther", R.string.NotificationsOther));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.resetSectionRow) {
                        headerCell.setText(LocaleController.getString("Reset", R.string.Reset));
                        return;
                    } else if (i == NotificationsSettingsActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("VoipNotificationSettings", R.string.VoipNotificationSettings));
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    if (i == NotificationsSettingsActivity.this.messageAlertRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("Alert", R.string.Alert), sharedPreferences.getBoolean("EnableAll", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.groupAlertRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("Alert", R.string.Alert), sharedPreferences.getBoolean("EnableGroup", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.messagePreviewRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("MessagePreview", R.string.MessagePreview), sharedPreferences.getBoolean("EnablePreviewAll", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.groupPreviewRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("MessagePreview", R.string.MessagePreview), sharedPreferences.getBoolean("EnablePreviewGroup", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inappSoundRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("InAppSounds", R.string.InAppSounds), sharedPreferences.getBoolean("EnableInAppSounds", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inappVibrateRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("InAppVibrate", R.string.InAppVibrate), sharedPreferences.getBoolean("EnableInAppVibrate", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inappPreviewRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("InAppPreview", R.string.InAppPreview), sharedPreferences.getBoolean("EnableInAppPreview", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inappPriorityRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), sharedPreferences.getBoolean("EnableInAppPriority", false), false);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.contactJoinedRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("ContactJoined", R.string.ContactJoined), sharedPreferences.getBoolean("EnableContactJoined", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.pinnedMessageRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("PinnedMessages", R.string.PinnedMessages), sharedPreferences.getBoolean("PinnedMessages", true), false);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.androidAutoAlertRow) {
                        textCheckCell.setTextAndCheck("Android Auto", sharedPreferences.getBoolean("EnableAutoNotifications", false), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.notificationsServiceRow) {
                        textCheckCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsService", R.string.NotificationsService), LocaleController.getString("NotificationsServiceInfo", R.string.NotificationsServiceInfo), sharedPreferences.getBoolean("pushService", true), true, true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                        textCheckCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsServiceConnection", R.string.NotificationsServiceConnection), LocaleController.getString("NotificationsServiceConnectionInfo", R.string.NotificationsServiceConnectionInfo), sharedPreferences.getBoolean("pushConnection", true), true, true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.badgeNumberRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("BadgeNumber", R.string.BadgeNumber), sharedPreferences.getBoolean("badgeNumber", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.inchatSoundRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("InChatSound", R.string.InChatSound), sharedPreferences.getBoolean("EnableInChatSound", true), true);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.callsVibrateRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("Vibrate", R.string.Vibrate), sharedPreferences.getBoolean("EnableCallVibrate", true), true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    TextDetailSettingsCell textDetailSettingsCell = (TextDetailSettingsCell) viewHolder.itemView;
                    textDetailSettingsCell.setMultilineDetail(true);
                    textDetailSettingsCell.setTextAndValue(LocaleController.getString("ResetAllNotifications", R.string.ResetAllNotifications), LocaleController.getString("UndoAllCustom", R.string.UndoAllCustom), false);
                    return;
                case 3:
                    TextColorCell textColorCell = (TextColorCell) viewHolder.itemView;
                    SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    i3 = i == NotificationsSettingsActivity.this.messageLedRow ? sharedPreferences2.getInt("MessagesLed", -16776961) : sharedPreferences2.getInt("GroupLed", -16776961);
                    while (i2 < 9) {
                        if (TextColorCell.colorsToSave[i2] == i3) {
                            i3 = TextColorCell.colors[i2];
                            textColorCell.setTextAndColor(LocaleController.getString("LedColor", R.string.LedColor), i3, true);
                            return;
                        }
                        i2++;
                    }
                    textColorCell.setTextAndColor(LocaleController.getString("LedColor", R.string.LedColor), i3, true);
                    return;
                case 5:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    String str;
                    if (i == NotificationsSettingsActivity.this.messageSoundRow || i == NotificationsSettingsActivity.this.groupSoundRow || i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                        str = null;
                        if (i == NotificationsSettingsActivity.this.messageSoundRow) {
                            str = sharedPreferences.getString("GlobalSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                        } else if (i == NotificationsSettingsActivity.this.groupSoundRow) {
                            str = sharedPreferences.getString("GroupSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                        } else if (i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                            str = sharedPreferences.getString("CallsRingtone", LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone));
                        }
                        if (str.equals("NoSound")) {
                            str = LocaleController.getString("NoSound", R.string.NoSound);
                        }
                        if (i == NotificationsSettingsActivity.this.callsRingtoneRow) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("VoipSettingsRingtone", R.string.VoipSettingsRingtone), str, true);
                            return;
                        } else {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Sound", R.string.Sound), str, true);
                            return;
                        }
                    } else if (i == NotificationsSettingsActivity.this.messageVibrateRow || i == NotificationsSettingsActivity.this.groupVibrateRow || i == NotificationsSettingsActivity.this.callsVibrateRow) {
                        if (i == NotificationsSettingsActivity.this.messageVibrateRow) {
                            i2 = sharedPreferences.getInt("vibrate_messages", 0);
                        } else if (i == NotificationsSettingsActivity.this.groupVibrateRow) {
                            i2 = sharedPreferences.getInt("vibrate_group", 0);
                        } else if (i == NotificationsSettingsActivity.this.callsVibrateRow) {
                            i2 = sharedPreferences.getInt("vibrate_calls", 0);
                        }
                        if (i2 == 0) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), true);
                            return;
                        } else if (i2 == 1) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Short", R.string.Short), true);
                            return;
                        } else if (i2 == 2) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), true);
                            return;
                        } else if (i2 == 3) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Long", R.string.Long), true);
                            return;
                        } else if (i2 == 4) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent), true);
                            return;
                        } else {
                            return;
                        }
                    } else if (i == NotificationsSettingsActivity.this.repeatRow) {
                        i3 = sharedPreferences.getInt("repeat_messages", 60);
                        str = i3 == 0 ? LocaleController.getString("RepeatNotificationsNever", R.string.RepeatNotificationsNever) : i3 < 60 ? LocaleController.formatPluralString("Minutes", i3) : LocaleController.formatPluralString("Hours", i3 / 60);
                        textSettingsCell.setTextAndValue(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications), str, false);
                        return;
                    } else if (i == NotificationsSettingsActivity.this.messagePriorityRow || i == NotificationsSettingsActivity.this.groupPriorityRow) {
                        i3 = i == NotificationsSettingsActivity.this.messagePriorityRow ? sharedPreferences.getInt("priority_messages", 1) : i == NotificationsSettingsActivity.this.groupPriorityRow ? sharedPreferences.getInt("priority_group", 1) : 0;
                        if (i3 == 0) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), false);
                            return;
                        } else if (i3 == 1) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), false);
                            return;
                        } else if (i3 == 2) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax), false);
                            return;
                        } else {
                            return;
                        }
                    } else if (i == NotificationsSettingsActivity.this.messagePopupNotificationRow || i == NotificationsSettingsActivity.this.groupPopupNotificationRow) {
                        i3 = i == NotificationsSettingsActivity.this.messagePopupNotificationRow ? sharedPreferences.getInt("popupAll", 0) : i == NotificationsSettingsActivity.this.groupPopupNotificationRow ? sharedPreferences.getInt("popupGroup", 0) : 0;
                        str = i3 == 0 ? LocaleController.getString("NoPopup", R.string.NoPopup) : i3 == 1 ? LocaleController.getString("OnlyWhenScreenOn", R.string.OnlyWhenScreenOn) : i3 == 2 ? LocaleController.getString("OnlyWhenScreenOff", R.string.OnlyWhenScreenOff) : LocaleController.getString("AlwaysShowPopup", R.string.AlwaysShowPopup);
                        textSettingsCell.setTextAndValue(LocaleController.getString("PopupNotification", R.string.PopupNotification), str, true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            switch (i) {
                case 0:
                    headerCell = new HeaderCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    headerCell = new TextCheckCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    headerCell = new TextDetailSettingsCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    headerCell = new TextColorCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    headerCell = new ShadowSectionCell(this.mContext);
                    break;
                default:
                    headerCell = new TextSettingsCell(this.mContext);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(headerCell);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("NotificationsAndSounds", R.string.NotificationsAndSounds));
        this.actionBar.setActionBarMenuOnItemClick(new C49721());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        });
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setOnItemClickListener(new C49813());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.notificationsSettingsUpdated) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[22];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{HeaderCell.class, TextCheckCell.class, TextDetailSettingsCell.class, TextColorCell.class, TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextColorCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        return themeDescriptionArr;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        if (i2 == -1) {
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            String str = null;
            if (uri != null) {
                Ringtone ringtone = RingtoneManager.getRingtone(getParentActivity(), uri);
                if (ringtone != null) {
                    str = i == this.callsRingtoneRow ? uri.equals(System.DEFAULT_RINGTONE_URI) ? LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone) : ringtone.getTitle(getParentActivity()) : uri.equals(System.DEFAULT_NOTIFICATION_URI) ? LocaleController.getString("SoundDefault", R.string.SoundDefault) : ringtone.getTitle(getParentActivity());
                    ringtone.stop();
                }
            }
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            if (i == this.messageSoundRow) {
                if (str == null || uri == null) {
                    edit.putString("GlobalSound", "NoSound");
                    edit.putString("GlobalSoundPath", "NoSound");
                } else {
                    edit.putString("GlobalSound", str);
                    edit.putString("GlobalSoundPath", uri.toString());
                }
            } else if (i == this.groupSoundRow) {
                if (str == null || uri == null) {
                    edit.putString("GroupSound", "NoSound");
                    edit.putString("GroupSoundPath", "NoSound");
                } else {
                    edit.putString("GroupSound", str);
                    edit.putString("GroupSoundPath", uri.toString());
                }
            } else if (i == this.callsRingtoneRow) {
                if (str == null || uri == null) {
                    edit.putString("CallsRingtone", "NoSound");
                    edit.putString("CallsRingtonePath", "NoSound");
                } else {
                    edit.putString("CallsRingtone", str);
                    edit.putString("CallsRingtonePath", uri.toString());
                }
            }
            edit.commit();
            this.adapter.notifyItemChanged(i);
        }
    }

    public boolean onFragmentCreate() {
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.messageSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messageAlertRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagePreviewRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messageLedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messageVibrateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messagePopupNotificationRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.messageSoundRow = i;
        if (VERSION.SDK_INT >= 21) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.messagePriorityRow = i;
        } else {
            this.messagePriorityRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupAlertRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupPreviewRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupLedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupVibrateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupPopupNotificationRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupSoundRow = i;
        if (VERSION.SDK_INT >= 21) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.groupPriorityRow = i;
        } else {
            this.groupPriorityRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inappSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inappSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inappSoundRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inappVibrateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inappPreviewRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.inchatSoundRow = i;
        if (VERSION.SDK_INT >= 21) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.inappPriorityRow = i;
        } else {
            this.inappPriorityRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsVibrateRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsRingtoneRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.eventsSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.eventsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.contactJoinedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.pinnedMessageRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.otherSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.otherSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationsServiceRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.notificationsServiceConnectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.badgeNumberRow = i;
        this.androidAutoAlertRow = -1;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.repeatRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetSectionRow2 = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.resetNotificationsRow = i;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
    }

    public void updateServerNotificationsSettings(boolean z) {
    }
}
