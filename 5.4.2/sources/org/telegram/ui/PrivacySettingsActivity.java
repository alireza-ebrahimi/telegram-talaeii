package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_payments_clearSavedInfo;
import org.telegram.tgnet.TLRPC$TL_privacyValueAllowAll;
import org.telegram.tgnet.TLRPC$TL_privacyValueAllowUsers;
import org.telegram.tgnet.TLRPC$TL_privacyValueDisallowAll;
import org.telegram.tgnet.TLRPC$TL_privacyValueDisallowUsers;
import org.telegram.tgnet.TLRPC.PrivacyRule;
import org.telegram.tgnet.TLRPC.TL_accountDaysTTL;
import org.telegram.tgnet.TLRPC.TL_account_setAccountTTL;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.BottomSheet;
import org.telegram.ui.ActionBar.BottomSheet.BottomSheetCell;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.CheckBoxCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.voip.VoIPHelper;

public class PrivacySettingsActivity extends BaseFragment implements NotificationCenterDelegate {
    private int blockedRow;
    private int callsDetailRow;
    private int callsP2PRow;
    private int callsRow;
    private int callsSectionRow;
    private boolean[] clear = new boolean[2];
    private int deleteAccountDetailRow;
    private int deleteAccountRow;
    private int deleteAccountSectionRow;
    private int groupsDetailRow;
    private int groupsRow;
    private int lastSeenRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int passcodeRow;
    private int passwordRow;
    private int paymentsClearRow;
    private int paymentsDetailRow;
    private int paymentsSectionRow;
    private int privacySectionRow;
    private int rowCount;
    private int secretDetailRow;
    private int secretSectionRow;
    private int secretWebpageRow;
    private int securitySectionRow;
    private int sessionsDetailRow;
    private int sessionsRow;

