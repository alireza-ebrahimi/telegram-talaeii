package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextUtils.TruncateAt;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.support.fingerprint.FingerprintManagerCompat;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView.Adapter;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.NumberPicker;
import org.telegram.ui.Components.NumberPicker.Formatter;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;

public class PasscodeActivity extends BaseFragment implements NotificationCenterDelegate {
    private static final int done_button = 1;
    private static final int password_item = 3;
    private static final int pin_item = 2;
    private int autoLockDetailRow;
    private int autoLockRow;
    private int captureDetailRow;
    private int captureRow;
    private int changePasscodeRow;
    private int currentPasswordType = 0;
    private TextView dropDown;
    private ActionBarMenuItem dropDownContainer;
    private Drawable dropDownDrawable;
    private int fingerprintRow;
    private String firstPassword;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int passcodeDetailRow;
    private int passcodeRow;
    private int passcodeSetStep = 0;
    private EditTextBoldCursor passwordEditText;
    private int rowCount;
    private TextView titleTextView;
    private int type;

    /* renamed from: org.telegram.ui.PasscodeActivity$1 */
    class C49821 extends ActionBarMenuOnItemClick {
        C49821() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                PasscodeActivity.this.finishFragment();
            } else if (i == 1) {
                if (PasscodeActivity.this.passcodeSetStep == 0) {
                    PasscodeActivity.this.processNext();
                } else if (PasscodeActivity.this.passcodeSetStep == 1) {
                    PasscodeActivity.this.processDone();
                }
            } else if (i == 2) {
                PasscodeActivity.this.currentPasswordType = 0;
                PasscodeActivity.this.updateDropDownTextView();
            } else if (i == 3) {
                PasscodeActivity.this.currentPasswordType = 1;
                PasscodeActivity.this.updateDropDownTextView();
            }
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$2 */
    class C49832 implements OnEditorActionListener {
        C49832() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (PasscodeActivity.this.passcodeSetStep == 0) {
                PasscodeActivity.this.processNext();
                return true;
            } else if (PasscodeActivity.this.passcodeSetStep != 1) {
                return false;
            } else {
                PasscodeActivity.this.processDone();
                return true;
            }
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$3 */
    class C49843 implements TextWatcher {
        C49843() {
        }

