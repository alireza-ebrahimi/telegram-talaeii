package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import org.ir.talaeii.R;
import org.telegram.customization.Application.AppApplication;
import org.telegram.customization.Internet.HandleRequest;
import org.telegram.customization.Internet.IResponseReceiver;
import org.telegram.customization.Model.Ads.Category;
import org.telegram.customization.Model.ContactChangeLog;
import org.telegram.customization.Model.Favourite;
import org.telegram.customization.util.Prefs;
import org.telegram.messenger.NotificationCenter.NotificationCenterDelegate;
import org.telegram.messenger.exoplayer2.DefaultLoadControl;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.query.DraftQuery;
import org.telegram.messenger.query.MessagesQuery;
import org.telegram.messenger.query.SearchQuery;
import org.telegram.messenger.query.StickersQuery;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.NativeByteBuffer;
import org.telegram.tgnet.SerializedData;
import org.telegram.tgnet.TLObject;
import org.telegram.tgnet.TLRPC$Chat;
import org.telegram.tgnet.TLRPC$ChatFull;
import org.telegram.tgnet.TLRPC$ChatParticipant;
import org.telegram.tgnet.TLRPC$ChatParticipants;
import org.telegram.tgnet.TLRPC$Document;
import org.telegram.tgnet.TLRPC$EncryptedChat;
import org.telegram.tgnet.TLRPC$ExportedChatInvite;
import org.telegram.tgnet.TLRPC$InputChannel;
import org.telegram.tgnet.TLRPC$InputFile;
import org.telegram.tgnet.TLRPC$InputPeer;
import org.telegram.tgnet.TLRPC$InputPhoto;
import org.telegram.tgnet.TLRPC$InputUser;
import org.telegram.tgnet.TLRPC$Message;
import org.telegram.tgnet.TLRPC$MessageEntity;
import org.telegram.tgnet.TLRPC$Peer;
import org.telegram.tgnet.TLRPC$PeerNotifySettings;
import org.telegram.tgnet.TLRPC$PhotoSize;
import org.telegram.tgnet.TLRPC$RecentMeUrl;
import org.telegram.tgnet.TLRPC$TL_account_registerDevice;
import org.telegram.tgnet.TLRPC$TL_account_unregisterDevice;
import org.telegram.tgnet.TLRPC$TL_account_updateStatus;
import org.telegram.tgnet.TLRPC$TL_auth_logOut;
import org.telegram.tgnet.TLRPC$TL_channel;
import org.telegram.tgnet.TLRPC$TL_channelAdminRights;
import org.telegram.tgnet.TLRPC$TL_channelBannedRights;
import org.telegram.tgnet.TLRPC$TL_channelForbidden;
import org.telegram.tgnet.TLRPC$TL_channelMessagesFilterEmpty;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsAdmins;
import org.telegram.tgnet.TLRPC$TL_channelParticipantsRecent;
import org.telegram.tgnet.TLRPC$TL_channels_createChannel;
import org.telegram.tgnet.TLRPC$TL_channels_deleteChannel;
import org.telegram.tgnet.TLRPC$TL_channels_deleteHistory;
import org.telegram.tgnet.TLRPC$TL_channels_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_channels_deleteUserHistory;
import org.telegram.tgnet.TLRPC$TL_channels_editAbout;
import org.telegram.tgnet.TLRPC$TL_channels_editAdmin;
import org.telegram.tgnet.TLRPC$TL_channels_editBanned;
import org.telegram.tgnet.TLRPC$TL_channels_editPhoto;
import org.telegram.tgnet.TLRPC$TL_channels_editTitle;
import org.telegram.tgnet.TLRPC$TL_channels_getFullChannel;
import org.telegram.tgnet.TLRPC$TL_channels_getMessages;
import org.telegram.tgnet.TLRPC$TL_channels_getParticipants;
import org.telegram.tgnet.TLRPC$TL_channels_inviteToChannel;
import org.telegram.tgnet.TLRPC$TL_channels_joinChannel;
import org.telegram.tgnet.TLRPC$TL_channels_leaveChannel;
import org.telegram.tgnet.TLRPC$TL_channels_readHistory;
import org.telegram.tgnet.TLRPC$TL_channels_readMessageContents;
import org.telegram.tgnet.TLRPC$TL_channels_toggleInvites;
import org.telegram.tgnet.TLRPC$TL_channels_togglePreHistoryHidden;
import org.telegram.tgnet.TLRPC$TL_channels_toggleSignatures;
import org.telegram.tgnet.TLRPC$TL_channels_updatePinnedMessage;
import org.telegram.tgnet.TLRPC$TL_channels_updateUsername;
import org.telegram.tgnet.TLRPC$TL_chat;
import org.telegram.tgnet.TLRPC$TL_chatFull;
import org.telegram.tgnet.TLRPC$TL_chatInviteEmpty;
import org.telegram.tgnet.TLRPC$TL_chatParticipant;
import org.telegram.tgnet.TLRPC$TL_chatParticipants;
import org.telegram.tgnet.TLRPC$TL_chatPhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_config;
import org.telegram.tgnet.TLRPC$TL_contactLinkContact;
import org.telegram.tgnet.TLRPC$TL_contacts_block;
import org.telegram.tgnet.TLRPC$TL_contacts_getBlocked;
import org.telegram.tgnet.TLRPC$TL_contacts_resolveUsername;
import org.telegram.tgnet.TLRPC$TL_contacts_unblock;
import org.telegram.tgnet.TLRPC$TL_dialog;
import org.telegram.tgnet.TLRPC$TL_disabledFeature;
import org.telegram.tgnet.TLRPC$TL_documentEmpty;
import org.telegram.tgnet.TLRPC$TL_encryptedChat;
import org.telegram.tgnet.TLRPC$TL_encryptedChatRequested;
import org.telegram.tgnet.TLRPC$TL_encryptedChatWaiting;
import org.telegram.tgnet.TLRPC$TL_help_getAppChangelog;
import org.telegram.tgnet.TLRPC$TL_help_getRecentMeUrls;
import org.telegram.tgnet.TLRPC$TL_inputChannel;
import org.telegram.tgnet.TLRPC$TL_inputChannelEmpty;
import org.telegram.tgnet.TLRPC$TL_inputChatPhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_inputChatUploadedPhoto;
import org.telegram.tgnet.TLRPC$TL_inputDocument;
import org.telegram.tgnet.TLRPC$TL_inputEncryptedChat;
import org.telegram.tgnet.TLRPC$TL_inputMessagesFilterChatPhotos;
import org.telegram.tgnet.TLRPC$TL_inputPeerChannel;
import org.telegram.tgnet.TLRPC$TL_inputPeerChat;
import org.telegram.tgnet.TLRPC$TL_inputPeerEmpty;
import org.telegram.tgnet.TLRPC$TL_inputPeerUser;
import org.telegram.tgnet.TLRPC$TL_inputPhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_inputUser;
import org.telegram.tgnet.TLRPC$TL_inputUserEmpty;
import org.telegram.tgnet.TLRPC$TL_inputUserSelf;
import org.telegram.tgnet.TLRPC$TL_message;
import org.telegram.tgnet.TLRPC$TL_messageActionChannelCreate;
import org.telegram.tgnet.TLRPC$TL_messageActionChatAddUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatDeleteUser;
import org.telegram.tgnet.TLRPC$TL_messageActionChatMigrateTo;
import org.telegram.tgnet.TLRPC$TL_messageActionCreatedBroadcastList;
import org.telegram.tgnet.TLRPC$TL_messageActionHistoryClear;
import org.telegram.tgnet.TLRPC$TL_messageActionUserJoined;
import org.telegram.tgnet.TLRPC$TL_messageEntityMentionName;
import org.telegram.tgnet.TLRPC$TL_messageMediaEmpty;
import org.telegram.tgnet.TLRPC$TL_messageService;
import org.telegram.tgnet.TLRPC$TL_messages_addChatUser;
import org.telegram.tgnet.TLRPC$TL_messages_createChat;
import org.telegram.tgnet.TLRPC$TL_messages_deleteChatUser;
import org.telegram.tgnet.TLRPC$TL_messages_deleteHistory;
import org.telegram.tgnet.TLRPC$TL_messages_deleteMessages;
import org.telegram.tgnet.TLRPC$TL_messages_editChatAdmin;
import org.telegram.tgnet.TLRPC$TL_messages_editChatPhoto;
import org.telegram.tgnet.TLRPC$TL_messages_editChatTitle;
import org.telegram.tgnet.TLRPC$TL_messages_getDialogs;
import org.telegram.tgnet.TLRPC$TL_messages_getFullChat;
import org.telegram.tgnet.TLRPC$TL_messages_getHistory;
import org.telegram.tgnet.TLRPC$TL_messages_getMessages;
import org.telegram.tgnet.TLRPC$TL_messages_getMessagesViews;
import org.telegram.tgnet.TLRPC$TL_messages_getPeerDialogs;
import org.telegram.tgnet.TLRPC$TL_messages_getPeerSettings;
import org.telegram.tgnet.TLRPC$TL_messages_getPinnedDialogs;
import org.telegram.tgnet.TLRPC$TL_messages_getWebPagePreview;
import org.telegram.tgnet.TLRPC$TL_messages_hideReportSpam;
import org.telegram.tgnet.TLRPC$TL_messages_messages;
import org.telegram.tgnet.TLRPC$TL_messages_migrateChat;
import org.telegram.tgnet.TLRPC$TL_messages_peerDialogs;
import org.telegram.tgnet.TLRPC$TL_messages_readEncryptedHistory;
import org.telegram.tgnet.TLRPC$TL_messages_readHistory;
import org.telegram.tgnet.TLRPC$TL_messages_readMessageContents;
import org.telegram.tgnet.TLRPC$TL_messages_receivedQueue;
import org.telegram.tgnet.TLRPC$TL_messages_reportEncryptedSpam;
import org.telegram.tgnet.TLRPC$TL_messages_reportSpam;
import org.telegram.tgnet.TLRPC$TL_messages_saveGif;
import org.telegram.tgnet.TLRPC$TL_messages_saveRecentSticker;
import org.telegram.tgnet.TLRPC$TL_messages_search;
import org.telegram.tgnet.TLRPC$TL_messages_setEncryptedTyping;
import org.telegram.tgnet.TLRPC$TL_messages_setTyping;
import org.telegram.tgnet.TLRPC$TL_messages_startBot;
import org.telegram.tgnet.TLRPC$TL_messages_toggleChatAdmins;
import org.telegram.tgnet.TLRPC$TL_messages_toggleDialogPin;
import org.telegram.tgnet.TLRPC$TL_peerChannel;
import org.telegram.tgnet.TLRPC$TL_peerChat;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettings;
import org.telegram.tgnet.TLRPC$TL_peerNotifySettingsEmpty;
import org.telegram.tgnet.TLRPC$TL_peerUser;
import org.telegram.tgnet.TLRPC$TL_photoEmpty;
import org.telegram.tgnet.TLRPC$TL_photos_deletePhotos;
import org.telegram.tgnet.TLRPC$TL_photos_getUserPhotos;
import org.telegram.tgnet.TLRPC$TL_photos_updateProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_photos_uploadProfilePhoto;
import org.telegram.tgnet.TLRPC$TL_replyKeyboardHide;
import org.telegram.tgnet.TLRPC$TL_sendMessageCancelAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageGamePlayAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageRecordAudioAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageRecordRoundAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageRecordVideoAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageTypingAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageUploadAudioAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageUploadDocumentAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageUploadPhotoAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageUploadRoundAction;
import org.telegram.tgnet.TLRPC$TL_sendMessageUploadVideoAction;
import org.telegram.tgnet.TLRPC$TL_updateChannel;
import org.telegram.tgnet.TLRPC$TL_updateChannelAvailableMessages;
import org.telegram.tgnet.TLRPC$TL_updateChannelMessageViews;
import org.telegram.tgnet.TLRPC$TL_updateChannelPinnedMessage;
import org.telegram.tgnet.TLRPC$TL_updateChannelReadMessagesContents;
import org.telegram.tgnet.TLRPC$TL_updateChannelTooLong;
import org.telegram.tgnet.TLRPC$TL_updateChannelWebPage;
import org.telegram.tgnet.TLRPC$TL_updateChatAdmins;
import org.telegram.tgnet.TLRPC$TL_updateChatParticipantAdd;
import org.telegram.tgnet.TLRPC$TL_updateChatParticipantAdmin;
import org.telegram.tgnet.TLRPC$TL_updateChatParticipantDelete;
import org.telegram.tgnet.TLRPC$TL_updateChatParticipants;
import org.telegram.tgnet.TLRPC$TL_updateChatUserTyping;
import org.telegram.tgnet.TLRPC$TL_updateConfig;
import org.telegram.tgnet.TLRPC$TL_updateContactLink;
import org.telegram.tgnet.TLRPC$TL_updateContactRegistered;
import org.telegram.tgnet.TLRPC$TL_updateContactsReset;
import org.telegram.tgnet.TLRPC$TL_updateDcOptions;
import org.telegram.tgnet.TLRPC$TL_updateDeleteChannelMessages;
import org.telegram.tgnet.TLRPC$TL_updateDeleteMessages;
import org.telegram.tgnet.TLRPC$TL_updateDialogPinned;
import org.telegram.tgnet.TLRPC$TL_updateDraftMessage;
import org.telegram.tgnet.TLRPC$TL_updateEditChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateEditMessage;
import org.telegram.tgnet.TLRPC$TL_updateEncryptedChatTyping;
import org.telegram.tgnet.TLRPC$TL_updateEncryptedMessagesRead;
import org.telegram.tgnet.TLRPC$TL_updateEncryption;
import org.telegram.tgnet.TLRPC$TL_updateFavedStickers;
import org.telegram.tgnet.TLRPC$TL_updateLangPack;
import org.telegram.tgnet.TLRPC$TL_updateLangPackTooLong;
import org.telegram.tgnet.TLRPC$TL_updateNewChannelMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewEncryptedMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewGeoChatMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewMessage;
import org.telegram.tgnet.TLRPC$TL_updateNewStickerSet;
import org.telegram.tgnet.TLRPC$TL_updateNotifySettings;
import org.telegram.tgnet.TLRPC$TL_updatePhoneCall;
import org.telegram.tgnet.TLRPC$TL_updatePinnedDialogs;
import org.telegram.tgnet.TLRPC$TL_updatePrivacy;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelInbox;
import org.telegram.tgnet.TLRPC$TL_updateReadChannelOutbox;
import org.telegram.tgnet.TLRPC$TL_updateReadFeaturedStickers;
import org.telegram.tgnet.TLRPC$TL_updateReadHistoryInbox;
import org.telegram.tgnet.TLRPC$TL_updateReadHistoryOutbox;
import org.telegram.tgnet.TLRPC$TL_updateReadMessagesContents;
import org.telegram.tgnet.TLRPC$TL_updateSavedGifs;
import org.telegram.tgnet.TLRPC$TL_updateServiceNotification;
import org.telegram.tgnet.TLRPC$TL_updateShort;
import org.telegram.tgnet.TLRPC$TL_updateShortChatMessage;
import org.telegram.tgnet.TLRPC$TL_updateShortMessage;
import org.telegram.tgnet.TLRPC$TL_updateStickerSets;
import org.telegram.tgnet.TLRPC$TL_updateStickerSetsOrder;
import org.telegram.tgnet.TLRPC$TL_updateUserBlocked;
import org.telegram.tgnet.TLRPC$TL_updateUserName;
import org.telegram.tgnet.TLRPC$TL_updateUserPhone;
import org.telegram.tgnet.TLRPC$TL_updateUserPhoto;
import org.telegram.tgnet.TLRPC$TL_updateUserStatus;
import org.telegram.tgnet.TLRPC$TL_updateUserTyping;
import org.telegram.tgnet.TLRPC$TL_updateWebPage;
import org.telegram.tgnet.TLRPC$TL_updates;
import org.telegram.tgnet.TLRPC$TL_updatesCombined;
import org.telegram.tgnet.TLRPC$TL_updatesTooLong;
import org.telegram.tgnet.TLRPC$TL_updates_getChannelDifference;
import org.telegram.tgnet.TLRPC$TL_updates_getDifference;
import org.telegram.tgnet.TLRPC$TL_updates_getState;
import org.telegram.tgnet.TLRPC$TL_userForeign_old2;
import org.telegram.tgnet.TLRPC$TL_userFull;
import org.telegram.tgnet.TLRPC$TL_userProfilePhotoEmpty;
import org.telegram.tgnet.TLRPC$TL_users_getFullUser;
import org.telegram.tgnet.TLRPC$Update;
import org.telegram.tgnet.TLRPC$Updates;
import org.telegram.tgnet.TLRPC$WebPage;
import org.telegram.tgnet.TLRPC$messages_Dialogs;
import org.telegram.tgnet.TLRPC$messages_Messages;
import org.telegram.tgnet.TLRPC$photos_Photos;
import org.telegram.tgnet.TLRPC.User;
import org.telegram.ui.ActionBar.AlertDialog;
import org.telegram.ui.ActionBar.AlertDialog.Builder;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.ChatActivity;
import org.telegram.ui.ProfileActivity;
import utils.app.AppPreferences;

public class MessagesController implements NotificationCenterDelegate {
    private static volatile MessagesController Instance = null;
    public static final int UPDATE_MASK_ALL = 1535;
    public static final int UPDATE_MASK_AVATAR = 2;
    public static final int UPDATE_MASK_CHANNEL = 8192;
    public static final int UPDATE_MASK_CHAT_ADMINS = 16384;
    public static final int UPDATE_MASK_CHAT_AVATAR = 8;
    public static final int UPDATE_MASK_CHAT_MEMBERS = 32;
    public static final int UPDATE_MASK_CHAT_NAME = 16;
    public static final int UPDATE_MASK_NAME = 1;
    public static final int UPDATE_MASK_NEW_MESSAGE = 2048;
    public static final int UPDATE_MASK_PHONE = 1024;
    public static final int UPDATE_MASK_READ_DIALOG_MESSAGE = 256;
    public static final int UPDATE_MASK_SELECT_DIALOG = 512;
    public static final int UPDATE_MASK_SEND_STATE = 4096;
    public static final int UPDATE_MASK_STATUS = 4;
    public static final int UPDATE_MASK_USER_PHONE = 128;
    public static final int UPDATE_MASK_USER_PRINT = 64;
    public static ArrayList<TLRPC$TL_dialog> staticBotArr = new ArrayList();
    public boolean allowBigEmoji;
    public ArrayList<Integer> blockedUsers = new ArrayList();
    public int callConnectTimeout = DefaultLoadControl.DEFAULT_MAX_BUFFER_MS;
    public int callPacketTimeout = 10000;
    public int callReceiveTimeout = BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT;
    public int callRingTimeout = 90000;
    ArrayList<Integer> categoriesIds = new ArrayList();
    private HashMap<Integer, ArrayList<Integer>> channelAdmins = new HashMap();
    private SparseArray<ArrayList<Integer>> channelViewsToSend = new SparseArray();
    private HashMap<Integer, Integer> channelsPts = new HashMap();
    private ConcurrentHashMap<Integer, TLRPC$Chat> chats = new ConcurrentHashMap(100, 1.0f, 2);
    private HashMap<Integer, Boolean> checkingLastMessagesDialogs = new HashMap();
    private ArrayList<Long> createdDialogIds = new ArrayList();
    private ArrayList<Long> createdDialogMainThreadIds = new ArrayList();
    private Runnable currentDeleteTaskRunnable;
    private int currentDeletingTaskChannelId;
    private ArrayList<Integer> currentDeletingTaskMids;
    private int currentDeletingTaskTime;
    public boolean defaultP2pContacts = false;
    private final Comparator<TLRPC$TL_dialog> dialogComparator = new MessagesController$1(this);
    public HashMap<Long, MessageObject> dialogMessage = new HashMap();
    public HashMap<Integer, MessageObject> dialogMessagesByIds = new HashMap();
    public HashMap<Long, MessageObject> dialogMessagesByRandomIds = new HashMap();
    public ArrayList<TLRPC$TL_dialog> dialogs = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsAds = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsAll = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsBots = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsChannels = new ArrayList();
    public boolean dialogsEndReached;
    public ArrayList<TLRPC$TL_dialog> dialogsFavs = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsForward = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsGroups = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsGroupsAll = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsGroupsOnly = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsHidden = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsMegaGroups = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsServerOnly = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsUnread = new ArrayList();
    public ArrayList<TLRPC$TL_dialog> dialogsUsers = new ArrayList();
    public ConcurrentHashMap<Long, TLRPC$TL_dialog> dialogs_dict = new ConcurrentHashMap(100, 1.0f, 2);
    public ConcurrentHashMap<Long, Integer> dialogs_read_inbox_max = new ConcurrentHashMap(100, 1.0f, 2);
    public ConcurrentHashMap<Long, Integer> dialogs_read_outbox_max = new ConcurrentHashMap(100, 1.0f, 2);
    private ArrayList<TLRPC$TL_disabledFeature> disabledFeatures = new ArrayList();
    public boolean enableJoined = true;
    private ConcurrentHashMap<Integer, TLRPC$EncryptedChat> encryptedChats = new ConcurrentHashMap(10, 1.0f, 2);
    private HashMap<Integer, TLRPC$ExportedChatInvite> exportedChats = new HashMap();
    public boolean firstGettingTask;
    public int fontSize = AndroidUtilities.dp(16.0f);
    private HashMap<Integer, TLRPC$TL_userFull> fullUsers = new HashMap();
    private boolean getDifferenceFirstSync = true;
    public boolean gettingDifference;
    private HashMap<Integer, Boolean> gettingDifferenceChannels = new HashMap();
    private boolean gettingNewDeleteTask;
    private HashMap<Integer, Boolean> gettingUnknownChannels = new HashMap();
    public int groupBigSize;
    private List<Long> hiddensIds = null;
    public boolean hideJoinedGroup;
    public boolean hideLeftGroup;
    public ArrayList<TLRPC$RecentMeUrl> hintDialogs = new ArrayList();
    private String installReferer;
    private ArrayList<Integer> joiningToChannels = new ArrayList();
    private int lastPrintingStringCount;
    private long lastStatusUpdateTime;
    private long lastViewsCheckTime;
    public String linkPrefix = "t.me";
    private ArrayList<Integer> loadedFullChats = new ArrayList();
    private ArrayList<Integer> loadedFullParticipants = new ArrayList();
    private ArrayList<Integer> loadedFullUsers = new ArrayList();
    public boolean loadingBlockedUsers = false;
    private SparseIntArray loadingChannelAdmins = new SparseIntArray();
    public boolean loadingDialogs;
    private ArrayList<Integer> loadingFullChats = new ArrayList();
    private ArrayList<Integer> loadingFullParticipants = new ArrayList();
    private ArrayList<Integer> loadingFullUsers = new ArrayList();
    private HashMap<Long, Boolean> loadingPeerSettings = new HashMap();
    public int maxBroadcastCount = 100;
    public int maxEditTime = 172800;
    public int maxFaveStickersCount = 5;
    public int maxGroupCount = 200;
    public int maxMegagroupCount = 10000;
    public int maxPinnedDialogsCount = 5;
    public int maxRecentGifsCount = 200;
    public int maxRecentStickersCount = 30;
    private boolean migratingDialogs;
    public int minGroupConvertSize = 200;
    private SparseIntArray needShortPollChannels = new SparseIntArray();
    public int nextDialogsCacheOffset;
    private ConcurrentHashMap<String, TLObject> objectsByUsernames = new ConcurrentHashMap(100, 1.0f, 2);
    private boolean offlineSent;
    public ConcurrentHashMap<Integer, Integer> onlinePrivacy = new ConcurrentHashMap(20, 1.0f, 2);
    public HashMap<Long, CharSequence> printingStrings = new HashMap();
    public HashMap<Long, Integer> printingStringsTypes = new HashMap();
    public ConcurrentHashMap<Long, ArrayList<MessagesController$PrintingUser>> printingUsers = new ConcurrentHashMap(20, 1.0f, 2);
    public int ratingDecay;
    public boolean registeringForPush;
    private HashMap<Long, ArrayList<Integer>> reloadingMessages = new HashMap();
    private HashMap<String, ArrayList<MessageObject>> reloadingWebpages = new HashMap();
    private HashMap<Long, ArrayList<MessageObject>> reloadingWebpagesPending = new HashMap();
    private TLRPC$messages_Dialogs resetDialogsAll;
    private TLRPC$TL_messages_peerDialogs resetDialogsPinned;
    private boolean resetingDialogs;
    public int secretWebpagePreview = 2;
    public HashMap<Integer, HashMap<Long, Boolean>> sendingTypings = new HashMap();
    public boolean serverDialogsEndReached;
    private SparseIntArray shortPollChannels = new SparseIntArray();
    private int statusRequest;
    private int statusSettingState;
    private final Comparator<TLRPC$Update> updatesComparator = new MessagesController$2(this);
    private HashMap<Integer, ArrayList<TLRPC$Updates>> updatesQueueChannels = new HashMap();
    private ArrayList<TLRPC$Updates> updatesQueuePts = new ArrayList();
    private ArrayList<TLRPC$Updates> updatesQueueQts = new ArrayList();
    private ArrayList<TLRPC$Updates> updatesQueueSeq = new ArrayList();
    private HashMap<Integer, Long> updatesStartWaitTimeChannels = new HashMap();
    private long updatesStartWaitTimePts;
    private long updatesStartWaitTimeQts;
    private long updatesStartWaitTimeSeq;
    public boolean updatingState;
    private String uploadingAvatar;
    public boolean useSystemEmoji;
    private ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap(100, 1.0f, 2);

    public static MessagesController getInstance() {
        MessagesController localInstance = Instance;
        if (localInstance == null) {
            synchronized (MessagesController.class) {
                try {
                    localInstance = Instance;
                    if (localInstance == null) {
                        MessagesController localInstance2 = new MessagesController();
                        try {
                            Instance = localInstance2;
                            localInstance = localInstance2;
                        } catch (Throwable th) {
                            Throwable th2 = th;
                            localInstance = localInstance2;
                            throw th2;
                        }
                    }
                } catch (Throwable th3) {
                    th2 = th3;
                    throw th2;
                }
            }
        }
        return localInstance;
    }

