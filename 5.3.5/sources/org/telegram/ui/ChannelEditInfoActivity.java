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
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ExportedChatInvite;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_boolTrue;
import org.telegram.tgnet.TLRPC$TL_channels_checkUsername;
import org.telegram.tgnet.TLRPC$TL_channels_exportInvite;
import org.telegram.tgnet.TLRPC$TL_channels_getAdminedPublicChannels;
import org.telegram.tgnet.TLRPC$TL_channels_updateUsername;
import org.telegram.tgnet.TLRPC$TL_chatInviteExported;
import org.telegram.tgnet.TLRPC$TL_chatPhoto;
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
import org.telegram.ui.Cells.TextCheckCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ChannelEditInfoActivity extends BaseFragment implements AvatarUpdaterDelegate, NotificationCenterDelegate {
    private static final int done_button = 1;
    private ArrayList<AdminedChannelCell> adminedChannelCells = new ArrayList();
    private ShadowSectionCell adminedInfoCell;
    private LinearLayout adminnedChannelsLayout;
    private TLRPC$FileLocation avatar;
    private AvatarDrawable avatarDrawable = new AvatarDrawable();
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater = new AvatarUpdater();
    private boolean canCreatePublic = true;
    private int chatId;
    private int checkReqId;
    private Runnable checkRunnable;
    private TextView checkTextView;
    private FrameLayout container1;
    private FrameLayout container2;
    private FrameLayout container3;
    private FrameLayout container4;
    private boolean createAfterUpload;
    private TLRPC$Chat currentChat;
    private EditTextBoldCursor descriptionTextView;
    private View doneButton;
    private boolean donePressed;
    private EditText editText;
    private HeaderCell headerCell;
    private HeaderCell headerCell2;
    private boolean historyHidden;
    private TLRPC$ChatFull info;
    private TextInfoPrivacyCell infoCell;
    private TextInfoPrivacyCell infoCell2;
    private TextInfoPrivacyCell infoCell3;
    private TLRPC$ExportedChatInvite invite;
    private boolean isPrivate;
    private String lastCheckName;
    private boolean lastNameAvailable;
    private View lineView;
    private View lineView2;
    private View lineView3;
    private LinearLayout linearLayout;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayoutInviteContainer;
    private LinearLayout linearLayoutTypeContainer;
    private LinearLayout linkContainer;
    private LoadingCell loadingAdminedCell;
    private boolean loadingAdminedChannels;
    private boolean loadingInvite;
    private EditTextBoldCursor nameTextView;
    private TextBlockCell privateContainer;
    private AlertDialog progressDialog;
    private LinearLayout publicContainer;
    private RadioButtonCell radioButtonCell1;
    private RadioButtonCell radioButtonCell2;
    private RadioButtonCell radioButtonCell3;
    private RadioButtonCell radioButtonCell4;
    private ShadowSectionCell sectionCell;
    private ShadowSectionCell sectionCell2;
    private ShadowSectionCell sectionCell3;
    private boolean signMessages;
    private TextSettingsCell textCell;
    private TextSettingsCell textCell2;
    private TextCheckCell textCheckCell;
    private TextInfoPrivacyCell typeInfoCell;
    private TLRPC$InputFile uploadedAvatar;
    private EditTextBoldCursor usernameTextView;

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$2 */
    class C23282 implements RequestDelegate {
        C23282() {
        }

        public void run(TLObject response, final TLRPC$TL_error error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ChannelEditInfoActivity channelEditInfoActivity = ChannelEditInfoActivity.this;
                    boolean z = error == null || !error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH");
                    channelEditInfoActivity.canCreatePublic = z;
                    if (!ChannelEditInfoActivity.this.canCreatePublic) {
                        ChannelEditInfoActivity.this.loadAdminedChannels();
                    }
                }
            });
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$3 */
    class C23303 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$3$1 */
        class C23291 implements OnClickListener {
            C23291() {
            }

            public void onClick(DialogInterface dialog, int which) {
                ChannelEditInfoActivity.this.createAfterUpload = false;
                ChannelEditInfoActivity.this.progressDialog = null;
                ChannelEditInfoActivity.this.donePressed = false;
                try {
                    dialog.dismiss();
                } catch (Exception e) {
                    FileLog.e(e);
                }
            }
        }

        C23303() {
        }

        public void onItemClick(int id) {
            if (id == -1) {
                ChannelEditInfoActivity.this.finishFragment();
            } else if (id == 1 && !ChannelEditInfoActivity.this.donePressed) {
                Vibrator v;
                if (ChannelEditInfoActivity.this.nameTextView.length() == 0) {
                    v = (Vibrator) ChannelEditInfoActivity.this.getParentActivity().getSystemService("vibrator");
                    if (v != null) {
                        v.vibrate(200);
                    }
                    AndroidUtilities.shakeView(ChannelEditInfoActivity.this.nameTextView, 2.0f, 0);
                } else if (ChannelEditInfoActivity.this.usernameTextView == null || ChannelEditInfoActivity.this.isPrivate || (((ChannelEditInfoActivity.this.currentChat.username != null || ChannelEditInfoActivity.this.usernameTextView.length() == 0) && (ChannelEditInfoActivity.this.currentChat.username == null || ChannelEditInfoActivity.this.currentChat.username.equalsIgnoreCase(ChannelEditInfoActivity.this.usernameTextView.getText().toString()))) || ChannelEditInfoActivity.this.nameTextView.length() == 0 || ChannelEditInfoActivity.this.lastNameAvailable)) {
                    ChannelEditInfoActivity.this.donePressed = true;
                    if (ChannelEditInfoActivity.this.avatarUpdater.uploadingAvatar != null) {
                        ChannelEditInfoActivity.this.createAfterUpload = true;
                        ChannelEditInfoActivity.this.progressDialog = new AlertDialog(ChannelEditInfoActivity.this.getParentActivity(), 1);
                        ChannelEditInfoActivity.this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                        ChannelEditInfoActivity.this.progressDialog.setCanceledOnTouchOutside(false);
                        ChannelEditInfoActivity.this.progressDialog.setCancelable(false);
                        ChannelEditInfoActivity.this.progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C23291());
                        ChannelEditInfoActivity.this.progressDialog.show();
                        return;
                    }
                    if (ChannelEditInfoActivity.this.usernameTextView != null) {
                        String newUserName;
                        String oldUserName = ChannelEditInfoActivity.this.currentChat.username != null ? ChannelEditInfoActivity.this.currentChat.username : "";
                        if (ChannelEditInfoActivity.this.isPrivate) {
                            newUserName = "";
                        } else {
                            newUserName = ChannelEditInfoActivity.this.usernameTextView.getText().toString();
                        }
                        if (!oldUserName.equals(newUserName)) {
                            MessagesController.getInstance().updateChannelUserName(ChannelEditInfoActivity.this.chatId, newUserName);
                        }
                    }
                    if (!ChannelEditInfoActivity.this.currentChat.title.equals(ChannelEditInfoActivity.this.nameTextView.getText().toString())) {
                        MessagesController.getInstance().changeChatTitle(ChannelEditInfoActivity.this.chatId, ChannelEditInfoActivity.this.nameTextView.getText().toString());
                    }
                    if (!(ChannelEditInfoActivity.this.info == null || ChannelEditInfoActivity.this.info.about.equals(ChannelEditInfoActivity.this.descriptionTextView.getText().toString()))) {
                        MessagesController.getInstance().updateChannelAbout(ChannelEditInfoActivity.this.chatId, ChannelEditInfoActivity.this.descriptionTextView.getText().toString(), ChannelEditInfoActivity.this.info);
                    }
                    if (!(ChannelEditInfoActivity.this.headerCell2 == null || ChannelEditInfoActivity.this.headerCell2.getVisibility() != 0 || ChannelEditInfoActivity.this.info == null || !ChannelEditInfoActivity.this.currentChat.creator || ChannelEditInfoActivity.this.info.hidden_prehistory == ChannelEditInfoActivity.this.historyHidden)) {
                        ChannelEditInfoActivity.this.info.hidden_prehistory = ChannelEditInfoActivity.this.historyHidden;
                        MessagesController.getInstance().toogleChannelInvitesHistory(ChannelEditInfoActivity.this.chatId, ChannelEditInfoActivity.this.historyHidden);
                    }
                    if (ChannelEditInfoActivity.this.signMessages != ChannelEditInfoActivity.this.currentChat.signatures) {
                        ChannelEditInfoActivity.this.currentChat.signatures = true;
                        MessagesController.getInstance().toogleChannelSignatures(ChannelEditInfoActivity.this.chatId, ChannelEditInfoActivity.this.signMessages);
                    }
                    if (ChannelEditInfoActivity.this.uploadedAvatar != null) {
                        MessagesController.getInstance().changeChatAvatar(ChannelEditInfoActivity.this.chatId, ChannelEditInfoActivity.this.uploadedAvatar);
                    } else if (ChannelEditInfoActivity.this.avatar == null && (ChannelEditInfoActivity.this.currentChat.photo instanceof TLRPC$TL_chatPhoto)) {
                        MessagesController.getInstance().changeChatAvatar(ChannelEditInfoActivity.this.chatId, null);
                    }
                    ChannelEditInfoActivity.this.finishFragment();
                } else {
                    v = (Vibrator) ChannelEditInfoActivity.this.getParentActivity().getSystemService("vibrator");
                    if (v != null) {
                        v.vibrate(200);
                    }
                    AndroidUtilities.shakeView(ChannelEditInfoActivity.this.checkTextView, 2.0f, 0);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$4 */
    class C23324 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$4$1 */
        class C23311 implements OnClickListener {
            C23311() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    ChannelEditInfoActivity.this.avatarUpdater.openCamera();
                } else if (i == 1) {
                    ChannelEditInfoActivity.this.avatarUpdater.openGallery();
                } else if (i == 2) {
                    ChannelEditInfoActivity.this.avatar = null;
                    ChannelEditInfoActivity.this.uploadedAvatar = null;
                    ChannelEditInfoActivity.this.avatarImage.setImage(ChannelEditInfoActivity.this.avatar, "50_50", ChannelEditInfoActivity.this.avatarDrawable);
                }
            }
        }

        C23324() {
        }

        public void onClick(View view) {
            if (ChannelEditInfoActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                builder.setItems(ChannelEditInfoActivity.this.avatar != null ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)}, new C23311());
                ChannelEditInfoActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$5 */
    class C23335 implements TextWatcher {
        C23335() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            String obj;
            AvatarDrawable access$2000 = ChannelEditInfoActivity.this.avatarDrawable;
            if (ChannelEditInfoActivity.this.nameTextView.length() > 0) {
                obj = ChannelEditInfoActivity.this.nameTextView.getText().toString();
            } else {
                obj = null;
            }
            access$2000.setInfo(5, obj, null, false);
            ChannelEditInfoActivity.this.avatarImage.invalidate();
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$6 */
    class C23346 implements OnEditorActionListener {
        C23346() {
        }

        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 6 || ChannelEditInfoActivity.this.doneButton == null) {
                return false;
            }
            ChannelEditInfoActivity.this.doneButton.performClick();
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$7 */
    class C23357 implements TextWatcher {
        C23357() {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void afterTextChanged(Editable editable) {
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$8 */
    class C23368 implements View.OnClickListener {
        C23368() {
        }

        public void onClick(View v) {
            if (ChannelEditInfoActivity.this.isPrivate) {
                ChannelEditInfoActivity.this.isPrivate = false;
                ChannelEditInfoActivity.this.updatePrivatePublic();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$9 */
    class C23379 implements View.OnClickListener {
        C23379() {
        }

        public void onClick(View v) {
            if (!ChannelEditInfoActivity.this.isPrivate) {
                ChannelEditInfoActivity.this.isPrivate = true;
                ChannelEditInfoActivity.this.updatePrivatePublic();
            }
        }
    }

    public ChannelEditInfoActivity(Bundle args) {
        super(args);
        this.chatId = args.getInt("chat_id", 0);
    }

    public boolean onFragmentCreate() {
        boolean z = false;
        this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        if (this.currentChat == null) {
            final Semaphore semaphore = new Semaphore(0);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                public void run() {
                    ChannelEditInfoActivity.this.currentChat = MessagesStorage.getInstance().getChat(ChannelEditInfoActivity.this.chatId);
                    semaphore.release();
                }
            });
            try {
                semaphore.acquire();
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (this.currentChat == null) {
                return false;
            }
            MessagesController.getInstance().putChat(this.currentChat, true);
            if (this.info == null) {
                MessagesStorage.getInstance().loadChatInfo(this.chatId, semaphore, false, false);
                try {
                    semaphore.acquire();
                } catch (Exception e2) {
                    FileLog.e(e2);
                }
                if (this.info == null) {
                    return false;
                }
            }
        }
        if (this.currentChat.username == null || this.currentChat.username.length() == 0) {
            z = true;
        }
        this.isPrivate = z;
        if (this.isPrivate && this.currentChat.creator) {
            TLRPC$TL_channels_checkUsername req = new TLRPC$TL_channels_checkUsername();
            req.username = BuildConfig.VERSION_NAME;
            req.channel = new TLRPC$TL_inputChannelEmpty();
            ConnectionsManager.getInstance().sendRequest(req, new C23282());
        }
        this.avatarUpdater.parentFragment = this;
        this.avatarUpdater.delegate = this;
        this.signMessages = this.currentChat.signatures;
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
        return super.onFragmentCreate();
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        if (this.avatarUpdater != null) {
            this.avatarUpdater.clear();
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
        AndroidUtilities.removeAdjustResize(getParentActivity(), this.classGuid);
    }

    public void onResume() {
        super.onResume();
        AndroidUtilities.requestAdjustResize(getParentActivity(), this.classGuid);
        if (this.textCell2 != null && this.info != null) {
            if (this.info.stickerset != null) {
                this.textCell2.setTextAndValue(LocaleController.getString("GroupStickers", R.string.GroupStickers), this.info.stickerset.title, false);
            } else {
                this.textCell2.setText(LocaleController.getString("GroupStickers", R.string.GroupStickers), false);
            }
        }
    }

    public View createView(Context context) {
        float f;
        float f2;
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C23303());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new ScrollView(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        ScrollView scrollView = this.fragmentView;
        scrollView.setFillViewport(true);
        this.linearLayout = new LinearLayout(context);
        scrollView.addView(this.linearLayout, new LayoutParams(-1, -2));
        this.linearLayout.setOrientation(1);
        this.actionBar.setTitle(LocaleController.getString("ChannelEdit", R.string.ChannelEdit));
        this.linearLayout2 = new LinearLayout(context);
        this.linearLayout2.setOrientation(1);
        this.linearLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.linearLayout2, LayoutHelper.createLinear(-1, -2));
        FrameLayout frameLayout = new FrameLayout(context);
        this.linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(32.0f));
        this.avatarDrawable.setInfo(5, null, null, false);
        this.avatarDrawable.setDrawPhoto(true);
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
        this.avatarImage.setOnClickListener(new C23324());
        this.nameTextView = new EditTextBoldCursor(context);
        if (this.currentChat.megagroup) {
            this.nameTextView.setHint(LocaleController.getString("GroupName", R.string.GroupName));
        } else {
            this.nameTextView.setHint(LocaleController.getString("EnterChannelName", R.string.EnterChannelName));
        }
        this.nameTextView.setMaxLines(4);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.nameTextView.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.nameTextView.setImeOptions(268435456);
        this.nameTextView.setInputType(16385);
        this.nameTextView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.nameTextView.setEnabled(ChatObject.canChangeChatInfo(this.currentChat));
        this.nameTextView.setFocusable(this.nameTextView.isEnabled());
        this.nameTextView.setFilters(new InputFilter[]{new LengthFilter(100)});
        this.nameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setCursorWidth(1.5f);
        view = this.nameTextView;
        f = LocaleController.isRTL ? 16.0f : 96.0f;
        if (LocaleController.isRTL) {
            f2 = 96.0f;
        } else {
            f2 = 16.0f;
        }
        frameLayout.addView(view, LayoutHelper.createFrame(-1, -2.0f, 16, f, 0.0f, f2, 0.0f));
        this.nameTextView.addTextChangedListener(new C23335());
        this.lineView = new View(context);
        this.lineView.setBackgroundColor(Theme.getColor(Theme.key_divider));
        this.linearLayout.addView(this.lineView, new LinearLayout.LayoutParams(-1, 1));
        this.linearLayout3 = new LinearLayout(context);
        this.linearLayout3.setOrientation(1);
        this.linearLayout3.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.linearLayout3, LayoutHelper.createLinear(-1, -2));
        this.descriptionTextView = new EditTextBoldCursor(context);
        this.descriptionTextView.setTextSize(1, 16.0f);
        this.descriptionTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.descriptionTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.descriptionTextView.setPadding(0, 0, 0, AndroidUtilities.dp(6.0f));
        this.descriptionTextView.setBackgroundDrawable(null);
        this.descriptionTextView.setGravity(LocaleController.isRTL ? 5 : 3);
        this.descriptionTextView.setInputType(180225);
        this.descriptionTextView.setImeOptions(6);
        this.descriptionTextView.setEnabled(ChatObject.canChangeChatInfo(this.currentChat));
        this.descriptionTextView.setFocusable(this.descriptionTextView.isEnabled());
        this.descriptionTextView.setFilters(new InputFilter[]{new LengthFilter(255)});
        this.descriptionTextView.setHint(LocaleController.getString("DescriptionOptionalPlaceholder", R.string.DescriptionOptionalPlaceholder));
        this.descriptionTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.descriptionTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.descriptionTextView.setCursorWidth(1.5f);
        this.linearLayout3.addView(this.descriptionTextView, LayoutHelper.createLinear(-1, -2, 17.0f, 12.0f, 17.0f, 6.0f));
        this.descriptionTextView.setOnEditorActionListener(new C23346());
        this.descriptionTextView.addTextChangedListener(new C23357());
        this.sectionCell = new ShadowSectionCell(context);
        this.linearLayout.addView(this.sectionCell, LayoutHelper.createLinear(-1, -2));
        this.container1 = new FrameLayout(context);
        this.container1.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.container1, LayoutHelper.createLinear(-1, -2));
        if (this.currentChat.creator && (this.info == null || this.info.can_set_username)) {
            this.linearLayoutTypeContainer = new LinearLayout(context);
            this.linearLayoutTypeContainer.setOrientation(1);
            this.linearLayoutTypeContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.linearLayoutTypeContainer, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell1 = new RadioButtonCell(context);
            this.radioButtonCell1.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            if (this.currentChat.megagroup) {
                this.radioButtonCell1.setTextAndValue(LocaleController.getString("MegaPublic", R.string.MegaPublic), LocaleController.getString("MegaPublicInfo", R.string.MegaPublicInfo), !this.isPrivate);
            } else {
                this.radioButtonCell1.setTextAndValue(LocaleController.getString("ChannelPublic", R.string.ChannelPublic), LocaleController.getString("ChannelPublicInfo", R.string.ChannelPublicInfo), !this.isPrivate);
            }
            this.linearLayoutTypeContainer.addView(this.radioButtonCell1, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell1.setOnClickListener(new C23368());
            this.radioButtonCell2 = new RadioButtonCell(context);
            this.radioButtonCell2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            if (this.currentChat.megagroup) {
                this.radioButtonCell2.setTextAndValue(LocaleController.getString("MegaPrivate", R.string.MegaPrivate), LocaleController.getString("MegaPrivateInfo", R.string.MegaPrivateInfo), this.isPrivate);
            } else {
                this.radioButtonCell2.setTextAndValue(LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate), LocaleController.getString("ChannelPrivateInfo", R.string.ChannelPrivateInfo), this.isPrivate);
            }
            this.linearLayoutTypeContainer.addView(this.radioButtonCell2, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell2.setOnClickListener(new C23379());
            this.sectionCell2 = new ShadowSectionCell(context);
            this.linearLayout.addView(this.sectionCell2, LayoutHelper.createLinear(-1, -2));
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
            this.usernameTextView = new EditTextBoldCursor(context);
            this.usernameTextView.setTextSize(1, 18.0f);
            if (!this.isPrivate) {
                this.usernameTextView.setText(this.currentChat.username);
            }
            this.usernameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
            this.usernameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.usernameTextView.setMaxLines(1);
            this.usernameTextView.setLines(1);
            this.usernameTextView.setBackgroundDrawable(null);
            this.usernameTextView.setPadding(0, 0, 0, 0);
            this.usernameTextView.setSingleLine(true);
            this.usernameTextView.setInputType(163872);
            this.usernameTextView.setImeOptions(6);
            this.usernameTextView.setHint(LocaleController.getString("ChannelUsernamePlaceholder", R.string.ChannelUsernamePlaceholder));
            this.usernameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.usernameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
            this.usernameTextView.setCursorWidth(1.5f);
            this.publicContainer.addView(this.usernameTextView, LayoutHelper.createLinear(-1, 36));
            this.usernameTextView.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    ChannelEditInfoActivity.this.checkUserName(ChannelEditInfoActivity.this.usernameTextView.getText().toString());
                }

                public void afterTextChanged(Editable editable) {
                }
            });
            this.privateContainer = new TextBlockCell(context);
            this.privateContainer.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.linkContainer.addView(this.privateContainer);
            this.privateContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (ChannelEditInfoActivity.this.invite != null) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", ChannelEditInfoActivity.this.invite.link));
                            Toast.makeText(ChannelEditInfoActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
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
            this.adminedInfoCell = new ShadowSectionCell(context);
            this.linearLayout.addView(this.adminedInfoCell, LayoutHelper.createLinear(-1, -2));
            updatePrivatePublic();
        }
        if (this.currentChat.creator && this.currentChat.megagroup) {
            this.headerCell2 = new HeaderCell(context);
            this.headerCell2.setText(LocaleController.getString("ChatHistory", R.string.ChatHistory));
            this.headerCell2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.headerCell2);
            this.linearLayoutInviteContainer = new LinearLayout(context);
            this.linearLayoutInviteContainer.setOrientation(1);
            this.linearLayoutInviteContainer.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.linearLayoutInviteContainer, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell3 = new RadioButtonCell(context);
            this.radioButtonCell3.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.radioButtonCell3.setTextAndValue(LocaleController.getString("ChatHistoryVisible", R.string.ChatHistoryVisible), LocaleController.getString("ChatHistoryVisibleInfo", R.string.ChatHistoryVisibleInfo), !this.historyHidden);
            this.linearLayoutInviteContainer.addView(this.radioButtonCell3, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChannelEditInfoActivity.this.radioButtonCell3.setChecked(true, true);
                    ChannelEditInfoActivity.this.radioButtonCell4.setChecked(false, true);
                    ChannelEditInfoActivity.this.historyHidden = false;
                }
            });
            this.radioButtonCell4 = new RadioButtonCell(context);
            this.radioButtonCell4.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.radioButtonCell4.setTextAndValue(LocaleController.getString("ChatHistoryHidden", R.string.ChatHistoryHidden), LocaleController.getString("ChatHistoryHiddenInfo", R.string.ChatHistoryHiddenInfo), this.historyHidden);
            this.linearLayoutInviteContainer.addView(this.radioButtonCell4, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChannelEditInfoActivity.this.radioButtonCell3.setChecked(false, true);
                    ChannelEditInfoActivity.this.radioButtonCell4.setChecked(true, true);
                    ChannelEditInfoActivity.this.historyHidden = true;
                }
            });
            this.sectionCell3 = new ShadowSectionCell(context);
            this.linearLayout.addView(this.sectionCell3, LayoutHelper.createLinear(-1, -2));
            updatePrivatePublic();
        }
        this.lineView2 = new View(context);
        this.lineView2.setBackgroundColor(Theme.getColor(Theme.key_divider));
        this.linearLayout.addView(this.lineView2, new LinearLayout.LayoutParams(-1, 1));
        this.container2 = new FrameLayout(context);
        this.container2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.container3 = new FrameLayout(context);
        this.container3.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.container3, LayoutHelper.createLinear(-1, -2));
        this.lineView3 = new View(context);
        this.lineView3.setBackgroundColor(Theme.getColor(Theme.key_divider));
        this.linearLayout.addView(this.lineView3, new LinearLayout.LayoutParams(-1, 1));
        this.linearLayout.addView(this.container2, LayoutHelper.createLinear(-1, -2));
        if (!this.currentChat.megagroup) {
            this.textCheckCell = new TextCheckCell(context);
            this.textCheckCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.textCheckCell.setTextAndCheck(LocaleController.getString("ChannelSignMessages", R.string.ChannelSignMessages), this.signMessages, false);
            this.container2.addView(this.textCheckCell, LayoutHelper.createFrame(-1, -2.0f));
            this.textCheckCell.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ChannelEditInfoActivity.this.signMessages = !ChannelEditInfoActivity.this.signMessages;
                    ((TextCheckCell) v).setChecked(ChannelEditInfoActivity.this.signMessages);
                }
            });
            this.infoCell = new TextInfoPrivacyCell(context);
            this.infoCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
            this.infoCell.setText(LocaleController.getString("ChannelSignMessagesInfo", R.string.ChannelSignMessagesInfo));
            this.linearLayout.addView(this.infoCell, LayoutHelper.createLinear(-1, -2));
        } else if (this.info != null && this.info.can_set_stickers) {
            this.textCell2 = new TextSettingsCell(context);
            this.textCell2.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
            this.textCell2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            if (this.info.stickerset != null) {
                this.textCell2.setTextAndValue(LocaleController.getString("GroupStickers", R.string.GroupStickers), this.info.stickerset.title, false);
            } else {
                this.textCell2.setText(LocaleController.getString("GroupStickers", R.string.GroupStickers), false);
            }
            this.container3.addView(this.textCell2, LayoutHelper.createFrame(-1, -2.0f));
            this.textCell2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GroupStickersActivity groupStickersActivity = new GroupStickersActivity(ChannelEditInfoActivity.this.currentChat.id);
                    groupStickersActivity.setInfo(ChannelEditInfoActivity.this.info);
                    ChannelEditInfoActivity.this.presentFragment(groupStickersActivity);
                }
            });
            this.infoCell3 = new TextInfoPrivacyCell(context);
            this.infoCell3.setText(LocaleController.getString("GroupStickersInfo", R.string.GroupStickersInfo));
            this.linearLayout.addView(this.infoCell3, LayoutHelper.createLinear(-1, -2));
        }
        if (this.currentChat.creator) {
            this.container3 = new FrameLayout(context);
            this.container3.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            this.linearLayout.addView(this.container3, LayoutHelper.createLinear(-1, -2));
            this.textCell = new TextSettingsCell(context);
            this.textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText5));
            this.textCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            if (this.currentChat.megagroup) {
                this.textCell.setText(LocaleController.getString("DeleteMega", R.string.DeleteMega), false);
            } else {
                this.textCell.setText(LocaleController.getString("ChannelDelete", R.string.ChannelDelete), false);
            }
            this.container3.addView(this.textCell, LayoutHelper.createFrame(-1, -2.0f));
            this.textCell.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ChannelEditInfoActivity$16$1 */
                class C23171 implements OnClickListener {
                    C23171() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
                        if (AndroidUtilities.isTablet()) {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(-((long) ChannelEditInfoActivity.this.chatId))});
                        } else {
                            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        }
                        MessagesController.getInstance().deleteUserFromChat(ChannelEditInfoActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), ChannelEditInfoActivity.this.info, true);
                        ChannelEditInfoActivity.this.finishFragment();
                    }
                }

                public void onClick(View v) {
                    Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                    if (ChannelEditInfoActivity.this.currentChat.megagroup) {
                        builder.setMessage(LocaleController.getString("MegaDeleteAlert", R.string.MegaDeleteAlert));
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelDeleteAlert", R.string.ChannelDeleteAlert));
                    }
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C23171());
                    builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                    ChannelEditInfoActivity.this.showDialog(builder.create());
                }
            });
            this.infoCell2 = new TextInfoPrivacyCell(context);
            this.infoCell2.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            if (this.currentChat.megagroup) {
                this.infoCell2.setText(LocaleController.getString("MegaDeleteInfo", R.string.MegaDeleteInfo));
            } else {
                this.infoCell2.setText(LocaleController.getString("ChannelDeleteInfo", R.string.ChannelDeleteInfo));
            }
            this.linearLayout.addView(this.infoCell2, LayoutHelper.createLinear(-1, -2));
        } else {
            if (!this.currentChat.megagroup) {
                this.infoCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            } else if (this.infoCell3 == null) {
                this.sectionCell.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            }
            this.lineView3.setVisibility(8);
            this.lineView2.setVisibility(8);
        }
        if (this.infoCell3 != null) {
            if (this.infoCell2 == null) {
                this.infoCell3.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            } else {
                this.infoCell3.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
            }
        }
        this.nameTextView.setText(this.currentChat.title);
        this.nameTextView.setSelection(this.nameTextView.length());
        if (this.info != null) {
            this.descriptionTextView.setText(this.info.about);
        }
        if (this.currentChat.photo != null) {
            this.avatar = this.currentChat.photo.photo_small;
            this.avatarImage.setImage(this.avatar, "50_50", this.avatarDrawable);
        } else {
            this.avatarImage.setImageDrawable(this.avatarDrawable);
        }
        return this.fragmentView;
    }

    public void didReceivedNotification(int id, Object... args) {
        if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null) {
                    this.descriptionTextView.setText(chatFull.about);
                    this.historyHidden = chatFull.hidden_prehistory;
                    if (this.radioButtonCell3 != null) {
                        boolean z;
                        RadioButtonCell radioButtonCell = this.radioButtonCell3;
                        if (this.historyHidden) {
                            z = false;
                        } else {
                            z = true;
                        }
                        radioButtonCell.setChecked(z, false);
                        this.radioButtonCell4.setChecked(this.historyHidden, false);
                    }
                }
                this.info = chatFull;
                this.invite = chatFull.exported_invite;
                updatePrivatePublic();
            }
        }
    }

    public void didUploadedPhoto(final TLRPC$InputFile file, final TLRPC$PhotoSize small, TLRPC$PhotoSize big) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ChannelEditInfoActivity.this.uploadedAvatar = file;
                ChannelEditInfoActivity.this.avatar = small.location;
                ChannelEditInfoActivity.this.avatarImage.setImage(ChannelEditInfoActivity.this.avatar, "50_50", ChannelEditInfoActivity.this.avatarDrawable);
                if (ChannelEditInfoActivity.this.createAfterUpload) {
                    try {
                        if (ChannelEditInfoActivity.this.progressDialog != null && ChannelEditInfoActivity.this.progressDialog.isShowing()) {
                            ChannelEditInfoActivity.this.progressDialog.dismiss();
                            ChannelEditInfoActivity.this.progressDialog = null;
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                    ChannelEditInfoActivity.this.donePressed = false;
                    ChannelEditInfoActivity.this.doneButton.performClick();
                }
            }
        });
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        this.avatarUpdater.onActivityResult(requestCode, resultCode, data);
    }

    public void saveSelfArgs(Bundle args) {
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

    public void restoreSelfArgs(Bundle args) {
        if (this.avatarUpdater != null) {
            this.avatarUpdater.currentPicturePath = args.getString("path");
        }
    }

    public void setInfo(TLRPC$ChatFull chatFull) {
        if (this.info == null && chatFull != null) {
            this.historyHidden = chatFull.hidden_prehistory;
        }
        this.info = chatFull;
        if (chatFull == null) {
            return;
        }
        if (chatFull.exported_invite instanceof TLRPC$TL_chatInviteExported) {
            this.invite = chatFull.exported_invite;
        } else {
            generateLink();
        }
    }

    private void loadAdminedChannels() {
        if (!this.loadingAdminedChannels && this.adminnedChannelsLayout != null) {
            this.loadingAdminedChannels = true;
            updatePrivatePublic();
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_channels_getAdminedPublicChannels(), new RequestDelegate() {
                public void run(final TLObject response, TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1 */
                        class C23211 implements View.OnClickListener {
                            C23211() {
                            }

                            public void onClick(View view) {
                                final TLRPC$Chat channel = ((AdminedChannelCell) view.getParent()).getCurrentChannel();
                                Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                if (channel.megagroup) {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlert", R.string.RevokeLinkAlert, new Object[]{MessagesController.getInstance().linkPrefix + "/" + channel.username, channel.title})));
                                } else {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlertChannel", R.string.RevokeLinkAlertChannel, new Object[]{MessagesController.getInstance().linkPrefix + "/" + channel.username, channel.title})));
                                }
                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString("RevokeButton", R.string.RevokeButton), new OnClickListener() {

                                    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1$1$1 */
                                    class C23191 implements RequestDelegate {

                                        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1$1$1$1 */
                                        class C23181 implements Runnable {
                                            C23181() {
                                            }

                                            public void run() {
                                                ChannelEditInfoActivity.this.canCreatePublic = true;
                                                if (ChannelEditInfoActivity.this.nameTextView.length() > 0) {
                                                    ChannelEditInfoActivity.this.checkUserName(ChannelEditInfoActivity.this.nameTextView.getText().toString());
                                                }
                                                ChannelEditInfoActivity.this.updatePrivatePublic();
                                            }
                                        }

                                        C23191() {
                                        }

                                        public void run(TLObject response, TLRPC$TL_error error) {
                                            if (response instanceof TLRPC$TL_boolTrue) {
                                                AndroidUtilities.runOnUIThread(new C23181());
                                            }
                                        }
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TLRPC$TL_channels_updateUsername req = new TLRPC$TL_channels_updateUsername();
                                        req.channel = MessagesController.getInputChannel(channel);
                                        req.username = "";
                                        ConnectionsManager.getInstance().sendRequest(req, new C23191(), 64);
                                    }
                                });
                                ChannelEditInfoActivity.this.showDialog(builder.create());
                            }
                        }

                        public void run() {
                            ChannelEditInfoActivity.this.loadingAdminedChannels = false;
                            if (response != null && ChannelEditInfoActivity.this.getParentActivity() != null) {
                                int a;
                                for (a = 0; a < ChannelEditInfoActivity.this.adminedChannelCells.size(); a++) {
                                    ChannelEditInfoActivity.this.linearLayout.removeView((View) ChannelEditInfoActivity.this.adminedChannelCells.get(a));
                                }
                                ChannelEditInfoActivity.this.adminedChannelCells.clear();
                                TLRPC$TL_messages_chats res = response;
                                a = 0;
                                while (a < res.chats.size()) {
                                    AdminedChannelCell adminedChannelCell = new AdminedChannelCell(ChannelEditInfoActivity.this.getParentActivity(), new C23211());
                                    adminedChannelCell.setChannel((TLRPC$Chat) res.chats.get(a), a == res.chats.size() + -1);
                                    ChannelEditInfoActivity.this.adminedChannelCells.add(adminedChannelCell);
                                    ChannelEditInfoActivity.this.adminnedChannelsLayout.addView(adminedChannelCell, LayoutHelper.createLinear(-1, 72));
                                    a++;
                                }
                                ChannelEditInfoActivity.this.updatePrivatePublic();
                            }
                        }
                    });
                }
            });
        }
    }

    private void updatePrivatePublic() {
        int i = 8;
        boolean z = false;
        if (this.sectionCell2 != null) {
            if (this.isPrivate || this.canCreatePublic) {
                int i2;
                this.typeInfoCell.setTag(Theme.key_windowBackgroundWhiteGrayText4);
                this.typeInfoCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText4));
                this.sectionCell2.setVisibility(0);
                this.adminedInfoCell.setVisibility(8);
                this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                this.adminnedChannelsLayout.setVisibility(8);
                this.linkContainer.setVisibility(0);
                this.loadingAdminedCell.setVisibility(8);
                if (this.currentChat.megagroup) {
                    this.typeInfoCell.setText(this.isPrivate ? LocaleController.getString("MegaPrivateLinkHelp", R.string.MegaPrivateLinkHelp) : LocaleController.getString("MegaUsernameHelp", R.string.MegaUsernameHelp));
                    this.headerCell.setText(this.isPrivate ? LocaleController.getString("ChannelInviteLinkTitle", R.string.ChannelInviteLinkTitle) : LocaleController.getString("ChannelLinkTitle", R.string.ChannelLinkTitle));
                } else {
                    this.typeInfoCell.setText(this.isPrivate ? LocaleController.getString("ChannelPrivateLinkHelp", R.string.ChannelPrivateLinkHelp) : LocaleController.getString("ChannelUsernameHelp", R.string.ChannelUsernameHelp));
                    this.headerCell.setText(this.isPrivate ? LocaleController.getString("ChannelInviteLinkTitle", R.string.ChannelInviteLinkTitle) : LocaleController.getString("ChannelLinkTitle", R.string.ChannelLinkTitle));
                }
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
                if (this.isPrivate || this.checkTextView.length() == 0) {
                    i2 = 8;
                } else {
                    i2 = 0;
                }
                textView.setVisibility(i2);
                if (this.headerCell2 != null) {
                    HeaderCell headerCell = this.headerCell2;
                    if (this.isPrivate) {
                        i2 = 0;
                    } else {
                        i2 = 8;
                    }
                    headerCell.setVisibility(i2);
                    linearLayout = this.linearLayoutInviteContainer;
                    if (this.isPrivate) {
                        i2 = 0;
                    } else {
                        i2 = 8;
                    }
                    linearLayout.setVisibility(i2);
                    ShadowSectionCell shadowSectionCell = this.sectionCell3;
                    if (this.isPrivate) {
                        i = 0;
                    }
                    shadowSectionCell.setVisibility(i);
                }
            } else {
                this.typeInfoCell.setText(LocaleController.getString("ChangePublicLimitReached", R.string.ChangePublicLimitReached));
                this.typeInfoCell.setTag(Theme.key_windowBackgroundWhiteRedText4);
                this.typeInfoCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                this.linkContainer.setVisibility(8);
                this.sectionCell2.setVisibility(8);
                this.adminedInfoCell.setVisibility(0);
                if (this.loadingAdminedChannels) {
                    this.loadingAdminedCell.setVisibility(0);
                    this.adminnedChannelsLayout.setVisibility(8);
                    this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    this.adminedInfoCell.setBackgroundDrawable(null);
                } else {
                    this.adminedInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.adminedInfoCell.getContext(), R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
                    this.typeInfoCell.setBackgroundDrawable(Theme.getThemedDrawable(this.typeInfoCell.getContext(), R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    this.loadingAdminedCell.setVisibility(8);
                    this.adminnedChannelsLayout.setVisibility(0);
                }
                if (this.headerCell2 != null) {
                    this.headerCell2.setVisibility(8);
                    this.linearLayoutInviteContainer.setVisibility(8);
                    this.sectionCell3.setVisibility(8);
                }
            }
            RadioButtonCell radioButtonCell = this.radioButtonCell1;
            if (!this.isPrivate) {
                z = true;
            }
            radioButtonCell.setChecked(z, true);
            this.radioButtonCell2.setChecked(this.isPrivate, true);
            this.usernameTextView.clearFocus();
            AndroidUtilities.hideKeyboard(this.nameTextView);
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
                if (a != 0 || ch < '0' || ch > '9') {
                    if ((ch < '0' || ch > '9') && ((ch < 'a' || ch > 'z') && ((ch < 'A' || ch > 'Z') && ch != '_'))) {
                        this.checkTextView.setText(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                        this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                        this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                        return false;
                    }
                    a++;
                } else if (this.currentChat.megagroup) {
                    this.checkTextView.setText(LocaleController.getString("LinkInvalidStartNumberMega", R.string.LinkInvalidStartNumberMega));
                    this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                    this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                    return false;
                } else {
                    this.checkTextView.setText(LocaleController.getString("LinkInvalidStartNumber", R.string.LinkInvalidStartNumber));
                    this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                    this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                    return false;
                }
            }
        }
        if (name == null || name.length() < 5) {
            if (this.currentChat.megagroup) {
                this.checkTextView.setText(LocaleController.getString("LinkInvalidShortMega", R.string.LinkInvalidShortMega));
                this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                return false;
            }
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

                /* renamed from: org.telegram.ui.ChannelEditInfoActivity$19$1 */
                class C23241 implements RequestDelegate {
                    C23241() {
                    }

                    public void run(final TLObject response, final TLRPC$TL_error error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ChannelEditInfoActivity.this.checkReqId = 0;
                                if (ChannelEditInfoActivity.this.lastCheckName != null && ChannelEditInfoActivity.this.lastCheckName.equals(name)) {
                                    if (error == null && (response instanceof TLRPC$TL_boolTrue)) {
                                        ChannelEditInfoActivity.this.checkTextView.setText(LocaleController.formatString("LinkAvailable", R.string.LinkAvailable, new Object[]{name}));
                                        ChannelEditInfoActivity.this.checkTextView.setTag(Theme.key_windowBackgroundWhiteGreenText);
                                        ChannelEditInfoActivity.this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText));
                                        ChannelEditInfoActivity.this.lastNameAvailable = true;
                                        return;
                                    }
                                    if (error == null || !error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH")) {
                                        ChannelEditInfoActivity.this.checkTextView.setText(LocaleController.getString("LinkInUse", R.string.LinkInUse));
                                    } else {
                                        ChannelEditInfoActivity.this.canCreatePublic = false;
                                        ChannelEditInfoActivity.this.loadAdminedChannels();
                                    }
                                    ChannelEditInfoActivity.this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                                    ChannelEditInfoActivity.this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                                    ChannelEditInfoActivity.this.lastNameAvailable = false;
                                }
                            }
                        });
                    }
                }

                public void run() {
                    TLRPC$TL_channels_checkUsername req = new TLRPC$TL_channels_checkUsername();
                    req.username = name;
                    req.channel = MessagesController.getInputChannel(ChannelEditInfoActivity.this.chatId);
                    ChannelEditInfoActivity.this.checkReqId = ConnectionsManager.getInstance().sendRequest(req, new C23241(), 2);
                }
            };
            AndroidUtilities.runOnUIThread(this.checkRunnable, 300);
            return true;
        }
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
                                ChannelEditInfoActivity.this.invite = (TLRPC$ExportedChatInvite) response;
                            }
                            ChannelEditInfoActivity.this.loadingInvite = false;
                            if (ChannelEditInfoActivity.this.privateContainer != null) {
                                ChannelEditInfoActivity.this.privateContainer.setText(ChannelEditInfoActivity.this.invite != null ? ChannelEditInfoActivity.this.invite.link : LocaleController.getString("Loading", R.string.Loading), false);
                            }
                        }
                    });
                }
            });
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                if (ChannelEditInfoActivity.this.avatarImage != null) {
                    String obj;
                    AvatarDrawable access$2000 = ChannelEditInfoActivity.this.avatarDrawable;
                    if (ChannelEditInfoActivity.this.nameTextView.length() > 0) {
                        obj = ChannelEditInfoActivity.this.nameTextView.getText().toString();
                    } else {
                        obj = null;
                    }
                    access$2000.setInfo(5, obj, null, false);
                    ChannelEditInfoActivity.this.avatarImage.invalidate();
                }
            }
        };
        ThemeDescriptionDelegate сellDelegate2 = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                if (ChannelEditInfoActivity.this.adminnedChannelsLayout != null) {
                    int count = ChannelEditInfoActivity.this.adminnedChannelsLayout.getChildCount();
                    for (int a = 0; a < count; a++) {
                        View child = ChannelEditInfoActivity.this.adminnedChannelsLayout.getChildAt(a);
                        if (child instanceof AdminedChannelCell) {
                            ((AdminedChannelCell) child).update();
                        }
                    }
                }
            }
        };
        r11 = new ThemeDescription[93];
        r11[17] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, сellDelegate, Theme.key_avatar_text);
        r11[18] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r11[19] = new ThemeDescription(this.lineView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r11[20] = new ThemeDescription(this.lineView2, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r11[21] = new ThemeDescription(this.lineView3, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r11[22] = new ThemeDescription(this.sectionCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[23] = new ThemeDescription(this.sectionCell2, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[24] = new ThemeDescription(this.sectionCell3, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[25] = new ThemeDescription(this.textCheckCell, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[26] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[27] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        r11[28] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        r11[29] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        r11[30] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        r11[31] = new ThemeDescription(this.infoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[32] = new ThemeDescription(this.infoCell, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r11[33] = new ThemeDescription(this.textCell, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[34] = new ThemeDescription(this.textCell, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText5);
        r11[35] = new ThemeDescription(this.textCell2, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[36] = new ThemeDescription(this.textCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[37] = new ThemeDescription(this.infoCell2, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[38] = new ThemeDescription(this.infoCell2, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r11[39] = new ThemeDescription(this.infoCell3, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[40] = new ThemeDescription(this.infoCell3, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r11[41] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[42] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r11[43] = new ThemeDescription(this.linearLayoutTypeContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r11[44] = new ThemeDescription(this.linkContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r11[45] = new ThemeDescription(this.headerCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r11[46] = new ThemeDescription(this.headerCell2, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r11[47] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[48] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r11[49] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r11[50] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText8);
        r11[51] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGreenText);
        r11[52] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[53] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r11[54] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r11[55] = new ThemeDescription(this.adminedInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r11[56] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r11[57] = new ThemeDescription(this.privateContainer, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[58] = new ThemeDescription(this.privateContainer, 0, new Class[]{TextBlockCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[59] = new ThemeDescription(this.loadingAdminedCell, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r11[60] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[61] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r11[62] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r11[63] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[64] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r11[65] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[66] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r11[67] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r11[68] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[69] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r11[70] = new ThemeDescription(this.linearLayoutInviteContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r11[71] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[72] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r11[73] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r11[74] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[75] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r11[76] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r11[77] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r11[78] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r11[79] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[80] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r11[81] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r11[82] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r11[83] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_LINKCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        r11[84] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"deleteButton"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r11[85] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, сellDelegate2, Theme.key_avatar_text);
        r11[86] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundRed);
        r11[87] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundOrange);
        r11[88] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundViolet);
        r11[89] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundGreen);
        r11[90] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundCyan);
        r11[91] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundBlue);
        r11[92] = new ThemeDescription(null, 0, null, null, null, сellDelegate2, Theme.key_avatar_backgroundPink);
        return r11;
    }
}
