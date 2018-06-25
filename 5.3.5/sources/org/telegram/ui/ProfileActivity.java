package org.telegram.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Keep;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.measurement.AppMeasurement.Param;
import com.persianswitch.sdk.base.log.LogCollector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.PhoneFormat.PhoneFormat;
import org.telegram.SLSSQLite.DatabaseHandler;
import org.telegram.customization.Activities.ScheduleDownloadActivity;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Internet.SLSProxyHelper;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.ApplicationLoader;
import org.telegram.messenger.BuildConfig;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.ContactsController;
import org.telegram.messenger.FileLog;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.MessageObject;
import org.telegram.messenger.MessagesController;
import org.telegram.messenger.MessagesStorage;
import org.telegram.messenger.NotificationCenter;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.NotificationsController;
import org.telegram.messenger.SecretChatHelper;
import org.telegram.messenger.SendMessagesHelper;
import org.telegram.messenger.UserConfig;
import org.telegram.messenger.UserObject;
import org.telegram.messenger.Utilities;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$BotInfo;
import org.telegram.tgnet.TLRPC$ChannelParticipant;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$FileLocation;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$TL_channelAdminRights;
import org.telegram.tgnet.TLRPC$TL_channelBannedRights;
import org.telegram.tgnet.TLRPC$TL_channelFull;
import org.telegram.tgnet.TLRPC$TL_channelParticipant;
import org.telegram.tgnet.TLRPC$TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_channelParticipantBanned;
import org.telegram.tgnet.TLRPC$TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC$TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_chatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_chatParticipantCreator;
import org.telegram.tgnet.TLRPC$TL_chatParticipants;
import org.telegram.tgnet.TLRPC$TL_chatParticipantsForbidden;
import org.telegram.tgnet.TLRPC$TL_chatPhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.ActionBar.ActionBarMenuOnItemClick;
import org.telegram.ui.ActionBar.ActionBarMenu;
import org.telegram.ui.ActionBar.ActionBarMenuItem;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BackDrawable;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ActionBar.SimpleTextView;
import org.telegram.ui.ActionBar.Theme;
import org.telegram.ui.ActionBar.ThemeDescription;
import org.telegram.ui.ActionBar.ThemeDescription.ThemeDescriptionDelegate;
import org.telegram.ui.Cells.AboutLinkCell;
import org.telegram.ui.Cells.AboutLinkCell.AboutLinkCellDelegate;
import org.telegram.ui.Cells.DividerCell;
import org.telegram.ui.Cells.EmptyCell;
import org.telegram.ui.Cells.LoadingCell;
import org.telegram.ui.Cells.ShadowSectionCell;
import org.telegram.ui.Cells.TextCell;
import org.telegram.ui.Cells.TextDetailCell;
import org.telegram.ui.Cells.TextInfoPrivacyCell;
import org.telegram.ui.Cells.UserCell;
import org.telegram.ui.ChannelRightsEditActivity.ChannelRightsEditActivityDelegate;
import org.telegram.ui.Components.AlertsCreator;
import org.telegram.ui.Components.AvatarDrawable;
import org.telegram.ui.Components.AvatarUpdater;
import org.telegram.ui.Components.AvatarUpdater.AvatarUpdaterDelegate;
import org.telegram.ui.Components.BackupImageView;
import org.telegram.ui.Components.CombinedDrawable;
import org.telegram.ui.Components.IdenticonDrawable;
import org.telegram.ui.Components.LayoutHelper;
import org.telegram.ui.Components.RecyclerListView;
import org.telegram.ui.Components.RecyclerListView.Holder;
import org.telegram.ui.Components.RecyclerListView.OnItemClickListener;
import org.telegram.ui.Components.RecyclerListView.OnItemLongClickListener;
import org.telegram.ui.Components.RecyclerListView.SelectionAdapter;
import org.telegram.ui.Components.voip.VoIPHelper;
import org.telegram.ui.ContactsActivity.ContactsActivityDelegate;
import org.telegram.ui.DialogsActivity.DialogsActivityDelegate;
import org.telegram.ui.PhotoViewer.EmptyPhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PhotoViewerProvider;
import org.telegram.ui.PhotoViewer.PlaceProviderObject;
import utils.app.AppPreferences;

public class ProfileActivity extends BaseFragment implements NotificationCenterDelegate, DialogsActivityDelegate {
    private static final int add_contact = 1;
    private static final int add_shortcut = 14;
    private static final int block_contact = 2;
    private static final int call_item = 15;
    private static final int convert_to_supergroup = 13;
    private static final int delete_contact = 5;
    private static final int edit_channel = 12;
    private static final int edit_contact = 4;
    private static final int edit_name = 8;
    private static final int invite_to_group = 9;
    private static final int leave_group = 7;
    private static final int search_members = 16;
    private static final int set_admins = 11;
    private static final int share = 10;
    private static final int share_contact = 3;
    private int addMemberRow;
    private boolean allowProfileAnimation = true;
    private ActionBarMenuItem animatingItem;
    private float animationProgress;
    private AvatarDrawable avatarDrawable;
    private BackupImageView avatarImage;
    private AvatarUpdater avatarUpdater;
    private int banFromGroup;
    private TLRPC$BotInfo botInfo;
    private ActionBarMenuItem callItem;
    private int channelInfoRow;
    boolean channelIsFilter = false;
    private int channelNameRow;
    private int chat_id;
    private int convertHelpRow;
    private int convertRow;
    private boolean creatingChat;
    private TLRPC$ChannelParticipant currentChannelParticipant;
    private TLRPC$Chat currentChat;
    private TLRPC$EncryptedChat currentEncryptedChat;
    private long dialog_id;
    private ActionBarMenuItem editItem;
    private int emptyRow;
    private int emptyRowChat;
    private int emptyRowChat2;
    private int extraHeight;
    private int groupsInCommonRow;
    private TLRPC$ChatFull info;
    private int initialAnimationExtraHeight;
    private boolean isBot;
    private LinearLayoutManager layoutManager;
    private int leaveChannelRow;
    private ListAdapter listAdapter;
    private RecyclerListView listView;
    private int loadMoreMembersRow;
    private boolean loadingUsers;
    private int membersEndRow;
    private int membersRow;
    private int membersSectionRow;
    private long mergeDialogId;
    private SimpleTextView[] nameTextView = new SimpleTextView[2];
    private int onlineCount = -1;
    private SimpleTextView[] onlineTextView = new SimpleTextView[2];
    private boolean openAnimationInProgress;
    private HashMap<Integer, TLRPC$ChatParticipant> participantsMap = new HashMap();
    private int phoneRow;
    private boolean playProfileAnimation;
    private PhotoViewerProvider provider = new C32843();
    private boolean recreateMenuAfterAnimation;
    private int rowCount = 0;
    private int sectionRow;
    private int selectedUser;
    private int settingsKeyRow;
    private int settingsNotificationsRow;
    private int settingsTimerRow;
    private int sharedMediaRow;
    private ArrayList<Integer> sortedUsers;
    private int startSecretChatRow;
    private TopView topView;
    private int totalMediaCount = -1;
    private int totalMediaCountMerge = -1;
    private boolean userBlocked;
    private int userInfoDetailedRow;
    private int userInfoRow;
    private int userSectionRow;
    private int user_id;
    private int usernameRow;
    private boolean usersEndReached;
    private ImageView writeButton;
    private AnimatorSet writeButtonAnimation;