        public void afterTextChanged(Editable editable) {
            if (PasscodeActivity.this.passwordEditText.length() != 4) {
                return;
            }
            if (PasscodeActivity.this.type == 2 && UserConfig.passcodeType == 0) {
                PasscodeActivity.this.processDone();
            } else if (PasscodeActivity.this.type != 1 || PasscodeActivity.this.currentPasswordType != 0) {
            } else {
                if (PasscodeActivity.this.passcodeSetStep == 0) {
                    PasscodeActivity.this.processNext();
                } else if (PasscodeActivity.this.passcodeSetStep == 1) {
                    PasscodeActivity.this.processDone();
                }
            }
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$4 */
    class C49854 implements Callback {
        C49854() {
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

    /* renamed from: org.telegram.ui.PasscodeActivity$5 */
    class C49865 implements OnClickListener {
        C49865() {
        }

        public void onClick(View view) {
            PasscodeActivity.this.dropDownContainer.toggleSubMenu();
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$7 */
    class C49907 implements OnItemClickListener {

        /* renamed from: org.telegram.ui.PasscodeActivity$7$1 */
        class C49881 implements Formatter {
            C49881() {
            }

            public String format(int i) {
                if (i == 0) {
                    return LocaleController.getString("AutoLockDisabled", R.string.AutoLockDisabled);
                }
                if (i == 1) {
                    return LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Minutes", 1)});
                } else if (i == 2) {
                    return LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Minutes", 5)});
                } else if (i == 3) {
                    return LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Hours", 1)});
                } else if (i != 4) {
                    return TtmlNode.ANONYMOUS_REGION_ID;
                } else {
                    return LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Hours", 5)});
                }
            }
        }

        C49907() {
        }

        public void onItemClick(View view, final int i) {
            boolean z = true;
            if (!view.isEnabled()) {
                return;
            }
            if (i == PasscodeActivity.this.changePasscodeRow) {
                PasscodeActivity.this.presentFragment(new PasscodeActivity(1));
            } else if (i == PasscodeActivity.this.passcodeRow) {
                TextCheckCell textCheckCell = (TextCheckCell) view;
                if (UserConfig.passcodeHash.length() != 0) {
                    UserConfig.passcodeHash = TtmlNode.ANONYMOUS_REGION_ID;
                    UserConfig.appLocked = false;
                    UserConfig.saveConfig(false);
                    int childCount = PasscodeActivity.this.listView.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = PasscodeActivity.this.listView.getChildAt(i2);
                        if (childAt instanceof TextSettingsCell) {
                            ((TextSettingsCell) childAt).setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText7));
                            break;
                        }
                    }
                    textCheckCell.setChecked(UserConfig.passcodeHash.length() != 0);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetPasscode, new Object[0]);
                    return;
                }
                PasscodeActivity.this.presentFragment(new PasscodeActivity(1));
            } else if (i == PasscodeActivity.this.autoLockRow) {
                if (PasscodeActivity.this.getParentActivity() != null) {
                    Builder builder = new Builder(PasscodeActivity.this.getParentActivity());
                    builder.setTitle(LocaleController.getString("AutoLock", R.string.AutoLock));
                    final View numberPicker = new NumberPicker(PasscodeActivity.this.getParentActivity());
                    numberPicker.setMinValue(0);
                    numberPicker.setMaxValue(4);
                    if (UserConfig.autoLockIn == 0) {
                        numberPicker.setValue(0);
                    } else if (UserConfig.autoLockIn == 60) {
                        numberPicker.setValue(1);
                    } else if (UserConfig.autoLockIn == 300) {
                        numberPicker.setValue(2);
                    } else if (UserConfig.autoLockIn == 3600) {
                        numberPicker.setValue(3);
                    } else if (UserConfig.autoLockIn == 18000) {
                        numberPicker.setValue(4);
                    }
                    numberPicker.setFormatter(new C49881());
                    builder.setView(numberPicker);
                    builder.setNegativeButton(LocaleController.getString("Done", R.string.Done), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            int value = numberPicker.getValue();
                            if (value == 0) {
                                UserConfig.autoLockIn = 0;
                            } else if (value == 1) {
                                UserConfig.autoLockIn = 60;
                            } else if (value == 2) {
                                UserConfig.autoLockIn = 300;
                            } else if (value == 3) {
                                UserConfig.autoLockIn = 3600;
                            } else if (value == 4) {
                                UserConfig.autoLockIn = 18000;
                            }
                            PasscodeActivity.this.listAdapter.notifyItemChanged(i);
                            UserConfig.saveConfig(false);
                        }
                    });
                    PasscodeActivity.this.showDialog(builder.create());
                }
            } else if (i == PasscodeActivity.this.fingerprintRow) {
                if (UserConfig.useFingerprint) {
                    z = false;
                }
                UserConfig.useFingerprint = z;
                UserConfig.saveConfig(false);
                ((TextCheckCell) view).setChecked(UserConfig.useFingerprint);
            } else if (i == PasscodeActivity.this.captureRow) {
                if (UserConfig.allowScreenCapture) {
                    z = false;
                }
                UserConfig.allowScreenCapture = z;
                UserConfig.saveConfig(false);
                ((TextCheckCell) view).setChecked(UserConfig.allowScreenCapture);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetPasscode, new Object[0]);
            }
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$8 */
    class C49918 implements Runnable {
        C49918() {
        }

        public void run() {
            if (PasscodeActivity.this.passwordEditText != null) {
                PasscodeActivity.this.passwordEditText.requestFocus();
                AndroidUtilities.showKeyboard(PasscodeActivity.this.passwordEditText);
            }
        }
    }

    /* renamed from: org.telegram.ui.PasscodeActivity$9 */
    class C49929 implements OnPreDrawListener {
        C49929() {
        }

