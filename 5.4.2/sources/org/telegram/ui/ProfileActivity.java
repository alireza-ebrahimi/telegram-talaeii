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
import android.support.v7.app.C0767b.C0766a;
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
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.measurement.AppMeasurement.Param;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.customization.Model.DialogStatus;
import org.telegram.customization.Model.FilterResponse;
import org.telegram.customization.p151g.C2497d;
import org.telegram.customization.p151g.C2818c;
import org.telegram.customization.p151g.C2820e;
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
import org.telegram.messenger.exoplayer2.text.ttml.TtmlNode;
import org.telegram.messenger.query.BotQuery;
import org.telegram.messenger.query.SharedMediaQuery;
import org.telegram.messenger.support.widget.LinearLayoutManager;
import org.telegram.messenger.support.widget.RecyclerView;
import org.telegram.messenger.support.widget.RecyclerView.LayoutParams;
import org.telegram.messenger.support.widget.RecyclerView.OnScrollListener;
import org.telegram.messenger.support.widget.RecyclerView.ViewHolder;
import org.telegram.messenger.support.widget.helper.ItemTouchHelper.Callback;
import org.telegram.p149a.C2488b;
import org.telegram.p150b.C2491a;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.RequestDelegate;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$TL_decryptedMessageActionSetMessageTTL;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_error;
import org.telegram.tgnet.TLRPC$TL_messageEncryptedAction;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_userEmpty;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC.BotInfo;
import org.telegram.tgnet.TLRPC.ChannelParticipant;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.ChatFull;
import org.telegram.tgnet.TLRPC.ChatParticipant;
import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.FileLocation;
import org.telegram.tgnet.TLRPC.InputFile;
import org.telegram.tgnet.TLRPC.PhotoSize;
import org.telegram.tgnet.TLRPC.TL_channelAdminRights;
import org.telegram.tgnet.TLRPC.TL_channelBannedRights;
import org.telegram.tgnet.TLRPC.TL_channelFull;
import org.telegram.tgnet.TLRPC.TL_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channelParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_channelParticipantBanned;
import org.telegram.tgnet.TLRPC.TL_channelParticipantCreator;
import org.telegram.tgnet.TLRPC.TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_channelParticipants;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipant;
import org.telegram.tgnet.TLRPC.TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC.TL_chatChannelParticipant;
import org.telegram.tgnet.TLRPC.TL_chatFull;
import org.telegram.tgnet.TLRPC.TL_chatParticipant;
import org.telegram.tgnet.TLRPC.TL_chatParticipantAdmin;
import org.telegram.tgnet.TLRPC.TL_chatParticipantCreator;
import org.telegram.tgnet.TLRPC.TL_chatParticipants;
import org.telegram.tgnet.TLRPC.TL_chatParticipantsForbidden;
import org.telegram.tgnet.TLRPC.TL_chatPhotoEmpty;
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
import utils.p178a.C3791b;

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
    private BotInfo botInfo;
    private ActionBarMenuItem callItem;
    private int channelInfoRow;
    boolean channelIsFilter = false;
    private int channelNameRow;
    private int chat_id;
    private int convertHelpRow;
    private int convertRow;
    private boolean creatingChat;
    private ChannelParticipant currentChannelParticipant;
    private Chat currentChat;
    private EncryptedChat currentEncryptedChat;
    private long dialog_id;
    private ActionBarMenuItem editItem;
    private int emptyRow;
    private int emptyRowChat;
    private int emptyRowChat2;
    private int extraHeight;
    private int groupsInCommonRow;
    private ChatFull info;
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
    private HashMap<Integer, ChatParticipant> participantsMap = new HashMap();
    private int phoneRow;
    private boolean playProfileAnimation;
    private PhotoViewerProvider provider = new C51233();
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
    class C51233 extends EmptyPhotoViewerProvider {
        C51233() {
        }

        public PlaceProviderObject getPlaceForPhoto(MessageObject messageObject, FileLocation fileLocation, int i) {
            PlaceProviderObject placeProviderObject = null;
            if (fileLocation != null) {
                FileLocation fileLocation2;
                if (ProfileActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (!(user == null || user.photo == null || user.photo.photo_big == null)) {
                        fileLocation2 = user.photo.photo_big;
                    }
                    fileLocation2 = null;
                } else {
                    if (ProfileActivity.this.chat_id != 0) {
                        Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                        if (!(chat == null || chat.photo == null || chat.photo.photo_big == null)) {
                            fileLocation2 = chat.photo.photo_big;
                        }
                    }
                    fileLocation2 = null;
                }
                if (fileLocation2 != null && fileLocation2.local_id == fileLocation.local_id && fileLocation2.volume_id == fileLocation.volume_id && fileLocation2.dc_id == fileLocation.dc_id) {
                    int[] iArr = new int[2];
                    ProfileActivity.this.avatarImage.getLocationInWindow(iArr);
                    placeProviderObject = new PlaceProviderObject();
                    placeProviderObject.viewX = iArr[0];
                    placeProviderObject.viewY = iArr[1] - (VERSION.SDK_INT >= 21 ? 0 : AndroidUtilities.statusBarHeight);
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
    class C51255 implements AvatarUpdaterDelegate {
        C51255() {
        }

        public void didUploadedPhoto(InputFile inputFile, PhotoSize photoSize, PhotoSize photoSize2) {
            if (ProfileActivity.this.chat_id != 0) {
                MessagesController.getInstance().changeChatAvatar(ProfileActivity.this.chat_id, inputFile);
            }
        }
    }

    /* renamed from: org.telegram.ui.ProfileActivity$7 */
    class C51287 implements Runnable {

        /* renamed from: org.telegram.ui.ProfileActivity$7$1 */
        class C51271 implements C2497d {
            C51271() {
            }

            public void onResult(Object obj, int i) {
                switch (i) {
                    case 19:
                        FilterResponse filterResponse = (FilterResponse) obj;
                        if (filterResponse != null) {
                            DialogStatus dialogStatus = new DialogStatus();
                            dialogStatus.setFilter(filterResponse.isFilter());
                            dialogStatus.setDialogId(filterResponse.getChannelId());
                            ApplicationLoader.databaseHandler.a(dialogStatus);
                            if (filterResponse.isFilter()) {
                                ProfileActivity.this.channelIsFilter = filterResponse.isFilter();
                                ProfileActivity.this.showCantOpenAlert(ProfileActivity.this, C3791b.T(ApplicationLoader.applicationContext), true);
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

        C51287() {
        }

        public void run() {
            if (ChatObject.isChannel(ProfileActivity.this.currentChat) && ProfileActivity.this.currentChat != null && !TextUtils.isEmpty(ProfileActivity.this.currentChat.username) && ChatObject.isNotInChat(ProfileActivity.this.currentChat)) {
                C2818c.a(ApplicationLoader.applicationContext, new C51271()).a((long) UserConfig.getClientUserId(), ProfileActivity.this.currentChat.id);
            }
        }
    }

    /* renamed from: org.telegram.ui.ProfileActivity$8 */
    class C51328 extends ActionBarMenuOnItemClick {

        /* renamed from: org.telegram.ui.ProfileActivity$8$1 */
        class C51291 implements OnClickListener {
            C51291() {
            }

            public void onClick(DialogInterface dialogInterface, int i) {
                if (ProfileActivity.this.userBlocked) {
                    MessagesController.getInstance().unblockUser(ProfileActivity.this.user_id);
                } else {
                    MessagesController.getInstance().blockUser(ProfileActivity.this.user_id);
                }
            }
        }

        C51328() {
        }

        public void onItemClick(int i) {
            if (ProfileActivity.this.getParentActivity() != null) {
                if (i == -1) {
                    ProfileActivity.this.finishFragment();
                } else if (i == 2) {
                    if (MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)) == null) {
                        return;
                    }
                    if (!ProfileActivity.this.isBot) {
                        Builder builder = new Builder(ProfileActivity.this.getParentActivity());
                        if (ProfileActivity.this.userBlocked) {
                            builder.setMessage(LocaleController.getString("AreYouSureUnblockContact", R.string.AreYouSureUnblockContact));
                        } else {
                            builder.setMessage(LocaleController.getString("AreYouSureBlockContact", R.string.AreYouSureBlockContact));
                        }
                        builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C51291());
                        builder.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder.create());
                    } else if (ProfileActivity.this.userBlocked) {
                        MessagesController.getInstance().unblockUser(ProfileActivity.this.user_id);
                        SendMessagesHelper.getInstance().sendMessage("/start", (long) ProfileActivity.this.user_id, null, null, false, null, null, null);
                        ProfileActivity.this.finishFragment();
                    } else {
                        MessagesController.getInstance().blockUser(ProfileActivity.this.user_id);
                    }
                } else if (i == 1) {
                    r0 = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    r1 = new Bundle();
                    r1.putInt("user_id", r0.id);
                    r1.putBoolean("addContact", true);
                    ProfileActivity.this.presentFragment(new ContactAddActivity(r1));
                } else if (i == 3) {
                    r0 = new Bundle();
                    r0.putBoolean("onlySelect", true);
                    r0.putString("selectAlertString", LocaleController.getString("SendContactTo", R.string.SendContactTo));
                    r0.putString("selectAlertStringGroup", LocaleController.getString("SendContactToGroup", R.string.SendContactToGroup));
                    r1 = new DialogsActivity(r0);
                    r1.setDelegate(ProfileActivity.this);
                    ProfileActivity.this.presentFragment(r1);
                } else if (i == 4) {
                    r0 = new Bundle();
                    r0.putInt("user_id", ProfileActivity.this.user_id);
                    ProfileActivity.this.presentFragment(new ContactAddActivity(r0));
                } else if (i == 5) {
                    r0 = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (r0 != null && ProfileActivity.this.getParentActivity() != null) {
                        Builder builder2 = new Builder(ProfileActivity.this.getParentActivity());
                        builder2.setMessage(LocaleController.getString("AreYouSureDeleteContact", R.string.AreYouSureDeleteContact));
                        builder2.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        builder2.setPositiveButton(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ArrayList arrayList = new ArrayList();
                                arrayList.add(r0);
                                ContactsController.getInstance().deleteContact(arrayList);
                            }
                        });
                        builder2.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(builder2.create());
                    }
                } else if (i == 7) {
                    ProfileActivity.this.leaveChatPressed();
                } else if (i == 8) {
                    r0 = new Bundle();
                    r0.putInt("chat_id", ProfileActivity.this.chat_id);
                    ProfileActivity.this.presentFragment(new ChangeChatNameActivity(r0));
                } else if (i == 12) {
                    r0 = new Bundle();
                    r0.putInt("chat_id", ProfileActivity.this.chat_id);
                    r1 = new ChannelEditActivity(r0);
                    r1.setInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(r1);
                } else if (i == 9) {
                    r0 = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (r0 != null) {
                        r1 = new Bundle();
                        r1.putBoolean("onlySelect", true);
                        r1.putInt("dialogsType", 2);
                        r1.putString("addToGroupAlertString", LocaleController.formatString("AddToTheGroupTitle", R.string.AddToTheGroupTitle, new Object[]{UserObject.getUserName(r0), "%1$s"}));
                        BaseFragment dialogsActivity = new DialogsActivity(r1);
                        dialogsActivity.setDelegate(new DialogsActivityDelegate() {
                            public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
                                long longValue = ((Long) arrayList.get(0)).longValue();
                                Bundle bundle = new Bundle();
                                bundle.putBoolean("scrollToTopOnResume", true);
                                bundle.putInt("chat_id", -((int) longValue));
                                if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    MessagesController.getInstance().addUserToChat(-((int) longValue), r0, null, 0, null, ProfileActivity.this);
                                    ProfileActivity.this.presentFragment(new ChatActivity(bundle), true);
                                    ProfileActivity.this.removeSelfFromStack();
                                }
                            }
                        });
                        ProfileActivity.this.presentFragment(dialogsActivity);
                    }
                } else if (i == 10) {
                    try {
                        if (MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)) != null) {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.botInfo.user_id);
                            if (ProfileActivity.this.botInfo == null || userFull == null || TextUtils.isEmpty(userFull.about)) {
                                intent.putExtra("android.intent.extra.TEXT", String.format("https://" + MessagesController.getInstance().linkPrefix + "/%s", new Object[]{r0.username}));
                            } else {
                                intent.putExtra("android.intent.extra.TEXT", String.format("%s https://" + MessagesController.getInstance().linkPrefix + "/%s", new Object[]{userFull.about, r0.username}));
                            }
                            ProfileActivity.this.startActivityForResult(Intent.createChooser(intent, LocaleController.getString("BotShare", R.string.BotShare)), ChatActivity.startAllServices);
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                } else if (i == 11) {
                    r0 = new Bundle();
                    r0.putInt("chat_id", ProfileActivity.this.chat_id);
                    r1 = new SetAdminsActivity(r0);
                    r1.setChatInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(r1);
                } else if (i == 13) {
                    r0 = new Bundle();
                    r0.putInt("chat_id", ProfileActivity.this.chat_id);
                    ProfileActivity.this.presentFragment(new ConvertGroupActivity(r0));
                } else if (i == 14) {
                    try {
                        long j;
                        if (ProfileActivity.this.currentEncryptedChat != null) {
                            j = ((long) ProfileActivity.this.currentEncryptedChat.id) << 32;
                        } else if (ProfileActivity.this.user_id != 0) {
                            j = (long) ProfileActivity.this.user_id;
                        } else if (ProfileActivity.this.chat_id != 0) {
                            j = (long) (-ProfileActivity.this.chat_id);
                        } else {
                            return;
                        }
                        AndroidUtilities.installShortcut(j);
                    } catch (Throwable e2) {
                        FileLog.e(e2);
                    }
                } else if (i == 15) {
                    r0 = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (r0 != null) {
                        VoIPHelper.startCall(r0, ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(r0.id));
                    }
                } else if (i == 16) {
                    r0 = new Bundle();
                    r0.putInt("chat_id", ProfileActivity.this.chat_id);
                    if (ChatObject.isChannel(ProfileActivity.this.currentChat)) {
                        r0.putInt(Param.TYPE, 2);
                        r0.putBoolean("open_search", true);
                        ProfileActivity.this.presentFragment(new ChannelUsersActivity(r0));
                        return;
                    }
                    r1 = new ChatUsersActivity(r0);
                    r1.setInfo(ProfileActivity.this.info);
                    ProfileActivity.this.presentFragment(r1);
                }
            }
        }
    }

    private class ListAdapter extends SelectionAdapter {
        private Context mContext;

        /* renamed from: org.telegram.ui.ProfileActivity$ListAdapter$1 */
        class C51341 implements AboutLinkCellDelegate {
            C51341() {
            }

            public void didPressUrl(String str) {
                if (str.startsWith("@")) {
                    MessagesController.openByUserName(str.substring(1), ProfileActivity.this, 0);
                } else if (str.startsWith("#")) {
                    r0 = new DialogsActivity(null);
                    r0.setSearchString(str);
                    ProfileActivity.this.presentFragment(r0);
                } else if (str.startsWith("/") && ProfileActivity.this.parentLayout.fragmentsStack.size() > 1) {
                    r0 = (BaseFragment) ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2);
                    if (r0 instanceof ChatActivity) {
                        ProfileActivity.this.finishFragment();
                        ((ChatActivity) r0).chatActivityEnterView.setCommand(null, str, false, false);
                    }
                }
            }
        }

        public ListAdapter(Context context) {
            this.mContext = context;
        }

        public int getItemCount() {
            return ProfileActivity.this.rowCount;
        }

        public int getItemViewType(int i) {
            return (i == ProfileActivity.this.emptyRow || i == ProfileActivity.this.emptyRowChat || i == ProfileActivity.this.emptyRowChat2) ? 0 : (i == ProfileActivity.this.sectionRow || i == ProfileActivity.this.userSectionRow) ? 1 : (i == ProfileActivity.this.phoneRow || i == ProfileActivity.this.usernameRow || i == ProfileActivity.this.channelNameRow || i == ProfileActivity.this.userInfoDetailedRow) ? 2 : (i == ProfileActivity.this.leaveChannelRow || i == ProfileActivity.this.sharedMediaRow || i == ProfileActivity.this.settingsTimerRow || i == ProfileActivity.this.settingsNotificationsRow || i == ProfileActivity.this.startSecretChatRow || i == ProfileActivity.this.settingsKeyRow || i == ProfileActivity.this.convertRow || i == ProfileActivity.this.addMemberRow || i == ProfileActivity.this.groupsInCommonRow || i == ProfileActivity.this.membersRow) ? 3 : (i <= ProfileActivity.this.emptyRowChat2 || i >= ProfileActivity.this.membersEndRow) ? i == ProfileActivity.this.membersSectionRow ? 5 : i == ProfileActivity.this.convertHelpRow ? 6 : i == ProfileActivity.this.loadMoreMembersRow ? 7 : (i == ProfileActivity.this.userInfoRow || i == ProfileActivity.this.channelInfoRow) ? 8 : 0 : 4;
        }

        public boolean isEnabled(ViewHolder viewHolder) {
            int adapterPosition = viewHolder.getAdapterPosition();
            return ProfileActivity.this.user_id != 0 ? adapterPosition == ProfileActivity.this.phoneRow || adapterPosition == ProfileActivity.this.settingsTimerRow || adapterPosition == ProfileActivity.this.settingsKeyRow || adapterPosition == ProfileActivity.this.settingsNotificationsRow || adapterPosition == ProfileActivity.this.sharedMediaRow || adapterPosition == ProfileActivity.this.startSecretChatRow || adapterPosition == ProfileActivity.this.usernameRow || adapterPosition == ProfileActivity.this.userInfoRow || adapterPosition == ProfileActivity.this.groupsInCommonRow || adapterPosition == ProfileActivity.this.userInfoDetailedRow : ProfileActivity.this.chat_id != 0 ? adapterPosition == ProfileActivity.this.convertRow || adapterPosition == ProfileActivity.this.settingsNotificationsRow || adapterPosition == ProfileActivity.this.sharedMediaRow || ((adapterPosition > ProfileActivity.this.emptyRowChat2 && adapterPosition < ProfileActivity.this.membersEndRow) || adapterPosition == ProfileActivity.this.addMemberRow || adapterPosition == ProfileActivity.this.channelNameRow || adapterPosition == ProfileActivity.this.leaveChannelRow || adapterPosition == ProfileActivity.this.channelInfoRow || adapterPosition == ProfileActivity.this.membersRow) : false;
        }

        public void onBindViewHolder(ViewHolder viewHolder, int i) {
            String str = null;
            int i2 = 0;
            String string;
            TLRPC$TL_userFull userFull;
            switch (viewHolder.getItemViewType()) {
                case 0:
                    if (i == ProfileActivity.this.emptyRowChat || i == ProfileActivity.this.emptyRowChat2) {
                        ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(8.0f));
                        return;
                    } else {
                        ((EmptyCell) viewHolder.itemView).setHeight(AndroidUtilities.dp(36.0f));
                        return;
                    }
                case 2:
                    TextDetailCell textDetailCell = (TextDetailCell) viewHolder.itemView;
                    textDetailCell.setMultiline(false);
                    User user;
                    if (i == ProfileActivity.this.phoneRow) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                        string = (user.phone == null || user.phone.length() == 0) ? LocaleController.getString("NumberUnknown", R.string.NumberUnknown) : C2488b.a().e("+" + user.phone);
                        textDetailCell.setTextAndValueAndIcon(string, LocaleController.getString("PhoneMobile", R.string.PhoneMobile), R.drawable.profile_phone, 0);
                        return;
                    } else if (i == ProfileActivity.this.usernameRow) {
                        user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                        string = (user == null || TextUtils.isEmpty(user.username)) ? "-" : "@" + user.username;
                        if (ProfileActivity.this.phoneRow == -1 && ProfileActivity.this.userInfoRow == -1 && ProfileActivity.this.userInfoDetailedRow == -1) {
                            textDetailCell.setTextAndValueAndIcon(string, LocaleController.getString("Username", R.string.Username), R.drawable.profile_info, 11);
                            return;
                        } else {
                            textDetailCell.setTextAndValue(string, LocaleController.getString("Username", R.string.Username));
                            return;
                        }
                    } else if (i == ProfileActivity.this.channelNameRow) {
                        string = (ProfileActivity.this.currentChat == null || TextUtils.isEmpty(ProfileActivity.this.currentChat.username)) ? "-" : "@" + ProfileActivity.this.currentChat.username;
                        textDetailCell.setTextAndValue(string, MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
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
                    TextCell textCell = (TextCell) viewHolder.itemView;
                    textCell.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteBlackText));
                    textCell.setTag(Theme.key_windowBackgroundWhiteBlackText);
                    String str2;
                    if (i == ProfileActivity.this.sharedMediaRow) {
                        if (ProfileActivity.this.totalMediaCount == -1) {
                            string = LocaleController.getString("Loading", R.string.Loading);
                        } else {
                            str2 = "%d";
                            Object[] objArr = new Object[1];
                            objArr[0] = Integer.valueOf((ProfileActivity.this.totalMediaCountMerge != -1 ? ProfileActivity.this.totalMediaCountMerge : 0) + ProfileActivity.this.totalMediaCount);
                            string = String.format(str2, objArr);
                        }
                        if (ProfileActivity.this.user_id == 0 || UserConfig.getClientUserId() != ProfileActivity.this.user_id) {
                            textCell.setTextAndValue(LocaleController.getString("SharedMedia", R.string.SharedMedia), string);
                            return;
                        } else {
                            textCell.setTextAndValueAndIcon(LocaleController.getString("SharedMedia", R.string.SharedMedia), string, R.drawable.profile_list);
                            return;
                        }
                    } else if (i == ProfileActivity.this.groupsInCommonRow) {
                        userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                        str2 = LocaleController.getString("GroupsInCommon", R.string.GroupsInCommon);
                        str = "%d";
                        Object[] objArr2 = new Object[1];
                        objArr2[0] = Integer.valueOf(userFull != null ? userFull.common_chats_count : 0);
                        textCell.setTextAndValue(str2, String.format(str, objArr2));
                        return;
                    } else if (i == ProfileActivity.this.settingsTimerRow) {
                        EncryptedChat encryptedChat = MessagesController.getInstance().getEncryptedChat(Integer.valueOf((int) (ProfileActivity.this.dialog_id >> 32)));
                        textCell.setTextAndValue(LocaleController.getString("MessageLifetime", R.string.MessageLifetime), encryptedChat.ttl == 0 ? LocaleController.getString("ShortMessageLifetimeForever", R.string.ShortMessageLifetimeForever) : LocaleController.formatTTLString(encryptedChat.ttl));
                        return;
                    } else if (i == ProfileActivity.this.settingsNotificationsRow) {
                        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                        long access$1400 = ProfileActivity.this.dialog_id != 0 ? ProfileActivity.this.dialog_id : ProfileActivity.this.user_id != 0 ? (long) ProfileActivity.this.user_id : (long) (-ProfileActivity.this.chat_id);
                        boolean z = sharedPreferences.getBoolean("custom_" + access$1400, false);
                        boolean contains = sharedPreferences.contains("notify2_" + access$1400);
                        int i3 = sharedPreferences.getInt("notify2_" + access$1400, 0);
                        int i4 = sharedPreferences.getInt("notifyuntil_" + access$1400, 0);
                        if (i3 != 3 || i4 == Integer.MAX_VALUE) {
                            boolean z2;
                            if (i3 == 0) {
                                z2 = contains ? true : ((int) access$1400) < 0 ? sharedPreferences.getBoolean("EnableGroup", true) : sharedPreferences.getBoolean("EnableAll", true);
                            } else if (i3 == 1) {
                                z2 = true;
                            } else if (i3 == 2) {
                            }
                            if (z2 && z) {
                                str = LocaleController.getString("NotificationsCustom", R.string.NotificationsCustom);
                            } else {
                                str = z2 ? LocaleController.getString("NotificationsOn", R.string.NotificationsOn) : LocaleController.getString("NotificationsOff", R.string.NotificationsOff);
                            }
                        } else {
                            int currentTime = i4 - ConnectionsManager.getInstance().getCurrentTime();
                            if (currentTime <= 0) {
                                str = z ? LocaleController.getString("NotificationsCustom", R.string.NotificationsCustom) : LocaleController.getString("NotificationsOn", R.string.NotificationsOn);
                            } else if (currentTime < 3600) {
                                str = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Minutes", currentTime / 60)});
                            } else if (currentTime < 86400) {
                                str = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Hours", (int) Math.ceil((double) ((((float) currentTime) / 60.0f) / 60.0f)))});
                            } else if (currentTime < 31536000) {
                                str = LocaleController.formatString("WillUnmuteIn", R.string.WillUnmuteIn, new Object[]{LocaleController.formatPluralString("Days", (int) Math.ceil((double) (((((float) currentTime) / 60.0f) / 60.0f) / 24.0f)))});
                            }
                        }
                        if (str != null) {
                            textCell.setTextAndValueAndIcon(LocaleController.getString("Notifications", R.string.Notifications), str, R.drawable.profile_list);
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
                    UserCell userCell = (UserCell) viewHolder.itemView;
                    ChatParticipant chatParticipant = !ProfileActivity.this.sortedUsers.isEmpty() ? (ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((i - ProfileActivity.this.emptyRowChat2) - 1)).intValue()) : (ChatParticipant) ProfileActivity.this.info.participants.participants.get((i - ProfileActivity.this.emptyRowChat2) - 1);
                    if (chatParticipant != null) {
                        if (chatParticipant instanceof TL_chatChannelParticipant) {
                            ChannelParticipant channelParticipant = ((TL_chatChannelParticipant) chatParticipant).channelParticipant;
                            if (channelParticipant instanceof TL_channelParticipantCreator) {
                                userCell.setIsAdmin(1);
                            } else if (channelParticipant instanceof TL_channelParticipantAdmin) {
                                userCell.setIsAdmin(2);
                            } else {
                                userCell.setIsAdmin(0);
                            }
                        } else if (chatParticipant instanceof TL_chatParticipantCreator) {
                            userCell.setIsAdmin(1);
                        } else if (ProfileActivity.this.currentChat.admins_enabled && (chatParticipant instanceof TL_chatParticipantAdmin)) {
                            userCell.setIsAdmin(2);
                        } else {
                            userCell.setIsAdmin(0);
                        }
                        TLObject user2 = MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id));
                        if (i == ProfileActivity.this.emptyRowChat2 + 1) {
                            i2 = R.drawable.menu_newgroup;
                        }
                        userCell.setData(user2, null, null, i2);
                        return;
                    }
                    return;
                case 8:
                    AboutLinkCell aboutLinkCell = (AboutLinkCell) viewHolder.itemView;
                    if (i == ProfileActivity.this.userInfoRow) {
                        userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                        if (userFull != null) {
                            str = userFull.about;
                        }
                        aboutLinkCell.setTextAndIcon(str, R.drawable.profile_info, ProfileActivity.this.isBot);
                        return;
                    } else if (i == ProfileActivity.this.channelInfoRow) {
                        string = ProfileActivity.this.info.about;
                        while (string.contains("\n\n\n")) {
                            string = string.replace("\n\n\n", "\n\n");
                        }
                        aboutLinkCell.setTextAndIcon(string, R.drawable.profile_info, true);
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = null;
            switch (i) {
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
                    Drawable combinedDrawable = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable.setFullsize(true);
                    view.setBackgroundDrawable(combinedDrawable);
                    break;
                case 6:
                    view = new TextInfoPrivacyCell(this.mContext);
                    TextInfoPrivacyCell textInfoPrivacyCell = (TextInfoPrivacyCell) view;
                    Drawable combinedDrawable2 = new CombinedDrawable(new ColorDrawable(Theme.getColor(Theme.key_windowBackgroundGray)), Theme.getThemedDrawable(this.mContext, R.drawable.greydivider, Theme.key_windowBackgroundGrayShadow));
                    combinedDrawable2.setFullsize(true);
                    textInfoPrivacyCell.setBackgroundDrawable(combinedDrawable2);
                    textInfoPrivacyCell.setText(AndroidUtilities.replaceTags(LocaleController.formatString("ConvertGroupInfo", R.string.ConvertGroupInfo, new Object[]{LocaleController.formatPluralString("Members", MessagesController.getInstance().maxMegagroupCount)})));
                    break;
                case 7:
                    view = new LoadingCell(this.mContext);
                    break;
                case 8:
                    view = new AboutLinkCell(this.mContext);
                    ((AboutLinkCell) view).setDelegate(new C51341());
                    break;
            }
            view.setLayoutParams(new LayoutParams(-1, -2));
            return new Holder(view);
        }
    }

    private class TopView extends View {
        private int currentColor;
        private Paint paint = new Paint();

        public TopView(Context context) {
            super(context);
        }

        protected void onDraw(Canvas canvas) {
            int measuredHeight = getMeasuredHeight() - AndroidUtilities.dp(91.0f);
            canvas.drawRect(BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, (float) getMeasuredWidth(), (float) (ProfileActivity.this.extraHeight + measuredHeight), this.paint);
            if (ProfileActivity.this.parentLayout != null) {
                ProfileActivity.this.parentLayout.drawHeaderShadow(canvas, ProfileActivity.this.extraHeight + measuredHeight);
            }
        }

        protected void onMeasure(int i, int i2) {
            setMeasuredDimension(MeasureSpec.getSize(i), ((ProfileActivity.this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight()) + AndroidUtilities.dp(91.0f));
        }

        public void setBackgroundColor(int i) {
            if (i != this.currentColor) {
                this.paint.setColor(i);
                invalidate();
            }
        }
    }

    public ProfileActivity(Bundle bundle) {
        super(bundle);
    }

    private void checkListViewScroll() {
        boolean z = false;
        if (this.listView.getChildCount() > 0 && !this.openAnimationInProgress) {
            View childAt = this.listView.getChildAt(0);
            Holder holder = (Holder) this.listView.findContainingViewHolder(childAt);
            int top = childAt.getTop();
            int i = (top < 0 || holder == null || holder.getAdapterPosition() != 0) ? 0 : top;
            if (this.extraHeight != i) {
                this.extraHeight = i;
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

    private void createActionBarMenu() {
        ActionBarMenu createMenu = this.actionBar.createMenu();
        createMenu.clearItems();
        this.animatingItem = null;
        ActionBarMenuItem actionBarMenuItem = null;
        if (this.user_id != 0) {
            if (UserConfig.getClientUserId() != this.user_id) {
                TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(this.user_id);
                if (C3791b.c() && userFull != null && userFull.phone_calls_available) {
                    this.callItem = createMenu.addItem(15, (int) R.drawable.ic_call_white_24dp);
                }
                ActionBarMenuItem addItem;
                if (ContactsController.getInstance().contactsDict.get(Integer.valueOf(this.user_id)) == null) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                    if (user != null) {
                        addItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                        if (this.isBot) {
                            if (!user.bot_nochats) {
                                addItem.addSubItem(9, LocaleController.getString("BotInvite", R.string.BotInvite));
                            }
                            addItem.addSubItem(10, LocaleController.getString("BotShare", R.string.BotShare));
                        }
                        if (user.phone != null && user.phone.length() != 0) {
                            addItem.addSubItem(1, LocaleController.getString("AddContact", R.string.AddContact));
                            addItem.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
                            addItem.addSubItem(2, !this.userBlocked ? LocaleController.getString("BlockContact", R.string.BlockContact) : LocaleController.getString("Unblock", R.string.Unblock));
                        } else if (this.isBot) {
                            addItem.addSubItem(2, !this.userBlocked ? LocaleController.getString("BotStop", R.string.BotStop) : LocaleController.getString("BotRestart", R.string.BotRestart));
                        } else {
                            addItem.addSubItem(2, !this.userBlocked ? LocaleController.getString("BlockContact", R.string.BlockContact) : LocaleController.getString("Unblock", R.string.Unblock));
                        }
                        actionBarMenuItem = addItem;
                    } else {
                        return;
                    }
                }
                addItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                addItem.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
                addItem.addSubItem(2, !this.userBlocked ? LocaleController.getString("BlockContact", R.string.BlockContact) : LocaleController.getString("Unblock", R.string.Unblock));
                addItem.addSubItem(4, LocaleController.getString("EditContact", R.string.EditContact));
                addItem.addSubItem(5, LocaleController.getString("DeleteContact", R.string.DeleteContact));
                actionBarMenuItem = addItem;
            } else {
                actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                actionBarMenuItem.addSubItem(3, LocaleController.getString("ShareContact", R.string.ShareContact));
            }
        } else if (this.chat_id != 0) {
            if (this.chat_id > 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
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
                        this.editItem = createMenu.addItem(12, (int) R.drawable.menu_settings);
                        if (null == null) {
                            actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                        }
                        if (chat.megagroup) {
                            actionBarMenuItem.addSubItem(12, LocaleController.getString("ManageGroupMenu", R.string.ManageGroupMenu));
                        } else {
                            actionBarMenuItem.addSubItem(12, LocaleController.getString("ManageChannelMenu", R.string.ManageChannelMenu));
                        }
                    }
                    if (chat.megagroup) {
                        if (actionBarMenuItem == null) {
                            actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                        }
                        actionBarMenuItem.addSubItem(16, LocaleController.getString("SearchMembers", R.string.SearchMembers));
                        if (!(chat.creator || chat.left || chat.kicked)) {
                            actionBarMenuItem.addSubItem(7, LocaleController.getString("LeaveMegaMenu", R.string.LeaveMegaMenu));
                        }
                    }
                } else {
                    if (!chat.admins_enabled || chat.creator || chat.admin) {
                        this.editItem = createMenu.addItem(8, (int) R.drawable.group_edit_profile);
                    }
                    actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                    if (chat.creator && this.chat_id > 0) {
                        actionBarMenuItem.addSubItem(11, LocaleController.getString("SetAdmins", R.string.SetAdmins));
                    }
                    if (!chat.admins_enabled || chat.creator || chat.admin) {
                        actionBarMenuItem.addSubItem(8, LocaleController.getString("ChannelEdit", R.string.ChannelEdit));
                    }
                    actionBarMenuItem.addSubItem(16, LocaleController.getString("SearchMembers", R.string.SearchMembers));
                    if (chat.creator && (this.info == null || this.info.participants.participants.size() > 0)) {
                        actionBarMenuItem.addSubItem(13, LocaleController.getString("ConvertGroupMenu", R.string.ConvertGroupMenu));
                    }
                    actionBarMenuItem.addSubItem(7, LocaleController.getString("DeleteAndExit", R.string.DeleteAndExit));
                }
            } else {
                actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
                actionBarMenuItem.addSubItem(8, LocaleController.getString("EditName", R.string.EditName));
            }
        }
        if (actionBarMenuItem == null) {
            actionBarMenuItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
        }
        actionBarMenuItem.addSubItem(14, LocaleController.getString("AddShortcut", R.string.AddShortcut));
    }

    private void fetchUsersFromChannelInfo() {
        if (this.currentChat != null && this.currentChat.megagroup && (this.info instanceof TL_channelFull) && this.info.participants != null) {
            for (int i = 0; i < this.info.participants.participants.size(); i++) {
                ChatParticipant chatParticipant = (ChatParticipant) this.info.participants.participants.get(i);
                this.participantsMap.put(Integer.valueOf(chatParticipant.user_id), chatParticipant);
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

    private void getChannelParticipants(boolean z) {
        int i = 0;
        if (!this.loadingUsers && this.participantsMap != null && this.info != null) {
            this.loadingUsers = true;
            final int i2 = (this.participantsMap.isEmpty() || !z) ? 0 : 300;
            final TLObject tL_channels_getParticipants = new TL_channels_getParticipants();
            tL_channels_getParticipants.channel = MessagesController.getInputChannel(this.chat_id);
            tL_channels_getParticipants.filter = new TL_channelParticipantsRecent();
            if (!z) {
                i = this.participantsMap.size();
            }
            tL_channels_getParticipants.offset = i;
            tL_channels_getParticipants.limit = Callback.DEFAULT_DRAG_ANIMATION_DURATION;
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipants, new RequestDelegate() {
                public void run(final TLObject tLObject, final TLRPC$TL_error tLRPC$TL_error) {
                    AndroidUtilities.runOnUIThread(new Runnable() {
                        public void run() {
                            if (tLRPC$TL_error == null) {
                                TL_channels_channelParticipants tL_channels_channelParticipants = (TL_channels_channelParticipants) tLObject;
                                MessagesController.getInstance().putUsers(tL_channels_channelParticipants.users, false);
                                if (tL_channels_channelParticipants.users.size() != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                                    ProfileActivity.this.usersEndReached = true;
                                }
                                if (tL_channels_getParticipants.offset == 0) {
                                    ProfileActivity.this.participantsMap.clear();
                                    ProfileActivity.this.info.participants = new TL_chatParticipants();
                                    MessagesStorage.getInstance().putUsersAndChats(tL_channels_channelParticipants.users, null, true, true);
                                    MessagesStorage.getInstance().updateChannelUsers(ProfileActivity.this.chat_id, tL_channels_channelParticipants.participants);
                                }
                                for (int i = 0; i < tL_channels_channelParticipants.participants.size(); i++) {
                                    TL_chatChannelParticipant tL_chatChannelParticipant = new TL_chatChannelParticipant();
                                    tL_chatChannelParticipant.channelParticipant = (ChannelParticipant) tL_channels_channelParticipants.participants.get(i);
                                    tL_chatChannelParticipant.inviter_id = tL_chatChannelParticipant.channelParticipant.inviter_id;
                                    tL_chatChannelParticipant.user_id = tL_chatChannelParticipant.channelParticipant.user_id;
                                    tL_chatChannelParticipant.date = tL_chatChannelParticipant.channelParticipant.date;
                                    if (!ProfileActivity.this.participantsMap.containsKey(Integer.valueOf(tL_chatChannelParticipant.user_id))) {
                                        ProfileActivity.this.info.participants.participants.add(tL_chatChannelParticipant);
                                        ProfileActivity.this.participantsMap.put(Integer.valueOf(tL_chatChannelParticipant.user_id), tL_chatChannelParticipant);
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
                    }, (long) i2);
                }
            }), this.classGuid);
        }
    }

    private void kickUser(int i) {
        if (i != 0) {
            MessagesController.getInstance().deleteUserFromChat(this.chat_id, MessagesController.getInstance().getUser(Integer.valueOf(i)), this.info);
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

    private void needLayout() {
        FrameLayout.LayoutParams layoutParams;
        int currentActionBarHeight = ActionBar.getCurrentActionBarHeight() + (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0);
        if (!(this.listView == null || this.openAnimationInProgress)) {
            layoutParams = (FrameLayout.LayoutParams) this.listView.getLayoutParams();
            if (layoutParams.topMargin != currentActionBarHeight) {
                layoutParams.topMargin = currentActionBarHeight;
                this.listView.setLayoutParams(layoutParams);
            }
        }
        if (this.avatarImage != null) {
            int i;
            float dp = ((float) this.extraHeight) / ((float) AndroidUtilities.dp(88.0f));
            this.listView.setTopGlowOffset(this.extraHeight);
            if (this.writeButton != null) {
                this.writeButton.setTranslationY((float) ((((this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0) + ActionBar.getCurrentActionBarHeight()) + this.extraHeight) - AndroidUtilities.dp(29.5f)));
                if (!this.openAnimationInProgress) {
                    i = dp > 0.2f ? 1 : 0;
                    if (i != (this.writeButton.getTag() == null ? 1 : 0)) {
                        if (i != 0) {
                            this.writeButton.setTag(null);
                        } else {
                            this.writeButton.setTag(Integer.valueOf(0));
                        }
                        if (this.writeButtonAnimation != null) {
                            AnimatorSet animatorSet = this.writeButtonAnimation;
                            this.writeButtonAnimation = null;
                            animatorSet.cancel();
                        }
                        this.writeButtonAnimation = new AnimatorSet();
                        AnimatorSet animatorSet2;
                        Animator[] animatorArr;
                        if (i != 0) {
                            this.writeButtonAnimation.setInterpolator(new DecelerateInterpolator());
                            animatorSet2 = this.writeButtonAnimation;
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{1.0f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{1.0f});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{1.0f});
                            animatorSet2.playTogether(animatorArr);
                        } else {
                            this.writeButtonAnimation.setInterpolator(new AccelerateInterpolator());
                            animatorSet2 = this.writeButtonAnimation;
                            animatorArr = new Animator[3];
                            animatorArr[0] = ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{0.2f});
                            animatorArr[1] = ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{0.2f});
                            animatorArr[2] = ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED});
                            animatorSet2.playTogether(animatorArr);
                        }
                        this.writeButtonAnimation.setDuration(150);
                        this.writeButtonAnimation.addListener(new AnimatorListenerAdapter() {
                            public void onAnimationEnd(Animator animator) {
                                if (ProfileActivity.this.writeButtonAnimation != null && ProfileActivity.this.writeButtonAnimation.equals(animator)) {
                                    ProfileActivity.this.writeButtonAnimation = null;
                                }
                            }
                        });
                        this.writeButtonAnimation.start();
                    }
                }
            }
            float currentActionBarHeight2 = ((((float) (this.actionBar.getOccupyStatusBar() ? AndroidUtilities.statusBarHeight : 0)) + ((((float) ActionBar.getCurrentActionBarHeight()) / 2.0f) * (1.0f + dp))) - (21.0f * AndroidUtilities.density)) + ((27.0f * AndroidUtilities.density) * dp);
            this.avatarImage.setScaleX(((18.0f * dp) + 42.0f) / 42.0f);
            this.avatarImage.setScaleY(((18.0f * dp) + 42.0f) / 42.0f);
            this.avatarImage.setTranslationX(((float) (-AndroidUtilities.dp(47.0f))) * dp);
            this.avatarImage.setTranslationY((float) Math.ceil((double) currentActionBarHeight2));
            for (int i2 = 0; i2 < 2; i2++) {
                if (this.nameTextView[i2] != null) {
                    this.nameTextView[i2].setTranslationX((-21.0f * AndroidUtilities.density) * dp);
                    this.nameTextView[i2].setTranslationY((((float) Math.floor((double) currentActionBarHeight2)) + ((float) AndroidUtilities.dp(1.3f))) + (((float) AndroidUtilities.dp(7.0f)) * dp));
                    this.onlineTextView[i2].setTranslationX((-21.0f * AndroidUtilities.density) * dp);
                    this.onlineTextView[i2].setTranslationY((((float) Math.floor((double) currentActionBarHeight2)) + ((float) AndroidUtilities.dp(24.0f))) + (((float) Math.floor((double) (11.0f * AndroidUtilities.density))) * dp));
                    this.nameTextView[i2].setScaleX((0.12f * dp) + 1.0f);
                    this.nameTextView[i2].setScaleY((0.12f * dp) + 1.0f);
                    if (i2 == 1 && !this.openAnimationInProgress) {
                        i = AndroidUtilities.isTablet() ? AndroidUtilities.dp(490.0f) : AndroidUtilities.displaySize.x;
                        currentActionBarHeight = (this.callItem == null && this.editItem == null) ? 0 : 48;
                        currentActionBarHeight = (int) (((float) (i - AndroidUtilities.dp((((float) (currentActionBarHeight + 40)) * (1.0f - dp)) + 126.0f))) - this.nameTextView[i2].getTranslationX());
                        layoutParams = (FrameLayout.LayoutParams) this.nameTextView[i2].getLayoutParams();
                        if (((float) currentActionBarHeight) < ((float) this.nameTextView[i2].getSideDrawablesSize()) + (this.nameTextView[i2].getPaint().measureText(this.nameTextView[i2].getText().toString()) * this.nameTextView[i2].getScaleX())) {
                            layoutParams.width = (int) Math.ceil((double) (((float) currentActionBarHeight) / this.nameTextView[i2].getScaleX()));
                        } else {
                            layoutParams.width = -2;
                        }
                        this.nameTextView[i2].setLayoutParams(layoutParams);
                        layoutParams = (FrameLayout.LayoutParams) this.onlineTextView[i2].getLayoutParams();
                        layoutParams.rightMargin = (int) Math.ceil((double) ((this.onlineTextView[i2].getTranslationX() + ((float) AndroidUtilities.dp(8.0f))) + (((float) AndroidUtilities.dp(40.0f)) * (1.0f - dp))));
                        this.onlineTextView[i2].setLayoutParams(layoutParams);
                    }
                }
            }
        }
    }

    private void openAddMember() {
        int i = 0;
        boolean z = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean("onlyUsers", true);
        bundle.putBoolean("destroyAfterSelect", true);
        bundle.putBoolean("returnAsResult", true);
        String str = "needForwardCount";
        if (ChatObject.isChannel(this.currentChat)) {
            z = false;
        }
        bundle.putBoolean(str, z);
        if (this.chat_id > 0) {
            if (ChatObject.canAddViaLink(this.currentChat)) {
                bundle.putInt("chat_id", this.currentChat.id);
            }
            bundle.putString("selectAlertString", LocaleController.getString("AddToTheGroup", R.string.AddToTheGroup));
        }
        BaseFragment contactsActivity = new ContactsActivity(bundle);
        contactsActivity.setDelegate(new ContactsActivityDelegate() {
            public void didSelectContact(User user, String str, ContactsActivity contactsActivity) {
                MessagesController.getInstance().addUserToChat(ProfileActivity.this.chat_id, user, ProfileActivity.this.info, str != null ? Utilities.parseInt(str).intValue() : 0, null, ProfileActivity.this);
            }
        });
        if (!(this.info == null || this.info.participants == null)) {
            HashMap hashMap = new HashMap();
            while (i < this.info.participants.participants.size()) {
                hashMap.put(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i)).user_id), null);
                i++;
            }
            contactsActivity.setIgnoreUsers(hashMap);
        }
        presentFragment(contactsActivity);
    }

    private boolean processOnClickOrPress(final int i) {
        if (i == this.usernameRow || i == this.channelNameRow) {
            String str;
            if (i == this.usernameRow) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                if (user == null || user.username == null) {
                    return false;
                }
                str = user.username;
            } else {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat == null || chat.username == null) {
                    return false;
                }
                str = chat.username;
            }
            Builder builder = new Builder(getParentActivity());
            builder.setItems(new CharSequence[]{LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", "@" + str));
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    }
                }
            });
            showDialog(builder.create());
            return true;
        } else if (i == this.phoneRow) {
            final User user2 = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            if (user2 == null || user2.phone == null || user2.phone.length() == 0 || getParentActivity() == null) {
                return false;
            }
            Builder builder2 = new Builder(getParentActivity());
            ArrayList arrayList = new ArrayList();
            final ArrayList arrayList2 = new ArrayList();
            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(user2.id);
            if (C3791b.c() && userFull != null && userFull.phone_calls_available) {
                arrayList.add(LocaleController.getString("CallViaTelegram", R.string.CallViaTelegram));
                arrayList2.add(Integer.valueOf(2));
            }
            arrayList.add(LocaleController.getString("Call", R.string.Call));
            arrayList2.add(Integer.valueOf(0));
            arrayList.add(LocaleController.getString("Copy", R.string.Copy));
            arrayList2.add(Integer.valueOf(1));
            builder2.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    int intValue = ((Integer) arrayList2.get(i)).intValue();
                    if (intValue == 0) {
                        try {
                            Intent intent = new Intent("android.intent.action.DIAL", Uri.parse("tel:+" + user2.phone));
                            intent.addFlags(ErrorDialogData.BINDER_CRASH);
                            ProfileActivity.this.getParentActivity().startActivityForResult(intent, ChatActivity.startAllServices);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    } else if (intValue == 1) {
                        try {
                            ((ClipboardManager) ApplicationLoader.applicationContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("label", "+" + user2.phone));
                        } catch (Throwable e2) {
                            FileLog.e(e2);
                        }
                    } else if (intValue == 2) {
                        VoIPHelper.startCall(user2, ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUserFull(user2.id));
                    }
                }
            });
            showDialog(builder2.create());
            return true;
        } else if (i != this.channelInfoRow && i != this.userInfoRow && i != this.userInfoDetailedRow) {
            return false;
        } else {
            Builder builder3 = new Builder(getParentActivity());
            builder3.setItems(new CharSequence[]{LocaleController.getString("Copy", R.string.Copy)}, new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        CharSequence charSequence;
                        if (i == ProfileActivity.this.channelInfoRow) {
                            charSequence = ProfileActivity.this.info.about;
                        } else {
                            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(ProfileActivity.this.user_id);
                            charSequence = userFull != null ? userFull.about : null;
                        }
                        if (!TextUtils.isEmpty(charSequence)) {
                            AndroidUtilities.addToClipboard(charSequence);
                        }
                    } catch (Throwable e) {
                        FileLog.e(e);
                    }
                }
            });
            showDialog(builder3.create());
            return true;
        }
    }

    private void showCantOpenAlert(BaseFragment baseFragment, String str, final boolean z) {
        if (baseFragment != null) {
            try {
                if (baseFragment.getParentActivity() != null) {
                    C0766a c0766a = new C0766a(baseFragment.getParentActivity());
                    c0766a.a(TtmlNode.ANONYMOUS_REGION_ID);
                    c0766a.a(LocaleController.getString("OK", R.string.OK), new OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (z) {
                                ProfileActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    c0766a.a(new OnCancelListener() {
                        public void onCancel(DialogInterface dialogInterface) {
                            if (z && ProfileActivity.this.parentLayout != null) {
                                ProfileActivity.this.parentLayout.onBackPressed();
                            }
                        }
                    });
                    c0766a.b(str);
                    c0766a.a(false);
                    baseFragment.showDialog(c0766a.b());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateOnlineCount() {
        this.onlineCount = 0;
        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
        this.sortedUsers.clear();
        if ((this.info instanceof TL_chatFull) || ((this.info instanceof TL_channelFull) && this.info.participants_count <= Callback.DEFAULT_DRAG_ANIMATION_DURATION && this.info.participants != null)) {
            for (int i = 0; i < this.info.participants.participants.size(); i++) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) this.info.participants.participants.get(i)).user_id));
                if (!(user == null || user.status == null || ((user.status.expires <= currentTime && user.id != UserConfig.getClientUserId()) || user.status.expires <= 10000))) {
                    this.onlineCount++;
                }
                this.sortedUsers.add(Integer.valueOf(i));
            }
            try {
                Collections.sort(this.sortedUsers, new Comparator<Integer>() {
                    public int compare(Integer num, Integer num2) {
                        User user = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) ProfileActivity.this.info.participants.participants.get(num2.intValue())).user_id));
                        User user2 = MessagesController.getInstance().getUser(Integer.valueOf(((ChatParticipant) ProfileActivity.this.info.participants.participants.get(num.intValue())).user_id));
                        int currentTime = (user == null || user.status == null) ? 0 : user.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user.status.expires;
                        int currentTime2 = (user2 == null || user2.status == null) ? 0 : user2.id == UserConfig.getClientUserId() ? ConnectionsManager.getInstance().getCurrentTime() + 50000 : user2.status.expires;
                        return (currentTime <= 0 || currentTime2 <= 0) ? (currentTime >= 0 || currentTime2 >= 0) ? ((currentTime >= 0 || currentTime2 <= 0) && (currentTime != 0 || currentTime2 == 0)) ? ((currentTime2 >= 0 || currentTime <= 0) && (currentTime2 != 0 || currentTime == 0)) ? 0 : 1 : -1 : currentTime > currentTime2 ? 1 : currentTime < currentTime2 ? -1 : 0 : currentTime > currentTime2 ? 1 : currentTime < currentTime2 ? -1 : 0;
                    }
                });
            } catch (Throwable e) {
                FileLog.e(e);
            }
            if (this.listAdapter != null) {
                this.listAdapter.notifyItemRangeChanged(this.emptyRowChat2 + 1, this.sortedUsers.size());
            }
        }
    }

    private void updateProfileData() {
        if (this.avatarImage != null && this.nameTextView != null) {
            int connectionState = ConnectionsManager.getInstance().getConnectionState();
            CharSequence string = connectionState == 2 ? LocaleController.getString("WaitingForNetwork", R.string.WaitingForNetwork) : connectionState == 1 ? LocaleController.getString("Connecting", R.string.Connecting) : connectionState == 5 ? LocaleController.getString("Updating", R.string.Updating) : connectionState == 4 ? C2820e.a() ? LocaleController.getString("Connecting", R.string.Connecting) : LocaleController.getString("ConnectingToProxy", R.string.ConnectingToProxy) : null;
            TLObject tLObject;
            CharSequence userName;
            CharSequence string2;
            if (this.user_id != 0) {
                User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
                tLObject = null;
                FileLocation fileLocation = null;
                if (user.photo != null) {
                    tLObject = user.photo.photo_small;
                    fileLocation = user.photo.photo_big;
                }
                this.avatarDrawable.setInfo(user);
                this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable);
                userName = UserObject.getUserName(user);
                if (user.id == UserConfig.getClientUserId()) {
                    string2 = LocaleController.getString("ChatYourSelf", R.string.ChatYourSelf);
                    userName = LocaleController.getString("ChatYourSelfName", R.string.ChatYourSelfName);
                } else {
                    string2 = (user.id == 333000 || user.id == 777000) ? LocaleController.getString("ServiceNotifications", R.string.ServiceNotifications) : this.isBot ? LocaleController.getString("Bot", R.string.Bot) : LocaleController.formatUserStatus(user);
                }
                for (int i = 0; i < 2; i++) {
                    if (this.nameTextView[i] != null) {
                        if (i == 0 && user.id != UserConfig.getClientUserId() && user.id / 1000 != 777 && user.id / 1000 != 333 && user.phone != null && user.phone.length() != 0 && ContactsController.getInstance().contactsDict.get(Integer.valueOf(user.id)) == null && (ContactsController.getInstance().contactsDict.size() != 0 || !ContactsController.getInstance().isLoadingContacts())) {
                            CharSequence e = C2488b.a().e("+" + user.phone);
                            if (!this.nameTextView[i].getText().equals(e)) {
                                this.nameTextView[i].setText(e);
                            }
                        } else if (!this.nameTextView[i].getText().equals(userName)) {
                            this.nameTextView[i].setText(userName);
                        }
                        if (i == 0 && string != null) {
                            this.onlineTextView[i].setText(string);
                        } else if (!this.onlineTextView[i].getText().equals(string2)) {
                            this.onlineTextView[i].setText(string2);
                        }
                        Drawable drawable = this.currentEncryptedChat != null ? Theme.chat_lockIconDrawable : null;
                        Drawable drawable2 = null;
                        if (i == 0) {
                            drawable2 = MessagesController.getInstance().isDialogMuted((this.dialog_id > 0 ? 1 : (this.dialog_id == 0 ? 0 : -1)) != 0 ? this.dialog_id : (long) this.user_id) ? Theme.chat_muteIconDrawable : null;
                        } else if (user.verified) {
                            drawable2 = new CombinedDrawable(Theme.profile_verifiedDrawable, Theme.profile_verifiedCheckDrawable);
                        }
                        this.nameTextView[i].setLeftDrawable(drawable);
                        this.nameTextView[i].setRightDrawable(drawable2);
                    }
                }
                this.avatarImage.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(fileLocation), false);
            } else if (this.chat_id != 0) {
                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat != null) {
                    this.currentChat = chat;
                } else {
                    chat = this.currentChat;
                }
                if (!ChatObject.isChannel(chat)) {
                    int i2 = chat.participants_count;
                    if (this.info != null) {
                        i2 = this.info.participants.participants.size();
                    }
                    string2 = (i2 == 0 || this.onlineCount <= 1) ? LocaleController.formatPluralString("Members", i2) : String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", i2), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                } else if (this.info == null || (!this.currentChat.megagroup && (this.info.participants_count == 0 || this.currentChat.admin || this.info.can_view_participants))) {
                    string2 = this.currentChat.megagroup ? LocaleController.getString("Loading", R.string.Loading).toLowerCase() : (chat.flags & 64) != 0 ? LocaleController.getString("ChannelPublic", R.string.ChannelPublic).toLowerCase() : LocaleController.getString("ChannelPrivate", R.string.ChannelPrivate).toLowerCase();
                } else if (!this.currentChat.megagroup || this.info.participants_count > Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                    int[] iArr = new int[1];
                    userName = LocaleController.formatShortNumber(this.info.participants_count, iArr);
                    string2 = this.currentChat.megagroup ? LocaleController.formatPluralString("Members", iArr[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr[0])}), userName) : LocaleController.formatPluralString("Subscribers", iArr[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr[0])}), userName);
                } else {
                    string2 = (this.onlineCount <= 1 || this.info.participants_count == 0) ? LocaleController.formatPluralString("Members", this.info.participants_count) : String.format("%s, %s", new Object[]{LocaleController.formatPluralString("Members", this.info.participants_count), LocaleController.formatPluralString("OnlineCount", this.onlineCount)});
                }
                int i3 = 0;
                while (i3 < 2) {
                    if (this.nameTextView[i3] != null) {
                        if (!(chat.title == null || this.nameTextView[i3].getText().equals(chat.title))) {
                            this.nameTextView[i3].setText(chat.title);
                        }
                        this.nameTextView[i3].setLeftDrawable(null);
                        if (i3 == 0) {
                            this.nameTextView[i3].setRightDrawable(MessagesController.getInstance().isDialogMuted((long) (-this.chat_id)) ? Theme.chat_muteIconDrawable : null);
                        } else if (chat.verified) {
                            this.nameTextView[i3].setRightDrawable(new CombinedDrawable(Theme.profile_verifiedDrawable, Theme.profile_verifiedCheckDrawable));
                        } else {
                            this.nameTextView[i3].setRightDrawable(null);
                        }
                        if (i3 == 0 && string != null) {
                            this.onlineTextView[i3].setText(string);
                        } else if (!this.currentChat.megagroup || this.info == null || this.info.participants_count > Callback.DEFAULT_DRAG_ANIMATION_DURATION || this.onlineCount <= 0) {
                            if (i3 == 0 && ChatObject.isChannel(this.currentChat) && this.info != null && this.info.participants_count != 0 && (this.currentChat.megagroup || this.currentChat.broadcast)) {
                                int[] iArr2 = new int[1];
                                CharSequence formatShortNumber = LocaleController.formatShortNumber(this.info.participants_count, iArr2);
                                if (this.currentChat.megagroup) {
                                    this.onlineTextView[i3].setText(LocaleController.formatPluralString("Members", iArr2[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr2[0])}), formatShortNumber));
                                } else {
                                    this.onlineTextView[i3].setText(LocaleController.formatPluralString("Subscribers", iArr2[0]).replace(String.format("%d", new Object[]{Integer.valueOf(iArr2[0])}), formatShortNumber));
                                }
                            } else if (!this.onlineTextView[i3].getText().equals(string2)) {
                                this.onlineTextView[i3].setText(string2);
                            }
                        } else if (!this.onlineTextView[i3].getText().equals(string2)) {
                            this.onlineTextView[i3].setText(string2);
                        }
                    }
                    i3++;
                }
                tLObject = null;
                FileLocation fileLocation2 = null;
                if (chat.photo != null) {
                    tLObject = chat.photo.photo_small;
                    fileLocation2 = chat.photo.photo_big;
                }
                this.avatarDrawable.setInfo(chat);
                this.avatarImage.setImage(tLObject, "50_50", this.avatarDrawable);
                this.avatarImage.getImageReceiver().setVisible(!PhotoViewer.getInstance().isShowingImage(fileLocation2), false);
            }
        }
    }

    private void updateRowsIds() {
        int i = 0;
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
        if (this.user_id != 0) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            int i2 = this.rowCount;
            this.rowCount = i2 + 1;
            this.emptyRow = i2;
            if (!(this.isBot || TextUtils.isEmpty(user.phone))) {
                i2 = this.rowCount;
                this.rowCount = i2 + 1;
                this.phoneRow = i2;
            }
            TLRPC$TL_userFull userFull = MessagesController.getInstance().getUserFull(this.user_id);
            if (!(user == null || TextUtils.isEmpty(user.username))) {
                i = 1;
            }
            if (!(userFull == null || TextUtils.isEmpty(userFull.about))) {
                int i3;
                if (this.phoneRow != -1) {
                    i3 = this.rowCount;
                    this.rowCount = i3 + 1;
                    this.userSectionRow = i3;
                }
                if (i != 0 || this.isBot) {
                    i3 = this.rowCount;
                    this.rowCount = i3 + 1;
                    this.userInfoRow = i3;
                } else {
                    i3 = this.rowCount;
                    this.rowCount = i3 + 1;
                    this.userInfoDetailedRow = i3;
                }
            }
            if (i != 0) {
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
                    if (!(this.info.participants instanceof TL_chatParticipantsForbidden) && this.info.participants.participants.size() < MessagesController.getInstance().maxGroupCount && (this.currentChat.admin || this.currentChat.creator || !this.currentChat.admins_enabled)) {
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
                if (this.info != null && !(this.info.participants instanceof TL_chatParticipantsForbidden)) {
                    i = this.rowCount;
                    this.rowCount = i + 1;
                    this.emptyRowChat2 = i;
                    this.rowCount += this.info.participants.participants.size();
                    this.membersEndRow = this.rowCount;
                }
            } else if (!ChatObject.isChannel(this.currentChat) && this.info != null && !(this.info.participants instanceof TL_chatParticipantsForbidden)) {
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

    protected ActionBar createActionBar(Context context) {
        ActionBar c51266 = new ActionBar(context) {
            public boolean onTouchEvent(MotionEvent motionEvent) {
                return super.onTouchEvent(motionEvent);
            }
        };
        int i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        c51266.setItemsBackgroundColor(AvatarDrawable.getButtonColorForId(i), false);
        c51266.setItemsColor(Theme.getColor(Theme.key_actionBarDefaultIcon), false);
        c51266.setItemsColor(Theme.getColor(Theme.key_actionBarActionModeDefaultIcon), true);
        c51266.setBackButtonDrawable(new BackDrawable(false));
        c51266.setCastShadows(false);
        c51266.setAddToContainer(false);
        boolean z = VERSION.SDK_INT >= 21 && !AndroidUtilities.isTablet();
        c51266.setOccupyStatusBar(z);
        return c51266;
    }

    public View createView(Context context) {
        this.channelIsFilter = false;
        if (!BuildConfig.FLAVOR.contentEquals("vip") && C3791b.ap(ApplicationLoader.applicationContext)) {
            if (C2491a.c((long) Math.abs(this.chat_id))) {
                this.channelIsFilter = true;
                return createView2(context);
            }
            new Handler().postDelayed(new C51287(), 1000);
        }
        if (this.currentChat == null || !ChatObject.isChannel(this.currentChat) || !C2491a.c((long) Math.abs(this.currentChat.id))) {
            return createView2(context);
        }
        this.channelIsFilter = true;
        return createView2(context);
    }

    public View createView2(Context context) {
        Theme.createProfileResources(context);
        this.hasOwnBackground = true;
        this.extraHeight = AndroidUtilities.dp(88.0f);
        this.actionBar.setActionBarMenuOnItemClick(new C51328());
        createActionBarMenu();
        this.listAdapter = new ListAdapter(context);
        this.avatarDrawable = new AvatarDrawable();
        this.avatarDrawable.setProfile(true);
        this.fragmentView = new FrameLayout(context) {
            public boolean hasOverlappingRendering() {
                return false;
            }

            protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
                super.onLayout(z, i, i2, i3, i4);
                ProfileActivity.this.checkListViewScroll();
            }
        };
        FrameLayout frameLayout = (FrameLayout) this.fragmentView;
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
        int i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        recyclerListView.setGlowColor(AvatarDrawable.getProfileBackColorForId(i));
        frameLayout.addView(this.listView, LayoutHelper.createFrame(-1, -1, 51));
        this.listView.setAdapter(this.listAdapter);
        this.listView.setOnItemClickListener(new OnItemClickListener() {

            /* renamed from: org.telegram.ui.ProfileActivity$12$2 */
            class C51132 implements OnClickListener {
                C51132() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    ProfileActivity.this.creatingChat = true;
                    SecretChatHelper.getInstance().startSecretChat(ProfileActivity.this.getParentActivity(), MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id)));
                }
            }

            /* renamed from: org.telegram.ui.ProfileActivity$12$3 */
            class C51143 implements OnClickListener {
                C51143() {
                }

                public void onClick(DialogInterface dialogInterface, int i) {
                    MessagesController.getInstance().convertToMegaGroup(ProfileActivity.this.getParentActivity(), ProfileActivity.this.chat_id);
                }
            }

            public void onItemClick(View view, int i) {
                if (ProfileActivity.this.getParentActivity() != null) {
                    if (i == ProfileActivity.this.sharedMediaRow) {
                        Bundle bundle = new Bundle();
                        if (ProfileActivity.this.user_id != 0) {
                            bundle.putLong("dialog_id", ProfileActivity.this.dialog_id != 0 ? ProfileActivity.this.dialog_id : (long) ProfileActivity.this.user_id);
                        } else {
                            bundle.putLong("dialog_id", (long) (-ProfileActivity.this.chat_id));
                        }
                        BaseFragment mediaActivity = new MediaActivity(bundle);
                        mediaActivity.setChatInfo(ProfileActivity.this.info);
                        ProfileActivity.this.presentFragment(mediaActivity);
                    } else if (i == ProfileActivity.this.groupsInCommonRow) {
                        ProfileActivity.this.presentFragment(new CommonGroupsActivity(ProfileActivity.this.user_id));
                    } else if (i == ProfileActivity.this.settingsKeyRow) {
                        r0 = new Bundle();
                        r0.putInt("chat_id", (int) (ProfileActivity.this.dialog_id >> 32));
                        ProfileActivity.this.presentFragment(new IdenticonActivity(r0));
                    } else if (i == ProfileActivity.this.settingsTimerRow) {
                        ProfileActivity.this.showDialog(AlertsCreator.createTTLAlert(ProfileActivity.this.getParentActivity(), ProfileActivity.this.currentEncryptedChat).create());
                    } else if (i == ProfileActivity.this.settingsNotificationsRow) {
                        long access$1400 = ProfileActivity.this.dialog_id != 0 ? ProfileActivity.this.dialog_id : ProfileActivity.this.user_id != 0 ? (long) ProfileActivity.this.user_id : (long) (-ProfileActivity.this.chat_id);
                        String[] strArr = new String[5];
                        strArr[0] = LocaleController.getString("NotificationsTurnOn", R.string.NotificationsTurnOn);
                        strArr[1] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Hours", 1)});
                        strArr[2] = LocaleController.formatString("MuteFor", R.string.MuteFor, new Object[]{LocaleController.formatPluralString("Days", 2)});
                        strArr[3] = LocaleController.getString("NotificationsCustomize", R.string.NotificationsCustomize);
                        strArr[4] = LocaleController.getString("NotificationsTurnOff", R.string.NotificationsTurnOff);
                        int[] iArr = new int[]{R.drawable.notifications_s_on, R.drawable.notifications_s_1h, R.drawable.notifications_s_2d, R.drawable.notifications_s_custom, R.drawable.notifications_s_off};
                        View linearLayout = new LinearLayout(ProfileActivity.this.getParentActivity());
                        linearLayout.setOrientation(1);
                        for (int i2 = 0; i2 < strArr.length; i2++) {
                            View textView = new TextView(ProfileActivity.this.getParentActivity());
                            textView.setTextColor(Theme.getColor(Theme.key_dialogTextBlack));
                            textView.setTextSize(1, 16.0f);
                            textView.setLines(1);
                            textView.setMaxLines(1);
                            Drawable drawable = ProfileActivity.this.getParentActivity().getResources().getDrawable(iArr[i2]);
                            drawable.setColorFilter(new PorterDuffColorFilter(Theme.getColor(Theme.key_dialogIcon), Mode.MULTIPLY));
                            textView.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
                            textView.setTag(Integer.valueOf(i2));
                            textView.setBackgroundDrawable(Theme.getSelectorDrawable(false));
                            textView.setPadding(AndroidUtilities.dp(24.0f), 0, AndroidUtilities.dp(24.0f), 0);
                            textView.setSingleLine(true);
                            textView.setGravity(19);
                            textView.setCompoundDrawablePadding(AndroidUtilities.dp(26.0f));
                            textView.setText(strArr[i2]);
                            linearLayout.addView(textView, LayoutHelper.createLinear(-1, 48, 51));
                            textView.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View view) {
                                    long j = 1;
                                    int intValue = ((Integer) view.getTag()).intValue();
                                    Editor edit;
                                    TLRPC$TL_dialog tLRPC$TL_dialog;
                                    if (intValue == 0) {
                                        edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                        edit.putInt("notify2_" + access$1400, 0);
                                        MessagesStorage.getInstance().setDialogFlags(access$1400, 0);
                                        edit.commit();
                                        tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(access$1400));
                                        if (tLRPC$TL_dialog != null) {
                                            tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                                        }
                                        NotificationsController.updateServerNotificationsSettings(access$1400);
                                    } else if (intValue == 3) {
                                        Bundle bundle = new Bundle();
                                        bundle.putLong("dialog_id", access$1400);
                                        ProfileActivity.this.presentFragment(new ProfileNotificationsActivity(bundle));
                                    } else {
                                        int currentTime = ConnectionsManager.getInstance().getCurrentTime();
                                        int i = intValue == 1 ? currentTime + 3600 : intValue == 2 ? currentTime + 172800 : intValue == 4 ? Integer.MAX_VALUE : currentTime;
                                        edit = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                                        if (intValue == 4) {
                                            edit.putInt("notify2_" + access$1400, 2);
                                        } else {
                                            edit.putInt("notify2_" + access$1400, 3);
                                            edit.putInt("notifyuntil_" + access$1400, i);
                                            j = 1 | (((long) i) << 32);
                                        }
                                        NotificationsController.getInstance().removeNotificationsForDialog(access$1400);
                                        MessagesStorage.getInstance().setDialogFlags(access$1400, j);
                                        edit.commit();
                                        tLRPC$TL_dialog = (TLRPC$TL_dialog) MessagesController.getInstance().dialogs_dict.get(Long.valueOf(access$1400));
                                        if (tLRPC$TL_dialog != null) {
                                            tLRPC$TL_dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                                            tLRPC$TL_dialog.notify_settings.mute_until = i;
                                        }
                                        NotificationsController.updateServerNotificationsSettings(access$1400);
                                    }
                                    ProfileActivity.this.listAdapter.notifyItemChanged(ProfileActivity.this.settingsNotificationsRow);
                                    ProfileActivity.this.dismissCurrentDialig();
                                }
                            });
                        }
                        r0 = new Builder(ProfileActivity.this.getParentActivity());
                        r0.setTitle(LocaleController.getString("Notifications", R.string.Notifications));
                        r0.setView(linearLayout);
                        ProfileActivity.this.showDialog(r0.create());
                    } else if (i == ProfileActivity.this.startSecretChatRow) {
                        r0 = new Builder(ProfileActivity.this.getParentActivity());
                        r0.setMessage(LocaleController.getString("AreYouSureSecretChat", R.string.AreYouSureSecretChat));
                        r0.setTitle(LocaleController.getString("AppName", R.string.AppName));
                        r0.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C51132());
                        r0.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(r0.create());
                    } else if (i > ProfileActivity.this.emptyRowChat2 && i < ProfileActivity.this.membersEndRow) {
                        int i3 = !ProfileActivity.this.sortedUsers.isEmpty() ? ((ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((i - ProfileActivity.this.emptyRowChat2) - 1)).intValue())).user_id : ((ChatParticipant) ProfileActivity.this.info.participants.participants.get((i - ProfileActivity.this.emptyRowChat2) - 1)).user_id;
                        if (i3 != UserConfig.getClientUserId()) {
                            Bundle bundle2 = new Bundle();
                            bundle2.putInt("user_id", i3);
                            ProfileActivity.this.presentFragment(new ProfileActivity(bundle2));
                        }
                    } else if (i == ProfileActivity.this.addMemberRow) {
                        ProfileActivity.this.openAddMember();
                    } else if (i == ProfileActivity.this.channelNameRow) {
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            if (ProfileActivity.this.info.about == null || ProfileActivity.this.info.about.length() <= 0) {
                                intent.putExtra("android.intent.extra.TEXT", ProfileActivity.this.currentChat.title + "\nhttps://" + MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
                            } else {
                                intent.putExtra("android.intent.extra.TEXT", ProfileActivity.this.currentChat.title + "\n" + ProfileActivity.this.info.about + "\nhttps://" + MessagesController.getInstance().linkPrefix + "/" + ProfileActivity.this.currentChat.username);
                            }
                            ProfileActivity.this.getParentActivity().startActivityForResult(Intent.createChooser(intent, LocaleController.getString("BotShare", R.string.BotShare)), ChatActivity.startAllServices);
                        } catch (Throwable e) {
                            FileLog.e(e);
                        }
                    } else if (i == ProfileActivity.this.leaveChannelRow) {
                        ProfileActivity.this.leaveChatPressed();
                    } else if (i == ProfileActivity.this.membersRow) {
                        r0 = new Bundle();
                        r0.putInt("chat_id", ProfileActivity.this.chat_id);
                        r0.putInt(Param.TYPE, 2);
                        ProfileActivity.this.presentFragment(new ChannelUsersActivity(r0));
                    } else if (i == ProfileActivity.this.convertRow) {
                        r0 = new Builder(ProfileActivity.this.getParentActivity());
                        r0.setMessage(LocaleController.getString("ConvertGroupAlert", R.string.ConvertGroupAlert));
                        r0.setTitle(LocaleController.getString("ConvertGroupAlertWarning", R.string.ConvertGroupAlertWarning));
                        r0.setPositiveButton(LocaleController.getString("OK", R.string.OK), new C51143());
                        r0.setNegativeButton(LocaleController.getString("Cancel", R.string.Cancel), null);
                        ProfileActivity.this.showDialog(r0.create());
                    } else {
                        ProfileActivity.this.processOnClickOrPress(i);
                    }
                }
            }
        });
        this.listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemClick(View view, int i) {
                if (i <= ProfileActivity.this.emptyRowChat2 || i >= ProfileActivity.this.membersEndRow) {
                    return ProfileActivity.this.processOnClickOrPress(i);
                }
                if (ProfileActivity.this.getParentActivity() == null) {
                    return false;
                }
                boolean z;
                ChannelParticipant channelParticipant;
                final ChatParticipant chatParticipant = !ProfileActivity.this.sortedUsers.isEmpty() ? (ChatParticipant) ProfileActivity.this.info.participants.participants.get(((Integer) ProfileActivity.this.sortedUsers.get((i - ProfileActivity.this.emptyRowChat2) - 1)).intValue()) : (ChatParticipant) ProfileActivity.this.info.participants.participants.get((i - ProfileActivity.this.emptyRowChat2) - 1);
                ProfileActivity.this.selectedUser = chatParticipant.user_id;
                boolean z2;
                boolean z3;
                if (ChatObject.isChannel(ProfileActivity.this.currentChat)) {
                    ChannelParticipant channelParticipant2 = ((TL_chatChannelParticipant) chatParticipant).channelParticipant;
                    if (chatParticipant.user_id == UserConfig.getClientUserId()) {
                        return false;
                    }
                    MessagesController.getInstance().getUser(Integer.valueOf(chatParticipant.user_id));
                    z2 = (channelParticipant2 instanceof TL_channelParticipant) || (channelParticipant2 instanceof TL_channelParticipantBanned);
                    z3 = !((channelParticipant2 instanceof TL_channelParticipantAdmin) || (channelParticipant2 instanceof TL_channelParticipantCreator)) || channelParticipant2.can_edit;
                    ChannelParticipant channelParticipant3 = channelParticipant2;
                    z = z2;
                    z2 = z3;
                    channelParticipant = channelParticipant3;
                } else {
                    int i2;
                    if (chatParticipant.user_id != UserConfig.getClientUserId()) {
                        if (ProfileActivity.this.currentChat.creator) {
                            i2 = 1;
                        } else if ((chatParticipant instanceof TL_chatParticipant) && ((ProfileActivity.this.currentChat.admin && ProfileActivity.this.currentChat.admins_enabled) || chatParticipant.inviter_id == UserConfig.getClientUserId())) {
                            i2 = 1;
                        }
                        if (i2 != 0) {
                            return false;
                        }
                        channelParticipant = null;
                        z = false;
                        z2 = false;
                    }
                    z3 = false;
                    if (i2 != 0) {
                        return false;
                    }
                    channelParticipant = null;
                    z = false;
                    z2 = false;
                }
                Builder builder = new Builder(ProfileActivity.this.getParentActivity());
                ArrayList arrayList = new ArrayList();
                final ArrayList arrayList2 = new ArrayList();
                if (ProfileActivity.this.currentChat.megagroup) {
                    if (z && ChatObject.canAddAdmins(ProfileActivity.this.currentChat)) {
                        arrayList.add(LocaleController.getString("SetAsAdmin", R.string.SetAsAdmin));
                        arrayList2.add(Integer.valueOf(0));
                    }
                    if (ChatObject.canBlockUsers(ProfileActivity.this.currentChat) && r0) {
                        arrayList.add(LocaleController.getString("KickFromSupergroup", R.string.KickFromSupergroup));
                        arrayList2.add(Integer.valueOf(1));
                        arrayList.add(LocaleController.getString("KickFromGroup", R.string.KickFromGroup));
                        arrayList2.add(Integer.valueOf(2));
                    }
                } else {
                    arrayList.add(ProfileActivity.this.chat_id > 0 ? LocaleController.getString("KickFromGroup", R.string.KickFromGroup) : LocaleController.getString("KickFromBroadcast", R.string.KickFromBroadcast));
                    arrayList2.add(Integer.valueOf(2));
                }
                if (arrayList.isEmpty()) {
                    return false;
                }
                builder.setItems((CharSequence[]) arrayList.toArray(new CharSequence[arrayList.size()]), new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, final int i) {
                        if (((Integer) arrayList2.get(i)).intValue() == 2) {
                            ProfileActivity.this.kickUser(ProfileActivity.this.selectedUser);
                            return;
                        }
                        BaseFragment channelRightsEditActivity = new ChannelRightsEditActivity(chatParticipant.user_id, ProfileActivity.this.chat_id, channelParticipant.admin_rights, channelParticipant.banned_rights, ((Integer) arrayList2.get(i)).intValue(), true);
                        channelRightsEditActivity.setDelegate(new ChannelRightsEditActivityDelegate() {
                            public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                                int i2 = 0;
                                if (((Integer) arrayList2.get(i)).intValue() == 0) {
                                    TL_chatChannelParticipant tL_chatChannelParticipant = (TL_chatChannelParticipant) chatParticipant;
                                    if (i == 1) {
                                        tL_chatChannelParticipant.channelParticipant = new TL_channelParticipantAdmin();
                                    } else {
                                        tL_chatChannelParticipant.channelParticipant = new TL_channelParticipant();
                                    }
                                    tL_chatChannelParticipant.channelParticipant.inviter_id = UserConfig.getClientUserId();
                                    tL_chatChannelParticipant.channelParticipant.user_id = chatParticipant.user_id;
                                    tL_chatChannelParticipant.channelParticipant.date = chatParticipant.date;
                                    tL_chatChannelParticipant.channelParticipant.banned_rights = tL_channelBannedRights;
                                    tL_chatChannelParticipant.channelParticipant.admin_rights = tL_channelAdminRights;
                                } else if (((Integer) arrayList2.get(i)).intValue() == 1 && i == 0 && ProfileActivity.this.currentChat.megagroup && ProfileActivity.this.info != null && ProfileActivity.this.info.participants != null) {
                                    int i3;
                                    for (i3 = 0; i3 < ProfileActivity.this.info.participants.participants.size(); i3++) {
                                        if (((TL_chatChannelParticipant) ProfileActivity.this.info.participants.participants.get(i3)).channelParticipant.user_id == chatParticipant.user_id) {
                                            if (ProfileActivity.this.info != null) {
                                                ChatFull access$900 = ProfileActivity.this.info;
                                                access$900.participants_count--;
                                            }
                                            ProfileActivity.this.info.participants.participants.remove(i3);
                                            i3 = 1;
                                            if (ProfileActivity.this.info != null && ProfileActivity.this.info.participants != null) {
                                                while (i2 < ProfileActivity.this.info.participants.participants.size()) {
                                                    if (((ChatParticipant) ProfileActivity.this.info.participants.participants.get(i2)).user_id == chatParticipant.user_id) {
                                                        ProfileActivity.this.info.participants.participants.remove(i2);
                                                        i3 = 1;
                                                        break;
                                                    }
                                                    i2++;
                                                }
                                            }
                                            if (i3 != 0) {
                                                ProfileActivity.this.updateOnlineCount();
                                                ProfileActivity.this.updateRowsIds();
                                                ProfileActivity.this.listAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                    i3 = 0;
                                    while (i2 < ProfileActivity.this.info.participants.participants.size()) {
                                        if (((ChatParticipant) ProfileActivity.this.info.participants.participants.get(i2)).user_id == chatParticipant.user_id) {
                                            ProfileActivity.this.info.participants.participants.remove(i2);
                                            i3 = 1;
                                            break;
                                        }
                                        i2++;
                                    }
                                    if (i3 != 0) {
                                        ProfileActivity.this.updateOnlineCount();
                                        ProfileActivity.this.updateRowsIds();
                                        ProfileActivity.this.listAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        });
                        ProfileActivity.this.presentFragment(channelRightsEditActivity);
                    }
                });
                ProfileActivity.this.showDialog(builder.create());
                return true;
            }
        });
        if (this.banFromGroup != 0) {
            if (this.currentChannelParticipant == null) {
                TLObject tL_channels_getParticipant = new TL_channels_getParticipant();
                tL_channels_getParticipant.channel = MessagesController.getInputChannel(this.banFromGroup);
                tL_channels_getParticipant.user_id = MessagesController.getInputUser(this.user_id);
                ConnectionsManager.getInstance().sendRequest(tL_channels_getParticipant, new RequestDelegate() {
                    public void run(final TLObject tLObject, TLRPC$TL_error tLRPC$TL_error) {
                        if (tLObject != null) {
                            AndroidUtilities.runOnUIThread(new Runnable() {
                                public void run() {
                                    ProfileActivity.this.currentChannelParticipant = ((TL_channels_channelParticipant) tLObject).participant;
                                }
                            });
                        }
                    }
                });
            }
            View anonymousClass15 = new FrameLayout(context) {
                protected void onDraw(Canvas canvas) {
                    int intrinsicHeight = Theme.chat_composeShadowDrawable.getIntrinsicHeight();
                    Theme.chat_composeShadowDrawable.setBounds(0, 0, getMeasuredWidth(), intrinsicHeight);
                    Theme.chat_composeShadowDrawable.draw(canvas);
                    canvas.drawRect(BitmapDescriptorFactory.HUE_RED, (float) intrinsicHeight, (float) getMeasuredWidth(), (float) getMeasuredHeight(), Theme.chat_composeBackgroundPaint);
                }
            };
            anonymousClass15.setWillNotDraw(false);
            frameLayout.addView(anonymousClass15, LayoutHelper.createFrame(-1, 51, 83));
            anonymousClass15.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ProfileActivity$16$1 */
                class C51181 implements ChannelRightsEditActivityDelegate {
                    C51181() {
                    }

                    public void didSetRights(int i, TL_channelAdminRights tL_channelAdminRights, TL_channelBannedRights tL_channelBannedRights) {
                        ProfileActivity.this.removeSelfFromStack();
                    }
                }

                public void onClick(View view) {
                    BaseFragment channelRightsEditActivity = new ChannelRightsEditActivity(ProfileActivity.this.user_id, ProfileActivity.this.banFromGroup, null, ProfileActivity.this.currentChannelParticipant != null ? ProfileActivity.this.currentChannelParticipant.banned_rights : null, 1, true);
                    channelRightsEditActivity.setDelegate(new C51181());
                    ProfileActivity.this.presentFragment(channelRightsEditActivity);
                }
            });
            View textView = new TextView(context);
            textView.setTextColor(Theme.getColor(Theme.key_windowBackgroundWhiteRedText));
            textView.setTextSize(1, 15.0f);
            textView.setGravity(17);
            textView.setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
            textView.setText(LocaleController.getString("BanFromTheGroup", R.string.BanFromTheGroup));
            anonymousClass15.addView(textView, LayoutHelper.createFrame(-2, -2.0f, 17, BitmapDescriptorFactory.HUE_RED, 1.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
            this.listView.setPadding(0, AndroidUtilities.dp(88.0f), 0, AndroidUtilities.dp(48.0f));
            this.listView.setBottomGlowOffset(AndroidUtilities.dp(48.0f));
        } else {
            this.listView.setPadding(0, AndroidUtilities.dp(88.0f), 0, 0);
        }
        this.topView = new TopView(context);
        TopView topView = this.topView;
        i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        topView.setBackgroundColor(AvatarDrawable.getProfileBackColorForId(i));
        frameLayout.addView(this.topView);
        frameLayout.addView(this.actionBar);
        this.avatarImage = new BackupImageView(context);
        this.avatarImage.setRoundRadius(AndroidUtilities.dp(21.0f));
        this.avatarImage.setPivotX(BitmapDescriptorFactory.HUE_RED);
        this.avatarImage.setPivotY(BitmapDescriptorFactory.HUE_RED);
        frameLayout.addView(this.avatarImage, LayoutHelper.createFrame(42, 42.0f, 51, 64.0f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
        this.avatarImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ProfileActivity.this.user_id != 0) {
                    User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                    if (user.photo != null && user.photo.photo_big != null) {
                        PhotoViewer.getInstance().setParentActivity(ProfileActivity.this.getParentActivity());
                        PhotoViewer.getInstance().openPhoto(user.photo.photo_big, ProfileActivity.this.provider);
                    }
                } else if (ProfileActivity.this.chat_id != 0) {
                    Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                    if (chat.photo != null && chat.photo.photo_big != null) {
                        PhotoViewer.getInstance().setParentActivity(ProfileActivity.this.getParentActivity());
                        PhotoViewer.getInstance().openPhoto(chat.photo.photo_big, ProfileActivity.this.provider);
                    }
                }
            }
        });
        int i2 = 0;
        while (i2 < 2) {
            if (this.playProfileAnimation || i2 != 0) {
                this.nameTextView[i2] = new SimpleTextView(context);
                if (i2 == 1) {
                    this.nameTextView[i2].setTextColor(Theme.getColor(Theme.key_profile_title));
                } else {
                    this.nameTextView[i2].setTextColor(Theme.getColor(Theme.key_actionBarDefaultTitle));
                }
                this.nameTextView[i2].setTextSize(18);
                this.nameTextView[i2].setGravity(3);
                this.nameTextView[i2].setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.nameTextView[i2].setLeftDrawableTopPadding(-AndroidUtilities.dp(1.3f));
                this.nameTextView[i2].setPivotX(BitmapDescriptorFactory.HUE_RED);
                this.nameTextView[i2].setPivotY(BitmapDescriptorFactory.HUE_RED);
                this.nameTextView[i2].setAlpha(i2 == 0 ? BitmapDescriptorFactory.HUE_RED : 1.0f);
                frameLayout.addView(this.nameTextView[i2], LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, BitmapDescriptorFactory.HUE_RED, i2 == 0 ? 48.0f : BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED));
                this.onlineTextView[i2] = new SimpleTextView(context);
                SimpleTextView simpleTextView = this.onlineTextView[i2];
                i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
                simpleTextView.setTextColor(AvatarDrawable.getProfileTextColorForId(i));
                this.onlineTextView[i2].setTypeface(AndroidUtilities.getTypeface("fonts/rmedium.ttf"));
                this.onlineTextView[i2].setTextSize(14);
                this.onlineTextView[i2].setGravity(3);
                this.onlineTextView[i2].setAlpha(i2 == 0 ? BitmapDescriptorFactory.HUE_RED : 1.0f);
                frameLayout.addView(this.onlineTextView[i2], LayoutHelper.createFrame(-2, -2.0f, 51, 118.0f, BitmapDescriptorFactory.HUE_RED, i2 == 0 ? 48.0f : 8.0f, BitmapDescriptorFactory.HUE_RED));
            }
            i2++;
        }
        if (this.user_id != 0 || (this.chat_id >= 0 && (!ChatObject.isLeftFromChat(this.currentChat) || ChatObject.isChannel(this.currentChat)))) {
            Drawable combinedDrawable;
            this.writeButton = new ImageView(context);
            Drawable createSimpleSelectorCircleDrawable = Theme.createSimpleSelectorCircleDrawable(AndroidUtilities.dp(56.0f), Theme.getColor(Theme.key_profile_actionBackground), Theme.getColor(Theme.key_profile_actionPressedBackground));
            if (VERSION.SDK_INT < 21) {
                Drawable mutate = context.getResources().getDrawable(R.drawable.floating_shadow_profile).mutate();
                mutate.setColorFilter(new PorterDuffColorFilter(Theme.ACTION_BAR_VIDEO_EDIT_COLOR, Mode.MULTIPLY));
                combinedDrawable = new CombinedDrawable(mutate, createSimpleSelectorCircleDrawable, 0, 0);
                combinedDrawable.setIconSize(AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
            } else {
                combinedDrawable = createSimpleSelectorCircleDrawable;
            }
            this.writeButton.setBackgroundDrawable(combinedDrawable);
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
            frameLayout.addView(this.writeButton, LayoutHelper.createFrame(VERSION.SDK_INT >= 21 ? 56 : 60, VERSION.SDK_INT >= 21 ? 56.0f : 60.0f, 53, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED, 16.0f, BitmapDescriptorFactory.HUE_RED));
            if (VERSION.SDK_INT >= 21) {
                StateListAnimator stateListAnimator = new StateListAnimator();
                stateListAnimator.addState(new int[]{16842919}, ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(2.0f), (float) AndroidUtilities.dp(4.0f)}).setDuration(200));
                stateListAnimator.addState(new int[0], ObjectAnimator.ofFloat(this.writeButton, "translationZ", new float[]{(float) AndroidUtilities.dp(4.0f), (float) AndroidUtilities.dp(2.0f)}).setDuration(200));
                this.writeButton.setStateListAnimator(stateListAnimator);
                this.writeButton.setOutlineProvider(new ViewOutlineProvider() {
                    @SuppressLint({"NewApi"})
                    public void getOutline(View view, Outline outline) {
                        outline.setOval(0, 0, AndroidUtilities.dp(56.0f), AndroidUtilities.dp(56.0f));
                    }
                });
            }
            this.writeButton.setOnClickListener(new View.OnClickListener() {

                /* renamed from: org.telegram.ui.ProfileActivity$19$1 */
                class C51191 implements OnClickListener {
                    C51191() {
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

                public void onClick(View view) {
                    if (ProfileActivity.this.getParentActivity() != null) {
                        Bundle bundle;
                        if (ProfileActivity.this.user_id != 0) {
                            if (ProfileActivity.this.playProfileAnimation && (ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2) instanceof ChatActivity)) {
                                ProfileActivity.this.finishFragment();
                                return;
                            }
                            User user = MessagesController.getInstance().getUser(Integer.valueOf(ProfileActivity.this.user_id));
                            if (user != null && !(user instanceof TLRPC$TL_userEmpty)) {
                                bundle = new Bundle();
                                bundle.putInt("user_id", ProfileActivity.this.user_id);
                                if (MessagesController.checkCanOpenChat(bundle, ProfileActivity.this)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    ProfileActivity.this.presentFragment(new ChatActivity(bundle), true);
                                }
                            }
                        } else if (ProfileActivity.this.chat_id != 0) {
                            boolean isChannel = ChatObject.isChannel(ProfileActivity.this.currentChat);
                            if ((!isChannel || ChatObject.canEditInfo(ProfileActivity.this.currentChat)) && (isChannel || ProfileActivity.this.currentChat.admin || ProfileActivity.this.currentChat.creator || !ProfileActivity.this.currentChat.admins_enabled)) {
                                Builder builder = new Builder(ProfileActivity.this.getParentActivity());
                                Chat chat = MessagesController.getInstance().getChat(Integer.valueOf(ProfileActivity.this.chat_id));
                                CharSequence[] charSequenceArr = (chat.photo == null || chat.photo.photo_big == null || (chat.photo instanceof TL_chatPhotoEmpty)) ? new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley)} : new CharSequence[]{LocaleController.getString("FromCamera", R.string.FromCamera), LocaleController.getString("FromGalley", R.string.FromGalley), LocaleController.getString("DeletePhoto", R.string.DeletePhoto)};
                                builder.setItems(charSequenceArr, new C51191());
                                ProfileActivity.this.showDialog(builder.create());
                            } else if (ProfileActivity.this.playProfileAnimation && (ProfileActivity.this.parentLayout.fragmentsStack.get(ProfileActivity.this.parentLayout.fragmentsStack.size() - 2) instanceof ChatActivity)) {
                                ProfileActivity.this.finishFragment();
                            } else {
                                bundle = new Bundle();
                                bundle.putInt("chat_id", ProfileActivity.this.currentChat.id);
                                if (MessagesController.checkCanOpenChat(bundle, ProfileActivity.this)) {
                                    NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                                    ProfileActivity.this.presentFragment(new ChatActivity(bundle), true);
                                }
                            }
                        }
                    }
                }
            });
        }
        needLayout();
        this.listView.setOnScrollListener(new OnScrollListener() {
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                ProfileActivity.this.checkListViewScroll();
                if (ProfileActivity.this.participantsMap != null && ProfileActivity.this.loadMoreMembersRow != -1 && ProfileActivity.this.layoutManager.findLastVisibleItemPosition() > ProfileActivity.this.loadMoreMembersRow - 8) {
                    ProfileActivity.this.getChannelParticipants(false);
                }
            }
        });
        return this.fragmentView;
    }

    public void didReceivedNotification(int i, final Object... objArr) {
        int i2 = 0;
        int intValue;
        Holder holder;
        Chat chat;
        if (i == NotificationCenter.updateInterfaces) {
            intValue = ((Integer) objArr[0]).intValue();
            if (this.user_id != 0) {
                if (!((intValue & 2) == 0 && (intValue & 1) == 0 && (intValue & 4) == 0)) {
                    updateProfileData();
                }
                if ((intValue & 1024) != 0 && this.listView != null) {
                    holder = (Holder) this.listView.findViewHolderForPosition(this.phoneRow);
                    if (holder != null) {
                        this.listAdapter.onBindViewHolder(holder, this.phoneRow);
                    }
                }
            } else if (this.chat_id != 0) {
                if ((intValue & MessagesController.UPDATE_MASK_CHAT_ADMINS) != 0) {
                    chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                    if (chat != null) {
                        this.currentChat = chat;
                        createActionBarMenu();
                        updateRowsIds();
                        if (this.listAdapter != null) {
                            this.listAdapter.notifyDataSetChanged();
                        }
                    }
                }
                if (!((intValue & MessagesController.UPDATE_MASK_CHANNEL) == 0 && (intValue & 8) == 0 && (intValue & 16) == 0 && (intValue & 32) == 0 && (intValue & 4) == 0)) {
                    updateOnlineCount();
                    updateProfileData();
                }
                if ((intValue & MessagesController.UPDATE_MASK_CHANNEL) != 0) {
                    updateRowsIds();
                    if (this.listAdapter != null) {
                        this.listAdapter.notifyDataSetChanged();
                    }
                }
                if (((intValue & 2) != 0 || (intValue & 1) != 0 || (intValue & 4) != 0) && this.listView != null) {
                    int childCount = this.listView.getChildCount();
                    while (i2 < childCount) {
                        View childAt = this.listView.getChildAt(i2);
                        if (childAt instanceof UserCell) {
                            ((UserCell) childAt).update(intValue);
                        }
                        i2++;
                    }
                }
            }
        } else if (i == NotificationCenter.contactsDidLoaded) {
            createActionBarMenu();
        } else if (i == NotificationCenter.mediaCountDidLoaded) {
            long longValue = ((Long) objArr[0]).longValue();
            long j = this.dialog_id;
            if (j == 0) {
                if (this.user_id != 0) {
                    j = (long) this.user_id;
                } else if (this.chat_id != 0) {
                    j = (long) (-this.chat_id);
                }
            }
            if (longValue == j || longValue == this.mergeDialogId) {
                if (longValue == j) {
                    this.totalMediaCount = ((Integer) objArr[1]).intValue();
                } else {
                    this.totalMediaCountMerge = ((Integer) objArr[1]).intValue();
                }
                if (this.listView != null) {
                    intValue = this.listView.getChildCount();
                    while (i2 < intValue) {
                        holder = (Holder) this.listView.getChildViewHolder(this.listView.getChildAt(i2));
                        if (holder.getAdapterPosition() == this.sharedMediaRow) {
                            this.listAdapter.onBindViewHolder(holder, this.sharedMediaRow);
                            return;
                        }
                        i2++;
                    }
                }
            }
        } else if (i == NotificationCenter.encryptedChatCreated) {
            if (this.creatingChat) {
                AndroidUtilities.runOnUIThread(new Runnable() {
                    public void run() {
                        NotificationCenter.getInstance().removeObserver(ProfileActivity.this, NotificationCenter.closeChats);
                        NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
                        EncryptedChat encryptedChat = (EncryptedChat) objArr[0];
                        Bundle bundle = new Bundle();
                        bundle.putInt("enc_id", encryptedChat.id);
                        ProfileActivity.this.presentFragment(new ChatActivity(bundle), true);
                    }
                });
            }
        } else if (i == NotificationCenter.encryptedChatUpdated) {
            EncryptedChat encryptedChat = (EncryptedChat) objArr[0];
            if (this.currentEncryptedChat != null && encryptedChat.id == this.currentEncryptedChat.id) {
                this.currentEncryptedChat = encryptedChat;
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
            }
        } else if (i == NotificationCenter.blockedUsersDidLoaded) {
            boolean z = this.userBlocked;
            this.userBlocked = MessagesController.getInstance().blockedUsers.contains(Integer.valueOf(this.user_id));
            if (z != this.userBlocked) {
                createActionBarMenu();
            }
        } else if (i == NotificationCenter.chatInfoDidLoaded) {
            ChatFull chatFull = (ChatFull) objArr[0];
            if (chatFull.id == this.chat_id) {
                boolean booleanValue = ((Boolean) objArr[2]).booleanValue();
                if ((this.info instanceof TL_channelFull) && chatFull.participants == null && this.info != null) {
                    chatFull.participants = this.info.participants;
                }
                boolean z2 = this.info == null && (chatFull instanceof TL_channelFull);
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
                chat = MessagesController.getInstance().getChat(Integer.valueOf(this.chat_id));
                if (chat != null) {
                    this.currentChat = chat;
                    createActionBarMenu();
                }
                if (!this.currentChat.megagroup) {
                    return;
                }
                if (z2 || !booleanValue) {
                    getChannelParticipants(true);
                }
            }
        } else if (i == NotificationCenter.closeChats) {
            removeSelfFromStack();
        } else if (i == NotificationCenter.botInfoDidLoaded) {
            BotInfo botInfo = (BotInfo) objArr[0];
            if (botInfo.user_id == this.user_id) {
                this.botInfo = botInfo;
                updateRowsIds();
                if (this.listAdapter != null) {
                    this.listAdapter.notifyDataSetChanged();
                }
            }
        } else if (i == NotificationCenter.userInfoDidLoaded) {
            if (((Integer) objArr[0]).intValue() == this.user_id) {
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
        } else if (i == NotificationCenter.didReceivedNewMessages && ((Long) objArr[0]).longValue() == this.dialog_id) {
            ArrayList arrayList = (ArrayList) objArr[1];
            while (i2 < arrayList.size()) {
                MessageObject messageObject = (MessageObject) arrayList.get(i2);
                if (this.currentEncryptedChat != null && messageObject.messageOwner.action != null && (messageObject.messageOwner.action instanceof TLRPC$TL_messageEncryptedAction) && (messageObject.messageOwner.action.encryptedAction instanceof TLRPC$TL_decryptedMessageActionSetMessageTTL)) {
                    TLRPC$TL_decryptedMessageActionSetMessageTTL tLRPC$TL_decryptedMessageActionSetMessageTTL = (TLRPC$TL_decryptedMessageActionSetMessageTTL) messageObject.messageOwner.action.encryptedAction;
                    if (this.listAdapter != null) {
                        this.listAdapter.notifyDataSetChanged();
                    }
                }
                i2++;
            }
        }
    }

    public void didSelectDialogs(DialogsActivity dialogsActivity, ArrayList<Long> arrayList, CharSequence charSequence, boolean z) {
        long longValue = ((Long) arrayList.get(0)).longValue();
        Bundle bundle = new Bundle();
        bundle.putBoolean("scrollToTopOnResume", true);
        int i = (int) longValue;
        if (i == 0) {
            bundle.putInt("enc_id", (int) (longValue >> 32));
        } else if (i > 0) {
            bundle.putInt("user_id", i);
        } else if (i < 0) {
            bundle.putInt("chat_id", -i);
        }
        if (MessagesController.checkCanOpenChat(bundle, dialogsActivity)) {
            NotificationCenter.getInstance().removeObserver(this, NotificationCenter.closeChats);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, new Object[0]);
            presentFragment(new ChatActivity(bundle), true);
            removeSelfFromStack();
            SendMessagesHelper.getInstance().sendMessage(MessagesController.getInstance().getUser(Integer.valueOf(this.user_id)), longValue, null, null, null);
        }
    }

    public float getAnimationProgress() {
        return this.animationProgress;
    }

    public ThemeDescription[] getThemeDescriptions() {
        AnonymousClass33 anonymousClass33 = new ThemeDescriptionDelegate() {
            public void didSetColor(int i) {
                int childCount = ProfileActivity.this.listView.getChildCount();
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = ProfileActivity.this.listView.getChildAt(i2);
                    if (childAt instanceof UserCell) {
                        ((UserCell) childAt).update(0);
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
        r10[70] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusColor"}, null, null, (ThemeDescriptionDelegate) anonymousClass33, Theme.key_windowBackgroundWhiteGrayText);
        r10[71] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, new String[]{"statusOnlineColor"}, null, null, (ThemeDescriptionDelegate) anonymousClass33, Theme.key_windowBackgroundWhiteBlueText);
        r10[72] = new ThemeDescription(this.listView, 0, new Class[]{UserCell.class}, null, new Drawable[]{Theme.avatar_photoDrawable, Theme.avatar_broadcastDrawable, Theme.avatar_savedDrawable}, null, Theme.key_avatar_text);
        r10[73] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundRed);
        r10[74] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundOrange);
        r10[75] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundViolet);
        r10[76] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundGreen);
        r10[77] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundCyan);
        r10[78] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundBlue);
        r10[79] = new ThemeDescription(null, 0, null, null, null, anonymousClass33, Theme.key_avatar_backgroundPink);
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

    public boolean isChat() {
        return this.chat_id != 0;
    }

    public void onActivityResultFragment(int i, int i2, Intent intent) {
        if (this.chat_id != 0) {
            this.avatarUpdater.onActivityResult(i, i2, intent);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        fixLayout();
    }

    protected AnimatorSet onCustomTransitionAnimation(boolean z, final Runnable runnable) {
        if (!this.playProfileAnimation || !this.allowProfileAnimation) {
            return null;
        }
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(180);
        this.listView.setLayerType(2, null);
        ActionBarMenu createMenu = this.actionBar.createMenu();
        if (createMenu.getItem(10) == null && this.animatingItem == null) {
            this.animatingItem = createMenu.addItem(10, (int) R.drawable.ic_ab_other);
        }
        int ceil;
        Collection arrayList;
        Object obj;
        String str;
        float[] fArr;
        if (z) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.onlineTextView[1].getLayoutParams();
            layoutParams.rightMargin = (int) ((-21.0f * AndroidUtilities.density) + ((float) AndroidUtilities.dp(8.0f)));
            this.onlineTextView[1].setLayoutParams(layoutParams);
            ceil = (int) Math.ceil((double) (((float) (AndroidUtilities.displaySize.x - AndroidUtilities.dp(126.0f))) + (21.0f * AndroidUtilities.density)));
            layoutParams = (FrameLayout.LayoutParams) this.nameTextView[1].getLayoutParams();
            if (((float) ceil) < ((float) this.nameTextView[1].getSideDrawablesSize()) + (this.nameTextView[1].getPaint().measureText(this.nameTextView[1].getText().toString()) * 1.12f)) {
                layoutParams.width = (int) Math.ceil((double) (((float) ceil) / 1.12f));
            } else {
                layoutParams.width = -2;
            }
            this.nameTextView[1].setLayoutParams(layoutParams);
            this.initialAnimationExtraHeight = AndroidUtilities.dp(88.0f);
            this.fragmentView.setBackgroundColor(0);
            setAnimationProgress(BitmapDescriptorFactory.HUE_RED);
            arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this, "animationProgress", new float[]{BitmapDescriptorFactory.HUE_RED, 1.0f}));
            if (this.writeButton != null) {
                this.writeButton.setScaleX(0.2f);
                this.writeButton.setScaleY(0.2f);
                this.writeButton.setAlpha(BitmapDescriptorFactory.HUE_RED);
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{1.0f}));
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{1.0f}));
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{1.0f}));
            }
            ceil = 0;
            while (ceil < 2) {
                this.onlineTextView[ceil].setAlpha(ceil == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
                this.nameTextView[ceil].setAlpha(ceil == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED);
                obj = this.onlineTextView[ceil];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = ceil == 0 ? BitmapDescriptorFactory.HUE_RED : 1.0f;
                arrayList.add(ObjectAnimator.ofFloat(obj, str, fArr));
                obj = this.nameTextView[ceil];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = ceil == 0 ? BitmapDescriptorFactory.HUE_RED : 1.0f;
                arrayList.add(ObjectAnimator.ofFloat(obj, str, fArr));
                ceil++;
            }
            if (this.animatingItem != null) {
                this.animatingItem.setAlpha(1.0f);
                arrayList.add(ObjectAnimator.ofFloat(this.animatingItem, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.callItem != null) {
                this.callItem.setAlpha(BitmapDescriptorFactory.HUE_RED);
                arrayList.add(ObjectAnimator.ofFloat(this.callItem, "alpha", new float[]{1.0f}));
            }
            if (this.editItem != null) {
                this.editItem.setAlpha(BitmapDescriptorFactory.HUE_RED);
                arrayList.add(ObjectAnimator.ofFloat(this.editItem, "alpha", new float[]{1.0f}));
            }
            animatorSet.playTogether(arrayList);
        } else {
            this.initialAnimationExtraHeight = this.extraHeight;
            arrayList = new ArrayList();
            arrayList.add(ObjectAnimator.ofFloat(this, "animationProgress", new float[]{1.0f, BitmapDescriptorFactory.HUE_RED}));
            if (this.writeButton != null) {
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "scaleX", new float[]{0.2f}));
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "scaleY", new float[]{0.2f}));
                arrayList.add(ObjectAnimator.ofFloat(this.writeButton, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            }
            ceil = 0;
            while (ceil < 2) {
                obj = this.onlineTextView[ceil];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = ceil == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                arrayList.add(ObjectAnimator.ofFloat(obj, str, fArr));
                obj = this.nameTextView[ceil];
                str = "alpha";
                fArr = new float[1];
                fArr[0] = ceil == 0 ? 1.0f : BitmapDescriptorFactory.HUE_RED;
                arrayList.add(ObjectAnimator.ofFloat(obj, str, fArr));
                ceil++;
            }
            if (this.animatingItem != null) {
                this.animatingItem.setAlpha(BitmapDescriptorFactory.HUE_RED);
                arrayList.add(ObjectAnimator.ofFloat(this.animatingItem, "alpha", new float[]{1.0f}));
            }
            if (this.callItem != null) {
                this.callItem.setAlpha(1.0f);
                arrayList.add(ObjectAnimator.ofFloat(this.callItem, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            }
            if (this.editItem != null) {
                this.editItem.setAlpha(1.0f);
                arrayList.add(ObjectAnimator.ofFloat(this.editItem, "alpha", new float[]{BitmapDescriptorFactory.HUE_RED}));
            }
            animatorSet.playTogether(arrayList);
        }
        animatorSet.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animator) {
                ProfileActivity.this.listView.setLayerType(0, null);
                if (ProfileActivity.this.animatingItem != null) {
                    ProfileActivity.this.actionBar.createMenu().clearItems();
                    ProfileActivity.this.animatingItem = null;
                }
                runnable.run();
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

    protected void onDialogDismiss(Dialog dialog) {
        if (this.listView != null) {
            this.listView.invalidateViews();
        }
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
                } catch (Throwable e) {
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
            this.avatarUpdater.delegate = new C51255();
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

    public void onRequestPermissionsResultFragment(int i, String[] strArr, int[] iArr) {
        if (i == 101) {
            User user = MessagesController.getInstance().getUser(Integer.valueOf(this.user_id));
            if (user != null) {
                if (iArr.length <= 0 || iArr[0] != 0) {
                    VoIPHelper.permissionDenied(getParentActivity(), null);
                } else {
                    VoIPHelper.startCall(user, getParentActivity(), MessagesController.getInstance().getUserFull(user.id));
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
                showCantOpenAlert(this, C3791b.T(ApplicationLoader.applicationContext), true);
            } catch (Exception e) {
            }
        }
    }

    protected void onTransitionAnimationEnd(boolean z, boolean z2) {
        if (!z2 && this.playProfileAnimation && this.allowProfileAnimation) {
            this.openAnimationInProgress = false;
            if (this.recreateMenuAfterAnimation) {
                createActionBarMenu();
            }
        }
        NotificationCenter.getInstance().setAnimationInProgress(false);
    }

    protected void onTransitionAnimationStart(boolean z, boolean z2) {
        if (!z2 && this.playProfileAnimation && this.allowProfileAnimation) {
            this.openAnimationInProgress = true;
        }
        NotificationCenter.getInstance().setAllowedNotificationsDutingAnimation(new int[]{NotificationCenter.dialogsNeedReload, NotificationCenter.closeChats, NotificationCenter.mediaCountDidLoaded});
        NotificationCenter.getInstance().setAnimationInProgress(true);
    }

    public void restoreSelfArgs(Bundle bundle) {
        if (this.chat_id != 0) {
            MessagesController.getInstance().loadChatInfo(this.chat_id, null, false);
            if (this.avatarUpdater != null) {
                this.avatarUpdater.currentPicturePath = bundle.getString("path");
            }
        }
    }

    public void saveSelfArgs(Bundle bundle) {
        if (this.chat_id != 0 && this.avatarUpdater != null && this.avatarUpdater.currentPicturePath != null) {
            bundle.putString("path", this.avatarUpdater.currentPicturePath);
        }
    }

    @Keep
    public void setAnimationProgress(float f) {
        this.animationProgress = f;
        this.listView.setAlpha(f);
        this.listView.setTranslationX(((float) AndroidUtilities.dp(48.0f)) - (((float) AndroidUtilities.dp(48.0f)) * f));
        int i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        i = AvatarDrawable.getProfileBackColorForId(i);
        int color = Theme.getColor(Theme.key_actionBarDefault);
        int red = Color.red(color);
        int green = Color.green(color);
        color = Color.blue(color);
        this.topView.setBackgroundColor(Color.rgb(red + ((int) (((float) (Color.red(i) - red)) * f)), green + ((int) (((float) (Color.green(i) - green)) * f)), ((int) (((float) (Color.blue(i) - color)) * f)) + color));
        i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        i = AvatarDrawable.getIconColorForId(i);
        color = Theme.getColor(Theme.key_actionBarDefaultIcon);
        red = Color.red(color);
        green = Color.green(color);
        color = Color.blue(color);
        this.actionBar.setItemsColor(Color.rgb(red + ((int) (((float) (Color.red(i) - red)) * f)), green + ((int) (((float) (Color.green(i) - green)) * f)), ((int) (((float) (Color.blue(i) - color)) * f)) + color), false);
        i = Theme.getColor(Theme.key_profile_title);
        color = Theme.getColor(Theme.key_actionBarDefaultTitle);
        red = Color.red(color);
        green = Color.green(color);
        int blue = Color.blue(color);
        color = Color.alpha(color);
        int red2 = (int) (((float) (Color.red(i) - red)) * f);
        int green2 = (int) (((float) (Color.green(i) - green)) * f);
        int blue2 = (int) (((float) (Color.blue(i) - blue)) * f);
        int alpha = (int) (((float) (Color.alpha(i) - color)) * f);
        for (i = 0; i < 2; i++) {
            if (this.nameTextView[i] != null) {
                this.nameTextView[i].setTextColor(Color.argb(color + alpha, red + red2, green + green2, blue + blue2));
            }
        }
        i = (this.user_id != 0 || (ChatObject.isChannel(this.chat_id) && !this.currentChat.megagroup)) ? 5 : this.chat_id;
        i = AvatarDrawable.getProfileTextColorForId(i);
        color = Theme.getColor(Theme.key_actionBarDefaultSubtitle);
        red = Color.red(color);
        green = Color.green(color);
        blue = Color.blue(color);
        color = Color.alpha(color);
        red2 = (int) (((float) (Color.red(i) - red)) * f);
        green2 = (int) (((float) (Color.green(i) - green)) * f);
        blue2 = (int) (((float) (Color.blue(i) - blue)) * f);
        alpha = (int) (((float) (Color.alpha(i) - color)) * f);
        for (i = 0; i < 2; i++) {
            if (this.onlineTextView[i] != null) {
                this.onlineTextView[i].setTextColor(Color.argb(color + alpha, red + red2, green + green2, blue + blue2));
            }
        }
        this.extraHeight = (int) (((float) this.initialAnimationExtraHeight) * f);
        color = AvatarDrawable.getProfileColorForId(this.user_id != 0 ? this.user_id : this.chat_id);
        i = AvatarDrawable.getColorForId(this.user_id != 0 ? this.user_id : this.chat_id);
        if (color != i) {
            this.avatarDrawable.setColor(Color.rgb(((int) (((float) (Color.red(color) - Color.red(i))) * f)) + Color.red(i), ((int) (((float) (Color.green(color) - Color.green(i))) * f)) + Color.green(i), Color.blue(i) + ((int) (((float) (Color.blue(color) - Color.blue(i))) * f))));
            this.avatarImage.invalidate();
        }
        needLayout();
    }

    public void setChatInfo(ChatFull chatFull) {
        this.info = chatFull;
        if (!(this.info == null || this.info.migrated_from_chat_id == 0)) {
            this.mergeDialogId = (long) (-this.info.migrated_from_chat_id);
        }
        fetchUsersFromChannelInfo();
    }

    public void setPlayProfileAnimation(boolean z) {
        SharedPreferences sharedPreferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        if (!AndroidUtilities.isTablet() && sharedPreferences.getBoolean("view_animations", true)) {
            this.playProfileAnimation = z;
        }
    }
}