    /* renamed from: org.telegram.ui.PrivacySettingsActivity$1 */
    class C50971 extends ActionBarMenuOnItemClick {
        C50971() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PrivacySettingsActivity.this.finishFragment();
            }
        }
    }

    /* renamed from: org.telegram.ui.PrivacySettingsActivity$2 */
    class C51052 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.PrivacySettingsActivity$2$1 */
        class C51001 implements OnClickListener {
            C51001() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                int i2 = i == 0 ? 30 : i == 1 ? 90 : i == 2 ? 182 : i == 3 ? 365 : 0;
                final AlertDialog alertDialog = new AlertDialog(PrivacySettingsActivity.this.getParentActivity(), 1);
                alertDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.show();
                final TLObject tL_account_setAccountTTL = new TL_account_setAccountTTL();
                tL_account_setAccountTTL.ttl = new TL_accountDaysTTL();
                tL_account_setAccountTTL.ttl.days = i2;
                ConnectionsManager.getInstance().sendRequest(tL_account_setAccountTTL, new RequestDelegate() {
                    public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                try {
                                    alertDialog.dismiss();
                                } catch (Throwable e) {
                                    FileLog.e(e);
                                }
                                if (tLObject instanceof TL_boolTrue) {
                                    ContactsController.getInstance().setDeleteAccountTTL(tL_account_setAccountTTL.ttl.days);
                                    PrivacySettingsActivity.this.listAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                    }
                });
            }
        }

        /* renamed from: org.telegram.ui.PrivacySettingsActivity$2$2 */
        class C51012 implements OnClickListener {
            C51012() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                PrivacySettingsActivity.this.getParentActivity().getSharedPreferences("mainconfig", 0).edit().putInt("calls_p2p_new", i).apply();
                PrivacySettingsActivity.this.listAdapter.notifyDataSetChanged();
            }
        }

        /* renamed from: org.telegram.ui.PrivacySettingsActivity$2$3 */
        class C51023 implements View.OnClickListener {
            C51023() {
            }

            public void onClick(View view) {
                CheckBoxCell checkBoxCell = (CheckBoxCell) view;
                int intValue = ((Integer) checkBoxCell.getTag()).intValue();
                PrivacySettingsActivity.this.clear[intValue] = !PrivacySettingsActivity.this.clear[intValue];
                checkBoxCell.setChecked(PrivacySettingsActivity.this.clear[intValue], true);
            }
        }

        /* renamed from: org.telegram.ui.PrivacySettingsActivity$2$4 */
        class C51044 implements View.OnClickListener {

            /* renamed from: org.telegram.ui.PrivacySettingsActivity$2$4$1 */
            class C51031 implements RequestDelegate {
                C51031() {
                }

                public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                }
            }

            C51044() {
            }

            public void onClick(View view) {
                try {
                    if (PrivacySettingsActivity.this.visibleDialog != null) {
                        PrivacySettingsActivity.this.visibleDialog.dismiss();
                    }
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                TLObject tLRPC$TL_payments_clearSavedInfo = new TLRPC$TL_payments_clearSavedInfo();
                tLRPC$TL_payments_clearSavedInfo.credentials = PrivacySettingsActivity.this.clear[1];
                tLRPC$TL_payments_clearSavedInfo.info = PrivacySettingsActivity.this.clear[0];
                UserConfig.tmpPassword = null;
                UserConfig.saveConfig(false);
                ConnectionsManager.getInstance().sendRequest(tLRPC$TL_payments_clearSavedInfo, new C51031());
            }
        }

        C51052() {
        }

        public void onItemClick(View view, int i) {
            boolean z = true;
            if (!view.isEnabled()) {
                return;
            }
            if (i == PrivacySettingsActivity.this.blockedRow) {
                PrivacySettingsActivity.this.presentFragment(new BlockedUsersActivity());
            } else if (i == PrivacySettingsActivity.this.sessionsRow) {
                PrivacySettingsActivity.this.presentFragment(new SessionsActivity());
            } else if (i == PrivacySettingsActivity.this.deleteAccountRow) {
                if (PrivacySettingsActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(PrivacySettingsActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("DeleteAccountTitle", R.string.DeleteAccountTitle));
                    builder.setItems(new CharSequence[]{LocaleController.formatPluralString("Months", 1), LocaleController.formatPluralString("Months", 3), LocaleController.formatPluralString("Months", 6), LocaleController.formatPluralString("Years", 1)}, new C51001());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    PrivacySettingsActivity.this.showDialog(builder.create());
                }
            } else if (i == PrivacySettingsActivity.this.lastSeenRow) {
                PrivacySettingsActivity.this.presentFragment(new PrivacyControlActivity(0));
            } else if (i == PrivacySettingsActivity.this.callsRow) {
                PrivacySettingsActivity.this.presentFragment(new PrivacyControlActivity(2));
            } else if (i == PrivacySettingsActivity.this.groupsRow) {
                PrivacySettingsActivity.this.presentFragment(new PrivacyControlActivity(1));
            } else if (i == PrivacySettingsActivity.this.passwordRow) {
                PrivacySettingsActivity.this.presentFragment(new TwoStepVerificationActivity(0));
            } else if (i == PrivacySettingsActivity.this.passcodeRow) {
                if (UserConfig.passcodeHash.length() > 0) {
                    PrivacySettingsActivity.this.presentFragment(new PasscodeActivity(2));
                } else {
                    PrivacySettingsActivity.this.presentFragment(new PasscodeActivity(0));
                }
            } else if (i == PrivacySettingsActivity.this.secretWebpageRow) {
                if (MessagesController.getInstance().secretWebpagePreview == 1) {
                    MessagesController.getInstance().secretWebpagePreview = 0;
                } else {
                    MessagesController.getInstance().secretWebpagePreview = 1;
                }
                ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putInt("secretWebpage2", MessagesController.getInstance().secretWebpagePreview).commit();
                if (view instanceof TextCheckCell) {
                    TextCheckCell textCheckCell = (TextCheckCell) view;
                    if (MessagesController.getInstance().secretWebpagePreview != 1) {
                        z = false;
                    }
                    textCheckCell.setChecked(z);
                }
            } else if (i == PrivacySettingsActivity.this.callsP2PRow) {
                new Builder(PrivacySettingsActivity.this.getParentActivity()).setTitle(LocaleController.getString("PrivacyCallsP2PTitle", R.string.PrivacyCallsP2PTitle)).setItems(new String[]{LocaleController.getString("LastSeenEverybody", R.string.LastSeenEverybody), LocaleController.getString("LastSeenContacts", R.string.LastSeenContacts), LocaleController.getString("LastSeenNobody", R.string.LastSeenNobody)}, new C51012()).setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null).show();
            } else if (i == PrivacySettingsActivity.this.paymentsClearRow) {
                BottomSheet.Builder builder2 = new BottomSheet.Builder(PrivacySettingsActivity.this.getParentActivity());
                builder2.setApplyTopPadding(false);
                builder2.setApplyBottomPadding(false);
                View linearLayout = new LinearLayout(PrivacySettingsActivity.this.getParentActivity());
                linearLayout.setOrientation(1);
                int i2 = 0;
                while (i2 < 2) {
                    String string = i2 == 0 ? LocaleController.getString("PrivacyClearShipping", R.string.PrivacyClearShipping) : i2 == 1 ? LocaleController.getString("PrivacyClearPayment", R.string.PrivacyClearPayment) : null;
                    PrivacySettingsActivity.this.clear[i2] = true;
                    View checkBoxCell = new CheckBoxCell(PrivacySettingsActivity.this.getParentActivity(), true);
                    checkBoxCell.setTag(Integer.valueOf(i2));
                    checkBoxCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                    linearLayout.addView(checkBoxCell, LayoutHelper.createLinear(-1, 48));
                    checkBoxCell.setText(string, null, true, true);
                    checkBoxCell.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                    checkBoxCell.setOnClickListener(new C51023());
                    i2++;
                }
                View bottomSheetCell = new BottomSheetCell(PrivacySettingsActivity.this.getParentActivity(), 1);
                bottomSheetCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                bottomSheetCell.setTextAndIcon(LocaleController.getString("ClearButton", R.string.ClearButton).toUpperCase(), 0);
                bottomSheetCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
                bottomSheetCell.setOnClickListener(new C51044());
                linearLayout.addView(bottomSheetCell, LayoutHelper.createLinear(-1, 48));
                builder2.setCustomView(linearLayout);
                PrivacySettingsActivity.this.showDialog(builder2.create());
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return PrivacySettingsActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == PrivacySettingsActivity.this.lastSeenRow || i == PrivacySettingsActivity.this.blockedRow || i == PrivacySettingsActivity.this.deleteAccountRow || i == PrivacySettingsActivity.this.sessionsRow || i == PrivacySettingsActivity.this.passwordRow || i == PrivacySettingsActivity.this.passcodeRow || i == PrivacySettingsActivity.this.groupsRow || i == PrivacySettingsActivity.this.paymentsClearRow || i == PrivacySettingsActivity.this.callsP2PRow) ? 0 : (i == PrivacySettingsActivity.this.deleteAccountDetailRow || i == PrivacySettingsActivity.this.groupsDetailRow || i == PrivacySettingsActivity.this.sessionsDetailRow || i == PrivacySettingsActivity.this.secretDetailRow || i == PrivacySettingsActivity.this.paymentsDetailRow || i == PrivacySettingsActivity.this.callsDetailRow) ? 1 : (i == PrivacySettingsActivity.this.securitySectionRow || i == PrivacySettingsActivity.this.deleteAccountSectionRow || i == PrivacySettingsActivity.this.privacySectionRow || i == PrivacySettingsActivity.this.secretSectionRow || i == PrivacySettingsActivity.this.paymentsSectionRow || i == PrivacySettingsActivity.this.callsSectionRow) ? 2 : i == PrivacySettingsActivity.this.secretWebpageRow ? 3 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == PrivacySettingsActivity.this.passcodeRow || adapterPosition == PrivacySettingsActivity.this.passwordRow || adapterPosition == PrivacySettingsActivity.this.blockedRow || adapterPosition == PrivacySettingsActivity.this.sessionsRow || adapterPosition == PrivacySettingsActivity.this.secretWebpageRow || ((adapterPosition == PrivacySettingsActivity.this.groupsRow && !ContactsController.getInstance().getLoadingGroupInfo()) || ((adapterPosition == PrivacySettingsActivity.this.lastSeenRow && !ContactsController.getInstance().getLoadingLastSeenInfo()) || ((adapterPosition == PrivacySettingsActivity.this.callsRow && !ContactsController.getInstance().getLoadingCallsInfo()) || ((adapterPosition == PrivacySettingsActivity.this.deleteAccountRow && !ContactsController.getInstance().getLoadingDeleteInfo()) || adapterPosition == PrivacySettingsActivity.this.paymentsClearRow || adapterPosition == PrivacySettingsActivity.this.callsP2PRow))));
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            int i2 = R.drawable.greydivider;
            boolean z = false;
            String string;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == PrivacySettingsActivity.this.blockedRow) {
                        textSettingsCell.setText(LocaleController.getString("BlockedUsers", R.string.BlockedUsers), true);
                        return;
                    } else if (i == PrivacySettingsActivity.this.sessionsRow) {
                        textSettingsCell.setText(LocaleController.getString("SessionsTitle", R.string.SessionsTitle), false);
                        return;
                    } else if (i == PrivacySettingsActivity.this.passwordRow) {
                        textSettingsCell.setText(LocaleController.getString("TwoStepVerification", R.string.TwoStepVerification), true);
                        return;
                    } else if (i == PrivacySettingsActivity.this.passcodeRow) {
                        textSettingsCell.setText(LocaleController.getString("Passcode", R.string.Passcode), true);
                        return;
                    } else if (i == PrivacySettingsActivity.this.lastSeenRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("PrivacyLastSeen", R.string.PrivacyLastSeen), ContactsController.getInstance().getLoadingLastSeenInfo() ? LocaleController.getString("Loading", R.string.Loading) : PrivacySettingsActivity.this.formatRulesString(0), true);
                        return;
                    } else if (i == PrivacySettingsActivity.this.callsRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("Calls", R.string.Calls), ContactsController.getInstance().getLoadingCallsInfo() ? LocaleController.getString("Loading", R.string.Loading) : PrivacySettingsActivity.this.formatRulesString(2), true);
                        return;
                    } else if (i == PrivacySettingsActivity.this.groupsRow) {
                        textSettingsCell.setTextAndValue(LocaleController.getString("GroupsAndChannels", R.string.GroupsAndChannels), ContactsController.getInstance().getLoadingGroupInfo() ? LocaleController.getString("Loading", R.string.Loading) : PrivacySettingsActivity.this.formatRulesString(1), false);
                        return;
                    } else if (i == PrivacySettingsActivity.this.deleteAccountRow) {
                        if (ContactsController.getInstance().getLoadingDeleteInfo()) {
                            string = LocaleController.getString("Loading", R.string.Loading);
                        } else {
                            i2 = ContactsController.getInstance().getDeleteAccountTTL();
                            string = i2 <= 182 ? LocaleController.formatPluralString("Months", i2 / 30) : i2 == 365 ? LocaleController.formatPluralString("Years", i2 / 365) : LocaleController.formatPluralString("Days", i2);
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("DeleteAccountIfAwayFor", R.string.DeleteAccountIfAwayFor), string, false);
                        return;
                    } else if (i == PrivacySettingsActivity.this.paymentsClearRow) {
                        textSettingsCell.setText(LocaleController.getString("PrivacyPaymentsClear", R.string.PrivacyPaymentsClear), false);
                        return;
                    } else if (i == PrivacySettingsActivity.this.callsP2PRow) {
                        switch (PrivacySettingsActivity.this.getParentActivity().getSharedPreferences("mainconfig", 0).getInt("calls_p2p_new", MessagesController.getInstance().defaultP2pContacts ? 1 : 0)) {
                            case 1:
                                string = LocaleController.getString("LastSeenContacts", R.string.LastSeenContacts);
                                break;
                            case 2:
                                string = LocaleController.getString("LastSeenNobody", R.string.LastSeenNobody);
                                break;
                            default:
                                string = LocaleController.getString("LastSeenEverybody", R.string.LastSeenEverybody);
                                break;
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("PrivacyCallsP2PTitle", R.string.PrivacyCallsP2PTitle), string, false);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == PrivacySettingsActivity.this.deleteAccountDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("DeleteAccountHelp", R.string.DeleteAccountHelp));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacySettingsActivity.this.groupsDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("GroupsAndChannelsHelp", R.string.GroupsAndChannelsHelp));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacySettingsActivity.this.sessionsDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("SessionsInfo", R.string.SessionsInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacySettingsActivity.this.secretDetailRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        r2 = this.mContext;
                        if (PrivacySettingsActivity.this.callsSectionRow == -1) {
                            i2 = R.drawable.greydivider_bottom;
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(r2, i2, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacySettingsActivity.this.paymentsDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("PrivacyPaymentsClearInfo", R.string.PrivacyPaymentsClearInfo));
                        r2 = this.mContext;
                        if (PrivacySettingsActivity.this.secretSectionRow == -1 && PrivacySettingsActivity.this.callsSectionRow == -1) {
                            i2 = R.drawable.greydivider_bottom;
                        }
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(r2, i2, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PrivacySettingsActivity.this.callsDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("PrivacyCallsP2PHelp", R.string.PrivacyCallsP2PHelp));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    HeaderCell headerCell = (HeaderCell) viewHolder.itemView;
                    if (i == PrivacySettingsActivity.this.privacySectionRow) {
                        headerCell.setText(LocaleController.getString("PrivacyTitle", R.string.PrivacyTitle));
                        return;
                    } else if (i == PrivacySettingsActivity.this.securitySectionRow) {
                        headerCell.setText(LocaleController.getString("SecurityTitle", R.string.SecurityTitle));
                        return;
                    } else if (i == PrivacySettingsActivity.this.deleteAccountSectionRow) {
                        headerCell.setText(LocaleController.getString("DeleteAccountTitle", R.string.DeleteAccountTitle));
                        return;
                    } else if (i == PrivacySettingsActivity.this.secretSectionRow) {
                        headerCell.setText(LocaleController.getString("SecretChat", R.string.SecretChat));
                        return;
                    } else if (i == PrivacySettingsActivity.this.paymentsSectionRow) {
                        headerCell.setText(LocaleController.getString("PrivacyPayments", R.string.PrivacyPayments));
                        return;
                    } else if (i == PrivacySettingsActivity.this.callsSectionRow) {
                        headerCell.setText(LocaleController.getString("Calls", R.string.Calls));
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    if (i == PrivacySettingsActivity.this.secretWebpageRow) {
                        string = LocaleController.getString("SecretWebPage", R.string.SecretWebPage);
                        if (MessagesController.getInstance().secretWebpagePreview == 1) {
                            z = true;
                        }
                        textCheckCell.setTextAndCheck(string, z, true);
                        return;
                    }
                    return;
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
                    textSettingsCell = new TextCheckCell(this.mContext);
                    textSettingsCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    private String formatRulesString(int i) {
        ArrayList privacyRules = ContactsController.getInstance().getPrivacyRules(i);
        if (privacyRules.size() == 0) {
            return LocaleController.getString("LastSeenNobody", R.string.LastSeenNobody);
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = -1;
        for (int i5 = 0; i5 < privacyRules.size(); i5++) {
            PrivacyRule privacyRule = (PrivacyRule) privacyRules.get(i5);
            if (privacyRule instanceof TLRPC$TL_privacyValueAllowUsers) {
                i3 += privacyRule.users.size();
            } else if (privacyRule instanceof TLRPC$TL_privacyValueDisallowUsers) {
                i2 += privacyRule.users.size();
            } else {
                i4 = privacyRule instanceof TLRPC$TL_privacyValueAllowAll ? 0 : privacyRule instanceof TLRPC$TL_privacyValueDisallowAll ? 1 : 2;
            }
        }
        if (i4 == 0 || (i4 == -1 && i2 > 0)) {
            if (i2 == 0) {
                return LocaleController.getString("LastSeenEverybody", R.string.LastSeenEverybody);
            }
            return LocaleController.formatString("LastSeenEverybodyMinus", R.string.LastSeenEverybodyMinus, new Object[]{Integer.valueOf(i2)});
        } else if (i4 == 2 || (i4 == -1 && i2 > 0 && i3 > 0)) {
            if (i3 == 0 && i2 == 0) {
                return LocaleController.getString("LastSeenContacts", R.string.LastSeenContacts);
            }
            if (i3 != 0 && i2 != 0) {
                return LocaleController.formatString("LastSeenContactsMinusPlus", R.string.LastSeenContactsMinusPlus, new Object[]{Integer.valueOf(i2), Integer.valueOf(i3)});
            } else if (i2 != 0) {
                return LocaleController.formatString("LastSeenContactsMinus", R.string.LastSeenContactsMinus, new Object[]{Integer.valueOf(i2)});
            } else {
                return LocaleController.formatString("LastSeenContactsPlus", R.string.LastSeenContactsPlus, new Object[]{Integer.valueOf(i3)});
            }
        } else if (i4 != 1 && i3 <= 0) {
            return "unknown";
        } else {
            if (i3 == 0) {
                return LocaleController.getString("LastSeenNobody", R.string.LastSeenNobody);
            }
            return LocaleController.formatString("LastSeenNobodyPlus", R.string.LastSeenNobodyPlus, new Object[]{Integer.valueOf(i3)});
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("PrivacySettings", R.string.PrivacySettings));
        this.actionBar.setActionBarMenuOnItemClick(new C50971());
        this.listAdapter = new ListAdapter(context);
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.listView = new RecyclerListView(context);
        this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
        this.listView.setVerticalScrollBarEnabled(false);
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new C51052());
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.privacyRulesUpdated && this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescription[] themeDescriptionArr = new ThemeDescription[20];
        themeDescriptionArr[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class, HeaderCell.class, TextCheckCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        themeDescriptionArr[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        themeDescriptionArr[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        themeDescriptionArr[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        themeDescriptionArr[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        themeDescriptionArr[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        themeDescriptionArr[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        themeDescriptionArr[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        themeDescriptionArr[9] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[10] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        themeDescriptionArr[11] = new ThemeDescription(this.listView, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        themeDescriptionArr[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        themeDescriptionArr[13] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        themeDescriptionArr[14] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        themeDescriptionArr[15] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        themeDescriptionArr[16] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        themeDescriptionArr[17] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        themeDescriptionArr[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        themeDescriptionArr[19] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        return themeDescriptionArr;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        ContactsController.getInstance().loadPrivacySettings();
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.privacySectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.blockedRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.lastSeenRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.groupsDetailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.securitySectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.passcodeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.passwordRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sessionsRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.sessionsDetailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.deleteAccountSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.deleteAccountRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.deleteAccountDetailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.paymentsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.paymentsClearRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.paymentsDetailRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsSectionRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsP2PRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.callsDetailRow = i;
        if (MessagesController.getInstance().secretWebpagePreview != 1) {
            i = this.rowCount;
            this.rowCount = i + 1;
            this.secretSectionRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.secretWebpageRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.secretDetailRow = i;
        } else {
            this.secretSectionRow = -1;
            this.secretWebpageRow = -1;
            this.secretDetailRow = -1;
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.privacyRulesUpdated);
        VoIPHelper.upgradeP2pSetting();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.privacyRulesUpdated);
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
    }
}
