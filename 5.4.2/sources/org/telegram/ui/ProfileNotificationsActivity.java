package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
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
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings.System;
import android.text.TextUtils.TruncateAt;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collection;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.RadioCell;
import org.telegram.ui.Cells.TextCheckBoxCell;
import org.telegram.ui.Cells.TextColorCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class ProfileNotificationsActivity extends BaseFragment implements NotificationCenterDelegate {
    private ListAdapter adapter;
    private AnimatorSet animatorSet;
    private int callsRow;
    private int callsVibrateRow;
    private int colorRow;
    private boolean customEnabled;
    private int customInfoRow;
    private int customRow;
    private long dialog_id;
    private int generalRow;
    private int ledInfoRow;
    private int ledRow;
    private RecyclerListView listView;
    private boolean notificationsEnabled;
    private int popupDisabledRow;
    private int popupEnabledRow;
    private int popupInfoRow;
    private int popupRow;
    private int priorityInfoRow;
    private int priorityRow;
    private int ringtoneInfoRow;
    private int ringtoneRow;
    private int rowCount;
    private int smartRow;
    private int soundRow;
    private int vibrateRow;

    /* renamed from: org.telegram.ui.ProfileNotificationsActivity$1 */
    class C51351 extends ActionBarMenuOnItemClick {
        C51351() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                if (ProfileNotificationsActivity.this.notificationsEnabled && ProfileNotificationsActivity.this.customEnabled) {
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("notify2_" + ProfileNotificationsActivity.this.dialog_id, 0).commit();
                }
                ProfileNotificationsActivity.this.finishFragment();
            }
        }
    }

    private class ListAdapter extends Adapter {
        private Context context;

        public ListAdapter(Context context) {
            this.context = context;
        }

        public int getItemCount() {
            return ProfileNotificationsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == ProfileNotificationsActivity.this.generalRow || i == ProfileNotificationsActivity.this.popupRow || i == ProfileNotificationsActivity.this.ledRow || i == ProfileNotificationsActivity.this.callsRow) ? 0 : (i == ProfileNotificationsActivity.this.soundRow || i == ProfileNotificationsActivity.this.vibrateRow || i == ProfileNotificationsActivity.this.priorityRow || i == ProfileNotificationsActivity.this.smartRow || i == ProfileNotificationsActivity.this.ringtoneRow || i == ProfileNotificationsActivity.this.callsVibrateRow) ? 1 : (i == ProfileNotificationsActivity.this.popupInfoRow || i == ProfileNotificationsActivity.this.ledInfoRow || i == ProfileNotificationsActivity.this.priorityInfoRow || i == ProfileNotificationsActivity.this.customInfoRow || i == ProfileNotificationsActivity.this.ringtoneInfoRow) ? 2 : i == ProfileNotificationsActivity.this.colorRow ? 3 : (i == ProfileNotificationsActivity.this.popupEnabledRow || i == ProfileNotificationsActivity.this.popupDisabledRow) ? 4 : i == ProfileNotificationsActivity.this.customRow ? 5 : 0;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            boolean z2 = false;
            int i2;
            String string;
            String string2;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == ProfileNotificationsActivity.this.generalRow) {
                        headerCell.setText(LocaleController.getString("General", R.string.General));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.popupRow) {
                        headerCell.setText(LocaleController.getString("ProfilePopupNotification", R.string.ProfilePopupNotification));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.ledRow) {
                        headerCell.setText(LocaleController.getString("NotificationsLed", R.string.NotificationsLed));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.callsRow) {
                        headerCell.setText(LocaleController.getString("VoipNotificationSettings", R.string.VoipNotificationSettings));
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    String string3;
                    if (i == ProfileNotificationsActivity.this.soundRow) {
                        string3 = sharedPreferences.getString("sound_" + ProfileNotificationsActivity.this.dialog_id, LocaleController.getString("SoundDefault", R.string.SoundDefault));
                        if (string3.equals("NoSound")) {
                            string3 = LocaleController.getString("NoSound", R.string.NoSound);
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("Sound", R.string.Sound), string3, true);
                        return;
                    } else if (i == ProfileNotificationsActivity.this.ringtoneRow) {
                        string3 = sharedPreferences.getString("ringtone_" + ProfileNotificationsActivity.this.dialog_id, LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone));
                        if (string3.equals("NoSound")) {
                            string3 = LocaleController.getString("NoSound", R.string.NoSound);
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("VoipSettingsRingtone", R.string.VoipSettingsRingtone), string3, true);
                        return;
                    } else if (i == ProfileNotificationsActivity.this.vibrateRow) {
                        i2 = sharedPreferences.getInt("vibrate_" + ProfileNotificationsActivity.this.dialog_id, 0);
                        if (i2 == 0 || i2 == 4) {
                            string = LocaleController.getString("Vibrate", R.string.Vibrate);
                            string2 = LocaleController.getString("VibrationDefault", R.string.VibrationDefault);
                            if (!(ProfileNotificationsActivity.this.smartRow == -1 && ProfileNotificationsActivity.this.priorityRow == -1)) {
                                z2 = true;
                            }
                            textSettingsCell.setTextAndValue(string, string2, z2);
                            return;
                        } else if (i2 == 1) {
                            string = LocaleController.getString("Vibrate", R.string.Vibrate);
                            string2 = LocaleController.getString("Short", R.string.Short);
                            if (!(ProfileNotificationsActivity.this.smartRow == -1 && ProfileNotificationsActivity.this.priorityRow == -1)) {
                                z2 = true;
                            }
                            textSettingsCell.setTextAndValue(string, string2, z2);
                            return;
                        } else if (i2 == 2) {
                            string = LocaleController.getString("Vibrate", R.string.Vibrate);
                            string2 = LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled);
                            if (!(ProfileNotificationsActivity.this.smartRow == -1 && ProfileNotificationsActivity.this.priorityRow == -1)) {
                                z2 = true;
                            }
                            textSettingsCell.setTextAndValue(string, string2, z2);
                            return;
                        } else if (i2 == 3) {
                            string = LocaleController.getString("Vibrate", R.string.Vibrate);
                            string2 = LocaleController.getString("Long", R.string.Long);
                            if (!(ProfileNotificationsActivity.this.smartRow == -1 && ProfileNotificationsActivity.this.priorityRow == -1)) {
                                z2 = true;
                            }
                            textSettingsCell.setTextAndValue(string, string2, z2);
                            return;
                        } else {
                            return;
                        }
                    } else if (i == ProfileNotificationsActivity.this.priorityRow) {
                        i2 = sharedPreferences.getInt("priority_" + ProfileNotificationsActivity.this.dialog_id, 3);
                        if (i2 == 0) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityDefault", R.string.NotificationsPriorityDefault), false);
                            return;
                        } else if (i2 == 1) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityHigh", R.string.NotificationsPriorityHigh), false);
                            return;
                        } else if (i2 == 2) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPriorityMax", R.string.NotificationsPriorityMax), false);
                            return;
                        } else if (i2 == 3) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NotificationsPriority", R.string.NotificationsPriority), LocaleController.getString("NotificationsPrioritySettings", R.string.NotificationsPrioritySettings), false);
                            return;
                        } else {
                            return;
                        }
                    } else if (i == ProfileNotificationsActivity.this.smartRow) {
                        int i3 = sharedPreferences.getInt("smart_max_count_" + ProfileNotificationsActivity.this.dialog_id, 2);
                        i2 = sharedPreferences.getInt("smart_delay_" + ProfileNotificationsActivity.this.dialog_id, 180);
                        if (i3 == 0) {
                            string = LocaleController.getString("SmartNotifications", R.string.SmartNotifications);
                            string2 = LocaleController.getString("SmartNotificationsDisabled", R.string.SmartNotificationsDisabled);
                            if (ProfileNotificationsActivity.this.priorityRow == -1) {
                                z = false;
                            }
                            textSettingsCell.setTextAndValue(string, string2, z);
                            return;
                        }
                        string = LocaleController.formatPluralString("Minutes", i2 / 60);
                        String string4 = LocaleController.getString("SmartNotifications", R.string.SmartNotifications);
                        string = LocaleController.formatString("SmartNotificationsInfo", R.string.SmartNotificationsInfo, new Object[]{Integer.valueOf(i3), string});
                        if (ProfileNotificationsActivity.this.priorityRow == -1) {
                            z = false;
                        }
                        textSettingsCell.setTextAndValue(string4, string, z);
                        return;
                    } else if (i == ProfileNotificationsActivity.this.callsVibrateRow) {
                        int i4 = sharedPreferences.getInt("calls_vibrate_" + ProfileNotificationsActivity.this.dialog_id, 0);
                        if (i4 == 0 || i4 == 4) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDefault", R.string.VibrationDefault), true);
                            return;
                        } else if (i4 == 1) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Short", R.string.Short), true);
                            return;
                        } else if (i4 == 2) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("VibrationDisabled", R.string.VibrationDisabled), true);
                            return;
                        } else if (i4 == 3) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("Vibrate", R.string.Vibrate), LocaleController.getString("Long", R.string.Long), true);
                            return;
                        } else {
                            return;
                        }
                    } else {
                        return;
                    }
                case 2:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == ProfileNotificationsActivity.this.popupInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("ProfilePopupNotificationInfo", R.string.ProfilePopupNotificationInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.ledInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("NotificationsLedInfo", R.string.NotificationsLedInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.priorityInfoRow) {
                        if (ProfileNotificationsActivity.this.priorityRow == -1) {
                            textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("PriorityInfo", R.string.PriorityInfo));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.customInfoRow) {
                        textInfoPrivacyCell.setText(null);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.ringtoneInfoRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("VoipRingtoneInfo", R.string.VoipRingtoneInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextColorCell textColorCell = (TextColorCell) viewHolder.itemView;
                    SharedPreferences sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    int i5 = sharedPreferences2.contains(new StringBuilder().append("color_").append(ProfileNotificationsActivity.this.dialog_id).toString()) ? sharedPreferences2.getInt("color_" + ProfileNotificationsActivity.this.dialog_id, -16776961) : ((int) ProfileNotificationsActivity.this.dialog_id) < 0 ? sharedPreferences2.getInt("GroupLed", -16776961) : sharedPreferences2.getInt("MessagesLed", -16776961);
                    for (i2 = 0; i2 < 9; i2++) {
                        if (TextColorCell.colorsToSave[i2] == i5) {
                            i5 = TextColorCell.colors[i2];
                            textColorCell.setTextAndColor(LocaleController.getString("NotificationsLedColor", R.string.NotificationsLedColor), i5, false);
                            return;
                        }
                    }
                    textColorCell.setTextAndColor(LocaleController.getString("NotificationsLedColor", R.string.NotificationsLedColor), i5, false);
                    return;
                case 4:
                    RadioCell radioCell = (RadioCell) viewHolder.itemView;
                    SharedPreferences sharedPreferences3 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    i2 = sharedPreferences3.getInt("popup_" + ProfileNotificationsActivity.this.dialog_id, 0);
                    if (i2 == 0) {
                        i2 = sharedPreferences3.getInt(((int) ProfileNotificationsActivity.this.dialog_id) < 0 ? "popupGroup" : "popupAll", 0) != 0 ? 1 : 2;
                    }
                    if (i == ProfileNotificationsActivity.this.popupEnabledRow) {
                        string2 = LocaleController.getString("PopupEnabled", R.string.PopupEnabled);
                        if (i2 == 1) {
                            z2 = true;
                        }
                        radioCell.setText(string2, z2, true);
                        radioCell.setTag(Integer.valueOf(1));
                        return;
                    } else if (i == ProfileNotificationsActivity.this.popupDisabledRow) {
                        String string5 = LocaleController.getString("PopupDisabled", R.string.PopupDisabled);
                        if (i2 != 2) {
                            z = false;
                        }
                        radioCell.setText(string5, z, false);
                        radioCell.setTag(Integer.valueOf(2));
                        return;
                    } else {
                        return;
                    }
                case 5:
                    TextCheckBoxCell textCheckBoxCell = (TextCheckBoxCell) viewHolder.itemView;
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    string = LocaleController.getString("NotificationsEnableCustom", R.string.NotificationsEnableCustom);
                    if (!(ProfileNotificationsActivity.this.customEnabled && ProfileNotificationsActivity.this.notificationsEnabled)) {
                        z = false;
                    }
                    textCheckBoxCell.setTextAndCheck(string, z, false);
                    return;
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View headerCell;
            switch (i) {
                case 0:
                    headerCell = new HeaderCell(this.context);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    headerCell = new TextSettingsCell(this.context);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 2:
                    headerCell = new TextInfoPrivacyCell(this.context);
                    break;
                case 3:
                    headerCell = new TextColorCell(this.context);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 4:
                    headerCell = new RadioCell(this.context);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    headerCell = new TextCheckBoxCell(this.context);
                    headerCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            headerCell.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(headerCell);
        }

        public void onViewAttachedToWindow(ViewHolder viewHolder) {
            boolean z = true;
            if (viewHolder.getItemViewType() != 0) {
                switch (viewHolder.getItemViewType()) {
                    case 1:
                        TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                        if (!(ProfileNotificationsActivity.this.customEnabled && ProfileNotificationsActivity.this.notificationsEnabled)) {
                            z = false;
                        }
                        textSettingsCell.setEnabled(z, null);
                        return;
                    case 2:
                        TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                        if (!(ProfileNotificationsActivity.this.customEnabled && ProfileNotificationsActivity.this.notificationsEnabled)) {
                            z = false;
                        }
                        textInfoPrivacyCell.setEnabled(z, null);
                        return;
                    case 3:
                        TextColorCell textColorCell = (TextColorCell) viewHolder.itemView;
                        if (!(ProfileNotificationsActivity.this.customEnabled && ProfileNotificationsActivity.this.notificationsEnabled)) {
                            z = false;
                        }
                        textColorCell.setEnabled(z, null);
                        return;
                    case 4:
                        RadioCell radioCell = (RadioCell) viewHolder.itemView;
                        if (!(ProfileNotificationsActivity.this.customEnabled && ProfileNotificationsActivity.this.notificationsEnabled)) {
                            z = false;
                        }
                        radioCell.setEnabled(z, null);
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public ProfileNotificationsActivity(Bundle bundle) {
        super(bundle);
        this.dialog_id = bundle.getLong("dialog_id");
    }

    public View createView(final Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("CustomNotifications", R.string.CustomNotifications));
        this.actionBar.setActionBarMenuOnItemClick(new C51351());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        RecyclerListView recyclerListView = this.listView;
        Adapter listAdapter = new ListAdapter(context);
        this.adapter = listAdapter;
        recyclerListView.setAdapter(listAdapter);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setLayoutManager(new LinearLayoutManager(context) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        });
        this.listView.setOnItemClickListener(new OnItemClickListener() {

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$1 */
            class C51371 extends AnimatorListenerAdapter {
                C51371() {
                }

                public void onAnimationEnd(Animator animator) {
                    if (animator.equals(ProfileNotificationsActivity.this.animatorSet)) {
                        ProfileNotificationsActivity.this.animatorSet = null;
                    }
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$2 */
            class C51382 implements Runnable {
                C51382() {
                }

                public void run() {
                    if (ProfileNotificationsActivity.this.adapter != null) {
                        ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.vibrateRow);
                    }
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$3 */
            class C51393 implements Runnable {
                C51393() {
                }

                public void run() {
                    if (ProfileNotificationsActivity.this.adapter != null) {
                        ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.callsVibrateRow);
                    }
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$4 */
            class C51404 implements Runnable {
                C51404() {
                }

                public void run() {
                    if (ProfileNotificationsActivity.this.adapter != null) {
                        ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.priorityRow);
                    }
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$6 */
            class C51436 implements OnItemClickListener {
                C51436() {
                }

                public void onItemClick(View view, int i) {
                    if (i >= 0 && i < 100) {
                        int i2 = (i % 10) + 1;
                        int i3 = (i / 10) + 1;
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                        sharedPreferences.edit().putInt("smart_max_count_" + ProfileNotificationsActivity.this.dialog_id, i2).commit();
                        sharedPreferences.edit().putInt("smart_delay_" + ProfileNotificationsActivity.this.dialog_id, i3 * 60).commit();
                        if (ProfileNotificationsActivity.this.adapter != null) {
                            ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.smartRow);
                        }
                        ProfileNotificationsActivity.this.dismissCurrentDialig();
                    }
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$7 */
            class C51447 implements OnClickListener {
                C51447() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("smart_max_count_" + ProfileNotificationsActivity.this.dialog_id, 0).commit();
                    if (ProfileNotificationsActivity.this.adapter != null) {
                        ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.smartRow);
                    }
                    ProfileNotificationsActivity.this.dismissCurrentDialig();
                }
            }

            /* renamed from: org.telegram.ui.ProfileNotificationsActivity$3$8 */
            class C51458 implements Runnable {
                C51458() {
                }

                public void run() {
                    if (ProfileNotificationsActivity.this.adapter != null) {
                        ProfileNotificationsActivity.this.adapter.notifyItemChanged(ProfileNotificationsActivity.this.colorRow);
                    }
                }
            }

            public void onItemClick(View view, int i) {
                Parcelable parcelable = null;
                int i2 = 2;
                int i3 = 0;
                if (i == ProfileNotificationsActivity.this.customRow && (view instanceof TextCheckBoxCell)) {
                    SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                    ProfileNotificationsActivity.this.customEnabled = !ProfileNotificationsActivity.this.customEnabled;
                    ProfileNotificationsActivity.this.notificationsEnabled = ProfileNotificationsActivity.this.customEnabled;
                    sharedPreferences.edit().putBoolean("custom_" + ProfileNotificationsActivity.this.dialog_id, ProfileNotificationsActivity.this.customEnabled).commit();
                    ((TextCheckBoxCell) view).setChecked(ProfileNotificationsActivity.this.customEnabled);
                    int childCount = ProfileNotificationsActivity.this.listView.getChildCount();
                    Collection arrayList = new ArrayList();
                    while (i3 < childCount) {
                        Holder holder = (Holder) ProfileNotificationsActivity.this.listView.getChildViewHolder(ProfileNotificationsActivity.this.listView.getChildAt(i3));
                        int itemViewType = holder.getItemViewType();
                        if (!(holder.getAdapterPosition() == ProfileNotificationsActivity.this.customRow || itemViewType == 0)) {
                            switch (itemViewType) {
                                case 1:
                                    ((TextSettingsCell) holder.itemView).setEnabled(ProfileNotificationsActivity.this.customEnabled, arrayList);
                                    break;
                                case 2:
                                    ((TextInfoPrivacyCell) holder.itemView).setEnabled(ProfileNotificationsActivity.this.customEnabled, arrayList);
                                    break;
                                case 3:
                                    ((TextColorCell) holder.itemView).setEnabled(ProfileNotificationsActivity.this.customEnabled, arrayList);
                                    break;
                                case 4:
                                    ((RadioCell) holder.itemView).setEnabled(ProfileNotificationsActivity.this.customEnabled, arrayList);
                                    break;
                                default:
                                    break;
                            }
                        }
                        i3++;
                    }
                    if (!arrayList.isEmpty()) {
                        if (ProfileNotificationsActivity.this.animatorSet != null) {
                            ProfileNotificationsActivity.this.animatorSet.cancel();
                        }
                        ProfileNotificationsActivity.this.animatorSet = new AnimatorSet();
                        ProfileNotificationsActivity.this.animatorSet.playTogether(arrayList);
                        ProfileNotificationsActivity.this.animatorSet.addListener(new C51371());
                        ProfileNotificationsActivity.this.animatorSet.setDuration(150);
                        ProfileNotificationsActivity.this.animatorSet.start();
                    }
                } else if (!ProfileNotificationsActivity.this.customEnabled) {
                } else {
                    Intent intent;
                    SharedPreferences sharedPreferences2;
                    String path;
                    String string;
                    if (i == ProfileNotificationsActivity.this.soundRow) {
                        try {
                            intent = new Intent("android.intent.action.RINGTONE_PICKER");
                            intent.putExtra("android.intent.extra.ringtone.TYPE", 2);
                            intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                            intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(2));
                            sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                            Uri uri = System.DEFAULT_NOTIFICATION_URI;
                            path = uri != null ? uri.getPath() : null;
                            string = sharedPreferences2.getString("sound_path_" + ProfileNotificationsActivity.this.dialog_id, path);
                            if (!(string == null || string.equals("NoSound"))) {
                                parcelable = string.equals(path) ? uri : Uri.parse(string);
                            }
                            intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", parcelable);
                            ProfileNotificationsActivity.this.startActivityForResult(intent, 12);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    } else if (i == ProfileNotificationsActivity.this.ringtoneRow) {
                        try {
                            intent = new Intent("android.intent.action.RINGTONE_PICKER");
                            intent.putExtra("android.intent.extra.ringtone.TYPE", 1);
                            intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true);
                            intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(1));
                            sharedPreferences2 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                            Parcelable parcelable2 = System.DEFAULT_NOTIFICATION_URI;
                            path = parcelable2 != null ? parcelable2.getPath() : null;
                            string = sharedPreferences2.getString("ringtone_path_" + ProfileNotificationsActivity.this.dialog_id, path);
                            if (string == null || string.equals("NoSound")) {
                                parcelable2 = null;
                            } else if (!string.equals(path)) {
                                parcelable2 = Uri.parse(string);
                            }
                            intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", parcelable2);
                            ProfileNotificationsActivity.this.startActivityForResult(intent, 13);
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    } else if (i == ProfileNotificationsActivity.this.vibrateRow) {
                        ProfileNotificationsActivity.this.showDialog(AlertsCreator.createVibrationSelectDialog(ProfileNotificationsActivity.this.getParentActivity(), ProfileNotificationsActivity.this, ProfileNotificationsActivity.this.dialog_id, false, false, new C51382()));
                    } else if (i == ProfileNotificationsActivity.this.callsVibrateRow) {
                        ProfileNotificationsActivity.this.showDialog(AlertsCreator.createVibrationSelectDialog(ProfileNotificationsActivity.this.getParentActivity(), ProfileNotificationsActivity.this, ProfileNotificationsActivity.this.dialog_id, "calls_vibrate_", new C51393()));
                    } else if (i == ProfileNotificationsActivity.this.priorityRow) {
                        ProfileNotificationsActivity.this.showDialog(AlertsCreator.createPrioritySelectDialog(ProfileNotificationsActivity.this.getParentActivity(), ProfileNotificationsActivity.this, ProfileNotificationsActivity.this.dialog_id, false, false, new C51404()));
                    } else if (i == ProfileNotificationsActivity.this.smartRow) {
                        if (ProfileNotificationsActivity.this.getParentActivity() != null) {
                            final Context parentActivity = ProfileNotificationsActivity.this.getParentActivity();
                            SharedPreferences sharedPreferences3 = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                            int i4 = sharedPreferences3.getInt("smart_max_count_" + ProfileNotificationsActivity.this.dialog_id, 2);
                            int i5 = sharedPreferences3.getInt("smart_delay_" + ProfileNotificationsActivity.this.dialog_id, 180);
                            if (i4 != 0) {
                                i2 = i4;
                            }
                            i2 = (i2 + (((i5 / 60) - 1) * 10)) - 1;
                            View recyclerListView = new RecyclerListView(ProfileNotificationsActivity.this.getParentActivity());
                            recyclerListView.setLayoutManager(new LinearLayoutManager(context, 1, false));
                            recyclerListView.setClipToPadding(true);
                            recyclerListView.setAdapter(new SelectionAdapter() {
                                public int getItemCount() {
                                    return 100;
                                }

                                public boolean isEnabled(ViewHolder viewHolder) {
                                    return true;
                                }

                                public void onBindViewHolder(ViewHolder viewHolder, int i) {
                                    TextView textView = (TextView) viewHolder.itemView;
                                    textView.setTextColor(Theme.getColor(i == i2 ? Theme.key_dialogTextGray : Theme.key_dialogTextBlack));
                                    int i2 = i / 10;
                                    String formatPluralString = LocaleController.formatPluralString("Times", (i % 10) + 1);
                                    String formatPluralString2 = LocaleController.formatPluralString("Minutes", i2 + 1);
                                    textView.setText(LocaleController.formatString("SmartNotificationsDetail", R.string.SmartNotificationsDetail, new Object[]{formatPluralString, formatPluralString2}));
                                }

                                public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                                    View c51411 = new TextView(parentActivity) {
                                        protected void onMeasure(int i, int i2) {
                                            super.onMeasure(MeasureSpec.makeMeasureSpec(i, 1073741824), MeasureSpec.makeMeasureSpec(AndroidUtilities.dp(48.0f), 1073741824));
                                        }
                                    };
                                    TextView textView = (TextView) c51411;
                                    textView.setGravity(17);
                                    textView.setTextSize(1, 18.0f);
                                    textView.setSingleLine(true);
                                    textView.setEllipsize(TruncateAt.END);
                                    textView.setLayoutParams(new LayoutParams(-1, -2));
                                    return new Holder(c51411);
                                }
                            });
                            recyclerListView.setPadding(0, AndroidUtilities.dp(12.0f), 0, AndroidUtilities.dp(8.0f));
                            recyclerListView.setOnItemClickListener(new C51436());
                            Builder builder = new Builder(ProfileNotificationsActivity.this.getParentActivity());
                            builder.setTitle(LocaleController.getString("SmartNotificationsAlert", R.string.SmartNotificationsAlert));
                            builder.setView(recyclerListView);
                            builder.setPositiveButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                            builder.setNegativeButton(LocaleController.getString("SmartNotificationsDisabled", R.string.SmartNotificationsDisabled), new C51447());
                            ProfileNotificationsActivity.this.showDialog(builder.create());
                        }
                    } else if (i == ProfileNotificationsActivity.this.colorRow) {
                        if (ProfileNotificationsActivity.this.getParentActivity() != null) {
                            ProfileNotificationsActivity.this.showDialog(AlertsCreator.createColorSelectDialog(ProfileNotificationsActivity.this.getParentActivity(), ProfileNotificationsActivity.this.dialog_id, false, false, new C51458()));
                        }
                    } else if (i == ProfileNotificationsActivity.this.popupEnabledRow) {
                        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("popup_" + ProfileNotificationsActivity.this.dialog_id, 1).commit();
                        ((RadioCell) view).setChecked(true, true);
                        r0 = ProfileNotificationsActivity.this.listView.findViewWithTag(Integer.valueOf(2));
                        if (r0 != null) {
                            ((RadioCell) r0).setChecked(false, true);
                        }
                    } else if (i == ProfileNotificationsActivity.this.popupDisabledRow) {
                        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().putInt("popup_" + ProfileNotificationsActivity.this.dialog_id, 2).commit();
                        ((RadioCell) view).setChecked(true, true);
                        r0 = ProfileNotificationsActivity.this.listView.findViewWithTag(Integer.valueOf(1));
                        if (r0 != null) {
                            ((RadioCell) r0).setChecked(false, true);
                        }
                    }
                }
            }
        });
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.notificationsSettingsUpdated) {
            this.adapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[23];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{HeaderCell.class, TextSettingsCell.class, TextColorCell.class, RadioCell.class, TextCheckBoxCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextColorCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareUnchecked);
        themeDescriptionArr[20] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareDisabled);
        themeDescriptionArr[21] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareBackground);
        themeDescriptionArr[22] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckBoxCell.class}, null, null, null, Theme.key_checkboxSquareCheck);
        return themeDescriptionArr;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        if (i2 == -1 && intent != null) {
            Uri uri = (Uri) intent.getParcelableExtra("android.intent.extra.ringtone.PICKED_URI");
            String str = null;
            if (uri != null) {
                Ringtone ringtone = RingtoneManager.getRingtone(ApplicationLoader.applicationContext, uri);
                if (ringtone != null) {
                    str = i == 13 ? uri.equals(System.DEFAULT_RINGTONE_URI) ? LocaleController.getString("DefaultRingtone", R.string.DefaultRingtone) : ringtone.getTitle(getParentActivity()) : uri.equals(System.DEFAULT_NOTIFICATION_URI) ? LocaleController.getString("SoundDefault", R.string.SoundDefault) : ringtone.getTitle(getParentActivity());
                    ringtone.stop();
                }
            }
            Editor edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            if (i == 12) {
                if (str != null) {
                    edit.putString("sound_" + this.dialog_id, str);
                    edit.putString("sound_path_" + this.dialog_id, uri.toString());
                } else {
                    edit.putString("sound_" + this.dialog_id, "NoSound");
                    edit.putString("sound_path_" + this.dialog_id, "NoSound");
                }
            } else if (i == 13) {
                if (str != null) {
                    edit.putString("ringtone_" + this.dialog_id, str);
                    edit.putString("ringtone_path_" + this.dialog_id, uri.toString());
                } else {
                    edit.putString("ringtone_" + this.dialog_id, "NoSound");
                    edit.putString("ringtone_path_" + this.dialog_id, "NoSound");
                }
            }
            edit.commit();
            if (this.adapter != null) {
                this.adapter.notifyItemChanged(i == 13 ? this.ringtoneRow : this.soundRow);
            }
        }
    }

    public boolean onFragmentCreate() {
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.customRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.customInfoRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.generalRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.soundRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.vibrateRow = i;
        if (((int) this.dialog_id) < 0) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.smartRow = i;
        } else {
            this.smartRow = -1;
        }
        if (VERSION.SDK_INT >= 21) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.priorityRow = i;
        } else {
            this.priorityRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.priorityInfoRow = i;
        int i2 = (int) this.dialog_id;
        boolean z;
        if (i2 < 0) {
            Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(-i2));
            z = (chat == null || !ChatObject.isChannel(chat) || chat.megagroup) ? false : true;
        } else {
            z = false;
        }
        if (i2 == 0 || r0) {
            this.popupRow = -1;
            this.popupEnabledRow = -1;
            this.popupDisabledRow = -1;
            this.popupInfoRow = -1;
        } else {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.popupRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.popupEnabledRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.popupDisabledRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.popupInfoRow = i;
        }
        if (i2 > 0) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.callsRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.callsVibrateRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.ringtoneRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.ringtoneInfoRow = i;
        } else {
            this.callsRow = -1;
            this.callsVibrateRow = -1;
            this.ringtoneRow = -1;
            this.ringtoneInfoRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.ledRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.colorRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.ledInfoRow = i;
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        this.customEnabled = sharedPreferences.getBoolean("custom_" + this.dialog_id, false);
        boolean contains = sharedPreferences.contains("notify2_" + this.dialog_id);
        int i3 = sharedPreferences.getInt("notify2_" + this.dialog_id, 0);
        if (i3 == 0) {
            if (contains) {
                this.notificationsEnabled = true;
            } else if (((int) this.dialog_id) < 0) {
                this.notificationsEnabled = sharedPreferences.getBoolean("EnableGroup", true);
            } else {
                this.notificationsEnabled = sharedPreferences.getBoolean("EnableAll", true);
            }
        } else if (i3 == 1) {
            this.notificationsEnabled = true;
        } else if (i3 == 2) {
            this.notificationsEnabled = false;
        } else {
            this.notificationsEnabled = false;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.notificationsSettingsUpdated);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.notificationsSettingsUpdated);
    }
}
