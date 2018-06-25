package org.telegram.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.thin.downloadmanager.BuildConfig;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.ArrayList;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ExportedChatInvite;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_channels_checkUsername;
import org.telegram.tgnet.TLRPC$TL_channels_exportInvite;
import org.telegram.tgnet.TLRPC$TL_channels_getAdminedPublicChannels;
import org.telegram.tgnet.TLRPC$TL_channels_updateUsername;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputChannelEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_chats;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.AdminedChannelCell;
import org.telegram.ui.Cells.HeaderCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.RadioButtonCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextBlockCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ChannelCreateActivity extends BaseFragment implements NotificationCenterDelegate, AvatarUpdaterDelegate {
    private static final int done_button = 1;
    private ArrayList<AdminedChannelCell> adminedChannelCells = new ArrayList();
    private TextInfoPrivacyCell adminedInfoCell;
    private LinearLayout adminnedChannelsLayout;
    private TLRPC$FileLocation avatar;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater;
    private boolean canCreatePublic = true;
    private int chatId;
    private int checkReqId;
    private Runnable checkRunnable;
    private TextView checkTextView;
    private boolean createAfterUpload;
    private int currentStep;
    private EditTextBoldCursor descriptionTextView;
    private View doneButton;
    private boolean donePressed;
    private EditText editText;
    private HeaderCell headerCell;
    private TextView helpTextView;
    private TLRPC$ExportedChatInvite invite;
    private boolean isPrivate;
    private String lastCheckName;
    private boolean lastNameAvailable;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private LinearLayout linkContainer;
    private LoadingCell loadingAdminedCell;
    private boolean loadingAdminedChannels;
    private boolean loadingInvite;
    private EditTextBoldCursor nameTextView;
    private String nameToSet;
    private TextBlockCell privateContainer;
    private AlertDialog progressDialog;
    private LinearLayout publicContainer;
    private RadioButtonCell radioButtonCell1;
    private RadioButtonCell radioButtonCell2;
    private ShadowSectionCell sectionCell;
    private TextInfoPrivacyCell typeInfoCell;
    private TLRPC$InputFile uploadedAvatar;

    /* renamed from: org.telegram.ui.ChannelCreateActivity$1 */
    class C22891 implements RequestDelegate {
        C22891() {
        }

        public void run(TLObject response, final TLRPC$TL_error error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ChannelCreateActivity channelCreateActivity = ChannelCreateActivity.this;
                    boolean z = error == null || !error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH");
                    channelCreateActivity.canCreatePublic = z;
                }
            });
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$2 */
    class C22922 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ChannelCreateActivity$2$1 */
        class C22901 implements OnClickListener {
            C22901() {
            }

            public void onClick(DialogInterface dialog, int which) {
                ChannelCreateActivity.this.createAfterUpload = false;
                ChannelCreateActivity.this.progressDialog = null;
                ChannelCreateActivity.this.donePressed = false;
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }

        C22922() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ChannelCreateActivity.this.finishFragment();
            } else if (id != 1) {
            } else {
                Vibrator v;
                if (ChannelCreateActivity.this.currentStep == 0) {
                    if (!ChannelCreateActivity.this.donePressed) {
                        if (ChannelCreateActivity.this.nameTextView.length() == 0) {
                            v = (Vibrator) ChannelCreateActivity.this.getParentActivity().getSystemService("vibrator");
                            if (v != null) {
                                v.vibrate(200);
                            }
                            AndroidUtilities.shakeView(ChannelCreateActivity.this.nameTextView, 2.0f, 0);
                            return;
                        }
                        ChannelCreateActivity.this.donePressed = true;
                        if (ChannelCreateActivity.this.avatarUpdater.uploadingAvatar != null) {
                            ChannelCreateActivity.this.createAfterUpload = true;
                            ChannelCreateActivity.this.progressDialog = new AlertDialog(ChannelCreateActivity.this.getParentActivity(), 1);
                            ChannelCreateActivity.this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                            ChannelCreateActivity.this.progressDialog.setCanceledOnTouchOutside(false);
                            ChannelCreateActivity.this.progressDialog.setCancelable(false);
                            ChannelCreateActivity.this.progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C22901());
                            ChannelCreateActivity.this.progressDialog.show();
                            return;
                        }
                        final int reqId = MessagesController.getInstance().createChat(ChannelCreateActivity.this.nameTextView.getText().toString(), new ArrayList(), ChannelCreateActivity.this.descriptionTextView.getText().toString(), 2, ChannelCreateActivity.this);
                        ChannelCreateActivity.this.progressDialog = new AlertDialog(ChannelCreateActivity.this.getParentActivity(), 1);
                        ChannelCreateActivity.this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                        ChannelCreateActivity.this.progressDialog.setCanceledOnTouchOutside(false);
                        ChannelCreateActivity.this.progressDialog.setCancelable(false);
                        ChannelCreateActivity.this.progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ConnectionsManager.getInstance().cancelRequest(reqId, true);
                                ChannelCreateActivity.this.donePressed = false;
                                try {
                                    dialog.dismiss();
                                } catch (Exception e) {
                                    FileLog.e(e);
                                }
                            }
                        });
                        ChannelCreateActivity.this.progressDialog.show();
                    }
                } else if (ChannelCreateActivity.this.currentStep == 1) {
                    if (!ChannelCreateActivity.this.isPrivate) {
                        if (ChannelCreateActivity.this.nameTextView.length() == 0) {
                            Builder builder = new Builder(ChannelCreateActivity.this.getParentActivity());
                            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                            builder.setMessage(LocaleController.getString("ChannelPublicEmptyUsername", R.string.ChannelPublicEmptyUsername));
                            builder.setPositiveButton(LocaleController.getString("Close", R.string.Close), null);
                            ChannelCreateActivity.this.showDialog(builder.create());
                            return;
                        } else if (ChannelCreateActivity.this.lastNameAvailable) {
                            MessagesController.getInstance().updateChannelUserName(ChannelCreateActivity.this.chatId, ChannelCreateActivity.this.lastCheckName);
                        } else {
                            v = (Vibrator) ChannelCreateActivity.this.getParentActivity().getSystemService("vibrator");
                            if (v != null) {
                                v.vibrate(200);
                            }
                            AndroidUtilities.shakeView(ChannelCreateActivity.this.checkTextView, 2.0f, 0);
                            return;
                        }
                    }
                    Bundle args = new Bundle();
                    args.putInt("step", 2);
                    args.putInt("chatId", ChannelCreateActivity.this.chatId);
                    args.putInt("chatType", 2);
                    ChannelCreateActivity.this.presentFragment(new GroupCreateActivity(args), true);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$3 */
    class C22943 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.ChannelCreateActivity$3$1 */
        class C22931 implements OnClickListener {
            C22931() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    ChannelCreateActivity.this.avatarUpdater.openCamera();
                } else if (i == 1) {
                    ChannelCreateActivity.this.avatarUpdater.openGallery();
                } else if (i == 2) {
                    ChannelCreateActivity.this.avatar = null;
                    ChannelCreateActivity.this.uploadedAvatar = null;
                    ChannelCreateActivity.this.avatarImage.setImage(ChannelCreateActivity.this.avatar, "50_50", ChannelCreateActivity.this.avatarDrawable);
                }
            }
        }

        C22943() {
        }

        public void onClick(View view) {
            if (ChannelCreateActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(ChannelCreateActivity.this.getParentActivity());
                builder.setItems(ChannelCreateActivity.this.avatar != null ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)}, new C22931());
                ChannelCreateActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$4 */
    class C22954 implements TextWatcher {
        C22954() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            String obj;
            AvatarDrawable access$1500 = ChannelCreateActivity.this.avatarDrawable;
            if (ChannelCreateActivity.this.nameTextView.length() > 0) {
                obj = ChannelCreateActivity.this.nameTextView.getText().toString();
            } else {
                obj = null;
            }
            access$1500.setInfo(5, obj, null, false);
            ChannelCreateActivity.this.avatarImage.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$5 */
    class C22965 implements OnEditorActionListener {
        C22965() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 6 || ChannelCreateActivity.this.doneButton == null) {
                return false;
            }
            ChannelCreateActivity.this.doneButton.performClick();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$6 */
    class C22976 implements TextWatcher {
        C22976() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$7 */
    class C22987 implements View.OnClickListener {
        C22987() {
        }

        public void onClick(View v) {
            if (ChannelCreateActivity.this.isPrivate) {
                ChannelCreateActivity.this.isPrivate = false;
                ChannelCreateActivity.this.updatePrivatePublic();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$8 */
    class C22998 implements View.OnClickListener {
        C22998() {
        }

        public void onClick(View v) {
            if (!ChannelCreateActivity.this.isPrivate) {
                ChannelCreateActivity.this.isPrivate = true;
                ChannelCreateActivity.this.updatePrivatePublic();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelCreateActivity$9 */
    class C23009 implements TextWatcher {
        C23009() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            ChannelCreateActivity.this.checkUserName(ChannelCreateActivity.this.nameTextView.getText().toString());
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    public ChannelCreateActivity(Bundle args) {
        boolean z = true;
        super(args);
        this.currentStep = args.getInt("step", 0);
        if (this.currentStep == 0) {
            this.avatarDrawable = new AvatarDrawable();
            this.avatarUpdater = new AvatarUpdater();
            TLRPC$TL_channels_checkUsername req = new TLRPC$TL_channels_checkUsername();
            req.username = BuildConfig.VERSION_NAME;
            req.channel = new TLRPC$TL_inputChannelEmpty();
            ConnectionsManager.getInstance().sendRequest(req, new C22891());
            return;
        }
        if (this.currentStep == 1) {
            this.canCreatePublic = args.getBoolean("canCreatePublic", true);
            if (this.canCreatePublic) {
                z = false;
            }
            this.isPrivate = z;
            if (!this.canCreatePublic) {
                loadAdminedChannels();
            }
        }
        this.chatId = args.getInt("chat_id", 0);
    }

    public boolean onFragmentCreate() {
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatDidFailCreate);
        if (this.currentStep == 1) {
            generateLink();
        }
        if (this.avatarUpdater != null) {
            this.avatarUpdater.parentFragment = this;
            this.avatarUpdater.delegate = this;
        }
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatDidCreated);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatDidFailCreate);
        if (this.avatarUpdater != null) {
            this.avatarUpdater.clear();
        }
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C22922());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new ScrollView(context);
        ScrollView scrollView = this.fragmentView;
        scrollView.setFillViewport(true);
        this.linearLayout = new LinearLayout(context);
        this.linearLayout.setOrientation(1);
        scrollView.addView(this.linearLayout, new LayoutParams(-1, -2));
        if (this.currentStep == 0) {
            float f;
            float f2;
            this.actionBar.setTitle(LocaleController.getString("NewChannel", R.string.NewChannel));
            this.fragmentView.setTag(Theme.key_windowBackgroundWhite);
            this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            FrameLayout frameLayout = new FrameLayout(context);
            this.linearLayout.addView(frameLayout, LayoutHelper.createLinear(-1, -2));
            this.avatarImage = new BackupImageView(context);
            this.avatarImage.setRoundRadius(AndroidUtilities.dp(32.0f));
            this.avatarDrawable.setInfo(5, null, null, false);
            this.avatarDrawable.setDrawPhoto(true);
            this.avatarImage.setImageDrawable(this.avatarDrawable);
            View view = this.avatarImage;
            int i = (LocaleController.isRTL ? 5 : 3) | 48;
            if (LocaleController.isRTL) {
                f = 0.0f;
            } else {
                f = 16.0f;
            }
            if (LocaleController.isRTL) {
                f2 = 16.0f;
            } else {
                f2 = 0.0f;
            }
            frameLayout.addView(view, LayoutHelper.createFrame(64, 64.0f, i, f, 12.0f, f2, 12.0f));
            this.avatarImage.setOnClickListener(new C22943());
            this.nameTextView = new EditTextBoldCursor(context);
            this.nameTextView.setHint(LocaleController.getString("EnterChannelName", R.string.EnterChannelName));
            if (this.nameToSet != null) {
                this.nameTextView.setText(this.nameToSet);
                this.nameToSet = null;
            }
            this.nameTextView.setMaxLines(4);
            this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
            this.nameTextView.setTextSize(1, 16.0f);
            this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.nameTextView.setImeOptions(268435456);
            this.nameTextView.setInputType(16385);
            this.nameTextView.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.nameTextView.setFilters(new InputFilter[]{new LengthFilter(100)});
            this.nameTextView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
            this.nameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
            this.nameTextView.setCursorWidth(1.5f);
            view = this.nameTextView;
            f = LocaleController.isRTL ? 16.0f : 96.0f;
            if (LocaleController.isRTL) {
                f2 = 96.0f;
            } else {
                f2 = 16.0f;
            }
            frameLayout.addView(view, LayoutHelper.createFrame(-1, -2.0f, 16, f, 0.0f, f2, 0.0f));
            this.nameTextView.addTextChangedListener(new C22954());
            this.descriptionTextView = new EditTextBoldCursor(context);
            this.descriptionTextView.setTextSize(1, 18.0f);
            this.descriptionTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.descriptionTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.descriptionTextView.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
            this.descriptionTextView.setPadding(0, 0, 0, AndroidUtilities.dp(6.0f));
            this.descriptionTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.descriptionTextView.setInputType(180225);
            this.descriptionTextView.setImeOptions(6);
            this.descriptionTextView.setFilters(new InputFilter[]{new LengthFilter(120)});
            this.descriptionTextView.setHint(LocaleController.getString("DescriptionPlaceholder", R.string.DescriptionPlaceholder));
            this.descriptionTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.descriptionTextView.setCursorSize(AndroidUtilities.dp(20.0f));
            this.descriptionTextView.setCursorWidth(1.5f);
            this.linearLayout.addView(this.descriptionTextView, LayoutHelper.createLinear(-1, -2, 24.0f, 18.0f, 24.0f, 0.0f));
            this.descriptionTextView.setOnEditorActionListener(new C22965());
            this.descriptionTextView.addTextChangedListener(new C22976());
            this.helpTextView = new TextView(context);
            this.helpTextView.setTextSize(1, 15.0f);
            this.helpTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText8));
            this.helpTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.helpTextView.setText(LocaleController.getString("DescriptionInfo", R.string.DescriptionInfo));
            LinearLayout linearLayout = this.linearLayout;
            View view2 = this.helpTextView;
            if (LocaleController.isRTL) {
                i = 5;
            } else {
                i = 3;
            }
            linearLayout.addView(view2, LayoutHelper.createLinear(-2, -2, i, 24, 10, 24, 20));
        } else if (this.currentStep == 1) {
            this.actionBar.setTitle(LocaleController.getString("ChannelSettings", R.string.ChannelSettings));
            this.fragmentView.setTag(Theme.key_windowBackgroundGray);
            this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
            this.linearLayout2 = new LinearLayout(context);
            this.linearLayout2.setOrientation(1);
            this.linearLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.linearLayout2, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell1 = new RadioButtonCell(context);
            this.radioButtonCell1.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.radioButtonCell1.setTextAndValue(LocaleController.getString("ChannelPublic", R.string.ChannelPublic), LocaleController.getString("ChannelPublicInfo", R.string.ChannelPublicInfo), !this.isPrivate);
            this.linearLayout2.addView(this.radioButtonCell1, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell1.setOnClickListener(new C22987());
            this.radioButtonCell2 = new RadioButtonCell(context);
            this.radioButtonCell2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.radioButtonCell2.setTextAndValue(LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate), LocaleController.getString("ChannelPrivateInfo", R.string.ChannelPrivateInfo), this.isPrivate);
            this.linearLayout2.addView(this.radioButtonCell2, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell2.setOnClickListener(new C22998());
            this.sectionCell = new ShadowSectionCell(context);
            this.linearLayout.addView(this.sectionCell, LayoutHelper.createLinear(-1, -2));
            this.linkContainer = new LinearLayout(context);
            this.linkContainer.setOrientation(1);
            this.linkContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.linkContainer, LayoutHelper.createLinear(-1, -2));
            this.headerCell = new HeaderCell(context);
            this.linkContainer.addView(this.headerCell);
            this.publicContainer = new LinearLayout(context);
            this.publicContainer.setOrientation(0);
            this.linkContainer.addView(this.publicContainer, LayoutHelper.createLinear(-1, 36, 17.0f, 7.0f, 17.0f, 0.0f));
            this.editText = new EditText(context);
            this.editText.setText(MessagesController.getInstance().linkPrefix + "/");
            this.editText.setTextSize(1, 18.0f);
            this.editText.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.editText.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.editText.setMaxLines(1);
            this.editText.setLines(1);
            this.editText.setEnabled(false);
            this.editText.setBackgroundDrawable(null);
            this.editText.setPadding(0, 0, 0, 0);
            this.editText.setSingleLine(true);
            this.editText.setInputType(163840);
            this.editText.setImeOptions(6);
            this.publicContainer.addView(this.editText, LayoutHelper.createLinear(-2, 36));
            this.nameTextView = new EditTextBoldCursor(context);
            this.nameTextView.setTextSize(1, 18.0f);
            this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.nameTextView.setMaxLines(1);
            this.nameTextView.setLines(1);
            this.nameTextView.setBackgroundDrawable(null);
            this.nameTextView.setPadding(0, 0, 0, 0);
            this.nameTextView.setSingleLine(true);
            this.nameTextView.setInputType(163872);
            this.nameTextView.setImeOptions(6);
            this.nameTextView.setHint(LocaleController.getString("ChannelUsernamePlaceholder", R.string.ChannelUsernamePlaceholder));
            this.nameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
            this.nameTextView.setCursorWidth(1.5f);
            this.publicContainer.addView(this.nameTextView, LayoutHelper.createLinear(-1, 36));
            this.nameTextView.addTextChangedListener(new C23009());
            this.privateContainer = new TextBlockCell(context);
            this.privateContainer.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.linkContainer.addView(this.privateContainer);
            this.privateContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ChannelCreateActivity.this.invite != null) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", ChannelCreateActivity.this.invite.link));
                            Toast.makeText(ChannelCreateActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            this.checkTextView = new TextView(context);
            this.checkTextView.setTextSize(1, 15.0f);
            this.checkTextView.setGravity(LocaleController.isRTL ? 5 : 3);
            this.checkTextView.setVisibility(8);
            this.linkContainer.addView(this.checkTextView, LayoutHelper.createLinear(-2, -2, LocaleController.isRTL ? 5 : 3, 17, 3, 17, 7));
            this.typeInfoCell = new TextInfoPrivacyCell(context);
            this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            this.linearLayout.addView(this.typeInfoCell, LayoutHelper.createLinear(-1, -2));
            this.loadingAdminedCell = new LoadingCell(context);
            this.linearLayout.addView(this.loadingAdminedCell, LayoutHelper.createLinear(-1, -2));
            this.adminnedChannelsLayout = new LinearLayout(context);
            this.adminnedChannelsLayout.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.adminnedChannelsLayout.setOrientation(1);
            this.linearLayout.addView(this.adminnedChannelsLayout, LayoutHelper.createLinear(-1, -2));
            this.adminedInfoCell = new TextInfoPrivacyCell(context);
            this.adminedInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            this.linearLayout.addView(this.adminedInfoCell, LayoutHelper.createLinear(-1, -2));
            updatePrivatePublic();
        }
        return this.fragmentView;
    }

    private void generateLink() {
        if (!this.loadingInvite && this.invite == null) {
            this.loadingInvite = true;
            TLRPC$TL_channels_exportInvite req = new TLRPC$TL_channels_exportInvite();
            req.channel = MessagesController.getInputChannel(this.chatId);
            ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (error == null) {
                                ChannelCreateActivity.this.invite = (TLRPC$ExportedChatInvite) response;
                            }
                            ChannelCreateActivity.this.loadingInvite = false;
                            ChannelCreateActivity.this.privateContainer.setText(ChannelCreateActivity.this.invite != null ? ChannelCreateActivity.this.invite.link : LocaleController.getString("Loading", R.string.Loading), false);
                        }
                    });
                }
            });
        }
    }

    private void updatePrivatePublic() {
        int i = 8;
        boolean z = false;
        if (this.sectionCell != null) {
            if (this.isPrivate || this.canCreatePublic) {
                int i2;
                this.typeInfoCell.setTag(Theme.key_windowBackgroundWhiteGrayText4);
                this.typeInfoCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
                this.sectionCell.setVisibility(0);
                this.adminedInfoCell.setVisibility(8);
                this.adminnedChannelsLayout.setVisibility(8);
                this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                this.linkContainer.setVisibility(0);
                this.loadingAdminedCell.setVisibility(8);
                this.typeInfoCell.setText(this.isPrivate ? LocaleController.getString("ChannelPrivateLinkHelp", R.string.ChannelPrivateLinkHelp) : LocaleController.getString("ChannelUsernameHelp", R.string.ChannelUsernameHelp));
                this.headerCell.setText(this.isPrivate ? LocaleController.getString("ChannelInviteLinkTitle", R.string.ChannelInviteLinkTitle) : LocaleController.getString("ChannelLinkTitle", R.string.ChannelLinkTitle));
                LinearLayout linearLayout = this.publicContainer;
                if (this.isPrivate) {
                    i2 = 8;
                } else {
                    i2 = 0;
                }
                linearLayout.setVisibility(i2);
                TextBlockCell textBlockCell = this.privateContainer;
                if (this.isPrivate) {
                    i2 = 0;
                } else {
                    i2 = 8;
                }
                textBlockCell.setVisibility(i2);
                this.linkContainer.setPadding(0, 0, 0, this.isPrivate ? 0 : AndroidUtilities.dp(7.0f));
                this.privateContainer.setText(this.invite != null ? this.invite.link : LocaleController.getString("Loading", R.string.Loading), false);
                TextView textView = this.checkTextView;
                if (!(this.isPrivate || this.checkTextView.length() == 0)) {
                    i = 0;
                }
                textView.setVisibility(i);
            } else {
                this.typeInfoCell.setText(LocaleController.getString("ChangePublicLimitReached", R.string.ChangePublicLimitReached));
                this.typeInfoCell.setTag(Theme.key_windowBackgroundWhiteRedText4);
                this.typeInfoCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                this.linkContainer.setVisibility(8);
                this.sectionCell.setVisibility(8);
                if (this.loadingAdminedChannels) {
                    this.loadingAdminedCell.setVisibility(0);
                    this.adminnedChannelsLayout.setVisibility(8);
                    this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    this.adminedInfoCell.setVisibility(8);
                } else {
                    this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    this.loadingAdminedCell.setVisibility(8);
                    this.adminnedChannelsLayout.setVisibility(0);
                    this.adminedInfoCell.setVisibility(0);
                }
            }
            RadioButtonCell radioButtonCell = this.radioButtonCell1;
            if (!this.isPrivate) {
                z = true;
            }
            radioButtonCell.setChecked(z, true);
            this.radioButtonCell2.setChecked(this.isPrivate, true);
            this.nameTextView.clearFocus();
            AndroidUtilities.hideKeyboard(this.nameTextView);
        }
    }

    public void didUploadedPhoto(final TLRPC$InputFile file, final TLRPC$PhotoSize small, TLRPC$PhotoSize big) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ChannelCreateActivity.this.uploadedAvatar = file;
                ChannelCreateActivity.this.avatar = small.location;
                ChannelCreateActivity.this.avatarImage.setImage(ChannelCreateActivity.this.avatar, "50_50", ChannelCreateActivity.this.avatarDrawable);
                if (ChannelCreateActivity.this.createAfterUpload) {
                    try {
                        if (ChannelCreateActivity.this.progressDialog != null && ChannelCreateActivity.this.progressDialog.isShowing()) {
                            ChannelCreateActivity.this.progressDialog.dismiss();
                            ChannelCreateActivity.this.progressDialog = null;
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    ChannelCreateActivity.this.doneButton.performClick();
                }
            }
        });
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        this.avatarUpdater.onActivityResult(requestCode, resultCode, data);
    }

    public void saveSelfArgs(Bundle args) {
        if (this.currentStep == 0) {
            if (!(this.avatarUpdater == null || this.avatarUpdater.currentPicturePath == null)) {
                args.putString("path", this.avatarUpdater.currentPicturePath);
            }
            if (this.nameTextView != null) {
                String text = this.nameTextView.getText().toString();
                if (text != null && text.length() != 0) {
                    args.putString("nameTextView", text);
                }
            }
        }
    }

    public void restoreSelfArgs(Bundle args) {
        if (this.currentStep == 0) {
            if (this.avatarUpdater != null) {
                this.avatarUpdater.currentPicturePath = args.getString("path");
            }
            String text = args.getString("nameTextView");
            if (text == null) {
                return;
            }
            if (this.nameTextView != null) {
                this.nameTextView.setText(text);
            } else {
                this.nameToSet = text;
            }
        }
    }

    public void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (isOpen && this.currentStep != 1) {
            this.nameTextView.requestFocus();
            AndroidUtilities.showKeyboard(this.nameTextView);
        }
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.chatDidFailCreate) {
            if (this.progressDialog != null) {
                try {
                    this.progressDialog.dismiss();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
            this.donePressed = false;
        } else if (id == NotificationCenter.chatDidCreated) {
            if (this.progressDialog != null) {
                try {
                    this.progressDialog.dismiss();
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
            }
            int chat_id = ((Integer) args[0]).intValue();
            Bundle bundle = new Bundle();
            bundle.putInt("step", 1);
            bundle.putInt("chat_id", chat_id);
            bundle.putBoolean("canCreatePublic", this.canCreatePublic);
            if (this.uploadedAvatar != null) {
                MessagesController.getInstance().changeChatAvatar(chat_id, this.uploadedAvatar);
            }
            presentFragment(new ChannelCreateActivity(bundle), true);
        }
    }

    private void loadAdminedChannels() {
        if (!this.loadingAdminedChannels) {
            this.loadingAdminedChannels = true;
            updatePrivatePublic();
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_channels_getAdminedPublicChannels(), new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.ChannelCreateActivity$13$1$1 */
                        class C22851 implements View.OnClickListener {
                            C22851() {
                            }

                            public void onClick(View view) {
                                final TLRPC$Chat channel = ((AdminedChannelCell) view.getParent()).getCurrentChannel();
                                Builder builder = new Builder(ChannelCreateActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                if (channel.megagroup) {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlert", R.string.RevokeLinkAlert, new Object[]{MessagesController.getInstance().linkPrefix + "/" + channel.username, channel.title})));
                                } else {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlertChannel", R.string.RevokeLinkAlertChannel, new Object[]{MessagesController.getInstance().linkPrefix + "/" + channel.username, channel.title})));
                                }
                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString("RevokeButton", R.string.RevokeButton), new OnClickListener() {

                                    /* renamed from: org.telegram.ui.ChannelCreateActivity$13$1$1$1$1 */
                                    class C22831 implements RequestDelegate {

                                        /* renamed from: org.telegram.ui.ChannelCreateActivity$13$1$1$1$1$1 */
                                        class C22821 implements Runnable {
                                            C22821() {
                                            }

                                            public void run() {
                                                ChannelCreateActivity.this.canCreatePublic = true;
                                                if (ChannelCreateActivity.this.nameTextView.length() > 0) {
                                                    ChannelCreateActivity.this.checkUserName(ChannelCreateActivity.this.nameTextView.getText().toString());
                                                }
                                                ChannelCreateActivity.this.updatePrivatePublic();
                                            }
                                        }

                                        C22831() {
                                        }

                                        public void run(TLObject response, TLRPC$TL_error error) {
                                            if (response instanceof TLRPC$TL_boolTrue) {
                                                AndroidUtilities.runOnUIThread(new C22821());
                                            }
                                        }
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TLRPC$TL_channels_updateUsername req = new TLRPC$TL_channels_updateUsername();
                                        req.channel = MessagesController.getInputChannel(channel);
                                        req.username = "";
                                        ConnectionsManager.getInstance().sendRequest(req, new C22831(), 64);
                                    }
                                });
                                ChannelCreateActivity.this.showDialog(builder.create());
                            }
                        }

                        public void run() {
                            ChannelCreateActivity.this.loadingAdminedChannels = false;
                            if (response != null && ChannelCreateActivity.this.getParentActivity() != null) {
                                int a;
                                for (a = 0; a < ChannelCreateActivity.this.adminedChannelCells.size(); a++) {
                                    ChannelCreateActivity.this.linearLayout.removeView((View) ChannelCreateActivity.this.adminedChannelCells.get(a));
                                }
                                ChannelCreateActivity.this.adminedChannelCells.clear();
                                TLRPC$TL_messages_chats res = response;
                                a = 0;
                                while (a < res.chats.size()) {
                                    AdminedChannelCell adminedChannelCell = new AdminedChannelCell(ChannelCreateActivity.this.getParentActivity(), new C22851());
                                    adminedChannelCell.setChannel((TLRPC$Chat) res.chats.get(a), a == res.chats.size() + -1);
                                    ChannelCreateActivity.this.adminedChannelCells.add(adminedChannelCell);
                                    ChannelCreateActivity.this.adminnedChannelsLayout.addView(adminedChannelCell, LayoutHelper.createLinear(-1, 72));
                                    a++;
                                }
                                ChannelCreateActivity.this.updatePrivatePublic();
                            }
                        }
                    });
                }
            });
        }
    }

    private boolean checkUserName(final String name) {
        if (name == null || name.length() <= 0) {
            this.checkTextView.setVisibility(8);
        } else {
            this.checkTextView.setVisibility(0);
        }
        if (this.checkRunnable != null) {
            AndroidUtilities.cancelRunOnUIThread(this.checkRunnable);
            this.checkRunnable = null;
            this.lastCheckName = null;
            if (this.checkReqId != 0) {
                ConnectionsManager.getInstance().cancelRequest(this.checkReqId, true);
            }
        }
        this.lastNameAvailable = false;
        if (name != null) {
            if (name.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) || name.endsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
                this.checkTextView.setText(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                return false;
            }
            int a = 0;
            while (a < name.length()) {
                char ch = name.charAt(a);
                if (a == 0 && ch >= '0' && ch <= '9') {
                    this.checkTextView.setText(LocaleController.getString("LinkInvalidStartNumber", R.string.LinkInvalidStartNumber));
                    this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                    this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                    return false;
                } else if ((ch < '0' || ch > '9') && ((ch < 'a' || ch > 'z') && ((ch < 'A' || ch > 'Z') && ch != '_'))) {
                    this.checkTextView.setText(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                    this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                    this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                    return false;
                } else {
                    a++;
                }
            }
        }
        if (name == null || name.length() < 5) {
            this.checkTextView.setText(LocaleController.getString("LinkInvalidShort", R.string.LinkInvalidShort));
            this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
            this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
            return false;
        } else if (name.length() > 32) {
            this.checkTextView.setText(LocaleController.getString("LinkInvalidLong", R.string.LinkInvalidLong));
            this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
            this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
            return false;
        } else {
            this.checkTextView.setText(LocaleController.getString("LinkChecking", R.string.LinkChecking));
            this.checkTextView.setTag(Theme.key_windowBackgroundWhiteGrayText8);
            this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText8));
            this.lastCheckName = name;
            this.checkRunnable = new Runnable() {

                /* renamed from: org.telegram.ui.ChannelCreateActivity$14$1 */
                class C22881 implements RequestDelegate {
                    C22881() {
                    }

                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ChannelCreateActivity.this.checkReqId = 0;
                                if (ChannelCreateActivity.this.lastCheckName != null && ChannelCreateActivity.this.lastCheckName.equals(name)) {
                                    if (error == null && (response instanceof TLRPC$TL_boolTrue)) {
                                        ChannelCreateActivity.this.checkTextView.setText(LocaleController.formatString("LinkAvailable", R.string.LinkAvailable, new Object[]{name}));
                                        ChannelCreateActivity.this.checkTextView.setTag(Theme.key_windowBackgroundWhiteGreenText);
                                        ChannelCreateActivity.this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText));
                                        ChannelCreateActivity.this.lastNameAvailable = true;
                                        return;
                                    }
                                    if (error == null || !error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH")) {
                                        ChannelCreateActivity.this.checkTextView.setText(LocaleController.getString("LinkInUse", R.string.LinkInUse));
                                    } else {
                                        ChannelCreateActivity.this.canCreatePublic = false;
                                        ChannelCreateActivity.this.loadAdminedChannels();
                                    }
                                    ChannelCreateActivity.this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                                    ChannelCreateActivity.this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                                    ChannelCreateActivity.this.lastNameAvailable = false;
                                }
                            }
                        });
                    }
                }

                public void run() {
                    TLRPC$TL_channels_checkUsername req = new TLRPC$TL_channels_checkUsername();
                    req.username = name;
                    req.channel = MessagesController.getInputChannel(ChannelCreateActivity.this.chatId);
                    ChannelCreateActivity.this.checkReqId = ConnectionsManager.getInstance().sendRequest(req, new C22881(), 2);
                }
            };
            AndroidUtilities.runOnUIThread(this.checkRunnable, 300);
            return true;
        }
    }

    private void showErrorAlert(String error) {
        if (getParentActivity() != null) {
            Builder builder = new Builder(getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            Object obj = -1;
            switch (error.hashCode()) {
                case 288843630:
                    if (error.equals("USERNAME_INVALID")) {
                        obj = null;
                        break;
                    }
                    break;
                case 533175271:
                    if (error.equals("USERNAME_OCCUPIED")) {
                        obj = 1;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    builder.setMessage(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                    break;
                case 1:
                    builder.setMessage(LocaleController.getString("LinkInUse", R.string.LinkInUse));
                    break;
                default:
                    builder.setMessage(LocaleController.getString("ErrorOccurred", R.string.ErrorOccurred));
                    break;
            }
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            showDialog(builder.create());
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                if (ChannelCreateActivity.this.adminnedChannelsLayout != null) {
                    int count = ChannelCreateActivity.this.adminnedChannelsLayout.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View child = ChannelCreateActivity.this.adminnedChannelsLayout.getChildAt(a);
                        if (child instanceof AdminedChannelCell) {
                            ((AdminedChannelCell) child).update();
                        }
                    }
                }
                if (ChannelCreateActivity.this.avatarImage != null) {
                    String obj;
                    AvatarDrawable access$1500 = ChannelCreateActivity.this.avatarDrawable;
                    if (ChannelCreateActivity.this.nameTextView.length() > 0) {
                        obj = ChannelCreateActivity.this.nameTextView.getText().toString();
                    } else {
                        obj = null;
                    }
                    access$1500.setInfo(5, obj, null, false);
                    ChannelCreateActivity.this.avatarImage.invalidate();
                }
            }
        };
        r10 = new ThemeDescription[54];
        r10[18] = new ThemeDescription(this.headerCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r10[19] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[20] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r10[21] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r10[22] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText8);
        r10[23] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGreenText);
        r10[24] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[25] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[26] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r10[27] = new ThemeDescription(this.adminedInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[28] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r10[29] = new ThemeDescription(this.privateContainer, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[30] = new ThemeDescription(this.privateContainer, 0, new Class[]{TextBlockCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[31] = new ThemeDescription(this.loadingAdminedCell, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[32] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[33] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[34] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[35] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[36] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[37] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[38] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[39] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[40] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[41] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[42] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[43] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r10[44] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_LINKCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        r10[45] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"deleteButton"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r10[46] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, сellDelegate, Theme.key_avatar_text);
        r10[47] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        r10[48] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        r10[49] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        r10[50] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        r10[51] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        r10[52] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r10[53] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        return r10;
    }
}
