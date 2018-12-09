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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_inputChannelEmpty;
import org.telegram.tgnet.TLRPC$TL_messages_chats;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ExportedChatInvite;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_boolTrue;
import org.telegram.tgnet.TLRPC.TL_channels_checkUsername;
import org.telegram.tgnet.TLRPC.TL_channels_exportInvite;
import org.telegram.tgnet.TLRPC.TL_channels_getAdminedPublicChannels;
import org.telegram.tgnet.TLRPC.TL_channels_updateUsername;
import org.telegram.tgnet.TLRPC.TL_chatInviteExported;
import org.telegram.tgnet.TLRPC.TL_chatPhoto;
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

public class ChannelEditInfoActivity extends BaseFragment implements NotificationCenterDelegate, AvatarUpdaterDelegate {
    private static final int done_button = 1;
    private ArrayList<AdminedChannelCell> adminedChannelCells = new ArrayList();
    private ShadowSectionCell adminedInfoCell;
    private LinearLayout adminnedChannelsLayout;
    private FileLocation avatar;
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
    private Chat currentChat;
    private EditTextBoldCursor descriptionTextView;
    private View doneButton;
    private boolean donePressed;
    private EditText editText;
    private HeaderCell headerCell;
    private HeaderCell headerCell2;
    private boolean historyHidden;
    private ChatFull info;
    private TextInfoPrivacyCell infoCell;
    private TextInfoPrivacyCell infoCell2;
    private TextInfoPrivacyCell infoCell3;
    private ExportedChatInvite invite;
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
    private InputFile uploadedAvatar;
    private EditTextBoldCursor usernameTextView;

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$2 */
    class C41662 implements RequestDelegate {
        C41662() {
        }

