package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyChatInvite;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyPhoneCall;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyKeyStatusTimestamp;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyValueAllowAll;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyValueAllowContacts;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyValueAllowUsers;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyValueDisallowAll;
import org.telegram.tgnet.TLRPC$TL_inputPrivacyValueDisallowUsers;
import org.telegram.tgnet.TLRPC$TL_privacyValueAllowAll;
import org.telegram.tgnet.TLRPC$TL_privacyValueAllowUsers;
import org.telegram.tgnet.TLRPC$TL_privacyValueDisallowAll;
import org.telegram.tgnet.TLRPC$TL_privacyValueDisallowUsers;
import org.telegram.tgnet.TLRPC.InputUser;
import org.telegram.tgnet.TLRPC.PrivacyRule;
import org.telegram.tgnet.TLRPC.TL_account_privacyRules;
import org.telegram.tgnet.TLRPC.TL_account_setPrivacy;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.RadioCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.GroupCreateActivity.GroupCreateActivityDelegate;
import org.telegram.ui.PrivacyUsersActivity.PrivacyActivityDelegate;

public class PrivacyControlActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private int alwaysShareRow;
    private ArrayList<Integer> currentMinus;
    private ArrayList<Integer> currentPlus;
    private int currentType;
    private int detailRow;
    private View doneButton;
    private boolean enableAnimation;
    private int everybodyRow;
    private int lastCheckedType = -1;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int myContactsRow;
    private int neverShareRow;
    private int nobodyRow;
    private int rowCount;
    private int rulesType;
    private int sectionRow;
    private int shareDetailRow;
    private int shareSectionRow;

    /* renamed from: org.telegram.ui.PrivacyControlActivity$1 */
    class C50911 extends ActionBarMenuOnItemClick {
        C50911() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PrivacyControlActivity.this.finishFragment();
            } else if (i == 1 && PrivacyControlActivity.this.getParentActivity() != null) {
                if (PrivacyControlActivity.this.currentType != 0 && PrivacyControlActivity.this.rulesType == 0) {
                    final SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
                    if (!sharedPreferences.getBoolean("privacyAlertShowed", false)) {
                        Builder builder = new Builder(PrivacyControlActivity.this.getParentActivity());
                        if (PrivacyControlActivity.this.rulesType == 1) {
                            builder.setMessage(LocaleController.getString("WhoCanAddMeInfo", R.string.WhoCanAddMeInfo));
                        } else {
                            builder.setMessage(LocaleController.getString("CustomHelp", R.string.CustomHelp));
                        }
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                PrivacyControlActivity.this.applyCurrentPrivacySettings();
                                sharedPreferences.edit().putBoolean("privacyAlertShowed", true).commit();
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        PrivacyControlActivity.this.showDialog(builder.create());
                        return;
                    }
                }
                PrivacyControlActivity.this.applyCurrentPrivacySettings();
            }
        }
    }

    /* renamed from: org.telegram.ui.PrivacyControlActivity$2 */
    class C50942 implements OnItemClickListener {
        C50942() {
        }

        public void onItemClick(View view, final int i) {
            boolean z = true;
            if (i == PrivacyControlActivity.this.nobodyRow || i == PrivacyControlActivity.this.everybodyRow || i == PrivacyControlActivity.this.myContactsRow) {
                int access$000 = PrivacyControlActivity.this.currentType;
                if (i == PrivacyControlActivity.this.nobodyRow) {
                    access$000 = 1;
                } else if (i == PrivacyControlActivity.this.everybodyRow) {
                    access$000 = 0;
                } else if (i == PrivacyControlActivity.this.myContactsRow) {
                    access$000 = 2;
                }
                if (access$000 != PrivacyControlActivity.this.currentType) {
                    PrivacyControlActivity.this.enableAnimation = true;
                    PrivacyControlActivity.this.doneButton.setVisibility(0);
                    PrivacyControlActivity.this.lastCheckedType = PrivacyControlActivity.this.currentType;
                    PrivacyControlActivity.this.currentType = access$000;
                    PrivacyControlActivity.this.updateRows();
                }
            } else if (i == PrivacyControlActivity.this.neverShareRow || i == PrivacyControlActivity.this.alwaysShareRow) {
                ArrayList access$1200 = i == PrivacyControlActivity.this.neverShareRow ? PrivacyControlActivity.this.currentMinus : PrivacyControlActivity.this.currentPlus;
                if (access$1200.isEmpty()) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(i == PrivacyControlActivity.this.neverShareRow ? "isNeverShare" : "isAlwaysShare", true);
                    String str = "isGroup";
                    if (PrivacyControlActivity.this.rulesType == 0) {
                        z = false;
                    }
                    bundle.putBoolean(str, z);
                    BaseFragment groupCreateActivity = new GroupCreateActivity(bundle);
                    groupCreateActivity.setDelegate(new GroupCreateActivityDelegate() {
                        public void didSelectUsers(ArrayList<Integer> arrayList) {
                            int i;
                            if (i == PrivacyControlActivity.this.neverShareRow) {
                                PrivacyControlActivity.this.currentMinus = arrayList;
                                for (i = 0; i < PrivacyControlActivity.this.currentMinus.size(); i++) {
                                    PrivacyControlActivity.this.currentPlus.remove(PrivacyControlActivity.this.currentMinus.get(i));
                                }
                            } else {
                                PrivacyControlActivity.this.currentPlus = arrayList;
                                for (i = 0; i < PrivacyControlActivity.this.currentPlus.size(); i++) {
                                    PrivacyControlActivity.this.currentMinus.remove(PrivacyControlActivity.this.currentPlus.get(i));
                                }
                            }
                            PrivacyControlActivity.this.doneButton.setVisibility(0);
                            PrivacyControlActivity.this.lastCheckedType = -1;
                            PrivacyControlActivity.this.listAdapter.notifyDataSetChanged();
                        }
                    });
                    PrivacyControlActivity.this.presentFragment(groupCreateActivity);
                    return;
                }
                boolean z2 = PrivacyControlActivity.this.rulesType != 0;
                if (i != PrivacyControlActivity.this.alwaysShareRow) {
                    z = false;
                }
                BaseFragment privacyUsersActivity = new PrivacyUsersActivity(access$1200, z2, z);
                privacyUsersActivity.setDelegate(new PrivacyActivityDelegate() {
                    public void didUpdatedUserList(ArrayList<Integer> arrayList, boolean z) {
                        int i;
                        if (i == PrivacyControlActivity.this.neverShareRow) {
                            PrivacyControlActivity.this.currentMinus = arrayList;
                            if (z) {
                                for (i = 0; i < PrivacyControlActivity.this.currentMinus.size(); i++) {
                                    PrivacyControlActivity.this.currentPlus.remove(PrivacyControlActivity.this.currentMinus.get(i));
                                }
                            }
                        } else {
                            PrivacyControlActivity.this.currentPlus = arrayList;
                            if (z) {
                                for (i = 0; i < PrivacyControlActivity.this.currentPlus.size(); i++) {
                                    PrivacyControlActivity.this.currentMinus.remove(PrivacyControlActivity.this.currentPlus.get(i));
                                }
                            }
                        }
                        PrivacyControlActivity.this.doneButton.setVisibility(0);
                        PrivacyControlActivity.this.listAdapter.notifyDataSetChanged();
                    }
                });
                PrivacyControlActivity.this.presentFragment(privacyUsersActivity);
            }
        }
    }

    private static class LinkMovementMethodMy extends LinkMovementMethod {
        private LinkMovementMethodMy() {
        }

        public boolean onTouchEvent(TextView textView, Spannable spannable, MotionEvent motionEvent) {
            try {
                return super.onTouchEvent(textView, spannable, motionEvent);
            } catch (Throwable e) {
                FileLog.e(e);
                return false;
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return PrivacyControlActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == PrivacyControlActivity.this.alwaysShareRow || i == PrivacyControlActivity.this.neverShareRow) ? 0 : (i == PrivacyControlActivity.this.shareDetailRow || i == PrivacyControlActivity.this.detailRow) ? 1 : (i == PrivacyControlActivity.this.sectionRow || i == PrivacyControlActivity.this.shareSectionRow) ? 2 : (i == PrivacyControlActivity.this.everybodyRow || i == PrivacyControlActivity.this.myContactsRow || i == PrivacyControlActivity.this.nobodyRow) ? 3 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == PrivacyControlActivity.this.nobodyRow || adapterPosition == PrivacyControlActivity.this.everybodyRow || adapterPosition == PrivacyControlActivity.this.myContactsRow || adapterPosition == PrivacyControlActivity.this.neverShareRow || adapterPosition == PrivacyControlActivity.this.alwaysShareRow;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    String formatPluralString;
                    if (i == PrivacyControlActivity.this.alwaysShareRow) {
                        formatPluralString = PrivacyControlActivity.this.currentPlus.size() != 0 ? LocaleController.formatPluralString("Users", PrivacyControlActivity.this.currentPlus.size()) : LocaleController.getString("EmpryUsersPlaceholder", R.string.EmpryUsersPlaceholder);
                        String string;
                        if (PrivacyControlActivity.this.rulesType != 0) {
                            string = LocaleController.getString("AlwaysAllow", R.string.AlwaysAllow);
                            if (PrivacyControlActivity.this.neverShareRow == -1) {
                                z = false;
                            }
                            textSettingsCell.setTextAndValue(string, formatPluralString, z);
                            return;
                        }
                        string = LocaleController.getString("AlwaysShareWith", R.string.AlwaysShareWith);
                        if (PrivacyControlActivity.this.neverShareRow == -1) {
                            z = false;
                        }
                        textSettingsCell.setTextAndValue(string, formatPluralString, z);
                        return;
                    } else if (i == PrivacyControlActivity.this.neverShareRow) {
                        formatPluralString = PrivacyControlActivity.this.currentMinus.size() != 0 ? LocaleController.formatPluralString("Users", PrivacyControlActivity.this.currentMinus.size()) : LocaleController.getString("EmpryUsersPlaceholder", R.string.EmpryUsersPlaceholder);
                        if (PrivacyControlActivity.this.rulesType != 0) {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NeverAllow", R.string.NeverAllow), formatPluralString, false);
                            return;
                        } else {
                            textSettingsCell.setTextAndValue(LocaleController.getString("NeverShareWith", R.string.NeverShareWith), formatPluralString, false);
                            return;
                        }
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == PrivacyControlActivity.this.detailRow) {
                        if (PrivacyControlActivity.this.rulesType == 2) {
                            textInfoPrivacyCell.setText(LocaleController.getString("WhoCanCallMeInfo", R.string.WhoCanCallMeInfo));
                        } else if (PrivacyControlActivity.this.rulesType == 1) {
                            textInfoPrivacyCell.setText(LocaleController.getString("WhoCanAddMeInfo", R.string.WhoCanAddMeInfo));
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("CustomHelp", R.string.CustomHelp));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacyControlActivity.this.shareDetailRow) {
                        if (PrivacyControlActivity.this.rulesType == 2) {
                            textInfoPrivacyCell.setText(LocaleController.getString("CustomCallInfo", R.string.CustomCallInfo));
                        } else if (PrivacyControlActivity.this.rulesType == 1) {
                            textInfoPrivacyCell.setText(LocaleController.getString("CustomShareInfo", R.string.CustomShareInfo));
                        } else {
                            textInfoPrivacyCell.setText(LocaleController.getString("CustomShareSettingsHelp", R.string.CustomShareSettingsHelp));
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == PrivacyControlActivity.this.sectionRow) {
                        if (PrivacyControlActivity.this.rulesType == 2) {
                            headerCell.setText(LocaleController.getString("WhoCanCallMe", R.string.WhoCanCallMe));
                            return;
                        } else if (PrivacyControlActivity.this.rulesType == 1) {
                            headerCell.setText(LocaleController.getString("WhoCanAddMe", R.string.WhoCanAddMe));
                            return;
                        } else {
                            headerCell.setText(LocaleController.getString("LastSeenTitle", R.string.LastSeenTitle));
                            return;
                        }
                    } else if (i == PrivacyControlActivity.this.shareSectionRow) {
                        headerCell.setText(LocaleController.getString("AddExceptions", R.string.AddExceptions));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    boolean z2;
                    RadioCell radioCell = (RadioCell) viewHolder.itemView;
                    if (i == PrivacyControlActivity.this.everybodyRow) {
                        radioCell.setText(LocaleController.getString("LastSeenEverybody", R.string.LastSeenEverybody), PrivacyControlActivity.this.lastCheckedType == 0, true);
                        z2 = false;
                    } else if (i == PrivacyControlActivity.this.myContactsRow) {
                        radioCell.setText(LocaleController.getString("LastSeenContacts", R.string.LastSeenContacts), PrivacyControlActivity.this.lastCheckedType == 2, PrivacyControlActivity.this.nobodyRow != -1);
                        z2 = true;
                    } else if (i == PrivacyControlActivity.this.nobodyRow) {
                        radioCell.setText(LocaleController.getString("LastSeenNobody", R.string.LastSeenNobody), PrivacyControlActivity.this.lastCheckedType == 1, false);
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (PrivacyControlActivity.this.lastCheckedType == z2) {
                        radioCell.setChecked(false, PrivacyControlActivity.this.enableAnimation);
                        return;
                    } else if (PrivacyControlActivity.this.currentType == z2) {
                        radioCell.setChecked(true, PrivacyControlActivity.this.enableAnimation);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View textSettingsCell;
            switch (i) {
                case 0:
                    textSettingsCell = new TextSettingsCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    textSettingsCell = new TextInfoPrivacyCell(this.mContext);
                    break;
                case 2:
                    textSettingsCell = new HeaderCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    textSettingsCell = new RadioCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    public PrivacyControlActivity(int i) {
        this.rulesType = i;
    }

    private void applyCurrentPrivacySettings() {
        int i;
        User user;
        InputUser inputUser;
        TLObject tL_account_setPrivacy = new TL_account_setPrivacy();
        if (this.rulesType == 2) {
            tL_account_setPrivacy.key = new TLRPC$TL_inputPrivacyKeyPhoneCall();
        } else if (this.rulesType == 1) {
            tL_account_setPrivacy.key = new TLRPC$TL_inputPrivacyKeyChatInvite();
        } else {
            tL_account_setPrivacy.key = new TLRPC$TL_inputPrivacyKeyStatusTimestamp();
        }
        if (this.currentType != 0 && this.currentPlus.size() > 0) {
            TLRPC$TL_inputPrivacyValueAllowUsers tLRPC$TL_inputPrivacyValueAllowUsers = new TLRPC$TL_inputPrivacyValueAllowUsers();
            for (i = 0; i < this.currentPlus.size(); i++) {
                user = MessagesController.getInstance().getUser((Integer) this.currentPlus.get(i));
                if (user != null) {
                    inputUser = MessagesController.getInputUser(user);
                    if (inputUser != null) {
                        tLRPC$TL_inputPrivacyValueAllowUsers.users.add(inputUser);
                    }
                }
            }
            tL_account_setPrivacy.rules.add(tLRPC$TL_inputPrivacyValueAllowUsers);
        }
        if (this.currentType != 1 && this.currentMinus.size() > 0) {
            TLRPC$TL_inputPrivacyValueDisallowUsers tLRPC$TL_inputPrivacyValueDisallowUsers = new TLRPC$TL_inputPrivacyValueDisallowUsers();
            for (i = 0; i < this.currentMinus.size(); i++) {
                user = MessagesController.getInstance().getUser((Integer) this.currentMinus.get(i));
                if (user != null) {
                    inputUser = MessagesController.getInputUser(user);
                    if (inputUser != null) {
                        tLRPC$TL_inputPrivacyValueDisallowUsers.users.add(inputUser);
                    }
                }
            }
            tL_account_setPrivacy.rules.add(tLRPC$TL_inputPrivacyValueDisallowUsers);
        }
        if (this.currentType == 0) {
            tL_account_setPrivacy.rules.add(new TLRPC$TL_inputPrivacyValueAllowAll());
        } else if (this.currentType == 1) {
            tL_account_setPrivacy.rules.add(new TLRPC$TL_inputPrivacyValueDisallowAll());
        } else if (this.currentType == 2) {
            tL_account_setPrivacy.rules.add(new TLRPC$TL_inputPrivacyValueAllowContacts());
        }
        AlertDialog alertDialog = null;
        if (getParentActivity() != null) {
            alertDialog = new AlertDialog(getParentActivity(), 1);
            alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            alertDialog.show();
        }
        ConnectionsManager.getInstance().sendRequest(tL_account_setPrivacy, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        try {
                            if (alertDialog != null) {
                                alertDialog.dismiss();
                            }
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                        if (tLRPC$TL_error == null) {
                            PrivacyControlActivity.this.finishFragment();
                            TL_account_privacyRules tL_account_privacyRules = (TL_account_privacyRules) tLObject;
                            MessagesController.getInstance().putUsers(tL_account_privacyRules.users, false);
                            ContactsController.getInstance().setPrivacyRules(tL_account_privacyRules.rules, PrivacyControlActivity.this.rulesType);
                            return;
                        }
                        PrivacyControlActivity.this.showErrorAlert();
                    }
                });
            }
        }, 2);
    }

    private void checkPrivacy() {
        this.currentPlus = new ArrayList();
        this.currentMinus = new ArrayList();
        ArrayList privacyRules = ContactsController.getInstance().getPrivacyRules(this.rulesType);
        if (privacyRules == null || privacyRules.size() == 0) {
            this.currentType = 1;
            return;
        }
        int i = -1;
        for (int i2 = 0; i2 < privacyRules.size(); i2++) {
            PrivacyRule privacyRule = (PrivacyRule) privacyRules.get(i2);
            if (privacyRule instanceof TLRPC$TL_privacyValueAllowUsers) {
                this.currentPlus.addAll(privacyRule.users);
            } else if (privacyRule instanceof TLRPC$TL_privacyValueDisallowUsers) {
                this.currentMinus.addAll(privacyRule.users);
            } else {
                i = privacyRule instanceof TLRPC$TL_privacyValueAllowAll ? 0 : privacyRule instanceof TLRPC$TL_privacyValueDisallowAll ? 1 : 2;
            }
        }
        if (i == 0 || (i == -1 && this.currentMinus.size() > 0)) {
            this.currentType = 0;
        } else if (i == 2 || (i == -1 && this.currentMinus.size() > 0 && this.currentPlus.size() > 0)) {
            this.currentType = 2;
        } else if (i == 1 || (i == -1 && this.currentPlus.size() > 0)) {
            this.currentType = 1;
        }
        if (this.doneButton != null) {
            this.doneButton.setVisibility(8);
        }
        updateRows();
    }

    private void showErrorAlert() {
        if (getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setMessage(LocaleController.getString("PrivacyFloodControlError", R.string.PrivacyFloodControlError));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    private void updateRows() {
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.sectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.everybodyRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.myContactsRow = i;
        if (this.rulesType == 0 || this.rulesType == 2) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.nobodyRow = i;
        } else {
            this.nobodyRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.detailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.shareSectionRow = i;
        if (this.currentType == 1 || this.currentType == 2) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.alwaysShareRow = i;
        } else {
            this.alwaysShareRow = -1;
        }
        if (this.currentType == 0 || this.currentType == 2) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.neverShareRow = i;
        } else {
            this.neverShareRow = -1;
        }
        i = this.rowCount;
        this.rowCount = i + 1;
        this.shareDetailRow = i;
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        if (this.rulesType == 2) {
            this.actionBar.setTitle(LocaleController.getString("Calls", R.string.Calls));
        } else if (this.rulesType == 1) {
            this.actionBar.setTitle(LocaleController.getString("GroupsAndChannels", R.string.GroupsAndChannels));
        } else {
            this.actionBar.setTitle(LocaleController.getString("PrivacyLastSeen", R.string.PrivacyLastSeen));
        }
        this.actionBar.setActionBarMenuOnItemClick(new C50911());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.doneButton.setVisibility(8);
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C50942());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.privacyRulesUpdated) {
            checkPrivacy();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[17];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, HeaderCell.class, RadioCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[9] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[10] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[12] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r9[14] = new ThemeDescription(this.listView, 0, new Class[]{RadioCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[15] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r9[16] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        checkPrivacy();
        updateRows();
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.privacyRulesUpdated);
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.privacyRulesUpdated);
    }

    public void onResume() {
        super.onResume();
        this.lastCheckedType = -1;
        this.enableAnimation = false;
    }
}
