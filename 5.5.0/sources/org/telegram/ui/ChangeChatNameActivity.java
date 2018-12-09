package org.telegram.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import org.ir.talaeii.R;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.UserConfig;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_chatPhoto;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.TextSettingsCell;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.EditTextBoldCursor;
import org.telegram.ui.Components.LayoutHelper;

public class ChangeChatNameActivity extends BaseFragment implements AvatarUpdaterDelegate {
    private static final int done_button = 1;
    private FileLocation avatar;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater;
    private int chatId;
    private boolean createAfterUpload;
    private Chat currentChat;
    private View doneButton;
    private boolean donePressed;
    private View headerLabelView;
    private EditTextBoldCursor nameTextView;
    private AlertDialog progressDialog;
    private InputFile uploadedAvatar;

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$1 */
    class C40401 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ChangeChatNameActivity$1$1 */
        class C40391 implements OnClickListener {
            C40391() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                ChangeChatNameActivity.this.createAfterUpload = false;
                ChangeChatNameActivity.this.progressDialog = null;
                ChangeChatNameActivity.this.donePressed = false;
                try {
                    dialogInterface.dismiss();
                } catch (Throwable e) {
                    FileLog.e(e);
                }
            }
        }

        C40401() {
        }

        public void onItemClick(int i) {
            if (i == -1) {
                ChangeChatNameActivity.this.finishFragment();
            } else if (i == 1 && !ChangeChatNameActivity.this.donePressed) {
                if (ChangeChatNameActivity.this.nameTextView.length() == 0) {
                    Vibrator vibrator = (Vibrator) ChangeChatNameActivity.this.getParentActivity().getSystemService("vibrator");
                    if (vibrator != null) {
                        vibrator.vibrate(200);
                    }
                    AndroidUtilities.shakeView(ChangeChatNameActivity.this.nameTextView, 2.0f, 0);
                    return;
                }
                ChangeChatNameActivity.this.donePressed = true;
                if (ChangeChatNameActivity.this.avatarUpdater.uploadingAvatar != null) {
                    ChangeChatNameActivity.this.createAfterUpload = true;
                    ChangeChatNameActivity.this.progressDialog = new AlertDialog(ChangeChatNameActivity.this.getParentActivity(), 1);
                    ChangeChatNameActivity.this.progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                    ChangeChatNameActivity.this.progressDialog.setCanceledOnTouchOutside(false);
                    ChangeChatNameActivity.this.progressDialog.setCancelable(false);
                    ChangeChatNameActivity.this.progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new C40391());
                    ChangeChatNameActivity.this.progressDialog.show();
                    return;
                }
                if (ChangeChatNameActivity.this.uploadedAvatar != null) {
                    MessagesController.getInstance().changeChatAvatar(ChangeChatNameActivity.this.chatId, ChangeChatNameActivity.this.uploadedAvatar);
                } else if (ChangeChatNameActivity.this.avatar == null && (ChangeChatNameActivity.this.currentChat.photo instanceof TL_chatPhoto)) {
                    MessagesController.getInstance().changeChatAvatar(ChangeChatNameActivity.this.chatId, null);
                }
                ChangeChatNameActivity.this.finishFragment();
                if (ChangeChatNameActivity.this.nameTextView.getText().length() != 0) {
                    ChangeChatNameActivity.this.saveName();
                    ChangeChatNameActivity.this.finishFragment();
                }
            }
        }
    }

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$2 */
    class C40412 implements OnTouchListener {
        C40412() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    }

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$3 */
    class C40433 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.ChangeChatNameActivity$3$1 */
        class C40421 implements OnClickListener {
            C40421() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    ChangeChatNameActivity.this.avatarUpdater.openCamera();
                } else if (i == 1) {
                    ChangeChatNameActivity.this.avatarUpdater.openGallery();
                } else if (i == 2) {
                    ChangeChatNameActivity.this.avatar = null;
                    ChangeChatNameActivity.this.uploadedAvatar = null;
                    ChangeChatNameActivity.this.avatarImage.setImage(ChangeChatNameActivity.this.avatar, "50_50", ChangeChatNameActivity.this.avatarDrawable);
                }
            }
        }

        C40433() {
        }

        public void onClick(View view) {
            if (ChangeChatNameActivity.this.getParentActivity() != null) {
                Builder builder = new Builder(ChangeChatNameActivity.this.getParentActivity());
                builder.setItems(ChangeChatNameActivity.this.avatar != null ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)}, new C40421());
                ChangeChatNameActivity.this.showDialog(builder.create());
            }
        }
    }

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$4 */
    class C40444 implements TextWatcher {
        C40444() {
        }

        public void afterTextChanged(Editable editable) {
            ChangeChatNameActivity.this.avatarDrawable.setInfo(5, ChangeChatNameActivity.this.nameTextView.length() > 0 ? ChangeChatNameActivity.this.nameTextView.getText().toString() : null, null, false);
            ChangeChatNameActivity.this.avatarImage.invalidate();
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$5 */
    class C40465 implements View.OnClickListener {

        /* renamed from: org.telegram.ui.ChangeChatNameActivity$5$1 */
        class C40451 implements OnClickListener {
            C40451() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
                if (AndroidUtilities.isTablet()) {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(-((long) ChangeChatNameActivity.this.chatId))});
                } else {
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                }
                MessagesController.getInstance().deleteUserFromChat(ChangeChatNameActivity.this.chatId, MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), null, true);
                ChangeChatNameActivity.this.finishFragment();
            }
        }

        C40465() {
        }

        public void onClick(View view) {
            Builder builder = new Builder(ChangeChatNameActivity.this.getParentActivity());
            builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C40451());
            builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
            ChangeChatNameActivity.this.showDialog(builder.create());
        }
    }

    /* renamed from: org.telegram.ui.ChangeChatNameActivity$7 */
    class C40487 implements Runnable {
        C40487() {
        }

        public void run() {
            if (ChangeChatNameActivity.this.nameTextView != null) {
                ChangeChatNameActivity.this.nameTextView.requestFocus();
                AndroidUtilities.showKeyboard(ChangeChatNameActivity.this.nameTextView);
            }
        }
    }

    public ChangeChatNameActivity(Bundle bundle) {
        super(bundle);
    }

    private void saveName() {
        MessagesController.getInstance().changeChatTitle(this.chatId, this.nameTextView.getText().toString());
    }

    public View createView(Context context) {
        this.actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        this.actionBar.setAllowOverlayTitle(true);
        this.actionBar.setTitle(LocaleController.getString("ChannelEdit", R.string.ChannelEdit));
        this.actionBar.setActionBarMenuOnItemClick(new C40401());
        this.doneButton = this.actionBar.createMenu().addItemWithWidth(1, R.drawable.ic_done, AndroidUtilities.dp(56.0f));
        this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chatId));
        View linearLayout = new LinearLayout(context);
        this.fragmentView = linearLayout;
        this.fragmentView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundGray));
        this.fragmentView.setLayoutParams(new LayoutParams(-1, -1));
        ((LinearLayout) this.fragmentView).setOrientation(1);
        this.fragmentView.setOnTouchListener(new C40412());
        View linearLayout2 = new LinearLayout(context);
        linearLayout2.setOrientation(1);
        linearLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2));
        View frameLayout = new FrameLayout(context);
        linearLayout2.addView(frameLayout, LayoutHelper.createLinear(-1, -2));
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(32.0f));
        this.avatarDrawable.setInfo(5, null, null, false);
        this.avatarDrawable.setDrawPhoto(true);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(64, 64.0f, (LocaleController.isRTL ? 5 : 3) | 48, LocaleController.isRTL ? BitmapDescriptorFactory.HUE_RED : 16.0f, 12.0f, LocaleController.isRTL ? 16.0f : BitmapDescriptorFactory.HUE_RED, 12.0f));
        this.avatarImage.setOnClickListener(new C40433());
        this.nameTextView = new EditTextBoldCursor(context);
        if (this.currentChat.megagroup) {
            this.nameTextView.setHint(LocaleController.getString("GroupName", R.string.GroupName));
        } else {
            this.nameTextView.setHint(LocaleController.getString("EnterChannelName", R.string.EnterChannelName));
        }
        this.nameTextView.setMaxLines(4);
        this.nameTextView.setText(this.currentChat.title);
        this.nameTextView.setGravity((LocaleController.isRTL ? 5 : 3) | 16);
        this.nameTextView.setTextSize(1, 16.0f);
        this.nameTextView.setHint(LocaleController.getString("GroupName", R.string.GroupName));
        this.nameTextView.setHintTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteHintText));
        this.nameTextView.setBackgroundDrawable(Theme.createEditTextDrawable(context, false));
        this.nameTextView.setImeOptions(ErrorDialogData.BINDER_CRASH);
        this.nameTextView.setInputType(16385);
        this.nameTextView.setPadding(0, 0, 0, AndroidUtilities.dp(8.0f));
        this.nameTextView.setFocusable(this.nameTextView.isEnabled());
        this.nameTextView.setFilters(new InputFilter[]{new LengthFilter(100)});
        this.nameTextView.setCursorColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        this.nameTextView.setCursorSize(AndroidUtilities.dp(20.0f));
        this.nameTextView.setCursorWidth(1.5f);
        this.nameTextView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
        frameLayout.addView(this.nameTextView, LayoutHelper.createFrame(-1, -2.0f, 16, LocaleController.isRTL ? 16.0f : 96.0f, BitmapDescriptorFactory.HUE_RED, LocaleController.isRTL ? 96.0f : 16.0f, BitmapDescriptorFactory.HUE_RED));
        this.nameTextView.addTextChangedListener(new C40444());
        linearLayout2 = new ShadowSectionCell(context);
        linearLayout2.setSize(20);
        linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2));
        if (this.currentChat.creator) {
            linearLayout2 = new FrameLayout(context);
            linearLayout2.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
            linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2));
            View textSettingsCell = new TextSettingsCell(context);
            textSettingsCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText5));
            textSettingsCell.setBackgroundDrawable(Theme.getSelectorDrawable(false));
            textSettingsCell.setText(LocaleController.getString("DeleteMega", R.string.DeleteMega), false);
            linearLayout2.addView(textSettingsCell, LayoutHelper.createFrame(-1, -2.0f));
            textSettingsCell.setOnClickListener(new C40465());
            linearLayout2 = new TextInfoPrivacyCell(context);
            linearLayout2.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
            linearLayout2.setText(LocaleController.getString("MegaDeleteInfo", R.string.MegaDeleteInfo));
            linearLayout.addView(linearLayout2, LayoutHelper.createLinear(-1, -2));
        } else {
            linearLayout2.setBackgroundDrawable(Theme.getThemedDrawable(context, R.drawable.greydivider_bottom, Theme.key_windowBackgroundGrayShadow));
        }
        this.nameTextView.setSelection(this.nameTextView.length());
        if (this.currentChat.photo != null) {
            this.avatar = this.currentChat.photo.photo_small;
            this.avatarImage.setImage(this.avatar, "50_50", this.avatarDrawable);
        } else {
            this.avatarImage.setImageDrawable(this.avatarDrawable);
        }
        return this.fragmentView;
    }

    public void didUploadedPhoto(final InputFile inputFile, final PhotoSize photoSize, PhotoSize photoSize2) {
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                ChangeChatNameActivity.this.uploadedAvatar = inputFile;
                ChangeChatNameActivity.this.avatar = photoSize.location;
                ChangeChatNameActivity.this.avatarImage.setImage(ChangeChatNameActivity.this.avatar, "50_50", ChangeChatNameActivity.this.avatarDrawable);
                if (ChangeChatNameActivity.this.createAfterUpload) {
                    ChangeChatNameActivity.this.donePressed = false;
                    try {
                        if (ChangeChatNameActivity.this.progressDialog != null && ChangeChatNameActivity.this.progressDialog.isShowing()) {
                            ChangeChatNameActivity.this.progressDialog.dismiss();
                            ChangeChatNameActivity.this.progressDialog = null;
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                    ChangeChatNameActivity.this.doneButton.performClick();
                }
            }
        });
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        this.avatarUpdater.onActivityResult(i, i2, intent);
    }

    public boolean onFragmentCreate() {
        super.onFragmentCreate();
        this.avatarDrawable = new AvatarDrawable();
        this.chatId = getArguments().getInt("chat_id", 0);
        this.avatarUpdater = new AvatarUpdater();
        this.avatarUpdater.parentFragment = this;
        this.avatarUpdater.delegate = this;
        return true;
    }

    public void onResume() {
        super.onResume();
        if (!ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).getBoolean("view_animations", true)) {
            this.nameTextView.requestFocus();
            AndroidUtilities.showKeyboard(this.nameTextView);
        }
    }

    public void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (z) {
            AndroidUtilities.runOnUIThread(new C40487(), 100);
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
}