    public MessagesController() {
        ImageLoader.getInstance();
        MessagesStorage.getInstance();
        LocationController.getInstance();
        AndroidUtilities.runOnUIThread(new MessagesController$3(this));
        addSupportUser();
        this.enableJoined = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).getBoolean("EnableContactJoined", true);
        SharedPreferences plusPrefs = ApplicationLoader.applicationContext.getSharedPreferences("plusconfig", 0);
        this.hideLeftGroup = plusPrefs.getBoolean("hideLeftGroup", false);
        this.hideJoinedGroup = plusPrefs.getBoolean("hideJoinedGroup", false);
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0);
        this.secretWebpagePreview = preferences.getInt("secretWebpage2", 2);
        this.maxGroupCount = preferences.getInt("maxGroupCount", 200);
        this.maxMegagroupCount = preferences.getInt("maxMegagroupCount", 10000);
        this.maxRecentGifsCount = preferences.getInt("maxRecentGifsCount", 200);
        this.maxRecentStickersCount = preferences.getInt("maxRecentStickersCount", 30);
        this.maxFaveStickersCount = preferences.getInt("maxFaveStickersCount", 5);
        this.maxEditTime = preferences.getInt("maxEditTime", 3600);
        this.groupBigSize = preferences.getInt("groupBigSize", 10);
        this.ratingDecay = preferences.getInt("ratingDecay", 2419200);
        this.fontSize = preferences.getInt("fons_size", AndroidUtilities.isTablet() ? 18 : 16);
        this.allowBigEmoji = preferences.getBoolean("allowBigEmoji", false);
        this.useSystemEmoji = preferences.getBoolean("useSystemEmoji", false);
        this.linkPrefix = preferences.getString("linkPrefix", "t.me");
        this.callReceiveTimeout = preferences.getInt("callReceiveTimeout", BaseImageDownloader.DEFAULT_HTTP_READ_TIMEOUT);
        this.callRingTimeout = preferences.getInt("callRingTimeout", 90000);
        this.callConnectTimeout = preferences.getInt("callConnectTimeout", DefaultLoadControl.DEFAULT_MAX_BUFFER_MS);
        this.callPacketTimeout = preferences.getInt("callPacketTimeout", 10000);
        this.maxPinnedDialogsCount = preferences.getInt("maxPinnedDialogsCount", 5);
        this.installReferer = preferences.getString("installReferer", null);
        this.defaultP2pContacts = preferences.getBoolean("defaultP2pContacts", false);
        String disabledFeaturesString = preferences.getString("disabledFeatures", null);
        if (disabledFeaturesString != null && disabledFeaturesString.length() != 0) {
            try {
                byte[] bytes = Base64.decode(disabledFeaturesString, 0);
                if (bytes != null) {
                    SerializedData data = new SerializedData(bytes);
                    int count = data.readInt32(false);
                    for (int a = 0; a < count; a++) {
                        TLRPC$TL_disabledFeature feature = TLRPC$TL_disabledFeature.TLdeserialize(data, data.readInt32(false), false);
                        if (!(feature == null || feature.feature == null || feature.description == null)) {
                            this.disabledFeatures.add(feature);
                        }
                    }
                }
            } catch (Throwable e) {
                FileLog.m94e(e);
            }
        }
    }

    public void updateConfig(TLRPC$TL_config config) {
        AndroidUtilities.runOnUIThread(new MessagesController$4(this, config));
    }

    public static boolean isFeatureEnabled(String feature, BaseFragment fragment) {
        if (feature == null || feature.length() == 0 || getInstance().disabledFeatures.isEmpty() || fragment == null) {
            return true;
        }
        Iterator it = getInstance().disabledFeatures.iterator();
        while (it.hasNext()) {
            TLRPC$TL_disabledFeature disabledFeature = (TLRPC$TL_disabledFeature) it.next();
            if (disabledFeature.feature.equals(feature)) {
                if (fragment.getParentActivity() != null) {
                    Builder builder = new Builder(fragment.getParentActivity());
                    builder.setTitle("Oops!");
                    builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
                    builder.setMessage(disabledFeature.description);
                    fragment.showDialog(builder.create());
                }
                return false;
            }
        }
        return true;
    }

    public void addSupportUser() {
        TLRPC$TL_userForeign_old2 user = new TLRPC$TL_userForeign_old2();
        user.phone = "333";
        user.id = 333000;
        user.first_name = "Telegram";
        user.last_name = "";
        user.status = null;
        user.photo = new TLRPC$TL_userProfilePhotoEmpty();
        putUser(user, true);
        user = new TLRPC$TL_userForeign_old2();
        user.phone = "42777";
        user.id = 777000;
        user.first_name = "Telegram";
        user.last_name = "Notifications";
        user.status = null;
        user.photo = new TLRPC$TL_userProfilePhotoEmpty();
        putUser(user, true);
    }

    public static TLRPC$InputUser getInputUser(User user) {
        if (user == null) {
            return new TLRPC$TL_inputUserEmpty();
        }
        if (user.id == UserConfig.getClientUserId()) {
            return new TLRPC$TL_inputUserSelf();
        }
        TLRPC$InputUser inputUser = new TLRPC$TL_inputUser();
        inputUser.user_id = user.id;
        inputUser.access_hash = user.access_hash;
        return inputUser;
    }

    public static TLRPC$InputUser getInputUser(int user_id) {
        return getInputUser(getInstance().getUser(Integer.valueOf(user_id)));
    }

    public static TLRPC$InputChannel getInputChannel(TLRPC$Chat chat) {
        if (!(chat instanceof TLRPC$TL_channel) && !(chat instanceof TLRPC$TL_channelForbidden)) {
            return new TLRPC$TL_inputChannelEmpty();
        }
        TLRPC$InputChannel inputChat = new TLRPC$TL_inputChannel();
        inputChat.channel_id = chat.id;
        inputChat.access_hash = chat.access_hash;
        return inputChat;
    }

    public static TLRPC$InputChannel getInputChannel(int chatId) {
        return getInputChannel(getInstance().getChat(Integer.valueOf(chatId)));
    }

    public static TLRPC$InputPeer getInputPeer(int id) {
        TLRPC$InputPeer inputPeer;
        if (id < 0) {
            TLRPC$Chat chat = getInstance().getChat(Integer.valueOf(-id));
            if (ChatObject.isChannel(chat)) {
                inputPeer = new TLRPC$TL_inputPeerChannel();
                inputPeer.channel_id = -id;
                inputPeer.access_hash = chat.access_hash;
                return inputPeer;
            }
            inputPeer = new TLRPC$TL_inputPeerChat();
            inputPeer.chat_id = -id;
            return inputPeer;
        }
        User user = getInstance().getUser(Integer.valueOf(id));
        inputPeer = new TLRPC$TL_inputPeerUser();
        inputPeer.user_id = id;
        if (user == null) {
            return inputPeer;
        }
        inputPeer.access_hash = user.access_hash;
        return inputPeer;
    }

    public static TLRPC$Peer getPeer(int id) {
        TLRPC$Peer inputPeer;
        if (id < 0) {
            TLRPC$Chat chat = getInstance().getChat(Integer.valueOf(-id));
            if ((chat instanceof TLRPC$TL_channel) || (chat instanceof TLRPC$TL_channelForbidden)) {
                inputPeer = new TLRPC$TL_peerChannel();
                inputPeer.channel_id = -id;
                return inputPeer;
            }
            inputPeer = new TLRPC$TL_peerChat();
            inputPeer.chat_id = -id;
            return inputPeer;
        }
        User user = getInstance().getUser(Integer.valueOf(id));
        inputPeer = new TLRPC$TL_peerUser();
        inputPeer.user_id = id;
        return inputPeer;
    }

    public void didReceivedNotification(int id, Object... args) {
        String location;
        if (id == NotificationCenter.FileDidUpload) {
            location = args[0];
            TLRPC$InputFile file = args[1];
            if (this.uploadingAvatar != null && this.uploadingAvatar.equals(location)) {
                TLRPC$TL_photos_uploadProfilePhoto req = new TLRPC$TL_photos_uploadProfilePhoto();
                req.file = file;
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$5(this));
            }
        } else if (id == NotificationCenter.FileDidFailUpload) {
            location = (String) args[0];
            if (this.uploadingAvatar != null && this.uploadingAvatar.equals(location)) {
                this.uploadingAvatar = null;
            }
        } else if (id == NotificationCenter.messageReceivedByServer) {
            Integer msgId = args[0];
            Integer newMsgId = args[1];
            Long did = args[3];
            MessageObject obj = (MessageObject) this.dialogMessage.get(did);
            if (obj != null && (obj.getId() == msgId.intValue() || obj.messageOwner.local_id == msgId.intValue())) {
                obj.messageOwner.id = newMsgId.intValue();
                obj.messageOwner.send_state = 0;
            }
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(did);
            if (dialog != null && dialog.top_message == msgId.intValue()) {
                dialog.top_message = newMsgId.intValue();
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            }
            obj = (MessageObject) this.dialogMessagesByIds.remove(msgId);
            if (obj != null) {
                this.dialogMessagesByIds.put(newMsgId, obj);
            }
        } else if (id == NotificationCenter.updateMessageMedia) {
            TLRPC$Message message = args[0];
            MessageObject existMessageObject = (MessageObject) this.dialogMessagesByIds.get(Integer.valueOf(message.id));
            if (existMessageObject != null) {
                existMessageObject.messageOwner.media = message.media;
                if (message.media.ttl_seconds == 0) {
                    return;
                }
                if ((message.media.photo instanceof TLRPC$TL_photoEmpty) || (message.media.document instanceof TLRPC$TL_documentEmpty)) {
                    existMessageObject.setType();
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
                }
            }
        }
    }

    public void cleanup() {
        ContactsController.getInstance().cleanup();
        MediaController.getInstance().cleanup();
        NotificationsController.getInstance().cleanup();
        SendMessagesHelper.getInstance().cleanup();
        SecretChatHelper.getInstance().cleanup();
        LocationController.getInstance().cleanup();
        StickersQuery.cleanup();
        SearchQuery.cleanup();
        DraftQuery.cleanup();
        this.dialogsUsers.clear();
        this.dialogsGroups.clear();
        this.dialogsGroupsAll.clear();
        this.dialogsChannels.clear();
        this.dialogsMegaGroups.clear();
        this.dialogsBots.clear();
        this.dialogsFavs.clear();
        this.dialogsHidden.clear();
        this.dialogsAll.clear();
        this.dialogsUnread.clear();
        this.dialogsAds.clear();
        this.reloadingWebpages.clear();
        this.reloadingWebpagesPending.clear();
        this.dialogs_dict.clear();
        this.dialogs_read_inbox_max.clear();
        this.dialogs_read_outbox_max.clear();
        this.exportedChats.clear();
        this.fullUsers.clear();
        this.dialogs.clear();
        this.joiningToChannels.clear();
        this.channelViewsToSend.clear();
        this.dialogsServerOnly.clear();
        this.dialogsForward.clear();
        this.dialogsGroupsOnly.clear();
        this.dialogMessagesByIds.clear();
        this.dialogMessagesByRandomIds.clear();
        this.channelAdmins.clear();
        this.loadingChannelAdmins.clear();
        this.users.clear();
        this.objectsByUsernames.clear();
        this.chats.clear();
        this.dialogMessage.clear();
        this.printingUsers.clear();
        this.printingStrings.clear();
        this.printingStringsTypes.clear();
        this.onlinePrivacy.clear();
        this.loadingPeerSettings.clear();
        this.lastPrintingStringCount = 0;
        this.nextDialogsCacheOffset = 0;
        Utilities.stageQueue.postRunnable(new MessagesController$6(this));
        this.createdDialogMainThreadIds.clear();
        this.blockedUsers.clear();
        this.sendingTypings.clear();
        this.loadingFullUsers.clear();
        this.loadedFullUsers.clear();
        this.reloadingMessages.clear();
        this.loadingFullChats.clear();
        this.loadingFullParticipants.clear();
        this.loadedFullParticipants.clear();
        this.loadedFullChats.clear();
        this.currentDeletingTaskTime = 0;
        this.currentDeletingTaskMids = null;
        this.currentDeletingTaskChannelId = 0;
        this.gettingNewDeleteTask = false;
        this.loadingDialogs = false;
        this.dialogsEndReached = false;
        this.serverDialogsEndReached = false;
        this.loadingBlockedUsers = false;
        this.firstGettingTask = false;
        this.updatingState = false;
        this.resetingDialogs = false;
        this.lastStatusUpdateTime = 0;
        this.offlineSent = false;
        this.registeringForPush = false;
        this.getDifferenceFirstSync = true;
        this.uploadingAvatar = null;
        this.statusRequest = 0;
        this.statusSettingState = 0;
        Utilities.stageQueue.postRunnable(new MessagesController$7(this));
        if (this.currentDeleteTaskRunnable != null) {
            Utilities.stageQueue.cancelRunnable(this.currentDeleteTaskRunnable);
            this.currentDeleteTaskRunnable = null;
        }
        addSupportUser();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
    }

    public User getUser(Integer id) {
        return (User) this.users.get(id);
    }

    public TLObject getUserOrChat(String username) {
        if (username == null || username.length() == 0) {
            return null;
        }
        return (TLObject) this.objectsByUsernames.get(username.toLowerCase());
    }

    public ConcurrentHashMap<Integer, User> getUsers() {
        return this.users;
    }

    public TLRPC$Chat getChat(Integer id) {
        return (TLRPC$Chat) this.chats.get(id);
    }

    public TLRPC$EncryptedChat getEncryptedChat(Integer id) {
        return (TLRPC$EncryptedChat) this.encryptedChats.get(id);
    }

    public TLRPC$EncryptedChat getEncryptedChatDB(int chat_id, boolean created) {
        TLRPC$EncryptedChat chat = (TLRPC$EncryptedChat) this.encryptedChats.get(Integer.valueOf(chat_id));
        if (chat != null) {
            if (!created) {
                return chat;
            }
            if (!((chat instanceof TLRPC$TL_encryptedChatWaiting) || (chat instanceof TLRPC$TL_encryptedChatRequested))) {
                return chat;
            }
        }
        Semaphore semaphore = new Semaphore(0);
        ArrayList<TLObject> result = new ArrayList();
        MessagesStorage.getInstance().getEncryptedChat(chat_id, semaphore, result);
        try {
            semaphore.acquire();
        } catch (Throwable e) {
            FileLog.m94e(e);
        }
        if (result.size() != 2) {
            return chat;
        }
        chat = (TLRPC$EncryptedChat) result.get(0);
        User user = (User) result.get(1);
        putEncryptedChat(chat, false);
        putUser(user, true);
        return chat;
    }

    public boolean isDialogCreated(long dialog_id) {
        return this.createdDialogMainThreadIds.contains(Long.valueOf(dialog_id));
    }

    public void setLastCreatedDialogId(long dialog_id, boolean set) {
        if (set) {
            this.createdDialogMainThreadIds.add(Long.valueOf(dialog_id));
        } else {
            this.createdDialogMainThreadIds.remove(Long.valueOf(dialog_id));
        }
        Utilities.stageQueue.postRunnable(new MessagesController$8(this, set, dialog_id));
    }

    public TLRPC$ExportedChatInvite getExportedInvite(int chat_id) {
        return (TLRPC$ExportedChatInvite) this.exportedChats.get(Integer.valueOf(chat_id));
    }

    public boolean putUser(User user, boolean fromCache) {
        if (user == null) {
            return false;
        }
        if (!fromCache || user.id / 1000 == 333 || user.id == 777000) {
            fromCache = false;
        } else {
            fromCache = true;
        }
        User oldUser = (User) this.users.get(Integer.valueOf(user.id));
        if (oldUser == user) {
            return false;
        }
        if (!(oldUser == null || TextUtils.isEmpty(oldUser.username))) {
            this.objectsByUsernames.remove(oldUser.username.toLowerCase());
        }
        if (!TextUtils.isEmpty(user.username)) {
            this.objectsByUsernames.put(user.username.toLowerCase(), user);
        }
        if (user.min) {
            if (oldUser == null) {
                this.users.put(Integer.valueOf(user.id), user);
                return false;
            } else if (fromCache) {
                return false;
            } else {
                if (user.bot) {
                    if (user.username != null) {
                        oldUser.username = user.username;
                        oldUser.flags |= 8;
                    } else {
                        oldUser.flags &= -9;
                        oldUser.username = null;
                    }
                }
                if (user.photo != null) {
                    oldUser.photo = user.photo;
                    oldUser.flags |= 32;
                    return false;
                }
                oldUser.flags &= -33;
                oldUser.photo = null;
                return false;
            }
        } else if (!fromCache) {
            this.users.put(Integer.valueOf(user.id), user);
            if (user.id == UserConfig.getClientUserId()) {
                UserConfig.setCurrentUser(user);
                UserConfig.saveConfig(true);
            }
            if (oldUser == null || user.status == null || oldUser.status == null || user.status.expires == oldUser.status.expires) {
                return false;
            }
            return true;
        } else if (oldUser == null) {
            this.users.put(Integer.valueOf(user.id), user);
            return false;
        } else if (!oldUser.min) {
            return false;
        } else {
            user.min = false;
            if (oldUser.bot) {
                if (oldUser.username != null) {
                    user.username = oldUser.username;
                    user.flags |= 8;
                } else {
                    user.flags &= -9;
                    user.username = null;
                }
            }
            if (oldUser.photo != null) {
                user.photo = oldUser.photo;
                user.flags |= 32;
            } else {
                user.flags &= -33;
                user.photo = null;
            }
            this.users.put(Integer.valueOf(user.id), user);
            return false;
        }
    }

    public void putUsers(ArrayList<User> users, boolean fromCache) {
        if (users != null && !users.isEmpty()) {
            boolean updateStatus = false;
            int count = users.size();
            for (int a = 0; a < count; a++) {
                if (putUser((User) users.get(a), fromCache)) {
                    updateStatus = true;
                }
            }
            if (updateStatus) {
                AndroidUtilities.runOnUIThread(new MessagesController$9(this));
            }
        }
    }

    public void putChat(TLRPC$Chat chat, boolean fromCache) {
        if (chat != null) {
            TLRPC$Chat oldChat = (TLRPC$Chat) this.chats.get(Integer.valueOf(chat.id));
            if (oldChat != chat) {
                if (!(oldChat == null || TextUtils.isEmpty(oldChat.username))) {
                    this.objectsByUsernames.remove(oldChat.username.toLowerCase());
                }
                if (!TextUtils.isEmpty(chat.username)) {
                    this.objectsByUsernames.put(chat.username.toLowerCase(), chat);
                }
                if (chat.min) {
                    if (oldChat == null) {
                        this.chats.put(Integer.valueOf(chat.id), chat);
                    } else if (!fromCache) {
                        oldChat.title = chat.title;
                        oldChat.photo = chat.photo;
                        oldChat.broadcast = chat.broadcast;
                        oldChat.verified = chat.verified;
                        oldChat.megagroup = chat.megagroup;
                        oldChat.democracy = chat.democracy;
                        if (chat.username != null) {
                            oldChat.username = chat.username;
                            oldChat.flags |= 64;
                        } else {
                            oldChat.flags &= -65;
                            oldChat.username = null;
                        }
                        if (chat.participants_count != 0) {
                            oldChat.participants_count = chat.participants_count;
                        }
                    }
                } else if (!fromCache) {
                    if (oldChat != null) {
                        int newFlags;
                        if (chat.version != oldChat.version) {
                            this.loadedFullChats.remove(Integer.valueOf(chat.id));
                        }
                        if (oldChat.participants_count != 0 && chat.participants_count == 0) {
                            chat.participants_count = oldChat.participants_count;
                            chat.flags |= 131072;
                        }
                        int oldFlags = oldChat.banned_rights != null ? oldChat.banned_rights.flags : 0;
                        if (chat.banned_rights != null) {
                            newFlags = chat.banned_rights.flags;
                        } else {
                            newFlags = 0;
                        }
                        if (oldFlags != newFlags) {
                            AndroidUtilities.runOnUIThread(new MessagesController$10(this, chat));
                        }
                    }
                    this.chats.put(Integer.valueOf(chat.id), chat);
                } else if (oldChat == null) {
                    this.chats.put(Integer.valueOf(chat.id), chat);
                } else if (oldChat.min) {
                    chat.min = false;
                    chat.title = oldChat.title;
                    chat.photo = oldChat.photo;
                    chat.broadcast = oldChat.broadcast;
                    chat.verified = oldChat.verified;
                    chat.megagroup = oldChat.megagroup;
                    chat.democracy = oldChat.democracy;
                    if (oldChat.username != null) {
                        chat.username = oldChat.username;
                        chat.flags |= 64;
                    } else {
                        chat.flags &= -65;
                        chat.username = null;
                    }
                    if (oldChat.participants_count != 0 && chat.participants_count == 0) {
                        chat.participants_count = oldChat.participants_count;
                        chat.flags |= 131072;
                    }
                    this.chats.put(Integer.valueOf(chat.id), chat);
                }
            }
        }
    }

    public void putChats(ArrayList<TLRPC$Chat> chats, boolean fromCache) {
        if (chats != null && !chats.isEmpty()) {
            int count = chats.size();
            for (int a = 0; a < count; a++) {
                putChat((TLRPC$Chat) chats.get(a), fromCache);
            }
        }
    }

    public void setReferer(String referer) {
        if (referer != null) {
            this.installReferer = referer;
            ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().putString("installReferer", referer).commit();
        }
    }

    public void putEncryptedChat(TLRPC$EncryptedChat encryptedChat, boolean fromCache) {
        if (encryptedChat != null) {
            if (fromCache) {
                this.encryptedChats.putIfAbsent(Integer.valueOf(encryptedChat.id), encryptedChat);
            } else {
                this.encryptedChats.put(Integer.valueOf(encryptedChat.id), encryptedChat);
            }
        }
    }

    public void putEncryptedChats(ArrayList<TLRPC$EncryptedChat> encryptedChats, boolean fromCache) {
        if (encryptedChats != null && !encryptedChats.isEmpty()) {
            int count = encryptedChats.size();
            for (int a = 0; a < count; a++) {
                putEncryptedChat((TLRPC$EncryptedChat) encryptedChats.get(a), fromCache);
            }
        }
    }

    public TLRPC$TL_userFull getUserFull(int uid) {
        return (TLRPC$TL_userFull) this.fullUsers.get(Integer.valueOf(uid));
    }

    public void cancelLoadFullUser(int uid) {
        this.loadingFullUsers.remove(Integer.valueOf(uid));
    }

    public void cancelLoadFullChat(int cid) {
        this.loadingFullChats.remove(Integer.valueOf(cid));
    }

    protected void clearFullUsers() {
        this.loadedFullUsers.clear();
        this.loadedFullChats.clear();
    }

    private void reloadDialogsReadValue(ArrayList<TLRPC$TL_dialog> dialogs, long did) {
        if (did != 0 || (dialogs != null && !dialogs.isEmpty())) {
            TLRPC$TL_messages_getPeerDialogs req = new TLRPC$TL_messages_getPeerDialogs();
            TLRPC$InputPeer inputPeer;
            if (dialogs != null) {
                for (int a = 0; a < dialogs.size(); a++) {
                    inputPeer = getInputPeer((int) ((TLRPC$TL_dialog) dialogs.get(a)).id);
                    if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel) || inputPeer.access_hash != 0) {
                        req.peers.add(inputPeer);
                    }
                }
            } else {
                inputPeer = getInputPeer((int) did);
                if (!(inputPeer instanceof TLRPC$TL_inputPeerChannel) || inputPeer.access_hash != 0) {
                    req.peers.add(inputPeer);
                } else {
                    return;
                }
            }
            if (!req.peers.isEmpty()) {
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$11(this));
            }
        }
    }

    public boolean isChannelAdmin(int chatId, int uid) {
        ArrayList<Integer> array = (ArrayList) this.channelAdmins.get(Integer.valueOf(chatId));
        return array != null && array.indexOf(Integer.valueOf(uid)) >= 0;
    }

    public void loadChannelAdmins(int chatId, boolean cache) {
        if (this.loadingChannelAdmins.indexOfKey(chatId) < 0) {
            this.loadingChannelAdmins.put(chatId, 0);
            if (cache) {
                MessagesStorage.getInstance().loadChannelAdmins(chatId);
                return;
            }
            TLRPC$TL_channels_getParticipants req = new TLRPC$TL_channels_getParticipants();
            ArrayList<Integer> array = (ArrayList) this.channelAdmins.get(Integer.valueOf(chatId));
            if (array != null) {
                long acc = 0;
                for (int a = 0; a < array.size(); a++) {
                    acc = (((20261 * acc) + 2147483648L) + ((long) ((Integer) array.get(a)).intValue())) % 2147483648L;
                }
                req.hash = (int) acc;
            }
            req.channel = getInputChannel(chatId);
            req.limit = 100;
            req.filter = new TLRPC$TL_channelParticipantsAdmins();
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$12(this, chatId));
        }
    }

    public void processLoadedChannelAdmins(ArrayList<Integer> array, int chatId, boolean cache) {
        Collections.sort(array);
        if (!cache) {
            MessagesStorage.getInstance().putChannelAdmins(chatId, array);
        }
        AndroidUtilities.runOnUIThread(new MessagesController$13(this, chatId, array, cache));
    }

    public void loadFullChat(int chat_id, int classGuid, boolean force) {
        boolean loaded = this.loadedFullChats.contains(Integer.valueOf(chat_id));
        if (!this.loadingFullChats.contains(Integer.valueOf(chat_id))) {
            if (force || !loaded) {
                TLObject request;
                this.loadingFullChats.add(Integer.valueOf(chat_id));
                long dialog_id = (long) (-chat_id);
                TLRPC$Chat chat = getChat(Integer.valueOf(chat_id));
                TLObject req;
                if (ChatObject.isChannel(chat)) {
                    req = new TLRPC$TL_channels_getFullChannel();
                    req.channel = getInputChannel(chat);
                    request = req;
                    if (chat.megagroup) {
                        loadChannelAdmins(chat_id, !loaded);
                    }
                } else {
                    req = new TLRPC$TL_messages_getFullChat();
                    req.chat_id = chat_id;
                    request = req;
                    if (this.dialogs_read_inbox_max.get(Long.valueOf(dialog_id)) == null || this.dialogs_read_outbox_max.get(Long.valueOf(dialog_id)) == null) {
                        reloadDialogsReadValue(null, dialog_id);
                    }
                }
                int reqId = ConnectionsManager.getInstance().sendRequest(request, new MessagesController$14(this, chat, dialog_id, chat_id, classGuid));
                if (classGuid != 0) {
                    ConnectionsManager.getInstance().bindRequestToGuid(reqId, classGuid);
                }
            }
        }
    }

    public void loadFullUser(User user, int classGuid, boolean force) {
        if (user != null && !this.loadingFullUsers.contains(Integer.valueOf(user.id))) {
            if (force || !this.loadedFullUsers.contains(Integer.valueOf(user.id))) {
                this.loadingFullUsers.add(Integer.valueOf(user.id));
                TLRPC$TL_users_getFullUser req = new TLRPC$TL_users_getFullUser();
                req.id = getInputUser(user);
                long dialog_id = (long) user.id;
                if (this.dialogs_read_inbox_max.get(Long.valueOf(dialog_id)) == null || this.dialogs_read_outbox_max.get(Long.valueOf(dialog_id)) == null) {
                    reloadDialogsReadValue(null, dialog_id);
                }
                ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new MessagesController$15(this, user, classGuid)), classGuid);
            }
        }
    }

    private void reloadMessages(ArrayList<Integer> mids, long dialog_id) {
        if (!mids.isEmpty()) {
            TLObject request;
            ArrayList<Integer> result = new ArrayList();
            TLRPC$Chat chat = ChatObject.getChatByDialog(dialog_id);
            TLObject req;
            if (ChatObject.isChannel(chat)) {
                req = new TLRPC$TL_channels_getMessages();
                req.channel = getInputChannel(chat);
                req.id = result;
                request = req;
            } else {
                req = new TLRPC$TL_messages_getMessages();
                req.id = result;
                request = req;
            }
            ArrayList<Integer> arrayList = (ArrayList) this.reloadingMessages.get(Long.valueOf(dialog_id));
            for (int a = 0; a < mids.size(); a++) {
                Integer mid = (Integer) mids.get(a);
                if (arrayList == null || !arrayList.contains(mid)) {
                    result.add(mid);
                }
            }
            if (!result.isEmpty()) {
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    this.reloadingMessages.put(Long.valueOf(dialog_id), arrayList);
                }
                arrayList.addAll(result);
                ConnectionsManager.getInstance().sendRequest(request, new MessagesController$16(this, dialog_id, chat, result));
            }
        }
    }

    public void hideReportSpam(long dialogId, User currentUser, TLRPC$Chat currentChat) {
        if (currentUser != null || currentChat != null) {
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("spam3_" + dialogId, 1);
            editor.commit();
            if (((int) dialogId) != 0) {
                TLRPC$TL_messages_hideReportSpam req = new TLRPC$TL_messages_hideReportSpam();
                if (currentUser != null) {
                    req.peer = getInputPeer(currentUser.id);
                } else if (currentChat != null) {
                    req.peer = getInputPeer(-currentChat.id);
                }
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$17(this));
            }
        }
    }

    public void reportSpam(long dialogId, User currentUser, TLRPC$Chat currentChat, TLRPC$EncryptedChat currentEncryptedChat) {
        if (currentUser != null || currentChat != null || currentEncryptedChat != null) {
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("spam3_" + dialogId, 1);
            editor.commit();
            if (((int) dialogId) != 0) {
                TLRPC$TL_messages_reportSpam req = new TLRPC$TL_messages_reportSpam();
                if (currentChat != null) {
                    req.peer = getInputPeer(-currentChat.id);
                } else if (currentUser != null) {
                    req.peer = getInputPeer(currentUser.id);
                }
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$19(this), 2);
            } else if (currentEncryptedChat != null && currentEncryptedChat.access_hash != 0) {
                TLRPC$TL_messages_reportEncryptedSpam req2 = new TLRPC$TL_messages_reportEncryptedSpam();
                req2.peer = new TLRPC$TL_inputEncryptedChat();
                req2.peer.chat_id = currentEncryptedChat.id;
                req2.peer.access_hash = currentEncryptedChat.access_hash;
                ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$18(this), 2);
            }
        }
    }

    public void loadPeerSettings(User currentUser, TLRPC$Chat currentChat) {
        if (currentUser != null || currentChat != null) {
            long dialogId;
            if (currentUser != null) {
                dialogId = (long) currentUser.id;
            } else {
                dialogId = (long) (-currentChat.id);
            }
            if (!this.loadingPeerSettings.containsKey(Long.valueOf(dialogId))) {
                this.loadingPeerSettings.put(Long.valueOf(dialogId), Boolean.valueOf(true));
                FileLog.m91d("request spam button for " + dialogId);
                SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
                if (preferences.getInt("spam3_" + dialogId, 0) == 1) {
                    FileLog.m91d("spam button already hidden for " + dialogId);
                } else if (preferences.getBoolean("spam_" + dialogId, false)) {
                    TLRPC$TL_messages_hideReportSpam req = new TLRPC$TL_messages_hideReportSpam();
                    if (currentUser != null) {
                        req.peer = getInputPeer(currentUser.id);
                    } else if (currentChat != null) {
                        req.peer = getInputPeer(-currentChat.id);
                    }
                    ConnectionsManager.getInstance().sendRequest(req, new MessagesController$20(this, dialogId));
                } else {
                    TLRPC$TL_messages_getPeerSettings req2 = new TLRPC$TL_messages_getPeerSettings();
                    if (currentUser != null) {
                        req2.peer = getInputPeer(currentUser.id);
                    } else if (currentChat != null) {
                        req2.peer = getInputPeer(-currentChat.id);
                    }
                    ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$21(this, dialogId));
                }
            }
        }
    }

    protected void processNewChannelDifferenceParams(int pts, int pts_count, int channelId) {
        FileLog.m92e("processNewChannelDifferenceParams pts = " + pts + " pts_count = " + pts_count + " channeldId = " + channelId);
        if (DialogObject.isChannel((TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf((long) (-channelId))))) {
            Integer channelPts = (Integer) this.channelsPts.get(Integer.valueOf(channelId));
            if (channelPts == null) {
                channelPts = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(channelId));
                if (channelPts.intValue() == 0) {
                    channelPts = Integer.valueOf(1);
                }
                this.channelsPts.put(Integer.valueOf(channelId), channelPts);
            }
            if (channelPts.intValue() + pts_count == pts) {
                FileLog.m92e("APPLY CHANNEL PTS");
                this.channelsPts.put(Integer.valueOf(channelId), Integer.valueOf(pts));
                MessagesStorage.getInstance().saveChannelPts(channelId, pts);
            } else if (channelPts.intValue() != pts) {
                Long updatesStartWaitTime = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(channelId));
                Boolean gettingDifferenceChannel = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(channelId));
                if (gettingDifferenceChannel == null) {
                    gettingDifferenceChannel = Boolean.valueOf(false);
                }
                if (gettingDifferenceChannel.booleanValue() || updatesStartWaitTime == null || Math.abs(System.currentTimeMillis() - updatesStartWaitTime.longValue()) <= 1500) {
                    FileLog.m92e("ADD CHANNEL UPDATE TO QUEUE pts = " + pts + " pts_count = " + pts_count);
                    if (updatesStartWaitTime == null) {
                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(channelId), Long.valueOf(System.currentTimeMillis()));
                    }
                    MessagesController$UserActionUpdatesPts updates = new MessagesController$UserActionUpdatesPts(this, null);
                    updates.pts = pts;
                    updates.pts_count = pts_count;
                    updates.chat_id = channelId;
                    ArrayList<TLRPC$Updates> arrayList = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(channelId));
                    if (arrayList == null) {
                        arrayList = new ArrayList();
                        this.updatesQueueChannels.put(Integer.valueOf(channelId), arrayList);
                    }
                    arrayList.add(updates);
                    return;
                }
                getChannelDifference(channelId);
            }
        }
    }

    protected void processNewDifferenceParams(int seq, int pts, int date, int pts_count) {
        FileLog.m92e("processNewDifferenceParams seq = " + seq + " pts = " + pts + " date = " + date + " pts_count = " + pts_count);
        if (pts != -1) {
            if (MessagesStorage.lastPtsValue + pts_count == pts) {
                FileLog.m92e("APPLY PTS");
                MessagesStorage.lastPtsValue = pts;
                MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
            } else if (MessagesStorage.lastPtsValue != pts) {
                if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500) {
                    FileLog.m92e("ADD UPDATE TO QUEUE pts = " + pts + " pts_count = " + pts_count);
                    if (this.updatesStartWaitTimePts == 0) {
                        this.updatesStartWaitTimePts = System.currentTimeMillis();
                    }
                    MessagesController$UserActionUpdatesPts updates = new MessagesController$UserActionUpdatesPts(this, null);
                    updates.pts = pts;
                    updates.pts_count = pts_count;
                    this.updatesQueuePts.add(updates);
                } else {
                    getDifference();
                }
            }
        }
        if (seq == -1) {
            return;
        }
        if (MessagesStorage.lastSeqValue + 1 == seq) {
            FileLog.m92e("APPLY SEQ");
            MessagesStorage.lastSeqValue = seq;
            if (date != -1) {
                MessagesStorage.lastDateValue = date;
            }
            MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
        } else if (MessagesStorage.lastSeqValue == seq) {
        } else {
            if (this.gettingDifference || this.updatesStartWaitTimeSeq == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeSeq) <= 1500) {
                FileLog.m92e("ADD UPDATE TO QUEUE seq = " + seq);
                if (this.updatesStartWaitTimeSeq == 0) {
                    this.updatesStartWaitTimeSeq = System.currentTimeMillis();
                }
                MessagesController$UserActionUpdatesSeq updates2 = new MessagesController$UserActionUpdatesSeq(this, null);
                updates2.seq = seq;
                this.updatesQueueSeq.add(updates2);
                return;
            }
            getDifference();
        }
    }

    public void didAddedNewTask(int minDate, SparseArray<ArrayList<Long>> mids) {
        Utilities.stageQueue.postRunnable(new MessagesController$22(this, minDate));
        AndroidUtilities.runOnUIThread(new MessagesController$23(this, mids));
    }

    public void getNewDeleteTask(ArrayList<Integer> oldTask, int channelId) {
        Utilities.stageQueue.postRunnable(new MessagesController$24(this, oldTask, channelId));
    }

    private boolean checkDeletingTask(boolean runnable) {
        int currentServerTime = ConnectionsManager.getInstance().getCurrentTime();
        if (this.currentDeletingTaskMids == null) {
            return false;
        }
        if (!runnable && (this.currentDeletingTaskTime == 0 || this.currentDeletingTaskTime > currentServerTime)) {
            return false;
        }
        this.currentDeletingTaskTime = 0;
        if (!(this.currentDeleteTaskRunnable == null || runnable)) {
            Utilities.stageQueue.cancelRunnable(this.currentDeleteTaskRunnable);
        }
        this.currentDeleteTaskRunnable = null;
        AndroidUtilities.runOnUIThread(new MessagesController$25(this, new ArrayList(this.currentDeletingTaskMids)));
        return true;
    }

    public void processLoadedDeleteTask(int taskTime, ArrayList<Integer> messages, int channelId) {
        Utilities.stageQueue.postRunnable(new MessagesController$26(this, messages, taskTime));
    }

    public void loadDialogPhotos(int did, int count, long max_id, boolean fromCache, int classGuid) {
        if (fromCache) {
            MessagesStorage.getInstance().getDialogPhotos(did, count, max_id, classGuid);
        } else if (did > 0) {
            User user = getUser(Integer.valueOf(did));
            if (user != null) {
                TLRPC$TL_photos_getUserPhotos req = new TLRPC$TL_photos_getUserPhotos();
                req.limit = count;
                req.offset = 0;
                req.max_id = (long) ((int) max_id);
                req.user_id = getInputUser(user);
                ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new MessagesController$27(this, did, count, max_id, classGuid)), classGuid);
            }
        } else if (did < 0) {
            TLRPC$TL_messages_search req2 = new TLRPC$TL_messages_search();
            req2.filter = new TLRPC$TL_inputMessagesFilterChatPhotos();
            req2.limit = count;
            req2.offset_id = (int) max_id;
            req2.f87q = "";
            req2.peer = getInputPeer(did);
            ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$28(this, did, count, max_id, classGuid)), classGuid);
        }
    }

    public void blockUser(int user_id) {
        User user = getUser(Integer.valueOf(user_id));
        if (user != null && !this.blockedUsers.contains(Integer.valueOf(user_id))) {
            this.blockedUsers.add(Integer.valueOf(user_id));
            if (user.bot) {
                SearchQuery.removeInline(user_id);
            } else {
                SearchQuery.removePeer(user_id);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
            TLRPC$TL_contacts_block req = new TLRPC$TL_contacts_block();
            req.id = getInputUser(user);
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$29(this, user));
        }
    }

    public static void setUserBannedRole(int chatId, User user, TLRPC$TL_channelBannedRights rights, boolean isMegagroup, BaseFragment parentFragment) {
        if (user != null && rights != null) {
            TLRPC$TL_channels_editBanned req = new TLRPC$TL_channels_editBanned();
            req.channel = getInputChannel(chatId);
            req.user_id = getInputUser(user);
            req.banned_rights = rights;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$30(chatId, parentFragment, req, isMegagroup));
        }
    }

    public static void setUserAdminRole(int chatId, User user, TLRPC$TL_channelAdminRights rights, boolean isMegagroup, BaseFragment parentFragment) {
        if (user != null && rights != null) {
            TLRPC$TL_channels_editAdmin req = new TLRPC$TL_channels_editAdmin();
            req.channel = getInputChannel(chatId);
            req.user_id = getInputUser(user);
            req.admin_rights = rights;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$31(chatId, parentFragment, req, isMegagroup));
        }
    }

    public void unblockUser(int user_id) {
        TLRPC$TL_contacts_unblock req = new TLRPC$TL_contacts_unblock();
        User user = getUser(Integer.valueOf(user_id));
        if (user != null) {
            this.blockedUsers.remove(Integer.valueOf(user.id));
            req.id = getInputUser(user);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.blockedUsersDidLoaded, new Object[0]);
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$32(this, user));
        }
    }

    public void getBlockedUsers(boolean cache) {
        if (UserConfig.isClientActivated() && !this.loadingBlockedUsers) {
            this.loadingBlockedUsers = true;
            if (cache) {
                MessagesStorage.getInstance().getBlockedUsers();
                return;
            }
            TLRPC$TL_contacts_getBlocked req = new TLRPC$TL_contacts_getBlocked();
            req.offset = 0;
            req.limit = 200;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$33(this));
        }
    }

    public void processLoadedBlockedUsers(ArrayList<Integer> ids, ArrayList<User> users, boolean cache) {
        AndroidUtilities.runOnUIThread(new MessagesController$34(this, users, cache, ids));
    }

    public void deleteUserPhoto(TLRPC$InputPhoto photo) {
        if (photo == null) {
            TLRPC$TL_photos_updateProfilePhoto req = new TLRPC$TL_photos_updateProfilePhoto();
            req.id = new TLRPC$TL_inputPhotoEmpty();
            UserConfig.getCurrentUser().photo = new TLRPC$TL_userProfilePhotoEmpty();
            User user = getUser(Integer.valueOf(UserConfig.getClientUserId()));
            if (user == null) {
                user = UserConfig.getCurrentUser();
            }
            if (user != null) {
                user.photo = UserConfig.getCurrentUser().photo;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.mainUserInfoChanged, new Object[0]);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(UPDATE_MASK_ALL));
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$35(this));
                return;
            }
            return;
        }
        TLRPC$TL_photos_deletePhotos req2 = new TLRPC$TL_photos_deletePhotos();
        req2.id.add(photo);
        ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$36(this));
    }

    public void processLoadedUserPhotos(TLRPC$photos_Photos res, int did, int count, long max_id, boolean fromCache, int classGuid) {
        if (!fromCache) {
            MessagesStorage.getInstance().putUsersAndChats(res.users, null, true, true);
            MessagesStorage.getInstance().putDialogPhotos(did, res);
        } else if (res == null || res.photos.isEmpty()) {
            loadDialogPhotos(did, count, max_id, false, classGuid);
            return;
        }
        AndroidUtilities.runOnUIThread(new MessagesController$37(this, res, fromCache, did, count, classGuid));
    }

    public void uploadAndApplyUserAvatar(TLRPC$PhotoSize bigPhoto) {
        if (bigPhoto != null) {
            this.uploadingAvatar = FileLoader.getInstance().getDirectory(4) + "/" + bigPhoto.location.volume_id + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + bigPhoto.location.local_id + ".jpg";
            FileLoader.getInstance().uploadFile(this.uploadingAvatar, false, true, 16777216);
        }
    }

    public void markChannelDialogMessageAsDeleted(ArrayList<Integer> messages, int channelId) {
        MessageObject obj = (MessageObject) this.dialogMessage.get(Long.valueOf((long) (-channelId)));
        if (obj != null) {
            for (int a = 0; a < messages.size(); a++) {
                if (obj.getId() == ((Integer) messages.get(a)).intValue()) {
                    obj.deleted = true;
                    return;
                }
            }
        }
    }

    public void deleteMessages(ArrayList<Integer> messages, ArrayList<Long> randoms, TLRPC$EncryptedChat encryptedChat, int channelId, boolean forAll) {
        deleteMessages(messages, randoms, encryptedChat, channelId, forAll, 0, null);
    }

    public void deleteMessages(ArrayList<Integer> messages, ArrayList<Long> randoms, TLRPC$EncryptedChat encryptedChat, int channelId, boolean forAll, long taskId, TLObject taskRequest) {
        long newTaskId;
        NativeByteBuffer data;
        Throwable e;
        TLRPC$TL_messages_deleteMessages req;
        if ((messages != null && !messages.isEmpty()) || taskRequest != null) {
            ArrayList<Integer> toSend = null;
            if (taskId == 0) {
                int a;
                if (channelId == 0) {
                    for (a = 0; a < messages.size(); a++) {
                        MessageObject obj = (MessageObject) this.dialogMessagesByIds.get((Integer) messages.get(a));
                        if (obj != null) {
                            obj.deleted = true;
                        }
                    }
                } else {
                    markChannelDialogMessageAsDeleted(messages, channelId);
                }
                toSend = new ArrayList();
                for (a = 0; a < messages.size(); a++) {
                    Integer mid = (Integer) messages.get(a);
                    if (mid.intValue() > 0) {
                        toSend.add(mid);
                    }
                }
                MessagesStorage.getInstance().markMessagesAsDeleted(messages, true, channelId);
                MessagesStorage.getInstance().updateDialogsWithDeletedMessages(messages, null, true, channelId);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesDeleted, messages, Integer.valueOf(channelId));
            }
            NativeByteBuffer data2;
            if (channelId != 0) {
                TLRPC$TL_channels_deleteMessages req2;
                if (taskRequest != null) {
                    req2 = (TLRPC$TL_channels_deleteMessages) taskRequest;
                    newTaskId = taskId;
                } else {
                    req2 = new TLRPC$TL_channels_deleteMessages();
                    req2.id = toSend;
                    req2.channel = getInputChannel(channelId);
                    data = null;
                    try {
                        data2 = new NativeByteBuffer(req2.getObjectSize() + 8);
                        try {
                            data2.writeInt32(7);
                            data2.writeInt32(channelId);
                            req2.serializeToStream(data2);
                            data = data2;
                        } catch (Exception e2) {
                            e = e2;
                            data = data2;
                            FileLog.m94e(e);
                            newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                            ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$38(this, channelId, newTaskId));
                            return;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$38(this, channelId, newTaskId));
                        return;
                    }
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                }
                ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$38(this, channelId, newTaskId));
                return;
            }
            if (!(randoms == null || encryptedChat == null || randoms.isEmpty())) {
                SecretChatHelper.getInstance().sendMessagesDeleteMessage(encryptedChat, randoms, null);
            }
            if (taskRequest != null) {
                req = (TLRPC$TL_messages_deleteMessages) taskRequest;
                newTaskId = taskId;
            } else {
                req = new TLRPC$TL_messages_deleteMessages();
                req.id = toSend;
                req.revoke = forAll;
                data = null;
                try {
                    data2 = new NativeByteBuffer(req.getObjectSize() + 8);
                    try {
                        data2.writeInt32(7);
                        data2.writeInt32(channelId);
                        req.serializeToStream(data2);
                        data = data2;
                    } catch (Exception e4) {
                        e = e4;
                        data = data2;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$39(this, newTaskId));
                    }
                } catch (Exception e5) {
                    e = e5;
                    FileLog.m94e(e);
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                    ConnectionsManager.getInstance().sendRequest(req, new MessagesController$39(this, newTaskId));
                }
                newTaskId = MessagesStorage.getInstance().createPendingTask(data);
            }
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$39(this, newTaskId));
        }
    }

    public void pinChannelMessage(TLRPC$Chat chat, int id, boolean notify) {
        TLRPC$TL_channels_updatePinnedMessage req = new TLRPC$TL_channels_updatePinnedMessage();
        req.channel = getInputChannel(chat);
        req.id = id;
        req.silent = !notify;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$40(this));
    }

    public void deleteUserChannelHistory(TLRPC$Chat chat, User user, int offset) {
        if (offset == 0) {
            MessagesStorage.getInstance().deleteUserChannelHistory(chat.id, user.id);
        }
        TLRPC$TL_channels_deleteUserHistory req = new TLRPC$TL_channels_deleteUserHistory();
        req.channel = getInputChannel(chat);
        req.user_id = getInputUser(user);
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$41(this, chat, user));
    }

    public void deleteDialog(long did, int onlyHistory) {
        deleteDialog(did, true, onlyHistory, 0);
    }

    private void deleteDialog(long did, boolean first, int onlyHistory, int max_id) {
        int lower_part = (int) did;
        int high_id = (int) (did >> 32);
        int max_id_delete = max_id;
        if (onlyHistory == 2) {
            MessagesStorage.getInstance().deleteDialog(did, onlyHistory);
            return;
        }
        if (onlyHistory == 0 || onlyHistory == 3) {
            AndroidUtilities.uninstallShortcut(did);
        }
        if (first) {
            MessagesStorage.getInstance().deleteDialog(did, onlyHistory);
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(did));
            if (dialog != null) {
                if (max_id_delete == 0) {
                    max_id_delete = Math.max(0, dialog.top_message);
                }
                if (onlyHistory == 0 || onlyHistory == 3) {
                    this.dialogs.remove(dialog);
                    if (this.dialogsServerOnly.remove(dialog) && DialogObject.isChannel(dialog)) {
                        Utilities.stageQueue.postRunnable(new MessagesController$42(this, did));
                    }
                    this.dialogsUsers.remove(dialog);
                    this.dialogsGroups.remove(dialog);
                    this.dialogsGroupsAll.remove(dialog);
                    this.dialogsChannels.remove(dialog);
                    this.dialogsMegaGroups.remove(dialog);
                    this.dialogsBots.remove(dialog);
                    this.dialogsFavs.remove(dialog);
                    this.dialogsHidden.remove(dialog);
                    this.dialogsAll.remove(dialog);
                    this.dialogsUnread.remove(dialog);
                    this.dialogsAds.remove(dialog);
                    this.dialogsGroupsOnly.remove(dialog);
                    this.dialogs_dict.remove(Long.valueOf(did));
                    this.dialogs_read_inbox_max.remove(Long.valueOf(did));
                    this.dialogs_read_outbox_max.remove(Long.valueOf(did));
                    this.nextDialogsCacheOffset--;
                } else {
                    dialog.unread_count = 0;
                }
                MessageObject object = (MessageObject) this.dialogMessage.remove(Long.valueOf(dialog.id));
                int lastMessageId;
                if (object != null) {
                    lastMessageId = object.getId();
                    this.dialogMessagesByIds.remove(Integer.valueOf(object.getId()));
                } else {
                    lastMessageId = dialog.top_message;
                    object = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(dialog.top_message));
                }
                if (!(object == null || object.messageOwner.random_id == 0)) {
                    this.dialogMessagesByRandomIds.remove(Long.valueOf(object.messageOwner.random_id));
                }
                if (onlyHistory != 1 || lower_part == 0 || lastMessageId <= 0) {
                    dialog.top_message = 0;
                } else {
                    TLRPC$Message message = new TLRPC$TL_messageService();
                    message.id = dialog.top_message;
                    message.out = ((long) UserConfig.getClientUserId()) == did;
                    message.from_id = UserConfig.getClientUserId();
                    message.flags |= 256;
                    message.action = new TLRPC$TL_messageActionHistoryClear();
                    message.date = dialog.last_message_date;
                    if (lower_part > 0) {
                        message.to_id = new TLRPC$TL_peerUser();
                        message.to_id.user_id = lower_part;
                    } else if (ChatObject.isChannel(getChat(Integer.valueOf(-lower_part)))) {
                        message.to_id = new TLRPC$TL_peerChannel();
                        message.to_id.channel_id = -lower_part;
                    } else {
                        message.to_id = new TLRPC$TL_peerChat();
                        message.to_id.chat_id = -lower_part;
                    }
                    MessageObject messageObject = new MessageObject(message, null, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                    ArrayList<MessageObject> objArr = new ArrayList();
                    objArr.add(messageObject);
                    ArrayList<TLRPC$Message> arr = new ArrayList();
                    arr.add(message);
                    updateInterfaceWithMessages(did, objArr);
                    MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
                }
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.removeAllMessagesFromDialog, Long.valueOf(did), Boolean.valueOf(false));
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$43(this, did));
        }
        if (high_id != 1 && onlyHistory != 3) {
            if (lower_part != 0) {
                TLRPC$InputPeer peer = getInputPeer(lower_part);
                if (peer == null) {
                    return;
                }
                TLObject req;
                if (!(peer instanceof TLRPC$TL_inputPeerChannel)) {
                    int i;
                    req = new TLRPC$TL_messages_deleteHistory();
                    req.peer = peer;
                    if (onlyHistory == 0) {
                        i = Integer.MAX_VALUE;
                    } else {
                        i = max_id_delete;
                    }
                    req.max_id = i;
                    req.just_clear = onlyHistory != 0;
                    ConnectionsManager.getInstance().sendRequest(req, new MessagesController$45(this, did, onlyHistory, max_id_delete), 64);
                } else if (onlyHistory != 0) {
                    req = new TLRPC$TL_channels_deleteHistory();
                    req.channel = new TLRPC$TL_inputChannel();
                    req.channel.channel_id = peer.channel_id;
                    req.channel.access_hash = peer.access_hash;
                    req.max_id = max_id_delete > 0 ? max_id_delete : Integer.MAX_VALUE;
                    ConnectionsManager.getInstance().sendRequest(req, new MessagesController$44(this), 64);
                }
            } else if (onlyHistory == 1) {
                SecretChatHelper.getInstance().sendClearHistoryMessage(getEncryptedChat(Integer.valueOf(high_id)), null);
            } else {
                SecretChatHelper.getInstance().declineSecretChat(high_id);
            }
        }
    }

    public void saveGif(TLRPC$Document document) {
        TLRPC$TL_messages_saveGif req = new TLRPC$TL_messages_saveGif();
        req.id = new TLRPC$TL_inputDocument();
        req.id.id = document.id;
        req.id.access_hash = document.access_hash;
        req.unsave = false;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$46(this));
    }

    public void saveRecentSticker(TLRPC$Document document, boolean asMask) {
        TLRPC$TL_messages_saveRecentSticker req = new TLRPC$TL_messages_saveRecentSticker();
        req.id = new TLRPC$TL_inputDocument();
        req.id.id = document.id;
        req.id.access_hash = document.access_hash;
        req.unsave = false;
        req.attached = asMask;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$47(this));
    }

    public void loadChannelParticipants(Integer chat_id) {
        if (!this.loadingFullParticipants.contains(chat_id) && !this.loadedFullParticipants.contains(chat_id)) {
            this.loadingFullParticipants.add(chat_id);
            TLRPC$TL_channels_getParticipants req = new TLRPC$TL_channels_getParticipants();
            req.channel = getInputChannel(chat_id.intValue());
            req.filter = new TLRPC$TL_channelParticipantsRecent();
            req.offset = 0;
            req.limit = 32;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$48(this, chat_id));
        }
    }

    public void loadChatInfo(int chat_id, Semaphore semaphore, boolean force) {
        MessagesStorage.getInstance().loadChatInfo(chat_id, semaphore, force, false);
    }

    public void processChatInfo(int chat_id, TLRPC$ChatFull info, ArrayList<User> usersArr, boolean fromCache, boolean force, boolean byChannelUsers, MessageObject pinnedMessageObject) {
        if (fromCache && chat_id > 0 && !byChannelUsers) {
            loadFullChat(chat_id, 0, force);
        }
        if (info != null) {
            AndroidUtilities.runOnUIThread(new MessagesController$49(this, usersArr, fromCache, info, byChannelUsers, pinnedMessageObject));
        }
    }

    public void updateTimerProc() {
        int a;
        int key;
        long currentTime = System.currentTimeMillis();
        checkDeletingTask(false);
        if (UserConfig.isClientActivated()) {
            TLRPC$TL_account_updateStatus req;
            if (ConnectionsManager.getInstance().getPauseTime() == 0 && ApplicationLoader.isScreenOn && !ApplicationLoader.mainInterfacePausedStageQueue) {
                if (ApplicationLoader.mainInterfacePausedStageQueueTime != 0 && Math.abs(ApplicationLoader.mainInterfacePausedStageQueueTime - System.currentTimeMillis()) > 1000 && this.statusSettingState != 1 && (this.lastStatusUpdateTime == 0 || Math.abs(System.currentTimeMillis() - this.lastStatusUpdateTime) >= 55000 || this.offlineSent)) {
                    this.statusSettingState = 1;
                    if (this.statusRequest != 0) {
                        ConnectionsManager.getInstance().cancelRequest(this.statusRequest, true);
                    }
                    req = new TLRPC$TL_account_updateStatus();
                    req.offline = false;
                    if (Prefs.getGhostMode(ApplicationLoader.applicationContext) == 1) {
                        req.offline = true;
                    }
                    this.statusRequest = ConnectionsManager.getInstance().sendRequest(req, new MessagesController$50(this));
                }
            } else if (!(this.statusSettingState == 2 || this.offlineSent || Math.abs(System.currentTimeMillis() - ConnectionsManager.getInstance().getPauseTime()) < FetchConst.DEFAULT_ON_UPDATE_INTERVAL)) {
                this.statusSettingState = 2;
                if (this.statusRequest != 0) {
                    ConnectionsManager.getInstance().cancelRequest(this.statusRequest, true);
                }
                req = new TLRPC$TL_account_updateStatus();
                req.offline = true;
                this.statusRequest = ConnectionsManager.getInstance().sendRequest(req, new MessagesController$51(this));
            }
            if (!this.updatesQueueChannels.isEmpty()) {
                ArrayList<Integer> keys = new ArrayList(this.updatesQueueChannels.keySet());
                for (a = 0; a < keys.size(); a++) {
                    key = ((Integer) keys.get(a)).intValue();
                    Long updatesStartWaitTime = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(key));
                    if (updatesStartWaitTime != null && updatesStartWaitTime.longValue() + 1500 < currentTime) {
                        FileLog.m92e("QUEUE CHANNEL " + key + " UPDATES WAIT TIMEOUT - CHECK QUEUE");
                        processChannelsUpdatesQueue(key, 0);
                    }
                }
            }
            a = 0;
            while (a < 3) {
                if (getUpdatesStartTime(a) != 0 && getUpdatesStartTime(a) + 1500 < currentTime) {
                    FileLog.m92e(a + " QUEUE UPDATES WAIT TIMEOUT - CHECK QUEUE");
                    processUpdatesQueue(a, 0);
                }
                a++;
            }
        }
        if (this.channelViewsToSend.size() != 0 && Math.abs(System.currentTimeMillis() - this.lastViewsCheckTime) >= DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS) {
            this.lastViewsCheckTime = System.currentTimeMillis();
            a = 0;
            while (a < this.channelViewsToSend.size()) {
                key = this.channelViewsToSend.keyAt(a);
                TLRPC$TL_messages_getMessagesViews req2 = new TLRPC$TL_messages_getMessagesViews();
                req2.peer = getInputPeer(key);
                req2.id = (ArrayList) this.channelViewsToSend.get(key);
                req2.increment = a == 0;
                ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$52(this, key, req2));
                a++;
            }
            this.channelViewsToSend.clear();
        }
        if (!this.onlinePrivacy.isEmpty()) {
            ArrayList<Integer> toRemove = null;
            int currentServerTime = ConnectionsManager.getInstance().getCurrentTime();
            for (Entry<Integer, Integer> entry : this.onlinePrivacy.entrySet()) {
                if (((Integer) entry.getValue()).intValue() < currentServerTime - 30) {
                    if (toRemove == null) {
                        toRemove = new ArrayList();
                    }
                    toRemove.add(entry.getKey());
                }
            }
            if (toRemove != null) {
                Iterator it = toRemove.iterator();
                while (it.hasNext()) {
                    this.onlinePrivacy.remove((Integer) it.next());
                }
                AndroidUtilities.runOnUIThread(new MessagesController$53(this));
            }
        }
        if (this.shortPollChannels.size() != 0) {
            for (a = 0; a < this.shortPollChannels.size(); a++) {
                key = this.shortPollChannels.keyAt(a);
                if (((long) this.shortPollChannels.get(key)) < System.currentTimeMillis() / 1000) {
                    this.shortPollChannels.delete(key);
                    if (this.needShortPollChannels.indexOfKey(key) >= 0) {
                        getChannelDifference(key);
                    }
                }
            }
        }
        if (!(this.printingUsers.isEmpty() && this.lastPrintingStringCount == this.printingUsers.size())) {
            boolean updated = false;
            ArrayList<Long> keys2 = new ArrayList(this.printingUsers.keySet());
            int b = 0;
            while (b < keys2.size()) {
                Long key2 = (Long) keys2.get(b);
                ArrayList<MessagesController$PrintingUser> arr = (ArrayList) this.printingUsers.get(key2);
                a = 0;
                while (a < arr.size()) {
                    int timeToRemove;
                    MessagesController$PrintingUser user = (MessagesController$PrintingUser) arr.get(a);
                    if (user.action instanceof TLRPC$TL_sendMessageGamePlayAction) {
                        timeToRemove = DefaultLoadControl.DEFAULT_MAX_BUFFER_MS;
                    } else {
                        timeToRemove = 5900;
                    }
                    if (user.lastTime + ((long) timeToRemove) < currentTime) {
                        updated = true;
                        arr.remove(user);
                        a--;
                    }
                    a++;
                }
                if (arr.isEmpty()) {
                    this.printingUsers.remove(key2);
                    keys2.remove(b);
                    b--;
                }
                b++;
            }
            updatePrintingStrings();
            if (updated) {
                AndroidUtilities.runOnUIThread(new MessagesController$54(this));
            }
        }
        LocationController.getInstance().update();
    }

    private String getUserNameForTyping(User user) {
        if (user == null) {
            return "";
        }
        if (user.first_name != null && user.first_name.length() > 0) {
            return user.first_name;
        }
        if (user.last_name == null || user.last_name.length() <= 0) {
            return "";
        }
        return user.last_name;
    }

    private void updatePrintingStrings() {
        HashMap<Long, CharSequence> newPrintingStrings = new HashMap();
        HashMap<Long, Integer> newPrintingStringsTypes = new HashMap();
        ArrayList<Long> keys = new ArrayList(this.printingUsers.keySet());
        for (Entry<Long, ArrayList<MessagesController$PrintingUser>> entry : this.printingUsers.entrySet()) {
            long key = ((Long) entry.getKey()).longValue();
            ArrayList<MessagesController$PrintingUser> arr = (ArrayList) entry.getValue();
            int lower_id = (int) key;
            User user;
            if (lower_id > 0 || lower_id == 0 || arr.size() == 1) {
                MessagesController$PrintingUser pu = (MessagesController$PrintingUser) arr.get(0);
                if (getUser(Integer.valueOf(pu.userId)) != null) {
                    if (pu.action instanceof TLRPC$TL_sendMessageRecordAudioAction) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsRecordingAudio", R.string.IsRecordingAudio, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("RecordingAudio", R.string.RecordingAudio));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(1));
                    } else if ((pu.action instanceof TLRPC$TL_sendMessageRecordRoundAction) || (pu.action instanceof TLRPC$TL_sendMessageUploadRoundAction)) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsRecordingRound", R.string.IsRecordingRound, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("RecordingRound", R.string.RecordingRound));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(4));
                    } else if (pu.action instanceof TLRPC$TL_sendMessageUploadAudioAction) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsSendingAudio", R.string.IsSendingAudio, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("SendingAudio", R.string.SendingAudio));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(2));
                    } else if ((pu.action instanceof TLRPC$TL_sendMessageUploadVideoAction) || (pu.action instanceof TLRPC$TL_sendMessageRecordVideoAction)) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsSendingVideo", R.string.IsSendingVideo, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("SendingVideoStatus", R.string.SendingVideoStatus));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(2));
                    } else if (pu.action instanceof TLRPC$TL_sendMessageUploadDocumentAction) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsSendingFile", R.string.IsSendingFile, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("SendingFile", R.string.SendingFile));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(2));
                    } else if (pu.action instanceof TLRPC$TL_sendMessageUploadPhotoAction) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsSendingPhoto", R.string.IsSendingPhoto, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("SendingPhoto", R.string.SendingPhoto));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(2));
                    } else if (pu.action instanceof TLRPC$TL_sendMessageGamePlayAction) {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsSendingGame", R.string.IsSendingGame, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("SendingGame", R.string.SendingGame));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(3));
                    } else {
                        if (lower_id < 0) {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsTypingGroup", R.string.IsTypingGroup, getUserNameForTyping(user)));
                        } else {
                            newPrintingStrings.put(Long.valueOf(key), LocaleController.getString("Typing", R.string.Typing));
                        }
                        newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(0));
                    }
                }
            } else {
                int count = 0;
                StringBuilder label = new StringBuilder();
                Iterator it = arr.iterator();
                while (it.hasNext()) {
                    user = getUser(Integer.valueOf(((MessagesController$PrintingUser) it.next()).userId));
                    if (user != null) {
                        if (label.length() != 0) {
                            label.append(", ");
                        }
                        label.append(getUserNameForTyping(user));
                        count++;
                    }
                    if (count == 2) {
                        break;
                    }
                }
                if (label.length() != 0) {
                    if (count == 1) {
                        newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("IsTypingGroup", R.string.IsTypingGroup, label.toString()));
                    } else if (arr.size() > 2) {
                        String plural = LocaleController.getPluralString("AndMoreTypingGroup", arr.size() - 2);
                        newPrintingStrings.put(Long.valueOf(key), String.format(plural, new Object[]{label.toString(), Integer.valueOf(arr.size() - 2)}));
                    } else {
                        newPrintingStrings.put(Long.valueOf(key), LocaleController.formatString("AreTypingGroup", R.string.AreTypingGroup, label.toString()));
                    }
                    newPrintingStringsTypes.put(Long.valueOf(key), Integer.valueOf(0));
                }
            }
        }
        this.lastPrintingStringCount = newPrintingStrings.size();
        AndroidUtilities.runOnUIThread(new MessagesController$55(this, newPrintingStrings, newPrintingStringsTypes));
    }

    public void cancelTyping(int action, long dialog_id) {
        HashMap<Long, Boolean> typings = (HashMap) this.sendingTypings.get(Integer.valueOf(action));
        if (typings != null) {
            typings.remove(Long.valueOf(dialog_id));
        }
    }

    public void sendTyping(long dialog_id, int action, int classGuid) {
        if (Prefs.getGhostMode(ApplicationLoader.applicationContext) != 1 && dialog_id != 0) {
            HashMap<Long, Boolean> typings = (HashMap) this.sendingTypings.get(Integer.valueOf(action));
            if (typings == null || typings.get(Long.valueOf(dialog_id)) == null) {
                if (typings == null) {
                    typings = new HashMap();
                    this.sendingTypings.put(Integer.valueOf(action), typings);
                }
                int lower_part = (int) dialog_id;
                int high_id = (int) (dialog_id >> 32);
                int reqId;
                if (lower_part != 0) {
                    if (high_id != 1) {
                        TLRPC$TL_messages_setTyping req = new TLRPC$TL_messages_setTyping();
                        req.peer = getInputPeer(lower_part);
                        if (req.peer instanceof TLRPC$TL_inputPeerChannel) {
                            TLRPC$Chat chat = getChat(Integer.valueOf(req.peer.channel_id));
                            if (chat == null || !chat.megagroup) {
                                return;
                            }
                        }
                        if (req.peer != null) {
                            if (action == 0) {
                                req.action = new TLRPC$TL_sendMessageTypingAction();
                            } else if (action == 1) {
                                req.action = new TLRPC$TL_sendMessageRecordAudioAction();
                            } else if (action == 2) {
                                req.action = new TLRPC$TL_sendMessageCancelAction();
                            } else if (action == 3) {
                                req.action = new TLRPC$TL_sendMessageUploadDocumentAction();
                            } else if (action == 4) {
                                req.action = new TLRPC$TL_sendMessageUploadPhotoAction();
                            } else if (action == 5) {
                                req.action = new TLRPC$TL_sendMessageUploadVideoAction();
                            } else if (action == 6) {
                                req.action = new TLRPC$TL_sendMessageGamePlayAction();
                            } else if (action == 7) {
                                req.action = new TLRPC$TL_sendMessageRecordRoundAction();
                            } else if (action == 8) {
                                req.action = new TLRPC$TL_sendMessageUploadRoundAction();
                            }
                            typings.put(Long.valueOf(dialog_id), Boolean.valueOf(true));
                            reqId = ConnectionsManager.getInstance().sendRequest(req, new MessagesController$56(this, action, dialog_id), 2);
                            if (classGuid != 0) {
                                ConnectionsManager.getInstance().bindRequestToGuid(reqId, classGuid);
                            }
                        }
                    }
                } else if (action == 0) {
                    TLRPC$EncryptedChat chat2 = getEncryptedChat(Integer.valueOf(high_id));
                    if (chat2.auth_key != null && chat2.auth_key.length > 1 && (chat2 instanceof TLRPC$TL_encryptedChat)) {
                        TLRPC$TL_messages_setEncryptedTyping req2 = new TLRPC$TL_messages_setEncryptedTyping();
                        req2.peer = new TLRPC$TL_inputEncryptedChat();
                        req2.peer.chat_id = chat2.id;
                        req2.peer.access_hash = chat2.access_hash;
                        req2.typing = true;
                        typings.put(Long.valueOf(dialog_id), Boolean.valueOf(true));
                        reqId = ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$57(this, action, dialog_id), 2);
                        if (classGuid != 0) {
                            ConnectionsManager.getInstance().bindRequestToGuid(reqId, classGuid);
                        }
                    }
                }
            }
        }
    }

    public void loadMessages(long dialog_id, int count, int max_id, int offset_date, boolean fromCache, int midDate, int classGuid, int load_type, int last_message_id, boolean isChannel, int loadIndex) {
        loadMessages(dialog_id, count, max_id, offset_date, fromCache, midDate, classGuid, load_type, last_message_id, isChannel, loadIndex, 0, 0, 0, false, 0);
    }

    public void loadMessages(long dialog_id, int count, int max_id, int offset_date, boolean fromCache, int midDate, int classGuid, int load_type, int last_message_id, boolean isChannel, int loadIndex, int first_unread, int unread_count, int last_date, boolean queryFromServer, int mentionsCount) {
        FileLog.m92e("load messages in chat " + dialog_id + " count " + count + " max_id " + max_id + " cache " + fromCache + " mindate = " + midDate + " guid " + classGuid + " load_type " + load_type + " last_message_id " + last_message_id + " index " + loadIndex + " firstUnread " + first_unread + " unread_count " + unread_count + " last_date " + last_date + " queryFromServer " + queryFromServer);
        int lower_part = (int) dialog_id;
        if (fromCache || lower_part == 0) {
            MessagesStorage.getInstance().getMessages(dialog_id, count, max_id, offset_date, midDate, classGuid, load_type, isChannel, loadIndex);
            return;
        }
        TLObject req = new TLRPC$TL_messages_getHistory();
        req.peer = getInputPeer(lower_part);
        if (load_type == 4) {
            req.add_offset = (-count) + 5;
        } else if (load_type == 3) {
            req.add_offset = (-count) / 2;
        } else if (load_type == 1) {
            req.add_offset = (-count) - 1;
        } else if (load_type == 2 && max_id != 0) {
            req.add_offset = (-count) + 6;
        } else if (lower_part < 0 && max_id != 0 && ChatObject.isChannel(getChat(Integer.valueOf(-lower_part)))) {
            req.add_offset = -1;
            req.limit++;
        }
        req.limit = count;
        req.offset_id = max_id;
        req.offset_date = offset_date;
        ConnectionsManager.getInstance().bindRequestToGuid(ConnectionsManager.getInstance().sendRequest(req, new MessagesController$58(this, count, max_id, offset_date, dialog_id, classGuid, first_unread, last_message_id, unread_count, last_date, load_type, isChannel, loadIndex, queryFromServer, mentionsCount)), classGuid);
    }

    public void reloadWebPages(long dialog_id, HashMap<String, ArrayList<MessageObject>> webpagesToReload) {
        for (Entry<String, ArrayList<MessageObject>> entry : webpagesToReload.entrySet()) {
            String url = (String) entry.getKey();
            ArrayList<MessageObject> messages = (ArrayList) entry.getValue();
            ArrayList<MessageObject> arrayList = (ArrayList) this.reloadingWebpages.get(url);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.reloadingWebpages.put(url, arrayList);
            }
            arrayList.addAll(messages);
            TLRPC$TL_messages_getWebPagePreview req = new TLRPC$TL_messages_getWebPagePreview();
            req.message = url;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$59(this, url, dialog_id));
        }
    }

    public void processLoadedMessages(TLRPC$messages_Messages messagesRes, long dialog_id, int count, int max_id, int offset_date, boolean isCache, int classGuid, int first_unread, int last_message_id, int unread_count, int last_date, int load_type, boolean isChannel, boolean isEnd, int loadIndex, boolean queryFromServer, int mentionsCount) {
        Log.d("LEE", "Debug1946 enter to processLoadedMessages() ");
        Utilities.stageQueue.postRunnable(new MessagesController$60(this, messagesRes, dialog_id, isCache, count, load_type, queryFromServer, first_unread, max_id, offset_date, classGuid, last_message_id, isChannel, loadIndex, unread_count, last_date, mentionsCount, isEnd));
    }

    public void loadHintDialogs() {
        if (this.hintDialogs.isEmpty() && !TextUtils.isEmpty(this.installReferer)) {
            TLRPC$TL_help_getRecentMeUrls req = new TLRPC$TL_help_getRecentMeUrls();
            req.referer = this.installReferer;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$61(this));
        }
    }

    public void loadDialogs(int offset, int count, boolean fromCache) {
        if (!this.loadingDialogs && !this.resetingDialogs) {
            this.loadingDialogs = true;
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            FileLog.m92e("load cacheOffset = " + offset + " count = " + count + " cache = " + fromCache);
            if (fromCache) {
                MessagesStorage.getInstance().getDialogs(offset == 0 ? 0 : this.nextDialogsCacheOffset, count);
                return;
            }
            TLRPC$TL_messages_getDialogs req = new TLRPC$TL_messages_getDialogs();
            req.limit = count;
            req.exclude_pinned = true;
            if (UserConfig.dialogsLoadOffsetId == -1) {
                boolean found = false;
                for (int a = this.dialogs.size() - 1; a >= 0; a--) {
                    TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs.get(a);
                    if (!dialog.pinned) {
                        int high_id = (int) (dialog.id >> 32);
                        if (!(((int) dialog.id) == 0 || high_id == 1 || dialog.top_message <= 0)) {
                            MessageObject message = (MessageObject) this.dialogMessage.get(Long.valueOf(dialog.id));
                            if (message != null && message.getId() > 0) {
                                int id;
                                req.offset_date = message.messageOwner.date;
                                req.offset_id = message.messageOwner.id;
                                if (message.messageOwner.to_id.channel_id != 0) {
                                    id = -message.messageOwner.to_id.channel_id;
                                } else if (message.messageOwner.to_id.chat_id != 0) {
                                    id = -message.messageOwner.to_id.chat_id;
                                } else {
                                    id = message.messageOwner.to_id.user_id;
                                }
                                req.offset_peer = getInputPeer(id);
                                found = true;
                                if (!found) {
                                    req.offset_peer = new TLRPC$TL_inputPeerEmpty();
                                }
                            }
                        }
                    }
                }
                if (found) {
                    req.offset_peer = new TLRPC$TL_inputPeerEmpty();
                }
            } else if (UserConfig.dialogsLoadOffsetId == Integer.MAX_VALUE) {
                this.dialogsEndReached = true;
                this.serverDialogsEndReached = true;
                this.loadingDialogs = false;
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
                return;
            } else {
                req.offset_id = UserConfig.dialogsLoadOffsetId;
                req.offset_date = UserConfig.dialogsLoadOffsetDate;
                if (req.offset_id == 0) {
                    req.offset_peer = new TLRPC$TL_inputPeerEmpty();
                } else {
                    if (UserConfig.dialogsLoadOffsetChannelId != 0) {
                        req.offset_peer = new TLRPC$TL_inputPeerChannel();
                        req.offset_peer.channel_id = UserConfig.dialogsLoadOffsetChannelId;
                    } else if (UserConfig.dialogsLoadOffsetUserId != 0) {
                        req.offset_peer = new TLRPC$TL_inputPeerUser();
                        req.offset_peer.user_id = UserConfig.dialogsLoadOffsetUserId;
                    } else {
                        req.offset_peer = new TLRPC$TL_inputPeerChat();
                        req.offset_peer.chat_id = UserConfig.dialogsLoadOffsetChatId;
                    }
                    req.offset_peer.access_hash = UserConfig.dialogsLoadOffsetAccess;
                }
            }
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$62(this, count));
        }
    }

    public void forceResetDialogs() {
        resetDialogs(true, MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
    }

    private void resetDialogs(boolean query, int seq, int newPts, int date, int qts) {
        if (query) {
            if (!this.resetingDialogs) {
                this.resetingDialogs = true;
                ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getPinnedDialogs(), new MessagesController$63(this, seq, newPts, date, qts));
                TLObject req2 = new TLRPC$TL_messages_getDialogs();
                req2.limit = 100;
                req2.exclude_pinned = true;
                req2.offset_peer = new TLRPC$TL_inputPeerEmpty();
                ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$64(this, seq, newPts, date, qts));
            }
        } else if (this.resetDialogsPinned != null && this.resetDialogsAll != null) {
            int a;
            TLRPC$Message message;
            TLRPC$Chat chat;
            Integer value;
            int messagesCount = this.resetDialogsAll.messages.size();
            int dialogsCount = this.resetDialogsAll.dialogs.size();
            this.resetDialogsAll.dialogs.addAll(this.resetDialogsPinned.dialogs);
            this.resetDialogsAll.messages.addAll(this.resetDialogsPinned.messages);
            this.resetDialogsAll.users.addAll(this.resetDialogsPinned.users);
            this.resetDialogsAll.chats.addAll(this.resetDialogsPinned.chats);
            HashMap<Long, TLRPC$TL_dialog> new_dialogs_dict = new HashMap();
            HashMap<Long, MessageObject> new_dialogMessage = new HashMap();
            AbstractMap usersDict = new HashMap();
            AbstractMap chatsDict = new HashMap();
            for (a = 0; a < this.resetDialogsAll.users.size(); a++) {
                User u = (User) this.resetDialogsAll.users.get(a);
                usersDict.put(Integer.valueOf(u.id), u);
            }
            for (a = 0; a < this.resetDialogsAll.chats.size(); a++) {
                TLRPC$Chat c = (TLRPC$Chat) this.resetDialogsAll.chats.get(a);
                chatsDict.put(Integer.valueOf(c.id), c);
            }
            TLRPC$Message lastMessage = null;
            for (a = 0; a < this.resetDialogsAll.messages.size(); a++) {
                message = (TLRPC$Message) this.resetDialogsAll.messages.get(a);
                if (a < messagesCount && (lastMessage == null || message.date < lastMessage.date)) {
                    lastMessage = message;
                }
                MessageObject messageObject;
                if (message.to_id.channel_id != 0) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.channel_id));
                    if (chat == null || !chat.left) {
                        if (chat != null && chat.megagroup) {
                            message.flags |= Integer.MIN_VALUE;
                        }
                        messageObject = new MessageObject(message, usersDict, chatsDict, false);
                        new_dialogMessage.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                    }
                } else {
                    if (message.to_id.chat_id != 0) {
                        chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.chat_id));
                        if (!(chat == null || chat.migrated_to == null)) {
                        }
                    }
                    messageObject = new MessageObject(message, usersDict, chatsDict, false);
                    new_dialogMessage.put(Long.valueOf(messageObject.getDialogId()), messageObject);
                }
            }
            for (a = 0; a < this.resetDialogsAll.dialogs.size(); a++) {
                TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.resetDialogsAll.dialogs.get(a);
                if (d.id == 0 && d.peer != null) {
                    if (d.peer.user_id != 0) {
                        d.id = (long) d.peer.user_id;
                    } else if (d.peer.chat_id != 0) {
                        d.id = (long) (-d.peer.chat_id);
                    } else if (d.peer.channel_id != 0) {
                        d.id = (long) (-d.peer.channel_id);
                    }
                }
                if (d.id != 0) {
                    if (d.last_message_date == 0) {
                        MessageObject mess = (MessageObject) new_dialogMessage.get(Long.valueOf(d.id));
                        if (mess != null) {
                            d.last_message_date = mess.messageOwner.date;
                        }
                    }
                    if (DialogObject.isChannel(d)) {
                        chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                        if (chat == null || !chat.left) {
                            this.channelsPts.put(Integer.valueOf(-((int) d.id)), Integer.valueOf(d.pts));
                        }
                    } else if (((int) d.id) < 0) {
                        chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-((int) d.id)));
                        if (!(chat == null || chat.migrated_to == null)) {
                        }
                    }
                    new_dialogs_dict.put(Long.valueOf(d.id), d);
                    value = (Integer) this.dialogs_read_inbox_max.get(Long.valueOf(d.id));
                    if (value == null) {
                        value = Integer.valueOf(0);
                    }
                    this.dialogs_read_inbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_inbox_max_id)));
                    value = (Integer) this.dialogs_read_outbox_max.get(Long.valueOf(d.id));
                    if (value == null) {
                        value = Integer.valueOf(0);
                    }
                    this.dialogs_read_outbox_max.put(Long.valueOf(d.id), Integer.valueOf(Math.max(value.intValue(), d.read_outbox_max_id)));
                }
            }
            ImageLoader.saveMessagesThumbs(this.resetDialogsAll.messages);
            for (a = 0; a < this.resetDialogsAll.messages.size(); a++) {
                message = (TLRPC$Message) this.resetDialogsAll.messages.get(a);
                if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    User user = (User) usersDict.get(Integer.valueOf(message.action.user_id));
                    if (user != null && user.bot) {
                        message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                        message.flags |= 64;
                    }
                }
                if ((message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate)) {
                    message.unread = false;
                    message.media_unread = false;
                } else {
                    boolean z;
                    ConcurrentHashMap<Long, Integer> read_max = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                    value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
                    if (value == null) {
                        value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                        read_max.put(Long.valueOf(message.dialog_id), value);
                    }
                    if (value.intValue() < message.id) {
                        z = true;
                    } else {
                        z = false;
                    }
                    message.unread = z;
                }
            }
            MessagesStorage.getInstance().resetDialogs(this.resetDialogsAll, messagesCount, seq, newPts, date, qts, new_dialogs_dict, new_dialogMessage, lastMessage, dialogsCount);
            this.resetDialogsPinned = null;
            this.resetDialogsAll = null;
        }
    }

    protected void completeDialogsReset(TLRPC$messages_Dialogs dialogsRes, int messagesCount, int seq, int newPts, int date, int qts, HashMap<Long, TLRPC$TL_dialog> new_dialogs_dict, HashMap<Long, MessageObject> new_dialogMessage, TLRPC$Message lastMessage) {
        Utilities.stageQueue.postRunnable(new MessagesController$65(this, newPts, date, qts, dialogsRes, new_dialogs_dict, new_dialogMessage));
    }

    private void migrateDialogs(int offset, int offsetDate, int offsetUser, int offsetChat, int offsetChannel, long accessPeer) {
        if (!this.migratingDialogs && offset != -1) {
            this.migratingDialogs = true;
            TLRPC$TL_messages_getDialogs req = new TLRPC$TL_messages_getDialogs();
            req.exclude_pinned = true;
            req.limit = 100;
            req.offset_id = offset;
            req.offset_date = offsetDate;
            FileLog.m92e("start migrate with id " + offset + " date " + LocaleController.getInstance().formatterStats.format(((long) offsetDate) * 1000));
            if (offset == 0) {
                req.offset_peer = new TLRPC$TL_inputPeerEmpty();
            } else {
                if (offsetChannel != 0) {
                    req.offset_peer = new TLRPC$TL_inputPeerChannel();
                    req.offset_peer.channel_id = offsetChannel;
                } else if (offsetUser != 0) {
                    req.offset_peer = new TLRPC$TL_inputPeerUser();
                    req.offset_peer.user_id = offsetUser;
                } else {
                    req.offset_peer = new TLRPC$TL_inputPeerChat();
                    req.offset_peer.chat_id = offsetChat;
                }
                req.offset_peer.access_hash = accessPeer;
            }
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$66(this, offset));
        }
    }

    public void processLoadedDialogs(TLRPC$messages_Dialogs dialogsRes, ArrayList<TLRPC$EncryptedChat> encChats, int offset, int count, int loadType, boolean resetEnd, boolean migrate, boolean fromCache) {
        Utilities.stageQueue.postRunnable(new MessagesController$67(this, loadType, dialogsRes, resetEnd, count, offset, fromCache, migrate, encChats));
    }

    private void applyDialogNotificationsSettings(long dialog_id, TLRPC$PeerNotifySettings notify_settings) {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        int currentValue = preferences.getInt("notify2_" + dialog_id, 0);
        int currentValue2 = preferences.getInt("notifyuntil_" + dialog_id, 0);
        Editor editor = preferences.edit();
        boolean updated = false;
        TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(dialog_id));
        if (dialog != null) {
            dialog.notify_settings = notify_settings;
        }
        editor.putBoolean("silent_" + dialog_id, notify_settings.silent);
        if (notify_settings.mute_until > ConnectionsManager.getInstance().getCurrentTime()) {
            int until = 0;
            if (notify_settings.mute_until <= ConnectionsManager.getInstance().getCurrentTime() + 31536000) {
                if (!(currentValue == 3 && currentValue2 == notify_settings.mute_until)) {
                    updated = true;
                    editor.putInt("notify2_" + dialog_id, 3);
                    editor.putInt("notifyuntil_" + dialog_id, notify_settings.mute_until);
                    if (dialog != null) {
                        dialog.notify_settings.mute_until = 0;
                    }
                }
                until = notify_settings.mute_until;
            } else if (currentValue != 2) {
                updated = true;
                editor.putInt("notify2_" + dialog_id, 2);
                if (dialog != null) {
                    dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                }
            }
            MessagesStorage.getInstance().setDialogFlags(dialog_id, (((long) until) << 32) | 1);
            NotificationsController.getInstance().removeNotificationsForDialog(dialog_id);
        } else {
            if (!(currentValue == 0 || currentValue == 1)) {
                updated = true;
                if (dialog != null) {
                    dialog.notify_settings.mute_until = 0;
                }
                editor.remove("notify2_" + dialog_id);
            }
            MessagesStorage.getInstance().setDialogFlags(dialog_id, 0);
        }
        editor.commit();
        if (updated) {
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.notificationsSettingsUpdated, new Object[0]);
        }
    }

    private void applyDialogsNotificationsSettings(ArrayList<TLRPC$TL_dialog> dialogs) {
        Editor editor = null;
        for (int a = 0; a < dialogs.size(); a++) {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) dialogs.get(a);
            if (dialog.peer != null && (dialog.notify_settings instanceof TLRPC$TL_peerNotifySettings)) {
                int dialog_id;
                if (editor == null) {
                    editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
                }
                if (dialog.peer.user_id != 0) {
                    dialog_id = dialog.peer.user_id;
                } else if (dialog.peer.chat_id != 0) {
                    dialog_id = -dialog.peer.chat_id;
                } else {
                    dialog_id = -dialog.peer.channel_id;
                }
                editor.putBoolean("silent_" + dialog_id, dialog.notify_settings.silent);
                if (dialog.notify_settings.mute_until == 0) {
                    editor.remove("notify2_" + dialog_id);
                } else if (dialog.notify_settings.mute_until > ConnectionsManager.getInstance().getCurrentTime() + 31536000) {
                    editor.putInt("notify2_" + dialog_id, 2);
                    dialog.notify_settings.mute_until = Integer.MAX_VALUE;
                } else {
                    editor.putInt("notify2_" + dialog_id, 3);
                    editor.putInt("notifyuntil_" + dialog_id, dialog.notify_settings.mute_until);
                }
            }
        }
        if (editor != null) {
            editor.commit();
        }
    }

    public void reloadMentionsCountForChannels(ArrayList<Integer> arrayList) {
        AndroidUtilities.runOnUIThread(new MessagesController$68(this, arrayList));
    }

    public void processDialogsUpdateRead(HashMap<Long, Integer> dialogsToUpdate, HashMap<Long, Integer> dialogsMentionsToUpdate) {
        AndroidUtilities.runOnUIThread(new MessagesController$69(this, dialogsToUpdate, dialogsMentionsToUpdate));
    }

    protected void checkLastDialogMessage(TLRPC$TL_dialog dialog, TLRPC$InputPeer peer, long taskId) {
        Throwable e;
        long newTaskId;
        int lower_id = (int) dialog.id;
        if (lower_id != 0 && !this.checkingLastMessagesDialogs.containsKey(Integer.valueOf(lower_id))) {
            TLRPC$InputPeer inputPeer;
            TLRPC$TL_messages_getHistory req = new TLRPC$TL_messages_getHistory();
            if (peer == null) {
                inputPeer = getInputPeer(lower_id);
            } else {
                inputPeer = peer;
            }
            req.peer = inputPeer;
            if (req.peer != null && !(req.peer instanceof TLRPC$TL_inputPeerChannel)) {
                req.limit = 1;
                this.checkingLastMessagesDialogs.put(Integer.valueOf(lower_id), Boolean.valueOf(true));
                if (taskId == 0) {
                    NativeByteBuffer data = null;
                    try {
                        NativeByteBuffer data2 = new NativeByteBuffer(req.peer.getObjectSize() + 48);
                        try {
                            data2.writeInt32(8);
                            data2.writeInt64(dialog.id);
                            data2.writeInt32(dialog.top_message);
                            data2.writeInt32(dialog.read_inbox_max_id);
                            data2.writeInt32(dialog.read_outbox_max_id);
                            data2.writeInt32(dialog.unread_count);
                            data2.writeInt32(dialog.last_message_date);
                            data2.writeInt32(dialog.pts);
                            data2.writeInt32(dialog.flags);
                            data2.writeBool(dialog.pinned);
                            data2.writeInt32(dialog.pinnedNum);
                            data2.writeInt32(dialog.unread_mentions_count);
                            peer.serializeToStream(data2);
                            data = data2;
                        } catch (Exception e2) {
                            e = e2;
                            data = data2;
                            FileLog.m94e(e);
                            newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$70(this, dialog, newTaskId, lower_id));
                        }
                    } catch (Exception e3) {
                        e = e3;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$70(this, dialog, newTaskId, lower_id));
                    }
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                } else {
                    newTaskId = taskId;
                }
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$70(this, dialog, newTaskId, lower_id));
            }
        }
    }

    public void processDialogsUpdate(TLRPC$messages_Dialogs dialogsRes, ArrayList<TLRPC$EncryptedChat> arrayList) {
        Utilities.stageQueue.postRunnable(new MessagesController$71(this, dialogsRes));
    }

    public void addToViewsQueue(TLRPC$Message message) {
        Utilities.stageQueue.postRunnable(new MessagesController$72(this, message));
    }

    public void markMessageContentAsRead(MessageObject messageObject) {
        ArrayList<Long> arrayList = new ArrayList();
        long messageId = (long) messageObject.getId();
        if (messageObject.messageOwner.to_id.channel_id != 0) {
            messageId |= ((long) messageObject.messageOwner.to_id.channel_id) << 32;
        }
        if (messageObject.messageOwner.mentioned) {
            MessagesStorage.getInstance().markMentionMessageAsRead(messageObject.getId(), messageObject.messageOwner.to_id.channel_id, messageObject.getDialogId());
        }
        arrayList.add(Long.valueOf(messageId));
        MessagesStorage.getInstance().markMessagesContentAsRead(arrayList, 0);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.messagesReadContent, arrayList);
        if (messageObject.getId() < 0) {
            markMessageAsRead(messageObject.getDialogId(), messageObject.messageOwner.random_id, Integer.MIN_VALUE);
        } else if (messageObject.messageOwner.to_id.channel_id != 0) {
            TLRPC$TL_channels_readMessageContents req = new TLRPC$TL_channels_readMessageContents();
            req.channel = getInputChannel(messageObject.messageOwner.to_id.channel_id);
            if (req.channel != null) {
                req.id.add(Integer.valueOf(messageObject.getId()));
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$73(this));
            }
        } else {
            TLRPC$TL_messages_readMessageContents req2 = new TLRPC$TL_messages_readMessageContents();
            req2.id.add(Integer.valueOf(messageObject.getId()));
            ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$74(this));
        }
    }

    public void markMentionMessageAsRead(int mid, int channelId, long did) {
        MessagesStorage.getInstance().markMentionMessageAsRead(mid, channelId, did);
        if (channelId != 0) {
            TLRPC$TL_channels_readMessageContents req = new TLRPC$TL_channels_readMessageContents();
            req.channel = getInputChannel(channelId);
            if (req.channel != null) {
                req.id.add(Integer.valueOf(mid));
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$75(this));
                return;
            }
            return;
        }
        TLRPC$TL_messages_readMessageContents req2 = new TLRPC$TL_messages_readMessageContents();
        req2.id.add(Integer.valueOf(mid));
        ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$76(this));
    }

    public void markMessageAsRead(int mid, int channelId, int ttl) {
        if (mid != 0 && ttl > 0) {
            int time = ConnectionsManager.getInstance().getCurrentTime();
            MessagesStorage.getInstance().createTaskForMid(mid, channelId, time, time, ttl, false);
            if (channelId != 0) {
                TLRPC$TL_channels_readMessageContents req = new TLRPC$TL_channels_readMessageContents();
                req.channel = getInputChannel(channelId);
                req.id.add(Integer.valueOf(mid));
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$77(this));
                return;
            }
            TLRPC$TL_messages_readMessageContents req2 = new TLRPC$TL_messages_readMessageContents();
            req2.id.add(Integer.valueOf(mid));
            ConnectionsManager.getInstance().sendRequest(req2, new MessagesController$78(this));
        }
    }

    public void markMessageAsRead(long dialog_id, long random_id, int ttl) {
        if (random_id != 0 && dialog_id != 0) {
            if (ttl > 0 || ttl == Integer.MIN_VALUE) {
                int high_id = (int) (dialog_id >> 32);
                if (((int) dialog_id) == 0) {
                    TLRPC$EncryptedChat chat = getEncryptedChat(Integer.valueOf(high_id));
                    if (chat != null) {
                        ArrayList<Long> random_ids = new ArrayList();
                        random_ids.add(Long.valueOf(random_id));
                        SecretChatHelper.getInstance().sendMessagesReadMessage(chat, random_ids, null);
                        if (ttl > 0) {
                            int time = ConnectionsManager.getInstance().getCurrentTime();
                            MessagesStorage.getInstance().createTaskForSecretChat(chat.id, time, time, 0, random_ids);
                        }
                    }
                }
            }
        }
    }

    public void markDialogAsRead(long dialog_id, int max_id, int max_positive_id, int max_date, boolean was, boolean popup) {
        int lower_part = (int) dialog_id;
        int high_id = (int) (dialog_id >> 32);
        TLObject req;
        if (lower_part != 0) {
            if (max_positive_id != 0 && high_id != 1) {
                TLRPC$InputPeer inputPeer = getInputPeer(lower_part);
                long messageId = (long) max_positive_id;
                TLObject request;
                if (inputPeer instanceof TLRPC$TL_inputPeerChannel) {
                    request = new TLRPC$TL_channels_readHistory();
                    request.channel = getInputChannel(-lower_part);
                    request.max_id = max_positive_id;
                    req = request;
                    messageId |= ((long) (-lower_part)) << 32;
                } else {
                    request = new TLRPC$TL_messages_readHistory();
                    request.peer = inputPeer;
                    request.max_id = max_positive_id;
                    req = request;
                }
                Integer value = (Integer) this.dialogs_read_inbox_max.get(Long.valueOf(dialog_id));
                if (value == null) {
                    value = Integer.valueOf(0);
                }
                this.dialogs_read_inbox_max.put(Long.valueOf(dialog_id), Integer.valueOf(Math.max(value.intValue(), max_positive_id)));
                MessagesStorage.getInstance().processPendingRead(dialog_id, messageId, max_date);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$79(this, dialog_id, popup, max_positive_id));
                if (Prefs.getGhostMode(ApplicationLoader.applicationContext) == 0 && max_positive_id != Integer.MAX_VALUE) {
                    ConnectionsManager.getInstance().sendRequest(req, new MessagesController$80(this));
                }
            }
        } else if (max_date != 0) {
            TLRPC$EncryptedChat chat = getEncryptedChat(Integer.valueOf(high_id));
            if (Prefs.getGhostMode(ApplicationLoader.applicationContext) == 0 && chat.auth_key != null && chat.auth_key.length > 1 && (chat instanceof TLRPC$TL_encryptedChat)) {
                req = new TLRPC$TL_messages_readEncryptedHistory();
                req.peer = new TLRPC$TL_inputEncryptedChat();
                req.peer.chat_id = chat.id;
                req.peer.access_hash = chat.access_hash;
                req.max_date = max_date;
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$81(this));
            }
            MessagesStorage.getInstance().processPendingRead(dialog_id, (long) max_id, max_date);
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$82(this, dialog_id, max_date, popup));
            if (chat.ttl > 0 && was) {
                int serverTime = Math.max(ConnectionsManager.getInstance().getCurrentTime(), max_date);
                MessagesStorage.getInstance().createTaskForSecretChat(chat.id, serverTime, serverTime, 0, null);
            }
        }
    }

    public int createChat(String title, ArrayList<Integer> selectedContacts, String about, int type, BaseFragment fragment) {
        int a;
        if (type == 1) {
            TLRPC$TL_chat chat = new TLRPC$TL_chat();
            chat.id = UserConfig.lastBroadcastId;
            chat.title = title;
            chat.photo = new TLRPC$TL_chatPhotoEmpty();
            chat.participants_count = selectedContacts.size();
            chat.date = (int) (System.currentTimeMillis() / 1000);
            chat.version = 1;
            UserConfig.lastBroadcastId--;
            putChat(chat, false);
            ArrayList<TLRPC$Chat> chatsArrays = new ArrayList();
            chatsArrays.add(chat);
            MessagesStorage.getInstance().putUsersAndChats(null, chatsArrays, true, true);
            TLRPC$TL_chatFull chatFull = new TLRPC$TL_chatFull();
            chatFull.id = chat.id;
            chatFull.chat_photo = new TLRPC$TL_photoEmpty();
            chatFull.notify_settings = new TLRPC$TL_peerNotifySettingsEmpty();
            chatFull.exported_invite = new TLRPC$TL_chatInviteEmpty();
            chatFull.participants = new TLRPC$TL_chatParticipants();
            chatFull.participants.chat_id = chat.id;
            chatFull.participants.admin_id = UserConfig.getClientUserId();
            chatFull.participants.version = 1;
            for (a = 0; a < selectedContacts.size(); a++) {
                TLRPC$TL_chatParticipant participant = new TLRPC$TL_chatParticipant();
                participant.user_id = ((Integer) selectedContacts.get(a)).intValue();
                participant.inviter_id = UserConfig.getClientUserId();
                participant.date = (int) (System.currentTimeMillis() / 1000);
                chatFull.participants.participants.add(participant);
            }
            MessagesStorage.getInstance().updateChatInfo(chatFull, false);
            TLRPC$TL_messageService newMsg = new TLRPC$TL_messageService();
            newMsg.action = new TLRPC$TL_messageActionCreatedBroadcastList();
            int newMessageId = UserConfig.getNewMessageId();
            newMsg.id = newMessageId;
            newMsg.local_id = newMessageId;
            newMsg.from_id = UserConfig.getClientUserId();
            newMsg.dialog_id = AndroidUtilities.makeBroadcastId(chat.id);
            newMsg.to_id = new TLRPC$TL_peerChat();
            newMsg.to_id.chat_id = chat.id;
            newMsg.date = ConnectionsManager.getInstance().getCurrentTime();
            newMsg.random_id = 0;
            newMsg.flags |= 256;
            UserConfig.saveConfig(false);
            MessageObject newMsgObj = new MessageObject(newMsg, this.users, true);
            newMsgObj.messageOwner.send_state = 0;
            ArrayList<MessageObject> objArr = new ArrayList();
            objArr.add(newMsgObj);
            ArrayList<TLRPC$Message> arr = new ArrayList();
            arr.add(newMsg);
            MessagesStorage.getInstance().putMessages(arr, false, true, false, 0);
            updateInterfaceWithMessages(newMsg.dialog_id, objArr);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatDidCreated, Integer.valueOf(chat.id));
            return 0;
        } else if (type == 0) {
            req = new TLRPC$TL_messages_createChat();
            req.title = title;
            for (a = 0; a < selectedContacts.size(); a++) {
                User user = getUser((Integer) selectedContacts.get(a));
                if (user != null) {
                    req.users.add(getInputUser(user));
                }
            }
            return ConnectionsManager.getInstance().sendRequest(req, new MessagesController$83(this, fragment, req), 2);
        } else if (type != 2 && type != 4) {
            return 0;
        } else {
            req = new TLRPC$TL_channels_createChannel();
            req.title = title;
            req.about = about;
            if (type == 4) {
                req.megagroup = true;
            } else {
                req.broadcast = true;
            }
            return ConnectionsManager.getInstance().sendRequest(req, new MessagesController$84(this, fragment, req), 2);
        }
    }

    public void convertToMegaGroup(Context context, int chat_id) {
        TLRPC$TL_messages_migrateChat req = new TLRPC$TL_messages_migrateChat();
        req.chat_id = chat_id;
        AlertDialog progressDialog = new AlertDialog(context, 1);
        progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new MessagesController$86(this, ConnectionsManager.getInstance().sendRequest(req, new MessagesController$85(this, context, progressDialog))));
        try {
            progressDialog.show();
        } catch (Exception e) {
        }
    }

    public void addUsersToChannel(int chat_id, ArrayList<TLRPC$InputUser> users, BaseFragment fragment) {
        if (users != null && !users.isEmpty()) {
            TLRPC$TL_channels_inviteToChannel req = new TLRPC$TL_channels_inviteToChannel();
            req.channel = getInputChannel(chat_id);
            req.users = users;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$87(this, fragment, req));
        }
    }

    public void toogleChannelInvites(int chat_id, boolean enabled) {
        TLRPC$TL_channels_toggleInvites req = new TLRPC$TL_channels_toggleInvites();
        req.channel = getInputChannel(chat_id);
        req.enabled = enabled;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$88(this), 64);
    }

    public void toogleChannelSignatures(int chat_id, boolean enabled) {
        TLRPC$TL_channels_toggleSignatures req = new TLRPC$TL_channels_toggleSignatures();
        req.channel = getInputChannel(chat_id);
        req.enabled = enabled;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$89(this), 64);
    }

    public void toogleChannelInvitesHistory(int chat_id, boolean enabled) {
        TLRPC$TL_channels_togglePreHistoryHidden req = new TLRPC$TL_channels_togglePreHistoryHidden();
        req.channel = getInputChannel(chat_id);
        req.enabled = enabled;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$90(this), 64);
    }

    public void updateChannelAbout(int chat_id, String about, TLRPC$ChatFull info) {
        if (info != null) {
            TLRPC$TL_channels_editAbout req = new TLRPC$TL_channels_editAbout();
            req.channel = getInputChannel(chat_id);
            req.about = about;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$91(this, info, about), 64);
        }
    }

    public void updateChannelUserName(int chat_id, String userName) {
        TLRPC$TL_channels_updateUsername req = new TLRPC$TL_channels_updateUsername();
        req.channel = getInputChannel(chat_id);
        req.username = userName;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$92(this, chat_id, userName), 64);
    }

    public void sendBotStart(User user, String botHash) {
        if (user != null) {
            TLRPC$TL_messages_startBot req = new TLRPC$TL_messages_startBot();
            req.bot = getInputUser(user);
            req.peer = getInputPeer(user.id);
            req.start_param = botHash;
            req.random_id = Utilities.random.nextLong();
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$93(this));
        }
    }

    public void toggleAdminMode(int chat_id, boolean enabled) {
        TLRPC$TL_messages_toggleChatAdmins req = new TLRPC$TL_messages_toggleChatAdmins();
        req.chat_id = chat_id;
        req.enabled = enabled;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$94(this, chat_id));
    }

    public void toggleUserAdmin(int chat_id, int user_id, boolean admin) {
        TLRPC$TL_messages_editChatAdmin req = new TLRPC$TL_messages_editChatAdmin();
        req.chat_id = chat_id;
        req.user_id = getInputUser(user_id);
        req.is_admin = admin;
        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$95(this));
    }

    public void addUserToChat(int chat_id, User user, TLRPC$ChatFull info, int count_fwd, String botHash, BaseFragment fragment) {
        if (user != null) {
            if (chat_id > 0) {
                TLObject request;
                boolean isChannel = ChatObject.isChannel(chat_id);
                boolean isMegagroup = isChannel && getChat(Integer.valueOf(chat_id)).megagroup;
                TLRPC$InputUser inputUser = getInputUser(user);
                TLObject req;
                if (botHash != null && (!isChannel || isMegagroup)) {
                    req = new TLRPC$TL_messages_startBot();
                    req.bot = inputUser;
                    if (isChannel) {
                        req.peer = getInputPeer(-chat_id);
                    } else {
                        req.peer = new TLRPC$TL_inputPeerChat();
                        req.peer.chat_id = chat_id;
                    }
                    req.start_param = botHash;
                    req.random_id = Utilities.random.nextLong();
                    request = req;
                } else if (!isChannel) {
                    req = new TLRPC$TL_messages_addChatUser();
                    req.chat_id = chat_id;
                    req.fwd_limit = count_fwd;
                    req.user_id = inputUser;
                    request = req;
                } else if (!(inputUser instanceof TLRPC$TL_inputUserSelf)) {
                    req = new TLRPC$TL_channels_inviteToChannel();
                    req.channel = getInputChannel(chat_id);
                    req.users.add(inputUser);
                    request = req;
                } else if (!this.joiningToChannels.contains(Integer.valueOf(chat_id))) {
                    req = new TLRPC$TL_channels_joinChannel();
                    req.channel = getInputChannel(chat_id);
                    request = req;
                    this.joiningToChannels.add(Integer.valueOf(chat_id));
                } else {
                    return;
                }
                ConnectionsManager.getInstance().sendRequest(request, new MessagesController$96(this, isChannel, inputUser, chat_id, fragment, request, isMegagroup));
            } else if (info instanceof TLRPC$TL_chatFull) {
                int a = 0;
                while (a < info.participants.participants.size()) {
                    if (((TLRPC$ChatParticipant) info.participants.participants.get(a)).user_id != user.id) {
                        a++;
                    } else {
                        return;
                    }
                }
                TLRPC$Chat chat = getChat(Integer.valueOf(chat_id));
                chat.participants_count++;
                ArrayList<TLRPC$Chat> chatArrayList = new ArrayList();
                chatArrayList.add(chat);
                MessagesStorage.getInstance().putUsersAndChats(null, chatArrayList, true, true);
                TLRPC$TL_chatParticipant newPart = new TLRPC$TL_chatParticipant();
                newPart.user_id = user.id;
                newPart.inviter_id = UserConfig.getClientUserId();
                newPart.date = ConnectionsManager.getInstance().getCurrentTime();
                info.participants.participants.add(0, newPart);
                MessagesStorage.getInstance().updateChatInfo(info, true);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, info, Integer.valueOf(0), Boolean.valueOf(false), null);
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(32));
            }
            if (AppPreferences.isAdsEnable(ApplicationLoader.applicationContext)) {
                ArrayList<Category> tmpCategories = new ArrayList();
                boolean isAdsChannel = false;
                Iterator it = AppPreferences.getAdsChannel(ApplicationLoader.applicationContext).iterator();
                while (it.hasNext()) {
                    Category category = (Category) it.next();
                    if (category.getChannelId() == chat_id) {
                        Category tmp = category;
                        tmp.setStatus(1);
                        tmpCategories.add(tmp);
                        isAdsChannel = true;
                        break;
                    }
                }
                if (isAdsChannel) {
                    HandleRequest.getNew(ApplicationLoader.applicationContext, new MessagesController$97(this)).manageCategory(tmpCategories);
                }
            }
        }
    }

    public void deleteUserFromChat(int chat_id, User user, TLRPC$ChatFull info) {
        deleteUserFromChat(chat_id, user, info, false);
    }

    public void deleteUserFromChat(int chat_id, User user, TLRPC$ChatFull info, boolean forceDelete) {
        if (user != null) {
            TLRPC$Chat chat;
            if (chat_id > 0) {
                TLObject request;
                TLRPC$InputUser inputUser = getInputUser(user);
                chat = getChat(Integer.valueOf(chat_id));
                boolean isChannel = ChatObject.isChannel(chat);
                TLObject req;
                if (!isChannel) {
                    req = new TLRPC$TL_messages_deleteChatUser();
                    req.chat_id = chat_id;
                    req.user_id = getInputUser(user);
                    request = req;
                } else if (!(inputUser instanceof TLRPC$TL_inputUserSelf)) {
                    req = new TLRPC$TL_channels_editBanned();
                    req.channel = getInputChannel(chat);
                    req.user_id = inputUser;
                    req.banned_rights = new TLRPC$TL_channelBannedRights();
                    req.banned_rights.view_messages = true;
                    req.banned_rights.send_media = true;
                    req.banned_rights.send_messages = true;
                    req.banned_rights.send_stickers = true;
                    req.banned_rights.send_gifs = true;
                    req.banned_rights.send_games = true;
                    req.banned_rights.send_inline = true;
                    req.banned_rights.embed_links = true;
                    request = req;
                } else if (chat.creator && forceDelete) {
                    req = new TLRPC$TL_channels_deleteChannel();
                    req.channel = getInputChannel(chat);
                    request = req;
                } else {
                    req = new TLRPC$TL_channels_leaveChannel();
                    req.channel = getInputChannel(chat);
                    request = req;
                }
                ConnectionsManager.getInstance().sendRequest(request, new MessagesController$98(this, user, chat_id, isChannel, inputUser), 64);
            } else if (info instanceof TLRPC$TL_chatFull) {
                chat = getChat(Integer.valueOf(chat_id));
                chat.participants_count--;
                ArrayList<TLRPC$Chat> chatArrayList = new ArrayList();
                chatArrayList.add(chat);
                MessagesStorage.getInstance().putUsersAndChats(null, chatArrayList, true, true);
                boolean changed = false;
                for (int a = 0; a < info.participants.participants.size(); a++) {
                    if (((TLRPC$ChatParticipant) info.participants.participants.get(a)).user_id == user.id) {
                        info.participants.participants.remove(a);
                        changed = true;
                        break;
                    }
                }
                if (changed) {
                    MessagesStorage.getInstance().updateChatInfo(info, true);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoDidLoaded, info, Integer.valueOf(0), Boolean.valueOf(false), null);
                }
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(32));
            }
        }
    }

    public void changeChatTitle(int chat_id, String title) {
        if (chat_id > 0) {
            TLObject request;
            TLObject req;
            if (ChatObject.isChannel(chat_id)) {
                req = new TLRPC$TL_channels_editTitle();
                req.channel = getInputChannel(chat_id);
                req.title = title;
                request = req;
            } else {
                req = new TLRPC$TL_messages_editChatTitle();
                req.chat_id = chat_id;
                req.title = title;
                request = req;
            }
            ConnectionsManager.getInstance().sendRequest(request, new MessagesController$99(this), 64);
            return;
        }
        TLRPC$Chat chat = getChat(Integer.valueOf(chat_id));
        chat.title = title;
        ArrayList<TLRPC$Chat> chatArrayList = new ArrayList();
        chatArrayList.add(chat);
        MessagesStorage.getInstance().putUsersAndChats(null, chatArrayList, true, true);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.updateInterfaces, Integer.valueOf(16));
    }

    public void changeChatAvatar(int chat_id, TLRPC$InputFile uploadedAvatar) {
        TLObject request;
        TLObject req;
        if (ChatObject.isChannel(chat_id)) {
            req = new TLRPC$TL_channels_editPhoto();
            req.channel = getInputChannel(chat_id);
            if (uploadedAvatar != null) {
                req.photo = new TLRPC$TL_inputChatUploadedPhoto();
                req.photo.file = uploadedAvatar;
            } else {
                req.photo = new TLRPC$TL_inputChatPhotoEmpty();
            }
            request = req;
        } else {
            req = new TLRPC$TL_messages_editChatPhoto();
            req.chat_id = chat_id;
            if (uploadedAvatar != null) {
                req.photo = new TLRPC$TL_inputChatUploadedPhoto();
                req.photo.file = uploadedAvatar;
            } else {
                req.photo = new TLRPC$TL_inputChatPhotoEmpty();
            }
            request = req;
        }
        ConnectionsManager.getInstance().sendRequest(request, new MessagesController$100(this), 64);
    }

    public void unregistedPush() {
        if (UserConfig.registeredForPush && UserConfig.pushString.length() == 0) {
            TLRPC$TL_account_unregisterDevice req = new TLRPC$TL_account_unregisterDevice();
            req.token = UserConfig.pushString;
            req.token_type = 2;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$101(this));
        }
    }

    public void performLogout(boolean byUser) {
        ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit().clear().commit();
        ApplicationLoader.applicationContext.getSharedPreferences("emoji", 0).edit().putLong("lastGifLoadTime", 0).putLong("lastStickersLoadTime", 0).putLong("lastStickersLoadTimeMask", 0).putLong("lastStickersLoadTimeFavs", 0).commit();
        ApplicationLoader.applicationContext.getSharedPreferences("mainconfig", 0).edit().remove("gifhint").commit();
        if (byUser) {
            unregistedPush();
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_auth_logOut(), new MessagesController$102(this));
        } else {
            ConnectionsManager.getInstance().cleanup();
        }
        UserConfig.clearConfig();
        NotificationCenter.getInstance().postNotificationName(NotificationCenter.appDidLogout, new Object[0]);
        MessagesStorage.getInstance().cleanup(false);
        cleanup();
        ContactsController.getInstance().deleteAllAppAccounts();
    }

    public void generateUpdateMessage() {
        if (!BuildVars.DEBUG_VERSION && UserConfig.lastUpdateVersion != null && !UserConfig.lastUpdateVersion.equals(BuildVars.BUILD_VERSION_STRING)) {
            TLRPC$TL_help_getAppChangelog req = new TLRPC$TL_help_getAppChangelog();
            req.prev_app_version = UserConfig.lastUpdateVersion;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$103(this));
        }
    }

    public void registerForPush(String regid) {
        if (regid != null && regid.length() != 0 && !this.registeringForPush && UserConfig.getClientUserId() != 0) {
            if (!UserConfig.registeredForPush || !regid.equals(UserConfig.pushString)) {
                this.registeringForPush = true;
                TLRPC$TL_account_registerDevice req = new TLRPC$TL_account_registerDevice();
                req.token_type = 2;
                req.token = regid;
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$104(this, regid));
            }
        }
    }

    public void loadCurrentState() {
        if (!this.updatingState) {
            this.updatingState = true;
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_updates_getState(), new MessagesController$105(this));
        }
    }

    private int getUpdateSeq(TLRPC$Updates updates) {
        if (updates instanceof TLRPC$TL_updatesCombined) {
            return updates.seq_start;
        }
        return updates.seq;
    }

    private void setUpdatesStartTime(int type, long time) {
        if (type == 0) {
            this.updatesStartWaitTimeSeq = time;
        } else if (type == 1) {
            this.updatesStartWaitTimePts = time;
        } else if (type == 2) {
            this.updatesStartWaitTimeQts = time;
        }
    }

    public long getUpdatesStartTime(int type) {
        if (type == 0) {
            return this.updatesStartWaitTimeSeq;
        }
        if (type == 1) {
            return this.updatesStartWaitTimePts;
        }
        if (type == 2) {
            return this.updatesStartWaitTimeQts;
        }
        return 0;
    }

    private int isValidUpdate(TLRPC$Updates updates, int type) {
        if (type == 0) {
            int seq = getUpdateSeq(updates);
            if (MessagesStorage.lastSeqValue + 1 == seq || MessagesStorage.lastSeqValue == seq) {
                return 0;
            }
            if (MessagesStorage.lastSeqValue >= seq) {
                return 2;
            }
            return 1;
        } else if (type == 1) {
            if (updates.pts <= MessagesStorage.lastPtsValue) {
                return 2;
            }
            if (MessagesStorage.lastPtsValue + updates.pts_count == updates.pts) {
                return 0;
            }
            return 1;
        } else if (type != 2) {
            return 0;
        } else {
            if (updates.pts <= MessagesStorage.lastQtsValue) {
                return 2;
            }
            if (MessagesStorage.lastQtsValue + updates.updates.size() == updates.pts) {
                return 0;
            }
            return 1;
        }
    }

    private void processChannelsUpdatesQueue(int channelId, int state) {
        ArrayList<TLRPC$Updates> updatesQueue = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(channelId));
        if (updatesQueue != null) {
            Integer channelPts = (Integer) this.channelsPts.get(Integer.valueOf(channelId));
            if (updatesQueue.isEmpty() || channelPts == null) {
                this.updatesQueueChannels.remove(Integer.valueOf(channelId));
                return;
            }
            Collections.sort(updatesQueue, new MessagesController$106(this));
            boolean anyProceed = false;
            if (state == 2) {
                this.channelsPts.put(Integer.valueOf(channelId), Integer.valueOf(((TLRPC$Updates) updatesQueue.get(0)).pts));
            }
            int a = 0;
            while (updatesQueue.size() > 0) {
                int updateState;
                TLRPC$Updates updates = (TLRPC$Updates) updatesQueue.get(a);
                if (updates.pts <= channelPts.intValue()) {
                    updateState = 2;
                } else if (channelPts.intValue() + updates.pts_count == updates.pts) {
                    updateState = 0;
                } else {
                    updateState = 1;
                }
                if (updateState == 0) {
                    processUpdates(updates, true);
                    anyProceed = true;
                    updatesQueue.remove(a);
                    a--;
                } else if (updateState == 1) {
                    Long updatesStartWaitTime = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(channelId));
                    if (updatesStartWaitTime == null || (!anyProceed && Math.abs(System.currentTimeMillis() - updatesStartWaitTime.longValue()) > 1500)) {
                        FileLog.m92e("HOLE IN CHANNEL " + channelId + " UPDATES QUEUE - getChannelDifference ");
                        this.updatesStartWaitTimeChannels.remove(Integer.valueOf(channelId));
                        this.updatesQueueChannels.remove(Integer.valueOf(channelId));
                        getChannelDifference(channelId);
                        return;
                    }
                    FileLog.m92e("HOLE IN CHANNEL " + channelId + " UPDATES QUEUE - will wait more time");
                    if (anyProceed) {
                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(channelId), Long.valueOf(System.currentTimeMillis()));
                        return;
                    }
                    return;
                } else {
                    updatesQueue.remove(a);
                    a--;
                }
                a++;
            }
            this.updatesQueueChannels.remove(Integer.valueOf(channelId));
            this.updatesStartWaitTimeChannels.remove(Integer.valueOf(channelId));
            FileLog.m92e("UPDATES CHANNEL " + channelId + " QUEUE PROCEED - OK");
        }
    }

    private void processUpdatesQueue(int type, int state) {
        ArrayList<TLRPC$Updates> updatesQueue = null;
        if (type == 0) {
            updatesQueue = this.updatesQueueSeq;
            Collections.sort(updatesQueue, new MessagesController$107(this));
        } else if (type == 1) {
            updatesQueue = this.updatesQueuePts;
            Collections.sort(updatesQueue, new MessagesController$108(this));
        } else if (type == 2) {
            updatesQueue = this.updatesQueueQts;
            Collections.sort(updatesQueue, new MessagesController$109(this));
        }
        if (!(updatesQueue == null || updatesQueue.isEmpty())) {
            TLRPC$Updates updates;
            boolean anyProceed = false;
            if (state == 2) {
                updates = (TLRPC$Updates) updatesQueue.get(0);
                if (type == 0) {
                    MessagesStorage.lastSeqValue = getUpdateSeq(updates);
                } else if (type == 1) {
                    MessagesStorage.lastPtsValue = updates.pts;
                } else {
                    MessagesStorage.lastQtsValue = updates.pts;
                }
            }
            int a = 0;
            while (updatesQueue.size() > 0) {
                updates = (TLRPC$Updates) updatesQueue.get(a);
                int updateState = isValidUpdate(updates, type);
                if (updateState == 0) {
                    processUpdates(updates, true);
                    anyProceed = true;
                    updatesQueue.remove(a);
                    a--;
                } else if (updateState != 1) {
                    updatesQueue.remove(a);
                    a--;
                } else if (getUpdatesStartTime(type) == 0 || (!anyProceed && Math.abs(System.currentTimeMillis() - getUpdatesStartTime(type)) > 1500)) {
                    FileLog.m92e("HOLE IN UPDATES QUEUE - getDifference");
                    setUpdatesStartTime(type, 0);
                    updatesQueue.clear();
                    getDifference();
                    return;
                } else {
                    FileLog.m92e("HOLE IN UPDATES QUEUE - will wait more time");
                    if (anyProceed) {
                        setUpdatesStartTime(type, System.currentTimeMillis());
                        return;
                    }
                    return;
                }
                a++;
            }
            updatesQueue.clear();
            FileLog.m92e("UPDATES QUEUE PROCEED - OK");
        }
        setUpdatesStartTime(type, 0);
    }

    protected void loadUnknownChannel(TLRPC$Chat channel, long taskId) {
        Throwable e;
        if ((channel instanceof TLRPC$TL_channel) && !this.gettingUnknownChannels.containsKey(Integer.valueOf(channel.id))) {
            if (channel.access_hash != 0) {
                long newTaskId;
                TLRPC$TL_inputPeerChannel inputPeer = new TLRPC$TL_inputPeerChannel();
                inputPeer.channel_id = channel.id;
                inputPeer.access_hash = channel.access_hash;
                this.gettingUnknownChannels.put(Integer.valueOf(channel.id), Boolean.valueOf(true));
                TLRPC$TL_messages_getPeerDialogs req = new TLRPC$TL_messages_getPeerDialogs();
                req.peers.add(inputPeer);
                if (taskId == 0) {
                    NativeByteBuffer data = null;
                    try {
                        NativeByteBuffer data2 = new NativeByteBuffer(channel.getObjectSize() + 4);
                        try {
                            data2.writeInt32(0);
                            channel.serializeToStream(data2);
                            data = data2;
                        } catch (Exception e2) {
                            e = e2;
                            data = data2;
                            FileLog.m94e(e);
                            newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$110(this, newTaskId, channel));
                        }
                    } catch (Exception e3) {
                        e = e3;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$110(this, newTaskId, channel));
                    }
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                } else {
                    newTaskId = taskId;
                }
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$110(this, newTaskId, channel));
            } else if (taskId != 0) {
                MessagesStorage.getInstance().removePendingTask(taskId);
            }
        }
    }

    public void startShortPoll(int channelId, boolean stop) {
        Utilities.stageQueue.postRunnable(new MessagesController$111(this, stop, channelId));
    }

    private void getChannelDifference(int channelId) {
        getChannelDifference(channelId, 0, 0, null);
    }

    public static boolean isSupportId(int id) {
        return id / 1000 == 777 || id == 333000 || id == 4240000 || id == 4240000 || id == 4244000 || id == 4245000 || id == 4246000 || id == 410000 || id == 420000 || id == 431000 || id == 431415000 || id == 434000 || id == 4243000 || id == 439000 || id == 449000 || id == 450000 || id == 452000 || id == 454000 || id == 4254000 || id == 455000 || id == 460000 || id == 470000 || id == 479000 || id == 796000 || id == 482000 || id == 490000 || id == 496000 || id == 497000 || id == 498000 || id == 4298000;
    }

    protected void getChannelDifference(int channelId, int newDialogType, long taskId, TLRPC$InputChannel inputChannel) {
        Integer channelPts;
        Throwable e;
        long newTaskId;
        TLRPC$TL_updates_getChannelDifference req;
        Boolean gettingDifferenceChannel = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(channelId));
        if (gettingDifferenceChannel == null) {
            gettingDifferenceChannel = Boolean.valueOf(false);
        }
        if (!gettingDifferenceChannel.booleanValue()) {
            int limit = 100;
            if (newDialogType != 1) {
                channelPts = (Integer) this.channelsPts.get(Integer.valueOf(channelId));
                if (channelPts == null) {
                    channelPts = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(channelId));
                    if (channelPts.intValue() != 0) {
                        this.channelsPts.put(Integer.valueOf(channelId), channelPts);
                    }
                    if (channelPts.intValue() == 0 && (newDialogType == 2 || newDialogType == 3)) {
                        return;
                    }
                }
                if (channelPts.intValue() == 0) {
                    return;
                }
            } else if (((Integer) this.channelsPts.get(Integer.valueOf(channelId))) == null) {
                channelPts = Integer.valueOf(1);
                limit = 1;
            } else {
                return;
            }
            if (inputChannel == null) {
                TLRPC$Chat chat = getChat(Integer.valueOf(channelId));
                if (chat == null) {
                    chat = MessagesStorage.getInstance().getChatSync(channelId);
                    if (chat != null) {
                        putChat(chat, true);
                    }
                }
                inputChannel = getInputChannel(chat);
            }
            if (inputChannel != null && inputChannel.access_hash != 0) {
                if (taskId == 0) {
                    NativeByteBuffer data = null;
                    try {
                        NativeByteBuffer data2 = new NativeByteBuffer(inputChannel.getObjectSize() + 12);
                        try {
                            data2.writeInt32(6);
                            data2.writeInt32(channelId);
                            data2.writeInt32(newDialogType);
                            inputChannel.serializeToStream(data2);
                            data = data2;
                        } catch (Exception e2) {
                            e = e2;
                            data = data2;
                            FileLog.m94e(e);
                            newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                            this.gettingDifferenceChannels.put(Integer.valueOf(channelId), Boolean.valueOf(true));
                            req = new TLRPC$TL_updates_getChannelDifference();
                            req.channel = inputChannel;
                            req.filter = new TLRPC$TL_channelMessagesFilterEmpty();
                            req.pts = channelPts.intValue();
                            req.limit = limit;
                            req.force = newDialogType == 3;
                            FileLog.m92e("start getChannelDifference with pts = " + channelPts + " channelId = " + channelId);
                            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$112(this, channelId, newDialogType, newTaskId));
                        }
                    } catch (Exception e3) {
                        e = e3;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        this.gettingDifferenceChannels.put(Integer.valueOf(channelId), Boolean.valueOf(true));
                        req = new TLRPC$TL_updates_getChannelDifference();
                        req.channel = inputChannel;
                        req.filter = new TLRPC$TL_channelMessagesFilterEmpty();
                        req.pts = channelPts.intValue();
                        req.limit = limit;
                        if (newDialogType == 3) {
                        }
                        req.force = newDialogType == 3;
                        FileLog.m92e("start getChannelDifference with pts = " + channelPts + " channelId = " + channelId);
                        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$112(this, channelId, newDialogType, newTaskId));
                    }
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                } else {
                    newTaskId = taskId;
                }
                this.gettingDifferenceChannels.put(Integer.valueOf(channelId), Boolean.valueOf(true));
                req = new TLRPC$TL_updates_getChannelDifference();
                req.channel = inputChannel;
                req.filter = new TLRPC$TL_channelMessagesFilterEmpty();
                req.pts = channelPts.intValue();
                req.limit = limit;
                if (newDialogType == 3) {
                }
                req.force = newDialogType == 3;
                FileLog.m92e("start getChannelDifference with pts = " + channelPts + " channelId = " + channelId);
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$112(this, channelId, newDialogType, newTaskId));
            } else if (taskId != 0) {
                MessagesStorage.getInstance().removePendingTask(taskId);
            }
        }
    }

    private void checkChannelError(String text, int channelId) {
        int i = -1;
        switch (text.hashCode()) {
            case -1809401834:
                if (text.equals("USER_BANNED_IN_CHANNEL")) {
                    i = 2;
                    break;
                }
                break;
            case -795226617:
                if (text.equals("CHANNEL_PRIVATE")) {
                    i = 0;
                    break;
                }
                break;
            case -471086771:
                if (text.equals("CHANNEL_PUBLIC_GROUP_NA")) {
                    i = 1;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(channelId), Integer.valueOf(0));
                return;
            case 1:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(channelId), Integer.valueOf(1));
                return;
            case 2:
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.chatInfoCantLoad, Integer.valueOf(channelId), Integer.valueOf(2));
                return;
            default:
                return;
        }
    }

    public void getDifference() {
        getDifference(MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue, false);
    }

    public void getDifference(int pts, int date, int qts, boolean slice) {
        registerForPush(UserConfig.pushString);
        if (MessagesStorage.lastPtsValue == 0) {
            loadCurrentState();
        } else if (slice || !this.gettingDifference) {
            this.gettingDifference = true;
            TLRPC$TL_updates_getDifference req = new TLRPC$TL_updates_getDifference();
            req.pts = pts;
            req.date = date;
            req.qts = qts;
            if (this.getDifferenceFirstSync) {
                req.flags |= 1;
                if (ConnectionsManager.isConnectedOrConnectingToWiFi()) {
                    req.pts_total_limit = 5000;
                } else {
                    req.pts_total_limit = 1000;
                }
                this.getDifferenceFirstSync = false;
            }
            if (req.date == 0) {
                req.date = ConnectionsManager.getInstance().getCurrentTime();
            }
            FileLog.m92e("start getDifference with date = " + date + " pts = " + pts + " qts = " + qts);
            ConnectionsManager.getInstance().setIsUpdating(true);
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$113(this, date, qts));
        }
    }

    public boolean canPinDialog(boolean secret) {
        int count = 0;
        for (int a = 0; a < this.dialogs.size(); a++) {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs.get(a);
            int lower_id = (int) dialog.id;
            if ((!secret || lower_id == 0) && ((secret || lower_id != 0) && dialog.pinned)) {
                count++;
            }
        }
        return count < this.maxPinnedDialogsCount;
    }

    public boolean pinDialog(long did, boolean pin, TLRPC$InputPeer peer, long taskId) {
        Throwable e;
        long newTaskId;
        int lower_id = (int) did;
        TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(did));
        if (dialog != null && dialog.pinned != pin) {
            dialog.pinned = pin;
            if (pin) {
                int maxPinnedNum = 0;
                for (int a = 0; a < this.dialogs.size(); a++) {
                    TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.dialogs.get(a);
                    if (!d.pinned) {
                        break;
                    }
                    maxPinnedNum = Math.max(d.pinnedNum, maxPinnedNum);
                }
                dialog.pinnedNum = maxPinnedNum + 1;
            } else {
                dialog.pinnedNum = 0;
            }
            sortDialogs(null);
            if (!pin && this.dialogs.get(this.dialogs.size() - 1) == dialog) {
                this.dialogs.remove(this.dialogs.size() - 1);
            }
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.dialogsNeedReload, new Object[0]);
            if (!(lower_id == 0 || taskId == -1)) {
                TLRPC$TL_messages_toggleDialogPin req = new TLRPC$TL_messages_toggleDialogPin();
                req.pinned = pin;
                if (peer == null) {
                    peer = getInputPeer(lower_id);
                }
                if (peer instanceof TLRPC$TL_inputPeerEmpty) {
                    return false;
                }
                req.peer = peer;
                if (taskId == 0) {
                    NativeByteBuffer data = null;
                    try {
                        NativeByteBuffer data2 = new NativeByteBuffer(peer.getObjectSize() + 16);
                        try {
                            data2.writeInt32(1);
                            data2.writeInt64(did);
                            data2.writeBool(pin);
                            peer.serializeToStream(data2);
                            data = data2;
                        } catch (Exception e2) {
                            e = e2;
                            data = data2;
                            FileLog.m94e(e);
                            newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$114(this, newTaskId));
                            MessagesStorage.getInstance().setDialogPinned(did, dialog.pinnedNum);
                            return true;
                        }
                    } catch (Exception e3) {
                        e = e3;
                        FileLog.m94e(e);
                        newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                        ConnectionsManager.getInstance().sendRequest(req, new MessagesController$114(this, newTaskId));
                        MessagesStorage.getInstance().setDialogPinned(did, dialog.pinnedNum);
                        return true;
                    }
                    newTaskId = MessagesStorage.getInstance().createPendingTask(data);
                } else {
                    newTaskId = taskId;
                }
                ConnectionsManager.getInstance().sendRequest(req, new MessagesController$114(this, newTaskId));
            }
            MessagesStorage.getInstance().setDialogPinned(did, dialog.pinnedNum);
            return true;
        } else if (dialog != null) {
            return true;
        } else {
            return false;
        }
    }

    public void loadPinnedDialogs(long newDialogId, ArrayList<Long> order) {
        if (!UserConfig.pinnedDialogsLoaded) {
            ConnectionsManager.getInstance().sendRequest(new TLRPC$TL_messages_getPinnedDialogs(), new MessagesController$115(this, order, newDialogId));
        }
    }

    public void generateJoinMessage(int chat_id, boolean ignoreLeft) {
        TLRPC$Chat chat = getChat(Integer.valueOf(chat_id));
        if (chat != null && ChatObject.isChannel(chat_id)) {
            if ((!chat.left && !chat.kicked) || ignoreLeft) {
                TLRPC$TL_messageService message = new TLRPC$TL_messageService();
                message.flags = 256;
                int newMessageId = UserConfig.getNewMessageId();
                message.id = newMessageId;
                message.local_id = newMessageId;
                message.date = ConnectionsManager.getInstance().getCurrentTime();
                message.from_id = UserConfig.getClientUserId();
                message.to_id = new TLRPC$TL_peerChannel();
                message.to_id.channel_id = chat_id;
                message.dialog_id = (long) (-chat_id);
                message.post = true;
                message.action = new TLRPC$TL_messageActionChatAddUser();
                message.action.users.add(Integer.valueOf(UserConfig.getClientUserId()));
                if (chat.megagroup) {
                    message.flags |= Integer.MIN_VALUE;
                }
                UserConfig.saveConfig(false);
                ArrayList<MessageObject> pushMessages = new ArrayList();
                ArrayList<TLRPC$Message> messagesArr = new ArrayList();
                messagesArr.add(message);
                pushMessages.add(new MessageObject(message, null, true));
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$116(this, pushMessages));
                MessagesStorage.getInstance().putMessages(messagesArr, true, true, false, 0);
                AndroidUtilities.runOnUIThread(new MessagesController$117(this, chat_id, pushMessages));
            }
        }
    }

    public void checkChannelInviter(int chat_id) {
        AndroidUtilities.runOnUIThread(new MessagesController$118(this, chat_id));
    }

    private int getUpdateType(TLRPC$Update update) {
        if ((update instanceof TLRPC$TL_updateNewMessage) || (update instanceof TLRPC$TL_updateReadMessagesContents) || (update instanceof TLRPC$TL_updateReadHistoryInbox) || (update instanceof TLRPC$TL_updateReadHistoryOutbox) || (update instanceof TLRPC$TL_updateDeleteMessages) || (update instanceof TLRPC$TL_updateWebPage) || (update instanceof TLRPC$TL_updateEditMessage)) {
            return 0;
        }
        if (update instanceof TLRPC$TL_updateNewEncryptedMessage) {
            return 1;
        }
        if ((update instanceof TLRPC$TL_updateNewChannelMessage) || (update instanceof TLRPC$TL_updateDeleteChannelMessages) || (update instanceof TLRPC$TL_updateEditChannelMessage) || (update instanceof TLRPC$TL_updateChannelWebPage)) {
            return 2;
        }
        return 3;
    }

    private int getUpdateChannelId(TLRPC$Update update) {
        if (update instanceof TLRPC$TL_updateNewChannelMessage) {
            return ((TLRPC$TL_updateNewChannelMessage) update).message.to_id.channel_id;
        }
        if (update instanceof TLRPC$TL_updateEditChannelMessage) {
            return ((TLRPC$TL_updateEditChannelMessage) update).message.to_id.channel_id;
        }
        return update.channel_id;
    }

    public void processUpdates(TLRPC$Updates updates, boolean fromQueue) {
        int a;
        ArrayList<Integer> needGetChannelsDiff = null;
        boolean needGetDiff = false;
        boolean needReceivedQueue = false;
        boolean updateStatus = false;
        if (updates instanceof TLRPC$TL_updateShort) {
            ArrayList<TLRPC$Update> arr = new ArrayList();
            arr.add(updates.update);
            processUpdateArray(arr, null, null, false);
        } else if ((updates instanceof TLRPC$TL_updateShortChatMessage) || (updates instanceof TLRPC$TL_updateShortMessage)) {
            int user_id;
            boolean missingData;
            if (updates instanceof TLRPC$TL_updateShortChatMessage) {
                user_id = updates.from_id;
            } else {
                user_id = updates.user_id;
            }
            User user = getUser(Integer.valueOf(user_id));
            User user2 = null;
            User user3 = null;
            TLRPC$Chat channel = null;
            if (user == null || user.min) {
                user = MessagesStorage.getInstance().getUserSync(user_id);
                if (user != null && user.min) {
                    user = null;
                }
                putUser(user, true);
            }
            boolean needFwdUser = false;
            if (updates.fwd_from != null) {
                if (updates.fwd_from.from_id != 0) {
                    user2 = getUser(Integer.valueOf(updates.fwd_from.from_id));
                    if (user2 == null) {
                        user2 = MessagesStorage.getInstance().getUserSync(updates.fwd_from.from_id);
                        putUser(user2, true);
                    }
                    needFwdUser = true;
                }
                if (updates.fwd_from.channel_id != 0) {
                    channel = getChat(Integer.valueOf(updates.fwd_from.channel_id));
                    if (channel == null) {
                        channel = MessagesStorage.getInstance().getChatSync(updates.fwd_from.channel_id);
                        putChat(channel, true);
                    }
                    needFwdUser = true;
                }
            }
            boolean needBotUser = false;
            if (updates.via_bot_id != 0) {
                user3 = getUser(Integer.valueOf(updates.via_bot_id));
                if (user3 == null) {
                    user3 = MessagesStorage.getInstance().getUserSync(updates.via_bot_id);
                    putUser(user3, true);
                }
                needBotUser = true;
            }
            if (updates instanceof TLRPC$TL_updateShortMessage) {
                missingData = user == null || ((needFwdUser && user2 == null && channel == null) || (needBotUser && user3 == null));
            } else {
                chat = getChat(Integer.valueOf(updates.chat_id));
                if (chat == null) {
                    chat = MessagesStorage.getInstance().getChatSync(updates.chat_id);
                    putChat(chat, true);
                }
                missingData = chat == null || user == null || ((needFwdUser && user2 == null && channel == null) || (needBotUser && user3 == null));
            }
            if (!missingData && !updates.entities.isEmpty()) {
                for (a = 0; a < updates.entities.size(); a++) {
                    TLRPC$MessageEntity entity = (TLRPC$MessageEntity) updates.entities.get(a);
                    if (entity instanceof TLRPC$TL_messageEntityMentionName) {
                        int uid = ((TLRPC$TL_messageEntityMentionName) entity).user_id;
                        User entityUser = getUser(Integer.valueOf(uid));
                        if (entityUser == null || entityUser.min) {
                            entityUser = MessagesStorage.getInstance().getUserSync(uid);
                            if (entityUser != null && entityUser.min) {
                                entityUser = null;
                            }
                            if (entityUser == null) {
                                missingData = true;
                                break;
                            }
                            putUser(user, true);
                        }
                    }
                }
            }
            if (!(user == null || user.status == null || user.status.expires > 0)) {
                this.onlinePrivacy.put(Integer.valueOf(user.id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                updateStatus = true;
            }
            if (missingData) {
                needGetDiff = true;
            } else if (MessagesStorage.lastPtsValue + updates.pts_count == updates.pts) {
                TLRPC$Message message = new TLRPC$TL_message();
                message.id = updates.id;
                int clientUserId = UserConfig.getClientUserId();
                if (updates instanceof TLRPC$TL_updateShortMessage) {
                    if (updates.out) {
                        message.from_id = clientUserId;
                    } else {
                        message.from_id = user_id;
                    }
                    message.to_id = new TLRPC$TL_peerUser();
                    message.to_id.user_id = user_id;
                    message.dialog_id = (long) user_id;
                } else {
                    message.from_id = user_id;
                    message.to_id = new TLRPC$TL_peerChat();
                    message.to_id.chat_id = updates.chat_id;
                    message.dialog_id = (long) (-updates.chat_id);
                }
                message.fwd_from = updates.fwd_from;
                message.silent = updates.silent;
                message.out = updates.out;
                message.mentioned = updates.mentioned;
                message.media_unread = updates.media_unread;
                message.entities = updates.entities;
                message.message = updates.message;
                message.date = updates.date;
                message.via_bot_id = updates.via_bot_id;
                message.flags = updates.flags | 256;
                message.reply_to_msg_id = updates.reply_to_msg_id;
                message.media = new TLRPC$TL_messageMediaEmpty();
                ConcurrentHashMap<Long, Integer> read_max = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                Integer value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                    read_max.put(Long.valueOf(message.dialog_id), value);
                }
                message.unread = value.intValue() < message.id;
                if (message.dialog_id == ((long) clientUserId)) {
                    message.unread = false;
                    message.media_unread = false;
                    message.out = true;
                }
                MessagesStorage.lastPtsValue = updates.pts;
                MessageObject messageObject = new MessageObject(message, null, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                ArrayList<MessageObject> objArr = new ArrayList();
                objArr.add(messageObject);
                ArrayList<TLRPC$Message> arr2 = new ArrayList();
                arr2.add(message);
                boolean printUpdate;
                if (updates instanceof TLRPC$TL_updateShortMessage) {
                    printUpdate = !updates.out && updatePrintingUsersWithNewMessages((long) updates.user_id, objArr);
                    if (printUpdate) {
                        updatePrintingStrings();
                    }
                    AndroidUtilities.runOnUIThread(new MessagesController$119(this, printUpdate, user_id, objArr));
                } else {
                    printUpdate = updatePrintingUsersWithNewMessages((long) (-updates.chat_id), objArr);
                    if (printUpdate) {
                        updatePrintingStrings();
                    }
                    AndroidUtilities.runOnUIThread(new MessagesController$120(this, printUpdate, updates, objArr));
                }
                if (!messageObject.isOut()) {
                    MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$121(this, objArr));
                }
                MessagesStorage.getInstance().putMessages(arr2, false, true, false, 0);
            } else if (MessagesStorage.lastPtsValue != updates.pts) {
                FileLog.m92e("need get diff short message, pts: " + MessagesStorage.lastPtsValue + " " + updates.pts + " count = " + updates.pts_count);
                if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500) {
                    if (this.updatesStartWaitTimePts == 0) {
                        this.updatesStartWaitTimePts = System.currentTimeMillis();
                    }
                    FileLog.m92e("add to queue");
                    this.updatesQueuePts.add(updates);
                } else {
                    needGetDiff = true;
                }
            }
        } else if ((updates instanceof TLRPC$TL_updatesCombined) || (updates instanceof TLRPC$TL_updates)) {
            TLRPC$Update update;
            int channelId;
            HashMap<Integer, TLRPC$Chat> minChannels = null;
            for (a = 0; a < updates.chats.size(); a++) {
                chat = (TLRPC$Chat) updates.chats.get(a);
                if ((chat instanceof TLRPC$TL_channel) && chat.min) {
                    TLRPC$Chat existChat = getChat(Integer.valueOf(chat.id));
                    if (existChat == null || existChat.min) {
                        TLRPC$Chat cacheChat = MessagesStorage.getInstance().getChatSync(updates.chat_id);
                        putChat(cacheChat, true);
                        existChat = cacheChat;
                    }
                    if (existChat == null || existChat.min) {
                        if (minChannels == null) {
                            minChannels = new HashMap();
                        }
                        minChannels.put(Integer.valueOf(chat.id), chat);
                    }
                }
            }
            if (minChannels != null) {
                for (a = 0; a < updates.updates.size(); a++) {
                    update = (TLRPC$Update) updates.updates.get(a);
                    if (update instanceof TLRPC$TL_updateNewChannelMessage) {
                        channelId = ((TLRPC$TL_updateNewChannelMessage) update).message.to_id.channel_id;
                        if (minChannels.containsKey(Integer.valueOf(channelId))) {
                            FileLog.m92e("need get diff because of min channel " + channelId);
                            needGetDiff = true;
                            break;
                        }
                    }
                }
            }
            if (!needGetDiff) {
                MessagesStorage.getInstance().putUsersAndChats(updates.users, updates.chats, true, true);
                Collections.sort(updates.updates, this.updatesComparator);
                a = 0;
                while (updates.updates.size() > 0) {
                    update = (TLRPC$Update) updates.updates.get(a);
                    TLRPC$TL_updates updatesNew;
                    int b;
                    TLRPC$Update update2;
                    if (getUpdateType(update) != 0) {
                        if (getUpdateType(update) != 1) {
                            if (getUpdateType(update) != 2) {
                                break;
                            }
                            channelId = getUpdateChannelId(update);
                            boolean skipUpdate = false;
                            Integer channelPts = (Integer) this.channelsPts.get(Integer.valueOf(channelId));
                            if (channelPts == null) {
                                channelPts = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(channelId));
                                if (channelPts.intValue() == 0) {
                                    for (int c = 0; c < updates.chats.size(); c++) {
                                        chat = (TLRPC$Chat) updates.chats.get(c);
                                        if (chat.id == channelId) {
                                            loadUnknownChannel(chat, 0);
                                            skipUpdate = true;
                                            break;
                                        }
                                    }
                                } else {
                                    this.channelsPts.put(Integer.valueOf(channelId), channelPts);
                                }
                            }
                            updatesNew = new TLRPC$TL_updates();
                            updatesNew.updates.add(update);
                            updatesNew.pts = update.pts;
                            updatesNew.pts_count = update.pts_count;
                            for (b = a + 1; b < updates.updates.size(); b = (b - 1) + 1) {
                                update2 = (TLRPC$Update) updates.updates.get(b);
                                if (getUpdateType(update2) != 2 || channelId != getUpdateChannelId(update2) || updatesNew.pts + update2.pts_count != update2.pts) {
                                    break;
                                }
                                updatesNew.updates.add(update2);
                                updatesNew.pts = update2.pts;
                                updatesNew.pts_count += update2.pts_count;
                                updates.updates.remove(b);
                            }
                            if (skipUpdate) {
                                FileLog.m92e("need load unknown channel = " + channelId);
                            } else if (channelPts.intValue() + updatesNew.pts_count == updatesNew.pts) {
                                if (processUpdateArray(updatesNew.updates, updates.users, updates.chats, false)) {
                                    this.channelsPts.put(Integer.valueOf(channelId), Integer.valueOf(updatesNew.pts));
                                    MessagesStorage.getInstance().saveChannelPts(channelId, updatesNew.pts);
                                } else {
                                    FileLog.m92e("need get channel diff inner TL_updates, channel_id = " + channelId);
                                    if (needGetChannelsDiff == null) {
                                        needGetChannelsDiff = new ArrayList();
                                    } else {
                                        if (!needGetChannelsDiff.contains(Integer.valueOf(channelId))) {
                                            needGetChannelsDiff.add(Integer.valueOf(channelId));
                                        }
                                    }
                                }
                            } else if (channelPts.intValue() != updatesNew.pts) {
                                FileLog.m92e(update + " need get channel diff, pts: " + channelPts + " " + updatesNew.pts + " count = " + updatesNew.pts_count + " channelId = " + channelId);
                                Long updatesStartWaitTime = (Long) this.updatesStartWaitTimeChannels.get(Integer.valueOf(channelId));
                                Boolean gettingDifferenceChannel = (Boolean) this.gettingDifferenceChannels.get(Integer.valueOf(channelId));
                                if (gettingDifferenceChannel == null) {
                                    gettingDifferenceChannel = Boolean.valueOf(false);
                                }
                                if (gettingDifferenceChannel.booleanValue() || updatesStartWaitTime == null || Math.abs(System.currentTimeMillis() - updatesStartWaitTime.longValue()) <= 1500) {
                                    if (updatesStartWaitTime == null) {
                                        this.updatesStartWaitTimeChannels.put(Integer.valueOf(channelId), Long.valueOf(System.currentTimeMillis()));
                                    }
                                    FileLog.m92e("add to queue");
                                    ArrayList<TLRPC$Updates> arrayList = (ArrayList) this.updatesQueueChannels.get(Integer.valueOf(channelId));
                                    if (arrayList == null) {
                                        arrayList = new ArrayList();
                                        this.updatesQueueChannels.put(Integer.valueOf(channelId), arrayList);
                                    }
                                    arrayList.add(updatesNew);
                                } else if (needGetChannelsDiff == null) {
                                    needGetChannelsDiff = new ArrayList();
                                } else {
                                    if (!needGetChannelsDiff.contains(Integer.valueOf(channelId))) {
                                        needGetChannelsDiff.add(Integer.valueOf(channelId));
                                    }
                                }
                            }
                        } else {
                            updatesNew = new TLRPC$TL_updates();
                            updatesNew.updates.add(update);
                            updatesNew.pts = update.qts;
                            for (b = a + 1; b < updates.updates.size(); b = (b - 1) + 1) {
                                update2 = (TLRPC$Update) updates.updates.get(b);
                                if (getUpdateType(update2) != 1 || updatesNew.pts + 1 != update2.qts) {
                                    break;
                                }
                                updatesNew.updates.add(update2);
                                updatesNew.pts = update2.qts;
                                updates.updates.remove(b);
                            }
                            if (MessagesStorage.lastQtsValue == 0 || MessagesStorage.lastQtsValue + updatesNew.updates.size() == updatesNew.pts) {
                                processUpdateArray(updatesNew.updates, updates.users, updates.chats, false);
                                MessagesStorage.lastQtsValue = updatesNew.pts;
                                needReceivedQueue = true;
                            } else if (MessagesStorage.lastPtsValue != updatesNew.pts) {
                                FileLog.m92e(update + " need get diff, qts: " + MessagesStorage.lastQtsValue + " " + updatesNew.pts);
                                if (this.gettingDifference || this.updatesStartWaitTimeQts == 0 || (this.updatesStartWaitTimeQts != 0 && Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeQts) <= 1500)) {
                                    if (this.updatesStartWaitTimeQts == 0) {
                                        this.updatesStartWaitTimeQts = System.currentTimeMillis();
                                    }
                                    FileLog.m92e("add to queue");
                                    this.updatesQueueQts.add(updatesNew);
                                } else {
                                    needGetDiff = true;
                                }
                            }
                        }
                    } else {
                        updatesNew = new TLRPC$TL_updates();
                        updatesNew.updates.add(update);
                        updatesNew.pts = update.pts;
                        updatesNew.pts_count = update.pts_count;
                        for (b = a + 1; b < updates.updates.size(); b = (b - 1) + 1) {
                            update2 = (TLRPC$Update) updates.updates.get(b);
                            if (getUpdateType(update2) != 0 || updatesNew.pts + update2.pts_count != update2.pts) {
                                break;
                            }
                            updatesNew.updates.add(update2);
                            updatesNew.pts = update2.pts;
                            updatesNew.pts_count += update2.pts_count;
                            updates.updates.remove(b);
                        }
                        if (MessagesStorage.lastPtsValue + updatesNew.pts_count == updatesNew.pts) {
                            if (processUpdateArray(updatesNew.updates, updates.users, updates.chats, false)) {
                                MessagesStorage.lastPtsValue = updatesNew.pts;
                            } else {
                                FileLog.m92e("need get diff inner TL_updates, seq: " + MessagesStorage.lastSeqValue + " " + updates.seq);
                                needGetDiff = true;
                            }
                        } else if (MessagesStorage.lastPtsValue != updatesNew.pts) {
                            FileLog.m92e(update + " need get diff, pts: " + MessagesStorage.lastPtsValue + " " + updatesNew.pts + " count = " + updatesNew.pts_count);
                            if (this.gettingDifference || this.updatesStartWaitTimePts == 0 || (this.updatesStartWaitTimePts != 0 && Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimePts) <= 1500)) {
                                if (this.updatesStartWaitTimePts == 0) {
                                    this.updatesStartWaitTimePts = System.currentTimeMillis();
                                }
                                FileLog.m92e("add to queue");
                                this.updatesQueuePts.add(updatesNew);
                            } else {
                                needGetDiff = true;
                            }
                        }
                    }
                    updates.updates.remove(a);
                    a = (a - 1) + 1;
                }
                boolean processUpdate = updates instanceof TLRPC$TL_updatesCombined ? MessagesStorage.lastSeqValue + 1 == updates.seq_start || MessagesStorage.lastSeqValue == updates.seq_start : MessagesStorage.lastSeqValue + 1 == updates.seq || updates.seq == 0 || updates.seq == MessagesStorage.lastSeqValue;
                if (processUpdate) {
                    processUpdateArray(updates.updates, updates.users, updates.chats, false);
                    if (updates.seq != 0) {
                        if (updates.date != 0) {
                            MessagesStorage.lastDateValue = updates.date;
                        }
                        MessagesStorage.lastSeqValue = updates.seq;
                    }
                } else {
                    if (updates instanceof TLRPC$TL_updatesCombined) {
                        FileLog.m92e("need get diff TL_updatesCombined, seq: " + MessagesStorage.lastSeqValue + " " + updates.seq_start);
                    } else {
                        FileLog.m92e("need get diff TL_updates, seq: " + MessagesStorage.lastSeqValue + " " + updates.seq);
                    }
                    if (this.gettingDifference || this.updatesStartWaitTimeSeq == 0 || Math.abs(System.currentTimeMillis() - this.updatesStartWaitTimeSeq) <= 1500) {
                        if (this.updatesStartWaitTimeSeq == 0) {
                            this.updatesStartWaitTimeSeq = System.currentTimeMillis();
                        }
                        FileLog.m92e("add TL_updates/Combined to queue");
                        this.updatesQueueSeq.add(updates);
                    } else {
                        needGetDiff = true;
                    }
                }
            }
        } else if (updates instanceof TLRPC$TL_updatesTooLong) {
            FileLog.m92e("need get diff TL_updatesTooLong");
            needGetDiff = true;
        } else if (updates instanceof MessagesController$UserActionUpdatesSeq) {
            MessagesStorage.lastSeqValue = updates.seq;
        } else if (updates instanceof MessagesController$UserActionUpdatesPts) {
            if (updates.chat_id != 0) {
                this.channelsPts.put(Integer.valueOf(updates.chat_id), Integer.valueOf(updates.pts));
                MessagesStorage.getInstance().saveChannelPts(updates.chat_id, updates.pts);
            } else {
                MessagesStorage.lastPtsValue = updates.pts;
            }
        }
        SecretChatHelper.getInstance().processPendingEncMessages();
        if (!fromQueue) {
            ArrayList<Integer> arrayList2 = new ArrayList(this.updatesQueueChannels.keySet());
            for (a = 0; a < arrayList2.size(); a++) {
                Integer key = (Integer) arrayList2.get(a);
                if (needGetChannelsDiff == null || !needGetChannelsDiff.contains(key)) {
                    processChannelsUpdatesQueue(key.intValue(), 0);
                } else {
                    getChannelDifference(key.intValue());
                }
            }
            if (needGetDiff) {
                getDifference();
            } else {
                for (a = 0; a < 3; a++) {
                    processUpdatesQueue(a, 0);
                }
            }
        }
        if (needReceivedQueue) {
            TLObject req = new TLRPC$TL_messages_receivedQueue();
            req.max_qts = MessagesStorage.lastQtsValue;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$122(this));
        }
        if (updateStatus) {
            AndroidUtilities.runOnUIThread(new MessagesController$123(this));
        }
        MessagesStorage.getInstance().saveDiffParams(MessagesStorage.lastSeqValue, MessagesStorage.lastPtsValue, MessagesStorage.lastDateValue, MessagesStorage.lastQtsValue);
    }

    public boolean processUpdateArray(ArrayList<TLRPC$Update> updates, ArrayList<User> usersArr, ArrayList<TLRPC$Chat> chatsArr, boolean fromGetDifference) {
        if (updates.isEmpty()) {
            if (!(usersArr == null && chatsArr == null)) {
                AndroidUtilities.runOnUIThread(new MessagesController$124(this, usersArr, chatsArr));
            }
            return true;
        }
        AbstractMap usersDict;
        int a;
        AbstractMap chatsDict;
        long currentTime = System.currentTimeMillis();
        HashMap<Long, ArrayList<MessageObject>> messages = new HashMap();
        HashMap<Long, TLRPC$WebPage> webPages = new HashMap();
        ArrayList<MessageObject> pushMessages = new ArrayList();
        ArrayList<TLRPC$Message> messagesArr = new ArrayList();
        HashMap<Long, ArrayList<MessageObject>> editingMessages = new HashMap();
        SparseArray<SparseIntArray> channelViews = new SparseArray();
        SparseArray<Long> markAsReadMessagesInbox = new SparseArray();
        SparseArray<Long> markAsReadMessagesOutbox = new SparseArray();
        ArrayList<Long> markAsReadMessages = new ArrayList();
        HashMap<Integer, Integer> markAsReadEncrypted = new HashMap();
        SparseArray<ArrayList<Integer>> deletedMessages = new SparseArray();
        SparseArray<Integer> clearHistoryMessages = new SparseArray();
        boolean printChanged = false;
        ArrayList<TLRPC$ChatParticipants> chatInfoToUpdate = new ArrayList();
        ArrayList<TLRPC$Update> updatesOnMainThread = new ArrayList();
        ArrayList<TLRPC$TL_updateEncryptedMessagesRead> tasks = new ArrayList();
        ArrayList<Integer> contactsIds = new ArrayList();
        boolean checkForUsers = true;
        if (usersArr != null) {
            usersDict = new ConcurrentHashMap();
            for (a = 0; a < usersArr.size(); a++) {
                User user = (User) usersArr.get(a);
                usersDict.put(Integer.valueOf(user.id), user);
            }
        } else {
            checkForUsers = false;
            usersDict = this.users;
        }
        if (chatsArr != null) {
            chatsDict = new ConcurrentHashMap();
            for (a = 0; a < chatsArr.size(); a++) {
                TLRPC$Chat chat = (TLRPC$Chat) chatsArr.get(a);
                chatsDict.put(Integer.valueOf(chat.id), chat);
            }
        } else {
            checkForUsers = false;
            chatsDict = this.chats;
        }
        if (fromGetDifference) {
            checkForUsers = false;
        }
        if (!(usersArr == null && chatsArr == null)) {
            AndroidUtilities.runOnUIThread(new MessagesController$125(this, usersArr, chatsArr));
        }
        int interfaceUpdateMask = 0;
        for (int c = 0; c < updates.size(); c++) {
            Iterator it;
            TLRPC$Update update = (TLRPC$Update) updates.get(c);
            FileLog.m91d("process update " + update);
            TLRPC$Message message;
            int user_id;
            int count;
            TLRPC$MessageEntity entity;
            int clientUserId;
            ConcurrentHashMap<Long, Integer> read_max;
            Integer value;
            MessageObject messageObject;
            ArrayList<MessageObject> arr;
            if ((update instanceof TLRPC$TL_updateNewMessage) || (update instanceof TLRPC$TL_updateNewChannelMessage)) {
                if (update instanceof TLRPC$TL_updateNewMessage) {
                    message = ((TLRPC$TL_updateNewMessage) update).message;
                } else {
                    message = ((TLRPC$TL_updateNewChannelMessage) update).message;
                    if (BuildVars.DEBUG_VERSION) {
                        FileLog.m91d(update + " channelId = " + message.to_id.channel_id);
                    }
                    if (!message.out && message.from_id == UserConfig.getClientUserId()) {
                        message.out = true;
                    }
                }
                chat = null;
                int chat_id = 0;
                user_id = 0;
                if (message.to_id.channel_id != 0) {
                    chat_id = message.to_id.channel_id;
                } else if (message.to_id.chat_id != 0) {
                    chat_id = message.to_id.chat_id;
                } else if (message.to_id.user_id != 0) {
                    user_id = message.to_id.user_id;
                }
                if (chat_id != 0) {
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(chat_id));
                    if (chat == null) {
                        chat = getChat(Integer.valueOf(chat_id));
                    }
                    if (chat == null) {
                        chat = MessagesStorage.getInstance().getChatSync(chat_id);
                        putChat(chat, true);
                    }
                }
                if (checkForUsers) {
                    if (chat_id == 0 || chat != null) {
                        count = message.entities.size() + 3;
                        for (a = 0; a < count; a++) {
                            boolean allowMin = false;
                            if (a != 0) {
                                if (a == 1) {
                                    user_id = message.from_id;
                                    if (message.post) {
                                        allowMin = true;
                                    }
                                } else if (a == 2) {
                                    user_id = message.fwd_from != null ? message.fwd_from.from_id : 0;
                                } else {
                                    entity = (TLRPC$MessageEntity) message.entities.get(a - 3);
                                    user_id = entity instanceof TLRPC$TL_messageEntityMentionName ? ((TLRPC$TL_messageEntityMentionName) entity).user_id : 0;
                                }
                            }
                            if (user_id > 0) {
                                user = (User) usersDict.get(Integer.valueOf(user_id));
                                if (user == null || (!allowMin && user.min)) {
                                    user = getUser(Integer.valueOf(user_id));
                                }
                                if (user == null || (!allowMin && user.min)) {
                                    user = MessagesStorage.getInstance().getUserSync(user_id);
                                    if (!(user == null || allowMin || !user.min)) {
                                        user = null;
                                    }
                                    putUser(user, true);
                                }
                                if (user == null) {
                                    FileLog.m91d("not found user " + user_id);
                                    return false;
                                } else if (a == 1 && user.status != null && user.status.expires <= 0) {
                                    this.onlinePrivacy.put(Integer.valueOf(user_id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                                    interfaceUpdateMask |= 4;
                                }
                            }
                        }
                    } else {
                        FileLog.m91d("not found chat " + chat_id);
                        return false;
                    }
                }
                if (chat != null && chat.megagroup) {
                    message.flags |= Integer.MIN_VALUE;
                }
                if (message.action instanceof TLRPC$TL_messageActionChatDeleteUser) {
                    if (!this.hideLeftGroup || message.action.user_id != message.from_id) {
                        user = (User) usersDict.get(Integer.valueOf(message.action.user_id));
                        if (user != null && user.bot) {
                            message.reply_markup = new TLRPC$TL_replyKeyboardHide();
                            message.flags |= 64;
                        } else if (message.from_id == UserConfig.getClientUserId() && message.action.user_id == UserConfig.getClientUserId()) {
                        }
                    }
                }
                messagesArr.add(message);
                ImageLoader.saveMessageThumbs(message);
                clientUserId = UserConfig.getClientUserId();
                if (message.to_id.chat_id != 0) {
                    message.dialog_id = (long) (-message.to_id.chat_id);
                } else if (message.to_id.channel_id != 0) {
                    message.dialog_id = (long) (-message.to_id.channel_id);
                } else {
                    if (message.to_id.user_id == clientUserId) {
                        message.to_id.user_id = message.from_id;
                    }
                    message.dialog_id = (long) message.to_id.user_id;
                }
                read_max = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                    read_max.put(Long.valueOf(message.dialog_id), value);
                }
                boolean z = value.intValue() < message.id && !((chat != null && ChatObject.isNotInChat(chat)) || (message.action instanceof TLRPC$TL_messageActionChatMigrateTo) || (message.action instanceof TLRPC$TL_messageActionChannelCreate));
                message.unread = z;
                if (message.dialog_id == ((long) clientUserId)) {
                    message.unread = false;
                    message.media_unread = false;
                    message.out = true;
                }
                messageObject = new MessageObject(message, usersDict, chatsDict, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                if (messageObject.type == 11) {
                    interfaceUpdateMask |= 8;
                } else if (messageObject.type == 10) {
                    interfaceUpdateMask |= 16;
                }
                arr = (ArrayList) messages.get(Long.valueOf(message.dialog_id));
                if (arr == null) {
                    arr = new ArrayList();
                    messages.put(Long.valueOf(message.dialog_id), arr);
                }
                arr.add(messageObject);
                if (!messageObject.isOut() && messageObject.isUnread()) {
                    pushMessages.add(messageObject);
                }
            } else if (update instanceof TLRPC$TL_updateReadMessagesContents) {
                for (a = 0; a < update.messages.size(); a++) {
                    markAsReadMessages.add(Long.valueOf((long) ((Integer) update.messages.get(a)).intValue()));
                }
            } else if (update instanceof TLRPC$TL_updateChannelReadMessagesContents) {
                for (a = 0; a < update.messages.size(); a++) {
                    markAsReadMessages.add(Long.valueOf(((long) ((Integer) update.messages.get(a)).intValue()) | (((long) update.channel_id) << 32)));
                }
            } else if ((update instanceof TLRPC$TL_updateReadHistoryInbox) || (update instanceof TLRPC$TL_updateReadHistoryOutbox)) {
                TLRPC$Peer peer;
                if (update instanceof TLRPC$TL_updateReadHistoryInbox) {
                    peer = ((TLRPC$TL_updateReadHistoryInbox) update).peer;
                    if (peer.chat_id != 0) {
                        markAsReadMessagesInbox.put(-peer.chat_id, Long.valueOf((long) update.max_id));
                        dialog_id = (long) (-peer.chat_id);
                    } else {
                        markAsReadMessagesInbox.put(peer.user_id, Long.valueOf((long) update.max_id));
                        dialog_id = (long) peer.user_id;
                    }
                    read_max = this.dialogs_read_inbox_max;
                } else {
                    peer = ((TLRPC$TL_updateReadHistoryOutbox) update).peer;
                    if (peer.chat_id != 0) {
                        markAsReadMessagesOutbox.put(-peer.chat_id, Long.valueOf((long) update.max_id));
                        dialog_id = (long) (-peer.chat_id);
                    } else {
                        markAsReadMessagesOutbox.put(peer.user_id, Long.valueOf((long) update.max_id));
                        dialog_id = (long) peer.user_id;
                    }
                    read_max = this.dialogs_read_outbox_max;
                }
                value = (Integer) read_max.get(Long.valueOf(dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(update instanceof TLRPC$TL_updateReadHistoryOutbox, dialog_id));
                }
                read_max.put(Long.valueOf(dialog_id), Integer.valueOf(Math.max(value.intValue(), update.max_id)));
            } else if (update instanceof TLRPC$TL_updateDeleteMessages) {
                arrayList = (ArrayList) deletedMessages.get(0);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    deletedMessages.put(0, arrayList);
                }
                arrayList.addAll(update.messages);
            } else if ((update instanceof TLRPC$TL_updateUserTyping) || (update instanceof TLRPC$TL_updateChatUserTyping)) {
                if (update.user_id != UserConfig.getClientUserId()) {
                    uid = (long) (-update.chat_id);
                    if (uid == 0) {
                        uid = (long) update.user_id;
                    }
                    arr = (ArrayList) this.printingUsers.get(Long.valueOf(uid));
                    if (!(update.action instanceof TLRPC$TL_sendMessageCancelAction)) {
                        if (arr == null) {
                            arr = new ArrayList();
                            this.printingUsers.put(Long.valueOf(uid), arr);
                        }
                        exist = false;
                        it = arr.iterator();
                        while (it.hasNext()) {
                            u = (MessagesController$PrintingUser) it.next();
                            if (u.userId == update.user_id) {
                                exist = true;
                                u.lastTime = currentTime;
                                if (u.action.getClass() != update.action.getClass()) {
                                    printChanged = true;
                                }
                                u.action = update.action;
                                if (!exist) {
                                    newUser = new MessagesController$PrintingUser();
                                    newUser.userId = update.user_id;
                                    newUser.lastTime = currentTime;
                                    newUser.action = update.action;
                                    arr.add(newUser);
                                    printChanged = true;
                                }
                            }
                        }
                        if (exist) {
                            newUser = new MessagesController$PrintingUser();
                            newUser.userId = update.user_id;
                            newUser.lastTime = currentTime;
                            newUser.action = update.action;
                            arr.add(newUser);
                            printChanged = true;
                        }
                    } else if (arr != null) {
                        for (a = 0; a < arr.size(); a++) {
                            if (((MessagesController$PrintingUser) arr.get(a)).userId == update.user_id) {
                                arr.remove(a);
                                printChanged = true;
                                break;
                            }
                        }
                        if (arr.isEmpty()) {
                            this.printingUsers.remove(Long.valueOf(uid));
                        }
                    }
                    this.onlinePrivacy.put(Integer.valueOf(update.user_id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                }
            } else if (update instanceof TLRPC$TL_updateChatParticipants) {
                interfaceUpdateMask |= 32;
                chatInfoToUpdate.add(update.participants);
            } else if (update instanceof TLRPC$TL_updateUserStatus) {
                interfaceUpdateMask |= 4;
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateUserName) {
                try {
                    AppApplication.getDatabaseHandler().addContactChangeLog(new ContactChangeLog((long) update.user_id, 2, getInstance().getUser(Integer.valueOf(update.user_id)).username, (long) ((int) (System.currentTimeMillis() / 1000))));
                } catch (Exception e) {
                }
                interfaceUpdateMask |= 1;
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateUserPhoto) {
                try {
                    AppApplication.getDatabaseHandler().addContactChangeLog(new ContactChangeLog((long) update.user_id, 1, update.photo.photo_id + "", (long) update.date));
                } catch (Exception e2) {
                }
                interfaceUpdateMask |= 2;
                MessagesStorage.getInstance().clearUserPhotos(update.user_id);
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateUserPhone) {
                AppApplication.getDatabaseHandler().addContactChangeLog(new ContactChangeLog((long) update.user_id, 3, getInstance().getUser(Integer.valueOf(update.user_id)).phone, (long) update.date));
                interfaceUpdateMask |= 1024;
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateContactRegistered) {
                if (this.enableJoined) {
                    if (usersDict.containsKey(Integer.valueOf(update.user_id)) && !MessagesStorage.getInstance().isDialogHasMessages((long) update.user_id)) {
                        newMessage = new TLRPC$TL_messageService();
                        newMessage.action = new TLRPC$TL_messageActionUserJoined();
                        r4 = UserConfig.getNewMessageId();
                        newMessage.id = r4;
                        newMessage.local_id = r4;
                        UserConfig.saveConfig(false);
                        newMessage.unread = false;
                        newMessage.flags = 256;
                        newMessage.date = update.date;
                        newMessage.from_id = update.user_id;
                        newMessage.to_id = new TLRPC$TL_peerUser();
                        newMessage.to_id.user_id = UserConfig.getClientUserId();
                        newMessage.dialog_id = (long) update.user_id;
                        messagesArr.add(newMessage);
                        messageObject = new MessageObject(newMessage, usersDict, chatsDict, this.createdDialogIds.contains(Long.valueOf(newMessage.dialog_id)));
                        arr = (ArrayList) messages.get(Long.valueOf(newMessage.dialog_id));
                        if (arr == null) {
                            arr = new ArrayList();
                            messages.put(Long.valueOf(newMessage.dialog_id), arr);
                        }
                        arr.add(messageObject);
                    }
                }
            } else if (update instanceof TLRPC$TL_updateContactLink) {
                int idx;
                if (update.my_link instanceof TLRPC$TL_contactLinkContact) {
                    idx = contactsIds.indexOf(Integer.valueOf(-update.user_id));
                    if (idx != -1) {
                        contactsIds.remove(idx);
                    }
                    if (!contactsIds.contains(Integer.valueOf(update.user_id))) {
                        contactsIds.add(Integer.valueOf(update.user_id));
                    }
                } else {
                    idx = contactsIds.indexOf(Integer.valueOf(update.user_id));
                    if (idx != -1) {
                        contactsIds.remove(idx);
                    }
                    if (!contactsIds.contains(Integer.valueOf(update.user_id))) {
                        contactsIds.add(Integer.valueOf(-update.user_id));
                    }
                }
            } else if (update instanceof TLRPC$TL_updateNewGeoChatMessage) {
                continue;
            } else if (update instanceof TLRPC$TL_updateNewEncryptedMessage) {
                ArrayList<TLRPC$Message> decryptedMessages = SecretChatHelper.getInstance().decryptMessage(((TLRPC$TL_updateNewEncryptedMessage) update).message);
                if (!(decryptedMessages == null || decryptedMessages.isEmpty())) {
                    uid = ((long) ((TLRPC$TL_updateNewEncryptedMessage) update).message.chat_id) << 32;
                    arr = (ArrayList) messages.get(Long.valueOf(uid));
                    if (arr == null) {
                        arr = new ArrayList();
                        messages.put(Long.valueOf(uid), arr);
                    }
                    for (a = 0; a < decryptedMessages.size(); a++) {
                        message = (TLRPC$Message) decryptedMessages.get(a);
                        ImageLoader.saveMessageThumbs(message);
                        messagesArr.add(message);
                        messageObject = new MessageObject(message, usersDict, chatsDict, this.createdDialogIds.contains(Long.valueOf(uid)));
                        arr.add(messageObject);
                        pushMessages.add(messageObject);
                    }
                }
            } else if (update instanceof TLRPC$TL_updateEncryptedChatTyping) {
                TLRPC$EncryptedChat encryptedChat = getEncryptedChatDB(update.chat_id, true);
                if (encryptedChat != null) {
                    update.user_id = encryptedChat.user_id;
                    uid = ((long) update.chat_id) << 32;
                    arr = (ArrayList) this.printingUsers.get(Long.valueOf(uid));
                    if (arr == null) {
                        arr = new ArrayList();
                        this.printingUsers.put(Long.valueOf(uid), arr);
                    }
                    exist = false;
                    it = arr.iterator();
                    while (it.hasNext()) {
                        u = (MessagesController$PrintingUser) it.next();
                        if (u.userId == update.user_id) {
                            exist = true;
                            u.lastTime = currentTime;
                            u.action = new TLRPC$TL_sendMessageTypingAction();
                            break;
                        }
                    }
                    if (!exist) {
                        newUser = new MessagesController$PrintingUser();
                        newUser.userId = update.user_id;
                        newUser.lastTime = currentTime;
                        newUser.action = new TLRPC$TL_sendMessageTypingAction();
                        arr.add(newUser);
                        printChanged = true;
                    }
                    this.onlinePrivacy.put(Integer.valueOf(update.user_id), Integer.valueOf(ConnectionsManager.getInstance().getCurrentTime()));
                }
            } else if (update instanceof TLRPC$TL_updateEncryptedMessagesRead) {
                markAsReadEncrypted.put(Integer.valueOf(update.chat_id), Integer.valueOf(Math.max(update.max_date, update.date)));
                tasks.add((TLRPC$TL_updateEncryptedMessagesRead) update);
            } else if (update instanceof TLRPC$TL_updateChatParticipantAdd) {
                MessagesStorage.getInstance().updateChatInfo(update.chat_id, update.user_id, 0, update.inviter_id, update.version);
            } else if (update instanceof TLRPC$TL_updateChatParticipantDelete) {
                MessagesStorage.getInstance().updateChatInfo(update.chat_id, update.user_id, 1, 0, update.version);
            } else if ((update instanceof TLRPC$TL_updateDcOptions) || (update instanceof TLRPC$TL_updateConfig)) {
                ConnectionsManager.getInstance().updateDcSettings();
            } else if (update instanceof TLRPC$TL_updateEncryption) {
                SecretChatHelper.getInstance().processUpdateEncryption((TLRPC$TL_updateEncryption) update, usersDict);
            } else if (update instanceof TLRPC$TL_updateUserBlocked) {
                TLRPC$TL_updateUserBlocked finalUpdate = (TLRPC$TL_updateUserBlocked) update;
                if (finalUpdate.blocked) {
                    ArrayList<Integer> ids = new ArrayList();
                    ids.add(Integer.valueOf(finalUpdate.user_id));
                    MessagesStorage.getInstance().putBlockedUsers(ids, false);
                } else {
                    MessagesStorage.getInstance().deleteBlockedUser(finalUpdate.user_id);
                }
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$126(this, finalUpdate));
            } else if (update instanceof TLRPC$TL_updateNotifySettings) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateServiceNotification) {
                TLRPC$TL_updateServiceNotification notification = (TLRPC$TL_updateServiceNotification) update;
                if (notification.popup && notification.message != null && notification.message.length() > 0) {
                    AndroidUtilities.runOnUIThread(new MessagesController$127(this, notification));
                }
                if ((notification.flags & 2) != 0) {
                    newMessage = new TLRPC$TL_message();
                    r4 = UserConfig.getNewMessageId();
                    newMessage.id = r4;
                    newMessage.local_id = r4;
                    UserConfig.saveConfig(false);
                    newMessage.unread = true;
                    newMessage.flags = 256;
                    if (notification.inbox_date != 0) {
                        newMessage.date = notification.inbox_date;
                    } else {
                        newMessage.date = (int) (System.currentTimeMillis() / 1000);
                    }
                    newMessage.from_id = 777000;
                    newMessage.to_id = new TLRPC$TL_peerUser();
                    newMessage.to_id.user_id = UserConfig.getClientUserId();
                    newMessage.dialog_id = 777000;
                    if (update.media != null) {
                        newMessage.media = update.media;
                        newMessage.flags |= 512;
                    }
                    newMessage.message = notification.message;
                    if (notification.entities != null) {
                        newMessage.entities = notification.entities;
                    }
                    messagesArr.add(newMessage);
                    messageObject = new MessageObject(newMessage, usersDict, chatsDict, this.createdDialogIds.contains(Long.valueOf(newMessage.dialog_id)));
                    arr = (ArrayList) messages.get(Long.valueOf(newMessage.dialog_id));
                    if (arr == null) {
                        arr = new ArrayList();
                        messages.put(Long.valueOf(newMessage.dialog_id), arr);
                    }
                    arr.add(messageObject);
                    pushMessages.add(messageObject);
                }
            } else if (update instanceof TLRPC$TL_updateDialogPinned) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updatePinnedDialogs) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updatePrivacy) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateWebPage) {
                webPages.put(Long.valueOf(update.webpage.id), update.webpage);
            } else if (update instanceof TLRPC$TL_updateChannelWebPage) {
                webPages.put(Long.valueOf(update.webpage.id), update.webpage);
            } else if (update instanceof TLRPC$TL_updateChannelTooLong) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m91d(update + " channelId = " + update.channel_id);
                }
                Integer channelPts = (Integer) this.channelsPts.get(Integer.valueOf(update.channel_id));
                if (channelPts == null) {
                    channelPts = Integer.valueOf(MessagesStorage.getInstance().getChannelPtsSync(update.channel_id));
                    if (channelPts.intValue() == 0) {
                        chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(update.channel_id));
                        if (chat == null || chat.min) {
                            chat = getChat(Integer.valueOf(update.channel_id));
                        }
                        if (chat == null || chat.min) {
                            chat = MessagesStorage.getInstance().getChatSync(update.channel_id);
                            putChat(chat, true);
                        }
                        if (!(chat == null || chat.min)) {
                            loadUnknownChannel(chat, 0);
                        }
                    } else {
                        this.channelsPts.put(Integer.valueOf(update.channel_id), channelPts);
                    }
                }
                if (channelPts.intValue() != 0) {
                    if ((update.flags & 1) == 0) {
                        getChannelDifference(update.channel_id);
                    } else if (update.pts > channelPts.intValue()) {
                        getChannelDifference(update.channel_id);
                    }
                }
            } else if ((update instanceof TLRPC$TL_updateReadChannelInbox) || (update instanceof TLRPC$TL_updateReadChannelOutbox)) {
                long message_id = ((long) update.max_id) | (((long) update.channel_id) << 32);
                dialog_id = (long) (-update.channel_id);
                if (update instanceof TLRPC$TL_updateReadChannelInbox) {
                    read_max = this.dialogs_read_inbox_max;
                    markAsReadMessagesInbox.put(-update.channel_id, Long.valueOf(message_id));
                } else {
                    read_max = this.dialogs_read_outbox_max;
                    markAsReadMessagesOutbox.put(-update.channel_id, Long.valueOf(message_id));
                }
                value = (Integer) read_max.get(Long.valueOf(dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(update instanceof TLRPC$TL_updateReadChannelOutbox, dialog_id));
                }
                read_max.put(Long.valueOf(dialog_id), Integer.valueOf(Math.max(value.intValue(), update.max_id)));
            } else if (update instanceof TLRPC$TL_updateDeleteChannelMessages) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m91d(update + " channelId = " + update.channel_id);
                }
                arrayList = (ArrayList) deletedMessages.get(update.channel_id);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    deletedMessages.put(update.channel_id, arrayList);
                }
                arrayList.addAll(update.messages);
            } else if (update instanceof TLRPC$TL_updateChannel) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m91d(update + " channelId = " + update.channel_id);
                }
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateChannelMessageViews) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m91d(update + " channelId = " + update.channel_id);
                }
                TLRPC$TL_updateChannelMessageViews updateChannelMessageViews = (TLRPC$TL_updateChannelMessageViews) update;
                SparseIntArray array = (SparseIntArray) channelViews.get(update.channel_id);
                if (array == null) {
                    array = new SparseIntArray();
                    channelViews.put(update.channel_id, array);
                }
                array.put(updateChannelMessageViews.id, update.views);
            } else if (update instanceof TLRPC$TL_updateChatParticipantAdmin) {
                MessagesStorage.getInstance().updateChatInfo(update.chat_id, update.user_id, 2, update.is_admin ? 1 : 0, update.version);
            } else if (update instanceof TLRPC$TL_updateChatAdmins) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateStickerSets) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateStickerSetsOrder) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateNewStickerSet) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateDraftMessage) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateSavedGifs) {
                updatesOnMainThread.add(update);
            } else if ((update instanceof TLRPC$TL_updateEditChannelMessage) || (update instanceof TLRPC$TL_updateEditMessage)) {
                clientUserId = UserConfig.getClientUserId();
                if (update instanceof TLRPC$TL_updateEditChannelMessage) {
                    message = ((TLRPC$TL_updateEditChannelMessage) update).message;
                    chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(message.to_id.channel_id));
                    if (chat == null) {
                        chat = getChat(Integer.valueOf(message.to_id.channel_id));
                    }
                    if (chat == null) {
                        chat = MessagesStorage.getInstance().getChatSync(message.to_id.channel_id);
                        putChat(chat, true);
                    }
                    if (chat != null && chat.megagroup) {
                        message.flags |= Integer.MIN_VALUE;
                    }
                } else {
                    message = ((TLRPC$TL_updateEditMessage) update).message;
                    if (message.dialog_id == ((long) clientUserId)) {
                        message.unread = false;
                        message.media_unread = false;
                        message.out = true;
                    }
                }
                if (!message.out && message.from_id == UserConfig.getClientUserId()) {
                    message.out = true;
                }
                if (!fromGetDifference) {
                    count = message.entities.size();
                    for (a = 0; a < count; a++) {
                        entity = (TLRPC$MessageEntity) message.entities.get(a);
                        if (entity instanceof TLRPC$TL_messageEntityMentionName) {
                            user_id = ((TLRPC$TL_messageEntityMentionName) entity).user_id;
                            user = (User) usersDict.get(Integer.valueOf(user_id));
                            if (user == null || user.min) {
                                user = getUser(Integer.valueOf(user_id));
                            }
                            if (user == null || user.min) {
                                user = MessagesStorage.getInstance().getUserSync(user_id);
                                if (user != null && user.min) {
                                    user = null;
                                }
                                putUser(user, true);
                            }
                            if (user == null) {
                                return false;
                            }
                        }
                    }
                }
                if (message.to_id.chat_id != 0) {
                    message.dialog_id = (long) (-message.to_id.chat_id);
                } else if (message.to_id.channel_id != 0) {
                    message.dialog_id = (long) (-message.to_id.channel_id);
                } else {
                    if (message.to_id.user_id == UserConfig.getClientUserId()) {
                        message.to_id.user_id = message.from_id;
                    }
                    message.dialog_id = (long) message.to_id.user_id;
                }
                read_max = message.out ? this.dialogs_read_outbox_max : this.dialogs_read_inbox_max;
                value = (Integer) read_max.get(Long.valueOf(message.dialog_id));
                if (value == null) {
                    value = Integer.valueOf(MessagesStorage.getInstance().getDialogReadMax(message.out, message.dialog_id));
                    read_max.put(Long.valueOf(message.dialog_id), value);
                }
                message.unread = value.intValue() < message.id;
                if (message.dialog_id == ((long) clientUserId)) {
                    message.out = true;
                    message.unread = false;
                    message.media_unread = false;
                }
                if (message.out && (message.message == null || message.message.length() == 0)) {
                    message.message = "-1";
                    message.attachPath = "";
                }
                ImageLoader.saveMessageThumbs(message);
                messageObject = new MessageObject(message, usersDict, chatsDict, this.createdDialogIds.contains(Long.valueOf(message.dialog_id)));
                arr = (ArrayList) editingMessages.get(Long.valueOf(message.dialog_id));
                if (arr == null) {
                    arr = new ArrayList();
                    editingMessages.put(Long.valueOf(message.dialog_id), arr);
                }
                arr.add(messageObject);
            } else if (update instanceof TLRPC$TL_updateChannelPinnedMessage) {
                if (BuildVars.DEBUG_VERSION) {
                    FileLog.m91d(update + " channelId = " + update.channel_id);
                }
                MessagesStorage.getInstance().updateChannelPinnedMessage(update.channel_id, ((TLRPC$TL_updateChannelPinnedMessage) update).id);
            } else if (update instanceof TLRPC$TL_updateReadFeaturedStickers) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updatePhoneCall) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateLangPack) {
                LocaleController.getInstance().saveRemoteLocaleStrings(update.difference);
            } else if (update instanceof TLRPC$TL_updateLangPackTooLong) {
                LocaleController.getInstance().reloadCurrentRemoteLocale();
            } else if (update instanceof TLRPC$TL_updateFavedStickers) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateContactsReset) {
                updatesOnMainThread.add(update);
            } else if (update instanceof TLRPC$TL_updateChannelAvailableMessages) {
                Integer currentValue = (Integer) clearHistoryMessages.get(update.channel_id);
                if (currentValue == null || currentValue.intValue() < update.available_min_id) {
                    clearHistoryMessages.put(update.channel_id, Integer.valueOf(update.available_min_id));
                }
            }
        }
        if (!messages.isEmpty()) {
            for (Entry<Long, ArrayList<MessageObject>> pair : messages.entrySet()) {
                if (updatePrintingUsersWithNewMessages(((Long) pair.getKey()).longValue(), (ArrayList) pair.getValue())) {
                    printChanged = true;
                }
            }
        }
        if (printChanged) {
            updatePrintingStrings();
        }
        int interfaceUpdateMaskFinal = interfaceUpdateMask;
        boolean printChangedArg = printChanged;
        if (!contactsIds.isEmpty()) {
            ContactsController.getInstance().processContactsUpdates(contactsIds, usersDict);
        }
        if (!pushMessages.isEmpty()) {
            MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$128(this, pushMessages));
        }
        if (!messagesArr.isEmpty()) {
            StatsController.getInstance().incrementReceivedItemsCount(ConnectionsManager.getCurrentNetworkType(), 1, messagesArr.size());
            MessagesStorage.getInstance().putMessages(messagesArr, true, true, false, MediaController.getInstance().getAutodownloadMask());
        }
        if (!editingMessages.isEmpty()) {
            for (Entry<Long, ArrayList<MessageObject>> pair2 : editingMessages.entrySet()) {
                TLRPC$TL_messages_messages messagesRes = new TLRPC$TL_messages_messages();
                ArrayList<MessageObject> messageObjects = (ArrayList) pair2.getValue();
                for (a = 0; a < messageObjects.size(); a++) {
                    messagesRes.messages.add(((MessageObject) messageObjects.get(a)).messageOwner);
                }
                MessagesStorage.getInstance().putMessages(messagesRes, ((Long) pair2.getKey()).longValue(), -2, 0, false);
            }
        }
        if (channelViews.size() != 0) {
            MessagesStorage.getInstance().putChannelViews(channelViews, true);
        }
        AndroidUtilities.runOnUIThread(new MessagesController$129(this, interfaceUpdateMaskFinal, updatesOnMainThread, webPages, messages, editingMessages, printChangedArg, contactsIds, chatInfoToUpdate, channelViews));
        MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$130(this, markAsReadMessagesInbox, markAsReadMessagesOutbox, markAsReadEncrypted, markAsReadMessages, deletedMessages, clearHistoryMessages));
        if (!webPages.isEmpty()) {
            MessagesStorage.getInstance().putWebPages(webPages);
        }
        if (!(markAsReadMessagesInbox.size() == 0 && markAsReadMessagesOutbox.size() == 0 && markAsReadEncrypted.isEmpty() && markAsReadMessages.isEmpty())) {
            if (!(markAsReadMessagesInbox.size() == 0 && markAsReadMessages.isEmpty())) {
                MessagesStorage.getInstance().updateDialogsWithReadMessages(markAsReadMessagesInbox, markAsReadMessagesOutbox, markAsReadMessages, true);
            }
            MessagesStorage.getInstance().markMessagesAsRead(markAsReadMessagesInbox, markAsReadMessagesOutbox, markAsReadEncrypted, true);
        }
        if (!markAsReadMessages.isEmpty()) {
            MessagesStorage.getInstance().markMessagesContentAsRead(markAsReadMessages, ConnectionsManager.getInstance().getCurrentTime());
        }
        if (deletedMessages.size() != 0) {
            for (a = 0; a < deletedMessages.size(); a++) {
                int key = deletedMessages.keyAt(a);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$131(this, (ArrayList) deletedMessages.get(key), key));
            }
        }
        if (clearHistoryMessages.size() != 0) {
            for (a = 0; a < clearHistoryMessages.size(); a++) {
                key = clearHistoryMessages.keyAt(a);
                MessagesStorage.getInstance().getStorageQueue().postRunnable(new MessagesController$132(this, key, (Integer) clearHistoryMessages.get(key)));
            }
        }
        if (!tasks.isEmpty()) {
            for (a = 0; a < tasks.size(); a++) {
                TLRPC$TL_updateEncryptedMessagesRead update2 = (TLRPC$TL_updateEncryptedMessagesRead) tasks.get(a);
                MessagesStorage.getInstance().createTaskForSecretChat(update2.chat_id, update2.max_date, update2.date, 1, null);
            }
        }
        return true;
    }

    private boolean isNotifySettingsMuted(TLRPC$PeerNotifySettings settings) {
        return (settings instanceof TLRPC$TL_peerNotifySettings) && settings.mute_until > ConnectionsManager.getInstance().getCurrentTime();
    }

    public boolean isDialogMuted(long dialog_id) {
        SharedPreferences preferences = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0);
        int mute_type = preferences.getInt("notify2_" + dialog_id, 0);
        if (mute_type == 2) {
            return true;
        }
        if (mute_type != 3 || preferences.getInt("notifyuntil_" + dialog_id, 0) < ConnectionsManager.getInstance().getCurrentTime()) {
            return false;
        }
        return true;
    }

    private boolean updatePrintingUsersWithNewMessages(long uid, ArrayList<MessageObject> messages) {
        if (uid > 0) {
            if (((ArrayList) this.printingUsers.get(Long.valueOf(uid))) != null) {
                this.printingUsers.remove(Long.valueOf(uid));
                return true;
            }
        } else if (uid < 0) {
            ArrayList<Integer> messagesUsers = new ArrayList();
            Iterator it = messages.iterator();
            while (it.hasNext()) {
                MessageObject message = (MessageObject) it.next();
                if (!messagesUsers.contains(Integer.valueOf(message.messageOwner.from_id))) {
                    messagesUsers.add(Integer.valueOf(message.messageOwner.from_id));
                }
            }
            ArrayList<MessagesController$PrintingUser> arr = (ArrayList) this.printingUsers.get(Long.valueOf(uid));
            boolean changed = false;
            if (arr != null) {
                int a = 0;
                while (a < arr.size()) {
                    if (messagesUsers.contains(Integer.valueOf(((MessagesController$PrintingUser) arr.get(a)).userId))) {
                        arr.remove(a);
                        a--;
                        if (arr.isEmpty()) {
                            this.printingUsers.remove(Long.valueOf(uid));
                        }
                        changed = true;
                    }
                    a++;
                }
            }
            if (changed) {
                return true;
            }
        }
        return false;
    }

    protected void updateInterfaceWithMessages(long uid, ArrayList<MessageObject> messages) {
        updateInterfaceWithMessages(uid, messages, false);
    }

    protected void updateInterfaceWithMessages(long uid, ArrayList<MessageObject> messages, boolean isBroadcast) {
        if (messages != null && !messages.isEmpty()) {
            boolean isEncryptedChat = ((int) uid) == 0;
            MessageObject lastMessage = null;
            int channelId = 0;
            boolean updateRating = false;
            for (int a = 0; a < messages.size(); a++) {
                MessageObject message = (MessageObject) messages.get(a);
                if (lastMessage == null || ((!isEncryptedChat && message.getId() > lastMessage.getId()) || (((isEncryptedChat || (message.getId() < 0 && lastMessage.getId() < 0)) && message.getId() < lastMessage.getId()) || message.messageOwner.date > lastMessage.messageOwner.date))) {
                    lastMessage = message;
                    if (message.messageOwner.to_id.channel_id != 0) {
                        channelId = message.messageOwner.to_id.channel_id;
                    }
                }
                if (!(!message.isOut() || message.isSending() || message.isForwarded())) {
                    if (message.isNewGif()) {
                        StickersQuery.addRecentGif(message.messageOwner.media.document, message.messageOwner.date);
                    } else if (message.isSticker()) {
                        StickersQuery.addRecentSticker(0, message.messageOwner.media.document, message.messageOwner.date, false);
                    }
                }
                if (message.isOut() && message.isSent()) {
                    updateRating = true;
                }
            }
            MessagesQuery.loadReplyMessagesForMessages(messages, uid);
            NotificationCenter.getInstance().postNotificationName(NotificationCenter.didReceivedNewMessages, Long.valueOf(uid), messages);
            if (lastMessage != null) {
                TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) this.dialogs_dict.get(Long.valueOf(uid));
                MessageObject object;
                if (!(lastMessage.messageOwner.action instanceof TLRPC$TL_messageActionChatMigrateTo)) {
                    boolean changed = false;
                    if (dialog == null) {
                        if (!isBroadcast) {
                            TLRPC$Chat chat = getChat(Integer.valueOf(channelId));
                            if (channelId != 0 && chat == null) {
                                return;
                            }
                            if (chat == null || !chat.left) {
                                dialog = new TLRPC$TL_dialog();
                                dialog.id = uid;
                                dialog.unread_count = 0;
                                dialog.top_message = lastMessage.getId();
                                dialog.last_message_date = lastMessage.messageOwner.date;
                                dialog.flags = ChatObject.isChannel(chat) ? 1 : 0;
                                this.dialogs_dict.put(Long.valueOf(uid), dialog);
                                this.dialogs.add(dialog);
                                this.dialogMessage.put(Long.valueOf(uid), lastMessage);
                                if (lastMessage.messageOwner.to_id.channel_id == 0) {
                                    this.dialogMessagesByIds.put(Integer.valueOf(lastMessage.getId()), lastMessage);
                                    if (lastMessage.messageOwner.random_id != 0) {
                                        this.dialogMessagesByRandomIds.put(Long.valueOf(lastMessage.messageOwner.random_id), lastMessage);
                                    }
                                }
                                this.nextDialogsCacheOffset++;
                                changed = true;
                            } else {
                                return;
                            }
                        }
                    } else if ((dialog.top_message > 0 && lastMessage.getId() > 0 && lastMessage.getId() > dialog.top_message) || ((dialog.top_message < 0 && lastMessage.getId() < 0 && lastMessage.getId() < dialog.top_message) || !this.dialogMessage.containsKey(Long.valueOf(uid)) || dialog.top_message < 0 || dialog.last_message_date <= lastMessage.messageOwner.date)) {
                        object = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(dialog.top_message));
                        if (!(object == null || object.messageOwner.random_id == 0)) {
                            this.dialogMessagesByRandomIds.remove(Long.valueOf(object.messageOwner.random_id));
                        }
                        dialog.top_message = lastMessage.getId();
                        if (!isBroadcast) {
                            dialog.last_message_date = lastMessage.messageOwner.date;
                            changed = true;
                        }
                        this.dialogMessage.put(Long.valueOf(uid), lastMessage);
                        if (lastMessage.messageOwner.to_id.channel_id == 0) {
                            this.dialogMessagesByIds.put(Integer.valueOf(lastMessage.getId()), lastMessage);
                            if (lastMessage.messageOwner.random_id != 0) {
                                this.dialogMessagesByRandomIds.put(Long.valueOf(lastMessage.messageOwner.random_id), lastMessage);
                            }
                        }
                    }
                    if (changed) {
                        sortDialogs(null);
                    }
                    if (updateRating) {
                        SearchQuery.increasePeerRaiting(uid);
                    }
                } else if (dialog != null) {
                    this.dialogs.remove(dialog);
                    this.dialogsServerOnly.remove(dialog);
                    this.dialogsGroupsOnly.remove(dialog);
                    this.dialogs_dict.remove(Long.valueOf(dialog.id));
                    this.dialogs_read_inbox_max.remove(Long.valueOf(dialog.id));
                    this.dialogs_read_outbox_max.remove(Long.valueOf(dialog.id));
                    this.nextDialogsCacheOffset--;
                    this.dialogMessage.remove(Long.valueOf(dialog.id));
                    object = (MessageObject) this.dialogMessagesByIds.remove(Integer.valueOf(dialog.top_message));
                    if (!(object == null || object.messageOwner.random_id == 0)) {
                        this.dialogMessagesByRandomIds.remove(Long.valueOf(object.messageOwner.random_id));
                    }
                    dialog.top_message = 0;
                    NotificationsController.getInstance().removeNotificationsForDialog(dialog.id);
                    NotificationCenter.getInstance().postNotificationName(NotificationCenter.needReloadRecentDialogsSearch, new Object[0]);
                }
            }
        }
    }

    public void sortDialogs(HashMap<Integer, TLRPC$Chat> chatsDict) {
        long startT = System.currentTimeMillis();
        this.dialogsServerOnly.clear();
        this.dialogsGroupsOnly.clear();
        this.dialogsForward.clear();
        boolean selfAdded = false;
        int selfId = UserConfig.getClientUserId();
        this.dialogsUsers.clear();
        this.dialogsGroups.clear();
        this.dialogsGroupsAll.clear();
        this.dialogsChannels.clear();
        this.dialogsMegaGroups.clear();
        this.dialogsBots.clear();
        this.dialogsFavs.clear();
        this.dialogsHidden.clear();
        this.dialogsAll.clear();
        this.dialogsUnread.clear();
        this.dialogsAds.clear();
        int type = AppPreferences.getSubtitleType(ApplicationLoader.applicationContext);
        this.hiddensIds = AppPreferences.getHiddenList(ApplicationLoader.applicationContext);
        this.categoriesIds = AppPreferences.getAdsChannelIds(ApplicationLoader.applicationContext);
        boolean isShowHiddenDialogs = AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext);
        ArrayList<Long> favouritesIds = Favourite.getFavouriteIds();
        Collections.sort(this.dialogs, this.dialogComparator);
        int a = 0;
        while (a < this.dialogs.size()) {
            User user;
            TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.dialogs.get(a);
            if ((type != 2 || d.unread_count > 0) && (!(type == 4 && getInstance().isDialogMuted(d.id)) && (type != 3 || getInstance().isDialogMuted(d.id)))) {
                TLRPC$Chat chat;
                boolean isDialogHidden = this.hiddensIds.contains(Long.valueOf(d.id));
                if (isDialogHidden) {
                    if (isShowHiddenDialogs) {
                        this.dialogsHidden.add(d);
                    }
                }
                if (isShowHiddenDialogs || !isDialogHidden) {
                    this.dialogsAll.add(d);
                }
                if (d.unread_count > 0) {
                    this.dialogsUnread.add(d);
                }
                int high_id = (int) (d.id >> 32);
                int lower_id = (int) d.id;
                if (lower_id == selfId) {
                    this.dialogsForward.add(0, d);
                    selfAdded = true;
                } else {
                    this.dialogsForward.add(d);
                }
                if (!(lower_id == 0 || high_id == 1)) {
                    this.dialogsServerOnly.add(d);
                    if (DialogObject.isChannel(d)) {
                        chat = getChat(Integer.valueOf(-lower_id));
                        if (chat != null && ((chat.megagroup && chat.admin_rights != null && chat.admin_rights.post_messages) || chat.creator)) {
                            this.dialogsGroupsOnly.add(d);
                        }
                    } else if (lower_id < 0) {
                        if (chatsDict != null) {
                            chat = (TLRPC$Chat) chatsDict.get(Integer.valueOf(-lower_id));
                            if (!(chat == null || chat.migrated_to == null)) {
                                this.dialogs.remove(a);
                                a--;
                            }
                        }
                        this.dialogsGroupsOnly.add(d);
                    }
                }
                if (((int) d.id) != 0 && high_id != 1 && (d instanceof TLRPC$TL_dialog)) {
                    if (d.id < 0) {
                        chat = getChat(Integer.valueOf(-((int) d.id)));
                        if (chat != null && chat.megagroup && chat.creator) {
                            this.dialogsGroupsOnly.add(d);
                        }
                        if (chat != null) {
                            if (chat.megagroup) {
                                this.dialogsMegaGroups.add(d);
                                this.dialogsGroupsAll.add(d);
                            } else if (ChatObject.isChannel(chat)) {
                                this.dialogsChannels.add(d);
                                Iterator it = this.categoriesIds.iterator();
                                while (it.hasNext()) {
                                    if (((long) ((Integer) it.next()).intValue()) == Math.abs(d.id)) {
                                        this.dialogsAds.add(d);
                                        break;
                                    }
                                }
                            } else {
                                this.dialogsGroups.add(d);
                                this.dialogsGroupsAll.add(d);
                            }
                        }
                    } else {
                        user = getUser(Integer.valueOf((int) d.id));
                        if (user == null) {
                            this.dialogsGroups.add(d);
                            this.dialogsGroupsAll.add(d);
                        } else if (user.bot) {
                            this.dialogsBots.add(d);
                            if (!staticBotArr.contains(d)) {
                                staticBotArr.add(d);
                            }
                        } else {
                            this.dialogsUsers.add(d);
                        }
                    }
                }
                if (favouritesIds.contains(Long.valueOf(d.id))) {
                    this.dialogsFavs.add(d);
                }
            }
            a++;
        }
        if (!selfAdded) {
            user = UserConfig.getCurrentUser();
            if (user != null) {
                TLRPC$TL_dialog dialog = new TLRPC$TL_dialog();
                dialog.id = (long) user.id;
                dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                dialog.peer = new TLRPC$TL_peerUser();
                dialog.peer.user_id = user.id;
                this.dialogsForward.add(0, dialog);
            }
        }
    }

    private static String getRestrictionReason(String reason) {
        if (reason == null || reason.length() == 0) {
            return null;
        }
        int index = reason.indexOf(": ");
        if (index <= 0) {
            return null;
        }
        String type = reason.substring(0, index);
        if (type.contains("-all") || type.contains("-android")) {
            return reason.substring(index + 2);
        }
        return null;
    }

    private static void showCantOpenAlert(BaseFragment fragment, String reason) {
        if (fragment != null && fragment.getParentActivity() != null) {
            Builder builder = new Builder(fragment.getParentActivity());
            builder.setTitle(LocaleController.getString("AppName", R.string.AppName));
            builder.setPositiveButton(LocaleController.getString("OK", R.string.OK), null);
            builder.setMessage(reason);
            fragment.showDialog(builder.create());
        }
    }

    public static boolean checkCanOpenChat(Bundle bundle, BaseFragment fragment) {
        return checkCanOpenChat(bundle, fragment, null);
    }

    public static boolean checkCanOpenChat(Bundle bundle, BaseFragment fragment, MessageObject originalMessage) {
        if (bundle == null || fragment == null) {
            return true;
        }
        User user = null;
        TLRPC$Chat chat = null;
        int user_id = bundle.getInt("user_id", 0);
        int chat_id = bundle.getInt("chat_id", 0);
        int messageId = bundle.getInt("message_id", 0);
        if (user_id != 0) {
            user = getInstance().getUser(Integer.valueOf(user_id));
        } else if (chat_id != 0) {
            chat = getInstance().getChat(Integer.valueOf(chat_id));
        }
        if (user == null && chat == null) {
            return true;
        }
        String reason = null;
        if (chat != null) {
            reason = getRestrictionReason(chat.restriction_reason);
        } else if (user != null) {
            reason = getRestrictionReason(user.restriction_reason);
        }
        if (reason != null) {
            showCantOpenAlert(fragment, reason);
            return false;
        }
        if (!(messageId == 0 || originalMessage == null || chat == null || chat.access_hash != 0)) {
            int did = (int) originalMessage.getDialogId();
            if (did != 0) {
                TLObject req;
                AlertDialog progressDialog = new AlertDialog(fragment.getParentActivity(), 1);
                progressDialog.setMessage(LocaleController.getString("Loading", R.string.Loading));
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                if (did < 0) {
                    chat = getInstance().getChat(Integer.valueOf(-did));
                }
                TLObject request;
                if (did > 0 || !ChatObject.isChannel(chat)) {
                    request = new TLRPC$TL_messages_getMessages();
                    request.id.add(Integer.valueOf(originalMessage.getId()));
                    req = request;
                } else {
                    chat = getInstance().getChat(Integer.valueOf(-did));
                    request = new TLRPC$TL_channels_getMessages();
                    request.channel = getInputChannel(chat);
                    request.id.add(Integer.valueOf(originalMessage.getId()));
                    req = request;
                }
                progressDialog.setButton(-2, LocaleController.getString("Cancel", R.string.Cancel), new MessagesController$134(ConnectionsManager.getInstance().sendRequest(req, new MessagesController$133(progressDialog, fragment, bundle)), fragment));
                fragment.setVisibleDialog(progressDialog);
                progressDialog.show();
                return false;
            }
        }
        return true;
    }

    public static void openChatOrProfileWith(User user, TLRPC$Chat chat, BaseFragment fragment, int type, boolean closeLast) {
        if ((user != null || chat != null) && fragment != null) {
            String reason = null;
            if (chat != null) {
                reason = getRestrictionReason(chat.restriction_reason);
            } else if (user != null) {
                reason = getRestrictionReason(user.restriction_reason);
                if (user.bot) {
                    type = 1;
                    closeLast = true;
                }
            }
            if (reason != null) {
                showCantOpenAlert(fragment, reason);
                return;
            }
            Bundle args = new Bundle();
            if (chat != null) {
                args.putInt("chat_id", chat.id);
            } else {
                args.putInt("user_id", user.id);
            }
            if (type == 0) {
                fragment.presentFragment(new ProfileActivity(args));
            } else if (type == 2) {
                fragment.presentFragment(new ChatActivity(args), true, true);
            } else {
                fragment.presentFragment(new ChatActivity(args), closeLast);
            }
        }
    }

    public static void openByUserName(String username, BaseFragment fragment, int type) {
        if (username != null && fragment != null) {
            TLObject object = getInstance().getUserOrChat(username);
            User user = null;
            TLRPC$Chat chat = null;
            if (object instanceof User) {
                user = (User) object;
                if (user.min) {
                    user = null;
                }
            } else if (object instanceof TLRPC$Chat) {
                chat = (TLRPC$Chat) object;
                if (chat.min) {
                    chat = null;
                }
            }
            if (user != null) {
                openChatOrProfileWith(user, null, fragment, type, false);
            } else if (chat != null) {
                openChatOrProfileWith(null, chat, fragment, 1, false);
            } else if (fragment.getParentActivity() != null) {
                AlertDialog[] progressDialog = new AlertDialog[]{new AlertDialog(fragment.getParentActivity(), 1)};
                TLRPC$TL_contacts_resolveUsername req = new TLRPC$TL_contacts_resolveUsername();
                req.username = username;
                AndroidUtilities.runOnUIThread(new MessagesController$136(progressDialog, ConnectionsManager.getInstance().sendRequest(req, new MessagesController$135(progressDialog, fragment, type)), fragment), 500);
            }
        }
    }

    public static void loadChannelInfoByUsername(String username, IResponseReceiver responseReceiver) {
        if (username != null) {
            TLRPC$TL_contacts_resolveUsername req = new TLRPC$TL_contacts_resolveUsername();
            req.username = username;
            ConnectionsManager.getInstance().sendRequest(req, new MessagesController$137(responseReceiver));
        }
    }

    public void updateDialogsForAddOrRemoveDialog() {
        Log.e("alireza", "updateDialogsForAddOrRemoveDialog dialogs");
        this.dialogsGroups.clear();
        this.dialogsUsers.clear();
        this.dialogsGroupsAll.clear();
        this.dialogsChannels.clear();
        this.dialogsMegaGroups.clear();
        this.dialogsBots.clear();
        this.dialogsFavs.clear();
        this.dialogsHidden.clear();
        this.dialogsAll.clear();
        this.dialogsUnread.clear();
        this.dialogsAds.clear();
        this.hiddensIds = AppPreferences.getHiddenList(ApplicationLoader.applicationContext);
        this.categoriesIds = AppPreferences.getAdsChannelIds(ApplicationLoader.applicationContext);
        for (int a = 0; a < this.dialogs.size(); a++) {
            TLRPC$TL_dialog d = (TLRPC$TL_dialog) this.dialogs.get(a);
            if (this.hiddensIds.contains(Long.valueOf(d.id))) {
                if (AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext)) {
                    this.dialogsHidden.add(d);
                } else {
                }
            }
            if (d.unread_count > 0) {
                this.dialogsUnread.add(d);
            }
            if (AppPreferences.isShowHiddenDialogs(ApplicationLoader.applicationContext) || !this.hiddensIds.contains(Long.valueOf(d.id))) {
                this.dialogsAll.add(d);
            }
            int high_id = (int) (d.id >> 32);
            if (!(((int) d.id) == 0 || high_id == 1)) {
                if (d instanceof TLRPC$TL_dialog) {
                    if (d.id < 0) {
                        this.dialogsGroupsOnly.add(d);
                        this.dialogsGroups.add(d);
                        this.dialogsGroupsAll.add(d);
                    } else {
                        User user = getUser(Integer.valueOf((int) d.id));
                        if (user != null) {
                            if (user.bot) {
                                this.dialogsBots.add(d);
                            } else {
                                this.dialogsUsers.add(d);
                            }
                        }
                    }
                }
                if (getEncryptedChat(Integer.valueOf(high_id)) instanceof TLRPC$TL_encryptedChat) {
                    this.dialogsUsers.add(d);
                }
                if (Favourite.isFavourite(Long.valueOf(d.id))) {
                    this.dialogsFavs.add(d);
                }
            }
        }
    }

    public void selectAllDialogs() {
        for (int i = 0; i < this.dialogs.size(); i++) {
            ((TLRPC$TL_dialog) this.dialogs.get(i)).setSelected(true);
        }
        sortDialogs(null);
    }

    public void unSelectAllDialogs() {
        for (int i = 0; i < this.dialogs.size(); i++) {
            ((TLRPC$TL_dialog) this.dialogs.get(i)).setSelected(false);
        }
        sortDialogs(null);
    }

    public ArrayList<TLRPC$TL_dialog> getSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> mDialogs = new ArrayList();
        Iterator it = this.dialogs.iterator();
        while (it.hasNext()) {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) it.next();
            if (dialog.isSelected()) {
                mDialogs.add(dialog);
            }
        }
        return mDialogs;
    }

    public void markSelectedDialogAsRead() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) i.get(a);
            if (dialg.unread_count > 0) {
                getInstance().markDialogAsRead(dialg.id, 0, Math.max(0, dialg.top_message), dialg.last_message_date, true, false);
            }
        }
    }

    public void muteSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            long dialog_id = ((TLRPC$TL_dialog) i.get(a)).id;
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("notify2_" + dialog_id, 2);
            NotificationsController.getInstance().removeNotificationsForDialog(dialog_id);
            MessagesStorage.getInstance().setDialogFlags(dialog_id, 1);
            editor.commit();
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(dialog_id));
            if (dialog != null) {
                dialog.notify_settings = new TLRPC$TL_peerNotifySettings();
                dialog.notify_settings.mute_until = Integer.MAX_VALUE;
            }
            NotificationsController.updateServerNotificationsSettings(dialog_id);
        }
    }

    public void unMuteSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            long selectedDialog = ((TLRPC$TL_dialog) i.get(a)).id;
            Editor editor = ApplicationLoader.applicationContext.getSharedPreferences("Notifications", 0).edit();
            editor.putInt("notify2_" + selectedDialog, 0);
            MessagesStorage.getInstance().setDialogFlags(selectedDialog, 0);
            editor.commit();
            TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(selectedDialog));
            if (dialg != null) {
                dialg.notify_settings = new TLRPC$TL_peerNotifySettings();
            }
            NotificationsController.updateServerNotificationsSettings(selectedDialog);
        }
    }

    public void favSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            long selectedDialog = ((TLRPC$TL_dialog) i.get(a)).id;
            TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(selectedDialog));
            if (!Favourite.isFavourite(Long.valueOf(dialg.id))) {
                Favourite.addFavourite(Long.valueOf(selectedDialog));
                getInstance().dialogsFavs.add(dialg);
            }
        }
    }

    public void unFavSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            long selectedDialog = ((TLRPC$TL_dialog) i.get(a)).id;
            TLRPC$TL_dialog dialg = (TLRPC$TL_dialog) getInstance().dialogs_dict.get(Long.valueOf(selectedDialog));
            if (dialg != null && Favourite.isFavourite(Long.valueOf(dialg.id))) {
                Favourite.deleteFavourite(Long.valueOf(selectedDialog));
                getInstance().dialogsFavs.remove(dialg);
            }
        }
    }

    public void deleteSelectedDialogs() {
        ArrayList<TLRPC$TL_dialog> i = getSelectedDialogs();
        for (int a = 0; a < i.size(); a++) {
            long selectedDialog = ((TLRPC$TL_dialog) i.get(a)).id;
            deleteDialog(selectedDialog, 0);
            getInstance().deleteUserFromChat((int) (-selectedDialog), UserConfig.getCurrentUser(), null);
            if (AndroidUtilities.isTablet()) {
                NotificationCenter.getInstance().postNotificationName(NotificationCenter.closeChats, Long.valueOf(selectedDialog));
            }
        }
    }

    public void updateDialogSelectStatus(long dialogId) {
        int counter = 0;
        Iterator it = this.dialogs.iterator();
        while (it.hasNext()) {
            TLRPC$TL_dialog dialog = (TLRPC$TL_dialog) it.next();
            if (dialogId == dialog.id) {
                ((TLRPC$TL_dialog) this.dialogs.get(counter)).setSelected(!dialog.isSelected);
                sortDialogs(null);
            }
            counter++;
        }
        sortDialogs(null);
    }
}
