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
import org.telegram.tgnet.TLRPC$TL_account_resetNotifySettings;
import org.telegram.tgnet.TLRPC$TL_error;
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
    class C31331 extends ActionBarMenuOnItemClick {
        C31331() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                NotificationsSettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3 */
    class C31423 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3$1 */
        class C31361 implements RequestDelegate {

            /* renamed from: org.telegram.ui.NotificationsSettingsActivity$3$1$1 */
            class C31351 implements Runnable {
                C31351() {
                }

                public void run() {
                    MessagesController.getInstance().enableJoined = true;
                    NotificationsSettingsActivity.this.reseting = false;
                    Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                    editor.clear();
                    editor.commit();
                    NotificationsSettingsActivity.this.adapter.notifyDataSetChanged();
                    if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                        Toast.makeText(NotificationsSettingsActivity.this.getParentActivity(), LocaleController.getString("ResetNotificationsText", R.string.ResetNotificationsText), 0).show();
                    }
                }
            }

            C31361() {
            }

            public void run(TLObject response, TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new C31351());
            }
        }

        C31423() {
        }

        public void onItemClick(View view, int position) {
            boolean enabled = false;
            SharedPreferences preferences;
            Editor editor;
            NotificationsSettingsActivity notificationsSettingsActivity;
            boolean z;
            if (position == NotificationsSettingsActivity.this.messageAlertRow || position == NotificationsSettingsActivity.this.groupAlertRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                if (position == NotificationsSettingsActivity.this.messageAlertRow) {
                    enabled = preferences.getBoolean("EnableAll", true);
                    editor.putBoolean("EnableAll", !enabled);
                } else if (position == NotificationsSettingsActivity.this.groupAlertRow) {
                    enabled = preferences.getBoolean("EnableGroup", true);
                    editor.putBoolean("EnableGroup", !enabled);
                }
                editor.commit();
                notificationsSettingsActivity = NotificationsSettingsActivity.this;
                if (position == NotificationsSettingsActivity.this.groupAlertRow) {
                    z = true;
                } else {
                    z = false;
                }
                notificationsSettingsActivity.updateServerNotificationsSettings(z);
            } else if (position == NotificationsSettingsActivity.this.messagePreviewRow || position == NotificationsSettingsActivity.this.groupPreviewRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                if (position == NotificationsSettingsActivity.this.messagePreviewRow) {
                    enabled = preferences.getBoolean("EnablePreviewAll", true);
                    editor.putBoolean("EnablePreviewAll", !enabled);
                } else if (position == NotificationsSettingsActivity.this.groupPreviewRow) {
                    enabled = preferences.getBoolean("EnablePreviewGroup", true);
                    editor.putBoolean("EnablePreviewGroup", !enabled);
                }
                editor.commit();
                notificationsSettingsActivity = NotificationsSettingsActivity.this;
                if (position == NotificationsSettingsActivity.this.groupPreviewRow) {
                    z = true;
                } else {
                    z = false;
                }
                notificationsSettingsActivity.updateServerNotificationsSettings(z);
            } else if (position == NotificationsSettingsActivity.this.messageSoundRow || position == NotificationsSettingsActivity.this.groupSoundRow || position == NotificationsSettingsActivity.this.callsRingtoneRow) {
                try {
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
                    intent.putExtra("android.intent.extra.ringtone.TYPE", position == NotificationsSettingsActivity.this.callsRingtoneRow ? 1 : 2);
                    intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                    intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(position == NotificationsSettingsActivity.this.callsRingtoneRow ? 1 : 2));
                    Uri currentSound = null;
                    String defaultPath = null;
                    Uri defaultUri = position == NotificationsSettingsActivity.this.callsRingtoneRow ? System.DEFAULT_RINGTONE_URI : System.DEFAULT_NOTIFICATION_URI;
                    if (defaultUri != null) {
                        defaultPath = defaultUri.getPath();
                    }
                    String path;
                    if (position == NotificationsSettingsActivity.this.messageSoundRow) {
                        path = preferences.getString("GlobalSoundPath", defaultPath);
                        if (path != null) {
                            if (!path.equals("NoSound")) {
                                currentSound = path.equals(defaultPath) ? defaultUri : Uri.parse(path);
                            }
                        }
                    } else if (position == NotificationsSettingsActivity.this.groupSoundRow) {
                        path = preferences.getString("GroupSoundPath", defaultPath);
                        if (path != null) {
                            if (!path.equals("NoSound")) {
                                currentSound = path.equals(defaultPath) ? defaultUri : Uri.parse(path);
                            }
                        }
                    } else if (position == NotificationsSettingsActivity.this.callsRingtoneRow) {
                        path = preferences.getString("CallsRingtonfePath", defaultPath);
                        if (path != null) {
                            if (!path.equals("NoSound")) {
                                currentSound = path.equals(defaultPath) ? defaultUri : Uri.parse(path);
                            }
                        }
                    }
                    intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", currentSound);
                    NotificationsSettingsActivity.this.startActivityForResult(intent, position);
                } catch (Exception e) {
                    FileLog.e(e);
                }
            } else if (position == NotificationsSettingsActivity.this.resetNotificationsRow) {
                if (!NotificationsSettingsActivity.this.reseting) {
                    NotificationsSettingsActivity.this.reseting = true;
                    ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_resetNotifySettings(), new C31361());
                } else {
                    return;
                }
            } else if (position == NotificationsSettingsActivity.this.inappSoundRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableInAppSounds", true);
                editor.putBoolean("EnableInAppSounds", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.inappVibrateRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableInAppVibrate", true);
                editor.putBoolean("EnableInAppVibrate", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.inappPreviewRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableInAppPreview", true);
                editor.putBoolean("EnableInAppPreview", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.inchatSoundRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableInChatSound", true);
                editor.putBoolean("EnableInChatSound", !enabled);
                editor.commit();
                NotificationsController.getInstance().setInChatSoundEnabled(!enabled);
            } else if (position == NotificationsSettingsActivity.this.inappPriorityRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableInAppPriority", false);
                editor.putBoolean("EnableInAppPriority", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.contactJoinedRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableContactJoined", true);
                MessagesController.getInstance().enableJoined = !enabled;
                editor.putBoolean("EnableContactJoined", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.pinnedMessageRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("PinnedMessages", true);
                editor.putBoolean("PinnedMessages", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.androidAutoAlertRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("EnableAutoNotifications", false);
                editor.putBoolean("EnableAutoNotifications", !enabled);
                editor.commit();
            } else if (position == NotificationsSettingsActivity.this.badgeNumberRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                editor = preferences.edit();
                enabled = preferences.getBoolean("badgeNumber", true);
                editor.putBoolean("badgeNumber", !enabled);
                editor.commit();
                NotificationsController.getInstance().setBadgeEnabled(!enabled);
            } else if (position == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                enabled = preferences.getBoolean("pushConnection", true);
                editor = preferences.edit();
                editor.putBoolean("pushConnection", !enabled);
                editor.commit();
                if (enabled) {
                    ConnectionsManager.getInstance().setPushConnectionEnabled(false);
                } else {
                    ConnectionsManager.getInstance().setPushConnectionEnabled(true);
                }
            } else if (position == NotificationsSettingsActivity.this.notificationsServiceRow) {
                preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                enabled = preferences.getBoolean("pushService", true);
                editor = preferences.edit();
                editor.putBoolean("pushService", !enabled);
                editor.commit();
                if (enabled) {
                    ApplicationLoader.stopPushService();
                } else {
                    ApplicationLoader.startPushService();
                }
            } else if (position == NotificationsSettingsActivity.this.messageLedRow || position == NotificationsSettingsActivity.this.groupLedRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    r1 = position;
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createColorSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), 0, position == NotificationsSettingsActivity.this.groupLedRow, position == NotificationsSettingsActivity.this.messageLedRow, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(r1);
                        }
                    }));
                } else {
                    return;
                }
            } else if (position == NotificationsSettingsActivity.this.messagePopupNotificationRow || position == NotificationsSettingsActivity.this.groupPopupNotificationRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    r1 = position;
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createPopupSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, position == NotificationsSettingsActivity.this.groupPopupNotificationRow, position == NotificationsSettingsActivity.this.messagePopupNotificationRow, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(r1);
                        }
                    }));
                } else {
                    return;
                }
            } else if (position == NotificationsSettingsActivity.this.messageVibrateRow || position == NotificationsSettingsActivity.this.groupVibrateRow || position == NotificationsSettingsActivity.this.callsVibrateRow) {
                if (NotificationsSettingsActivity.this.getParentActivity() != null) {
                    String key = null;
                    if (position == NotificationsSettingsActivity.this.messageVibrateRow) {
                        key = "vibrate_messages";
                    } else if (position == NotificationsSettingsActivity.this.groupVibrateRow) {
                        key = "vibrate_group";
                    } else if (position == NotificationsSettingsActivity.this.callsVibrateRow) {
                        key = "vibrate_calls";
                    }
                    r1 = position;
                    NotificationsSettingsActivity.this.showDialog(AlertsCreator.createVibrationSelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, 0, key, new Runnable() {
                        public void run() {
                            NotificationsSettingsActivity.this.adapter.notifyItemChanged(r1);
                        }
                    }));
                } else {
                    return;
                }
            } else if (position == NotificationsSettingsActivity.this.messagePriorityRow || position == NotificationsSettingsActivity.this.groupPriorityRow) {
                r1 = position;
                NotificationsSettingsActivity.this.showDialog(AlertsCreator.createPrioritySelectDialog(NotificationsSettingsActivity.this.getParentActivity(), NotificationsSettingsActivity.this, 0, position == NotificationsSettingsActivity.this.groupPriorityRow, position == NotificationsSettingsActivity.this.messagePriorityRow, new Runnable() {
                    public void run() {
                        NotificationsSettingsActivity.this.adapter.notifyItemChanged(r1);
                    }
                }));
            } else if (position == NotificationsSettingsActivity.this.repeatRow) {
                Builder builder = new Builder(NotificationsSettingsActivity.this.getParentActivity());
                builder.setTitle(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications));
                r1 = position;
                builder.setItems(new CharSequence[]{LocaleController.getString("RepeatDisabled", R.string.RepeatDisabled), LocaleController.formatPluralString("Minutes", 5), LocaleController.formatPluralString("Minutes", 10), LocaleController.formatPluralString("Minutes", 30), LocaleController.formatPluralString("Hours", 1), LocaleController.formatPluralString("Hours", 2), LocaleController.formatPluralString("Hours", 4)}, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        int minutes = 0;
                        if (which == 1) {
                            minutes = 5;
                        } else if (which == 2) {
                            minutes = 10;
                        } else if (which == 3) {
                            minutes = 30;
                        } else if (which == 4) {
                            minutes = 60;
                        } else if (which == 5) {
                            minutes = 120;
                        } else if (which == 6) {
                            minutes = PsExtractor.VIDEO_STREAM_MASK;
                        }
                        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("repeat_messages", minutes).commit();
                        NotificationsSettingsActivity.this.adapter.notifyItemChanged(r1);
                    }
                });
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                NotificationsSettingsActivity.this.showDialog(builder.create());
            }
            if (view instanceof TextCheckCell) {
                ((TextCheckCell) view).setChecked(!enabled);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            return (position == NotificationsSettingsActivity.this.messageSectionRow || position == NotificationsSettingsActivity.this.groupSectionRow || position == NotificationsSettingsActivity.this.inappSectionRow || position == NotificationsSettingsActivity.this.eventsSectionRow || position == NotificationsSettingsActivity.this.otherSectionRow || position == NotificationsSettingsActivity.this.resetSectionRow || position == NotificationsSettingsActivity.this.eventsSectionRow2 || position == NotificationsSettingsActivity.this.groupSectionRow2 || position == NotificationsSettingsActivity.this.inappSectionRow2 || position == NotificationsSettingsActivity.this.otherSectionRow2 || position == NotificationsSettingsActivity.this.resetSectionRow2 || position == NotificationsSettingsActivity.this.callsSectionRow2 || position == NotificationsSettingsActivity.this.callsSectionRow) ? false : true;
        }

        public int getItemCount() {
            return NotificationsSettingsActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new HeaderCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    view = new TextCheckCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    view = new TextDetailSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 3:
                    view = new TextColorCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    view = new ShadowSectionCell(this.mContext);
                    break;
                default:
                    view = new TextSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            SharedPreferences preferences;
            switch (holder.getItemViewType()) {
                case 0:
                    HeaderCell headerCell = holder.itemView;
                    if (position == NotificationsSettingsActivity.this.messageSectionRow) {
                        headerCell.setText(LocaleController.getString("MessageNotifications", R.string.MessageNotifications));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.groupSectionRow) {
                        headerCell.setText(LocaleController.getString("GroupNotifications", R.string.GroupNotifications));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inappSectionRow) {
                        headerCell.setText(LocaleController.getString("InAppNotifications", R.string.InAppNotifications));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.eventsSectionRow) {
                        headerCell.setText(LocaleController.getString("Events", R.string.Events));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.otherSectionRow) {
                        headerCell.setText(LocaleController.getString("NotificationsOther", R.string.NotificationsOther));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.resetSectionRow) {
                        headerCell.setText(LocaleController.getString("Reset", R.string.Reset));
                        return;
                    } else if (position == NotificationsSettingsActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("VoipNotificationSettings", R.string.VoipNotificationSettings));
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextCheckCell checkCell = holder.itemView;
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    if (position == NotificationsSettingsActivity.this.messageAlertRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("Alert", R.string.Alert), preferences.getBoolean("EnableAll", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.groupAlertRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("Alert", R.string.Alert), preferences.getBoolean("EnableGroup", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.messagePreviewRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("MessagePreview", R.string.MessagePreview), preferences.getBoolean("EnablePreviewAll", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.groupPreviewRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("MessagePreview", R.string.MessagePreview), preferences.getBoolean("EnablePreviewGroup", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inappSoundRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("InAppSounds", R.string.InAppSounds), preferences.getBoolean("EnableInAppSounds", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inappVibrateRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("InAppVibrate", R.string.InAppVibrate), preferences.getBoolean("EnableInAppVibrate", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inappPreviewRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("InAppPreview", R.string.InAppPreview), preferences.getBoolean("EnableInAppPreview", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inappPriorityRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), preferences.getBoolean("EnableInAppPriority", false), false);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.contactJoinedRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("ContactJoined", R.string.ContactJoined), preferences.getBoolean("EnableContactJoined", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.pinnedMessageRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("PinnedMessages", R.string.PinnedMessages), preferences.getBoolean("PinnedMessages", true), false);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.androidAutoAlertRow) {
                        checkCell.setTextAndCheck("Android Auto", preferences.getBoolean("EnableAutoNotifications", false), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.notificationsServiceRow) {
                        checkCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsService", R.string.NotificationsService), LocaleController.getString("NotificationsServiceInfo", R.string.NotificationsServiceInfo), preferences.getBoolean("pushService", true), true, true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                        checkCell.setTextAndValueAndCheck(LocaleController.getString("NotificationsServiceConnection", R.string.NotificationsServiceConnection), LocaleController.getString("NotificationsServiceConnectionInfo", R.string.NotificationsServiceConnectionInfo), preferences.getBoolean("pushConnection", true), true, true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.badgeNumberRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("BadgeNumber", R.string.BadgeNumber), preferences.getBoolean("badgeNumber", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.inchatSoundRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("InChatSound", R.string.InChatSound), preferences.getBoolean("EnableInChatSound", true), true);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.callsVibrateRow) {
                        checkCell.setTextAndCheck(LocaleController.getString("Vibrate", R.string.Vibrate), preferences.getBoolean("EnableCallVibrate", true), true);
                        return;
                    } else {
                        return;
                    }
                case 2:
                    TextDetailSettingsCell settingsCell = holder.itemView;
                    settingsCell.setMultilineDetail(true);
                    settingsCell.setTextAndValue(LocaleController.getString("ResetAllNotifications", R.string.ResetAllNotifications), LocaleController.getString("UndoAllCustom", R.string.UndoAllCustom), false);
                    return;
                case 3:
                    int color;
                    TextColorCell textColorCell = holder.itemView;
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    if (position == NotificationsSettingsActivity.this.messageLedRow) {
                        color = preferences.getInt("MessagesLed", -16776961);
                    } else {
                        color = preferences.getInt("GroupLed", -16776961);
                    }
                    for (int a = 0; a < 9; a++) {
                        if (TextColorCell.colorsToSave[a] == color) {
                            color = TextColorCell.colors[a];
                            textColorCell.setTextAndColor(LocaleController.getString("LedColor", R.string.LedColor), color, true);
                            return;
                        }
                    }
                    textColorCell.setTextAndColor(LocaleController.getString("LedColor", R.string.LedColor), color, true);
                    return;
                case 5:
                    TextSettingsCell textCell = holder.itemView;
                    preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    String value;
                    if (position == NotificationsSettingsActivity.this.messageSoundRow || position == NotificationsSettingsActivity.this.groupSoundRow || position == NotificationsSettingsActivity.this.callsRingtoneRow) {
                        value = null;
                        if (position == NotificationsSettingsActivity.this.messageSoundRow) {
                            value = preferences.getString("GlobalSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                        } else if (position == NotificationsSettingsActivity.this.groupSoundRow) {
                            value = preferences.getString("GroupSound", LocaleController.getString("SoundDefault", R.string.SoundDefault));
                        } else if (position == NotificationsSettingsActivity.this.callsRingtoneRow) {
                            value = preferences.getString("CallsRingtone", LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone));
                        }
                        if (value.equals("NoSound")) {
                            value = LocaleController.getString("NoSound", R.string.NoSound);
                        }
                        if (position == NotificationsSettingsActivity.this.callsRingtoneRow) {
                            textCell.setTextAndValue(LocaleController.getString("VoipSettingsRingtone", R.string.VoipSettingsRingtone), value, true);
                            return;
                        } else {
                            textCell.setTextAndValue(LocaleController.getString("Sound", R.string.Sound), value, true);
                            return;
                        }
                    } else if (position == NotificationsSettingsActivity.this.messageVibrateRow || position == NotificationsSettingsActivity.this.groupVibrateRow || position == NotificationsSettingsActivity.this.callsVibrateRow) {
                        value = 0;
                        if (position == NotificationsSettingsActivity.this.messageVibrateRow) {
                            value = preferences.getInt("vibrate_messages", 0);
                        } else if (position == NotificationsSettingsActivity.this.groupVibrateRow) {
                            value = preferences.getInt("vibrate_group", 0);
                        } else if (position == NotificationsSettingsActivity.this.callsVibrateRow) {
                            value = preferences.getInt("vibrate_calls", 0);
                        }
                        if (value == 0) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), true);
                            return;
                        } else if (value == 1) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Short", R.string.Short), true);
                            return;
                        } else if (value == 2) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), true);
                            return;
                        } else if (value == 3) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Long", R.string.Long), true);
                            return;
                        } else if (value == 4) {
                            textCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("OnlyIfSilent", R.string.OnlyIfSilent), true);
                            return;
                        } else {
                            return;
                        }
                    } else if (position == NotificationsSettingsActivity.this.repeatRow) {
                        int minutes = preferences.getInt("repeat_messages", 60);
                        if (minutes == 0) {
                            value = LocaleController.getString("RepeatNotificationsNever", R.string.RepeatNotificationsNever);
                        } else if (minutes < 60) {
                            value = LocaleController.formatPluralString("Minutes", minutes);
                        } else {
                            value = LocaleController.formatPluralString("Hours", minutes / 60);
                        }
                        textCell.setTextAndValue(LocaleController.getString("RepeatNotifications", R.string.RepeatNotifications), value, false);
                        return;
                    } else if (position == NotificationsSettingsActivity.this.messagePriorityRow || position == NotificationsSettingsActivity.this.groupPriorityRow) {
                        value = 0;
                        if (position == NotificationsSettingsActivity.this.messagePriorityRow) {
                            value = preferences.getInt("priority_messages", 1);
                        } else if (position == NotificationsSettingsActivity.this.groupPriorityRow) {
                            value = preferences.getInt("priority_group", 1);
                        }
                        if (value == 0) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), false);
                            return;
                        } else if (value == 1) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), false);
                            return;
                        } else if (value == 2) {
                            textCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax), false);
                            return;
                        } else {
                            return;
                        }
                    } else if (position == NotificationsSettingsActivity.this.messagePopupNotificationRow || position == NotificationsSettingsActivity.this.groupPopupNotificationRow) {
                        int option = 0;
                        if (position == NotificationsSettingsActivity.this.messagePopupNotificationRow) {
                            option = preferences.getInt("popupAll", 0);
                        } else if (position == NotificationsSettingsActivity.this.groupPopupNotificationRow) {
                            option = preferences.getInt("popupGroup", 0);
                        }
                        if (option == 0) {
                            value = LocaleController.getString("NoPopup", R.string.NoPopup);
                        } else if (option == 1) {
                            value = LocaleController.getString("OnlyWhenScreenOn", R.string.OnlyWhenScreenOn);
                        } else if (option == 2) {
                            value = LocaleController.getString("OnlyWhenScreenOff", R.string.OnlyWhenScreenOff);
                        } else {
                            value = LocaleController.getString("AlwaysShowPopup", R.string.AlwaysShowPopup);
                        }
                        textCell.setTextAndValue(LocaleController.getString("PopupNotification", R.string.PopupNotification), value, true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public int getItemViewType(int position) {
            if (position == NotificationsSettingsActivity.this.messageSectionRow || position == NotificationsSettingsActivity.this.groupSectionRow || position == NotificationsSettingsActivity.this.inappSectionRow || position == NotificationsSettingsActivity.this.eventsSectionRow || position == NotificationsSettingsActivity.this.otherSectionRow || position == NotificationsSettingsActivity.this.resetSectionRow || position == NotificationsSettingsActivity.this.callsSectionRow) {
                return 0;
            }
            if (position == NotificationsSettingsActivity.this.messageAlertRow || position == NotificationsSettingsActivity.this.messagePreviewRow || position == NotificationsSettingsActivity.this.groupAlertRow || position == NotificationsSettingsActivity.this.groupPreviewRow || position == NotificationsSettingsActivity.this.inappSoundRow || position == NotificationsSettingsActivity.this.inappVibrateRow || position == NotificationsSettingsActivity.this.inappPreviewRow || position == NotificationsSettingsActivity.this.contactJoinedRow || position == NotificationsSettingsActivity.this.pinnedMessageRow || position == NotificationsSettingsActivity.this.notificationsServiceRow || position == NotificationsSettingsActivity.this.badgeNumberRow || position == NotificationsSettingsActivity.this.inappPriorityRow || position == NotificationsSettingsActivity.this.inchatSoundRow || position == NotificationsSettingsActivity.this.androidAutoAlertRow || position == NotificationsSettingsActivity.this.notificationsServiceConnectionRow) {
                return 1;
            }
            if (position == NotificationsSettingsActivity.this.messageLedRow || position == NotificationsSettingsActivity.this.groupLedRow) {
                return 3;
            }
            if (position == NotificationsSettingsActivity.this.eventsSectionRow2 || position == NotificationsSettingsActivity.this.groupSectionRow2 || position == NotificationsSettingsActivity.this.inappSectionRow2 || position == NotificationsSettingsActivity.this.otherSectionRow2 || position == NotificationsSettingsActivity.this.resetSectionRow2 || position == NotificationsSettingsActivity.this.callsSectionRow2) {
                return 4;
            }
            if (position == NotificationsSettingsActivity.this.resetNotificationsRow) {
                return 2;
            }
            return 5;
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

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("NotificationsAndSounds", R.string.NotificationsAndSounds));
        this.actionBar.setActionBarMenuOnItemClick(new C31331());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
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
        this.listView.setOnItemClickListener(new C31423());
        return this.fragmentView;
    }

    public void updateServerNotificationsSettings(boolean group) {
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            Uri ringtone = (Uri) data.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            String name = null;
            if (ringtone != null) {
                Ringtone rng = RingtoneManager.getRingtone(getParentActivity(), ringtone);
                if (rng != null) {
                    if (requestCode == this.callsRingtoneRow) {
                        if (ringtone.equals(System.DEFAULT_RINGTONE_URI)) {
                            name = LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone);
                        } else {
                            name = rng.getTitle(getParentActivity());
                        }
                    } else if (ringtone.equals(System.DEFAULT_NOTIFICATION_URI)) {
                        name = LocaleController.getString("SoundDefault", R.string.SoundDefault);
                    } else {
                        name = rng.getTitle(getParentActivity());
                    }
                    rng.stop();
                }
            }
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            if (requestCode == this.messageSoundRow) {
                if (name == null || ringtone == null) {
                    editor.putString("GlobalSound", "NoSound");
                    editor.putString("GlobalSoundPath", "NoSound");
                } else {
                    editor.putString("GlobalSound", name);
                    editor.putString("GlobalSoundPath", ringtone.toString());
                }
            } else if (requestCode == this.groupSoundRow) {
                if (name == null || ringtone == null) {
                    editor.putString("GroupSound", "NoSound");
                    editor.putString("GroupSoundPath", "NoSound");
                } else {
                    editor.putString("GroupSound", name);
                    editor.putString("GroupSoundPath", ringtone.toString());
                }
            } else if (requestCode == this.callsRingtoneRow) {
                if (name == null || ringtone == null) {
                    editor.putString("CallsRingtone", "NoSound");
                    editor.putString("CallsRingtonePath", "NoSound");
                } else {
                    editor.putString("CallsRingtone", name);
                    editor.putString("CallsRingtonePath", ringtone.toString());
                }
            }
            editor.commit();
            this.adapter.notifyItemChanged(requestCode);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.notificationsSettingsUpdated) {
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
}
