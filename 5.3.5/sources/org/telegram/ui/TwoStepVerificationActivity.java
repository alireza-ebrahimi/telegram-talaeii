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
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_account_getPassword;
import org.telegram.tgnet.TLRPC$TL_account_getPasswordSettings;
import org.telegram.tgnet.TLRPC$TL_account_noPassword;
import org.telegram.tgnet.TLRPC$TL_account_password;
import org.telegram.tgnet.TLRPC$TL_account_passwordInputSettings;
import org.telegram.tgnet.TLRPC$TL_account_updatePasswordSettings;
import org.telegram.tgnet.TLRPC$TL_auth_passwordRecovery;
import org.telegram.tgnet.TLRPC$TL_auth_recoverPassword;
import org.telegram.tgnet.TLRPC$TL_auth_requestPasswordRecovery;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$account_Password;
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
    class C34081 extends ActionBarMenuOnItemClick {
        C34081() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                TwoStepVerificationActivity.this.finishFragment();
            } else if (id == 1) {
                TwoStepVerificationActivity.this.processDone();
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$2 */
    class C34092 implements OnEditorActionListener {
        C34092() {
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
    class C34103 implements Callback {
        C34103() {
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4 */
    class C34154 implements OnClickListener {

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4$1 */
        class C34131 implements RequestDelegate {
            C34131() {
            }

            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        TwoStepVerificationActivity.this.needHideProgress();
                        if (error == null) {
                            final TLRPC$TL_auth_passwordRecovery res = response;
                            Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                            builder.setMessage(LocaleController.formatString("RestoreEmailSent", R.string.RestoreEmailSent, new Object[]{res.email_pattern}));
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TwoStepVerificationActivity fragment = new TwoStepVerificationActivity(1);
                                    fragment.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                                    fragment.currentPassword.email_unconfirmed_pattern = res.email_pattern;
                                    fragment.passwordSetState = 4;
                                    TwoStepVerificationActivity.this.presentFragment(fragment);
                                }
                            });
                            Dialog dialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                            if (dialog != null) {
                                dialog.setCanceledOnTouchOutside(false);
                                dialog.setCancelable(false);
                            }
                        } else if (error.text.startsWith("FLOOD_WAIT")) {
                            String timeString;
                            int time = Utilities.parseInt(error.text).intValue();
                            if (time < 60) {
                                timeString = LocaleController.formatPluralString("Seconds", time);
                            } else {
                                timeString = LocaleController.formatPluralString("Minutes", time / 60);
                            }
                            TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                        } else {
                            TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), error.text);
                        }
                    }
                });
            }
        }

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$4$2 */
        class C34142 implements DialogInterface.OnClickListener {
            C34142() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                TwoStepVerificationActivity.this.email = "";
                TwoStepVerificationActivity.this.setNewPassword(false);
            }
        }

        C34154() {
        }

        public void onClick(View v) {
            if (TwoStepVerificationActivity.this.type == 0) {
                if (TwoStepVerificationActivity.this.currentPassword.has_recovery) {
                    TwoStepVerificationActivity.this.needShowProgress();
                    ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_auth_requestPasswordRecovery(), new C34131(), 10);
                    return;
                }
                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle), LocaleController.getString("RestorePasswordNoEmailText", R.string.RestorePasswordNoEmailText));
            } else if (TwoStepVerificationActivity.this.passwordSetState == 4) {
                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("RestorePasswordNoEmailTitle", R.string.RestorePasswordNoEmailTitle), LocaleController.getString("RestoreEmailTroubleText", R.string.RestoreEmailTroubleText));
            } else {
                Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("YourEmailSkipWarningText", R.string.YourEmailSkipWarningText));
                builder.setTitle(LocaleController.getString("YourEmailSkipWarning", R.string.YourEmailSkipWarning));
                builder.setPositiveButton(LocaleController.getString("YourEmailSkip", R.string.YourEmailSkip), new C34142());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                TwoStepVerificationActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$5 */
    class C34175 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.TwoStepVerificationActivity$5$1 */
        class C34161 implements DialogInterface.OnClickListener {
            C34161() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                TwoStepVerificationActivity.this.setNewPassword(true);
            }
        }

        C34175() {
        }

        public void onItemClick(View view, int position) {
            TwoStepVerificationActivity fragment;
            if (position == TwoStepVerificationActivity.this.setPasswordRow || position == TwoStepVerificationActivity.this.changePasswordRow) {
                fragment = new TwoStepVerificationActivity(1);
                fragment.currentPasswordHash = TwoStepVerificationActivity.this.currentPasswordHash;
                fragment.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                TwoStepVerificationActivity.this.presentFragment(fragment);
            } else if (position == TwoStepVerificationActivity.this.setRecoveryEmailRow || position == TwoStepVerificationActivity.this.changeRecoveryEmailRow) {
                fragment = new TwoStepVerificationActivity(1);
                fragment.currentPasswordHash = TwoStepVerificationActivity.this.currentPasswordHash;
                fragment.currentPassword = TwoStepVerificationActivity.this.currentPassword;
                fragment.emailOnly = true;
                fragment.passwordSetState = 3;
                TwoStepVerificationActivity.this.presentFragment(fragment);
            } else if (position == TwoStepVerificationActivity.this.turnPasswordOffRow || position == TwoStepVerificationActivity.this.abortPasswordRow) {
                Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                builder.setMessage(LocaleController.getString("TurnPasswordOffQuestion", R.string.TurnPasswordOffQuestion));
                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C34161());
                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                TwoStepVerificationActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$6 */
    class C34186 implements Runnable {
        C34186() {
        }

        public void run() {
            if (TwoStepVerificationActivity.this.passwordEditText != null) {
                TwoStepVerificationActivity.this.passwordEditText.requestFocus();
                AndroidUtilities.showKeyboard(TwoStepVerificationActivity.this.passwordEditText);
            }
        }
    }

    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$8 */
    class C34228 implements Runnable {
        C34228() {
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

        public boolean isEnabled(ViewHolder holder) {
            int position = holder.getAdapterPosition();
            return (position == TwoStepVerificationActivity.this.setPasswordDetailRow || position == TwoStepVerificationActivity.this.shadowRow || position == TwoStepVerificationActivity.this.passwordSetupDetailRow || position == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow || position == TwoStepVerificationActivity.this.passwordEnabledDetailRow) ? false : true;
        }

        public int getItemCount() {
            return (TwoStepVerificationActivity.this.loading || TwoStepVerificationActivity.this.currentPassword == null) ? 0 : TwoStepVerificationActivity.this.rowCount;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            switch (viewType) {
                case 0:
                    view = new TextSettingsCell(this.mContext);
                    view.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    view = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            boolean z = true;
            switch (holder.getItemViewType()) {
                case 0:
                    TextSettingsCell textCell = holder.itemView;
                    textCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    if (position == TwoStepVerificationActivity.this.changePasswordRow) {
                        textCell.setText(LocaleController.getString("ChangePassword", R.string.ChangePassword), true);
                        return;
                    } else if (position == TwoStepVerificationActivity.this.setPasswordRow) {
                        textCell.setText(LocaleController.getString("SetAdditionalPassword", R.string.SetAdditionalPassword), true);
                        return;
                    } else if (position == TwoStepVerificationActivity.this.turnPasswordOffRow) {
                        textCell.setText(LocaleController.getString("TurnPasswordOff", R.string.TurnPasswordOff), true);
                        return;
                    } else if (position == TwoStepVerificationActivity.this.changeRecoveryEmailRow) {
                        String string = LocaleController.getString("ChangeRecoveryEmail", R.string.ChangeRecoveryEmail);
                        if (TwoStepVerificationActivity.this.abortPasswordRow == -1) {
                            z = false;
                        }
                        textCell.setText(string, z);
                        return;
                    } else if (position == TwoStepVerificationActivity.this.setRecoveryEmailRow) {
                        textCell.setText(LocaleController.getString("SetRecoveryEmail", R.string.SetRecoveryEmail), false);
                        return;
                    } else if (position == TwoStepVerificationActivity.this.abortPasswordRow) {
                        textCell.setTag(Theme.key_windowBackgroundWhiteRedText3);
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText3));
                        textCell.setText(LocaleController.getString("AbortPassword", R.string.AbortPassword), false);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextInfoPrivacyCell privacyCell = holder.itemView;
                    if (position == TwoStepVerificationActivity.this.setPasswordDetailRow) {
                        privacyCell.setText(LocaleController.getString("SetAdditionalPasswordInfo", R.string.SetAdditionalPasswordInfo));
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (position == TwoStepVerificationActivity.this.shadowRow) {
                        privacyCell.setText("");
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (position == TwoStepVerificationActivity.this.passwordSetupDetailRow) {
                        privacyCell.setText(LocaleController.formatString("EmailPasswordConfirmText", R.string.EmailPasswordConfirmText, new Object[]{TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern}));
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_top, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (position == TwoStepVerificationActivity.this.passwordEnabledDetailRow) {
                        privacyCell.setText(LocaleController.getString("EnabledPasswordText", R.string.EnabledPasswordText));
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (position == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow) {
                        privacyCell.setText(LocaleController.formatString("PendingEmailText", R.string.PendingEmailText, new Object[]{TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern}));
                        privacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public int getItemViewType(int position) {
            if (position == TwoStepVerificationActivity.this.setPasswordDetailRow || position == TwoStepVerificationActivity.this.shadowRow || position == TwoStepVerificationActivity.this.passwordSetupDetailRow || position == TwoStepVerificationActivity.this.passwordEnabledDetailRow || position == TwoStepVerificationActivity.this.passwordEmailVerifyDetailRow) {
                return 1;
            }
            return 0;
        }
    }

    public TwoStepVerificationActivity(int type) {
        this.type = type;
        if (type == 0) {
            loadPasswordInfo(false);
        }
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
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new C34081());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = this.fragmentView;
        frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.doneItem = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.scrollView = new ScrollView(context);
        this.scrollView.setFillViewport(true);
        frameLayout.addView(this.scrollView, LayoutHelper.createFrame(-1, -1.0f));
        LinearLayout linearLayout = new LinearLayout(context);
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
        this.passwordEditText.setInputType(TsExtractor.TS_STREAM_TYPE_AC3);
        this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        this.passwordEditText.setTypeface(Typeface.DEFAULT);
        this.passwordEditText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.passwordEditText.setCursorSize(AndroidUtilities.dp(20.0f));
        this.passwordEditText.setCursorWidth(1.5f);
        linearLayout.addView(this.passwordEditText, LayoutHelper.createLinear(-1, 36, 51, 40, 32, 40, 0));
        this.passwordEditText.setOnEditorActionListener(new C34092());
        this.passwordEditText.setCustomSelectionActionModeCallback(new C34103());
        this.bottomTextView = new TextView(context);
        this.bottomTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
        this.bottomTextView.setTextSize(1, 14.0f);
        this.bottomTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 48);
        this.bottomTextView.setText(LocaleController.getString("YourEmailInfo", R.string.YourEmailInfo));
        linearLayout.addView(this.bottomTextView, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 48, 40, 30, 40, 0));
        LinearLayout linearLayout2 = new LinearLayout(context);
        linearLayout2.setGravity(80);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -1));
        this.bottomButton = new TextView(context);
        this.bottomButton.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlueText4));
        this.bottomButton.setTextSize(1, 14.0f);
        this.bottomButton.setGravity((LocaleController.isRTL ? 5 : 3) | 80);
        this.bottomButton.setText(LocaleController.getString("YourEmailSkip", R.string.YourEmailSkip));
        this.bottomButton.setPadding(0, AndroidUtilities.dp(10.0f), 0, 0);
        linearLayout2.addView(this.bottomButton, LayoutHelper.createLinear(-2, -2, (LocaleController.isRTL ? 5 : 3) | 80, 40, 0, 40, 14));
        this.bottomButton.setOnClickListener(new C34154());
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
            this.listView.setOnItemClickListener(new C34175());
            updateRows();
            this.actionBar.setTitle(LocaleController.getString("TwoStepVerification", R.string.TwoStepVerification));
            this.titleTextView.setText(LocaleController.getString("PleaseEnterCurrentPassword", R.string.PleaseEnterCurrentPassword));
        } else if (this.type == 1) {
            setPasswordSetState(this.passwordSetState);
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.didSetTwoStepPassword) {
            if (!(args == null || args.length <= 0 || args[0] == null)) {
                this.currentPasswordHash = (byte[]) args[0];
            }
            loadPasswordInfo(false);
            updateRows();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.type == 1) {
            AndroidUtilities.runOnUIThread(new C34186(), 200);
        }
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && this.type == 1) {
            AndroidUtilities.showKeyboard(this.passwordEditText);
        }
    }

    private void loadPasswordInfo(final boolean silent) {
        if (!silent) {
            this.loading = true;
            if (this.listAdapter != null) {
                this.listAdapter.notifyDataSetChanged();
            }
        }
        ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_account_getPassword(), new RequestDelegate() {
            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$7$1$1 */
                    class C34191 implements Runnable {
                        C34191() {
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
                        if (error == null) {
                            if (!silent) {
                                TwoStepVerificationActivity twoStepVerificationActivity = TwoStepVerificationActivity.this;
                                boolean z2 = TwoStepVerificationActivity.this.currentPassword != null || (response instanceof TLRPC$TL_account_noPassword);
                                twoStepVerificationActivity.passwordEntered = z2;
                            }
                            TwoStepVerificationActivity.this.currentPassword = (TLRPC$account_Password) response;
                            TwoStepVerificationActivity twoStepVerificationActivity2 = TwoStepVerificationActivity.this;
                            if (TwoStepVerificationActivity.this.currentPassword.email_unconfirmed_pattern.length() <= 0) {
                                z = false;
                            }
                            twoStepVerificationActivity2.waitingForEmail = z;
                            byte[] salt = new byte[(TwoStepVerificationActivity.this.currentPassword.new_salt.length + 8)];
                            Utilities.random.nextBytes(salt);
                            System.arraycopy(TwoStepVerificationActivity.this.currentPassword.new_salt, 0, salt, 0, TwoStepVerificationActivity.this.currentPassword.new_salt.length);
                            TwoStepVerificationActivity.this.currentPassword.new_salt = salt;
                        }
                        if (TwoStepVerificationActivity.this.type == 0 && !TwoStepVerificationActivity.this.destroyed && TwoStepVerificationActivity.this.shortPollRunnable == null) {
                            TwoStepVerificationActivity.this.shortPollRunnable = new C34191();
                            AndroidUtilities.runOnUIThread(TwoStepVerificationActivity.this.shortPollRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
                        }
                        TwoStepVerificationActivity.this.updateRows();
                    }
                });
            }
        }, 10);
    }

    private void setPasswordSetState(int state) {
        int i = 4;
        if (this.passwordEditText != null) {
            this.passwordSetState = state;
            if (this.passwordSetState == 0) {
                this.actionBar.setTitle(LocaleController.getString("YourPassword", R.string.YourPassword));
                if (this.currentPassword instanceof TLRPC$TL_account_noPassword) {
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
                    i = 0;
                }
                textView.setVisibility(i);
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
            this.passwordEditText.setText("");
        }
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
            if (this.currentPassword instanceof TLRPC$TL_account_noPassword) {
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
            } else if (this.currentPassword instanceof TLRPC$TL_account_password) {
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
                this.passwordEditText.setHint("");
            } else {
                this.passwordEditText.setHint(this.currentPassword.hint);
            }
            AndroidUtilities.runOnUIThread(new C34228(), 200);
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

    private void needHideProgress() {
        if (this.progressDialog != null) {
            try {
                this.progressDialog.dismiss();
            } catch (Exception e) {
                FileLog.e(e);
            }
            this.progressDialog = null;
        }
    }

    private boolean isValidEmail(String text) {
        if (text == null || text.length() < 3) {
            return false;
        }
        int dot = text.lastIndexOf(46);
        int dog = text.lastIndexOf(64);
        if (dot < 0 || dog < 0 || dot < dog) {
            return false;
        }
        return true;
    }

    private void showAlertWithText(String title, String text) {
        Builder builder = new Builder(getParentActivity());
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
        builder.setTitle(title);
        builder.setMessage(text);
        showDialog(builder.create());
    }

    private void setNewPassword(final boolean clear) {
        final TLRPC$TL_account_updatePasswordSettings req = new TLRPC$TL_account_updatePasswordSettings();
        req.current_password_hash = this.currentPasswordHash;
        req.new_settings = new TLRPC$TL_account_passwordInputSettings();
        if (!clear) {
            TLRPC$TL_account_passwordInputSettings tLRPC$TL_account_passwordInputSettings;
            if (this.firstPassword != null && this.firstPassword.length() > 0) {
                byte[] newPasswordBytes = null;
                try {
                    newPasswordBytes = this.firstPassword.getBytes("UTF-8");
                } catch (Exception e) {
                    FileLog.e(e);
                }
                byte[] new_salt = this.currentPassword.new_salt;
                byte[] hash = new byte[((new_salt.length * 2) + newPasswordBytes.length)];
                System.arraycopy(new_salt, 0, hash, 0, new_salt.length);
                System.arraycopy(newPasswordBytes, 0, hash, new_salt.length, newPasswordBytes.length);
                System.arraycopy(new_salt, 0, hash, hash.length - new_salt.length, new_salt.length);
                tLRPC$TL_account_passwordInputSettings = req.new_settings;
                tLRPC$TL_account_passwordInputSettings.flags |= 1;
                req.new_settings.hint = this.hint;
                req.new_settings.new_password_hash = Utilities.computeSHA256(hash, 0, hash.length);
                req.new_settings.new_salt = new_salt;
            }
            if (this.email.length() > 0) {
                tLRPC$TL_account_passwordInputSettings = req.new_settings;
                tLRPC$TL_account_passwordInputSettings.flags |= 2;
                req.new_settings.email = this.email.trim();
            }
        } else if (this.waitingForEmail && (this.currentPassword instanceof TLRPC$TL_account_noPassword)) {
            req.new_settings.flags = 2;
            req.new_settings.email = "";
            req.current_password_hash = new byte[0];
        } else {
            req.new_settings.flags = 3;
            req.new_settings.hint = "";
            req.new_settings.new_password_hash = new byte[0];
            req.new_settings.new_salt = new byte[0];
            req.new_settings.email = "";
        }
        needShowProgress();
        ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
            public void run(final TLObject response, final TLRPC$TL_error error) {
                AndroidUtilities.runOnUIThread(new Runnable() {

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$9$1$1 */
                    class C34231 implements DialogInterface.OnClickListener {
                        C34231() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[]{req.new_settings.new_password_hash});
                            TwoStepVerificationActivity.this.finishFragment();
                        }
                    }

                    /* renamed from: org.telegram.ui.TwoStepVerificationActivity$9$1$2 */
                    class C34242 implements DialogInterface.OnClickListener {
                        C34242() {
                        }

                        public void onClick(DialogInterface dialogInterface, int i) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[]{req.new_settings.new_password_hash});
                            TwoStepVerificationActivity.this.finishFragment();
                        }
                    }

                    public void run() {
                        TwoStepVerificationActivity.this.needHideProgress();
                        Builder builder;
                        Dialog dialog;
                        if (error == null && (response instanceof TLRPC$TL_boolTrue)) {
                            if (clear) {
                                TwoStepVerificationActivity.this.currentPassword = null;
                                TwoStepVerificationActivity.this.currentPasswordHash = new byte[0];
                                TwoStepVerificationActivity.this.loadPasswordInfo(false);
                                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didRemovedTwoStepPassword, new Object[0]);
                                TwoStepVerificationActivity.this.updateRows();
                            } else if (TwoStepVerificationActivity.this.getParentActivity() != null) {
                                builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C34231());
                                builder.setMessage(LocaleController.getString("YourPasswordSuccessText", R.string.YourPasswordSuccessText));
                                builder.setTitle(LocaleController.getString("YourPasswordSuccess", R.string.YourPasswordSuccess));
                                dialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                if (dialog != null) {
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCancelable(false);
                                }
                            }
                        } else if (error == null) {
                        } else {
                            if (error.text.equals("EMAIL_UNCONFIRMED")) {
                                builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C34242());
                                builder.setMessage(LocaleController.getString("YourEmailAlmostThereText", R.string.YourEmailAlmostThereText));
                                builder.setTitle(LocaleController.getString("YourEmailAlmostThere", R.string.YourEmailAlmostThere));
                                dialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                if (dialog != null) {
                                    dialog.setCanceledOnTouchOutside(false);
                                    dialog.setCancelable(false);
                                }
                            } else if (error.text.equals("EMAIL_INVALID")) {
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.getString("PasswordEmailInvalid", R.string.PasswordEmailInvalid));
                            } else if (error.text.startsWith("FLOOD_WAIT")) {
                                String timeString;
                                int time = Utilities.parseInt(error.text).intValue();
                                if (time < 60) {
                                    timeString = LocaleController.formatPluralString("Seconds", time);
                                } else {
                                    timeString = LocaleController.formatPluralString("Minutes", time / 60);
                                }
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                            } else {
                                TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), error.text);
                            }
                        }
                    }
                });
            }
        }, 10);
    }

    private void processDone() {
        if (this.type == 0) {
            if (!this.passwordEntered) {
                String oldPassword = this.passwordEditText.getText().toString();
                if (oldPassword.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                byte[] oldPasswordBytes = null;
                try {
                    oldPasswordBytes = oldPassword.getBytes("UTF-8");
                } catch (Exception e) {
                    FileLog.e(e);
                }
                needShowProgress();
                byte[] hash = new byte[((this.currentPassword.current_salt.length * 2) + oldPasswordBytes.length)];
                System.arraycopy(this.currentPassword.current_salt, 0, hash, 0, this.currentPassword.current_salt.length);
                System.arraycopy(oldPasswordBytes, 0, hash, this.currentPassword.current_salt.length, oldPasswordBytes.length);
                System.arraycopy(this.currentPassword.current_salt, 0, hash, hash.length - this.currentPassword.current_salt.length, this.currentPassword.current_salt.length);
                final TLRPC$TL_account_getPasswordSettings req = new TLRPC$TL_account_getPasswordSettings();
                req.current_password_hash = Utilities.computeSHA256(hash, 0, hash.length);
                ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                    public void run(TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                TwoStepVerificationActivity.this.needHideProgress();
                                if (error == null) {
                                    TwoStepVerificationActivity.this.currentPasswordHash = req.current_password_hash;
                                    TwoStepVerificationActivity.this.passwordEntered = true;
                                    AndroidUtilities.hideKeyboard(TwoStepVerificationActivity.this.passwordEditText);
                                    TwoStepVerificationActivity.this.updateRows();
                                } else if (error.text.equals("PASSWORD_HASH_INVALID")) {
                                    TwoStepVerificationActivity.this.onPasscodeError(true);
                                } else if (error.text.startsWith("FLOOD_WAIT")) {
                                    String timeString;
                                    int time = Utilities.parseInt(error.text).intValue();
                                    if (time < 60) {
                                        timeString = LocaleController.formatPluralString("Seconds", time);
                                    } else {
                                        timeString = LocaleController.formatPluralString("Minutes", time / 60);
                                    }
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                                } else {
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), error.text);
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
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                onPasscodeError(true);
            } else if (this.passwordSetState == 2) {
                this.hint = this.passwordEditText.getText().toString();
                if (this.hint.toLowerCase().equals(this.firstPassword.toLowerCase())) {
                    try {
                        Toast.makeText(getParentActivity(), LocaleController.getString("PasswordAsHintError", R.string.PasswordAsHintError), 0).show();
                    } catch (Exception e22) {
                        FileLog.e(e22);
                    }
                    onPasscodeError(false);
                } else if (this.currentPassword.has_recovery) {
                    this.email = "";
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
                String code = this.passwordEditText.getText().toString();
                if (code.length() == 0) {
                    onPasscodeError(false);
                    return;
                }
                TLRPC$TL_auth_recoverPassword req2 = new TLRPC$TL_auth_recoverPassword();
                req2.code = code;
                ConnectionsManager.getInstance().sendRequest(req2, new RequestDelegate() {
                    public void run(TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {

                            /* renamed from: org.telegram.ui.TwoStepVerificationActivity$11$1$1 */
                            class C34061 implements DialogInterface.OnClickListener {
                                C34061() {
                                }

                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetTwoStepPassword, new Object[0]);
                                    TwoStepVerificationActivity.this.finishFragment();
                                }
                            }

                            public void run() {
                                if (error == null) {
                                    Builder builder = new Builder(TwoStepVerificationActivity.this.getParentActivity());
                                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C34061());
                                    builder.setMessage(LocaleController.getString("PasswordReset", R.string.PasswordReset));
                                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                    Dialog dialog = TwoStepVerificationActivity.this.showDialog(builder.create());
                                    if (dialog != null) {
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.setCancelable(false);
                                    }
                                } else if (error.text.startsWith("CODE_INVALID")) {
                                    TwoStepVerificationActivity.this.onPasscodeError(true);
                                } else if (error.text.startsWith("FLOOD_WAIT")) {
                                    String timeString;
                                    int time = Utilities.parseInt(error.text).intValue();
                                    if (time < 60) {
                                        timeString = LocaleController.formatPluralString("Seconds", time);
                                    } else {
                                        timeString = LocaleController.formatPluralString("Minutes", time / 60);
                                    }
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), LocaleController.formatString("FloodWaitTime", R.string.FloodWaitTime, new Object[]{timeString}));
                                } else {
                                    TwoStepVerificationActivity.this.showAlertWithText(LocaleController.getString("AppName", R.string.AppName), error.text);
                                }
                            }
                        });
                    }
                }, 10);
            }
        }
    }

    private void onPasscodeError(boolean clear) {
        if (getParentActivity() != null) {
            Vibrator v = (Vibrator) getParentActivity().getSystemService("vibrator");
            if (v != null) {
                v.vibrate(200);
            }
            if (clear) {
                this.passwordEditText.setText("");
            }
            AndroidUtilities.shakeView(this.titleTextView, 2.0f, 0);
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
}