        public boolean onPreDraw() {
            PasscodeActivity.this.listView.getViewTreeObserver().removeOnPreDrawListener(this);
            PasscodeActivity.this.fixLayoutInternal();
            return true;
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return PasscodeActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == PasscodeActivity.this.passcodeRow || i == PasscodeActivity.this.fingerprintRow || i == PasscodeActivity.this.captureRow) ? 0 : (i == PasscodeActivity.this.changePasscodeRow || i == PasscodeActivity.this.autoLockRow) ? 1 : (i == PasscodeActivity.this.passcodeDetailRow || i == PasscodeActivity.this.autoLockDetailRow || i == PasscodeActivity.this.captureDetailRow) ? 2 : 0;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return adapterPosition == PasscodeActivity.this.passcodeRow || adapterPosition == PasscodeActivity.this.fingerprintRow || adapterPosition == PasscodeActivity.this.autoLockRow || adapterPosition == PasscodeActivity.this.captureRow || (UserConfig.passcodeHash.length() != 0 && adapterPosition == PasscodeActivity.this.changePasscodeRow);
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            boolean z = false;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    TextCheckCell textCheckCell = (TextCheckCell) viewHolder.itemView;
                    if (i == PasscodeActivity.this.passcodeRow) {
                        String string = LocaleController.getString("Passcode", R.string.Passcode);
                        if (UserConfig.passcodeHash.length() > 0) {
                            z = true;
                        }
                        textCheckCell.setTextAndCheck(string, z, true);
                        return;
                    } else if (i == PasscodeActivity.this.fingerprintRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("UnlockFingerprint", R.string.UnlockFingerprint), UserConfig.useFingerprint, true);
                        return;
                    } else if (i == PasscodeActivity.this.captureRow) {
                        textCheckCell.setTextAndCheck(LocaleController.getString("ScreenCapture", R.string.ScreenCapture), UserConfig.allowScreenCapture, false);
                        return;
                    } else {
                        return;
                    }
                case 1:
                    TextSettingsCell textSettingsCell = (TextSettingsCell) viewHolder.itemView;
                    if (i == PasscodeActivity.this.changePasscodeRow) {
                        textSettingsCell.setText(LocaleController.getString("ChangePasscode", R.string.ChangePasscode), false);
                        if (UserConfig.passcodeHash.length() == 0) {
                            textSettingsCell.setTag(Theme.key_windowBackgroundWhiteGrayText7);
                            textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText7));
                            return;
                        }
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                        return;
                    } else if (i == PasscodeActivity.this.autoLockRow) {
                        String formatString;
                        if (UserConfig.autoLockIn == 0) {
                            formatString = LocaleController.formatString("AutoLockDisabled", R.string.AutoLockDisabled, new Object[0]);
                        } else if (UserConfig.autoLockIn < 3600) {
                            formatString = LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Minutes", UserConfig.autoLockIn / 60)});
                        } else if (UserConfig.autoLockIn < 86400) {
                            formatString = LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Hours", (int) Math.ceil((double) ((((float) UserConfig.autoLockIn) / 60.0f) / 60.0f)))});
                        } else {
                            formatString = LocaleController.formatString("AutoLockInTime", R.string.AutoLockInTime, new Object[]{LocaleController.formatPluralString("Days", (int) Math.ceil((double) (((((float) UserConfig.autoLockIn) / 60.0f) / 60.0f) / 24.0f)))});
                        }
                        textSettingsCell.setTextAndValue(LocaleController.getString("AutoLock", R.string.AutoLock), formatString, true);
                        textSettingsCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                        textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                        return;
                    } else {
                        return;
                    }
                case 2:
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) viewHolder.itemView;
                    if (i == PasscodeActivity.this.passcodeDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("ChangePasscodeInfo", R.string.ChangePasscodeInfo));
                        if (PasscodeActivity.this.autoLockDetailRow != -1) {
                            textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                            return;
                        } else {
                            textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                            return;
                        }
                    } else if (i == PasscodeActivity.this.autoLockDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("AutoLockInfo", R.string.AutoLockInfo));
                        textInfoPrivacyCell.setBackgroundDrawable(Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                        return;
                    } else if (i == PasscodeActivity.this.captureDetailRow) {
                        textInfoPrivacyCell.setText(LocaleController.getString("ScreenCaptureInfo", R.string.ScreenCaptureInfo));
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
            View textCheckCell;
            switch (i) {
                case 0:
                    textCheckCell = new TextCheckCell(this.mContext);
                    textCheckCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                case 1:
                    textCheckCell = new TextSettingsCell(this.mContext);
                    textCheckCell.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
                    break;
                default:
                    textCheckCell = new TextInfoPrivacyCell(this.mContext);
                    break;
            }
            return new Holder(textCheckCell);
        }
    }

    public PasscodeActivity(int i) {
        this.type = i;
    }

    private void fixLayoutInternal() {
        if (this.dropDownContainer != null) {
            if (!AndroidUtilities.isTablet()) {
                LayoutParams layoutParams = (LayoutParams) this.dropDownContainer.getLayoutParams();
                layoutParams.topMargin = VERSION.SDK_INT >= 21 ? AndroidUtilities.statusBarHeight : 0;
                this.dropDownContainer.setLayoutParams(layoutParams);
            }
            if (AndroidUtilities.isTablet() || ApplicationLoader.applicationContext.getResources().getConfiguration().orientation != 2) {
                this.dropDown.setTextSize(20.0f);
            } else {
                this.dropDown.setTextSize(18.0f);
            }
        }
    }

    private void onPasscodeError() {
        if (getParentActivity() != null) {
            Vibrator vibrator = (Vibrator) getParentActivity().getSystemService("vibrator");
            if (vibrator != null) {
                vibrator.vibrate(200);
            }
            AndroidUtilities.shakeView(this.titleTextView, 2.0f, 0);
        }
    }

    private void processDone() {
        if (this.passwordEditText.getText().length() == 0) {
            onPasscodeError();
        } else if (this.type == 1) {
            if (this.firstPassword.equals(this.passwordEditText.getText().toString())) {
                try {
                    UserConfig.passcodeSalt = new byte[16];
                    Utilities.random.nextBytes(UserConfig.passcodeSalt);
                    Object bytes = this.firstPassword.getBytes(C3446C.UTF8_NAME);
                    Object obj = new byte[(bytes.length + 32)];
                    System.arraycopy(UserConfig.passcodeSalt, 0, obj, 0, 16);
                    System.arraycopy(bytes, 0, obj, 16, bytes.length);
                    System.arraycopy(UserConfig.passcodeSalt, 0, obj, bytes.length + 16, 16);
                    UserConfig.passcodeHash = Utilities.bytesToHex(Utilities.computeSHA256(obj, 0, obj.length));
                } catch (Throwable e) {
                    FileLog.e(e);
                }
                UserConfig.passcodeType = this.currentPasswordType;
                UserConfig.saveConfig(false);
                finishFragment();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.didSetPasscode, new Object[0]);
                this.passwordEditText.clearFocus();
                AndroidUtilities.hideKeyboard(this.passwordEditText);
                return;
            }
            try {
                Toast.makeText(getParentActivity(), LocaleController.getString("PasscodeDoNotMatch", R.string.PasscodeDoNotMatch), 0).show();
            } catch (Throwable e2) {
                FileLog.e(e2);
            }
            AndroidUtilities.shakeView(this.titleTextView, 2.0f, 0);
            this.passwordEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
        } else if (this.type != 2) {
        } else {
            if (UserConfig.checkPasscode(this.passwordEditText.getText().toString())) {
                this.passwordEditText.clearFocus();
                AndroidUtilities.hideKeyboard(this.passwordEditText);
                presentFragment(new PasscodeActivity(0), true);
                return;
            }
            this.passwordEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
            onPasscodeError();
        }
    }

    private void processNext() {
        if (this.passwordEditText.getText().length() == 0 || (this.currentPasswordType == 0 && this.passwordEditText.getText().length() != 4)) {
            onPasscodeError();
            return;
        }
        if (this.currentPasswordType == 0) {
            this.actionBar.setTitle(LocaleController.getString("PasscodePIN", R.string.PasscodePIN));
        } else {
            this.actionBar.setTitle(LocaleController.getString("PasscodePassword", R.string.PasscodePassword));
        }
        this.dropDownContainer.setVisibility(8);
        this.titleTextView.setText(LocaleController.getString("ReEnterYourPasscode", R.string.ReEnterYourPasscode));
        this.firstPassword = this.passwordEditText.getText().toString();
        this.passwordEditText.setText(TtmlNode.ANONYMOUS_REGION_ID);
        this.passcodeSetStep = 1;
    }

    private void updateDropDownTextView() {
        if (this.dropDown != null) {
            if (this.currentPasswordType == 0) {
                this.dropDown.setText(LocaleController.getString("PasscodePIN", R.string.PasscodePIN));
            } else if (this.currentPasswordType == 1) {
                this.dropDown.setText(LocaleController.getString("PasscodePassword", R.string.PasscodePassword));
            }
        }
        if ((this.type == 1 && this.currentPasswordType == 0) || (this.type == 2 && UserConfig.passcodeType == 0)) {
            this.passwordEditText.setFilters(new InputFilter[]{new LengthFilter(4)});
            this.passwordEditText.setInputType(3);
            this.passwordEditText.setKeyListener(DigitsKeyListener.getInstance("1234567890"));
        } else if ((this.type == 1 && this.currentPasswordType == 1) || (this.type == 2 && UserConfig.passcodeType == 1)) {
            this.passwordEditText.setFilters(new InputFilter[0]);
            this.passwordEditText.setKeyListener(null);
            this.passwordEditText.setInputType(129);
        }
        this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    private void updateRows() {
        this.rowCount = 0;
        int i = this.rowCount;
        this.rowCount = i + 1;
        this.passcodeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.changePasscodeRow = i;
        i = this.rowCount;
        this.rowCount = i + 1;
        this.passcodeDetailRow = i;
        if (UserConfig.passcodeHash.length() > 0) {
            try {
                if (VERSION.SDK_INT >= 23 && FingerprintManagerCompat.from(ApplicationLoader.applicationContext).isHardwareDetected()) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.fingerprintRow = i;
                }
            } catch (Throwable th) {
                FileLog.e(th);
            }
            i = this.rowCount;
            this.rowCount = i + 1;
            this.autoLockRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.autoLockDetailRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.captureRow = i;
            i = this.rowCount;
            this.rowCount = i + 1;
            this.captureDetailRow = i;
            return;
        }
        this.captureRow = -1;
        this.captureDetailRow = -1;
        this.fingerprintRow = -1;
        this.autoLockRow = -1;
        this.autoLockDetailRow = -1;
    }

    public View createView(Context context) {
        if (this.type != 3) {
            this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        }
        this.actionBar.setAllowOverlayTitle(false);
        this.actionBar.setActionBarMenuOnItemClick(new C49821());
        this.fragmentView = new FrameLayout(context);
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
        if (this.type != 0) {
            ActionBarMenu createMenu = this.actionBar.createMenu();
            createMenu.addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
            this.titleTextView = new TextView(context);
            this.titleTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText6));
            if (this.type != 1) {
                this.titleTextView.setText(LocaleController.getString("EnterCurrentPasscode", R.string.EnterCurrentPasscode));
            } else if (UserConfig.passcodeHash.length() != 0) {
                this.titleTextView.setText(LocaleController.getString("EnterNewPasscode", R.string.EnterNewPasscode));
            } else {
                this.titleTextView.setText(LocaleController.getString("EnterNewFirstPasscode", R.string.EnterNewFirstPasscode));
            }
            this.titleTextView.setTextSize(1, 18.0f);
            this.titleTextView.setGravity(1);
            frameLayout.addView(this.titleTextView, LayoutHelper.createFrame(-2, -2.0f, 1, BitmapDescriptorFactory.HUE_RED, 38.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.passwordEditText = new EditTextBoldCursor(context);
            this.passwordEditText.setTextSize(1, 20.0f);
            this.passwordEditText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.passwordEditText.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.passwordEditText.setMaxLines(1);
            this.passwordEditText.setLines(1);
            this.passwordEditText.setGravity(1);
            this.passwordEditText.setSingleLine(true);
            if (this.type == 1) {
                this.passcodeSetStep = 0;
                this.passwordEditText.setImeOptions(5);
            } else {
                this.passcodeSetStep = 1;
                this.passwordEditText.setImeOptions(6);
            }
            this.passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            this.passwordEditText.setTypeface(Typeface.DEFAULT);
            this.passwordEditText.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.passwordEditText.setCursorSize(AndroidUtilities.dp(20.0f));
            this.passwordEditText.setCursorWidth(1.5f);
            frameLayout.addView(this.passwordEditText, LayoutHelper.createFrame(-1, 36.0f, 51, 40.0f, 90.0f, 40.0f, BitmapDescriptorFactory.HUE_RED));
            this.passwordEditText.setOnEditorActionListener(new C49832());
            this.passwordEditText.addTextChangedListener(new C49843());
            this.passwordEditText.setCustomSelectionActionModeCallback(new C49854());
            if (this.type == 1) {
                frameLayout.setTag(Theme.key_windowBackgroundWhite);
                this.dropDownContainer = new ActionBarMenuItem(context, createMenu, 0, 0);
                this.dropDownContainer.setSubMenuOpenSide(1);
                this.dropDownContainer.addSubItem(2, LocaleController.getString("PasscodePIN", R.string.PasscodePIN));
                this.dropDownContainer.addSubItem(3, LocaleController.getString("PasscodePassword", R.string.PasscodePassword));
                this.actionBar.addView(this.dropDownContainer, LayoutHelper.createFrame(-2, -1.0f, 51, AndroidUtilities.isTablet() ? 64.0f : 56.0f, BitmapDescriptorFactory.HUE_RED, 40.0f, BitmapDescriptorFactory.HUE_RED));
                this.dropDownContainer.setOnClickListener(new C49865());
                this.dropDown = new TextView(context);
                this.dropDown.setGravity(3);
                this.dropDown.setSingleLine(true);
                this.dropDown.setLines(1);
                this.dropDown.setMaxLines(1);
                this.dropDown.setEllipsize(TruncateAt.END);
                this.dropDown.setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
                this.dropDown.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.dropDownDrawable = context.getResources().getDrawable(R.drawable.ic_arrow_drop_down).mutate();
                this.dropDownDrawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_actionBarDefaultTitle), Mode.MULTIPLY));
                this.dropDown.setCompoundDrawablesWithIntrinsicBounds(null, null, this.dropDownDrawable, null);
                this.dropDown.setCompoundDrawablePadding(AndroidUtilities.dp(4.0f));
                this.dropDown.setPadding(0, 0, AndroidUtilities.dp(10.0f), 0);
                this.dropDownContainer.addView(this.dropDown, LayoutHelper.createFrame(-2, -2.0f, 16, 16.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 1.0f));
            } else {
                this.actionBar.setTitle(LocaleController.getString("Passcode", R.string.Passcode));
            }
            updateDropDownTextView();
        } else {
            this.actionBar.setTitle(LocaleController.getString("Passcode", R.string.Passcode));
            frameLayout.setTag(Theme.key_windowBackgroundGray);
            frameLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
            this.listView = new RecyclerListView(context);
            this.listView.setLayoutManager(new LinearLayoutManager(context, 1, false) {
                public boolean supportsPredictiveItemAnimations() {
                    return false;
                }
            });
            this.listView.setVerticalScrollBarEnabled(false);
            this.listView.setItemAnimator(null);
            this.listView.setLayoutAnimation(null);
            frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1.0f));
            RecyclerListView recyclerListView = this.listView;
            Adapter listAdapter = new ListAdapter(context);
            this.listAdapter = listAdapter;
            recyclerListView.setAdapter(listAdapter);
            this.listView.setOnItemClickListener(new C49907());
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.didSetPasscode && this.type == 0) {
            updateRows();
            if (this.listAdapter != null) {
                this.listAdapter.notifyDataSetChanged();
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        r9 = new ThemeDescription[28];
        r9[0] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextCheckCell.class, TextSettingsCell.class}, null, null, null, Theme.key_windowBackgroundWhite);
        r9[1] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhite);
        r9[2] = new ThemeDescription(this.fragmentView, ThemeDescription.FLAG_BACKGROUND | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundGray);
        r9[3] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_actionBarDefault);
        r9[4] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LISTGLOWCOLOR, null, null, null, null, Theme.key_actionBarDefault);
        r9[5] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_ITEMSCOLOR, null, null, null, null, Theme.key_actionBarDefaultIcon);
        r9[6] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_TITLECOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[7] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SELECTORCOLOR, null, null, null, null, Theme.key_actionBarDefaultSelector);
        r9[8] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUBACKGROUND, null, null, null, null, Theme.key_actionBarDefaultSubmenuBackground);
        r9[9] = new ThemeDescription(this.actionBar, ThemeDescription.FLAG_AB_SUBMENUITEM, null, null, null, null, Theme.key_actionBarDefaultSubmenuItem);
        r9[10] = new ThemeDescription(this.listView, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r9[11] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r9[12] = new ThemeDescription(this.titleTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText6);
        r9[13] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[14] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_windowBackgroundWhiteInputField);
        r9[15] = new ThemeDescription(this.passwordEditText, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_windowBackgroundWhiteInputFieldActivated);
        r9[16] = new ThemeDescription(this.dropDown, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_actionBarDefaultTitle);
        r9[17] = new ThemeDescription(this.dropDown, 0, null, null, new Drawable[]{this.dropDownDrawable}, null, Theme.key_actionBarDefaultTitle);
        r9[18] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[19] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        r9[20] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        r9[21] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        r9[22] = new ThemeDescription(this.listView, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        r9[23] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r9[24] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText7);
        r9[25] = new ThemeDescription(this.listView, 0, new Class[]{TextSettingsCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r9[26] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r9[27] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        return r9;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.listView != null) {
            this.listView.getViewTreeObserver().addOnPreDrawListener(new C49929());
        }
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        updateRows();
        if (this.type == 0) {
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.didSetPasscode);
        }
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.type == 0) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didSetPasscode);
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        if (this.type != 0) {
            AndroidUtilities.runOnUIThread(new C49918(), 200);
        }
        fixLayoutInternal();
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z && this.type != 0) {
            AndroidUtilities.showKeyboard(this.passwordEditText);
        }
    }
}