    /* renamed from: org.telegram.ui.ProfileActivity$3 */
    class C32843 extends EmptyPhotoViewerProvider {
        C32843() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, TLRPC$FileLocation fileLocation, int index) {
            PlaceProviderObject placeProviderObject = null;
            int i = 0;
            if (fileLocation != null) {
                TLRPC$FileLocation photoBig = null;
                if (ProfileActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                        photoBig = user.photo.photo_big;
                    }
                } else if (ProfileActivity.this.chat_id != 0) {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                    if (!(chat == null || chat.photo == null || chat.photo.photo_big == null)) {
                        photoBig = chat.photo.photo_big;
                    }
                }
                if (photoBig != null && photoBig.local_id == fileLocation.local_id && photoBig.volume_id == fileLocation.volume_id && photoBig.dc_id == fileLocation.dc_id) {
                    int[] coords = new int[2];
                    ProfileActivity.this.avatarImage.getLocationInWindow(coords);
                    placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = coords[0];
                    int i2 = coords[1];
                    if (VERSION.SDK_INT < 21) {
                        i = AndroidUtilities.statusBarHeight;
                    }
                    placeProviderObject.viewY = i2 - i;
                    placeProviderObject.parentView = ProfileActivity.this.avatarImage;
                    placeProviderObject.imageReceiver = ProfileActivity.this.avatarImage.getImageReceiver();
                    if (ProfileActivity.this.user_id != 0) {
                        placeProviderObject.dialogId = ProfileActivity.this.user_id;
                    } else if (ProfileActivity.this.chat_id != 0) {
                        placeProviderObject.dialogId = -ProfileActivity.this.chat_id;
                    }
                    placeProviderObject.thumb = placeProviderObject.imageReceiver.getBitmap();
                    placeProviderObject.size = -1;
                    placeProviderObject.radius = ProfileActivity.this.avatarImage.getImageReceiver().getRoundRadius();
                    placeProviderObject.scale = ProfileActivity.this.avatarImage.getScaleX();
                }
            }
            return placeProviderObject;
        }

        public void willHidePhotoViewer() {
            ProfileActivity.this.avatarImage.getImageReceiver().setVisible(true, true);
        }
    }

    /* renamed from: org.telegram.ui.ProfileActivity$5 */
    class C32865 implements AvatarUpdaterDelegate {
        C32865() {
        }

        public void didUploadedPhoto(TLRPC$InputFile file, TLRPC$PhotoSize small, TLRPC$PhotoSize big) {
            if (ProfileActivity.this.chat_id != 0) {
                MessagesController.getInstance().changeChatAvatar(ProfileActivity.this.chat_id, file);
            }
        }
    }

    /* renamed from: org.telegram.ui.ProfileActivity$7 */
    class C32897 implements Runnable {

        /* renamed from: org.telegram.ui.ProfileActivity$7$1 */
        class C32881 implements IResponseReceiver {
            C32881() {
            }

            public void onResult(Object object, int StatusCode) {
                switch (StatusCode) {
                    case 19:
                        FilterResponse response = (FilterResponse) object;
                        if (response != null) {
                            DialogStatus dialogStatus = new DialogStatus();
                            dialogStatus.setFilter(response.isFilter());
                            dialogStatus.setDialogId(response.getChannelId());
                            ApplicationLoader.databaseHandler.createOrUpdateDialogStatus(dialogStatus);
                            if (response.isFilter()) {
                                ProfileActivity.this.channelIsFilter = response.isFilter();
                                ProfileActivity.this.showCantOpenAlert(ProfileActivity.this, AppPreferences.getFilterMessage(ApplicationLoader.applicationContext), true);
                                return;
                            }
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }

        C32897() {
        }

        public void run() {
            if (ChatObject.isChannel(ProfileActivity.this.currentChat) && ProfileActivity.this.currentChat != null && !TextUtils.isEmpty(ProfileActivity.this.currentChat.username) && ChatObject.isNotInChat(ProfileActivity.this.currentChat)) {
                HandleRequest.getNew(ApplicationLoader.applicationContext, new C32881()).singleCheckFilterStatus((long) UserConfig.getClientUserId(), ProfileActivity.this.currentChat.username);
            }
        }
    }

    /* renamed from: org.telegram.ui.ProfileActivity$8 */
    class C32938 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ProfileActivity$8$1 */
        class C32901 implements OnClickListener {
            C32901() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (ProfileActivity.this.userBlocked) {
                    MessagesController.getInstance().unblockUser(ProfileActivity.this.user_id);
                } else {
                    MessagesController.getInstance().blockUser(ProfileActivity.this.user_id);
                }
            }
        }

        C32938() {
        }

        public void onItemClick(int id) {
            if (ProfileActivity.this.getParentActivity() != null) {
                if (id == -1) {
                    ProfileActivity.this.finishFragment();
                } else if (id == 2) {
                    if (MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)) == null) {
                        return;
                    }
                    if (!ProfileActivity.this.isBot) {
                        builder = new Builder(ProfileActivity.this.getParentActivity());
                        if (ProfileActivity.this.userBlocked) {
                            builder.setMessage(LocaleController.getString("AreYouSureUnblockContact", R.string.AreYouSureUnblockContact));
                        } else {
                            builder.setMessage(LocaleController.getString("AreYouSureBlockContact", R.string.AreYouSureBlockContact));
                        }
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C32901());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder.create());
                    } else if (ProfileActivity.this.userBlocked) {
                        MessagesController.getInstance().unblockUser(ProfileActivity.this.user_id);
                        SendMessagesHelper.getInstance().sendMessage("/start", (long) ProfileActivity.this.user_id, null, null, false, null, null, null);
                        ProfileActivity.this.finishFragment();
                    } else {
                        MessagesController.getInstance().blockUser(ProfileActivity.this.user_id);
                    }
                } else if (id == 1) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    args = new Bundle();
                    args.putInt("user_id", user.id);
                    args.putBoolean("addContact", true);
                    ProfileActivity.this.presentFragment(new ContactAddActivity(args));
                } else if (id == 3) {
                    args = new Bundle();
                    args.putBoolean("onlySelect", true);
                    args.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendContactTo));
                    args.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                    r0 = new DialogsActivity(args);
                    r0.setDelegate(ProfileActivity.this);
                    ProfileActivity.this.presentFragment(r0);
                } else if (id == 4) {
                    args = new Bundle();
                    args.putInt("user_id", ProfileActivity.this.user_id);
                    ProfileActivity.this.presentFragment(new ContactAddActivity(args));
                } else if (id == 5) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (user != null && ProfileActivity.this.getParentActivity() != null) {
                        builder = new Builder(ProfileActivity.this.getParentActivity());
                        builder.setMessage(LocaleController.getString("AreYouSureDeleteContact", R.string.AreYouSureDeleteContact));
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        r1 = user;
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList<User> arrayList = new ArrayList();
                                arrayList.add(r1);
                                ContactsController.getInstance().deleteContact(arrayList);
                            }
                        });
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder.create());
                    }
                } else if (id == 7) {
                    ProfileActivity.this.leaveChatPressed();
                } else if (id == 8) {
                    args = new Bundle();
                    args.putInt("chat_id", ProfileActivity.this.chat_id);
                    ProfileActivity.this.presentFragment(new ChangeChatNameActivity(args));
                } else if (id == 12) {
                    args = new Bundle();
                    args.putInt("chat_id", ProfileActivity.this.chat_id);
                    r0 = new ChannelEditActivity(args);
                    r0.setInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(r0);
                } else if (id == 9) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (user != null) {
                        args = new Bundle();
                        args.putBoolean("onlySelect", true);
                        args.putInt("dialogsType", 2);
                        args.putString("addToGroupAlertString", LocaleController.formatString("AddToTheGroupTitle", R.string.AddToTheGroupTitle, new Object[]{UserObject.getUserName(user), "%1$s"}));
                        r0 = new DialogsActivity(args);
                        r1 = user;
                        r0.setDelegate(new DialogsActivityDelegate() {
                            public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
                                long did = ((Long) dids.get(0)).longValue();
                                Bundle args = new Bundle();
                                args.putBoolean("scrollToTopOnResume", true);
                                args.putInt("chat_id", -((int) did));
                                if (MessagesController.checkCanOpenChat(args, fragment)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    MessagesController.getInstance().addUserToChat(-((int) did), r1, null, 0, null, ProfileActivity.this);
                                    ProfileActivity.this.presentFragment(new ChatActivity(args), true);
                                    ProfileActivity.this.removeSelfFromStack();
                                }
                            }
                        });
                        ProfileActivity.this.presentFragment(r0);
                    }
                } else if (id == 10) {
                    try {
                        if (MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)) != null) {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.botInfo.user_id);
                            if (ProfileActivity.this.botInfo == null || userFull == null || TextUtils.isEmpty(userFull.about)) {
                                intent = intent;
                                intent.putExtra("android.intent.extra.TEXT", String.format("https://" + MessagesController.getInstance().linkPrefix + "/%s", new Object[]{user.username}));
                            } else {
                                intent = intent;
                                intent.putExtra("android.intent.extra.TEXT", String.format("%s https://" + MessagesController.getInstance().linkPrefix + "/%s", new Object[]{userFull.about, user.username}));
                            }
                            ProfileActivity.this.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("BotShare", R.string.BotShare)), 500);
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                } else if (id == 11) {
                    args = new Bundle();
                    args.putInt("chat_id", ProfileActivity.this.chat_id);
                    r0 = new SetAdminsActivity(args);
                    r0.setChatInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(r0);
                } else if (id == 13) {
                    args = new Bundle();
                    args.putInt("chat_id", ProfileActivity.this.chat_id);
                    ProfileActivity.this.presentFragment(new ConvertGroupActivity(args));
                } else if (id == 14) {
                    try {
                        long did;
                        if (ProfileActivity.this.currentEncryptedChat != null) {
                            did = ((long) ProfileActivity.this.currentEncryptedChat.id) << 32;
                        } else if (ProfileActivity.this.user_id != 0) {
                            did = (long) ProfileActivity.this.user_id;
                        } else if (ProfileActivity.this.chat_id != 0) {
                            did = (long) (-ProfileActivity.this.chat_id);
                        } else {
                            return;
                        }
                        AndroidUtilities.installShortcut(did);
                    } catch (Exception e2) {
                        FileLog.e(e2);
                    }
                } else if (id == 15) {
                    user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (user != null) {
                        VoIPHelper.startCall(user, ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(user.id));
                    }
                } else if (id == 16) {
                    args = new Bundle();
                    args.putInt("chat_id", ProfileActivity.this.chat_id);
                    if (ChatObject.isChannel(ProfileActivity.this.currentChat)) {
                        args.putInt(Param.TYPE, 2);
                        args.putBoolean("open_search", true);
                        ProfileActivity.this.presentFragment(new ChannelUsersActivity(args));
                        return;
                    }
                    ChatUsersActivity chatUsersActivity = new ChatUsersActivity(args);
                    chatUsersActivity.setInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(chatUsersActivity);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ProfileActivity$ListAdapter$1 */
        class C32951 implements AboutLinkCellDelegate {
            C32951() {
            }

            public void didPressUrl(String url) {
                if (url.startsWith("@")) {
                    MessagesController.openByUserName(url.substring(1), ProfileActivity.this, 0);
                } else if (url.startsWith("#")) {
                    DialogsActivity fragment = new DialogsActivity(null);
                    fragment.setSearchString(url);
                    ProfileActivity.this.presentFragment(fragment);
                } else if (url.startsWith("/") && ProfileActivity.this.parentLayout.fragmentsStack.size() > 1) {
                    BaseFragment previousFragment = (BaseFragment) ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2);
                    if (previousFragment instanceof ChatActivity) {
                        ProfileActivity.this.finishFragment();
                        ((ChatActivity) previousFragment).chatActivityEnterView.setCommand(null, url, false, false);
                    }
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = null;
            CombinedDrawable combinedDrawable;
            switch (viewType) {
                case 0:
                    view = new EmptyCell(this.mContext);
                    break;
                case 1:
                    view = new DividerCell(this.mContext);
                    view.setPadding(AndroidUtilities.dp(72.0f), 0, 0, 0);
                    break;
                case 2:
                    view = new TextDetailCell(this.mContext);
                    break;
                case 3:
                    view = new TextCell(this.mContext);
                    break;
                case 4:
                    view = new UserCell(this.mContext, 61, 0, true);
                    break;
                case 5:
                    view = new ShadowSectionCell(this.mContext);
                    combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable.setFullsize(true);
                    view.setBackgroundDrawable(combinedDrawable);
                    break;
                case 6:
                    view = new TextInfoPrivacyCell(this.mContext);
                    TextInfoPrivacyCell cell = (TextInfoPrivacyCell) view;
                    combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable.setFullsize(true);
                    cell.setBackgroundDrawable(combinedDrawable);
                    cell.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ConvertGroupInfo", R.string.ConvertGroupInfo, new Object[]{LocaleController.formatPluralString("Members", MessagesController.getInstance().maxMegagroupCount)})));
                    break;
                case 7:
                    view = new LoadingCell(this.mContext);
                    break;
                case 8:
                    view = new AboutLinkCell(this.mContext);
                    ((AboutLinkCell) view).setDelegate(new C32951());
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }

        public void onBindViewHolder(ViewHolder holder, int i) {
            String text;
            TLRPC$TL_userFull userFull;
            switch (holder.getItemViewType()) {
                case 0:
                    if (i == ProfileActivity.this.emptyRowChat || i == ProfileActivity.this.emptyRowChat2) {
                        ((EmptyCell) holder.itemView).setHeight(AndroidUtilities.dp(8.0f));
                        return;
                    } else {
                        ((EmptyCell) holder.itemView).setHeight(AndroidUtilities.dp(36.0f));
                        return;
                    }
                case 2:
                    TextDetailCell textDetailCell = (TextDetailCell) holder.itemView;
                    textDetailCell.setMultiline(false);
                    User user;
                    if (i == ProfileActivity.this.phoneRow) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                        if (user.phone == null || user.phone.length() == 0) {
                            text = LocaleController.getString("NumberUnknown", R.string.NumberUnknown);
                        } else {
                            text = PhoneFormat.getInstance().format("+" + user.phone);
                        }
                        textDetailCell.setTextAndValueAndIcon(text, LocaleController.getString("PhoneMobile", R.string.PhoneMobile), R.drawable.profile_phone, 0);
                        return;
                    } else if (i == ProfileActivity.this.usernameRow) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                        if (user == null || TextUtils.isEmpty(user.username)) {
                            text = "-";
                        } else {
                            text = "@" + user.username;
                        }
                        if (ProfileActivity.this.phoneRow == -1 && ProfileActivity.this.userInfoRow == -1 && ProfileActivity.this.userInfoDetailedRow == -1) {
                            textDetailCell.setTextAndValueAndIcon(text, LocaleController.getString("Username", R.string.Username), R.drawable.profile_info, 11);
                            return;
                        } else {
                            textDetailCell.setTextAndValue(text, LocaleController.getString("Username", R.string.Username));
                            return;
                        }
                    } else if (i == ProfileActivity.this.channelNameRow) {
                        if (ProfileActivity.this.currentChat == null || TextUtils.isEmpty(ProfileActivity.this.currentChat.username)) {
                            text = "-";
                        } else {
                            text = "@" + ProfileActivity.this.currentChat.username;
                        }
                        textDetailCell.setTextAndValue(text, MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
                        return;
                    } else if (i == ProfileActivity.this.userInfoDetailedRow) {
                        userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                        textDetailCell.setMultiline(true);
                        textDetailCell.setTextAndValueAndIcon(userFull != null ? userFull.about : null, LocaleController.getString("UserBio", R.string.UserBio), R.drawable.profile_info, 11);
                        return;
                    } else {
                        return;
                    }
                case 3:
                    TextCell textCell = (TextCell) holder.itemView;
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    textCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    String value;
                    String str;
                    if (i == ProfileActivity.this.sharedMediaRow) {
                        if (ProfileActivity.this.totalMediaCount == -1) {
                            value = LocaleController.getString("Loading", R.string.Loading);
                        } else {
                            str = "%d";
                            Object[] objArr = new Object[1];
                            objArr[0] = Integer.valueOf((ProfileActivity.this.totalMediaCountMerge != -1 ? ProfileActivity.this.totalMediaCountMerge : 0) + ProfileActivity.this.totalMediaCount);
                            value = String.format(str, objArr);
                        }
                        if (ProfileActivity.this.user_id == 0 || UserConfig.getClientUserId() != ProfileActivity.this.user_id) {
                            textCell.setTextAndValue(LocaleController.getString("SharedMedia", R.string.SharedMedia), value);
                            return;
                        } else {
                            textCell.setTextAndValueAndIcon(LocaleController.getString("SharedMedia", R.string.SharedMedia), value, R.drawable.profile_list);
                            return;
                        }
                    } else if (i == ProfileActivity.this.groupsInCommonRow) {
                        userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                        str = LocaleController.getString("GroupsInCommon", R.string.GroupsInCommon);
                        String str2 = "%d";
                        Object[] objArr2 = new Object[1];
                        objArr2[0] = Integer.valueOf(userFull != null ? userFull.common_chats_count : 0);
                        textCell.setTextAndValue(str, String.format(str2, objArr2));
                        return;
                    } else if (i == ProfileActivity.this.settingsTimerRow) {
                        TLRPC$EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (ProfileActivity.this.dialog_id >> 32)));
                        if (encryptedChat.ttl == 0) {
                            value = LocaleController.getString("ShortMessageLifetimeForever", R.string.ShortMessageLifetimeForever);
                        } else {
                            value = LocaleController.formatTTLString(encryptedChat.ttl);
                        }
                        textCell.setTextAndValue(LocaleController.getString("MessageLifetime", R.string.MessageLifetime), value);
                        return;
                    } else if (i == ProfileActivity.this.settingsNotificationsRow) {
                        long did;
                        String val;
                        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                        if (ProfileActivity.this.dialog_id != 0) {
                            did = ProfileActivity.this.dialog_id;
                        } else if (ProfileActivity.this.user_id != 0) {
                            did = (long) ProfileActivity.this.user_id;
                        } else {
                            did = (long) (-ProfileActivity.this.chat_id);
                        }
                        boolean custom = preferences.getBoolean("custom_" + did, false);
                        boolean hasOverride = preferences.contains("notify2_" + did);
                        int value2 = preferences.getInt("notify2_" + did, 0);
                        int delta = preferences.getInt("notifyuntil_" + did, 0);
                        if (value2 != 3 || delta == Integer.MAX_VALUE) {
                            boolean enabled;
                            if (value2 == 0) {
                                if (hasOverride) {
                                    enabled = true;
                                } else if (((int) did) < 0) {
                                    enabled = preferences.getBoolean("EnableGroup", true);
                                } else {
                                    enabled = preferences.getBoolean("EnableAll", true);
                                }
                            } else if (value2 == 1) {
                                enabled = true;
                            } else if (value2 == 2) {
                                enabled = false;
                            } else {
                                enabled = false;
                            }
                            if (enabled && custom) {
                                val = LocaleController.getString("NotificationsCustom", R.string.NotificationsCustom);
                            } else {
                                val = enabled ? LocaleController.getString("NotificationsOn", R.string.NotificationsOn) : LocaleController.getString("NotificationsOff", R.string.NotificationsOff);
                            }
                        } else {
                            delta -= ConnectionsManager.getInstance().getCurrentTime();
                            if (delta <= 0) {
                                if (custom) {
                                    val = LocaleController.getString("NotificationsCustom", R.string.NotificationsCustom);
                                } else {
                                    val = LocaleController.getString("NotificationsOn", R.string.NotificationsOn);
                                }
                            } else if (delta < 3600) {
                                val = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Minutes", delta / 60)});
                            } else if (delta < 86400) {
                                val = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Hours", (int) Math.ceil((double) ((((float) delta) / 60.0f) / 60.0f)))});
                            } else if (delta < 31536000) {
                                val = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Days", (int) Math.ceil((double) (((((float) delta) / 60.0f) / 60.0f) / 24.0f)))});
                            } else {
                                val = null;
                            }
                        }
                        if (val != null) {
                            textCell.setTextAndValueAndIcon(LocaleController.getString("Notifications", R.string.Notifications), val, R.drawable.profile_list);
                            return;
                        } else {
                            textCell.setTextAndValueAndIcon(LocaleController.getString("Notifications", R.string.Notifications), LocaleController.getString("NotificationsOff", R.string.NotificationsOff), R.drawable.profile_list);
                            return;
                        }
                    } else if (i == ProfileActivity.this.startSecretChatRow) {
                        textCell.setText(LocaleController.getString("StartEncryptedChat", R.string.StartEncryptedChat));
                        textCell.setTag(Theme.key_windowBackgroundWhiteGreenText2);
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText2));
                        return;
                    } else if (i == ProfileActivity.this.settingsKeyRow) {
                        Drawable identiconDrawable = new IdenticonDrawable();
                        identiconDrawable.setEncryptedChat(MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (ProfileActivity.this.dialog_id >> 32))));
                        textCell.setTextAndValueDrawable(LocaleController.getString("EncryptionKey", R.string.EncryptionKey), identiconDrawable);
                        return;
                    } else if (i == ProfileActivity.this.leaveChannelRow) {
                        textCell.setTag(Theme.key_windowBackgroundWhiteRedText5);
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText5));
                        textCell.setText(LocaleController.getString("LeaveChannel", R.string.LeaveChannel));
                        return;
                    } else if (i == ProfileActivity.this.convertRow) {
                        textCell.setText(LocaleController.getString("UpgradeGroup", R.string.UpgradeGroup));
                        textCell.setTag(Theme.key_windowBackgroundWhiteGreenText2);
                        textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteGreenText2));
                        return;
                    } else if (i == ProfileActivity.this.addMemberRow) {
                        if (ProfileActivity.this.chat_id > 0) {
                            textCell.setText(LocaleController.getString("AddMember", R.string.AddMember));
                            return;
                        } else {
                            textCell.setText(LocaleController.getString("AddRecipient", R.string.AddRecipient));
                            return;
                        }
                    } else if (i != ProfileActivity.this.membersRow) {
                        return;
                    } else {
                        if (ProfileActivity.this.info != null) {
                            if (!ChatObject.isChannel(ProfileActivity.this.currentChat) || ProfileActivity.this.currentChat.megagroup) {
                                textCell.setTextAndValue(LocaleController.getString("ChannelMembers", R.string.ChannelMembers), String.format("%d", new Object[]{Integer.valueOf(ProfileActivity.this.info.participants_count)}));
                                return;
                            } else {
                                textCell.setTextAndValue(LocaleController.getString("ChannelSubscribers", R.string.ChannelSubscribers), String.format("%d", new Object[]{Integer.valueOf(ProfileActivity.this.info.participants_count)}));
                                return;
                            }
                        } else if (!ChatObject.isChannel(ProfileActivity.this.currentChat) || ProfileActivity.this.currentChat.megagroup) {
                            textCell.setText(LocaleController.getString("ChannelMembers", R.string.ChannelMembers));
                            return;
                        } else {
                            textCell.setText(LocaleController.getString("ChannelSubscribers", R.string.ChannelSubscribers));
                            return;
                        }
                    }
                case 4:
                    TLRPC$ChatParticipant part;
                    UserCell userCell = (UserCell) holder.itemView;
                    if (ProfileActivity.this.sortedUsers.isEmpty()) {
                        part = (TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get((i - ProfileActivity.this.emptyRowChat2) - 1);
                    } else {
                        part = (TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((i - ProfileActivity.this.emptyRowChat2) - 1)).intValue());
                    }
                    if (part != null) {
                        int i2;
                        if (part instanceof TLRPC$TL_chatChannelParticipant) {
                            TLRPC$ChannelParticipant channelParticipant = ((TLRPC$TL_chatChannelParticipant) part).channelParticipant;
                            if (channelParticipant instanceof TLRPC$TL_channelParticipantCreator) {
                                userCell.setIsAdmin(1);
                            } else if (channelParticipant instanceof TLRPC$TL_channelParticipantAdmin) {
                                userCell.setIsAdmin(2);
                            } else {
                                userCell.setIsAdmin(0);
                            }
                        } else if (part instanceof TLRPC$TL_chatParticipantCreator) {
                            userCell.setIsAdmin(1);
                        } else if (ProfileActivity.this.currentChat.admins_enabled && (part instanceof TLRPC$TL_chatParticipantAdmin)) {
                            userCell.setIsAdmin(2);
                        } else {
                            userCell.setIsAdmin(0);
                        }
                        TLObject user2 = MessagesController.getInstance().getUser(Integer.valueOf(part.user_id));
                        if (i == ProfileActivity.this.emptyRowChat2 + 1) {
                            i2 = R.drawable.menu_newgroup;
                        } else {
                            i2 = 0;
                        }
                        userCell.setData(user2, null, null, i2);
                        return;
                    }
                    return;
                case 8:
                    AboutLinkCell aboutLinkCell = holder.itemView;
                    if (i == ProfileActivity.this.userInfoRow) {
                        userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                        aboutLinkCell.setTextAndIcon(userFull != null ? userFull.about : null, R.drawable.profile_info, ProfileActivity.this.isBot);
                        return;
                    } else if (i == ProfileActivity.this.channelInfoRow) {
                        text = ProfileActivity.this.info.about;
                        while (text.contains("\n\n\n")) {
                            text = text.replace("\n\n\n", "\n\n");
                        }
                        aboutLinkCell.setTextAndIcon(text, R.drawable.profile_info, true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public boolean isEnabled(ViewHolder holder) {
            int i = holder.getAdapterPosition();
            if (ProfileActivity.this.user_id != 0) {
                if (i == ProfileActivity.this.phoneRow || i == ProfileActivity.this.settingsTimerRow || i == ProfileActivity.this.settingsKeyRow || i == ProfileActivity.this.settingsNotificationsRow || i == ProfileActivity.this.sharedMediaRow || i == ProfileActivity.this.startSecretChatRow || i == ProfileActivity.this.usernameRow || i == ProfileActivity.this.userInfoRow || i == ProfileActivity.this.groupsInCommonRow || i == ProfileActivity.this.userInfoDetailedRow) {
                    return true;
                }
                return false;
            } else if (ProfileActivity.this.chat_id == 0) {
                return false;
            } else {
                if (i == ProfileActivity.this.convertRow || i == ProfileActivity.this.settingsNotificationsRow || i == ProfileActivity.this.sharedMediaRow || ((i > ProfileActivity.this.emptyRowChat2 && i < ProfileActivity.this.membersEndRow) || i == ProfileActivity.this.addMemberRow || i == ProfileActivity.this.channelNameRow || i == ProfileActivity.this.leaveChannelRow || i == ProfileActivity.this.channelInfoRow || i == ProfileActivity.this.membersRow)) {
                    return true;
                }
                return false;
            }
        }

        public int getItemCount() {
            return ProfileActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            if (i == ProfileActivity.this.emptyRow || i == ProfileActivity.this.emptyRowChat || i == ProfileActivity.this.emptyRowChat2) {
                return 0;
            }
            if (i == ProfileActivity.this.sectionRow || i == ProfileActivity.this.userSectionRow) {
                return 1;
            }
            if (i == ProfileActivity.this.phoneRow || i == ProfileActivity.this.usernameRow || i == ProfileActivity.this.channelNameRow || i == ProfileActivity.this.userInfoDetailedRow) {
                return 2;
            }
            if (i == ProfileActivity.this.leaveChannelRow || i == ProfileActivity.this.sharedMediaRow || i == ProfileActivity.this.settingsTimerRow || i == ProfileActivity.this.settingsNotificationsRow || i == ProfileActivity.this.startSecretChatRow || i == ProfileActivity.this.settingsKeyRow || i == ProfileActivity.this.convertRow || i == ProfileActivity.this.addMemberRow || i == ProfileActivity.this.groupsInCommonRow || i == ProfileActivity.this.membersRow) {
                return 3;
            }
            if (i > ProfileActivity.this.emptyRowChat2 && i < ProfileActivity.this.membersEndRow) {
                return 4;
            }
            if (i == ProfileActivity.this.membersSectionRow) {
                return 5;
            }
            if (i == ProfileActivity.this.convertHelpRow) {
                return 6;
            }
            if (i == ProfileActivity.this.loadMoreMembersRow) {
                return 7;
            }
            if (i == ProfileActivity.this.userInfoRow || i == ProfileActivity.this.channelInfoRow) {
                return 8;
            }
            return 0;
        }
    }

    private class TopView extends View {
        private int currentColor;
        private Paint paint = new Paint();

        public TopView(Context context) {
            super(context);
        }

        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), ((ProfileActivity.this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.dp(91.0f));
        }

        public void setBackgroundColor(int color) {
            if (color != this.currentColor) {
                this.paint.setColor(color);
                invalidate();
            }
        }

        protected void onDraw(Canvas canvas) {
            int height = getMeasuredHeight() - AndroidUtilities.dp(91.0f);
            canvas.drawRect(0.0f, 0.0f, (float) getMeasuredWidth(), (float) (ProfileActivity.this.extraHeight + height), this.paint);
            if (ProfileActivity.this.parentLayout != null) {
                ProfileActivity.this.parentLayout.drawHeaderShadow(canvas, ProfileActivity.this.extraHeight + height);
            }
        }
    }

    private void showCantOpenAlert(BaseFragment fragment, String reason, final boolean callBackPress) {
        if (fragment != null) {
            try {
                if (fragment.getParentActivity() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getParentActivity());
                    builder.setTitle("");
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (callBackPress) {
                                ProfileActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    builder.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            if (callBackPress && ProfileActivity.this.parentLayout != null) {
                                ProfileActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    builder.setMessage(reason);
                    builder.setCancelable(false);
                    fragment.showDialog(builder.create());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ProfileActivity(Bundle args) {
        super(args);
    }

    public boolean onFragmentCreate() {
        this.user_id = this.arguments.getInt("user_id", 0);
        this.chat_id = this.arguments.getInt("chat_id", 0);
        this.banFromGroup = this.arguments.getInt("ban_chat_id", 0);
        if (this.user_id != 0) {
            this.dialog_id = this.arguments.getLong("dialog_id", 0);
            if (this.dialog_id != 0) {
                this.currentEncryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (this.dialog_id >> 32)));
            }
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            if (user == null) {
                return false;
            }
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.contactsDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatCreated);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.encryptedChatUpdated);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.blockedUsersDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.botInfoDidLoaded);
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.userInfoDidLoaded);
            if (this.currentEncryptedChat != null) {
                NotificationCenter.getInstance().addObserver(this, NotificationCenter.didReceivedNewMessages);
            }
            this.userBlocked = MessagesController.getInstance().blockedUsers.contains(Integer.valueOf(this.user_id));
            if (user.bot) {
                this.isBot = true;
                BotQuery.loadBotInfo(user.id, true, this.classGuid);
            }
            MessagesController.getInstance().loadFullUser(MessagesController.getInstance().getUser(Integer.valueOf(this.user_id)), this.classGuid, true);
            this.participantsMap = null;
        } else if (this.chat_id == 0) {
            return false;
        } else {
            this.currentChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
            if (this.currentChat == null) {
                final Semaphore semaphore = new Semaphore(0);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new Runnable() {
                    public void run() {
                        ProfileActivity.this.currentChat = MessagesStorage.getInstance().getChat(ProfileActivity.this.chat_id);
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
            }
            if (this.currentChat.megagroup) {
                getChannelParticipants(true);
            } else {
                this.participantsMap = null;
            }
            NotificationCenter.getInstance().addObserver(this, NotificationCenter.chatInfoDidLoaded);
            this.sortedUsers = new ArrayList();
            updateOnlineCount();
            this.avatarUpdater = new AvatarUpdater();
            this.avatarUpdater.delegate = new C32865();
            this.avatarUpdater.parentFragment = this;
            if (ChatObject.isChannel(this.currentChat)) {
                MessagesController.getInstance().loadFullChat(this.chat_id, this.classGuid, true);
            }
        }
        if (this.dialog_id != 0) {
            SharedMediaQuery.getMediaCount(this.dialog_id, 0, this.classGuid, true);
        } else if (this.user_id != 0) {
            SharedMediaQuery.getMediaCount((long) this.user_id, 0, this.classGuid, true);
        } else if (this.chat_id > 0) {
            SharedMediaQuery.getMediaCount((long) (-this.chat_id), 0, this.classGuid, true);
            if (this.mergeDialogId != 0) {
                SharedMediaQuery.getMediaCount(this.mergeDialogId, 0, this.classGuid, true);
            }
        }
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.mediaCountDidLoaded);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().addObserver(this, NotificationCenter.closeChats);
        updateRowsIds();
        return true;
    }

    public void onFragmentDestroy() {
        super.onFragmentDestroy();
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.mediaCountDidLoaded);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.updateInterfaces);
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        if (this.user_id != 0) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.contactsDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.encryptedChatCreated);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.encryptedChatUpdated);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.blockedUsersDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.botInfoDidLoaded);
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.userInfoDidLoaded);
            MessagesController.getInstance().cancelLoadFullUser(this.user_id);
            if (this.currentEncryptedChat != null) {
                NotificationCenter.getInstance().removeObserver(this, NotificationCenter.didReceivedNewMessages);
            }
        } else if (this.chat_id != 0) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.chatInfoDidLoaded);
            this.avatarUpdater.clear();
        }
    }

    protected ActionBar createActionBar(Context context) {
        boolean z;
        ActionBar actionBar = new ActionBar(context) {
            public boolean onTouchEvent(MotionEvent event) {
                return super.onTouchEvent(event);
            }
        };
        int i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        actionBar.setItemsBackgroundColor(AvatarDrawable.getButtonColorForId(i), false);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon), false);
        actionBar.setItemsColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), true);
        actionBar.setBackButtonDrawable(new BackDrawable(false));
        actionBar.setCastShadows(false);
        actionBar.setAddToContainer(false);
        if (VERSION.SDK_INT < 21 || AndroidUtilities.isTablet()) {
            z = false;
        } else {
            z = true;
        }
        actionBar.setOccupyStatusBar(z);
        return actionBar;
    }

    public View createView(Context context) {
        if (!BuildConfig.FLAVOR.contentEquals("vip") && AppPreferences.checkFilterChannel(ApplicationLoader.applicationContext)) {
            if (DatabaseHandler.isFilter((long) Math.abs(this.chat_id))) {
                this.channelIsFilter = true;
                return createView2(context);
            }
            new Handler().postDelayed(new C32897(), 1000);
        }
        if (DatabaseHandler.isFilter(Math.abs(this.dialog_id))) {
            this.channelIsFilter = true;
            return createView2(context);
        } else if (!DatabaseHandler.isFilter(Math.abs(this.dialog_id))) {
            return createView2(context);
        } else {
            this.channelIsFilter = true;
            return createView2(context);
        }
    }

    public View createView2(Context context) {
        int i;
        Theme.createProfileResources(context);
        this.hasOwnBackground = true;
        this.extraHeight = AndroidUtilities.dp(88.0f);
        this.actionBar.setActionBarMenuOnItemClick(new C32938());
        createActionBarMenu();
        this.listAdapter = new ListAdapter(context);
        this.avatarDrawable = new AvatarDrawable();
        this.avatarDrawable.setProfile(true);
        this.fragmentView = new FrameLayout(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }

            protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
                super.onLayout(changed, left, top, right, bottom);
                ProfileActivity.this.checkListViewScroll();
            }
        };
        FrameLayout frameLayout = this.fragmentView;
        this.listView = new RecyclerListView(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }
        };
        this.listView.setTag(Integer.valueOf(6));
        this.listView.setBackgroundColor(Theme.getColor(Theme.key_windowBackgroundWhite));
        this.listView.setVerticalScrollBarEnabled(false);
        this.listView.setItemAnimator(null);
        this.listView.setLayoutAnimation(null);
        this.listView.setClipToPadding(false);
        this.layoutManager = new LinearLayoutManager(context) {
            public boolean supportsPredictiveItemAnimations() {
                return false;
            }
        };
        this.layoutManager.setOrientation(1);
        this.listView.setLayoutManager(this.layoutManager);
        RecyclerListView recyclerListView = this.listView;
        if (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) {
            i = 5;
        } else {
            i = this.chat_id;
        }
        recyclerListView.setGlowColor(AvatarDrawable.getProfileBackColorForId(i));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new OnItemClickListener() {

            /* renamed from: org.telegram.ui.ProfileActivity$12$2 */
            class C32742 implements OnClickListener {
                C32742() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ProfileActivity.this.creatingChat = true;
                    SecretChatHelper.getInstance().startSecretChat(ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)));
                }
            }

            /* renamed from: org.telegram.ui.ProfileActivity$12$3 */
            class C32753 implements OnClickListener {
                C32753() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    MessagesController.getInstance().convertToMegaGroup(ProfileActivity.this.getParentActivity(), ProfileActivity.this.chat_id);
                }
            }

            public void onItemClick(View view, int position) {
                if (ProfileActivity.this.getParentActivity() != null) {
                    Bundle args;
                    if (position == ProfileActivity.this.sharedMediaRow) {
                        args = new Bundle();
                        if (ProfileActivity.this.user_id != 0) {
                            args.putLong("dialog_id", ProfileActivity.this.dialog_id != 0 ? ProfileActivity.this.dialog_id : (long) ProfileActivity.this.user_id);
                        } else {
                            args.putLong("dialog_id", (long) (-ProfileActivity.this.chat_id));
                        }
                        MediaActivity fragment = new MediaActivity(args);
                        fragment.setChatInfo(ProfileActivity.this.info);
                        ProfileActivity.this.presentFragment(fragment);
                    } else if (position == ProfileActivity.this.groupsInCommonRow) {
                        ProfileActivity.this.presentFragment(new CommonGroupsActivity(ProfileActivity.this.user_id));
                    } else if (position == ProfileActivity.this.settingsKeyRow) {
                        args = new Bundle();
                        args.putInt("chat_id", (int) (ProfileActivity.this.dialog_id >> 32));
                        ProfileActivity.this.presentFragment(new IdenticonActivity(args));
                    } else if (position == ProfileActivity.this.settingsTimerRow) {
                        ProfileActivity.this.showDialog(AlertsCreator.createTTLAlert(ProfileActivity.this.getParentActivity(), ProfileActivity.this.currentEncryptedChat).create());
                    } else if (position == ProfileActivity.this.settingsNotificationsRow) {
                        long did;
                        if (ProfileActivity.this.dialog_id != 0) {
                            did = ProfileActivity.this.dialog_id;
                        } else if (ProfileActivity.this.user_id != 0) {
                            did = (long) ProfileActivity.this.user_id;
                        } else {
                            did = (long) (-ProfileActivity.this.chat_id);
                        }
                        String[] descriptions = new String[5];
                        descriptions[0] = LocaleController.getString("NotificationsTurnOn", R.string.NotificationsTurnOn);
                        descriptions[1] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 1)});
                        descriptions[2] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Days", 2)});
                        descriptions[3] = LocaleController.getString("NotificationsCustomize", R.string.NotificationsCustomize);
                        descriptions[4] = LocaleController.getString("NotificationsTurnOff", R.string.NotificationsTurnOff);
                        int i = 5;
                        int[] icons = new int[]{R.drawable.notifications_s_on, R.drawable.notifications_s_1h, R.drawable.notifications_s_2d, R.drawable.notifications_s_custom, R.drawable.notifications_s_off};
                        View linearLayout = new LinearLayout(ProfileActivity.this.getParentActivity());
                        linearLayout.setOrientation(1);
                        for (int a = 0; a < descriptions.length; a++) {
                            linearLayout = new TextView(ProfileActivity.this.getParentActivity());
                            linearLayout.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                            linearLayout.setTextSize(1, 16.0f);
                            linearLayout.setLines(1);
                            linearLayout.setMaxLines(1);
                            Drawable drawable = ProfileActivity.this.getParentActivity().getResources().getDrawable(icons[a]);
                            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
                            linearLayout.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                            linearLayout.setTag(Integer.valueOf(a));
                            linearLayout.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            linearLayout.setPadding(AndroidUtilities.dp(24.0f), 0, AndroidUtilities.dp(24.0f), 0);
                            linearLayout.setSingleLine(true);
                            linearLayout.setGravity(19);
                            linearLayout.setCompoundDrawablePadding(AndroidUtilities.dp(26.0f));
                            linearLayout.setText(descriptions[a]);
                            linearLayout.addView(linearLayout, LayoutHelper.createLinear(-1, 48, 51));
                            linearLayout.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    int i = ((Integer) v.getTag()).intValue();
                                    Editor editor;
                                    TLRPC$TL_dialog dialog;
                                    if (i == 0) {
                                        editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                        editor.putInt("notify2_" + did, 0);
                                        MessagesStorage.getInstance().setDialogFlags(did, 0);
                                        editor.commit();
                                        dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(did));
                                        if (dialog != null) {
                                            dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                                        }
                                        NotificationsController.updateServerNotificationsSettings(did);
                                    } else if (i == 3) {
                                        Bundle args = new Bundle();
                                        args.putLong("dialog_id", did);
                                        ProfileActivity.this.presentFragment(new ProfileNotificationsActivity(args));
                                    } else {
                                        long flags;
                                        int untilTime = ConnectionsManager.getInstance().getCurrentTime();
                                        if (i == 1) {
                                            untilTime += 3600;
                                        } else if (i == 2) {
                                            untilTime += 172800;
                                        } else if (i == 4) {
                                            untilTime = Integer.MAX_VALUE;
                                        }
                                        editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                        if (i == 4) {
                                            editor.putInt("notify2_" + did, 2);
                                            flags = 1;
                                        } else {
                                            editor.putInt("notify2_" + did, 3);
                                            editor.putInt("notifyuntil_" + did, untilTime);
                                            flags = (((long) untilTime) << 32) | 1;
                                        }
                                        NotificationsController.getInstance().removeNotificationsForDialog(did);
                                        MessagesStorage.getInstance().setDialogFlags(did, flags);
                                        editor.commit();
                                        dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(did));
                                        if (dialog != null) {
                                            dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                                            dialog.notify_settings.mute_until = untilTime;
                                        }
                                        NotificationsController.updateServerNotificationsSettings(did);
                                    }
                                    ProfileActivity.this.listAdapter.notifyItemChanged(ProfileActivity.this.settingsNotificationsRow);
                                    ProfileActivity.this.dismissCurrentDialig();
                                }
                            });
                        }
                        builder = new Builder(ProfileActivity.this.getParentActivity());
                        builder.setTitle(LocaleController.getString("Notifications", R.string.Notifications));
                        builder.setView(linearLayout);
                        ProfileActivity.this.showDialog(builder.create());
                    } else if (position == ProfileActivity.this.startSecretChatRow) {
                        builder = new Builder(ProfileActivity.this.getParentActivity());
                        builder.setMessage(LocaleController.getString("AreYouSureSecretChat", R.string.AreYouSureSecretChat));
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C32742());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder.create());
                    } else if (position > ProfileActivity.this.emptyRowChat2 && position < ProfileActivity.this.membersEndRow) {
                        int user_id;
                        if (ProfileActivity.this.sortedUsers.isEmpty()) {
                            user_id = ((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get((position - ProfileActivity.this.emptyRowChat2) - 1)).user_id;
                        } else {
                            user_id = ((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((position - ProfileActivity.this.emptyRowChat2) - 1)).intValue())).user_id;
                        }
                        if (user_id != UserConfig.getClientUserId()) {
                            args = new Bundle();
                            args.putInt("user_id", user_id);
                            ProfileActivity.this.presentFragment(new ProfileActivity(args));
                        }
                    } else if (position == ProfileActivity.this.addMemberRow) {
                        ProfileActivity.this.openAddMember();
                    } else if (position == ProfileActivity.this.channelNameRow) {
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            if (ProfileActivity.this.info.about == null || ProfileActivity.this.info.about.length() <= 0) {
                                intent.putExtra("android.intent.extra.TEXT", ProfileActivity.this.currentChat.title + "\nhttps://" + MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
                            } else {
                                intent.putExtra("android.intent.extra.TEXT", ProfileActivity.this.currentChat.title + LogCollector.LINE_SEPARATOR + ProfileActivity.this.info.about + "\nhttps://" + MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
                            }
                            ProfileActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("BotShare", R.string.BotShare)), 500);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    } else if (position == ProfileActivity.this.leaveChannelRow) {
                        ProfileActivity.this.leaveChatPressed();
                    } else if (position == ProfileActivity.this.membersRow) {
                        args = new Bundle();
                        args.putInt("chat_id", ProfileActivity.this.chat_id);
                        args.putInt(Param.TYPE, 2);
                        ProfileActivity.this.presentFragment(new ChannelUsersActivity(args));
                    } else if (position == ProfileActivity.this.convertRow) {
                        builder = new Builder(ProfileActivity.this.getParentActivity());
                        builder.setMessage(LocaleController.getString("ConvertGroupAlert", R.string.ConvertGroupAlert));
                        builder.setTitle(LocaleController.getString("ConvertGroupAlertWarning", R.string.ConvertGroupAlertWarning));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C32753());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder.create());
                    } else {
                        ProfileActivity.this.processOnClickOrPress(position);
                    }
                }
            }
        });
        this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemClick(View view, int position) {
                if (position <= ProfileActivity.this.emptyRowChat2 || position >= ProfileActivity.this.membersEndRow) {
                    return ProfileActivity.this.processOnClickOrPress(position);
                }
                if (ProfileActivity.this.getParentActivity() == null) {
                    return false;
                }
                TLRPC$ChatParticipant user;
                TLRPC$ChannelParticipant channelParticipant;
                boolean allowKick = false;
                boolean allowSetAdmin = false;
                boolean canEditAdmin = false;
                if (ProfileActivity.this.sortedUsers.isEmpty()) {
                    user = (TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get((position - ProfileActivity.this.emptyRowChat2) - 1);
                } else {
                    user = (TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((position - ProfileActivity.this.emptyRowChat2) - 1)).intValue());
                }
                ProfileActivity.this.selectedUser = user.user_id;
                if (ChatObject.isChannel(ProfileActivity.this.currentChat)) {
                    channelParticipant = ((TLRPC$TL_chatChannelParticipant) user).channelParticipant;
                    if (user.user_id == UserConfig.getClientUserId()) {
                        return false;
                    }
                    User u = MessagesController.getInstance().getUser(Integer.valueOf(user.user_id));
                    allowSetAdmin = (channelParticipant instanceof TLRPC$TL_channelParticipant) || (channelParticipant instanceof TLRPC$TL_channelParticipantBanned);
                    if (((channelParticipant instanceof TLRPC$TL_channelParticipantAdmin) || (channelParticipant instanceof TLRPC$TL_channelParticipantCreator)) && !channelParticipant.can_edit) {
                        canEditAdmin = false;
                    } else {
                        canEditAdmin = true;
                    }
                } else {
                    channelParticipant = null;
                    if (user.user_id != UserConfig.getClientUserId()) {
                        if (ProfileActivity.this.currentChat.creator) {
                            allowKick = true;
                        } else if ((user instanceof TLRPC$TL_chatParticipant) && ((ProfileActivity.this.currentChat.admin && ProfileActivity.this.currentChat.admins_enabled) || user.inviter_id == UserConfig.getClientUserId())) {
                            allowKick = true;
                        }
                    }
                    if (!allowKick) {
                        return false;
                    }
                }
                Builder builder = new Builder(ProfileActivity.this.getParentActivity());
                ArrayList<String> items = new ArrayList();
                final ArrayList<Integer> actions = new ArrayList();
                if (ProfileActivity.this.currentChat.megagroup) {
                    if (allowSetAdmin && ChatObject.canAddAdmins(ProfileActivity.this.currentChat)) {
                        items.add(LocaleController.getString("SetAsAdmin", R.string.SetAsAdmin));
                        actions.add(Integer.valueOf(0));
                    }
                    if (ChatObject.canBlockUsers(ProfileActivity.this.currentChat) && canEditAdmin) {
                        items.add(LocaleController.getString("KickFromSupergroup", R.string.KickFromSupergroup));
                        actions.add(Integer.valueOf(1));
                        items.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
                        actions.add(Integer.valueOf(2));
                    }
                } else {
                    items.add(ProfileActivity.this.chat_id > 0 ? LocaleController.getString("KickFromGroup", R.string.KickFromGroup) : LocaleController.getString("KickFromBroadcast", R.string.KickFromBroadcast));
                    actions.add(Integer.valueOf(2));
                }
                if (items.isEmpty()) {
                    return false;
                }
                builder.setItems((CharSequence[]) items.toArray(new CharSequence[items.size()]), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        if (((Integer) actions.get(i)).intValue() == 2) {
                            ProfileActivity.this.kickUser(ProfileActivity.this.selectedUser);
                            return;
                        }
                        ChannelRightsEditActivity fragment = new ChannelRightsEditActivity(user.user_id, ProfileActivity.this.chat_id, channelParticipant.admin_rights, channelParticipant.banned_rights, ((Integer) actions.get(i)).intValue(), true);
                        fragment.setDelegate(new ChannelRightsEditActivityDelegate() {
                            public void didSetRights(int rights, TLRPC$TL_channelAdminRights rightsAdmin, TLRPC$TL_channelBannedRights rightsBanned) {
                                if (((Integer) actions.get(i)).intValue() == 0) {
                                    TLRPC$TL_chatChannelParticipant channelParticipant = user;
                                    if (rights == 1) {
                                        channelParticipant.channelParticipant = new TLRPC$TL_channelParticipantAdmin();
                                    } else {
                                        channelParticipant.channelParticipant = new TLRPC$TL_channelParticipant();
                                    }
                                    channelParticipant.channelParticipant.inviter_id = UserConfig.getClientUserId();
                                    channelParticipant.channelParticipant.user_id = user.user_id;
                                    channelParticipant.channelParticipant.date = user.date;
                                    channelParticipant.channelParticipant.banned_rights = rightsBanned;
                                    channelParticipant.channelParticipant.admin_rights = rightsAdmin;
                                } else if (((Integer) actions.get(i)).intValue() == 1 && rights == 0 && ProfileActivity.this.currentChat.megagroup && ProfileActivity.this.info != null && ProfileActivity.this.info.participants != null) {
                                    int a;
                                    boolean changed = false;
                                    for (a = 0; a < ProfileActivity.this.info.participants.participants.size(); a++) {
                                        if (((TLRPC$TL_chatChannelParticipant) ProfileActivity.this.info.participants.participants.get(a)).channelParticipant.user_id == user.user_id) {
                                            if (ProfileActivity.this.info != null) {
                                                TLRPC$ChatFull access$900 = ProfileActivity.this.info;
                                                access$900.participants_count--;
                                            }
                                            ProfileActivity.this.info.participants.participants.remove(a);
                                            changed = true;
                                            if (ProfileActivity.this.info != null && ProfileActivity.this.info.participants != null) {
                                                for (a = 0; a < ProfileActivity.this.info.participants.participants.size(); a++) {
                                                    if (((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(a)).user_id == user.user_id) {
                                                        ProfileActivity.this.info.participants.participants.remove(a);
                                                        changed = true;
                                                        break;
                                                    }
                                                }
                                            }
                                            if (changed) {
                                                ProfileActivity.this.updateOnlineCount();
                                                ProfileActivity.this.updateRowsIds();
                                                ProfileActivity.this.listAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    for (a = 0; a < ProfileActivity.this.info.participants.participants.size(); a++) {
                                        if (((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(a)).user_id == user.user_id) {
                                            ProfileActivity.this.info.participants.participants.remove(a);
                                            changed = true;
                                            break;
                                        }
                                    }
                                    if (changed) {
                                        ProfileActivity.this.updateOnlineCount();
                                        ProfileActivity.this.updateRowsIds();
                                        ProfileActivity.this.listAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        ProfileActivity.this.presentFragment(fragment);
                    }
                });
                ProfileActivity.this.showDialog(builder.create());
                return true;
            }
        });
        if (this.banFromGroup != 0) {
            if (this.currentChannelParticipant == null) {
                TLObject req = new TLRPC$TL_channels_getParticipant();
                req.channel = MessagesController.getInputChannel(this.banFromGroup);
                req.user_id = MessagesController.getInputUser(this.user_id);
                ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                    public void run(final TLObject response, TLRPC$TL_error error) {
                        if (response != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    ProfileActivity.this.currentChannelParticipant = ((TLRPC$TL_channels_channelParticipant) response).participant;
                                }
                            });
                        }
                    }
                });
            }
            FrameLayout frameLayout1 = new FrameLayout(context) {
                protected void onDraw(Canvas canvas) {
                    int bottom = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                    Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), bottom);
                    Theme.chat_composeShadowDrawable.draw(canvas);
                    canvas.drawRect(0.0f, (float) bottom, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
                }
            };
            frameLayout1.setWillNotDraw(false);
            frameLayout.addView(frameLayout1, LayoutHelper.createFrame(-1, 51, 83));
            frameLayout1.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ProfileActivity$16$1 */
                class C32791 implements ChannelRightsEditActivityDelegate {
                    C32791() {
                    }

                    public void didSetRights(int rights, TLRPC$TL_channelAdminRights rightsAdmin, TLRPC$TL_channelBannedRights rightsBanned) {
                        ProfileActivity.this.removeSelfFromStack();
                    }
                }

                public void onClick(View v) {
                    TLRPC$TL_channelBannedRights tLRPC$TL_channelBannedRights;
                    int access$000 = ProfileActivity.this.user_id;
                    int access$3700 = ProfileActivity.this.banFromGroup;
                    if (ProfileActivity.this.currentChannelParticipant != null) {
                        tLRPC$TL_channelBannedRights = ProfileActivity.this.currentChannelParticipant.banned_rights;
                    } else {
                        tLRPC$TL_channelBannedRights = null;
                    }
                    ChannelRightsEditActivity fragment = new ChannelRightsEditActivity(access$000, access$3700, null, tLRPC$TL_channelBannedRights, 1, true);
                    fragment.setDelegate(new C32791());
                    ProfileActivity.this.presentFragment(fragment);
                }
            });
            View textView = new TextView(context);
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
            textView.setTextSize(1, 15.0f);
            textView.setGravity(17);
            textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            textView.setText(LocaleController.getString("BanFromTheGroup", R.string.BanFromTheGroup));
            frameLayout1.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, 0.0f, 1.0f, 0.0f, 0.0f));
            this.listView.setPadding(0, AndroidUtilities.dp(88.0f), 0, AndroidUtilities.dp(48.0f));
            this.listView.setBottomGlowOffset(AndroidUtilities.dp(48.0f));
        } else {
            this.listView.setPadding(0, AndroidUtilities.dp(88.0f), 0, 0);
        }
        this.topView = new TopView(context);
        TopView topView = this.topView;
        if (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) {
            i = 5;
        } else {
            i = this.chat_id;
        }
        topView.setBackgroundColor(AvatarDrawable.getProfileBackColorForId(i));
        frameLayout.addView(this.topView);
        frameLayout.addView(this.actionBar);
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarImage.setPivotX(0.0f);
        this.avatarImage.setPivotY(0.0f);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(42, 42.0f, 51, 64.0f, 0.0f, 0.0f, 0.0f));
        this.avatarImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (ProfileActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (user.photo != null && user.photo.photo_big != null) {
                        PhotoViewer.getInstance().setParentActivity(ProfileActivity.this.getParentActivity());
                        PhotoViewer.getInstance().openPhoto(user.photo.photo_big, ProfileActivity.this.provider);
                    }
                } else if (ProfileActivity.this.chat_id != 0) {
                    TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                    if (chat.photo != null && chat.photo.photo_big != null) {
                        PhotoViewer.getInstance().setParentActivity(ProfileActivity.this.getParentActivity());
                        PhotoViewer.getInstance().openPhoto(chat.photo.photo_big, ProfileActivity.this.provider);
                    }
                }
            }
        });
        int a = 0;
        while (a < 2) {
            if (this.playProfileAnimation || a != 0) {
                float f;
                this.nameTextView[a] = new SimpleTextView(context);
                if (a == 1) {
                    this.nameTextView[a].setTextColor(Theme.getColor(Theme.key_profile_title));
                } else {
                    this.nameTextView[a].setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
                }
                this.nameTextView[a].setTextSize(18);
                this.nameTextView[a].setGravity(3);
                this.nameTextView[a].setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.nameTextView[a].setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
                this.nameTextView[a].setPivotX(0.0f);
                this.nameTextView[a].setPivotY(0.0f);
                this.nameTextView[a].setAlpha(a == 0 ? 0.0f : 1.0f);
                frameLayout.addView(this.nameTextView[a], LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, 0.0f, a == 0 ? 48.0f : 0.0f, 0.0f));
                this.onlineTextView[a] = new SimpleTextView(context);
                SimpleTextView simpleTextView = this.onlineTextView[a];
                i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
                simpleTextView.setTextColor(AvatarDrawable.getProfileTextColorForId(i));
                this.onlineTextView[a].setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.onlineTextView[a].setTextSize(14);
                this.onlineTextView[a].setGravity(3);
                this.onlineTextView[a].setAlpha(a == 0 ? 0.0f : 1.0f);
                View view = this.onlineTextView[a];
                if (a == 0) {
                    f = 48.0f;
                } else {
                    f = 8.0f;
                }
                frameLayout.addView(view, LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, 0.0f, f, 0.0f));
            }
            a++;
        }
        if (this.user_id != 0 || (this.chat_id >= 0 && (!ChatObject.isLeftFromChat(this.currentChat) || ChatObject.isChannel(this.currentChat)))) {
            this.writeButton = new ImageView(context);
            Drawable drawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_profile_actionBackground), Theme.getColor(Theme.key_profile_actionPressedBackground));
            if (VERSION.SDK_INT < 21) {
                Drawable shadowDrawable = context.getResources().getDrawable(R.drawable.floating_shadow_profile).mutate();
                shadowDrawable.setColorFilter(new PorterDuffColorFilter(-16777216, Mode.MULTIPLY));
                Drawable combinedDrawable = new CombinedDrawable(shadowDrawable, drawable, 0, 0);
                combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                drawable = combinedDrawable;
            }
            this.writeButton.setBackgroundDrawable(drawable);
            this.writeButton.setScaleType(ScaleType.CENTER);
            this.writeButton.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_profile_actionIcon), Mode.MULTIPLY));
            if (this.user_id != 0) {
                this.writeButton.setImageResource(R.drawable.floating_message);
                this.writeButton.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
            } else if (this.chat_id != 0) {
                boolean isChannel = ChatObject.isChannel(this.currentChat);
                if ((!isChannel || ChatObject.canEditInfo(this.currentChat)) && (isChannel || this.currentChat.admin || this.currentChat.creator || !this.currentChat.admins_enabled)) {
                    this.writeButton.setImageResource(R.drawable.floating_camera);
                } else {
                    this.writeButton.setImageResource(R.drawable.floating_message);
                    this.writeButton.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
                }
            }
            frameLayout.addView(this.writeButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, 53, 0.0f, 0.0f, 16.0f, 0.0f));
            if (VERSION.SDK_INT >= 21) {
                StateListAnimator animator = new StateListAnimator();
                animator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
                animator.addState(new int[0], ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
                this.writeButton.setStateListAnimator(animator);
                this.writeButton.setOutlineProvider(new ViewOutlineProvider() {
                    @SuppressLint({"NewApi"})
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                    }
                });
            }
            this.writeButton.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ProfileActivity$19$1 */
                class C32801 implements OnClickListener {
                    C32801() {
                    }

                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            ProfileActivity.this.avatarUpdater.openCamera();
                        } else if (i == 1) {
                            ProfileActivity.this.avatarUpdater.openGallery();
                        } else if (i == 2) {
                            MessagesController.getInstance().changeChatAvatar(ProfileActivity.this.chat_id, null);
                        }
                    }
                }

                public void onClick(View v) {
                    if (ProfileActivity.this.getParentActivity() != null) {
                        Bundle args;
                        if (ProfileActivity.this.user_id != 0) {
                            if (ProfileActivity.this.playProfileAnimation && (ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2) instanceof ChatActivity)) {
                                ProfileActivity.this.finishFragment();
                                return;
                            }
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                            if (user != null && !(user instanceof TLRPC$TL_userEmpty)) {
                                args = new Bundle();
                                args.putInt("user_id", ProfileActivity.this.user_id);
                                if (MessagesController.checkCanOpenChat(args, ProfileActivity.this)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    ProfileActivity.this.presentFragment(new ChatActivity(args), true);
                                }
                            }
                        } else if (ProfileActivity.this.chat_id != 0) {
                            boolean isChannel = ChatObject.isChannel(ProfileActivity.this.currentChat);
                            if ((!isChannel || ChatObject.canEditInfo(ProfileActivity.this.currentChat)) && (isChannel || ProfileActivity.this.currentChat.admin || ProfileActivity.this.currentChat.creator || !ProfileActivity.this.currentChat.admins_enabled)) {
                                Builder builder = new Builder(ProfileActivity.this.getParentActivity());
                                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                                CharSequence[] items = (chat.photo == null || chat.photo.photo_big == null || (chat.photo instanceof TLRPC$TL_chatPhotoEmpty)) ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)};
                                builder.setItems(items, new C32801());
                                ProfileActivity.this.showDialog(builder.create());
                            } else if (ProfileActivity.this.playProfileAnimation && (ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2) instanceof ChatActivity)) {
                                ProfileActivity.this.finishFragment();
                            } else {
                                args = new Bundle();
                                args.putInt("chat_id", ProfileActivity.this.currentChat.id);
                                if (MessagesController.checkCanOpenChat(args, ProfileActivity.this)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    ProfileActivity.this.presentFragment(new ChatActivity(args), true);
                                }
                            }
                        }
                    }
                }
            });
        }
        needLayout();
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                ProfileActivity.this.checkListViewScroll();
                if (ProfileActivity.this.participantsMap != null && ProfileActivity.this.loadMoreMembersRow != -1 && ProfileActivity.this.layoutManager.findLastVisibleItemPosition() > ProfileActivity.this.loadMoreMembersRow - 8) {
                    ProfileActivity.this.getChannelParticipants(false);
                }
            }
        });
        return this.fragmentView;
    }

    private boolean processOnClickOrPress(final int position) {
        User user;
        Builder builder;
        if (position == this.usernameRow || position == this.channelNameRow) {
            String username;
            if (position == this.usernameRow) {
                user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                if (user == null || user.username == null) {
                    return false;
                }
                username = user.username;
            } else {
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat == null || chat.username == null) {
                    return false;
                }
                username = chat.username;
            }
            builder = new Builder(getParentActivity());
            builder.setItems(new CharSequence[]{LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", "@" + username));
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            showDialog(builder.create());
            return true;
        } else if (position == this.phoneRow) {
            user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            if (user == null || user.phone == null || user.phone.length() == 0 || getParentActivity() == null) {
                return false;
            }
            builder = new Builder(getParentActivity());
            ArrayList<CharSequence> items = new ArrayList();
            final ArrayList<Integer> actions = new ArrayList();
            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(user.id);
            if (AppPreferences.isCallEnable() && userFull != null && userFull.phone_calls_available) {
                items.add(LocaleController.getString("CallViaTelegram", R.string.CallViaTelegram));
                actions.add(Integer.valueOf(2));
            }
            items.add(LocaleController.getString("Call", R.string.Call));
            actions.add(Integer.valueOf(0));
            items.add(LocaleController.getString("Copy", R.string.Copy));
            actions.add(Integer.valueOf(1));
            builder.setItems((CharSequence[]) items.toArray(new CharSequence[items.size()]), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    i = ((Integer) actions.get(i)).intValue();
                    if (i == 0) {
                        try {
                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:+" + user.phone));
                            intent.addFlags(268435456);
                            ProfileActivity.this.getParentActivity().startActivityForResult(intent, 500);
                        } catch (Exception e) {
                            FileLog.e(e);
                        }
                    } else if (i == 1) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", "+" + user.phone));
                        } catch (Exception e2) {
                            FileLog.e(e2);
                        }
                    } else if (i == 2) {
                        VoIPHelper.startCall(user, ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(user.id));
                    }
                }
            });
            showDialog(builder.create());
            return true;
        } else if (position != this.channelInfoRow && position != this.userInfoRow && position != this.userInfoDetailedRow) {
            return false;
        } else {
            builder = new Builder(getParentActivity());
            builder.setItems(new CharSequence[]{LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        String about;
                        if (position == ProfileActivity.this.channelInfoRow) {
                            about = ProfileActivity.this.info.about;
                        } else {
                            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                            about = userFull != null ? userFull.about : null;
                        }
                        if (!TextUtils.isEmpty(about)) {
                            AndroidUtilities.addToClipboard(about);
                        }
                    } catch (Exception e) {
                        FileLog.e(e);
                    }
                }
            });
            showDialog(builder.create());
            return true;
        }
    }

    private void leaveChatPressed() {
        Builder builder = new Builder(getParentActivity());
        if (!ChatObject.isChannel(this.chat_id) || this.currentChat.megagroup) {
            builder.setMessage(LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
        } else {
            builder.setMessage(ChatObject.isChannel(this.chat_id) ? LocaleController.getString("ChannelLeaveAlert", R.string.ChannelLeaveAlert) : LocaleController.getString("AreYouSureDeleteAndExit", R.string.AreYouSureDeleteAndExit));
        }
        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ProfileActivity.this.kickUser(0);
            }
        });
        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
        showDialog(builder.create());
    }

    public void saveSelfArgs(Bundle args) {
        if (this.chat_id != 0 && this.avatarUpdater != null && this.avatarUpdater.currentPicturePath != null) {
            args.putString("path", this.avatarUpdater.currentPicturePath);
        }
    }

    public void restoreSelfArgs(Bundle args) {
        if (this.chat_id != 0) {
            MessagesController.getInstance().loadChatInfo(this.chat_id, null, false);
            if (this.avatarUpdater != null) {
                this.avatarUpdater.currentPicturePath = args.getString("path");
            }
        }
    }

    public void onActivityResultFragment(int requestCode, int resultCode, Intent data) {
        if (this.chat_id != 0) {
            this.avatarUpdater.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void getChannelParticipants(boolean reload) {
        int i = 0;
        if (!this.loadingUsers && this.participantsMap != null && this.info != null) {
            int delay;
            this.loadingUsers = true;
            if (this.participantsMap.isEmpty() || !reload) {
                delay = 0;
            } else {
                delay = ScheduleDownloadActivity.CHECK_CELL2;
            }
            final TLRPC$TL_channels_getParticipants req = new TLRPC$TL_channels_getParticipants();
            req.channel = MessagesController.getInputChannel(this.chat_id);
            req.filter = new TLRPC$TL_channelParticipantsRecent();
            if (!reload) {
                i = this.participantsMap.size();
            }
            req.offset = i;
            req.limit = 200;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new RequestDelegate() {
                public void run(final TLObject response, final TLRPC$TL_error error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (error == null) {
                                TLRPC$TL_channels_channelParticipants res = response;
                                MessagesController.getInstance().putUsers(res.users, false);
                                if (res.users.size() != 200) {
                                    ProfileActivity.this.usersEndReached = true;
                                }
                                if (req.offset == 0) {
                                    ProfileActivity.this.participantsMap.clear();
                                    ProfileActivity.this.info.participants = new TLRPC$TL_chatParticipants();
                                    MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
                                    MessagesStorage.getInstance().updateChannelUsers(ProfileActivity.this.chat_id, res.participants);
                                }
                                for (int a = 0; a < res.participants.size(); a++) {
                                    TLRPC$TL_chatChannelParticipant participant = new TLRPC$TL_chatChannelParticipant();
                                    participant.channelParticipant = (TLRPC$ChannelParticipant) res.participants.get(a);
                                    participant.inviter_id = participant.channelParticipant.inviter_id;
                                    participant.user_id = participant.channelParticipant.user_id;
                                    participant.date = participant.channelParticipant.date;
                                    if (!ProfileActivity.this.participantsMap.containsKey(Integer.valueOf(participant.user_id))) {
                                        ProfileActivity.this.info.participants.participants.add(participant);
                                        ProfileActivity.this.participantsMap.put(Integer.valueOf(participant.user_id), participant);
                                    }
                                }
                            }
                            ProfileActivity.this.updateOnlineCount();
                            ProfileActivity.this.loadingUsers = false;
                            ProfileActivity.this.updateRowsIds();
                            if (ProfileActivity.this.listAdapter != null) {
                                ProfileActivity.this.listAdapter.notifyDataSetChanged();
                            }
                        }
                    }, (long) delay);
                }
            }), this.classGuid);
        }
    }

    private void openAddMember() {
        boolean z = true;
        Bundle args = new Bundle();
        args.putBoolean("onlyUsers", true);
        args.putBoolean("destroyAfterSelect", true);
        args.putBoolean("returnAsResult", true);
        String str = "needForwardCount";
        if (ChatObject.isChannel(this.currentChat)) {
            z = false;
        }
        args.putBoolean(str, z);
        if (this.chat_id > 0) {
            if (ChatObject.canAddViaLink(this.currentChat)) {
                args.putInt("chat_id", this.currentChat.id);
            }
            args.putString("selectAlertString", LocaleController.getString("AddToTheGroup", R.string.AddToTheGroup));
        }
        ContactsActivity fragment = new ContactsActivity(args);
        fragment.setDelegate(new ContactsActivityDelegate() {
            public void didSelectContact(User user, String param, ContactsActivity activity) {
                MessagesController.getInstance().addUserToChat(ProfileActivity.this.chat_id, user, ProfileActivity.this.info, param != null ? Utilities.parseInt(param).intValue() : 0, null, ProfileActivity.this);
            }
        });
        if (!(this.info == null || this.info.participants == null)) {
            HashMap<Integer, User> users = new HashMap();
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                users.put(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id), null);
            }
            fragment.setIgnoreUsers(users);
        }
        presentFragment(fragment);
    }

    private void checkListViewScroll() {
        boolean z = false;
        if (this.listView.getChildCount() > 0 && !this.openAnimationInProgress) {
            View child = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(child);
            int top = child.getTop();
            int newOffset = 0;
            if (top >= 0 && holder != null && holder.getAdapterPosition() == 0) {
                newOffset = top;
            }
            if (this.extraHeight != newOffset) {
                this.extraHeight = newOffset;
                this.topView.invalidate();
                if (this.playProfileAnimation) {
                    if (this.extraHeight != 0) {
                        z = true;
                    }
                    this.allowProfileAnimation = z;
                }
                needLayout();
            }
        }
    }

    private void needLayout() {
        FrameLayout.LayoutParams layoutParams;
        int newTop = (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight();
        if (!(this.listView == null || this.openAnimationInProgress)) {
            layoutParams = (FrameLayout.LayoutParams) this.listView.getLayoutParams();
            if (layoutParams.topMargin != newTop) {
                layoutParams.topMargin = newTop;
                this.listView.setLayoutParams(layoutParams);
            }
        }
        if (this.avatarImage != null) {
            float diff = ((float) this.extraHeight) / ((float) AndroidUtilities.dp(88.0f));
            this.listView.setTopGlowOffset(this.extraHeight);
            if (this.writeButton != null) {
                this.writeButton.setTranslationY((float) ((((this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight()) + this.extraHeight) - AndroidUtilities.dp(29.5f)));
                if (!this.openAnimationInProgress) {
                    boolean setVisible = diff > 0.2f;
                    if (setVisible != (this.writeButton.getTag() == null)) {
                        if (setVisible) {
                            this.writeButton.setTag(null);
                        } else {
                            this.writeButton.setTag(Integer.valueOf(0));
                        }
                        if (this.writeButtonAnimation != null) {
                            AnimatorSet old = this.writeButtonAnimation;
                            this.writeButtonAnimation = null;
                            old.cancel();
                        }
                        this.writeButtonAnimation = new AnimatorSet();
                        AnimatorSet animatorSet;
                        Animator[] animatorArr;
                        if (setVisible) {
                            this.writeButtonAnimation.setInterpolator(new DecelerateInterpolator());
                            animatorSet = this.writeButtonAnimation;
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{1.0f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{1.0f});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{1.0f});
                            animatorSet.playTogether(animatorArr);
                        } else {
                            this.writeButtonAnimation.setInterpolator(new AccelerateInterpolator());
                            animatorSet = this.writeButtonAnimation;
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{0.2f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{0.2f});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{0.0f});
                            animatorSet.playTogether(animatorArr);
                        }
                        this.writeButtonAnimation.setDuration(150);
                        this.writeButtonAnimation.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animation) {
                                if (ProfileActivity.this.writeButtonAnimation != null && ProfileActivity.this.writeButtonAnimation.equals(animation)) {
                                    ProfileActivity.this.writeButtonAnimation = null;
                                }
                            }
                        });
                        this.writeButtonAnimation.start();
                    }
                }
            }
            float avatarY = ((((float) (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0)) + ((((float) ActionBar.getCurrentActionBarHeight()) / 2.0f) * (1.0f + diff))) - (21.0f * AndroidUtilities.density)) + ((27.0f * AndroidUtilities.density) * diff);
            this.avatarImage.setScaleX((42.0f + (18.0f * diff)) / 42.0f);
            this.avatarImage.setScaleY((42.0f + (18.0f * diff)) / 42.0f);
            this.avatarImage.setTranslationX(((float) (-AndroidUtilities.dp(47.0f))) * diff);
            this.avatarImage.setTranslationY((float) Math.ceil((double) avatarY));
            for (int a = 0; a < 2; a++) {
                if (this.nameTextView[a] != null) {
                    this.nameTextView[a].setTranslationX((-21.0f * AndroidUtilities.density) * diff);
                    this.nameTextView[a].setTranslationY((((float) Math.floor((double) avatarY)) + ((float) AndroidUtilities.dp(1.3f))) + (((float) AndroidUtilities.dp(7.0f)) * diff));
                    this.onlineTextView[a].setTranslationX((-21.0f * AndroidUtilities.density) * diff);
                    this.onlineTextView[a].setTranslationY((((float) Math.floor((double) avatarY)) + ((float) AndroidUtilities.dp(24.0f))) + (((float) Math.floor((double) (11.0f * AndroidUtilities.density))) * diff));
                    this.nameTextView[a].setScaleX(1.0f + (0.12f * diff));
                    this.nameTextView[a].setScaleY(1.0f + (0.12f * diff));
                    if (a == 1 && !this.openAnimationInProgress) {
                        int width;
                        if (AndroidUtilities.isTablet()) {
                            width = AndroidUtilities.dp(490.0f);
                        } else {
                            width = AndroidUtilities.displaySize.x;
                        }
                        int i = (this.callItem == null && this.editItem == null) ? 0 : 48;
                        width = (int) (((float) (width - AndroidUtilities.dp((((float) (i + 40)) * (1.0f - diff)) + 126.0f))) - this.nameTextView[a].getTranslationX());
                        layoutParams = (FrameLayout.LayoutParams) this.nameTextView[a].getLayoutParams();
                        if (((float) width) < (this.nameTextView[a].getPaint().measureText(this.nameTextView[a].getText().toString()) * this.nameTextView[a].getScaleX()) + ((float) this.nameTextView[a].getSideDrawablesSize())) {
                            layoutParams.width = (int) Math.ceil((double) (((float) width) / this.nameTextView[a].getScaleX()));
                        } else {
                            layoutParams.width = -2;
                        }
                        this.nameTextView[a].setLayoutParams(layoutParams);
                        layoutParams = (FrameLayout.LayoutParams) this.onlineTextView[a].getLayoutParams();
                        layoutParams.rightMargin = (int) Math.ceil((double) ((this.onlineTextView[a].getTranslationX() + ((float) AndroidUtilities.dp(8.0f))) + (((float) AndroidUtilities.dp(40.0f)) * (1.0f - diff))));
                        this.onlineTextView[a].setLayoutParams(layoutParams);
                    }
                }
            }
        }
    }

    private void fixLayout() {
        if (this.fragmentView != null) {
            this.fragmentView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() {
                    if (ProfileActivity.this.fragmentView != null) {
                        ProfileActivity.this.checkListViewScroll();
                        ProfileActivity.this.needLayout();
                        ProfileActivity.this.fragmentView.getViewTreeObserver().removeOnPreDrawListener(this);
                    }
                    return true;
                }
            });
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fixLayout();
    }

    public void didReceivedNotification(int id, Object... args) {
        ViewHolder holder;
        TLRPC$Chat newChat;
        int count;
        int a;
        if (id == NotificationCenter.updateInterfaces) {
            int mask = ((Integer) args[0]).intValue();
            if (this.user_id != 0) {
                if (!((mask & 2) == 0 && (mask & 1) == 0 && (mask & 4) == 0)) {
                    updateProfileData();
                }
                if ((mask & 1024) != 0 && this.listView != null) {
                    holder = (Holder) this.listView.findViewHolderForPosition(this.phoneRow);
                    if (holder != null) {
                        this.listAdapter.onBindViewHolder(holder, this.phoneRow);
                    }
                }
            } else if (this.chat_id != 0) {
                if ((mask & 16384) != 0) {
                    newChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                    if (newChat != null) {
                        this.currentChat = newChat;
                        createActionBarMenu();
                        updateRowsIds();
                        if (this.listAdapter != null) {
                            this.listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                if (!((mask & 8192) == 0 && (mask & 8) == 0 && (mask & 16) == 0 && (mask & 32) == 0 && (mask & 4) == 0)) {
                    updateOnlineCount();
                    updateProfileData();
                }
                if ((mask & 8192) != 0) {
                    updateRowsIds();
                    if (this.listAdapter != null) {
                        this.listAdapter.notifyDataSetChanged();
                    }
                }
                if (((mask & 2) != 0 || (mask & 1) != 0 || (mask & 4) != 0) && this.listView != null) {
                    count = this.listView.getChildCount();
                    for (a = 0; a < count; a++) {
                        View child = this.listView.getChildAt(a);
                        if (child instanceof UserCell) {
                            ((UserCell) child).update(mask);
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.contactsDidLoaded) {
            createActionBarMenu();
        } else if (id == NotificationCenter.mediaCountDidLoaded) {
            long uid = ((Long) args[0]).longValue();
            long did = this.dialog_id;
            if (did == 0) {
                if (this.user_id != 0) {
                    did = (long) this.user_id;
                } else if (this.chat_id != 0) {
                    did = (long) (-this.chat_id);
                }
            }
            if (uid == did || uid == this.mergeDialogId) {
                if (uid == did) {
                    this.totalMediaCount = ((Integer) args[1]).intValue();
                } else {
                    this.totalMediaCountMerge = ((Integer) args[1]).intValue();
                }
                if (this.listView != null) {
                    count = this.listView.getChildCount();
                    for (a = 0; a < count; a++) {
                        holder = (Holder) this.listView.getChildViewHolder(this.listView.getChildAt(a));
                        if (holder.getAdapterPosition() == this.sharedMediaRow) {
                            this.listAdapter.onBindViewHolder(holder, this.sharedMediaRow);
                            return;
                        }
                    }
                }
            }
        } else if (id == NotificationCenter.encryptedChatCreated) {
            if (this.creatingChat) {
                final Object[] objArr = args;
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        TLRPC$EncryptedChat encryptedChat = objArr[0];
                        Bundle args2 = new Bundle();
                        args2.putInt("enc_id", encryptedChat.id);
                        ProfileActivity.this.presentFragment(new ChatActivity(args2), true);
                    }
                });
            }
        } else if (id == NotificationCenter.encryptedChatUpdated) {
            TLRPC$EncryptedChat chat = args[0];
            if (this.currentEncryptedChat != null && chat.id == this.currentEncryptedChat.id) {
                this.currentEncryptedChat = chat;
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
            }
        } else if (id == NotificationCenter.blockedUsersDidLoaded) {
            boolean oldValue = this.userBlocked;
            this.userBlocked = MessagesController.getInstance().blockedUsers.contains(Integer.valueOf(this.user_id));
            if (oldValue != this.userBlocked) {
                createActionBarMenu();
            }
        } else if (id == NotificationCenter.chatInfoDidLoaded) {
            TLRPC$ChatFull chatFull = args[0];
            if (chatFull.id == this.chat_id) {
                boolean byChannelUsers = ((Boolean) args[2]).booleanValue();
                if ((this.info instanceof TLRPC$TL_channelFull) && chatFull.participants == null && this.info != null) {
                    chatFull.participants = this.info.participants;
                }
                boolean loadChannelParticipants = this.info == null && (chatFull instanceof TLRPC$TL_channelFull);
                this.info = chatFull;
                if (this.mergeDialogId == 0 && this.info.migrated_from_chat_id != 0) {
                    this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
                    SharedMediaQuery.getMediaCount(this.mergeDialogId, 0, this.classGuid, true);
                }
                fetchUsersFromChannelInfo();
                updateOnlineCount();
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
                newChat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (newChat != null) {
                    this.currentChat = newChat;
                    createActionBarMenu();
                }
                if (!this.currentChat.megagroup) {
                    return;
                }
                if (loadChannelParticipants || !byChannelUsers) {
                    getChannelParticipants(true);
                }
            }
        } else if (id == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (id == NotificationCenter.botInfoDidLoaded) {
            TLRPC$BotInfo info = args[0];
            if (info.user_id == this.user_id) {
                this.botInfo = info;
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
            }
        } else if (id == NotificationCenter.userInfoDidLoaded) {
            if (((Integer) args[0]).intValue() == this.user_id) {
                if (this.openAnimationInProgress || this.callItem != null) {
                    this.recreateMenuAfterAnimation = true;
                } else {
                    createActionBarMenu();
                }
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
            }
        } else if (id == NotificationCenter.didReceivedNewMessages && ((Long) args[0]).longValue() == this.dialog_id) {
            ArrayList<MessageObject> arr = args[1];
            for (a = 0; a < arr.size(); a++) {
                MessageObject obj = (MessageObject) arr.get(a);
                if (this.currentEncryptedChat != null && obj.messageOwner.action != null && (obj.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) && (obj.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) {
                    TLRPC$TL_decryptedMessageActionSetMessageTTL action = obj.messageOwner.action.encryptedAction;
                    if (this.listAdapter != null) {
                        this.listAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    public void onResume() {
        super.onResume();
        if (this.listAdapter != null) {
            this.listAdapter.notifyDataSetChanged();
        }
        updateProfileData();
        fixLayout();
        if (this.channelIsFilter) {
            try {
                showCantOpenAlert(this, AppPreferences.getFilterMessage(ApplicationLoader.applicationContext), true);
            } catch (Exception e) {
            }
        }
    }

    public void setPlayProfileAnimation(boolean value) {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        if (!AndroidUtilities.isTablet() && preferences.getBoolean("view_animations", true)) {
            this.playProfileAnimation = value;
        }
    }

    protected void onTransitionAnimationStart(boolean isOpen, boolean backward) {
        if (!backward && this.playProfileAnimation && this.allowProfileAnimation) {
            this.openAnimationInProgress = true;
        }
        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.mediaCountDidLoaded});
        NotificationCenter.getInstance().setAnimationInProgress(true);
    }

    protected void onTransitionAnimationEnd(boolean isOpen, boolean backward) {
        if (!backward && this.playProfileAnimation && this.allowProfileAnimation) {
            this.openAnimationInProgress = false;
            if (this.recreateMenuAfterAnimation) {
                createActionBarMenu();
            }
        }
        NotificationCenter.getInstance().setAnimationInProgress(false);
    }

    public float getAnimationProgress() {
        return this.animationProgress;
    }

    @Keep
    public void setAnimationProgress(float progress) {
        int i;
        int i2;
        this.animationProgress = progress;
        this.listView.setAlpha(progress);
        this.listView.setTranslationX(((float) AndroidUtilities.dp(48.0f)) - (((float) AndroidUtilities.dp(48.0f)) * progress));
        if (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) {
            i = 5;
        } else {
            i = this.chat_id;
        }
        int color = AvatarDrawable.getProfileBackColorForId(i);
        int actionBarColor = Theme.getColor(Theme.key_actionBarDefault);
        int r = Color.red(actionBarColor);
        int g = Color.green(actionBarColor);
        int b = Color.blue(actionBarColor);
        this.topView.setBackgroundColor(Color.rgb(r + ((int) (((float) (Color.red(color) - r)) * progress)), g + ((int) (((float) (Color.green(color) - g)) * progress)), b + ((int) (((float) (Color.blue(color) - b)) * progress))));
        if (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) {
            i = 5;
        } else {
            i = this.chat_id;
        }
        color = AvatarDrawable.getIconColorForId(i);
        int iconColor = Theme.getColor(Theme.key_actionBarDefaultIcon);
        r = Color.red(iconColor);
        g = Color.green(iconColor);
        b = Color.blue(iconColor);
        this.actionBar.setItemsColor(Color.rgb(r + ((int) (((float) (Color.red(color) - r)) * progress)), g + ((int) (((float) (Color.green(color) - g)) * progress)), b + ((int) (((float) (Color.blue(color) - b)) * progress))), false);
        color = Theme.getColor(Theme.key_profile_title);
        int titleColor = Theme.getColor(Theme.key_actionBarDefaultTitle);
        r = Color.red(titleColor);
        g = Color.green(titleColor);
        b = Color.blue(titleColor);
        int a = Color.alpha(titleColor);
        int rD = (int) (((float) (Color.red(color) - r)) * progress);
        int gD = (int) (((float) (Color.green(color) - g)) * progress);
        int bD = (int) (((float) (Color.blue(color) - b)) * progress);
        int aD = (int) (((float) (Color.alpha(color) - a)) * progress);
        for (i2 = 0; i2 < 2; i2++) {
            if (this.nameTextView[i2] != null) {
                this.nameTextView[i2].setTextColor(Color.argb(a + aD, r + rD, g + gD, b + bD));
            }
        }
        if (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) {
            i = 5;
        } else {
            i = this.chat_id;
        }
        color = AvatarDrawable.getProfileTextColorForId(i);
        int subtitleColor = Theme.getColor(Theme.key_actionBarDefaultSubtitle);
        r = Color.red(subtitleColor);
        g = Color.green(subtitleColor);
        b = Color.blue(subtitleColor);
        a = Color.alpha(subtitleColor);
        rD = (int) (((float) (Color.red(color) - r)) * progress);
        gD = (int) (((float) (Color.green(color) - g)) * progress);
        bD = (int) (((float) (Color.blue(color) - b)) * progress);
        aD = (int) (((float) (Color.alpha(color) - a)) * progress);
        for (i2 = 0; i2 < 2; i2++) {
            if (this.onlineTextView[i2] != null) {
                this.onlineTextView[i2].setTextColor(Color.argb(a + aD, r + rD, g + gD, b + bD));
            }
        }
        this.extraHeight = (int) (((float) this.initialAnimationExtraHeight) * progress);
        color = AvatarDrawable.getProfileColorForId(this.user_id != 0 ? this.user_id : this.chat_id);
        int color2 = AvatarDrawable.getColorForId(this.user_id != 0 ? this.user_id : this.chat_id);
        if (color != color2) {
            this.avatarDrawable.setColor(Color.rgb(Color.red(color2) + ((int) (((float) (Color.red(color) - Color.red(color2))) * progress)), Color.green(color2) + ((int) (((float) (Color.green(color) - Color.green(color2))) * progress)), Color.blue(color2) + ((int) (((float) (Color.blue(color) - Color.blue(color2))) * progress))));
            this.avatarImage.invalidate();
        }
        needLayout();
    }

    protected AnimatorSet onCustomTransitionAnimation(boolean isOpen, final Runnable callback) {
        if (!this.playProfileAnimation || !this.allowProfileAnimation) {
            return null;
        }
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        this.listView.setLayerType(2, null);
        ActionBarMenu menu = this.actionBar.createMenu();
        if (menu.getItem(10) == null && this.animatingItem == null) {
            this.animatingItem = menu.addItem(10, (int) R.drawable.ic_ab_other);
        }
        ArrayList<Animator> animators;
        int a;
        Object obj;
        String str;
        float[] fArr;
        if (isOpen) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.onlineTextView[1].getLayoutParams();
            layoutParams.rightMargin = (int) ((-21.0f * AndroidUtilities.density) + ((float) AndroidUtilities.dp(8.0f)));
            this.onlineTextView[1].setLayoutParams(layoutParams);
            int width = (int) Math.ceil((double) (((float) (AndroidUtilities.displaySize.x - AndroidUtilities.dp(126.0f))) + (21.0f * AndroidUtilities.density)));
            layoutParams = (FrameLayout.LayoutParams) this.nameTextView[1].getLayoutParams();
            if (((float) width) < (this.nameTextView[1].getPaint().measureText(this.nameTextView[1].getText().toString()) * 1.12f) + ((float) this.nameTextView[1].getSideDrawablesSize())) {
                layoutParams.width = (int) Math.ceil((double) (((float) width) / 1.12f));
            } else {
                layoutParams.width = -2;
            }
            this.nameTextView[1].setLayoutParams(layoutParams);
            this.initialAnimationExtraHeight = AndroidUtilities.dp(88.0f);
            this.fragmentView.setBackgroundColor(0);
            setAnimationProgress(0.0f);
            animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(this, "animationProgress", new float[]{0.0f, 1.0f}));
            if (this.writeButton != null) {
                this.writeButton.setScaleX(0.2f);
                this.writeButton.setScaleY(0.2f);
                this.writeButton.setAlpha(0.0f);
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{1.0f}));
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{1.0f}));
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{1.0f}));
            }
            a = 0;
            while (a < 2) {
                this.onlineTextView[a].setAlpha(a == 0 ? 1.0f : 0.0f);
                this.nameTextView[a].setAlpha(a == 0 ? 1.0f : 0.0f);
                obj = this.onlineTextView[a];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = a == 0 ? 0.0f : 1.0f;
                animators.add(ObjectAnimator.ofFloat(obj, str, fArr));
                obj = this.nameTextView[a];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = a == 0 ? 0.0f : 1.0f;
                animators.add(ObjectAnimator.ofFloat(obj, str, fArr));
                a++;
            }
            if (this.animatingItem != null) {
                this.animatingItem.setAlpha(1.0f);
                animators.add(ObjectAnimator.ofFloat(this.animatingItem, "alpha", new float[]{0.0f}));
            }
            if (this.callItem != null) {
                this.callItem.setAlpha(0.0f);
                animators.add(ObjectAnimator.ofFloat(this.callItem, "alpha", new float[]{1.0f}));
            }
            if (this.editItem != null) {
                this.editItem.setAlpha(0.0f);
                animators.add(ObjectAnimator.ofFloat(this.editItem, "alpha", new float[]{1.0f}));
            }
            animatorSet.playTogether(animators);
        } else {
            this.initialAnimationExtraHeight = this.extraHeight;
            animators = new ArrayList();
            animators.add(ObjectAnimator.ofFloat(this, "animationProgress", new float[]{1.0f, 0.0f}));
            if (this.writeButton != null) {
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{0.2f}));
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{0.2f}));
                animators.add(ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{0.0f}));
            }
            a = 0;
            while (a < 2) {
                obj = this.onlineTextView[a];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = a == 0 ? 1.0f : 0.0f;
                animators.add(ObjectAnimator.ofFloat(obj, str, fArr));
                obj = this.nameTextView[a];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = a == 0 ? 1.0f : 0.0f;
                animators.add(ObjectAnimator.ofFloat(obj, str, fArr));
                a++;
            }
            if (this.animatingItem != null) {
                this.animatingItem.setAlpha(0.0f);
                animators.add(ObjectAnimator.ofFloat(this.animatingItem, "alpha", new float[]{1.0f}));
            }
            if (this.callItem != null) {
                this.callItem.setAlpha(1.0f);
                animators.add(ObjectAnimator.ofFloat(this.callItem, "alpha", new float[]{0.0f}));
            }
            if (this.editItem != null) {
                this.editItem.setAlpha(1.0f);
                animators.add(ObjectAnimator.ofFloat(this.editItem, "alpha", new float[]{0.0f}));
            }
            animatorSet.playTogether(animators);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                ProfileActivity.this.listView.setLayerType(0, null);
                if (ProfileActivity.this.animatingItem != null) {
                    ProfileActivity.this.actionBar.createMenu().clearItems();
                    ProfileActivity.this.animatingItem = null;
                }
                callback.run();
            }
        });
        animatorSet.setInterpolator(new DecelerateInterpolator());
        AndroidUtilities.runOnUIThread(new Runnable() {
            public void run() {
                animatorSet.start();
            }
        }, 50);
        return animatorSet;
    }

    private void updateOnlineCount() {
        this.onlineCount = 0;
        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
        this.sortedUsers.clear();
        if ((this.info instanceof TLRPC$TL_chatFull) || ((this.info instanceof TLRPC$TL_channelFull) && this.info.participants_count <= 200 && this.info.participants != null)) {
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) this.info.participants.participants.get(a)).user_id));
                if (!(user == null || user.status == null || ((user.status.expires <= currentTime && user.id != UserConfig.getClientUserId()) || user.status.expires <= 10000))) {
                    this.onlineCount++;
                }
                this.sortedUsers.add(Integer.valueOf(a));
            }
            try {
                Collections.sort(this.sortedUsers, new Comparator<Integer>() {
                    public int compare(Integer lhs, Integer rhs) {
                        User user1 = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(rhs.intValue())).user_id));
                        User user2 = MessagesController.getInstance().getUser(Integer.valueOf(((TLRPC$ChatParticipant) ProfileActivity.this.info.participants.participants.get(lhs.intValue())).user_id));
                        int status1 = 0;
                        int status2 = 0;
                        if (!(user1 == null || user1.status == null)) {
                            status1 = user1.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user1.status.expires;
                        }
                        if (!(user2 == null || user2.status == null)) {
                            status2 = user2.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user2.status.expires;
                        }
                        if (status1 <= 0 || status2 <= 0) {
                            if (status1 >= 0 || status2 >= 0) {
                                if ((status1 < 0 && status2 > 0) || (status1 == 0 && status2 != 0)) {
                                    return -1;
                                }
                                if ((status2 >= 0 || status1 <= 0) && (status2 != 0 || status1 == 0)) {
                                    return 0;
                                }
                                return 1;
                            } else if (status1 > status2) {
                                return 1;
                            } else {
                                if (status1 < status2) {
                                    return -1;
                                }
                                return 0;
                            }
                        } else if (status1 > status2) {
                            return 1;
                        } else {
                            if (status1 < status2) {
                                return -1;
                            }
                            return 0;
                        }
                    }
                });
            } catch (Exception e) {
                FileLog.e(e);
            }
            if (this.listAdapter != null) {
                this.listAdapter.notifyItemRangeChanged(this.emptyRowChat2 + 1, this.sortedUsers.size());
            }
        }
    }

    public void setChatInfo(TLRPC$ChatFull chatInfo) {
        this.info = chatInfo;
        if (!(this.info == null || this.info.migrated_from_chat_id == 0)) {
            this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
        }
        fetchUsersFromChannelInfo();
    }

    private void fetchUsersFromChannelInfo() {
        if (this.currentChat != null && this.currentChat.megagroup && (this.info instanceof TLRPC$TL_channelFull) && this.info.participants != null) {
            for (int a = 0; a < this.info.participants.participants.size(); a++) {
                TLRPC$ChatParticipant chatParticipant = (TLRPC$ChatParticipant) this.info.participants.participants.get(a);
                this.participantsMap.put(Integer.valueOf(chatParticipant.user_id), chatParticipant);
            }
        }
    }

    private void kickUser(int uid) {
        if (uid != 0) {
            MessagesController.getInstance().deleteUserFromChat(this.chat_id, MessagesController.getInstance().getUser(Integer.valueOf(uid)), this.info);
            return;
        }
        NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
        if (AndroidUtilities.isTablet()) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[]{Long.valueOf(-((long) this.chat_id))});
        } else {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
        }
        MessagesController.getInstance().deleteUserFromChat(this.chat_id, MessagesController.getInstance().getUser(Integer.valueOf(UserConfig.getClientUserId())), this.info);
        this.playProfileAnimation = false;
        finishFragment();
    }

    public boolean isChat() {
        return this.chat_id != 0;
    }

    private void updateRowsIds() {
        boolean hasUsername = false;
        this.emptyRow = -1;
        this.phoneRow = -1;
        this.userInfoRow = -1;
        this.userInfoDetailedRow = -1;
        this.userSectionRow = -1;
        this.sectionRow = -1;
        this.sharedMediaRow = -1;
        this.settingsNotificationsRow = -1;
        this.usernameRow = -1;
        this.settingsTimerRow = -1;
        this.settingsKeyRow = -1;
        this.startSecretChatRow = -1;
        this.membersEndRow = -1;
        this.emptyRowChat2 = -1;
        this.addMemberRow = -1;
        this.channelInfoRow = -1;
        this.channelNameRow = -1;
        this.convertRow = -1;
        this.convertHelpRow = -1;
        this.emptyRowChat = -1;
        this.membersSectionRow = -1;
        this.membersRow = -1;
        this.leaveChannelRow = -1;
        this.loadMoreMembersRow = -1;
        this.groupsInCommonRow = -1;
        this.rowCount = 0;
        int i;
        if (this.user_id != 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            i = this.rowCount;
            this.rowCount = i + 1;
            this.emptyRow = i;
            if (!(this.isBot || TextUtils.isEmpty(user.phone))) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.phoneRow = i;
            }
            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(this.user_id);
            if (!(user == null || TextUtils.isEmpty(user.username))) {
                hasUsername = true;
            }
            if (!(userFull == null || TextUtils.isEmpty(userFull.about))) {
                if (this.phoneRow != -1) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.userSectionRow = i;
                }
                if (hasUsername || this.isBot) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.userInfoRow = i;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.userInfoDetailedRow = i;
                }
            }
            if (hasUsername) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.usernameRow = i;
            }
            if (!(this.phoneRow == -1 && this.userInfoRow == -1 && this.userInfoDetailedRow == -1 && this.usernameRow == -1)) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.sectionRow = i;
            }
            if (this.user_id != UserConfig.getClientUserId()) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.settingsNotificationsRow = i;
            }
            i = this.rowCount;
            this.rowCount = i + 1;
            this.sharedMediaRow = i;
            if (this.currentEncryptedChat instanceof TLRPC$TL_encryptedChat) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.settingsTimerRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.settingsKeyRow = i;
            }
            if (!(userFull == null || userFull.common_chats_count == 0)) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.groupsInCommonRow = i;
            }
            if (user != null && !this.isBot && this.currentEncryptedChat == null && user.id != UserConfig.getClientUserId()) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.startSecretChatRow = i;
            }
        } else if (this.chat_id == 0) {
        } else {
            if (this.chat_id > 0) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.emptyRow = i;
                if (ChatObject.isChannel(this.currentChat) && (!(this.info == null || this.info.about == null || this.info.about.length() <= 0) || (this.currentChat.username != null && this.currentChat.username.length() > 0))) {
                    if (!(this.info == null || this.info.about == null || this.info.about.length() <= 0)) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.channelInfoRow = i;
                    }
                    if (this.currentChat.username != null && this.currentChat.username.length() > 0) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.channelNameRow = i;
                    }
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.sectionRow = i;
                }
                i = this.rowCount;
                this.rowCount = i + 1;
                this.settingsNotificationsRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.sharedMediaRow = i;
                if (ChatObject.isChannel(this.currentChat)) {
                    if (!(this.currentChat.megagroup || this.info == null || (!this.currentChat.creator && !this.info.can_view_participants))) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.membersRow = i;
                    }
                    if (!(this.currentChat.creator || this.currentChat.left || this.currentChat.kicked || this.currentChat.megagroup)) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.leaveChannelRow = i;
                    }
                    if (this.currentChat.megagroup && (((this.currentChat.admin_rights != null && this.currentChat.admin_rights.invite_users) || this.currentChat.creator || this.currentChat.democracy) && (this.info == null || this.info.participants_count < MessagesController.getInstance().maxMegagroupCount))) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.addMemberRow = i;
                    }
                    if (this.info != null && this.currentChat.megagroup && this.info.participants != null && !this.info.participants.participants.isEmpty()) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.emptyRowChat = i;
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.membersSectionRow = i;
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.emptyRowChat2 = i;
                        this.rowCount += this.info.participants.participants.size();
                        this.membersEndRow = this.rowCount;
                        if (!this.usersEndReached) {
                            i = this.rowCount;
                            this.rowCount = i + 1;
                            this.loadMoreMembersRow = i;
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (this.info != null) {
                    if (!(this.info.participants instanceof TLRPC$TL_chatParticipantsForbidden) && this.info.participants.participants.size() < MessagesController.getInstance().maxGroupCount && (this.currentChat.admin || this.currentChat.creator || !this.currentChat.admins_enabled)) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.addMemberRow = i;
                    }
                    if (this.currentChat.creator && this.info.participants.participants.size() >= MessagesController.getInstance().minGroupConvertSize) {
                        i = this.rowCount;
                        this.rowCount = i + 1;
                        this.convertRow = i;
                    }
                }
                i = this.rowCount;
                this.rowCount = i + 1;
                this.emptyRowChat = i;
                if (this.convertRow != -1) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.convertHelpRow = i;
                } else {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.membersSectionRow = i;
                }
                if (this.info != null && !(this.info.participants instanceof TLRPC$TL_chatParticipantsForbidden)) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.emptyRowChat2 = i;
                    this.rowCount += this.info.participants.participants.size();
                    this.membersEndRow = this.rowCount;
                }
            } else if (!ChatObject.isChannel(this.currentChat) && this.info != null && !(this.info.participants instanceof TLRPC$TL_chatParticipantsForbidden)) {
                i = this.rowCount;
                this.rowCount = i + 1;
                this.addMemberRow = i;
                i = this.rowCount;
                this.rowCount = i + 1;
                this.emptyRowChat2 = i;
                this.rowCount += this.info.participants.participants.size();
                this.membersEndRow = this.rowCount;
            }
        }
    }

    private void updateProfileData() {
        if (this.avatarImage != null && this.nameTextView != null) {
            String onlineTextOverride;
            int currentConnectionState = ConnectionsManager.getInstance().getConnectionState();
            if (currentConnectionState == 2) {
                onlineTextOverride = LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork);
            } else if (currentConnectionState == 1) {
                onlineTextOverride = LocaleController.getString("Connecting", R.string.Connecting);
            } else if (currentConnectionState == 5) {
                onlineTextOverride = LocaleController.getString("Updating", R.string.Updating);
            } else if (currentConnectionState != 4) {
                onlineTextOverride = null;
            } else if (SLSProxyHelper.isProxyFromSLS()) {
                onlineTextOverride = LocaleController.getString("Connecting", R.string.Connecting);
            } else {
                onlineTextOverride = LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy);
            }
            TLObject photo;
            TLRPC$FileLocation photoBig;
            String newString;
            int a;
            if (this.user_id != 0) {
                String newString2;
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                photo = null;
                photoBig = null;
                if (user.photo != null) {
                    photo = user.photo.photo_small;
                    photoBig = user.photo.photo_big;
                }
                this.avatarDrawable.setInfo(user);
                this.avatarImage.setImage(photo, "50_50", this.avatarDrawable);
                newString = UserObject.getUserName(user);
                if (user.id == UserConfig.getClientUserId()) {
                    newString2 = LocaleController.getString("ChatYourSelf", R.string.ChatYourSelf);
                    newString = LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName);
                } else if (user.id == 333000 || user.id == 777000) {
                    newString2 = LocaleController.getString("ServiceNotifications", R.string.ServiceNotifications);
                } else if (this.isBot) {
                    newString2 = LocaleController.getString("Bot", R.string.Bot);
                } else {
                    newString2 = LocaleController.formatUserStatus(user);
                }
                for (a = 0; a < 2; a++) {
                    if (this.nameTextView[a] != null) {
                        if (a == 0 && user.id != UserConfig.getClientUserId() && user.id / 1000 != 777 && user.id / 1000 != 333 && user.phone != null && user.phone.length() != 0 && ContactsController.getInstance().contactsDict.get(Integer.valueOf(user.id)) == null && (ContactsController.getInstance().contactsDict.size() != 0 || !ContactsController.getInstance().isLoadingContacts())) {
                            String phoneString = PhoneFormat.getInstance().format("+" + user.phone);
                            if (!this.nameTextView[a].getText().equals(phoneString)) {
                                this.nameTextView[a].setText(phoneString);
                            }
                        } else if (!this.nameTextView[a].getText().equals(newString)) {
                            this.nameTextView[a].setText(newString);
                        }
                        if (a == 0 && onlineTextOverride != null) {
                            this.onlineTextView[a].setText(onlineTextOverride);
                        } else if (!this.onlineTextView[a].getText().equals(newString2)) {
                            this.onlineTextView[a].setText(newString2);
                        }
                        Drawable leftIcon = this.currentEncryptedChat != null ? Theme.chat_lockIconDrawable : null;
                        Drawable rightIcon = null;
                        if (a == 0) {
                            rightIcon = MessagesController.getInstance().isDialogMuted((this.dialog_id > 0 ? 1 : (this.dialog_id == 0 ? 0 : -1)) != 0 ? this.dialog_id : (long) this.user_id) ? Theme.chat_muteIconDrawable : null;
                        } else if (user.verified) {
                            Drawable combinedDrawable = new CombinedDrawable(Theme.profile_verifiedDrawable, Theme.profile_verifiedCheckDrawable);
                        }
                        this.nameTextView[a].setLeftDrawable(leftIcon);
                        this.nameTextView[a].setRightDrawable(rightIcon);
                    }
                }
                this.avatarImage.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(photoBig), false);
            } else if (this.chat_id != 0) {
                int[] result;
                String shortNumber;
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat != null) {
                    this.currentChat = chat;
                } else {
                    chat = this.currentChat;
                }
                if (!ChatObject.isChannel(chat)) {
                    int count = chat.participants_count;
                    if (this.info != null) {
                        count = this.info.participants.participants.size();
                    }
                    if (count == 0 || this.onlineCount <= 1) {
                        newString = LocaleController.formatPluralString("Members", count);
                    } else {
                        newString = String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                    }
                } else if (this.info == null || (!this.currentChat.megagroup && (this.info.participants_count == 0 || this.currentChat.admin || this.info.can_view_participants))) {
                    if (this.currentChat.megagroup) {
                        newString = LocaleController.getString("Loading", R.string.Loading).toLowerCase();
                    } else if ((chat.flags & 64) != 0) {
                        newString = LocaleController.getString("ChannelPublic", R.string.ChannelPublic).toLowerCase();
                    } else {
                        newString = LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate).toLowerCase();
                    }
                } else if (!this.currentChat.megagroup || this.info.participants_count > 200) {
                    result = new int[1];
                    shortNumber = LocaleController.formatShortNumber(this.info.participants_count, result);
                    if (this.currentChat.megagroup) {
                        newString = LocaleController.formatPluralString("Members", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber);
                    } else {
                        newString = LocaleController.formatPluralString("Subscribers", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber);
                    }
                } else if (this.onlineCount <= 1 || this.info.participants_count == 0) {
                    newString = LocaleController.formatPluralString("Members", this.info.participants_count);
                } else {
                    newString = String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", this.info.participants_count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                }
                a = 0;
                while (a < 2) {
                    if (this.nameTextView[a] != null) {
                        if (!(chat.title == null || this.nameTextView[a].getText().equals(chat.title))) {
                            this.nameTextView[a].setText(chat.title);
                        }
                        this.nameTextView[a].setLeftDrawable(null);
                        if (a == 0) {
                            this.nameTextView[a].setRightDrawable(MessagesController.getInstance().isDialogMuted((long) (-this.chat_id)) ? Theme.chat_muteIconDrawable : null);
                        } else if (chat.verified) {
                            this.nameTextView[a].setRightDrawable(new CombinedDrawable(Theme.profile_verifiedDrawable, Theme.profile_verifiedCheckDrawable));
                        } else {
                            this.nameTextView[a].setRightDrawable(null);
                        }
                        if (a == 0 && onlineTextOverride != null) {
                            this.onlineTextView[a].setText(onlineTextOverride);
                        } else if (!this.currentChat.megagroup || this.info == null || this.info.participants_count > 200 || this.onlineCount <= 0) {
                            if (a == 0 && ChatObject.isChannel(this.currentChat) && this.info != null && this.info.participants_count != 0 && (this.currentChat.megagroup || this.currentChat.broadcast)) {
                                result = new int[1];
                                shortNumber = LocaleController.formatShortNumber(this.info.participants_count, result);
                                if (this.currentChat.megagroup) {
                                    this.onlineTextView[a].setText(LocaleController.formatPluralString("Members", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber));
                                } else {
                                    this.onlineTextView[a].setText(LocaleController.formatPluralString("Subscribers", result[0]).replace(String.format("%d", new Object[]{Integer.valueOf(result[0])}), shortNumber));
                                }
                            } else if (!this.onlineTextView[a].getText().equals(newString)) {
                                this.onlineTextView[a].setText(newString);
                            }
                        } else if (!this.onlineTextView[a].getText().equals(newString)) {
                            this.onlineTextView[a].setText(newString);
                        }
                    }
                    a++;
                }
                photo = null;
                photoBig = null;
                if (chat.photo != null) {
                    photo = chat.photo.photo_small;
                    photoBig = chat.photo.photo_big;
                }
                this.avatarDrawable.setInfo(chat);
                this.avatarImage.setImage(photo, "50_50", this.avatarDrawable);
                this.avatarImage.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(photoBig), false);
            }
        }
    }

    private void createActionBarMenu() {
        ActionBarMenu menu = this.actionBar.createMenu();
        menu.clearItems();
        this.animatingItem = null;
        ActionBarMenuItem item = null;
        if (this.user_id != 0) {
            if (UserConfig.getClientUserId() != this.user_id) {
                TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(this.user_id);
                if (AppPreferences.isCallEnable() && userFull != null && userFull.phone_calls_available) {
                    this.callItem = menu.addItem(15, (int) R.drawable.ic_call_white_24dp);
                }
                if (ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.user_id)) == null) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                    if (user != null) {
                        item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                        if (this.isBot) {
                            if (!user.bot_nochats) {
                                item.addSubItem(9, LocaleController.getString("BotInvite", R.string.BotInvite));
                            }
                            item.addSubItem(10, LocaleController.getString("BotShare", R.string.BotShare));
                        }
                        if (user.phone != null && user.phone.length() != 0) {
                            String string;
                            item.addSubItem(1, LocaleController.getString("AddContact", R.string.AddContact));
                            item.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
                            if (this.userBlocked) {
                                string = LocaleController.getString("Unblock", R.string.Unblock);
                            } else {
                                string = LocaleController.getString("BlockContact", R.string.BlockContact);
                            }
                            item.addSubItem(2, string);
                        } else if (this.isBot) {
                            item.addSubItem(2, !this.userBlocked ? LocaleController.getString("BotStop", R.string.BotStop) : LocaleController.getString("BotRestart", R.string.BotRestart));
                        } else {
                            item.addSubItem(2, !this.userBlocked ? LocaleController.getString("BlockContact", R.string.BlockContact) : LocaleController.getString("Unblock", R.string.Unblock));
                        }
                    } else {
                        return;
                    }
                }
                item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                item.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
                item.addSubItem(2, !this.userBlocked ? LocaleController.getString("BlockContact", R.string.BlockContact) : LocaleController.getString("Unblock", R.string.Unblock));
                item.addSubItem(4, LocaleController.getString("EditContact", R.string.EditContact));
                item.addSubItem(5, LocaleController.getString("DeleteContact", R.string.DeleteContact));
            } else {
                item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                item.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
            }
        } else if (this.chat_id != 0) {
            if (this.chat_id > 0) {
                TLRPC$Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (this.writeButton != null) {
                    boolean isChannel = ChatObject.isChannel(this.currentChat);
                    if ((!isChannel || ChatObject.canChangeChatInfo(this.currentChat)) && (isChannel || this.currentChat.admin || this.currentChat.creator || !this.currentChat.admins_enabled)) {
                        this.writeButton.setImageResource(R.drawable.floating_camera);
                        this.writeButton.setPadding(0, 0, 0, 0);
                    } else {
                        this.writeButton.setImageResource(R.drawable.floating_message);
                        this.writeButton.setPadding(0, AndroidUtilities.dp(3.0f), 0, 0);
                    }
                }
                if (ChatObject.isChannel(chat)) {
                    if (ChatObject.hasAdminRights(chat)) {
                        this.editItem = menu.addItem(12, (int) R.drawable.menu_settings);
                        if (null == null) {
                            item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                        }
                        if (chat.megagroup) {
                            item.addSubItem(12, LocaleController.getString("ManageGroupMenu", R.string.ManageGroupMenu));
                        } else {
                            item.addSubItem(12, LocaleController.getString("ManageChannelMenu", R.string.ManageChannelMenu));
                        }
                    }
                    if (chat.megagroup) {
                        if (item == null) {
                            item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                        }
                        item.addSubItem(16, LocaleController.getString("SearchMembers", R.string.SearchMembers));
                        if (!(chat.creator || chat.left || chat.kicked)) {
                            item.addSubItem(7, LocaleController.getString("LeaveMegaMenu", R.string.LeaveMegaMenu));
                        }
                    }
                } else {
                    if (!chat.admins_enabled || chat.creator || chat.admin) {
                        this.editItem = menu.addItem(8, (int) R.drawable.group_edit_profile);
                    }
                    item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                    if (chat.creator && this.chat_id > 0) {
                        item.addSubItem(11, LocaleController.getString("SetAdmins", R.string.SetAdmins));
                    }
                    if (!chat.admins_enabled || chat.creator || chat.admin) {
                        item.addSubItem(8, LocaleController.getString("ChannelEdit", R.string.ChannelEdit));
                    }
                    item.addSubItem(16, LocaleController.getString("SearchMembers", R.string.SearchMembers));
                    if (chat.creator && (this.info == null || this.info.participants.participants.size() > 0)) {
                        item.addSubItem(13, LocaleController.getString("ConvertGroupMenu", R.string.ConvertGroupMenu));
                    }
                    item.addSubItem(7, LocaleController.getString("DeleteAndExit", R.string.DeleteAndExit));
                }
            } else {
                item = menu.addItem(10, (int) R.drawable.ic_ab_other);
                item.addSubItem(8, LocaleController.getString("EditName", R.string.EditName));
            }
        }
        if (item == null) {
            item = menu.addItem(10, (int) R.drawable.ic_ab_other);
        }
        item.addSubItem(14, LocaleController.getString("AddShortcut", R.string.AddShortcut));
    }

    protected void onDialogDismiss(Dialog dialog) {
        if (this.listView != null) {
            this.listView.invalidateViews();
        }
    }

    public void didSelectDialogs(DialogsActivity fragment, ArrayList<Long> dids, CharSequence message, boolean param) {
        long did = ((Long) dids.get(0)).longValue();
        Bundle args = new Bundle();
        args.putBoolean("scrollToTopOnResume", true);
        int lower_part = (int) did;
        if (lower_part == 0) {
            args.putInt("enc_id", (int) (did >> 32));
        } else if (lower_part > 0) {
            args.putInt("user_id", lower_part);
        } else if (lower_part < 0) {
            args.putInt("chat_id", -lower_part);
        }
        if (MessagesController.checkCanOpenChat(args, fragment)) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
            presentFragment(new ChatActivity(args), true);
            removeSelfFromStack();
            SendMessagesHelper.getInstance().sendMessage(MessagesController.getInstance().getUser(Integer.valueOf(this.user_id)), did, null, null, null);
        }
    }

    public void onRequestPermissionsResultFragment(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            if (user != null) {
                if (grantResults.length <= 0 || grantResults[0] != 0) {
                    VoIPHelper.permissionDenied(getParentActivity(), null);
                } else {
                    VoIPHelper.startCall(user, getParentActivity(), MessagesController.getInstance().getUserFull(user.id));
                }
            }
        }
    }

    public ThemeDescription[] getThemeDescriptions() {
        ThemeDescriptionDelegate сellDelegate = new ThemeDescriptionDelegate() {
            public void didSetColor(int color) {
                int count = ProfileActivity.this.listView.getChildCount();
                for (int a = 0; a < count; a++) {
                    View child = ProfileActivity.this.listView.getChildAt(a);
                    if (child instanceof UserCell) {
                        ((UserCell) child).update(0);
                    }
                }
            }
        };
        r10 = new ThemeDescription[92];
        r10[46] = new ThemeDescription(this.listView, 0, new Class[]{View.class}, Theme.dividerPaint, null, null, Theme.key_divider);
        r10[47] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[48] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[49] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileRed);
        r10[50] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileOrange);
        r10[51] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileViolet);
        r10[52] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileGreen);
        r10[53] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileCyan);
        r10[54] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfileBlue);
        r10[55] = new ThemeDescription(this.avatarImage, 0, null, null, new Drawable[]{this.avatarDrawable}, null, Theme.key_avatar_backgroundInProfilePink);
        r10[56] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_IMAGECOLOR, null, null, null, null, Theme.key_profile_actionIcon);
        r10[57] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_BACKGROUNDFILTER, null, null, null, null, Theme.key_profile_actionBackground);
        r10[58] = new ThemeDescription(this.writeButton, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_DRAWABLESELECTEDSTATE, null, null, null, null, Theme.key_profile_actionPressedBackground);
        r10[59] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[60] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGreenText2);
        r10[61] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{TextCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteRedText5);
        r10[62] = new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"valueTextView"}, null, null, null, Theme.key_windowBackgroundWhiteValueText);
        r10[63] = new ThemeDescription(this.listView, 0, new Class[]{TextCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[64] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[65] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailCell.class}, new String[]{"valueImageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[66] = new ThemeDescription(this.listView, 0, new Class[]{TextDetailCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[67] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{UserCell.class}, new String[]{"adminImage"}, null, null, null, Theme.key_profile_creatorIcon);
        r10[68] = new ThemeDescription(this.listView, ThemeDescription.FLAG_CHECKTAG, new Class[]{UserCell.class}, new String[]{"adminImage"}, null, null, null, Theme.key_profile_adminIcon);
        r10[69] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"nameTextView"}, null, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[70] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteGrayText);
        r10[71] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, сellDelegate, Theme.key_windowBackgroundWhiteBlueText);
        r10[72] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[73] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundRed);
        r10[74] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundOrange);
        r10[75] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundViolet);
        r10[76] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundGreen);
        r10[77] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundCyan);
        r10[78] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundBlue);
        r10[79] = new ThemeDescription(null, 0, null, null, null, сellDelegate, Theme.key_avatar_backgroundPink);
        r10[80] = new ThemeDescription(this.listView, 0, new Class[]{LoadingCell.class}, new String[]{"progressBar"}, null, null, null, Theme.key_progressCircle);
        r10[81] = new ThemeDescription(this.listView, 0, new Class[]{AboutLinkCell.class}, new String[]{"imageView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayIcon);
        r10[82] = new ThemeDescription(this.listView, ThemeDescription.FLAG_TEXTCOLOR, new Class[]{AboutLinkCell.class}, Theme.profile_aboutTextPaint, null, null, Theme.key_windowBackgroundWhiteBlackText);
        r10[83] = new ThemeDescription(this.listView, ThemeDescription.FLAG_LINKCOLOR, new Class[]{AboutLinkCell.class}, Theme.profile_aboutTextPaint, null, null, Theme.key_windowBackgroundWhiteLinkText);
        r10[84] = new ThemeDescription(this.listView, 0, new Class[]{AboutLinkCell.class}, Theme.linkSelectionPaint, null, null, Theme.key_windowBackgroundWhiteLinkSelection);
        r10[85] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[86] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{ShadowSectionCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[87] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGrayShadow);
        r10[88] = new ThemeDescription(this.listView, ThemeDescription.FLAG_BACKGROUNDFILTER | ThemeDescription.FLAG_CELLBACKGROUNDCOLOR, new Class[]{TextInfoPrivacyCell.class}, null, null, null, Theme.key_windowBackgroundGray);
        r10[89] = new ThemeDescription(this.listView, 0, new Class[]{TextInfoPrivacyCell.class}, new String[]{"textView"}, null, null, null, Theme.key_windowBackgroundWhiteGrayText4);
        r10[90] = new ThemeDescription(this.nameTextView[1], 0, null, null, new Drawable[]{Theme.profile_verifiedCheckDrawable}, null, Theme.key_profile_verifiedCheck);
        r10[91] = new ThemeDescription(this.nameTextView[1], 0, null, null, new Drawable[]{Theme.profile_verifiedDrawable}, null, Theme.key_profile_verifiedBackground);
        return r10;
    }
}