        public void run(TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
            AndroidUtilities.runOnUIThread(new Runnable() {
                public void run() {
                    ChannelEditInfoActivity channelEditInfoActivity = ChannelEditInfoActivity.this;
                    boolean z = tLRPC$TL_error == null || !tLRPC$TL_error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH");
                    channelEditInfoActivity.canCreatePublic = z;
                    if (!ChannelEditInfoActivity.this.canCreatePublic) {
                        ChannelEditInfoActivity.this.loadAdminedChannels();
                    }
                }
            });
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$3 */
    class C41683 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$3$1 */
        class C41671 implements OnClickListener {
            C41671() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                ChannelEditInfoActivity.this.createAfterUpload = false;
                ChannelEditInfoActivity.this.progressDialog = null;
                ChannelEditInfoActivity.this.donePressed = false;
                try {
                    dialogInterface.dismiss();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }

        C41683() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChannelEditInfoActivity.this.finishFragment();
            } else if (i == 1 && !ChannelEditInfoActivity.this.donePressed) {
                Vibrator vibrator;
                if (ChannelEditInfoActivity.this.nameTextView.length() == 0) {
                    vibrator = (Vibrator) ChannelEditInfoActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
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
                        ChannelEditInfoActivity.this.progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C41671());
                        ChannelEditInfoActivity.this.progressDialog.show();
                        return;
                    }
                    if (ChannelEditInfoActivity.this.usernameTextView != null) {
                        String str = ChannelEditInfoActivity.this.currentChat.username != null ? ChannelEditInfoActivity.this.currentChat.username : TtmlNode.ANONYMOUS_REGION_ID;
                        String obj = ChannelEditInfoActivity.this.isPrivate ? TtmlNode.ANONYMOUS_REGION_ID : ChannelEditInfoActivity.this.usernameTextView.getText().toString();
                        if (!str.equals(obj)) {
                            MessagesController.getInstance().updateChannelUserName(ChannelEditInfoActivity.this.chatId, obj);
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
                    } else if (ChannelEditInfoActivity.this.avatar == null && (ChannelEditInfoActivity.this.currentChat.photo instanceof TL_chatPhoto)) {
                        MessagesController.getInstance().changeChatAvatar(ChannelEditInfoActivity.this.chatId, null);
                    }
                    ChannelEditInfoActivity.this.finishFragment();
                } else {
                    vibrator = (Vibrator) ChannelEditInfoActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(ChannelEditInfoActivity.this.checkTextView, 2.0f, 0);
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$4 */
    class C41704 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$4$1 */
        class C41691 implements OnClickListener {
            C41691() {
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

        C41704() {
        }

        public void onClick(View view) {
            if (ChannelEditInfoActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                builder.setItems(ChannelEditInfoActivity.this.avatar != null ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)}, new C41691());
                ChannelEditInfoActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$5 */
    class C41715 implements TextWatcher {
        C41715() {
        }

        public void afterTextChanged(Editable editable) {
            ChannelEditInfoActivity.this.avatarDrawable.setInfo(5, ChannelEditInfoActivity.this.nameTextView.length() > 0 ? ChannelEditInfoActivity.this.nameTextView.getText().toString() : null, null, false);
            ChannelEditInfoActivity.this.avatarImage.invalidate();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$6 */
    class C41726 implements OnEditorActionListener {
        C41726() {
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
    class C41737 implements TextWatcher {
        C41737() {
        }

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$8 */
    class C41748 implements View.OnClickListener {
        C41748() {
        }

        public void onClick(View view) {
            if (ChannelEditInfoActivity.this.isPrivate) {
                ChannelEditInfoActivity.this.isPrivate = false;
                ChannelEditInfoActivity.this.updatePrivatePublic();
            }
        }
    }

    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$9 */
    class C41759 implements View.OnClickListener {
        C41759() {
        }

        public void onClick(View view) {
            if (!ChannelEditInfoActivity.this.isPrivate) {
                ChannelEditInfoActivity.this.isPrivate = true;
                ChannelEditInfoActivity.this.updatePrivatePublic();
            }
        }
    }

    public ChannelEditInfoActivity(Bundle bundle) {
        super(bundle);
        this.chatId = bundle.getInt("chat_id", 0);
    }

    private boolean checkUserName(final String str) {
        if (str == null || str.length() <= 0) {
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
        if (str != null) {
            if (str.startsWith("_") || str.endsWith("_")) {
                this.checkTextView.setText(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                return false;
            }
            int i = 0;
            while (i < str.length()) {
                char charAt = str.charAt(i);
                if (i != 0 || charAt < '0' || charAt > '9') {
                    if ((charAt < '0' || charAt > '9') && ((charAt < 'a' || charAt > 'z') && ((charAt < 'A' || charAt > 'Z') && charAt != '_'))) {
                        this.checkTextView.setText(LocaleController.getString("LinkInvalid", R.string.LinkInvalid));
                        this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
                        this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
                        return false;
                    }
                    i++;
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
        if (str == null || str.length() < 5) {
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
        } else if (str.length() > 32) {
            this.checkTextView.setText(LocaleController.getString("LinkInvalidLong", R.string.LinkInvalidLong));
            this.checkTextView.setTag(Theme.key_windowBackgroundWhiteRedText4);
            this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText4));
            return false;
        } else {
            this.checkTextView.setText(LocaleController.getString("LinkChecking", R.string.LinkChecking));
            this.checkTextView.setTag(Theme.key_windowBackgroundWhiteGrayText8);
            this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGrayText8));
            this.lastCheckName = str;
            this.checkRunnable = new Runnable() {

                /* renamed from: org.telegram.ui.ChannelEditInfoActivity$19$1 */
                class C41621 implements RequestDelegate {
                    C41621() {
                    }

                    public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                        AndroidUtilities.runOnUIThread(new Runnable() {
                            public void run() {
                                ChannelEditInfoActivity.this.checkReqId = 0;
                                if (ChannelEditInfoActivity.this.lastCheckName != null && ChannelEditInfoActivity.this.lastCheckName.equals(str)) {
                                    if (tLRPC$TL_error == null && (tLObject instanceof TL_boolTrue)) {
                                        ChannelEditInfoActivity.this.checkTextView.setText(LocaleController.formatString("LinkAvailable", R.string.LinkAvailable, new Object[]{str}));
                                        ChannelEditInfoActivity.this.checkTextView.setTag(Theme.key_windowBackgroundWhiteGreenText);
                                        ChannelEditInfoActivity.this.checkTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText));
                                        ChannelEditInfoActivity.this.lastNameAvailable = true;
                                        return;
                                    }
                                    if (tLRPC$TL_error == null || !tLRPC$TL_error.text.equals("CHANNELS_ADMIN_PUBLIC_TOO_MUCH")) {
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
                    TLObject tL_channels_checkUsername = new TL_channels_checkUsername();
                    tL_channels_checkUsername.username = str;
                    tL_channels_checkUsername.channel = MessagesController.getInputChannel(ChannelEditInfoActivity.this.chatId);
                    ChannelEditInfoActivity.this.checkReqId = ConnectionsManager.getInstance().sendRequest(tL_channels_checkUsername, new C41621(), 2);
                }
            };
            AndroidUtilities.runOnUIThread(this.checkRunnable, 300);
            return true;
        }
    }

    private void generateLink() {
        if (!this.loadingInvite && this.invite == null) {
            this.loadingInvite = true;
            TLObject tL_channels_exportInvite = new TL_channels_exportInvite();
            tL_channels_exportInvite.channel = MessagesController.getInputChannel(this.chatId);
            ConnectionsManager.getInstance().sendRequest(tL_channels_exportInvite, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                ChannelEditInfoActivity.this.invite = (ExportedChatInvite) tLObject;
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

    private void loadAdminedChannels() {
        if (!this.loadingAdminedChannels && this.adminnedChannelsLayout != null) {
            this.loadingAdminedChannels = true;
            updatePrivatePublic();
            ConnectionsManager.getInstance().sendRequest(new TL_channels_getAdminedPublicChannels(), new RequestDelegate() {
                public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {

                        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1 */
                        class C41591 implements View.OnClickListener {
                            C41591() {
                            }

                            public void onClick(View view) {
                                final Chat currentChannel = ((AdminedChannelCell) view.getParent()).getCurrentChannel();
                                Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                                builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                                if (currentChannel.megagroup) {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlert", R.string.RevokeLinkAlert, new Object[]{MessagesController.getInstance().linkPrefix + "/" + currentChannel.username, currentChannel.title})));
                                } else {
                                    builder.setMessage(AndroidUtilities.replaceTags(LocaleController.formatString("RevokeLinkAlertChannel", R.string.RevokeLinkAlertChannel, new Object[]{MessagesController.getInstance().linkPrefix + "/" + currentChannel.username, currentChannel.title})));
                                }
                                builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                                builder.setPositiveButton(LocaleController.getString("RevokeButton", R.string.RevokeButton), new OnClickListener() {

                                    /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1$1$1 */
                                    class C41571 implements RequestDelegate {

                                        /* renamed from: org.telegram.ui.ChannelEditInfoActivity$18$1$1$1$1$1 */
                                        class C41561 implements Runnable {
                                            C41561() {
                                            }

                                            public void run() {
                                                ChannelEditInfoActivity.this.canCreatePublic = true;
                                                if (ChannelEditInfoActivity.this.nameTextView.length() > 0) {
                                                    ChannelEditInfoActivity.this.checkUserName(ChannelEditInfoActivity.this.nameTextView.getText().toString());
                                                }
                                                ChannelEditInfoActivity.this.updatePrivatePublic();
                                            }
                                        }

                                        C41571() {
                                        }

                                        public void run(TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                                            if (tLObject instanceof TL_boolTrue) {
                                                AndroidUtilities.runOnUIThread(new C41561());
                                            }
                                        }
                                    }

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        TLObject tL_channels_updateUsername = new TL_channels_updateUsername();
                                        tL_channels_updateUsername.channel = MessagesController.getInputChannel(currentChannel);
                                        tL_channels_updateUsername.username = TtmlNode.ANONYMOUS_REGION_ID;
                                        ConnectionsManager.getInstance().sendRequest(tL_channels_updateUsername, new C41571(), 64);
                                    }
                                });
                                ChannelEditInfoActivity.this.showDialog(builder.create());
                            }
                        }

                        public void run() {
                            ChannelEditInfoActivity.this.loadingAdminedChannels = false;
                            if (tLObject != null && ChannelEditInfoActivity.this.getParentActivity() != null) {
                                for (int i = 0; i < ChannelEditInfoActivity.this.adminedChannelCells.size(); i++) {
                                    ChannelEditInfoActivity.this.linearLayout.removeView((View) ChannelEditInfoActivity.this.adminedChannelCells.get(i));
                                }
                                ChannelEditInfoActivity.this.adminedChannelCells.clear();
                                TLRPC$TL_messages_chats tLRPC$TL_messages_chats = (TLRPC$TL_messages_chats) tLObject;
                                int i2 = 0;
                                while (i2 < tLRPC$TL_messages_chats.chats.size()) {
                                    View adminedChannelCell = new AdminedChannelCell(ChannelEditInfoActivity.this.getParentActivity(), new C41591());
                                    adminedChannelCell.setChannel((Chat) tLRPC$TL_messages_chats.chats.get(i2), i2 == tLRPC$TL_messages_chats.chats.size() + -1);
                                    ChannelEditInfoActivity.this.adminedChannelCells.add(adminedChannelCell);
                                    ChannelEditInfoActivity.this.adminnedChannelsLayout.addView(adminedChannelCell, LayoutHelper.createLinear(-1, 72));
                                    i2++;
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
                this.publicContainer.setVisibility(this.isPrivate ? 8 : 0);
                this.privateContainer.setVisibility(this.isPrivate ? 0 : 8);
                this.linkContainer.setPadding(0, 0, 0, this.isPrivate ? 0 : AndroidUtilities.dp(7.0f));
                this.privateContainer.setText(this.invite != null ? this.invite.link : LocaleController.getString("Loading", R.string.Loading), false);
                TextView textView = this.checkTextView;
                int i2 = (this.isPrivate || this.checkTextView.length() == 0) ? 8 : 0;
                textView.setVisibility(i2);
                if (this.headerCell2 != null) {
                    this.headerCell2.setVisibility(this.isPrivate ? 0 : 8);
                    this.linearLayoutInviteContainer.setVisibility(this.isPrivate ? 0 : 8);
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

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setActionBarMenuOnItemClick(new C41683());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.fragmentView = new ScrollView(context);
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        ScrollView scrollView = (ScrollView) this.fragmentView;
        scrollView.setFillViewport(true);
        this.linearLayout = new LinearLayout(context);
        scrollView.addView(this.linearLayout, new LayoutParams(-1, -2));
        this.linearLayout.setOrientation(1);
        this.actionBar.setTitle(LocaleController.getString("ChannelEdit", R.string.ChannelEdit));
        this.linearLayout2 = new LinearLayout(context);
        this.linearLayout2.setOrientation(1);
        this.linearLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.linearLayout.addView(this.linearLayout2, LayoutHelper.createLinear(-1, -2));
        View frameLayout = new FrameLayout(context);
        this.linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(32.0f));
        this.avatarDrawable.setInfo(5, null, null, false);
        this.avatarDrawable.setDrawPhoto(true);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(64, 64.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 16.0f, 12.0f, LocaleController.isRTL ? 16.0f : BitmapDescriptorFactory.HUE_RED, 12.0f));
        this.avatarImage.setOnClickListener(new C41704());
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
        this.nameTextView.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.nameTextView.setInputType(16385);
        this.nameTextView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.nameTextView.setEnabled(ChatObject.canChangeChatInfo(this.currentChat));
        this.nameTextView.setFocusable(this.nameTextView.isEnabled());
        this.nameTextView.setFilters(new InputFilter[]{new LengthFilter(100)});
        this.nameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setCursorWidth(1.5f);
        frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 16, LocaleController.isRTL ? 16.0f : 96.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 96.0f : 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView.addTextChangedListener(new C41715());
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
        this.descriptionTextView.setOnEditorActionListener(new C41726());
        this.descriptionTextView.addTextChangedListener(new C41737());
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
            this.radioButtonCell1.setOnClickListener(new C41748());
            this.radioButtonCell2 = new RadioButtonCell(context);
            this.radioButtonCell2.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            if (this.currentChat.megagroup) {
                this.radioButtonCell2.setTextAndValue(LocaleController.getString("MegaPrivate", R.string.MegaPrivate), LocaleController.getString("MegaPrivateInfo", R.string.MegaPrivateInfo), this.isPrivate);
            } else {
                this.radioButtonCell2.setTextAndValue(LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate), LocaleController.getString("ChannelPrivateInfo", R.string.ChannelPrivateInfo), this.isPrivate);
            }
            this.linearLayoutTypeContainer.addView(this.radioButtonCell2, LayoutHelper.createLinear(-1, -2));
            this.radioButtonCell2.setOnClickListener(new C41759());
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
            this.linkContainer.addView(this.publicContainer, LayoutHelper.createLinear(-1, 36, 17.0f, 7.0f, 17.0f, BitmapDescriptorFactory.HUE_RED));
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
                public void afterTextChanged(Editable editable) {
                }

                public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                    ChannelEditInfoActivity.this.checkUserName(ChannelEditInfoActivity.this.usernameTextView.getText().toString());
                }
            });
            this.privateContainer = new TextBlockCell(context);
            this.privateContainer.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            this.linkContainer.addView(this.privateContainer);
            this.privateContainer.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (ChannelEditInfoActivity.this.invite != null) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", ChannelEditInfoActivity.this.invite.link));
                            Toast.makeText(ChannelEditInfoActivity.this.getParentActivity(), LocaleController.getString("LinkCopied", R.string.LinkCopied), 0).show();
                        } catch (Throwable e) {
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
                public void onClick(View view) {
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
                public void onClick(View view) {
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
                public void onClick(View view) {
                    ChannelEditInfoActivity.this.signMessages = !ChannelEditInfoActivity.this.signMessages;
                    ((TextCheckCell) view).setChecked(ChannelEditInfoActivity.this.signMessages);
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
                public void onClick(View view) {
                    BaseFragment groupStickersActivity = new GroupStickersActivity(ChannelEditInfoActivity.this.currentChat.id);
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
                class C41551 implements OnClickListener {
                    C41551() {
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

                public void onClick(View view) {
                    Builder builder = new Builder(ChannelEditInfoActivity.this.getParentActivity());
                    if (ChannelEditInfoActivity.this.currentChat.megagroup) {
                        builder.setMessage(LocaleController.getString("MegaDeleteAlert", R.string.MegaDeleteAlert));
                    } else {
                        builder.setMessage(LocaleController.getString("ChannelDeleteAlert", R.string.ChannelDeleteAlert));
                    }
                    builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C41551());
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

    public void didReceivedNotification(int i, Object... objArr) {
        if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chatId) {
                if (this.info == null) {
                    this.descriptionTextView.setText(chatFull.about);
                    this.historyHidden = chatFull.hidden_prehistory;
                    if (this.radioButtonCell3 != null) {
                        this.radioButtonCell3.setChecked(!this.historyHidden, false);
                        this.radioButtonCell4.setChecked(this.historyHidden, false);
                    }
                }
                this.info = chatFull;
                this.invite = chatFull.exported_invite;
                updatePrivatePublic();
            }
        }
    }

    public void didUploadedPhoto(final InputFile inputFile, final PhotoSize photoSize, PhotoSize photoSize2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ChannelEditInfoActivity.this.uploadedAvatar = inputFile;
                ChannelEditInfoActivity.this.avatar = photoSize.location;
                ChannelEditInfoActivity.this.avatarImage.setImage(ChannelEditInfoActivity.this.avatar, "50_50", ChannelEditInfoActivity.this.avatarDrawable);
                if (ChannelEditInfoActivity.this.createAfterUpload) {
                    try {
                        if (ChannelEditInfoActivity.this.progressDialog != null && ChannelEditInfoActivity.this.progressDialog.isShowing()) {
                            ChannelEditInfoActivity.this.progressDialog.dismiss();
                            ChannelEditInfoActivity.this.progressDialog = null;
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    ChannelEditInfoActivity.this.donePressed = false;
                    ChannelEditInfoActivity.this.doneButton.performClick();
                }
            }
        });
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass21 anonymousClass21 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                if (ChannelEditInfoActivity.this.avatarImage != null) {
                    ChannelEditInfoActivity.this.avatarDrawable.setInfo(5, ChannelEditInfoActivity.this.nameTextView.length() > 0 ? ChannelEditInfoActivity.this.nameTextView.getText().toString() : null, null, false);
                    ChannelEditInfoActivity.this.avatarImage.invalidate();
                }
            }
        };
        AnonymousClass22 anonymousClass22 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                if (ChannelEditInfoActivity.this.adminnedChannelsLayout != null) {
                    int childCount = ChannelEditInfoActivity.this.adminnedChannelsLayout.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = ChannelEditInfoActivity.this.adminnedChannelsLayout.getChildAt(i2);
                        if (childAt instanceof AdminedChannelCell) {
                            ((AdminedChannelCell) childAt).update();
                        }
                    }
                }
            }
        };
        r10 = new ThemeDescription[93];
        r10[17] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, anonymousClass21, Theme.key_avatar_text);
        r10[18] = new ThemeDescription(null, 0, null, null, null, anonymousClass21, Theme.key_avatar_backgroundBlue);
        r10[19] = new ThemeDescription(this.lineView, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r10[20] = new ThemeDescription(this.lineView2, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r10[21] = new ThemeDescription(this.lineView3, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_divider);
        r10[22] = new ThemeDescription(this.sectionCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[23] = new ThemeDescription(this.sectionCell2, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[24] = new ThemeDescription(this.sectionCell3, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[25] = new ThemeDescription(this.textCheckCell, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[26] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[27] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumb);
        r10[28] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrack);
        r10[29] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchThumbChecked);
        r10[30] = new ThemeDescription(this.textCheckCell, 0, new Class[]{TextCheckCell.class}, new String[]{"checkBox"}, null, null, null, Theme.key_switchTrackChecked);
        r10[31] = new ThemeDescription(this.infoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[32] = new ThemeDescription(this.infoCell, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[33] = new ThemeDescription(this.textCell, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[34] = new ThemeDescription(this.textCell, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText5);
        r10[35] = new ThemeDescription(this.textCell2, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[36] = new ThemeDescription(this.textCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{TextSettingsCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[37] = new ThemeDescription(this.infoCell2, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[38] = new ThemeDescription(this.infoCell2, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[39] = new ThemeDescription(this.infoCell3, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[40] = new ThemeDescription(this.infoCell3, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[41] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[42] = new ThemeDescription(this.usernameTextView, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r10[43] = new ThemeDescription(this.linearLayoutTypeContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r10[44] = new ThemeDescription(this.linkContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r10[45] = new ThemeDescription(this.headerCell, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r10[46] = new ThemeDescription(this.headerCell2, 0, new Class[]{HeaderCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlueHeader);
        r10[47] = new ThemeDescription(this.editText, ThemeDescription.FLAG_TEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[48] = new ThemeDescription(this.editText, ThemeDescription.FLAG_HINTTEXTCOLOR, null, null, null, null, Theme.key_windowBackgroundWhiteHintText);
        r10[49] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r10[50] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGrayText8);
        r10[51] = new ThemeDescription(this.checkTextView, ThemeDescription.FLAG_TEXTCOLOR | ThemeDescription.FLAG_CHECKTAG, null, null, null, null, Theme.key_windowBackgroundWhiteGreenText);
        r10[52] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[53] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[54] = new ThemeDescription(this.typeInfoCell, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText4);
        r10[55] = new ThemeDescription(this.adminedInfoCell, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[56] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r10[57] = new ThemeDescription(this.privateContainer, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[58] = new ThemeDescription(this.privateContainer, 0, new Class[]{TextBlockCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[59] = new ThemeDescription(this.loadingAdminedCell, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[60] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[61] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[62] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[63] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[64] = new ThemeDescription(this.radioButtonCell1, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[65] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[66] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[67] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[68] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[69] = new ThemeDescription(this.radioButtonCell2, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[70] = new ThemeDescription(this.linearLayoutInviteContainer, ThemeDescription.FLAG_BACKGROUND, null, null, null, null, Theme.key_windowBackgroundWhite);
        r10[71] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[72] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[73] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[74] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[75] = new ThemeDescription(this.radioButtonCell3, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[76] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_SELECTOR, null, null, null, null, Theme.key_listSelector);
        r10[77] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOX, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackground);
        r10[78] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_CHECKBOXCHECK, new Class[]{RadioButtonCell.class}, new String[]{"radioButton"}, null, null, null, Theme.key_radioBackgroundChecked);
        r10[79] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[80] = new ThemeDescription(this.radioButtonCell4, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{RadioButtonCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText2);
        r10[81] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[82] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r10[83] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_LINKCOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"statusTextView"}, null, null, null, Theme.key_windowBackgroundWhiteLinkText);
        r10[84] = new ThemeDescription(this.adminnedChannelsLayout, ThemeDescription.FLAG_IMAGECOLOR, new Class[]{AdminedChannelCell.class}, new String[]{"deleteButton"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText);
        r10[85] = new ThemeDescription(null, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, anonymousClass22, Theme.key_avatar_text);
        r10[86] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundRed);
        r10[87] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundOrange);
        r10[88] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundViolet);
        r10[89] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundGreen);
        r10[90] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundCyan);
        r10[91] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundBlue);
        r10[92] = new ThemeDescription(null, 0, null, null, null, anonymousClass22, Theme.key_avatar_backgroundPink);
        return r10;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.avatarUpdater.onActivityResult(i, i2, intent);
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
            } catch (Throwable e) {
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
                } catch (Throwable e2) {
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
            TLObject tL_channels_checkUsername = new TL_channels_checkUsername();
            tL_channels_checkUsername.username = "1";
            tL_channels_checkUsername.channel = new TLRPC$TL_inputChannelEmpty();
            ConnectionsManager.getInstance().sendRequest(tL_channels_checkUsername, new C41662());
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

    public void restoreSelfArgs(Bundle bundle) {
        if (this.avatarUpdater != null) {
            this.avatarUpdater.currentPicturePath = bundle.getString("path");
        }
    }

    public void saveSelfArgs(Bundle bundle) {
        if (!(this.avatarUpdater == null || this.avatarUpdater.currentPicturePath == null)) {
            bundle.putString("path", this.avatarUpdater.currentPicturePath);
        }
        if (this.nameTextView != null) {
            String obj = this.nameTextView.getText().toString();
            if (obj != null && obj.length() != 0) {
                bundle.putString("nameTextView", obj);
            }
        }
    }

    public void setInfo(ChatFull chatFull) {
        if (this.info == null && chatFull != null) {
            this.historyHidden = chatFull.hidden_prehistory;
        }
        this.info = chatFull;
        if (chatFull == null) {
            return;
        }
        if (chatFull.exported_invite instanceof TL_chatInviteExported) {
            this.invite = chatFull.exported_invite;
        } else {
            generateLink();
        }
    }
}
