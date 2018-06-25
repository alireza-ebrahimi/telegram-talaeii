package org.telegram.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$account_Password;
import org.telegram.tgnet.TLRPC.TL_account_getPassword;
import org.telegram.tgnet.TLRPC.TL_account_getPasswordSettings;
import org.telegram.tgnet.TLRPC.TL_account_noPassword;
import org.telegram.tgnet.TLRPC.TL_account_password;
import org.telegram.tgnet.TLRPC.TL_account_passwordInputSettings;
import org.telegram.tgnet.TLRPC.TL_account_updatePasswordSettings;
import org.telegram.tgnet.TLRPC.TL_auth_passwordRecovery;
import org.telegram.tgnet.TLRPC.TL_auth_recoverPassword;
import org.telegram.tgnet.TLRPC.TL_auth_requestPasswordRecovery;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.EmptyTextProgressView;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class TwoStepVerificationActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private int abortPasswordRow;
    private TextView bottomButton;
    private TextView bottomTextView;
    private int changePasswordRow;
    private int changeRecoveryEmailRow;
    private TLRPC$account_Password currentPassword;
    private byte[] currentPasswordHash = new byte[0];
    private boolean destroyed;
    private ActionBarMenuItem doneItem;
    private String email;
    private boolean emailOnly;
    private EmptyTextProgressView emptyView;
    private String firstPassword;
    private String hint;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private boolean loading;
    private EditTextBoldCursor passwordEditText;
    private int passwordEmailVerifyDetailRow;
    private int passwordEnabledDetailRow;
    private boolean passwordEntered = true;
    private int passwordSetState;
    private int passwordSetupDetailRow;
    private AlertDialog progressDialog;
    private int rowCount;
    private ScrollView scrollView;
    private int setPasswordDetailRow;
    private int setPasswordRow;
    private int setRecoveryEmailRow;
    private int shadowRow;
    private Runnable shortPollRunnable;
    private TextView titleTextView;
    private int turnPasswordOffRow;
    private int type;
    private boolean waitingForEmail;

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$1 */
    class C52471 extends ActionBarMenuOnItemClick {
        C52471() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                TwoStepVerificationActivity.this.finishFragment();
            } else if (i == 1) {
                TwoStepVerificationActivity.this.processDone();
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$2 */
    class C52482 implements OnEditorActionListener {
        C52482() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 5 && i != 6) {
                return false;
            }
            TwoStepVerificationActivity.this.processDone();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$3 */
    class C52493 implements Callback {
        C52493() {
        }

        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return false;
        }

        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode actionMode) {
        }

        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4 */
    class C52544 implements OnClickListener {

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4$1 */
        class C52521 implements RequestDelegate {
            C52521() {
            }

            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        TwoStepVerificationActivity.this.needHideProgress();
                        if (tLRPC$TL_error == null) {
                            final TL_auth_passwordRecovery tL_auth_passwordRecovery = (TL_auth_passwordRecovery) tLObject;
                            Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.formatString("RestoreEmailSent", R.string.RestoreEmailSent, new Object[]{tL_auth_passwordRecovery.email_pattern}));
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    BaseFragment twoStepVerificationActivity = new TwoStepVerificationActivity(1);
                                    twoStepVerificationActivity.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                                    twoStepVerificationActivity.currentPassword.email_unconfirmed_pattern = tL_auth_passwordRecovery.email_pattern;
                                    twoStepVerificationActivity.passwordSetState = 4;
                                    TwoStepVerificationActivity.this.presentFragment(twoStepVerificationActivity);
                                }
                            });
                            Dialog showDialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                            if (showDialog != null) {
                                showDialog.setCanceledOnTouchOutside(false);
                                showDialog.setCancelable(false);
                            }
                        } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                            int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                            String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                            TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                        } else {
                            TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                        }
                    }
                });
            }
        }

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4$2 */
        class C52532 implements DialogInterface.OnClickListener {
            C52532() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                TwoStepVerificationActivity.this.email = TtmlNode.ANONYMOUS_REGION_ID;
                TwoStepVerificationActivity.this.setNewPassword(false);
            }
        }

        C52544() {
        }

        public void onClick(View view) {
            if (TwoStepVerificationActivity.this.type == 0) {
                if (TwoStepVerificationActivity.this.currentPassword.has_recovery) {
                    TwoStepVerificationActivity.this.needShowProgress();
                    ConnectionsManager.getInstance().sendRequest(new TL_auth_requestPasswordRecovery(), new C52521(), 10);
                    return;
                }
                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle), LocaleController.getString("RestorePasswordNoEmailText", R.string.RestorePasswordNoEmailText));
            } else if (TwoStepVerificationActivity.this.passwordSetState == 4) {
                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle), LocaleController.getString("RestoreEmailTroubleText", R.string.RestoreEmailTroubleText));
            } else {
                Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("YourEmailSkipWarningText", R.string.YourEmailSkipWarningText));
                builder.setTitle(LocaleController.getString("YourEmailSkipWarning", R.string.YourEmailSkipWarning));
                builder.setPositiveButton(LocaleController.getString("YourEmailSkip", R.string.YourEmailSkip), new C52532());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                TwoStepVerificationActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$5 */
    class C52565 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$5$1 */
        class C52551 implements DialogInterface.OnClickListener {
            C52551() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                TwoStepVerificationActivity.this.setNewPassword(true);
            }
        }

        C52565() {
        }

        public void onItemClick(View view, int i) {
            BaseFragment twoStepVerificationActivity;
            if (i == TwoStepVerificationActivity.this.setPasswordRow || i == TwoStepVerificationActivity.this.changePasswordRow) {
                twoStepVerificationActivity = new TwoStepVerificationActivity(1);
                twoStepVerificationActivity.currentPasswordHash = TwoStepVerificationActivity.this.currentPasswordHash;
                twoStepVerificationActivity.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                TwoStepVerificationActivity.this.presentFragment(twoStepVerificationActivity);
            } else if (i == TwoStepVerificationActivity.this.setRecoveryEmailRow || i == TwoStepVerificationActivity.this.changeRecoveryEmailRow) {
                twoStepVerificationActivity = new TwoStepVerificationActivity(1);
                twoStepVerificationActivity.currentPasswordHash = TwoStepVerificationActivity.this.currentPasswordHash;
                twoStepVerificationActivity.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                twoStepVerificationActivity.emailOnly = true;
                twoStepVerificationActivity.passwordSetState = 3;
                TwoStepVerificationActivity.this.presentFragment(twoStepVerificationActivity);
            } else if (i == TwoStepVerificationActivity.this.turnPasswordOffRow || i == TwoStepVerificationActivity.this.abortPasswordRow) {
                Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("TurnPasswordOffQuestion", R.string.TurnPasswordOffQuestion));
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52551());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                TwoStepVerificationActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$6 */
    class C52576 implements Runnable {
        C52576() {
        }

        public void run() {
            if (TwoStepVerificationActivity.this.passwordEditText != null) {
                TwoStepVerificationActivity.this.passwordEditText.requestFocus();
                AndroidUtilities.showKeyboard(TwoStepVerificationActivity.this.passwordEditText);
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$8 */
    class C52618 implements Runnable {
        C52618() {
        }

        public void run() {
            if (TwoStepVerificationActivity.this.passwordEditText != null) {
                TwoStepVerificationActivity.this.passwordEditText.requestFocus();
                AndroidUtilities.showKeyboard(TwoStepVerificationActivity.this.passwordEditText);
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return (TwoStepVerificationActivity.this.loading || TwoStepVerificationActivity.this.currentPassword == null) ? 0 : TwoStepVerificationActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == TwoStepVerificationActivity.this.setPasswordDetailRow || i == TwoStepVerificationActivity.this.shadowRow || i == TwoStepVerificationActivity.this.passwordSetupDetailRow || i == TwoStepVerificationActivity.this.passwordEnabledDetailRow || i == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow) ? 1 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return (adapterPosition == TwoStepVerificationActivity.this.setPasswordDetailRow || adapterPosition == TwoStepVerificationActivity.this.shadowRow || adapterPosition == TwoStepVerificationActivity.this.passwordSetupDetailRow || adapterPosition == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow || adapterPosition == TwoStepVerificationActivity.this.passwordEnabledDetailRow) ? false : true;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = true;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    textSettingsCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    if (i == TwoStepVerificationActivity.this.changePasswordRow) {
                        textSettingsCell.setText(LocaleController.getString("ChangePassword", R.string.ChangePassword), true);
                        return;
                    } else if (i == TwoStepVerificationActivity.this.setPasswordRow) {
                        textSettingsCell.setText(LocaleController.getString("SetAdditionalPassword", R.string.SetAdditionalPassword), true);
                        return;
                    } else if (i == TwoStepVerificationActivity.this.turnPasswordOffRow) {
                        textSettingsCell.setText(LocaleController.getString("TurnPasswordOff", R.string.TurnPasswordOff), true);
                        return;
                    } else if (i == TwoStepVerificationActivity.this.changeRecoveryEmailRow) {
                        String string = LocaleController.getString("ChangeRecoveryEmail", R.string.ChangeRecoveryEmail);
                        if (TwoStepVerificationActivity.this.abortPasswordRow == -1) {
                            z = false;
                        }
                        textSettingsCell.setText(string, z);
                        return;
                    } else if (i == TwoStepVerificationActivity.this.setRecoveryEmailRow) {
                        textSettingsCell.setText(LocaleController.getString("SetRecoveryEmail", R.string.SetRecoveryEmail), false);
                        return;
                    } else if (i == TwoStepVerificationActivity.this.abortPasswordRow) {
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteRedText3);
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText3));
                        textSettingsCell.setText(LocaleController.getString("AbortPassword", R.string.AbortPassword), false);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == TwoStepVerificationActivity.this.setPasswordDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("SetAdditionalPasswordInfo", R.string.SetAdditionalPasswordInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == TwoStepVerificationActivity.this.shadowRow) {
                        textInfoPrivacyCell.setText(TtmlNode.ANONYMOUS_REGION_ID);
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == TwoStepVerificationActivity.this.passwordSetupDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.formatString("EmailPasswordConfirmText", R.string.EmailPasswordConfirmText, new Object[]{TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern}));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_top, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == TwoStepVerificationActivity.this.passwordEnabledDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("EnabledPasswordText", R.string.EnabledPasswordText));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.formatString("PendingEmailText", R.string.PendingEmailText, new Object[]{TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern}));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
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
                default:
                    textSettingsCell = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            return new Holder(textSettingsCell);
        }
    }

    public TwoStepVerificationActivity(int i) {
        this.type = i;
        if (i == 0) {
            loadPasswordInfo(false);
        }
    }

    private boolean isValidEmail(String str) {
        if (str == null || str.length() < 3) {
            return false;
        }
        int lastIndexOf = str.lastIndexOf(46);
        int lastIndexOf2 = str.lastIndexOf(64);
        return lastIndexOf >= 0 && lastIndexOf2 >= 0 && lastIndexOf >= lastIndexOf2;
    }

    private void loadPasswordInfo(final boolean z) {
        if (!z) {
            this.loading = true;
            if (this.listAdapter != null) {
                this.listAdapter.notifyDataSetChanged();
            }
        }
        ConnectionsManager.getInstance().sendRequest(new TL_account_getPassword(), new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$7$1$1 */
                    class C52581 implements Runnable {
                        C52581() {
                        }

                        public void run() {
                            if (TwoStepVerificationActivity.this.shortPollRunnable != null) {
                                TwoStepVerificationActivity.this.loadPasswordInfo(true);
                                TwoStepVerificationActivity.this.shortPollRunnable = null;
                            }
                        }
                    }

                    public void run() {
                        boolean z = true;
                        TwoStepVerificationActivity.this.loading = false;
                        if (tLRPC$TL_error == null) {
                            if (!z) {
                                TwoStepVerificationActivity twoStepVerificationActivity = TwoStepVerificationActivity.this;
                                boolean z2 = TwoStepVerificationActivity.this.currentPassword != null || (tLObject instanceof TL_account_noPassword);
                                twoStepVerificationActivity.passwordEntered = z2;
                            }
                            TwoStepVerificationActivity.this.currentPassword = (TLRPC$account_Password) tLObject;
                            TwoStepVerificationActivity twoStepVerificationActivity2 = TwoStepVerificationActivity.this;
                            if (TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern.length() <= 0) {
                                z = false;
                            }
                            twoStepVerificationActivity2.waitingForEmail = z;
                            Object obj = new byte[(TwoStepVerificationActivity.this.currentPassword.new_salt.length + 8)];
                            Utilities.random.nextBytes(obj);
                            System.arraycopy(TwoStepVerificationActivity.this.currentPassword.new_salt, 0, obj, 0, TwoStepVerificationActivity.this.currentPassword.new_salt.length);
                            TwoStepVerificationActivity.this.currentPassword.new_salt = obj;
                        }
                        if (TwoStepVerificationActivity.this.type == 0 && !TwoStepVerificationActivity.this.destroyed && TwoStepVerificationActivity.this.shortPollRunnable == null) {
                            TwoStepVerificationActivity.this.shortPollRunnable = new C52581();
                            AndroidUtilities.runOnUIThread(TwoStepVerificationActivity.this.shortPollRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                        }
                        TwoStepVerificationActivity.this.updateRows();
                    }
                });
            }
        }, 10);
    }

    private void needHideProgress() {
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    private void needShowProgress() {
        if (getParentActivity() != null && !getParentActivity().isFinishing() && this.progressDialog == null) {
            this.progressDialog = new AlertDialog(getParentActivity(), 1);
            this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
            this.progressDialog.setCanceledOnTouchOutside(false);
            this.progressDialog.setCancelable(false);
            this.progressDialog.show();
        }
    }

    private void onPasscodeError(boolean z) {
        if (getParentActivity() != null) {
            Vibrator vibrator = (Vibrator) getParentActivity().getSystemService("vibrator");
            if (vibrator != null) {
                vibrator.vibrate(200);
            }
            if (z) {
                this.passwordEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
            }
            AndroidUtilities.shakeView(this.titleTextView, 2.0f, 0);
        }
    }

    private void processDone() {
        if (this.type == 0) {
            if (!this.passwordEntered) {
                String obj = this.passwordEditText.getText().toString();
                if (obj.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                Object obj2 = null;
                try {
                    obj2 = obj.getBytes(C3446C.UTF8_NAME);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                needShowProgress();
                Object obj3 = new byte[((this.currentPassword.current_salt.length * 2) + obj2.length)];
                System.arraycopy(this.currentPassword.current_salt, 0, obj3, 0, this.currentPassword.current_salt.length);
                System.arraycopy(obj2, 0, obj3, this.currentPassword.current_salt.length, obj2.length);
                System.arraycopy(this.currentPassword.current_salt, 0, obj3, obj3.length - this.currentPassword.current_salt.length, this.currentPassword.current_salt.length);
                final TLObject tL_account_getPasswordSettings = new TL_account_getPasswordSettings();
                tL_account_getPasswordSettings.current_password_hash = Utilities.computeSHA256(obj3, 0, obj3.length);
                ConnectionsManager.getInstance().sendRequest(tL_account_getPasswordSettings, new RequestDelegate() {
                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                TwoStepVerificationActivity.this.needHideProgress();
                                if (tLRPC$TL_error == null) {
                                    TwoStepVerificationActivity.this.currentPasswordHash = tL_account_getPasswordSettings.current_password_hash;
                                    TwoStepVerificationActivity.this.passwordEntered = true;
                                    AndroidUtilities.hideKeyboard(TwoStepVerificationActivity.this.passwordEditText);
                                    TwoStepVerificationActivity.this.updateRows();
                                } else if (tLRPC$TL_error.text.equals("PASSWORD_HASH_INVALID")) {
                                    TwoStepVerificationActivity.this.onPasscodeError(true);
                                } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                    int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                                    String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                                } else {
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                }
                            }
                        });
                    }
                }, 10);
            }
        } else if (this.type != 1) {
        } else {
            if (this.passwordSetState == 0) {
                if (this.passwordEditText.getText().length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                this.titleTextView.setText(LocaleController.getString("ReEnterYourPasscode", R.string.ReEnterYourPasscode));
                this.firstPassword = this.passwordEditText.getText().toString();
                setPasswordSetState(1);
            } else if (this.passwordSetState == 1) {
                if (this.firstPassword.equals(this.passwordEditText.getText().toString())) {
                    setPasswordSetState(2);
                    return;
                }
                try {
                    Toast.makeText(getParentActivity(), LocaleController.getString("PasswordDoNotMatch", R.string.PasswordDoNotMatch), 0).show();
                } catch (Throwable e2) {
                    FileLog.e(e2);
                }
                onPasscodeError(true);
            } else if (this.passwordSetState == 2) {
                this.hint = this.passwordEditText.getText().toString();
                if (this.hint.toLowerCase().equals(this.firstPassword.toLowerCase())) {
                    try {
                        Toast.makeText(getParentActivity(), LocaleController.getString("PasswordAsHintError", R.string.PasswordAsHintError), 0).show();
                    } catch (Throwable e22) {
                        FileLog.e(e22);
                    }
                    onPasscodeError(false);
                } else if (this.currentPassword.has_recovery) {
                    this.email = TtmlNode.ANONYMOUS_REGION_ID;
                    setNewPassword(false);
                } else {
                    setPasswordSetState(3);
                }
            } else if (this.passwordSetState == 3) {
                this.email = this.passwordEditText.getText().toString();
                if (isValidEmail(this.email)) {
                    setNewPassword(false);
                } else {
                    onPasscodeError(false);
                }
            } else if (this.passwordSetState == 4) {
                String obj4 = this.passwordEditText.getText().toString();
                if (obj4.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                TLObject tL_auth_recoverPassword = new TL_auth_recoverPassword();
                tL_auth_recoverPassword.code = obj4;
                ConnectionsManager.getInstance().sendRequest(tL_auth_recoverPassword, new RequestDelegate() {
                    public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.TwoStepVerificationActivity$11$1$1 */
                            class C52451 implements DialogInterface.OnClickListener {
                                C52451() {
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[0]);
                                    TwoStepVerificationActivity.this.finishFragment();
                                }
                            }

                            public void run() {
                                if (tLRPC$TL_error == null) {
                                    Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52451());
                                    builder.setMessage(LocaleController.getString("PasswordReset", R.string.PasswordReset));
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    Dialog showDialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                    if (showDialog != null) {
                                        showDialog.setCanceledOnTouchOutside(false);
                                        showDialog.setCancelable(false);
                                    }
                                } else if (tLRPC$TL_error.text.startsWith("CODE_INVALID")) {
                                    TwoStepVerificationActivity.this.onPasscodeError(true);
                                } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                    int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                                    String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                                } else {
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                                }
                            }
                        });
                    }
                }, 10);
            }
        }
    }

    private void setNewPassword(final boolean z) {
        final TLObject tL_account_updatePasswordSettings = new TL_account_updatePasswordSettings();
        tL_account_updatePasswordSettings.current_password_hash = this.currentPasswordHash;
        tL_account_updatePasswordSettings.new_settings = new TL_account_passwordInputSettings();
        if (!z) {
            TL_account_passwordInputSettings tL_account_passwordInputSettings;
            if (this.firstPassword != null && this.firstPassword.length() > 0) {
                Object obj = null;
                try {
                    obj = this.firstPassword.getBytes(C3446C.UTF8_NAME);
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                Object obj2 = this.currentPassword.new_salt;
                Object obj3 = new byte[((obj2.length * 2) + obj.length)];
                System.arraycopy(obj2, 0, obj3, 0, obj2.length);
                System.arraycopy(obj, 0, obj3, obj2.length, obj.length);
                System.arraycopy(obj2, 0, obj3, obj3.length - obj2.length, obj2.length);
                tL_account_passwordInputSettings = tL_account_updatePasswordSettings.new_settings;
                tL_account_passwordInputSettings.flags |= 1;
                tL_account_updatePasswordSettings.new_settings.hint = this.hint;
                tL_account_updatePasswordSettings.new_settings.new_password_hash = Utilities.computeSHA256(obj3, 0, obj3.length);
                tL_account_updatePasswordSettings.new_settings.new_salt = obj2;
            }
            if (this.email.length() > 0) {
                tL_account_passwordInputSettings = tL_account_updatePasswordSettings.new_settings;
                tL_account_passwordInputSettings.flags |= 2;
                tL_account_updatePasswordSettings.new_settings.email = this.email.trim();
            }
        } else if (this.waitingForEmail && (this.currentPassword instanceof TL_account_noPassword)) {
            tL_account_updatePasswordSettings.new_settings.flags = 2;
            tL_account_updatePasswordSettings.new_settings.email = TtmlNode.ANONYMOUS_REGION_ID;
            tL_account_updatePasswordSettings.current_password_hash = new byte[0];
        } else {
            tL_account_updatePasswordSettings.new_settings.flags = 3;
            tL_account_updatePasswordSettings.new_settings.hint = TtmlNode.ANONYMOUS_REGION_ID;
            tL_account_updatePasswordSettings.new_settings.new_password_hash = new byte[0];
            tL_account_updatePasswordSettings.new_settings.new_salt = new byte[0];
            tL_account_updatePasswordSettings.new_settings.email = TtmlNode.ANONYMOUS_REGION_ID;
        }
        needShowProgress();
        ConnectionsManager.getInstance().sendRequest(tL_account_updatePasswordSettings, new RequestDelegate() {
            public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$9$1$1 */
                    class C52621 implements DialogInterface.OnClickListener {
                        C52621() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[]{tL_account_updatePasswordSettings.new_settings.new_password_hash});
                            TwoStepVerificationActivity.this.finishFragment();
                        }
                    }

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$9$1$2 */
                    class C52632 implements DialogInterface.OnClickListener {
                        C52632() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[]{tL_account_updatePasswordSettings.new_settings.new_password_hash});
                            TwoStepVerificationActivity.this.finishFragment();
                        }
                    }

                    public void run() {
                        TwoStepVerificationActivity.this.needHideProgress();
                        Builder builder;
                        Dialog showDialog;
                        if (tLRPC$TL_error == null && (tLObject instanceof TL_boolTrue)) {
                            if (z) {
                                TwoStepVerificationActivity.this.currentPassword = null;
                                TwoStepVerificationActivity.this.currentPasswordHash = new byte[0];
                                TwoStepVerificationActivity.this.loadPasswordInfo(false);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didRemovedTwoStepPassword, new Object[0]);
                                TwoStepVerificationActivity.this.updateRows();
                            } else if (TwoStepVerificationActivity.this.getParentActivity() != null) {
                                builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52621());
                                builder.setMessage(LocaleController.getString("YourPasswordSuccessText", R.string.YourPasswordSuccessText));
                                builder.setTitle(LocaleController.getString("YourPasswordSuccess", R.string.YourPasswordSuccess));
                                showDialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                if (showDialog != null) {
                                    showDialog.setCanceledOnTouchOutside(false);
                                    showDialog.setCancelable(false);
                                }
                            }
                        } else if (tLRPC$TL_error == null) {
                        } else {
                            if (tLRPC$TL_error.text.equals("EMAIL_UNCONFIRMED")) {
                                builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C52632());
                                builder.setMessage(LocaleController.getString("YourEmailAlmostThereText", R.string.YourEmailAlmostThereText));
                                builder.setTitle(LocaleController.getString("YourEmailAlmostThere", R.string.YourEmailAlmostThere));
                                showDialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                if (showDialog != null) {
                                    showDialog.setCanceledOnTouchOutside(false);
                                    showDialog.setCancelable(false);
                                }
                            } else if (tLRPC$TL_error.text.equals("EMAIL_INVALID")) {
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PasswordEmailInvalid", R.string.PasswordEmailInvalid));
                            } else if (tLRPC$TL_error.text.startsWith("FLOOD_WAIT")) {
                                int intValue = Utilities.parseInt(tLRPC$TL_error.text).intValue();
                                String formatPluralString = intValue < 60 ? LocaleController.formatPluralString("Seconds", intValue) : LocaleController.formatPluralString("Minutes", intValue / 60);
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{formatPluralString}));
                            } else {
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), tLRPC$TL_error.text);
                            }
                        }
                    }
                });
            }
        }, 10);
    }

    private void setPasswordSetState(int i) {
        int i2 = 4;
        if (this.passwordEditText != null) {
            this.passwordSetState = i;
            if (this.passwordSetState == 0) {
                this.actionBar.setTitle(LocaleController.getString("YourPassword", R.string.YourPassword));
                if (this.currentPassword instanceof TL_account_noPassword) {
                    this.titleTextView.setText(LocaleController.getString("PleaseEnterFirstPassword", R.string.PleaseEnterFirstPassword));
                } else {
                    this.titleTextView.setText(LocaleController.getString("PleaseEnterPassword", R.string.PleaseEnterPassword));
                }
                this.passwordEditText.setImeOptions(5);
                this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.bottomTextView.setVisibility(4);
                this.bottomButton.setVisibility(4);
            } else if (this.passwordSetState == 1) {
                this.actionBar.setTitle(LocaleController.getString("YourPassword", R.string.YourPassword));
                this.titleTextView.setText(LocaleController.getString("PleaseReEnterPassword", R.string.PleaseReEnterPassword));
                this.passwordEditText.setImeOptions(5);
                this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.bottomTextView.setVisibility(4);
                this.bottomButton.setVisibility(4);
            } else if (this.passwordSetState == 2) {
                this.actionBar.setTitle(LocaleController.getString("PasswordHint", R.string.PasswordHint));
                this.titleTextView.setText(LocaleController.getString("PasswordHintText", R.string.PasswordHintText));
                this.passwordEditText.setImeOptions(5);
                this.passwordEditText.setTransformationMethod(null);
                this.bottomTextView.setVisibility(4);
                this.bottomButton.setVisibility(4);
            } else if (this.passwordSetState == 3) {
                this.actionBar.setTitle(LocaleController.getString("RecoveryEmail", R.string.RecoveryEmail));
                this.titleTextView.setText(LocaleController.getString("YourEmail", R.string.YourEmail));
                this.passwordEditText.setImeOptions(6);
                this.passwordEditText.setTransformationMethod(null);
                this.passwordEditText.setInputType(33);
                this.bottomTextView.setVisibility(0);
                TextView textView = this.bottomButton;
                if (!this.emailOnly) {
                    i2 = 0;
                }
                textView.setVisibility(i2);
            } else if (this.passwordSetState == 4) {
                this.actionBar.setTitle(LocaleController.getString("PasswordRecovery", R.string.PasswordRecovery));
                this.titleTextView.setText(LocaleController.getString("PasswordCode", R.string.PasswordCode));
                this.bottomTextView.setText(LocaleController.getString("RestoreEmailSentInfo", R.string.RestoreEmailSentInfo));
                this.bottomButton.setText(LocaleController.formatString("RestoreEmailTrouble", R.string.RestoreEmailTrouble, new Object[]{this.currentPassword.email_unconfirmed_pattern}));
                this.passwordEditText.setImeOptions(6);
                this.passwordEditText.setTransformationMethod(null);
                this.passwordEditText.setInputType(3);
                this.bottomTextView.setVisibility(0);
                this.bottomButton.setVisibility(0);
            }
            this.passwordEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
        }
    }

    private void showAlertWithText(String str, String str2) {
        Builder builder = new Builder(getParentActivity());
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        builder.setTitle(str);
        builder.setMessage(str2);
        showDialog(builder.create());
    }

    private void updateRows() {
        this.rowCount = 0;
        this.setPasswordRow = -1;
        this.setPasswordDetailRow = -1;
        this.changePasswordRow = -1;
        this.turnPasswordOffRow = -1;
        this.setRecoveryEmailRow = -1;
        this.changeRecoveryEmailRow = -1;
        this.abortPasswordRow = -1;
        this.passwordSetupDetailRow = -1;
        this.passwordEnabledDetailRow = -1;
        this.passwordEmailVerifyDetailRow = -1;
        this.shadowRow = -1;
        if (!(this.loading || this.currentPassword == null)) {
            int i;
            if (this.currentPassword instanceof TL_account_noPassword) {
                if (this.waitingForEmail) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.passwordSetupDetailRow = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.abortPasswordRow = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.shadowRow = i;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.setPasswordRow = i;
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.setPasswordDetailRow = i;
                }
            } else if (this.currentPassword instanceof TL_account_password) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.changePasswordRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.turnPasswordOffRow = i;
                if (this.currentPassword.has_recovery) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.changeRecoveryEmailRow = i;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.setRecoveryEmailRow = i;
                }
                if (this.waitingForEmail) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.passwordEmailVerifyDetailRow = i;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.passwordEnabledDetailRow = i;
                }
            }
        }
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        if (this.passwordEntered) {
            if (this.listView != null) {
                this.listView.setVisibility(0);
                this.scrollView.setVisibility(4);
                this.emptyView.setVisibility(0);
                this.listView.setEmptyView(this.emptyView);
            }
            if (this.passwordEditText != null) {
                this.doneItem.setVisibility(8);
                this.passwordEditText.setVisibility(4);
                this.titleTextView.setVisibility(4);
                this.bottomTextView.setVisibility(4);
                this.bottomButton.setVisibility(4);
                return;
            }
            return;
        }
        if (this.listView != null) {
            this.listView.setEmptyView(null);
            this.listView.setVisibility(4);
            this.scrollView.setVisibility(0);
            this.emptyView.setVisibility(4);
        }
        if (this.passwordEditText != null) {
            this.doneItem.setVisibility(0);
            this.passwordEditText.setVisibility(0);
            this.titleTextView.setVisibility(0);
            this.bottomButton.setVisibility(0);
            this.bottomTextView.setVisibility(4);
            this.bottomButton.setText(LocaleController.getString("ForgotPassword", R.string.ForgotPassword));
            if (this.currentPassword.hint == null || this.currentPassword.hint.length() <= 0) {
                this.passwordEditText.setHint(TtmlNode.ANONYMOUS_REGION_ID);
            } else {
                this.passwordEditText.setHint(this.currentPassword.hint);
            }
            AndroidUtilities.runOnUIThread(new C52618(), 200);
        }
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new C52471());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.doneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.scrollView = new ScrollView(context);
        this.scrollView.setFillViewport(true);
        frameLayout.addView(this.scrollView, LayoutHelper.createFrame(-1, -1.0f));
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        this.scrollView.addView(linearLayout, LayoutHelper.createScroll(-1, -2, 51));
        this.titleTextView = new TextView(context);
        this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
        this.titleTextView.setTextSize(1, 18.0f);
        this.titleTextView.setGravity(1);
        linearLayout.addView(this.titleTextView, LayoutHelper.createLinear(-2, -2, 1, 0, 38, 0, 0));
        this.passwordEditText = new EditTextBoldCursor(context);
        this.passwordEditText.setTextSize(1, 20.0f);
        this.passwordEditText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.passwordEditText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.passwordEditText.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.passwordEditText.setMaxLines(1);
        this.passwordEditText.setLines(1);
        this.passwordEditText.setGravity(1);
        this.passwordEditText.setSingleLine(true);
        this.passwordEditText.setInputType(129);
        this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.passwordEditText.setTypeface(Typeface.DEFAULT);
        this.passwordEditText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.passwordEditText.setCursorSize(AndroidUtilities.dp(20.0f));
        this.passwordEditText.setCursorWidth(1.5f);
        linearLayout.addView(this.passwordEditText, LayoutHelper.createLinear(-1, 36, 51, 40, 32, 40, 0));
        this.passwordEditText.setOnEditorActionListener(new C52482());
        this.passwordEditText.setCustomSelectionActionModeCallback(new C52493());
        this.bottomTextView = new TextView(context);
        this.bottomTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
        this.bottomTextView.setTextSize(1, 14.0f);
        this.bottomTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        this.bottomTextView.setText(LocaleController.getString("YourEmailInfo", R.string.YourEmailInfo));
        linearLayout.addView(this.bottomTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 40, 30, 40, 0));
        View linearLayout2 = new LinearLayout(context);
        linearLayout2.setGravity(80);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -1));
        this.bottomButton = new TextView(context);
        this.bottomButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
        this.bottomButton.setTextSize(1, 14.0f);
        this.bottomButton.setGravity((LocaleController.isRTL ? 5 : 3) | 80);
        this.bottomButton.setText(LocaleController.getString("YourEmailSkip", R.string.YourEmailSkip));
        this.bottomButton.setPadding(0, AndroidUtilities.dp(10.0f), 0, 0);
        linearLayout2.addView(this.bottomButton, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 40, 0, 40, 14));
        this.bottomButton.setOnClickListener(new C52544());
        if (this.type == 0) {
            this.emptyView = new EmptyTextProgressView(context);
            this.emptyView.showProgress();
            this.listView = new RecyclerListView(context);
            this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false));
            this.listView.setEmptyView(this.emptyView);
            this.listView.setVerticalScrollBarEnabled(false);
            frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
            RecyclerListView recyclerListView = this.listView;
            Adapter listAdapter = new ListAdapter(context);
            this.listAdapter = listAdapter;
            recyclerListView.setAdapter(listAdapter);
            this.listView.setOnItemClickListener(new C52565());
            updateRows();
            this.actionBar.setTitle(LocaleController.getString("TwoStepVerification", R.string.TwoStepVerification));
            this.titleTextView.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
        } else if (this.type == 1) {
            setPasswordSetState(this.passwordSetState);
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.didSetTwoStepPassword) {
            if (!(objArr == null || objArr.length <= 0 || objArr[0] == null)) {
                this.currentPasswordHash = (byte[]) objArr[0];
            }
            loadPasswordInfo(false);
            updateRows();
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[21];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[2] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[3] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[7] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[8] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[9] = new ThemeDescription(this.emptyView, ThemeDescription.FLAG_PROGRESSBAR, null, null, null, null, Theme.key_progressCircle);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[11] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText3);
        r9[12] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[13] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r9[14] = new ThemeDescription(this.titleTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r9[15] = new ThemeDescription(this.bottomTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r9[16] = new ThemeDescription(this.bottomButton, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlueText4);
        r9[17] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[18] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r9[19] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r9[20] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        return r9;
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        if (this.type == 0) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetTwoStepPassword);
        }
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.type == 0) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetTwoStepPassword);
            if (this.shortPollRunnable != null) {
                AndroidUtilities.cancelRunOnUIThread(this.shortPollRunnable);
                this.shortPollRunnable = null;
            }
            this.destroyed = true;
        }
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Throwable e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onResume() {
        super.onResume();
        if (this.type == 1) {
            AndroidUtilities.runOnUIThread(new C52576(), 200);
        }
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && this.type == 1) {
            AndroidUtilities.showKeyboard(this.passwordEditText);
        }
    }
}
